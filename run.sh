#!/bin/zsh

export MVN_ANDROID_BUILD_TOOL_VERSION=22.0.1
export MVN_JAVA_VERSION=1.5


export JAVA_HOME="/usr/lib/jvm/jdk1.8"
export JAVA_JAR="/usr/lib/jvm/jdk$MVN_JAVA_VERSION/bin/jar"
export ANDROID_HOME="$HOME/android-sdk/"
export PATH=$PATH:$ANDROID_HOME/platform-tools/:$ANDROID_HOME/build-tools/$MVN_ANDROID_BUILD_TOOL_VERSION:$JAVA_HOME/bin

command -v adb >/dev/null 2>&1 || { echo >&2 "I require adb but it's not installed.  Aborting."; exit 1; }
command -v $JAVA_JAR >/dev/null 2>&1 || { echo >&2 "I require jar but it's not installed.  Aborting."; exit 1; }
command -v sh >/dev/null 2>&1 || { echo >&2 "I require sh but it's not installed.  Aborting."; exit 1; }
command -v aapt >/dev/null 2>&1 || { echo >&2 "I require aapt but it's not installed.  Aborting."; exit 1; }

# adb shell am force-stop br.com.vt.mapek/br.com.vt.mapek.android.StatusActivity
adb shell rm -rf /data/data/br.com.vt.mapek/cache



dxmefunc() {
    dx --dex --core-library --output=classes.dex $1 && aapt add $1 classes.dex
    rm -rf classes.dex
}
dxallfunc(){
	##http://unix.stackexchange.com/questions/310540/how-to-get-rid-of-no-match-found-when-running-rm
	setopt +o nomatch

	find *.jar -type f \
	-exec echo :: Removendo diretorio _{}_ \; \
	-exec rm -rf _{}_ \; \
	-exec echo :: Criando diretorio _{}_ \; \
	-exec mkdir _{}_ \; \
	-exec echo :: Copiando JARs {} para _{}_ \; \
	-exec cp {} _{}_/ \; 

	for dir in _*_; do echo "
	cd \$(dirname \$0) && echo :: Descopactando JAR ::  && unzip *.jar
	echo \$(pwd)
	if [ -d \"lib/\" ]; then
	find lib/*.jar -type f \\
	-exec echo :: LIB :: Injetando DEX nos JARs em  \$(pwd)/{} \; \\
	-exec dx --dex --core-library --output=classes.dex {} \; \\
	-exec aapt add {} classes.dex  \; \\
	-exec rm  classes.dex  \;  
	fi

	if [ -d \"bundles/\" ]; then
	find bundles/*.bundle -type f \\
	-exec echo :: BUNDLES :: Injetando DEX nos Bundles em  \$(pwd)/{} \; \\
	-exec mv {} {}.jar \; \\
	-exec dx --dex --core-library --output=classes.dex {}.jar \; \\
	-exec aapt add {}.jar classes.dex  \; \\
	-exec rm  classes.dex  \; \\
	-exec mv {}.jar {} \;
	fi

	find . -name *.jar -type f \\
	-exec echo :: Criando JAR :: {} \; \\
	-exec rm {}  \; \\
	-exec $JAVA_JAR cfm {} META-INF/MANIFEST.MF . \; \\
	-exec echo :: Injetando dex no JAR \$(pwd)/{} \; \\
	-exec dx --dex --core-library --output=classes.dex {} \; \\
	-exec aapt add {} classes.dex  \; \\
	-exec rm  classes.dex  \; 
	" > "$dir"/unzipAndCompileLibs.sh; done

	find *.jar -type f \
	-exec sh _{}_/unzipAndCompileLibs.sh \; \
	-exec echo :: Substituindo JAR {} \; \
	-exec mv _{}_/{} . \; \
	-exec rm -rf _{}_ \; 

}
# \$(find */*.jar -type f -exec echo {} \; && find */*.bundle -type f -exec echo {} \; | tr '\n' ' ')  \\
	
alias dxme=dxmefunc
alias dxall=dxallfunc
export dxall=dxallfunc
export dxme=dxmefunc
# echo Gerando numeros randomicos && \
# shuf -i 1-100000000 | tr '\n' ',' > mapek.bundles/mapek.bundles.resource/src/main/resources/input.csv && \
echo Construindo o Projeto && \
mvn clean install && \

echo "Compilando mb_* bundles para o dalvikVM" && \
echo $(cd mapek.android/res/raw && rm -rf dx && mkdir dx && mv mb_* dx && cd dx && dxall && mv mb_* .. && cd .. && rm -rf dx) > /dev/null && \
adb install -r mapek.android/target/mapek.android.apk && \
adb shell am start -n br.com.vt.mapek/br.com.vt.mapek.android.StatusActivity

#Result
# watch -n 1 adb pull /storage/sdcard0/result.csv
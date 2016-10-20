cd $ANDROID_HOME

mvn install:install-file -Dfile="./extras/android/m2repository/com/android/support/appcompat-v7/18.0.0/appcompat-v7-18.0.0.jar"/ -DpomFile="./extras/android/m2repository/com/android/support/appcompat-v7/18.0.0/appcompat-v7-18.0.0.pom"/ -Dpackaging="jar"
mvn install:install-file -Dfile="./extras/android/m2repository/com/android/support/appcompat-v7/18.0.0/appcompat-v7-18.0.0.aar"/ -DpomFile="./extras/android/m2repository/com/android/support/appcompat-v7/18.0.0/appcompat-v7-18.0.0.pom"/ -Dpackaging="apklib"
mvn install:install-file -Dfile="./extras/android/m2repository/com/android/support/support-v4/18.0.0/support-v4-18.0.0.jar"/ -DpomFile="./extras/android/m2repository/com/android/support/support-v4/18.0.0/support-v4-18.0.0.pom"/ -Dpackaging="jar"
mvn install:install-file -Dfile="./extras/android/m2repository/com/android/support/support-v4/18.0.0/support-v4-18.0.0.aar"/ -DpomFile="./extras/android/m2repository/com/android/support/support-v4/18.0.0/support-v4-18.0.0.pom"/ -Dpackaging="apklib"


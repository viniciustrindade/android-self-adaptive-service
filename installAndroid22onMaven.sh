mvn install:install-file \
  -DgroupId=com.google.android \
  -DartifactId=android \
  -Dpackaging=jar \
  -Dversion=5.1.1 \
  -Dfile=$ANDROID_HOME/platforms/android-22/android.jar
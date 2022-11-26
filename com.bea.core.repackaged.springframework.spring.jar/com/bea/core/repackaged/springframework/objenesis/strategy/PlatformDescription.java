package com.bea.core.repackaged.springframework.objenesis.strategy;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import java.lang.reflect.Field;

public final class PlatformDescription {
   public static final String GNU = "GNU libgcj";
   public static final String HOTSPOT = "Java HotSpot";
   /** @deprecated */
   @Deprecated
   public static final String SUN = "Java HotSpot";
   public static final String OPENJDK = "OpenJDK";
   public static final String PERC = "PERC";
   public static final String DALVIK = "Dalvik";
   public static final String SPECIFICATION_VERSION = System.getProperty("java.specification.version");
   public static final String VM_VERSION = System.getProperty("java.runtime.version");
   public static final String VM_INFO = System.getProperty("java.vm.info");
   public static final String VENDOR_VERSION = System.getProperty("java.vm.version");
   public static final String VENDOR = System.getProperty("java.vm.vendor");
   public static final String JVM_NAME = System.getProperty("java.vm.name");
   public static final int ANDROID_VERSION = getAndroidVersion();
   public static final boolean IS_ANDROID_OPENJDK = getIsAndroidOpenJDK();
   public static final String GAE_VERSION = getGaeRuntimeVersion();

   public static String describePlatform() {
      String desc = "Java " + SPECIFICATION_VERSION + " (VM vendor name=\"" + VENDOR + "\", VM vendor version=" + VENDOR_VERSION + ", JVM name=\"" + JVM_NAME + "\", JVM version=" + VM_VERSION + ", JVM info=" + VM_INFO;
      if (ANDROID_VERSION != 0) {
         desc = desc + ", API level=" + ANDROID_VERSION;
      }

      desc = desc + ")";
      return desc;
   }

   public static boolean isThisJVM(String name) {
      return JVM_NAME.startsWith(name);
   }

   public static boolean isAndroidOpenJDK() {
      return IS_ANDROID_OPENJDK;
   }

   private static boolean getIsAndroidOpenJDK() {
      if (getAndroidVersion() == 0) {
         return false;
      } else {
         String bootClasspath = System.getProperty("java.boot.class.path");
         return bootClasspath != null && bootClasspath.toLowerCase().contains("core-oj.jar");
      }
   }

   public static boolean isAfterJigsaw() {
      String version = SPECIFICATION_VERSION;
      return version.indexOf(46) < 0;
   }

   public static boolean isAfterJava11() {
      if (!isAfterJigsaw()) {
         return false;
      } else {
         int version = Integer.parseInt(SPECIFICATION_VERSION);
         return version >= 11;
      }
   }

   public static boolean isGoogleAppEngine() {
      return GAE_VERSION != null;
   }

   private static String getGaeRuntimeVersion() {
      return System.getProperty("com.google.appengine.runtime.version");
   }

   private static int getAndroidVersion() {
      return !isThisJVM("Dalvik") ? 0 : getAndroidVersion0();
   }

   private static int getAndroidVersion0() {
      Class clazz;
      try {
         clazz = Class.forName("android.os.Build$VERSION");
      } catch (ClassNotFoundException var6) {
         throw new ObjenesisException(var6);
      }

      Field field;
      try {
         field = clazz.getField("SDK_INT");
      } catch (NoSuchFieldException var5) {
         return getOldAndroidVersion(clazz);
      }

      try {
         int version = (Integer)field.get((Object)null);
         return version;
      } catch (IllegalAccessException var4) {
         throw new RuntimeException(var4);
      }
   }

   private static int getOldAndroidVersion(Class versionClass) {
      Field field;
      try {
         field = versionClass.getField("SDK");
      } catch (NoSuchFieldException var5) {
         throw new ObjenesisException(var5);
      }

      String version;
      try {
         version = (String)field.get((Object)null);
      } catch (IllegalAccessException var4) {
         throw new RuntimeException(var4);
      }

      return Integer.parseInt(version);
   }

   private PlatformDescription() {
   }
}

package com.bea.core.repackaged.springframework.core;

abstract class GraalDetector {
   private static final boolean imageCode = System.getProperty("org.graalvm.nativeimage.imagecode") != null;

   public static boolean inImageCode() {
      return imageCode;
   }
}

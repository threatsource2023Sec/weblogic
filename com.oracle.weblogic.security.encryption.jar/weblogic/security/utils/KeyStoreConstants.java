package weblogic.security.utils;

import java.io.File;
import weblogic.management.DomainDir;
import weblogic.utils.classloaders.Home;

public class KeyStoreConstants implements WLSKeyStoreConstants {
   public static final String DEMO_CA_NAME = "CertGenCA";
   public static final String JAVA_HOME_STRING = "$JAVA_HOME";

   private static final String getWLSLibRelativePath(String filename) {
      return Home.getPath() + File.separator + "lib" + File.separator + filename;
   }

   public static final String getDemoIdentityKeyStoreFileName() {
      return DomainDir.getSecurityDir() + File.separator + "DemoIdentity.jks";
   }

   public static final String getDemoTrustKeyStoreFileName() {
      return getWLSLibRelativePath("DemoTrust.jks");
   }

   public static final String getDemoCompatibilityTrustKeyStoreFileName() {
      return getWLSLibRelativePath("cacerts");
   }

   public static final String getJavaStandardTrustKeyStoreFileName() {
      return System.getProperty("java.home") + File.separator + "lib" + File.separator + "security" + File.separator + "cacerts";
   }

   public static final String getJavaHomeStandardTrustKeyStoreFileName() {
      return "$JAVA_HOME" + File.separator + "lib" + File.separator + "security" + File.separator + "cacerts";
   }
}

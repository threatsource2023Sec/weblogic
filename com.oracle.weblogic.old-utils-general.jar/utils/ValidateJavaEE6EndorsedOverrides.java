package utils;

public class ValidateJavaEE6EndorsedOverrides {
   private static final String ENDORSED_MESSAGE = " API is required, but an older version was found in the JDK.\nUse the endorsed standards override mechanism (http://java.sun.com/javase/6/docs/technotes/guides/standards/).\n1) locate the bundled Java EE 6 endorsed directory in $MW_HOME/oracle_common/modules/endorsed.\n2) copy those JAR files to $JAVA_HOME/jre/lib/endorsed OR add the endorsed directory to the value specified by system property java.endorsed.dirs.";
   private static final String JAXWS_MESSAGE = "JAX-WS 2.2 API is required, but an older version was found in the JDK.\nUse the endorsed standards override mechanism (http://java.sun.com/javase/6/docs/technotes/guides/standards/).\n1) locate the bundled Java EE 6 endorsed directory in $MW_HOME/oracle_common/modules/endorsed.\n2) copy those JAR files to $JAVA_HOME/jre/lib/endorsed OR add the endorsed directory to the value specified by system property java.endorsed.dirs.";
   private static final String JAXB_MESSAGE = "JAXB 2.2.1 API is required, but an older version was found in the JDK.\nUse the endorsed standards override mechanism (http://java.sun.com/javase/6/docs/technotes/guides/standards/).\n1) locate the bundled Java EE 6 endorsed directory in $MW_HOME/oracle_common/modules/endorsed.\n2) copy those JAR files to $JAVA_HOME/jre/lib/endorsed OR add the endorsed directory to the value specified by system property java.endorsed.dirs.";
   private static final String JSR250_MESSAGE = "Common Annotations for the Java Platform Maintenance Release 1.1 API is required, but an older version was found in the JDK.\nUse the endorsed standards override mechanism (http://java.sun.com/javase/6/docs/technotes/guides/standards/).\n1) locate the bundled Java EE 6 endorsed directory in $MW_HOME/oracle_common/modules/endorsed.\n2) copy those JAR files to $JAVA_HOME/jre/lib/endorsed OR add the endorsed directory to the value specified by system property java.endorsed.dirs.";

   public static final boolean validateEndorsedOverrides(boolean fail) throws IllegalStateException {
      return true;
   }

   private static final boolean validateLib(String className, String methodName, String errorMessage, boolean fail) {
      try {
         Class clz = Class.forName(className);
         clz.getMethod(methodName);
         return true;
      } catch (ClassNotFoundException var5) {
         if (fail) {
            throw new AssertionError(errorMessage);
         } else {
            return false;
         }
      } catch (NoSuchMethodException var6) {
         if (fail) {
            throw new AssertionError(errorMessage);
         } else {
            return false;
         }
      }
   }
}

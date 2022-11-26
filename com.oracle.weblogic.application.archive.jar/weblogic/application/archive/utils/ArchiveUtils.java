package weblogic.application.archive.utils;

public class ArchiveUtils {
   public static final String JMS_POSTFIX = "-jms.xml";
   public static final String JDBC_POSTFIX = "-jdbc.xml";
   public static final String APPLICATION_POSTFIX = ".ear";
   public static final String EJBJAR_POSTFIX = ".jar";
   public static final String WEBAPP_POSTFIX = ".war";
   public static final String CONNECTOR_POSTFIX = ".rar";
   public static final String CLIENT_POSTFIX = ".jar";

   public static final boolean isValidArchiveName(String name) {
      String lcName = name.toLowerCase();
      return lcName.endsWith(".ear") || isValidArchiveModuleName(name);
   }

   public static final boolean isValidArchiveModuleName(String name) {
      String lowerCaseName = name.toLowerCase();
      return lowerCaseName.endsWith(".jar") || lowerCaseName.endsWith(".war") || lowerCaseName.endsWith(".jar") || lowerCaseName.endsWith(".rar");
   }

   public static final boolean isValidWLSModuleName(String name) {
      String lcName = name.toLowerCase();
      return lcName.endsWith("-jms.xml") || lcName.endsWith("-jdbc.xml");
   }
}

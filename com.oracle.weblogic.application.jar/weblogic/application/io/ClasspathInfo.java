package weblogic.application.io;

public interface ClasspathInfo {
   String[] getClasspathURIs();

   String[] getJarURIs();

   ArchiveType getArchiveType();

   public static enum ArchiveType {
      EAR,
      WAR,
      RAR,
      OTHER;
   }
}

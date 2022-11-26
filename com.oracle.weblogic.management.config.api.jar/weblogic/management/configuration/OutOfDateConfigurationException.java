package weblogic.management.configuration;

public final class OutOfDateConfigurationException extends ConfigurationException {
   private static final long serialVersionUID = 5881019814205842701L;
   private Object version;
   private Object currentVersion;

   protected OutOfDateConfigurationException(Object currentVersion, Object version, String message) {
      super(message);
      this.version = version;
      this.currentVersion = currentVersion;
   }

   public Object getVersion() {
      return this.version;
   }

   public Object getCurrentVersion() {
      return this.currentVersion;
   }
}

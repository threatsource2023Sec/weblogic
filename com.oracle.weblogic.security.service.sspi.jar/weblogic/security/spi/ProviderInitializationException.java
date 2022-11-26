package weblogic.security.spi;

public class ProviderInitializationException extends SecurityException {
   public ProviderInitializationException(String string) {
      super(string);
   }

   public ProviderInitializationException(String string, Throwable cause) {
      super(string);
      this.initCause(cause);
   }
}

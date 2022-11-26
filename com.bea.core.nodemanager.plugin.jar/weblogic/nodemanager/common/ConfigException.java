package weblogic.nodemanager.common;

public class ConfigException extends Exception {
   public ConfigException(String msg) {
      super(msg);
   }

   public ConfigException(String msg, Throwable cause) {
      super(msg);
      this.initCause(cause);
   }
}

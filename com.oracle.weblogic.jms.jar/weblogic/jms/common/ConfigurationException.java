package weblogic.jms.common;

public final class ConfigurationException extends JMSException {
   static final long serialVersionUID = -7415015313197218710L;

   public ConfigurationException(String reason, String errorCode) {
      super(reason, errorCode);
   }

   public ConfigurationException(String reason, String errorCode, Throwable throwable) {
      super(reason, errorCode, throwable);
   }

   public ConfigurationException(String reason) {
      super(reason);
   }

   public ConfigurationException(String reason, Throwable throwable) {
      super(reason, throwable);
   }
}

package weblogic.deploy.service;

public class RegistrationException extends Exception {
   private final String msg;

   public RegistrationException(String msg) {
      super(msg);
      this.msg = msg;
   }

   public String toString() {
      return this.msg != null ? "RegistrationException : " + this.msg : "RegistrationException";
   }
}

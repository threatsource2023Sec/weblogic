package weblogic.deploy.service;

public class RegistrationExistsException extends RegistrationException {
   private final String msg;

   public RegistrationExistsException(String msg) {
      super(msg);
      this.msg = msg;
   }

   public String toString() {
      return this.msg != null ? "RegistrationExistsException : " + this.msg : "RegistrationExistsException";
   }
}

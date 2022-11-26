package weblogic.deploy.service;

public class InvalidCreateChangeDescriptorException extends Exception {
   private final String msg;

   public InvalidCreateChangeDescriptorException(String msg) {
      super(msg);
      this.msg = msg;
   }

   public String toString() {
      return this.msg != null ? "InvalidCreateChangeDescriptorException : " + this.msg : "InvalidCreateChangeDescriptorException";
   }
}

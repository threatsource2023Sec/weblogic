package weblogic.deploy.service;

public class VersionExistsException extends Exception {
   private final String msg;

   public VersionExistsException(String msg) {
      super(msg);
      this.msg = msg;
   }

   public String toString() {
      return this.msg != null ? "VersionExistsException : " + this.msg : "VersionExistsException";
   }
}

package weblogic.deploy.service;

public class DataTransferHandlerExistsException extends Exception {
   private final String msg;

   public DataTransferHandlerExistsException(String msg) {
      super(msg);
      this.msg = msg;
   }

   public String toString() {
      return this.msg != null ? "DataTransferHandlerExistsException : " + this.msg : "DataTransferHandlerExistsException";
   }
}

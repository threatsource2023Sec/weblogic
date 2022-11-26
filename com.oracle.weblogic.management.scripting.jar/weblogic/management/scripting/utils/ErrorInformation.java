package weblogic.management.scripting.utils;

public class ErrorInformation {
   private Throwable th = null;
   private String message = null;

   public ErrorInformation(Throwable e, String message) {
      this.th = e;
      this.message = message;
   }

   public ErrorInformation(String message) {
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }

   public Throwable getError() {
      return this.th;
   }
}

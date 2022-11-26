package weblogic.application.library;

public class IllegalSpecVersionTypeException extends Exception {
   private final String illegalSpecVersion;

   public IllegalSpecVersionTypeException(String illegalSpecVersion) {
      this.illegalSpecVersion = illegalSpecVersion;
   }

   public String getSpecVersion() {
      return this.illegalSpecVersion;
   }
}

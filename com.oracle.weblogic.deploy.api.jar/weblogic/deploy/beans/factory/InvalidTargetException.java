package weblogic.deploy.beans.factory;

public class InvalidTargetException extends Exception {
   public InvalidTargetException(String targetName) {
      super(targetName);
   }
}

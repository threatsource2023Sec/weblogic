package kodo.jdo;

public class GeneralException extends CanRetryException {
   public GeneralException(String msg, Throwable[] nested, Object failed) {
      super(msg, nested, failed);
   }

   protected CanRetryException newSerializableInstance(String msg, Throwable[] nested, Object failed) {
      return new GeneralException(msg, nested, failed);
   }
}

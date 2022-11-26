package kodo.jdo;

public class LockException extends DataStoreException {
   private final int timeout;

   public LockException(String msg, Throwable[] nested, Object failed, int timeout) {
      super(msg, nested, failed);
      this.timeout = timeout;
   }

   public int getTimeout() {
      return this.timeout;
   }

   public int getSubtype() {
      return 1;
   }

   protected DataStoreException newSerializableInstance(String msg, Throwable[] nested, Object failed) {
      return new LockException(msg, nested, failed, this.timeout);
   }
}

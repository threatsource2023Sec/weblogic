package weblogic.cache;

public class CacheRuntimeException extends RuntimeException {
   public CacheRuntimeException() {
   }

   public CacheRuntimeException(String message) {
      super(message);
   }

   public CacheRuntimeException(Throwable t) {
      super(t);
   }

   public CacheRuntimeException(String messsage, Throwable t) {
      super(messsage, t);
   }
}

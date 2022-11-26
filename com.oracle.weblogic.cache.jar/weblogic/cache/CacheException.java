package weblogic.cache;

import weblogic.utils.NestedException;

public class CacheException extends NestedException {
   public CacheException(String message) {
      super(message);
   }

   public CacheException(String messsage, Throwable t) {
      super(messsage, t);
   }
}

package org.python.indexer;

public class IndexingException extends RuntimeException {
   public IndexingException() {
   }

   public IndexingException(String msg) {
      super(msg);
   }

   public IndexingException(String msg, Throwable cause) {
      super(msg, cause);
   }

   public IndexingException(Throwable cause) {
      super(cause);
   }
}

package com.ziclix.python.sql.util;

public class QueueClosedException extends RuntimeException {
   public QueueClosedException() {
   }

   public QueueClosedException(String msg) {
      super(msg);
   }
}

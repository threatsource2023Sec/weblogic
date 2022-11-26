package com.bea.logging;

import java.io.IOException;

public class LogRotationException extends IOException {
   public LogRotationException() {
   }

   public LogRotationException(String msg) {
      super(msg);
   }
}

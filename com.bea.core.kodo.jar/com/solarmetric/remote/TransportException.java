package com.solarmetric.remote;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.openjpa.lib.util.Localizer;

public class TransportException extends NestableRuntimeException {
   public TransportException() {
   }

   public TransportException(Localizer.Message msg) {
      super(msg.getMessage());
   }

   public TransportException(Throwable t) {
      super(t);
   }

   public TransportException(Localizer.Message msg, Throwable t) {
      super(msg.getMessage(), t);
   }
}

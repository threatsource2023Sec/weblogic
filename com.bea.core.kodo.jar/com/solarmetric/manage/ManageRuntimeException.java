package com.solarmetric.manage;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.openjpa.lib.util.Localizer;

public class ManageRuntimeException extends NestableRuntimeException {
   public ManageRuntimeException() {
   }

   public ManageRuntimeException(String msg) {
      super(msg);
   }

   public ManageRuntimeException(Localizer.Message msg) {
      super(msg.getMessage());
   }

   public ManageRuntimeException(Throwable t) {
      super(t);
   }

   public ManageRuntimeException(Localizer.Message msg, Throwable t) {
      super(msg.getMessage(), t);
   }
}

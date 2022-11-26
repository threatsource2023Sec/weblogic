package com.solarmetric.profile;

import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.openjpa.lib.util.Localizer;

public class ProfilingEnhancerException extends NestableRuntimeException {
   public ProfilingEnhancerException(Localizer.Message msg) {
      super(msg.getMessage());
   }

   public ProfilingEnhancerException(Localizer.Message msg, Throwable t) {
      super(msg.getMessage(), t);
   }

   public ProfilingEnhancerException(String msg, Throwable t) {
      super(msg, t);
   }
}

package com.solarmetric.profile;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class ProfilingExportException extends NestableRuntimeException {
   public ProfilingExportException(String msg) {
      super(msg);
   }

   public ProfilingExportException(Throwable t) {
      super(t);
   }

   public ProfilingExportException(String msg, Throwable t) {
      super(msg, t);
   }
}

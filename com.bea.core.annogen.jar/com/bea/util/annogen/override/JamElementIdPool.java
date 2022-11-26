package com.bea.util.annogen.override;

import com.bea.util.annogen.override.internal.jam.JamElementIdPoolImpl;
import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.internal.JamLoggerImpl;
import com.bea.util.jam.provider.JamLogger;

public interface JamElementIdPool {
   ElementId getIdFor(JAnnotatedElement var1);

   public static class Factory {
      public static JamElementIdPool create(JamLogger logger) {
         return new JamElementIdPoolImpl(logger);
      }

      public static JamElementIdPool create() {
         return new JamElementIdPoolImpl(new JamLoggerImpl());
      }
   }
}

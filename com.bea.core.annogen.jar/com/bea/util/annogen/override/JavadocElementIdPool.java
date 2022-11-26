package com.bea.util.annogen.override;

import com.bea.util.annogen.override.internal.javadoc.JavadocElementIdPoolImpl;
import com.bea.util.jam.internal.JamLoggerImpl;
import com.bea.util.jam.provider.JamLogger;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.ProgramElementDoc;

public interface JavadocElementIdPool {
   ElementId getIdFor(ProgramElementDoc var1);

   ElementId getIdFor(ExecutableMemberDoc var1, int var2);

   public static class Factory {
      public static JavadocElementIdPool create(JamLogger logger) {
         return new JavadocElementIdPoolImpl(logger);
      }

      public static JavadocElementIdPool create() {
         return new JavadocElementIdPoolImpl(new JamLoggerImpl());
      }
   }
}

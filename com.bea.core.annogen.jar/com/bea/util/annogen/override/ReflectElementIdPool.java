package com.bea.util.annogen.override;

import com.bea.util.annogen.override.internal.reflect.ReflectElementIdPoolImpl;
import com.bea.util.jam.internal.JamLoggerImpl;
import com.bea.util.jam.provider.JamLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface ReflectElementIdPool {
   ElementId getIdFor(Class var1);

   ElementId getIdFor(Package var1);

   ElementId getIdFor(Field var1);

   ElementId getIdFor(Constructor var1);

   ElementId getIdFor(Method var1);

   ElementId getIdFor(Method var1, int var2);

   ElementId getIdFor(Constructor var1, int var2);

   public static class Factory {
      public static ReflectElementIdPool create(JamLogger logger) {
         return new ReflectElementIdPoolImpl(logger);
      }

      public static ReflectElementIdPool create() {
         return new ReflectElementIdPoolImpl(new JamLoggerImpl());
      }
   }
}

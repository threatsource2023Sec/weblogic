package com.bea.util.annogen.view.internal.reflect;

import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.jam.internal.TigerDelegateHelper;
import com.bea.util.jam.provider.JamLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class ReflectAnnogenTigerDelegate {
   private static final String IMPL_NAME = "com.bea.util.annogen.view.internal.ReflectAnnogenTigerDelegateImpl_150";

   public static ReflectAnnogenTigerDelegate create(JamLogger logger) {
      if (!TigerDelegateHelper.isTigerReflectionAvailable(logger)) {
         return null;
      } else {
         try {
            ReflectAnnogenTigerDelegate out = (ReflectAnnogenTigerDelegate)Class.forName("com.bea.util.annogen.view.internal.ReflectAnnogenTigerDelegateImpl_150").newInstance();
            out.init(logger);
            return out;
         } catch (ClassNotFoundException var2) {
            TigerDelegateHelper.issue14BuildWarning(var2, logger);
         } catch (IllegalAccessException var3) {
            logger.error((Throwable)var3);
         } catch (InstantiationException var4) {
            logger.error((Throwable)var4);
         }

         return null;
      }
   }

   protected ReflectAnnogenTigerDelegate() {
   }

   public abstract Class getAnnotationClassFor(Object var1);

   public abstract void init(JamLogger var1);

   public abstract boolean extractAnnotations(AnnoBeanSet var1, Package var2);

   public abstract boolean extractAnnotations(AnnoBeanSet var1, Class var2);

   public abstract boolean extractAnnotations(AnnoBeanSet var1, Method var2);

   public abstract boolean extractAnnotations(AnnoBeanSet var1, Field var2);

   public abstract boolean extractAnnotations(AnnoBeanSet var1, Constructor var2);

   public abstract boolean extractAnnotations(AnnoBeanSet var1, Method var2, int var3);

   public abstract boolean extractAnnotations(AnnoBeanSet var1, Constructor var2, int var3);
}

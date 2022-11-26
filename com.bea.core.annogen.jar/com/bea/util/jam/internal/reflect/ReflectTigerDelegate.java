package com.bea.util.jam.internal.reflect;

import com.bea.util.jam.internal.TigerDelegateHelper;
import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.mutable.MClass;
import com.bea.util.jam.mutable.MConstructor;
import com.bea.util.jam.mutable.MField;
import com.bea.util.jam.mutable.MMember;
import com.bea.util.jam.mutable.MParameter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class ReflectTigerDelegate {
   private static final String IMPL_NAME = "com.bea.util.jam.internal.reflect.ReflectTigerDelegateImpl_150";

   public static ReflectTigerDelegate create(ElementContext ctx) {
      if (!TigerDelegateHelper.isTigerReflectionAvailable(ctx.getLogger())) {
         return null;
      } else {
         try {
            ReflectTigerDelegate out = (ReflectTigerDelegate)Class.forName("com.bea.util.jam.internal.reflect.ReflectTigerDelegateImpl_150").newInstance();
            out.init(ctx);
            return out;
         } catch (ClassNotFoundException var2) {
            TigerDelegateHelper.issue14BuildWarning(var2, ctx.getLogger());
         } catch (IllegalAccessException var3) {
            ctx.getLogger().error((Throwable)var3);
         } catch (InstantiationException var4) {
            ctx.getLogger().error((Throwable)var4);
         }

         return null;
      }
   }

   protected ReflectTigerDelegate() {
   }

   protected abstract void init(ElementContext var1);

   public abstract void populateAnnotationTypeIfNecessary(Class var1, MClass var2, ReflectClassBuilder var3);

   public abstract boolean isEnum(Class var1);

   public abstract Constructor getEnclosingConstructor(Class var1);

   public abstract Method getEnclosingMethod(Class var1);

   public abstract void extractAnnotations(MMember var1, Method var2);

   public abstract void extractAnnotations(MConstructor var1, Constructor var2);

   public abstract void extractAnnotations(MField var1, Field var2);

   public abstract void extractAnnotations(MClass var1, Class var2);

   public abstract void extractAnnotations(MParameter var1, Method var2, int var3);

   public abstract void extractAnnotations(MParameter var1, Constructor var2, int var3);
}

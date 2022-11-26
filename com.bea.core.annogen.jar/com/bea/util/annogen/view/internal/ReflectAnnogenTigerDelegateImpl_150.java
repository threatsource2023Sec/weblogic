package com.bea.util.annogen.view.internal;

import com.bea.util.annogen.override.AnnoBean;
import com.bea.util.annogen.override.AnnoBeanSet;
import com.bea.util.annogen.view.internal.reflect.ReflectAnnogenTigerDelegate;
import com.bea.util.jam.provider.JamLogger;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ReflectAnnogenTigerDelegateImpl_150 extends ReflectAnnogenTigerDelegate {
   private JamLogger mLogger;

   public void init(JamLogger l) {
      this.mLogger = l;
   }

   public Class getAnnotationClassFor(Object annotation) {
      return ((Annotation)annotation).annotationType();
   }

   public boolean extractAnnotations(AnnoBeanSet out, Package on) {
      return this.doExtract(out, on.getAnnotations());
   }

   public boolean extractAnnotations(AnnoBeanSet out, Class on) {
      return this.doExtract(out, on.getAnnotations());
   }

   public boolean extractAnnotations(AnnoBeanSet out, Method on) {
      return this.doExtract(out, on.getAnnotations());
   }

   public boolean extractAnnotations(AnnoBeanSet out, Field on) {
      return this.doExtract(out, on.getAnnotations());
   }

   public boolean extractAnnotations(AnnoBeanSet out, Constructor on) {
      return this.doExtract(out, on.getAnnotations());
   }

   public boolean extractAnnotations(AnnoBeanSet out, Method on, int pnum) {
      Annotation[][] raw = on.getParameterAnnotations();
      return out != null && raw.length > pnum ? this.doExtract(out, raw[pnum]) : false;
   }

   public boolean extractAnnotations(AnnoBeanSet out, Constructor on, int pnum) {
      Annotation[][] raw = on.getParameterAnnotations();
      return out != null && raw.length > pnum ? this.doExtract(out, raw[pnum]) : false;
   }

   private boolean doExtract(AnnoBeanSet out, Object[] raw) {
      if (raw != null && raw.length != 0) {
         for(int i = 0; i < raw.length; ++i) {
            Class declClass = this.getAnnotationClassFor(raw[i]);
            AnnoBean proxy = out.findOrCreateBeanFor(declClass);
            if (proxy != null) {
               this.copyValues(raw[i], proxy, declClass);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void copyValues(Object src, AnnoBean dest, Class declaredClass) {
      boolean isVerbose = false;
      if (src == null) {
         throw new IllegalArgumentException();
      } else {
         if (isVerbose) {
            this.mLogger.verbose("type is " + declaredClass.getName());
         }

         Method[] methods = declaredClass.getMethods();

         for(int i = 0; i < methods.length; ++i) {
            if (isVerbose) {
               this.mLogger.verbose("examining " + methods[i].toString());
            }

            int mods = methods[i].getModifiers();
            if (!Modifier.isStatic(mods) && Modifier.isPublic(mods) && methods[i].getParameterTypes().length <= 0) {
               Class c = methods[i].getDeclaringClass();
               if (!Object.class.equals(c)) {
                  if (isVerbose) {
                     this.mLogger.verbose("invoking " + methods[i].getName() + "()");
                  }

                  Object value;
                  try {
                     value = methods[i].invoke(src, (Object[])null);
                  } catch (IllegalAccessException var12) {
                     this.mLogger.error((Throwable)var12);
                     continue;
                  } catch (InvocationTargetException var13) {
                     this.mLogger.error((Throwable)var13);
                     continue;
                  }

                  if (isVerbose) {
                     this.mLogger.verbose("value is " + value);
                  }

                  Class valClass = value.getClass();

                  try {
                     if (!this.isSimpleType(valClass)) {
                        if (!valClass.isArray()) {
                           throw new IllegalArgumentException("complex annotation properties NYI " + valClass.getName());
                        }

                        if (this.isSimpleType(valClass.getComponentType())) {
                        }

                        throw new IllegalArgumentException("array annotation properties NYI");
                     }

                     dest.setValue(methods[i].getName(), value);
                  } catch (Exception var11) {
                     this.mLogger.error((Throwable)var11);
                  }
               }
            }
         }

      }
   }

   private boolean isSimpleType(Class c) {
      return c.isPrimitive() || String.class.equals(c) || Number.class.isAssignableFrom(c) || Boolean.class.equals(c) || Character.class.equals(c) || Character.class.equals(c) || Class.class.equals(c);
   }
}

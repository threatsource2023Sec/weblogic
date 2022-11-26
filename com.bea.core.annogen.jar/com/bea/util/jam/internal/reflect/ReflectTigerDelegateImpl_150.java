package com.bea.util.jam.internal.reflect;

import com.bea.util.jam.JClass;
import com.bea.util.jam.internal.elements.ElementContext;
import com.bea.util.jam.mutable.MAnnotatedElement;
import com.bea.util.jam.mutable.MAnnotation;
import com.bea.util.jam.mutable.MClass;
import com.bea.util.jam.mutable.MConstructor;
import com.bea.util.jam.mutable.MField;
import com.bea.util.jam.mutable.MMember;
import com.bea.util.jam.mutable.MParameter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class ReflectTigerDelegateImpl_150 extends ReflectTigerDelegate {
   private ElementContext mContext;

   public void init(ElementContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException();
      } else {
         this.mContext = ctx;
      }
   }

   public void populateAnnotationTypeIfNecessary(Class cd, MClass clazz, ReflectClassBuilder builder) {
      clazz.setIsAnnotationType(cd.isAnnotation());
   }

   public void extractAnnotations(MMember dest, Method src) {
      Annotation[] anns = src.getDeclaredAnnotations();
      if (anns != null) {
         this.extractAnnotations((MAnnotatedElement)dest, (Annotation[])anns);
      }
   }

   public void extractAnnotations(MConstructor dest, Constructor src) {
      Annotation[] anns = src.getDeclaredAnnotations();
      if (anns != null) {
         this.extractAnnotations((MAnnotatedElement)dest, (Annotation[])anns);
      }
   }

   public void extractAnnotations(MField dest, Field src) {
      Annotation[] anns = src.getDeclaredAnnotations();
      if (anns != null) {
         this.extractAnnotations((MAnnotatedElement)dest, (Annotation[])anns);
      }
   }

   public void extractAnnotations(MClass dest, Class src) {
      Annotation[] anns = src.getDeclaredAnnotations();
      if (anns != null) {
         this.extractAnnotations((MAnnotatedElement)dest, (Annotation[])anns);
      }
   }

   public void extractAnnotations(MParameter dest, Method src, int paramNum) {
      Annotation[][] anns;
      try {
         anns = src.getParameterAnnotations();
      } catch (NullPointerException var6) {
         if (this.mContext.getLogger().isVerbose((Object)this)) {
            this.mContext.getLogger().verbose("ignoring unexpected error while calling Method.getParameterAnnotations():");
            this.mContext.getLogger().verbose((Throwable)var6);
         }

         return;
      }

      if (anns != null) {
         if (paramNum >= anns.length) {
            if (this.mContext.getLogger().isVerbose((Object)this)) {
               this.mContext.getLogger().warning("method " + src.getName() + " has fewer than expected parameter annotations ");
            }

         } else {
            this.extractAnnotations((MAnnotatedElement)dest, (Annotation[])anns[paramNum]);
         }
      }
   }

   public void extractAnnotations(MParameter dest, Constructor src, int paramNum) {
      Annotation[][] anns;
      try {
         anns = src.getParameterAnnotations();
      } catch (NullPointerException var6) {
         if (this.mContext.getLogger().isVerbose((Object)this)) {
            this.mContext.getLogger().verbose("ignoring unexpected error while calling Constructor.getParameterAnnotations():");
            this.mContext.getLogger().verbose((Throwable)var6);
         }

         return;
      }

      if (anns != null) {
         if (paramNum >= anns.length) {
            if (this.mContext.getLogger().isVerbose((Object)this)) {
               this.mContext.getLogger().warning("constructor " + src.getName() + " has fewer than expected parameter annotations ");
            }

         } else {
            this.extractAnnotations((MAnnotatedElement)dest, (Annotation[])anns[paramNum]);
         }
      }
   }

   public boolean isEnum(Class clazz) {
      return clazz.isEnum();
   }

   public Constructor getEnclosingConstructor(Class clazz) {
      return clazz.getEnclosingConstructor();
   }

   public Method getEnclosingMethod(Class clazz) {
      return clazz.getEnclosingMethod();
   }

   private void extractAnnotations(MAnnotatedElement dest, Annotation[] anns) {
      if (anns != null && anns.length != 0) {
         for(int i = 0; i < anns.length; ++i) {
            MAnnotation destAnn = dest.findOrCreateAnnotation(anns[i].annotationType().getName());
            destAnn.setAnnotationInstance(anns[i]);
            this.populateAnnotation(destAnn, anns[i]);
         }

      }
   }

   private void populateAnnotation(MAnnotation dest, Annotation src) {
      boolean isVerbose = this.mContext.getLogger().isVerbose((Object)this);
      if (src == null) {
         throw new IllegalArgumentException();
      } else {
         Class annType = src.annotationType();
         if (isVerbose) {
            this.mContext.getLogger().verbose("type is " + annType.getName());
         }

         Method[] methods = annType.getMethods();

         for(int i = 0; i < methods.length; ++i) {
            if (isVerbose) {
               this.mContext.getLogger().verbose("examining " + methods[i].toString());
            }

            int mods = methods[i].getModifiers();
            if (!Modifier.isStatic(mods) && Modifier.isPublic(mods) && methods[i].getParameterTypes().length <= 0) {
               Class c = methods[i].getDeclaringClass();
               if (!Object.class.equals(c) && !Annotation.class.equals(c)) {
                  try {
                     if (isVerbose) {
                        this.mContext.getLogger().verbose("invoking " + methods[i].getName() + "()");
                     }

                     Object value = methods[i].invoke(src, (Object[])null);
                     if (isVerbose) {
                        this.mContext.getLogger().verbose("value is " + value);
                     }

                     if (value instanceof Annotation) {
                        if (isVerbose) {
                           this.mContext.getLogger().verbose("it's nested!!  creating for " + methods[i].getName() + " and " + annType.getName());
                        }

                        MAnnotation nested = dest.createNestedValue(methods[i].getName(), annType.getName());
                        nested.setAnnotationInstance(value);
                        this.populateAnnotation(nested, (Annotation)value);
                     } else if (value instanceof Annotation[]) {
                        Annotation[] anns = (Annotation[])((Annotation[])value);
                        MAnnotation[] nested = dest.createNestedValueArray(methods[i].getName(), methods[i].getReturnType().getComponentType().getName(), anns.length);

                        for(int j = 0; j < anns.length; ++j) {
                           this.populateAnnotation(nested[j], anns[j]);
                        }
                     } else {
                        JClass type = this.mContext.getClassLoader().loadClass(methods[i].getReturnType().getName());
                        if (value instanceof Class) {
                           value = this.mContext.getClassLoader().loadClass(((Class)value).getName());
                        }

                        dest.setSimpleValue(methods[i].getName(), value, type);
                     }
                  } catch (IllegalAccessException var12) {
                     this.mContext.getLogger().warning((Throwable)var12);
                  } catch (InvocationTargetException var13) {
                     this.mContext.getLogger().warning((Throwable)var13);
                  }
               }
            }
         }

      }
   }
}

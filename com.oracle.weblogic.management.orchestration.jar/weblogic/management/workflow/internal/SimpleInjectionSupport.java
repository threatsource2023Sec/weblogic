package weblogic.management.workflow.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.NoSuchElementException;
import weblogic.management.workflow.StateInjectionException;

public abstract class SimpleInjectionSupport {
   public final void inject(Object target, Class annotation) throws StateInjectionException {
      this.inject(target, target.getClass(), annotation);
   }

   public final void inject(final Object target, Class targetClass, Class annotation) throws StateInjectionException {
      if (targetClass != null && !targetClass.equals(Object.class)) {
         Field[] fields = targetClass.getDeclaredFields();
         Field[] var5 = fields;
         int var6 = fields.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            final Field field = var5[var7];
            Annotation annot = field.getAnnotation(annotation);
            if (annot != null) {
               try {
                  final Object value = this.getValue(target, targetClass, annot, field.getName());
                  AccessController.doPrivileged(new PrivilegedAction() {
                     public Object run() {
                        if (!field.isAccessible()) {
                           field.setAccessible(true);
                        }

                        try {
                           field.set(target, value);
                           return null;
                        } catch (IllegalAccessException | IllegalArgumentException var2) {
                           throw new StateInjectionException(var2);
                        }
                     }
                  });
               } catch (NoSuchElementException var11) {
               }
            }
         }

         this.inject(target, targetClass.getSuperclass(), annotation);
      }
   }

   protected abstract Object getValue(Object var1, Class var2, Annotation var3, String var4) throws NoSuchElementException;
}

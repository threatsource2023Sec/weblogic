package weblogic.j2ee.dd.xml;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.jvnet.hk2.annotations.Contract;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.PropertyBean;

@Contract
public abstract class AnnotationProcessorService {
   private BaseJ2eeAnnotationProcessor processor;
   private HashMap annotationsByName;
   private Method getValue;
   private Method getNameFromBean;
   private Method getNameFromAnno;

   private Set getAnnotations(Class beanClass, Class annotationClass, Class annotationsClass) {
      Set set = null;
      Class superClass = beanClass.getSuperclass();
      if (superClass.equals(Object.class)) {
         set = new HashSet();
      } else {
         set = this.getAnnotations(superClass, annotationClass, annotationsClass);
      }

      Annotation def;
      if (annotationsClass != null && this.processor.isAnnotationPresent(beanClass, annotationsClass)) {
         def = this.processor.getAnnotation(beanClass, annotationsClass);
         Object[] var7 = this.getValue(def);
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Object def = var7[var9];
            this.validateAnnotation(def, beanClass);
            ((Set)set).add(def);
         }
      }

      if (this.processor.isAnnotationPresent(beanClass, annotationClass)) {
         def = this.processor.getAnnotation(beanClass, annotationClass);
         this.validateAnnotation(def, beanClass);
         ((Set)set).add(def);
      }

      return (Set)set;
   }

   private Object[] getValue(Object defs) {
      if (this.getValue == null) {
         try {
            this.getValue = defs.getClass().getDeclaredMethod("value");
         } catch (SecurityException | NoSuchMethodException var5) {
            throw new AssertionError("Unable to find the getValue method on class, " + defs.getClass(), var5);
         }
      }

      try {
         return (Object[])((Object[])this.getValue.invoke(defs));
      } catch (IllegalArgumentException | IllegalAccessException var3) {
         throw new AssertionError("Something is wrong with class, " + defs.getClass(), var3);
      } catch (InvocationTargetException var4) {
         throw new AssertionError("Unable to get the name from, " + defs, var4);
      }
   }

   protected abstract void validateAnnotation(Object var1, Class var2);

   protected void setUnsetAttribute(Object bean, boolean isNotInDescriptor, String name, Object value) {
      this.processor.setUnsetAttribute(bean, isNotInDescriptor, name, value);
   }

   protected void setAttribute(Object bean, String name, Object value, Class clz) {
      this.processor.setAttribute(bean, name, value, clz);
   }

   protected void setUnsetProperties(PropertyBean bean, boolean isNotInDescriptor, String[] properties) {
      this.processor.setUnsetProperties(bean, isNotInDescriptor, properties);
   }

   public void processAnnotations(Class beanClass, J2eeClientEnvironmentBean eg, BaseJ2eeAnnotationProcessor proc) {
      this.processor = proc;
      Iterator var4 = this.getAnnotations(beanClass, this.getAnnotationClass(), this.getAnnotationsClass()).iterator();

      while(var4.hasNext()) {
         Object annotation = var4.next();
         this.add(annotation, eg);
      }

   }

   protected abstract Class getAnnotationsClass();

   protected abstract Class getAnnotationClass();

   protected void add(Object annotation, J2eeClientEnvironmentBean eg) {
      String name = this.getNameFromAnno(annotation);
      Object bean = this.getBean(name, eg);
      boolean isNotInDescriptor = bean == null;
      if (bean == null) {
         bean = this.createBeanNamed(name, eg);
      }

      Object mergedAnnotation = this.getMergedAnnotation(name);
      this.setMergedAnnotation(name, this.processAnnotation(bean, annotation, mergedAnnotation, isNotInDescriptor));
   }

   private void setMergedAnnotation(String name, Object annotation) {
      if (this.annotationsByName == null) {
         this.annotationsByName = new HashMap();
      }

      this.annotationsByName.put(name, annotation);
   }

   protected abstract Object processAnnotation(Object var1, Object var2, Object var3, boolean var4) throws DuplicateAnnotationException;

   private Object getMergedAnnotation(String name) {
      return this.annotationsByName == null ? null : this.annotationsByName.get(name);
   }

   private Object getBean(String name, J2eeClientEnvironmentBean eg) {
      Object[] var3 = this.getBeans(eg);
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object bean = var3[var5];
         if (this.getNameFromBean(bean).equals(name)) {
            return bean;
         }
      }

      return null;
   }

   protected abstract Object createBeanNamed(String var1, J2eeClientEnvironmentBean var2);

   private String getNameFromBean(Object bean) {
      if (this.getNameFromBean == null) {
         try {
            this.getNameFromBean = bean.getClass().getDeclaredMethod("getName");
         } catch (SecurityException | NoSuchMethodException var5) {
            throw new AssertionError("Unable to find the getName method on class, " + bean.getClass(), var5);
         }
      }

      try {
         return (String)this.getNameFromBean.invoke(bean);
      } catch (IllegalArgumentException | IllegalAccessException var3) {
         throw new AssertionError("Something is wrong with class, " + bean.getClass(), var3);
      } catch (InvocationTargetException var4) {
         throw new AssertionError("Unable to get the name from, " + bean, var4);
      }
   }

   protected abstract Object[] getBeans(J2eeClientEnvironmentBean var1);

   private String getNameFromAnno(Object annotation) {
      if (this.getNameFromAnno == null) {
         try {
            this.getNameFromAnno = annotation.getClass().getDeclaredMethod("name");
         } catch (SecurityException | NoSuchMethodException var5) {
            throw new AssertionError("Unable to find the getName method on class, " + annotation.getClass(), var5);
         }
      }

      try {
         return (String)this.getNameFromAnno.invoke(annotation);
      } catch (IllegalArgumentException | IllegalAccessException var3) {
         throw new AssertionError("Something is wrong with class, " + annotation.getClass(), var3);
      } catch (InvocationTargetException var4) {
         throw new AssertionError("Unable to get the name from, " + annotation, var4);
      }
   }

   protected void addProcessingError(String message) {
      this.processor.addProcessingError(message);
   }
}

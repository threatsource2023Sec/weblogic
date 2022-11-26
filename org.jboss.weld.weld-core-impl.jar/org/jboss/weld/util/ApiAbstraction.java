package org.jboss.weld.util;

import java.lang.reflect.Field;
import org.jboss.weld.logging.UtilLogger;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;

public class ApiAbstraction {
   private static final Class DUMMY_ANNOTATION = DummyAnnotation.class;
   private static final Class DUMMY_CLASS = Dummy.class;
   private ResourceLoader resourceLoader;

   public ApiAbstraction(ResourceLoader resourceLoader) {
      this.resourceLoader = resourceLoader;
   }

   protected Class annotationTypeForName(String name) {
      try {
         return this.resourceLoader.classForName(name);
      } catch (ResourceLoadingException var3) {
         return DUMMY_ANNOTATION;
      }
   }

   protected Class classForName(String name) {
      try {
         return this.resourceLoader.classForName(name);
      } catch (ResourceLoadingException var3) {
         return DUMMY_CLASS;
      }
   }

   protected Object enumValue(Class clazz, String memberName) {
      Preconditions.checkArgumentNotNull(memberName, "memberName");
      if (!clazz.isEnum()) {
         throw UtilLogger.LOG.classNotEnum(clazz);
      } else {
         try {
            Field field = clazz.getField(memberName);
            return field.get((Object)null);
         } catch (SecurityException var4) {
            return null;
         } catch (NoSuchFieldException var5) {
            return null;
         } catch (IllegalArgumentException var6) {
            return null;
         } catch (IllegalAccessException var7) {
            return null;
         }
      }
   }

   public static enum DummyEnum {
      DUMMY_VALUE;
   }

   public interface Dummy {
   }

   public @interface DummyAnnotation {
   }
}

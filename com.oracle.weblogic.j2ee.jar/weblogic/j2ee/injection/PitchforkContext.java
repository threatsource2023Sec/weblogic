package weblogic.j2ee.injection;

import com.oracle.pitchfork.interfaces.PitchforkUtils;
import com.oracle.pitchfork.spi.PitchforkUtilsImpl;
import weblogic.utils.AssertionError;

public class PitchforkContext {
   private static final String SPRING_COMPONENT_FACTORY_CLASS_NAME = "com.oracle.pitchfork.interfaces.SpringComponentFactory";
   private static final String OLD_SPRING_COMPONENT_FACTORY_CLASS_NAME = "org.springframework.jee.interfaces.SpringComponentFactory";
   private PitchforkUtils pUtils;
   private final boolean isSpringComponentFactoryClassName;
   private final String componentFactoryClassName;

   public PitchforkContext(String componentFactoryClassName) {
      this.componentFactoryClassName = componentFactoryClassName;
      this.isSpringComponentFactoryClassName = "com.oracle.pitchfork.interfaces.SpringComponentFactory".equalsIgnoreCase(componentFactoryClassName) || "org.springframework.jee.interfaces.SpringComponentFactory".equalsIgnoreCase(componentFactoryClassName);
   }

   public boolean isSpringComponentFactoryClassName() {
      return this.isSpringComponentFactoryClassName;
   }

   public String getComponentFactoryClassName() {
      return this.componentFactoryClassName;
   }

   public static String getSynthesizedComponentFactoryClassName(String compFactoryClassName) {
      return compFactoryClassName != null && !"com.oracle.pitchfork.interfaces.SpringComponentFactory".equalsIgnoreCase(compFactoryClassName) && !"org.springframework.jee.interfaces.SpringComponentFactory".equalsIgnoreCase(compFactoryClassName) ? compFactoryClassName : null;
   }

   public PitchforkUtils getPitchforkUtils() {
      if (this.pUtils != null) {
         return this.pUtils;
      } else {
         try {
            this.pUtils = (PitchforkUtils)this.getTargetClass().newInstance();
            return this.pUtils;
         } catch (Exception var2) {
            throw new AssertionError("Unable to create PitchforkUtils implementation", var2);
         }
      }
   }

   private Class getTargetClass() throws ClassNotFoundException {
      if (this.componentFactoryClassName == null) {
         return PitchforkUtilsImpl.class;
      } else {
         String targetClassName = this.isSpringComponentFactoryClassName() ? "org.springframework.jee.spi.PitchforkUtilsImpl" : this.componentFactoryClassName;

         try {
            return Class.forName(targetClassName);
         } catch (ClassNotFoundException var4) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl == null) {
               throw var4;
            } else {
               return cl.loadClass(targetClassName);
            }
         }
      }
   }
}

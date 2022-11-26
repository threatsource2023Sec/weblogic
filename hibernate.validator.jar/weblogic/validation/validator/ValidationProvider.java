package weblogic.validation.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.validation.Configuration;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import org.hibernate.validator.HibernateValidatorConfiguration;
import weblogic.validation.ValidationContextManager;

public class ValidationProvider implements javax.validation.spi.ValidationProvider {
   Class hvc;
   Object hv;

   public ValidationProvider() {
      Object e;
      try {
         this.hvc = Thread.currentThread().getContextClassLoader().loadClass("org.hibernate.validator.HibernateValidator");
         this.hv = this.hvc.newInstance();
         return;
      } catch (InstantiationException var3) {
         e = var3;
      } catch (IllegalAccessException var4) {
         e = var4;
      } catch (ClassNotFoundException var5) {
         e = var5;
      }

      throw new RuntimeException("Could not instantiate a HibernateValidator", (Throwable)e);
   }

   public Configuration createGenericConfiguration(BootstrapState state) {
      this.registerVCClassFinder();
      return (Configuration)this.invoke("createGenericConfiguration", BootstrapState.class, state);
   }

   public HibernateValidatorConfiguration createSpecializedConfiguration(BootstrapState state) {
      this.registerVCClassFinder();
      return (HibernateValidatorConfiguration)this.invoke("createSpecializedConfiguration", BootstrapState.class, state);
   }

   public ValidatorFactory buildValidatorFactory(ConfigurationState state) {
      return (ValidatorFactory)this.invoke("buildValidatorFactory", ConfigurationState.class, state);
   }

   private Object invoke(String name, Class paramClass, Object state) {
      Object e;
      try {
         Method m = this.hvc.getDeclaredMethod(name, paramClass);
         return m.invoke(this.hv, state);
      } catch (NoSuchMethodException var7) {
         e = var7;
      } catch (SecurityException var8) {
         e = var8;
      } catch (InvocationTargetException var9) {
         e = var9;
      } catch (IllegalAccessException var10) {
         e = var10;
      }

      throw new RuntimeException((Throwable)e);
   }

   private void registerVCClassFinder() {
      try {
         ValidationContextManager.getInstance().registerVCClassFinder();
      } catch (NoClassDefFoundError var2) {
         if (!var2.getMessage().contains("weblogic")) {
            throw var2;
         }
      }

   }
}

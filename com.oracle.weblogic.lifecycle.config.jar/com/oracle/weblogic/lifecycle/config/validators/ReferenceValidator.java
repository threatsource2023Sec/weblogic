package com.oracle.weblogic.lifecycle.config.validators;

import com.oracle.weblogic.lifecycle.config.Environment;
import com.oracle.weblogic.lifecycle.config.Environments;
import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import com.oracle.weblogic.lifecycle.config.Partition;
import com.oracle.weblogic.lifecycle.config.Runtime;
import com.oracle.weblogic.lifecycle.config.Runtimes;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.UnexpectedTypeException;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.server.GlobalServiceLocator;

public class ReferenceValidator implements ConstraintValidator {
   private static ServiceLocator locator;
   private Class type;

   public ReferenceValidator() {
      setLocatorIfNotSet(GlobalServiceLocator.getServiceLocator());
   }

   public static synchronized void setLocator(ServiceLocator paramLocator) {
      locator = paramLocator;
   }

   private static synchronized void setLocatorIfNotSet(ServiceLocator paramLocator) {
      if (locator == null) {
         setLocator(paramLocator);
      }
   }

   public void initialize(ReferenceConstraint rc) {
      this.type = rc.type();
   }

   public boolean isValid(String config, ConstraintValidatorContext cvc) throws UnexpectedTypeException {
      if (config == null) {
         return true;
      } else {
         LifecycleConfig lifecycleConfig = (LifecycleConfig)locator.getService(LifecycleConfig.class, new Annotation[0]);
         if (lifecycleConfig == null) {
            return true;
         } else if (Environment.class.equals(this.type)) {
            Environments environments = lifecycleConfig.getEnvironments();
            Environment env = environments.lookupEnvironment(config);
            return env != null;
         } else {
            Runtimes runtimes;
            if (!Partition.class.equals(this.type)) {
               if (Runtime.class.equals(this.type)) {
                  runtimes = lifecycleConfig.getRuntimes();
                  Runtime runtime = runtimes.lookupRuntime(config);
                  return runtime != null;
               } else {
                  return false;
               }
            } else {
               runtimes = lifecycleConfig.getRuntimes();
               boolean found = false;
               Iterator var6 = runtimes.getRuntimes().iterator();

               while(var6.hasNext()) {
                  Runtime runtime = (Runtime)var6.next();
                  Partition partition = runtime.lookupPartition(config);
                  if (partition != null) {
                     found = true;
                     break;
                  }
               }

               return found;
            }
         }
      }
   }

   public String toString() {
      return "ReferenceValidator(" + locator + "," + this.type + "," + System.identityHashCode(this) + ")";
   }
}

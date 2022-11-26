package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Environment;
import com.oracle.weblogic.lifecycle.config.PartitionRef;
import com.oracle.weblogic.lifecycle.config.Runtime;
import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;

@Singleton
public class PartitionRefCustomizer {
   @Inject
   private ServiceLocator locator;

   public Runtime getRuntime(PartitionRef partitionRef) {
      String refName = partitionRef.getRuntimeRef();
      if (refName == null) {
         throw new IllegalStateException("PartitionRef with id " + partitionRef.getId() + " has no runtime-ref");
      } else {
         return (Runtime)this.locator.getService(Runtime.class, refName, new Annotation[0]);
      }
   }

   public Environment getEnvironment(PartitionRef partitionRef) {
      XmlHk2ConfigurationBean bean = (XmlHk2ConfigurationBean)partitionRef;
      return (Environment)bean._getParent();
   }
}

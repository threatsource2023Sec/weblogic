package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Runtime;
import com.oracle.weblogic.lifecycle.config.UncommittedAssociation;
import com.oracle.weblogic.lifecycle.config.UncommittedEnvironment;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class UncommittedEnvironmentCustomizer {
   @Inject
   private XmlService xmlService;
   @Inject
   private ServiceLocator locator;

   public UncommittedAssociation createAssociation(UncommittedEnvironment environment, String partition1Name, String runtime1Name, String partition2Name, String runtime2Name) {
      this.validateRuntimes(environment, runtime1Name, runtime2Name);
      validateAssociation(environment, partition1Name, partition2Name);
      UncommittedAssociation association = (UncommittedAssociation)this.xmlService.createBean(UncommittedAssociation.class);
      association.setPartition1Name(partition1Name);
      association.setPartition2Name(partition2Name);
      association.setRuntime1Name(runtime1Name);
      association.setRuntime2Name(runtime2Name);
      return environment.addUncommittedAssociation(association);
   }

   public boolean isDeleteType(UncommittedEnvironment environment) {
      return "delete".equals(environment.getType());
   }

   private void validateRuntimes(UncommittedEnvironment environment, String runtime1Name, String runtime2Name) {
      Runtime runtime1 = this.getRuntime(environment, runtime1Name);
      if (runtime1 == null) {
         throw new RuntimeException("Invalid runtime " + runtime1Name);
      } else {
         Runtime runtime2 = this.getRuntime(environment, runtime2Name);
         if (runtime2 == null) {
            throw new RuntimeException("Invalid runtime " + runtime2Name);
         } else if (runtime1.getType() != null && runtime1.getType().equals(runtime2.getType())) {
            throw new RuntimeException("Both runtimes are of the same type");
         }
      }
   }

   private Runtime getRuntime(UncommittedEnvironment environment, String runtimeName) {
      return (Runtime)this.locator.getService(Runtime.class, runtimeName, new Annotation[0]);
   }

   private static void validateAssociation(UncommittedEnvironment environment, String partition1Name, String partition2Name) {
      List associations = environment.getUncommittedAssociations();
      Iterator var4 = associations.iterator();

      UncommittedAssociation association;
      do {
         if (!var4.hasNext()) {
            return;
         }

         association = (UncommittedAssociation)var4.next();
      } while((!partition1Name.equals(association.getPartition1Name()) || !partition2Name.equals(association.getPartition2Name())) && (!partition1Name.equals(association.getPartition2Name()) || !partition2Name.equals(association.getPartition1Name())));

      throw new RuntimeException("Partitions are already associated in the environment");
   }
}

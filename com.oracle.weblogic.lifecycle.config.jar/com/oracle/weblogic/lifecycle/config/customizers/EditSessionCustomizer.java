package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.EditSession;
import com.oracle.weblogic.lifecycle.config.Environments;
import com.oracle.weblogic.lifecycle.config.Runtime;
import com.oracle.weblogic.lifecycle.config.Runtimes;
import com.oracle.weblogic.lifecycle.config.UncommittedEnvironment;
import com.oracle.weblogic.lifecycle.config.UncommittedPartition;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class EditSessionCustomizer {
   @Inject
   private XmlService xmlService;
   @Inject
   private ServiceLocator locator;

   public UncommittedEnvironment createEnvironment(EditSession me, String enviornmentName) {
      this.validateEnvironment(me, enviornmentName);
      UncommittedEnvironment environment = (UncommittedEnvironment)this.xmlService.createBean(UncommittedEnvironment.class);
      environment.setName(enviornmentName);
      environment.setType("create");
      return me.addUncommittedEnvironment(environment);
   }

   public UncommittedEnvironment deleteEnvironment(EditSession me, String environmentName) {
      this.validateEnvironment(me, environmentName);
      UncommittedEnvironment killMe = this.getEnvironmentByName(me, environmentName);
      if (killMe == null) {
         killMe = (UncommittedEnvironment)this.xmlService.createBean(UncommittedEnvironment.class);
         killMe.setName(environmentName);
         killMe.setType("delete");
         return me.addUncommittedEnvironment(killMe);
      } else {
         killMe.setType("delete");
         return killMe;
      }
   }

   public UncommittedEnvironment getEnvironmentByName(EditSession me, String environmentName) {
      if (environmentName == null) {
         return null;
      } else {
         Iterator var3 = me.getUncommittedEnvironments().iterator();

         UncommittedEnvironment candidate;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            candidate = (UncommittedEnvironment)var3.next();
         } while(!environmentName.equals(candidate.getName()));

         return candidate;
      }
   }

   public UncommittedPartition createPartition(EditSession me, String partitionName, String runtimeName) {
      this.validatePartition(me, partitionName, runtimeName);
      UncommittedPartition partition = (UncommittedPartition)this.xmlService.createBean(UncommittedPartition.class);
      partition.setName(partitionName);
      partition.setRuntimeName(runtimeName);
      return me.addPartition(partition);
   }

   public UncommittedPartition getPartitionByName(EditSession me, String partitionName) {
      return null;
   }

   private void validateEnvironment(EditSession editSession, String name) {
      Environments environments = (Environments)this.locator.getService(Environments.class, new Annotation[0]);
      if (environments.lookupEnvironment(name) != null) {
         throw new RuntimeException("Environment " + name + " already exists");
      }
   }

   private void validatePartition(EditSession editSession, String partitionName, String runtimeName) {
      UncommittedPartition partition = editSession.getPartitionByName(partitionName);
      if (partition != null) {
         throw new RuntimeException("Uncommitted partition already exists " + partitionName);
      } else {
         Runtimes runtimes = (Runtimes)this.locator.getService(Runtimes.class, new Annotation[0]);
         Runtime runtime = runtimes.getRuntimeByName(runtimeName);
         if (runtime == null) {
            throw new RuntimeException("Runtime does not exist " + runtimeName);
         }
      }
   }
}

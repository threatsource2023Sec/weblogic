package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Associations;
import com.oracle.weblogic.lifecycle.config.Environment;
import com.oracle.weblogic.lifecycle.config.Environments;
import com.oracle.weblogic.lifecycle.config.Partition;
import com.oracle.weblogic.lifecycle.config.PartitionRef;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class EnvironmentsCustomizer {
   @Inject
   private XmlService xmlService;

   public Environment getOrCreateEnvironment(Environments env, String name) {
      Environment old = env.lookupEnvironment(name);
      return old != null ? old : this.createEnvironment(env, name);
   }

   public Environment createEnvironment(Environments env, String name) {
      Environment foundEnv = env.lookupEnvironment(name);
      if (foundEnv != null) {
         throw new IllegalStateException("There is already an environment with name " + name);
      } else {
         Environment newEnv = (Environment)this.xmlService.createBean(Environment.class);
         newEnv.setName(name);
         Associations associations = (Associations)this.xmlService.createBean(Associations.class);
         newEnv.setAssociations(associations);
         return env.addEnvironment(newEnv);
      }
   }

   public Environment getEnvironmentByName(Environments env, String name) {
      return env.lookupEnvironment(name);
   }

   public Environment deleteEnvironment(Environments env, Environment environment) {
      return environment == null ? null : env.removeEnvironment(environment.getName());
   }

   public PartitionRef getPartitionRef(Environments env, Partition partition) {
      List environmentConfigs = env.getEnvironments();
      Iterator var4 = environmentConfigs.iterator();

      PartitionRef partitionRef;
      do {
         if (!var4.hasNext()) {
            return null;
         }

         Environment environment = (Environment)var4.next();
         partitionRef = environment.getPartitionRefById(partition.getId());
      } while(partitionRef == null);

      return partitionRef;
   }

   public Environment getReferencedEnvironment(Environments env, Partition partition) {
      PartitionRef partitionRef = env.getPartitionRef(partition);
      if (partitionRef == null) {
         return null;
      } else {
         XmlHk2ConfigurationBean asBean = (XmlHk2ConfigurationBean)partitionRef;
         return (Environment)asBean._getParent();
      }
   }
}

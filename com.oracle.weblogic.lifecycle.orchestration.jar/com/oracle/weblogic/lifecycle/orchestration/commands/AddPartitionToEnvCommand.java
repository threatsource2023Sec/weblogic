package com.oracle.weblogic.lifecycle.orchestration.commands;

import com.oracle.weblogic.lifecycle.Environment;
import com.oracle.weblogic.lifecycle.LifecycleManager;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.server.GlobalServiceLocator;

public class AddPartitionToEnvCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 1L;
   private static Logger logger = Logger.getLogger("com.oracle.weblogic.lifecycle.orchestration");
   @SharedState
   public String runtimeName;
   @SharedState
   public String partitionName;
   @SharedState
   public String environmentName;
   private transient ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
   private transient RuntimeManager runtimeManager;
   private transient LifecycleManager lifecycleManager;

   public AddPartitionToEnvCommand() {
      this.runtimeManager = (RuntimeManager)this.serviceLocator.getService(RuntimeManager.class, new Annotation[0]);
      this.lifecycleManager = (LifecycleManager)this.serviceLocator.getService(LifecycleManager.class, new Annotation[0]);
   }

   public boolean revert() throws Exception {
      LifecycleRuntime runtime = this.runtimeManager.getRuntime(this.runtimeName);
      if (runtime == null) {
         logger.log(Level.SEVERE, "Runtime {0} does not exist", this.runtimeName);
         return false;
      } else {
         LifecyclePartition partition = runtime.getPartition(this.partitionName);
         if (partition == null) {
            logger.log(Level.SEVERE, "Partition {0} does not exist on runtime {1}", new Object[]{this.partitionName, this.runtimeName});
            return false;
         } else {
            Environment environment = this.lifecycleManager.getEnvironment(this.environmentName);
            if (environment == null) {
               logger.log(Level.SEVERE, "Environment {0} does not exist", this.environmentName);
               return false;
            } else {
               logger.log(Level.INFO, "Removing partition {0} from environment {1}", new Object[]{this.partitionName, this.environmentName});
               environment.removePartition(partition.getPartitionType(), this.partitionName);
               return true;
            }
         }
      }
   }

   public void initialize(WorkflowContext wc) {
   }

   public boolean execute() throws Exception {
      LifecycleRuntime runtime = this.runtimeManager.getRuntime(this.runtimeName);
      if (runtime == null) {
         logger.log(Level.SEVERE, "Runtime {0} does not exist", this.runtimeName);
         return false;
      } else {
         LifecyclePartition partition = runtime.getPartition(this.partitionName);
         if (partition == null) {
            logger.log(Level.SEVERE, "Partition {0} does not exist on runtime {1}", new Object[]{this.partitionName, this.runtimeName});
            return false;
         } else {
            Environment environment = this.lifecycleManager.getEnvironment(this.environmentName);
            if (environment == null) {
               logger.log(Level.SEVERE, "Environment {0} does not exist", this.environmentName);
               return false;
            } else {
               logger.log(Level.INFO, "Add partition {0} to environment {1}", new Object[]{this.partitionName, this.environmentName});
               environment.addPartition(partition);
               return true;
            }
         }
      }
   }
}

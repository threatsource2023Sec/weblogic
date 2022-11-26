package com.oracle.weblogic.lifecycle.orchestration.commands;

import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.server.GlobalServiceLocator;

public class DeletePartitionCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 1L;
   private static Logger logger = Logger.getLogger("com.oracle.weblogic.lifecycle.orchestration");
   @SharedState
   public String partitionName;
   @SharedState
   public String runtimeName;
   private transient ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
   private transient RuntimeManager runtimeManager;
   public Map partitionProperties;

   public DeletePartitionCommand() {
      this.runtimeManager = (RuntimeManager)this.serviceLocator.getService(RuntimeManager.class, new Annotation[0]);
   }

   public boolean revert() throws Exception {
      LifecycleRuntime runtime = this.runtimeManager.getRuntime(this.runtimeName);
      if (runtime == null) {
         logger.log(Level.SEVERE, "runtime {0} does not exist", this.runtimeName);
         return false;
      } else {
         LifecyclePartition existingPartition = runtime.getPartition(this.partitionName);
         if (existingPartition == null) {
            logger.log(Level.INFO, "Create partition {0}", this.partitionName);
            runtime.createPartition(this.partitionName, this.partitionProperties);
         }

         return true;
      }
   }

   public void initialize(WorkflowContext wc) {
   }

   public boolean execute() throws Exception {
      LifecycleRuntime runtime = this.runtimeManager.getRuntime(this.runtimeName);
      if (runtime == null) {
         logger.log(Level.SEVERE, "runtime {0} does not exist", this.runtimeName);
         return false;
      } else {
         LifecyclePartition partition = runtime.getPartition(this.partitionName);
         if (partition != null) {
            this.partitionProperties = partition.getPartitionProperties();
         }

         logger.log(Level.INFO, "Delete partition {0}", this.partitionName);
         runtime.deletePartition(this.partitionName, this.partitionProperties);
         return true;
      }
   }
}

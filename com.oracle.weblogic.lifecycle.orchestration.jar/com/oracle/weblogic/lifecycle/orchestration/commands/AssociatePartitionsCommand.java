package com.oracle.weblogic.lifecycle.orchestration.commands;

import com.oracle.weblogic.lifecycle.Environment;
import com.oracle.weblogic.lifecycle.LifecycleManager;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.server.GlobalServiceLocator;

public class AssociatePartitionsCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 1L;
   private static Logger logger = Logger.getLogger("com.oracle.weblogic.lifecycle.orchestration");
   @SharedState
   public String partition1;
   @SharedState
   public String runtime1;
   @SharedState
   public String partition2;
   @SharedState
   public String runtime2;
   @SharedState
   public String environmentName;
   @SharedState
   public HashMap associateProperties;
   private transient ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
   private transient RuntimeManager runtimeManager;
   private transient LifecycleManager lifecycleManager;

   public AssociatePartitionsCommand() {
      this.runtimeManager = (RuntimeManager)this.serviceLocator.getService(RuntimeManager.class, new Annotation[0]);
      this.lifecycleManager = (LifecycleManager)this.serviceLocator.getService(LifecycleManager.class, new Annotation[0]);
   }

   public boolean revert() throws Exception {
      LifecycleRuntime lifecycleRuntime1 = this.runtimeManager.getRuntime(this.runtime1);
      if (lifecycleRuntime1 == null) {
         logger.log(Level.SEVERE, "runtime1 {0} does not exist", this.runtime1);
         return false;
      } else {
         LifecycleRuntime lifecycleRuntime2 = this.runtimeManager.getRuntime(this.runtime2);
         if (lifecycleRuntime2 == null) {
            logger.log(Level.SEVERE, "runtime2 {0} does not exist", this.runtime2);
            return false;
         } else {
            LifecyclePartition lifecyclePartition1 = lifecycleRuntime1.getPartition(this.partition1);
            if (lifecyclePartition1 == null) {
               logger.log(Level.SEVERE, "partition1 {0} does not exist", this.partition1);
               return false;
            } else {
               LifecyclePartition lifecyclePartition2 = lifecycleRuntime2.getPartition(this.partition2);
               if (lifecyclePartition2 == null) {
                  logger.log(Level.SEVERE, "partition2 {0} does not exist", this.partition2);
                  return false;
               } else {
                  Environment environment = this.lifecycleManager.getEnvironment(this.environmentName);
                  if (environment == null) {
                     logger.log(Level.SEVERE, "environment {0} does not exist", this.environmentName);
                     return false;
                  } else {
                     logger.log(Level.INFO, "Disassociate partition1 {0} from partition2 {1}", new Object[]{this.partition1, this.partition2});
                     environment.dissociate(lifecyclePartition1, lifecyclePartition2, this.associateProperties);
                     return true;
                  }
               }
            }
         }
      }
   }

   public void initialize(WorkflowContext wc) {
   }

   public boolean execute() throws Exception {
      LifecycleRuntime lifecycleRuntime1 = this.runtimeManager.getRuntime(this.runtime1);
      if (lifecycleRuntime1 == null) {
         logger.log(Level.SEVERE, "runtime1 {0} does not exist", this.runtime1);
         return false;
      } else {
         LifecycleRuntime lifecycleRuntime2 = this.runtimeManager.getRuntime(this.runtime2);
         if (lifecycleRuntime2 == null) {
            logger.log(Level.SEVERE, "runtime2 {0} does not exist", this.runtime2);
            return false;
         } else {
            LifecyclePartition lifecyclePartition1 = lifecycleRuntime1.getPartition(this.partition1);
            if (lifecyclePartition1 == null) {
               logger.log(Level.SEVERE, "partition1 {0} does not exist", this.partition1);
               return false;
            } else {
               LifecyclePartition lifecyclePartition2 = lifecycleRuntime2.getPartition(this.partition2);
               if (lifecyclePartition2 == null) {
                  logger.log(Level.SEVERE, "partition2 {0} does not exist", this.partition2);
                  return false;
               } else {
                  Environment environment = this.lifecycleManager.getEnvironment(this.environmentName);
                  if (environment == null) {
                     logger.log(Level.SEVERE, "environment {0} does not exist", this.environmentName);
                     return false;
                  } else {
                     logger.log(Level.INFO, "Associate partition1 {0} to partition2 {1}. Partition1 properties {2}", new Object[]{this.partition1, this.partition2, this.associateProperties});
                     environment.associate(lifecyclePartition1, lifecyclePartition2, this.associateProperties);
                     return true;
                  }
               }
            }
         }
      }
   }
}

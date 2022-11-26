package com.oracle.weblogic.lifecycle.orchestration.commands;

import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Objects;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.workflow.MutableString;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.server.GlobalServiceLocator;

public class CreatePartitionCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 1L;
   private static DebugLogger debugLogger;
   private transient ServiceLocator serviceLocator;
   private transient RuntimeManager runtimeManager;
   @SharedState
   public MutableString partitionId;
   @SharedState
   public String partitionName;
   @SharedState
   public HashMap partitionProperties;
   @SharedState
   public String runtimeName;

   public CreatePartitionCommand() {
      this.initialize();
   }

   public boolean revert() throws Exception {
      if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug("CreatePartitionCommand revert()");
         debugLogger.debug("Runtime: " + this.runtimeName + ", Partition: " + this.partitionName + ", Partition Properties: " + Objects.toString(this.partitionProperties));
      }

      LifecycleRuntime runtime = this.runtimeManager.getRuntime(this.runtimeName);
      if (runtime != null) {
         if (debugLogger != null && debugLogger.isDebugEnabled()) {
            debugLogger.debug("Runtime Type: " + runtime.getRuntimeType());
         }

         LifecyclePartition partition = runtime.getPartition(this.partitionName);
         if (partition != null) {
            runtime.deletePartition(this.partitionName, this.partitionProperties);
            if (debugLogger != null && debugLogger.isDebugEnabled()) {
               debugLogger.debug("Partition deleted for " + this.partitionName + " on Runtime named " + this.runtimeName);
            }
         } else if (debugLogger != null && debugLogger.isDebugEnabled()) {
            debugLogger.debug("Partition " + this.partitionName + " does not exist on Runtime named " + this.runtimeName);
         }
      } else if (debugLogger != null && debugLogger.isDebugEnabled()) {
         debugLogger.debug("Runtime " + this.runtimeName + " does not exist in Lifecycle Config");
      }

      return true;
   }

   public void initialize(WorkflowContext wc) {
      Objects.requireNonNull(this.runtimeName);
      Objects.requireNonNull(this.partitionName);
   }

   public boolean execute() throws Exception {
      if (this.partitionId == null) {
         throw new IllegalStateException("SharedState partitionId has not been initialized");
      } else {
         if (debugLogger != null && debugLogger.isDebugEnabled()) {
            debugLogger.debug("CreatePartitionCommand execute()");
            debugLogger.debug("Runtime: " + this.runtimeName + ", Partition: " + this.partitionName + ", Partition Properties: " + Objects.toString(this.partitionProperties));
         }

         LifecycleRuntime runtime = this.runtimeManager.getRuntime(this.runtimeName);
         if (runtime != null) {
            if (debugLogger != null && debugLogger.isDebugEnabled()) {
               debugLogger.debug("Runtime Type: " + runtime.getRuntimeType());
            }

            LifecyclePartition lp = runtime.createPartition(this.partitionName, this.partitionProperties);
            if (lp == null) {
               throw new LifecycleException("CreatePartitionCommand failed: Partition not created for " + this.partitionName + " on Runtime named " + this.runtimeName);
            } else {
               this.partitionId.setValue(lp.getId());
               if (debugLogger != null && debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Partition created: name = " + this.partitionName + ", id = " + this.partitionId + " type = " + lp.getPartitionType());
               }

               return true;
            }
         } else {
            throw new LifecycleException("Could not create Partition " + this.partitionName + " on Runtime named " + this.runtimeName + ". Runtime is not registered in Lifecycle Manager.");
         }
      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      if (in != null) {
         in.defaultReadObject();
      }

      this.initialize();
   }

   private final void initialize() {
      debugLogger = DebugLogger.getDebugLogger("DebugLifecycleManager");
      this.serviceLocator = GlobalServiceLocator.getServiceLocator();
      Objects.requireNonNull(this.serviceLocator);
      this.runtimeManager = (RuntimeManager)this.serviceLocator.getService(RuntimeManager.class, new Annotation[0]);
      Objects.requireNonNull(this.runtimeManager);
   }
}

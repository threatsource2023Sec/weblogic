package com.oracle.weblogic.lifecycle.orchestration.commands;

import com.oracle.weblogic.lifecycle.LifecycleManager;
import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.management.workflow.command.CommandRevertInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.server.GlobalServiceLocator;

public class DeleteEnvironmentCommand implements CommandRevertInterface {
   private static final long serialVersionUID = 1L;
   private static Logger logger = Logger.getLogger("com.oracle.weblogic.lifecycle.orchestration");
   private transient ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
   private transient LifecycleManager lifecycleManager;
   @SharedState
   public String environmentName;

   public DeleteEnvironmentCommand() {
      this.lifecycleManager = (LifecycleManager)this.serviceLocator.getService(LifecycleManager.class, new Annotation[0]);
   }

   public boolean revert() throws Exception {
      logger.log(Level.INFO, "Create environment {0}", this.environmentName);
      this.lifecycleManager.createEnvironment(this.environmentName);
      return true;
   }

   public void initialize(WorkflowContext wc) {
   }

   public boolean execute() throws Exception {
      logger.log(Level.INFO, "Delete environment {0}", this.environmentName);
      this.lifecycleManager.deleteEnvironment(this.environmentName);
      return true;
   }
}

package weblogic.deploy.api.tools.deployer;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import weblogic.deploy.api.internal.utils.JMXDeployerHelper;
import weblogic.deploy.internal.DeployerTextFormatter;
import weblogic.deploy.utils.MBeanHomeTool;
import weblogic.management.ApplicationException;
import weblogic.management.DeploymentNotification;
import weblogic.management.ManagementException;
import weblogic.management.deploy.TargetStatus;
import weblogic.management.runtime.DeploymentTaskRuntimeMBean;
import weblogic.utils.Getopt2;
import weblogic.utils.StackTraceUtils;

public abstract class Operation {
   public transient MBeanHomeTool tool;
   public transient Options options;
   public static transient DeployerTextFormatter cat;
   public transient DeploymentTaskRuntimeMBean task;
   public transient String id;
   public transient int failures;
   public transient StringBuffer outstr;
   public transient JMXDeployerHelper helper;
   public transient HashSet allowedOptions;
   public transient PrintStream out;

   protected Operation(MBeanHomeTool tool, Options options) {
      this(options);
      this.tool = tool;
      this.setAllowedOptions();
   }

   protected Operation(Options options) {
      this();
      this.options = options;
   }

   protected Operation() {
      this.task = null;
      this.out = System.out;
      this.init();
   }

   protected void init() {
      cat = new DeployerTextFormatter();
      this.allowedOptions = new HashSet();
   }

   public void setOptions(Options opts) {
      this.options = opts;
   }

   public abstract void setAllowedOptions() throws IllegalArgumentException;

   public void validate() throws IllegalArgumentException, DeployerException {
      HashSet badOpts = (HashSet)Options.allOptions.clone();
      badOpts.removeAll(this.allowedOptions);
      Iterator o = badOpts.iterator();
      Getopt2 opts = this.options.getOpts();

      String opt;
      do {
         if (!o.hasNext()) {
            return;
         }

         opt = (String)o.next();
      } while(!opts.hasOption(opt));

      throw new IllegalArgumentException(cat.errorOptionNotAllowed(opt, this.getOperation()));
   }

   public abstract void connect() throws DeployerException;

   public abstract void prepare() throws DeployerException;

   public abstract void execute() throws Exception;

   public abstract int report() throws DeployerException;

   public void debug(String msg) {
      if (this.options.debug) {
         this.println(msg);
      }

   }

   public void inform(String msg) {
      if (this.options.verbose) {
         this.println(msg);
      }

   }

   private int showRawTaskInfo(DeploymentTaskRuntimeMBean dtr) {
      int failures = 0;
      this.outstr = new StringBuffer();
      String taskStatus = this.getTaskStatus(dtr);
      this.outstr.append(cat.allTaskStatus(dtr.getId(), taskStatus, dtr.getDescription()));
      this.outstr.append("\n");
      TargetStatus[] targets = dtr.getTargets();
      if (targets == null) {
         return failures;
      } else {
         for(int i = 0; i < targets.length; ++i) {
            TargetStatus target = targets[i];
            if (target.getState() == 2) {
               ++failures;
            }

            String targetType = this.getTargetType(target.getTargetType());
            String state = this.getTargetState(target.getState());
            this.outstr.append(cat.showTargetState(targetType, target.getTarget(), state, this.getOperation()));
            this.outstr.append("\n");
            Exception[] msgs = target.getMessages();

            for(int j = 0; j < msgs.length; ++j) {
               this.outstr.append(this.translateDeploymentMessage(msgs[j]));
            }
         }

         if (failures == 0) {
            String errorMessage = this.getDeploymentTaskErrorMessage(dtr);
            if (errorMessage != null) {
               this.outstr.append(errorMessage);
               failures = 1;
            }
         }

         this.outstr.append("\n");
         return failures;
      }
   }

   public int showTaskInformation(DeploymentTaskRuntimeMBean dtr) {
      int failureCount;
      if (!this.options.formatted) {
         failureCount = this.showRawTaskInfo(dtr);
         this.print(this.outstr.toString());
         return failureCount;
      } else {
         failureCount = 0;
         String taskId = dtr.getId();
         String taskString = this.getOperation();
         String applicationSource = dtr.getSource();
         String applicationName = dtr.getApplicationName();
         TargetStatus[] targets = dtr.getTargets();
         int taskState = dtr.getState();
         if (targets.length == 0) {
            if (taskState != 2 && taskState != 4) {
               if (taskState == 3) {
                  Exception ex = dtr.getError();
                  String message = cat.noMessage();
                  if (ex != null) {
                     message = ex.getMessage();
                  }

                  cat.errorNoRealTargets(message);
                  return 1;
               } else {
                  cat.messageNoTargetsRunning();
                  return 0;
               }
            } else {
               cat.messageNoRealTargets();
               return 0;
            }
         } else {
            for(int t = 0; t < targets.length; ++t) {
               TargetStatus status = targets[t];
               String targetName = status.getTarget();
               int targetType = status.getType();
               int statusValue = status.getState();
               String serverOrCluster = this.translateTargetType(targetType);
               String statusString = this.translateStatus(statusValue);
               this.print(taskId + "\t" + taskString + "\t" + statusString + "\t" + targetName + "\t" + serverOrCluster + "\t" + applicationName + "\t" + applicationSource + "\t");
               if (statusValue == 2) {
                  ++failureCount;
               }

               Exception[] msgs = status.getMessages();

               for(int m = 0; m < msgs.length && msgs[m] != null; ++m) {
                  this.print(this.translateDeploymentMessage(msgs[m]));
               }

               this.println("");
            }

            if (failureCount == 0) {
               String errorMessage = this.getDeploymentTaskErrorMessage(dtr);
               if (errorMessage != null) {
                  this.println("");
                  this.println(errorMessage);
                  failureCount = 1;
               }
            }

            return failureCount;
         }
      }
   }

   private String getDeploymentTaskErrorMessage(DeploymentTaskRuntimeMBean dtr) {
      if (dtr.getState() == 3) {
         Exception ex = dtr.getError();
         if (ex != null) {
            return cat.allTaskStatus(dtr.getId(), this.getTaskStatus(dtr), ex.getMessage());
         }
      }

      return null;
   }

   public abstract String getOperation();

   public void showTaskInformationHeader() {
      this.println("");
      this.println(cat.showListHeader());
   }

   private String getTaskStatus(DeploymentTaskRuntimeMBean dtr) {
      int state = dtr.getState();
      switch (state) {
         case 0:
            return cat.stateInit();
         case 1:
            return cat.stateRunning();
         case 2:
            return cat.stateCompleted();
         case 3:
            return cat.stateFailed();
         case 4:
            return cat.stateDeferred();
         default:
            return cat.unknown();
      }
   }

   private String translateDeploymentMessage(Exception msg) {
      StringBuffer result;
      String componentName;
      Throwable t;
      if (this.options.formatted) {
         if (msg instanceof ApplicationException) {
            ApplicationException ae = (ApplicationException)msg;
            result = new StringBuffer("\nException:");
            result.append(ae.getClass().getName() + ": ");
            result.append(ae.getApplicationMessage());
            result.append("\n");
            Hashtable errorTable = ae.getModuleErrors();
            if (errorTable != null && errorTable.size() != 0) {
               Iterator iter = errorTable.keySet().iterator();

               while(iter.hasNext()) {
                  String componentName = (String)iter.next();
                  componentName = (String)errorTable.get(componentName);
                  result.append("\tModule: ");
                  result.append(componentName);
                  result.append("\tError: ");
                  result.append(componentName);
                  result.append("\n");
                  if (this.options.debug) {
                     Exception me = ae.getTargetException(componentName);
                     if (me != null) {
                        result.append(StackTraceUtils.throwable2StackTrace(me));
                        result.append("\n");
                     }
                  }
               }
            }

            if (ae.getNestedException() != null) {
               result.append("Nested Exception:\n");
               result.append(StackTraceUtils.throwable2StackTrace(ae.getNestedException()));
               result.append("\n");
            }
         } else if (msg instanceof ManagementException) {
            t = ManagementException.unWrapExceptions(msg);
            if (t != msg) {
               if (t instanceof Exception) {
                  return this.translateDeploymentMessage((Exception)t);
               }

               result = new StringBuffer("\n" + t.toString());
            } else {
               result = new StringBuffer("\n" + msg.toString());
            }
         } else {
            result = new StringBuffer("\n" + msg.toString());
         }
      } else {
         result = new StringBuffer();
         t = ManagementException.unWrapExceptions(msg);
         if (t instanceof ApplicationException) {
            ApplicationException ae = (ApplicationException)t;
            this.debug("dumping ApplicationException message");
            result.append(ae.getApplicationMessage());
            result.append("\n");
            Hashtable errorTable = ae.getModuleErrors();
            if (errorTable != null && errorTable.size() != 0) {
               Iterator iter = errorTable.keySet().iterator();

               while(iter.hasNext()) {
                  componentName = (String)iter.next();
                  String message = (String)errorTable.get(componentName);
                  this.debug("dumping ModuleException message");
                  result.append(cat.moduleException(componentName, message));
                  result.append("\n");
                  if (this.options.debug) {
                     Throwable me = ae.getTargetException(componentName);
                     if (me != null) {
                        this.debug("dumping ModuleException stack");
                        result.append(StackTraceUtils.throwable2StackTrace(me));
                        result.append("\n");
                     }
                  }
               }
            }
         } else if (this.options.debug) {
            this.debug("dumping Exception stack");
            result.append(StackTraceUtils.throwable2StackTrace(t));
            result.append("\n");
         } else {
            result.append(t.toString() + "\n");
            if (t.getMessage() == null) {
               result.append(StackTraceUtils.throwable2StackTrace(t));
            }

            result.append("\n");
         }
      }

      return result.toString();
   }

   private String translateStatus(int status) {
      switch (status) {
         case 0:
            return cat.messageStateInit();
         case 1:
            return cat.messageStateInProgress();
         case 2:
            return cat.messageStateFailed();
         case 3:
            return cat.messageStateSuccess();
         case 4:
            return cat.messageStateDeferred();
         default:
            return null;
      }
   }

   private String translateTargetType(int mBeanType) {
      if (mBeanType == 1) {
         return cat.messageServer();
      } else {
         return mBeanType == 2 ? cat.messageCluster() : cat.messageUnknown();
      }
   }

   private String getTargetState(int state) {
      switch (state) {
         case 0:
            return cat.stateInit();
         case 1:
            return cat.stateRunning();
         case 2:
            return cat.stateFailed();
         case 3:
            return cat.stateCompleted();
         case 4:
            return cat.stateDeferred();
         default:
            return cat.unknown();
      }
   }

   private String getTargetType(int type) {
      switch (type) {
         case 1:
            return cat.messageServer();
         case 2:
            return cat.messageCluster();
         case 3:
            return cat.messageJMSServer();
         case 4:
            return cat.messageHost();
         case 5:
            return cat.messageSAFAgent();
         case 6:
            return cat.messageTarget();
         default:
            return cat.unknown();
      }
   }

   public void showDeploymentNotificationInformation(String taskId, DeploymentNotification n) {
      String event;
      if (this.options.formatted) {
         event = this.translateNotificationType(n.getPhase());
         if (n.isAppNotification()) {
            this.println(cat.showDeploymentNotification(taskId, event, n.getAppName(), n.getServerName()));
         }
      } else {
         event = n.getAppName();
         String server = n.getServerName();
         String msg = null;
         String moduleName = null;
         String currState;
         if (n.isModuleNotification()) {
            moduleName = n.getModuleName();
            currState = n.getCurrentState();
            String targetState = n.getTargetState();
            String trans = n.getTransition();
            if (trans.equals("end")) {
               msg = cat.successfulTransition(moduleName, currState, targetState, server);
            } else if (trans.equals("failed")) {
               msg = cat.failedTransition(moduleName, currState, targetState, server);
            }

            if (msg != null) {
               this.println(msg);
            }
         } else {
            currState = n.getPhase();
            this.println(cat.appNotification(event, server, currState));
         }
      }

   }

   private String translateNotificationType(String notification) {
      if ("activated".equals(notification)) {
         return cat.messageNotificationActivated();
      } else if ("activating".equals(notification)) {
         return cat.messageNotificationActivating();
      } else if ("deactivated".equals(notification)) {
         return cat.messageNotificationDeactivated();
      } else if ("deactivating".equals(notification)) {
         return cat.messageNotificationDeactivating();
      } else if ("prepared".equals(notification)) {
         return cat.messageNotificationPrepared();
      } else if ("preparing".equals(notification)) {
         return cat.messageNotificationPreparing();
      } else if ("unprepared".equals(notification)) {
         return cat.messageNotificationUnprepared();
      } else if ("unpreparing".equals(notification)) {
         return cat.messageNotificationUnpreparing();
      } else if ("distributing".equals(notification)) {
         return cat.messageNotificationDistributing();
      } else if ("distributed".equals(notification)) {
         return cat.messageNotificationDistributed();
      } else {
         return "failed".equals(notification) ? cat.messageNotificationFailed() : notification;
      }
   }

   public void cleanUp() {
   }

   public void setOut(PrintStream stream) {
      this.out = stream;
   }

   public PrintStream getOut() {
      return this.out;
   }

   public void println(String msg) {
      this.out.println(msg);
   }

   public void print(String msg) {
      this.out.print(msg);
   }
}

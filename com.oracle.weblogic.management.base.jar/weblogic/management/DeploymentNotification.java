package weblogic.management;

import java.util.Date;
import javax.management.Notification;
import javax.management.ObjectName;

/** @deprecated */
@Deprecated
public final class DeploymentNotification extends Notification {
   private static final long serialVersionUID = 1L;
   private static long sequenceNumber = 0L;
   private String appName;
   private String serverName;
   private String moduleName;
   private String transition;
   private String currentState;
   private String targetState;
   private boolean appNotification;
   private boolean moduleNotification;
   private String appPhase;
   public static final String PREPARING = "preparing";
   public static final String PREPARED = "prepared";
   public static final String ACTIVATING = "activating";
   public static final String ACTIVATED = "activated";
   public static final String DEACTIVATING = "deactivating";
   public static final String DEACTIVATED = "deactivated";
   public static final String UNPREPARING = "unpreparing";
   public static final String UNPREPARED = "unprepared";
   public static final String FAILED = "failed";
   public static final String DISTRIBUTING = "distributing";
   public static final String DISTRIBUTED = "distributed";
   public static final String TYPE_APPLICATION = "weblogic.deployment.application";
   public static final String TYPE_MODULE = "weblogic.deployment.application.module";
   public static final String TRANSITION_BEGIN = "begin";
   public static final String TRANSITION_END = "end";
   public static final String TRANSITION_FAILED = "failed";
   public static final String STATE_UNPREPARED = "unprepared";
   public static final String STATE_PREPARED = "prepared";
   public static final String STATE_ACTIVE = "active";
   public static final String STATE_START = "start";
   private String task;

   public String getTask() {
      return this.task;
   }

   public void setTask(String task) {
      this.task = task;
   }

   public DeploymentNotification(ObjectName objectName, long sequenceNumber, String server, String app, String phase) {
      super("weblogic.deployment.application", objectName, sequenceNumber, (new Date()).getTime(), phase + "." + server + "." + app);
      this.appName = null;
      this.serverName = null;
      this.moduleName = null;
      this.transition = null;
      this.currentState = null;
      this.targetState = null;
      this.appNotification = false;
      this.moduleNotification = false;
      this.task = null;
      this.appName = app;
      this.serverName = server;
      this.appNotification = true;
      this.appPhase = phase;
      if (phase.endsWith("ed")) {
         this.transition = "end";
      } else {
         this.transition = "begin";
      }

      if (phase.equals("distributing")) {
         this.currentState = "start";
         this.targetState = "unprepared";
      } else if (phase.equals("distributed")) {
         this.currentState = "start";
         this.targetState = "unprepared";
      } else if (phase.equals("preparing")) {
         this.currentState = "unprepared";
         this.targetState = "prepared";
      } else if (phase.equals("prepared")) {
         this.currentState = "unprepared";
         this.targetState = "prepared";
      } else if (phase.equals("activating")) {
         this.currentState = "prepared";
         this.targetState = "active";
      } else if (phase.equals("activated")) {
         this.currentState = "prepared";
         this.targetState = "active";
      } else if (phase.equals("unpreparing")) {
         this.currentState = "prepared";
         this.targetState = "unprepared";
      } else if (phase.equals("unprepared")) {
         this.currentState = "prepared";
         this.targetState = "unprepared";
      } else if (phase.equals("failed")) {
         this.transition = "failed";
      }

   }

   public DeploymentNotification(ObjectName objectName, long sequenceNumber, String server, String app, String module, String transition, String currState, String targetState, long gentime) {
      super("weblogic.deployment.application.module", objectName, sequenceNumber, gentime, server + "." + app + "." + module + "." + transition + "." + currState + "." + targetState);
      this.appName = null;
      this.serverName = null;
      this.moduleName = null;
      this.transition = null;
      this.currentState = null;
      this.targetState = null;
      this.appNotification = false;
      this.moduleNotification = false;
      this.task = null;
      this.appName = app;
      this.serverName = server;
      this.moduleName = module;
      this.transition = transition;
      this.currentState = currState;
      this.targetState = targetState;
      this.moduleNotification = true;
   }

   public DeploymentNotification(ObjectName objectName, long sequenceNumber, String server, String app, String module, String transition, String currState, String targetState) {
      this(objectName, sequenceNumber, server, app, module, transition, currState, targetState, (new Date()).getTime());
   }

   public static long getChangeNotificationCount() {
      return sequenceNumber;
   }

   private static synchronized long generateSequenceNumber() {
      return (long)(sequenceNumber++);
   }

   public String getPhase() {
      return this.appPhase;
   }

   public String getAppName() {
      return this.appName;
   }

   public String getServerName() {
      return this.serverName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getTransition() {
      return this.transition;
   }

   public boolean isEndTransition() {
      return "end".equals(this.transition);
   }

   public boolean isBeginTransition() {
      return "begin".equals(this.transition);
   }

   public boolean isFailedTransition() {
      return "failed".equals(this.transition);
   }

   public String getCurrentState() {
      return this.currentState;
   }

   public String getTargetState() {
      return this.targetState;
   }

   public boolean isAppNotification() {
      return this.appNotification;
   }

   public boolean isModuleNotification() {
      return this.moduleNotification;
   }

   public String toString() {
      return "[DeploymentNotification: " + "AppName:" + this.appName + ", ModName:" + this.moduleName + ", ServerName:" + this.serverName + ", " + this.transition + "." + this.currentState + "." + this.targetState + "]";
   }
}

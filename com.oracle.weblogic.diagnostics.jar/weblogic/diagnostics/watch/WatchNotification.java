package weblogic.diagnostics.watch;

import java.io.Serializable;
import java.util.Map;

/** @deprecated */
@Deprecated
public class WatchNotification implements Serializable {
   static final long serialVersionUID = 8863588077682047098L;
   public static final String WATCH_NAME = "WatchName";
   public static final String WATCH_RULE_TYPE = "WatchRuleType";
   public static final String WATCH_RULE = "WatchRule";
   public static final String WATCH_TIME = "WatchTime";
   public static final String WATCH_SEVERITY = "WatchSeverityLevel";
   public static final String WATCH_DATA = "WatchData";
   public static final String WATCH_ALARM_TYPE = "WatchAlarmType";
   public static final String WATCH_ALARM_RESET_PERIOD = "WatchAlarmResetPeriod";
   public static final String WATCH_DOMAIN_NAME = "WatchDomainName";
   public static final String WATCH_SERVER_NAME = "WatchServerName";
   public static final String EVENT_TIMESTAMP = "EventTimestamp";
   public static final String EVENT_CONTEXT_ID = "EventContextId";
   public static final String EVENT_TRANSACTION_ID = "EventTransactionId";
   public static final String EVENT_USER_ID = "EventUserId";
   public static final String EVENT_ACTION_TYPE = "EventActionType";
   public static final String EVENT_DOMAIN_NAME = "EventDomainName";
   public static final String EVENT_SERVER_NAME = "EventServerName";
   public static final String EVENT_SCOPE_NAME = "EventScopeName";
   public static final String EVENT_MONITOR_TYPE = "EventMonitorType";
   public static final String EVENT_SOURCE_FILE = "EventSourceFile";
   public static final String EVENT_LINE_NUMBER = "EventLineNumber";
   public static final String EVENT_CLASS_NAME = "EventClassName";
   public static final String EVENT_METHOD_NAME = "EventMethodName";
   public static final String EVENT_METHOD_DESCRIPTOR = "EventMethodDescriptor";
   public static final String EVENT_RET_VALUE = "EventReturnValue";
   public static final String EVENT_PAYLOAD = "EventPayload";
   private String watchName;
   private String watchRuleType;
   private String watchRule;
   private String watchTime;
   private String watchSeverityLevel;
   private String watchAlarmType;
   private String watchAlarmResetPeriod;
   private String watchDomainName;
   private String watchServerName;
   private String message;
   private transient Map watchData;
   private String watchDataStr;

   WatchNotification() {
   }

   public String getWatchName() {
      return this.watchName;
   }

   void setWatchName(String wn) {
      this.watchName = wn;
   }

   public String getWatchRuleType() {
      return this.watchRuleType;
   }

   void setWatchRuleType(String ruleType) {
      this.watchRuleType = ruleType;
   }

   public String getWatchRule() {
      return this.watchRule;
   }

   void setWatchRule(String rule) {
      this.watchRule = rule;
   }

   public String getWatchTime() {
      return this.watchTime;
   }

   void setWatchTime(String wt) {
      this.watchTime = wt;
   }

   public String getWatchSeverityLevel() {
      return this.watchSeverityLevel;
   }

   void setWatchSeverityLevel(String severity) {
      this.watchSeverityLevel = severity;
   }

   public String getWatchAlarmType() {
      return this.watchAlarmType;
   }

   void setWatchAlarmType(String wat) {
      this.watchAlarmType = wat;
   }

   public String getWatchAlarmResetPeriod() {
      return this.watchAlarmResetPeriod;
   }

   void setWatchAlarmResetPeriod(String warp) {
      this.watchAlarmResetPeriod = warp;
   }

   public String getWatchDomainName() {
      return this.watchDomainName;
   }

   void setWatchDomainName(String domain) {
      this.watchDomainName = domain;
   }

   public String getMessage() {
      return this.message;
   }

   void setMessage(String m) {
      this.message = m;
   }

   public String getWatchServerName() {
      return this.watchServerName;
   }

   void setWatchServerName(String wsn) {
      this.watchServerName = wsn;
   }

   public Map getWatchData() {
      return this.watchData;
   }

   void setWatchData(Map data) {
      this.watchData = data;
   }

   public String getWatchDataToString() {
      return this.watchDataStr;
   }

   void setWatchDataString(String wds) {
      this.watchDataStr = wds;
   }

   public String toString() {
      return this.getClass().getName() + ":  watchName: " + this.watchName + " watchRuleType: " + this.watchRuleType + " watchRule: " + this.watchRule + " watchTime: " + this.watchTime + "\n watchSeverityLevel: " + this.watchSeverityLevel + " watchData: " + this.getWatchDataToString() + " watchAlarmType: " + this.watchAlarmType + " watchResetPeriod: " + this.watchAlarmResetPeriod + "\n watchDomainName: " + this.watchDomainName + "\n watchServerName: " + this.watchServerName + "\n - " + super.toString();
   }
}

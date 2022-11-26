package weblogic.diagnostics.watch;

import com.bea.adaptive.harvester.WatchedValues;
import com.bea.logging.BaseLogEntry;
import com.bea.logging.MsgIdPrefixConverter;
import com.oracle.weblogic.diagnostics.expressions.EvaluatorFactory;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBeanRuntimeException;
import com.oracle.weblogic.diagnostics.expressions.FixedExpressionEvaluator;
import com.oracle.weblogic.diagnostics.expressions.NotEnoughDataException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.archive.InstrumentationEventBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.harvester.InstanceNameNormalizer;
import weblogic.diagnostics.harvester.InvalidHarvesterInstanceNameException;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.diagnostics.logging.LogEventBean;
import weblogic.diagnostics.logging.LogVariablesImpl;
import weblogic.diagnostics.notifications.i18n.NotificationsTextTextFormatter;
import weblogic.diagnostics.query.Query;
import weblogic.diagnostics.query.QueryFactory;
import weblogic.diagnostics.query.UnknownVariableException;
import weblogic.diagnostics.query.VariableIndexResolver;
import weblogic.diagnostics.query.VariableResolver;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.ScheduleExpression;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

class Watch {
   static final String LOG_EVENT_VAR_NAME = "log";
   static final String INSTRUMENTATION_EVENT_VAR_NAME = "instrumentationEvent";
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static final String WATCH_NOTICE_MSG_ID = MsgIdPrefixConverter.getDefaultMsgIdPrefix() + "-320068";
   private static final int STATE_ENABLED = 0;
   private static final int STATE_DISABLED = 1;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private ComponentInvocationContextManager cicManager;
   private String watchName;
   private String watchRuleExpression;
   private int watchRuleType;
   private int watchSeverity;
   private String[] watchNotifications;
   private int watchAlarmType;
   private int watchAlarmResetPeriod;
   private boolean alarm = false;
   private WatchNotificationListener[] watchNotificationListeners;
   private Query watchQuery;
   private FixedExpressionEvaluator _elEvaluator;
   private VariableIndexResolver variableIndexResolver;
   private int watchState;
   private long watchResetTime;
   private WatchedValues watchedValues;
   private String moduleName;
   private ServiceLocator locator;
   private String languageType = "WLDF";
   private boolean usingELLanguage = false;
   private EvaluatorFactory elEvalFactory;
   private ScheduleExpression scheduleExpression;
   private static final WorkManager workManager = WorkManagerFactory.getInstance().getDefault();
   private Boolean lastEvaluatedResult = null;
   private String partitionName = "";
   private LogEventBean logEventBean = new LogEventBean();
   private InstrumentationEventBean instEventBean = new InstrumentationEventBean();
   private NotificationsWork currentNotificationsWork;

   Watch(String partition_name, String name, int ruleType, String expressionLanguage, String ruleExpression, ScheduleExpression scheduleExpr, int severity, int alarmType, int alarmResetPeriod, String[] notifications, WatchNotificationListener[] listeners, String moduleName, WatchedValues wv, WatchConfiguration.ResourceExpressionBean resourceBean) throws ManagementException, InvalidWatchException {
      this.setWatchName(name);
      this.moduleName = moduleName;
      this.watchRuleType = ruleType;
      this.partitionName = partition_name;
      this.watchedValues = wv;
      if (this.partitionName != null && !partition_name.isEmpty()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("partition-scoped watch, forcing policy language to be Java EL");
         }

         this.languageType = "EL";
      } else {
         this.languageType = expressionLanguage;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("policy language: " + this.languageType);
         }
      }

      this.usingELLanguage = this.languageType.equals("EL");
      this.watchRuleExpression = ruleExpression;
      if (!this.ruleIsEmpty()) {
         if (this.usingELLanguage) {
            if (this.partitionName != null && !this.partitionName.isEmpty()) {
               String varName = null;
               switch (ruleType) {
                  case 1:
                     varName = "log";
                     break;
                  case 2:
                  default:
                     varName = null;
                     break;
                  case 3:
                     varName = "instrumentationEvent";
                     break;
                  case 4:
                     varName = "log";
               }

               StringBuilder sb = new StringBuilder();
               sb.append('(');
               sb.append(this.watchRuleExpression);
               sb.append(')');
               if (varName != null) {
                  sb.append(" && ");
                  sb.append(varName);
                  sb.append(".getPartitionName().equals('").append(this.partitionName).append("')");
               }

               this.watchRuleExpression = sb.toString();
            }

            FixedExpressionEvaluator evaluator = this.getEvaluator();
            switch (ruleType) {
               case 1:
               case 4:
                  evaluator.getELContext().bind("log", this.logEventBean);
                  break;
               case 2:
                  if (resourceBean != null) {
                     evaluator.getELContext().bind("resource", resourceBean);
                  }
                  break;
               case 3:
                  evaluator.getELContext().bind("instrumentationEvent", this.instEventBean);
            }
         } else {
            if (this.partitionName != null && !this.partitionName.isEmpty()) {
               switch (ruleType) {
                  case 1:
                  case 3:
                  case 4:
                     StringBuilder sb = new StringBuilder();
                     sb.append('(');
                     sb.append(this.watchRuleExpression);
                     sb.append(')');
                     sb.append(" AND PARTITION_NAME = '").append(this.partitionName).append("'");
                     this.watchRuleExpression = sb.toString();
                  case 2:
               }
            }

            this.initQueryExpression(this.watchRuleExpression);
         }
      }

      this.scheduleExpression = scheduleExpr;
      this.setSeverity(severity);
      this.setAlarmType(alarmType);
      this.setAlarmResetPeriod(alarmResetPeriod);
      this.watchNotifications = notifications;
      this.watchNotificationListeners = listeners;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Created watch: " + this);
      }

   }

   synchronized void destroy() {
      if (this._elEvaluator != null) {
         this.elEvalFactory.destroyEvaluator(this._elEvaluator);
      }

   }

   boolean isCalendarSchedule() {
      return this.scheduleExpression != null && this.usingELLanguage;
   }

   ScheduleExpression getScheduleExpression() {
      return this.scheduleExpression;
   }

   public WatchedValues.Values addVariable(String varName, String watchName) throws UnknownVariableException {
      String[] props = HarvesterVariablesParser.parse(varName, watchName);
      boolean typeIsPattern = false;
      boolean instIsPattern = false;
      WatchedValues.Values vimpl = null;
      String namespace = props[0];
      String typeName = props[1];
      String instName = props[2];
      String attrName = props[3];

      try {
         if (instName != null) {
            InstanceNameNormalizer translator = new InstanceNameNormalizer(instName);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Normalizing instance name " + instName);
            }

            instName = translator.translateHarvesterSpec();
            instIsPattern = translator.isRegexPattern();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Normalized instance name: " + instName + ", isPattern: " + instIsPattern);
            }
         }

         vimpl = this.watchedValues.addMetric(namespace, typeName, instName, attrName, typeIsPattern, instIsPattern, false, true);
         return vimpl;
      } catch (InvalidHarvesterInstanceNameException var12) {
         DiagnosticsLogger.logInvalidWatchVariableInstanceNameSpecification(instName, this.getWatchName());
         throw new UnknownVariableException(var12.getMessage(), var12);
      }
   }

   String getWatchName() {
      return this.watchName;
   }

   String getModuleName() {
      return this.moduleName;
   }

   String getRuleExpression() {
      return this.watchRuleExpression;
   }

   int getRuleType() {
      return this.watchRuleType;
   }

   int getSeverity() {
      return this.watchSeverity;
   }

   String[] getNotifications() {
      return this.watchNotifications;
   }

   int getAlarmType() {
      return this.watchAlarmType;
   }

   boolean hasAlarm() {
      return this.getAlarmType() != 0;
   }

   boolean hasAutomaticResetAlarm() {
      return this.getAlarmType() == 2;
   }

   boolean hasManualResetAlarm() {
      return this.getAlarmType() == 1;
   }

   int getAlarmResetPeriod() {
      return this.watchAlarmResetPeriod;
   }

   WatchNotificationListener[] getNotificationListeners() {
      return this.watchNotificationListeners;
   }

   boolean isEnabled() {
      return this.watchState == 0;
   }

   boolean isDisabled() {
      return this.watchState == 1;
   }

   boolean isAlarm() {
      return this.alarm;
   }

   long getResetTime() {
      return this.watchResetTime;
   }

   void setWatchName(String name) throws InvalidWatchException {
      if (name == null) {
         throw new InvalidWatchException("Name can not be null");
      } else if (name.length() == 0) {
         throw new InvalidWatchException("Name can not be empty");
      } else {
         this.watchName = name;
      }
   }

   void initQueryExpression(String ruleExpression) throws InvalidWatchException {
      this.initWLDFQueryVars();

      try {
         this.watchQuery = QueryFactory.createQuery(this.variableIndexResolver, ruleExpression);
      } catch (Exception var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Parsing of watch rule failed: ", var3);
         }

         throw new InvalidWatchException("Invalid watch rule expression", var3);
      }
   }

   void setSeverity(int severity) throws InvalidWatchException {
      switch (severity) {
         case 0:
         case 1:
         case 2:
         case 4:
         case 8:
         case 16:
         case 32:
         case 64:
         case 128:
         case 256:
            this.watchSeverity = severity;
            return;
         default:
            throw new InvalidWatchException("Invalid severity " + severity);
      }
   }

   void setAlarmType(int alarmType) throws InvalidWatchException {
      switch (alarmType) {
         case 0:
         case 1:
         case 2:
            this.watchAlarmType = alarmType;
            return;
         default:
            throw new InvalidWatchException("Invalid alarm type " + alarmType);
      }
   }

   void setAlarmResetPeriod(int resetPeriod) throws InvalidWatchException {
      if (resetPeriod < 0) {
         throw new InvalidWatchException("Invalid reset period " + resetPeriod);
      } else {
         this.watchAlarmResetPeriod = resetPeriod;
      }
   }

   void setNotifications(String[] notifications) {
      this.watchNotifications = notifications;
   }

   void setNotificationListeners(WatchNotificationListener[] listeners) {
      this.watchNotificationListeners = listeners;
   }

   void setEnabled() {
      this.setState(0);
   }

   void setDisabled() {
      this.setState(1);
   }

   synchronized void setAlarm(boolean value) {
      this.alarm = value;
   }

   void setResetTime(long resetTime) {
      this.watchResetTime = resetTime;
   }

   boolean evaluateHarvesterRuleWatch() throws Exception {
      if (this.usingELLanguage) {
         return this.evaluateELExpression(this.getEvaluator());
      } else {
         HarvesterVariablesImpl harvesterVariables = new HarvesterVariablesImpl(this.watchedValues);
         return this.evaluateWLDFLegacyRule(harvesterVariables);
      }
   }

   boolean evaluateLogRuleWatch(BaseLogEntry logEntry) throws Exception {
      if (logEntry.getId().equals(WATCH_NOTICE_MSG_ID)) {
         return false;
      } else if (this.usingELLanguage) {
         this.logEventBean.setBaseLogEntry(logEntry);
         return this.evaluateELExpression(this.getEvaluator());
      } else {
         LogVariablesImpl.LogVariablesResolver logVarRes = LogVariablesImpl.getInstance().getLogVariablesResolver(logEntry);
         return this.evaluateWLDFLegacyRule(logVarRes);
      }
   }

   boolean evaluateEventDataRuleWatch(DataRecord dataRecord) throws Exception {
      if (this.usingELLanguage) {
         this.instEventBean.setDataRecord(dataRecord);
         return this.evaluateELExpression(this.getEvaluator());
      } else {
         EventDataVariablesImpl variables = new EventDataVariablesImpl();
         variables.setDataRecord(dataRecord);
         return this.evaluateWLDFLegacyRule(variables);
      }
   }

   private synchronized FixedExpressionEvaluator getEvaluator() {
      if (this._elEvaluator == null) {
         this.locator = GlobalServiceLocator.getServiceLocator();
         this.elEvalFactory = (EvaluatorFactory)this.locator.getService(EvaluatorFactory.class, new Annotation[0]);
         Annotation[] serviceQualifiers = WatchUtils.buildRuntimeAnnotationsScopeList(this.partitionName, this.watchRuleType);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Creating Java EL evaluator for express, ion " + this.watchRuleExpression + " with search qualifiers " + Arrays.toString(serviceQualifiers));
         }

         this._elEvaluator = this.elEvalFactory.createEvaluator(this.watchRuleExpression, Boolean.class, serviceQualifiers);
      }

      return this._elEvaluator;
   }

   private void performNotifications(Map watchData) throws Exception {
      WatchNotificationInternal wn = new WatchNotificationInternal(this, System.currentTimeMillis(), watchData);
      DiagnosticsLogger.logWatchEvaluatedToTrue(wn.getWatchName(), wn.getModuleName(), wn.getWatchSeverityLevel(), wn.getWatchServerName(), wn.getWatchTime(), this.recordWatchNotificationDetails(wn));
      if (this.watchNotificationListeners != null && this.watchNotificationListeners.length != 0) {
         synchronized(this) {
            if (this.currentNotificationsWork == null) {
               WatchNotificationListener[] currentListeners = (WatchNotificationListener[])Arrays.copyOf(this.watchNotificationListeners, this.watchNotificationListeners.length);
               this.currentNotificationsWork = new NotificationsWork(wn, currentListeners, this);
               workManager.schedule(new NotificationsWork(wn, currentListeners, this));
            } else {
               DiagnosticsWatchLogger.logNotificationsAlreadyInProgress(wn.getWatchName());
            }

         }
      }
   }

   void notificationsWorkCompleted() {
      this.currentNotificationsWork = null;
   }

   private String recordWatchNotificationDetails(WatchNotificationInternal wn) {
      NotificationsTextTextFormatter fm = NotificationsTextTextFormatter.getInstance();
      StringWriter sw = new StringWriter();
      BufferedWriter bw = new BufferedWriter(sw);

      try {
         bw.newLine();
         bw.write(fm.getSMTPDefaultBodyLine("WatchRuleType", wn.getWatchRuleType()));
         bw.write(fm.getSMTPDefaultBodyLine("WatchRule", this.escapeComparisonOperators(wn.getWatchRule())));
         bw.write(fm.getSMTPDefaultBodyLine("WatchData", wn.getWatchDataToString()));
         bw.write(fm.getSMTPDefaultBodyLine("WatchAlarmType", wn.getWatchAlarmType()));
         bw.write(fm.getSMTPDefaultBodyLine("WatchAlarmResetPeriod", wn.getWatchAlarmResetPeriod()));
         bw.close();
      } catch (IOException var6) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Caught IOException building WatchNotification details string", var6);
         }
      }

      return sw.toString();
   }

   private String escapeComparisonOperators(String watchRuleString) {
      return watchRuleString == null ? watchRuleString : watchRuleString.replaceAll("> ", ">");
   }

   private void setState(int state) {
      this.watchState = state;
   }

   public String toString() {
      String ret = "Watch: " + this.watchName + " rule: " + this.watchRuleExpression + " ruleType: " + this.watchRuleType + " severity: " + this.watchSeverity + " alarmType: " + this.watchAlarmType + " alarmReset: " + this.watchAlarmResetPeriod + " state: " + this.watchState + " notifications: ";

      for(int i = 0; this.watchNotifications != null && i < this.watchNotifications.length; ++i) {
         ret = ret + " " + this.watchNotifications[i];
      }

      return ret;
   }

   Boolean getLastEvaluatedResult() {
      return this.lastEvaluatedResult;
   }

   private void initWLDFQueryVars() {
      switch (this.watchRuleType) {
         case 1:
         case 4:
            LogVariablesImpl logVariables = LogVariablesImpl.getInstance();
            this.variableIndexResolver = logVariables;
            break;
         case 2:
            this.variableIndexResolver = new HarvesterVariablesIndexResolver();
            break;
         case 3:
            EventDataVariablesImpl eventVariables = new EventDataVariablesImpl();
            this.variableIndexResolver = eventVariables;
            break;
         default:
            throw new InvalidWatchException("Unknown rule type " + this.watchRuleType);
      }

   }

   private boolean evaluateELExpression(FixedExpressionEvaluator elEvaluator) throws Exception {
      boolean result = false;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Evaluating EL " + this.watchRuleExpression);
      }

      if (this.ruleIsEmpty()) {
         result = true;
      } else {
         try {
            result = this.executeELPolicyEvaluation(elEvaluator);
         } catch (Throwable var8) {
            Throwable cause = WatchUtils.hasRootCause(var8, NotEnoughDataException.class);
            if (cause == null) {
               throw var8;
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Not enough metric data to evaluate expression: " + cause.getMessage());
            }
         } finally {
            ;
         }
      }

      if (result) {
         Map data = null;
         switch (this.watchRuleType) {
            case 1:
            case 4:
               data = this.logEventBean.getBeanData();
               break;
            case 2:
               if (elEvaluator != null) {
                  data = elEvaluator.getResolvedValues();
               }
               break;
            case 3:
               data = this.instEventBean.getBeanData();
         }

         this.performNotifications(data);
      }

      this.lastEvaluatedResult = result;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Evaluated watch " + this.watchName + " to " + result);
      }

      return result;
   }

   private boolean executeELPolicyEvaluation(final FixedExpressionEvaluator elEvaluator) {
      boolean result = false;
      if (this.partitionName != null && !this.partitionName.isEmpty()) {
         if (this.cicManager == null) {
            this.cicManager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
         }

         ComponentInvocationContext cic = this.cicManager.createComponentInvocationContext(this.partitionName);

         try {
            result = (Boolean)ComponentInvocationContextManager.runAs(KERNEL_ID, cic, new Callable() {
               public Boolean call() throws Exception {
                  return (Boolean)elEvaluator.evaluate();
               }
            });
         } catch (ExecutionException var5) {
            throw new ExpressionBeanRuntimeException(var5);
         }
      } else if (elEvaluator != null) {
         result = (Boolean)elEvaluator.evaluate();
      }

      return result;
   }

   private boolean evaluateWLDFLegacyRule(VariableResolver resolver) throws Exception {
      boolean result = false;
      Map data = null;
      if (this.ruleIsEmpty()) {
         result = true;
      } else {
         result = this.watchQuery.executeQuery(resolver);
         if (result) {
            data = this.getResolverEvaluatedData(resolver);
         }
      }

      if (result) {
         this.performNotifications(data);
      }

      this.lastEvaluatedResult = result;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Evaluated watch " + this.watchName + " to " + result);
      }

      return result;
   }

   private Map getResolverEvaluatedData(VariableResolver resolver) {
      Map data = null;
      switch (this.watchRuleType) {
         case 1:
         case 4:
            data = ((LogVariablesImpl.LogVariablesResolver)resolver).getVariableData();
            break;
         case 2:
            data = this.watchQuery.getLastExecutionTrace().getEvaluatedVariables();
            break;
         case 3:
            data = ((EventDataVariablesImpl)resolver).getWatchData();
      }

      return (Map)data;
   }

   private boolean ruleIsEmpty() {
      return this.watchRuleExpression == null || this.watchRuleExpression.isEmpty();
   }

   private class HarvesterVariablesIndexResolver implements VariableIndexResolver {
      private HarvesterVariablesIndexResolver() {
      }

      public int getVariableIndex(String varName) throws UnknownVariableException {
         if (Watch.debugLogger.isDebugEnabled()) {
            Watch.debugLogger.debug("Getting index for " + varName);
         }

         WatchedValues.Values vals = Watch.this.addVariable(varName, Watch.this.getWatchName());
         if (vals == null) {
            throw new UnknownVariableException(DiagnosticsTextWatchTextFormatter.getInstance().getUnknownWatchVariableExceptionText(Watch.this.getWatchName(), varName));
         } else {
            int index = vals.getVID();
            if (Watch.debugLogger.isDebugEnabled()) {
               Watch.debugLogger.debug("Returning index for " + varName + " idx is " + index);
            }

            return index;
         }
      }

      // $FF: synthetic method
      HarvesterVariablesIndexResolver(Object x1) {
         this();
      }
   }
}

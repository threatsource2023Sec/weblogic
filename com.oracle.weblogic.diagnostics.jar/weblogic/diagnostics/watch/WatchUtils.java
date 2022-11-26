package weblogic.diagnostics.watch;

import com.oracle.weblogic.diagnostics.expressions.EvaluatorFactory;
import com.oracle.weblogic.diagnostics.expressions.FixedExpressionEvaluator;
import com.oracle.weblogic.diagnostics.expressions.NotEnoughDataException;
import com.oracle.weblogic.diagnostics.watch.DomainLogRuleTypeLiteral;
import com.oracle.weblogic.diagnostics.watch.EventDataRuleTypeLiteral;
import com.oracle.weblogic.diagnostics.watch.MetricRuleTypeLiteral;
import com.oracle.weblogic.diagnostics.watch.ServerLogRuleTypeLiteral;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFNotificationBean;
import weblogic.diagnostics.descriptor.WLDFScheduleBean;
import weblogic.diagnostics.query.VariableInstance;
import weblogic.i18n.logging.SeverityI18N;
import weblogic.management.WebLogicMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.ScheduleExpression;

public final class WatchUtils {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatchUtils");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static String domainName = null;
   private static final DateFormat DATE_FORMAT = DateFormat.getDateTimeInstance(2, 0, Locale.getDefault());
   private static ServiceLocator locator;
   private static EvaluatorFactory expressionFactory;
   static final String IMAGE_NOTIFICATION_TYPE = "WLDFImageNotification";
   static final String JMX_NOTIFICATION_TYPE = "WLDFJMXNotification";
   static final String JMS_NOTIFICATION_TYPE = "WLDFJMSNotification";
   static final String SNMP_NOTIFICATION_TYPE = "WLDFSNMPNotification";
   static final String SMTP_NOTIFICATION_TYPE = "WLDFSMTPNotification";

   public static int convertRuleTypeToInt(String ruleType) {
      int rule = 2;
      if (ruleType != null) {
         if (ruleType.equals("Harvester")) {
            rule = 2;
         } else if (ruleType.equals("Log")) {
            rule = 1;
         } else if (ruleType.equals("EventData")) {
            rule = 3;
         } else if (ruleType.equals("DomainLog")) {
            rule = 4;
         }
      }

      return rule;
   }

   public static String convertRuleTypeToString(int rule) {
      switch (rule) {
         case 1:
            return "Log";
         case 2:
            return "Harvester";
         case 3:
            return "EventData";
         case 4:
            return "DomainLog";
         default:
            return "UNKNOWN";
      }
   }

   public static int convertAlarmResetTypeToInt(String alarmResetType) {
      int rule = 1;
      if (alarmResetType != null) {
         if (alarmResetType.equals("ManualReset")) {
            rule = 1;
         } else if (alarmResetType.equals("AutomaticReset")) {
            rule = 2;
         } else {
            rule = 0;
         }
      }

      return rule;
   }

   public static String[] getNotificationNames(WLDFNotificationBean[] notifications) {
      ArrayList al = new ArrayList();
      if (notifications != null) {
         for(int i = 0; i < notifications.length; ++i) {
            al.add(notifications[i].getName());
         }
      }

      String[] results = new String[al.size()];
      al.toArray(results);
      return results;
   }

   public static WLDFNotificationBean[] getNotificationsOfType(WLDFNotificationBean[] notifications, String type) {
      ArrayList al = new ArrayList();
      if (notifications != null) {
         for(int i = 0; i < notifications.length; ++i) {
            if (((WebLogicMBean)notifications[i]).getType().equals(type)) {
               al.add(notifications[i]);
            }
         }
      }

      WLDFNotificationBean[] result = new WLDFNotificationBean[al.size()];
      al.toArray(result);
      return result;
   }

   static String getCurrentDomainName() {
      if (domainName != null) {
         return domainName;
      } else {
         RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
         if (runtimeAccess != null) {
            domainName = runtimeAccess.getDomainName();
         }

         return domainName;
      }
   }

   static String getToAddresses(String[] recipients) {
      StringBuffer buffer = new StringBuffer();

      for(int i = 0; i < recipients.length; ++i) {
         buffer.append(recipients[i]);
         if (i < recipients.length - 1) {
            buffer.append(',');
         }
      }

      return buffer.toString();
   }

   static WatchNotification setWatchTimeInDateFormat(WatchNotification watchNotification, long time) {
      synchronized(DATE_FORMAT) {
         watchNotification.setWatchTime(DATE_FORMAT.format(new Date(time)));
         return watchNotification;
      }
   }

   static WatchNotification populateFromWatch(WatchNotification watchNotification, Watch watch) throws NotificationCreateException {
      if (watch == null) {
         return watchNotification;
      } else {
         String message = "WatchName: " + watch.getWatchName() + " " + "WatchSeverityLevel" + ": " + SeverityI18N.severityNumToString(watch.getSeverity());
         watchNotification.setMessage(message);
         watchNotification.setWatchName(watch.getWatchName());
         switch (watch.getRuleType()) {
            case 1:
               watchNotification.setWatchRuleType("Log");
               break;
            case 2:
               watchNotification.setWatchRuleType("Harvester");
               break;
            case 3:
               watchNotification.setWatchRuleType("EventData");
               break;
            case 4:
               watchNotification.setWatchRuleType("DomainLog");
               break;
            default:
               throw new NotificationCreateException("Invalid rule type");
         }

         String ruleExpression = watch.getRuleExpression() == null ? "" : watch.getRuleExpression();
         watchNotification.setWatchRule(ruleExpression);
         watchNotification.setWatchSeverityLevel(SeverityI18N.severityNumToString(watch.getSeverity()));
         watchNotification.setWatchAlarmResetPeriod("" + watch.getAlarmResetPeriod());
         if (!watch.hasAlarm()) {
            watchNotification.setWatchAlarmType("None");
         } else if (watch.hasManualResetAlarm()) {
            watchNotification.setWatchAlarmType("ManualReset");
         } else {
            if (!watch.hasAutomaticResetAlarm()) {
               throw new NotificationCreateException("Invalid alarm type");
            }

            watchNotification.setWatchAlarmType("AutomaticReset");
         }

         watchNotification.setWatchDomainName(WatchUtils.WatchDataStaticInitializer.DOMAIN_NAME);
         watchNotification.setWatchServerName(WatchUtils.WatchDataStaticInitializer.SERVER_NAME);
         return watchNotification;
      }
   }

   static String getWatchDataString(Map props) {
      StringBuilder buffer = new StringBuilder(128);
      if (props != null) {
         Iterator it = props.entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
               if (value.getClass().isArray()) {
                  Object[] values = (Object[])((Object[])value);
                  appendVariableInstance(buffer, key, Arrays.toString(values));
               } else {
                  appendVariableInstance(buffer, key, value);
               }
            }
         }
      }

      return buffer.toString();
   }

   private static void appendVariableInstance(StringBuilder buffer, Object key, Object value) {
      if (value != null) {
         if (value instanceof VariableInstance) {
            VariableInstance vi = (VariableInstance)value;
            if (vi.getInstanceValue() == null) {
               return;
            }

            buffer.append(vi.getInstanceName());
            buffer.append("//");
            buffer.append(vi.getAttributeName());
            buffer.append(" = ");
            buffer.append(vi.getInstanceValue());
         } else {
            buffer.append(key);
            buffer.append(" = ");
            buffer.append(value);
         }

         buffer.append(" ");
      }
   }

   public static ScheduleExpression createScheduleExpression(WLDFScheduleBean scheduleBean) {
      ScheduleExpression scheduleExpression = new ScheduleExpression();
      scheduleExpression.dayOfMonth(scheduleBean.getDayOfMonth());
      scheduleExpression.dayOfWeek(scheduleBean.getDayOfWeek());
      scheduleExpression.hour(scheduleBean.getHour());
      scheduleExpression.minute(scheduleBean.getMinute());
      scheduleExpression.second(scheduleBean.getSecond());
      scheduleExpression.month(scheduleBean.getMonth());
      scheduleExpression.year(scheduleBean.getYear());
      String timezone = scheduleBean.getTimezone();
      if (timezone != null) {
         scheduleExpression.timezone(timezone);
      }

      return scheduleExpression;
   }

   public static Object evalSingleExpression(String expression, Annotation... qualifiers) {
      EvaluatorFactory evaluatorFactory = getExpressionFactory();
      if (evaluatorFactory == null) {
         return null;
      } else {
         Object result = null;
         final FixedExpressionEvaluator elEvaluator = evaluatorFactory.createEvaluator(expression, Object.class, qualifiers);

         try {
            result = SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedAction() {
               public Object run() {
                  Object value = elEvaluator.evaluate();
                  return value;
               }
            });
            if (debugLogger.isDebugEnabled()) {
               if (result instanceof Iterable) {
                  try {
                     Iterable iterable = (Iterable)result;
                     Iterator it = iterable.iterator();

                     while(it.hasNext()) {
                        debugLogger.debug("Result: " + it.next());
                     }
                  } catch (NotEnoughDataException var11) {
                     debugLogger.debug("Not enough metric data exception: " + var11.getMessage());
                  }
               } else {
                  debugLogger.debug("Result: " + result);
               }

               Map data = elEvaluator.getResolvedValues();
               debugLogger.debug("Evaluated data: " + data.toString());
            }
         } catch (Throwable var12) {
            Throwable cause = hasRootCause(var12, NotEnoughDataException.class);
            if (cause == null) {
               throw var12;
            }

            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Not enough metric data to evaluate expression: " + cause.getMessage());
            }
         } finally {
            if (elEvaluator != null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("destroying evaluator");
               }

               evaluatorFactory.destroyEvaluator(elEvaluator);
            }

         }

         return result;
      }
   }

   static Throwable hasRootCause(Throwable cause, Class rootCauseType) {
      if (cause != null) {
         return rootCauseType.equals(cause.getClass()) ? cause : hasRootCause(cause.getCause(), rootCauseType);
      } else {
         return null;
      }
   }

   static EvaluatorFactory getExpressionFactory() {
      Class var0 = WatchUtils.class;
      synchronized(WatchUtils.class) {
         if (expressionFactory == null) {
            expressionFactory = (EvaluatorFactory)getServiceLocator().getService(EvaluatorFactory.class, new Annotation[0]);
         }
      }

      return expressionFactory;
   }

   static ServiceLocator getServiceLocator() {
      Class var0 = WatchUtils.class;
      synchronized(WatchUtils.class) {
         if (locator == null) {
            locator = GlobalServiceLocator.getServiceLocator();
         }
      }

      return locator;
   }

   static Annotation[] buildAnnotationsScopeList(String partitionName) {
      return buildRuntimeAnnotationsScopeList(partitionName, -1);
   }

   public static Annotation[] buildRuntimeAnnotationsScopeList(String partitionName, int watchRuleType) {
      List alList = new ArrayList();
      if (partitionName != null && !partitionName.isEmpty()) {
         alList.add(new PartitionLiteral());
      } else if (!isAdminServer()) {
         alList.add(new ManagedServerLiteral());
      } else {
         alList.add(new AdminServerLiteral());
      }

      buildRuleTypeAnnotationsList(watchRuleType, alList);
      Annotation[] searchAnnotations = (Annotation[])alList.toArray(new Annotation[0]);
      return searchAnnotations;
   }

   public static Annotation[] buildConfigTimeAnnotationsScopeList(String partitionName, int watchRuleType) {
      List alList = new ArrayList();
      if (partitionName != null && !partitionName.isEmpty()) {
         alList.add(new PartitionLiteral());
      }

      buildRuleTypeAnnotationsList(watchRuleType, alList);
      Annotation[] searchAnnotations = (Annotation[])alList.toArray(new Annotation[0]);
      return searchAnnotations;
   }

   static boolean isAdminServer() {
      return ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServer();
   }

   private static void buildRuleTypeAnnotationsList(int watchRuleType, List alList) {
      switch (watchRuleType) {
         case 1:
            alList.add(new ServerLogRuleTypeLiteral());
            break;
         case 2:
            alList.add(new MetricRuleTypeLiteral());
            break;
         case 3:
            alList.add(new EventDataRuleTypeLiteral());
            break;
         case 4:
            alList.add(new DomainLogRuleTypeLiteral());
      }

   }

   private static class WatchDataStaticInitializer {
      private static final String DOMAIN_NAME;
      private static final String SERVER_NAME;

      static {
         DOMAIN_NAME = ManagementService.getRuntimeAccess(WatchUtils.KERNEL_ID).getDomainName();
         SERVER_NAME = ManagementService.getRuntimeAccess(WatchUtils.KERNEL_ID).getServerName();
      }
   }
}

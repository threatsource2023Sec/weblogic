package weblogic.diagnostics.watch;

import com.bea.adaptive.harvester.HarvestCallback;
import com.bea.adaptive.harvester.WatchedValues;
import com.bea.diagnostics.notifications.InvalidNotificationException;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBeanRuntimeException;
import com.oracle.weblogic.diagnostics.expressions.NotEnoughDataException;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.watch.actions.Action;
import com.oracle.weblogic.diagnostics.watch.actions.ActionsManager;
import com.oracle.weblogic.diagnostics.watch.actions.ScopedActionsFactory;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.descriptor.WLDFActionBean;
import weblogic.diagnostics.descriptor.WLDFImageNotificationBean;
import weblogic.diagnostics.descriptor.WLDFJMSNotificationBean;
import weblogic.diagnostics.descriptor.WLDFJMXNotificationBean;
import weblogic.diagnostics.descriptor.WLDFNotificationBean;
import weblogic.diagnostics.descriptor.WLDFRESTNotificationBean;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.descriptor.WLDFSMTPNotificationBean;
import weblogic.diagnostics.descriptor.WLDFSNMPNotificationBean;
import weblogic.diagnostics.descriptor.WLDFScheduleBean;
import weblogic.diagnostics.descriptor.WLDFWatchBean;
import weblogic.diagnostics.descriptor.WLDFWatchNotificationBean;
import weblogic.diagnostics.harvester.HarvesterException;
import weblogic.diagnostics.harvester.WLDFHarvester;
import weblogic.diagnostics.harvester.WLDFHarvesterManager;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextWatchTextFormatter;
import weblogic.diagnostics.module.WLDFModuleException;
import weblogic.diagnostics.utils.PartitionHelper;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;
import weblogic.i18n.logging.Severities;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.WLDFWatchNotificationRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.timers.ScheduleExpression;

final class WatchConfiguration {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticWatch");
   private static final DiagnosticsTextWatchTextFormatter watchTextFormatter = DiagnosticsTextWatchTextFormatter.getInstance();
   private Map enabledNotifications = new Hashtable();
   private Map allWatches = new Hashtable();
   private ArrayList enabledHarvesterWatches = new ArrayList();
   private ArrayList enabledCalendarWatches = new ArrayList();
   private ArrayList enabledLogWatches = new ArrayList();
   private ArrayList enabledDomainLogWatches = new ArrayList();
   private ArrayList enabledEventDataWatches = new ArrayList();
   private int logEventHandlerSeverity = 16;
   private WatchedValues watchedValues;
   private int numActiveImageNotifications;
   private WLDFResourceBean resourceBean;
   private WLDFHarvester harvester;
   private WatchManagerStatisticsImpl statistics = new WatchManagerStatisticsImpl();
   private String partitionName;
   private String partitionId;
   private ScopedActionsFactory actionsFactory;
   private static ActionsManager actionsManager;

   WatchConfiguration(String partition, WLDFResourceBean profile) throws WLDFModuleException {
      if (partition != null && partition.length() > 0) {
         this.partitionName = partition;
         this.partitionId = PartitionHelper.getPartitionId(this.partitionName);
      }

      this.resourceBean = profile;
      this.initializeFromConfiguration();
   }

   WatchConfiguration() {
   }

   protected ResourceExpressionBean createResourceExpressionBean(String forWatch) {
      return new ResourceExpressionBean(forWatch);
   }

   boolean isWatchNotificationEnabled() {
      return this.resourceBean.getWatchNotification().isEnabled();
   }

   private void addWatch(Watch watch) {
      if (watch == null) {
         throw new InvalidWatchException("Watch can not be null");
      } else {
         String watchName = watch.getWatchName();
         if (watchName == null) {
            throw new InvalidWatchException("Watch name can not be null");
         } else {
            Watch existingWatch = (Watch)this.allWatches.get(watchName);
            if (existingWatch != null) {
               DiagnosticsLogger.logDuplicateWatch(watchName);
            } else {
               this.allWatches.put(watchName, watch);
               if (watch.isEnabled()) {
                  this.addEnabledWatch(watch);
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Added watch " + watchName);
               }

            }
         }
      }
   }

   public Watch getWatch(String watchName) throws WatchNotFoundException {
      if (watchName == null) {
         throw new WatchNotFoundException("Watch name can not be null");
      } else {
         Watch watch = (Watch)this.allWatches.get(watchName);
         if (watch != null) {
            return watch;
         } else {
            throw new WatchNotFoundException("Watch " + watchName + " is not a registered watch");
         }
      }
   }

   public boolean hasWatch(String watchName) {
      return this.allWatches.get(watchName) != null;
   }

   public boolean isWatchEnabled(String watchName) throws WatchNotFoundException {
      Watch watch = this.getWatch(watchName);
      return watch.isEnabled();
   }

   public boolean isWatchDisabled(String watchName) throws WatchNotFoundException {
      Watch watch = this.getWatch(watchName);
      return watch.isDisabled();
   }

   public boolean isWatchAlarmActive(String watchName) throws WatchNotFoundException {
      Watch watch = this.getWatch(watchName);
      return watch.isAlarm();
   }

   public Watch[] getWatches() {
      Watch[] retArray = new Watch[this.allWatches.size()];
      this.allWatches.values().toArray(retArray);
      return retArray;
   }

   public ArrayList getEnabledHarvesterWatches() {
      return this.enabledHarvesterWatches;
   }

   public ArrayList getEnabledLogWatches() {
      return this.enabledLogWatches;
   }

   public ArrayList getEnabledDomainLogWatches() {
      return this.enabledDomainLogWatches;
   }

   public ArrayList getEnabledEventDataWatches() {
      return this.enabledEventDataWatches;
   }

   private void addEnabledWatch(Watch watch) {
      watch.setEnabled();
      switch (watch.getRuleType()) {
         case 1:
            this.enabledLogWatches.add(watch);
            break;
         case 2:
            if (watch.isCalendarSchedule()) {
               this.enabledCalendarWatches.add(watch);
            }

            this.enabledHarvesterWatches.add(watch);
            break;
         case 3:
            this.enabledEventDataWatches.add(watch);
            break;
         case 4:
            if (isAdminServer()) {
               this.enabledDomainLogWatches.add(watch);
            }
            break;
         default:
            throw new AssertionError("Unknown rule type" + watch.getRuleType());
      }

   }

   static boolean isAdminServer() {
      return ManagementService.getRuntimeAccess(kernelId).isAdminServer();
   }

   private void addNotificationListener(WatchNotificationListener listener) {
      if (listener == null) {
         throw new InvalidNotificationException("Notification can not be null");
      } else {
         String notificationName = listener.getNotificationName();
         if (notificationName == null) {
            throw new InvalidNotificationException("Notification name can not be null");
         } else {
            WatchNotificationListener existingListener = (WatchNotificationListener)this.enabledNotifications.get(notificationName);
            if (existingListener != null) {
               DiagnosticsLogger.logDuplicateNotification(listener.getNotificationName());
            } else {
               if (listener.isEnabled()) {
                  this.enabledNotifications.put(notificationName, listener);
               }

               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Added notification " + listener);
               }

            }
         }
      }
   }

   private WatchNotificationListener getNotification(String notificationName) throws NotificationNotFoundException {
      if (notificationName == null) {
         throw new NotificationNotFoundException("Notification name can not be null");
      } else {
         WatchNotificationListener listener = (WatchNotificationListener)this.enabledNotifications.get(notificationName);
         if (listener == null) {
            throw new NotificationNotFoundException("Notification " + notificationName + " has is not a registered notification");
         } else {
            return listener;
         }
      }
   }

   public WatchNotificationListener[] getNotifications() {
      WatchNotificationListener[] retArray = new WatchNotificationListener[this.enabledNotifications.size()];
      return (WatchNotificationListener[])this.enabledNotifications.values().toArray(retArray);
   }

   public WatchNotificationListener[] getEnabledNotifications() {
      WatchNotificationListener[] retArray = new WatchNotificationListener[this.enabledNotifications.size()];
      this.enabledNotifications.values().toArray(retArray);
      return retArray;
   }

   public int getEventHandlerSeverity() {
      return this.logEventHandlerSeverity;
   }

   public void initializeLogEventHandlerSeverity(String severity) {
      this.logEventHandlerSeverity = Severities.severityStringToNum(severity);
   }

   WatchedValues getWatchedValues() {
      return this.watchedValues;
   }

   private void identifyImageNotifications(WLDFImageNotificationBean[] imageNotifications) throws WLDFModuleException {
      if (imageNotifications != null && imageNotifications.length != 0) {
         WLDFImageNotificationBean[] var2 = imageNotifications;
         int var3 = imageNotifications.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDFImageNotificationBean imageNotification = var2[var4];

            try {
               if (imageNotification.isEnabled()) {
                  ImageNotificationListener listener = new ImageNotificationListener(imageNotification, this.partitionName, this.partitionId, this.statistics);
                  this.addNotificationListener(listener);
               } else if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Notification " + imageNotification.getName() + " not enabled, ignoring.");
               }
            } catch (Exception var7) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error creating Image notification ", var7);
               }

               throw new WLDFModuleException(var7);
            }
         }

      }
   }

   private void identifyJMSNotifications(WLDFJMSNotificationBean[] jmsNotifications) throws WLDFModuleException {
      if (jmsNotifications != null && jmsNotifications.length != 0) {
         WLDFJMSNotificationBean[] var2 = jmsNotifications;
         int var3 = jmsNotifications.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDFJMSNotificationBean jmsNotification = var2[var4];

            try {
               if (jmsNotification.isEnabled()) {
                  JMSNotificationListener listener = new JMSNotificationListener(jmsNotification, this.statistics);
                  this.addNotificationListener(listener);
               } else if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Notification " + jmsNotification.getName() + " not enabled, ignoring.");
               }
            } catch (Exception var7) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error creating JMS Notification ", var7);
               }

               throw new WLDFModuleException(var7);
            }
         }

      }
   }

   private void identifyJMXNotifications(WLDFJMXNotificationBean[] jmxNotifications) throws WLDFModuleException {
      if (jmxNotifications != null && jmxNotifications.length != 0) {
         WLDFJMXNotificationBean[] var2 = jmxNotifications;
         int var3 = jmxNotifications.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDFJMXNotificationBean jmxNotification = var2[var4];

            try {
               if (jmxNotification.isEnabled()) {
                  JMXNotificationListener listener = new JMXNotificationListener(jmxNotification, this.statistics);
                  this.addNotificationListener(listener);
               } else if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Notification " + jmxNotification.getName() + " not enabled, ignoring.");
               }
            } catch (Exception var7) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error creating JMX Notification ", var7);
               }

               throw new WLDFModuleException(var7);
            }
         }

      }
   }

   private void identifyNotifications(WLDFWatchNotificationBean wnBean) throws WLDFModuleException {
      this.identifyImageNotifications(wnBean.getImageNotifications());
      this.identifyJMSNotifications(wnBean.getJMSNotifications());
      this.identifyJMXNotifications(wnBean.getJMXNotifications());
      this.identifySMTPNotifications(wnBean.getSMTPNotifications());
      this.identifySNMPNotifications(wnBean.getSNMPNotifications());
      this.identifyRESTNotifications(wnBean.getRESTNotifications());
      this.identifyBuiltInActions(wnBean.getScriptActions(), "ScriptAction");
      this.identifyBuiltInActions(wnBean.getScaleUpActions(), "ScaleUp");
      this.identifyBuiltInActions(wnBean.getScaleDownActions(), "ScaleDown");
      this.identifyBuiltInActions(wnBean.getLogActions(), "LogAction");
      this.identifyBuiltInActions(wnBean.getHeapDumpActions(), "HeapDump");
      this.identifyBuiltInActions(wnBean.getThreadDumpActions(), "ThreadDump");
      this.identifyActionExtensions(wnBean.getActions());
   }

   private void identifyBuiltInActions(WLDFNotificationBean[] builtInConfigs, String actionType) throws WLDFModuleException {
      if (builtInConfigs != null && builtInConfigs.length != 0) {
         WLDFNotificationBean[] var3 = builtInConfigs;
         int var4 = builtInConfigs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            WLDFNotificationBean descriptorBean = var3[var5];
            if (descriptorBean.isEnabled()) {
               this.addActionListener(descriptorBean, actionType);
            } else if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Action " + descriptorBean.getName() + " not enabled, ignoring.");
            }
         }

      }
   }

   private void identifyActionExtensions(WLDFActionBean[] descriptorBeans) throws WLDFModuleException {
      if (descriptorBeans != null && descriptorBeans.length != 0) {
         WLDFActionBean[] var2 = descriptorBeans;
         int var3 = descriptorBeans.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDFActionBean actionDescriptor = var2[var4];
            if (actionDescriptor.isEnabled()) {
               this.addActionListener(actionDescriptor, actionDescriptor.getType());
            } else if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Action " + actionDescriptor.getName() + " not enabled, ignoring.");
            }
         }

      }
   }

   private void addActionListener(WLDFNotificationBean actionBean, String actionType) throws WLDFModuleException {
      String actionName = actionBean.getName();

      try {
         Action delegate = this.getActionsFactory().getAction(actionType);
         if (delegate == null) {
            DiagnosticsWatchLogger.logInvalidActionTypeForScope(actionName, actionType);
         } else {
            ActionWrapperNotificationListener listener = new ActionWrapperNotificationListener(actionBean, delegate, this.statistics);
            this.addNotificationListener(listener);
         }

      } catch (Exception var6) {
         DiagnosticsWatchLogger.logUnexpectedExceptionForAction(actionName, actionType, var6);
         throw new WLDFModuleException(var6);
      }
   }

   private ScopedActionsFactory getActionsFactory() {
      Class var1 = WatchConfiguration.class;
      synchronized(WatchConfiguration.class) {
         if (actionsManager == null) {
            actionsManager = (ActionsManager)GlobalServiceLocator.getServiceLocator().getService(ActionsManager.class, new Annotation[0]);
         }
      }

      if (this.actionsFactory == null) {
         this.actionsFactory = actionsManager.createScopedActionsFactory(WatchUtils.buildAnnotationsScopeList(this.partitionName));
      }

      return this.actionsFactory;
   }

   private void identifySMTPNotifications(WLDFSMTPNotificationBean[] smtpNotifications) throws WLDFModuleException {
      if (smtpNotifications != null && smtpNotifications.length != 0) {
         WLDFSMTPNotificationBean[] var2 = smtpNotifications;
         int var3 = smtpNotifications.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDFSMTPNotificationBean smtpNotification = var2[var4];

            try {
               if (smtpNotification.isEnabled()) {
                  SMTPNotificationListener listener = new SMTPNotificationListener(smtpNotification, this.statistics);
                  this.addNotificationListener(listener);
               } else if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Notification " + smtpNotification.getName() + " not enabled, ignoring.");
               }
            } catch (Exception var7) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error creating SMTP Notification ", var7);
               }

               throw new WLDFModuleException(var7);
            }
         }

      }
   }

   private void identifySNMPNotifications(WLDFSNMPNotificationBean[] snmpNotifications) throws WLDFModuleException {
      if (snmpNotifications != null && snmpNotifications.length != 0) {
         WLDFSNMPNotificationBean[] var2 = snmpNotifications;
         int var3 = snmpNotifications.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDFSNMPNotificationBean snmpNotif = var2[var4];

            try {
               if (snmpNotif.isEnabled()) {
                  SNMPNotificationListener listener = new SNMPNotificationListener(snmpNotif, this.statistics);
                  this.addNotificationListener(listener);
               } else if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Notification " + snmpNotif.getName() + " not enabled, ignoring.");
               }
            } catch (Exception var7) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error creating SNMP Notification ", var7);
               }

               throw new WLDFModuleException(var7);
            }
         }

      }
   }

   private void identifyRESTNotifications(WLDFRESTNotificationBean[] restNotifications) throws WLDFModuleException {
      if (restNotifications != null && restNotifications.length != 0) {
         WLDFRESTNotificationBean[] var2 = restNotifications;
         int var3 = restNotifications.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            WLDFRESTNotificationBean restNotification = var2[var4];

            try {
               if (restNotification.isEnabled()) {
                  RESTNotificationListener listener = new RESTNotificationListener(restNotification, this.statistics);
                  this.addNotificationListener(listener);
               } else if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Notification " + restNotification.getName() + " not enabled, ignoring.");
               }
            } catch (Exception var7) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error creating REST Notification ", var7);
               }

               throw new WLDFModuleException(var7);
            }
         }

      }
   }

   private void identifyWatches(WLDFWatchBean[] watches) throws WLDFModuleException {
      if (watches != null && watches.length != 0) {
         int len = watches.length;

         for(int i = 0; i < len; ++i) {
            WLDFWatchBean watchBean = watches[i];
            String watchName = watchBean.getName();
            int rule = WatchUtils.convertRuleTypeToInt(watchBean.getRuleType());
            int severity = Severities.severityStringToNum(watchBean.getSeverity());
            int alarm = WatchUtils.convertAlarmResetTypeToInt(watchBean.getAlarmType());
            int alarmResetPeriod = watchBean.getAlarmResetPeriod();
            ScheduleExpression scheduleExpression = null;
            boolean usingEL = watchBean.getExpressionLanguage().equals("EL");
            if (usingEL && watchBean.getRuleType().equals("Harvester")) {
               WLDFScheduleBean scheduleBean = watchBean.getSchedule();
               scheduleExpression = WatchUtils.createScheduleExpression(scheduleBean);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Using schedule expression for watch " + watchBean.getName() + ": " + scheduleExpression);
               }
            }

            try {
               WLDFNotificationBean[] notifications = watchBean.getNotifications();
               ArrayList enabledNotifications = null;
               ArrayList enabledNotificationNames = null;
               if (notifications != null && notifications.length > 0) {
                  enabledNotifications = new ArrayList();
                  enabledNotificationNames = new ArrayList();
                  WLDFNotificationBean[] var15 = notifications;
                  int var16 = notifications.length;

                  for(int var17 = 0; var17 < var16; ++var17) {
                     WLDFNotificationBean notifBean = var15[var17];

                     try {
                        enabledNotifications.add(this.getNotification(notifBean.getName()));
                        enabledNotificationNames.add(notifBean.getName());
                     } catch (NotificationNotFoundException var20) {
                        DiagnosticsLogger.logInvalidNotification(watchName, notifBean.getName());
                     }
                  }
               }

               String[] notifNames = enabledNotificationNames == null ? null : (String[])enabledNotificationNames.toArray(new String[enabledNotificationNames.size()]);
               WatchNotificationListener[] notifs = enabledNotifications == null ? null : (WatchNotificationListener[])enabledNotifications.toArray(new WatchNotificationListener[enabledNotifications.size()]);
               Watch watch = new Watch(this.partitionName, watchName, rule, watchBean.getExpressionLanguage(), watchBean.getRuleExpression(), scheduleExpression, severity, alarm, alarmResetPeriod, notifNames, notifs, this.getModuleName(), this.watchedValues, this.createResourceExpressionBean(watchName));
               if (watchBean.isEnabled()) {
                  watch.setEnabled();
               } else {
                  watch.setDisabled();
               }

               this.addWatch(watch);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Loaded watch " + watchName);
               }
            } catch (Exception var21) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error creating watch ", var21);
               }

               throw new WLDFModuleException(DiagnosticsLogger.logCreateWatchErrorLoggable(watchName, var21).getMessage());
            }
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Loaded " + len + " watches ");
         }

      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No configured watches");
         }

      }
   }

   int getNumActiveImageNotifications() {
      return this.numActiveImageNotifications;
   }

   private void identifyActiveImageNotifications() {
      Watch[] var1 = this.getWatches();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Watch w = var1[var3];
         WatchNotificationListener[] notificationListeners = w.getNotificationListeners();
         if (notificationListeners != null) {
            WatchNotificationListener[] var6 = notificationListeners;
            int var7 = notificationListeners.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               WatchNotificationListener listener = var6[var8];
               if (listener instanceof ImageNotificationListener) {
                  ++this.numActiveImageNotifications;
               }
            }
         }
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Number of active Diagnostic Image listeners: " + this.numActiveImageNotifications);
      }

   }

   private void initializeFromConfiguration() throws WLDFModuleException {
      WLDFResourceBean resource = this.getResourceBean();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Creating new WatchNotification configuration object for " + resource.getWatchNotification());
      }

      this.watchedValues = this.getHarvester().createWatchedValues(this.getName(), this.partitionId, this.partitionName);
      this.initializeLogEventHandlerSeverity(resource.getWatchNotification().getLogWatchSeverity());
      this.identifyNotifications(resource.getWatchNotification());
      this.identifyWatches(resource.getWatchNotification().getWatches());
      this.identifyActiveImageNotifications();
      if (debugLogger.isDebugEnabled()) {
         boolean isEnabled = this.isWatchNotificationEnabled();
         debugLogger.debug("Current WatchNotification configuration is " + (isEnabled ? "enabled" : "disabled"));
      }

   }

   public WLDFResourceBean getResourceBean() {
      return this.resourceBean;
   }

   long getSamplePeriodMillis() {
      return this.resourceBean != null ? this.resourceBean.getHarvester().getSamplePeriod() : 300000L;
   }

   void harvest() {
      int wvid = this.watchedValues.getId();
      if (wvid < 0) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("No watched values registered, skipping W&N harvest cycle");
         }

      } else {
         this.getHarvester().harvest(wvid);
      }
   }

   String getName() {
      return this.resourceBean == null ? "" : this.resourceBean.getWatchNotification().getName();
   }

   void deactivate() throws WLDFModuleException {
      try {
         this.destroyWatches();
         this.deleteWatchedValues();
         this.destroyNotifications();
      } catch (HarvesterException var2) {
         throw new WLDFModuleException(var2);
      }
   }

   private void destroyWatches() {
      Watch watch;
      for(Iterator var1 = this.allWatches.values().iterator(); var1.hasNext(); watch.destroy()) {
         watch = (Watch)var1.next();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Destroying watch " + watch.getWatchName());
         }
      }

   }

   private void destroyNotifications() {
      if (this.actionsFactory != null) {
         this.actionsFactory.destroy();
      }

   }

   void activate(WatchManager watchManager) throws WLDFModuleException {
      try {
         if (this.isWatchNotificationEnabled()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Activating WatchedNotification Configuration.");
            }

            WatchNotificationRuntimeMBeanImpl notifRuntime = watchManager.getWatchNotificationRuntime();
            if (notifRuntime != null) {
               this.initializeJMXNotificationListeners(notifRuntime);
            }

            if (this.watchedValues == null) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("No watched metrics configured.");
               }
            } else if (this.watchedValues.getId() > -1) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Watched Values already appears initialized, value: " + this.watchedValues.getId());
               }
            } else if (this.getNumConfiguredWatchedValues() > 0) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Registering WatchedValues for module " + this.getModuleName() + " with Harvester.");
               }

               this.getHarvester().addWatchedValues(this.watchedValues.getName(), this.watchedValues, (HarvestCallback)null);
            } else if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("No watched metrics configured.");
            }
         } else {
            debugLogger.debug("Watch activation deferred, harvester is not yet activated");
         }

      } catch (Exception var3) {
         throw new WLDFModuleException(var3);
      }
   }

   void initializeJMXNotificationListeners(WLDFWatchNotificationRuntimeMBean notifRuntime) {
      Iterator var2 = this.enabledNotifications.values().iterator();

      while(var2.hasNext()) {
         WatchNotificationListener listener = (WatchNotificationListener)var2.next();
         if (listener instanceof JMXNotificationListener) {
            JMXNotificationListener jmxListener = (JMXNotificationListener)listener;
            jmxListener.activate(notifRuntime.getWLDFWatchJMXNotificationRuntime(), notifRuntime.getWLDFWatchJMXNotificationSource());
         }
      }

   }

   int getNumConfiguredWatchedValues() {
      return this.watchedValues == null ? 0 : this.watchedValues.getAllMetricValues().size();
   }

   private WLDFHarvester getHarvester() {
      if (this.harvester == null) {
         this.harvester = WLDFHarvesterManager.getInstance().getHarvesterSingleton();
      }

      return this.harvester;
   }

   private void deleteWatchedValues() throws HarvesterException {
      if (this.watchedValues != null) {
         if (this.watchedValues.getId() > -1) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("WatchSubModule.deleteWatchedValues(): unregistering WatchedValues " + this.watchedValues.getId());
            }

            this.getHarvester().deleteWatchedValues(this.watchedValues);
         } else if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("WatchSubModule.deleteWatchedValues(): Watched values ID invalid, does not appear to have been registered");
         }
      }

   }

   WatchManagerStatisticsImpl getStatistics() {
      return this.statistics;
   }

   String getModuleName() {
      return this.resourceBean == null ? "" : this.resourceBean.getName();
   }

   String getPartitionName() {
      return this.partitionName;
   }

   @WLDFI18n(
      value = "resourcebean.short",
      displayName = "resourcebean.display.name",
      fullDescription = "resourcebean.full",
      resourceBundle = "weblogic.diagnostics.watch.ResourceExpressionBean"
   )
   public class ResourceExpressionBean {
      private Map watches;
      private String sourceWatch;

      private ResourceExpressionBean(String forWatch) {
         this.sourceWatch = forWatch;
      }

      @WLDFI18n("resourcebean.watches.short")
      public Map getWatches() {
         if (this.watches == null) {
            this.watches = new WatchBeanMap(WatchConfiguration.this.allWatches.size());
            Iterator var1 = WatchConfiguration.this.allWatches.entrySet().iterator();

            while(var1.hasNext()) {
               Map.Entry entry = (Map.Entry)var1.next();
               this.watches.put(entry.getKey(), WatchConfiguration.this.new WatchExpressionBean((Watch)entry.getValue()));
            }
         }

         return this.watches;
      }

      // $FF: synthetic method
      ResourceExpressionBean(String x1, Object x2) {
         this(x1);
      }

      private class WatchBeanMap extends HashMap {
         public WatchBeanMap(int capacity) {
            super(capacity);
         }

         public WatchExpressionBean get(Object key) {
            String keyName = (String)key;
            if (keyName.equals(ResourceExpressionBean.this.sourceWatch)) {
               throw new ExpressionBeanRuntimeException(WatchConfiguration.watchTextFormatter.getCircularWatchRuleExpressionDetectedText(ResourceExpressionBean.this.sourceWatch));
            } else {
               WatchExpressionBean value = (WatchExpressionBean)super.get(keyName);
               if (value == null) {
                  throw new ExpressionBeanRuntimeException(WatchConfiguration.watchTextFormatter.getWatchBeanNotFoundText(keyName));
               } else {
                  return value;
               }
            }
         }
      }
   }

   @WLDFI18n(
      value = "watchbean.short",
      displayName = "watchbean.display.name",
      fullDescription = "watchbean.full",
      resourceBundle = "weblogic.diagnostics.watch.ResourceExpressionBean"
   )
   public class WatchExpressionBean implements WatchConstants {
      private Watch delegate;

      public WatchExpressionBean(Watch w) {
         this.delegate = w;
      }

      @WLDFI18n(
         value = "watchbean.alarm.short",
         fullDescription = "watchbean.alarm.full"
      )
      public boolean isAlarm() {
         if (!this.delegate.isEnabled()) {
            throw new ExpressionBeanRuntimeException(WatchConfiguration.watchTextFormatter.getWatchExpressionBeanNotEnabled(this.delegate.getWatchName()));
         } else {
            Boolean lastResult = this.delegate.getLastEvaluatedResult();
            if (lastResult == null) {
               throw new NotEnoughDataException(this.delegate.getWatchName());
            } else {
               Boolean result = false;
               switch (this.delegate.getAlarmType()) {
                  case 1:
                  case 2:
                     result = this.delegate.isAlarm();
                     break;
                  default:
                     result = lastResult;
               }

               return result;
            }
         }
      }
   }
}

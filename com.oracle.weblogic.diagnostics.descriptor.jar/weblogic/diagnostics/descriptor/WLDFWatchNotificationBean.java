package weblogic.diagnostics.descriptor;

public interface WLDFWatchNotificationBean extends WLDFBean {
   boolean isEnabled();

   void setEnabled(boolean var1);

   String getSeverity();

   void setSeverity(String var1);

   String getLogWatchSeverity();

   void setLogWatchSeverity(String var1);

   WLDFWatchBean[] getWatches();

   WLDFWatchBean createWatch(String var1);

   void destroyWatch(WLDFWatchBean var1);

   WLDFNotificationBean[] getNotifications();

   WLDFNotificationBean lookupNotification(String var1);

   WLDFActionBean createAction(String var1, String var2);

   void destroyAction(WLDFActionBean var1);

   WLDFActionBean[] getActions();

   WLDFActionBean lookupAction(String var1);

   WLDFActionBean[] lookupActions(String var1);

   WLDFImageNotificationBean[] getImageNotifications();

   WLDFImageNotificationBean createImageNotification(String var1);

   void destroyImageNotification(WLDFImageNotificationBean var1);

   WLDFImageNotificationBean lookupImageNotification(String var1);

   WLDFJMSNotificationBean[] getJMSNotifications();

   WLDFJMSNotificationBean createJMSNotification(String var1);

   void destroyJMSNotification(WLDFJMSNotificationBean var1);

   WLDFJMSNotificationBean lookupJMSNotification(String var1);

   WLDFLogActionBean[] getLogActions();

   WLDFLogActionBean createLogAction(String var1);

   void destroyLogAction(WLDFLogActionBean var1);

   WLDFLogActionBean lookupLogAction(String var1);

   WLDFJMXNotificationBean[] getJMXNotifications();

   WLDFJMXNotificationBean createJMXNotification(String var1);

   void destroyJMXNotification(WLDFJMXNotificationBean var1);

   WLDFJMXNotificationBean lookupJMXNotification(String var1);

   WLDFSMTPNotificationBean[] getSMTPNotifications();

   WLDFSMTPNotificationBean createSMTPNotification(String var1);

   void destroySMTPNotification(WLDFSMTPNotificationBean var1);

   WLDFSMTPNotificationBean lookupSMTPNotification(String var1);

   WLDFSNMPNotificationBean[] getSNMPNotifications();

   WLDFSNMPNotificationBean createSNMPNotification(String var1);

   void destroySNMPNotification(WLDFSNMPNotificationBean var1);

   WLDFSNMPNotificationBean lookupSNMPNotification(String var1);

   WLDFRESTNotificationBean[] getRESTNotifications();

   WLDFRESTNotificationBean createRESTNotification(String var1);

   void destroyRESTNotification(WLDFRESTNotificationBean var1);

   WLDFRESTNotificationBean lookupRESTNotification(String var1);

   WLDFScaleUpActionBean[] getScaleUpActions();

   WLDFScaleUpActionBean createScaleUpAction(String var1);

   void destroyScaleUpAction(WLDFScaleUpActionBean var1);

   WLDFScaleDownActionBean lookupScaleDownAction(String var1);

   WLDFScaleDownActionBean[] getScaleDownActions();

   WLDFScaleDownActionBean createScaleDownAction(String var1);

   void destroyScaleDownAction(WLDFScaleDownActionBean var1);

   WLDFScaleUpActionBean lookupScaleUpAction(String var1);

   WLDFScriptActionBean[] getScriptActions();

   WLDFScriptActionBean createScriptAction(String var1);

   void destroyScriptAction(WLDFScriptActionBean var1);

   WLDFScriptActionBean lookupScriptAction(String var1);

   WLDFHeapDumpActionBean[] getHeapDumpActions();

   WLDFHeapDumpActionBean createHeapDumpAction(String var1);

   void destroyHeapDumpAction(WLDFHeapDumpActionBean var1);

   WLDFHeapDumpActionBean lookupHeapDumpAction(String var1);

   WLDFThreadDumpActionBean[] getThreadDumpActions();

   WLDFThreadDumpActionBean createThreadDumpAction(String var1);

   void destroyThreadDumpAction(WLDFThreadDumpActionBean var1);

   WLDFThreadDumpActionBean lookupThreadDumpAction(String var1);

   WLDFWatchBean lookupWatch(String var1);
}

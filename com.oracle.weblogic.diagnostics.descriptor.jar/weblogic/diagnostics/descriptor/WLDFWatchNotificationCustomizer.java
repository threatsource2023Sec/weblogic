package weblogic.diagnostics.descriptor;

import java.util.ArrayList;
import weblogic.utils.ArrayUtils;

public class WLDFWatchNotificationCustomizer {
   private final WLDFWatchNotificationBean watchNotificationBean;

   public WLDFWatchNotificationCustomizer(WLDFWatchNotificationBean bean) {
      this.watchNotificationBean = bean;
   }

   public WLDFNotificationBean[] getNotifications() {
      ArrayList list = new ArrayList();
      addAll(list, this.watchNotificationBean.getImageNotifications());
      addAll(list, this.watchNotificationBean.getJMSNotifications());
      addAll(list, this.watchNotificationBean.getJMXNotifications());
      addAll(list, this.watchNotificationBean.getSMTPNotifications());
      addAll(list, this.watchNotificationBean.getSNMPNotifications());
      addAll(list, this.watchNotificationBean.getRESTNotifications());
      addAll(list, this.watchNotificationBean.getScaleUpActions());
      addAll(list, this.watchNotificationBean.getScaleDownActions());
      addAll(list, this.watchNotificationBean.getScriptActions());
      addAll(list, this.watchNotificationBean.getLogActions());
      addAll(list, this.watchNotificationBean.getHeapDumpActions());
      addAll(list, this.watchNotificationBean.getThreadDumpActions());
      addAll(list, this.watchNotificationBean.getActions());
      WLDFNotificationBean[] result = (WLDFNotificationBean[])list.toArray(new WLDFNotificationBean[list.size()]);
      return result;
   }

   public WLDFNotificationBean lookupNotification(String name) {
      WLDFNotificationBean[] notifs = this.getNotifications();
      if (notifs != null && notifs.length != 0) {
         for(int i = 0; i < notifs.length; ++i) {
            if (notifs[i].getName().equals(name)) {
               return notifs[i];
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public WLDFActionBean lookupAction(String name, String type) {
      WLDFActionBean[] actions = this.watchNotificationBean.getActions();
      WLDFActionBean[] var4 = actions;
      int var5 = actions.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         WLDFActionBean action = var4[var6];
         if (action instanceof WLDFActionBean && action.getName().equals(name) && action.getType().equals(type)) {
            return action;
         }
      }

      return null;
   }

   public WLDFActionBean[] lookupActions(String typeName) {
      WLDFActionBean[] actionExtensions = this.watchNotificationBean.getActions();
      ArrayList resultList = new ArrayList();
      WLDFActionBean[] var4 = actionExtensions;
      int var5 = actionExtensions.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         WLDFActionBean action = var4[var6];
         if (action.getType().equals(typeName)) {
            resultList.add(action);
         }
      }

      return (WLDFActionBean[])resultList.toArray(new WLDFActionBean[resultList.size()]);
   }

   private static void addAll(ArrayList list, WLDFNotificationBean[] elements) {
      if (elements != null && elements.length > 0) {
         ArrayUtils.addAll(list, elements);
      }

   }
}

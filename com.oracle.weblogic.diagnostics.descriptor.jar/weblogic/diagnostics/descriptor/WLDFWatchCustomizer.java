package weblogic.diagnostics.descriptor;

import weblogic.descriptor.DescriptorBean;

public class WLDFWatchCustomizer {
   private WLDFWatchBean watchBean;
   String severity = null;

   public WLDFWatchCustomizer(WLDFWatchBean bean) {
      this.watchBean = bean;
   }

   public String getSeverity() {
      if (((DescriptorBean)this.watchBean).isSet("Severity")) {
         return this.severity;
      } else {
         WLDFWatchNotificationBean parent = (WLDFWatchNotificationBean)((DescriptorBean)this.watchBean).getParentBean();
         return parent == null ? "Notice" : parent.getSeverity();
      }
   }

   public void setSeverity(String severity) {
      this.severity = severity;
   }

   public static String getDefaultMinuteSchedule() {
      return "*/5";
   }
}

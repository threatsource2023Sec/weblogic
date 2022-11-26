package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBeanAdapter;
import weblogic.diagnostics.descriptor.WLDFNotificationBean;

public class WLDFActionConfigWrapper extends ActionConfigBeanAdapter {
   private WLDFNotificationBean bean;

   public WLDFActionConfigWrapper(WLDFNotificationBean descriptorBean) {
      super(descriptorBean.getClass().getName());
      this.bean = descriptorBean;
   }

   public String getName() {
      return this.bean.getName();
   }

   public int getTimeout() {
      return this.bean.getTimeout();
   }

   public WLDFNotificationBean getBean() {
      return this.bean;
   }

   public boolean isEnabled() {
      return this.bean.isEnabled();
   }

   public void setEnabled(boolean enabled) {
   }

   public void setName(String name) {
   }

   public void setTimeout(int timeout) {
   }
}

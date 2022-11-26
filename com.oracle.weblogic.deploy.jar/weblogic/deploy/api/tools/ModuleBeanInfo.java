package weblogic.deploy.api.tools;

import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.utils.JMSModuleDefaultingHelper;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public class ModuleBeanInfo extends ModuleInfo {
   private WeblogicModuleBean module = null;
   DescriptorBean beanTree;

   protected ModuleBeanInfo(WeblogicModuleBean mod, WebLogicModuleType type, DescriptorBean bt) {
      this.name = mod.getName();
      this.type = type;
      this.module = mod;
      this.beanTree = bt;
   }

   public String[] getSubDeployments() {
      if (this.subDeployments != null) {
         return this.subDeployments;
      } else {
         if (WebLogicModuleType.JMS.equals(this.type)) {
            JMSBean jms = this.getJMSBeanRoot();
            this.subDeployments = JMSModuleDefaultingHelper.getSubDeploymentNames(jms);
         }

         return this.subDeployments;
      }
   }

   private JMSBean getJMSBeanRoot() {
      return this.beanTree != null ? (JMSBean)this.beanTree : null;
   }
}

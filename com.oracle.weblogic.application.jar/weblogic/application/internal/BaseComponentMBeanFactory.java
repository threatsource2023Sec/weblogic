package weblogic.application.internal;

import javax.management.InvalidAttributeValueException;
import weblogic.application.ComponentMBeanFactory;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ComponentMBean;

public abstract class BaseComponentMBeanFactory {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   public boolean needsApplicationPathMunging() {
      return true;
   }

   protected void dumpCompMBeans(ApplicationMBean appMBean) {
      debugLogger.debug("** Dumping ComponentMBeans for app " + appMBean.getName());
      ComponentMBean[] comps = appMBean.getComponents();
      if (comps == null) {
         comps = new ComponentMBean[0];
      }

      debugLogger.debug("** App has " + comps.length + " ComponentMBeans");

      for(int i = 0; i < comps.length; ++i) {
         debugLogger.debug("** Type is " + comps[i].getClass().getName() + " uri is " + comps[i].getURI());
      }

   }

   protected String removeExtension(String n) {
      int i = n.lastIndexOf(".");
      return i == -1 ? n : n.substring(0, i);
   }

   protected final ComponentMBean findOrCreateComponentMBean(ComponentMBeanFactory.MBeanFactory factory, ApplicationMBean appMBean, String name) {
      return this.findOrCreateComponentMBean(factory, appMBean, name, name);
   }

   protected final ComponentMBean findOrCreateComponentMBean(ComponentMBeanFactory.MBeanFactory factory, ApplicationMBean appMBean, String name, String uri) {
      try {
         ComponentMBean c = ComponentMBeanHelper.findComponentMBeanByName(appMBean, name, factory.getComponentMBeanType());
         if (c != null) {
            return c;
         } else {
            c = factory.newCompMBean(appMBean, name);
            c.setApplication(appMBean);
            c.setURI(uri);
            return c;
         }
      } catch (InvalidAttributeValueException var6) {
         throw new AssertionError(var6);
      }
   }

   protected String getCompatibilityName(String name, AppDeploymentMBean mbean) {
      String cname = mbean.getCompatibilityName();
      return cname == null ? name : cname;
   }
}

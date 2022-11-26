package weblogic.management.mbeans.custom;

import weblogic.management.configuration.WTCExportMBean;
import weblogic.management.configuration.WTCImportMBean;
import weblogic.management.configuration.WTCLocalTuxDomMBean;
import weblogic.management.configuration.WTCPasswordMBean;
import weblogic.management.configuration.WTCRemoteTuxDomMBean;
import weblogic.management.configuration.WTCResourcesMBean;
import weblogic.management.configuration.WTCServerMBean;
import weblogic.management.configuration.WTCtBridgeRedirectMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public final class WTCServer extends ConfigurationMBeanCustomizer {
   public WTCServer(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public WTCImportMBean[] getImports() {
      WTCServerMBean bean = (WTCServerMBean)this.getMbean();
      return bean.getWTCImports();
   }

   public WTCExportMBean[] getExports() {
      WTCServerMBean bean = (WTCServerMBean)this.getMbean();
      return bean.getWTCExports();
   }

   public WTCLocalTuxDomMBean[] getLocalTuxDoms() {
      WTCServerMBean bean = (WTCServerMBean)this.getMbean();
      return bean.getWTCLocalTuxDoms();
   }

   public WTCRemoteTuxDomMBean[] getRemoteTuxDoms() {
      WTCServerMBean bean = (WTCServerMBean)this.getMbean();
      return bean.getWTCRemoteTuxDoms();
   }

   public WTCResourcesMBean getResources() {
      WTCServerMBean bean = (WTCServerMBean)this.getMbean();
      return bean.getWTCResources();
   }

   public WTCPasswordMBean[] getPasswords() {
      WTCServerMBean bean = (WTCServerMBean)this.getMbean();
      return bean.getWTCPasswords();
   }

   public WTCtBridgeRedirectMBean[] gettBridgeRedirects() {
      WTCServerMBean bean = (WTCServerMBean)this.getMbean();
      return bean.getWTCtBridgeRedirects();
   }
}

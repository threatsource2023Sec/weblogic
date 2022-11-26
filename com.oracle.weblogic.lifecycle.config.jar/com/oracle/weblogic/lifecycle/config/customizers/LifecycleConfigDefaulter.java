package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Associations;
import com.oracle.weblogic.lifecycle.config.EditSessions;
import com.oracle.weblogic.lifecycle.config.Environment;
import com.oracle.weblogic.lifecycle.config.Environments;
import com.oracle.weblogic.lifecycle.config.LifecycleConfig;
import com.oracle.weblogic.lifecycle.config.Plugins;
import com.oracle.weblogic.lifecycle.config.Resources;
import com.oracle.weblogic.lifecycle.config.Runtimes;
import com.oracle.weblogic.lifecycle.config.Tenants;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.InstanceLifecycleEvent;
import org.glassfish.hk2.api.InstanceLifecycleEventType;
import org.glassfish.hk2.api.InstanceLifecycleListener;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.xml.api.XmlHandleTransaction;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlRootHandle;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class LifecycleConfigDefaulter implements InstanceLifecycleListener {
   private static final Filter FILTER = BuilderHelper.createContractFilter(XmlHk2ConfigurationBean.class.getName());
   @Inject
   private Provider xmlService;

   public Filter getFilter() {
      return FILTER;
   }

   public void lifecycleEvent(InstanceLifecycleEvent event) {
      if (InstanceLifecycleEventType.POST_PRODUCTION.equals(event.getEventType())) {
         XmlHk2ConfigurationBean bean = (XmlHk2ConfigurationBean)event.getLifecycleObject();
         if (bean != null) {
            if (bean instanceof LifecycleConfig) {
               this.handleLifecycleConfig((LifecycleConfig)bean);
            } else if (bean instanceof Environment) {
               this.handleEnvironment((Environment)bean);
            } else if (bean instanceof Tenants) {
               this.handleTenants((Tenants)bean);
            }
         }
      }
   }

   private void handleTenants(Tenants bean) {
      XmlService xService = (XmlService)this.xmlService.get();
      if (bean.getResources() == null) {
         bean.setResources((Resources)xService.createBean(Resources.class));
      }

   }

   private void handleEnvironment(Environment bean) {
      XmlService xService = (XmlService)this.xmlService.get();
      if (bean.getAssociations() == null) {
         bean.setAssociations((Associations)xService.createBean(Associations.class));
      }

   }

   private void handleLifecycleConfig(LifecycleConfig bean) {
      XmlService xService = (XmlService)this.xmlService.get();
      XmlHk2ConfigurationBean beanAsHk2 = (XmlHk2ConfigurationBean)bean;
      XmlRootHandle handle = beanAsHk2._getRoot();
      XmlHandleTransaction transaction = handle.lockForTransaction();
      boolean success = false;

      try {
         if (bean.getRuntimes() == null) {
            bean.setRuntimes((Runtimes)xService.createBean(Runtimes.class));
         }

         if (bean.getEnvironments() == null) {
            bean.setEnvironments((Environments)xService.createBean(Environments.class));
         }

         if (bean.getTenants() == null) {
            bean.setTenants((Tenants)xService.createBean(Tenants.class));
         }

         if (bean.getPlugins() == null) {
            bean.setPlugins((Plugins)xService.createBean(Plugins.class));
         }

         if (bean.getEditSessions() == null) {
            bean.setEditSessions((EditSessions)xService.createBean(EditSessions.class));
         }

         success = true;
      } finally {
         if (success) {
            transaction.commit();
         } else {
            transaction.abandon();
         }

      }

   }
}

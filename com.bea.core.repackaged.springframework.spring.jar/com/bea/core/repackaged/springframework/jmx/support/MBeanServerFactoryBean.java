package com.bea.core.repackaged.springframework.jmx.support;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jmx.MBeanServerNotFoundException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;

public class MBeanServerFactoryBean implements FactoryBean, InitializingBean, DisposableBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private boolean locateExistingServerIfPossible = false;
   @Nullable
   private String agentId;
   @Nullable
   private String defaultDomain;
   private boolean registerWithFactory = true;
   @Nullable
   private MBeanServer server;
   private boolean newlyRegistered = false;

   public void setLocateExistingServerIfPossible(boolean locateExistingServerIfPossible) {
      this.locateExistingServerIfPossible = locateExistingServerIfPossible;
   }

   public void setAgentId(String agentId) {
      this.agentId = agentId;
   }

   public void setDefaultDomain(String defaultDomain) {
      this.defaultDomain = defaultDomain;
   }

   public void setRegisterWithFactory(boolean registerWithFactory) {
      this.registerWithFactory = registerWithFactory;
   }

   public void afterPropertiesSet() throws MBeanServerNotFoundException {
      if (this.locateExistingServerIfPossible || this.agentId != null) {
         try {
            this.server = this.locateMBeanServer(this.agentId);
         } catch (MBeanServerNotFoundException var2) {
            if (this.agentId != null) {
               throw var2;
            }

            this.logger.debug("No existing MBeanServer found - creating new one");
         }
      }

      if (this.server == null) {
         this.server = this.createMBeanServer(this.defaultDomain, this.registerWithFactory);
         this.newlyRegistered = this.registerWithFactory;
      }

   }

   protected MBeanServer locateMBeanServer(@Nullable String agentId) throws MBeanServerNotFoundException {
      return JmxUtils.locateMBeanServer(agentId);
   }

   protected MBeanServer createMBeanServer(@Nullable String defaultDomain, boolean registerWithFactory) {
      return registerWithFactory ? MBeanServerFactory.createMBeanServer(defaultDomain) : MBeanServerFactory.newMBeanServer(defaultDomain);
   }

   @Nullable
   public MBeanServer getObject() {
      return this.server;
   }

   public Class getObjectType() {
      return this.server != null ? this.server.getClass() : MBeanServer.class;
   }

   public boolean isSingleton() {
      return true;
   }

   public void destroy() {
      if (this.newlyRegistered) {
         MBeanServerFactory.releaseMBeanServer(this.server);
      }

   }
}

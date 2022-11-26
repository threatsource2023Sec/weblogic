package weblogic.jms.frontend;

import java.util.List;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.management.configuration.DomainMBean;

public final class FEConnectionFactoryDelegate implements JMSModuleManagedEntity {
   private Object myLock = new Object();
   private FEConnectionFactory feConnectionFactory = null;
   private JMSConnectionFactoryBean connectionFactoryBean = null;
   private String savedEntityName = null;

   public FEConnectionFactoryDelegate(FEConnectionFactory feConnectionFactory) {
      this.feConnectionFactory = feConnectionFactory;
      this.connectionFactoryBean = feConnectionFactory.getConnectionFactoryBean();
   }

   public void prepare() throws ModuleException {
      FEConnectionFactory fecf = this.checkFEConnectionFactory();
      fecf.prepare();
   }

   public void activate(JMSBean paramWholeModule) throws ModuleException {
      FEConnectionFactory fecf = this.checkFEConnectionFactory();
      fecf.activate(paramWholeModule);
      this.connectionFactoryBean = fecf.getConnectionFactoryBean();
   }

   public void deactivate() throws ModuleException {
      FEConnectionFactory fecf = this.checkFEConnectionFactory();
      fecf.deactivate();
   }

   public void unprepare() throws ModuleException {
      FEConnectionFactory fecf = this.checkFEConnectionFactory();
      fecf.unprepare();
   }

   public void destroy() throws ModuleException {
      FEConnectionFactory fecf = this.checkFEConnectionFactory();
      this.savedEntityName = fecf.getEntityName();
      fecf.destroy();
      synchronized(this.myLock) {
         this.feConnectionFactory = null;
      }
   }

   public void remove() throws ModuleException {
      if ("WebLogic_Debug_CON_fail_remove".equals(this.connectionFactoryBean.getName())) {
         throw new ModuleException("DEBUG: A connection factory with name WebLogic_Debug_CON_fail_remove will force the remove to fail");
      }
   }

   public String getEntityName() {
      FEConnectionFactory fecf = this.feConnectionFactory;
      return fecf == null ? this.savedEntityName : fecf.getEntityName();
   }

   public void prepareChangeOfTargets(List targets, DomainMBean proposedDomain) throws ModuleException {
   }

   public void activateChangeOfTargets() throws ModuleException {
   }

   public void rollbackChangeOfTargets() {
   }

   private FEConnectionFactory checkFEConnectionFactory() throws ModuleException {
      synchronized(this.myLock) {
         if (this.feConnectionFactory == null) {
            throw new ModuleException("The FEConnectionFactory " + this.getEntityName() + " has been destroyed");
         } else {
            return this.feConnectionFactory;
         }
      }
   }
}

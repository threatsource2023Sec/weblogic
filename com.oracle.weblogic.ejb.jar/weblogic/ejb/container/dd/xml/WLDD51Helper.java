package weblogic.ejb.container.dd.xml;

import java.util.HashMap;
import java.util.Map;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.EntityDescriptorBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.StatelessSessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.logging.Loggable;
import weblogic.xml.process.SAXValidationException;

public final class WLDD51Helper {
   public Object beanDesc;
   public boolean setBeanDesc = false;
   private final Map pStorage = new HashMap();

   public void initialize(EjbDescriptorBean ejbDesc, WeblogicEnterpriseBeanBean wlBean) {
      EnterpriseBeansBean entBeans = ejbDesc.getEjbJarBean().getEnterpriseBeans();
      EnterpriseBeanBean theBean = null;
      SessionBeanBean[] var5 = entBeans.getSessions();
      int var6 = var5.length;

      int var7;
      for(var7 = 0; var7 < var6; ++var7) {
         SessionBeanBean sbb = var5[var7];
         if (sbb.getEjbName().equals(wlBean.getEjbName())) {
            theBean = sbb;
            break;
         }
      }

      if (null == theBean) {
         EntityBeanBean[] var9 = entBeans.getEntities();
         var6 = var9.length;

         for(var7 = 0; var7 < var6; ++var7) {
            EntityBeanBean ebb = var9[var7];
            if (ebb.getEjbName().equals(wlBean.getEjbName())) {
               theBean = ebb;
               break;
            }
         }
      }

      if (theBean instanceof EntityBeanBean) {
         EntityDescriptorBean eb = wlBean.getEntityDescriptor();
         eb.getEntityCache().setConcurrencyStrategy("Exclusive");
         this.beanDesc = eb;
      } else if (theBean instanceof SessionBeanBean) {
         if (((SessionBeanBean)theBean).getSessionType().equals("Stateless")) {
            StatelessSessionDescriptorBean sb = wlBean.getStatelessSessionDescriptor();
            this.beanDesc = sb;
         } else {
            StatefulSessionDescriptorBean sb = wlBean.getStatefulSessionDescriptor();
            this.beanDesc = sb;
         }
      }

   }

   public boolean isStateless() {
      return this.beanDesc instanceof StatelessSessionDescriptorBean;
   }

   public boolean isStateful() {
      return this.beanDesc instanceof StatefulSessionDescriptorBean;
   }

   public boolean isEntity() {
      return this.beanDesc instanceof EntityDescriptorBean;
   }

   public StatelessSessionDescriptorBean getStatelessDescriptor() {
      this.setBeanDesc = true;
      return (StatelessSessionDescriptorBean)this.beanDesc;
   }

   public StatefulSessionDescriptorBean getStatefulDescriptor() {
      this.setBeanDesc = true;
      return (StatefulSessionDescriptorBean)this.beanDesc;
   }

   public EntityDescriptorBean getEntityDescriptor() {
      this.setBeanDesc = true;
      return (EntityDescriptorBean)this.beanDesc;
   }

   public PersistenceBean getPersistence() {
      return this.getEntityDescriptor().getPersistence();
   }

   public void addPersistenceType(String id, String version, String storage) {
      this.pStorage.put(id + version, storage);
   }

   public String getPersistenceStorage(String id, String version, String ejbName) throws SAXValidationException {
      if (!this.pStorage.containsKey(id + version)) {
         Loggable l = EJBLogger.logpersistentTypeMissingLoggable(id, version, ejbName);
         throw new SAXValidationException(l.getMessageText());
      } else {
         return (String)this.pStorage.get(id + version);
      }
   }
}

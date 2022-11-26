package weblogic.ejb.container.internal;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.application.PersistenceUnitParent;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.health.HealthStateBuilder;
import weblogic.j2ee.ComponentConcurrentRuntimeMBeanImpl;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.EJBComponentMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.CoherenceClusterRuntimeMBean;
import weblogic.management.runtime.EJBComponentRuntimeMBean;
import weblogic.management.runtime.EJBRuntimeMBean;
import weblogic.management.runtime.KodoPersistenceUnitRuntimeMBean;
import weblogic.management.runtime.PersistenceUnitRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.SpringRuntimeMBean;
import weblogic.management.runtime.WseeClientConfigurationRuntimeMBean;
import weblogic.management.runtime.WseeClientRuntimeMBean;
import weblogic.management.runtime.WseeV2RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class EJBComponentRuntimeMBeanImpl extends ComponentConcurrentRuntimeMBeanImpl implements EJBComponentRuntimeMBean, PersistenceUnitParent, EJBRuntimeHolder, HealthFeedback {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String applicationName;
   private final Map ejbRuntimes = new ConcurrentHashMap();
   private final Map puRuntimes = new HashMap();
   private final Set wseeClientConfigurationRuntimes = new HashSet();
   private final HashSet wseeV2Runtimes = new HashSet();
   private final HashSet wseeClientRuntimes = new HashSet();
   private SpringRuntimeMBean springRuntimeMBean;
   private CoherenceClusterRuntimeMBean coherenceClusterRuntimeMBean;

   public EJBComponentRuntimeMBeanImpl(String name, String moduleId, RuntimeMBean theParent, String applicationName) throws ManagementException {
      super(name, moduleId, theParent, true);
      this.applicationName = applicationName;
   }

   /** @deprecated */
   @Deprecated
   public int getStatus() {
      return 0;
   }

   public EJBRuntimeMBean[] getEJBRuntimes() {
      return (EJBRuntimeMBean[])this.ejbRuntimes.values().toArray(new EJBRuntimeMBean[this.ejbRuntimes.size()]);
   }

   public EJBRuntimeMBean getEJBRuntime(String ejbName) {
      return (EJBRuntimeMBean)this.ejbRuntimes.get(ejbName);
   }

   public void addEJBRuntimeMBean(String ejbName, EJBRuntimeMBean mbean) {
      this.ejbRuntimes.put(ejbName, mbean);
   }

   public void removeEJBRuntimeMBean(String ejbName) {
      this.ejbRuntimes.remove(ejbName);
   }

   public void removeAllEJBRuntimeMBeans() {
      this.ejbRuntimes.clear();
   }

   public EJBComponentMBean getEJBComponent() {
      DomainMBean root = ManagementService.getRuntimeAccess(KERNEL_ID).getDomain();
      ApplicationMBean appMBean = root.lookupApplication(this.applicationName);
      return appMBean == null ? null : appMBean.lookupEJBComponent(this.getName());
   }

   public String toString() {
      return "EJBComponentRuntimeMBean: name=" + this.getName();
   }

   public void addKodoPersistenceUnit(KodoPersistenceUnitRuntimeMBean mbean) {
      this.puRuntimes.put(mbean.getPersistenceUnitName(), mbean);
   }

   public KodoPersistenceUnitRuntimeMBean[] getKodoPersistenceUnitRuntimes() {
      List kodoMBeans = new ArrayList();
      Iterator var2 = this.puRuntimes.values().iterator();

      while(var2.hasNext()) {
         PersistenceUnitRuntimeMBean each = (PersistenceUnitRuntimeMBean)var2.next();
         if (each instanceof KodoPersistenceUnitRuntimeMBean) {
            kodoMBeans.add((KodoPersistenceUnitRuntimeMBean)each);
         }
      }

      return (KodoPersistenceUnitRuntimeMBean[])kodoMBeans.toArray(new KodoPersistenceUnitRuntimeMBean[kodoMBeans.size()]);
   }

   public KodoPersistenceUnitRuntimeMBean getKodoPersistenceUnitRuntime(String name) {
      PersistenceUnitRuntimeMBean result = (PersistenceUnitRuntimeMBean)this.puRuntimes.get(name);
      return result != null && result instanceof KodoPersistenceUnitRuntimeMBean ? (KodoPersistenceUnitRuntimeMBean)result : null;
   }

   public void addPersistenceUnit(PersistenceUnitRuntimeMBean mbean) {
      this.puRuntimes.put(mbean.getPersistenceUnitName(), mbean);
   }

   public PersistenceUnitRuntimeMBean[] getPersistenceUnitRuntimes() {
      return (PersistenceUnitRuntimeMBean[])this.puRuntimes.values().toArray(new PersistenceUnitRuntimeMBean[this.puRuntimes.size()]);
   }

   public PersistenceUnitRuntimeMBean getPersistenceUnitRuntime(String name) {
      return (PersistenceUnitRuntimeMBean)this.puRuntimes.get(name);
   }

   public SpringRuntimeMBean getSpringRuntimeMBean() {
      return this.springRuntimeMBean;
   }

   public void setSpringRuntimeMBean(SpringRuntimeMBean mbean) {
      this.springRuntimeMBean = mbean;
   }

   public CoherenceClusterRuntimeMBean getCoherenceClusterRuntime() {
      return this.coherenceClusterRuntimeMBean;
   }

   public void setCoherenceClusterRuntime(CoherenceClusterRuntimeMBean mbean) {
      this.coherenceClusterRuntimeMBean = mbean;
   }

   public WseeClientRuntimeMBean[] getWseeClientRuntimes() {
      synchronized(this.wseeClientRuntimes) {
         return (WseeClientRuntimeMBean[])this.wseeClientRuntimes.toArray(new WseeClientRuntimeMBean[this.wseeClientRuntimes.size()]);
      }
   }

   public WseeClientRuntimeMBean lookupWseeClientRuntime(String rawClientId) {
      WseeClientRuntimeMBean client = null;
      synchronized(this.wseeClientRuntimes) {
         Iterator var4 = this.wseeClientRuntimes.iterator();

         while(var4.hasNext()) {
            WseeClientRuntimeMBean temp = (WseeClientRuntimeMBean)var4.next();
            if (temp.getName().equals(rawClientId)) {
               client = temp;
               break;
            }
         }

         return client;
      }
   }

   public void addWseeClientRuntime(WseeClientRuntimeMBean mbean) {
      synchronized(this.wseeClientRuntimes) {
         this.wseeClientRuntimes.add(mbean);
      }
   }

   public void removeWseeClientRuntime(WseeClientRuntimeMBean mbean) {
      synchronized(this.wseeClientRuntimes) {
         this.wseeClientRuntimes.remove(mbean);
      }
   }

   public WseeV2RuntimeMBean[] getWseeV2Runtimes() {
      synchronized(this.wseeV2Runtimes) {
         return (WseeV2RuntimeMBean[])this.wseeV2Runtimes.toArray(new WseeV2RuntimeMBean[this.wseeV2Runtimes.size()]);
      }
   }

   public WseeV2RuntimeMBean lookupWseeV2Runtime(String name) {
      WseeV2RuntimeMBean mbean = null;
      synchronized(this.wseeV2Runtimes) {
         Iterator var4 = this.wseeV2Runtimes.iterator();

         while(var4.hasNext()) {
            WseeV2RuntimeMBean temp = (WseeV2RuntimeMBean)var4.next();
            if (temp.getName().equals(name)) {
               mbean = temp;
               break;
            }
         }

         return mbean;
      }
   }

   public void addWseeV2Runtime(WseeV2RuntimeMBean mbean) {
      synchronized(this.wseeV2Runtimes) {
         this.wseeV2Runtimes.add(mbean);
      }
   }

   public void removeWseeV2Runtime(WseeV2RuntimeMBean mbean) {
      synchronized(this.wseeV2Runtimes) {
         this.wseeV2Runtimes.remove(mbean);
      }
   }

   public WseeClientConfigurationRuntimeMBean[] getWseeClientConfigurationRuntimes() {
      synchronized(this.wseeClientConfigurationRuntimes) {
         return (WseeClientConfigurationRuntimeMBean[])this.wseeClientConfigurationRuntimes.toArray(new WseeClientConfigurationRuntimeMBean[this.wseeClientConfigurationRuntimes.size()]);
      }
   }

   public WseeClientConfigurationRuntimeMBean lookupWseeClientConfigurationRuntime(String name) {
      WseeClientConfigurationRuntimeMBean mbean = null;
      synchronized(this.wseeClientConfigurationRuntimes) {
         Iterator var4 = this.wseeClientConfigurationRuntimes.iterator();

         while(var4.hasNext()) {
            WseeClientConfigurationRuntimeMBean temp = (WseeClientConfigurationRuntimeMBean)var4.next();
            if (temp.getName().equals(name)) {
               mbean = temp;
               break;
            }
         }

         return mbean;
      }
   }

   public void addWseeClientConfigurationRuntime(WseeClientConfigurationRuntimeMBean mbean) {
      synchronized(this.wseeClientConfigurationRuntimes) {
         this.wseeClientConfigurationRuntimes.add(mbean);
      }
   }

   public void removeWseeClientConfigurationRuntime(WseeClientConfigurationRuntimeMBean mbean) {
      synchronized(this.wseeClientConfigurationRuntimes) {
         this.wseeClientConfigurationRuntimes.remove(mbean);
      }
   }

   public HealthState getHealthState() {
      HealthStateBuilder healthStateBuilder = new HealthStateBuilder();
      EJBRuntimeMBean[] var2 = this.getEJBRuntimes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         EJBRuntimeMBean ejbRuntime = var2[var4];
         if (ejbRuntime instanceof HealthFeedback) {
            HealthState ejbHealthState = ((HealthFeedback)ejbRuntime).getHealthState();
            healthStateBuilder.append(ejbHealthState);
         }
      }

      return healthStateBuilder.get();
   }
}

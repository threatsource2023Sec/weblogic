package weblogic.ejb.container.deployer;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.ScheduleExpression;
import javax.ejb.TimerConfig;
import weblogic.descriptor.DescriptorBean;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.compliance.ComplianceException;
import weblogic.ejb.container.compliance.EJBComplianceChecker;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.compliance.TimeoutCheckHelper;
import weblogic.ejb.container.dd.ClusteringDescriptor;
import weblogic.ejb.container.deployer.mbimpl.SecurityRoleRefImpl;
import weblogic.ejb.container.interfaces.CachingDescriptor;
import weblogic.ejb.container.interfaces.IIOPSecurityDescriptor;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.NamedMethodBean;
import weblogic.j2ee.descriptor.PersistenceContextRefBean;
import weblogic.j2ee.descriptor.PersistenceUnitRefBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.RunAsBean;
import weblogic.j2ee.descriptor.SecurityIdentityBean;
import weblogic.j2ee.descriptor.SecurityRoleRefBean;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.StatefulTimeoutBean;
import weblogic.j2ee.descriptor.TimerBean;
import weblogic.j2ee.descriptor.TimerScheduleBean;
import weblogic.j2ee.descriptor.wl.BusinessInterfaceJndiNameMapBean;
import weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.EntityDescriptorBean;
import weblogic.j2ee.descriptor.wl.InvalidationTargetBean;
import weblogic.j2ee.descriptor.wl.JndiBindingBean;
import weblogic.j2ee.descriptor.wl.MessageDrivenDescriptorBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean;
import weblogic.j2ee.descriptor.wl.ServiceReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.TimerDescriptorBean;
import weblogic.j2ee.descriptor.wl.TransportRequirementsBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.j2ee.descriptor.wl60.BaseWeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean;
import weblogic.logging.Loggable;
import weblogic.utils.Debug;

public final class CompositeDescriptor {
   private final EnterpriseBeanBean enterpriseBeanBean;
   private final EjbDescriptorBean ejbDescriptorBean;
   private BaseWeblogicRdbmsBeanBean wlRdbmsBeanBean;
   private WeblogicEnterpriseBeanBean wlEnterpriseBeanBean;
   private Map ejbReferencesMap;
   private Map ejbLocalReferencesMap;
   private Map jndiNamesMap;
   private static final int EJB_STATELESS_SESSION = 1;
   private static final int EJB_STATEFUL_SESSION = 2;
   private static final int EJB_SINGLETON_SESSION = 3;
   private static final int EJB_ENTITY = 4;
   private static final int EJB_MESSAGE_DRIVEN = 5;
   private int beanType = -1;

   public CompositeDescriptor(EnterpriseBeanBean bean, WeblogicEnterpriseBeanBean wlBean, EjbDescriptorBean desc) throws WLDeploymentException {
      this.enterpriseBeanBean = bean;
      this.ejbDescriptorBean = desc;
      this.wlEnterpriseBeanBean = wlBean;
      this.init();
   }

   public CompositeDescriptor(EnterpriseBeanBean bean, EjbDescriptorBean desc) throws WLDeploymentException {
      this.enterpriseBeanBean = bean;
      this.ejbDescriptorBean = desc;
      this.init();
   }

   public boolean isEJB30() {
      return this.getEJBDescriptor().isEjb30();
   }

   public boolean isVersionGreaterThan30() {
      return this.getEJBDescriptor().isVersionGreaterThan30();
   }

   private void init() throws WLDeploymentException {
      this.initializeBeans(this.getBean(), this.getEJBDescriptor());
      Debug.assertion(this.getBean().getEjbName() != null);
      Debug.assertion(this.getWlBean() != null);
      Debug.assertion(this.getWlBean().getEjbName() != null);
      completeBeans(this.getEJBDescriptor());
      this.initializeType();
      this.initializeEJBReferences();
      this.initializeEJBLocalReferences();
      this.validateServiceRefsAndDescriptions();
   }

   private void initializeType() throws WLDeploymentException {
      if (this.getBean() instanceof EntityBeanBean) {
         this.beanType = 4;
      } else if (this.getBean() instanceof MessageDrivenBeanBean) {
         this.beanType = 5;
      } else {
         SessionBeanBean smb = (SessionBeanBean)this.getBean();
         if ("Stateful".equalsIgnoreCase(smb.getSessionType())) {
            this.beanType = 2;
         } else if ("Singleton".equalsIgnoreCase(smb.getSessionType())) {
            this.beanType = 3;
         } else {
            if (!"Stateless".equalsIgnoreCase(smb.getSessionType())) {
               throw new AssertionError("Unknown session bean type : " + smb.getSessionType());
            }

            this.beanType = 1;
         }
      }

      Loggable l;
      if (!this.isEntity() && this.getWlBean().isEntityDescriptorSet()) {
         l = EJBLogger.logmismatchBetweenEJBNamesLoggable(this.getWlBean().getEjbName());
         throw new WLDeploymentException(l.getMessageText());
      } else if (!this.isStatelessSession() && this.getWlBean().isStatelessSessionDescriptorSet()) {
         l = EJBLogger.logmismatchBetweenslsbEJBNamesLoggable(this.getWlBean().getEjbName());
         throw new WLDeploymentException(l.getMessageText());
      } else if (!this.isStatefulSession() && this.getWlBean().isStatefulSessionDescriptorSet()) {
         l = EJBLogger.logmismatchBetweensfsbEJBNamesLoggable(this.getWlBean().getEjbName());
         throw new WLDeploymentException(l.getMessageText());
      } else if (!this.isSingletonSession() && this.getWlBean().isSingletonSessionDescriptorSet()) {
         l = EJBLogger.logMismatchBetweenSingletonEJBNamesLoggable(this.getWlBean().getEjbName());
         throw new WLDeploymentException(l.getMessageText());
      } else if (!this.isMessageDriven() && this.getWlBean().isMessageDrivenDescriptorSet()) {
         l = EJBLogger.logmismatchBetweenmdbEJBNamesLoggable(this.getWlBean().getEjbName());
         throw new WLDeploymentException(l.getMessageText());
      }
   }

   private void initializeBeans(EnterpriseBeanBean bean, EjbDescriptorBean desc) throws WLDeploymentException {
      String n = bean.getEjbName();
      WeblogicEnterpriseBeanBean[] var4 = desc.getWeblogicEjbJarBean().getWeblogicEnterpriseBeans();
      int var5 = var4.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         WeblogicEnterpriseBeanBean webb = var4[var6];
         if (webb.getEjbName().equals(n)) {
            this.wlEnterpriseBeanBean = webb;
            break;
         }
      }

      if (this.wlEnterpriseBeanBean == null) {
         throw new WLDeploymentException(this.getFmt().CANNOT_FIND_WL_DESCRIPTOR_FOR_EJB(n));
      } else {
         if (bean instanceof EntityBeanBean) {
            WeblogicRdbmsJarBean[] var12 = desc.getWeblogicRdbms11JarBeans();
            var5 = var12.length;

            int var9;
            int var10;
            for(var6 = 0; var6 < var5; ++var6) {
               WeblogicRdbmsJarBean wrjb = var12[var6];
               WeblogicRdbmsBeanBean[] var8 = wrjb.getWeblogicRdbmsBeans();
               var9 = var8.length;

               for(var10 = 0; var10 < var9; ++var10) {
                  BaseWeblogicRdbmsBeanBean rdbmsBean = var8[var10];
                  if (n.equals(rdbmsBean.getEjbName())) {
                     this.wlRdbmsBeanBean = rdbmsBean;
                     break;
                  }
               }
            }

            if (null == this.wlRdbmsBeanBean) {
               weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean[] var13 = desc.getWeblogicRdbmsJarBeans();
               var5 = var13.length;

               for(var6 = 0; var6 < var5; ++var6) {
                  weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean wrjb = var13[var6];
                  weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBean[] var16 = wrjb.getWeblogicRdbmsBeans();
                  var9 = var16.length;

                  for(var10 = 0; var10 < var9; ++var10) {
                     BaseWeblogicRdbmsBeanBean rdbmsBean = var16[var10];
                     if (n.equals(rdbmsBean.getEjbName())) {
                        this.wlRdbmsBeanBean = rdbmsBean;
                        break;
                     }
                  }
               }
            }
         }

      }
   }

   public EjbDescriptorBean getEJBDescriptor() {
      return this.ejbDescriptorBean;
   }

   public EnterpriseBeanBean getBean() {
      return this.enterpriseBeanBean;
   }

   public BaseWeblogicRdbmsBeanBean getRDBMSBean() {
      return this.wlRdbmsBeanBean;
   }

   public void setRDBMSBean(BaseWeblogicRdbmsBeanBean bean) {
      this.wlRdbmsBeanBean = bean;
   }

   public WeblogicEnterpriseBeanBean getWlBean() {
      return this.wlEnterpriseBeanBean;
   }

   public String getHomeInterfaceName() {
      if (this.isSession()) {
         return ((SessionBeanBean)this.getBean()).getHome();
      } else if (this.isEntity()) {
         return ((EntityBeanBean)this.getBean()).getHome();
      } else {
         throw new AssertionError("Trying to get the home interface of a MessageDriven Bean");
      }
   }

   public String getRemoteInterfaceName() {
      if (this.isSession()) {
         return ((SessionBeanBean)this.getBean()).getRemote();
      } else if (this.isEntity()) {
         return ((EntityBeanBean)this.getBean()).getRemote();
      } else {
         throw new AssertionError("Trying to get the remote interface of a MessageDriven Bean");
      }
   }

   public String getLocalHomeInterfaceName() {
      if (this.isSession()) {
         return ((SessionBeanBean)this.getBean()).getLocalHome();
      } else if (this.isEntity()) {
         return ((EntityBeanBean)this.getBean()).getLocalHome();
      } else {
         throw new AssertionError("Trying to get the local home interface of a MessageDriven Bean");
      }
   }

   public String getLocalInterfaceName() {
      if (this.isSession()) {
         return ((SessionBeanBean)this.getBean()).getLocal();
      } else if (this.isEntity()) {
         return ((EntityBeanBean)this.getBean()).getLocal();
      } else {
         throw new AssertionError("Trying to get the local interface of a MessageDriven Bean");
      }
   }

   public String[] getBusinessRemotes() {
      return ((SessionBeanBean)this.getBean()).getBusinessRemotes();
   }

   public String[] getBusinessLocals() {
      return ((SessionBeanBean)this.getBean()).getBusinessLocals();
   }

   public String getMessagingTypeName() {
      return this.isMessageDriven() ? ((MessageDrivenBeanBean)this.getBean()).getMessagingType() : null;
   }

   public String getServiceEndpointName() {
      return this.isSession() ? ((SessionBeanBean)this.getBean()).getServiceEndpoint() : null;
   }

   public String getEJBClassName() {
      return this.getBean().getEjbClass();
   }

   public String getJNDIName() {
      if (!this.isMessageDriven()) {
         String mappedName;
         String homeName;
         if (this.isSession()) {
            SessionBeanBean sbb = (SessionBeanBean)this.getBean();
            mappedName = sbb.getMappedName();
            homeName = sbb.getHome();
         } else {
            EntityBeanBean ebb = (EntityBeanBean)this.getBean();
            mappedName = ebb.getMappedName();
            homeName = ebb.getHome();
         }

         JndiBindingBean binding = this.getWlBean().lookupJndiBinding(homeName);
         if (binding != null) {
            return binding.getJndiName();
         }

         if (mappedName != null && homeName != null) {
            return mappedName + "#" + homeName;
         }

         if (this.getWlBean().getJNDIName() != null && homeName != null) {
            return this.getWlBean().getJNDIName();
         }
      }

      return null;
   }

   public String getBusinessJNDIName(Class iface) {
      if (this.jndiNamesMap == null) {
         this.jndiNamesMap = new HashMap();
         SessionBeanBean sbb = (SessionBeanBean)this.getBean();
         int var5;
         if (sbb.getBusinessRemotes() != null && sbb.getMappedName() != null) {
            String[] var3 = sbb.getBusinessRemotes();
            int var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
               String remoteBI = var3[var5];
               this.jndiNamesMap.put(remoteBI, sbb.getMappedName() + "#" + remoteBI);
            }
         }

         BusinessInterfaceJndiNameMapBean[] oldbiMaps = null;
         if (this.isStatefulSession()) {
            oldbiMaps = this.getWlBean().getStatefulSessionDescriptor().getBusinessInterfaceJndiNameMaps();
         } else if (this.isStatelessSession()) {
            oldbiMaps = this.getWlBean().getStatelessSessionDescriptor().getBusinessInterfaceJndiNameMaps();
         }

         int var11;
         if (oldbiMaps != null) {
            BusinessInterfaceJndiNameMapBean[] var9 = oldbiMaps;
            var5 = oldbiMaps.length;

            for(var11 = 0; var11 < var5; ++var11) {
               BusinessInterfaceJndiNameMapBean oldbiMap = var9[var11];
               this.jndiNamesMap.put(oldbiMap.getBusinessRemote(), oldbiMap.getJNDIName());
            }
         }

         JndiBindingBean[] var10 = this.getWlBean().getJndiBinding();
         var5 = var10.length;

         for(var11 = 0; var11 < var5; ++var11) {
            JndiBindingBean binding = var10[var11];
            this.jndiNamesMap.put(binding.getClassName(), binding.getJndiName());
         }
      }

      return (String)this.jndiNamesMap.get(iface.getName());
   }

   public String getDispatchPolicy() {
      return this.getWlBean().getDispatchPolicy();
   }

   public boolean getStickToFirstServer() {
      return this.getWlBean().isStickToFirstServer();
   }

   public int getRemoteClientTimeout() {
      return this.getWlBean().getRemoteClientTimeout();
   }

   public String getLocalJNDIName() {
      if (!this.isMessageDriven()) {
         String mappedName;
         String lhomeName;
         if (this.isSession()) {
            SessionBeanBean sbb = (SessionBeanBean)this.getBean();
            mappedName = sbb.getMappedName();
            lhomeName = sbb.getLocalHome();
         } else {
            EntityBeanBean ebb = (EntityBeanBean)this.getBean();
            mappedName = ebb.getMappedName();
            lhomeName = ebb.getLocalHome();
         }

         JndiBindingBean binding = this.getWlBean().lookupJndiBinding(lhomeName);
         if (binding != null) {
            return binding.getJndiName();
         }

         if (mappedName != null && lhomeName != null) {
            return mappedName + "#" + lhomeName;
         }

         if (this.getWlBean().getLocalJNDIName() != null && lhomeName != null) {
            return this.getWlBean().getLocalJNDIName();
         }
      }

      return null;
   }

   public String getDestinationJNDIName() {
      if (!this.isMessageDriven()) {
         return null;
      } else {
         MessageDrivenBeanBean mdb = (MessageDrivenBeanBean)this.getBean();
         if (this.getWlBean().isMessageDrivenDescriptorSet()) {
            MessageDrivenDescriptorBean md = this.getWlBean().getMessageDrivenDescriptor();
            return this.isEJB30() && mdb.getMappedName() != null && md.getDestinationJNDIName() == null ? mdb.getMappedName() : md.getDestinationJNDIName();
         } else {
            return this.isEJB30() && mdb.getMappedName() != null ? mdb.getMappedName() : null;
         }
      }
   }

   public String getEJBName() {
      return this.getBean().getEjbName();
   }

   public Collection getAllEnvironmentEntries() {
      Map map = new HashMap();
      EnvEntryBean[] envEntries = this.getBean().getEnvEntries();
      if (null != envEntries) {
         EnvEntryBean[] var3 = envEntries;
         int var4 = envEntries.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            EnvEntryBean e = var3[var5];
            map.put(e.getEnvEntryName(), e);
         }
      }

      return map.values();
   }

   public Collection getAllEJBReferences() {
      return this.ejbReferencesMap.values();
   }

   public Collection getAllEJBLocalReferences() {
      return this.ejbLocalReferencesMap.values();
   }

   public Collection getAllResourceReferences() {
      Map map = new HashMap();
      ResourceRefBean[] refs = this.getBean().getResourceRefs();
      if (null != refs) {
         ResourceRefBean[] var3 = refs;
         int var4 = refs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ResourceRefBean ref = var3[var5];
            map.put(ref.getResRefName(), ref);
         }
      }

      return map.values();
   }

   public Collection getAllResourceEnvReferences() {
      Map map = new HashMap();
      ResourceEnvRefBean[] refs = this.getBean().getResourceEnvRefs();
      if (null != refs) {
         ResourceEnvRefBean[] var3 = refs;
         int var4 = refs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ResourceEnvRefBean ref = var3[var5];
            map.put(ref.getResourceEnvRefName(), ref);
         }
      }

      return map.values();
   }

   public Collection getAllWlResourceReferences() {
      Map map = new HashMap();
      ResourceDescriptionBean[] refs = this.getWlBean().getResourceDescriptions();
      if (null != refs) {
         ResourceDescriptionBean[] var3 = refs;
         int var4 = refs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ResourceDescriptionBean ref = var3[var5];
            map.put(ref.getResRefName(), ref);
         }
      }

      return map.values();
   }

   public Collection getAllWlResourceEnvReferences() {
      Map map = new HashMap();
      ResourceEnvDescriptionBean[] refs = this.getWlBean().getResourceEnvDescriptions();
      if (null != refs) {
         ResourceEnvDescriptionBean[] var3 = refs;
         int var4 = refs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ResourceEnvDescriptionBean ref = var3[var5];
            map.put(ref.getResourceEnvRefName(), ref);
         }
      }

      return map.values();
   }

   public Collection getAllMessageDestinationReferences() {
      return Arrays.asList(this.getBean().getMessageDestinationRefs());
   }

   public Map getSecurityRoleReferencesMap() {
      SecurityRoleRefBean[] refs = null;
      if (this.isSession()) {
         refs = ((SessionBeanBean)this.getBean()).getSecurityRoleRefs();
      } else if (this.isEntity()) {
         refs = ((EntityBeanBean)this.getBean()).getSecurityRoleRefs();
      } else if (this.isMessageDriven() && this.isVersionGreaterThan30()) {
         refs = ((MessageDrivenBeanBean)this.getBean()).getSecurityRoleRefs();
      }

      Map map = new HashMap();
      if (null != refs) {
         SecurityRoleRefBean[] var3 = refs;
         int var4 = refs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            SecurityRoleRefBean ref = var3[var5];
            map.put(ref.getRoleName(), new SecurityRoleRefImpl(ref));
         }
      }

      return map;
   }

   public PersistenceContextRefBean[] getPersistenceContextRefs() {
      return this.getBean().getPersistenceContextRefs();
   }

   public PersistenceUnitRefBean[] getPersistenceUnitRefs() {
      return this.getBean().getPersistenceUnitRefs();
   }

   public int getTransactionTimeoutSeconds() {
      return this.getWlBean().getTransactionDescriptor().getTransTimeoutSeconds();
   }

   public boolean useCallByReference() {
      return this.getWlBean().isEnableCallByReference();
   }

   public String getNetworkAccessPoint() {
      return this.getWlBean().getNetworkAccessPoint();
   }

   public boolean getClientsOnSameServer() {
      return this.getWlBean().isClientsOnSameServer();
   }

   private int getIdleTimeoutSecondsCache() {
      int secs = -1;
      if (this.isStatefulSession()) {
         secs = this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionCache().getIdleTimeoutSeconds();
      } else if (this.isEntity()) {
         if (this.hasEntityCacheReference()) {
            secs = this.getWlBean().getEntityDescriptor().getEntityCacheRef().getIdleTimeoutSeconds();
         } else if (this.getWlBean().getEntityDescriptor().isEntityCacheSet()) {
            secs = this.getWlBean().getEntityDescriptor().getEntityCache().getIdleTimeoutSeconds();
         }
      }

      return secs;
   }

   public void setIdleTimeoutSecondsCache(int n) {
      if (this.isStatefulSession()) {
         this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionCache().setIdleTimeoutSeconds(n);
      } else if (this.isEntity()) {
         if (this.hasEntityCacheReference()) {
            this.getWlBean().getEntityDescriptor().getEntityCacheRef().setIdleTimeoutSeconds(n);
         } else {
            this.getWlBean().getEntityDescriptor().getEntityCache().setIdleTimeoutSeconds(n);
         }
      }

   }

   private int getIdleTimeoutSecondsPool() {
      int secs = -1;
      if (this.isStatelessSession()) {
         secs = this.getWlBean().getStatelessSessionDescriptor().getPool().getIdleTimeoutSeconds();
      } else if (this.isMessageDriven()) {
         secs = this.getWlBean().getMessageDrivenDescriptor().getPool().getIdleTimeoutSeconds();
      } else if (this.isEntity()) {
         secs = this.getWlBean().getEntityDescriptor().getPool().getIdleTimeoutSeconds();
      }

      return secs;
   }

   public int getSessionTimeoutSeconds() {
      if (!this.isStatefulSession()) {
         throw new UnsupportedOperationException();
      } else {
         return this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionCache().getSessionTimeoutSeconds();
      }
   }

   private int getReadTimeoutSeconds() {
      int readTimeoutSeconds = -1;
      if (this.isEntity()) {
         EntityDescriptorBean ed = this.getWlBean().getEntityDescriptor();
         boolean isSet = false;
         if (this.hasEntityCacheReference()) {
            readTimeoutSeconds = ed.getEntityCacheRef().getReadTimeoutSeconds();
            isSet = ((DescriptorBean)ed.getEntityCacheRef()).isSet("ReadTimeoutSeconds");
         } else {
            readTimeoutSeconds = ed.getEntityCache().getReadTimeoutSeconds();
            isSet = ((DescriptorBean)ed.getEntityCache()).isSet("ReadTimeoutSeconds");
         }

         if (this.getCacheBetweenTransactions() && !isSet) {
            readTimeoutSeconds = 0;
         }
      }

      return readTimeoutSeconds;
   }

   private String getCacheType() {
      return !this.isStatefulSession() ? null : this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionCache().getCacheType();
   }

   private int getMaxBeansInFreePool() {
      if (this.isStatelessSession()) {
         return this.getWlBean().getStatelessSessionDescriptor().getPool().getMaxBeansInFreePool();
      } else if (this.isEntity()) {
         return this.getWlBean().getEntityDescriptor().getPool().getMaxBeansInFreePool();
      } else {
         return this.isMessageDriven() ? this.getWlBean().getMessageDrivenDescriptor().getPool().getMaxBeansInFreePool() : 0;
      }
   }

   public void setMaxBeansInFreePool(int n) {
      if (this.isStatelessSession()) {
         this.getWlBean().getStatelessSessionDescriptor().getPool().setMaxBeansInFreePool(n);
      } else if (this.isEntity()) {
         this.getWlBean().getEntityDescriptor().getPool().setMaxBeansInFreePool(n);
      } else if (this.isMessageDriven()) {
         this.getWlBean().getMessageDrivenDescriptor().getPool().setMaxBeansInFreePool(n);
      }

   }

   private int getInitialBeansInFreePool() {
      if (this.isStatelessSession()) {
         return this.getWlBean().getStatelessSessionDescriptor().getPool().getInitialBeansInFreePool();
      } else if (this.isEntity()) {
         return this.getWlBean().getEntityDescriptor().getPool().getInitialBeansInFreePool();
      } else {
         return this.isMessageDriven() ? this.getWlBean().getMessageDrivenDescriptor().getPool().getInitialBeansInFreePool() : 0;
      }
   }

   private int getMaxBeansInCache() {
      if (this.isEntity() && !this.hasEntityCacheReference()) {
         return this.getWlBean().getEntityDescriptor().getEntityCache().getMaxBeansInCache();
      } else {
         return this.isStatefulSession() ? this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionCache().getMaxBeansInCache() : -1;
      }
   }

   public void setMaxBeansInCache(int n) {
      if (this.isEntity()) {
         this.getWlBean().getEntityDescriptor().getEntityCache().setMaxBeansInCache(n);
      } else if (this.isStatefulSession()) {
         this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionCache().setMaxBeansInCache(n);
      }

   }

   public int getMaxQueriesInCache() {
      return this.isEntity() && !this.hasEntityCacheReference() ? this.getWlBean().getEntityDescriptor().getEntityCache().getMaxQueriesInCache() : -1;
   }

   public String getConcurrencyStrategy() {
      String result = null;
      if (this.isEntity()) {
         if (this.hasEntityCacheReference()) {
            result = this.getWlBean().getEntityDescriptor().getEntityCacheRef().getConcurrencyStrategy();
         } else {
            result = this.getWlBean().getEntityDescriptor().getEntityCache().getConcurrencyStrategy();
         }
      }

      return result;
   }

   public CachingDescriptor getCachingDescriptor() {
      return new CachingDescriptorImpl(this.getMaxBeansInCache(), this.getMaxQueriesInCache(), this.getMaxBeansInFreePool(), this.getInitialBeansInFreePool(), this.getIdleTimeoutSecondsCache(), this.getIdleTimeoutSecondsPool(), this.getCacheType(), this.getReadTimeoutSeconds(), this.getConcurrencyStrategy());
   }

   public IIOPSecurityDescriptor getIIOPSecurityDescriptor() {
      IIOPSecurityDescriptorImpl result = new IIOPSecurityDescriptorImpl();
      if (!this.getWlBean().isIiopSecurityDescriptorSet()) {
         return result;
      } else {
         if (this.getWlBean().getIiopSecurityDescriptor().isTransportRequirementsSet()) {
            TransportRequirementsBean transReqs = this.getWlBean().getIiopSecurityDescriptor().getTransportRequirements();
            result.setTransport_integrity(transReqs.getIntegrity());
            result.setTransport_confidentiality(transReqs.getConfidentiality());
            result.setTransport_client_cert_authentication(transReqs.getClientCertAuthentication());
         }

         result.setClient_authentication(this.getWlBean().getIiopSecurityDescriptor().getClientAuthentication());
         result.setIdentity_assertion(this.getWlBean().getIiopSecurityDescriptor().getIdentityAssertion());
         return result;
      }
   }

   public boolean hasEntityCacheReference() {
      return this.getWlBean().getEntityDescriptor().isEntityCacheRefSet();
   }

   public String getEntityCacheName() {
      return this.getWlBean().getEntityDescriptor().getEntityCacheRef().getEntityCacheName();
   }

   public int getEstimatedBeanSize() {
      return this.getWlBean().getEntityDescriptor().getEntityCacheRef().getEstimatedBeanSize();
   }

   private String getHomeLoadAlgorithm() {
      if (this.isEntity()) {
         return this.getWlBean().getEntityDescriptor().getEntityClustering().getHomeLoadAlgorithm();
      } else if (this.isStatefulSession()) {
         return this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionClustering().getHomeLoadAlgorithm();
      } else if (this.isStatelessSession()) {
         return this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().getHomeLoadAlgorithm();
      } else {
         throw new AssertionError("HomeLoadAlgorithm should only be for Entity, Stateless or Stateful Session");
      }
   }

   public void setHomeLoadAlgorithm(String s) {
      if (this.isEntity()) {
         this.getWlBean().getEntityDescriptor().getEntityClustering().setHomeLoadAlgorithm(s);
      } else if (this.isStatefulSession()) {
         this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionClustering().setHomeLoadAlgorithm(s);
      } else {
         if (!this.isStatelessSession()) {
            throw new AssertionError("HomeLoadAlgorithm should only be for Entity, Stateless or Stateful Session");
         }

         this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().setHomeLoadAlgorithm(s);
      }

   }

   private boolean getHomeIsClusterable() {
      if (this.isEntity()) {
         return this.getWlBean().getEntityDescriptor().getEntityClustering().isHomeIsClusterable();
      } else if (this.isStatefulSession()) {
         return this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionClustering().isHomeIsClusterable();
      } else if (this.isStatelessSession()) {
         return this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().isHomeIsClusterable();
      } else {
         throw new AssertionError("HomeIsClusterable should only be for Entity, Stateless or Stateful Session");
      }
   }

   private boolean getUseServersideStubs() {
      if (this.isEntity()) {
         return this.getWlBean().getEntityDescriptor().getEntityClustering().isUseServersideStubs();
      } else if (this.isStatefulSession()) {
         return this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionClustering().isUseServersideStubs();
      } else if (this.isStatelessSession()) {
         return this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().isUseServersideStubs();
      } else {
         return this.isSingletonSession() ? this.getWlBean().getSingletonSessionDescriptor().getSingletonClustering().isUseServersideStubs() : false;
      }
   }

   public void setHomeIsClusterable(boolean f) {
      if (this.isEntity()) {
         this.getWlBean().getEntityDescriptor().getEntityClustering().setHomeIsClusterable(f);
      } else if (this.isStatefulSession()) {
         this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionClustering().setHomeIsClusterable(f);
      } else if (this.isStatelessSession()) {
         this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().setHomeIsClusterable(f);
      }

   }

   private String getHomeCallRouterClassName() {
      if (this.isEntity()) {
         return this.getWlBean().getEntityDescriptor().getEntityClustering().getHomeCallRouterClassName();
      } else if (this.isStatefulSession()) {
         return this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionClustering().getHomeCallRouterClassName();
      } else if (this.isStatelessSession()) {
         return this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().getHomeCallRouterClassName();
      } else {
         throw new AssertionError("HomeCallRouterClassName should only be for Entity, Stateless or Stateful Session");
      }
   }

   public void setHomeCallRouterClassName(String s) {
      if (this.isEntity()) {
         this.getWlBean().getEntityDescriptor().getEntityClustering().setHomeCallRouterClassName(s);
      } else if (this.isStatefulSession()) {
         this.getWlBean().getStatefulSessionDescriptor().getStatefulSessionClustering().setHomeCallRouterClassName(s);
      } else {
         if (!this.isStatelessSession()) {
            throw new AssertionError("HomeCallRouterClassName should only be for Entity, Stateless or Stateful Session");
         }

         this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().setHomeCallRouterClassName(s);
      }

   }

   private boolean getBeanIsClusterable() {
      if (this.isStatelessSession()) {
         return this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().isStatelessBeanIsClusterable();
      } else if (this.isSingletonSession()) {
         return this.getWlBean().getSingletonSessionDescriptor().getSingletonClustering().isSingletonBeanIsClusterable();
      } else {
         throw new AssertionError("BeanIsClusterable should only be for Stateless or Singleton");
      }
   }

   private String getBeanLoadAlgorithm() {
      if (this.isStatelessSession()) {
         return this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().getStatelessBeanLoadAlgorithm();
      } else if (this.isSingletonSession()) {
         return this.getWlBean().getSingletonSessionDescriptor().getSingletonClustering().getSingletonBeanLoadAlgorithm();
      } else {
         throw new AssertionError("BeanLoadAlgorithm should only be for Stateless or Singleton");
      }
   }

   private String getBeanCallRouterClassName() {
      if (this.isStatelessSession()) {
         return this.getWlBean().getStatelessSessionDescriptor().getStatelessClustering().getStatelessBeanCallRouterClassName();
      } else if (this.isSingletonSession()) {
         return this.getWlBean().getSingletonSessionDescriptor().getSingletonClustering().getSingletonBeanCallRouterClassName();
      } else {
         throw new AssertionError("BeanCallRouterClassName should only be for Stateless or Singleton");
      }
   }

   public ClusteringDescriptor getClusteringDescriptor() {
      ClusteringDescriptor result = new ClusteringDescriptor();
      if (this.isEntity() || this.isStatefulSession() || this.isStatelessSession()) {
         result.setHomeLoadAlgorithm(this.getHomeLoadAlgorithm());
         result.setHomeIsClusterable(this.getHomeIsClusterable());
         result.setHomeCallRouterClassName(this.getHomeCallRouterClassName());
         result.setUseServersideStubs(this.getUseServersideStubs());
      }

      if (this.isSingletonSession() || this.isStatelessSession()) {
         result.setUseServersideStubs(this.getUseServersideStubs());
         result.setBeanIsClusterable(this.getBeanIsClusterable());
         result.setBeanLoadAlgorithm(this.getBeanLoadAlgorithm());
         result.setBeanCallRouterClassName(this.getBeanCallRouterClassName());
      }

      return result;
   }

   public StatefulTimeoutConfiguration getStatefulTimeoutConfiguration() {
      if (!this.isStatefulSession()) {
         throw new IllegalArgumentException();
      } else {
         StatefulTimeoutBean stb = ((SessionBeanBean)this.getBean()).getStatefulTimeout();
         return stb == null ? null : new StatefulTimeoutConfiguration(stb);
      }
   }

   public boolean isEntity() {
      return 4 == this.beanType;
   }

   public boolean isStatefulSession() {
      return 2 == this.beanType;
   }

   public boolean isStatelessSession() {
      return 1 == this.beanType;
   }

   public boolean isSingletonSession() {
      return 3 == this.beanType;
   }

   public boolean isSession() {
      return 1 == this.beanType || 3 == this.beanType || 2 == this.beanType;
   }

   public boolean isMessageDriven() {
      return 5 == this.beanType;
   }

   public Map getAllEJBReferenceJNDINames() {
      Map result = new HashMap();
      EjbReferenceDescriptionBean[] erdbs = this.getWlBean().getEjbReferenceDescriptions();
      if (null != erdbs) {
         EjbReferenceDescriptionBean[] var3 = erdbs;
         int var4 = erdbs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            EjbReferenceDescriptionBean erdb = var3[var5];
            if (this.ejbReferencesMap.containsKey(erdb.getEjbRefName())) {
               result.put(erdb.getEjbRefName(), erdb.getJNDIName());
            }
         }
      }

      return result;
   }

   public Map getAllEJBLocalReferenceJNDINames() {
      Map result = new HashMap();
      EjbReferenceDescriptionBean[] erdbs = this.getWlBean().getEjbReferenceDescriptions();
      if (null != erdbs) {
         EjbReferenceDescriptionBean[] var3 = erdbs;
         int var4 = erdbs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            EjbReferenceDescriptionBean erdb = var3[var5];
            if (this.ejbLocalReferencesMap.containsKey(erdb.getEjbRefName())) {
               result.put(erdb.getEjbRefName(), erdb.getJNDIName());
            }
         }
      }

      return result;
   }

   private RunAsBean getRunAsBean() {
      SecurityIdentityBean si = null;
      if (this.isEntity()) {
         si = ((EntityBeanBean)this.getBean()).getSecurityIdentity();
      } else if (this.isSession()) {
         si = ((SessionBeanBean)this.getBean()).getSecurityIdentity();
      } else if (this.isMessageDriven()) {
         si = ((MessageDrivenBeanBean)this.getBean()).getSecurityIdentity();
      }

      return si != null ? si.getRunAs() : null;
   }

   public String getRunAsRoleName() {
      RunAsBean ra = this.getRunAsBean();
      return ra != null ? ra.getRoleName() : null;
   }

   public String getRunAsIdentityPrincipal() {
      return this.getWlBean().getRunAsPrincipalName();
   }

   public String getCreateAsPrincipalName() {
      return this.getWlBean().getCreateAsPrincipalName();
   }

   public String getRemoveAsPrincipalName() {
      return this.getWlBean().getRemoveAsPrincipalName();
   }

   public String getPassivateAsPrincipalName() {
      return this.getWlBean().getPassivateAsPrincipalName();
   }

   public NamedMethodBean getTimeoutMethod() {
      if (this.isSession()) {
         return ((SessionBeanBean)this.getBean()).getTimeoutMethod();
      } else {
         return this.isMessageDriven() ? ((MessageDrivenBeanBean)this.getBean()).getTimeoutMethod() : null;
      }
   }

   public Map getAutoTimers() throws ComplianceException {
      TimerBean[] timers = null;
      if (this.isSession()) {
         timers = ((SessionBeanBean)this.getBean()).getTimers();
      } else if (this.isMessageDriven()) {
         timers = ((MessageDrivenBeanBean)this.getBean()).getTimers();
      }

      if (timers == null) {
         return Collections.emptyMap();
      } else {
         Map timerMap = new HashMap();
         TimerBean[] var3 = timers;
         int var4 = timers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TimerBean tb = var3[var5];
            TimerScheduleBean tsb = tb.getSchedule();
            ScheduleExpression se = new ScheduleExpression();
            se.dayOfMonth(tsb.getDayOfMonth());
            se.dayOfWeek(tsb.getDayOfWeek());
            se.end(tb.getEnd());
            se.start(tb.getStart());
            se.hour(tsb.getHour());
            se.minute(tsb.getMinute());
            se.month(tsb.getMonth());
            se.second(tsb.getSecond());
            se.year(tsb.getYear());
            se.timezone(tb.getTimezone());
            if (!EJBComplianceChecker.isNeedCheck) {
               TimeoutCheckHelper.validateSingleScheduleExpressionSimply(se);
            }

            TimerConfig tc = new TimerConfig(tb.getInfo(), tb.getPersistent());
            Map.Entry timerData = new AbstractMap.SimpleEntry(se, tc);
            timerMap.put(timerData, tb.getTimeoutMethod());
         }

         return timerMap;
      }
   }

   public String getTimerStoreName() {
      TimerDescriptorBean td = null;
      if (this.isEntity()) {
         if (!this.getWlBean().getEntityDescriptor().isTimerDescriptorSet()) {
            return null;
         }

         td = this.getWlBean().getEntityDescriptor().getTimerDescriptor();
      } else if (this.isMessageDriven()) {
         if (!this.getWlBean().getMessageDrivenDescriptor().isTimerDescriptorSet()) {
            return null;
         }

         td = this.getWlBean().getMessageDrivenDescriptor().getTimerDescriptor();
      } else if (this.isStatelessSession()) {
         if (!this.getWlBean().getStatelessSessionDescriptor().isTimerDescriptorSet()) {
            return null;
         }

         td = this.getWlBean().getStatelessSessionDescriptor().getTimerDescriptor();
      } else if (this.isSingletonSession()) {
         if (!this.getWlBean().getSingletonSessionDescriptor().isTimerDescriptorSet()) {
            return null;
         }

         td = this.getWlBean().getSingletonSessionDescriptor().getTimerDescriptor();
      }

      return td == null ? null : td.getPersistentStoreLogicalName();
   }

   private static WeblogicEnterpriseBeanBean findWLBean(String ejbName, WeblogicEnterpriseBeanBean[] beans) {
      WeblogicEnterpriseBeanBean[] var2 = beans;
      int var3 = beans.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         WeblogicEnterpriseBeanBean bean = var2[var4];
         if (ejbName.equals(bean.getEjbName())) {
            return bean;
         }
      }

      return null;
   }

   public static EnterpriseBeanBean[] getEnterpriseBeans(EjbJarBean jar) {
      return getEnterpriseBeans(jar.getEnterpriseBeans());
   }

   public static EnterpriseBeanBean[] getEnterpriseBeans(EnterpriseBeansBean ent) {
      SessionBeanBean[] sbbs = ent.getSessions();
      EntityBeanBean[] ebbs = ent.getEntities();
      MessageDrivenBeanBean[] mdbbs = ent.getMessageDrivens();
      EnterpriseBeanBean[] result = new EnterpriseBeanBean[sbbs.length + ebbs.length + mdbbs.length];
      int i = false;

      int i;
      for(i = 0; i < sbbs.length; ++i) {
         result[i] = sbbs[i];
      }

      EntityBeanBean[] var6 = ebbs;
      int var7 = ebbs.length;

      int var8;
      for(var8 = 0; var8 < var7; ++var8) {
         EntityBeanBean ebb = var6[var8];
         result[i++] = ebb;
      }

      MessageDrivenBeanBean[] var11 = mdbbs;
      var7 = mdbbs.length;

      for(var8 = 0; var8 < var7; ++var8) {
         MessageDrivenBeanBean mdbb = var11[var8];
         result[i++] = mdbb;
      }

      return result;
   }

   private static void completeBeans(EjbDescriptorBean desc) throws WLDeploymentException {
      if (null == desc.getEjbJarBean()) {
         desc.createEjbJarBean().createEnterpriseBeans();
      }

      if (null == desc.getWeblogicEjbJarBean()) {
         desc.createWeblogicEjbJarBean();
      }

      EnterpriseBeanBean[] var1 = getEnterpriseBeans(desc.getEjbJarBean());
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EnterpriseBeanBean bean = var1[var3];
         WeblogicEnterpriseBeanBean wlBean = findWLBean(bean.getEjbName(), desc.getWeblogicEjbJarBean().getWeblogicEnterpriseBeans());
         if (wlBean == null) {
            throw new WLDeploymentException(EJBComplianceTextFormatter.getInstance().CANNOT_FIND_WL_DESCRIPTOR_FOR_EJB(bean.getEjbName()));
         }
      }

   }

   public boolean getEntityAlwaysUsesTransaction() {
      return null == this.getEJBDescriptor().getWeblogicEjbJarBean() ? false : this.getEJBDescriptor().getWeblogicEjbJarBean().getWeblogicCompatibility().isEntityAlwaysUsesTransaction();
   }

   public void setEJBName(String name) {
      this.getBean().setEjbName(name);
      this.getWlBean().setEjbName(name);
      if (null != this.getRDBMSBean()) {
         this.getRDBMSBean().setEjbName(name);
      }

   }

   public boolean isBeanManagedPersistence() {
      return "bean".equalsIgnoreCase(((EntityBeanBean)this.getBean()).getPersistenceType());
   }

   public boolean getCacheBetweenTransactions() {
      if (this.hasEntityCacheReference()) {
         return this.getWlBean().getEntityDescriptor().getEntityCacheRef().isCacheBetweenTransactions();
      } else {
         PersistenceBean persist = this.getWlBean().getEntityDescriptor().getPersistence();
         Debug.assertion(persist != null);
         return this.getWlBean().getEntityDescriptor().getEntityCache().isCacheBetweenTransactions();
      }
   }

   public boolean getDisableReadyIntances() {
      if (!this.hasEntityCacheReference()) {
         PersistenceBean persist = this.getWlBean().getEntityDescriptor().getPersistence();
         Debug.assertion(persist != null);
         return this.getWlBean().getEntityDescriptor().getEntityCache().isDisableReadyInstances();
      } else {
         return false;
      }
   }

   public String getIsModifiedMethodName() {
      return this.getWlBean().getEntityDescriptor().getPersistence().getIsModifiedMethodName();
   }

   public boolean getDelayUpdatesUntilEndOfTx() {
      return this.getWlBean().getEntityDescriptor().getPersistence().isDelayUpdatesUntilEndOfTx();
   }

   public String getPersistenceUseIdentifier() {
      if (this.getWlBean().getEntityDescriptor().isPersistenceSet()) {
         PersistenceBean perBean = this.getWlBean().getEntityDescriptor().getPersistence();
         if (perBean.isPersistenceUseSet()) {
            return perBean.getPersistenceUse().getTypeIdentifier();
         }
      }

      return null;
   }

   public String getPersistenceUseVersion() {
      if (this.getWlBean().getEntityDescriptor().isPersistenceSet()) {
         PersistenceBean perBean = this.getWlBean().getEntityDescriptor().getPersistence();
         if (perBean.isPersistenceUseSet()) {
            return perBean.getPersistenceUse().getTypeVersion();
         }
      }

      return null;
   }

   public String getPersistenceUseStorage() {
      if (this.getWlBean().getEntityDescriptor().isPersistenceSet()) {
         PersistenceBean perBean = this.getWlBean().getEntityDescriptor().getPersistence();
         if (perBean.isPersistenceUseSet()) {
            return perBean.getPersistenceUse().getTypeStorage();
         }
      }

      return null;
   }

   public boolean getFindersLoadBean() {
      return this.getWlBean().getEntityDescriptor().getPersistence().isFindersLoadBean();
   }

   public EnvEntryBean createEnvironmentEntry() {
      return this.getBean().createEnvEntry();
   }

   public String getInvalidationTargetEJBName() {
      InvalidationTargetBean mb = this.getWlBean().getEntityDescriptor().getInvalidationTarget();
      return this.getWlBean().getEntityDescriptor().isInvalidationTargetSet() ? mb.getEjbName() : null;
   }

   public boolean isDynamicQueriesEnabled() {
      return this.getWlBean().isEntityDescriptorSet() && this.getWlBean().getEntityDescriptor().isEnableDynamicQueries();
   }

   public boolean isClusteredTimers() {
      return "Clustered".equals(this.getEJBDescriptor().getWeblogicEjbJarBean().getTimerImplementation());
   }

   private void initializeEJBReferences() throws WLDeploymentException {
      this.ejbReferencesMap = new HashMap();
      EjbRefBean[] var1 = this.getBean().getEjbRefs();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EjbRefBean ref = var1[var3];
         if (this.ejbReferencesMap.containsKey(ref.getEjbRefName())) {
            throw new WLDeploymentException(this.getFmt().noDuplicateEjbRefNamesAllowed(this.getEJBName(), ref.getEjbRefName()));
         }

         this.ejbReferencesMap.put(ref.getEjbRefName(), ref);
      }

   }

   private void initializeEJBLocalReferences() throws WLDeploymentException {
      this.ejbLocalReferencesMap = new HashMap();
      EjbLocalRefBean[] var1 = this.getBean().getEjbLocalRefs();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EjbLocalRefBean lref = var1[var3];
         if (this.ejbLocalReferencesMap.containsKey(lref.getEjbRefName())) {
            throw new WLDeploymentException(this.getFmt().noDuplicateEjbRefNamesAllowed(this.getEJBName(), lref.getEjbRefName()));
         }

         this.ejbLocalReferencesMap.put(lref.getEjbRefName(), lref);
      }

   }

   private void validateServiceRefsAndDescriptions() throws WLDeploymentException {
      Map allServiceRefs = new HashMap();
      ServiceRefBean[] var2 = this.getBean().getServiceRefs();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         ServiceRefBean sref = var2[var4];
         if (allServiceRefs.containsKey(sref.getServiceRefName())) {
            throw new WLDeploymentException(this.getFmt().noDuplicateServiceRefNamesAllowed(this.getEJBName(), sref.getServiceRefName()));
         }

         allServiceRefs.put(sref.getServiceRefName(), sref);
      }

      Map map = new HashMap();
      ServiceReferenceDescriptionBean[] var8 = this.getWlBean().getServiceReferenceDescriptions();
      var4 = var8.length;

      for(int var9 = 0; var9 < var4; ++var9) {
         ServiceReferenceDescriptionBean sref = var8[var9];
         if (map.containsKey(sref.getServiceRefName())) {
            throw new WLDeploymentException(this.getFmt().noDuplicateServiceReferenceDescriptionNamesAllowed(this.getEJBName(), sref.getServiceRefName()));
         }

         if (!allServiceRefs.containsKey(sref.getServiceRefName())) {
            throw new WLDeploymentException(this.getFmt().noServiceRefForReferenceDescription(this.getEJBName(), sref.getServiceRefName()));
         }

         map.put(sref.getServiceRefName(), sref);
      }

   }

   private EJBComplianceTextFormatter getFmt() {
      return EJBComplianceTextFormatter.getInstance();
   }
}

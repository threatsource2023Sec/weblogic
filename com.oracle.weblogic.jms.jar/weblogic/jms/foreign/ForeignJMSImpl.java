package weblogic.jms.foreign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.ForeignDestinationBean;
import weblogic.j2ee.descriptor.wl.ForeignJNDIObjectBean;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.PropertyBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSException;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.utils.BeanListenerCustomizer;
import weblogic.management.utils.GenericBeanListener;

public class ForeignJMSImpl implements JMSModuleManagedEntity, BeanListenerCustomizer {
   private ForeignServerBean foreignServerBean;
   private HashMap factoryImpls;
   private HashMap destinationImpls;
   private JMSService jmsService = JMSService.getJMSServiceWithModuleException();
   private String initialContextFactory;
   private String connectionURL;
   private transient PropertyBean[] jndiPropertyBeans;
   private boolean defaultTargetingEnabled;
   private transient GenericBeanListener foreignServerBeanListener;
   private transient List jndiPropertyBeanListeners = new ArrayList();
   private static final HashMap foreignServerBeanSignatures = new HashMap();
   private static final HashMap foreignServerAdditionSignatures = new HashMap();
   private static final HashMap jndiPropertyBeanSignatures = new HashMap();
   private String name;
   private boolean needRebind;

   public ForeignJMSImpl(ForeignServerBean paramFSBean, String moduleName) throws ModuleException {
      this.foreignServerBean = paramFSBean;
      this.name = JMSBeanHelper.getDecoratedName(moduleName, this.foreignServerBean.getName());
   }

   private void validateJNDI() throws ModuleException {
      Iterator factories = this.factoryImpls.values().iterator();

      while(factories.hasNext()) {
         ForeignJNDIObjectImpl impl = (ForeignJNDIObjectImpl)factories.next();
         impl.validateJNDI();
      }

      Iterator destinations = this.destinationImpls.values().iterator();

      while(destinations.hasNext()) {
         ForeignJNDIObjectImpl impl = (ForeignJNDIObjectImpl)destinations.next();
         impl.validateJNDI();
      }

   }

   private void bind(boolean rebind) throws JMSException {
      Iterator destinations;
      ForeignJNDIObjectImpl impl;
      if (this.factoryImpls != null) {
         for(destinations = this.factoryImpls.values().iterator(); destinations.hasNext(); impl.bind(rebind)) {
            impl = (ForeignJNDIObjectImpl)destinations.next();
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("ForeignJMSImpl: Binding factory impl=" + impl);
            }
         }
      }

      if (this.destinationImpls != null) {
         for(destinations = this.destinationImpls.values().iterator(); destinations.hasNext(); impl.bind(rebind)) {
            impl = (ForeignJNDIObjectImpl)destinations.next();
            if (JMSDebug.JMSModule.isDebugEnabled()) {
               JMSDebug.JMSModule.debug("ForeignJMSImpl: Binding destination impl=" + impl);
            }
         }
      }

   }

   private void unbind() {
      Iterator factories = this.factoryImpls.values().iterator();

      while(factories.hasNext()) {
         ForeignJNDIObjectImpl impl = (ForeignJNDIObjectImpl)factories.next();
         impl.unbind();
      }

      Iterator destinations = this.destinationImpls.values().iterator();

      while(destinations.hasNext()) {
         ForeignJNDIObjectImpl impl = (ForeignJNDIObjectImpl)destinations.next();
         impl.unbind();
      }

   }

   private void prepareForeignJNDIObject(ForeignJNDIObjectImpl impl, boolean isConnectionFactory) throws BeanUpdateRejectedException {
      try {
         impl.bind(false);
      } catch (JMSException var4) {
         throw new BeanUpdateRejectedException(var4.getMessage(), var4);
      }

      if (isConnectionFactory) {
         this.factoryImpls.put(impl.getName(), impl);
      } else {
         this.destinationImpls.put(impl.getName(), impl);
      }

   }

   private void unprepareForeignJNDIObject(ForeignJNDIObjectBean bean, boolean isConnectionFactory) {
      ForeignJNDIObjectImpl impl;
      if (isConnectionFactory) {
         impl = (ForeignJNDIObjectImpl)this.factoryImpls.remove(bean.getName());
      } else {
         impl = (ForeignJNDIObjectImpl)this.destinationImpls.remove(bean.getName());
      }

      if (impl == null) {
         throw new AssertionError("ERROR: ForeignJNDIObject " + bean.getName() + " previously not in prepared state");
      } else {
         impl.unbind();
         impl.close();
      }
   }

   public void prepare() throws ModuleException {
      this.needRebind = false;
      ForeignConnectionFactoryBean[] foreignConnectionFactories = this.foreignServerBean.getForeignConnectionFactories();
      this.factoryImpls = new HashMap();

      ForeignJNDIObjectImpl newImpl;
      for(int i = 0; i < foreignConnectionFactories.length; ++i) {
         try {
            newImpl = new ForeignJNDIObjectImpl(this.jmsService.getCtx(false), this.foreignServerBean, foreignConnectionFactories[i]);
         } catch (JMSException var7) {
            throw new ModuleException(var7.getMessage(), var7);
         }

         this.factoryImpls.put(foreignConnectionFactories[i].getName(), newImpl);
      }

      ForeignDestinationBean[] foreignDestinations = this.foreignServerBean.getForeignDestinations();
      this.destinationImpls = new HashMap();

      for(int i = 0; i < foreignDestinations.length; ++i) {
         try {
            newImpl = new ForeignJNDIObjectImpl(this.jmsService.getCtx(false), this.foreignServerBean, foreignDestinations[i]);
         } catch (JMSException var6) {
            throw new ModuleException(var6.getMessage(), var6);
         }

         this.destinationImpls.put(foreignDestinations[i].getName(), newImpl);
      }

      this.validateJNDI();
   }

   public void activate(JMSBean paramWholeModule) throws ModuleException {
      this.foreignServerBean = paramWholeModule.lookupForeignServer(this.getEntityName());
      this.unregisterBeanUpdateListeners();

      try {
         this.registerBeanUpdateListeners();
      } catch (JMSException var4) {
         throw new ModuleException("ERROR: registering BeanUpdateListener for the Foreign JMS Server components", var4);
      }

      try {
         this.bind(false);
         JMSLogger.logForeignJMSDeployed(this.foreignServerBean.getName());
      } catch (JMSException var3) {
         JMSLogger.logErrorBindForeignJMS(this.foreignServerBean.getName(), var3);
         throw new ModuleException("ERROR: binding the Foreign JMS Server components", var3);
      }
   }

   public void deactivate() throws ModuleException {
      this.unregisterBeanUpdateListeners();
      if (this.jmsService.isActive()) {
         this.unbind();
      }

   }

   public void unprepare() throws ModuleException {
      Iterator factories = this.factoryImpls.values().iterator();

      while(factories.hasNext()) {
         ForeignJNDIObjectImpl impl = (ForeignJNDIObjectImpl)factories.next();
         impl.close();
         factories.remove();
      }

      Iterator destinations = this.destinationImpls.values().iterator();

      while(destinations.hasNext()) {
         ForeignJNDIObjectImpl impl = (ForeignJNDIObjectImpl)destinations.next();
         impl.close();
         destinations.remove();
      }

   }

   public void remove() throws ModuleException {
   }

   public void destroy() throws ModuleException {
   }

   public String getEntityName() {
      return this.foreignServerBean.getName();
   }

   public void prepareChangeOfTargets(List targets, DomainMBean proposedDomain) {
   }

   public void activateChangeOfTargets() {
   }

   public void rollbackChangeOfTargets() {
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      throw new AssertionError("Name setter only here to satisfy interface: " + name);
   }

   public String getNotes() {
      return this.foreignServerBean.getNotes();
   }

   public void setNotes(String notes) {
      throw new AssertionError("Notes setter only here to satisfy interface: " + notes);
   }

   public String getInitialContextFactory() {
      return this.initialContextFactory;
   }

   public void setInitialContextFactory(String paramInitialContextFactory) {
      this.initialContextFactory = paramInitialContextFactory;
      this.needRebind = true;
   }

   public void setJNDIPropertiesCredential(String paramJNDIPropertiesCredential) {
      this.needRebind = true;
   }

   public void setJNDIPropertiesCredentialEncrypted(byte[] paramJNDIPropertiesCredentialEncrypted) {
      this.needRebind = true;
   }

   public String getConnectionURL() {
      return this.connectionURL;
   }

   public void setConnectionURL(String paramConnectionURL) {
      this.connectionURL = paramConnectionURL;
      this.needRebind = true;
   }

   public PropertyBean[] getJNDIProperties() {
      return this.jndiPropertyBeans;
   }

   public void setJNDIProperties(PropertyBean[] paramJNDIPropertyBeans) throws BeanUpdateFailedException {
      Iterator listenerIterator = this.jndiPropertyBeanListeners.iterator();

      while(listenerIterator.hasNext()) {
         GenericBeanListener jndiPropertyBeanListener = (GenericBeanListener)listenerIterator.next();
         jndiPropertyBeanListener.close();
         jndiPropertyBeanListener = null;
         listenerIterator.remove();
      }

      PropertyBean[] var11 = paramJNDIPropertyBeans;
      int var4 = paramJNDIPropertyBeans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PropertyBean propertyBean = var11[var5];
         DescriptorBean descriptor = (DescriptorBean)propertyBean;
         GenericBeanListener jndiPropertyBeanListener = new GenericBeanListener(descriptor, this, jndiPropertyBeanSignatures);

         try {
            jndiPropertyBeanListener.initialize();
         } catch (ManagementException var10) {
            throw new BeanUpdateFailedException(var10.getMessage(), var10);
         }

         jndiPropertyBeanListener.setCustomizer(this);
         this.jndiPropertyBeanListeners.add(jndiPropertyBeanListener);
      }

      this.jndiPropertyBeans = paramJNDIPropertyBeans;
      this.needRebind = true;
   }

   public boolean isDefaultTargetingEnabled() {
      return this.defaultTargetingEnabled;
   }

   public void setDefaultTargetingEnabled(boolean paramDefaultTargetingEnabled) {
      this.defaultTargetingEnabled = paramDefaultTargetingEnabled;
   }

   public PropertyBean createJNDIProperty(String key) {
      throw new AssertionError("JNDI property setter only here to satisfy interface: " + this.name);
   }

   public void destroyJNDIProperty(PropertyBean property) {
      throw new AssertionError("JNDI Property destructor only here to satisfy interface: " + this.name);
   }

   public String getSubDeploymentName() {
      return this.foreignServerBean.getSubDeploymentName();
   }

   public void setSubDeploymentName(String groupName) {
      throw new AssertionError("SubDeployment setter only here to satisfy interface: " + groupName);
   }

   public ForeignDestinationBean[] getForeignDestinations() {
      return this.foreignServerBean.getForeignDestinations();
   }

   public void setForeignDestinations(ForeignDestinationBean[] foreignDestinations) {
      throw new AssertionError("Foreign Destination setter only here to satisfy interface");
   }

   public ForeignDestinationBean createForeignDestination(String name) {
      throw new AssertionError("Foreign Destination creator only here to satisfy interface");
   }

   public void destroyForeignDestination(ForeignDestinationBean foreignDestinationBean) {
      throw new AssertionError("Foreign Destination destroyer only here to satisfy interface");
   }

   public ForeignConnectionFactoryBean[] getForeignConnectionFactories() {
      return this.foreignServerBean.getForeignConnectionFactories();
   }

   public void setForeignConnectionFactories(ForeignConnectionFactoryBean[] foreignConnectionFactories) {
      throw new AssertionError("Foreign Connection Factory setter only here to satisfy interface");
   }

   public ForeignConnectionFactoryBean createForeignConnectionFactory(String name) {
      throw new AssertionError("Foreign Connection Factory creator only here to satisfy interface");
   }

   public void destroyForeignConnectionFactory(ForeignConnectionFactoryBean foreignConnectionFactoryBean) {
      throw new AssertionError("Foreign Connection Factory destroyer only here to satisfy interface");
   }

   private void registerBeanUpdateListeners() throws JMSException {
      DescriptorBean descriptor = (DescriptorBean)this.foreignServerBean;
      this.foreignServerBeanListener = new GenericBeanListener(descriptor, this, foreignServerBeanSignatures, foreignServerAdditionSignatures);
      this.foreignServerBeanListener.setCustomizer(this);

      try {
         this.foreignServerBeanListener.initialize();
      } catch (ManagementException var3) {
         throw new JMSException(var3.getMessage(), var3);
      }
   }

   private void unregisterBeanUpdateListeners() {
      if (this.foreignServerBeanListener != null) {
         this.foreignServerBeanListener.close();
         this.foreignServerBeanListener = null;
      }

   }

   public void startAddForeignDestinations(ForeignDestinationBean destination) throws BeanUpdateRejectedException {
   }

   public void startAddForeignConnectionFactories(ForeignConnectionFactoryBean conFac) throws BeanUpdateRejectedException {
   }

   public void finishAddForeignDestinations(ForeignDestinationBean destination, boolean isActivate) throws BeanUpdateRejectedException {
      if (isActivate) {
         ForeignJNDIObjectImpl impl;
         try {
            impl = new ForeignJNDIObjectImpl(this.jmsService.getCtx(false), this.foreignServerBean, destination);
         } catch (JMSException var5) {
            throw new BeanUpdateRejectedException(var5.getMessage(), var5);
         }

         this.prepareForeignJNDIObject(impl, false);
      } else {
         this.unprepareForeignJNDIObject(destination, false);
      }

   }

   public void finishAddForeignConnectionFactories(ForeignConnectionFactoryBean conFac, boolean isActivate) throws BeanUpdateRejectedException {
      if (isActivate) {
         ForeignJNDIObjectImpl impl;
         try {
            impl = new ForeignJNDIObjectImpl(this.jmsService.getCtx(false), this.foreignServerBean, conFac);
         } catch (JMSException var5) {
            throw new BeanUpdateRejectedException(var5.getMessage(), var5);
         }

         this.prepareForeignJNDIObject(impl, true);
      } else {
         this.unprepareForeignJNDIObject(conFac, true);
      }

   }

   public void startRemoveForeignDestinations(ForeignDestinationBean destination) throws BeanUpdateRejectedException {
   }

   public void startRemoveForeignConnectionFactories(ForeignConnectionFactoryBean conFac) throws BeanUpdateRejectedException {
   }

   public void finishRemoveForeignDestinations(ForeignDestinationBean destination, boolean isActivate) {
      if (isActivate) {
         this.unprepareForeignJNDIObject(destination, false);
      }

   }

   public void finishRemoveForeignConnectionFactories(ForeignConnectionFactoryBean conFac, boolean isActivate) {
      if (isActivate) {
         this.unprepareForeignJNDIObject(conFac, true);
      }

   }

   public ForeignDestinationBean lookupForeignDestination(String name) {
      return null;
   }

   public ForeignConnectionFactoryBean lookupForeignConnectionFactory(String name) {
      return null;
   }

   public void activateFinished() throws BeanUpdateFailedException {
      if (this.needRebind) {
         try {
            this.bind(true);
         } catch (JMSException var5) {
            throw new BeanUpdateFailedException(var5.getMessage());
         } finally {
            this.needRebind = false;
         }
      }

   }

   public void setKey(String key) {
      this.needRebind = true;
   }

   public void setValue(String value) {
      this.needRebind = true;
   }

   static {
      foreignServerBeanSignatures.put("InitialContextFactory", String.class);
      foreignServerBeanSignatures.put("JNDIPropertiesCredential", String.class);
      foreignServerBeanSignatures.put("JNDIPropertiesCredentialEncrypted", byte[].class);
      foreignServerBeanSignatures.put("ConnectionURL", String.class);
      foreignServerBeanSignatures.put("JNDIProperties", PropertyBean[].class);
      foreignServerBeanSignatures.put("DefaultTargetingEnabled", Boolean.TYPE);
      foreignServerAdditionSignatures.put("ForeignDestinations", ForeignDestinationBean.class);
      foreignServerAdditionSignatures.put("ForeignConnectionFactories", ForeignConnectionFactoryBean.class);
      jndiPropertyBeanSignatures.put("Key", String.class);
      jndiPropertyBeanSignatures.put("Value", String.class);
   }
}

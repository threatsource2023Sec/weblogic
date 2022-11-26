package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.descriptor.beangen.CustomizerFactoryBuilder;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.customizers.WeblogicApplicationBeanCustomizer;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicApplicationBeanImpl extends SettableBeanImpl implements WeblogicApplicationBean, Serializable {
   private ApplicationAdminModeTriggerBean _ApplicationAdminModeTrigger;
   private ApplicationParamBean[] _ApplicationParams;
   private CapacityBean[] _Capacities;
   private CdiDescriptorBean _CdiDescriptor;
   private ClassLoadingBean _ClassLoading;
   private ClassloaderStructureBean _ClassloaderStructure;
   private CoherenceClusterRefBean _CoherenceClusterRef;
   private String _ComponentFactoryClassName;
   private ContextRequestClassBean[] _ContextRequests;
   private EjbBean _Ejb;
   private EjbReferenceDescriptionBean[] _EjbReferenceDescriptions;
   private FairShareRequestClassBean[] _FairShareRequests;
   private FastSwapBean _FastSwap;
   private JDBCConnectionPoolBean[] _JDBCConnectionPools;
   private LibraryContextRootOverrideBean[] _LibraryContextRootOverrides;
   private LibraryRefBean[] _LibraryRefs;
   private ListenerBean[] _Listeners;
   private ManagedExecutorServiceBean[] _ManagedExecutorServices;
   private ManagedScheduledExecutorServiceBean[] _ManagedScheduledExecutorServices;
   private ManagedThreadFactoryBean[] _ManagedThreadFactories;
   private MaxThreadsConstraintBean[] _MaxThreadsConstraints;
   private MessageDestinationDescriptorBean[] _MessageDestinationDescriptors;
   private MinThreadsConstraintBean[] _MinThreadsConstraints;
   private WeblogicModuleBean[] _Modules;
   private OsgiFrameworkReferenceBean _OsgiFrameworkReference;
   private PreferApplicationPackagesBean _PreferApplicationPackages;
   private PreferApplicationResourcesBean _PreferApplicationResources;
   private String _ReadyRegistration;
   private ResourceDescriptionBean[] _ResourceDescriptions;
   private ResourceEnvDescriptionBean[] _ResourceEnvDescriptions;
   private ResponseTimeRequestClassBean[] _ResponseTimeRequests;
   private SecurityBean _Security;
   private ServiceReferenceDescriptionBean[] _ServiceReferenceDescriptions;
   private SessionDescriptorBean _SessionDescriptor;
   private ShutdownBean[] _Shutdowns;
   private SingletonServiceBean[] _SingletonServices;
   private StartupBean[] _Startups;
   private String _Version;
   private WorkManagerBean[] _WorkManagers;
   private XmlBean _Xml;
   private transient WeblogicApplicationBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicApplicationBeanImpl() {
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.descriptor.customizers.WeblogicApplicationBeanCustomizerFactory");
         this._customizer = (WeblogicApplicationBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WeblogicApplicationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.descriptor.customizers.WeblogicApplicationBeanCustomizerFactory");
         this._customizer = (WeblogicApplicationBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WeblogicApplicationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.descriptor.customizers.WeblogicApplicationBeanCustomizerFactory");
         this._customizer = (WeblogicApplicationBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public EjbBean getEjb() {
      return this._Ejb;
   }

   public boolean isEjbInherited() {
      return false;
   }

   public boolean isEjbSet() {
      return this._isSet(0);
   }

   public void setEjb(EjbBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getEjb() != null && param0 != this.getEjb()) {
         throw new BeanAlreadyExistsException(this.getEjb() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         EjbBean _oldVal = this._Ejb;
         this._Ejb = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public EjbBean createEjb() {
      EjbBeanImpl _val = new EjbBeanImpl(this, -1);

      try {
         this.setEjb(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEjb(EjbBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Ejb;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setEjb((EjbBean)null);
               this._unSet(0);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public XmlBean getXml() {
      return this._Xml;
   }

   public boolean isXmlInherited() {
      return false;
   }

   public boolean isXmlSet() {
      return this._isSet(1);
   }

   public void setXml(XmlBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getXml() != null && param0 != this.getXml()) {
         throw new BeanAlreadyExistsException(this.getXml() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         XmlBean _oldVal = this._Xml;
         this._Xml = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public XmlBean createXml() {
      XmlBeanImpl _val = new XmlBeanImpl(this, -1);

      try {
         this.setXml(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyXml(XmlBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Xml;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setXml((XmlBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addJDBCConnectionPool(JDBCConnectionPoolBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         JDBCConnectionPoolBean[] _new;
         if (this._isSet(2)) {
            _new = (JDBCConnectionPoolBean[])((JDBCConnectionPoolBean[])this._getHelper()._extendArray(this.getJDBCConnectionPools(), JDBCConnectionPoolBean.class, param0));
         } else {
            _new = new JDBCConnectionPoolBean[]{param0};
         }

         try {
            this.setJDBCConnectionPools(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JDBCConnectionPoolBean[] getJDBCConnectionPools() {
      return this._JDBCConnectionPools;
   }

   public boolean isJDBCConnectionPoolsInherited() {
      return false;
   }

   public boolean isJDBCConnectionPoolsSet() {
      return this._isSet(2);
   }

   public void removeJDBCConnectionPool(JDBCConnectionPoolBean param0) {
      this.destroyJDBCConnectionPool(param0);
   }

   public void setJDBCConnectionPools(JDBCConnectionPoolBean[] param0) throws InvalidAttributeValueException {
      JDBCConnectionPoolBean[] param0 = param0 == null ? new JDBCConnectionPoolBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JDBCConnectionPoolBean[] _oldVal = this._JDBCConnectionPools;
      this._JDBCConnectionPools = (JDBCConnectionPoolBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public JDBCConnectionPoolBean createJDBCConnectionPool() {
      JDBCConnectionPoolBeanImpl _val = new JDBCConnectionPoolBeanImpl(this, -1);

      try {
         this.addJDBCConnectionPool(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJDBCConnectionPool(JDBCConnectionPoolBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         JDBCConnectionPoolBean[] _old = this.getJDBCConnectionPools();
         JDBCConnectionPoolBean[] _new = (JDBCConnectionPoolBean[])((JDBCConnectionPoolBean[])this._getHelper()._removeElement(_old, JDBCConnectionPoolBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setJDBCConnectionPools(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public SecurityBean getSecurity() {
      return this._Security;
   }

   public boolean isSecurityInherited() {
      return false;
   }

   public boolean isSecuritySet() {
      return this._isSet(3);
   }

   public void setSecurity(SecurityBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSecurity() != null && param0 != this.getSecurity()) {
         throw new BeanAlreadyExistsException(this.getSecurity() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SecurityBean _oldVal = this._Security;
         this._Security = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public SecurityBean createSecurity() {
      SecurityBeanImpl _val = new SecurityBeanImpl(this, -1);

      try {
         this.setSecurity(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySecurity(SecurityBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Security;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSecurity((SecurityBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addApplicationParam(ApplicationParamBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         ApplicationParamBean[] _new;
         if (this._isSet(4)) {
            _new = (ApplicationParamBean[])((ApplicationParamBean[])this._getHelper()._extendArray(this.getApplicationParams(), ApplicationParamBean.class, param0));
         } else {
            _new = new ApplicationParamBean[]{param0};
         }

         try {
            this.setApplicationParams(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ApplicationParamBean[] getApplicationParams() {
      return this._ApplicationParams;
   }

   public boolean isApplicationParamsInherited() {
      return false;
   }

   public boolean isApplicationParamsSet() {
      return this._isSet(4);
   }

   public void removeApplicationParam(ApplicationParamBean param0) {
      this.destroyApplicationParam(param0);
   }

   public void setApplicationParams(ApplicationParamBean[] param0) throws InvalidAttributeValueException {
      ApplicationParamBean[] param0 = param0 == null ? new ApplicationParamBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ApplicationParamBean[] _oldVal = this._ApplicationParams;
      this._ApplicationParams = (ApplicationParamBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public ApplicationParamBean createApplicationParam() {
      ApplicationParamBeanImpl _val = new ApplicationParamBeanImpl(this, -1);

      try {
         this.addApplicationParam(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyApplicationParam(ApplicationParamBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         ApplicationParamBean[] _old = this.getApplicationParams();
         ApplicationParamBean[] _new = (ApplicationParamBean[])((ApplicationParamBean[])this._getHelper()._removeElement(_old, ApplicationParamBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setApplicationParams(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public ClassloaderStructureBean getClassloaderStructure() {
      return this._ClassloaderStructure;
   }

   public boolean isClassloaderStructureInherited() {
      return false;
   }

   public boolean isClassloaderStructureSet() {
      return this._isSet(5);
   }

   public void setClassloaderStructure(ClassloaderStructureBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getClassloaderStructure() != null && param0 != this.getClassloaderStructure()) {
         throw new BeanAlreadyExistsException(this.getClassloaderStructure() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ClassloaderStructureBean _oldVal = this._ClassloaderStructure;
         this._ClassloaderStructure = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public ClassloaderStructureBean createClassloaderStructure() {
      ClassloaderStructureBeanImpl _val = new ClassloaderStructureBeanImpl(this, -1);

      try {
         this.setClassloaderStructure(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyClassloaderStructure(ClassloaderStructureBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ClassloaderStructure;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setClassloaderStructure((ClassloaderStructureBean)null);
               this._unSet(5);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addListener(ListenerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         ListenerBean[] _new;
         if (this._isSet(6)) {
            _new = (ListenerBean[])((ListenerBean[])this._getHelper()._extendArray(this.getListeners(), ListenerBean.class, param0));
         } else {
            _new = new ListenerBean[]{param0};
         }

         try {
            this.setListeners(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ListenerBean[] getListeners() {
      return this._Listeners;
   }

   public boolean isListenersInherited() {
      return false;
   }

   public boolean isListenersSet() {
      return this._isSet(6);
   }

   public void removeListener(ListenerBean param0) {
      this.destroyListener(param0);
   }

   public void setListeners(ListenerBean[] param0) throws InvalidAttributeValueException {
      ListenerBean[] param0 = param0 == null ? new ListenerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ListenerBean[] _oldVal = this._Listeners;
      this._Listeners = (ListenerBean[])param0;
      this._postSet(6, _oldVal, param0);
   }

   public ListenerBean createListener() {
      ListenerBeanImpl _val = new ListenerBeanImpl(this, -1);

      try {
         this.addListener(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyListener(ListenerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 6);
         ListenerBean[] _old = this.getListeners();
         ListenerBean[] _new = (ListenerBean[])((ListenerBean[])this._getHelper()._removeElement(_old, ListenerBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setListeners(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addSingletonService(SingletonServiceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         SingletonServiceBean[] _new;
         if (this._isSet(7)) {
            _new = (SingletonServiceBean[])((SingletonServiceBean[])this._getHelper()._extendArray(this.getSingletonServices(), SingletonServiceBean.class, param0));
         } else {
            _new = new SingletonServiceBean[]{param0};
         }

         try {
            this.setSingletonServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public SingletonServiceBean[] getSingletonServices() {
      return this._SingletonServices;
   }

   public boolean isSingletonServicesInherited() {
      return false;
   }

   public boolean isSingletonServicesSet() {
      return this._isSet(7);
   }

   public void removeSingletonService(SingletonServiceBean param0) {
      this.destroySingletonService(param0);
   }

   public void setSingletonServices(SingletonServiceBean[] param0) throws InvalidAttributeValueException {
      SingletonServiceBean[] param0 = param0 == null ? new SingletonServiceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      SingletonServiceBean[] _oldVal = this._SingletonServices;
      this._SingletonServices = (SingletonServiceBean[])param0;
      this._postSet(7, _oldVal, param0);
   }

   public SingletonServiceBean createSingletonService() {
      SingletonServiceBeanImpl _val = new SingletonServiceBeanImpl(this, -1);

      try {
         this.addSingletonService(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySingletonService(SingletonServiceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 7);
         SingletonServiceBean[] _old = this.getSingletonServices();
         SingletonServiceBean[] _new = (SingletonServiceBean[])((SingletonServiceBean[])this._getHelper()._removeElement(_old, SingletonServiceBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSingletonServices(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addStartup(StartupBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 8)) {
         StartupBean[] _new;
         if (this._isSet(8)) {
            _new = (StartupBean[])((StartupBean[])this._getHelper()._extendArray(this.getStartups(), StartupBean.class, param0));
         } else {
            _new = new StartupBean[]{param0};
         }

         try {
            this.setStartups(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public StartupBean[] getStartups() {
      return this._Startups;
   }

   public boolean isStartupsInherited() {
      return false;
   }

   public boolean isStartupsSet() {
      return this._isSet(8);
   }

   public void removeStartup(StartupBean param0) {
      this.destroyStartup(param0);
   }

   public void setStartups(StartupBean[] param0) throws InvalidAttributeValueException {
      StartupBean[] param0 = param0 == null ? new StartupBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 8)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      StartupBean[] _oldVal = this._Startups;
      this._Startups = (StartupBean[])param0;
      this._postSet(8, _oldVal, param0);
   }

   public StartupBean createStartup() {
      StartupBeanImpl _val = new StartupBeanImpl(this, -1);

      try {
         this.addStartup(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyStartup(StartupBean param0) {
      try {
         this._checkIsPotentialChild(param0, 8);
         StartupBean[] _old = this.getStartups();
         StartupBean[] _new = (StartupBean[])((StartupBean[])this._getHelper()._removeElement(_old, StartupBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setStartups(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addShutdown(ShutdownBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         ShutdownBean[] _new;
         if (this._isSet(9)) {
            _new = (ShutdownBean[])((ShutdownBean[])this._getHelper()._extendArray(this.getShutdowns(), ShutdownBean.class, param0));
         } else {
            _new = new ShutdownBean[]{param0};
         }

         try {
            this.setShutdowns(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ShutdownBean[] getShutdowns() {
      return this._Shutdowns;
   }

   public boolean isShutdownsInherited() {
      return false;
   }

   public boolean isShutdownsSet() {
      return this._isSet(9);
   }

   public void removeShutdown(ShutdownBean param0) {
      this.destroyShutdown(param0);
   }

   public void setShutdowns(ShutdownBean[] param0) throws InvalidAttributeValueException {
      ShutdownBean[] param0 = param0 == null ? new ShutdownBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ShutdownBean[] _oldVal = this._Shutdowns;
      this._Shutdowns = (ShutdownBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public ShutdownBean createShutdown() {
      ShutdownBeanImpl _val = new ShutdownBeanImpl(this, -1);

      try {
         this.addShutdown(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyShutdown(ShutdownBean param0) {
      try {
         this._checkIsPotentialChild(param0, 9);
         ShutdownBean[] _old = this.getShutdowns();
         ShutdownBean[] _new = (ShutdownBean[])((ShutdownBean[])this._getHelper()._removeElement(_old, ShutdownBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setShutdowns(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addModule(WeblogicModuleBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         WeblogicModuleBean[] _new;
         if (this._isSet(10)) {
            _new = (WeblogicModuleBean[])((WeblogicModuleBean[])this._getHelper()._extendArray(this.getModules(), WeblogicModuleBean.class, param0));
         } else {
            _new = new WeblogicModuleBean[]{param0};
         }

         try {
            this.setModules(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WeblogicModuleBean[] getModules() {
      return this._Modules;
   }

   public boolean isModulesInherited() {
      return false;
   }

   public boolean isModulesSet() {
      return this._isSet(10);
   }

   public void removeModule(WeblogicModuleBean param0) {
      this.destroyModule(param0);
   }

   public void setModules(WeblogicModuleBean[] param0) throws InvalidAttributeValueException {
      WeblogicModuleBean[] param0 = param0 == null ? new WeblogicModuleBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WeblogicModuleBean[] _oldVal = this._Modules;
      this._Modules = (WeblogicModuleBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public WeblogicModuleBean createModule() {
      WeblogicModuleBeanImpl _val = new WeblogicModuleBeanImpl(this, -1);

      try {
         this.addModule(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyModule(WeblogicModuleBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         WeblogicModuleBean[] _old = this.getModules();
         WeblogicModuleBean[] _new = (WeblogicModuleBean[])((WeblogicModuleBean[])this._getHelper()._removeElement(_old, WeblogicModuleBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setModules(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addLibraryRef(LibraryRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         LibraryRefBean[] _new;
         if (this._isSet(11)) {
            _new = (LibraryRefBean[])((LibraryRefBean[])this._getHelper()._extendArray(this.getLibraryRefs(), LibraryRefBean.class, param0));
         } else {
            _new = new LibraryRefBean[]{param0};
         }

         try {
            this.setLibraryRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LibraryRefBean[] getLibraryRefs() {
      return this._LibraryRefs;
   }

   public boolean isLibraryRefsInherited() {
      return false;
   }

   public boolean isLibraryRefsSet() {
      return this._isSet(11);
   }

   public void removeLibraryRef(LibraryRefBean param0) {
      this.destroyLibraryRef(param0);
   }

   public void setLibraryRefs(LibraryRefBean[] param0) throws InvalidAttributeValueException {
      LibraryRefBean[] param0 = param0 == null ? new LibraryRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LibraryRefBean[] _oldVal = this._LibraryRefs;
      this._LibraryRefs = (LibraryRefBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public LibraryRefBean createLibraryRef() {
      LibraryRefBeanImpl _val = new LibraryRefBeanImpl(this, -1);

      try {
         this.addLibraryRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLibraryRef(LibraryRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         LibraryRefBean[] _old = this.getLibraryRefs();
         LibraryRefBean[] _new = (LibraryRefBean[])((LibraryRefBean[])this._getHelper()._removeElement(_old, LibraryRefBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLibraryRefs(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addFairShareRequest(FairShareRequestClassBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         FairShareRequestClassBean[] _new;
         if (this._isSet(12)) {
            _new = (FairShareRequestClassBean[])((FairShareRequestClassBean[])this._getHelper()._extendArray(this.getFairShareRequests(), FairShareRequestClassBean.class, param0));
         } else {
            _new = new FairShareRequestClassBean[]{param0};
         }

         try {
            this.setFairShareRequests(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FairShareRequestClassBean[] getFairShareRequests() {
      return this._FairShareRequests;
   }

   public boolean isFairShareRequestsInherited() {
      return false;
   }

   public boolean isFairShareRequestsSet() {
      return this._isSet(12);
   }

   public void removeFairShareRequest(FairShareRequestClassBean param0) {
      this.destroyFairShareRequest(param0);
   }

   public void setFairShareRequests(FairShareRequestClassBean[] param0) throws InvalidAttributeValueException {
      FairShareRequestClassBean[] param0 = param0 == null ? new FairShareRequestClassBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      FairShareRequestClassBean[] _oldVal = this._FairShareRequests;
      this._FairShareRequests = (FairShareRequestClassBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public FairShareRequestClassBean createFairShareRequest() {
      FairShareRequestClassBeanImpl _val = new FairShareRequestClassBeanImpl(this, -1);

      try {
         this.addFairShareRequest(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFairShareRequest(FairShareRequestClassBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         FairShareRequestClassBean[] _old = this.getFairShareRequests();
         FairShareRequestClassBean[] _new = (FairShareRequestClassBean[])((FairShareRequestClassBean[])this._getHelper()._removeElement(_old, FairShareRequestClassBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFairShareRequests(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addResponseTimeRequest(ResponseTimeRequestClassBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         ResponseTimeRequestClassBean[] _new;
         if (this._isSet(13)) {
            _new = (ResponseTimeRequestClassBean[])((ResponseTimeRequestClassBean[])this._getHelper()._extendArray(this.getResponseTimeRequests(), ResponseTimeRequestClassBean.class, param0));
         } else {
            _new = new ResponseTimeRequestClassBean[]{param0};
         }

         try {
            this.setResponseTimeRequests(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResponseTimeRequestClassBean[] getResponseTimeRequests() {
      return this._ResponseTimeRequests;
   }

   public boolean isResponseTimeRequestsInherited() {
      return false;
   }

   public boolean isResponseTimeRequestsSet() {
      return this._isSet(13);
   }

   public void removeResponseTimeRequest(ResponseTimeRequestClassBean param0) {
      this.destroyResponseTimeRequest(param0);
   }

   public void setResponseTimeRequests(ResponseTimeRequestClassBean[] param0) throws InvalidAttributeValueException {
      ResponseTimeRequestClassBean[] param0 = param0 == null ? new ResponseTimeRequestClassBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ResponseTimeRequestClassBean[] _oldVal = this._ResponseTimeRequests;
      this._ResponseTimeRequests = (ResponseTimeRequestClassBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public ResponseTimeRequestClassBean createResponseTimeRequest() {
      ResponseTimeRequestClassBeanImpl _val = new ResponseTimeRequestClassBeanImpl(this, -1);

      try {
         this.addResponseTimeRequest(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResponseTimeRequest(ResponseTimeRequestClassBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         ResponseTimeRequestClassBean[] _old = this.getResponseTimeRequests();
         ResponseTimeRequestClassBean[] _new = (ResponseTimeRequestClassBean[])((ResponseTimeRequestClassBean[])this._getHelper()._removeElement(_old, ResponseTimeRequestClassBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setResponseTimeRequests(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addContextRequest(ContextRequestClassBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         ContextRequestClassBean[] _new;
         if (this._isSet(14)) {
            _new = (ContextRequestClassBean[])((ContextRequestClassBean[])this._getHelper()._extendArray(this.getContextRequests(), ContextRequestClassBean.class, param0));
         } else {
            _new = new ContextRequestClassBean[]{param0};
         }

         try {
            this.setContextRequests(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ContextRequestClassBean[] getContextRequests() {
      return this._ContextRequests;
   }

   public boolean isContextRequestsInherited() {
      return false;
   }

   public boolean isContextRequestsSet() {
      return this._isSet(14);
   }

   public void removeContextRequest(ContextRequestClassBean param0) {
      this.destroyContextRequest(param0);
   }

   public void setContextRequests(ContextRequestClassBean[] param0) throws InvalidAttributeValueException {
      ContextRequestClassBean[] param0 = param0 == null ? new ContextRequestClassBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ContextRequestClassBean[] _oldVal = this._ContextRequests;
      this._ContextRequests = (ContextRequestClassBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public ContextRequestClassBean createContextRequest() {
      ContextRequestClassBeanImpl _val = new ContextRequestClassBeanImpl(this, -1);

      try {
         this.addContextRequest(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyContextRequest(ContextRequestClassBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         ContextRequestClassBean[] _old = this.getContextRequests();
         ContextRequestClassBean[] _new = (ContextRequestClassBean[])((ContextRequestClassBean[])this._getHelper()._removeElement(_old, ContextRequestClassBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setContextRequests(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addMaxThreadsConstraint(MaxThreadsConstraintBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         MaxThreadsConstraintBean[] _new;
         if (this._isSet(15)) {
            _new = (MaxThreadsConstraintBean[])((MaxThreadsConstraintBean[])this._getHelper()._extendArray(this.getMaxThreadsConstraints(), MaxThreadsConstraintBean.class, param0));
         } else {
            _new = new MaxThreadsConstraintBean[]{param0};
         }

         try {
            this.setMaxThreadsConstraints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MaxThreadsConstraintBean[] getMaxThreadsConstraints() {
      return this._MaxThreadsConstraints;
   }

   public boolean isMaxThreadsConstraintsInherited() {
      return false;
   }

   public boolean isMaxThreadsConstraintsSet() {
      return this._isSet(15);
   }

   public void removeMaxThreadsConstraint(MaxThreadsConstraintBean param0) {
      this.destroyMaxThreadsConstraint(param0);
   }

   public void setMaxThreadsConstraints(MaxThreadsConstraintBean[] param0) throws InvalidAttributeValueException {
      MaxThreadsConstraintBean[] param0 = param0 == null ? new MaxThreadsConstraintBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MaxThreadsConstraintBean[] _oldVal = this._MaxThreadsConstraints;
      this._MaxThreadsConstraints = (MaxThreadsConstraintBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public MaxThreadsConstraintBean createMaxThreadsConstraint() {
      MaxThreadsConstraintBeanImpl _val = new MaxThreadsConstraintBeanImpl(this, -1);

      try {
         this.addMaxThreadsConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMaxThreadsConstraint(MaxThreadsConstraintBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         MaxThreadsConstraintBean[] _old = this.getMaxThreadsConstraints();
         MaxThreadsConstraintBean[] _new = (MaxThreadsConstraintBean[])((MaxThreadsConstraintBean[])this._getHelper()._removeElement(_old, MaxThreadsConstraintBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMaxThreadsConstraints(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addMinThreadsConstraint(MinThreadsConstraintBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         MinThreadsConstraintBean[] _new;
         if (this._isSet(16)) {
            _new = (MinThreadsConstraintBean[])((MinThreadsConstraintBean[])this._getHelper()._extendArray(this.getMinThreadsConstraints(), MinThreadsConstraintBean.class, param0));
         } else {
            _new = new MinThreadsConstraintBean[]{param0};
         }

         try {
            this.setMinThreadsConstraints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MinThreadsConstraintBean[] getMinThreadsConstraints() {
      return this._MinThreadsConstraints;
   }

   public boolean isMinThreadsConstraintsInherited() {
      return false;
   }

   public boolean isMinThreadsConstraintsSet() {
      return this._isSet(16);
   }

   public void removeMinThreadsConstraint(MinThreadsConstraintBean param0) {
      this.destroyMinThreadsConstraint(param0);
   }

   public void setMinThreadsConstraints(MinThreadsConstraintBean[] param0) throws InvalidAttributeValueException {
      MinThreadsConstraintBean[] param0 = param0 == null ? new MinThreadsConstraintBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MinThreadsConstraintBean[] _oldVal = this._MinThreadsConstraints;
      this._MinThreadsConstraints = (MinThreadsConstraintBean[])param0;
      this._postSet(16, _oldVal, param0);
   }

   public MinThreadsConstraintBean createMinThreadsConstraint() {
      MinThreadsConstraintBeanImpl _val = new MinThreadsConstraintBeanImpl(this, -1);

      try {
         this.addMinThreadsConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMinThreadsConstraint(MinThreadsConstraintBean param0) {
      try {
         this._checkIsPotentialChild(param0, 16);
         MinThreadsConstraintBean[] _old = this.getMinThreadsConstraints();
         MinThreadsConstraintBean[] _new = (MinThreadsConstraintBean[])((MinThreadsConstraintBean[])this._getHelper()._removeElement(_old, MinThreadsConstraintBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMinThreadsConstraints(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addCapacity(CapacityBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 17)) {
         CapacityBean[] _new;
         if (this._isSet(17)) {
            _new = (CapacityBean[])((CapacityBean[])this._getHelper()._extendArray(this.getCapacities(), CapacityBean.class, param0));
         } else {
            _new = new CapacityBean[]{param0};
         }

         try {
            this.setCapacities(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CapacityBean[] getCapacities() {
      return this._Capacities;
   }

   public boolean isCapacitiesInherited() {
      return false;
   }

   public boolean isCapacitiesSet() {
      return this._isSet(17);
   }

   public void removeCapacity(CapacityBean param0) {
      this.destroyCapacity(param0);
   }

   public void setCapacities(CapacityBean[] param0) throws InvalidAttributeValueException {
      CapacityBean[] param0 = param0 == null ? new CapacityBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 17)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CapacityBean[] _oldVal = this._Capacities;
      this._Capacities = (CapacityBean[])param0;
      this._postSet(17, _oldVal, param0);
   }

   public CapacityBean createCapacity() {
      CapacityBeanImpl _val = new CapacityBeanImpl(this, -1);

      try {
         this.addCapacity(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCapacity(CapacityBean param0) {
      try {
         this._checkIsPotentialChild(param0, 17);
         CapacityBean[] _old = this.getCapacities();
         CapacityBean[] _new = (CapacityBean[])((CapacityBean[])this._getHelper()._removeElement(_old, CapacityBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCapacities(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addWorkManager(WorkManagerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         WorkManagerBean[] _new;
         if (this._isSet(18)) {
            _new = (WorkManagerBean[])((WorkManagerBean[])this._getHelper()._extendArray(this.getWorkManagers(), WorkManagerBean.class, param0));
         } else {
            _new = new WorkManagerBean[]{param0};
         }

         try {
            this.setWorkManagers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WorkManagerBean[] getWorkManagers() {
      return this._WorkManagers;
   }

   public boolean isWorkManagersInherited() {
      return false;
   }

   public boolean isWorkManagersSet() {
      return this._isSet(18);
   }

   public void removeWorkManager(WorkManagerBean param0) {
      this.destroyWorkManager(param0);
   }

   public void setWorkManagers(WorkManagerBean[] param0) throws InvalidAttributeValueException {
      WorkManagerBean[] param0 = param0 == null ? new WorkManagerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WorkManagerBean[] _oldVal = this._WorkManagers;
      this._WorkManagers = (WorkManagerBean[])param0;
      this._postSet(18, _oldVal, param0);
   }

   public WorkManagerBean createWorkManager() {
      WorkManagerBeanImpl _val = new WorkManagerBeanImpl(this, -1);

      try {
         this.addWorkManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWorkManager(WorkManagerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 18);
         WorkManagerBean[] _old = this.getWorkManagers();
         WorkManagerBean[] _new = (WorkManagerBean[])((WorkManagerBean[])this._getHelper()._removeElement(_old, WorkManagerBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWorkManagers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addManagedExecutorService(ManagedExecutorServiceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 19)) {
         ManagedExecutorServiceBean[] _new;
         if (this._isSet(19)) {
            _new = (ManagedExecutorServiceBean[])((ManagedExecutorServiceBean[])this._getHelper()._extendArray(this.getManagedExecutorServices(), ManagedExecutorServiceBean.class, param0));
         } else {
            _new = new ManagedExecutorServiceBean[]{param0};
         }

         try {
            this.setManagedExecutorServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedExecutorServiceBean[] getManagedExecutorServices() {
      return this._ManagedExecutorServices;
   }

   public boolean isManagedExecutorServicesInherited() {
      return false;
   }

   public boolean isManagedExecutorServicesSet() {
      return this._isSet(19);
   }

   public void removeManagedExecutorService(ManagedExecutorServiceBean param0) {
      this.destroyManagedExecutorService(param0);
   }

   public void setManagedExecutorServices(ManagedExecutorServiceBean[] param0) throws InvalidAttributeValueException {
      ManagedExecutorServiceBean[] param0 = param0 == null ? new ManagedExecutorServiceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 19)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedExecutorServiceBean[] _oldVal = this._ManagedExecutorServices;
      this._ManagedExecutorServices = (ManagedExecutorServiceBean[])param0;
      this._postSet(19, _oldVal, param0);
   }

   public ManagedExecutorServiceBean createManagedExecutorService() {
      ManagedExecutorServiceBeanImpl _val = new ManagedExecutorServiceBeanImpl(this, -1);

      try {
         this.addManagedExecutorService(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyManagedExecutorService(ManagedExecutorServiceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 19);
         ManagedExecutorServiceBean[] _old = this.getManagedExecutorServices();
         ManagedExecutorServiceBean[] _new = (ManagedExecutorServiceBean[])((ManagedExecutorServiceBean[])this._getHelper()._removeElement(_old, ManagedExecutorServiceBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManagedExecutorServices(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addManagedScheduledExecutorService(ManagedScheduledExecutorServiceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 20)) {
         ManagedScheduledExecutorServiceBean[] _new;
         if (this._isSet(20)) {
            _new = (ManagedScheduledExecutorServiceBean[])((ManagedScheduledExecutorServiceBean[])this._getHelper()._extendArray(this.getManagedScheduledExecutorServices(), ManagedScheduledExecutorServiceBean.class, param0));
         } else {
            _new = new ManagedScheduledExecutorServiceBean[]{param0};
         }

         try {
            this.setManagedScheduledExecutorServices(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedScheduledExecutorServiceBean[] getManagedScheduledExecutorServices() {
      return this._ManagedScheduledExecutorServices;
   }

   public boolean isManagedScheduledExecutorServicesInherited() {
      return false;
   }

   public boolean isManagedScheduledExecutorServicesSet() {
      return this._isSet(20);
   }

   public void removeManagedScheduledExecutorService(ManagedScheduledExecutorServiceBean param0) {
      this.destroyManagedScheduledExecutorService(param0);
   }

   public void setManagedScheduledExecutorServices(ManagedScheduledExecutorServiceBean[] param0) throws InvalidAttributeValueException {
      ManagedScheduledExecutorServiceBean[] param0 = param0 == null ? new ManagedScheduledExecutorServiceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 20)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedScheduledExecutorServiceBean[] _oldVal = this._ManagedScheduledExecutorServices;
      this._ManagedScheduledExecutorServices = (ManagedScheduledExecutorServiceBean[])param0;
      this._postSet(20, _oldVal, param0);
   }

   public ManagedScheduledExecutorServiceBean createManagedScheduledExecutorService() {
      ManagedScheduledExecutorServiceBeanImpl _val = new ManagedScheduledExecutorServiceBeanImpl(this, -1);

      try {
         this.addManagedScheduledExecutorService(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyManagedScheduledExecutorService(ManagedScheduledExecutorServiceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 20);
         ManagedScheduledExecutorServiceBean[] _old = this.getManagedScheduledExecutorServices();
         ManagedScheduledExecutorServiceBean[] _new = (ManagedScheduledExecutorServiceBean[])((ManagedScheduledExecutorServiceBean[])this._getHelper()._removeElement(_old, ManagedScheduledExecutorServiceBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManagedScheduledExecutorServices(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addManagedThreadFactory(ManagedThreadFactoryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 21)) {
         ManagedThreadFactoryBean[] _new;
         if (this._isSet(21)) {
            _new = (ManagedThreadFactoryBean[])((ManagedThreadFactoryBean[])this._getHelper()._extendArray(this.getManagedThreadFactories(), ManagedThreadFactoryBean.class, param0));
         } else {
            _new = new ManagedThreadFactoryBean[]{param0};
         }

         try {
            this.setManagedThreadFactories(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedThreadFactoryBean[] getManagedThreadFactories() {
      return this._ManagedThreadFactories;
   }

   public boolean isManagedThreadFactoriesInherited() {
      return false;
   }

   public boolean isManagedThreadFactoriesSet() {
      return this._isSet(21);
   }

   public void removeManagedThreadFactory(ManagedThreadFactoryBean param0) {
      this.destroyManagedThreadFactory(param0);
   }

   public void setManagedThreadFactories(ManagedThreadFactoryBean[] param0) throws InvalidAttributeValueException {
      ManagedThreadFactoryBean[] param0 = param0 == null ? new ManagedThreadFactoryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 21)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedThreadFactoryBean[] _oldVal = this._ManagedThreadFactories;
      this._ManagedThreadFactories = (ManagedThreadFactoryBean[])param0;
      this._postSet(21, _oldVal, param0);
   }

   public ManagedThreadFactoryBean createManagedThreadFactory() {
      ManagedThreadFactoryBeanImpl _val = new ManagedThreadFactoryBeanImpl(this, -1);

      try {
         this.addManagedThreadFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyManagedThreadFactory(ManagedThreadFactoryBean param0) {
      try {
         this._checkIsPotentialChild(param0, 21);
         ManagedThreadFactoryBean[] _old = this.getManagedThreadFactories();
         ManagedThreadFactoryBean[] _new = (ManagedThreadFactoryBean[])((ManagedThreadFactoryBean[])this._getHelper()._removeElement(_old, ManagedThreadFactoryBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setManagedThreadFactories(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public String getComponentFactoryClassName() {
      return this._ComponentFactoryClassName;
   }

   public boolean isComponentFactoryClassNameInherited() {
      return false;
   }

   public boolean isComponentFactoryClassNameSet() {
      return this._isSet(22);
   }

   public void setComponentFactoryClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ComponentFactoryClassName;
      this._ComponentFactoryClassName = param0;
      this._postSet(22, _oldVal, param0);
   }

   public ApplicationAdminModeTriggerBean getApplicationAdminModeTrigger() {
      return this._ApplicationAdminModeTrigger;
   }

   public boolean isApplicationAdminModeTriggerInherited() {
      return false;
   }

   public boolean isApplicationAdminModeTriggerSet() {
      return this._isSet(23);
   }

   public void setApplicationAdminModeTrigger(ApplicationAdminModeTriggerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getApplicationAdminModeTrigger() != null && param0 != this.getApplicationAdminModeTrigger()) {
         throw new BeanAlreadyExistsException(this.getApplicationAdminModeTrigger() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 23)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ApplicationAdminModeTriggerBean _oldVal = this._ApplicationAdminModeTrigger;
         this._ApplicationAdminModeTrigger = param0;
         this._postSet(23, _oldVal, param0);
      }
   }

   public ApplicationAdminModeTriggerBean createApplicationAdminModeTrigger() {
      ApplicationAdminModeTriggerBeanImpl _val = new ApplicationAdminModeTriggerBeanImpl(this, -1);

      try {
         this.setApplicationAdminModeTrigger(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyApplicationAdminModeTrigger() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ApplicationAdminModeTrigger;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setApplicationAdminModeTrigger((ApplicationAdminModeTriggerBean)null);
               this._unSet(23);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public SessionDescriptorBean getSessionDescriptor() {
      return this._SessionDescriptor;
   }

   public boolean isSessionDescriptorInherited() {
      return false;
   }

   public boolean isSessionDescriptorSet() {
      return this._isSet(24) || this._isAnythingSet((AbstractDescriptorBean)this.getSessionDescriptor());
   }

   public void setSessionDescriptor(SessionDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 24)) {
         this._postCreate(_child);
      }

      SessionDescriptorBean _oldVal = this._SessionDescriptor;
      this._SessionDescriptor = param0;
      this._postSet(24, _oldVal, param0);
   }

   public void addLibraryContextRootOverride(LibraryContextRootOverrideBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 25)) {
         LibraryContextRootOverrideBean[] _new;
         if (this._isSet(25)) {
            _new = (LibraryContextRootOverrideBean[])((LibraryContextRootOverrideBean[])this._getHelper()._extendArray(this.getLibraryContextRootOverrides(), LibraryContextRootOverrideBean.class, param0));
         } else {
            _new = new LibraryContextRootOverrideBean[]{param0};
         }

         try {
            this.setLibraryContextRootOverrides(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LibraryContextRootOverrideBean[] getLibraryContextRootOverrides() {
      return this._LibraryContextRootOverrides;
   }

   public boolean isLibraryContextRootOverridesInherited() {
      return false;
   }

   public boolean isLibraryContextRootOverridesSet() {
      return this._isSet(25);
   }

   public void removeLibraryContextRootOverride(LibraryContextRootOverrideBean param0) {
      LibraryContextRootOverrideBean[] _old = this.getLibraryContextRootOverrides();
      LibraryContextRootOverrideBean[] _new = (LibraryContextRootOverrideBean[])((LibraryContextRootOverrideBean[])this._getHelper()._removeElement(_old, LibraryContextRootOverrideBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setLibraryContextRootOverrides(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setLibraryContextRootOverrides(LibraryContextRootOverrideBean[] param0) throws InvalidAttributeValueException {
      LibraryContextRootOverrideBean[] param0 = param0 == null ? new LibraryContextRootOverrideBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 25)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LibraryContextRootOverrideBean[] _oldVal = this._LibraryContextRootOverrides;
      this._LibraryContextRootOverrides = (LibraryContextRootOverrideBean[])param0;
      this._postSet(25, _oldVal, param0);
   }

   public LibraryContextRootOverrideBean createLibraryContextRootOverride() {
      LibraryContextRootOverrideBeanImpl _val = new LibraryContextRootOverrideBeanImpl(this, -1);

      try {
         this.addLibraryContextRootOverride(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PreferApplicationPackagesBean getPreferApplicationPackages() {
      return this._PreferApplicationPackages;
   }

   public boolean isPreferApplicationPackagesInherited() {
      return false;
   }

   public boolean isPreferApplicationPackagesSet() {
      return this._isSet(26);
   }

   public void setPreferApplicationPackages(PreferApplicationPackagesBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPreferApplicationPackages() != null && param0 != this.getPreferApplicationPackages()) {
         throw new BeanAlreadyExistsException(this.getPreferApplicationPackages() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 26)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         PreferApplicationPackagesBean _oldVal = this._PreferApplicationPackages;
         this._PreferApplicationPackages = param0;
         this._postSet(26, _oldVal, param0);
      }
   }

   public PreferApplicationPackagesBean createPreferApplicationPackages() {
      PreferApplicationPackagesBeanImpl _val = new PreferApplicationPackagesBeanImpl(this, -1);

      try {
         this.setPreferApplicationPackages(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPreferApplicationPackages() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PreferApplicationPackages;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPreferApplicationPackages((PreferApplicationPackagesBean)null);
               this._unSet(26);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public PreferApplicationResourcesBean getPreferApplicationResources() {
      return this._PreferApplicationResources;
   }

   public boolean isPreferApplicationResourcesInherited() {
      return false;
   }

   public boolean isPreferApplicationResourcesSet() {
      return this._isSet(27);
   }

   public void setPreferApplicationResources(PreferApplicationResourcesBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPreferApplicationResources() != null && param0 != this.getPreferApplicationResources()) {
         throw new BeanAlreadyExistsException(this.getPreferApplicationResources() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 27)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         PreferApplicationResourcesBean _oldVal = this._PreferApplicationResources;
         this._PreferApplicationResources = param0;
         this._postSet(27, _oldVal, param0);
      }
   }

   public PreferApplicationResourcesBean createPreferApplicationResources() {
      PreferApplicationResourcesBeanImpl _val = new PreferApplicationResourcesBeanImpl(this, -1);

      try {
         this.setPreferApplicationResources(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPreferApplicationResources() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PreferApplicationResources;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPreferApplicationResources((PreferApplicationResourcesBean)null);
               this._unSet(27);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public FastSwapBean getFastSwap() {
      return this._FastSwap;
   }

   public boolean isFastSwapInherited() {
      return false;
   }

   public boolean isFastSwapSet() {
      return this._isSet(28);
   }

   public void setFastSwap(FastSwapBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getFastSwap() != null && param0 != this.getFastSwap()) {
         throw new BeanAlreadyExistsException(this.getFastSwap() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 28)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         FastSwapBean _oldVal = this._FastSwap;
         this._FastSwap = param0;
         this._postSet(28, _oldVal, param0);
      }
   }

   public FastSwapBean createFastSwap() {
      FastSwapBeanImpl _val = new FastSwapBeanImpl(this, -1);

      try {
         this.setFastSwap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFastSwap() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._FastSwap;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFastSwap((FastSwapBean)null);
               this._unSet(28);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public CoherenceClusterRefBean getCoherenceClusterRef() {
      return this._CoherenceClusterRef;
   }

   public boolean isCoherenceClusterRefInherited() {
      return false;
   }

   public boolean isCoherenceClusterRefSet() {
      return this._isSet(29);
   }

   public void setCoherenceClusterRef(CoherenceClusterRefBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCoherenceClusterRef() != null && param0 != this.getCoherenceClusterRef()) {
         throw new BeanAlreadyExistsException(this.getCoherenceClusterRef() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 29)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         CoherenceClusterRefBean _oldVal = this._CoherenceClusterRef;
         this._CoherenceClusterRef = param0;
         this._postSet(29, _oldVal, param0);
      }
   }

   public CoherenceClusterRefBean createCoherenceClusterRef() {
      CoherenceClusterRefBeanImpl _val = new CoherenceClusterRefBeanImpl(this, -1);

      try {
         this.setCoherenceClusterRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCoherenceClusterRef() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._CoherenceClusterRef;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCoherenceClusterRef((CoherenceClusterRefBean)null);
               this._unSet(29);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addResourceDescription(ResourceDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 30)) {
         ResourceDescriptionBean[] _new;
         if (this._isSet(30)) {
            _new = (ResourceDescriptionBean[])((ResourceDescriptionBean[])this._getHelper()._extendArray(this.getResourceDescriptions(), ResourceDescriptionBean.class, param0));
         } else {
            _new = new ResourceDescriptionBean[]{param0};
         }

         try {
            this.setResourceDescriptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceDescriptionBean[] getResourceDescriptions() {
      return this._ResourceDescriptions;
   }

   public boolean isResourceDescriptionsInherited() {
      return false;
   }

   public boolean isResourceDescriptionsSet() {
      return this._isSet(30);
   }

   public void removeResourceDescription(ResourceDescriptionBean param0) {
      ResourceDescriptionBean[] _old = this.getResourceDescriptions();
      ResourceDescriptionBean[] _new = (ResourceDescriptionBean[])((ResourceDescriptionBean[])this._getHelper()._removeElement(_old, ResourceDescriptionBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setResourceDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setResourceDescriptions(ResourceDescriptionBean[] param0) throws InvalidAttributeValueException {
      ResourceDescriptionBean[] param0 = param0 == null ? new ResourceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 30)) {
            this._postCreate(_child);
         }
      }

      ResourceDescriptionBean[] _oldVal = this._ResourceDescriptions;
      this._ResourceDescriptions = (ResourceDescriptionBean[])param0;
      this._postSet(30, _oldVal, param0);
   }

   public void addResourceEnvDescription(ResourceEnvDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 31)) {
         ResourceEnvDescriptionBean[] _new;
         if (this._isSet(31)) {
            _new = (ResourceEnvDescriptionBean[])((ResourceEnvDescriptionBean[])this._getHelper()._extendArray(this.getResourceEnvDescriptions(), ResourceEnvDescriptionBean.class, param0));
         } else {
            _new = new ResourceEnvDescriptionBean[]{param0};
         }

         try {
            this.setResourceEnvDescriptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResourceEnvDescriptionBean[] getResourceEnvDescriptions() {
      return this._ResourceEnvDescriptions;
   }

   public boolean isResourceEnvDescriptionsInherited() {
      return false;
   }

   public boolean isResourceEnvDescriptionsSet() {
      return this._isSet(31);
   }

   public void removeResourceEnvDescription(ResourceEnvDescriptionBean param0) {
      ResourceEnvDescriptionBean[] _old = this.getResourceEnvDescriptions();
      ResourceEnvDescriptionBean[] _new = (ResourceEnvDescriptionBean[])((ResourceEnvDescriptionBean[])this._getHelper()._removeElement(_old, ResourceEnvDescriptionBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setResourceEnvDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setResourceEnvDescriptions(ResourceEnvDescriptionBean[] param0) throws InvalidAttributeValueException {
      ResourceEnvDescriptionBean[] param0 = param0 == null ? new ResourceEnvDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 31)) {
            this._postCreate(_child);
         }
      }

      ResourceEnvDescriptionBean[] _oldVal = this._ResourceEnvDescriptions;
      this._ResourceEnvDescriptions = (ResourceEnvDescriptionBean[])param0;
      this._postSet(31, _oldVal, param0);
   }

   public void addEjbReferenceDescription(EjbReferenceDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 32)) {
         EjbReferenceDescriptionBean[] _new;
         if (this._isSet(32)) {
            _new = (EjbReferenceDescriptionBean[])((EjbReferenceDescriptionBean[])this._getHelper()._extendArray(this.getEjbReferenceDescriptions(), EjbReferenceDescriptionBean.class, param0));
         } else {
            _new = new EjbReferenceDescriptionBean[]{param0};
         }

         try {
            this.setEjbReferenceDescriptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EjbReferenceDescriptionBean[] getEjbReferenceDescriptions() {
      return this._EjbReferenceDescriptions;
   }

   public boolean isEjbReferenceDescriptionsInherited() {
      return false;
   }

   public boolean isEjbReferenceDescriptionsSet() {
      return this._isSet(32);
   }

   public void removeEjbReferenceDescription(EjbReferenceDescriptionBean param0) {
      EjbReferenceDescriptionBean[] _old = this.getEjbReferenceDescriptions();
      EjbReferenceDescriptionBean[] _new = (EjbReferenceDescriptionBean[])((EjbReferenceDescriptionBean[])this._getHelper()._removeElement(_old, EjbReferenceDescriptionBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setEjbReferenceDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setEjbReferenceDescriptions(EjbReferenceDescriptionBean[] param0) throws InvalidAttributeValueException {
      EjbReferenceDescriptionBean[] param0 = param0 == null ? new EjbReferenceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 32)) {
            this._postCreate(_child);
         }
      }

      EjbReferenceDescriptionBean[] _oldVal = this._EjbReferenceDescriptions;
      this._EjbReferenceDescriptions = (EjbReferenceDescriptionBean[])param0;
      this._postSet(32, _oldVal, param0);
   }

   public void addServiceReferenceDescription(ServiceReferenceDescriptionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 33)) {
         ServiceReferenceDescriptionBean[] _new;
         if (this._isSet(33)) {
            _new = (ServiceReferenceDescriptionBean[])((ServiceReferenceDescriptionBean[])this._getHelper()._extendArray(this.getServiceReferenceDescriptions(), ServiceReferenceDescriptionBean.class, param0));
         } else {
            _new = new ServiceReferenceDescriptionBean[]{param0};
         }

         try {
            this.setServiceReferenceDescriptions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceReferenceDescriptionBean[] getServiceReferenceDescriptions() {
      return this._ServiceReferenceDescriptions;
   }

   public boolean isServiceReferenceDescriptionsInherited() {
      return false;
   }

   public boolean isServiceReferenceDescriptionsSet() {
      return this._isSet(33);
   }

   public void removeServiceReferenceDescription(ServiceReferenceDescriptionBean param0) {
      ServiceReferenceDescriptionBean[] _old = this.getServiceReferenceDescriptions();
      ServiceReferenceDescriptionBean[] _new = (ServiceReferenceDescriptionBean[])((ServiceReferenceDescriptionBean[])this._getHelper()._removeElement(_old, ServiceReferenceDescriptionBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setServiceReferenceDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setServiceReferenceDescriptions(ServiceReferenceDescriptionBean[] param0) throws InvalidAttributeValueException {
      ServiceReferenceDescriptionBean[] param0 = param0 == null ? new ServiceReferenceDescriptionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 33)) {
            this._postCreate(_child);
         }
      }

      ServiceReferenceDescriptionBean[] _oldVal = this._ServiceReferenceDescriptions;
      this._ServiceReferenceDescriptions = (ServiceReferenceDescriptionBean[])param0;
      this._postSet(33, _oldVal, param0);
   }

   public void addMessageDestinationDescriptor(MessageDestinationDescriptorBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 34)) {
         MessageDestinationDescriptorBean[] _new;
         if (this._isSet(34)) {
            _new = (MessageDestinationDescriptorBean[])((MessageDestinationDescriptorBean[])this._getHelper()._extendArray(this.getMessageDestinationDescriptors(), MessageDestinationDescriptorBean.class, param0));
         } else {
            _new = new MessageDestinationDescriptorBean[]{param0};
         }

         try {
            this.setMessageDestinationDescriptors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MessageDestinationDescriptorBean[] getMessageDestinationDescriptors() {
      return this._MessageDestinationDescriptors;
   }

   public boolean isMessageDestinationDescriptorsInherited() {
      return false;
   }

   public boolean isMessageDestinationDescriptorsSet() {
      return this._isSet(34);
   }

   public void removeMessageDestinationDescriptor(MessageDestinationDescriptorBean param0) {
      MessageDestinationDescriptorBean[] _old = this.getMessageDestinationDescriptors();
      MessageDestinationDescriptorBean[] _new = (MessageDestinationDescriptorBean[])((MessageDestinationDescriptorBean[])this._getHelper()._removeElement(_old, MessageDestinationDescriptorBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMessageDestinationDescriptors(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMessageDestinationDescriptors(MessageDestinationDescriptorBean[] param0) throws InvalidAttributeValueException {
      MessageDestinationDescriptorBean[] param0 = param0 == null ? new MessageDestinationDescriptorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 34)) {
            this._postCreate(_child);
         }
      }

      MessageDestinationDescriptorBean[] _oldVal = this._MessageDestinationDescriptors;
      this._MessageDestinationDescriptors = (MessageDestinationDescriptorBean[])param0;
      this._postSet(34, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(35);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(35, _oldVal, param0);
   }

   public OsgiFrameworkReferenceBean getOsgiFrameworkReference() {
      return this._OsgiFrameworkReference;
   }

   public boolean isOsgiFrameworkReferenceInherited() {
      return false;
   }

   public boolean isOsgiFrameworkReferenceSet() {
      return this._isSet(36);
   }

   public void setOsgiFrameworkReference(OsgiFrameworkReferenceBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getOsgiFrameworkReference() != null && param0 != this.getOsgiFrameworkReference()) {
         throw new BeanAlreadyExistsException(this.getOsgiFrameworkReference() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 36)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         OsgiFrameworkReferenceBean _oldVal = this._OsgiFrameworkReference;
         this._OsgiFrameworkReference = param0;
         this._postSet(36, _oldVal, param0);
      }
   }

   public OsgiFrameworkReferenceBean createOsgiFrameworkReference() {
      OsgiFrameworkReferenceBeanImpl _val = new OsgiFrameworkReferenceBeanImpl(this, -1);

      try {
         this.setOsgiFrameworkReference(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyOsgiFrameworkReference() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._OsgiFrameworkReference;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setOsgiFrameworkReference((OsgiFrameworkReferenceBean)null);
               this._unSet(36);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public WeblogicEnvironmentBean convertToWeblogicEnvironmentBean() {
      return this._customizer.convertToWeblogicEnvironmentBean();
   }

   public ClassLoadingBean getClassLoading() {
      return this._ClassLoading;
   }

   public boolean isClassLoadingInherited() {
      return false;
   }

   public boolean isClassLoadingSet() {
      return this._isSet(37) || this._isAnythingSet((AbstractDescriptorBean)this.getClassLoading());
   }

   public void setClassLoading(ClassLoadingBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 37)) {
         this._postCreate(_child);
      }

      ClassLoadingBean _oldVal = this._ClassLoading;
      this._ClassLoading = param0;
      this._postSet(37, _oldVal, param0);
   }

   public String getReadyRegistration() {
      return this._ReadyRegistration;
   }

   public boolean isReadyRegistrationInherited() {
      return false;
   }

   public boolean isReadyRegistrationSet() {
      return this._isSet(38);
   }

   public void setReadyRegistration(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ReadyRegistration;
      this._ReadyRegistration = param0;
      this._postSet(38, _oldVal, param0);
   }

   public CdiDescriptorBean getCdiDescriptor() {
      return this._CdiDescriptor;
   }

   public boolean isCdiDescriptorInherited() {
      return false;
   }

   public boolean isCdiDescriptorSet() {
      return this._isSet(39) || this._isAnythingSet((AbstractDescriptorBean)this.getCdiDescriptor());
   }

   public void setCdiDescriptor(CdiDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 39)) {
         this._postCreate(_child);
      }

      CdiDescriptorBean _oldVal = this._CdiDescriptor;
      this._CdiDescriptor = param0;
      this._postSet(39, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet() || this.isCdiDescriptorSet() || this.isClassLoadingSet() || this.isEjbReferenceDescriptionsSet() || this.isMessageDestinationDescriptorsSet() || this.isResourceDescriptionsSet() || this.isResourceEnvDescriptionsSet() || this.isServiceReferenceDescriptionsSet() || this.isSessionDescriptorSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 23;
      }

      try {
         switch (idx) {
            case 23:
               this._ApplicationAdminModeTrigger = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ApplicationParams = new ApplicationParamBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._Capacities = new CapacityBean[0];
               if (initOne) {
                  break;
               }
            case 39:
               this._CdiDescriptor = new CdiDescriptorBeanImpl(this, 39);
               this._postCreate((AbstractDescriptorBean)this._CdiDescriptor);
               if (initOne) {
                  break;
               }
            case 37:
               this._ClassLoading = new ClassLoadingBeanImpl(this, 37);
               this._postCreate((AbstractDescriptorBean)this._ClassLoading);
               if (initOne) {
                  break;
               }
            case 5:
               this._ClassloaderStructure = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._CoherenceClusterRef = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._ComponentFactoryClassName = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._ContextRequests = new ContextRequestClassBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Ejb = null;
               if (initOne) {
                  break;
               }
            case 32:
               this._EjbReferenceDescriptions = new EjbReferenceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._FairShareRequests = new FairShareRequestClassBean[0];
               if (initOne) {
                  break;
               }
            case 28:
               this._FastSwap = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._JDBCConnectionPools = new JDBCConnectionPoolBean[0];
               if (initOne) {
                  break;
               }
            case 25:
               this._LibraryContextRootOverrides = new LibraryContextRootOverrideBean[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._LibraryRefs = new LibraryRefBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._Listeners = new ListenerBean[0];
               if (initOne) {
                  break;
               }
            case 19:
               this._ManagedExecutorServices = new ManagedExecutorServiceBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._ManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceBean[0];
               if (initOne) {
                  break;
               }
            case 21:
               this._ManagedThreadFactories = new ManagedThreadFactoryBean[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._MaxThreadsConstraints = new MaxThreadsConstraintBean[0];
               if (initOne) {
                  break;
               }
            case 34:
               this._MessageDestinationDescriptors = new MessageDestinationDescriptorBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._MinThreadsConstraints = new MinThreadsConstraintBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._Modules = new WeblogicModuleBean[0];
               if (initOne) {
                  break;
               }
            case 36:
               this._OsgiFrameworkReference = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._PreferApplicationPackages = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._PreferApplicationResources = null;
               if (initOne) {
                  break;
               }
            case 38:
               this._ReadyRegistration = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._ResourceDescriptions = new ResourceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 31:
               this._ResourceEnvDescriptions = new ResourceEnvDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._ResponseTimeRequests = new ResponseTimeRequestClassBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Security = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._ServiceReferenceDescriptions = new ServiceReferenceDescriptionBean[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._SessionDescriptor = new SessionDescriptorBeanImpl(this, 24);
               this._postCreate((AbstractDescriptorBean)this._SessionDescriptor);
               if (initOne) {
                  break;
               }
            case 9:
               this._Shutdowns = new ShutdownBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._SingletonServices = new SingletonServiceBean[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._Startups = new StartupBean[0];
               if (initOne) {
                  break;
               }
            case 35:
               this._Version = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._WorkManagers = new WorkManagerBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Xml = null;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/weblogic-application/1.5/weblogic-application.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-application";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("ejb")) {
                  return 0;
               }

               if (s.equals("xml")) {
                  return 1;
               }
            case 4:
            case 5:
            case 10:
            case 16:
            case 19:
            case 23:
            case 26:
            case 31:
            case 32:
            case 33:
            default:
               break;
            case 6:
               if (s.equals("module")) {
                  return 10;
               }
               break;
            case 7:
               if (s.equals("startup")) {
                  return 8;
               }

               if (s.equals("version")) {
                  return 35;
               }
               break;
            case 8:
               if (s.equals("capacity")) {
                  return 17;
               }

               if (s.equals("listener")) {
                  return 6;
               }

               if (s.equals("security")) {
                  return 3;
               }

               if (s.equals("shutdown")) {
                  return 9;
               }
               break;
            case 9:
               if (s.equals("fast-swap")) {
                  return 28;
               }
               break;
            case 11:
               if (s.equals("library-ref")) {
                  return 11;
               }
               break;
            case 12:
               if (s.equals("work-manager")) {
                  return 18;
               }
               break;
            case 13:
               if (s.equals("class-loading")) {
                  return 37;
               }
               break;
            case 14:
               if (s.equals("cdi-descriptor")) {
                  return 39;
               }
               break;
            case 15:
               if (s.equals("context-request")) {
                  return 14;
               }
               break;
            case 17:
               if (s.equals("application-param")) {
                  return 4;
               }

               if (s.equals("singleton-service")) {
                  return 7;
               }
               break;
            case 18:
               if (s.equals("fair-share-request")) {
                  return 12;
               }

               if (s.equals("ready-registration")) {
                  return 38;
               }

               if (s.equals("session-descriptor")) {
                  return 24;
               }
               break;
            case 20:
               if (s.equals("jdbc-connection-pool")) {
                  return 2;
               }

               if (s.equals("resource-description")) {
                  return 30;
               }
               break;
            case 21:
               if (s.equals("classloader-structure")) {
                  return 5;
               }

               if (s.equals("coherence-cluster-ref")) {
                  return 29;
               }

               if (s.equals("response-time-request")) {
                  return 13;
               }
               break;
            case 22:
               if (s.equals("managed-thread-factory")) {
                  return 21;
               }

               if (s.equals("max-threads-constraint")) {
                  return 15;
               }

               if (s.equals("min-threads-constraint")) {
                  return 16;
               }
               break;
            case 24:
               if (s.equals("managed-executor-service")) {
                  return 19;
               }

               if (s.equals("osgi-framework-reference")) {
                  return 36;
               }

               if (s.equals("resource-env-description")) {
                  return 31;
               }
               break;
            case 25:
               if (s.equals("ejb-reference-description")) {
                  return 32;
               }
               break;
            case 27:
               if (s.equals("prefer-application-packages")) {
                  return 26;
               }
               break;
            case 28:
               if (s.equals("component-factory-class-name")) {
                  return 22;
               }

               if (s.equals("prefer-application-resources")) {
                  return 27;
               }
               break;
            case 29:
               if (s.equals("library-context-root-override")) {
                  return 25;
               }

               if (s.equals("service-reference-description")) {
                  return 33;
               }
               break;
            case 30:
               if (s.equals("application-admin-mode-trigger")) {
                  return 23;
               }

               if (s.equals("message-destination-descriptor")) {
                  return 34;
               }
               break;
            case 34:
               if (s.equals("managed-scheduled-executor-service")) {
                  return 20;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new EjbBeanImpl.SchemaHelper2();
            case 1:
               return new XmlBeanImpl.SchemaHelper2();
            case 2:
               return new JDBCConnectionPoolBeanImpl.SchemaHelper2();
            case 3:
               return new SecurityBeanImpl.SchemaHelper2();
            case 4:
               return new ApplicationParamBeanImpl.SchemaHelper2();
            case 5:
               return new ClassloaderStructureBeanImpl.SchemaHelper2();
            case 6:
               return new ListenerBeanImpl.SchemaHelper2();
            case 7:
               return new SingletonServiceBeanImpl.SchemaHelper2();
            case 8:
               return new StartupBeanImpl.SchemaHelper2();
            case 9:
               return new ShutdownBeanImpl.SchemaHelper2();
            case 10:
               return new WeblogicModuleBeanImpl.SchemaHelper2();
            case 11:
               return new LibraryRefBeanImpl.SchemaHelper2();
            case 12:
               return new FairShareRequestClassBeanImpl.SchemaHelper2();
            case 13:
               return new ResponseTimeRequestClassBeanImpl.SchemaHelper2();
            case 14:
               return new ContextRequestClassBeanImpl.SchemaHelper2();
            case 15:
               return new MaxThreadsConstraintBeanImpl.SchemaHelper2();
            case 16:
               return new MinThreadsConstraintBeanImpl.SchemaHelper2();
            case 17:
               return new CapacityBeanImpl.SchemaHelper2();
            case 18:
               return new WorkManagerBeanImpl.SchemaHelper2();
            case 19:
               return new ManagedExecutorServiceBeanImpl.SchemaHelper2();
            case 20:
               return new ManagedScheduledExecutorServiceBeanImpl.SchemaHelper2();
            case 21:
               return new ManagedThreadFactoryBeanImpl.SchemaHelper2();
            case 22:
            case 35:
            case 38:
            default:
               return super.getSchemaHelper(propIndex);
            case 23:
               return new ApplicationAdminModeTriggerBeanImpl.SchemaHelper2();
            case 24:
               return new SessionDescriptorBeanImpl.SchemaHelper2();
            case 25:
               return new LibraryContextRootOverrideBeanImpl.SchemaHelper2();
            case 26:
               return new PreferApplicationPackagesBeanImpl.SchemaHelper2();
            case 27:
               return new PreferApplicationResourcesBeanImpl.SchemaHelper2();
            case 28:
               return new FastSwapBeanImpl.SchemaHelper2();
            case 29:
               return new CoherenceClusterRefBeanImpl.SchemaHelper2();
            case 30:
               return new ResourceDescriptionBeanImpl.SchemaHelper2();
            case 31:
               return new ResourceEnvDescriptionBeanImpl.SchemaHelper2();
            case 32:
               return new EjbReferenceDescriptionBeanImpl.SchemaHelper2();
            case 33:
               return new ServiceReferenceDescriptionBeanImpl.SchemaHelper2();
            case 34:
               return new MessageDestinationDescriptorBeanImpl.SchemaHelper2();
            case 36:
               return new OsgiFrameworkReferenceBeanImpl.SchemaHelper2();
            case 37:
               return new ClassLoadingBeanImpl.SchemaHelper2();
            case 39:
               return new CdiDescriptorBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "weblogic-application";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ejb";
            case 1:
               return "xml";
            case 2:
               return "jdbc-connection-pool";
            case 3:
               return "security";
            case 4:
               return "application-param";
            case 5:
               return "classloader-structure";
            case 6:
               return "listener";
            case 7:
               return "singleton-service";
            case 8:
               return "startup";
            case 9:
               return "shutdown";
            case 10:
               return "module";
            case 11:
               return "library-ref";
            case 12:
               return "fair-share-request";
            case 13:
               return "response-time-request";
            case 14:
               return "context-request";
            case 15:
               return "max-threads-constraint";
            case 16:
               return "min-threads-constraint";
            case 17:
               return "capacity";
            case 18:
               return "work-manager";
            case 19:
               return "managed-executor-service";
            case 20:
               return "managed-scheduled-executor-service";
            case 21:
               return "managed-thread-factory";
            case 22:
               return "component-factory-class-name";
            case 23:
               return "application-admin-mode-trigger";
            case 24:
               return "session-descriptor";
            case 25:
               return "library-context-root-override";
            case 26:
               return "prefer-application-packages";
            case 27:
               return "prefer-application-resources";
            case 28:
               return "fast-swap";
            case 29:
               return "coherence-cluster-ref";
            case 30:
               return "resource-description";
            case 31:
               return "resource-env-description";
            case 32:
               return "ejb-reference-description";
            case 33:
               return "service-reference-description";
            case 34:
               return "message-destination-descriptor";
            case 35:
               return "version";
            case 36:
               return "osgi-framework-reference";
            case 37:
               return "class-loading";
            case 38:
               return "ready-registration";
            case 39:
               return "cdi-descriptor";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
            case 5:
            case 22:
            case 23:
            case 24:
            case 26:
            case 27:
            case 28:
            case 29:
            default:
               return super.isArray(propIndex);
            case 4:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 25:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
            case 35:
            case 38:
            default:
               return super.isBean(propIndex);
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 36:
               return true;
            case 37:
               return true;
            case 39:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 22:
            default:
               return super.isConfigurable(propIndex);
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 23:
               return true;
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private WeblogicApplicationBeanImpl bean;

      protected Helper(WeblogicApplicationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Ejb";
            case 1:
               return "Xml";
            case 2:
               return "JDBCConnectionPools";
            case 3:
               return "Security";
            case 4:
               return "ApplicationParams";
            case 5:
               return "ClassloaderStructure";
            case 6:
               return "Listeners";
            case 7:
               return "SingletonServices";
            case 8:
               return "Startups";
            case 9:
               return "Shutdowns";
            case 10:
               return "Modules";
            case 11:
               return "LibraryRefs";
            case 12:
               return "FairShareRequests";
            case 13:
               return "ResponseTimeRequests";
            case 14:
               return "ContextRequests";
            case 15:
               return "MaxThreadsConstraints";
            case 16:
               return "MinThreadsConstraints";
            case 17:
               return "Capacities";
            case 18:
               return "WorkManagers";
            case 19:
               return "ManagedExecutorServices";
            case 20:
               return "ManagedScheduledExecutorServices";
            case 21:
               return "ManagedThreadFactories";
            case 22:
               return "ComponentFactoryClassName";
            case 23:
               return "ApplicationAdminModeTrigger";
            case 24:
               return "SessionDescriptor";
            case 25:
               return "LibraryContextRootOverrides";
            case 26:
               return "PreferApplicationPackages";
            case 27:
               return "PreferApplicationResources";
            case 28:
               return "FastSwap";
            case 29:
               return "CoherenceClusterRef";
            case 30:
               return "ResourceDescriptions";
            case 31:
               return "ResourceEnvDescriptions";
            case 32:
               return "EjbReferenceDescriptions";
            case 33:
               return "ServiceReferenceDescriptions";
            case 34:
               return "MessageDestinationDescriptors";
            case 35:
               return "Version";
            case 36:
               return "OsgiFrameworkReference";
            case 37:
               return "ClassLoading";
            case 38:
               return "ReadyRegistration";
            case 39:
               return "CdiDescriptor";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ApplicationAdminModeTrigger")) {
            return 23;
         } else if (propName.equals("ApplicationParams")) {
            return 4;
         } else if (propName.equals("Capacities")) {
            return 17;
         } else if (propName.equals("CdiDescriptor")) {
            return 39;
         } else if (propName.equals("ClassLoading")) {
            return 37;
         } else if (propName.equals("ClassloaderStructure")) {
            return 5;
         } else if (propName.equals("CoherenceClusterRef")) {
            return 29;
         } else if (propName.equals("ComponentFactoryClassName")) {
            return 22;
         } else if (propName.equals("ContextRequests")) {
            return 14;
         } else if (propName.equals("Ejb")) {
            return 0;
         } else if (propName.equals("EjbReferenceDescriptions")) {
            return 32;
         } else if (propName.equals("FairShareRequests")) {
            return 12;
         } else if (propName.equals("FastSwap")) {
            return 28;
         } else if (propName.equals("JDBCConnectionPools")) {
            return 2;
         } else if (propName.equals("LibraryContextRootOverrides")) {
            return 25;
         } else if (propName.equals("LibraryRefs")) {
            return 11;
         } else if (propName.equals("Listeners")) {
            return 6;
         } else if (propName.equals("ManagedExecutorServices")) {
            return 19;
         } else if (propName.equals("ManagedScheduledExecutorServices")) {
            return 20;
         } else if (propName.equals("ManagedThreadFactories")) {
            return 21;
         } else if (propName.equals("MaxThreadsConstraints")) {
            return 15;
         } else if (propName.equals("MessageDestinationDescriptors")) {
            return 34;
         } else if (propName.equals("MinThreadsConstraints")) {
            return 16;
         } else if (propName.equals("Modules")) {
            return 10;
         } else if (propName.equals("OsgiFrameworkReference")) {
            return 36;
         } else if (propName.equals("PreferApplicationPackages")) {
            return 26;
         } else if (propName.equals("PreferApplicationResources")) {
            return 27;
         } else if (propName.equals("ReadyRegistration")) {
            return 38;
         } else if (propName.equals("ResourceDescriptions")) {
            return 30;
         } else if (propName.equals("ResourceEnvDescriptions")) {
            return 31;
         } else if (propName.equals("ResponseTimeRequests")) {
            return 13;
         } else if (propName.equals("Security")) {
            return 3;
         } else if (propName.equals("ServiceReferenceDescriptions")) {
            return 33;
         } else if (propName.equals("SessionDescriptor")) {
            return 24;
         } else if (propName.equals("Shutdowns")) {
            return 9;
         } else if (propName.equals("SingletonServices")) {
            return 7;
         } else if (propName.equals("Startups")) {
            return 8;
         } else if (propName.equals("Version")) {
            return 35;
         } else if (propName.equals("WorkManagers")) {
            return 18;
         } else {
            return propName.equals("Xml") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getApplicationAdminModeTrigger() != null) {
            iterators.add(new ArrayIterator(new ApplicationAdminModeTriggerBean[]{this.bean.getApplicationAdminModeTrigger()}));
         }

         iterators.add(new ArrayIterator(this.bean.getApplicationParams()));
         iterators.add(new ArrayIterator(this.bean.getCapacities()));
         if (this.bean.getCdiDescriptor() != null) {
            iterators.add(new ArrayIterator(new CdiDescriptorBean[]{this.bean.getCdiDescriptor()}));
         }

         if (this.bean.getClassLoading() != null) {
            iterators.add(new ArrayIterator(new ClassLoadingBean[]{this.bean.getClassLoading()}));
         }

         if (this.bean.getClassloaderStructure() != null) {
            iterators.add(new ArrayIterator(new ClassloaderStructureBean[]{this.bean.getClassloaderStructure()}));
         }

         if (this.bean.getCoherenceClusterRef() != null) {
            iterators.add(new ArrayIterator(new CoherenceClusterRefBean[]{this.bean.getCoherenceClusterRef()}));
         }

         iterators.add(new ArrayIterator(this.bean.getContextRequests()));
         if (this.bean.getEjb() != null) {
            iterators.add(new ArrayIterator(new EjbBean[]{this.bean.getEjb()}));
         }

         iterators.add(new ArrayIterator(this.bean.getEjbReferenceDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getFairShareRequests()));
         if (this.bean.getFastSwap() != null) {
            iterators.add(new ArrayIterator(new FastSwapBean[]{this.bean.getFastSwap()}));
         }

         iterators.add(new ArrayIterator(this.bean.getJDBCConnectionPools()));
         iterators.add(new ArrayIterator(this.bean.getLibraryContextRootOverrides()));
         iterators.add(new ArrayIterator(this.bean.getLibraryRefs()));
         iterators.add(new ArrayIterator(this.bean.getListeners()));
         iterators.add(new ArrayIterator(this.bean.getManagedExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedScheduledExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedThreadFactories()));
         iterators.add(new ArrayIterator(this.bean.getMaxThreadsConstraints()));
         iterators.add(new ArrayIterator(this.bean.getMessageDestinationDescriptors()));
         iterators.add(new ArrayIterator(this.bean.getMinThreadsConstraints()));
         iterators.add(new ArrayIterator(this.bean.getModules()));
         if (this.bean.getOsgiFrameworkReference() != null) {
            iterators.add(new ArrayIterator(new OsgiFrameworkReferenceBean[]{this.bean.getOsgiFrameworkReference()}));
         }

         if (this.bean.getPreferApplicationPackages() != null) {
            iterators.add(new ArrayIterator(new PreferApplicationPackagesBean[]{this.bean.getPreferApplicationPackages()}));
         }

         if (this.bean.getPreferApplicationResources() != null) {
            iterators.add(new ArrayIterator(new PreferApplicationResourcesBean[]{this.bean.getPreferApplicationResources()}));
         }

         iterators.add(new ArrayIterator(this.bean.getResourceDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getResourceEnvDescriptions()));
         iterators.add(new ArrayIterator(this.bean.getResponseTimeRequests()));
         if (this.bean.getSecurity() != null) {
            iterators.add(new ArrayIterator(new SecurityBean[]{this.bean.getSecurity()}));
         }

         iterators.add(new ArrayIterator(this.bean.getServiceReferenceDescriptions()));
         if (this.bean.getSessionDescriptor() != null) {
            iterators.add(new ArrayIterator(new SessionDescriptorBean[]{this.bean.getSessionDescriptor()}));
         }

         iterators.add(new ArrayIterator(this.bean.getShutdowns()));
         iterators.add(new ArrayIterator(this.bean.getSingletonServices()));
         iterators.add(new ArrayIterator(this.bean.getStartups()));
         iterators.add(new ArrayIterator(this.bean.getWorkManagers()));
         if (this.bean.getXml() != null) {
            iterators.add(new ArrayIterator(new XmlBean[]{this.bean.getXml()}));
         }

         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            childValue = this.computeChildHashValue(this.bean.getApplicationAdminModeTrigger());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getApplicationParams().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getApplicationParams()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getCapacities().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCapacities()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCdiDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getClassLoading());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getClassloaderStructure());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceClusterRef());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isComponentFactoryClassNameSet()) {
               buf.append("ComponentFactoryClassName");
               buf.append(String.valueOf(this.bean.getComponentFactoryClassName()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getContextRequests().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getContextRequests()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getEjb());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEjbReferenceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEjbReferenceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getFairShareRequests().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFairShareRequests()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getFastSwap());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getJDBCConnectionPools().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJDBCConnectionPools()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLibraryContextRootOverrides().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLibraryContextRootOverrides()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getLibraryRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLibraryRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getListeners().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getListeners()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedExecutorServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedExecutorServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedScheduledExecutorServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedScheduledExecutorServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedThreadFactories().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedThreadFactories()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMaxThreadsConstraints().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMaxThreadsConstraints()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMessageDestinationDescriptors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMessageDestinationDescriptors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMinThreadsConstraints().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMinThreadsConstraints()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getModules().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getModules()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getOsgiFrameworkReference());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPreferApplicationPackages());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPreferApplicationResources());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isReadyRegistrationSet()) {
               buf.append("ReadyRegistration");
               buf.append(String.valueOf(this.bean.getReadyRegistration()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResourceEnvDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResourceEnvDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResponseTimeRequests().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResponseTimeRequests()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSecurity());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getServiceReferenceDescriptions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getServiceReferenceDescriptions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSessionDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getShutdowns().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getShutdowns()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getSingletonServices().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getSingletonServices()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getStartups().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getStartups()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWorkManagers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWorkManagers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getXml());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            WeblogicApplicationBeanImpl otherTyped = (WeblogicApplicationBeanImpl)other;
            this.computeChildDiff("ApplicationAdminModeTrigger", this.bean.getApplicationAdminModeTrigger(), otherTyped.getApplicationAdminModeTrigger(), false);
            this.computeChildDiff("ApplicationParams", this.bean.getApplicationParams(), otherTyped.getApplicationParams(), false);
            this.computeChildDiff("Capacities", this.bean.getCapacities(), otherTyped.getCapacities(), false);
            this.computeSubDiff("CdiDescriptor", this.bean.getCdiDescriptor(), otherTyped.getCdiDescriptor());
            this.computeSubDiff("ClassLoading", this.bean.getClassLoading(), otherTyped.getClassLoading());
            this.computeChildDiff("ClassloaderStructure", this.bean.getClassloaderStructure(), otherTyped.getClassloaderStructure(), false);
            this.computeChildDiff("CoherenceClusterRef", this.bean.getCoherenceClusterRef(), otherTyped.getCoherenceClusterRef(), false);
            this.computeDiff("ComponentFactoryClassName", this.bean.getComponentFactoryClassName(), otherTyped.getComponentFactoryClassName(), false);
            this.computeChildDiff("ContextRequests", this.bean.getContextRequests(), otherTyped.getContextRequests(), false);
            this.computeChildDiff("Ejb", this.bean.getEjb(), otherTyped.getEjb(), false);
            this.computeChildDiff("EjbReferenceDescriptions", this.bean.getEjbReferenceDescriptions(), otherTyped.getEjbReferenceDescriptions(), false);
            this.computeChildDiff("FairShareRequests", this.bean.getFairShareRequests(), otherTyped.getFairShareRequests(), false);
            this.computeChildDiff("FastSwap", this.bean.getFastSwap(), otherTyped.getFastSwap(), false);
            this.computeChildDiff("JDBCConnectionPools", this.bean.getJDBCConnectionPools(), otherTyped.getJDBCConnectionPools(), false);
            this.computeChildDiff("LibraryContextRootOverrides", this.bean.getLibraryContextRootOverrides(), otherTyped.getLibraryContextRootOverrides(), false);
            this.computeChildDiff("LibraryRefs", this.bean.getLibraryRefs(), otherTyped.getLibraryRefs(), false);
            this.computeChildDiff("Listeners", this.bean.getListeners(), otherTyped.getListeners(), false);
            this.computeChildDiff("ManagedExecutorServices", this.bean.getManagedExecutorServices(), otherTyped.getManagedExecutorServices(), false);
            this.computeChildDiff("ManagedScheduledExecutorServices", this.bean.getManagedScheduledExecutorServices(), otherTyped.getManagedScheduledExecutorServices(), false);
            this.computeChildDiff("ManagedThreadFactories", this.bean.getManagedThreadFactories(), otherTyped.getManagedThreadFactories(), false);
            this.computeChildDiff("MaxThreadsConstraints", this.bean.getMaxThreadsConstraints(), otherTyped.getMaxThreadsConstraints(), false);
            this.computeChildDiff("MessageDestinationDescriptors", this.bean.getMessageDestinationDescriptors(), otherTyped.getMessageDestinationDescriptors(), false);
            this.computeChildDiff("MinThreadsConstraints", this.bean.getMinThreadsConstraints(), otherTyped.getMinThreadsConstraints(), false);
            this.computeChildDiff("Modules", this.bean.getModules(), otherTyped.getModules(), false);
            this.computeChildDiff("OsgiFrameworkReference", this.bean.getOsgiFrameworkReference(), otherTyped.getOsgiFrameworkReference(), false);
            this.computeChildDiff("PreferApplicationPackages", this.bean.getPreferApplicationPackages(), otherTyped.getPreferApplicationPackages(), false);
            this.computeChildDiff("PreferApplicationResources", this.bean.getPreferApplicationResources(), otherTyped.getPreferApplicationResources(), false);
            this.computeDiff("ReadyRegistration", this.bean.getReadyRegistration(), otherTyped.getReadyRegistration(), false);
            this.computeChildDiff("ResourceDescriptions", this.bean.getResourceDescriptions(), otherTyped.getResourceDescriptions(), false);
            this.computeChildDiff("ResourceEnvDescriptions", this.bean.getResourceEnvDescriptions(), otherTyped.getResourceEnvDescriptions(), false);
            this.computeChildDiff("ResponseTimeRequests", this.bean.getResponseTimeRequests(), otherTyped.getResponseTimeRequests(), false);
            this.computeChildDiff("Security", this.bean.getSecurity(), otherTyped.getSecurity(), false);
            this.computeChildDiff("ServiceReferenceDescriptions", this.bean.getServiceReferenceDescriptions(), otherTyped.getServiceReferenceDescriptions(), false);
            this.computeSubDiff("SessionDescriptor", this.bean.getSessionDescriptor(), otherTyped.getSessionDescriptor());
            this.computeChildDiff("Shutdowns", this.bean.getShutdowns(), otherTyped.getShutdowns(), false);
            this.computeChildDiff("SingletonServices", this.bean.getSingletonServices(), otherTyped.getSingletonServices(), false);
            this.computeChildDiff("Startups", this.bean.getStartups(), otherTyped.getStartups(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeChildDiff("WorkManagers", this.bean.getWorkManagers(), otherTyped.getWorkManagers(), false);
            this.computeChildDiff("Xml", this.bean.getXml(), otherTyped.getXml(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicApplicationBeanImpl original = (WeblogicApplicationBeanImpl)event.getSourceBean();
            WeblogicApplicationBeanImpl proposed = (WeblogicApplicationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ApplicationAdminModeTrigger")) {
                  if (type == 2) {
                     original.setApplicationAdminModeTrigger((ApplicationAdminModeTriggerBean)this.createCopy((AbstractDescriptorBean)proposed.getApplicationAdminModeTrigger()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ApplicationAdminModeTrigger", (DescriptorBean)original.getApplicationAdminModeTrigger());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("ApplicationParams")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addApplicationParam((ApplicationParamBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeApplicationParam((ApplicationParamBean)update.getRemovedObject());
                  }

                  if (original.getApplicationParams() == null || original.getApplicationParams().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("Capacities")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCapacity((CapacityBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCapacity((CapacityBean)update.getRemovedObject());
                  }

                  if (original.getCapacities() == null || original.getCapacities().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  }
               } else if (prop.equals("CdiDescriptor")) {
                  if (type == 2) {
                     original.setCdiDescriptor((CdiDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getCdiDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CdiDescriptor", (DescriptorBean)original.getCdiDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("ClassLoading")) {
                  if (type == 2) {
                     original.setClassLoading((ClassLoadingBean)this.createCopy((AbstractDescriptorBean)proposed.getClassLoading()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ClassLoading", (DescriptorBean)original.getClassLoading());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("ClassloaderStructure")) {
                  if (type == 2) {
                     original.setClassloaderStructure((ClassloaderStructureBean)this.createCopy((AbstractDescriptorBean)proposed.getClassloaderStructure()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ClassloaderStructure", (DescriptorBean)original.getClassloaderStructure());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("CoherenceClusterRef")) {
                  if (type == 2) {
                     original.setCoherenceClusterRef((CoherenceClusterRefBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceClusterRef()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceClusterRef", (DescriptorBean)original.getCoherenceClusterRef());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("ComponentFactoryClassName")) {
                  original.setComponentFactoryClassName(proposed.getComponentFactoryClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("ContextRequests")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addContextRequest((ContextRequestClassBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeContextRequest((ContextRequestClassBean)update.getRemovedObject());
                  }

                  if (original.getContextRequests() == null || original.getContextRequests().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("Ejb")) {
                  if (type == 2) {
                     original.setEjb((EjbBean)this.createCopy((AbstractDescriptorBean)proposed.getEjb()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Ejb", (DescriptorBean)original.getEjb());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("EjbReferenceDescriptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEjbReferenceDescription((EjbReferenceDescriptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEjbReferenceDescription((EjbReferenceDescriptionBean)update.getRemovedObject());
                  }

                  if (original.getEjbReferenceDescriptions() == null || original.getEjbReferenceDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 32);
                  }
               } else if (prop.equals("FairShareRequests")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFairShareRequest((FairShareRequestClassBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFairShareRequest((FairShareRequestClassBean)update.getRemovedObject());
                  }

                  if (original.getFairShareRequests() == null || original.getFairShareRequests().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("FastSwap")) {
                  if (type == 2) {
                     original.setFastSwap((FastSwapBean)this.createCopy((AbstractDescriptorBean)proposed.getFastSwap()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("FastSwap", (DescriptorBean)original.getFastSwap());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("JDBCConnectionPools")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJDBCConnectionPool((JDBCConnectionPoolBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJDBCConnectionPool((JDBCConnectionPoolBean)update.getRemovedObject());
                  }

                  if (original.getJDBCConnectionPools() == null || original.getJDBCConnectionPools().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("LibraryContextRootOverrides")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLibraryContextRootOverride((LibraryContextRootOverrideBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLibraryContextRootOverride((LibraryContextRootOverrideBean)update.getRemovedObject());
                  }

                  if (original.getLibraryContextRootOverrides() == null || original.getLibraryContextRootOverrides().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  }
               } else if (prop.equals("LibraryRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLibraryRef((LibraryRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLibraryRef((LibraryRefBean)update.getRemovedObject());
                  }

                  if (original.getLibraryRefs() == null || original.getLibraryRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("Listeners")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addListener((ListenerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeListener((ListenerBean)update.getRemovedObject());
                  }

                  if (original.getListeners() == null || original.getListeners().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("ManagedExecutorServices")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedExecutorService((ManagedExecutorServiceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedExecutorService((ManagedExecutorServiceBean)update.getRemovedObject());
                  }

                  if (original.getManagedExecutorServices() == null || original.getManagedExecutorServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  }
               } else if (prop.equals("ManagedScheduledExecutorServices")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedScheduledExecutorService((ManagedScheduledExecutorServiceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedScheduledExecutorService((ManagedScheduledExecutorServiceBean)update.getRemovedObject());
                  }

                  if (original.getManagedScheduledExecutorServices() == null || original.getManagedScheduledExecutorServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  }
               } else if (prop.equals("ManagedThreadFactories")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedThreadFactory((ManagedThreadFactoryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedThreadFactory((ManagedThreadFactoryBean)update.getRemovedObject());
                  }

                  if (original.getManagedThreadFactories() == null || original.getManagedThreadFactories().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  }
               } else if (prop.equals("MaxThreadsConstraints")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMaxThreadsConstraint((MaxThreadsConstraintBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMaxThreadsConstraint((MaxThreadsConstraintBean)update.getRemovedObject());
                  }

                  if (original.getMaxThreadsConstraints() == null || original.getMaxThreadsConstraints().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("MessageDestinationDescriptors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMessageDestinationDescriptor((MessageDestinationDescriptorBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageDestinationDescriptor((MessageDestinationDescriptorBean)update.getRemovedObject());
                  }

                  if (original.getMessageDestinationDescriptors() == null || original.getMessageDestinationDescriptors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 34);
                  }
               } else if (prop.equals("MinThreadsConstraints")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMinThreadsConstraint((MinThreadsConstraintBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMinThreadsConstraint((MinThreadsConstraintBean)update.getRemovedObject());
                  }

                  if (original.getMinThreadsConstraints() == null || original.getMinThreadsConstraints().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  }
               } else if (prop.equals("Modules")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addModule((WeblogicModuleBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeModule((WeblogicModuleBean)update.getRemovedObject());
                  }

                  if (original.getModules() == null || original.getModules().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("OsgiFrameworkReference")) {
                  if (type == 2) {
                     original.setOsgiFrameworkReference((OsgiFrameworkReferenceBean)this.createCopy((AbstractDescriptorBean)proposed.getOsgiFrameworkReference()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("OsgiFrameworkReference", (DescriptorBean)original.getOsgiFrameworkReference());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("PreferApplicationPackages")) {
                  if (type == 2) {
                     original.setPreferApplicationPackages((PreferApplicationPackagesBean)this.createCopy((AbstractDescriptorBean)proposed.getPreferApplicationPackages()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PreferApplicationPackages", (DescriptorBean)original.getPreferApplicationPackages());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("PreferApplicationResources")) {
                  if (type == 2) {
                     original.setPreferApplicationResources((PreferApplicationResourcesBean)this.createCopy((AbstractDescriptorBean)proposed.getPreferApplicationResources()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PreferApplicationResources", (DescriptorBean)original.getPreferApplicationResources());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("ReadyRegistration")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("ResourceDescriptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResourceDescription((ResourceDescriptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResourceDescription((ResourceDescriptionBean)update.getRemovedObject());
                  }

                  if (original.getResourceDescriptions() == null || original.getResourceDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  }
               } else if (prop.equals("ResourceEnvDescriptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResourceEnvDescription((ResourceEnvDescriptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResourceEnvDescription((ResourceEnvDescriptionBean)update.getRemovedObject());
                  }

                  if (original.getResourceEnvDescriptions() == null || original.getResourceEnvDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 31);
                  }
               } else if (prop.equals("ResponseTimeRequests")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResponseTimeRequest((ResponseTimeRequestClassBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResponseTimeRequest((ResponseTimeRequestClassBean)update.getRemovedObject());
                  }

                  if (original.getResponseTimeRequests() == null || original.getResponseTimeRequests().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("Security")) {
                  if (type == 2) {
                     original.setSecurity((SecurityBean)this.createCopy((AbstractDescriptorBean)proposed.getSecurity()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Security", (DescriptorBean)original.getSecurity());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ServiceReferenceDescriptions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addServiceReferenceDescription((ServiceReferenceDescriptionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeServiceReferenceDescription((ServiceReferenceDescriptionBean)update.getRemovedObject());
                  }

                  if (original.getServiceReferenceDescriptions() == null || original.getServiceReferenceDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  }
               } else if (prop.equals("SessionDescriptor")) {
                  if (type == 2) {
                     original.setSessionDescriptor((SessionDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getSessionDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SessionDescriptor", (DescriptorBean)original.getSessionDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("Shutdowns")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addShutdown((ShutdownBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeShutdown((ShutdownBean)update.getRemovedObject());
                  }

                  if (original.getShutdowns() == null || original.getShutdowns().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("SingletonServices")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addSingletonService((SingletonServiceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSingletonService((SingletonServiceBean)update.getRemovedObject());
                  }

                  if (original.getSingletonServices() == null || original.getSingletonServices().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("Startups")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addStartup((StartupBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeStartup((StartupBean)update.getRemovedObject());
                  }

                  if (original.getStartups() == null || original.getStartups().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("WorkManagers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWorkManager((WorkManagerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWorkManager((WorkManagerBean)update.getRemovedObject());
                  }

                  if (original.getWorkManagers() == null || original.getWorkManagers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  }
               } else if (prop.equals("Xml")) {
                  if (type == 2) {
                     original.setXml((XmlBean)this.createCopy((AbstractDescriptorBean)proposed.getXml()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Xml", (DescriptorBean)original.getXml());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            WeblogicApplicationBeanImpl copy = (WeblogicApplicationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ApplicationAdminModeTrigger")) && this.bean.isApplicationAdminModeTriggerSet() && !copy._isSet(23)) {
               Object o = this.bean.getApplicationAdminModeTrigger();
               copy.setApplicationAdminModeTrigger((ApplicationAdminModeTriggerBean)null);
               copy.setApplicationAdminModeTrigger(o == null ? null : (ApplicationAdminModeTriggerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("ApplicationParams")) && this.bean.isApplicationParamsSet() && !copy._isSet(4)) {
               ApplicationParamBean[] oldApplicationParams = this.bean.getApplicationParams();
               ApplicationParamBean[] newApplicationParams = new ApplicationParamBean[oldApplicationParams.length];

               for(i = 0; i < newApplicationParams.length; ++i) {
                  newApplicationParams[i] = (ApplicationParamBean)((ApplicationParamBean)this.createCopy((AbstractDescriptorBean)oldApplicationParams[i], includeObsolete));
               }

               copy.setApplicationParams(newApplicationParams);
            }

            if ((excludeProps == null || !excludeProps.contains("Capacities")) && this.bean.isCapacitiesSet() && !copy._isSet(17)) {
               CapacityBean[] oldCapacities = this.bean.getCapacities();
               CapacityBean[] newCapacities = new CapacityBean[oldCapacities.length];

               for(i = 0; i < newCapacities.length; ++i) {
                  newCapacities[i] = (CapacityBean)((CapacityBean)this.createCopy((AbstractDescriptorBean)oldCapacities[i], includeObsolete));
               }

               copy.setCapacities(newCapacities);
            }

            if ((excludeProps == null || !excludeProps.contains("CdiDescriptor")) && this.bean.isCdiDescriptorSet() && !copy._isSet(39)) {
               Object o = this.bean.getCdiDescriptor();
               copy.setCdiDescriptor((CdiDescriptorBean)null);
               copy.setCdiDescriptor(o == null ? null : (CdiDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ClassLoading")) && this.bean.isClassLoadingSet() && !copy._isSet(37)) {
               Object o = this.bean.getClassLoading();
               copy.setClassLoading((ClassLoadingBean)null);
               copy.setClassLoading(o == null ? null : (ClassLoadingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ClassloaderStructure")) && this.bean.isClassloaderStructureSet() && !copy._isSet(5)) {
               Object o = this.bean.getClassloaderStructure();
               copy.setClassloaderStructure((ClassloaderStructureBean)null);
               copy.setClassloaderStructure(o == null ? null : (ClassloaderStructureBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterRef")) && this.bean.isCoherenceClusterRefSet() && !copy._isSet(29)) {
               Object o = this.bean.getCoherenceClusterRef();
               copy.setCoherenceClusterRef((CoherenceClusterRefBean)null);
               copy.setCoherenceClusterRef(o == null ? null : (CoherenceClusterRefBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ComponentFactoryClassName")) && this.bean.isComponentFactoryClassNameSet()) {
               copy.setComponentFactoryClassName(this.bean.getComponentFactoryClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("ContextRequests")) && this.bean.isContextRequestsSet() && !copy._isSet(14)) {
               ContextRequestClassBean[] oldContextRequests = this.bean.getContextRequests();
               ContextRequestClassBean[] newContextRequests = new ContextRequestClassBean[oldContextRequests.length];

               for(i = 0; i < newContextRequests.length; ++i) {
                  newContextRequests[i] = (ContextRequestClassBean)((ContextRequestClassBean)this.createCopy((AbstractDescriptorBean)oldContextRequests[i], includeObsolete));
               }

               copy.setContextRequests(newContextRequests);
            }

            if ((excludeProps == null || !excludeProps.contains("Ejb")) && this.bean.isEjbSet() && !copy._isSet(0)) {
               Object o = this.bean.getEjb();
               copy.setEjb((EjbBean)null);
               copy.setEjb(o == null ? null : (EjbBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EjbReferenceDescriptions")) && this.bean.isEjbReferenceDescriptionsSet() && !copy._isSet(32)) {
               EjbReferenceDescriptionBean[] oldEjbReferenceDescriptions = this.bean.getEjbReferenceDescriptions();
               EjbReferenceDescriptionBean[] newEjbReferenceDescriptions = new EjbReferenceDescriptionBean[oldEjbReferenceDescriptions.length];

               for(i = 0; i < newEjbReferenceDescriptions.length; ++i) {
                  newEjbReferenceDescriptions[i] = (EjbReferenceDescriptionBean)((EjbReferenceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldEjbReferenceDescriptions[i], includeObsolete));
               }

               copy.setEjbReferenceDescriptions(newEjbReferenceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("FairShareRequests")) && this.bean.isFairShareRequestsSet() && !copy._isSet(12)) {
               FairShareRequestClassBean[] oldFairShareRequests = this.bean.getFairShareRequests();
               FairShareRequestClassBean[] newFairShareRequests = new FairShareRequestClassBean[oldFairShareRequests.length];

               for(i = 0; i < newFairShareRequests.length; ++i) {
                  newFairShareRequests[i] = (FairShareRequestClassBean)((FairShareRequestClassBean)this.createCopy((AbstractDescriptorBean)oldFairShareRequests[i], includeObsolete));
               }

               copy.setFairShareRequests(newFairShareRequests);
            }

            if ((excludeProps == null || !excludeProps.contains("FastSwap")) && this.bean.isFastSwapSet() && !copy._isSet(28)) {
               Object o = this.bean.getFastSwap();
               copy.setFastSwap((FastSwapBean)null);
               copy.setFastSwap(o == null ? null : (FastSwapBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCConnectionPools")) && this.bean.isJDBCConnectionPoolsSet() && !copy._isSet(2)) {
               JDBCConnectionPoolBean[] oldJDBCConnectionPools = this.bean.getJDBCConnectionPools();
               JDBCConnectionPoolBean[] newJDBCConnectionPools = new JDBCConnectionPoolBean[oldJDBCConnectionPools.length];

               for(i = 0; i < newJDBCConnectionPools.length; ++i) {
                  newJDBCConnectionPools[i] = (JDBCConnectionPoolBean)((JDBCConnectionPoolBean)this.createCopy((AbstractDescriptorBean)oldJDBCConnectionPools[i], includeObsolete));
               }

               copy.setJDBCConnectionPools(newJDBCConnectionPools);
            }

            if ((excludeProps == null || !excludeProps.contains("LibraryContextRootOverrides")) && this.bean.isLibraryContextRootOverridesSet() && !copy._isSet(25)) {
               LibraryContextRootOverrideBean[] oldLibraryContextRootOverrides = this.bean.getLibraryContextRootOverrides();
               LibraryContextRootOverrideBean[] newLibraryContextRootOverrides = new LibraryContextRootOverrideBean[oldLibraryContextRootOverrides.length];

               for(i = 0; i < newLibraryContextRootOverrides.length; ++i) {
                  newLibraryContextRootOverrides[i] = (LibraryContextRootOverrideBean)((LibraryContextRootOverrideBean)this.createCopy((AbstractDescriptorBean)oldLibraryContextRootOverrides[i], includeObsolete));
               }

               copy.setLibraryContextRootOverrides(newLibraryContextRootOverrides);
            }

            if ((excludeProps == null || !excludeProps.contains("LibraryRefs")) && this.bean.isLibraryRefsSet() && !copy._isSet(11)) {
               LibraryRefBean[] oldLibraryRefs = this.bean.getLibraryRefs();
               LibraryRefBean[] newLibraryRefs = new LibraryRefBean[oldLibraryRefs.length];

               for(i = 0; i < newLibraryRefs.length; ++i) {
                  newLibraryRefs[i] = (LibraryRefBean)((LibraryRefBean)this.createCopy((AbstractDescriptorBean)oldLibraryRefs[i], includeObsolete));
               }

               copy.setLibraryRefs(newLibraryRefs);
            }

            if ((excludeProps == null || !excludeProps.contains("Listeners")) && this.bean.isListenersSet() && !copy._isSet(6)) {
               ListenerBean[] oldListeners = this.bean.getListeners();
               ListenerBean[] newListeners = new ListenerBean[oldListeners.length];

               for(i = 0; i < newListeners.length; ++i) {
                  newListeners[i] = (ListenerBean)((ListenerBean)this.createCopy((AbstractDescriptorBean)oldListeners[i], includeObsolete));
               }

               copy.setListeners(newListeners);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedExecutorServices")) && this.bean.isManagedExecutorServicesSet() && !copy._isSet(19)) {
               ManagedExecutorServiceBean[] oldManagedExecutorServices = this.bean.getManagedExecutorServices();
               ManagedExecutorServiceBean[] newManagedExecutorServices = new ManagedExecutorServiceBean[oldManagedExecutorServices.length];

               for(i = 0; i < newManagedExecutorServices.length; ++i) {
                  newManagedExecutorServices[i] = (ManagedExecutorServiceBean)((ManagedExecutorServiceBean)this.createCopy((AbstractDescriptorBean)oldManagedExecutorServices[i], includeObsolete));
               }

               copy.setManagedExecutorServices(newManagedExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedScheduledExecutorServices")) && this.bean.isManagedScheduledExecutorServicesSet() && !copy._isSet(20)) {
               ManagedScheduledExecutorServiceBean[] oldManagedScheduledExecutorServices = this.bean.getManagedScheduledExecutorServices();
               ManagedScheduledExecutorServiceBean[] newManagedScheduledExecutorServices = new ManagedScheduledExecutorServiceBean[oldManagedScheduledExecutorServices.length];

               for(i = 0; i < newManagedScheduledExecutorServices.length; ++i) {
                  newManagedScheduledExecutorServices[i] = (ManagedScheduledExecutorServiceBean)((ManagedScheduledExecutorServiceBean)this.createCopy((AbstractDescriptorBean)oldManagedScheduledExecutorServices[i], includeObsolete));
               }

               copy.setManagedScheduledExecutorServices(newManagedScheduledExecutorServices);
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedThreadFactories")) && this.bean.isManagedThreadFactoriesSet() && !copy._isSet(21)) {
               ManagedThreadFactoryBean[] oldManagedThreadFactories = this.bean.getManagedThreadFactories();
               ManagedThreadFactoryBean[] newManagedThreadFactories = new ManagedThreadFactoryBean[oldManagedThreadFactories.length];

               for(i = 0; i < newManagedThreadFactories.length; ++i) {
                  newManagedThreadFactories[i] = (ManagedThreadFactoryBean)((ManagedThreadFactoryBean)this.createCopy((AbstractDescriptorBean)oldManagedThreadFactories[i], includeObsolete));
               }

               copy.setManagedThreadFactories(newManagedThreadFactories);
            }

            if ((excludeProps == null || !excludeProps.contains("MaxThreadsConstraints")) && this.bean.isMaxThreadsConstraintsSet() && !copy._isSet(15)) {
               MaxThreadsConstraintBean[] oldMaxThreadsConstraints = this.bean.getMaxThreadsConstraints();
               MaxThreadsConstraintBean[] newMaxThreadsConstraints = new MaxThreadsConstraintBean[oldMaxThreadsConstraints.length];

               for(i = 0; i < newMaxThreadsConstraints.length; ++i) {
                  newMaxThreadsConstraints[i] = (MaxThreadsConstraintBean)((MaxThreadsConstraintBean)this.createCopy((AbstractDescriptorBean)oldMaxThreadsConstraints[i], includeObsolete));
               }

               copy.setMaxThreadsConstraints(newMaxThreadsConstraints);
            }

            if ((excludeProps == null || !excludeProps.contains("MessageDestinationDescriptors")) && this.bean.isMessageDestinationDescriptorsSet() && !copy._isSet(34)) {
               MessageDestinationDescriptorBean[] oldMessageDestinationDescriptors = this.bean.getMessageDestinationDescriptors();
               MessageDestinationDescriptorBean[] newMessageDestinationDescriptors = new MessageDestinationDescriptorBean[oldMessageDestinationDescriptors.length];

               for(i = 0; i < newMessageDestinationDescriptors.length; ++i) {
                  newMessageDestinationDescriptors[i] = (MessageDestinationDescriptorBean)((MessageDestinationDescriptorBean)this.createCopy((AbstractDescriptorBean)oldMessageDestinationDescriptors[i], includeObsolete));
               }

               copy.setMessageDestinationDescriptors(newMessageDestinationDescriptors);
            }

            if ((excludeProps == null || !excludeProps.contains("MinThreadsConstraints")) && this.bean.isMinThreadsConstraintsSet() && !copy._isSet(16)) {
               MinThreadsConstraintBean[] oldMinThreadsConstraints = this.bean.getMinThreadsConstraints();
               MinThreadsConstraintBean[] newMinThreadsConstraints = new MinThreadsConstraintBean[oldMinThreadsConstraints.length];

               for(i = 0; i < newMinThreadsConstraints.length; ++i) {
                  newMinThreadsConstraints[i] = (MinThreadsConstraintBean)((MinThreadsConstraintBean)this.createCopy((AbstractDescriptorBean)oldMinThreadsConstraints[i], includeObsolete));
               }

               copy.setMinThreadsConstraints(newMinThreadsConstraints);
            }

            if ((excludeProps == null || !excludeProps.contains("Modules")) && this.bean.isModulesSet() && !copy._isSet(10)) {
               WeblogicModuleBean[] oldModules = this.bean.getModules();
               WeblogicModuleBean[] newModules = new WeblogicModuleBean[oldModules.length];

               for(i = 0; i < newModules.length; ++i) {
                  newModules[i] = (WeblogicModuleBean)((WeblogicModuleBean)this.createCopy((AbstractDescriptorBean)oldModules[i], includeObsolete));
               }

               copy.setModules(newModules);
            }

            if ((excludeProps == null || !excludeProps.contains("OsgiFrameworkReference")) && this.bean.isOsgiFrameworkReferenceSet() && !copy._isSet(36)) {
               Object o = this.bean.getOsgiFrameworkReference();
               copy.setOsgiFrameworkReference((OsgiFrameworkReferenceBean)null);
               copy.setOsgiFrameworkReference(o == null ? null : (OsgiFrameworkReferenceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PreferApplicationPackages")) && this.bean.isPreferApplicationPackagesSet() && !copy._isSet(26)) {
               Object o = this.bean.getPreferApplicationPackages();
               copy.setPreferApplicationPackages((PreferApplicationPackagesBean)null);
               copy.setPreferApplicationPackages(o == null ? null : (PreferApplicationPackagesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PreferApplicationResources")) && this.bean.isPreferApplicationResourcesSet() && !copy._isSet(27)) {
               Object o = this.bean.getPreferApplicationResources();
               copy.setPreferApplicationResources((PreferApplicationResourcesBean)null);
               copy.setPreferApplicationResources(o == null ? null : (PreferApplicationResourcesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ReadyRegistration")) && this.bean.isReadyRegistrationSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceDescriptions")) && this.bean.isResourceDescriptionsSet() && !copy._isSet(30)) {
               ResourceDescriptionBean[] oldResourceDescriptions = this.bean.getResourceDescriptions();
               ResourceDescriptionBean[] newResourceDescriptions = new ResourceDescriptionBean[oldResourceDescriptions.length];

               for(i = 0; i < newResourceDescriptions.length; ++i) {
                  newResourceDescriptions[i] = (ResourceDescriptionBean)((ResourceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldResourceDescriptions[i], includeObsolete));
               }

               copy.setResourceDescriptions(newResourceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceEnvDescriptions")) && this.bean.isResourceEnvDescriptionsSet() && !copy._isSet(31)) {
               ResourceEnvDescriptionBean[] oldResourceEnvDescriptions = this.bean.getResourceEnvDescriptions();
               ResourceEnvDescriptionBean[] newResourceEnvDescriptions = new ResourceEnvDescriptionBean[oldResourceEnvDescriptions.length];

               for(i = 0; i < newResourceEnvDescriptions.length; ++i) {
                  newResourceEnvDescriptions[i] = (ResourceEnvDescriptionBean)((ResourceEnvDescriptionBean)this.createCopy((AbstractDescriptorBean)oldResourceEnvDescriptions[i], includeObsolete));
               }

               copy.setResourceEnvDescriptions(newResourceEnvDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseTimeRequests")) && this.bean.isResponseTimeRequestsSet() && !copy._isSet(13)) {
               ResponseTimeRequestClassBean[] oldResponseTimeRequests = this.bean.getResponseTimeRequests();
               ResponseTimeRequestClassBean[] newResponseTimeRequests = new ResponseTimeRequestClassBean[oldResponseTimeRequests.length];

               for(i = 0; i < newResponseTimeRequests.length; ++i) {
                  newResponseTimeRequests[i] = (ResponseTimeRequestClassBean)((ResponseTimeRequestClassBean)this.createCopy((AbstractDescriptorBean)oldResponseTimeRequests[i], includeObsolete));
               }

               copy.setResponseTimeRequests(newResponseTimeRequests);
            }

            if ((excludeProps == null || !excludeProps.contains("Security")) && this.bean.isSecuritySet() && !copy._isSet(3)) {
               Object o = this.bean.getSecurity();
               copy.setSecurity((SecurityBean)null);
               copy.setSecurity(o == null ? null : (SecurityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceReferenceDescriptions")) && this.bean.isServiceReferenceDescriptionsSet() && !copy._isSet(33)) {
               ServiceReferenceDescriptionBean[] oldServiceReferenceDescriptions = this.bean.getServiceReferenceDescriptions();
               ServiceReferenceDescriptionBean[] newServiceReferenceDescriptions = new ServiceReferenceDescriptionBean[oldServiceReferenceDescriptions.length];

               for(i = 0; i < newServiceReferenceDescriptions.length; ++i) {
                  newServiceReferenceDescriptions[i] = (ServiceReferenceDescriptionBean)((ServiceReferenceDescriptionBean)this.createCopy((AbstractDescriptorBean)oldServiceReferenceDescriptions[i], includeObsolete));
               }

               copy.setServiceReferenceDescriptions(newServiceReferenceDescriptions);
            }

            if ((excludeProps == null || !excludeProps.contains("SessionDescriptor")) && this.bean.isSessionDescriptorSet() && !copy._isSet(24)) {
               Object o = this.bean.getSessionDescriptor();
               copy.setSessionDescriptor((SessionDescriptorBean)null);
               copy.setSessionDescriptor(o == null ? null : (SessionDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Shutdowns")) && this.bean.isShutdownsSet() && !copy._isSet(9)) {
               ShutdownBean[] oldShutdowns = this.bean.getShutdowns();
               ShutdownBean[] newShutdowns = new ShutdownBean[oldShutdowns.length];

               for(i = 0; i < newShutdowns.length; ++i) {
                  newShutdowns[i] = (ShutdownBean)((ShutdownBean)this.createCopy((AbstractDescriptorBean)oldShutdowns[i], includeObsolete));
               }

               copy.setShutdowns(newShutdowns);
            }

            if ((excludeProps == null || !excludeProps.contains("SingletonServices")) && this.bean.isSingletonServicesSet() && !copy._isSet(7)) {
               SingletonServiceBean[] oldSingletonServices = this.bean.getSingletonServices();
               SingletonServiceBean[] newSingletonServices = new SingletonServiceBean[oldSingletonServices.length];

               for(i = 0; i < newSingletonServices.length; ++i) {
                  newSingletonServices[i] = (SingletonServiceBean)((SingletonServiceBean)this.createCopy((AbstractDescriptorBean)oldSingletonServices[i], includeObsolete));
               }

               copy.setSingletonServices(newSingletonServices);
            }

            if ((excludeProps == null || !excludeProps.contains("Startups")) && this.bean.isStartupsSet() && !copy._isSet(8)) {
               StartupBean[] oldStartups = this.bean.getStartups();
               StartupBean[] newStartups = new StartupBean[oldStartups.length];

               for(i = 0; i < newStartups.length; ++i) {
                  newStartups[i] = (StartupBean)((StartupBean)this.createCopy((AbstractDescriptorBean)oldStartups[i], includeObsolete));
               }

               copy.setStartups(newStartups);
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManagers")) && this.bean.isWorkManagersSet() && !copy._isSet(18)) {
               WorkManagerBean[] oldWorkManagers = this.bean.getWorkManagers();
               WorkManagerBean[] newWorkManagers = new WorkManagerBean[oldWorkManagers.length];

               for(i = 0; i < newWorkManagers.length; ++i) {
                  newWorkManagers[i] = (WorkManagerBean)((WorkManagerBean)this.createCopy((AbstractDescriptorBean)oldWorkManagers[i], includeObsolete));
               }

               copy.setWorkManagers(newWorkManagers);
            }

            if ((excludeProps == null || !excludeProps.contains("Xml")) && this.bean.isXmlSet() && !copy._isSet(1)) {
               Object o = this.bean.getXml();
               copy.setXml((XmlBean)null);
               copy.setXml(o == null ? null : (XmlBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getApplicationAdminModeTrigger(), clazz, annotation);
         this.inferSubTree(this.bean.getApplicationParams(), clazz, annotation);
         this.inferSubTree(this.bean.getCapacities(), clazz, annotation);
         this.inferSubTree(this.bean.getCdiDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getClassLoading(), clazz, annotation);
         this.inferSubTree(this.bean.getClassloaderStructure(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceClusterRef(), clazz, annotation);
         this.inferSubTree(this.bean.getContextRequests(), clazz, annotation);
         this.inferSubTree(this.bean.getEjb(), clazz, annotation);
         this.inferSubTree(this.bean.getEjbReferenceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getFairShareRequests(), clazz, annotation);
         this.inferSubTree(this.bean.getFastSwap(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCConnectionPools(), clazz, annotation);
         this.inferSubTree(this.bean.getLibraryContextRootOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getLibraryRefs(), clazz, annotation);
         this.inferSubTree(this.bean.getListeners(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedScheduledExecutorServices(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedThreadFactories(), clazz, annotation);
         this.inferSubTree(this.bean.getMaxThreadsConstraints(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageDestinationDescriptors(), clazz, annotation);
         this.inferSubTree(this.bean.getMinThreadsConstraints(), clazz, annotation);
         this.inferSubTree(this.bean.getModules(), clazz, annotation);
         this.inferSubTree(this.bean.getOsgiFrameworkReference(), clazz, annotation);
         this.inferSubTree(this.bean.getPreferApplicationPackages(), clazz, annotation);
         this.inferSubTree(this.bean.getPreferApplicationResources(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getResourceEnvDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getResponseTimeRequests(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurity(), clazz, annotation);
         this.inferSubTree(this.bean.getServiceReferenceDescriptions(), clazz, annotation);
         this.inferSubTree(this.bean.getSessionDescriptor(), clazz, annotation);
         this.inferSubTree(this.bean.getShutdowns(), clazz, annotation);
         this.inferSubTree(this.bean.getSingletonServices(), clazz, annotation);
         this.inferSubTree(this.bean.getStartups(), clazz, annotation);
         this.inferSubTree(this.bean.getWorkManagers(), clazz, annotation);
         this.inferSubTree(this.bean.getXml(), clazz, annotation);
      }
   }
}

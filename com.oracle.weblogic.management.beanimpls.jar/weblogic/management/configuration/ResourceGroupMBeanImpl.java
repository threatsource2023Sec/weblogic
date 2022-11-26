package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.mbeans.custom.ResourceGroup;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ResourceGroupMBeanImpl extends ResourceGroupTemplateMBeanImpl implements ResourceGroupMBean, Serializable {
   private boolean _Administrative;
   private boolean _AutoTargetAdminServer;
   private ResourceGroupTemplateMBean _ResourceGroupTemplate;
   private TargetMBean[] _Targets;
   private String _UploadDirectoryName;
   private boolean _UseDefaultTarget;
   private transient ResourceGroup _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ResourceGroupTemplateMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ResourceGroupTemplateMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ResourceGroupTemplateMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ResourceGroupTemplateMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ResourceGroupTemplateMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ResourceGroupTemplateMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ResourceGroupMBeanImpl() {
      try {
         this._customizer = new ResourceGroup(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ResourceGroupMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ResourceGroup(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ResourceGroupMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ResourceGroup(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public ResourceGroupTemplateMBean getResourceGroupTemplate() {
      return this._ResourceGroupTemplate;
   }

   public String getResourceGroupTemplateAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getResourceGroupTemplate();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isResourceGroupTemplateInherited() {
      return false;
   }

   public boolean isResourceGroupTemplateSet() {
      return this._isSet(32);
   }

   public void setResourceGroupTemplateAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ResourceGroupTemplateMBean.class, new ReferenceManager.Resolver(this, 32) {
            public void resolveReference(Object value) {
               try {
                  ResourceGroupMBeanImpl.this.setResourceGroupTemplate((ResourceGroupTemplateMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ResourceGroupTemplateMBean _oldVal = this._ResourceGroupTemplate;
         this._initializeProperty(32);
         this._setDelegateBean((ResourceGroupTemplateMBeanImpl)null);
         this._postSet(32, _oldVal, this._ResourceGroupTemplate);
      }

   }

   public void setResourceGroupTemplate(ResourceGroupTemplateMBean param0) {
      ResourceGroupValidator.validateSetResourceGroupTemplate(param0);
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 32, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ResourceGroupMBeanImpl.this.getResourceGroupTemplate();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      ResourceGroupTemplateMBean _oldVal = this._ResourceGroupTemplate;
      this._ResourceGroupTemplate = param0;
      if (param0 != _oldVal) {
         int var4;
         int var5;
         if (this._isSet(10)) {
            AppDeploymentMBean[] var3 = this.getAppDeployments();
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
               AppDeploymentMBean p = var3[var5];
               if (((AppDeploymentMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyAppDeployment(p);
               }
            }
         }

         if (this._isSet(24)) {
            CoherenceClusterSystemResourceMBean[] var8 = this.getCoherenceClusterSystemResources();
            var4 = var8.length;

            for(var5 = 0; var5 < var4; ++var5) {
               CoherenceClusterSystemResourceMBean p = var8[var5];
               if (((CoherenceClusterSystemResourceMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyCoherenceClusterSystemResource(p);
               }
            }
         }

         if (this._isSet(17)) {
            FileStoreMBean[] var9 = this.getFileStores();
            var4 = var9.length;

            for(var5 = 0; var5 < var4; ++var5) {
               FileStoreMBean p = var9[var5];
               if (((FileStoreMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyFileStore(p);
               }
            }
         }

         if (this._isSet(20)) {
            ForeignJNDIProviderMBean[] var10 = this.getForeignJNDIProviders();
            var4 = var10.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ForeignJNDIProviderMBean p = var10[var5];
               if (((ForeignJNDIProviderMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyForeignJNDIProvider(p);
               }
            }
         }

         if (this._isSet(18)) {
            JDBCStoreMBean[] var11 = this.getJDBCStores();
            var4 = var11.length;

            for(var5 = 0; var5 < var4; ++var5) {
               JDBCStoreMBean p = var11[var5];
               if (((JDBCStoreMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyJDBCStore(p);
               }
            }
         }

         if (this._isSet(21)) {
            JDBCSystemResourceMBean[] var12 = this.getJDBCSystemResources();
            var4 = var12.length;

            for(var5 = 0; var5 < var4; ++var5) {
               JDBCSystemResourceMBean p = var12[var5];
               if (((JDBCSystemResourceMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyJDBCSystemResource(p);
               }
            }
         }

         if (this._isSet(15)) {
            JMSBridgeDestinationMBean[] var13 = this.getJMSBridgeDestinations();
            var4 = var13.length;

            for(var5 = 0; var5 < var4; ++var5) {
               JMSBridgeDestinationMBean p = var13[var5];
               if (((JMSBridgeDestinationMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyJMSBridgeDestination(p);
               }
            }
         }

         if (this._isSet(12)) {
            JMSServerMBean[] var14 = this.getJMSServers();
            var4 = var14.length;

            for(var5 = 0; var5 < var4; ++var5) {
               JMSServerMBean p = var14[var5];
               if (((JMSServerMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyJMSServer(p);
               }
            }
         }

         if (this._isSet(19)) {
            JMSSystemResourceMBean[] var15 = this.getJMSSystemResources();
            var4 = var15.length;

            for(var5 = 0; var5 < var4; ++var5) {
               JMSSystemResourceMBean p = var15[var5];
               if (((JMSSystemResourceMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyJMSSystemResource(p);
               }
            }
         }

         if (this._isSet(11)) {
            LibraryMBean[] var16 = this.getLibraries();
            var4 = var16.length;

            for(var5 = 0; var5 < var4; ++var5) {
               LibraryMBean p = var16[var5];
               if (((LibraryMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyLibrary(p);
               }
            }
         }

         if (this._isSet(16)) {
            MailSessionMBean[] var18 = this.getMailSessions();
            var4 = var18.length;

            for(var5 = 0; var5 < var4; ++var5) {
               MailSessionMBean p = var18[var5];
               if (((MailSessionMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyMailSession(p);
               }
            }
         }

         if (this._isSet(26)) {
            ManagedExecutorServiceMBean[] var20 = this.getManagedExecutorServices();
            var4 = var20.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ManagedExecutorServiceMBean p = var20[var5];
               if (((ManagedExecutorServiceMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyManagedExecutorService(p);
               }
            }
         }

         if (this._isSet(27)) {
            ManagedScheduledExecutorServiceMBean[] var22 = this.getManagedScheduledExecutorServices();
            var4 = var22.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ManagedScheduledExecutorServiceMBean p = var22[var5];
               if (((ManagedScheduledExecutorServiceMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyManagedScheduledExecutorService(p);
               }
            }
         }

         if (this._isSet(28)) {
            ManagedThreadFactoryMBean[] var24 = this.getManagedThreadFactories();
            var4 = var24.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ManagedThreadFactoryMBean p = var24[var5];
               if (((ManagedThreadFactoryMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyManagedThreadFactory(p);
               }
            }
         }

         if (this._isSet(13)) {
            MessagingBridgeMBean[] var26 = this.getMessagingBridges();
            var4 = var26.length;

            for(var5 = 0; var5 < var4; ++var5) {
               MessagingBridgeMBean p = var26[var5];
               if (((MessagingBridgeMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyMessagingBridge(p);
               }
            }
         }

         if (this._isSet(25)) {
            OsgiFrameworkMBean[] var28 = this.getOsgiFrameworks();
            var4 = var28.length;

            for(var5 = 0; var5 < var4; ++var5) {
               OsgiFrameworkMBean p = var28[var5];
               if (((OsgiFrameworkMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyOsgiFramework(p);
               }
            }
         }

         if (this._isSet(14)) {
            PathServiceMBean[] var30 = this.getPathServices();
            var4 = var30.length;

            for(var5 = 0; var5 < var4; ++var5) {
               PathServiceMBean p = var30[var5];
               if (((PathServiceMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyPathService(p);
               }
            }
         }

         if (this._isSet(23)) {
            SAFAgentMBean[] var32 = this.getSAFAgents();
            var4 = var32.length;

            for(var5 = 0; var5 < var4; ++var5) {
               SAFAgentMBean p = var32[var5];
               if (((SAFAgentMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroySAFAgent(p);
               }
            }
         }

         if (this._isSet(22)) {
            WLDFSystemResourceMBean[] var34 = this.getWLDFSystemResources();
            var4 = var34.length;

            for(var5 = 0; var5 < var4; ++var5) {
               WLDFSystemResourceMBean p = var34[var5];
               if (((WLDFSystemResourceMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyWLDFSystemResource(p);
               }
            }
         }
      }

      this._setDelegateBean((ResourceGroupTemplateMBeanImpl)param0);
      this._postSet(32, _oldVal, param0);
   }

   public TargetMBean[] findEffectiveTargets() {
      try {
         return this._customizer.findEffectiveTargets();
      } catch (InvalidAttributeValueException var2) {
         throw new UndeclaredThrowableException(var2);
      } catch (DistributedManagementException var3) {
         throw new UndeclaredThrowableException(var3);
      }
   }

   public TargetMBean[] getTargets() {
      return this._Targets;
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isTargetsInherited() {
      return false;
   }

   public boolean isTargetsSet() {
      return this._isSet(33);
   }

   public void setTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Targets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 33, param0) {
                  public void resolveReference(Object value) {
                     try {
                        ResourceGroupMBeanImpl.this.addTarget((TargetMBean)value);
                        ResourceGroupMBeanImpl.this._getHelper().reorderArrayObjects((Object[])ResourceGroupMBeanImpl.this._Targets, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               TargetMBean[] var6 = this._Targets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTarget(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         TargetMBean[] _oldVal = this._Targets;
         this._initializeProperty(33);
         this._postSet(33, _oldVal, this._Targets);
      }
   }

   public TargetMBean lookupTarget(String param0) {
      return this._customizer.lookupTarget(param0);
   }

   public void addTarget(TargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 33)) {
         TargetMBean[] _new;
         if (this._isSet(33)) {
            _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));
         } else {
            _new = new TargetMBean[]{param0};
         }

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void removeTarget(TargetMBean param0) {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setTargets(TargetMBean[] param0) {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 33, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return ResourceGroupMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      TargetMBean[] _oldVal = this._Targets;
      this._Targets = param0;
      this._postSet(33, _oldVal, param0);
   }

   public boolean isUseDefaultTarget() {
      return this._UseDefaultTarget;
   }

   public boolean isUseDefaultTargetInherited() {
      return false;
   }

   public boolean isUseDefaultTargetSet() {
      return this._isSet(34);
   }

   public BasicDeploymentMBean[] getBasicDeployments() {
      return this._customizer.getBasicDeployments();
   }

   public void setUseDefaultTarget(boolean param0) {
      boolean _oldVal = this._UseDefaultTarget;
      this._UseDefaultTarget = param0;
      this._postSet(34, _oldVal, param0);
   }

   public Boolean[] areDefinedInTemplate(ConfigurationMBean[] param0) {
      return this._customizer.areDefinedInTemplate(param0);
   }

   public DeploymentMBean[] getDeployments() {
      return this._customizer.getDeployments();
   }

   public SystemResourceMBean[] getSystemResources() {
      return this._customizer.getSystemResources();
   }

   public void setAutoTargetAdminServer(boolean param0) {
      boolean _oldVal = this._AutoTargetAdminServer;
      this._AutoTargetAdminServer = param0;
      this._postSet(35, _oldVal, param0);
   }

   public boolean isAutoTargetAdminServer() {
      return this._AutoTargetAdminServer;
   }

   public boolean isAutoTargetAdminServerInherited() {
      return false;
   }

   public boolean isAutoTargetAdminServerSet() {
      return this._isSet(35);
   }

   public void setAdministrative(boolean param0) {
      boolean _oldVal = this._Administrative;
      this._Administrative = param0;
      this._postSet(36, _oldVal, param0);
   }

   public boolean isAdministrative() {
      return this._Administrative;
   }

   public boolean isAdministrativeInherited() {
      return false;
   }

   public boolean isAdministrativeSet() {
      return this._isSet(36);
   }

   public String getUploadDirectoryName() {
      return this._customizer.getUploadDirectoryName();
   }

   public boolean isUploadDirectoryNameInherited() {
      return false;
   }

   public boolean isUploadDirectoryNameSet() {
      return this._isSet(31);
   }

   public void setUploadDirectoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getUploadDirectoryName();
      this._customizer.setUploadDirectoryName(param0);
      this._postSet(31, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      ResourceGroupValidator.validateResourceGroup(this);
      ResourceGroupValidator.validateSetTargets(this, this.getTargets());
   }

   protected void _preDestroy() {
      this._customizer._preDestroy();
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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 32;
      }

      try {
         switch (idx) {
            case 32:
               this._ResourceGroupTemplate = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._Targets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 31:
               this._customizer.setUploadDirectoryName((String)null);
               if (initOne) {
                  break;
               }
            case 36:
               this._Administrative = false;
               if (initOne) {
                  break;
               }
            case 35:
               this._AutoTargetAdminServer = false;
               if (initOne) {
                  break;
               }
            case 34:
               this._UseDefaultTarget = true;
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "ResourceGroup";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("Administrative")) {
         oldVal = this._Administrative;
         this._Administrative = (Boolean)v;
         this._postSet(36, oldVal, this._Administrative);
      } else if (name.equals("AutoTargetAdminServer")) {
         oldVal = this._AutoTargetAdminServer;
         this._AutoTargetAdminServer = (Boolean)v;
         this._postSet(35, oldVal, this._AutoTargetAdminServer);
      } else if (name.equals("ResourceGroupTemplate")) {
         ResourceGroupTemplateMBean oldVal = this._ResourceGroupTemplate;
         this._ResourceGroupTemplate = (ResourceGroupTemplateMBean)v;
         this._postSet(32, oldVal, this._ResourceGroupTemplate);
      } else if (name.equals("Targets")) {
         TargetMBean[] oldVal = this._Targets;
         this._Targets = (TargetMBean[])((TargetMBean[])v);
         this._postSet(33, oldVal, this._Targets);
      } else if (name.equals("UploadDirectoryName")) {
         String oldVal = this._UploadDirectoryName;
         this._UploadDirectoryName = (String)v;
         this._postSet(31, oldVal, this._UploadDirectoryName);
      } else if (name.equals("UseDefaultTarget")) {
         oldVal = this._UseDefaultTarget;
         this._UseDefaultTarget = (Boolean)v;
         this._postSet(34, oldVal, this._UseDefaultTarget);
      } else if (name.equals("customizer")) {
         ResourceGroup oldVal = this._customizer;
         this._customizer = (ResourceGroup)v;
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Administrative")) {
         return new Boolean(this._Administrative);
      } else if (name.equals("AutoTargetAdminServer")) {
         return new Boolean(this._AutoTargetAdminServer);
      } else if (name.equals("ResourceGroupTemplate")) {
         return this._ResourceGroupTemplate;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("UploadDirectoryName")) {
         return this._UploadDirectoryName;
      } else if (name.equals("UseDefaultTarget")) {
         return new Boolean(this._UseDefaultTarget);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ResourceGroupTemplateMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("target")) {
                  return 33;
               }
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 22:
            default:
               break;
            case 14:
               if (s.equals("administrative")) {
                  return 36;
               }
               break;
            case 18:
               if (s.equals("use-default-target")) {
                  return 34;
               }
               break;
            case 21:
               if (s.equals("upload-directory-name")) {
                  return 31;
               }
               break;
            case 23:
               if (s.equals("resource-group-template")) {
                  return 32;
               }
               break;
            case 24:
               if (s.equals("auto-target-admin-server")) {
                  return 35;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new AppDeploymentMBeanImpl.SchemaHelper2();
            case 11:
               return new LibraryMBeanImpl.SchemaHelper2();
            case 12:
               return new JMSServerMBeanImpl.SchemaHelper2();
            case 13:
               return new MessagingBridgeMBeanImpl.SchemaHelper2();
            case 14:
               return new PathServiceMBeanImpl.SchemaHelper2();
            case 15:
               return new JMSBridgeDestinationMBeanImpl.SchemaHelper2();
            case 16:
               return new MailSessionMBeanImpl.SchemaHelper2();
            case 17:
               return new FileStoreMBeanImpl.SchemaHelper2();
            case 18:
               return new JDBCStoreMBeanImpl.SchemaHelper2();
            case 19:
               return new JMSSystemResourceMBeanImpl.SchemaHelper2();
            case 20:
               return new ForeignJNDIProviderMBeanImpl.SchemaHelper2();
            case 21:
               return new JDBCSystemResourceMBeanImpl.SchemaHelper2();
            case 22:
               return new WLDFSystemResourceMBeanImpl.SchemaHelper2();
            case 23:
               return new SAFAgentMBeanImpl.SchemaHelper2();
            case 24:
               return new CoherenceClusterSystemResourceMBeanImpl.SchemaHelper2();
            case 25:
               return new OsgiFrameworkMBeanImpl.SchemaHelper2();
            case 26:
               return new ManagedExecutorServiceMBeanImpl.SchemaHelper2();
            case 27:
               return new ManagedScheduledExecutorServiceMBeanImpl.SchemaHelper2();
            case 28:
               return new ManagedThreadFactoryMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 31:
               return "upload-directory-name";
            case 32:
               return "resource-group-template";
            case 33:
               return "target";
            case 34:
               return "use-default-target";
            case 35:
               return "auto-target-admin-server";
            case 36:
               return "administrative";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
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
               return true;
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
            case 30:
            case 31:
            case 32:
            default:
               return super.isArray(propIndex);
            case 33:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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
               return true;
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
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ResourceGroupTemplateMBeanImpl.Helper {
      private ResourceGroupMBeanImpl bean;

      protected Helper(ResourceGroupMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 31:
               return "UploadDirectoryName";
            case 32:
               return "ResourceGroupTemplate";
            case 33:
               return "Targets";
            case 34:
               return "UseDefaultTarget";
            case 35:
               return "AutoTargetAdminServer";
            case 36:
               return "Administrative";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ResourceGroupTemplate")) {
            return 32;
         } else if (propName.equals("Targets")) {
            return 33;
         } else if (propName.equals("UploadDirectoryName")) {
            return 31;
         } else if (propName.equals("Administrative")) {
            return 36;
         } else if (propName.equals("AutoTargetAdminServer")) {
            return 35;
         } else {
            return propName.equals("UseDefaultTarget") ? 34 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAppDeployments()));
         iterators.add(new ArrayIterator(this.bean.getCoherenceClusterSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getFileStores()));
         iterators.add(new ArrayIterator(this.bean.getForeignJNDIProviders()));
         iterators.add(new ArrayIterator(this.bean.getJDBCStores()));
         iterators.add(new ArrayIterator(this.bean.getJDBCSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getJMSBridgeDestinations()));
         iterators.add(new ArrayIterator(this.bean.getJMSServers()));
         iterators.add(new ArrayIterator(this.bean.getJMSSystemResources()));
         iterators.add(new ArrayIterator(this.bean.getLibraries()));
         iterators.add(new ArrayIterator(this.bean.getMailSessions()));
         iterators.add(new ArrayIterator(this.bean.getManagedExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedScheduledExecutorServices()));
         iterators.add(new ArrayIterator(this.bean.getManagedThreadFactories()));
         iterators.add(new ArrayIterator(this.bean.getMessagingBridges()));
         iterators.add(new ArrayIterator(this.bean.getOsgiFrameworks()));
         iterators.add(new ArrayIterator(this.bean.getPathServices()));
         iterators.add(new ArrayIterator(this.bean.getSAFAgents()));
         iterators.add(new ArrayIterator(this.bean.getWLDFSystemResources()));
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
            if (this.bean.isResourceGroupTemplateSet()) {
               buf.append("ResourceGroupTemplate");
               buf.append(String.valueOf(this.bean.getResourceGroupTemplate()));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isUploadDirectoryNameSet()) {
               buf.append("UploadDirectoryName");
               buf.append(String.valueOf(this.bean.getUploadDirectoryName()));
            }

            if (this.bean.isAdministrativeSet()) {
               buf.append("Administrative");
               buf.append(String.valueOf(this.bean.isAdministrative()));
            }

            if (this.bean.isAutoTargetAdminServerSet()) {
               buf.append("AutoTargetAdminServer");
               buf.append(String.valueOf(this.bean.isAutoTargetAdminServer()));
            }

            if (this.bean.isUseDefaultTargetSet()) {
               buf.append("UseDefaultTarget");
               buf.append(String.valueOf(this.bean.isUseDefaultTarget()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            ResourceGroupMBeanImpl otherTyped = (ResourceGroupMBeanImpl)other;
            this.computeDiff("ResourceGroupTemplate", this.bean.getResourceGroupTemplate(), otherTyped.getResourceGroupTemplate(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            this.computeDiff("UploadDirectoryName", this.bean.getUploadDirectoryName(), otherTyped.getUploadDirectoryName(), true);
            this.computeDiff("Administrative", this.bean.isAdministrative(), otherTyped.isAdministrative(), true);
            this.computeDiff("AutoTargetAdminServer", this.bean.isAutoTargetAdminServer(), otherTyped.isAutoTargetAdminServer(), true);
            this.computeDiff("UseDefaultTarget", this.bean.isUseDefaultTarget(), otherTyped.isUseDefaultTarget(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ResourceGroupMBeanImpl original = (ResourceGroupMBeanImpl)event.getSourceBean();
            ResourceGroupMBeanImpl proposed = (ResourceGroupMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ResourceGroupTemplate")) {
                  original.setResourceGroupTemplateAsString(proposed.getResourceGroupTemplateAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("Targets")) {
                  original.setTargetsAsString(proposed.getTargetsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("UploadDirectoryName")) {
                  original.setUploadDirectoryName(proposed.getUploadDirectoryName());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("Administrative")) {
                  original.setAdministrative(proposed.isAdministrative());
                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("AutoTargetAdminServer")) {
                  original.setAutoTargetAdminServer(proposed.isAutoTargetAdminServer());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("UseDefaultTarget")) {
                  original.setUseDefaultTarget(proposed.isUseDefaultTarget());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
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
            ResourceGroupMBeanImpl copy = (ResourceGroupMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ResourceGroupTemplate")) && this.bean.isResourceGroupTemplateSet()) {
               copy._unSet(copy, 32);
               copy.setResourceGroupTemplateAsString(this.bean.getResourceGroupTemplateAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 33);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("UploadDirectoryName")) && this.bean.isUploadDirectoryNameSet()) {
               copy.setUploadDirectoryName(this.bean.getUploadDirectoryName());
            }

            if ((excludeProps == null || !excludeProps.contains("Administrative")) && this.bean.isAdministrativeSet()) {
               copy.setAdministrative(this.bean.isAdministrative());
            }

            if ((excludeProps == null || !excludeProps.contains("AutoTargetAdminServer")) && this.bean.isAutoTargetAdminServerSet()) {
               copy.setAutoTargetAdminServer(this.bean.isAutoTargetAdminServer());
            }

            if ((excludeProps == null || !excludeProps.contains("UseDefaultTarget")) && this.bean.isUseDefaultTargetSet()) {
               copy.setUseDefaultTarget(this.bean.isUseDefaultTarget());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getResourceGroupTemplate(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}

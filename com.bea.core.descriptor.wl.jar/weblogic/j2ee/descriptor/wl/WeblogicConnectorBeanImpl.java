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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicConnectorBeanImpl extends AbstractDescriptorBean implements WeblogicConnectorBean, Serializable {
   private AdminObjectsBean _AdminObjects;
   private ConnectorWorkManagerBean _ConnectorWorkManager;
   private boolean _DeployAsAWhole;
   private boolean _EnableAccessOutsideApp;
   private boolean _EnableGlobalAccessToClasses;
   private String _JNDIName;
   private String _NativeLibdir;
   private OutboundResourceAdapterBean _OutboundResourceAdapter;
   private ConfigPropertiesBean _Properties;
   private ResourceAdapterSecurityBean _Security;
   private String _Version;
   private WorkManagerBean _WorkManager;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicConnectorBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicConnectorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicConnectorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public String getNativeLibdir() {
      return this._NativeLibdir;
   }

   public boolean isNativeLibdirInherited() {
      return false;
   }

   public boolean isNativeLibdirSet() {
      return this._isSet(0);
   }

   public void setNativeLibdir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NativeLibdir;
      this._NativeLibdir = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getJNDIName() {
      return this._JNDIName;
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(1);
   }

   public void setJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isEnableAccessOutsideApp() {
      return this._EnableAccessOutsideApp;
   }

   public boolean isEnableAccessOutsideAppInherited() {
      return false;
   }

   public boolean isEnableAccessOutsideAppSet() {
      return this._isSet(2);
   }

   public void setEnableAccessOutsideApp(boolean param0) {
      boolean _oldVal = this._EnableAccessOutsideApp;
      this._EnableAccessOutsideApp = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isEnableGlobalAccessToClasses() {
      return this._EnableGlobalAccessToClasses;
   }

   public boolean isEnableGlobalAccessToClassesInherited() {
      return false;
   }

   public boolean isEnableGlobalAccessToClassesSet() {
      return this._isSet(3);
   }

   public void setEnableGlobalAccessToClasses(boolean param0) {
      boolean _oldVal = this._EnableGlobalAccessToClasses;
      this._EnableGlobalAccessToClasses = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isDeployAsAWhole() {
      return this._DeployAsAWhole;
   }

   public boolean isDeployAsAWholeInherited() {
      return false;
   }

   public boolean isDeployAsAWholeSet() {
      return this._isSet(4);
   }

   public void setDeployAsAWhole(boolean param0) {
      boolean _oldVal = this._DeployAsAWhole;
      this._DeployAsAWhole = param0;
      this._postSet(4, _oldVal, param0);
   }

   public WorkManagerBean getWorkManager() {
      return this._WorkManager;
   }

   public boolean isWorkManagerInherited() {
      return false;
   }

   public boolean isWorkManagerSet() {
      return this._isSet(5);
   }

   public void setWorkManager(WorkManagerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWorkManager() != null && param0 != this.getWorkManager()) {
         throw new BeanAlreadyExistsException(this.getWorkManager() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WorkManagerBean _oldVal = this._WorkManager;
         this._WorkManager = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public WorkManagerBean createWorkManager() {
      WorkManagerBeanImpl _val = new WorkManagerBeanImpl(this, -1);

      try {
         this.setWorkManager(_val);
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
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WorkManager;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWorkManager((WorkManagerBean)null);
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

   public ConnectorWorkManagerBean getConnectorWorkManager() {
      return this._ConnectorWorkManager;
   }

   public boolean isConnectorWorkManagerInherited() {
      return false;
   }

   public boolean isConnectorWorkManagerSet() {
      return this._isSet(6) || this._isAnythingSet((AbstractDescriptorBean)this.getConnectorWorkManager());
   }

   public void setConnectorWorkManager(ConnectorWorkManagerBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 6)) {
         this._postCreate(_child);
      }

      ConnectorWorkManagerBean _oldVal = this._ConnectorWorkManager;
      this._ConnectorWorkManager = param0;
      this._postSet(6, _oldVal, param0);
   }

   public ResourceAdapterSecurityBean getSecurity() {
      return this._Security;
   }

   public boolean isSecurityInherited() {
      return false;
   }

   public boolean isSecuritySet() {
      return this._isSet(7) || this._isAnythingSet((AbstractDescriptorBean)this.getSecurity());
   }

   public void setSecurity(ResourceAdapterSecurityBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 7)) {
         this._postCreate(_child);
      }

      ResourceAdapterSecurityBean _oldVal = this._Security;
      this._Security = param0;
      this._postSet(7, _oldVal, param0);
   }

   public ConfigPropertiesBean getProperties() {
      return this._Properties;
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(8) || this._isAnythingSet((AbstractDescriptorBean)this.getProperties());
   }

   public void setProperties(ConfigPropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 8)) {
         this._postCreate(_child);
      }

      ConfigPropertiesBean _oldVal = this._Properties;
      this._Properties = param0;
      this._postSet(8, _oldVal, param0);
   }

   public AdminObjectsBean getAdminObjects() {
      return this._AdminObjects;
   }

   public boolean isAdminObjectsInherited() {
      return false;
   }

   public boolean isAdminObjectsSet() {
      return this._isSet(9) || this._isAnythingSet((AbstractDescriptorBean)this.getAdminObjects());
   }

   public void setAdminObjects(AdminObjectsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 9)) {
         this._postCreate(_child);
      }

      AdminObjectsBean _oldVal = this._AdminObjects;
      this._AdminObjects = param0;
      this._postSet(9, _oldVal, param0);
   }

   public OutboundResourceAdapterBean getOutboundResourceAdapter() {
      return this._OutboundResourceAdapter;
   }

   public boolean isOutboundResourceAdapterInherited() {
      return false;
   }

   public boolean isOutboundResourceAdapterSet() {
      return this._isSet(10) || this._isAnythingSet((AbstractDescriptorBean)this.getOutboundResourceAdapter());
   }

   public void setOutboundResourceAdapter(OutboundResourceAdapterBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 10)) {
         this._postCreate(_child);
      }

      OutboundResourceAdapterBean _oldVal = this._OutboundResourceAdapter;
      this._OutboundResourceAdapter = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(11);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(11, _oldVal, param0);
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
      return super._isAnythingSet() || this.isAdminObjectsSet() || this.isConnectorWorkManagerSet() || this.isOutboundResourceAdapterSet() || this.isPropertiesSet() || this.isSecuritySet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 9;
      }

      try {
         switch (idx) {
            case 9:
               this._AdminObjects = new AdminObjectsBeanImpl(this, 9);
               this._postCreate((AbstractDescriptorBean)this._AdminObjects);
               if (initOne) {
                  break;
               }
            case 6:
               this._ConnectorWorkManager = new ConnectorWorkManagerBeanImpl(this, 6);
               this._postCreate((AbstractDescriptorBean)this._ConnectorWorkManager);
               if (initOne) {
                  break;
               }
            case 1:
               this._JNDIName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._NativeLibdir = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._OutboundResourceAdapter = new OutboundResourceAdapterBeanImpl(this, 10);
               this._postCreate((AbstractDescriptorBean)this._OutboundResourceAdapter);
               if (initOne) {
                  break;
               }
            case 8:
               this._Properties = new ConfigPropertiesBeanImpl(this, 8);
               this._postCreate((AbstractDescriptorBean)this._Properties);
               if (initOne) {
                  break;
               }
            case 7:
               this._Security = new ResourceAdapterSecurityBeanImpl(this, 7);
               this._postCreate((AbstractDescriptorBean)this._Security);
               if (initOne) {
                  break;
               }
            case 11:
               this._Version = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._WorkManager = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._DeployAsAWhole = true;
               if (initOne) {
                  break;
               }
            case 2:
               this._EnableAccessOutsideApp = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._EnableGlobalAccessToClasses = false;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-connector/1.2/weblogic-connector.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-connector";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("version")) {
                  return 11;
               }
               break;
            case 8:
               if (s.equals("security")) {
                  return 7;
               }
               break;
            case 9:
               if (s.equals("jndi-name")) {
                  return 1;
               }
               break;
            case 10:
               if (s.equals("properties")) {
                  return 8;
               }
            case 11:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 24:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            default:
               break;
            case 12:
               if (s.equals("work-manager")) {
                  return 5;
               }
               break;
            case 13:
               if (s.equals("admin-objects")) {
                  return 9;
               }

               if (s.equals("native-libdir")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("deploy-as-a-whole")) {
                  return 4;
               }
               break;
            case 22:
               if (s.equals("connector-work-manager")) {
                  return 6;
               }
               break;
            case 25:
               if (s.equals("outbound-resource-adapter")) {
                  return 10;
               }

               if (s.equals("enable-access-outside-app")) {
                  return 2;
               }
               break;
            case 31:
               if (s.equals("enable-global-access-to-classes")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new WorkManagerBeanImpl.SchemaHelper2();
            case 6:
               return new ConnectorWorkManagerBeanImpl.SchemaHelper2();
            case 7:
               return new ResourceAdapterSecurityBeanImpl.SchemaHelper2();
            case 8:
               return new ConfigPropertiesBeanImpl.SchemaHelper2();
            case 9:
               return new AdminObjectsBeanImpl.SchemaHelper2();
            case 10:
               return new OutboundResourceAdapterBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "weblogic-connector";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "native-libdir";
            case 1:
               return "jndi-name";
            case 2:
               return "enable-access-outside-app";
            case 3:
               return "enable-global-access-to-classes";
            case 4:
               return "deploy-as-a-whole";
            case 5:
               return "work-manager";
            case 6:
               return "connector-work-manager";
            case 7:
               return "security";
            case 8:
               return "properties";
            case 9:
               return "admin-objects";
            case 10:
               return "outbound-resource-adapter";
            case 11:
               return "version";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicConnectorBeanImpl bean;

      protected Helper(WeblogicConnectorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "NativeLibdir";
            case 1:
               return "JNDIName";
            case 2:
               return "EnableAccessOutsideApp";
            case 3:
               return "EnableGlobalAccessToClasses";
            case 4:
               return "DeployAsAWhole";
            case 5:
               return "WorkManager";
            case 6:
               return "ConnectorWorkManager";
            case 7:
               return "Security";
            case 8:
               return "Properties";
            case 9:
               return "AdminObjects";
            case 10:
               return "OutboundResourceAdapter";
            case 11:
               return "Version";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdminObjects")) {
            return 9;
         } else if (propName.equals("ConnectorWorkManager")) {
            return 6;
         } else if (propName.equals("JNDIName")) {
            return 1;
         } else if (propName.equals("NativeLibdir")) {
            return 0;
         } else if (propName.equals("OutboundResourceAdapter")) {
            return 10;
         } else if (propName.equals("Properties")) {
            return 8;
         } else if (propName.equals("Security")) {
            return 7;
         } else if (propName.equals("Version")) {
            return 11;
         } else if (propName.equals("WorkManager")) {
            return 5;
         } else if (propName.equals("DeployAsAWhole")) {
            return 4;
         } else if (propName.equals("EnableAccessOutsideApp")) {
            return 2;
         } else {
            return propName.equals("EnableGlobalAccessToClasses") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAdminObjects() != null) {
            iterators.add(new ArrayIterator(new AdminObjectsBean[]{this.bean.getAdminObjects()}));
         }

         if (this.bean.getConnectorWorkManager() != null) {
            iterators.add(new ArrayIterator(new ConnectorWorkManagerBean[]{this.bean.getConnectorWorkManager()}));
         }

         if (this.bean.getOutboundResourceAdapter() != null) {
            iterators.add(new ArrayIterator(new OutboundResourceAdapterBean[]{this.bean.getOutboundResourceAdapter()}));
         }

         if (this.bean.getProperties() != null) {
            iterators.add(new ArrayIterator(new ConfigPropertiesBean[]{this.bean.getProperties()}));
         }

         if (this.bean.getSecurity() != null) {
            iterators.add(new ArrayIterator(new ResourceAdapterSecurityBean[]{this.bean.getSecurity()}));
         }

         if (this.bean.getWorkManager() != null) {
            iterators.add(new ArrayIterator(new WorkManagerBean[]{this.bean.getWorkManager()}));
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
            childValue = this.computeChildHashValue(this.bean.getAdminObjects());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getConnectorWorkManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            if (this.bean.isNativeLibdirSet()) {
               buf.append("NativeLibdir");
               buf.append(String.valueOf(this.bean.getNativeLibdir()));
            }

            childValue = this.computeChildHashValue(this.bean.getOutboundResourceAdapter());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSecurity());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
            }

            childValue = this.computeChildHashValue(this.bean.getWorkManager());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDeployAsAWholeSet()) {
               buf.append("DeployAsAWhole");
               buf.append(String.valueOf(this.bean.isDeployAsAWhole()));
            }

            if (this.bean.isEnableAccessOutsideAppSet()) {
               buf.append("EnableAccessOutsideApp");
               buf.append(String.valueOf(this.bean.isEnableAccessOutsideApp()));
            }

            if (this.bean.isEnableGlobalAccessToClassesSet()) {
               buf.append("EnableGlobalAccessToClasses");
               buf.append(String.valueOf(this.bean.isEnableGlobalAccessToClasses()));
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
            WeblogicConnectorBeanImpl otherTyped = (WeblogicConnectorBeanImpl)other;
            this.computeSubDiff("AdminObjects", this.bean.getAdminObjects(), otherTyped.getAdminObjects());
            this.computeSubDiff("ConnectorWorkManager", this.bean.getConnectorWorkManager(), otherTyped.getConnectorWorkManager());
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), true);
            this.computeDiff("NativeLibdir", this.bean.getNativeLibdir(), otherTyped.getNativeLibdir(), false);
            this.computeSubDiff("OutboundResourceAdapter", this.bean.getOutboundResourceAdapter(), otherTyped.getOutboundResourceAdapter());
            this.computeSubDiff("Properties", this.bean.getProperties(), otherTyped.getProperties());
            this.computeSubDiff("Security", this.bean.getSecurity(), otherTyped.getSecurity());
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
            this.computeChildDiff("WorkManager", this.bean.getWorkManager(), otherTyped.getWorkManager(), false);
            this.computeDiff("DeployAsAWhole", this.bean.isDeployAsAWhole(), otherTyped.isDeployAsAWhole(), false);
            this.computeDiff("EnableAccessOutsideApp", this.bean.isEnableAccessOutsideApp(), otherTyped.isEnableAccessOutsideApp(), false);
            this.computeDiff("EnableGlobalAccessToClasses", this.bean.isEnableGlobalAccessToClasses(), otherTyped.isEnableGlobalAccessToClasses(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicConnectorBeanImpl original = (WeblogicConnectorBeanImpl)event.getSourceBean();
            WeblogicConnectorBeanImpl proposed = (WeblogicConnectorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdminObjects")) {
                  if (type == 2) {
                     original.setAdminObjects((AdminObjectsBean)this.createCopy((AbstractDescriptorBean)proposed.getAdminObjects()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AdminObjects", (DescriptorBean)original.getAdminObjects());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("ConnectorWorkManager")) {
                  if (type == 2) {
                     original.setConnectorWorkManager((ConnectorWorkManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getConnectorWorkManager()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ConnectorWorkManager", (DescriptorBean)original.getConnectorWorkManager());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("NativeLibdir")) {
                  original.setNativeLibdir(proposed.getNativeLibdir());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("OutboundResourceAdapter")) {
                  if (type == 2) {
                     original.setOutboundResourceAdapter((OutboundResourceAdapterBean)this.createCopy((AbstractDescriptorBean)proposed.getOutboundResourceAdapter()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("OutboundResourceAdapter", (DescriptorBean)original.getOutboundResourceAdapter());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("Properties")) {
                  if (type == 2) {
                     original.setProperties((ConfigPropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getProperties()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Properties", (DescriptorBean)original.getProperties());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("Security")) {
                  if (type == 2) {
                     original.setSecurity((ResourceAdapterSecurityBean)this.createCopy((AbstractDescriptorBean)proposed.getSecurity()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Security", (DescriptorBean)original.getSecurity());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("WorkManager")) {
                  if (type == 2) {
                     original.setWorkManager((WorkManagerBean)this.createCopy((AbstractDescriptorBean)proposed.getWorkManager()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WorkManager", (DescriptorBean)original.getWorkManager());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("DeployAsAWhole")) {
                  original.setDeployAsAWhole(proposed.isDeployAsAWhole());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("EnableAccessOutsideApp")) {
                  original.setEnableAccessOutsideApp(proposed.isEnableAccessOutsideApp());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("EnableGlobalAccessToClasses")) {
                  original.setEnableGlobalAccessToClasses(proposed.isEnableGlobalAccessToClasses());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            WeblogicConnectorBeanImpl copy = (WeblogicConnectorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdminObjects")) && this.bean.isAdminObjectsSet() && !copy._isSet(9)) {
               Object o = this.bean.getAdminObjects();
               copy.setAdminObjects((AdminObjectsBean)null);
               copy.setAdminObjects(o == null ? null : (AdminObjectsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectorWorkManager")) && this.bean.isConnectorWorkManagerSet() && !copy._isSet(6)) {
               Object o = this.bean.getConnectorWorkManager();
               copy.setConnectorWorkManager((ConnectorWorkManagerBean)null);
               copy.setConnectorWorkManager(o == null ? null : (ConnectorWorkManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("NativeLibdir")) && this.bean.isNativeLibdirSet()) {
               copy.setNativeLibdir(this.bean.getNativeLibdir());
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundResourceAdapter")) && this.bean.isOutboundResourceAdapterSet() && !copy._isSet(10)) {
               Object o = this.bean.getOutboundResourceAdapter();
               copy.setOutboundResourceAdapter((OutboundResourceAdapterBean)null);
               copy.setOutboundResourceAdapter(o == null ? null : (OutboundResourceAdapterBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet() && !copy._isSet(8)) {
               Object o = this.bean.getProperties();
               copy.setProperties((ConfigPropertiesBean)null);
               copy.setProperties(o == null ? null : (ConfigPropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Security")) && this.bean.isSecuritySet() && !copy._isSet(7)) {
               Object o = this.bean.getSecurity();
               copy.setSecurity((ResourceAdapterSecurityBean)null);
               copy.setSecurity(o == null ? null : (ResourceAdapterSecurityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManager")) && this.bean.isWorkManagerSet() && !copy._isSet(5)) {
               Object o = this.bean.getWorkManager();
               copy.setWorkManager((WorkManagerBean)null);
               copy.setWorkManager(o == null ? null : (WorkManagerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DeployAsAWhole")) && this.bean.isDeployAsAWholeSet()) {
               copy.setDeployAsAWhole(this.bean.isDeployAsAWhole());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableAccessOutsideApp")) && this.bean.isEnableAccessOutsideAppSet()) {
               copy.setEnableAccessOutsideApp(this.bean.isEnableAccessOutsideApp());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableGlobalAccessToClasses")) && this.bean.isEnableGlobalAccessToClassesSet()) {
               copy.setEnableGlobalAccessToClasses(this.bean.isEnableGlobalAccessToClasses());
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
         this.inferSubTree(this.bean.getAdminObjects(), clazz, annotation);
         this.inferSubTree(this.bean.getConnectorWorkManager(), clazz, annotation);
         this.inferSubTree(this.bean.getOutboundResourceAdapter(), clazz, annotation);
         this.inferSubTree(this.bean.getProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurity(), clazz, annotation);
         this.inferSubTree(this.bean.getWorkManager(), clazz, annotation);
      }
   }
}

package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.WseeConfigBeanValidator;
import weblogic.management.ManagementException;
import weblogic.utils.collections.CombinedIterator;

public class WebServicePhysicalStoreMBeanImpl extends ConfigurationMBeanImpl implements WebServicePhysicalStoreMBean, Serializable {
   private String _Location;
   private String _Name;
   private String _StoreType;
   private String _SynchronousWritePolicy;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WebServicePhysicalStoreMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WebServicePhysicalStoreMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WebServicePhysicalStoreMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WebServicePhysicalStoreMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WebServicePhysicalStoreMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WebServicePhysicalStoreMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public WebServicePhysicalStoreMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebServicePhysicalStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebServicePhysicalStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2) ? this._performMacroSubstitution(this._getDelegateBean().getName(), this) : this._Name;
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      WseeConfigBeanValidator.validatePhysicalStoreName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServicePhysicalStoreMBeanImpl source = (WebServicePhysicalStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setStoreType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"FILE", "JDBC"};
      param0 = LegalChecks.checkInEnum("StoreType", param0, _set);
      boolean wasSet = this._isSet(10);
      String _oldVal = this._StoreType;
      this._StoreType = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServicePhysicalStoreMBeanImpl source = (WebServicePhysicalStoreMBeanImpl)var5.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getStoreType() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getStoreType(), this) : this._StoreType;
   }

   public boolean isStoreTypeInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isStoreTypeSet() {
      return this._isSet(10);
   }

   public String getLocation() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getLocation(), this) : this._Location;
   }

   public boolean isLocationInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isLocationSet() {
      return this._isSet(11);
   }

   public void setLocation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._Location;
      this._Location = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServicePhysicalStoreMBeanImpl source = (WebServicePhysicalStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSynchronousWritePolicy() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getSynchronousWritePolicy(), this) : this._SynchronousWritePolicy;
   }

   public boolean isSynchronousWritePolicyInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isSynchronousWritePolicySet() {
      return this._isSet(12);
   }

   public void setSynchronousWritePolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"DISABLED", "CACHE_FLUSH", "DIRECT_WRITE"};
      param0 = LegalChecks.checkInEnum("SynchronousWritePolicy", param0, _set);
      boolean wasSet = this._isSet(12);
      String _oldVal = this._SynchronousWritePolicy;
      this._SynchronousWritePolicy = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServicePhysicalStoreMBeanImpl source = (WebServicePhysicalStoreMBeanImpl)var5.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._Location = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._StoreType = "FILE";
               if (initOne) {
                  break;
               }
            case 12:
               this._SynchronousWritePolicy = "CACHE_FLUSH";
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
      return "WebServicePhysicalStore";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Location")) {
         oldVal = this._Location;
         this._Location = (String)v;
         this._postSet(11, oldVal, this._Location);
      } else if (name.equals("Name")) {
         oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("StoreType")) {
         oldVal = this._StoreType;
         this._StoreType = (String)v;
         this._postSet(10, oldVal, this._StoreType);
      } else if (name.equals("SynchronousWritePolicy")) {
         oldVal = this._SynchronousWritePolicy;
         this._SynchronousWritePolicy = (String)v;
         this._postSet(12, oldVal, this._SynchronousWritePolicy);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Location")) {
         return this._Location;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("StoreType")) {
         return this._StoreType;
      } else {
         return name.equals("SynchronousWritePolicy") ? this._SynchronousWritePolicy : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 8:
               if (s.equals("location")) {
                  return 11;
               }
               break;
            case 10:
               if (s.equals("store-type")) {
                  return 10;
               }
               break;
            case 24:
               if (s.equals("synchronous-write-policy")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 10:
               return "store-type";
            case 11:
               return "location";
            case 12:
               return "synchronous-write-policy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WebServicePhysicalStoreMBeanImpl bean;

      protected Helper(WebServicePhysicalStoreMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 10:
               return "StoreType";
            case 11:
               return "Location";
            case 12:
               return "SynchronousWritePolicy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Location")) {
            return 11;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("StoreType")) {
            return 10;
         } else {
            return propName.equals("SynchronousWritePolicy") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isLocationSet()) {
               buf.append("Location");
               buf.append(String.valueOf(this.bean.getLocation()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isStoreTypeSet()) {
               buf.append("StoreType");
               buf.append(String.valueOf(this.bean.getStoreType()));
            }

            if (this.bean.isSynchronousWritePolicySet()) {
               buf.append("SynchronousWritePolicy");
               buf.append(String.valueOf(this.bean.getSynchronousWritePolicy()));
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
            WebServicePhysicalStoreMBeanImpl otherTyped = (WebServicePhysicalStoreMBeanImpl)other;
            this.computeDiff("Location", this.bean.getLocation(), otherTyped.getLocation(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("StoreType", this.bean.getStoreType(), otherTyped.getStoreType(), true);
            this.computeDiff("SynchronousWritePolicy", this.bean.getSynchronousWritePolicy(), otherTyped.getSynchronousWritePolicy(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebServicePhysicalStoreMBeanImpl original = (WebServicePhysicalStoreMBeanImpl)event.getSourceBean();
            WebServicePhysicalStoreMBeanImpl proposed = (WebServicePhysicalStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Location")) {
                  original.setLocation(proposed.getLocation());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("StoreType")) {
                  original.setStoreType(proposed.getStoreType());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("SynchronousWritePolicy")) {
                  original.setSynchronousWritePolicy(proposed.getSynchronousWritePolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            WebServicePhysicalStoreMBeanImpl copy = (WebServicePhysicalStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Location")) && this.bean.isLocationSet()) {
               copy.setLocation(this.bean.getLocation());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreType")) && this.bean.isStoreTypeSet()) {
               copy.setStoreType(this.bean.getStoreType());
            }

            if ((excludeProps == null || !excludeProps.contains("SynchronousWritePolicy")) && this.bean.isSynchronousWritePolicySet()) {
               copy.setSynchronousWritePolicy(this.bean.getSynchronousWritePolicy());
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
      }
   }
}

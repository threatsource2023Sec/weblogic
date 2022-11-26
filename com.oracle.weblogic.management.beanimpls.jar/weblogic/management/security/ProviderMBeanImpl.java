package weblogic.management.security;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.commo.AbstractCommoConfigurationBean;
import weblogic.management.commo.RequiredModelMBeanWrapper;
import weblogic.utils.collections.CombinedIterator;

public class ProviderMBeanImpl extends AbstractCommoConfigurationBean implements ProviderMBean, Serializable {
   private String _CompatibilityObjectName;
   private String _Description;
   private String _Name;
   private String _ProviderClassName;
   private RealmMBean _Realm;
   private String _Version;
   private transient ProviderImpl _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ProviderMBeanImpl() {
      try {
         this._customizer = new ProviderImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ProviderMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ProviderImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ProviderMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ProviderImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getProviderClassName() {
      return this._ProviderClassName;
   }

   public boolean isProviderClassNameInherited() {
      return false;
   }

   public boolean isProviderClassNameSet() {
      return this._isSet(2);
   }

   public void setProviderClassName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this._ProviderClassName = param0;
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(3);
   }

   public void setDescription(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this._Description = param0;
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(4);
   }

   public void setVersion(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this._Version = param0;
   }

   public RealmMBean getRealm() {
      return this._customizer.getRealm();
   }

   public boolean isRealmInherited() {
      return false;
   }

   public boolean isRealmSet() {
      return this._isSet(5);
   }

   public void setRealm(RealmMBean param0) throws InvalidAttributeValueException {
      this._Realm = param0;
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(6);
   }

   public void setName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getCompatibilityObjectName() {
      return this._customizer.getCompatibilityObjectName();
   }

   public boolean isCompatibilityObjectNameInherited() {
      return false;
   }

   public boolean isCompatibilityObjectNameSet() {
      return this._isSet(7);
   }

   public void setCompatibilityObjectName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CompatibilityObjectName;
      this._CompatibilityObjectName = param0;
      this._postSet(7, _oldVal, param0);
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
         idx = 7;
      }

      try {
         switch (idx) {
            case 7:
               this._CompatibilityObjectName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Name = "Provider";
               if (initOne) {
                  break;
               }
            case 2:
               this._ProviderClassName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Realm = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Version = null;
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
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.security.ProviderMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 6;
               }
               break;
            case 5:
               if (s.equals("realm")) {
                  return 5;
               }
               break;
            case 7:
               if (s.equals("version")) {
                  return 4;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 3;
               }
               break;
            case 19:
               if (s.equals("provider-class-name")) {
                  return 2;
               }
               break;
            case 25:
               if (s.equals("compatibility-object-name")) {
                  return 7;
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
               return "provider-class-name";
            case 3:
               return "description";
            case 4:
               return "version";
            case 5:
               return "realm";
            case 6:
               return "name";
            case 7:
               return "compatibility-object-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractCommoConfigurationBean.Helper {
      private ProviderMBeanImpl bean;

      protected Helper(ProviderMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "ProviderClassName";
            case 3:
               return "Description";
            case 4:
               return "Version";
            case 5:
               return "Realm";
            case 6:
               return "Name";
            case 7:
               return "CompatibilityObjectName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CompatibilityObjectName")) {
            return 7;
         } else if (propName.equals("Description")) {
            return 3;
         } else if (propName.equals("Name")) {
            return 6;
         } else if (propName.equals("ProviderClassName")) {
            return 2;
         } else if (propName.equals("Realm")) {
            return 5;
         } else {
            return propName.equals("Version") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isCompatibilityObjectNameSet()) {
               buf.append("CompatibilityObjectName");
               buf.append(String.valueOf(this.bean.getCompatibilityObjectName()));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isProviderClassNameSet()) {
               buf.append("ProviderClassName");
               buf.append(String.valueOf(this.bean.getProviderClassName()));
            }

            if (this.bean.isRealmSet()) {
               buf.append("Realm");
               buf.append(String.valueOf(this.bean.getRealm()));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
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
            ProviderMBeanImpl otherTyped = (ProviderMBeanImpl)other;
            this.computeDiff("CompatibilityObjectName", this.bean.getCompatibilityObjectName(), otherTyped.getCompatibilityObjectName(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ProviderMBeanImpl original = (ProviderMBeanImpl)event.getSourceBean();
            ProviderMBeanImpl proposed = (ProviderMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CompatibilityObjectName")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (!prop.equals("Description")) {
                  if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  } else if (!prop.equals("ProviderClassName") && !prop.equals("Realm") && !prop.equals("Version")) {
                     super.applyPropertyUpdate(event, update);
                  }
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
            ProviderMBeanImpl copy = (ProviderMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CompatibilityObjectName")) && this.bean.isCompatibilityObjectNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
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
         this.inferSubTree(this.bean.getRealm(), clazz, annotation);
      }
   }
}

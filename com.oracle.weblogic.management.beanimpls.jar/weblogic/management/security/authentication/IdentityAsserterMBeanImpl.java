package weblogic.management.security.authentication;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.commo.RequiredModelMBeanWrapper;
import weblogic.management.security.RealmMBean;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class IdentityAsserterMBeanImpl extends AuthenticationProviderMBeanImpl implements IdentityAsserterMBean, Serializable {
   private String[] _ActiveTypes;
   private boolean _Base64DecodingRequired;
   private String _CompatibilityObjectName;
   private RealmMBean _Realm;
   private String[] _SupportedTypes;
   private transient IdentityAsserterImpl _customizer;
   private static SchemaHelper2 _schemaHelper;

   public IdentityAsserterMBeanImpl() {
      try {
         this._customizer = new IdentityAsserterImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public IdentityAsserterMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new IdentityAsserterImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public IdentityAsserterMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new IdentityAsserterImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String[] getSupportedTypes() {
      return this._SupportedTypes;
   }

   public boolean isSupportedTypesInherited() {
      return false;
   }

   public boolean isSupportedTypesSet() {
      return this._isSet(8);
   }

   public void setSupportedTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      this._SupportedTypes = param0;
   }

   public String[] getActiveTypes() {
      return this._ActiveTypes;
   }

   public boolean isActiveTypesInherited() {
      return false;
   }

   public boolean isActiveTypesSet() {
      return this._isSet(9);
   }

   public void setActiveTypes(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ActiveTypes;
      this._ActiveTypes = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean getBase64DecodingRequired() {
      return this._Base64DecodingRequired;
   }

   public RealmMBean getRealm() {
      return this._customizer.getRealm();
   }

   public boolean isBase64DecodingRequiredInherited() {
      return false;
   }

   public boolean isBase64DecodingRequiredSet() {
      return this._isSet(10);
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

   public void setBase64DecodingRequired(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._Base64DecodingRequired;
      this._Base64DecodingRequired = param0;
      this._postSet(10, _oldVal, param0);
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

      try {
         if (!this._customizer.validateActiveTypes(this.getActiveTypes())) {
            throw new IllegalArgumentException("The IdentityAsserter ActiveTypes attribute was set to an illegal value.");
         }
      } catch (InvalidAttributeValueException var2) {
         throw new IllegalArgumentException(var2.toString());
      }
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
         idx = 9;
      }

      try {
         switch (idx) {
            case 9:
               this._ActiveTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._Base64DecodingRequired = true;
               if (initOne) {
                  break;
               }
            case 7:
               this._CompatibilityObjectName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Realm = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._SupportedTypes = new String[0];
               if (initOne) {
                  break;
               }
            case 6:
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
      return "weblogic.management.security.authentication.IdentityAsserterMBean";
   }

   public static class SchemaHelper2 extends AuthenticationProviderMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("realm")) {
                  return 5;
               }
               break;
            case 11:
               if (s.equals("active-type")) {
                  return 9;
               }
               break;
            case 14:
               if (s.equals("supported-type")) {
                  return 8;
               }
               break;
            case 24:
               if (s.equals("base64-decoding-required")) {
                  return 10;
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
            case 5:
               return "realm";
            case 6:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "compatibility-object-name";
            case 8:
               return "supported-type";
            case 9:
               return "active-type";
            case 10:
               return "base64-decoding-required";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 8:
               return true;
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AuthenticationProviderMBeanImpl.Helper {
      private IdentityAsserterMBeanImpl bean;

      protected Helper(IdentityAsserterMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 5:
               return "Realm";
            case 6:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "CompatibilityObjectName";
            case 8:
               return "SupportedTypes";
            case 9:
               return "ActiveTypes";
            case 10:
               return "Base64DecodingRequired";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActiveTypes")) {
            return 9;
         } else if (propName.equals("Base64DecodingRequired")) {
            return 10;
         } else if (propName.equals("CompatibilityObjectName")) {
            return 7;
         } else if (propName.equals("Realm")) {
            return 5;
         } else {
            return propName.equals("SupportedTypes") ? 8 : super.getPropertyIndex(propName);
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
            if (this.bean.isActiveTypesSet()) {
               buf.append("ActiveTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getActiveTypes())));
            }

            if (this.bean.isBase64DecodingRequiredSet()) {
               buf.append("Base64DecodingRequired");
               buf.append(String.valueOf(this.bean.getBase64DecodingRequired()));
            }

            if (this.bean.isCompatibilityObjectNameSet()) {
               buf.append("CompatibilityObjectName");
               buf.append(String.valueOf(this.bean.getCompatibilityObjectName()));
            }

            if (this.bean.isRealmSet()) {
               buf.append("Realm");
               buf.append(String.valueOf(this.bean.getRealm()));
            }

            if (this.bean.isSupportedTypesSet()) {
               buf.append("SupportedTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSupportedTypes())));
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
            IdentityAsserterMBeanImpl otherTyped = (IdentityAsserterMBeanImpl)other;
            this.computeDiff("ActiveTypes", this.bean.getActiveTypes(), otherTyped.getActiveTypes(), false);
            this.computeDiff("Base64DecodingRequired", this.bean.getBase64DecodingRequired(), otherTyped.getBase64DecodingRequired(), false);
            this.computeDiff("CompatibilityObjectName", this.bean.getCompatibilityObjectName(), otherTyped.getCompatibilityObjectName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            IdentityAsserterMBeanImpl original = (IdentityAsserterMBeanImpl)event.getSourceBean();
            IdentityAsserterMBeanImpl proposed = (IdentityAsserterMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ActiveTypes")) {
                  original.setActiveTypes(proposed.getActiveTypes());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("Base64DecodingRequired")) {
                  original.setBase64DecodingRequired(proposed.getBase64DecodingRequired());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("CompatibilityObjectName")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (!prop.equals("Realm") && !prop.equals("SupportedTypes")) {
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
            IdentityAsserterMBeanImpl copy = (IdentityAsserterMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ActiveTypes")) && this.bean.isActiveTypesSet()) {
               Object o = this.bean.getActiveTypes();
               copy.setActiveTypes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Base64DecodingRequired")) && this.bean.isBase64DecodingRequiredSet()) {
               copy.setBase64DecodingRequired(this.bean.getBase64DecodingRequired());
            }

            if ((excludeProps == null || !excludeProps.contains("CompatibilityObjectName")) && this.bean.isCompatibilityObjectNameSet()) {
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

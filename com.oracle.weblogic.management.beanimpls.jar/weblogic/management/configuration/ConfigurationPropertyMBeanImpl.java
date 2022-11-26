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
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ConfigurationPropertyMBeanImpl extends ConfigurationMBeanImpl implements ConfigurationPropertyMBean, Serializable {
   private boolean _EncryptValueRequired;
   private String _EncryptedValue;
   private byte[] _EncryptedValueEncrypted;
   private String _Value;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ConfigurationPropertyMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ConfigurationPropertyMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ConfigurationPropertyMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ConfigurationPropertyMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ConfigurationPropertyMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ConfigurationPropertyMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ConfigurationPropertyMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConfigurationPropertyMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConfigurationPropertyMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEncryptValueRequired() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().isEncryptValueRequired() : this._EncryptValueRequired;
   }

   public boolean isEncryptValueRequiredInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isEncryptValueRequiredSet() {
      return this._isSet(10);
   }

   public void setEncryptValueRequired(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      boolean _oldVal = this._EncryptValueRequired;
      this._EncryptValueRequired = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ConfigurationPropertyMBeanImpl source = (ConfigurationPropertyMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getValue() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getValue(), this) : this._Value;
   }

   public boolean isValueInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isValueSet() {
      return this._isSet(11);
   }

   public void setValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._Value;
      this._Value = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ConfigurationPropertyMBeanImpl source = (ConfigurationPropertyMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getEncryptedValue() {
      if (!this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         return this._performMacroSubstitution(this._getDelegateBean().getEncryptedValue(), this);
      } else {
         byte[] bEncrypted = this.getEncryptedValueEncrypted();
         return bEncrypted == null ? null : this._decrypt("EncryptedValue", bEncrypted);
      }
   }

   public boolean isEncryptedValueInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isEncryptedValueSet() {
      return this.isEncryptedValueEncryptedSet();
   }

   public void setEncryptedValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      try {
         this.setEncryptedValueEncrypted(param0 == null ? null : this._encrypt("EncryptedValue", param0));
      } catch (InvalidAttributeValueException var3) {
      }

   }

   public byte[] getEncryptedValueEncrypted() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getEncryptedValueEncrypted() : this._getHelper()._cloneArray(this._EncryptedValueEncrypted);
   }

   public String getEncryptedValueEncryptedAsString() {
      byte[] obj = this.getEncryptedValueEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isEncryptedValueEncryptedInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isEncryptedValueEncryptedSet() {
      return this._isSet(13);
   }

   public void setEncryptedValueEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setEncryptedValueEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setEncryptedValueEncrypted(byte[] param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      byte[] _oldVal = this._EncryptedValueEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: EncryptedValueEncrypted of ConfigurationPropertyMBean");
      } else {
         this._getHelper()._clearArray(this._EncryptedValueEncrypted);
         this._EncryptedValueEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(13, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ConfigurationPropertyMBeanImpl source = (ConfigurationPropertyMBeanImpl)var4.next();
            if (source != null && !source._isSet(13)) {
               source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
            }
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
         if (idx == 12) {
            this._markSet(13, false);
         }
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._EncryptedValueEncrypted = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._EncryptedValueEncrypted = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._Value = "";
               if (initOne) {
                  break;
               }
            case 10:
               this._EncryptValueRequired = false;
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
      return "ConfigurationProperty";
   }

   public void putValue(String name, Object v) {
      if (name.equals("EncryptValueRequired")) {
         boolean oldVal = this._EncryptValueRequired;
         this._EncryptValueRequired = (Boolean)v;
         this._postSet(10, oldVal, this._EncryptValueRequired);
      } else {
         String oldVal;
         if (name.equals("EncryptedValue")) {
            oldVal = this._EncryptedValue;
            this._EncryptedValue = (String)v;
            this._postSet(12, oldVal, this._EncryptedValue);
         } else if (name.equals("EncryptedValueEncrypted")) {
            byte[] oldVal = this._EncryptedValueEncrypted;
            this._EncryptedValueEncrypted = (byte[])((byte[])v);
            this._postSet(13, oldVal, this._EncryptedValueEncrypted);
         } else if (name.equals("Value")) {
            oldVal = this._Value;
            this._Value = (String)v;
            this._postSet(11, oldVal, this._Value);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("EncryptValueRequired")) {
         return new Boolean(this._EncryptValueRequired);
      } else if (name.equals("EncryptedValue")) {
         return this._EncryptedValue;
      } else if (name.equals("EncryptedValueEncrypted")) {
         return this._EncryptedValueEncrypted;
      } else {
         return name.equals("Value") ? this._Value : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("value")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("encrypted-value")) {
                  return 12;
               }
               break;
            case 22:
               if (s.equals("encrypt-value-required")) {
                  return 10;
               }
               break;
            case 25:
               if (s.equals("encrypted-value-encrypted")) {
                  return 13;
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
            case 10:
               return "encrypt-value-required";
            case 11:
               return "value";
            case 12:
               return "encrypted-value";
            case 13:
               return "encrypted-value-encrypted";
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private ConfigurationPropertyMBeanImpl bean;

      protected Helper(ConfigurationPropertyMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "EncryptValueRequired";
            case 11:
               return "Value";
            case 12:
               return "EncryptedValue";
            case 13:
               return "EncryptedValueEncrypted";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EncryptedValue")) {
            return 12;
         } else if (propName.equals("EncryptedValueEncrypted")) {
            return 13;
         } else if (propName.equals("Value")) {
            return 11;
         } else {
            return propName.equals("EncryptValueRequired") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isEncryptedValueSet()) {
               buf.append("EncryptedValue");
               buf.append(String.valueOf(this.bean.getEncryptedValue()));
            }

            if (this.bean.isEncryptedValueEncryptedSet()) {
               buf.append("EncryptedValueEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEncryptedValueEncrypted())));
            }

            if (this.bean.isValueSet()) {
               buf.append("Value");
               buf.append(String.valueOf(this.bean.getValue()));
            }

            if (this.bean.isEncryptValueRequiredSet()) {
               buf.append("EncryptValueRequired");
               buf.append(String.valueOf(this.bean.isEncryptValueRequired()));
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
            ConfigurationPropertyMBeanImpl otherTyped = (ConfigurationPropertyMBeanImpl)other;
            this.computeDiff("EncryptedValueEncrypted", this.bean.getEncryptedValueEncrypted(), otherTyped.getEncryptedValueEncrypted(), false);
            this.computeDiff("Value", this.bean.getValue(), otherTyped.getValue(), true);
            this.computeDiff("EncryptValueRequired", this.bean.isEncryptValueRequired(), otherTyped.isEncryptValueRequired(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConfigurationPropertyMBeanImpl original = (ConfigurationPropertyMBeanImpl)event.getSourceBean();
            ConfigurationPropertyMBeanImpl proposed = (ConfigurationPropertyMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("EncryptedValue")) {
                  if (prop.equals("EncryptedValueEncrypted")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("Value")) {
                     original.setValue(proposed.getValue());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("EncryptValueRequired")) {
                     original.setEncryptValueRequired(proposed.isEncryptValueRequired());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else {
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
            ConfigurationPropertyMBeanImpl copy = (ConfigurationPropertyMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EncryptedValueEncrypted")) && this.bean.isEncryptedValueEncryptedSet()) {
               Object o = this.bean.getEncryptedValueEncrypted();
               copy.setEncryptedValueEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Value")) && this.bean.isValueSet()) {
               copy.setValue(this.bean.getValue());
            }

            if ((excludeProps == null || !excludeProps.contains("EncryptValueRequired")) && this.bean.isEncryptValueRequiredSet()) {
               copy.setEncryptValueRequired(this.bean.isEncryptValueRequired());
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

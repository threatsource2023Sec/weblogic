package weblogic.management.configuration;

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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JDBCPropertyOverrideMBeanImpl extends ConfigurationMBeanImpl implements JDBCPropertyOverrideMBean, Serializable {
   private String _EncryptedValue;
   private byte[] _EncryptedValueEncrypted;
   private String _SysPropValue;
   private String _Value;
   private static SchemaHelper2 _schemaHelper;

   public JDBCPropertyOverrideMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JDBCPropertyOverrideMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JDBCPropertyOverrideMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getValue() {
      return this._Value;
   }

   public boolean isValueInherited() {
      return false;
   }

   public boolean isValueSet() {
      return this._isSet(10);
   }

   public void setValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Value;
      this._Value = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getSysPropValue() {
      return this._SysPropValue;
   }

   public boolean isSysPropValueInherited() {
      return false;
   }

   public boolean isSysPropValueSet() {
      return this._isSet(11);
   }

   public void setSysPropValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SysPropValue;
      this._SysPropValue = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getEncryptedValue() {
      byte[] bEncrypted = this.getEncryptedValueEncrypted();
      return bEncrypted == null ? null : this._decrypt("EncryptedValue", bEncrypted);
   }

   public boolean isEncryptedValueInherited() {
      return false;
   }

   public boolean isEncryptedValueSet() {
      return this.isEncryptedValueEncryptedSet();
   }

   public void setEncryptedValue(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setEncryptedValueEncrypted(param0 == null ? null : this._encrypt("EncryptedValue", param0));
   }

   public byte[] getEncryptedValueEncrypted() {
      return this._getHelper()._cloneArray(this._EncryptedValueEncrypted);
   }

   public String getEncryptedValueEncryptedAsString() {
      byte[] obj = this.getEncryptedValueEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isEncryptedValueEncryptedInherited() {
      return false;
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

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setEncryptedValueEncrypted(byte[] param0) {
      byte[] _oldVal = this._EncryptedValueEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: EncryptedValueEncrypted of JDBCPropertyOverrideMBean");
      } else {
         this._getHelper()._clearArray(this._EncryptedValueEncrypted);
         this._EncryptedValueEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(13, _oldVal, param0);
      }
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
               this._SysPropValue = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Value = null;
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
      return "JDBCPropertyOverride";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("EncryptedValue")) {
         oldVal = this._EncryptedValue;
         this._EncryptedValue = (String)v;
         this._postSet(12, oldVal, this._EncryptedValue);
      } else if (name.equals("EncryptedValueEncrypted")) {
         byte[] oldVal = this._EncryptedValueEncrypted;
         this._EncryptedValueEncrypted = (byte[])((byte[])v);
         this._postSet(13, oldVal, this._EncryptedValueEncrypted);
      } else if (name.equals("SysPropValue")) {
         oldVal = this._SysPropValue;
         this._SysPropValue = (String)v;
         this._postSet(11, oldVal, this._SysPropValue);
      } else if (name.equals("Value")) {
         oldVal = this._Value;
         this._Value = (String)v;
         this._postSet(10, oldVal, this._Value);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("EncryptedValue")) {
         return this._EncryptedValue;
      } else if (name.equals("EncryptedValueEncrypted")) {
         return this._EncryptedValueEncrypted;
      } else if (name.equals("SysPropValue")) {
         return this._SysPropValue;
      } else {
         return name.equals("Value") ? this._Value : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("value")) {
                  return 10;
               }
               break;
            case 14:
               if (s.equals("sys-prop-value")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("encrypted-value")) {
                  return 12;
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
               return "value";
            case 11:
               return "sys-prop-value";
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

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 11:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
      private JDBCPropertyOverrideMBeanImpl bean;

      protected Helper(JDBCPropertyOverrideMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Value";
            case 11:
               return "SysPropValue";
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
         } else if (propName.equals("SysPropValue")) {
            return 11;
         } else {
            return propName.equals("Value") ? 10 : super.getPropertyIndex(propName);
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

            if (this.bean.isSysPropValueSet()) {
               buf.append("SysPropValue");
               buf.append(String.valueOf(this.bean.getSysPropValue()));
            }

            if (this.bean.isValueSet()) {
               buf.append("Value");
               buf.append(String.valueOf(this.bean.getValue()));
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
            JDBCPropertyOverrideMBeanImpl otherTyped = (JDBCPropertyOverrideMBeanImpl)other;
            this.addRestartElements("EncryptedValueEncrypted", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("EncryptedValueEncrypted", this.bean.getEncryptedValueEncrypted(), otherTyped.getEncryptedValueEncrypted(), true);
            this.addRestartElements("SysPropValue", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("SysPropValue", this.bean.getSysPropValue(), otherTyped.getSysPropValue(), true);
            this.addRestartElements("Value", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("Value", this.bean.getValue(), otherTyped.getValue(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCPropertyOverrideMBeanImpl original = (JDBCPropertyOverrideMBeanImpl)event.getSourceBean();
            JDBCPropertyOverrideMBeanImpl proposed = (JDBCPropertyOverrideMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("EncryptedValue")) {
                  if (prop.equals("EncryptedValueEncrypted")) {
                     original.setEncryptedValueEncrypted(proposed.getEncryptedValueEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("SysPropValue")) {
                     original.setSysPropValue(proposed.getSysPropValue());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("Value")) {
                     original.setValue(proposed.getValue());
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
            JDBCPropertyOverrideMBeanImpl copy = (JDBCPropertyOverrideMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EncryptedValueEncrypted")) && this.bean.isEncryptedValueEncryptedSet()) {
               Object o = this.bean.getEncryptedValueEncrypted();
               copy.setEncryptedValueEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("SysPropValue")) && this.bean.isSysPropValueSet()) {
               copy.setSysPropValue(this.bean.getSysPropValue());
            }

            if ((excludeProps == null || !excludeProps.contains("Value")) && this.bean.isValueSet()) {
               copy.setValue(this.bean.getValue());
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

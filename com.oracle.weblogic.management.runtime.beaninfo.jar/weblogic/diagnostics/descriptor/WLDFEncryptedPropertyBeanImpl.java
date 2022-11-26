package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WLDFEncryptedPropertyBeanImpl extends WLDFConfigurationPropertyBeanImpl implements WLDFEncryptedPropertyBean, Serializable {
   private String _EncryptedValue;
   private byte[] _EncryptedValueEncrypted;
   private static SchemaHelper2 _schemaHelper;

   public WLDFEncryptedPropertyBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFEncryptedPropertyBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFEncryptedPropertyBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
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

   public void setEncryptedValue(String param0) {
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
      return this._isSet(3);
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
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: EncryptedValueEncrypted of WLDFEncryptedPropertyBean");
      } else {
         this._getHelper()._clearArray(this._EncryptedValueEncrypted);
         this._EncryptedValueEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(3, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 2) {
            this._markSet(3, false);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._EncryptedValueEncrypted = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._EncryptedValueEncrypted = null;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFConfigurationPropertyBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 15:
               if (s.equals("encrypted-value")) {
                  return 2;
               }
               break;
            case 25:
               if (s.equals("encrypted-value-encrypted")) {
                  return 3;
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
               return "encrypted-value";
            case 3:
               return "encrypted-value-encrypted";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends WLDFConfigurationPropertyBeanImpl.Helper {
      private WLDFEncryptedPropertyBeanImpl bean;

      protected Helper(WLDFEncryptedPropertyBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "EncryptedValue";
            case 3:
               return "EncryptedValueEncrypted";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EncryptedValue")) {
            return 2;
         } else {
            return propName.equals("EncryptedValueEncrypted") ? 3 : super.getPropertyIndex(propName);
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

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            WLDFEncryptedPropertyBeanImpl otherTyped = (WLDFEncryptedPropertyBeanImpl)other;
            this.computeDiff("EncryptedValueEncrypted", this.bean.getEncryptedValueEncrypted(), otherTyped.getEncryptedValueEncrypted(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFEncryptedPropertyBeanImpl original = (WLDFEncryptedPropertyBeanImpl)event.getSourceBean();
            WLDFEncryptedPropertyBeanImpl proposed = (WLDFEncryptedPropertyBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("EncryptedValue")) {
                  if (prop.equals("EncryptedValueEncrypted")) {
                     original.setEncryptedValueEncrypted(proposed.getEncryptedValueEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            WLDFEncryptedPropertyBeanImpl copy = (WLDFEncryptedPropertyBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EncryptedValueEncrypted")) && this.bean.isEncryptedValueEncryptedSet()) {
               Object o = this.bean.getEncryptedValueEncrypted();
               copy.setEncryptedValueEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
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

package weblogic.j2ee.descriptor.wl;

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
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.descriptor.beangen.CustomizerFactoryBuilder;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.customizers.ArrayMemberBeanCustomizer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ArrayMemberBeanImpl extends AbstractDescriptorBean implements ArrayMemberBean, Serializable {
   private String[] _CleartextOverrideValues;
   private String _MemberName;
   private String[] _MemberValues;
   private String[] _OverrideValues;
   private boolean _RequiresEncryption;
   private String _SecuredOverrideValue;
   private byte[] _SecuredOverrideValueEncrypted;
   private String _ShortDescription;
   private transient ArrayMemberBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ArrayMemberBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.ArrayMemberBeanCustomizerFactory");
         this._customizer = (ArrayMemberBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ArrayMemberBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.ArrayMemberBeanCustomizerFactory");
         this._customizer = (ArrayMemberBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ArrayMemberBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.ArrayMemberBeanCustomizerFactory");
         this._customizer = (ArrayMemberBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getMemberName() {
      return this._MemberName;
   }

   public boolean isMemberNameInherited() {
      return false;
   }

   public boolean isMemberNameSet() {
      return this._isSet(0);
   }

   public void setMemberName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MemberName;
      this._MemberName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getMemberValues() {
      return this._MemberValues;
   }

   public boolean isMemberValuesInherited() {
      return false;
   }

   public boolean isMemberValuesSet() {
      return this._isSet(1);
   }

   public void setMemberValues(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._MemberValues;
      this._MemberValues = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String[] getOverrideValues() {
      return this._customizer.getOverrideValues();
   }

   public boolean isOverrideValuesInherited() {
      return false;
   }

   public boolean isOverrideValuesSet() {
      return this._isSet(2);
   }

   public void setOverrideValues(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getOverrideValues();
      this._customizer.setOverrideValues(param0);
      this._postSet(2, _oldVal, param0);
   }

   public boolean getRequiresEncryption() {
      return this._RequiresEncryption;
   }

   public boolean isRequiresEncryptionInherited() {
      return false;
   }

   public boolean isRequiresEncryptionSet() {
      return this._isSet(3);
   }

   public void setRequiresEncryption(boolean param0) {
      boolean _oldVal = this._RequiresEncryption;
      this._RequiresEncryption = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String[] getCleartextOverrideValues() {
      return this._CleartextOverrideValues;
   }

   public boolean isCleartextOverrideValuesInherited() {
      return false;
   }

   public boolean isCleartextOverrideValuesSet() {
      return this._isSet(4);
   }

   public void setCleartextOverrideValues(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._CleartextOverrideValues;
      this._CleartextOverrideValues = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getSecuredOverrideValue() {
      byte[] bEncrypted = this.getSecuredOverrideValueEncrypted();
      return bEncrypted == null ? null : this._decrypt("SecuredOverrideValue", bEncrypted);
   }

   public boolean isSecuredOverrideValueInherited() {
      return false;
   }

   public boolean isSecuredOverrideValueSet() {
      return this.isSecuredOverrideValueEncryptedSet();
   }

   public void setSecuredOverrideValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setSecuredOverrideValueEncrypted(param0 == null ? null : this._encrypt("SecuredOverrideValue", param0));
   }

   public byte[] getSecuredOverrideValueEncrypted() {
      return this._getHelper()._cloneArray(this._SecuredOverrideValueEncrypted);
   }

   public String getSecuredOverrideValueEncryptedAsString() {
      byte[] obj = this.getSecuredOverrideValueEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isSecuredOverrideValueEncryptedInherited() {
      return false;
   }

   public boolean isSecuredOverrideValueEncryptedSet() {
      return this._isSet(6);
   }

   public void setSecuredOverrideValueEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setSecuredOverrideValueEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getShortDescription() {
      return this._customizer.getShortDescription();
   }

   public boolean isShortDescriptionInherited() {
      return false;
   }

   public boolean isShortDescriptionSet() {
      return this._isSet(7);
   }

   public void setShortDescription(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ShortDescription;
      this._ShortDescription = param0;
      this._postSet(7, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getMemberName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setSecuredOverrideValueEncrypted(byte[] param0) {
      byte[] _oldVal = this._SecuredOverrideValueEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: SecuredOverrideValueEncrypted of ArrayMemberBean");
      } else {
         this._getHelper()._clearArray(this._SecuredOverrideValueEncrypted);
         this._SecuredOverrideValueEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(6, _oldVal, param0);
      }
   }

   protected void _postCreate() {
      this._customizer._postCreate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 11:
            if (s.equals("member-name")) {
               return info.compareXpaths(this._getPropertyXpath("member-name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 5) {
            this._markSet(6, false);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._CleartextOverrideValues = new String[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._MemberName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._MemberValues = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setOverrideValues(new String[0]);
               if (initOne) {
                  break;
               }
            case 3:
               this._RequiresEncryption = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._SecuredOverrideValueEncrypted = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._SecuredOverrideValueEncrypted = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._ShortDescription = null;
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("member-name")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("member-value")) {
                  return 1;
               }
            case 13:
            case 15:
            case 16:
            case 18:
            case 20:
            case 21:
            case 23:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            default:
               break;
            case 14:
               if (s.equals("override-value")) {
                  return 2;
               }
               break;
            case 17:
               if (s.equals("short-description")) {
                  return 7;
               }
               break;
            case 19:
               if (s.equals("requires-encryption")) {
                  return 3;
               }
               break;
            case 22:
               if (s.equals("secured-override-value")) {
                  return 5;
               }
               break;
            case 24:
               if (s.equals("cleartext-override-value")) {
                  return 4;
               }
               break;
            case 32:
               if (s.equals("secured-override-value-encrypted")) {
                  return 6;
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
            case 0:
               return "member-name";
            case 1:
               return "member-value";
            case 2:
               return "override-value";
            case 3:
               return "requires-encryption";
            case 4:
               return "cleartext-override-value";
            case 5:
               return "secured-override-value";
            case 6:
               return "secured-override-value-encrypted";
            case 7:
               return "short-description";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
            default:
               return super.isArray(propIndex);
            case 4:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 6:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("member-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ArrayMemberBeanImpl bean;

      protected Helper(ArrayMemberBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MemberName";
            case 1:
               return "MemberValues";
            case 2:
               return "OverrideValues";
            case 3:
               return "RequiresEncryption";
            case 4:
               return "CleartextOverrideValues";
            case 5:
               return "SecuredOverrideValue";
            case 6:
               return "SecuredOverrideValueEncrypted";
            case 7:
               return "ShortDescription";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CleartextOverrideValues")) {
            return 4;
         } else if (propName.equals("MemberName")) {
            return 0;
         } else if (propName.equals("MemberValues")) {
            return 1;
         } else if (propName.equals("OverrideValues")) {
            return 2;
         } else if (propName.equals("RequiresEncryption")) {
            return 3;
         } else if (propName.equals("SecuredOverrideValue")) {
            return 5;
         } else if (propName.equals("SecuredOverrideValueEncrypted")) {
            return 6;
         } else {
            return propName.equals("ShortDescription") ? 7 : super.getPropertyIndex(propName);
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
            if (this.bean.isCleartextOverrideValuesSet()) {
               buf.append("CleartextOverrideValues");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCleartextOverrideValues())));
            }

            if (this.bean.isMemberNameSet()) {
               buf.append("MemberName");
               buf.append(String.valueOf(this.bean.getMemberName()));
            }

            if (this.bean.isMemberValuesSet()) {
               buf.append("MemberValues");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getMemberValues())));
            }

            if (this.bean.isOverrideValuesSet()) {
               buf.append("OverrideValues");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getOverrideValues())));
            }

            if (this.bean.isRequiresEncryptionSet()) {
               buf.append("RequiresEncryption");
               buf.append(String.valueOf(this.bean.getRequiresEncryption()));
            }

            if (this.bean.isSecuredOverrideValueSet()) {
               buf.append("SecuredOverrideValue");
               buf.append(String.valueOf(this.bean.getSecuredOverrideValue()));
            }

            if (this.bean.isSecuredOverrideValueEncryptedSet()) {
               buf.append("SecuredOverrideValueEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSecuredOverrideValueEncrypted())));
            }

            if (this.bean.isShortDescriptionSet()) {
               buf.append("ShortDescription");
               buf.append(String.valueOf(this.bean.getShortDescription()));
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
            ArrayMemberBeanImpl otherTyped = (ArrayMemberBeanImpl)other;
            this.computeDiff("CleartextOverrideValues", this.bean.getCleartextOverrideValues(), otherTyped.getCleartextOverrideValues(), true);
            this.computeDiff("MemberName", this.bean.getMemberName(), otherTyped.getMemberName(), false);
            this.computeDiff("MemberValues", this.bean.getMemberValues(), otherTyped.getMemberValues(), true);
            this.computeDiff("OverrideValues", this.bean.getOverrideValues(), otherTyped.getOverrideValues(), true);
            this.computeDiff("RequiresEncryption", this.bean.getRequiresEncryption(), otherTyped.getRequiresEncryption(), false);
            this.computeDiff("SecuredOverrideValueEncrypted", this.bean.getSecuredOverrideValueEncrypted(), otherTyped.getSecuredOverrideValueEncrypted(), true);
            this.computeDiff("ShortDescription", this.bean.getShortDescription(), otherTyped.getShortDescription(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ArrayMemberBeanImpl original = (ArrayMemberBeanImpl)event.getSourceBean();
            ArrayMemberBeanImpl proposed = (ArrayMemberBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CleartextOverrideValues")) {
                  original.setCleartextOverrideValues(proposed.getCleartextOverrideValues());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("MemberName")) {
                  original.setMemberName(proposed.getMemberName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MemberValues")) {
                  original.setMemberValues(proposed.getMemberValues());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("OverrideValues")) {
                  original.setOverrideValues(proposed.getOverrideValues());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RequiresEncryption")) {
                  original.setRequiresEncryption(proposed.getRequiresEncryption());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (!prop.equals("SecuredOverrideValue")) {
                  if (prop.equals("SecuredOverrideValueEncrypted")) {
                     original.setSecuredOverrideValueEncrypted(proposed.getSecuredOverrideValueEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  } else if (prop.equals("ShortDescription")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
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
            ArrayMemberBeanImpl copy = (ArrayMemberBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("CleartextOverrideValues")) && this.bean.isCleartextOverrideValuesSet()) {
               o = this.bean.getCleartextOverrideValues();
               copy.setCleartextOverrideValues(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("MemberName")) && this.bean.isMemberNameSet()) {
               copy.setMemberName(this.bean.getMemberName());
            }

            if ((excludeProps == null || !excludeProps.contains("MemberValues")) && this.bean.isMemberValuesSet()) {
               o = this.bean.getMemberValues();
               copy.setMemberValues(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("OverrideValues")) && this.bean.isOverrideValuesSet()) {
               o = this.bean.getOverrideValues();
               copy.setOverrideValues(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("RequiresEncryption")) && this.bean.isRequiresEncryptionSet()) {
               copy.setRequiresEncryption(this.bean.getRequiresEncryption());
            }

            if ((excludeProps == null || !excludeProps.contains("SecuredOverrideValueEncrypted")) && this.bean.isSecuredOverrideValueEncryptedSet()) {
               Object o = this.bean.getSecuredOverrideValueEncrypted();
               copy.setSecuredOverrideValueEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ShortDescription")) && this.bean.isShortDescriptionSet()) {
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

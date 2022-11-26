package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class MemberConstraintBeanImpl extends AbstractDescriptorBean implements MemberConstraintBean, Serializable {
   private String _ConstraintType;
   private String _MaxLength;
   private String _MaxValue;
   private String _MinValue;
   private int _Scale;
   private static SchemaHelper2 _schemaHelper;

   public MemberConstraintBeanImpl() {
      this._initializeProperty(-1);
   }

   public MemberConstraintBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MemberConstraintBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getConstraintType() {
      return this._ConstraintType;
   }

   public boolean isConstraintTypeInherited() {
      return false;
   }

   public boolean isConstraintTypeSet() {
      return this._isSet(0);
   }

   public void setConstraintType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConstraintType;
      this._ConstraintType = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getMaxLength() {
      return this._MaxLength;
   }

   public boolean isMaxLengthInherited() {
      return false;
   }

   public boolean isMaxLengthSet() {
      return this._isSet(1);
   }

   public void setMaxLength(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MaxLength;
      this._MaxLength = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getMinValue() {
      return this._MinValue;
   }

   public boolean isMinValueInherited() {
      return false;
   }

   public boolean isMinValueSet() {
      return this._isSet(2);
   }

   public void setMinValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MinValue;
      this._MinValue = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getMaxValue() {
      return this._MaxValue;
   }

   public boolean isMaxValueInherited() {
      return false;
   }

   public boolean isMaxValueSet() {
      return this._isSet(3);
   }

   public void setMaxValue(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MaxValue;
      this._MaxValue = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getScale() {
      return this._Scale;
   }

   public boolean isScaleInherited() {
      return false;
   }

   public boolean isScaleSet() {
      return this._isSet(4);
   }

   public void setScale(int param0) {
      int _oldVal = this._Scale;
      this._Scale = param0;
      this._postSet(4, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getConstraintType();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 15:
            if (s.equals("constraint-type")) {
               return info.compareXpaths(this._getPropertyXpath("constraint-type"));
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ConstraintType = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._MaxLength = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._MaxValue = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._MinValue = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Scale = 0;
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
            case 5:
               if (s.equals("scale")) {
                  return 4;
               }
               break;
            case 9:
               if (s.equals("max-value")) {
                  return 3;
               }

               if (s.equals("min-value")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("max-length")) {
                  return 1;
               }
               break;
            case 15:
               if (s.equals("constraint-type")) {
                  return 0;
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
               return "constraint-type";
            case 1:
               return "max-length";
            case 2:
               return "min-value";
            case 3:
               return "max-value";
            case 4:
               return "scale";
            default:
               return super.getElementName(propIndex);
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
         indices.add("constraint-type");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MemberConstraintBeanImpl bean;

      protected Helper(MemberConstraintBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ConstraintType";
            case 1:
               return "MaxLength";
            case 2:
               return "MinValue";
            case 3:
               return "MaxValue";
            case 4:
               return "Scale";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConstraintType")) {
            return 0;
         } else if (propName.equals("MaxLength")) {
            return 1;
         } else if (propName.equals("MaxValue")) {
            return 3;
         } else if (propName.equals("MinValue")) {
            return 2;
         } else {
            return propName.equals("Scale") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isConstraintTypeSet()) {
               buf.append("ConstraintType");
               buf.append(String.valueOf(this.bean.getConstraintType()));
            }

            if (this.bean.isMaxLengthSet()) {
               buf.append("MaxLength");
               buf.append(String.valueOf(this.bean.getMaxLength()));
            }

            if (this.bean.isMaxValueSet()) {
               buf.append("MaxValue");
               buf.append(String.valueOf(this.bean.getMaxValue()));
            }

            if (this.bean.isMinValueSet()) {
               buf.append("MinValue");
               buf.append(String.valueOf(this.bean.getMinValue()));
            }

            if (this.bean.isScaleSet()) {
               buf.append("Scale");
               buf.append(String.valueOf(this.bean.getScale()));
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
            MemberConstraintBeanImpl otherTyped = (MemberConstraintBeanImpl)other;
            this.computeDiff("ConstraintType", this.bean.getConstraintType(), otherTyped.getConstraintType(), false);
            this.computeDiff("MaxLength", this.bean.getMaxLength(), otherTyped.getMaxLength(), false);
            this.computeDiff("MaxValue", this.bean.getMaxValue(), otherTyped.getMaxValue(), false);
            this.computeDiff("MinValue", this.bean.getMinValue(), otherTyped.getMinValue(), false);
            this.computeDiff("Scale", this.bean.getScale(), otherTyped.getScale(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MemberConstraintBeanImpl original = (MemberConstraintBeanImpl)event.getSourceBean();
            MemberConstraintBeanImpl proposed = (MemberConstraintBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConstraintType")) {
                  original.setConstraintType(proposed.getConstraintType());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MaxLength")) {
                  original.setMaxLength(proposed.getMaxLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MaxValue")) {
                  original.setMaxValue(proposed.getMaxValue());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("MinValue")) {
                  original.setMinValue(proposed.getMinValue());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Scale")) {
                  original.setScale(proposed.getScale());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            MemberConstraintBeanImpl copy = (MemberConstraintBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConstraintType")) && this.bean.isConstraintTypeSet()) {
               copy.setConstraintType(this.bean.getConstraintType());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxLength")) && this.bean.isMaxLengthSet()) {
               copy.setMaxLength(this.bean.getMaxLength());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxValue")) && this.bean.isMaxValueSet()) {
               copy.setMaxValue(this.bean.getMaxValue());
            }

            if ((excludeProps == null || !excludeProps.contains("MinValue")) && this.bean.isMinValueSet()) {
               copy.setMinValue(this.bean.getMinValue());
            }

            if ((excludeProps == null || !excludeProps.contains("Scale")) && this.bean.isScaleSet()) {
               copy.setScale(this.bean.getScale());
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

package kodo.jdbc.conf.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import kodo.conf.descriptor.SequenceBeanImpl;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class NativeJDBCSeqBeanImpl extends SequenceBeanImpl implements NativeJDBCSeqBean, Serializable {
   private int _Allocate;
   private String _Format;
   private int _Increment;
   private int _InitialValue;
   private String _Sequence;
   private String _SequenceName;
   private String _TableName;
   private int _Type;
   private static SchemaHelper2 _schemaHelper;

   public NativeJDBCSeqBeanImpl() {
      this._initializeProperty(-1);
   }

   public NativeJDBCSeqBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public NativeJDBCSeqBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getType() {
      return this._Type;
   }

   public boolean isTypeInherited() {
      return false;
   }

   public boolean isTypeSet() {
      return this._isSet(0);
   }

   public void setType(int param0) {
      int _oldVal = this._Type;
      this._Type = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getAllocate() {
      return this._Allocate;
   }

   public boolean isAllocateInherited() {
      return false;
   }

   public boolean isAllocateSet() {
      return this._isSet(1);
   }

   public void setAllocate(int param0) {
      int _oldVal = this._Allocate;
      this._Allocate = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getTableName() {
      return this._TableName;
   }

   public boolean isTableNameInherited() {
      return false;
   }

   public boolean isTableNameSet() {
      return this._isSet(2);
   }

   public void setTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableName;
      this._TableName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getInitialValue() {
      return this._InitialValue;
   }

   public boolean isInitialValueInherited() {
      return false;
   }

   public boolean isInitialValueSet() {
      return this._isSet(3);
   }

   public void setInitialValue(int param0) {
      int _oldVal = this._InitialValue;
      this._InitialValue = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getSequence() {
      return this._Sequence;
   }

   public boolean isSequenceInherited() {
      return false;
   }

   public boolean isSequenceSet() {
      return this._isSet(4);
   }

   public void setSequence(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Sequence;
      this._Sequence = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getSequenceName() {
      return this._SequenceName;
   }

   public boolean isSequenceNameInherited() {
      return false;
   }

   public boolean isSequenceNameSet() {
      return this._isSet(5);
   }

   public void setSequenceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SequenceName;
      this._SequenceName = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getFormat() {
      return this._Format;
   }

   public boolean isFormatInherited() {
      return false;
   }

   public boolean isFormatSet() {
      return this._isSet(6);
   }

   public void setFormat(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Format;
      this._Format = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getIncrement() {
      return this._Increment;
   }

   public boolean isIncrementInherited() {
      return false;
   }

   public boolean isIncrementSet() {
      return this._isSet(7);
   }

   public void setIncrement(int param0) {
      int _oldVal = this._Increment;
      this._Increment = param0;
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._Allocate = 0;
               if (initOne) {
                  break;
               }
            case 6:
               this._Format = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Increment = 1;
               if (initOne) {
                  break;
               }
            case 3:
               this._InitialValue = 1;
               if (initOne) {
                  break;
               }
            case 4:
               this._Sequence = "OPENJPA_SEQUENCE";
               if (initOne) {
                  break;
               }
            case 5:
               this._SequenceName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._TableName = "DUAL";
               if (initOne) {
                  break;
               }
            case 0:
               this._Type = 0;
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

   public static class SchemaHelper2 extends SequenceBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("type")) {
                  return 0;
               }
            case 5:
            case 7:
            case 11:
            case 12:
            default:
               break;
            case 6:
               if (s.equals("format")) {
                  return 6;
               }
               break;
            case 8:
               if (s.equals("allocate")) {
                  return 1;
               }

               if (s.equals("sequence")) {
                  return 4;
               }
               break;
            case 9:
               if (s.equals("increment")) {
                  return 7;
               }
               break;
            case 10:
               if (s.equals("table-name")) {
                  return 2;
               }
               break;
            case 13:
               if (s.equals("initial-value")) {
                  return 3;
               }

               if (s.equals("sequence-name")) {
                  return 5;
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
               return "type";
            case 1:
               return "allocate";
            case 2:
               return "table-name";
            case 3:
               return "initial-value";
            case 4:
               return "sequence";
            case 5:
               return "sequence-name";
            case 6:
               return "format";
            case 7:
               return "increment";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SequenceBeanImpl.Helper {
      private NativeJDBCSeqBeanImpl bean;

      protected Helper(NativeJDBCSeqBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Type";
            case 1:
               return "Allocate";
            case 2:
               return "TableName";
            case 3:
               return "InitialValue";
            case 4:
               return "Sequence";
            case 5:
               return "SequenceName";
            case 6:
               return "Format";
            case 7:
               return "Increment";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Allocate")) {
            return 1;
         } else if (propName.equals("Format")) {
            return 6;
         } else if (propName.equals("Increment")) {
            return 7;
         } else if (propName.equals("InitialValue")) {
            return 3;
         } else if (propName.equals("Sequence")) {
            return 4;
         } else if (propName.equals("SequenceName")) {
            return 5;
         } else if (propName.equals("TableName")) {
            return 2;
         } else {
            return propName.equals("Type") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isAllocateSet()) {
               buf.append("Allocate");
               buf.append(String.valueOf(this.bean.getAllocate()));
            }

            if (this.bean.isFormatSet()) {
               buf.append("Format");
               buf.append(String.valueOf(this.bean.getFormat()));
            }

            if (this.bean.isIncrementSet()) {
               buf.append("Increment");
               buf.append(String.valueOf(this.bean.getIncrement()));
            }

            if (this.bean.isInitialValueSet()) {
               buf.append("InitialValue");
               buf.append(String.valueOf(this.bean.getInitialValue()));
            }

            if (this.bean.isSequenceSet()) {
               buf.append("Sequence");
               buf.append(String.valueOf(this.bean.getSequence()));
            }

            if (this.bean.isSequenceNameSet()) {
               buf.append("SequenceName");
               buf.append(String.valueOf(this.bean.getSequenceName()));
            }

            if (this.bean.isTableNameSet()) {
               buf.append("TableName");
               buf.append(String.valueOf(this.bean.getTableName()));
            }

            if (this.bean.isTypeSet()) {
               buf.append("Type");
               buf.append(String.valueOf(this.bean.getType()));
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
            NativeJDBCSeqBeanImpl otherTyped = (NativeJDBCSeqBeanImpl)other;
            this.computeDiff("Allocate", this.bean.getAllocate(), otherTyped.getAllocate(), false);
            this.computeDiff("Format", this.bean.getFormat(), otherTyped.getFormat(), false);
            this.computeDiff("Increment", this.bean.getIncrement(), otherTyped.getIncrement(), false);
            this.computeDiff("InitialValue", this.bean.getInitialValue(), otherTyped.getInitialValue(), false);
            this.computeDiff("Sequence", this.bean.getSequence(), otherTyped.getSequence(), false);
            this.computeDiff("SequenceName", this.bean.getSequenceName(), otherTyped.getSequenceName(), false);
            this.computeDiff("TableName", this.bean.getTableName(), otherTyped.getTableName(), false);
            this.computeDiff("Type", this.bean.getType(), otherTyped.getType(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            NativeJDBCSeqBeanImpl original = (NativeJDBCSeqBeanImpl)event.getSourceBean();
            NativeJDBCSeqBeanImpl proposed = (NativeJDBCSeqBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Allocate")) {
                  original.setAllocate(proposed.getAllocate());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Format")) {
                  original.setFormat(proposed.getFormat());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Increment")) {
                  original.setIncrement(proposed.getIncrement());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("InitialValue")) {
                  original.setInitialValue(proposed.getInitialValue());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Sequence")) {
                  original.setSequence(proposed.getSequence());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("SequenceName")) {
                  original.setSequenceName(proposed.getSequenceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("TableName")) {
                  original.setTableName(proposed.getTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Type")) {
                  original.setType(proposed.getType());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            NativeJDBCSeqBeanImpl copy = (NativeJDBCSeqBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Allocate")) && this.bean.isAllocateSet()) {
               copy.setAllocate(this.bean.getAllocate());
            }

            if ((excludeProps == null || !excludeProps.contains("Format")) && this.bean.isFormatSet()) {
               copy.setFormat(this.bean.getFormat());
            }

            if ((excludeProps == null || !excludeProps.contains("Increment")) && this.bean.isIncrementSet()) {
               copy.setIncrement(this.bean.getIncrement());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialValue")) && this.bean.isInitialValueSet()) {
               copy.setInitialValue(this.bean.getInitialValue());
            }

            if ((excludeProps == null || !excludeProps.contains("Sequence")) && this.bean.isSequenceSet()) {
               copy.setSequence(this.bean.getSequence());
            }

            if ((excludeProps == null || !excludeProps.contains("SequenceName")) && this.bean.isSequenceNameSet()) {
               copy.setSequenceName(this.bean.getSequenceName());
            }

            if ((excludeProps == null || !excludeProps.contains("TableName")) && this.bean.isTableNameSet()) {
               copy.setTableName(this.bean.getTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("Type")) && this.bean.isTypeSet()) {
               copy.setType(this.bean.getType());
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

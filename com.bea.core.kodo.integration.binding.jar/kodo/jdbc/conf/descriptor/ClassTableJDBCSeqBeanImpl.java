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

public class ClassTableJDBCSeqBeanImpl extends SequenceBeanImpl implements ClassTableJDBCSeqBean, Serializable {
   private int _Allocate;
   private boolean _IgnoreUnmapped;
   private boolean _IgnoreVirtual;
   private int _Increment;
   private String _PrimaryKeyColumn;
   private String _SequenceColumn;
   private String _Table;
   private String _TableName;
   private int _Type;
   private boolean _UseAliases;
   private static SchemaHelper2 _schemaHelper;

   public ClassTableJDBCSeqBeanImpl() {
      this._initializeProperty(-1);
   }

   public ClassTableJDBCSeqBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ClassTableJDBCSeqBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public boolean getIgnoreVirtual() {
      return this._IgnoreVirtual;
   }

   public boolean isIgnoreVirtualInherited() {
      return false;
   }

   public boolean isIgnoreVirtualSet() {
      return this._isSet(3);
   }

   public void setIgnoreVirtual(boolean param0) {
      boolean _oldVal = this._IgnoreVirtual;
      this._IgnoreVirtual = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean getIgnoreUnmapped() {
      return this._IgnoreUnmapped;
   }

   public boolean isIgnoreUnmappedInherited() {
      return false;
   }

   public boolean isIgnoreUnmappedSet() {
      return this._isSet(4);
   }

   public void setIgnoreUnmapped(boolean param0) {
      boolean _oldVal = this._IgnoreUnmapped;
      this._IgnoreUnmapped = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getTable() {
      return this._Table;
   }

   public boolean isTableInherited() {
      return false;
   }

   public boolean isTableSet() {
      return this._isSet(5);
   }

   public void setTable(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Table;
      this._Table = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getPrimaryKeyColumn() {
      return this._PrimaryKeyColumn;
   }

   public boolean isPrimaryKeyColumnInherited() {
      return false;
   }

   public boolean isPrimaryKeyColumnSet() {
      return this._isSet(6);
   }

   public void setPrimaryKeyColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PrimaryKeyColumn;
      this._PrimaryKeyColumn = param0;
      this._postSet(6, _oldVal, param0);
   }

   public boolean getUseAliases() {
      return this._UseAliases;
   }

   public boolean isUseAliasesInherited() {
      return false;
   }

   public boolean isUseAliasesSet() {
      return this._isSet(7);
   }

   public void setUseAliases(boolean param0) {
      boolean _oldVal = this._UseAliases;
      this._UseAliases = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getSequenceColumn() {
      return this._SequenceColumn;
   }

   public boolean isSequenceColumnInherited() {
      return false;
   }

   public boolean isSequenceColumnSet() {
      return this._isSet(8);
   }

   public void setSequenceColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SequenceColumn;
      this._SequenceColumn = param0;
      this._postSet(8, _oldVal, param0);
   }

   public int getIncrement() {
      return this._Increment;
   }

   public boolean isIncrementInherited() {
      return false;
   }

   public boolean isIncrementSet() {
      return this._isSet(9);
   }

   public void setIncrement(int param0) {
      int _oldVal = this._Increment;
      this._Increment = param0;
      this._postSet(9, _oldVal, param0);
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
               this._Allocate = 50;
               if (initOne) {
                  break;
               }
            case 4:
               this._IgnoreUnmapped = false;
               if (initOne) {
                  break;
               }
            case 3:
               this._IgnoreVirtual = false;
               if (initOne) {
                  break;
               }
            case 9:
               this._Increment = 0;
               if (initOne) {
                  break;
               }
            case 6:
               this._PrimaryKeyColumn = "ID";
               if (initOne) {
                  break;
               }
            case 8:
               this._SequenceColumn = "SEQUENCE_VALUE";
               if (initOne) {
                  break;
               }
            case 5:
               this._Table = "OPENJPA_SEQUENCES_TABLE";
               if (initOne) {
                  break;
               }
            case 2:
               this._TableName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Type = 0;
               if (initOne) {
                  break;
               }
            case 7:
               this._UseAliases = false;
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
               break;
            case 5:
               if (s.equals("table")) {
                  return 5;
               }
            case 6:
            case 7:
            case 12:
            case 13:
            case 16:
            case 17:
            default:
               break;
            case 8:
               if (s.equals("allocate")) {
                  return 1;
               }
               break;
            case 9:
               if (s.equals("increment")) {
                  return 9;
               }
               break;
            case 10:
               if (s.equals("table-name")) {
                  return 2;
               }
               break;
            case 11:
               if (s.equals("use-aliases")) {
                  return 7;
               }
               break;
            case 14:
               if (s.equals("ignore-virtual")) {
                  return 3;
               }
               break;
            case 15:
               if (s.equals("ignore-unmapped")) {
                  return 4;
               }

               if (s.equals("sequence-column")) {
                  return 8;
               }
               break;
            case 18:
               if (s.equals("primary-key-column")) {
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
               return "type";
            case 1:
               return "allocate";
            case 2:
               return "table-name";
            case 3:
               return "ignore-virtual";
            case 4:
               return "ignore-unmapped";
            case 5:
               return "table";
            case 6:
               return "primary-key-column";
            case 7:
               return "use-aliases";
            case 8:
               return "sequence-column";
            case 9:
               return "increment";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SequenceBeanImpl.Helper {
      private ClassTableJDBCSeqBeanImpl bean;

      protected Helper(ClassTableJDBCSeqBeanImpl bean) {
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
               return "IgnoreVirtual";
            case 4:
               return "IgnoreUnmapped";
            case 5:
               return "Table";
            case 6:
               return "PrimaryKeyColumn";
            case 7:
               return "UseAliases";
            case 8:
               return "SequenceColumn";
            case 9:
               return "Increment";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Allocate")) {
            return 1;
         } else if (propName.equals("IgnoreUnmapped")) {
            return 4;
         } else if (propName.equals("IgnoreVirtual")) {
            return 3;
         } else if (propName.equals("Increment")) {
            return 9;
         } else if (propName.equals("PrimaryKeyColumn")) {
            return 6;
         } else if (propName.equals("SequenceColumn")) {
            return 8;
         } else if (propName.equals("Table")) {
            return 5;
         } else if (propName.equals("TableName")) {
            return 2;
         } else if (propName.equals("Type")) {
            return 0;
         } else {
            return propName.equals("UseAliases") ? 7 : super.getPropertyIndex(propName);
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

            if (this.bean.isIgnoreUnmappedSet()) {
               buf.append("IgnoreUnmapped");
               buf.append(String.valueOf(this.bean.getIgnoreUnmapped()));
            }

            if (this.bean.isIgnoreVirtualSet()) {
               buf.append("IgnoreVirtual");
               buf.append(String.valueOf(this.bean.getIgnoreVirtual()));
            }

            if (this.bean.isIncrementSet()) {
               buf.append("Increment");
               buf.append(String.valueOf(this.bean.getIncrement()));
            }

            if (this.bean.isPrimaryKeyColumnSet()) {
               buf.append("PrimaryKeyColumn");
               buf.append(String.valueOf(this.bean.getPrimaryKeyColumn()));
            }

            if (this.bean.isSequenceColumnSet()) {
               buf.append("SequenceColumn");
               buf.append(String.valueOf(this.bean.getSequenceColumn()));
            }

            if (this.bean.isTableSet()) {
               buf.append("Table");
               buf.append(String.valueOf(this.bean.getTable()));
            }

            if (this.bean.isTableNameSet()) {
               buf.append("TableName");
               buf.append(String.valueOf(this.bean.getTableName()));
            }

            if (this.bean.isTypeSet()) {
               buf.append("Type");
               buf.append(String.valueOf(this.bean.getType()));
            }

            if (this.bean.isUseAliasesSet()) {
               buf.append("UseAliases");
               buf.append(String.valueOf(this.bean.getUseAliases()));
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
            ClassTableJDBCSeqBeanImpl otherTyped = (ClassTableJDBCSeqBeanImpl)other;
            this.computeDiff("Allocate", this.bean.getAllocate(), otherTyped.getAllocate(), false);
            this.computeDiff("IgnoreUnmapped", this.bean.getIgnoreUnmapped(), otherTyped.getIgnoreUnmapped(), false);
            this.computeDiff("IgnoreVirtual", this.bean.getIgnoreVirtual(), otherTyped.getIgnoreVirtual(), false);
            this.computeDiff("Increment", this.bean.getIncrement(), otherTyped.getIncrement(), false);
            this.computeDiff("PrimaryKeyColumn", this.bean.getPrimaryKeyColumn(), otherTyped.getPrimaryKeyColumn(), false);
            this.computeDiff("SequenceColumn", this.bean.getSequenceColumn(), otherTyped.getSequenceColumn(), false);
            this.computeDiff("Table", this.bean.getTable(), otherTyped.getTable(), false);
            this.computeDiff("TableName", this.bean.getTableName(), otherTyped.getTableName(), false);
            this.computeDiff("Type", this.bean.getType(), otherTyped.getType(), false);
            this.computeDiff("UseAliases", this.bean.getUseAliases(), otherTyped.getUseAliases(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ClassTableJDBCSeqBeanImpl original = (ClassTableJDBCSeqBeanImpl)event.getSourceBean();
            ClassTableJDBCSeqBeanImpl proposed = (ClassTableJDBCSeqBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Allocate")) {
                  original.setAllocate(proposed.getAllocate());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("IgnoreUnmapped")) {
                  original.setIgnoreUnmapped(proposed.getIgnoreUnmapped());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("IgnoreVirtual")) {
                  original.setIgnoreVirtual(proposed.getIgnoreVirtual());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Increment")) {
                  original.setIncrement(proposed.getIncrement());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("PrimaryKeyColumn")) {
                  original.setPrimaryKeyColumn(proposed.getPrimaryKeyColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("SequenceColumn")) {
                  original.setSequenceColumn(proposed.getSequenceColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("Table")) {
                  original.setTable(proposed.getTable());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("TableName")) {
                  original.setTableName(proposed.getTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Type")) {
                  original.setType(proposed.getType());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("UseAliases")) {
                  original.setUseAliases(proposed.getUseAliases());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
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
            ClassTableJDBCSeqBeanImpl copy = (ClassTableJDBCSeqBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Allocate")) && this.bean.isAllocateSet()) {
               copy.setAllocate(this.bean.getAllocate());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreUnmapped")) && this.bean.isIgnoreUnmappedSet()) {
               copy.setIgnoreUnmapped(this.bean.getIgnoreUnmapped());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreVirtual")) && this.bean.isIgnoreVirtualSet()) {
               copy.setIgnoreVirtual(this.bean.getIgnoreVirtual());
            }

            if ((excludeProps == null || !excludeProps.contains("Increment")) && this.bean.isIncrementSet()) {
               copy.setIncrement(this.bean.getIncrement());
            }

            if ((excludeProps == null || !excludeProps.contains("PrimaryKeyColumn")) && this.bean.isPrimaryKeyColumnSet()) {
               copy.setPrimaryKeyColumn(this.bean.getPrimaryKeyColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("SequenceColumn")) && this.bean.isSequenceColumnSet()) {
               copy.setSequenceColumn(this.bean.getSequenceColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("Table")) && this.bean.isTableSet()) {
               copy.setTable(this.bean.getTable());
            }

            if ((excludeProps == null || !excludeProps.contains("TableName")) && this.bean.isTableNameSet()) {
               copy.setTableName(this.bean.getTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("Type")) && this.bean.isTypeSet()) {
               copy.setType(this.bean.getType());
            }

            if ((excludeProps == null || !excludeProps.contains("UseAliases")) && this.bean.isUseAliasesSet()) {
               copy.setUseAliases(this.bean.getUseAliases());
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

package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class TableMapBeanImpl extends AbstractDescriptorBean implements TableMapBean, Serializable {
   private FieldMapBean[] _FieldMaps;
   private String _Id;
   private String _OptimisticColumn;
   private String _TableName;
   private boolean _TriggerUpdatesOptimisticColumn;
   private String _VerifyColumns;
   private String _VerifyRows;
   private int _VersionColumnInitialValue;
   private static SchemaHelper2 _schemaHelper;

   public TableMapBeanImpl() {
      this._initializeProperty(-1);
   }

   public TableMapBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TableMapBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getTableName() {
      return this._TableName;
   }

   public boolean isTableNameInherited() {
      return false;
   }

   public boolean isTableNameSet() {
      return this._isSet(0);
   }

   public void setTableName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TableName;
      this._TableName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addFieldMap(FieldMapBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         FieldMapBean[] _new;
         if (this._isSet(1)) {
            _new = (FieldMapBean[])((FieldMapBean[])this._getHelper()._extendArray(this.getFieldMaps(), FieldMapBean.class, param0));
         } else {
            _new = new FieldMapBean[]{param0};
         }

         try {
            this.setFieldMaps(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FieldMapBean[] getFieldMaps() {
      return this._FieldMaps;
   }

   public boolean isFieldMapsInherited() {
      return false;
   }

   public boolean isFieldMapsSet() {
      return this._isSet(1);
   }

   public void removeFieldMap(FieldMapBean param0) {
      this.destroyFieldMap(param0);
   }

   public void setFieldMaps(FieldMapBean[] param0) throws InvalidAttributeValueException {
      FieldMapBean[] param0 = param0 == null ? new FieldMapBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      FieldMapBean[] _oldVal = this._FieldMaps;
      this._FieldMaps = (FieldMapBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public FieldMapBean createFieldMap() {
      FieldMapBeanImpl _val = new FieldMapBeanImpl(this, -1);

      try {
         this.addFieldMap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFieldMap(FieldMapBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         FieldMapBean[] _old = this.getFieldMaps();
         FieldMapBean[] _new = (FieldMapBean[])((FieldMapBean[])this._getHelper()._removeElement(_old, FieldMapBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFieldMaps(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public String getVerifyRows() {
      return !this._isSet(2) ? null : this._VerifyRows;
   }

   public boolean isVerifyRowsInherited() {
      return false;
   }

   public boolean isVerifyRowsSet() {
      return this._isSet(2);
   }

   public void setVerifyRows(String param0) {
      if (param0 == null) {
         this._unSet(2);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"Modified", "Read"};
         param0 = LegalChecks.checkInEnum("VerifyRows", param0, _set);
         String _oldVal = this._VerifyRows;
         this._VerifyRows = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public String getVerifyColumns() {
      return !this._isSet(3) ? null : this._VerifyColumns;
   }

   public boolean isVerifyColumnsInherited() {
      return false;
   }

   public boolean isVerifyColumnsSet() {
      return this._isSet(3);
   }

   public void setVerifyColumns(String param0) {
      if (param0 == null) {
         this._unSet(3);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"Read", "Modified", "Version", "Timestamp"};
         param0 = LegalChecks.checkInEnum("VerifyColumns", param0, _set);
         String _oldVal = this._VerifyColumns;
         this._VerifyColumns = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public String getOptimisticColumn() {
      return this._OptimisticColumn;
   }

   public boolean isOptimisticColumnInherited() {
      return false;
   }

   public boolean isOptimisticColumnSet() {
      return this._isSet(4);
   }

   public void setOptimisticColumn(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OptimisticColumn;
      this._OptimisticColumn = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isTriggerUpdatesOptimisticColumn() {
      return this._TriggerUpdatesOptimisticColumn;
   }

   public boolean isTriggerUpdatesOptimisticColumnInherited() {
      return false;
   }

   public boolean isTriggerUpdatesOptimisticColumnSet() {
      return this._isSet(5);
   }

   public void setTriggerUpdatesOptimisticColumn(boolean param0) {
      boolean _oldVal = this._TriggerUpdatesOptimisticColumn;
      this._TriggerUpdatesOptimisticColumn = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getVersionColumnInitialValue() {
      return this._VersionColumnInitialValue;
   }

   public boolean isVersionColumnInitialValueInherited() {
      return false;
   }

   public boolean isVersionColumnInitialValueSet() {
      return this._isSet(6);
   }

   public void setVersionColumnInitialValue(int param0) {
      int _oldVal = this._VersionColumnInitialValue;
      this._VersionColumnInitialValue = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getTableName();
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
         case 10:
            if (s.equals("table-name")) {
               return info.compareXpaths(this._getPropertyXpath("table-name"));
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._FieldMaps = new FieldMapBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._OptimisticColumn = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._TableName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._VerifyColumns = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._VerifyRows = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._VersionColumnInitialValue = 1;
               if (initOne) {
                  break;
               }
            case 5:
               this._TriggerUpdatesOptimisticColumn = false;
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
            case 2:
               if (s.equals("id")) {
                  return 7;
               }
               break;
            case 9:
               if (s.equals("field-map")) {
                  return 1;
               }
               break;
            case 10:
               if (s.equals("table-name")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("verify-rows")) {
                  return 2;
               }
               break;
            case 14:
               if (s.equals("verify-columns")) {
                  return 3;
               }
               break;
            case 17:
               if (s.equals("optimistic-column")) {
                  return 4;
               }
               break;
            case 28:
               if (s.equals("version-column-initial-value")) {
                  return 6;
               }
               break;
            case 33:
               if (s.equals("trigger-updates-optimistic-column")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new FieldMapBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "table-name";
            case 1:
               return "field-map";
            case 2:
               return "verify-rows";
            case 3:
               return "verify-columns";
            case 4:
               return "optimistic-column";
            case 5:
               return "trigger-updates-optimistic-column";
            case 6:
               return "version-column-initial-value";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
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
         indices.add("table-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private TableMapBeanImpl bean;

      protected Helper(TableMapBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TableName";
            case 1:
               return "FieldMaps";
            case 2:
               return "VerifyRows";
            case 3:
               return "VerifyColumns";
            case 4:
               return "OptimisticColumn";
            case 5:
               return "TriggerUpdatesOptimisticColumn";
            case 6:
               return "VersionColumnInitialValue";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FieldMaps")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("OptimisticColumn")) {
            return 4;
         } else if (propName.equals("TableName")) {
            return 0;
         } else if (propName.equals("VerifyColumns")) {
            return 3;
         } else if (propName.equals("VerifyRows")) {
            return 2;
         } else if (propName.equals("VersionColumnInitialValue")) {
            return 6;
         } else {
            return propName.equals("TriggerUpdatesOptimisticColumn") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getFieldMaps()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getFieldMaps().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFieldMaps()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isOptimisticColumnSet()) {
               buf.append("OptimisticColumn");
               buf.append(String.valueOf(this.bean.getOptimisticColumn()));
            }

            if (this.bean.isTableNameSet()) {
               buf.append("TableName");
               buf.append(String.valueOf(this.bean.getTableName()));
            }

            if (this.bean.isVerifyColumnsSet()) {
               buf.append("VerifyColumns");
               buf.append(String.valueOf(this.bean.getVerifyColumns()));
            }

            if (this.bean.isVerifyRowsSet()) {
               buf.append("VerifyRows");
               buf.append(String.valueOf(this.bean.getVerifyRows()));
            }

            if (this.bean.isVersionColumnInitialValueSet()) {
               buf.append("VersionColumnInitialValue");
               buf.append(String.valueOf(this.bean.getVersionColumnInitialValue()));
            }

            if (this.bean.isTriggerUpdatesOptimisticColumnSet()) {
               buf.append("TriggerUpdatesOptimisticColumn");
               buf.append(String.valueOf(this.bean.isTriggerUpdatesOptimisticColumn()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            TableMapBeanImpl otherTyped = (TableMapBeanImpl)other;
            this.computeChildDiff("FieldMaps", this.bean.getFieldMaps(), otherTyped.getFieldMaps(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("OptimisticColumn", this.bean.getOptimisticColumn(), otherTyped.getOptimisticColumn(), false);
            this.computeDiff("TableName", this.bean.getTableName(), otherTyped.getTableName(), false);
            this.computeDiff("VerifyColumns", this.bean.getVerifyColumns(), otherTyped.getVerifyColumns(), false);
            this.computeDiff("VerifyRows", this.bean.getVerifyRows(), otherTyped.getVerifyRows(), false);
            this.computeDiff("VersionColumnInitialValue", this.bean.getVersionColumnInitialValue(), otherTyped.getVersionColumnInitialValue(), false);
            this.computeDiff("TriggerUpdatesOptimisticColumn", this.bean.isTriggerUpdatesOptimisticColumn(), otherTyped.isTriggerUpdatesOptimisticColumn(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TableMapBeanImpl original = (TableMapBeanImpl)event.getSourceBean();
            TableMapBeanImpl proposed = (TableMapBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FieldMaps")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFieldMap((FieldMapBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFieldMap((FieldMapBean)update.getRemovedObject());
                  }

                  if (original.getFieldMaps() == null || original.getFieldMaps().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("OptimisticColumn")) {
                  original.setOptimisticColumn(proposed.getOptimisticColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TableName")) {
                  original.setTableName(proposed.getTableName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("VerifyColumns")) {
                  original.setVerifyColumns(proposed.getVerifyColumns());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("VerifyRows")) {
                  original.setVerifyRows(proposed.getVerifyRows());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("VersionColumnInitialValue")) {
                  original.setVersionColumnInitialValue(proposed.getVersionColumnInitialValue());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("TriggerUpdatesOptimisticColumn")) {
                  original.setTriggerUpdatesOptimisticColumn(proposed.isTriggerUpdatesOptimisticColumn());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            TableMapBeanImpl copy = (TableMapBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FieldMaps")) && this.bean.isFieldMapsSet() && !copy._isSet(1)) {
               FieldMapBean[] oldFieldMaps = this.bean.getFieldMaps();
               FieldMapBean[] newFieldMaps = new FieldMapBean[oldFieldMaps.length];

               for(int i = 0; i < newFieldMaps.length; ++i) {
                  newFieldMaps[i] = (FieldMapBean)((FieldMapBean)this.createCopy((AbstractDescriptorBean)oldFieldMaps[i], includeObsolete));
               }

               copy.setFieldMaps(newFieldMaps);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("OptimisticColumn")) && this.bean.isOptimisticColumnSet()) {
               copy.setOptimisticColumn(this.bean.getOptimisticColumn());
            }

            if ((excludeProps == null || !excludeProps.contains("TableName")) && this.bean.isTableNameSet()) {
               copy.setTableName(this.bean.getTableName());
            }

            if ((excludeProps == null || !excludeProps.contains("VerifyColumns")) && this.bean.isVerifyColumnsSet()) {
               copy.setVerifyColumns(this.bean.getVerifyColumns());
            }

            if ((excludeProps == null || !excludeProps.contains("VerifyRows")) && this.bean.isVerifyRowsSet()) {
               copy.setVerifyRows(this.bean.getVerifyRows());
            }

            if ((excludeProps == null || !excludeProps.contains("VersionColumnInitialValue")) && this.bean.isVersionColumnInitialValueSet()) {
               copy.setVersionColumnInitialValue(this.bean.getVersionColumnInitialValue());
            }

            if ((excludeProps == null || !excludeProps.contains("TriggerUpdatesOptimisticColumn")) && this.bean.isTriggerUpdatesOptimisticColumnSet()) {
               copy.setTriggerUpdatesOptimisticColumn(this.bean.isTriggerUpdatesOptimisticColumn());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getFieldMaps(), clazz, annotation);
      }
   }
}

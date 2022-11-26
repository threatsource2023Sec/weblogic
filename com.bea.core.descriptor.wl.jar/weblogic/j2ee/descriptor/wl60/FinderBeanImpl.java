package weblogic.j2ee.descriptor.wl60;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class FinderBeanImpl extends AbstractDescriptorBean implements FinderBean, Serializable {
   private boolean _FindForUpdate;
   private String _FinderName;
   private String[] _FinderParams;
   private String _FinderQuery;
   private String _FinderSql;
   private String _Id;
   private static SchemaHelper2 _schemaHelper;

   public FinderBeanImpl() {
      this._initializeProperty(-1);
   }

   public FinderBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public FinderBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getFinderName() {
      return this._FinderName;
   }

   public boolean isFinderNameInherited() {
      return false;
   }

   public boolean isFinderNameSet() {
      return this._isSet(0);
   }

   public void setFinderName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FinderName;
      this._FinderName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getFinderParams() {
      return this._FinderParams;
   }

   public boolean isFinderParamsInherited() {
      return false;
   }

   public boolean isFinderParamsSet() {
      return this._isSet(1);
   }

   public void addFinderParam(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getFinderParams(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setFinderParams(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeFinderParam(String param0) {
      String[] _old = this.getFinderParams();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setFinderParams(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setFinderParams(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._FinderParams;
      this._FinderParams = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getFinderQuery() {
      return this._FinderQuery;
   }

   public boolean isFinderQueryInherited() {
      return false;
   }

   public boolean isFinderQuerySet() {
      return this._isSet(2);
   }

   public void setFinderQuery(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FinderQuery;
      this._FinderQuery = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getFinderSql() {
      return this._FinderSql;
   }

   public boolean isFinderSqlInherited() {
      return false;
   }

   public boolean isFinderSqlSet() {
      return this._isSet(3);
   }

   public void setFinderSql(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FinderSql;
      this._FinderSql = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isFindForUpdate() {
      return this._FindForUpdate;
   }

   public boolean isFindForUpdateInherited() {
      return false;
   }

   public boolean isFindForUpdateSet() {
      return this._isSet(4);
   }

   public void setFindForUpdate(boolean param0) {
      boolean _oldVal = this._FindForUpdate;
      this._FindForUpdate = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._FinderName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._FinderParams = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._FinderQuery = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._FinderSql = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._FindForUpdate = false;
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
                  return 5;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 13:
            case 14:
            default:
               break;
            case 10:
               if (s.equals("finder-sql")) {
                  return 3;
               }
               break;
            case 11:
               if (s.equals("finder-name")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("finder-param")) {
                  return 1;
               }

               if (s.equals("finder-query")) {
                  return 2;
               }
               break;
            case 15:
               if (s.equals("find-for-update")) {
                  return 4;
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
               return "finder-name";
            case 1:
               return "finder-param";
            case 2:
               return "finder-query";
            case 3:
               return "finder-sql";
            case 4:
               return "find-for-update";
            case 5:
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
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private FinderBeanImpl bean;

      protected Helper(FinderBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "FinderName";
            case 1:
               return "FinderParams";
            case 2:
               return "FinderQuery";
            case 3:
               return "FinderSql";
            case 4:
               return "FindForUpdate";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FinderName")) {
            return 0;
         } else if (propName.equals("FinderParams")) {
            return 1;
         } else if (propName.equals("FinderQuery")) {
            return 2;
         } else if (propName.equals("FinderSql")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 5;
         } else {
            return propName.equals("FindForUpdate") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isFinderNameSet()) {
               buf.append("FinderName");
               buf.append(String.valueOf(this.bean.getFinderName()));
            }

            if (this.bean.isFinderParamsSet()) {
               buf.append("FinderParams");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getFinderParams())));
            }

            if (this.bean.isFinderQuerySet()) {
               buf.append("FinderQuery");
               buf.append(String.valueOf(this.bean.getFinderQuery()));
            }

            if (this.bean.isFinderSqlSet()) {
               buf.append("FinderSql");
               buf.append(String.valueOf(this.bean.getFinderSql()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isFindForUpdateSet()) {
               buf.append("FindForUpdate");
               buf.append(String.valueOf(this.bean.isFindForUpdate()));
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
            FinderBeanImpl otherTyped = (FinderBeanImpl)other;
            this.computeDiff("FinderName", this.bean.getFinderName(), otherTyped.getFinderName(), false);
            this.computeDiff("FinderParams", this.bean.getFinderParams(), otherTyped.getFinderParams(), false);
            this.computeDiff("FinderQuery", this.bean.getFinderQuery(), otherTyped.getFinderQuery(), false);
            this.computeDiff("FinderSql", this.bean.getFinderSql(), otherTyped.getFinderSql(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("FindForUpdate", this.bean.isFindForUpdate(), otherTyped.isFindForUpdate(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FinderBeanImpl original = (FinderBeanImpl)event.getSourceBean();
            FinderBeanImpl proposed = (FinderBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FinderName")) {
                  original.setFinderName(proposed.getFinderName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("FinderParams")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addFinderParam((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFinderParam((String)update.getRemovedObject());
                  }

                  if (original.getFinderParams() == null || original.getFinderParams().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("FinderQuery")) {
                  original.setFinderQuery(proposed.getFinderQuery());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("FinderSql")) {
                  original.setFinderSql(proposed.getFinderSql());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("FindForUpdate")) {
                  original.setFindForUpdate(proposed.isFindForUpdate());
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
            FinderBeanImpl copy = (FinderBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FinderName")) && this.bean.isFinderNameSet()) {
               copy.setFinderName(this.bean.getFinderName());
            }

            if ((excludeProps == null || !excludeProps.contains("FinderParams")) && this.bean.isFinderParamsSet()) {
               Object o = this.bean.getFinderParams();
               copy.setFinderParams(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("FinderQuery")) && this.bean.isFinderQuerySet()) {
               copy.setFinderQuery(this.bean.getFinderQuery());
            }

            if ((excludeProps == null || !excludeProps.contains("FinderSql")) && this.bean.isFinderSqlSet()) {
               copy.setFinderSql(this.bean.getFinderSql());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("FindForUpdate")) && this.bean.isFindForUpdateSet()) {
               copy.setFindForUpdate(this.bean.isFindForUpdate());
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

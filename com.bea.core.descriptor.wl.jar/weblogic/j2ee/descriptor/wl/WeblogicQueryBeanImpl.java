package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
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

public class WeblogicQueryBeanImpl extends AbstractDescriptorBean implements WeblogicQueryBean, Serializable {
   private String _Description;
   private EjbQlQueryBean _EjbQlQuery;
   private boolean _EnableEagerRefresh;
   private boolean _EnableQueryCaching;
   private String _Id;
   private boolean _IncludeResultCacheHint;
   private boolean _IncludeUpdates;
   private int _MaxElements;
   private QueryMethodBean _QueryMethod;
   private SqlQueryBean _SqlQuery;
   private boolean _SqlSelectDistinct;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicQueryBeanImpl() {
      this._initializeProperty(-1);
   }

   public WeblogicQueryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WeblogicQueryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public QueryMethodBean getQueryMethod() {
      return this._QueryMethod;
   }

   public boolean isQueryMethodInherited() {
      return false;
   }

   public boolean isQueryMethodSet() {
      return this._isSet(1);
   }

   public void setQueryMethod(QueryMethodBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getQueryMethod() != null && param0 != this.getQueryMethod()) {
         throw new BeanAlreadyExistsException(this.getQueryMethod() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         QueryMethodBean _oldVal = this._QueryMethod;
         this._QueryMethod = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public QueryMethodBean createQueryMethod() {
      QueryMethodBeanImpl _val = new QueryMethodBeanImpl(this, -1);

      try {
         this.setQueryMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyQueryMethod(QueryMethodBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._QueryMethod;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setQueryMethod((QueryMethodBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public EjbQlQueryBean getEjbQlQuery() {
      return this._EjbQlQuery;
   }

   public boolean isEjbQlQueryInherited() {
      return false;
   }

   public boolean isEjbQlQuerySet() {
      return this._isSet(2);
   }

   public void setEjbQlQuery(EjbQlQueryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getEjbQlQuery() != null && param0 != this.getEjbQlQuery()) {
         throw new BeanAlreadyExistsException(this.getEjbQlQuery() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         EjbQlQueryBean _oldVal = this._EjbQlQuery;
         this._EjbQlQuery = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public EjbQlQueryBean createEjbQlQuery() {
      EjbQlQueryBeanImpl _val = new EjbQlQueryBeanImpl(this, -1);

      try {
         this.setEjbQlQuery(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEjbQlQuery(EjbQlQueryBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._EjbQlQuery;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setEjbQlQuery((EjbQlQueryBean)null);
               this._unSet(2);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public SqlQueryBean getSqlQuery() {
      return this._SqlQuery;
   }

   public boolean isSqlQueryInherited() {
      return false;
   }

   public boolean isSqlQuerySet() {
      return this._isSet(3);
   }

   public void setSqlQuery(SqlQueryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSqlQuery() != null && param0 != this.getSqlQuery()) {
         throw new BeanAlreadyExistsException(this.getSqlQuery() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SqlQueryBean _oldVal = this._SqlQuery;
         this._SqlQuery = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public SqlQueryBean createSqlQuery() {
      SqlQueryBeanImpl _val = new SqlQueryBeanImpl(this, -1);

      try {
         this.setSqlQuery(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySqlQuery(SqlQueryBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SqlQuery;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSqlQuery((SqlQueryBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public int getMaxElements() {
      return this._MaxElements;
   }

   public boolean isMaxElementsInherited() {
      return false;
   }

   public boolean isMaxElementsSet() {
      return this._isSet(4);
   }

   public void setMaxElements(int param0) {
      LegalChecks.checkMin("MaxElements", param0, 0);
      int _oldVal = this._MaxElements;
      this._MaxElements = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isIncludeUpdates() {
      return this._IncludeUpdates;
   }

   public boolean isIncludeUpdatesInherited() {
      return false;
   }

   public boolean isIncludeUpdatesSet() {
      return this._isSet(5);
   }

   public void setIncludeUpdates(boolean param0) {
      boolean _oldVal = this._IncludeUpdates;
      this._IncludeUpdates = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isSqlSelectDistinct() {
      return this._SqlSelectDistinct;
   }

   public boolean isSqlSelectDistinctInherited() {
      return false;
   }

   public boolean isSqlSelectDistinctSet() {
      return this._isSet(6);
   }

   public void setSqlSelectDistinct(boolean param0) {
      boolean _oldVal = this._SqlSelectDistinct;
      this._SqlSelectDistinct = param0;
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

   public boolean getEnableQueryCaching() {
      return this._EnableQueryCaching;
   }

   public boolean isEnableQueryCachingInherited() {
      return false;
   }

   public boolean isEnableQueryCachingSet() {
      return this._isSet(8);
   }

   public void setEnableQueryCaching(boolean param0) {
      boolean _oldVal = this._EnableQueryCaching;
      this._EnableQueryCaching = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean getEnableEagerRefresh() {
      return this._EnableEagerRefresh;
   }

   public boolean isEnableEagerRefreshInherited() {
      return false;
   }

   public boolean isEnableEagerRefreshSet() {
      return this._isSet(9);
   }

   public void setEnableEagerRefresh(boolean param0) {
      boolean _oldVal = this._EnableEagerRefresh;
      this._EnableEagerRefresh = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean isIncludeResultCacheHint() {
      return this._IncludeResultCacheHint;
   }

   public boolean isIncludeResultCacheHintInherited() {
      return false;
   }

   public boolean isIncludeResultCacheHintSet() {
      return this._isSet(10);
   }

   public void setIncludeResultCacheHint(boolean param0) {
      boolean _oldVal = this._IncludeResultCacheHint;
      this._IncludeResultCacheHint = param0;
      this._postSet(10, _oldVal, param0);
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
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._EjbQlQuery = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._EnableEagerRefresh = false;
               if (initOne) {
                  break;
               }
            case 8:
               this._EnableQueryCaching = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._MaxElements = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._QueryMethod = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._SqlQuery = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._IncludeResultCacheHint = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._IncludeUpdates = true;
               if (initOne) {
                  break;
               }
            case 6:
               this._SqlSelectDistinct = false;
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
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 21:
            case 22:
            case 23:
            case 24:
            default:
               break;
            case 9:
               if (s.equals("sql-query")) {
                  return 3;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("ejb-ql-query")) {
                  return 2;
               }

               if (s.equals("max-elements")) {
                  return 4;
               }

               if (s.equals("query-method")) {
                  return 1;
               }
               break;
            case 15:
               if (s.equals("include-updates")) {
                  return 5;
               }
               break;
            case 19:
               if (s.equals("sql-select-distinct")) {
                  return 6;
               }
               break;
            case 20:
               if (s.equals("enable-eager-refresh")) {
                  return 9;
               }

               if (s.equals("enable-query-caching")) {
                  return 8;
               }
               break;
            case 25:
               if (s.equals("include-result-cache-hint")) {
                  return 10;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new QueryMethodBeanImpl.SchemaHelper2();
            case 2:
               return new EjbQlQueryBeanImpl.SchemaHelper2();
            case 3:
               return new SqlQueryBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "query-method";
            case 2:
               return "ejb-ql-query";
            case 3:
               return "sql-query";
            case 4:
               return "max-elements";
            case 5:
               return "include-updates";
            case 6:
               return "sql-select-distinct";
            case 7:
               return "id";
            case 8:
               return "enable-query-caching";
            case 9:
               return "enable-eager-refresh";
            case 10:
               return "include-result-cache-hint";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WeblogicQueryBeanImpl bean;

      protected Helper(WeblogicQueryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "QueryMethod";
            case 2:
               return "EjbQlQuery";
            case 3:
               return "SqlQuery";
            case 4:
               return "MaxElements";
            case 5:
               return "IncludeUpdates";
            case 6:
               return "SqlSelectDistinct";
            case 7:
               return "Id";
            case 8:
               return "EnableQueryCaching";
            case 9:
               return "EnableEagerRefresh";
            case 10:
               return "IncludeResultCacheHint";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("EjbQlQuery")) {
            return 2;
         } else if (propName.equals("EnableEagerRefresh")) {
            return 9;
         } else if (propName.equals("EnableQueryCaching")) {
            return 8;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("MaxElements")) {
            return 4;
         } else if (propName.equals("QueryMethod")) {
            return 1;
         } else if (propName.equals("SqlQuery")) {
            return 3;
         } else if (propName.equals("IncludeResultCacheHint")) {
            return 10;
         } else if (propName.equals("IncludeUpdates")) {
            return 5;
         } else {
            return propName.equals("SqlSelectDistinct") ? 6 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getEjbQlQuery() != null) {
            iterators.add(new ArrayIterator(new EjbQlQueryBean[]{this.bean.getEjbQlQuery()}));
         }

         if (this.bean.getQueryMethod() != null) {
            iterators.add(new ArrayIterator(new QueryMethodBean[]{this.bean.getQueryMethod()}));
         }

         if (this.bean.getSqlQuery() != null) {
            iterators.add(new ArrayIterator(new SqlQueryBean[]{this.bean.getSqlQuery()}));
         }

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
            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            childValue = this.computeChildHashValue(this.bean.getEjbQlQuery());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEnableEagerRefreshSet()) {
               buf.append("EnableEagerRefresh");
               buf.append(String.valueOf(this.bean.getEnableEagerRefresh()));
            }

            if (this.bean.isEnableQueryCachingSet()) {
               buf.append("EnableQueryCaching");
               buf.append(String.valueOf(this.bean.getEnableQueryCaching()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isMaxElementsSet()) {
               buf.append("MaxElements");
               buf.append(String.valueOf(this.bean.getMaxElements()));
            }

            childValue = this.computeChildHashValue(this.bean.getQueryMethod());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getSqlQuery());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIncludeResultCacheHintSet()) {
               buf.append("IncludeResultCacheHint");
               buf.append(String.valueOf(this.bean.isIncludeResultCacheHint()));
            }

            if (this.bean.isIncludeUpdatesSet()) {
               buf.append("IncludeUpdates");
               buf.append(String.valueOf(this.bean.isIncludeUpdates()));
            }

            if (this.bean.isSqlSelectDistinctSet()) {
               buf.append("SqlSelectDistinct");
               buf.append(String.valueOf(this.bean.isSqlSelectDistinct()));
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
            WeblogicQueryBeanImpl otherTyped = (WeblogicQueryBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeChildDiff("EjbQlQuery", this.bean.getEjbQlQuery(), otherTyped.getEjbQlQuery(), false);
            this.computeDiff("EnableEagerRefresh", this.bean.getEnableEagerRefresh(), otherTyped.getEnableEagerRefresh(), false);
            this.computeDiff("EnableQueryCaching", this.bean.getEnableQueryCaching(), otherTyped.getEnableQueryCaching(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("MaxElements", this.bean.getMaxElements(), otherTyped.getMaxElements(), false);
            this.computeChildDiff("QueryMethod", this.bean.getQueryMethod(), otherTyped.getQueryMethod(), false);
            this.computeChildDiff("SqlQuery", this.bean.getSqlQuery(), otherTyped.getSqlQuery(), false);
            this.computeDiff("IncludeResultCacheHint", this.bean.isIncludeResultCacheHint(), otherTyped.isIncludeResultCacheHint(), false);
            this.computeDiff("IncludeUpdates", this.bean.isIncludeUpdates(), otherTyped.isIncludeUpdates(), false);
            this.computeDiff("SqlSelectDistinct", this.bean.isSqlSelectDistinct(), otherTyped.isSqlSelectDistinct(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicQueryBeanImpl original = (WeblogicQueryBeanImpl)event.getSourceBean();
            WeblogicQueryBeanImpl proposed = (WeblogicQueryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("EjbQlQuery")) {
                  if (type == 2) {
                     original.setEjbQlQuery((EjbQlQueryBean)this.createCopy((AbstractDescriptorBean)proposed.getEjbQlQuery()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("EjbQlQuery", (DescriptorBean)original.getEjbQlQuery());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("EnableEagerRefresh")) {
                  original.setEnableEagerRefresh(proposed.getEnableEagerRefresh());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("EnableQueryCaching")) {
                  original.setEnableQueryCaching(proposed.getEnableQueryCaching());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("MaxElements")) {
                  original.setMaxElements(proposed.getMaxElements());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("QueryMethod")) {
                  if (type == 2) {
                     original.setQueryMethod((QueryMethodBean)this.createCopy((AbstractDescriptorBean)proposed.getQueryMethod()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("QueryMethod", (DescriptorBean)original.getQueryMethod());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("SqlQuery")) {
                  if (type == 2) {
                     original.setSqlQuery((SqlQueryBean)this.createCopy((AbstractDescriptorBean)proposed.getSqlQuery()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SqlQuery", (DescriptorBean)original.getSqlQuery());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("IncludeResultCacheHint")) {
                  original.setIncludeResultCacheHint(proposed.isIncludeResultCacheHint());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("IncludeUpdates")) {
                  original.setIncludeUpdates(proposed.isIncludeUpdates());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("SqlSelectDistinct")) {
                  original.setSqlSelectDistinct(proposed.isSqlSelectDistinct());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            WeblogicQueryBeanImpl copy = (WeblogicQueryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbQlQuery")) && this.bean.isEjbQlQuerySet() && !copy._isSet(2)) {
               Object o = this.bean.getEjbQlQuery();
               copy.setEjbQlQuery((EjbQlQueryBean)null);
               copy.setEjbQlQuery(o == null ? null : (EjbQlQueryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("EnableEagerRefresh")) && this.bean.isEnableEagerRefreshSet()) {
               copy.setEnableEagerRefresh(this.bean.getEnableEagerRefresh());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableQueryCaching")) && this.bean.isEnableQueryCachingSet()) {
               copy.setEnableQueryCaching(this.bean.getEnableQueryCaching());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxElements")) && this.bean.isMaxElementsSet()) {
               copy.setMaxElements(this.bean.getMaxElements());
            }

            if ((excludeProps == null || !excludeProps.contains("QueryMethod")) && this.bean.isQueryMethodSet() && !copy._isSet(1)) {
               Object o = this.bean.getQueryMethod();
               copy.setQueryMethod((QueryMethodBean)null);
               copy.setQueryMethod(o == null ? null : (QueryMethodBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SqlQuery")) && this.bean.isSqlQuerySet() && !copy._isSet(3)) {
               Object o = this.bean.getSqlQuery();
               copy.setSqlQuery((SqlQueryBean)null);
               copy.setSqlQuery(o == null ? null : (SqlQueryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("IncludeResultCacheHint")) && this.bean.isIncludeResultCacheHintSet()) {
               copy.setIncludeResultCacheHint(this.bean.isIncludeResultCacheHint());
            }

            if ((excludeProps == null || !excludeProps.contains("IncludeUpdates")) && this.bean.isIncludeUpdatesSet()) {
               copy.setIncludeUpdates(this.bean.isIncludeUpdates());
            }

            if ((excludeProps == null || !excludeProps.contains("SqlSelectDistinct")) && this.bean.isSqlSelectDistinctSet()) {
               copy.setSqlSelectDistinct(this.bean.isSqlSelectDistinct());
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
         this.inferSubTree(this.bean.getEjbQlQuery(), clazz, annotation);
         this.inferSubTree(this.bean.getQueryMethod(), clazz, annotation);
         this.inferSubTree(this.bean.getSqlQuery(), clazz, annotation);
      }
   }
}

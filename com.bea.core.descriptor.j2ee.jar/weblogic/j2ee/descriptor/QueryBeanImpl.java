package weblogic.j2ee.descriptor;

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

public class QueryBeanImpl extends AbstractDescriptorBean implements QueryBean, Serializable {
   private String _Description;
   private String _EjbQl;
   private String _Id;
   private String _NamedQueryName;
   private QueryMethodBean _QueryMethod;
   private String _ResultTypeMapping;
   private static SchemaHelper2 _schemaHelper;

   public QueryBeanImpl() {
      this._initializeProperty(-1);
   }

   public QueryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public QueryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String getResultTypeMapping() {
      return this._ResultTypeMapping;
   }

   public boolean isResultTypeMappingInherited() {
      return false;
   }

   public boolean isResultTypeMappingSet() {
      return this._isSet(2);
   }

   public void setResultTypeMapping(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Remote", "Local"};
      param0 = LegalChecks.checkInEnum("ResultTypeMapping", param0, _set);
      String _oldVal = this._ResultTypeMapping;
      this._ResultTypeMapping = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getEjbQl() {
      return this._EjbQl;
   }

   public boolean isEjbQlInherited() {
      return false;
   }

   public boolean isEjbQlSet() {
      return this._isSet(3);
   }

   public void setEjbQl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbQl;
      this._EjbQl = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(4, _oldVal, param0);
   }

   public void setNamedQueryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NamedQueryName;
      this._NamedQueryName = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getNamedQueryName() {
      return this._NamedQueryName;
   }

   public boolean isNamedQueryNameInherited() {
      return false;
   }

   public boolean isNamedQueryNameSet() {
      return this._isSet(5);
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
            case 3:
               this._EjbQl = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._NamedQueryName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._QueryMethod = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ResultTypeMapping = "Local";
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
                  return 4;
               }
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            default:
               break;
            case 6:
               if (s.equals("ejb-ql")) {
                  return 3;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("query-method")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("named-query-name")) {
                  return 5;
               }
               break;
            case 19:
               if (s.equals("result-type-mapping")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new QueryMethodBeanImpl.SchemaHelper2();
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
               return "result-type-mapping";
            case 3:
               return "ejb-ql";
            case 4:
               return "id";
            case 5:
               return "named-query-name";
            default:
               return super.getElementName(propIndex);
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
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private QueryBeanImpl bean;

      protected Helper(QueryBeanImpl bean) {
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
               return "ResultTypeMapping";
            case 3:
               return "EjbQl";
            case 4:
               return "Id";
            case 5:
               return "NamedQueryName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("EjbQl")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("NamedQueryName")) {
            return 5;
         } else if (propName.equals("QueryMethod")) {
            return 1;
         } else {
            return propName.equals("ResultTypeMapping") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getQueryMethod() != null) {
            iterators.add(new ArrayIterator(new QueryMethodBean[]{this.bean.getQueryMethod()}));
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

            if (this.bean.isEjbQlSet()) {
               buf.append("EjbQl");
               buf.append(String.valueOf(this.bean.getEjbQl()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isNamedQueryNameSet()) {
               buf.append("NamedQueryName");
               buf.append(String.valueOf(this.bean.getNamedQueryName()));
            }

            childValue = this.computeChildHashValue(this.bean.getQueryMethod());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isResultTypeMappingSet()) {
               buf.append("ResultTypeMapping");
               buf.append(String.valueOf(this.bean.getResultTypeMapping()));
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
            QueryBeanImpl otherTyped = (QueryBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("EjbQl", this.bean.getEjbQl(), otherTyped.getEjbQl(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("NamedQueryName", this.bean.getNamedQueryName(), otherTyped.getNamedQueryName(), false);
            this.computeChildDiff("QueryMethod", this.bean.getQueryMethod(), otherTyped.getQueryMethod(), false);
            this.computeDiff("ResultTypeMapping", this.bean.getResultTypeMapping(), otherTyped.getResultTypeMapping(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            QueryBeanImpl original = (QueryBeanImpl)event.getSourceBean();
            QueryBeanImpl proposed = (QueryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("EjbQl")) {
                  original.setEjbQl(proposed.getEjbQl());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("NamedQueryName")) {
                  original.setNamedQueryName(proposed.getNamedQueryName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
               } else if (prop.equals("ResultTypeMapping")) {
                  original.setResultTypeMapping(proposed.getResultTypeMapping());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            QueryBeanImpl copy = (QueryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbQl")) && this.bean.isEjbQlSet()) {
               copy.setEjbQl(this.bean.getEjbQl());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("NamedQueryName")) && this.bean.isNamedQueryNameSet()) {
               copy.setNamedQueryName(this.bean.getNamedQueryName());
            }

            if ((excludeProps == null || !excludeProps.contains("QueryMethod")) && this.bean.isQueryMethodSet() && !copy._isSet(1)) {
               Object o = this.bean.getQueryMethod();
               copy.setQueryMethod((QueryMethodBean)null);
               copy.setQueryMethod(o == null ? null : (QueryMethodBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ResultTypeMapping")) && this.bean.isResultTypeMappingSet()) {
               copy.setResultTypeMapping(this.bean.getResultTypeMapping());
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
         this.inferSubTree(this.bean.getQueryMethod(), clazz, annotation);
      }
   }
}

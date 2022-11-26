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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class QueryMethodBeanImpl extends AbstractDescriptorBean implements QueryMethodBean, Serializable {
   private String _Id;
   private String _MethodName;
   private MethodParamsBean _MethodParams;
   private static SchemaHelper2 _schemaHelper;

   public QueryMethodBeanImpl() {
      this._initializeProperty(-1);
   }

   public QueryMethodBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public QueryMethodBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getMethodName() {
      return this._MethodName;
   }

   public boolean isMethodNameInherited() {
      return false;
   }

   public boolean isMethodNameSet() {
      return this._isSet(0);
   }

   public void setMethodName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MethodName;
      this._MethodName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public MethodParamsBean getMethodParams() {
      return this._MethodParams;
   }

   public boolean isMethodParamsInherited() {
      return false;
   }

   public boolean isMethodParamsSet() {
      return this._isSet(1);
   }

   public void setMethodParams(MethodParamsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMethodParams() != null && param0 != this.getMethodParams()) {
         throw new BeanAlreadyExistsException(this.getMethodParams() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MethodParamsBean _oldVal = this._MethodParams;
         this._MethodParams = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public MethodParamsBean createMethodParams() {
      MethodParamsBeanImpl _val = new MethodParamsBeanImpl(this, -1);

      try {
         this.setMethodParams(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMethodParams(MethodParamsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MethodParams;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMethodParams((MethodParamsBean)null);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(2);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._MethodName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._MethodParams = null;
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
                  return 2;
               }
               break;
            case 11:
               if (s.equals("method-name")) {
                  return 0;
               }
               break;
            case 13:
               if (s.equals("method-params")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new MethodParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "method-name";
            case 1:
               return "method-params";
            case 2:
               return "id";
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
      private QueryMethodBeanImpl bean;

      protected Helper(QueryMethodBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MethodName";
            case 1:
               return "MethodParams";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 2;
         } else if (propName.equals("MethodName")) {
            return 0;
         } else {
            return propName.equals("MethodParams") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getMethodParams() != null) {
            iterators.add(new ArrayIterator(new MethodParamsBean[]{this.bean.getMethodParams()}));
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isMethodNameSet()) {
               buf.append("MethodName");
               buf.append(String.valueOf(this.bean.getMethodName()));
            }

            childValue = this.computeChildHashValue(this.bean.getMethodParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            QueryMethodBeanImpl otherTyped = (QueryMethodBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("MethodName", this.bean.getMethodName(), otherTyped.getMethodName(), false);
            this.computeChildDiff("MethodParams", this.bean.getMethodParams(), otherTyped.getMethodParams(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            QueryMethodBeanImpl original = (QueryMethodBeanImpl)event.getSourceBean();
            QueryMethodBeanImpl proposed = (QueryMethodBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MethodName")) {
                  original.setMethodName(proposed.getMethodName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MethodParams")) {
                  if (type == 2) {
                     original.setMethodParams((MethodParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getMethodParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MethodParams", (DescriptorBean)original.getMethodParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            QueryMethodBeanImpl copy = (QueryMethodBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MethodName")) && this.bean.isMethodNameSet()) {
               copy.setMethodName(this.bean.getMethodName());
            }

            if ((excludeProps == null || !excludeProps.contains("MethodParams")) && this.bean.isMethodParamsSet() && !copy._isSet(1)) {
               Object o = this.bean.getMethodParams();
               copy.setMethodParams((MethodParamsBean)null);
               copy.setMethodParams(o == null ? null : (MethodParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getMethodParams(), clazz, annotation);
      }
   }
}

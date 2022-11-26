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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class RetryMethodsOnRollbackBeanImpl extends AbstractDescriptorBean implements RetryMethodsOnRollbackBean, Serializable {
   private String _Description;
   private String _Id;
   private MethodBean[] _Methods;
   private int _RetryCount;
   private static SchemaHelper2 _schemaHelper;

   public RetryMethodsOnRollbackBeanImpl() {
      this._initializeProperty(-1);
   }

   public RetryMethodsOnRollbackBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public RetryMethodsOnRollbackBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public int getRetryCount() {
      return this._RetryCount;
   }

   public boolean isRetryCountInherited() {
      return false;
   }

   public boolean isRetryCountSet() {
      return this._isSet(1);
   }

   public void setRetryCount(int param0) {
      int _oldVal = this._RetryCount;
      this._RetryCount = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addMethod(MethodBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         MethodBean[] _new;
         if (this._isSet(2)) {
            _new = (MethodBean[])((MethodBean[])this._getHelper()._extendArray(this.getMethods(), MethodBean.class, param0));
         } else {
            _new = new MethodBean[]{param0};
         }

         try {
            this.setMethods(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MethodBean[] getMethods() {
      return this._Methods;
   }

   public boolean isMethodsInherited() {
      return false;
   }

   public boolean isMethodsSet() {
      return this._isSet(2);
   }

   public void removeMethod(MethodBean param0) {
      this.destroyMethod(param0);
   }

   public void setMethods(MethodBean[] param0) throws InvalidAttributeValueException {
      MethodBean[] param0 = param0 == null ? new MethodBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MethodBean[] _oldVal = this._Methods;
      this._Methods = (MethodBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public MethodBean createMethod() {
      MethodBeanImpl _val = new MethodBeanImpl(this, -1);

      try {
         this.addMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMethod(MethodBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         MethodBean[] _old = this.getMethods();
         MethodBean[] _new = (MethodBean[])((MethodBean[])this._getHelper()._removeElement(_old, MethodBean.class, param0));
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
               this.setMethods(_new);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
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
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Methods = new MethodBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._RetryCount = 0;
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
                  return 3;
               }
               break;
            case 6:
               if (s.equals("method")) {
                  return 2;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("retry-count")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new MethodBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "retry-count";
            case 2:
               return "method";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private RetryMethodsOnRollbackBeanImpl bean;

      protected Helper(RetryMethodsOnRollbackBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "RetryCount";
            case 2:
               return "Methods";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("Methods")) {
            return 2;
         } else {
            return propName.equals("RetryCount") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getMethods()));
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

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getMethods().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMethods()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isRetryCountSet()) {
               buf.append("RetryCount");
               buf.append(String.valueOf(this.bean.getRetryCount()));
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
            RetryMethodsOnRollbackBeanImpl otherTyped = (RetryMethodsOnRollbackBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("Methods", this.bean.getMethods(), otherTyped.getMethods(), false);
            this.computeDiff("RetryCount", this.bean.getRetryCount(), otherTyped.getRetryCount(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            RetryMethodsOnRollbackBeanImpl original = (RetryMethodsOnRollbackBeanImpl)event.getSourceBean();
            RetryMethodsOnRollbackBeanImpl proposed = (RetryMethodsOnRollbackBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Methods")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMethod((MethodBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMethod((MethodBean)update.getRemovedObject());
                  }

                  if (original.getMethods() == null || original.getMethods().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("RetryCount")) {
                  original.setRetryCount(proposed.getRetryCount());
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
            RetryMethodsOnRollbackBeanImpl copy = (RetryMethodsOnRollbackBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Methods")) && this.bean.isMethodsSet() && !copy._isSet(2)) {
               MethodBean[] oldMethods = this.bean.getMethods();
               MethodBean[] newMethods = new MethodBean[oldMethods.length];

               for(int i = 0; i < newMethods.length; ++i) {
                  newMethods[i] = (MethodBean)((MethodBean)this.createCopy((AbstractDescriptorBean)oldMethods[i], includeObsolete));
               }

               copy.setMethods(newMethods);
            }

            if ((excludeProps == null || !excludeProps.contains("RetryCount")) && this.bean.isRetryCountSet()) {
               copy.setRetryCount(this.bean.getRetryCount());
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
         this.inferSubTree(this.bean.getMethods(), clazz, annotation);
      }
   }
}

package weblogic.j2ee.descriptor;

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

public class ApplicationExceptionBeanImpl extends AbstractDescriptorBean implements ApplicationExceptionBean, Serializable {
   private String _ExceptionClass;
   private String _Id;
   private boolean _Inherited;
   private boolean _Rollback;
   private static SchemaHelper2 _schemaHelper;

   public ApplicationExceptionBeanImpl() {
      this._initializeProperty(-1);
   }

   public ApplicationExceptionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ApplicationExceptionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getExceptionClass() {
      return this._ExceptionClass;
   }

   public boolean isExceptionClassInherited() {
      return false;
   }

   public boolean isExceptionClassSet() {
      return this._isSet(0);
   }

   public void setExceptionClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ExceptionClass;
      this._ExceptionClass = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isRollback() {
      return this._Rollback;
   }

   public boolean isRollbackInherited() {
      return false;
   }

   public boolean isRollbackSet() {
      return this._isSet(1);
   }

   public void setRollback(boolean param0) {
      boolean _oldVal = this._Rollback;
      this._Rollback = param0;
      this._postSet(1, _oldVal, param0);
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

   public boolean isInherited() {
      return this._Inherited;
   }

   public boolean isInheritedInherited() {
      return false;
   }

   public boolean isInheritedSet() {
      return this._isSet(3);
   }

   public void setInherited(boolean param0) {
      boolean _oldVal = this._Inherited;
      this._Inherited = param0;
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
               this._ExceptionClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Inherited = false;
               if (initOne) {
                  break;
               }
            case 1:
               this._Rollback = false;
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
            case 8:
               if (s.equals("rollback")) {
                  return 1;
               }
               break;
            case 9:
               if (s.equals("inherited")) {
                  return 3;
               }
               break;
            case 15:
               if (s.equals("exception-class")) {
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
               return "exception-class";
            case 1:
               return "rollback";
            case 2:
               return "id";
            case 3:
               return "inherited";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ApplicationExceptionBeanImpl bean;

      protected Helper(ApplicationExceptionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ExceptionClass";
            case 1:
               return "Rollback";
            case 2:
               return "Id";
            case 3:
               return "Inherited";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ExceptionClass")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 2;
         } else if (propName.equals("Inherited")) {
            return 3;
         } else {
            return propName.equals("Rollback") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isExceptionClassSet()) {
               buf.append("ExceptionClass");
               buf.append(String.valueOf(this.bean.getExceptionClass()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInheritedSet()) {
               buf.append("Inherited");
               buf.append(String.valueOf(this.bean.isInherited()));
            }

            if (this.bean.isRollbackSet()) {
               buf.append("Rollback");
               buf.append(String.valueOf(this.bean.isRollback()));
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
            ApplicationExceptionBeanImpl otherTyped = (ApplicationExceptionBeanImpl)other;
            this.computeDiff("ExceptionClass", this.bean.getExceptionClass(), otherTyped.getExceptionClass(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Inherited", this.bean.isInherited(), otherTyped.isInherited(), false);
            this.computeDiff("Rollback", this.bean.isRollback(), otherTyped.isRollback(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ApplicationExceptionBeanImpl original = (ApplicationExceptionBeanImpl)event.getSourceBean();
            ApplicationExceptionBeanImpl proposed = (ApplicationExceptionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ExceptionClass")) {
                  original.setExceptionClass(proposed.getExceptionClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Inherited")) {
                  original.setInherited(proposed.isInherited());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Rollback")) {
                  original.setRollback(proposed.isRollback());
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
            ApplicationExceptionBeanImpl copy = (ApplicationExceptionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ExceptionClass")) && this.bean.isExceptionClassSet()) {
               copy.setExceptionClass(this.bean.getExceptionClass());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Inherited")) && this.bean.isInheritedSet()) {
               copy.setInherited(this.bean.isInherited());
            }

            if ((excludeProps == null || !excludeProps.contains("Rollback")) && this.bean.isRollbackSet()) {
               copy.setRollback(this.bean.isRollback());
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

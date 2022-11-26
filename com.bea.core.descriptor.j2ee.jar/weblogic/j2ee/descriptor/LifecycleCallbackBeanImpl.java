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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class LifecycleCallbackBeanImpl extends SourceTrackerBeanImpl implements LifecycleCallbackBean, Serializable {
   private String _LifecycleCallbackClass;
   private String _LifecycleCallbackMethod;
   private static SchemaHelper2 _schemaHelper;

   public LifecycleCallbackBeanImpl() {
      this._initializeProperty(-1);
   }

   public LifecycleCallbackBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LifecycleCallbackBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getLifecycleCallbackClass() {
      return this._LifecycleCallbackClass;
   }

   public boolean isLifecycleCallbackClassInherited() {
      return false;
   }

   public boolean isLifecycleCallbackClassSet() {
      return this._isSet(1);
   }

   public void setLifecycleCallbackClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LifecycleCallbackClass;
      this._LifecycleCallbackClass = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getLifecycleCallbackMethod() {
      return this._LifecycleCallbackMethod;
   }

   public boolean isLifecycleCallbackMethodInherited() {
      return false;
   }

   public boolean isLifecycleCallbackMethodSet() {
      return this._isSet(2);
   }

   public void setLifecycleCallbackMethod(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LifecycleCallbackMethod;
      this._LifecycleCallbackMethod = param0;
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._LifecycleCallbackClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._LifecycleCallbackMethod = null;
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

   public static class SchemaHelper2 extends SourceTrackerBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 24:
               if (s.equals("lifecycle-callback-class")) {
                  return 1;
               }
               break;
            case 25:
               if (s.equals("lifecycle-callback-method")) {
                  return 2;
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
            case 1:
               return "lifecycle-callback-class";
            case 2:
               return "lifecycle-callback-method";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SourceTrackerBeanImpl.Helper {
      private LifecycleCallbackBeanImpl bean;

      protected Helper(LifecycleCallbackBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 1:
               return "LifecycleCallbackClass";
            case 2:
               return "LifecycleCallbackMethod";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LifecycleCallbackClass")) {
            return 1;
         } else {
            return propName.equals("LifecycleCallbackMethod") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isLifecycleCallbackClassSet()) {
               buf.append("LifecycleCallbackClass");
               buf.append(String.valueOf(this.bean.getLifecycleCallbackClass()));
            }

            if (this.bean.isLifecycleCallbackMethodSet()) {
               buf.append("LifecycleCallbackMethod");
               buf.append(String.valueOf(this.bean.getLifecycleCallbackMethod()));
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
            LifecycleCallbackBeanImpl otherTyped = (LifecycleCallbackBeanImpl)other;
            this.computeDiff("LifecycleCallbackClass", this.bean.getLifecycleCallbackClass(), otherTyped.getLifecycleCallbackClass(), false);
            this.computeDiff("LifecycleCallbackMethod", this.bean.getLifecycleCallbackMethod(), otherTyped.getLifecycleCallbackMethod(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LifecycleCallbackBeanImpl original = (LifecycleCallbackBeanImpl)event.getSourceBean();
            LifecycleCallbackBeanImpl proposed = (LifecycleCallbackBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LifecycleCallbackClass")) {
                  original.setLifecycleCallbackClass(proposed.getLifecycleCallbackClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("LifecycleCallbackMethod")) {
                  original.setLifecycleCallbackMethod(proposed.getLifecycleCallbackMethod());
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
            LifecycleCallbackBeanImpl copy = (LifecycleCallbackBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LifecycleCallbackClass")) && this.bean.isLifecycleCallbackClassSet()) {
               copy.setLifecycleCallbackClass(this.bean.getLifecycleCallbackClass());
            }

            if ((excludeProps == null || !excludeProps.contains("LifecycleCallbackMethod")) && this.bean.isLifecycleCallbackMethodSet()) {
               copy.setLifecycleCallbackMethod(this.bean.getLifecycleCallbackMethod());
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

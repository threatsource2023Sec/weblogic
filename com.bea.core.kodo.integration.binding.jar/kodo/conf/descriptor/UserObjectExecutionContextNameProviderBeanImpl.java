package kodo.conf.descriptor;

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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class UserObjectExecutionContextNameProviderBeanImpl extends ExecutionContextNameProviderBeanImpl implements UserObjectExecutionContextNameProviderBean, Serializable {
   private String _Key;
   private static SchemaHelper2 _schemaHelper;

   public UserObjectExecutionContextNameProviderBeanImpl() {
      this._initializeProperty(-1);
   }

   public UserObjectExecutionContextNameProviderBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public UserObjectExecutionContextNameProviderBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getKey() {
      return this._Key;
   }

   public boolean isKeyInherited() {
      return false;
   }

   public boolean isKeySet() {
      return this._isSet(3);
   }

   public void setKey(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Key;
      this._Key = param0;
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Key = "null";
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

   public static class SchemaHelper2 extends ExecutionContextNameProviderBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("key")) {
                  return 3;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new StackExecutionContextNameProviderBeanImpl.SchemaHelper2();
            case 1:
               return new TransactionNameExecutionContextNameProviderBeanImpl.SchemaHelper2();
            case 2:
               return new SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "key";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends ExecutionContextNameProviderBeanImpl.Helper {
      private UserObjectExecutionContextNameProviderBeanImpl bean;

      protected Helper(UserObjectExecutionContextNameProviderBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "Key";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("Key") ? 3 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getStackExecutionContextNameProvider() != null) {
            iterators.add(new ArrayIterator(new StackExecutionContextNameProviderBean[]{this.bean.getStackExecutionContextNameProvider()}));
         }

         if (this.bean.getTransactionNameExecutionContextNameProvider() != null) {
            iterators.add(new ArrayIterator(new TransactionNameExecutionContextNameProviderBean[]{this.bean.getTransactionNameExecutionContextNameProvider()}));
         }

         if (this.bean.getUserObjectExecutionContextNameProvider() != null) {
            iterators.add(new ArrayIterator(new UserObjectExecutionContextNameProviderBean[]{this.bean.getUserObjectExecutionContextNameProvider()}));
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
            if (this.bean.isKeySet()) {
               buf.append("Key");
               buf.append(String.valueOf(this.bean.getKey()));
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
            UserObjectExecutionContextNameProviderBeanImpl otherTyped = (UserObjectExecutionContextNameProviderBeanImpl)other;
            this.computeDiff("Key", this.bean.getKey(), otherTyped.getKey(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            UserObjectExecutionContextNameProviderBeanImpl original = (UserObjectExecutionContextNameProviderBeanImpl)event.getSourceBean();
            UserObjectExecutionContextNameProviderBeanImpl proposed = (UserObjectExecutionContextNameProviderBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Key")) {
                  original.setKey(proposed.getKey());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            UserObjectExecutionContextNameProviderBeanImpl copy = (UserObjectExecutionContextNameProviderBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Key")) && this.bean.isKeySet()) {
               copy.setKey(this.bean.getKey());
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

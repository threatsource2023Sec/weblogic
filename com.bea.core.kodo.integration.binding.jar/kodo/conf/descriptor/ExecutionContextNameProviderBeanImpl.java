package kodo.conf.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import kodo.conf.customizers.ExecutionContextNameProviderCustomizer;
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

public class ExecutionContextNameProviderBeanImpl extends AbstractDescriptorBean implements ExecutionContextNameProviderBean, Serializable {
   private StackExecutionContextNameProviderBean _StackExecutionContextNameProvider;
   private TransactionNameExecutionContextNameProviderBean _TransactionNameExecutionContextNameProvider;
   private UserObjectExecutionContextNameProviderBean _UserObjectExecutionContextNameProvider;
   private transient ExecutionContextNameProviderCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ExecutionContextNameProviderBeanImpl() {
      try {
         this._customizer = new ExecutionContextNameProviderCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ExecutionContextNameProviderBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ExecutionContextNameProviderCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ExecutionContextNameProviderBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ExecutionContextNameProviderCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public StackExecutionContextNameProviderBean getStackExecutionContextNameProvider() {
      return this._StackExecutionContextNameProvider;
   }

   public boolean isStackExecutionContextNameProviderInherited() {
      return false;
   }

   public boolean isStackExecutionContextNameProviderSet() {
      return this._isSet(0);
   }

   public void setStackExecutionContextNameProvider(StackExecutionContextNameProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getStackExecutionContextNameProvider() != null && param0 != this.getStackExecutionContextNameProvider()) {
         throw new BeanAlreadyExistsException(this.getStackExecutionContextNameProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         StackExecutionContextNameProviderBean _oldVal = this._StackExecutionContextNameProvider;
         this._StackExecutionContextNameProvider = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public StackExecutionContextNameProviderBean createStackExecutionContextNameProvider() {
      StackExecutionContextNameProviderBeanImpl _val = new StackExecutionContextNameProviderBeanImpl(this, -1);

      try {
         this.setStackExecutionContextNameProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyStackExecutionContextNameProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._StackExecutionContextNameProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setStackExecutionContextNameProvider((StackExecutionContextNameProviderBean)null);
               this._unSet(0);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public TransactionNameExecutionContextNameProviderBean getTransactionNameExecutionContextNameProvider() {
      return this._TransactionNameExecutionContextNameProvider;
   }

   public boolean isTransactionNameExecutionContextNameProviderInherited() {
      return false;
   }

   public boolean isTransactionNameExecutionContextNameProviderSet() {
      return this._isSet(1);
   }

   public void setTransactionNameExecutionContextNameProvider(TransactionNameExecutionContextNameProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getTransactionNameExecutionContextNameProvider() != null && param0 != this.getTransactionNameExecutionContextNameProvider()) {
         throw new BeanAlreadyExistsException(this.getTransactionNameExecutionContextNameProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         TransactionNameExecutionContextNameProviderBean _oldVal = this._TransactionNameExecutionContextNameProvider;
         this._TransactionNameExecutionContextNameProvider = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public TransactionNameExecutionContextNameProviderBean createTransactionNameExecutionContextNameProvider() {
      TransactionNameExecutionContextNameProviderBeanImpl _val = new TransactionNameExecutionContextNameProviderBeanImpl(this, -1);

      try {
         this.setTransactionNameExecutionContextNameProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTransactionNameExecutionContextNameProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._TransactionNameExecutionContextNameProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setTransactionNameExecutionContextNameProvider((TransactionNameExecutionContextNameProviderBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public UserObjectExecutionContextNameProviderBean getUserObjectExecutionContextNameProvider() {
      return this._UserObjectExecutionContextNameProvider;
   }

   public boolean isUserObjectExecutionContextNameProviderInherited() {
      return false;
   }

   public boolean isUserObjectExecutionContextNameProviderSet() {
      return this._isSet(2);
   }

   public void setUserObjectExecutionContextNameProvider(UserObjectExecutionContextNameProviderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getUserObjectExecutionContextNameProvider() != null && param0 != this.getUserObjectExecutionContextNameProvider()) {
         throw new BeanAlreadyExistsException(this.getUserObjectExecutionContextNameProvider() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         UserObjectExecutionContextNameProviderBean _oldVal = this._UserObjectExecutionContextNameProvider;
         this._UserObjectExecutionContextNameProvider = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public UserObjectExecutionContextNameProviderBean createUserObjectExecutionContextNameProvider() {
      UserObjectExecutionContextNameProviderBeanImpl _val = new UserObjectExecutionContextNameProviderBeanImpl(this, -1);

      try {
         this.setUserObjectExecutionContextNameProvider(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyUserObjectExecutionContextNameProvider() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._UserObjectExecutionContextNameProvider;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setUserObjectExecutionContextNameProvider((UserObjectExecutionContextNameProviderBean)null);
               this._unSet(2);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Class[] getExecutionContextNameProviderTypes() {
      return this._customizer.getExecutionContextNameProviderTypes();
   }

   public ExecutionContextNameProviderBean getExecutionContextNameProvider() {
      return this._customizer.getExecutionContextNameProvider();
   }

   public ExecutionContextNameProviderBean createExecutionContextNameProvider(Class param0) {
      return this._customizer.createExecutionContextNameProvider(param0);
   }

   public void destroyExecutionContextNameProvider() {
      this._customizer.destroyExecutionContextNameProvider();
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
               this._StackExecutionContextNameProvider = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._TransactionNameExecutionContextNameProvider = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._UserObjectExecutionContextNameProvider = null;
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
            case 37:
               if (s.equals("stack-execution-context-name-provider")) {
                  return 0;
               }
               break;
            case 43:
               if (s.equals("user-object-execution-context-name-provider")) {
                  return 2;
               }
               break;
            case 48:
               if (s.equals("transaction-name-execution-context-name-provider")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new StackExecutionContextNameProviderBeanImpl.SchemaHelper2();
            case 1:
               return new TransactionNameExecutionContextNameProviderBeanImpl.SchemaHelper2();
            case 2:
               return new UserObjectExecutionContextNameProviderBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "stack-execution-context-name-provider";
            case 1:
               return "transaction-name-execution-context-name-provider";
            case 2:
               return "user-object-execution-context-name-provider";
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ExecutionContextNameProviderBeanImpl bean;

      protected Helper(ExecutionContextNameProviderBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "StackExecutionContextNameProvider";
            case 1:
               return "TransactionNameExecutionContextNameProvider";
            case 2:
               return "UserObjectExecutionContextNameProvider";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("StackExecutionContextNameProvider")) {
            return 0;
         } else if (propName.equals("TransactionNameExecutionContextNameProvider")) {
            return 1;
         } else {
            return propName.equals("UserObjectExecutionContextNameProvider") ? 2 : super.getPropertyIndex(propName);
         }
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
            childValue = this.computeChildHashValue(this.bean.getStackExecutionContextNameProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTransactionNameExecutionContextNameProvider());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getUserObjectExecutionContextNameProvider());
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
            ExecutionContextNameProviderBeanImpl otherTyped = (ExecutionContextNameProviderBeanImpl)other;
            this.computeChildDiff("StackExecutionContextNameProvider", this.bean.getStackExecutionContextNameProvider(), otherTyped.getStackExecutionContextNameProvider(), false);
            this.computeChildDiff("TransactionNameExecutionContextNameProvider", this.bean.getTransactionNameExecutionContextNameProvider(), otherTyped.getTransactionNameExecutionContextNameProvider(), false);
            this.computeChildDiff("UserObjectExecutionContextNameProvider", this.bean.getUserObjectExecutionContextNameProvider(), otherTyped.getUserObjectExecutionContextNameProvider(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ExecutionContextNameProviderBeanImpl original = (ExecutionContextNameProviderBeanImpl)event.getSourceBean();
            ExecutionContextNameProviderBeanImpl proposed = (ExecutionContextNameProviderBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("StackExecutionContextNameProvider")) {
                  if (type == 2) {
                     original.setStackExecutionContextNameProvider((StackExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getStackExecutionContextNameProvider()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("StackExecutionContextNameProvider", (DescriptorBean)original.getStackExecutionContextNameProvider());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TransactionNameExecutionContextNameProvider")) {
                  if (type == 2) {
                     original.setTransactionNameExecutionContextNameProvider((TransactionNameExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getTransactionNameExecutionContextNameProvider()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TransactionNameExecutionContextNameProvider", (DescriptorBean)original.getTransactionNameExecutionContextNameProvider());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("UserObjectExecutionContextNameProvider")) {
                  if (type == 2) {
                     original.setUserObjectExecutionContextNameProvider((UserObjectExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)proposed.getUserObjectExecutionContextNameProvider()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("UserObjectExecutionContextNameProvider", (DescriptorBean)original.getUserObjectExecutionContextNameProvider());
                  }

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
            ExecutionContextNameProviderBeanImpl copy = (ExecutionContextNameProviderBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("StackExecutionContextNameProvider")) && this.bean.isStackExecutionContextNameProviderSet() && !copy._isSet(0)) {
               Object o = this.bean.getStackExecutionContextNameProvider();
               copy.setStackExecutionContextNameProvider((StackExecutionContextNameProviderBean)null);
               copy.setStackExecutionContextNameProvider(o == null ? null : (StackExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionNameExecutionContextNameProvider")) && this.bean.isTransactionNameExecutionContextNameProviderSet() && !copy._isSet(1)) {
               Object o = this.bean.getTransactionNameExecutionContextNameProvider();
               copy.setTransactionNameExecutionContextNameProvider((TransactionNameExecutionContextNameProviderBean)null);
               copy.setTransactionNameExecutionContextNameProvider(o == null ? null : (TransactionNameExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("UserObjectExecutionContextNameProvider")) && this.bean.isUserObjectExecutionContextNameProviderSet() && !copy._isSet(2)) {
               Object o = this.bean.getUserObjectExecutionContextNameProvider();
               copy.setUserObjectExecutionContextNameProvider((UserObjectExecutionContextNameProviderBean)null);
               copy.setUserObjectExecutionContextNameProvider(o == null ? null : (UserObjectExecutionContextNameProviderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getStackExecutionContextNameProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getTransactionNameExecutionContextNameProvider(), clazz, annotation);
         this.inferSubTree(this.bean.getUserObjectExecutionContextNameProvider(), clazz, annotation);
      }
   }
}

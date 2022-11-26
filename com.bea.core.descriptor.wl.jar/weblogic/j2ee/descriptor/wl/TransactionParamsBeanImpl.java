package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class TransactionParamsBeanImpl extends SettableBeanImpl implements TransactionParamsBean, Serializable {
   private long _TransactionTimeout;
   private boolean _XAConnectionFactoryEnabled;
   private static SchemaHelper2 _schemaHelper;

   public TransactionParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public TransactionParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TransactionParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public long getTransactionTimeout() {
      return this._TransactionTimeout;
   }

   public boolean isTransactionTimeoutInherited() {
      return false;
   }

   public boolean isTransactionTimeoutSet() {
      return this._isSet(0);
   }

   public void setTransactionTimeout(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("TransactionTimeout", param0, 0L, 2147483647L);
      long _oldVal = this._TransactionTimeout;
      this._TransactionTimeout = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isXAConnectionFactoryEnabled() {
      return this._XAConnectionFactoryEnabled;
   }

   public boolean isXAConnectionFactoryEnabledInherited() {
      return false;
   }

   public boolean isXAConnectionFactoryEnabledSet() {
      return this._isSet(1);
   }

   public void setXAConnectionFactoryEnabled(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._XAConnectionFactoryEnabled;
      this._XAConnectionFactoryEnabled = param0;
      this._postSet(1, _oldVal, param0);
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
               this._TransactionTimeout = 3600L;
               if (initOne) {
                  break;
               }
            case 1:
               this._XAConnectionFactoryEnabled = false;
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 19:
               if (s.equals("transaction-timeout")) {
                  return 0;
               }
               break;
            case 29:
               if (s.equals("xa-connection-factory-enabled")) {
                  return 1;
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
               return "transaction-timeout";
            case 1:
               return "xa-connection-factory-enabled";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private TransactionParamsBeanImpl bean;

      protected Helper(TransactionParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TransactionTimeout";
            case 1:
               return "XAConnectionFactoryEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("TransactionTimeout")) {
            return 0;
         } else {
            return propName.equals("XAConnectionFactoryEnabled") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isTransactionTimeoutSet()) {
               buf.append("TransactionTimeout");
               buf.append(String.valueOf(this.bean.getTransactionTimeout()));
            }

            if (this.bean.isXAConnectionFactoryEnabledSet()) {
               buf.append("XAConnectionFactoryEnabled");
               buf.append(String.valueOf(this.bean.isXAConnectionFactoryEnabled()));
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
            TransactionParamsBeanImpl otherTyped = (TransactionParamsBeanImpl)other;
            this.computeDiff("TransactionTimeout", this.bean.getTransactionTimeout(), otherTyped.getTransactionTimeout(), true);
            this.computeDiff("XAConnectionFactoryEnabled", this.bean.isXAConnectionFactoryEnabled(), otherTyped.isXAConnectionFactoryEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TransactionParamsBeanImpl original = (TransactionParamsBeanImpl)event.getSourceBean();
            TransactionParamsBeanImpl proposed = (TransactionParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("TransactionTimeout")) {
                  original.setTransactionTimeout(proposed.getTransactionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("XAConnectionFactoryEnabled")) {
                  original.setXAConnectionFactoryEnabled(proposed.isXAConnectionFactoryEnabled());
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
            TransactionParamsBeanImpl copy = (TransactionParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("TransactionTimeout")) && this.bean.isTransactionTimeoutSet()) {
               copy.setTransactionTimeout(this.bean.getTransactionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("XAConnectionFactoryEnabled")) && this.bean.isXAConnectionFactoryEnabledSet()) {
               copy.setXAConnectionFactoryEnabled(this.bean.isXAConnectionFactoryEnabled());
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

package weblogic.j2ee.descriptor.wl;

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
import weblogic.j2ee.descriptor.wl.validators.WseeConfigBeanValidator;
import weblogic.utils.collections.CombinedIterator;

public class BufferingQueueBeanImpl extends AbstractDescriptorBean implements BufferingQueueBean, Serializable {
   private String _ConnectionFactoryJndiName;
   private Boolean _Enabled;
   private String _Name;
   private Boolean _TransactionEnabled;
   private static SchemaHelper2 _schemaHelper;

   public BufferingQueueBeanImpl() {
      this._initializeProperty(-1);
   }

   public BufferingQueueBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public BufferingQueueBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateBufferQueueJndiName(param0);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public Boolean getEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(1);
   }

   public void setEnabled(Boolean param0) {
      Boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getConnectionFactoryJndiName() {
      return this._ConnectionFactoryJndiName;
   }

   public boolean isConnectionFactoryJndiNameInherited() {
      return false;
   }

   public boolean isConnectionFactoryJndiNameSet() {
      return this._isSet(2);
   }

   public void setConnectionFactoryJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactoryJndiName;
      this._ConnectionFactoryJndiName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Boolean getTransactionEnabled() {
      return this._TransactionEnabled;
   }

   public boolean isTransactionEnabledInherited() {
      return false;
   }

   public boolean isTransactionEnabledSet() {
      return this._isSet(3);
   }

   public void setTransactionEnabled(Boolean param0) {
      Boolean _oldVal = this._TransactionEnabled;
      this._TransactionEnabled = param0;
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ConnectionFactoryJndiName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Enabled = false;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._TransactionEnabled = false;
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
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 1;
               }
               break;
            case 19:
               if (s.equals("transaction-enabled")) {
                  return 3;
               }
               break;
            case 28:
               if (s.equals("connection-factory-jndi-name")) {
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
            case 0:
               return "name";
            case 1:
               return "enabled";
            case 2:
               return "connection-factory-jndi-name";
            case 3:
               return "transaction-enabled";
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
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private BufferingQueueBeanImpl bean;

      protected Helper(BufferingQueueBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "Enabled";
            case 2:
               return "ConnectionFactoryJndiName";
            case 3:
               return "TransactionEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactoryJndiName")) {
            return 2;
         } else if (propName.equals("Enabled")) {
            return 1;
         } else if (propName.equals("Name")) {
            return 0;
         } else {
            return propName.equals("TransactionEnabled") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionFactoryJndiNameSet()) {
               buf.append("ConnectionFactoryJndiName");
               buf.append(String.valueOf(this.bean.getConnectionFactoryJndiName()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.getEnabled()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isTransactionEnabledSet()) {
               buf.append("TransactionEnabled");
               buf.append(String.valueOf(this.bean.getTransactionEnabled()));
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
            BufferingQueueBeanImpl otherTyped = (BufferingQueueBeanImpl)other;
            this.computeDiff("ConnectionFactoryJndiName", this.bean.getConnectionFactoryJndiName(), otherTyped.getConnectionFactoryJndiName(), true);
            this.computeDiff("Enabled", this.bean.getEnabled(), otherTyped.getEnabled(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), true);
            this.computeDiff("TransactionEnabled", this.bean.getTransactionEnabled(), otherTyped.getTransactionEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            BufferingQueueBeanImpl original = (BufferingQueueBeanImpl)event.getSourceBean();
            BufferingQueueBeanImpl proposed = (BufferingQueueBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionFactoryJndiName")) {
                  original.setConnectionFactoryJndiName(proposed.getConnectionFactoryJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.getEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("TransactionEnabled")) {
                  original.setTransactionEnabled(proposed.getTransactionEnabled());
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
            BufferingQueueBeanImpl copy = (BufferingQueueBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryJndiName")) && this.bean.isConnectionFactoryJndiNameSet()) {
               copy.setConnectionFactoryJndiName(this.bean.getConnectionFactoryJndiName());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.getEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionEnabled")) && this.bean.isTransactionEnabledSet()) {
               copy.setTransactionEnabled(this.bean.getTransactionEnabled());
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

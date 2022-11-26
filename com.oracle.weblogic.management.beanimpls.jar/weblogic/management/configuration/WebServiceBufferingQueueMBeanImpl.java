package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.WseeConfigBeanValidator;
import weblogic.management.ManagementException;
import weblogic.utils.collections.CombinedIterator;

public class WebServiceBufferingQueueMBeanImpl extends ConfigurationMBeanImpl implements WebServiceBufferingQueueMBean, Serializable {
   private String _ConnectionFactoryJndiName;
   private Boolean _Enabled;
   private String _Name;
   private Boolean _TransactionEnabled;
   private static SchemaHelper2 _schemaHelper;

   public WebServiceBufferingQueueMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebServiceBufferingQueueMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebServiceBufferingQueueMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
      return this._isSet(2);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateBufferQueueJndiName(param0);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(10);
   }

   public void setEnabled(Boolean param0) {
      Boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getConnectionFactoryJndiName() {
      return this._ConnectionFactoryJndiName;
   }

   public boolean isConnectionFactoryJndiNameInherited() {
      return false;
   }

   public boolean isConnectionFactoryJndiNameSet() {
      return this._isSet(11);
   }

   public void setConnectionFactoryJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseeConfigBeanValidator.validateConnectionFactoryJndiName(param0);
      String _oldVal = this._ConnectionFactoryJndiName;
      this._ConnectionFactoryJndiName = param0;
      this._postSet(11, _oldVal, param0);
   }

   public Boolean isTransactionEnabled() {
      return this._TransactionEnabled;
   }

   public boolean isTransactionEnabledInherited() {
      return false;
   }

   public boolean isTransactionEnabledSet() {
      return this._isSet(12);
   }

   public void setTransactionEnabled(Boolean param0) {
      Boolean _oldVal = this._TransactionEnabled;
      this._TransactionEnabled = param0;
      this._postSet(12, _oldVal, param0);
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._ConnectionFactoryJndiName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Enabled = false;
               if (initOne) {
                  break;
               }
            case 12:
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "WebServiceBufferingQueue";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ConnectionFactoryJndiName")) {
         oldVal = this._ConnectionFactoryJndiName;
         this._ConnectionFactoryJndiName = (String)v;
         this._postSet(11, oldVal, this._ConnectionFactoryJndiName);
      } else {
         Boolean oldVal;
         if (name.equals("Enabled")) {
            oldVal = this._Enabled;
            this._Enabled = (Boolean)v;
            this._postSet(10, oldVal, this._Enabled);
         } else if (name.equals("Name")) {
            oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(2, oldVal, this._Name);
         } else if (name.equals("TransactionEnabled")) {
            oldVal = this._TransactionEnabled;
            this._TransactionEnabled = (Boolean)v;
            this._postSet(12, oldVal, this._TransactionEnabled);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ConnectionFactoryJndiName")) {
         return this._ConnectionFactoryJndiName;
      } else if (name.equals("Enabled")) {
         return this._Enabled;
      } else if (name.equals("Name")) {
         return this._Name;
      } else {
         return name.equals("TransactionEnabled") ? this._TransactionEnabled : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 10;
               }
               break;
            case 19:
               if (s.equals("transaction-enabled")) {
                  return 12;
               }
               break;
            case 28:
               if (s.equals("connection-factory-jndi-name")) {
                  return 11;
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
            case 2:
               return "name";
            case 10:
               return "enabled";
            case 11:
               return "connection-factory-jndi-name";
            case 12:
               return "transaction-enabled";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WebServiceBufferingQueueMBeanImpl bean;

      protected Helper(WebServiceBufferingQueueMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 10:
               return "Enabled";
            case 11:
               return "ConnectionFactoryJndiName";
            case 12:
               return "TransactionEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactoryJndiName")) {
            return 11;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Enabled")) {
            return 10;
         } else {
            return propName.equals("TransactionEnabled") ? 12 : super.getPropertyIndex(propName);
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

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
            }

            if (this.bean.isTransactionEnabledSet()) {
               buf.append("TransactionEnabled");
               buf.append(String.valueOf(this.bean.isTransactionEnabled()));
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
            WebServiceBufferingQueueMBeanImpl otherTyped = (WebServiceBufferingQueueMBeanImpl)other;
            this.computeDiff("ConnectionFactoryJndiName", this.bean.getConnectionFactoryJndiName(), otherTyped.getConnectionFactoryJndiName(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
            this.computeDiff("TransactionEnabled", this.bean.isTransactionEnabled(), otherTyped.isTransactionEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebServiceBufferingQueueMBeanImpl original = (WebServiceBufferingQueueMBeanImpl)event.getSourceBean();
            WebServiceBufferingQueueMBeanImpl proposed = (WebServiceBufferingQueueMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionFactoryJndiName")) {
                  original.setConnectionFactoryJndiName(proposed.getConnectionFactoryJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("TransactionEnabled")) {
                  original.setTransactionEnabled(proposed.isTransactionEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            WebServiceBufferingQueueMBeanImpl copy = (WebServiceBufferingQueueMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryJndiName")) && this.bean.isConnectionFactoryJndiNameSet()) {
               copy.setConnectionFactoryJndiName(this.bean.getConnectionFactoryJndiName());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionEnabled")) && this.bean.isTransactionEnabledSet()) {
               copy.setTransactionEnabled(this.bean.isTransactionEnabled());
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

package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class WSReliableDeliveryPolicyMBeanImpl extends ConfigurationMBeanImpl implements WSReliableDeliveryPolicyMBean, Serializable {
   private int _DefaultRetryCount;
   private int _DefaultRetryInterval;
   private int _DefaultTimeToLive;
   private JMSServerMBean _JMSServer;
   private JMSStoreMBean _Store;
   private static SchemaHelper2 _schemaHelper;

   public WSReliableDeliveryPolicyMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WSReliableDeliveryPolicyMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WSReliableDeliveryPolicyMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public JMSStoreMBean getStore() {
      return this._Store;
   }

   public String getStoreAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getStore();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isStoreInherited() {
      return false;
   }

   public boolean isStoreSet() {
      return this._isSet(10);
   }

   public void setStoreAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JMSStoreMBean.class, new ReferenceManager.Resolver(this, 10) {
            public void resolveReference(Object value) {
               try {
                  WSReliableDeliveryPolicyMBeanImpl.this.setStore((JMSStoreMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JMSStoreMBean _oldVal = this._Store;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Store);
      }

   }

   public void setStore(JMSStoreMBean param0) throws InvalidAttributeValueException {
      JMSStoreMBean _oldVal = this._Store;
      this._Store = param0;
      this._postSet(10, _oldVal, param0);
   }

   public JMSServerMBean getJMSServer() {
      return this._JMSServer;
   }

   public String getJMSServerAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getJMSServer();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isJMSServerInherited() {
      return false;
   }

   public boolean isJMSServerSet() {
      return this._isSet(11);
   }

   public void setJMSServerAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JMSServerMBean.class, new ReferenceManager.Resolver(this, 11) {
            public void resolveReference(Object value) {
               try {
                  WSReliableDeliveryPolicyMBeanImpl.this.setJMSServer((JMSServerMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JMSServerMBean _oldVal = this._JMSServer;
         this._initializeProperty(11);
         this._postSet(11, _oldVal, this._JMSServer);
      }

   }

   public void setJMSServer(JMSServerMBean param0) throws InvalidAttributeValueException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 11, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return WSReliableDeliveryPolicyMBeanImpl.this.getJMSServer();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      JMSServerMBean _oldVal = this._JMSServer;
      this._JMSServer = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getDefaultRetryCount() {
      return this._DefaultRetryCount;
   }

   public boolean isDefaultRetryCountInherited() {
      return false;
   }

   public boolean isDefaultRetryCountSet() {
      return this._isSet(12);
   }

   public void setDefaultRetryCount(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._DefaultRetryCount;
      this._DefaultRetryCount = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getDefaultRetryInterval() {
      return this._DefaultRetryInterval;
   }

   public boolean isDefaultRetryIntervalInherited() {
      return false;
   }

   public boolean isDefaultRetryIntervalSet() {
      return this._isSet(13);
   }

   public void setDefaultRetryInterval(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._DefaultRetryInterval;
      this._DefaultRetryInterval = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getDefaultTimeToLive() {
      return this._DefaultTimeToLive;
   }

   public boolean isDefaultTimeToLiveInherited() {
      return false;
   }

   public boolean isDefaultTimeToLiveSet() {
      return this._isSet(14);
   }

   public void setDefaultTimeToLive(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._DefaultTimeToLive;
      this._DefaultTimeToLive = param0;
      this._postSet(14, _oldVal, param0);
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._DefaultRetryCount = 10;
               if (initOne) {
                  break;
               }
            case 13:
               this._DefaultRetryInterval = 6;
               if (initOne) {
                  break;
               }
            case 14:
               this._DefaultTimeToLive = 360;
               if (initOne) {
                  break;
               }
            case 11:
               this._JMSServer = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Store = null;
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
      return "WSReliableDeliveryPolicy";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("DefaultRetryCount")) {
         oldVal = this._DefaultRetryCount;
         this._DefaultRetryCount = (Integer)v;
         this._postSet(12, oldVal, this._DefaultRetryCount);
      } else if (name.equals("DefaultRetryInterval")) {
         oldVal = this._DefaultRetryInterval;
         this._DefaultRetryInterval = (Integer)v;
         this._postSet(13, oldVal, this._DefaultRetryInterval);
      } else if (name.equals("DefaultTimeToLive")) {
         oldVal = this._DefaultTimeToLive;
         this._DefaultTimeToLive = (Integer)v;
         this._postSet(14, oldVal, this._DefaultTimeToLive);
      } else if (name.equals("JMSServer")) {
         JMSServerMBean oldVal = this._JMSServer;
         this._JMSServer = (JMSServerMBean)v;
         this._postSet(11, oldVal, this._JMSServer);
      } else if (name.equals("Store")) {
         JMSStoreMBean oldVal = this._Store;
         this._Store = (JMSStoreMBean)v;
         this._postSet(10, oldVal, this._Store);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("DefaultRetryCount")) {
         return new Integer(this._DefaultRetryCount);
      } else if (name.equals("DefaultRetryInterval")) {
         return new Integer(this._DefaultRetryInterval);
      } else if (name.equals("DefaultTimeToLive")) {
         return new Integer(this._DefaultTimeToLive);
      } else if (name.equals("JMSServer")) {
         return this._JMSServer;
      } else {
         return name.equals("Store") ? this._Store : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("store")) {
                  return 10;
               }
               break;
            case 10:
               if (s.equals("jms-server")) {
                  return 11;
               }
               break;
            case 19:
               if (s.equals("default-retry-count")) {
                  return 12;
               }
               break;
            case 20:
               if (s.equals("default-time-to-live")) {
                  return 14;
               }
               break;
            case 22:
               if (s.equals("default-retry-interval")) {
                  return 13;
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
            case 10:
               return "store";
            case 11:
               return "jms-server";
            case 12:
               return "default-retry-count";
            case 13:
               return "default-retry-interval";
            case 14:
               return "default-time-to-live";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WSReliableDeliveryPolicyMBeanImpl bean;

      protected Helper(WSReliableDeliveryPolicyMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Store";
            case 11:
               return "JMSServer";
            case 12:
               return "DefaultRetryCount";
            case 13:
               return "DefaultRetryInterval";
            case 14:
               return "DefaultTimeToLive";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DefaultRetryCount")) {
            return 12;
         } else if (propName.equals("DefaultRetryInterval")) {
            return 13;
         } else if (propName.equals("DefaultTimeToLive")) {
            return 14;
         } else if (propName.equals("JMSServer")) {
            return 11;
         } else {
            return propName.equals("Store") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isDefaultRetryCountSet()) {
               buf.append("DefaultRetryCount");
               buf.append(String.valueOf(this.bean.getDefaultRetryCount()));
            }

            if (this.bean.isDefaultRetryIntervalSet()) {
               buf.append("DefaultRetryInterval");
               buf.append(String.valueOf(this.bean.getDefaultRetryInterval()));
            }

            if (this.bean.isDefaultTimeToLiveSet()) {
               buf.append("DefaultTimeToLive");
               buf.append(String.valueOf(this.bean.getDefaultTimeToLive()));
            }

            if (this.bean.isJMSServerSet()) {
               buf.append("JMSServer");
               buf.append(String.valueOf(this.bean.getJMSServer()));
            }

            if (this.bean.isStoreSet()) {
               buf.append("Store");
               buf.append(String.valueOf(this.bean.getStore()));
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
            WSReliableDeliveryPolicyMBeanImpl otherTyped = (WSReliableDeliveryPolicyMBeanImpl)other;
            this.computeDiff("DefaultRetryCount", this.bean.getDefaultRetryCount(), otherTyped.getDefaultRetryCount(), false);
            this.computeDiff("DefaultRetryInterval", this.bean.getDefaultRetryInterval(), otherTyped.getDefaultRetryInterval(), false);
            this.computeDiff("DefaultTimeToLive", this.bean.getDefaultTimeToLive(), otherTyped.getDefaultTimeToLive(), false);
            this.computeDiff("JMSServer", this.bean.getJMSServer(), otherTyped.getJMSServer(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Store", this.bean.getStore(), otherTyped.getStore(), false);
            }

         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WSReliableDeliveryPolicyMBeanImpl original = (WSReliableDeliveryPolicyMBeanImpl)event.getSourceBean();
            WSReliableDeliveryPolicyMBeanImpl proposed = (WSReliableDeliveryPolicyMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DefaultRetryCount")) {
                  original.setDefaultRetryCount(proposed.getDefaultRetryCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("DefaultRetryInterval")) {
                  original.setDefaultRetryInterval(proposed.getDefaultRetryInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DefaultTimeToLive")) {
                  original.setDefaultTimeToLive(proposed.getDefaultTimeToLive());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("JMSServer")) {
                  original.setJMSServerAsString(proposed.getJMSServerAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Store")) {
                  original.setStoreAsString(proposed.getStoreAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            WSReliableDeliveryPolicyMBeanImpl copy = (WSReliableDeliveryPolicyMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DefaultRetryCount")) && this.bean.isDefaultRetryCountSet()) {
               copy.setDefaultRetryCount(this.bean.getDefaultRetryCount());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRetryInterval")) && this.bean.isDefaultRetryIntervalSet()) {
               copy.setDefaultRetryInterval(this.bean.getDefaultRetryInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultTimeToLive")) && this.bean.isDefaultTimeToLiveSet()) {
               copy.setDefaultTimeToLive(this.bean.getDefaultTimeToLive());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSServer")) && this.bean.isJMSServerSet()) {
               copy._unSet(copy, 11);
               copy.setJMSServerAsString(this.bean.getJMSServerAsString());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Store")) && this.bean.isStoreSet()) {
               copy._unSet(copy, 10);
               copy.setStoreAsString(this.bean.getStoreAsString());
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
         this.inferSubTree(this.bean.getJMSServer(), clazz, annotation);
         this.inferSubTree(this.bean.getStore(), clazz, annotation);
      }
   }
}

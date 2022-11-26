package weblogic.management.configuration;

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

public class WebserviceTokenHandlerMBeanImpl extends WebserviceSecurityConfigurationMBeanImpl implements WebserviceTokenHandlerMBean, Serializable {
   private int _HandlingOrder;
   private static SchemaHelper2 _schemaHelper;

   public WebserviceTokenHandlerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebserviceTokenHandlerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebserviceTokenHandlerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getHandlingOrder() {
      return this._HandlingOrder;
   }

   public boolean isHandlingOrderInherited() {
      return false;
   }

   public boolean isHandlingOrderSet() {
      return this._isSet(13);
   }

   public void setHandlingOrder(int param0) {
      int _oldVal = this._HandlingOrder;
      this._HandlingOrder = param0;
      this._postSet(13, _oldVal, param0);
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
         idx = 13;
      }

      try {
         switch (idx) {
            case 13:
               this._HandlingOrder = 0;
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
      return "WebserviceTokenHandler";
   }

   public void putValue(String name, Object v) {
      if (name.equals("HandlingOrder")) {
         int oldVal = this._HandlingOrder;
         this._HandlingOrder = (Integer)v;
         this._postSet(13, oldVal, this._HandlingOrder);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      return name.equals("HandlingOrder") ? new Integer(this._HandlingOrder) : super.getValue(name);
   }

   public static class SchemaHelper2 extends WebserviceSecurityConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 14:
               if (s.equals("handling-order")) {
                  return 13;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 12:
               return new ConfigurationPropertyMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 13:
               return "handling-order";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 12:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
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

   protected static class Helper extends WebserviceSecurityConfigurationMBeanImpl.Helper {
      private WebserviceTokenHandlerMBeanImpl bean;

      protected Helper(WebserviceTokenHandlerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 13:
               return "HandlingOrder";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("HandlingOrder") ? 13 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
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
            if (this.bean.isHandlingOrderSet()) {
               buf.append("HandlingOrder");
               buf.append(String.valueOf(this.bean.getHandlingOrder()));
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
            WebserviceTokenHandlerMBeanImpl otherTyped = (WebserviceTokenHandlerMBeanImpl)other;
            this.computeDiff("HandlingOrder", this.bean.getHandlingOrder(), otherTyped.getHandlingOrder(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebserviceTokenHandlerMBeanImpl original = (WebserviceTokenHandlerMBeanImpl)event.getSourceBean();
            WebserviceTokenHandlerMBeanImpl proposed = (WebserviceTokenHandlerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("HandlingOrder")) {
                  original.setHandlingOrder(proposed.getHandlingOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            WebserviceTokenHandlerMBeanImpl copy = (WebserviceTokenHandlerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("HandlingOrder")) && this.bean.isHandlingOrderSet()) {
               copy.setHandlingOrder(this.bean.getHandlingOrder());
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

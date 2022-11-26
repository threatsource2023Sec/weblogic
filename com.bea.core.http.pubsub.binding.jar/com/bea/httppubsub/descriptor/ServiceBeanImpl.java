package com.bea.httppubsub.descriptor;

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

public class ServiceBeanImpl extends AbstractDescriptorBean implements ServiceBean, Serializable {
   private String _ServiceChannel;
   private String _ServiceClass;
   private String _ServiceMethod;
   private static SchemaHelper2 _schemaHelper;

   public ServiceBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServiceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServiceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getServiceChannel() {
      return this._ServiceChannel;
   }

   public boolean isServiceChannelInherited() {
      return false;
   }

   public boolean isServiceChannelSet() {
      return this._isSet(0);
   }

   public void setServiceChannel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceChannel;
      this._ServiceChannel = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getServiceClass() {
      return this._ServiceClass;
   }

   public boolean isServiceClassInherited() {
      return false;
   }

   public boolean isServiceClassSet() {
      return this._isSet(1);
   }

   public void setServiceClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceClass;
      this._ServiceClass = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getServiceMethod() {
      return this._ServiceMethod;
   }

   public boolean isServiceMethodInherited() {
      return false;
   }

   public boolean isServiceMethodSet() {
      return this._isSet(2);
   }

   public void setServiceMethod(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceMethod;
      this._ServiceMethod = param0;
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ServiceChannel = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ServiceClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ServiceMethod = null;
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
            case 13:
               if (s.equals("service-class")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("service-method")) {
                  return 2;
               }
               break;
            case 15:
               if (s.equals("service-channel")) {
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
               return "service-channel";
            case 1:
               return "service-class";
            case 2:
               return "service-method";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServiceBeanImpl bean;

      protected Helper(ServiceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServiceChannel";
            case 1:
               return "ServiceClass";
            case 2:
               return "ServiceMethod";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ServiceChannel")) {
            return 0;
         } else if (propName.equals("ServiceClass")) {
            return 1;
         } else {
            return propName.equals("ServiceMethod") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isServiceChannelSet()) {
               buf.append("ServiceChannel");
               buf.append(String.valueOf(this.bean.getServiceChannel()));
            }

            if (this.bean.isServiceClassSet()) {
               buf.append("ServiceClass");
               buf.append(String.valueOf(this.bean.getServiceClass()));
            }

            if (this.bean.isServiceMethodSet()) {
               buf.append("ServiceMethod");
               buf.append(String.valueOf(this.bean.getServiceMethod()));
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
            ServiceBeanImpl otherTyped = (ServiceBeanImpl)other;
            this.computeDiff("ServiceChannel", this.bean.getServiceChannel(), otherTyped.getServiceChannel(), false);
            this.computeDiff("ServiceClass", this.bean.getServiceClass(), otherTyped.getServiceClass(), false);
            this.computeDiff("ServiceMethod", this.bean.getServiceMethod(), otherTyped.getServiceMethod(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServiceBeanImpl original = (ServiceBeanImpl)event.getSourceBean();
            ServiceBeanImpl proposed = (ServiceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ServiceChannel")) {
                  original.setServiceChannel(proposed.getServiceChannel());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ServiceClass")) {
                  original.setServiceClass(proposed.getServiceClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ServiceMethod")) {
                  original.setServiceMethod(proposed.getServiceMethod());
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
            ServiceBeanImpl copy = (ServiceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ServiceChannel")) && this.bean.isServiceChannelSet()) {
               copy.setServiceChannel(this.bean.getServiceChannel());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceClass")) && this.bean.isServiceClassSet()) {
               copy.setServiceClass(this.bean.getServiceClass());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceMethod")) && this.bean.isServiceMethodSet()) {
               copy.setServiceMethod(this.bean.getServiceMethod());
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

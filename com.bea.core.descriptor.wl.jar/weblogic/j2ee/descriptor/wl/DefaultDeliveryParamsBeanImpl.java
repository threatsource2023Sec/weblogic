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

public class DefaultDeliveryParamsBeanImpl extends SettableBeanImpl implements DefaultDeliveryParamsBean, Serializable {
   private int _DefaultCompressionThreshold;
   private String _DefaultDeliveryMode;
   private int _DefaultPriority;
   private long _DefaultRedeliveryDelay;
   private String _DefaultTimeToDeliver;
   private long _DefaultTimeToLive;
   private String _DefaultUnitOfOrder;
   private long _SendTimeout;
   private static SchemaHelper2 _schemaHelper;

   public DefaultDeliveryParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public DefaultDeliveryParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DefaultDeliveryParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDefaultDeliveryMode() {
      return this._DefaultDeliveryMode;
   }

   public boolean isDefaultDeliveryModeInherited() {
      return false;
   }

   public boolean isDefaultDeliveryModeSet() {
      return this._isSet(0);
   }

   public void setDefaultDeliveryMode(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Persistent", "Non-Persistent"};
      param0 = LegalChecks.checkInEnum("DefaultDeliveryMode", param0, _set);
      String _oldVal = this._DefaultDeliveryMode;
      this._DefaultDeliveryMode = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDefaultTimeToDeliver() {
      return this._DefaultTimeToDeliver;
   }

   public boolean isDefaultTimeToDeliverInherited() {
      return false;
   }

   public boolean isDefaultTimeToDeliverSet() {
      return this._isSet(1);
   }

   public void setDefaultTimeToDeliver(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DefaultTimeToDeliver;
      this._DefaultTimeToDeliver = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getDefaultTimeToLive() {
      return this._DefaultTimeToLive;
   }

   public boolean isDefaultTimeToLiveInherited() {
      return false;
   }

   public boolean isDefaultTimeToLiveSet() {
      return this._isSet(2);
   }

   public void setDefaultTimeToLive(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("DefaultTimeToLive", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._DefaultTimeToLive;
      this._DefaultTimeToLive = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getDefaultPriority() {
      return this._DefaultPriority;
   }

   public boolean isDefaultPriorityInherited() {
      return false;
   }

   public boolean isDefaultPrioritySet() {
      return this._isSet(3);
   }

   public void setDefaultPriority(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("DefaultPriority", (long)param0, 0L, 9L);
      int _oldVal = this._DefaultPriority;
      this._DefaultPriority = param0;
      this._postSet(3, _oldVal, param0);
   }

   public long getDefaultRedeliveryDelay() {
      return this._DefaultRedeliveryDelay;
   }

   public boolean isDefaultRedeliveryDelayInherited() {
      return false;
   }

   public boolean isDefaultRedeliveryDelaySet() {
      return this._isSet(4);
   }

   public void setDefaultRedeliveryDelay(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("DefaultRedeliveryDelay", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._DefaultRedeliveryDelay;
      this._DefaultRedeliveryDelay = param0;
      this._postSet(4, _oldVal, param0);
   }

   public long getSendTimeout() {
      return this._SendTimeout;
   }

   public boolean isSendTimeoutInherited() {
      return false;
   }

   public boolean isSendTimeoutSet() {
      return this._isSet(5);
   }

   public void setSendTimeout(long param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("SendTimeout", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._SendTimeout;
      this._SendTimeout = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getDefaultCompressionThreshold() {
      return this._DefaultCompressionThreshold;
   }

   public boolean isDefaultCompressionThresholdInherited() {
      return false;
   }

   public boolean isDefaultCompressionThresholdSet() {
      return this._isSet(6);
   }

   public void setDefaultCompressionThreshold(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("DefaultCompressionThreshold", (long)param0, 0L, 2147483647L);
      int _oldVal = this._DefaultCompressionThreshold;
      this._DefaultCompressionThreshold = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getDefaultUnitOfOrder() {
      return this._DefaultUnitOfOrder;
   }

   public boolean isDefaultUnitOfOrderInherited() {
      return false;
   }

   public boolean isDefaultUnitOfOrderSet() {
      return this._isSet(7);
   }

   public void setDefaultUnitOfOrder(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DefaultUnitOfOrder;
      this._DefaultUnitOfOrder = param0;
      this._postSet(7, _oldVal, param0);
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
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._DefaultCompressionThreshold = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 0:
               this._DefaultDeliveryMode = "Persistent";
               if (initOne) {
                  break;
               }
            case 3:
               this._DefaultPriority = 4;
               if (initOne) {
                  break;
               }
            case 4:
               this._DefaultRedeliveryDelay = 0L;
               if (initOne) {
                  break;
               }
            case 1:
               this._DefaultTimeToDeliver = "0";
               if (initOne) {
                  break;
               }
            case 2:
               this._DefaultTimeToLive = 0L;
               if (initOne) {
                  break;
               }
            case 7:
               this._DefaultUnitOfOrder = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._SendTimeout = 10L;
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
            case 12:
               if (s.equals("send-timeout")) {
                  return 5;
               }
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            case 19:
            case 22:
            case 25:
            case 26:
            case 27:
            case 28:
            default:
               break;
            case 16:
               if (s.equals("default-priority")) {
                  return 3;
               }
               break;
            case 20:
               if (s.equals("default-time-to-live")) {
                  return 2;
               }
               break;
            case 21:
               if (s.equals("default-delivery-mode")) {
                  return 0;
               }

               if (s.equals("default-unit-of-order")) {
                  return 7;
               }
               break;
            case 23:
               if (s.equals("default-time-to-deliver")) {
                  return 1;
               }
               break;
            case 24:
               if (s.equals("default-redelivery-delay")) {
                  return 4;
               }
               break;
            case 29:
               if (s.equals("default-compression-threshold")) {
                  return 6;
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
               return "default-delivery-mode";
            case 1:
               return "default-time-to-deliver";
            case 2:
               return "default-time-to-live";
            case 3:
               return "default-priority";
            case 4:
               return "default-redelivery-delay";
            case 5:
               return "send-timeout";
            case 6:
               return "default-compression-threshold";
            case 7:
               return "default-unit-of-order";
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
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private DefaultDeliveryParamsBeanImpl bean;

      protected Helper(DefaultDeliveryParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DefaultDeliveryMode";
            case 1:
               return "DefaultTimeToDeliver";
            case 2:
               return "DefaultTimeToLive";
            case 3:
               return "DefaultPriority";
            case 4:
               return "DefaultRedeliveryDelay";
            case 5:
               return "SendTimeout";
            case 6:
               return "DefaultCompressionThreshold";
            case 7:
               return "DefaultUnitOfOrder";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DefaultCompressionThreshold")) {
            return 6;
         } else if (propName.equals("DefaultDeliveryMode")) {
            return 0;
         } else if (propName.equals("DefaultPriority")) {
            return 3;
         } else if (propName.equals("DefaultRedeliveryDelay")) {
            return 4;
         } else if (propName.equals("DefaultTimeToDeliver")) {
            return 1;
         } else if (propName.equals("DefaultTimeToLive")) {
            return 2;
         } else if (propName.equals("DefaultUnitOfOrder")) {
            return 7;
         } else {
            return propName.equals("SendTimeout") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isDefaultCompressionThresholdSet()) {
               buf.append("DefaultCompressionThreshold");
               buf.append(String.valueOf(this.bean.getDefaultCompressionThreshold()));
            }

            if (this.bean.isDefaultDeliveryModeSet()) {
               buf.append("DefaultDeliveryMode");
               buf.append(String.valueOf(this.bean.getDefaultDeliveryMode()));
            }

            if (this.bean.isDefaultPrioritySet()) {
               buf.append("DefaultPriority");
               buf.append(String.valueOf(this.bean.getDefaultPriority()));
            }

            if (this.bean.isDefaultRedeliveryDelaySet()) {
               buf.append("DefaultRedeliveryDelay");
               buf.append(String.valueOf(this.bean.getDefaultRedeliveryDelay()));
            }

            if (this.bean.isDefaultTimeToDeliverSet()) {
               buf.append("DefaultTimeToDeliver");
               buf.append(String.valueOf(this.bean.getDefaultTimeToDeliver()));
            }

            if (this.bean.isDefaultTimeToLiveSet()) {
               buf.append("DefaultTimeToLive");
               buf.append(String.valueOf(this.bean.getDefaultTimeToLive()));
            }

            if (this.bean.isDefaultUnitOfOrderSet()) {
               buf.append("DefaultUnitOfOrder");
               buf.append(String.valueOf(this.bean.getDefaultUnitOfOrder()));
            }

            if (this.bean.isSendTimeoutSet()) {
               buf.append("SendTimeout");
               buf.append(String.valueOf(this.bean.getSendTimeout()));
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
            DefaultDeliveryParamsBeanImpl otherTyped = (DefaultDeliveryParamsBeanImpl)other;
            this.computeDiff("DefaultCompressionThreshold", this.bean.getDefaultCompressionThreshold(), otherTyped.getDefaultCompressionThreshold(), true);
            this.computeDiff("DefaultDeliveryMode", this.bean.getDefaultDeliveryMode(), otherTyped.getDefaultDeliveryMode(), true);
            this.computeDiff("DefaultPriority", this.bean.getDefaultPriority(), otherTyped.getDefaultPriority(), true);
            this.computeDiff("DefaultRedeliveryDelay", this.bean.getDefaultRedeliveryDelay(), otherTyped.getDefaultRedeliveryDelay(), true);
            this.computeDiff("DefaultTimeToDeliver", this.bean.getDefaultTimeToDeliver(), otherTyped.getDefaultTimeToDeliver(), true);
            this.computeDiff("DefaultTimeToLive", this.bean.getDefaultTimeToLive(), otherTyped.getDefaultTimeToLive(), true);
            this.computeDiff("DefaultUnitOfOrder", this.bean.getDefaultUnitOfOrder(), otherTyped.getDefaultUnitOfOrder(), false);
            this.computeDiff("SendTimeout", this.bean.getSendTimeout(), otherTyped.getSendTimeout(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DefaultDeliveryParamsBeanImpl original = (DefaultDeliveryParamsBeanImpl)event.getSourceBean();
            DefaultDeliveryParamsBeanImpl proposed = (DefaultDeliveryParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DefaultCompressionThreshold")) {
                  original.setDefaultCompressionThreshold(proposed.getDefaultCompressionThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("DefaultDeliveryMode")) {
                  original.setDefaultDeliveryMode(proposed.getDefaultDeliveryMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("DefaultPriority")) {
                  original.setDefaultPriority(proposed.getDefaultPriority());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("DefaultRedeliveryDelay")) {
                  original.setDefaultRedeliveryDelay(proposed.getDefaultRedeliveryDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("DefaultTimeToDeliver")) {
                  original.setDefaultTimeToDeliver(proposed.getDefaultTimeToDeliver());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DefaultTimeToLive")) {
                  original.setDefaultTimeToLive(proposed.getDefaultTimeToLive());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("DefaultUnitOfOrder")) {
                  original.setDefaultUnitOfOrder(proposed.getDefaultUnitOfOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("SendTimeout")) {
                  original.setSendTimeout(proposed.getSendTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            DefaultDeliveryParamsBeanImpl copy = (DefaultDeliveryParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DefaultCompressionThreshold")) && this.bean.isDefaultCompressionThresholdSet()) {
               copy.setDefaultCompressionThreshold(this.bean.getDefaultCompressionThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultDeliveryMode")) && this.bean.isDefaultDeliveryModeSet()) {
               copy.setDefaultDeliveryMode(this.bean.getDefaultDeliveryMode());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultPriority")) && this.bean.isDefaultPrioritySet()) {
               copy.setDefaultPriority(this.bean.getDefaultPriority());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultRedeliveryDelay")) && this.bean.isDefaultRedeliveryDelaySet()) {
               copy.setDefaultRedeliveryDelay(this.bean.getDefaultRedeliveryDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultTimeToDeliver")) && this.bean.isDefaultTimeToDeliverSet()) {
               copy.setDefaultTimeToDeliver(this.bean.getDefaultTimeToDeliver());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultTimeToLive")) && this.bean.isDefaultTimeToLiveSet()) {
               copy.setDefaultTimeToLive(this.bean.getDefaultTimeToLive());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultUnitOfOrder")) && this.bean.isDefaultUnitOfOrderSet()) {
               copy.setDefaultUnitOfOrder(this.bean.getDefaultUnitOfOrder());
            }

            if ((excludeProps == null || !excludeProps.contains("SendTimeout")) && this.bean.isSendTimeoutSet()) {
               copy.setSendTimeout(this.bean.getSendTimeout());
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

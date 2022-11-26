package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.jms.module.validators.JMSModuleValidator;
import weblogic.management.DistributedManagementException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JMSDestCommonMBeanImpl extends ConfigurationMBeanImpl implements JMSDestCommonMBean, Serializable {
   private long _BytesMaximum;
   private long _BytesThresholdHigh;
   private long _BytesThresholdLow;
   private long _CreationTime;
   private String _DeliveryModeOverride;
   private JMSDestinationKeyMBean[] _DestinationKeys;
   private JMSDestinationMBean _ErrorDestination;
   private String _ExpirationLoggingPolicy;
   private String _ExpirationPolicy;
   private int _MaximumMessageSize;
   private long _MessagesMaximum;
   private long _MessagesThresholdHigh;
   private long _MessagesThresholdLow;
   private int _PriorityOverride;
   private long _RedeliveryDelayOverride;
   private int _RedeliveryLimit;
   private String _TimeToDeliverOverride;
   private long _TimeToLiveOverride;
   private static SchemaHelper2 _schemaHelper;

   public JMSDestCommonMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JMSDestCommonMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JMSDestCommonMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public JMSDestinationKeyMBean[] getDestinationKeys() {
      return this._DestinationKeys;
   }

   public String getDestinationKeysAsString() {
      return this._getHelper()._serializeKeyList(this.getDestinationKeys());
   }

   public boolean isDestinationKeysInherited() {
      return false;
   }

   public boolean isDestinationKeysSet() {
      return this._isSet(10);
   }

   public void setDestinationKeysAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._DestinationKeys);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, JMSDestinationKeyMBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        JMSDestCommonMBeanImpl.this.addDestinationKey((JMSDestinationKeyMBean)value);
                        JMSDestCommonMBeanImpl.this._getHelper().reorderArrayObjects((Object[])JMSDestCommonMBeanImpl.this._DestinationKeys, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               JMSDestinationKeyMBean[] var6 = this._DestinationKeys;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  JMSDestinationKeyMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeDestinationKey(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         JMSDestinationKeyMBean[] _oldVal = this._DestinationKeys;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._DestinationKeys);
      }
   }

   public boolean addDestinationKey(JMSDestinationKeyMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         JMSDestinationKeyMBean[] _new;
         if (this._isSet(10)) {
            _new = (JMSDestinationKeyMBean[])((JMSDestinationKeyMBean[])this._getHelper()._extendArray(this.getDestinationKeys(), JMSDestinationKeyMBean.class, param0));
         } else {
            _new = new JMSDestinationKeyMBean[]{param0};
         }

         try {
            this.setDestinationKeys(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public void setDestinationKeys(JMSDestinationKeyMBean[] param0) throws InvalidAttributeValueException {
      JMSDestinationKeyMBean[] param0 = param0 == null ? new JMSDestinationKeyMBeanImpl[0] : param0;
      JMSDestinationKeyMBean[] _oldVal = this._DestinationKeys;
      this._DestinationKeys = (JMSDestinationKeyMBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean removeDestinationKey(JMSDestinationKeyMBean param0) {
      JMSDestinationKeyMBean[] _old = this.getDestinationKeys();
      JMSDestinationKeyMBean[] _new = (JMSDestinationKeyMBean[])((JMSDestinationKeyMBean[])this._getHelper()._removeElement(_old, JMSDestinationKeyMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDestinationKeys(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public long getBytesMaximum() {
      return this._BytesMaximum;
   }

   public boolean isBytesMaximumInherited() {
      return false;
   }

   public boolean isBytesMaximumSet() {
      return this._isSet(11);
   }

   public void setBytesMaximum(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("BytesMaximum", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._BytesMaximum;
      this._BytesMaximum = param0;
      this._postSet(11, _oldVal, param0);
   }

   public long getBytesThresholdHigh() {
      return this._BytesThresholdHigh;
   }

   public boolean isBytesThresholdHighInherited() {
      return false;
   }

   public boolean isBytesThresholdHighSet() {
      return this._isSet(12);
   }

   public void setBytesThresholdHigh(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("BytesThresholdHigh", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._BytesThresholdHigh;
      this._BytesThresholdHigh = param0;
      this._postSet(12, _oldVal, param0);
   }

   public long getBytesThresholdLow() {
      return this._BytesThresholdLow;
   }

   public boolean isBytesThresholdLowInherited() {
      return false;
   }

   public boolean isBytesThresholdLowSet() {
      return this._isSet(13);
   }

   public void setBytesThresholdLow(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("BytesThresholdLow", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._BytesThresholdLow;
      this._BytesThresholdLow = param0;
      this._postSet(13, _oldVal, param0);
   }

   public long getMessagesMaximum() {
      return this._MessagesMaximum;
   }

   public boolean isMessagesMaximumInherited() {
      return false;
   }

   public boolean isMessagesMaximumSet() {
      return this._isSet(14);
   }

   public void setMessagesMaximum(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MessagesMaximum", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._MessagesMaximum;
      this._MessagesMaximum = param0;
      this._postSet(14, _oldVal, param0);
   }

   public long getMessagesThresholdHigh() {
      return this._MessagesThresholdHigh;
   }

   public boolean isMessagesThresholdHighInherited() {
      return false;
   }

   public boolean isMessagesThresholdHighSet() {
      return this._isSet(15);
   }

   public void setMessagesThresholdHigh(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MessagesThresholdHigh", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._MessagesThresholdHigh;
      this._MessagesThresholdHigh = param0;
      this._postSet(15, _oldVal, param0);
   }

   public long getMessagesThresholdLow() {
      return this._MessagesThresholdLow;
   }

   public boolean isMessagesThresholdLowInherited() {
      return false;
   }

   public boolean isMessagesThresholdLowSet() {
      return this._isSet(16);
   }

   public void setMessagesThresholdLow(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MessagesThresholdLow", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._MessagesThresholdLow;
      this._MessagesThresholdLow = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getPriorityOverride() {
      return this._PriorityOverride;
   }

   public boolean isPriorityOverrideInherited() {
      return false;
   }

   public boolean isPriorityOverrideSet() {
      return this._isSet(17);
   }

   public void setPriorityOverride(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("PriorityOverride", (long)param0, -1L, 9L);
      int _oldVal = this._PriorityOverride;
      this._PriorityOverride = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getTimeToDeliverOverride() {
      return this._TimeToDeliverOverride;
   }

   public boolean isTimeToDeliverOverrideInherited() {
      return false;
   }

   public boolean isTimeToDeliverOverrideSet() {
      return this._isSet(18);
   }

   public void setTimeToDeliverOverride(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      JMSModuleValidator.validateTimeToDeliverOverride(param0);
      String _oldVal = this._TimeToDeliverOverride;
      this._TimeToDeliverOverride = param0;
      this._postSet(18, _oldVal, param0);
   }

   public long getRedeliveryDelayOverride() {
      return this._RedeliveryDelayOverride;
   }

   public boolean isRedeliveryDelayOverrideInherited() {
      return false;
   }

   public boolean isRedeliveryDelayOverrideSet() {
      return this._isSet(19);
   }

   public void setRedeliveryDelayOverride(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("RedeliveryDelayOverride", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._RedeliveryDelayOverride;
      this._RedeliveryDelayOverride = param0;
      this._postSet(19, _oldVal, param0);
   }

   public void setErrorDestination(JMSDestinationMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      JMSDestinationMBean _oldVal = this._ErrorDestination;
      this._ErrorDestination = param0;
      this._postSet(20, _oldVal, param0);
   }

   public JMSDestinationMBean getErrorDestination() {
      return this._ErrorDestination;
   }

   public String getErrorDestinationAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getErrorDestination();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isErrorDestinationInherited() {
      return false;
   }

   public boolean isErrorDestinationSet() {
      return this._isSet(20);
   }

   public void setErrorDestinationAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JMSDestinationMBean.class, new ReferenceManager.Resolver(this, 20) {
            public void resolveReference(Object value) {
               try {
                  JMSDestCommonMBeanImpl.this.setErrorDestination((JMSDestinationMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JMSDestinationMBean _oldVal = this._ErrorDestination;
         this._initializeProperty(20);
         this._postSet(20, _oldVal, this._ErrorDestination);
      }

   }

   public int getRedeliveryLimit() {
      return this._RedeliveryLimit;
   }

   public boolean isRedeliveryLimitInherited() {
      return false;
   }

   public boolean isRedeliveryLimitSet() {
      return this._isSet(21);
   }

   public void setRedeliveryLimit(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("RedeliveryLimit", (long)param0, -1L, 2147483647L);
      int _oldVal = this._RedeliveryLimit;
      this._RedeliveryLimit = param0;
      this._postSet(21, _oldVal, param0);
   }

   public long getTimeToLiveOverride() {
      return this._TimeToLiveOverride;
   }

   public boolean isTimeToLiveOverrideInherited() {
      return false;
   }

   public boolean isTimeToLiveOverrideSet() {
      return this._isSet(22);
   }

   public void setTimeToLiveOverride(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("TimeToLiveOverride", param0, -1L, Long.MAX_VALUE);
      long _oldVal = this._TimeToLiveOverride;
      this._TimeToLiveOverride = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getDeliveryModeOverride() {
      return this._DeliveryModeOverride;
   }

   public boolean isDeliveryModeOverrideInherited() {
      return false;
   }

   public boolean isDeliveryModeOverrideSet() {
      return this._isSet(23);
   }

   public void setDeliveryModeOverride(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Persistent", "Non-Persistent", "No-Delivery"};
      param0 = LegalChecks.checkInEnum("DeliveryModeOverride", param0, _set);
      String _oldVal = this._DeliveryModeOverride;
      this._DeliveryModeOverride = param0;
      this._postSet(23, _oldVal, param0);
   }

   public void setExpirationPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Discard", "Log", "Redirect"};
      param0 = LegalChecks.checkInEnum("ExpirationPolicy", param0, _set);
      String _oldVal = this._ExpirationPolicy;
      this._ExpirationPolicy = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String getExpirationPolicy() {
      return this._ExpirationPolicy;
   }

   public boolean isExpirationPolicyInherited() {
      return false;
   }

   public boolean isExpirationPolicySet() {
      return this._isSet(24);
   }

   public void setExpirationLoggingPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ExpirationLoggingPolicy;
      this._ExpirationLoggingPolicy = param0;
      this._postSet(25, _oldVal, param0);
   }

   public String getExpirationLoggingPolicy() {
      return this._ExpirationLoggingPolicy;
   }

   public boolean isExpirationLoggingPolicyInherited() {
      return false;
   }

   public boolean isExpirationLoggingPolicySet() {
      return this._isSet(25);
   }

   public int getMaximumMessageSize() {
      return this._MaximumMessageSize;
   }

   public boolean isMaximumMessageSizeInherited() {
      return false;
   }

   public boolean isMaximumMessageSizeSet() {
      return this._isSet(26);
   }

   public void setMaximumMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaximumMessageSize", (long)param0, 0L, 2147483647L);
      int _oldVal = this._MaximumMessageSize;
      this._MaximumMessageSize = param0;
      this._postSet(26, _oldVal, param0);
   }

   public long getCreationTime() {
      return this._CreationTime;
   }

   public boolean isCreationTimeInherited() {
      return false;
   }

   public boolean isCreationTimeSet() {
      return this._isSet(27);
   }

   public void setCreationTime(long param0) {
      long _oldVal = this._CreationTime;
      this._CreationTime = param0;
      this._postSet(27, _oldVal, param0);
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
               this._BytesMaximum = -1L;
               if (initOne) {
                  break;
               }
            case 12:
               this._BytesThresholdHigh = -1L;
               if (initOne) {
                  break;
               }
            case 13:
               this._BytesThresholdLow = -1L;
               if (initOne) {
                  break;
               }
            case 27:
               this._CreationTime = 1L;
               if (initOne) {
                  break;
               }
            case 23:
               this._DeliveryModeOverride = "No-Delivery";
               if (initOne) {
                  break;
               }
            case 10:
               this._DestinationKeys = new JMSDestinationKeyMBean[0];
               if (initOne) {
                  break;
               }
            case 20:
               this._ErrorDestination = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._ExpirationLoggingPolicy = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._ExpirationPolicy = "Discard";
               if (initOne) {
                  break;
               }
            case 26:
               this._MaximumMessageSize = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 14:
               this._MessagesMaximum = -1L;
               if (initOne) {
                  break;
               }
            case 15:
               this._MessagesThresholdHigh = -1L;
               if (initOne) {
                  break;
               }
            case 16:
               this._MessagesThresholdLow = -1L;
               if (initOne) {
                  break;
               }
            case 17:
               this._PriorityOverride = -1;
               if (initOne) {
                  break;
               }
            case 19:
               this._RedeliveryDelayOverride = -1L;
               if (initOne) {
                  break;
               }
            case 21:
               this._RedeliveryLimit = -1;
               if (initOne) {
                  break;
               }
            case 18:
               this._TimeToDeliverOverride = "-1";
               if (initOne) {
                  break;
               }
            case 22:
               this._TimeToLiveOverride = -1L;
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
      return "JMSDestCommon";
   }

   public void putValue(String name, Object v) {
      long oldVal;
      if (name.equals("BytesMaximum")) {
         oldVal = this._BytesMaximum;
         this._BytesMaximum = (Long)v;
         this._postSet(11, oldVal, this._BytesMaximum);
      } else if (name.equals("BytesThresholdHigh")) {
         oldVal = this._BytesThresholdHigh;
         this._BytesThresholdHigh = (Long)v;
         this._postSet(12, oldVal, this._BytesThresholdHigh);
      } else if (name.equals("BytesThresholdLow")) {
         oldVal = this._BytesThresholdLow;
         this._BytesThresholdLow = (Long)v;
         this._postSet(13, oldVal, this._BytesThresholdLow);
      } else if (name.equals("CreationTime")) {
         oldVal = this._CreationTime;
         this._CreationTime = (Long)v;
         this._postSet(27, oldVal, this._CreationTime);
      } else {
         String oldVal;
         if (name.equals("DeliveryModeOverride")) {
            oldVal = this._DeliveryModeOverride;
            this._DeliveryModeOverride = (String)v;
            this._postSet(23, oldVal, this._DeliveryModeOverride);
         } else if (name.equals("DestinationKeys")) {
            JMSDestinationKeyMBean[] oldVal = this._DestinationKeys;
            this._DestinationKeys = (JMSDestinationKeyMBean[])((JMSDestinationKeyMBean[])v);
            this._postSet(10, oldVal, this._DestinationKeys);
         } else if (name.equals("ErrorDestination")) {
            JMSDestinationMBean oldVal = this._ErrorDestination;
            this._ErrorDestination = (JMSDestinationMBean)v;
            this._postSet(20, oldVal, this._ErrorDestination);
         } else if (name.equals("ExpirationLoggingPolicy")) {
            oldVal = this._ExpirationLoggingPolicy;
            this._ExpirationLoggingPolicy = (String)v;
            this._postSet(25, oldVal, this._ExpirationLoggingPolicy);
         } else if (name.equals("ExpirationPolicy")) {
            oldVal = this._ExpirationPolicy;
            this._ExpirationPolicy = (String)v;
            this._postSet(24, oldVal, this._ExpirationPolicy);
         } else {
            int oldVal;
            if (name.equals("MaximumMessageSize")) {
               oldVal = this._MaximumMessageSize;
               this._MaximumMessageSize = (Integer)v;
               this._postSet(26, oldVal, this._MaximumMessageSize);
            } else if (name.equals("MessagesMaximum")) {
               oldVal = this._MessagesMaximum;
               this._MessagesMaximum = (Long)v;
               this._postSet(14, oldVal, this._MessagesMaximum);
            } else if (name.equals("MessagesThresholdHigh")) {
               oldVal = this._MessagesThresholdHigh;
               this._MessagesThresholdHigh = (Long)v;
               this._postSet(15, oldVal, this._MessagesThresholdHigh);
            } else if (name.equals("MessagesThresholdLow")) {
               oldVal = this._MessagesThresholdLow;
               this._MessagesThresholdLow = (Long)v;
               this._postSet(16, oldVal, this._MessagesThresholdLow);
            } else if (name.equals("PriorityOverride")) {
               oldVal = this._PriorityOverride;
               this._PriorityOverride = (Integer)v;
               this._postSet(17, oldVal, this._PriorityOverride);
            } else if (name.equals("RedeliveryDelayOverride")) {
               oldVal = this._RedeliveryDelayOverride;
               this._RedeliveryDelayOverride = (Long)v;
               this._postSet(19, oldVal, this._RedeliveryDelayOverride);
            } else if (name.equals("RedeliveryLimit")) {
               oldVal = this._RedeliveryLimit;
               this._RedeliveryLimit = (Integer)v;
               this._postSet(21, oldVal, this._RedeliveryLimit);
            } else if (name.equals("TimeToDeliverOverride")) {
               oldVal = this._TimeToDeliverOverride;
               this._TimeToDeliverOverride = (String)v;
               this._postSet(18, oldVal, this._TimeToDeliverOverride);
            } else if (name.equals("TimeToLiveOverride")) {
               oldVal = this._TimeToLiveOverride;
               this._TimeToLiveOverride = (Long)v;
               this._postSet(22, oldVal, this._TimeToLiveOverride);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("BytesMaximum")) {
         return new Long(this._BytesMaximum);
      } else if (name.equals("BytesThresholdHigh")) {
         return new Long(this._BytesThresholdHigh);
      } else if (name.equals("BytesThresholdLow")) {
         return new Long(this._BytesThresholdLow);
      } else if (name.equals("CreationTime")) {
         return new Long(this._CreationTime);
      } else if (name.equals("DeliveryModeOverride")) {
         return this._DeliveryModeOverride;
      } else if (name.equals("DestinationKeys")) {
         return this._DestinationKeys;
      } else if (name.equals("ErrorDestination")) {
         return this._ErrorDestination;
      } else if (name.equals("ExpirationLoggingPolicy")) {
         return this._ExpirationLoggingPolicy;
      } else if (name.equals("ExpirationPolicy")) {
         return this._ExpirationPolicy;
      } else if (name.equals("MaximumMessageSize")) {
         return new Integer(this._MaximumMessageSize);
      } else if (name.equals("MessagesMaximum")) {
         return new Long(this._MessagesMaximum);
      } else if (name.equals("MessagesThresholdHigh")) {
         return new Long(this._MessagesThresholdHigh);
      } else if (name.equals("MessagesThresholdLow")) {
         return new Long(this._MessagesThresholdLow);
      } else if (name.equals("PriorityOverride")) {
         return new Integer(this._PriorityOverride);
      } else if (name.equals("RedeliveryDelayOverride")) {
         return new Long(this._RedeliveryDelayOverride);
      } else if (name.equals("RedeliveryLimit")) {
         return new Integer(this._RedeliveryLimit);
      } else if (name.equals("TimeToDeliverOverride")) {
         return this._TimeToDeliverOverride;
      } else {
         return name.equals("TimeToLiveOverride") ? new Long(this._TimeToLiveOverride) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("bytes-maximum")) {
                  return 11;
               }

               if (s.equals("creation-time")) {
                  return 27;
               }
            case 14:
            case 18:
            default:
               break;
            case 15:
               if (s.equals("destination-key")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("messages-maximum")) {
                  return 14;
               }

               if (s.equals("redelivery-limit")) {
                  return 21;
               }
               break;
            case 17:
               if (s.equals("error-destination")) {
                  return 20;
               }

               if (s.equals("expiration-policy")) {
                  return 24;
               }

               if (s.equals("priority-override")) {
                  return 17;
               }
               break;
            case 19:
               if (s.equals("bytes-threshold-low")) {
                  return 13;
               }
               break;
            case 20:
               if (s.equals("bytes-threshold-high")) {
                  return 12;
               }

               if (s.equals("maximum-message-size")) {
                  return 26;
               }
               break;
            case 21:
               if (s.equals("time-to-live-override")) {
                  return 22;
               }
               break;
            case 22:
               if (s.equals("delivery-mode-override")) {
                  return 23;
               }

               if (s.equals("messages-threshold-low")) {
                  return 16;
               }
               break;
            case 23:
               if (s.equals("messages-threshold-high")) {
                  return 15;
               }
               break;
            case 24:
               if (s.equals("time-to-deliver-override")) {
                  return 18;
               }
               break;
            case 25:
               if (s.equals("expiration-logging-policy")) {
                  return 25;
               }

               if (s.equals("redelivery-delay-override")) {
                  return 19;
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
               return "destination-key";
            case 11:
               return "bytes-maximum";
            case 12:
               return "bytes-threshold-high";
            case 13:
               return "bytes-threshold-low";
            case 14:
               return "messages-maximum";
            case 15:
               return "messages-threshold-high";
            case 16:
               return "messages-threshold-low";
            case 17:
               return "priority-override";
            case 18:
               return "time-to-deliver-override";
            case 19:
               return "redelivery-delay-override";
            case 20:
               return "error-destination";
            case 21:
               return "redelivery-limit";
            case 22:
               return "time-to-live-override";
            case 23:
               return "delivery-mode-override";
            case 24:
               return "expiration-policy";
            case 25:
               return "expiration-logging-policy";
            case 26:
               return "maximum-message-size";
            case 27:
               return "creation-time";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
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
      private JMSDestCommonMBeanImpl bean;

      protected Helper(JMSDestCommonMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "DestinationKeys";
            case 11:
               return "BytesMaximum";
            case 12:
               return "BytesThresholdHigh";
            case 13:
               return "BytesThresholdLow";
            case 14:
               return "MessagesMaximum";
            case 15:
               return "MessagesThresholdHigh";
            case 16:
               return "MessagesThresholdLow";
            case 17:
               return "PriorityOverride";
            case 18:
               return "TimeToDeliverOverride";
            case 19:
               return "RedeliveryDelayOverride";
            case 20:
               return "ErrorDestination";
            case 21:
               return "RedeliveryLimit";
            case 22:
               return "TimeToLiveOverride";
            case 23:
               return "DeliveryModeOverride";
            case 24:
               return "ExpirationPolicy";
            case 25:
               return "ExpirationLoggingPolicy";
            case 26:
               return "MaximumMessageSize";
            case 27:
               return "CreationTime";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BytesMaximum")) {
            return 11;
         } else if (propName.equals("BytesThresholdHigh")) {
            return 12;
         } else if (propName.equals("BytesThresholdLow")) {
            return 13;
         } else if (propName.equals("CreationTime")) {
            return 27;
         } else if (propName.equals("DeliveryModeOverride")) {
            return 23;
         } else if (propName.equals("DestinationKeys")) {
            return 10;
         } else if (propName.equals("ErrorDestination")) {
            return 20;
         } else if (propName.equals("ExpirationLoggingPolicy")) {
            return 25;
         } else if (propName.equals("ExpirationPolicy")) {
            return 24;
         } else if (propName.equals("MaximumMessageSize")) {
            return 26;
         } else if (propName.equals("MessagesMaximum")) {
            return 14;
         } else if (propName.equals("MessagesThresholdHigh")) {
            return 15;
         } else if (propName.equals("MessagesThresholdLow")) {
            return 16;
         } else if (propName.equals("PriorityOverride")) {
            return 17;
         } else if (propName.equals("RedeliveryDelayOverride")) {
            return 19;
         } else if (propName.equals("RedeliveryLimit")) {
            return 21;
         } else if (propName.equals("TimeToDeliverOverride")) {
            return 18;
         } else {
            return propName.equals("TimeToLiveOverride") ? 22 : super.getPropertyIndex(propName);
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
            if (this.bean.isBytesMaximumSet()) {
               buf.append("BytesMaximum");
               buf.append(String.valueOf(this.bean.getBytesMaximum()));
            }

            if (this.bean.isBytesThresholdHighSet()) {
               buf.append("BytesThresholdHigh");
               buf.append(String.valueOf(this.bean.getBytesThresholdHigh()));
            }

            if (this.bean.isBytesThresholdLowSet()) {
               buf.append("BytesThresholdLow");
               buf.append(String.valueOf(this.bean.getBytesThresholdLow()));
            }

            if (this.bean.isCreationTimeSet()) {
               buf.append("CreationTime");
               buf.append(String.valueOf(this.bean.getCreationTime()));
            }

            if (this.bean.isDeliveryModeOverrideSet()) {
               buf.append("DeliveryModeOverride");
               buf.append(String.valueOf(this.bean.getDeliveryModeOverride()));
            }

            if (this.bean.isDestinationKeysSet()) {
               buf.append("DestinationKeys");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDestinationKeys())));
            }

            if (this.bean.isErrorDestinationSet()) {
               buf.append("ErrorDestination");
               buf.append(String.valueOf(this.bean.getErrorDestination()));
            }

            if (this.bean.isExpirationLoggingPolicySet()) {
               buf.append("ExpirationLoggingPolicy");
               buf.append(String.valueOf(this.bean.getExpirationLoggingPolicy()));
            }

            if (this.bean.isExpirationPolicySet()) {
               buf.append("ExpirationPolicy");
               buf.append(String.valueOf(this.bean.getExpirationPolicy()));
            }

            if (this.bean.isMaximumMessageSizeSet()) {
               buf.append("MaximumMessageSize");
               buf.append(String.valueOf(this.bean.getMaximumMessageSize()));
            }

            if (this.bean.isMessagesMaximumSet()) {
               buf.append("MessagesMaximum");
               buf.append(String.valueOf(this.bean.getMessagesMaximum()));
            }

            if (this.bean.isMessagesThresholdHighSet()) {
               buf.append("MessagesThresholdHigh");
               buf.append(String.valueOf(this.bean.getMessagesThresholdHigh()));
            }

            if (this.bean.isMessagesThresholdLowSet()) {
               buf.append("MessagesThresholdLow");
               buf.append(String.valueOf(this.bean.getMessagesThresholdLow()));
            }

            if (this.bean.isPriorityOverrideSet()) {
               buf.append("PriorityOverride");
               buf.append(String.valueOf(this.bean.getPriorityOverride()));
            }

            if (this.bean.isRedeliveryDelayOverrideSet()) {
               buf.append("RedeliveryDelayOverride");
               buf.append(String.valueOf(this.bean.getRedeliveryDelayOverride()));
            }

            if (this.bean.isRedeliveryLimitSet()) {
               buf.append("RedeliveryLimit");
               buf.append(String.valueOf(this.bean.getRedeliveryLimit()));
            }

            if (this.bean.isTimeToDeliverOverrideSet()) {
               buf.append("TimeToDeliverOverride");
               buf.append(String.valueOf(this.bean.getTimeToDeliverOverride()));
            }

            if (this.bean.isTimeToLiveOverrideSet()) {
               buf.append("TimeToLiveOverride");
               buf.append(String.valueOf(this.bean.getTimeToLiveOverride()));
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
            JMSDestCommonMBeanImpl otherTyped = (JMSDestCommonMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("BytesMaximum", this.bean.getBytesMaximum(), otherTyped.getBytesMaximum(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("BytesThresholdHigh", this.bean.getBytesThresholdHigh(), otherTyped.getBytesThresholdHigh(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("BytesThresholdLow", this.bean.getBytesThresholdLow(), otherTyped.getBytesThresholdLow(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("CreationTime", this.bean.getCreationTime(), otherTyped.getCreationTime(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DeliveryModeOverride", this.bean.getDeliveryModeOverride(), otherTyped.getDeliveryModeOverride(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DestinationKeys", this.bean.getDestinationKeys(), otherTyped.getDestinationKeys(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ErrorDestination", this.bean.getErrorDestination(), otherTyped.getErrorDestination(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ExpirationLoggingPolicy", this.bean.getExpirationLoggingPolicy(), otherTyped.getExpirationLoggingPolicy(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ExpirationPolicy", this.bean.getExpirationPolicy(), otherTyped.getExpirationPolicy(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MaximumMessageSize", this.bean.getMaximumMessageSize(), otherTyped.getMaximumMessageSize(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MessagesMaximum", this.bean.getMessagesMaximum(), otherTyped.getMessagesMaximum(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MessagesThresholdHigh", this.bean.getMessagesThresholdHigh(), otherTyped.getMessagesThresholdHigh(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MessagesThresholdLow", this.bean.getMessagesThresholdLow(), otherTyped.getMessagesThresholdLow(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("PriorityOverride", this.bean.getPriorityOverride(), otherTyped.getPriorityOverride(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("RedeliveryDelayOverride", this.bean.getRedeliveryDelayOverride(), otherTyped.getRedeliveryDelayOverride(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("RedeliveryLimit", this.bean.getRedeliveryLimit(), otherTyped.getRedeliveryLimit(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("TimeToDeliverOverride", this.bean.getTimeToDeliverOverride(), otherTyped.getTimeToDeliverOverride(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("TimeToLiveOverride", this.bean.getTimeToLiveOverride(), otherTyped.getTimeToLiveOverride(), true);
            }

         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSDestCommonMBeanImpl original = (JMSDestCommonMBeanImpl)event.getSourceBean();
            JMSDestCommonMBeanImpl proposed = (JMSDestCommonMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BytesMaximum")) {
                  original.setBytesMaximum(proposed.getBytesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("BytesThresholdHigh")) {
                  original.setBytesThresholdHigh(proposed.getBytesThresholdHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("BytesThresholdLow")) {
                  original.setBytesThresholdLow(proposed.getBytesThresholdLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("CreationTime")) {
                  original.setCreationTime(proposed.getCreationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("DeliveryModeOverride")) {
                  original.setDeliveryModeOverride(proposed.getDeliveryModeOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("DestinationKeys")) {
                  original.setDestinationKeysAsString(proposed.getDestinationKeysAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ErrorDestination")) {
                  original.setErrorDestinationAsString(proposed.getErrorDestinationAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("ExpirationLoggingPolicy")) {
                  original.setExpirationLoggingPolicy(proposed.getExpirationLoggingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("ExpirationPolicy")) {
                  original.setExpirationPolicy(proposed.getExpirationPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("MaximumMessageSize")) {
                  original.setMaximumMessageSize(proposed.getMaximumMessageSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("MessagesMaximum")) {
                  original.setMessagesMaximum(proposed.getMessagesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MessagesThresholdHigh")) {
                  original.setMessagesThresholdHigh(proposed.getMessagesThresholdHigh());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MessagesThresholdLow")) {
                  original.setMessagesThresholdLow(proposed.getMessagesThresholdLow());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("PriorityOverride")) {
                  original.setPriorityOverride(proposed.getPriorityOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("RedeliveryDelayOverride")) {
                  original.setRedeliveryDelayOverride(proposed.getRedeliveryDelayOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("RedeliveryLimit")) {
                  original.setRedeliveryLimit(proposed.getRedeliveryLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("TimeToDeliverOverride")) {
                  original.setTimeToDeliverOverride(proposed.getTimeToDeliverOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("TimeToLiveOverride")) {
                  original.setTimeToLiveOverride(proposed.getTimeToLiveOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
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
            JMSDestCommonMBeanImpl copy = (JMSDestCommonMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("BytesMaximum")) && this.bean.isBytesMaximumSet()) {
               copy.setBytesMaximum(this.bean.getBytesMaximum());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("BytesThresholdHigh")) && this.bean.isBytesThresholdHighSet()) {
               copy.setBytesThresholdHigh(this.bean.getBytesThresholdHigh());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("BytesThresholdLow")) && this.bean.isBytesThresholdLowSet()) {
               copy.setBytesThresholdLow(this.bean.getBytesThresholdLow());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("CreationTime")) && this.bean.isCreationTimeSet()) {
               copy.setCreationTime(this.bean.getCreationTime());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DeliveryModeOverride")) && this.bean.isDeliveryModeOverrideSet()) {
               copy.setDeliveryModeOverride(this.bean.getDeliveryModeOverride());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DestinationKeys")) && this.bean.isDestinationKeysSet()) {
               copy._unSet(copy, 10);
               copy.setDestinationKeysAsString(this.bean.getDestinationKeysAsString());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ErrorDestination")) && this.bean.isErrorDestinationSet()) {
               copy._unSet(copy, 20);
               copy.setErrorDestinationAsString(this.bean.getErrorDestinationAsString());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ExpirationLoggingPolicy")) && this.bean.isExpirationLoggingPolicySet()) {
               copy.setExpirationLoggingPolicy(this.bean.getExpirationLoggingPolicy());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ExpirationPolicy")) && this.bean.isExpirationPolicySet()) {
               copy.setExpirationPolicy(this.bean.getExpirationPolicy());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MaximumMessageSize")) && this.bean.isMaximumMessageSizeSet()) {
               copy.setMaximumMessageSize(this.bean.getMaximumMessageSize());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MessagesMaximum")) && this.bean.isMessagesMaximumSet()) {
               copy.setMessagesMaximum(this.bean.getMessagesMaximum());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MessagesThresholdHigh")) && this.bean.isMessagesThresholdHighSet()) {
               copy.setMessagesThresholdHigh(this.bean.getMessagesThresholdHigh());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MessagesThresholdLow")) && this.bean.isMessagesThresholdLowSet()) {
               copy.setMessagesThresholdLow(this.bean.getMessagesThresholdLow());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("PriorityOverride")) && this.bean.isPriorityOverrideSet()) {
               copy.setPriorityOverride(this.bean.getPriorityOverride());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("RedeliveryDelayOverride")) && this.bean.isRedeliveryDelayOverrideSet()) {
               copy.setRedeliveryDelayOverride(this.bean.getRedeliveryDelayOverride());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("RedeliveryLimit")) && this.bean.isRedeliveryLimitSet()) {
               copy.setRedeliveryLimit(this.bean.getRedeliveryLimit());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("TimeToDeliverOverride")) && this.bean.isTimeToDeliverOverrideSet()) {
               copy.setTimeToDeliverOverride(this.bean.getTimeToDeliverOverride());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("TimeToLiveOverride")) && this.bean.isTimeToLiveOverrideSet()) {
               copy.setTimeToLiveOverride(this.bean.getTimeToLiveOverride());
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
         this.inferSubTree(this.bean.getDestinationKeys(), clazz, annotation);
         this.inferSubTree(this.bean.getErrorDestination(), clazz, annotation);
      }
   }
}

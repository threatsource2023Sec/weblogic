package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class DestinationBeanImpl extends TargetableBeanImpl implements DestinationBean, Serializable {
   private String _AttachSender;
   private boolean _ConsumptionPausedAtStartup;
   private boolean _DefaultUnitOfOrder;
   private DeliveryFailureParamsBean _DeliveryFailureParams;
   private DeliveryParamsOverridesBean _DeliveryParamsOverrides;
   private String[] _DestinationKeys;
   private int _IncompleteWorkExpirationTime;
   private boolean _InsertionPausedAtStartup;
   private String _JMSCreateDestinationIdentifier;
   private String _JNDIName;
   private String _LoadBalancingPolicy;
   private String _LocalJNDIName;
   private int _MaximumMessageSize;
   private MessageLoggingParamsBean _MessageLoggingParams;
   private int _MessagingPerformancePreference;
   private boolean _ProductionPausedAtStartup;
   private QuotaBean _Quota;
   private String _SAFExportPolicy;
   private TemplateBean _Template;
   private ThresholdParamsBean _Thresholds;
   private String _UnitOfOrderRouting;
   private String _UnitOfWorkHandlingPolicy;
   private static SchemaHelper2 _schemaHelper;

   public DestinationBeanImpl() {
      this._initializeProperty(-1);
   }

   public DestinationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DestinationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public TemplateBean getTemplate() {
      return this._Template;
   }

   public String getTemplateAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getTemplate();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isTemplateInherited() {
      return false;
   }

   public boolean isTemplateSet() {
      return this._isSet(5);
   }

   public void setTemplateAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, TemplateBean.class, new ReferenceManager.Resolver(this, 5) {
            public void resolveReference(Object value) {
               try {
                  DestinationBeanImpl.this.setTemplate((TemplateBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         TemplateBean _oldVal = this._Template;
         this._initializeProperty(5);
         this._postSet(5, _oldVal, this._Template);
      }

   }

   public void setTemplate(TemplateBean param0) throws IllegalArgumentException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 5, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return DestinationBeanImpl.this.getTemplate();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      TemplateBean _oldVal = this._Template;
      this._Template = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String[] getDestinationKeys() {
      if (!this._isSet(6)) {
         try {
            return this.getTemplate().getDestinationKeys();
         } catch (NullPointerException var2) {
         }
      }

      return this._DestinationKeys;
   }

   public boolean isDestinationKeysInherited() {
      return false;
   }

   public boolean isDestinationKeysSet() {
      return this._isSet(6);
   }

   public void addDestinationKey(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(6)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDestinationKeys(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDestinationKeys(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDestinationKey(String param0) {
      String[] _old = this.getDestinationKeys();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDestinationKeys(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDestinationKeys(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._DestinationKeys;
      this._DestinationKeys = param0;
      this._postSet(6, _oldVal, param0);
   }

   public ThresholdParamsBean getThresholds() {
      return this._Thresholds;
   }

   public boolean isThresholdsInherited() {
      return false;
   }

   public boolean isThresholdsSet() {
      return this._isSet(7) || this._isAnythingSet((AbstractDescriptorBean)this.getThresholds());
   }

   public void setThresholds(ThresholdParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 7)) {
         this._postCreate(_child);
      }

      ThresholdParamsBean _oldVal = this._Thresholds;
      this._Thresholds = param0;
      this._postSet(7, _oldVal, param0);
   }

   public DeliveryParamsOverridesBean getDeliveryParamsOverrides() {
      return this._DeliveryParamsOverrides;
   }

   public boolean isDeliveryParamsOverridesInherited() {
      return false;
   }

   public boolean isDeliveryParamsOverridesSet() {
      return this._isSet(8) || this._isAnythingSet((AbstractDescriptorBean)this.getDeliveryParamsOverrides());
   }

   public void setDeliveryParamsOverrides(DeliveryParamsOverridesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 8)) {
         this._postCreate(_child);
      }

      DeliveryParamsOverridesBean _oldVal = this._DeliveryParamsOverrides;
      this._DeliveryParamsOverrides = param0;
      this._postSet(8, _oldVal, param0);
   }

   public DeliveryFailureParamsBean getDeliveryFailureParams() {
      return this._DeliveryFailureParams;
   }

   public boolean isDeliveryFailureParamsInherited() {
      return false;
   }

   public boolean isDeliveryFailureParamsSet() {
      return this._isSet(9) || this._isAnythingSet((AbstractDescriptorBean)this.getDeliveryFailureParams());
   }

   public void setDeliveryFailureParams(DeliveryFailureParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 9)) {
         this._postCreate(_child);
      }

      DeliveryFailureParamsBean _oldVal = this._DeliveryFailureParams;
      this._DeliveryFailureParams = param0;
      this._postSet(9, _oldVal, param0);
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      return this._MessageLoggingParams;
   }

   public boolean isMessageLoggingParamsInherited() {
      return false;
   }

   public boolean isMessageLoggingParamsSet() {
      return this._isSet(10) || this._isAnythingSet((AbstractDescriptorBean)this.getMessageLoggingParams());
   }

   public void setMessageLoggingParams(MessageLoggingParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 10)) {
         this._postCreate(_child);
      }

      MessageLoggingParamsBean _oldVal = this._MessageLoggingParams;
      this._MessageLoggingParams = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getAttachSender() {
      if (!this._isSet(11)) {
         try {
            return this.getTemplate().getAttachSender();
         } catch (NullPointerException var2) {
         }
      }

      return this._AttachSender;
   }

   public boolean isAttachSenderInherited() {
      return false;
   }

   public boolean isAttachSenderSet() {
      return this._isSet(11);
   }

   public void setAttachSender(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"supports", "never", "always"};
      param0 = LegalChecks.checkInEnum("AttachSender", param0, _set);
      String _oldVal = this._AttachSender;
      this._AttachSender = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isProductionPausedAtStartup() {
      if (!this._isSet(12)) {
         try {
            return this.getTemplate().isProductionPausedAtStartup();
         } catch (NullPointerException var2) {
         }
      }

      return this._ProductionPausedAtStartup;
   }

   public boolean isProductionPausedAtStartupInherited() {
      return false;
   }

   public boolean isProductionPausedAtStartupSet() {
      return this._isSet(12);
   }

   public void setProductionPausedAtStartup(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._ProductionPausedAtStartup;
      this._ProductionPausedAtStartup = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isInsertionPausedAtStartup() {
      if (!this._isSet(13)) {
         try {
            return this.getTemplate().isInsertionPausedAtStartup();
         } catch (NullPointerException var2) {
         }
      }

      return this._InsertionPausedAtStartup;
   }

   public boolean isInsertionPausedAtStartupInherited() {
      return false;
   }

   public boolean isInsertionPausedAtStartupSet() {
      return this._isSet(13);
   }

   public void setInsertionPausedAtStartup(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._InsertionPausedAtStartup;
      this._InsertionPausedAtStartup = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isConsumptionPausedAtStartup() {
      if (!this._isSet(14)) {
         try {
            return this.getTemplate().isConsumptionPausedAtStartup();
         } catch (NullPointerException var2) {
         }
      }

      return this._ConsumptionPausedAtStartup;
   }

   public boolean isConsumptionPausedAtStartupInherited() {
      return false;
   }

   public boolean isConsumptionPausedAtStartupSet() {
      return this._isSet(14);
   }

   public void setConsumptionPausedAtStartup(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._ConsumptionPausedAtStartup;
      this._ConsumptionPausedAtStartup = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getMaximumMessageSize() {
      if (!this._isSet(15)) {
         try {
            return this.getTemplate().getMaximumMessageSize();
         } catch (NullPointerException var2) {
         }
      }

      return this._MaximumMessageSize;
   }

   public boolean isMaximumMessageSizeInherited() {
      return false;
   }

   public boolean isMaximumMessageSizeSet() {
      return this._isSet(15);
   }

   public void setMaximumMessageSize(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MaximumMessageSize", (long)param0, 0L, 2147483647L);
      int _oldVal = this._MaximumMessageSize;
      this._MaximumMessageSize = param0;
      this._postSet(15, _oldVal, param0);
   }

   public QuotaBean getQuota() {
      if (!this._isSet(16)) {
         try {
            return this.getTemplate().getQuota();
         } catch (NullPointerException var2) {
         }
      }

      return this._Quota;
   }

   public String getQuotaAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getQuota();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isQuotaInherited() {
      return false;
   }

   public boolean isQuotaSet() {
      return this._isSet(16);
   }

   public void setQuotaAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, QuotaBean.class, new ReferenceManager.Resolver(this, 16) {
            public void resolveReference(Object value) {
               try {
                  DestinationBeanImpl.this.setQuota((QuotaBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         QuotaBean _oldVal = this._Quota;
         this._initializeProperty(16);
         this._postSet(16, _oldVal, this._Quota);
      }

   }

   public void setQuota(QuotaBean param0) throws IllegalArgumentException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 16, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return DestinationBeanImpl.this.getQuota();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      QuotaBean _oldVal = this._Quota;
      this._Quota = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getJNDIName() {
      return this._JNDIName;
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(17);
   }

   public void setJNDIName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getLocalJNDIName() {
      return this._LocalJNDIName;
   }

   public boolean isLocalJNDINameInherited() {
      return false;
   }

   public boolean isLocalJNDINameSet() {
      return this._isSet(18);
   }

   public void setLocalJNDIName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LocalJNDIName;
      this._LocalJNDIName = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getJMSCreateDestinationIdentifier() {
      return this._JMSCreateDestinationIdentifier;
   }

   public boolean isJMSCreateDestinationIdentifierInherited() {
      return false;
   }

   public boolean isJMSCreateDestinationIdentifierSet() {
      return this._isSet(19);
   }

   public void setJMSCreateDestinationIdentifier(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JMSCreateDestinationIdentifier;
      this._JMSCreateDestinationIdentifier = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean isDefaultUnitOfOrder() {
      return this._DefaultUnitOfOrder;
   }

   public boolean isDefaultUnitOfOrderInherited() {
      return false;
   }

   public boolean isDefaultUnitOfOrderSet() {
      return this._isSet(20);
   }

   public void setDefaultUnitOfOrder(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._DefaultUnitOfOrder;
      this._DefaultUnitOfOrder = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getSAFExportPolicy() {
      if (!this._isSet(21)) {
         try {
            return this.getTemplate().getSafExportPolicy();
         } catch (NullPointerException var2) {
         }
      }

      return this._SAFExportPolicy;
   }

   public boolean isSAFExportPolicyInherited() {
      return false;
   }

   public boolean isSAFExportPolicySet() {
      return this._isSet(21);
   }

   public void setSAFExportPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"All", "None"};
      param0 = LegalChecks.checkInEnum("SAFExportPolicy", param0, _set);
      String _oldVal = this._SAFExportPolicy;
      this._SAFExportPolicy = param0;
      this._postSet(21, _oldVal, param0);
   }

   public int getMessagingPerformancePreference() {
      if (!this._isSet(22)) {
         try {
            return this.getTemplate().getMessagingPerformancePreference();
         } catch (NullPointerException var2) {
         }
      }

      return this._MessagingPerformancePreference;
   }

   public boolean isMessagingPerformancePreferenceInherited() {
      return false;
   }

   public boolean isMessagingPerformancePreferenceSet() {
      return this._isSet(22);
   }

   public void setMessagingPerformancePreference(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MessagingPerformancePreference", (long)param0, 0L, 100L);
      int _oldVal = this._MessagingPerformancePreference;
      this._MessagingPerformancePreference = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getUnitOfWorkHandlingPolicy() {
      if (!this._isSet(23)) {
         try {
            return this.getTemplate().getUnitOfWorkHandlingPolicy();
         } catch (NullPointerException var2) {
         }
      }

      return this._UnitOfWorkHandlingPolicy;
   }

   public boolean isUnitOfWorkHandlingPolicyInherited() {
      return false;
   }

   public boolean isUnitOfWorkHandlingPolicySet() {
      return this._isSet(23);
   }

   public void setUnitOfWorkHandlingPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"PassThrough", "SingleMessageDelivery"};
      param0 = LegalChecks.checkInEnum("UnitOfWorkHandlingPolicy", param0, _set);
      String _oldVal = this._UnitOfWorkHandlingPolicy;
      this._UnitOfWorkHandlingPolicy = param0;
      this._postSet(23, _oldVal, param0);
   }

   public int getIncompleteWorkExpirationTime() {
      return this._IncompleteWorkExpirationTime;
   }

   public boolean isIncompleteWorkExpirationTimeInherited() {
      return false;
   }

   public boolean isIncompleteWorkExpirationTimeSet() {
      return this._isSet(24);
   }

   public void setIncompleteWorkExpirationTime(int param0) throws IllegalArgumentException {
      int _oldVal = this._IncompleteWorkExpirationTime;
      this._IncompleteWorkExpirationTime = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String getLoadBalancingPolicy() {
      return this._LoadBalancingPolicy;
   }

   public boolean isLoadBalancingPolicyInherited() {
      return false;
   }

   public boolean isLoadBalancingPolicySet() {
      return this._isSet(25);
   }

   public void setLoadBalancingPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Round-Robin", "Random"};
      param0 = LegalChecks.checkInEnum("LoadBalancingPolicy", param0, _set);
      String _oldVal = this._LoadBalancingPolicy;
      this._LoadBalancingPolicy = param0;
      this._postSet(25, _oldVal, param0);
   }

   public String getUnitOfOrderRouting() {
      return this._UnitOfOrderRouting;
   }

   public boolean isUnitOfOrderRoutingInherited() {
      return false;
   }

   public boolean isUnitOfOrderRoutingSet() {
      return this._isSet(26);
   }

   public void setUnitOfOrderRouting(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UnitOfOrderRouting;
      this._UnitOfOrderRouting = param0;
      this._postSet(26, _oldVal, param0);
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
      return super._isAnythingSet() || this.isDeliveryFailureParamsSet() || this.isDeliveryParamsOverridesSet() || this.isMessageLoggingParamsSet() || this.isThresholdsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._AttachSender = "supports";
               if (initOne) {
                  break;
               }
            case 9:
               this._DeliveryFailureParams = new DeliveryFailureParamsBeanImpl(this, 9);
               this._postCreate((AbstractDescriptorBean)this._DeliveryFailureParams);
               if (initOne) {
                  break;
               }
            case 8:
               this._DeliveryParamsOverrides = new DeliveryParamsOverridesBeanImpl(this, 8);
               this._postCreate((AbstractDescriptorBean)this._DeliveryParamsOverrides);
               if (initOne) {
                  break;
               }
            case 6:
               this._DestinationKeys = new String[0];
               if (initOne) {
                  break;
               }
            case 24:
               this._IncompleteWorkExpirationTime = -1;
               if (initOne) {
                  break;
               }
            case 19:
               this._JMSCreateDestinationIdentifier = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._JNDIName = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._LoadBalancingPolicy = "Round-Robin";
               if (initOne) {
                  break;
               }
            case 18:
               this._LocalJNDIName = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._MaximumMessageSize = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 10:
               this._MessageLoggingParams = new MessageLoggingParamsBeanImpl(this, 10);
               this._postCreate((AbstractDescriptorBean)this._MessageLoggingParams);
               if (initOne) {
                  break;
               }
            case 22:
               this._MessagingPerformancePreference = 25;
               if (initOne) {
                  break;
               }
            case 16:
               this._Quota = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._SAFExportPolicy = "All";
               if (initOne) {
                  break;
               }
            case 5:
               this._Template = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Thresholds = new ThresholdParamsBeanImpl(this, 7);
               this._postCreate((AbstractDescriptorBean)this._Thresholds);
               if (initOne) {
                  break;
               }
            case 26:
               this._UnitOfOrderRouting = "Hash";
               if (initOne) {
                  break;
               }
            case 23:
               this._UnitOfWorkHandlingPolicy = "PassThrough";
               if (initOne) {
                  break;
               }
            case 14:
               this._ConsumptionPausedAtStartup = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._DefaultUnitOfOrder = false;
               if (initOne) {
                  break;
               }
            case 13:
               this._InsertionPausedAtStartup = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._ProductionPausedAtStartup = false;
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

   public static class SchemaHelper2 extends TargetableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("quota")) {
                  return 16;
               }
            case 6:
            case 7:
            case 11:
            case 12:
            case 14:
            case 16:
            case 18:
            case 19:
            case 24:
            case 26:
            case 30:
            default:
               break;
            case 8:
               if (s.equals("template")) {
                  return 5;
               }
               break;
            case 9:
               if (s.equals("jndi-name")) {
                  return 17;
               }
               break;
            case 10:
               if (s.equals("thresholds")) {
                  return 7;
               }
               break;
            case 13:
               if (s.equals("attach-sender")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("destination-key")) {
                  return 6;
               }

               if (s.equals("local-jndi-name")) {
                  return 18;
               }
               break;
            case 17:
               if (s.equals("saf-export-policy")) {
                  return 21;
               }
               break;
            case 20:
               if (s.equals("maximum-message-size")) {
                  return 15;
               }
               break;
            case 21:
               if (s.equals("load-balancing-policy")) {
                  return 25;
               }

               if (s.equals("unit-of-order-routing")) {
                  return 26;
               }

               if (s.equals("default-unit-of-order")) {
                  return 20;
               }
               break;
            case 22:
               if (s.equals("message-logging-params")) {
                  return 10;
               }
               break;
            case 23:
               if (s.equals("delivery-failure-params")) {
                  return 9;
               }
               break;
            case 25:
               if (s.equals("delivery-params-overrides")) {
                  return 8;
               }
               break;
            case 27:
               if (s.equals("insertion-paused-at-startup")) {
                  return 13;
               }
               break;
            case 28:
               if (s.equals("unit-of-work-handling-policy")) {
                  return 23;
               }

               if (s.equals("production-paused-at-startup")) {
                  return 12;
               }
               break;
            case 29:
               if (s.equals("consumption-paused-at-startup")) {
                  return 14;
               }
               break;
            case 31:
               if (s.equals("incomplete-work-expiration-time")) {
                  return 24;
               }
               break;
            case 32:
               if (s.equals("messaging-performance-preference")) {
                  return 22;
               }
               break;
            case 33:
               if (s.equals("jms-create-destination-identifier")) {
                  return 19;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 7:
               return new ThresholdParamsBeanImpl.SchemaHelper2();
            case 8:
               return new DeliveryParamsOverridesBeanImpl.SchemaHelper2();
            case 9:
               return new DeliveryFailureParamsBeanImpl.SchemaHelper2();
            case 10:
               return new MessageLoggingParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 5:
               return "template";
            case 6:
               return "destination-key";
            case 7:
               return "thresholds";
            case 8:
               return "delivery-params-overrides";
            case 9:
               return "delivery-failure-params";
            case 10:
               return "message-logging-params";
            case 11:
               return "attach-sender";
            case 12:
               return "production-paused-at-startup";
            case 13:
               return "insertion-paused-at-startup";
            case 14:
               return "consumption-paused-at-startup";
            case 15:
               return "maximum-message-size";
            case 16:
               return "quota";
            case 17:
               return "jndi-name";
            case 18:
               return "local-jndi-name";
            case 19:
               return "jms-create-destination-identifier";
            case 20:
               return "default-unit-of-order";
            case 21:
               return "saf-export-policy";
            case 22:
               return "messaging-performance-preference";
            case 23:
               return "unit-of-work-handling-policy";
            case 24:
               return "incomplete-work-expiration-time";
            case 25:
               return "load-balancing-policy";
            case 26:
               return "unit-of-order-routing";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 6:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 17:
            case 18:
            case 19:
            case 21:
            default:
               return super.isConfigurable(propIndex);
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 20:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends TargetableBeanImpl.Helper {
      private DestinationBeanImpl bean;

      protected Helper(DestinationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 5:
               return "Template";
            case 6:
               return "DestinationKeys";
            case 7:
               return "Thresholds";
            case 8:
               return "DeliveryParamsOverrides";
            case 9:
               return "DeliveryFailureParams";
            case 10:
               return "MessageLoggingParams";
            case 11:
               return "AttachSender";
            case 12:
               return "ProductionPausedAtStartup";
            case 13:
               return "InsertionPausedAtStartup";
            case 14:
               return "ConsumptionPausedAtStartup";
            case 15:
               return "MaximumMessageSize";
            case 16:
               return "Quota";
            case 17:
               return "JNDIName";
            case 18:
               return "LocalJNDIName";
            case 19:
               return "JMSCreateDestinationIdentifier";
            case 20:
               return "DefaultUnitOfOrder";
            case 21:
               return "SAFExportPolicy";
            case 22:
               return "MessagingPerformancePreference";
            case 23:
               return "UnitOfWorkHandlingPolicy";
            case 24:
               return "IncompleteWorkExpirationTime";
            case 25:
               return "LoadBalancingPolicy";
            case 26:
               return "UnitOfOrderRouting";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AttachSender")) {
            return 11;
         } else if (propName.equals("DeliveryFailureParams")) {
            return 9;
         } else if (propName.equals("DeliveryParamsOverrides")) {
            return 8;
         } else if (propName.equals("DestinationKeys")) {
            return 6;
         } else if (propName.equals("IncompleteWorkExpirationTime")) {
            return 24;
         } else if (propName.equals("JMSCreateDestinationIdentifier")) {
            return 19;
         } else if (propName.equals("JNDIName")) {
            return 17;
         } else if (propName.equals("LoadBalancingPolicy")) {
            return 25;
         } else if (propName.equals("LocalJNDIName")) {
            return 18;
         } else if (propName.equals("MaximumMessageSize")) {
            return 15;
         } else if (propName.equals("MessageLoggingParams")) {
            return 10;
         } else if (propName.equals("MessagingPerformancePreference")) {
            return 22;
         } else if (propName.equals("Quota")) {
            return 16;
         } else if (propName.equals("SAFExportPolicy")) {
            return 21;
         } else if (propName.equals("Template")) {
            return 5;
         } else if (propName.equals("Thresholds")) {
            return 7;
         } else if (propName.equals("UnitOfOrderRouting")) {
            return 26;
         } else if (propName.equals("UnitOfWorkHandlingPolicy")) {
            return 23;
         } else if (propName.equals("ConsumptionPausedAtStartup")) {
            return 14;
         } else if (propName.equals("DefaultUnitOfOrder")) {
            return 20;
         } else if (propName.equals("InsertionPausedAtStartup")) {
            return 13;
         } else {
            return propName.equals("ProductionPausedAtStartup") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getDeliveryFailureParams() != null) {
            iterators.add(new ArrayIterator(new DeliveryFailureParamsBean[]{this.bean.getDeliveryFailureParams()}));
         }

         if (this.bean.getDeliveryParamsOverrides() != null) {
            iterators.add(new ArrayIterator(new DeliveryParamsOverridesBean[]{this.bean.getDeliveryParamsOverrides()}));
         }

         if (this.bean.getMessageLoggingParams() != null) {
            iterators.add(new ArrayIterator(new MessageLoggingParamsBean[]{this.bean.getMessageLoggingParams()}));
         }

         if (this.bean.getThresholds() != null) {
            iterators.add(new ArrayIterator(new ThresholdParamsBean[]{this.bean.getThresholds()}));
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
            if (this.bean.isAttachSenderSet()) {
               buf.append("AttachSender");
               buf.append(String.valueOf(this.bean.getAttachSender()));
            }

            childValue = this.computeChildHashValue(this.bean.getDeliveryFailureParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDeliveryParamsOverrides());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDestinationKeysSet()) {
               buf.append("DestinationKeys");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDestinationKeys())));
            }

            if (this.bean.isIncompleteWorkExpirationTimeSet()) {
               buf.append("IncompleteWorkExpirationTime");
               buf.append(String.valueOf(this.bean.getIncompleteWorkExpirationTime()));
            }

            if (this.bean.isJMSCreateDestinationIdentifierSet()) {
               buf.append("JMSCreateDestinationIdentifier");
               buf.append(String.valueOf(this.bean.getJMSCreateDestinationIdentifier()));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            if (this.bean.isLoadBalancingPolicySet()) {
               buf.append("LoadBalancingPolicy");
               buf.append(String.valueOf(this.bean.getLoadBalancingPolicy()));
            }

            if (this.bean.isLocalJNDINameSet()) {
               buf.append("LocalJNDIName");
               buf.append(String.valueOf(this.bean.getLocalJNDIName()));
            }

            if (this.bean.isMaximumMessageSizeSet()) {
               buf.append("MaximumMessageSize");
               buf.append(String.valueOf(this.bean.getMaximumMessageSize()));
            }

            childValue = this.computeChildHashValue(this.bean.getMessageLoggingParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMessagingPerformancePreferenceSet()) {
               buf.append("MessagingPerformancePreference");
               buf.append(String.valueOf(this.bean.getMessagingPerformancePreference()));
            }

            if (this.bean.isQuotaSet()) {
               buf.append("Quota");
               buf.append(String.valueOf(this.bean.getQuota()));
            }

            if (this.bean.isSAFExportPolicySet()) {
               buf.append("SAFExportPolicy");
               buf.append(String.valueOf(this.bean.getSAFExportPolicy()));
            }

            if (this.bean.isTemplateSet()) {
               buf.append("Template");
               buf.append(String.valueOf(this.bean.getTemplate()));
            }

            childValue = this.computeChildHashValue(this.bean.getThresholds());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isUnitOfOrderRoutingSet()) {
               buf.append("UnitOfOrderRouting");
               buf.append(String.valueOf(this.bean.getUnitOfOrderRouting()));
            }

            if (this.bean.isUnitOfWorkHandlingPolicySet()) {
               buf.append("UnitOfWorkHandlingPolicy");
               buf.append(String.valueOf(this.bean.getUnitOfWorkHandlingPolicy()));
            }

            if (this.bean.isConsumptionPausedAtStartupSet()) {
               buf.append("ConsumptionPausedAtStartup");
               buf.append(String.valueOf(this.bean.isConsumptionPausedAtStartup()));
            }

            if (this.bean.isDefaultUnitOfOrderSet()) {
               buf.append("DefaultUnitOfOrder");
               buf.append(String.valueOf(this.bean.isDefaultUnitOfOrder()));
            }

            if (this.bean.isInsertionPausedAtStartupSet()) {
               buf.append("InsertionPausedAtStartup");
               buf.append(String.valueOf(this.bean.isInsertionPausedAtStartup()));
            }

            if (this.bean.isProductionPausedAtStartupSet()) {
               buf.append("ProductionPausedAtStartup");
               buf.append(String.valueOf(this.bean.isProductionPausedAtStartup()));
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
            DestinationBeanImpl otherTyped = (DestinationBeanImpl)other;
            this.computeDiff("AttachSender", this.bean.getAttachSender(), otherTyped.getAttachSender(), true);
            this.computeSubDiff("DeliveryFailureParams", this.bean.getDeliveryFailureParams(), otherTyped.getDeliveryFailureParams());
            this.computeSubDiff("DeliveryParamsOverrides", this.bean.getDeliveryParamsOverrides(), otherTyped.getDeliveryParamsOverrides());
            this.computeDiff("DestinationKeys", this.bean.getDestinationKeys(), otherTyped.getDestinationKeys(), false);
            this.computeDiff("IncompleteWorkExpirationTime", this.bean.getIncompleteWorkExpirationTime(), otherTyped.getIncompleteWorkExpirationTime(), true);
            this.computeDiff("JMSCreateDestinationIdentifier", this.bean.getJMSCreateDestinationIdentifier(), otherTyped.getJMSCreateDestinationIdentifier(), false);
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), true);
            this.computeDiff("LoadBalancingPolicy", this.bean.getLoadBalancingPolicy(), otherTyped.getLoadBalancingPolicy(), true);
            this.computeDiff("LocalJNDIName", this.bean.getLocalJNDIName(), otherTyped.getLocalJNDIName(), true);
            this.computeDiff("MaximumMessageSize", this.bean.getMaximumMessageSize(), otherTyped.getMaximumMessageSize(), true);
            this.computeSubDiff("MessageLoggingParams", this.bean.getMessageLoggingParams(), otherTyped.getMessageLoggingParams());
            this.computeDiff("MessagingPerformancePreference", this.bean.getMessagingPerformancePreference(), otherTyped.getMessagingPerformancePreference(), true);
            this.computeDiff("Quota", this.bean.getQuota(), otherTyped.getQuota(), true);
            this.computeDiff("SAFExportPolicy", this.bean.getSAFExportPolicy(), otherTyped.getSAFExportPolicy(), false);
            this.computeDiff("Template", this.bean.getTemplate(), otherTyped.getTemplate(), false);
            this.computeSubDiff("Thresholds", this.bean.getThresholds(), otherTyped.getThresholds());
            this.computeDiff("UnitOfOrderRouting", this.bean.getUnitOfOrderRouting(), otherTyped.getUnitOfOrderRouting(), false);
            this.computeDiff("UnitOfWorkHandlingPolicy", this.bean.getUnitOfWorkHandlingPolicy(), otherTyped.getUnitOfWorkHandlingPolicy(), true);
            this.computeDiff("ConsumptionPausedAtStartup", this.bean.isConsumptionPausedAtStartup(), otherTyped.isConsumptionPausedAtStartup(), false);
            this.computeDiff("DefaultUnitOfOrder", this.bean.isDefaultUnitOfOrder(), otherTyped.isDefaultUnitOfOrder(), false);
            this.computeDiff("InsertionPausedAtStartup", this.bean.isInsertionPausedAtStartup(), otherTyped.isInsertionPausedAtStartup(), false);
            this.computeDiff("ProductionPausedAtStartup", this.bean.isProductionPausedAtStartup(), otherTyped.isProductionPausedAtStartup(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DestinationBeanImpl original = (DestinationBeanImpl)event.getSourceBean();
            DestinationBeanImpl proposed = (DestinationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AttachSender")) {
                  original.setAttachSender(proposed.getAttachSender());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("DeliveryFailureParams")) {
                  if (type == 2) {
                     original.setDeliveryFailureParams((DeliveryFailureParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getDeliveryFailureParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DeliveryFailureParams", (DescriptorBean)original.getDeliveryFailureParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("DeliveryParamsOverrides")) {
                  if (type == 2) {
                     original.setDeliveryParamsOverrides((DeliveryParamsOverridesBean)this.createCopy((AbstractDescriptorBean)proposed.getDeliveryParamsOverrides()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DeliveryParamsOverrides", (DescriptorBean)original.getDeliveryParamsOverrides());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("DestinationKeys")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDestinationKey((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDestinationKey((String)update.getRemovedObject());
                  }

                  if (original.getDestinationKeys() == null || original.getDestinationKeys().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("IncompleteWorkExpirationTime")) {
                  original.setIncompleteWorkExpirationTime(proposed.getIncompleteWorkExpirationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("JMSCreateDestinationIdentifier")) {
                  original.setJMSCreateDestinationIdentifier(proposed.getJMSCreateDestinationIdentifier());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("LoadBalancingPolicy")) {
                  original.setLoadBalancingPolicy(proposed.getLoadBalancingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("LocalJNDIName")) {
                  original.setLocalJNDIName(proposed.getLocalJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("MaximumMessageSize")) {
                  original.setMaximumMessageSize(proposed.getMaximumMessageSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MessageLoggingParams")) {
                  if (type == 2) {
                     original.setMessageLoggingParams((MessageLoggingParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getMessageLoggingParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MessageLoggingParams", (DescriptorBean)original.getMessageLoggingParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MessagingPerformancePreference")) {
                  original.setMessagingPerformancePreference(proposed.getMessagingPerformancePreference());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("Quota")) {
                  original.setQuotaAsString(proposed.getQuotaAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("SAFExportPolicy")) {
                  original.setSAFExportPolicy(proposed.getSAFExportPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("Template")) {
                  original.setTemplateAsString(proposed.getTemplateAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Thresholds")) {
                  if (type == 2) {
                     original.setThresholds((ThresholdParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getThresholds()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Thresholds", (DescriptorBean)original.getThresholds());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("UnitOfOrderRouting")) {
                  original.setUnitOfOrderRouting(proposed.getUnitOfOrderRouting());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("UnitOfWorkHandlingPolicy")) {
                  original.setUnitOfWorkHandlingPolicy(proposed.getUnitOfWorkHandlingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("ConsumptionPausedAtStartup")) {
                  original.setConsumptionPausedAtStartup(proposed.isConsumptionPausedAtStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("DefaultUnitOfOrder")) {
                  original.setDefaultUnitOfOrder(proposed.isDefaultUnitOfOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("InsertionPausedAtStartup")) {
                  original.setInsertionPausedAtStartup(proposed.isInsertionPausedAtStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ProductionPausedAtStartup")) {
                  original.setProductionPausedAtStartup(proposed.isProductionPausedAtStartup());
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
            DestinationBeanImpl copy = (DestinationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AttachSender")) && this.bean.isAttachSenderSet()) {
               copy.setAttachSender(this.bean.getAttachSender());
            }

            if ((excludeProps == null || !excludeProps.contains("DeliveryFailureParams")) && this.bean.isDeliveryFailureParamsSet() && !copy._isSet(9)) {
               Object o = this.bean.getDeliveryFailureParams();
               copy.setDeliveryFailureParams((DeliveryFailureParamsBean)null);
               copy.setDeliveryFailureParams(o == null ? null : (DeliveryFailureParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DeliveryParamsOverrides")) && this.bean.isDeliveryParamsOverridesSet() && !copy._isSet(8)) {
               Object o = this.bean.getDeliveryParamsOverrides();
               copy.setDeliveryParamsOverrides((DeliveryParamsOverridesBean)null);
               copy.setDeliveryParamsOverrides(o == null ? null : (DeliveryParamsOverridesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationKeys")) && this.bean.isDestinationKeysSet()) {
               Object o = this.bean.getDestinationKeys();
               copy.setDestinationKeys(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("IncompleteWorkExpirationTime")) && this.bean.isIncompleteWorkExpirationTimeSet()) {
               copy.setIncompleteWorkExpirationTime(this.bean.getIncompleteWorkExpirationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSCreateDestinationIdentifier")) && this.bean.isJMSCreateDestinationIdentifierSet()) {
               copy.setJMSCreateDestinationIdentifier(this.bean.getJMSCreateDestinationIdentifier());
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadBalancingPolicy")) && this.bean.isLoadBalancingPolicySet()) {
               copy.setLoadBalancingPolicy(this.bean.getLoadBalancingPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalJNDIName")) && this.bean.isLocalJNDINameSet()) {
               copy.setLocalJNDIName(this.bean.getLocalJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumMessageSize")) && this.bean.isMaximumMessageSizeSet()) {
               copy.setMaximumMessageSize(this.bean.getMaximumMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageLoggingParams")) && this.bean.isMessageLoggingParamsSet() && !copy._isSet(10)) {
               Object o = this.bean.getMessageLoggingParams();
               copy.setMessageLoggingParams((MessageLoggingParamsBean)null);
               copy.setMessageLoggingParams(o == null ? null : (MessageLoggingParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MessagingPerformancePreference")) && this.bean.isMessagingPerformancePreferenceSet()) {
               copy.setMessagingPerformancePreference(this.bean.getMessagingPerformancePreference());
            }

            if ((excludeProps == null || !excludeProps.contains("Quota")) && this.bean.isQuotaSet()) {
               copy._unSet(copy, 16);
               copy.setQuotaAsString(this.bean.getQuotaAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("SAFExportPolicy")) && this.bean.isSAFExportPolicySet()) {
               copy.setSAFExportPolicy(this.bean.getSAFExportPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Template")) && this.bean.isTemplateSet()) {
               copy._unSet(copy, 5);
               copy.setTemplateAsString(this.bean.getTemplateAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Thresholds")) && this.bean.isThresholdsSet() && !copy._isSet(7)) {
               Object o = this.bean.getThresholds();
               copy.setThresholds((ThresholdParamsBean)null);
               copy.setThresholds(o == null ? null : (ThresholdParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("UnitOfOrderRouting")) && this.bean.isUnitOfOrderRoutingSet()) {
               copy.setUnitOfOrderRouting(this.bean.getUnitOfOrderRouting());
            }

            if ((excludeProps == null || !excludeProps.contains("UnitOfWorkHandlingPolicy")) && this.bean.isUnitOfWorkHandlingPolicySet()) {
               copy.setUnitOfWorkHandlingPolicy(this.bean.getUnitOfWorkHandlingPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("ConsumptionPausedAtStartup")) && this.bean.isConsumptionPausedAtStartupSet()) {
               copy.setConsumptionPausedAtStartup(this.bean.isConsumptionPausedAtStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultUnitOfOrder")) && this.bean.isDefaultUnitOfOrderSet()) {
               copy.setDefaultUnitOfOrder(this.bean.isDefaultUnitOfOrder());
            }

            if ((excludeProps == null || !excludeProps.contains("InsertionPausedAtStartup")) && this.bean.isInsertionPausedAtStartupSet()) {
               copy.setInsertionPausedAtStartup(this.bean.isInsertionPausedAtStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("ProductionPausedAtStartup")) && this.bean.isProductionPausedAtStartupSet()) {
               copy.setProductionPausedAtStartup(this.bean.isProductionPausedAtStartup());
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
         this.inferSubTree(this.bean.getDeliveryFailureParams(), clazz, annotation);
         this.inferSubTree(this.bean.getDeliveryParamsOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageLoggingParams(), clazz, annotation);
         this.inferSubTree(this.bean.getQuota(), clazz, annotation);
         this.inferSubTree(this.bean.getTemplate(), clazz, annotation);
         this.inferSubTree(this.bean.getThresholds(), clazz, annotation);
      }
   }
}

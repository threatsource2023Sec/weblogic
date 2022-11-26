package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.descriptor.beangen.CustomizerFactoryBuilder;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.customizers.TemplateBeanCustomizer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class TemplateBeanImpl extends NamedEntityBeanImpl implements TemplateBean, Serializable {
   private String _AttachSender;
   private boolean _ConsumptionPausedAtStartup;
   private boolean _DefaultUnitOfOrder;
   private DeliveryFailureParamsBean _DeliveryFailureParams;
   private DeliveryParamsOverridesBean _DeliveryParamsOverrides;
   private String[] _DestinationKeys;
   private GroupParamsBean[] _GroupParams;
   private int _IncompleteWorkExpirationTime;
   private boolean _InsertionPausedAtStartup;
   private int _MaximumMessageSize;
   private MessageLoggingParamsBean _MessageLoggingParams;
   private int _MessagingPerformancePreference;
   private MulticastParamsBean _Multicast;
   private boolean _ProductionPausedAtStartup;
   private QuotaBean _Quota;
   private String _SafExportPolicy;
   private ThresholdParamsBean _Thresholds;
   private TopicSubscriptionParamsBean _TopicSubscriptionParams;
   private String _UnitOfWorkHandlingPolicy;
   private transient TemplateBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public TemplateBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.TemplateBeanCustomizerFactory");
         this._customizer = (TemplateBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public TemplateBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.TemplateBeanCustomizerFactory");
         this._customizer = (TemplateBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public TemplateBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jms.module.customizers.TemplateBeanCustomizerFactory");
         this._customizer = (TemplateBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String[] getDestinationKeys() {
      return this._DestinationKeys;
   }

   public boolean isDestinationKeysInherited() {
      return false;
   }

   public boolean isDestinationKeysSet() {
      return this._isSet(3);
   }

   public void addDestinationKey(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
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
      this._postSet(3, _oldVal, param0);
   }

   public ThresholdParamsBean getThresholds() {
      return this._Thresholds;
   }

   public boolean isThresholdsInherited() {
      return false;
   }

   public boolean isThresholdsSet() {
      return this._isSet(4) || this._isAnythingSet((AbstractDescriptorBean)this.getThresholds());
   }

   public void setThresholds(ThresholdParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 4)) {
         this._postCreate(_child);
      }

      ThresholdParamsBean _oldVal = this._Thresholds;
      this._Thresholds = param0;
      this._postSet(4, _oldVal, param0);
   }

   public DeliveryParamsOverridesBean getDeliveryParamsOverrides() {
      return this._DeliveryParamsOverrides;
   }

   public boolean isDeliveryParamsOverridesInherited() {
      return false;
   }

   public boolean isDeliveryParamsOverridesSet() {
      return this._isSet(5) || this._isAnythingSet((AbstractDescriptorBean)this.getDeliveryParamsOverrides());
   }

   public void setDeliveryParamsOverrides(DeliveryParamsOverridesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 5)) {
         this._postCreate(_child);
      }

      DeliveryParamsOverridesBean _oldVal = this._DeliveryParamsOverrides;
      this._DeliveryParamsOverrides = param0;
      this._postSet(5, _oldVal, param0);
   }

   public DeliveryFailureParamsBean getDeliveryFailureParams() {
      return this._DeliveryFailureParams;
   }

   public boolean isDeliveryFailureParamsInherited() {
      return false;
   }

   public boolean isDeliveryFailureParamsSet() {
      return this._isSet(6) || this._isAnythingSet((AbstractDescriptorBean)this.getDeliveryFailureParams());
   }

   public void setDeliveryFailureParams(DeliveryFailureParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 6)) {
         this._postCreate(_child);
      }

      DeliveryFailureParamsBean _oldVal = this._DeliveryFailureParams;
      this._DeliveryFailureParams = param0;
      this._postSet(6, _oldVal, param0);
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      return this._MessageLoggingParams;
   }

   public boolean isMessageLoggingParamsInherited() {
      return false;
   }

   public boolean isMessageLoggingParamsSet() {
      return this._isSet(7) || this._isAnythingSet((AbstractDescriptorBean)this.getMessageLoggingParams());
   }

   public void setMessageLoggingParams(MessageLoggingParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 7)) {
         this._postCreate(_child);
      }

      MessageLoggingParamsBean _oldVal = this._MessageLoggingParams;
      this._MessageLoggingParams = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getAttachSender() {
      return this._AttachSender;
   }

   public boolean isAttachSenderInherited() {
      return false;
   }

   public boolean isAttachSenderSet() {
      return this._isSet(8);
   }

   public void setAttachSender(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"supports", "never", "always"};
      param0 = LegalChecks.checkInEnum("AttachSender", param0, _set);
      String _oldVal = this._AttachSender;
      this._AttachSender = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean isProductionPausedAtStartup() {
      return this._ProductionPausedAtStartup;
   }

   public boolean isProductionPausedAtStartupInherited() {
      return false;
   }

   public boolean isProductionPausedAtStartupSet() {
      return this._isSet(9);
   }

   public void setProductionPausedAtStartup(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._ProductionPausedAtStartup;
      this._ProductionPausedAtStartup = param0;
      this._postSet(9, _oldVal, param0);
   }

   public boolean isInsertionPausedAtStartup() {
      return this._InsertionPausedAtStartup;
   }

   public boolean isInsertionPausedAtStartupInherited() {
      return false;
   }

   public boolean isInsertionPausedAtStartupSet() {
      return this._isSet(10);
   }

   public void setInsertionPausedAtStartup(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._InsertionPausedAtStartup;
      this._InsertionPausedAtStartup = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isConsumptionPausedAtStartup() {
      return this._ConsumptionPausedAtStartup;
   }

   public boolean isConsumptionPausedAtStartupInherited() {
      return false;
   }

   public boolean isConsumptionPausedAtStartupSet() {
      return this._isSet(11);
   }

   public void setConsumptionPausedAtStartup(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._ConsumptionPausedAtStartup;
      this._ConsumptionPausedAtStartup = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getMaximumMessageSize() {
      return this._MaximumMessageSize;
   }

   public boolean isMaximumMessageSizeInherited() {
      return false;
   }

   public boolean isMaximumMessageSizeSet() {
      return this._isSet(12);
   }

   public void setMaximumMessageSize(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MaximumMessageSize", (long)param0, 0L, 2147483647L);
      int _oldVal = this._MaximumMessageSize;
      this._MaximumMessageSize = param0;
      this._postSet(12, _oldVal, param0);
   }

   public QuotaBean getQuota() {
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
      return this._isSet(13);
   }

   public void setQuotaAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, QuotaBean.class, new ReferenceManager.Resolver(this, 13) {
            public void resolveReference(Object value) {
               try {
                  TemplateBeanImpl.this.setQuota((QuotaBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         QuotaBean _oldVal = this._Quota;
         this._initializeProperty(13);
         this._postSet(13, _oldVal, this._Quota);
      }

   }

   public void setQuota(QuotaBean param0) throws IllegalArgumentException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 13, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return TemplateBeanImpl.this.getQuota();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      QuotaBean _oldVal = this._Quota;
      this._Quota = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isDefaultUnitOfOrder() {
      return this._DefaultUnitOfOrder;
   }

   public boolean isDefaultUnitOfOrderInherited() {
      return false;
   }

   public boolean isDefaultUnitOfOrderSet() {
      return this._isSet(14);
   }

   public void setDefaultUnitOfOrder(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._DefaultUnitOfOrder;
      this._DefaultUnitOfOrder = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getSafExportPolicy() {
      return this._SafExportPolicy;
   }

   public boolean isSafExportPolicyInherited() {
      return false;
   }

   public boolean isSafExportPolicySet() {
      return this._isSet(15);
   }

   public void setSafExportPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"All", "None"};
      param0 = LegalChecks.checkInEnum("SafExportPolicy", param0, _set);
      String _oldVal = this._SafExportPolicy;
      this._SafExportPolicy = param0;
      this._postSet(15, _oldVal, param0);
   }

   public MulticastParamsBean getMulticast() {
      return this._Multicast;
   }

   public boolean isMulticastInherited() {
      return false;
   }

   public boolean isMulticastSet() {
      return this._isSet(16) || this._isAnythingSet((AbstractDescriptorBean)this.getMulticast());
   }

   public void setMulticast(MulticastParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 16)) {
         this._postCreate(_child);
      }

      MulticastParamsBean _oldVal = this._Multicast;
      this._Multicast = param0;
      this._postSet(16, _oldVal, param0);
   }

   public TopicSubscriptionParamsBean getTopicSubscriptionParams() {
      return this._TopicSubscriptionParams;
   }

   public boolean isTopicSubscriptionParamsInherited() {
      return false;
   }

   public boolean isTopicSubscriptionParamsSet() {
      return this._isSet(17) || this._isAnythingSet((AbstractDescriptorBean)this.getTopicSubscriptionParams());
   }

   public void setTopicSubscriptionParams(TopicSubscriptionParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 17)) {
         this._postCreate(_child);
      }

      TopicSubscriptionParamsBean _oldVal = this._TopicSubscriptionParams;
      this._TopicSubscriptionParams = param0;
      this._postSet(17, _oldVal, param0);
   }

   public void addGroupParam(GroupParamsBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         GroupParamsBean[] _new;
         if (this._isSet(18)) {
            _new = (GroupParamsBean[])((GroupParamsBean[])this._getHelper()._extendArray(this.getGroupParams(), GroupParamsBean.class, param0));
         } else {
            _new = new GroupParamsBean[]{param0};
         }

         try {
            this.setGroupParams(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public GroupParamsBean[] getGroupParams() {
      return this._GroupParams;
   }

   public boolean isGroupParamsInherited() {
      return false;
   }

   public boolean isGroupParamsSet() {
      return this._isSet(18);
   }

   public void removeGroupParam(GroupParamsBean param0) {
      this.destroyGroupParams(param0);
   }

   public void setGroupParams(GroupParamsBean[] param0) throws InvalidAttributeValueException {
      GroupParamsBean[] param0 = param0 == null ? new GroupParamsBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      GroupParamsBean[] _oldVal = this._GroupParams;
      this._GroupParams = (GroupParamsBean[])param0;
      this._postSet(18, _oldVal, param0);
   }

   public GroupParamsBean createGroupParams(String param0) {
      GroupParamsBeanImpl _val = new GroupParamsBeanImpl(this, -1);

      try {
         _val.setSubDeploymentName(param0);
         this.addGroupParam(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyGroupParams(GroupParamsBean param0) {
      try {
         this._checkIsPotentialChild(param0, 18);
         GroupParamsBean[] _old = this.getGroupParams();
         GroupParamsBean[] _new = (GroupParamsBean[])((GroupParamsBean[])this._getHelper()._removeElement(_old, GroupParamsBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setGroupParams(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public GroupParamsBean lookupGroupParams(String param0) {
      Object[] aary = (Object[])this._GroupParams;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      GroupParamsBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (GroupParamsBeanImpl)it.previous();
      } while(!bean.getSubDeploymentName().equals(param0));

      return bean;
   }

   public DestinationBean findErrorDestination(String param0) {
      return this._customizer.findErrorDestination(param0);
   }

   public int getMessagingPerformancePreference() {
      return this._MessagingPerformancePreference;
   }

   public boolean isMessagingPerformancePreferenceInherited() {
      return false;
   }

   public boolean isMessagingPerformancePreferenceSet() {
      return this._isSet(19);
   }

   public void setMessagingPerformancePreference(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("MessagingPerformancePreference", (long)param0, 0L, 100L);
      int _oldVal = this._MessagingPerformancePreference;
      this._MessagingPerformancePreference = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String getUnitOfWorkHandlingPolicy() {
      return this._UnitOfWorkHandlingPolicy;
   }

   public boolean isUnitOfWorkHandlingPolicyInherited() {
      return false;
   }

   public boolean isUnitOfWorkHandlingPolicySet() {
      return this._isSet(20);
   }

   public void setUnitOfWorkHandlingPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"PassThrough", "SingleMessageDelivery"};
      param0 = LegalChecks.checkInEnum("UnitOfWorkHandlingPolicy", param0, _set);
      String _oldVal = this._UnitOfWorkHandlingPolicy;
      this._UnitOfWorkHandlingPolicy = param0;
      this._postSet(20, _oldVal, param0);
   }

   public int getIncompleteWorkExpirationTime() {
      return this._IncompleteWorkExpirationTime;
   }

   public boolean isIncompleteWorkExpirationTimeInherited() {
      return false;
   }

   public boolean isIncompleteWorkExpirationTimeSet() {
      return this._isSet(21);
   }

   public void setIncompleteWorkExpirationTime(int param0) throws IllegalArgumentException {
      int _oldVal = this._IncompleteWorkExpirationTime;
      this._IncompleteWorkExpirationTime = param0;
      this._postSet(21, _oldVal, param0);
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
      return super._isAnythingSet() || this.isDeliveryFailureParamsSet() || this.isDeliveryParamsOverridesSet() || this.isMessageLoggingParamsSet() || this.isMulticastSet() || this.isThresholdsSet() || this.isTopicSubscriptionParamsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._AttachSender = "supports";
               if (initOne) {
                  break;
               }
            case 6:
               this._DeliveryFailureParams = new DeliveryFailureParamsBeanImpl(this, 6);
               this._postCreate((AbstractDescriptorBean)this._DeliveryFailureParams);
               if (initOne) {
                  break;
               }
            case 5:
               this._DeliveryParamsOverrides = new DeliveryParamsOverridesBeanImpl(this, 5);
               this._postCreate((AbstractDescriptorBean)this._DeliveryParamsOverrides);
               if (initOne) {
                  break;
               }
            case 3:
               this._DestinationKeys = new String[0];
               if (initOne) {
                  break;
               }
            case 18:
               this._GroupParams = new GroupParamsBean[0];
               if (initOne) {
                  break;
               }
            case 21:
               this._IncompleteWorkExpirationTime = -1;
               if (initOne) {
                  break;
               }
            case 12:
               this._MaximumMessageSize = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 7:
               this._MessageLoggingParams = new MessageLoggingParamsBeanImpl(this, 7);
               this._postCreate((AbstractDescriptorBean)this._MessageLoggingParams);
               if (initOne) {
                  break;
               }
            case 19:
               this._MessagingPerformancePreference = 25;
               if (initOne) {
                  break;
               }
            case 16:
               this._Multicast = new MulticastParamsBeanImpl(this, 16);
               this._postCreate((AbstractDescriptorBean)this._Multicast);
               if (initOne) {
                  break;
               }
            case 13:
               this._Quota = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._SafExportPolicy = "All";
               if (initOne) {
                  break;
               }
            case 4:
               this._Thresholds = new ThresholdParamsBeanImpl(this, 4);
               this._postCreate((AbstractDescriptorBean)this._Thresholds);
               if (initOne) {
                  break;
               }
            case 17:
               this._TopicSubscriptionParams = new TopicSubscriptionParamsBeanImpl(this, 17);
               this._postCreate((AbstractDescriptorBean)this._TopicSubscriptionParams);
               if (initOne) {
                  break;
               }
            case 20:
               this._UnitOfWorkHandlingPolicy = "PassThrough";
               if (initOne) {
                  break;
               }
            case 11:
               this._ConsumptionPausedAtStartup = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._DefaultUnitOfOrder = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._InsertionPausedAtStartup = false;
               if (initOne) {
                  break;
               }
            case 9:
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

   public static class SchemaHelper2 extends NamedEntityBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("quota")) {
                  return 13;
               }
            case 6:
            case 7:
            case 8:
            case 11:
            case 14:
            case 16:
            case 18:
            case 19:
            case 24:
            case 26:
            case 30:
            default:
               break;
            case 9:
               if (s.equals("multicast")) {
                  return 16;
               }
               break;
            case 10:
               if (s.equals("thresholds")) {
                  return 4;
               }
               break;
            case 12:
               if (s.equals("group-params")) {
                  return 18;
               }
               break;
            case 13:
               if (s.equals("attach-sender")) {
                  return 8;
               }
               break;
            case 15:
               if (s.equals("destination-key")) {
                  return 3;
               }
               break;
            case 17:
               if (s.equals("saf-export-policy")) {
                  return 15;
               }
               break;
            case 20:
               if (s.equals("maximum-message-size")) {
                  return 12;
               }
               break;
            case 21:
               if (s.equals("default-unit-of-order")) {
                  return 14;
               }
               break;
            case 22:
               if (s.equals("message-logging-params")) {
                  return 7;
               }
               break;
            case 23:
               if (s.equals("delivery-failure-params")) {
                  return 6;
               }
               break;
            case 25:
               if (s.equals("delivery-params-overrides")) {
                  return 5;
               }

               if (s.equals("topic-subscription-params")) {
                  return 17;
               }
               break;
            case 27:
               if (s.equals("insertion-paused-at-startup")) {
                  return 10;
               }
               break;
            case 28:
               if (s.equals("unit-of-work-handling-policy")) {
                  return 20;
               }

               if (s.equals("production-paused-at-startup")) {
                  return 9;
               }
               break;
            case 29:
               if (s.equals("consumption-paused-at-startup")) {
                  return 11;
               }
               break;
            case 31:
               if (s.equals("incomplete-work-expiration-time")) {
                  return 21;
               }
               break;
            case 32:
               if (s.equals("messaging-performance-preference")) {
                  return 19;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new ThresholdParamsBeanImpl.SchemaHelper2();
            case 5:
               return new DeliveryParamsOverridesBeanImpl.SchemaHelper2();
            case 6:
               return new DeliveryFailureParamsBeanImpl.SchemaHelper2();
            case 7:
               return new MessageLoggingParamsBeanImpl.SchemaHelper2();
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.getSchemaHelper(propIndex);
            case 16:
               return new MulticastParamsBeanImpl.SchemaHelper2();
            case 17:
               return new TopicSubscriptionParamsBeanImpl.SchemaHelper2();
            case 18:
               return new GroupParamsBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "destination-key";
            case 4:
               return "thresholds";
            case 5:
               return "delivery-params-overrides";
            case 6:
               return "delivery-failure-params";
            case 7:
               return "message-logging-params";
            case 8:
               return "attach-sender";
            case 9:
               return "production-paused-at-startup";
            case 10:
               return "insertion-paused-at-startup";
            case 11:
               return "consumption-paused-at-startup";
            case 12:
               return "maximum-message-size";
            case 13:
               return "quota";
            case 14:
               return "default-unit-of-order";
            case 15:
               return "saf-export-policy";
            case 16:
               return "multicast";
            case 17:
               return "topic-subscription-params";
            case 18:
               return "group-params";
            case 19:
               return "messaging-performance-preference";
            case 20:
               return "unit-of-work-handling-policy";
            case 21:
               return "incomplete-work-expiration-time";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 18:
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
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.isBean(propIndex);
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
            case 5:
            case 6:
            case 7:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
               return super.isConfigurable(propIndex);
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
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

   protected static class Helper extends NamedEntityBeanImpl.Helper {
      private TemplateBeanImpl bean;

      protected Helper(TemplateBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "DestinationKeys";
            case 4:
               return "Thresholds";
            case 5:
               return "DeliveryParamsOverrides";
            case 6:
               return "DeliveryFailureParams";
            case 7:
               return "MessageLoggingParams";
            case 8:
               return "AttachSender";
            case 9:
               return "ProductionPausedAtStartup";
            case 10:
               return "InsertionPausedAtStartup";
            case 11:
               return "ConsumptionPausedAtStartup";
            case 12:
               return "MaximumMessageSize";
            case 13:
               return "Quota";
            case 14:
               return "DefaultUnitOfOrder";
            case 15:
               return "SafExportPolicy";
            case 16:
               return "Multicast";
            case 17:
               return "TopicSubscriptionParams";
            case 18:
               return "GroupParams";
            case 19:
               return "MessagingPerformancePreference";
            case 20:
               return "UnitOfWorkHandlingPolicy";
            case 21:
               return "IncompleteWorkExpirationTime";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AttachSender")) {
            return 8;
         } else if (propName.equals("DeliveryFailureParams")) {
            return 6;
         } else if (propName.equals("DeliveryParamsOverrides")) {
            return 5;
         } else if (propName.equals("DestinationKeys")) {
            return 3;
         } else if (propName.equals("GroupParams")) {
            return 18;
         } else if (propName.equals("IncompleteWorkExpirationTime")) {
            return 21;
         } else if (propName.equals("MaximumMessageSize")) {
            return 12;
         } else if (propName.equals("MessageLoggingParams")) {
            return 7;
         } else if (propName.equals("MessagingPerformancePreference")) {
            return 19;
         } else if (propName.equals("Multicast")) {
            return 16;
         } else if (propName.equals("Quota")) {
            return 13;
         } else if (propName.equals("SafExportPolicy")) {
            return 15;
         } else if (propName.equals("Thresholds")) {
            return 4;
         } else if (propName.equals("TopicSubscriptionParams")) {
            return 17;
         } else if (propName.equals("UnitOfWorkHandlingPolicy")) {
            return 20;
         } else if (propName.equals("ConsumptionPausedAtStartup")) {
            return 11;
         } else if (propName.equals("DefaultUnitOfOrder")) {
            return 14;
         } else if (propName.equals("InsertionPausedAtStartup")) {
            return 10;
         } else {
            return propName.equals("ProductionPausedAtStartup") ? 9 : super.getPropertyIndex(propName);
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

         iterators.add(new ArrayIterator(this.bean.getGroupParams()));
         if (this.bean.getMessageLoggingParams() != null) {
            iterators.add(new ArrayIterator(new MessageLoggingParamsBean[]{this.bean.getMessageLoggingParams()}));
         }

         if (this.bean.getMulticast() != null) {
            iterators.add(new ArrayIterator(new MulticastParamsBean[]{this.bean.getMulticast()}));
         }

         if (this.bean.getThresholds() != null) {
            iterators.add(new ArrayIterator(new ThresholdParamsBean[]{this.bean.getThresholds()}));
         }

         if (this.bean.getTopicSubscriptionParams() != null) {
            iterators.add(new ArrayIterator(new TopicSubscriptionParamsBean[]{this.bean.getTopicSubscriptionParams()}));
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

            childValue = 0L;

            for(int i = 0; i < this.bean.getGroupParams().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getGroupParams()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIncompleteWorkExpirationTimeSet()) {
               buf.append("IncompleteWorkExpirationTime");
               buf.append(String.valueOf(this.bean.getIncompleteWorkExpirationTime()));
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

            childValue = this.computeChildHashValue(this.bean.getMulticast());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isQuotaSet()) {
               buf.append("Quota");
               buf.append(String.valueOf(this.bean.getQuota()));
            }

            if (this.bean.isSafExportPolicySet()) {
               buf.append("SafExportPolicy");
               buf.append(String.valueOf(this.bean.getSafExportPolicy()));
            }

            childValue = this.computeChildHashValue(this.bean.getThresholds());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTopicSubscriptionParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            TemplateBeanImpl otherTyped = (TemplateBeanImpl)other;
            this.computeDiff("AttachSender", this.bean.getAttachSender(), otherTyped.getAttachSender(), true);
            this.computeSubDiff("DeliveryFailureParams", this.bean.getDeliveryFailureParams(), otherTyped.getDeliveryFailureParams());
            this.computeSubDiff("DeliveryParamsOverrides", this.bean.getDeliveryParamsOverrides(), otherTyped.getDeliveryParamsOverrides());
            this.computeDiff("DestinationKeys", this.bean.getDestinationKeys(), otherTyped.getDestinationKeys(), false);
            this.computeChildDiff("GroupParams", this.bean.getGroupParams(), otherTyped.getGroupParams(), true);
            this.computeDiff("IncompleteWorkExpirationTime", this.bean.getIncompleteWorkExpirationTime(), otherTyped.getIncompleteWorkExpirationTime(), true);
            this.computeDiff("MaximumMessageSize", this.bean.getMaximumMessageSize(), otherTyped.getMaximumMessageSize(), true);
            this.computeSubDiff("MessageLoggingParams", this.bean.getMessageLoggingParams(), otherTyped.getMessageLoggingParams());
            this.computeDiff("MessagingPerformancePreference", this.bean.getMessagingPerformancePreference(), otherTyped.getMessagingPerformancePreference(), true);
            this.computeSubDiff("Multicast", this.bean.getMulticast(), otherTyped.getMulticast());
            this.computeDiff("Quota", this.bean.getQuota(), otherTyped.getQuota(), true);
            this.computeDiff("SafExportPolicy", this.bean.getSafExportPolicy(), otherTyped.getSafExportPolicy(), false);
            this.computeSubDiff("Thresholds", this.bean.getThresholds(), otherTyped.getThresholds());
            this.computeSubDiff("TopicSubscriptionParams", this.bean.getTopicSubscriptionParams(), otherTyped.getTopicSubscriptionParams());
            this.computeDiff("UnitOfWorkHandlingPolicy", this.bean.getUnitOfWorkHandlingPolicy(), otherTyped.getUnitOfWorkHandlingPolicy(), true);
            this.computeDiff("ConsumptionPausedAtStartup", this.bean.isConsumptionPausedAtStartup(), otherTyped.isConsumptionPausedAtStartup(), false);
            this.computeDiff("DefaultUnitOfOrder", this.bean.isDefaultUnitOfOrder(), otherTyped.isDefaultUnitOfOrder(), true);
            this.computeDiff("InsertionPausedAtStartup", this.bean.isInsertionPausedAtStartup(), otherTyped.isInsertionPausedAtStartup(), false);
            this.computeDiff("ProductionPausedAtStartup", this.bean.isProductionPausedAtStartup(), otherTyped.isProductionPausedAtStartup(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TemplateBeanImpl original = (TemplateBeanImpl)event.getSourceBean();
            TemplateBeanImpl proposed = (TemplateBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AttachSender")) {
                  original.setAttachSender(proposed.getAttachSender());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("DeliveryFailureParams")) {
                  if (type == 2) {
                     original.setDeliveryFailureParams((DeliveryFailureParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getDeliveryFailureParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DeliveryFailureParams", (DescriptorBean)original.getDeliveryFailureParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("DeliveryParamsOverrides")) {
                  if (type == 2) {
                     original.setDeliveryParamsOverrides((DeliveryParamsOverridesBean)this.createCopy((AbstractDescriptorBean)proposed.getDeliveryParamsOverrides()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DeliveryParamsOverrides", (DescriptorBean)original.getDeliveryParamsOverrides());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("GroupParams")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addGroupParam((GroupParamsBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeGroupParam((GroupParamsBean)update.getRemovedObject());
                  }

                  if (original.getGroupParams() == null || original.getGroupParams().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  }
               } else if (prop.equals("IncompleteWorkExpirationTime")) {
                  original.setIncompleteWorkExpirationTime(proposed.getIncompleteWorkExpirationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("MaximumMessageSize")) {
                  original.setMaximumMessageSize(proposed.getMaximumMessageSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MessageLoggingParams")) {
                  if (type == 2) {
                     original.setMessageLoggingParams((MessageLoggingParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getMessageLoggingParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MessageLoggingParams", (DescriptorBean)original.getMessageLoggingParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("MessagingPerformancePreference")) {
                  original.setMessagingPerformancePreference(proposed.getMessagingPerformancePreference());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("Multicast")) {
                  if (type == 2) {
                     original.setMulticast((MulticastParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getMulticast()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Multicast", (DescriptorBean)original.getMulticast());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Quota")) {
                  original.setQuotaAsString(proposed.getQuotaAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("SafExportPolicy")) {
                  original.setSafExportPolicy(proposed.getSafExportPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("Thresholds")) {
                  if (type == 2) {
                     original.setThresholds((ThresholdParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getThresholds()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Thresholds", (DescriptorBean)original.getThresholds());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TopicSubscriptionParams")) {
                  if (type == 2) {
                     original.setTopicSubscriptionParams((TopicSubscriptionParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getTopicSubscriptionParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TopicSubscriptionParams", (DescriptorBean)original.getTopicSubscriptionParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("UnitOfWorkHandlingPolicy")) {
                  original.setUnitOfWorkHandlingPolicy(proposed.getUnitOfWorkHandlingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("ConsumptionPausedAtStartup")) {
                  original.setConsumptionPausedAtStartup(proposed.isConsumptionPausedAtStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("DefaultUnitOfOrder")) {
                  original.setDefaultUnitOfOrder(proposed.isDefaultUnitOfOrder());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("InsertionPausedAtStartup")) {
                  original.setInsertionPausedAtStartup(proposed.isInsertionPausedAtStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ProductionPausedAtStartup")) {
                  original.setProductionPausedAtStartup(proposed.isProductionPausedAtStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
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
            TemplateBeanImpl copy = (TemplateBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AttachSender")) && this.bean.isAttachSenderSet()) {
               copy.setAttachSender(this.bean.getAttachSender());
            }

            if ((excludeProps == null || !excludeProps.contains("DeliveryFailureParams")) && this.bean.isDeliveryFailureParamsSet() && !copy._isSet(6)) {
               Object o = this.bean.getDeliveryFailureParams();
               copy.setDeliveryFailureParams((DeliveryFailureParamsBean)null);
               copy.setDeliveryFailureParams(o == null ? null : (DeliveryFailureParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DeliveryParamsOverrides")) && this.bean.isDeliveryParamsOverridesSet() && !copy._isSet(5)) {
               Object o = this.bean.getDeliveryParamsOverrides();
               copy.setDeliveryParamsOverrides((DeliveryParamsOverridesBean)null);
               copy.setDeliveryParamsOverrides(o == null ? null : (DeliveryParamsOverridesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationKeys")) && this.bean.isDestinationKeysSet()) {
               Object o = this.bean.getDestinationKeys();
               copy.setDestinationKeys(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("GroupParams")) && this.bean.isGroupParamsSet() && !copy._isSet(18)) {
               GroupParamsBean[] oldGroupParams = this.bean.getGroupParams();
               GroupParamsBean[] newGroupParams = new GroupParamsBean[oldGroupParams.length];

               for(int i = 0; i < newGroupParams.length; ++i) {
                  newGroupParams[i] = (GroupParamsBean)((GroupParamsBean)this.createCopy((AbstractDescriptorBean)oldGroupParams[i], includeObsolete));
               }

               copy.setGroupParams(newGroupParams);
            }

            if ((excludeProps == null || !excludeProps.contains("IncompleteWorkExpirationTime")) && this.bean.isIncompleteWorkExpirationTimeSet()) {
               copy.setIncompleteWorkExpirationTime(this.bean.getIncompleteWorkExpirationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumMessageSize")) && this.bean.isMaximumMessageSizeSet()) {
               copy.setMaximumMessageSize(this.bean.getMaximumMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageLoggingParams")) && this.bean.isMessageLoggingParamsSet() && !copy._isSet(7)) {
               Object o = this.bean.getMessageLoggingParams();
               copy.setMessageLoggingParams((MessageLoggingParamsBean)null);
               copy.setMessageLoggingParams(o == null ? null : (MessageLoggingParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MessagingPerformancePreference")) && this.bean.isMessagingPerformancePreferenceSet()) {
               copy.setMessagingPerformancePreference(this.bean.getMessagingPerformancePreference());
            }

            if ((excludeProps == null || !excludeProps.contains("Multicast")) && this.bean.isMulticastSet() && !copy._isSet(16)) {
               Object o = this.bean.getMulticast();
               copy.setMulticast((MulticastParamsBean)null);
               copy.setMulticast(o == null ? null : (MulticastParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Quota")) && this.bean.isQuotaSet()) {
               copy._unSet(copy, 13);
               copy.setQuotaAsString(this.bean.getQuotaAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("SafExportPolicy")) && this.bean.isSafExportPolicySet()) {
               copy.setSafExportPolicy(this.bean.getSafExportPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Thresholds")) && this.bean.isThresholdsSet() && !copy._isSet(4)) {
               Object o = this.bean.getThresholds();
               copy.setThresholds((ThresholdParamsBean)null);
               copy.setThresholds(o == null ? null : (ThresholdParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TopicSubscriptionParams")) && this.bean.isTopicSubscriptionParamsSet() && !copy._isSet(17)) {
               Object o = this.bean.getTopicSubscriptionParams();
               copy.setTopicSubscriptionParams((TopicSubscriptionParamsBean)null);
               copy.setTopicSubscriptionParams(o == null ? null : (TopicSubscriptionParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getDeliveryFailureParams(), clazz, annotation);
         this.inferSubTree(this.bean.getDeliveryParamsOverrides(), clazz, annotation);
         this.inferSubTree(this.bean.getGroupParams(), clazz, annotation);
         this.inferSubTree(this.bean.getMessageLoggingParams(), clazz, annotation);
         this.inferSubTree(this.bean.getMulticast(), clazz, annotation);
         this.inferSubTree(this.bean.getQuota(), clazz, annotation);
         this.inferSubTree(this.bean.getThresholds(), clazz, annotation);
         this.inferSubTree(this.bean.getTopicSubscriptionParams(), clazz, annotation);
      }
   }
}

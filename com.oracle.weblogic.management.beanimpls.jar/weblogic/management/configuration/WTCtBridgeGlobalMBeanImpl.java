package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class WTCtBridgeGlobalMBeanImpl extends ConfigurationMBeanImpl implements WTCtBridgeGlobalMBean, Serializable {
   private String _AllowNonStandardTypes;
   private String _DefaultReplyDeliveryMode;
   private String _DeliveryModeOverride;
   private String _JmsFactory;
   private String _JmsToTuxPriorityMap;
   private String _JndiFactory;
   private int _Retries;
   private int _RetryDelay;
   private int _Timeout;
   private String _Transactional;
   private String _TuxErrorQueue;
   private String _TuxFactory;
   private String _TuxToJmsPriorityMap;
   private String _UserId;
   private String _WlsErrorDestination;
   private static SchemaHelper2 _schemaHelper;

   public WTCtBridgeGlobalMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WTCtBridgeGlobalMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WTCtBridgeGlobalMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setTransactional(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Yes", "No"};
      param0 = LegalChecks.checkInEnum("Transactional", param0, _set);
      String _oldVal = this._Transactional;
      this._Transactional = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getTransactional() {
      return this._Transactional;
   }

   public boolean isTransactionalInherited() {
      return false;
   }

   public boolean isTransactionalSet() {
      return this._isSet(10);
   }

   public void setTimeout(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("Timeout", (long)param0, 0L, 2147483647L);
      int _oldVal = this._Timeout;
      this._Timeout = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getTimeout() {
      return this._Timeout;
   }

   public boolean isTimeoutInherited() {
      return false;
   }

   public boolean isTimeoutSet() {
      return this._isSet(11);
   }

   public void setRetries(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("Retries", (long)param0, 0L, 2147483647L);
      int _oldVal = this._Retries;
      this._Retries = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getRetries() {
      return this._Retries;
   }

   public boolean isRetriesInherited() {
      return false;
   }

   public boolean isRetriesSet() {
      return this._isSet(12);
   }

   public void setRetryDelay(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("RetryDelay", (long)param0, 0L, 2147483647L);
      int _oldVal = this._RetryDelay;
      this._RetryDelay = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getRetryDelay() {
      return this._RetryDelay;
   }

   public boolean isRetryDelayInherited() {
      return false;
   }

   public boolean isRetryDelaySet() {
      return this._isSet(13);
   }

   public void setWlsErrorDestination(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WlsErrorDestination;
      this._WlsErrorDestination = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getWlsErrorDestination() {
      return this._WlsErrorDestination;
   }

   public boolean isWlsErrorDestinationInherited() {
      return false;
   }

   public boolean isWlsErrorDestinationSet() {
      return this._isSet(14);
   }

   public void setTuxErrorQueue(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TuxErrorQueue;
      this._TuxErrorQueue = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getTuxErrorQueue() {
      return this._TuxErrorQueue;
   }

   public boolean isTuxErrorQueueInherited() {
      return false;
   }

   public boolean isTuxErrorQueueSet() {
      return this._isSet(15);
   }

   public void setDeliveryModeOverride(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"PERSIST", "NONPERSIST"};
      param0 = LegalChecks.checkInEnum("DeliveryModeOverride", param0, _set);
      String _oldVal = this._DeliveryModeOverride;
      this._DeliveryModeOverride = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getDeliveryModeOverride() {
      return this._DeliveryModeOverride;
   }

   public boolean isDeliveryModeOverrideInherited() {
      return false;
   }

   public boolean isDeliveryModeOverrideSet() {
      return this._isSet(16);
   }

   public void setDefaultReplyDeliveryMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"PERSIST", "NONPERSIST", "DEFAULT"};
      param0 = LegalChecks.checkInEnum("DefaultReplyDeliveryMode", param0, _set);
      String _oldVal = this._DefaultReplyDeliveryMode;
      this._DefaultReplyDeliveryMode = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getDefaultReplyDeliveryMode() {
      return this._DefaultReplyDeliveryMode;
   }

   public boolean isDefaultReplyDeliveryModeInherited() {
      return false;
   }

   public boolean isDefaultReplyDeliveryModeSet() {
      return this._isSet(17);
   }

   public void setUserId(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UserId;
      this._UserId = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getUserId() {
      return this._UserId;
   }

   public boolean isUserIdInherited() {
      return false;
   }

   public boolean isUserIdSet() {
      return this._isSet(18);
   }

   public void setAllowNonStandardTypes(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Yes", "No"};
      param0 = LegalChecks.checkInEnum("AllowNonStandardTypes", param0, _set);
      String _oldVal = this._AllowNonStandardTypes;
      this._AllowNonStandardTypes = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String getAllowNonStandardTypes() {
      return this._AllowNonStandardTypes;
   }

   public boolean isAllowNonStandardTypesInherited() {
      return false;
   }

   public boolean isAllowNonStandardTypesSet() {
      return this._isSet(19);
   }

   public void setJndiFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("JndiFactory", param0);
      String _oldVal = this._JndiFactory;
      this._JndiFactory = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getJndiFactory() {
      return this._JndiFactory;
   }

   public boolean isJndiFactoryInherited() {
      return false;
   }

   public boolean isJndiFactorySet() {
      return this._isSet(20);
   }

   public void setJmsFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("JmsFactory", param0);
      String _oldVal = this._JmsFactory;
      this._JmsFactory = param0;
      this._postSet(21, _oldVal, param0);
   }

   public String getJmsFactory() {
      return this._JmsFactory;
   }

   public boolean isJmsFactoryInherited() {
      return false;
   }

   public boolean isJmsFactorySet() {
      return this._isSet(21);
   }

   public void setTuxFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("TuxFactory", param0);
      String _oldVal = this._TuxFactory;
      this._TuxFactory = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getTuxFactory() {
      return this._TuxFactory;
   }

   public boolean isTuxFactoryInherited() {
      return false;
   }

   public boolean isTuxFactorySet() {
      return this._isSet(22);
   }

   public void setJmsToTuxPriorityMap(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JmsToTuxPriorityMap;
      this._JmsToTuxPriorityMap = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getJmsToTuxPriorityMap() {
      return this._JmsToTuxPriorityMap;
   }

   public boolean isJmsToTuxPriorityMapInherited() {
      return false;
   }

   public boolean isJmsToTuxPriorityMapSet() {
      return this._isSet(23);
   }

   public void setTuxToJmsPriorityMap(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TuxToJmsPriorityMap;
      this._TuxToJmsPriorityMap = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String getTuxToJmsPriorityMap() {
      return this._TuxToJmsPriorityMap;
   }

   public boolean isTuxToJmsPriorityMapInherited() {
      return false;
   }

   public boolean isTuxToJmsPriorityMapSet() {
      return this._isSet(24);
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
         idx = 19;
      }

      try {
         switch (idx) {
            case 19:
               this._AllowNonStandardTypes = "NO";
               if (initOne) {
                  break;
               }
            case 17:
               this._DefaultReplyDeliveryMode = "DEFAULT";
               if (initOne) {
                  break;
               }
            case 16:
               this._DeliveryModeOverride = "NONPERSIST";
               if (initOne) {
                  break;
               }
            case 21:
               this._JmsFactory = "weblogic.jms.XAConnectionFactory";
               if (initOne) {
                  break;
               }
            case 23:
               this._JmsToTuxPriorityMap = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._JndiFactory = "weblogic.jndi.WLInitialContextFactory";
               if (initOne) {
                  break;
               }
            case 12:
               this._Retries = 0;
               if (initOne) {
                  break;
               }
            case 13:
               this._RetryDelay = 10;
               if (initOne) {
                  break;
               }
            case 11:
               this._Timeout = 60;
               if (initOne) {
                  break;
               }
            case 10:
               this._Transactional = "NO";
               if (initOne) {
                  break;
               }
            case 15:
               this._TuxErrorQueue = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._TuxFactory = "tuxedo.services.TuxedoConnection";
               if (initOne) {
                  break;
               }
            case 24:
               this._TuxToJmsPriorityMap = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._UserId = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._WlsErrorDestination = null;
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
      return "WTCtBridgeGlobal";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AllowNonStandardTypes")) {
         oldVal = this._AllowNonStandardTypes;
         this._AllowNonStandardTypes = (String)v;
         this._postSet(19, oldVal, this._AllowNonStandardTypes);
      } else if (name.equals("DefaultReplyDeliveryMode")) {
         oldVal = this._DefaultReplyDeliveryMode;
         this._DefaultReplyDeliveryMode = (String)v;
         this._postSet(17, oldVal, this._DefaultReplyDeliveryMode);
      } else if (name.equals("DeliveryModeOverride")) {
         oldVal = this._DeliveryModeOverride;
         this._DeliveryModeOverride = (String)v;
         this._postSet(16, oldVal, this._DeliveryModeOverride);
      } else if (name.equals("JmsFactory")) {
         oldVal = this._JmsFactory;
         this._JmsFactory = (String)v;
         this._postSet(21, oldVal, this._JmsFactory);
      } else if (name.equals("JmsToTuxPriorityMap")) {
         oldVal = this._JmsToTuxPriorityMap;
         this._JmsToTuxPriorityMap = (String)v;
         this._postSet(23, oldVal, this._JmsToTuxPriorityMap);
      } else if (name.equals("JndiFactory")) {
         oldVal = this._JndiFactory;
         this._JndiFactory = (String)v;
         this._postSet(20, oldVal, this._JndiFactory);
      } else {
         int oldVal;
         if (name.equals("Retries")) {
            oldVal = this._Retries;
            this._Retries = (Integer)v;
            this._postSet(12, oldVal, this._Retries);
         } else if (name.equals("RetryDelay")) {
            oldVal = this._RetryDelay;
            this._RetryDelay = (Integer)v;
            this._postSet(13, oldVal, this._RetryDelay);
         } else if (name.equals("Timeout")) {
            oldVal = this._Timeout;
            this._Timeout = (Integer)v;
            this._postSet(11, oldVal, this._Timeout);
         } else if (name.equals("Transactional")) {
            oldVal = this._Transactional;
            this._Transactional = (String)v;
            this._postSet(10, oldVal, this._Transactional);
         } else if (name.equals("TuxErrorQueue")) {
            oldVal = this._TuxErrorQueue;
            this._TuxErrorQueue = (String)v;
            this._postSet(15, oldVal, this._TuxErrorQueue);
         } else if (name.equals("TuxFactory")) {
            oldVal = this._TuxFactory;
            this._TuxFactory = (String)v;
            this._postSet(22, oldVal, this._TuxFactory);
         } else if (name.equals("TuxToJmsPriorityMap")) {
            oldVal = this._TuxToJmsPriorityMap;
            this._TuxToJmsPriorityMap = (String)v;
            this._postSet(24, oldVal, this._TuxToJmsPriorityMap);
         } else if (name.equals("UserId")) {
            oldVal = this._UserId;
            this._UserId = (String)v;
            this._postSet(18, oldVal, this._UserId);
         } else if (name.equals("WlsErrorDestination")) {
            oldVal = this._WlsErrorDestination;
            this._WlsErrorDestination = (String)v;
            this._postSet(14, oldVal, this._WlsErrorDestination);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AllowNonStandardTypes")) {
         return this._AllowNonStandardTypes;
      } else if (name.equals("DefaultReplyDeliveryMode")) {
         return this._DefaultReplyDeliveryMode;
      } else if (name.equals("DeliveryModeOverride")) {
         return this._DeliveryModeOverride;
      } else if (name.equals("JmsFactory")) {
         return this._JmsFactory;
      } else if (name.equals("JmsToTuxPriorityMap")) {
         return this._JmsToTuxPriorityMap;
      } else if (name.equals("JndiFactory")) {
         return this._JndiFactory;
      } else if (name.equals("Retries")) {
         return new Integer(this._Retries);
      } else if (name.equals("RetryDelay")) {
         return new Integer(this._RetryDelay);
      } else if (name.equals("Timeout")) {
         return new Integer(this._Timeout);
      } else if (name.equals("Transactional")) {
         return this._Transactional;
      } else if (name.equals("TuxErrorQueue")) {
         return this._TuxErrorQueue;
      } else if (name.equals("TuxFactory")) {
         return this._TuxFactory;
      } else if (name.equals("TuxToJmsPriorityMap")) {
         return this._TuxToJmsPriorityMap;
      } else if (name.equals("UserId")) {
         return this._UserId;
      } else {
         return name.equals("WlsErrorDestination") ? this._WlsErrorDestination : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      String[] _set;
      try {
         _set = new String[]{"Yes", "No"};
         LegalChecks.checkInEnum("AllowNonStandardTypes", "NO", _set);
      } catch (IllegalArgumentException var5) {
         throw new DescriptorValidateException("Default value for a property  should be one of the legal values. Refer annotation legalValues on property AllowNonStandardTypes in WTCtBridgeGlobalMBean" + var5.getMessage());
      }

      try {
         LegalChecks.checkNonNull("JmsFactory", "weblogic.jms.XAConnectionFactory");
      } catch (IllegalArgumentException var4) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property JmsFactory in WTCtBridgeGlobalMBean" + var4.getMessage());
      }

      try {
         LegalChecks.checkNonNull("JndiFactory", "weblogic.jndi.WLInitialContextFactory");
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property JndiFactory in WTCtBridgeGlobalMBean" + var3.getMessage());
      }

      try {
         _set = new String[]{"Yes", "No"};
         LegalChecks.checkInEnum("Transactional", "NO", _set);
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("Default value for a property  should be one of the legal values. Refer annotation legalValues on property Transactional in WTCtBridgeGlobalMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("TuxFactory", "tuxedo.services.TuxedoConnection");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property TuxFactory in WTCtBridgeGlobalMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("retries")) {
                  return 12;
               }

               if (s.equals("timeout")) {
                  return 11;
               }

               if (s.equals("user-id")) {
                  return 18;
               }
            case 8:
            case 9:
            case 10:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 25:
            case 26:
            default:
               break;
            case 11:
               if (s.equals("jms-factory")) {
                  return 21;
               }

               if (s.equals("retry-delay")) {
                  return 13;
               }

               if (s.equals("tux-factory")) {
                  return 22;
               }
               break;
            case 12:
               if (s.equals("jndi-factory")) {
                  return 20;
               }
               break;
            case 13:
               if (s.equals("transactional")) {
                  return 10;
               }
               break;
            case 15:
               if (s.equals("tux-error-queue")) {
                  return 15;
               }
               break;
            case 21:
               if (s.equals("wls-error-destination")) {
                  return 14;
               }
               break;
            case 22:
               if (s.equals("delivery-mode-override")) {
                  return 16;
               }
               break;
            case 23:
               if (s.equals("jms-to-tux-priority-map")) {
                  return 23;
               }

               if (s.equals("tux-to-jms-priority-map")) {
                  return 24;
               }
               break;
            case 24:
               if (s.equals("allow-non-standard-types")) {
                  return 19;
               }
               break;
            case 27:
               if (s.equals("default-reply-delivery-mode")) {
                  return 17;
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
               return "transactional";
            case 11:
               return "timeout";
            case 12:
               return "retries";
            case 13:
               return "retry-delay";
            case 14:
               return "wls-error-destination";
            case 15:
               return "tux-error-queue";
            case 16:
               return "delivery-mode-override";
            case 17:
               return "default-reply-delivery-mode";
            case 18:
               return "user-id";
            case 19:
               return "allow-non-standard-types";
            case 20:
               return "jndi-factory";
            case 21:
               return "jms-factory";
            case 22:
               return "tux-factory";
            case 23:
               return "jms-to-tux-priority-map";
            case 24:
               return "tux-to-jms-priority-map";
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
      private WTCtBridgeGlobalMBeanImpl bean;

      protected Helper(WTCtBridgeGlobalMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Transactional";
            case 11:
               return "Timeout";
            case 12:
               return "Retries";
            case 13:
               return "RetryDelay";
            case 14:
               return "WlsErrorDestination";
            case 15:
               return "TuxErrorQueue";
            case 16:
               return "DeliveryModeOverride";
            case 17:
               return "DefaultReplyDeliveryMode";
            case 18:
               return "UserId";
            case 19:
               return "AllowNonStandardTypes";
            case 20:
               return "JndiFactory";
            case 21:
               return "JmsFactory";
            case 22:
               return "TuxFactory";
            case 23:
               return "JmsToTuxPriorityMap";
            case 24:
               return "TuxToJmsPriorityMap";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllowNonStandardTypes")) {
            return 19;
         } else if (propName.equals("DefaultReplyDeliveryMode")) {
            return 17;
         } else if (propName.equals("DeliveryModeOverride")) {
            return 16;
         } else if (propName.equals("JmsFactory")) {
            return 21;
         } else if (propName.equals("JmsToTuxPriorityMap")) {
            return 23;
         } else if (propName.equals("JndiFactory")) {
            return 20;
         } else if (propName.equals("Retries")) {
            return 12;
         } else if (propName.equals("RetryDelay")) {
            return 13;
         } else if (propName.equals("Timeout")) {
            return 11;
         } else if (propName.equals("Transactional")) {
            return 10;
         } else if (propName.equals("TuxErrorQueue")) {
            return 15;
         } else if (propName.equals("TuxFactory")) {
            return 22;
         } else if (propName.equals("TuxToJmsPriorityMap")) {
            return 24;
         } else if (propName.equals("UserId")) {
            return 18;
         } else {
            return propName.equals("WlsErrorDestination") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isAllowNonStandardTypesSet()) {
               buf.append("AllowNonStandardTypes");
               buf.append(String.valueOf(this.bean.getAllowNonStandardTypes()));
            }

            if (this.bean.isDefaultReplyDeliveryModeSet()) {
               buf.append("DefaultReplyDeliveryMode");
               buf.append(String.valueOf(this.bean.getDefaultReplyDeliveryMode()));
            }

            if (this.bean.isDeliveryModeOverrideSet()) {
               buf.append("DeliveryModeOverride");
               buf.append(String.valueOf(this.bean.getDeliveryModeOverride()));
            }

            if (this.bean.isJmsFactorySet()) {
               buf.append("JmsFactory");
               buf.append(String.valueOf(this.bean.getJmsFactory()));
            }

            if (this.bean.isJmsToTuxPriorityMapSet()) {
               buf.append("JmsToTuxPriorityMap");
               buf.append(String.valueOf(this.bean.getJmsToTuxPriorityMap()));
            }

            if (this.bean.isJndiFactorySet()) {
               buf.append("JndiFactory");
               buf.append(String.valueOf(this.bean.getJndiFactory()));
            }

            if (this.bean.isRetriesSet()) {
               buf.append("Retries");
               buf.append(String.valueOf(this.bean.getRetries()));
            }

            if (this.bean.isRetryDelaySet()) {
               buf.append("RetryDelay");
               buf.append(String.valueOf(this.bean.getRetryDelay()));
            }

            if (this.bean.isTimeoutSet()) {
               buf.append("Timeout");
               buf.append(String.valueOf(this.bean.getTimeout()));
            }

            if (this.bean.isTransactionalSet()) {
               buf.append("Transactional");
               buf.append(String.valueOf(this.bean.getTransactional()));
            }

            if (this.bean.isTuxErrorQueueSet()) {
               buf.append("TuxErrorQueue");
               buf.append(String.valueOf(this.bean.getTuxErrorQueue()));
            }

            if (this.bean.isTuxFactorySet()) {
               buf.append("TuxFactory");
               buf.append(String.valueOf(this.bean.getTuxFactory()));
            }

            if (this.bean.isTuxToJmsPriorityMapSet()) {
               buf.append("TuxToJmsPriorityMap");
               buf.append(String.valueOf(this.bean.getTuxToJmsPriorityMap()));
            }

            if (this.bean.isUserIdSet()) {
               buf.append("UserId");
               buf.append(String.valueOf(this.bean.getUserId()));
            }

            if (this.bean.isWlsErrorDestinationSet()) {
               buf.append("WlsErrorDestination");
               buf.append(String.valueOf(this.bean.getWlsErrorDestination()));
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
            WTCtBridgeGlobalMBeanImpl otherTyped = (WTCtBridgeGlobalMBeanImpl)other;
            this.computeDiff("AllowNonStandardTypes", this.bean.getAllowNonStandardTypes(), otherTyped.getAllowNonStandardTypes(), true);
            this.computeDiff("DefaultReplyDeliveryMode", this.bean.getDefaultReplyDeliveryMode(), otherTyped.getDefaultReplyDeliveryMode(), true);
            this.computeDiff("DeliveryModeOverride", this.bean.getDeliveryModeOverride(), otherTyped.getDeliveryModeOverride(), true);
            this.computeDiff("JmsFactory", this.bean.getJmsFactory(), otherTyped.getJmsFactory(), true);
            this.computeDiff("JmsToTuxPriorityMap", this.bean.getJmsToTuxPriorityMap(), otherTyped.getJmsToTuxPriorityMap(), true);
            this.computeDiff("JndiFactory", this.bean.getJndiFactory(), otherTyped.getJndiFactory(), true);
            this.computeDiff("Retries", this.bean.getRetries(), otherTyped.getRetries(), true);
            this.computeDiff("RetryDelay", this.bean.getRetryDelay(), otherTyped.getRetryDelay(), true);
            this.computeDiff("Timeout", this.bean.getTimeout(), otherTyped.getTimeout(), true);
            this.computeDiff("Transactional", this.bean.getTransactional(), otherTyped.getTransactional(), true);
            this.computeDiff("TuxErrorQueue", this.bean.getTuxErrorQueue(), otherTyped.getTuxErrorQueue(), true);
            this.computeDiff("TuxFactory", this.bean.getTuxFactory(), otherTyped.getTuxFactory(), true);
            this.computeDiff("TuxToJmsPriorityMap", this.bean.getTuxToJmsPriorityMap(), otherTyped.getTuxToJmsPriorityMap(), true);
            this.computeDiff("UserId", this.bean.getUserId(), otherTyped.getUserId(), true);
            this.computeDiff("WlsErrorDestination", this.bean.getWlsErrorDestination(), otherTyped.getWlsErrorDestination(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WTCtBridgeGlobalMBeanImpl original = (WTCtBridgeGlobalMBeanImpl)event.getSourceBean();
            WTCtBridgeGlobalMBeanImpl proposed = (WTCtBridgeGlobalMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AllowNonStandardTypes")) {
                  original.setAllowNonStandardTypes(proposed.getAllowNonStandardTypes());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("DefaultReplyDeliveryMode")) {
                  original.setDefaultReplyDeliveryMode(proposed.getDefaultReplyDeliveryMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("DeliveryModeOverride")) {
                  original.setDeliveryModeOverride(proposed.getDeliveryModeOverride());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("JmsFactory")) {
                  original.setJmsFactory(proposed.getJmsFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("JmsToTuxPriorityMap")) {
                  original.setJmsToTuxPriorityMap(proposed.getJmsToTuxPriorityMap());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("JndiFactory")) {
                  original.setJndiFactory(proposed.getJndiFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("Retries")) {
                  original.setRetries(proposed.getRetries());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("RetryDelay")) {
                  original.setRetryDelay(proposed.getRetryDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Timeout")) {
                  original.setTimeout(proposed.getTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Transactional")) {
                  original.setTransactional(proposed.getTransactional());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("TuxErrorQueue")) {
                  original.setTuxErrorQueue(proposed.getTuxErrorQueue());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("TuxFactory")) {
                  original.setTuxFactory(proposed.getTuxFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("TuxToJmsPriorityMap")) {
                  original.setTuxToJmsPriorityMap(proposed.getTuxToJmsPriorityMap());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("UserId")) {
                  original.setUserId(proposed.getUserId());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("WlsErrorDestination")) {
                  original.setWlsErrorDestination(proposed.getWlsErrorDestination());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
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
            WTCtBridgeGlobalMBeanImpl copy = (WTCtBridgeGlobalMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AllowNonStandardTypes")) && this.bean.isAllowNonStandardTypesSet()) {
               copy.setAllowNonStandardTypes(this.bean.getAllowNonStandardTypes());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultReplyDeliveryMode")) && this.bean.isDefaultReplyDeliveryModeSet()) {
               copy.setDefaultReplyDeliveryMode(this.bean.getDefaultReplyDeliveryMode());
            }

            if ((excludeProps == null || !excludeProps.contains("DeliveryModeOverride")) && this.bean.isDeliveryModeOverrideSet()) {
               copy.setDeliveryModeOverride(this.bean.getDeliveryModeOverride());
            }

            if ((excludeProps == null || !excludeProps.contains("JmsFactory")) && this.bean.isJmsFactorySet()) {
               copy.setJmsFactory(this.bean.getJmsFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("JmsToTuxPriorityMap")) && this.bean.isJmsToTuxPriorityMapSet()) {
               copy.setJmsToTuxPriorityMap(this.bean.getJmsToTuxPriorityMap());
            }

            if ((excludeProps == null || !excludeProps.contains("JndiFactory")) && this.bean.isJndiFactorySet()) {
               copy.setJndiFactory(this.bean.getJndiFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("Retries")) && this.bean.isRetriesSet()) {
               copy.setRetries(this.bean.getRetries());
            }

            if ((excludeProps == null || !excludeProps.contains("RetryDelay")) && this.bean.isRetryDelaySet()) {
               copy.setRetryDelay(this.bean.getRetryDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("Timeout")) && this.bean.isTimeoutSet()) {
               copy.setTimeout(this.bean.getTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("Transactional")) && this.bean.isTransactionalSet()) {
               copy.setTransactional(this.bean.getTransactional());
            }

            if ((excludeProps == null || !excludeProps.contains("TuxErrorQueue")) && this.bean.isTuxErrorQueueSet()) {
               copy.setTuxErrorQueue(this.bean.getTuxErrorQueue());
            }

            if ((excludeProps == null || !excludeProps.contains("TuxFactory")) && this.bean.isTuxFactorySet()) {
               copy.setTuxFactory(this.bean.getTuxFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("TuxToJmsPriorityMap")) && this.bean.isTuxToJmsPriorityMapSet()) {
               copy.setTuxToJmsPriorityMap(this.bean.getTuxToJmsPriorityMap());
            }

            if ((excludeProps == null || !excludeProps.contains("UserId")) && this.bean.isUserIdSet()) {
               copy.setUserId(this.bean.getUserId());
            }

            if ((excludeProps == null || !excludeProps.contains("WlsErrorDestination")) && this.bean.isWlsErrorDestinationSet()) {
               copy.setWlsErrorDestination(this.bean.getWlsErrorDestination());
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

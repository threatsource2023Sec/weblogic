package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SoapjmsServiceEndpointAddressBeanImpl extends AbstractDescriptorBean implements SoapjmsServiceEndpointAddressBean, Serializable {
   private String _ActivationConfig;
   private String _BindingVersion;
   private String _DeliveryMode;
   private String _DestinationName;
   private String _DestinationType;
   private boolean _EnableHttpWsdlAccess;
   private String _JmsMessageHeader;
   private String _JmsMessageProperty;
   private String _JndiConnectionFactoryName;
   private String _JndiContextParameter;
   private String _JndiInitialContextFactory;
   private String _JndiUrl;
   private String _LookupVariant;
   private boolean _MdbPerDestination;
   private String _MessageType;
   private int _Priority;
   private String _ReplyToName;
   private String _RunAsPrincipal;
   private String _RunAsRole;
   private String _TargetService;
   private long _TimeToLive;
   private static SchemaHelper2 _schemaHelper;

   public SoapjmsServiceEndpointAddressBeanImpl() {
      this._initializeProperty(-1);
   }

   public SoapjmsServiceEndpointAddressBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SoapjmsServiceEndpointAddressBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getLookupVariant() {
      return this._LookupVariant;
   }

   public boolean isLookupVariantInherited() {
      return false;
   }

   public boolean isLookupVariantSet() {
      return this._isSet(0);
   }

   public void setLookupVariant(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"jndi"};
      param0 = LegalChecks.checkInEnum("LookupVariant", param0, _set);
      String _oldVal = this._LookupVariant;
      this._LookupVariant = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDestinationName() {
      return this._DestinationName;
   }

   public boolean isDestinationNameInherited() {
      return false;
   }

   public boolean isDestinationNameSet() {
      return this._isSet(1);
   }

   public void setDestinationName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DestinationName;
      this._DestinationName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getDestinationType() {
      return this._DestinationType;
   }

   public boolean isDestinationTypeInherited() {
      return false;
   }

   public boolean isDestinationTypeSet() {
      return this._isSet(2);
   }

   public void setDestinationType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"QUEUE", "TOPIC"};
      param0 = LegalChecks.checkInEnum("DestinationType", param0, _set);
      String _oldVal = this._DestinationType;
      this._DestinationType = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getJndiConnectionFactoryName() {
      return this._JndiConnectionFactoryName;
   }

   public boolean isJndiConnectionFactoryNameInherited() {
      return false;
   }

   public boolean isJndiConnectionFactoryNameSet() {
      return this._isSet(3);
   }

   public void setJndiConnectionFactoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JndiConnectionFactoryName;
      this._JndiConnectionFactoryName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getJndiInitialContextFactory() {
      return this._JndiInitialContextFactory;
   }

   public boolean isJndiInitialContextFactoryInherited() {
      return false;
   }

   public boolean isJndiInitialContextFactorySet() {
      return this._isSet(4);
   }

   public void setJndiInitialContextFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JndiInitialContextFactory;
      this._JndiInitialContextFactory = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getJndiUrl() {
      return this._JndiUrl;
   }

   public boolean isJndiUrlInherited() {
      return false;
   }

   public boolean isJndiUrlSet() {
      return this._isSet(5);
   }

   public void setJndiUrl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JndiUrl;
      this._JndiUrl = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getJndiContextParameter() {
      return this._JndiContextParameter;
   }

   public boolean isJndiContextParameterInherited() {
      return false;
   }

   public boolean isJndiContextParameterSet() {
      return this._isSet(6);
   }

   public void setJndiContextParameter(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JndiContextParameter;
      this._JndiContextParameter = param0;
      this._postSet(6, _oldVal, param0);
   }

   public long getTimeToLive() {
      return this._TimeToLive;
   }

   public boolean isTimeToLiveInherited() {
      return false;
   }

   public boolean isTimeToLiveSet() {
      return this._isSet(7);
   }

   public void setTimeToLive(long param0) {
      long _oldVal = this._TimeToLive;
      this._TimeToLive = param0;
      this._postSet(7, _oldVal, param0);
   }

   public int getPriority() {
      return this._Priority;
   }

   public boolean isPriorityInherited() {
      return false;
   }

   public boolean isPrioritySet() {
      return this._isSet(8);
   }

   public void setPriority(int param0) {
      LegalChecks.checkInRange("Priority", (long)param0, 0L, 9L);
      int _oldVal = this._Priority;
      this._Priority = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getDeliveryMode() {
      return this._DeliveryMode;
   }

   public boolean isDeliveryModeInherited() {
      return false;
   }

   public boolean isDeliveryModeSet() {
      return this._isSet(9);
   }

   public void setDeliveryMode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"PERSISTENT", "NON_PERSISTENT"};
      param0 = LegalChecks.checkInEnum("DeliveryMode", param0, _set);
      String _oldVal = this._DeliveryMode;
      this._DeliveryMode = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getReplyToName() {
      return this._ReplyToName;
   }

   public boolean isReplyToNameInherited() {
      return false;
   }

   public boolean isReplyToNameSet() {
      return this._isSet(10);
   }

   public void setReplyToName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ReplyToName;
      this._ReplyToName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getTargetService() {
      return this._TargetService;
   }

   public boolean isTargetServiceInherited() {
      return false;
   }

   public boolean isTargetServiceSet() {
      return this._isSet(11);
   }

   public void setTargetService(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TargetService;
      this._TargetService = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getBindingVersion() {
      return this._BindingVersion;
   }

   public boolean isBindingVersionInherited() {
      return false;
   }

   public boolean isBindingVersionSet() {
      return this._isSet(12);
   }

   public void setBindingVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._BindingVersion;
      this._BindingVersion = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getMessageType() {
      return this._MessageType;
   }

   public boolean isMessageTypeInherited() {
      return false;
   }

   public boolean isMessageTypeSet() {
      return this._isSet(13);
   }

   public void setMessageType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"BYTES", "TEXT"};
      param0 = LegalChecks.checkInEnum("MessageType", param0, _set);
      String _oldVal = this._MessageType;
      this._MessageType = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isEnableHttpWsdlAccess() {
      return this._EnableHttpWsdlAccess;
   }

   public boolean isEnableHttpWsdlAccessInherited() {
      return false;
   }

   public boolean isEnableHttpWsdlAccessSet() {
      return this._isSet(14);
   }

   public void setEnableHttpWsdlAccess(boolean param0) {
      boolean _oldVal = this._EnableHttpWsdlAccess;
      this._EnableHttpWsdlAccess = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getRunAsPrincipal() {
      return this._RunAsPrincipal;
   }

   public boolean isRunAsPrincipalInherited() {
      return false;
   }

   public boolean isRunAsPrincipalSet() {
      return this._isSet(15);
   }

   public void setRunAsPrincipal(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RunAsPrincipal;
      this._RunAsPrincipal = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getRunAsRole() {
      return this._RunAsRole;
   }

   public boolean isRunAsRoleInherited() {
      return false;
   }

   public boolean isRunAsRoleSet() {
      return this._isSet(16);
   }

   public void setRunAsRole(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RunAsRole;
      this._RunAsRole = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean isMdbPerDestination() {
      return this._MdbPerDestination;
   }

   public boolean isMdbPerDestinationInherited() {
      return false;
   }

   public boolean isMdbPerDestinationSet() {
      return this._isSet(17);
   }

   public void setMdbPerDestination(boolean param0) {
      boolean _oldVal = this._MdbPerDestination;
      this._MdbPerDestination = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getActivationConfig() {
      return this._ActivationConfig;
   }

   public boolean isActivationConfigInherited() {
      return false;
   }

   public boolean isActivationConfigSet() {
      return this._isSet(18);
   }

   public void setActivationConfig(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ActivationConfig;
      this._ActivationConfig = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getJmsMessageHeader() {
      return this._JmsMessageHeader;
   }

   public boolean isJmsMessageHeaderInherited() {
      return false;
   }

   public boolean isJmsMessageHeaderSet() {
      return this._isSet(19);
   }

   public void setJmsMessageHeader(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JmsMessageHeader;
      this._JmsMessageHeader = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String getJmsMessageProperty() {
      return this._JmsMessageProperty;
   }

   public boolean isJmsMessagePropertyInherited() {
      return false;
   }

   public boolean isJmsMessagePropertySet() {
      return this._isSet(20);
   }

   public void setJmsMessageProperty(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JmsMessageProperty;
      this._JmsMessageProperty = param0;
      this._postSet(20, _oldVal, param0);
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
         idx = 18;
      }

      try {
         switch (idx) {
            case 18:
               this._ActivationConfig = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._BindingVersion = "1.0";
               if (initOne) {
                  break;
               }
            case 9:
               this._DeliveryMode = "PERSISTENT";
               if (initOne) {
                  break;
               }
            case 1:
               this._DestinationName = "com.oracle.webservices.jms.RequestQueue";
               if (initOne) {
                  break;
               }
            case 2:
               this._DestinationType = "QUEUE";
               if (initOne) {
                  break;
               }
            case 19:
               this._JmsMessageHeader = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._JmsMessageProperty = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._JndiConnectionFactoryName = "com.oracle.webservices.jms.ConnectionFactory";
               if (initOne) {
                  break;
               }
            case 6:
               this._JndiContextParameter = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._JndiInitialContextFactory = "weblogic.jndi.WLInitialContextFactory";
               if (initOne) {
                  break;
               }
            case 5:
               this._JndiUrl = "t3://localhost:7001";
               if (initOne) {
                  break;
               }
            case 0:
               this._LookupVariant = "jndi";
               if (initOne) {
                  break;
               }
            case 13:
               this._MessageType = "BYTES";
               if (initOne) {
                  break;
               }
            case 8:
               this._Priority = 0;
               if (initOne) {
                  break;
               }
            case 10:
               this._ReplyToName = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._RunAsPrincipal = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._RunAsRole = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._TargetService = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._TimeToLive = 0L;
               if (initOne) {
                  break;
               }
            case 14:
               this._EnableHttpWsdlAccess = true;
               if (initOne) {
                  break;
               }
            case 17:
               this._MdbPerDestination = true;
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
            case 8:
               if (s.equals("jndi-url")) {
                  return 5;
               }

               if (s.equals("priority")) {
                  return 8;
               }
            case 9:
            case 10:
            case 21:
            case 24:
            case 25:
            case 26:
            case 27:
            default:
               break;
            case 11:
               if (s.equals("run-as-role")) {
                  return 16;
               }
               break;
            case 12:
               if (s.equals("message-type")) {
                  return 13;
               }

               if (s.equals("time-to-live")) {
                  return 7;
               }
               break;
            case 13:
               if (s.equals("delivery-mode")) {
                  return 9;
               }

               if (s.equals("reply-to-name")) {
                  return 10;
               }
               break;
            case 14:
               if (s.equals("lookup-variant")) {
                  return 0;
               }

               if (s.equals("target-service")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("binding-version")) {
                  return 12;
               }
               break;
            case 16:
               if (s.equals("destination-name")) {
                  return 1;
               }

               if (s.equals("destination-type")) {
                  return 2;
               }

               if (s.equals("run-as-principal")) {
                  return 15;
               }
               break;
            case 17:
               if (s.equals("activation-config")) {
                  return 18;
               }
               break;
            case 18:
               if (s.equals("jms-message-header")) {
                  return 19;
               }
               break;
            case 19:
               if (s.equals("mdb-per-destination")) {
                  return 17;
               }
               break;
            case 20:
               if (s.equals("jms-message-property")) {
                  return 20;
               }
               break;
            case 22:
               if (s.equals("jndi-context-parameter")) {
                  return 6;
               }
               break;
            case 23:
               if (s.equals("enable-http-wsdl-access")) {
                  return 14;
               }
               break;
            case 28:
               if (s.equals("jndi-connection-factory-name")) {
                  return 3;
               }

               if (s.equals("jndi-initial-context-factory")) {
                  return 4;
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
               return "lookup-variant";
            case 1:
               return "destination-name";
            case 2:
               return "destination-type";
            case 3:
               return "jndi-connection-factory-name";
            case 4:
               return "jndi-initial-context-factory";
            case 5:
               return "jndi-url";
            case 6:
               return "jndi-context-parameter";
            case 7:
               return "time-to-live";
            case 8:
               return "priority";
            case 9:
               return "delivery-mode";
            case 10:
               return "reply-to-name";
            case 11:
               return "target-service";
            case 12:
               return "binding-version";
            case 13:
               return "message-type";
            case 14:
               return "enable-http-wsdl-access";
            case 15:
               return "run-as-principal";
            case 16:
               return "run-as-role";
            case 17:
               return "mdb-per-destination";
            case 18:
               return "activation-config";
            case 19:
               return "jms-message-header";
            case 20:
               return "jms-message-property";
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
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
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
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SoapjmsServiceEndpointAddressBeanImpl bean;

      protected Helper(SoapjmsServiceEndpointAddressBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "LookupVariant";
            case 1:
               return "DestinationName";
            case 2:
               return "DestinationType";
            case 3:
               return "JndiConnectionFactoryName";
            case 4:
               return "JndiInitialContextFactory";
            case 5:
               return "JndiUrl";
            case 6:
               return "JndiContextParameter";
            case 7:
               return "TimeToLive";
            case 8:
               return "Priority";
            case 9:
               return "DeliveryMode";
            case 10:
               return "ReplyToName";
            case 11:
               return "TargetService";
            case 12:
               return "BindingVersion";
            case 13:
               return "MessageType";
            case 14:
               return "EnableHttpWsdlAccess";
            case 15:
               return "RunAsPrincipal";
            case 16:
               return "RunAsRole";
            case 17:
               return "MdbPerDestination";
            case 18:
               return "ActivationConfig";
            case 19:
               return "JmsMessageHeader";
            case 20:
               return "JmsMessageProperty";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActivationConfig")) {
            return 18;
         } else if (propName.equals("BindingVersion")) {
            return 12;
         } else if (propName.equals("DeliveryMode")) {
            return 9;
         } else if (propName.equals("DestinationName")) {
            return 1;
         } else if (propName.equals("DestinationType")) {
            return 2;
         } else if (propName.equals("JmsMessageHeader")) {
            return 19;
         } else if (propName.equals("JmsMessageProperty")) {
            return 20;
         } else if (propName.equals("JndiConnectionFactoryName")) {
            return 3;
         } else if (propName.equals("JndiContextParameter")) {
            return 6;
         } else if (propName.equals("JndiInitialContextFactory")) {
            return 4;
         } else if (propName.equals("JndiUrl")) {
            return 5;
         } else if (propName.equals("LookupVariant")) {
            return 0;
         } else if (propName.equals("MessageType")) {
            return 13;
         } else if (propName.equals("Priority")) {
            return 8;
         } else if (propName.equals("ReplyToName")) {
            return 10;
         } else if (propName.equals("RunAsPrincipal")) {
            return 15;
         } else if (propName.equals("RunAsRole")) {
            return 16;
         } else if (propName.equals("TargetService")) {
            return 11;
         } else if (propName.equals("TimeToLive")) {
            return 7;
         } else if (propName.equals("EnableHttpWsdlAccess")) {
            return 14;
         } else {
            return propName.equals("MdbPerDestination") ? 17 : super.getPropertyIndex(propName);
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
            if (this.bean.isActivationConfigSet()) {
               buf.append("ActivationConfig");
               buf.append(String.valueOf(this.bean.getActivationConfig()));
            }

            if (this.bean.isBindingVersionSet()) {
               buf.append("BindingVersion");
               buf.append(String.valueOf(this.bean.getBindingVersion()));
            }

            if (this.bean.isDeliveryModeSet()) {
               buf.append("DeliveryMode");
               buf.append(String.valueOf(this.bean.getDeliveryMode()));
            }

            if (this.bean.isDestinationNameSet()) {
               buf.append("DestinationName");
               buf.append(String.valueOf(this.bean.getDestinationName()));
            }

            if (this.bean.isDestinationTypeSet()) {
               buf.append("DestinationType");
               buf.append(String.valueOf(this.bean.getDestinationType()));
            }

            if (this.bean.isJmsMessageHeaderSet()) {
               buf.append("JmsMessageHeader");
               buf.append(String.valueOf(this.bean.getJmsMessageHeader()));
            }

            if (this.bean.isJmsMessagePropertySet()) {
               buf.append("JmsMessageProperty");
               buf.append(String.valueOf(this.bean.getJmsMessageProperty()));
            }

            if (this.bean.isJndiConnectionFactoryNameSet()) {
               buf.append("JndiConnectionFactoryName");
               buf.append(String.valueOf(this.bean.getJndiConnectionFactoryName()));
            }

            if (this.bean.isJndiContextParameterSet()) {
               buf.append("JndiContextParameter");
               buf.append(String.valueOf(this.bean.getJndiContextParameter()));
            }

            if (this.bean.isJndiInitialContextFactorySet()) {
               buf.append("JndiInitialContextFactory");
               buf.append(String.valueOf(this.bean.getJndiInitialContextFactory()));
            }

            if (this.bean.isJndiUrlSet()) {
               buf.append("JndiUrl");
               buf.append(String.valueOf(this.bean.getJndiUrl()));
            }

            if (this.bean.isLookupVariantSet()) {
               buf.append("LookupVariant");
               buf.append(String.valueOf(this.bean.getLookupVariant()));
            }

            if (this.bean.isMessageTypeSet()) {
               buf.append("MessageType");
               buf.append(String.valueOf(this.bean.getMessageType()));
            }

            if (this.bean.isPrioritySet()) {
               buf.append("Priority");
               buf.append(String.valueOf(this.bean.getPriority()));
            }

            if (this.bean.isReplyToNameSet()) {
               buf.append("ReplyToName");
               buf.append(String.valueOf(this.bean.getReplyToName()));
            }

            if (this.bean.isRunAsPrincipalSet()) {
               buf.append("RunAsPrincipal");
               buf.append(String.valueOf(this.bean.getRunAsPrincipal()));
            }

            if (this.bean.isRunAsRoleSet()) {
               buf.append("RunAsRole");
               buf.append(String.valueOf(this.bean.getRunAsRole()));
            }

            if (this.bean.isTargetServiceSet()) {
               buf.append("TargetService");
               buf.append(String.valueOf(this.bean.getTargetService()));
            }

            if (this.bean.isTimeToLiveSet()) {
               buf.append("TimeToLive");
               buf.append(String.valueOf(this.bean.getTimeToLive()));
            }

            if (this.bean.isEnableHttpWsdlAccessSet()) {
               buf.append("EnableHttpWsdlAccess");
               buf.append(String.valueOf(this.bean.isEnableHttpWsdlAccess()));
            }

            if (this.bean.isMdbPerDestinationSet()) {
               buf.append("MdbPerDestination");
               buf.append(String.valueOf(this.bean.isMdbPerDestination()));
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
            SoapjmsServiceEndpointAddressBeanImpl otherTyped = (SoapjmsServiceEndpointAddressBeanImpl)other;
            this.computeDiff("ActivationConfig", this.bean.getActivationConfig(), otherTyped.getActivationConfig(), true);
            this.computeDiff("BindingVersion", this.bean.getBindingVersion(), otherTyped.getBindingVersion(), true);
            this.computeDiff("DeliveryMode", this.bean.getDeliveryMode(), otherTyped.getDeliveryMode(), true);
            this.computeDiff("DestinationName", this.bean.getDestinationName(), otherTyped.getDestinationName(), true);
            this.computeDiff("DestinationType", this.bean.getDestinationType(), otherTyped.getDestinationType(), true);
            this.computeDiff("JmsMessageHeader", this.bean.getJmsMessageHeader(), otherTyped.getJmsMessageHeader(), true);
            this.computeDiff("JmsMessageProperty", this.bean.getJmsMessageProperty(), otherTyped.getJmsMessageProperty(), true);
            this.computeDiff("JndiConnectionFactoryName", this.bean.getJndiConnectionFactoryName(), otherTyped.getJndiConnectionFactoryName(), true);
            this.computeDiff("JndiContextParameter", this.bean.getJndiContextParameter(), otherTyped.getJndiContextParameter(), true);
            this.computeDiff("JndiInitialContextFactory", this.bean.getJndiInitialContextFactory(), otherTyped.getJndiInitialContextFactory(), true);
            this.computeDiff("JndiUrl", this.bean.getJndiUrl(), otherTyped.getJndiUrl(), true);
            this.computeDiff("LookupVariant", this.bean.getLookupVariant(), otherTyped.getLookupVariant(), true);
            this.computeDiff("MessageType", this.bean.getMessageType(), otherTyped.getMessageType(), true);
            this.computeDiff("Priority", this.bean.getPriority(), otherTyped.getPriority(), true);
            this.computeDiff("ReplyToName", this.bean.getReplyToName(), otherTyped.getReplyToName(), true);
            this.computeDiff("RunAsPrincipal", this.bean.getRunAsPrincipal(), otherTyped.getRunAsPrincipal(), true);
            this.computeDiff("RunAsRole", this.bean.getRunAsRole(), otherTyped.getRunAsRole(), true);
            this.computeDiff("TargetService", this.bean.getTargetService(), otherTyped.getTargetService(), true);
            this.computeDiff("TimeToLive", this.bean.getTimeToLive(), otherTyped.getTimeToLive(), true);
            this.computeDiff("EnableHttpWsdlAccess", this.bean.isEnableHttpWsdlAccess(), otherTyped.isEnableHttpWsdlAccess(), true);
            this.computeDiff("MdbPerDestination", this.bean.isMdbPerDestination(), otherTyped.isMdbPerDestination(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SoapjmsServiceEndpointAddressBeanImpl original = (SoapjmsServiceEndpointAddressBeanImpl)event.getSourceBean();
            SoapjmsServiceEndpointAddressBeanImpl proposed = (SoapjmsServiceEndpointAddressBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ActivationConfig")) {
                  original.setActivationConfig(proposed.getActivationConfig());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("BindingVersion")) {
                  original.setBindingVersion(proposed.getBindingVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("DeliveryMode")) {
                  original.setDeliveryMode(proposed.getDeliveryMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("DestinationName")) {
                  original.setDestinationName(proposed.getDestinationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DestinationType")) {
                  original.setDestinationType(proposed.getDestinationType());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("JmsMessageHeader")) {
                  original.setJmsMessageHeader(proposed.getJmsMessageHeader());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("JmsMessageProperty")) {
                  original.setJmsMessageProperty(proposed.getJmsMessageProperty());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("JndiConnectionFactoryName")) {
                  original.setJndiConnectionFactoryName(proposed.getJndiConnectionFactoryName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("JndiContextParameter")) {
                  original.setJndiContextParameter(proposed.getJndiContextParameter());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("JndiInitialContextFactory")) {
                  original.setJndiInitialContextFactory(proposed.getJndiInitialContextFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("JndiUrl")) {
                  original.setJndiUrl(proposed.getJndiUrl());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("LookupVariant")) {
                  original.setLookupVariant(proposed.getLookupVariant());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MessageType")) {
                  original.setMessageType(proposed.getMessageType());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Priority")) {
                  original.setPriority(proposed.getPriority());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("ReplyToName")) {
                  original.setReplyToName(proposed.getReplyToName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("RunAsPrincipal")) {
                  original.setRunAsPrincipal(proposed.getRunAsPrincipal());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("RunAsRole")) {
                  original.setRunAsRole(proposed.getRunAsRole());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("TargetService")) {
                  original.setTargetService(proposed.getTargetService());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("TimeToLive")) {
                  original.setTimeToLive(proposed.getTimeToLive());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("EnableHttpWsdlAccess")) {
                  original.setEnableHttpWsdlAccess(proposed.isEnableHttpWsdlAccess());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MdbPerDestination")) {
                  original.setMdbPerDestination(proposed.isMdbPerDestination());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
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
            SoapjmsServiceEndpointAddressBeanImpl copy = (SoapjmsServiceEndpointAddressBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ActivationConfig")) && this.bean.isActivationConfigSet()) {
               copy.setActivationConfig(this.bean.getActivationConfig());
            }

            if ((excludeProps == null || !excludeProps.contains("BindingVersion")) && this.bean.isBindingVersionSet()) {
               copy.setBindingVersion(this.bean.getBindingVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("DeliveryMode")) && this.bean.isDeliveryModeSet()) {
               copy.setDeliveryMode(this.bean.getDeliveryMode());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationName")) && this.bean.isDestinationNameSet()) {
               copy.setDestinationName(this.bean.getDestinationName());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationType")) && this.bean.isDestinationTypeSet()) {
               copy.setDestinationType(this.bean.getDestinationType());
            }

            if ((excludeProps == null || !excludeProps.contains("JmsMessageHeader")) && this.bean.isJmsMessageHeaderSet()) {
               copy.setJmsMessageHeader(this.bean.getJmsMessageHeader());
            }

            if ((excludeProps == null || !excludeProps.contains("JmsMessageProperty")) && this.bean.isJmsMessagePropertySet()) {
               copy.setJmsMessageProperty(this.bean.getJmsMessageProperty());
            }

            if ((excludeProps == null || !excludeProps.contains("JndiConnectionFactoryName")) && this.bean.isJndiConnectionFactoryNameSet()) {
               copy.setJndiConnectionFactoryName(this.bean.getJndiConnectionFactoryName());
            }

            if ((excludeProps == null || !excludeProps.contains("JndiContextParameter")) && this.bean.isJndiContextParameterSet()) {
               copy.setJndiContextParameter(this.bean.getJndiContextParameter());
            }

            if ((excludeProps == null || !excludeProps.contains("JndiInitialContextFactory")) && this.bean.isJndiInitialContextFactorySet()) {
               copy.setJndiInitialContextFactory(this.bean.getJndiInitialContextFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("JndiUrl")) && this.bean.isJndiUrlSet()) {
               copy.setJndiUrl(this.bean.getJndiUrl());
            }

            if ((excludeProps == null || !excludeProps.contains("LookupVariant")) && this.bean.isLookupVariantSet()) {
               copy.setLookupVariant(this.bean.getLookupVariant());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageType")) && this.bean.isMessageTypeSet()) {
               copy.setMessageType(this.bean.getMessageType());
            }

            if ((excludeProps == null || !excludeProps.contains("Priority")) && this.bean.isPrioritySet()) {
               copy.setPriority(this.bean.getPriority());
            }

            if ((excludeProps == null || !excludeProps.contains("ReplyToName")) && this.bean.isReplyToNameSet()) {
               copy.setReplyToName(this.bean.getReplyToName());
            }

            if ((excludeProps == null || !excludeProps.contains("RunAsPrincipal")) && this.bean.isRunAsPrincipalSet()) {
               copy.setRunAsPrincipal(this.bean.getRunAsPrincipal());
            }

            if ((excludeProps == null || !excludeProps.contains("RunAsRole")) && this.bean.isRunAsRoleSet()) {
               copy.setRunAsRole(this.bean.getRunAsRole());
            }

            if ((excludeProps == null || !excludeProps.contains("TargetService")) && this.bean.isTargetServiceSet()) {
               copy.setTargetService(this.bean.getTargetService());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeToLive")) && this.bean.isTimeToLiveSet()) {
               copy.setTimeToLive(this.bean.getTimeToLive());
            }

            if ((excludeProps == null || !excludeProps.contains("EnableHttpWsdlAccess")) && this.bean.isEnableHttpWsdlAccessSet()) {
               copy.setEnableHttpWsdlAccess(this.bean.isEnableHttpWsdlAccess());
            }

            if ((excludeProps == null || !excludeProps.contains("MdbPerDestination")) && this.bean.isMdbPerDestinationSet()) {
               copy.setMdbPerDestination(this.bean.isMdbPerDestination());
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

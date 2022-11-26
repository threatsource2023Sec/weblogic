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
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.utils.collections.CombinedIterator;

public class ClientParamsBeanImpl extends SettableBeanImpl implements ClientParamsBean, Serializable {
   private String _AcknowledgePolicy;
   private boolean _AllowCloseInOnMessage;
   private String _ClientId;
   private String _ClientIdPolicy;
   private int _MessagesMaximum;
   private String _MulticastOverrunPolicy;
   private long _ReconnectBlockingMillis;
   private String _ReconnectPolicy;
   private String _SubscriptionSharingPolicy;
   private String _SynchronousPrefetchMode;
   private long _TotalReconnectPeriodMillis;
   private static SchemaHelper2 _schemaHelper;

   public ClientParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public ClientParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ClientParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getClientId() {
      return this._ClientId;
   }

   public boolean isClientIdInherited() {
      return false;
   }

   public boolean isClientIdSet() {
      return this._isSet(0);
   }

   public void setClientId(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClientId;
      this._ClientId = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getClientIdPolicy() {
      return this._ClientIdPolicy;
   }

   public boolean isClientIdPolicyInherited() {
      return false;
   }

   public boolean isClientIdPolicySet() {
      return this._isSet(1);
   }

   public void setClientIdPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{JMSConstants.CLIENT_ID_POLICY_RESTRICTED, JMSConstants.CLIENT_ID_POLICY_UNRESTRICTED};
      param0 = LegalChecks.checkInEnum("ClientIdPolicy", param0, _set);
      String _oldVal = this._ClientIdPolicy;
      this._ClientIdPolicy = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getSubscriptionSharingPolicy() {
      return this._SubscriptionSharingPolicy;
   }

   public boolean isSubscriptionSharingPolicyInherited() {
      return false;
   }

   public boolean isSubscriptionSharingPolicySet() {
      return this._isSet(2);
   }

   public void setSubscriptionSharingPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{JMSConstants.SUBSCRIPTION_EXCLUSIVE, JMSConstants.SUBSCRIPTION_SHARABLE};
      param0 = LegalChecks.checkInEnum("SubscriptionSharingPolicy", param0, _set);
      String _oldVal = this._SubscriptionSharingPolicy;
      this._SubscriptionSharingPolicy = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getAcknowledgePolicy() {
      return this._AcknowledgePolicy;
   }

   public boolean isAcknowledgePolicyInherited() {
      return false;
   }

   public boolean isAcknowledgePolicySet() {
      return this._isSet(3);
   }

   public void setAcknowledgePolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"All", "Previous"};
      param0 = LegalChecks.checkInEnum("AcknowledgePolicy", param0, _set);
      String _oldVal = this._AcknowledgePolicy;
      this._AcknowledgePolicy = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isAllowCloseInOnMessage() {
      return this._AllowCloseInOnMessage;
   }

   public boolean isAllowCloseInOnMessageInherited() {
      return false;
   }

   public boolean isAllowCloseInOnMessageSet() {
      return this._isSet(4);
   }

   public void setAllowCloseInOnMessage(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._AllowCloseInOnMessage;
      this._AllowCloseInOnMessage = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getMessagesMaximum() {
      return this._MessagesMaximum;
   }

   public boolean isMessagesMaximumInherited() {
      return false;
   }

   public boolean isMessagesMaximumSet() {
      return this._isSet(5);
   }

   public void setMessagesMaximum(int param0) throws IllegalArgumentException {
      int _oldVal = this._MessagesMaximum;
      this._MessagesMaximum = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getMulticastOverrunPolicy() {
      return this._MulticastOverrunPolicy;
   }

   public boolean isMulticastOverrunPolicyInherited() {
      return false;
   }

   public boolean isMulticastOverrunPolicySet() {
      return this._isSet(6);
   }

   public void setMulticastOverrunPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"KeepOld", "KeepNew"};
      param0 = LegalChecks.checkInEnum("MulticastOverrunPolicy", param0, _set);
      String _oldVal = this._MulticastOverrunPolicy;
      this._MulticastOverrunPolicy = param0;
      this._postSet(6, _oldVal, param0);
   }

   public void setSynchronousPrefetchMode(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SynchronousPrefetchMode;
      this._SynchronousPrefetchMode = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getSynchronousPrefetchMode() {
      return this._SynchronousPrefetchMode;
   }

   public boolean isSynchronousPrefetchModeInherited() {
      return false;
   }

   public boolean isSynchronousPrefetchModeSet() {
      return this._isSet(7);
   }

   public void setReconnectPolicy(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ReconnectPolicy;
      this._ReconnectPolicy = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getReconnectPolicy() {
      return this._ReconnectPolicy;
   }

   public boolean isReconnectPolicyInherited() {
      return false;
   }

   public boolean isReconnectPolicySet() {
      return this._isSet(8);
   }

   public void setReconnectBlockingMillis(long param0) throws IllegalArgumentException {
      long _oldVal = this._ReconnectBlockingMillis;
      this._ReconnectBlockingMillis = param0;
      this._postSet(9, _oldVal, param0);
   }

   public long getReconnectBlockingMillis() {
      return this._ReconnectBlockingMillis;
   }

   public boolean isReconnectBlockingMillisInherited() {
      return false;
   }

   public boolean isReconnectBlockingMillisSet() {
      return this._isSet(9);
   }

   public void setTotalReconnectPeriodMillis(long param0) throws IllegalArgumentException {
      long _oldVal = this._TotalReconnectPeriodMillis;
      this._TotalReconnectPeriodMillis = param0;
      this._postSet(10, _oldVal, param0);
   }

   public long getTotalReconnectPeriodMillis() {
      return this._TotalReconnectPeriodMillis;
   }

   public boolean isTotalReconnectPeriodMillisInherited() {
      return false;
   }

   public boolean isTotalReconnectPeriodMillisSet() {
      return this._isSet(10);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._AcknowledgePolicy = "All";
               if (initOne) {
                  break;
               }
            case 0:
               this._ClientId = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ClientIdPolicy = JMSConstants.CLIENT_ID_POLICY_RESTRICTED;
               if (initOne) {
                  break;
               }
            case 5:
               this._MessagesMaximum = 10;
               if (initOne) {
                  break;
               }
            case 6:
               this._MulticastOverrunPolicy = "KeepOld";
               if (initOne) {
                  break;
               }
            case 9:
               this._ReconnectBlockingMillis = 60000L;
               if (initOne) {
                  break;
               }
            case 8:
               this._ReconnectPolicy = JMSConstants.RECONNECT_POLICY_PRODUCER;
               if (initOne) {
                  break;
               }
            case 2:
               this._SubscriptionSharingPolicy = JMSConstants.SUBSCRIPTION_EXCLUSIVE;
               if (initOne) {
                  break;
               }
            case 7:
               this._SynchronousPrefetchMode = "disabled";
               if (initOne) {
                  break;
               }
            case 10:
               this._TotalReconnectPeriodMillis = -1L;
               if (initOne) {
                  break;
               }
            case 4:
               this._AllowCloseInOnMessage = true;
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
            case 9:
               if (s.equals("client-id")) {
                  return 0;
               }
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 26:
            case 28:
            default:
               break;
            case 16:
               if (s.equals("client-id-policy")) {
                  return 1;
               }

               if (s.equals("messages-maximum")) {
                  return 5;
               }

               if (s.equals("reconnect-policy")) {
                  return 8;
               }
               break;
            case 18:
               if (s.equals("acknowledge-policy")) {
                  return 3;
               }
               break;
            case 24:
               if (s.equals("multicast-overrun-policy")) {
                  return 6;
               }

               if (s.equals("allow-close-in-onMessage")) {
                  return 4;
               }
               break;
            case 25:
               if (s.equals("reconnect-blocking-millis")) {
                  return 9;
               }

               if (s.equals("synchronous-prefetch-mode")) {
                  return 7;
               }
               break;
            case 27:
               if (s.equals("subscription-sharing-policy")) {
                  return 2;
               }
               break;
            case 29:
               if (s.equals("total-reconnect-period-millis")) {
                  return 10;
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
               return "client-id";
            case 1:
               return "client-id-policy";
            case 2:
               return "subscription-sharing-policy";
            case 3:
               return "acknowledge-policy";
            case 4:
               return "allow-close-in-onMessage";
            case 5:
               return "messages-maximum";
            case 6:
               return "multicast-overrun-policy";
            case 7:
               return "synchronous-prefetch-mode";
            case 8:
               return "reconnect-policy";
            case 9:
               return "reconnect-blocking-millis";
            case 10:
               return "total-reconnect-period-millis";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 5:
               return true;
            case 6:
            default:
               return super.isConfigurable(propIndex);
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private ClientParamsBeanImpl bean;

      protected Helper(ClientParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ClientId";
            case 1:
               return "ClientIdPolicy";
            case 2:
               return "SubscriptionSharingPolicy";
            case 3:
               return "AcknowledgePolicy";
            case 4:
               return "AllowCloseInOnMessage";
            case 5:
               return "MessagesMaximum";
            case 6:
               return "MulticastOverrunPolicy";
            case 7:
               return "SynchronousPrefetchMode";
            case 8:
               return "ReconnectPolicy";
            case 9:
               return "ReconnectBlockingMillis";
            case 10:
               return "TotalReconnectPeriodMillis";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AcknowledgePolicy")) {
            return 3;
         } else if (propName.equals("ClientId")) {
            return 0;
         } else if (propName.equals("ClientIdPolicy")) {
            return 1;
         } else if (propName.equals("MessagesMaximum")) {
            return 5;
         } else if (propName.equals("MulticastOverrunPolicy")) {
            return 6;
         } else if (propName.equals("ReconnectBlockingMillis")) {
            return 9;
         } else if (propName.equals("ReconnectPolicy")) {
            return 8;
         } else if (propName.equals("SubscriptionSharingPolicy")) {
            return 2;
         } else if (propName.equals("SynchronousPrefetchMode")) {
            return 7;
         } else if (propName.equals("TotalReconnectPeriodMillis")) {
            return 10;
         } else {
            return propName.equals("AllowCloseInOnMessage") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isAcknowledgePolicySet()) {
               buf.append("AcknowledgePolicy");
               buf.append(String.valueOf(this.bean.getAcknowledgePolicy()));
            }

            if (this.bean.isClientIdSet()) {
               buf.append("ClientId");
               buf.append(String.valueOf(this.bean.getClientId()));
            }

            if (this.bean.isClientIdPolicySet()) {
               buf.append("ClientIdPolicy");
               buf.append(String.valueOf(this.bean.getClientIdPolicy()));
            }

            if (this.bean.isMessagesMaximumSet()) {
               buf.append("MessagesMaximum");
               buf.append(String.valueOf(this.bean.getMessagesMaximum()));
            }

            if (this.bean.isMulticastOverrunPolicySet()) {
               buf.append("MulticastOverrunPolicy");
               buf.append(String.valueOf(this.bean.getMulticastOverrunPolicy()));
            }

            if (this.bean.isReconnectBlockingMillisSet()) {
               buf.append("ReconnectBlockingMillis");
               buf.append(String.valueOf(this.bean.getReconnectBlockingMillis()));
            }

            if (this.bean.isReconnectPolicySet()) {
               buf.append("ReconnectPolicy");
               buf.append(String.valueOf(this.bean.getReconnectPolicy()));
            }

            if (this.bean.isSubscriptionSharingPolicySet()) {
               buf.append("SubscriptionSharingPolicy");
               buf.append(String.valueOf(this.bean.getSubscriptionSharingPolicy()));
            }

            if (this.bean.isSynchronousPrefetchModeSet()) {
               buf.append("SynchronousPrefetchMode");
               buf.append(String.valueOf(this.bean.getSynchronousPrefetchMode()));
            }

            if (this.bean.isTotalReconnectPeriodMillisSet()) {
               buf.append("TotalReconnectPeriodMillis");
               buf.append(String.valueOf(this.bean.getTotalReconnectPeriodMillis()));
            }

            if (this.bean.isAllowCloseInOnMessageSet()) {
               buf.append("AllowCloseInOnMessage");
               buf.append(String.valueOf(this.bean.isAllowCloseInOnMessage()));
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
            ClientParamsBeanImpl otherTyped = (ClientParamsBeanImpl)other;
            this.computeDiff("AcknowledgePolicy", this.bean.getAcknowledgePolicy(), otherTyped.getAcknowledgePolicy(), true);
            this.computeDiff("ClientId", this.bean.getClientId(), otherTyped.getClientId(), true);
            this.computeDiff("ClientIdPolicy", this.bean.getClientIdPolicy(), otherTyped.getClientIdPolicy(), true);
            this.computeDiff("MessagesMaximum", this.bean.getMessagesMaximum(), otherTyped.getMessagesMaximum(), true);
            this.computeDiff("MulticastOverrunPolicy", this.bean.getMulticastOverrunPolicy(), otherTyped.getMulticastOverrunPolicy(), true);
            this.computeDiff("ReconnectBlockingMillis", this.bean.getReconnectBlockingMillis(), otherTyped.getReconnectBlockingMillis(), true);
            this.computeDiff("ReconnectPolicy", this.bean.getReconnectPolicy(), otherTyped.getReconnectPolicy(), true);
            this.computeDiff("SubscriptionSharingPolicy", this.bean.getSubscriptionSharingPolicy(), otherTyped.getSubscriptionSharingPolicy(), true);
            this.computeDiff("SynchronousPrefetchMode", this.bean.getSynchronousPrefetchMode(), otherTyped.getSynchronousPrefetchMode(), true);
            this.computeDiff("TotalReconnectPeriodMillis", this.bean.getTotalReconnectPeriodMillis(), otherTyped.getTotalReconnectPeriodMillis(), true);
            this.computeDiff("AllowCloseInOnMessage", this.bean.isAllowCloseInOnMessage(), otherTyped.isAllowCloseInOnMessage(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ClientParamsBeanImpl original = (ClientParamsBeanImpl)event.getSourceBean();
            ClientParamsBeanImpl proposed = (ClientParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AcknowledgePolicy")) {
                  original.setAcknowledgePolicy(proposed.getAcknowledgePolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ClientId")) {
                  original.setClientId(proposed.getClientId());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ClientIdPolicy")) {
                  original.setClientIdPolicy(proposed.getClientIdPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MessagesMaximum")) {
                  original.setMessagesMaximum(proposed.getMessagesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("MulticastOverrunPolicy")) {
                  original.setMulticastOverrunPolicy(proposed.getMulticastOverrunPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ReconnectBlockingMillis")) {
                  original.setReconnectBlockingMillis(proposed.getReconnectBlockingMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("ReconnectPolicy")) {
                  original.setReconnectPolicy(proposed.getReconnectPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("SubscriptionSharingPolicy")) {
                  original.setSubscriptionSharingPolicy(proposed.getSubscriptionSharingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SynchronousPrefetchMode")) {
                  original.setSynchronousPrefetchMode(proposed.getSynchronousPrefetchMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("TotalReconnectPeriodMillis")) {
                  original.setTotalReconnectPeriodMillis(proposed.getTotalReconnectPeriodMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("AllowCloseInOnMessage")) {
                  original.setAllowCloseInOnMessage(proposed.isAllowCloseInOnMessage());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            ClientParamsBeanImpl copy = (ClientParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AcknowledgePolicy")) && this.bean.isAcknowledgePolicySet()) {
               copy.setAcknowledgePolicy(this.bean.getAcknowledgePolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientId")) && this.bean.isClientIdSet()) {
               copy.setClientId(this.bean.getClientId());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientIdPolicy")) && this.bean.isClientIdPolicySet()) {
               copy.setClientIdPolicy(this.bean.getClientIdPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesMaximum")) && this.bean.isMessagesMaximumSet()) {
               copy.setMessagesMaximum(this.bean.getMessagesMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("MulticastOverrunPolicy")) && this.bean.isMulticastOverrunPolicySet()) {
               copy.setMulticastOverrunPolicy(this.bean.getMulticastOverrunPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("ReconnectBlockingMillis")) && this.bean.isReconnectBlockingMillisSet()) {
               copy.setReconnectBlockingMillis(this.bean.getReconnectBlockingMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("ReconnectPolicy")) && this.bean.isReconnectPolicySet()) {
               copy.setReconnectPolicy(this.bean.getReconnectPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("SubscriptionSharingPolicy")) && this.bean.isSubscriptionSharingPolicySet()) {
               copy.setSubscriptionSharingPolicy(this.bean.getSubscriptionSharingPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("SynchronousPrefetchMode")) && this.bean.isSynchronousPrefetchModeSet()) {
               copy.setSynchronousPrefetchMode(this.bean.getSynchronousPrefetchMode());
            }

            if ((excludeProps == null || !excludeProps.contains("TotalReconnectPeriodMillis")) && this.bean.isTotalReconnectPeriodMillisSet()) {
               copy.setTotalReconnectPeriodMillis(this.bean.getTotalReconnectPeriodMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowCloseInOnMessage")) && this.bean.isAllowCloseInOnMessageSet()) {
               copy.setAllowCloseInOnMessage(this.bean.isAllowCloseInOnMessage());
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

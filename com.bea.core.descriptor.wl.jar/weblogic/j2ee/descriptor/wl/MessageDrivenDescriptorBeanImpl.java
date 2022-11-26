package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.WeblogicEjbJarBeanDescriptorValidator;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class MessageDrivenDescriptorBeanImpl extends AbstractDescriptorBean implements MessageDrivenDescriptorBean, Serializable {
   private String _ConnectionFactoryJNDIName;
   private String _ConnectionFactoryResourceLink;
   private String _DestinationJNDIName;
   private String _DestinationResourceLink;
   private String _DistributedDestinationConnection;
   private boolean _DurableSubscriptionDeletion;
   private boolean _GenerateUniqueJmsClientId;
   private String _Id;
   private int _InitSuspendSeconds;
   private String _InitialContextFactory;
   private String _JmsClientId;
   private int _JmsPollingIntervalSeconds;
   private int _MaxMessagesInTransaction;
   private int _MaxSuspendSeconds;
   private PoolBean _Pool;
   private String _ProviderUrl;
   private String _ResourceAdapterJNDIName;
   private SecurityPluginBean _SecurityPlugin;
   private TimerDescriptorBean _TimerDescriptor;
   private boolean _Use81StylePolling;
   private static SchemaHelper2 _schemaHelper;

   public MessageDrivenDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public MessageDrivenDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MessageDrivenDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public PoolBean getPool() {
      return this._Pool;
   }

   public boolean isPoolInherited() {
      return false;
   }

   public boolean isPoolSet() {
      return this._isSet(0) || this._isAnythingSet((AbstractDescriptorBean)this.getPool());
   }

   public void setPool(PoolBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 0)) {
         this._postCreate(_child);
      }

      PoolBean _oldVal = this._Pool;
      this._Pool = param0;
      this._postSet(0, _oldVal, param0);
   }

   public TimerDescriptorBean getTimerDescriptor() {
      return this._TimerDescriptor;
   }

   public boolean isTimerDescriptorInherited() {
      return false;
   }

   public boolean isTimerDescriptorSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getTimerDescriptor());
   }

   public void setTimerDescriptor(TimerDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      TimerDescriptorBean _oldVal = this._TimerDescriptor;
      this._TimerDescriptor = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getResourceAdapterJNDIName() {
      return this._ResourceAdapterJNDIName;
   }

   public boolean isResourceAdapterJNDINameInherited() {
      return false;
   }

   public boolean isResourceAdapterJNDINameSet() {
      return this._isSet(2);
   }

   public void setResourceAdapterJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceAdapterJNDIName;
      this._ResourceAdapterJNDIName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getDestinationJNDIName() {
      return this._DestinationJNDIName;
   }

   public boolean isDestinationJNDINameInherited() {
      return false;
   }

   public boolean isDestinationJNDINameSet() {
      return this._isSet(3);
   }

   public void setDestinationJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DestinationJNDIName;
      this._DestinationJNDIName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getInitialContextFactory() {
      return this._InitialContextFactory;
   }

   public boolean isInitialContextFactoryInherited() {
      return false;
   }

   public boolean isInitialContextFactorySet() {
      return this._isSet(4);
   }

   public void setInitialContextFactory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WeblogicEjbJarBeanDescriptorValidator.validateMDBInitialContextFactory(param0);
      String _oldVal = this._InitialContextFactory;
      this._InitialContextFactory = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getProviderUrl() {
      return this._ProviderUrl;
   }

   public boolean isProviderUrlInherited() {
      return false;
   }

   public boolean isProviderUrlSet() {
      return this._isSet(5);
   }

   public void setProviderUrl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ProviderUrl;
      this._ProviderUrl = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getConnectionFactoryJNDIName() {
      return this._ConnectionFactoryJNDIName;
   }

   public boolean isConnectionFactoryJNDINameInherited() {
      return false;
   }

   public boolean isConnectionFactoryJNDINameSet() {
      return this._isSet(6);
   }

   public void setConnectionFactoryJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactoryJNDIName;
      this._ConnectionFactoryJNDIName = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getDestinationResourceLink() {
      return this._DestinationResourceLink;
   }

   public boolean isDestinationResourceLinkInherited() {
      return false;
   }

   public boolean isDestinationResourceLinkSet() {
      return this._isSet(7);
   }

   public void setDestinationResourceLink(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DestinationResourceLink;
      this._DestinationResourceLink = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getConnectionFactoryResourceLink() {
      return this._ConnectionFactoryResourceLink;
   }

   public boolean isConnectionFactoryResourceLinkInherited() {
      return false;
   }

   public boolean isConnectionFactoryResourceLinkSet() {
      return this._isSet(8);
   }

   public void setConnectionFactoryResourceLink(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactoryResourceLink;
      this._ConnectionFactoryResourceLink = param0;
      this._postSet(8, _oldVal, param0);
   }

   public int getJmsPollingIntervalSeconds() {
      return this._JmsPollingIntervalSeconds;
   }

   public boolean isJmsPollingIntervalSecondsInherited() {
      return false;
   }

   public boolean isJmsPollingIntervalSecondsSet() {
      return this._isSet(9);
   }

   public void setJmsPollingIntervalSeconds(int param0) {
      LegalChecks.checkMin("JmsPollingIntervalSeconds", param0, 0);
      int _oldVal = this._JmsPollingIntervalSeconds;
      this._JmsPollingIntervalSeconds = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getJmsClientId() {
      return this._JmsClientId;
   }

   public boolean isJmsClientIdInherited() {
      return false;
   }

   public boolean isJmsClientIdSet() {
      return this._isSet(10);
   }

   public void setJmsClientId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JmsClientId;
      this._JmsClientId = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isGenerateUniqueJmsClientId() {
      return this._GenerateUniqueJmsClientId;
   }

   public boolean isGenerateUniqueJmsClientIdInherited() {
      return false;
   }

   public boolean isGenerateUniqueJmsClientIdSet() {
      return this._isSet(11);
   }

   public void setGenerateUniqueJmsClientId(boolean param0) {
      boolean _oldVal = this._GenerateUniqueJmsClientId;
      this._GenerateUniqueJmsClientId = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isDurableSubscriptionDeletion() {
      return this._DurableSubscriptionDeletion;
   }

   public boolean isDurableSubscriptionDeletionInherited() {
      return false;
   }

   public boolean isDurableSubscriptionDeletionSet() {
      return this._isSet(12);
   }

   public void setDurableSubscriptionDeletion(boolean param0) {
      boolean _oldVal = this._DurableSubscriptionDeletion;
      this._DurableSubscriptionDeletion = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getMaxMessagesInTransaction() {
      return this._MaxMessagesInTransaction;
   }

   public boolean isMaxMessagesInTransactionInherited() {
      return false;
   }

   public boolean isMaxMessagesInTransactionSet() {
      return this._isSet(13);
   }

   public void setMaxMessagesInTransaction(int param0) {
      LegalChecks.checkMin("MaxMessagesInTransaction", param0, 1);
      int _oldVal = this._MaxMessagesInTransaction;
      this._MaxMessagesInTransaction = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getDistributedDestinationConnection() {
      return this._DistributedDestinationConnection;
   }

   public boolean isDistributedDestinationConnectionInherited() {
      return false;
   }

   public boolean isDistributedDestinationConnectionSet() {
      return this._isSet(14);
   }

   public void setDistributedDestinationConnection(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"LocalOnly", "EveryMember"};
      param0 = LegalChecks.checkInEnum("DistributedDestinationConnection", param0, _set);
      String _oldVal = this._DistributedDestinationConnection;
      this._DistributedDestinationConnection = param0;
      this._postSet(14, _oldVal, param0);
   }

   public boolean isUse81StylePolling() {
      return this._Use81StylePolling;
   }

   public boolean isUse81StylePollingInherited() {
      return false;
   }

   public boolean isUse81StylePollingSet() {
      return this._isSet(15);
   }

   public void setUse81StylePolling(boolean param0) {
      boolean _oldVal = this._Use81StylePolling;
      this._Use81StylePolling = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getInitSuspendSeconds() {
      return this._InitSuspendSeconds;
   }

   public boolean isInitSuspendSecondsInherited() {
      return false;
   }

   public boolean isInitSuspendSecondsSet() {
      return this._isSet(16);
   }

   public void setInitSuspendSeconds(int param0) {
      int _oldVal = this._InitSuspendSeconds;
      this._InitSuspendSeconds = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getMaxSuspendSeconds() {
      return this._MaxSuspendSeconds;
   }

   public boolean isMaxSuspendSecondsInherited() {
      return false;
   }

   public boolean isMaxSuspendSecondsSet() {
      return this._isSet(17);
   }

   public void setMaxSuspendSeconds(int param0) {
      LegalChecks.checkMin("MaxSuspendSeconds", param0, 0);
      int _oldVal = this._MaxSuspendSeconds;
      this._MaxSuspendSeconds = param0;
      this._postSet(17, _oldVal, param0);
   }

   public SecurityPluginBean getSecurityPlugin() {
      return this._SecurityPlugin;
   }

   public boolean isSecurityPluginInherited() {
      return false;
   }

   public boolean isSecurityPluginSet() {
      return this._isSet(18) || this._isAnythingSet((AbstractDescriptorBean)this.getSecurityPlugin());
   }

   public void setSecurityPlugin(SecurityPluginBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 18)) {
         this._postCreate(_child);
      }

      SecurityPluginBean _oldVal = this._SecurityPlugin;
      this._SecurityPlugin = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(19);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(19, _oldVal, param0);
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
      return super._isAnythingSet() || this.isPoolSet() || this.isSecurityPluginSet() || this.isTimerDescriptorSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._ConnectionFactoryJNDIName = "weblogic.jms.MessageDrivenBeanConnectionFactory";
               if (initOne) {
                  break;
               }
            case 8:
               this._ConnectionFactoryResourceLink = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._DestinationJNDIName = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._DestinationResourceLink = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._DistributedDestinationConnection = "LocalOnly";
               if (initOne) {
                  break;
               }
            case 19:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._InitSuspendSeconds = 5;
               if (initOne) {
                  break;
               }
            case 4:
               this._InitialContextFactory = "weblogic.jndi.WLInitialContextFactory";
               if (initOne) {
                  break;
               }
            case 10:
               this._JmsClientId = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._JmsPollingIntervalSeconds = 10;
               if (initOne) {
                  break;
               }
            case 13:
               this._MaxMessagesInTransaction = 1;
               if (initOne) {
                  break;
               }
            case 17:
               this._MaxSuspendSeconds = 60;
               if (initOne) {
                  break;
               }
            case 0:
               this._Pool = new PoolBeanImpl(this, 0);
               this._postCreate((AbstractDescriptorBean)this._Pool);
               if (initOne) {
                  break;
               }
            case 5:
               this._ProviderUrl = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ResourceAdapterJNDIName = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._SecurityPlugin = new SecurityPluginBeanImpl(this, 18);
               this._postCreate((AbstractDescriptorBean)this._SecurityPlugin);
               if (initOne) {
                  break;
               }
            case 1:
               this._TimerDescriptor = new TimerDescriptorBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._TimerDescriptor);
               if (initOne) {
                  break;
               }
            case 12:
               this._DurableSubscriptionDeletion = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._GenerateUniqueJmsClientId = false;
               if (initOne) {
                  break;
               }
            case 15:
               this._Use81StylePolling = false;
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
            case 2:
               if (s.equals("id")) {
                  return 19;
               }
            case 3:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 14:
            case 17:
            case 18:
            case 22:
            case 24:
            case 30:
            case 31:
            case 33:
            default:
               break;
            case 4:
               if (s.equals("pool")) {
                  return 0;
               }
               break;
            case 12:
               if (s.equals("provider-url")) {
                  return 5;
               }
               break;
            case 13:
               if (s.equals("jms-client-id")) {
                  return 10;
               }
               break;
            case 15:
               if (s.equals("security-plugin")) {
                  return 18;
               }
               break;
            case 16:
               if (s.equals("timer-descriptor")) {
                  return 1;
               }
               break;
            case 19:
               if (s.equals("max-suspend-seconds")) {
                  return 17;
               }

               if (s.equals("use81-style-polling")) {
                  return 15;
               }
               break;
            case 20:
               if (s.equals("init-suspend-seconds")) {
                  return 16;
               }
               break;
            case 21:
               if (s.equals("destination-jndi-name")) {
                  return 3;
               }
               break;
            case 23:
               if (s.equals("initial-context-factory")) {
                  return 4;
               }
               break;
            case 25:
               if (s.equals("destination-resource-link")) {
                  return 7;
               }
               break;
            case 26:
               if (s.equals("resource-adapter-jndi-name")) {
                  return 2;
               }
               break;
            case 27:
               if (s.equals("max-messages-in-transaction")) {
                  return 13;
               }
               break;
            case 28:
               if (s.equals("connection-factory-jndi-name")) {
                  return 6;
               }

               if (s.equals("jms-polling-interval-seconds")) {
                  return 9;
               }
               break;
            case 29:
               if (s.equals("durable-subscription-deletion")) {
                  return 12;
               }

               if (s.equals("generate-unique-jms-client-id")) {
                  return 11;
               }
               break;
            case 32:
               if (s.equals("connection-factory-resource-link")) {
                  return 8;
               }
               break;
            case 34:
               if (s.equals("distributed-destination-connection")) {
                  return 14;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new PoolBeanImpl.SchemaHelper2();
            case 1:
               return new TimerDescriptorBeanImpl.SchemaHelper2();
            case 18:
               return new SecurityPluginBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "pool";
            case 1:
               return "timer-descriptor";
            case 2:
               return "resource-adapter-jndi-name";
            case 3:
               return "destination-jndi-name";
            case 4:
               return "initial-context-factory";
            case 5:
               return "provider-url";
            case 6:
               return "connection-factory-jndi-name";
            case 7:
               return "destination-resource-link";
            case 8:
               return "connection-factory-resource-link";
            case 9:
               return "jms-polling-interval-seconds";
            case 10:
               return "jms-client-id";
            case 11:
               return "generate-unique-jms-client-id";
            case 12:
               return "durable-subscription-deletion";
            case 13:
               return "max-messages-in-transaction";
            case 14:
               return "distributed-destination-connection";
            case 15:
               return "use81-style-polling";
            case 16:
               return "init-suspend-seconds";
            case 17:
               return "max-suspend-seconds";
            case 18:
               return "security-plugin";
            case 19:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 18:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
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
            default:
               return super.isConfigurable(propIndex);
            case 16:
               return true;
            case 17:
               return true;
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MessageDrivenDescriptorBeanImpl bean;

      protected Helper(MessageDrivenDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Pool";
            case 1:
               return "TimerDescriptor";
            case 2:
               return "ResourceAdapterJNDIName";
            case 3:
               return "DestinationJNDIName";
            case 4:
               return "InitialContextFactory";
            case 5:
               return "ProviderUrl";
            case 6:
               return "ConnectionFactoryJNDIName";
            case 7:
               return "DestinationResourceLink";
            case 8:
               return "ConnectionFactoryResourceLink";
            case 9:
               return "JmsPollingIntervalSeconds";
            case 10:
               return "JmsClientId";
            case 11:
               return "GenerateUniqueJmsClientId";
            case 12:
               return "DurableSubscriptionDeletion";
            case 13:
               return "MaxMessagesInTransaction";
            case 14:
               return "DistributedDestinationConnection";
            case 15:
               return "Use81StylePolling";
            case 16:
               return "InitSuspendSeconds";
            case 17:
               return "MaxSuspendSeconds";
            case 18:
               return "SecurityPlugin";
            case 19:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactoryJNDIName")) {
            return 6;
         } else if (propName.equals("ConnectionFactoryResourceLink")) {
            return 8;
         } else if (propName.equals("DestinationJNDIName")) {
            return 3;
         } else if (propName.equals("DestinationResourceLink")) {
            return 7;
         } else if (propName.equals("DistributedDestinationConnection")) {
            return 14;
         } else if (propName.equals("Id")) {
            return 19;
         } else if (propName.equals("InitSuspendSeconds")) {
            return 16;
         } else if (propName.equals("InitialContextFactory")) {
            return 4;
         } else if (propName.equals("JmsClientId")) {
            return 10;
         } else if (propName.equals("JmsPollingIntervalSeconds")) {
            return 9;
         } else if (propName.equals("MaxMessagesInTransaction")) {
            return 13;
         } else if (propName.equals("MaxSuspendSeconds")) {
            return 17;
         } else if (propName.equals("Pool")) {
            return 0;
         } else if (propName.equals("ProviderUrl")) {
            return 5;
         } else if (propName.equals("ResourceAdapterJNDIName")) {
            return 2;
         } else if (propName.equals("SecurityPlugin")) {
            return 18;
         } else if (propName.equals("TimerDescriptor")) {
            return 1;
         } else if (propName.equals("DurableSubscriptionDeletion")) {
            return 12;
         } else if (propName.equals("GenerateUniqueJmsClientId")) {
            return 11;
         } else {
            return propName.equals("Use81StylePolling") ? 15 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getPool() != null) {
            iterators.add(new ArrayIterator(new PoolBean[]{this.bean.getPool()}));
         }

         if (this.bean.getSecurityPlugin() != null) {
            iterators.add(new ArrayIterator(new SecurityPluginBean[]{this.bean.getSecurityPlugin()}));
         }

         if (this.bean.getTimerDescriptor() != null) {
            iterators.add(new ArrayIterator(new TimerDescriptorBean[]{this.bean.getTimerDescriptor()}));
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
            if (this.bean.isConnectionFactoryJNDINameSet()) {
               buf.append("ConnectionFactoryJNDIName");
               buf.append(String.valueOf(this.bean.getConnectionFactoryJNDIName()));
            }

            if (this.bean.isConnectionFactoryResourceLinkSet()) {
               buf.append("ConnectionFactoryResourceLink");
               buf.append(String.valueOf(this.bean.getConnectionFactoryResourceLink()));
            }

            if (this.bean.isDestinationJNDINameSet()) {
               buf.append("DestinationJNDIName");
               buf.append(String.valueOf(this.bean.getDestinationJNDIName()));
            }

            if (this.bean.isDestinationResourceLinkSet()) {
               buf.append("DestinationResourceLink");
               buf.append(String.valueOf(this.bean.getDestinationResourceLink()));
            }

            if (this.bean.isDistributedDestinationConnectionSet()) {
               buf.append("DistributedDestinationConnection");
               buf.append(String.valueOf(this.bean.getDistributedDestinationConnection()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInitSuspendSecondsSet()) {
               buf.append("InitSuspendSeconds");
               buf.append(String.valueOf(this.bean.getInitSuspendSeconds()));
            }

            if (this.bean.isInitialContextFactorySet()) {
               buf.append("InitialContextFactory");
               buf.append(String.valueOf(this.bean.getInitialContextFactory()));
            }

            if (this.bean.isJmsClientIdSet()) {
               buf.append("JmsClientId");
               buf.append(String.valueOf(this.bean.getJmsClientId()));
            }

            if (this.bean.isJmsPollingIntervalSecondsSet()) {
               buf.append("JmsPollingIntervalSeconds");
               buf.append(String.valueOf(this.bean.getJmsPollingIntervalSeconds()));
            }

            if (this.bean.isMaxMessagesInTransactionSet()) {
               buf.append("MaxMessagesInTransaction");
               buf.append(String.valueOf(this.bean.getMaxMessagesInTransaction()));
            }

            if (this.bean.isMaxSuspendSecondsSet()) {
               buf.append("MaxSuspendSeconds");
               buf.append(String.valueOf(this.bean.getMaxSuspendSeconds()));
            }

            childValue = this.computeChildHashValue(this.bean.getPool());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isProviderUrlSet()) {
               buf.append("ProviderUrl");
               buf.append(String.valueOf(this.bean.getProviderUrl()));
            }

            if (this.bean.isResourceAdapterJNDINameSet()) {
               buf.append("ResourceAdapterJNDIName");
               buf.append(String.valueOf(this.bean.getResourceAdapterJNDIName()));
            }

            childValue = this.computeChildHashValue(this.bean.getSecurityPlugin());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTimerDescriptor());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDurableSubscriptionDeletionSet()) {
               buf.append("DurableSubscriptionDeletion");
               buf.append(String.valueOf(this.bean.isDurableSubscriptionDeletion()));
            }

            if (this.bean.isGenerateUniqueJmsClientIdSet()) {
               buf.append("GenerateUniqueJmsClientId");
               buf.append(String.valueOf(this.bean.isGenerateUniqueJmsClientId()));
            }

            if (this.bean.isUse81StylePollingSet()) {
               buf.append("Use81StylePolling");
               buf.append(String.valueOf(this.bean.isUse81StylePolling()));
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
            MessageDrivenDescriptorBeanImpl otherTyped = (MessageDrivenDescriptorBeanImpl)other;
            this.computeDiff("ConnectionFactoryJNDIName", this.bean.getConnectionFactoryJNDIName(), otherTyped.getConnectionFactoryJNDIName(), true);
            this.computeDiff("ConnectionFactoryResourceLink", this.bean.getConnectionFactoryResourceLink(), otherTyped.getConnectionFactoryResourceLink(), true);
            this.computeDiff("DestinationJNDIName", this.bean.getDestinationJNDIName(), otherTyped.getDestinationJNDIName(), true);
            this.computeDiff("DestinationResourceLink", this.bean.getDestinationResourceLink(), otherTyped.getDestinationResourceLink(), true);
            this.computeDiff("DistributedDestinationConnection", this.bean.getDistributedDestinationConnection(), otherTyped.getDistributedDestinationConnection(), true);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("InitSuspendSeconds", this.bean.getInitSuspendSeconds(), otherTyped.getInitSuspendSeconds(), true);
            this.computeDiff("InitialContextFactory", this.bean.getInitialContextFactory(), otherTyped.getInitialContextFactory(), true);
            this.computeDiff("JmsClientId", this.bean.getJmsClientId(), otherTyped.getJmsClientId(), true);
            this.computeDiff("JmsPollingIntervalSeconds", this.bean.getJmsPollingIntervalSeconds(), otherTyped.getJmsPollingIntervalSeconds(), true);
            this.computeDiff("MaxMessagesInTransaction", this.bean.getMaxMessagesInTransaction(), otherTyped.getMaxMessagesInTransaction(), true);
            this.computeDiff("MaxSuspendSeconds", this.bean.getMaxSuspendSeconds(), otherTyped.getMaxSuspendSeconds(), true);
            this.computeSubDiff("Pool", this.bean.getPool(), otherTyped.getPool());
            this.computeDiff("ProviderUrl", this.bean.getProviderUrl(), otherTyped.getProviderUrl(), true);
            this.computeDiff("ResourceAdapterJNDIName", this.bean.getResourceAdapterJNDIName(), otherTyped.getResourceAdapterJNDIName(), true);
            this.computeSubDiff("SecurityPlugin", this.bean.getSecurityPlugin(), otherTyped.getSecurityPlugin());
            this.computeSubDiff("TimerDescriptor", this.bean.getTimerDescriptor(), otherTyped.getTimerDescriptor());
            this.computeDiff("DurableSubscriptionDeletion", this.bean.isDurableSubscriptionDeletion(), otherTyped.isDurableSubscriptionDeletion(), true);
            this.computeDiff("GenerateUniqueJmsClientId", this.bean.isGenerateUniqueJmsClientId(), otherTyped.isGenerateUniqueJmsClientId(), true);
            this.computeDiff("Use81StylePolling", this.bean.isUse81StylePolling(), otherTyped.isUse81StylePolling(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MessageDrivenDescriptorBeanImpl original = (MessageDrivenDescriptorBeanImpl)event.getSourceBean();
            MessageDrivenDescriptorBeanImpl proposed = (MessageDrivenDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionFactoryJNDIName")) {
                  original.setConnectionFactoryJNDIName(proposed.getConnectionFactoryJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ConnectionFactoryResourceLink")) {
                  original.setConnectionFactoryResourceLink(proposed.getConnectionFactoryResourceLink());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("DestinationJNDIName")) {
                  original.setDestinationJNDIName(proposed.getDestinationJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("DestinationResourceLink")) {
                  original.setDestinationResourceLink(proposed.getDestinationResourceLink());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("DistributedDestinationConnection")) {
                  original.setDistributedDestinationConnection(proposed.getDistributedDestinationConnection());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("InitSuspendSeconds")) {
                  original.setInitSuspendSeconds(proposed.getInitSuspendSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("InitialContextFactory")) {
                  original.setInitialContextFactory(proposed.getInitialContextFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("JmsClientId")) {
                  original.setJmsClientId(proposed.getJmsClientId());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("JmsPollingIntervalSeconds")) {
                  original.setJmsPollingIntervalSeconds(proposed.getJmsPollingIntervalSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("MaxMessagesInTransaction")) {
                  original.setMaxMessagesInTransaction(proposed.getMaxMessagesInTransaction());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("MaxSuspendSeconds")) {
                  original.setMaxSuspendSeconds(proposed.getMaxSuspendSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("Pool")) {
                  if (type == 2) {
                     original.setPool((PoolBean)this.createCopy((AbstractDescriptorBean)proposed.getPool()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Pool", (DescriptorBean)original.getPool());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ProviderUrl")) {
                  original.setProviderUrl(proposed.getProviderUrl());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ResourceAdapterJNDIName")) {
                  original.setResourceAdapterJNDIName(proposed.getResourceAdapterJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SecurityPlugin")) {
                  if (type == 2) {
                     original.setSecurityPlugin((SecurityPluginBean)this.createCopy((AbstractDescriptorBean)proposed.getSecurityPlugin()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SecurityPlugin", (DescriptorBean)original.getSecurityPlugin());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("TimerDescriptor")) {
                  if (type == 2) {
                     original.setTimerDescriptor((TimerDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getTimerDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TimerDescriptor", (DescriptorBean)original.getTimerDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DurableSubscriptionDeletion")) {
                  original.setDurableSubscriptionDeletion(proposed.isDurableSubscriptionDeletion());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("GenerateUniqueJmsClientId")) {
                  original.setGenerateUniqueJmsClientId(proposed.isGenerateUniqueJmsClientId());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Use81StylePolling")) {
                  original.setUse81StylePolling(proposed.isUse81StylePolling());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
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
            MessageDrivenDescriptorBeanImpl copy = (MessageDrivenDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryJNDIName")) && this.bean.isConnectionFactoryJNDINameSet()) {
               copy.setConnectionFactoryJNDIName(this.bean.getConnectionFactoryJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryResourceLink")) && this.bean.isConnectionFactoryResourceLinkSet()) {
               copy.setConnectionFactoryResourceLink(this.bean.getConnectionFactoryResourceLink());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationJNDIName")) && this.bean.isDestinationJNDINameSet()) {
               copy.setDestinationJNDIName(this.bean.getDestinationJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationResourceLink")) && this.bean.isDestinationResourceLinkSet()) {
               copy.setDestinationResourceLink(this.bean.getDestinationResourceLink());
            }

            if ((excludeProps == null || !excludeProps.contains("DistributedDestinationConnection")) && this.bean.isDistributedDestinationConnectionSet()) {
               copy.setDistributedDestinationConnection(this.bean.getDistributedDestinationConnection());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InitSuspendSeconds")) && this.bean.isInitSuspendSecondsSet()) {
               copy.setInitSuspendSeconds(this.bean.getInitSuspendSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialContextFactory")) && this.bean.isInitialContextFactorySet()) {
               copy.setInitialContextFactory(this.bean.getInitialContextFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("JmsClientId")) && this.bean.isJmsClientIdSet()) {
               copy.setJmsClientId(this.bean.getJmsClientId());
            }

            if ((excludeProps == null || !excludeProps.contains("JmsPollingIntervalSeconds")) && this.bean.isJmsPollingIntervalSecondsSet()) {
               copy.setJmsPollingIntervalSeconds(this.bean.getJmsPollingIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxMessagesInTransaction")) && this.bean.isMaxMessagesInTransactionSet()) {
               copy.setMaxMessagesInTransaction(this.bean.getMaxMessagesInTransaction());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxSuspendSeconds")) && this.bean.isMaxSuspendSecondsSet()) {
               copy.setMaxSuspendSeconds(this.bean.getMaxSuspendSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("Pool")) && this.bean.isPoolSet() && !copy._isSet(0)) {
               Object o = this.bean.getPool();
               copy.setPool((PoolBean)null);
               copy.setPool(o == null ? null : (PoolBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ProviderUrl")) && this.bean.isProviderUrlSet()) {
               copy.setProviderUrl(this.bean.getProviderUrl());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceAdapterJNDIName")) && this.bean.isResourceAdapterJNDINameSet()) {
               copy.setResourceAdapterJNDIName(this.bean.getResourceAdapterJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityPlugin")) && this.bean.isSecurityPluginSet() && !copy._isSet(18)) {
               Object o = this.bean.getSecurityPlugin();
               copy.setSecurityPlugin((SecurityPluginBean)null);
               copy.setSecurityPlugin(o == null ? null : (SecurityPluginBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TimerDescriptor")) && this.bean.isTimerDescriptorSet() && !copy._isSet(1)) {
               Object o = this.bean.getTimerDescriptor();
               copy.setTimerDescriptor((TimerDescriptorBean)null);
               copy.setTimerDescriptor(o == null ? null : (TimerDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DurableSubscriptionDeletion")) && this.bean.isDurableSubscriptionDeletionSet()) {
               copy.setDurableSubscriptionDeletion(this.bean.isDurableSubscriptionDeletion());
            }

            if ((excludeProps == null || !excludeProps.contains("GenerateUniqueJmsClientId")) && this.bean.isGenerateUniqueJmsClientIdSet()) {
               copy.setGenerateUniqueJmsClientId(this.bean.isGenerateUniqueJmsClientId());
            }

            if ((excludeProps == null || !excludeProps.contains("Use81StylePolling")) && this.bean.isUse81StylePollingSet()) {
               copy.setUse81StylePolling(this.bean.isUse81StylePolling());
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
         this.inferSubTree(this.bean.getPool(), clazz, annotation);
         this.inferSubTree(this.bean.getSecurityPlugin(), clazz, annotation);
         this.inferSubTree(this.bean.getTimerDescriptor(), clazz, annotation);
      }
   }
}

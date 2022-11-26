package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.jms.module.validators.JMSModuleValidator;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.JMSConnectionFactory;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JMSConnectionFactoryMBeanImpl extends DeploymentMBeanImpl implements JMSConnectionFactoryMBean, Serializable {
   private String _AcknowledgePolicy;
   private boolean _AllowCloseInOnMessage;
   private String _ClientId;
   private String _DefaultDeliveryMode;
   private int _DefaultPriority;
   private long _DefaultRedeliveryDelay;
   private long _DefaultTimeToDeliver;
   private long _DefaultTimeToLive;
   private boolean _DynamicallyCreated;
   private boolean _FlowControlEnabled;
   private int _FlowInterval;
   private int _FlowMaximum;
   private int _FlowMinimum;
   private int _FlowSteps;
   private String _JNDIName;
   private boolean _LoadBalancingEnabled;
   private int _MessagesMaximum;
   private String _Name;
   private String _Notes;
   private String _OverrunPolicy;
   private String _ProducerLoadBalancingPolicy;
   private long _SendTimeout;
   private boolean _ServerAffinityEnabled;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private long _TransactionTimeout;
   private boolean _UserTransactionsEnabled;
   private boolean _XAConnectionFactoryEnabled;
   private boolean _XAServerEnabled;
   private transient JMSConnectionFactory _customizer;
   private static SchemaHelper2 _schemaHelper;

   public JMSConnectionFactoryMBeanImpl() {
      try {
         this._customizer = new JMSConnectionFactory(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JMSConnectionFactoryMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JMSConnectionFactory(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JMSConnectionFactoryMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JMSConnectionFactory(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getJNDIName() {
      return this._customizer.getJNDIName();
   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public TargetMBean[] getTargets() {
      return this._customizer.getTargets();
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isJNDINameInherited() {
      return false;
   }

   public boolean isJNDINameSet() {
      return this._isSet(12);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTargetsInherited() {
      return false;
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public void setTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Targets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        JMSConnectionFactoryMBeanImpl.this.addTarget((TargetMBean)value);
                        JMSConnectionFactoryMBeanImpl.this._getHelper().reorderArrayObjects((Object[])JMSConnectionFactoryMBeanImpl.this._Targets, this.getHandbackObject());
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
               TargetMBean[] var6 = this._Targets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTarget(member);
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
         TargetMBean[] _oldVal = this._Targets;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Targets);
      }
   }

   public void setJNDIName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      JMSModuleValidator.validateCFJNDIName(param0);
      String _oldVal = this.getJNDIName();
      this._customizer.setJNDIName(param0);
      this._postSet(12, _oldVal, param0);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return JMSConnectionFactoryMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      TargetMBean[] _oldVal = this.getTargets();
      this._customizer.setTargets(param0);
      this._postSet(10, _oldVal, param0);
   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public String getClientId() {
      return this._customizer.getClientId();
   }

   public String getNotes() {
      return this._customizer.getNotes();
   }

   public boolean isClientIdInherited() {
      return false;
   }

   public boolean isClientIdSet() {
      return this._isSet(13);
   }

   public boolean isNotesInherited() {
      return false;
   }

   public boolean isNotesSet() {
      return this._isSet(3);
   }

   public boolean removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setClientId(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getClientId();
      this._customizer.setClientId(param0);
      this._postSet(13, _oldVal, param0);
   }

   public void setNotes(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getNotes();
      this._customizer.setNotes(param0);
      this._postSet(3, _oldVal, param0);
   }

   public int getDefaultPriority() {
      return this._customizer.getDefaultPriority();
   }

   public boolean isDefaultPriorityInherited() {
      return false;
   }

   public boolean isDefaultPrioritySet() {
      return this._isSet(14);
   }

   public void setDefaultPriority(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("DefaultPriority", (long)param0, 0L, 9L);
      int _oldVal = this.getDefaultPriority();
      this._customizer.setDefaultPriority(param0);
      this._postSet(14, _oldVal, param0);
   }

   public long getDefaultTimeToDeliver() {
      return this._customizer.getDefaultTimeToDeliver();
   }

   public boolean isDefaultTimeToDeliverInherited() {
      return false;
   }

   public boolean isDefaultTimeToDeliverSet() {
      return this._isSet(15);
   }

   public void setDefaultTimeToDeliver(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("DefaultTimeToDeliver", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this.getDefaultTimeToDeliver();
      this._customizer.setDefaultTimeToDeliver(param0);
      this._postSet(15, _oldVal, param0);
   }

   public long getDefaultTimeToLive() {
      return this._customizer.getDefaultTimeToLive();
   }

   public boolean isDefaultTimeToLiveInherited() {
      return false;
   }

   public boolean isDefaultTimeToLiveSet() {
      return this._isSet(16);
   }

   public void setDefaultTimeToLive(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("DefaultTimeToLive", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this.getDefaultTimeToLive();
      this._customizer.setDefaultTimeToLive(param0);
      this._postSet(16, _oldVal, param0);
   }

   public long getSendTimeout() {
      return this._customizer.getSendTimeout();
   }

   public boolean isSendTimeoutInherited() {
      return false;
   }

   public boolean isSendTimeoutSet() {
      return this._isSet(17);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setSendTimeout(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("SendTimeout", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this.getSendTimeout();
      this._customizer.setSendTimeout(param0);
      this._postSet(17, _oldVal, param0);
   }

   public String getDefaultDeliveryMode() {
      return this._customizer.getDefaultDeliveryMode();
   }

   public boolean isDefaultDeliveryModeInherited() {
      return false;
   }

   public boolean isDefaultDeliveryModeSet() {
      return this._isSet(18);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setDefaultDeliveryMode(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Persistent", "Non-Persistent"};
      param0 = LegalChecks.checkInEnum("DefaultDeliveryMode", param0, _set);
      String _oldVal = this.getDefaultDeliveryMode();
      this._customizer.setDefaultDeliveryMode(param0);
      this._postSet(18, _oldVal, param0);
   }

   public long getDefaultRedeliveryDelay() {
      return this._customizer.getDefaultRedeliveryDelay();
   }

   public boolean isDefaultRedeliveryDelayInherited() {
      return false;
   }

   public boolean isDefaultRedeliveryDelaySet() {
      return this._isSet(19);
   }

   public void setDefaultRedeliveryDelay(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("DefaultRedeliveryDelay", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this.getDefaultRedeliveryDelay();
      this._customizer.setDefaultRedeliveryDelay(param0);
      this._postSet(19, _oldVal, param0);
   }

   public long getTransactionTimeout() {
      return this._customizer.getTransactionTimeout();
   }

   public boolean isTransactionTimeoutInherited() {
      return false;
   }

   public boolean isTransactionTimeoutSet() {
      return this._isSet(20);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public void setTransactionTimeout(long param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("TransactionTimeout", param0, 0L, 2147483647L);
      long _oldVal = this.getTransactionTimeout();
      this._customizer.setTransactionTimeout(param0);
      this._postSet(20, _oldVal, param0);
   }

   public boolean isUserTransactionsEnabled() {
      return this._customizer.isUserTransactionsEnabled();
   }

   public boolean isUserTransactionsEnabledInherited() {
      return false;
   }

   public boolean isUserTransactionsEnabledSet() {
      return this._isSet(21);
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setUserTransactionsEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this.isUserTransactionsEnabled();
      this._customizer.setUserTransactionsEnabled(param0);
      this._postSet(21, _oldVal, param0);
   }

   public boolean getAllowCloseInOnMessage() {
      return this._customizer.getAllowCloseInOnMessage();
   }

   public boolean isAllowCloseInOnMessageInherited() {
      return false;
   }

   public boolean isAllowCloseInOnMessageSet() {
      return this._isSet(22);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void setAllowCloseInOnMessage(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this.getAllowCloseInOnMessage();
      this._customizer.setAllowCloseInOnMessage(param0);
      this._postSet(22, _oldVal, param0);
   }

   public int getMessagesMaximum() {
      return this._customizer.getMessagesMaximum();
   }

   public boolean isMessagesMaximumInherited() {
      return false;
   }

   public boolean isMessagesMaximumSet() {
      return this._isSet(23);
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setMessagesMaximum(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("MessagesMaximum", (long)param0, -1L, 2147483647L);
      int _oldVal = this.getMessagesMaximum();
      this._customizer.setMessagesMaximum(param0);
      this._postSet(23, _oldVal, param0);
   }

   public String getOverrunPolicy() {
      return this._customizer.getOverrunPolicy();
   }

   public boolean isOverrunPolicyInherited() {
      return false;
   }

   public boolean isOverrunPolicySet() {
      return this._isSet(24);
   }

   public void setOverrunPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"KeepOld", "KeepNew"};
      param0 = LegalChecks.checkInEnum("OverrunPolicy", param0, _set);
      String _oldVal = this.getOverrunPolicy();
      this._customizer.setOverrunPolicy(param0);
      this._postSet(24, _oldVal, param0);
   }

   public boolean isXAConnectionFactoryEnabled() {
      return this._customizer.isXAConnectionFactoryEnabled();
   }

   public boolean isXAConnectionFactoryEnabledInherited() {
      return false;
   }

   public boolean isXAConnectionFactoryEnabledSet() {
      return this._isSet(25);
   }

   public void setXAConnectionFactoryEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this.isXAConnectionFactoryEnabled();
      this._customizer.setXAConnectionFactoryEnabled(param0);
      this._postSet(25, _oldVal, param0);
   }

   public String getAcknowledgePolicy() {
      if (!this._isSet(26)) {
         return this._isSecureModeEnabled() ? "All" : "All";
      } else {
         return this._customizer.getAcknowledgePolicy();
      }
   }

   public boolean isAcknowledgePolicyInherited() {
      return false;
   }

   public boolean isAcknowledgePolicySet() {
      return this._isSet(26);
   }

   public void setAcknowledgePolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"All", "Previous"};
      param0 = LegalChecks.checkInEnum("AcknowledgePolicy", param0, _set);
      String _oldVal = this.getAcknowledgePolicy();
      this._customizer.setAcknowledgePolicy(param0);
      this._postSet(26, _oldVal, param0);
   }

   public int getFlowMinimum() {
      return this._customizer.getFlowMinimum();
   }

   public boolean isFlowMinimumInherited() {
      return false;
   }

   public boolean isFlowMinimumSet() {
      return this._isSet(27);
   }

   public void setFlowMinimum(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("FlowMinimum", (long)param0, 1L, 2147483647L);
      int _oldVal = this.getFlowMinimum();
      this._customizer.setFlowMinimum(param0);
      this._postSet(27, _oldVal, param0);
   }

   public int getFlowMaximum() {
      return this._customizer.getFlowMaximum();
   }

   public boolean isFlowMaximumInherited() {
      return false;
   }

   public boolean isFlowMaximumSet() {
      return this._isSet(28);
   }

   public void setFlowMaximum(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("FlowMaximum", (long)param0, 1L, 2147483647L);
      int _oldVal = this.getFlowMaximum();
      this._customizer.setFlowMaximum(param0);
      this._postSet(28, _oldVal, param0);
   }

   public int getFlowInterval() {
      return this._customizer.getFlowInterval();
   }

   public boolean isFlowIntervalInherited() {
      return false;
   }

   public boolean isFlowIntervalSet() {
      return this._isSet(29);
   }

   public void setFlowInterval(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("FlowInterval", (long)param0, 0L, 2147483647L);
      int _oldVal = this.getFlowInterval();
      this._customizer.setFlowInterval(param0);
      this._postSet(29, _oldVal, param0);
   }

   public int getFlowSteps() {
      return this._customizer.getFlowSteps();
   }

   public boolean isFlowStepsInherited() {
      return false;
   }

   public boolean isFlowStepsSet() {
      return this._isSet(30);
   }

   public void setFlowSteps(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("FlowSteps", (long)param0, 1L, 2147483647L);
      int _oldVal = this.getFlowSteps();
      this._customizer.setFlowSteps(param0);
      this._postSet(30, _oldVal, param0);
   }

   public boolean isFlowControlEnabled() {
      return this._FlowControlEnabled;
   }

   public boolean isFlowControlEnabledInherited() {
      return false;
   }

   public boolean isFlowControlEnabledSet() {
      return this._isSet(31);
   }

   public void setFlowControlEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this.isFlowControlEnabled();
      this._customizer.setFlowControlEnabled(param0);
      this._postSet(31, _oldVal, param0);
   }

   public boolean isLoadBalancingEnabled() {
      return this._LoadBalancingEnabled;
   }

   public boolean isLoadBalancingEnabledInherited() {
      return false;
   }

   public boolean isLoadBalancingEnabledSet() {
      return this._isSet(32);
   }

   public void setLoadBalancingEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this.isLoadBalancingEnabled();
      this._customizer.setLoadBalancingEnabled(param0);
      this._postSet(32, _oldVal, param0);
   }

   public boolean isServerAffinityEnabled() {
      return this._ServerAffinityEnabled;
   }

   public boolean isServerAffinityEnabledInherited() {
      return false;
   }

   public boolean isServerAffinityEnabledSet() {
      return this._isSet(33);
   }

   public void setServerAffinityEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this.isServerAffinityEnabled();
      this._customizer.setServerAffinityEnabled(param0);
      this._postSet(33, _oldVal, param0);
   }

   public String getProducerLoadBalancingPolicy() {
      return this._customizer.getProducerLoadBalancingPolicy();
   }

   public boolean isProducerLoadBalancingPolicyInherited() {
      return false;
   }

   public boolean isProducerLoadBalancingPolicySet() {
      return this._isSet(34);
   }

   public void setProducerLoadBalancingPolicy(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER, JMSConstants.PRODUCER_LB_POLICY_PER_JVM};
      param0 = LegalChecks.checkInEnum("ProducerLoadBalancingPolicy", param0, _set);
      String _oldVal = this.getProducerLoadBalancingPolicy();
      this._customizer.setProducerLoadBalancingPolicy(param0);
      this._postSet(34, _oldVal, param0);
   }

   public boolean isXAServerEnabled() {
      return this._customizer.isXAServerEnabled();
   }

   public boolean isXAServerEnabledInherited() {
      return false;
   }

   public boolean isXAServerEnabledSet() {
      return this._isSet(35);
   }

   public void setXAServerEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this.isXAServerEnabled();
      this._customizer.setXAServerEnabled(param0);
      this._postSet(35, _oldVal, param0);
   }

   public void useDelegates(JMSConnectionFactoryBean param0, SubDeploymentMBean param1) {
      this._customizer.useDelegates(param0, param1);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
         idx = 26;
      }

      try {
         switch (idx) {
            case 26:
               this._customizer.setAcknowledgePolicy("All");
               if (initOne) {
                  break;
               }
            case 22:
               this._customizer.setAllowCloseInOnMessage(false);
               if (initOne) {
                  break;
               }
            case 13:
               this._customizer.setClientId((String)null);
               if (initOne) {
                  break;
               }
            case 18:
               this._customizer.setDefaultDeliveryMode("Persistent");
               if (initOne) {
                  break;
               }
            case 14:
               this._customizer.setDefaultPriority(4);
               if (initOne) {
                  break;
               }
            case 19:
               this._customizer.setDefaultRedeliveryDelay(0L);
               if (initOne) {
                  break;
               }
            case 15:
               this._customizer.setDefaultTimeToDeliver(0L);
               if (initOne) {
                  break;
               }
            case 16:
               this._customizer.setDefaultTimeToLive(0L);
               if (initOne) {
                  break;
               }
            case 29:
               this._customizer.setFlowInterval(60);
               if (initOne) {
                  break;
               }
            case 28:
               this._customizer.setFlowMaximum(500);
               if (initOne) {
                  break;
               }
            case 27:
               this._customizer.setFlowMinimum(50);
               if (initOne) {
                  break;
               }
            case 30:
               this._customizer.setFlowSteps(10);
               if (initOne) {
                  break;
               }
            case 12:
               this._customizer.setJNDIName((String)null);
               if (initOne) {
                  break;
               }
            case 23:
               this._customizer.setMessagesMaximum(10);
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 3:
               this._customizer.setNotes((String)null);
               if (initOne) {
                  break;
               }
            case 24:
               this._customizer.setOverrunPolicy("KeepOld");
               if (initOne) {
                  break;
               }
            case 34:
               this._customizer.setProducerLoadBalancingPolicy(JMSConstants.PRODUCER_LB_POLICY_PER_MEMBER);
               if (initOne) {
                  break;
               }
            case 17:
               this._customizer.setSendTimeout(10L);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setTargets(new TargetMBean[0]);
               if (initOne) {
                  break;
               }
            case 20:
               this._customizer.setTransactionTimeout(3600L);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._customizer.setFlowControlEnabled(true);
               if (initOne) {
                  break;
               }
            case 32:
               this._customizer.setLoadBalancingEnabled(true);
               if (initOne) {
                  break;
               }
            case 33:
               this._customizer.setServerAffinityEnabled(true);
               if (initOne) {
                  break;
               }
            case 21:
               this._customizer.setUserTransactionsEnabled(false);
               if (initOne) {
                  break;
               }
            case 25:
               this._customizer.setXAConnectionFactoryEnabled(false);
               if (initOne) {
                  break;
               }
            case 35:
               this._customizer.setXAServerEnabled(false);
               if (initOne) {
                  break;
               }
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
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
      return "JMSConnectionFactory";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AcknowledgePolicy")) {
         oldVal = this._AcknowledgePolicy;
         this._AcknowledgePolicy = (String)v;
         this._postSet(26, oldVal, this._AcknowledgePolicy);
      } else {
         boolean oldVal;
         if (name.equals("AllowCloseInOnMessage")) {
            oldVal = this._AllowCloseInOnMessage;
            this._AllowCloseInOnMessage = (Boolean)v;
            this._postSet(22, oldVal, this._AllowCloseInOnMessage);
         } else if (name.equals("ClientId")) {
            oldVal = this._ClientId;
            this._ClientId = (String)v;
            this._postSet(13, oldVal, this._ClientId);
         } else if (name.equals("DefaultDeliveryMode")) {
            oldVal = this._DefaultDeliveryMode;
            this._DefaultDeliveryMode = (String)v;
            this._postSet(18, oldVal, this._DefaultDeliveryMode);
         } else {
            int oldVal;
            if (name.equals("DefaultPriority")) {
               oldVal = this._DefaultPriority;
               this._DefaultPriority = (Integer)v;
               this._postSet(14, oldVal, this._DefaultPriority);
            } else {
               long oldVal;
               if (name.equals("DefaultRedeliveryDelay")) {
                  oldVal = this._DefaultRedeliveryDelay;
                  this._DefaultRedeliveryDelay = (Long)v;
                  this._postSet(19, oldVal, this._DefaultRedeliveryDelay);
               } else if (name.equals("DefaultTimeToDeliver")) {
                  oldVal = this._DefaultTimeToDeliver;
                  this._DefaultTimeToDeliver = (Long)v;
                  this._postSet(15, oldVal, this._DefaultTimeToDeliver);
               } else if (name.equals("DefaultTimeToLive")) {
                  oldVal = this._DefaultTimeToLive;
                  this._DefaultTimeToLive = (Long)v;
                  this._postSet(16, oldVal, this._DefaultTimeToLive);
               } else if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("FlowControlEnabled")) {
                  oldVal = this._FlowControlEnabled;
                  this._FlowControlEnabled = (Boolean)v;
                  this._postSet(31, oldVal, this._FlowControlEnabled);
               } else if (name.equals("FlowInterval")) {
                  oldVal = this._FlowInterval;
                  this._FlowInterval = (Integer)v;
                  this._postSet(29, oldVal, this._FlowInterval);
               } else if (name.equals("FlowMaximum")) {
                  oldVal = this._FlowMaximum;
                  this._FlowMaximum = (Integer)v;
                  this._postSet(28, oldVal, this._FlowMaximum);
               } else if (name.equals("FlowMinimum")) {
                  oldVal = this._FlowMinimum;
                  this._FlowMinimum = (Integer)v;
                  this._postSet(27, oldVal, this._FlowMinimum);
               } else if (name.equals("FlowSteps")) {
                  oldVal = this._FlowSteps;
                  this._FlowSteps = (Integer)v;
                  this._postSet(30, oldVal, this._FlowSteps);
               } else if (name.equals("JNDIName")) {
                  oldVal = this._JNDIName;
                  this._JNDIName = (String)v;
                  this._postSet(12, oldVal, this._JNDIName);
               } else if (name.equals("LoadBalancingEnabled")) {
                  oldVal = this._LoadBalancingEnabled;
                  this._LoadBalancingEnabled = (Boolean)v;
                  this._postSet(32, oldVal, this._LoadBalancingEnabled);
               } else if (name.equals("MessagesMaximum")) {
                  oldVal = this._MessagesMaximum;
                  this._MessagesMaximum = (Integer)v;
                  this._postSet(23, oldVal, this._MessagesMaximum);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("Notes")) {
                  oldVal = this._Notes;
                  this._Notes = (String)v;
                  this._postSet(3, oldVal, this._Notes);
               } else if (name.equals("OverrunPolicy")) {
                  oldVal = this._OverrunPolicy;
                  this._OverrunPolicy = (String)v;
                  this._postSet(24, oldVal, this._OverrunPolicy);
               } else if (name.equals("ProducerLoadBalancingPolicy")) {
                  oldVal = this._ProducerLoadBalancingPolicy;
                  this._ProducerLoadBalancingPolicy = (String)v;
                  this._postSet(34, oldVal, this._ProducerLoadBalancingPolicy);
               } else if (name.equals("SendTimeout")) {
                  oldVal = this._SendTimeout;
                  this._SendTimeout = (Long)v;
                  this._postSet(17, oldVal, this._SendTimeout);
               } else if (name.equals("ServerAffinityEnabled")) {
                  oldVal = this._ServerAffinityEnabled;
                  this._ServerAffinityEnabled = (Boolean)v;
                  this._postSet(33, oldVal, this._ServerAffinityEnabled);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("Targets")) {
                  TargetMBean[] oldVal = this._Targets;
                  this._Targets = (TargetMBean[])((TargetMBean[])v);
                  this._postSet(10, oldVal, this._Targets);
               } else if (name.equals("TransactionTimeout")) {
                  oldVal = this._TransactionTimeout;
                  this._TransactionTimeout = (Long)v;
                  this._postSet(20, oldVal, this._TransactionTimeout);
               } else if (name.equals("UserTransactionsEnabled")) {
                  oldVal = this._UserTransactionsEnabled;
                  this._UserTransactionsEnabled = (Boolean)v;
                  this._postSet(21, oldVal, this._UserTransactionsEnabled);
               } else if (name.equals("XAConnectionFactoryEnabled")) {
                  oldVal = this._XAConnectionFactoryEnabled;
                  this._XAConnectionFactoryEnabled = (Boolean)v;
                  this._postSet(25, oldVal, this._XAConnectionFactoryEnabled);
               } else if (name.equals("XAServerEnabled")) {
                  oldVal = this._XAServerEnabled;
                  this._XAServerEnabled = (Boolean)v;
                  this._postSet(35, oldVal, this._XAServerEnabled);
               } else if (name.equals("customizer")) {
                  JMSConnectionFactory oldVal = this._customizer;
                  this._customizer = (JMSConnectionFactory)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AcknowledgePolicy")) {
         return this._AcknowledgePolicy;
      } else if (name.equals("AllowCloseInOnMessage")) {
         return new Boolean(this._AllowCloseInOnMessage);
      } else if (name.equals("ClientId")) {
         return this._ClientId;
      } else if (name.equals("DefaultDeliveryMode")) {
         return this._DefaultDeliveryMode;
      } else if (name.equals("DefaultPriority")) {
         return new Integer(this._DefaultPriority);
      } else if (name.equals("DefaultRedeliveryDelay")) {
         return new Long(this._DefaultRedeliveryDelay);
      } else if (name.equals("DefaultTimeToDeliver")) {
         return new Long(this._DefaultTimeToDeliver);
      } else if (name.equals("DefaultTimeToLive")) {
         return new Long(this._DefaultTimeToLive);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("FlowControlEnabled")) {
         return new Boolean(this._FlowControlEnabled);
      } else if (name.equals("FlowInterval")) {
         return new Integer(this._FlowInterval);
      } else if (name.equals("FlowMaximum")) {
         return new Integer(this._FlowMaximum);
      } else if (name.equals("FlowMinimum")) {
         return new Integer(this._FlowMinimum);
      } else if (name.equals("FlowSteps")) {
         return new Integer(this._FlowSteps);
      } else if (name.equals("JNDIName")) {
         return this._JNDIName;
      } else if (name.equals("LoadBalancingEnabled")) {
         return new Boolean(this._LoadBalancingEnabled);
      } else if (name.equals("MessagesMaximum")) {
         return new Integer(this._MessagesMaximum);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Notes")) {
         return this._Notes;
      } else if (name.equals("OverrunPolicy")) {
         return this._OverrunPolicy;
      } else if (name.equals("ProducerLoadBalancingPolicy")) {
         return this._ProducerLoadBalancingPolicy;
      } else if (name.equals("SendTimeout")) {
         return new Long(this._SendTimeout);
      } else if (name.equals("ServerAffinityEnabled")) {
         return new Boolean(this._ServerAffinityEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("TransactionTimeout")) {
         return new Long(this._TransactionTimeout);
      } else if (name.equals("UserTransactionsEnabled")) {
         return new Boolean(this._UserTransactionsEnabled);
      } else if (name.equals("XAConnectionFactoryEnabled")) {
         return new Boolean(this._XAConnectionFactoryEnabled);
      } else if (name.equals("XAServerEnabled")) {
         return new Boolean(this._XAServerEnabled);
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 5:
               if (s.equals("notes")) {
                  return 3;
               }
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
            case 7:
            case 8:
            case 11:
            case 15:
            case 26:
            case 27:
            case 28:
            default:
               break;
            case 9:
               if (s.equals("client-id")) {
                  return 13;
               }

               if (s.equals("jndi-name")) {
                  return 12;
               }
               break;
            case 10:
               if (s.equals("flow-steps")) {
                  return 30;
               }
               break;
            case 12:
               if (s.equals("flow-maximum")) {
                  return 28;
               }

               if (s.equals("flow-minimum")) {
                  return 27;
               }

               if (s.equals("send-timeout")) {
                  return 17;
               }
               break;
            case 13:
               if (s.equals("flow-interval")) {
                  return 29;
               }
               break;
            case 14:
               if (s.equals("overrun-policy")) {
                  return 24;
               }
               break;
            case 16:
               if (s.equals("default-priority")) {
                  return 14;
               }

               if (s.equals("messages-maximum")) {
                  return 23;
               }
               break;
            case 17:
               if (s.equals("xa-server-enabled")) {
                  return 35;
               }
               break;
            case 18:
               if (s.equals("acknowledge-policy")) {
                  return 26;
               }
               break;
            case 19:
               if (s.equals("transaction-timeout")) {
                  return 20;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("default-time-to-live")) {
                  return 16;
               }

               if (s.equals("flow-control-enabled")) {
                  return 31;
               }
               break;
            case 21:
               if (s.equals("default-delivery-mode")) {
                  return 18;
               }
               break;
            case 22:
               if (s.equals("load-balancing-enabled")) {
                  return 32;
               }
               break;
            case 23:
               if (s.equals("default-time-to-deliver")) {
                  return 15;
               }

               if (s.equals("server-affinity-enabled")) {
                  return 33;
               }
               break;
            case 24:
               if (s.equals("default-redelivery-delay")) {
                  return 19;
               }
               break;
            case 25:
               if (s.equals("allow-close-in-on-message")) {
                  return 22;
               }

               if (s.equals("user-transactions-enabled")) {
                  return 21;
               }
               break;
            case 29:
               if (s.equals("xa-connection-factory-enabled")) {
                  return 25;
               }
               break;
            case 30:
               if (s.equals("producer-load-balancing-policy")) {
                  return 34;
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
            case 2:
               return "name";
            case 3:
               return "notes";
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "target";
            case 12:
               return "jndi-name";
            case 13:
               return "client-id";
            case 14:
               return "default-priority";
            case 15:
               return "default-time-to-deliver";
            case 16:
               return "default-time-to-live";
            case 17:
               return "send-timeout";
            case 18:
               return "default-delivery-mode";
            case 19:
               return "default-redelivery-delay";
            case 20:
               return "transaction-timeout";
            case 21:
               return "user-transactions-enabled";
            case 22:
               return "allow-close-in-on-message";
            case 23:
               return "messages-maximum";
            case 24:
               return "overrun-policy";
            case 25:
               return "xa-connection-factory-enabled";
            case 26:
               return "acknowledge-policy";
            case 27:
               return "flow-minimum";
            case 28:
               return "flow-maximum";
            case 29:
               return "flow-interval";
            case 30:
               return "flow-steps";
            case 31:
               return "flow-control-enabled";
            case 32:
               return "load-balancing-enabled";
            case 33:
               return "server-affinity-enabled";
            case 34:
               return "producer-load-balancing-policy";
            case 35:
               return "xa-server-enabled";
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private JMSConnectionFactoryMBeanImpl bean;

      protected Helper(JMSConnectionFactoryMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
               return "Notes";
            case 4:
            case 5:
            case 6:
            case 8:
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Targets";
            case 12:
               return "JNDIName";
            case 13:
               return "ClientId";
            case 14:
               return "DefaultPriority";
            case 15:
               return "DefaultTimeToDeliver";
            case 16:
               return "DefaultTimeToLive";
            case 17:
               return "SendTimeout";
            case 18:
               return "DefaultDeliveryMode";
            case 19:
               return "DefaultRedeliveryDelay";
            case 20:
               return "TransactionTimeout";
            case 21:
               return "UserTransactionsEnabled";
            case 22:
               return "AllowCloseInOnMessage";
            case 23:
               return "MessagesMaximum";
            case 24:
               return "OverrunPolicy";
            case 25:
               return "XAConnectionFactoryEnabled";
            case 26:
               return "AcknowledgePolicy";
            case 27:
               return "FlowMinimum";
            case 28:
               return "FlowMaximum";
            case 29:
               return "FlowInterval";
            case 30:
               return "FlowSteps";
            case 31:
               return "FlowControlEnabled";
            case 32:
               return "LoadBalancingEnabled";
            case 33:
               return "ServerAffinityEnabled";
            case 34:
               return "ProducerLoadBalancingPolicy";
            case 35:
               return "XAServerEnabled";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AcknowledgePolicy")) {
            return 26;
         } else if (propName.equals("AllowCloseInOnMessage")) {
            return 22;
         } else if (propName.equals("ClientId")) {
            return 13;
         } else if (propName.equals("DefaultDeliveryMode")) {
            return 18;
         } else if (propName.equals("DefaultPriority")) {
            return 14;
         } else if (propName.equals("DefaultRedeliveryDelay")) {
            return 19;
         } else if (propName.equals("DefaultTimeToDeliver")) {
            return 15;
         } else if (propName.equals("DefaultTimeToLive")) {
            return 16;
         } else if (propName.equals("FlowInterval")) {
            return 29;
         } else if (propName.equals("FlowMaximum")) {
            return 28;
         } else if (propName.equals("FlowMinimum")) {
            return 27;
         } else if (propName.equals("FlowSteps")) {
            return 30;
         } else if (propName.equals("JNDIName")) {
            return 12;
         } else if (propName.equals("MessagesMaximum")) {
            return 23;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Notes")) {
            return 3;
         } else if (propName.equals("OverrunPolicy")) {
            return 24;
         } else if (propName.equals("ProducerLoadBalancingPolicy")) {
            return 34;
         } else if (propName.equals("SendTimeout")) {
            return 17;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("TransactionTimeout")) {
            return 20;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("FlowControlEnabled")) {
            return 31;
         } else if (propName.equals("LoadBalancingEnabled")) {
            return 32;
         } else if (propName.equals("ServerAffinityEnabled")) {
            return 33;
         } else if (propName.equals("UserTransactionsEnabled")) {
            return 21;
         } else if (propName.equals("XAConnectionFactoryEnabled")) {
            return 25;
         } else {
            return propName.equals("XAServerEnabled") ? 35 : super.getPropertyIndex(propName);
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

            if (this.bean.isAllowCloseInOnMessageSet()) {
               buf.append("AllowCloseInOnMessage");
               buf.append(String.valueOf(this.bean.getAllowCloseInOnMessage()));
            }

            if (this.bean.isClientIdSet()) {
               buf.append("ClientId");
               buf.append(String.valueOf(this.bean.getClientId()));
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

            if (this.bean.isFlowIntervalSet()) {
               buf.append("FlowInterval");
               buf.append(String.valueOf(this.bean.getFlowInterval()));
            }

            if (this.bean.isFlowMaximumSet()) {
               buf.append("FlowMaximum");
               buf.append(String.valueOf(this.bean.getFlowMaximum()));
            }

            if (this.bean.isFlowMinimumSet()) {
               buf.append("FlowMinimum");
               buf.append(String.valueOf(this.bean.getFlowMinimum()));
            }

            if (this.bean.isFlowStepsSet()) {
               buf.append("FlowSteps");
               buf.append(String.valueOf(this.bean.getFlowSteps()));
            }

            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            if (this.bean.isMessagesMaximumSet()) {
               buf.append("MessagesMaximum");
               buf.append(String.valueOf(this.bean.getMessagesMaximum()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNotesSet()) {
               buf.append("Notes");
               buf.append(String.valueOf(this.bean.getNotes()));
            }

            if (this.bean.isOverrunPolicySet()) {
               buf.append("OverrunPolicy");
               buf.append(String.valueOf(this.bean.getOverrunPolicy()));
            }

            if (this.bean.isProducerLoadBalancingPolicySet()) {
               buf.append("ProducerLoadBalancingPolicy");
               buf.append(String.valueOf(this.bean.getProducerLoadBalancingPolicy()));
            }

            if (this.bean.isSendTimeoutSet()) {
               buf.append("SendTimeout");
               buf.append(String.valueOf(this.bean.getSendTimeout()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isTransactionTimeoutSet()) {
               buf.append("TransactionTimeout");
               buf.append(String.valueOf(this.bean.getTransactionTimeout()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isFlowControlEnabledSet()) {
               buf.append("FlowControlEnabled");
               buf.append(String.valueOf(this.bean.isFlowControlEnabled()));
            }

            if (this.bean.isLoadBalancingEnabledSet()) {
               buf.append("LoadBalancingEnabled");
               buf.append(String.valueOf(this.bean.isLoadBalancingEnabled()));
            }

            if (this.bean.isServerAffinityEnabledSet()) {
               buf.append("ServerAffinityEnabled");
               buf.append(String.valueOf(this.bean.isServerAffinityEnabled()));
            }

            if (this.bean.isUserTransactionsEnabledSet()) {
               buf.append("UserTransactionsEnabled");
               buf.append(String.valueOf(this.bean.isUserTransactionsEnabled()));
            }

            if (this.bean.isXAConnectionFactoryEnabledSet()) {
               buf.append("XAConnectionFactoryEnabled");
               buf.append(String.valueOf(this.bean.isXAConnectionFactoryEnabled()));
            }

            if (this.bean.isXAServerEnabledSet()) {
               buf.append("XAServerEnabled");
               buf.append(String.valueOf(this.bean.isXAServerEnabled()));
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
            JMSConnectionFactoryMBeanImpl otherTyped = (JMSConnectionFactoryMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("AcknowledgePolicy", this.bean.getAcknowledgePolicy(), otherTyped.getAcknowledgePolicy(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("AllowCloseInOnMessage", this.bean.getAllowCloseInOnMessage(), otherTyped.getAllowCloseInOnMessage(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ClientId", this.bean.getClientId(), otherTyped.getClientId(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DefaultDeliveryMode", this.bean.getDefaultDeliveryMode(), otherTyped.getDefaultDeliveryMode(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DefaultPriority", this.bean.getDefaultPriority(), otherTyped.getDefaultPriority(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DefaultRedeliveryDelay", this.bean.getDefaultRedeliveryDelay(), otherTyped.getDefaultRedeliveryDelay(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DefaultTimeToDeliver", this.bean.getDefaultTimeToDeliver(), otherTyped.getDefaultTimeToDeliver(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DefaultTimeToLive", this.bean.getDefaultTimeToLive(), otherTyped.getDefaultTimeToLive(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("FlowInterval", this.bean.getFlowInterval(), otherTyped.getFlowInterval(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("FlowMaximum", this.bean.getFlowMaximum(), otherTyped.getFlowMaximum(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("FlowMinimum", this.bean.getFlowMinimum(), otherTyped.getFlowMinimum(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("FlowSteps", this.bean.getFlowSteps(), otherTyped.getFlowSteps(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MessagesMaximum", this.bean.getMessagesMaximum(), otherTyped.getMessagesMaximum(), true);
            }

            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Notes", this.bean.getNotes(), otherTyped.getNotes(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("OverrunPolicy", this.bean.getOverrunPolicy(), otherTyped.getOverrunPolicy(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ProducerLoadBalancingPolicy", this.bean.getProducerLoadBalancingPolicy(), otherTyped.getProducerLoadBalancingPolicy(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("SendTimeout", this.bean.getSendTimeout(), otherTyped.getSendTimeout(), true);
            }

            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("TransactionTimeout", this.bean.getTransactionTimeout(), otherTyped.getTransactionTimeout(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("FlowControlEnabled", this.bean.isFlowControlEnabled(), otherTyped.isFlowControlEnabled(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LoadBalancingEnabled", this.bean.isLoadBalancingEnabled(), otherTyped.isLoadBalancingEnabled(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ServerAffinityEnabled", this.bean.isServerAffinityEnabled(), otherTyped.isServerAffinityEnabled(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("UserTransactionsEnabled", this.bean.isUserTransactionsEnabled(), otherTyped.isUserTransactionsEnabled(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("XAConnectionFactoryEnabled", this.bean.isXAConnectionFactoryEnabled(), otherTyped.isXAConnectionFactoryEnabled(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("XAServerEnabled", this.bean.isXAServerEnabled(), otherTyped.isXAServerEnabled(), false);
            }

         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSConnectionFactoryMBeanImpl original = (JMSConnectionFactoryMBeanImpl)event.getSourceBean();
            JMSConnectionFactoryMBeanImpl proposed = (JMSConnectionFactoryMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AcknowledgePolicy")) {
                  original.setAcknowledgePolicy(proposed.getAcknowledgePolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("AllowCloseInOnMessage")) {
                  original.setAllowCloseInOnMessage(proposed.getAllowCloseInOnMessage());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("ClientId")) {
                  original.setClientId(proposed.getClientId());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DefaultDeliveryMode")) {
                  original.setDefaultDeliveryMode(proposed.getDefaultDeliveryMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("DefaultPriority")) {
                  original.setDefaultPriority(proposed.getDefaultPriority());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("DefaultRedeliveryDelay")) {
                  original.setDefaultRedeliveryDelay(proposed.getDefaultRedeliveryDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("DefaultTimeToDeliver")) {
                  original.setDefaultTimeToDeliver(proposed.getDefaultTimeToDeliver());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("DefaultTimeToLive")) {
                  original.setDefaultTimeToLive(proposed.getDefaultTimeToLive());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("FlowInterval")) {
                  original.setFlowInterval(proposed.getFlowInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("FlowMaximum")) {
                  original.setFlowMaximum(proposed.getFlowMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("FlowMinimum")) {
                  original.setFlowMinimum(proposed.getFlowMinimum());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("FlowSteps")) {
                  original.setFlowSteps(proposed.getFlowSteps());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
               } else if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MessagesMaximum")) {
                  original.setMessagesMaximum(proposed.getMessagesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Notes")) {
                  original.setNotes(proposed.getNotes());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("OverrunPolicy")) {
                  original.setOverrunPolicy(proposed.getOverrunPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("ProducerLoadBalancingPolicy")) {
                  original.setProducerLoadBalancingPolicy(proposed.getProducerLoadBalancingPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("SendTimeout")) {
                  original.setSendTimeout(proposed.getSendTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("Tags")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addTag((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTag((String)update.getRemovedObject());
                  }

                  if (original.getTags() == null || original.getTags().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("Targets")) {
                  original.setTargetsAsString(proposed.getTargetsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("TransactionTimeout")) {
                  original.setTransactionTimeout(proposed.getTransactionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (!prop.equals("DynamicallyCreated")) {
                  if (prop.equals("FlowControlEnabled")) {
                     original.setFlowControlEnabled(proposed.isFlowControlEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 31);
                  } else if (prop.equals("LoadBalancingEnabled")) {
                     original.setLoadBalancingEnabled(proposed.isLoadBalancingEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 32);
                  } else if (prop.equals("ServerAffinityEnabled")) {
                     original.setServerAffinityEnabled(proposed.isServerAffinityEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  } else if (prop.equals("UserTransactionsEnabled")) {
                     original.setUserTransactionsEnabled(proposed.isUserTransactionsEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("XAConnectionFactoryEnabled")) {
                     original.setXAConnectionFactoryEnabled(proposed.isXAConnectionFactoryEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  } else if (prop.equals("XAServerEnabled")) {
                     original.setXAServerEnabled(proposed.isXAServerEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  } else {
                     super.applyPropertyUpdate(event, update);
                  }
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
            JMSConnectionFactoryMBeanImpl copy = (JMSConnectionFactoryMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("AcknowledgePolicy")) && this.bean.isAcknowledgePolicySet()) {
               copy.setAcknowledgePolicy(this.bean.getAcknowledgePolicy());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("AllowCloseInOnMessage")) && this.bean.isAllowCloseInOnMessageSet()) {
               copy.setAllowCloseInOnMessage(this.bean.getAllowCloseInOnMessage());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ClientId")) && this.bean.isClientIdSet()) {
               copy.setClientId(this.bean.getClientId());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DefaultDeliveryMode")) && this.bean.isDefaultDeliveryModeSet()) {
               copy.setDefaultDeliveryMode(this.bean.getDefaultDeliveryMode());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DefaultPriority")) && this.bean.isDefaultPrioritySet()) {
               copy.setDefaultPriority(this.bean.getDefaultPriority());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DefaultRedeliveryDelay")) && this.bean.isDefaultRedeliveryDelaySet()) {
               copy.setDefaultRedeliveryDelay(this.bean.getDefaultRedeliveryDelay());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DefaultTimeToDeliver")) && this.bean.isDefaultTimeToDeliverSet()) {
               copy.setDefaultTimeToDeliver(this.bean.getDefaultTimeToDeliver());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DefaultTimeToLive")) && this.bean.isDefaultTimeToLiveSet()) {
               copy.setDefaultTimeToLive(this.bean.getDefaultTimeToLive());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("FlowInterval")) && this.bean.isFlowIntervalSet()) {
               copy.setFlowInterval(this.bean.getFlowInterval());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("FlowMaximum")) && this.bean.isFlowMaximumSet()) {
               copy.setFlowMaximum(this.bean.getFlowMaximum());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("FlowMinimum")) && this.bean.isFlowMinimumSet()) {
               copy.setFlowMinimum(this.bean.getFlowMinimum());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("FlowSteps")) && this.bean.isFlowStepsSet()) {
               copy.setFlowSteps(this.bean.getFlowSteps());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MessagesMaximum")) && this.bean.isMessagesMaximumSet()) {
               copy.setMessagesMaximum(this.bean.getMessagesMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Notes")) && this.bean.isNotesSet()) {
               copy.setNotes(this.bean.getNotes());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("OverrunPolicy")) && this.bean.isOverrunPolicySet()) {
               copy.setOverrunPolicy(this.bean.getOverrunPolicy());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ProducerLoadBalancingPolicy")) && this.bean.isProducerLoadBalancingPolicySet()) {
               copy.setProducerLoadBalancingPolicy(this.bean.getProducerLoadBalancingPolicy());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("SendTimeout")) && this.bean.isSendTimeoutSet()) {
               copy.setSendTimeout(this.bean.getSendTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("TransactionTimeout")) && this.bean.isTransactionTimeoutSet()) {
               copy.setTransactionTimeout(this.bean.getTransactionTimeout());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("FlowControlEnabled")) && this.bean.isFlowControlEnabledSet()) {
               copy.setFlowControlEnabled(this.bean.isFlowControlEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LoadBalancingEnabled")) && this.bean.isLoadBalancingEnabledSet()) {
               copy.setLoadBalancingEnabled(this.bean.isLoadBalancingEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ServerAffinityEnabled")) && this.bean.isServerAffinityEnabledSet()) {
               copy.setServerAffinityEnabled(this.bean.isServerAffinityEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("UserTransactionsEnabled")) && this.bean.isUserTransactionsEnabledSet()) {
               copy.setUserTransactionsEnabled(this.bean.isUserTransactionsEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("XAConnectionFactoryEnabled")) && this.bean.isXAConnectionFactoryEnabledSet()) {
               copy.setXAConnectionFactoryEnabled(this.bean.isXAConnectionFactoryEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("XAServerEnabled")) && this.bean.isXAServerEnabledSet()) {
               copy.setXAServerEnabled(this.bean.isXAServerEnabled());
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
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}

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
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.JTAMBeanCustomizer;
import weblogic.transaction.internal.JTAValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JTAMBeanImpl extends ConfigurationMBeanImpl implements JTAMBean, Serializable {
   private int _AbandonTimeoutSeconds;
   private int _BeforeCompletionIterationLimit;
   private int _CheckpointIntervalSeconds;
   private boolean _ClusterwideRecoveryEnabled;
   private int _CompletionTimeoutSeconds;
   private int _CrossDomainRecoveryRetryInterval;
   private int _CrossSiteRecoveryLeaseExpiration;
   private int _CrossSiteRecoveryLeaseUpdate;
   private int _CrossSiteRecoveryRetryInterval;
   private DeterminerCandidateResourceInfoVBean[] _DeterminerCandidateResourceInfoList;
   private String[] _Determiners;
   private boolean _DynamicallyCreated;
   private boolean _ForgetHeuristics;
   private int _MaxResourceRequestsOnServer;
   private long _MaxResourceUnavailableMillis;
   private int _MaxRetrySecondsBeforeDeterminerFail;
   private int _MaxTransactions;
   private long _MaxTransactionsHealthIntervalMillis;
   private int _MaxUniqueNameStatistics;
   private long _MaxXACallMillis;
   private int _MigrationCheckpointIntervalSeconds;
   private String _Name;
   private String _ParallelXADispatchPolicy;
   private boolean _ParallelXAEnabled;
   private int _PurgeResourceFromCheckpointIntervalSeconds;
   private String _RecoverySiteName;
   private long _RecoveryThresholdMillis;
   private String _SecurityInteropMode;
   private long _SerializeEnlistmentsGCIntervalMillis;
   private int _ShutdownGracePeriod;
   private boolean _TLOGWriteWhenDeterminerExistsEnabled;
   private String[] _Tags;
   private boolean _TightlyCoupledTransactionsEnabled;
   private int _TimeoutSeconds;
   private boolean _TwoPhaseEnabled;
   private int _UnregisterResourceGracePeriod;
   private String[] _UseNonSecureAddressesForDomains;
   private String[] _UsePublicAddressesForRemoteDomains;
   private boolean _WSATIssuedTokenEnabled;
   private String _WSATTransportSecurityMode;
   private transient JTAMBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public JTAMBeanImpl() {
      try {
         this._customizer = new JTAMBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JTAMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JTAMBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JTAMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JTAMBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
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

   public int getTimeoutSeconds() {
      return this._TimeoutSeconds;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTimeoutSecondsInherited() {
      return false;
   }

   public boolean isTimeoutSecondsSet() {
      return this._isSet(10);
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

   public void setTimeoutSeconds(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("TimeoutSeconds", (long)param0, 1L, 2147483647L);
      int _oldVal = this._TimeoutSeconds;
      this._TimeoutSeconds = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getAbandonTimeoutSeconds() {
      return this._AbandonTimeoutSeconds;
   }

   public boolean isAbandonTimeoutSecondsInherited() {
      return false;
   }

   public boolean isAbandonTimeoutSecondsSet() {
      return this._isSet(11);
   }

   public void setAbandonTimeoutSeconds(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("AbandonTimeoutSeconds", (long)param0, 1L, 2147483647L);
      int _oldVal = this._AbandonTimeoutSeconds;
      this._AbandonTimeoutSeconds = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getCompletionTimeoutSeconds() {
      return this._CompletionTimeoutSeconds;
   }

   public boolean isCompletionTimeoutSecondsInherited() {
      return false;
   }

   public boolean isCompletionTimeoutSecondsSet() {
      return this._isSet(12);
   }

   public void setCompletionTimeoutSeconds(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CompletionTimeoutSeconds", (long)param0, -1L, 2147483647L);
      int _oldVal = this._CompletionTimeoutSeconds;
      this._CompletionTimeoutSeconds = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean getForgetHeuristics() {
      return this._ForgetHeuristics;
   }

   public boolean isForgetHeuristicsInherited() {
      return false;
   }

   public boolean isForgetHeuristicsSet() {
      return this._isSet(13);
   }

   public void setForgetHeuristics(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      boolean _oldVal = this._ForgetHeuristics;
      this._ForgetHeuristics = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getBeforeCompletionIterationLimit() {
      return this._BeforeCompletionIterationLimit;
   }

   public boolean isBeforeCompletionIterationLimitInherited() {
      return false;
   }

   public boolean isBeforeCompletionIterationLimitSet() {
      return this._isSet(14);
   }

   public void setBeforeCompletionIterationLimit(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("BeforeCompletionIterationLimit", (long)param0, 1L, 2147483647L);
      int _oldVal = this._BeforeCompletionIterationLimit;
      this._BeforeCompletionIterationLimit = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getMaxTransactions() {
      return this._MaxTransactions;
   }

   public boolean isMaxTransactionsInherited() {
      return false;
   }

   public boolean isMaxTransactionsSet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setMaxTransactions(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxTransactions", (long)param0, 1L, 2147483647L);
      int _oldVal = this._MaxTransactions;
      this._MaxTransactions = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getMaxUniqueNameStatistics() {
      return this._MaxUniqueNameStatistics;
   }

   public boolean isMaxUniqueNameStatisticsInherited() {
      return false;
   }

   public boolean isMaxUniqueNameStatisticsSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setMaxUniqueNameStatistics(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxUniqueNameStatistics", (long)param0, 0L, 2147483647L);
      int _oldVal = this._MaxUniqueNameStatistics;
      this._MaxUniqueNameStatistics = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getMaxResourceRequestsOnServer() {
      return this._MaxResourceRequestsOnServer;
   }

   public boolean isMaxResourceRequestsOnServerInherited() {
      return false;
   }

   public boolean isMaxResourceRequestsOnServerSet() {
      return this._isSet(17);
   }

   public void setMaxResourceRequestsOnServer(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxResourceRequestsOnServer", (long)param0, 10L, 2147483647L);
      int _oldVal = this._MaxResourceRequestsOnServer;
      this._MaxResourceRequestsOnServer = param0;
      this._postSet(17, _oldVal, param0);
   }

   public long getMaxXACallMillis() {
      return this._MaxXACallMillis;
   }

   public boolean isMaxXACallMillisInherited() {
      return false;
   }

   public boolean isMaxXACallMillisSet() {
      return this._isSet(18);
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

   public void setMaxXACallMillis(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxXACallMillis", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._MaxXACallMillis;
      this._MaxXACallMillis = param0;
      this._postSet(18, _oldVal, param0);
   }

   public long getMaxResourceUnavailableMillis() {
      return this._MaxResourceUnavailableMillis;
   }

   public boolean isMaxResourceUnavailableMillisInherited() {
      return false;
   }

   public boolean isMaxResourceUnavailableMillisSet() {
      return this._isSet(19);
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

   public void setMaxResourceUnavailableMillis(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxResourceUnavailableMillis", param0, 0L, Long.MAX_VALUE);
      long _oldVal = this._MaxResourceUnavailableMillis;
      this._MaxResourceUnavailableMillis = param0;
      this._postSet(19, _oldVal, param0);
   }

   public long getRecoveryThresholdMillis() {
      return this._RecoveryThresholdMillis;
   }

   public boolean isRecoveryThresholdMillisInherited() {
      return false;
   }

   public boolean isRecoveryThresholdMillisSet() {
      return this._isSet(20);
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

   public void setRecoveryThresholdMillis(long param0) {
      LegalChecks.checkInRange("RecoveryThresholdMillis", param0, 60000L, Long.MAX_VALUE);
      long _oldVal = this._RecoveryThresholdMillis;
      this._RecoveryThresholdMillis = param0;
      this._postSet(20, _oldVal, param0);
   }

   public int getMigrationCheckpointIntervalSeconds() {
      return this._MigrationCheckpointIntervalSeconds;
   }

   public boolean isMigrationCheckpointIntervalSecondsInherited() {
      return false;
   }

   public boolean isMigrationCheckpointIntervalSecondsSet() {
      return this._isSet(21);
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

   public void setMigrationCheckpointIntervalSeconds(int param0) {
      LegalChecks.checkInRange("MigrationCheckpointIntervalSeconds", (long)param0, 1L, 2147483647L);
      int _oldVal = this._MigrationCheckpointIntervalSeconds;
      this._MigrationCheckpointIntervalSeconds = param0;
      this._postSet(21, _oldVal, param0);
   }

   public long getMaxTransactionsHealthIntervalMillis() {
      return this._MaxTransactionsHealthIntervalMillis;
   }

   public boolean isMaxTransactionsHealthIntervalMillisInherited() {
      return false;
   }

   public boolean isMaxTransactionsHealthIntervalMillisSet() {
      return this._isSet(22);
   }

   public void setMaxTransactionsHealthIntervalMillis(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxTransactionsHealthIntervalMillis", param0, 1000L, Long.MAX_VALUE);
      long _oldVal = this._MaxTransactionsHealthIntervalMillis;
      this._MaxTransactionsHealthIntervalMillis = param0;
      this._postSet(22, _oldVal, param0);
   }

   public int getPurgeResourceFromCheckpointIntervalSeconds() {
      return this._PurgeResourceFromCheckpointIntervalSeconds;
   }

   public boolean isPurgeResourceFromCheckpointIntervalSecondsInherited() {
      return false;
   }

   public boolean isPurgeResourceFromCheckpointIntervalSecondsSet() {
      return this._isSet(23);
   }

   public void setPurgeResourceFromCheckpointIntervalSeconds(int param0) {
      LegalChecks.checkInRange("PurgeResourceFromCheckpointIntervalSeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._PurgeResourceFromCheckpointIntervalSeconds;
      this._PurgeResourceFromCheckpointIntervalSeconds = param0;
      this._postSet(23, _oldVal, param0);
   }

   public int getCheckpointIntervalSeconds() {
      return this._CheckpointIntervalSeconds;
   }

   public boolean isCheckpointIntervalSecondsInherited() {
      return false;
   }

   public boolean isCheckpointIntervalSecondsSet() {
      return this._isSet(24);
   }

   public void setCheckpointIntervalSeconds(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CheckpointIntervalSeconds", (long)param0, 10L, 1800L);
      int _oldVal = this._CheckpointIntervalSeconds;
      this._CheckpointIntervalSeconds = param0;
      this._postSet(24, _oldVal, param0);
   }

   public long getSerializeEnlistmentsGCIntervalMillis() {
      return this._SerializeEnlistmentsGCIntervalMillis;
   }

   public boolean isSerializeEnlistmentsGCIntervalMillisInherited() {
      return false;
   }

   public boolean isSerializeEnlistmentsGCIntervalMillisSet() {
      return this._isSet(25);
   }

   public void setSerializeEnlistmentsGCIntervalMillis(long param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkMin("SerializeEnlistmentsGCIntervalMillis", param0, 0L);
      long _oldVal = this._SerializeEnlistmentsGCIntervalMillis;
      this._SerializeEnlistmentsGCIntervalMillis = param0;
      this._postSet(25, _oldVal, param0);
   }

   public boolean getParallelXAEnabled() {
      return this._ParallelXAEnabled;
   }

   public boolean isParallelXAEnabledInherited() {
      return false;
   }

   public boolean isParallelXAEnabledSet() {
      return this._isSet(26);
   }

   public void setParallelXAEnabled(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      JTAValidator.validateParallelXA(this, param0);
      boolean _oldVal = this._ParallelXAEnabled;
      this._ParallelXAEnabled = param0;
      this._postSet(26, _oldVal, param0);
   }

   public String getParallelXADispatchPolicy() {
      return this._ParallelXADispatchPolicy;
   }

   public boolean isParallelXADispatchPolicyInherited() {
      return false;
   }

   public boolean isParallelXADispatchPolicySet() {
      return this._isSet(27);
   }

   public void setParallelXADispatchPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ParallelXADispatchPolicy;
      this._ParallelXADispatchPolicy = param0;
      this._postSet(27, _oldVal, param0);
   }

   public int getUnregisterResourceGracePeriod() {
      return this._UnregisterResourceGracePeriod;
   }

   public boolean isUnregisterResourceGracePeriodInherited() {
      return false;
   }

   public boolean isUnregisterResourceGracePeriodSet() {
      return this._isSet(28);
   }

   public void setUnregisterResourceGracePeriod(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("UnregisterResourceGracePeriod", (long)param0, 0L, 2147483647L);
      int _oldVal = this._UnregisterResourceGracePeriod;
      this._UnregisterResourceGracePeriod = param0;
      this._postSet(28, _oldVal, param0);
   }

   public String getSecurityInteropMode() {
      return this._SecurityInteropMode;
   }

   public boolean isSecurityInteropModeInherited() {
      return false;
   }

   public boolean isSecurityInteropModeSet() {
      return this._isSet(29);
   }

   public void setSecurityInteropMode(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"default", "performance"};
      param0 = LegalChecks.checkInEnum("SecurityInteropMode", param0, _set);
      String _oldVal = this._SecurityInteropMode;
      this._SecurityInteropMode = param0;
      this._postSet(29, _oldVal, param0);
   }

   public String getWSATTransportSecurityMode() {
      return this._WSATTransportSecurityMode;
   }

   public boolean isWSATTransportSecurityModeInherited() {
      return false;
   }

   public boolean isWSATTransportSecurityModeSet() {
      return this._isSet(30);
   }

   public void setWSATTransportSecurityMode(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"SSLNotRequired", "SSLRequired", "ClientCertRequired"};
      param0 = LegalChecks.checkInEnum("WSATTransportSecurityMode", param0, _set);
      String _oldVal = this._WSATTransportSecurityMode;
      this._WSATTransportSecurityMode = param0;
      this._postSet(30, _oldVal, param0);
   }

   public boolean isWSATIssuedTokenEnabled() {
      return this._WSATIssuedTokenEnabled;
   }

   public boolean isWSATIssuedTokenEnabledInherited() {
      return false;
   }

   public boolean isWSATIssuedTokenEnabledSet() {
      return this._isSet(31);
   }

   public void setWSATIssuedTokenEnabled(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      boolean _oldVal = this._WSATIssuedTokenEnabled;
      this._WSATIssuedTokenEnabled = param0;
      this._postSet(31, _oldVal, param0);
   }

   public boolean isTwoPhaseEnabled() {
      return this._TwoPhaseEnabled;
   }

   public boolean isTwoPhaseEnabledInherited() {
      return false;
   }

   public boolean isTwoPhaseEnabledSet() {
      return this._isSet(32);
   }

   public void setTwoPhaseEnabled(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      boolean _oldVal = this._TwoPhaseEnabled;
      this._TwoPhaseEnabled = param0;
      this._postSet(32, _oldVal, param0);
   }

   public boolean isClusterwideRecoveryEnabled() {
      return this._ClusterwideRecoveryEnabled;
   }

   public boolean isClusterwideRecoveryEnabledInherited() {
      return false;
   }

   public boolean isClusterwideRecoveryEnabledSet() {
      return this._isSet(33);
   }

   public void setClusterwideRecoveryEnabled(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      boolean _oldVal = this._ClusterwideRecoveryEnabled;
      this._ClusterwideRecoveryEnabled = param0;
      this._postSet(33, _oldVal, param0);
   }

   public boolean isTightlyCoupledTransactionsEnabled() {
      return this._TightlyCoupledTransactionsEnabled;
   }

   public boolean isTightlyCoupledTransactionsEnabledInherited() {
      return false;
   }

   public boolean isTightlyCoupledTransactionsEnabledSet() {
      return this._isSet(34);
   }

   public void setTightlyCoupledTransactionsEnabled(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      boolean _oldVal = this._TightlyCoupledTransactionsEnabled;
      this._TightlyCoupledTransactionsEnabled = param0;
      this._postSet(34, _oldVal, param0);
   }

   public String[] getDeterminers() {
      return this._Determiners;
   }

   public boolean isDeterminersInherited() {
      return false;
   }

   public boolean isDeterminersSet() {
      return this._isSet(35);
   }

   public DeterminerCandidateResourceInfoVBean[] getDeterminerCandidateResourceInfoList() {
      return this._customizer.getDeterminerCandidateResourceInfoList();
   }

   public boolean isDeterminerCandidateResourceInfoListInherited() {
      return false;
   }

   public boolean isDeterminerCandidateResourceInfoListSet() {
      return this._isSet(36);
   }

   public void setDeterminerCandidateResourceInfoList(DeterminerCandidateResourceInfoVBean[] param0) throws InvalidAttributeValueException {
      this._DeterminerCandidateResourceInfoList = param0;
   }

   public void setDeterminers(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      JTAValidator.validateDeterminersGlobal(this, param0);
      String[] _oldVal = this._Determiners;
      this._Determiners = param0;
      this._postSet(35, _oldVal, param0);
   }

   public boolean isTLOGWriteWhenDeterminerExistsEnabled() {
      return this._TLOGWriteWhenDeterminerExistsEnabled;
   }

   public boolean isTLOGWriteWhenDeterminerExistsEnabledInherited() {
      return false;
   }

   public boolean isTLOGWriteWhenDeterminerExistsEnabledSet() {
      return this._isSet(37);
   }

   public void setTLOGWriteWhenDeterminerExistsEnabled(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      boolean _oldVal = this._TLOGWriteWhenDeterminerExistsEnabled;
      this._TLOGWriteWhenDeterminerExistsEnabled = param0;
      this._postSet(37, _oldVal, param0);
   }

   public int getShutdownGracePeriod() {
      return this._ShutdownGracePeriod;
   }

   public boolean isShutdownGracePeriodInherited() {
      return false;
   }

   public boolean isShutdownGracePeriodSet() {
      return this._isSet(38);
   }

   public void setShutdownGracePeriod(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      int _oldVal = this._ShutdownGracePeriod;
      this._ShutdownGracePeriod = param0;
      this._postSet(38, _oldVal, param0);
   }

   public int getMaxRetrySecondsBeforeDeterminerFail() {
      return this._MaxRetrySecondsBeforeDeterminerFail;
   }

   public boolean isMaxRetrySecondsBeforeDeterminerFailInherited() {
      return false;
   }

   public boolean isMaxRetrySecondsBeforeDeterminerFailSet() {
      return this._isSet(39);
   }

   public void setMaxRetrySecondsBeforeDeterminerFail(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxRetrySecondsBeforeDeterminerFail", (long)param0, 0L, 2147483647L);
      int _oldVal = this._MaxRetrySecondsBeforeDeterminerFail;
      this._MaxRetrySecondsBeforeDeterminerFail = param0;
      this._postSet(39, _oldVal, param0);
   }

   public String getRecoverySiteName() {
      return this._RecoverySiteName;
   }

   public boolean isRecoverySiteNameInherited() {
      return false;
   }

   public boolean isRecoverySiteNameSet() {
      return this._isSet(40);
   }

   public void setRecoverySiteName(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RecoverySiteName;
      this._RecoverySiteName = param0;
      this._postSet(40, _oldVal, param0);
   }

   public int getCrossDomainRecoveryRetryInterval() {
      return this._CrossDomainRecoveryRetryInterval;
   }

   public boolean isCrossDomainRecoveryRetryIntervalInherited() {
      return false;
   }

   public boolean isCrossDomainRecoveryRetryIntervalSet() {
      return this._isSet(41);
   }

   public void setCrossDomainRecoveryRetryInterval(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CrossDomainRecoveryRetryInterval", (long)param0, 1L, 2147483647L);
      int _oldVal = this._CrossDomainRecoveryRetryInterval;
      this._CrossDomainRecoveryRetryInterval = param0;
      this._postSet(41, _oldVal, param0);
   }

   public int getCrossSiteRecoveryRetryInterval() {
      return this._CrossSiteRecoveryRetryInterval;
   }

   public boolean isCrossSiteRecoveryRetryIntervalInherited() {
      return false;
   }

   public boolean isCrossSiteRecoveryRetryIntervalSet() {
      return this._isSet(42);
   }

   public void setCrossSiteRecoveryRetryInterval(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CrossSiteRecoveryRetryInterval", (long)param0, 1L, 2147483647L);
      int _oldVal = this._CrossSiteRecoveryRetryInterval;
      this._CrossSiteRecoveryRetryInterval = param0;
      this._postSet(42, _oldVal, param0);
   }

   public int getCrossSiteRecoveryLeaseExpiration() {
      return this._CrossSiteRecoveryLeaseExpiration;
   }

   public boolean isCrossSiteRecoveryLeaseExpirationInherited() {
      return false;
   }

   public boolean isCrossSiteRecoveryLeaseExpirationSet() {
      return this._isSet(43);
   }

   public void setCrossSiteRecoveryLeaseExpiration(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CrossSiteRecoveryLeaseExpiration", (long)param0, 1L, 2147483647L);
      int _oldVal = this._CrossSiteRecoveryLeaseExpiration;
      this._CrossSiteRecoveryLeaseExpiration = param0;
      this._postSet(43, _oldVal, param0);
   }

   public int getCrossSiteRecoveryLeaseUpdate() {
      return this._CrossSiteRecoveryLeaseUpdate;
   }

   public boolean isCrossSiteRecoveryLeaseUpdateInherited() {
      return false;
   }

   public boolean isCrossSiteRecoveryLeaseUpdateSet() {
      return this._isSet(44);
   }

   public void setCrossSiteRecoveryLeaseUpdate(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CrossSiteRecoveryLeaseUpdate", (long)param0, 1L, 2147483647L);
      int _oldVal = this._CrossSiteRecoveryLeaseUpdate;
      this._CrossSiteRecoveryLeaseUpdate = param0;
      this._postSet(44, _oldVal, param0);
   }

   public String[] getUsePublicAddressesForRemoteDomains() {
      return this._UsePublicAddressesForRemoteDomains;
   }

   public boolean isUsePublicAddressesForRemoteDomainsInherited() {
      return false;
   }

   public boolean isUsePublicAddressesForRemoteDomainsSet() {
      return this._isSet(45);
   }

   public void setUsePublicAddressesForRemoteDomains(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._UsePublicAddressesForRemoteDomains;
      this._UsePublicAddressesForRemoteDomains = param0;
      this._postSet(45, _oldVal, param0);
   }

   public String[] getUseNonSecureAddressesForDomains() {
      return this._UseNonSecureAddressesForDomains;
   }

   public boolean isUseNonSecureAddressesForDomainsInherited() {
      return false;
   }

   public boolean isUseNonSecureAddressesForDomainsSet() {
      return this._isSet(46);
   }

   public void setUseNonSecureAddressesForDomains(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._UseNonSecureAddressesForDomains;
      this._UseNonSecureAddressesForDomains = param0;
      this._postSet(46, _oldVal, param0);
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._AbandonTimeoutSeconds = 86400;
               if (initOne) {
                  break;
               }
            case 14:
               this._BeforeCompletionIterationLimit = 10;
               if (initOne) {
                  break;
               }
            case 24:
               this._CheckpointIntervalSeconds = 300;
               if (initOne) {
                  break;
               }
            case 12:
               this._CompletionTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 41:
               this._CrossDomainRecoveryRetryInterval = 60;
               if (initOne) {
                  break;
               }
            case 43:
               this._CrossSiteRecoveryLeaseExpiration = 30;
               if (initOne) {
                  break;
               }
            case 44:
               this._CrossSiteRecoveryLeaseUpdate = 10;
               if (initOne) {
                  break;
               }
            case 42:
               this._CrossSiteRecoveryRetryInterval = 60;
               if (initOne) {
                  break;
               }
            case 36:
               this._DeterminerCandidateResourceInfoList = new DeterminerCandidateResourceInfoVBean[0];
               if (initOne) {
                  break;
               }
            case 35:
               this._Determiners = new String[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._ForgetHeuristics = true;
               if (initOne) {
                  break;
               }
            case 17:
               this._MaxResourceRequestsOnServer = 50;
               if (initOne) {
                  break;
               }
            case 19:
               this._MaxResourceUnavailableMillis = 1800000L;
               if (initOne) {
                  break;
               }
            case 39:
               this._MaxRetrySecondsBeforeDeterminerFail = 300;
               if (initOne) {
                  break;
               }
            case 15:
               this._MaxTransactions = 10000;
               if (initOne) {
                  break;
               }
            case 22:
               this._MaxTransactionsHealthIntervalMillis = 60000L;
               if (initOne) {
                  break;
               }
            case 16:
               this._MaxUniqueNameStatistics = 1000;
               if (initOne) {
                  break;
               }
            case 18:
               this._MaxXACallMillis = 120000L;
               if (initOne) {
                  break;
               }
            case 21:
               this._MigrationCheckpointIntervalSeconds = 60;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 27:
               this._ParallelXADispatchPolicy = null;
               if (initOne) {
                  break;
               }
            case 26:
               this._ParallelXAEnabled = true;
               if (initOne) {
                  break;
               }
            case 23:
               this._PurgeResourceFromCheckpointIntervalSeconds = 86400;
               if (initOne) {
                  break;
               }
            case 40:
               this._RecoverySiteName = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._RecoveryThresholdMillis = 300000L;
               if (initOne) {
                  break;
               }
            case 29:
               this._SecurityInteropMode = "default";
               if (initOne) {
                  break;
               }
            case 25:
               this._SerializeEnlistmentsGCIntervalMillis = 30000L;
               if (initOne) {
                  break;
               }
            case 38:
               this._ShutdownGracePeriod = 180;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 10:
               this._TimeoutSeconds = 30;
               if (initOne) {
                  break;
               }
            case 28:
               this._UnregisterResourceGracePeriod = 30;
               if (initOne) {
                  break;
               }
            case 46:
               this._UseNonSecureAddressesForDomains = new String[0];
               if (initOne) {
                  break;
               }
            case 45:
               this._UsePublicAddressesForRemoteDomains = new String[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._WSATTransportSecurityMode = "SSLNotRequired";
               if (initOne) {
                  break;
               }
            case 33:
               this._ClusterwideRecoveryEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 37:
               this._TLOGWriteWhenDeterminerExistsEnabled = false;
               if (initOne) {
                  break;
               }
            case 34:
               this._TightlyCoupledTransactionsEnabled = false;
               if (initOne) {
                  break;
               }
            case 32:
               this._TwoPhaseEnabled = true;
               if (initOne) {
                  break;
               }
            case 31:
               this._WSATIssuedTokenEnabled = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
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
      return "JTA";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("AbandonTimeoutSeconds")) {
         oldVal = this._AbandonTimeoutSeconds;
         this._AbandonTimeoutSeconds = (Integer)v;
         this._postSet(11, oldVal, this._AbandonTimeoutSeconds);
      } else if (name.equals("BeforeCompletionIterationLimit")) {
         oldVal = this._BeforeCompletionIterationLimit;
         this._BeforeCompletionIterationLimit = (Integer)v;
         this._postSet(14, oldVal, this._BeforeCompletionIterationLimit);
      } else if (name.equals("CheckpointIntervalSeconds")) {
         oldVal = this._CheckpointIntervalSeconds;
         this._CheckpointIntervalSeconds = (Integer)v;
         this._postSet(24, oldVal, this._CheckpointIntervalSeconds);
      } else {
         boolean oldVal;
         if (name.equals("ClusterwideRecoveryEnabled")) {
            oldVal = this._ClusterwideRecoveryEnabled;
            this._ClusterwideRecoveryEnabled = (Boolean)v;
            this._postSet(33, oldVal, this._ClusterwideRecoveryEnabled);
         } else if (name.equals("CompletionTimeoutSeconds")) {
            oldVal = this._CompletionTimeoutSeconds;
            this._CompletionTimeoutSeconds = (Integer)v;
            this._postSet(12, oldVal, this._CompletionTimeoutSeconds);
         } else if (name.equals("CrossDomainRecoveryRetryInterval")) {
            oldVal = this._CrossDomainRecoveryRetryInterval;
            this._CrossDomainRecoveryRetryInterval = (Integer)v;
            this._postSet(41, oldVal, this._CrossDomainRecoveryRetryInterval);
         } else if (name.equals("CrossSiteRecoveryLeaseExpiration")) {
            oldVal = this._CrossSiteRecoveryLeaseExpiration;
            this._CrossSiteRecoveryLeaseExpiration = (Integer)v;
            this._postSet(43, oldVal, this._CrossSiteRecoveryLeaseExpiration);
         } else if (name.equals("CrossSiteRecoveryLeaseUpdate")) {
            oldVal = this._CrossSiteRecoveryLeaseUpdate;
            this._CrossSiteRecoveryLeaseUpdate = (Integer)v;
            this._postSet(44, oldVal, this._CrossSiteRecoveryLeaseUpdate);
         } else if (name.equals("CrossSiteRecoveryRetryInterval")) {
            oldVal = this._CrossSiteRecoveryRetryInterval;
            this._CrossSiteRecoveryRetryInterval = (Integer)v;
            this._postSet(42, oldVal, this._CrossSiteRecoveryRetryInterval);
         } else if (name.equals("DeterminerCandidateResourceInfoList")) {
            DeterminerCandidateResourceInfoVBean[] oldVal = this._DeterminerCandidateResourceInfoList;
            this._DeterminerCandidateResourceInfoList = (DeterminerCandidateResourceInfoVBean[])((DeterminerCandidateResourceInfoVBean[])v);
            this._postSet(36, oldVal, this._DeterminerCandidateResourceInfoList);
         } else {
            String[] oldVal;
            if (name.equals("Determiners")) {
               oldVal = this._Determiners;
               this._Determiners = (String[])((String[])v);
               this._postSet(35, oldVal, this._Determiners);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("ForgetHeuristics")) {
               oldVal = this._ForgetHeuristics;
               this._ForgetHeuristics = (Boolean)v;
               this._postSet(13, oldVal, this._ForgetHeuristics);
            } else if (name.equals("MaxResourceRequestsOnServer")) {
               oldVal = this._MaxResourceRequestsOnServer;
               this._MaxResourceRequestsOnServer = (Integer)v;
               this._postSet(17, oldVal, this._MaxResourceRequestsOnServer);
            } else {
               long oldVal;
               if (name.equals("MaxResourceUnavailableMillis")) {
                  oldVal = this._MaxResourceUnavailableMillis;
                  this._MaxResourceUnavailableMillis = (Long)v;
                  this._postSet(19, oldVal, this._MaxResourceUnavailableMillis);
               } else if (name.equals("MaxRetrySecondsBeforeDeterminerFail")) {
                  oldVal = this._MaxRetrySecondsBeforeDeterminerFail;
                  this._MaxRetrySecondsBeforeDeterminerFail = (Integer)v;
                  this._postSet(39, oldVal, this._MaxRetrySecondsBeforeDeterminerFail);
               } else if (name.equals("MaxTransactions")) {
                  oldVal = this._MaxTransactions;
                  this._MaxTransactions = (Integer)v;
                  this._postSet(15, oldVal, this._MaxTransactions);
               } else if (name.equals("MaxTransactionsHealthIntervalMillis")) {
                  oldVal = this._MaxTransactionsHealthIntervalMillis;
                  this._MaxTransactionsHealthIntervalMillis = (Long)v;
                  this._postSet(22, oldVal, this._MaxTransactionsHealthIntervalMillis);
               } else if (name.equals("MaxUniqueNameStatistics")) {
                  oldVal = this._MaxUniqueNameStatistics;
                  this._MaxUniqueNameStatistics = (Integer)v;
                  this._postSet(16, oldVal, this._MaxUniqueNameStatistics);
               } else if (name.equals("MaxXACallMillis")) {
                  oldVal = this._MaxXACallMillis;
                  this._MaxXACallMillis = (Long)v;
                  this._postSet(18, oldVal, this._MaxXACallMillis);
               } else if (name.equals("MigrationCheckpointIntervalSeconds")) {
                  oldVal = this._MigrationCheckpointIntervalSeconds;
                  this._MigrationCheckpointIntervalSeconds = (Integer)v;
                  this._postSet(21, oldVal, this._MigrationCheckpointIntervalSeconds);
               } else {
                  String oldVal;
                  if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("ParallelXADispatchPolicy")) {
                     oldVal = this._ParallelXADispatchPolicy;
                     this._ParallelXADispatchPolicy = (String)v;
                     this._postSet(27, oldVal, this._ParallelXADispatchPolicy);
                  } else if (name.equals("ParallelXAEnabled")) {
                     oldVal = this._ParallelXAEnabled;
                     this._ParallelXAEnabled = (Boolean)v;
                     this._postSet(26, oldVal, this._ParallelXAEnabled);
                  } else if (name.equals("PurgeResourceFromCheckpointIntervalSeconds")) {
                     oldVal = this._PurgeResourceFromCheckpointIntervalSeconds;
                     this._PurgeResourceFromCheckpointIntervalSeconds = (Integer)v;
                     this._postSet(23, oldVal, this._PurgeResourceFromCheckpointIntervalSeconds);
                  } else if (name.equals("RecoverySiteName")) {
                     oldVal = this._RecoverySiteName;
                     this._RecoverySiteName = (String)v;
                     this._postSet(40, oldVal, this._RecoverySiteName);
                  } else if (name.equals("RecoveryThresholdMillis")) {
                     oldVal = this._RecoveryThresholdMillis;
                     this._RecoveryThresholdMillis = (Long)v;
                     this._postSet(20, oldVal, this._RecoveryThresholdMillis);
                  } else if (name.equals("SecurityInteropMode")) {
                     oldVal = this._SecurityInteropMode;
                     this._SecurityInteropMode = (String)v;
                     this._postSet(29, oldVal, this._SecurityInteropMode);
                  } else if (name.equals("SerializeEnlistmentsGCIntervalMillis")) {
                     oldVal = this._SerializeEnlistmentsGCIntervalMillis;
                     this._SerializeEnlistmentsGCIntervalMillis = (Long)v;
                     this._postSet(25, oldVal, this._SerializeEnlistmentsGCIntervalMillis);
                  } else if (name.equals("ShutdownGracePeriod")) {
                     oldVal = this._ShutdownGracePeriod;
                     this._ShutdownGracePeriod = (Integer)v;
                     this._postSet(38, oldVal, this._ShutdownGracePeriod);
                  } else if (name.equals("TLOGWriteWhenDeterminerExistsEnabled")) {
                     oldVal = this._TLOGWriteWhenDeterminerExistsEnabled;
                     this._TLOGWriteWhenDeterminerExistsEnabled = (Boolean)v;
                     this._postSet(37, oldVal, this._TLOGWriteWhenDeterminerExistsEnabled);
                  } else if (name.equals("Tags")) {
                     oldVal = this._Tags;
                     this._Tags = (String[])((String[])v);
                     this._postSet(9, oldVal, this._Tags);
                  } else if (name.equals("TightlyCoupledTransactionsEnabled")) {
                     oldVal = this._TightlyCoupledTransactionsEnabled;
                     this._TightlyCoupledTransactionsEnabled = (Boolean)v;
                     this._postSet(34, oldVal, this._TightlyCoupledTransactionsEnabled);
                  } else if (name.equals("TimeoutSeconds")) {
                     oldVal = this._TimeoutSeconds;
                     this._TimeoutSeconds = (Integer)v;
                     this._postSet(10, oldVal, this._TimeoutSeconds);
                  } else if (name.equals("TwoPhaseEnabled")) {
                     oldVal = this._TwoPhaseEnabled;
                     this._TwoPhaseEnabled = (Boolean)v;
                     this._postSet(32, oldVal, this._TwoPhaseEnabled);
                  } else if (name.equals("UnregisterResourceGracePeriod")) {
                     oldVal = this._UnregisterResourceGracePeriod;
                     this._UnregisterResourceGracePeriod = (Integer)v;
                     this._postSet(28, oldVal, this._UnregisterResourceGracePeriod);
                  } else if (name.equals("UseNonSecureAddressesForDomains")) {
                     oldVal = this._UseNonSecureAddressesForDomains;
                     this._UseNonSecureAddressesForDomains = (String[])((String[])v);
                     this._postSet(46, oldVal, this._UseNonSecureAddressesForDomains);
                  } else if (name.equals("UsePublicAddressesForRemoteDomains")) {
                     oldVal = this._UsePublicAddressesForRemoteDomains;
                     this._UsePublicAddressesForRemoteDomains = (String[])((String[])v);
                     this._postSet(45, oldVal, this._UsePublicAddressesForRemoteDomains);
                  } else if (name.equals("WSATIssuedTokenEnabled")) {
                     oldVal = this._WSATIssuedTokenEnabled;
                     this._WSATIssuedTokenEnabled = (Boolean)v;
                     this._postSet(31, oldVal, this._WSATIssuedTokenEnabled);
                  } else if (name.equals("WSATTransportSecurityMode")) {
                     oldVal = this._WSATTransportSecurityMode;
                     this._WSATTransportSecurityMode = (String)v;
                     this._postSet(30, oldVal, this._WSATTransportSecurityMode);
                  } else if (name.equals("customizer")) {
                     JTAMBeanCustomizer oldVal = this._customizer;
                     this._customizer = (JTAMBeanCustomizer)v;
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AbandonTimeoutSeconds")) {
         return new Integer(this._AbandonTimeoutSeconds);
      } else if (name.equals("BeforeCompletionIterationLimit")) {
         return new Integer(this._BeforeCompletionIterationLimit);
      } else if (name.equals("CheckpointIntervalSeconds")) {
         return new Integer(this._CheckpointIntervalSeconds);
      } else if (name.equals("ClusterwideRecoveryEnabled")) {
         return new Boolean(this._ClusterwideRecoveryEnabled);
      } else if (name.equals("CompletionTimeoutSeconds")) {
         return new Integer(this._CompletionTimeoutSeconds);
      } else if (name.equals("CrossDomainRecoveryRetryInterval")) {
         return new Integer(this._CrossDomainRecoveryRetryInterval);
      } else if (name.equals("CrossSiteRecoveryLeaseExpiration")) {
         return new Integer(this._CrossSiteRecoveryLeaseExpiration);
      } else if (name.equals("CrossSiteRecoveryLeaseUpdate")) {
         return new Integer(this._CrossSiteRecoveryLeaseUpdate);
      } else if (name.equals("CrossSiteRecoveryRetryInterval")) {
         return new Integer(this._CrossSiteRecoveryRetryInterval);
      } else if (name.equals("DeterminerCandidateResourceInfoList")) {
         return this._DeterminerCandidateResourceInfoList;
      } else if (name.equals("Determiners")) {
         return this._Determiners;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ForgetHeuristics")) {
         return new Boolean(this._ForgetHeuristics);
      } else if (name.equals("MaxResourceRequestsOnServer")) {
         return new Integer(this._MaxResourceRequestsOnServer);
      } else if (name.equals("MaxResourceUnavailableMillis")) {
         return new Long(this._MaxResourceUnavailableMillis);
      } else if (name.equals("MaxRetrySecondsBeforeDeterminerFail")) {
         return new Integer(this._MaxRetrySecondsBeforeDeterminerFail);
      } else if (name.equals("MaxTransactions")) {
         return new Integer(this._MaxTransactions);
      } else if (name.equals("MaxTransactionsHealthIntervalMillis")) {
         return new Long(this._MaxTransactionsHealthIntervalMillis);
      } else if (name.equals("MaxUniqueNameStatistics")) {
         return new Integer(this._MaxUniqueNameStatistics);
      } else if (name.equals("MaxXACallMillis")) {
         return new Long(this._MaxXACallMillis);
      } else if (name.equals("MigrationCheckpointIntervalSeconds")) {
         return new Integer(this._MigrationCheckpointIntervalSeconds);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("ParallelXADispatchPolicy")) {
         return this._ParallelXADispatchPolicy;
      } else if (name.equals("ParallelXAEnabled")) {
         return new Boolean(this._ParallelXAEnabled);
      } else if (name.equals("PurgeResourceFromCheckpointIntervalSeconds")) {
         return new Integer(this._PurgeResourceFromCheckpointIntervalSeconds);
      } else if (name.equals("RecoverySiteName")) {
         return this._RecoverySiteName;
      } else if (name.equals("RecoveryThresholdMillis")) {
         return new Long(this._RecoveryThresholdMillis);
      } else if (name.equals("SecurityInteropMode")) {
         return this._SecurityInteropMode;
      } else if (name.equals("SerializeEnlistmentsGCIntervalMillis")) {
         return new Long(this._SerializeEnlistmentsGCIntervalMillis);
      } else if (name.equals("ShutdownGracePeriod")) {
         return new Integer(this._ShutdownGracePeriod);
      } else if (name.equals("TLOGWriteWhenDeterminerExistsEnabled")) {
         return new Boolean(this._TLOGWriteWhenDeterminerExistsEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("TightlyCoupledTransactionsEnabled")) {
         return new Boolean(this._TightlyCoupledTransactionsEnabled);
      } else if (name.equals("TimeoutSeconds")) {
         return new Integer(this._TimeoutSeconds);
      } else if (name.equals("TwoPhaseEnabled")) {
         return new Boolean(this._TwoPhaseEnabled);
      } else if (name.equals("UnregisterResourceGracePeriod")) {
         return new Integer(this._UnregisterResourceGracePeriod);
      } else if (name.equals("UseNonSecureAddressesForDomains")) {
         return this._UseNonSecureAddressesForDomains;
      } else if (name.equals("UsePublicAddressesForRemoteDomains")) {
         return this._UsePublicAddressesForRemoteDomains;
      } else if (name.equals("WSATIssuedTokenEnabled")) {
         return new Boolean(this._WSATIssuedTokenEnabled);
      } else if (name.equals("WSATTransportSecurityMode")) {
         return this._WSATTransportSecurityMode;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 20:
            case 22:
            case 24:
            case 29:
            case 30:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            default:
               break;
            case 10:
               if (s.equals("determiner")) {
                  return 35;
               }
               break;
            case 15:
               if (s.equals("timeout-seconds")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("max-transactions")) {
                  return 15;
               }
               break;
            case 17:
               if (s.equals("forget-heuristics")) {
                  return 13;
               }

               if (s.equals("two-phase-enabled")) {
                  return 32;
               }
               break;
            case 18:
               if (s.equals("max-xa-call-millis")) {
                  return 18;
               }

               if (s.equals("recovery-site-name")) {
                  return 40;
               }
               break;
            case 19:
               if (s.equals("parallel-xa-enabled")) {
                  return 26;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 21:
               if (s.equals("security-interop-mode")) {
                  return 29;
               }

               if (s.equals("shutdown-grace-period")) {
                  return 38;
               }
               break;
            case 23:
               if (s.equals("abandon-timeout-seconds")) {
                  return 11;
               }
               break;
            case 25:
               if (s.equals("recovery-threshold-millis")) {
                  return 20;
               }

               if (s.equals("wsat-issued-token-enabled")) {
                  return 31;
               }
               break;
            case 26:
               if (s.equals("completion-timeout-seconds")) {
                  return 12;
               }

               if (s.equals("max-unique-name-statistics")) {
                  return 16;
               }
               break;
            case 27:
               if (s.equals("checkpoint-interval-seconds")) {
                  return 24;
               }

               if (s.equals("parallel-xa-dispatch-policy")) {
                  return 27;
               }
               break;
            case 28:
               if (s.equals("wsat-transport-security-mode")) {
                  return 30;
               }

               if (s.equals("clusterwide-recovery-enabled")) {
                  return 33;
               }
               break;
            case 31:
               if (s.equals("max-resource-requests-on-server")) {
                  return 17;
               }

               if (s.equals("max-resource-unavailable-millis")) {
                  return 19;
               }
               break;
            case 32:
               if (s.equals("cross-site-recovery-lease-update")) {
                  return 44;
               }

               if (s.equals("unregister-resource-grace-period")) {
                  return 28;
               }
               break;
            case 33:
               if (s.equals("before-completion-iteration-limit")) {
                  return 14;
               }
               break;
            case 34:
               if (s.equals("cross-site-recovery-retry-interval")) {
                  return 42;
               }
               break;
            case 35:
               if (s.equals("use-non-secure-addresses-for-domain")) {
                  return 46;
               }
               break;
            case 36:
               if (s.equals("cross-domain-recovery-retry-interval")) {
                  return 41;
               }

               if (s.equals("cross-site-recovery-lease-expiration")) {
                  return 43;
               }

               if (s.equals("tightly-coupled-transactions-enabled")) {
                  return 34;
               }
               break;
            case 37:
               if (s.equals("migration-checkpoint-interval-seconds")) {
                  return 21;
               }
               break;
            case 38:
               if (s.equals("use-public-addresses-for-remote-domain")) {
                  return 45;
               }
               break;
            case 39:
               if (s.equals("determiner-candidate-resource-info-list")) {
                  return 36;
               }

               if (s.equals("max-transactions-health-interval-millis")) {
                  return 22;
               }

               if (s.equals("serialize-enlistmentsgc-interval-millis")) {
                  return 25;
               }
               break;
            case 40:
               if (s.equals("max-retry-seconds-before-determiner-fail")) {
                  return 39;
               }
               break;
            case 41:
               if (s.equals("tlog-write-when-determiner-exists-enabled")) {
                  return 37;
               }
               break;
            case 47:
               if (s.equals("purge-resource-from-checkpoint-interval-seconds")) {
                  return 23;
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
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "timeout-seconds";
            case 11:
               return "abandon-timeout-seconds";
            case 12:
               return "completion-timeout-seconds";
            case 13:
               return "forget-heuristics";
            case 14:
               return "before-completion-iteration-limit";
            case 15:
               return "max-transactions";
            case 16:
               return "max-unique-name-statistics";
            case 17:
               return "max-resource-requests-on-server";
            case 18:
               return "max-xa-call-millis";
            case 19:
               return "max-resource-unavailable-millis";
            case 20:
               return "recovery-threshold-millis";
            case 21:
               return "migration-checkpoint-interval-seconds";
            case 22:
               return "max-transactions-health-interval-millis";
            case 23:
               return "purge-resource-from-checkpoint-interval-seconds";
            case 24:
               return "checkpoint-interval-seconds";
            case 25:
               return "serialize-enlistmentsgc-interval-millis";
            case 26:
               return "parallel-xa-enabled";
            case 27:
               return "parallel-xa-dispatch-policy";
            case 28:
               return "unregister-resource-grace-period";
            case 29:
               return "security-interop-mode";
            case 30:
               return "wsat-transport-security-mode";
            case 31:
               return "wsat-issued-token-enabled";
            case 32:
               return "two-phase-enabled";
            case 33:
               return "clusterwide-recovery-enabled";
            case 34:
               return "tightly-coupled-transactions-enabled";
            case 35:
               return "determiner";
            case 36:
               return "determiner-candidate-resource-info-list";
            case 37:
               return "tlog-write-when-determiner-exists-enabled";
            case 38:
               return "shutdown-grace-period";
            case 39:
               return "max-retry-seconds-before-determiner-fail";
            case 40:
               return "recovery-site-name";
            case 41:
               return "cross-domain-recovery-retry-interval";
            case 42:
               return "cross-site-recovery-retry-interval";
            case 43:
               return "cross-site-recovery-lease-expiration";
            case 44:
               return "cross-site-recovery-lease-update";
            case 45:
               return "use-public-addresses-for-remote-domain";
            case 46:
               return "use-non-secure-addresses-for-domain";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 35:
               return true;
            case 36:
               return true;
            case 45:
               return true;
            case 46:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 29:
               return true;
            case 30:
               return true;
            case 31:
            case 32:
            case 33:
            case 34:
            case 36:
            case 37:
            case 38:
            case 39:
            default:
               return super.isConfigurable(propIndex);
            case 35:
               return true;
            case 40:
               return true;
            case 41:
               return true;
            case 42:
               return true;
            case 43:
               return true;
            case 44:
               return true;
            case 45:
               return true;
            case 46:
               return true;
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private JTAMBeanImpl bean;

      protected Helper(JTAMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "TimeoutSeconds";
            case 11:
               return "AbandonTimeoutSeconds";
            case 12:
               return "CompletionTimeoutSeconds";
            case 13:
               return "ForgetHeuristics";
            case 14:
               return "BeforeCompletionIterationLimit";
            case 15:
               return "MaxTransactions";
            case 16:
               return "MaxUniqueNameStatistics";
            case 17:
               return "MaxResourceRequestsOnServer";
            case 18:
               return "MaxXACallMillis";
            case 19:
               return "MaxResourceUnavailableMillis";
            case 20:
               return "RecoveryThresholdMillis";
            case 21:
               return "MigrationCheckpointIntervalSeconds";
            case 22:
               return "MaxTransactionsHealthIntervalMillis";
            case 23:
               return "PurgeResourceFromCheckpointIntervalSeconds";
            case 24:
               return "CheckpointIntervalSeconds";
            case 25:
               return "SerializeEnlistmentsGCIntervalMillis";
            case 26:
               return "ParallelXAEnabled";
            case 27:
               return "ParallelXADispatchPolicy";
            case 28:
               return "UnregisterResourceGracePeriod";
            case 29:
               return "SecurityInteropMode";
            case 30:
               return "WSATTransportSecurityMode";
            case 31:
               return "WSATIssuedTokenEnabled";
            case 32:
               return "TwoPhaseEnabled";
            case 33:
               return "ClusterwideRecoveryEnabled";
            case 34:
               return "TightlyCoupledTransactionsEnabled";
            case 35:
               return "Determiners";
            case 36:
               return "DeterminerCandidateResourceInfoList";
            case 37:
               return "TLOGWriteWhenDeterminerExistsEnabled";
            case 38:
               return "ShutdownGracePeriod";
            case 39:
               return "MaxRetrySecondsBeforeDeterminerFail";
            case 40:
               return "RecoverySiteName";
            case 41:
               return "CrossDomainRecoveryRetryInterval";
            case 42:
               return "CrossSiteRecoveryRetryInterval";
            case 43:
               return "CrossSiteRecoveryLeaseExpiration";
            case 44:
               return "CrossSiteRecoveryLeaseUpdate";
            case 45:
               return "UsePublicAddressesForRemoteDomains";
            case 46:
               return "UseNonSecureAddressesForDomains";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AbandonTimeoutSeconds")) {
            return 11;
         } else if (propName.equals("BeforeCompletionIterationLimit")) {
            return 14;
         } else if (propName.equals("CheckpointIntervalSeconds")) {
            return 24;
         } else if (propName.equals("CompletionTimeoutSeconds")) {
            return 12;
         } else if (propName.equals("CrossDomainRecoveryRetryInterval")) {
            return 41;
         } else if (propName.equals("CrossSiteRecoveryLeaseExpiration")) {
            return 43;
         } else if (propName.equals("CrossSiteRecoveryLeaseUpdate")) {
            return 44;
         } else if (propName.equals("CrossSiteRecoveryRetryInterval")) {
            return 42;
         } else if (propName.equals("DeterminerCandidateResourceInfoList")) {
            return 36;
         } else if (propName.equals("Determiners")) {
            return 35;
         } else if (propName.equals("ForgetHeuristics")) {
            return 13;
         } else if (propName.equals("MaxResourceRequestsOnServer")) {
            return 17;
         } else if (propName.equals("MaxResourceUnavailableMillis")) {
            return 19;
         } else if (propName.equals("MaxRetrySecondsBeforeDeterminerFail")) {
            return 39;
         } else if (propName.equals("MaxTransactions")) {
            return 15;
         } else if (propName.equals("MaxTransactionsHealthIntervalMillis")) {
            return 22;
         } else if (propName.equals("MaxUniqueNameStatistics")) {
            return 16;
         } else if (propName.equals("MaxXACallMillis")) {
            return 18;
         } else if (propName.equals("MigrationCheckpointIntervalSeconds")) {
            return 21;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("ParallelXADispatchPolicy")) {
            return 27;
         } else if (propName.equals("ParallelXAEnabled")) {
            return 26;
         } else if (propName.equals("PurgeResourceFromCheckpointIntervalSeconds")) {
            return 23;
         } else if (propName.equals("RecoverySiteName")) {
            return 40;
         } else if (propName.equals("RecoveryThresholdMillis")) {
            return 20;
         } else if (propName.equals("SecurityInteropMode")) {
            return 29;
         } else if (propName.equals("SerializeEnlistmentsGCIntervalMillis")) {
            return 25;
         } else if (propName.equals("ShutdownGracePeriod")) {
            return 38;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("TimeoutSeconds")) {
            return 10;
         } else if (propName.equals("UnregisterResourceGracePeriod")) {
            return 28;
         } else if (propName.equals("UseNonSecureAddressesForDomains")) {
            return 46;
         } else if (propName.equals("UsePublicAddressesForRemoteDomains")) {
            return 45;
         } else if (propName.equals("WSATTransportSecurityMode")) {
            return 30;
         } else if (propName.equals("ClusterwideRecoveryEnabled")) {
            return 33;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("TLOGWriteWhenDeterminerExistsEnabled")) {
            return 37;
         } else if (propName.equals("TightlyCoupledTransactionsEnabled")) {
            return 34;
         } else if (propName.equals("TwoPhaseEnabled")) {
            return 32;
         } else {
            return propName.equals("WSATIssuedTokenEnabled") ? 31 : super.getPropertyIndex(propName);
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
            if (this.bean.isAbandonTimeoutSecondsSet()) {
               buf.append("AbandonTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getAbandonTimeoutSeconds()));
            }

            if (this.bean.isBeforeCompletionIterationLimitSet()) {
               buf.append("BeforeCompletionIterationLimit");
               buf.append(String.valueOf(this.bean.getBeforeCompletionIterationLimit()));
            }

            if (this.bean.isCheckpointIntervalSecondsSet()) {
               buf.append("CheckpointIntervalSeconds");
               buf.append(String.valueOf(this.bean.getCheckpointIntervalSeconds()));
            }

            if (this.bean.isCompletionTimeoutSecondsSet()) {
               buf.append("CompletionTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getCompletionTimeoutSeconds()));
            }

            if (this.bean.isCrossDomainRecoveryRetryIntervalSet()) {
               buf.append("CrossDomainRecoveryRetryInterval");
               buf.append(String.valueOf(this.bean.getCrossDomainRecoveryRetryInterval()));
            }

            if (this.bean.isCrossSiteRecoveryLeaseExpirationSet()) {
               buf.append("CrossSiteRecoveryLeaseExpiration");
               buf.append(String.valueOf(this.bean.getCrossSiteRecoveryLeaseExpiration()));
            }

            if (this.bean.isCrossSiteRecoveryLeaseUpdateSet()) {
               buf.append("CrossSiteRecoveryLeaseUpdate");
               buf.append(String.valueOf(this.bean.getCrossSiteRecoveryLeaseUpdate()));
            }

            if (this.bean.isCrossSiteRecoveryRetryIntervalSet()) {
               buf.append("CrossSiteRecoveryRetryInterval");
               buf.append(String.valueOf(this.bean.getCrossSiteRecoveryRetryInterval()));
            }

            if (this.bean.isDeterminerCandidateResourceInfoListSet()) {
               buf.append("DeterminerCandidateResourceInfoList");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDeterminerCandidateResourceInfoList())));
            }

            if (this.bean.isDeterminersSet()) {
               buf.append("Determiners");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDeterminers())));
            }

            if (this.bean.isForgetHeuristicsSet()) {
               buf.append("ForgetHeuristics");
               buf.append(String.valueOf(this.bean.getForgetHeuristics()));
            }

            if (this.bean.isMaxResourceRequestsOnServerSet()) {
               buf.append("MaxResourceRequestsOnServer");
               buf.append(String.valueOf(this.bean.getMaxResourceRequestsOnServer()));
            }

            if (this.bean.isMaxResourceUnavailableMillisSet()) {
               buf.append("MaxResourceUnavailableMillis");
               buf.append(String.valueOf(this.bean.getMaxResourceUnavailableMillis()));
            }

            if (this.bean.isMaxRetrySecondsBeforeDeterminerFailSet()) {
               buf.append("MaxRetrySecondsBeforeDeterminerFail");
               buf.append(String.valueOf(this.bean.getMaxRetrySecondsBeforeDeterminerFail()));
            }

            if (this.bean.isMaxTransactionsSet()) {
               buf.append("MaxTransactions");
               buf.append(String.valueOf(this.bean.getMaxTransactions()));
            }

            if (this.bean.isMaxTransactionsHealthIntervalMillisSet()) {
               buf.append("MaxTransactionsHealthIntervalMillis");
               buf.append(String.valueOf(this.bean.getMaxTransactionsHealthIntervalMillis()));
            }

            if (this.bean.isMaxUniqueNameStatisticsSet()) {
               buf.append("MaxUniqueNameStatistics");
               buf.append(String.valueOf(this.bean.getMaxUniqueNameStatistics()));
            }

            if (this.bean.isMaxXACallMillisSet()) {
               buf.append("MaxXACallMillis");
               buf.append(String.valueOf(this.bean.getMaxXACallMillis()));
            }

            if (this.bean.isMigrationCheckpointIntervalSecondsSet()) {
               buf.append("MigrationCheckpointIntervalSeconds");
               buf.append(String.valueOf(this.bean.getMigrationCheckpointIntervalSeconds()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isParallelXADispatchPolicySet()) {
               buf.append("ParallelXADispatchPolicy");
               buf.append(String.valueOf(this.bean.getParallelXADispatchPolicy()));
            }

            if (this.bean.isParallelXAEnabledSet()) {
               buf.append("ParallelXAEnabled");
               buf.append(String.valueOf(this.bean.getParallelXAEnabled()));
            }

            if (this.bean.isPurgeResourceFromCheckpointIntervalSecondsSet()) {
               buf.append("PurgeResourceFromCheckpointIntervalSeconds");
               buf.append(String.valueOf(this.bean.getPurgeResourceFromCheckpointIntervalSeconds()));
            }

            if (this.bean.isRecoverySiteNameSet()) {
               buf.append("RecoverySiteName");
               buf.append(String.valueOf(this.bean.getRecoverySiteName()));
            }

            if (this.bean.isRecoveryThresholdMillisSet()) {
               buf.append("RecoveryThresholdMillis");
               buf.append(String.valueOf(this.bean.getRecoveryThresholdMillis()));
            }

            if (this.bean.isSecurityInteropModeSet()) {
               buf.append("SecurityInteropMode");
               buf.append(String.valueOf(this.bean.getSecurityInteropMode()));
            }

            if (this.bean.isSerializeEnlistmentsGCIntervalMillisSet()) {
               buf.append("SerializeEnlistmentsGCIntervalMillis");
               buf.append(String.valueOf(this.bean.getSerializeEnlistmentsGCIntervalMillis()));
            }

            if (this.bean.isShutdownGracePeriodSet()) {
               buf.append("ShutdownGracePeriod");
               buf.append(String.valueOf(this.bean.getShutdownGracePeriod()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTimeoutSecondsSet()) {
               buf.append("TimeoutSeconds");
               buf.append(String.valueOf(this.bean.getTimeoutSeconds()));
            }

            if (this.bean.isUnregisterResourceGracePeriodSet()) {
               buf.append("UnregisterResourceGracePeriod");
               buf.append(String.valueOf(this.bean.getUnregisterResourceGracePeriod()));
            }

            if (this.bean.isUseNonSecureAddressesForDomainsSet()) {
               buf.append("UseNonSecureAddressesForDomains");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUseNonSecureAddressesForDomains())));
            }

            if (this.bean.isUsePublicAddressesForRemoteDomainsSet()) {
               buf.append("UsePublicAddressesForRemoteDomains");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUsePublicAddressesForRemoteDomains())));
            }

            if (this.bean.isWSATTransportSecurityModeSet()) {
               buf.append("WSATTransportSecurityMode");
               buf.append(String.valueOf(this.bean.getWSATTransportSecurityMode()));
            }

            if (this.bean.isClusterwideRecoveryEnabledSet()) {
               buf.append("ClusterwideRecoveryEnabled");
               buf.append(String.valueOf(this.bean.isClusterwideRecoveryEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isTLOGWriteWhenDeterminerExistsEnabledSet()) {
               buf.append("TLOGWriteWhenDeterminerExistsEnabled");
               buf.append(String.valueOf(this.bean.isTLOGWriteWhenDeterminerExistsEnabled()));
            }

            if (this.bean.isTightlyCoupledTransactionsEnabledSet()) {
               buf.append("TightlyCoupledTransactionsEnabled");
               buf.append(String.valueOf(this.bean.isTightlyCoupledTransactionsEnabled()));
            }

            if (this.bean.isTwoPhaseEnabledSet()) {
               buf.append("TwoPhaseEnabled");
               buf.append(String.valueOf(this.bean.isTwoPhaseEnabled()));
            }

            if (this.bean.isWSATIssuedTokenEnabledSet()) {
               buf.append("WSATIssuedTokenEnabled");
               buf.append(String.valueOf(this.bean.isWSATIssuedTokenEnabled()));
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
            JTAMBeanImpl otherTyped = (JTAMBeanImpl)other;
            this.computeDiff("AbandonTimeoutSeconds", this.bean.getAbandonTimeoutSeconds(), otherTyped.getAbandonTimeoutSeconds(), true);
            this.computeDiff("BeforeCompletionIterationLimit", this.bean.getBeforeCompletionIterationLimit(), otherTyped.getBeforeCompletionIterationLimit(), true);
            this.computeDiff("CheckpointIntervalSeconds", this.bean.getCheckpointIntervalSeconds(), otherTyped.getCheckpointIntervalSeconds(), true);
            this.computeDiff("CompletionTimeoutSeconds", this.bean.getCompletionTimeoutSeconds(), otherTyped.getCompletionTimeoutSeconds(), true);
            this.computeDiff("CrossDomainRecoveryRetryInterval", this.bean.getCrossDomainRecoveryRetryInterval(), otherTyped.getCrossDomainRecoveryRetryInterval(), true);
            this.computeDiff("CrossSiteRecoveryLeaseExpiration", this.bean.getCrossSiteRecoveryLeaseExpiration(), otherTyped.getCrossSiteRecoveryLeaseExpiration(), true);
            this.computeDiff("CrossSiteRecoveryLeaseUpdate", this.bean.getCrossSiteRecoveryLeaseUpdate(), otherTyped.getCrossSiteRecoveryLeaseUpdate(), true);
            this.computeDiff("CrossSiteRecoveryRetryInterval", this.bean.getCrossSiteRecoveryRetryInterval(), otherTyped.getCrossSiteRecoveryRetryInterval(), true);
            this.computeDiff("Determiners", this.bean.getDeterminers(), otherTyped.getDeterminers(), true);
            this.computeDiff("ForgetHeuristics", this.bean.getForgetHeuristics(), otherTyped.getForgetHeuristics(), true);
            this.computeDiff("MaxResourceRequestsOnServer", this.bean.getMaxResourceRequestsOnServer(), otherTyped.getMaxResourceRequestsOnServer(), true);
            this.computeDiff("MaxResourceUnavailableMillis", this.bean.getMaxResourceUnavailableMillis(), otherTyped.getMaxResourceUnavailableMillis(), true);
            this.computeDiff("MaxRetrySecondsBeforeDeterminerFail", this.bean.getMaxRetrySecondsBeforeDeterminerFail(), otherTyped.getMaxRetrySecondsBeforeDeterminerFail(), true);
            this.computeDiff("MaxTransactions", this.bean.getMaxTransactions(), otherTyped.getMaxTransactions(), true);
            this.computeDiff("MaxTransactionsHealthIntervalMillis", this.bean.getMaxTransactionsHealthIntervalMillis(), otherTyped.getMaxTransactionsHealthIntervalMillis(), true);
            this.computeDiff("MaxUniqueNameStatistics", this.bean.getMaxUniqueNameStatistics(), otherTyped.getMaxUniqueNameStatistics(), true);
            this.computeDiff("MaxXACallMillis", this.bean.getMaxXACallMillis(), otherTyped.getMaxXACallMillis(), true);
            this.computeDiff("MigrationCheckpointIntervalSeconds", this.bean.getMigrationCheckpointIntervalSeconds(), otherTyped.getMigrationCheckpointIntervalSeconds(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("ParallelXADispatchPolicy", this.bean.getParallelXADispatchPolicy(), otherTyped.getParallelXADispatchPolicy(), true);
            this.computeDiff("ParallelXAEnabled", this.bean.getParallelXAEnabled(), otherTyped.getParallelXAEnabled(), true);
            this.computeDiff("PurgeResourceFromCheckpointIntervalSeconds", this.bean.getPurgeResourceFromCheckpointIntervalSeconds(), otherTyped.getPurgeResourceFromCheckpointIntervalSeconds(), true);
            this.computeDiff("RecoverySiteName", this.bean.getRecoverySiteName(), otherTyped.getRecoverySiteName(), true);
            this.computeDiff("RecoveryThresholdMillis", this.bean.getRecoveryThresholdMillis(), otherTyped.getRecoveryThresholdMillis(), true);
            this.computeDiff("SecurityInteropMode", this.bean.getSecurityInteropMode(), otherTyped.getSecurityInteropMode(), false);
            this.computeDiff("SerializeEnlistmentsGCIntervalMillis", this.bean.getSerializeEnlistmentsGCIntervalMillis(), otherTyped.getSerializeEnlistmentsGCIntervalMillis(), true);
            this.computeDiff("ShutdownGracePeriod", this.bean.getShutdownGracePeriod(), otherTyped.getShutdownGracePeriod(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("TimeoutSeconds", this.bean.getTimeoutSeconds(), otherTyped.getTimeoutSeconds(), true);
            this.computeDiff("UnregisterResourceGracePeriod", this.bean.getUnregisterResourceGracePeriod(), otherTyped.getUnregisterResourceGracePeriod(), true);
            this.computeDiff("UseNonSecureAddressesForDomains", this.bean.getUseNonSecureAddressesForDomains(), otherTyped.getUseNonSecureAddressesForDomains(), true);
            this.computeDiff("UsePublicAddressesForRemoteDomains", this.bean.getUsePublicAddressesForRemoteDomains(), otherTyped.getUsePublicAddressesForRemoteDomains(), true);
            this.computeDiff("WSATTransportSecurityMode", this.bean.getWSATTransportSecurityMode(), otherTyped.getWSATTransportSecurityMode(), true);
            this.computeDiff("ClusterwideRecoveryEnabled", this.bean.isClusterwideRecoveryEnabled(), otherTyped.isClusterwideRecoveryEnabled(), true);
            this.computeDiff("TLOGWriteWhenDeterminerExistsEnabled", this.bean.isTLOGWriteWhenDeterminerExistsEnabled(), otherTyped.isTLOGWriteWhenDeterminerExistsEnabled(), true);
            this.computeDiff("TightlyCoupledTransactionsEnabled", this.bean.isTightlyCoupledTransactionsEnabled(), otherTyped.isTightlyCoupledTransactionsEnabled(), true);
            this.computeDiff("TwoPhaseEnabled", this.bean.isTwoPhaseEnabled(), otherTyped.isTwoPhaseEnabled(), false);
            this.computeDiff("WSATIssuedTokenEnabled", this.bean.isWSATIssuedTokenEnabled(), otherTyped.isWSATIssuedTokenEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JTAMBeanImpl original = (JTAMBeanImpl)event.getSourceBean();
            JTAMBeanImpl proposed = (JTAMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AbandonTimeoutSeconds")) {
                  original.setAbandonTimeoutSeconds(proposed.getAbandonTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("BeforeCompletionIterationLimit")) {
                  original.setBeforeCompletionIterationLimit(proposed.getBeforeCompletionIterationLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("CheckpointIntervalSeconds")) {
                  original.setCheckpointIntervalSeconds(proposed.getCheckpointIntervalSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("CompletionTimeoutSeconds")) {
                  original.setCompletionTimeoutSeconds(proposed.getCompletionTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("CrossDomainRecoveryRetryInterval")) {
                  original.setCrossDomainRecoveryRetryInterval(proposed.getCrossDomainRecoveryRetryInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 41);
               } else if (prop.equals("CrossSiteRecoveryLeaseExpiration")) {
                  original.setCrossSiteRecoveryLeaseExpiration(proposed.getCrossSiteRecoveryLeaseExpiration());
                  original._conditionalUnset(update.isUnsetUpdate(), 43);
               } else if (prop.equals("CrossSiteRecoveryLeaseUpdate")) {
                  original.setCrossSiteRecoveryLeaseUpdate(proposed.getCrossSiteRecoveryLeaseUpdate());
                  original._conditionalUnset(update.isUnsetUpdate(), 44);
               } else if (prop.equals("CrossSiteRecoveryRetryInterval")) {
                  original.setCrossSiteRecoveryRetryInterval(proposed.getCrossSiteRecoveryRetryInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (!prop.equals("DeterminerCandidateResourceInfoList")) {
                  if (prop.equals("Determiners")) {
                     original.setDeterminers(proposed.getDeterminers());
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  } else if (prop.equals("ForgetHeuristics")) {
                     original.setForgetHeuristics(proposed.getForgetHeuristics());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("MaxResourceRequestsOnServer")) {
                     original.setMaxResourceRequestsOnServer(proposed.getMaxResourceRequestsOnServer());
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  } else if (prop.equals("MaxResourceUnavailableMillis")) {
                     original.setMaxResourceUnavailableMillis(proposed.getMaxResourceUnavailableMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("MaxRetrySecondsBeforeDeterminerFail")) {
                     original.setMaxRetrySecondsBeforeDeterminerFail(proposed.getMaxRetrySecondsBeforeDeterminerFail());
                     original._conditionalUnset(update.isUnsetUpdate(), 39);
                  } else if (prop.equals("MaxTransactions")) {
                     original.setMaxTransactions(proposed.getMaxTransactions());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("MaxTransactionsHealthIntervalMillis")) {
                     original.setMaxTransactionsHealthIntervalMillis(proposed.getMaxTransactionsHealthIntervalMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (prop.equals("MaxUniqueNameStatistics")) {
                     original.setMaxUniqueNameStatistics(proposed.getMaxUniqueNameStatistics());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("MaxXACallMillis")) {
                     original.setMaxXACallMillis(proposed.getMaxXACallMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (prop.equals("MigrationCheckpointIntervalSeconds")) {
                     original.setMigrationCheckpointIntervalSeconds(proposed.getMigrationCheckpointIntervalSeconds());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("ParallelXADispatchPolicy")) {
                     original.setParallelXADispatchPolicy(proposed.getParallelXADispatchPolicy());
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  } else if (prop.equals("ParallelXAEnabled")) {
                     original.setParallelXAEnabled(proposed.getParallelXAEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  } else if (prop.equals("PurgeResourceFromCheckpointIntervalSeconds")) {
                     original.setPurgeResourceFromCheckpointIntervalSeconds(proposed.getPurgeResourceFromCheckpointIntervalSeconds());
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  } else if (prop.equals("RecoverySiteName")) {
                     original.setRecoverySiteName(proposed.getRecoverySiteName());
                     original._conditionalUnset(update.isUnsetUpdate(), 40);
                  } else if (prop.equals("RecoveryThresholdMillis")) {
                     original.setRecoveryThresholdMillis(proposed.getRecoveryThresholdMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("SecurityInteropMode")) {
                     original.setSecurityInteropMode(proposed.getSecurityInteropMode());
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
                  } else if (prop.equals("SerializeEnlistmentsGCIntervalMillis")) {
                     original.setSerializeEnlistmentsGCIntervalMillis(proposed.getSerializeEnlistmentsGCIntervalMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  } else if (prop.equals("ShutdownGracePeriod")) {
                     original.setShutdownGracePeriod(proposed.getShutdownGracePeriod());
                     original._conditionalUnset(update.isUnsetUpdate(), 38);
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
                  } else if (prop.equals("TimeoutSeconds")) {
                     original.setTimeoutSeconds(proposed.getTimeoutSeconds());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("UnregisterResourceGracePeriod")) {
                     original.setUnregisterResourceGracePeriod(proposed.getUnregisterResourceGracePeriod());
                     original._conditionalUnset(update.isUnsetUpdate(), 28);
                  } else if (prop.equals("UseNonSecureAddressesForDomains")) {
                     original.setUseNonSecureAddressesForDomains(proposed.getUseNonSecureAddressesForDomains());
                     original._conditionalUnset(update.isUnsetUpdate(), 46);
                  } else if (prop.equals("UsePublicAddressesForRemoteDomains")) {
                     original.setUsePublicAddressesForRemoteDomains(proposed.getUsePublicAddressesForRemoteDomains());
                     original._conditionalUnset(update.isUnsetUpdate(), 45);
                  } else if (prop.equals("WSATTransportSecurityMode")) {
                     original.setWSATTransportSecurityMode(proposed.getWSATTransportSecurityMode());
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  } else if (prop.equals("ClusterwideRecoveryEnabled")) {
                     original.setClusterwideRecoveryEnabled(proposed.isClusterwideRecoveryEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  } else if (!prop.equals("DynamicallyCreated")) {
                     if (prop.equals("TLOGWriteWhenDeterminerExistsEnabled")) {
                        original.setTLOGWriteWhenDeterminerExistsEnabled(proposed.isTLOGWriteWhenDeterminerExistsEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 37);
                     } else if (prop.equals("TightlyCoupledTransactionsEnabled")) {
                        original.setTightlyCoupledTransactionsEnabled(proposed.isTightlyCoupledTransactionsEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 34);
                     } else if (prop.equals("TwoPhaseEnabled")) {
                        original.setTwoPhaseEnabled(proposed.isTwoPhaseEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 32);
                     } else if (prop.equals("WSATIssuedTokenEnabled")) {
                        original.setWSATIssuedTokenEnabled(proposed.isWSATIssuedTokenEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 31);
                     } else {
                        super.applyPropertyUpdate(event, update);
                     }
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
            JTAMBeanImpl copy = (JTAMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AbandonTimeoutSeconds")) && this.bean.isAbandonTimeoutSecondsSet()) {
               copy.setAbandonTimeoutSeconds(this.bean.getAbandonTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("BeforeCompletionIterationLimit")) && this.bean.isBeforeCompletionIterationLimitSet()) {
               copy.setBeforeCompletionIterationLimit(this.bean.getBeforeCompletionIterationLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("CheckpointIntervalSeconds")) && this.bean.isCheckpointIntervalSecondsSet()) {
               copy.setCheckpointIntervalSeconds(this.bean.getCheckpointIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("CompletionTimeoutSeconds")) && this.bean.isCompletionTimeoutSecondsSet()) {
               copy.setCompletionTimeoutSeconds(this.bean.getCompletionTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("CrossDomainRecoveryRetryInterval")) && this.bean.isCrossDomainRecoveryRetryIntervalSet()) {
               copy.setCrossDomainRecoveryRetryInterval(this.bean.getCrossDomainRecoveryRetryInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("CrossSiteRecoveryLeaseExpiration")) && this.bean.isCrossSiteRecoveryLeaseExpirationSet()) {
               copy.setCrossSiteRecoveryLeaseExpiration(this.bean.getCrossSiteRecoveryLeaseExpiration());
            }

            if ((excludeProps == null || !excludeProps.contains("CrossSiteRecoveryLeaseUpdate")) && this.bean.isCrossSiteRecoveryLeaseUpdateSet()) {
               copy.setCrossSiteRecoveryLeaseUpdate(this.bean.getCrossSiteRecoveryLeaseUpdate());
            }

            if ((excludeProps == null || !excludeProps.contains("CrossSiteRecoveryRetryInterval")) && this.bean.isCrossSiteRecoveryRetryIntervalSet()) {
               copy.setCrossSiteRecoveryRetryInterval(this.bean.getCrossSiteRecoveryRetryInterval());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Determiners")) && this.bean.isDeterminersSet()) {
               o = this.bean.getDeterminers();
               copy.setDeterminers(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ForgetHeuristics")) && this.bean.isForgetHeuristicsSet()) {
               copy.setForgetHeuristics(this.bean.getForgetHeuristics());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxResourceRequestsOnServer")) && this.bean.isMaxResourceRequestsOnServerSet()) {
               copy.setMaxResourceRequestsOnServer(this.bean.getMaxResourceRequestsOnServer());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxResourceUnavailableMillis")) && this.bean.isMaxResourceUnavailableMillisSet()) {
               copy.setMaxResourceUnavailableMillis(this.bean.getMaxResourceUnavailableMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRetrySecondsBeforeDeterminerFail")) && this.bean.isMaxRetrySecondsBeforeDeterminerFailSet()) {
               copy.setMaxRetrySecondsBeforeDeterminerFail(this.bean.getMaxRetrySecondsBeforeDeterminerFail());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxTransactions")) && this.bean.isMaxTransactionsSet()) {
               copy.setMaxTransactions(this.bean.getMaxTransactions());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxTransactionsHealthIntervalMillis")) && this.bean.isMaxTransactionsHealthIntervalMillisSet()) {
               copy.setMaxTransactionsHealthIntervalMillis(this.bean.getMaxTransactionsHealthIntervalMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxUniqueNameStatistics")) && this.bean.isMaxUniqueNameStatisticsSet()) {
               copy.setMaxUniqueNameStatistics(this.bean.getMaxUniqueNameStatistics());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxXACallMillis")) && this.bean.isMaxXACallMillisSet()) {
               copy.setMaxXACallMillis(this.bean.getMaxXACallMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("MigrationCheckpointIntervalSeconds")) && this.bean.isMigrationCheckpointIntervalSecondsSet()) {
               copy.setMigrationCheckpointIntervalSeconds(this.bean.getMigrationCheckpointIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("ParallelXADispatchPolicy")) && this.bean.isParallelXADispatchPolicySet()) {
               copy.setParallelXADispatchPolicy(this.bean.getParallelXADispatchPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("ParallelXAEnabled")) && this.bean.isParallelXAEnabledSet()) {
               copy.setParallelXAEnabled(this.bean.getParallelXAEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("PurgeResourceFromCheckpointIntervalSeconds")) && this.bean.isPurgeResourceFromCheckpointIntervalSecondsSet()) {
               copy.setPurgeResourceFromCheckpointIntervalSeconds(this.bean.getPurgeResourceFromCheckpointIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("RecoverySiteName")) && this.bean.isRecoverySiteNameSet()) {
               copy.setRecoverySiteName(this.bean.getRecoverySiteName());
            }

            if ((excludeProps == null || !excludeProps.contains("RecoveryThresholdMillis")) && this.bean.isRecoveryThresholdMillisSet()) {
               copy.setRecoveryThresholdMillis(this.bean.getRecoveryThresholdMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityInteropMode")) && this.bean.isSecurityInteropModeSet()) {
               copy.setSecurityInteropMode(this.bean.getSecurityInteropMode());
            }

            if ((excludeProps == null || !excludeProps.contains("SerializeEnlistmentsGCIntervalMillis")) && this.bean.isSerializeEnlistmentsGCIntervalMillisSet()) {
               copy.setSerializeEnlistmentsGCIntervalMillis(this.bean.getSerializeEnlistmentsGCIntervalMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("ShutdownGracePeriod")) && this.bean.isShutdownGracePeriodSet()) {
               copy.setShutdownGracePeriod(this.bean.getShutdownGracePeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("TimeoutSeconds")) && this.bean.isTimeoutSecondsSet()) {
               copy.setTimeoutSeconds(this.bean.getTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("UnregisterResourceGracePeriod")) && this.bean.isUnregisterResourceGracePeriodSet()) {
               copy.setUnregisterResourceGracePeriod(this.bean.getUnregisterResourceGracePeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("UseNonSecureAddressesForDomains")) && this.bean.isUseNonSecureAddressesForDomainsSet()) {
               o = this.bean.getUseNonSecureAddressesForDomains();
               copy.setUseNonSecureAddressesForDomains(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UsePublicAddressesForRemoteDomains")) && this.bean.isUsePublicAddressesForRemoteDomainsSet()) {
               o = this.bean.getUsePublicAddressesForRemoteDomains();
               copy.setUsePublicAddressesForRemoteDomains(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("WSATTransportSecurityMode")) && this.bean.isWSATTransportSecurityModeSet()) {
               copy.setWSATTransportSecurityMode(this.bean.getWSATTransportSecurityMode());
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterwideRecoveryEnabled")) && this.bean.isClusterwideRecoveryEnabledSet()) {
               copy.setClusterwideRecoveryEnabled(this.bean.isClusterwideRecoveryEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TLOGWriteWhenDeterminerExistsEnabled")) && this.bean.isTLOGWriteWhenDeterminerExistsEnabledSet()) {
               copy.setTLOGWriteWhenDeterminerExistsEnabled(this.bean.isTLOGWriteWhenDeterminerExistsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TightlyCoupledTransactionsEnabled")) && this.bean.isTightlyCoupledTransactionsEnabledSet()) {
               copy.setTightlyCoupledTransactionsEnabled(this.bean.isTightlyCoupledTransactionsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TwoPhaseEnabled")) && this.bean.isTwoPhaseEnabledSet()) {
               copy.setTwoPhaseEnabled(this.bean.isTwoPhaseEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("WSATIssuedTokenEnabled")) && this.bean.isWSATIssuedTokenEnabledSet()) {
               copy.setWSATIssuedTokenEnabled(this.bean.isWSATIssuedTokenEnabled());
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

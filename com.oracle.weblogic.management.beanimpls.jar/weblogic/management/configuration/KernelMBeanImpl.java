package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.Kernel;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class KernelMBeanImpl extends ConfigurationMBeanImpl implements KernelMBean, Serializable {
   private boolean _AddWorkManagerThreadsByCpuCount;
   private String _AdministrationProtocol;
   private boolean _AllowShrinkingPriorityRequestQueue;
   private int _CompleteCOMMessageTimeout;
   private int _CompleteHTTPMessageTimeout;
   private int _CompleteIIOPMessageTimeout;
   private int _CompleteMessageTimeout;
   private int _CompleteT3MessageTimeout;
   private int _CompleteWriteTimeout;
   private int _ConnectTimeout;
   private int _DGCIdlePeriodsUntilTimeout;
   private int _DefaultGIOPMinorVersion;
   private String _DefaultProtocol;
   private String _DefaultSecureProtocol;
   private boolean _DevPollDisabled;
   private boolean _DynamicallyCreated;
   private boolean _EagerThreadLocalCleanup;
   private ExecuteQueueMBean[] _ExecuteQueues;
   private boolean _GatheredWritesEnabled;
   private IIOPMBean _IIOP;
   private String _IIOPLocationForwardPolicy;
   private String _IIOPTxMechanism;
   private int _IdleConnectionTimeout;
   private int _IdleIIOPConnectionTimeout;
   private int _IdlePeriodsUntilTimeout;
   private boolean _InstrumentStackTraceEnabled;
   private boolean _IsolatePartitionThreadLocals;
   private int _JMSThreadPoolSize;
   private KernelDebugMBean _KernelDebug;
   private boolean _LoadStubUsingContextClassLoader;
   private LogMBean _Log;
   private boolean _LogRemoteExceptionsEnabled;
   private int _MTUSize;
   private int _MaxCOMMessageSize;
   private int _MaxHTTPMessageSize;
   private int _MaxIIOPMessageSize;
   private int _MaxMessageSize;
   private int _MaxOpenSockCount;
   private int _MaxT3MessageSize;
   private int _MessagingBridgeThreadPoolSize;
   private String _MuxerClass;
   private String _Name;
   private boolean _NativeIOEnabled;
   private boolean _OutboundEnabled;
   private boolean _OutboundPrivateKeyEnabled;
   private int _PeriodLength;
   private boolean _PrintStackTraceInProduction;
   private boolean _RefreshClientRuntimeDescriptor;
   private int _ResponseTimeout;
   private boolean _ReverseDNSAllowed;
   private int _RjvmIdleTimeout;
   private SSLMBean _SSL;
   private boolean _ScatteredReadsEnabled;
   private int _SelfTuningThreadPoolSizeMax;
   private int _SelfTuningThreadPoolSizeMin;
   private boolean _SocketBufferSizeAsChunkSize;
   private int _SocketReaderTimeoutMaxMillis;
   private int _SocketReaderTimeoutMinMillis;
   private int _SocketReaders;
   private boolean _StdoutDebugEnabled;
   private boolean _StdoutEnabled;
   private String _StdoutFormat;
   private boolean _StdoutLogStack;
   private int _StdoutSeverityLevel;
   private int _StuckThreadMaxTime;
   private int _StuckThreadTimerInterval;
   private int _SystemThreadPoolSize;
   private int _T3ClientAbbrevTableSize;
   private int _T3ServerAbbrevTableSize;
   private String[] _Tags;
   private int _ThreadPoolPercentSocketReaders;
   private int _ThreadPoolSize;
   private long _TimedOutRefIsolationTime;
   private boolean _TracingEnabled;
   private boolean _Use81StyleExecuteQueues;
   private boolean _UseConcurrentQueueForRequestManager;
   private boolean _UseDetailedThreadName;
   private boolean _UseEnhancedIncrementAdvisor;
   private boolean _UseEnhancedPriorityQueueForRequestManager;
   private boolean _UseIIOPLocateRequest;
   private Map _ValidProtocols;
   private transient Kernel _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private KernelMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(KernelMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(KernelMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public KernelMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(KernelMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      KernelMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._IIOP instanceof IIOPMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getIIOP() != null) {
            this._getReferenceManager().unregisterBean((IIOPMBeanImpl)oldDelegate.getIIOP());
         }

         if (delegate != null && delegate.getIIOP() != null) {
            this._getReferenceManager().registerBean((IIOPMBeanImpl)delegate.getIIOP(), false);
         }

         ((IIOPMBeanImpl)this._IIOP)._setDelegateBean((IIOPMBeanImpl)((IIOPMBeanImpl)(delegate == null ? null : delegate.getIIOP())));
      }

      if (this._KernelDebug instanceof KernelDebugMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getKernelDebug() != null) {
            this._getReferenceManager().unregisterBean((KernelDebugMBeanImpl)oldDelegate.getKernelDebug());
         }

         if (delegate != null && delegate.getKernelDebug() != null) {
            this._getReferenceManager().registerBean((KernelDebugMBeanImpl)delegate.getKernelDebug(), false);
         }

         ((KernelDebugMBeanImpl)this._KernelDebug)._setDelegateBean((KernelDebugMBeanImpl)((KernelDebugMBeanImpl)(delegate == null ? null : delegate.getKernelDebug())));
      }

      if (this._Log instanceof LogMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getLog() != null) {
            this._getReferenceManager().unregisterBean((LogMBeanImpl)oldDelegate.getLog());
         }

         if (delegate != null && delegate.getLog() != null) {
            this._getReferenceManager().registerBean((LogMBeanImpl)delegate.getLog(), false);
         }

         ((LogMBeanImpl)this._Log)._setDelegateBean((LogMBeanImpl)((LogMBeanImpl)(delegate == null ? null : delegate.getLog())));
      }

      if (this._SSL instanceof SSLMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getSSL() != null) {
            this._getReferenceManager().unregisterBean((SSLMBeanImpl)oldDelegate.getSSL());
         }

         if (delegate != null && delegate.getSSL() != null) {
            this._getReferenceManager().registerBean((SSLMBeanImpl)delegate.getSSL(), false);
         }

         ((SSLMBeanImpl)this._SSL)._setDelegateBean((SSLMBeanImpl)((SSLMBeanImpl)(delegate == null ? null : delegate.getSSL())));
      }

   }

   public KernelMBeanImpl() {
      try {
         this._customizer = new Kernel(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public KernelMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new Kernel(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public KernelMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new Kernel(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getName();
      }
   }

   public Map getValidProtocols() {
      return this._ValidProtocols;
   }

   public String getValidProtocolsAsString() {
      return StringHelper.objectToString(this.getValidProtocols());
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isValidProtocolsInherited() {
      return false;
   }

   public boolean isValidProtocolsSet() {
      return this._isSet(10);
   }

   public void setValidProtocolsAsString(String param0) {
      try {
         this.setValidProtocols(StringHelper.stringToMap(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setValidProtocols(Map param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      Map _oldVal = this._ValidProtocols;
      this._ValidProtocols = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isReverseDNSAllowed() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().isReverseDNSAllowed() : this._ReverseDNSAllowed;
   }

   public boolean isReverseDNSAllowedInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isReverseDNSAllowedSet() {
      return this._isSet(11);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setReverseDNSAllowed(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      boolean _oldVal = this._ReverseDNSAllowed;
      this._ReverseDNSAllowed = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultProtocol() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultProtocol(), this) : this._DefaultProtocol;
   }

   public boolean isDefaultProtocolInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isDefaultProtocolSet() {
      return this._isSet(12);
   }

   public void setDefaultProtocol(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"t3", "t3s", "http", "https", "iiop", "iiops"};
      param0 = LegalChecks.checkInEnum("DefaultProtocol", param0, _set);
      boolean wasSet = this._isSet(12);
      String _oldVal = this._DefaultProtocol;
      this._DefaultProtocol = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultSecureProtocol() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultSecureProtocol(), this) : this._DefaultSecureProtocol;
   }

   public boolean isDefaultSecureProtocolInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isDefaultSecureProtocolSet() {
      return this._isSet(13);
   }

   public void setDefaultSecureProtocol(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"t3s", "https", "iiops"};
      param0 = LegalChecks.checkInEnum("DefaultSecureProtocol", param0, _set);
      boolean wasSet = this._isSet(13);
      String _oldVal = this._DefaultSecureProtocol;
      this._DefaultSecureProtocol = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAdministrationProtocol() {
      if (!this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         return this._performMacroSubstitution(this._getDelegateBean().getAdministrationProtocol(), this);
      } else {
         if (!this._isSet(14)) {
            try {
               return ((DomainMBean)this.getParent()).getAdministrationProtocol();
            } catch (NullPointerException var2) {
            }
         }

         return this._AdministrationProtocol;
      }
   }

   public boolean isAdministrationProtocolInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isAdministrationProtocolSet() {
      return this._isSet(14);
   }

   public void setAdministrationProtocol(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"t3s", "https", "iiops", "t3", "http", "iiop"};
      param0 = LegalChecks.checkInEnum("AdministrationProtocol", param0, _set);
      boolean wasSet = this._isSet(14);
      String _oldVal = this._AdministrationProtocol;
      this._AdministrationProtocol = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public int getThreadPoolSize() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getThreadPoolSize() : this._ThreadPoolSize;
   }

   public boolean isThreadPoolSizeInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isThreadPoolSizeSet() {
      return this._isSet(15);
   }

   public void setThreadPoolSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ThreadPoolSize", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(15);
      int _oldVal = this._ThreadPoolSize;
      this._ThreadPoolSize = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public int getSystemThreadPoolSize() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getSystemThreadPoolSize() : this._SystemThreadPoolSize;
   }

   public boolean isSystemThreadPoolSizeInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isSystemThreadPoolSizeSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setSystemThreadPoolSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("SystemThreadPoolSize", (long)param0, 5L, 65534L);
      boolean wasSet = this._isSet(16);
      int _oldVal = this._SystemThreadPoolSize;
      this._SystemThreadPoolSize = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSelfTuningThreadPoolSizeMin(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("SelfTuningThreadPoolSizeMin", (long)param0, 1L, 65534L);
      boolean wasSet = this._isSet(17);
      int _oldVal = this._SelfTuningThreadPoolSizeMin;
      this._SelfTuningThreadPoolSizeMin = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSelfTuningThreadPoolSizeMin() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getSelfTuningThreadPoolSizeMin() : this._SelfTuningThreadPoolSizeMin;
   }

   public boolean isSelfTuningThreadPoolSizeMinInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isSelfTuningThreadPoolSizeMinSet() {
      return this._isSet(17);
   }

   public void setSelfTuningThreadPoolSizeMax(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("SelfTuningThreadPoolSizeMax", (long)param0, 1L, 65534L);
      boolean wasSet = this._isSet(18);
      int _oldVal = this._SelfTuningThreadPoolSizeMax;
      this._SelfTuningThreadPoolSizeMax = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSelfTuningThreadPoolSizeMax() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getSelfTuningThreadPoolSizeMax() : this._SelfTuningThreadPoolSizeMax;
   }

   public boolean isSelfTuningThreadPoolSizeMaxInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isSelfTuningThreadPoolSizeMaxSet() {
      return this._isSet(18);
   }

   public int getJMSThreadPoolSize() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getJMSThreadPoolSize() : this._JMSThreadPoolSize;
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

   public boolean isJMSThreadPoolSizeInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isJMSThreadPoolSizeSet() {
      return this._isSet(19);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setJMSThreadPoolSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("JMSThreadPoolSize", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(19);
      int _oldVal = this._JMSThreadPoolSize;
      this._JMSThreadPoolSize = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isNativeIOEnabled() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().isNativeIOEnabled() : this._NativeIOEnabled;
   }

   public boolean isNativeIOEnabledInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isNativeIOEnabledSet() {
      return this._isSet(20);
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setNativeIOEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      boolean _oldVal = this._NativeIOEnabled;
      this._NativeIOEnabled = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(9);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(9)) {
            source._postSetFirePropertyChange(9, wasSet, _oldVal, param0);
         }
      }

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

   public void setDevPollDisabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      boolean _oldVal = this._DevPollDisabled;
      this._DevPollDisabled = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDevPollDisabled() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().isDevPollDisabled() : this._DevPollDisabled;
   }

   public boolean isDevPollDisabledInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isDevPollDisabledSet() {
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

   public String getMuxerClass() {
      if (!this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22)) {
         return this._performMacroSubstitution(this._getDelegateBean().getMuxerClass(), this);
      } else {
         if (!this._isSet(22)) {
            try {
               return ((DomainMBean)this.getParent()).isExalogicOptimizationsEnabled() ? "weblogic.socket.NIOSharedWorkSocketMuxer" : "weblogic.socket.NIOSocketMuxer";
            } catch (NullPointerException var2) {
            }
         }

         return this._MuxerClass;
      }
   }

   public boolean isMuxerClassInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isMuxerClassSet() {
      return this._isSet(22);
   }

   public void setMuxerClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      String _oldVal = this._MuxerClass;
      this._MuxerClass = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSocketReaders() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getSocketReaders() : this._SocketReaders;
   }

   public boolean isSocketReadersInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isSocketReadersSet() {
      return this._isSet(23);
   }

   public void setSocketReaders(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("SocketReaders", (long)param0, -1L, 65534L);
      boolean wasSet = this._isSet(23);
      int _oldVal = this._SocketReaders;
      this._SocketReaders = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public int getThreadPoolPercentSocketReaders() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().getThreadPoolPercentSocketReaders() : this._ThreadPoolPercentSocketReaders;
   }

   public boolean isThreadPoolPercentSocketReadersInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isThreadPoolPercentSocketReadersSet() {
      return this._isSet(24);
   }

   public void setThreadPoolPercentSocketReaders(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ThreadPoolPercentSocketReaders", (long)param0, 1L, 99L);
      boolean wasSet = this._isSet(24);
      int _oldVal = this._ThreadPoolPercentSocketReaders;
      this._ThreadPoolPercentSocketReaders = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSocketReaderTimeoutMinMillis() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getSocketReaderTimeoutMinMillis() : this._SocketReaderTimeoutMinMillis;
   }

   public boolean isSocketReaderTimeoutMinMillisInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isSocketReaderTimeoutMinMillisSet() {
      return this._isSet(25);
   }

   public void setSocketReaderTimeoutMinMillis(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("SocketReaderTimeoutMinMillis", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(25);
      int _oldVal = this._SocketReaderTimeoutMinMillis;
      this._SocketReaderTimeoutMinMillis = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSocketReaderTimeoutMaxMillis() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getSocketReaderTimeoutMaxMillis() : this._SocketReaderTimeoutMaxMillis;
   }

   public boolean isSocketReaderTimeoutMaxMillisInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isSocketReaderTimeoutMaxMillisSet() {
      return this._isSet(26);
   }

   public void setSocketReaderTimeoutMaxMillis(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("SocketReaderTimeoutMaxMillis", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(26);
      int _oldVal = this._SocketReaderTimeoutMaxMillis;
      this._SocketReaderTimeoutMaxMillis = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isOutboundEnabled() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().isOutboundEnabled() : this._OutboundEnabled;
   }

   public boolean isOutboundEnabledInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isOutboundEnabledSet() {
      return this._isSet(27);
   }

   public void setOutboundEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      boolean _oldVal = this._OutboundEnabled;
      this._OutboundEnabled = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isOutboundPrivateKeyEnabled() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().isOutboundPrivateKeyEnabled() : this._OutboundPrivateKeyEnabled;
   }

   public boolean isOutboundPrivateKeyEnabledInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isOutboundPrivateKeyEnabledSet() {
      return this._isSet(28);
   }

   public void setOutboundPrivateKeyEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      boolean _oldVal = this._OutboundPrivateKeyEnabled;
      this._OutboundPrivateKeyEnabled = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxMessageSize() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().getMaxMessageSize() : this._MaxMessageSize;
   }

   public boolean isMaxMessageSizeInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isMaxMessageSizeSet() {
      return this._isSet(29);
   }

   public void setMaxMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxMessageSize", (long)param0, 4096L, 2000000000L);
      boolean wasSet = this._isSet(29);
      int _oldVal = this._MaxMessageSize;
      this._MaxMessageSize = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxT3MessageSize() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getMaxT3MessageSize() : this._MaxT3MessageSize;
   }

   public boolean isMaxT3MessageSizeInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isMaxT3MessageSizeSet() {
      return this._isSet(30);
   }

   public void setMaxT3MessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      KernelValidator.validateMaxT3MessageSize(param0);
      boolean wasSet = this._isSet(30);
      int _oldVal = this._MaxT3MessageSize;
      this._MaxT3MessageSize = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public void setSocketBufferSizeAsChunkSize(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      boolean _oldVal = this._SocketBufferSizeAsChunkSize;
      this._SocketBufferSizeAsChunkSize = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isSocketBufferSizeAsChunkSize() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().isSocketBufferSizeAsChunkSize() : this._SocketBufferSizeAsChunkSize;
   }

   public boolean isSocketBufferSizeAsChunkSizeInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isSocketBufferSizeAsChunkSizeSet() {
      return this._isSet(31);
   }

   public int getMaxHTTPMessageSize() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().getMaxHTTPMessageSize() : this._MaxHTTPMessageSize;
   }

   public boolean isMaxHTTPMessageSizeInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isMaxHTTPMessageSizeSet() {
      return this._isSet(32);
   }

   public void setMaxHTTPMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      KernelValidator.validateMaxHTTPMessageSize(param0);
      boolean wasSet = this._isSet(32);
      int _oldVal = this._MaxHTTPMessageSize;
      this._MaxHTTPMessageSize = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxCOMMessageSize() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getMaxCOMMessageSize() : this._MaxCOMMessageSize;
   }

   public boolean isMaxCOMMessageSizeInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isMaxCOMMessageSizeSet() {
      return this._isSet(33);
   }

   public void setMaxCOMMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      KernelValidator.validateMaxCommMessageSize(param0);
      boolean wasSet = this._isSet(33);
      int _oldVal = this._MaxCOMMessageSize;
      this._MaxCOMMessageSize = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxIIOPMessageSize() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().getMaxIIOPMessageSize() : this._MaxIIOPMessageSize;
   }

   public boolean isMaxIIOPMessageSizeInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isMaxIIOPMessageSizeSet() {
      return this._isSet(34);
   }

   public void setMaxIIOPMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      KernelValidator.validateMaxIIOPMessageSize(param0);
      boolean wasSet = this._isSet(34);
      int _oldVal = this._MaxIIOPMessageSize;
      this._MaxIIOPMessageSize = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public int getDefaultGIOPMinorVersion() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().getDefaultGIOPMinorVersion() : this._DefaultGIOPMinorVersion;
   }

   public boolean isDefaultGIOPMinorVersionInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isDefaultGIOPMinorVersionSet() {
      return this._isSet(35);
   }

   public void setDefaultGIOPMinorVersion(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DefaultGIOPMinorVersion", (long)param0, 0L, 2L);
      boolean wasSet = this._isSet(35);
      int _oldVal = this._DefaultGIOPMinorVersion;
      this._DefaultGIOPMinorVersion = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getUseIIOPLocateRequest() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().getUseIIOPLocateRequest() : this._UseIIOPLocateRequest;
   }

   public boolean isUseIIOPLocateRequestInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isUseIIOPLocateRequestSet() {
      return this._isSet(36);
   }

   public void setUseIIOPLocateRequest(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(36);
      boolean _oldVal = this._UseIIOPLocateRequest;
      this._UseIIOPLocateRequest = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public String getIIOPTxMechanism() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._performMacroSubstitution(this._getDelegateBean().getIIOPTxMechanism(), this) : this._IIOPTxMechanism;
   }

   public boolean isIIOPTxMechanismInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isIIOPTxMechanismSet() {
      return this._isSet(37);
   }

   public void setIIOPTxMechanism(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"ots", "jta"};
      param0 = LegalChecks.checkInEnum("IIOPTxMechanism", param0, _set);
      boolean wasSet = this._isSet(37);
      String _oldVal = this._IIOPTxMechanism;
      this._IIOPTxMechanism = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public String getIIOPLocationForwardPolicy() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._performMacroSubstitution(this._getDelegateBean().getIIOPLocationForwardPolicy(), this) : this._IIOPLocationForwardPolicy;
   }

   public boolean isIIOPLocationForwardPolicyInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isIIOPLocationForwardPolicySet() {
      return this._isSet(38);
   }

   public void setIIOPLocationForwardPolicy(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"off", "failover", "round-robin", "random"};
      param0 = LegalChecks.checkInEnum("IIOPLocationForwardPolicy", param0, _set);
      boolean wasSet = this._isSet(38);
      String _oldVal = this._IIOPLocationForwardPolicy;
      this._IIOPLocationForwardPolicy = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public int getConnectTimeout() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().getConnectTimeout() : this._ConnectTimeout;
   }

   public boolean isConnectTimeoutInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isConnectTimeoutSet() {
      return this._isSet(39);
   }

   public void setConnectTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ConnectTimeout", (long)param0, 0L, 240L);
      boolean wasSet = this._isSet(39);
      int _oldVal = this._ConnectTimeout;
      this._ConnectTimeout = param0;
      this._postSet(39, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(39)) {
            source._postSetFirePropertyChange(39, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteMessageTimeout() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40) ? this._getDelegateBean().getCompleteMessageTimeout() : this._CompleteMessageTimeout;
   }

   public boolean isCompleteMessageTimeoutInherited() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40);
   }

   public boolean isCompleteMessageTimeoutSet() {
      return this._isSet(40);
   }

   public void setCompleteMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteMessageTimeout", (long)param0, 0L, 480L);
      boolean wasSet = this._isSet(40);
      int _oldVal = this._CompleteMessageTimeout;
      this._CompleteMessageTimeout = param0;
      this._postSet(40, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(40)) {
            source._postSetFirePropertyChange(40, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteT3MessageTimeout() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41) ? this._getDelegateBean().getCompleteT3MessageTimeout() : this._CompleteT3MessageTimeout;
   }

   public boolean isCompleteT3MessageTimeoutInherited() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41);
   }

   public boolean isCompleteT3MessageTimeoutSet() {
      return this._isSet(41);
   }

   public void setCompleteT3MessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteT3MessageTimeout", (long)param0, -1L, 480L);
      boolean wasSet = this._isSet(41);
      int _oldVal = this._CompleteT3MessageTimeout;
      this._CompleteT3MessageTimeout = param0;
      this._postSet(41, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(41)) {
            source._postSetFirePropertyChange(41, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteHTTPMessageTimeout() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42) ? this._getDelegateBean().getCompleteHTTPMessageTimeout() : this._CompleteHTTPMessageTimeout;
   }

   public boolean isCompleteHTTPMessageTimeoutInherited() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42);
   }

   public boolean isCompleteHTTPMessageTimeoutSet() {
      return this._isSet(42);
   }

   public void setCompleteHTTPMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteHTTPMessageTimeout", (long)param0, -1L, 480L);
      boolean wasSet = this._isSet(42);
      int _oldVal = this._CompleteHTTPMessageTimeout;
      this._CompleteHTTPMessageTimeout = param0;
      this._postSet(42, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(42)) {
            source._postSetFirePropertyChange(42, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteCOMMessageTimeout() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43) ? this._getDelegateBean().getCompleteCOMMessageTimeout() : this._CompleteCOMMessageTimeout;
   }

   public boolean isCompleteCOMMessageTimeoutInherited() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43);
   }

   public boolean isCompleteCOMMessageTimeoutSet() {
      return this._isSet(43);
   }

   public void setCompleteCOMMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteCOMMessageTimeout", (long)param0, -1L, 480L);
      boolean wasSet = this._isSet(43);
      int _oldVal = this._CompleteCOMMessageTimeout;
      this._CompleteCOMMessageTimeout = param0;
      this._postSet(43, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(43)) {
            source._postSetFirePropertyChange(43, wasSet, _oldVal, param0);
         }
      }

   }

   public int getIdleConnectionTimeout() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44) ? this._getDelegateBean().getIdleConnectionTimeout() : this._IdleConnectionTimeout;
   }

   public boolean isIdleConnectionTimeoutInherited() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44);
   }

   public boolean isIdleConnectionTimeoutSet() {
      return this._isSet(44);
   }

   public void setIdleConnectionTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("IdleConnectionTimeout", param0, 0);
      boolean wasSet = this._isSet(44);
      int _oldVal = this._IdleConnectionTimeout;
      this._IdleConnectionTimeout = param0;
      this._postSet(44, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(44)) {
            source._postSetFirePropertyChange(44, wasSet, _oldVal, param0);
         }
      }

   }

   public int getIdleIIOPConnectionTimeout() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45) ? this._getDelegateBean().getIdleIIOPConnectionTimeout() : this._IdleIIOPConnectionTimeout;
   }

   public boolean isIdleIIOPConnectionTimeoutInherited() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45);
   }

   public boolean isIdleIIOPConnectionTimeoutSet() {
      return this._isSet(45);
   }

   public void setIdleIIOPConnectionTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("IdleIIOPConnectionTimeout", param0, -1);
      boolean wasSet = this._isSet(45);
      int _oldVal = this._IdleIIOPConnectionTimeout;
      this._IdleIIOPConnectionTimeout = param0;
      this._postSet(45, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(45)) {
            source._postSetFirePropertyChange(45, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteIIOPMessageTimeout() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46) ? this._getDelegateBean().getCompleteIIOPMessageTimeout() : this._CompleteIIOPMessageTimeout;
   }

   public boolean isCompleteIIOPMessageTimeoutInherited() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46);
   }

   public boolean isCompleteIIOPMessageTimeoutSet() {
      return this._isSet(46);
   }

   public void setCompleteIIOPMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteIIOPMessageTimeout", (long)param0, -1L, 480L);
      boolean wasSet = this._isSet(46);
      int _oldVal = this._CompleteIIOPMessageTimeout;
      this._CompleteIIOPMessageTimeout = param0;
      this._postSet(46, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(46)) {
            source._postSetFirePropertyChange(46, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPeriodLength() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47) ? this._getDelegateBean().getPeriodLength() : this._PeriodLength;
   }

   public boolean isPeriodLengthInherited() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47);
   }

   public boolean isPeriodLengthSet() {
      return this._isSet(47);
   }

   public void setPeriodLength(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("PeriodLength", param0, 0);
      boolean wasSet = this._isSet(47);
      int _oldVal = this._PeriodLength;
      this._PeriodLength = param0;
      this._postSet(47, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(47)) {
            source._postSetFirePropertyChange(47, wasSet, _oldVal, param0);
         }
      }

   }

   public int getIdlePeriodsUntilTimeout() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48) ? this._getDelegateBean().getIdlePeriodsUntilTimeout() : this._IdlePeriodsUntilTimeout;
   }

   public boolean isIdlePeriodsUntilTimeoutInherited() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48);
   }

   public boolean isIdlePeriodsUntilTimeoutSet() {
      return this._isSet(48);
   }

   public void setIdlePeriodsUntilTimeout(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("IdlePeriodsUntilTimeout", (long)param0, 4L, 65534L);
      boolean wasSet = this._isSet(48);
      int _oldVal = this._IdlePeriodsUntilTimeout;
      this._IdlePeriodsUntilTimeout = param0;
      this._postSet(48, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(48)) {
            source._postSetFirePropertyChange(48, wasSet, _oldVal, param0);
         }
      }

   }

   public int getRjvmIdleTimeout() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49) ? this._getDelegateBean().getRjvmIdleTimeout() : this._RjvmIdleTimeout;
   }

   public boolean isRjvmIdleTimeoutInherited() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49);
   }

   public boolean isRjvmIdleTimeoutSet() {
      return this._isSet(49);
   }

   public void setRjvmIdleTimeout(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("RjvmIdleTimeout", (long)param0, 0L, 900000L);
      boolean wasSet = this._isSet(49);
      int _oldVal = this._RjvmIdleTimeout;
      this._RjvmIdleTimeout = param0;
      this._postSet(49, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(49)) {
            source._postSetFirePropertyChange(49, wasSet, _oldVal, param0);
         }
      }

   }

   public int getResponseTimeout() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50) ? this._getDelegateBean().getResponseTimeout() : this._ResponseTimeout;
   }

   public boolean isResponseTimeoutInherited() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50);
   }

   public boolean isResponseTimeoutSet() {
      return this._isSet(50);
   }

   public void setResponseTimeout(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ResponseTimeout", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(50);
      int _oldVal = this._ResponseTimeout;
      this._ResponseTimeout = param0;
      this._postSet(50, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(50)) {
            source._postSetFirePropertyChange(50, wasSet, _oldVal, param0);
         }
      }

   }

   public KernelDebugMBean getKernelDebug() {
      return this._customizer.getKernelDebug();
   }

   public boolean isKernelDebugInherited() {
      return false;
   }

   public boolean isKernelDebugSet() {
      return this._isSet(51);
   }

   public void setKernelDebug(KernelDebugMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._KernelDebug = param0;
   }

   public int getDGCIdlePeriodsUntilTimeout() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52) ? this._getDelegateBean().getDGCIdlePeriodsUntilTimeout() : this._DGCIdlePeriodsUntilTimeout;
   }

   public boolean isDGCIdlePeriodsUntilTimeoutInherited() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52);
   }

   public boolean isDGCIdlePeriodsUntilTimeoutSet() {
      return this._isSet(52);
   }

   public void setDGCIdlePeriodsUntilTimeout(int param0) throws ConfigurationException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(52);
      int _oldVal = this._DGCIdlePeriodsUntilTimeout;
      this._DGCIdlePeriodsUntilTimeout = param0;
      this._postSet(52, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(52)) {
            source._postSetFirePropertyChange(52, wasSet, _oldVal, param0);
         }
      }

   }

   public SSLMBean getSSL() {
      return this._SSL;
   }

   public boolean isSSLInherited() {
      return false;
   }

   public boolean isSSLSet() {
      return this._isSet(53) || this._isAnythingSet((AbstractDescriptorBean)this.getSSL());
   }

   public void setSSL(SSLMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 53)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(53);
      SSLMBean _oldVal = this._SSL;
      this._SSL = param0;
      this._postSet(53, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(53)) {
            source._postSetFirePropertyChange(53, wasSet, _oldVal, param0);
         }
      }

   }

   public IIOPMBean getIIOP() {
      return this._IIOP;
   }

   public boolean isIIOPInherited() {
      return false;
   }

   public boolean isIIOPSet() {
      return this._isSet(54) || this._isAnythingSet((AbstractDescriptorBean)this.getIIOP());
   }

   public void setIIOP(IIOPMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 54)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(54);
      IIOPMBean _oldVal = this._IIOP;
      this._IIOP = param0;
      this._postSet(54, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(54)) {
            source._postSetFirePropertyChange(54, wasSet, _oldVal, param0);
         }
      }

   }

   public LogMBean getLog() {
      return this._Log;
   }

   public boolean isLogInherited() {
      return false;
   }

   public boolean isLogSet() {
      return this._isSet(55) || this._isAnythingSet((AbstractDescriptorBean)this.getLog());
   }

   public void setLog(LogMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 55)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(55);
      LogMBean _oldVal = this._Log;
      this._Log = param0;
      this._postSet(55, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(55)) {
            source._postSetFirePropertyChange(55, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isStdoutEnabled() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56) ? this._getDelegateBean().isStdoutEnabled() : this._customizer.isStdoutEnabled();
   }

   public boolean isStdoutEnabledInherited() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56);
   }

   public boolean isStdoutEnabledSet() {
      return this._isSet(56);
   }

   public void setStdoutEnabled(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(56);
      boolean _oldVal = this.isStdoutEnabled();
      this._customizer.setStdoutEnabled(param0);
      this._postSet(56, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(56)) {
            source._postSetFirePropertyChange(56, wasSet, _oldVal, param0);
         }
      }

   }

   public int getStdoutSeverityLevel() {
      return !this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57) ? this._getDelegateBean().getStdoutSeverityLevel() : this._customizer.getStdoutSeverityLevel();
   }

   public boolean isStdoutSeverityLevelInherited() {
      return !this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57);
   }

   public boolean isStdoutSeverityLevelSet() {
      return this._isSet(57);
   }

   public void setStdoutSeverityLevel(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      int[] _set = new int[]{256, 128, 64, 16, 8, 32, 4, 2, 1, 0};
      param0 = LegalChecks.checkInEnum("StdoutSeverityLevel", param0, _set);
      boolean wasSet = this._isSet(57);
      int _oldVal = this.getStdoutSeverityLevel();
      this._customizer.setStdoutSeverityLevel(param0);
      this._postSet(57, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(57)) {
            source._postSetFirePropertyChange(57, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isStdoutDebugEnabled() {
      return !this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58) ? this._getDelegateBean().isStdoutDebugEnabled() : this._customizer.isStdoutDebugEnabled();
   }

   public boolean isStdoutDebugEnabledInherited() {
      return !this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58);
   }

   public boolean isStdoutDebugEnabledSet() {
      return this._isSet(58);
   }

   public void setStdoutDebugEnabled(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(58);
      boolean _oldVal = this.isStdoutDebugEnabled();
      this._customizer.setStdoutDebugEnabled(param0);
      this._postSet(58, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(58)) {
            source._postSetFirePropertyChange(58, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLogRemoteExceptionsEnabled() {
      if (!this._isSet(59) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(59)) {
         return this._getDelegateBean().isLogRemoteExceptionsEnabled();
      } else if (!this._isSet(59)) {
         return this._isSecureModeEnabled();
      } else {
         return this._LogRemoteExceptionsEnabled;
      }
   }

   public boolean isLogRemoteExceptionsEnabledInherited() {
      return !this._isSet(59) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(59);
   }

   public boolean isLogRemoteExceptionsEnabledSet() {
      return this._isSet(59);
   }

   public void setLogRemoteExceptionsEnabled(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(59);
      boolean _oldVal = this._LogRemoteExceptionsEnabled;
      this._LogRemoteExceptionsEnabled = param0;
      this._postSet(59, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(59)) {
            source._postSetFirePropertyChange(59, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isInstrumentStackTraceEnabled() {
      if (!this._isSet(60) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(60)) {
         return this._getDelegateBean().isInstrumentStackTraceEnabled();
      } else if (!this._isSet(60)) {
         return !this._isSecureModeEnabled();
      } else {
         return this._InstrumentStackTraceEnabled;
      }
   }

   public boolean isInstrumentStackTraceEnabledInherited() {
      return !this._isSet(60) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(60);
   }

   public boolean isInstrumentStackTraceEnabledSet() {
      return this._isSet(60);
   }

   public void setInstrumentStackTraceEnabled(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(60);
      boolean _oldVal = this._InstrumentStackTraceEnabled;
      this._InstrumentStackTraceEnabled = param0;
      this._postSet(60, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(60)) {
            source._postSetFirePropertyChange(60, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getPrintStackTraceInProduction() {
      return !this._isSet(61) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(61) ? this._getDelegateBean().getPrintStackTraceInProduction() : this._PrintStackTraceInProduction;
   }

   public boolean isPrintStackTraceInProductionInherited() {
      return !this._isSet(61) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(61);
   }

   public boolean isPrintStackTraceInProductionSet() {
      return this._isSet(61);
   }

   public void setPrintStackTraceInProduction(boolean param0) throws DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(61);
      boolean _oldVal = this._PrintStackTraceInProduction;
      this._PrintStackTraceInProduction = param0;
      this._postSet(61, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(61)) {
            source._postSetFirePropertyChange(61, wasSet, _oldVal, param0);
         }
      }

   }

   public void addExecuteQueue(ExecuteQueueMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 62)) {
         ExecuteQueueMBean[] _new;
         if (this._isSet(62)) {
            _new = (ExecuteQueueMBean[])((ExecuteQueueMBean[])this._getHelper()._extendArray(this.getExecuteQueues(), ExecuteQueueMBean.class, param0));
         } else {
            _new = new ExecuteQueueMBean[]{param0};
         }

         try {
            this.setExecuteQueues(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ExecuteQueueMBean[] getExecuteQueues() {
      return !this._isSet(62) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(62) ? this._getDelegateBean().getExecuteQueues() : this._ExecuteQueues;
   }

   public boolean isExecuteQueuesInherited() {
      return !this._isSet(62) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(62);
   }

   public boolean isExecuteQueuesSet() {
      return this._isSet(62);
   }

   public void removeExecuteQueue(ExecuteQueueMBean param0) {
      this.destroyExecuteQueue(param0);
   }

   public void setExecuteQueues(ExecuteQueueMBean[] param0) throws InvalidAttributeValueException {
      ExecuteQueueMBean[] param0 = param0 == null ? new ExecuteQueueMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 62)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(62);
      ExecuteQueueMBean[] _oldVal = this._ExecuteQueues;
      this._ExecuteQueues = (ExecuteQueueMBean[])param0;
      this._postSet(62, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(62)) {
            source._postSetFirePropertyChange(62, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxOpenSockCount() {
      return !this._isSet(63) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(63) ? this._getDelegateBean().getMaxOpenSockCount() : this._MaxOpenSockCount;
   }

   public boolean isMaxOpenSockCountInherited() {
      return !this._isSet(63) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(63);
   }

   public boolean isMaxOpenSockCountSet() {
      return this._isSet(63);
   }

   public void setMaxOpenSockCount(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxOpenSockCount", (long)param0, -1L, 2147483647L);
      boolean wasSet = this._isSet(63);
      int _oldVal = this._MaxOpenSockCount;
      this._MaxOpenSockCount = param0;
      this._postSet(63, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(63)) {
            source._postSetFirePropertyChange(63, wasSet, _oldVal, param0);
         }
      }

   }

   public String getStdoutFormat() {
      return !this._isSet(64) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(64) ? this._performMacroSubstitution(this._getDelegateBean().getStdoutFormat(), this) : this._customizer.getStdoutFormat();
   }

   public boolean isStdoutFormatInherited() {
      return !this._isSet(64) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(64);
   }

   public boolean isStdoutFormatSet() {
      return this._isSet(64);
   }

   public void setStdoutFormat(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"standard", "noid"};
      param0 = LegalChecks.checkInEnum("StdoutFormat", param0, _set);
      boolean wasSet = this._isSet(64);
      String _oldVal = this.getStdoutFormat();
      this._customizer.setStdoutFormat(param0);
      this._postSet(64, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var5.next();
         if (source != null && !source._isSet(64)) {
            source._postSetFirePropertyChange(64, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isStdoutLogStack() {
      return !this._isSet(65) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(65) ? this._getDelegateBean().isStdoutLogStack() : this._customizer.isStdoutLogStack();
   }

   public boolean isStdoutLogStackInherited() {
      return !this._isSet(65) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(65);
   }

   public boolean isStdoutLogStackSet() {
      return this._isSet(65);
   }

   public void setStdoutLogStack(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(65);
      boolean _oldVal = this.isStdoutLogStack();
      this._customizer.setStdoutLogStack(param0);
      this._postSet(65, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(65)) {
            source._postSetFirePropertyChange(65, wasSet, _oldVal, param0);
         }
      }

   }

   public int getStuckThreadMaxTime() {
      return !this._isSet(66) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(66) ? this._getDelegateBean().getStuckThreadMaxTime() : this._StuckThreadMaxTime;
   }

   public boolean isStuckThreadMaxTimeInherited() {
      return !this._isSet(66) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(66);
   }

   public boolean isStuckThreadMaxTimeSet() {
      return this._isSet(66);
   }

   public void setStuckThreadMaxTime(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("StuckThreadMaxTime", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(66);
      int _oldVal = this._StuckThreadMaxTime;
      this._StuckThreadMaxTime = param0;
      this._postSet(66, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(66)) {
            source._postSetFirePropertyChange(66, wasSet, _oldVal, param0);
         }
      }

   }

   public int getStuckThreadTimerInterval() {
      return !this._isSet(67) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(67) ? this._getDelegateBean().getStuckThreadTimerInterval() : this._StuckThreadTimerInterval;
   }

   public boolean isStuckThreadTimerIntervalInherited() {
      return !this._isSet(67) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(67);
   }

   public boolean isStuckThreadTimerIntervalSet() {
      return this._isSet(67);
   }

   public void setStuckThreadTimerInterval(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("StuckThreadTimerInterval", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(67);
      int _oldVal = this._StuckThreadTimerInterval;
      this._StuckThreadTimerInterval = param0;
      this._postSet(67, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(67)) {
            source._postSetFirePropertyChange(67, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getTracingEnabled() {
      return !this._isSet(68) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(68) ? this._getDelegateBean().getTracingEnabled() : this._TracingEnabled;
   }

   public boolean isTracingEnabledInherited() {
      return !this._isSet(68) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(68);
   }

   public boolean isTracingEnabledSet() {
      return this._isSet(68);
   }

   public void setTracingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(68);
      boolean _oldVal = this._TracingEnabled;
      this._TracingEnabled = param0;
      this._postSet(68, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(68)) {
            source._postSetFirePropertyChange(68, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMessagingBridgeThreadPoolSize() {
      return !this._isSet(69) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(69) ? this._getDelegateBean().getMessagingBridgeThreadPoolSize() : this._MessagingBridgeThreadPoolSize;
   }

   public boolean isMessagingBridgeThreadPoolSizeInherited() {
      return !this._isSet(69) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(69);
   }

   public boolean isMessagingBridgeThreadPoolSizeSet() {
      return this._isSet(69);
   }

   public void setMessagingBridgeThreadPoolSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MessagingBridgeThreadPoolSize", (long)param0, -1L, 65534L);
      boolean wasSet = this._isSet(69);
      int _oldVal = this._MessagingBridgeThreadPoolSize;
      this._MessagingBridgeThreadPoolSize = param0;
      this._postSet(69, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(69)) {
            source._postSetFirePropertyChange(69, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMTUSize() {
      return !this._isSet(70) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(70) ? this._getDelegateBean().getMTUSize() : this._MTUSize;
   }

   public boolean isMTUSizeInherited() {
      return !this._isSet(70) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(70);
   }

   public boolean isMTUSizeSet() {
      return this._isSet(70);
   }

   public void setMTUSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(70);
      int _oldVal = this._MTUSize;
      this._MTUSize = param0;
      this._postSet(70, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(70)) {
            source._postSetFirePropertyChange(70, wasSet, _oldVal, param0);
         }
      }

   }

   public void setLoadStubUsingContextClassLoader(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(71);
      boolean _oldVal = this._LoadStubUsingContextClassLoader;
      this._LoadStubUsingContextClassLoader = param0;
      this._postSet(71, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(71)) {
            source._postSetFirePropertyChange(71, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getLoadStubUsingContextClassLoader() {
      return !this._isSet(71) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(71) ? this._getDelegateBean().getLoadStubUsingContextClassLoader() : this._LoadStubUsingContextClassLoader;
   }

   public boolean isLoadStubUsingContextClassLoaderInherited() {
      return !this._isSet(71) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(71);
   }

   public boolean isLoadStubUsingContextClassLoaderSet() {
      return this._isSet(71);
   }

   public void setRefreshClientRuntimeDescriptor(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(72);
      boolean _oldVal = this._RefreshClientRuntimeDescriptor;
      this._RefreshClientRuntimeDescriptor = param0;
      this._postSet(72, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(72)) {
            source._postSetFirePropertyChange(72, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getRefreshClientRuntimeDescriptor() {
      return !this._isSet(72) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(72) ? this._getDelegateBean().getRefreshClientRuntimeDescriptor() : this._RefreshClientRuntimeDescriptor;
   }

   public boolean isRefreshClientRuntimeDescriptorInherited() {
      return !this._isSet(72) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(72);
   }

   public boolean isRefreshClientRuntimeDescriptorSet() {
      return this._isSet(72);
   }

   public void setTimedOutRefIsolationTime(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(73);
      long _oldVal = this._TimedOutRefIsolationTime;
      this._TimedOutRefIsolationTime = param0;
      this._postSet(73, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var6.next();
         if (source != null && !source._isSet(73)) {
            source._postSetFirePropertyChange(73, wasSet, _oldVal, param0);
         }
      }

   }

   public long getTimedOutRefIsolationTime() {
      return !this._isSet(73) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(73) ? this._getDelegateBean().getTimedOutRefIsolationTime() : this._TimedOutRefIsolationTime;
   }

   public boolean isTimedOutRefIsolationTimeInherited() {
      return !this._isSet(73) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(73);
   }

   public boolean isTimedOutRefIsolationTimeSet() {
      return this._isSet(73);
   }

   public void setUse81StyleExecuteQueues(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(74);
      boolean _oldVal = this._Use81StyleExecuteQueues;
      this._Use81StyleExecuteQueues = param0;
      this._postSet(74, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(74)) {
            source._postSetFirePropertyChange(74, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getUse81StyleExecuteQueues() {
      return !this._isSet(74) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(74) ? this._getDelegateBean().getUse81StyleExecuteQueues() : this._Use81StyleExecuteQueues;
   }

   public boolean isUse81StyleExecuteQueuesInherited() {
      return !this._isSet(74) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(74);
   }

   public boolean isUse81StyleExecuteQueuesSet() {
      return this._isSet(74);
   }

   public void setT3ClientAbbrevTableSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("T3ClientAbbrevTableSize", (long)param0, 255L, 1024L);
      boolean wasSet = this._isSet(75);
      int _oldVal = this._T3ClientAbbrevTableSize;
      this._T3ClientAbbrevTableSize = param0;
      this._postSet(75, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(75)) {
            source._postSetFirePropertyChange(75, wasSet, _oldVal, param0);
         }
      }

   }

   public int getT3ClientAbbrevTableSize() {
      return !this._isSet(75) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(75) ? this._getDelegateBean().getT3ClientAbbrevTableSize() : this._T3ClientAbbrevTableSize;
   }

   public boolean isT3ClientAbbrevTableSizeInherited() {
      return !this._isSet(75) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(75);
   }

   public boolean isT3ClientAbbrevTableSizeSet() {
      return this._isSet(75);
   }

   public void setT3ServerAbbrevTableSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("T3ServerAbbrevTableSize", (long)param0, 255L, 10240L);
      boolean wasSet = this._isSet(76);
      int _oldVal = this._T3ServerAbbrevTableSize;
      this._T3ServerAbbrevTableSize = param0;
      this._postSet(76, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(76)) {
            source._postSetFirePropertyChange(76, wasSet, _oldVal, param0);
         }
      }

   }

   public int getT3ServerAbbrevTableSize() {
      return !this._isSet(76) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(76) ? this._getDelegateBean().getT3ServerAbbrevTableSize() : this._T3ServerAbbrevTableSize;
   }

   public boolean isT3ServerAbbrevTableSizeInherited() {
      return !this._isSet(76) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(76);
   }

   public boolean isT3ServerAbbrevTableSizeSet() {
      return this._isSet(76);
   }

   public ExecuteQueueMBean createExecuteQueue(String param0) {
      ExecuteQueueMBeanImpl lookup = (ExecuteQueueMBeanImpl)this.lookupExecuteQueue(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ExecuteQueueMBeanImpl _val = new ExecuteQueueMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addExecuteQueue(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyExecuteQueue(ExecuteQueueMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 62);
         ExecuteQueueMBean[] _old = this.getExecuteQueues();
         ExecuteQueueMBean[] _new = (ExecuteQueueMBean[])((ExecuteQueueMBean[])this._getHelper()._removeElement(_old, ExecuteQueueMBean.class, param0));
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
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  KernelMBeanImpl source = (KernelMBeanImpl)var6.next();
                  ExecuteQueueMBeanImpl childImpl = (ExecuteQueueMBeanImpl)_child;
                  ExecuteQueueMBeanImpl lookup = (ExecuteQueueMBeanImpl)source.lookupExecuteQueue(childImpl.getName());
                  if (lookup != null) {
                     source.destroyExecuteQueue(lookup);
                  }
               }

               this.setExecuteQueues(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public ExecuteQueueMBean lookupExecuteQueue(String param0) {
      Object[] aary = (Object[])this._ExecuteQueues;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ExecuteQueueMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ExecuteQueueMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void setGatheredWritesEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(77);
      boolean _oldVal = this._GatheredWritesEnabled;
      this._GatheredWritesEnabled = param0;
      this._postSet(77, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(77)) {
            source._postSetFirePropertyChange(77, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isGatheredWritesEnabled() {
      if (!this._isSet(77) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(77)) {
         return this._getDelegateBean().isGatheredWritesEnabled();
      } else {
         if (!this._isSet(77)) {
            try {
               return ((DomainMBean)this.getParent()).isExalogicOptimizationsEnabled();
            } catch (NullPointerException var2) {
            }
         }

         return this._GatheredWritesEnabled;
      }
   }

   public boolean isGatheredWritesEnabledInherited() {
      return !this._isSet(77) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(77);
   }

   public boolean isGatheredWritesEnabledSet() {
      return this._isSet(77);
   }

   public void setScatteredReadsEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(78);
      boolean _oldVal = this._ScatteredReadsEnabled;
      this._ScatteredReadsEnabled = param0;
      this._postSet(78, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(78)) {
            source._postSetFirePropertyChange(78, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isScatteredReadsEnabled() {
      if (!this._isSet(78) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(78)) {
         return this._getDelegateBean().isScatteredReadsEnabled();
      } else {
         if (!this._isSet(78)) {
            try {
               return ((DomainMBean)this.getParent()).isExalogicOptimizationsEnabled();
            } catch (NullPointerException var2) {
            }
         }

         return this._ScatteredReadsEnabled;
      }
   }

   public boolean isScatteredReadsEnabledInherited() {
      return !this._isSet(78) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(78);
   }

   public boolean isScatteredReadsEnabledSet() {
      return this._isSet(78);
   }

   public void setAddWorkManagerThreadsByCpuCount(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(79);
      boolean _oldVal = this._AddWorkManagerThreadsByCpuCount;
      this._AddWorkManagerThreadsByCpuCount = param0;
      this._postSet(79, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(79)) {
            source._postSetFirePropertyChange(79, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAddWorkManagerThreadsByCpuCount() {
      if (!this._isSet(79) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(79)) {
         return this._getDelegateBean().isAddWorkManagerThreadsByCpuCount();
      } else {
         if (!this._isSet(79)) {
            try {
               return ((DomainMBean)this.getParent()).isExalogicOptimizationsEnabled();
            } catch (NullPointerException var2) {
            }
         }

         return this._AddWorkManagerThreadsByCpuCount;
      }
   }

   public boolean isAddWorkManagerThreadsByCpuCountInherited() {
      return !this._isSet(79) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(79);
   }

   public boolean isAddWorkManagerThreadsByCpuCountSet() {
      return this._isSet(79);
   }

   public void setUseConcurrentQueueForRequestManager(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(80);
      boolean _oldVal = this._UseConcurrentQueueForRequestManager;
      this._UseConcurrentQueueForRequestManager = param0;
      this._postSet(80, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(80)) {
            source._postSetFirePropertyChange(80, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isUseConcurrentQueueForRequestManager() {
      if (!this._isSet(80) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(80)) {
         return this._getDelegateBean().isUseConcurrentQueueForRequestManager();
      } else {
         if (!this._isSet(80)) {
            try {
               return ((DomainMBean)this.getParent()).isExalogicOptimizationsEnabled();
            } catch (NullPointerException var2) {
            }
         }

         return this._UseConcurrentQueueForRequestManager;
      }
   }

   public boolean isUseConcurrentQueueForRequestManagerInherited() {
      return !this._isSet(80) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(80);
   }

   public boolean isUseConcurrentQueueForRequestManagerSet() {
      return this._isSet(80);
   }

   public int getCompleteWriteTimeout() {
      if (!this._isSet(81) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(81)) {
         return this._getDelegateBean().getCompleteWriteTimeout();
      } else {
         if (!this._isSet(81)) {
            try {
               return this.getCompleteHTTPMessageTimeout() == -1 ? this.getCompleteMessageTimeout() : this.getCompleteHTTPMessageTimeout();
            } catch (NullPointerException var2) {
            }
         }

         return this._CompleteWriteTimeout;
      }
   }

   public boolean isCompleteWriteTimeoutInherited() {
      return !this._isSet(81) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(81);
   }

   public boolean isCompleteWriteTimeoutSet() {
      return this._isSet(81);
   }

   public void setCompleteWriteTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("CompleteWriteTimeout", param0, 0);
      boolean wasSet = this._isSet(81);
      int _oldVal = this._CompleteWriteTimeout;
      this._CompleteWriteTimeout = param0;
      this._postSet(81, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(81)) {
            source._postSetFirePropertyChange(81, wasSet, _oldVal, param0);
         }
      }

   }

   public void setUseDetailedThreadName(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(82);
      boolean _oldVal = this._UseDetailedThreadName;
      this._UseDetailedThreadName = param0;
      this._postSet(82, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(82)) {
            source._postSetFirePropertyChange(82, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isUseDetailedThreadName() {
      return !this._isSet(82) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(82) ? this._getDelegateBean().isUseDetailedThreadName() : this._UseDetailedThreadName;
   }

   public boolean isUseDetailedThreadNameInherited() {
      return !this._isSet(82) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(82);
   }

   public boolean isUseDetailedThreadNameSet() {
      return this._isSet(82);
   }

   public void setUseEnhancedPriorityQueueForRequestManager(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(83);
      boolean _oldVal = this._UseEnhancedPriorityQueueForRequestManager;
      this._UseEnhancedPriorityQueueForRequestManager = param0;
      this._postSet(83, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(83)) {
            source._postSetFirePropertyChange(83, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isUseEnhancedPriorityQueueForRequestManager() {
      return !this._isSet(83) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(83) ? this._getDelegateBean().isUseEnhancedPriorityQueueForRequestManager() : this._UseEnhancedPriorityQueueForRequestManager;
   }

   public boolean isUseEnhancedPriorityQueueForRequestManagerInherited() {
      return !this._isSet(83) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(83);
   }

   public boolean isUseEnhancedPriorityQueueForRequestManagerSet() {
      return this._isSet(83);
   }

   public void setAllowShrinkingPriorityRequestQueue(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(84);
      boolean _oldVal = this._AllowShrinkingPriorityRequestQueue;
      this._AllowShrinkingPriorityRequestQueue = param0;
      this._postSet(84, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(84)) {
            source._postSetFirePropertyChange(84, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAllowShrinkingPriorityRequestQueue() {
      return !this._isSet(84) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(84) ? this._getDelegateBean().isAllowShrinkingPriorityRequestQueue() : this._AllowShrinkingPriorityRequestQueue;
   }

   public boolean isAllowShrinkingPriorityRequestQueueInherited() {
      return !this._isSet(84) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(84);
   }

   public boolean isAllowShrinkingPriorityRequestQueueSet() {
      return this._isSet(84);
   }

   public void setUseEnhancedIncrementAdvisor(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(85);
      boolean _oldVal = this._UseEnhancedIncrementAdvisor;
      this._UseEnhancedIncrementAdvisor = param0;
      this._postSet(85, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(85)) {
            source._postSetFirePropertyChange(85, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isUseEnhancedIncrementAdvisor() {
      return !this._isSet(85) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(85) ? this._getDelegateBean().isUseEnhancedIncrementAdvisor() : this._UseEnhancedIncrementAdvisor;
   }

   public boolean isUseEnhancedIncrementAdvisorInherited() {
      return !this._isSet(85) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(85);
   }

   public boolean isUseEnhancedIncrementAdvisorSet() {
      return this._isSet(85);
   }

   public void setEagerThreadLocalCleanup(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(86);
      boolean _oldVal = this._EagerThreadLocalCleanup;
      this._EagerThreadLocalCleanup = param0;
      this._postSet(86, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(86)) {
            source._postSetFirePropertyChange(86, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isEagerThreadLocalCleanup() {
      return !this._isSet(86) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(86) ? this._getDelegateBean().isEagerThreadLocalCleanup() : this._EagerThreadLocalCleanup;
   }

   public boolean isEagerThreadLocalCleanupInherited() {
      return !this._isSet(86) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(86);
   }

   public boolean isEagerThreadLocalCleanupSet() {
      return this._isSet(86);
   }

   public void setIsolatePartitionThreadLocals(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(87);
      boolean _oldVal = this._IsolatePartitionThreadLocals;
      this._IsolatePartitionThreadLocals = param0;
      this._postSet(87, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         KernelMBeanImpl source = (KernelMBeanImpl)var4.next();
         if (source != null && !source._isSet(87)) {
            source._postSetFirePropertyChange(87, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isIsolatePartitionThreadLocals() {
      return !this._isSet(87) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(87) ? this._getDelegateBean().isIsolatePartitionThreadLocals() : this._IsolatePartitionThreadLocals;
   }

   public boolean isIsolatePartitionThreadLocalsInherited() {
      return !this._isSet(87) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(87);
   }

   public boolean isIsolatePartitionThreadLocalsSet() {
      return this._isSet(87);
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
      return super._isAnythingSet() || this.isIIOPSet() || this.isLogSet() || this.isSSLSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._AdministrationProtocol = null;
               if (initOne) {
                  break;
               }
            case 43:
               this._CompleteCOMMessageTimeout = -1;
               if (initOne) {
                  break;
               }
            case 42:
               this._CompleteHTTPMessageTimeout = -1;
               if (initOne) {
                  break;
               }
            case 46:
               this._CompleteIIOPMessageTimeout = -1;
               if (initOne) {
                  break;
               }
            case 40:
               this._CompleteMessageTimeout = 60;
               if (initOne) {
                  break;
               }
            case 41:
               this._CompleteT3MessageTimeout = -1;
               if (initOne) {
                  break;
               }
            case 81:
               this._CompleteWriteTimeout = 0;
               if (initOne) {
                  break;
               }
            case 39:
               this._ConnectTimeout = 0;
               if (initOne) {
                  break;
               }
            case 52:
               this._DGCIdlePeriodsUntilTimeout = 5;
               if (initOne) {
                  break;
               }
            case 35:
               this._DefaultGIOPMinorVersion = 2;
               if (initOne) {
                  break;
               }
            case 12:
               this._DefaultProtocol = "t3";
               if (initOne) {
                  break;
               }
            case 13:
               this._DefaultSecureProtocol = "t3s";
               if (initOne) {
                  break;
               }
            case 62:
               this._ExecuteQueues = new ExecuteQueueMBean[0];
               if (initOne) {
                  break;
               }
            case 54:
               this._IIOP = new IIOPMBeanImpl(this, 54);
               this._postCreate((AbstractDescriptorBean)this._IIOP);
               if (initOne) {
                  break;
               }
            case 38:
               this._IIOPLocationForwardPolicy = "off";
               if (initOne) {
                  break;
               }
            case 37:
               this._IIOPTxMechanism = "ots";
               if (initOne) {
                  break;
               }
            case 44:
               this._IdleConnectionTimeout = 65;
               if (initOne) {
                  break;
               }
            case 45:
               this._IdleIIOPConnectionTimeout = -1;
               if (initOne) {
                  break;
               }
            case 48:
               this._IdlePeriodsUntilTimeout = 4;
               if (initOne) {
                  break;
               }
            case 19:
               this._JMSThreadPoolSize = 15;
               if (initOne) {
                  break;
               }
            case 51:
               this._KernelDebug = null;
               if (initOne) {
                  break;
               }
            case 71:
               this._LoadStubUsingContextClassLoader = false;
               if (initOne) {
                  break;
               }
            case 55:
               this._Log = new LogMBeanImpl(this, 55);
               this._postCreate((AbstractDescriptorBean)this._Log);
               if (initOne) {
                  break;
               }
            case 70:
               this._MTUSize = 1500;
               if (initOne) {
                  break;
               }
            case 33:
               this._MaxCOMMessageSize = -1;
               if (initOne) {
                  break;
               }
            case 32:
               this._MaxHTTPMessageSize = -1;
               if (initOne) {
                  break;
               }
            case 34:
               this._MaxIIOPMessageSize = -1;
               if (initOne) {
                  break;
               }
            case 29:
               this._MaxMessageSize = 10000000;
               if (initOne) {
                  break;
               }
            case 63:
               this._MaxOpenSockCount = -1;
               if (initOne) {
                  break;
               }
            case 30:
               this._MaxT3MessageSize = -1;
               if (initOne) {
                  break;
               }
            case 69:
               this._MessagingBridgeThreadPoolSize = 5;
               if (initOne) {
                  break;
               }
            case 22:
               this._MuxerClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 47:
               this._PeriodLength = 60000;
               if (initOne) {
                  break;
               }
            case 61:
               this._PrintStackTraceInProduction = false;
               if (initOne) {
                  break;
               }
            case 72:
               this._RefreshClientRuntimeDescriptor = false;
               if (initOne) {
                  break;
               }
            case 50:
               this._ResponseTimeout = 0;
               if (initOne) {
                  break;
               }
            case 49:
               this._RjvmIdleTimeout = 0;
               if (initOne) {
                  break;
               }
            case 53:
               this._SSL = new SSLMBeanImpl(this, 53);
               this._postCreate((AbstractDescriptorBean)this._SSL);
               if (initOne) {
                  break;
               }
            case 18:
               this._SelfTuningThreadPoolSizeMax = 400;
               if (initOne) {
                  break;
               }
            case 17:
               this._SelfTuningThreadPoolSizeMin = 1;
               if (initOne) {
                  break;
               }
            case 26:
               this._SocketReaderTimeoutMaxMillis = 100;
               if (initOne) {
                  break;
               }
            case 25:
               this._SocketReaderTimeoutMinMillis = 10;
               if (initOne) {
                  break;
               }
            case 23:
               this._SocketReaders = -1;
               if (initOne) {
                  break;
               }
            case 64:
               this._customizer.setStdoutFormat("standard");
               if (initOne) {
                  break;
               }
            case 57:
               this._customizer.setStdoutSeverityLevel(32);
               if (initOne) {
                  break;
               }
            case 66:
               this._StuckThreadMaxTime = 600;
               if (initOne) {
                  break;
               }
            case 67:
               this._StuckThreadTimerInterval = 60;
               if (initOne) {
                  break;
               }
            case 16:
               this._SystemThreadPoolSize = 5;
               if (initOne) {
                  break;
               }
            case 75:
               this._T3ClientAbbrevTableSize = 255;
               if (initOne) {
                  break;
               }
            case 76:
               this._T3ServerAbbrevTableSize = 2048;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 24:
               this._ThreadPoolPercentSocketReaders = 33;
               if (initOne) {
                  break;
               }
            case 15:
               this._ThreadPoolSize = 15;
               if (initOne) {
                  break;
               }
            case 73:
               this._TimedOutRefIsolationTime = 0L;
               if (initOne) {
                  break;
               }
            case 68:
               this._TracingEnabled = false;
               if (initOne) {
                  break;
               }
            case 74:
               this._Use81StyleExecuteQueues = false;
               if (initOne) {
                  break;
               }
            case 36:
               this._UseIIOPLocateRequest = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._ValidProtocols = null;
               if (initOne) {
                  break;
               }
            case 79:
               this._AddWorkManagerThreadsByCpuCount = false;
               if (initOne) {
                  break;
               }
            case 84:
               this._AllowShrinkingPriorityRequestQueue = true;
               if (initOne) {
                  break;
               }
            case 21:
               this._DevPollDisabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 86:
               this._EagerThreadLocalCleanup = false;
               if (initOne) {
                  break;
               }
            case 77:
               this._GatheredWritesEnabled = false;
               if (initOne) {
                  break;
               }
            case 60:
               this._InstrumentStackTraceEnabled = true;
               if (initOne) {
                  break;
               }
            case 87:
               this._IsolatePartitionThreadLocals = false;
               if (initOne) {
                  break;
               }
            case 59:
               this._LogRemoteExceptionsEnabled = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._NativeIOEnabled = true;
               if (initOne) {
                  break;
               }
            case 27:
               this._OutboundEnabled = false;
               if (initOne) {
                  break;
               }
            case 28:
               this._OutboundPrivateKeyEnabled = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._ReverseDNSAllowed = false;
               if (initOne) {
                  break;
               }
            case 78:
               this._ScatteredReadsEnabled = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._SocketBufferSizeAsChunkSize = false;
               if (initOne) {
                  break;
               }
            case 58:
               this._customizer.setStdoutDebugEnabled(false);
               if (initOne) {
                  break;
               }
            case 56:
               this._customizer.setStdoutEnabled(true);
               if (initOne) {
                  break;
               }
            case 65:
               this._customizer.setStdoutLogStack(true);
               if (initOne) {
                  break;
               }
            case 80:
               this._UseConcurrentQueueForRequestManager = false;
               if (initOne) {
                  break;
               }
            case 82:
               this._UseDetailedThreadName = false;
               if (initOne) {
                  break;
               }
            case 85:
               this._UseEnhancedIncrementAdvisor = true;
               if (initOne) {
                  break;
               }
            case 83:
               this._UseEnhancedPriorityQueueForRequestManager = false;
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
      return "Kernel";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("AddWorkManagerThreadsByCpuCount")) {
         oldVal = this._AddWorkManagerThreadsByCpuCount;
         this._AddWorkManagerThreadsByCpuCount = (Boolean)v;
         this._postSet(79, oldVal, this._AddWorkManagerThreadsByCpuCount);
      } else {
         String oldVal;
         if (name.equals("AdministrationProtocol")) {
            oldVal = this._AdministrationProtocol;
            this._AdministrationProtocol = (String)v;
            this._postSet(14, oldVal, this._AdministrationProtocol);
         } else if (name.equals("AllowShrinkingPriorityRequestQueue")) {
            oldVal = this._AllowShrinkingPriorityRequestQueue;
            this._AllowShrinkingPriorityRequestQueue = (Boolean)v;
            this._postSet(84, oldVal, this._AllowShrinkingPriorityRequestQueue);
         } else {
            int oldVal;
            if (name.equals("CompleteCOMMessageTimeout")) {
               oldVal = this._CompleteCOMMessageTimeout;
               this._CompleteCOMMessageTimeout = (Integer)v;
               this._postSet(43, oldVal, this._CompleteCOMMessageTimeout);
            } else if (name.equals("CompleteHTTPMessageTimeout")) {
               oldVal = this._CompleteHTTPMessageTimeout;
               this._CompleteHTTPMessageTimeout = (Integer)v;
               this._postSet(42, oldVal, this._CompleteHTTPMessageTimeout);
            } else if (name.equals("CompleteIIOPMessageTimeout")) {
               oldVal = this._CompleteIIOPMessageTimeout;
               this._CompleteIIOPMessageTimeout = (Integer)v;
               this._postSet(46, oldVal, this._CompleteIIOPMessageTimeout);
            } else if (name.equals("CompleteMessageTimeout")) {
               oldVal = this._CompleteMessageTimeout;
               this._CompleteMessageTimeout = (Integer)v;
               this._postSet(40, oldVal, this._CompleteMessageTimeout);
            } else if (name.equals("CompleteT3MessageTimeout")) {
               oldVal = this._CompleteT3MessageTimeout;
               this._CompleteT3MessageTimeout = (Integer)v;
               this._postSet(41, oldVal, this._CompleteT3MessageTimeout);
            } else if (name.equals("CompleteWriteTimeout")) {
               oldVal = this._CompleteWriteTimeout;
               this._CompleteWriteTimeout = (Integer)v;
               this._postSet(81, oldVal, this._CompleteWriteTimeout);
            } else if (name.equals("ConnectTimeout")) {
               oldVal = this._ConnectTimeout;
               this._ConnectTimeout = (Integer)v;
               this._postSet(39, oldVal, this._ConnectTimeout);
            } else if (name.equals("DGCIdlePeriodsUntilTimeout")) {
               oldVal = this._DGCIdlePeriodsUntilTimeout;
               this._DGCIdlePeriodsUntilTimeout = (Integer)v;
               this._postSet(52, oldVal, this._DGCIdlePeriodsUntilTimeout);
            } else if (name.equals("DefaultGIOPMinorVersion")) {
               oldVal = this._DefaultGIOPMinorVersion;
               this._DefaultGIOPMinorVersion = (Integer)v;
               this._postSet(35, oldVal, this._DefaultGIOPMinorVersion);
            } else if (name.equals("DefaultProtocol")) {
               oldVal = this._DefaultProtocol;
               this._DefaultProtocol = (String)v;
               this._postSet(12, oldVal, this._DefaultProtocol);
            } else if (name.equals("DefaultSecureProtocol")) {
               oldVal = this._DefaultSecureProtocol;
               this._DefaultSecureProtocol = (String)v;
               this._postSet(13, oldVal, this._DefaultSecureProtocol);
            } else if (name.equals("DevPollDisabled")) {
               oldVal = this._DevPollDisabled;
               this._DevPollDisabled = (Boolean)v;
               this._postSet(21, oldVal, this._DevPollDisabled);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("EagerThreadLocalCleanup")) {
               oldVal = this._EagerThreadLocalCleanup;
               this._EagerThreadLocalCleanup = (Boolean)v;
               this._postSet(86, oldVal, this._EagerThreadLocalCleanup);
            } else if (name.equals("ExecuteQueues")) {
               ExecuteQueueMBean[] oldVal = this._ExecuteQueues;
               this._ExecuteQueues = (ExecuteQueueMBean[])((ExecuteQueueMBean[])v);
               this._postSet(62, oldVal, this._ExecuteQueues);
            } else if (name.equals("GatheredWritesEnabled")) {
               oldVal = this._GatheredWritesEnabled;
               this._GatheredWritesEnabled = (Boolean)v;
               this._postSet(77, oldVal, this._GatheredWritesEnabled);
            } else if (name.equals("IIOP")) {
               IIOPMBean oldVal = this._IIOP;
               this._IIOP = (IIOPMBean)v;
               this._postSet(54, oldVal, this._IIOP);
            } else if (name.equals("IIOPLocationForwardPolicy")) {
               oldVal = this._IIOPLocationForwardPolicy;
               this._IIOPLocationForwardPolicy = (String)v;
               this._postSet(38, oldVal, this._IIOPLocationForwardPolicy);
            } else if (name.equals("IIOPTxMechanism")) {
               oldVal = this._IIOPTxMechanism;
               this._IIOPTxMechanism = (String)v;
               this._postSet(37, oldVal, this._IIOPTxMechanism);
            } else if (name.equals("IdleConnectionTimeout")) {
               oldVal = this._IdleConnectionTimeout;
               this._IdleConnectionTimeout = (Integer)v;
               this._postSet(44, oldVal, this._IdleConnectionTimeout);
            } else if (name.equals("IdleIIOPConnectionTimeout")) {
               oldVal = this._IdleIIOPConnectionTimeout;
               this._IdleIIOPConnectionTimeout = (Integer)v;
               this._postSet(45, oldVal, this._IdleIIOPConnectionTimeout);
            } else if (name.equals("IdlePeriodsUntilTimeout")) {
               oldVal = this._IdlePeriodsUntilTimeout;
               this._IdlePeriodsUntilTimeout = (Integer)v;
               this._postSet(48, oldVal, this._IdlePeriodsUntilTimeout);
            } else if (name.equals("InstrumentStackTraceEnabled")) {
               oldVal = this._InstrumentStackTraceEnabled;
               this._InstrumentStackTraceEnabled = (Boolean)v;
               this._postSet(60, oldVal, this._InstrumentStackTraceEnabled);
            } else if (name.equals("IsolatePartitionThreadLocals")) {
               oldVal = this._IsolatePartitionThreadLocals;
               this._IsolatePartitionThreadLocals = (Boolean)v;
               this._postSet(87, oldVal, this._IsolatePartitionThreadLocals);
            } else if (name.equals("JMSThreadPoolSize")) {
               oldVal = this._JMSThreadPoolSize;
               this._JMSThreadPoolSize = (Integer)v;
               this._postSet(19, oldVal, this._JMSThreadPoolSize);
            } else if (name.equals("KernelDebug")) {
               KernelDebugMBean oldVal = this._KernelDebug;
               this._KernelDebug = (KernelDebugMBean)v;
               this._postSet(51, oldVal, this._KernelDebug);
            } else if (name.equals("LoadStubUsingContextClassLoader")) {
               oldVal = this._LoadStubUsingContextClassLoader;
               this._LoadStubUsingContextClassLoader = (Boolean)v;
               this._postSet(71, oldVal, this._LoadStubUsingContextClassLoader);
            } else if (name.equals("Log")) {
               LogMBean oldVal = this._Log;
               this._Log = (LogMBean)v;
               this._postSet(55, oldVal, this._Log);
            } else if (name.equals("LogRemoteExceptionsEnabled")) {
               oldVal = this._LogRemoteExceptionsEnabled;
               this._LogRemoteExceptionsEnabled = (Boolean)v;
               this._postSet(59, oldVal, this._LogRemoteExceptionsEnabled);
            } else if (name.equals("MTUSize")) {
               oldVal = this._MTUSize;
               this._MTUSize = (Integer)v;
               this._postSet(70, oldVal, this._MTUSize);
            } else if (name.equals("MaxCOMMessageSize")) {
               oldVal = this._MaxCOMMessageSize;
               this._MaxCOMMessageSize = (Integer)v;
               this._postSet(33, oldVal, this._MaxCOMMessageSize);
            } else if (name.equals("MaxHTTPMessageSize")) {
               oldVal = this._MaxHTTPMessageSize;
               this._MaxHTTPMessageSize = (Integer)v;
               this._postSet(32, oldVal, this._MaxHTTPMessageSize);
            } else if (name.equals("MaxIIOPMessageSize")) {
               oldVal = this._MaxIIOPMessageSize;
               this._MaxIIOPMessageSize = (Integer)v;
               this._postSet(34, oldVal, this._MaxIIOPMessageSize);
            } else if (name.equals("MaxMessageSize")) {
               oldVal = this._MaxMessageSize;
               this._MaxMessageSize = (Integer)v;
               this._postSet(29, oldVal, this._MaxMessageSize);
            } else if (name.equals("MaxOpenSockCount")) {
               oldVal = this._MaxOpenSockCount;
               this._MaxOpenSockCount = (Integer)v;
               this._postSet(63, oldVal, this._MaxOpenSockCount);
            } else if (name.equals("MaxT3MessageSize")) {
               oldVal = this._MaxT3MessageSize;
               this._MaxT3MessageSize = (Integer)v;
               this._postSet(30, oldVal, this._MaxT3MessageSize);
            } else if (name.equals("MessagingBridgeThreadPoolSize")) {
               oldVal = this._MessagingBridgeThreadPoolSize;
               this._MessagingBridgeThreadPoolSize = (Integer)v;
               this._postSet(69, oldVal, this._MessagingBridgeThreadPoolSize);
            } else if (name.equals("MuxerClass")) {
               oldVal = this._MuxerClass;
               this._MuxerClass = (String)v;
               this._postSet(22, oldVal, this._MuxerClass);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("NativeIOEnabled")) {
               oldVal = this._NativeIOEnabled;
               this._NativeIOEnabled = (Boolean)v;
               this._postSet(20, oldVal, this._NativeIOEnabled);
            } else if (name.equals("OutboundEnabled")) {
               oldVal = this._OutboundEnabled;
               this._OutboundEnabled = (Boolean)v;
               this._postSet(27, oldVal, this._OutboundEnabled);
            } else if (name.equals("OutboundPrivateKeyEnabled")) {
               oldVal = this._OutboundPrivateKeyEnabled;
               this._OutboundPrivateKeyEnabled = (Boolean)v;
               this._postSet(28, oldVal, this._OutboundPrivateKeyEnabled);
            } else if (name.equals("PeriodLength")) {
               oldVal = this._PeriodLength;
               this._PeriodLength = (Integer)v;
               this._postSet(47, oldVal, this._PeriodLength);
            } else if (name.equals("PrintStackTraceInProduction")) {
               oldVal = this._PrintStackTraceInProduction;
               this._PrintStackTraceInProduction = (Boolean)v;
               this._postSet(61, oldVal, this._PrintStackTraceInProduction);
            } else if (name.equals("RefreshClientRuntimeDescriptor")) {
               oldVal = this._RefreshClientRuntimeDescriptor;
               this._RefreshClientRuntimeDescriptor = (Boolean)v;
               this._postSet(72, oldVal, this._RefreshClientRuntimeDescriptor);
            } else if (name.equals("ResponseTimeout")) {
               oldVal = this._ResponseTimeout;
               this._ResponseTimeout = (Integer)v;
               this._postSet(50, oldVal, this._ResponseTimeout);
            } else if (name.equals("ReverseDNSAllowed")) {
               oldVal = this._ReverseDNSAllowed;
               this._ReverseDNSAllowed = (Boolean)v;
               this._postSet(11, oldVal, this._ReverseDNSAllowed);
            } else if (name.equals("RjvmIdleTimeout")) {
               oldVal = this._RjvmIdleTimeout;
               this._RjvmIdleTimeout = (Integer)v;
               this._postSet(49, oldVal, this._RjvmIdleTimeout);
            } else if (name.equals("SSL")) {
               SSLMBean oldVal = this._SSL;
               this._SSL = (SSLMBean)v;
               this._postSet(53, oldVal, this._SSL);
            } else if (name.equals("ScatteredReadsEnabled")) {
               oldVal = this._ScatteredReadsEnabled;
               this._ScatteredReadsEnabled = (Boolean)v;
               this._postSet(78, oldVal, this._ScatteredReadsEnabled);
            } else if (name.equals("SelfTuningThreadPoolSizeMax")) {
               oldVal = this._SelfTuningThreadPoolSizeMax;
               this._SelfTuningThreadPoolSizeMax = (Integer)v;
               this._postSet(18, oldVal, this._SelfTuningThreadPoolSizeMax);
            } else if (name.equals("SelfTuningThreadPoolSizeMin")) {
               oldVal = this._SelfTuningThreadPoolSizeMin;
               this._SelfTuningThreadPoolSizeMin = (Integer)v;
               this._postSet(17, oldVal, this._SelfTuningThreadPoolSizeMin);
            } else if (name.equals("SocketBufferSizeAsChunkSize")) {
               oldVal = this._SocketBufferSizeAsChunkSize;
               this._SocketBufferSizeAsChunkSize = (Boolean)v;
               this._postSet(31, oldVal, this._SocketBufferSizeAsChunkSize);
            } else if (name.equals("SocketReaderTimeoutMaxMillis")) {
               oldVal = this._SocketReaderTimeoutMaxMillis;
               this._SocketReaderTimeoutMaxMillis = (Integer)v;
               this._postSet(26, oldVal, this._SocketReaderTimeoutMaxMillis);
            } else if (name.equals("SocketReaderTimeoutMinMillis")) {
               oldVal = this._SocketReaderTimeoutMinMillis;
               this._SocketReaderTimeoutMinMillis = (Integer)v;
               this._postSet(25, oldVal, this._SocketReaderTimeoutMinMillis);
            } else if (name.equals("SocketReaders")) {
               oldVal = this._SocketReaders;
               this._SocketReaders = (Integer)v;
               this._postSet(23, oldVal, this._SocketReaders);
            } else if (name.equals("StdoutDebugEnabled")) {
               oldVal = this._StdoutDebugEnabled;
               this._StdoutDebugEnabled = (Boolean)v;
               this._postSet(58, oldVal, this._StdoutDebugEnabled);
            } else if (name.equals("StdoutEnabled")) {
               oldVal = this._StdoutEnabled;
               this._StdoutEnabled = (Boolean)v;
               this._postSet(56, oldVal, this._StdoutEnabled);
            } else if (name.equals("StdoutFormat")) {
               oldVal = this._StdoutFormat;
               this._StdoutFormat = (String)v;
               this._postSet(64, oldVal, this._StdoutFormat);
            } else if (name.equals("StdoutLogStack")) {
               oldVal = this._StdoutLogStack;
               this._StdoutLogStack = (Boolean)v;
               this._postSet(65, oldVal, this._StdoutLogStack);
            } else if (name.equals("StdoutSeverityLevel")) {
               oldVal = this._StdoutSeverityLevel;
               this._StdoutSeverityLevel = (Integer)v;
               this._postSet(57, oldVal, this._StdoutSeverityLevel);
            } else if (name.equals("StuckThreadMaxTime")) {
               oldVal = this._StuckThreadMaxTime;
               this._StuckThreadMaxTime = (Integer)v;
               this._postSet(66, oldVal, this._StuckThreadMaxTime);
            } else if (name.equals("StuckThreadTimerInterval")) {
               oldVal = this._StuckThreadTimerInterval;
               this._StuckThreadTimerInterval = (Integer)v;
               this._postSet(67, oldVal, this._StuckThreadTimerInterval);
            } else if (name.equals("SystemThreadPoolSize")) {
               oldVal = this._SystemThreadPoolSize;
               this._SystemThreadPoolSize = (Integer)v;
               this._postSet(16, oldVal, this._SystemThreadPoolSize);
            } else if (name.equals("T3ClientAbbrevTableSize")) {
               oldVal = this._T3ClientAbbrevTableSize;
               this._T3ClientAbbrevTableSize = (Integer)v;
               this._postSet(75, oldVal, this._T3ClientAbbrevTableSize);
            } else if (name.equals("T3ServerAbbrevTableSize")) {
               oldVal = this._T3ServerAbbrevTableSize;
               this._T3ServerAbbrevTableSize = (Integer)v;
               this._postSet(76, oldVal, this._T3ServerAbbrevTableSize);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("ThreadPoolPercentSocketReaders")) {
               oldVal = this._ThreadPoolPercentSocketReaders;
               this._ThreadPoolPercentSocketReaders = (Integer)v;
               this._postSet(24, oldVal, this._ThreadPoolPercentSocketReaders);
            } else if (name.equals("ThreadPoolSize")) {
               oldVal = this._ThreadPoolSize;
               this._ThreadPoolSize = (Integer)v;
               this._postSet(15, oldVal, this._ThreadPoolSize);
            } else if (name.equals("TimedOutRefIsolationTime")) {
               long oldVal = this._TimedOutRefIsolationTime;
               this._TimedOutRefIsolationTime = (Long)v;
               this._postSet(73, oldVal, this._TimedOutRefIsolationTime);
            } else if (name.equals("TracingEnabled")) {
               oldVal = this._TracingEnabled;
               this._TracingEnabled = (Boolean)v;
               this._postSet(68, oldVal, this._TracingEnabled);
            } else if (name.equals("Use81StyleExecuteQueues")) {
               oldVal = this._Use81StyleExecuteQueues;
               this._Use81StyleExecuteQueues = (Boolean)v;
               this._postSet(74, oldVal, this._Use81StyleExecuteQueues);
            } else if (name.equals("UseConcurrentQueueForRequestManager")) {
               oldVal = this._UseConcurrentQueueForRequestManager;
               this._UseConcurrentQueueForRequestManager = (Boolean)v;
               this._postSet(80, oldVal, this._UseConcurrentQueueForRequestManager);
            } else if (name.equals("UseDetailedThreadName")) {
               oldVal = this._UseDetailedThreadName;
               this._UseDetailedThreadName = (Boolean)v;
               this._postSet(82, oldVal, this._UseDetailedThreadName);
            } else if (name.equals("UseEnhancedIncrementAdvisor")) {
               oldVal = this._UseEnhancedIncrementAdvisor;
               this._UseEnhancedIncrementAdvisor = (Boolean)v;
               this._postSet(85, oldVal, this._UseEnhancedIncrementAdvisor);
            } else if (name.equals("UseEnhancedPriorityQueueForRequestManager")) {
               oldVal = this._UseEnhancedPriorityQueueForRequestManager;
               this._UseEnhancedPriorityQueueForRequestManager = (Boolean)v;
               this._postSet(83, oldVal, this._UseEnhancedPriorityQueueForRequestManager);
            } else if (name.equals("UseIIOPLocateRequest")) {
               oldVal = this._UseIIOPLocateRequest;
               this._UseIIOPLocateRequest = (Boolean)v;
               this._postSet(36, oldVal, this._UseIIOPLocateRequest);
            } else if (name.equals("ValidProtocols")) {
               Map oldVal = this._ValidProtocols;
               this._ValidProtocols = (Map)v;
               this._postSet(10, oldVal, this._ValidProtocols);
            } else if (name.equals("customizer")) {
               Kernel oldVal = this._customizer;
               this._customizer = (Kernel)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AddWorkManagerThreadsByCpuCount")) {
         return new Boolean(this._AddWorkManagerThreadsByCpuCount);
      } else if (name.equals("AdministrationProtocol")) {
         return this._AdministrationProtocol;
      } else if (name.equals("AllowShrinkingPriorityRequestQueue")) {
         return new Boolean(this._AllowShrinkingPriorityRequestQueue);
      } else if (name.equals("CompleteCOMMessageTimeout")) {
         return new Integer(this._CompleteCOMMessageTimeout);
      } else if (name.equals("CompleteHTTPMessageTimeout")) {
         return new Integer(this._CompleteHTTPMessageTimeout);
      } else if (name.equals("CompleteIIOPMessageTimeout")) {
         return new Integer(this._CompleteIIOPMessageTimeout);
      } else if (name.equals("CompleteMessageTimeout")) {
         return new Integer(this._CompleteMessageTimeout);
      } else if (name.equals("CompleteT3MessageTimeout")) {
         return new Integer(this._CompleteT3MessageTimeout);
      } else if (name.equals("CompleteWriteTimeout")) {
         return new Integer(this._CompleteWriteTimeout);
      } else if (name.equals("ConnectTimeout")) {
         return new Integer(this._ConnectTimeout);
      } else if (name.equals("DGCIdlePeriodsUntilTimeout")) {
         return new Integer(this._DGCIdlePeriodsUntilTimeout);
      } else if (name.equals("DefaultGIOPMinorVersion")) {
         return new Integer(this._DefaultGIOPMinorVersion);
      } else if (name.equals("DefaultProtocol")) {
         return this._DefaultProtocol;
      } else if (name.equals("DefaultSecureProtocol")) {
         return this._DefaultSecureProtocol;
      } else if (name.equals("DevPollDisabled")) {
         return new Boolean(this._DevPollDisabled);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("EagerThreadLocalCleanup")) {
         return new Boolean(this._EagerThreadLocalCleanup);
      } else if (name.equals("ExecuteQueues")) {
         return this._ExecuteQueues;
      } else if (name.equals("GatheredWritesEnabled")) {
         return new Boolean(this._GatheredWritesEnabled);
      } else if (name.equals("IIOP")) {
         return this._IIOP;
      } else if (name.equals("IIOPLocationForwardPolicy")) {
         return this._IIOPLocationForwardPolicy;
      } else if (name.equals("IIOPTxMechanism")) {
         return this._IIOPTxMechanism;
      } else if (name.equals("IdleConnectionTimeout")) {
         return new Integer(this._IdleConnectionTimeout);
      } else if (name.equals("IdleIIOPConnectionTimeout")) {
         return new Integer(this._IdleIIOPConnectionTimeout);
      } else if (name.equals("IdlePeriodsUntilTimeout")) {
         return new Integer(this._IdlePeriodsUntilTimeout);
      } else if (name.equals("InstrumentStackTraceEnabled")) {
         return new Boolean(this._InstrumentStackTraceEnabled);
      } else if (name.equals("IsolatePartitionThreadLocals")) {
         return new Boolean(this._IsolatePartitionThreadLocals);
      } else if (name.equals("JMSThreadPoolSize")) {
         return new Integer(this._JMSThreadPoolSize);
      } else if (name.equals("KernelDebug")) {
         return this._KernelDebug;
      } else if (name.equals("LoadStubUsingContextClassLoader")) {
         return new Boolean(this._LoadStubUsingContextClassLoader);
      } else if (name.equals("Log")) {
         return this._Log;
      } else if (name.equals("LogRemoteExceptionsEnabled")) {
         return new Boolean(this._LogRemoteExceptionsEnabled);
      } else if (name.equals("MTUSize")) {
         return new Integer(this._MTUSize);
      } else if (name.equals("MaxCOMMessageSize")) {
         return new Integer(this._MaxCOMMessageSize);
      } else if (name.equals("MaxHTTPMessageSize")) {
         return new Integer(this._MaxHTTPMessageSize);
      } else if (name.equals("MaxIIOPMessageSize")) {
         return new Integer(this._MaxIIOPMessageSize);
      } else if (name.equals("MaxMessageSize")) {
         return new Integer(this._MaxMessageSize);
      } else if (name.equals("MaxOpenSockCount")) {
         return new Integer(this._MaxOpenSockCount);
      } else if (name.equals("MaxT3MessageSize")) {
         return new Integer(this._MaxT3MessageSize);
      } else if (name.equals("MessagingBridgeThreadPoolSize")) {
         return new Integer(this._MessagingBridgeThreadPoolSize);
      } else if (name.equals("MuxerClass")) {
         return this._MuxerClass;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NativeIOEnabled")) {
         return new Boolean(this._NativeIOEnabled);
      } else if (name.equals("OutboundEnabled")) {
         return new Boolean(this._OutboundEnabled);
      } else if (name.equals("OutboundPrivateKeyEnabled")) {
         return new Boolean(this._OutboundPrivateKeyEnabled);
      } else if (name.equals("PeriodLength")) {
         return new Integer(this._PeriodLength);
      } else if (name.equals("PrintStackTraceInProduction")) {
         return new Boolean(this._PrintStackTraceInProduction);
      } else if (name.equals("RefreshClientRuntimeDescriptor")) {
         return new Boolean(this._RefreshClientRuntimeDescriptor);
      } else if (name.equals("ResponseTimeout")) {
         return new Integer(this._ResponseTimeout);
      } else if (name.equals("ReverseDNSAllowed")) {
         return new Boolean(this._ReverseDNSAllowed);
      } else if (name.equals("RjvmIdleTimeout")) {
         return new Integer(this._RjvmIdleTimeout);
      } else if (name.equals("SSL")) {
         return this._SSL;
      } else if (name.equals("ScatteredReadsEnabled")) {
         return new Boolean(this._ScatteredReadsEnabled);
      } else if (name.equals("SelfTuningThreadPoolSizeMax")) {
         return new Integer(this._SelfTuningThreadPoolSizeMax);
      } else if (name.equals("SelfTuningThreadPoolSizeMin")) {
         return new Integer(this._SelfTuningThreadPoolSizeMin);
      } else if (name.equals("SocketBufferSizeAsChunkSize")) {
         return new Boolean(this._SocketBufferSizeAsChunkSize);
      } else if (name.equals("SocketReaderTimeoutMaxMillis")) {
         return new Integer(this._SocketReaderTimeoutMaxMillis);
      } else if (name.equals("SocketReaderTimeoutMinMillis")) {
         return new Integer(this._SocketReaderTimeoutMinMillis);
      } else if (name.equals("SocketReaders")) {
         return new Integer(this._SocketReaders);
      } else if (name.equals("StdoutDebugEnabled")) {
         return new Boolean(this._StdoutDebugEnabled);
      } else if (name.equals("StdoutEnabled")) {
         return new Boolean(this._StdoutEnabled);
      } else if (name.equals("StdoutFormat")) {
         return this._StdoutFormat;
      } else if (name.equals("StdoutLogStack")) {
         return new Boolean(this._StdoutLogStack);
      } else if (name.equals("StdoutSeverityLevel")) {
         return new Integer(this._StdoutSeverityLevel);
      } else if (name.equals("StuckThreadMaxTime")) {
         return new Integer(this._StuckThreadMaxTime);
      } else if (name.equals("StuckThreadTimerInterval")) {
         return new Integer(this._StuckThreadTimerInterval);
      } else if (name.equals("SystemThreadPoolSize")) {
         return new Integer(this._SystemThreadPoolSize);
      } else if (name.equals("T3ClientAbbrevTableSize")) {
         return new Integer(this._T3ClientAbbrevTableSize);
      } else if (name.equals("T3ServerAbbrevTableSize")) {
         return new Integer(this._T3ServerAbbrevTableSize);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("ThreadPoolPercentSocketReaders")) {
         return new Integer(this._ThreadPoolPercentSocketReaders);
      } else if (name.equals("ThreadPoolSize")) {
         return new Integer(this._ThreadPoolSize);
      } else if (name.equals("TimedOutRefIsolationTime")) {
         return new Long(this._TimedOutRefIsolationTime);
      } else if (name.equals("TracingEnabled")) {
         return new Boolean(this._TracingEnabled);
      } else if (name.equals("Use81StyleExecuteQueues")) {
         return new Boolean(this._Use81StyleExecuteQueues);
      } else if (name.equals("UseConcurrentQueueForRequestManager")) {
         return new Boolean(this._UseConcurrentQueueForRequestManager);
      } else if (name.equals("UseDetailedThreadName")) {
         return new Boolean(this._UseDetailedThreadName);
      } else if (name.equals("UseEnhancedIncrementAdvisor")) {
         return new Boolean(this._UseEnhancedIncrementAdvisor);
      } else if (name.equals("UseEnhancedPriorityQueueForRequestManager")) {
         return new Boolean(this._UseEnhancedPriorityQueueForRequestManager);
      } else if (name.equals("UseIIOPLocateRequest")) {
         return new Boolean(this._UseIIOPLocateRequest);
      } else if (name.equals("ValidProtocols")) {
         return this._ValidProtocols;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("log")) {
                  return 55;
               }

               if (s.equals("ssl")) {
                  return 53;
               }

               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("iiop")) {
                  return 54;
               }

               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 35:
            case 39:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            default:
               break;
            case 8:
               if (s.equals("mtu-size")) {
                  return 70;
               }
               break;
            case 11:
               if (s.equals("muxer-class")) {
                  return 22;
               }
               break;
            case 12:
               if (s.equals("kernel-debug")) {
                  return 51;
               }
               break;
            case 13:
               if (s.equals("execute-queue")) {
                  return 62;
               }

               if (s.equals("period-length")) {
                  return 47;
               }

               if (s.equals("stdout-format")) {
                  return 64;
               }
               break;
            case 14:
               if (s.equals("socket-readers")) {
                  return 23;
               }

               if (s.equals("stdout-enabled")) {
                  return 56;
               }
               break;
            case 15:
               if (s.equals("connect-timeout")) {
                  return 39;
               }

               if (s.equals("tracing-enabled")) {
                  return 68;
               }

               if (s.equals("valid-protocols")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("default-protocol")) {
                  return 12;
               }

               if (s.equals("max-message-size")) {
                  return 29;
               }

               if (s.equals("response-timeout")) {
                  return 50;
               }

               if (s.equals("thread-pool-size")) {
                  return 15;
               }

               if (s.equals("nativeio-enabled")) {
                  return 20;
               }

               if (s.equals("outbound-enabled")) {
                  return 27;
               }

               if (s.equals("stdout-log-stack")) {
                  return 65;
               }
               break;
            case 17:
               if (s.equals("iiop-tx-mechanism")) {
                  return 37;
               }

               if (s.equals("rjvm-idle-timeout")) {
                  return 49;
               }

               if (s.equals("dev-poll-disabled")) {
                  return 21;
               }
               break;
            case 18:
               if (s.equals("maxt3-message-size")) {
                  return 30;
               }

               if (s.equals("reversedns-allowed")) {
                  return 11;
               }
               break;
            case 19:
               if (s.equals("maxcom-message-size")) {
                  return 33;
               }

               if (s.equals("max-open-sock-count")) {
                  return 63;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("jms-thread-pool-size")) {
                  return 19;
               }

               if (s.equals("maxhttp-message-size")) {
                  return 32;
               }

               if (s.equals("maxiiop-message-size")) {
                  return 34;
               }

               if (s.equals("stdout-debug-enabled")) {
                  return 58;
               }
               break;
            case 21:
               if (s.equals("stdout-severity-level")) {
                  return 57;
               }

               if (s.equals("stuck-thread-max-time")) {
                  return 66;
               }
               break;
            case 22:
               if (s.equals("complete-write-timeout")) {
                  return 81;
               }

               if (s.equals("useiiop-locate-request")) {
                  return 36;
               }
               break;
            case 23:
               if (s.equals("administration-protocol")) {
                  return 14;
               }

               if (s.equals("default-secure-protocol")) {
                  return 13;
               }

               if (s.equals("idle-connection-timeout")) {
                  return 44;
               }

               if (s.equals("system-thread-pool-size")) {
                  return 16;
               }

               if (s.equals("gathered-writes-enabled")) {
                  return 77;
               }

               if (s.equals("scattered-reads-enabled")) {
                  return 78;
               }
               break;
            case 24:
               if (s.equals("complete-message-timeout")) {
                  return 40;
               }

               if (s.equals("use-detailed-thread-name")) {
                  return 82;
               }
               break;
            case 25:
               if (s.equals("defaultgiop-minor-version")) {
                  return 35;
               }
               break;
            case 26:
               if (s.equals("completet3-message-timeout")) {
                  return 41;
               }

               if (s.equals("idle-periods-until-timeout")) {
                  return 48;
               }

               if (s.equals("use81-style-execute-queues")) {
                  return 74;
               }

               if (s.equals("eager-thread-local-cleanup")) {
                  return 86;
               }
               break;
            case 27:
               if (s.equals("completecom-message-timeout")) {
                  return 43;
               }

               if (s.equals("idleiiop-connection-timeout")) {
                  return 45;
               }

               if (s.equals("stuck-thread-timer-interval")) {
                  return 67;
               }

               if (s.equals("t3-client-abbrev-table-size")) {
                  return 75;
               }

               if (s.equals("t3-server-abbrev-table-size")) {
                  return 76;
               }
               break;
            case 28:
               if (s.equals("completehttp-message-timeout")) {
                  return 42;
               }

               if (s.equals("completeiiop-message-timeout")) {
                  return 46;
               }

               if (s.equals("iiop-location-forward-policy")) {
                  return 38;
               }

               if (s.equals("timed-out-ref-isolation-time")) {
                  return 73;
               }

               if (s.equals("outbound-private-key-enabled")) {
                  return 28;
               }
               break;
            case 29:
               if (s.equals("log-remote-exceptions-enabled")) {
                  return 59;
               }
               break;
            case 30:
               if (s.equals("dgc-idle-periods-until-timeout")) {
                  return 52;
               }

               if (s.equals("instrument-stack-trace-enabled")) {
                  return 60;
               }

               if (s.equals("use-enhanced-increment-advisor")) {
                  return 85;
               }
               break;
            case 31:
               if (s.equals("print-stack-trace-in-production")) {
                  return 61;
               }

               if (s.equals("isolate-partition-thread-locals")) {
                  return 87;
               }
               break;
            case 32:
               if (s.equals("self-tuning-thread-pool-size-max")) {
                  return 18;
               }

               if (s.equals("self-tuning-thread-pool-size-min")) {
                  return 17;
               }

               if (s.equals("socket-reader-timeout-max-millis")) {
                  return 26;
               }

               if (s.equals("socket-reader-timeout-min-millis")) {
                  return 25;
               }

               if (s.equals("socket-buffer-size-as-chunk-size")) {
                  return 31;
               }
               break;
            case 33:
               if (s.equals("messaging-bridge-thread-pool-size")) {
                  return 69;
               }

               if (s.equals("refresh-client-runtime-descriptor")) {
                  return 72;
               }
               break;
            case 34:
               if (s.equals("thread-pool-percent-socket-readers")) {
                  return 24;
               }
               break;
            case 36:
               if (s.equals("load-stub-using-context-class-loader")) {
                  return 71;
               }
               break;
            case 37:
               if (s.equals("add-work-manager-threads-by-cpu-count")) {
                  return 79;
               }
               break;
            case 38:
               if (s.equals("allow-shrinking-priority-request-queue")) {
                  return 84;
               }
               break;
            case 40:
               if (s.equals("use-concurrent-queue-for-request-manager")) {
                  return 80;
               }
               break;
            case 47:
               if (s.equals("use-enhanced-priority-queue-for-request-manager")) {
                  return 83;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 53:
               return new SSLMBeanImpl.SchemaHelper2();
            case 54:
               return new IIOPMBeanImpl.SchemaHelper2();
            case 55:
               return new LogMBeanImpl.SchemaHelper2();
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            default:
               return super.getSchemaHelper(propIndex);
            case 62:
               return new ExecuteQueueMBeanImpl.SchemaHelper2();
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
               return "valid-protocols";
            case 11:
               return "reversedns-allowed";
            case 12:
               return "default-protocol";
            case 13:
               return "default-secure-protocol";
            case 14:
               return "administration-protocol";
            case 15:
               return "thread-pool-size";
            case 16:
               return "system-thread-pool-size";
            case 17:
               return "self-tuning-thread-pool-size-min";
            case 18:
               return "self-tuning-thread-pool-size-max";
            case 19:
               return "jms-thread-pool-size";
            case 20:
               return "nativeio-enabled";
            case 21:
               return "dev-poll-disabled";
            case 22:
               return "muxer-class";
            case 23:
               return "socket-readers";
            case 24:
               return "thread-pool-percent-socket-readers";
            case 25:
               return "socket-reader-timeout-min-millis";
            case 26:
               return "socket-reader-timeout-max-millis";
            case 27:
               return "outbound-enabled";
            case 28:
               return "outbound-private-key-enabled";
            case 29:
               return "max-message-size";
            case 30:
               return "maxt3-message-size";
            case 31:
               return "socket-buffer-size-as-chunk-size";
            case 32:
               return "maxhttp-message-size";
            case 33:
               return "maxcom-message-size";
            case 34:
               return "maxiiop-message-size";
            case 35:
               return "defaultgiop-minor-version";
            case 36:
               return "useiiop-locate-request";
            case 37:
               return "iiop-tx-mechanism";
            case 38:
               return "iiop-location-forward-policy";
            case 39:
               return "connect-timeout";
            case 40:
               return "complete-message-timeout";
            case 41:
               return "completet3-message-timeout";
            case 42:
               return "completehttp-message-timeout";
            case 43:
               return "completecom-message-timeout";
            case 44:
               return "idle-connection-timeout";
            case 45:
               return "idleiiop-connection-timeout";
            case 46:
               return "completeiiop-message-timeout";
            case 47:
               return "period-length";
            case 48:
               return "idle-periods-until-timeout";
            case 49:
               return "rjvm-idle-timeout";
            case 50:
               return "response-timeout";
            case 51:
               return "kernel-debug";
            case 52:
               return "dgc-idle-periods-until-timeout";
            case 53:
               return "ssl";
            case 54:
               return "iiop";
            case 55:
               return "log";
            case 56:
               return "stdout-enabled";
            case 57:
               return "stdout-severity-level";
            case 58:
               return "stdout-debug-enabled";
            case 59:
               return "log-remote-exceptions-enabled";
            case 60:
               return "instrument-stack-trace-enabled";
            case 61:
               return "print-stack-trace-in-production";
            case 62:
               return "execute-queue";
            case 63:
               return "max-open-sock-count";
            case 64:
               return "stdout-format";
            case 65:
               return "stdout-log-stack";
            case 66:
               return "stuck-thread-max-time";
            case 67:
               return "stuck-thread-timer-interval";
            case 68:
               return "tracing-enabled";
            case 69:
               return "messaging-bridge-thread-pool-size";
            case 70:
               return "mtu-size";
            case 71:
               return "load-stub-using-context-class-loader";
            case 72:
               return "refresh-client-runtime-descriptor";
            case 73:
               return "timed-out-ref-isolation-time";
            case 74:
               return "use81-style-execute-queues";
            case 75:
               return "t3-client-abbrev-table-size";
            case 76:
               return "t3-server-abbrev-table-size";
            case 77:
               return "gathered-writes-enabled";
            case 78:
               return "scattered-reads-enabled";
            case 79:
               return "add-work-manager-threads-by-cpu-count";
            case 80:
               return "use-concurrent-queue-for-request-manager";
            case 81:
               return "complete-write-timeout";
            case 82:
               return "use-detailed-thread-name";
            case 83:
               return "use-enhanced-priority-queue-for-request-manager";
            case 84:
               return "allow-shrinking-priority-request-queue";
            case 85:
               return "use-enhanced-increment-advisor";
            case 86:
               return "eager-thread-local-cleanup";
            case 87:
               return "isolate-partition-thread-locals";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 62:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 53:
               return true;
            case 54:
               return true;
            case 55:
               return true;
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            default:
               return super.isBean(propIndex);
            case 62:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 31:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
      private KernelMBeanImpl bean;

      protected Helper(KernelMBeanImpl bean) {
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
               return "ValidProtocols";
            case 11:
               return "ReverseDNSAllowed";
            case 12:
               return "DefaultProtocol";
            case 13:
               return "DefaultSecureProtocol";
            case 14:
               return "AdministrationProtocol";
            case 15:
               return "ThreadPoolSize";
            case 16:
               return "SystemThreadPoolSize";
            case 17:
               return "SelfTuningThreadPoolSizeMin";
            case 18:
               return "SelfTuningThreadPoolSizeMax";
            case 19:
               return "JMSThreadPoolSize";
            case 20:
               return "NativeIOEnabled";
            case 21:
               return "DevPollDisabled";
            case 22:
               return "MuxerClass";
            case 23:
               return "SocketReaders";
            case 24:
               return "ThreadPoolPercentSocketReaders";
            case 25:
               return "SocketReaderTimeoutMinMillis";
            case 26:
               return "SocketReaderTimeoutMaxMillis";
            case 27:
               return "OutboundEnabled";
            case 28:
               return "OutboundPrivateKeyEnabled";
            case 29:
               return "MaxMessageSize";
            case 30:
               return "MaxT3MessageSize";
            case 31:
               return "SocketBufferSizeAsChunkSize";
            case 32:
               return "MaxHTTPMessageSize";
            case 33:
               return "MaxCOMMessageSize";
            case 34:
               return "MaxIIOPMessageSize";
            case 35:
               return "DefaultGIOPMinorVersion";
            case 36:
               return "UseIIOPLocateRequest";
            case 37:
               return "IIOPTxMechanism";
            case 38:
               return "IIOPLocationForwardPolicy";
            case 39:
               return "ConnectTimeout";
            case 40:
               return "CompleteMessageTimeout";
            case 41:
               return "CompleteT3MessageTimeout";
            case 42:
               return "CompleteHTTPMessageTimeout";
            case 43:
               return "CompleteCOMMessageTimeout";
            case 44:
               return "IdleConnectionTimeout";
            case 45:
               return "IdleIIOPConnectionTimeout";
            case 46:
               return "CompleteIIOPMessageTimeout";
            case 47:
               return "PeriodLength";
            case 48:
               return "IdlePeriodsUntilTimeout";
            case 49:
               return "RjvmIdleTimeout";
            case 50:
               return "ResponseTimeout";
            case 51:
               return "KernelDebug";
            case 52:
               return "DGCIdlePeriodsUntilTimeout";
            case 53:
               return "SSL";
            case 54:
               return "IIOP";
            case 55:
               return "Log";
            case 56:
               return "StdoutEnabled";
            case 57:
               return "StdoutSeverityLevel";
            case 58:
               return "StdoutDebugEnabled";
            case 59:
               return "LogRemoteExceptionsEnabled";
            case 60:
               return "InstrumentStackTraceEnabled";
            case 61:
               return "PrintStackTraceInProduction";
            case 62:
               return "ExecuteQueues";
            case 63:
               return "MaxOpenSockCount";
            case 64:
               return "StdoutFormat";
            case 65:
               return "StdoutLogStack";
            case 66:
               return "StuckThreadMaxTime";
            case 67:
               return "StuckThreadTimerInterval";
            case 68:
               return "TracingEnabled";
            case 69:
               return "MessagingBridgeThreadPoolSize";
            case 70:
               return "MTUSize";
            case 71:
               return "LoadStubUsingContextClassLoader";
            case 72:
               return "RefreshClientRuntimeDescriptor";
            case 73:
               return "TimedOutRefIsolationTime";
            case 74:
               return "Use81StyleExecuteQueues";
            case 75:
               return "T3ClientAbbrevTableSize";
            case 76:
               return "T3ServerAbbrevTableSize";
            case 77:
               return "GatheredWritesEnabled";
            case 78:
               return "ScatteredReadsEnabled";
            case 79:
               return "AddWorkManagerThreadsByCpuCount";
            case 80:
               return "UseConcurrentQueueForRequestManager";
            case 81:
               return "CompleteWriteTimeout";
            case 82:
               return "UseDetailedThreadName";
            case 83:
               return "UseEnhancedPriorityQueueForRequestManager";
            case 84:
               return "AllowShrinkingPriorityRequestQueue";
            case 85:
               return "UseEnhancedIncrementAdvisor";
            case 86:
               return "EagerThreadLocalCleanup";
            case 87:
               return "IsolatePartitionThreadLocals";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdministrationProtocol")) {
            return 14;
         } else if (propName.equals("CompleteCOMMessageTimeout")) {
            return 43;
         } else if (propName.equals("CompleteHTTPMessageTimeout")) {
            return 42;
         } else if (propName.equals("CompleteIIOPMessageTimeout")) {
            return 46;
         } else if (propName.equals("CompleteMessageTimeout")) {
            return 40;
         } else if (propName.equals("CompleteT3MessageTimeout")) {
            return 41;
         } else if (propName.equals("CompleteWriteTimeout")) {
            return 81;
         } else if (propName.equals("ConnectTimeout")) {
            return 39;
         } else if (propName.equals("DGCIdlePeriodsUntilTimeout")) {
            return 52;
         } else if (propName.equals("DefaultGIOPMinorVersion")) {
            return 35;
         } else if (propName.equals("DefaultProtocol")) {
            return 12;
         } else if (propName.equals("DefaultSecureProtocol")) {
            return 13;
         } else if (propName.equals("ExecuteQueues")) {
            return 62;
         } else if (propName.equals("IIOP")) {
            return 54;
         } else if (propName.equals("IIOPLocationForwardPolicy")) {
            return 38;
         } else if (propName.equals("IIOPTxMechanism")) {
            return 37;
         } else if (propName.equals("IdleConnectionTimeout")) {
            return 44;
         } else if (propName.equals("IdleIIOPConnectionTimeout")) {
            return 45;
         } else if (propName.equals("IdlePeriodsUntilTimeout")) {
            return 48;
         } else if (propName.equals("JMSThreadPoolSize")) {
            return 19;
         } else if (propName.equals("KernelDebug")) {
            return 51;
         } else if (propName.equals("LoadStubUsingContextClassLoader")) {
            return 71;
         } else if (propName.equals("Log")) {
            return 55;
         } else if (propName.equals("MTUSize")) {
            return 70;
         } else if (propName.equals("MaxCOMMessageSize")) {
            return 33;
         } else if (propName.equals("MaxHTTPMessageSize")) {
            return 32;
         } else if (propName.equals("MaxIIOPMessageSize")) {
            return 34;
         } else if (propName.equals("MaxMessageSize")) {
            return 29;
         } else if (propName.equals("MaxOpenSockCount")) {
            return 63;
         } else if (propName.equals("MaxT3MessageSize")) {
            return 30;
         } else if (propName.equals("MessagingBridgeThreadPoolSize")) {
            return 69;
         } else if (propName.equals("MuxerClass")) {
            return 22;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PeriodLength")) {
            return 47;
         } else if (propName.equals("PrintStackTraceInProduction")) {
            return 61;
         } else if (propName.equals("RefreshClientRuntimeDescriptor")) {
            return 72;
         } else if (propName.equals("ResponseTimeout")) {
            return 50;
         } else if (propName.equals("RjvmIdleTimeout")) {
            return 49;
         } else if (propName.equals("SSL")) {
            return 53;
         } else if (propName.equals("SelfTuningThreadPoolSizeMax")) {
            return 18;
         } else if (propName.equals("SelfTuningThreadPoolSizeMin")) {
            return 17;
         } else if (propName.equals("SocketReaderTimeoutMaxMillis")) {
            return 26;
         } else if (propName.equals("SocketReaderTimeoutMinMillis")) {
            return 25;
         } else if (propName.equals("SocketReaders")) {
            return 23;
         } else if (propName.equals("StdoutFormat")) {
            return 64;
         } else if (propName.equals("StdoutSeverityLevel")) {
            return 57;
         } else if (propName.equals("StuckThreadMaxTime")) {
            return 66;
         } else if (propName.equals("StuckThreadTimerInterval")) {
            return 67;
         } else if (propName.equals("SystemThreadPoolSize")) {
            return 16;
         } else if (propName.equals("T3ClientAbbrevTableSize")) {
            return 75;
         } else if (propName.equals("T3ServerAbbrevTableSize")) {
            return 76;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("ThreadPoolPercentSocketReaders")) {
            return 24;
         } else if (propName.equals("ThreadPoolSize")) {
            return 15;
         } else if (propName.equals("TimedOutRefIsolationTime")) {
            return 73;
         } else if (propName.equals("TracingEnabled")) {
            return 68;
         } else if (propName.equals("Use81StyleExecuteQueues")) {
            return 74;
         } else if (propName.equals("UseIIOPLocateRequest")) {
            return 36;
         } else if (propName.equals("ValidProtocols")) {
            return 10;
         } else if (propName.equals("AddWorkManagerThreadsByCpuCount")) {
            return 79;
         } else if (propName.equals("AllowShrinkingPriorityRequestQueue")) {
            return 84;
         } else if (propName.equals("DevPollDisabled")) {
            return 21;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("EagerThreadLocalCleanup")) {
            return 86;
         } else if (propName.equals("GatheredWritesEnabled")) {
            return 77;
         } else if (propName.equals("InstrumentStackTraceEnabled")) {
            return 60;
         } else if (propName.equals("IsolatePartitionThreadLocals")) {
            return 87;
         } else if (propName.equals("LogRemoteExceptionsEnabled")) {
            return 59;
         } else if (propName.equals("NativeIOEnabled")) {
            return 20;
         } else if (propName.equals("OutboundEnabled")) {
            return 27;
         } else if (propName.equals("OutboundPrivateKeyEnabled")) {
            return 28;
         } else if (propName.equals("ReverseDNSAllowed")) {
            return 11;
         } else if (propName.equals("ScatteredReadsEnabled")) {
            return 78;
         } else if (propName.equals("SocketBufferSizeAsChunkSize")) {
            return 31;
         } else if (propName.equals("StdoutDebugEnabled")) {
            return 58;
         } else if (propName.equals("StdoutEnabled")) {
            return 56;
         } else if (propName.equals("StdoutLogStack")) {
            return 65;
         } else if (propName.equals("UseConcurrentQueueForRequestManager")) {
            return 80;
         } else if (propName.equals("UseDetailedThreadName")) {
            return 82;
         } else if (propName.equals("UseEnhancedIncrementAdvisor")) {
            return 85;
         } else {
            return propName.equals("UseEnhancedPriorityQueueForRequestManager") ? 83 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getExecuteQueues()));
         if (this.bean.getIIOP() != null) {
            iterators.add(new ArrayIterator(new IIOPMBean[]{this.bean.getIIOP()}));
         }

         if (this.bean.getLog() != null) {
            iterators.add(new ArrayIterator(new LogMBean[]{this.bean.getLog()}));
         }

         if (this.bean.getSSL() != null) {
            iterators.add(new ArrayIterator(new SSLMBean[]{this.bean.getSSL()}));
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
            if (this.bean.isAdministrationProtocolSet()) {
               buf.append("AdministrationProtocol");
               buf.append(String.valueOf(this.bean.getAdministrationProtocol()));
            }

            if (this.bean.isCompleteCOMMessageTimeoutSet()) {
               buf.append("CompleteCOMMessageTimeout");
               buf.append(String.valueOf(this.bean.getCompleteCOMMessageTimeout()));
            }

            if (this.bean.isCompleteHTTPMessageTimeoutSet()) {
               buf.append("CompleteHTTPMessageTimeout");
               buf.append(String.valueOf(this.bean.getCompleteHTTPMessageTimeout()));
            }

            if (this.bean.isCompleteIIOPMessageTimeoutSet()) {
               buf.append("CompleteIIOPMessageTimeout");
               buf.append(String.valueOf(this.bean.getCompleteIIOPMessageTimeout()));
            }

            if (this.bean.isCompleteMessageTimeoutSet()) {
               buf.append("CompleteMessageTimeout");
               buf.append(String.valueOf(this.bean.getCompleteMessageTimeout()));
            }

            if (this.bean.isCompleteT3MessageTimeoutSet()) {
               buf.append("CompleteT3MessageTimeout");
               buf.append(String.valueOf(this.bean.getCompleteT3MessageTimeout()));
            }

            if (this.bean.isCompleteWriteTimeoutSet()) {
               buf.append("CompleteWriteTimeout");
               buf.append(String.valueOf(this.bean.getCompleteWriteTimeout()));
            }

            if (this.bean.isConnectTimeoutSet()) {
               buf.append("ConnectTimeout");
               buf.append(String.valueOf(this.bean.getConnectTimeout()));
            }

            if (this.bean.isDGCIdlePeriodsUntilTimeoutSet()) {
               buf.append("DGCIdlePeriodsUntilTimeout");
               buf.append(String.valueOf(this.bean.getDGCIdlePeriodsUntilTimeout()));
            }

            if (this.bean.isDefaultGIOPMinorVersionSet()) {
               buf.append("DefaultGIOPMinorVersion");
               buf.append(String.valueOf(this.bean.getDefaultGIOPMinorVersion()));
            }

            if (this.bean.isDefaultProtocolSet()) {
               buf.append("DefaultProtocol");
               buf.append(String.valueOf(this.bean.getDefaultProtocol()));
            }

            if (this.bean.isDefaultSecureProtocolSet()) {
               buf.append("DefaultSecureProtocol");
               buf.append(String.valueOf(this.bean.getDefaultSecureProtocol()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getExecuteQueues().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getExecuteQueues()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getIIOP());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIIOPLocationForwardPolicySet()) {
               buf.append("IIOPLocationForwardPolicy");
               buf.append(String.valueOf(this.bean.getIIOPLocationForwardPolicy()));
            }

            if (this.bean.isIIOPTxMechanismSet()) {
               buf.append("IIOPTxMechanism");
               buf.append(String.valueOf(this.bean.getIIOPTxMechanism()));
            }

            if (this.bean.isIdleConnectionTimeoutSet()) {
               buf.append("IdleConnectionTimeout");
               buf.append(String.valueOf(this.bean.getIdleConnectionTimeout()));
            }

            if (this.bean.isIdleIIOPConnectionTimeoutSet()) {
               buf.append("IdleIIOPConnectionTimeout");
               buf.append(String.valueOf(this.bean.getIdleIIOPConnectionTimeout()));
            }

            if (this.bean.isIdlePeriodsUntilTimeoutSet()) {
               buf.append("IdlePeriodsUntilTimeout");
               buf.append(String.valueOf(this.bean.getIdlePeriodsUntilTimeout()));
            }

            if (this.bean.isJMSThreadPoolSizeSet()) {
               buf.append("JMSThreadPoolSize");
               buf.append(String.valueOf(this.bean.getJMSThreadPoolSize()));
            }

            if (this.bean.isKernelDebugSet()) {
               buf.append("KernelDebug");
               buf.append(String.valueOf(this.bean.getKernelDebug()));
            }

            if (this.bean.isLoadStubUsingContextClassLoaderSet()) {
               buf.append("LoadStubUsingContextClassLoader");
               buf.append(String.valueOf(this.bean.getLoadStubUsingContextClassLoader()));
            }

            childValue = this.computeChildHashValue(this.bean.getLog());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMTUSizeSet()) {
               buf.append("MTUSize");
               buf.append(String.valueOf(this.bean.getMTUSize()));
            }

            if (this.bean.isMaxCOMMessageSizeSet()) {
               buf.append("MaxCOMMessageSize");
               buf.append(String.valueOf(this.bean.getMaxCOMMessageSize()));
            }

            if (this.bean.isMaxHTTPMessageSizeSet()) {
               buf.append("MaxHTTPMessageSize");
               buf.append(String.valueOf(this.bean.getMaxHTTPMessageSize()));
            }

            if (this.bean.isMaxIIOPMessageSizeSet()) {
               buf.append("MaxIIOPMessageSize");
               buf.append(String.valueOf(this.bean.getMaxIIOPMessageSize()));
            }

            if (this.bean.isMaxMessageSizeSet()) {
               buf.append("MaxMessageSize");
               buf.append(String.valueOf(this.bean.getMaxMessageSize()));
            }

            if (this.bean.isMaxOpenSockCountSet()) {
               buf.append("MaxOpenSockCount");
               buf.append(String.valueOf(this.bean.getMaxOpenSockCount()));
            }

            if (this.bean.isMaxT3MessageSizeSet()) {
               buf.append("MaxT3MessageSize");
               buf.append(String.valueOf(this.bean.getMaxT3MessageSize()));
            }

            if (this.bean.isMessagingBridgeThreadPoolSizeSet()) {
               buf.append("MessagingBridgeThreadPoolSize");
               buf.append(String.valueOf(this.bean.getMessagingBridgeThreadPoolSize()));
            }

            if (this.bean.isMuxerClassSet()) {
               buf.append("MuxerClass");
               buf.append(String.valueOf(this.bean.getMuxerClass()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPeriodLengthSet()) {
               buf.append("PeriodLength");
               buf.append(String.valueOf(this.bean.getPeriodLength()));
            }

            if (this.bean.isPrintStackTraceInProductionSet()) {
               buf.append("PrintStackTraceInProduction");
               buf.append(String.valueOf(this.bean.getPrintStackTraceInProduction()));
            }

            if (this.bean.isRefreshClientRuntimeDescriptorSet()) {
               buf.append("RefreshClientRuntimeDescriptor");
               buf.append(String.valueOf(this.bean.getRefreshClientRuntimeDescriptor()));
            }

            if (this.bean.isResponseTimeoutSet()) {
               buf.append("ResponseTimeout");
               buf.append(String.valueOf(this.bean.getResponseTimeout()));
            }

            if (this.bean.isRjvmIdleTimeoutSet()) {
               buf.append("RjvmIdleTimeout");
               buf.append(String.valueOf(this.bean.getRjvmIdleTimeout()));
            }

            childValue = this.computeChildHashValue(this.bean.getSSL());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSelfTuningThreadPoolSizeMaxSet()) {
               buf.append("SelfTuningThreadPoolSizeMax");
               buf.append(String.valueOf(this.bean.getSelfTuningThreadPoolSizeMax()));
            }

            if (this.bean.isSelfTuningThreadPoolSizeMinSet()) {
               buf.append("SelfTuningThreadPoolSizeMin");
               buf.append(String.valueOf(this.bean.getSelfTuningThreadPoolSizeMin()));
            }

            if (this.bean.isSocketReaderTimeoutMaxMillisSet()) {
               buf.append("SocketReaderTimeoutMaxMillis");
               buf.append(String.valueOf(this.bean.getSocketReaderTimeoutMaxMillis()));
            }

            if (this.bean.isSocketReaderTimeoutMinMillisSet()) {
               buf.append("SocketReaderTimeoutMinMillis");
               buf.append(String.valueOf(this.bean.getSocketReaderTimeoutMinMillis()));
            }

            if (this.bean.isSocketReadersSet()) {
               buf.append("SocketReaders");
               buf.append(String.valueOf(this.bean.getSocketReaders()));
            }

            if (this.bean.isStdoutFormatSet()) {
               buf.append("StdoutFormat");
               buf.append(String.valueOf(this.bean.getStdoutFormat()));
            }

            if (this.bean.isStdoutSeverityLevelSet()) {
               buf.append("StdoutSeverityLevel");
               buf.append(String.valueOf(this.bean.getStdoutSeverityLevel()));
            }

            if (this.bean.isStuckThreadMaxTimeSet()) {
               buf.append("StuckThreadMaxTime");
               buf.append(String.valueOf(this.bean.getStuckThreadMaxTime()));
            }

            if (this.bean.isStuckThreadTimerIntervalSet()) {
               buf.append("StuckThreadTimerInterval");
               buf.append(String.valueOf(this.bean.getStuckThreadTimerInterval()));
            }

            if (this.bean.isSystemThreadPoolSizeSet()) {
               buf.append("SystemThreadPoolSize");
               buf.append(String.valueOf(this.bean.getSystemThreadPoolSize()));
            }

            if (this.bean.isT3ClientAbbrevTableSizeSet()) {
               buf.append("T3ClientAbbrevTableSize");
               buf.append(String.valueOf(this.bean.getT3ClientAbbrevTableSize()));
            }

            if (this.bean.isT3ServerAbbrevTableSizeSet()) {
               buf.append("T3ServerAbbrevTableSize");
               buf.append(String.valueOf(this.bean.getT3ServerAbbrevTableSize()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isThreadPoolPercentSocketReadersSet()) {
               buf.append("ThreadPoolPercentSocketReaders");
               buf.append(String.valueOf(this.bean.getThreadPoolPercentSocketReaders()));
            }

            if (this.bean.isThreadPoolSizeSet()) {
               buf.append("ThreadPoolSize");
               buf.append(String.valueOf(this.bean.getThreadPoolSize()));
            }

            if (this.bean.isTimedOutRefIsolationTimeSet()) {
               buf.append("TimedOutRefIsolationTime");
               buf.append(String.valueOf(this.bean.getTimedOutRefIsolationTime()));
            }

            if (this.bean.isTracingEnabledSet()) {
               buf.append("TracingEnabled");
               buf.append(String.valueOf(this.bean.getTracingEnabled()));
            }

            if (this.bean.isUse81StyleExecuteQueuesSet()) {
               buf.append("Use81StyleExecuteQueues");
               buf.append(String.valueOf(this.bean.getUse81StyleExecuteQueues()));
            }

            if (this.bean.isUseIIOPLocateRequestSet()) {
               buf.append("UseIIOPLocateRequest");
               buf.append(String.valueOf(this.bean.getUseIIOPLocateRequest()));
            }

            if (this.bean.isValidProtocolsSet()) {
               buf.append("ValidProtocols");
               buf.append(String.valueOf(this.bean.getValidProtocols()));
            }

            if (this.bean.isAddWorkManagerThreadsByCpuCountSet()) {
               buf.append("AddWorkManagerThreadsByCpuCount");
               buf.append(String.valueOf(this.bean.isAddWorkManagerThreadsByCpuCount()));
            }

            if (this.bean.isAllowShrinkingPriorityRequestQueueSet()) {
               buf.append("AllowShrinkingPriorityRequestQueue");
               buf.append(String.valueOf(this.bean.isAllowShrinkingPriorityRequestQueue()));
            }

            if (this.bean.isDevPollDisabledSet()) {
               buf.append("DevPollDisabled");
               buf.append(String.valueOf(this.bean.isDevPollDisabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isEagerThreadLocalCleanupSet()) {
               buf.append("EagerThreadLocalCleanup");
               buf.append(String.valueOf(this.bean.isEagerThreadLocalCleanup()));
            }

            if (this.bean.isGatheredWritesEnabledSet()) {
               buf.append("GatheredWritesEnabled");
               buf.append(String.valueOf(this.bean.isGatheredWritesEnabled()));
            }

            if (this.bean.isInstrumentStackTraceEnabledSet()) {
               buf.append("InstrumentStackTraceEnabled");
               buf.append(String.valueOf(this.bean.isInstrumentStackTraceEnabled()));
            }

            if (this.bean.isIsolatePartitionThreadLocalsSet()) {
               buf.append("IsolatePartitionThreadLocals");
               buf.append(String.valueOf(this.bean.isIsolatePartitionThreadLocals()));
            }

            if (this.bean.isLogRemoteExceptionsEnabledSet()) {
               buf.append("LogRemoteExceptionsEnabled");
               buf.append(String.valueOf(this.bean.isLogRemoteExceptionsEnabled()));
            }

            if (this.bean.isNativeIOEnabledSet()) {
               buf.append("NativeIOEnabled");
               buf.append(String.valueOf(this.bean.isNativeIOEnabled()));
            }

            if (this.bean.isOutboundEnabledSet()) {
               buf.append("OutboundEnabled");
               buf.append(String.valueOf(this.bean.isOutboundEnabled()));
            }

            if (this.bean.isOutboundPrivateKeyEnabledSet()) {
               buf.append("OutboundPrivateKeyEnabled");
               buf.append(String.valueOf(this.bean.isOutboundPrivateKeyEnabled()));
            }

            if (this.bean.isReverseDNSAllowedSet()) {
               buf.append("ReverseDNSAllowed");
               buf.append(String.valueOf(this.bean.isReverseDNSAllowed()));
            }

            if (this.bean.isScatteredReadsEnabledSet()) {
               buf.append("ScatteredReadsEnabled");
               buf.append(String.valueOf(this.bean.isScatteredReadsEnabled()));
            }

            if (this.bean.isSocketBufferSizeAsChunkSizeSet()) {
               buf.append("SocketBufferSizeAsChunkSize");
               buf.append(String.valueOf(this.bean.isSocketBufferSizeAsChunkSize()));
            }

            if (this.bean.isStdoutDebugEnabledSet()) {
               buf.append("StdoutDebugEnabled");
               buf.append(String.valueOf(this.bean.isStdoutDebugEnabled()));
            }

            if (this.bean.isStdoutEnabledSet()) {
               buf.append("StdoutEnabled");
               buf.append(String.valueOf(this.bean.isStdoutEnabled()));
            }

            if (this.bean.isStdoutLogStackSet()) {
               buf.append("StdoutLogStack");
               buf.append(String.valueOf(this.bean.isStdoutLogStack()));
            }

            if (this.bean.isUseConcurrentQueueForRequestManagerSet()) {
               buf.append("UseConcurrentQueueForRequestManager");
               buf.append(String.valueOf(this.bean.isUseConcurrentQueueForRequestManager()));
            }

            if (this.bean.isUseDetailedThreadNameSet()) {
               buf.append("UseDetailedThreadName");
               buf.append(String.valueOf(this.bean.isUseDetailedThreadName()));
            }

            if (this.bean.isUseEnhancedIncrementAdvisorSet()) {
               buf.append("UseEnhancedIncrementAdvisor");
               buf.append(String.valueOf(this.bean.isUseEnhancedIncrementAdvisor()));
            }

            if (this.bean.isUseEnhancedPriorityQueueForRequestManagerSet()) {
               buf.append("UseEnhancedPriorityQueueForRequestManager");
               buf.append(String.valueOf(this.bean.isUseEnhancedPriorityQueueForRequestManager()));
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
            KernelMBeanImpl otherTyped = (KernelMBeanImpl)other;
            this.computeDiff("AdministrationProtocol", this.bean.getAdministrationProtocol(), otherTyped.getAdministrationProtocol(), false);
            this.computeDiff("CompleteCOMMessageTimeout", this.bean.getCompleteCOMMessageTimeout(), otherTyped.getCompleteCOMMessageTimeout(), true);
            this.computeDiff("CompleteHTTPMessageTimeout", this.bean.getCompleteHTTPMessageTimeout(), otherTyped.getCompleteHTTPMessageTimeout(), true);
            this.computeDiff("CompleteIIOPMessageTimeout", this.bean.getCompleteIIOPMessageTimeout(), otherTyped.getCompleteIIOPMessageTimeout(), true);
            this.computeDiff("CompleteMessageTimeout", this.bean.getCompleteMessageTimeout(), otherTyped.getCompleteMessageTimeout(), true);
            this.computeDiff("CompleteT3MessageTimeout", this.bean.getCompleteT3MessageTimeout(), otherTyped.getCompleteT3MessageTimeout(), true);
            this.computeDiff("CompleteWriteTimeout", this.bean.getCompleteWriteTimeout(), otherTyped.getCompleteWriteTimeout(), false);
            this.computeDiff("ConnectTimeout", this.bean.getConnectTimeout(), otherTyped.getConnectTimeout(), true);
            this.computeDiff("DGCIdlePeriodsUntilTimeout", this.bean.getDGCIdlePeriodsUntilTimeout(), otherTyped.getDGCIdlePeriodsUntilTimeout(), false);
            this.computeDiff("DefaultGIOPMinorVersion", this.bean.getDefaultGIOPMinorVersion(), otherTyped.getDefaultGIOPMinorVersion(), true);
            this.computeDiff("DefaultProtocol", this.bean.getDefaultProtocol(), otherTyped.getDefaultProtocol(), false);
            this.computeDiff("DefaultSecureProtocol", this.bean.getDefaultSecureProtocol(), otherTyped.getDefaultSecureProtocol(), false);
            this.computeChildDiff("ExecuteQueues", this.bean.getExecuteQueues(), otherTyped.getExecuteQueues(), false);
            this.computeSubDiff("IIOP", this.bean.getIIOP(), otherTyped.getIIOP());
            this.computeDiff("IIOPLocationForwardPolicy", this.bean.getIIOPLocationForwardPolicy(), otherTyped.getIIOPLocationForwardPolicy(), true);
            this.computeDiff("IIOPTxMechanism", this.bean.getIIOPTxMechanism(), otherTyped.getIIOPTxMechanism(), true);
            this.computeDiff("IdleConnectionTimeout", this.bean.getIdleConnectionTimeout(), otherTyped.getIdleConnectionTimeout(), true);
            this.computeDiff("IdleIIOPConnectionTimeout", this.bean.getIdleIIOPConnectionTimeout(), otherTyped.getIdleIIOPConnectionTimeout(), true);
            this.computeDiff("IdlePeriodsUntilTimeout", this.bean.getIdlePeriodsUntilTimeout(), otherTyped.getIdlePeriodsUntilTimeout(), false);
            this.computeDiff("JMSThreadPoolSize", this.bean.getJMSThreadPoolSize(), otherTyped.getJMSThreadPoolSize(), false);
            this.computeDiff("LoadStubUsingContextClassLoader", this.bean.getLoadStubUsingContextClassLoader(), otherTyped.getLoadStubUsingContextClassLoader(), false);
            this.computeSubDiff("Log", this.bean.getLog(), otherTyped.getLog());
            this.computeDiff("MTUSize", this.bean.getMTUSize(), otherTyped.getMTUSize(), false);
            this.computeDiff("MaxCOMMessageSize", this.bean.getMaxCOMMessageSize(), otherTyped.getMaxCOMMessageSize(), true);
            this.computeDiff("MaxHTTPMessageSize", this.bean.getMaxHTTPMessageSize(), otherTyped.getMaxHTTPMessageSize(), true);
            this.computeDiff("MaxIIOPMessageSize", this.bean.getMaxIIOPMessageSize(), otherTyped.getMaxIIOPMessageSize(), true);
            this.computeDiff("MaxMessageSize", this.bean.getMaxMessageSize(), otherTyped.getMaxMessageSize(), true);
            this.computeDiff("MaxOpenSockCount", this.bean.getMaxOpenSockCount(), otherTyped.getMaxOpenSockCount(), true);
            this.computeDiff("MaxT3MessageSize", this.bean.getMaxT3MessageSize(), otherTyped.getMaxT3MessageSize(), true);
            this.computeDiff("MessagingBridgeThreadPoolSize", this.bean.getMessagingBridgeThreadPoolSize(), otherTyped.getMessagingBridgeThreadPoolSize(), false);
            this.computeDiff("MuxerClass", this.bean.getMuxerClass(), otherTyped.getMuxerClass(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PeriodLength", this.bean.getPeriodLength(), otherTyped.getPeriodLength(), false);
            this.computeDiff("PrintStackTraceInProduction", this.bean.getPrintStackTraceInProduction(), otherTyped.getPrintStackTraceInProduction(), true);
            this.computeDiff("RefreshClientRuntimeDescriptor", this.bean.getRefreshClientRuntimeDescriptor(), otherTyped.getRefreshClientRuntimeDescriptor(), false);
            this.computeDiff("ResponseTimeout", this.bean.getResponseTimeout(), otherTyped.getResponseTimeout(), false);
            this.computeDiff("RjvmIdleTimeout", this.bean.getRjvmIdleTimeout(), otherTyped.getRjvmIdleTimeout(), false);
            this.computeSubDiff("SSL", this.bean.getSSL(), otherTyped.getSSL());
            this.computeDiff("SelfTuningThreadPoolSizeMax", this.bean.getSelfTuningThreadPoolSizeMax(), otherTyped.getSelfTuningThreadPoolSizeMax(), false);
            this.computeDiff("SelfTuningThreadPoolSizeMin", this.bean.getSelfTuningThreadPoolSizeMin(), otherTyped.getSelfTuningThreadPoolSizeMin(), false);
            this.computeDiff("SocketReaderTimeoutMaxMillis", this.bean.getSocketReaderTimeoutMaxMillis(), otherTyped.getSocketReaderTimeoutMaxMillis(), true);
            this.computeDiff("SocketReaderTimeoutMinMillis", this.bean.getSocketReaderTimeoutMinMillis(), otherTyped.getSocketReaderTimeoutMinMillis(), true);
            this.computeDiff("SocketReaders", this.bean.getSocketReaders(), otherTyped.getSocketReaders(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutFormat", this.bean.getStdoutFormat(), otherTyped.getStdoutFormat(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutSeverityLevel", this.bean.getStdoutSeverityLevel(), otherTyped.getStdoutSeverityLevel(), true);
            }

            this.computeDiff("StuckThreadMaxTime", this.bean.getStuckThreadMaxTime(), otherTyped.getStuckThreadMaxTime(), false);
            this.computeDiff("StuckThreadTimerInterval", this.bean.getStuckThreadTimerInterval(), otherTyped.getStuckThreadTimerInterval(), false);
            this.computeDiff("SystemThreadPoolSize", this.bean.getSystemThreadPoolSize(), otherTyped.getSystemThreadPoolSize(), false);
            this.computeDiff("T3ClientAbbrevTableSize", this.bean.getT3ClientAbbrevTableSize(), otherTyped.getT3ClientAbbrevTableSize(), false);
            this.computeDiff("T3ServerAbbrevTableSize", this.bean.getT3ServerAbbrevTableSize(), otherTyped.getT3ServerAbbrevTableSize(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("ThreadPoolPercentSocketReaders", this.bean.getThreadPoolPercentSocketReaders(), otherTyped.getThreadPoolPercentSocketReaders(), true);
            this.computeDiff("ThreadPoolSize", this.bean.getThreadPoolSize(), otherTyped.getThreadPoolSize(), false);
            this.computeDiff("TimedOutRefIsolationTime", this.bean.getTimedOutRefIsolationTime(), otherTyped.getTimedOutRefIsolationTime(), false);
            this.computeDiff("TracingEnabled", this.bean.getTracingEnabled(), otherTyped.getTracingEnabled(), false);
            this.computeDiff("Use81StyleExecuteQueues", this.bean.getUse81StyleExecuteQueues(), otherTyped.getUse81StyleExecuteQueues(), false);
            this.computeDiff("UseIIOPLocateRequest", this.bean.getUseIIOPLocateRequest(), otherTyped.getUseIIOPLocateRequest(), true);
            this.computeDiff("ValidProtocols", this.bean.getValidProtocols(), otherTyped.getValidProtocols(), false);
            this.computeDiff("AddWorkManagerThreadsByCpuCount", this.bean.isAddWorkManagerThreadsByCpuCount(), otherTyped.isAddWorkManagerThreadsByCpuCount(), false);
            this.computeDiff("AllowShrinkingPriorityRequestQueue", this.bean.isAllowShrinkingPriorityRequestQueue(), otherTyped.isAllowShrinkingPriorityRequestQueue(), false);
            this.computeDiff("DevPollDisabled", this.bean.isDevPollDisabled(), otherTyped.isDevPollDisabled(), false);
            this.computeDiff("EagerThreadLocalCleanup", this.bean.isEagerThreadLocalCleanup(), otherTyped.isEagerThreadLocalCleanup(), false);
            this.computeDiff("GatheredWritesEnabled", this.bean.isGatheredWritesEnabled(), otherTyped.isGatheredWritesEnabled(), false);
            this.computeDiff("InstrumentStackTraceEnabled", this.bean.isInstrumentStackTraceEnabled(), otherTyped.isInstrumentStackTraceEnabled(), true);
            this.computeDiff("IsolatePartitionThreadLocals", this.bean.isIsolatePartitionThreadLocals(), otherTyped.isIsolatePartitionThreadLocals(), false);
            this.computeDiff("LogRemoteExceptionsEnabled", this.bean.isLogRemoteExceptionsEnabled(), otherTyped.isLogRemoteExceptionsEnabled(), true);
            this.computeDiff("NativeIOEnabled", this.bean.isNativeIOEnabled(), otherTyped.isNativeIOEnabled(), false);
            this.computeDiff("OutboundEnabled", this.bean.isOutboundEnabled(), otherTyped.isOutboundEnabled(), true);
            this.computeDiff("OutboundPrivateKeyEnabled", this.bean.isOutboundPrivateKeyEnabled(), otherTyped.isOutboundPrivateKeyEnabled(), true);
            this.computeDiff("ReverseDNSAllowed", this.bean.isReverseDNSAllowed(), otherTyped.isReverseDNSAllowed(), true);
            this.computeDiff("ScatteredReadsEnabled", this.bean.isScatteredReadsEnabled(), otherTyped.isScatteredReadsEnabled(), false);
            this.computeDiff("SocketBufferSizeAsChunkSize", this.bean.isSocketBufferSizeAsChunkSize(), otherTyped.isSocketBufferSizeAsChunkSize(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutDebugEnabled", this.bean.isStdoutDebugEnabled(), otherTyped.isStdoutDebugEnabled(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutEnabled", this.bean.isStdoutEnabled(), otherTyped.isStdoutEnabled(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("StdoutLogStack", this.bean.isStdoutLogStack(), otherTyped.isStdoutLogStack(), true);
            }

            this.computeDiff("UseConcurrentQueueForRequestManager", this.bean.isUseConcurrentQueueForRequestManager(), otherTyped.isUseConcurrentQueueForRequestManager(), false);
            this.computeDiff("UseDetailedThreadName", this.bean.isUseDetailedThreadName(), otherTyped.isUseDetailedThreadName(), true);
            this.computeDiff("UseEnhancedIncrementAdvisor", this.bean.isUseEnhancedIncrementAdvisor(), otherTyped.isUseEnhancedIncrementAdvisor(), false);
            this.computeDiff("UseEnhancedPriorityQueueForRequestManager", this.bean.isUseEnhancedPriorityQueueForRequestManager(), otherTyped.isUseEnhancedPriorityQueueForRequestManager(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            KernelMBeanImpl original = (KernelMBeanImpl)event.getSourceBean();
            KernelMBeanImpl proposed = (KernelMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdministrationProtocol")) {
                  original.setAdministrationProtocol(proposed.getAdministrationProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("CompleteCOMMessageTimeout")) {
                  original.setCompleteCOMMessageTimeout(proposed.getCompleteCOMMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 43);
               } else if (prop.equals("CompleteHTTPMessageTimeout")) {
                  original.setCompleteHTTPMessageTimeout(proposed.getCompleteHTTPMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("CompleteIIOPMessageTimeout")) {
                  original.setCompleteIIOPMessageTimeout(proposed.getCompleteIIOPMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 46);
               } else if (prop.equals("CompleteMessageTimeout")) {
                  original.setCompleteMessageTimeout(proposed.getCompleteMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("CompleteT3MessageTimeout")) {
                  original.setCompleteT3MessageTimeout(proposed.getCompleteT3MessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 41);
               } else if (prop.equals("CompleteWriteTimeout")) {
                  original.setCompleteWriteTimeout(proposed.getCompleteWriteTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 81);
               } else if (prop.equals("ConnectTimeout")) {
                  original.setConnectTimeout(proposed.getConnectTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("DGCIdlePeriodsUntilTimeout")) {
                  original.setDGCIdlePeriodsUntilTimeout(proposed.getDGCIdlePeriodsUntilTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (prop.equals("DefaultGIOPMinorVersion")) {
                  original.setDefaultGIOPMinorVersion(proposed.getDefaultGIOPMinorVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("DefaultProtocol")) {
                  original.setDefaultProtocol(proposed.getDefaultProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("DefaultSecureProtocol")) {
                  original.setDefaultSecureProtocol(proposed.getDefaultSecureProtocol());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ExecuteQueues")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addExecuteQueue((ExecuteQueueMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeExecuteQueue((ExecuteQueueMBean)update.getRemovedObject());
                  }

                  if (original.getExecuteQueues() == null || original.getExecuteQueues().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 62);
                  }
               } else if (prop.equals("IIOP")) {
                  if (type == 2) {
                     original.setIIOP((IIOPMBean)this.createCopy((AbstractDescriptorBean)proposed.getIIOP()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("IIOP", original.getIIOP());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 54);
               } else if (prop.equals("IIOPLocationForwardPolicy")) {
                  original.setIIOPLocationForwardPolicy(proposed.getIIOPLocationForwardPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("IIOPTxMechanism")) {
                  original.setIIOPTxMechanism(proposed.getIIOPTxMechanism());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("IdleConnectionTimeout")) {
                  original.setIdleConnectionTimeout(proposed.getIdleConnectionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 44);
               } else if (prop.equals("IdleIIOPConnectionTimeout")) {
                  original.setIdleIIOPConnectionTimeout(proposed.getIdleIIOPConnectionTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (prop.equals("IdlePeriodsUntilTimeout")) {
                  original.setIdlePeriodsUntilTimeout(proposed.getIdlePeriodsUntilTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 48);
               } else if (prop.equals("JMSThreadPoolSize")) {
                  original.setJMSThreadPoolSize(proposed.getJMSThreadPoolSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (!prop.equals("KernelDebug")) {
                  if (prop.equals("LoadStubUsingContextClassLoader")) {
                     original.setLoadStubUsingContextClassLoader(proposed.getLoadStubUsingContextClassLoader());
                     original._conditionalUnset(update.isUnsetUpdate(), 71);
                  } else if (prop.equals("Log")) {
                     if (type == 2) {
                        original.setLog((LogMBean)this.createCopy((AbstractDescriptorBean)proposed.getLog()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("Log", original.getLog());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 55);
                  } else if (prop.equals("MTUSize")) {
                     original.setMTUSize(proposed.getMTUSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 70);
                  } else if (prop.equals("MaxCOMMessageSize")) {
                     original.setMaxCOMMessageSize(proposed.getMaxCOMMessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  } else if (prop.equals("MaxHTTPMessageSize")) {
                     original.setMaxHTTPMessageSize(proposed.getMaxHTTPMessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 32);
                  } else if (prop.equals("MaxIIOPMessageSize")) {
                     original.setMaxIIOPMessageSize(proposed.getMaxIIOPMessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 34);
                  } else if (prop.equals("MaxMessageSize")) {
                     original.setMaxMessageSize(proposed.getMaxMessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
                  } else if (prop.equals("MaxOpenSockCount")) {
                     original.setMaxOpenSockCount(proposed.getMaxOpenSockCount());
                     original._conditionalUnset(update.isUnsetUpdate(), 63);
                  } else if (prop.equals("MaxT3MessageSize")) {
                     original.setMaxT3MessageSize(proposed.getMaxT3MessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  } else if (prop.equals("MessagingBridgeThreadPoolSize")) {
                     original.setMessagingBridgeThreadPoolSize(proposed.getMessagingBridgeThreadPoolSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 69);
                  } else if (prop.equals("MuxerClass")) {
                     original.setMuxerClass(proposed.getMuxerClass());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("PeriodLength")) {
                     original.setPeriodLength(proposed.getPeriodLength());
                     original._conditionalUnset(update.isUnsetUpdate(), 47);
                  } else if (prop.equals("PrintStackTraceInProduction")) {
                     original.setPrintStackTraceInProduction(proposed.getPrintStackTraceInProduction());
                     original._conditionalUnset(update.isUnsetUpdate(), 61);
                  } else if (prop.equals("RefreshClientRuntimeDescriptor")) {
                     original.setRefreshClientRuntimeDescriptor(proposed.getRefreshClientRuntimeDescriptor());
                     original._conditionalUnset(update.isUnsetUpdate(), 72);
                  } else if (prop.equals("ResponseTimeout")) {
                     original.setResponseTimeout(proposed.getResponseTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 50);
                  } else if (prop.equals("RjvmIdleTimeout")) {
                     original.setRjvmIdleTimeout(proposed.getRjvmIdleTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 49);
                  } else if (prop.equals("SSL")) {
                     if (type == 2) {
                        original.setSSL((SSLMBean)this.createCopy((AbstractDescriptorBean)proposed.getSSL()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("SSL", original.getSSL());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 53);
                  } else if (prop.equals("SelfTuningThreadPoolSizeMax")) {
                     original.setSelfTuningThreadPoolSizeMax(proposed.getSelfTuningThreadPoolSizeMax());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (prop.equals("SelfTuningThreadPoolSizeMin")) {
                     original.setSelfTuningThreadPoolSizeMin(proposed.getSelfTuningThreadPoolSizeMin());
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  } else if (prop.equals("SocketReaderTimeoutMaxMillis")) {
                     original.setSocketReaderTimeoutMaxMillis(proposed.getSocketReaderTimeoutMaxMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 26);
                  } else if (prop.equals("SocketReaderTimeoutMinMillis")) {
                     original.setSocketReaderTimeoutMinMillis(proposed.getSocketReaderTimeoutMinMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 25);
                  } else if (prop.equals("SocketReaders")) {
                     original.setSocketReaders(proposed.getSocketReaders());
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  } else if (prop.equals("StdoutFormat")) {
                     original.setStdoutFormat(proposed.getStdoutFormat());
                     original._conditionalUnset(update.isUnsetUpdate(), 64);
                  } else if (prop.equals("StdoutSeverityLevel")) {
                     original.setStdoutSeverityLevel(proposed.getStdoutSeverityLevel());
                     original._conditionalUnset(update.isUnsetUpdate(), 57);
                  } else if (prop.equals("StuckThreadMaxTime")) {
                     original.setStuckThreadMaxTime(proposed.getStuckThreadMaxTime());
                     original._conditionalUnset(update.isUnsetUpdate(), 66);
                  } else if (prop.equals("StuckThreadTimerInterval")) {
                     original.setStuckThreadTimerInterval(proposed.getStuckThreadTimerInterval());
                     original._conditionalUnset(update.isUnsetUpdate(), 67);
                  } else if (prop.equals("SystemThreadPoolSize")) {
                     original.setSystemThreadPoolSize(proposed.getSystemThreadPoolSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("T3ClientAbbrevTableSize")) {
                     original.setT3ClientAbbrevTableSize(proposed.getT3ClientAbbrevTableSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 75);
                  } else if (prop.equals("T3ServerAbbrevTableSize")) {
                     original.setT3ServerAbbrevTableSize(proposed.getT3ServerAbbrevTableSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 76);
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
                  } else if (prop.equals("ThreadPoolPercentSocketReaders")) {
                     original.setThreadPoolPercentSocketReaders(proposed.getThreadPoolPercentSocketReaders());
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  } else if (prop.equals("ThreadPoolSize")) {
                     original.setThreadPoolSize(proposed.getThreadPoolSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("TimedOutRefIsolationTime")) {
                     original.setTimedOutRefIsolationTime(proposed.getTimedOutRefIsolationTime());
                     original._conditionalUnset(update.isUnsetUpdate(), 73);
                  } else if (prop.equals("TracingEnabled")) {
                     original.setTracingEnabled(proposed.getTracingEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 68);
                  } else if (prop.equals("Use81StyleExecuteQueues")) {
                     original.setUse81StyleExecuteQueues(proposed.getUse81StyleExecuteQueues());
                     original._conditionalUnset(update.isUnsetUpdate(), 74);
                  } else if (prop.equals("UseIIOPLocateRequest")) {
                     original.setUseIIOPLocateRequest(proposed.getUseIIOPLocateRequest());
                     original._conditionalUnset(update.isUnsetUpdate(), 36);
                  } else if (prop.equals("ValidProtocols")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("AddWorkManagerThreadsByCpuCount")) {
                     original.setAddWorkManagerThreadsByCpuCount(proposed.isAddWorkManagerThreadsByCpuCount());
                     original._conditionalUnset(update.isUnsetUpdate(), 79);
                  } else if (prop.equals("AllowShrinkingPriorityRequestQueue")) {
                     original.setAllowShrinkingPriorityRequestQueue(proposed.isAllowShrinkingPriorityRequestQueue());
                     original._conditionalUnset(update.isUnsetUpdate(), 84);
                  } else if (prop.equals("DevPollDisabled")) {
                     original.setDevPollDisabled(proposed.isDevPollDisabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (!prop.equals("DynamicallyCreated")) {
                     if (prop.equals("EagerThreadLocalCleanup")) {
                        original.setEagerThreadLocalCleanup(proposed.isEagerThreadLocalCleanup());
                        original._conditionalUnset(update.isUnsetUpdate(), 86);
                     } else if (prop.equals("GatheredWritesEnabled")) {
                        original.setGatheredWritesEnabled(proposed.isGatheredWritesEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 77);
                     } else if (prop.equals("InstrumentStackTraceEnabled")) {
                        original.setInstrumentStackTraceEnabled(proposed.isInstrumentStackTraceEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 60);
                     } else if (prop.equals("IsolatePartitionThreadLocals")) {
                        original.setIsolatePartitionThreadLocals(proposed.isIsolatePartitionThreadLocals());
                        original._conditionalUnset(update.isUnsetUpdate(), 87);
                     } else if (prop.equals("LogRemoteExceptionsEnabled")) {
                        original.setLogRemoteExceptionsEnabled(proposed.isLogRemoteExceptionsEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 59);
                     } else if (prop.equals("NativeIOEnabled")) {
                        original.setNativeIOEnabled(proposed.isNativeIOEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 20);
                     } else if (prop.equals("OutboundEnabled")) {
                        original.setOutboundEnabled(proposed.isOutboundEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 27);
                     } else if (prop.equals("OutboundPrivateKeyEnabled")) {
                        original.setOutboundPrivateKeyEnabled(proposed.isOutboundPrivateKeyEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 28);
                     } else if (prop.equals("ReverseDNSAllowed")) {
                        original.setReverseDNSAllowed(proposed.isReverseDNSAllowed());
                        original._conditionalUnset(update.isUnsetUpdate(), 11);
                     } else if (prop.equals("ScatteredReadsEnabled")) {
                        original.setScatteredReadsEnabled(proposed.isScatteredReadsEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 78);
                     } else if (prop.equals("SocketBufferSizeAsChunkSize")) {
                        original.setSocketBufferSizeAsChunkSize(proposed.isSocketBufferSizeAsChunkSize());
                        original._conditionalUnset(update.isUnsetUpdate(), 31);
                     } else if (prop.equals("StdoutDebugEnabled")) {
                        original.setStdoutDebugEnabled(proposed.isStdoutDebugEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 58);
                     } else if (prop.equals("StdoutEnabled")) {
                        original.setStdoutEnabled(proposed.isStdoutEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 56);
                     } else if (prop.equals("StdoutLogStack")) {
                        original.setStdoutLogStack(proposed.isStdoutLogStack());
                        original._conditionalUnset(update.isUnsetUpdate(), 65);
                     } else if (prop.equals("UseConcurrentQueueForRequestManager")) {
                        original.setUseConcurrentQueueForRequestManager(proposed.isUseConcurrentQueueForRequestManager());
                        original._conditionalUnset(update.isUnsetUpdate(), 80);
                     } else if (prop.equals("UseDetailedThreadName")) {
                        original.setUseDetailedThreadName(proposed.isUseDetailedThreadName());
                        original._conditionalUnset(update.isUnsetUpdate(), 82);
                     } else if (prop.equals("UseEnhancedIncrementAdvisor")) {
                        original.setUseEnhancedIncrementAdvisor(proposed.isUseEnhancedIncrementAdvisor());
                        original._conditionalUnset(update.isUnsetUpdate(), 85);
                     } else if (prop.equals("UseEnhancedPriorityQueueForRequestManager")) {
                        original.setUseEnhancedPriorityQueueForRequestManager(proposed.isUseEnhancedPriorityQueueForRequestManager());
                        original._conditionalUnset(update.isUnsetUpdate(), 83);
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
            KernelMBeanImpl copy = (KernelMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdministrationProtocol")) && this.bean.isAdministrationProtocolSet()) {
               copy.setAdministrationProtocol(this.bean.getAdministrationProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("CompleteCOMMessageTimeout")) && this.bean.isCompleteCOMMessageTimeoutSet()) {
               copy.setCompleteCOMMessageTimeout(this.bean.getCompleteCOMMessageTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("CompleteHTTPMessageTimeout")) && this.bean.isCompleteHTTPMessageTimeoutSet()) {
               copy.setCompleteHTTPMessageTimeout(this.bean.getCompleteHTTPMessageTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("CompleteIIOPMessageTimeout")) && this.bean.isCompleteIIOPMessageTimeoutSet()) {
               copy.setCompleteIIOPMessageTimeout(this.bean.getCompleteIIOPMessageTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("CompleteMessageTimeout")) && this.bean.isCompleteMessageTimeoutSet()) {
               copy.setCompleteMessageTimeout(this.bean.getCompleteMessageTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("CompleteT3MessageTimeout")) && this.bean.isCompleteT3MessageTimeoutSet()) {
               copy.setCompleteT3MessageTimeout(this.bean.getCompleteT3MessageTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("CompleteWriteTimeout")) && this.bean.isCompleteWriteTimeoutSet()) {
               copy.setCompleteWriteTimeout(this.bean.getCompleteWriteTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectTimeout")) && this.bean.isConnectTimeoutSet()) {
               copy.setConnectTimeout(this.bean.getConnectTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("DGCIdlePeriodsUntilTimeout")) && this.bean.isDGCIdlePeriodsUntilTimeoutSet()) {
               copy.setDGCIdlePeriodsUntilTimeout(this.bean.getDGCIdlePeriodsUntilTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultGIOPMinorVersion")) && this.bean.isDefaultGIOPMinorVersionSet()) {
               copy.setDefaultGIOPMinorVersion(this.bean.getDefaultGIOPMinorVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultProtocol")) && this.bean.isDefaultProtocolSet()) {
               copy.setDefaultProtocol(this.bean.getDefaultProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultSecureProtocol")) && this.bean.isDefaultSecureProtocolSet()) {
               copy.setDefaultSecureProtocol(this.bean.getDefaultSecureProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("ExecuteQueues")) && this.bean.isExecuteQueuesSet() && !copy._isSet(62)) {
               ExecuteQueueMBean[] oldExecuteQueues = this.bean.getExecuteQueues();
               ExecuteQueueMBean[] newExecuteQueues = new ExecuteQueueMBean[oldExecuteQueues.length];

               for(int i = 0; i < newExecuteQueues.length; ++i) {
                  newExecuteQueues[i] = (ExecuteQueueMBean)((ExecuteQueueMBean)this.createCopy((AbstractDescriptorBean)oldExecuteQueues[i], includeObsolete));
               }

               copy.setExecuteQueues(newExecuteQueues);
            }

            if ((excludeProps == null || !excludeProps.contains("IIOP")) && this.bean.isIIOPSet() && !copy._isSet(54)) {
               Object o = this.bean.getIIOP();
               copy.setIIOP((IIOPMBean)null);
               copy.setIIOP(o == null ? null : (IIOPMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("IIOPLocationForwardPolicy")) && this.bean.isIIOPLocationForwardPolicySet()) {
               copy.setIIOPLocationForwardPolicy(this.bean.getIIOPLocationForwardPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("IIOPTxMechanism")) && this.bean.isIIOPTxMechanismSet()) {
               copy.setIIOPTxMechanism(this.bean.getIIOPTxMechanism());
            }

            if ((excludeProps == null || !excludeProps.contains("IdleConnectionTimeout")) && this.bean.isIdleConnectionTimeoutSet()) {
               copy.setIdleConnectionTimeout(this.bean.getIdleConnectionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("IdleIIOPConnectionTimeout")) && this.bean.isIdleIIOPConnectionTimeoutSet()) {
               copy.setIdleIIOPConnectionTimeout(this.bean.getIdleIIOPConnectionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("IdlePeriodsUntilTimeout")) && this.bean.isIdlePeriodsUntilTimeoutSet()) {
               copy.setIdlePeriodsUntilTimeout(this.bean.getIdlePeriodsUntilTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSThreadPoolSize")) && this.bean.isJMSThreadPoolSizeSet()) {
               copy.setJMSThreadPoolSize(this.bean.getJMSThreadPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("LoadStubUsingContextClassLoader")) && this.bean.isLoadStubUsingContextClassLoaderSet()) {
               copy.setLoadStubUsingContextClassLoader(this.bean.getLoadStubUsingContextClassLoader());
            }

            if ((excludeProps == null || !excludeProps.contains("Log")) && this.bean.isLogSet() && !copy._isSet(55)) {
               Object o = this.bean.getLog();
               copy.setLog((LogMBean)null);
               copy.setLog(o == null ? null : (LogMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MTUSize")) && this.bean.isMTUSizeSet()) {
               copy.setMTUSize(this.bean.getMTUSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxCOMMessageSize")) && this.bean.isMaxCOMMessageSizeSet()) {
               copy.setMaxCOMMessageSize(this.bean.getMaxCOMMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxHTTPMessageSize")) && this.bean.isMaxHTTPMessageSizeSet()) {
               copy.setMaxHTTPMessageSize(this.bean.getMaxHTTPMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxIIOPMessageSize")) && this.bean.isMaxIIOPMessageSizeSet()) {
               copy.setMaxIIOPMessageSize(this.bean.getMaxIIOPMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxMessageSize")) && this.bean.isMaxMessageSizeSet()) {
               copy.setMaxMessageSize(this.bean.getMaxMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxOpenSockCount")) && this.bean.isMaxOpenSockCountSet()) {
               copy.setMaxOpenSockCount(this.bean.getMaxOpenSockCount());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxT3MessageSize")) && this.bean.isMaxT3MessageSizeSet()) {
               copy.setMaxT3MessageSize(this.bean.getMaxT3MessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagingBridgeThreadPoolSize")) && this.bean.isMessagingBridgeThreadPoolSizeSet()) {
               copy.setMessagingBridgeThreadPoolSize(this.bean.getMessagingBridgeThreadPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MuxerClass")) && this.bean.isMuxerClassSet()) {
               copy.setMuxerClass(this.bean.getMuxerClass());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PeriodLength")) && this.bean.isPeriodLengthSet()) {
               copy.setPeriodLength(this.bean.getPeriodLength());
            }

            if ((excludeProps == null || !excludeProps.contains("PrintStackTraceInProduction")) && this.bean.isPrintStackTraceInProductionSet()) {
               copy.setPrintStackTraceInProduction(this.bean.getPrintStackTraceInProduction());
            }

            if ((excludeProps == null || !excludeProps.contains("RefreshClientRuntimeDescriptor")) && this.bean.isRefreshClientRuntimeDescriptorSet()) {
               copy.setRefreshClientRuntimeDescriptor(this.bean.getRefreshClientRuntimeDescriptor());
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseTimeout")) && this.bean.isResponseTimeoutSet()) {
               copy.setResponseTimeout(this.bean.getResponseTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("RjvmIdleTimeout")) && this.bean.isRjvmIdleTimeoutSet()) {
               copy.setRjvmIdleTimeout(this.bean.getRjvmIdleTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("SSL")) && this.bean.isSSLSet() && !copy._isSet(53)) {
               Object o = this.bean.getSSL();
               copy.setSSL((SSLMBean)null);
               copy.setSSL(o == null ? null : (SSLMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SelfTuningThreadPoolSizeMax")) && this.bean.isSelfTuningThreadPoolSizeMaxSet()) {
               copy.setSelfTuningThreadPoolSizeMax(this.bean.getSelfTuningThreadPoolSizeMax());
            }

            if ((excludeProps == null || !excludeProps.contains("SelfTuningThreadPoolSizeMin")) && this.bean.isSelfTuningThreadPoolSizeMinSet()) {
               copy.setSelfTuningThreadPoolSizeMin(this.bean.getSelfTuningThreadPoolSizeMin());
            }

            if ((excludeProps == null || !excludeProps.contains("SocketReaderTimeoutMaxMillis")) && this.bean.isSocketReaderTimeoutMaxMillisSet()) {
               copy.setSocketReaderTimeoutMaxMillis(this.bean.getSocketReaderTimeoutMaxMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("SocketReaderTimeoutMinMillis")) && this.bean.isSocketReaderTimeoutMinMillisSet()) {
               copy.setSocketReaderTimeoutMinMillis(this.bean.getSocketReaderTimeoutMinMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("SocketReaders")) && this.bean.isSocketReadersSet()) {
               copy.setSocketReaders(this.bean.getSocketReaders());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutFormat")) && this.bean.isStdoutFormatSet()) {
               copy.setStdoutFormat(this.bean.getStdoutFormat());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutSeverityLevel")) && this.bean.isStdoutSeverityLevelSet()) {
               copy.setStdoutSeverityLevel(this.bean.getStdoutSeverityLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("StuckThreadMaxTime")) && this.bean.isStuckThreadMaxTimeSet()) {
               copy.setStuckThreadMaxTime(this.bean.getStuckThreadMaxTime());
            }

            if ((excludeProps == null || !excludeProps.contains("StuckThreadTimerInterval")) && this.bean.isStuckThreadTimerIntervalSet()) {
               copy.setStuckThreadTimerInterval(this.bean.getStuckThreadTimerInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("SystemThreadPoolSize")) && this.bean.isSystemThreadPoolSizeSet()) {
               copy.setSystemThreadPoolSize(this.bean.getSystemThreadPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("T3ClientAbbrevTableSize")) && this.bean.isT3ClientAbbrevTableSizeSet()) {
               copy.setT3ClientAbbrevTableSize(this.bean.getT3ClientAbbrevTableSize());
            }

            if ((excludeProps == null || !excludeProps.contains("T3ServerAbbrevTableSize")) && this.bean.isT3ServerAbbrevTableSizeSet()) {
               copy.setT3ServerAbbrevTableSize(this.bean.getT3ServerAbbrevTableSize());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadPoolPercentSocketReaders")) && this.bean.isThreadPoolPercentSocketReadersSet()) {
               copy.setThreadPoolPercentSocketReaders(this.bean.getThreadPoolPercentSocketReaders());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadPoolSize")) && this.bean.isThreadPoolSizeSet()) {
               copy.setThreadPoolSize(this.bean.getThreadPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("TimedOutRefIsolationTime")) && this.bean.isTimedOutRefIsolationTimeSet()) {
               copy.setTimedOutRefIsolationTime(this.bean.getTimedOutRefIsolationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("TracingEnabled")) && this.bean.isTracingEnabledSet()) {
               copy.setTracingEnabled(this.bean.getTracingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("Use81StyleExecuteQueues")) && this.bean.isUse81StyleExecuteQueuesSet()) {
               copy.setUse81StyleExecuteQueues(this.bean.getUse81StyleExecuteQueues());
            }

            if ((excludeProps == null || !excludeProps.contains("UseIIOPLocateRequest")) && this.bean.isUseIIOPLocateRequestSet()) {
               copy.setUseIIOPLocateRequest(this.bean.getUseIIOPLocateRequest());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidProtocols")) && this.bean.isValidProtocolsSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("AddWorkManagerThreadsByCpuCount")) && this.bean.isAddWorkManagerThreadsByCpuCountSet()) {
               copy.setAddWorkManagerThreadsByCpuCount(this.bean.isAddWorkManagerThreadsByCpuCount());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowShrinkingPriorityRequestQueue")) && this.bean.isAllowShrinkingPriorityRequestQueueSet()) {
               copy.setAllowShrinkingPriorityRequestQueue(this.bean.isAllowShrinkingPriorityRequestQueue());
            }

            if ((excludeProps == null || !excludeProps.contains("DevPollDisabled")) && this.bean.isDevPollDisabledSet()) {
               copy.setDevPollDisabled(this.bean.isDevPollDisabled());
            }

            if ((excludeProps == null || !excludeProps.contains("EagerThreadLocalCleanup")) && this.bean.isEagerThreadLocalCleanupSet()) {
               copy.setEagerThreadLocalCleanup(this.bean.isEagerThreadLocalCleanup());
            }

            if ((excludeProps == null || !excludeProps.contains("GatheredWritesEnabled")) && this.bean.isGatheredWritesEnabledSet()) {
               copy.setGatheredWritesEnabled(this.bean.isGatheredWritesEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("InstrumentStackTraceEnabled")) && this.bean.isInstrumentStackTraceEnabledSet()) {
               copy.setInstrumentStackTraceEnabled(this.bean.isInstrumentStackTraceEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IsolatePartitionThreadLocals")) && this.bean.isIsolatePartitionThreadLocalsSet()) {
               copy.setIsolatePartitionThreadLocals(this.bean.isIsolatePartitionThreadLocals());
            }

            if ((excludeProps == null || !excludeProps.contains("LogRemoteExceptionsEnabled")) && this.bean.isLogRemoteExceptionsEnabledSet()) {
               copy.setLogRemoteExceptionsEnabled(this.bean.isLogRemoteExceptionsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("NativeIOEnabled")) && this.bean.isNativeIOEnabledSet()) {
               copy.setNativeIOEnabled(this.bean.isNativeIOEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundEnabled")) && this.bean.isOutboundEnabledSet()) {
               copy.setOutboundEnabled(this.bean.isOutboundEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundPrivateKeyEnabled")) && this.bean.isOutboundPrivateKeyEnabledSet()) {
               copy.setOutboundPrivateKeyEnabled(this.bean.isOutboundPrivateKeyEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ReverseDNSAllowed")) && this.bean.isReverseDNSAllowedSet()) {
               copy.setReverseDNSAllowed(this.bean.isReverseDNSAllowed());
            }

            if ((excludeProps == null || !excludeProps.contains("ScatteredReadsEnabled")) && this.bean.isScatteredReadsEnabledSet()) {
               copy.setScatteredReadsEnabled(this.bean.isScatteredReadsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SocketBufferSizeAsChunkSize")) && this.bean.isSocketBufferSizeAsChunkSizeSet()) {
               copy.setSocketBufferSizeAsChunkSize(this.bean.isSocketBufferSizeAsChunkSize());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutDebugEnabled")) && this.bean.isStdoutDebugEnabledSet()) {
               copy.setStdoutDebugEnabled(this.bean.isStdoutDebugEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutEnabled")) && this.bean.isStdoutEnabledSet()) {
               copy.setStdoutEnabled(this.bean.isStdoutEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("StdoutLogStack")) && this.bean.isStdoutLogStackSet()) {
               copy.setStdoutLogStack(this.bean.isStdoutLogStack());
            }

            if ((excludeProps == null || !excludeProps.contains("UseConcurrentQueueForRequestManager")) && this.bean.isUseConcurrentQueueForRequestManagerSet()) {
               copy.setUseConcurrentQueueForRequestManager(this.bean.isUseConcurrentQueueForRequestManager());
            }

            if ((excludeProps == null || !excludeProps.contains("UseDetailedThreadName")) && this.bean.isUseDetailedThreadNameSet()) {
               copy.setUseDetailedThreadName(this.bean.isUseDetailedThreadName());
            }

            if ((excludeProps == null || !excludeProps.contains("UseEnhancedIncrementAdvisor")) && this.bean.isUseEnhancedIncrementAdvisorSet()) {
               copy.setUseEnhancedIncrementAdvisor(this.bean.isUseEnhancedIncrementAdvisor());
            }

            if ((excludeProps == null || !excludeProps.contains("UseEnhancedPriorityQueueForRequestManager")) && this.bean.isUseEnhancedPriorityQueueForRequestManagerSet()) {
               copy.setUseEnhancedPriorityQueueForRequestManager(this.bean.isUseEnhancedPriorityQueueForRequestManager());
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
         this.inferSubTree(this.bean.getExecuteQueues(), clazz, annotation);
         this.inferSubTree(this.bean.getIIOP(), clazz, annotation);
         this.inferSubTree(this.bean.getKernelDebug(), clazz, annotation);
         this.inferSubTree(this.bean.getLog(), clazz, annotation);
         this.inferSubTree(this.bean.getSSL(), clazz, annotation);
      }
   }
}

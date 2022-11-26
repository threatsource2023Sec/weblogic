package weblogic.management.configuration;

import com.bea.logging.LoggingConfigValidator;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.Log;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class LogMBeanImpl extends CommonLogMBeanImpl implements LogMBean, Serializable {
   private LogFilterMBean _DomainLogBroadcastFilter;
   private String _DomainLogBroadcastSeverity;
   private int _DomainLogBroadcasterBufferSize;
   private boolean _DynamicallyCreated;
   private boolean _Log4jLoggingEnabled;
   private LogFilterMBean _LogFileFilter;
   private String _LogFilePath;
   private boolean _LogMonitoringEnabled;
   private int _LogMonitoringIntervalSecs;
   private int _LogMonitoringMaxThrottleMessageSignatureCount;
   private int _LogMonitoringThrottleMessageLength;
   private int _LogMonitoringThrottleThreshold;
   private String _LogRotationDirPath;
   private LogFilterMBean _MemoryBufferFilter;
   private String _MemoryBufferSeverity;
   private int _MemoryBufferSize;
   private String _Name;
   private Properties _PlatformLoggerLevels;
   private boolean _RedirectStderrToServerLogEnabled;
   private boolean _RedirectStdoutToServerLogEnabled;
   private boolean _ServerLoggingBridgeAtRootLoggerEnabled;
   private boolean _ServerLoggingBridgeUseParentLoggersEnabled;
   private LogFilterMBean _StdoutFilter;
   private String[] _Tags;
   private transient Log _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private LogMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(LogMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(LogMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public LogMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(LogMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      LogMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._DomainLogBroadcastFilter instanceof LogFilterMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getDomainLogBroadcastFilter() != null) {
            this._getReferenceManager().unregisterBean((LogFilterMBeanImpl)oldDelegate.getDomainLogBroadcastFilter());
         }

         if (delegate != null && delegate.getDomainLogBroadcastFilter() != null) {
            this._getReferenceManager().registerBean((LogFilterMBeanImpl)delegate.getDomainLogBroadcastFilter(), false);
         }

         ((LogFilterMBeanImpl)this._DomainLogBroadcastFilter)._setDelegateBean((LogFilterMBeanImpl)((LogFilterMBeanImpl)(delegate == null ? null : delegate.getDomainLogBroadcastFilter())));
      }

      if (this._LogFileFilter instanceof LogFilterMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getLogFileFilter() != null) {
            this._getReferenceManager().unregisterBean((LogFilterMBeanImpl)oldDelegate.getLogFileFilter());
         }

         if (delegate != null && delegate.getLogFileFilter() != null) {
            this._getReferenceManager().registerBean((LogFilterMBeanImpl)delegate.getLogFileFilter(), false);
         }

         ((LogFilterMBeanImpl)this._LogFileFilter)._setDelegateBean((LogFilterMBeanImpl)((LogFilterMBeanImpl)(delegate == null ? null : delegate.getLogFileFilter())));
      }

      if (this._MemoryBufferFilter instanceof LogFilterMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getMemoryBufferFilter() != null) {
            this._getReferenceManager().unregisterBean((LogFilterMBeanImpl)oldDelegate.getMemoryBufferFilter());
         }

         if (delegate != null && delegate.getMemoryBufferFilter() != null) {
            this._getReferenceManager().registerBean((LogFilterMBeanImpl)delegate.getMemoryBufferFilter(), false);
         }

         ((LogFilterMBeanImpl)this._MemoryBufferFilter)._setDelegateBean((LogFilterMBeanImpl)((LogFilterMBeanImpl)(delegate == null ? null : delegate.getMemoryBufferFilter())));
      }

      if (this._StdoutFilter instanceof LogFilterMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getStdoutFilter() != null) {
            this._getReferenceManager().unregisterBean((LogFilterMBeanImpl)oldDelegate.getStdoutFilter());
         }

         if (delegate != null && delegate.getStdoutFilter() != null) {
            this._getReferenceManager().registerBean((LogFilterMBeanImpl)delegate.getStdoutFilter(), false);
         }

         ((LogFilterMBeanImpl)this._StdoutFilter)._setDelegateBean((LogFilterMBeanImpl)((LogFilterMBeanImpl)(delegate == null ? null : delegate.getStdoutFilter())));
      }

   }

   public LogMBeanImpl() {
      try {
         this._customizer = new Log(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public LogMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new Log(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public LogMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new Log(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public LogFilterMBean getLogFileFilter() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().getLogFileFilter() : this._LogFileFilter;
   }

   public String getLogFileFilterAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getLogFileFilter();
      return bean == null ? null : bean._getKey().toString();
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

   public boolean isLogFileFilterInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isLogFileFilterSet() {
      return this._isSet(32);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setLogFileFilterAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, LogFilterMBean.class, new ReferenceManager.Resolver(this, 32) {
            public void resolveReference(Object value) {
               try {
                  LogMBeanImpl.this.setLogFileFilter((LogFilterMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         LogFilterMBean _oldVal = this._LogFileFilter;
         this._initializeProperty(32);
         this._postSet(32, _oldVal, this._LogFileFilter);
      }

   }

   public void setLogFileFilter(LogFilterMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 32, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return LogMBeanImpl.this.getLogFileFilter();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(32);
      LogFilterMBean _oldVal = this._LogFileFilter;
      this._LogFileFilter = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

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
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public LogFilterMBean getStdoutFilter() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getStdoutFilter() : this._StdoutFilter;
   }

   public String getStdoutFilterAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getStdoutFilter();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isStdoutFilterInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isStdoutFilterSet() {
      return this._isSet(33);
   }

   public void setStdoutFilterAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, LogFilterMBean.class, new ReferenceManager.Resolver(this, 33) {
            public void resolveReference(Object value) {
               try {
                  LogMBeanImpl.this.setStdoutFilter((LogFilterMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         LogFilterMBean _oldVal = this._StdoutFilter;
         this._initializeProperty(33);
         this._postSet(33, _oldVal, this._StdoutFilter);
      }

   }

   public void setStdoutFilter(LogFilterMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 33, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return LogMBeanImpl.this.getStdoutFilter();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(33);
      LogFilterMBean _oldVal = this._StdoutFilter;
      this._StdoutFilter = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDomainLogBroadcastSeverity() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._performMacroSubstitution(this._getDelegateBean().getDomainLogBroadcastSeverity(), this) : this._DomainLogBroadcastSeverity;
   }

   public boolean isDomainLogBroadcastSeverityInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isDomainLogBroadcastSeveritySet() {
      return this._isSet(34);
   }

   public void setDomainLogBroadcastSeverity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Debug", "Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency", "Off"};
      param0 = LegalChecks.checkInEnum("DomainLogBroadcastSeverity", param0, _set);
      boolean wasSet = this._isSet(34);
      String _oldVal = this._DomainLogBroadcastSeverity;
      this._DomainLogBroadcastSeverity = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var5.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public LogFilterMBean getDomainLogBroadcastFilter() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().getDomainLogBroadcastFilter() : this._DomainLogBroadcastFilter;
   }

   public String getDomainLogBroadcastFilterAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDomainLogBroadcastFilter();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isDomainLogBroadcastFilterInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isDomainLogBroadcastFilterSet() {
      return this._isSet(35);
   }

   public void setDomainLogBroadcastFilterAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, LogFilterMBean.class, new ReferenceManager.Resolver(this, 35) {
            public void resolveReference(Object value) {
               try {
                  LogMBeanImpl.this.setDomainLogBroadcastFilter((LogFilterMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         LogFilterMBean _oldVal = this._DomainLogBroadcastFilter;
         this._initializeProperty(35);
         this._postSet(35, _oldVal, this._DomainLogBroadcastFilter);
      }

   }

   public void setDomainLogBroadcastFilter(LogFilterMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 35, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return LogMBeanImpl.this.getDomainLogBroadcastFilter();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(35);
      LogFilterMBean _oldVal = this._DomainLogBroadcastFilter;
      this._DomainLogBroadcastFilter = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMemoryBufferSeverity() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._performMacroSubstitution(this._getDelegateBean().getMemoryBufferSeverity(), this) : this._MemoryBufferSeverity;
   }

   public boolean isMemoryBufferSeverityInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isMemoryBufferSeveritySet() {
      return this._isSet(36);
   }

   public void setMemoryBufferSeverity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Trace", "Debug", "Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency", "Off"};
      param0 = LegalChecks.checkInEnum("MemoryBufferSeverity", param0, _set);
      boolean wasSet = this._isSet(36);
      String _oldVal = this._MemoryBufferSeverity;
      this._MemoryBufferSeverity = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var5.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public LogFilterMBean getMemoryBufferFilter() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().getMemoryBufferFilter() : this._MemoryBufferFilter;
   }

   public String getMemoryBufferFilterAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getMemoryBufferFilter();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isMemoryBufferFilterInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isMemoryBufferFilterSet() {
      return this._isSet(37);
   }

   public void setMemoryBufferFilterAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, LogFilterMBean.class, new ReferenceManager.Resolver(this, 37) {
            public void resolveReference(Object value) {
               try {
                  LogMBeanImpl.this.setMemoryBufferFilter((LogFilterMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         LogFilterMBean _oldVal = this._MemoryBufferFilter;
         this._initializeProperty(37);
         this._postSet(37, _oldVal, this._MemoryBufferFilter);
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setMemoryBufferFilter(LogFilterMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 37, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return LogMBeanImpl.this.getMemoryBufferFilter();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(37);
      LogFilterMBean _oldVal = this._MemoryBufferFilter;
      this._MemoryBufferFilter = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMemoryBufferSize() {
      if (!this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38)) {
         return this._getDelegateBean().getMemoryBufferSize();
      } else if (!this._isSet(38)) {
         return this._isProductionModeEnabled() ? 500 : 10;
      } else {
         return this._MemoryBufferSize;
      }
   }

   public boolean isMemoryBufferSizeInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isMemoryBufferSizeSet() {
      return this._isSet(38);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setMemoryBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MemoryBufferSize", (long)param0, 10L, 5000L);
      boolean wasSet = this._isSet(38);
      int _oldVal = this._MemoryBufferSize;
      this._MemoryBufferSize = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLog4jLoggingEnabled() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().isLog4jLoggingEnabled() : this._Log4jLoggingEnabled;
   }

   public boolean isLog4jLoggingEnabledInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isLog4jLoggingEnabledSet() {
      return this._isSet(39);
   }

   public void setLog4jLoggingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(39);
      boolean _oldVal = this._Log4jLoggingEnabled;
      this._Log4jLoggingEnabled = param0;
      this._postSet(39, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(39)) {
            source._postSetFirePropertyChange(39, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isRedirectStdoutToServerLogEnabled() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40) ? this._getDelegateBean().isRedirectStdoutToServerLogEnabled() : this._RedirectStdoutToServerLogEnabled;
   }

   public boolean isRedirectStdoutToServerLogEnabledInherited() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40);
   }

   public boolean isRedirectStdoutToServerLogEnabledSet() {
      return this._isSet(40);
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
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setRedirectStdoutToServerLogEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(40);
      boolean _oldVal = this._RedirectStdoutToServerLogEnabled;
      this._RedirectStdoutToServerLogEnabled = param0;
      this._postSet(40, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(40)) {
            source._postSetFirePropertyChange(40, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isRedirectStderrToServerLogEnabled() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41) ? this._getDelegateBean().isRedirectStderrToServerLogEnabled() : this._RedirectStderrToServerLogEnabled;
   }

   public boolean isRedirectStderrToServerLogEnabledInherited() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41);
   }

   public boolean isRedirectStderrToServerLogEnabledSet() {
      return this._isSet(41);
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setRedirectStderrToServerLogEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(41);
      boolean _oldVal = this._RedirectStderrToServerLogEnabled;
      this._RedirectStderrToServerLogEnabled = param0;
      this._postSet(41, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(41)) {
            source._postSetFirePropertyChange(41, wasSet, _oldVal, param0);
         }
      }

   }

   public int getDomainLogBroadcasterBufferSize() {
      if (!this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42)) {
         return this._getDelegateBean().getDomainLogBroadcasterBufferSize();
      } else if (!this._isSet(42)) {
         return this._isProductionModeEnabled() ? 10 : 1;
      } else {
         return this._DomainLogBroadcasterBufferSize;
      }
   }

   public boolean isDomainLogBroadcasterBufferSizeInherited() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42);
   }

   public boolean isDomainLogBroadcasterBufferSizeSet() {
      return this._isSet(42);
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
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
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

   public void setDomainLogBroadcasterBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DomainLogBroadcasterBufferSize", (long)param0, 1L, 100L);
      boolean wasSet = this._isSet(42);
      int _oldVal = this._DomainLogBroadcasterBufferSize;
      this._DomainLogBroadcasterBufferSize = param0;
      this._postSet(42, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(42)) {
            source._postSetFirePropertyChange(42, wasSet, _oldVal, param0);
         }
      }

   }

   public String computeLogFilePath() {
      return this._customizer.computeLogFilePath();
   }

   public boolean isServerLoggingBridgeUseParentLoggersEnabled() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43) ? this._getDelegateBean().isServerLoggingBridgeUseParentLoggersEnabled() : this._ServerLoggingBridgeUseParentLoggersEnabled;
   }

   public boolean isServerLoggingBridgeUseParentLoggersEnabledInherited() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43);
   }

   public boolean isServerLoggingBridgeUseParentLoggersEnabledSet() {
      return this._isSet(43);
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

   public String getLogFilePath() {
      return this._customizer.getLogFilePath();
   }

   public boolean isLogFilePathInherited() {
      return false;
   }

   public boolean isLogFilePathSet() {
      return this._isSet(21);
   }

   public void setLogFilePath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LogFilePath = param0;
   }

   public void setServerLoggingBridgeUseParentLoggersEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(43);
      boolean _oldVal = this._ServerLoggingBridgeUseParentLoggersEnabled;
      this._ServerLoggingBridgeUseParentLoggersEnabled = param0;
      this._postSet(43, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(43)) {
            source._postSetFirePropertyChange(43, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLogRotationDirPath() {
      return this._customizer.getLogRotationDirPath();
   }

   public Properties getPlatformLoggerLevels() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44) ? this._getDelegateBean().getPlatformLoggerLevels() : this._PlatformLoggerLevels;
   }

   public String getPlatformLoggerLevelsAsString() {
      return StringHelper.objectToString(this.getPlatformLoggerLevels());
   }

   public boolean isLogRotationDirPathInherited() {
      return false;
   }

   public boolean isLogRotationDirPathSet() {
      return this._isSet(22);
   }

   public boolean isPlatformLoggerLevelsInherited() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44);
   }

   public boolean isPlatformLoggerLevelsSet() {
      return this._isSet(44);
   }

   public void setLogRotationDirPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LogRotationDirPath = param0;
   }

   public void setPlatformLoggerLevelsAsString(String param0) {
      try {
         this.setPlatformLoggerLevels(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setPlatformLoggerLevels(Properties param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LoggingConfigValidator.validatePlatformLoggerLevels(param0);
      boolean wasSet = this._isSet(44);
      Properties _oldVal = this._PlatformLoggerLevels;
      this._PlatformLoggerLevels = param0;
      this._postSet(44, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(44)) {
            source._postSetFirePropertyChange(44, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isServerLoggingBridgeAtRootLoggerEnabled() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45) ? this._getDelegateBean().isServerLoggingBridgeAtRootLoggerEnabled() : this._ServerLoggingBridgeAtRootLoggerEnabled;
   }

   public boolean isServerLoggingBridgeAtRootLoggerEnabledInherited() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45);
   }

   public boolean isServerLoggingBridgeAtRootLoggerEnabledSet() {
      return this._isSet(45);
   }

   public void setServerLoggingBridgeAtRootLoggerEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(45);
      boolean _oldVal = this._ServerLoggingBridgeAtRootLoggerEnabled;
      this._ServerLoggingBridgeAtRootLoggerEnabled = param0;
      this._postSet(45, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(45)) {
            source._postSetFirePropertyChange(45, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLogMonitoringEnabled() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46) ? this._getDelegateBean().isLogMonitoringEnabled() : this._LogMonitoringEnabled;
   }

   public boolean isLogMonitoringEnabledInherited() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46);
   }

   public boolean isLogMonitoringEnabledSet() {
      return this._isSet(46);
   }

   public void setLogMonitoringEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(46);
      boolean _oldVal = this._LogMonitoringEnabled;
      this._LogMonitoringEnabled = param0;
      this._postSet(46, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(46)) {
            source._postSetFirePropertyChange(46, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLogMonitoringIntervalSecs() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47) ? this._getDelegateBean().getLogMonitoringIntervalSecs() : this._LogMonitoringIntervalSecs;
   }

   public boolean isLogMonitoringIntervalSecsInherited() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47);
   }

   public boolean isLogMonitoringIntervalSecsSet() {
      return this._isSet(47);
   }

   public void setLogMonitoringIntervalSecs(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("LogMonitoringIntervalSecs", param0, 5);
      boolean wasSet = this._isSet(47);
      int _oldVal = this._LogMonitoringIntervalSecs;
      this._LogMonitoringIntervalSecs = param0;
      this._postSet(47, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(47)) {
            source._postSetFirePropertyChange(47, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLogMonitoringThrottleThreshold() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48) ? this._getDelegateBean().getLogMonitoringThrottleThreshold() : this._LogMonitoringThrottleThreshold;
   }

   public boolean isLogMonitoringThrottleThresholdInherited() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48);
   }

   public boolean isLogMonitoringThrottleThresholdSet() {
      return this._isSet(48);
   }

   public void setLogMonitoringThrottleThreshold(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("LogMonitoringThrottleThreshold", param0, 5);
      boolean wasSet = this._isSet(48);
      int _oldVal = this._LogMonitoringThrottleThreshold;
      this._LogMonitoringThrottleThreshold = param0;
      this._postSet(48, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(48)) {
            source._postSetFirePropertyChange(48, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLogMonitoringThrottleMessageLength() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49) ? this._getDelegateBean().getLogMonitoringThrottleMessageLength() : this._LogMonitoringThrottleMessageLength;
   }

   public boolean isLogMonitoringThrottleMessageLengthInherited() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49);
   }

   public boolean isLogMonitoringThrottleMessageLengthSet() {
      return this._isSet(49);
   }

   public void setLogMonitoringThrottleMessageLength(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LogMonitoringThrottleMessageLength", (long)param0, 10L, 500L);
      boolean wasSet = this._isSet(49);
      int _oldVal = this._LogMonitoringThrottleMessageLength;
      this._LogMonitoringThrottleMessageLength = param0;
      this._postSet(49, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(49)) {
            source._postSetFirePropertyChange(49, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLogMonitoringMaxThrottleMessageSignatureCount() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50) ? this._getDelegateBean().getLogMonitoringMaxThrottleMessageSignatureCount() : this._LogMonitoringMaxThrottleMessageSignatureCount;
   }

   public boolean isLogMonitoringMaxThrottleMessageSignatureCountInherited() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50);
   }

   public boolean isLogMonitoringMaxThrottleMessageSignatureCountSet() {
      return this._isSet(50);
   }

   public void setLogMonitoringMaxThrottleMessageSignatureCount(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LogMonitoringMaxThrottleMessageSignatureCount", (long)param0, 100L, 5000L);
      boolean wasSet = this._isSet(50);
      int _oldVal = this._LogMonitoringMaxThrottleMessageSignatureCount;
      this._LogMonitoringMaxThrottleMessageSignatureCount = param0;
      this._postSet(50, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogMBeanImpl source = (LogMBeanImpl)var4.next();
         if (source != null && !source._isSet(50)) {
            source._postSetFirePropertyChange(50, wasSet, _oldVal, param0);
         }
      }

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
         idx = 35;
      }

      try {
         switch (idx) {
            case 35:
               this._DomainLogBroadcastFilter = null;
               if (initOne) {
                  break;
               }
            case 34:
               this._DomainLogBroadcastSeverity = "Notice";
               if (initOne) {
                  break;
               }
            case 42:
               this._DomainLogBroadcasterBufferSize = 1;
               if (initOne) {
                  break;
               }
            case 32:
               this._LogFileFilter = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._LogFilePath = null;
               if (initOne) {
                  break;
               }
            case 47:
               this._LogMonitoringIntervalSecs = 30;
               if (initOne) {
                  break;
               }
            case 50:
               this._LogMonitoringMaxThrottleMessageSignatureCount = 1000;
               if (initOne) {
                  break;
               }
            case 49:
               this._LogMonitoringThrottleMessageLength = 50;
               if (initOne) {
                  break;
               }
            case 48:
               this._LogMonitoringThrottleThreshold = 1500;
               if (initOne) {
                  break;
               }
            case 22:
               this._LogRotationDirPath = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._MemoryBufferFilter = null;
               if (initOne) {
                  break;
               }
            case 36:
               this._MemoryBufferSeverity = "Trace";
               if (initOne) {
                  break;
               }
            case 38:
               this._MemoryBufferSize = 10;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 44:
               this._PlatformLoggerLevels = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._StdoutFilter = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 39:
               this._Log4jLoggingEnabled = false;
               if (initOne) {
                  break;
               }
            case 46:
               this._LogMonitoringEnabled = true;
               if (initOne) {
                  break;
               }
            case 41:
               this._RedirectStderrToServerLogEnabled = false;
               if (initOne) {
                  break;
               }
            case 40:
               this._RedirectStdoutToServerLogEnabled = false;
               if (initOne) {
                  break;
               }
            case 45:
               this._ServerLoggingBridgeAtRootLoggerEnabled = true;
               if (initOne) {
                  break;
               }
            case 43:
               this._ServerLoggingBridgeUseParentLoggersEnabled = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
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
      return "Log";
   }

   public void putValue(String name, Object v) {
      LogFilterMBean oldVal;
      if (name.equals("DomainLogBroadcastFilter")) {
         oldVal = this._DomainLogBroadcastFilter;
         this._DomainLogBroadcastFilter = (LogFilterMBean)v;
         this._postSet(35, oldVal, this._DomainLogBroadcastFilter);
      } else {
         String oldVal;
         if (name.equals("DomainLogBroadcastSeverity")) {
            oldVal = this._DomainLogBroadcastSeverity;
            this._DomainLogBroadcastSeverity = (String)v;
            this._postSet(34, oldVal, this._DomainLogBroadcastSeverity);
         } else {
            int oldVal;
            if (name.equals("DomainLogBroadcasterBufferSize")) {
               oldVal = this._DomainLogBroadcasterBufferSize;
               this._DomainLogBroadcasterBufferSize = (Integer)v;
               this._postSet(42, oldVal, this._DomainLogBroadcasterBufferSize);
            } else {
               boolean oldVal;
               if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("Log4jLoggingEnabled")) {
                  oldVal = this._Log4jLoggingEnabled;
                  this._Log4jLoggingEnabled = (Boolean)v;
                  this._postSet(39, oldVal, this._Log4jLoggingEnabled);
               } else if (name.equals("LogFileFilter")) {
                  oldVal = this._LogFileFilter;
                  this._LogFileFilter = (LogFilterMBean)v;
                  this._postSet(32, oldVal, this._LogFileFilter);
               } else if (name.equals("LogFilePath")) {
                  oldVal = this._LogFilePath;
                  this._LogFilePath = (String)v;
                  this._postSet(21, oldVal, this._LogFilePath);
               } else if (name.equals("LogMonitoringEnabled")) {
                  oldVal = this._LogMonitoringEnabled;
                  this._LogMonitoringEnabled = (Boolean)v;
                  this._postSet(46, oldVal, this._LogMonitoringEnabled);
               } else if (name.equals("LogMonitoringIntervalSecs")) {
                  oldVal = this._LogMonitoringIntervalSecs;
                  this._LogMonitoringIntervalSecs = (Integer)v;
                  this._postSet(47, oldVal, this._LogMonitoringIntervalSecs);
               } else if (name.equals("LogMonitoringMaxThrottleMessageSignatureCount")) {
                  oldVal = this._LogMonitoringMaxThrottleMessageSignatureCount;
                  this._LogMonitoringMaxThrottleMessageSignatureCount = (Integer)v;
                  this._postSet(50, oldVal, this._LogMonitoringMaxThrottleMessageSignatureCount);
               } else if (name.equals("LogMonitoringThrottleMessageLength")) {
                  oldVal = this._LogMonitoringThrottleMessageLength;
                  this._LogMonitoringThrottleMessageLength = (Integer)v;
                  this._postSet(49, oldVal, this._LogMonitoringThrottleMessageLength);
               } else if (name.equals("LogMonitoringThrottleThreshold")) {
                  oldVal = this._LogMonitoringThrottleThreshold;
                  this._LogMonitoringThrottleThreshold = (Integer)v;
                  this._postSet(48, oldVal, this._LogMonitoringThrottleThreshold);
               } else if (name.equals("LogRotationDirPath")) {
                  oldVal = this._LogRotationDirPath;
                  this._LogRotationDirPath = (String)v;
                  this._postSet(22, oldVal, this._LogRotationDirPath);
               } else if (name.equals("MemoryBufferFilter")) {
                  oldVal = this._MemoryBufferFilter;
                  this._MemoryBufferFilter = (LogFilterMBean)v;
                  this._postSet(37, oldVal, this._MemoryBufferFilter);
               } else if (name.equals("MemoryBufferSeverity")) {
                  oldVal = this._MemoryBufferSeverity;
                  this._MemoryBufferSeverity = (String)v;
                  this._postSet(36, oldVal, this._MemoryBufferSeverity);
               } else if (name.equals("MemoryBufferSize")) {
                  oldVal = this._MemoryBufferSize;
                  this._MemoryBufferSize = (Integer)v;
                  this._postSet(38, oldVal, this._MemoryBufferSize);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("PlatformLoggerLevels")) {
                  Properties oldVal = this._PlatformLoggerLevels;
                  this._PlatformLoggerLevels = (Properties)v;
                  this._postSet(44, oldVal, this._PlatformLoggerLevels);
               } else if (name.equals("RedirectStderrToServerLogEnabled")) {
                  oldVal = this._RedirectStderrToServerLogEnabled;
                  this._RedirectStderrToServerLogEnabled = (Boolean)v;
                  this._postSet(41, oldVal, this._RedirectStderrToServerLogEnabled);
               } else if (name.equals("RedirectStdoutToServerLogEnabled")) {
                  oldVal = this._RedirectStdoutToServerLogEnabled;
                  this._RedirectStdoutToServerLogEnabled = (Boolean)v;
                  this._postSet(40, oldVal, this._RedirectStdoutToServerLogEnabled);
               } else if (name.equals("ServerLoggingBridgeAtRootLoggerEnabled")) {
                  oldVal = this._ServerLoggingBridgeAtRootLoggerEnabled;
                  this._ServerLoggingBridgeAtRootLoggerEnabled = (Boolean)v;
                  this._postSet(45, oldVal, this._ServerLoggingBridgeAtRootLoggerEnabled);
               } else if (name.equals("ServerLoggingBridgeUseParentLoggersEnabled")) {
                  oldVal = this._ServerLoggingBridgeUseParentLoggersEnabled;
                  this._ServerLoggingBridgeUseParentLoggersEnabled = (Boolean)v;
                  this._postSet(43, oldVal, this._ServerLoggingBridgeUseParentLoggersEnabled);
               } else if (name.equals("StdoutFilter")) {
                  oldVal = this._StdoutFilter;
                  this._StdoutFilter = (LogFilterMBean)v;
                  this._postSet(33, oldVal, this._StdoutFilter);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("customizer")) {
                  Log oldVal = this._customizer;
                  this._customizer = (Log)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DomainLogBroadcastFilter")) {
         return this._DomainLogBroadcastFilter;
      } else if (name.equals("DomainLogBroadcastSeverity")) {
         return this._DomainLogBroadcastSeverity;
      } else if (name.equals("DomainLogBroadcasterBufferSize")) {
         return new Integer(this._DomainLogBroadcasterBufferSize);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Log4jLoggingEnabled")) {
         return new Boolean(this._Log4jLoggingEnabled);
      } else if (name.equals("LogFileFilter")) {
         return this._LogFileFilter;
      } else if (name.equals("LogFilePath")) {
         return this._LogFilePath;
      } else if (name.equals("LogMonitoringEnabled")) {
         return new Boolean(this._LogMonitoringEnabled);
      } else if (name.equals("LogMonitoringIntervalSecs")) {
         return new Integer(this._LogMonitoringIntervalSecs);
      } else if (name.equals("LogMonitoringMaxThrottleMessageSignatureCount")) {
         return new Integer(this._LogMonitoringMaxThrottleMessageSignatureCount);
      } else if (name.equals("LogMonitoringThrottleMessageLength")) {
         return new Integer(this._LogMonitoringThrottleMessageLength);
      } else if (name.equals("LogMonitoringThrottleThreshold")) {
         return new Integer(this._LogMonitoringThrottleThreshold);
      } else if (name.equals("LogRotationDirPath")) {
         return this._LogRotationDirPath;
      } else if (name.equals("MemoryBufferFilter")) {
         return this._MemoryBufferFilter;
      } else if (name.equals("MemoryBufferSeverity")) {
         return this._MemoryBufferSeverity;
      } else if (name.equals("MemoryBufferSize")) {
         return new Integer(this._MemoryBufferSize);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PlatformLoggerLevels")) {
         return this._PlatformLoggerLevels;
      } else if (name.equals("RedirectStderrToServerLogEnabled")) {
         return new Boolean(this._RedirectStderrToServerLogEnabled);
      } else if (name.equals("RedirectStdoutToServerLogEnabled")) {
         return new Boolean(this._RedirectStdoutToServerLogEnabled);
      } else if (name.equals("ServerLoggingBridgeAtRootLoggerEnabled")) {
         return new Boolean(this._ServerLoggingBridgeAtRootLoggerEnabled);
      } else if (name.equals("ServerLoggingBridgeUseParentLoggersEnabled")) {
         return new Boolean(this._ServerLoggingBridgeUseParentLoggersEnabled);
      } else if (name.equals("StdoutFilter")) {
         return this._StdoutFilter;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends CommonLogMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 10:
            case 11:
            case 12:
            case 14:
            case 16:
            case 17:
            case 23:
            case 24:
            case 25:
            case 26:
            case 30:
            case 31:
            case 32:
            case 35:
            case 36:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 45:
            case 46:
            case 47:
            case 49:
            case 50:
            default:
               break;
            case 13:
               if (s.equals("log-file-path")) {
                  return 21;
               }

               if (s.equals("stdout-filter")) {
                  return 33;
               }
               break;
            case 15:
               if (s.equals("log-file-filter")) {
                  return 32;
               }
               break;
            case 18:
               if (s.equals("memory-buffer-size")) {
                  return 38;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("memory-buffer-filter")) {
                  return 37;
               }
               break;
            case 21:
               if (s.equals("log-rotation-dir-path")) {
                  return 22;
               }

               if (s.equals("log4j-logging-enabled")) {
                  return 39;
               }
               break;
            case 22:
               if (s.equals("memory-buffer-severity")) {
                  return 36;
               }

               if (s.equals("platform-logger-levels")) {
                  return 44;
               }

               if (s.equals("log-monitoring-enabled")) {
                  return 46;
               }
               break;
            case 27:
               if (s.equals("domain-log-broadcast-filter")) {
                  return 35;
               }
               break;
            case 28:
               if (s.equals("log-monitoring-interval-secs")) {
                  return 47;
               }
               break;
            case 29:
               if (s.equals("domain-log-broadcast-severity")) {
                  return 34;
               }
               break;
            case 33:
               if (s.equals("log-monitoring-throttle-threshold")) {
                  return 48;
               }
               break;
            case 34:
               if (s.equals("domain-log-broadcaster-buffer-size")) {
                  return 42;
               }
               break;
            case 37:
               if (s.equals("redirect-stderr-to-server-log-enabled")) {
                  return 41;
               }

               if (s.equals("redirect-stdout-to-server-log-enabled")) {
                  return 40;
               }
               break;
            case 38:
               if (s.equals("log-monitoring-throttle-message-length")) {
                  return 49;
               }
               break;
            case 44:
               if (s.equals("server-logging-bridge-at-root-logger-enabled")) {
                  return 45;
               }
               break;
            case 48:
               if (s.equals("server-logging-bridge-use-parent-loggers-enabled")) {
                  return 43;
               }
               break;
            case 51:
               if (s.equals("log-monitoring-max-throttle-message-signature-count")) {
                  return 50;
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
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 21:
               return "log-file-path";
            case 22:
               return "log-rotation-dir-path";
            case 32:
               return "log-file-filter";
            case 33:
               return "stdout-filter";
            case 34:
               return "domain-log-broadcast-severity";
            case 35:
               return "domain-log-broadcast-filter";
            case 36:
               return "memory-buffer-severity";
            case 37:
               return "memory-buffer-filter";
            case 38:
               return "memory-buffer-size";
            case 39:
               return "log4j-logging-enabled";
            case 40:
               return "redirect-stdout-to-server-log-enabled";
            case 41:
               return "redirect-stderr-to-server-log-enabled";
            case 42:
               return "domain-log-broadcaster-buffer-size";
            case 43:
               return "server-logging-bridge-use-parent-loggers-enabled";
            case 44:
               return "platform-logger-levels";
            case 45:
               return "server-logging-bridge-at-root-logger-enabled";
            case 46:
               return "log-monitoring-enabled";
            case 47:
               return "log-monitoring-interval-secs";
            case 48:
               return "log-monitoring-throttle-threshold";
            case 49:
               return "log-monitoring-throttle-message-length";
            case 50:
               return "log-monitoring-max-throttle-message-signature-count";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 11:
               return true;
            case 16:
               return true;
            case 18:
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

   protected static class Helper extends CommonLogMBeanImpl.Helper {
      private LogMBeanImpl bean;

      protected Helper(LogMBeanImpl bean) {
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
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 21:
               return "LogFilePath";
            case 22:
               return "LogRotationDirPath";
            case 32:
               return "LogFileFilter";
            case 33:
               return "StdoutFilter";
            case 34:
               return "DomainLogBroadcastSeverity";
            case 35:
               return "DomainLogBroadcastFilter";
            case 36:
               return "MemoryBufferSeverity";
            case 37:
               return "MemoryBufferFilter";
            case 38:
               return "MemoryBufferSize";
            case 39:
               return "Log4jLoggingEnabled";
            case 40:
               return "RedirectStdoutToServerLogEnabled";
            case 41:
               return "RedirectStderrToServerLogEnabled";
            case 42:
               return "DomainLogBroadcasterBufferSize";
            case 43:
               return "ServerLoggingBridgeUseParentLoggersEnabled";
            case 44:
               return "PlatformLoggerLevels";
            case 45:
               return "ServerLoggingBridgeAtRootLoggerEnabled";
            case 46:
               return "LogMonitoringEnabled";
            case 47:
               return "LogMonitoringIntervalSecs";
            case 48:
               return "LogMonitoringThrottleThreshold";
            case 49:
               return "LogMonitoringThrottleMessageLength";
            case 50:
               return "LogMonitoringMaxThrottleMessageSignatureCount";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DomainLogBroadcastFilter")) {
            return 35;
         } else if (propName.equals("DomainLogBroadcastSeverity")) {
            return 34;
         } else if (propName.equals("DomainLogBroadcasterBufferSize")) {
            return 42;
         } else if (propName.equals("LogFileFilter")) {
            return 32;
         } else if (propName.equals("LogFilePath")) {
            return 21;
         } else if (propName.equals("LogMonitoringIntervalSecs")) {
            return 47;
         } else if (propName.equals("LogMonitoringMaxThrottleMessageSignatureCount")) {
            return 50;
         } else if (propName.equals("LogMonitoringThrottleMessageLength")) {
            return 49;
         } else if (propName.equals("LogMonitoringThrottleThreshold")) {
            return 48;
         } else if (propName.equals("LogRotationDirPath")) {
            return 22;
         } else if (propName.equals("MemoryBufferFilter")) {
            return 37;
         } else if (propName.equals("MemoryBufferSeverity")) {
            return 36;
         } else if (propName.equals("MemoryBufferSize")) {
            return 38;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PlatformLoggerLevels")) {
            return 44;
         } else if (propName.equals("StdoutFilter")) {
            return 33;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("Log4jLoggingEnabled")) {
            return 39;
         } else if (propName.equals("LogMonitoringEnabled")) {
            return 46;
         } else if (propName.equals("RedirectStderrToServerLogEnabled")) {
            return 41;
         } else if (propName.equals("RedirectStdoutToServerLogEnabled")) {
            return 40;
         } else if (propName.equals("ServerLoggingBridgeAtRootLoggerEnabled")) {
            return 45;
         } else {
            return propName.equals("ServerLoggingBridgeUseParentLoggersEnabled") ? 43 : super.getPropertyIndex(propName);
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
            if (this.bean.isDomainLogBroadcastFilterSet()) {
               buf.append("DomainLogBroadcastFilter");
               buf.append(String.valueOf(this.bean.getDomainLogBroadcastFilter()));
            }

            if (this.bean.isDomainLogBroadcastSeveritySet()) {
               buf.append("DomainLogBroadcastSeverity");
               buf.append(String.valueOf(this.bean.getDomainLogBroadcastSeverity()));
            }

            if (this.bean.isDomainLogBroadcasterBufferSizeSet()) {
               buf.append("DomainLogBroadcasterBufferSize");
               buf.append(String.valueOf(this.bean.getDomainLogBroadcasterBufferSize()));
            }

            if (this.bean.isLogFileFilterSet()) {
               buf.append("LogFileFilter");
               buf.append(String.valueOf(this.bean.getLogFileFilter()));
            }

            if (this.bean.isLogFilePathSet()) {
               buf.append("LogFilePath");
               buf.append(String.valueOf(this.bean.getLogFilePath()));
            }

            if (this.bean.isLogMonitoringIntervalSecsSet()) {
               buf.append("LogMonitoringIntervalSecs");
               buf.append(String.valueOf(this.bean.getLogMonitoringIntervalSecs()));
            }

            if (this.bean.isLogMonitoringMaxThrottleMessageSignatureCountSet()) {
               buf.append("LogMonitoringMaxThrottleMessageSignatureCount");
               buf.append(String.valueOf(this.bean.getLogMonitoringMaxThrottleMessageSignatureCount()));
            }

            if (this.bean.isLogMonitoringThrottleMessageLengthSet()) {
               buf.append("LogMonitoringThrottleMessageLength");
               buf.append(String.valueOf(this.bean.getLogMonitoringThrottleMessageLength()));
            }

            if (this.bean.isLogMonitoringThrottleThresholdSet()) {
               buf.append("LogMonitoringThrottleThreshold");
               buf.append(String.valueOf(this.bean.getLogMonitoringThrottleThreshold()));
            }

            if (this.bean.isLogRotationDirPathSet()) {
               buf.append("LogRotationDirPath");
               buf.append(String.valueOf(this.bean.getLogRotationDirPath()));
            }

            if (this.bean.isMemoryBufferFilterSet()) {
               buf.append("MemoryBufferFilter");
               buf.append(String.valueOf(this.bean.getMemoryBufferFilter()));
            }

            if (this.bean.isMemoryBufferSeveritySet()) {
               buf.append("MemoryBufferSeverity");
               buf.append(String.valueOf(this.bean.getMemoryBufferSeverity()));
            }

            if (this.bean.isMemoryBufferSizeSet()) {
               buf.append("MemoryBufferSize");
               buf.append(String.valueOf(this.bean.getMemoryBufferSize()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPlatformLoggerLevelsSet()) {
               buf.append("PlatformLoggerLevels");
               buf.append(String.valueOf(this.bean.getPlatformLoggerLevels()));
            }

            if (this.bean.isStdoutFilterSet()) {
               buf.append("StdoutFilter");
               buf.append(String.valueOf(this.bean.getStdoutFilter()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isLog4jLoggingEnabledSet()) {
               buf.append("Log4jLoggingEnabled");
               buf.append(String.valueOf(this.bean.isLog4jLoggingEnabled()));
            }

            if (this.bean.isLogMonitoringEnabledSet()) {
               buf.append("LogMonitoringEnabled");
               buf.append(String.valueOf(this.bean.isLogMonitoringEnabled()));
            }

            if (this.bean.isRedirectStderrToServerLogEnabledSet()) {
               buf.append("RedirectStderrToServerLogEnabled");
               buf.append(String.valueOf(this.bean.isRedirectStderrToServerLogEnabled()));
            }

            if (this.bean.isRedirectStdoutToServerLogEnabledSet()) {
               buf.append("RedirectStdoutToServerLogEnabled");
               buf.append(String.valueOf(this.bean.isRedirectStdoutToServerLogEnabled()));
            }

            if (this.bean.isServerLoggingBridgeAtRootLoggerEnabledSet()) {
               buf.append("ServerLoggingBridgeAtRootLoggerEnabled");
               buf.append(String.valueOf(this.bean.isServerLoggingBridgeAtRootLoggerEnabled()));
            }

            if (this.bean.isServerLoggingBridgeUseParentLoggersEnabledSet()) {
               buf.append("ServerLoggingBridgeUseParentLoggersEnabled");
               buf.append(String.valueOf(this.bean.isServerLoggingBridgeUseParentLoggersEnabled()));
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
            LogMBeanImpl otherTyped = (LogMBeanImpl)other;
            this.computeDiff("DomainLogBroadcastFilter", this.bean.getDomainLogBroadcastFilter(), otherTyped.getDomainLogBroadcastFilter(), true);
            this.computeDiff("DomainLogBroadcastSeverity", this.bean.getDomainLogBroadcastSeverity(), otherTyped.getDomainLogBroadcastSeverity(), true);
            this.computeDiff("DomainLogBroadcasterBufferSize", this.bean.getDomainLogBroadcasterBufferSize(), otherTyped.getDomainLogBroadcasterBufferSize(), true);
            this.computeDiff("LogFileFilter", this.bean.getLogFileFilter(), otherTyped.getLogFileFilter(), true);
            this.computeDiff("LogMonitoringIntervalSecs", this.bean.getLogMonitoringIntervalSecs(), otherTyped.getLogMonitoringIntervalSecs(), true);
            this.computeDiff("LogMonitoringMaxThrottleMessageSignatureCount", this.bean.getLogMonitoringMaxThrottleMessageSignatureCount(), otherTyped.getLogMonitoringMaxThrottleMessageSignatureCount(), true);
            this.computeDiff("LogMonitoringThrottleMessageLength", this.bean.getLogMonitoringThrottleMessageLength(), otherTyped.getLogMonitoringThrottleMessageLength(), true);
            this.computeDiff("LogMonitoringThrottleThreshold", this.bean.getLogMonitoringThrottleThreshold(), otherTyped.getLogMonitoringThrottleThreshold(), true);
            this.computeDiff("MemoryBufferFilter", this.bean.getMemoryBufferFilter(), otherTyped.getMemoryBufferFilter(), true);
            this.computeDiff("MemoryBufferSeverity", this.bean.getMemoryBufferSeverity(), otherTyped.getMemoryBufferSeverity(), true);
            this.computeDiff("MemoryBufferSize", this.bean.getMemoryBufferSize(), otherTyped.getMemoryBufferSize(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PlatformLoggerLevels", this.bean.getPlatformLoggerLevels(), otherTyped.getPlatformLoggerLevels(), true);
            this.computeDiff("StdoutFilter", this.bean.getStdoutFilter(), otherTyped.getStdoutFilter(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Log4jLoggingEnabled", this.bean.isLog4jLoggingEnabled(), otherTyped.isLog4jLoggingEnabled(), false);
            this.computeDiff("LogMonitoringEnabled", this.bean.isLogMonitoringEnabled(), otherTyped.isLogMonitoringEnabled(), true);
            this.computeDiff("RedirectStderrToServerLogEnabled", this.bean.isRedirectStderrToServerLogEnabled(), otherTyped.isRedirectStderrToServerLogEnabled(), false);
            this.computeDiff("RedirectStdoutToServerLogEnabled", this.bean.isRedirectStdoutToServerLogEnabled(), otherTyped.isRedirectStdoutToServerLogEnabled(), false);
            this.computeDiff("ServerLoggingBridgeAtRootLoggerEnabled", this.bean.isServerLoggingBridgeAtRootLoggerEnabled(), otherTyped.isServerLoggingBridgeAtRootLoggerEnabled(), false);
            this.computeDiff("ServerLoggingBridgeUseParentLoggersEnabled", this.bean.isServerLoggingBridgeUseParentLoggersEnabled(), otherTyped.isServerLoggingBridgeUseParentLoggersEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LogMBeanImpl original = (LogMBeanImpl)event.getSourceBean();
            LogMBeanImpl proposed = (LogMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DomainLogBroadcastFilter")) {
                  original.setDomainLogBroadcastFilterAsString(proposed.getDomainLogBroadcastFilterAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("DomainLogBroadcastSeverity")) {
                  original.setDomainLogBroadcastSeverity(proposed.getDomainLogBroadcastSeverity());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("DomainLogBroadcasterBufferSize")) {
                  original.setDomainLogBroadcasterBufferSize(proposed.getDomainLogBroadcasterBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 42);
               } else if (prop.equals("LogFileFilter")) {
                  original.setLogFileFilterAsString(proposed.getLogFileFilterAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (!prop.equals("LogFilePath")) {
                  if (prop.equals("LogMonitoringIntervalSecs")) {
                     original.setLogMonitoringIntervalSecs(proposed.getLogMonitoringIntervalSecs());
                     original._conditionalUnset(update.isUnsetUpdate(), 47);
                  } else if (prop.equals("LogMonitoringMaxThrottleMessageSignatureCount")) {
                     original.setLogMonitoringMaxThrottleMessageSignatureCount(proposed.getLogMonitoringMaxThrottleMessageSignatureCount());
                     original._conditionalUnset(update.isUnsetUpdate(), 50);
                  } else if (prop.equals("LogMonitoringThrottleMessageLength")) {
                     original.setLogMonitoringThrottleMessageLength(proposed.getLogMonitoringThrottleMessageLength());
                     original._conditionalUnset(update.isUnsetUpdate(), 49);
                  } else if (prop.equals("LogMonitoringThrottleThreshold")) {
                     original.setLogMonitoringThrottleThreshold(proposed.getLogMonitoringThrottleThreshold());
                     original._conditionalUnset(update.isUnsetUpdate(), 48);
                  } else if (!prop.equals("LogRotationDirPath")) {
                     if (prop.equals("MemoryBufferFilter")) {
                        original.setMemoryBufferFilterAsString(proposed.getMemoryBufferFilterAsString());
                        original._conditionalUnset(update.isUnsetUpdate(), 37);
                     } else if (prop.equals("MemoryBufferSeverity")) {
                        original.setMemoryBufferSeverity(proposed.getMemoryBufferSeverity());
                        original._conditionalUnset(update.isUnsetUpdate(), 36);
                     } else if (prop.equals("MemoryBufferSize")) {
                        original.setMemoryBufferSize(proposed.getMemoryBufferSize());
                        original._conditionalUnset(update.isUnsetUpdate(), 38);
                     } else if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (prop.equals("PlatformLoggerLevels")) {
                        original.setPlatformLoggerLevels(proposed.getPlatformLoggerLevels() == null ? null : (Properties)proposed.getPlatformLoggerLevels().clone());
                        original._conditionalUnset(update.isUnsetUpdate(), 44);
                     } else if (prop.equals("StdoutFilter")) {
                        original.setStdoutFilterAsString(proposed.getStdoutFilterAsString());
                        original._conditionalUnset(update.isUnsetUpdate(), 33);
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
                     } else if (!prop.equals("DynamicallyCreated")) {
                        if (prop.equals("Log4jLoggingEnabled")) {
                           original.setLog4jLoggingEnabled(proposed.isLog4jLoggingEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 39);
                        } else if (prop.equals("LogMonitoringEnabled")) {
                           original.setLogMonitoringEnabled(proposed.isLogMonitoringEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 46);
                        } else if (prop.equals("RedirectStderrToServerLogEnabled")) {
                           original.setRedirectStderrToServerLogEnabled(proposed.isRedirectStderrToServerLogEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 41);
                        } else if (prop.equals("RedirectStdoutToServerLogEnabled")) {
                           original.setRedirectStdoutToServerLogEnabled(proposed.isRedirectStdoutToServerLogEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 40);
                        } else if (prop.equals("ServerLoggingBridgeAtRootLoggerEnabled")) {
                           original.setServerLoggingBridgeAtRootLoggerEnabled(proposed.isServerLoggingBridgeAtRootLoggerEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 45);
                        } else if (prop.equals("ServerLoggingBridgeUseParentLoggersEnabled")) {
                           original.setServerLoggingBridgeUseParentLoggersEnabled(proposed.isServerLoggingBridgeUseParentLoggersEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 43);
                        } else {
                           super.applyPropertyUpdate(event, update);
                        }
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
            LogMBeanImpl copy = (LogMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DomainLogBroadcastFilter")) && this.bean.isDomainLogBroadcastFilterSet()) {
               copy._unSet(copy, 35);
               copy.setDomainLogBroadcastFilterAsString(this.bean.getDomainLogBroadcastFilterAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DomainLogBroadcastSeverity")) && this.bean.isDomainLogBroadcastSeveritySet()) {
               copy.setDomainLogBroadcastSeverity(this.bean.getDomainLogBroadcastSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("DomainLogBroadcasterBufferSize")) && this.bean.isDomainLogBroadcasterBufferSizeSet()) {
               copy.setDomainLogBroadcasterBufferSize(this.bean.getDomainLogBroadcasterBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("LogFileFilter")) && this.bean.isLogFileFilterSet()) {
               copy._unSet(copy, 32);
               copy.setLogFileFilterAsString(this.bean.getLogFileFilterAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("LogMonitoringIntervalSecs")) && this.bean.isLogMonitoringIntervalSecsSet()) {
               copy.setLogMonitoringIntervalSecs(this.bean.getLogMonitoringIntervalSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("LogMonitoringMaxThrottleMessageSignatureCount")) && this.bean.isLogMonitoringMaxThrottleMessageSignatureCountSet()) {
               copy.setLogMonitoringMaxThrottleMessageSignatureCount(this.bean.getLogMonitoringMaxThrottleMessageSignatureCount());
            }

            if ((excludeProps == null || !excludeProps.contains("LogMonitoringThrottleMessageLength")) && this.bean.isLogMonitoringThrottleMessageLengthSet()) {
               copy.setLogMonitoringThrottleMessageLength(this.bean.getLogMonitoringThrottleMessageLength());
            }

            if ((excludeProps == null || !excludeProps.contains("LogMonitoringThrottleThreshold")) && this.bean.isLogMonitoringThrottleThresholdSet()) {
               copy.setLogMonitoringThrottleThreshold(this.bean.getLogMonitoringThrottleThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("MemoryBufferFilter")) && this.bean.isMemoryBufferFilterSet()) {
               copy._unSet(copy, 37);
               copy.setMemoryBufferFilterAsString(this.bean.getMemoryBufferFilterAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("MemoryBufferSeverity")) && this.bean.isMemoryBufferSeveritySet()) {
               copy.setMemoryBufferSeverity(this.bean.getMemoryBufferSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("MemoryBufferSize")) && this.bean.isMemoryBufferSizeSet()) {
               copy.setMemoryBufferSize(this.bean.getMemoryBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PlatformLoggerLevels")) && this.bean.isPlatformLoggerLevelsSet()) {
               copy.setPlatformLoggerLevels(this.bean.getPlatformLoggerLevels());
            }

            if ((excludeProps == null || !excludeProps.contains("StdoutFilter")) && this.bean.isStdoutFilterSet()) {
               copy._unSet(copy, 33);
               copy.setStdoutFilterAsString(this.bean.getStdoutFilterAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Log4jLoggingEnabled")) && this.bean.isLog4jLoggingEnabledSet()) {
               copy.setLog4jLoggingEnabled(this.bean.isLog4jLoggingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("LogMonitoringEnabled")) && this.bean.isLogMonitoringEnabledSet()) {
               copy.setLogMonitoringEnabled(this.bean.isLogMonitoringEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RedirectStderrToServerLogEnabled")) && this.bean.isRedirectStderrToServerLogEnabledSet()) {
               copy.setRedirectStderrToServerLogEnabled(this.bean.isRedirectStderrToServerLogEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RedirectStdoutToServerLogEnabled")) && this.bean.isRedirectStdoutToServerLogEnabledSet()) {
               copy.setRedirectStdoutToServerLogEnabled(this.bean.isRedirectStdoutToServerLogEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerLoggingBridgeAtRootLoggerEnabled")) && this.bean.isServerLoggingBridgeAtRootLoggerEnabledSet()) {
               copy.setServerLoggingBridgeAtRootLoggerEnabled(this.bean.isServerLoggingBridgeAtRootLoggerEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerLoggingBridgeUseParentLoggersEnabled")) && this.bean.isServerLoggingBridgeUseParentLoggersEnabledSet()) {
               copy.setServerLoggingBridgeUseParentLoggersEnabled(this.bean.isServerLoggingBridgeUseParentLoggersEnabled());
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
         this.inferSubTree(this.bean.getDomainLogBroadcastFilter(), clazz, annotation);
         this.inferSubTree(this.bean.getLogFileFilter(), clazz, annotation);
         this.inferSubTree(this.bean.getMemoryBufferFilter(), clazz, annotation);
         this.inferSubTree(this.bean.getStdoutFilter(), clazz, annotation);
      }
   }
}

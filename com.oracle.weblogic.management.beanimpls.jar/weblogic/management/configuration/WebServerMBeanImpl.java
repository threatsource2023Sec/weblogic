package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.WebServer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebServerMBeanImpl extends DeploymentMBeanImpl implements WebServerMBean, Serializable {
   private boolean _AcceptContextPathInGetRealPath;
   private boolean _AuthCookieEnabled;
   private Map _Charsets;
   private boolean _ChunkedTransferDisabled;
   private String _ClientIpHeader;
   private boolean _DebugEnabled;
   private WebAppComponentMBean _DefaultWebApp;
   private String _DefaultWebAppContextRoot;
   private boolean _DynamicallyCreated;
   private int _FrontendHTTPPort;
   private int _FrontendHTTPSPort;
   private String _FrontendHost;
   private int _HttpsKeepAliveSecs;
   private boolean _KeepAliveEnabled;
   private int _KeepAliveSecs;
   private int _LogFileBufferKBytes;
   private int _LogFileCount;
   private int _LogFileFlushSecs;
   private String _LogFileFormat;
   private boolean _LogFileLimitEnabled;
   private String _LogFileName;
   private int _LogRotationPeriodMins;
   private String _LogRotationTimeBegin;
   private String _LogRotationType;
   private boolean _LogTimeInGMT;
   private boolean _LoggingEnabled;
   private int _MaxLogFileSizeKBytes;
   private int _MaxPostSize;
   private int _MaxPostTimeSecs;
   private int _MaxRequestParameterCount;
   private int _MaxRequestParamterCount;
   private String _Name;
   private int _OverloadResponseCode;
   private int _PostTimeoutSecs;
   private boolean _SendServerHeaderEnabled;
   private boolean _SingleSignonDisabled;
   private String[] _Tags;
   private Map _URLResource;
   private boolean _UseHeaderEncoding;
   private boolean _UseHighestCompatibleHTTPVersion;
   private boolean _WAPEnabled;
   private WebDeploymentMBean[] _WebDeployments;
   private WebServerLogMBean _WebServerLog;
   private String _WorkManagerForRemoteSessionFetching;
   private int _WriteChunkBytes;
   private transient WebServer _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WebServerMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WebServerMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WebServerMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WebServerMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WebServerMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WebServerMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._DefaultWebApp instanceof WebAppComponentMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getDefaultWebApp() != null) {
            this._getReferenceManager().unregisterBean((WebAppComponentMBeanImpl)oldDelegate.getDefaultWebApp());
         }

         if (delegate != null && delegate.getDefaultWebApp() != null) {
            this._getReferenceManager().registerBean((WebAppComponentMBeanImpl)delegate.getDefaultWebApp(), false);
         }

         ((WebAppComponentMBeanImpl)this._DefaultWebApp)._setDelegateBean((WebAppComponentMBeanImpl)((WebAppComponentMBeanImpl)(delegate == null ? null : delegate.getDefaultWebApp())));
      }

      if (this._WebServerLog instanceof WebServerLogMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getWebServerLog() != null) {
            this._getReferenceManager().unregisterBean((WebServerLogMBeanImpl)oldDelegate.getWebServerLog());
         }

         if (delegate != null && delegate.getWebServerLog() != null) {
            this._getReferenceManager().registerBean((WebServerLogMBeanImpl)delegate.getWebServerLog(), false);
         }

         ((WebServerLogMBeanImpl)this._WebServerLog)._setDelegateBean((WebServerLogMBeanImpl)((WebServerLogMBeanImpl)(delegate == null ? null : delegate.getWebServerLog())));
      }

   }

   public WebServerMBeanImpl() {
      try {
         this._customizer = new WebServer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WebServerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new WebServer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WebServerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new WebServer(this);
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

   public WebServerLogMBean getWebServerLog() {
      return this._WebServerLog;
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isWebServerLogInherited() {
      return false;
   }

   public boolean isWebServerLogSet() {
      return this._isSet(12) || this._isAnythingSet((AbstractDescriptorBean)this.getWebServerLog());
   }

   public void setWebServerLog(WebServerLogMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 12)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(12);
      WebServerLogMBean _oldVal = this._WebServerLog;
      this._WebServerLog = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var5.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public void setLoggingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      boolean _oldVal = this.isLoggingEnabled();
      this._customizer.setLoggingEnabled(param0);
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
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
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLoggingEnabled() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().isLoggingEnabled() : this._customizer.isLoggingEnabled();
   }

   public boolean isLoggingEnabledInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isLoggingEnabledSet() {
      return this._isSet(13);
   }

   public String getLogFileFormat() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getLogFileFormat(), this) : this._customizer.getLogFileFormat();
   }

   public boolean isLogFileFormatInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isLogFileFormatSet() {
      return this._isSet(14);
   }

   public void setLogFileFormat(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"common", "extended"};
      param0 = LegalChecks.checkInEnum("LogFileFormat", param0, _set);
      boolean wasSet = this._isSet(14);
      String _oldVal = this.getLogFileFormat();
      this._customizer.setLogFileFormat(param0);
      this._postSet(14, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var5.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getLogTimeInGMT() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getLogTimeInGMT() : this._customizer.getLogTimeInGMT();
   }

   public boolean isLogTimeInGMTInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isLogTimeInGMTSet() {
      return this._isSet(15);
   }

   public void setLogTimeInGMT(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      boolean _oldVal = this.getLogTimeInGMT();
      this._customizer.setLogTimeInGMT(param0);
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLogFileName() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getLogFileName(), this) : this._customizer.getLogFileName();
   }

   public boolean isLogFileNameInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isLogFileNameSet() {
      return this._isSet(16);
   }

   public void setLogFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      String _oldVal = this.getLogFileName();
      this._customizer.setLogFileName(param0);
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getFrontendHost() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getFrontendHost(), this) : this._FrontendHost;
   }

   public boolean isFrontendHostInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isFrontendHostSet() {
      return this._isSet(17);
   }

   public void setFrontendHost(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      String _oldVal = this._FrontendHost;
      this._FrontendHost = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public int getFrontendHTTPPort() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getFrontendHTTPPort() : this._FrontendHTTPPort;
   }

   public boolean isFrontendHTTPPortInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isFrontendHTTPPortSet() {
      return this._isSet(18);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setFrontendHTTPPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      int _oldVal = this._FrontendHTTPPort;
      this._FrontendHTTPPort = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public int getFrontendHTTPSPort() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getFrontendHTTPSPort() : this._FrontendHTTPSPort;
   }

   public boolean isFrontendHTTPSPortInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isFrontendHTTPSPortSet() {
      return this._isSet(19);
   }

   public void setFrontendHTTPSPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      int _oldVal = this._FrontendHTTPSPort;
      this._FrontendHTTPSPort = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public void setLogFileBufferKBytes(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LogFileBufferKBytes", (long)param0, 0L, 1024L);
      boolean wasSet = this._isSet(20);
      int _oldVal = this._LogFileBufferKBytes;
      this._LogFileBufferKBytes = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLogFileBufferKBytes() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._getDelegateBean().getLogFileBufferKBytes() : this._LogFileBufferKBytes;
   }

   public boolean isLogFileBufferKBytesInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isLogFileBufferKBytesSet() {
      return this._isSet(20);
   }

   public int getMaxLogFileSizeKBytes() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getMaxLogFileSizeKBytes() : this._MaxLogFileSizeKBytes;
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

   public boolean isMaxLogFileSizeKBytesInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isMaxLogFileSizeKBytesSet() {
      return this._isSet(21);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setMaxLogFileSizeKBytes(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("MaxLogFileSizeKBytes", param0, 0);
      boolean wasSet = this._isSet(21);
      int _oldVal = this._MaxLogFileSizeKBytes;
      this._MaxLogFileSizeKBytes = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLogRotationType() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._performMacroSubstitution(this._getDelegateBean().getLogRotationType(), this) : this._customizer.getLogRotationType();
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isLogRotationTypeInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isLogRotationTypeSet() {
      return this._isSet(22);
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setLogRotationType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"size", "date"};
      param0 = LegalChecks.checkInEnum("LogRotationType", param0, _set);
      boolean wasSet = this._isSet(22);
      String _oldVal = this.getLogRotationType();
      this._customizer.setLogRotationType(param0);
      this._postSet(22, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var5.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
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
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
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

   public int getLogRotationPeriodMins() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getLogRotationPeriodMins() : this._customizer.getLogRotationPeriodMins();
   }

   public boolean isLogRotationPeriodMinsInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isLogRotationPeriodMinsSet() {
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

   public void setLogRotationPeriodMins(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LogRotationPeriodMins", (long)param0, 1L, 2147483647L);
      boolean wasSet = this._isSet(23);
      int _oldVal = this.getLogRotationPeriodMins();

      try {
         this._customizer.setLogRotationPeriodMins(param0);
      } catch (DistributedManagementException var6) {
         throw new UndeclaredThrowableException(var6);
      }

      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public int getOverloadResponseCode() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().getOverloadResponseCode() : this._OverloadResponseCode;
   }

   public boolean isOverloadResponseCodeInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isOverloadResponseCodeSet() {
      return this._isSet(24);
   }

   public void setOverloadResponseCode(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("OverloadResponseCode", (long)param0, 100L, 599L);
      boolean wasSet = this._isSet(24);
      int _oldVal = this._OverloadResponseCode;
      this._OverloadResponseCode = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLogFileFlushSecs() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().getLogFileFlushSecs() : this._LogFileFlushSecs;
   }

   public boolean isLogFileFlushSecsInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isLogFileFlushSecsSet() {
      return this._isSet(25);
   }

   public void setLogFileFlushSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LogFileFlushSecs", (long)param0, 1L, 360L);
      boolean wasSet = this._isSet(25);
      int _oldVal = this._LogFileFlushSecs;
      this._LogFileFlushSecs = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLogRotationTimeBegin() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._performMacroSubstitution(this._getDelegateBean().getLogRotationTimeBegin(), this) : this._customizer.getLogRotationTimeBegin();
   }

   public boolean isLogRotationTimeBeginInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isLogRotationTimeBeginSet() {
      return this._isSet(26);
   }

   public void setLogRotationTimeBegin(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LoggingLegalHelper.validateWebServerLogRotationTimeBegin(param0);
      boolean wasSet = this._isSet(26);
      String _oldVal = this.getLogRotationTimeBegin();
      this._customizer.setLogRotationTimeBegin(param0);
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public void setKeepAliveEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      boolean _oldVal = this._KeepAliveEnabled;
      this._KeepAliveEnabled = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isKeepAliveEnabled() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().isKeepAliveEnabled() : this._KeepAliveEnabled;
   }

   public boolean isKeepAliveEnabledInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isKeepAliveEnabledSet() {
      return this._isSet(27);
   }

   public int getKeepAliveSecs() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getKeepAliveSecs() : this._KeepAliveSecs;
   }

   public boolean isKeepAliveSecsInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isKeepAliveSecsSet() {
      return this._isSet(28);
   }

   public void setKeepAliveSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("KeepAliveSecs", (long)param0, 5L, 3600L);
      boolean wasSet = this._isSet(28);
      int _oldVal = this._KeepAliveSecs;
      this._KeepAliveSecs = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public int getHttpsKeepAliveSecs() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().getHttpsKeepAliveSecs() : this._HttpsKeepAliveSecs;
   }

   public boolean isHttpsKeepAliveSecsInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isHttpsKeepAliveSecsSet() {
      return this._isSet(29);
   }

   public void setHttpsKeepAliveSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("HttpsKeepAliveSecs", (long)param0, 30L, 360L);
      boolean wasSet = this._isSet(29);
      int _oldVal = this._HttpsKeepAliveSecs;
      this._HttpsKeepAliveSecs = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public void setPostTimeoutSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("PostTimeoutSecs", (long)param0, 0L, 120L);
      boolean wasSet = this._isSet(30);
      int _oldVal = this._PostTimeoutSecs;
      this._PostTimeoutSecs = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPostTimeoutSecs() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getPostTimeoutSecs() : this._PostTimeoutSecs;
   }

   public boolean isPostTimeoutSecsInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isPostTimeoutSecsSet() {
      return this._isSet(30);
   }

   public void setMaxPostTimeSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      int _oldVal = this._MaxPostTimeSecs;
      this._MaxPostTimeSecs = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxPostTimeSecs() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getMaxPostTimeSecs() : this._MaxPostTimeSecs;
   }

   public boolean isMaxPostTimeSecsInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isMaxPostTimeSecsSet() {
      return this._isSet(31);
   }

   public void setMaxPostSize(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(32);
      int _oldVal = this._MaxPostSize;
      this._MaxPostSize = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxPostSize() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().getMaxPostSize() : this._MaxPostSize;
   }

   public boolean isMaxPostSizeInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isMaxPostSizeSet() {
      return this._isSet(32);
   }

   public void setMaxRequestParameterCount(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(33);
      int _oldVal = this._MaxRequestParameterCount;
      this._MaxRequestParameterCount = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxRequestParameterCount() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().getMaxRequestParameterCount() : this._MaxRequestParameterCount;
   }

   public boolean isMaxRequestParameterCountInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isMaxRequestParameterCountSet() {
      return this._isSet(33);
   }

   public void setMaxRequestParamterCount(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      int _oldVal = this.getMaxRequestParamterCount();
      this._customizer.setMaxRequestParamterCount(param0);
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxRequestParamterCount() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().getMaxRequestParamterCount() : this._customizer.getMaxRequestParamterCount();
   }

   public boolean isMaxRequestParamterCountInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isMaxRequestParamterCountSet() {
      return this._isSet(34);
   }

   public void setSendServerHeaderEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(35);
      boolean _oldVal = this._SendServerHeaderEnabled;
      this._SendServerHeaderEnabled = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isSendServerHeaderEnabled() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().isSendServerHeaderEnabled() : this._SendServerHeaderEnabled;
   }

   public boolean isSendServerHeaderEnabledInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isSendServerHeaderEnabledSet() {
      return this._isSet(35);
   }

   public String getDefaultWebAppContextRoot() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultWebAppContextRoot(), this) : this._DefaultWebAppContextRoot;
   }

   public boolean isDefaultWebAppContextRootInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isDefaultWebAppContextRootSet() {
      return this._isSet(36);
   }

   public void setDefaultWebAppContextRoot(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(36);
      String _oldVal = this._DefaultWebAppContextRoot;
      this._DefaultWebAppContextRoot = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public WebAppComponentMBean getDefaultWebApp() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().getDefaultWebApp() : this._DefaultWebApp;
   }

   public String getDefaultWebAppAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDefaultWebApp();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isDefaultWebAppInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isDefaultWebAppSet() {
      return this._isSet(37);
   }

   public void setDefaultWebAppAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, WebAppComponentMBean.class, new ReferenceManager.Resolver(this, 37) {
            public void resolveReference(Object value) {
               try {
                  WebServerMBeanImpl.this.setDefaultWebApp((WebAppComponentMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         WebAppComponentMBean _oldVal = this._DefaultWebApp;
         this._initializeProperty(37);
         this._postSet(37, _oldVal, this._DefaultWebApp);
      }

   }

   public void setDefaultWebApp(WebAppComponentMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(37);
      WebAppComponentMBean _oldVal = this._DefaultWebApp;
      this._DefaultWebApp = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public void setCharsets(Map param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(38);
      Map _oldVal = this._Charsets;
      this._Charsets = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public Map getCharsets() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._getDelegateBean().getCharsets() : this._Charsets;
   }

   public String getCharsetsAsString() {
      return StringHelper.objectToString(this.getCharsets());
   }

   public boolean isCharsetsInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isCharsetsSet() {
      return this._isSet(38);
   }

   public void setCharsetsAsString(String param0) {
      try {
         this.setCharsets(StringHelper.stringToMap(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setURLResource(Map param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(39);
      Map _oldVal = this._URLResource;
      this._URLResource = param0;
      this._postSet(39, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(39)) {
            source._postSetFirePropertyChange(39, wasSet, _oldVal, param0);
         }
      }

   }

   public Map getURLResource() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._getDelegateBean().getURLResource() : this._URLResource;
   }

   public String getURLResourceAsString() {
      return StringHelper.objectToString(this.getURLResource());
   }

   public boolean isURLResourceInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isURLResourceSet() {
      return this._isSet(39);
   }

   public void setURLResourceAsString(String param0) {
      try {
         this.setURLResource(StringHelper.stringToMap(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setChunkedTransferDisabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(40);
      boolean _oldVal = this._ChunkedTransferDisabled;
      this._ChunkedTransferDisabled = param0;
      this._postSet(40, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(40)) {
            source._postSetFirePropertyChange(40, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isChunkedTransferDisabled() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40) ? this._getDelegateBean().isChunkedTransferDisabled() : this._ChunkedTransferDisabled;
   }

   public boolean isChunkedTransferDisabledInherited() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40);
   }

   public boolean isChunkedTransferDisabledSet() {
      return this._isSet(40);
   }

   public void setUseHighestCompatibleHTTPVersion(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(41);
      boolean _oldVal = this._UseHighestCompatibleHTTPVersion;
      this._UseHighestCompatibleHTTPVersion = param0;
      this._postSet(41, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(41)) {
            source._postSetFirePropertyChange(41, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isUseHighestCompatibleHTTPVersion() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41) ? this._getDelegateBean().isUseHighestCompatibleHTTPVersion() : this._UseHighestCompatibleHTTPVersion;
   }

   public boolean isUseHighestCompatibleHTTPVersionInherited() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41);
   }

   public boolean isUseHighestCompatibleHTTPVersionSet() {
      return this._isSet(41);
   }

   public void setUseHeaderEncoding(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(42);
      boolean _oldVal = this._UseHeaderEncoding;
      this._UseHeaderEncoding = param0;
      this._postSet(42, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(42)) {
            source._postSetFirePropertyChange(42, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isUseHeaderEncoding() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42) ? this._getDelegateBean().isUseHeaderEncoding() : this._UseHeaderEncoding;
   }

   public boolean isUseHeaderEncodingInherited() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42);
   }

   public boolean isUseHeaderEncodingSet() {
      return this._isSet(42);
   }

   public void setAuthCookieEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(43);
      boolean _oldVal = this._AuthCookieEnabled;
      this._AuthCookieEnabled = param0;
      this._postSet(43, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(43)) {
            source._postSetFirePropertyChange(43, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAuthCookieEnabled() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43) ? this._getDelegateBean().isAuthCookieEnabled() : this._AuthCookieEnabled;
   }

   public boolean isAuthCookieEnabledInherited() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43);
   }

   public boolean isAuthCookieEnabledSet() {
      return this._isSet(43);
   }

   public void setWriteChunkBytes(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(44);
      int _oldVal = this._WriteChunkBytes;
      this._WriteChunkBytes = param0;
      this._postSet(44, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(44)) {
            source._postSetFirePropertyChange(44, wasSet, _oldVal, param0);
         }
      }

   }

   public int getWriteChunkBytes() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44) ? this._getDelegateBean().getWriteChunkBytes() : this._WriteChunkBytes;
   }

   public boolean isWriteChunkBytesInherited() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44);
   }

   public boolean isWriteChunkBytesSet() {
      return this._isSet(44);
   }

   public void setDebugEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(45);
      boolean _oldVal = this._DebugEnabled;
      this._DebugEnabled = param0;
      this._postSet(45, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(45)) {
            source._postSetFirePropertyChange(45, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDebugEnabled() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45) ? this._getDelegateBean().isDebugEnabled() : this._DebugEnabled;
   }

   public boolean isDebugEnabledInherited() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45);
   }

   public boolean isDebugEnabledSet() {
      return this._isSet(45);
   }

   public void setWAPEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(46);
      boolean _oldVal = this._WAPEnabled;
      this._WAPEnabled = param0;
      this._postSet(46, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(46)) {
            source._postSetFirePropertyChange(46, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isWAPEnabled() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46) ? this._getDelegateBean().isWAPEnabled() : this._WAPEnabled;
   }

   public boolean isWAPEnabledInherited() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46);
   }

   public boolean isWAPEnabledSet() {
      return this._isSet(46);
   }

   public void setAcceptContextPathInGetRealPath(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(47);
      boolean _oldVal = this._AcceptContextPathInGetRealPath;
      this._AcceptContextPathInGetRealPath = param0;
      this._postSet(47, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(47)) {
            source._postSetFirePropertyChange(47, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAcceptContextPathInGetRealPath() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47) ? this._getDelegateBean().isAcceptContextPathInGetRealPath() : this._AcceptContextPathInGetRealPath;
   }

   public boolean isAcceptContextPathInGetRealPathInherited() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47);
   }

   public boolean isAcceptContextPathInGetRealPathSet() {
      return this._isSet(47);
   }

   public void setSingleSignonDisabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(48);
      boolean _oldVal = this._SingleSignonDisabled;
      this._SingleSignonDisabled = param0;
      this._postSet(48, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(48)) {
            source._postSetFirePropertyChange(48, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isSingleSignonDisabled() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48) ? this._getDelegateBean().isSingleSignonDisabled() : this._SingleSignonDisabled;
   }

   public boolean isSingleSignonDisabledInherited() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48);
   }

   public boolean isSingleSignonDisabledSet() {
      return this._isSet(48);
   }

   public WebDeploymentMBean[] getWebDeployments() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49) ? this._getDelegateBean().getWebDeployments() : this._WebDeployments;
   }

   public String getWebDeploymentsAsString() {
      return this._getHelper()._serializeKeyList(this.getWebDeployments());
   }

   public boolean isWebDeploymentsInherited() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49);
   }

   public boolean isWebDeploymentsSet() {
      return this._isSet(49);
   }

   public void setWebDeploymentsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._WebDeployments);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, WebDeploymentMBean.class, new ReferenceManager.Resolver(this, 49, param0) {
                  public void resolveReference(Object value) {
                     try {
                        WebServerMBeanImpl.this.addWebDeployment((WebDeploymentMBean)value);
                        WebServerMBeanImpl.this._getHelper().reorderArrayObjects((Object[])WebServerMBeanImpl.this._WebDeployments, this.getHandbackObject());
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
               WebDeploymentMBean[] var6 = this._WebDeployments;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  WebDeploymentMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeWebDeployment(member);
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
         WebDeploymentMBean[] _oldVal = this._WebDeployments;
         this._initializeProperty(49);
         this._postSet(49, _oldVal, this._WebDeployments);
      }
   }

   public void setWebDeployments(WebDeploymentMBean[] param0) throws DistributedManagementException {
      WebDeploymentMBean[] param0 = param0 == null ? new WebDeploymentMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      param0 = (WebDeploymentMBean[])((WebDeploymentMBean[])this._getHelper()._cleanAndValidateArray(param0, WebDeploymentMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 49, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return WebServerMBeanImpl.this.getWebDeployments();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(49);
      WebDeploymentMBean[] _oldVal = this._WebDeployments;
      this._WebDeployments = param0;
      this._postSet(49, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(49)) {
            source._postSetFirePropertyChange(49, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLogFileLimitEnabled() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50) ? this._getDelegateBean().isLogFileLimitEnabled() : this._customizer.isLogFileLimitEnabled();
   }

   public boolean isLogFileLimitEnabledInherited() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50);
   }

   public boolean isLogFileLimitEnabledSet() {
      return this._isSet(50);
   }

   public void setLogFileLimitEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(50);
      boolean _oldVal = this.isLogFileLimitEnabled();
      this._customizer.setLogFileLimitEnabled(param0);
      this._postSet(50, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(50)) {
            source._postSetFirePropertyChange(50, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLogFileCount() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51) ? this._getDelegateBean().getLogFileCount() : this._customizer.getLogFileCount();
   }

   public boolean isLogFileCountInherited() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51);
   }

   public boolean isLogFileCountSet() {
      return this._isSet(51);
   }

   public void setLogFileCount(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LogFileCount", (long)param0, 1L, 9999L);
      boolean wasSet = this._isSet(51);
      int _oldVal = this.getLogFileCount();
      this._customizer.setLogFileCount(param0);
      this._postSet(51, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(51)) {
            source._postSetFirePropertyChange(51, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addWebDeployment(WebDeploymentMBean param0) throws DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 49)) {
         WebDeploymentMBean[] _new;
         if (this._isSet(49)) {
            _new = (WebDeploymentMBean[])((WebDeploymentMBean[])this._getHelper()._extendArray(this.getWebDeployments(), WebDeploymentMBean.class, param0));
         } else {
            _new = new WebDeploymentMBean[]{param0};
         }

         try {
            this.setWebDeployments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeWebDeployment(WebDeploymentMBean param0) throws DistributedManagementException {
      WebDeploymentMBean[] _old = this.getWebDeployments();
      WebDeploymentMBean[] _new = (WebDeploymentMBean[])((WebDeploymentMBean[])this._getHelper()._removeElement(_old, WebDeploymentMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setWebDeployments(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
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

   public void setWorkManagerForRemoteSessionFetching(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(52);
      String _oldVal = this._WorkManagerForRemoteSessionFetching;
      this._WorkManagerForRemoteSessionFetching = param0;
      this._postSet(52, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(52)) {
            source._postSetFirePropertyChange(52, wasSet, _oldVal, param0);
         }
      }

   }

   public String getWorkManagerForRemoteSessionFetching() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52) ? this._performMacroSubstitution(this._getDelegateBean().getWorkManagerForRemoteSessionFetching(), this) : this._WorkManagerForRemoteSessionFetching;
   }

   public boolean isWorkManagerForRemoteSessionFetchingInherited() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52);
   }

   public boolean isWorkManagerForRemoteSessionFetchingSet() {
      return this._isSet(52);
   }

   public void setClientIpHeader(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(53);
      String _oldVal = this._ClientIpHeader;
      this._ClientIpHeader = param0;
      this._postSet(53, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerMBeanImpl source = (WebServerMBeanImpl)var4.next();
         if (source != null && !source._isSet(53)) {
            source._postSetFirePropertyChange(53, wasSet, _oldVal, param0);
         }
      }

   }

   public String getClientIpHeader() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53) ? this._performMacroSubstitution(this._getDelegateBean().getClientIpHeader(), this) : this._ClientIpHeader;
   }

   public boolean isClientIpHeaderInherited() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53);
   }

   public boolean isClientIpHeaderSet() {
      return this._isSet(53);
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
      return super._isAnythingSet() || this.isWebServerLogSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 38;
      }

      try {
         switch (idx) {
            case 38:
               this._Charsets = null;
               if (initOne) {
                  break;
               }
            case 53:
               this._ClientIpHeader = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._DefaultWebApp = null;
               if (initOne) {
                  break;
               }
            case 36:
               this._DefaultWebAppContextRoot = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._FrontendHTTPPort = 0;
               if (initOne) {
                  break;
               }
            case 19:
               this._FrontendHTTPSPort = 0;
               if (initOne) {
                  break;
               }
            case 17:
               this._FrontendHost = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._HttpsKeepAliveSecs = 60;
               if (initOne) {
                  break;
               }
            case 28:
               this._KeepAliveSecs = 30;
               if (initOne) {
                  break;
               }
            case 20:
               this._LogFileBufferKBytes = 8;
               if (initOne) {
                  break;
               }
            case 51:
               this._customizer.setLogFileCount(7);
               if (initOne) {
                  break;
               }
            case 25:
               this._LogFileFlushSecs = 60;
               if (initOne) {
                  break;
               }
            case 14:
               this._customizer.setLogFileFormat("common");
               if (initOne) {
                  break;
               }
            case 16:
               this._customizer.setLogFileName("logs/access.log");
               if (initOne) {
                  break;
               }
            case 23:
               this._customizer.setLogRotationPeriodMins(1440);
               if (initOne) {
                  break;
               }
            case 26:
               this._customizer.setLogRotationTimeBegin((String)null);
               if (initOne) {
                  break;
               }
            case 22:
               this._customizer.setLogRotationType("size");
               if (initOne) {
                  break;
               }
            case 15:
               this._customizer.setLogTimeInGMT(false);
               if (initOne) {
                  break;
               }
            case 21:
               this._MaxLogFileSizeKBytes = 5000;
               if (initOne) {
                  break;
               }
            case 32:
               this._MaxPostSize = -1;
               if (initOne) {
                  break;
               }
            case 31:
               this._MaxPostTimeSecs = -1;
               if (initOne) {
                  break;
               }
            case 33:
               this._MaxRequestParameterCount = 10000;
               if (initOne) {
                  break;
               }
            case 34:
               this._customizer.setMaxRequestParamterCount(10000);
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 24:
               this._OverloadResponseCode = 503;
               if (initOne) {
                  break;
               }
            case 30:
               this._PostTimeoutSecs = 30;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 39:
               this._URLResource = null;
               if (initOne) {
                  break;
               }
            case 49:
               this._WebDeployments = new WebDeploymentMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._WebServerLog = new WebServerLogMBeanImpl(this, 12);
               this._postCreate((AbstractDescriptorBean)this._WebServerLog);
               if (initOne) {
                  break;
               }
            case 52:
               this._WorkManagerForRemoteSessionFetching = null;
               if (initOne) {
                  break;
               }
            case 44:
               this._WriteChunkBytes = 512;
               if (initOne) {
                  break;
               }
            case 47:
               this._AcceptContextPathInGetRealPath = false;
               if (initOne) {
                  break;
               }
            case 43:
               this._AuthCookieEnabled = true;
               if (initOne) {
                  break;
               }
            case 40:
               this._ChunkedTransferDisabled = false;
               if (initOne) {
                  break;
               }
            case 45:
               this._DebugEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 27:
               this._KeepAliveEnabled = true;
               if (initOne) {
                  break;
               }
            case 50:
               this._customizer.setLogFileLimitEnabled(false);
               if (initOne) {
                  break;
               }
            case 13:
               this._customizer.setLoggingEnabled(true);
               if (initOne) {
                  break;
               }
            case 35:
               this._SendServerHeaderEnabled = false;
               if (initOne) {
                  break;
               }
            case 48:
               this._SingleSignonDisabled = false;
               if (initOne) {
                  break;
               }
            case 42:
               this._UseHeaderEncoding = false;
               if (initOne) {
                  break;
               }
            case 41:
               this._UseHighestCompatibleHTTPVersion = true;
               if (initOne) {
                  break;
               }
            case 46:
               this._WAPEnabled = false;
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
      return "WebServer";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("AcceptContextPathInGetRealPath")) {
         oldVal = this._AcceptContextPathInGetRealPath;
         this._AcceptContextPathInGetRealPath = (Boolean)v;
         this._postSet(47, oldVal, this._AcceptContextPathInGetRealPath);
      } else if (name.equals("AuthCookieEnabled")) {
         oldVal = this._AuthCookieEnabled;
         this._AuthCookieEnabled = (Boolean)v;
         this._postSet(43, oldVal, this._AuthCookieEnabled);
      } else {
         Map oldVal;
         if (name.equals("Charsets")) {
            oldVal = this._Charsets;
            this._Charsets = (Map)v;
            this._postSet(38, oldVal, this._Charsets);
         } else if (name.equals("ChunkedTransferDisabled")) {
            oldVal = this._ChunkedTransferDisabled;
            this._ChunkedTransferDisabled = (Boolean)v;
            this._postSet(40, oldVal, this._ChunkedTransferDisabled);
         } else {
            String oldVal;
            if (name.equals("ClientIpHeader")) {
               oldVal = this._ClientIpHeader;
               this._ClientIpHeader = (String)v;
               this._postSet(53, oldVal, this._ClientIpHeader);
            } else if (name.equals("DebugEnabled")) {
               oldVal = this._DebugEnabled;
               this._DebugEnabled = (Boolean)v;
               this._postSet(45, oldVal, this._DebugEnabled);
            } else if (name.equals("DefaultWebApp")) {
               WebAppComponentMBean oldVal = this._DefaultWebApp;
               this._DefaultWebApp = (WebAppComponentMBean)v;
               this._postSet(37, oldVal, this._DefaultWebApp);
            } else if (name.equals("DefaultWebAppContextRoot")) {
               oldVal = this._DefaultWebAppContextRoot;
               this._DefaultWebAppContextRoot = (String)v;
               this._postSet(36, oldVal, this._DefaultWebAppContextRoot);
            } else if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else {
               int oldVal;
               if (name.equals("FrontendHTTPPort")) {
                  oldVal = this._FrontendHTTPPort;
                  this._FrontendHTTPPort = (Integer)v;
                  this._postSet(18, oldVal, this._FrontendHTTPPort);
               } else if (name.equals("FrontendHTTPSPort")) {
                  oldVal = this._FrontendHTTPSPort;
                  this._FrontendHTTPSPort = (Integer)v;
                  this._postSet(19, oldVal, this._FrontendHTTPSPort);
               } else if (name.equals("FrontendHost")) {
                  oldVal = this._FrontendHost;
                  this._FrontendHost = (String)v;
                  this._postSet(17, oldVal, this._FrontendHost);
               } else if (name.equals("HttpsKeepAliveSecs")) {
                  oldVal = this._HttpsKeepAliveSecs;
                  this._HttpsKeepAliveSecs = (Integer)v;
                  this._postSet(29, oldVal, this._HttpsKeepAliveSecs);
               } else if (name.equals("KeepAliveEnabled")) {
                  oldVal = this._KeepAliveEnabled;
                  this._KeepAliveEnabled = (Boolean)v;
                  this._postSet(27, oldVal, this._KeepAliveEnabled);
               } else if (name.equals("KeepAliveSecs")) {
                  oldVal = this._KeepAliveSecs;
                  this._KeepAliveSecs = (Integer)v;
                  this._postSet(28, oldVal, this._KeepAliveSecs);
               } else if (name.equals("LogFileBufferKBytes")) {
                  oldVal = this._LogFileBufferKBytes;
                  this._LogFileBufferKBytes = (Integer)v;
                  this._postSet(20, oldVal, this._LogFileBufferKBytes);
               } else if (name.equals("LogFileCount")) {
                  oldVal = this._LogFileCount;
                  this._LogFileCount = (Integer)v;
                  this._postSet(51, oldVal, this._LogFileCount);
               } else if (name.equals("LogFileFlushSecs")) {
                  oldVal = this._LogFileFlushSecs;
                  this._LogFileFlushSecs = (Integer)v;
                  this._postSet(25, oldVal, this._LogFileFlushSecs);
               } else if (name.equals("LogFileFormat")) {
                  oldVal = this._LogFileFormat;
                  this._LogFileFormat = (String)v;
                  this._postSet(14, oldVal, this._LogFileFormat);
               } else if (name.equals("LogFileLimitEnabled")) {
                  oldVal = this._LogFileLimitEnabled;
                  this._LogFileLimitEnabled = (Boolean)v;
                  this._postSet(50, oldVal, this._LogFileLimitEnabled);
               } else if (name.equals("LogFileName")) {
                  oldVal = this._LogFileName;
                  this._LogFileName = (String)v;
                  this._postSet(16, oldVal, this._LogFileName);
               } else if (name.equals("LogRotationPeriodMins")) {
                  oldVal = this._LogRotationPeriodMins;
                  this._LogRotationPeriodMins = (Integer)v;
                  this._postSet(23, oldVal, this._LogRotationPeriodMins);
               } else if (name.equals("LogRotationTimeBegin")) {
                  oldVal = this._LogRotationTimeBegin;
                  this._LogRotationTimeBegin = (String)v;
                  this._postSet(26, oldVal, this._LogRotationTimeBegin);
               } else if (name.equals("LogRotationType")) {
                  oldVal = this._LogRotationType;
                  this._LogRotationType = (String)v;
                  this._postSet(22, oldVal, this._LogRotationType);
               } else if (name.equals("LogTimeInGMT")) {
                  oldVal = this._LogTimeInGMT;
                  this._LogTimeInGMT = (Boolean)v;
                  this._postSet(15, oldVal, this._LogTimeInGMT);
               } else if (name.equals("LoggingEnabled")) {
                  oldVal = this._LoggingEnabled;
                  this._LoggingEnabled = (Boolean)v;
                  this._postSet(13, oldVal, this._LoggingEnabled);
               } else if (name.equals("MaxLogFileSizeKBytes")) {
                  oldVal = this._MaxLogFileSizeKBytes;
                  this._MaxLogFileSizeKBytes = (Integer)v;
                  this._postSet(21, oldVal, this._MaxLogFileSizeKBytes);
               } else if (name.equals("MaxPostSize")) {
                  oldVal = this._MaxPostSize;
                  this._MaxPostSize = (Integer)v;
                  this._postSet(32, oldVal, this._MaxPostSize);
               } else if (name.equals("MaxPostTimeSecs")) {
                  oldVal = this._MaxPostTimeSecs;
                  this._MaxPostTimeSecs = (Integer)v;
                  this._postSet(31, oldVal, this._MaxPostTimeSecs);
               } else if (name.equals("MaxRequestParameterCount")) {
                  oldVal = this._MaxRequestParameterCount;
                  this._MaxRequestParameterCount = (Integer)v;
                  this._postSet(33, oldVal, this._MaxRequestParameterCount);
               } else if (name.equals("MaxRequestParamterCount")) {
                  oldVal = this._MaxRequestParamterCount;
                  this._MaxRequestParamterCount = (Integer)v;
                  this._postSet(34, oldVal, this._MaxRequestParamterCount);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("OverloadResponseCode")) {
                  oldVal = this._OverloadResponseCode;
                  this._OverloadResponseCode = (Integer)v;
                  this._postSet(24, oldVal, this._OverloadResponseCode);
               } else if (name.equals("PostTimeoutSecs")) {
                  oldVal = this._PostTimeoutSecs;
                  this._PostTimeoutSecs = (Integer)v;
                  this._postSet(30, oldVal, this._PostTimeoutSecs);
               } else if (name.equals("SendServerHeaderEnabled")) {
                  oldVal = this._SendServerHeaderEnabled;
                  this._SendServerHeaderEnabled = (Boolean)v;
                  this._postSet(35, oldVal, this._SendServerHeaderEnabled);
               } else if (name.equals("SingleSignonDisabled")) {
                  oldVal = this._SingleSignonDisabled;
                  this._SingleSignonDisabled = (Boolean)v;
                  this._postSet(48, oldVal, this._SingleSignonDisabled);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("URLResource")) {
                  oldVal = this._URLResource;
                  this._URLResource = (Map)v;
                  this._postSet(39, oldVal, this._URLResource);
               } else if (name.equals("UseHeaderEncoding")) {
                  oldVal = this._UseHeaderEncoding;
                  this._UseHeaderEncoding = (Boolean)v;
                  this._postSet(42, oldVal, this._UseHeaderEncoding);
               } else if (name.equals("UseHighestCompatibleHTTPVersion")) {
                  oldVal = this._UseHighestCompatibleHTTPVersion;
                  this._UseHighestCompatibleHTTPVersion = (Boolean)v;
                  this._postSet(41, oldVal, this._UseHighestCompatibleHTTPVersion);
               } else if (name.equals("WAPEnabled")) {
                  oldVal = this._WAPEnabled;
                  this._WAPEnabled = (Boolean)v;
                  this._postSet(46, oldVal, this._WAPEnabled);
               } else if (name.equals("WebDeployments")) {
                  WebDeploymentMBean[] oldVal = this._WebDeployments;
                  this._WebDeployments = (WebDeploymentMBean[])((WebDeploymentMBean[])v);
                  this._postSet(49, oldVal, this._WebDeployments);
               } else if (name.equals("WebServerLog")) {
                  WebServerLogMBean oldVal = this._WebServerLog;
                  this._WebServerLog = (WebServerLogMBean)v;
                  this._postSet(12, oldVal, this._WebServerLog);
               } else if (name.equals("WorkManagerForRemoteSessionFetching")) {
                  oldVal = this._WorkManagerForRemoteSessionFetching;
                  this._WorkManagerForRemoteSessionFetching = (String)v;
                  this._postSet(52, oldVal, this._WorkManagerForRemoteSessionFetching);
               } else if (name.equals("WriteChunkBytes")) {
                  oldVal = this._WriteChunkBytes;
                  this._WriteChunkBytes = (Integer)v;
                  this._postSet(44, oldVal, this._WriteChunkBytes);
               } else if (name.equals("customizer")) {
                  WebServer oldVal = this._customizer;
                  this._customizer = (WebServer)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AcceptContextPathInGetRealPath")) {
         return new Boolean(this._AcceptContextPathInGetRealPath);
      } else if (name.equals("AuthCookieEnabled")) {
         return new Boolean(this._AuthCookieEnabled);
      } else if (name.equals("Charsets")) {
         return this._Charsets;
      } else if (name.equals("ChunkedTransferDisabled")) {
         return new Boolean(this._ChunkedTransferDisabled);
      } else if (name.equals("ClientIpHeader")) {
         return this._ClientIpHeader;
      } else if (name.equals("DebugEnabled")) {
         return new Boolean(this._DebugEnabled);
      } else if (name.equals("DefaultWebApp")) {
         return this._DefaultWebApp;
      } else if (name.equals("DefaultWebAppContextRoot")) {
         return this._DefaultWebAppContextRoot;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("FrontendHTTPPort")) {
         return new Integer(this._FrontendHTTPPort);
      } else if (name.equals("FrontendHTTPSPort")) {
         return new Integer(this._FrontendHTTPSPort);
      } else if (name.equals("FrontendHost")) {
         return this._FrontendHost;
      } else if (name.equals("HttpsKeepAliveSecs")) {
         return new Integer(this._HttpsKeepAliveSecs);
      } else if (name.equals("KeepAliveEnabled")) {
         return new Boolean(this._KeepAliveEnabled);
      } else if (name.equals("KeepAliveSecs")) {
         return new Integer(this._KeepAliveSecs);
      } else if (name.equals("LogFileBufferKBytes")) {
         return new Integer(this._LogFileBufferKBytes);
      } else if (name.equals("LogFileCount")) {
         return new Integer(this._LogFileCount);
      } else if (name.equals("LogFileFlushSecs")) {
         return new Integer(this._LogFileFlushSecs);
      } else if (name.equals("LogFileFormat")) {
         return this._LogFileFormat;
      } else if (name.equals("LogFileLimitEnabled")) {
         return new Boolean(this._LogFileLimitEnabled);
      } else if (name.equals("LogFileName")) {
         return this._LogFileName;
      } else if (name.equals("LogRotationPeriodMins")) {
         return new Integer(this._LogRotationPeriodMins);
      } else if (name.equals("LogRotationTimeBegin")) {
         return this._LogRotationTimeBegin;
      } else if (name.equals("LogRotationType")) {
         return this._LogRotationType;
      } else if (name.equals("LogTimeInGMT")) {
         return new Boolean(this._LogTimeInGMT);
      } else if (name.equals("LoggingEnabled")) {
         return new Boolean(this._LoggingEnabled);
      } else if (name.equals("MaxLogFileSizeKBytes")) {
         return new Integer(this._MaxLogFileSizeKBytes);
      } else if (name.equals("MaxPostSize")) {
         return new Integer(this._MaxPostSize);
      } else if (name.equals("MaxPostTimeSecs")) {
         return new Integer(this._MaxPostTimeSecs);
      } else if (name.equals("MaxRequestParameterCount")) {
         return new Integer(this._MaxRequestParameterCount);
      } else if (name.equals("MaxRequestParamterCount")) {
         return new Integer(this._MaxRequestParamterCount);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OverloadResponseCode")) {
         return new Integer(this._OverloadResponseCode);
      } else if (name.equals("PostTimeoutSecs")) {
         return new Integer(this._PostTimeoutSecs);
      } else if (name.equals("SendServerHeaderEnabled")) {
         return new Boolean(this._SendServerHeaderEnabled);
      } else if (name.equals("SingleSignonDisabled")) {
         return new Boolean(this._SingleSignonDisabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("URLResource")) {
         return this._URLResource;
      } else if (name.equals("UseHeaderEncoding")) {
         return new Boolean(this._UseHeaderEncoding);
      } else if (name.equals("UseHighestCompatibleHTTPVersion")) {
         return new Boolean(this._UseHighestCompatibleHTTPVersion);
      } else if (name.equals("WAPEnabled")) {
         return new Boolean(this._WAPEnabled);
      } else if (name.equals("WebDeployments")) {
         return this._WebDeployments;
      } else if (name.equals("WebServerLog")) {
         return this._WebServerLog;
      } else if (name.equals("WorkManagerForRemoteSessionFetching")) {
         return this._WorkManagerForRemoteSessionFetching;
      } else if (name.equals("WriteChunkBytes")) {
         return new Integer(this._WriteChunkBytes);
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
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 20:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 37:
            case 38:
            case 39:
            default:
               break;
            case 8:
               if (s.equals("charsets")) {
                  return 38;
               }
               break;
            case 11:
               if (s.equals("wap-enabled")) {
                  return 46;
               }
               break;
            case 12:
               if (s.equals("url-resource")) {
                  return 39;
               }
               break;
            case 13:
               if (s.equals("frontend-host")) {
                  return 17;
               }

               if (s.equals("log-file-name")) {
                  return 16;
               }

               if (s.equals("max-post-size")) {
                  return 32;
               }

               if (s.equals("debug-enabled")) {
                  return 45;
               }
               break;
            case 14:
               if (s.equals("log-file-count")) {
                  return 51;
               }

               if (s.equals("log-time-ingmt")) {
                  return 15;
               }

               if (s.equals("web-deployment")) {
                  return 49;
               }

               if (s.equals("web-server-log")) {
                  return 12;
               }
               break;
            case 15:
               if (s.equals("default-web-app")) {
                  return 37;
               }

               if (s.equals("keep-alive-secs")) {
                  return 28;
               }

               if (s.equals("log-file-format")) {
                  return 14;
               }

               if (s.equals("logging-enabled")) {
                  return 13;
               }
               break;
            case 16:
               if (s.equals("client-ip-header")) {
                  return 53;
               }
               break;
            case 17:
               if (s.equals("frontendhttp-port")) {
                  return 18;
               }

               if (s.equals("log-rotation-type")) {
                  return 22;
               }

               if (s.equals("post-timeout-secs")) {
                  return 30;
               }

               if (s.equals("write-chunk-bytes")) {
                  return 44;
               }
               break;
            case 18:
               if (s.equals("frontendhttps-port")) {
                  return 19;
               }

               if (s.equals("max-post-time-secs")) {
                  return 31;
               }

               if (s.equals("keep-alive-enabled")) {
                  return 27;
               }
               break;
            case 19:
               if (s.equals("log-file-flush-secs")) {
                  return 25;
               }

               if (s.equals("auth-cookie-enabled")) {
                  return 43;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }

               if (s.equals("use-header-encoding")) {
                  return 42;
               }
               break;
            case 21:
               if (s.equals("https-keep-alive-secs")) {
                  return 29;
               }
               break;
            case 22:
               if (s.equals("log-file-bufferk-bytes")) {
                  return 20;
               }

               if (s.equals("overload-response-code")) {
                  return 24;
               }

               if (s.equals("log-file-limit-enabled")) {
                  return 50;
               }

               if (s.equals("single-signon-disabled")) {
                  return 48;
               }
               break;
            case 23:
               if (s.equals("log-rotation-time-begin")) {
                  return 26;
               }
               break;
            case 24:
               if (s.equals("log-rotation-period-mins")) {
                  return 23;
               }

               if (s.equals("max-log-file-sizek-bytes")) {
                  return 21;
               }
               break;
            case 25:
               if (s.equals("chunked-transfer-disabled")) {
                  return 40;
               }
               break;
            case 26:
               if (s.equals("max-request-paramter-count")) {
                  return 34;
               }

               if (s.equals("send-server-header-enabled")) {
                  return 35;
               }
               break;
            case 27:
               if (s.equals("max-request-parameter-count")) {
                  return 33;
               }
               break;
            case 28:
               if (s.equals("default-web-app-context-root")) {
                  return 36;
               }
               break;
            case 34:
               if (s.equals("use-highest-compatiblehttp-version")) {
                  return 41;
               }
               break;
            case 36:
               if (s.equals("accept-context-path-in-get-real-path")) {
                  return 47;
               }
               break;
            case 40:
               if (s.equals("work-manager-for-remote-session-fetching")) {
                  return 52;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 12:
               return new WebServerLogMBeanImpl.SchemaHelper2();
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
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 12:
               return "web-server-log";
            case 13:
               return "logging-enabled";
            case 14:
               return "log-file-format";
            case 15:
               return "log-time-ingmt";
            case 16:
               return "log-file-name";
            case 17:
               return "frontend-host";
            case 18:
               return "frontendhttp-port";
            case 19:
               return "frontendhttps-port";
            case 20:
               return "log-file-bufferk-bytes";
            case 21:
               return "max-log-file-sizek-bytes";
            case 22:
               return "log-rotation-type";
            case 23:
               return "log-rotation-period-mins";
            case 24:
               return "overload-response-code";
            case 25:
               return "log-file-flush-secs";
            case 26:
               return "log-rotation-time-begin";
            case 27:
               return "keep-alive-enabled";
            case 28:
               return "keep-alive-secs";
            case 29:
               return "https-keep-alive-secs";
            case 30:
               return "post-timeout-secs";
            case 31:
               return "max-post-time-secs";
            case 32:
               return "max-post-size";
            case 33:
               return "max-request-parameter-count";
            case 34:
               return "max-request-paramter-count";
            case 35:
               return "send-server-header-enabled";
            case 36:
               return "default-web-app-context-root";
            case 37:
               return "default-web-app";
            case 38:
               return "charsets";
            case 39:
               return "url-resource";
            case 40:
               return "chunked-transfer-disabled";
            case 41:
               return "use-highest-compatiblehttp-version";
            case 42:
               return "use-header-encoding";
            case 43:
               return "auth-cookie-enabled";
            case 44:
               return "write-chunk-bytes";
            case 45:
               return "debug-enabled";
            case 46:
               return "wap-enabled";
            case 47:
               return "accept-context-path-in-get-real-path";
            case 48:
               return "single-signon-disabled";
            case 49:
               return "web-deployment";
            case 50:
               return "log-file-limit-enabled";
            case 51:
               return "log-file-count";
            case 52:
               return "work-manager-for-remote-session-fetching";
            case 53:
               return "client-ip-header";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 49:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
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
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 38:
            case 39:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            default:
               return super.isConfigurable(propIndex);
            case 36:
               return true;
            case 37:
               return true;
            case 40:
               return true;
            case 41:
               return true;
            case 42:
               return true;
            case 43:
               return true;
            case 52:
               return true;
            case 53:
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private WebServerMBeanImpl bean;

      protected Helper(WebServerMBeanImpl bean) {
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
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 12:
               return "WebServerLog";
            case 13:
               return "LoggingEnabled";
            case 14:
               return "LogFileFormat";
            case 15:
               return "LogTimeInGMT";
            case 16:
               return "LogFileName";
            case 17:
               return "FrontendHost";
            case 18:
               return "FrontendHTTPPort";
            case 19:
               return "FrontendHTTPSPort";
            case 20:
               return "LogFileBufferKBytes";
            case 21:
               return "MaxLogFileSizeKBytes";
            case 22:
               return "LogRotationType";
            case 23:
               return "LogRotationPeriodMins";
            case 24:
               return "OverloadResponseCode";
            case 25:
               return "LogFileFlushSecs";
            case 26:
               return "LogRotationTimeBegin";
            case 27:
               return "KeepAliveEnabled";
            case 28:
               return "KeepAliveSecs";
            case 29:
               return "HttpsKeepAliveSecs";
            case 30:
               return "PostTimeoutSecs";
            case 31:
               return "MaxPostTimeSecs";
            case 32:
               return "MaxPostSize";
            case 33:
               return "MaxRequestParameterCount";
            case 34:
               return "MaxRequestParamterCount";
            case 35:
               return "SendServerHeaderEnabled";
            case 36:
               return "DefaultWebAppContextRoot";
            case 37:
               return "DefaultWebApp";
            case 38:
               return "Charsets";
            case 39:
               return "URLResource";
            case 40:
               return "ChunkedTransferDisabled";
            case 41:
               return "UseHighestCompatibleHTTPVersion";
            case 42:
               return "UseHeaderEncoding";
            case 43:
               return "AuthCookieEnabled";
            case 44:
               return "WriteChunkBytes";
            case 45:
               return "DebugEnabled";
            case 46:
               return "WAPEnabled";
            case 47:
               return "AcceptContextPathInGetRealPath";
            case 48:
               return "SingleSignonDisabled";
            case 49:
               return "WebDeployments";
            case 50:
               return "LogFileLimitEnabled";
            case 51:
               return "LogFileCount";
            case 52:
               return "WorkManagerForRemoteSessionFetching";
            case 53:
               return "ClientIpHeader";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Charsets")) {
            return 38;
         } else if (propName.equals("ClientIpHeader")) {
            return 53;
         } else if (propName.equals("DefaultWebApp")) {
            return 37;
         } else if (propName.equals("DefaultWebAppContextRoot")) {
            return 36;
         } else if (propName.equals("FrontendHTTPPort")) {
            return 18;
         } else if (propName.equals("FrontendHTTPSPort")) {
            return 19;
         } else if (propName.equals("FrontendHost")) {
            return 17;
         } else if (propName.equals("HttpsKeepAliveSecs")) {
            return 29;
         } else if (propName.equals("KeepAliveSecs")) {
            return 28;
         } else if (propName.equals("LogFileBufferKBytes")) {
            return 20;
         } else if (propName.equals("LogFileCount")) {
            return 51;
         } else if (propName.equals("LogFileFlushSecs")) {
            return 25;
         } else if (propName.equals("LogFileFormat")) {
            return 14;
         } else if (propName.equals("LogFileName")) {
            return 16;
         } else if (propName.equals("LogRotationPeriodMins")) {
            return 23;
         } else if (propName.equals("LogRotationTimeBegin")) {
            return 26;
         } else if (propName.equals("LogRotationType")) {
            return 22;
         } else if (propName.equals("LogTimeInGMT")) {
            return 15;
         } else if (propName.equals("MaxLogFileSizeKBytes")) {
            return 21;
         } else if (propName.equals("MaxPostSize")) {
            return 32;
         } else if (propName.equals("MaxPostTimeSecs")) {
            return 31;
         } else if (propName.equals("MaxRequestParameterCount")) {
            return 33;
         } else if (propName.equals("MaxRequestParamterCount")) {
            return 34;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("OverloadResponseCode")) {
            return 24;
         } else if (propName.equals("PostTimeoutSecs")) {
            return 30;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("URLResource")) {
            return 39;
         } else if (propName.equals("WebDeployments")) {
            return 49;
         } else if (propName.equals("WebServerLog")) {
            return 12;
         } else if (propName.equals("WorkManagerForRemoteSessionFetching")) {
            return 52;
         } else if (propName.equals("WriteChunkBytes")) {
            return 44;
         } else if (propName.equals("AcceptContextPathInGetRealPath")) {
            return 47;
         } else if (propName.equals("AuthCookieEnabled")) {
            return 43;
         } else if (propName.equals("ChunkedTransferDisabled")) {
            return 40;
         } else if (propName.equals("DebugEnabled")) {
            return 45;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("KeepAliveEnabled")) {
            return 27;
         } else if (propName.equals("LogFileLimitEnabled")) {
            return 50;
         } else if (propName.equals("LoggingEnabled")) {
            return 13;
         } else if (propName.equals("SendServerHeaderEnabled")) {
            return 35;
         } else if (propName.equals("SingleSignonDisabled")) {
            return 48;
         } else if (propName.equals("UseHeaderEncoding")) {
            return 42;
         } else if (propName.equals("UseHighestCompatibleHTTPVersion")) {
            return 41;
         } else {
            return propName.equals("WAPEnabled") ? 46 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getWebServerLog() != null) {
            iterators.add(new ArrayIterator(new WebServerLogMBean[]{this.bean.getWebServerLog()}));
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
            if (this.bean.isCharsetsSet()) {
               buf.append("Charsets");
               buf.append(String.valueOf(this.bean.getCharsets()));
            }

            if (this.bean.isClientIpHeaderSet()) {
               buf.append("ClientIpHeader");
               buf.append(String.valueOf(this.bean.getClientIpHeader()));
            }

            if (this.bean.isDefaultWebAppSet()) {
               buf.append("DefaultWebApp");
               buf.append(String.valueOf(this.bean.getDefaultWebApp()));
            }

            if (this.bean.isDefaultWebAppContextRootSet()) {
               buf.append("DefaultWebAppContextRoot");
               buf.append(String.valueOf(this.bean.getDefaultWebAppContextRoot()));
            }

            if (this.bean.isFrontendHTTPPortSet()) {
               buf.append("FrontendHTTPPort");
               buf.append(String.valueOf(this.bean.getFrontendHTTPPort()));
            }

            if (this.bean.isFrontendHTTPSPortSet()) {
               buf.append("FrontendHTTPSPort");
               buf.append(String.valueOf(this.bean.getFrontendHTTPSPort()));
            }

            if (this.bean.isFrontendHostSet()) {
               buf.append("FrontendHost");
               buf.append(String.valueOf(this.bean.getFrontendHost()));
            }

            if (this.bean.isHttpsKeepAliveSecsSet()) {
               buf.append("HttpsKeepAliveSecs");
               buf.append(String.valueOf(this.bean.getHttpsKeepAliveSecs()));
            }

            if (this.bean.isKeepAliveSecsSet()) {
               buf.append("KeepAliveSecs");
               buf.append(String.valueOf(this.bean.getKeepAliveSecs()));
            }

            if (this.bean.isLogFileBufferKBytesSet()) {
               buf.append("LogFileBufferKBytes");
               buf.append(String.valueOf(this.bean.getLogFileBufferKBytes()));
            }

            if (this.bean.isLogFileCountSet()) {
               buf.append("LogFileCount");
               buf.append(String.valueOf(this.bean.getLogFileCount()));
            }

            if (this.bean.isLogFileFlushSecsSet()) {
               buf.append("LogFileFlushSecs");
               buf.append(String.valueOf(this.bean.getLogFileFlushSecs()));
            }

            if (this.bean.isLogFileFormatSet()) {
               buf.append("LogFileFormat");
               buf.append(String.valueOf(this.bean.getLogFileFormat()));
            }

            if (this.bean.isLogFileNameSet()) {
               buf.append("LogFileName");
               buf.append(String.valueOf(this.bean.getLogFileName()));
            }

            if (this.bean.isLogRotationPeriodMinsSet()) {
               buf.append("LogRotationPeriodMins");
               buf.append(String.valueOf(this.bean.getLogRotationPeriodMins()));
            }

            if (this.bean.isLogRotationTimeBeginSet()) {
               buf.append("LogRotationTimeBegin");
               buf.append(String.valueOf(this.bean.getLogRotationTimeBegin()));
            }

            if (this.bean.isLogRotationTypeSet()) {
               buf.append("LogRotationType");
               buf.append(String.valueOf(this.bean.getLogRotationType()));
            }

            if (this.bean.isLogTimeInGMTSet()) {
               buf.append("LogTimeInGMT");
               buf.append(String.valueOf(this.bean.getLogTimeInGMT()));
            }

            if (this.bean.isMaxLogFileSizeKBytesSet()) {
               buf.append("MaxLogFileSizeKBytes");
               buf.append(String.valueOf(this.bean.getMaxLogFileSizeKBytes()));
            }

            if (this.bean.isMaxPostSizeSet()) {
               buf.append("MaxPostSize");
               buf.append(String.valueOf(this.bean.getMaxPostSize()));
            }

            if (this.bean.isMaxPostTimeSecsSet()) {
               buf.append("MaxPostTimeSecs");
               buf.append(String.valueOf(this.bean.getMaxPostTimeSecs()));
            }

            if (this.bean.isMaxRequestParameterCountSet()) {
               buf.append("MaxRequestParameterCount");
               buf.append(String.valueOf(this.bean.getMaxRequestParameterCount()));
            }

            if (this.bean.isMaxRequestParamterCountSet()) {
               buf.append("MaxRequestParamterCount");
               buf.append(String.valueOf(this.bean.getMaxRequestParamterCount()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isOverloadResponseCodeSet()) {
               buf.append("OverloadResponseCode");
               buf.append(String.valueOf(this.bean.getOverloadResponseCode()));
            }

            if (this.bean.isPostTimeoutSecsSet()) {
               buf.append("PostTimeoutSecs");
               buf.append(String.valueOf(this.bean.getPostTimeoutSecs()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isURLResourceSet()) {
               buf.append("URLResource");
               buf.append(String.valueOf(this.bean.getURLResource()));
            }

            if (this.bean.isWebDeploymentsSet()) {
               buf.append("WebDeployments");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getWebDeployments())));
            }

            childValue = this.computeChildHashValue(this.bean.getWebServerLog());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWorkManagerForRemoteSessionFetchingSet()) {
               buf.append("WorkManagerForRemoteSessionFetching");
               buf.append(String.valueOf(this.bean.getWorkManagerForRemoteSessionFetching()));
            }

            if (this.bean.isWriteChunkBytesSet()) {
               buf.append("WriteChunkBytes");
               buf.append(String.valueOf(this.bean.getWriteChunkBytes()));
            }

            if (this.bean.isAcceptContextPathInGetRealPathSet()) {
               buf.append("AcceptContextPathInGetRealPath");
               buf.append(String.valueOf(this.bean.isAcceptContextPathInGetRealPath()));
            }

            if (this.bean.isAuthCookieEnabledSet()) {
               buf.append("AuthCookieEnabled");
               buf.append(String.valueOf(this.bean.isAuthCookieEnabled()));
            }

            if (this.bean.isChunkedTransferDisabledSet()) {
               buf.append("ChunkedTransferDisabled");
               buf.append(String.valueOf(this.bean.isChunkedTransferDisabled()));
            }

            if (this.bean.isDebugEnabledSet()) {
               buf.append("DebugEnabled");
               buf.append(String.valueOf(this.bean.isDebugEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isKeepAliveEnabledSet()) {
               buf.append("KeepAliveEnabled");
               buf.append(String.valueOf(this.bean.isKeepAliveEnabled()));
            }

            if (this.bean.isLogFileLimitEnabledSet()) {
               buf.append("LogFileLimitEnabled");
               buf.append(String.valueOf(this.bean.isLogFileLimitEnabled()));
            }

            if (this.bean.isLoggingEnabledSet()) {
               buf.append("LoggingEnabled");
               buf.append(String.valueOf(this.bean.isLoggingEnabled()));
            }

            if (this.bean.isSendServerHeaderEnabledSet()) {
               buf.append("SendServerHeaderEnabled");
               buf.append(String.valueOf(this.bean.isSendServerHeaderEnabled()));
            }

            if (this.bean.isSingleSignonDisabledSet()) {
               buf.append("SingleSignonDisabled");
               buf.append(String.valueOf(this.bean.isSingleSignonDisabled()));
            }

            if (this.bean.isUseHeaderEncodingSet()) {
               buf.append("UseHeaderEncoding");
               buf.append(String.valueOf(this.bean.isUseHeaderEncoding()));
            }

            if (this.bean.isUseHighestCompatibleHTTPVersionSet()) {
               buf.append("UseHighestCompatibleHTTPVersion");
               buf.append(String.valueOf(this.bean.isUseHighestCompatibleHTTPVersion()));
            }

            if (this.bean.isWAPEnabledSet()) {
               buf.append("WAPEnabled");
               buf.append(String.valueOf(this.bean.isWAPEnabled()));
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
            WebServerMBeanImpl otherTyped = (WebServerMBeanImpl)other;
            this.computeDiff("Charsets", this.bean.getCharsets(), otherTyped.getCharsets(), true);
            this.computeDiff("ClientIpHeader", this.bean.getClientIpHeader(), otherTyped.getClientIpHeader(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DefaultWebApp", this.bean.getDefaultWebApp(), otherTyped.getDefaultWebApp(), true);
            }

            this.computeDiff("DefaultWebAppContextRoot", this.bean.getDefaultWebAppContextRoot(), otherTyped.getDefaultWebAppContextRoot(), true);
            this.computeDiff("FrontendHTTPPort", this.bean.getFrontendHTTPPort(), otherTyped.getFrontendHTTPPort(), true);
            this.computeDiff("FrontendHTTPSPort", this.bean.getFrontendHTTPSPort(), otherTyped.getFrontendHTTPSPort(), true);
            this.computeDiff("FrontendHost", this.bean.getFrontendHost(), otherTyped.getFrontendHost(), true);
            this.computeDiff("HttpsKeepAliveSecs", this.bean.getHttpsKeepAliveSecs(), otherTyped.getHttpsKeepAliveSecs(), true);
            this.computeDiff("KeepAliveSecs", this.bean.getKeepAliveSecs(), otherTyped.getKeepAliveSecs(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileBufferKBytes", this.bean.getLogFileBufferKBytes(), otherTyped.getLogFileBufferKBytes(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileCount", this.bean.getLogFileCount(), otherTyped.getLogFileCount(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileFlushSecs", this.bean.getLogFileFlushSecs(), otherTyped.getLogFileFlushSecs(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileFormat", this.bean.getLogFileFormat(), otherTyped.getLogFileFormat(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileName", this.bean.getLogFileName(), otherTyped.getLogFileName(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogRotationPeriodMins", this.bean.getLogRotationPeriodMins(), otherTyped.getLogRotationPeriodMins(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogRotationTimeBegin", this.bean.getLogRotationTimeBegin(), otherTyped.getLogRotationTimeBegin(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogRotationType", this.bean.getLogRotationType(), otherTyped.getLogRotationType(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogTimeInGMT", this.bean.getLogTimeInGMT(), otherTyped.getLogTimeInGMT(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("MaxLogFileSizeKBytes", this.bean.getMaxLogFileSizeKBytes(), otherTyped.getMaxLogFileSizeKBytes(), false);
            }

            this.computeDiff("MaxPostSize", this.bean.getMaxPostSize(), otherTyped.getMaxPostSize(), true);
            this.computeDiff("MaxPostTimeSecs", this.bean.getMaxPostTimeSecs(), otherTyped.getMaxPostTimeSecs(), true);
            this.computeDiff("MaxRequestParameterCount", this.bean.getMaxRequestParameterCount(), otherTyped.getMaxRequestParameterCount(), true);
            this.computeDiff("MaxRequestParamterCount", this.bean.getMaxRequestParamterCount(), otherTyped.getMaxRequestParamterCount(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("OverloadResponseCode", this.bean.getOverloadResponseCode(), otherTyped.getOverloadResponseCode(), true);
            this.computeDiff("PostTimeoutSecs", this.bean.getPostTimeoutSecs(), otherTyped.getPostTimeoutSecs(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("URLResource", this.bean.getURLResource(), otherTyped.getURLResource(), true);
            this.computeDiff("WebDeployments", this.bean.getWebDeployments(), otherTyped.getWebDeployments(), true);
            this.computeSubDiff("WebServerLog", this.bean.getWebServerLog(), otherTyped.getWebServerLog());
            this.computeDiff("WorkManagerForRemoteSessionFetching", this.bean.getWorkManagerForRemoteSessionFetching(), otherTyped.getWorkManagerForRemoteSessionFetching(), false);
            this.computeDiff("WriteChunkBytes", this.bean.getWriteChunkBytes(), otherTyped.getWriteChunkBytes(), true);
            this.computeDiff("AcceptContextPathInGetRealPath", this.bean.isAcceptContextPathInGetRealPath(), otherTyped.isAcceptContextPathInGetRealPath(), false);
            this.computeDiff("AuthCookieEnabled", this.bean.isAuthCookieEnabled(), otherTyped.isAuthCookieEnabled(), true);
            this.computeDiff("ChunkedTransferDisabled", this.bean.isChunkedTransferDisabled(), otherTyped.isChunkedTransferDisabled(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("DebugEnabled", this.bean.isDebugEnabled(), otherTyped.isDebugEnabled(), false);
            }

            this.computeDiff("KeepAliveEnabled", this.bean.isKeepAliveEnabled(), otherTyped.isKeepAliveEnabled(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileLimitEnabled", this.bean.isLogFileLimitEnabled(), otherTyped.isLogFileLimitEnabled(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LoggingEnabled", this.bean.isLoggingEnabled(), otherTyped.isLoggingEnabled(), false);
            }

            this.computeDiff("SendServerHeaderEnabled", this.bean.isSendServerHeaderEnabled(), otherTyped.isSendServerHeaderEnabled(), true);
            this.computeDiff("SingleSignonDisabled", this.bean.isSingleSignonDisabled(), otherTyped.isSingleSignonDisabled(), true);
            this.computeDiff("UseHeaderEncoding", this.bean.isUseHeaderEncoding(), otherTyped.isUseHeaderEncoding(), true);
            this.computeDiff("UseHighestCompatibleHTTPVersion", this.bean.isUseHighestCompatibleHTTPVersion(), otherTyped.isUseHighestCompatibleHTTPVersion(), true);
            this.computeDiff("WAPEnabled", this.bean.isWAPEnabled(), otherTyped.isWAPEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebServerMBeanImpl original = (WebServerMBeanImpl)event.getSourceBean();
            WebServerMBeanImpl proposed = (WebServerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Charsets")) {
                  original.setCharsets(proposed.getCharsets());
                  original._conditionalUnset(update.isUnsetUpdate(), 38);
               } else if (prop.equals("ClientIpHeader")) {
                  original.setClientIpHeader(proposed.getClientIpHeader());
                  original._conditionalUnset(update.isUnsetUpdate(), 53);
               } else if (prop.equals("DefaultWebApp")) {
                  original.setDefaultWebAppAsString(proposed.getDefaultWebAppAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("DefaultWebAppContextRoot")) {
                  original.setDefaultWebAppContextRoot(proposed.getDefaultWebAppContextRoot());
                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("FrontendHTTPPort")) {
                  original.setFrontendHTTPPort(proposed.getFrontendHTTPPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("FrontendHTTPSPort")) {
                  original.setFrontendHTTPSPort(proposed.getFrontendHTTPSPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("FrontendHost")) {
                  original.setFrontendHost(proposed.getFrontendHost());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("HttpsKeepAliveSecs")) {
                  original.setHttpsKeepAliveSecs(proposed.getHttpsKeepAliveSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("KeepAliveSecs")) {
                  original.setKeepAliveSecs(proposed.getKeepAliveSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("LogFileBufferKBytes")) {
                  original.setLogFileBufferKBytes(proposed.getLogFileBufferKBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("LogFileCount")) {
                  original.setLogFileCount(proposed.getLogFileCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 51);
               } else if (prop.equals("LogFileFlushSecs")) {
                  original.setLogFileFlushSecs(proposed.getLogFileFlushSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("LogFileFormat")) {
                  original.setLogFileFormat(proposed.getLogFileFormat());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("LogFileName")) {
                  original.setLogFileName(proposed.getLogFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("LogRotationPeriodMins")) {
                  original.setLogRotationPeriodMins(proposed.getLogRotationPeriodMins());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("LogRotationTimeBegin")) {
                  original.setLogRotationTimeBegin(proposed.getLogRotationTimeBegin());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("LogRotationType")) {
                  original.setLogRotationType(proposed.getLogRotationType());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("LogTimeInGMT")) {
                  original.setLogTimeInGMT(proposed.getLogTimeInGMT());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MaxLogFileSizeKBytes")) {
                  original.setMaxLogFileSizeKBytes(proposed.getMaxLogFileSizeKBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("MaxPostSize")) {
                  original.setMaxPostSize(proposed.getMaxPostSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("MaxPostTimeSecs")) {
                  original.setMaxPostTimeSecs(proposed.getMaxPostTimeSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("MaxRequestParameterCount")) {
                  original.setMaxRequestParameterCount(proposed.getMaxRequestParameterCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("MaxRequestParamterCount")) {
                  original.setMaxRequestParamterCount(proposed.getMaxRequestParamterCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("OverloadResponseCode")) {
                  original.setOverloadResponseCode(proposed.getOverloadResponseCode());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("PostTimeoutSecs")) {
                  original.setPostTimeoutSecs(proposed.getPostTimeoutSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 30);
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
               } else if (prop.equals("URLResource")) {
                  original.setURLResource(proposed.getURLResource());
                  original._conditionalUnset(update.isUnsetUpdate(), 39);
               } else if (prop.equals("WebDeployments")) {
                  original.setWebDeploymentsAsString(proposed.getWebDeploymentsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("WebServerLog")) {
                  if (type == 2) {
                     original.setWebServerLog((WebServerLogMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebServerLog()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WebServerLog", original.getWebServerLog());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("WorkManagerForRemoteSessionFetching")) {
                  original.setWorkManagerForRemoteSessionFetching(proposed.getWorkManagerForRemoteSessionFetching());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (prop.equals("WriteChunkBytes")) {
                  original.setWriteChunkBytes(proposed.getWriteChunkBytes());
                  original._conditionalUnset(update.isUnsetUpdate(), 44);
               } else if (prop.equals("AcceptContextPathInGetRealPath")) {
                  original.setAcceptContextPathInGetRealPath(proposed.isAcceptContextPathInGetRealPath());
                  original._conditionalUnset(update.isUnsetUpdate(), 47);
               } else if (prop.equals("AuthCookieEnabled")) {
                  original.setAuthCookieEnabled(proposed.isAuthCookieEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 43);
               } else if (prop.equals("ChunkedTransferDisabled")) {
                  original.setChunkedTransferDisabled(proposed.isChunkedTransferDisabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("DebugEnabled")) {
                  original.setDebugEnabled(proposed.isDebugEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 45);
               } else if (!prop.equals("DynamicallyCreated")) {
                  if (prop.equals("KeepAliveEnabled")) {
                     original.setKeepAliveEnabled(proposed.isKeepAliveEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  } else if (prop.equals("LogFileLimitEnabled")) {
                     original.setLogFileLimitEnabled(proposed.isLogFileLimitEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 50);
                  } else if (prop.equals("LoggingEnabled")) {
                     original.setLoggingEnabled(proposed.isLoggingEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("SendServerHeaderEnabled")) {
                     original.setSendServerHeaderEnabled(proposed.isSendServerHeaderEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  } else if (prop.equals("SingleSignonDisabled")) {
                     original.setSingleSignonDisabled(proposed.isSingleSignonDisabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 48);
                  } else if (prop.equals("UseHeaderEncoding")) {
                     original.setUseHeaderEncoding(proposed.isUseHeaderEncoding());
                     original._conditionalUnset(update.isUnsetUpdate(), 42);
                  } else if (prop.equals("UseHighestCompatibleHTTPVersion")) {
                     original.setUseHighestCompatibleHTTPVersion(proposed.isUseHighestCompatibleHTTPVersion());
                     original._conditionalUnset(update.isUnsetUpdate(), 41);
                  } else if (prop.equals("WAPEnabled")) {
                     original.setWAPEnabled(proposed.isWAPEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 46);
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
            WebServerMBeanImpl copy = (WebServerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Charsets")) && this.bean.isCharsetsSet()) {
               copy.setCharsets(this.bean.getCharsets());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientIpHeader")) && this.bean.isClientIpHeaderSet()) {
               copy.setClientIpHeader(this.bean.getClientIpHeader());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DefaultWebApp")) && this.bean.isDefaultWebAppSet()) {
               copy._unSet(copy, 37);
               copy.setDefaultWebAppAsString(this.bean.getDefaultWebAppAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultWebAppContextRoot")) && this.bean.isDefaultWebAppContextRootSet()) {
               copy.setDefaultWebAppContextRoot(this.bean.getDefaultWebAppContextRoot());
            }

            if ((excludeProps == null || !excludeProps.contains("FrontendHTTPPort")) && this.bean.isFrontendHTTPPortSet()) {
               copy.setFrontendHTTPPort(this.bean.getFrontendHTTPPort());
            }

            if ((excludeProps == null || !excludeProps.contains("FrontendHTTPSPort")) && this.bean.isFrontendHTTPSPortSet()) {
               copy.setFrontendHTTPSPort(this.bean.getFrontendHTTPSPort());
            }

            if ((excludeProps == null || !excludeProps.contains("FrontendHost")) && this.bean.isFrontendHostSet()) {
               copy.setFrontendHost(this.bean.getFrontendHost());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpsKeepAliveSecs")) && this.bean.isHttpsKeepAliveSecsSet()) {
               copy.setHttpsKeepAliveSecs(this.bean.getHttpsKeepAliveSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepAliveSecs")) && this.bean.isKeepAliveSecsSet()) {
               copy.setKeepAliveSecs(this.bean.getKeepAliveSecs());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileBufferKBytes")) && this.bean.isLogFileBufferKBytesSet()) {
               copy.setLogFileBufferKBytes(this.bean.getLogFileBufferKBytes());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileCount")) && this.bean.isLogFileCountSet()) {
               copy.setLogFileCount(this.bean.getLogFileCount());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileFlushSecs")) && this.bean.isLogFileFlushSecsSet()) {
               copy.setLogFileFlushSecs(this.bean.getLogFileFlushSecs());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileFormat")) && this.bean.isLogFileFormatSet()) {
               copy.setLogFileFormat(this.bean.getLogFileFormat());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileName")) && this.bean.isLogFileNameSet()) {
               copy.setLogFileName(this.bean.getLogFileName());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogRotationPeriodMins")) && this.bean.isLogRotationPeriodMinsSet()) {
               copy.setLogRotationPeriodMins(this.bean.getLogRotationPeriodMins());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogRotationTimeBegin")) && this.bean.isLogRotationTimeBeginSet()) {
               copy.setLogRotationTimeBegin(this.bean.getLogRotationTimeBegin());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogRotationType")) && this.bean.isLogRotationTypeSet()) {
               copy.setLogRotationType(this.bean.getLogRotationType());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogTimeInGMT")) && this.bean.isLogTimeInGMTSet()) {
               copy.setLogTimeInGMT(this.bean.getLogTimeInGMT());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("MaxLogFileSizeKBytes")) && this.bean.isMaxLogFileSizeKBytesSet()) {
               copy.setMaxLogFileSizeKBytes(this.bean.getMaxLogFileSizeKBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxPostSize")) && this.bean.isMaxPostSizeSet()) {
               copy.setMaxPostSize(this.bean.getMaxPostSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxPostTimeSecs")) && this.bean.isMaxPostTimeSecsSet()) {
               copy.setMaxPostTimeSecs(this.bean.getMaxPostTimeSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRequestParameterCount")) && this.bean.isMaxRequestParameterCountSet()) {
               copy.setMaxRequestParameterCount(this.bean.getMaxRequestParameterCount());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRequestParamterCount")) && this.bean.isMaxRequestParamterCountSet()) {
               copy.setMaxRequestParamterCount(this.bean.getMaxRequestParamterCount());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("OverloadResponseCode")) && this.bean.isOverloadResponseCodeSet()) {
               copy.setOverloadResponseCode(this.bean.getOverloadResponseCode());
            }

            if ((excludeProps == null || !excludeProps.contains("PostTimeoutSecs")) && this.bean.isPostTimeoutSecsSet()) {
               copy.setPostTimeoutSecs(this.bean.getPostTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("URLResource")) && this.bean.isURLResourceSet()) {
               copy.setURLResource(this.bean.getURLResource());
            }

            if ((excludeProps == null || !excludeProps.contains("WebDeployments")) && this.bean.isWebDeploymentsSet()) {
               copy._unSet(copy, 49);
               copy.setWebDeploymentsAsString(this.bean.getWebDeploymentsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("WebServerLog")) && this.bean.isWebServerLogSet() && !copy._isSet(12)) {
               Object o = this.bean.getWebServerLog();
               copy.setWebServerLog((WebServerLogMBean)null);
               copy.setWebServerLog(o == null ? null : (WebServerLogMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManagerForRemoteSessionFetching")) && this.bean.isWorkManagerForRemoteSessionFetchingSet()) {
               copy.setWorkManagerForRemoteSessionFetching(this.bean.getWorkManagerForRemoteSessionFetching());
            }

            if ((excludeProps == null || !excludeProps.contains("WriteChunkBytes")) && this.bean.isWriteChunkBytesSet()) {
               copy.setWriteChunkBytes(this.bean.getWriteChunkBytes());
            }

            if ((excludeProps == null || !excludeProps.contains("AcceptContextPathInGetRealPath")) && this.bean.isAcceptContextPathInGetRealPathSet()) {
               copy.setAcceptContextPathInGetRealPath(this.bean.isAcceptContextPathInGetRealPath());
            }

            if ((excludeProps == null || !excludeProps.contains("AuthCookieEnabled")) && this.bean.isAuthCookieEnabledSet()) {
               copy.setAuthCookieEnabled(this.bean.isAuthCookieEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ChunkedTransferDisabled")) && this.bean.isChunkedTransferDisabledSet()) {
               copy.setChunkedTransferDisabled(this.bean.isChunkedTransferDisabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("DebugEnabled")) && this.bean.isDebugEnabledSet()) {
               copy.setDebugEnabled(this.bean.isDebugEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("KeepAliveEnabled")) && this.bean.isKeepAliveEnabledSet()) {
               copy.setKeepAliveEnabled(this.bean.isKeepAliveEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileLimitEnabled")) && this.bean.isLogFileLimitEnabledSet()) {
               copy.setLogFileLimitEnabled(this.bean.isLogFileLimitEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LoggingEnabled")) && this.bean.isLoggingEnabledSet()) {
               copy.setLoggingEnabled(this.bean.isLoggingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SendServerHeaderEnabled")) && this.bean.isSendServerHeaderEnabledSet()) {
               copy.setSendServerHeaderEnabled(this.bean.isSendServerHeaderEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SingleSignonDisabled")) && this.bean.isSingleSignonDisabledSet()) {
               copy.setSingleSignonDisabled(this.bean.isSingleSignonDisabled());
            }

            if ((excludeProps == null || !excludeProps.contains("UseHeaderEncoding")) && this.bean.isUseHeaderEncodingSet()) {
               copy.setUseHeaderEncoding(this.bean.isUseHeaderEncoding());
            }

            if ((excludeProps == null || !excludeProps.contains("UseHighestCompatibleHTTPVersion")) && this.bean.isUseHighestCompatibleHTTPVersionSet()) {
               copy.setUseHighestCompatibleHTTPVersion(this.bean.isUseHighestCompatibleHTTPVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("WAPEnabled")) && this.bean.isWAPEnabledSet()) {
               copy.setWAPEnabled(this.bean.isWAPEnabled());
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
         this.inferSubTree(this.bean.getDefaultWebApp(), clazz, annotation);
         this.inferSubTree(this.bean.getWebDeployments(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServerLog(), clazz, annotation);
      }
   }
}

package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.mbeans.custom.NetworkAccessPoint;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class NetworkAccessPointMBeanImpl extends ConfigurationMBeanImpl implements NetworkAccessPointMBean, Serializable {
   private int _AcceptBacklog;
   private boolean _AllowUnencryptedNullCipher;
   private boolean _ChannelIdentityCustomized;
   private int _ChannelWeight;
   private String[] _Ciphersuites;
   private boolean _ClientCertificateEnforced;
   private boolean _ClientInitSecureRenegotiationAccepted;
   private String _ClusterAddress;
   private int _CompleteCOMMessageTimeout;
   private int _CompleteHTTPMessageTimeout;
   private int _CompleteIIOPMessageTimeout;
   private int _CompleteMessageTimeout;
   private int _CompleteT3MessageTimeout;
   private int _ConnectTimeout;
   private String _CustomIdentityKeyStoreFileName;
   private String _CustomIdentityKeyStorePassPhrase;
   private byte[] _CustomIdentityKeyStorePassPhraseEncrypted;
   private String _CustomIdentityKeyStoreType;
   private String _CustomPrivateKeyAlias;
   private String _CustomPrivateKeyPassPhrase;
   private byte[] _CustomPrivateKeyPassPhraseEncrypted;
   private Properties _CustomProperties;
   private boolean _Enabled;
   private String[] _ExcludedCiphersuites;
   private String _ExternalDNSName;
   private boolean _HostnameVerificationIgnored;
   private String _HostnameVerifier;
   private boolean _HttpEnabledForThisProtocol;
   private int _IdleConnectionTimeout;
   private int _IdleIIOPConnectionTimeout;
   private String _InboundCertificateValidation;
   private String _ListenAddress;
   private int _ListenPort;
   private int _LoginTimeoutMillis;
   private int _LoginTimeoutMillisSSL;
   private int _MaxBackoffBetweenFailures;
   private int _MaxConnectedClients;
   private int _MaxMessageSize;
   private String _MinimumTLSProtocolVersion;
   private String _Name;
   private String _OutboundCertificateValidation;
   private boolean _OutboundEnabled;
   private String _OutboundPrivateKeyAlias;
   private boolean _OutboundPrivateKeyEnabled;
   private String _OutboundPrivateKeyPassPhrase;
   private String _PrivateKeyAlias;
   private String _PrivateKeyPassPhrase;
   private String _Protocol;
   private String _ProxyAddress;
   private int _ProxyPort;
   private String _PublicAddress;
   private int _PublicPort;
   private boolean _ResolveDNSName;
   private boolean _SDPEnabled;
   private int _SSLListenPort;
   private boolean _SSLv2HelloEnabled;
   private boolean _TimeoutConnectionWithPendingResponses;
   private int _TunnelingClientPingSecs;
   private int _TunnelingClientTimeoutSecs;
   private boolean _TunnelingEnabled;
   private boolean _TwoWaySSLEnabled;
   private boolean _UseFastSerialization;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private NetworkAccessPointMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(NetworkAccessPointMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(NetworkAccessPointMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public NetworkAccessPointMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(NetworkAccessPointMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      NetworkAccessPointMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public NetworkAccessPointMBeanImpl() {
      this._initializeProperty(-1);
   }

   public NetworkAccessPointMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public NetworkAccessPointMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2) ? this._performMacroSubstitution(this._getDelegateBean().getName(), this) : this._Name;
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public String getProtocol() {
      if (!this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10)) {
         return this._performMacroSubstitution(this._getDelegateBean().getProtocol(), this);
      } else if (!this._isSet(10)) {
         return this._isSecureModeEnabled() ? "t3s" : "t3";
      } else {
         return this._Protocol;
      }
   }

   public boolean isProtocolInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isProtocolSet() {
      return this._isSet(10);
   }

   public void setProtocol(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._Protocol;
      this._Protocol = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getListenAddress() {
      if (!this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         return this._performMacroSubstitution(this._getDelegateBean().getListenAddress(), this);
      } else {
         if (!this._isSet(11)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getListenAddress();
            } catch (NullPointerException var2) {
            }
         }

         return this._ListenAddress;
      }
   }

   public boolean isListenAddressInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isListenAddressSet() {
      return this._isSet(11);
   }

   public void setListenAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._ListenAddress;
      this._ListenAddress = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPublicAddress() {
      if (!this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         return this._performMacroSubstitution(this._getDelegateBean().getPublicAddress(), this);
      } else {
         if (!this._isSet(12)) {
            try {
               return NetworkAccessPoint.getPublicAddress(this);
            } catch (NullPointerException var2) {
            }
         }

         return this._PublicAddress;
      }
   }

   public boolean isPublicAddressInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isPublicAddressSet() {
      return this._isSet(12);
   }

   public void setPublicAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._PublicAddress;
      this._PublicAddress = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public int getListenPort() {
      if (!this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         return this._getDelegateBean().getListenPort();
      } else {
         if (!this._isSet(13)) {
            try {
               return NetworkAccessPoint.isSecure(this) ? ((ServerTemplateMBean)this.getParent()).getSSL().getListenPort() : ((ServerTemplateMBean)this.getParent()).getListenPort();
            } catch (NullPointerException var2) {
            }
         }

         return this._ListenPort;
      }
   }

   public boolean isListenPortInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isListenPortSet() {
      return this._isSet(13);
   }

   public void setListenPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      NetworkAccessPointValidator.validateListenPort(param0);
      boolean wasSet = this._isSet(13);
      int _oldVal = this._ListenPort;
      this._ListenPort = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPublicPort() {
      if (!this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         return this._getDelegateBean().getPublicPort();
      } else {
         if (!this._isSet(14)) {
            try {
               return this.getListenPort();
            } catch (NullPointerException var2) {
            }
         }

         return this._PublicPort;
      }
   }

   public boolean isPublicPortInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isPublicPortSet() {
      return this._isSet(14);
   }

   public void setPublicPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      NetworkAccessPointValidator.validatePublicPort(param0);
      boolean wasSet = this._isSet(14);
      int _oldVal = this._PublicPort;
      this._PublicPort = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getResolveDNSName() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getResolveDNSName() : this._ResolveDNSName;
   }

   public boolean isResolveDNSNameInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isResolveDNSNameSet() {
      return this._isSet(15);
   }

   public void setResolveDNSName(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      boolean _oldVal = this._ResolveDNSName;
      this._ResolveDNSName = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public String getProxyAddress() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getProxyAddress(), this) : this._ProxyAddress;
   }

   public boolean isProxyAddressInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isProxyAddressSet() {
      return this._isSet(16);
   }

   public void setProxyAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      String _oldVal = this._ProxyAddress;
      this._ProxyAddress = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public int getProxyPort() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getProxyPort() : this._ProxyPort;
   }

   public boolean isProxyPortInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isProxyPortSet() {
      return this._isSet(17);
   }

   public void setProxyPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      int _oldVal = this._ProxyPort;
      this._ProxyPort = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isHttpEnabledForThisProtocol() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().isHttpEnabledForThisProtocol() : this._HttpEnabledForThisProtocol;
   }

   public boolean isHttpEnabledForThisProtocolInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isHttpEnabledForThisProtocolSet() {
      return this._isSet(18);
   }

   public void setHttpEnabledForThisProtocol(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      NetworkAccessPointValidator.validateHttpEnabledForThisProtocol(this, param0);
      boolean wasSet = this._isSet(18);
      boolean _oldVal = this._HttpEnabledForThisProtocol;
      this._HttpEnabledForThisProtocol = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public int getAcceptBacklog() {
      if (!this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19)) {
         return this._getDelegateBean().getAcceptBacklog();
      } else {
         if (!this._isSet(19)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getAcceptBacklog();
            } catch (NullPointerException var2) {
            }
         }

         return this._AcceptBacklog;
      }
   }

   public boolean isAcceptBacklogInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isAcceptBacklogSet() {
      return this._isSet(19);
   }

   public void setAcceptBacklog(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("AcceptBacklog", param0, 0);
      boolean wasSet = this._isSet(19);
      int _oldVal = this._AcceptBacklog;
      this._AcceptBacklog = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxBackoffBetweenFailures() {
      if (!this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20)) {
         return this._getDelegateBean().getMaxBackoffBetweenFailures();
      } else {
         if (!this._isSet(20)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getMaxBackoffBetweenFailures();
            } catch (NullPointerException var2) {
            }
         }

         return this._MaxBackoffBetweenFailures;
      }
   }

   public boolean isMaxBackoffBetweenFailuresInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isMaxBackoffBetweenFailuresSet() {
      return this._isSet(20);
   }

   public void setMaxBackoffBetweenFailures(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("MaxBackoffBetweenFailures", param0, 0);
      boolean wasSet = this._isSet(20);
      int _oldVal = this._MaxBackoffBetweenFailures;
      this._MaxBackoffBetweenFailures = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLoginTimeoutMillis() {
      if (!this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21)) {
         return this._getDelegateBean().getLoginTimeoutMillis();
      } else {
         if (!this._isSet(21)) {
            try {
               return NetworkAccessPoint.isSecure(this) ? ((ServerTemplateMBean)this.getParent()).getSSL().getLoginTimeoutMillis() : ((ServerTemplateMBean)this.getParent()).getLoginTimeoutMillis();
            } catch (NullPointerException var2) {
            }
         }

         return this._LoginTimeoutMillis;
      }
   }

   public boolean isLoginTimeoutMillisInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isLoginTimeoutMillisSet() {
      return this._isSet(21);
   }

   public void setLoginTimeoutMillis(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LoginTimeoutMillis", (long)param0, 0L, 100000L);
      boolean wasSet = this._isSet(21);
      int _oldVal = this._LoginTimeoutMillis;
      this._LoginTimeoutMillis = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public int getTunnelingClientPingSecs() {
      if (!this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22)) {
         return this._getDelegateBean().getTunnelingClientPingSecs();
      } else {
         if (!this._isSet(22)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getTunnelingClientPingSecs();
            } catch (NullPointerException var2) {
            }
         }

         return this._TunnelingClientPingSecs;
      }
   }

   public boolean isTunnelingClientPingSecsInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isTunnelingClientPingSecsSet() {
      return this._isSet(22);
   }

   public void setTunnelingClientPingSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("TunnelingClientPingSecs", param0, 1);
      boolean wasSet = this._isSet(22);
      int _oldVal = this._TunnelingClientPingSecs;
      this._TunnelingClientPingSecs = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public int getTunnelingClientTimeoutSecs() {
      if (!this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23)) {
         return this._getDelegateBean().getTunnelingClientTimeoutSecs();
      } else {
         if (!this._isSet(23)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getTunnelingClientTimeoutSecs();
            } catch (NullPointerException var2) {
            }
         }

         return this._TunnelingClientTimeoutSecs;
      }
   }

   public boolean isTunnelingClientTimeoutSecsInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isTunnelingClientTimeoutSecsSet() {
      return this._isSet(23);
   }

   public void setTunnelingClientTimeoutSecs(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("TunnelingClientTimeoutSecs", param0, 1);
      boolean wasSet = this._isSet(23);
      int _oldVal = this._TunnelingClientTimeoutSecs;
      this._TunnelingClientTimeoutSecs = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isTunnelingEnabled() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().isTunnelingEnabled() : this._TunnelingEnabled;
   }

   public boolean isTunnelingEnabledInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isTunnelingEnabledSet() {
      return this._isSet(24);
   }

   public void setTunnelingEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      NetworkAccessPointValidator.validateTunnelingEnabled(this, param0);
      boolean wasSet = this._isSet(24);
      boolean _oldVal = this._TunnelingEnabled;
      this._TunnelingEnabled = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteMessageTimeout() {
      if (!this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25)) {
         return this._getDelegateBean().getCompleteMessageTimeout();
      } else {
         if (!this._isSet(25)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getCompleteMessageTimeout();
            } catch (NullPointerException var2) {
            }
         }

         return this._CompleteMessageTimeout;
      }
   }

   public boolean isCompleteMessageTimeoutInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isCompleteMessageTimeoutSet() {
      return this._isSet(25);
   }

   public void setCompleteMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteMessageTimeout", (long)param0, 0L, 480L);
      boolean wasSet = this._isSet(25);
      int _oldVal = this._CompleteMessageTimeout;
      this._CompleteMessageTimeout = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getTimeoutConnectionWithPendingResponses() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getTimeoutConnectionWithPendingResponses() : this._TimeoutConnectionWithPendingResponses;
   }

   public boolean isTimeoutConnectionWithPendingResponsesInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isTimeoutConnectionWithPendingResponsesSet() {
      return this._isSet(26);
   }

   public void setTimeoutConnectionWithPendingResponses(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      boolean _oldVal = this._TimeoutConnectionWithPendingResponses;
      this._TimeoutConnectionWithPendingResponses = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public int getIdleConnectionTimeout() {
      if (!this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27)) {
         return this._getDelegateBean().getIdleConnectionTimeout();
      } else {
         if (!this._isSet(27)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getIdleConnectionTimeout();
            } catch (NullPointerException var2) {
            }
         }

         return this._IdleConnectionTimeout;
      }
   }

   public boolean isIdleConnectionTimeoutInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isIdleConnectionTimeoutSet() {
      return this._isSet(27);
   }

   public void setIdleConnectionTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("IdleConnectionTimeout", param0, 0);
      boolean wasSet = this._isSet(27);
      int _oldVal = this._IdleConnectionTimeout;
      this._IdleConnectionTimeout = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public int getConnectTimeout() {
      if (!this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28)) {
         return this._getDelegateBean().getConnectTimeout();
      } else {
         if (!this._isSet(28)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getConnectTimeout();
            } catch (NullPointerException var2) {
            }
         }

         return this._ConnectTimeout;
      }
   }

   public boolean isConnectTimeoutInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isConnectTimeoutSet() {
      return this._isSet(28);
   }

   public void setConnectTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ConnectTimeout", (long)param0, 0L, 240L);
      boolean wasSet = this._isSet(28);
      int _oldVal = this._ConnectTimeout;
      this._ConnectTimeout = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxMessageSize() {
      if (!this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29)) {
         return this._getDelegateBean().getMaxMessageSize();
      } else {
         if (!this._isSet(29)) {
            try {
               return NetworkAccessPoint.getMaxMessageSize(this);
            } catch (NullPointerException var2) {
            }
         }

         return this._MaxMessageSize;
      }
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

      LegalChecks.checkInRange("MaxMessageSize", (long)param0, 4096L, 100000000L);
      boolean wasSet = this._isSet(29);
      int _oldVal = this._MaxMessageSize;
      this._MaxMessageSize = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isOutboundEnabled() {
      if (!this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30)) {
         return this._getDelegateBean().isOutboundEnabled();
      } else {
         if (!this._isSet(30)) {
            try {
               return this.getProtocol().toLowerCase().equals("admin");
            } catch (NullPointerException var2) {
            }
         }

         return this._OutboundEnabled;
      }
   }

   public boolean isOutboundEnabledInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isOutboundEnabledSet() {
      return this._isSet(30);
   }

   public void setOutboundEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      boolean _oldVal = this._OutboundEnabled;
      this._OutboundEnabled = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public int getChannelWeight() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getChannelWeight() : this._ChannelWeight;
   }

   public boolean isChannelWeightInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isChannelWeightSet() {
      return this._isSet(31);
   }

   public void setChannelWeight(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ChannelWeight", (long)param0, 1L, 100L);
      boolean wasSet = this._isSet(31);
      int _oldVal = this._ChannelWeight;
      this._ChannelWeight = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public String getClusterAddress() {
      if (!this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32)) {
         return this._performMacroSubstitution(this._getDelegateBean().getClusterAddress(), this);
      } else {
         if (!this._isSet(32)) {
            try {
               return NetworkAccessPoint.getClusterAddress(this);
            } catch (NullPointerException var2) {
            }
         }

         return this._ClusterAddress;
      }
   }

   public boolean isClusterAddressInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isClusterAddressSet() {
      return this._isSet(32);
   }

   public void setClusterAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(32);
      String _oldVal = this._ClusterAddress;
      this._ClusterAddress = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isEnabled() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().isEnabled() : this._Enabled;
   }

   public boolean isEnabledInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isEnabledSet() {
      return this._isSet(33);
   }

   public void setEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(33);
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxConnectedClients() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().getMaxConnectedClients() : this._MaxConnectedClients;
   }

   public boolean isMaxConnectedClientsInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isMaxConnectedClientsSet() {
      return this._isSet(34);
   }

   public void setMaxConnectedClients(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      int _oldVal = this._MaxConnectedClients;
      this._MaxConnectedClients = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isTwoWaySSLEnabled() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().isTwoWaySSLEnabled() : this._TwoWaySSLEnabled;
   }

   public boolean isTwoWaySSLEnabledInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isTwoWaySSLEnabledSet() {
      return this._isSet(35);
   }

   public void setTwoWaySSLEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(35);
      boolean _oldVal = this._TwoWaySSLEnabled;
      this._TwoWaySSLEnabled = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isChannelIdentityCustomized() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().isChannelIdentityCustomized() : this._ChannelIdentityCustomized;
   }

   public boolean isChannelIdentityCustomizedInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isChannelIdentityCustomizedSet() {
      return this._isSet(36);
   }

   public void setChannelIdentityCustomized(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(36);
      boolean _oldVal = this._ChannelIdentityCustomized;
      this._ChannelIdentityCustomized = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomPrivateKeyAlias() {
      if (!this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37)) {
         return this._performMacroSubstitution(this._getDelegateBean().getCustomPrivateKeyAlias(), this);
      } else {
         if (!this._isSet(37)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getSSL().getServerPrivateKeyAlias();
            } catch (NullPointerException var2) {
            }
         }

         return this._CustomPrivateKeyAlias;
      }
   }

   public boolean isCustomPrivateKeyAliasInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isCustomPrivateKeyAliasSet() {
      return this._isSet(37);
   }

   public void setCustomPrivateKeyAlias(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(37);
      String _oldVal = this._CustomPrivateKeyAlias;
      this._CustomPrivateKeyAlias = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPrivateKeyAlias() {
      if (!this._isSet(38)) {
         try {
            return this.isChannelIdentityCustomized() ? this.getCustomPrivateKeyAlias() : ((ServerTemplateMBean)this.getParent()).getSSL().getServerPrivateKeyAlias();
         } catch (NullPointerException var2) {
         }
      }

      return this._PrivateKeyAlias;
   }

   public boolean isPrivateKeyAliasInherited() {
      return false;
   }

   public boolean isPrivateKeyAliasSet() {
      return this._isSet(38);
   }

   public void setPrivateKeyAlias(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(38);
      String _oldVal = this._PrivateKeyAlias;
      this._PrivateKeyAlias = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomPrivateKeyPassPhrase() {
      if (!this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39)) {
         return this._performMacroSubstitution(this._getDelegateBean().getCustomPrivateKeyPassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getCustomPrivateKeyPassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("CustomPrivateKeyPassPhrase", bEncrypted);
      }
   }

   public boolean isCustomPrivateKeyPassPhraseInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isCustomPrivateKeyPassPhraseSet() {
      return this.isCustomPrivateKeyPassPhraseEncryptedSet();
   }

   public void setCustomPrivateKeyPassPhrase(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setCustomPrivateKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("CustomPrivateKeyPassPhrase", param0));
   }

   public String getPrivateKeyPassPhrase() {
      if (!this._isSet(40)) {
         try {
            return this.isChannelIdentityCustomized() ? this.getCustomPrivateKeyPassPhrase() : ((ServerTemplateMBean)this.getParent()).getSSL().getServerPrivateKeyPassPhrase();
         } catch (NullPointerException var2) {
         }
      }

      return this._PrivateKeyPassPhrase;
   }

   public boolean isPrivateKeyPassPhraseInherited() {
      return false;
   }

   public boolean isPrivateKeyPassPhraseSet() {
      return this._isSet(40);
   }

   public void setPrivateKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(40);
      String _oldVal = this._PrivateKeyPassPhrase;
      this._PrivateKeyPassPhrase = param0;
      this._postSet(40, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(40)) {
            source._postSetFirePropertyChange(40, wasSet, _oldVal, param0);
         }
      }

   }

   public byte[] getCustomPrivateKeyPassPhraseEncrypted() {
      if (!this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41)) {
         return this._getDelegateBean().getCustomPrivateKeyPassPhraseEncrypted();
      } else {
         if (!this._isSet(41)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).getSSL().getServerPrivateKeyPassPhraseEncrypted();
            } catch (NullPointerException var2) {
            }
         }

         return this._getHelper()._cloneArray(this._CustomPrivateKeyPassPhraseEncrypted);
      }
   }

   public String getCustomPrivateKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getCustomPrivateKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isCustomPrivateKeyPassPhraseEncryptedInherited() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41);
   }

   public boolean isCustomPrivateKeyPassPhraseEncryptedSet() {
      return this._isSet(41);
   }

   public void setCustomPrivateKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setCustomPrivateKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isClientCertificateEnforced() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42) ? this._getDelegateBean().isClientCertificateEnforced() : this._ClientCertificateEnforced;
   }

   public boolean isClientCertificateEnforcedInherited() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42);
   }

   public boolean isClientCertificateEnforcedSet() {
      return this._isSet(42);
   }

   public void setClientCertificateEnforced(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(42);
      boolean _oldVal = this._ClientCertificateEnforced;
      this._ClientCertificateEnforced = param0;
      this._postSet(42, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(42)) {
            source._postSetFirePropertyChange(42, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isOutboundPrivateKeyEnabled() {
      if (!this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43)) {
         return this._getDelegateBean().isOutboundPrivateKeyEnabled();
      } else {
         if (!this._isSet(43)) {
            try {
               return ((ServerTemplateMBean)this.getParent()).isOutboundPrivateKeyEnabled();
            } catch (NullPointerException var2) {
            }
         }

         if (!this._isSet(43)) {
            return this._isSecureModeEnabled() ? false : false;
         } else {
            return this._OutboundPrivateKeyEnabled;
         }
      }
   }

   public boolean isOutboundPrivateKeyEnabledInherited() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43);
   }

   public boolean isOutboundPrivateKeyEnabledSet() {
      return this._isSet(43);
   }

   public void setOutboundPrivateKeyEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(43);
      boolean _oldVal = this._OutboundPrivateKeyEnabled;
      this._OutboundPrivateKeyEnabled = param0;
      this._postSet(43, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(43)) {
            source._postSetFirePropertyChange(43, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getUseFastSerialization() {
      if (!this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44)) {
         return this._getDelegateBean().getUseFastSerialization();
      } else {
         if (!this._isSet(44)) {
            try {
               return this.getProtocol().toLowerCase().startsWith("iiop") ? ((ServerTemplateMBean)this.getParent()).getIIOP().getUseJavaSerialization() : false;
            } catch (NullPointerException var2) {
            }
         }

         return this._UseFastSerialization;
      }
   }

   public boolean isUseFastSerializationInherited() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44);
   }

   public boolean isUseFastSerializationSet() {
      return this._isSet(44);
   }

   public void setUseFastSerialization(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(44);
      boolean _oldVal = this._UseFastSerialization;
      this._UseFastSerialization = param0;
      this._postSet(44, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
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
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(45)) {
            source._postSetFirePropertyChange(45, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSSLListenPort() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46) ? this._getDelegateBean().getSSLListenPort() : this._SSLListenPort;
   }

   public boolean isSSLListenPortInherited() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46);
   }

   public boolean isSSLListenPortSet() {
      return this._isSet(46);
   }

   public void setSSLListenPort(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      NetworkAccessPointValidator.validateMaxMessageSize(param0);
      boolean wasSet = this._isSet(46);
      int _oldVal = this._SSLListenPort;
      this._SSLListenPort = param0;
      this._postSet(46, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(46)) {
            source._postSetFirePropertyChange(46, wasSet, _oldVal, param0);
         }
      }

   }

   public String getExternalDNSName() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47) ? this._performMacroSubstitution(this._getDelegateBean().getExternalDNSName(), this) : this._ExternalDNSName;
   }

   public boolean isExternalDNSNameInherited() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47);
   }

   public boolean isExternalDNSNameSet() {
      return this._isSet(47);
   }

   public void setExternalDNSName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(47);
      String _oldVal = this._ExternalDNSName;
      this._ExternalDNSName = param0;
      this._postSet(47, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(47)) {
            source._postSetFirePropertyChange(47, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLoginTimeoutMillisSSL() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48) ? this._getDelegateBean().getLoginTimeoutMillisSSL() : this._LoginTimeoutMillisSSL;
   }

   public boolean isLoginTimeoutMillisSSLInherited() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48);
   }

   public boolean isLoginTimeoutMillisSSLSet() {
      return this._isSet(48);
   }

   public void setLoginTimeoutMillisSSL(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LoginTimeoutMillisSSL", (long)param0, -1L, 2147483647L);
      boolean wasSet = this._isSet(48);
      int _oldVal = this._LoginTimeoutMillisSSL;
      this._LoginTimeoutMillisSSL = param0;
      this._postSet(48, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(48)) {
            source._postSetFirePropertyChange(48, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteT3MessageTimeout() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49) ? this._getDelegateBean().getCompleteT3MessageTimeout() : this._CompleteT3MessageTimeout;
   }

   public boolean isCompleteT3MessageTimeoutInherited() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49);
   }

   public boolean isCompleteT3MessageTimeoutSet() {
      return this._isSet(49);
   }

   public void setCompleteT3MessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteT3MessageTimeout", (long)param0, -1L, 480L);
      boolean wasSet = this._isSet(49);
      int _oldVal = this._CompleteT3MessageTimeout;
      this._CompleteT3MessageTimeout = param0;
      this._postSet(49, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(49)) {
            source._postSetFirePropertyChange(49, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteHTTPMessageTimeout() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50) ? this._getDelegateBean().getCompleteHTTPMessageTimeout() : this._CompleteHTTPMessageTimeout;
   }

   public boolean isCompleteHTTPMessageTimeoutInherited() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50);
   }

   public boolean isCompleteHTTPMessageTimeoutSet() {
      return this._isSet(50);
   }

   public void setCompleteHTTPMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteHTTPMessageTimeout", (long)param0, -1L, 480L);
      boolean wasSet = this._isSet(50);
      int _oldVal = this._CompleteHTTPMessageTimeout;
      this._CompleteHTTPMessageTimeout = param0;
      this._postSet(50, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(50)) {
            source._postSetFirePropertyChange(50, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteCOMMessageTimeout() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51) ? this._getDelegateBean().getCompleteCOMMessageTimeout() : this._CompleteCOMMessageTimeout;
   }

   public boolean isCompleteCOMMessageTimeoutInherited() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51);
   }

   public boolean isCompleteCOMMessageTimeoutSet() {
      return this._isSet(51);
   }

   public void setCompleteCOMMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteCOMMessageTimeout", (long)param0, -1L, 480L);
      boolean wasSet = this._isSet(51);
      int _oldVal = this._CompleteCOMMessageTimeout;
      this._CompleteCOMMessageTimeout = param0;
      this._postSet(51, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(51)) {
            source._postSetFirePropertyChange(51, wasSet, _oldVal, param0);
         }
      }

   }

   public int getCompleteIIOPMessageTimeout() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52) ? this._getDelegateBean().getCompleteIIOPMessageTimeout() : this._CompleteIIOPMessageTimeout;
   }

   public boolean isCompleteIIOPMessageTimeoutInherited() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52);
   }

   public boolean isCompleteIIOPMessageTimeoutSet() {
      return this._isSet(52);
   }

   public void setCompleteIIOPMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("CompleteIIOPMessageTimeout", (long)param0, -1L, 480L);
      boolean wasSet = this._isSet(52);
      int _oldVal = this._CompleteIIOPMessageTimeout;
      this._CompleteIIOPMessageTimeout = param0;
      this._postSet(52, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(52)) {
            source._postSetFirePropertyChange(52, wasSet, _oldVal, param0);
         }
      }

   }

   public void setCustomProperties(Properties param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(53);
      Properties _oldVal = this._CustomProperties;
      this._CustomProperties = param0;
      this._postSet(53, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(53)) {
            source._postSetFirePropertyChange(53, wasSet, _oldVal, param0);
         }
      }

   }

   public Properties getCustomProperties() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53) ? this._getDelegateBean().getCustomProperties() : this._CustomProperties;
   }

   public String getCustomPropertiesAsString() {
      return StringHelper.objectToString(this.getCustomProperties());
   }

   public boolean isCustomPropertiesInherited() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53);
   }

   public boolean isCustomPropertiesSet() {
      return this._isSet(53);
   }

   public void setCustomPropertiesAsString(String param0) {
      try {
         this.setCustomProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isSDPEnabled() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54) ? this._getDelegateBean().isSDPEnabled() : this._SDPEnabled;
   }

   public boolean isSDPEnabledInherited() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54);
   }

   public boolean isSDPEnabledSet() {
      return this._isSet(54);
   }

   public void setSDPEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(54);
      boolean _oldVal = this._SDPEnabled;
      this._SDPEnabled = param0;
      this._postSet(54, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(54)) {
            source._postSetFirePropertyChange(54, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOutboundPrivateKeyAlias() {
      if (!this._isSet(55)) {
         try {
            return this.isOutboundPrivateKeyEnabled() && this.isChannelIdentityCustomized() ? this.getCustomPrivateKeyAlias() : ((ServerTemplateMBean)this.getParent()).getSSL().getOutboundPrivateKeyAlias();
         } catch (NullPointerException var2) {
         }
      }

      return this._OutboundPrivateKeyAlias;
   }

   public boolean isOutboundPrivateKeyAliasInherited() {
      return false;
   }

   public boolean isOutboundPrivateKeyAliasSet() {
      return this._isSet(55);
   }

   public void setOutboundPrivateKeyAlias(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(55);
      String _oldVal = this._OutboundPrivateKeyAlias;
      this._OutboundPrivateKeyAlias = param0;
      this._postSet(55, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(55)) {
            source._postSetFirePropertyChange(55, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOutboundPrivateKeyPassPhrase() {
      if (!this._isSet(56)) {
         try {
            return this.isOutboundPrivateKeyEnabled() && this.isChannelIdentityCustomized() ? this.getCustomPrivateKeyPassPhrase() : ((ServerTemplateMBean)this.getParent()).getSSL().getOutboundPrivateKeyPassPhrase();
         } catch (NullPointerException var2) {
         }
      }

      return this._OutboundPrivateKeyPassPhrase;
   }

   public boolean isOutboundPrivateKeyPassPhraseInherited() {
      return false;
   }

   public boolean isOutboundPrivateKeyPassPhraseSet() {
      return this._isSet(56);
   }

   public void setOutboundPrivateKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(56);
      String _oldVal = this._OutboundPrivateKeyPassPhrase;
      this._OutboundPrivateKeyPassPhrase = param0;
      this._postSet(56, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(56)) {
            source._postSetFirePropertyChange(56, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomIdentityKeyStoreFileName() {
      if (!this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57)) {
         return this._performMacroSubstitution(this._getDelegateBean().getCustomIdentityKeyStoreFileName(), this);
      } else {
         if (!this._isSet(57)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getCustomIdentityKeyStoreFileName();
            } catch (NullPointerException var2) {
            }
         }

         return this._CustomIdentityKeyStoreFileName;
      }
   }

   public boolean isCustomIdentityKeyStoreFileNameInherited() {
      return !this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57);
   }

   public boolean isCustomIdentityKeyStoreFileNameSet() {
      return this._isSet(57);
   }

   public void setCustomIdentityKeyStoreFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(57);
      String _oldVal = this._CustomIdentityKeyStoreFileName;
      this._CustomIdentityKeyStoreFileName = param0;
      this._postSet(57, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(57)) {
            source._postSetFirePropertyChange(57, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomIdentityKeyStoreType() {
      if (!this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58)) {
         return this._performMacroSubstitution(this._getDelegateBean().getCustomIdentityKeyStoreType(), this);
      } else {
         if (!this._isSet(58)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getCustomIdentityKeyStoreType();
            } catch (NullPointerException var2) {
            }
         }

         return this._CustomIdentityKeyStoreType;
      }
   }

   public boolean isCustomIdentityKeyStoreTypeInherited() {
      return !this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58);
   }

   public boolean isCustomIdentityKeyStoreTypeSet() {
      return this._isSet(58);
   }

   public void setCustomIdentityKeyStoreType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(58);
      String _oldVal = this._CustomIdentityKeyStoreType;
      this._CustomIdentityKeyStoreType = param0;
      this._postSet(58, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(58)) {
            source._postSetFirePropertyChange(58, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCustomIdentityKeyStorePassPhrase() {
      if (!this._isSet(59) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(59)) {
         return this._performMacroSubstitution(this._getDelegateBean().getCustomIdentityKeyStorePassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getCustomIdentityKeyStorePassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("CustomIdentityKeyStorePassPhrase", bEncrypted);
      }
   }

   public boolean isCustomIdentityKeyStorePassPhraseInherited() {
      return !this._isSet(59) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(59);
   }

   public boolean isCustomIdentityKeyStorePassPhraseSet() {
      return this.isCustomIdentityKeyStorePassPhraseEncryptedSet();
   }

   public void setCustomIdentityKeyStorePassPhrase(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setCustomIdentityKeyStorePassPhraseEncrypted(param0 == null ? null : this._encrypt("CustomIdentityKeyStorePassPhrase", param0));
   }

   public byte[] getCustomIdentityKeyStorePassPhraseEncrypted() {
      if (!this._isSet(60) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(60)) {
         return this._getDelegateBean().getCustomIdentityKeyStorePassPhraseEncrypted();
      } else {
         if (!this._isSet(60)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getCustomIdentityKeyStorePassPhraseEncrypted();
            } catch (NullPointerException var2) {
            }
         }

         return this._getHelper()._cloneArray(this._CustomIdentityKeyStorePassPhraseEncrypted);
      }
   }

   public String getCustomIdentityKeyStorePassPhraseEncryptedAsString() {
      byte[] obj = this.getCustomIdentityKeyStorePassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isCustomIdentityKeyStorePassPhraseEncryptedInherited() {
      return !this._isSet(60) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(60);
   }

   public boolean isCustomIdentityKeyStorePassPhraseEncryptedSet() {
      return this._isSet(60);
   }

   public void setCustomIdentityKeyStorePassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setCustomIdentityKeyStorePassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getHostnameVerifier() {
      if (!this._isSet(61) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(61)) {
         return this._performMacroSubstitution(this._getDelegateBean().getHostnameVerifier(), this);
      } else {
         if (!this._isSet(61)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getSSL().getHostnameVerifier();
            } catch (NullPointerException var2) {
            }
         }

         return this._HostnameVerifier;
      }
   }

   public boolean isHostnameVerifierInherited() {
      return !this._isSet(61) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(61);
   }

   public boolean isHostnameVerifierSet() {
      return this._isSet(61);
   }

   public void setHostnameVerifier(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(61);
      String _oldVal = this._HostnameVerifier;
      this._HostnameVerifier = param0;
      this._postSet(61, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(61)) {
            source._postSetFirePropertyChange(61, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isHostnameVerificationIgnored() {
      if (!this._isSet(62) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(62)) {
         return this._getDelegateBean().isHostnameVerificationIgnored();
      } else {
         if (!this._isSet(62)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getSSL().isHostnameVerificationIgnored();
            } catch (NullPointerException var2) {
            }
         }

         return this._HostnameVerificationIgnored;
      }
   }

   public boolean isHostnameVerificationIgnoredInherited() {
      return !this._isSet(62) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(62);
   }

   public boolean isHostnameVerificationIgnoredSet() {
      return this._isSet(62);
   }

   public void setHostnameVerificationIgnored(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(62);
      boolean _oldVal = this._HostnameVerificationIgnored;
      this._HostnameVerificationIgnored = param0;
      this._postSet(62, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(62)) {
            source._postSetFirePropertyChange(62, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getCiphersuites() {
      if (!this._isSet(63) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(63)) {
         return this._getDelegateBean().getCiphersuites();
      } else {
         if (!this._isSet(63)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getSSL().getCiphersuites();
            } catch (NullPointerException var2) {
            }
         }

         return this._Ciphersuites;
      }
   }

   public boolean isCiphersuitesInherited() {
      return !this._isSet(63) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(63);
   }

   public boolean isCiphersuitesSet() {
      return this._isSet(63);
   }

   public void setCiphersuites(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(63);
      String[] _oldVal = this._Ciphersuites;
      this._Ciphersuites = param0;
      this._postSet(63, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(63)) {
            source._postSetFirePropertyChange(63, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getExcludedCiphersuites() {
      if (!this._isSet(64) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(64)) {
         return this._getDelegateBean().getExcludedCiphersuites();
      } else {
         if (!this._isSet(64)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getSSL().getExcludedCiphersuites();
            } catch (NullPointerException var2) {
            }
         }

         return this._ExcludedCiphersuites;
      }
   }

   public boolean isExcludedCiphersuitesInherited() {
      return !this._isSet(64) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(64);
   }

   public boolean isExcludedCiphersuitesSet() {
      return this._isSet(64);
   }

   public void setExcludedCiphersuites(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(64);
      String[] _oldVal = this._ExcludedCiphersuites;
      this._ExcludedCiphersuites = param0;
      this._postSet(64, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(64)) {
            source._postSetFirePropertyChange(64, wasSet, _oldVal, param0);
         }
      }

   }

   public void setAllowUnencryptedNullCipher(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(65);
      boolean _oldVal = this._AllowUnencryptedNullCipher;
      this._AllowUnencryptedNullCipher = param0;
      this._postSet(65, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(65)) {
            source._postSetFirePropertyChange(65, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAllowUnencryptedNullCipher() {
      if (!this._isSet(65) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(65)) {
         return this._getDelegateBean().isAllowUnencryptedNullCipher();
      } else {
         if (!this._isSet(65)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getSSL().isAllowUnencryptedNullCipher();
            } catch (NullPointerException var2) {
            }
         }

         return this._AllowUnencryptedNullCipher;
      }
   }

   public boolean isAllowUnencryptedNullCipherInherited() {
      return !this._isSet(65) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(65);
   }

   public boolean isAllowUnencryptedNullCipherSet() {
      return this._isSet(65);
   }

   public String getInboundCertificateValidation() {
      if (!this._isSet(66) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(66)) {
         return this._performMacroSubstitution(this._getDelegateBean().getInboundCertificateValidation(), this);
      } else {
         if (!this._isSet(66)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getSSL().getInboundCertificateValidation();
            } catch (NullPointerException var2) {
            }
         }

         return this._InboundCertificateValidation;
      }
   }

   public boolean isInboundCertificateValidationInherited() {
      return !this._isSet(66) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(66);
   }

   public boolean isInboundCertificateValidationSet() {
      return this._isSet(66);
   }

   public void setInboundCertificateValidation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"};
      param0 = LegalChecks.checkInEnum("InboundCertificateValidation", param0, _set);
      boolean wasSet = this._isSet(66);
      String _oldVal = this._InboundCertificateValidation;
      this._InboundCertificateValidation = param0;
      this._postSet(66, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var5.next();
         if (source != null && !source._isSet(66)) {
            source._postSetFirePropertyChange(66, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOutboundCertificateValidation() {
      if (!this._isSet(67) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(67)) {
         return this._performMacroSubstitution(this._getDelegateBean().getOutboundCertificateValidation(), this);
      } else {
         if (!this._isSet(67)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getSSL().getOutboundCertificateValidation();
            } catch (NullPointerException var2) {
            }
         }

         return this._OutboundCertificateValidation;
      }
   }

   public boolean isOutboundCertificateValidationInherited() {
      return !this._isSet(67) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(67);
   }

   public boolean isOutboundCertificateValidationSet() {
      return this._isSet(67);
   }

   public void setOutboundCertificateValidation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"BuiltinSSLValidationOnly", "BuiltinSSLValidationAndCertPathValidators"};
      param0 = LegalChecks.checkInEnum("OutboundCertificateValidation", param0, _set);
      boolean wasSet = this._isSet(67);
      String _oldVal = this._OutboundCertificateValidation;
      this._OutboundCertificateValidation = param0;
      this._postSet(67, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var5.next();
         if (source != null && !source._isSet(67)) {
            source._postSetFirePropertyChange(67, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMinimumTLSProtocolVersion() {
      if (!this._isSet(68) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(68)) {
         return this._performMacroSubstitution(this._getDelegateBean().getMinimumTLSProtocolVersion(), this);
      } else {
         if (!this._isSet(68)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getSSL().getMinimumTLSProtocolVersion();
            } catch (NullPointerException var2) {
            }
         }

         return this._MinimumTLSProtocolVersion;
      }
   }

   public boolean isMinimumTLSProtocolVersionInherited() {
      return !this._isSet(68) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(68);
   }

   public boolean isMinimumTLSProtocolVersionSet() {
      return this._isSet(68);
   }

   public void setMinimumTLSProtocolVersion(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      ServerLegalHelper.validateMinimumSSLProtocol(param0);
      boolean wasSet = this._isSet(68);
      String _oldVal = this._MinimumTLSProtocolVersion;
      this._MinimumTLSProtocolVersion = param0;
      this._postSet(68, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(68)) {
            source._postSetFirePropertyChange(68, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isSSLv2HelloEnabled() {
      if (!this._isSet(69) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(69)) {
         return this._getDelegateBean().isSSLv2HelloEnabled();
      } else {
         if (!this._isSet(69)) {
            try {
               return ((ServerTemplateMBean)((ServerTemplateMBean)this.getParent())).getSSL().isSSLv2HelloEnabled();
            } catch (NullPointerException var2) {
            }
         }

         return this._SSLv2HelloEnabled;
      }
   }

   public boolean isSSLv2HelloEnabledInherited() {
      return !this._isSet(69) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(69);
   }

   public boolean isSSLv2HelloEnabledSet() {
      return this._isSet(69);
   }

   public void setSSLv2HelloEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(69);
      boolean _oldVal = this._SSLv2HelloEnabled;
      this._SSLv2HelloEnabled = param0;
      this._postSet(69, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(69)) {
            source._postSetFirePropertyChange(69, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isClientInitSecureRenegotiationAccepted() {
      return !this._isSet(70) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(70) ? this._getDelegateBean().isClientInitSecureRenegotiationAccepted() : this._ClientInitSecureRenegotiationAccepted;
   }

   public boolean isClientInitSecureRenegotiationAcceptedInherited() {
      return !this._isSet(70) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(70);
   }

   public boolean isClientInitSecureRenegotiationAcceptedSet() {
      return this._isSet(70);
   }

   public void setClientInitSecureRenegotiationAccepted(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(70);
      boolean _oldVal = this._ClientInitSecureRenegotiationAccepted;
      this._ClientInitSecureRenegotiationAccepted = param0;
      this._postSet(70, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
         if (source != null && !source._isSet(70)) {
            source._postSetFirePropertyChange(70, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      NetworkAccessPointValidator.validateNetworkAccessPoint(this);
   }

   public void setCustomIdentityKeyStorePassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(60);
      byte[] _oldVal = this._CustomIdentityKeyStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: CustomIdentityKeyStorePassPhraseEncrypted of NetworkAccessPointMBean");
      } else {
         this._getHelper()._clearArray(this._CustomIdentityKeyStorePassPhraseEncrypted);
         this._CustomIdentityKeyStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(60, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
            if (source != null && !source._isSet(60)) {
               source._postSetFirePropertyChange(60, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setCustomPrivateKeyPassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(41);
      byte[] _oldVal = this._CustomPrivateKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: CustomPrivateKeyPassPhraseEncrypted of NetworkAccessPointMBean");
      } else {
         this._getHelper()._clearArray(this._CustomPrivateKeyPassPhraseEncrypted);
         this._CustomPrivateKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(41, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            NetworkAccessPointMBeanImpl source = (NetworkAccessPointMBeanImpl)var4.next();
            if (source != null && !source._isSet(41)) {
               source._postSetFirePropertyChange(41, wasSet, _oldVal, param0);
            }
         }

      }
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
         if (idx == 59) {
            this._markSet(60, false);
         }

         if (idx == 39) {
            this._markSet(41, false);
         }
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
               this._AcceptBacklog = 0;
               if (initOne) {
                  break;
               }
            case 31:
               this._ChannelWeight = 50;
               if (initOne) {
                  break;
               }
            case 63:
               this._Ciphersuites = new String[0];
               if (initOne) {
                  break;
               }
            case 32:
               this._ClusterAddress = null;
               if (initOne) {
                  break;
               }
            case 51:
               this._CompleteCOMMessageTimeout = -1;
               if (initOne) {
                  break;
               }
            case 50:
               this._CompleteHTTPMessageTimeout = -1;
               if (initOne) {
                  break;
               }
            case 52:
               this._CompleteIIOPMessageTimeout = -1;
               if (initOne) {
                  break;
               }
            case 25:
               this._CompleteMessageTimeout = 0;
               if (initOne) {
                  break;
               }
            case 49:
               this._CompleteT3MessageTimeout = -1;
               if (initOne) {
                  break;
               }
            case 28:
               this._ConnectTimeout = 0;
               if (initOne) {
                  break;
               }
            case 57:
               this._CustomIdentityKeyStoreFileName = null;
               if (initOne) {
                  break;
               }
            case 59:
               this._CustomIdentityKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 60:
               this._CustomIdentityKeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 58:
               this._CustomIdentityKeyStoreType = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._CustomPrivateKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 39:
               this._CustomPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 41:
               this._CustomPrivateKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 53:
               this._CustomProperties = null;
               if (initOne) {
                  break;
               }
            case 64:
               this._ExcludedCiphersuites = new String[0];
               if (initOne) {
                  break;
               }
            case 47:
               this._ExternalDNSName = null;
               if (initOne) {
                  break;
               }
            case 61:
               this._HostnameVerifier = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._IdleConnectionTimeout = 0;
               if (initOne) {
                  break;
               }
            case 45:
               this._IdleIIOPConnectionTimeout = -1;
               if (initOne) {
                  break;
               }
            case 66:
               this._InboundCertificateValidation = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._ListenAddress = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ListenPort = 0;
               if (initOne) {
                  break;
               }
            case 21:
               this._LoginTimeoutMillis = 0;
               if (initOne) {
                  break;
               }
            case 48:
               this._LoginTimeoutMillisSSL = -1;
               if (initOne) {
                  break;
               }
            case 20:
               this._MaxBackoffBetweenFailures = 0;
               if (initOne) {
                  break;
               }
            case 34:
               this._MaxConnectedClients = Integer.MAX_VALUE;
               if (initOne) {
                  break;
               }
            case 29:
               this._MaxMessageSize = 0;
               if (initOne) {
                  break;
               }
            case 68:
               this._MinimumTLSProtocolVersion = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = "<unknown>";
               if (initOne) {
                  break;
               }
            case 67:
               this._OutboundCertificateValidation = null;
               if (initOne) {
                  break;
               }
            case 55:
               this._OutboundPrivateKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 56:
               this._OutboundPrivateKeyPassPhrase = null;
               if (initOne) {
                  break;
               }
            case 38:
               this._PrivateKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 40:
               this._PrivateKeyPassPhrase = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Protocol = "t3";
               if (initOne) {
                  break;
               }
            case 16:
               this._ProxyAddress = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._ProxyPort = 80;
               if (initOne) {
                  break;
               }
            case 12:
               this._PublicAddress = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._PublicPort = 0;
               if (initOne) {
                  break;
               }
            case 15:
               this._ResolveDNSName = false;
               if (initOne) {
                  break;
               }
            case 46:
               this._SSLListenPort = -1;
               if (initOne) {
                  break;
               }
            case 26:
               this._TimeoutConnectionWithPendingResponses = false;
               if (initOne) {
                  break;
               }
            case 22:
               this._TunnelingClientPingSecs = 0;
               if (initOne) {
                  break;
               }
            case 23:
               this._TunnelingClientTimeoutSecs = 0;
               if (initOne) {
                  break;
               }
            case 44:
               this._UseFastSerialization = false;
               if (initOne) {
                  break;
               }
            case 65:
               this._AllowUnencryptedNullCipher = false;
               if (initOne) {
                  break;
               }
            case 36:
               this._ChannelIdentityCustomized = false;
               if (initOne) {
                  break;
               }
            case 42:
               this._ClientCertificateEnforced = false;
               if (initOne) {
                  break;
               }
            case 70:
               this._ClientInitSecureRenegotiationAccepted = false;
               if (initOne) {
                  break;
               }
            case 33:
               this._Enabled = true;
               if (initOne) {
                  break;
               }
            case 62:
               this._HostnameVerificationIgnored = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._HttpEnabledForThisProtocol = true;
               if (initOne) {
                  break;
               }
            case 30:
               this._OutboundEnabled = false;
               if (initOne) {
                  break;
               }
            case 43:
               this._OutboundPrivateKeyEnabled = false;
               if (initOne) {
                  break;
               }
            case 54:
               this._SDPEnabled = false;
               if (initOne) {
                  break;
               }
            case 69:
               this._SSLv2HelloEnabled = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._TunnelingEnabled = false;
               if (initOne) {
                  break;
               }
            case 35:
               this._TwoWaySSLEnabled = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
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
      return "NetworkAccessPoint";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("AcceptBacklog")) {
         oldVal = this._AcceptBacklog;
         this._AcceptBacklog = (Integer)v;
         this._postSet(19, oldVal, this._AcceptBacklog);
      } else {
         boolean oldVal;
         if (name.equals("AllowUnencryptedNullCipher")) {
            oldVal = this._AllowUnencryptedNullCipher;
            this._AllowUnencryptedNullCipher = (Boolean)v;
            this._postSet(65, oldVal, this._AllowUnencryptedNullCipher);
         } else if (name.equals("ChannelIdentityCustomized")) {
            oldVal = this._ChannelIdentityCustomized;
            this._ChannelIdentityCustomized = (Boolean)v;
            this._postSet(36, oldVal, this._ChannelIdentityCustomized);
         } else if (name.equals("ChannelWeight")) {
            oldVal = this._ChannelWeight;
            this._ChannelWeight = (Integer)v;
            this._postSet(31, oldVal, this._ChannelWeight);
         } else {
            String[] oldVal;
            if (name.equals("Ciphersuites")) {
               oldVal = this._Ciphersuites;
               this._Ciphersuites = (String[])((String[])v);
               this._postSet(63, oldVal, this._Ciphersuites);
            } else if (name.equals("ClientCertificateEnforced")) {
               oldVal = this._ClientCertificateEnforced;
               this._ClientCertificateEnforced = (Boolean)v;
               this._postSet(42, oldVal, this._ClientCertificateEnforced);
            } else if (name.equals("ClientInitSecureRenegotiationAccepted")) {
               oldVal = this._ClientInitSecureRenegotiationAccepted;
               this._ClientInitSecureRenegotiationAccepted = (Boolean)v;
               this._postSet(70, oldVal, this._ClientInitSecureRenegotiationAccepted);
            } else {
               String oldVal;
               if (name.equals("ClusterAddress")) {
                  oldVal = this._ClusterAddress;
                  this._ClusterAddress = (String)v;
                  this._postSet(32, oldVal, this._ClusterAddress);
               } else if (name.equals("CompleteCOMMessageTimeout")) {
                  oldVal = this._CompleteCOMMessageTimeout;
                  this._CompleteCOMMessageTimeout = (Integer)v;
                  this._postSet(51, oldVal, this._CompleteCOMMessageTimeout);
               } else if (name.equals("CompleteHTTPMessageTimeout")) {
                  oldVal = this._CompleteHTTPMessageTimeout;
                  this._CompleteHTTPMessageTimeout = (Integer)v;
                  this._postSet(50, oldVal, this._CompleteHTTPMessageTimeout);
               } else if (name.equals("CompleteIIOPMessageTimeout")) {
                  oldVal = this._CompleteIIOPMessageTimeout;
                  this._CompleteIIOPMessageTimeout = (Integer)v;
                  this._postSet(52, oldVal, this._CompleteIIOPMessageTimeout);
               } else if (name.equals("CompleteMessageTimeout")) {
                  oldVal = this._CompleteMessageTimeout;
                  this._CompleteMessageTimeout = (Integer)v;
                  this._postSet(25, oldVal, this._CompleteMessageTimeout);
               } else if (name.equals("CompleteT3MessageTimeout")) {
                  oldVal = this._CompleteT3MessageTimeout;
                  this._CompleteT3MessageTimeout = (Integer)v;
                  this._postSet(49, oldVal, this._CompleteT3MessageTimeout);
               } else if (name.equals("ConnectTimeout")) {
                  oldVal = this._ConnectTimeout;
                  this._ConnectTimeout = (Integer)v;
                  this._postSet(28, oldVal, this._ConnectTimeout);
               } else if (name.equals("CustomIdentityKeyStoreFileName")) {
                  oldVal = this._CustomIdentityKeyStoreFileName;
                  this._CustomIdentityKeyStoreFileName = (String)v;
                  this._postSet(57, oldVal, this._CustomIdentityKeyStoreFileName);
               } else if (name.equals("CustomIdentityKeyStorePassPhrase")) {
                  oldVal = this._CustomIdentityKeyStorePassPhrase;
                  this._CustomIdentityKeyStorePassPhrase = (String)v;
                  this._postSet(59, oldVal, this._CustomIdentityKeyStorePassPhrase);
               } else {
                  byte[] oldVal;
                  if (name.equals("CustomIdentityKeyStorePassPhraseEncrypted")) {
                     oldVal = this._CustomIdentityKeyStorePassPhraseEncrypted;
                     this._CustomIdentityKeyStorePassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(60, oldVal, this._CustomIdentityKeyStorePassPhraseEncrypted);
                  } else if (name.equals("CustomIdentityKeyStoreType")) {
                     oldVal = this._CustomIdentityKeyStoreType;
                     this._CustomIdentityKeyStoreType = (String)v;
                     this._postSet(58, oldVal, this._CustomIdentityKeyStoreType);
                  } else if (name.equals("CustomPrivateKeyAlias")) {
                     oldVal = this._CustomPrivateKeyAlias;
                     this._CustomPrivateKeyAlias = (String)v;
                     this._postSet(37, oldVal, this._CustomPrivateKeyAlias);
                  } else if (name.equals("CustomPrivateKeyPassPhrase")) {
                     oldVal = this._CustomPrivateKeyPassPhrase;
                     this._CustomPrivateKeyPassPhrase = (String)v;
                     this._postSet(39, oldVal, this._CustomPrivateKeyPassPhrase);
                  } else if (name.equals("CustomPrivateKeyPassPhraseEncrypted")) {
                     oldVal = this._CustomPrivateKeyPassPhraseEncrypted;
                     this._CustomPrivateKeyPassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(41, oldVal, this._CustomPrivateKeyPassPhraseEncrypted);
                  } else if (name.equals("CustomProperties")) {
                     Properties oldVal = this._CustomProperties;
                     this._CustomProperties = (Properties)v;
                     this._postSet(53, oldVal, this._CustomProperties);
                  } else if (name.equals("Enabled")) {
                     oldVal = this._Enabled;
                     this._Enabled = (Boolean)v;
                     this._postSet(33, oldVal, this._Enabled);
                  } else if (name.equals("ExcludedCiphersuites")) {
                     oldVal = this._ExcludedCiphersuites;
                     this._ExcludedCiphersuites = (String[])((String[])v);
                     this._postSet(64, oldVal, this._ExcludedCiphersuites);
                  } else if (name.equals("ExternalDNSName")) {
                     oldVal = this._ExternalDNSName;
                     this._ExternalDNSName = (String)v;
                     this._postSet(47, oldVal, this._ExternalDNSName);
                  } else if (name.equals("HostnameVerificationIgnored")) {
                     oldVal = this._HostnameVerificationIgnored;
                     this._HostnameVerificationIgnored = (Boolean)v;
                     this._postSet(62, oldVal, this._HostnameVerificationIgnored);
                  } else if (name.equals("HostnameVerifier")) {
                     oldVal = this._HostnameVerifier;
                     this._HostnameVerifier = (String)v;
                     this._postSet(61, oldVal, this._HostnameVerifier);
                  } else if (name.equals("HttpEnabledForThisProtocol")) {
                     oldVal = this._HttpEnabledForThisProtocol;
                     this._HttpEnabledForThisProtocol = (Boolean)v;
                     this._postSet(18, oldVal, this._HttpEnabledForThisProtocol);
                  } else if (name.equals("IdleConnectionTimeout")) {
                     oldVal = this._IdleConnectionTimeout;
                     this._IdleConnectionTimeout = (Integer)v;
                     this._postSet(27, oldVal, this._IdleConnectionTimeout);
                  } else if (name.equals("IdleIIOPConnectionTimeout")) {
                     oldVal = this._IdleIIOPConnectionTimeout;
                     this._IdleIIOPConnectionTimeout = (Integer)v;
                     this._postSet(45, oldVal, this._IdleIIOPConnectionTimeout);
                  } else if (name.equals("InboundCertificateValidation")) {
                     oldVal = this._InboundCertificateValidation;
                     this._InboundCertificateValidation = (String)v;
                     this._postSet(66, oldVal, this._InboundCertificateValidation);
                  } else if (name.equals("ListenAddress")) {
                     oldVal = this._ListenAddress;
                     this._ListenAddress = (String)v;
                     this._postSet(11, oldVal, this._ListenAddress);
                  } else if (name.equals("ListenPort")) {
                     oldVal = this._ListenPort;
                     this._ListenPort = (Integer)v;
                     this._postSet(13, oldVal, this._ListenPort);
                  } else if (name.equals("LoginTimeoutMillis")) {
                     oldVal = this._LoginTimeoutMillis;
                     this._LoginTimeoutMillis = (Integer)v;
                     this._postSet(21, oldVal, this._LoginTimeoutMillis);
                  } else if (name.equals("LoginTimeoutMillisSSL")) {
                     oldVal = this._LoginTimeoutMillisSSL;
                     this._LoginTimeoutMillisSSL = (Integer)v;
                     this._postSet(48, oldVal, this._LoginTimeoutMillisSSL);
                  } else if (name.equals("MaxBackoffBetweenFailures")) {
                     oldVal = this._MaxBackoffBetweenFailures;
                     this._MaxBackoffBetweenFailures = (Integer)v;
                     this._postSet(20, oldVal, this._MaxBackoffBetweenFailures);
                  } else if (name.equals("MaxConnectedClients")) {
                     oldVal = this._MaxConnectedClients;
                     this._MaxConnectedClients = (Integer)v;
                     this._postSet(34, oldVal, this._MaxConnectedClients);
                  } else if (name.equals("MaxMessageSize")) {
                     oldVal = this._MaxMessageSize;
                     this._MaxMessageSize = (Integer)v;
                     this._postSet(29, oldVal, this._MaxMessageSize);
                  } else if (name.equals("MinimumTLSProtocolVersion")) {
                     oldVal = this._MinimumTLSProtocolVersion;
                     this._MinimumTLSProtocolVersion = (String)v;
                     this._postSet(68, oldVal, this._MinimumTLSProtocolVersion);
                  } else if (name.equals("Name")) {
                     oldVal = this._Name;
                     this._Name = (String)v;
                     this._postSet(2, oldVal, this._Name);
                  } else if (name.equals("OutboundCertificateValidation")) {
                     oldVal = this._OutboundCertificateValidation;
                     this._OutboundCertificateValidation = (String)v;
                     this._postSet(67, oldVal, this._OutboundCertificateValidation);
                  } else if (name.equals("OutboundEnabled")) {
                     oldVal = this._OutboundEnabled;
                     this._OutboundEnabled = (Boolean)v;
                     this._postSet(30, oldVal, this._OutboundEnabled);
                  } else if (name.equals("OutboundPrivateKeyAlias")) {
                     oldVal = this._OutboundPrivateKeyAlias;
                     this._OutboundPrivateKeyAlias = (String)v;
                     this._postSet(55, oldVal, this._OutboundPrivateKeyAlias);
                  } else if (name.equals("OutboundPrivateKeyEnabled")) {
                     oldVal = this._OutboundPrivateKeyEnabled;
                     this._OutboundPrivateKeyEnabled = (Boolean)v;
                     this._postSet(43, oldVal, this._OutboundPrivateKeyEnabled);
                  } else if (name.equals("OutboundPrivateKeyPassPhrase")) {
                     oldVal = this._OutboundPrivateKeyPassPhrase;
                     this._OutboundPrivateKeyPassPhrase = (String)v;
                     this._postSet(56, oldVal, this._OutboundPrivateKeyPassPhrase);
                  } else if (name.equals("PrivateKeyAlias")) {
                     oldVal = this._PrivateKeyAlias;
                     this._PrivateKeyAlias = (String)v;
                     this._postSet(38, oldVal, this._PrivateKeyAlias);
                  } else if (name.equals("PrivateKeyPassPhrase")) {
                     oldVal = this._PrivateKeyPassPhrase;
                     this._PrivateKeyPassPhrase = (String)v;
                     this._postSet(40, oldVal, this._PrivateKeyPassPhrase);
                  } else if (name.equals("Protocol")) {
                     oldVal = this._Protocol;
                     this._Protocol = (String)v;
                     this._postSet(10, oldVal, this._Protocol);
                  } else if (name.equals("ProxyAddress")) {
                     oldVal = this._ProxyAddress;
                     this._ProxyAddress = (String)v;
                     this._postSet(16, oldVal, this._ProxyAddress);
                  } else if (name.equals("ProxyPort")) {
                     oldVal = this._ProxyPort;
                     this._ProxyPort = (Integer)v;
                     this._postSet(17, oldVal, this._ProxyPort);
                  } else if (name.equals("PublicAddress")) {
                     oldVal = this._PublicAddress;
                     this._PublicAddress = (String)v;
                     this._postSet(12, oldVal, this._PublicAddress);
                  } else if (name.equals("PublicPort")) {
                     oldVal = this._PublicPort;
                     this._PublicPort = (Integer)v;
                     this._postSet(14, oldVal, this._PublicPort);
                  } else if (name.equals("ResolveDNSName")) {
                     oldVal = this._ResolveDNSName;
                     this._ResolveDNSName = (Boolean)v;
                     this._postSet(15, oldVal, this._ResolveDNSName);
                  } else if (name.equals("SDPEnabled")) {
                     oldVal = this._SDPEnabled;
                     this._SDPEnabled = (Boolean)v;
                     this._postSet(54, oldVal, this._SDPEnabled);
                  } else if (name.equals("SSLListenPort")) {
                     oldVal = this._SSLListenPort;
                     this._SSLListenPort = (Integer)v;
                     this._postSet(46, oldVal, this._SSLListenPort);
                  } else if (name.equals("SSLv2HelloEnabled")) {
                     oldVal = this._SSLv2HelloEnabled;
                     this._SSLv2HelloEnabled = (Boolean)v;
                     this._postSet(69, oldVal, this._SSLv2HelloEnabled);
                  } else if (name.equals("TimeoutConnectionWithPendingResponses")) {
                     oldVal = this._TimeoutConnectionWithPendingResponses;
                     this._TimeoutConnectionWithPendingResponses = (Boolean)v;
                     this._postSet(26, oldVal, this._TimeoutConnectionWithPendingResponses);
                  } else if (name.equals("TunnelingClientPingSecs")) {
                     oldVal = this._TunnelingClientPingSecs;
                     this._TunnelingClientPingSecs = (Integer)v;
                     this._postSet(22, oldVal, this._TunnelingClientPingSecs);
                  } else if (name.equals("TunnelingClientTimeoutSecs")) {
                     oldVal = this._TunnelingClientTimeoutSecs;
                     this._TunnelingClientTimeoutSecs = (Integer)v;
                     this._postSet(23, oldVal, this._TunnelingClientTimeoutSecs);
                  } else if (name.equals("TunnelingEnabled")) {
                     oldVal = this._TunnelingEnabled;
                     this._TunnelingEnabled = (Boolean)v;
                     this._postSet(24, oldVal, this._TunnelingEnabled);
                  } else if (name.equals("TwoWaySSLEnabled")) {
                     oldVal = this._TwoWaySSLEnabled;
                     this._TwoWaySSLEnabled = (Boolean)v;
                     this._postSet(35, oldVal, this._TwoWaySSLEnabled);
                  } else if (name.equals("UseFastSerialization")) {
                     oldVal = this._UseFastSerialization;
                     this._UseFastSerialization = (Boolean)v;
                     this._postSet(44, oldVal, this._UseFastSerialization);
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AcceptBacklog")) {
         return new Integer(this._AcceptBacklog);
      } else if (name.equals("AllowUnencryptedNullCipher")) {
         return new Boolean(this._AllowUnencryptedNullCipher);
      } else if (name.equals("ChannelIdentityCustomized")) {
         return new Boolean(this._ChannelIdentityCustomized);
      } else if (name.equals("ChannelWeight")) {
         return new Integer(this._ChannelWeight);
      } else if (name.equals("Ciphersuites")) {
         return this._Ciphersuites;
      } else if (name.equals("ClientCertificateEnforced")) {
         return new Boolean(this._ClientCertificateEnforced);
      } else if (name.equals("ClientInitSecureRenegotiationAccepted")) {
         return new Boolean(this._ClientInitSecureRenegotiationAccepted);
      } else if (name.equals("ClusterAddress")) {
         return this._ClusterAddress;
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
      } else if (name.equals("ConnectTimeout")) {
         return new Integer(this._ConnectTimeout);
      } else if (name.equals("CustomIdentityKeyStoreFileName")) {
         return this._CustomIdentityKeyStoreFileName;
      } else if (name.equals("CustomIdentityKeyStorePassPhrase")) {
         return this._CustomIdentityKeyStorePassPhrase;
      } else if (name.equals("CustomIdentityKeyStorePassPhraseEncrypted")) {
         return this._CustomIdentityKeyStorePassPhraseEncrypted;
      } else if (name.equals("CustomIdentityKeyStoreType")) {
         return this._CustomIdentityKeyStoreType;
      } else if (name.equals("CustomPrivateKeyAlias")) {
         return this._CustomPrivateKeyAlias;
      } else if (name.equals("CustomPrivateKeyPassPhrase")) {
         return this._CustomPrivateKeyPassPhrase;
      } else if (name.equals("CustomPrivateKeyPassPhraseEncrypted")) {
         return this._CustomPrivateKeyPassPhraseEncrypted;
      } else if (name.equals("CustomProperties")) {
         return this._CustomProperties;
      } else if (name.equals("Enabled")) {
         return new Boolean(this._Enabled);
      } else if (name.equals("ExcludedCiphersuites")) {
         return this._ExcludedCiphersuites;
      } else if (name.equals("ExternalDNSName")) {
         return this._ExternalDNSName;
      } else if (name.equals("HostnameVerificationIgnored")) {
         return new Boolean(this._HostnameVerificationIgnored);
      } else if (name.equals("HostnameVerifier")) {
         return this._HostnameVerifier;
      } else if (name.equals("HttpEnabledForThisProtocol")) {
         return new Boolean(this._HttpEnabledForThisProtocol);
      } else if (name.equals("IdleConnectionTimeout")) {
         return new Integer(this._IdleConnectionTimeout);
      } else if (name.equals("IdleIIOPConnectionTimeout")) {
         return new Integer(this._IdleIIOPConnectionTimeout);
      } else if (name.equals("InboundCertificateValidation")) {
         return this._InboundCertificateValidation;
      } else if (name.equals("ListenAddress")) {
         return this._ListenAddress;
      } else if (name.equals("ListenPort")) {
         return new Integer(this._ListenPort);
      } else if (name.equals("LoginTimeoutMillis")) {
         return new Integer(this._LoginTimeoutMillis);
      } else if (name.equals("LoginTimeoutMillisSSL")) {
         return new Integer(this._LoginTimeoutMillisSSL);
      } else if (name.equals("MaxBackoffBetweenFailures")) {
         return new Integer(this._MaxBackoffBetweenFailures);
      } else if (name.equals("MaxConnectedClients")) {
         return new Integer(this._MaxConnectedClients);
      } else if (name.equals("MaxMessageSize")) {
         return new Integer(this._MaxMessageSize);
      } else if (name.equals("MinimumTLSProtocolVersion")) {
         return this._MinimumTLSProtocolVersion;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OutboundCertificateValidation")) {
         return this._OutboundCertificateValidation;
      } else if (name.equals("OutboundEnabled")) {
         return new Boolean(this._OutboundEnabled);
      } else if (name.equals("OutboundPrivateKeyAlias")) {
         return this._OutboundPrivateKeyAlias;
      } else if (name.equals("OutboundPrivateKeyEnabled")) {
         return new Boolean(this._OutboundPrivateKeyEnabled);
      } else if (name.equals("OutboundPrivateKeyPassPhrase")) {
         return this._OutboundPrivateKeyPassPhrase;
      } else if (name.equals("PrivateKeyAlias")) {
         return this._PrivateKeyAlias;
      } else if (name.equals("PrivateKeyPassPhrase")) {
         return this._PrivateKeyPassPhrase;
      } else if (name.equals("Protocol")) {
         return this._Protocol;
      } else if (name.equals("ProxyAddress")) {
         return this._ProxyAddress;
      } else if (name.equals("ProxyPort")) {
         return new Integer(this._ProxyPort);
      } else if (name.equals("PublicAddress")) {
         return this._PublicAddress;
      } else if (name.equals("PublicPort")) {
         return new Integer(this._PublicPort);
      } else if (name.equals("ResolveDNSName")) {
         return new Boolean(this._ResolveDNSName);
      } else if (name.equals("SDPEnabled")) {
         return new Boolean(this._SDPEnabled);
      } else if (name.equals("SSLListenPort")) {
         return new Integer(this._SSLListenPort);
      } else if (name.equals("SSLv2HelloEnabled")) {
         return new Boolean(this._SSLv2HelloEnabled);
      } else if (name.equals("TimeoutConnectionWithPendingResponses")) {
         return new Boolean(this._TimeoutConnectionWithPendingResponses);
      } else if (name.equals("TunnelingClientPingSecs")) {
         return new Integer(this._TunnelingClientPingSecs);
      } else if (name.equals("TunnelingClientTimeoutSecs")) {
         return new Integer(this._TunnelingClientTimeoutSecs);
      } else if (name.equals("TunnelingEnabled")) {
         return new Boolean(this._TunnelingEnabled);
      } else if (name.equals("TwoWaySSLEnabled")) {
         return new Boolean(this._TwoWaySSLEnabled);
      } else {
         return name.equals("UseFastSerialization") ? new Boolean(this._UseFastSerialization) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 9:
            case 12:
            case 19:
            case 25:
            case 33:
            case 34:
            case 36:
            case 38:
            case 39:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            default:
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 33;
               }
               break;
            case 8:
               if (s.equals("protocol")) {
                  return 10;
               }
               break;
            case 10:
               if (s.equals("proxy-port")) {
                  return 17;
               }
               break;
            case 11:
               if (s.equals("ciphersuite")) {
                  return 63;
               }

               if (s.equals("listen-port")) {
                  return 13;
               }

               if (s.equals("public-port")) {
                  return 14;
               }

               if (s.equals("sdp-enabled")) {
                  return 54;
               }
               break;
            case 13:
               if (s.equals("proxy-address")) {
                  return 16;
               }
               break;
            case 14:
               if (s.equals("accept-backlog")) {
                  return 19;
               }

               if (s.equals("channel-weight")) {
                  return 31;
               }

               if (s.equals("listen-address")) {
                  return 11;
               }

               if (s.equals("public-address")) {
                  return 12;
               }
               break;
            case 15:
               if (s.equals("cluster-address")) {
                  return 32;
               }

               if (s.equals("connect-timeout")) {
                  return 28;
               }

               if (s.equals("resolvedns-name")) {
                  return 15;
               }

               if (s.equals("ssl-listen-port")) {
                  return 46;
               }
               break;
            case 16:
               if (s.equals("externaldns-name")) {
                  return 47;
               }

               if (s.equals("max-message-size")) {
                  return 29;
               }

               if (s.equals("outbound-enabled")) {
                  return 30;
               }
               break;
            case 17:
               if (s.equals("custom-properties")) {
                  return 53;
               }

               if (s.equals("hostname-verifier")) {
                  return 61;
               }

               if (s.equals("private-key-alias")) {
                  return 38;
               }

               if (s.equals("tunneling-enabled")) {
                  return 24;
               }
               break;
            case 18:
               if (s.equals("two-wayssl-enabled")) {
                  return 35;
               }
               break;
            case 20:
               if (s.equals("excluded-ciphersuite")) {
                  return 64;
               }

               if (s.equals("login-timeout-millis")) {
                  return 21;
               }

               if (s.equals("ss-lv2-hello-enabled")) {
                  return 69;
               }
               break;
            case 21:
               if (s.equals("max-connected-clients")) {
                  return 34;
               }
               break;
            case 22:
               if (s.equals("use-fast-serialization")) {
                  return 44;
               }
               break;
            case 23:
               if (s.equals("idle-connection-timeout")) {
                  return 27;
               }

               if (s.equals("login-timeout-millisssl")) {
                  return 48;
               }

               if (s.equals("private-key-pass-phrase")) {
                  return 40;
               }
               break;
            case 24:
               if (s.equals("complete-message-timeout")) {
                  return 25;
               }

               if (s.equals("custom-private-key-alias")) {
                  return 37;
               }
               break;
            case 26:
               if (s.equals("completet3-message-timeout")) {
                  return 49;
               }

               if (s.equals("outbound-private-key-alias")) {
                  return 55;
               }

               if (s.equals("tunneling-client-ping-secs")) {
                  return 22;
               }
               break;
            case 27:
               if (s.equals("completecom-message-timeout")) {
                  return 51;
               }

               if (s.equals("idleiiop-connection-timeout")) {
                  return 45;
               }

               if (s.equals("minimumtls-protocol-version")) {
                  return 68;
               }

               if (s.equals("channel-identity-customized")) {
                  return 36;
               }

               if (s.equals("client-certificate-enforced")) {
                  return 42;
               }
               break;
            case 28:
               if (s.equals("completehttp-message-timeout")) {
                  return 50;
               }

               if (s.equals("completeiiop-message-timeout")) {
                  return 52;
               }

               if (s.equals("max-backoff-between-failures")) {
                  return 20;
               }

               if (s.equals("outbound-private-key-enabled")) {
                  return 43;
               }
               break;
            case 29:
               if (s.equals("tunneling-client-timeout-secs")) {
                  return 23;
               }

               if (s.equals("allow-unencrypted-null-cipher")) {
                  return 65;
               }

               if (s.equals("hostname-verification-ignored")) {
                  return 62;
               }
               break;
            case 30:
               if (s.equals("custom-identity-key-store-type")) {
                  return 58;
               }

               if (s.equals("custom-private-key-pass-phrase")) {
                  return 39;
               }

               if (s.equals("inbound-certificate-validation")) {
                  return 66;
               }

               if (s.equals("http-enabled-for-this-protocol")) {
                  return 18;
               }
               break;
            case 31:
               if (s.equals("outbound-certificate-validation")) {
                  return 67;
               }
               break;
            case 32:
               if (s.equals("outbound-private-key-pass-phrase")) {
                  return 56;
               }
               break;
            case 35:
               if (s.equals("custom-identity-key-store-file-name")) {
                  return 57;
               }
               break;
            case 37:
               if (s.equals("custom-identity-key-store-pass-phrase")) {
                  return 59;
               }
               break;
            case 40:
               if (s.equals("custom-private-key-pass-phrase-encrypted")) {
                  return 41;
               }
               break;
            case 41:
               if (s.equals("timeout-connection-with-pending-responses")) {
                  return 26;
               }

               if (s.equals("client-init-secure-renegotiation-accepted")) {
                  return 70;
               }
               break;
            case 47:
               if (s.equals("custom-identity-key-store-pass-phrase-encrypted")) {
                  return 60;
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
            case 7:
            case 8:
            case 9:
            default:
               return super.getElementName(propIndex);
            case 10:
               return "protocol";
            case 11:
               return "listen-address";
            case 12:
               return "public-address";
            case 13:
               return "listen-port";
            case 14:
               return "public-port";
            case 15:
               return "resolvedns-name";
            case 16:
               return "proxy-address";
            case 17:
               return "proxy-port";
            case 18:
               return "http-enabled-for-this-protocol";
            case 19:
               return "accept-backlog";
            case 20:
               return "max-backoff-between-failures";
            case 21:
               return "login-timeout-millis";
            case 22:
               return "tunneling-client-ping-secs";
            case 23:
               return "tunneling-client-timeout-secs";
            case 24:
               return "tunneling-enabled";
            case 25:
               return "complete-message-timeout";
            case 26:
               return "timeout-connection-with-pending-responses";
            case 27:
               return "idle-connection-timeout";
            case 28:
               return "connect-timeout";
            case 29:
               return "max-message-size";
            case 30:
               return "outbound-enabled";
            case 31:
               return "channel-weight";
            case 32:
               return "cluster-address";
            case 33:
               return "enabled";
            case 34:
               return "max-connected-clients";
            case 35:
               return "two-wayssl-enabled";
            case 36:
               return "channel-identity-customized";
            case 37:
               return "custom-private-key-alias";
            case 38:
               return "private-key-alias";
            case 39:
               return "custom-private-key-pass-phrase";
            case 40:
               return "private-key-pass-phrase";
            case 41:
               return "custom-private-key-pass-phrase-encrypted";
            case 42:
               return "client-certificate-enforced";
            case 43:
               return "outbound-private-key-enabled";
            case 44:
               return "use-fast-serialization";
            case 45:
               return "idleiiop-connection-timeout";
            case 46:
               return "ssl-listen-port";
            case 47:
               return "externaldns-name";
            case 48:
               return "login-timeout-millisssl";
            case 49:
               return "completet3-message-timeout";
            case 50:
               return "completehttp-message-timeout";
            case 51:
               return "completecom-message-timeout";
            case 52:
               return "completeiiop-message-timeout";
            case 53:
               return "custom-properties";
            case 54:
               return "sdp-enabled";
            case 55:
               return "outbound-private-key-alias";
            case 56:
               return "outbound-private-key-pass-phrase";
            case 57:
               return "custom-identity-key-store-file-name";
            case 58:
               return "custom-identity-key-store-type";
            case 59:
               return "custom-identity-key-store-pass-phrase";
            case 60:
               return "custom-identity-key-store-pass-phrase-encrypted";
            case 61:
               return "hostname-verifier";
            case 62:
               return "hostname-verification-ignored";
            case 63:
               return "ciphersuite";
            case 64:
               return "excluded-ciphersuite";
            case 65:
               return "allow-unencrypted-null-cipher";
            case 66:
               return "inbound-certificate-validation";
            case 67:
               return "outbound-certificate-validation";
            case 68:
               return "minimumtls-protocol-version";
            case 69:
               return "ss-lv2-hello-enabled";
            case 70:
               return "client-init-secure-renegotiation-accepted";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 63:
               return true;
            case 64:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            case 13:
            case 16:
            case 17:
            case 18:
            case 24:
            case 26:
            case 31:
            case 33:
            case 34:
            case 40:
            case 41:
            case 46:
            case 47:
            default:
               return super.isConfigurable(propIndex);
            case 14:
               return true;
            case 15:
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
            case 25:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            case 29:
               return true;
            case 30:
               return true;
            case 32:
               return true;
            case 35:
               return true;
            case 36:
               return true;
            case 37:
               return true;
            case 38:
               return true;
            case 39:
               return true;
            case 42:
               return true;
            case 43:
               return true;
            case 44:
               return true;
            case 45:
               return true;
            case 48:
               return true;
            case 49:
               return true;
            case 50:
               return true;
            case 51:
               return true;
            case 52:
               return true;
            case 53:
               return true;
            case 54:
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
      private NetworkAccessPointMBeanImpl bean;

      protected Helper(NetworkAccessPointMBeanImpl bean) {
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
            case 7:
            case 8:
            case 9:
            default:
               return super.getPropertyName(propIndex);
            case 10:
               return "Protocol";
            case 11:
               return "ListenAddress";
            case 12:
               return "PublicAddress";
            case 13:
               return "ListenPort";
            case 14:
               return "PublicPort";
            case 15:
               return "ResolveDNSName";
            case 16:
               return "ProxyAddress";
            case 17:
               return "ProxyPort";
            case 18:
               return "HttpEnabledForThisProtocol";
            case 19:
               return "AcceptBacklog";
            case 20:
               return "MaxBackoffBetweenFailures";
            case 21:
               return "LoginTimeoutMillis";
            case 22:
               return "TunnelingClientPingSecs";
            case 23:
               return "TunnelingClientTimeoutSecs";
            case 24:
               return "TunnelingEnabled";
            case 25:
               return "CompleteMessageTimeout";
            case 26:
               return "TimeoutConnectionWithPendingResponses";
            case 27:
               return "IdleConnectionTimeout";
            case 28:
               return "ConnectTimeout";
            case 29:
               return "MaxMessageSize";
            case 30:
               return "OutboundEnabled";
            case 31:
               return "ChannelWeight";
            case 32:
               return "ClusterAddress";
            case 33:
               return "Enabled";
            case 34:
               return "MaxConnectedClients";
            case 35:
               return "TwoWaySSLEnabled";
            case 36:
               return "ChannelIdentityCustomized";
            case 37:
               return "CustomPrivateKeyAlias";
            case 38:
               return "PrivateKeyAlias";
            case 39:
               return "CustomPrivateKeyPassPhrase";
            case 40:
               return "PrivateKeyPassPhrase";
            case 41:
               return "CustomPrivateKeyPassPhraseEncrypted";
            case 42:
               return "ClientCertificateEnforced";
            case 43:
               return "OutboundPrivateKeyEnabled";
            case 44:
               return "UseFastSerialization";
            case 45:
               return "IdleIIOPConnectionTimeout";
            case 46:
               return "SSLListenPort";
            case 47:
               return "ExternalDNSName";
            case 48:
               return "LoginTimeoutMillisSSL";
            case 49:
               return "CompleteT3MessageTimeout";
            case 50:
               return "CompleteHTTPMessageTimeout";
            case 51:
               return "CompleteCOMMessageTimeout";
            case 52:
               return "CompleteIIOPMessageTimeout";
            case 53:
               return "CustomProperties";
            case 54:
               return "SDPEnabled";
            case 55:
               return "OutboundPrivateKeyAlias";
            case 56:
               return "OutboundPrivateKeyPassPhrase";
            case 57:
               return "CustomIdentityKeyStoreFileName";
            case 58:
               return "CustomIdentityKeyStoreType";
            case 59:
               return "CustomIdentityKeyStorePassPhrase";
            case 60:
               return "CustomIdentityKeyStorePassPhraseEncrypted";
            case 61:
               return "HostnameVerifier";
            case 62:
               return "HostnameVerificationIgnored";
            case 63:
               return "Ciphersuites";
            case 64:
               return "ExcludedCiphersuites";
            case 65:
               return "AllowUnencryptedNullCipher";
            case 66:
               return "InboundCertificateValidation";
            case 67:
               return "OutboundCertificateValidation";
            case 68:
               return "MinimumTLSProtocolVersion";
            case 69:
               return "SSLv2HelloEnabled";
            case 70:
               return "ClientInitSecureRenegotiationAccepted";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AcceptBacklog")) {
            return 19;
         } else if (propName.equals("ChannelWeight")) {
            return 31;
         } else if (propName.equals("Ciphersuites")) {
            return 63;
         } else if (propName.equals("ClusterAddress")) {
            return 32;
         } else if (propName.equals("CompleteCOMMessageTimeout")) {
            return 51;
         } else if (propName.equals("CompleteHTTPMessageTimeout")) {
            return 50;
         } else if (propName.equals("CompleteIIOPMessageTimeout")) {
            return 52;
         } else if (propName.equals("CompleteMessageTimeout")) {
            return 25;
         } else if (propName.equals("CompleteT3MessageTimeout")) {
            return 49;
         } else if (propName.equals("ConnectTimeout")) {
            return 28;
         } else if (propName.equals("CustomIdentityKeyStoreFileName")) {
            return 57;
         } else if (propName.equals("CustomIdentityKeyStorePassPhrase")) {
            return 59;
         } else if (propName.equals("CustomIdentityKeyStorePassPhraseEncrypted")) {
            return 60;
         } else if (propName.equals("CustomIdentityKeyStoreType")) {
            return 58;
         } else if (propName.equals("CustomPrivateKeyAlias")) {
            return 37;
         } else if (propName.equals("CustomPrivateKeyPassPhrase")) {
            return 39;
         } else if (propName.equals("CustomPrivateKeyPassPhraseEncrypted")) {
            return 41;
         } else if (propName.equals("CustomProperties")) {
            return 53;
         } else if (propName.equals("ExcludedCiphersuites")) {
            return 64;
         } else if (propName.equals("ExternalDNSName")) {
            return 47;
         } else if (propName.equals("HostnameVerifier")) {
            return 61;
         } else if (propName.equals("IdleConnectionTimeout")) {
            return 27;
         } else if (propName.equals("IdleIIOPConnectionTimeout")) {
            return 45;
         } else if (propName.equals("InboundCertificateValidation")) {
            return 66;
         } else if (propName.equals("ListenAddress")) {
            return 11;
         } else if (propName.equals("ListenPort")) {
            return 13;
         } else if (propName.equals("LoginTimeoutMillis")) {
            return 21;
         } else if (propName.equals("LoginTimeoutMillisSSL")) {
            return 48;
         } else if (propName.equals("MaxBackoffBetweenFailures")) {
            return 20;
         } else if (propName.equals("MaxConnectedClients")) {
            return 34;
         } else if (propName.equals("MaxMessageSize")) {
            return 29;
         } else if (propName.equals("MinimumTLSProtocolVersion")) {
            return 68;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("OutboundCertificateValidation")) {
            return 67;
         } else if (propName.equals("OutboundPrivateKeyAlias")) {
            return 55;
         } else if (propName.equals("OutboundPrivateKeyPassPhrase")) {
            return 56;
         } else if (propName.equals("PrivateKeyAlias")) {
            return 38;
         } else if (propName.equals("PrivateKeyPassPhrase")) {
            return 40;
         } else if (propName.equals("Protocol")) {
            return 10;
         } else if (propName.equals("ProxyAddress")) {
            return 16;
         } else if (propName.equals("ProxyPort")) {
            return 17;
         } else if (propName.equals("PublicAddress")) {
            return 12;
         } else if (propName.equals("PublicPort")) {
            return 14;
         } else if (propName.equals("ResolveDNSName")) {
            return 15;
         } else if (propName.equals("SSLListenPort")) {
            return 46;
         } else if (propName.equals("TimeoutConnectionWithPendingResponses")) {
            return 26;
         } else if (propName.equals("TunnelingClientPingSecs")) {
            return 22;
         } else if (propName.equals("TunnelingClientTimeoutSecs")) {
            return 23;
         } else if (propName.equals("UseFastSerialization")) {
            return 44;
         } else if (propName.equals("AllowUnencryptedNullCipher")) {
            return 65;
         } else if (propName.equals("ChannelIdentityCustomized")) {
            return 36;
         } else if (propName.equals("ClientCertificateEnforced")) {
            return 42;
         } else if (propName.equals("ClientInitSecureRenegotiationAccepted")) {
            return 70;
         } else if (propName.equals("Enabled")) {
            return 33;
         } else if (propName.equals("HostnameVerificationIgnored")) {
            return 62;
         } else if (propName.equals("HttpEnabledForThisProtocol")) {
            return 18;
         } else if (propName.equals("OutboundEnabled")) {
            return 30;
         } else if (propName.equals("OutboundPrivateKeyEnabled")) {
            return 43;
         } else if (propName.equals("SDPEnabled")) {
            return 54;
         } else if (propName.equals("SSLv2HelloEnabled")) {
            return 69;
         } else if (propName.equals("TunnelingEnabled")) {
            return 24;
         } else {
            return propName.equals("TwoWaySSLEnabled") ? 35 : super.getPropertyIndex(propName);
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
            if (this.bean.isAcceptBacklogSet()) {
               buf.append("AcceptBacklog");
               buf.append(String.valueOf(this.bean.getAcceptBacklog()));
            }

            if (this.bean.isChannelWeightSet()) {
               buf.append("ChannelWeight");
               buf.append(String.valueOf(this.bean.getChannelWeight()));
            }

            if (this.bean.isCiphersuitesSet()) {
               buf.append("Ciphersuites");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCiphersuites())));
            }

            if (this.bean.isClusterAddressSet()) {
               buf.append("ClusterAddress");
               buf.append(String.valueOf(this.bean.getClusterAddress()));
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

            if (this.bean.isConnectTimeoutSet()) {
               buf.append("ConnectTimeout");
               buf.append(String.valueOf(this.bean.getConnectTimeout()));
            }

            if (this.bean.isCustomIdentityKeyStoreFileNameSet()) {
               buf.append("CustomIdentityKeyStoreFileName");
               buf.append(String.valueOf(this.bean.getCustomIdentityKeyStoreFileName()));
            }

            if (this.bean.isCustomIdentityKeyStorePassPhraseSet()) {
               buf.append("CustomIdentityKeyStorePassPhrase");
               buf.append(String.valueOf(this.bean.getCustomIdentityKeyStorePassPhrase()));
            }

            if (this.bean.isCustomIdentityKeyStorePassPhraseEncryptedSet()) {
               buf.append("CustomIdentityKeyStorePassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCustomIdentityKeyStorePassPhraseEncrypted())));
            }

            if (this.bean.isCustomIdentityKeyStoreTypeSet()) {
               buf.append("CustomIdentityKeyStoreType");
               buf.append(String.valueOf(this.bean.getCustomIdentityKeyStoreType()));
            }

            if (this.bean.isCustomPrivateKeyAliasSet()) {
               buf.append("CustomPrivateKeyAlias");
               buf.append(String.valueOf(this.bean.getCustomPrivateKeyAlias()));
            }

            if (this.bean.isCustomPrivateKeyPassPhraseSet()) {
               buf.append("CustomPrivateKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getCustomPrivateKeyPassPhrase()));
            }

            if (this.bean.isCustomPrivateKeyPassPhraseEncryptedSet()) {
               buf.append("CustomPrivateKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCustomPrivateKeyPassPhraseEncrypted())));
            }

            if (this.bean.isCustomPropertiesSet()) {
               buf.append("CustomProperties");
               buf.append(String.valueOf(this.bean.getCustomProperties()));
            }

            if (this.bean.isExcludedCiphersuitesSet()) {
               buf.append("ExcludedCiphersuites");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getExcludedCiphersuites())));
            }

            if (this.bean.isExternalDNSNameSet()) {
               buf.append("ExternalDNSName");
               buf.append(String.valueOf(this.bean.getExternalDNSName()));
            }

            if (this.bean.isHostnameVerifierSet()) {
               buf.append("HostnameVerifier");
               buf.append(String.valueOf(this.bean.getHostnameVerifier()));
            }

            if (this.bean.isIdleConnectionTimeoutSet()) {
               buf.append("IdleConnectionTimeout");
               buf.append(String.valueOf(this.bean.getIdleConnectionTimeout()));
            }

            if (this.bean.isIdleIIOPConnectionTimeoutSet()) {
               buf.append("IdleIIOPConnectionTimeout");
               buf.append(String.valueOf(this.bean.getIdleIIOPConnectionTimeout()));
            }

            if (this.bean.isInboundCertificateValidationSet()) {
               buf.append("InboundCertificateValidation");
               buf.append(String.valueOf(this.bean.getInboundCertificateValidation()));
            }

            if (this.bean.isListenAddressSet()) {
               buf.append("ListenAddress");
               buf.append(String.valueOf(this.bean.getListenAddress()));
            }

            if (this.bean.isListenPortSet()) {
               buf.append("ListenPort");
               buf.append(String.valueOf(this.bean.getListenPort()));
            }

            if (this.bean.isLoginTimeoutMillisSet()) {
               buf.append("LoginTimeoutMillis");
               buf.append(String.valueOf(this.bean.getLoginTimeoutMillis()));
            }

            if (this.bean.isLoginTimeoutMillisSSLSet()) {
               buf.append("LoginTimeoutMillisSSL");
               buf.append(String.valueOf(this.bean.getLoginTimeoutMillisSSL()));
            }

            if (this.bean.isMaxBackoffBetweenFailuresSet()) {
               buf.append("MaxBackoffBetweenFailures");
               buf.append(String.valueOf(this.bean.getMaxBackoffBetweenFailures()));
            }

            if (this.bean.isMaxConnectedClientsSet()) {
               buf.append("MaxConnectedClients");
               buf.append(String.valueOf(this.bean.getMaxConnectedClients()));
            }

            if (this.bean.isMaxMessageSizeSet()) {
               buf.append("MaxMessageSize");
               buf.append(String.valueOf(this.bean.getMaxMessageSize()));
            }

            if (this.bean.isMinimumTLSProtocolVersionSet()) {
               buf.append("MinimumTLSProtocolVersion");
               buf.append(String.valueOf(this.bean.getMinimumTLSProtocolVersion()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isOutboundCertificateValidationSet()) {
               buf.append("OutboundCertificateValidation");
               buf.append(String.valueOf(this.bean.getOutboundCertificateValidation()));
            }

            if (this.bean.isOutboundPrivateKeyAliasSet()) {
               buf.append("OutboundPrivateKeyAlias");
               buf.append(String.valueOf(this.bean.getOutboundPrivateKeyAlias()));
            }

            if (this.bean.isOutboundPrivateKeyPassPhraseSet()) {
               buf.append("OutboundPrivateKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getOutboundPrivateKeyPassPhrase()));
            }

            if (this.bean.isPrivateKeyAliasSet()) {
               buf.append("PrivateKeyAlias");
               buf.append(String.valueOf(this.bean.getPrivateKeyAlias()));
            }

            if (this.bean.isPrivateKeyPassPhraseSet()) {
               buf.append("PrivateKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getPrivateKeyPassPhrase()));
            }

            if (this.bean.isProtocolSet()) {
               buf.append("Protocol");
               buf.append(String.valueOf(this.bean.getProtocol()));
            }

            if (this.bean.isProxyAddressSet()) {
               buf.append("ProxyAddress");
               buf.append(String.valueOf(this.bean.getProxyAddress()));
            }

            if (this.bean.isProxyPortSet()) {
               buf.append("ProxyPort");
               buf.append(String.valueOf(this.bean.getProxyPort()));
            }

            if (this.bean.isPublicAddressSet()) {
               buf.append("PublicAddress");
               buf.append(String.valueOf(this.bean.getPublicAddress()));
            }

            if (this.bean.isPublicPortSet()) {
               buf.append("PublicPort");
               buf.append(String.valueOf(this.bean.getPublicPort()));
            }

            if (this.bean.isResolveDNSNameSet()) {
               buf.append("ResolveDNSName");
               buf.append(String.valueOf(this.bean.getResolveDNSName()));
            }

            if (this.bean.isSSLListenPortSet()) {
               buf.append("SSLListenPort");
               buf.append(String.valueOf(this.bean.getSSLListenPort()));
            }

            if (this.bean.isTimeoutConnectionWithPendingResponsesSet()) {
               buf.append("TimeoutConnectionWithPendingResponses");
               buf.append(String.valueOf(this.bean.getTimeoutConnectionWithPendingResponses()));
            }

            if (this.bean.isTunnelingClientPingSecsSet()) {
               buf.append("TunnelingClientPingSecs");
               buf.append(String.valueOf(this.bean.getTunnelingClientPingSecs()));
            }

            if (this.bean.isTunnelingClientTimeoutSecsSet()) {
               buf.append("TunnelingClientTimeoutSecs");
               buf.append(String.valueOf(this.bean.getTunnelingClientTimeoutSecs()));
            }

            if (this.bean.isUseFastSerializationSet()) {
               buf.append("UseFastSerialization");
               buf.append(String.valueOf(this.bean.getUseFastSerialization()));
            }

            if (this.bean.isAllowUnencryptedNullCipherSet()) {
               buf.append("AllowUnencryptedNullCipher");
               buf.append(String.valueOf(this.bean.isAllowUnencryptedNullCipher()));
            }

            if (this.bean.isChannelIdentityCustomizedSet()) {
               buf.append("ChannelIdentityCustomized");
               buf.append(String.valueOf(this.bean.isChannelIdentityCustomized()));
            }

            if (this.bean.isClientCertificateEnforcedSet()) {
               buf.append("ClientCertificateEnforced");
               buf.append(String.valueOf(this.bean.isClientCertificateEnforced()));
            }

            if (this.bean.isClientInitSecureRenegotiationAcceptedSet()) {
               buf.append("ClientInitSecureRenegotiationAccepted");
               buf.append(String.valueOf(this.bean.isClientInitSecureRenegotiationAccepted()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
            }

            if (this.bean.isHostnameVerificationIgnoredSet()) {
               buf.append("HostnameVerificationIgnored");
               buf.append(String.valueOf(this.bean.isHostnameVerificationIgnored()));
            }

            if (this.bean.isHttpEnabledForThisProtocolSet()) {
               buf.append("HttpEnabledForThisProtocol");
               buf.append(String.valueOf(this.bean.isHttpEnabledForThisProtocol()));
            }

            if (this.bean.isOutboundEnabledSet()) {
               buf.append("OutboundEnabled");
               buf.append(String.valueOf(this.bean.isOutboundEnabled()));
            }

            if (this.bean.isOutboundPrivateKeyEnabledSet()) {
               buf.append("OutboundPrivateKeyEnabled");
               buf.append(String.valueOf(this.bean.isOutboundPrivateKeyEnabled()));
            }

            if (this.bean.isSDPEnabledSet()) {
               buf.append("SDPEnabled");
               buf.append(String.valueOf(this.bean.isSDPEnabled()));
            }

            if (this.bean.isSSLv2HelloEnabledSet()) {
               buf.append("SSLv2HelloEnabled");
               buf.append(String.valueOf(this.bean.isSSLv2HelloEnabled()));
            }

            if (this.bean.isTunnelingEnabledSet()) {
               buf.append("TunnelingEnabled");
               buf.append(String.valueOf(this.bean.isTunnelingEnabled()));
            }

            if (this.bean.isTwoWaySSLEnabledSet()) {
               buf.append("TwoWaySSLEnabled");
               buf.append(String.valueOf(this.bean.isTwoWaySSLEnabled()));
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
            NetworkAccessPointMBeanImpl otherTyped = (NetworkAccessPointMBeanImpl)other;
            this.computeDiff("AcceptBacklog", this.bean.getAcceptBacklog(), otherTyped.getAcceptBacklog(), true);
            this.computeDiff("ChannelWeight", this.bean.getChannelWeight(), otherTyped.getChannelWeight(), true);
            this.computeDiff("Ciphersuites", this.bean.getCiphersuites(), otherTyped.getCiphersuites(), true);
            this.computeDiff("ClusterAddress", this.bean.getClusterAddress(), otherTyped.getClusterAddress(), true);
            this.computeDiff("CompleteCOMMessageTimeout", this.bean.getCompleteCOMMessageTimeout(), otherTyped.getCompleteCOMMessageTimeout(), true);
            this.computeDiff("CompleteHTTPMessageTimeout", this.bean.getCompleteHTTPMessageTimeout(), otherTyped.getCompleteHTTPMessageTimeout(), true);
            this.computeDiff("CompleteIIOPMessageTimeout", this.bean.getCompleteIIOPMessageTimeout(), otherTyped.getCompleteIIOPMessageTimeout(), true);
            this.computeDiff("CompleteMessageTimeout", this.bean.getCompleteMessageTimeout(), otherTyped.getCompleteMessageTimeout(), true);
            this.computeDiff("CompleteT3MessageTimeout", this.bean.getCompleteT3MessageTimeout(), otherTyped.getCompleteT3MessageTimeout(), true);
            this.computeDiff("ConnectTimeout", this.bean.getConnectTimeout(), otherTyped.getConnectTimeout(), true);
            this.computeDiff("CustomIdentityKeyStoreFileName", this.bean.getCustomIdentityKeyStoreFileName(), otherTyped.getCustomIdentityKeyStoreFileName(), true);
            this.computeDiff("CustomIdentityKeyStorePassPhraseEncrypted", this.bean.getCustomIdentityKeyStorePassPhraseEncrypted(), otherTyped.getCustomIdentityKeyStorePassPhraseEncrypted(), true);
            this.computeDiff("CustomIdentityKeyStoreType", this.bean.getCustomIdentityKeyStoreType(), otherTyped.getCustomIdentityKeyStoreType(), true);
            this.computeDiff("CustomPrivateKeyAlias", this.bean.getCustomPrivateKeyAlias(), otherTyped.getCustomPrivateKeyAlias(), true);
            this.computeDiff("CustomPrivateKeyPassPhraseEncrypted", this.bean.getCustomPrivateKeyPassPhraseEncrypted(), otherTyped.getCustomPrivateKeyPassPhraseEncrypted(), true);
            this.computeDiff("CustomProperties", this.bean.getCustomProperties(), otherTyped.getCustomProperties(), false);
            this.computeDiff("ExcludedCiphersuites", this.bean.getExcludedCiphersuites(), otherTyped.getExcludedCiphersuites(), true);
            this.computeDiff("ExternalDNSName", this.bean.getExternalDNSName(), otherTyped.getExternalDNSName(), false);
            this.computeDiff("HostnameVerifier", this.bean.getHostnameVerifier(), otherTyped.getHostnameVerifier(), true);
            this.computeDiff("IdleConnectionTimeout", this.bean.getIdleConnectionTimeout(), otherTyped.getIdleConnectionTimeout(), true);
            this.computeDiff("IdleIIOPConnectionTimeout", this.bean.getIdleIIOPConnectionTimeout(), otherTyped.getIdleIIOPConnectionTimeout(), true);
            this.computeDiff("InboundCertificateValidation", this.bean.getInboundCertificateValidation(), otherTyped.getInboundCertificateValidation(), true);
            this.computeDiff("ListenAddress", this.bean.getListenAddress(), otherTyped.getListenAddress(), false);
            this.computeDiff("ListenPort", this.bean.getListenPort(), otherTyped.getListenPort(), false);
            this.computeDiff("LoginTimeoutMillis", this.bean.getLoginTimeoutMillis(), otherTyped.getLoginTimeoutMillis(), true);
            this.computeDiff("LoginTimeoutMillisSSL", this.bean.getLoginTimeoutMillisSSL(), otherTyped.getLoginTimeoutMillisSSL(), true);
            this.computeDiff("MaxBackoffBetweenFailures", this.bean.getMaxBackoffBetweenFailures(), otherTyped.getMaxBackoffBetweenFailures(), true);
            this.computeDiff("MaxConnectedClients", this.bean.getMaxConnectedClients(), otherTyped.getMaxConnectedClients(), true);
            this.computeDiff("MaxMessageSize", this.bean.getMaxMessageSize(), otherTyped.getMaxMessageSize(), true);
            this.computeDiff("MinimumTLSProtocolVersion", this.bean.getMinimumTLSProtocolVersion(), otherTyped.getMinimumTLSProtocolVersion(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("OutboundCertificateValidation", this.bean.getOutboundCertificateValidation(), otherTyped.getOutboundCertificateValidation(), true);
            this.computeDiff("OutboundPrivateKeyAlias", this.bean.getOutboundPrivateKeyAlias(), otherTyped.getOutboundPrivateKeyAlias(), true);
            this.computeDiff("OutboundPrivateKeyPassPhrase", this.bean.getOutboundPrivateKeyPassPhrase(), otherTyped.getOutboundPrivateKeyPassPhrase(), true);
            this.computeDiff("PrivateKeyAlias", this.bean.getPrivateKeyAlias(), otherTyped.getPrivateKeyAlias(), true);
            this.computeDiff("PrivateKeyPassPhrase", this.bean.getPrivateKeyPassPhrase(), otherTyped.getPrivateKeyPassPhrase(), true);
            this.computeDiff("Protocol", this.bean.getProtocol(), otherTyped.getProtocol(), false);
            this.computeDiff("ProxyAddress", this.bean.getProxyAddress(), otherTyped.getProxyAddress(), true);
            this.computeDiff("ProxyPort", this.bean.getProxyPort(), otherTyped.getProxyPort(), true);
            this.computeDiff("PublicAddress", this.bean.getPublicAddress(), otherTyped.getPublicAddress(), true);
            this.computeDiff("PublicPort", this.bean.getPublicPort(), otherTyped.getPublicPort(), true);
            this.computeDiff("ResolveDNSName", this.bean.getResolveDNSName(), otherTyped.getResolveDNSName(), false);
            this.computeDiff("SSLListenPort", this.bean.getSSLListenPort(), otherTyped.getSSLListenPort(), false);
            this.computeDiff("TimeoutConnectionWithPendingResponses", this.bean.getTimeoutConnectionWithPendingResponses(), otherTyped.getTimeoutConnectionWithPendingResponses(), true);
            this.computeDiff("TunnelingClientPingSecs", this.bean.getTunnelingClientPingSecs(), otherTyped.getTunnelingClientPingSecs(), true);
            this.computeDiff("TunnelingClientTimeoutSecs", this.bean.getTunnelingClientTimeoutSecs(), otherTyped.getTunnelingClientTimeoutSecs(), true);
            this.computeDiff("UseFastSerialization", this.bean.getUseFastSerialization(), otherTyped.getUseFastSerialization(), true);
            this.computeDiff("AllowUnencryptedNullCipher", this.bean.isAllowUnencryptedNullCipher(), otherTyped.isAllowUnencryptedNullCipher(), true);
            this.computeDiff("ChannelIdentityCustomized", this.bean.isChannelIdentityCustomized(), otherTyped.isChannelIdentityCustomized(), true);
            this.computeDiff("ClientCertificateEnforced", this.bean.isClientCertificateEnforced(), otherTyped.isClientCertificateEnforced(), true);
            this.computeDiff("ClientInitSecureRenegotiationAccepted", this.bean.isClientInitSecureRenegotiationAccepted(), otherTyped.isClientInitSecureRenegotiationAccepted(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
            this.computeDiff("HostnameVerificationIgnored", this.bean.isHostnameVerificationIgnored(), otherTyped.isHostnameVerificationIgnored(), true);
            this.computeDiff("HttpEnabledForThisProtocol", this.bean.isHttpEnabledForThisProtocol(), otherTyped.isHttpEnabledForThisProtocol(), true);
            this.computeDiff("OutboundEnabled", this.bean.isOutboundEnabled(), otherTyped.isOutboundEnabled(), true);
            this.computeDiff("OutboundPrivateKeyEnabled", this.bean.isOutboundPrivateKeyEnabled(), otherTyped.isOutboundPrivateKeyEnabled(), true);
            this.computeDiff("SDPEnabled", this.bean.isSDPEnabled(), otherTyped.isSDPEnabled(), false);
            this.computeDiff("SSLv2HelloEnabled", this.bean.isSSLv2HelloEnabled(), otherTyped.isSSLv2HelloEnabled(), true);
            this.computeDiff("TunnelingEnabled", this.bean.isTunnelingEnabled(), otherTyped.isTunnelingEnabled(), true);
            this.computeDiff("TwoWaySSLEnabled", this.bean.isTwoWaySSLEnabled(), otherTyped.isTwoWaySSLEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            NetworkAccessPointMBeanImpl original = (NetworkAccessPointMBeanImpl)event.getSourceBean();
            NetworkAccessPointMBeanImpl proposed = (NetworkAccessPointMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AcceptBacklog")) {
                  original.setAcceptBacklog(proposed.getAcceptBacklog());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("ChannelWeight")) {
                  original.setChannelWeight(proposed.getChannelWeight());
                  original._conditionalUnset(update.isUnsetUpdate(), 31);
               } else if (prop.equals("Ciphersuites")) {
                  original.setCiphersuites(proposed.getCiphersuites());
                  original._conditionalUnset(update.isUnsetUpdate(), 63);
               } else if (prop.equals("ClusterAddress")) {
                  original.setClusterAddress(proposed.getClusterAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (prop.equals("CompleteCOMMessageTimeout")) {
                  original.setCompleteCOMMessageTimeout(proposed.getCompleteCOMMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 51);
               } else if (prop.equals("CompleteHTTPMessageTimeout")) {
                  original.setCompleteHTTPMessageTimeout(proposed.getCompleteHTTPMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 50);
               } else if (prop.equals("CompleteIIOPMessageTimeout")) {
                  original.setCompleteIIOPMessageTimeout(proposed.getCompleteIIOPMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 52);
               } else if (prop.equals("CompleteMessageTimeout")) {
                  original.setCompleteMessageTimeout(proposed.getCompleteMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("CompleteT3MessageTimeout")) {
                  original.setCompleteT3MessageTimeout(proposed.getCompleteT3MessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 49);
               } else if (prop.equals("ConnectTimeout")) {
                  original.setConnectTimeout(proposed.getConnectTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("CustomIdentityKeyStoreFileName")) {
                  original.setCustomIdentityKeyStoreFileName(proposed.getCustomIdentityKeyStoreFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 57);
               } else if (!prop.equals("CustomIdentityKeyStorePassPhrase")) {
                  if (prop.equals("CustomIdentityKeyStorePassPhraseEncrypted")) {
                     original.setCustomIdentityKeyStorePassPhraseEncrypted(proposed.getCustomIdentityKeyStorePassPhraseEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 60);
                  } else if (prop.equals("CustomIdentityKeyStoreType")) {
                     original.setCustomIdentityKeyStoreType(proposed.getCustomIdentityKeyStoreType());
                     original._conditionalUnset(update.isUnsetUpdate(), 58);
                  } else if (prop.equals("CustomPrivateKeyAlias")) {
                     original.setCustomPrivateKeyAlias(proposed.getCustomPrivateKeyAlias());
                     original._conditionalUnset(update.isUnsetUpdate(), 37);
                  } else if (!prop.equals("CustomPrivateKeyPassPhrase")) {
                     if (prop.equals("CustomPrivateKeyPassPhraseEncrypted")) {
                        original.setCustomPrivateKeyPassPhraseEncrypted(proposed.getCustomPrivateKeyPassPhraseEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 41);
                     } else if (prop.equals("CustomProperties")) {
                        original.setCustomProperties(proposed.getCustomProperties() == null ? null : (Properties)proposed.getCustomProperties().clone());
                        original._conditionalUnset(update.isUnsetUpdate(), 53);
                     } else if (prop.equals("ExcludedCiphersuites")) {
                        original.setExcludedCiphersuites(proposed.getExcludedCiphersuites());
                        original._conditionalUnset(update.isUnsetUpdate(), 64);
                     } else if (prop.equals("ExternalDNSName")) {
                        original.setExternalDNSName(proposed.getExternalDNSName());
                        original._conditionalUnset(update.isUnsetUpdate(), 47);
                     } else if (prop.equals("HostnameVerifier")) {
                        original.setHostnameVerifier(proposed.getHostnameVerifier());
                        original._conditionalUnset(update.isUnsetUpdate(), 61);
                     } else if (prop.equals("IdleConnectionTimeout")) {
                        original.setIdleConnectionTimeout(proposed.getIdleConnectionTimeout());
                        original._conditionalUnset(update.isUnsetUpdate(), 27);
                     } else if (prop.equals("IdleIIOPConnectionTimeout")) {
                        original.setIdleIIOPConnectionTimeout(proposed.getIdleIIOPConnectionTimeout());
                        original._conditionalUnset(update.isUnsetUpdate(), 45);
                     } else if (prop.equals("InboundCertificateValidation")) {
                        original.setInboundCertificateValidation(proposed.getInboundCertificateValidation());
                        original._conditionalUnset(update.isUnsetUpdate(), 66);
                     } else if (prop.equals("ListenAddress")) {
                        original.setListenAddress(proposed.getListenAddress());
                        original._conditionalUnset(update.isUnsetUpdate(), 11);
                     } else if (prop.equals("ListenPort")) {
                        original.setListenPort(proposed.getListenPort());
                        original._conditionalUnset(update.isUnsetUpdate(), 13);
                     } else if (prop.equals("LoginTimeoutMillis")) {
                        original.setLoginTimeoutMillis(proposed.getLoginTimeoutMillis());
                        original._conditionalUnset(update.isUnsetUpdate(), 21);
                     } else if (prop.equals("LoginTimeoutMillisSSL")) {
                        original.setLoginTimeoutMillisSSL(proposed.getLoginTimeoutMillisSSL());
                        original._conditionalUnset(update.isUnsetUpdate(), 48);
                     } else if (prop.equals("MaxBackoffBetweenFailures")) {
                        original.setMaxBackoffBetweenFailures(proposed.getMaxBackoffBetweenFailures());
                        original._conditionalUnset(update.isUnsetUpdate(), 20);
                     } else if (prop.equals("MaxConnectedClients")) {
                        original.setMaxConnectedClients(proposed.getMaxConnectedClients());
                        original._conditionalUnset(update.isUnsetUpdate(), 34);
                     } else if (prop.equals("MaxMessageSize")) {
                        original.setMaxMessageSize(proposed.getMaxMessageSize());
                        original._conditionalUnset(update.isUnsetUpdate(), 29);
                     } else if (prop.equals("MinimumTLSProtocolVersion")) {
                        original.setMinimumTLSProtocolVersion(proposed.getMinimumTLSProtocolVersion());
                        original._conditionalUnset(update.isUnsetUpdate(), 68);
                     } else if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (prop.equals("OutboundCertificateValidation")) {
                        original.setOutboundCertificateValidation(proposed.getOutboundCertificateValidation());
                        original._conditionalUnset(update.isUnsetUpdate(), 67);
                     } else if (prop.equals("OutboundPrivateKeyAlias")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 55);
                     } else if (prop.equals("OutboundPrivateKeyPassPhrase")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 56);
                     } else if (prop.equals("PrivateKeyAlias")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 38);
                     } else if (prop.equals("PrivateKeyPassPhrase")) {
                        original._conditionalUnset(update.isUnsetUpdate(), 40);
                     } else if (prop.equals("Protocol")) {
                        original.setProtocol(proposed.getProtocol());
                        original._conditionalUnset(update.isUnsetUpdate(), 10);
                     } else if (prop.equals("ProxyAddress")) {
                        original.setProxyAddress(proposed.getProxyAddress());
                        original._conditionalUnset(update.isUnsetUpdate(), 16);
                     } else if (prop.equals("ProxyPort")) {
                        original.setProxyPort(proposed.getProxyPort());
                        original._conditionalUnset(update.isUnsetUpdate(), 17);
                     } else if (prop.equals("PublicAddress")) {
                        original.setPublicAddress(proposed.getPublicAddress());
                        original._conditionalUnset(update.isUnsetUpdate(), 12);
                     } else if (prop.equals("PublicPort")) {
                        original.setPublicPort(proposed.getPublicPort());
                        original._conditionalUnset(update.isUnsetUpdate(), 14);
                     } else if (prop.equals("ResolveDNSName")) {
                        original.setResolveDNSName(proposed.getResolveDNSName());
                        original._conditionalUnset(update.isUnsetUpdate(), 15);
                     } else if (prop.equals("SSLListenPort")) {
                        original.setSSLListenPort(proposed.getSSLListenPort());
                        original._conditionalUnset(update.isUnsetUpdate(), 46);
                     } else if (prop.equals("TimeoutConnectionWithPendingResponses")) {
                        original.setTimeoutConnectionWithPendingResponses(proposed.getTimeoutConnectionWithPendingResponses());
                        original._conditionalUnset(update.isUnsetUpdate(), 26);
                     } else if (prop.equals("TunnelingClientPingSecs")) {
                        original.setTunnelingClientPingSecs(proposed.getTunnelingClientPingSecs());
                        original._conditionalUnset(update.isUnsetUpdate(), 22);
                     } else if (prop.equals("TunnelingClientTimeoutSecs")) {
                        original.setTunnelingClientTimeoutSecs(proposed.getTunnelingClientTimeoutSecs());
                        original._conditionalUnset(update.isUnsetUpdate(), 23);
                     } else if (prop.equals("UseFastSerialization")) {
                        original.setUseFastSerialization(proposed.getUseFastSerialization());
                        original._conditionalUnset(update.isUnsetUpdate(), 44);
                     } else if (prop.equals("AllowUnencryptedNullCipher")) {
                        original.setAllowUnencryptedNullCipher(proposed.isAllowUnencryptedNullCipher());
                        original._conditionalUnset(update.isUnsetUpdate(), 65);
                     } else if (prop.equals("ChannelIdentityCustomized")) {
                        original.setChannelIdentityCustomized(proposed.isChannelIdentityCustomized());
                        original._conditionalUnset(update.isUnsetUpdate(), 36);
                     } else if (prop.equals("ClientCertificateEnforced")) {
                        original.setClientCertificateEnforced(proposed.isClientCertificateEnforced());
                        original._conditionalUnset(update.isUnsetUpdate(), 42);
                     } else if (prop.equals("ClientInitSecureRenegotiationAccepted")) {
                        original.setClientInitSecureRenegotiationAccepted(proposed.isClientInitSecureRenegotiationAccepted());
                        original._conditionalUnset(update.isUnsetUpdate(), 70);
                     } else if (prop.equals("Enabled")) {
                        original.setEnabled(proposed.isEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 33);
                     } else if (prop.equals("HostnameVerificationIgnored")) {
                        original.setHostnameVerificationIgnored(proposed.isHostnameVerificationIgnored());
                        original._conditionalUnset(update.isUnsetUpdate(), 62);
                     } else if (prop.equals("HttpEnabledForThisProtocol")) {
                        original.setHttpEnabledForThisProtocol(proposed.isHttpEnabledForThisProtocol());
                        original._conditionalUnset(update.isUnsetUpdate(), 18);
                     } else if (prop.equals("OutboundEnabled")) {
                        original.setOutboundEnabled(proposed.isOutboundEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 30);
                     } else if (prop.equals("OutboundPrivateKeyEnabled")) {
                        original.setOutboundPrivateKeyEnabled(proposed.isOutboundPrivateKeyEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 43);
                     } else if (prop.equals("SDPEnabled")) {
                        original.setSDPEnabled(proposed.isSDPEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 54);
                     } else if (prop.equals("SSLv2HelloEnabled")) {
                        original.setSSLv2HelloEnabled(proposed.isSSLv2HelloEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 69);
                     } else if (prop.equals("TunnelingEnabled")) {
                        original.setTunnelingEnabled(proposed.isTunnelingEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 24);
                     } else if (prop.equals("TwoWaySSLEnabled")) {
                        original.setTwoWaySSLEnabled(proposed.isTwoWaySSLEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 35);
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
            NetworkAccessPointMBeanImpl copy = (NetworkAccessPointMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AcceptBacklog")) && this.bean.isAcceptBacklogSet()) {
               copy.setAcceptBacklog(this.bean.getAcceptBacklog());
            }

            if ((excludeProps == null || !excludeProps.contains("ChannelWeight")) && this.bean.isChannelWeightSet()) {
               copy.setChannelWeight(this.bean.getChannelWeight());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Ciphersuites")) && this.bean.isCiphersuitesSet()) {
               o = this.bean.getCiphersuites();
               copy.setCiphersuites(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ClusterAddress")) && this.bean.isClusterAddressSet()) {
               copy.setClusterAddress(this.bean.getClusterAddress());
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

            if ((excludeProps == null || !excludeProps.contains("ConnectTimeout")) && this.bean.isConnectTimeoutSet()) {
               copy.setConnectTimeout(this.bean.getConnectTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomIdentityKeyStoreFileName")) && this.bean.isCustomIdentityKeyStoreFileNameSet()) {
               copy.setCustomIdentityKeyStoreFileName(this.bean.getCustomIdentityKeyStoreFileName());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("CustomIdentityKeyStorePassPhraseEncrypted")) && this.bean.isCustomIdentityKeyStorePassPhraseEncryptedSet()) {
               o = this.bean.getCustomIdentityKeyStorePassPhraseEncrypted();
               copy.setCustomIdentityKeyStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomIdentityKeyStoreType")) && this.bean.isCustomIdentityKeyStoreTypeSet()) {
               copy.setCustomIdentityKeyStoreType(this.bean.getCustomIdentityKeyStoreType());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomPrivateKeyAlias")) && this.bean.isCustomPrivateKeyAliasSet()) {
               copy.setCustomPrivateKeyAlias(this.bean.getCustomPrivateKeyAlias());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomPrivateKeyPassPhraseEncrypted")) && this.bean.isCustomPrivateKeyPassPhraseEncryptedSet()) {
               o = this.bean.getCustomPrivateKeyPassPhraseEncrypted();
               copy.setCustomPrivateKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomProperties")) && this.bean.isCustomPropertiesSet()) {
               copy.setCustomProperties(this.bean.getCustomProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("ExcludedCiphersuites")) && this.bean.isExcludedCiphersuitesSet()) {
               o = this.bean.getExcludedCiphersuites();
               copy.setExcludedCiphersuites(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ExternalDNSName")) && this.bean.isExternalDNSNameSet()) {
               copy.setExternalDNSName(this.bean.getExternalDNSName());
            }

            if ((excludeProps == null || !excludeProps.contains("HostnameVerifier")) && this.bean.isHostnameVerifierSet()) {
               copy.setHostnameVerifier(this.bean.getHostnameVerifier());
            }

            if ((excludeProps == null || !excludeProps.contains("IdleConnectionTimeout")) && this.bean.isIdleConnectionTimeoutSet()) {
               copy.setIdleConnectionTimeout(this.bean.getIdleConnectionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("IdleIIOPConnectionTimeout")) && this.bean.isIdleIIOPConnectionTimeoutSet()) {
               copy.setIdleIIOPConnectionTimeout(this.bean.getIdleIIOPConnectionTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("InboundCertificateValidation")) && this.bean.isInboundCertificateValidationSet()) {
               copy.setInboundCertificateValidation(this.bean.getInboundCertificateValidation());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenAddress")) && this.bean.isListenAddressSet()) {
               copy.setListenAddress(this.bean.getListenAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenPort")) && this.bean.isListenPortSet()) {
               copy.setListenPort(this.bean.getListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginTimeoutMillis")) && this.bean.isLoginTimeoutMillisSet()) {
               copy.setLoginTimeoutMillis(this.bean.getLoginTimeoutMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginTimeoutMillisSSL")) && this.bean.isLoginTimeoutMillisSSLSet()) {
               copy.setLoginTimeoutMillisSSL(this.bean.getLoginTimeoutMillisSSL());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxBackoffBetweenFailures")) && this.bean.isMaxBackoffBetweenFailuresSet()) {
               copy.setMaxBackoffBetweenFailures(this.bean.getMaxBackoffBetweenFailures());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConnectedClients")) && this.bean.isMaxConnectedClientsSet()) {
               copy.setMaxConnectedClients(this.bean.getMaxConnectedClients());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxMessageSize")) && this.bean.isMaxMessageSizeSet()) {
               copy.setMaxMessageSize(this.bean.getMaxMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MinimumTLSProtocolVersion")) && this.bean.isMinimumTLSProtocolVersionSet()) {
               copy.setMinimumTLSProtocolVersion(this.bean.getMinimumTLSProtocolVersion());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundCertificateValidation")) && this.bean.isOutboundCertificateValidationSet()) {
               copy.setOutboundCertificateValidation(this.bean.getOutboundCertificateValidation());
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundPrivateKeyAlias")) && this.bean.isOutboundPrivateKeyAliasSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundPrivateKeyPassPhrase")) && this.bean.isOutboundPrivateKeyPassPhraseSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("PrivateKeyAlias")) && this.bean.isPrivateKeyAliasSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("PrivateKeyPassPhrase")) && this.bean.isPrivateKeyPassPhraseSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("Protocol")) && this.bean.isProtocolSet()) {
               copy.setProtocol(this.bean.getProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("ProxyAddress")) && this.bean.isProxyAddressSet()) {
               copy.setProxyAddress(this.bean.getProxyAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("ProxyPort")) && this.bean.isProxyPortSet()) {
               copy.setProxyPort(this.bean.getProxyPort());
            }

            if ((excludeProps == null || !excludeProps.contains("PublicAddress")) && this.bean.isPublicAddressSet()) {
               copy.setPublicAddress(this.bean.getPublicAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("PublicPort")) && this.bean.isPublicPortSet()) {
               copy.setPublicPort(this.bean.getPublicPort());
            }

            if ((excludeProps == null || !excludeProps.contains("ResolveDNSName")) && this.bean.isResolveDNSNameSet()) {
               copy.setResolveDNSName(this.bean.getResolveDNSName());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLListenPort")) && this.bean.isSSLListenPortSet()) {
               copy.setSSLListenPort(this.bean.getSSLListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeoutConnectionWithPendingResponses")) && this.bean.isTimeoutConnectionWithPendingResponsesSet()) {
               copy.setTimeoutConnectionWithPendingResponses(this.bean.getTimeoutConnectionWithPendingResponses());
            }

            if ((excludeProps == null || !excludeProps.contains("TunnelingClientPingSecs")) && this.bean.isTunnelingClientPingSecsSet()) {
               copy.setTunnelingClientPingSecs(this.bean.getTunnelingClientPingSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("TunnelingClientTimeoutSecs")) && this.bean.isTunnelingClientTimeoutSecsSet()) {
               copy.setTunnelingClientTimeoutSecs(this.bean.getTunnelingClientTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("UseFastSerialization")) && this.bean.isUseFastSerializationSet()) {
               copy.setUseFastSerialization(this.bean.getUseFastSerialization());
            }

            if ((excludeProps == null || !excludeProps.contains("AllowUnencryptedNullCipher")) && this.bean.isAllowUnencryptedNullCipherSet()) {
               copy.setAllowUnencryptedNullCipher(this.bean.isAllowUnencryptedNullCipher());
            }

            if ((excludeProps == null || !excludeProps.contains("ChannelIdentityCustomized")) && this.bean.isChannelIdentityCustomizedSet()) {
               copy.setChannelIdentityCustomized(this.bean.isChannelIdentityCustomized());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientCertificateEnforced")) && this.bean.isClientCertificateEnforcedSet()) {
               copy.setClientCertificateEnforced(this.bean.isClientCertificateEnforced());
            }

            if ((excludeProps == null || !excludeProps.contains("ClientInitSecureRenegotiationAccepted")) && this.bean.isClientInitSecureRenegotiationAcceptedSet()) {
               copy.setClientInitSecureRenegotiationAccepted(this.bean.isClientInitSecureRenegotiationAccepted());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("HostnameVerificationIgnored")) && this.bean.isHostnameVerificationIgnoredSet()) {
               copy.setHostnameVerificationIgnored(this.bean.isHostnameVerificationIgnored());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpEnabledForThisProtocol")) && this.bean.isHttpEnabledForThisProtocolSet()) {
               copy.setHttpEnabledForThisProtocol(this.bean.isHttpEnabledForThisProtocol());
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundEnabled")) && this.bean.isOutboundEnabledSet()) {
               copy.setOutboundEnabled(this.bean.isOutboundEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("OutboundPrivateKeyEnabled")) && this.bean.isOutboundPrivateKeyEnabledSet()) {
               copy.setOutboundPrivateKeyEnabled(this.bean.isOutboundPrivateKeyEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SDPEnabled")) && this.bean.isSDPEnabledSet()) {
               copy.setSDPEnabled(this.bean.isSDPEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLv2HelloEnabled")) && this.bean.isSSLv2HelloEnabledSet()) {
               copy.setSSLv2HelloEnabled(this.bean.isSSLv2HelloEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TunnelingEnabled")) && this.bean.isTunnelingEnabledSet()) {
               copy.setTunnelingEnabled(this.bean.isTunnelingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TwoWaySSLEnabled")) && this.bean.isTwoWaySSLEnabledSet()) {
               copy.setTwoWaySSLEnabled(this.bean.isTwoWaySSLEnabled());
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

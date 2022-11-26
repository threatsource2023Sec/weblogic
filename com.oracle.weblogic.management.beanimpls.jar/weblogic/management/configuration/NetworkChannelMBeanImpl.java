package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class NetworkChannelMBeanImpl extends DeploymentMBeanImpl implements NetworkChannelMBean, Serializable {
   private int _AcceptBacklog;
   private boolean _BoundOutgoingEnabled;
   private boolean _COMEnabled;
   private int _ChannelWeight;
   private String _ClusterAddress;
   private int _CompleteCOMMessageTimeout;
   private int _CompleteHTTPMessageTimeout;
   private int _CompleteIIOPMessageTimeout;
   private int _CompleteT3MessageTimeout;
   private String _DefaultIIOPPassword;
   private byte[] _DefaultIIOPPasswordEncrypted;
   private String _DefaultIIOPUser;
   private String _Description;
   private boolean _HTTPEnabled;
   private boolean _HTTPSEnabled;
   private boolean _IIOPEnabled;
   private boolean _IIOPSEnabled;
   private int _IdleIIOPConnectionTimeout;
   private int _ListenPort;
   private boolean _ListenPortEnabled;
   private int _LoginTimeoutMillis;
   private int _LoginTimeoutMillisSSL;
   private int _MaxCOMMessageSize;
   private int _MaxHTTPMessageSize;
   private int _MaxIIOPMessageSize;
   private int _MaxT3MessageSize;
   private String _Name;
   private boolean _OutgoingEnabled;
   private int _SSLListenPort;
   private boolean _SSLListenPortEnabled;
   private boolean _T3Enabled;
   private boolean _T3SEnabled;
   private int _TunnelingClientPingSecs;
   private int _TunnelingClientTimeoutSecs;
   private boolean _TunnelingEnabled;
   private static SchemaHelper2 _schemaHelper;

   public NetworkChannelMBeanImpl() {
      this._initializeProperty(-1);
   }

   public NetworkChannelMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public NetworkChannelMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      NetworkChannelValidator.validateName(param0);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(12);
   }

   public void setDescription(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getListenPort() {
      return this._ListenPort;
   }

   public boolean isListenPortInherited() {
      return false;
   }

   public boolean isListenPortSet() {
      return this._isSet(13);
   }

   public void setListenPort(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("ListenPort", (long)param0, 1L, 65535L);
      int _oldVal = this._ListenPort;
      this._ListenPort = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isListenPortEnabled() {
      return this._ListenPortEnabled;
   }

   public boolean isListenPortEnabledInherited() {
      return false;
   }

   public boolean isListenPortEnabledSet() {
      return this._isSet(14);
   }

   public void setListenPortEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._ListenPortEnabled;
      this._ListenPortEnabled = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getSSLListenPort() {
      return this._SSLListenPort;
   }

   public boolean isSSLListenPortInherited() {
      return false;
   }

   public boolean isSSLListenPortSet() {
      return this._isSet(15);
   }

   public void setSSLListenPort(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("SSLListenPort", (long)param0, 1L, 65535L);
      int _oldVal = this._SSLListenPort;
      this._SSLListenPort = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean isSSLListenPortEnabled() {
      return this._SSLListenPortEnabled;
   }

   public boolean isSSLListenPortEnabledInherited() {
      return false;
   }

   public boolean isSSLListenPortEnabledSet() {
      return this._isSet(16);
   }

   public void setSSLListenPortEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._SSLListenPortEnabled;
      this._SSLListenPortEnabled = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getClusterAddress() {
      return this._ClusterAddress;
   }

   public boolean isClusterAddressInherited() {
      return false;
   }

   public boolean isClusterAddressSet() {
      return this._isSet(17);
   }

   public void setClusterAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClusterAddress;
      this._ClusterAddress = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isT3Enabled() {
      return this._T3Enabled;
   }

   public boolean isT3EnabledInherited() {
      return false;
   }

   public boolean isT3EnabledSet() {
      return this._isSet(18);
   }

   public void setT3Enabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._T3Enabled;
      this._T3Enabled = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isT3SEnabled() {
      return this._T3SEnabled;
   }

   public boolean isT3SEnabledInherited() {
      return false;
   }

   public boolean isT3SEnabledSet() {
      return this._isSet(19);
   }

   public void setT3SEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._T3SEnabled;
      this._T3SEnabled = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean isHTTPEnabled() {
      return this._HTTPEnabled;
   }

   public boolean isHTTPEnabledInherited() {
      return false;
   }

   public boolean isHTTPEnabledSet() {
      return this._isSet(20);
   }

   public void setHTTPEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._HTTPEnabled;
      this._HTTPEnabled = param0;
      this._postSet(20, _oldVal, param0);
   }

   public boolean isHTTPSEnabled() {
      return this._HTTPSEnabled;
   }

   public boolean isHTTPSEnabledInherited() {
      return false;
   }

   public boolean isHTTPSEnabledSet() {
      return this._isSet(21);
   }

   public void setHTTPSEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._HTTPSEnabled;
      this._HTTPSEnabled = param0;
      this._postSet(21, _oldVal, param0);
   }

   public boolean isCOMEnabled() {
      return this._COMEnabled;
   }

   public boolean isCOMEnabledInherited() {
      return false;
   }

   public boolean isCOMEnabledSet() {
      return this._isSet(22);
   }

   public void setCOMEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._COMEnabled;
      this._COMEnabled = param0;
      this._postSet(22, _oldVal, param0);
   }

   public boolean isOutgoingEnabled() {
      return this._OutgoingEnabled;
   }

   public boolean isOutgoingEnabledInherited() {
      return false;
   }

   public boolean isOutgoingEnabledSet() {
      return this._isSet(23);
   }

   public void setOutgoingEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._OutgoingEnabled;
      this._OutgoingEnabled = param0;
      this._postSet(23, _oldVal, param0);
   }

   public boolean isBoundOutgoingEnabled() {
      return this._BoundOutgoingEnabled;
   }

   public boolean isBoundOutgoingEnabledInherited() {
      return false;
   }

   public boolean isBoundOutgoingEnabledSet() {
      return this._isSet(24);
   }

   public void setBoundOutgoingEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._BoundOutgoingEnabled;
      this._BoundOutgoingEnabled = param0;
      this._postSet(24, _oldVal, param0);
   }

   public int getChannelWeight() {
      return this._ChannelWeight;
   }

   public boolean isChannelWeightInherited() {
      return false;
   }

   public boolean isChannelWeightSet() {
      return this._isSet(25);
   }

   public void setChannelWeight(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("ChannelWeight", (long)param0, 1L, 100L);
      int _oldVal = this._ChannelWeight;
      this._ChannelWeight = param0;
      this._postSet(25, _oldVal, param0);
   }

   public int getAcceptBacklog() {
      return this._AcceptBacklog;
   }

   public boolean isAcceptBacklogInherited() {
      return false;
   }

   public boolean isAcceptBacklogSet() {
      return this._isSet(26);
   }

   public void setAcceptBacklog(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("AcceptBacklog", param0, 0);
      int _oldVal = this._AcceptBacklog;
      this._AcceptBacklog = param0;
      this._postSet(26, _oldVal, param0);
   }

   public int getLoginTimeoutMillis() {
      return this._LoginTimeoutMillis;
   }

   public boolean isLoginTimeoutMillisInherited() {
      return false;
   }

   public boolean isLoginTimeoutMillisSet() {
      return this._isSet(27);
   }

   public void setLoginTimeoutMillis(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("LoginTimeoutMillis", (long)param0, 0L, 100000L);
      int _oldVal = this._LoginTimeoutMillis;
      this._LoginTimeoutMillis = param0;
      this._postSet(27, _oldVal, param0);
   }

   public int getLoginTimeoutMillisSSL() {
      return this._LoginTimeoutMillisSSL;
   }

   public boolean isLoginTimeoutMillisSSLInherited() {
      return false;
   }

   public boolean isLoginTimeoutMillisSSLSet() {
      return this._isSet(28);
   }

   public void setLoginTimeoutMillisSSL(int param0) {
      LegalChecks.checkInRange("LoginTimeoutMillisSSL", (long)param0, 0L, 2147483647L);
      int _oldVal = this._LoginTimeoutMillisSSL;
      this._LoginTimeoutMillisSSL = param0;
      this._postSet(28, _oldVal, param0);
   }

   public boolean isTunnelingEnabled() {
      return this._TunnelingEnabled;
   }

   public boolean isTunnelingEnabledInherited() {
      return false;
   }

   public boolean isTunnelingEnabledSet() {
      return this._isSet(29);
   }

   public void setTunnelingEnabled(boolean param0) throws DistributedManagementException {
      boolean _oldVal = this._TunnelingEnabled;
      this._TunnelingEnabled = param0;
      this._postSet(29, _oldVal, param0);
   }

   public int getTunnelingClientPingSecs() {
      return this._TunnelingClientPingSecs;
   }

   public boolean isTunnelingClientPingSecsInherited() {
      return false;
   }

   public boolean isTunnelingClientPingSecsSet() {
      return this._isSet(30);
   }

   public void setTunnelingClientPingSecs(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("TunnelingClientPingSecs", param0, 1);
      int _oldVal = this._TunnelingClientPingSecs;
      this._TunnelingClientPingSecs = param0;
      this._postSet(30, _oldVal, param0);
   }

   public int getTunnelingClientTimeoutSecs() {
      return this._TunnelingClientTimeoutSecs;
   }

   public boolean isTunnelingClientTimeoutSecsInherited() {
      return false;
   }

   public boolean isTunnelingClientTimeoutSecsSet() {
      return this._isSet(31);
   }

   public void setTunnelingClientTimeoutSecs(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("TunnelingClientTimeoutSecs", param0, 1);
      int _oldVal = this._TunnelingClientTimeoutSecs;
      this._TunnelingClientTimeoutSecs = param0;
      this._postSet(31, _oldVal, param0);
   }

   public int getCompleteT3MessageTimeout() {
      return this._CompleteT3MessageTimeout;
   }

   public boolean isCompleteT3MessageTimeoutInherited() {
      return false;
   }

   public boolean isCompleteT3MessageTimeoutSet() {
      return this._isSet(32);
   }

   public void setCompleteT3MessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CompleteT3MessageTimeout", (long)param0, 0L, 480L);
      int _oldVal = this._CompleteT3MessageTimeout;
      this._CompleteT3MessageTimeout = param0;
      this._postSet(32, _oldVal, param0);
   }

   public int getCompleteHTTPMessageTimeout() {
      return this._CompleteHTTPMessageTimeout;
   }

   public boolean isCompleteHTTPMessageTimeoutInherited() {
      return false;
   }

   public boolean isCompleteHTTPMessageTimeoutSet() {
      return this._isSet(33);
   }

   public void setCompleteHTTPMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CompleteHTTPMessageTimeout", (long)param0, 0L, 480L);
      int _oldVal = this._CompleteHTTPMessageTimeout;
      this._CompleteHTTPMessageTimeout = param0;
      this._postSet(33, _oldVal, param0);
   }

   public int getCompleteCOMMessageTimeout() {
      return this._CompleteCOMMessageTimeout;
   }

   public boolean isCompleteCOMMessageTimeoutInherited() {
      return false;
   }

   public boolean isCompleteCOMMessageTimeoutSet() {
      return this._isSet(34);
   }

   public void setCompleteCOMMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CompleteCOMMessageTimeout", (long)param0, 0L, 480L);
      int _oldVal = this._CompleteCOMMessageTimeout;
      this._CompleteCOMMessageTimeout = param0;
      this._postSet(34, _oldVal, param0);
   }

   public int getMaxT3MessageSize() {
      return this._MaxT3MessageSize;
   }

   public boolean isMaxT3MessageSizeInherited() {
      return false;
   }

   public boolean isMaxT3MessageSizeSet() {
      return this._isSet(35);
   }

   public void setMaxT3MessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxT3MessageSize", (long)param0, 4096L, 2000000000L);
      int _oldVal = this._MaxT3MessageSize;
      this._MaxT3MessageSize = param0;
      this._postSet(35, _oldVal, param0);
   }

   public int getMaxHTTPMessageSize() {
      return this._MaxHTTPMessageSize;
   }

   public boolean isMaxHTTPMessageSizeInherited() {
      return false;
   }

   public boolean isMaxHTTPMessageSizeSet() {
      return this._isSet(36);
   }

   public void setMaxHTTPMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxHTTPMessageSize", (long)param0, 4096L, 2000000000L);
      int _oldVal = this._MaxHTTPMessageSize;
      this._MaxHTTPMessageSize = param0;
      this._postSet(36, _oldVal, param0);
   }

   public int getMaxCOMMessageSize() {
      return this._MaxCOMMessageSize;
   }

   public boolean isMaxCOMMessageSizeInherited() {
      return false;
   }

   public boolean isMaxCOMMessageSizeSet() {
      return this._isSet(37);
   }

   public void setMaxCOMMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxCOMMessageSize", (long)param0, 4096L, 2000000000L);
      int _oldVal = this._MaxCOMMessageSize;
      this._MaxCOMMessageSize = param0;
      this._postSet(37, _oldVal, param0);
   }

   public boolean isIIOPEnabled() {
      return this._IIOPEnabled;
   }

   public boolean isIIOPEnabledInherited() {
      return false;
   }

   public boolean isIIOPEnabledSet() {
      return this._isSet(38);
   }

   public void setIIOPEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._IIOPEnabled;
      this._IIOPEnabled = param0;
      this._postSet(38, _oldVal, param0);
   }

   public boolean isIIOPSEnabled() {
      return this._IIOPSEnabled;
   }

   public boolean isIIOPSEnabledInherited() {
      return false;
   }

   public boolean isIIOPSEnabledSet() {
      return this._isSet(39);
   }

   public void setIIOPSEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._IIOPSEnabled;
      this._IIOPSEnabled = param0;
      this._postSet(39, _oldVal, param0);
   }

   public int getCompleteIIOPMessageTimeout() {
      return this._CompleteIIOPMessageTimeout;
   }

   public boolean isCompleteIIOPMessageTimeoutInherited() {
      return false;
   }

   public boolean isCompleteIIOPMessageTimeoutSet() {
      return this._isSet(40);
   }

   public void setCompleteIIOPMessageTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("CompleteIIOPMessageTimeout", (long)param0, 0L, 480L);
      int _oldVal = this._CompleteIIOPMessageTimeout;
      this._CompleteIIOPMessageTimeout = param0;
      this._postSet(40, _oldVal, param0);
   }

   public int getMaxIIOPMessageSize() {
      return this._MaxIIOPMessageSize;
   }

   public boolean isMaxIIOPMessageSizeInherited() {
      return false;
   }

   public boolean isMaxIIOPMessageSizeSet() {
      return this._isSet(41);
   }

   public void setMaxIIOPMessageSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkInRange("MaxIIOPMessageSize", (long)param0, 4096L, 2000000000L);
      int _oldVal = this._MaxIIOPMessageSize;
      this._MaxIIOPMessageSize = param0;
      this._postSet(41, _oldVal, param0);
   }

   public int getIdleIIOPConnectionTimeout() {
      return this._IdleIIOPConnectionTimeout;
   }

   public boolean isIdleIIOPConnectionTimeoutInherited() {
      return false;
   }

   public boolean isIdleIIOPConnectionTimeoutSet() {
      return this._isSet(42);
   }

   public void setIdleIIOPConnectionTimeout(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkMin("IdleIIOPConnectionTimeout", param0, 0);
      int _oldVal = this._IdleIIOPConnectionTimeout;
      this._IdleIIOPConnectionTimeout = param0;
      this._postSet(42, _oldVal, param0);
   }

   public String getDefaultIIOPUser() {
      return this._DefaultIIOPUser;
   }

   public boolean isDefaultIIOPUserInherited() {
      return false;
   }

   public boolean isDefaultIIOPUserSet() {
      return this._isSet(43);
   }

   public void setDefaultIIOPUser(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DefaultIIOPUser;
      this._DefaultIIOPUser = param0;
      this._postSet(43, _oldVal, param0);
   }

   public String getDefaultIIOPPassword() {
      byte[] bEncrypted = this.getDefaultIIOPPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("DefaultIIOPPassword", bEncrypted);
   }

   public boolean isDefaultIIOPPasswordInherited() {
      return false;
   }

   public boolean isDefaultIIOPPasswordSet() {
      return this.isDefaultIIOPPasswordEncryptedSet();
   }

   public void setDefaultIIOPPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setDefaultIIOPPasswordEncrypted(param0 == null ? null : this._encrypt("DefaultIIOPPassword", param0));
   }

   public byte[] getDefaultIIOPPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._DefaultIIOPPasswordEncrypted);
   }

   public String getDefaultIIOPPasswordEncryptedAsString() {
      byte[] obj = this.getDefaultIIOPPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isDefaultIIOPPasswordEncryptedInherited() {
      return false;
   }

   public boolean isDefaultIIOPPasswordEncryptedSet() {
      return this._isSet(45);
   }

   public void setDefaultIIOPPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setDefaultIIOPPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setDefaultIIOPPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._DefaultIIOPPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: DefaultIIOPPasswordEncrypted of NetworkChannelMBean");
      } else {
         this._getHelper()._clearArray(this._DefaultIIOPPasswordEncrypted);
         this._DefaultIIOPPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(45, _oldVal, param0);
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
         if (idx == 44) {
            this._markSet(45, false);
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
         idx = 26;
      }

      try {
         switch (idx) {
            case 26:
               this._AcceptBacklog = 50;
               if (initOne) {
                  break;
               }
            case 25:
               this._ChannelWeight = 50;
               if (initOne) {
                  break;
               }
            case 17:
               this._ClusterAddress = null;
               if (initOne) {
                  break;
               }
            case 34:
               this._CompleteCOMMessageTimeout = 60;
               if (initOne) {
                  break;
               }
            case 33:
               this._CompleteHTTPMessageTimeout = 60;
               if (initOne) {
                  break;
               }
            case 40:
               this._CompleteIIOPMessageTimeout = 60;
               if (initOne) {
                  break;
               }
            case 32:
               this._CompleteT3MessageTimeout = 60;
               if (initOne) {
                  break;
               }
            case 44:
               this._DefaultIIOPPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 45:
               this._DefaultIIOPPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 43:
               this._DefaultIIOPUser = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 42:
               this._IdleIIOPConnectionTimeout = 60;
               if (initOne) {
                  break;
               }
            case 13:
               this._ListenPort = 8001;
               if (initOne) {
                  break;
               }
            case 27:
               this._LoginTimeoutMillis = 5000;
               if (initOne) {
                  break;
               }
            case 28:
               this._LoginTimeoutMillisSSL = 25000;
               if (initOne) {
                  break;
               }
            case 37:
               this._MaxCOMMessageSize = 10000000;
               if (initOne) {
                  break;
               }
            case 36:
               this._MaxHTTPMessageSize = 10000000;
               if (initOne) {
                  break;
               }
            case 41:
               this._MaxIIOPMessageSize = 10000000;
               if (initOne) {
                  break;
               }
            case 35:
               this._MaxT3MessageSize = 10000000;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._SSLListenPort = 8002;
               if (initOne) {
                  break;
               }
            case 30:
               this._TunnelingClientPingSecs = 45;
               if (initOne) {
                  break;
               }
            case 31:
               this._TunnelingClientTimeoutSecs = 40;
               if (initOne) {
                  break;
               }
            case 24:
               this._BoundOutgoingEnabled = false;
               if (initOne) {
                  break;
               }
            case 22:
               this._COMEnabled = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._HTTPEnabled = false;
               if (initOne) {
                  break;
               }
            case 21:
               this._HTTPSEnabled = false;
               if (initOne) {
                  break;
               }
            case 38:
               this._IIOPEnabled = false;
               if (initOne) {
                  break;
               }
            case 39:
               this._IIOPSEnabled = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._ListenPortEnabled = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._OutgoingEnabled = false;
               if (initOne) {
                  break;
               }
            case 16:
               this._SSLListenPortEnabled = false;
               if (initOne) {
                  break;
               }
            case 18:
               this._T3Enabled = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._T3SEnabled = false;
               if (initOne) {
                  break;
               }
            case 29:
               this._TunnelingEnabled = false;
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
      return "NetworkChannel";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("AcceptBacklog")) {
         oldVal = this._AcceptBacklog;
         this._AcceptBacklog = (Integer)v;
         this._postSet(26, oldVal, this._AcceptBacklog);
      } else {
         boolean oldVal;
         if (name.equals("BoundOutgoingEnabled")) {
            oldVal = this._BoundOutgoingEnabled;
            this._BoundOutgoingEnabled = (Boolean)v;
            this._postSet(24, oldVal, this._BoundOutgoingEnabled);
         } else if (name.equals("COMEnabled")) {
            oldVal = this._COMEnabled;
            this._COMEnabled = (Boolean)v;
            this._postSet(22, oldVal, this._COMEnabled);
         } else if (name.equals("ChannelWeight")) {
            oldVal = this._ChannelWeight;
            this._ChannelWeight = (Integer)v;
            this._postSet(25, oldVal, this._ChannelWeight);
         } else {
            String oldVal;
            if (name.equals("ClusterAddress")) {
               oldVal = this._ClusterAddress;
               this._ClusterAddress = (String)v;
               this._postSet(17, oldVal, this._ClusterAddress);
            } else if (name.equals("CompleteCOMMessageTimeout")) {
               oldVal = this._CompleteCOMMessageTimeout;
               this._CompleteCOMMessageTimeout = (Integer)v;
               this._postSet(34, oldVal, this._CompleteCOMMessageTimeout);
            } else if (name.equals("CompleteHTTPMessageTimeout")) {
               oldVal = this._CompleteHTTPMessageTimeout;
               this._CompleteHTTPMessageTimeout = (Integer)v;
               this._postSet(33, oldVal, this._CompleteHTTPMessageTimeout);
            } else if (name.equals("CompleteIIOPMessageTimeout")) {
               oldVal = this._CompleteIIOPMessageTimeout;
               this._CompleteIIOPMessageTimeout = (Integer)v;
               this._postSet(40, oldVal, this._CompleteIIOPMessageTimeout);
            } else if (name.equals("CompleteT3MessageTimeout")) {
               oldVal = this._CompleteT3MessageTimeout;
               this._CompleteT3MessageTimeout = (Integer)v;
               this._postSet(32, oldVal, this._CompleteT3MessageTimeout);
            } else if (name.equals("DefaultIIOPPassword")) {
               oldVal = this._DefaultIIOPPassword;
               this._DefaultIIOPPassword = (String)v;
               this._postSet(44, oldVal, this._DefaultIIOPPassword);
            } else if (name.equals("DefaultIIOPPasswordEncrypted")) {
               byte[] oldVal = this._DefaultIIOPPasswordEncrypted;
               this._DefaultIIOPPasswordEncrypted = (byte[])((byte[])v);
               this._postSet(45, oldVal, this._DefaultIIOPPasswordEncrypted);
            } else if (name.equals("DefaultIIOPUser")) {
               oldVal = this._DefaultIIOPUser;
               this._DefaultIIOPUser = (String)v;
               this._postSet(43, oldVal, this._DefaultIIOPUser);
            } else if (name.equals("Description")) {
               oldVal = this._Description;
               this._Description = (String)v;
               this._postSet(12, oldVal, this._Description);
            } else if (name.equals("HTTPEnabled")) {
               oldVal = this._HTTPEnabled;
               this._HTTPEnabled = (Boolean)v;
               this._postSet(20, oldVal, this._HTTPEnabled);
            } else if (name.equals("HTTPSEnabled")) {
               oldVal = this._HTTPSEnabled;
               this._HTTPSEnabled = (Boolean)v;
               this._postSet(21, oldVal, this._HTTPSEnabled);
            } else if (name.equals("IIOPEnabled")) {
               oldVal = this._IIOPEnabled;
               this._IIOPEnabled = (Boolean)v;
               this._postSet(38, oldVal, this._IIOPEnabled);
            } else if (name.equals("IIOPSEnabled")) {
               oldVal = this._IIOPSEnabled;
               this._IIOPSEnabled = (Boolean)v;
               this._postSet(39, oldVal, this._IIOPSEnabled);
            } else if (name.equals("IdleIIOPConnectionTimeout")) {
               oldVal = this._IdleIIOPConnectionTimeout;
               this._IdleIIOPConnectionTimeout = (Integer)v;
               this._postSet(42, oldVal, this._IdleIIOPConnectionTimeout);
            } else if (name.equals("ListenPort")) {
               oldVal = this._ListenPort;
               this._ListenPort = (Integer)v;
               this._postSet(13, oldVal, this._ListenPort);
            } else if (name.equals("ListenPortEnabled")) {
               oldVal = this._ListenPortEnabled;
               this._ListenPortEnabled = (Boolean)v;
               this._postSet(14, oldVal, this._ListenPortEnabled);
            } else if (name.equals("LoginTimeoutMillis")) {
               oldVal = this._LoginTimeoutMillis;
               this._LoginTimeoutMillis = (Integer)v;
               this._postSet(27, oldVal, this._LoginTimeoutMillis);
            } else if (name.equals("LoginTimeoutMillisSSL")) {
               oldVal = this._LoginTimeoutMillisSSL;
               this._LoginTimeoutMillisSSL = (Integer)v;
               this._postSet(28, oldVal, this._LoginTimeoutMillisSSL);
            } else if (name.equals("MaxCOMMessageSize")) {
               oldVal = this._MaxCOMMessageSize;
               this._MaxCOMMessageSize = (Integer)v;
               this._postSet(37, oldVal, this._MaxCOMMessageSize);
            } else if (name.equals("MaxHTTPMessageSize")) {
               oldVal = this._MaxHTTPMessageSize;
               this._MaxHTTPMessageSize = (Integer)v;
               this._postSet(36, oldVal, this._MaxHTTPMessageSize);
            } else if (name.equals("MaxIIOPMessageSize")) {
               oldVal = this._MaxIIOPMessageSize;
               this._MaxIIOPMessageSize = (Integer)v;
               this._postSet(41, oldVal, this._MaxIIOPMessageSize);
            } else if (name.equals("MaxT3MessageSize")) {
               oldVal = this._MaxT3MessageSize;
               this._MaxT3MessageSize = (Integer)v;
               this._postSet(35, oldVal, this._MaxT3MessageSize);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("OutgoingEnabled")) {
               oldVal = this._OutgoingEnabled;
               this._OutgoingEnabled = (Boolean)v;
               this._postSet(23, oldVal, this._OutgoingEnabled);
            } else if (name.equals("SSLListenPort")) {
               oldVal = this._SSLListenPort;
               this._SSLListenPort = (Integer)v;
               this._postSet(15, oldVal, this._SSLListenPort);
            } else if (name.equals("SSLListenPortEnabled")) {
               oldVal = this._SSLListenPortEnabled;
               this._SSLListenPortEnabled = (Boolean)v;
               this._postSet(16, oldVal, this._SSLListenPortEnabled);
            } else if (name.equals("T3Enabled")) {
               oldVal = this._T3Enabled;
               this._T3Enabled = (Boolean)v;
               this._postSet(18, oldVal, this._T3Enabled);
            } else if (name.equals("T3SEnabled")) {
               oldVal = this._T3SEnabled;
               this._T3SEnabled = (Boolean)v;
               this._postSet(19, oldVal, this._T3SEnabled);
            } else if (name.equals("TunnelingClientPingSecs")) {
               oldVal = this._TunnelingClientPingSecs;
               this._TunnelingClientPingSecs = (Integer)v;
               this._postSet(30, oldVal, this._TunnelingClientPingSecs);
            } else if (name.equals("TunnelingClientTimeoutSecs")) {
               oldVal = this._TunnelingClientTimeoutSecs;
               this._TunnelingClientTimeoutSecs = (Integer)v;
               this._postSet(31, oldVal, this._TunnelingClientTimeoutSecs);
            } else if (name.equals("TunnelingEnabled")) {
               oldVal = this._TunnelingEnabled;
               this._TunnelingEnabled = (Boolean)v;
               this._postSet(29, oldVal, this._TunnelingEnabled);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AcceptBacklog")) {
         return new Integer(this._AcceptBacklog);
      } else if (name.equals("BoundOutgoingEnabled")) {
         return new Boolean(this._BoundOutgoingEnabled);
      } else if (name.equals("COMEnabled")) {
         return new Boolean(this._COMEnabled);
      } else if (name.equals("ChannelWeight")) {
         return new Integer(this._ChannelWeight);
      } else if (name.equals("ClusterAddress")) {
         return this._ClusterAddress;
      } else if (name.equals("CompleteCOMMessageTimeout")) {
         return new Integer(this._CompleteCOMMessageTimeout);
      } else if (name.equals("CompleteHTTPMessageTimeout")) {
         return new Integer(this._CompleteHTTPMessageTimeout);
      } else if (name.equals("CompleteIIOPMessageTimeout")) {
         return new Integer(this._CompleteIIOPMessageTimeout);
      } else if (name.equals("CompleteT3MessageTimeout")) {
         return new Integer(this._CompleteT3MessageTimeout);
      } else if (name.equals("DefaultIIOPPassword")) {
         return this._DefaultIIOPPassword;
      } else if (name.equals("DefaultIIOPPasswordEncrypted")) {
         return this._DefaultIIOPPasswordEncrypted;
      } else if (name.equals("DefaultIIOPUser")) {
         return this._DefaultIIOPUser;
      } else if (name.equals("Description")) {
         return this._Description;
      } else if (name.equals("HTTPEnabled")) {
         return new Boolean(this._HTTPEnabled);
      } else if (name.equals("HTTPSEnabled")) {
         return new Boolean(this._HTTPSEnabled);
      } else if (name.equals("IIOPEnabled")) {
         return new Boolean(this._IIOPEnabled);
      } else if (name.equals("IIOPSEnabled")) {
         return new Boolean(this._IIOPSEnabled);
      } else if (name.equals("IdleIIOPConnectionTimeout")) {
         return new Integer(this._IdleIIOPConnectionTimeout);
      } else if (name.equals("ListenPort")) {
         return new Integer(this._ListenPort);
      } else if (name.equals("ListenPortEnabled")) {
         return new Boolean(this._ListenPortEnabled);
      } else if (name.equals("LoginTimeoutMillis")) {
         return new Integer(this._LoginTimeoutMillis);
      } else if (name.equals("LoginTimeoutMillisSSL")) {
         return new Integer(this._LoginTimeoutMillisSSL);
      } else if (name.equals("MaxCOMMessageSize")) {
         return new Integer(this._MaxCOMMessageSize);
      } else if (name.equals("MaxHTTPMessageSize")) {
         return new Integer(this._MaxHTTPMessageSize);
      } else if (name.equals("MaxIIOPMessageSize")) {
         return new Integer(this._MaxIIOPMessageSize);
      } else if (name.equals("MaxT3MessageSize")) {
         return new Integer(this._MaxT3MessageSize);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OutgoingEnabled")) {
         return new Boolean(this._OutgoingEnabled);
      } else if (name.equals("SSLListenPort")) {
         return new Integer(this._SSLListenPort);
      } else if (name.equals("SSLListenPortEnabled")) {
         return new Boolean(this._SSLListenPortEnabled);
      } else if (name.equals("T3Enabled")) {
         return new Boolean(this._T3Enabled);
      } else if (name.equals("T3SEnabled")) {
         return new Boolean(this._T3SEnabled);
      } else if (name.equals("TunnelingClientPingSecs")) {
         return new Integer(this._TunnelingClientPingSecs);
      } else if (name.equals("TunnelingClientTimeoutSecs")) {
         return new Integer(this._TunnelingClientTimeoutSecs);
      } else {
         return name.equals("TunnelingEnabled") ? new Boolean(this._TunnelingEnabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 21:
            case 24:
            case 25:
            default:
               break;
            case 10:
               if (s.equals("t3-enabled")) {
                  return 18;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 12;
               }

               if (s.equals("listen-port")) {
                  return 13;
               }

               if (s.equals("com-enabled")) {
                  return 22;
               }

               if (s.equals("t3s-enabled")) {
                  return 19;
               }
               break;
            case 12:
               if (s.equals("http-enabled")) {
                  return 20;
               }

               if (s.equals("iiop-enabled")) {
                  return 38;
               }
               break;
            case 13:
               if (s.equals("https-enabled")) {
                  return 21;
               }

               if (s.equals("iiops-enabled")) {
                  return 39;
               }
               break;
            case 14:
               if (s.equals("accept-backlog")) {
                  return 26;
               }

               if (s.equals("channel-weight")) {
                  return 25;
               }
               break;
            case 15:
               if (s.equals("cluster-address")) {
                  return 17;
               }

               if (s.equals("ssl-listen-port")) {
                  return 15;
               }
               break;
            case 16:
               if (s.equals("defaultiiop-user")) {
                  return 43;
               }

               if (s.equals("outgoing-enabled")) {
                  return 23;
               }
               break;
            case 17:
               if (s.equals("tunneling-enabled")) {
                  return 29;
               }
               break;
            case 18:
               if (s.equals("maxt3-message-size")) {
                  return 35;
               }
               break;
            case 19:
               if (s.equals("maxcom-message-size")) {
                  return 37;
               }

               if (s.equals("listen-port-enabled")) {
                  return 14;
               }
               break;
            case 20:
               if (s.equals("defaultiiop-password")) {
                  return 44;
               }

               if (s.equals("login-timeout-millis")) {
                  return 27;
               }

               if (s.equals("maxhttp-message-size")) {
                  return 36;
               }

               if (s.equals("maxiiop-message-size")) {
                  return 41;
               }
               break;
            case 22:
               if (s.equals("bound-outgoing-enabled")) {
                  return 24;
               }
               break;
            case 23:
               if (s.equals("login-timeout-millisssl")) {
                  return 28;
               }

               if (s.equals("ssl-listen-port-enabled")) {
                  return 16;
               }
               break;
            case 26:
               if (s.equals("completet3-message-timeout")) {
                  return 32;
               }

               if (s.equals("tunneling-client-ping-secs")) {
                  return 30;
               }
               break;
            case 27:
               if (s.equals("completecom-message-timeout")) {
                  return 34;
               }

               if (s.equals("idleiiop-connection-timeout")) {
                  return 42;
               }
               break;
            case 28:
               if (s.equals("completehttp-message-timeout")) {
                  return 33;
               }

               if (s.equals("completeiiop-message-timeout")) {
                  return 40;
               }
               break;
            case 29:
               if (s.equals("tunneling-client-timeout-secs")) {
                  return 31;
               }
               break;
            case 30:
               if (s.equals("defaultiiop-password-encrypted")) {
                  return 45;
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
            case 10:
            case 11:
            default:
               return super.getElementName(propIndex);
            case 12:
               return "description";
            case 13:
               return "listen-port";
            case 14:
               return "listen-port-enabled";
            case 15:
               return "ssl-listen-port";
            case 16:
               return "ssl-listen-port-enabled";
            case 17:
               return "cluster-address";
            case 18:
               return "t3-enabled";
            case 19:
               return "t3s-enabled";
            case 20:
               return "http-enabled";
            case 21:
               return "https-enabled";
            case 22:
               return "com-enabled";
            case 23:
               return "outgoing-enabled";
            case 24:
               return "bound-outgoing-enabled";
            case 25:
               return "channel-weight";
            case 26:
               return "accept-backlog";
            case 27:
               return "login-timeout-millis";
            case 28:
               return "login-timeout-millisssl";
            case 29:
               return "tunneling-enabled";
            case 30:
               return "tunneling-client-ping-secs";
            case 31:
               return "tunneling-client-timeout-secs";
            case 32:
               return "completet3-message-timeout";
            case 33:
               return "completehttp-message-timeout";
            case 34:
               return "completecom-message-timeout";
            case 35:
               return "maxt3-message-size";
            case 36:
               return "maxhttp-message-size";
            case 37:
               return "maxcom-message-size";
            case 38:
               return "iiop-enabled";
            case 39:
               return "iiops-enabled";
            case 40:
               return "completeiiop-message-timeout";
            case 41:
               return "maxiiop-message-size";
            case 42:
               return "idleiiop-connection-timeout";
            case 43:
               return "defaultiiop-user";
            case 44:
               return "defaultiiop-password";
            case 45:
               return "defaultiiop-password-encrypted";
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

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 28:
               return true;
            case 29:
            case 30:
            case 31:
            case 38:
            case 39:
            default:
               return super.isConfigurable(propIndex);
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 35:
               return true;
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
      private NetworkChannelMBeanImpl bean;

      protected Helper(NetworkChannelMBeanImpl bean) {
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
            case 10:
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 12:
               return "Description";
            case 13:
               return "ListenPort";
            case 14:
               return "ListenPortEnabled";
            case 15:
               return "SSLListenPort";
            case 16:
               return "SSLListenPortEnabled";
            case 17:
               return "ClusterAddress";
            case 18:
               return "T3Enabled";
            case 19:
               return "T3SEnabled";
            case 20:
               return "HTTPEnabled";
            case 21:
               return "HTTPSEnabled";
            case 22:
               return "COMEnabled";
            case 23:
               return "OutgoingEnabled";
            case 24:
               return "BoundOutgoingEnabled";
            case 25:
               return "ChannelWeight";
            case 26:
               return "AcceptBacklog";
            case 27:
               return "LoginTimeoutMillis";
            case 28:
               return "LoginTimeoutMillisSSL";
            case 29:
               return "TunnelingEnabled";
            case 30:
               return "TunnelingClientPingSecs";
            case 31:
               return "TunnelingClientTimeoutSecs";
            case 32:
               return "CompleteT3MessageTimeout";
            case 33:
               return "CompleteHTTPMessageTimeout";
            case 34:
               return "CompleteCOMMessageTimeout";
            case 35:
               return "MaxT3MessageSize";
            case 36:
               return "MaxHTTPMessageSize";
            case 37:
               return "MaxCOMMessageSize";
            case 38:
               return "IIOPEnabled";
            case 39:
               return "IIOPSEnabled";
            case 40:
               return "CompleteIIOPMessageTimeout";
            case 41:
               return "MaxIIOPMessageSize";
            case 42:
               return "IdleIIOPConnectionTimeout";
            case 43:
               return "DefaultIIOPUser";
            case 44:
               return "DefaultIIOPPassword";
            case 45:
               return "DefaultIIOPPasswordEncrypted";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AcceptBacklog")) {
            return 26;
         } else if (propName.equals("ChannelWeight")) {
            return 25;
         } else if (propName.equals("ClusterAddress")) {
            return 17;
         } else if (propName.equals("CompleteCOMMessageTimeout")) {
            return 34;
         } else if (propName.equals("CompleteHTTPMessageTimeout")) {
            return 33;
         } else if (propName.equals("CompleteIIOPMessageTimeout")) {
            return 40;
         } else if (propName.equals("CompleteT3MessageTimeout")) {
            return 32;
         } else if (propName.equals("DefaultIIOPPassword")) {
            return 44;
         } else if (propName.equals("DefaultIIOPPasswordEncrypted")) {
            return 45;
         } else if (propName.equals("DefaultIIOPUser")) {
            return 43;
         } else if (propName.equals("Description")) {
            return 12;
         } else if (propName.equals("IdleIIOPConnectionTimeout")) {
            return 42;
         } else if (propName.equals("ListenPort")) {
            return 13;
         } else if (propName.equals("LoginTimeoutMillis")) {
            return 27;
         } else if (propName.equals("LoginTimeoutMillisSSL")) {
            return 28;
         } else if (propName.equals("MaxCOMMessageSize")) {
            return 37;
         } else if (propName.equals("MaxHTTPMessageSize")) {
            return 36;
         } else if (propName.equals("MaxIIOPMessageSize")) {
            return 41;
         } else if (propName.equals("MaxT3MessageSize")) {
            return 35;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("SSLListenPort")) {
            return 15;
         } else if (propName.equals("TunnelingClientPingSecs")) {
            return 30;
         } else if (propName.equals("TunnelingClientTimeoutSecs")) {
            return 31;
         } else if (propName.equals("BoundOutgoingEnabled")) {
            return 24;
         } else if (propName.equals("COMEnabled")) {
            return 22;
         } else if (propName.equals("HTTPEnabled")) {
            return 20;
         } else if (propName.equals("HTTPSEnabled")) {
            return 21;
         } else if (propName.equals("IIOPEnabled")) {
            return 38;
         } else if (propName.equals("IIOPSEnabled")) {
            return 39;
         } else if (propName.equals("ListenPortEnabled")) {
            return 14;
         } else if (propName.equals("OutgoingEnabled")) {
            return 23;
         } else if (propName.equals("SSLListenPortEnabled")) {
            return 16;
         } else if (propName.equals("T3Enabled")) {
            return 18;
         } else if (propName.equals("T3SEnabled")) {
            return 19;
         } else {
            return propName.equals("TunnelingEnabled") ? 29 : super.getPropertyIndex(propName);
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

            if (this.bean.isCompleteT3MessageTimeoutSet()) {
               buf.append("CompleteT3MessageTimeout");
               buf.append(String.valueOf(this.bean.getCompleteT3MessageTimeout()));
            }

            if (this.bean.isDefaultIIOPPasswordSet()) {
               buf.append("DefaultIIOPPassword");
               buf.append(String.valueOf(this.bean.getDefaultIIOPPassword()));
            }

            if (this.bean.isDefaultIIOPPasswordEncryptedSet()) {
               buf.append("DefaultIIOPPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDefaultIIOPPasswordEncrypted())));
            }

            if (this.bean.isDefaultIIOPUserSet()) {
               buf.append("DefaultIIOPUser");
               buf.append(String.valueOf(this.bean.getDefaultIIOPUser()));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isIdleIIOPConnectionTimeoutSet()) {
               buf.append("IdleIIOPConnectionTimeout");
               buf.append(String.valueOf(this.bean.getIdleIIOPConnectionTimeout()));
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

            if (this.bean.isMaxT3MessageSizeSet()) {
               buf.append("MaxT3MessageSize");
               buf.append(String.valueOf(this.bean.getMaxT3MessageSize()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isSSLListenPortSet()) {
               buf.append("SSLListenPort");
               buf.append(String.valueOf(this.bean.getSSLListenPort()));
            }

            if (this.bean.isTunnelingClientPingSecsSet()) {
               buf.append("TunnelingClientPingSecs");
               buf.append(String.valueOf(this.bean.getTunnelingClientPingSecs()));
            }

            if (this.bean.isTunnelingClientTimeoutSecsSet()) {
               buf.append("TunnelingClientTimeoutSecs");
               buf.append(String.valueOf(this.bean.getTunnelingClientTimeoutSecs()));
            }

            if (this.bean.isBoundOutgoingEnabledSet()) {
               buf.append("BoundOutgoingEnabled");
               buf.append(String.valueOf(this.bean.isBoundOutgoingEnabled()));
            }

            if (this.bean.isCOMEnabledSet()) {
               buf.append("COMEnabled");
               buf.append(String.valueOf(this.bean.isCOMEnabled()));
            }

            if (this.bean.isHTTPEnabledSet()) {
               buf.append("HTTPEnabled");
               buf.append(String.valueOf(this.bean.isHTTPEnabled()));
            }

            if (this.bean.isHTTPSEnabledSet()) {
               buf.append("HTTPSEnabled");
               buf.append(String.valueOf(this.bean.isHTTPSEnabled()));
            }

            if (this.bean.isIIOPEnabledSet()) {
               buf.append("IIOPEnabled");
               buf.append(String.valueOf(this.bean.isIIOPEnabled()));
            }

            if (this.bean.isIIOPSEnabledSet()) {
               buf.append("IIOPSEnabled");
               buf.append(String.valueOf(this.bean.isIIOPSEnabled()));
            }

            if (this.bean.isListenPortEnabledSet()) {
               buf.append("ListenPortEnabled");
               buf.append(String.valueOf(this.bean.isListenPortEnabled()));
            }

            if (this.bean.isOutgoingEnabledSet()) {
               buf.append("OutgoingEnabled");
               buf.append(String.valueOf(this.bean.isOutgoingEnabled()));
            }

            if (this.bean.isSSLListenPortEnabledSet()) {
               buf.append("SSLListenPortEnabled");
               buf.append(String.valueOf(this.bean.isSSLListenPortEnabled()));
            }

            if (this.bean.isT3EnabledSet()) {
               buf.append("T3Enabled");
               buf.append(String.valueOf(this.bean.isT3Enabled()));
            }

            if (this.bean.isT3SEnabledSet()) {
               buf.append("T3SEnabled");
               buf.append(String.valueOf(this.bean.isT3SEnabled()));
            }

            if (this.bean.isTunnelingEnabledSet()) {
               buf.append("TunnelingEnabled");
               buf.append(String.valueOf(this.bean.isTunnelingEnabled()));
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
            NetworkChannelMBeanImpl otherTyped = (NetworkChannelMBeanImpl)other;
            this.computeDiff("AcceptBacklog", this.bean.getAcceptBacklog(), otherTyped.getAcceptBacklog(), false);
            this.computeDiff("ChannelWeight", this.bean.getChannelWeight(), otherTyped.getChannelWeight(), false);
            this.computeDiff("ClusterAddress", this.bean.getClusterAddress(), otherTyped.getClusterAddress(), false);
            this.computeDiff("CompleteCOMMessageTimeout", this.bean.getCompleteCOMMessageTimeout(), otherTyped.getCompleteCOMMessageTimeout(), true);
            this.computeDiff("CompleteHTTPMessageTimeout", this.bean.getCompleteHTTPMessageTimeout(), otherTyped.getCompleteHTTPMessageTimeout(), true);
            this.computeDiff("CompleteIIOPMessageTimeout", this.bean.getCompleteIIOPMessageTimeout(), otherTyped.getCompleteIIOPMessageTimeout(), true);
            this.computeDiff("CompleteT3MessageTimeout", this.bean.getCompleteT3MessageTimeout(), otherTyped.getCompleteT3MessageTimeout(), true);
            this.computeDiff("DefaultIIOPPasswordEncrypted", this.bean.getDefaultIIOPPasswordEncrypted(), otherTyped.getDefaultIIOPPasswordEncrypted(), false);
            this.computeDiff("DefaultIIOPUser", this.bean.getDefaultIIOPUser(), otherTyped.getDefaultIIOPUser(), false);
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("IdleIIOPConnectionTimeout", this.bean.getIdleIIOPConnectionTimeout(), otherTyped.getIdleIIOPConnectionTimeout(), true);
            this.computeDiff("ListenPort", this.bean.getListenPort(), otherTyped.getListenPort(), false);
            this.computeDiff("LoginTimeoutMillis", this.bean.getLoginTimeoutMillis(), otherTyped.getLoginTimeoutMillis(), true);
            this.computeDiff("LoginTimeoutMillisSSL", this.bean.getLoginTimeoutMillisSSL(), otherTyped.getLoginTimeoutMillisSSL(), true);
            this.computeDiff("MaxCOMMessageSize", this.bean.getMaxCOMMessageSize(), otherTyped.getMaxCOMMessageSize(), true);
            this.computeDiff("MaxHTTPMessageSize", this.bean.getMaxHTTPMessageSize(), otherTyped.getMaxHTTPMessageSize(), true);
            this.computeDiff("MaxIIOPMessageSize", this.bean.getMaxIIOPMessageSize(), otherTyped.getMaxIIOPMessageSize(), true);
            this.computeDiff("MaxT3MessageSize", this.bean.getMaxT3MessageSize(), otherTyped.getMaxT3MessageSize(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("SSLListenPort", this.bean.getSSLListenPort(), otherTyped.getSSLListenPort(), false);
            this.computeDiff("TunnelingClientPingSecs", this.bean.getTunnelingClientPingSecs(), otherTyped.getTunnelingClientPingSecs(), false);
            this.computeDiff("TunnelingClientTimeoutSecs", this.bean.getTunnelingClientTimeoutSecs(), otherTyped.getTunnelingClientTimeoutSecs(), false);
            this.computeDiff("BoundOutgoingEnabled", this.bean.isBoundOutgoingEnabled(), otherTyped.isBoundOutgoingEnabled(), false);
            this.computeDiff("COMEnabled", this.bean.isCOMEnabled(), otherTyped.isCOMEnabled(), false);
            this.computeDiff("HTTPEnabled", this.bean.isHTTPEnabled(), otherTyped.isHTTPEnabled(), false);
            this.computeDiff("HTTPSEnabled", this.bean.isHTTPSEnabled(), otherTyped.isHTTPSEnabled(), false);
            this.computeDiff("IIOPEnabled", this.bean.isIIOPEnabled(), otherTyped.isIIOPEnabled(), false);
            this.computeDiff("IIOPSEnabled", this.bean.isIIOPSEnabled(), otherTyped.isIIOPSEnabled(), false);
            this.computeDiff("ListenPortEnabled", this.bean.isListenPortEnabled(), otherTyped.isListenPortEnabled(), false);
            this.computeDiff("OutgoingEnabled", this.bean.isOutgoingEnabled(), otherTyped.isOutgoingEnabled(), false);
            this.computeDiff("SSLListenPortEnabled", this.bean.isSSLListenPortEnabled(), otherTyped.isSSLListenPortEnabled(), false);
            this.computeDiff("T3Enabled", this.bean.isT3Enabled(), otherTyped.isT3Enabled(), false);
            this.computeDiff("T3SEnabled", this.bean.isT3SEnabled(), otherTyped.isT3SEnabled(), false);
            this.computeDiff("TunnelingEnabled", this.bean.isTunnelingEnabled(), otherTyped.isTunnelingEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            NetworkChannelMBeanImpl original = (NetworkChannelMBeanImpl)event.getSourceBean();
            NetworkChannelMBeanImpl proposed = (NetworkChannelMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AcceptBacklog")) {
                  original.setAcceptBacklog(proposed.getAcceptBacklog());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("ChannelWeight")) {
                  original.setChannelWeight(proposed.getChannelWeight());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
               } else if (prop.equals("ClusterAddress")) {
                  original.setClusterAddress(proposed.getClusterAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("CompleteCOMMessageTimeout")) {
                  original.setCompleteCOMMessageTimeout(proposed.getCompleteCOMMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("CompleteHTTPMessageTimeout")) {
                  original.setCompleteHTTPMessageTimeout(proposed.getCompleteHTTPMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 33);
               } else if (prop.equals("CompleteIIOPMessageTimeout")) {
                  original.setCompleteIIOPMessageTimeout(proposed.getCompleteIIOPMessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 40);
               } else if (prop.equals("CompleteT3MessageTimeout")) {
                  original.setCompleteT3MessageTimeout(proposed.getCompleteT3MessageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 32);
               } else if (!prop.equals("DefaultIIOPPassword")) {
                  if (prop.equals("DefaultIIOPPasswordEncrypted")) {
                     original.setDefaultIIOPPasswordEncrypted(proposed.getDefaultIIOPPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 45);
                  } else if (prop.equals("DefaultIIOPUser")) {
                     original.setDefaultIIOPUser(proposed.getDefaultIIOPUser());
                     original._conditionalUnset(update.isUnsetUpdate(), 43);
                  } else if (prop.equals("Description")) {
                     original.setDescription(proposed.getDescription());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("IdleIIOPConnectionTimeout")) {
                     original.setIdleIIOPConnectionTimeout(proposed.getIdleIIOPConnectionTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 42);
                  } else if (prop.equals("ListenPort")) {
                     original.setListenPort(proposed.getListenPort());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("LoginTimeoutMillis")) {
                     original.setLoginTimeoutMillis(proposed.getLoginTimeoutMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  } else if (prop.equals("LoginTimeoutMillisSSL")) {
                     original.setLoginTimeoutMillisSSL(proposed.getLoginTimeoutMillisSSL());
                     original._conditionalUnset(update.isUnsetUpdate(), 28);
                  } else if (prop.equals("MaxCOMMessageSize")) {
                     original.setMaxCOMMessageSize(proposed.getMaxCOMMessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 37);
                  } else if (prop.equals("MaxHTTPMessageSize")) {
                     original.setMaxHTTPMessageSize(proposed.getMaxHTTPMessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 36);
                  } else if (prop.equals("MaxIIOPMessageSize")) {
                     original.setMaxIIOPMessageSize(proposed.getMaxIIOPMessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 41);
                  } else if (prop.equals("MaxT3MessageSize")) {
                     original.setMaxT3MessageSize(proposed.getMaxT3MessageSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 35);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("SSLListenPort")) {
                     original.setSSLListenPort(proposed.getSSLListenPort());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("TunnelingClientPingSecs")) {
                     original.setTunnelingClientPingSecs(proposed.getTunnelingClientPingSecs());
                     original._conditionalUnset(update.isUnsetUpdate(), 30);
                  } else if (prop.equals("TunnelingClientTimeoutSecs")) {
                     original.setTunnelingClientTimeoutSecs(proposed.getTunnelingClientTimeoutSecs());
                     original._conditionalUnset(update.isUnsetUpdate(), 31);
                  } else if (prop.equals("BoundOutgoingEnabled")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 24);
                  } else if (prop.equals("COMEnabled")) {
                     original.setCOMEnabled(proposed.isCOMEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 22);
                  } else if (prop.equals("HTTPEnabled")) {
                     original.setHTTPEnabled(proposed.isHTTPEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("HTTPSEnabled")) {
                     original.setHTTPSEnabled(proposed.isHTTPSEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("IIOPEnabled")) {
                     original.setIIOPEnabled(proposed.isIIOPEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 38);
                  } else if (prop.equals("IIOPSEnabled")) {
                     original.setIIOPSEnabled(proposed.isIIOPSEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 39);
                  } else if (prop.equals("ListenPortEnabled")) {
                     original.setListenPortEnabled(proposed.isListenPortEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("OutgoingEnabled")) {
                     original.setOutgoingEnabled(proposed.isOutgoingEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 23);
                  } else if (prop.equals("SSLListenPortEnabled")) {
                     original.setSSLListenPortEnabled(proposed.isSSLListenPortEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("T3Enabled")) {
                     original.setT3Enabled(proposed.isT3Enabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (prop.equals("T3SEnabled")) {
                     original.setT3SEnabled(proposed.isT3SEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("TunnelingEnabled")) {
                     original.setTunnelingEnabled(proposed.isTunnelingEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 29);
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
            NetworkChannelMBeanImpl copy = (NetworkChannelMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AcceptBacklog")) && this.bean.isAcceptBacklogSet()) {
               copy.setAcceptBacklog(this.bean.getAcceptBacklog());
            }

            if ((excludeProps == null || !excludeProps.contains("ChannelWeight")) && this.bean.isChannelWeightSet()) {
               copy.setChannelWeight(this.bean.getChannelWeight());
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

            if ((excludeProps == null || !excludeProps.contains("CompleteT3MessageTimeout")) && this.bean.isCompleteT3MessageTimeoutSet()) {
               copy.setCompleteT3MessageTimeout(this.bean.getCompleteT3MessageTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultIIOPPasswordEncrypted")) && this.bean.isDefaultIIOPPasswordEncryptedSet()) {
               Object o = this.bean.getDefaultIIOPPasswordEncrypted();
               copy.setDefaultIIOPPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultIIOPUser")) && this.bean.isDefaultIIOPUserSet()) {
               copy.setDefaultIIOPUser(this.bean.getDefaultIIOPUser());
            }

            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("IdleIIOPConnectionTimeout")) && this.bean.isIdleIIOPConnectionTimeoutSet()) {
               copy.setIdleIIOPConnectionTimeout(this.bean.getIdleIIOPConnectionTimeout());
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

            if ((excludeProps == null || !excludeProps.contains("MaxCOMMessageSize")) && this.bean.isMaxCOMMessageSizeSet()) {
               copy.setMaxCOMMessageSize(this.bean.getMaxCOMMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxHTTPMessageSize")) && this.bean.isMaxHTTPMessageSizeSet()) {
               copy.setMaxHTTPMessageSize(this.bean.getMaxHTTPMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxIIOPMessageSize")) && this.bean.isMaxIIOPMessageSizeSet()) {
               copy.setMaxIIOPMessageSize(this.bean.getMaxIIOPMessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxT3MessageSize")) && this.bean.isMaxT3MessageSizeSet()) {
               copy.setMaxT3MessageSize(this.bean.getMaxT3MessageSize());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLListenPort")) && this.bean.isSSLListenPortSet()) {
               copy.setSSLListenPort(this.bean.getSSLListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("TunnelingClientPingSecs")) && this.bean.isTunnelingClientPingSecsSet()) {
               copy.setTunnelingClientPingSecs(this.bean.getTunnelingClientPingSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("TunnelingClientTimeoutSecs")) && this.bean.isTunnelingClientTimeoutSecsSet()) {
               copy.setTunnelingClientTimeoutSecs(this.bean.getTunnelingClientTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("BoundOutgoingEnabled")) && this.bean.isBoundOutgoingEnabledSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("COMEnabled")) && this.bean.isCOMEnabledSet()) {
               copy.setCOMEnabled(this.bean.isCOMEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("HTTPEnabled")) && this.bean.isHTTPEnabledSet()) {
               copy.setHTTPEnabled(this.bean.isHTTPEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("HTTPSEnabled")) && this.bean.isHTTPSEnabledSet()) {
               copy.setHTTPSEnabled(this.bean.isHTTPSEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IIOPEnabled")) && this.bean.isIIOPEnabledSet()) {
               copy.setIIOPEnabled(this.bean.isIIOPEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IIOPSEnabled")) && this.bean.isIIOPSEnabledSet()) {
               copy.setIIOPSEnabled(this.bean.isIIOPSEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenPortEnabled")) && this.bean.isListenPortEnabledSet()) {
               copy.setListenPortEnabled(this.bean.isListenPortEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("OutgoingEnabled")) && this.bean.isOutgoingEnabledSet()) {
               copy.setOutgoingEnabled(this.bean.isOutgoingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLListenPortEnabled")) && this.bean.isSSLListenPortEnabledSet()) {
               copy.setSSLListenPortEnabled(this.bean.isSSLListenPortEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("T3Enabled")) && this.bean.isT3EnabledSet()) {
               copy.setT3Enabled(this.bean.isT3Enabled());
            }

            if ((excludeProps == null || !excludeProps.contains("T3SEnabled")) && this.bean.isT3SEnabledSet()) {
               copy.setT3SEnabled(this.bean.isT3SEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("TunnelingEnabled")) && this.bean.isTunnelingEnabledSet()) {
               copy.setTunnelingEnabled(this.bean.isTunnelingEnabled());
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

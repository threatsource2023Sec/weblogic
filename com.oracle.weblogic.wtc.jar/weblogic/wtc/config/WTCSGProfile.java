package weblogic.wtc.config;

import com.bea.core.jatmi.config.TuxedoConnectorSessionProfile;
import weblogic.wtc.gwt.TDMRemoteTDomain;

public class WTCSGProfile extends ConfigObjectBase implements TuxedoConnectorSessionProfile {
   private String _name;
   private String _cp;
   private String _aclp;
   private String _credp;
   private long _interval;
   private long _maxRetries;
   private int _cmpLimit;
   private int _minEncBits;
   private int _maxEncBits;
   private int _ka;
   private int _kaw;
   private String _security;

   public WTCSGProfile() {
      this.ctype = 4;
      this.mtype = 1;
   }

   public void setSessionProfileName(String name) {
      this._name = name;
   }

   public String getSessionProfileName() {
      return this._name;
   }

   public void setSecurity(String sec) {
      this._security = sec;
   }

   public String getSecurity() {
      return this._security;
   }

   public void setConnectionPolicy(String policy) {
      this._cp = policy;
   }

   public String getConnectionPolicy() {
      return this._cp;
   }

   public void setAclPolicy(String acl) {
      this._aclp = acl;
   }

   public String getAclPolicy() {
      return this._aclp;
   }

   public void setCredentialPolicy(String cred) {
      this._credp = cred;
   }

   public String getCredentialPolicy() {
      return this._credp;
   }

   public void setRetryInterval(long intvl) {
      this._interval = intvl;
   }

   public long getRetryInterval() {
      return this._interval;
   }

   public void setMaxRetries(long retries) {
      this._maxRetries = retries;
   }

   public long getMaxRetries() {
      return this._maxRetries;
   }

   public void setCmpLimit(int lim) {
      this._cmpLimit = lim;
   }

   public int getCmpLimit() {
      return this._cmpLimit;
   }

   public void setMinEncryptBits(int bits) {
      this._minEncBits = bits;
   }

   public int getMinEncryptBits() {
      return this._minEncBits;
   }

   public void setMaxEncryptBits(int bits) {
      this._maxEncBits = bits;
   }

   public int getMaxEncryptBits() {
      return this._maxEncBits;
   }

   public void setKeepAlive(int ka) {
      this._ka = ka;
   }

   public int getKeepAlive() {
      return this._ka;
   }

   public void setKeepAliveWait(int kaw) {
      this._kaw = kaw;
   }

   public int getKeepAliveWait() {
      return this._kaw;
   }

   public void fillFromSource(TDMRemoteTDomain rap) {
      this._name = new String("Prof-" + rap.getAccessPoint());
      this._cp = rap.getConnectionPolicy();
      this._aclp = rap.getAclPolicy();
      this._credp = rap.getCredentialPolicy();
      this._interval = rap.getRetryInterval();
      this._maxRetries = rap.getMaxRetries();
      this._cmpLimit = rap.getCmpLimit();
      this._minEncBits = rap.getMinEncryptBits();
      this._maxEncBits = rap.getMaxEncryptBits();
      this._ka = rap.getKeepAlive();
      this._kaw = rap.getKeepAliveWait();
      this._security = rap.getLocalAccessPointObject().getSecurity();
      this.configSource = rap;
   }
}

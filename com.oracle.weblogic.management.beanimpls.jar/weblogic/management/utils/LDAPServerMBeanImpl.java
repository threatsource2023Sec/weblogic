package weblogic.management.utils;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.commo.AbstractCommoConfigurationBean;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class LDAPServerMBeanImpl extends AbstractCommoConfigurationBean implements LDAPServerMBean, Serializable {
   private boolean _BindAnonymouslyOnReferrals;
   private boolean _CacheEnabled;
   private int _CacheSize;
   private int _CacheTTL;
   private int _ConnectTimeout;
   private int _ConnectionPoolSize;
   private int _ConnectionRetryLimit;
   private String _Credential;
   private byte[] _CredentialEncrypted;
   private boolean _FollowReferrals;
   private String _Host;
   private int _ParallelConnectDelay;
   private int _Port;
   private String _Principal;
   private int _ResultsTimeLimit;
   private boolean _SSLEnabled;
   private static SchemaHelper2 _schemaHelper;

   public LDAPServerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public LDAPServerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LDAPServerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getHost() {
      return this._Host;
   }

   public boolean isHostInherited() {
      return false;
   }

   public boolean isHostSet() {
      return this._isSet(2);
   }

   public void setHost(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Host;
      this._Host = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getPort() {
      return this._Port;
   }

   public boolean isPortInherited() {
      return false;
   }

   public boolean isPortSet() {
      return this._isSet(3);
   }

   public void setPort(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("Port", (long)param0, 1L, 65534L);
      int _oldVal = this._Port;
      this._Port = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isSSLEnabled() {
      return this._SSLEnabled;
   }

   public boolean isSSLEnabledInherited() {
      return false;
   }

   public boolean isSSLEnabledSet() {
      return this._isSet(4);
   }

   public void setSSLEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._SSLEnabled;
      this._SSLEnabled = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getPrincipal() {
      return this._Principal;
   }

   public boolean isPrincipalInherited() {
      return false;
   }

   public boolean isPrincipalSet() {
      return this._isSet(5);
   }

   public void setPrincipal(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Principal;
      this._Principal = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getCredential() {
      byte[] bEncrypted = this.getCredentialEncrypted();
      return bEncrypted == null ? null : this._decrypt("Credential", bEncrypted);
   }

   public boolean isCredentialInherited() {
      return false;
   }

   public boolean isCredentialSet() {
      return this.isCredentialEncryptedSet();
   }

   public void setCredential(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setCredentialEncrypted(param0 == null ? null : this._encrypt("Credential", param0));
   }

   public byte[] getCredentialEncrypted() {
      return this._getHelper()._cloneArray(this._CredentialEncrypted);
   }

   public String getCredentialEncryptedAsString() {
      byte[] obj = this.getCredentialEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isCredentialEncryptedInherited() {
      return false;
   }

   public boolean isCredentialEncryptedSet() {
      return this._isSet(7);
   }

   public void setCredentialEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setCredentialEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isCacheEnabled() {
      return this._CacheEnabled;
   }

   public boolean isCacheEnabledInherited() {
      return false;
   }

   public boolean isCacheEnabledSet() {
      return this._isSet(8);
   }

   public void setCacheEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._CacheEnabled;
      this._CacheEnabled = param0;
      this._postSet(8, _oldVal, param0);
   }

   public int getCacheSize() {
      return this._CacheSize;
   }

   public boolean isCacheSizeInherited() {
      return false;
   }

   public boolean isCacheSizeSet() {
      return this._isSet(9);
   }

   public void setCacheSize(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("CacheSize", param0, 0);
      int _oldVal = this._CacheSize;
      this._CacheSize = param0;
      this._postSet(9, _oldVal, param0);
   }

   public int getCacheTTL() {
      return this._CacheTTL;
   }

   public boolean isCacheTTLInherited() {
      return false;
   }

   public boolean isCacheTTLSet() {
      return this._isSet(10);
   }

   public void setCacheTTL(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("CacheTTL", param0, 0);
      int _oldVal = this._CacheTTL;
      this._CacheTTL = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isFollowReferrals() {
      return this._FollowReferrals;
   }

   public boolean isFollowReferralsInherited() {
      return false;
   }

   public boolean isFollowReferralsSet() {
      return this._isSet(11);
   }

   public void setFollowReferrals(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._FollowReferrals;
      this._FollowReferrals = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isBindAnonymouslyOnReferrals() {
      return this._BindAnonymouslyOnReferrals;
   }

   public boolean isBindAnonymouslyOnReferralsInherited() {
      return false;
   }

   public boolean isBindAnonymouslyOnReferralsSet() {
      return this._isSet(12);
   }

   public void setBindAnonymouslyOnReferrals(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._BindAnonymouslyOnReferrals;
      this._BindAnonymouslyOnReferrals = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getResultsTimeLimit() {
      return this._ResultsTimeLimit;
   }

   public boolean isResultsTimeLimitInherited() {
      return false;
   }

   public boolean isResultsTimeLimitSet() {
      return this._isSet(13);
   }

   public void setResultsTimeLimit(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._ResultsTimeLimit;
      this._ResultsTimeLimit = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getConnectTimeout() {
      return this._ConnectTimeout;
   }

   public boolean isConnectTimeoutInherited() {
      return false;
   }

   public boolean isConnectTimeoutSet() {
      return this._isSet(14);
   }

   public void setConnectTimeout(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._ConnectTimeout;
      this._ConnectTimeout = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getParallelConnectDelay() {
      return this._ParallelConnectDelay;
   }

   public boolean isParallelConnectDelayInherited() {
      return false;
   }

   public boolean isParallelConnectDelaySet() {
      return this._isSet(15);
   }

   public void setParallelConnectDelay(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._ParallelConnectDelay;
      this._ParallelConnectDelay = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getConnectionRetryLimit() {
      return this._ConnectionRetryLimit;
   }

   public boolean isConnectionRetryLimitInherited() {
      return false;
   }

   public boolean isConnectionRetryLimitSet() {
      return this._isSet(16);
   }

   public void setConnectionRetryLimit(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._ConnectionRetryLimit;
      this._ConnectionRetryLimit = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getConnectionPoolSize() {
      return this._ConnectionPoolSize;
   }

   public boolean isConnectionPoolSizeInherited() {
      return false;
   }

   public boolean isConnectionPoolSizeSet() {
      return this._isSet(17);
   }

   public void setConnectionPoolSize(int param0) throws InvalidAttributeValueException {
      int _oldVal = this._ConnectionPoolSize;
      this._ConnectionPoolSize = param0;
      this._postSet(17, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setCredentialEncrypted(byte[] param0) {
      byte[] _oldVal = this._CredentialEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: CredentialEncrypted of LDAPServerMBean");
      } else {
         this._getHelper()._clearArray(this._CredentialEncrypted);
         this._CredentialEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(7, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 6) {
            this._markSet(7, false);
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
         idx = 9;
      }

      try {
         switch (idx) {
            case 9:
               this._CacheSize = 32;
               if (initOne) {
                  break;
               }
            case 10:
               this._CacheTTL = 60;
               if (initOne) {
                  break;
               }
            case 14:
               this._ConnectTimeout = 0;
               if (initOne) {
                  break;
               }
            case 17:
               this._ConnectionPoolSize = 6;
               if (initOne) {
                  break;
               }
            case 16:
               this._ConnectionRetryLimit = 1;
               if (initOne) {
                  break;
               }
            case 6:
               this._CredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._CredentialEncrypted = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Host = "localhost";
               if (initOne) {
                  break;
               }
            case 15:
               this._ParallelConnectDelay = 0;
               if (initOne) {
                  break;
               }
            case 3:
               this._Port = 389;
               if (initOne) {
                  break;
               }
            case 5:
               this._Principal = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ResultsTimeLimit = 0;
               if (initOne) {
                  break;
               }
            case 12:
               this._BindAnonymouslyOnReferrals = false;
               if (initOne) {
                  break;
               }
            case 8:
               this._CacheEnabled = true;
               if (initOne) {
                  break;
               }
            case 11:
               this._FollowReferrals = true;
               if (initOne) {
                  break;
               }
            case 4:
               this._SSLEnabled = false;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.utils.LDAPServerMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("host")) {
                  return 2;
               }

               if (s.equals("port")) {
                  return 3;
               }
            case 5:
            case 6:
            case 7:
            case 12:
            case 14:
            case 17:
            case 19:
            case 21:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            default:
               break;
            case 8:
               if (s.equals("cachettl")) {
                  return 10;
               }
               break;
            case 9:
               if (s.equals("principal")) {
                  return 5;
               }
               break;
            case 10:
               if (s.equals("cache-size")) {
                  return 9;
               }

               if (s.equals("credential")) {
                  return 6;
               }
               break;
            case 11:
               if (s.equals("ssl-enabled")) {
                  return 4;
               }
               break;
            case 13:
               if (s.equals("cache-enabled")) {
                  return 8;
               }
               break;
            case 15:
               if (s.equals("connect-timeout")) {
                  return 14;
               }
               break;
            case 16:
               if (s.equals("follow-referrals")) {
                  return 11;
               }
               break;
            case 18:
               if (s.equals("results-time-limit")) {
                  return 13;
               }
               break;
            case 20:
               if (s.equals("connection-pool-size")) {
                  return 17;
               }

               if (s.equals("credential-encrypted")) {
                  return 7;
               }
               break;
            case 22:
               if (s.equals("connection-retry-limit")) {
                  return 16;
               }

               if (s.equals("parallel-connect-delay")) {
                  return 15;
               }
               break;
            case 29:
               if (s.equals("bind-anonymously-on-referrals")) {
                  return 12;
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
               return "host";
            case 3:
               return "port";
            case 4:
               return "ssl-enabled";
            case 5:
               return "principal";
            case 6:
               return "credential";
            case 7:
               return "credential-encrypted";
            case 8:
               return "cache-enabled";
            case 9:
               return "cache-size";
            case 10:
               return "cachettl";
            case 11:
               return "follow-referrals";
            case 12:
               return "bind-anonymously-on-referrals";
            case 13:
               return "results-time-limit";
            case 14:
               return "connect-timeout";
            case 15:
               return "parallel-connect-delay";
            case 16:
               return "connection-retry-limit";
            case 17:
               return "connection-pool-size";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractCommoConfigurationBean.Helper {
      private LDAPServerMBeanImpl bean;

      protected Helper(LDAPServerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Host";
            case 3:
               return "Port";
            case 4:
               return "SSLEnabled";
            case 5:
               return "Principal";
            case 6:
               return "Credential";
            case 7:
               return "CredentialEncrypted";
            case 8:
               return "CacheEnabled";
            case 9:
               return "CacheSize";
            case 10:
               return "CacheTTL";
            case 11:
               return "FollowReferrals";
            case 12:
               return "BindAnonymouslyOnReferrals";
            case 13:
               return "ResultsTimeLimit";
            case 14:
               return "ConnectTimeout";
            case 15:
               return "ParallelConnectDelay";
            case 16:
               return "ConnectionRetryLimit";
            case 17:
               return "ConnectionPoolSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CacheSize")) {
            return 9;
         } else if (propName.equals("CacheTTL")) {
            return 10;
         } else if (propName.equals("ConnectTimeout")) {
            return 14;
         } else if (propName.equals("ConnectionPoolSize")) {
            return 17;
         } else if (propName.equals("ConnectionRetryLimit")) {
            return 16;
         } else if (propName.equals("Credential")) {
            return 6;
         } else if (propName.equals("CredentialEncrypted")) {
            return 7;
         } else if (propName.equals("Host")) {
            return 2;
         } else if (propName.equals("ParallelConnectDelay")) {
            return 15;
         } else if (propName.equals("Port")) {
            return 3;
         } else if (propName.equals("Principal")) {
            return 5;
         } else if (propName.equals("ResultsTimeLimit")) {
            return 13;
         } else if (propName.equals("BindAnonymouslyOnReferrals")) {
            return 12;
         } else if (propName.equals("CacheEnabled")) {
            return 8;
         } else if (propName.equals("FollowReferrals")) {
            return 11;
         } else {
            return propName.equals("SSLEnabled") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isCacheSizeSet()) {
               buf.append("CacheSize");
               buf.append(String.valueOf(this.bean.getCacheSize()));
            }

            if (this.bean.isCacheTTLSet()) {
               buf.append("CacheTTL");
               buf.append(String.valueOf(this.bean.getCacheTTL()));
            }

            if (this.bean.isConnectTimeoutSet()) {
               buf.append("ConnectTimeout");
               buf.append(String.valueOf(this.bean.getConnectTimeout()));
            }

            if (this.bean.isConnectionPoolSizeSet()) {
               buf.append("ConnectionPoolSize");
               buf.append(String.valueOf(this.bean.getConnectionPoolSize()));
            }

            if (this.bean.isConnectionRetryLimitSet()) {
               buf.append("ConnectionRetryLimit");
               buf.append(String.valueOf(this.bean.getConnectionRetryLimit()));
            }

            if (this.bean.isCredentialSet()) {
               buf.append("Credential");
               buf.append(String.valueOf(this.bean.getCredential()));
            }

            if (this.bean.isCredentialEncryptedSet()) {
               buf.append("CredentialEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getCredentialEncrypted())));
            }

            if (this.bean.isHostSet()) {
               buf.append("Host");
               buf.append(String.valueOf(this.bean.getHost()));
            }

            if (this.bean.isParallelConnectDelaySet()) {
               buf.append("ParallelConnectDelay");
               buf.append(String.valueOf(this.bean.getParallelConnectDelay()));
            }

            if (this.bean.isPortSet()) {
               buf.append("Port");
               buf.append(String.valueOf(this.bean.getPort()));
            }

            if (this.bean.isPrincipalSet()) {
               buf.append("Principal");
               buf.append(String.valueOf(this.bean.getPrincipal()));
            }

            if (this.bean.isResultsTimeLimitSet()) {
               buf.append("ResultsTimeLimit");
               buf.append(String.valueOf(this.bean.getResultsTimeLimit()));
            }

            if (this.bean.isBindAnonymouslyOnReferralsSet()) {
               buf.append("BindAnonymouslyOnReferrals");
               buf.append(String.valueOf(this.bean.isBindAnonymouslyOnReferrals()));
            }

            if (this.bean.isCacheEnabledSet()) {
               buf.append("CacheEnabled");
               buf.append(String.valueOf(this.bean.isCacheEnabled()));
            }

            if (this.bean.isFollowReferralsSet()) {
               buf.append("FollowReferrals");
               buf.append(String.valueOf(this.bean.isFollowReferrals()));
            }

            if (this.bean.isSSLEnabledSet()) {
               buf.append("SSLEnabled");
               buf.append(String.valueOf(this.bean.isSSLEnabled()));
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
            LDAPServerMBeanImpl otherTyped = (LDAPServerMBeanImpl)other;
            this.computeDiff("CacheSize", this.bean.getCacheSize(), otherTyped.getCacheSize(), true);
            this.computeDiff("CacheTTL", this.bean.getCacheTTL(), otherTyped.getCacheTTL(), true);
            this.computeDiff("ConnectTimeout", this.bean.getConnectTimeout(), otherTyped.getConnectTimeout(), true);
            this.computeDiff("ConnectionPoolSize", this.bean.getConnectionPoolSize(), otherTyped.getConnectionPoolSize(), true);
            this.computeDiff("ConnectionRetryLimit", this.bean.getConnectionRetryLimit(), otherTyped.getConnectionRetryLimit(), true);
            this.computeDiff("CredentialEncrypted", this.bean.getCredentialEncrypted(), otherTyped.getCredentialEncrypted(), true);
            this.computeDiff("Host", this.bean.getHost(), otherTyped.getHost(), true);
            this.computeDiff("ParallelConnectDelay", this.bean.getParallelConnectDelay(), otherTyped.getParallelConnectDelay(), true);
            this.computeDiff("Port", this.bean.getPort(), otherTyped.getPort(), true);
            this.computeDiff("Principal", this.bean.getPrincipal(), otherTyped.getPrincipal(), true);
            this.computeDiff("ResultsTimeLimit", this.bean.getResultsTimeLimit(), otherTyped.getResultsTimeLimit(), true);
            this.computeDiff("BindAnonymouslyOnReferrals", this.bean.isBindAnonymouslyOnReferrals(), otherTyped.isBindAnonymouslyOnReferrals(), false);
            this.computeDiff("CacheEnabled", this.bean.isCacheEnabled(), otherTyped.isCacheEnabled(), true);
            this.computeDiff("FollowReferrals", this.bean.isFollowReferrals(), otherTyped.isFollowReferrals(), true);
            this.computeDiff("SSLEnabled", this.bean.isSSLEnabled(), otherTyped.isSSLEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LDAPServerMBeanImpl original = (LDAPServerMBeanImpl)event.getSourceBean();
            LDAPServerMBeanImpl proposed = (LDAPServerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CacheSize")) {
                  original.setCacheSize(proposed.getCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("CacheTTL")) {
                  original.setCacheTTL(proposed.getCacheTTL());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ConnectTimeout")) {
                  original.setConnectTimeout(proposed.getConnectTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ConnectionPoolSize")) {
                  original.setConnectionPoolSize(proposed.getConnectionPoolSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("ConnectionRetryLimit")) {
                  original.setConnectionRetryLimit(proposed.getConnectionRetryLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (!prop.equals("Credential")) {
                  if (prop.equals("CredentialEncrypted")) {
                     original.setCredentialEncrypted(proposed.getCredentialEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  } else if (prop.equals("Host")) {
                     original.setHost(proposed.getHost());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("ParallelConnectDelay")) {
                     original.setParallelConnectDelay(proposed.getParallelConnectDelay());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("Port")) {
                     original.setPort(proposed.getPort());
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  } else if (prop.equals("Principal")) {
                     original.setPrincipal(proposed.getPrincipal());
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  } else if (prop.equals("ResultsTimeLimit")) {
                     original.setResultsTimeLimit(proposed.getResultsTimeLimit());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("BindAnonymouslyOnReferrals")) {
                     original.setBindAnonymouslyOnReferrals(proposed.isBindAnonymouslyOnReferrals());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("CacheEnabled")) {
                     original.setCacheEnabled(proposed.isCacheEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  } else if (prop.equals("FollowReferrals")) {
                     original.setFollowReferrals(proposed.isFollowReferrals());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("SSLEnabled")) {
                     original.setSSLEnabled(proposed.isSSLEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            LDAPServerMBeanImpl copy = (LDAPServerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CacheSize")) && this.bean.isCacheSizeSet()) {
               copy.setCacheSize(this.bean.getCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheTTL")) && this.bean.isCacheTTLSet()) {
               copy.setCacheTTL(this.bean.getCacheTTL());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectTimeout")) && this.bean.isConnectTimeoutSet()) {
               copy.setConnectTimeout(this.bean.getConnectTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionPoolSize")) && this.bean.isConnectionPoolSizeSet()) {
               copy.setConnectionPoolSize(this.bean.getConnectionPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionRetryLimit")) && this.bean.isConnectionRetryLimitSet()) {
               copy.setConnectionRetryLimit(this.bean.getConnectionRetryLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("CredentialEncrypted")) && this.bean.isCredentialEncryptedSet()) {
               Object o = this.bean.getCredentialEncrypted();
               copy.setCredentialEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Host")) && this.bean.isHostSet()) {
               copy.setHost(this.bean.getHost());
            }

            if ((excludeProps == null || !excludeProps.contains("ParallelConnectDelay")) && this.bean.isParallelConnectDelaySet()) {
               copy.setParallelConnectDelay(this.bean.getParallelConnectDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("Port")) && this.bean.isPortSet()) {
               copy.setPort(this.bean.getPort());
            }

            if ((excludeProps == null || !excludeProps.contains("Principal")) && this.bean.isPrincipalSet()) {
               copy.setPrincipal(this.bean.getPrincipal());
            }

            if ((excludeProps == null || !excludeProps.contains("ResultsTimeLimit")) && this.bean.isResultsTimeLimitSet()) {
               copy.setResultsTimeLimit(this.bean.getResultsTimeLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("BindAnonymouslyOnReferrals")) && this.bean.isBindAnonymouslyOnReferralsSet()) {
               copy.setBindAnonymouslyOnReferrals(this.bean.isBindAnonymouslyOnReferrals());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheEnabled")) && this.bean.isCacheEnabledSet()) {
               copy.setCacheEnabled(this.bean.isCacheEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("FollowReferrals")) && this.bean.isFollowReferralsSet()) {
               copy.setFollowReferrals(this.bean.isFollowReferrals());
            }

            if ((excludeProps == null || !excludeProps.contains("SSLEnabled")) && this.bean.isSSLEnabledSet()) {
               copy.setSSLEnabled(this.bean.isSSLEnabled());
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

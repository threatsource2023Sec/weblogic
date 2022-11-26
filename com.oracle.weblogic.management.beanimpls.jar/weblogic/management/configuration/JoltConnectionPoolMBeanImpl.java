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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JoltConnectionPoolMBeanImpl extends DeploymentMBeanImpl implements JoltConnectionPoolMBean, Serializable {
   private String _ApplicationPassword;
   private byte[] _ApplicationPasswordEncrypted;
   private String[] _FailoverAddresses;
   private String _KeyPassPhrase;
   private byte[] _KeyPassPhraseEncrypted;
   private String _KeyStoreName;
   private String _KeyStorePassPhrase;
   private byte[] _KeyStorePassPhraseEncrypted;
   private int _MaximumPoolSize;
   private int _MinimumPoolSize;
   private String[] _PrimaryAddresses;
   private int _RecvTimeout;
   private boolean _SecurityContextEnabled;
   private String _TrustStoreName;
   private String _TrustStorePassPhrase;
   private byte[] _TrustStorePassPhraseEncrypted;
   private String _UserName;
   private String _UserPassword;
   private byte[] _UserPasswordEncrypted;
   private String _UserRole;
   private static SchemaHelper2 _schemaHelper;

   public JoltConnectionPoolMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JoltConnectionPoolMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JoltConnectionPoolMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getPrimaryAddresses() {
      return this._PrimaryAddresses;
   }

   public boolean isPrimaryAddressesInherited() {
      return false;
   }

   public boolean isPrimaryAddressesSet() {
      return this._isSet(12);
   }

   public void setPrimaryAddresses(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._PrimaryAddresses;
      this._PrimaryAddresses = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean addPrimaryAddress(String param0) throws InvalidAttributeValueException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(12)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getPrimaryAddresses(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setPrimaryAddresses(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof InvalidAttributeValueException) {
            throw (InvalidAttributeValueException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removePrimaryAddress(String param0) throws InvalidAttributeValueException {
      String[] _old = this.getPrimaryAddresses();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setPrimaryAddresses(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public String[] getFailoverAddresses() {
      return this._FailoverAddresses;
   }

   public boolean isFailoverAddressesInherited() {
      return false;
   }

   public boolean isFailoverAddressesSet() {
      return this._isSet(13);
   }

   public void setFailoverAddresses(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._FailoverAddresses;
      this._FailoverAddresses = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean addFailoverAddress(String param0) throws InvalidAttributeValueException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(13)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getFailoverAddresses(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setFailoverAddresses(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof InvalidAttributeValueException) {
            throw (InvalidAttributeValueException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeFailoverAddress(String param0) throws InvalidAttributeValueException {
      String[] _old = this.getFailoverAddresses();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setFailoverAddresses(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public int getMinimumPoolSize() {
      return this._MinimumPoolSize;
   }

   public boolean isMinimumPoolSizeInherited() {
      return false;
   }

   public boolean isMinimumPoolSizeSet() {
      return this._isSet(14);
   }

   public void setMinimumPoolSize(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("MinimumPoolSize", (long)param0, 0L, 2147483647L);
      int _oldVal = this._MinimumPoolSize;
      this._MinimumPoolSize = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getMaximumPoolSize() {
      return this._MaximumPoolSize;
   }

   public boolean isMaximumPoolSizeInherited() {
      return false;
   }

   public boolean isMaximumPoolSizeSet() {
      return this._isSet(15);
   }

   public void setMaximumPoolSize(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("MaximumPoolSize", (long)param0, 1L, 2147483647L);
      int _oldVal = this._MaximumPoolSize;
      this._MaximumPoolSize = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getUserName() {
      return this._UserName;
   }

   public boolean isUserNameInherited() {
      return false;
   }

   public boolean isUserNameSet() {
      return this._isSet(16);
   }

   public void setUserName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UserName;
      this._UserName = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getUserPassword() {
      byte[] bEncrypted = this.getUserPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("UserPassword", bEncrypted);
   }

   public boolean isUserPasswordInherited() {
      return false;
   }

   public boolean isUserPasswordSet() {
      return this.isUserPasswordEncryptedSet();
   }

   public void setUserPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setUserPasswordEncrypted(param0 == null ? null : this._encrypt("UserPassword", param0));
   }

   public byte[] getUserPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._UserPasswordEncrypted);
   }

   public String getUserPasswordEncryptedAsString() {
      byte[] obj = this.getUserPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isUserPasswordEncryptedInherited() {
      return false;
   }

   public boolean isUserPasswordEncryptedSet() {
      return this._isSet(18);
   }

   public void setUserPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setUserPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getApplicationPassword() {
      byte[] bEncrypted = this.getApplicationPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("ApplicationPassword", bEncrypted);
   }

   public boolean isApplicationPasswordInherited() {
      return false;
   }

   public boolean isApplicationPasswordSet() {
      return this.isApplicationPasswordEncryptedSet();
   }

   public void setApplicationPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setApplicationPasswordEncrypted(param0 == null ? null : this._encrypt("ApplicationPassword", param0));
   }

   public byte[] getApplicationPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._ApplicationPasswordEncrypted);
   }

   public String getApplicationPasswordEncryptedAsString() {
      byte[] obj = this.getApplicationPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isApplicationPasswordEncryptedInherited() {
      return false;
   }

   public boolean isApplicationPasswordEncryptedSet() {
      return this._isSet(20);
   }

   public void setApplicationPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setApplicationPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getUserRole() {
      return this._UserRole;
   }

   public boolean isUserRoleInherited() {
      return false;
   }

   public boolean isUserRoleSet() {
      return this._isSet(21);
   }

   public void setUserRole(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UserRole;
      this._UserRole = param0;
      this._postSet(21, _oldVal, param0);
   }

   public boolean isSecurityContextEnabled() {
      if (!this._isSet(22)) {
         return this._isSecureModeEnabled();
      } else {
         return this._SecurityContextEnabled;
      }
   }

   public boolean isSecurityContextEnabledInherited() {
      return false;
   }

   public boolean isSecurityContextEnabledSet() {
      return this._isSet(22);
   }

   public void setSecurityContextEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._SecurityContextEnabled;
      this._SecurityContextEnabled = param0;
      this._postSet(22, _oldVal, param0);
   }

   public int getRecvTimeout() {
      return this._RecvTimeout;
   }

   public boolean isRecvTimeoutInherited() {
      return false;
   }

   public boolean isRecvTimeoutSet() {
      return this._isSet(23);
   }

   public void setRecvTimeout(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("RecvTimeout", (long)param0, 0L, 2147483647L);
      int _oldVal = this._RecvTimeout;
      this._RecvTimeout = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getKeyStoreName() {
      return this._KeyStoreName;
   }

   public boolean isKeyStoreNameInherited() {
      return false;
   }

   public boolean isKeyStoreNameSet() {
      return this._isSet(24);
   }

   public void setKeyStoreName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._KeyStoreName;
      this._KeyStoreName = param0;
      this._postSet(24, _oldVal, param0);
   }

   public String getTrustStoreName() {
      return this._TrustStoreName;
   }

   public boolean isTrustStoreNameInherited() {
      return false;
   }

   public boolean isTrustStoreNameSet() {
      return this._isSet(25);
   }

   public void setTrustStoreName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TrustStoreName;
      this._TrustStoreName = param0;
      this._postSet(25, _oldVal, param0);
   }

   public String getKeyPassPhrase() {
      byte[] bEncrypted = this.getKeyPassPhraseEncrypted();
      return bEncrypted == null ? null : this._decrypt("KeyPassPhrase", bEncrypted);
   }

   public boolean isKeyPassPhraseInherited() {
      return false;
   }

   public boolean isKeyPassPhraseSet() {
      return this.isKeyPassPhraseEncryptedSet();
   }

   public void setKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("KeyPassPhrase", param0));
   }

   public byte[] getKeyPassPhraseEncrypted() {
      return this._getHelper()._cloneArray(this._KeyPassPhraseEncrypted);
   }

   public String getKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isKeyPassPhraseEncryptedInherited() {
      return false;
   }

   public boolean isKeyPassPhraseEncryptedSet() {
      return this._isSet(27);
   }

   public void setKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getKeyStorePassPhrase() {
      byte[] bEncrypted = this.getKeyStorePassPhraseEncrypted();
      return bEncrypted == null ? null : this._decrypt("KeyStorePassPhrase", bEncrypted);
   }

   public boolean isKeyStorePassPhraseInherited() {
      return false;
   }

   public boolean isKeyStorePassPhraseSet() {
      return this.isKeyStorePassPhraseEncryptedSet();
   }

   public void setKeyStorePassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setKeyStorePassPhraseEncrypted(param0 == null ? null : this._encrypt("KeyStorePassPhrase", param0));
   }

   public byte[] getKeyStorePassPhraseEncrypted() {
      return this._getHelper()._cloneArray(this._KeyStorePassPhraseEncrypted);
   }

   public String getKeyStorePassPhraseEncryptedAsString() {
      byte[] obj = this.getKeyStorePassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isKeyStorePassPhraseEncryptedInherited() {
      return false;
   }

   public boolean isKeyStorePassPhraseEncryptedSet() {
      return this._isSet(29);
   }

   public void setKeyStorePassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setKeyStorePassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getTrustStorePassPhrase() {
      byte[] bEncrypted = this.getTrustStorePassPhraseEncrypted();
      return bEncrypted == null ? null : this._decrypt("TrustStorePassPhrase", bEncrypted);
   }

   public boolean isTrustStorePassPhraseInherited() {
      return false;
   }

   public boolean isTrustStorePassPhraseSet() {
      return this.isTrustStorePassPhraseEncryptedSet();
   }

   public void setTrustStorePassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setTrustStorePassPhraseEncrypted(param0 == null ? null : this._encrypt("TrustStorePassPhrase", param0));
   }

   public byte[] getTrustStorePassPhraseEncrypted() {
      return this._getHelper()._cloneArray(this._TrustStorePassPhraseEncrypted);
   }

   public String getTrustStorePassPhraseEncryptedAsString() {
      byte[] obj = this.getTrustStorePassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isTrustStorePassPhraseEncryptedInherited() {
      return false;
   }

   public boolean isTrustStorePassPhraseEncryptedSet() {
      return this._isSet(31);
   }

   public void setTrustStorePassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setTrustStorePassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      JoltConnectionPoolLegalHelper.validate(this);
   }

   public void setApplicationPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._ApplicationPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: ApplicationPasswordEncrypted of JoltConnectionPoolMBean");
      } else {
         this._getHelper()._clearArray(this._ApplicationPasswordEncrypted);
         this._ApplicationPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(20, _oldVal, param0);
      }
   }

   public void setKeyPassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._KeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: KeyPassPhraseEncrypted of JoltConnectionPoolMBean");
      } else {
         this._getHelper()._clearArray(this._KeyPassPhraseEncrypted);
         this._KeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(27, _oldVal, param0);
      }
   }

   public void setKeyStorePassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._KeyStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: KeyStorePassPhraseEncrypted of JoltConnectionPoolMBean");
      } else {
         this._getHelper()._clearArray(this._KeyStorePassPhraseEncrypted);
         this._KeyStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(29, _oldVal, param0);
      }
   }

   public void setTrustStorePassPhraseEncrypted(byte[] param0) {
      byte[] _oldVal = this._TrustStorePassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: TrustStorePassPhraseEncrypted of JoltConnectionPoolMBean");
      } else {
         this._getHelper()._clearArray(this._TrustStorePassPhraseEncrypted);
         this._TrustStorePassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(31, _oldVal, param0);
      }
   }

   public void setUserPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._UserPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: UserPasswordEncrypted of JoltConnectionPoolMBean");
      } else {
         this._getHelper()._clearArray(this._UserPasswordEncrypted);
         this._UserPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(18, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 19) {
            this._markSet(20, false);
         }

         if (idx == 26) {
            this._markSet(27, false);
         }

         if (idx == 28) {
            this._markSet(29, false);
         }

         if (idx == 30) {
            this._markSet(31, false);
         }

         if (idx == 17) {
            this._markSet(18, false);
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
               this._ApplicationPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._ApplicationPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._FailoverAddresses = new String[0];
               if (initOne) {
                  break;
               }
            case 26:
               this._KeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._KeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._KeyStoreName = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._KeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._KeyStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._MaximumPoolSize = 1;
               if (initOne) {
                  break;
               }
            case 14:
               this._MinimumPoolSize = 0;
               if (initOne) {
                  break;
               }
            case 12:
               this._PrimaryAddresses = new String[0];
               if (initOne) {
                  break;
               }
            case 23:
               this._RecvTimeout = 0;
               if (initOne) {
                  break;
               }
            case 25:
               this._TrustStoreName = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._TrustStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 31:
               this._TrustStorePassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._UserName = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._UserPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._UserPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._UserRole = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._SecurityContextEnabled = false;
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
      return "JoltConnectionPool";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ApplicationPassword")) {
         oldVal = this._ApplicationPassword;
         this._ApplicationPassword = (String)v;
         this._postSet(19, oldVal, this._ApplicationPassword);
      } else {
         byte[] oldVal;
         if (name.equals("ApplicationPasswordEncrypted")) {
            oldVal = this._ApplicationPasswordEncrypted;
            this._ApplicationPasswordEncrypted = (byte[])((byte[])v);
            this._postSet(20, oldVal, this._ApplicationPasswordEncrypted);
         } else {
            String[] oldVal;
            if (name.equals("FailoverAddresses")) {
               oldVal = this._FailoverAddresses;
               this._FailoverAddresses = (String[])((String[])v);
               this._postSet(13, oldVal, this._FailoverAddresses);
            } else if (name.equals("KeyPassPhrase")) {
               oldVal = this._KeyPassPhrase;
               this._KeyPassPhrase = (String)v;
               this._postSet(26, oldVal, this._KeyPassPhrase);
            } else if (name.equals("KeyPassPhraseEncrypted")) {
               oldVal = this._KeyPassPhraseEncrypted;
               this._KeyPassPhraseEncrypted = (byte[])((byte[])v);
               this._postSet(27, oldVal, this._KeyPassPhraseEncrypted);
            } else if (name.equals("KeyStoreName")) {
               oldVal = this._KeyStoreName;
               this._KeyStoreName = (String)v;
               this._postSet(24, oldVal, this._KeyStoreName);
            } else if (name.equals("KeyStorePassPhrase")) {
               oldVal = this._KeyStorePassPhrase;
               this._KeyStorePassPhrase = (String)v;
               this._postSet(28, oldVal, this._KeyStorePassPhrase);
            } else if (name.equals("KeyStorePassPhraseEncrypted")) {
               oldVal = this._KeyStorePassPhraseEncrypted;
               this._KeyStorePassPhraseEncrypted = (byte[])((byte[])v);
               this._postSet(29, oldVal, this._KeyStorePassPhraseEncrypted);
            } else {
               int oldVal;
               if (name.equals("MaximumPoolSize")) {
                  oldVal = this._MaximumPoolSize;
                  this._MaximumPoolSize = (Integer)v;
                  this._postSet(15, oldVal, this._MaximumPoolSize);
               } else if (name.equals("MinimumPoolSize")) {
                  oldVal = this._MinimumPoolSize;
                  this._MinimumPoolSize = (Integer)v;
                  this._postSet(14, oldVal, this._MinimumPoolSize);
               } else if (name.equals("PrimaryAddresses")) {
                  oldVal = this._PrimaryAddresses;
                  this._PrimaryAddresses = (String[])((String[])v);
                  this._postSet(12, oldVal, this._PrimaryAddresses);
               } else if (name.equals("RecvTimeout")) {
                  oldVal = this._RecvTimeout;
                  this._RecvTimeout = (Integer)v;
                  this._postSet(23, oldVal, this._RecvTimeout);
               } else if (name.equals("SecurityContextEnabled")) {
                  boolean oldVal = this._SecurityContextEnabled;
                  this._SecurityContextEnabled = (Boolean)v;
                  this._postSet(22, oldVal, this._SecurityContextEnabled);
               } else if (name.equals("TrustStoreName")) {
                  oldVal = this._TrustStoreName;
                  this._TrustStoreName = (String)v;
                  this._postSet(25, oldVal, this._TrustStoreName);
               } else if (name.equals("TrustStorePassPhrase")) {
                  oldVal = this._TrustStorePassPhrase;
                  this._TrustStorePassPhrase = (String)v;
                  this._postSet(30, oldVal, this._TrustStorePassPhrase);
               } else if (name.equals("TrustStorePassPhraseEncrypted")) {
                  oldVal = this._TrustStorePassPhraseEncrypted;
                  this._TrustStorePassPhraseEncrypted = (byte[])((byte[])v);
                  this._postSet(31, oldVal, this._TrustStorePassPhraseEncrypted);
               } else if (name.equals("UserName")) {
                  oldVal = this._UserName;
                  this._UserName = (String)v;
                  this._postSet(16, oldVal, this._UserName);
               } else if (name.equals("UserPassword")) {
                  oldVal = this._UserPassword;
                  this._UserPassword = (String)v;
                  this._postSet(17, oldVal, this._UserPassword);
               } else if (name.equals("UserPasswordEncrypted")) {
                  oldVal = this._UserPasswordEncrypted;
                  this._UserPasswordEncrypted = (byte[])((byte[])v);
                  this._postSet(18, oldVal, this._UserPasswordEncrypted);
               } else if (name.equals("UserRole")) {
                  oldVal = this._UserRole;
                  this._UserRole = (String)v;
                  this._postSet(21, oldVal, this._UserRole);
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ApplicationPassword")) {
         return this._ApplicationPassword;
      } else if (name.equals("ApplicationPasswordEncrypted")) {
         return this._ApplicationPasswordEncrypted;
      } else if (name.equals("FailoverAddresses")) {
         return this._FailoverAddresses;
      } else if (name.equals("KeyPassPhrase")) {
         return this._KeyPassPhrase;
      } else if (name.equals("KeyPassPhraseEncrypted")) {
         return this._KeyPassPhraseEncrypted;
      } else if (name.equals("KeyStoreName")) {
         return this._KeyStoreName;
      } else if (name.equals("KeyStorePassPhrase")) {
         return this._KeyStorePassPhrase;
      } else if (name.equals("KeyStorePassPhraseEncrypted")) {
         return this._KeyStorePassPhraseEncrypted;
      } else if (name.equals("MaximumPoolSize")) {
         return new Integer(this._MaximumPoolSize);
      } else if (name.equals("MinimumPoolSize")) {
         return new Integer(this._MinimumPoolSize);
      } else if (name.equals("PrimaryAddresses")) {
         return this._PrimaryAddresses;
      } else if (name.equals("RecvTimeout")) {
         return new Integer(this._RecvTimeout);
      } else if (name.equals("SecurityContextEnabled")) {
         return new Boolean(this._SecurityContextEnabled);
      } else if (name.equals("TrustStoreName")) {
         return this._TrustStoreName;
      } else if (name.equals("TrustStorePassPhrase")) {
         return this._TrustStorePassPhrase;
      } else if (name.equals("TrustStorePassPhraseEncrypted")) {
         return this._TrustStorePassPhraseEncrypted;
      } else if (name.equals("UserName")) {
         return this._UserName;
      } else if (name.equals("UserPassword")) {
         return this._UserPassword;
      } else if (name.equals("UserPasswordEncrypted")) {
         return this._UserPasswordEncrypted;
      } else {
         return name.equals("UserRole") ? this._UserRole : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("user-name")) {
                  return 16;
               }

               if (s.equals("user-role")) {
                  return 21;
               }
            case 10:
            case 11:
            case 18:
            case 19:
            case 22:
            case 26:
            case 27:
            case 28:
            case 29:
            case 32:
            default:
               break;
            case 12:
               if (s.equals("recv-timeout")) {
                  return 23;
               }
               break;
            case 13:
               if (s.equals("user-password")) {
                  return 17;
               }
               break;
            case 14:
               if (s.equals("key-store-name")) {
                  return 24;
               }
               break;
            case 15:
               if (s.equals("key-pass-phrase")) {
                  return 26;
               }

               if (s.equals("primary-address")) {
                  return 12;
               }
               break;
            case 16:
               if (s.equals("failover-address")) {
                  return 13;
               }

               if (s.equals("trust-store-name")) {
                  return 25;
               }
               break;
            case 17:
               if (s.equals("maximum-pool-size")) {
                  return 15;
               }

               if (s.equals("minimum-pool-size")) {
                  return 14;
               }
               break;
            case 20:
               if (s.equals("application-password")) {
                  return 19;
               }
               break;
            case 21:
               if (s.equals("key-store-pass-phrase")) {
                  return 28;
               }
               break;
            case 23:
               if (s.equals("trust-store-pass-phrase")) {
                  return 30;
               }

               if (s.equals("user-password-encrypted")) {
                  return 18;
               }
               break;
            case 24:
               if (s.equals("security-context-enabled")) {
                  return 22;
               }
               break;
            case 25:
               if (s.equals("key-pass-phrase-encrypted")) {
                  return 27;
               }
               break;
            case 30:
               if (s.equals("application-password-encrypted")) {
                  return 20;
               }
               break;
            case 31:
               if (s.equals("key-store-pass-phrase-encrypted")) {
                  return 29;
               }
               break;
            case 33:
               if (s.equals("trust-store-pass-phrase-encrypted")) {
                  return 31;
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
            case 12:
               return "primary-address";
            case 13:
               return "failover-address";
            case 14:
               return "minimum-pool-size";
            case 15:
               return "maximum-pool-size";
            case 16:
               return "user-name";
            case 17:
               return "user-password";
            case 18:
               return "user-password-encrypted";
            case 19:
               return "application-password";
            case 20:
               return "application-password-encrypted";
            case 21:
               return "user-role";
            case 22:
               return "security-context-enabled";
            case 23:
               return "recv-timeout";
            case 24:
               return "key-store-name";
            case 25:
               return "trust-store-name";
            case 26:
               return "key-pass-phrase";
            case 27:
               return "key-pass-phrase-encrypted";
            case 28:
               return "key-store-pass-phrase";
            case 29:
               return "key-store-pass-phrase-encrypted";
            case 30:
               return "trust-store-pass-phrase";
            case 31:
               return "trust-store-pass-phrase-encrypted";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
            default:
               return super.isArray(propIndex);
            case 12:
               return true;
            case 13:
               return true;
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private JoltConnectionPoolMBeanImpl bean;

      protected Helper(JoltConnectionPoolMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "PrimaryAddresses";
            case 13:
               return "FailoverAddresses";
            case 14:
               return "MinimumPoolSize";
            case 15:
               return "MaximumPoolSize";
            case 16:
               return "UserName";
            case 17:
               return "UserPassword";
            case 18:
               return "UserPasswordEncrypted";
            case 19:
               return "ApplicationPassword";
            case 20:
               return "ApplicationPasswordEncrypted";
            case 21:
               return "UserRole";
            case 22:
               return "SecurityContextEnabled";
            case 23:
               return "RecvTimeout";
            case 24:
               return "KeyStoreName";
            case 25:
               return "TrustStoreName";
            case 26:
               return "KeyPassPhrase";
            case 27:
               return "KeyPassPhraseEncrypted";
            case 28:
               return "KeyStorePassPhrase";
            case 29:
               return "KeyStorePassPhraseEncrypted";
            case 30:
               return "TrustStorePassPhrase";
            case 31:
               return "TrustStorePassPhraseEncrypted";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ApplicationPassword")) {
            return 19;
         } else if (propName.equals("ApplicationPasswordEncrypted")) {
            return 20;
         } else if (propName.equals("FailoverAddresses")) {
            return 13;
         } else if (propName.equals("KeyPassPhrase")) {
            return 26;
         } else if (propName.equals("KeyPassPhraseEncrypted")) {
            return 27;
         } else if (propName.equals("KeyStoreName")) {
            return 24;
         } else if (propName.equals("KeyStorePassPhrase")) {
            return 28;
         } else if (propName.equals("KeyStorePassPhraseEncrypted")) {
            return 29;
         } else if (propName.equals("MaximumPoolSize")) {
            return 15;
         } else if (propName.equals("MinimumPoolSize")) {
            return 14;
         } else if (propName.equals("PrimaryAddresses")) {
            return 12;
         } else if (propName.equals("RecvTimeout")) {
            return 23;
         } else if (propName.equals("TrustStoreName")) {
            return 25;
         } else if (propName.equals("TrustStorePassPhrase")) {
            return 30;
         } else if (propName.equals("TrustStorePassPhraseEncrypted")) {
            return 31;
         } else if (propName.equals("UserName")) {
            return 16;
         } else if (propName.equals("UserPassword")) {
            return 17;
         } else if (propName.equals("UserPasswordEncrypted")) {
            return 18;
         } else if (propName.equals("UserRole")) {
            return 21;
         } else {
            return propName.equals("SecurityContextEnabled") ? 22 : super.getPropertyIndex(propName);
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
            if (this.bean.isApplicationPasswordSet()) {
               buf.append("ApplicationPassword");
               buf.append(String.valueOf(this.bean.getApplicationPassword()));
            }

            if (this.bean.isApplicationPasswordEncryptedSet()) {
               buf.append("ApplicationPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getApplicationPasswordEncrypted())));
            }

            if (this.bean.isFailoverAddressesSet()) {
               buf.append("FailoverAddresses");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getFailoverAddresses())));
            }

            if (this.bean.isKeyPassPhraseSet()) {
               buf.append("KeyPassPhrase");
               buf.append(String.valueOf(this.bean.getKeyPassPhrase()));
            }

            if (this.bean.isKeyPassPhraseEncryptedSet()) {
               buf.append("KeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getKeyPassPhraseEncrypted())));
            }

            if (this.bean.isKeyStoreNameSet()) {
               buf.append("KeyStoreName");
               buf.append(String.valueOf(this.bean.getKeyStoreName()));
            }

            if (this.bean.isKeyStorePassPhraseSet()) {
               buf.append("KeyStorePassPhrase");
               buf.append(String.valueOf(this.bean.getKeyStorePassPhrase()));
            }

            if (this.bean.isKeyStorePassPhraseEncryptedSet()) {
               buf.append("KeyStorePassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getKeyStorePassPhraseEncrypted())));
            }

            if (this.bean.isMaximumPoolSizeSet()) {
               buf.append("MaximumPoolSize");
               buf.append(String.valueOf(this.bean.getMaximumPoolSize()));
            }

            if (this.bean.isMinimumPoolSizeSet()) {
               buf.append("MinimumPoolSize");
               buf.append(String.valueOf(this.bean.getMinimumPoolSize()));
            }

            if (this.bean.isPrimaryAddressesSet()) {
               buf.append("PrimaryAddresses");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPrimaryAddresses())));
            }

            if (this.bean.isRecvTimeoutSet()) {
               buf.append("RecvTimeout");
               buf.append(String.valueOf(this.bean.getRecvTimeout()));
            }

            if (this.bean.isTrustStoreNameSet()) {
               buf.append("TrustStoreName");
               buf.append(String.valueOf(this.bean.getTrustStoreName()));
            }

            if (this.bean.isTrustStorePassPhraseSet()) {
               buf.append("TrustStorePassPhrase");
               buf.append(String.valueOf(this.bean.getTrustStorePassPhrase()));
            }

            if (this.bean.isTrustStorePassPhraseEncryptedSet()) {
               buf.append("TrustStorePassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTrustStorePassPhraseEncrypted())));
            }

            if (this.bean.isUserNameSet()) {
               buf.append("UserName");
               buf.append(String.valueOf(this.bean.getUserName()));
            }

            if (this.bean.isUserPasswordSet()) {
               buf.append("UserPassword");
               buf.append(String.valueOf(this.bean.getUserPassword()));
            }

            if (this.bean.isUserPasswordEncryptedSet()) {
               buf.append("UserPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUserPasswordEncrypted())));
            }

            if (this.bean.isUserRoleSet()) {
               buf.append("UserRole");
               buf.append(String.valueOf(this.bean.getUserRole()));
            }

            if (this.bean.isSecurityContextEnabledSet()) {
               buf.append("SecurityContextEnabled");
               buf.append(String.valueOf(this.bean.isSecurityContextEnabled()));
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
            JoltConnectionPoolMBeanImpl otherTyped = (JoltConnectionPoolMBeanImpl)other;
            this.computeDiff("ApplicationPasswordEncrypted", this.bean.getApplicationPasswordEncrypted(), otherTyped.getApplicationPasswordEncrypted(), false);
            this.computeDiff("FailoverAddresses", this.bean.getFailoverAddresses(), otherTyped.getFailoverAddresses(), false);
            this.computeDiff("KeyPassPhraseEncrypted", this.bean.getKeyPassPhraseEncrypted(), otherTyped.getKeyPassPhraseEncrypted(), true);
            this.computeDiff("KeyStoreName", this.bean.getKeyStoreName(), otherTyped.getKeyStoreName(), false);
            this.computeDiff("KeyStorePassPhraseEncrypted", this.bean.getKeyStorePassPhraseEncrypted(), otherTyped.getKeyStorePassPhraseEncrypted(), true);
            this.computeDiff("MaximumPoolSize", this.bean.getMaximumPoolSize(), otherTyped.getMaximumPoolSize(), false);
            this.computeDiff("MinimumPoolSize", this.bean.getMinimumPoolSize(), otherTyped.getMinimumPoolSize(), false);
            this.computeDiff("PrimaryAddresses", this.bean.getPrimaryAddresses(), otherTyped.getPrimaryAddresses(), false);
            this.computeDiff("RecvTimeout", this.bean.getRecvTimeout(), otherTyped.getRecvTimeout(), false);
            this.computeDiff("TrustStoreName", this.bean.getTrustStoreName(), otherTyped.getTrustStoreName(), false);
            this.computeDiff("TrustStorePassPhraseEncrypted", this.bean.getTrustStorePassPhraseEncrypted(), otherTyped.getTrustStorePassPhraseEncrypted(), true);
            this.computeDiff("UserName", this.bean.getUserName(), otherTyped.getUserName(), false);
            this.computeDiff("UserPasswordEncrypted", this.bean.getUserPasswordEncrypted(), otherTyped.getUserPasswordEncrypted(), false);
            this.computeDiff("UserRole", this.bean.getUserRole(), otherTyped.getUserRole(), false);
            this.computeDiff("SecurityContextEnabled", this.bean.isSecurityContextEnabled(), otherTyped.isSecurityContextEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JoltConnectionPoolMBeanImpl original = (JoltConnectionPoolMBeanImpl)event.getSourceBean();
            JoltConnectionPoolMBeanImpl proposed = (JoltConnectionPoolMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("ApplicationPassword")) {
                  if (prop.equals("ApplicationPasswordEncrypted")) {
                     original.setApplicationPasswordEncrypted(proposed.getApplicationPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (prop.equals("FailoverAddresses")) {
                     if (type == 2) {
                        update.resetAddedObject(update.getAddedObject());
                        original.addFailoverAddress((String)update.getAddedObject());
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeFailoverAddress((String)update.getRemovedObject());
                     }

                     if (original.getFailoverAddresses() == null || original.getFailoverAddresses().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 13);
                     }
                  } else if (!prop.equals("KeyPassPhrase")) {
                     if (prop.equals("KeyPassPhraseEncrypted")) {
                        original.setKeyPassPhraseEncrypted(proposed.getKeyPassPhraseEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 27);
                     } else if (prop.equals("KeyStoreName")) {
                        original.setKeyStoreName(proposed.getKeyStoreName());
                        original._conditionalUnset(update.isUnsetUpdate(), 24);
                     } else if (!prop.equals("KeyStorePassPhrase")) {
                        if (prop.equals("KeyStorePassPhraseEncrypted")) {
                           original.setKeyStorePassPhraseEncrypted(proposed.getKeyStorePassPhraseEncrypted());
                           original._conditionalUnset(update.isUnsetUpdate(), 29);
                        } else if (prop.equals("MaximumPoolSize")) {
                           original.setMaximumPoolSize(proposed.getMaximumPoolSize());
                           original._conditionalUnset(update.isUnsetUpdate(), 15);
                        } else if (prop.equals("MinimumPoolSize")) {
                           original.setMinimumPoolSize(proposed.getMinimumPoolSize());
                           original._conditionalUnset(update.isUnsetUpdate(), 14);
                        } else if (prop.equals("PrimaryAddresses")) {
                           if (type == 2) {
                              update.resetAddedObject(update.getAddedObject());
                              original.addPrimaryAddress((String)update.getAddedObject());
                           } else {
                              if (type != 3) {
                                 throw new AssertionError("Invalid type: " + type);
                              }

                              original.removePrimaryAddress((String)update.getRemovedObject());
                           }

                           if (original.getPrimaryAddresses() == null || original.getPrimaryAddresses().length == 0) {
                              original._conditionalUnset(update.isUnsetUpdate(), 12);
                           }
                        } else if (prop.equals("RecvTimeout")) {
                           original.setRecvTimeout(proposed.getRecvTimeout());
                           original._conditionalUnset(update.isUnsetUpdate(), 23);
                        } else if (prop.equals("TrustStoreName")) {
                           original.setTrustStoreName(proposed.getTrustStoreName());
                           original._conditionalUnset(update.isUnsetUpdate(), 25);
                        } else if (!prop.equals("TrustStorePassPhrase")) {
                           if (prop.equals("TrustStorePassPhraseEncrypted")) {
                              original.setTrustStorePassPhraseEncrypted(proposed.getTrustStorePassPhraseEncrypted());
                              original._conditionalUnset(update.isUnsetUpdate(), 31);
                           } else if (prop.equals("UserName")) {
                              original.setUserName(proposed.getUserName());
                              original._conditionalUnset(update.isUnsetUpdate(), 16);
                           } else if (!prop.equals("UserPassword")) {
                              if (prop.equals("UserPasswordEncrypted")) {
                                 original.setUserPasswordEncrypted(proposed.getUserPasswordEncrypted());
                                 original._conditionalUnset(update.isUnsetUpdate(), 18);
                              } else if (prop.equals("UserRole")) {
                                 original.setUserRole(proposed.getUserRole());
                                 original._conditionalUnset(update.isUnsetUpdate(), 21);
                              } else if (prop.equals("SecurityContextEnabled")) {
                                 original.setSecurityContextEnabled(proposed.isSecurityContextEnabled());
                                 original._conditionalUnset(update.isUnsetUpdate(), 22);
                              } else {
                                 super.applyPropertyUpdate(event, update);
                              }
                           }
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
            JoltConnectionPoolMBeanImpl copy = (JoltConnectionPoolMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("ApplicationPasswordEncrypted")) && this.bean.isApplicationPasswordEncryptedSet()) {
               o = this.bean.getApplicationPasswordEncrypted();
               copy.setApplicationPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("FailoverAddresses")) && this.bean.isFailoverAddressesSet()) {
               o = this.bean.getFailoverAddresses();
               copy.setFailoverAddresses(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("KeyPassPhraseEncrypted")) && this.bean.isKeyPassPhraseEncryptedSet()) {
               o = this.bean.getKeyPassPhraseEncrypted();
               copy.setKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("KeyStoreName")) && this.bean.isKeyStoreNameSet()) {
               copy.setKeyStoreName(this.bean.getKeyStoreName());
            }

            if ((excludeProps == null || !excludeProps.contains("KeyStorePassPhraseEncrypted")) && this.bean.isKeyStorePassPhraseEncryptedSet()) {
               o = this.bean.getKeyStorePassPhraseEncrypted();
               copy.setKeyStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("MaximumPoolSize")) && this.bean.isMaximumPoolSizeSet()) {
               copy.setMaximumPoolSize(this.bean.getMaximumPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MinimumPoolSize")) && this.bean.isMinimumPoolSizeSet()) {
               copy.setMinimumPoolSize(this.bean.getMinimumPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("PrimaryAddresses")) && this.bean.isPrimaryAddressesSet()) {
               o = this.bean.getPrimaryAddresses();
               copy.setPrimaryAddresses(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("RecvTimeout")) && this.bean.isRecvTimeoutSet()) {
               copy.setRecvTimeout(this.bean.getRecvTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("TrustStoreName")) && this.bean.isTrustStoreNameSet()) {
               copy.setTrustStoreName(this.bean.getTrustStoreName());
            }

            if ((excludeProps == null || !excludeProps.contains("TrustStorePassPhraseEncrypted")) && this.bean.isTrustStorePassPhraseEncryptedSet()) {
               o = this.bean.getTrustStorePassPhraseEncrypted();
               copy.setTrustStorePassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UserName")) && this.bean.isUserNameSet()) {
               copy.setUserName(this.bean.getUserName());
            }

            if ((excludeProps == null || !excludeProps.contains("UserPasswordEncrypted")) && this.bean.isUserPasswordEncryptedSet()) {
               o = this.bean.getUserPasswordEncrypted();
               copy.setUserPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UserRole")) && this.bean.isUserRoleSet()) {
               copy.setUserRole(this.bean.getUserRole());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityContextEnabled")) && this.bean.isSecurityContextEnabledSet()) {
               copy.setSecurityContextEnabled(this.bean.isSecurityContextEnabled());
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

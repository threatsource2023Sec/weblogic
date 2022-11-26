package weblogic.management.security;

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
import weblogic.management.commo.RequiredModelMBeanWrapper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class RDBMSSecurityStoreMBeanImpl extends AbstractCommoConfigurationBean implements RDBMSSecurityStoreMBean, Serializable {
   private String _CompatibilityObjectName;
   private String _ConnectionProperties;
   private String _ConnectionURL;
   private String _DriverName;
   private int _JMSExceptionReconnectAttempts;
   private String _JMSTopic;
   private String _JMSTopicConnectionFactory;
   private String _JNDIPassword;
   private byte[] _JNDIPasswordEncrypted;
   private String _JNDIUsername;
   private String _Name;
   private String _NotificationProperties;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private RealmMBean _Realm;
   private String _Username;
   private transient RDBMSSecurityStoreImpl _customizer;
   private static SchemaHelper2 _schemaHelper;

   public RDBMSSecurityStoreMBeanImpl() {
      try {
         this._customizer = new RDBMSSecurityStoreImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public RDBMSSecurityStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new RDBMSSecurityStoreImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public RDBMSSecurityStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new RDBMSSecurityStoreImpl(new RequiredModelMBeanWrapper(this));
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getUsername() {
      return this._Username;
   }

   public boolean isUsernameInherited() {
      return false;
   }

   public boolean isUsernameSet() {
      return this._isSet(2);
   }

   public void setUsername(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Username", param0);
      LegalChecks.checkNonNull("Username", param0);
      String _oldVal = this._Username;
      this._Username = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getPassword() {
      byte[] bEncrypted = this.getPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("Password", bEncrypted);
   }

   public boolean isPasswordInherited() {
      return false;
   }

   public boolean isPasswordSet() {
      return this.isPasswordEncryptedSet();
   }

   public void setPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setPasswordEncrypted(param0 == null ? null : this._encrypt("Password", param0));
   }

   public byte[] getPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._PasswordEncrypted);
   }

   public String getPasswordEncryptedAsString() {
      byte[] obj = this.getPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isPasswordEncryptedInherited() {
      return false;
   }

   public boolean isPasswordEncryptedSet() {
      return this._isSet(4);
   }

   public void setPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getJNDIUsername() {
      return this._JNDIUsername;
   }

   public boolean isJNDIUsernameInherited() {
      return false;
   }

   public boolean isJNDIUsernameSet() {
      return this._isSet(5);
   }

   public void setJNDIUsername(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JNDIUsername;
      this._JNDIUsername = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getJNDIPassword() {
      byte[] bEncrypted = this.getJNDIPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("JNDIPassword", bEncrypted);
   }

   public boolean isJNDIPasswordInherited() {
      return false;
   }

   public boolean isJNDIPasswordSet() {
      return this.isJNDIPasswordEncryptedSet();
   }

   public void setJNDIPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setJNDIPasswordEncrypted(param0 == null ? null : this._encrypt("JNDIPassword", param0));
   }

   public byte[] getJNDIPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._JNDIPasswordEncrypted);
   }

   public String getJNDIPasswordEncryptedAsString() {
      byte[] obj = this.getJNDIPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isJNDIPasswordEncryptedInherited() {
      return false;
   }

   public boolean isJNDIPasswordEncryptedSet() {
      return this._isSet(7);
   }

   public void setJNDIPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setJNDIPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getConnectionURL() {
      return this._ConnectionURL;
   }

   public boolean isConnectionURLInherited() {
      return false;
   }

   public boolean isConnectionURLSet() {
      return this._isSet(8);
   }

   public void setConnectionURL(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("ConnectionURL", param0);
      LegalChecks.checkNonNull("ConnectionURL", param0);
      String _oldVal = this._ConnectionURL;
      this._ConnectionURL = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getDriverName() {
      return this._DriverName;
   }

   public boolean isDriverNameInherited() {
      return false;
   }

   public boolean isDriverNameSet() {
      return this._isSet(9);
   }

   public void setDriverName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("DriverName", param0);
      LegalChecks.checkNonNull("DriverName", param0);
      String _oldVal = this._DriverName;
      this._DriverName = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getConnectionProperties() {
      return this._ConnectionProperties;
   }

   public boolean isConnectionPropertiesInherited() {
      return false;
   }

   public boolean isConnectionPropertiesSet() {
      return this._isSet(10);
   }

   public void setConnectionProperties(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      RDBMSSecurityStoreValidator.validateProperties(param0);
      String _oldVal = this._ConnectionProperties;
      this._ConnectionProperties = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getJMSTopic() {
      return this._JMSTopic;
   }

   public boolean isJMSTopicInherited() {
      return false;
   }

   public boolean isJMSTopicSet() {
      return this._isSet(11);
   }

   public void setJMSTopic(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JMSTopic;
      this._JMSTopic = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getJMSTopicConnectionFactory() {
      return this._JMSTopicConnectionFactory;
   }

   public boolean isJMSTopicConnectionFactoryInherited() {
      return false;
   }

   public boolean isJMSTopicConnectionFactorySet() {
      return this._isSet(12);
   }

   public void setJMSTopicConnectionFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JMSTopicConnectionFactory;
      this._JMSTopicConnectionFactory = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getJMSExceptionReconnectAttempts() {
      return this._JMSExceptionReconnectAttempts;
   }

   public boolean isJMSExceptionReconnectAttemptsInherited() {
      return false;
   }

   public boolean isJMSExceptionReconnectAttemptsSet() {
      return this._isSet(13);
   }

   public void setJMSExceptionReconnectAttempts(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("JMSExceptionReconnectAttempts", param0, 0);
      int _oldVal = this._JMSExceptionReconnectAttempts;
      this._JMSExceptionReconnectAttempts = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getNotificationProperties() {
      return this._NotificationProperties;
   }

   public boolean isNotificationPropertiesInherited() {
      return false;
   }

   public boolean isNotificationPropertiesSet() {
      return this._isSet(14);
   }

   public void setNotificationProperties(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      RDBMSSecurityStoreValidator.validateProperties(param0);
      String _oldVal = this._NotificationProperties;
      this._NotificationProperties = param0;
      this._postSet(14, _oldVal, param0);
   }

   public RealmMBean getRealm() {
      return this._customizer.getRealm();
   }

   public boolean isRealmInherited() {
      return false;
   }

   public boolean isRealmSet() {
      return this._isSet(15);
   }

   public void setRealm(RealmMBean param0) throws InvalidAttributeValueException {
      this._Realm = param0;
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(16);
   }

   public void setName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getCompatibilityObjectName() {
      return this._customizer.getCompatibilityObjectName();
   }

   public boolean isCompatibilityObjectNameInherited() {
      return false;
   }

   public boolean isCompatibilityObjectNameSet() {
      return this._isSet(17);
   }

   public void setCompatibilityObjectName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CompatibilityObjectName;
      this._CompatibilityObjectName = param0;
      this._postSet(17, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("ConnectionURL", this.isConnectionURLSet());
      LegalChecks.checkIsSet("DriverName", this.isDriverNameSet());
      LegalChecks.checkIsSet("Username", this.isUsernameSet());
   }

   public void setJNDIPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._JNDIPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: JNDIPasswordEncrypted of RDBMSSecurityStoreMBean");
      } else {
         this._getHelper()._clearArray(this._JNDIPasswordEncrypted);
         this._JNDIPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(7, _oldVal, param0);
      }
   }

   public void setPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._PasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of RDBMSSecurityStoreMBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(4, _oldVal, param0);
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

         if (idx == 3) {
            this._markSet(4, false);
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._CompatibilityObjectName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._ConnectionProperties = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._ConnectionURL = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._DriverName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._JMSExceptionReconnectAttempts = 0;
               if (initOne) {
                  break;
               }
            case 11:
               this._JMSTopic = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._JMSTopicConnectionFactory = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._JNDIPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._JNDIPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._JNDIUsername = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._Name = "RDBMSSecurityStore";
               if (initOne) {
                  break;
               }
            case 14:
               this._NotificationProperties = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._Realm = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Username = null;
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
      return "weblogic.management.security.RDBMSSecurityStoreMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 16;
               }
               break;
            case 5:
               if (s.equals("realm")) {
                  return 15;
               }
            case 6:
            case 7:
            case 10:
            case 12:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 22:
            case 24:
            case 26:
            case 27:
            case 29:
            case 30:
            case 31:
            default:
               break;
            case 8:
               if (s.equals("password")) {
                  return 3;
               }

               if (s.equals("username")) {
                  return 2;
               }
               break;
            case 9:
               if (s.equals("jms-topic")) {
                  return 11;
               }
               break;
            case 11:
               if (s.equals("driver-name")) {
                  return 9;
               }
               break;
            case 13:
               if (s.equals("jndi-password")) {
                  return 6;
               }

               if (s.equals("jndi-username")) {
                  return 5;
               }
               break;
            case 14:
               if (s.equals("connection-url")) {
                  return 8;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("connection-properties")) {
                  return 10;
               }
               break;
            case 23:
               if (s.equals("jndi-password-encrypted")) {
                  return 7;
               }

               if (s.equals("notification-properties")) {
                  return 14;
               }
               break;
            case 25:
               if (s.equals("compatibility-object-name")) {
                  return 17;
               }
               break;
            case 28:
               if (s.equals("jms-topic-connection-factory")) {
                  return 12;
               }
               break;
            case 32:
               if (s.equals("jms-exception-reconnect-attempts")) {
                  return 13;
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
               return "username";
            case 3:
               return "password";
            case 4:
               return "password-encrypted";
            case 5:
               return "jndi-username";
            case 6:
               return "jndi-password";
            case 7:
               return "jndi-password-encrypted";
            case 8:
               return "connection-url";
            case 9:
               return "driver-name";
            case 10:
               return "connection-properties";
            case 11:
               return "jms-topic";
            case 12:
               return "jms-topic-connection-factory";
            case 13:
               return "jms-exception-reconnect-attempts";
            case 14:
               return "notification-properties";
            case 15:
               return "realm";
            case 16:
               return "name";
            case 17:
               return "compatibility-object-name";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractCommoConfigurationBean.Helper {
      private RDBMSSecurityStoreMBeanImpl bean;

      protected Helper(RDBMSSecurityStoreMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Username";
            case 3:
               return "Password";
            case 4:
               return "PasswordEncrypted";
            case 5:
               return "JNDIUsername";
            case 6:
               return "JNDIPassword";
            case 7:
               return "JNDIPasswordEncrypted";
            case 8:
               return "ConnectionURL";
            case 9:
               return "DriverName";
            case 10:
               return "ConnectionProperties";
            case 11:
               return "JMSTopic";
            case 12:
               return "JMSTopicConnectionFactory";
            case 13:
               return "JMSExceptionReconnectAttempts";
            case 14:
               return "NotificationProperties";
            case 15:
               return "Realm";
            case 16:
               return "Name";
            case 17:
               return "CompatibilityObjectName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CompatibilityObjectName")) {
            return 17;
         } else if (propName.equals("ConnectionProperties")) {
            return 10;
         } else if (propName.equals("ConnectionURL")) {
            return 8;
         } else if (propName.equals("DriverName")) {
            return 9;
         } else if (propName.equals("JMSExceptionReconnectAttempts")) {
            return 13;
         } else if (propName.equals("JMSTopic")) {
            return 11;
         } else if (propName.equals("JMSTopicConnectionFactory")) {
            return 12;
         } else if (propName.equals("JNDIPassword")) {
            return 6;
         } else if (propName.equals("JNDIPasswordEncrypted")) {
            return 7;
         } else if (propName.equals("JNDIUsername")) {
            return 5;
         } else if (propName.equals("Name")) {
            return 16;
         } else if (propName.equals("NotificationProperties")) {
            return 14;
         } else if (propName.equals("Password")) {
            return 3;
         } else if (propName.equals("PasswordEncrypted")) {
            return 4;
         } else if (propName.equals("Realm")) {
            return 15;
         } else {
            return propName.equals("Username") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isCompatibilityObjectNameSet()) {
               buf.append("CompatibilityObjectName");
               buf.append(String.valueOf(this.bean.getCompatibilityObjectName()));
            }

            if (this.bean.isConnectionPropertiesSet()) {
               buf.append("ConnectionProperties");
               buf.append(String.valueOf(this.bean.getConnectionProperties()));
            }

            if (this.bean.isConnectionURLSet()) {
               buf.append("ConnectionURL");
               buf.append(String.valueOf(this.bean.getConnectionURL()));
            }

            if (this.bean.isDriverNameSet()) {
               buf.append("DriverName");
               buf.append(String.valueOf(this.bean.getDriverName()));
            }

            if (this.bean.isJMSExceptionReconnectAttemptsSet()) {
               buf.append("JMSExceptionReconnectAttempts");
               buf.append(String.valueOf(this.bean.getJMSExceptionReconnectAttempts()));
            }

            if (this.bean.isJMSTopicSet()) {
               buf.append("JMSTopic");
               buf.append(String.valueOf(this.bean.getJMSTopic()));
            }

            if (this.bean.isJMSTopicConnectionFactorySet()) {
               buf.append("JMSTopicConnectionFactory");
               buf.append(String.valueOf(this.bean.getJMSTopicConnectionFactory()));
            }

            if (this.bean.isJNDIPasswordSet()) {
               buf.append("JNDIPassword");
               buf.append(String.valueOf(this.bean.getJNDIPassword()));
            }

            if (this.bean.isJNDIPasswordEncryptedSet()) {
               buf.append("JNDIPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getJNDIPasswordEncrypted())));
            }

            if (this.bean.isJNDIUsernameSet()) {
               buf.append("JNDIUsername");
               buf.append(String.valueOf(this.bean.getJNDIUsername()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNotificationPropertiesSet()) {
               buf.append("NotificationProperties");
               buf.append(String.valueOf(this.bean.getNotificationProperties()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isRealmSet()) {
               buf.append("Realm");
               buf.append(String.valueOf(this.bean.getRealm()));
            }

            if (this.bean.isUsernameSet()) {
               buf.append("Username");
               buf.append(String.valueOf(this.bean.getUsername()));
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
            RDBMSSecurityStoreMBeanImpl otherTyped = (RDBMSSecurityStoreMBeanImpl)other;
            this.computeDiff("CompatibilityObjectName", this.bean.getCompatibilityObjectName(), otherTyped.getCompatibilityObjectName(), false);
            this.computeDiff("ConnectionProperties", this.bean.getConnectionProperties(), otherTyped.getConnectionProperties(), false);
            this.computeDiff("ConnectionURL", this.bean.getConnectionURL(), otherTyped.getConnectionURL(), false);
            this.computeDiff("DriverName", this.bean.getDriverName(), otherTyped.getDriverName(), false);
            this.computeDiff("JMSExceptionReconnectAttempts", this.bean.getJMSExceptionReconnectAttempts(), otherTyped.getJMSExceptionReconnectAttempts(), false);
            this.computeDiff("JMSTopic", this.bean.getJMSTopic(), otherTyped.getJMSTopic(), false);
            this.computeDiff("JMSTopicConnectionFactory", this.bean.getJMSTopicConnectionFactory(), otherTyped.getJMSTopicConnectionFactory(), false);
            this.computeDiff("JNDIPasswordEncrypted", this.bean.getJNDIPasswordEncrypted(), otherTyped.getJNDIPasswordEncrypted(), false);
            this.computeDiff("JNDIUsername", this.bean.getJNDIUsername(), otherTyped.getJNDIUsername(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("NotificationProperties", this.bean.getNotificationProperties(), otherTyped.getNotificationProperties(), false);
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), false);
            this.computeDiff("Username", this.bean.getUsername(), otherTyped.getUsername(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            RDBMSSecurityStoreMBeanImpl original = (RDBMSSecurityStoreMBeanImpl)event.getSourceBean();
            RDBMSSecurityStoreMBeanImpl proposed = (RDBMSSecurityStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CompatibilityObjectName")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("ConnectionProperties")) {
                  original.setConnectionProperties(proposed.getConnectionProperties());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ConnectionURL")) {
                  original.setConnectionURL(proposed.getConnectionURL());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("DriverName")) {
                  original.setDriverName(proposed.getDriverName());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("JMSExceptionReconnectAttempts")) {
                  original.setJMSExceptionReconnectAttempts(proposed.getJMSExceptionReconnectAttempts());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("JMSTopic")) {
                  original.setJMSTopic(proposed.getJMSTopic());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("JMSTopicConnectionFactory")) {
                  original.setJMSTopicConnectionFactory(proposed.getJMSTopicConnectionFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (!prop.equals("JNDIPassword")) {
                  if (prop.equals("JNDIPasswordEncrypted")) {
                     original.setJNDIPasswordEncrypted(proposed.getJNDIPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  } else if (prop.equals("JNDIUsername")) {
                     original.setJNDIUsername(proposed.getJNDIUsername());
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("NotificationProperties")) {
                     original.setNotificationProperties(proposed.getNotificationProperties());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (!prop.equals("Password")) {
                     if (prop.equals("PasswordEncrypted")) {
                        original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 4);
                     } else if (!prop.equals("Realm")) {
                        if (prop.equals("Username")) {
                           original.setUsername(proposed.getUsername());
                           original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            RDBMSSecurityStoreMBeanImpl copy = (RDBMSSecurityStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CompatibilityObjectName")) && this.bean.isCompatibilityObjectNameSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionProperties")) && this.bean.isConnectionPropertiesSet()) {
               copy.setConnectionProperties(this.bean.getConnectionProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionURL")) && this.bean.isConnectionURLSet()) {
               copy.setConnectionURL(this.bean.getConnectionURL());
            }

            if ((excludeProps == null || !excludeProps.contains("DriverName")) && this.bean.isDriverNameSet()) {
               copy.setDriverName(this.bean.getDriverName());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSExceptionReconnectAttempts")) && this.bean.isJMSExceptionReconnectAttemptsSet()) {
               copy.setJMSExceptionReconnectAttempts(this.bean.getJMSExceptionReconnectAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSTopic")) && this.bean.isJMSTopicSet()) {
               copy.setJMSTopic(this.bean.getJMSTopic());
            }

            if ((excludeProps == null || !excludeProps.contains("JMSTopicConnectionFactory")) && this.bean.isJMSTopicConnectionFactorySet()) {
               copy.setJMSTopicConnectionFactory(this.bean.getJMSTopicConnectionFactory());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("JNDIPasswordEncrypted")) && this.bean.isJNDIPasswordEncryptedSet()) {
               o = this.bean.getJNDIPasswordEncrypted();
               copy.setJNDIPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("JNDIUsername")) && this.bean.isJNDIUsernameSet()) {
               copy.setJNDIUsername(this.bean.getJNDIUsername());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("NotificationProperties")) && this.bean.isNotificationPropertiesSet()) {
               copy.setNotificationProperties(this.bean.getNotificationProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Username")) && this.bean.isUsernameSet()) {
               copy.setUsername(this.bean.getUsername());
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
         this.inferSubTree(this.bean.getRealm(), clazz, annotation);
      }
   }
}

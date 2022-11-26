package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.diagnostics.descriptor.validation.RESTNotificationBeanValidator;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WLDFRESTNotificationBeanImpl extends WLDFNotificationBeanImpl implements WLDFRESTNotificationBean, Serializable {
   private String _AcceptedResponseType;
   private Properties _CustomNotificationProperties;
   private String _EndpointURL;
   private String _HttpAuthenticationMode;
   private String _HttpAuthenticationPassword;
   private byte[] _HttpAuthenticationPasswordEncrypted;
   private String _HttpAuthenticationUserName;
   private String _RestInvocationMethodType;
   private static SchemaHelper2 _schemaHelper;

   public WLDFRESTNotificationBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFRESTNotificationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFRESTNotificationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEndpointURL() {
      return this._EndpointURL;
   }

   public boolean isEndpointURLInherited() {
      return false;
   }

   public boolean isEndpointURLSet() {
      return this._isSet(4);
   }

   public void setEndpointURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      RESTNotificationBeanValidator.validateEndpointURL(param0);
      String _oldVal = this._EndpointURL;
      this._EndpointURL = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getRestInvocationMethodType() {
      return this._RestInvocationMethodType;
   }

   public boolean isRestInvocationMethodTypeInherited() {
      return false;
   }

   public boolean isRestInvocationMethodTypeSet() {
      return this._isSet(5);
   }

   public void setRestInvocationMethodType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"PUT", "POST"};
      param0 = LegalChecks.checkInEnum("RestInvocationMethodType", param0, _set);
      String _oldVal = this._RestInvocationMethodType;
      this._RestInvocationMethodType = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getAcceptedResponseType() {
      return this._AcceptedResponseType;
   }

   public boolean isAcceptedResponseTypeInherited() {
      return false;
   }

   public boolean isAcceptedResponseTypeSet() {
      return this._isSet(6);
   }

   public void setAcceptedResponseType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"application/json", "text/plain", "text/xml", "text/html"};
      param0 = LegalChecks.checkInEnum("AcceptedResponseType", param0, _set);
      String _oldVal = this._AcceptedResponseType;
      this._AcceptedResponseType = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getHttpAuthenticationMode() {
      return this._HttpAuthenticationMode;
   }

   public boolean isHttpAuthenticationModeInherited() {
      return false;
   }

   public boolean isHttpAuthenticationModeSet() {
      return this._isSet(7);
   }

   public void setHttpAuthenticationMode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"None", "Basic"};
      param0 = LegalChecks.checkInEnum("HttpAuthenticationMode", param0, _set);
      String _oldVal = this._HttpAuthenticationMode;
      this._HttpAuthenticationMode = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getHttpAuthenticationUserName() {
      return this._HttpAuthenticationUserName;
   }

   public boolean isHttpAuthenticationUserNameInherited() {
      return false;
   }

   public boolean isHttpAuthenticationUserNameSet() {
      return this._isSet(8);
   }

   public void setHttpAuthenticationUserName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._HttpAuthenticationUserName;
      this._HttpAuthenticationUserName = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getHttpAuthenticationPassword() {
      byte[] bEncrypted = this.getHttpAuthenticationPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("HttpAuthenticationPassword", bEncrypted);
   }

   public boolean isHttpAuthenticationPasswordInherited() {
      return false;
   }

   public boolean isHttpAuthenticationPasswordSet() {
      return this.isHttpAuthenticationPasswordEncryptedSet();
   }

   public void setHttpAuthenticationPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setHttpAuthenticationPasswordEncrypted(param0 == null ? null : this._encrypt("HttpAuthenticationPassword", param0));
   }

   public byte[] getHttpAuthenticationPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._HttpAuthenticationPasswordEncrypted);
   }

   public String getHttpAuthenticationPasswordEncryptedAsString() {
      byte[] obj = this.getHttpAuthenticationPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isHttpAuthenticationPasswordEncryptedInherited() {
      return false;
   }

   public boolean isHttpAuthenticationPasswordEncryptedSet() {
      return this._isSet(10);
   }

   public void setHttpAuthenticationPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setHttpAuthenticationPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Properties getCustomNotificationProperties() {
      return this._CustomNotificationProperties;
   }

   public String getCustomNotificationPropertiesAsString() {
      return StringHelper.objectToString(this.getCustomNotificationProperties());
   }

   public boolean isCustomNotificationPropertiesInherited() {
      return false;
   }

   public boolean isCustomNotificationPropertiesSet() {
      return this._isSet(11);
   }

   public void setCustomNotificationPropertiesAsString(String param0) {
      try {
         this.setCustomNotificationProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setCustomNotificationProperties(Properties param0) {
      Properties _oldVal = this._CustomNotificationProperties;
      this._CustomNotificationProperties = param0;
      this._postSet(11, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      RESTNotificationBeanValidator.validateRESTNotificationBean(this);
      LegalChecks.checkIsSet("EndpointURL", this.isEndpointURLSet());
   }

   public void setHttpAuthenticationPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._HttpAuthenticationPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: HttpAuthenticationPasswordEncrypted of WLDFRESTNotificationBean");
      } else {
         this._getHelper()._clearArray(this._HttpAuthenticationPasswordEncrypted);
         this._HttpAuthenticationPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(10, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 9) {
            this._markSet(10, false);
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
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._AcceptedResponseType = "application/json";
               if (initOne) {
                  break;
               }
            case 11:
               this._CustomNotificationProperties = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._EndpointURL = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._HttpAuthenticationMode = "None";
               if (initOne) {
                  break;
               }
            case 9:
               this._HttpAuthenticationPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._HttpAuthenticationPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._HttpAuthenticationUserName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._RestInvocationMethodType = "PUT";
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFNotificationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("endpoint-url")) {
                  return 4;
               }
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 23:
            case 25:
            case 26:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            default:
               break;
            case 22:
               if (s.equals("accepted-response-type")) {
                  return 6;
               }
               break;
            case 24:
               if (s.equals("http-authentication-mode")) {
                  return 7;
               }
               break;
            case 27:
               if (s.equals("rest-invocation-method-type")) {
                  return 5;
               }
               break;
            case 28:
               if (s.equals("http-authentication-password")) {
                  return 9;
               }
               break;
            case 29:
               if (s.equals("http-authentication-user-name")) {
                  return 8;
               }
               break;
            case 30:
               if (s.equals("custom-notification-properties")) {
                  return 11;
               }
               break;
            case 38:
               if (s.equals("http-authentication-password-encrypted")) {
                  return 10;
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
            case 4:
               return "endpoint-url";
            case 5:
               return "rest-invocation-method-type";
            case 6:
               return "accepted-response-type";
            case 7:
               return "http-authentication-mode";
            case 8:
               return "http-authentication-user-name";
            case 9:
               return "http-authentication-password";
            case 10:
               return "http-authentication-password-encrypted";
            case 11:
               return "custom-notification-properties";
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

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends WLDFNotificationBeanImpl.Helper {
      private WLDFRESTNotificationBeanImpl bean;

      protected Helper(WLDFRESTNotificationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "EndpointURL";
            case 5:
               return "RestInvocationMethodType";
            case 6:
               return "AcceptedResponseType";
            case 7:
               return "HttpAuthenticationMode";
            case 8:
               return "HttpAuthenticationUserName";
            case 9:
               return "HttpAuthenticationPassword";
            case 10:
               return "HttpAuthenticationPasswordEncrypted";
            case 11:
               return "CustomNotificationProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AcceptedResponseType")) {
            return 6;
         } else if (propName.equals("CustomNotificationProperties")) {
            return 11;
         } else if (propName.equals("EndpointURL")) {
            return 4;
         } else if (propName.equals("HttpAuthenticationMode")) {
            return 7;
         } else if (propName.equals("HttpAuthenticationPassword")) {
            return 9;
         } else if (propName.equals("HttpAuthenticationPasswordEncrypted")) {
            return 10;
         } else if (propName.equals("HttpAuthenticationUserName")) {
            return 8;
         } else {
            return propName.equals("RestInvocationMethodType") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isAcceptedResponseTypeSet()) {
               buf.append("AcceptedResponseType");
               buf.append(String.valueOf(this.bean.getAcceptedResponseType()));
            }

            if (this.bean.isCustomNotificationPropertiesSet()) {
               buf.append("CustomNotificationProperties");
               buf.append(String.valueOf(this.bean.getCustomNotificationProperties()));
            }

            if (this.bean.isEndpointURLSet()) {
               buf.append("EndpointURL");
               buf.append(String.valueOf(this.bean.getEndpointURL()));
            }

            if (this.bean.isHttpAuthenticationModeSet()) {
               buf.append("HttpAuthenticationMode");
               buf.append(String.valueOf(this.bean.getHttpAuthenticationMode()));
            }

            if (this.bean.isHttpAuthenticationPasswordSet()) {
               buf.append("HttpAuthenticationPassword");
               buf.append(String.valueOf(this.bean.getHttpAuthenticationPassword()));
            }

            if (this.bean.isHttpAuthenticationPasswordEncryptedSet()) {
               buf.append("HttpAuthenticationPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getHttpAuthenticationPasswordEncrypted())));
            }

            if (this.bean.isHttpAuthenticationUserNameSet()) {
               buf.append("HttpAuthenticationUserName");
               buf.append(String.valueOf(this.bean.getHttpAuthenticationUserName()));
            }

            if (this.bean.isRestInvocationMethodTypeSet()) {
               buf.append("RestInvocationMethodType");
               buf.append(String.valueOf(this.bean.getRestInvocationMethodType()));
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
            WLDFRESTNotificationBeanImpl otherTyped = (WLDFRESTNotificationBeanImpl)other;
            this.computeDiff("AcceptedResponseType", this.bean.getAcceptedResponseType(), otherTyped.getAcceptedResponseType(), true);
            this.computeDiff("CustomNotificationProperties", this.bean.getCustomNotificationProperties(), otherTyped.getCustomNotificationProperties(), true);
            this.computeDiff("EndpointURL", this.bean.getEndpointURL(), otherTyped.getEndpointURL(), true);
            this.computeDiff("HttpAuthenticationMode", this.bean.getHttpAuthenticationMode(), otherTyped.getHttpAuthenticationMode(), true);
            this.computeDiff("HttpAuthenticationPasswordEncrypted", this.bean.getHttpAuthenticationPasswordEncrypted(), otherTyped.getHttpAuthenticationPasswordEncrypted(), true);
            this.computeDiff("HttpAuthenticationUserName", this.bean.getHttpAuthenticationUserName(), otherTyped.getHttpAuthenticationUserName(), true);
            this.computeDiff("RestInvocationMethodType", this.bean.getRestInvocationMethodType(), otherTyped.getRestInvocationMethodType(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFRESTNotificationBeanImpl original = (WLDFRESTNotificationBeanImpl)event.getSourceBean();
            WLDFRESTNotificationBeanImpl proposed = (WLDFRESTNotificationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AcceptedResponseType")) {
                  original.setAcceptedResponseType(proposed.getAcceptedResponseType());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("CustomNotificationProperties")) {
                  original.setCustomNotificationProperties(proposed.getCustomNotificationProperties() == null ? null : (Properties)proposed.getCustomNotificationProperties().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("EndpointURL")) {
                  original.setEndpointURL(proposed.getEndpointURL());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("HttpAuthenticationMode")) {
                  original.setHttpAuthenticationMode(proposed.getHttpAuthenticationMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (!prop.equals("HttpAuthenticationPassword")) {
                  if (prop.equals("HttpAuthenticationPasswordEncrypted")) {
                     original.setHttpAuthenticationPasswordEncrypted(proposed.getHttpAuthenticationPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("HttpAuthenticationUserName")) {
                     original.setHttpAuthenticationUserName(proposed.getHttpAuthenticationUserName());
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  } else if (prop.equals("RestInvocationMethodType")) {
                     original.setRestInvocationMethodType(proposed.getRestInvocationMethodType());
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            WLDFRESTNotificationBeanImpl copy = (WLDFRESTNotificationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AcceptedResponseType")) && this.bean.isAcceptedResponseTypeSet()) {
               copy.setAcceptedResponseType(this.bean.getAcceptedResponseType());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomNotificationProperties")) && this.bean.isCustomNotificationPropertiesSet()) {
               copy.setCustomNotificationProperties(this.bean.getCustomNotificationProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("EndpointURL")) && this.bean.isEndpointURLSet()) {
               copy.setEndpointURL(this.bean.getEndpointURL());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpAuthenticationMode")) && this.bean.isHttpAuthenticationModeSet()) {
               copy.setHttpAuthenticationMode(this.bean.getHttpAuthenticationMode());
            }

            if ((excludeProps == null || !excludeProps.contains("HttpAuthenticationPasswordEncrypted")) && this.bean.isHttpAuthenticationPasswordEncryptedSet()) {
               Object o = this.bean.getHttpAuthenticationPasswordEncrypted();
               copy.setHttpAuthenticationPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("HttpAuthenticationUserName")) && this.bean.isHttpAuthenticationUserNameSet()) {
               copy.setHttpAuthenticationUserName(this.bean.getHttpAuthenticationUserName());
            }

            if ((excludeProps == null || !excludeProps.contains("RestInvocationMethodType")) && this.bean.isRestInvocationMethodTypeSet()) {
               copy.setRestInvocationMethodType(this.bean.getRestInvocationMethodType());
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

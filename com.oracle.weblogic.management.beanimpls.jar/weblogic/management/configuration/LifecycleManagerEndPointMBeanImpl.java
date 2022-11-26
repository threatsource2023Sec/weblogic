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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class LifecycleManagerEndPointMBeanImpl extends ConfigurationMBeanImpl implements LifecycleManagerEndPointMBean, Serializable {
   private boolean _Enabled;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private String _RuntimeName;
   private String _URL;
   private String _Username;
   private static SchemaHelper2 _schemaHelper;

   public LifecycleManagerEndPointMBeanImpl() {
      this._initializeProperty(-1);
   }

   public LifecycleManagerEndPointMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LifecycleManagerEndPointMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(10);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getURL() {
      return this._URL;
   }

   public boolean isURLInherited() {
      return false;
   }

   public boolean isURLSet() {
      return this._isSet(11);
   }

   public void setURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._URL;
      this._URL = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getRuntimeName() {
      if (!this._isSet(12)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._RuntimeName;
   }

   public boolean isRuntimeNameInherited() {
      return false;
   }

   public boolean isRuntimeNameSet() {
      return this._isSet(12);
   }

   public void setRuntimeName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RuntimeName;
      this._RuntimeName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getUsername() {
      return this._Username;
   }

   public boolean isUsernameInherited() {
      return false;
   }

   public boolean isUsernameSet() {
      return this._isSet(13);
   }

   public void setUsername(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Username;
      this._Username = param0;
      this._postSet(13, _oldVal, param0);
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
      return this._isSet(15);
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

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._PasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of LifecycleManagerEndPointMBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(15, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 14) {
            this._markSet(15, false);
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._RuntimeName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._URL = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._Username = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Enabled = true;
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
      return "LifecycleManagerEndPoint";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Enabled")) {
         boolean oldVal = this._Enabled;
         this._Enabled = (Boolean)v;
         this._postSet(10, oldVal, this._Enabled);
      } else {
         String oldVal;
         if (name.equals("Password")) {
            oldVal = this._Password;
            this._Password = (String)v;
            this._postSet(14, oldVal, this._Password);
         } else if (name.equals("PasswordEncrypted")) {
            byte[] oldVal = this._PasswordEncrypted;
            this._PasswordEncrypted = (byte[])((byte[])v);
            this._postSet(15, oldVal, this._PasswordEncrypted);
         } else if (name.equals("RuntimeName")) {
            oldVal = this._RuntimeName;
            this._RuntimeName = (String)v;
            this._postSet(12, oldVal, this._RuntimeName);
         } else if (name.equals("URL")) {
            oldVal = this._URL;
            this._URL = (String)v;
            this._postSet(11, oldVal, this._URL);
         } else if (name.equals("Username")) {
            oldVal = this._Username;
            this._Username = (String)v;
            this._postSet(13, oldVal, this._Username);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("Enabled")) {
         return new Boolean(this._Enabled);
      } else if (name.equals("Password")) {
         return this._Password;
      } else if (name.equals("PasswordEncrypted")) {
         return this._PasswordEncrypted;
      } else if (name.equals("RuntimeName")) {
         return this._RuntimeName;
      } else if (name.equals("URL")) {
         return this._URL;
      } else {
         return name.equals("Username") ? this._Username : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("url")) {
                  return 11;
               }
               break;
            case 7:
               if (s.equals("enabled")) {
                  return 10;
               }
               break;
            case 8:
               if (s.equals("password")) {
                  return 14;
               }

               if (s.equals("username")) {
                  return 13;
               }
               break;
            case 12:
               if (s.equals("runtime-name")) {
                  return 12;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 15;
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
            case 10:
               return "enabled";
            case 11:
               return "url";
            case 12:
               return "runtime-name";
            case 13:
               return "username";
            case 14:
               return "password";
            case 15:
               return "password-encrypted";
            default:
               return super.getElementName(propIndex);
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private LifecycleManagerEndPointMBeanImpl bean;

      protected Helper(LifecycleManagerEndPointMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Enabled";
            case 11:
               return "URL";
            case 12:
               return "RuntimeName";
            case 13:
               return "Username";
            case 14:
               return "Password";
            case 15:
               return "PasswordEncrypted";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Password")) {
            return 14;
         } else if (propName.equals("PasswordEncrypted")) {
            return 15;
         } else if (propName.equals("RuntimeName")) {
            return 12;
         } else if (propName.equals("URL")) {
            return 11;
         } else if (propName.equals("Username")) {
            return 13;
         } else {
            return propName.equals("Enabled") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isRuntimeNameSet()) {
               buf.append("RuntimeName");
               buf.append(String.valueOf(this.bean.getRuntimeName()));
            }

            if (this.bean.isURLSet()) {
               buf.append("URL");
               buf.append(String.valueOf(this.bean.getURL()));
            }

            if (this.bean.isUsernameSet()) {
               buf.append("Username");
               buf.append(String.valueOf(this.bean.getUsername()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            LifecycleManagerEndPointMBeanImpl otherTyped = (LifecycleManagerEndPointMBeanImpl)other;
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), true);
            this.computeDiff("RuntimeName", this.bean.getRuntimeName(), otherTyped.getRuntimeName(), true);
            this.computeDiff("URL", this.bean.getURL(), otherTyped.getURL(), true);
            this.computeDiff("Username", this.bean.getUsername(), otherTyped.getUsername(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LifecycleManagerEndPointMBeanImpl original = (LifecycleManagerEndPointMBeanImpl)event.getSourceBean();
            LifecycleManagerEndPointMBeanImpl proposed = (LifecycleManagerEndPointMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("Password")) {
                  if (prop.equals("PasswordEncrypted")) {
                     original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("RuntimeName")) {
                     original.setRuntimeName(proposed.getRuntimeName());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("URL")) {
                     original.setURL(proposed.getURL());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("Username")) {
                     original.setUsername(proposed.getUsername());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("Enabled")) {
                     original.setEnabled(proposed.isEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            LifecycleManagerEndPointMBeanImpl copy = (LifecycleManagerEndPointMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("RuntimeName")) && this.bean.isRuntimeNameSet()) {
               copy.setRuntimeName(this.bean.getRuntimeName());
            }

            if ((excludeProps == null || !excludeProps.contains("URL")) && this.bean.isURLSet()) {
               copy.setURL(this.bean.getURL());
            }

            if ((excludeProps == null || !excludeProps.contains("Username")) && this.bean.isUsernameSet()) {
               copy.setUsername(this.bean.getUsername());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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

package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ForeignConnectionFactoryBeanImpl extends ForeignJNDIObjectBeanImpl implements ForeignConnectionFactoryBean, Serializable {
   private String _ConnectionHealthChecking;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private String _Username;
   private static SchemaHelper2 _schemaHelper;

   public ForeignConnectionFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public ForeignConnectionFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ForeignConnectionFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getUsername() {
      return this._Username;
   }

   public boolean isUsernameInherited() {
      return false;
   }

   public boolean isUsernameSet() {
      return this._isSet(5);
   }

   public void setUsername(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Username;
      this._Username = param0;
      this._postSet(5, _oldVal, param0);
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
      return this._isSet(6);
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

   public void setPassword(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      this.setPasswordEncrypted(param0 == null ? null : this._encrypt("Password", param0));
   }

   public String getConnectionHealthChecking() {
      return this._ConnectionHealthChecking;
   }

   public boolean isConnectionHealthCheckingInherited() {
      return false;
   }

   public boolean isConnectionHealthCheckingSet() {
      return this._isSet(8);
   }

   public void setConnectionHealthChecking(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionHealthChecking;
      this._ConnectionHealthChecking = param0;
      this._postSet(8, _oldVal, param0);
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
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of ForeignConnectionFactoryBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(6, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 7) {
            this._markSet(6, false);
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
         idx = 8;
      }

      try {
         switch (idx) {
            case 8:
               this._ConnectionHealthChecking = "enabled";
               if (initOne) {
                  break;
               }
            case 7:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 5:
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends ForeignJNDIObjectBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("password")) {
                  return 7;
               }

               if (s.equals("username")) {
                  return 5;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 6;
               }
               break;
            case 26:
               if (s.equals("connection-health-checking")) {
                  return 8;
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
            case 5:
               return "username";
            case 6:
               return "password-encrypted";
            case 7:
               return "password";
            case 8:
               return "connection-health-checking";
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

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
            default:
               return super.isConfigurable(propIndex);
            case 8:
               return true;
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends ForeignJNDIObjectBeanImpl.Helper {
      private ForeignConnectionFactoryBeanImpl bean;

      protected Helper(ForeignConnectionFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 5:
               return "Username";
            case 6:
               return "PasswordEncrypted";
            case 7:
               return "Password";
            case 8:
               return "ConnectionHealthChecking";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionHealthChecking")) {
            return 8;
         } else if (propName.equals("Password")) {
            return 7;
         } else if (propName.equals("PasswordEncrypted")) {
            return 6;
         } else {
            return propName.equals("Username") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionHealthCheckingSet()) {
               buf.append("ConnectionHealthChecking");
               buf.append(String.valueOf(this.bean.getConnectionHealthChecking()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
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
            ForeignConnectionFactoryBeanImpl otherTyped = (ForeignConnectionFactoryBeanImpl)other;
            this.computeDiff("ConnectionHealthChecking", this.bean.getConnectionHealthChecking(), otherTyped.getConnectionHealthChecking(), false);
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), true);
            this.computeDiff("Username", this.bean.getUsername(), otherTyped.getUsername(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ForeignConnectionFactoryBeanImpl original = (ForeignConnectionFactoryBeanImpl)event.getSourceBean();
            ForeignConnectionFactoryBeanImpl proposed = (ForeignConnectionFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionHealthChecking")) {
                  original.setConnectionHealthChecking(proposed.getConnectionHealthChecking());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (!prop.equals("Password")) {
                  if (prop.equals("PasswordEncrypted")) {
                     original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  } else if (prop.equals("Username")) {
                     original.setUsername(proposed.getUsername());
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
            ForeignConnectionFactoryBeanImpl copy = (ForeignConnectionFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionHealthChecking")) && this.bean.isConnectionHealthCheckingSet()) {
               copy.setConnectionHealthChecking(this.bean.getConnectionHealthChecking());
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
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
      }
   }
}

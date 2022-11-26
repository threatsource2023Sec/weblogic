package kodo.conf.descriptor;

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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WLS81JMXBeanImpl extends LocalJMXBeanImpl implements WLS81JMXBean, Serializable {
   private String _Password;
   private byte[] _PasswordEncrypted;
   private String _URL;
   private String _UserName;
   private static SchemaHelper2 _schemaHelper;

   public WLS81JMXBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLS81JMXBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLS81JMXBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getURL() {
      return this._URL;
   }

   public boolean isURLInherited() {
      return false;
   }

   public boolean isURLSet() {
      return this._isSet(9);
   }

   public void setURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._URL;
      this._URL = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getUserName() {
      return this._UserName;
   }

   public boolean isUserNameInherited() {
      return false;
   }

   public boolean isUserNameSet() {
      return this._isSet(10);
   }

   public void setUserName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UserName;
      this._UserName = param0;
      this._postSet(10, _oldVal, param0);
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

   public void setPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();

      try {
         this.setPasswordEncrypted(param0 == null ? null : this._encrypt("Password", param0));
      } catch (InvalidAttributeValueException var3) {
      }

   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
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
      return this._isSet(12);
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

   public void setPasswordEncrypted(byte[] param0) throws InvalidAttributeValueException {
      byte[] _oldVal = this._PasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of WLS81JMXBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(12, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 11) {
            this._markSet(12, false);
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._URL = "localhost";
               if (initOne) {
                  break;
               }
            case 10:
               this._UserName = "root";
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

   public static class SchemaHelper2 extends LocalJMXBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("URL")) {
                  return 9;
               }
               break;
            case 8:
               if (s.equals("Password")) {
                  return 11;
               }

               if (s.equals("UserName")) {
                  return 10;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new NoneJMXBeanImpl.SchemaHelper2();
            case 1:
               return new LocalJMXBeanImpl.SchemaHelper2();
            case 2:
               return new GUIJMXBeanImpl.SchemaHelper2();
            case 3:
               return new JMX2JMXBeanImpl.SchemaHelper2();
            case 4:
               return new MX4J1JMXBeanImpl.SchemaHelper2();
            case 5:
               return new SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 9:
               return "URL";
            case 10:
               return "UserName";
            case 11:
               return "Password";
            case 12:
               return "password-encrypted";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends LocalJMXBeanImpl.Helper {
      private WLS81JMXBeanImpl bean;

      protected Helper(WLS81JMXBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 9:
               return "URL";
            case 10:
               return "UserName";
            case 11:
               return "Password";
            case 12:
               return "PasswordEncrypted";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Password")) {
            return 11;
         } else if (propName.equals("PasswordEncrypted")) {
            return 12;
         } else if (propName.equals("URL")) {
            return 9;
         } else {
            return propName.equals("UserName") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getGUIJMX() != null) {
            iterators.add(new ArrayIterator(new GUIJMXBean[]{this.bean.getGUIJMX()}));
         }

         if (this.bean.getJMX2JMX() != null) {
            iterators.add(new ArrayIterator(new JMX2JMXBean[]{this.bean.getJMX2JMX()}));
         }

         if (this.bean.getLocalJMX() != null) {
            iterators.add(new ArrayIterator(new LocalJMXBean[]{this.bean.getLocalJMX()}));
         }

         if (this.bean.getMX4J1JMX() != null) {
            iterators.add(new ArrayIterator(new MX4J1JMXBean[]{this.bean.getMX4J1JMX()}));
         }

         if (this.bean.getNoneJMX() != null) {
            iterators.add(new ArrayIterator(new NoneJMXBean[]{this.bean.getNoneJMX()}));
         }

         if (this.bean.getWLS81JMX() != null) {
            iterators.add(new ArrayIterator(new WLS81JMXBean[]{this.bean.getWLS81JMX()}));
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
            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isURLSet()) {
               buf.append("URL");
               buf.append(String.valueOf(this.bean.getURL()));
            }

            if (this.bean.isUserNameSet()) {
               buf.append("UserName");
               buf.append(String.valueOf(this.bean.getUserName()));
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
            WLS81JMXBeanImpl otherTyped = (WLS81JMXBeanImpl)other;
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), false);
            this.computeDiff("URL", this.bean.getURL(), otherTyped.getURL(), false);
            this.computeDiff("UserName", this.bean.getUserName(), otherTyped.getUserName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLS81JMXBeanImpl original = (WLS81JMXBeanImpl)event.getSourceBean();
            WLS81JMXBeanImpl proposed = (WLS81JMXBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("Password")) {
                  if (prop.equals("PasswordEncrypted")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("URL")) {
                     original.setURL(proposed.getURL());
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  } else if (prop.equals("UserName")) {
                     original.setUserName(proposed.getUserName());
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
            WLS81JMXBeanImpl copy = (WLS81JMXBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("URL")) && this.bean.isURLSet()) {
               copy.setURL(this.bean.getURL());
            }

            if ((excludeProps == null || !excludeProps.contains("UserName")) && this.bean.isUserNameSet()) {
               copy.setUserName(this.bean.getUserName());
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

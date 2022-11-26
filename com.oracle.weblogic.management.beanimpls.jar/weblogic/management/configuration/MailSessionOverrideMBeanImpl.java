package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class MailSessionOverrideMBeanImpl extends ConfigurationMBeanImpl implements MailSessionOverrideMBean, Serializable {
   private Properties _Properties;
   private String _SessionPassword;
   private byte[] _SessionPasswordEncrypted;
   private String _SessionUsername;
   private static SchemaHelper2 _schemaHelper;

   public MailSessionOverrideMBeanImpl() {
      this._initializeProperty(-1);
   }

   public MailSessionOverrideMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MailSessionOverrideMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public Properties getProperties() {
      return this._Properties;
   }

   public String getPropertiesAsString() {
      return StringHelper.objectToString(this.getProperties());
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(10);
   }

   public void setPropertiesAsString(String param0) {
      try {
         this.setProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setProperties(Properties param0) throws InvalidAttributeValueException {
      Properties _oldVal = this._Properties;
      this._Properties = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getSessionUsername() {
      return this._SessionUsername;
   }

   public boolean isSessionUsernameInherited() {
      return false;
   }

   public boolean isSessionUsernameSet() {
      return this._isSet(11);
   }

   public void setSessionUsername(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SessionUsername;
      this._SessionUsername = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getSessionPassword() {
      byte[] bEncrypted = this.getSessionPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("SessionPassword", bEncrypted);
   }

   public boolean isSessionPasswordInherited() {
      return false;
   }

   public boolean isSessionPasswordSet() {
      return this.isSessionPasswordEncryptedSet();
   }

   public void setSessionPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setSessionPasswordEncrypted(param0 == null ? null : this._encrypt("SessionPassword", param0));
   }

   public byte[] getSessionPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._SessionPasswordEncrypted);
   }

   public String getSessionPasswordEncryptedAsString() {
      byte[] obj = this.getSessionPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isSessionPasswordEncryptedInherited() {
      return false;
   }

   public boolean isSessionPasswordEncryptedSet() {
      return this._isSet(13);
   }

   public void setSessionPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setSessionPasswordEncrypted(encryptedBytes);
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

   public void setSessionPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._SessionPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: SessionPasswordEncrypted of MailSessionOverrideMBean");
      } else {
         this._getHelper()._clearArray(this._SessionPasswordEncrypted);
         this._SessionPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(13, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 12) {
            this._markSet(13, false);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._Properties = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._SessionPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._SessionPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._SessionUsername = null;
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
      return "MailSessionOverride";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Properties")) {
         Properties oldVal = this._Properties;
         this._Properties = (Properties)v;
         this._postSet(10, oldVal, this._Properties);
      } else {
         String oldVal;
         if (name.equals("SessionPassword")) {
            oldVal = this._SessionPassword;
            this._SessionPassword = (String)v;
            this._postSet(12, oldVal, this._SessionPassword);
         } else if (name.equals("SessionPasswordEncrypted")) {
            byte[] oldVal = this._SessionPasswordEncrypted;
            this._SessionPasswordEncrypted = (byte[])((byte[])v);
            this._postSet(13, oldVal, this._SessionPasswordEncrypted);
         } else if (name.equals("SessionUsername")) {
            oldVal = this._SessionUsername;
            this._SessionUsername = (String)v;
            this._postSet(11, oldVal, this._SessionUsername);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("Properties")) {
         return this._Properties;
      } else if (name.equals("SessionPassword")) {
         return this._SessionPassword;
      } else if (name.equals("SessionPasswordEncrypted")) {
         return this._SessionPasswordEncrypted;
      } else {
         return name.equals("SessionUsername") ? this._SessionUsername : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("properties")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("session-password")) {
                  return 12;
               }

               if (s.equals("session-username")) {
                  return 11;
               }
               break;
            case 26:
               if (s.equals("session-password-encrypted")) {
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
            case 10:
               return "properties";
            case 11:
               return "session-username";
            case 12:
               return "session-password";
            case 13:
               return "session-password-encrypted";
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
      private MailSessionOverrideMBeanImpl bean;

      protected Helper(MailSessionOverrideMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Properties";
            case 11:
               return "SessionUsername";
            case 12:
               return "SessionPassword";
            case 13:
               return "SessionPasswordEncrypted";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Properties")) {
            return 10;
         } else if (propName.equals("SessionPassword")) {
            return 12;
         } else if (propName.equals("SessionPasswordEncrypted")) {
            return 13;
         } else {
            return propName.equals("SessionUsername") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isPropertiesSet()) {
               buf.append("Properties");
               buf.append(String.valueOf(this.bean.getProperties()));
            }

            if (this.bean.isSessionPasswordSet()) {
               buf.append("SessionPassword");
               buf.append(String.valueOf(this.bean.getSessionPassword()));
            }

            if (this.bean.isSessionPasswordEncryptedSet()) {
               buf.append("SessionPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSessionPasswordEncrypted())));
            }

            if (this.bean.isSessionUsernameSet()) {
               buf.append("SessionUsername");
               buf.append(String.valueOf(this.bean.getSessionUsername()));
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
            MailSessionOverrideMBeanImpl otherTyped = (MailSessionOverrideMBeanImpl)other;
            this.addRestartElements("Properties", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), true);
            this.addRestartElements("SessionPasswordEncrypted", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("SessionPasswordEncrypted", this.bean.getSessionPasswordEncrypted(), otherTyped.getSessionPasswordEncrypted(), true);
            this.addRestartElements("SessionUsername", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("SessionUsername", this.bean.getSessionUsername(), otherTyped.getSessionUsername(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MailSessionOverrideMBeanImpl original = (MailSessionOverrideMBeanImpl)event.getSourceBean();
            MailSessionOverrideMBeanImpl proposed = (MailSessionOverrideMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Properties")) {
                  original.setProperties(proposed.getProperties() == null ? null : (Properties)proposed.getProperties().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (!prop.equals("SessionPassword")) {
                  if (prop.equals("SessionPasswordEncrypted")) {
                     original.setSessionPasswordEncrypted(proposed.getSessionPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("SessionUsername")) {
                     original.setSessionUsername(proposed.getSessionUsername());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            MailSessionOverrideMBeanImpl copy = (MailSessionOverrideMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet()) {
               copy.setProperties(this.bean.getProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionPasswordEncrypted")) && this.bean.isSessionPasswordEncryptedSet()) {
               Object o = this.bean.getSessionPasswordEncrypted();
               copy.setSessionPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("SessionUsername")) && this.bean.isSessionUsernameSet()) {
               copy.setSessionUsername(this.bean.getSessionUsername());
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

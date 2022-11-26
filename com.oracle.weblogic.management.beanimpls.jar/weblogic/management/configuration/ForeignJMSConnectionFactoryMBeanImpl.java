package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.ForeignJMSConnectionFactory;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ForeignJMSConnectionFactoryMBeanImpl extends ForeignJNDIObjectMBeanImpl implements ForeignJMSConnectionFactoryMBean, Serializable {
   private String _ConnectionHealthChecking;
   private boolean _DynamicallyCreated;
   private String _LocalJNDIName;
   private String _Name;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private String _RemoteJNDIName;
   private String[] _Tags;
   private String _Username;
   private transient ForeignJMSConnectionFactory _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ForeignJMSConnectionFactoryMBeanImpl() {
      try {
         this._customizer = new ForeignJMSConnectionFactory(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ForeignJMSConnectionFactoryMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ForeignJMSConnectionFactory(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ForeignJMSConnectionFactoryMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ForeignJMSConnectionFactory(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setLocalJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("LocalJNDIName", param0);
      String _oldVal = this.getLocalJNDIName();

      try {
         this._customizer.setLocalJNDIName(param0);
      } catch (InvalidAttributeValueException var4) {
         throw new UndeclaredThrowableException(var4);
      }

      this._postSet(10, _oldVal, param0);
   }

   public String getLocalJNDIName() {
      return this._customizer.getLocalJNDIName();
   }

   public boolean isLocalJNDINameInherited() {
      return false;
   }

   public boolean isLocalJNDINameSet() {
      return this._isSet(10);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setRemoteJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("RemoteJNDIName", param0);
      String _oldVal = this.getRemoteJNDIName();

      try {
         this._customizer.setRemoteJNDIName(param0);
      } catch (InvalidAttributeValueException var4) {
         throw new UndeclaredThrowableException(var4);
      }

      this._postSet(11, _oldVal, param0);
   }

   public String getRemoteJNDIName() {
      return this._customizer.getRemoteJNDIName();
   }

   public boolean isRemoteJNDINameInherited() {
      return false;
   }

   public boolean isRemoteJNDINameSet() {
      return this._isSet(11);
   }

   public void setUsername(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getUsername();

      try {
         this._customizer.setUsername(param0);
      } catch (InvalidAttributeValueException var4) {
         throw new UndeclaredThrowableException(var4);
      }

      this._postSet(12, _oldVal, param0);
   }

   public String getUsername() {
      return this._customizer.getUsername();
   }

   public boolean isUsernameInherited() {
      return false;
   }

   public boolean isUsernameSet() {
      return this._isSet(12);
   }

   public void setPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getPassword();

      try {
         this._customizer.setPassword(param0);
      } catch (InvalidAttributeValueException var4) {
         throw new UndeclaredThrowableException(var4);
      }

      this._postSet(13, _oldVal, param0);
   }

   public String getPassword() {
      return this._customizer.getPassword();
   }

   public boolean isPasswordInherited() {
      return false;
   }

   public boolean isPasswordSet() {
      return this._isSet(13);
   }

   public void setConnectionHealthChecking(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionHealthChecking;
      this._ConnectionHealthChecking = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getConnectionHealthChecking() {
      return this._ConnectionHealthChecking;
   }

   public boolean isConnectionHealthCheckingInherited() {
      return false;
   }

   public boolean isConnectionHealthCheckingSet() {
      return this._isSet(14);
   }

   public byte[] getPasswordEncrypted() {
      return this._PasswordEncrypted;
   }

   public boolean isPasswordEncryptedInherited() {
      return false;
   }

   public boolean isPasswordEncryptedSet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._PasswordEncrypted;
      this._PasswordEncrypted = param0;
      this._postSet(15, _oldVal, param0);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void useDelegates(ForeignConnectionFactoryBean param0) {
      this._customizer.useDelegates(param0);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      this._DynamicallyCreated = param0;
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("LocalJNDIName", this.isLocalJNDINameSet());
      LegalChecks.checkIsSet("RemoteJNDIName", this.isRemoteJNDINameSet());
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
               this._ConnectionHealthChecking = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setLocalJNDIName((String)null);
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 13:
               this._customizer.setPassword((String)null);
               if (initOne) {
                  break;
               }
            case 15:
               this._PasswordEncrypted = new byte[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._customizer.setRemoteJNDIName((String)null);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 12:
               this._customizer.setUsername((String)null);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
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
      return "ForeignJMSConnectionFactory";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ConnectionHealthChecking")) {
         oldVal = this._ConnectionHealthChecking;
         this._ConnectionHealthChecking = (String)v;
         this._postSet(14, oldVal, this._ConnectionHealthChecking);
      } else if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else if (name.equals("LocalJNDIName")) {
         oldVal = this._LocalJNDIName;
         this._LocalJNDIName = (String)v;
         this._postSet(10, oldVal, this._LocalJNDIName);
      } else if (name.equals("Name")) {
         oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("Password")) {
         oldVal = this._Password;
         this._Password = (String)v;
         this._postSet(13, oldVal, this._Password);
      } else if (name.equals("PasswordEncrypted")) {
         byte[] oldVal = this._PasswordEncrypted;
         this._PasswordEncrypted = (byte[])((byte[])v);
         this._postSet(15, oldVal, this._PasswordEncrypted);
      } else if (name.equals("RemoteJNDIName")) {
         oldVal = this._RemoteJNDIName;
         this._RemoteJNDIName = (String)v;
         this._postSet(11, oldVal, this._RemoteJNDIName);
      } else if (name.equals("Tags")) {
         String[] oldVal = this._Tags;
         this._Tags = (String[])((String[])v);
         this._postSet(9, oldVal, this._Tags);
      } else if (name.equals("Username")) {
         oldVal = this._Username;
         this._Username = (String)v;
         this._postSet(12, oldVal, this._Username);
      } else if (name.equals("customizer")) {
         ForeignJMSConnectionFactory oldVal = this._customizer;
         this._customizer = (ForeignJMSConnectionFactory)v;
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ConnectionHealthChecking")) {
         return this._ConnectionHealthChecking;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("LocalJNDIName")) {
         return this._LocalJNDIName;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Password")) {
         return this._Password;
      } else if (name.equals("PasswordEncrypted")) {
         return this._PasswordEncrypted;
      } else if (name.equals("RemoteJNDIName")) {
         return this._RemoteJNDIName;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Username")) {
         return this._Username;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ForeignJNDIObjectMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            default:
               break;
            case 8:
               if (s.equals("password")) {
                  return 13;
               }

               if (s.equals("username")) {
                  return 12;
               }
               break;
            case 15:
               if (s.equals("local-jndi-name")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("remote-jndi-name")) {
                  return 11;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 15;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 26:
               if (s.equals("connection-health-checking")) {
                  return 14;
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
            case 8:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "local-jndi-name";
            case 11:
               return "remote-jndi-name";
            case 12:
               return "username";
            case 13:
               return "password";
            case 14:
               return "connection-health-checking";
            case 15:
               return "password-encrypted";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 15:
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ForeignJNDIObjectMBeanImpl.Helper {
      private ForeignJMSConnectionFactoryMBeanImpl bean;

      protected Helper(ForeignJMSConnectionFactoryMBeanImpl bean) {
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
            case 8:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "LocalJNDIName";
            case 11:
               return "RemoteJNDIName";
            case 12:
               return "Username";
            case 13:
               return "Password";
            case 14:
               return "ConnectionHealthChecking";
            case 15:
               return "PasswordEncrypted";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionHealthChecking")) {
            return 14;
         } else if (propName.equals("LocalJNDIName")) {
            return 10;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Password")) {
            return 13;
         } else if (propName.equals("PasswordEncrypted")) {
            return 15;
         } else if (propName.equals("RemoteJNDIName")) {
            return 11;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Username")) {
            return 12;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
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

            if (this.bean.isLocalJNDINameSet()) {
               buf.append("LocalJNDIName");
               buf.append(String.valueOf(this.bean.getLocalJNDIName()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isRemoteJNDINameSet()) {
               buf.append("RemoteJNDIName");
               buf.append(String.valueOf(this.bean.getRemoteJNDIName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUsernameSet()) {
               buf.append("Username");
               buf.append(String.valueOf(this.bean.getUsername()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
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
            ForeignJMSConnectionFactoryMBeanImpl otherTyped = (ForeignJMSConnectionFactoryMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("ConnectionHealthChecking", this.bean.getConnectionHealthChecking(), otherTyped.getConnectionHealthChecking(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LocalJNDIName", this.bean.getLocalJNDIName(), otherTyped.getLocalJNDIName(), true);
            }

            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Password", this.bean.getPassword(), otherTyped.getPassword(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), true);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("RemoteJNDIName", this.bean.getRemoteJNDIName(), otherTyped.getRemoteJNDIName(), true);
            }

            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("Username", this.bean.getUsername(), otherTyped.getUsername(), true);
            }

         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ForeignJMSConnectionFactoryMBeanImpl original = (ForeignJMSConnectionFactoryMBeanImpl)event.getSourceBean();
            ForeignJMSConnectionFactoryMBeanImpl proposed = (ForeignJMSConnectionFactoryMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionHealthChecking")) {
                  original.setConnectionHealthChecking(proposed.getConnectionHealthChecking());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("LocalJNDIName")) {
                  original.setLocalJNDIName(proposed.getLocalJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Password")) {
                  original.setPassword(proposed.getPassword());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("PasswordEncrypted")) {
                  original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("RemoteJNDIName")) {
                  original.setRemoteJNDIName(proposed.getRemoteJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Tags")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addTag((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTag((String)update.getRemovedObject());
                  }

                  if (original.getTags() == null || original.getTags().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("Username")) {
                  original.setUsername(proposed.getUsername());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (!prop.equals("DynamicallyCreated")) {
                  super.applyPropertyUpdate(event, update);
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
            ForeignJMSConnectionFactoryMBeanImpl copy = (ForeignJMSConnectionFactoryMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("ConnectionHealthChecking")) && this.bean.isConnectionHealthCheckingSet()) {
               copy.setConnectionHealthChecking(this.bean.getConnectionHealthChecking());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LocalJNDIName")) && this.bean.isLocalJNDINameSet()) {
               copy.setLocalJNDIName(this.bean.getLocalJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Password")) && this.bean.isPasswordSet()) {
               copy.setPassword(this.bean.getPassword());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("RemoteJNDIName")) && this.bean.isRemoteJNDINameSet()) {
               copy.setRemoteJNDIName(this.bean.getRemoteJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("Username")) && this.bean.isUsernameSet()) {
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

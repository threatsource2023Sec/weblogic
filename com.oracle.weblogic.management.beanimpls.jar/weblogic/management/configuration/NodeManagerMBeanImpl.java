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
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.NodeManager;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class NodeManagerMBeanImpl extends ConfigurationMBeanImpl implements NodeManagerMBean, Serializable {
   private String _Adapter;
   private String _AdapterName;
   private String _AdapterVersion;
   private boolean _DebugEnabled;
   private boolean _DynamicallyCreated;
   private String[] _InstalledVMMAdapters;
   private String _ListenAddress;
   private int _ListenPort;
   private int _NMSocketCreateTimeoutInMillis;
   private String _NMType;
   private String _Name;
   private String _NodeManagerHome;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private String _ShellCommand;
   private String[] _Tags;
   private String _UserName;
   private transient NodeManager _customizer;
   private static SchemaHelper2 _schemaHelper;

   public NodeManagerMBeanImpl() {
      try {
         this._customizer = new NodeManager(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public NodeManagerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new NodeManager(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public NodeManagerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new NodeManager(this);
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

   public void setNMType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"SSH", "RSH", "Plain", "SSL", "ssh", "rsh", "ssl", "plain"};
      param0 = LegalChecks.checkInEnum("NMType", param0, _set);
      String _oldVal = this._NMType;
      this._NMType = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getNMType() {
      return this._NMType;
   }

   public boolean isNMTypeInherited() {
      return false;
   }

   public boolean isNMTypeSet() {
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

   public String getListenAddress() {
      return this._ListenAddress;
   }

   public boolean isListenAddressInherited() {
      return false;
   }

   public boolean isListenAddressSet() {
      return this._isSet(11);
   }

   public void setListenAddress(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ListenAddress;
      this._ListenAddress = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getListenPort() {
      return this._ListenPort;
   }

   public boolean isListenPortInherited() {
      return false;
   }

   public boolean isListenPortSet() {
      return this._isSet(12);
   }

   public void setListenPort(int param0) {
      LegalChecks.checkInRange("ListenPort", (long)param0, 1L, 65534L);
      int _oldVal = this._ListenPort;
      this._ListenPort = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isDebugEnabled() {
      return this._DebugEnabled;
   }

   public boolean isDebugEnabledInherited() {
      return false;
   }

   public boolean isDebugEnabledSet() {
      return this._isSet(13);
   }

   public void setDebugEnabled(boolean param0) {
      boolean _oldVal = this._DebugEnabled;
      this._DebugEnabled = param0;
      this._postSet(13, _oldVal, param0);
   }

   public void setShellCommand(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ShellCommand;
      this._ShellCommand = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getShellCommand() {
      return this._ShellCommand;
   }

   public boolean isShellCommandInherited() {
      return false;
   }

   public boolean isShellCommandSet() {
      return this._isSet(14);
   }

   public void setNodeManagerHome(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NodeManagerHome;
      this._NodeManagerHome = param0;
      this._postSet(15, _oldVal, param0);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public String getNodeManagerHome() {
      return this._NodeManagerHome;
   }

   public boolean isNodeManagerHomeInherited() {
      return false;
   }

   public boolean isNodeManagerHomeSet() {
      return this._isSet(15);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setAdapter(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Adapter;
      this._Adapter = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getAdapter() {
      return this._Adapter;
   }

   public boolean isAdapterInherited() {
      return false;
   }

   public boolean isAdapterSet() {
      return this._isSet(16);
   }

   public void setAdapterName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AdapterName;
      this._AdapterName = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getAdapterName() {
      return this._AdapterName;
   }

   public boolean isAdapterNameInherited() {
      return false;
   }

   public boolean isAdapterNameSet() {
      return this._isSet(17);
   }

   public void setAdapterVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AdapterVersion;
      this._AdapterVersion = param0;
      this._postSet(18, _oldVal, param0);
   }

   public String getAdapterVersion() {
      return this._AdapterVersion;
   }

   public boolean isAdapterVersionInherited() {
      return false;
   }

   public boolean isAdapterVersionSet() {
      return this._isSet(18);
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

   public void setUserName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UserName;
      this._UserName = param0;
      this._postSet(19, _oldVal, param0);
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public String getUserName() {
      return this._UserName;
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public boolean isUserNameInherited() {
      return false;
   }

   public boolean isUserNameSet() {
      return this._isSet(19);
   }

   public void setPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setPasswordEncrypted(param0 == null ? null : this._encrypt("Password", param0));
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
      return this._isSet(21);
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

   public String[] getInstalledVMMAdapters() {
      return this._InstalledVMMAdapters;
   }

   public boolean isInstalledVMMAdaptersInherited() {
      return false;
   }

   public boolean isInstalledVMMAdaptersSet() {
      return this._isSet(22);
   }

   public void setInstalledVMMAdapters(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._InstalledVMMAdapters;
      this._InstalledVMMAdapters = param0;
      this._postSet(22, _oldVal, param0);
   }

   public int getNMSocketCreateTimeoutInMillis() {
      return this._NMSocketCreateTimeoutInMillis;
   }

   public boolean isNMSocketCreateTimeoutInMillisInherited() {
      return false;
   }

   public boolean isNMSocketCreateTimeoutInMillisSet() {
      return this._isSet(23);
   }

   public void setNMSocketCreateTimeoutInMillis(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkMin("NMSocketCreateTimeoutInMillis", param0, 0);
      int _oldVal = this._NMSocketCreateTimeoutInMillis;
      this._NMSocketCreateTimeoutInMillis = param0;
      this._postSet(23, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._PasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of NodeManagerMBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(21, _oldVal, param0);
      }
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
         if (idx == 20) {
            this._markSet(21, false);
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
         idx = 16;
      }

      try {
         switch (idx) {
            case 16:
               this._Adapter = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._AdapterName = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._AdapterVersion = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._InstalledVMMAdapters = new String[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._ListenAddress = "localhost";
               if (initOne) {
                  break;
               }
            case 12:
               this._ListenPort = 5556;
               if (initOne) {
                  break;
               }
            case 23:
               this._NMSocketCreateTimeoutInMillis = 180000;
               if (initOne) {
                  break;
               }
            case 10:
               this._NMType = "SSL";
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 15:
               this._NodeManagerHome = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._ShellCommand = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 19:
               this._UserName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._DebugEnabled = false;
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
      return "NodeManager";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Adapter")) {
         oldVal = this._Adapter;
         this._Adapter = (String)v;
         this._postSet(16, oldVal, this._Adapter);
      } else if (name.equals("AdapterName")) {
         oldVal = this._AdapterName;
         this._AdapterName = (String)v;
         this._postSet(17, oldVal, this._AdapterName);
      } else if (name.equals("AdapterVersion")) {
         oldVal = this._AdapterVersion;
         this._AdapterVersion = (String)v;
         this._postSet(18, oldVal, this._AdapterVersion);
      } else {
         boolean oldVal;
         if (name.equals("DebugEnabled")) {
            oldVal = this._DebugEnabled;
            this._DebugEnabled = (Boolean)v;
            this._postSet(13, oldVal, this._DebugEnabled);
         } else if (name.equals("DynamicallyCreated")) {
            oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else {
            String[] oldVal;
            if (name.equals("InstalledVMMAdapters")) {
               oldVal = this._InstalledVMMAdapters;
               this._InstalledVMMAdapters = (String[])((String[])v);
               this._postSet(22, oldVal, this._InstalledVMMAdapters);
            } else if (name.equals("ListenAddress")) {
               oldVal = this._ListenAddress;
               this._ListenAddress = (String)v;
               this._postSet(11, oldVal, this._ListenAddress);
            } else {
               int oldVal;
               if (name.equals("ListenPort")) {
                  oldVal = this._ListenPort;
                  this._ListenPort = (Integer)v;
                  this._postSet(12, oldVal, this._ListenPort);
               } else if (name.equals("NMSocketCreateTimeoutInMillis")) {
                  oldVal = this._NMSocketCreateTimeoutInMillis;
                  this._NMSocketCreateTimeoutInMillis = (Integer)v;
                  this._postSet(23, oldVal, this._NMSocketCreateTimeoutInMillis);
               } else if (name.equals("NMType")) {
                  oldVal = this._NMType;
                  this._NMType = (String)v;
                  this._postSet(10, oldVal, this._NMType);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("NodeManagerHome")) {
                  oldVal = this._NodeManagerHome;
                  this._NodeManagerHome = (String)v;
                  this._postSet(15, oldVal, this._NodeManagerHome);
               } else if (name.equals("Password")) {
                  oldVal = this._Password;
                  this._Password = (String)v;
                  this._postSet(20, oldVal, this._Password);
               } else if (name.equals("PasswordEncrypted")) {
                  byte[] oldVal = this._PasswordEncrypted;
                  this._PasswordEncrypted = (byte[])((byte[])v);
                  this._postSet(21, oldVal, this._PasswordEncrypted);
               } else if (name.equals("ShellCommand")) {
                  oldVal = this._ShellCommand;
                  this._ShellCommand = (String)v;
                  this._postSet(14, oldVal, this._ShellCommand);
               } else if (name.equals("Tags")) {
                  oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("UserName")) {
                  oldVal = this._UserName;
                  this._UserName = (String)v;
                  this._postSet(19, oldVal, this._UserName);
               } else if (name.equals("customizer")) {
                  NodeManager oldVal = this._customizer;
                  this._customizer = (NodeManager)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("Adapter")) {
         return this._Adapter;
      } else if (name.equals("AdapterName")) {
         return this._AdapterName;
      } else if (name.equals("AdapterVersion")) {
         return this._AdapterVersion;
      } else if (name.equals("DebugEnabled")) {
         return new Boolean(this._DebugEnabled);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("InstalledVMMAdapters")) {
         return this._InstalledVMMAdapters;
      } else if (name.equals("ListenAddress")) {
         return this._ListenAddress;
      } else if (name.equals("ListenPort")) {
         return new Integer(this._ListenPort);
      } else if (name.equals("NMSocketCreateTimeoutInMillis")) {
         return new Integer(this._NMSocketCreateTimeoutInMillis);
      } else if (name.equals("NMType")) {
         return this._NMType;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NodeManagerHome")) {
         return this._NodeManagerHome;
      } else if (name.equals("Password")) {
         return this._Password;
      } else if (name.equals("PasswordEncrypted")) {
         return this._PasswordEncrypted;
      } else if (name.equals("ShellCommand")) {
         return this._ShellCommand;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UserName")) {
         return this._UserName;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 10:
            case 16:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            default:
               break;
            case 7:
               if (s.equals("adapter")) {
                  return 16;
               }

               if (s.equals("nm-type")) {
                  return 10;
               }
               break;
            case 8:
               if (s.equals("password")) {
                  return 20;
               }
               break;
            case 9:
               if (s.equals("user-name")) {
                  return 19;
               }
               break;
            case 11:
               if (s.equals("listen-port")) {
                  return 12;
               }
               break;
            case 12:
               if (s.equals("adapter-name")) {
                  return 17;
               }
               break;
            case 13:
               if (s.equals("shell-command")) {
                  return 14;
               }

               if (s.equals("debug-enabled")) {
                  return 13;
               }
               break;
            case 14:
               if (s.equals("listen-address")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("adapter-version")) {
                  return 18;
               }
               break;
            case 17:
               if (s.equals("node-manager-home")) {
                  return 15;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 21;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("installedvmm-adapter")) {
                  return 22;
               }
               break;
            case 34:
               if (s.equals("nm-socket-create-timeout-in-millis")) {
                  return 23;
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
               return "nm-type";
            case 11:
               return "listen-address";
            case 12:
               return "listen-port";
            case 13:
               return "debug-enabled";
            case 14:
               return "shell-command";
            case 15:
               return "node-manager-home";
            case 16:
               return "adapter";
            case 17:
               return "adapter-name";
            case 18:
               return "adapter-version";
            case 19:
               return "user-name";
            case 20:
               return "password";
            case 21:
               return "password-encrypted";
            case 22:
               return "installedvmm-adapter";
            case 23:
               return "nm-socket-create-timeout-in-millis";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 22:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private NodeManagerMBeanImpl bean;

      protected Helper(NodeManagerMBeanImpl bean) {
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
               return "NMType";
            case 11:
               return "ListenAddress";
            case 12:
               return "ListenPort";
            case 13:
               return "DebugEnabled";
            case 14:
               return "ShellCommand";
            case 15:
               return "NodeManagerHome";
            case 16:
               return "Adapter";
            case 17:
               return "AdapterName";
            case 18:
               return "AdapterVersion";
            case 19:
               return "UserName";
            case 20:
               return "Password";
            case 21:
               return "PasswordEncrypted";
            case 22:
               return "InstalledVMMAdapters";
            case 23:
               return "NMSocketCreateTimeoutInMillis";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Adapter")) {
            return 16;
         } else if (propName.equals("AdapterName")) {
            return 17;
         } else if (propName.equals("AdapterVersion")) {
            return 18;
         } else if (propName.equals("InstalledVMMAdapters")) {
            return 22;
         } else if (propName.equals("ListenAddress")) {
            return 11;
         } else if (propName.equals("ListenPort")) {
            return 12;
         } else if (propName.equals("NMSocketCreateTimeoutInMillis")) {
            return 23;
         } else if (propName.equals("NMType")) {
            return 10;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("NodeManagerHome")) {
            return 15;
         } else if (propName.equals("Password")) {
            return 20;
         } else if (propName.equals("PasswordEncrypted")) {
            return 21;
         } else if (propName.equals("ShellCommand")) {
            return 14;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UserName")) {
            return 19;
         } else if (propName.equals("DebugEnabled")) {
            return 13;
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
            if (this.bean.isAdapterSet()) {
               buf.append("Adapter");
               buf.append(String.valueOf(this.bean.getAdapter()));
            }

            if (this.bean.isAdapterNameSet()) {
               buf.append("AdapterName");
               buf.append(String.valueOf(this.bean.getAdapterName()));
            }

            if (this.bean.isAdapterVersionSet()) {
               buf.append("AdapterVersion");
               buf.append(String.valueOf(this.bean.getAdapterVersion()));
            }

            if (this.bean.isInstalledVMMAdaptersSet()) {
               buf.append("InstalledVMMAdapters");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getInstalledVMMAdapters())));
            }

            if (this.bean.isListenAddressSet()) {
               buf.append("ListenAddress");
               buf.append(String.valueOf(this.bean.getListenAddress()));
            }

            if (this.bean.isListenPortSet()) {
               buf.append("ListenPort");
               buf.append(String.valueOf(this.bean.getListenPort()));
            }

            if (this.bean.isNMSocketCreateTimeoutInMillisSet()) {
               buf.append("NMSocketCreateTimeoutInMillis");
               buf.append(String.valueOf(this.bean.getNMSocketCreateTimeoutInMillis()));
            }

            if (this.bean.isNMTypeSet()) {
               buf.append("NMType");
               buf.append(String.valueOf(this.bean.getNMType()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNodeManagerHomeSet()) {
               buf.append("NodeManagerHome");
               buf.append(String.valueOf(this.bean.getNodeManagerHome()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isShellCommandSet()) {
               buf.append("ShellCommand");
               buf.append(String.valueOf(this.bean.getShellCommand()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUserNameSet()) {
               buf.append("UserName");
               buf.append(String.valueOf(this.bean.getUserName()));
            }

            if (this.bean.isDebugEnabledSet()) {
               buf.append("DebugEnabled");
               buf.append(String.valueOf(this.bean.isDebugEnabled()));
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
            NodeManagerMBeanImpl otherTyped = (NodeManagerMBeanImpl)other;
            this.computeDiff("Adapter", this.bean.getAdapter(), otherTyped.getAdapter(), false);
            this.computeDiff("AdapterName", this.bean.getAdapterName(), otherTyped.getAdapterName(), false);
            this.computeDiff("AdapterVersion", this.bean.getAdapterVersion(), otherTyped.getAdapterVersion(), false);
            this.computeDiff("InstalledVMMAdapters", this.bean.getInstalledVMMAdapters(), otherTyped.getInstalledVMMAdapters(), false);
            this.computeDiff("ListenAddress", this.bean.getListenAddress(), otherTyped.getListenAddress(), true);
            this.computeDiff("ListenPort", this.bean.getListenPort(), otherTyped.getListenPort(), true);
            this.computeDiff("NMSocketCreateTimeoutInMillis", this.bean.getNMSocketCreateTimeoutInMillis(), otherTyped.getNMSocketCreateTimeoutInMillis(), false);
            this.computeDiff("NMType", this.bean.getNMType(), otherTyped.getNMType(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("NodeManagerHome", this.bean.getNodeManagerHome(), otherTyped.getNodeManagerHome(), false);
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), false);
            this.computeDiff("ShellCommand", this.bean.getShellCommand(), otherTyped.getShellCommand(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UserName", this.bean.getUserName(), otherTyped.getUserName(), true);
            this.computeDiff("DebugEnabled", this.bean.isDebugEnabled(), otherTyped.isDebugEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            NodeManagerMBeanImpl original = (NodeManagerMBeanImpl)event.getSourceBean();
            NodeManagerMBeanImpl proposed = (NodeManagerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Adapter")) {
                  original.setAdapter(proposed.getAdapter());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("AdapterName")) {
                  original.setAdapterName(proposed.getAdapterName());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("AdapterVersion")) {
                  original.setAdapterVersion(proposed.getAdapterVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("InstalledVMMAdapters")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("ListenAddress")) {
                  original.setListenAddress(proposed.getListenAddress());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("ListenPort")) {
                  original.setListenPort(proposed.getListenPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("NMSocketCreateTimeoutInMillis")) {
                  original.setNMSocketCreateTimeoutInMillis(proposed.getNMSocketCreateTimeoutInMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("NMType")) {
                  original.setNMType(proposed.getNMType());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("NodeManagerHome")) {
                  original.setNodeManagerHome(proposed.getNodeManagerHome());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (!prop.equals("Password")) {
                  if (prop.equals("PasswordEncrypted")) {
                     original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 21);
                  } else if (prop.equals("ShellCommand")) {
                     original.setShellCommand(proposed.getShellCommand());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
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
                  } else if (prop.equals("UserName")) {
                     original.setUserName(proposed.getUserName());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("DebugEnabled")) {
                     original.setDebugEnabled(proposed.isDebugEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (!prop.equals("DynamicallyCreated")) {
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
            NodeManagerMBeanImpl copy = (NodeManagerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Adapter")) && this.bean.isAdapterSet()) {
               copy.setAdapter(this.bean.getAdapter());
            }

            if ((excludeProps == null || !excludeProps.contains("AdapterName")) && this.bean.isAdapterNameSet()) {
               copy.setAdapterName(this.bean.getAdapterName());
            }

            if ((excludeProps == null || !excludeProps.contains("AdapterVersion")) && this.bean.isAdapterVersionSet()) {
               copy.setAdapterVersion(this.bean.getAdapterVersion());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("InstalledVMMAdapters")) && this.bean.isInstalledVMMAdaptersSet()) {
               o = this.bean.getInstalledVMMAdapters();
               copy.setInstalledVMMAdapters(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ListenAddress")) && this.bean.isListenAddressSet()) {
               copy.setListenAddress(this.bean.getListenAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("ListenPort")) && this.bean.isListenPortSet()) {
               copy.setListenPort(this.bean.getListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("NMSocketCreateTimeoutInMillis")) && this.bean.isNMSocketCreateTimeoutInMillisSet()) {
               copy.setNMSocketCreateTimeoutInMillis(this.bean.getNMSocketCreateTimeoutInMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("NMType")) && this.bean.isNMTypeSet()) {
               copy.setNMType(this.bean.getNMType());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("NodeManagerHome")) && this.bean.isNodeManagerHomeSet()) {
               copy.setNodeManagerHome(this.bean.getNodeManagerHome());
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ShellCommand")) && this.bean.isShellCommandSet()) {
               copy.setShellCommand(this.bean.getShellCommand());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UserName")) && this.bean.isUserNameSet()) {
               copy.setUserName(this.bean.getUserName());
            }

            if ((excludeProps == null || !excludeProps.contains("DebugEnabled")) && this.bean.isDebugEnabledSet()) {
               copy.setDebugEnabled(this.bean.isDebugEnabled());
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

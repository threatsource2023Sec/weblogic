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
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.JMSFileStore;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class JMSFileStoreMBeanImpl extends JMSStoreMBeanImpl implements JMSFileStoreMBean, Serializable {
   private int _BlockSize;
   private String _CacheDirectory;
   private FileStoreMBean _DelegatedBean;
   private JMSServerMBean _DelegatedJMSServer;
   private String _Directory;
   private boolean _DynamicallyCreated;
   private boolean _FileLockingEnabled;
   private long _InitialSize;
   private int _IoBufferSize;
   private long _MaxFileSize;
   private int _MaxWindowBufferSize;
   private int _MinWindowBufferSize;
   private String _Name;
   private String _SynchronousWritePolicy;
   private String[] _Tags;
   private transient JMSFileStore _customizer;
   private static SchemaHelper2 _schemaHelper;

   public JMSFileStoreMBeanImpl() {
      try {
         this._customizer = new JMSFileStore(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JMSFileStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new JMSFileStore(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JMSFileStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new JMSFileStore(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getDirectory() {
      return this._customizer.getDirectory();
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

   public boolean isDirectoryInherited() {
      return false;
   }

   public boolean isDirectorySet() {
      return this._isSet(11);
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setDelegatedBean(FileStoreMBean param0) {
      this._customizer.setDelegatedBean(param0);
   }

   public FileStoreMBean getDelegatedBean() {
      return this._customizer.getDelegatedBean();
   }

   public boolean isDelegatedBeanInherited() {
      return false;
   }

   public boolean isDelegatedBeanSet() {
      return this._isSet(21);
   }

   public void setDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getDirectory();
      this._customizer.setDirectory(param0);
      this._postSet(11, _oldVal, param0);
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

   public String getSynchronousWritePolicy() {
      return this._customizer.getSynchronousWritePolicy();
   }

   public boolean isSynchronousWritePolicyInherited() {
      return false;
   }

   public boolean isSynchronousWritePolicySet() {
      return this._isSet(12);
   }

   public void setDelegatedJMSServer(JMSServerMBean param0) {
      this._customizer.setDelegatedJMSServer(param0);
   }

   public JMSServerMBean getDelegatedJMSServer() {
      return this._customizer.getDelegatedJMSServer();
   }

   public boolean isDelegatedJMSServerInherited() {
      return false;
   }

   public boolean isDelegatedJMSServerSet() {
      return this._isSet(22);
   }

   public void setSynchronousWritePolicy(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Disabled", "Cache-Flush", "Direct-Write", "Direct-Write-With-Cache"};
      param0 = LegalChecks.checkInEnum("SynchronousWritePolicy", param0, _set);
      String _oldVal = this.getSynchronousWritePolicy();
      this._customizer.setSynchronousWritePolicy(param0);
      this._postSet(12, _oldVal, param0);
   }

   public String getCacheDirectory() {
      return this._CacheDirectory;
   }

   public boolean isCacheDirectoryInherited() {
      return false;
   }

   public boolean isCacheDirectorySet() {
      return this._isSet(13);
   }

   public void setCacheDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CacheDirectory;
      this._CacheDirectory = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getMinWindowBufferSize() {
      return this._MinWindowBufferSize;
   }

   public boolean isMinWindowBufferSizeInherited() {
      return false;
   }

   public boolean isMinWindowBufferSizeSet() {
      return this._isSet(14);
   }

   public void setMinWindowBufferSize(int param0) {
      LegalChecks.checkInRange("MinWindowBufferSize", (long)param0, -1L, 1073741824L);
      int _oldVal = this._MinWindowBufferSize;
      this._MinWindowBufferSize = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getMaxWindowBufferSize() {
      return this._MaxWindowBufferSize;
   }

   public boolean isMaxWindowBufferSizeInherited() {
      return false;
   }

   public boolean isMaxWindowBufferSizeSet() {
      return this._isSet(15);
   }

   public void setMaxWindowBufferSize(int param0) {
      LegalChecks.checkInRange("MaxWindowBufferSize", (long)param0, -1L, 1073741824L);
      int _oldVal = this._MaxWindowBufferSize;
      this._MaxWindowBufferSize = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getIoBufferSize() {
      return this._IoBufferSize;
   }

   public boolean isIoBufferSizeInherited() {
      return false;
   }

   public boolean isIoBufferSizeSet() {
      return this._isSet(16);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setIoBufferSize(int param0) {
      LegalChecks.checkInRange("IoBufferSize", (long)param0, -1L, 67108864L);
      int _oldVal = this._IoBufferSize;
      this._IoBufferSize = param0;
      this._postSet(16, _oldVal, param0);
   }

   public long getMaxFileSize() {
      return this._MaxFileSize;
   }

   public boolean isMaxFileSizeInherited() {
      return false;
   }

   public boolean isMaxFileSizeSet() {
      return this._isSet(17);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setMaxFileSize(long param0) {
      LegalChecks.checkInRange("MaxFileSize", param0, 1048576L, 2139095040L);
      long _oldVal = this._MaxFileSize;
      this._MaxFileSize = param0;
      this._postSet(17, _oldVal, param0);
   }

   public int getBlockSize() {
      return this._BlockSize;
   }

   public boolean isBlockSizeInherited() {
      return false;
   }

   public boolean isBlockSizeSet() {
      return this._isSet(18);
   }

   public void setBlockSize(int param0) {
      LegalChecks.checkInRange("BlockSize", (long)param0, -1L, 8192L);
      int _oldVal = this._BlockSize;
      this._BlockSize = param0;
      this._postSet(18, _oldVal, param0);
   }

   public long getInitialSize() {
      return this._InitialSize;
   }

   public boolean isInitialSizeInherited() {
      return false;
   }

   public boolean isInitialSizeSet() {
      return this._isSet(19);
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

   public void setInitialSize(long param0) {
      LegalChecks.checkMin("InitialSize", param0, 0L);
      long _oldVal = this._InitialSize;
      this._InitialSize = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean isFileLockingEnabled() {
      return this._FileLockingEnabled;
   }

   public boolean isFileLockingEnabledInherited() {
      return false;
   }

   public boolean isFileLockingEnabledSet() {
      return this._isSet(20);
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

   public void setFileLockingEnabled(boolean param0) {
      boolean _oldVal = this._FileLockingEnabled;
      this._FileLockingEnabled = param0;
      this._postSet(20, _oldVal, param0);
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
         idx = 18;
      }

      try {
         switch (idx) {
            case 18:
               this._BlockSize = -1;
               if (initOne) {
                  break;
               }
            case 13:
               this._CacheDirectory = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._customizer.setDelegatedBean((FileStoreMBean)null);
               if (initOne) {
                  break;
               }
            case 22:
               this._customizer.setDelegatedJMSServer((JMSServerMBean)null);
               if (initOne) {
                  break;
               }
            case 11:
               this._customizer.setDirectory((String)null);
               if (initOne) {
                  break;
               }
            case 19:
               this._InitialSize = 0L;
               if (initOne) {
                  break;
               }
            case 16:
               this._IoBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 17:
               this._MaxFileSize = 1342177280L;
               if (initOne) {
                  break;
               }
            case 15:
               this._MaxWindowBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 14:
               this._MinWindowBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 12:
               this._customizer.setSynchronousWritePolicy("Direct-Write");
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._FileLockingEnabled = true;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
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
      return "JMSFileStore";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("BlockSize")) {
         oldVal = this._BlockSize;
         this._BlockSize = (Integer)v;
         this._postSet(18, oldVal, this._BlockSize);
      } else {
         String oldVal;
         if (name.equals("CacheDirectory")) {
            oldVal = this._CacheDirectory;
            this._CacheDirectory = (String)v;
            this._postSet(13, oldVal, this._CacheDirectory);
         } else if (name.equals("DelegatedBean")) {
            FileStoreMBean oldVal = this._DelegatedBean;
            this._DelegatedBean = (FileStoreMBean)v;
            this._postSet(21, oldVal, this._DelegatedBean);
         } else if (name.equals("DelegatedJMSServer")) {
            JMSServerMBean oldVal = this._DelegatedJMSServer;
            this._DelegatedJMSServer = (JMSServerMBean)v;
            this._postSet(22, oldVal, this._DelegatedJMSServer);
         } else if (name.equals("Directory")) {
            oldVal = this._Directory;
            this._Directory = (String)v;
            this._postSet(11, oldVal, this._Directory);
         } else {
            boolean oldVal;
            if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("FileLockingEnabled")) {
               oldVal = this._FileLockingEnabled;
               this._FileLockingEnabled = (Boolean)v;
               this._postSet(20, oldVal, this._FileLockingEnabled);
            } else {
               long oldVal;
               if (name.equals("InitialSize")) {
                  oldVal = this._InitialSize;
                  this._InitialSize = (Long)v;
                  this._postSet(19, oldVal, this._InitialSize);
               } else if (name.equals("IoBufferSize")) {
                  oldVal = this._IoBufferSize;
                  this._IoBufferSize = (Integer)v;
                  this._postSet(16, oldVal, this._IoBufferSize);
               } else if (name.equals("MaxFileSize")) {
                  oldVal = this._MaxFileSize;
                  this._MaxFileSize = (Long)v;
                  this._postSet(17, oldVal, this._MaxFileSize);
               } else if (name.equals("MaxWindowBufferSize")) {
                  oldVal = this._MaxWindowBufferSize;
                  this._MaxWindowBufferSize = (Integer)v;
                  this._postSet(15, oldVal, this._MaxWindowBufferSize);
               } else if (name.equals("MinWindowBufferSize")) {
                  oldVal = this._MinWindowBufferSize;
                  this._MinWindowBufferSize = (Integer)v;
                  this._postSet(14, oldVal, this._MinWindowBufferSize);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("SynchronousWritePolicy")) {
                  oldVal = this._SynchronousWritePolicy;
                  this._SynchronousWritePolicy = (String)v;
                  this._postSet(12, oldVal, this._SynchronousWritePolicy);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("customizer")) {
                  JMSFileStore oldVal = this._customizer;
                  this._customizer = (JMSFileStore)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("BlockSize")) {
         return new Integer(this._BlockSize);
      } else if (name.equals("CacheDirectory")) {
         return this._CacheDirectory;
      } else if (name.equals("DelegatedBean")) {
         return this._DelegatedBean;
      } else if (name.equals("DelegatedJMSServer")) {
         return this._DelegatedJMSServer;
      } else if (name.equals("Directory")) {
         return this._Directory;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("FileLockingEnabled")) {
         return new Boolean(this._FileLockingEnabled);
      } else if (name.equals("InitialSize")) {
         return new Long(this._InitialSize);
      } else if (name.equals("IoBufferSize")) {
         return new Integer(this._IoBufferSize);
      } else if (name.equals("MaxFileSize")) {
         return new Long(this._MaxFileSize);
      } else if (name.equals("MaxWindowBufferSize")) {
         return new Integer(this._MaxWindowBufferSize);
      } else if (name.equals("MinWindowBufferSize")) {
         return new Integer(this._MinWindowBufferSize);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("SynchronousWritePolicy")) {
         return this._SynchronousWritePolicy;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends JMSStoreMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 8:
            case 11:
            case 16:
            case 17:
            case 18:
            case 21:
            case 23:
            default:
               break;
            case 9:
               if (s.equals("directory")) {
                  return 11;
               }
               break;
            case 10:
               if (s.equals("block-size")) {
                  return 18;
               }
               break;
            case 12:
               if (s.equals("initial-size")) {
                  return 19;
               }
               break;
            case 13:
               if (s.equals("max-file-size")) {
                  return 17;
               }
               break;
            case 14:
               if (s.equals("delegated-bean")) {
                  return 21;
               }

               if (s.equals("io-buffer-size")) {
                  return 16;
               }
               break;
            case 15:
               if (s.equals("cache-directory")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("delegated-jms-server")) {
                  return 22;
               }

               if (s.equals("file-locking-enabled")) {
                  return 20;
               }
               break;
            case 22:
               if (s.equals("max-window-buffer-size")) {
                  return 15;
               }

               if (s.equals("min-window-buffer-size")) {
                  return 14;
               }
               break;
            case 24:
               if (s.equals("synchronous-write-policy")) {
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
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 11:
               return "directory";
            case 12:
               return "synchronous-write-policy";
            case 13:
               return "cache-directory";
            case 14:
               return "min-window-buffer-size";
            case 15:
               return "max-window-buffer-size";
            case 16:
               return "io-buffer-size";
            case 17:
               return "max-file-size";
            case 18:
               return "block-size";
            case 19:
               return "initial-size";
            case 20:
               return "file-locking-enabled";
            case 21:
               return "delegated-bean";
            case 22:
               return "delegated-jms-server";
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends JMSStoreMBeanImpl.Helper {
      private JMSFileStoreMBeanImpl bean;

      protected Helper(JMSFileStoreMBeanImpl bean) {
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
            case 10:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 11:
               return "Directory";
            case 12:
               return "SynchronousWritePolicy";
            case 13:
               return "CacheDirectory";
            case 14:
               return "MinWindowBufferSize";
            case 15:
               return "MaxWindowBufferSize";
            case 16:
               return "IoBufferSize";
            case 17:
               return "MaxFileSize";
            case 18:
               return "BlockSize";
            case 19:
               return "InitialSize";
            case 20:
               return "FileLockingEnabled";
            case 21:
               return "DelegatedBean";
            case 22:
               return "DelegatedJMSServer";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BlockSize")) {
            return 18;
         } else if (propName.equals("CacheDirectory")) {
            return 13;
         } else if (propName.equals("DelegatedBean")) {
            return 21;
         } else if (propName.equals("DelegatedJMSServer")) {
            return 22;
         } else if (propName.equals("Directory")) {
            return 11;
         } else if (propName.equals("InitialSize")) {
            return 19;
         } else if (propName.equals("IoBufferSize")) {
            return 16;
         } else if (propName.equals("MaxFileSize")) {
            return 17;
         } else if (propName.equals("MaxWindowBufferSize")) {
            return 15;
         } else if (propName.equals("MinWindowBufferSize")) {
            return 14;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("SynchronousWritePolicy")) {
            return 12;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("FileLockingEnabled") ? 20 : super.getPropertyIndex(propName);
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
            if (this.bean.isBlockSizeSet()) {
               buf.append("BlockSize");
               buf.append(String.valueOf(this.bean.getBlockSize()));
            }

            if (this.bean.isCacheDirectorySet()) {
               buf.append("CacheDirectory");
               buf.append(String.valueOf(this.bean.getCacheDirectory()));
            }

            if (this.bean.isDelegatedBeanSet()) {
               buf.append("DelegatedBean");
               buf.append(String.valueOf(this.bean.getDelegatedBean()));
            }

            if (this.bean.isDelegatedJMSServerSet()) {
               buf.append("DelegatedJMSServer");
               buf.append(String.valueOf(this.bean.getDelegatedJMSServer()));
            }

            if (this.bean.isDirectorySet()) {
               buf.append("Directory");
               buf.append(String.valueOf(this.bean.getDirectory()));
            }

            if (this.bean.isInitialSizeSet()) {
               buf.append("InitialSize");
               buf.append(String.valueOf(this.bean.getInitialSize()));
            }

            if (this.bean.isIoBufferSizeSet()) {
               buf.append("IoBufferSize");
               buf.append(String.valueOf(this.bean.getIoBufferSize()));
            }

            if (this.bean.isMaxFileSizeSet()) {
               buf.append("MaxFileSize");
               buf.append(String.valueOf(this.bean.getMaxFileSize()));
            }

            if (this.bean.isMaxWindowBufferSizeSet()) {
               buf.append("MaxWindowBufferSize");
               buf.append(String.valueOf(this.bean.getMaxWindowBufferSize()));
            }

            if (this.bean.isMinWindowBufferSizeSet()) {
               buf.append("MinWindowBufferSize");
               buf.append(String.valueOf(this.bean.getMinWindowBufferSize()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isSynchronousWritePolicySet()) {
               buf.append("SynchronousWritePolicy");
               buf.append(String.valueOf(this.bean.getSynchronousWritePolicy()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isFileLockingEnabledSet()) {
               buf.append("FileLockingEnabled");
               buf.append(String.valueOf(this.bean.isFileLockingEnabled()));
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
            JMSFileStoreMBeanImpl otherTyped = (JMSFileStoreMBeanImpl)other;
            this.computeDiff("BlockSize", this.bean.getBlockSize(), otherTyped.getBlockSize(), true);
            this.computeDiff("CacheDirectory", this.bean.getCacheDirectory(), otherTyped.getCacheDirectory(), true);
            this.computeDiff("Directory", this.bean.getDirectory(), otherTyped.getDirectory(), false);
            this.computeDiff("InitialSize", this.bean.getInitialSize(), otherTyped.getInitialSize(), true);
            this.computeDiff("IoBufferSize", this.bean.getIoBufferSize(), otherTyped.getIoBufferSize(), true);
            this.computeDiff("MaxFileSize", this.bean.getMaxFileSize(), otherTyped.getMaxFileSize(), true);
            this.computeDiff("MaxWindowBufferSize", this.bean.getMaxWindowBufferSize(), otherTyped.getMaxWindowBufferSize(), true);
            this.computeDiff("MinWindowBufferSize", this.bean.getMinWindowBufferSize(), otherTyped.getMinWindowBufferSize(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("SynchronousWritePolicy", this.bean.getSynchronousWritePolicy(), otherTyped.getSynchronousWritePolicy(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("FileLockingEnabled", this.bean.isFileLockingEnabled(), otherTyped.isFileLockingEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSFileStoreMBeanImpl original = (JMSFileStoreMBeanImpl)event.getSourceBean();
            JMSFileStoreMBeanImpl proposed = (JMSFileStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BlockSize")) {
                  original.setBlockSize(proposed.getBlockSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("CacheDirectory")) {
                  original.setCacheDirectory(proposed.getCacheDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (!prop.equals("DelegatedBean") && !prop.equals("DelegatedJMSServer")) {
                  if (prop.equals("Directory")) {
                     original.setDirectory(proposed.getDirectory());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("InitialSize")) {
                     original.setInitialSize(proposed.getInitialSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (prop.equals("IoBufferSize")) {
                     original.setIoBufferSize(proposed.getIoBufferSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("MaxFileSize")) {
                     original.setMaxFileSize(proposed.getMaxFileSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 17);
                  } else if (prop.equals("MaxWindowBufferSize")) {
                     original.setMaxWindowBufferSize(proposed.getMaxWindowBufferSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("MinWindowBufferSize")) {
                     original.setMinWindowBufferSize(proposed.getMinWindowBufferSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("SynchronousWritePolicy")) {
                     original.setSynchronousWritePolicy(proposed.getSynchronousWritePolicy());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
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
                  } else if (!prop.equals("DynamicallyCreated")) {
                     if (prop.equals("FileLockingEnabled")) {
                        original.setFileLockingEnabled(proposed.isFileLockingEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 20);
                     } else {
                        super.applyPropertyUpdate(event, update);
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
            JMSFileStoreMBeanImpl copy = (JMSFileStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BlockSize")) && this.bean.isBlockSizeSet()) {
               copy.setBlockSize(this.bean.getBlockSize());
            }

            if ((excludeProps == null || !excludeProps.contains("CacheDirectory")) && this.bean.isCacheDirectorySet()) {
               copy.setCacheDirectory(this.bean.getCacheDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("Directory")) && this.bean.isDirectorySet()) {
               copy.setDirectory(this.bean.getDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialSize")) && this.bean.isInitialSizeSet()) {
               copy.setInitialSize(this.bean.getInitialSize());
            }

            if ((excludeProps == null || !excludeProps.contains("IoBufferSize")) && this.bean.isIoBufferSizeSet()) {
               copy.setIoBufferSize(this.bean.getIoBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxFileSize")) && this.bean.isMaxFileSizeSet()) {
               copy.setMaxFileSize(this.bean.getMaxFileSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxWindowBufferSize")) && this.bean.isMaxWindowBufferSizeSet()) {
               copy.setMaxWindowBufferSize(this.bean.getMaxWindowBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MinWindowBufferSize")) && this.bean.isMinWindowBufferSizeSet()) {
               copy.setMinWindowBufferSize(this.bean.getMinWindowBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SynchronousWritePolicy")) && this.bean.isSynchronousWritePolicySet()) {
               copy.setSynchronousWritePolicy(this.bean.getSynchronousWritePolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("FileLockingEnabled")) && this.bean.isFileLockingEnabledSet()) {
               copy.setFileLockingEnabled(this.bean.isFileLockingEnabled());
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
         this.inferSubTree(this.bean.getDelegatedBean(), clazz, annotation);
         this.inferSubTree(this.bean.getDelegatedJMSServer(), clazz, annotation);
      }
   }
}

package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.utils.collections.CombinedIterator;

public class GenericFileStoreMBeanImpl extends ConfigurationMBeanImpl implements GenericFileStoreMBean, Serializable {
   private int _BlockSize;
   private String _CacheDirectory;
   private String _Directory;
   private boolean _FileLockingEnabled;
   private long _InitialSize;
   private int _IoBufferSize;
   private long _MaxFileSize;
   private int _MaxWindowBufferSize;
   private int _MinWindowBufferSize;
   private String _SynchronousWritePolicy;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private GenericFileStoreMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(GenericFileStoreMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(GenericFileStoreMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public GenericFileStoreMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(GenericFileStoreMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      GenericFileStoreMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public GenericFileStoreMBeanImpl() {
      this._initializeProperty(-1);
   }

   public GenericFileStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public GenericFileStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDirectory() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getDirectory(), this) : this._Directory;
   }

   public boolean isDirectoryInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isDirectorySet() {
      return this._isSet(10);
   }

   public void setDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._Directory;
      this._Directory = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSynchronousWritePolicy() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getSynchronousWritePolicy(), this) : this._SynchronousWritePolicy;
   }

   public boolean isSynchronousWritePolicyInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isSynchronousWritePolicySet() {
      return this._isSet(11);
   }

   public void setSynchronousWritePolicy(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Disabled", "Cache-Flush", "Direct-Write", "Direct-Write-With-Cache"};
      param0 = LegalChecks.checkInEnum("SynchronousWritePolicy", param0, _set);
      boolean wasSet = this._isSet(11);
      String _oldVal = this._SynchronousWritePolicy;
      this._SynchronousWritePolicy = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var5.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCacheDirectory() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getCacheDirectory(), this) : this._CacheDirectory;
   }

   public boolean isCacheDirectoryInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isCacheDirectorySet() {
      return this._isSet(12);
   }

   public void setCacheDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._CacheDirectory;
      this._CacheDirectory = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMinWindowBufferSize() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getMinWindowBufferSize() : this._MinWindowBufferSize;
   }

   public boolean isMinWindowBufferSizeInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isMinWindowBufferSizeSet() {
      return this._isSet(13);
   }

   public void setMinWindowBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MinWindowBufferSize", (long)param0, -1L, 1073741824L);
      boolean wasSet = this._isSet(13);
      int _oldVal = this._MinWindowBufferSize;
      this._MinWindowBufferSize = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxWindowBufferSize() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getMaxWindowBufferSize() : this._MaxWindowBufferSize;
   }

   public boolean isMaxWindowBufferSizeInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isMaxWindowBufferSizeSet() {
      return this._isSet(14);
   }

   public void setMaxWindowBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxWindowBufferSize", (long)param0, -1L, 1073741824L);
      boolean wasSet = this._isSet(14);
      int _oldVal = this._MaxWindowBufferSize;
      this._MaxWindowBufferSize = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public int getIoBufferSize() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getIoBufferSize() : this._IoBufferSize;
   }

   public boolean isIoBufferSizeInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isIoBufferSizeSet() {
      return this._isSet(15);
   }

   public void setIoBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("IoBufferSize", (long)param0, -1L, 67108864L);
      boolean wasSet = this._isSet(15);
      int _oldVal = this._IoBufferSize;
      this._IoBufferSize = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public long getMaxFileSize() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getMaxFileSize() : this._MaxFileSize;
   }

   public boolean isMaxFileSizeInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isMaxFileSizeSet() {
      return this._isSet(16);
   }

   public void setMaxFileSize(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxFileSize", param0, 1048576L, 2139095040L);
      boolean wasSet = this._isSet(16);
      long _oldVal = this._MaxFileSize;
      this._MaxFileSize = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var6.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public int getBlockSize() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getBlockSize() : this._BlockSize;
   }

   public boolean isBlockSizeInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isBlockSizeSet() {
      return this._isSet(17);
   }

   public void setBlockSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("BlockSize", (long)param0, -1L, 8192L);
      boolean wasSet = this._isSet(17);
      int _oldVal = this._BlockSize;
      this._BlockSize = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public long getInitialSize() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getInitialSize() : this._InitialSize;
   }

   public boolean isInitialSizeInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isInitialSizeSet() {
      return this._isSet(18);
   }

   public void setInitialSize(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("InitialSize", param0, 0L);
      boolean wasSet = this._isSet(18);
      long _oldVal = this._InitialSize;
      this._InitialSize = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var6.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isFileLockingEnabled() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().isFileLockingEnabled() : this._FileLockingEnabled;
   }

   public boolean isFileLockingEnabledInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isFileLockingEnabledSet() {
      return this._isSet(19);
   }

   public void setFileLockingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      boolean _oldVal = this._FileLockingEnabled;
      this._FileLockingEnabled = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         GenericFileStoreMBeanImpl source = (GenericFileStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._BlockSize = -1;
               if (initOne) {
                  break;
               }
            case 12:
               this._CacheDirectory = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Directory = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._InitialSize = 0L;
               if (initOne) {
                  break;
               }
            case 15:
               this._IoBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 16:
               this._MaxFileSize = 1342177280L;
               if (initOne) {
                  break;
               }
            case 14:
               this._MaxWindowBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 13:
               this._MinWindowBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 11:
               this._SynchronousWritePolicy = "Direct-Write";
               if (initOne) {
                  break;
               }
            case 19:
               this._FileLockingEnabled = true;
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
      return "GenericFileStore";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("BlockSize")) {
         oldVal = this._BlockSize;
         this._BlockSize = (Integer)v;
         this._postSet(17, oldVal, this._BlockSize);
      } else {
         String oldVal;
         if (name.equals("CacheDirectory")) {
            oldVal = this._CacheDirectory;
            this._CacheDirectory = (String)v;
            this._postSet(12, oldVal, this._CacheDirectory);
         } else if (name.equals("Directory")) {
            oldVal = this._Directory;
            this._Directory = (String)v;
            this._postSet(10, oldVal, this._Directory);
         } else if (name.equals("FileLockingEnabled")) {
            boolean oldVal = this._FileLockingEnabled;
            this._FileLockingEnabled = (Boolean)v;
            this._postSet(19, oldVal, this._FileLockingEnabled);
         } else {
            long oldVal;
            if (name.equals("InitialSize")) {
               oldVal = this._InitialSize;
               this._InitialSize = (Long)v;
               this._postSet(18, oldVal, this._InitialSize);
            } else if (name.equals("IoBufferSize")) {
               oldVal = this._IoBufferSize;
               this._IoBufferSize = (Integer)v;
               this._postSet(15, oldVal, this._IoBufferSize);
            } else if (name.equals("MaxFileSize")) {
               oldVal = this._MaxFileSize;
               this._MaxFileSize = (Long)v;
               this._postSet(16, oldVal, this._MaxFileSize);
            } else if (name.equals("MaxWindowBufferSize")) {
               oldVal = this._MaxWindowBufferSize;
               this._MaxWindowBufferSize = (Integer)v;
               this._postSet(14, oldVal, this._MaxWindowBufferSize);
            } else if (name.equals("MinWindowBufferSize")) {
               oldVal = this._MinWindowBufferSize;
               this._MinWindowBufferSize = (Integer)v;
               this._postSet(13, oldVal, this._MinWindowBufferSize);
            } else if (name.equals("SynchronousWritePolicy")) {
               oldVal = this._SynchronousWritePolicy;
               this._SynchronousWritePolicy = (String)v;
               this._postSet(11, oldVal, this._SynchronousWritePolicy);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("BlockSize")) {
         return new Integer(this._BlockSize);
      } else if (name.equals("CacheDirectory")) {
         return this._CacheDirectory;
      } else if (name.equals("Directory")) {
         return this._Directory;
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
      } else {
         return name.equals("SynchronousWritePolicy") ? this._SynchronousWritePolicy : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("directory")) {
                  return 10;
               }
               break;
            case 10:
               if (s.equals("block-size")) {
                  return 17;
               }
            case 11:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            default:
               break;
            case 12:
               if (s.equals("initial-size")) {
                  return 18;
               }
               break;
            case 13:
               if (s.equals("max-file-size")) {
                  return 16;
               }
               break;
            case 14:
               if (s.equals("io-buffer-size")) {
                  return 15;
               }
               break;
            case 15:
               if (s.equals("cache-directory")) {
                  return 12;
               }
               break;
            case 20:
               if (s.equals("file-locking-enabled")) {
                  return 19;
               }
               break;
            case 22:
               if (s.equals("max-window-buffer-size")) {
                  return 14;
               }

               if (s.equals("min-window-buffer-size")) {
                  return 13;
               }
               break;
            case 24:
               if (s.equals("synchronous-write-policy")) {
                  return 11;
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
               return "directory";
            case 11:
               return "synchronous-write-policy";
            case 12:
               return "cache-directory";
            case 13:
               return "min-window-buffer-size";
            case 14:
               return "max-window-buffer-size";
            case 15:
               return "io-buffer-size";
            case 16:
               return "max-file-size";
            case 17:
               return "block-size";
            case 18:
               return "initial-size";
            case 19:
               return "file-locking-enabled";
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
      private GenericFileStoreMBeanImpl bean;

      protected Helper(GenericFileStoreMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Directory";
            case 11:
               return "SynchronousWritePolicy";
            case 12:
               return "CacheDirectory";
            case 13:
               return "MinWindowBufferSize";
            case 14:
               return "MaxWindowBufferSize";
            case 15:
               return "IoBufferSize";
            case 16:
               return "MaxFileSize";
            case 17:
               return "BlockSize";
            case 18:
               return "InitialSize";
            case 19:
               return "FileLockingEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BlockSize")) {
            return 17;
         } else if (propName.equals("CacheDirectory")) {
            return 12;
         } else if (propName.equals("Directory")) {
            return 10;
         } else if (propName.equals("InitialSize")) {
            return 18;
         } else if (propName.equals("IoBufferSize")) {
            return 15;
         } else if (propName.equals("MaxFileSize")) {
            return 16;
         } else if (propName.equals("MaxWindowBufferSize")) {
            return 14;
         } else if (propName.equals("MinWindowBufferSize")) {
            return 13;
         } else if (propName.equals("SynchronousWritePolicy")) {
            return 11;
         } else {
            return propName.equals("FileLockingEnabled") ? 19 : super.getPropertyIndex(propName);
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

            if (this.bean.isSynchronousWritePolicySet()) {
               buf.append("SynchronousWritePolicy");
               buf.append(String.valueOf(this.bean.getSynchronousWritePolicy()));
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
            GenericFileStoreMBeanImpl otherTyped = (GenericFileStoreMBeanImpl)other;
            this.computeDiff("BlockSize", this.bean.getBlockSize(), otherTyped.getBlockSize(), true);
            this.computeDiff("CacheDirectory", this.bean.getCacheDirectory(), otherTyped.getCacheDirectory(), true);
            this.computeDiff("Directory", this.bean.getDirectory(), otherTyped.getDirectory(), false);
            this.computeDiff("InitialSize", this.bean.getInitialSize(), otherTyped.getInitialSize(), true);
            this.computeDiff("IoBufferSize", this.bean.getIoBufferSize(), otherTyped.getIoBufferSize(), true);
            this.computeDiff("MaxFileSize", this.bean.getMaxFileSize(), otherTyped.getMaxFileSize(), true);
            this.computeDiff("MaxWindowBufferSize", this.bean.getMaxWindowBufferSize(), otherTyped.getMaxWindowBufferSize(), true);
            this.computeDiff("MinWindowBufferSize", this.bean.getMinWindowBufferSize(), otherTyped.getMinWindowBufferSize(), true);
            this.computeDiff("SynchronousWritePolicy", this.bean.getSynchronousWritePolicy(), otherTyped.getSynchronousWritePolicy(), true);
            this.computeDiff("FileLockingEnabled", this.bean.isFileLockingEnabled(), otherTyped.isFileLockingEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            GenericFileStoreMBeanImpl original = (GenericFileStoreMBeanImpl)event.getSourceBean();
            GenericFileStoreMBeanImpl proposed = (GenericFileStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BlockSize")) {
                  original.setBlockSize(proposed.getBlockSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("CacheDirectory")) {
                  original.setCacheDirectory(proposed.getCacheDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Directory")) {
                  original.setDirectory(proposed.getDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("InitialSize")) {
                  original.setInitialSize(proposed.getInitialSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("IoBufferSize")) {
                  original.setIoBufferSize(proposed.getIoBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MaxFileSize")) {
                  original.setMaxFileSize(proposed.getMaxFileSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("MaxWindowBufferSize")) {
                  original.setMaxWindowBufferSize(proposed.getMaxWindowBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MinWindowBufferSize")) {
                  original.setMinWindowBufferSize(proposed.getMinWindowBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("SynchronousWritePolicy")) {
                  original.setSynchronousWritePolicy(proposed.getSynchronousWritePolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("FileLockingEnabled")) {
                  original.setFileLockingEnabled(proposed.isFileLockingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else {
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
            GenericFileStoreMBeanImpl copy = (GenericFileStoreMBeanImpl)initialCopy;
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

            if ((excludeProps == null || !excludeProps.contains("SynchronousWritePolicy")) && this.bean.isSynchronousWritePolicySet()) {
               copy.setSynchronousWritePolicy(this.bean.getSynchronousWritePolicy());
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
      }
   }
}

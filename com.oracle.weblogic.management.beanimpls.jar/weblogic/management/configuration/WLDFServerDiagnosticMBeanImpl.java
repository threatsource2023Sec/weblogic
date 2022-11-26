package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.WLDFServerDiagnostic;
import weblogic.utils.ArrayUtils;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StringUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WLDFServerDiagnosticMBeanImpl extends ConfigurationMBeanImpl implements WLDFServerDiagnosticMBean, Serializable {
   private boolean _DataRetirementEnabled;
   private boolean _DataRetirementTestModeEnabled;
   private boolean _DiagnosticContextEnabled;
   private String _DiagnosticDataArchiveType;
   private String _DiagnosticDumpsDir;
   private JDBCSystemResourceMBean _DiagnosticJDBCResource;
   private String _DiagnosticJDBCSchemaName;
   private int _DiagnosticStoreBlockSize;
   private String _DiagnosticStoreDir;
   private boolean _DiagnosticStoreFileLockingEnabled;
   private int _DiagnosticStoreIoBufferSize;
   private long _DiagnosticStoreMaxFileSize;
   private int _DiagnosticStoreMaxWindowBufferSize;
   private int _DiagnosticStoreMinWindowBufferSize;
   private boolean _DynamicallyCreated;
   private long _EventPersistenceInterval;
   private long _EventsImageCaptureInterval;
   private String _ImageDir;
   private int _ImageTimeout;
   private int _MaxHeapDumpCount;
   private int _MaxThreadDumpCount;
   private String _Name;
   private int _PreferredStoreSizeLimit;
   private int _StoreSizeCheckPeriod;
   private boolean _SynchronousEventPersistenceEnabled;
   private String[] _Tags;
   private WLDFResourceBean _WLDFBuiltinSystemResourceDescriptorBean;
   private String _WLDFBuiltinSystemResourceType;
   private WLDFDataRetirementByAgeMBean[] _WLDFDataRetirementByAges;
   private WLDFDataRetirementMBean[] _WLDFDataRetirements;
   private String _WLDFDiagnosticVolume;
   private transient WLDFServerDiagnostic _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WLDFServerDiagnosticMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WLDFServerDiagnosticMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WLDFServerDiagnosticMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WLDFServerDiagnosticMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WLDFServerDiagnosticMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WLDFServerDiagnosticMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._DiagnosticJDBCResource instanceof JDBCSystemResourceMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getDiagnosticJDBCResource() != null) {
            this._getReferenceManager().unregisterBean((JDBCSystemResourceMBeanImpl)oldDelegate.getDiagnosticJDBCResource());
         }

         if (delegate != null && delegate.getDiagnosticJDBCResource() != null) {
            this._getReferenceManager().registerBean((JDBCSystemResourceMBeanImpl)delegate.getDiagnosticJDBCResource(), false);
         }

         ((JDBCSystemResourceMBeanImpl)this._DiagnosticJDBCResource)._setDelegateBean((JDBCSystemResourceMBeanImpl)((JDBCSystemResourceMBeanImpl)(delegate == null ? null : delegate.getDiagnosticJDBCResource())));
      }

   }

   public WLDFServerDiagnosticMBeanImpl() {
      try {
         this._customizer = new WLDFServerDiagnostic(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WLDFServerDiagnosticMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new WLDFServerDiagnostic(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WLDFServerDiagnosticMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new WLDFServerDiagnostic(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getImageDir() {
      if (!this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10)) {
         return this._performMacroSubstitution(this._getDelegateBean().getImageDir(), this);
      } else {
         if (!this._isSet(10)) {
            try {
               return "logs" + PlatformConstants.FILE_SEP + "diagnostic_images";
            } catch (NullPointerException var2) {
            }
         }

         return this._ImageDir;
      }
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getName();
      }
   }

   public boolean isImageDirInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isImageDirSet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setImageDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._ImageDir;
      this._ImageDir = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public int getImageTimeout() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().getImageTimeout() : this._ImageTimeout;
   }

   public boolean isImageTimeoutInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isImageTimeoutSet() {
      return this._isSet(11);
   }

   public void setImageTimeout(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("ImageTimeout", (long)param0, 0L, 1440L);
      boolean wasSet = this._isSet(11);
      int _oldVal = this._ImageTimeout;
      this._ImageTimeout = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public long getEventsImageCaptureInterval() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getEventsImageCaptureInterval() : this._EventsImageCaptureInterval;
   }

   public boolean isEventsImageCaptureIntervalInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isEventsImageCaptureIntervalSet() {
      return this._isSet(12);
   }

   public void setEventsImageCaptureInterval(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      long _oldVal = this._EventsImageCaptureInterval;
      this._EventsImageCaptureInterval = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var6.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDiagnosticStoreDir() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getDiagnosticStoreDir(), this) : this._DiagnosticStoreDir;
   }

   public boolean isDiagnosticStoreDirInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isDiagnosticStoreDirSet() {
      return this._isSet(13);
   }

   public void setDiagnosticStoreDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this._DiagnosticStoreDir;
      this._DiagnosticStoreDir = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDiagnosticStoreFileLockingEnabled() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().isDiagnosticStoreFileLockingEnabled() : this._DiagnosticStoreFileLockingEnabled;
   }

   public boolean isDiagnosticStoreFileLockingEnabledInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isDiagnosticStoreFileLockingEnabledSet() {
      return this._isSet(14);
   }

   public void setDiagnosticStoreFileLockingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      boolean _oldVal = this._DiagnosticStoreFileLockingEnabled;
      this._DiagnosticStoreFileLockingEnabled = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public int getDiagnosticStoreMinWindowBufferSize() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getDiagnosticStoreMinWindowBufferSize() : this._DiagnosticStoreMinWindowBufferSize;
   }

   public boolean isDiagnosticStoreMinWindowBufferSizeInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isDiagnosticStoreMinWindowBufferSizeSet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setDiagnosticStoreMinWindowBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DiagnosticStoreMinWindowBufferSize", (long)param0, -1L, 1073741824L);
      boolean wasSet = this._isSet(15);
      int _oldVal = this._DiagnosticStoreMinWindowBufferSize;
      this._DiagnosticStoreMinWindowBufferSize = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public int getDiagnosticStoreMaxWindowBufferSize() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getDiagnosticStoreMaxWindowBufferSize() : this._DiagnosticStoreMaxWindowBufferSize;
   }

   public boolean isDiagnosticStoreMaxWindowBufferSizeInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isDiagnosticStoreMaxWindowBufferSizeSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setDiagnosticStoreMaxWindowBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DiagnosticStoreMaxWindowBufferSize", (long)param0, -1L, 1073741824L);
      boolean wasSet = this._isSet(16);
      int _oldVal = this._DiagnosticStoreMaxWindowBufferSize;
      this._DiagnosticStoreMaxWindowBufferSize = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public int getDiagnosticStoreIoBufferSize() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getDiagnosticStoreIoBufferSize() : this._DiagnosticStoreIoBufferSize;
   }

   public boolean isDiagnosticStoreIoBufferSizeInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isDiagnosticStoreIoBufferSizeSet() {
      return this._isSet(17);
   }

   public void setDiagnosticStoreIoBufferSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DiagnosticStoreIoBufferSize", (long)param0, -1L, 67108864L);
      boolean wasSet = this._isSet(17);
      int _oldVal = this._DiagnosticStoreIoBufferSize;
      this._DiagnosticStoreIoBufferSize = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public long getDiagnosticStoreMaxFileSize() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getDiagnosticStoreMaxFileSize() : this._DiagnosticStoreMaxFileSize;
   }

   public boolean isDiagnosticStoreMaxFileSizeInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isDiagnosticStoreMaxFileSizeSet() {
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

   public void setDiagnosticStoreMaxFileSize(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("DiagnosticStoreMaxFileSize", param0, 10485760L);
      boolean wasSet = this._isSet(18);
      long _oldVal = this._DiagnosticStoreMaxFileSize;
      this._DiagnosticStoreMaxFileSize = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var6.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public int getDiagnosticStoreBlockSize() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._getDelegateBean().getDiagnosticStoreBlockSize() : this._DiagnosticStoreBlockSize;
   }

   public boolean isDiagnosticStoreBlockSizeInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isDiagnosticStoreBlockSizeSet() {
      return this._isSet(19);
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setDiagnosticStoreBlockSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("DiagnosticStoreBlockSize", (long)param0, -1L, 8192L);
      boolean wasSet = this._isSet(19);
      int _oldVal = this._DiagnosticStoreBlockSize;
      this._DiagnosticStoreBlockSize = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDiagnosticDataArchiveType() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._performMacroSubstitution(this._getDelegateBean().getDiagnosticDataArchiveType(), this) : this._DiagnosticDataArchiveType;
   }

   public boolean isDiagnosticDataArchiveTypeInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isDiagnosticDataArchiveTypeSet() {
      return this._isSet(20);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(9);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(9)) {
            source._postSetFirePropertyChange(9, wasSet, _oldVal, param0);
         }
      }

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

   public void setDiagnosticDataArchiveType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"FileStoreArchive", "JDBCArchive"};
      param0 = LegalChecks.checkInEnum("DiagnosticDataArchiveType", param0, _set);
      boolean wasSet = this._isSet(20);
      String _oldVal = this._DiagnosticDataArchiveType;
      this._DiagnosticDataArchiveType = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var5.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public JDBCSystemResourceMBean getDiagnosticJDBCResource() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().getDiagnosticJDBCResource() : this._DiagnosticJDBCResource;
   }

   public String getDiagnosticJDBCResourceAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getDiagnosticJDBCResource();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isDiagnosticJDBCResourceInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isDiagnosticJDBCResourceSet() {
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

   public void setDiagnosticJDBCResourceAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, JDBCSystemResourceMBean.class, new ReferenceManager.Resolver(this, 21) {
            public void resolveReference(Object value) {
               try {
                  WLDFServerDiagnosticMBeanImpl.this.setDiagnosticJDBCResource((JDBCSystemResourceMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         JDBCSystemResourceMBean _oldVal = this._DiagnosticJDBCResource;
         this._initializeProperty(21);
         this._postSet(21, _oldVal, this._DiagnosticJDBCResource);
      }

   }

   public void setDiagnosticJDBCResource(JDBCSystemResourceMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 21, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return WLDFServerDiagnosticMBeanImpl.this.getDiagnosticJDBCResource();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(21);
      JDBCSystemResourceMBean _oldVal = this._DiagnosticJDBCResource;
      this._DiagnosticJDBCResource = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDiagnosticJDBCSchemaName() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._performMacroSubstitution(this._getDelegateBean().getDiagnosticJDBCSchemaName(), this) : this._DiagnosticJDBCSchemaName;
   }

   public boolean isDiagnosticJDBCSchemaNameInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isDiagnosticJDBCSchemaNameSet() {
      return this._isSet(22);
   }

   public void setDiagnosticJDBCSchemaName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      String _oldVal = this._DiagnosticJDBCSchemaName;
      this._DiagnosticJDBCSchemaName = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isSynchronousEventPersistenceEnabled() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().isSynchronousEventPersistenceEnabled() : this._SynchronousEventPersistenceEnabled;
   }

   public boolean isSynchronousEventPersistenceEnabledInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isSynchronousEventPersistenceEnabledSet() {
      return this._isSet(23);
   }

   public void setSynchronousEventPersistenceEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      boolean _oldVal = this._SynchronousEventPersistenceEnabled;
      this._SynchronousEventPersistenceEnabled = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public long getEventPersistenceInterval() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().getEventPersistenceInterval() : this._EventPersistenceInterval;
   }

   public boolean isEventPersistenceIntervalInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isEventPersistenceIntervalSet() {
      return this._isSet(24);
   }

   public void setEventPersistenceInterval(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      long _oldVal = this._EventPersistenceInterval;
      this._EventPersistenceInterval = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var6.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDiagnosticContextEnabled() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().isDiagnosticContextEnabled() : this._DiagnosticContextEnabled;
   }

   public boolean isDiagnosticContextEnabledInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isDiagnosticContextEnabledSet() {
      return this._isSet(25);
   }

   public void setDiagnosticContextEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(25);
      boolean _oldVal = this._DiagnosticContextEnabled;
      this._DiagnosticContextEnabled = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDataRetirementTestModeEnabled() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().isDataRetirementTestModeEnabled() : this._DataRetirementTestModeEnabled;
   }

   public boolean isDataRetirementTestModeEnabledInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isDataRetirementTestModeEnabledSet() {
      return this._isSet(26);
   }

   public void setDataRetirementTestModeEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      boolean _oldVal = this._DataRetirementTestModeEnabled;
      this._DataRetirementTestModeEnabled = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isDataRetirementEnabled() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().isDataRetirementEnabled() : this._DataRetirementEnabled;
   }

   public boolean isDataRetirementEnabledInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isDataRetirementEnabledSet() {
      return this._isSet(27);
   }

   public void setDataRetirementEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      boolean _oldVal = this._DataRetirementEnabled;
      this._DataRetirementEnabled = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPreferredStoreSizeLimit() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().getPreferredStoreSizeLimit() : this._PreferredStoreSizeLimit;
   }

   public boolean isPreferredStoreSizeLimitInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isPreferredStoreSizeLimitSet() {
      return this._isSet(28);
   }

   public void setPreferredStoreSizeLimit(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("PreferredStoreSizeLimit", param0, 10);
      boolean wasSet = this._isSet(28);
      int _oldVal = this._PreferredStoreSizeLimit;
      this._PreferredStoreSizeLimit = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public int getStoreSizeCheckPeriod() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().getStoreSizeCheckPeriod() : this._StoreSizeCheckPeriod;
   }

   public boolean isStoreSizeCheckPeriodInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isStoreSizeCheckPeriodSet() {
      return this._isSet(29);
   }

   public void setStoreSizeCheckPeriod(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("StoreSizeCheckPeriod", param0, 1);
      boolean wasSet = this._isSet(29);
      int _oldVal = this._StoreSizeCheckPeriod;
      this._StoreSizeCheckPeriod = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public void addWLDFDataRetirement(WLDFDataRetirementMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 30)) {
         WLDFDataRetirementMBean[] _new = (WLDFDataRetirementMBean[])((WLDFDataRetirementMBean[])this._getHelper()._extendArray(this.getWLDFDataRetirements(), WLDFDataRetirementMBean.class, param0));

         try {
            this.setWLDFDataRetirements(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFDataRetirementMBean[] getWLDFDataRetirements() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getWLDFDataRetirements() : this._customizer.getWLDFDataRetirements();
   }

   public boolean isWLDFDataRetirementsInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isWLDFDataRetirementsSet() {
      return this._isSet(30);
   }

   public void removeWLDFDataRetirement(WLDFDataRetirementMBean param0) {
      WLDFDataRetirementMBean[] _old = this.getWLDFDataRetirements();
      WLDFDataRetirementMBean[] _new = (WLDFDataRetirementMBean[])((WLDFDataRetirementMBean[])this._getHelper()._removeElement(_old, WLDFDataRetirementMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setWLDFDataRetirements(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setWLDFDataRetirements(WLDFDataRetirementMBean[] param0) throws InvalidAttributeValueException {
      WLDFDataRetirementMBean[] param0 = param0 == null ? new WLDFDataRetirementMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._WLDFDataRetirements = (WLDFDataRetirementMBean[])param0;
   }

   public WLDFDataRetirementMBean lookupWLDFDataRetirement(String param0) {
      return this._customizer.lookupWLDFDataRetirement(param0);
   }

   public void addWLDFDataRetirementByAge(WLDFDataRetirementByAgeMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 31)) {
         WLDFDataRetirementByAgeMBean[] _new;
         if (this._isSet(31)) {
            _new = (WLDFDataRetirementByAgeMBean[])((WLDFDataRetirementByAgeMBean[])this._getHelper()._extendArray(this.getWLDFDataRetirementByAges(), WLDFDataRetirementByAgeMBean.class, param0));
         } else {
            _new = new WLDFDataRetirementByAgeMBean[]{param0};
         }

         try {
            this.setWLDFDataRetirementByAges(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFDataRetirementByAgeMBean[] getWLDFDataRetirementByAges() {
      WLDFDataRetirementByAgeMBean[] delegateArray;
      int j;
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(31)) {
         delegateArray = this._getDelegateBean().getWLDFDataRetirementByAges();

         for(int i = 0; i < delegateArray.length; ++i) {
            boolean found = false;

            for(j = 0; j < this._WLDFDataRetirementByAges.length; ++j) {
               if (delegateArray[i].getName().equals(this._WLDFDataRetirementByAges[j].getName())) {
                  ((WLDFDataRetirementByAgeMBeanImpl)this._WLDFDataRetirementByAges[j])._setDelegateBean((WLDFDataRetirementByAgeMBeanImpl)delegateArray[i]);
                  found = true;
               }
            }

            if (!found) {
               try {
                  WLDFDataRetirementByAgeMBeanImpl mbean = new WLDFDataRetirementByAgeMBeanImpl(this, -1, true);
                  this._setParent(mbean, this, 31);
                  mbean.setName(delegateArray[i].getName());
                  mbean._setDelegateBean((WLDFDataRetirementByAgeMBeanImpl)delegateArray[i]);
                  mbean._setTransient(true);
                  if (this._isSet(31)) {
                     this.setWLDFDataRetirementByAges((WLDFDataRetirementByAgeMBean[])((WLDFDataRetirementByAgeMBean[])this._getHelper()._extendArray(this._WLDFDataRetirementByAges, WLDFDataRetirementByAgeMBean.class, mbean)));
                  } else {
                     this.setWLDFDataRetirementByAges(new WLDFDataRetirementByAgeMBean[]{mbean});
                  }

                  mbean._setSynthetic(true);
               } catch (Exception var16) {
                  throw new UndeclaredThrowableException(var16);
               }
            }
         }
      } else {
         delegateArray = new WLDFDataRetirementByAgeMBean[0];
      }

      if (this._WLDFDataRetirementByAges != null) {
         List removeList = new ArrayList();
         WLDFDataRetirementByAgeMBean[] var18 = this._WLDFDataRetirementByAges;
         j = var18.length;

         for(int var5 = 0; var5 < j; ++var5) {
            WLDFDataRetirementByAgeMBean bn = var18[var5];
            WLDFDataRetirementByAgeMBeanImpl bni = (WLDFDataRetirementByAgeMBeanImpl)bn;
            if (bni._isTransient() && bni._isSynthetic()) {
               String nameToSearch = bni._getDelegateBean().getName();
               boolean found = false;
               WLDFDataRetirementByAgeMBean[] var10 = delegateArray;
               int var11 = delegateArray.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  WLDFDataRetirementByAgeMBean delegateTo = var10[var12];
                  if (nameToSearch.equals(delegateTo.getName())) {
                     found = true;
                     break;
                  }
               }

               if (!found) {
                  removeList.add(bn);
               }
            }
         }

         Iterator var19 = removeList.iterator();

         while(var19.hasNext()) {
            WLDFDataRetirementByAgeMBean removeIt = (WLDFDataRetirementByAgeMBean)var19.next();
            WLDFDataRetirementByAgeMBeanImpl removeItImpl = (WLDFDataRetirementByAgeMBeanImpl)removeIt;
            WLDFDataRetirementByAgeMBean[] _new = (WLDFDataRetirementByAgeMBean[])((WLDFDataRetirementByAgeMBean[])this._getHelper()._removeElement(this._WLDFDataRetirementByAges, WLDFDataRetirementByAgeMBean.class, removeIt));

            try {
               this._preDestroy(removeItImpl);
               this._getReferenceManager().unregisterBean(removeItImpl, false);
               this._markDestroyed(removeItImpl);
            } catch (Exception var15) {
            }

            try {
               this.setWLDFDataRetirementByAges(_new);
            } catch (Exception var14) {
               throw new UndeclaredThrowableException(var14);
            }
         }
      }

      return this._WLDFDataRetirementByAges;
   }

   public boolean isWLDFDataRetirementByAgesInherited() {
      if (this._getDelegateBean() != null && this._getDelegateBean()._isSet(31)) {
         WLDFDataRetirementByAgeMBean[] elements = this.getWLDFDataRetirementByAges();
         WLDFDataRetirementByAgeMBean[] var2 = elements;
         int var3 = elements.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            if (o instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean adBean = (AbstractDescriptorBean)o;
               if (!adBean._isTransient() || !adBean._isSynthetic()) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean isWLDFDataRetirementByAgesSet() {
      return this._isSet(31);
   }

   public void removeWLDFDataRetirementByAge(WLDFDataRetirementByAgeMBean param0) {
      this.destroyWLDFDataRetirementByAge(param0);
   }

   public void setWLDFDataRetirementByAges(WLDFDataRetirementByAgeMBean[] param0) throws InvalidAttributeValueException {
      WLDFDataRetirementByAgeMBean[] param0 = param0 == null ? new WLDFDataRetirementByAgeMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         ArrayUtils.CollectAllDiffHandler handler = new ArrayUtils.CollectAllDiffHandler();
         ArrayUtils.computeDiff(this._WLDFDataRetirementByAges, (Object[])param0, handler, new Comparator() {
            public int compare(WLDFDataRetirementByAgeMBean o1, WLDFDataRetirementByAgeMBean o2) {
               return StringUtils.compare(o1.getName(), o2.getName());
            }
         });
         Iterator var3 = handler.getAll().iterator();

         while(var3.hasNext()) {
            WLDFDataRetirementByAgeMBean bean = (WLDFDataRetirementByAgeMBean)var3.next();
            WLDFDataRetirementByAgeMBeanImpl beanImpl = (WLDFDataRetirementByAgeMBeanImpl)bean;
            if (!beanImpl._isTransient() && beanImpl._isSynthetic()) {
               this._untransient();
               break;
            }
         }
      }

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 31)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      boolean wasSet = this._isSet(31);
      WLDFDataRetirementByAgeMBean[] _oldVal = this._WLDFDataRetirementByAges;
      this._WLDFDataRetirementByAges = (WLDFDataRetirementByAgeMBean[])param0;
      this._postSet(31, _oldVal, param0);
      Iterator var11 = this._DelegateSources.iterator();

      while(var11.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var11.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public WLDFDataRetirementByAgeMBean createWLDFDataRetirementByAge(String param0) {
      WLDFDataRetirementByAgeMBeanImpl lookup = (WLDFDataRetirementByAgeMBeanImpl)this.lookupWLDFDataRetirementByAge(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFDataRetirementByAgeMBeanImpl _val = new WLDFDataRetirementByAgeMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWLDFDataRetirementByAge(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWLDFDataRetirementByAge(WLDFDataRetirementByAgeMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 31);
         WLDFDataRetirementByAgeMBean[] _old = this.getWLDFDataRetirementByAges();
         WLDFDataRetirementByAgeMBean[] _new = (WLDFDataRetirementByAgeMBean[])((WLDFDataRetirementByAgeMBean[])this._getHelper()._removeElement(_old, WLDFDataRetirementByAgeMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               Iterator var6 = this._DelegateSources.iterator();

               while(var6.hasNext()) {
                  WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var6.next();
                  WLDFDataRetirementByAgeMBeanImpl childImpl = (WLDFDataRetirementByAgeMBeanImpl)_child;
                  WLDFDataRetirementByAgeMBeanImpl lookup = (WLDFDataRetirementByAgeMBeanImpl)source.lookupWLDFDataRetirementByAge(childImpl.getName());
                  if (lookup != null) {
                     source.destroyWLDFDataRetirementByAge(lookup);
                  }
               }

               this.setWLDFDataRetirementByAges(_new);
            } catch (Exception var10) {
               if (var10 instanceof RuntimeException) {
                  throw (RuntimeException)var10;
               }

               throw new UndeclaredThrowableException(var10);
            }
         }

      } catch (Exception var11) {
         if (var11 instanceof RuntimeException) {
            throw (RuntimeException)var11;
         } else {
            throw new UndeclaredThrowableException(var11);
         }
      }
   }

   public WLDFDataRetirementByAgeMBean lookupWLDFDataRetirementByAge(String param0) {
      Object[] aary = (Object[])this.getWLDFDataRetirementByAges();
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFDataRetirementByAgeMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFDataRetirementByAgeMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public String getWLDFDiagnosticVolume() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._performMacroSubstitution(this._getDelegateBean().getWLDFDiagnosticVolume(), this) : this._WLDFDiagnosticVolume;
   }

   public boolean isWLDFDiagnosticVolumeInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isWLDFDiagnosticVolumeSet() {
      return this._isSet(32);
   }

   public void setWLDFDiagnosticVolume(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Off", "Low", "Medium", "High"};
      param0 = LegalChecks.checkInEnum("WLDFDiagnosticVolume", param0, _set);
      boolean wasSet = this._isSet(32);
      String _oldVal = this._WLDFDiagnosticVolume;
      this._WLDFDiagnosticVolume = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var5.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public String getWLDFBuiltinSystemResourceType() {
      if (!this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33)) {
         return this._performMacroSubstitution(this._getDelegateBean().getWLDFBuiltinSystemResourceType(), this);
      } else if (!this._isSet(33)) {
         return this._isProductionModeEnabled() ? "Low" : "None";
      } else {
         return this._WLDFBuiltinSystemResourceType;
      }
   }

   public boolean isWLDFBuiltinSystemResourceTypeInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isWLDFBuiltinSystemResourceTypeSet() {
      return this._isSet(33);
   }

   public void setWLDFBuiltinSystemResourceType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"None", "Low", "Medium", "High"};
      param0 = LegalChecks.checkInEnum("WLDFBuiltinSystemResourceType", param0, _set);
      boolean wasSet = this._isSet(33);
      String _oldVal = this._WLDFBuiltinSystemResourceType;
      this._WLDFBuiltinSystemResourceType = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var5.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public WLDFResourceBean getWLDFBuiltinSystemResourceDescriptorBean() {
      return this._customizer.getWLDFBuiltinSystemResourceDescriptorBean();
   }

   public boolean isWLDFBuiltinSystemResourceDescriptorBeanInherited() {
      return false;
   }

   public boolean isWLDFBuiltinSystemResourceDescriptorBeanSet() {
      return this._isSet(34);
   }

   public void setWLDFBuiltinSystemResourceDescriptorBean(WLDFResourceBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._WLDFBuiltinSystemResourceDescriptorBean = param0;
   }

   public String getDiagnosticDumpsDir() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._performMacroSubstitution(this._getDelegateBean().getDiagnosticDumpsDir(), this) : this._DiagnosticDumpsDir;
   }

   public boolean isDiagnosticDumpsDirInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isDiagnosticDumpsDirSet() {
      return this._isSet(35);
   }

   public void setDiagnosticDumpsDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WLDFValidator.validateRelativePath(this, param0);
      boolean wasSet = this._isSet(35);
      String _oldVal = this._DiagnosticDumpsDir;
      this._DiagnosticDumpsDir = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxHeapDumpCount() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().getMaxHeapDumpCount() : this._MaxHeapDumpCount;
   }

   public boolean isMaxHeapDumpCountInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isMaxHeapDumpCountSet() {
      return this._isSet(36);
   }

   public void setMaxHeapDumpCount(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxHeapDumpCount", (long)param0, 1L, 50L);
      boolean wasSet = this._isSet(36);
      int _oldVal = this._MaxHeapDumpCount;
      this._MaxHeapDumpCount = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxThreadDumpCount() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._getDelegateBean().getMaxThreadDumpCount() : this._MaxThreadDumpCount;
   }

   public boolean isMaxThreadDumpCountInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isMaxThreadDumpCountSet() {
      return this._isSet(37);
   }

   public void setMaxThreadDumpCount(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxThreadDumpCount", (long)param0, 1L, 1000L);
      boolean wasSet = this._isSet(37);
      int _oldVal = this._MaxThreadDumpCount;
      this._MaxThreadDumpCount = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFServerDiagnosticMBeanImpl source = (WLDFServerDiagnosticMBeanImpl)var4.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
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
         idx = 20;
      }

      try {
         switch (idx) {
            case 20:
               this._DiagnosticDataArchiveType = "FileStoreArchive";
               if (initOne) {
                  break;
               }
            case 35:
               this._DiagnosticDumpsDir = WLDFServerDiagnosticMBean.DEFAULT_DUMP_DIR;
               if (initOne) {
                  break;
               }
            case 21:
               this._DiagnosticJDBCResource = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._DiagnosticJDBCSchemaName = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._DiagnosticStoreBlockSize = -1;
               if (initOne) {
                  break;
               }
            case 13:
               this._DiagnosticStoreDir = "data/store/diagnostics";
               if (initOne) {
                  break;
               }
            case 17:
               this._DiagnosticStoreIoBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 18:
               this._DiagnosticStoreMaxFileSize = 1342177280L;
               if (initOne) {
                  break;
               }
            case 16:
               this._DiagnosticStoreMaxWindowBufferSize = 4194304;
               if (initOne) {
                  break;
               }
            case 15:
               this._DiagnosticStoreMinWindowBufferSize = -1;
               if (initOne) {
                  break;
               }
            case 24:
               this._EventPersistenceInterval = 5000L;
               if (initOne) {
                  break;
               }
            case 12:
               this._EventsImageCaptureInterval = 60000L;
               if (initOne) {
                  break;
               }
            case 10:
               this._ImageDir = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._ImageTimeout = 1;
               if (initOne) {
                  break;
               }
            case 36:
               this._MaxHeapDumpCount = 8;
               if (initOne) {
                  break;
               }
            case 37:
               this._MaxThreadDumpCount = 100;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 28:
               this._PreferredStoreSizeLimit = 100;
               if (initOne) {
                  break;
               }
            case 29:
               this._StoreSizeCheckPeriod = 1;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 34:
               this._WLDFBuiltinSystemResourceDescriptorBean = null;
               if (initOne) {
                  break;
               }
            case 33:
               this._WLDFBuiltinSystemResourceType = "None";
               if (initOne) {
                  break;
               }
            case 31:
               this._WLDFDataRetirementByAges = new WLDFDataRetirementByAgeMBean[0];
               if (initOne) {
                  break;
               }
            case 30:
               this._WLDFDataRetirements = new WLDFDataRetirementMBean[0];
               if (initOne) {
                  break;
               }
            case 32:
               this._WLDFDiagnosticVolume = "Low";
               if (initOne) {
                  break;
               }
            case 27:
               this._DataRetirementEnabled = true;
               if (initOne) {
                  break;
               }
            case 26:
               this._DataRetirementTestModeEnabled = false;
               if (initOne) {
                  break;
               }
            case 25:
               this._DiagnosticContextEnabled = true;
               if (initOne) {
                  break;
               }
            case 14:
               this._DiagnosticStoreFileLockingEnabled = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._SynchronousEventPersistenceEnabled = false;
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
      return "WLDFServerDiagnostic";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("DataRetirementEnabled")) {
         oldVal = this._DataRetirementEnabled;
         this._DataRetirementEnabled = (Boolean)v;
         this._postSet(27, oldVal, this._DataRetirementEnabled);
      } else if (name.equals("DataRetirementTestModeEnabled")) {
         oldVal = this._DataRetirementTestModeEnabled;
         this._DataRetirementTestModeEnabled = (Boolean)v;
         this._postSet(26, oldVal, this._DataRetirementTestModeEnabled);
      } else if (name.equals("DiagnosticContextEnabled")) {
         oldVal = this._DiagnosticContextEnabled;
         this._DiagnosticContextEnabled = (Boolean)v;
         this._postSet(25, oldVal, this._DiagnosticContextEnabled);
      } else {
         String oldVal;
         if (name.equals("DiagnosticDataArchiveType")) {
            oldVal = this._DiagnosticDataArchiveType;
            this._DiagnosticDataArchiveType = (String)v;
            this._postSet(20, oldVal, this._DiagnosticDataArchiveType);
         } else if (name.equals("DiagnosticDumpsDir")) {
            oldVal = this._DiagnosticDumpsDir;
            this._DiagnosticDumpsDir = (String)v;
            this._postSet(35, oldVal, this._DiagnosticDumpsDir);
         } else if (name.equals("DiagnosticJDBCResource")) {
            JDBCSystemResourceMBean oldVal = this._DiagnosticJDBCResource;
            this._DiagnosticJDBCResource = (JDBCSystemResourceMBean)v;
            this._postSet(21, oldVal, this._DiagnosticJDBCResource);
         } else if (name.equals("DiagnosticJDBCSchemaName")) {
            oldVal = this._DiagnosticJDBCSchemaName;
            this._DiagnosticJDBCSchemaName = (String)v;
            this._postSet(22, oldVal, this._DiagnosticJDBCSchemaName);
         } else {
            int oldVal;
            if (name.equals("DiagnosticStoreBlockSize")) {
               oldVal = this._DiagnosticStoreBlockSize;
               this._DiagnosticStoreBlockSize = (Integer)v;
               this._postSet(19, oldVal, this._DiagnosticStoreBlockSize);
            } else if (name.equals("DiagnosticStoreDir")) {
               oldVal = this._DiagnosticStoreDir;
               this._DiagnosticStoreDir = (String)v;
               this._postSet(13, oldVal, this._DiagnosticStoreDir);
            } else if (name.equals("DiagnosticStoreFileLockingEnabled")) {
               oldVal = this._DiagnosticStoreFileLockingEnabled;
               this._DiagnosticStoreFileLockingEnabled = (Boolean)v;
               this._postSet(14, oldVal, this._DiagnosticStoreFileLockingEnabled);
            } else if (name.equals("DiagnosticStoreIoBufferSize")) {
               oldVal = this._DiagnosticStoreIoBufferSize;
               this._DiagnosticStoreIoBufferSize = (Integer)v;
               this._postSet(17, oldVal, this._DiagnosticStoreIoBufferSize);
            } else {
               long oldVal;
               if (name.equals("DiagnosticStoreMaxFileSize")) {
                  oldVal = this._DiagnosticStoreMaxFileSize;
                  this._DiagnosticStoreMaxFileSize = (Long)v;
                  this._postSet(18, oldVal, this._DiagnosticStoreMaxFileSize);
               } else if (name.equals("DiagnosticStoreMaxWindowBufferSize")) {
                  oldVal = this._DiagnosticStoreMaxWindowBufferSize;
                  this._DiagnosticStoreMaxWindowBufferSize = (Integer)v;
                  this._postSet(16, oldVal, this._DiagnosticStoreMaxWindowBufferSize);
               } else if (name.equals("DiagnosticStoreMinWindowBufferSize")) {
                  oldVal = this._DiagnosticStoreMinWindowBufferSize;
                  this._DiagnosticStoreMinWindowBufferSize = (Integer)v;
                  this._postSet(15, oldVal, this._DiagnosticStoreMinWindowBufferSize);
               } else if (name.equals("DynamicallyCreated")) {
                  oldVal = this._DynamicallyCreated;
                  this._DynamicallyCreated = (Boolean)v;
                  this._postSet(7, oldVal, this._DynamicallyCreated);
               } else if (name.equals("EventPersistenceInterval")) {
                  oldVal = this._EventPersistenceInterval;
                  this._EventPersistenceInterval = (Long)v;
                  this._postSet(24, oldVal, this._EventPersistenceInterval);
               } else if (name.equals("EventsImageCaptureInterval")) {
                  oldVal = this._EventsImageCaptureInterval;
                  this._EventsImageCaptureInterval = (Long)v;
                  this._postSet(12, oldVal, this._EventsImageCaptureInterval);
               } else if (name.equals("ImageDir")) {
                  oldVal = this._ImageDir;
                  this._ImageDir = (String)v;
                  this._postSet(10, oldVal, this._ImageDir);
               } else if (name.equals("ImageTimeout")) {
                  oldVal = this._ImageTimeout;
                  this._ImageTimeout = (Integer)v;
                  this._postSet(11, oldVal, this._ImageTimeout);
               } else if (name.equals("MaxHeapDumpCount")) {
                  oldVal = this._MaxHeapDumpCount;
                  this._MaxHeapDumpCount = (Integer)v;
                  this._postSet(36, oldVal, this._MaxHeapDumpCount);
               } else if (name.equals("MaxThreadDumpCount")) {
                  oldVal = this._MaxThreadDumpCount;
                  this._MaxThreadDumpCount = (Integer)v;
                  this._postSet(37, oldVal, this._MaxThreadDumpCount);
               } else if (name.equals("Name")) {
                  oldVal = this._Name;
                  this._Name = (String)v;
                  this._postSet(2, oldVal, this._Name);
               } else if (name.equals("PreferredStoreSizeLimit")) {
                  oldVal = this._PreferredStoreSizeLimit;
                  this._PreferredStoreSizeLimit = (Integer)v;
                  this._postSet(28, oldVal, this._PreferredStoreSizeLimit);
               } else if (name.equals("StoreSizeCheckPeriod")) {
                  oldVal = this._StoreSizeCheckPeriod;
                  this._StoreSizeCheckPeriod = (Integer)v;
                  this._postSet(29, oldVal, this._StoreSizeCheckPeriod);
               } else if (name.equals("SynchronousEventPersistenceEnabled")) {
                  oldVal = this._SynchronousEventPersistenceEnabled;
                  this._SynchronousEventPersistenceEnabled = (Boolean)v;
                  this._postSet(23, oldVal, this._SynchronousEventPersistenceEnabled);
               } else if (name.equals("Tags")) {
                  String[] oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("WLDFBuiltinSystemResourceDescriptorBean")) {
                  WLDFResourceBean oldVal = this._WLDFBuiltinSystemResourceDescriptorBean;
                  this._WLDFBuiltinSystemResourceDescriptorBean = (WLDFResourceBean)v;
                  this._postSet(34, oldVal, this._WLDFBuiltinSystemResourceDescriptorBean);
               } else if (name.equals("WLDFBuiltinSystemResourceType")) {
                  oldVal = this._WLDFBuiltinSystemResourceType;
                  this._WLDFBuiltinSystemResourceType = (String)v;
                  this._postSet(33, oldVal, this._WLDFBuiltinSystemResourceType);
               } else if (name.equals("WLDFDataRetirementByAges")) {
                  WLDFDataRetirementByAgeMBean[] oldVal = this._WLDFDataRetirementByAges;
                  this._WLDFDataRetirementByAges = (WLDFDataRetirementByAgeMBean[])((WLDFDataRetirementByAgeMBean[])v);
                  this._postSet(31, oldVal, this._WLDFDataRetirementByAges);
               } else if (name.equals("WLDFDataRetirements")) {
                  WLDFDataRetirementMBean[] oldVal = this._WLDFDataRetirements;
                  this._WLDFDataRetirements = (WLDFDataRetirementMBean[])((WLDFDataRetirementMBean[])v);
                  this._postSet(30, oldVal, this._WLDFDataRetirements);
               } else if (name.equals("WLDFDiagnosticVolume")) {
                  oldVal = this._WLDFDiagnosticVolume;
                  this._WLDFDiagnosticVolume = (String)v;
                  this._postSet(32, oldVal, this._WLDFDiagnosticVolume);
               } else if (name.equals("customizer")) {
                  WLDFServerDiagnostic oldVal = this._customizer;
                  this._customizer = (WLDFServerDiagnostic)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DataRetirementEnabled")) {
         return new Boolean(this._DataRetirementEnabled);
      } else if (name.equals("DataRetirementTestModeEnabled")) {
         return new Boolean(this._DataRetirementTestModeEnabled);
      } else if (name.equals("DiagnosticContextEnabled")) {
         return new Boolean(this._DiagnosticContextEnabled);
      } else if (name.equals("DiagnosticDataArchiveType")) {
         return this._DiagnosticDataArchiveType;
      } else if (name.equals("DiagnosticDumpsDir")) {
         return this._DiagnosticDumpsDir;
      } else if (name.equals("DiagnosticJDBCResource")) {
         return this._DiagnosticJDBCResource;
      } else if (name.equals("DiagnosticJDBCSchemaName")) {
         return this._DiagnosticJDBCSchemaName;
      } else if (name.equals("DiagnosticStoreBlockSize")) {
         return new Integer(this._DiagnosticStoreBlockSize);
      } else if (name.equals("DiagnosticStoreDir")) {
         return this._DiagnosticStoreDir;
      } else if (name.equals("DiagnosticStoreFileLockingEnabled")) {
         return new Boolean(this._DiagnosticStoreFileLockingEnabled);
      } else if (name.equals("DiagnosticStoreIoBufferSize")) {
         return new Integer(this._DiagnosticStoreIoBufferSize);
      } else if (name.equals("DiagnosticStoreMaxFileSize")) {
         return new Long(this._DiagnosticStoreMaxFileSize);
      } else if (name.equals("DiagnosticStoreMaxWindowBufferSize")) {
         return new Integer(this._DiagnosticStoreMaxWindowBufferSize);
      } else if (name.equals("DiagnosticStoreMinWindowBufferSize")) {
         return new Integer(this._DiagnosticStoreMinWindowBufferSize);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("EventPersistenceInterval")) {
         return new Long(this._EventPersistenceInterval);
      } else if (name.equals("EventsImageCaptureInterval")) {
         return new Long(this._EventsImageCaptureInterval);
      } else if (name.equals("ImageDir")) {
         return this._ImageDir;
      } else if (name.equals("ImageTimeout")) {
         return new Integer(this._ImageTimeout);
      } else if (name.equals("MaxHeapDumpCount")) {
         return new Integer(this._MaxHeapDumpCount);
      } else if (name.equals("MaxThreadDumpCount")) {
         return new Integer(this._MaxThreadDumpCount);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PreferredStoreSizeLimit")) {
         return new Integer(this._PreferredStoreSizeLimit);
      } else if (name.equals("StoreSizeCheckPeriod")) {
         return new Integer(this._StoreSizeCheckPeriod);
      } else if (name.equals("SynchronousEventPersistenceEnabled")) {
         return new Boolean(this._SynchronousEventPersistenceEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("WLDFBuiltinSystemResourceDescriptorBean")) {
         return this._WLDFBuiltinSystemResourceDescriptorBean;
      } else if (name.equals("WLDFBuiltinSystemResourceType")) {
         return this._WLDFBuiltinSystemResourceType;
      } else if (name.equals("WLDFDataRetirementByAges")) {
         return this._WLDFDataRetirementByAges;
      } else if (name.equals("WLDFDataRetirements")) {
         return this._WLDFDataRetirements;
      } else if (name.equals("WLDFDiagnosticVolume")) {
         return this._WLDFDiagnosticVolume;
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
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 25:
            case 32:
            case 34:
            case 35:
            case 36:
            case 38:
            case 40:
            case 41:
            case 42:
            case 43:
            default:
               break;
            case 9:
               if (s.equals("image-dir")) {
                  return 10;
               }
               break;
            case 13:
               if (s.equals("image-timeout")) {
                  return 11;
               }
               break;
            case 19:
               if (s.equals("max-heap-dump-count")) {
                  return 36;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("diagnostic-dumps-dir")) {
                  return 35;
               }

               if (s.equals("diagnostic-store-dir")) {
                  return 13;
               }

               if (s.equals("wldf-data-retirement")) {
                  return 30;
               }
               break;
            case 21:
               if (s.equals("max-thread-dump-count")) {
                  return 37;
               }
               break;
            case 22:
               if (s.equals("wldf-diagnostic-volume")) {
                  return 32;
               }
               break;
            case 23:
               if (s.equals("store-size-check-period")) {
                  return 29;
               }

               if (s.equals("data-retirement-enabled")) {
                  return 27;
               }
               break;
            case 24:
               if (s.equals("diagnostic-jdbc-resource")) {
                  return 21;
               }
               break;
            case 26:
               if (s.equals("event-persistence-interval")) {
                  return 24;
               }

               if (s.equals("preferred-store-size-limit")) {
                  return 28;
               }

               if (s.equals("diagnostic-context-enabled")) {
                  return 25;
               }
               break;
            case 27:
               if (s.equals("diagnostic-jdbc-schema-name")) {
                  return 22;
               }

               if (s.equals("diagnostic-store-block-size")) {
                  return 19;
               }

               if (s.equals("wldf-data-retirement-by-age")) {
                  return 31;
               }
               break;
            case 28:
               if (s.equals("diagnostic-data-archive-type")) {
                  return 20;
               }
               break;
            case 29:
               if (s.equals("events-image-capture-interval")) {
                  return 12;
               }
               break;
            case 30:
               if (s.equals("diagnostic-store-max-file-size")) {
                  return 18;
               }
               break;
            case 31:
               if (s.equals("diagnostic-store-io-buffer-size")) {
                  return 17;
               }
               break;
            case 33:
               if (s.equals("wldf-builtin-system-resource-type")) {
                  return 33;
               }

               if (s.equals("data-retirement-test-mode-enabled")) {
                  return 26;
               }
               break;
            case 37:
               if (s.equals("diagnostic-store-file-locking-enabled")) {
                  return 14;
               }

               if (s.equals("synchronous-event-persistence-enabled")) {
                  return 23;
               }
               break;
            case 39:
               if (s.equals("diagnostic-store-max-window-buffer-size")) {
                  return 16;
               }

               if (s.equals("diagnostic-store-min-window-buffer-size")) {
                  return 15;
               }
               break;
            case 44:
               if (s.equals("wldf-builtin-system-resource-descriptor-bean")) {
                  return 34;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 31:
               return new WLDFDataRetirementByAgeMBeanImpl.SchemaHelper2();
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
               return "image-dir";
            case 11:
               return "image-timeout";
            case 12:
               return "events-image-capture-interval";
            case 13:
               return "diagnostic-store-dir";
            case 14:
               return "diagnostic-store-file-locking-enabled";
            case 15:
               return "diagnostic-store-min-window-buffer-size";
            case 16:
               return "diagnostic-store-max-window-buffer-size";
            case 17:
               return "diagnostic-store-io-buffer-size";
            case 18:
               return "diagnostic-store-max-file-size";
            case 19:
               return "diagnostic-store-block-size";
            case 20:
               return "diagnostic-data-archive-type";
            case 21:
               return "diagnostic-jdbc-resource";
            case 22:
               return "diagnostic-jdbc-schema-name";
            case 23:
               return "synchronous-event-persistence-enabled";
            case 24:
               return "event-persistence-interval";
            case 25:
               return "diagnostic-context-enabled";
            case 26:
               return "data-retirement-test-mode-enabled";
            case 27:
               return "data-retirement-enabled";
            case 28:
               return "preferred-store-size-limit";
            case 29:
               return "store-size-check-period";
            case 30:
               return "wldf-data-retirement";
            case 31:
               return "wldf-data-retirement-by-age";
            case 32:
               return "wldf-diagnostic-volume";
            case 33:
               return "wldf-builtin-system-resource-type";
            case 34:
               return "wldf-builtin-system-resource-descriptor-bean";
            case 35:
               return "diagnostic-dumps-dir";
            case 36:
               return "max-heap-dump-count";
            case 37:
               return "max-thread-dump-count";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 30:
               return true;
            case 31:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 31:
               return true;
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WLDFServerDiagnosticMBeanImpl bean;

      protected Helper(WLDFServerDiagnosticMBeanImpl bean) {
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
               return "ImageDir";
            case 11:
               return "ImageTimeout";
            case 12:
               return "EventsImageCaptureInterval";
            case 13:
               return "DiagnosticStoreDir";
            case 14:
               return "DiagnosticStoreFileLockingEnabled";
            case 15:
               return "DiagnosticStoreMinWindowBufferSize";
            case 16:
               return "DiagnosticStoreMaxWindowBufferSize";
            case 17:
               return "DiagnosticStoreIoBufferSize";
            case 18:
               return "DiagnosticStoreMaxFileSize";
            case 19:
               return "DiagnosticStoreBlockSize";
            case 20:
               return "DiagnosticDataArchiveType";
            case 21:
               return "DiagnosticJDBCResource";
            case 22:
               return "DiagnosticJDBCSchemaName";
            case 23:
               return "SynchronousEventPersistenceEnabled";
            case 24:
               return "EventPersistenceInterval";
            case 25:
               return "DiagnosticContextEnabled";
            case 26:
               return "DataRetirementTestModeEnabled";
            case 27:
               return "DataRetirementEnabled";
            case 28:
               return "PreferredStoreSizeLimit";
            case 29:
               return "StoreSizeCheckPeriod";
            case 30:
               return "WLDFDataRetirements";
            case 31:
               return "WLDFDataRetirementByAges";
            case 32:
               return "WLDFDiagnosticVolume";
            case 33:
               return "WLDFBuiltinSystemResourceType";
            case 34:
               return "WLDFBuiltinSystemResourceDescriptorBean";
            case 35:
               return "DiagnosticDumpsDir";
            case 36:
               return "MaxHeapDumpCount";
            case 37:
               return "MaxThreadDumpCount";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DiagnosticDataArchiveType")) {
            return 20;
         } else if (propName.equals("DiagnosticDumpsDir")) {
            return 35;
         } else if (propName.equals("DiagnosticJDBCResource")) {
            return 21;
         } else if (propName.equals("DiagnosticJDBCSchemaName")) {
            return 22;
         } else if (propName.equals("DiagnosticStoreBlockSize")) {
            return 19;
         } else if (propName.equals("DiagnosticStoreDir")) {
            return 13;
         } else if (propName.equals("DiagnosticStoreIoBufferSize")) {
            return 17;
         } else if (propName.equals("DiagnosticStoreMaxFileSize")) {
            return 18;
         } else if (propName.equals("DiagnosticStoreMaxWindowBufferSize")) {
            return 16;
         } else if (propName.equals("DiagnosticStoreMinWindowBufferSize")) {
            return 15;
         } else if (propName.equals("EventPersistenceInterval")) {
            return 24;
         } else if (propName.equals("EventsImageCaptureInterval")) {
            return 12;
         } else if (propName.equals("ImageDir")) {
            return 10;
         } else if (propName.equals("ImageTimeout")) {
            return 11;
         } else if (propName.equals("MaxHeapDumpCount")) {
            return 36;
         } else if (propName.equals("MaxThreadDumpCount")) {
            return 37;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PreferredStoreSizeLimit")) {
            return 28;
         } else if (propName.equals("StoreSizeCheckPeriod")) {
            return 29;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("WLDFBuiltinSystemResourceDescriptorBean")) {
            return 34;
         } else if (propName.equals("WLDFBuiltinSystemResourceType")) {
            return 33;
         } else if (propName.equals("WLDFDataRetirementByAges")) {
            return 31;
         } else if (propName.equals("WLDFDataRetirements")) {
            return 30;
         } else if (propName.equals("WLDFDiagnosticVolume")) {
            return 32;
         } else if (propName.equals("DataRetirementEnabled")) {
            return 27;
         } else if (propName.equals("DataRetirementTestModeEnabled")) {
            return 26;
         } else if (propName.equals("DiagnosticContextEnabled")) {
            return 25;
         } else if (propName.equals("DiagnosticStoreFileLockingEnabled")) {
            return 14;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("SynchronousEventPersistenceEnabled") ? 23 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWLDFDataRetirementByAges()));
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
            if (this.bean.isDiagnosticDataArchiveTypeSet()) {
               buf.append("DiagnosticDataArchiveType");
               buf.append(String.valueOf(this.bean.getDiagnosticDataArchiveType()));
            }

            if (this.bean.isDiagnosticDumpsDirSet()) {
               buf.append("DiagnosticDumpsDir");
               buf.append(String.valueOf(this.bean.getDiagnosticDumpsDir()));
            }

            if (this.bean.isDiagnosticJDBCResourceSet()) {
               buf.append("DiagnosticJDBCResource");
               buf.append(String.valueOf(this.bean.getDiagnosticJDBCResource()));
            }

            if (this.bean.isDiagnosticJDBCSchemaNameSet()) {
               buf.append("DiagnosticJDBCSchemaName");
               buf.append(String.valueOf(this.bean.getDiagnosticJDBCSchemaName()));
            }

            if (this.bean.isDiagnosticStoreBlockSizeSet()) {
               buf.append("DiagnosticStoreBlockSize");
               buf.append(String.valueOf(this.bean.getDiagnosticStoreBlockSize()));
            }

            if (this.bean.isDiagnosticStoreDirSet()) {
               buf.append("DiagnosticStoreDir");
               buf.append(String.valueOf(this.bean.getDiagnosticStoreDir()));
            }

            if (this.bean.isDiagnosticStoreIoBufferSizeSet()) {
               buf.append("DiagnosticStoreIoBufferSize");
               buf.append(String.valueOf(this.bean.getDiagnosticStoreIoBufferSize()));
            }

            if (this.bean.isDiagnosticStoreMaxFileSizeSet()) {
               buf.append("DiagnosticStoreMaxFileSize");
               buf.append(String.valueOf(this.bean.getDiagnosticStoreMaxFileSize()));
            }

            if (this.bean.isDiagnosticStoreMaxWindowBufferSizeSet()) {
               buf.append("DiagnosticStoreMaxWindowBufferSize");
               buf.append(String.valueOf(this.bean.getDiagnosticStoreMaxWindowBufferSize()));
            }

            if (this.bean.isDiagnosticStoreMinWindowBufferSizeSet()) {
               buf.append("DiagnosticStoreMinWindowBufferSize");
               buf.append(String.valueOf(this.bean.getDiagnosticStoreMinWindowBufferSize()));
            }

            if (this.bean.isEventPersistenceIntervalSet()) {
               buf.append("EventPersistenceInterval");
               buf.append(String.valueOf(this.bean.getEventPersistenceInterval()));
            }

            if (this.bean.isEventsImageCaptureIntervalSet()) {
               buf.append("EventsImageCaptureInterval");
               buf.append(String.valueOf(this.bean.getEventsImageCaptureInterval()));
            }

            if (this.bean.isImageDirSet()) {
               buf.append("ImageDir");
               buf.append(String.valueOf(this.bean.getImageDir()));
            }

            if (this.bean.isImageTimeoutSet()) {
               buf.append("ImageTimeout");
               buf.append(String.valueOf(this.bean.getImageTimeout()));
            }

            if (this.bean.isMaxHeapDumpCountSet()) {
               buf.append("MaxHeapDumpCount");
               buf.append(String.valueOf(this.bean.getMaxHeapDumpCount()));
            }

            if (this.bean.isMaxThreadDumpCountSet()) {
               buf.append("MaxThreadDumpCount");
               buf.append(String.valueOf(this.bean.getMaxThreadDumpCount()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPreferredStoreSizeLimitSet()) {
               buf.append("PreferredStoreSizeLimit");
               buf.append(String.valueOf(this.bean.getPreferredStoreSizeLimit()));
            }

            if (this.bean.isStoreSizeCheckPeriodSet()) {
               buf.append("StoreSizeCheckPeriod");
               buf.append(String.valueOf(this.bean.getStoreSizeCheckPeriod()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isWLDFBuiltinSystemResourceDescriptorBeanSet()) {
               buf.append("WLDFBuiltinSystemResourceDescriptorBean");
               buf.append(String.valueOf(this.bean.getWLDFBuiltinSystemResourceDescriptorBean()));
            }

            if (this.bean.isWLDFBuiltinSystemResourceTypeSet()) {
               buf.append("WLDFBuiltinSystemResourceType");
               buf.append(String.valueOf(this.bean.getWLDFBuiltinSystemResourceType()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getWLDFDataRetirementByAges().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWLDFDataRetirementByAges()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWLDFDataRetirementsSet()) {
               buf.append("WLDFDataRetirements");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getWLDFDataRetirements())));
            }

            if (this.bean.isWLDFDiagnosticVolumeSet()) {
               buf.append("WLDFDiagnosticVolume");
               buf.append(String.valueOf(this.bean.getWLDFDiagnosticVolume()));
            }

            if (this.bean.isDataRetirementEnabledSet()) {
               buf.append("DataRetirementEnabled");
               buf.append(String.valueOf(this.bean.isDataRetirementEnabled()));
            }

            if (this.bean.isDataRetirementTestModeEnabledSet()) {
               buf.append("DataRetirementTestModeEnabled");
               buf.append(String.valueOf(this.bean.isDataRetirementTestModeEnabled()));
            }

            if (this.bean.isDiagnosticContextEnabledSet()) {
               buf.append("DiagnosticContextEnabled");
               buf.append(String.valueOf(this.bean.isDiagnosticContextEnabled()));
            }

            if (this.bean.isDiagnosticStoreFileLockingEnabledSet()) {
               buf.append("DiagnosticStoreFileLockingEnabled");
               buf.append(String.valueOf(this.bean.isDiagnosticStoreFileLockingEnabled()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isSynchronousEventPersistenceEnabledSet()) {
               buf.append("SynchronousEventPersistenceEnabled");
               buf.append(String.valueOf(this.bean.isSynchronousEventPersistenceEnabled()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            WLDFServerDiagnosticMBeanImpl otherTyped = (WLDFServerDiagnosticMBeanImpl)other;
            this.computeDiff("DiagnosticDataArchiveType", this.bean.getDiagnosticDataArchiveType(), otherTyped.getDiagnosticDataArchiveType(), false);
            this.computeDiff("DiagnosticDumpsDir", this.bean.getDiagnosticDumpsDir(), otherTyped.getDiagnosticDumpsDir(), true);
            this.computeDiff("DiagnosticJDBCResource", this.bean.getDiagnosticJDBCResource(), otherTyped.getDiagnosticJDBCResource(), false);
            this.computeDiff("DiagnosticJDBCSchemaName", this.bean.getDiagnosticJDBCSchemaName(), otherTyped.getDiagnosticJDBCSchemaName(), false);
            this.computeDiff("DiagnosticStoreBlockSize", this.bean.getDiagnosticStoreBlockSize(), otherTyped.getDiagnosticStoreBlockSize(), false);
            this.computeDiff("DiagnosticStoreDir", this.bean.getDiagnosticStoreDir(), otherTyped.getDiagnosticStoreDir(), false);
            this.computeDiff("DiagnosticStoreIoBufferSize", this.bean.getDiagnosticStoreIoBufferSize(), otherTyped.getDiagnosticStoreIoBufferSize(), false);
            this.computeDiff("DiagnosticStoreMaxFileSize", this.bean.getDiagnosticStoreMaxFileSize(), otherTyped.getDiagnosticStoreMaxFileSize(), false);
            this.computeDiff("DiagnosticStoreMaxWindowBufferSize", this.bean.getDiagnosticStoreMaxWindowBufferSize(), otherTyped.getDiagnosticStoreMaxWindowBufferSize(), false);
            this.computeDiff("DiagnosticStoreMinWindowBufferSize", this.bean.getDiagnosticStoreMinWindowBufferSize(), otherTyped.getDiagnosticStoreMinWindowBufferSize(), false);
            this.computeDiff("EventPersistenceInterval", this.bean.getEventPersistenceInterval(), otherTyped.getEventPersistenceInterval(), true);
            this.computeDiff("EventsImageCaptureInterval", this.bean.getEventsImageCaptureInterval(), otherTyped.getEventsImageCaptureInterval(), true);
            this.computeDiff("ImageDir", this.bean.getImageDir(), otherTyped.getImageDir(), true);
            this.computeDiff("ImageTimeout", this.bean.getImageTimeout(), otherTyped.getImageTimeout(), true);
            this.computeDiff("MaxHeapDumpCount", this.bean.getMaxHeapDumpCount(), otherTyped.getMaxHeapDumpCount(), true);
            this.computeDiff("MaxThreadDumpCount", this.bean.getMaxThreadDumpCount(), otherTyped.getMaxThreadDumpCount(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PreferredStoreSizeLimit", this.bean.getPreferredStoreSizeLimit(), otherTyped.getPreferredStoreSizeLimit(), true);
            this.computeDiff("StoreSizeCheckPeriod", this.bean.getStoreSizeCheckPeriod(), otherTyped.getStoreSizeCheckPeriod(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("WLDFBuiltinSystemResourceType", this.bean.getWLDFBuiltinSystemResourceType(), otherTyped.getWLDFBuiltinSystemResourceType(), true);
            this.computeChildDiff("WLDFDataRetirementByAges", this.bean.getWLDFDataRetirementByAges(), otherTyped.getWLDFDataRetirementByAges(), true);
            this.computeDiff("WLDFDiagnosticVolume", this.bean.getWLDFDiagnosticVolume(), otherTyped.getWLDFDiagnosticVolume(), true);
            this.computeDiff("DataRetirementEnabled", this.bean.isDataRetirementEnabled(), otherTyped.isDataRetirementEnabled(), true);
            this.computeDiff("DataRetirementTestModeEnabled", this.bean.isDataRetirementTestModeEnabled(), otherTyped.isDataRetirementTestModeEnabled(), false);
            this.computeDiff("DiagnosticContextEnabled", this.bean.isDiagnosticContextEnabled(), otherTyped.isDiagnosticContextEnabled(), true);
            this.computeDiff("DiagnosticStoreFileLockingEnabled", this.bean.isDiagnosticStoreFileLockingEnabled(), otherTyped.isDiagnosticStoreFileLockingEnabled(), false);
            this.computeDiff("SynchronousEventPersistenceEnabled", this.bean.isSynchronousEventPersistenceEnabled(), otherTyped.isSynchronousEventPersistenceEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFServerDiagnosticMBeanImpl original = (WLDFServerDiagnosticMBeanImpl)event.getSourceBean();
            WLDFServerDiagnosticMBeanImpl proposed = (WLDFServerDiagnosticMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DiagnosticDataArchiveType")) {
                  original.setDiagnosticDataArchiveType(proposed.getDiagnosticDataArchiveType());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("DiagnosticDumpsDir")) {
                  original.setDiagnosticDumpsDir(proposed.getDiagnosticDumpsDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 35);
               } else if (prop.equals("DiagnosticJDBCResource")) {
                  original.setDiagnosticJDBCResourceAsString(proposed.getDiagnosticJDBCResourceAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("DiagnosticJDBCSchemaName")) {
                  original.setDiagnosticJDBCSchemaName(proposed.getDiagnosticJDBCSchemaName());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("DiagnosticStoreBlockSize")) {
                  original.setDiagnosticStoreBlockSize(proposed.getDiagnosticStoreBlockSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("DiagnosticStoreDir")) {
                  original.setDiagnosticStoreDir(proposed.getDiagnosticStoreDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DiagnosticStoreIoBufferSize")) {
                  original.setDiagnosticStoreIoBufferSize(proposed.getDiagnosticStoreIoBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("DiagnosticStoreMaxFileSize")) {
                  original.setDiagnosticStoreMaxFileSize(proposed.getDiagnosticStoreMaxFileSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("DiagnosticStoreMaxWindowBufferSize")) {
                  original.setDiagnosticStoreMaxWindowBufferSize(proposed.getDiagnosticStoreMaxWindowBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("DiagnosticStoreMinWindowBufferSize")) {
                  original.setDiagnosticStoreMinWindowBufferSize(proposed.getDiagnosticStoreMinWindowBufferSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("EventPersistenceInterval")) {
                  original.setEventPersistenceInterval(proposed.getEventPersistenceInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("EventsImageCaptureInterval")) {
                  original.setEventsImageCaptureInterval(proposed.getEventsImageCaptureInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ImageDir")) {
                  original.setImageDir(proposed.getImageDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ImageTimeout")) {
                  original.setImageTimeout(proposed.getImageTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MaxHeapDumpCount")) {
                  original.setMaxHeapDumpCount(proposed.getMaxHeapDumpCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 36);
               } else if (prop.equals("MaxThreadDumpCount")) {
                  original.setMaxThreadDumpCount(proposed.getMaxThreadDumpCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 37);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("PreferredStoreSizeLimit")) {
                  original.setPreferredStoreSizeLimit(proposed.getPreferredStoreSizeLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("StoreSizeCheckPeriod")) {
                  original.setStoreSizeCheckPeriod(proposed.getStoreSizeCheckPeriod());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
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
               } else if (!prop.equals("WLDFBuiltinSystemResourceDescriptorBean")) {
                  if (prop.equals("WLDFBuiltinSystemResourceType")) {
                     original.setWLDFBuiltinSystemResourceType(proposed.getWLDFBuiltinSystemResourceType());
                     original._conditionalUnset(update.isUnsetUpdate(), 33);
                  } else if (prop.equals("WLDFDataRetirementByAges")) {
                     if (type == 2) {
                        if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                           update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                           original.addWLDFDataRetirementByAge((WLDFDataRetirementByAgeMBean)update.getAddedObject());
                        }
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original.removeWLDFDataRetirementByAge((WLDFDataRetirementByAgeMBean)update.getRemovedObject());
                     }

                     if (original.getWLDFDataRetirementByAges() == null || original.getWLDFDataRetirementByAges().length == 0) {
                        original._conditionalUnset(update.isUnsetUpdate(), 31);
                     }
                  } else if (!prop.equals("WLDFDataRetirements")) {
                     if (prop.equals("WLDFDiagnosticVolume")) {
                        original.setWLDFDiagnosticVolume(proposed.getWLDFDiagnosticVolume());
                        original._conditionalUnset(update.isUnsetUpdate(), 32);
                     } else if (prop.equals("DataRetirementEnabled")) {
                        original.setDataRetirementEnabled(proposed.isDataRetirementEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 27);
                     } else if (prop.equals("DataRetirementTestModeEnabled")) {
                        original.setDataRetirementTestModeEnabled(proposed.isDataRetirementTestModeEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 26);
                     } else if (prop.equals("DiagnosticContextEnabled")) {
                        original.setDiagnosticContextEnabled(proposed.isDiagnosticContextEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 25);
                     } else if (prop.equals("DiagnosticStoreFileLockingEnabled")) {
                        original.setDiagnosticStoreFileLockingEnabled(proposed.isDiagnosticStoreFileLockingEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 14);
                     } else if (!prop.equals("DynamicallyCreated")) {
                        if (prop.equals("SynchronousEventPersistenceEnabled")) {
                           original.setSynchronousEventPersistenceEnabled(proposed.isSynchronousEventPersistenceEnabled());
                           original._conditionalUnset(update.isUnsetUpdate(), 23);
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
            WLDFServerDiagnosticMBeanImpl copy = (WLDFServerDiagnosticMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DiagnosticDataArchiveType")) && this.bean.isDiagnosticDataArchiveTypeSet()) {
               copy.setDiagnosticDataArchiveType(this.bean.getDiagnosticDataArchiveType());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticDumpsDir")) && this.bean.isDiagnosticDumpsDirSet()) {
               copy.setDiagnosticDumpsDir(this.bean.getDiagnosticDumpsDir());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticJDBCResource")) && this.bean.isDiagnosticJDBCResourceSet()) {
               copy._unSet(copy, 21);
               copy.setDiagnosticJDBCResourceAsString(this.bean.getDiagnosticJDBCResourceAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticJDBCSchemaName")) && this.bean.isDiagnosticJDBCSchemaNameSet()) {
               copy.setDiagnosticJDBCSchemaName(this.bean.getDiagnosticJDBCSchemaName());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticStoreBlockSize")) && this.bean.isDiagnosticStoreBlockSizeSet()) {
               copy.setDiagnosticStoreBlockSize(this.bean.getDiagnosticStoreBlockSize());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticStoreDir")) && this.bean.isDiagnosticStoreDirSet()) {
               copy.setDiagnosticStoreDir(this.bean.getDiagnosticStoreDir());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticStoreIoBufferSize")) && this.bean.isDiagnosticStoreIoBufferSizeSet()) {
               copy.setDiagnosticStoreIoBufferSize(this.bean.getDiagnosticStoreIoBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticStoreMaxFileSize")) && this.bean.isDiagnosticStoreMaxFileSizeSet()) {
               copy.setDiagnosticStoreMaxFileSize(this.bean.getDiagnosticStoreMaxFileSize());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticStoreMaxWindowBufferSize")) && this.bean.isDiagnosticStoreMaxWindowBufferSizeSet()) {
               copy.setDiagnosticStoreMaxWindowBufferSize(this.bean.getDiagnosticStoreMaxWindowBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticStoreMinWindowBufferSize")) && this.bean.isDiagnosticStoreMinWindowBufferSizeSet()) {
               copy.setDiagnosticStoreMinWindowBufferSize(this.bean.getDiagnosticStoreMinWindowBufferSize());
            }

            if ((excludeProps == null || !excludeProps.contains("EventPersistenceInterval")) && this.bean.isEventPersistenceIntervalSet()) {
               copy.setEventPersistenceInterval(this.bean.getEventPersistenceInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("EventsImageCaptureInterval")) && this.bean.isEventsImageCaptureIntervalSet()) {
               copy.setEventsImageCaptureInterval(this.bean.getEventsImageCaptureInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("ImageDir")) && this.bean.isImageDirSet()) {
               copy.setImageDir(this.bean.getImageDir());
            }

            if ((excludeProps == null || !excludeProps.contains("ImageTimeout")) && this.bean.isImageTimeoutSet()) {
               copy.setImageTimeout(this.bean.getImageTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxHeapDumpCount")) && this.bean.isMaxHeapDumpCountSet()) {
               copy.setMaxHeapDumpCount(this.bean.getMaxHeapDumpCount());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxThreadDumpCount")) && this.bean.isMaxThreadDumpCountSet()) {
               copy.setMaxThreadDumpCount(this.bean.getMaxThreadDumpCount());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PreferredStoreSizeLimit")) && this.bean.isPreferredStoreSizeLimitSet()) {
               copy.setPreferredStoreSizeLimit(this.bean.getPreferredStoreSizeLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("StoreSizeCheckPeriod")) && this.bean.isStoreSizeCheckPeriodSet()) {
               copy.setStoreSizeCheckPeriod(this.bean.getStoreSizeCheckPeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("WLDFBuiltinSystemResourceType")) && this.bean.isWLDFBuiltinSystemResourceTypeSet()) {
               copy.setWLDFBuiltinSystemResourceType(this.bean.getWLDFBuiltinSystemResourceType());
            }

            if ((excludeProps == null || !excludeProps.contains("WLDFDataRetirementByAges")) && this.bean.isWLDFDataRetirementByAgesSet() && !copy._isSet(31)) {
               WLDFDataRetirementByAgeMBean[] oldWLDFDataRetirementByAges = this.bean.getWLDFDataRetirementByAges();
               WLDFDataRetirementByAgeMBean[] newWLDFDataRetirementByAges = new WLDFDataRetirementByAgeMBean[oldWLDFDataRetirementByAges.length];

               for(int i = 0; i < newWLDFDataRetirementByAges.length; ++i) {
                  newWLDFDataRetirementByAges[i] = (WLDFDataRetirementByAgeMBean)((WLDFDataRetirementByAgeMBean)this.createCopy((AbstractDescriptorBean)oldWLDFDataRetirementByAges[i], includeObsolete));
               }

               copy.setWLDFDataRetirementByAges(newWLDFDataRetirementByAges);
            }

            if ((excludeProps == null || !excludeProps.contains("WLDFDiagnosticVolume")) && this.bean.isWLDFDiagnosticVolumeSet()) {
               copy.setWLDFDiagnosticVolume(this.bean.getWLDFDiagnosticVolume());
            }

            if ((excludeProps == null || !excludeProps.contains("DataRetirementEnabled")) && this.bean.isDataRetirementEnabledSet()) {
               copy.setDataRetirementEnabled(this.bean.isDataRetirementEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DataRetirementTestModeEnabled")) && this.bean.isDataRetirementTestModeEnabledSet()) {
               copy.setDataRetirementTestModeEnabled(this.bean.isDataRetirementTestModeEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticContextEnabled")) && this.bean.isDiagnosticContextEnabledSet()) {
               copy.setDiagnosticContextEnabled(this.bean.isDiagnosticContextEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DiagnosticStoreFileLockingEnabled")) && this.bean.isDiagnosticStoreFileLockingEnabledSet()) {
               copy.setDiagnosticStoreFileLockingEnabled(this.bean.isDiagnosticStoreFileLockingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SynchronousEventPersistenceEnabled")) && this.bean.isSynchronousEventPersistenceEnabledSet()) {
               copy.setSynchronousEventPersistenceEnabled(this.bean.isSynchronousEventPersistenceEnabled());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getDiagnosticJDBCResource(), clazz, annotation);
         this.inferSubTree(this.bean.getWLDFDataRetirementByAges(), clazz, annotation);
         this.inferSubTree(this.bean.getWLDFDataRetirements(), clazz, annotation);
      }
   }
}

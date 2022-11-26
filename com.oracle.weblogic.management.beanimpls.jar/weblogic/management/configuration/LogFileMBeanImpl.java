package weblogic.management.configuration;

import com.bea.logging.DateFormatter;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
import weblogic.management.mbeans.custom.LogFile;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class LogFileMBeanImpl extends ConfigurationMBeanImpl implements LogFileMBean, Serializable {
   private int _BufferSizeKB;
   private String _DateFormatPattern;
   private boolean _DynamicallyCreated;
   private int _FileCount;
   private int _FileMinSize;
   private String _FileName;
   private int _FileTimeSpan;
   private long _FileTimeSpanFactor;
   private String _LogFilePath;
   private String _LogFileRotationDir;
   private String _LogRotationDirPath;
   private String _Name;
   private boolean _NumberOfFilesLimited;
   private OutputStream _OutputStream;
   private boolean _RotateLogOnStartup;
   private String _RotationTime;
   private String _RotationType;
   private String[] _Tags;
   private transient LogFile _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private LogFileMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(LogFileMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(LogFileMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public LogFileMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(LogFileMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      LogFileMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public LogFileMBeanImpl() {
      try {
         this._customizer = new LogFile(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public LogFileMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new LogFile(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public LogFileMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new LogFile(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getDateFormatPattern() {
      if (!this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10)) {
         return this._performMacroSubstitution(this._getDelegateBean().getDateFormatPattern(), this);
      } else {
         if (!this._isSet(10)) {
            try {
               return DateFormatter.getDefaultDateFormatPattern();
            } catch (NullPointerException var2) {
            }
         }

         return this._DateFormatPattern;
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

   public boolean isDateFormatPatternInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isDateFormatPatternSet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setDateFormatPattern(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkNonEmptyString("DateFormatPattern", param0);
      LegalChecks.checkNonNull("DateFormatPattern", param0);
      DateFormatter.validateDateFormatPattern(param0);
      boolean wasSet = this._isSet(10);
      String _oldVal = this._DateFormatPattern;
      this._DateFormatPattern = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
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
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public String getFileName() {
      if (!this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         return this._performMacroSubstitution(this._getDelegateBean().getFileName(), this);
      } else {
         if (!this._isSet(11)) {
            try {
               return "logs/" + this.getName() + ".log";
            } catch (NullPointerException var2) {
            }
         }

         return this._FileName;
      }
   }

   public boolean isFileNameInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isFileNameSet() {
      return this._isSet(11);
   }

   public void setFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._FileName;
      this._FileName = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getRotationType() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getRotationType(), this) : this._RotationType;
   }

   public boolean isRotationTypeInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isRotationTypeSet() {
      return this._isSet(12);
   }

   public void setRotationType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"bySize", "byTime", "none", "bySizeOrTime"};
      param0 = LegalChecks.checkInEnum("RotationType", param0, _set);
      boolean wasSet = this._isSet(12);
      String _oldVal = this._RotationType;
      this._RotationType = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var5.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public void setNumberOfFilesLimited(boolean param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      boolean _oldVal = this._NumberOfFilesLimited;
      this._NumberOfFilesLimited = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isNumberOfFilesLimited() {
      if (!this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         return this._getDelegateBean().isNumberOfFilesLimited();
      } else if (!this._isSet(13)) {
         return this._isProductionModeEnabled() ? true : true;
      } else {
         return this._NumberOfFilesLimited;
      }
   }

   public boolean isNumberOfFilesLimitedInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isNumberOfFilesLimitedSet() {
      return this._isSet(13);
   }

   public int getFileCount() {
      if (!this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         return this._getDelegateBean().getFileCount();
      } else if (!this._isSet(14)) {
         return this._isProductionModeEnabled() ? 100 : 7;
      } else {
         return this._FileCount;
      }
   }

   public boolean isFileCountInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isFileCountSet() {
      return this._isSet(14);
   }

   public void setFileCount(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("FileCount", (long)param0, 1L, 99999L);
      boolean wasSet = this._isSet(14);
      int _oldVal = this._FileCount;
      this._FileCount = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public int getFileTimeSpan() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getFileTimeSpan() : this._FileTimeSpan;
   }

   public boolean isFileTimeSpanInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isFileTimeSpanSet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public String getRotationTime() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getRotationTime(), this) : this._RotationTime;
   }

   public boolean isRotationTimeInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isRotationTimeSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setRotationTime(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LoggingLegalHelper.validateLogTimeString(param0);
      boolean wasSet = this._isSet(16);
      String _oldVal = this._RotationTime;
      this._RotationTime = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public void setFileTimeSpan(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("FileTimeSpan", param0, 1);
      boolean wasSet = this._isSet(15);
      int _oldVal = this._FileTimeSpan;
      this._FileTimeSpan = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public long getFileTimeSpanFactor() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._getDelegateBean().getFileTimeSpanFactor() : this._FileTimeSpanFactor;
   }

   public boolean isFileTimeSpanFactorInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isFileTimeSpanFactorSet() {
      return this._isSet(17);
   }

   public void setFileTimeSpanFactor(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      long _oldVal = this._FileTimeSpanFactor;
      this._FileTimeSpanFactor = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var6.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public int getFileMinSize() {
      if (!this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18)) {
         return this._getDelegateBean().getFileMinSize();
      } else if (!this._isSet(18)) {
         return this._isProductionModeEnabled() ? 5000 : 500;
      } else {
         return this._FileMinSize;
      }
   }

   public boolean isFileMinSizeInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isFileMinSizeSet() {
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
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public void setFileMinSize(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("FileMinSize", (long)param0, 1L, 2097150L);
      boolean wasSet = this._isSet(18);
      int _oldVal = this._FileMinSize;
      this._FileMinSize = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean getRotateLogOnStartup() {
      if (!this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19)) {
         return this._getDelegateBean().getRotateLogOnStartup();
      } else if (!this._isSet(19)) {
         return !this._isProductionModeEnabled();
      } else {
         return this._RotateLogOnStartup;
      }
   }

   public boolean isRotateLogOnStartupInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isRotateLogOnStartupSet() {
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

   public void setRotateLogOnStartup(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      boolean _oldVal = this._RotateLogOnStartup;
      this._RotateLogOnStartup = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLogFileRotationDir() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._performMacroSubstitution(this._getDelegateBean().getLogFileRotationDir(), this) : this._LogFileRotationDir;
   }

   public boolean isLogFileRotationDirInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isLogFileRotationDirSet() {
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
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
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

   public void setLogFileRotationDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      String _oldVal = this._LogFileRotationDir;
      this._LogFileRotationDir = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public String computeLogFilePath() {
      return this._customizer.computeLogFilePath();
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

   public String getLogFilePath() {
      return this._customizer.getLogFilePath();
   }

   public boolean isLogFilePathInherited() {
      return false;
   }

   public boolean isLogFilePathSet() {
      return this._isSet(21);
   }

   public void setLogFilePath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LogFilePath = param0;
   }

   public String getLogRotationDirPath() {
      return this._customizer.getLogRotationDirPath();
   }

   public boolean isLogRotationDirPathInherited() {
      return false;
   }

   public boolean isLogRotationDirPathSet() {
      return this._isSet(22);
   }

   public void setLogRotationDirPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._LogRotationDirPath = param0;
   }

   public OutputStream getOutputStream() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().getOutputStream() : this._OutputStream;
   }

   public boolean isOutputStreamInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isOutputStreamSet() {
      return this._isSet(23);
   }

   public void setOutputStream(OutputStream param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._OutputStream = param0;
   }

   public int getBufferSizeKB() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().getBufferSizeKB() : this._BufferSizeKB;
   }

   public boolean isBufferSizeKBInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isBufferSizeKBSet() {
      return this._isSet(24);
   }

   public void setBufferSizeKB(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      int _oldVal = this._BufferSizeKB;
      this._BufferSizeKB = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         LogFileMBeanImpl source = (LogFileMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
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
         idx = 24;
      }

      try {
         switch (idx) {
            case 24:
               this._BufferSizeKB = 8;
               if (initOne) {
                  break;
               }
            case 10:
               this._DateFormatPattern = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._FileCount = 7;
               if (initOne) {
                  break;
               }
            case 18:
               this._FileMinSize = 500;
               if (initOne) {
                  break;
               }
            case 11:
               this._FileName = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._FileTimeSpan = 24;
               if (initOne) {
                  break;
               }
            case 17:
               this._FileTimeSpanFactor = 3600000L;
               if (initOne) {
                  break;
               }
            case 21:
               this._LogFilePath = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._LogFileRotationDir = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._LogRotationDirPath = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 23:
               this._OutputStream = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._RotateLogOnStartup = true;
               if (initOne) {
                  break;
               }
            case 16:
               this._RotationTime = "00:00";
               if (initOne) {
                  break;
               }
            case 12:
               this._RotationType = "bySize";
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
            case 13:
               this._NumberOfFilesLimited = true;
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
      return "LogFile";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("BufferSizeKB")) {
         oldVal = this._BufferSizeKB;
         this._BufferSizeKB = (Integer)v;
         this._postSet(24, oldVal, this._BufferSizeKB);
      } else {
         String oldVal;
         if (name.equals("DateFormatPattern")) {
            oldVal = this._DateFormatPattern;
            this._DateFormatPattern = (String)v;
            this._postSet(10, oldVal, this._DateFormatPattern);
         } else {
            boolean oldVal;
            if (name.equals("DynamicallyCreated")) {
               oldVal = this._DynamicallyCreated;
               this._DynamicallyCreated = (Boolean)v;
               this._postSet(7, oldVal, this._DynamicallyCreated);
            } else if (name.equals("FileCount")) {
               oldVal = this._FileCount;
               this._FileCount = (Integer)v;
               this._postSet(14, oldVal, this._FileCount);
            } else if (name.equals("FileMinSize")) {
               oldVal = this._FileMinSize;
               this._FileMinSize = (Integer)v;
               this._postSet(18, oldVal, this._FileMinSize);
            } else if (name.equals("FileName")) {
               oldVal = this._FileName;
               this._FileName = (String)v;
               this._postSet(11, oldVal, this._FileName);
            } else if (name.equals("FileTimeSpan")) {
               oldVal = this._FileTimeSpan;
               this._FileTimeSpan = (Integer)v;
               this._postSet(15, oldVal, this._FileTimeSpan);
            } else if (name.equals("FileTimeSpanFactor")) {
               long oldVal = this._FileTimeSpanFactor;
               this._FileTimeSpanFactor = (Long)v;
               this._postSet(17, oldVal, this._FileTimeSpanFactor);
            } else if (name.equals("LogFilePath")) {
               oldVal = this._LogFilePath;
               this._LogFilePath = (String)v;
               this._postSet(21, oldVal, this._LogFilePath);
            } else if (name.equals("LogFileRotationDir")) {
               oldVal = this._LogFileRotationDir;
               this._LogFileRotationDir = (String)v;
               this._postSet(20, oldVal, this._LogFileRotationDir);
            } else if (name.equals("LogRotationDirPath")) {
               oldVal = this._LogRotationDirPath;
               this._LogRotationDirPath = (String)v;
               this._postSet(22, oldVal, this._LogRotationDirPath);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("NumberOfFilesLimited")) {
               oldVal = this._NumberOfFilesLimited;
               this._NumberOfFilesLimited = (Boolean)v;
               this._postSet(13, oldVal, this._NumberOfFilesLimited);
            } else if (name.equals("OutputStream")) {
               OutputStream oldVal = this._OutputStream;
               this._OutputStream = (OutputStream)v;
               this._postSet(23, oldVal, this._OutputStream);
            } else if (name.equals("RotateLogOnStartup")) {
               oldVal = this._RotateLogOnStartup;
               this._RotateLogOnStartup = (Boolean)v;
               this._postSet(19, oldVal, this._RotateLogOnStartup);
            } else if (name.equals("RotationTime")) {
               oldVal = this._RotationTime;
               this._RotationTime = (String)v;
               this._postSet(16, oldVal, this._RotationTime);
            } else if (name.equals("RotationType")) {
               oldVal = this._RotationType;
               this._RotationType = (String)v;
               this._postSet(12, oldVal, this._RotationType);
            } else if (name.equals("Tags")) {
               String[] oldVal = this._Tags;
               this._Tags = (String[])((String[])v);
               this._postSet(9, oldVal, this._Tags);
            } else if (name.equals("customizer")) {
               LogFile oldVal = this._customizer;
               this._customizer = (LogFile)v;
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("BufferSizeKB")) {
         return new Integer(this._BufferSizeKB);
      } else if (name.equals("DateFormatPattern")) {
         return this._DateFormatPattern;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("FileCount")) {
         return new Integer(this._FileCount);
      } else if (name.equals("FileMinSize")) {
         return new Integer(this._FileMinSize);
      } else if (name.equals("FileName")) {
         return this._FileName;
      } else if (name.equals("FileTimeSpan")) {
         return new Integer(this._FileTimeSpan);
      } else if (name.equals("FileTimeSpanFactor")) {
         return new Long(this._FileTimeSpanFactor);
      } else if (name.equals("LogFilePath")) {
         return this._LogFilePath;
      } else if (name.equals("LogFileRotationDir")) {
         return this._LogFileRotationDir;
      } else if (name.equals("LogRotationDirPath")) {
         return this._LogRotationDirPath;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NumberOfFilesLimited")) {
         return new Boolean(this._NumberOfFilesLimited);
      } else if (name.equals("OutputStream")) {
         return this._OutputStream;
      } else if (name.equals("RotateLogOnStartup")) {
         return new Boolean(this._RotateLogOnStartup);
      } else if (name.equals("RotationTime")) {
         return this._RotationTime;
      } else if (name.equals("RotationType")) {
         return this._RotationType;
      } else if (name.equals("Tags")) {
         return this._Tags;
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
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 22:
            default:
               break;
            case 9:
               if (s.equals("file-name")) {
                  return 11;
               }
               break;
            case 10:
               if (s.equals("file-count")) {
                  return 14;
               }
               break;
            case 13:
               if (s.equals("buffer-sizekb")) {
                  return 24;
               }

               if (s.equals("file-min-size")) {
                  return 18;
               }

               if (s.equals("log-file-path")) {
                  return 21;
               }

               if (s.equals("output-stream")) {
                  return 23;
               }

               if (s.equals("rotation-time")) {
                  return 16;
               }

               if (s.equals("rotation-type")) {
                  return 12;
               }
               break;
            case 14:
               if (s.equals("file-time-span")) {
                  return 15;
               }
               break;
            case 19:
               if (s.equals("date-format-pattern")) {
                  return 10;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 21:
               if (s.equals("file-time-span-factor")) {
                  return 17;
               }

               if (s.equals("log-file-rotation-dir")) {
                  return 20;
               }

               if (s.equals("log-rotation-dir-path")) {
                  return 22;
               }

               if (s.equals("rotate-log-on-startup")) {
                  return 19;
               }
               break;
            case 23:
               if (s.equals("number-of-files-limited")) {
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
               return "date-format-pattern";
            case 11:
               return "file-name";
            case 12:
               return "rotation-type";
            case 13:
               return "number-of-files-limited";
            case 14:
               return "file-count";
            case 15:
               return "file-time-span";
            case 16:
               return "rotation-time";
            case 17:
               return "file-time-span-factor";
            case 18:
               return "file-min-size";
            case 19:
               return "rotate-log-on-startup";
            case 20:
               return "log-file-rotation-dir";
            case 21:
               return "log-file-path";
            case 22:
               return "log-rotation-dir-path";
            case 23:
               return "output-stream";
            case 24:
               return "buffer-sizekb";
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

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 11:
               return true;
            case 16:
               return true;
            case 18:
               return true;
            default:
               return super.isConfigurable(propIndex);
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
      private LogFileMBeanImpl bean;

      protected Helper(LogFileMBeanImpl bean) {
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
               return "DateFormatPattern";
            case 11:
               return "FileName";
            case 12:
               return "RotationType";
            case 13:
               return "NumberOfFilesLimited";
            case 14:
               return "FileCount";
            case 15:
               return "FileTimeSpan";
            case 16:
               return "RotationTime";
            case 17:
               return "FileTimeSpanFactor";
            case 18:
               return "FileMinSize";
            case 19:
               return "RotateLogOnStartup";
            case 20:
               return "LogFileRotationDir";
            case 21:
               return "LogFilePath";
            case 22:
               return "LogRotationDirPath";
            case 23:
               return "OutputStream";
            case 24:
               return "BufferSizeKB";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BufferSizeKB")) {
            return 24;
         } else if (propName.equals("DateFormatPattern")) {
            return 10;
         } else if (propName.equals("FileCount")) {
            return 14;
         } else if (propName.equals("FileMinSize")) {
            return 18;
         } else if (propName.equals("FileName")) {
            return 11;
         } else if (propName.equals("FileTimeSpan")) {
            return 15;
         } else if (propName.equals("FileTimeSpanFactor")) {
            return 17;
         } else if (propName.equals("LogFilePath")) {
            return 21;
         } else if (propName.equals("LogFileRotationDir")) {
            return 20;
         } else if (propName.equals("LogRotationDirPath")) {
            return 22;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("OutputStream")) {
            return 23;
         } else if (propName.equals("RotateLogOnStartup")) {
            return 19;
         } else if (propName.equals("RotationTime")) {
            return 16;
         } else if (propName.equals("RotationType")) {
            return 12;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("NumberOfFilesLimited") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isBufferSizeKBSet()) {
               buf.append("BufferSizeKB");
               buf.append(String.valueOf(this.bean.getBufferSizeKB()));
            }

            if (this.bean.isDateFormatPatternSet()) {
               buf.append("DateFormatPattern");
               buf.append(String.valueOf(this.bean.getDateFormatPattern()));
            }

            if (this.bean.isFileCountSet()) {
               buf.append("FileCount");
               buf.append(String.valueOf(this.bean.getFileCount()));
            }

            if (this.bean.isFileMinSizeSet()) {
               buf.append("FileMinSize");
               buf.append(String.valueOf(this.bean.getFileMinSize()));
            }

            if (this.bean.isFileNameSet()) {
               buf.append("FileName");
               buf.append(String.valueOf(this.bean.getFileName()));
            }

            if (this.bean.isFileTimeSpanSet()) {
               buf.append("FileTimeSpan");
               buf.append(String.valueOf(this.bean.getFileTimeSpan()));
            }

            if (this.bean.isFileTimeSpanFactorSet()) {
               buf.append("FileTimeSpanFactor");
               buf.append(String.valueOf(this.bean.getFileTimeSpanFactor()));
            }

            if (this.bean.isLogFilePathSet()) {
               buf.append("LogFilePath");
               buf.append(String.valueOf(this.bean.getLogFilePath()));
            }

            if (this.bean.isLogFileRotationDirSet()) {
               buf.append("LogFileRotationDir");
               buf.append(String.valueOf(this.bean.getLogFileRotationDir()));
            }

            if (this.bean.isLogRotationDirPathSet()) {
               buf.append("LogRotationDirPath");
               buf.append(String.valueOf(this.bean.getLogRotationDirPath()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isOutputStreamSet()) {
               buf.append("OutputStream");
               buf.append(String.valueOf(this.bean.getOutputStream()));
            }

            if (this.bean.isRotateLogOnStartupSet()) {
               buf.append("RotateLogOnStartup");
               buf.append(String.valueOf(this.bean.getRotateLogOnStartup()));
            }

            if (this.bean.isRotationTimeSet()) {
               buf.append("RotationTime");
               buf.append(String.valueOf(this.bean.getRotationTime()));
            }

            if (this.bean.isRotationTypeSet()) {
               buf.append("RotationType");
               buf.append(String.valueOf(this.bean.getRotationType()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isNumberOfFilesLimitedSet()) {
               buf.append("NumberOfFilesLimited");
               buf.append(String.valueOf(this.bean.isNumberOfFilesLimited()));
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
            LogFileMBeanImpl otherTyped = (LogFileMBeanImpl)other;
            this.computeDiff("BufferSizeKB", this.bean.getBufferSizeKB(), otherTyped.getBufferSizeKB(), true);
            this.computeDiff("DateFormatPattern", this.bean.getDateFormatPattern(), otherTyped.getDateFormatPattern(), true);
            this.computeDiff("FileCount", this.bean.getFileCount(), otherTyped.getFileCount(), true);
            this.computeDiff("FileMinSize", this.bean.getFileMinSize(), otherTyped.getFileMinSize(), true);
            this.computeDiff("FileName", this.bean.getFileName(), otherTyped.getFileName(), true);
            this.computeDiff("FileTimeSpan", this.bean.getFileTimeSpan(), otherTyped.getFileTimeSpan(), true);
            this.computeDiff("FileTimeSpanFactor", this.bean.getFileTimeSpanFactor(), otherTyped.getFileTimeSpanFactor(), false);
            this.computeDiff("LogFileRotationDir", this.bean.getLogFileRotationDir(), otherTyped.getLogFileRotationDir(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("RotateLogOnStartup", this.bean.getRotateLogOnStartup(), otherTyped.getRotateLogOnStartup(), true);
            this.computeDiff("RotationTime", this.bean.getRotationTime(), otherTyped.getRotationTime(), true);
            this.computeDiff("RotationType", this.bean.getRotationType(), otherTyped.getRotationType(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("NumberOfFilesLimited", this.bean.isNumberOfFilesLimited(), otherTyped.isNumberOfFilesLimited(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LogFileMBeanImpl original = (LogFileMBeanImpl)event.getSourceBean();
            LogFileMBeanImpl proposed = (LogFileMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BufferSizeKB")) {
                  original.setBufferSizeKB(proposed.getBufferSizeKB());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("DateFormatPattern")) {
                  original.setDateFormatPattern(proposed.getDateFormatPattern());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("FileCount")) {
                  original.setFileCount(proposed.getFileCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("FileMinSize")) {
                  original.setFileMinSize(proposed.getFileMinSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("FileName")) {
                  original.setFileName(proposed.getFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("FileTimeSpan")) {
                  original.setFileTimeSpan(proposed.getFileTimeSpan());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("FileTimeSpanFactor")) {
                  original.setFileTimeSpanFactor(proposed.getFileTimeSpanFactor());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (!prop.equals("LogFilePath")) {
                  if (prop.equals("LogFileRotationDir")) {
                     original.setLogFileRotationDir(proposed.getLogFileRotationDir());
                     original._conditionalUnset(update.isUnsetUpdate(), 20);
                  } else if (!prop.equals("LogRotationDirPath")) {
                     if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (!prop.equals("OutputStream")) {
                        if (prop.equals("RotateLogOnStartup")) {
                           original.setRotateLogOnStartup(proposed.getRotateLogOnStartup());
                           original._conditionalUnset(update.isUnsetUpdate(), 19);
                        } else if (prop.equals("RotationTime")) {
                           original.setRotationTime(proposed.getRotationTime());
                           original._conditionalUnset(update.isUnsetUpdate(), 16);
                        } else if (prop.equals("RotationType")) {
                           original.setRotationType(proposed.getRotationType());
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
                           if (prop.equals("NumberOfFilesLimited")) {
                              original.setNumberOfFilesLimited(proposed.isNumberOfFilesLimited());
                              original._conditionalUnset(update.isUnsetUpdate(), 13);
                           } else {
                              super.applyPropertyUpdate(event, update);
                           }
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
            LogFileMBeanImpl copy = (LogFileMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BufferSizeKB")) && this.bean.isBufferSizeKBSet()) {
               copy.setBufferSizeKB(this.bean.getBufferSizeKB());
            }

            if ((excludeProps == null || !excludeProps.contains("DateFormatPattern")) && this.bean.isDateFormatPatternSet()) {
               copy.setDateFormatPattern(this.bean.getDateFormatPattern());
            }

            if ((excludeProps == null || !excludeProps.contains("FileCount")) && this.bean.isFileCountSet()) {
               copy.setFileCount(this.bean.getFileCount());
            }

            if ((excludeProps == null || !excludeProps.contains("FileMinSize")) && this.bean.isFileMinSizeSet()) {
               copy.setFileMinSize(this.bean.getFileMinSize());
            }

            if ((excludeProps == null || !excludeProps.contains("FileName")) && this.bean.isFileNameSet()) {
               copy.setFileName(this.bean.getFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("FileTimeSpan")) && this.bean.isFileTimeSpanSet()) {
               copy.setFileTimeSpan(this.bean.getFileTimeSpan());
            }

            if ((excludeProps == null || !excludeProps.contains("FileTimeSpanFactor")) && this.bean.isFileTimeSpanFactorSet()) {
               copy.setFileTimeSpanFactor(this.bean.getFileTimeSpanFactor());
            }

            if ((excludeProps == null || !excludeProps.contains("LogFileRotationDir")) && this.bean.isLogFileRotationDirSet()) {
               copy.setLogFileRotationDir(this.bean.getLogFileRotationDir());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("RotateLogOnStartup")) && this.bean.isRotateLogOnStartupSet()) {
               copy.setRotateLogOnStartup(this.bean.getRotateLogOnStartup());
            }

            if ((excludeProps == null || !excludeProps.contains("RotationTime")) && this.bean.isRotationTimeSet()) {
               copy.setRotationTime(this.bean.getRotationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("RotationType")) && this.bean.isRotationTypeSet()) {
               copy.setRotationType(this.bean.getRotationType());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("NumberOfFilesLimited")) && this.bean.isNumberOfFilesLimitedSet()) {
               copy.setNumberOfFilesLimited(this.bean.isNumberOfFilesLimited());
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

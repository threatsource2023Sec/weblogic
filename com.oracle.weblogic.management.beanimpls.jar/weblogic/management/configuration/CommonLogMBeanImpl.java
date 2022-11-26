package weblogic.management.configuration;

import com.bea.logging.LoggingConfigValidator;
import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.Log;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class CommonLogMBeanImpl extends LogFileMBeanImpl implements CommonLogMBean, Serializable {
   private boolean _DynamicallyCreated;
   private String _LogFilePath;
   private String _LogFileSeverity;
   private String _LogRotationDirPath;
   private String _LoggerSeverity;
   private Properties _LoggerSeverityProperties;
   private String _Name;
   private int _StacktraceDepth;
   private String _StdoutFormat;
   private boolean _StdoutLogStack;
   private String _StdoutSeverity;
   private String[] _Tags;
   private transient Log _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private CommonLogMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(CommonLogMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(CommonLogMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public CommonLogMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(CommonLogMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      CommonLogMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public CommonLogMBeanImpl() {
      try {
         this._customizer = new Log(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public CommonLogMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new Log(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public CommonLogMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new Log(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getLoggerSeverity() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._performMacroSubstitution(this._getDelegateBean().getLoggerSeverity(), this) : this._LoggerSeverity;
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

   public boolean isLoggerSeverityInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isLoggerSeveritySet() {
      return this._isSet(25);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setLoggerSeverity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Trace", "Debug", "Info", "Notice", "Warning"};
      param0 = LegalChecks.checkInEnum("LoggerSeverity", param0, _set);
      boolean wasSet = this._isSet(25);
      String _oldVal = this._LoggerSeverity;
      this._LoggerSeverity = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         CommonLogMBeanImpl source = (CommonLogMBeanImpl)var5.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
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
         CommonLogMBeanImpl source = (CommonLogMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public Properties getLoggerSeverityProperties() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().getLoggerSeverityProperties() : this._LoggerSeverityProperties;
   }

   public String getLoggerSeverityPropertiesAsString() {
      return StringHelper.objectToString(this.getLoggerSeverityProperties());
   }

   public boolean isLoggerSeverityPropertiesInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isLoggerSeverityPropertiesSet() {
      return this._isSet(26);
   }

   public void setLoggerSeverityPropertiesAsString(String param0) {
      try {
         this.setLoggerSeverityProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setLoggerSeverityProperties(Properties param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LoggingConfigValidator.validateLoggerSeverityProperties(param0);
      boolean wasSet = this._isSet(26);
      Properties _oldVal = this._LoggerSeverityProperties;
      this._LoggerSeverityProperties = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CommonLogMBeanImpl source = (CommonLogMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLogFileSeverity() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._performMacroSubstitution(this._getDelegateBean().getLogFileSeverity(), this) : this._LogFileSeverity;
   }

   public boolean isLogFileSeverityInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isLogFileSeveritySet() {
      return this._isSet(27);
   }

   public void setLogFileSeverity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Trace", "Debug", "Info", "Notice", "Warning"};
      param0 = LegalChecks.checkInEnum("LogFileSeverity", param0, _set);
      boolean wasSet = this._isSet(27);
      String _oldVal = this._LogFileSeverity;
      this._LogFileSeverity = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         CommonLogMBeanImpl source = (CommonLogMBeanImpl)var5.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public String getStdoutSeverity() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._performMacroSubstitution(this._getDelegateBean().getStdoutSeverity(), this) : this._StdoutSeverity;
   }

   public boolean isStdoutSeverityInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isStdoutSeveritySet() {
      return this._isSet(28);
   }

   public void setStdoutSeverity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Trace", "Debug", "Info", "Warning", "Error", "Notice", "Critical", "Alert", "Emergency", "Off"};
      param0 = LegalChecks.checkInEnum("StdoutSeverity", param0, _set);
      boolean wasSet = this._isSet(28);
      String _oldVal = this._StdoutSeverity;
      this._StdoutSeverity = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         CommonLogMBeanImpl source = (CommonLogMBeanImpl)var5.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public String getStdoutFormat() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._performMacroSubstitution(this._getDelegateBean().getStdoutFormat(), this) : this._StdoutFormat;
   }

   public boolean isStdoutFormatInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isStdoutFormatSet() {
      return this._isSet(29);
   }

   public void setStdoutFormat(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"standard", "noid"};
      param0 = LegalChecks.checkInEnum("StdoutFormat", param0, _set);
      boolean wasSet = this._isSet(29);
      String _oldVal = this._StdoutFormat;
      this._StdoutFormat = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         CommonLogMBeanImpl source = (CommonLogMBeanImpl)var5.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isStdoutLogStack() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().isStdoutLogStack() : this._StdoutLogStack;
   }

   public boolean isStdoutLogStackInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isStdoutLogStackSet() {
      return this._isSet(30);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setStdoutLogStack(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      boolean _oldVal = this._StdoutLogStack;
      this._StdoutLogStack = param0;
      this._postSet(30, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CommonLogMBeanImpl source = (CommonLogMBeanImpl)var4.next();
         if (source != null && !source._isSet(30)) {
            source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
         }
      }

   }

   public int getStacktraceDepth() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().getStacktraceDepth() : this._StacktraceDepth;
   }

   public boolean isStacktraceDepthInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isStacktraceDepthSet() {
      return this._isSet(31);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setStacktraceDepth(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      int _oldVal = this._StacktraceDepth;
      this._StacktraceDepth = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         CommonLogMBeanImpl source = (CommonLogMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

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

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
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
         CommonLogMBeanImpl source = (CommonLogMBeanImpl)var4.next();
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
         idx = 21;
      }

      try {
         switch (idx) {
            case 21:
               this._LogFilePath = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._LogFileSeverity = "Trace";
               if (initOne) {
                  break;
               }
            case 22:
               this._LogRotationDirPath = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._LoggerSeverity = "Info";
               if (initOne) {
                  break;
               }
            case 26:
               this._LoggerSeverityProperties = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 31:
               this._StacktraceDepth = 5;
               if (initOne) {
                  break;
               }
            case 29:
               this._StdoutFormat = "standard";
               if (initOne) {
                  break;
               }
            case 28:
               this._StdoutSeverity = "Notice";
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
            case 30:
               this._StdoutLogStack = true;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
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
      return "CommonLog";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("DynamicallyCreated")) {
         oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else {
         String oldVal;
         if (name.equals("LogFilePath")) {
            oldVal = this._LogFilePath;
            this._LogFilePath = (String)v;
            this._postSet(21, oldVal, this._LogFilePath);
         } else if (name.equals("LogFileSeverity")) {
            oldVal = this._LogFileSeverity;
            this._LogFileSeverity = (String)v;
            this._postSet(27, oldVal, this._LogFileSeverity);
         } else if (name.equals("LogRotationDirPath")) {
            oldVal = this._LogRotationDirPath;
            this._LogRotationDirPath = (String)v;
            this._postSet(22, oldVal, this._LogRotationDirPath);
         } else if (name.equals("LoggerSeverity")) {
            oldVal = this._LoggerSeverity;
            this._LoggerSeverity = (String)v;
            this._postSet(25, oldVal, this._LoggerSeverity);
         } else if (name.equals("LoggerSeverityProperties")) {
            Properties oldVal = this._LoggerSeverityProperties;
            this._LoggerSeverityProperties = (Properties)v;
            this._postSet(26, oldVal, this._LoggerSeverityProperties);
         } else if (name.equals("Name")) {
            oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(2, oldVal, this._Name);
         } else if (name.equals("StacktraceDepth")) {
            int oldVal = this._StacktraceDepth;
            this._StacktraceDepth = (Integer)v;
            this._postSet(31, oldVal, this._StacktraceDepth);
         } else if (name.equals("StdoutFormat")) {
            oldVal = this._StdoutFormat;
            this._StdoutFormat = (String)v;
            this._postSet(29, oldVal, this._StdoutFormat);
         } else if (name.equals("StdoutLogStack")) {
            oldVal = this._StdoutLogStack;
            this._StdoutLogStack = (Boolean)v;
            this._postSet(30, oldVal, this._StdoutLogStack);
         } else if (name.equals("StdoutSeverity")) {
            oldVal = this._StdoutSeverity;
            this._StdoutSeverity = (String)v;
            this._postSet(28, oldVal, this._StdoutSeverity);
         } else if (name.equals("Tags")) {
            String[] oldVal = this._Tags;
            this._Tags = (String[])((String[])v);
            this._postSet(9, oldVal, this._Tags);
         } else if (name.equals("customizer")) {
            Log oldVal = this._customizer;
            this._customizer = (Log)v;
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("LogFilePath")) {
         return this._LogFilePath;
      } else if (name.equals("LogFileSeverity")) {
         return this._LogFileSeverity;
      } else if (name.equals("LogRotationDirPath")) {
         return this._LogRotationDirPath;
      } else if (name.equals("LoggerSeverity")) {
         return this._LoggerSeverity;
      } else if (name.equals("LoggerSeverityProperties")) {
         return this._LoggerSeverityProperties;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("StacktraceDepth")) {
         return new Integer(this._StacktraceDepth);
      } else if (name.equals("StdoutFormat")) {
         return this._StdoutFormat;
      } else if (name.equals("StdoutLogStack")) {
         return new Boolean(this._StdoutLogStack);
      } else if (name.equals("StdoutSeverity")) {
         return this._StdoutSeverity;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends LogFileMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 18:
            case 20:
            case 22:
            case 23:
            case 24:
            case 25:
            default:
               break;
            case 13:
               if (s.equals("log-file-path")) {
                  return 21;
               }

               if (s.equals("stdout-format")) {
                  return 29;
               }
               break;
            case 15:
               if (s.equals("logger-severity")) {
                  return 25;
               }

               if (s.equals("stdout-severity")) {
                  return 28;
               }
               break;
            case 16:
               if (s.equals("stacktrace-depth")) {
                  return 31;
               }

               if (s.equals("stdout-log-stack")) {
                  return 30;
               }
               break;
            case 17:
               if (s.equals("log-file-severity")) {
                  return 27;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 21:
               if (s.equals("log-rotation-dir-path")) {
                  return 22;
               }
               break;
            case 26:
               if (s.equals("logger-severity-properties")) {
                  return 26;
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
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 21:
               return "log-file-path";
            case 22:
               return "log-rotation-dir-path";
            case 25:
               return "logger-severity";
            case 26:
               return "logger-severity-properties";
            case 27:
               return "log-file-severity";
            case 28:
               return "stdout-severity";
            case 29:
               return "stdout-format";
            case 30:
               return "stdout-log-stack";
            case 31:
               return "stacktrace-depth";
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

   protected static class Helper extends LogFileMBeanImpl.Helper {
      private CommonLogMBeanImpl bean;

      protected Helper(CommonLogMBeanImpl bean) {
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
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 21:
               return "LogFilePath";
            case 22:
               return "LogRotationDirPath";
            case 25:
               return "LoggerSeverity";
            case 26:
               return "LoggerSeverityProperties";
            case 27:
               return "LogFileSeverity";
            case 28:
               return "StdoutSeverity";
            case 29:
               return "StdoutFormat";
            case 30:
               return "StdoutLogStack";
            case 31:
               return "StacktraceDepth";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LogFilePath")) {
            return 21;
         } else if (propName.equals("LogFileSeverity")) {
            return 27;
         } else if (propName.equals("LogRotationDirPath")) {
            return 22;
         } else if (propName.equals("LoggerSeverity")) {
            return 25;
         } else if (propName.equals("LoggerSeverityProperties")) {
            return 26;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("StacktraceDepth")) {
            return 31;
         } else if (propName.equals("StdoutFormat")) {
            return 29;
         } else if (propName.equals("StdoutSeverity")) {
            return 28;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("StdoutLogStack") ? 30 : super.getPropertyIndex(propName);
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
            if (this.bean.isLogFilePathSet()) {
               buf.append("LogFilePath");
               buf.append(String.valueOf(this.bean.getLogFilePath()));
            }

            if (this.bean.isLogFileSeveritySet()) {
               buf.append("LogFileSeverity");
               buf.append(String.valueOf(this.bean.getLogFileSeverity()));
            }

            if (this.bean.isLogRotationDirPathSet()) {
               buf.append("LogRotationDirPath");
               buf.append(String.valueOf(this.bean.getLogRotationDirPath()));
            }

            if (this.bean.isLoggerSeveritySet()) {
               buf.append("LoggerSeverity");
               buf.append(String.valueOf(this.bean.getLoggerSeverity()));
            }

            if (this.bean.isLoggerSeverityPropertiesSet()) {
               buf.append("LoggerSeverityProperties");
               buf.append(String.valueOf(this.bean.getLoggerSeverityProperties()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isStacktraceDepthSet()) {
               buf.append("StacktraceDepth");
               buf.append(String.valueOf(this.bean.getStacktraceDepth()));
            }

            if (this.bean.isStdoutFormatSet()) {
               buf.append("StdoutFormat");
               buf.append(String.valueOf(this.bean.getStdoutFormat()));
            }

            if (this.bean.isStdoutSeveritySet()) {
               buf.append("StdoutSeverity");
               buf.append(String.valueOf(this.bean.getStdoutSeverity()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isStdoutLogStackSet()) {
               buf.append("StdoutLogStack");
               buf.append(String.valueOf(this.bean.isStdoutLogStack()));
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
            CommonLogMBeanImpl otherTyped = (CommonLogMBeanImpl)other;
            this.computeDiff("LogFileSeverity", this.bean.getLogFileSeverity(), otherTyped.getLogFileSeverity(), true);
            this.computeDiff("LoggerSeverity", this.bean.getLoggerSeverity(), otherTyped.getLoggerSeverity(), true);
            this.computeDiff("LoggerSeverityProperties", this.bean.getLoggerSeverityProperties(), otherTyped.getLoggerSeverityProperties(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("StacktraceDepth", this.bean.getStacktraceDepth(), otherTyped.getStacktraceDepth(), true);
            this.computeDiff("StdoutFormat", this.bean.getStdoutFormat(), otherTyped.getStdoutFormat(), false);
            this.computeDiff("StdoutSeverity", this.bean.getStdoutSeverity(), otherTyped.getStdoutSeverity(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("StdoutLogStack", this.bean.isStdoutLogStack(), otherTyped.isStdoutLogStack(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CommonLogMBeanImpl original = (CommonLogMBeanImpl)event.getSourceBean();
            CommonLogMBeanImpl proposed = (CommonLogMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("LogFilePath")) {
                  if (prop.equals("LogFileSeverity")) {
                     original.setLogFileSeverity(proposed.getLogFileSeverity());
                     original._conditionalUnset(update.isUnsetUpdate(), 27);
                  } else if (!prop.equals("LogRotationDirPath")) {
                     if (prop.equals("LoggerSeverity")) {
                        original.setLoggerSeverity(proposed.getLoggerSeverity());
                        original._conditionalUnset(update.isUnsetUpdate(), 25);
                     } else if (prop.equals("LoggerSeverityProperties")) {
                        original.setLoggerSeverityProperties(proposed.getLoggerSeverityProperties() == null ? null : (Properties)proposed.getLoggerSeverityProperties().clone());
                        original._conditionalUnset(update.isUnsetUpdate(), 26);
                     } else if (prop.equals("Name")) {
                        original.setName(proposed.getName());
                        original._conditionalUnset(update.isUnsetUpdate(), 2);
                     } else if (prop.equals("StacktraceDepth")) {
                        original.setStacktraceDepth(proposed.getStacktraceDepth());
                        original._conditionalUnset(update.isUnsetUpdate(), 31);
                     } else if (prop.equals("StdoutFormat")) {
                        original.setStdoutFormat(proposed.getStdoutFormat());
                        original._conditionalUnset(update.isUnsetUpdate(), 29);
                     } else if (prop.equals("StdoutSeverity")) {
                        original.setStdoutSeverity(proposed.getStdoutSeverity());
                        original._conditionalUnset(update.isUnsetUpdate(), 28);
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
                        if (prop.equals("StdoutLogStack")) {
                           original.setStdoutLogStack(proposed.isStdoutLogStack());
                           original._conditionalUnset(update.isUnsetUpdate(), 30);
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
            CommonLogMBeanImpl copy = (CommonLogMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LogFileSeverity")) && this.bean.isLogFileSeveritySet()) {
               copy.setLogFileSeverity(this.bean.getLogFileSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("LoggerSeverity")) && this.bean.isLoggerSeveritySet()) {
               copy.setLoggerSeverity(this.bean.getLoggerSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("LoggerSeverityProperties")) && this.bean.isLoggerSeverityPropertiesSet()) {
               copy.setLoggerSeverityProperties(this.bean.getLoggerSeverityProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("StacktraceDepth")) && this.bean.isStacktraceDepthSet()) {
               copy.setStacktraceDepth(this.bean.getStacktraceDepth());
            }

            if ((excludeProps == null || !excludeProps.contains("StdoutFormat")) && this.bean.isStdoutFormatSet()) {
               copy.setStdoutFormat(this.bean.getStdoutFormat());
            }

            if ((excludeProps == null || !excludeProps.contains("StdoutSeverity")) && this.bean.isStdoutSeveritySet()) {
               copy.setStdoutSeverity(this.bean.getStdoutSeverity());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("StdoutLogStack")) && this.bean.isStdoutLogStackSet()) {
               copy.setStdoutLogStack(this.bean.isStdoutLogStack());
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

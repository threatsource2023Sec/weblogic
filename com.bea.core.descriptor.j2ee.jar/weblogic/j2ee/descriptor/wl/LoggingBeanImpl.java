package weblogic.j2ee.descriptor.wl;

import com.bea.logging.DateFormatter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class LoggingBeanImpl extends AbstractDescriptorBean implements LoggingBean, Serializable {
   private String _DateFormatPattern;
   private int _FileCount;
   private int _FileSizeLimit;
   private int _FileTimeSpan;
   private String _Id;
   private String _LogFileRotationDir;
   private String _LogFilename;
   private boolean _LoggingEnabled;
   private boolean _NumberOfFilesLimited;
   private boolean _RotateLogOnStartup;
   private String _RotationTime;
   private String _RotationType;
   private static SchemaHelper2 _schemaHelper;

   public LoggingBeanImpl() {
      this._initializeProperty(-1);
   }

   public LoggingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public LoggingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getLogFilename() {
      return this._LogFilename;
   }

   public boolean isLogFilenameInherited() {
      return false;
   }

   public boolean isLogFilenameSet() {
      return this._isSet(0);
   }

   public void setLogFilename(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LogFilename;
      this._LogFilename = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean isLoggingEnabled() {
      return this._LoggingEnabled;
   }

   public boolean isLoggingEnabledInherited() {
      return false;
   }

   public boolean isLoggingEnabledSet() {
      return this._isSet(1);
   }

   public void setLoggingEnabled(boolean param0) {
      boolean _oldVal = this._LoggingEnabled;
      this._LoggingEnabled = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getRotationType() {
      return this._RotationType;
   }

   public boolean isRotationTypeInherited() {
      return false;
   }

   public boolean isRotationTypeSet() {
      return this._isSet(2);
   }

   public void setRotationType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"bySize", "byTime", "none"};
      param0 = LegalChecks.checkInEnum("RotationType", param0, _set);
      String _oldVal = this._RotationType;
      this._RotationType = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isNumberOfFilesLimited() {
      return this._NumberOfFilesLimited;
   }

   public boolean isNumberOfFilesLimitedInherited() {
      return false;
   }

   public boolean isNumberOfFilesLimitedSet() {
      return this._isSet(3);
   }

   public void setNumberOfFilesLimited(boolean param0) {
      boolean _oldVal = this._NumberOfFilesLimited;
      this._NumberOfFilesLimited = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getFileCount() {
      return this._FileCount;
   }

   public boolean isFileCountInherited() {
      return false;
   }

   public boolean isFileCountSet() {
      return this._isSet(4);
   }

   public void setFileCount(int param0) {
      int _oldVal = this._FileCount;
      this._FileCount = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getFileSizeLimit() {
      return this._FileSizeLimit;
   }

   public boolean isFileSizeLimitInherited() {
      return false;
   }

   public boolean isFileSizeLimitSet() {
      return this._isSet(5);
   }

   public void setFileSizeLimit(int param0) {
      int _oldVal = this._FileSizeLimit;
      this._FileSizeLimit = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isRotateLogOnStartup() {
      return this._RotateLogOnStartup;
   }

   public boolean isRotateLogOnStartupInherited() {
      return false;
   }

   public boolean isRotateLogOnStartupSet() {
      return this._isSet(6);
   }

   public void setRotateLogOnStartup(boolean param0) {
      boolean _oldVal = this._RotateLogOnStartup;
      this._RotateLogOnStartup = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getLogFileRotationDir() {
      return this._LogFileRotationDir;
   }

   public boolean isLogFileRotationDirInherited() {
      return false;
   }

   public boolean isLogFileRotationDirSet() {
      return this._isSet(7);
   }

   public void setLogFileRotationDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LogFileRotationDir;
      this._LogFileRotationDir = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getRotationTime() {
      return this._RotationTime;
   }

   public boolean isRotationTimeInherited() {
      return false;
   }

   public boolean isRotationTimeSet() {
      return this._isSet(8);
   }

   public void setRotationTime(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("RotationTime", param0);
      LegalChecks.checkNonNull("RotationTime", param0);
      LoggingBeanValidator.validateLogTimeString(param0);
      String _oldVal = this._RotationTime;
      this._RotationTime = param0;
      this._postSet(8, _oldVal, param0);
   }

   public int getFileTimeSpan() {
      return this._FileTimeSpan;
   }

   public boolean isFileTimeSpanInherited() {
      return false;
   }

   public boolean isFileTimeSpanSet() {
      return this._isSet(9);
   }

   public void setFileTimeSpan(int param0) {
      int _oldVal = this._FileTimeSpan;
      this._FileTimeSpan = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getDateFormatPattern() {
      if (!this._isSet(10)) {
         try {
            return DateFormatter.getDefaultDateFormatPattern();
         } catch (NullPointerException var2) {
         }
      }

      return this._DateFormatPattern;
   }

   public boolean isDateFormatPatternInherited() {
      return false;
   }

   public boolean isDateFormatPatternSet() {
      return this._isSet(10);
   }

   public void setDateFormatPattern(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("DateFormatPattern", param0);
      LegalChecks.checkNonNull("DateFormatPattern", param0);
      DateFormatter.validateDateFormatPattern(param0);
      String _oldVal = this._DateFormatPattern;
      this._DateFormatPattern = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(11);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(11, _oldVal, param0);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._DateFormatPattern = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._FileCount = 7;
               if (initOne) {
                  break;
               }
            case 5:
               this._FileSizeLimit = 500;
               if (initOne) {
                  break;
               }
            case 9:
               this._FileTimeSpan = 24;
               if (initOne) {
                  break;
               }
            case 11:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._LogFileRotationDir = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._LogFilename = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._RotationTime = "00:00";
               if (initOne) {
                  break;
               }
            case 2:
               this._RotationType = "bySize";
               if (initOne) {
                  break;
               }
            case 1:
               this._LoggingEnabled = true;
               if (initOne) {
                  break;
               }
            case 3:
               this._NumberOfFilesLimited = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._RotateLogOnStartup = true;
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

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("RotationTime", "00:00");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property RotationTime in LoggingBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonEmptyString("RotationTime", "00:00");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property RotationTime in LoggingBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 11;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 16:
            case 17:
            case 18:
            case 20:
            case 22:
            default:
               break;
            case 10:
               if (s.equals("file-count")) {
                  return 4;
               }
               break;
            case 12:
               if (s.equals("log-filename")) {
                  return 0;
               }
               break;
            case 13:
               if (s.equals("rotation-time")) {
                  return 8;
               }

               if (s.equals("rotation-type")) {
                  return 2;
               }
               break;
            case 14:
               if (s.equals("file-time-span")) {
                  return 9;
               }
               break;
            case 15:
               if (s.equals("file-size-limit")) {
                  return 5;
               }

               if (s.equals("logging-enabled")) {
                  return 1;
               }
               break;
            case 19:
               if (s.equals("date-format-pattern")) {
                  return 10;
               }
               break;
            case 21:
               if (s.equals("log-file-rotation-dir")) {
                  return 7;
               }

               if (s.equals("rotate-log-on-startup")) {
                  return 6;
               }
               break;
            case 23:
               if (s.equals("number-of-files-limited")) {
                  return 3;
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
            case 0:
               return "log-filename";
            case 1:
               return "logging-enabled";
            case 2:
               return "rotation-type";
            case 3:
               return "number-of-files-limited";
            case 4:
               return "file-count";
            case 5:
               return "file-size-limit";
            case 6:
               return "rotate-log-on-startup";
            case 7:
               return "log-file-rotation-dir";
            case 8:
               return "rotation-time";
            case 9:
               return "file-time-span";
            case 10:
               return "date-format-pattern";
            case 11:
               return "id";
            default:
               return super.getElementName(propIndex);
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private LoggingBeanImpl bean;

      protected Helper(LoggingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "LogFilename";
            case 1:
               return "LoggingEnabled";
            case 2:
               return "RotationType";
            case 3:
               return "NumberOfFilesLimited";
            case 4:
               return "FileCount";
            case 5:
               return "FileSizeLimit";
            case 6:
               return "RotateLogOnStartup";
            case 7:
               return "LogFileRotationDir";
            case 8:
               return "RotationTime";
            case 9:
               return "FileTimeSpan";
            case 10:
               return "DateFormatPattern";
            case 11:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DateFormatPattern")) {
            return 10;
         } else if (propName.equals("FileCount")) {
            return 4;
         } else if (propName.equals("FileSizeLimit")) {
            return 5;
         } else if (propName.equals("FileTimeSpan")) {
            return 9;
         } else if (propName.equals("Id")) {
            return 11;
         } else if (propName.equals("LogFileRotationDir")) {
            return 7;
         } else if (propName.equals("LogFilename")) {
            return 0;
         } else if (propName.equals("RotationTime")) {
            return 8;
         } else if (propName.equals("RotationType")) {
            return 2;
         } else if (propName.equals("LoggingEnabled")) {
            return 1;
         } else if (propName.equals("NumberOfFilesLimited")) {
            return 3;
         } else {
            return propName.equals("RotateLogOnStartup") ? 6 : super.getPropertyIndex(propName);
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
            if (this.bean.isDateFormatPatternSet()) {
               buf.append("DateFormatPattern");
               buf.append(String.valueOf(this.bean.getDateFormatPattern()));
            }

            if (this.bean.isFileCountSet()) {
               buf.append("FileCount");
               buf.append(String.valueOf(this.bean.getFileCount()));
            }

            if (this.bean.isFileSizeLimitSet()) {
               buf.append("FileSizeLimit");
               buf.append(String.valueOf(this.bean.getFileSizeLimit()));
            }

            if (this.bean.isFileTimeSpanSet()) {
               buf.append("FileTimeSpan");
               buf.append(String.valueOf(this.bean.getFileTimeSpan()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isLogFileRotationDirSet()) {
               buf.append("LogFileRotationDir");
               buf.append(String.valueOf(this.bean.getLogFileRotationDir()));
            }

            if (this.bean.isLogFilenameSet()) {
               buf.append("LogFilename");
               buf.append(String.valueOf(this.bean.getLogFilename()));
            }

            if (this.bean.isRotationTimeSet()) {
               buf.append("RotationTime");
               buf.append(String.valueOf(this.bean.getRotationTime()));
            }

            if (this.bean.isRotationTypeSet()) {
               buf.append("RotationType");
               buf.append(String.valueOf(this.bean.getRotationType()));
            }

            if (this.bean.isLoggingEnabledSet()) {
               buf.append("LoggingEnabled");
               buf.append(String.valueOf(this.bean.isLoggingEnabled()));
            }

            if (this.bean.isNumberOfFilesLimitedSet()) {
               buf.append("NumberOfFilesLimited");
               buf.append(String.valueOf(this.bean.isNumberOfFilesLimited()));
            }

            if (this.bean.isRotateLogOnStartupSet()) {
               buf.append("RotateLogOnStartup");
               buf.append(String.valueOf(this.bean.isRotateLogOnStartup()));
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
            LoggingBeanImpl otherTyped = (LoggingBeanImpl)other;
            this.computeDiff("DateFormatPattern", this.bean.getDateFormatPattern(), otherTyped.getDateFormatPattern(), false);
            this.computeDiff("FileCount", this.bean.getFileCount(), otherTyped.getFileCount(), true);
            this.computeDiff("FileSizeLimit", this.bean.getFileSizeLimit(), otherTyped.getFileSizeLimit(), true);
            this.computeDiff("FileTimeSpan", this.bean.getFileTimeSpan(), otherTyped.getFileTimeSpan(), true);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("LogFileRotationDir", this.bean.getLogFileRotationDir(), otherTyped.getLogFileRotationDir(), true);
            this.computeDiff("LogFilename", this.bean.getLogFilename(), otherTyped.getLogFilename(), false);
            this.computeDiff("RotationTime", this.bean.getRotationTime(), otherTyped.getRotationTime(), true);
            this.computeDiff("RotationType", this.bean.getRotationType(), otherTyped.getRotationType(), true);
            this.computeDiff("LoggingEnabled", this.bean.isLoggingEnabled(), otherTyped.isLoggingEnabled(), true);
            this.computeDiff("NumberOfFilesLimited", this.bean.isNumberOfFilesLimited(), otherTyped.isNumberOfFilesLimited(), true);
            this.computeDiff("RotateLogOnStartup", this.bean.isRotateLogOnStartup(), otherTyped.isRotateLogOnStartup(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LoggingBeanImpl original = (LoggingBeanImpl)event.getSourceBean();
            LoggingBeanImpl proposed = (LoggingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DateFormatPattern")) {
                  original.setDateFormatPattern(proposed.getDateFormatPattern());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("FileCount")) {
                  original.setFileCount(proposed.getFileCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("FileSizeLimit")) {
                  original.setFileSizeLimit(proposed.getFileSizeLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("FileTimeSpan")) {
                  original.setFileTimeSpan(proposed.getFileTimeSpan());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("LogFileRotationDir")) {
                  original.setLogFileRotationDir(proposed.getLogFileRotationDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("LogFilename")) {
                  original.setLogFilename(proposed.getLogFilename());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("RotationTime")) {
                  original.setRotationTime(proposed.getRotationTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("RotationType")) {
                  original.setRotationType(proposed.getRotationType());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("LoggingEnabled")) {
                  original.setLoggingEnabled(proposed.isLoggingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("NumberOfFilesLimited")) {
                  original.setNumberOfFilesLimited(proposed.isNumberOfFilesLimited());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RotateLogOnStartup")) {
                  original.setRotateLogOnStartup(proposed.isRotateLogOnStartup());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            LoggingBeanImpl copy = (LoggingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DateFormatPattern")) && this.bean.isDateFormatPatternSet()) {
               copy.setDateFormatPattern(this.bean.getDateFormatPattern());
            }

            if ((excludeProps == null || !excludeProps.contains("FileCount")) && this.bean.isFileCountSet()) {
               copy.setFileCount(this.bean.getFileCount());
            }

            if ((excludeProps == null || !excludeProps.contains("FileSizeLimit")) && this.bean.isFileSizeLimitSet()) {
               copy.setFileSizeLimit(this.bean.getFileSizeLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("FileTimeSpan")) && this.bean.isFileTimeSpanSet()) {
               copy.setFileTimeSpan(this.bean.getFileTimeSpan());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("LogFileRotationDir")) && this.bean.isLogFileRotationDirSet()) {
               copy.setLogFileRotationDir(this.bean.getLogFileRotationDir());
            }

            if ((excludeProps == null || !excludeProps.contains("LogFilename")) && this.bean.isLogFilenameSet()) {
               copy.setLogFilename(this.bean.getLogFilename());
            }

            if ((excludeProps == null || !excludeProps.contains("RotationTime")) && this.bean.isRotationTimeSet()) {
               copy.setRotationTime(this.bean.getRotationTime());
            }

            if ((excludeProps == null || !excludeProps.contains("RotationType")) && this.bean.isRotationTypeSet()) {
               copy.setRotationType(this.bean.getRotationType());
            }

            if ((excludeProps == null || !excludeProps.contains("LoggingEnabled")) && this.bean.isLoggingEnabledSet()) {
               copy.setLoggingEnabled(this.bean.isLoggingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("NumberOfFilesLimited")) && this.bean.isNumberOfFilesLimitedSet()) {
               copy.setNumberOfFilesLimited(this.bean.isNumberOfFilesLimited());
            }

            if ((excludeProps == null || !excludeProps.contains("RotateLogOnStartup")) && this.bean.isRotateLogOnStartupSet()) {
               copy.setRotateLogOnStartup(this.bean.isRotateLogOnStartup());
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

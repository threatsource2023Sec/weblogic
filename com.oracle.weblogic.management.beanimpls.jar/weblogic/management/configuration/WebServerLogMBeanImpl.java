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
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.utils.collections.CombinedIterator;

public class WebServerLogMBeanImpl extends LogFileMBeanImpl implements WebServerLogMBean, Serializable {
   private String _ELFFields;
   private String _FileName;
   private String _LogFileFormat;
   private boolean _LogMilliSeconds;
   private boolean _LogTimeInGMT;
   private boolean _LoggingEnabled;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WebServerLogMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WebServerLogMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WebServerLogMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WebServerLogMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WebServerLogMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WebServerLogMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public WebServerLogMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebServerLogMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebServerLogMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getFileName() {
      if (!this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         return this._performMacroSubstitution(this._getDelegateBean().getFileName(), this);
      } else {
         if (!this._isSet(11)) {
            try {
               return this.getParent() instanceof VirtualHostMBean ? "logs/virtualHosts/" + this.getName() + "/" + "access.log" : (this.getParent().getParent() instanceof VirtualTargetMBean ? "logs/virtualTargets/" + this.getName() + "/" + "access.log" : "logs/access.log");
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

   public void setLoggingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(25);
      boolean _oldVal = this._LoggingEnabled;
      this._LoggingEnabled = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerLogMBeanImpl source = (WebServerLogMBeanImpl)var4.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLoggingEnabled() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._getDelegateBean().isLoggingEnabled() : this._LoggingEnabled;
   }

   public boolean isLoggingEnabledInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isLoggingEnabledSet() {
      return this._isSet(25);
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
         WebServerLogMBeanImpl source = (WebServerLogMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getELFFields() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._performMacroSubstitution(this._getDelegateBean().getELFFields(), this) : this._ELFFields;
   }

   public boolean isELFFieldsInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isELFFieldsSet() {
      return this._isSet(26);
   }

   public void setELFFields(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkNonEmptyString("ELFFields", param0);
      boolean wasSet = this._isSet(26);
      String _oldVal = this._ELFFields;
      this._ELFFields = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerLogMBeanImpl source = (WebServerLogMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLogFileFormat() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._performMacroSubstitution(this._getDelegateBean().getLogFileFormat(), this) : this._LogFileFormat;
   }

   public boolean isLogFileFormatInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isLogFileFormatSet() {
      return this._isSet(27);
   }

   public void setLogFileFormat(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"common", "extended"};
      param0 = LegalChecks.checkInEnum("LogFileFormat", param0, _set);
      boolean wasSet = this._isSet(27);
      String _oldVal = this._LogFileFormat;
      this._LogFileFormat = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServerLogMBeanImpl source = (WebServerLogMBeanImpl)var5.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLogTimeInGMT() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._getDelegateBean().isLogTimeInGMT() : this._LogTimeInGMT;
   }

   public boolean isLogTimeInGMTInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isLogTimeInGMTSet() {
      return this._isSet(28);
   }

   public void setLogTimeInGMT(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      boolean _oldVal = this._LogTimeInGMT;
      this._LogTimeInGMT = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerLogMBeanImpl source = (WebServerLogMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isLogMilliSeconds() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29) ? this._getDelegateBean().isLogMilliSeconds() : this._LogMilliSeconds;
   }

   public boolean isLogMilliSecondsInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isLogMilliSecondsSet() {
      return this._isSet(29);
   }

   public void setLogMilliSeconds(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(29);
      boolean _oldVal = this._LogMilliSeconds;
      this._LogMilliSeconds = param0;
      this._postSet(29, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServerLogMBeanImpl source = (WebServerLogMBeanImpl)var4.next();
         if (source != null && !source._isSet(29)) {
            source._postSetFirePropertyChange(29, wasSet, _oldVal, param0);
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
         idx = 26;
      }

      try {
         switch (idx) {
            case 26:
               this._ELFFields = "date time cs-method cs-uri sc-status";
               if (initOne) {
                  break;
               }
            case 11:
               this._FileName = null;
               if (initOne) {
                  break;
               }
            case 27:
               this._LogFileFormat = "common";
               if (initOne) {
                  break;
               }
            case 29:
               this._LogMilliSeconds = false;
               if (initOne) {
                  break;
               }
            case 28:
               this._LogTimeInGMT = false;
               if (initOne) {
                  break;
               }
            case 25:
               this._LoggingEnabled = true;
               if (initOne) {
                  break;
               }
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
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
      return "WebServerLog";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ELFFields")) {
         oldVal = this._ELFFields;
         this._ELFFields = (String)v;
         this._postSet(26, oldVal, this._ELFFields);
      } else if (name.equals("FileName")) {
         oldVal = this._FileName;
         this._FileName = (String)v;
         this._postSet(11, oldVal, this._FileName);
      } else if (name.equals("LogFileFormat")) {
         oldVal = this._LogFileFormat;
         this._LogFileFormat = (String)v;
         this._postSet(27, oldVal, this._LogFileFormat);
      } else {
         boolean oldVal;
         if (name.equals("LogMilliSeconds")) {
            oldVal = this._LogMilliSeconds;
            this._LogMilliSeconds = (Boolean)v;
            this._postSet(29, oldVal, this._LogMilliSeconds);
         } else if (name.equals("LogTimeInGMT")) {
            oldVal = this._LogTimeInGMT;
            this._LogTimeInGMT = (Boolean)v;
            this._postSet(28, oldVal, this._LogTimeInGMT);
         } else if (name.equals("LoggingEnabled")) {
            oldVal = this._LoggingEnabled;
            this._LoggingEnabled = (Boolean)v;
            this._postSet(25, oldVal, this._LoggingEnabled);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ELFFields")) {
         return this._ELFFields;
      } else if (name.equals("FileName")) {
         return this._FileName;
      } else if (name.equals("LogFileFormat")) {
         return this._LogFileFormat;
      } else if (name.equals("LogMilliSeconds")) {
         return new Boolean(this._LogMilliSeconds);
      } else if (name.equals("LogTimeInGMT")) {
         return new Boolean(this._LogTimeInGMT);
      } else {
         return name.equals("LoggingEnabled") ? new Boolean(this._LoggingEnabled) : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonEmptyString("ELFFields", "date time cs-method cs-uri sc-status");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property ELFFields in WebServerLogMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends LogFileMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("file-name")) {
                  return 11;
               }
               break;
            case 10:
               if (s.equals("elf-fields")) {
                  return 26;
               }
            case 11:
            case 12:
            case 13:
            case 16:
            default:
               break;
            case 14:
               if (s.equals("log-time-ingmt")) {
                  return 28;
               }
               break;
            case 15:
               if (s.equals("log-file-format")) {
                  return 27;
               }

               if (s.equals("logging-enabled")) {
                  return 25;
               }
               break;
            case 17:
               if (s.equals("log-milli-seconds")) {
                  return 29;
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
            case 11:
               return "file-name";
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            default:
               return super.getElementName(propIndex);
            case 25:
               return "logging-enabled";
            case 26:
               return "elf-fields";
            case 27:
               return "log-file-format";
            case 28:
               return "log-time-ingmt";
            case 29:
               return "log-milli-seconds";
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
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            default:
               return super.isConfigurable(propIndex);
            case 16:
               return true;
            case 18:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 28:
               return true;
            case 29:
               return true;
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

   protected static class Helper extends LogFileMBeanImpl.Helper {
      private WebServerLogMBeanImpl bean;

      protected Helper(WebServerLogMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 11:
               return "FileName";
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            default:
               return super.getPropertyName(propIndex);
            case 25:
               return "LoggingEnabled";
            case 26:
               return "ELFFields";
            case 27:
               return "LogFileFormat";
            case 28:
               return "LogTimeInGMT";
            case 29:
               return "LogMilliSeconds";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ELFFields")) {
            return 26;
         } else if (propName.equals("FileName")) {
            return 11;
         } else if (propName.equals("LogFileFormat")) {
            return 27;
         } else if (propName.equals("LogMilliSeconds")) {
            return 29;
         } else if (propName.equals("LogTimeInGMT")) {
            return 28;
         } else {
            return propName.equals("LoggingEnabled") ? 25 : super.getPropertyIndex(propName);
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
            if (this.bean.isELFFieldsSet()) {
               buf.append("ELFFields");
               buf.append(String.valueOf(this.bean.getELFFields()));
            }

            if (this.bean.isFileNameSet()) {
               buf.append("FileName");
               buf.append(String.valueOf(this.bean.getFileName()));
            }

            if (this.bean.isLogFileFormatSet()) {
               buf.append("LogFileFormat");
               buf.append(String.valueOf(this.bean.getLogFileFormat()));
            }

            if (this.bean.isLogMilliSecondsSet()) {
               buf.append("LogMilliSeconds");
               buf.append(String.valueOf(this.bean.isLogMilliSeconds()));
            }

            if (this.bean.isLogTimeInGMTSet()) {
               buf.append("LogTimeInGMT");
               buf.append(String.valueOf(this.bean.isLogTimeInGMT()));
            }

            if (this.bean.isLoggingEnabledSet()) {
               buf.append("LoggingEnabled");
               buf.append(String.valueOf(this.bean.isLoggingEnabled()));
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
            WebServerLogMBeanImpl otherTyped = (WebServerLogMBeanImpl)other;
            this.computeDiff("ELFFields", this.bean.getELFFields(), otherTyped.getELFFields(), true);
            this.computeDiff("FileName", this.bean.getFileName(), otherTyped.getFileName(), false);
            this.computeDiff("LogFileFormat", this.bean.getLogFileFormat(), otherTyped.getLogFileFormat(), false);
            this.computeDiff("LogMilliSeconds", this.bean.isLogMilliSeconds(), otherTyped.isLogMilliSeconds(), false);
            this.computeDiff("LogTimeInGMT", this.bean.isLogTimeInGMT(), otherTyped.isLogTimeInGMT(), false);
            this.computeDiff("LoggingEnabled", this.bean.isLoggingEnabled(), otherTyped.isLoggingEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebServerLogMBeanImpl original = (WebServerLogMBeanImpl)event.getSourceBean();
            WebServerLogMBeanImpl proposed = (WebServerLogMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ELFFields")) {
                  original.setELFFields(proposed.getELFFields());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("FileName")) {
                  original.setFileName(proposed.getFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("LogFileFormat")) {
                  original.setLogFileFormat(proposed.getLogFileFormat());
                  original._conditionalUnset(update.isUnsetUpdate(), 27);
               } else if (prop.equals("LogMilliSeconds")) {
                  original.setLogMilliSeconds(proposed.isLogMilliSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 29);
               } else if (prop.equals("LogTimeInGMT")) {
                  original.setLogTimeInGMT(proposed.isLogTimeInGMT());
                  original._conditionalUnset(update.isUnsetUpdate(), 28);
               } else if (prop.equals("LoggingEnabled")) {
                  original.setLoggingEnabled(proposed.isLoggingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 25);
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
            WebServerLogMBeanImpl copy = (WebServerLogMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ELFFields")) && this.bean.isELFFieldsSet()) {
               copy.setELFFields(this.bean.getELFFields());
            }

            if ((excludeProps == null || !excludeProps.contains("FileName")) && this.bean.isFileNameSet()) {
               copy.setFileName(this.bean.getFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("LogFileFormat")) && this.bean.isLogFileFormatSet()) {
               copy.setLogFileFormat(this.bean.getLogFileFormat());
            }

            if ((excludeProps == null || !excludeProps.contains("LogMilliSeconds")) && this.bean.isLogMilliSecondsSet()) {
               copy.setLogMilliSeconds(this.bean.isLogMilliSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("LogTimeInGMT")) && this.bean.isLogTimeInGMTSet()) {
               copy.setLogTimeInGMT(this.bean.isLogTimeInGMT());
            }

            if ((excludeProps == null || !excludeProps.contains("LoggingEnabled")) && this.bean.isLoggingEnabledSet()) {
               copy.setLoggingEnabled(this.bean.isLoggingEnabled());
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

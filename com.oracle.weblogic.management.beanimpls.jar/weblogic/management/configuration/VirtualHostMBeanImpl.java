package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.VirtualHost;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class VirtualHostMBeanImpl extends WebServerMBeanImpl implements VirtualHostMBean, Serializable {
   private boolean _DynamicallyCreated;
   private int _LogFileCount;
   private String _LogFileFormat;
   private boolean _LogFileLimitEnabled;
   private String _LogFileName;
   private int _LogRotationPeriodMins;
   private String _LogRotationTimeBegin;
   private String _LogRotationType;
   private boolean _LogTimeInGMT;
   private boolean _LoggingEnabled;
   private int _MaxRequestParamterCount;
   private String _Name;
   private String _NetworkAccessPoint;
   private Set _ServerNames;
   private String[] _Tags;
   private String _UriPath;
   private String[] _VirtualHostNames;
   private transient VirtualHost _customizer;
   private static SchemaHelper2 _schemaHelper;

   public VirtualHostMBeanImpl() {
      try {
         this._customizer = new VirtualHost(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public VirtualHostMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new VirtualHost(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public VirtualHostMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new VirtualHost(this);
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

   public Set getServerNames() {
      return this._customizer.getServerNames();
   }

   public String[] getVirtualHostNames() {
      return this._VirtualHostNames;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isServerNamesInherited() {
      return false;
   }

   public boolean isServerNamesSet() {
      return this._isSet(54);
   }

   public boolean isVirtualHostNamesInherited() {
      return false;
   }

   public boolean isVirtualHostNamesSet() {
      return this._isSet(55);
   }

   public void setServerNames(Set param0) throws InvalidAttributeValueException {
      this._ServerNames = param0;
   }

   public void setLoggingEnabled(boolean param0) {
      boolean _oldVal = this.isLoggingEnabled();
      this._customizer.setLoggingEnabled(param0);
      this._postSet(13, _oldVal, param0);
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

   public void setVirtualHostNames(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._VirtualHostNames;
      this._VirtualHostNames = param0;
      this._postSet(55, _oldVal, param0);
   }

   public String getNetworkAccessPoint() {
      return this._NetworkAccessPoint;
   }

   public boolean isLoggingEnabled() {
      return this._customizer.isLoggingEnabled();
   }

   public boolean isLoggingEnabledInherited() {
      return false;
   }

   public boolean isLoggingEnabledSet() {
      return this._isSet(13);
   }

   public boolean isNetworkAccessPointInherited() {
      return false;
   }

   public boolean isNetworkAccessPointSet() {
      return this._isSet(56);
   }

   public String getLogFileFormat() {
      return this._customizer.getLogFileFormat();
   }

   public boolean isLogFileFormatInherited() {
      return false;
   }

   public boolean isLogFileFormatSet() {
      return this._isSet(14);
   }

   public void setNetworkAccessPoint(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._NetworkAccessPoint;
      this._NetworkAccessPoint = param0;
      this._postSet(56, _oldVal, param0);
   }

   public String getUriPath() {
      return this._UriPath;
   }

   public boolean isUriPathInherited() {
      return false;
   }

   public boolean isUriPathSet() {
      return this._isSet(57);
   }

   public void setLogFileFormat(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"common", "extended"};
      param0 = LegalChecks.checkInEnum("LogFileFormat", param0, _set);
      String _oldVal = this.getLogFileFormat();
      this._customizer.setLogFileFormat(param0);
      this._postSet(14, _oldVal, param0);
   }

   public boolean getLogTimeInGMT() {
      return this._customizer.getLogTimeInGMT();
   }

   public boolean isLogTimeInGMTInherited() {
      return false;
   }

   public boolean isLogTimeInGMTSet() {
      return this._isSet(15);
   }

   public void setUriPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UriPath;
      this._UriPath = param0;
      this._postSet(57, _oldVal, param0);
   }

   public void setLogTimeInGMT(boolean param0) {
      boolean _oldVal = this.getLogTimeInGMT();
      this._customizer.setLogTimeInGMT(param0);
      this._postSet(15, _oldVal, param0);
   }

   public String getLogFileName() {
      return this._customizer.getLogFileName();
   }

   public boolean isLogFileNameInherited() {
      return false;
   }

   public boolean isLogFileNameSet() {
      return this._isSet(16);
   }

   public void setLogFileName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getLogFileName();
      this._customizer.setLogFileName(param0);
      this._postSet(16, _oldVal, param0);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
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

   public String getLogRotationType() {
      return this._customizer.getLogRotationType();
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isLogRotationTypeInherited() {
      return false;
   }

   public boolean isLogRotationTypeSet() {
      return this._isSet(22);
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setLogRotationType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"size", "date"};
      param0 = LegalChecks.checkInEnum("LogRotationType", param0, _set);
      String _oldVal = this.getLogRotationType();
      this._customizer.setLogRotationType(param0);
      this._postSet(22, _oldVal, param0);
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

   public int getLogRotationPeriodMins() {
      return this._customizer.getLogRotationPeriodMins();
   }

   public boolean isLogRotationPeriodMinsInherited() {
      return false;
   }

   public boolean isLogRotationPeriodMinsSet() {
      return this._isSet(23);
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

   public void setLogRotationPeriodMins(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("LogRotationPeriodMins", (long)param0, 1L, 2147483647L);
      int _oldVal = this.getLogRotationPeriodMins();

      try {
         this._customizer.setLogRotationPeriodMins(param0);
      } catch (DistributedManagementException var4) {
         throw new UndeclaredThrowableException(var4);
      }

      this._postSet(23, _oldVal, param0);
   }

   public String getLogRotationTimeBegin() {
      return this._customizer.getLogRotationTimeBegin();
   }

   public boolean isLogRotationTimeBeginInherited() {
      return false;
   }

   public boolean isLogRotationTimeBeginSet() {
      return this._isSet(26);
   }

   public void setLogRotationTimeBegin(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LoggingLegalHelper.validateWebServerLogRotationTimeBegin(param0);
      String _oldVal = this.getLogRotationTimeBegin();
      this._customizer.setLogRotationTimeBegin(param0);
      this._postSet(26, _oldVal, param0);
   }

   public void setMaxRequestParamterCount(int param0) throws InvalidAttributeValueException {
      int _oldVal = this.getMaxRequestParamterCount();
      this._customizer.setMaxRequestParamterCount(param0);
      this._postSet(34, _oldVal, param0);
   }

   public int getMaxRequestParamterCount() {
      return this._customizer.getMaxRequestParamterCount();
   }

   public boolean isMaxRequestParamterCountInherited() {
      return false;
   }

   public boolean isMaxRequestParamterCountSet() {
      return this._isSet(34);
   }

   public boolean isLogFileLimitEnabled() {
      return this._customizer.isLogFileLimitEnabled();
   }

   public boolean isLogFileLimitEnabledInherited() {
      return false;
   }

   public boolean isLogFileLimitEnabledSet() {
      return this._isSet(50);
   }

   public void setLogFileLimitEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this.isLogFileLimitEnabled();
      this._customizer.setLogFileLimitEnabled(param0);
      this._postSet(50, _oldVal, param0);
   }

   public int getLogFileCount() {
      return this._customizer.getLogFileCount();
   }

   public boolean isLogFileCountInherited() {
      return false;
   }

   public boolean isLogFileCountSet() {
      return this._isSet(51);
   }

   public void setLogFileCount(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("LogFileCount", (long)param0, 1L, 9999L);
      int _oldVal = this.getLogFileCount();
      this._customizer.setLogFileCount(param0);
      this._postSet(51, _oldVal, param0);
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
         idx = 51;
      }

      try {
         switch (idx) {
            case 51:
               this._customizer.setLogFileCount(7);
               if (initOne) {
                  break;
               }
            case 14:
               this._customizer.setLogFileFormat("common");
               if (initOne) {
                  break;
               }
            case 16:
               this._customizer.setLogFileName("logs/access.log");
               if (initOne) {
                  break;
               }
            case 23:
               this._customizer.setLogRotationPeriodMins(1440);
               if (initOne) {
                  break;
               }
            case 26:
               this._customizer.setLogRotationTimeBegin((String)null);
               if (initOne) {
                  break;
               }
            case 22:
               this._customizer.setLogRotationType("size");
               if (initOne) {
                  break;
               }
            case 15:
               this._customizer.setLogTimeInGMT(false);
               if (initOne) {
                  break;
               }
            case 34:
               this._customizer.setMaxRequestParamterCount(10000);
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 56:
               this._NetworkAccessPoint = null;
               if (initOne) {
                  break;
               }
            case 54:
               this._ServerNames = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 57:
               this._UriPath = null;
               if (initOne) {
                  break;
               }
            case 55:
               this._VirtualHostNames = new String[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 50:
               this._customizer.setLogFileLimitEnabled(false);
               if (initOne) {
                  break;
               }
            case 13:
               this._customizer.setLoggingEnabled(true);
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
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 52:
            case 53:
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
      return "VirtualHost";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("DynamicallyCreated")) {
         oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else {
         int oldVal;
         if (name.equals("LogFileCount")) {
            oldVal = this._LogFileCount;
            this._LogFileCount = (Integer)v;
            this._postSet(51, oldVal, this._LogFileCount);
         } else {
            String oldVal;
            if (name.equals("LogFileFormat")) {
               oldVal = this._LogFileFormat;
               this._LogFileFormat = (String)v;
               this._postSet(14, oldVal, this._LogFileFormat);
            } else if (name.equals("LogFileLimitEnabled")) {
               oldVal = this._LogFileLimitEnabled;
               this._LogFileLimitEnabled = (Boolean)v;
               this._postSet(50, oldVal, this._LogFileLimitEnabled);
            } else if (name.equals("LogFileName")) {
               oldVal = this._LogFileName;
               this._LogFileName = (String)v;
               this._postSet(16, oldVal, this._LogFileName);
            } else if (name.equals("LogRotationPeriodMins")) {
               oldVal = this._LogRotationPeriodMins;
               this._LogRotationPeriodMins = (Integer)v;
               this._postSet(23, oldVal, this._LogRotationPeriodMins);
            } else if (name.equals("LogRotationTimeBegin")) {
               oldVal = this._LogRotationTimeBegin;
               this._LogRotationTimeBegin = (String)v;
               this._postSet(26, oldVal, this._LogRotationTimeBegin);
            } else if (name.equals("LogRotationType")) {
               oldVal = this._LogRotationType;
               this._LogRotationType = (String)v;
               this._postSet(22, oldVal, this._LogRotationType);
            } else if (name.equals("LogTimeInGMT")) {
               oldVal = this._LogTimeInGMT;
               this._LogTimeInGMT = (Boolean)v;
               this._postSet(15, oldVal, this._LogTimeInGMT);
            } else if (name.equals("LoggingEnabled")) {
               oldVal = this._LoggingEnabled;
               this._LoggingEnabled = (Boolean)v;
               this._postSet(13, oldVal, this._LoggingEnabled);
            } else if (name.equals("MaxRequestParamterCount")) {
               oldVal = this._MaxRequestParamterCount;
               this._MaxRequestParamterCount = (Integer)v;
               this._postSet(34, oldVal, this._MaxRequestParamterCount);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("NetworkAccessPoint")) {
               oldVal = this._NetworkAccessPoint;
               this._NetworkAccessPoint = (String)v;
               this._postSet(56, oldVal, this._NetworkAccessPoint);
            } else if (name.equals("ServerNames")) {
               Set oldVal = this._ServerNames;
               this._ServerNames = (Set)v;
               this._postSet(54, oldVal, this._ServerNames);
            } else {
               String[] oldVal;
               if (name.equals("Tags")) {
                  oldVal = this._Tags;
                  this._Tags = (String[])((String[])v);
                  this._postSet(9, oldVal, this._Tags);
               } else if (name.equals("UriPath")) {
                  oldVal = this._UriPath;
                  this._UriPath = (String)v;
                  this._postSet(57, oldVal, this._UriPath);
               } else if (name.equals("VirtualHostNames")) {
                  oldVal = this._VirtualHostNames;
                  this._VirtualHostNames = (String[])((String[])v);
                  this._postSet(55, oldVal, this._VirtualHostNames);
               } else if (name.equals("customizer")) {
                  VirtualHost oldVal = this._customizer;
                  this._customizer = (VirtualHost)v;
               } else {
                  super.putValue(name, v);
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("LogFileCount")) {
         return new Integer(this._LogFileCount);
      } else if (name.equals("LogFileFormat")) {
         return this._LogFileFormat;
      } else if (name.equals("LogFileLimitEnabled")) {
         return new Boolean(this._LogFileLimitEnabled);
      } else if (name.equals("LogFileName")) {
         return this._LogFileName;
      } else if (name.equals("LogRotationPeriodMins")) {
         return new Integer(this._LogRotationPeriodMins);
      } else if (name.equals("LogRotationTimeBegin")) {
         return this._LogRotationTimeBegin;
      } else if (name.equals("LogRotationType")) {
         return this._LogRotationType;
      } else if (name.equals("LogTimeInGMT")) {
         return new Boolean(this._LogTimeInGMT);
      } else if (name.equals("LoggingEnabled")) {
         return new Boolean(this._LoggingEnabled);
      } else if (name.equals("MaxRequestParamterCount")) {
         return new Integer(this._MaxRequestParamterCount);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("NetworkAccessPoint")) {
         return this._NetworkAccessPoint;
      } else if (name.equals("ServerNames")) {
         return this._ServerNames;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UriPath")) {
         return this._UriPath;
      } else if (name.equals("VirtualHostNames")) {
         return this._VirtualHostNames;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends WebServerMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 16:
            case 18:
            case 21:
            case 25:
            default:
               break;
            case 8:
               if (s.equals("uri-path")) {
                  return 57;
               }
               break;
            case 12:
               if (s.equals("server-names")) {
                  return 54;
               }
               break;
            case 13:
               if (s.equals("log-file-name")) {
                  return 16;
               }
               break;
            case 14:
               if (s.equals("log-file-count")) {
                  return 51;
               }

               if (s.equals("log-time-ingmt")) {
                  return 15;
               }
               break;
            case 15:
               if (s.equals("log-file-format")) {
                  return 14;
               }

               if (s.equals("logging-enabled")) {
                  return 13;
               }
               break;
            case 17:
               if (s.equals("log-rotation-type")) {
                  return 22;
               }

               if (s.equals("virtual-host-name")) {
                  return 55;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("network-access-point")) {
                  return 56;
               }
               break;
            case 22:
               if (s.equals("log-file-limit-enabled")) {
                  return 50;
               }
               break;
            case 23:
               if (s.equals("log-rotation-time-begin")) {
                  return 26;
               }
               break;
            case 24:
               if (s.equals("log-rotation-period-mins")) {
                  return 23;
               }
               break;
            case 26:
               if (s.equals("max-request-paramter-count")) {
                  return 34;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 12:
               return new WebServerLogMBeanImpl.SchemaHelper2();
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
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 52:
            case 53:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 13:
               return "logging-enabled";
            case 14:
               return "log-file-format";
            case 15:
               return "log-time-ingmt";
            case 16:
               return "log-file-name";
            case 22:
               return "log-rotation-type";
            case 23:
               return "log-rotation-period-mins";
            case 26:
               return "log-rotation-time-begin";
            case 34:
               return "max-request-paramter-count";
            case 50:
               return "log-file-limit-enabled";
            case 51:
               return "log-file-count";
            case 54:
               return "server-names";
            case 55:
               return "virtual-host-name";
            case 56:
               return "network-access-point";
            case 57:
               return "uri-path";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 49:
               return true;
            case 55:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
               return true;
            case 18:
               return true;
            case 19:
               return true;
            case 20:
               return true;
            case 21:
               return true;
            case 22:
               return true;
            case 23:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 38:
            case 39:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 54:
            default:
               return super.isConfigurable(propIndex);
            case 36:
               return true;
            case 37:
               return true;
            case 40:
               return true;
            case 41:
               return true;
            case 42:
               return true;
            case 43:
               return true;
            case 52:
               return true;
            case 53:
               return true;
            case 55:
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends WebServerMBeanImpl.Helper {
      private VirtualHostMBeanImpl bean;

      protected Helper(VirtualHostMBeanImpl bean) {
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
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
            case 25:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 52:
            case 53:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 13:
               return "LoggingEnabled";
            case 14:
               return "LogFileFormat";
            case 15:
               return "LogTimeInGMT";
            case 16:
               return "LogFileName";
            case 22:
               return "LogRotationType";
            case 23:
               return "LogRotationPeriodMins";
            case 26:
               return "LogRotationTimeBegin";
            case 34:
               return "MaxRequestParamterCount";
            case 50:
               return "LogFileLimitEnabled";
            case 51:
               return "LogFileCount";
            case 54:
               return "ServerNames";
            case 55:
               return "VirtualHostNames";
            case 56:
               return "NetworkAccessPoint";
            case 57:
               return "UriPath";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LogFileCount")) {
            return 51;
         } else if (propName.equals("LogFileFormat")) {
            return 14;
         } else if (propName.equals("LogFileName")) {
            return 16;
         } else if (propName.equals("LogRotationPeriodMins")) {
            return 23;
         } else if (propName.equals("LogRotationTimeBegin")) {
            return 26;
         } else if (propName.equals("LogRotationType")) {
            return 22;
         } else if (propName.equals("LogTimeInGMT")) {
            return 15;
         } else if (propName.equals("MaxRequestParamterCount")) {
            return 34;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("NetworkAccessPoint")) {
            return 56;
         } else if (propName.equals("ServerNames")) {
            return 54;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UriPath")) {
            return 57;
         } else if (propName.equals("VirtualHostNames")) {
            return 55;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("LogFileLimitEnabled")) {
            return 50;
         } else {
            return propName.equals("LoggingEnabled") ? 13 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getWebServerLog() != null) {
            iterators.add(new ArrayIterator(new WebServerLogMBean[]{this.bean.getWebServerLog()}));
         }

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
            if (this.bean.isLogFileCountSet()) {
               buf.append("LogFileCount");
               buf.append(String.valueOf(this.bean.getLogFileCount()));
            }

            if (this.bean.isLogFileFormatSet()) {
               buf.append("LogFileFormat");
               buf.append(String.valueOf(this.bean.getLogFileFormat()));
            }

            if (this.bean.isLogFileNameSet()) {
               buf.append("LogFileName");
               buf.append(String.valueOf(this.bean.getLogFileName()));
            }

            if (this.bean.isLogRotationPeriodMinsSet()) {
               buf.append("LogRotationPeriodMins");
               buf.append(String.valueOf(this.bean.getLogRotationPeriodMins()));
            }

            if (this.bean.isLogRotationTimeBeginSet()) {
               buf.append("LogRotationTimeBegin");
               buf.append(String.valueOf(this.bean.getLogRotationTimeBegin()));
            }

            if (this.bean.isLogRotationTypeSet()) {
               buf.append("LogRotationType");
               buf.append(String.valueOf(this.bean.getLogRotationType()));
            }

            if (this.bean.isLogTimeInGMTSet()) {
               buf.append("LogTimeInGMT");
               buf.append(String.valueOf(this.bean.getLogTimeInGMT()));
            }

            if (this.bean.isMaxRequestParamterCountSet()) {
               buf.append("MaxRequestParamterCount");
               buf.append(String.valueOf(this.bean.getMaxRequestParamterCount()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNetworkAccessPointSet()) {
               buf.append("NetworkAccessPoint");
               buf.append(String.valueOf(this.bean.getNetworkAccessPoint()));
            }

            if (this.bean.isServerNamesSet()) {
               buf.append("ServerNames");
               buf.append(String.valueOf(this.bean.getServerNames()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUriPathSet()) {
               buf.append("UriPath");
               buf.append(String.valueOf(this.bean.getUriPath()));
            }

            if (this.bean.isVirtualHostNamesSet()) {
               buf.append("VirtualHostNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getVirtualHostNames())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isLogFileLimitEnabledSet()) {
               buf.append("LogFileLimitEnabled");
               buf.append(String.valueOf(this.bean.isLogFileLimitEnabled()));
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
            VirtualHostMBeanImpl otherTyped = (VirtualHostMBeanImpl)other;
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileCount", this.bean.getLogFileCount(), otherTyped.getLogFileCount(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileFormat", this.bean.getLogFileFormat(), otherTyped.getLogFileFormat(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileName", this.bean.getLogFileName(), otherTyped.getLogFileName(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogRotationPeriodMins", this.bean.getLogRotationPeriodMins(), otherTyped.getLogRotationPeriodMins(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogRotationTimeBegin", this.bean.getLogRotationTimeBegin(), otherTyped.getLogRotationTimeBegin(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogRotationType", this.bean.getLogRotationType(), otherTyped.getLogRotationType(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogTimeInGMT", this.bean.getLogTimeInGMT(), otherTyped.getLogTimeInGMT(), false);
            }

            this.computeDiff("MaxRequestParamterCount", this.bean.getMaxRequestParamterCount(), otherTyped.getMaxRequestParamterCount(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("NetworkAccessPoint", this.bean.getNetworkAccessPoint(), otherTyped.getNetworkAccessPoint(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UriPath", this.bean.getUriPath(), otherTyped.getUriPath(), false);
            this.computeDiff("VirtualHostNames", this.bean.getVirtualHostNames(), otherTyped.getVirtualHostNames(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LogFileLimitEnabled", this.bean.isLogFileLimitEnabled(), otherTyped.isLogFileLimitEnabled(), false);
            }

            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("LoggingEnabled", this.bean.isLoggingEnabled(), otherTyped.isLoggingEnabled(), false);
            }

         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            VirtualHostMBeanImpl original = (VirtualHostMBeanImpl)event.getSourceBean();
            VirtualHostMBeanImpl proposed = (VirtualHostMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LogFileCount")) {
                  original.setLogFileCount(proposed.getLogFileCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 51);
               } else if (prop.equals("LogFileFormat")) {
                  original.setLogFileFormat(proposed.getLogFileFormat());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("LogFileName")) {
                  original.setLogFileName(proposed.getLogFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("LogRotationPeriodMins")) {
                  original.setLogRotationPeriodMins(proposed.getLogRotationPeriodMins());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("LogRotationTimeBegin")) {
                  original.setLogRotationTimeBegin(proposed.getLogRotationTimeBegin());
                  original._conditionalUnset(update.isUnsetUpdate(), 26);
               } else if (prop.equals("LogRotationType")) {
                  original.setLogRotationType(proposed.getLogRotationType());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("LogTimeInGMT")) {
                  original.setLogTimeInGMT(proposed.getLogTimeInGMT());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MaxRequestParamterCount")) {
                  original.setMaxRequestParamterCount(proposed.getMaxRequestParamterCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 34);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("NetworkAccessPoint")) {
                  original.setNetworkAccessPoint(proposed.getNetworkAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 56);
               } else if (!prop.equals("ServerNames")) {
                  if (prop.equals("Tags")) {
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
                  } else if (prop.equals("UriPath")) {
                     original.setUriPath(proposed.getUriPath());
                     original._conditionalUnset(update.isUnsetUpdate(), 57);
                  } else if (prop.equals("VirtualHostNames")) {
                     original.setVirtualHostNames(proposed.getVirtualHostNames());
                     original._conditionalUnset(update.isUnsetUpdate(), 55);
                  } else if (!prop.equals("DynamicallyCreated")) {
                     if (prop.equals("LogFileLimitEnabled")) {
                        original.setLogFileLimitEnabled(proposed.isLogFileLimitEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 50);
                     } else if (prop.equals("LoggingEnabled")) {
                        original.setLoggingEnabled(proposed.isLoggingEnabled());
                        original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            VirtualHostMBeanImpl copy = (VirtualHostMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileCount")) && this.bean.isLogFileCountSet()) {
               copy.setLogFileCount(this.bean.getLogFileCount());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileFormat")) && this.bean.isLogFileFormatSet()) {
               copy.setLogFileFormat(this.bean.getLogFileFormat());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileName")) && this.bean.isLogFileNameSet()) {
               copy.setLogFileName(this.bean.getLogFileName());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogRotationPeriodMins")) && this.bean.isLogRotationPeriodMinsSet()) {
               copy.setLogRotationPeriodMins(this.bean.getLogRotationPeriodMins());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogRotationTimeBegin")) && this.bean.isLogRotationTimeBeginSet()) {
               copy.setLogRotationTimeBegin(this.bean.getLogRotationTimeBegin());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogRotationType")) && this.bean.isLogRotationTypeSet()) {
               copy.setLogRotationType(this.bean.getLogRotationType());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogTimeInGMT")) && this.bean.isLogTimeInGMTSet()) {
               copy.setLogTimeInGMT(this.bean.getLogTimeInGMT());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRequestParamterCount")) && this.bean.isMaxRequestParamterCountSet()) {
               copy.setMaxRequestParamterCount(this.bean.getMaxRequestParamterCount());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("NetworkAccessPoint")) && this.bean.isNetworkAccessPointSet()) {
               copy.setNetworkAccessPoint(this.bean.getNetworkAccessPoint());
            }

            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UriPath")) && this.bean.isUriPathSet()) {
               copy.setUriPath(this.bean.getUriPath());
            }

            if ((excludeProps == null || !excludeProps.contains("VirtualHostNames")) && this.bean.isVirtualHostNamesSet()) {
               o = this.bean.getVirtualHostNames();
               copy.setVirtualHostNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LogFileLimitEnabled")) && this.bean.isLogFileLimitEnabledSet()) {
               copy.setLogFileLimitEnabled(this.bean.isLogFileLimitEnabled());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("LoggingEnabled")) && this.bean.isLoggingEnabledSet()) {
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

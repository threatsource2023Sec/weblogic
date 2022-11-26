package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ScriptMBeanImpl extends ConfigurationMBeanImpl implements ScriptMBean, Serializable {
   private String[] _Arguments;
   private Properties _Environment;
   private boolean _IgnoreFailures;
   private int _NumberOfRetriesAllowed;
   private String _PathToErrorHandlerScript;
   private String _PathToScript;
   private long _RetryDelayInMillis;
   private int _TimeoutInSeconds;
   private String _WorkingDirectory;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ScriptMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ScriptMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ScriptMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ScriptMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ScriptMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ScriptMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ScriptMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ScriptMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ScriptMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getWorkingDirectory() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getWorkingDirectory(), this) : this._WorkingDirectory;
   }

   public boolean isWorkingDirectoryInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isWorkingDirectorySet() {
      return this._isSet(10);
   }

   public void setWorkingDirectory(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._WorkingDirectory;
      this._WorkingDirectory = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ScriptMBeanImpl source = (ScriptMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPathToScript() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getPathToScript(), this) : this._PathToScript;
   }

   public boolean isPathToScriptInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isPathToScriptSet() {
      return this._isSet(11);
   }

   public void setPathToScript(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._PathToScript;
      this._PathToScript = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ScriptMBeanImpl source = (ScriptMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getArguments() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getArguments() : this._Arguments;
   }

   public boolean isArgumentsInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isArgumentsSet() {
      return this._isSet(12);
   }

   public void setArguments(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String[] _oldVal = this._Arguments;
      this._Arguments = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ScriptMBeanImpl source = (ScriptMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public Properties getEnvironment() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getEnvironment() : this._Environment;
   }

   public String getEnvironmentAsString() {
      return StringHelper.objectToString(this.getEnvironment());
   }

   public boolean isEnvironmentInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isEnvironmentSet() {
      return this._isSet(13);
   }

   public void setEnvironmentAsString(String param0) {
      try {
         this.setEnvironment(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setEnvironment(Properties param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      Properties _oldVal = this._Environment;
      this._Environment = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ScriptMBeanImpl source = (ScriptMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isIgnoreFailures() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().isIgnoreFailures() : this._IgnoreFailures;
   }

   public boolean isIgnoreFailuresInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isIgnoreFailuresSet() {
      return this._isSet(14);
   }

   public void setIgnoreFailures(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      boolean _oldVal = this._IgnoreFailures;
      this._IgnoreFailures = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ScriptMBeanImpl source = (ScriptMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public int getNumberOfRetriesAllowed() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().getNumberOfRetriesAllowed() : this._NumberOfRetriesAllowed;
   }

   public boolean isNumberOfRetriesAllowedInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isNumberOfRetriesAllowedSet() {
      return this._isSet(15);
   }

   public void setNumberOfRetriesAllowed(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("NumberOfRetriesAllowed", param0, 0);
      boolean wasSet = this._isSet(15);
      int _oldVal = this._NumberOfRetriesAllowed;
      this._NumberOfRetriesAllowed = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ScriptMBeanImpl source = (ScriptMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public long getRetryDelayInMillis() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getRetryDelayInMillis() : this._RetryDelayInMillis;
   }

   public boolean isRetryDelayInMillisInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isRetryDelayInMillisSet() {
      return this._isSet(16);
   }

   public void setRetryDelayInMillis(long param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("RetryDelayInMillis", param0, 0L);
      boolean wasSet = this._isSet(16);
      long _oldVal = this._RetryDelayInMillis;
      this._RetryDelayInMillis = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         ScriptMBeanImpl source = (ScriptMBeanImpl)var6.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPathToErrorHandlerScript() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getPathToErrorHandlerScript(), this) : this._PathToErrorHandlerScript;
   }

   public boolean isPathToErrorHandlerScriptInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isPathToErrorHandlerScriptSet() {
      return this._isSet(17);
   }

   public void setPathToErrorHandlerScript(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      String _oldVal = this._PathToErrorHandlerScript;
      this._PathToErrorHandlerScript = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ScriptMBeanImpl source = (ScriptMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public int getTimeoutInSeconds() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getTimeoutInSeconds() : this._TimeoutInSeconds;
   }

   public boolean isTimeoutInSecondsInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isTimeoutInSecondsSet() {
      return this._isSet(18);
   }

   public void setTimeoutInSeconds(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      int _oldVal = this._TimeoutInSeconds;
      this._TimeoutInSeconds = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ScriptMBeanImpl source = (ScriptMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._Arguments = new String[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._Environment = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._NumberOfRetriesAllowed = 0;
               if (initOne) {
                  break;
               }
            case 17:
               this._PathToErrorHandlerScript = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._PathToScript = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._RetryDelayInMillis = 2000L;
               if (initOne) {
                  break;
               }
            case 18:
               this._TimeoutInSeconds = -1;
               if (initOne) {
                  break;
               }
            case 10:
               this._WorkingDirectory = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._IgnoreFailures = false;
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
      return "Script";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Arguments")) {
         String[] oldVal = this._Arguments;
         this._Arguments = (String[])((String[])v);
         this._postSet(12, oldVal, this._Arguments);
      } else if (name.equals("Environment")) {
         Properties oldVal = this._Environment;
         this._Environment = (Properties)v;
         this._postSet(13, oldVal, this._Environment);
      } else if (name.equals("IgnoreFailures")) {
         boolean oldVal = this._IgnoreFailures;
         this._IgnoreFailures = (Boolean)v;
         this._postSet(14, oldVal, this._IgnoreFailures);
      } else {
         int oldVal;
         if (name.equals("NumberOfRetriesAllowed")) {
            oldVal = this._NumberOfRetriesAllowed;
            this._NumberOfRetriesAllowed = (Integer)v;
            this._postSet(15, oldVal, this._NumberOfRetriesAllowed);
         } else {
            String oldVal;
            if (name.equals("PathToErrorHandlerScript")) {
               oldVal = this._PathToErrorHandlerScript;
               this._PathToErrorHandlerScript = (String)v;
               this._postSet(17, oldVal, this._PathToErrorHandlerScript);
            } else if (name.equals("PathToScript")) {
               oldVal = this._PathToScript;
               this._PathToScript = (String)v;
               this._postSet(11, oldVal, this._PathToScript);
            } else if (name.equals("RetryDelayInMillis")) {
               long oldVal = this._RetryDelayInMillis;
               this._RetryDelayInMillis = (Long)v;
               this._postSet(16, oldVal, this._RetryDelayInMillis);
            } else if (name.equals("TimeoutInSeconds")) {
               oldVal = this._TimeoutInSeconds;
               this._TimeoutInSeconds = (Integer)v;
               this._postSet(18, oldVal, this._TimeoutInSeconds);
            } else if (name.equals("WorkingDirectory")) {
               oldVal = this._WorkingDirectory;
               this._WorkingDirectory = (String)v;
               this._postSet(10, oldVal, this._WorkingDirectory);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("Arguments")) {
         return this._Arguments;
      } else if (name.equals("Environment")) {
         return this._Environment;
      } else if (name.equals("IgnoreFailures")) {
         return new Boolean(this._IgnoreFailures);
      } else if (name.equals("NumberOfRetriesAllowed")) {
         return new Integer(this._NumberOfRetriesAllowed);
      } else if (name.equals("PathToErrorHandlerScript")) {
         return this._PathToErrorHandlerScript;
      } else if (name.equals("PathToScript")) {
         return this._PathToScript;
      } else if (name.equals("RetryDelayInMillis")) {
         return new Long(this._RetryDelayInMillis);
      } else if (name.equals("TimeoutInSeconds")) {
         return new Integer(this._TimeoutInSeconds);
      } else {
         return name.equals("WorkingDirectory") ? this._WorkingDirectory : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("argument")) {
                  return 12;
               }
            case 9:
            case 10:
            case 12:
            case 13:
            case 16:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
            case 26:
            case 27:
            default:
               break;
            case 11:
               if (s.equals("environment")) {
                  return 13;
               }
               break;
            case 14:
               if (s.equals("path-to-script")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("ignore-failures")) {
                  return 14;
               }
               break;
            case 17:
               if (s.equals("working-directory")) {
                  return 10;
               }
               break;
            case 18:
               if (s.equals("timeout-in-seconds")) {
                  return 18;
               }
               break;
            case 21:
               if (s.equals("retry-delay-in-millis")) {
                  return 16;
               }
               break;
            case 25:
               if (s.equals("number-of-retries-allowed")) {
                  return 15;
               }
               break;
            case 28:
               if (s.equals("path-to-error-handler-script")) {
                  return 17;
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
               return "working-directory";
            case 11:
               return "path-to-script";
            case 12:
               return "argument";
            case 13:
               return "environment";
            case 14:
               return "ignore-failures";
            case 15:
               return "number-of-retries-allowed";
            case 16:
               return "retry-delay-in-millis";
            case 17:
               return "path-to-error-handler-script";
            case 18:
               return "timeout-in-seconds";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 12:
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
      private ScriptMBeanImpl bean;

      protected Helper(ScriptMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "WorkingDirectory";
            case 11:
               return "PathToScript";
            case 12:
               return "Arguments";
            case 13:
               return "Environment";
            case 14:
               return "IgnoreFailures";
            case 15:
               return "NumberOfRetriesAllowed";
            case 16:
               return "RetryDelayInMillis";
            case 17:
               return "PathToErrorHandlerScript";
            case 18:
               return "TimeoutInSeconds";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Arguments")) {
            return 12;
         } else if (propName.equals("Environment")) {
            return 13;
         } else if (propName.equals("NumberOfRetriesAllowed")) {
            return 15;
         } else if (propName.equals("PathToErrorHandlerScript")) {
            return 17;
         } else if (propName.equals("PathToScript")) {
            return 11;
         } else if (propName.equals("RetryDelayInMillis")) {
            return 16;
         } else if (propName.equals("TimeoutInSeconds")) {
            return 18;
         } else if (propName.equals("WorkingDirectory")) {
            return 10;
         } else {
            return propName.equals("IgnoreFailures") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isArgumentsSet()) {
               buf.append("Arguments");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getArguments())));
            }

            if (this.bean.isEnvironmentSet()) {
               buf.append("Environment");
               buf.append(String.valueOf(this.bean.getEnvironment()));
            }

            if (this.bean.isNumberOfRetriesAllowedSet()) {
               buf.append("NumberOfRetriesAllowed");
               buf.append(String.valueOf(this.bean.getNumberOfRetriesAllowed()));
            }

            if (this.bean.isPathToErrorHandlerScriptSet()) {
               buf.append("PathToErrorHandlerScript");
               buf.append(String.valueOf(this.bean.getPathToErrorHandlerScript()));
            }

            if (this.bean.isPathToScriptSet()) {
               buf.append("PathToScript");
               buf.append(String.valueOf(this.bean.getPathToScript()));
            }

            if (this.bean.isRetryDelayInMillisSet()) {
               buf.append("RetryDelayInMillis");
               buf.append(String.valueOf(this.bean.getRetryDelayInMillis()));
            }

            if (this.bean.isTimeoutInSecondsSet()) {
               buf.append("TimeoutInSeconds");
               buf.append(String.valueOf(this.bean.getTimeoutInSeconds()));
            }

            if (this.bean.isWorkingDirectorySet()) {
               buf.append("WorkingDirectory");
               buf.append(String.valueOf(this.bean.getWorkingDirectory()));
            }

            if (this.bean.isIgnoreFailuresSet()) {
               buf.append("IgnoreFailures");
               buf.append(String.valueOf(this.bean.isIgnoreFailures()));
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
            ScriptMBeanImpl otherTyped = (ScriptMBeanImpl)other;
            this.computeDiff("Arguments", this.bean.getArguments(), otherTyped.getArguments(), true);
            this.computeDiff("Environment", this.bean.getEnvironment(), otherTyped.getEnvironment(), true);
            this.computeDiff("NumberOfRetriesAllowed", this.bean.getNumberOfRetriesAllowed(), otherTyped.getNumberOfRetriesAllowed(), true);
            this.computeDiff("PathToErrorHandlerScript", this.bean.getPathToErrorHandlerScript(), otherTyped.getPathToErrorHandlerScript(), true);
            this.computeDiff("PathToScript", this.bean.getPathToScript(), otherTyped.getPathToScript(), true);
            this.computeDiff("RetryDelayInMillis", this.bean.getRetryDelayInMillis(), otherTyped.getRetryDelayInMillis(), true);
            this.computeDiff("TimeoutInSeconds", this.bean.getTimeoutInSeconds(), otherTyped.getTimeoutInSeconds(), true);
            this.computeDiff("WorkingDirectory", this.bean.getWorkingDirectory(), otherTyped.getWorkingDirectory(), true);
            this.computeDiff("IgnoreFailures", this.bean.isIgnoreFailures(), otherTyped.isIgnoreFailures(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ScriptMBeanImpl original = (ScriptMBeanImpl)event.getSourceBean();
            ScriptMBeanImpl proposed = (ScriptMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Arguments")) {
                  original.setArguments(proposed.getArguments());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Environment")) {
                  original.setEnvironment(proposed.getEnvironment() == null ? null : (Properties)proposed.getEnvironment().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("NumberOfRetriesAllowed")) {
                  original.setNumberOfRetriesAllowed(proposed.getNumberOfRetriesAllowed());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("PathToErrorHandlerScript")) {
                  original.setPathToErrorHandlerScript(proposed.getPathToErrorHandlerScript());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("PathToScript")) {
                  original.setPathToScript(proposed.getPathToScript());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("RetryDelayInMillis")) {
                  original.setRetryDelayInMillis(proposed.getRetryDelayInMillis());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("TimeoutInSeconds")) {
                  original.setTimeoutInSeconds(proposed.getTimeoutInSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("WorkingDirectory")) {
                  original.setWorkingDirectory(proposed.getWorkingDirectory());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("IgnoreFailures")) {
                  original.setIgnoreFailures(proposed.isIgnoreFailures());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
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
            ScriptMBeanImpl copy = (ScriptMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Arguments")) && this.bean.isArgumentsSet()) {
               Object o = this.bean.getArguments();
               copy.setArguments(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Environment")) && this.bean.isEnvironmentSet()) {
               copy.setEnvironment(this.bean.getEnvironment());
            }

            if ((excludeProps == null || !excludeProps.contains("NumberOfRetriesAllowed")) && this.bean.isNumberOfRetriesAllowedSet()) {
               copy.setNumberOfRetriesAllowed(this.bean.getNumberOfRetriesAllowed());
            }

            if ((excludeProps == null || !excludeProps.contains("PathToErrorHandlerScript")) && this.bean.isPathToErrorHandlerScriptSet()) {
               copy.setPathToErrorHandlerScript(this.bean.getPathToErrorHandlerScript());
            }

            if ((excludeProps == null || !excludeProps.contains("PathToScript")) && this.bean.isPathToScriptSet()) {
               copy.setPathToScript(this.bean.getPathToScript());
            }

            if ((excludeProps == null || !excludeProps.contains("RetryDelayInMillis")) && this.bean.isRetryDelayInMillisSet()) {
               copy.setRetryDelayInMillis(this.bean.getRetryDelayInMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeoutInSeconds")) && this.bean.isTimeoutInSecondsSet()) {
               copy.setTimeoutInSeconds(this.bean.getTimeoutInSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkingDirectory")) && this.bean.isWorkingDirectorySet()) {
               copy.setWorkingDirectory(this.bean.getWorkingDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreFailures")) && this.bean.isIgnoreFailuresSet()) {
               copy.setIgnoreFailures(this.bean.isIgnoreFailures());
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

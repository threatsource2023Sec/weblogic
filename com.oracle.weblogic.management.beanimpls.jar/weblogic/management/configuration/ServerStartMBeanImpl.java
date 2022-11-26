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
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.ServerStart;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ServerStartMBeanImpl extends ConfigurationMBeanImpl implements ServerStartMBean, Serializable {
   private String _Arguments;
   private String _BeaHome;
   private Properties _BootProperties;
   private String _ClassPath;
   private boolean _DynamicallyCreated;
   private String _JavaHome;
   private String _JavaVendor;
   private String _MWHome;
   private int _MaxRestartCount;
   private String _Name;
   private String _OutputFile;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private String _RootDirectory;
   private String _SecurityPolicyFile;
   private Properties _StartupProperties;
   private String[] _Tags;
   private String _Username;
   private transient ServerStart _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ServerStartMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ServerStartMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ServerStartMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ServerStartMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ServerStartMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ServerStartMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ServerStartMBeanImpl() {
      try {
         this._customizer = new ServerStart(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ServerStartMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ServerStart(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ServerStartMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ServerStart(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getJavaVendor() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getJavaVendor(), this) : this._JavaVendor;
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

   public boolean isJavaVendorInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isJavaVendorSet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setJavaVendor(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._JavaVendor;
      this._JavaVendor = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
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
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public String getJavaHome() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getJavaHome(), this) : this._JavaHome;
   }

   public boolean isJavaHomeInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isJavaHomeSet() {
      return this._isSet(11);
   }

   public void setJavaHome(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalHelper.validateJavaHome(param0);
      boolean wasSet = this._isSet(11);
      String _oldVal = this._JavaHome;
      this._JavaHome = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getClassPath() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getClassPath(), this) : this._ClassPath;
   }

   public boolean isClassPathInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isClassPathSet() {
      return this._isSet(12);
   }

   public void setClassPath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalHelper.validateClasspath(param0);
      boolean wasSet = this._isSet(12);
      String _oldVal = this._ClassPath;
      this._ClassPath = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getBeaHome() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getBeaHome(), this) : this._BeaHome;
   }

   public boolean isBeaHomeInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isBeaHomeSet() {
      return this._isSet(13);
   }

   public void setBeaHome(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalHelper.validateBeaHome(param0);
      boolean wasSet = this._isSet(13);
      String _oldVal = this._BeaHome;
      this._BeaHome = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getMWHome() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getMWHome(), this) : this._MWHome;
   }

   public boolean isMWHomeInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isMWHomeSet() {
      return this._isSet(14);
   }

   public void setMWHome(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalHelper.validateBeaHome(param0);
      boolean wasSet = this._isSet(14);
      String _oldVal = this._MWHome;
      this._MWHome = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public String getRootDirectory() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getRootDirectory(), this) : this._RootDirectory;
   }

   public boolean isRootDirectoryInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isRootDirectorySet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setRootDirectory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalHelper.validateRootDirectory(param0);
      boolean wasSet = this._isSet(15);
      String _oldVal = this._RootDirectory;
      this._RootDirectory = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSecurityPolicyFile() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getSecurityPolicyFile(), this) : this._SecurityPolicyFile;
   }

   public boolean isSecurityPolicyFileInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isSecurityPolicyFileSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setSecurityPolicyFile(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalHelper.validateSecurityPolicyFile(param0);
      boolean wasSet = this._isSet(16);
      String _oldVal = this._SecurityPolicyFile;
      this._SecurityPolicyFile = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getArguments() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getArguments(), this) : this._Arguments;
   }

   public boolean isArgumentsInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isArgumentsSet() {
      return this._isSet(17);
   }

   public void setArguments(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalHelper.validateArguments(param0);
      boolean wasSet = this._isSet(17);
      String _oldVal = this._Arguments;
      this._Arguments = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxRestartCount() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._getDelegateBean().getMaxRestartCount() : this._MaxRestartCount;
   }

   public boolean isMaxRestartCountInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isMaxRestartCountSet() {
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

   public void setMaxRestartCount(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      int _oldVal = this._MaxRestartCount;
      this._MaxRestartCount = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOutputFile() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._performMacroSubstitution(this._getDelegateBean().getOutputFile(), this) : this._OutputFile;
   }

   public boolean isOutputFileInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isOutputFileSet() {
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

   public void setOutputFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      String _oldVal = this._OutputFile;
      this._OutputFile = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public String getUsername() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._performMacroSubstitution(this._getDelegateBean().getUsername(), this) : this._Username;
   }

   public boolean isUsernameInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isUsernameSet() {
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
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
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

   public void setUsername(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      String _oldVal = this._Username;
      this._Username = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPassword() {
      if (!this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21)) {
         return this._performMacroSubstitution(this._getDelegateBean().getPassword(), this);
      } else {
         byte[] bEncrypted = this.getPasswordEncrypted();
         return bEncrypted == null ? null : this._decrypt("Password", bEncrypted);
      }
   }

   public boolean isPasswordInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isPasswordSet() {
      return this.isPasswordEncryptedSet();
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

   public void setPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setPasswordEncrypted(param0 == null ? null : this._encrypt("Password", param0));
   }

   public byte[] getPasswordEncrypted() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._getDelegateBean().getPasswordEncrypted() : this._getHelper()._cloneArray(this._PasswordEncrypted);
   }

   public String getPasswordEncryptedAsString() {
      byte[] obj = this.getPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isPasswordEncryptedInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isPasswordEncryptedSet() {
      return this._isSet(22);
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

   public Properties getBootProperties() {
      return this._customizer.getBootProperties();
   }

   public boolean isBootPropertiesInherited() {
      return false;
   }

   public boolean isBootPropertiesSet() {
      return this._isSet(23);
   }

   public void setBootProperties(Properties param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._BootProperties = param0;
   }

   public Properties getStartupProperties() {
      return this._customizer.getStartupProperties();
   }

   public boolean isStartupPropertiesInherited() {
      return false;
   }

   public boolean isStartupPropertiesSet() {
      return this._isSet(24);
   }

   public void setStartupProperties(Properties param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._StartupProperties = param0;
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setPasswordEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      byte[] _oldVal = this._PasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of ServerStartMBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(22, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            ServerStartMBeanImpl source = (ServerStartMBeanImpl)var4.next();
            if (source != null && !source._isSet(22)) {
               source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
            }
         }

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
         if (idx == 21) {
            this._markSet(22, false);
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._Arguments = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._BeaHome = null;
               if (initOne) {
                  break;
               }
            case 23:
               this._BootProperties = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._ClassPath = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._JavaHome = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._JavaVendor = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._MWHome = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._MaxRestartCount = 0;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 19:
               this._OutputFile = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 22:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._RootDirectory = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._SecurityPolicyFile = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._StartupProperties = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 20:
               this._Username = "";
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
      return "ServerStart";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Arguments")) {
         oldVal = this._Arguments;
         this._Arguments = (String)v;
         this._postSet(17, oldVal, this._Arguments);
      } else if (name.equals("BeaHome")) {
         oldVal = this._BeaHome;
         this._BeaHome = (String)v;
         this._postSet(13, oldVal, this._BeaHome);
      } else {
         Properties oldVal;
         if (name.equals("BootProperties")) {
            oldVal = this._BootProperties;
            this._BootProperties = (Properties)v;
            this._postSet(23, oldVal, this._BootProperties);
         } else if (name.equals("ClassPath")) {
            oldVal = this._ClassPath;
            this._ClassPath = (String)v;
            this._postSet(12, oldVal, this._ClassPath);
         } else if (name.equals("DynamicallyCreated")) {
            boolean oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else if (name.equals("JavaHome")) {
            oldVal = this._JavaHome;
            this._JavaHome = (String)v;
            this._postSet(11, oldVal, this._JavaHome);
         } else if (name.equals("JavaVendor")) {
            oldVal = this._JavaVendor;
            this._JavaVendor = (String)v;
            this._postSet(10, oldVal, this._JavaVendor);
         } else if (name.equals("MWHome")) {
            oldVal = this._MWHome;
            this._MWHome = (String)v;
            this._postSet(14, oldVal, this._MWHome);
         } else if (name.equals("MaxRestartCount")) {
            int oldVal = this._MaxRestartCount;
            this._MaxRestartCount = (Integer)v;
            this._postSet(18, oldVal, this._MaxRestartCount);
         } else if (name.equals("Name")) {
            oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(2, oldVal, this._Name);
         } else if (name.equals("OutputFile")) {
            oldVal = this._OutputFile;
            this._OutputFile = (String)v;
            this._postSet(19, oldVal, this._OutputFile);
         } else if (name.equals("Password")) {
            oldVal = this._Password;
            this._Password = (String)v;
            this._postSet(21, oldVal, this._Password);
         } else if (name.equals("PasswordEncrypted")) {
            byte[] oldVal = this._PasswordEncrypted;
            this._PasswordEncrypted = (byte[])((byte[])v);
            this._postSet(22, oldVal, this._PasswordEncrypted);
         } else if (name.equals("RootDirectory")) {
            oldVal = this._RootDirectory;
            this._RootDirectory = (String)v;
            this._postSet(15, oldVal, this._RootDirectory);
         } else if (name.equals("SecurityPolicyFile")) {
            oldVal = this._SecurityPolicyFile;
            this._SecurityPolicyFile = (String)v;
            this._postSet(16, oldVal, this._SecurityPolicyFile);
         } else if (name.equals("StartupProperties")) {
            oldVal = this._StartupProperties;
            this._StartupProperties = (Properties)v;
            this._postSet(24, oldVal, this._StartupProperties);
         } else if (name.equals("Tags")) {
            String[] oldVal = this._Tags;
            this._Tags = (String[])((String[])v);
            this._postSet(9, oldVal, this._Tags);
         } else if (name.equals("Username")) {
            oldVal = this._Username;
            this._Username = (String)v;
            this._postSet(20, oldVal, this._Username);
         } else if (name.equals("customizer")) {
            ServerStart oldVal = this._customizer;
            this._customizer = (ServerStart)v;
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("Arguments")) {
         return this._Arguments;
      } else if (name.equals("BeaHome")) {
         return this._BeaHome;
      } else if (name.equals("BootProperties")) {
         return this._BootProperties;
      } else if (name.equals("ClassPath")) {
         return this._ClassPath;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("JavaHome")) {
         return this._JavaHome;
      } else if (name.equals("JavaVendor")) {
         return this._JavaVendor;
      } else if (name.equals("MWHome")) {
         return this._MWHome;
      } else if (name.equals("MaxRestartCount")) {
         return new Integer(this._MaxRestartCount);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("OutputFile")) {
         return this._OutputFile;
      } else if (name.equals("Password")) {
         return this._Password;
      } else if (name.equals("PasswordEncrypted")) {
         return this._PasswordEncrypted;
      } else if (name.equals("RootDirectory")) {
         return this._RootDirectory;
      } else if (name.equals("SecurityPolicyFile")) {
         return this._SecurityPolicyFile;
      } else if (name.equals("StartupProperties")) {
         return this._StartupProperties;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Username")) {
         return this._Username;
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
            case 12:
            case 13:
            case 16:
            default:
               break;
            case 7:
               if (s.equals("mw-home")) {
                  return 14;
               }
               break;
            case 8:
               if (s.equals("bea-home")) {
                  return 13;
               }

               if (s.equals("password")) {
                  return 21;
               }

               if (s.equals("username")) {
                  return 20;
               }
               break;
            case 9:
               if (s.equals("arguments")) {
                  return 17;
               }

               if (s.equals("java-home")) {
                  return 11;
               }
               break;
            case 10:
               if (s.equals("class-path")) {
                  return 12;
               }
               break;
            case 11:
               if (s.equals("java-vendor")) {
                  return 10;
               }

               if (s.equals("output-file")) {
                  return 19;
               }
               break;
            case 14:
               if (s.equals("root-directory")) {
                  return 15;
               }
               break;
            case 15:
               if (s.equals("boot-properties")) {
                  return 23;
               }
               break;
            case 17:
               if (s.equals("max-restart-count")) {
                  return 18;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 22;
               }

               if (s.equals("startup-properties")) {
                  return 24;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("security-policy-file")) {
                  return 16;
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
               return "java-vendor";
            case 11:
               return "java-home";
            case 12:
               return "class-path";
            case 13:
               return "bea-home";
            case 14:
               return "mw-home";
            case 15:
               return "root-directory";
            case 16:
               return "security-policy-file";
            case 17:
               return "arguments";
            case 18:
               return "max-restart-count";
            case 19:
               return "output-file";
            case 20:
               return "username";
            case 21:
               return "password";
            case 22:
               return "password-encrypted";
            case 23:
               return "boot-properties";
            case 24:
               return "startup-properties";
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
      private ServerStartMBeanImpl bean;

      protected Helper(ServerStartMBeanImpl bean) {
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
               return "JavaVendor";
            case 11:
               return "JavaHome";
            case 12:
               return "ClassPath";
            case 13:
               return "BeaHome";
            case 14:
               return "MWHome";
            case 15:
               return "RootDirectory";
            case 16:
               return "SecurityPolicyFile";
            case 17:
               return "Arguments";
            case 18:
               return "MaxRestartCount";
            case 19:
               return "OutputFile";
            case 20:
               return "Username";
            case 21:
               return "Password";
            case 22:
               return "PasswordEncrypted";
            case 23:
               return "BootProperties";
            case 24:
               return "StartupProperties";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Arguments")) {
            return 17;
         } else if (propName.equals("BeaHome")) {
            return 13;
         } else if (propName.equals("BootProperties")) {
            return 23;
         } else if (propName.equals("ClassPath")) {
            return 12;
         } else if (propName.equals("JavaHome")) {
            return 11;
         } else if (propName.equals("JavaVendor")) {
            return 10;
         } else if (propName.equals("MWHome")) {
            return 14;
         } else if (propName.equals("MaxRestartCount")) {
            return 18;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("OutputFile")) {
            return 19;
         } else if (propName.equals("Password")) {
            return 21;
         } else if (propName.equals("PasswordEncrypted")) {
            return 22;
         } else if (propName.equals("RootDirectory")) {
            return 15;
         } else if (propName.equals("SecurityPolicyFile")) {
            return 16;
         } else if (propName.equals("StartupProperties")) {
            return 24;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Username")) {
            return 20;
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
            if (this.bean.isArgumentsSet()) {
               buf.append("Arguments");
               buf.append(String.valueOf(this.bean.getArguments()));
            }

            if (this.bean.isBeaHomeSet()) {
               buf.append("BeaHome");
               buf.append(String.valueOf(this.bean.getBeaHome()));
            }

            if (this.bean.isBootPropertiesSet()) {
               buf.append("BootProperties");
               buf.append(String.valueOf(this.bean.getBootProperties()));
            }

            if (this.bean.isClassPathSet()) {
               buf.append("ClassPath");
               buf.append(String.valueOf(this.bean.getClassPath()));
            }

            if (this.bean.isJavaHomeSet()) {
               buf.append("JavaHome");
               buf.append(String.valueOf(this.bean.getJavaHome()));
            }

            if (this.bean.isJavaVendorSet()) {
               buf.append("JavaVendor");
               buf.append(String.valueOf(this.bean.getJavaVendor()));
            }

            if (this.bean.isMWHomeSet()) {
               buf.append("MWHome");
               buf.append(String.valueOf(this.bean.getMWHome()));
            }

            if (this.bean.isMaxRestartCountSet()) {
               buf.append("MaxRestartCount");
               buf.append(String.valueOf(this.bean.getMaxRestartCount()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isOutputFileSet()) {
               buf.append("OutputFile");
               buf.append(String.valueOf(this.bean.getOutputFile()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isRootDirectorySet()) {
               buf.append("RootDirectory");
               buf.append(String.valueOf(this.bean.getRootDirectory()));
            }

            if (this.bean.isSecurityPolicyFileSet()) {
               buf.append("SecurityPolicyFile");
               buf.append(String.valueOf(this.bean.getSecurityPolicyFile()));
            }

            if (this.bean.isStartupPropertiesSet()) {
               buf.append("StartupProperties");
               buf.append(String.valueOf(this.bean.getStartupProperties()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUsernameSet()) {
               buf.append("Username");
               buf.append(String.valueOf(this.bean.getUsername()));
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
            ServerStartMBeanImpl otherTyped = (ServerStartMBeanImpl)other;
            this.computeDiff("Arguments", this.bean.getArguments(), otherTyped.getArguments(), true);
            this.computeDiff("BeaHome", this.bean.getBeaHome(), otherTyped.getBeaHome(), true);
            this.computeDiff("ClassPath", this.bean.getClassPath(), otherTyped.getClassPath(), true);
            this.computeDiff("JavaHome", this.bean.getJavaHome(), otherTyped.getJavaHome(), true);
            this.computeDiff("JavaVendor", this.bean.getJavaVendor(), otherTyped.getJavaVendor(), true);
            this.computeDiff("MWHome", this.bean.getMWHome(), otherTyped.getMWHome(), true);
            this.computeDiff("MaxRestartCount", this.bean.getMaxRestartCount(), otherTyped.getMaxRestartCount(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            if (BootstrapProperties.getIncludeObsoletePropsInDiff()) {
               this.computeDiff("OutputFile", this.bean.getOutputFile(), otherTyped.getOutputFile(), true);
            }

            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), true);
            this.computeDiff("RootDirectory", this.bean.getRootDirectory(), otherTyped.getRootDirectory(), true);
            this.computeDiff("SecurityPolicyFile", this.bean.getSecurityPolicyFile(), otherTyped.getSecurityPolicyFile(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Username", this.bean.getUsername(), otherTyped.getUsername(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServerStartMBeanImpl original = (ServerStartMBeanImpl)event.getSourceBean();
            ServerStartMBeanImpl proposed = (ServerStartMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Arguments")) {
                  original.setArguments(proposed.getArguments());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("BeaHome")) {
                  original.setBeaHome(proposed.getBeaHome());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (!prop.equals("BootProperties")) {
                  if (prop.equals("ClassPath")) {
                     original.setClassPath(proposed.getClassPath());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("JavaHome")) {
                     original.setJavaHome(proposed.getJavaHome());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("JavaVendor")) {
                     original.setJavaVendor(proposed.getJavaVendor());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("MWHome")) {
                     original.setMWHome(proposed.getMWHome());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("MaxRestartCount")) {
                     original.setMaxRestartCount(proposed.getMaxRestartCount());
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("OutputFile")) {
                     original.setOutputFile(proposed.getOutputFile());
                     original._conditionalUnset(update.isUnsetUpdate(), 19);
                  } else if (!prop.equals("Password")) {
                     if (prop.equals("PasswordEncrypted")) {
                        original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 22);
                     } else if (prop.equals("RootDirectory")) {
                        original.setRootDirectory(proposed.getRootDirectory());
                        original._conditionalUnset(update.isUnsetUpdate(), 15);
                     } else if (prop.equals("SecurityPolicyFile")) {
                        original.setSecurityPolicyFile(proposed.getSecurityPolicyFile());
                        original._conditionalUnset(update.isUnsetUpdate(), 16);
                     } else if (!prop.equals("StartupProperties")) {
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
                        } else if (prop.equals("Username")) {
                           original.setUsername(proposed.getUsername());
                           original._conditionalUnset(update.isUnsetUpdate(), 20);
                        } else if (!prop.equals("DynamicallyCreated")) {
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
            ServerStartMBeanImpl copy = (ServerStartMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Arguments")) && this.bean.isArgumentsSet()) {
               copy.setArguments(this.bean.getArguments());
            }

            if ((excludeProps == null || !excludeProps.contains("BeaHome")) && this.bean.isBeaHomeSet()) {
               copy.setBeaHome(this.bean.getBeaHome());
            }

            if ((excludeProps == null || !excludeProps.contains("ClassPath")) && this.bean.isClassPathSet()) {
               copy.setClassPath(this.bean.getClassPath());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaHome")) && this.bean.isJavaHomeSet()) {
               copy.setJavaHome(this.bean.getJavaHome());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaVendor")) && this.bean.isJavaVendorSet()) {
               copy.setJavaVendor(this.bean.getJavaVendor());
            }

            if ((excludeProps == null || !excludeProps.contains("MWHome")) && this.bean.isMWHomeSet()) {
               copy.setMWHome(this.bean.getMWHome());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRestartCount")) && this.bean.isMaxRestartCountSet()) {
               copy.setMaxRestartCount(this.bean.getMaxRestartCount());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if (includeObsolete && (excludeProps == null || !excludeProps.contains("OutputFile")) && this.bean.isOutputFileSet()) {
               copy.setOutputFile(this.bean.getOutputFile());
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("RootDirectory")) && this.bean.isRootDirectorySet()) {
               copy.setRootDirectory(this.bean.getRootDirectory());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityPolicyFile")) && this.bean.isSecurityPolicyFileSet()) {
               copy.setSecurityPolicyFile(this.bean.getSecurityPolicyFile());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Username")) && this.bean.isUsernameSet()) {
               copy.setUsername(this.bean.getUsername());
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

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
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.SecureMode;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class SecureModeMBeanImpl extends ConfigurationMBeanImpl implements SecureModeMBean, Serializable {
   private boolean _DynamicallyCreated;
   private String _Name;
   private boolean _RestrictiveJMXPolicies;
   private boolean _SecureModeEnabled;
   private String[] _Tags;
   private boolean _WarnOnAuditing;
   private boolean _WarnOnInsecureApplications;
   private boolean _WarnOnInsecureFileSystem;
   private boolean _WarnOnInsecureSSL;
   private boolean _WarnOnJavaSecurityManager;
   private transient SecureMode _customizer;
   private static SchemaHelper2 _schemaHelper;

   public SecureModeMBeanImpl() {
      try {
         this._customizer = new SecureMode(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public SecureModeMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new SecureMode(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public SecureModeMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new SecureMode(this);
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

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isSecureModeEnabled() {
      return this._customizer.isSecureModeEnabled();
   }

   public boolean isSecureModeEnabledInherited() {
      return false;
   }

   public boolean isSecureModeEnabledSet() {
      return this._isSet(10);
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

   public void setSecureModeEnabled(boolean param0) {
      boolean _oldVal = this.isSecureModeEnabled();
      this._customizer.setSecureModeEnabled(param0);
      this._postSet(10, _oldVal, param0);
   }

   public boolean isRestrictiveJMXPolicies() {
      return this._RestrictiveJMXPolicies;
   }

   public boolean isRestrictiveJMXPoliciesInherited() {
      return false;
   }

   public boolean isRestrictiveJMXPoliciesSet() {
      return this._isSet(11);
   }

   public void setRestrictiveJMXPolicies(boolean param0) {
      boolean _oldVal = this._RestrictiveJMXPolicies;
      this._RestrictiveJMXPolicies = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isWarnOnInsecureSSL() {
      return this._WarnOnInsecureSSL;
   }

   public boolean isWarnOnInsecureSSLInherited() {
      return false;
   }

   public boolean isWarnOnInsecureSSLSet() {
      return this._isSet(12);
   }

   public void setWarnOnInsecureSSL(boolean param0) {
      boolean _oldVal = this._WarnOnInsecureSSL;
      this._WarnOnInsecureSSL = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isWarnOnInsecureFileSystem() {
      return this._WarnOnInsecureFileSystem;
   }

   public boolean isWarnOnInsecureFileSystemInherited() {
      return false;
   }

   public boolean isWarnOnInsecureFileSystemSet() {
      return this._isSet(13);
   }

   public void setWarnOnInsecureFileSystem(boolean param0) {
      boolean _oldVal = this._WarnOnInsecureFileSystem;
      this._WarnOnInsecureFileSystem = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isWarnOnAuditing() {
      return this._WarnOnAuditing;
   }

   public boolean isWarnOnAuditingInherited() {
      return false;
   }

   public boolean isWarnOnAuditingSet() {
      return this._isSet(14);
   }

   public void setWarnOnAuditing(boolean param0) {
      boolean _oldVal = this._WarnOnAuditing;
      this._WarnOnAuditing = param0;
      this._postSet(14, _oldVal, param0);
   }

   public boolean isWarnOnInsecureApplications() {
      return this._WarnOnInsecureApplications;
   }

   public boolean isWarnOnInsecureApplicationsInherited() {
      return false;
   }

   public boolean isWarnOnInsecureApplicationsSet() {
      return this._isSet(15);
   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void setWarnOnInsecureApplications(boolean param0) {
      boolean _oldVal = this._WarnOnInsecureApplications;
      this._WarnOnInsecureApplications = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean isWarnOnJavaSecurityManager() {
      return this._WarnOnJavaSecurityManager;
   }

   public boolean isWarnOnJavaSecurityManagerInherited() {
      return false;
   }

   public boolean isWarnOnJavaSecurityManagerSet() {
      return this._isSet(16);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setWarnOnJavaSecurityManager(boolean param0) {
      boolean _oldVal = this._WarnOnJavaSecurityManager;
      this._WarnOnJavaSecurityManager = param0;
      this._postSet(16, _oldVal, param0);
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

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
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
      SecurityLegalHelper.validateSecureMode(this);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._customizer.setName((String)null);
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
            case 11:
               this._RestrictiveJMXPolicies = true;
               if (initOne) {
                  break;
               }
            case 10:
               this._customizer.setSecureModeEnabled(false);
               if (initOne) {
                  break;
               }
            case 14:
               this._WarnOnAuditing = true;
               if (initOne) {
                  break;
               }
            case 15:
               this._WarnOnInsecureApplications = true;
               if (initOne) {
                  break;
               }
            case 13:
               this._WarnOnInsecureFileSystem = true;
               if (initOne) {
                  break;
               }
            case 12:
               this._WarnOnInsecureSSL = true;
               if (initOne) {
                  break;
               }
            case 16:
               this._WarnOnJavaSecurityManager = true;
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
      return "SecureMode";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("DynamicallyCreated")) {
         oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         String oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("RestrictiveJMXPolicies")) {
         oldVal = this._RestrictiveJMXPolicies;
         this._RestrictiveJMXPolicies = (Boolean)v;
         this._postSet(11, oldVal, this._RestrictiveJMXPolicies);
      } else if (name.equals("SecureModeEnabled")) {
         oldVal = this._SecureModeEnabled;
         this._SecureModeEnabled = (Boolean)v;
         this._postSet(10, oldVal, this._SecureModeEnabled);
      } else if (name.equals("Tags")) {
         String[] oldVal = this._Tags;
         this._Tags = (String[])((String[])v);
         this._postSet(9, oldVal, this._Tags);
      } else if (name.equals("WarnOnAuditing")) {
         oldVal = this._WarnOnAuditing;
         this._WarnOnAuditing = (Boolean)v;
         this._postSet(14, oldVal, this._WarnOnAuditing);
      } else if (name.equals("WarnOnInsecureApplications")) {
         oldVal = this._WarnOnInsecureApplications;
         this._WarnOnInsecureApplications = (Boolean)v;
         this._postSet(15, oldVal, this._WarnOnInsecureApplications);
      } else if (name.equals("WarnOnInsecureFileSystem")) {
         oldVal = this._WarnOnInsecureFileSystem;
         this._WarnOnInsecureFileSystem = (Boolean)v;
         this._postSet(13, oldVal, this._WarnOnInsecureFileSystem);
      } else if (name.equals("WarnOnInsecureSSL")) {
         oldVal = this._WarnOnInsecureSSL;
         this._WarnOnInsecureSSL = (Boolean)v;
         this._postSet(12, oldVal, this._WarnOnInsecureSSL);
      } else if (name.equals("WarnOnJavaSecurityManager")) {
         oldVal = this._WarnOnJavaSecurityManager;
         this._WarnOnJavaSecurityManager = (Boolean)v;
         this._postSet(16, oldVal, this._WarnOnJavaSecurityManager);
      } else if (name.equals("customizer")) {
         SecureMode oldVal = this._customizer;
         this._customizer = (SecureMode)v;
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("RestrictiveJMXPolicies")) {
         return new Boolean(this._RestrictiveJMXPolicies);
      } else if (name.equals("SecureModeEnabled")) {
         return new Boolean(this._SecureModeEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("WarnOnAuditing")) {
         return new Boolean(this._WarnOnAuditing);
      } else if (name.equals("WarnOnInsecureApplications")) {
         return new Boolean(this._WarnOnInsecureApplications);
      } else if (name.equals("WarnOnInsecureFileSystem")) {
         return new Boolean(this._WarnOnInsecureFileSystem);
      } else if (name.equals("WarnOnInsecureSSL")) {
         return new Boolean(this._WarnOnInsecureSSL);
      } else if (name.equals("WarnOnJavaSecurityManager")) {
         return new Boolean(this._WarnOnJavaSecurityManager);
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
               break;
            case 16:
               if (s.equals("warn-on-auditing")) {
                  return 14;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }

               if (s.equals("secure-mode-enabled")) {
                  return 10;
               }

               if (s.equals("warn-on-insecuressl")) {
                  return 12;
               }
               break;
            case 23:
               if (s.equals("restrictivejmx-policies")) {
                  return 11;
               }
               break;
            case 28:
               if (s.equals("warn-on-insecure-file-system")) {
                  return 13;
               }
               break;
            case 29:
               if (s.equals("warn-on-insecure-applications")) {
                  return 15;
               }

               if (s.equals("warn-on-java-security-manager")) {
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
               return "secure-mode-enabled";
            case 11:
               return "restrictivejmx-policies";
            case 12:
               return "warn-on-insecuressl";
            case 13:
               return "warn-on-insecure-file-system";
            case 14:
               return "warn-on-auditing";
            case 15:
               return "warn-on-insecure-applications";
            case 16:
               return "warn-on-java-security-manager";
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
      private SecureModeMBeanImpl bean;

      protected Helper(SecureModeMBeanImpl bean) {
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
               return "SecureModeEnabled";
            case 11:
               return "RestrictiveJMXPolicies";
            case 12:
               return "WarnOnInsecureSSL";
            case 13:
               return "WarnOnInsecureFileSystem";
            case 14:
               return "WarnOnAuditing";
            case 15:
               return "WarnOnInsecureApplications";
            case 16:
               return "WarnOnJavaSecurityManager";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else if (propName.equals("RestrictiveJMXPolicies")) {
            return 11;
         } else if (propName.equals("SecureModeEnabled")) {
            return 10;
         } else if (propName.equals("WarnOnAuditing")) {
            return 14;
         } else if (propName.equals("WarnOnInsecureApplications")) {
            return 15;
         } else if (propName.equals("WarnOnInsecureFileSystem")) {
            return 13;
         } else if (propName.equals("WarnOnInsecureSSL")) {
            return 12;
         } else {
            return propName.equals("WarnOnJavaSecurityManager") ? 16 : super.getPropertyIndex(propName);
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
            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isRestrictiveJMXPoliciesSet()) {
               buf.append("RestrictiveJMXPolicies");
               buf.append(String.valueOf(this.bean.isRestrictiveJMXPolicies()));
            }

            if (this.bean.isSecureModeEnabledSet()) {
               buf.append("SecureModeEnabled");
               buf.append(String.valueOf(this.bean.isSecureModeEnabled()));
            }

            if (this.bean.isWarnOnAuditingSet()) {
               buf.append("WarnOnAuditing");
               buf.append(String.valueOf(this.bean.isWarnOnAuditing()));
            }

            if (this.bean.isWarnOnInsecureApplicationsSet()) {
               buf.append("WarnOnInsecureApplications");
               buf.append(String.valueOf(this.bean.isWarnOnInsecureApplications()));
            }

            if (this.bean.isWarnOnInsecureFileSystemSet()) {
               buf.append("WarnOnInsecureFileSystem");
               buf.append(String.valueOf(this.bean.isWarnOnInsecureFileSystem()));
            }

            if (this.bean.isWarnOnInsecureSSLSet()) {
               buf.append("WarnOnInsecureSSL");
               buf.append(String.valueOf(this.bean.isWarnOnInsecureSSL()));
            }

            if (this.bean.isWarnOnJavaSecurityManagerSet()) {
               buf.append("WarnOnJavaSecurityManager");
               buf.append(String.valueOf(this.bean.isWarnOnJavaSecurityManager()));
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
            SecureModeMBeanImpl otherTyped = (SecureModeMBeanImpl)other;
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("RestrictiveJMXPolicies", this.bean.isRestrictiveJMXPolicies(), otherTyped.isRestrictiveJMXPolicies(), true);
            this.computeDiff("SecureModeEnabled", this.bean.isSecureModeEnabled(), otherTyped.isSecureModeEnabled(), false);
            this.computeDiff("WarnOnAuditing", this.bean.isWarnOnAuditing(), otherTyped.isWarnOnAuditing(), false);
            this.computeDiff("WarnOnInsecureApplications", this.bean.isWarnOnInsecureApplications(), otherTyped.isWarnOnInsecureApplications(), false);
            this.computeDiff("WarnOnInsecureFileSystem", this.bean.isWarnOnInsecureFileSystem(), otherTyped.isWarnOnInsecureFileSystem(), false);
            this.computeDiff("WarnOnInsecureSSL", this.bean.isWarnOnInsecureSSL(), otherTyped.isWarnOnInsecureSSL(), false);
            this.computeDiff("WarnOnJavaSecurityManager", this.bean.isWarnOnJavaSecurityManager(), otherTyped.isWarnOnJavaSecurityManager(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SecureModeMBeanImpl original = (SecureModeMBeanImpl)event.getSourceBean();
            SecureModeMBeanImpl proposed = (SecureModeMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
                  if (prop.equals("RestrictiveJMXPolicies")) {
                     original.setRestrictiveJMXPolicies(proposed.isRestrictiveJMXPolicies());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("SecureModeEnabled")) {
                     original.setSecureModeEnabled(proposed.isSecureModeEnabled());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("WarnOnAuditing")) {
                     original.setWarnOnAuditing(proposed.isWarnOnAuditing());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("WarnOnInsecureApplications")) {
                     original.setWarnOnInsecureApplications(proposed.isWarnOnInsecureApplications());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("WarnOnInsecureFileSystem")) {
                     original.setWarnOnInsecureFileSystem(proposed.isWarnOnInsecureFileSystem());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("WarnOnInsecureSSL")) {
                     original.setWarnOnInsecureSSL(proposed.isWarnOnInsecureSSL());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("WarnOnJavaSecurityManager")) {
                     original.setWarnOnJavaSecurityManager(proposed.isWarnOnJavaSecurityManager());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else {
                     super.applyPropertyUpdate(event, update);
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
            SecureModeMBeanImpl copy = (SecureModeMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("RestrictiveJMXPolicies")) && this.bean.isRestrictiveJMXPoliciesSet()) {
               copy.setRestrictiveJMXPolicies(this.bean.isRestrictiveJMXPolicies());
            }

            if ((excludeProps == null || !excludeProps.contains("SecureModeEnabled")) && this.bean.isSecureModeEnabledSet()) {
               copy.setSecureModeEnabled(this.bean.isSecureModeEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("WarnOnAuditing")) && this.bean.isWarnOnAuditingSet()) {
               copy.setWarnOnAuditing(this.bean.isWarnOnAuditing());
            }

            if ((excludeProps == null || !excludeProps.contains("WarnOnInsecureApplications")) && this.bean.isWarnOnInsecureApplicationsSet()) {
               copy.setWarnOnInsecureApplications(this.bean.isWarnOnInsecureApplications());
            }

            if ((excludeProps == null || !excludeProps.contains("WarnOnInsecureFileSystem")) && this.bean.isWarnOnInsecureFileSystemSet()) {
               copy.setWarnOnInsecureFileSystem(this.bean.isWarnOnInsecureFileSystem());
            }

            if ((excludeProps == null || !excludeProps.contains("WarnOnInsecureSSL")) && this.bean.isWarnOnInsecureSSLSet()) {
               copy.setWarnOnInsecureSSL(this.bean.isWarnOnInsecureSSL());
            }

            if ((excludeProps == null || !excludeProps.contains("WarnOnJavaSecurityManager")) && this.bean.isWarnOnJavaSecurityManagerSet()) {
               copy.setWarnOnJavaSecurityManager(this.bean.isWarnOnJavaSecurityManager());
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

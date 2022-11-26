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
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.beangen.StringHelper;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class MailSessionMBeanImpl extends RMCFactoryMBeanImpl implements MailSessionMBean, Serializable {
   private String _JNDIName;
   private String _Name;
   private Properties _Properties;
   private String _SessionPassword;
   private byte[] _SessionPasswordEncrypted;
   private String _SessionUsername;
   private TargetMBean[] _Targets;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private MailSessionMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(MailSessionMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(MailSessionMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public MailSessionMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(MailSessionMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      MailSessionMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public MailSessionMBeanImpl() {
      this._initializeProperty(-1);
   }

   public MailSessionMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MailSessionMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getJNDIName() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getJNDIName(), this) : this._JNDIName;
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

         return this._Name;
      }
   }

   public TargetMBean[] getTargets() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getTargets() : this._Targets;
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isJNDINameInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isJNDINameSet() {
      return this._isSet(12);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTargetsInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public void setTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Targets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        MailSessionMBeanImpl.this.addTarget((TargetMBean)value);
                        MailSessionMBeanImpl.this._getHelper().reorderArrayObjects((Object[])MailSessionMBeanImpl.this._Targets, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               TargetMBean[] var6 = this._Targets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTarget(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         TargetMBean[] _oldVal = this._Targets;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Targets);
      }
   }

   public Properties getProperties() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getProperties() : this._Properties;
   }

   public String getPropertiesAsString() {
      return StringHelper.objectToString(this.getProperties());
   }

   public boolean isPropertiesInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isPropertiesSet() {
      return this._isSet(13);
   }

   public void setJNDIName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._JNDIName;
      this._JNDIName = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MailSessionMBeanImpl source = (MailSessionMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
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
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MailSessionMBeanImpl source = (MailSessionMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setPropertiesAsString(String param0) {
      try {
         this.setProperties(StringHelper.stringToProperties(param0));
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return MailSessionMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(10);
      TargetMBean[] _oldVal = this._Targets;
      this._Targets = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MailSessionMBeanImpl source = (MailSessionMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         TargetMBean[] _new;
         if (this._isSet(10)) {
            _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));
         } else {
            _new = new TargetMBean[]{param0};
         }

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public void setProperties(Properties param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      Properties _oldVal = this._Properties;
      this._Properties = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MailSessionMBeanImpl source = (MailSessionMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSessionUsername() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getSessionUsername(), this) : this._SessionUsername;
   }

   public boolean isSessionUsernameInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isSessionUsernameSet() {
      return this._isSet(14);
   }

   public boolean removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setSessionUsername(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      String _oldVal = this._SessionUsername;
      this._SessionUsername = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         MailSessionMBeanImpl source = (MailSessionMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSessionPassword() {
      if (!this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15)) {
         return this._performMacroSubstitution(this._getDelegateBean().getSessionPassword(), this);
      } else {
         byte[] bEncrypted = this.getSessionPasswordEncrypted();
         return bEncrypted == null ? null : this._decrypt("SessionPassword", bEncrypted);
      }
   }

   public boolean isSessionPasswordInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isSessionPasswordSet() {
      return this.isSessionPasswordEncryptedSet();
   }

   public void setSessionPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setSessionPasswordEncrypted(param0 == null ? null : this._encrypt("SessionPassword", param0));
   }

   public byte[] getSessionPasswordEncrypted() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._getDelegateBean().getSessionPasswordEncrypted() : this._getHelper()._cloneArray(this._SessionPasswordEncrypted);
   }

   public String getSessionPasswordEncryptedAsString() {
      byte[] obj = this.getSessionPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isSessionPasswordEncryptedInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isSessionPasswordEncryptedSet() {
      return this._isSet(16);
   }

   public void setSessionPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setSessionPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      MailSessionValidator.validateUniqueJNDIName(this, this.getJNDIName());
   }

   public void setSessionPasswordEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      byte[] _oldVal = this._SessionPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: SessionPasswordEncrypted of MailSessionMBean");
      } else {
         this._getHelper()._clearArray(this._SessionPasswordEncrypted);
         this._SessionPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(16, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            MailSessionMBeanImpl source = (MailSessionMBeanImpl)var4.next();
            if (source != null && !source._isSet(16)) {
               source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
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
         if (idx == 15) {
            this._markSet(16, false);
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._JNDIName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._Properties = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._SessionPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._SessionPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._SessionUsername = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Targets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
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
      return "MailSession";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("JNDIName")) {
         oldVal = this._JNDIName;
         this._JNDIName = (String)v;
         this._postSet(12, oldVal, this._JNDIName);
      } else if (name.equals("Name")) {
         oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("Properties")) {
         Properties oldVal = this._Properties;
         this._Properties = (Properties)v;
         this._postSet(13, oldVal, this._Properties);
      } else if (name.equals("SessionPassword")) {
         oldVal = this._SessionPassword;
         this._SessionPassword = (String)v;
         this._postSet(15, oldVal, this._SessionPassword);
      } else if (name.equals("SessionPasswordEncrypted")) {
         byte[] oldVal = this._SessionPasswordEncrypted;
         this._SessionPasswordEncrypted = (byte[])((byte[])v);
         this._postSet(16, oldVal, this._SessionPasswordEncrypted);
      } else if (name.equals("SessionUsername")) {
         oldVal = this._SessionUsername;
         this._SessionUsername = (String)v;
         this._postSet(14, oldVal, this._SessionUsername);
      } else if (name.equals("Targets")) {
         TargetMBean[] oldVal = this._Targets;
         this._Targets = (TargetMBean[])((TargetMBean[])v);
         this._postSet(10, oldVal, this._Targets);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("JNDIName")) {
         return this._JNDIName;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Properties")) {
         return this._Properties;
      } else if (name.equals("SessionPassword")) {
         return this._SessionPassword;
      } else if (name.equals("SessionPasswordEncrypted")) {
         return this._SessionPasswordEncrypted;
      } else if (name.equals("SessionUsername")) {
         return this._SessionUsername;
      } else {
         return name.equals("Targets") ? this._Targets : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends RMCFactoryMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
               break;
            case 9:
               if (s.equals("jndi-name")) {
                  return 12;
               }
               break;
            case 10:
               if (s.equals("properties")) {
                  return 13;
               }
               break;
            case 16:
               if (s.equals("session-password")) {
                  return 15;
               }

               if (s.equals("session-username")) {
                  return 14;
               }
               break;
            case 26:
               if (s.equals("session-password-encrypted")) {
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
            case 7:
            case 8:
            case 9:
            case 11:
            default:
               return super.getElementName(propIndex);
            case 10:
               return "target";
            case 12:
               return "jndi-name";
            case 13:
               return "properties";
            case 14:
               return "session-username";
            case 15:
               return "session-password";
            case 16:
               return "session-password-encrypted";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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

   protected static class Helper extends RMCFactoryMBeanImpl.Helper {
      private MailSessionMBeanImpl bean;

      protected Helper(MailSessionMBeanImpl bean) {
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
            case 7:
            case 8:
            case 9:
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 10:
               return "Targets";
            case 12:
               return "JNDIName";
            case 13:
               return "Properties";
            case 14:
               return "SessionUsername";
            case 15:
               return "SessionPassword";
            case 16:
               return "SessionPasswordEncrypted";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("JNDIName")) {
            return 12;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Properties")) {
            return 13;
         } else if (propName.equals("SessionPassword")) {
            return 15;
         } else if (propName.equals("SessionPasswordEncrypted")) {
            return 16;
         } else if (propName.equals("SessionUsername")) {
            return 14;
         } else {
            return propName.equals("Targets") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isJNDINameSet()) {
               buf.append("JNDIName");
               buf.append(String.valueOf(this.bean.getJNDIName()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPropertiesSet()) {
               buf.append("Properties");
               buf.append(String.valueOf(this.bean.getProperties()));
            }

            if (this.bean.isSessionPasswordSet()) {
               buf.append("SessionPassword");
               buf.append(String.valueOf(this.bean.getSessionPassword()));
            }

            if (this.bean.isSessionPasswordEncryptedSet()) {
               buf.append("SessionPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSessionPasswordEncrypted())));
            }

            if (this.bean.isSessionUsernameSet()) {
               buf.append("SessionUsername");
               buf.append(String.valueOf(this.bean.getSessionUsername()));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
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
            MailSessionMBeanImpl otherTyped = (MailSessionMBeanImpl)other;
            this.computeDiff("JNDIName", this.bean.getJNDIName(), otherTyped.getJNDIName(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), false);
            this.computeDiff("SessionPasswordEncrypted", this.bean.getSessionPasswordEncrypted(), otherTyped.getSessionPasswordEncrypted(), false);
            this.computeDiff("SessionUsername", this.bean.getSessionUsername(), otherTyped.getSessionUsername(), false);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MailSessionMBeanImpl original = (MailSessionMBeanImpl)event.getSourceBean();
            MailSessionMBeanImpl proposed = (MailSessionMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("JNDIName")) {
                  original.setJNDIName(proposed.getJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Properties")) {
                  original.setProperties(proposed.getProperties() == null ? null : (Properties)proposed.getProperties().clone());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (!prop.equals("SessionPassword")) {
                  if (prop.equals("SessionPasswordEncrypted")) {
                     original.setSessionPasswordEncrypted(proposed.getSessionPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
                  } else if (prop.equals("SessionUsername")) {
                     original.setSessionUsername(proposed.getSessionUsername());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("Targets")) {
                     original.setTargetsAsString(proposed.getTargetsAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            MailSessionMBeanImpl copy = (MailSessionMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("JNDIName")) && this.bean.isJNDINameSet()) {
               copy.setJNDIName(this.bean.getJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet()) {
               copy.setProperties(this.bean.getProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("SessionPasswordEncrypted")) && this.bean.isSessionPasswordEncryptedSet()) {
               Object o = this.bean.getSessionPasswordEncrypted();
               copy.setSessionPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("SessionUsername")) && this.bean.isSessionUsernameSet()) {
               copy.setSessionUsername(this.bean.getSessionUsername());
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
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
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}

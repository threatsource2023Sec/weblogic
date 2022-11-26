package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceManagementClusterMBeanImpl extends ConfigurationMBeanImpl implements CoherenceManagementClusterMBean, Serializable {
   private CoherenceManagementAddressProviderMBean[] _CoherenceManagementAddressProviders;
   private byte[] _EncryptedPassword;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private String _ReportGroupFile;
   private String _Username;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceManagementClusterMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceManagementClusterMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceManagementClusterMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getUsername() {
      return this._Username;
   }

   public boolean isUsernameInherited() {
      return false;
   }

   public boolean isUsernameSet() {
      return this._isSet(10);
   }

   public void setUsername(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Username;
      this._Username = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getPassword() {
      byte[] bEncrypted = this.getPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("Password", bEncrypted);
   }

   public boolean isPasswordInherited() {
      return false;
   }

   public boolean isPasswordSet() {
      return this.isPasswordEncryptedSet();
   }

   public void setPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this.setPasswordEncrypted(param0 == null ? null : this._encrypt("Password", param0));
   }

   public byte[] getPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._PasswordEncrypted);
   }

   public String getPasswordEncryptedAsString() {
      byte[] obj = this.getPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isPasswordEncryptedInherited() {
      return false;
   }

   public boolean isPasswordEncryptedSet() {
      return this._isSet(12);
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

   public byte[] getEncryptedPassword() {
      return this._EncryptedPassword;
   }

   public boolean isEncryptedPasswordInherited() {
      return false;
   }

   public boolean isEncryptedPasswordSet() {
      return this._isSet(13);
   }

   public void setEncryptedPassword(byte[] param0) throws InvalidAttributeValueException {
      byte[] _oldVal = this._EncryptedPassword;
      this._EncryptedPassword = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getReportGroupFile() {
      return this._ReportGroupFile;
   }

   public boolean isReportGroupFileInherited() {
      return false;
   }

   public boolean isReportGroupFileSet() {
      return this._isSet(14);
   }

   public void setReportGroupFile(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ReportGroupFile;
      this._ReportGroupFile = param0;
      this._postSet(14, _oldVal, param0);
   }

   public void addCoherenceManagementAddressProvider(CoherenceManagementAddressProviderMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         CoherenceManagementAddressProviderMBean[] _new;
         if (this._isSet(15)) {
            _new = (CoherenceManagementAddressProviderMBean[])((CoherenceManagementAddressProviderMBean[])this._getHelper()._extendArray(this.getCoherenceManagementAddressProviders(), CoherenceManagementAddressProviderMBean.class, param0));
         } else {
            _new = new CoherenceManagementAddressProviderMBean[]{param0};
         }

         try {
            this.setCoherenceManagementAddressProviders(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceManagementAddressProviderMBean[] getCoherenceManagementAddressProviders() {
      return this._CoherenceManagementAddressProviders;
   }

   public boolean isCoherenceManagementAddressProvidersInherited() {
      return false;
   }

   public boolean isCoherenceManagementAddressProvidersSet() {
      return this._isSet(15);
   }

   public void removeCoherenceManagementAddressProvider(CoherenceManagementAddressProviderMBean param0) {
      this.destroyCoherenceManagementAddressProvider(param0);
   }

   public void setCoherenceManagementAddressProviders(CoherenceManagementAddressProviderMBean[] param0) throws InvalidAttributeValueException {
      CoherenceManagementAddressProviderMBean[] param0 = param0 == null ? new CoherenceManagementAddressProviderMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      CoherenceManagementAddressProviderMBean[] _oldVal = this._CoherenceManagementAddressProviders;
      this._CoherenceManagementAddressProviders = (CoherenceManagementAddressProviderMBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public CoherenceManagementAddressProviderMBean createCoherenceManagementAddressProvider(String param0) {
      CoherenceManagementAddressProviderMBeanImpl lookup = (CoherenceManagementAddressProviderMBeanImpl)this.lookupCoherenceManagementAddressProvider(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceManagementAddressProviderMBeanImpl _val = new CoherenceManagementAddressProviderMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceManagementAddressProvider(_val);
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

   public CoherenceManagementAddressProviderMBean lookupCoherenceManagementAddressProvider(String param0) {
      Object[] aary = (Object[])this._CoherenceManagementAddressProviders;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceManagementAddressProviderMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceManagementAddressProviderMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyCoherenceManagementAddressProvider(CoherenceManagementAddressProviderMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         CoherenceManagementAddressProviderMBean[] _old = this.getCoherenceManagementAddressProviders();
         CoherenceManagementAddressProviderMBean[] _new = (CoherenceManagementAddressProviderMBean[])((CoherenceManagementAddressProviderMBean[])this._getHelper()._removeElement(_old, CoherenceManagementAddressProviderMBean.class, param0));
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
               this.setCoherenceManagementAddressProviders(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._PasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of CoherenceManagementClusterMBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(12, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 11) {
            this._markSet(12, false);
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
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._CoherenceManagementAddressProviders = new CoherenceManagementAddressProviderMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._EncryptedPassword = new byte[0];
               if (initOne) {
                  break;
               }
            case 11:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._ReportGroupFile = "em/metadata/reports/coherence/report-group.xml";
               if (initOne) {
                  break;
               }
            case 10:
               this._Username = null;
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
      return "CoherenceManagementCluster";
   }

   public void putValue(String name, Object v) {
      if (name.equals("CoherenceManagementAddressProviders")) {
         CoherenceManagementAddressProviderMBean[] oldVal = this._CoherenceManagementAddressProviders;
         this._CoherenceManagementAddressProviders = (CoherenceManagementAddressProviderMBean[])((CoherenceManagementAddressProviderMBean[])v);
         this._postSet(15, oldVal, this._CoherenceManagementAddressProviders);
      } else {
         byte[] oldVal;
         if (name.equals("EncryptedPassword")) {
            oldVal = this._EncryptedPassword;
            this._EncryptedPassword = (byte[])((byte[])v);
            this._postSet(13, oldVal, this._EncryptedPassword);
         } else {
            String oldVal;
            if (name.equals("Password")) {
               oldVal = this._Password;
               this._Password = (String)v;
               this._postSet(11, oldVal, this._Password);
            } else if (name.equals("PasswordEncrypted")) {
               oldVal = this._PasswordEncrypted;
               this._PasswordEncrypted = (byte[])((byte[])v);
               this._postSet(12, oldVal, this._PasswordEncrypted);
            } else if (name.equals("ReportGroupFile")) {
               oldVal = this._ReportGroupFile;
               this._ReportGroupFile = (String)v;
               this._postSet(14, oldVal, this._ReportGroupFile);
            } else if (name.equals("Username")) {
               oldVal = this._Username;
               this._Username = (String)v;
               this._postSet(10, oldVal, this._Username);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CoherenceManagementAddressProviders")) {
         return this._CoherenceManagementAddressProviders;
      } else if (name.equals("EncryptedPassword")) {
         return this._EncryptedPassword;
      } else if (name.equals("Password")) {
         return this._Password;
      } else if (name.equals("PasswordEncrypted")) {
         return this._PasswordEncrypted;
      } else if (name.equals("ReportGroupFile")) {
         return this._ReportGroupFile;
      } else {
         return name.equals("Username") ? this._Username : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("password")) {
                  return 11;
               }

               if (s.equals("username")) {
                  return 10;
               }
               break;
            case 17:
               if (s.equals("report-group-file")) {
                  return 14;
               }
               break;
            case 18:
               if (s.equals("encrypted-password")) {
                  return 13;
               }

               if (s.equals("password-encrypted")) {
                  return 12;
               }
               break;
            case 37:
               if (s.equals("coherence-management-address-provider")) {
                  return 15;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 15:
               return new CoherenceManagementAddressProviderMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "username";
            case 11:
               return "password";
            case 12:
               return "password-encrypted";
            case 13:
               return "encrypted-password";
            case 14:
               return "report-group-file";
            case 15:
               return "coherence-management-address-provider";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 13:
               return true;
            case 15:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 15:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private CoherenceManagementClusterMBeanImpl bean;

      protected Helper(CoherenceManagementClusterMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Username";
            case 11:
               return "Password";
            case 12:
               return "PasswordEncrypted";
            case 13:
               return "EncryptedPassword";
            case 14:
               return "ReportGroupFile";
            case 15:
               return "CoherenceManagementAddressProviders";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CoherenceManagementAddressProviders")) {
            return 15;
         } else if (propName.equals("EncryptedPassword")) {
            return 13;
         } else if (propName.equals("Password")) {
            return 11;
         } else if (propName.equals("PasswordEncrypted")) {
            return 12;
         } else if (propName.equals("ReportGroupFile")) {
            return 14;
         } else {
            return propName.equals("Username") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCoherenceManagementAddressProviders()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getCoherenceManagementAddressProviders().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceManagementAddressProviders()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEncryptedPasswordSet()) {
               buf.append("EncryptedPassword");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getEncryptedPassword())));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isReportGroupFileSet()) {
               buf.append("ReportGroupFile");
               buf.append(String.valueOf(this.bean.getReportGroupFile()));
            }

            if (this.bean.isUsernameSet()) {
               buf.append("Username");
               buf.append(String.valueOf(this.bean.getUsername()));
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
            CoherenceManagementClusterMBeanImpl otherTyped = (CoherenceManagementClusterMBeanImpl)other;
            this.computeChildDiff("CoherenceManagementAddressProviders", this.bean.getCoherenceManagementAddressProviders(), otherTyped.getCoherenceManagementAddressProviders(), false);
            this.computeDiff("EncryptedPassword", this.bean.getEncryptedPassword(), otherTyped.getEncryptedPassword(), false);
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), false);
            this.computeDiff("ReportGroupFile", this.bean.getReportGroupFile(), otherTyped.getReportGroupFile(), false);
            this.computeDiff("Username", this.bean.getUsername(), otherTyped.getUsername(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceManagementClusterMBeanImpl original = (CoherenceManagementClusterMBeanImpl)event.getSourceBean();
            CoherenceManagementClusterMBeanImpl proposed = (CoherenceManagementClusterMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CoherenceManagementAddressProviders")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceManagementAddressProvider((CoherenceManagementAddressProviderMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherenceManagementAddressProvider((CoherenceManagementAddressProviderMBean)update.getRemovedObject());
                  }

                  if (original.getCoherenceManagementAddressProviders() == null || original.getCoherenceManagementAddressProviders().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("EncryptedPassword")) {
                  original.setEncryptedPassword(proposed.getEncryptedPassword());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (!prop.equals("Password")) {
                  if (prop.equals("PasswordEncrypted")) {
                     original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("ReportGroupFile")) {
                     original.setReportGroupFile(proposed.getReportGroupFile());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("Username")) {
                     original.setUsername(proposed.getUsername());
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
            CoherenceManagementClusterMBeanImpl copy = (CoherenceManagementClusterMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CoherenceManagementAddressProviders")) && this.bean.isCoherenceManagementAddressProvidersSet() && !copy._isSet(15)) {
               CoherenceManagementAddressProviderMBean[] oldCoherenceManagementAddressProviders = this.bean.getCoherenceManagementAddressProviders();
               CoherenceManagementAddressProviderMBean[] newCoherenceManagementAddressProviders = new CoherenceManagementAddressProviderMBean[oldCoherenceManagementAddressProviders.length];

               for(int i = 0; i < newCoherenceManagementAddressProviders.length; ++i) {
                  newCoherenceManagementAddressProviders[i] = (CoherenceManagementAddressProviderMBean)((CoherenceManagementAddressProviderMBean)this.createCopy((AbstractDescriptorBean)oldCoherenceManagementAddressProviders[i], includeObsolete));
               }

               copy.setCoherenceManagementAddressProviders(newCoherenceManagementAddressProviders);
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("EncryptedPassword")) && this.bean.isEncryptedPasswordSet()) {
               o = this.bean.getEncryptedPassword();
               copy.setEncryptedPassword(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ReportGroupFile")) && this.bean.isReportGroupFileSet()) {
               copy.setReportGroupFile(this.bean.getReportGroupFile());
            }

            if ((excludeProps == null || !excludeProps.contains("Username")) && this.bean.isUsernameSet()) {
               copy.setUsername(this.bean.getUsername());
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
         this.inferSubTree(this.bean.getCoherenceManagementAddressProviders(), clazz, annotation);
      }
   }
}

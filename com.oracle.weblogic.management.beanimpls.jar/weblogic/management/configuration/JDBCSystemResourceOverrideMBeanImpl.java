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

public class JDBCSystemResourceOverrideMBeanImpl extends ConfigurationMBeanImpl implements JDBCSystemResourceOverrideMBean, Serializable {
   private String _DataSourceName;
   private int _InitialCapacity;
   private JDBCPropertyOverrideMBean[] _JDBCPropertyOverrides;
   private int _MaxCapacity;
   private int _MinCapacity;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private String _URL;
   private String _User;
   private static SchemaHelper2 _schemaHelper;

   public JDBCSystemResourceOverrideMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JDBCSystemResourceOverrideMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JDBCSystemResourceOverrideMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDataSourceName() {
      if (!this._isSet(10)) {
         try {
            return this.getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._DataSourceName;
   }

   public boolean isDataSourceNameInherited() {
      return false;
   }

   public boolean isDataSourceNameSet() {
      return this._isSet(10);
   }

   public void setDataSourceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DataSourceName;
      this._DataSourceName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getURL() {
      return this._URL;
   }

   public boolean isURLInherited() {
      return false;
   }

   public boolean isURLSet() {
      return this._isSet(11);
   }

   public void setURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._URL;
      this._URL = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getUser() {
      return this._User;
   }

   public boolean isUserInherited() {
      return false;
   }

   public boolean isUserSet() {
      return this._isSet(12);
   }

   public void setUser(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._User;
      this._User = param0;
      this._postSet(12, _oldVal, param0);
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
      return this._isSet(14);
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

   public int getInitialCapacity() {
      return this._InitialCapacity;
   }

   public boolean isInitialCapacityInherited() {
      return false;
   }

   public boolean isInitialCapacitySet() {
      return this._isSet(15);
   }

   public void setInitialCapacity(int param0) {
      int _oldVal = this._InitialCapacity;
      this._InitialCapacity = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getMinCapacity() {
      return this._MinCapacity;
   }

   public boolean isMinCapacityInherited() {
      return false;
   }

   public boolean isMinCapacitySet() {
      return this._isSet(16);
   }

   public void setMinCapacity(int param0) {
      int _oldVal = this._MinCapacity;
      this._MinCapacity = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getMaxCapacity() {
      return this._MaxCapacity;
   }

   public boolean isMaxCapacityInherited() {
      return false;
   }

   public boolean isMaxCapacitySet() {
      return this._isSet(17);
   }

   public void setMaxCapacity(int param0) {
      int _oldVal = this._MaxCapacity;
      this._MaxCapacity = param0;
      this._postSet(17, _oldVal, param0);
   }

   public void addJDBCPropertyOverride(JDBCPropertyOverrideMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 18)) {
         JDBCPropertyOverrideMBean[] _new;
         if (this._isSet(18)) {
            _new = (JDBCPropertyOverrideMBean[])((JDBCPropertyOverrideMBean[])this._getHelper()._extendArray(this.getJDBCPropertyOverrides(), JDBCPropertyOverrideMBean.class, param0));
         } else {
            _new = new JDBCPropertyOverrideMBean[]{param0};
         }

         try {
            this.setJDBCPropertyOverrides(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JDBCPropertyOverrideMBean[] getJDBCPropertyOverrides() {
      return this._JDBCPropertyOverrides;
   }

   public boolean isJDBCPropertyOverridesInherited() {
      return false;
   }

   public boolean isJDBCPropertyOverridesSet() {
      return this._isSet(18);
   }

   public void removeJDBCPropertyOverride(JDBCPropertyOverrideMBean param0) {
      this.destroyJDBCPropertyOverride(param0);
   }

   public void setJDBCPropertyOverrides(JDBCPropertyOverrideMBean[] param0) throws InvalidAttributeValueException {
      JDBCPropertyOverrideMBean[] param0 = param0 == null ? new JDBCPropertyOverrideMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 18)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JDBCPropertyOverrideMBean[] _oldVal = this._JDBCPropertyOverrides;
      this._JDBCPropertyOverrides = (JDBCPropertyOverrideMBean[])param0;
      this._postSet(18, _oldVal, param0);
   }

   public JDBCPropertyOverrideMBean lookupJDBCPropertyOverride(String param0) {
      Object[] aary = (Object[])this._JDBCPropertyOverrides;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JDBCPropertyOverrideMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JDBCPropertyOverrideMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JDBCPropertyOverrideMBean createJDBCPropertyOverride(String param0) {
      JDBCPropertyOverrideMBeanImpl lookup = (JDBCPropertyOverrideMBeanImpl)this.lookupJDBCPropertyOverride(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         JDBCPropertyOverrideMBeanImpl _val = new JDBCPropertyOverrideMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addJDBCPropertyOverride(_val);
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

   public void destroyJDBCPropertyOverride(JDBCPropertyOverrideMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 18);
         JDBCPropertyOverrideMBean[] _old = this.getJDBCPropertyOverrides();
         JDBCPropertyOverrideMBean[] _new = (JDBCPropertyOverrideMBean[])((JDBCPropertyOverrideMBean[])this._getHelper()._removeElement(_old, JDBCPropertyOverrideMBean.class, param0));
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
               this.setJDBCPropertyOverrides(_new);
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
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of JDBCSystemResourceOverrideMBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(14, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 13) {
            this._markSet(14, false);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._DataSourceName = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._InitialCapacity = -1;
               if (initOne) {
                  break;
               }
            case 18:
               this._JDBCPropertyOverrides = new JDBCPropertyOverrideMBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._MaxCapacity = -1;
               if (initOne) {
                  break;
               }
            case 16:
               this._MinCapacity = -1;
               if (initOne) {
                  break;
               }
            case 13:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._URL = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._User = null;
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
      return "JDBCSystemResourceOverride";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("DataSourceName")) {
         oldVal = this._DataSourceName;
         this._DataSourceName = (String)v;
         this._postSet(10, oldVal, this._DataSourceName);
      } else {
         int oldVal;
         if (name.equals("InitialCapacity")) {
            oldVal = this._InitialCapacity;
            this._InitialCapacity = (Integer)v;
            this._postSet(15, oldVal, this._InitialCapacity);
         } else if (name.equals("JDBCPropertyOverrides")) {
            JDBCPropertyOverrideMBean[] oldVal = this._JDBCPropertyOverrides;
            this._JDBCPropertyOverrides = (JDBCPropertyOverrideMBean[])((JDBCPropertyOverrideMBean[])v);
            this._postSet(18, oldVal, this._JDBCPropertyOverrides);
         } else if (name.equals("MaxCapacity")) {
            oldVal = this._MaxCapacity;
            this._MaxCapacity = (Integer)v;
            this._postSet(17, oldVal, this._MaxCapacity);
         } else if (name.equals("MinCapacity")) {
            oldVal = this._MinCapacity;
            this._MinCapacity = (Integer)v;
            this._postSet(16, oldVal, this._MinCapacity);
         } else if (name.equals("Password")) {
            oldVal = this._Password;
            this._Password = (String)v;
            this._postSet(13, oldVal, this._Password);
         } else if (name.equals("PasswordEncrypted")) {
            byte[] oldVal = this._PasswordEncrypted;
            this._PasswordEncrypted = (byte[])((byte[])v);
            this._postSet(14, oldVal, this._PasswordEncrypted);
         } else if (name.equals("URL")) {
            oldVal = this._URL;
            this._URL = (String)v;
            this._postSet(11, oldVal, this._URL);
         } else if (name.equals("User")) {
            oldVal = this._User;
            this._User = (String)v;
            this._postSet(12, oldVal, this._User);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DataSourceName")) {
         return this._DataSourceName;
      } else if (name.equals("InitialCapacity")) {
         return new Integer(this._InitialCapacity);
      } else if (name.equals("JDBCPropertyOverrides")) {
         return this._JDBCPropertyOverrides;
      } else if (name.equals("MaxCapacity")) {
         return new Integer(this._MaxCapacity);
      } else if (name.equals("MinCapacity")) {
         return new Integer(this._MinCapacity);
      } else if (name.equals("Password")) {
         return this._Password;
      } else if (name.equals("PasswordEncrypted")) {
         return this._PasswordEncrypted;
      } else if (name.equals("URL")) {
         return this._URL;
      } else {
         return name.equals("User") ? this._User : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("url")) {
                  return 11;
               }
               break;
            case 4:
               if (s.equals("user")) {
                  return 12;
               }
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 20:
            case 21:
            default:
               break;
            case 8:
               if (s.equals("password")) {
                  return 13;
               }
               break;
            case 12:
               if (s.equals("max-capacity")) {
                  return 17;
               }

               if (s.equals("min-capacity")) {
                  return 16;
               }
               break;
            case 16:
               if (s.equals("data-source-name")) {
                  return 10;
               }

               if (s.equals("initial-capacity")) {
                  return 15;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 14;
               }
               break;
            case 22:
               if (s.equals("jdbc-property-override")) {
                  return 18;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 18:
               return new JDBCPropertyOverrideMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "data-source-name";
            case 11:
               return "url";
            case 12:
               return "user";
            case 13:
               return "password";
            case 14:
               return "password-encrypted";
            case 15:
               return "initial-capacity";
            case 16:
               return "min-capacity";
            case 17:
               return "max-capacity";
            case 18:
               return "jdbc-property-override";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 18:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 18:
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
      private JDBCSystemResourceOverrideMBeanImpl bean;

      protected Helper(JDBCSystemResourceOverrideMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "DataSourceName";
            case 11:
               return "URL";
            case 12:
               return "User";
            case 13:
               return "Password";
            case 14:
               return "PasswordEncrypted";
            case 15:
               return "InitialCapacity";
            case 16:
               return "MinCapacity";
            case 17:
               return "MaxCapacity";
            case 18:
               return "JDBCPropertyOverrides";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DataSourceName")) {
            return 10;
         } else if (propName.equals("InitialCapacity")) {
            return 15;
         } else if (propName.equals("JDBCPropertyOverrides")) {
            return 18;
         } else if (propName.equals("MaxCapacity")) {
            return 17;
         } else if (propName.equals("MinCapacity")) {
            return 16;
         } else if (propName.equals("Password")) {
            return 13;
         } else if (propName.equals("PasswordEncrypted")) {
            return 14;
         } else if (propName.equals("URL")) {
            return 11;
         } else {
            return propName.equals("User") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getJDBCPropertyOverrides()));
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
            if (this.bean.isDataSourceNameSet()) {
               buf.append("DataSourceName");
               buf.append(String.valueOf(this.bean.getDataSourceName()));
            }

            if (this.bean.isInitialCapacitySet()) {
               buf.append("InitialCapacity");
               buf.append(String.valueOf(this.bean.getInitialCapacity()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getJDBCPropertyOverrides().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJDBCPropertyOverrides()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMaxCapacitySet()) {
               buf.append("MaxCapacity");
               buf.append(String.valueOf(this.bean.getMaxCapacity()));
            }

            if (this.bean.isMinCapacitySet()) {
               buf.append("MinCapacity");
               buf.append(String.valueOf(this.bean.getMinCapacity()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isURLSet()) {
               buf.append("URL");
               buf.append(String.valueOf(this.bean.getURL()));
            }

            if (this.bean.isUserSet()) {
               buf.append("User");
               buf.append(String.valueOf(this.bean.getUser()));
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
            JDBCSystemResourceOverrideMBeanImpl otherTyped = (JDBCSystemResourceOverrideMBeanImpl)other;
            this.addRestartElements("DataSourceName", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("DataSourceName", this.bean.getDataSourceName(), otherTyped.getDataSourceName(), false);
            this.addRestartElements("InitialCapacity", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("InitialCapacity", this.bean.getInitialCapacity(), otherTyped.getInitialCapacity(), true);
            this.addRestartElements("JDBCPropertyOverrides", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeChildDiff("JDBCPropertyOverrides", this.bean.getJDBCPropertyOverrides(), otherTyped.getJDBCPropertyOverrides(), false);
            this.addRestartElements("MaxCapacity", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("MaxCapacity", this.bean.getMaxCapacity(), otherTyped.getMaxCapacity(), true);
            this.addRestartElements("MinCapacity", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("MinCapacity", this.bean.getMinCapacity(), otherTyped.getMinCapacity(), true);
            this.addRestartElements("PasswordEncrypted", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), true);
            this.addRestartElements("URL", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("URL", this.bean.getURL(), otherTyped.getURL(), true);
            this.addRestartElements("User", RestartElementFinder.getPartitionPendingRestart(this.bean));
            this.computeDiff("User", this.bean.getUser(), otherTyped.getUser(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCSystemResourceOverrideMBeanImpl original = (JDBCSystemResourceOverrideMBeanImpl)event.getSourceBean();
            JDBCSystemResourceOverrideMBeanImpl proposed = (JDBCSystemResourceOverrideMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DataSourceName")) {
                  original.setDataSourceName(proposed.getDataSourceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("InitialCapacity")) {
                  original.setInitialCapacity(proposed.getInitialCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("JDBCPropertyOverrides")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJDBCPropertyOverride((JDBCPropertyOverrideMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJDBCPropertyOverride((JDBCPropertyOverrideMBean)update.getRemovedObject());
                  }

                  if (original.getJDBCPropertyOverrides() == null || original.getJDBCPropertyOverrides().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 18);
                  }
               } else if (prop.equals("MaxCapacity")) {
                  original.setMaxCapacity(proposed.getMaxCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("MinCapacity")) {
                  original.setMinCapacity(proposed.getMinCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (!prop.equals("Password")) {
                  if (prop.equals("PasswordEncrypted")) {
                     original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("URL")) {
                     original.setURL(proposed.getURL());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (prop.equals("User")) {
                     original.setUser(proposed.getUser());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            JDBCSystemResourceOverrideMBeanImpl copy = (JDBCSystemResourceOverrideMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DataSourceName")) && this.bean.isDataSourceNameSet()) {
               copy.setDataSourceName(this.bean.getDataSourceName());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialCapacity")) && this.bean.isInitialCapacitySet()) {
               copy.setInitialCapacity(this.bean.getInitialCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCPropertyOverrides")) && this.bean.isJDBCPropertyOverridesSet() && !copy._isSet(18)) {
               JDBCPropertyOverrideMBean[] oldJDBCPropertyOverrides = this.bean.getJDBCPropertyOverrides();
               JDBCPropertyOverrideMBean[] newJDBCPropertyOverrides = new JDBCPropertyOverrideMBean[oldJDBCPropertyOverrides.length];

               for(int i = 0; i < newJDBCPropertyOverrides.length; ++i) {
                  newJDBCPropertyOverrides[i] = (JDBCPropertyOverrideMBean)((JDBCPropertyOverrideMBean)this.createCopy((AbstractDescriptorBean)oldJDBCPropertyOverrides[i], includeObsolete));
               }

               copy.setJDBCPropertyOverrides(newJDBCPropertyOverrides);
            }

            if ((excludeProps == null || !excludeProps.contains("MaxCapacity")) && this.bean.isMaxCapacitySet()) {
               copy.setMaxCapacity(this.bean.getMaxCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("MinCapacity")) && this.bean.isMinCapacitySet()) {
               copy.setMinCapacity(this.bean.getMinCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("URL")) && this.bean.isURLSet()) {
               copy.setURL(this.bean.getURL());
            }

            if ((excludeProps == null || !excludeProps.contains("User")) && this.bean.isUserSet()) {
               copy.setUser(this.bean.getUser());
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
         this.inferSubTree(this.bean.getJDBCPropertyOverrides(), clazz, annotation);
      }
   }
}

package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ConnectionPropertiesBeanImpl extends AbstractDescriptorBean implements ConnectionPropertiesBean, Serializable {
   private ConnectionParamsBean[] _ConnectionParams;
   private String _DriverClassName;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private String _Url;
   private String _UserName;
   private static SchemaHelper2 _schemaHelper;

   public ConnectionPropertiesBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConnectionPropertiesBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConnectionPropertiesBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getUserName() {
      return this._UserName;
   }

   public boolean isUserNameInherited() {
      return false;
   }

   public boolean isUserNameSet() {
      return this._isSet(0);
   }

   public void setUserName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UserName;
      this._UserName = param0;
      this._postSet(0, _oldVal, param0);
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

   public void setPassword(String param0) throws IllegalArgumentException {
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
      return this._isSet(2);
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

   public String getUrl() {
      return this._Url;
   }

   public boolean isUrlInherited() {
      return false;
   }

   public boolean isUrlSet() {
      return this._isSet(3);
   }

   public void setUrl(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Url;
      this._Url = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getDriverClassName() {
      return this._DriverClassName;
   }

   public boolean isDriverClassNameInherited() {
      return false;
   }

   public boolean isDriverClassNameSet() {
      return this._isSet(4);
   }

   public void setDriverClassName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DriverClassName;
      this._DriverClassName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public void addConnectionParam(ConnectionParamsBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         ConnectionParamsBean[] _new;
         if (this._isSet(5)) {
            _new = (ConnectionParamsBean[])((ConnectionParamsBean[])this._getHelper()._extendArray(this.getConnectionParams(), ConnectionParamsBean.class, param0));
         } else {
            _new = new ConnectionParamsBean[]{param0};
         }

         try {
            this.setConnectionParams(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConnectionParamsBean[] getConnectionParams() {
      return this._ConnectionParams;
   }

   public boolean isConnectionParamsInherited() {
      return false;
   }

   public boolean isConnectionParamsSet() {
      return this._isSet(5);
   }

   public void removeConnectionParam(ConnectionParamsBean param0) {
      this.destroyConnectionParams(param0);
   }

   public void setConnectionParams(ConnectionParamsBean[] param0) throws InvalidAttributeValueException {
      ConnectionParamsBean[] param0 = param0 == null ? new ConnectionParamsBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConnectionParamsBean[] _oldVal = this._ConnectionParams;
      this._ConnectionParams = (ConnectionParamsBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public ConnectionParamsBean createConnectionParams() {
      ConnectionParamsBeanImpl _val = new ConnectionParamsBeanImpl(this, -1);

      try {
         this.addConnectionParam(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConnectionParams(ConnectionParamsBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         ConnectionParamsBean[] _old = this.getConnectionParams();
         ConnectionParamsBean[] _new = (ConnectionParamsBean[])((ConnectionParamsBean[])this._getHelper()._removeElement(_old, ConnectionParamsBean.class, param0));
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
               this.setConnectionParams(_new);
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
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of ConnectionPropertiesBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(2, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 1) {
            this._markSet(2, false);
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._ConnectionParams = new ConnectionParamsBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._DriverClassName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Url = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._UserName = null;
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("url")) {
                  return 3;
               }
               break;
            case 8:
               if (s.equals("password")) {
                  return 1;
               }
               break;
            case 9:
               if (s.equals("user-name")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("connection-params")) {
                  return 5;
               }

               if (s.equals("driver-class-name")) {
                  return 4;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new ConnectionParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "user-name";
            case 1:
               return "password";
            case 2:
               return "password-encrypted";
            case 3:
               return "url";
            case 4:
               return "driver-class-name";
            case 5:
               return "connection-params";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ConnectionPropertiesBeanImpl bean;

      protected Helper(ConnectionPropertiesBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "UserName";
            case 1:
               return "Password";
            case 2:
               return "PasswordEncrypted";
            case 3:
               return "Url";
            case 4:
               return "DriverClassName";
            case 5:
               return "ConnectionParams";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionParams")) {
            return 5;
         } else if (propName.equals("DriverClassName")) {
            return 4;
         } else if (propName.equals("Password")) {
            return 1;
         } else if (propName.equals("PasswordEncrypted")) {
            return 2;
         } else if (propName.equals("Url")) {
            return 3;
         } else {
            return propName.equals("UserName") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConnectionParams()));
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

            for(int i = 0; i < this.bean.getConnectionParams().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConnectionParams()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDriverClassNameSet()) {
               buf.append("DriverClassName");
               buf.append(String.valueOf(this.bean.getDriverClassName()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            if (this.bean.isUrlSet()) {
               buf.append("Url");
               buf.append(String.valueOf(this.bean.getUrl()));
            }

            if (this.bean.isUserNameSet()) {
               buf.append("UserName");
               buf.append(String.valueOf(this.bean.getUserName()));
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
            ConnectionPropertiesBeanImpl otherTyped = (ConnectionPropertiesBeanImpl)other;
            this.computeChildDiff("ConnectionParams", this.bean.getConnectionParams(), otherTyped.getConnectionParams(), false);
            this.computeDiff("DriverClassName", this.bean.getDriverClassName(), otherTyped.getDriverClassName(), false);
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), false);
            this.computeDiff("Url", this.bean.getUrl(), otherTyped.getUrl(), false);
            this.computeDiff("UserName", this.bean.getUserName(), otherTyped.getUserName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConnectionPropertiesBeanImpl original = (ConnectionPropertiesBeanImpl)event.getSourceBean();
            ConnectionPropertiesBeanImpl proposed = (ConnectionPropertiesBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionParams")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConnectionParam((ConnectionParamsBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConnectionParam((ConnectionParamsBean)update.getRemovedObject());
                  }

                  if (original.getConnectionParams() == null || original.getConnectionParams().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("DriverClassName")) {
                  original.setDriverClassName(proposed.getDriverClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (!prop.equals("Password")) {
                  if (prop.equals("PasswordEncrypted")) {
                     original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("Url")) {
                     original.setUrl(proposed.getUrl());
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  } else if (prop.equals("UserName")) {
                     original.setUserName(proposed.getUserName());
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            ConnectionPropertiesBeanImpl copy = (ConnectionPropertiesBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionParams")) && this.bean.isConnectionParamsSet() && !copy._isSet(5)) {
               ConnectionParamsBean[] oldConnectionParams = this.bean.getConnectionParams();
               ConnectionParamsBean[] newConnectionParams = new ConnectionParamsBean[oldConnectionParams.length];

               for(int i = 0; i < newConnectionParams.length; ++i) {
                  newConnectionParams[i] = (ConnectionParamsBean)((ConnectionParamsBean)this.createCopy((AbstractDescriptorBean)oldConnectionParams[i], includeObsolete));
               }

               copy.setConnectionParams(newConnectionParams);
            }

            if ((excludeProps == null || !excludeProps.contains("DriverClassName")) && this.bean.isDriverClassNameSet()) {
               copy.setDriverClassName(this.bean.getDriverClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Url")) && this.bean.isUrlSet()) {
               copy.setUrl(this.bean.getUrl());
            }

            if ((excludeProps == null || !excludeProps.contains("UserName")) && this.bean.isUserNameSet()) {
               copy.setUserName(this.bean.getUserName());
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
         this.inferSubTree(this.bean.getConnectionParams(), clazz, annotation);
      }
   }
}

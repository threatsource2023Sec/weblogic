package kodo.jdbc.conf.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class KodoPoolingDataSourceBeanImpl extends DriverDataSourceBeanImpl implements KodoPoolingDataSourceBean, Serializable {
   private String _ConnectionDriverName;
   private String _ConnectionPassword;
   private byte[] _ConnectionPasswordEncrypted;
   private String _ConnectionURL;
   private String _ConnectionUserName;
   private int _LoginTimeout;
   private static SchemaHelper2 _schemaHelper;

   public KodoPoolingDataSourceBeanImpl() {
      this._initializeProperty(-1);
   }

   public KodoPoolingDataSourceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public KodoPoolingDataSourceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getConnectionUserName() {
      return this._ConnectionUserName;
   }

   public boolean isConnectionUserNameInherited() {
      return false;
   }

   public boolean isConnectionUserNameSet() {
      return this._isSet(0);
   }

   public void setConnectionUserName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionUserName;
      this._ConnectionUserName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getLoginTimeout() {
      return this._LoginTimeout;
   }

   public boolean isLoginTimeoutInherited() {
      return false;
   }

   public boolean isLoginTimeoutSet() {
      return this._isSet(1);
   }

   public void setLoginTimeout(int param0) {
      int _oldVal = this._LoginTimeout;
      this._LoginTimeout = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getConnectionPassword() {
      byte[] bEncrypted = this.getConnectionPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("ConnectionPassword", bEncrypted);
   }

   public boolean isConnectionPasswordInherited() {
      return false;
   }

   public boolean isConnectionPasswordSet() {
      return this.isConnectionPasswordEncryptedSet();
   }

   public void setConnectionPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setConnectionPasswordEncrypted(param0 == null ? null : this._encrypt("ConnectionPassword", param0));
   }

   public byte[] getConnectionPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._ConnectionPasswordEncrypted);
   }

   public String getConnectionPasswordEncryptedAsString() {
      byte[] obj = this.getConnectionPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isConnectionPasswordEncryptedInherited() {
      return false;
   }

   public boolean isConnectionPasswordEncryptedSet() {
      return this._isSet(3);
   }

   public void setConnectionPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setConnectionPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getConnectionURL() {
      return this._ConnectionURL;
   }

   public boolean isConnectionURLInherited() {
      return false;
   }

   public boolean isConnectionURLSet() {
      return this._isSet(4);
   }

   public void setConnectionURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionURL;
      this._ConnectionURL = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getConnectionDriverName() {
      return this._ConnectionDriverName;
   }

   public boolean isConnectionDriverNameInherited() {
      return false;
   }

   public boolean isConnectionDriverNameSet() {
      return this._isSet(5);
   }

   public void setConnectionDriverName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionDriverName;
      this._ConnectionDriverName = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setConnectionPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._ConnectionPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: ConnectionPasswordEncrypted of KodoPoolingDataSourceBean");
      } else {
         this._getHelper()._clearArray(this._ConnectionPasswordEncrypted);
         this._ConnectionPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(3, _oldVal, param0);
      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 2) {
            this._markSet(3, false);
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
               this._ConnectionDriverName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ConnectionPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ConnectionPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ConnectionURL = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ConnectionUserName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._LoginTimeout = 10;
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

   public static class SchemaHelper2 extends DriverDataSourceBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("login-timeout")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("connection-url")) {
                  return 4;
               }
            case 15:
            case 16:
            case 17:
            case 18:
            case 21:
            default:
               break;
            case 19:
               if (s.equals("connection-password")) {
                  return 2;
               }

               if (s.equals("connection-password")) {
                  return 3;
               }
               break;
            case 20:
               if (s.equals("connection-user-name")) {
                  return 0;
               }
               break;
            case 22:
               if (s.equals("connection-driver-name")) {
                  return 5;
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
               return "connection-user-name";
            case 1:
               return "login-timeout";
            case 2:
               return "connection-password";
            case 3:
               return "connection-password";
            case 4:
               return "connection-url";
            case 5:
               return "connection-driver-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            default:
               return super.isArray(propIndex);
         }
      }
   }

   protected static class Helper extends DriverDataSourceBeanImpl.Helper {
      private KodoPoolingDataSourceBeanImpl bean;

      protected Helper(KodoPoolingDataSourceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ConnectionUserName";
            case 1:
               return "LoginTimeout";
            case 2:
               return "ConnectionPassword";
            case 3:
               return "ConnectionPasswordEncrypted";
            case 4:
               return "ConnectionURL";
            case 5:
               return "ConnectionDriverName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionDriverName")) {
            return 5;
         } else if (propName.equals("ConnectionPassword")) {
            return 2;
         } else if (propName.equals("ConnectionPasswordEncrypted")) {
            return 3;
         } else if (propName.equals("ConnectionURL")) {
            return 4;
         } else if (propName.equals("ConnectionUserName")) {
            return 0;
         } else {
            return propName.equals("LoginTimeout") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionDriverNameSet()) {
               buf.append("ConnectionDriverName");
               buf.append(String.valueOf(this.bean.getConnectionDriverName()));
            }

            if (this.bean.isConnectionPasswordSet()) {
               buf.append("ConnectionPassword");
               buf.append(String.valueOf(this.bean.getConnectionPassword()));
            }

            if (this.bean.isConnectionPasswordEncryptedSet()) {
               buf.append("ConnectionPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getConnectionPasswordEncrypted())));
            }

            if (this.bean.isConnectionURLSet()) {
               buf.append("ConnectionURL");
               buf.append(String.valueOf(this.bean.getConnectionURL()));
            }

            if (this.bean.isConnectionUserNameSet()) {
               buf.append("ConnectionUserName");
               buf.append(String.valueOf(this.bean.getConnectionUserName()));
            }

            if (this.bean.isLoginTimeoutSet()) {
               buf.append("LoginTimeout");
               buf.append(String.valueOf(this.bean.getLoginTimeout()));
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
            KodoPoolingDataSourceBeanImpl otherTyped = (KodoPoolingDataSourceBeanImpl)other;
            this.computeDiff("ConnectionDriverName", this.bean.getConnectionDriverName(), otherTyped.getConnectionDriverName(), false);
            this.computeDiff("ConnectionPasswordEncrypted", this.bean.getConnectionPasswordEncrypted(), otherTyped.getConnectionPasswordEncrypted(), false);
            this.computeDiff("ConnectionURL", this.bean.getConnectionURL(), otherTyped.getConnectionURL(), false);
            this.computeDiff("ConnectionUserName", this.bean.getConnectionUserName(), otherTyped.getConnectionUserName(), false);
            this.computeDiff("LoginTimeout", this.bean.getLoginTimeout(), otherTyped.getLoginTimeout(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            KodoPoolingDataSourceBeanImpl original = (KodoPoolingDataSourceBeanImpl)event.getSourceBean();
            KodoPoolingDataSourceBeanImpl proposed = (KodoPoolingDataSourceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionDriverName")) {
                  original.setConnectionDriverName(proposed.getConnectionDriverName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (!prop.equals("ConnectionPassword")) {
                  if (prop.equals("ConnectionPasswordEncrypted")) {
                     original.setConnectionPasswordEncrypted(proposed.getConnectionPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  } else if (prop.equals("ConnectionURL")) {
                     original.setConnectionURL(proposed.getConnectionURL());
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  } else if (prop.equals("ConnectionUserName")) {
                     original.setConnectionUserName(proposed.getConnectionUserName());
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  } else if (prop.equals("LoginTimeout")) {
                     original.setLoginTimeout(proposed.getLoginTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            KodoPoolingDataSourceBeanImpl copy = (KodoPoolingDataSourceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionDriverName")) && this.bean.isConnectionDriverNameSet()) {
               copy.setConnectionDriverName(this.bean.getConnectionDriverName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionPasswordEncrypted")) && this.bean.isConnectionPasswordEncryptedSet()) {
               Object o = this.bean.getConnectionPasswordEncrypted();
               copy.setConnectionPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionURL")) && this.bean.isConnectionURLSet()) {
               copy.setConnectionURL(this.bean.getConnectionURL());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionUserName")) && this.bean.isConnectionUserNameSet()) {
               copy.setConnectionUserName(this.bean.getConnectionUserName());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginTimeout")) && this.bean.isLoginTimeoutSet()) {
               copy.setLoginTimeout(this.bean.getLoginTimeout());
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

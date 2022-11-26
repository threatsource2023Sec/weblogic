package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class DataSourceBeanImpl extends SettableBeanImpl implements DataSourceBean, Serializable {
   private String _ClassName;
   private String _DatabaseName;
   private String _Description;
   private String _Id;
   private int _InitialPoolSize;
   private String _IsolationLevel;
   private int _LoginTimeout;
   private int _MaxIdleTime;
   private int _MaxPoolSize;
   private int _MaxStatements;
   private int _MinPoolSize;
   private String _Name;
   private String _Password;
   private int _PortNumber;
   private JavaEEPropertyBean[] _Properties;
   private String _ServerName;
   private boolean _Transactional;
   private String _Url;
   private String _User;
   private static SchemaHelper2 _schemaHelper;

   public DataSourceBeanImpl() {
      this._initializeProperty(-1);
   }

   public DataSourceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DataSourceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(1);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getClassName() {
      return this._ClassName;
   }

   public boolean isClassNameInherited() {
      return false;
   }

   public boolean isClassNameSet() {
      return this._isSet(2);
   }

   public void setClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClassName;
      this._ClassName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getServerName() {
      return this._ServerName;
   }

   public boolean isServerNameInherited() {
      return false;
   }

   public boolean isServerNameSet() {
      return this._isSet(3);
   }

   public void setServerName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServerName;
      this._ServerName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getPortNumber() {
      return this._PortNumber;
   }

   public boolean isPortNumberInherited() {
      return false;
   }

   public boolean isPortNumberSet() {
      return this._isSet(4);
   }

   public void setPortNumber(int param0) {
      int _oldVal = this._PortNumber;
      this._PortNumber = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getDatabaseName() {
      return this._DatabaseName;
   }

   public boolean isDatabaseNameInherited() {
      return false;
   }

   public boolean isDatabaseNameSet() {
      return this._isSet(5);
   }

   public void setDatabaseName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DatabaseName;
      this._DatabaseName = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getUrl() {
      return this._Url;
   }

   public boolean isUrlInherited() {
      return false;
   }

   public boolean isUrlSet() {
      return this._isSet(6);
   }

   public void setUrl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Url;
      this._Url = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getUser() {
      return this._User;
   }

   public boolean isUserInherited() {
      return false;
   }

   public boolean isUserSet() {
      return this._isSet(7);
   }

   public void setUser(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._User;
      this._User = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getPassword() {
      return this._Password;
   }

   public boolean isPasswordInherited() {
      return false;
   }

   public boolean isPasswordSet() {
      return this._isSet(8);
   }

   public void setPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Password;
      this._Password = param0;
      this._postSet(8, _oldVal, param0);
   }

   public void addProperty(JavaEEPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 9)) {
         JavaEEPropertyBean[] _new;
         if (this._isSet(9)) {
            _new = (JavaEEPropertyBean[])((JavaEEPropertyBean[])this._getHelper()._extendArray(this.getProperties(), JavaEEPropertyBean.class, param0));
         } else {
            _new = new JavaEEPropertyBean[]{param0};
         }

         try {
            this.setProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JavaEEPropertyBean[] getProperties() {
      return this._Properties;
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(9);
   }

   public void removeProperty(JavaEEPropertyBean param0) {
      this.destroyProperty(param0);
   }

   public void setProperties(JavaEEPropertyBean[] param0) throws InvalidAttributeValueException {
      JavaEEPropertyBean[] param0 = param0 == null ? new JavaEEPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 9)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JavaEEPropertyBean[] _oldVal = this._Properties;
      this._Properties = (JavaEEPropertyBean[])param0;
      this._postSet(9, _oldVal, param0);
   }

   public JavaEEPropertyBean lookupProperty(String param0) {
      Object[] aary = (Object[])this._Properties;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      JavaEEPropertyBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (JavaEEPropertyBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public JavaEEPropertyBean createProperty() {
      JavaEEPropertyBeanImpl _val = new JavaEEPropertyBeanImpl(this, -1);

      try {
         this.addProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyProperty(JavaEEPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 9);
         JavaEEPropertyBean[] _old = this.getProperties();
         JavaEEPropertyBean[] _new = (JavaEEPropertyBean[])((JavaEEPropertyBean[])this._getHelper()._removeElement(_old, JavaEEPropertyBean.class, param0));
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
               this.setProperties(_new);
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

   public int getLoginTimeout() {
      return this._LoginTimeout;
   }

   public boolean isLoginTimeoutInherited() {
      return false;
   }

   public boolean isLoginTimeoutSet() {
      return this._isSet(10);
   }

   public void setLoginTimeout(int param0) {
      int _oldVal = this._LoginTimeout;
      this._LoginTimeout = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isTransactional() {
      return this._Transactional;
   }

   public boolean isTransactionalInherited() {
      return false;
   }

   public boolean isTransactionalSet() {
      return this._isSet(11);
   }

   public void setTransactional(boolean param0) {
      boolean _oldVal = this._Transactional;
      this._Transactional = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getIsolationLevel() {
      return !this._isSet(12) ? null : this._IsolationLevel;
   }

   public boolean isIsolationLevelInherited() {
      return false;
   }

   public boolean isIsolationLevelSet() {
      return this._isSet(12);
   }

   public void setIsolationLevel(String param0) {
      if (param0 == null) {
         this._unSet(12);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"TRANSACTION_READ_UNCOMMITTED", "TRANSACTION_READ_COMMITTED", "TRANSACTION_REPEATABLE_READ", "TRANSACTION_SERIALIZABLE"};
         param0 = LegalChecks.checkInEnum("IsolationLevel", param0, _set);
         String _oldVal = this._IsolationLevel;
         this._IsolationLevel = param0;
         this._postSet(12, _oldVal, param0);
      }
   }

   public int getInitialPoolSize() {
      return this._InitialPoolSize;
   }

   public boolean isInitialPoolSizeInherited() {
      return false;
   }

   public boolean isInitialPoolSizeSet() {
      return this._isSet(13);
   }

   public void setInitialPoolSize(int param0) {
      int _oldVal = this._InitialPoolSize;
      this._InitialPoolSize = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getMaxPoolSize() {
      return this._MaxPoolSize;
   }

   public boolean isMaxPoolSizeInherited() {
      return false;
   }

   public boolean isMaxPoolSizeSet() {
      return this._isSet(14);
   }

   public void setMaxPoolSize(int param0) {
      int _oldVal = this._MaxPoolSize;
      this._MaxPoolSize = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getMinPoolSize() {
      return this._MinPoolSize;
   }

   public boolean isMinPoolSizeInherited() {
      return false;
   }

   public boolean isMinPoolSizeSet() {
      return this._isSet(15);
   }

   public void setMinPoolSize(int param0) {
      int _oldVal = this._MinPoolSize;
      this._MinPoolSize = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getMaxIdleTime() {
      return this._MaxIdleTime;
   }

   public boolean isMaxIdleTimeInherited() {
      return false;
   }

   public boolean isMaxIdleTimeSet() {
      return this._isSet(16);
   }

   public void setMaxIdleTime(int param0) {
      int _oldVal = this._MaxIdleTime;
      this._MaxIdleTime = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getMaxStatements() {
      return this._MaxStatements;
   }

   public boolean isMaxStatementsInherited() {
      return false;
   }

   public boolean isMaxStatementsSet() {
      return this._isSet(17);
   }

   public void setMaxStatements(int param0) {
      int _oldVal = this._MaxStatements;
      this._MaxStatements = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(18);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(18, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Name", this.isNameSet());
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
               this._ClassName = "";
               if (initOne) {
                  break;
               }
            case 5:
               this._DatabaseName = "";
               if (initOne) {
                  break;
               }
            case 0:
               this._Description = "";
               if (initOne) {
                  break;
               }
            case 18:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._InitialPoolSize = -1;
               if (initOne) {
                  break;
               }
            case 12:
               this._IsolationLevel = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._LoginTimeout = -1;
               if (initOne) {
                  break;
               }
            case 16:
               this._MaxIdleTime = -1;
               if (initOne) {
                  break;
               }
            case 14:
               this._MaxPoolSize = -1;
               if (initOne) {
                  break;
               }
            case 17:
               this._MaxStatements = -1;
               if (initOne) {
                  break;
               }
            case 15:
               this._MinPoolSize = -1;
               if (initOne) {
                  break;
               }
            case 1:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._Password = "";
               if (initOne) {
                  break;
               }
            case 4:
               this._PortNumber = -1;
               if (initOne) {
                  break;
               }
            case 9:
               this._Properties = new JavaEEPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._ServerName = "localhost";
               if (initOne) {
                  break;
               }
            case 6:
               this._Url = "";
               if (initOne) {
                  break;
               }
            case 7:
               this._User = "";
               if (initOne) {
                  break;
               }
            case 11:
               this._Transactional = true;
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 18;
               }
               break;
            case 3:
               if (s.equals("url")) {
                  return 6;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 1;
               }

               if (s.equals("user")) {
                  return 7;
               }
            case 5:
            case 6:
            case 7:
            case 9:
            case 12:
            case 16:
            default:
               break;
            case 8:
               if (s.equals("password")) {
                  return 8;
               }

               if (s.equals("property")) {
                  return 9;
               }
               break;
            case 10:
               if (s.equals("class-name")) {
                  return 2;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("port-number")) {
                  return 4;
               }

               if (s.equals("server-name")) {
                  return 3;
               }
               break;
            case 13:
               if (s.equals("database-name")) {
                  return 5;
               }

               if (s.equals("login-timeout")) {
                  return 10;
               }

               if (s.equals("max-idle-time")) {
                  return 16;
               }

               if (s.equals("max-pool-size")) {
                  return 14;
               }

               if (s.equals("min-pool-size")) {
                  return 15;
               }

               if (s.equals("transactional")) {
                  return 11;
               }
               break;
            case 14:
               if (s.equals("max-statements")) {
                  return 17;
               }
               break;
            case 15:
               if (s.equals("isolation-level")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("initial-pool-size")) {
                  return 13;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 9:
               return new JavaEEPropertyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "name";
            case 2:
               return "class-name";
            case 3:
               return "server-name";
            case 4:
               return "port-number";
            case 5:
               return "database-name";
            case 6:
               return "url";
            case 7:
               return "user";
            case 8:
               return "password";
            case 9:
               return "property";
            case 10:
               return "login-timeout";
            case 11:
               return "transactional";
            case 12:
               return "isolation-level";
            case 13:
               return "initial-pool-size";
            case 14:
               return "max-pool-size";
            case 15:
               return "min-pool-size";
            case 16:
               return "max-idle-time";
            case 17:
               return "max-statements";
            case 18:
               return "id";
            default:
               return super.getElementName(propIndex);
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends SettableBeanImpl.Helper {
      private DataSourceBeanImpl bean;

      protected Helper(DataSourceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "Name";
            case 2:
               return "ClassName";
            case 3:
               return "ServerName";
            case 4:
               return "PortNumber";
            case 5:
               return "DatabaseName";
            case 6:
               return "Url";
            case 7:
               return "User";
            case 8:
               return "Password";
            case 9:
               return "Properties";
            case 10:
               return "LoginTimeout";
            case 11:
               return "Transactional";
            case 12:
               return "IsolationLevel";
            case 13:
               return "InitialPoolSize";
            case 14:
               return "MaxPoolSize";
            case 15:
               return "MinPoolSize";
            case 16:
               return "MaxIdleTime";
            case 17:
               return "MaxStatements";
            case 18:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClassName")) {
            return 2;
         } else if (propName.equals("DatabaseName")) {
            return 5;
         } else if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 18;
         } else if (propName.equals("InitialPoolSize")) {
            return 13;
         } else if (propName.equals("IsolationLevel")) {
            return 12;
         } else if (propName.equals("LoginTimeout")) {
            return 10;
         } else if (propName.equals("MaxIdleTime")) {
            return 16;
         } else if (propName.equals("MaxPoolSize")) {
            return 14;
         } else if (propName.equals("MaxStatements")) {
            return 17;
         } else if (propName.equals("MinPoolSize")) {
            return 15;
         } else if (propName.equals("Name")) {
            return 1;
         } else if (propName.equals("Password")) {
            return 8;
         } else if (propName.equals("PortNumber")) {
            return 4;
         } else if (propName.equals("Properties")) {
            return 9;
         } else if (propName.equals("ServerName")) {
            return 3;
         } else if (propName.equals("Url")) {
            return 6;
         } else if (propName.equals("User")) {
            return 7;
         } else {
            return propName.equals("Transactional") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getProperties()));
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
            if (this.bean.isClassNameSet()) {
               buf.append("ClassName");
               buf.append(String.valueOf(this.bean.getClassName()));
            }

            if (this.bean.isDatabaseNameSet()) {
               buf.append("DatabaseName");
               buf.append(String.valueOf(this.bean.getDatabaseName()));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInitialPoolSizeSet()) {
               buf.append("InitialPoolSize");
               buf.append(String.valueOf(this.bean.getInitialPoolSize()));
            }

            if (this.bean.isIsolationLevelSet()) {
               buf.append("IsolationLevel");
               buf.append(String.valueOf(this.bean.getIsolationLevel()));
            }

            if (this.bean.isLoginTimeoutSet()) {
               buf.append("LoginTimeout");
               buf.append(String.valueOf(this.bean.getLoginTimeout()));
            }

            if (this.bean.isMaxIdleTimeSet()) {
               buf.append("MaxIdleTime");
               buf.append(String.valueOf(this.bean.getMaxIdleTime()));
            }

            if (this.bean.isMaxPoolSizeSet()) {
               buf.append("MaxPoolSize");
               buf.append(String.valueOf(this.bean.getMaxPoolSize()));
            }

            if (this.bean.isMaxStatementsSet()) {
               buf.append("MaxStatements");
               buf.append(String.valueOf(this.bean.getMaxStatements()));
            }

            if (this.bean.isMinPoolSizeSet()) {
               buf.append("MinPoolSize");
               buf.append(String.valueOf(this.bean.getMinPoolSize()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPortNumberSet()) {
               buf.append("PortNumber");
               buf.append(String.valueOf(this.bean.getPortNumber()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isServerNameSet()) {
               buf.append("ServerName");
               buf.append(String.valueOf(this.bean.getServerName()));
            }

            if (this.bean.isUrlSet()) {
               buf.append("Url");
               buf.append(String.valueOf(this.bean.getUrl()));
            }

            if (this.bean.isUserSet()) {
               buf.append("User");
               buf.append(String.valueOf(this.bean.getUser()));
            }

            if (this.bean.isTransactionalSet()) {
               buf.append("Transactional");
               buf.append(String.valueOf(this.bean.isTransactional()));
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
            DataSourceBeanImpl otherTyped = (DataSourceBeanImpl)other;
            this.computeDiff("ClassName", this.bean.getClassName(), otherTyped.getClassName(), false);
            this.computeDiff("DatabaseName", this.bean.getDatabaseName(), otherTyped.getDatabaseName(), false);
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("InitialPoolSize", this.bean.getInitialPoolSize(), otherTyped.getInitialPoolSize(), false);
            this.computeDiff("IsolationLevel", this.bean.getIsolationLevel(), otherTyped.getIsolationLevel(), false);
            this.computeDiff("LoginTimeout", this.bean.getLoginTimeout(), otherTyped.getLoginTimeout(), false);
            this.computeDiff("MaxIdleTime", this.bean.getMaxIdleTime(), otherTyped.getMaxIdleTime(), false);
            this.computeDiff("MaxPoolSize", this.bean.getMaxPoolSize(), otherTyped.getMaxPoolSize(), false);
            this.computeDiff("MaxStatements", this.bean.getMaxStatements(), otherTyped.getMaxStatements(), false);
            this.computeDiff("MinPoolSize", this.bean.getMinPoolSize(), otherTyped.getMinPoolSize(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Password", this.bean.getPassword(), otherTyped.getPassword(), false);
            this.computeDiff("PortNumber", this.bean.getPortNumber(), otherTyped.getPortNumber(), false);
            this.computeChildDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), false);
            this.computeDiff("ServerName", this.bean.getServerName(), otherTyped.getServerName(), false);
            this.computeDiff("Url", this.bean.getUrl(), otherTyped.getUrl(), false);
            this.computeDiff("User", this.bean.getUser(), otherTyped.getUser(), false);
            this.computeDiff("Transactional", this.bean.isTransactional(), otherTyped.isTransactional(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DataSourceBeanImpl original = (DataSourceBeanImpl)event.getSourceBean();
            DataSourceBeanImpl proposed = (DataSourceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClassName")) {
                  original.setClassName(proposed.getClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("DatabaseName")) {
                  original.setDatabaseName(proposed.getDatabaseName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("InitialPoolSize")) {
                  original.setInitialPoolSize(proposed.getInitialPoolSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("IsolationLevel")) {
                  original.setIsolationLevel(proposed.getIsolationLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("LoginTimeout")) {
                  original.setLoginTimeout(proposed.getLoginTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("MaxIdleTime")) {
                  original.setMaxIdleTime(proposed.getMaxIdleTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("MaxPoolSize")) {
                  original.setMaxPoolSize(proposed.getMaxPoolSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MaxStatements")) {
                  original.setMaxStatements(proposed.getMaxStatements());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("MinPoolSize")) {
                  original.setMinPoolSize(proposed.getMinPoolSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Password")) {
                  original.setPassword(proposed.getPassword());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("PortNumber")) {
                  original.setPortNumber(proposed.getPortNumber());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Properties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addProperty((JavaEEPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeProperty((JavaEEPropertyBean)update.getRemovedObject());
                  }

                  if (original.getProperties() == null || original.getProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (prop.equals("ServerName")) {
                  original.setServerName(proposed.getServerName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Url")) {
                  original.setUrl(proposed.getUrl());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("User")) {
                  original.setUser(proposed.getUser());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Transactional")) {
                  original.setTransactional(proposed.isTransactional());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            DataSourceBeanImpl copy = (DataSourceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClassName")) && this.bean.isClassNameSet()) {
               copy.setClassName(this.bean.getClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("DatabaseName")) && this.bean.isDatabaseNameSet()) {
               copy.setDatabaseName(this.bean.getDatabaseName());
            }

            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialPoolSize")) && this.bean.isInitialPoolSizeSet()) {
               copy.setInitialPoolSize(this.bean.getInitialPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("IsolationLevel")) && this.bean.isIsolationLevelSet()) {
               copy.setIsolationLevel(this.bean.getIsolationLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginTimeout")) && this.bean.isLoginTimeoutSet()) {
               copy.setLoginTimeout(this.bean.getLoginTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxIdleTime")) && this.bean.isMaxIdleTimeSet()) {
               copy.setMaxIdleTime(this.bean.getMaxIdleTime());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxPoolSize")) && this.bean.isMaxPoolSizeSet()) {
               copy.setMaxPoolSize(this.bean.getMaxPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxStatements")) && this.bean.isMaxStatementsSet()) {
               copy.setMaxStatements(this.bean.getMaxStatements());
            }

            if ((excludeProps == null || !excludeProps.contains("MinPoolSize")) && this.bean.isMinPoolSizeSet()) {
               copy.setMinPoolSize(this.bean.getMinPoolSize());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Password")) && this.bean.isPasswordSet()) {
               copy.setPassword(this.bean.getPassword());
            }

            if ((excludeProps == null || !excludeProps.contains("PortNumber")) && this.bean.isPortNumberSet()) {
               copy.setPortNumber(this.bean.getPortNumber());
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet() && !copy._isSet(9)) {
               JavaEEPropertyBean[] oldProperties = this.bean.getProperties();
               JavaEEPropertyBean[] newProperties = new JavaEEPropertyBean[oldProperties.length];

               for(int i = 0; i < newProperties.length; ++i) {
                  newProperties[i] = (JavaEEPropertyBean)((JavaEEPropertyBean)this.createCopy((AbstractDescriptorBean)oldProperties[i], includeObsolete));
               }

               copy.setProperties(newProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("ServerName")) && this.bean.isServerNameSet()) {
               copy.setServerName(this.bean.getServerName());
            }

            if ((excludeProps == null || !excludeProps.contains("Url")) && this.bean.isUrlSet()) {
               copy.setUrl(this.bean.getUrl());
            }

            if ((excludeProps == null || !excludeProps.contains("User")) && this.bean.isUserSet()) {
               copy.setUser(this.bean.getUser());
            }

            if ((excludeProps == null || !excludeProps.contains("Transactional")) && this.bean.isTransactionalSet()) {
               copy.setTransactional(this.bean.isTransactional());
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
         this.inferSubTree(this.bean.getProperties(), clazz, annotation);
      }
   }
}

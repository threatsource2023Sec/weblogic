package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import weblogic.j2ee.descriptor.AuthenticationMechanismBean;
import weblogic.j2ee.descriptor.AuthenticationMechanismBeanImpl;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ConnectionDefinitionPropertiesBeanImpl extends SettableBeanImpl implements ConnectionDefinitionPropertiesBean, Serializable {
   private AuthenticationMechanismBean[] _AuthenticationMechanisms;
   private String _Id;
   private LoggingBean _Logging;
   private PoolParamsBean _PoolParams;
   private ConfigPropertiesBean _Properties;
   private boolean _ReauthenticationSupport;
   private String _ResAuth;
   private String _TransactionSupport;
   private static SchemaHelper2 _schemaHelper;

   public ConnectionDefinitionPropertiesBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConnectionDefinitionPropertiesBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConnectionDefinitionPropertiesBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public PoolParamsBean getPoolParams() {
      return this._PoolParams;
   }

   public boolean isPoolParamsInherited() {
      return false;
   }

   public boolean isPoolParamsSet() {
      return this._isSet(0) || this._isAnythingSet((AbstractDescriptorBean)this.getPoolParams());
   }

   public void setPoolParams(PoolParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 0)) {
         this._postCreate(_child);
      }

      PoolParamsBean _oldVal = this._PoolParams;
      this._PoolParams = param0;
      this._postSet(0, _oldVal, param0);
   }

   public LoggingBean getLogging() {
      return this._Logging;
   }

   public boolean isLoggingInherited() {
      return false;
   }

   public boolean isLoggingSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getLogging());
   }

   public void setLogging(LoggingBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      LoggingBean _oldVal = this._Logging;
      this._Logging = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getTransactionSupport() {
      return this._TransactionSupport;
   }

   public boolean isTransactionSupportInherited() {
      return false;
   }

   public boolean isTransactionSupportSet() {
      return this._isSet(2);
   }

   public void setTransactionSupport(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NoTransaction", "LocalTransaction", "XATransaction"};
      param0 = LegalChecks.checkInEnum("TransactionSupport", param0, _set);
      String _oldVal = this._TransactionSupport;
      this._TransactionSupport = param0;
      this._postSet(2, _oldVal, param0);
   }

   public void addAuthenticationMechanism(AuthenticationMechanismBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         AuthenticationMechanismBean[] _new;
         if (this._isSet(3)) {
            _new = (AuthenticationMechanismBean[])((AuthenticationMechanismBean[])this._getHelper()._extendArray(this.getAuthenticationMechanisms(), AuthenticationMechanismBean.class, param0));
         } else {
            _new = new AuthenticationMechanismBean[]{param0};
         }

         try {
            this.setAuthenticationMechanisms(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AuthenticationMechanismBean[] getAuthenticationMechanisms() {
      return this._AuthenticationMechanisms;
   }

   public boolean isAuthenticationMechanismsInherited() {
      return false;
   }

   public boolean isAuthenticationMechanismsSet() {
      return this._isSet(3);
   }

   public void removeAuthenticationMechanism(AuthenticationMechanismBean param0) {
      this.destroyAuthenticationMechanism(param0);
   }

   public void setAuthenticationMechanisms(AuthenticationMechanismBean[] param0) throws InvalidAttributeValueException {
      AuthenticationMechanismBean[] param0 = param0 == null ? new AuthenticationMechanismBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AuthenticationMechanismBean[] _oldVal = this._AuthenticationMechanisms;
      this._AuthenticationMechanisms = (AuthenticationMechanismBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public AuthenticationMechanismBean createAuthenticationMechanism() {
      AuthenticationMechanismBeanImpl _val = new AuthenticationMechanismBeanImpl(this, -1);

      try {
         this.addAuthenticationMechanism(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAuthenticationMechanism(AuthenticationMechanismBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         AuthenticationMechanismBean[] _old = this.getAuthenticationMechanisms();
         AuthenticationMechanismBean[] _new = (AuthenticationMechanismBean[])((AuthenticationMechanismBean[])this._getHelper()._removeElement(_old, AuthenticationMechanismBean.class, param0));
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
               this.setAuthenticationMechanisms(_new);
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

   public boolean isReauthenticationSupport() {
      return this._ReauthenticationSupport;
   }

   public boolean isReauthenticationSupportInherited() {
      return false;
   }

   public boolean isReauthenticationSupportSet() {
      return this._isSet(4);
   }

   public void setReauthenticationSupport(boolean param0) {
      boolean _oldVal = this._ReauthenticationSupport;
      this._ReauthenticationSupport = param0;
      this._postSet(4, _oldVal, param0);
   }

   public ConfigPropertiesBean getProperties() {
      return this._Properties;
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(5) || this._isAnythingSet((AbstractDescriptorBean)this.getProperties());
   }

   public void setProperties(ConfigPropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 5)) {
         this._postCreate(_child);
      }

      ConfigPropertiesBean _oldVal = this._Properties;
      this._Properties = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getResAuth() {
      return this._ResAuth;
   }

   public boolean isResAuthInherited() {
      return false;
   }

   public boolean isResAuthSet() {
      return this._isSet(6);
   }

   public void setResAuth(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResAuth;
      this._ResAuth = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
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
      return super._isAnythingSet() || this.isLoggingSet() || this.isPoolParamsSet() || this.isPropertiesSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._AuthenticationMechanisms = new AuthenticationMechanismBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Logging = new LoggingBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._Logging);
               if (initOne) {
                  break;
               }
            case 0:
               this._PoolParams = new PoolParamsBeanImpl(this, 0);
               this._postCreate((AbstractDescriptorBean)this._PoolParams);
               if (initOne) {
                  break;
               }
            case 5:
               this._Properties = new ConfigPropertiesBeanImpl(this, 5);
               this._postCreate((AbstractDescriptorBean)this._Properties);
               if (initOne) {
                  break;
               }
            case 6:
               this._ResAuth = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._TransactionSupport = "NoTransaction";
               if (initOne) {
                  break;
               }
            case 4:
               this._ReauthenticationSupport = false;
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
                  return 7;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 9:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            case 23:
            default:
               break;
            case 7:
               if (s.equals("logging")) {
                  return 1;
               }
               break;
            case 8:
               if (s.equals("res-auth")) {
                  return 6;
               }
               break;
            case 10:
               if (s.equals("properties")) {
                  return 5;
               }
               break;
            case 11:
               if (s.equals("pool-params")) {
                  return 0;
               }
               break;
            case 19:
               if (s.equals("transaction-support")) {
                  return 2;
               }
               break;
            case 24:
               if (s.equals("authentication-mechanism")) {
                  return 3;
               }

               if (s.equals("reauthentication-support")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new PoolParamsBeanImpl.SchemaHelper2();
            case 1:
               return new LoggingBeanImpl.SchemaHelper2();
            case 2:
            case 4:
            default:
               return super.getSchemaHelper(propIndex);
            case 3:
               return new AuthenticationMechanismBeanImpl.SchemaHelper2();
            case 5:
               return new ConfigPropertiesBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "pool-params";
            case 1:
               return "logging";
            case 2:
               return "transaction-support";
            case 3:
               return "authentication-mechanism";
            case 4:
               return "reauthentication-support";
            case 5:
               return "properties";
            case 6:
               return "res-auth";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
            case 4:
            default:
               return super.isBean(propIndex);
            case 3:
               return true;
            case 5:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
            case 5:
            default:
               return super.isConfigurable(propIndex);
            case 4:
               return true;
            case 6:
               return true;
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private ConnectionDefinitionPropertiesBeanImpl bean;

      protected Helper(ConnectionDefinitionPropertiesBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "PoolParams";
            case 1:
               return "Logging";
            case 2:
               return "TransactionSupport";
            case 3:
               return "AuthenticationMechanisms";
            case 4:
               return "ReauthenticationSupport";
            case 5:
               return "Properties";
            case 6:
               return "ResAuth";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthenticationMechanisms")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("Logging")) {
            return 1;
         } else if (propName.equals("PoolParams")) {
            return 0;
         } else if (propName.equals("Properties")) {
            return 5;
         } else if (propName.equals("ResAuth")) {
            return 6;
         } else if (propName.equals("TransactionSupport")) {
            return 2;
         } else {
            return propName.equals("ReauthenticationSupport") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAuthenticationMechanisms()));
         if (this.bean.getLogging() != null) {
            iterators.add(new ArrayIterator(new LoggingBean[]{this.bean.getLogging()}));
         }

         if (this.bean.getPoolParams() != null) {
            iterators.add(new ArrayIterator(new PoolParamsBean[]{this.bean.getPoolParams()}));
         }

         if (this.bean.getProperties() != null) {
            iterators.add(new ArrayIterator(new ConfigPropertiesBean[]{this.bean.getProperties()}));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getAuthenticationMechanisms().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAuthenticationMechanisms()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getLogging());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPoolParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isResAuthSet()) {
               buf.append("ResAuth");
               buf.append(String.valueOf(this.bean.getResAuth()));
            }

            if (this.bean.isTransactionSupportSet()) {
               buf.append("TransactionSupport");
               buf.append(String.valueOf(this.bean.getTransactionSupport()));
            }

            if (this.bean.isReauthenticationSupportSet()) {
               buf.append("ReauthenticationSupport");
               buf.append(String.valueOf(this.bean.isReauthenticationSupport()));
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
            ConnectionDefinitionPropertiesBeanImpl otherTyped = (ConnectionDefinitionPropertiesBeanImpl)other;
            this.computeChildDiff("AuthenticationMechanisms", this.bean.getAuthenticationMechanisms(), otherTyped.getAuthenticationMechanisms(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeSubDiff("Logging", this.bean.getLogging(), otherTyped.getLogging());
            this.computeSubDiff("PoolParams", this.bean.getPoolParams(), otherTyped.getPoolParams());
            this.computeSubDiff("Properties", this.bean.getProperties(), otherTyped.getProperties());
            this.computeDiff("ResAuth", this.bean.getResAuth(), otherTyped.getResAuth(), false);
            this.computeDiff("TransactionSupport", this.bean.getTransactionSupport(), otherTyped.getTransactionSupport(), false);
            this.computeDiff("ReauthenticationSupport", this.bean.isReauthenticationSupport(), otherTyped.isReauthenticationSupport(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConnectionDefinitionPropertiesBeanImpl original = (ConnectionDefinitionPropertiesBeanImpl)event.getSourceBean();
            ConnectionDefinitionPropertiesBeanImpl proposed = (ConnectionDefinitionPropertiesBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AuthenticationMechanisms")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAuthenticationMechanism((AuthenticationMechanismBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAuthenticationMechanism((AuthenticationMechanismBean)update.getRemovedObject());
                  }

                  if (original.getAuthenticationMechanisms() == null || original.getAuthenticationMechanisms().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Logging")) {
                  if (type == 2) {
                     original.setLogging((LoggingBean)this.createCopy((AbstractDescriptorBean)proposed.getLogging()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Logging", (DescriptorBean)original.getLogging());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("PoolParams")) {
                  if (type == 2) {
                     original.setPoolParams((PoolParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getPoolParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PoolParams", (DescriptorBean)original.getPoolParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Properties")) {
                  if (type == 2) {
                     original.setProperties((ConfigPropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getProperties()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Properties", (DescriptorBean)original.getProperties());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ResAuth")) {
                  original.setResAuth(proposed.getResAuth());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("TransactionSupport")) {
                  original.setTransactionSupport(proposed.getTransactionSupport());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ReauthenticationSupport")) {
                  original.setReauthenticationSupport(proposed.isReauthenticationSupport());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            ConnectionDefinitionPropertiesBeanImpl copy = (ConnectionDefinitionPropertiesBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthenticationMechanisms")) && this.bean.isAuthenticationMechanismsSet() && !copy._isSet(3)) {
               AuthenticationMechanismBean[] oldAuthenticationMechanisms = this.bean.getAuthenticationMechanisms();
               AuthenticationMechanismBean[] newAuthenticationMechanisms = new AuthenticationMechanismBean[oldAuthenticationMechanisms.length];

               for(int i = 0; i < newAuthenticationMechanisms.length; ++i) {
                  newAuthenticationMechanisms[i] = (AuthenticationMechanismBean)((AuthenticationMechanismBean)this.createCopy((AbstractDescriptorBean)oldAuthenticationMechanisms[i], includeObsolete));
               }

               copy.setAuthenticationMechanisms(newAuthenticationMechanisms);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Logging")) && this.bean.isLoggingSet() && !copy._isSet(1)) {
               Object o = this.bean.getLogging();
               copy.setLogging((LoggingBean)null);
               copy.setLogging(o == null ? null : (LoggingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PoolParams")) && this.bean.isPoolParamsSet() && !copy._isSet(0)) {
               Object o = this.bean.getPoolParams();
               copy.setPoolParams((PoolParamsBean)null);
               copy.setPoolParams(o == null ? null : (PoolParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet() && !copy._isSet(5)) {
               Object o = this.bean.getProperties();
               copy.setProperties((ConfigPropertiesBean)null);
               copy.setProperties(o == null ? null : (ConfigPropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ResAuth")) && this.bean.isResAuthSet()) {
               copy.setResAuth(this.bean.getResAuth());
            }

            if ((excludeProps == null || !excludeProps.contains("TransactionSupport")) && this.bean.isTransactionSupportSet()) {
               copy.setTransactionSupport(this.bean.getTransactionSupport());
            }

            if ((excludeProps == null || !excludeProps.contains("ReauthenticationSupport")) && this.bean.isReauthenticationSupportSet()) {
               copy.setReauthenticationSupport(this.bean.isReauthenticationSupport());
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
         this.inferSubTree(this.bean.getAuthenticationMechanisms(), clazz, annotation);
         this.inferSubTree(this.bean.getLogging(), clazz, annotation);
         this.inferSubTree(this.bean.getPoolParams(), clazz, annotation);
         this.inferSubTree(this.bean.getProperties(), clazz, annotation);
      }
   }
}

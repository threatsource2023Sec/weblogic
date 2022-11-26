package weblogic.j2ee.descriptor;

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
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class OutboundResourceAdapterBeanImpl extends AbstractDescriptorBean implements OutboundResourceAdapterBean, Serializable {
   private AuthenticationMechanismBean[] _AuthenticationMechanisms;
   private ConnectionDefinitionBean[] _ConnectionDefinitions;
   private String _Id;
   private boolean _ReauthenticationSupport;
   private String _TransactionSupport;
   private static SchemaHelper2 _schemaHelper;

   public OutboundResourceAdapterBeanImpl() {
      this._initializeProperty(-1);
   }

   public OutboundResourceAdapterBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public OutboundResourceAdapterBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addConnectionDefinition(ConnectionDefinitionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         ConnectionDefinitionBean[] _new;
         if (this._isSet(0)) {
            _new = (ConnectionDefinitionBean[])((ConnectionDefinitionBean[])this._getHelper()._extendArray(this.getConnectionDefinitions(), ConnectionDefinitionBean.class, param0));
         } else {
            _new = new ConnectionDefinitionBean[]{param0};
         }

         try {
            this.setConnectionDefinitions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConnectionDefinitionBean[] getConnectionDefinitions() {
      return this._ConnectionDefinitions;
   }

   public boolean isConnectionDefinitionsInherited() {
      return false;
   }

   public boolean isConnectionDefinitionsSet() {
      return this._isSet(0);
   }

   public void removeConnectionDefinition(ConnectionDefinitionBean param0) {
      this.destroyConnectionDefinition(param0);
   }

   public void setConnectionDefinitions(ConnectionDefinitionBean[] param0) throws InvalidAttributeValueException {
      ConnectionDefinitionBean[] param0 = param0 == null ? new ConnectionDefinitionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConnectionDefinitionBean[] _oldVal = this._ConnectionDefinitions;
      this._ConnectionDefinitions = (ConnectionDefinitionBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public ConnectionDefinitionBean createConnectionDefinition() {
      ConnectionDefinitionBeanImpl _val = new ConnectionDefinitionBeanImpl(this, -1);

      try {
         this.addConnectionDefinition(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConnectionDefinition(ConnectionDefinitionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         ConnectionDefinitionBean[] _old = this.getConnectionDefinitions();
         ConnectionDefinitionBean[] _new = (ConnectionDefinitionBean[])((ConnectionDefinitionBean[])this._getHelper()._removeElement(_old, ConnectionDefinitionBean.class, param0));
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
               this.setConnectionDefinitions(_new);
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

   public String getTransactionSupport() {
      return this._TransactionSupport;
   }

   public boolean isTransactionSupportInherited() {
      return false;
   }

   public boolean isTransactionSupportSet() {
      return this._isSet(1);
   }

   public void setTransactionSupport(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NoTransaction", "LocalTransaction", "XATransaction"};
      param0 = LegalChecks.checkInEnum("TransactionSupport", param0, _set);
      String _oldVal = this._TransactionSupport;
      this._TransactionSupport = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addAuthenticationMechanism(AuthenticationMechanismBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         AuthenticationMechanismBean[] _new;
         if (this._isSet(2)) {
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
      return this._isSet(2);
   }

   public void removeAuthenticationMechanism(AuthenticationMechanismBean param0) {
      this.destroyAuthenticationMechanism(param0);
   }

   public void setAuthenticationMechanisms(AuthenticationMechanismBean[] param0) throws InvalidAttributeValueException {
      AuthenticationMechanismBean[] param0 = param0 == null ? new AuthenticationMechanismBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AuthenticationMechanismBean[] _oldVal = this._AuthenticationMechanisms;
      this._AuthenticationMechanisms = (AuthenticationMechanismBean[])param0;
      this._postSet(2, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 2);
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
      return this._isSet(3);
   }

   public void setReauthenticationSupport(boolean param0) {
      boolean _oldVal = this._ReauthenticationSupport;
      this._ReauthenticationSupport = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(4, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("TransactionSupport", this.isTransactionSupportSet());
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
               this._AuthenticationMechanisms = new AuthenticationMechanismBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ConnectionDefinitions = new ConnectionDefinitionBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._TransactionSupport = null;
               if (initOne) {
                  break;
               }
            case 3:
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 4;
               }
               break;
            case 19:
               if (s.equals("transaction-support")) {
                  return 1;
               }
               break;
            case 21:
               if (s.equals("connection-definition")) {
                  return 0;
               }
               break;
            case 24:
               if (s.equals("authentication-mechanism")) {
                  return 2;
               }

               if (s.equals("reauthentication-support")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new ConnectionDefinitionBeanImpl.SchemaHelper2();
            case 2:
               return new AuthenticationMechanismBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "connection-definition";
            case 1:
               return "transaction-support";
            case 2:
               return "authentication-mechanism";
            case 3:
               return "reauthentication-support";
            case 4:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private OutboundResourceAdapterBeanImpl bean;

      protected Helper(OutboundResourceAdapterBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ConnectionDefinitions";
            case 1:
               return "TransactionSupport";
            case 2:
               return "AuthenticationMechanisms";
            case 3:
               return "ReauthenticationSupport";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthenticationMechanisms")) {
            return 2;
         } else if (propName.equals("ConnectionDefinitions")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("TransactionSupport")) {
            return 1;
         } else {
            return propName.equals("ReauthenticationSupport") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAuthenticationMechanisms()));
         iterators.add(new ArrayIterator(this.bean.getConnectionDefinitions()));
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

            int i;
            for(i = 0; i < this.bean.getAuthenticationMechanisms().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAuthenticationMechanisms()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getConnectionDefinitions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConnectionDefinitions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
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
            OutboundResourceAdapterBeanImpl otherTyped = (OutboundResourceAdapterBeanImpl)other;
            this.computeChildDiff("AuthenticationMechanisms", this.bean.getAuthenticationMechanisms(), otherTyped.getAuthenticationMechanisms(), false);
            this.computeChildDiff("ConnectionDefinitions", this.bean.getConnectionDefinitions(), otherTyped.getConnectionDefinitions(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("TransactionSupport", this.bean.getTransactionSupport(), otherTyped.getTransactionSupport(), false);
            this.computeDiff("ReauthenticationSupport", this.bean.isReauthenticationSupport(), otherTyped.isReauthenticationSupport(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            OutboundResourceAdapterBeanImpl original = (OutboundResourceAdapterBeanImpl)event.getSourceBean();
            OutboundResourceAdapterBeanImpl proposed = (OutboundResourceAdapterBeanImpl)event.getProposedBean();
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
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("ConnectionDefinitions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConnectionDefinition((ConnectionDefinitionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConnectionDefinition((ConnectionDefinitionBean)update.getRemovedObject());
                  }

                  if (original.getConnectionDefinitions() == null || original.getConnectionDefinitions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("TransactionSupport")) {
                  original.setTransactionSupport(proposed.getTransactionSupport());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ReauthenticationSupport")) {
                  original.setReauthenticationSupport(proposed.isReauthenticationSupport());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            OutboundResourceAdapterBeanImpl copy = (OutboundResourceAdapterBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AuthenticationMechanisms")) && this.bean.isAuthenticationMechanismsSet() && !copy._isSet(2)) {
               AuthenticationMechanismBean[] oldAuthenticationMechanisms = this.bean.getAuthenticationMechanisms();
               AuthenticationMechanismBean[] newAuthenticationMechanisms = new AuthenticationMechanismBean[oldAuthenticationMechanisms.length];

               for(i = 0; i < newAuthenticationMechanisms.length; ++i) {
                  newAuthenticationMechanisms[i] = (AuthenticationMechanismBean)((AuthenticationMechanismBean)this.createCopy((AbstractDescriptorBean)oldAuthenticationMechanisms[i], includeObsolete));
               }

               copy.setAuthenticationMechanisms(newAuthenticationMechanisms);
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionDefinitions")) && this.bean.isConnectionDefinitionsSet() && !copy._isSet(0)) {
               ConnectionDefinitionBean[] oldConnectionDefinitions = this.bean.getConnectionDefinitions();
               ConnectionDefinitionBean[] newConnectionDefinitions = new ConnectionDefinitionBean[oldConnectionDefinitions.length];

               for(i = 0; i < newConnectionDefinitions.length; ++i) {
                  newConnectionDefinitions[i] = (ConnectionDefinitionBean)((ConnectionDefinitionBean)this.createCopy((AbstractDescriptorBean)oldConnectionDefinitions[i], includeObsolete));
               }

               copy.setConnectionDefinitions(newConnectionDefinitions);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
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
         this.inferSubTree(this.bean.getConnectionDefinitions(), clazz, annotation);
      }
   }
}

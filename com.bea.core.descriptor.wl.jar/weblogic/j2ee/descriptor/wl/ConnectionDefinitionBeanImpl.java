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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ConnectionDefinitionBeanImpl extends AbstractDescriptorBean implements ConnectionDefinitionBean, Serializable {
   private String _ConnectionFactoryInterface;
   private ConnectionInstanceBean[] _ConnectionInstances;
   private ConnectionDefinitionPropertiesBean _DefaultConnectionProperties;
   private String _Id;
   private static SchemaHelper2 _schemaHelper;

   public ConnectionDefinitionBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConnectionDefinitionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConnectionDefinitionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getConnectionFactoryInterface() {
      return this._ConnectionFactoryInterface;
   }

   public boolean isConnectionFactoryInterfaceInherited() {
      return false;
   }

   public boolean isConnectionFactoryInterfaceSet() {
      return this._isSet(0);
   }

   public void setConnectionFactoryInterface(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactoryInterface;
      this._ConnectionFactoryInterface = param0;
      this._postSet(0, _oldVal, param0);
   }

   public ConnectionDefinitionPropertiesBean getDefaultConnectionProperties() {
      return this._DefaultConnectionProperties;
   }

   public boolean isDefaultConnectionPropertiesInherited() {
      return false;
   }

   public boolean isDefaultConnectionPropertiesSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getDefaultConnectionProperties());
   }

   public void setDefaultConnectionProperties(ConnectionDefinitionPropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      ConnectionDefinitionPropertiesBean _oldVal = this._DefaultConnectionProperties;
      this._DefaultConnectionProperties = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addConnectionInstance(ConnectionInstanceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         ConnectionInstanceBean[] _new;
         if (this._isSet(2)) {
            _new = (ConnectionInstanceBean[])((ConnectionInstanceBean[])this._getHelper()._extendArray(this.getConnectionInstances(), ConnectionInstanceBean.class, param0));
         } else {
            _new = new ConnectionInstanceBean[]{param0};
         }

         try {
            this.setConnectionInstances(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConnectionInstanceBean[] getConnectionInstances() {
      return this._ConnectionInstances;
   }

   public boolean isConnectionInstancesInherited() {
      return false;
   }

   public boolean isConnectionInstancesSet() {
      return this._isSet(2);
   }

   public void removeConnectionInstance(ConnectionInstanceBean param0) {
      this.destroyConnectionInstance(param0);
   }

   public void setConnectionInstances(ConnectionInstanceBean[] param0) throws InvalidAttributeValueException {
      ConnectionInstanceBean[] param0 = param0 == null ? new ConnectionInstanceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConnectionInstanceBean[] _oldVal = this._ConnectionInstances;
      this._ConnectionInstances = (ConnectionInstanceBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public ConnectionInstanceBean createConnectionInstance() {
      ConnectionInstanceBeanImpl _val = new ConnectionInstanceBeanImpl(this, -1);

      try {
         this.addConnectionInstance(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConnectionInstance(ConnectionInstanceBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         ConnectionInstanceBean[] _old = this.getConnectionInstances();
         ConnectionInstanceBean[] _new = (ConnectionInstanceBean[])((ConnectionInstanceBean[])this._getHelper()._removeElement(_old, ConnectionInstanceBean.class, param0));
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
               this.setConnectionInstances(_new);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getConnectionFactoryInterface();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 28:
            if (s.equals("connection-factory-interface")) {
               return info.compareXpaths(this._getPropertyXpath("connection-factory-interface"));
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
      return super._isAnythingSet() || this.isDefaultConnectionPropertiesSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ConnectionFactoryInterface = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ConnectionInstances = new ConnectionInstanceBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._DefaultConnectionProperties = new ConnectionDefinitionPropertiesBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._DefaultConnectionProperties);
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
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
                  return 3;
               }
               break;
            case 19:
               if (s.equals("connection-instance")) {
                  return 2;
               }
               break;
            case 28:
               if (s.equals("connection-factory-interface")) {
                  return 0;
               }
               break;
            case 29:
               if (s.equals("default-connection-properties")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ConnectionDefinitionPropertiesBeanImpl.SchemaHelper2();
            case 2:
               return new ConnectionInstanceBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "connection-factory-interface";
            case 1:
               return "default-connection-properties";
            case 2:
               return "connection-instance";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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
         indices.add("connection-factory-interface");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ConnectionDefinitionBeanImpl bean;

      protected Helper(ConnectionDefinitionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ConnectionFactoryInterface";
            case 1:
               return "DefaultConnectionProperties";
            case 2:
               return "ConnectionInstances";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactoryInterface")) {
            return 0;
         } else if (propName.equals("ConnectionInstances")) {
            return 2;
         } else if (propName.equals("DefaultConnectionProperties")) {
            return 1;
         } else {
            return propName.equals("Id") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConnectionInstances()));
         if (this.bean.getDefaultConnectionProperties() != null) {
            iterators.add(new ArrayIterator(new ConnectionDefinitionPropertiesBean[]{this.bean.getDefaultConnectionProperties()}));
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
            if (this.bean.isConnectionFactoryInterfaceSet()) {
               buf.append("ConnectionFactoryInterface");
               buf.append(String.valueOf(this.bean.getConnectionFactoryInterface()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getConnectionInstances().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConnectionInstances()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getDefaultConnectionProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
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
            ConnectionDefinitionBeanImpl otherTyped = (ConnectionDefinitionBeanImpl)other;
            this.computeDiff("ConnectionFactoryInterface", this.bean.getConnectionFactoryInterface(), otherTyped.getConnectionFactoryInterface(), false);
            this.computeChildDiff("ConnectionInstances", this.bean.getConnectionInstances(), otherTyped.getConnectionInstances(), false);
            this.computeSubDiff("DefaultConnectionProperties", this.bean.getDefaultConnectionProperties(), otherTyped.getDefaultConnectionProperties());
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConnectionDefinitionBeanImpl original = (ConnectionDefinitionBeanImpl)event.getSourceBean();
            ConnectionDefinitionBeanImpl proposed = (ConnectionDefinitionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionFactoryInterface")) {
                  original.setConnectionFactoryInterface(proposed.getConnectionFactoryInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ConnectionInstances")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConnectionInstance((ConnectionInstanceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConnectionInstance((ConnectionInstanceBean)update.getRemovedObject());
                  }

                  if (original.getConnectionInstances() == null || original.getConnectionInstances().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("DefaultConnectionProperties")) {
                  if (type == 2) {
                     original.setDefaultConnectionProperties((ConnectionDefinitionPropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getDefaultConnectionProperties()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DefaultConnectionProperties", (DescriptorBean)original.getDefaultConnectionProperties());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
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
            ConnectionDefinitionBeanImpl copy = (ConnectionDefinitionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryInterface")) && this.bean.isConnectionFactoryInterfaceSet()) {
               copy.setConnectionFactoryInterface(this.bean.getConnectionFactoryInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionInstances")) && this.bean.isConnectionInstancesSet() && !copy._isSet(2)) {
               ConnectionInstanceBean[] oldConnectionInstances = this.bean.getConnectionInstances();
               ConnectionInstanceBean[] newConnectionInstances = new ConnectionInstanceBean[oldConnectionInstances.length];

               for(int i = 0; i < newConnectionInstances.length; ++i) {
                  newConnectionInstances[i] = (ConnectionInstanceBean)((ConnectionInstanceBean)this.createCopy((AbstractDescriptorBean)oldConnectionInstances[i], includeObsolete));
               }

               copy.setConnectionInstances(newConnectionInstances);
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultConnectionProperties")) && this.bean.isDefaultConnectionPropertiesSet() && !copy._isSet(1)) {
               Object o = this.bean.getDefaultConnectionProperties();
               copy.setDefaultConnectionProperties((ConnectionDefinitionPropertiesBean)null);
               copy.setDefaultConnectionProperties(o == null ? null : (ConnectionDefinitionPropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
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
         this.inferSubTree(this.bean.getConnectionInstances(), clazz, annotation);
         this.inferSubTree(this.bean.getDefaultConnectionProperties(), clazz, annotation);
      }
   }
}

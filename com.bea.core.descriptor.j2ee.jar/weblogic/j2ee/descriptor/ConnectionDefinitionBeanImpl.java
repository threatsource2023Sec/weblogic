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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ConnectionDefinitionBeanImpl extends AbstractDescriptorBean implements ConnectionDefinitionBean, Serializable {
   private ConfigPropertyBean[] _ConfigProperties;
   private String _ConnectionFactoryImplClass;
   private String _ConnectionFactoryInterface;
   private String _ConnectionImplClass;
   private String _ConnectionInterface;
   private String _Id;
   private String _ManagedConnectionFactoryClass;
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

   public String getManagedConnectionFactoryClass() {
      return this._ManagedConnectionFactoryClass;
   }

   public boolean isManagedConnectionFactoryClassInherited() {
      return false;
   }

   public boolean isManagedConnectionFactoryClassSet() {
      return this._isSet(0);
   }

   public void setManagedConnectionFactoryClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ManagedConnectionFactoryClass;
      this._ManagedConnectionFactoryClass = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addConfigProperty(ConfigPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         ConfigPropertyBean[] _new;
         if (this._isSet(1)) {
            _new = (ConfigPropertyBean[])((ConfigPropertyBean[])this._getHelper()._extendArray(this.getConfigProperties(), ConfigPropertyBean.class, param0));
         } else {
            _new = new ConfigPropertyBean[]{param0};
         }

         try {
            this.setConfigProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ConfigPropertyBean[] getConfigProperties() {
      return this._ConfigProperties;
   }

   public boolean isConfigPropertiesInherited() {
      return false;
   }

   public boolean isConfigPropertiesSet() {
      return this._isSet(1);
   }

   public void removeConfigProperty(ConfigPropertyBean param0) {
      this.destroyConfigProperty(param0);
   }

   public void setConfigProperties(ConfigPropertyBean[] param0) throws InvalidAttributeValueException {
      ConfigPropertyBean[] param0 = param0 == null ? new ConfigPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ConfigPropertyBean[] _oldVal = this._ConfigProperties;
      this._ConfigProperties = (ConfigPropertyBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public ConfigPropertyBean createConfigProperty() {
      ConfigPropertyBeanImpl _val = new ConfigPropertyBeanImpl(this, -1);

      try {
         this.addConfigProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConfigProperty(ConfigPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         ConfigPropertyBean[] _old = this.getConfigProperties();
         ConfigPropertyBean[] _new = (ConfigPropertyBean[])((ConfigPropertyBean[])this._getHelper()._removeElement(_old, ConfigPropertyBean.class, param0));
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
               this.setConfigProperties(_new);
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

   public String getConnectionFactoryInterface() {
      return this._ConnectionFactoryInterface;
   }

   public boolean isConnectionFactoryInterfaceInherited() {
      return false;
   }

   public boolean isConnectionFactoryInterfaceSet() {
      return this._isSet(2);
   }

   public void setConnectionFactoryInterface(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactoryInterface;
      this._ConnectionFactoryInterface = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getConnectionFactoryImplClass() {
      return this._ConnectionFactoryImplClass;
   }

   public boolean isConnectionFactoryImplClassInherited() {
      return false;
   }

   public boolean isConnectionFactoryImplClassSet() {
      return this._isSet(3);
   }

   public void setConnectionFactoryImplClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionFactoryImplClass;
      this._ConnectionFactoryImplClass = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getConnectionInterface() {
      return this._ConnectionInterface;
   }

   public boolean isConnectionInterfaceInherited() {
      return false;
   }

   public boolean isConnectionInterfaceSet() {
      return this._isSet(4);
   }

   public void setConnectionInterface(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionInterface;
      this._ConnectionInterface = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getConnectionImplClass() {
      return this._ConnectionImplClass;
   }

   public boolean isConnectionImplClassInherited() {
      return false;
   }

   public boolean isConnectionImplClassSet() {
      return this._isSet(5);
   }

   public void setConnectionImplClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ConnectionImplClass;
      this._ConnectionImplClass = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(6);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(6, _oldVal, param0);
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
         case 27:
            if (s.equals("connectionfactory-interface")) {
               return info.compareXpaths(this._getPropertyXpath("connectionfactory-interface"));
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._ConfigProperties = new ConfigPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._ConnectionFactoryImplClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ConnectionFactoryInterface = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._ConnectionImplClass = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ConnectionInterface = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ManagedConnectionFactoryClass = null;
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
                  return 6;
               }
               break;
            case 15:
               if (s.equals("config-property")) {
                  return 1;
               }
               break;
            case 20:
               if (s.equals("connection-interface")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("connection-impl-class")) {
                  return 5;
               }
               break;
            case 27:
               if (s.equals("connectionfactory-interface")) {
                  return 2;
               }
               break;
            case 28:
               if (s.equals("connectionfactory-impl-class")) {
                  return 3;
               }
               break;
            case 30:
               if (s.equals("managedconnectionfactory-class")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ConfigPropertyBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "managedconnectionfactory-class";
            case 1:
               return "config-property";
            case 2:
               return "connectionfactory-interface";
            case 3:
               return "connectionfactory-impl-class";
            case 4:
               return "connection-interface";
            case 5:
               return "connection-impl-class";
            case 6:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("connectionfactory-interface");
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
               return "ManagedConnectionFactoryClass";
            case 1:
               return "ConfigProperties";
            case 2:
               return "ConnectionFactoryInterface";
            case 3:
               return "ConnectionFactoryImplClass";
            case 4:
               return "ConnectionInterface";
            case 5:
               return "ConnectionImplClass";
            case 6:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConfigProperties")) {
            return 1;
         } else if (propName.equals("ConnectionFactoryImplClass")) {
            return 3;
         } else if (propName.equals("ConnectionFactoryInterface")) {
            return 2;
         } else if (propName.equals("ConnectionImplClass")) {
            return 5;
         } else if (propName.equals("ConnectionInterface")) {
            return 4;
         } else if (propName.equals("Id")) {
            return 6;
         } else {
            return propName.equals("ManagedConnectionFactoryClass") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getConfigProperties()));
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

            for(int i = 0; i < this.bean.getConfigProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getConfigProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isConnectionFactoryImplClassSet()) {
               buf.append("ConnectionFactoryImplClass");
               buf.append(String.valueOf(this.bean.getConnectionFactoryImplClass()));
            }

            if (this.bean.isConnectionFactoryInterfaceSet()) {
               buf.append("ConnectionFactoryInterface");
               buf.append(String.valueOf(this.bean.getConnectionFactoryInterface()));
            }

            if (this.bean.isConnectionImplClassSet()) {
               buf.append("ConnectionImplClass");
               buf.append(String.valueOf(this.bean.getConnectionImplClass()));
            }

            if (this.bean.isConnectionInterfaceSet()) {
               buf.append("ConnectionInterface");
               buf.append(String.valueOf(this.bean.getConnectionInterface()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isManagedConnectionFactoryClassSet()) {
               buf.append("ManagedConnectionFactoryClass");
               buf.append(String.valueOf(this.bean.getManagedConnectionFactoryClass()));
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
            this.computeChildDiff("ConfigProperties", this.bean.getConfigProperties(), otherTyped.getConfigProperties(), false);
            this.computeDiff("ConnectionFactoryImplClass", this.bean.getConnectionFactoryImplClass(), otherTyped.getConnectionFactoryImplClass(), false);
            this.computeDiff("ConnectionFactoryInterface", this.bean.getConnectionFactoryInterface(), otherTyped.getConnectionFactoryInterface(), false);
            this.computeDiff("ConnectionImplClass", this.bean.getConnectionImplClass(), otherTyped.getConnectionImplClass(), false);
            this.computeDiff("ConnectionInterface", this.bean.getConnectionInterface(), otherTyped.getConnectionInterface(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("ManagedConnectionFactoryClass", this.bean.getManagedConnectionFactoryClass(), otherTyped.getManagedConnectionFactoryClass(), false);
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
               if (prop.equals("ConfigProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addConfigProperty((ConfigPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeConfigProperty((ConfigPropertyBean)update.getRemovedObject());
                  }

                  if (original.getConfigProperties() == null || original.getConfigProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("ConnectionFactoryImplClass")) {
                  original.setConnectionFactoryImplClass(proposed.getConnectionFactoryImplClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ConnectionFactoryInterface")) {
                  original.setConnectionFactoryInterface(proposed.getConnectionFactoryInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ConnectionImplClass")) {
                  original.setConnectionImplClass(proposed.getConnectionImplClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ConnectionInterface")) {
                  original.setConnectionInterface(proposed.getConnectionInterface());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ManagedConnectionFactoryClass")) {
                  original.setManagedConnectionFactoryClass(proposed.getManagedConnectionFactoryClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            if ((excludeProps == null || !excludeProps.contains("ConfigProperties")) && this.bean.isConfigPropertiesSet() && !copy._isSet(1)) {
               ConfigPropertyBean[] oldConfigProperties = this.bean.getConfigProperties();
               ConfigPropertyBean[] newConfigProperties = new ConfigPropertyBean[oldConfigProperties.length];

               for(int i = 0; i < newConfigProperties.length; ++i) {
                  newConfigProperties[i] = (ConfigPropertyBean)((ConfigPropertyBean)this.createCopy((AbstractDescriptorBean)oldConfigProperties[i], includeObsolete));
               }

               copy.setConfigProperties(newConfigProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryImplClass")) && this.bean.isConnectionFactoryImplClassSet()) {
               copy.setConnectionFactoryImplClass(this.bean.getConnectionFactoryImplClass());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryInterface")) && this.bean.isConnectionFactoryInterfaceSet()) {
               copy.setConnectionFactoryInterface(this.bean.getConnectionFactoryInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionImplClass")) && this.bean.isConnectionImplClassSet()) {
               copy.setConnectionImplClass(this.bean.getConnectionImplClass());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionInterface")) && this.bean.isConnectionInterfaceSet()) {
               copy.setConnectionInterface(this.bean.getConnectionInterface());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedConnectionFactoryClass")) && this.bean.isManagedConnectionFactoryClassSet()) {
               copy.setManagedConnectionFactoryClass(this.bean.getManagedConnectionFactoryClass());
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
         this.inferSubTree(this.bean.getConfigProperties(), clazz, annotation);
      }
   }
}

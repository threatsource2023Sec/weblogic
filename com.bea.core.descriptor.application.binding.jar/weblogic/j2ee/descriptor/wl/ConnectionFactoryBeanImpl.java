package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
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

public class ConnectionFactoryBeanImpl extends AbstractDescriptorBean implements ConnectionFactoryBean, Serializable {
   private ConnectionPropertiesBean _ConnectionProperties;
   private String _FactoryName;
   private static SchemaHelper2 _schemaHelper;

   public ConnectionFactoryBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConnectionFactoryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConnectionFactoryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getFactoryName() {
      return this._FactoryName;
   }

   public boolean isFactoryNameInherited() {
      return false;
   }

   public boolean isFactoryNameSet() {
      return this._isSet(0);
   }

   public void setFactoryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FactoryName;
      this._FactoryName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public ConnectionPropertiesBean getConnectionProperties() {
      return this._ConnectionProperties;
   }

   public boolean isConnectionPropertiesInherited() {
      return false;
   }

   public boolean isConnectionPropertiesSet() {
      return this._isSet(1);
   }

   public void setConnectionProperties(ConnectionPropertiesBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getConnectionProperties() != null && param0 != this.getConnectionProperties()) {
         throw new BeanAlreadyExistsException(this.getConnectionProperties() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ConnectionPropertiesBean _oldVal = this._ConnectionProperties;
         this._ConnectionProperties = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public ConnectionPropertiesBean createConnectionProperties() {
      ConnectionPropertiesBeanImpl _val = new ConnectionPropertiesBeanImpl(this, -1);

      try {
         this.setConnectionProperties(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConnectionProperties(ConnectionPropertiesBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ConnectionProperties;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setConnectionProperties((ConnectionPropertiesBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
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
               this._ConnectionProperties = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._FactoryName = null;
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
            case 12:
               if (s.equals("factory-name")) {
                  return 0;
               }
               break;
            case 21:
               if (s.equals("connection-properties")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ConnectionPropertiesBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "factory-name";
            case 1:
               return "connection-properties";
            default:
               return super.getElementName(propIndex);
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
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ConnectionFactoryBeanImpl bean;

      protected Helper(ConnectionFactoryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "FactoryName";
            case 1:
               return "ConnectionProperties";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionProperties")) {
            return 1;
         } else {
            return propName.equals("FactoryName") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getConnectionProperties() != null) {
            iterators.add(new ArrayIterator(new ConnectionPropertiesBean[]{this.bean.getConnectionProperties()}));
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
            childValue = this.computeChildHashValue(this.bean.getConnectionProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isFactoryNameSet()) {
               buf.append("FactoryName");
               buf.append(String.valueOf(this.bean.getFactoryName()));
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
            ConnectionFactoryBeanImpl otherTyped = (ConnectionFactoryBeanImpl)other;
            this.computeChildDiff("ConnectionProperties", this.bean.getConnectionProperties(), otherTyped.getConnectionProperties(), false);
            this.computeDiff("FactoryName", this.bean.getFactoryName(), otherTyped.getFactoryName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConnectionFactoryBeanImpl original = (ConnectionFactoryBeanImpl)event.getSourceBean();
            ConnectionFactoryBeanImpl proposed = (ConnectionFactoryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionProperties")) {
                  if (type == 2) {
                     original.setConnectionProperties((ConnectionPropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getConnectionProperties()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ConnectionProperties", (DescriptorBean)original.getConnectionProperties());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("FactoryName")) {
                  original.setFactoryName(proposed.getFactoryName());
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
            ConnectionFactoryBeanImpl copy = (ConnectionFactoryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionProperties")) && this.bean.isConnectionPropertiesSet() && !copy._isSet(1)) {
               Object o = this.bean.getConnectionProperties();
               copy.setConnectionProperties((ConnectionPropertiesBean)null);
               copy.setConnectionProperties(o == null ? null : (ConnectionPropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FactoryName")) && this.bean.isFactoryNameSet()) {
               copy.setFactoryName(this.bean.getFactoryName());
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
         this.inferSubTree(this.bean.getConnectionProperties(), clazz, annotation);
      }
   }
}

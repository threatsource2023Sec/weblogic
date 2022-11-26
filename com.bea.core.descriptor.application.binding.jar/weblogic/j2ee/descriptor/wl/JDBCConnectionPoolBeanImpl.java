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

public class JDBCConnectionPoolBeanImpl extends AbstractDescriptorBean implements JDBCConnectionPoolBean, Serializable {
   private String _AclName;
   private ConnectionFactoryBean _ConnectionFactory;
   private String _DataSourceJNDIName;
   private DriverParamsBean _DriverParams;
   private ApplicationPoolParamsBean _PoolParams;
   private static SchemaHelper2 _schemaHelper;

   public JDBCConnectionPoolBeanImpl() {
      this._initializeProperty(-1);
   }

   public JDBCConnectionPoolBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JDBCConnectionPoolBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDataSourceJNDIName() {
      return this._DataSourceJNDIName;
   }

   public boolean isDataSourceJNDINameInherited() {
      return false;
   }

   public boolean isDataSourceJNDINameSet() {
      return this._isSet(0);
   }

   public void setDataSourceJNDIName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DataSourceJNDIName;
      this._DataSourceJNDIName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public ConnectionFactoryBean getConnectionFactory() {
      return this._ConnectionFactory;
   }

   public boolean isConnectionFactoryInherited() {
      return false;
   }

   public boolean isConnectionFactorySet() {
      return this._isSet(1);
   }

   public void setConnectionFactory(ConnectionFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getConnectionFactory() != null && param0 != this.getConnectionFactory()) {
         throw new BeanAlreadyExistsException(this.getConnectionFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ConnectionFactoryBean _oldVal = this._ConnectionFactory;
         this._ConnectionFactory = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public ConnectionFactoryBean createConnectionFactory() {
      ConnectionFactoryBeanImpl _val = new ConnectionFactoryBeanImpl(this, -1);

      try {
         this.setConnectionFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConnectionFactory(ConnectionFactoryBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ConnectionFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setConnectionFactory((ConnectionFactoryBean)null);
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

   public ApplicationPoolParamsBean getPoolParams() {
      return this._PoolParams;
   }

   public boolean isPoolParamsInherited() {
      return false;
   }

   public boolean isPoolParamsSet() {
      return this._isSet(2);
   }

   public void setPoolParams(ApplicationPoolParamsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getPoolParams() != null && param0 != this.getPoolParams()) {
         throw new BeanAlreadyExistsException(this.getPoolParams() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ApplicationPoolParamsBean _oldVal = this._PoolParams;
         this._PoolParams = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public ApplicationPoolParamsBean createPoolParams() {
      ApplicationPoolParamsBeanImpl _val = new ApplicationPoolParamsBeanImpl(this, -1);

      try {
         this.setPoolParams(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPoolParams(ApplicationPoolParamsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._PoolParams;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setPoolParams((ApplicationPoolParamsBean)null);
               this._unSet(2);
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

   public DriverParamsBean getDriverParams() {
      return this._DriverParams;
   }

   public boolean isDriverParamsInherited() {
      return false;
   }

   public boolean isDriverParamsSet() {
      return this._isSet(3);
   }

   public void setDriverParams(DriverParamsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getDriverParams() != null && param0 != this.getDriverParams()) {
         throw new BeanAlreadyExistsException(this.getDriverParams() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         DriverParamsBean _oldVal = this._DriverParams;
         this._DriverParams = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public DriverParamsBean createDriverParams() {
      DriverParamsBeanImpl _val = new DriverParamsBeanImpl(this, -1);

      try {
         this.setDriverParams(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyDriverParams(DriverParamsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._DriverParams;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setDriverParams((DriverParamsBean)null);
               this._unSet(3);
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

   public String getAclName() {
      return this._AclName;
   }

   public boolean isAclNameInherited() {
      return false;
   }

   public boolean isAclNameSet() {
      return this._isSet(4);
   }

   public void setAclName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AclName;
      this._AclName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getDataSourceJNDIName();
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
         case 21:
            if (s.equals("data-source-jndi-name")) {
               return info.compareXpaths(this._getPropertyXpath("data-source-jndi-name"));
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._AclName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ConnectionFactory = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._DataSourceJNDIName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._DriverParams = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._PoolParams = null;
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
            case 8:
               if (s.equals("acl-name")) {
                  return 4;
               }
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            default:
               break;
            case 11:
               if (s.equals("pool-params")) {
                  return 2;
               }
               break;
            case 13:
               if (s.equals("driver-params")) {
                  return 3;
               }
               break;
            case 18:
               if (s.equals("connection-factory")) {
                  return 1;
               }
               break;
            case 21:
               if (s.equals("data-source-jndi-name")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ConnectionFactoryBeanImpl.SchemaHelper2();
            case 2:
               return new ApplicationPoolParamsBeanImpl.SchemaHelper2();
            case 3:
               return new DriverParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "data-source-jndi-name";
            case 1:
               return "connection-factory";
            case 2:
               return "pool-params";
            case 3:
               return "driver-params";
            case 4:
               return "acl-name";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
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
         indices.add("data-source-jndi-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JDBCConnectionPoolBeanImpl bean;

      protected Helper(JDBCConnectionPoolBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DataSourceJNDIName";
            case 1:
               return "ConnectionFactory";
            case 2:
               return "PoolParams";
            case 3:
               return "DriverParams";
            case 4:
               return "AclName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AclName")) {
            return 4;
         } else if (propName.equals("ConnectionFactory")) {
            return 1;
         } else if (propName.equals("DataSourceJNDIName")) {
            return 0;
         } else if (propName.equals("DriverParams")) {
            return 3;
         } else {
            return propName.equals("PoolParams") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getConnectionFactory() != null) {
            iterators.add(new ArrayIterator(new ConnectionFactoryBean[]{this.bean.getConnectionFactory()}));
         }

         if (this.bean.getDriverParams() != null) {
            iterators.add(new ArrayIterator(new DriverParamsBean[]{this.bean.getDriverParams()}));
         }

         if (this.bean.getPoolParams() != null) {
            iterators.add(new ArrayIterator(new ApplicationPoolParamsBean[]{this.bean.getPoolParams()}));
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
            if (this.bean.isAclNameSet()) {
               buf.append("AclName");
               buf.append(String.valueOf(this.bean.getAclName()));
            }

            childValue = this.computeChildHashValue(this.bean.getConnectionFactory());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDataSourceJNDINameSet()) {
               buf.append("DataSourceJNDIName");
               buf.append(String.valueOf(this.bean.getDataSourceJNDIName()));
            }

            childValue = this.computeChildHashValue(this.bean.getDriverParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getPoolParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            JDBCConnectionPoolBeanImpl otherTyped = (JDBCConnectionPoolBeanImpl)other;
            this.computeDiff("AclName", this.bean.getAclName(), otherTyped.getAclName(), false);
            this.computeChildDiff("ConnectionFactory", this.bean.getConnectionFactory(), otherTyped.getConnectionFactory(), false);
            this.computeDiff("DataSourceJNDIName", this.bean.getDataSourceJNDIName(), otherTyped.getDataSourceJNDIName(), false);
            this.computeChildDiff("DriverParams", this.bean.getDriverParams(), otherTyped.getDriverParams(), false);
            this.computeChildDiff("PoolParams", this.bean.getPoolParams(), otherTyped.getPoolParams(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCConnectionPoolBeanImpl original = (JDBCConnectionPoolBeanImpl)event.getSourceBean();
            JDBCConnectionPoolBeanImpl proposed = (JDBCConnectionPoolBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AclName")) {
                  original.setAclName(proposed.getAclName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ConnectionFactory")) {
                  if (type == 2) {
                     original.setConnectionFactory((ConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getConnectionFactory()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ConnectionFactory", (DescriptorBean)original.getConnectionFactory());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("DataSourceJNDIName")) {
                  original.setDataSourceJNDIName(proposed.getDataSourceJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("DriverParams")) {
                  if (type == 2) {
                     original.setDriverParams((DriverParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getDriverParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DriverParams", (DescriptorBean)original.getDriverParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("PoolParams")) {
                  if (type == 2) {
                     original.setPoolParams((ApplicationPoolParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getPoolParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("PoolParams", (DescriptorBean)original.getPoolParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            JDBCConnectionPoolBeanImpl copy = (JDBCConnectionPoolBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AclName")) && this.bean.isAclNameSet()) {
               copy.setAclName(this.bean.getAclName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionFactory")) && this.bean.isConnectionFactorySet() && !copy._isSet(1)) {
               Object o = this.bean.getConnectionFactory();
               copy.setConnectionFactory((ConnectionFactoryBean)null);
               copy.setConnectionFactory(o == null ? null : (ConnectionFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DataSourceJNDIName")) && this.bean.isDataSourceJNDINameSet()) {
               copy.setDataSourceJNDIName(this.bean.getDataSourceJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("DriverParams")) && this.bean.isDriverParamsSet() && !copy._isSet(3)) {
               Object o = this.bean.getDriverParams();
               copy.setDriverParams((DriverParamsBean)null);
               copy.setDriverParams(o == null ? null : (DriverParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("PoolParams")) && this.bean.isPoolParamsSet() && !copy._isSet(2)) {
               Object o = this.bean.getPoolParams();
               copy.setPoolParams((ApplicationPoolParamsBean)null);
               copy.setPoolParams(o == null ? null : (ApplicationPoolParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getConnectionFactory(), clazz, annotation);
         this.inferSubTree(this.bean.getDriverParams(), clazz, annotation);
         this.inferSubTree(this.bean.getPoolParams(), clazz, annotation);
      }
   }
}

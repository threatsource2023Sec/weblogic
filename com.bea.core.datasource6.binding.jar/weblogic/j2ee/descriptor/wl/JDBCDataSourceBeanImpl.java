package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
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

public class JDBCDataSourceBeanImpl extends SettableBeanImpl implements JDBCDataSourceBean, Serializable {
   private String _DatasourceType;
   private long _Id;
   private JDBCPropertiesBean _InternalProperties;
   private JDBCConnectionPoolParamsBean _JDBCConnectionPoolParams;
   private JDBCDataSourceParamsBean _JDBCDataSourceParams;
   private JDBCDriverParamsBean _JDBCDriverParams;
   private JDBCOracleParamsBean _JDBCOracleParams;
   private JDBCXAParamsBean _JDBCXAParams;
   private String _Name;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public JDBCDataSourceBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public JDBCDataSourceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public JDBCDataSourceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDatasourceType() {
      return !this._isSet(1) ? null : this._DatasourceType;
   }

   public boolean isDatasourceTypeInherited() {
      return false;
   }

   public boolean isDatasourceTypeSet() {
      return this._isSet(1);
   }

   public void setDatasourceType(String param0) {
      if (param0 == null) {
         this._unSet(1);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"GENERIC", "MDS", "AGL", "UCP", "PROXY"};
         param0 = LegalChecks.checkInEnum("DatasourceType", param0, _set);
         String _oldVal = this._DatasourceType;
         this._DatasourceType = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public JDBCPropertiesBean getInternalProperties() {
      return this._InternalProperties;
   }

   public boolean isInternalPropertiesInherited() {
      return false;
   }

   public boolean isInternalPropertiesSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getInternalProperties());
   }

   public void setInternalProperties(JDBCPropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      JDBCPropertiesBean _oldVal = this._InternalProperties;
      this._InternalProperties = param0;
      this._postSet(2, _oldVal, param0);
   }

   public JDBCDriverParamsBean getJDBCDriverParams() {
      return this._JDBCDriverParams;
   }

   public boolean isJDBCDriverParamsInherited() {
      return false;
   }

   public boolean isJDBCDriverParamsSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getJDBCDriverParams());
   }

   public void setJDBCDriverParams(JDBCDriverParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      JDBCDriverParamsBean _oldVal = this._JDBCDriverParams;
      this._JDBCDriverParams = param0;
      this._postSet(3, _oldVal, param0);
   }

   public JDBCConnectionPoolParamsBean getJDBCConnectionPoolParams() {
      return this._JDBCConnectionPoolParams;
   }

   public boolean isJDBCConnectionPoolParamsInherited() {
      return false;
   }

   public boolean isJDBCConnectionPoolParamsSet() {
      return this._isSet(4) || this._isAnythingSet((AbstractDescriptorBean)this.getJDBCConnectionPoolParams());
   }

   public void setJDBCConnectionPoolParams(JDBCConnectionPoolParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 4)) {
         this._postCreate(_child);
      }

      JDBCConnectionPoolParamsBean _oldVal = this._JDBCConnectionPoolParams;
      this._JDBCConnectionPoolParams = param0;
      this._postSet(4, _oldVal, param0);
   }

   public JDBCDataSourceParamsBean getJDBCDataSourceParams() {
      return this._JDBCDataSourceParams;
   }

   public boolean isJDBCDataSourceParamsInherited() {
      return false;
   }

   public boolean isJDBCDataSourceParamsSet() {
      return this._isSet(5) || this._isAnythingSet((AbstractDescriptorBean)this.getJDBCDataSourceParams());
   }

   public void setJDBCDataSourceParams(JDBCDataSourceParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 5)) {
         this._postCreate(_child);
      }

      JDBCDataSourceParamsBean _oldVal = this._JDBCDataSourceParams;
      this._JDBCDataSourceParams = param0;
      this._postSet(5, _oldVal, param0);
   }

   public JDBCXAParamsBean getJDBCXAParams() {
      return this._JDBCXAParams;
   }

   public boolean isJDBCXAParamsInherited() {
      return false;
   }

   public boolean isJDBCXAParamsSet() {
      return this._isSet(6) || this._isAnythingSet((AbstractDescriptorBean)this.getJDBCXAParams());
   }

   public void setJDBCXAParams(JDBCXAParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 6)) {
         this._postCreate(_child);
      }

      JDBCXAParamsBean _oldVal = this._JDBCXAParams;
      this._JDBCXAParams = param0;
      this._postSet(6, _oldVal, param0);
   }

   public JDBCOracleParamsBean getJDBCOracleParams() {
      return this._JDBCOracleParams;
   }

   public boolean isJDBCOracleParamsInherited() {
      return false;
   }

   public boolean isJDBCOracleParamsSet() {
      return this._isSet(7) || this._isAnythingSet((AbstractDescriptorBean)this.getJDBCOracleParams());
   }

   public void setJDBCOracleParams(JDBCOracleParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 7)) {
         this._postCreate(_child);
      }

      JDBCOracleParamsBean _oldVal = this._JDBCOracleParams;
      this._JDBCOracleParams = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(8);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(8, _oldVal, param0);
   }

   public long getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(9);
   }

   public void setId(long param0) throws InvalidAttributeValueException {
      long _oldVal = this._Id;
      this._Id = param0;
      this._postSet(9, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      JDBCValidator.validateJDBCDataSource(this);
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
      return super._isAnythingSet() || this.isInternalPropertiesSet() || this.isJDBCConnectionPoolParamsSet() || this.isJDBCDataSourceParamsSet() || this.isJDBCDriverParamsSet() || this.isJDBCOracleParamsSet() || this.isJDBCXAParamsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._DatasourceType = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._Id = 0L;
               if (initOne) {
                  break;
               }
            case 2:
               this._InternalProperties = new JDBCPropertiesBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._InternalProperties);
               if (initOne) {
                  break;
               }
            case 4:
               this._JDBCConnectionPoolParams = new JDBCConnectionPoolParamsBeanImpl(this, 4);
               this._postCreate((AbstractDescriptorBean)this._JDBCConnectionPoolParams);
               if (initOne) {
                  break;
               }
            case 5:
               this._JDBCDataSourceParams = new JDBCDataSourceParamsBeanImpl(this, 5);
               this._postCreate((AbstractDescriptorBean)this._JDBCDataSourceParams);
               if (initOne) {
                  break;
               }
            case 3:
               this._JDBCDriverParams = new JDBCDriverParamsBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._JDBCDriverParams);
               if (initOne) {
                  break;
               }
            case 7:
               this._JDBCOracleParams = new JDBCOracleParamsBeanImpl(this, 7);
               this._postCreate((AbstractDescriptorBean)this._JDBCOracleParams);
               if (initOne) {
                  break;
               }
            case 6:
               this._JDBCXAParams = new JDBCXAParamsBeanImpl(this, 6);
               this._postCreate((AbstractDescriptorBean)this._JDBCXAParams);
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._Version = null;
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
      return "http://xmlns.oracle.com/weblogic/jdbc-data-source/1.5/jdbc-data-source.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/jdbc-data-source";
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
                  return 9;
               }
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
            case 17:
            case 20:
            case 21:
            case 22:
            case 24:
            case 25:
            case 26:
            default:
               break;
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 7:
               if (s.equals("version")) {
                  return 8;
               }
               break;
            case 14:
               if (s.equals("jdbc-xa-params")) {
                  return 6;
               }
               break;
            case 15:
               if (s.equals("datasource-type")) {
                  return 1;
               }
               break;
            case 18:
               if (s.equals("jdbc-driver-params")) {
                  return 3;
               }

               if (s.equals("jdbc-oracle-params")) {
                  return 7;
               }
               break;
            case 19:
               if (s.equals("internal-properties")) {
                  return 2;
               }
               break;
            case 23:
               if (s.equals("jdbc-data-source-params")) {
                  return 5;
               }
               break;
            case 27:
               if (s.equals("jdbc-connection-pool-params")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new JDBCPropertiesBeanImpl.SchemaHelper2();
            case 3:
               return new JDBCDriverParamsBeanImpl.SchemaHelper2();
            case 4:
               return new JDBCConnectionPoolParamsBeanImpl.SchemaHelper2();
            case 5:
               return new JDBCDataSourceParamsBeanImpl.SchemaHelper2();
            case 6:
               return new JDBCXAParamsBeanImpl.SchemaHelper2();
            case 7:
               return new JDBCOracleParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "jdbc-data-source";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "name";
            case 1:
               return "datasource-type";
            case 2:
               return "internal-properties";
            case 3:
               return "jdbc-driver-params";
            case 4:
               return "jdbc-connection-pool-params";
            case 5:
               return "jdbc-data-source-params";
            case 6:
               return "jdbc-xa-params";
            case 7:
               return "jdbc-oracle-params";
            case 8:
               return "version";
            case 9:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
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
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private JDBCDataSourceBeanImpl bean;

      protected Helper(JDBCDataSourceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "DatasourceType";
            case 2:
               return "InternalProperties";
            case 3:
               return "JDBCDriverParams";
            case 4:
               return "JDBCConnectionPoolParams";
            case 5:
               return "JDBCDataSourceParams";
            case 6:
               return "JDBCXAParams";
            case 7:
               return "JDBCOracleParams";
            case 8:
               return "Version";
            case 9:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DatasourceType")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 9;
         } else if (propName.equals("InternalProperties")) {
            return 2;
         } else if (propName.equals("JDBCConnectionPoolParams")) {
            return 4;
         } else if (propName.equals("JDBCDataSourceParams")) {
            return 5;
         } else if (propName.equals("JDBCDriverParams")) {
            return 3;
         } else if (propName.equals("JDBCOracleParams")) {
            return 7;
         } else if (propName.equals("JDBCXAParams")) {
            return 6;
         } else if (propName.equals("Name")) {
            return 0;
         } else {
            return propName.equals("Version") ? 8 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getInternalProperties() != null) {
            iterators.add(new ArrayIterator(new JDBCPropertiesBean[]{this.bean.getInternalProperties()}));
         }

         if (this.bean.getJDBCConnectionPoolParams() != null) {
            iterators.add(new ArrayIterator(new JDBCConnectionPoolParamsBean[]{this.bean.getJDBCConnectionPoolParams()}));
         }

         if (this.bean.getJDBCDataSourceParams() != null) {
            iterators.add(new ArrayIterator(new JDBCDataSourceParamsBean[]{this.bean.getJDBCDataSourceParams()}));
         }

         if (this.bean.getJDBCDriverParams() != null) {
            iterators.add(new ArrayIterator(new JDBCDriverParamsBean[]{this.bean.getJDBCDriverParams()}));
         }

         if (this.bean.getJDBCOracleParams() != null) {
            iterators.add(new ArrayIterator(new JDBCOracleParamsBean[]{this.bean.getJDBCOracleParams()}));
         }

         if (this.bean.getJDBCXAParams() != null) {
            iterators.add(new ArrayIterator(new JDBCXAParamsBean[]{this.bean.getJDBCXAParams()}));
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
            if (this.bean.isDatasourceTypeSet()) {
               buf.append("DatasourceType");
               buf.append(String.valueOf(this.bean.getDatasourceType()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getInternalProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDBCConnectionPoolParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDBCDataSourceParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDBCDriverParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDBCOracleParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getJDBCXAParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
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
            JDBCDataSourceBeanImpl otherTyped = (JDBCDataSourceBeanImpl)other;
            this.computeDiff("DatasourceType", this.bean.getDatasourceType(), otherTyped.getDatasourceType(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeSubDiff("InternalProperties", this.bean.getInternalProperties(), otherTyped.getInternalProperties());
            this.computeSubDiff("JDBCConnectionPoolParams", this.bean.getJDBCConnectionPoolParams(), otherTyped.getJDBCConnectionPoolParams());
            this.computeSubDiff("JDBCDataSourceParams", this.bean.getJDBCDataSourceParams(), otherTyped.getJDBCDataSourceParams());
            this.computeSubDiff("JDBCDriverParams", this.bean.getJDBCDriverParams(), otherTyped.getJDBCDriverParams());
            this.computeSubDiff("JDBCOracleParams", this.bean.getJDBCOracleParams(), otherTyped.getJDBCOracleParams());
            this.computeSubDiff("JDBCXAParams", this.bean.getJDBCXAParams(), otherTyped.getJDBCXAParams());
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCDataSourceBeanImpl original = (JDBCDataSourceBeanImpl)event.getSourceBean();
            JDBCDataSourceBeanImpl proposed = (JDBCDataSourceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DatasourceType")) {
                  original.setDatasourceType(proposed.getDatasourceType());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("InternalProperties")) {
                  if (type == 2) {
                     original.setInternalProperties((JDBCPropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getInternalProperties()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("InternalProperties", (DescriptorBean)original.getInternalProperties());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("JDBCConnectionPoolParams")) {
                  if (type == 2) {
                     original.setJDBCConnectionPoolParams((JDBCConnectionPoolParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getJDBCConnectionPoolParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JDBCConnectionPoolParams", (DescriptorBean)original.getJDBCConnectionPoolParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("JDBCDataSourceParams")) {
                  if (type == 2) {
                     original.setJDBCDataSourceParams((JDBCDataSourceParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getJDBCDataSourceParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JDBCDataSourceParams", (DescriptorBean)original.getJDBCDataSourceParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("JDBCDriverParams")) {
                  if (type == 2) {
                     original.setJDBCDriverParams((JDBCDriverParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getJDBCDriverParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JDBCDriverParams", (DescriptorBean)original.getJDBCDriverParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("JDBCOracleParams")) {
                  if (type == 2) {
                     original.setJDBCOracleParams((JDBCOracleParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getJDBCOracleParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JDBCOracleParams", (DescriptorBean)original.getJDBCOracleParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("JDBCXAParams")) {
                  if (type == 2) {
                     original.setJDBCXAParams((JDBCXAParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getJDBCXAParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("JDBCXAParams", (DescriptorBean)original.getJDBCXAParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
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
            JDBCDataSourceBeanImpl copy = (JDBCDataSourceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DatasourceType")) && this.bean.isDatasourceTypeSet()) {
               copy.setDatasourceType(this.bean.getDatasourceType());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("InternalProperties")) && this.bean.isInternalPropertiesSet() && !copy._isSet(2)) {
               Object o = this.bean.getInternalProperties();
               copy.setInternalProperties((JDBCPropertiesBean)null);
               copy.setInternalProperties(o == null ? null : (JDBCPropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCConnectionPoolParams")) && this.bean.isJDBCConnectionPoolParamsSet() && !copy._isSet(4)) {
               Object o = this.bean.getJDBCConnectionPoolParams();
               copy.setJDBCConnectionPoolParams((JDBCConnectionPoolParamsBean)null);
               copy.setJDBCConnectionPoolParams(o == null ? null : (JDBCConnectionPoolParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCDataSourceParams")) && this.bean.isJDBCDataSourceParamsSet() && !copy._isSet(5)) {
               Object o = this.bean.getJDBCDataSourceParams();
               copy.setJDBCDataSourceParams((JDBCDataSourceParamsBean)null);
               copy.setJDBCDataSourceParams(o == null ? null : (JDBCDataSourceParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCDriverParams")) && this.bean.isJDBCDriverParamsSet() && !copy._isSet(3)) {
               Object o = this.bean.getJDBCDriverParams();
               copy.setJDBCDriverParams((JDBCDriverParamsBean)null);
               copy.setJDBCDriverParams(o == null ? null : (JDBCDriverParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCOracleParams")) && this.bean.isJDBCOracleParamsSet() && !copy._isSet(7)) {
               Object o = this.bean.getJDBCOracleParams();
               copy.setJDBCOracleParams((JDBCOracleParamsBean)null);
               copy.setJDBCOracleParams(o == null ? null : (JDBCOracleParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCXAParams")) && this.bean.isJDBCXAParamsSet() && !copy._isSet(6)) {
               Object o = this.bean.getJDBCXAParams();
               copy.setJDBCXAParams((JDBCXAParamsBean)null);
               copy.setJDBCXAParams(o == null ? null : (JDBCXAParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
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
         this.inferSubTree(this.bean.getInternalProperties(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCConnectionPoolParams(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCDataSourceParams(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCDriverParams(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCOracleParams(), clazz, annotation);
         this.inferSubTree(this.bean.getJDBCXAParams(), clazz, annotation);
      }
   }
}

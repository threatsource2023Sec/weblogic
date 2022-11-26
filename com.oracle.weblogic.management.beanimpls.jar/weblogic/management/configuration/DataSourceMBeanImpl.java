package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.DataSource;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class DataSourceMBeanImpl extends DeploymentMBeanImpl implements DataSourceMBean, Serializable {
   private DataSourceLogFileMBean _DataSourceLogFile;
   private String _DefaultDatasource;
   private boolean _DynamicallyCreated;
   private String _Name;
   private String _RmiJDBCSecurity;
   private String[] _Tags;
   private transient DataSource _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private DataSourceMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(DataSourceMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(DataSourceMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public DataSourceMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(DataSourceMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      DataSourceMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._DataSourceLogFile instanceof DataSourceLogFileMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getDataSourceLogFile() != null) {
            this._getReferenceManager().unregisterBean((DataSourceLogFileMBeanImpl)oldDelegate.getDataSourceLogFile());
         }

         if (delegate != null && delegate.getDataSourceLogFile() != null) {
            this._getReferenceManager().registerBean((DataSourceLogFileMBeanImpl)delegate.getDataSourceLogFile(), false);
         }

         ((DataSourceLogFileMBeanImpl)this._DataSourceLogFile)._setDelegateBean((DataSourceLogFileMBeanImpl)((DataSourceLogFileMBeanImpl)(delegate == null ? null : delegate.getDataSourceLogFile())));
      }

   }

   public DataSourceMBeanImpl() {
      try {
         this._customizer = new DataSource(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public DataSourceMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new DataSource(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public DataSourceMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new DataSource(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public DataSourceLogFileMBean getDataSourceLogFile() {
      return this._DataSourceLogFile;
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getName();
      }
   }

   public boolean isDataSourceLogFileInherited() {
      return false;
   }

   public boolean isDataSourceLogFileSet() {
      return this._isSet(12) || this._isAnythingSet((AbstractDescriptorBean)this.getDataSourceLogFile());
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setDataSourceLogFile(DataSourceLogFileMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 12)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(12);
      DataSourceLogFileMBean _oldVal = this._DataSourceLogFile;
      this._DataSourceLogFile = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         DataSourceMBeanImpl source = (DataSourceMBeanImpl)var5.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getRmiJDBCSecurity() {
      if (!this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         return this._performMacroSubstitution(this._getDelegateBean().getRmiJDBCSecurity(), this);
      } else if (!this._isSet(13)) {
         return this._isSecureModeEnabled() ? "Secure" : "Compatibility";
      } else {
         return this._RmiJDBCSecurity;
      }
   }

   public boolean isRmiJDBCSecurityInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isRmiJDBCSecuritySet() {
      return this._isSet(13);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         DataSourceMBeanImpl source = (DataSourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setRmiJDBCSecurity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this._RmiJDBCSecurity;
      this._RmiJDBCSecurity = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         DataSourceMBeanImpl source = (DataSourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultDatasource() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultDatasource(), this) : this._DefaultDatasource;
   }

   public boolean isDefaultDatasourceInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isDefaultDatasourceSet() {
      return this._isSet(14);
   }

   public void setDefaultDatasource(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      String _oldVal = this._DefaultDatasource;
      this._DefaultDatasource = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         DataSourceMBeanImpl source = (DataSourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(9);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         DataSourceMBeanImpl source = (DataSourceMBeanImpl)var4.next();
         if (source != null && !source._isSet(9)) {
            source._postSetFirePropertyChange(9, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public Object _getKey() {
      return this.getName();
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
      return super._isAnythingSet() || this.isDataSourceLogFileSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._DataSourceLogFile = new DataSourceLogFileMBeanImpl(this, 12);
               this._postCreate((AbstractDescriptorBean)this._DataSourceLogFile);
               if (initOne) {
                  break;
               }
            case 14:
               this._DefaultDatasource = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 13:
               this._RmiJDBCSecurity = "Compatibility";
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "DataSource";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DataSourceLogFile")) {
         DataSourceLogFileMBean oldVal = this._DataSourceLogFile;
         this._DataSourceLogFile = (DataSourceLogFileMBean)v;
         this._postSet(12, oldVal, this._DataSourceLogFile);
      } else {
         String oldVal;
         if (name.equals("DefaultDatasource")) {
            oldVal = this._DefaultDatasource;
            this._DefaultDatasource = (String)v;
            this._postSet(14, oldVal, this._DefaultDatasource);
         } else if (name.equals("DynamicallyCreated")) {
            boolean oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else if (name.equals("Name")) {
            oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(2, oldVal, this._Name);
         } else if (name.equals("RmiJDBCSecurity")) {
            oldVal = this._RmiJDBCSecurity;
            this._RmiJDBCSecurity = (String)v;
            this._postSet(13, oldVal, this._RmiJDBCSecurity);
         } else if (name.equals("Tags")) {
            String[] oldVal = this._Tags;
            this._Tags = (String[])((String[])v);
            this._postSet(9, oldVal, this._Tags);
         } else if (name.equals("customizer")) {
            DataSource oldVal = this._customizer;
            this._customizer = (DataSource)v;
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DataSourceLogFile")) {
         return this._DataSourceLogFile;
      } else if (name.equals("DefaultDatasource")) {
         return this._DefaultDatasource;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("RmiJDBCSecurity")) {
         return this._RmiJDBCSecurity;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            default:
               break;
            case 17:
               if (s.equals("rmi-jdbc-security")) {
                  return 13;
               }
               break;
            case 18:
               if (s.equals("default-datasource")) {
                  return 14;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("data-source-log-file")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 12:
               return new DataSourceLogFileMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 12:
               return "data-source-log-file";
            case 13:
               return "rmi-jdbc-security";
            case 14:
               return "default-datasource";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 12:
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
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private DataSourceMBeanImpl bean;

      protected Helper(DataSourceMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            case 10:
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 12:
               return "DataSourceLogFile";
            case 13:
               return "RmiJDBCSecurity";
            case 14:
               return "DefaultDatasource";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DataSourceLogFile")) {
            return 12;
         } else if (propName.equals("DefaultDatasource")) {
            return 14;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("RmiJDBCSecurity")) {
            return 13;
         } else if (propName.equals("Tags")) {
            return 9;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getDataSourceLogFile() != null) {
            iterators.add(new ArrayIterator(new DataSourceLogFileMBean[]{this.bean.getDataSourceLogFile()}));
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
            childValue = this.computeChildHashValue(this.bean.getDataSourceLogFile());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isDefaultDatasourceSet()) {
               buf.append("DefaultDatasource");
               buf.append(String.valueOf(this.bean.getDefaultDatasource()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isRmiJDBCSecuritySet()) {
               buf.append("RmiJDBCSecurity");
               buf.append(String.valueOf(this.bean.getRmiJDBCSecurity()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
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
            DataSourceMBeanImpl otherTyped = (DataSourceMBeanImpl)other;
            this.computeSubDiff("DataSourceLogFile", this.bean.getDataSourceLogFile(), otherTyped.getDataSourceLogFile());
            this.computeDiff("DefaultDatasource", this.bean.getDefaultDatasource(), otherTyped.getDefaultDatasource(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("RmiJDBCSecurity", this.bean.getRmiJDBCSecurity(), otherTyped.getRmiJDBCSecurity(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DataSourceMBeanImpl original = (DataSourceMBeanImpl)event.getSourceBean();
            DataSourceMBeanImpl proposed = (DataSourceMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DataSourceLogFile")) {
                  if (type == 2) {
                     original.setDataSourceLogFile((DataSourceLogFileMBean)this.createCopy((AbstractDescriptorBean)proposed.getDataSourceLogFile()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("DataSourceLogFile", original.getDataSourceLogFile());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("DefaultDatasource")) {
                  original.setDefaultDatasource(proposed.getDefaultDatasource());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RmiJDBCSecurity")) {
                  original.setRmiJDBCSecurity(proposed.getRmiJDBCSecurity());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("Tags")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addTag((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTag((String)update.getRemovedObject());
                  }

                  if (original.getTags() == null || original.getTags().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (!prop.equals("DynamicallyCreated")) {
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
            DataSourceMBeanImpl copy = (DataSourceMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DataSourceLogFile")) && this.bean.isDataSourceLogFileSet() && !copy._isSet(12)) {
               Object o = this.bean.getDataSourceLogFile();
               copy.setDataSourceLogFile((DataSourceLogFileMBean)null);
               copy.setDataSourceLogFile(o == null ? null : (DataSourceLogFileMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultDatasource")) && this.bean.isDefaultDatasourceSet()) {
               copy.setDefaultDatasource(this.bean.getDefaultDatasource());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("RmiJDBCSecurity")) && this.bean.isRmiJDBCSecuritySet()) {
               copy.setRmiJDBCSecurity(this.bean.getRmiJDBCSecurity());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
         this.inferSubTree(this.bean.getDataSourceLogFile(), clazz, annotation);
      }
   }
}

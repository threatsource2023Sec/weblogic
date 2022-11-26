package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.PartitionTemplate;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class PartitionTemplateMBeanImpl extends PartitionMBeanImpl implements PartitionTemplateMBean, Serializable {
   private boolean _DynamicallyCreated;
   private String _Name;
   private PartitionConfiguratorMBean[] _PartitionConfigurators;
   private String[] _Tags;
   private transient PartitionTemplate _customizer;
   private static SchemaHelper2 _schemaHelper;

   public PartitionTemplateMBeanImpl() {
      try {
         this._customizer = new PartitionTemplate(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public PartitionTemplateMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new PartitionTemplate(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public PartitionTemplateMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new PartitionTemplate(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public void addPartitionConfigurator(PartitionConfiguratorMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 58)) {
         PartitionConfiguratorMBean[] _new;
         if (this._isSet(58)) {
            _new = (PartitionConfiguratorMBean[])((PartitionConfiguratorMBean[])this._getHelper()._extendArray(this.getPartitionConfigurators(), PartitionConfiguratorMBean.class, param0));
         } else {
            _new = new PartitionConfiguratorMBean[]{param0};
         }

         try {
            this.setPartitionConfigurators(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public String getName() {
      if (!this._isSet(2)) {
         try {
            return ((ConfigurationMBean)this.getParent()).getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._customizer.getName();
   }

   public PartitionConfiguratorMBean[] getPartitionConfigurators() {
      return this._PartitionConfigurators;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isPartitionConfiguratorsInherited() {
      return false;
   }

   public boolean isPartitionConfiguratorsSet() {
      return this._isSet(58);
   }

   public void removePartitionConfigurator(PartitionConfiguratorMBean param0) {
      this.destroyPartitionConfigurator(param0);
   }

   public void setPartitionConfigurators(PartitionConfiguratorMBean[] param0) throws InvalidAttributeValueException {
      PartitionConfiguratorMBean[] param0 = param0 == null ? new PartitionConfiguratorMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 58)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      PartitionConfiguratorMBean[] _oldVal = this._PartitionConfigurators;
      this._PartitionConfigurators = (PartitionConfiguratorMBean[])param0;
      this._postSet(58, _oldVal, param0);
   }

   public PartitionConfiguratorMBean lookupPartitionConfigurator(String param0) {
      Object[] aary = (Object[])this._PartitionConfigurators;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      PartitionConfiguratorMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (PartitionConfiguratorMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void destroyPartitionConfigurator(PartitionConfiguratorMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 58);
         PartitionConfiguratorMBean[] _old = this.getPartitionConfigurators();
         PartitionConfiguratorMBean[] _new = (PartitionConfiguratorMBean[])((PartitionConfiguratorMBean[])this._getHelper()._removeElement(_old, PartitionConfiguratorMBean.class, param0));
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
               this.setPartitionConfigurators(_new);
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

   public PartitionConfiguratorMBean createPartitionConfigurator(String param0, String param1) {
      return this._customizer.createPartitionConfigurator(param0, param1);
   }

   public String[] listPartitionConfiguratorServiceNames() {
      return this._customizer.listPartitionConfiguratorServiceNames();
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
      this._DynamicallyCreated = param0;
   }

   public String[] getTags() {
      return this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return false;
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
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
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 58:
               this._PartitionConfigurators = new PartitionConfiguratorMBean[0];
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
      return "PartitionTemplate";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         String oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("PartitionConfigurators")) {
         PartitionConfiguratorMBean[] oldVal = this._PartitionConfigurators;
         this._PartitionConfigurators = (PartitionConfiguratorMBean[])((PartitionConfiguratorMBean[])v);
         this._postSet(58, oldVal, this._PartitionConfigurators);
      } else if (name.equals("Tags")) {
         String[] oldVal = this._Tags;
         this._Tags = (String[])((String[])v);
         this._postSet(9, oldVal, this._Tags);
      } else if (name.equals("customizer")) {
         PartitionTemplate oldVal = this._customizer;
         this._customizer = (PartitionTemplate)v;
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PartitionConfigurators")) {
         return this._PartitionConfigurators;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends PartitionMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 22:
               if (s.equals("partition-configurator")) {
                  return 58;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new ConfigurationPropertyMBeanImpl.SchemaHelper2();
            case 11:
               return new ResourceGroupMBeanImpl.SchemaHelper2();
            case 12:
               return new JDBCSystemResourceOverrideMBeanImpl.SchemaHelper2();
            case 13:
               return new MailSessionOverrideMBeanImpl.SchemaHelper2();
            case 14:
               return new ForeignJNDIProviderOverrideMBeanImpl.SchemaHelper2();
            case 15:
            case 16:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            case 28:
            case 29:
            case 31:
            case 36:
            case 37:
            case 38:
            case 39:
            case 41:
            case 42:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 56:
            default:
               return super.getSchemaHelper(propIndex);
            case 17:
               return new SelfTuningMBeanImpl.SchemaHelper2();
            case 20:
               return new PartitionLogMBeanImpl.SchemaHelper2();
            case 24:
               return new ManagedExecutorServiceTemplateMBeanImpl.SchemaHelper2();
            case 25:
               return new ManagedScheduledExecutorServiceTemplateMBeanImpl.SchemaHelper2();
            case 26:
               return new ManagedThreadFactoryTemplateMBeanImpl.SchemaHelper2();
            case 27:
               return new JTAPartitionMBeanImpl.SchemaHelper2();
            case 30:
               return new ResourceManagerMBeanImpl.SchemaHelper2();
            case 32:
               return new DataSourcePartitionMBeanImpl.SchemaHelper2();
            case 33:
               return new CoherencePartitionCacheConfigMBeanImpl.SchemaHelper2();
            case 34:
               return new PartitionFileSystemMBeanImpl.SchemaHelper2();
            case 35:
               return new PartitionUserFileSystemMBeanImpl.SchemaHelper2();
            case 40:
               return new JMSSystemResourceOverrideMBeanImpl.SchemaHelper2();
            case 43:
               return new PartitionWorkManagerMBeanImpl.SchemaHelper2();
            case 55:
               return new WebServiceMBeanImpl.SchemaHelper2();
            case 57:
               return new AdminVirtualTargetMBeanImpl.SchemaHelper2();
            case 58:
               return new PartitionConfiguratorMBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 58:
               return "partition-configurator";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
               return true;
            case 16:
               return true;
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 34:
            case 35:
            case 36:
            case 39:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            default:
               return super.isArray(propIndex);
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 33:
               return true;
            case 37:
               return true;
            case 38:
               return true;
            case 40:
               return true;
            case 41:
               return true;
            case 42:
               return true;
            case 58:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            case 15:
            case 16:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            case 28:
            case 29:
            case 31:
            case 36:
            case 37:
            case 38:
            case 39:
            case 41:
            case 42:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 56:
            default:
               return super.isBean(propIndex);
            case 17:
               return true;
            case 20:
               return true;
            case 24:
               return true;
            case 25:
               return true;
            case 26:
               return true;
            case 27:
               return true;
            case 30:
               return true;
            case 32:
               return true;
            case 33:
               return true;
            case 34:
               return true;
            case 35:
               return true;
            case 40:
               return true;
            case 43:
               return true;
            case 55:
               return true;
            case 57:
               return true;
            case 58:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 50:
               return true;
            case 51:
               return true;
            case 52:
               return true;
            case 53:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

   protected static class Helper extends PartitionMBeanImpl.Helper {
      private PartitionTemplateMBeanImpl bean;

      protected Helper(PartitionTemplateMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 58:
               return "PartitionConfigurators";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PartitionConfigurators")) {
            return 58;
         } else if (propName.equals("Tags")) {
            return 9;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAdminVirtualTarget() != null) {
            iterators.add(new ArrayIterator(new AdminVirtualTargetMBean[]{this.bean.getAdminVirtualTarget()}));
         }

         iterators.add(new ArrayIterator(this.bean.getCoherencePartitionCacheConfigs()));
         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
         if (this.bean.getDataSourcePartition() != null) {
            iterators.add(new ArrayIterator(new DataSourcePartitionMBean[]{this.bean.getDataSourcePartition()}));
         }

         iterators.add(new ArrayIterator(this.bean.getForeignJNDIProviderOverrides()));
         iterators.add(new ArrayIterator(this.bean.getJDBCSystemResourceOverrides()));
         iterators.add(new ArrayIterator(this.bean.getJMSSystemResourceOverrides()));
         if (this.bean.getJTAPartition() != null) {
            iterators.add(new ArrayIterator(new JTAPartitionMBean[]{this.bean.getJTAPartition()}));
         }

         iterators.add(new ArrayIterator(this.bean.getMailSessionOverrides()));
         iterators.add(new ArrayIterator(this.bean.getManagedExecutorServiceTemplates()));
         iterators.add(new ArrayIterator(this.bean.getManagedScheduledExecutorServiceTemplates()));
         iterators.add(new ArrayIterator(this.bean.getManagedThreadFactoryTemplates()));
         iterators.add(new ArrayIterator(this.bean.getPartitionConfigurators()));
         if (this.bean.getPartitionLog() != null) {
            iterators.add(new ArrayIterator(new PartitionLogMBean[]{this.bean.getPartitionLog()}));
         }

         if (this.bean.getPartitionWorkManager() != null) {
            iterators.add(new ArrayIterator(new PartitionWorkManagerMBean[]{this.bean.getPartitionWorkManager()}));
         }

         iterators.add(new ArrayIterator(this.bean.getResourceGroups()));
         if (this.bean.getResourceManager() != null) {
            iterators.add(new ArrayIterator(new ResourceManagerMBean[]{this.bean.getResourceManager()}));
         }

         if (this.bean.getSelfTuning() != null) {
            iterators.add(new ArrayIterator(new SelfTuningMBean[]{this.bean.getSelfTuning()}));
         }

         if (this.bean.getSystemFileSystem() != null) {
            iterators.add(new ArrayIterator(new PartitionFileSystemMBean[]{this.bean.getSystemFileSystem()}));
         }

         if (this.bean.getUserFileSystem() != null) {
            iterators.add(new ArrayIterator(new PartitionUserFileSystemMBean[]{this.bean.getUserFileSystem()}));
         }

         if (this.bean.getWebService() != null) {
            iterators.add(new ArrayIterator(new WebServiceMBean[]{this.bean.getWebService()}));
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
            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getPartitionConfigurators().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPartitionConfigurators()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            PartitionTemplateMBeanImpl otherTyped = (PartitionTemplateMBeanImpl)other;
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeChildDiff("PartitionConfigurators", this.bean.getPartitionConfigurators(), otherTyped.getPartitionConfigurators(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PartitionTemplateMBeanImpl original = (PartitionTemplateMBeanImpl)event.getSourceBean();
            PartitionTemplateMBeanImpl proposed = (PartitionTemplateMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("PartitionConfigurators")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPartitionConfigurator((PartitionConfiguratorMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePartitionConfigurator((PartitionConfiguratorMBean)update.getRemovedObject());
                  }

                  if (original.getPartitionConfigurators() == null || original.getPartitionConfigurators().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 58);
                  }
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
            PartitionTemplateMBeanImpl copy = (PartitionTemplateMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionConfigurators")) && this.bean.isPartitionConfiguratorsSet() && !copy._isSet(58)) {
               PartitionConfiguratorMBean[] oldPartitionConfigurators = this.bean.getPartitionConfigurators();
               PartitionConfiguratorMBean[] newPartitionConfigurators = new PartitionConfiguratorMBean[oldPartitionConfigurators.length];

               for(int i = 0; i < newPartitionConfigurators.length; ++i) {
                  newPartitionConfigurators[i] = (PartitionConfiguratorMBean)((PartitionConfiguratorMBean)this.createCopy((AbstractDescriptorBean)oldPartitionConfigurators[i], includeObsolete));
               }

               copy.setPartitionConfigurators(newPartitionConfigurators);
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
         this.inferSubTree(this.bean.getPartitionConfigurators(), clazz, annotation);
      }
   }
}

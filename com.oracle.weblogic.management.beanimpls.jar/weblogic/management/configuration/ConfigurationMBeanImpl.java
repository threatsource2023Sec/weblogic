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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBeanImpl;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ConfigurationMBeanImpl extends WebLogicMBeanImpl implements ConfigurationMBean, Serializable {
   private String _Comments;
   private boolean _DefaultedMBean;
   private boolean _DynamicallyCreated;
   private long _Id;
   private String _Name;
   private String _Notes;
   private boolean _PersistenceEnabled;
   private String[] _Tags;
   private transient ConfigurationMBeanCustomizer _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ConfigurationMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ConfigurationMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ConfigurationMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ConfigurationMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ConfigurationMBeanImpl delegate) {
      ConfigurationMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ConfigurationMBeanImpl() {
      try {
         this._customizer = new ConfigurationMBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ConfigurationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ConfigurationMBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ConfigurationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ConfigurationMBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
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

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
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
         ConfigurationMBeanImpl source = (ConfigurationMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public String getNotes() {
      return !this._isSet(3) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(3) ? this._performMacroSubstitution(this._getDelegateBean().getNotes(), this) : this._Notes;
   }

   public boolean isNotesInherited() {
      return !this._isSet(3) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(3);
   }

   public boolean isNotesSet() {
      return this._isSet(3);
   }

   public void setNotes(String param0) throws InvalidAttributeValueException, DistributedManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(3);
      String _oldVal = this._Notes;
      this._Notes = param0;
      this._postSet(3, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ConfigurationMBeanImpl source = (ConfigurationMBeanImpl)var4.next();
         if (source != null && !source._isSet(3)) {
            source._postSetFirePropertyChange(3, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPersistenceEnabled() {
      return !this._isSet(4) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(4) ? this._getDelegateBean().isPersistenceEnabled() : this._PersistenceEnabled;
   }

   public boolean isPersistenceEnabledInherited() {
      return !this._isSet(4) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(4);
   }

   public boolean isPersistenceEnabledSet() {
      return this._isSet(4);
   }

   public void setPersistenceEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._PersistenceEnabled = param0;
   }

   public boolean isDefaultedMBean() {
      return !this._isSet(5) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(5) ? this._getDelegateBean().isDefaultedMBean() : this._DefaultedMBean;
   }

   public boolean isDefaultedMBeanInherited() {
      return !this._isSet(5) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(5);
   }

   public boolean isDefaultedMBeanSet() {
      return this._isSet(5);
   }

   public void setDefaultedMBean(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DefaultedMBean = param0;
   }

   public String getComments() {
      return !this._isSet(6) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(6) ? this._performMacroSubstitution(this._getDelegateBean().getComments(), this) : this._Comments;
   }

   public boolean isCommentsInherited() {
      return !this._isSet(6) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(6);
   }

   public boolean isCommentsSet() {
      return this._isSet(6);
   }

   public void setComments(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._Comments = param0;
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

   public long getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(8);
   }

   public void setId(long param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(8);
      long _oldVal = this._Id;
      this._Id = param0;
      this._postSet(8, _oldVal, param0);
      Iterator var6 = this._DelegateSources.iterator();

      while(var6.hasNext()) {
         ConfigurationMBeanImpl source = (ConfigurationMBeanImpl)var6.next();
         if (source != null && !source._isSet(8)) {
            source._postSetFirePropertyChange(8, wasSet, _oldVal, param0);
         }
      }

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
         ConfigurationMBeanImpl source = (ConfigurationMBeanImpl)var4.next();
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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 6;
      }

      try {
         switch (idx) {
            case 6:
               this._Comments = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._Id = 0L;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 3:
               this._Notes = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 5:
               this._DefaultedMBean = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._PersistenceEnabled = true;
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
      return "Configuration";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Comments")) {
         oldVal = this._Comments;
         this._Comments = (String)v;
         this._postSet(6, oldVal, this._Comments);
      } else {
         boolean oldVal;
         if (name.equals("DefaultedMBean")) {
            oldVal = this._DefaultedMBean;
            this._DefaultedMBean = (Boolean)v;
            this._postSet(5, oldVal, this._DefaultedMBean);
         } else if (name.equals("DynamicallyCreated")) {
            oldVal = this._DynamicallyCreated;
            this._DynamicallyCreated = (Boolean)v;
            this._postSet(7, oldVal, this._DynamicallyCreated);
         } else if (name.equals("Id")) {
            long oldVal = this._Id;
            this._Id = (Long)v;
            this._postSet(8, oldVal, this._Id);
         } else if (name.equals("Name")) {
            oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(2, oldVal, this._Name);
         } else if (name.equals("Notes")) {
            oldVal = this._Notes;
            this._Notes = (String)v;
            this._postSet(3, oldVal, this._Notes);
         } else if (name.equals("PersistenceEnabled")) {
            oldVal = this._PersistenceEnabled;
            this._PersistenceEnabled = (Boolean)v;
            this._postSet(4, oldVal, this._PersistenceEnabled);
         } else if (name.equals("Tags")) {
            String[] oldVal = this._Tags;
            this._Tags = (String[])((String[])v);
            this._postSet(9, oldVal, this._Tags);
         } else if (name.equals("customizer")) {
            ConfigurationMBeanCustomizer oldVal = this._customizer;
            this._customizer = (ConfigurationMBeanCustomizer)v;
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("Comments")) {
         return this._Comments;
      } else if (name.equals("DefaultedMBean")) {
         return new Boolean(this._DefaultedMBean);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Id")) {
         return new Long(this._Id);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Notes")) {
         return this._Notes;
      } else if (name.equals("PersistenceEnabled")) {
         return new Boolean(this._PersistenceEnabled);
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 8;
               }
               break;
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
            case 5:
               if (s.equals("notes")) {
                  return 3;
               }
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            default:
               break;
            case 8:
               if (s.equals("comments")) {
                  return 6;
               }
               break;
            case 15:
               if (s.equals("defaultedm-bean")) {
                  return 5;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }

               if (s.equals("persistence-enabled")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "name";
            case 3:
               return "notes";
            case 4:
               return "persistence-enabled";
            case 5:
               return "defaultedm-bean";
            case 6:
               return "comments";
            case 7:
               return "dynamically-created";
            case 8:
               return "id";
            case 9:
               return "tag";
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

   protected static class Helper extends WebLogicMBeanImpl.Helper {
      private ConfigurationMBeanImpl bean;

      protected Helper(ConfigurationMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
               return "Notes";
            case 4:
               return "PersistenceEnabled";
            case 5:
               return "DefaultedMBean";
            case 6:
               return "Comments";
            case 7:
               return "DynamicallyCreated";
            case 8:
               return "Id";
            case 9:
               return "Tags";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Comments")) {
            return 6;
         } else if (propName.equals("Id")) {
            return 8;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Notes")) {
            return 3;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("DefaultedMBean")) {
            return 5;
         } else if (propName.equals("DynamicallyCreated")) {
            return 7;
         } else {
            return propName.equals("PersistenceEnabled") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isCommentsSet()) {
               buf.append("Comments");
               buf.append(String.valueOf(this.bean.getComments()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNotesSet()) {
               buf.append("Notes");
               buf.append(String.valueOf(this.bean.getNotes()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isDefaultedMBeanSet()) {
               buf.append("DefaultedMBean");
               buf.append(String.valueOf(this.bean.isDefaultedMBean()));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
            }

            if (this.bean.isPersistenceEnabledSet()) {
               buf.append("PersistenceEnabled");
               buf.append(String.valueOf(this.bean.isPersistenceEnabled()));
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
            ConfigurationMBeanImpl otherTyped = (ConfigurationMBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Notes", this.bean.getNotes(), otherTyped.getNotes(), true);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConfigurationMBeanImpl original = (ConfigurationMBeanImpl)event.getSourceBean();
            ConfigurationMBeanImpl proposed = (ConfigurationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("Comments")) {
                  if (prop.equals("Id")) {
                     original._conditionalUnset(update.isUnsetUpdate(), 8);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("Notes")) {
                     original.setNotes(proposed.getNotes());
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
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
                  } else if (!prop.equals("DefaultedMBean") && !prop.equals("DynamicallyCreated") && !prop.equals("PersistenceEnabled")) {
                     super.applyPropertyUpdate(event, update);
                  }
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
            ConfigurationMBeanImpl copy = (ConfigurationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Notes")) && this.bean.isNotesSet()) {
               copy.setNotes(this.bean.getNotes());
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
      }
   }
}

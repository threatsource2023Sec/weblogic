package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.SystemComponent;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SystemComponentMBeanImpl extends ManagedExternalServerMBeanImpl implements SystemComponentMBean, Serializable {
   private String _ComponentType;
   private boolean _DynamicallyCreated;
   private ManagedExternalServerStartMBean _ManagedExternalServerStart;
   private String _ManagedExternalType;
   private String _Name;
   private SystemComponentStartMBean _SystemComponentStart;
   private String[] _Tags;
   private transient SystemComponent _customizer;
   private static SchemaHelper2 _schemaHelper;

   public SystemComponentMBeanImpl() {
      try {
         this._customizer = new SystemComponent(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public SystemComponentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new SystemComponent(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public SystemComponentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new SystemComponent(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      return this._customizer.getName();
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public String getComponentType() {
      return this._ComponentType;
   }

   public boolean isComponentTypeInherited() {
      return false;
   }

   public boolean isComponentTypeSet() {
      return this._isSet(18);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      ConfigurationValidator.validateNameInMultiByte(param0);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
   }

   public void setComponentType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ComponentType;
      this._ComponentType = param0;
      this._postSet(18, _oldVal, param0);
   }

   public SystemComponentStartMBean getSystemComponentStart() {
      return this._SystemComponentStart;
   }

   public boolean isSystemComponentStartInherited() {
      return false;
   }

   public boolean isSystemComponentStartSet() {
      return this._isSet(19) || this._isAnythingSet((AbstractDescriptorBean)this.getSystemComponentStart());
   }

   public void setSystemComponentStart(SystemComponentStartMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 19)) {
         this._postCreate(_child);
      }

      SystemComponentStartMBean _oldVal = this._SystemComponentStart;
      this._SystemComponentStart = param0;
      this._postSet(19, _oldVal, param0);
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

   public ManagedExternalServerStartMBean getManagedExternalServerStart() {
      return this._customizer.getManagedExternalServerStart();
   }

   public boolean isManagedExternalServerStartInherited() {
      return false;
   }

   public boolean isManagedExternalServerStartSet() {
      return this._isSet(16);
   }

   public void setManagedExternalServerStart(ManagedExternalServerStartMBean param0) throws InvalidAttributeValueException {
      this._ManagedExternalServerStart = param0;
   }

   public String getManagedExternalType() {
      return this._customizer.getManagedExternalType();
   }

   public boolean isManagedExternalTypeInherited() {
      return false;
   }

   public boolean isManagedExternalTypeSet() {
      return this._isSet(17);
   }

   public void setManagedExternalType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this._ManagedExternalType = param0;
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
      return super._isAnythingSet() || this.isSystemComponentStartSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 18;
      }

      try {
         switch (idx) {
            case 18:
               this._ComponentType = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._ManagedExternalServerStart = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._ManagedExternalType = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 19:
               this._SystemComponentStart = new SystemComponentStartMBeanImpl(this, 19);
               this._postCreate((AbstractDescriptorBean)this._SystemComponentStart);
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
            case 12:
            case 13:
            case 14:
            case 15:
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
      return "SystemComponent";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ComponentType")) {
         oldVal = this._ComponentType;
         this._ComponentType = (String)v;
         this._postSet(18, oldVal, this._ComponentType);
      } else if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else if (name.equals("ManagedExternalServerStart")) {
         ManagedExternalServerStartMBean oldVal = this._ManagedExternalServerStart;
         this._ManagedExternalServerStart = (ManagedExternalServerStartMBean)v;
         this._postSet(16, oldVal, this._ManagedExternalServerStart);
      } else if (name.equals("ManagedExternalType")) {
         oldVal = this._ManagedExternalType;
         this._ManagedExternalType = (String)v;
         this._postSet(17, oldVal, this._ManagedExternalType);
      } else if (name.equals("Name")) {
         oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("SystemComponentStart")) {
         SystemComponentStartMBean oldVal = this._SystemComponentStart;
         this._SystemComponentStart = (SystemComponentStartMBean)v;
         this._postSet(19, oldVal, this._SystemComponentStart);
      } else if (name.equals("Tags")) {
         String[] oldVal = this._Tags;
         this._Tags = (String[])((String[])v);
         this._postSet(9, oldVal, this._Tags);
      } else if (name.equals("customizer")) {
         SystemComponent oldVal = this._customizer;
         this._customizer = (SystemComponent)v;
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ComponentType")) {
         return this._ComponentType;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("ManagedExternalServerStart")) {
         return this._ManagedExternalServerStart;
      } else if (name.equals("ManagedExternalType")) {
         return this._ManagedExternalType;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("SystemComponentStart")) {
         return this._SystemComponentStart;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ManagedExternalServerMBeanImpl.SchemaHelper2 implements SchemaHelper {
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
            case 14:
               if (s.equals("component-type")) {
                  return 18;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 21:
               if (s.equals("managed-external-type")) {
                  return 17;
               }
               break;
            case 22:
               if (s.equals("system-component-start")) {
                  return 19;
               }
               break;
            case 29:
               if (s.equals("managed-external-server-start")) {
                  return 16;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 19:
               return new SystemComponentStartMBeanImpl.SchemaHelper2();
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
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 16:
               return "managed-external-server-start";
            case 17:
               return "managed-external-type";
            case 18:
               return "component-type";
            case 19:
               return "system-component-start";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 19:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
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

   protected static class Helper extends ManagedExternalServerMBeanImpl.Helper {
      private SystemComponentMBeanImpl bean;

      protected Helper(SystemComponentMBeanImpl bean) {
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
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 16:
               return "ManagedExternalServerStart";
            case 17:
               return "ManagedExternalType";
            case 18:
               return "ComponentType";
            case 19:
               return "SystemComponentStart";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ComponentType")) {
            return 18;
         } else if (propName.equals("ManagedExternalServerStart")) {
            return 16;
         } else if (propName.equals("ManagedExternalType")) {
            return 17;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("SystemComponentStart")) {
            return 19;
         } else if (propName.equals("Tags")) {
            return 9;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getSystemComponentStart() != null) {
            iterators.add(new ArrayIterator(new SystemComponentStartMBean[]{this.bean.getSystemComponentStart()}));
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
            if (this.bean.isComponentTypeSet()) {
               buf.append("ComponentType");
               buf.append(String.valueOf(this.bean.getComponentType()));
            }

            if (this.bean.isManagedExternalServerStartSet()) {
               buf.append("ManagedExternalServerStart");
               buf.append(String.valueOf(this.bean.getManagedExternalServerStart()));
            }

            if (this.bean.isManagedExternalTypeSet()) {
               buf.append("ManagedExternalType");
               buf.append(String.valueOf(this.bean.getManagedExternalType()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            childValue = this.computeChildHashValue(this.bean.getSystemComponentStart());
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
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            SystemComponentMBeanImpl otherTyped = (SystemComponentMBeanImpl)other;
            this.computeDiff("ComponentType", this.bean.getComponentType(), otherTyped.getComponentType(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeSubDiff("SystemComponentStart", this.bean.getSystemComponentStart(), otherTyped.getSystemComponentStart());
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SystemComponentMBeanImpl original = (SystemComponentMBeanImpl)event.getSourceBean();
            SystemComponentMBeanImpl proposed = (SystemComponentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ComponentType")) {
                  original.setComponentType(proposed.getComponentType());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (!prop.equals("ManagedExternalServerStart") && !prop.equals("ManagedExternalType")) {
                  if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("SystemComponentStart")) {
                     if (type == 2) {
                        original.setSystemComponentStart((SystemComponentStartMBean)this.createCopy((AbstractDescriptorBean)proposed.getSystemComponentStart()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("SystemComponentStart", original.getSystemComponentStart());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 19);
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

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            SystemComponentMBeanImpl copy = (SystemComponentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ComponentType")) && this.bean.isComponentTypeSet()) {
               copy.setComponentType(this.bean.getComponentType());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("SystemComponentStart")) && this.bean.isSystemComponentStartSet() && !copy._isSet(19)) {
               Object o = this.bean.getSystemComponentStart();
               copy.setSystemComponentStart((SystemComponentStartMBean)null);
               copy.setSystemComponentStart(o == null ? null : (SystemComponentStartMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getManagedExternalServerStart(), clazz, annotation);
         this.inferSubTree(this.bean.getSystemComponentStart(), clazz, annotation);
      }
   }
}

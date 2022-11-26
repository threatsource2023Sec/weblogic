package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class SystemComponentConfigurationMBeanImpl extends ConfigurationMBeanImpl implements SystemComponentConfigurationMBean, Serializable {
   private String _ComponentType;
   private String _SourcePath;
   private SystemComponentMBean[] _SystemComponents;
   private static SchemaHelper2 _schemaHelper;

   public SystemComponentConfigurationMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SystemComponentConfigurationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SystemComponentConfigurationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getComponentType() {
      return this._ComponentType;
   }

   public boolean isComponentTypeInherited() {
      return false;
   }

   public boolean isComponentTypeSet() {
      return this._isSet(10);
   }

   public void setComponentType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ComponentType;
      this._ComponentType = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getSourcePath() {
      if (!this._isSet(11)) {
         try {
            return "config/fmwconfig/components" + this.getComponentType() + "/" + this.getName();
         } catch (NullPointerException var2) {
         }
      }

      return this._SourcePath;
   }

   public boolean isSourcePathInherited() {
      return false;
   }

   public boolean isSourcePathSet() {
      return this._isSet(11);
   }

   public void setSourcePath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SourcePath;
      this._SourcePath = param0;
      this._postSet(11, _oldVal, param0);
   }

   public SystemComponentMBean[] getSystemComponents() {
      return this._SystemComponents;
   }

   public String getSystemComponentsAsString() {
      return this._getHelper()._serializeKeyList(this.getSystemComponents());
   }

   public boolean isSystemComponentsInherited() {
      return false;
   }

   public boolean isSystemComponentsSet() {
      return this._isSet(12);
   }

   public void setSystemComponentsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._SystemComponents);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, SystemComponentMBean.class, new ReferenceManager.Resolver(this, 12, param0) {
                  public void resolveReference(Object value) {
                     try {
                        SystemComponentConfigurationMBeanImpl.this.addSystemComponent((SystemComponentMBean)value);
                        SystemComponentConfigurationMBeanImpl.this._getHelper().reorderArrayObjects((Object[])SystemComponentConfigurationMBeanImpl.this._SystemComponents, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               SystemComponentMBean[] var6 = this._SystemComponents;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  SystemComponentMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeSystemComponent(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         SystemComponentMBean[] _oldVal = this._SystemComponents;
         this._initializeProperty(12);
         this._postSet(12, _oldVal, this._SystemComponents);
      }
   }

   public void setSystemComponents(SystemComponentMBean[] param0) throws InvalidAttributeValueException {
      SystemComponentMBean[] param0 = param0 == null ? new SystemComponentMBeanImpl[0] : param0;
      param0 = (SystemComponentMBean[])((SystemComponentMBean[])this._getHelper()._cleanAndValidateArray(param0, SystemComponentMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 12, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return SystemComponentConfigurationMBeanImpl.this.getSystemComponents();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      SystemComponentMBean[] _oldVal = this._SystemComponents;
      this._SystemComponents = param0;
      this._postSet(12, _oldVal, param0);
   }

   public void addSystemComponent(SystemComponentMBean param0) throws InvalidAttributeValueException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         SystemComponentMBean[] _new;
         if (this._isSet(12)) {
            _new = (SystemComponentMBean[])((SystemComponentMBean[])this._getHelper()._extendArray(this.getSystemComponents(), SystemComponentMBean.class, param0));
         } else {
            _new = new SystemComponentMBean[]{param0};
         }

         try {
            this.setSystemComponents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void removeSystemComponent(SystemComponentMBean param0) throws InvalidAttributeValueException {
      SystemComponentMBean[] _old = this.getSystemComponents();
      SystemComponentMBean[] _new = (SystemComponentMBean[])((SystemComponentMBean[])this._getHelper()._removeElement(_old, SystemComponentMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setSystemComponents(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            }

            throw new UndeclaredThrowableException(var5);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._ComponentType = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._SourcePath = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._SystemComponents = new SystemComponentMBean[0];
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
      return "SystemComponentConfiguration";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ComponentType")) {
         oldVal = this._ComponentType;
         this._ComponentType = (String)v;
         this._postSet(10, oldVal, this._ComponentType);
      } else if (name.equals("SourcePath")) {
         oldVal = this._SourcePath;
         this._SourcePath = (String)v;
         this._postSet(11, oldVal, this._SourcePath);
      } else if (name.equals("SystemComponents")) {
         SystemComponentMBean[] oldVal = this._SystemComponents;
         this._SystemComponents = (SystemComponentMBean[])((SystemComponentMBean[])v);
         this._postSet(12, oldVal, this._SystemComponents);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ComponentType")) {
         return this._ComponentType;
      } else if (name.equals("SourcePath")) {
         return this._SourcePath;
      } else {
         return name.equals("SystemComponents") ? this._SystemComponents : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("source-path")) {
                  return 11;
               }
               break;
            case 14:
               if (s.equals("component-type")) {
                  return 10;
               }
               break;
            case 16:
               if (s.equals("system-component")) {
                  return 12;
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
            case 10:
               return "component-type";
            case 11:
               return "source-path";
            case 12:
               return "system-component";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 12:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private SystemComponentConfigurationMBeanImpl bean;

      protected Helper(SystemComponentConfigurationMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ComponentType";
            case 11:
               return "SourcePath";
            case 12:
               return "SystemComponents";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ComponentType")) {
            return 10;
         } else if (propName.equals("SourcePath")) {
            return 11;
         } else {
            return propName.equals("SystemComponents") ? 12 : super.getPropertyIndex(propName);
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
            if (this.bean.isComponentTypeSet()) {
               buf.append("ComponentType");
               buf.append(String.valueOf(this.bean.getComponentType()));
            }

            if (this.bean.isSourcePathSet()) {
               buf.append("SourcePath");
               buf.append(String.valueOf(this.bean.getSourcePath()));
            }

            if (this.bean.isSystemComponentsSet()) {
               buf.append("SystemComponents");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSystemComponents())));
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
            SystemComponentConfigurationMBeanImpl otherTyped = (SystemComponentConfigurationMBeanImpl)other;
            this.computeDiff("ComponentType", this.bean.getComponentType(), otherTyped.getComponentType(), false);
            this.computeDiff("SourcePath", this.bean.getSourcePath(), otherTyped.getSourcePath(), false);
            this.computeDiff("SystemComponents", this.bean.getSystemComponents(), otherTyped.getSystemComponents(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SystemComponentConfigurationMBeanImpl original = (SystemComponentConfigurationMBeanImpl)event.getSourceBean();
            SystemComponentConfigurationMBeanImpl proposed = (SystemComponentConfigurationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ComponentType")) {
                  original.setComponentType(proposed.getComponentType());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("SourcePath")) {
                  original.setSourcePath(proposed.getSourcePath());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("SystemComponents")) {
                  original.setSystemComponentsAsString(proposed.getSystemComponentsAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            SystemComponentConfigurationMBeanImpl copy = (SystemComponentConfigurationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ComponentType")) && this.bean.isComponentTypeSet()) {
               copy.setComponentType(this.bean.getComponentType());
            }

            if ((excludeProps == null || !excludeProps.contains("SourcePath")) && this.bean.isSourcePathSet()) {
               copy.setSourcePath(this.bean.getSourcePath());
            }

            if ((excludeProps == null || !excludeProps.contains("SystemComponents")) && this.bean.isSystemComponentsSet()) {
               copy._unSet(copy, 12);
               copy.setSystemComponentsAsString(this.bean.getSystemComponentsAsString());
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
         this.inferSubTree(this.bean.getSystemComponents(), clazz, annotation);
      }
   }
}

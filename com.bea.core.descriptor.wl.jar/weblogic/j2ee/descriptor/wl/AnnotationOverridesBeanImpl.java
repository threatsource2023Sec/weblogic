package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class AnnotationOverridesBeanImpl extends AbstractDescriptorBean implements AnnotationOverridesBean, Serializable {
   private AnnotatedClassBean[] _AnnotatedClasses;
   private AnnotationDefinitionBean[] _AnnotationDefinitions;
   private EnumDefinitionBean[] _EnumDefinitions;
   private long _UpdateCount;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public AnnotationOverridesBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public AnnotationOverridesBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public AnnotationOverridesBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addAnnotatedClass(AnnotatedClassBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         AnnotatedClassBean[] _new;
         if (this._isSet(0)) {
            _new = (AnnotatedClassBean[])((AnnotatedClassBean[])this._getHelper()._extendArray(this.getAnnotatedClasses(), AnnotatedClassBean.class, param0));
         } else {
            _new = new AnnotatedClassBean[]{param0};
         }

         try {
            this.setAnnotatedClasses(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AnnotatedClassBean[] getAnnotatedClasses() {
      return this._AnnotatedClasses;
   }

   public boolean isAnnotatedClassesInherited() {
      return false;
   }

   public boolean isAnnotatedClassesSet() {
      return this._isSet(0);
   }

   public void removeAnnotatedClass(AnnotatedClassBean param0) {
      AnnotatedClassBean[] _old = this.getAnnotatedClasses();
      AnnotatedClassBean[] _new = (AnnotatedClassBean[])((AnnotatedClassBean[])this._getHelper()._removeElement(_old, AnnotatedClassBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setAnnotatedClasses(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setAnnotatedClasses(AnnotatedClassBean[] param0) throws InvalidAttributeValueException {
      AnnotatedClassBean[] param0 = param0 == null ? new AnnotatedClassBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AnnotatedClassBean[] _oldVal = this._AnnotatedClasses;
      this._AnnotatedClasses = (AnnotatedClassBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public AnnotatedClassBean createAnnotatedClass() {
      AnnotatedClassBeanImpl _val = new AnnotatedClassBeanImpl(this, -1);

      try {
         this.addAnnotatedClass(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addAnnotationDefinition(AnnotationDefinitionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         AnnotationDefinitionBean[] _new;
         if (this._isSet(1)) {
            _new = (AnnotationDefinitionBean[])((AnnotationDefinitionBean[])this._getHelper()._extendArray(this.getAnnotationDefinitions(), AnnotationDefinitionBean.class, param0));
         } else {
            _new = new AnnotationDefinitionBean[]{param0};
         }

         try {
            this.setAnnotationDefinitions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AnnotationDefinitionBean[] getAnnotationDefinitions() {
      return this._AnnotationDefinitions;
   }

   public boolean isAnnotationDefinitionsInherited() {
      return false;
   }

   public boolean isAnnotationDefinitionsSet() {
      return this._isSet(1);
   }

   public void removeAnnotationDefinition(AnnotationDefinitionBean param0) {
      AnnotationDefinitionBean[] _old = this.getAnnotationDefinitions();
      AnnotationDefinitionBean[] _new = (AnnotationDefinitionBean[])((AnnotationDefinitionBean[])this._getHelper()._removeElement(_old, AnnotationDefinitionBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setAnnotationDefinitions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setAnnotationDefinitions(AnnotationDefinitionBean[] param0) throws InvalidAttributeValueException {
      AnnotationDefinitionBean[] param0 = param0 == null ? new AnnotationDefinitionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AnnotationDefinitionBean[] _oldVal = this._AnnotationDefinitions;
      this._AnnotationDefinitions = (AnnotationDefinitionBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public AnnotationDefinitionBean createAnnotationDefinition() {
      AnnotationDefinitionBeanImpl _val = new AnnotationDefinitionBeanImpl(this, -1);

      try {
         this.addAnnotationDefinition(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addEnumDefinition(EnumDefinitionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         EnumDefinitionBean[] _new;
         if (this._isSet(2)) {
            _new = (EnumDefinitionBean[])((EnumDefinitionBean[])this._getHelper()._extendArray(this.getEnumDefinitions(), EnumDefinitionBean.class, param0));
         } else {
            _new = new EnumDefinitionBean[]{param0};
         }

         try {
            this.setEnumDefinitions(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EnumDefinitionBean[] getEnumDefinitions() {
      return this._EnumDefinitions;
   }

   public boolean isEnumDefinitionsInherited() {
      return false;
   }

   public boolean isEnumDefinitionsSet() {
      return this._isSet(2);
   }

   public void removeEnumDefinition(EnumDefinitionBean param0) {
      EnumDefinitionBean[] _old = this.getEnumDefinitions();
      EnumDefinitionBean[] _new = (EnumDefinitionBean[])((EnumDefinitionBean[])this._getHelper()._removeElement(_old, EnumDefinitionBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setEnumDefinitions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setEnumDefinitions(EnumDefinitionBean[] param0) throws InvalidAttributeValueException {
      EnumDefinitionBean[] param0 = param0 == null ? new EnumDefinitionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EnumDefinitionBean[] _oldVal = this._EnumDefinitions;
      this._EnumDefinitions = (EnumDefinitionBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public EnumDefinitionBean createEnumDefinition() {
      EnumDefinitionBeanImpl _val = new EnumDefinitionBeanImpl(this, -1);

      try {
         this.addEnumDefinition(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public long getUpdateCount() {
      return this._UpdateCount;
   }

   public boolean isUpdateCountInherited() {
      return false;
   }

   public boolean isUpdateCountSet() {
      return this._isSet(3);
   }

   public void setUpdateCount(long param0) {
      long _oldVal = this._UpdateCount;
      this._UpdateCount = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(4);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(4, _oldVal, param0);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._AnnotatedClasses = new AnnotatedClassBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._AnnotationDefinitions = new AnnotationDefinitionBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._EnumDefinitions = new EnumDefinitionBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._UpdateCount = 0L;
               if (initOne) {
                  break;
               }
            case 4:
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("version")) {
                  return 4;
               }
               break;
            case 12:
               if (s.equals("update-count")) {
                  return 3;
               }
               break;
            case 15:
               if (s.equals("annotated-class")) {
                  return 0;
               }

               if (s.equals("enum-definition")) {
                  return 2;
               }
               break;
            case 21:
               if (s.equals("annotation-definition")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new AnnotatedClassBeanImpl.SchemaHelper2();
            case 1:
               return new AnnotationDefinitionBeanImpl.SchemaHelper2();
            case 2:
               return new EnumDefinitionBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "annotation-overrides";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "annotated-class";
            case 1:
               return "annotation-definition";
            case 2:
               return "enum-definition";
            case 3:
               return "update-count";
            case 4:
               return "version";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AnnotationOverridesBeanImpl bean;

      protected Helper(AnnotationOverridesBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AnnotatedClasses";
            case 1:
               return "AnnotationDefinitions";
            case 2:
               return "EnumDefinitions";
            case 3:
               return "UpdateCount";
            case 4:
               return "Version";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AnnotatedClasses")) {
            return 0;
         } else if (propName.equals("AnnotationDefinitions")) {
            return 1;
         } else if (propName.equals("EnumDefinitions")) {
            return 2;
         } else if (propName.equals("UpdateCount")) {
            return 3;
         } else {
            return propName.equals("Version") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAnnotatedClasses()));
         iterators.add(new ArrayIterator(this.bean.getAnnotationDefinitions()));
         iterators.add(new ArrayIterator(this.bean.getEnumDefinitions()));
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

            int i;
            for(i = 0; i < this.bean.getAnnotatedClasses().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAnnotatedClasses()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getAnnotationDefinitions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAnnotationDefinitions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getEnumDefinitions().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEnumDefinitions()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isUpdateCountSet()) {
               buf.append("UpdateCount");
               buf.append(String.valueOf(this.bean.getUpdateCount()));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
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
            AnnotationOverridesBeanImpl otherTyped = (AnnotationOverridesBeanImpl)other;
            this.computeChildDiff("AnnotatedClasses", this.bean.getAnnotatedClasses(), otherTyped.getAnnotatedClasses(), true);
            this.computeChildDiff("AnnotationDefinitions", this.bean.getAnnotationDefinitions(), otherTyped.getAnnotationDefinitions(), false);
            this.computeChildDiff("EnumDefinitions", this.bean.getEnumDefinitions(), otherTyped.getEnumDefinitions(), false);
            this.computeDiff("UpdateCount", this.bean.getUpdateCount(), otherTyped.getUpdateCount(), true);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AnnotationOverridesBeanImpl original = (AnnotationOverridesBeanImpl)event.getSourceBean();
            AnnotationOverridesBeanImpl proposed = (AnnotationOverridesBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AnnotatedClasses")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAnnotatedClass((AnnotatedClassBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAnnotatedClass((AnnotatedClassBean)update.getRemovedObject());
                  }

                  if (original.getAnnotatedClasses() == null || original.getAnnotatedClasses().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("AnnotationDefinitions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAnnotationDefinition((AnnotationDefinitionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAnnotationDefinition((AnnotationDefinitionBean)update.getRemovedObject());
                  }

                  if (original.getAnnotationDefinitions() == null || original.getAnnotationDefinitions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("EnumDefinitions")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEnumDefinition((EnumDefinitionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEnumDefinition((EnumDefinitionBean)update.getRemovedObject());
                  }

                  if (original.getEnumDefinitions() == null || original.getEnumDefinitions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("UpdateCount")) {
                  original.setUpdateCount(proposed.getUpdateCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            AnnotationOverridesBeanImpl copy = (AnnotationOverridesBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AnnotatedClasses")) && this.bean.isAnnotatedClassesSet() && !copy._isSet(0)) {
               AnnotatedClassBean[] oldAnnotatedClasses = this.bean.getAnnotatedClasses();
               AnnotatedClassBean[] newAnnotatedClasses = new AnnotatedClassBean[oldAnnotatedClasses.length];

               for(i = 0; i < newAnnotatedClasses.length; ++i) {
                  newAnnotatedClasses[i] = (AnnotatedClassBean)((AnnotatedClassBean)this.createCopy((AbstractDescriptorBean)oldAnnotatedClasses[i], includeObsolete));
               }

               copy.setAnnotatedClasses(newAnnotatedClasses);
            }

            if ((excludeProps == null || !excludeProps.contains("AnnotationDefinitions")) && this.bean.isAnnotationDefinitionsSet() && !copy._isSet(1)) {
               AnnotationDefinitionBean[] oldAnnotationDefinitions = this.bean.getAnnotationDefinitions();
               AnnotationDefinitionBean[] newAnnotationDefinitions = new AnnotationDefinitionBean[oldAnnotationDefinitions.length];

               for(i = 0; i < newAnnotationDefinitions.length; ++i) {
                  newAnnotationDefinitions[i] = (AnnotationDefinitionBean)((AnnotationDefinitionBean)this.createCopy((AbstractDescriptorBean)oldAnnotationDefinitions[i], includeObsolete));
               }

               copy.setAnnotationDefinitions(newAnnotationDefinitions);
            }

            if ((excludeProps == null || !excludeProps.contains("EnumDefinitions")) && this.bean.isEnumDefinitionsSet() && !copy._isSet(2)) {
               EnumDefinitionBean[] oldEnumDefinitions = this.bean.getEnumDefinitions();
               EnumDefinitionBean[] newEnumDefinitions = new EnumDefinitionBean[oldEnumDefinitions.length];

               for(i = 0; i < newEnumDefinitions.length; ++i) {
                  newEnumDefinitions[i] = (EnumDefinitionBean)((EnumDefinitionBean)this.createCopy((AbstractDescriptorBean)oldEnumDefinitions[i], includeObsolete));
               }

               copy.setEnumDefinitions(newEnumDefinitions);
            }

            if ((excludeProps == null || !excludeProps.contains("UpdateCount")) && this.bean.isUpdateCountSet()) {
               copy.setUpdateCount(this.bean.getUpdateCount());
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
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
         this.inferSubTree(this.bean.getAnnotatedClasses(), clazz, annotation);
         this.inferSubTree(this.bean.getAnnotationDefinitions(), clazz, annotation);
         this.inferSubTree(this.bean.getEnumDefinitions(), clazz, annotation);
      }
   }
}

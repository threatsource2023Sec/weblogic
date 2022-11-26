package weblogic.j2ee.descriptor.wl;

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

public class ClassloaderStructureBeanImpl extends AbstractDescriptorBean implements ClassloaderStructureBean, Serializable {
   private ClassloaderStructureBean[] _ClassloaderStructures;
   private ModuleRefBean[] _ModuleRefs;
   private static SchemaHelper2 _schemaHelper;

   public ClassloaderStructureBeanImpl() {
      this._initializeProperty(-1);
   }

   public ClassloaderStructureBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ClassloaderStructureBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addModuleRef(ModuleRefBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         ModuleRefBean[] _new;
         if (this._isSet(0)) {
            _new = (ModuleRefBean[])((ModuleRefBean[])this._getHelper()._extendArray(this.getModuleRefs(), ModuleRefBean.class, param0));
         } else {
            _new = new ModuleRefBean[]{param0};
         }

         try {
            this.setModuleRefs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ModuleRefBean[] getModuleRefs() {
      return this._ModuleRefs;
   }

   public boolean isModuleRefsInherited() {
      return false;
   }

   public boolean isModuleRefsSet() {
      return this._isSet(0);
   }

   public void removeModuleRef(ModuleRefBean param0) {
      this.destroyModuleRef(param0);
   }

   public void setModuleRefs(ModuleRefBean[] param0) throws InvalidAttributeValueException {
      ModuleRefBean[] param0 = param0 == null ? new ModuleRefBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ModuleRefBean[] _oldVal = this._ModuleRefs;
      this._ModuleRefs = (ModuleRefBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public ModuleRefBean createModuleRef() {
      ModuleRefBeanImpl _val = new ModuleRefBeanImpl(this, -1);

      try {
         this.addModuleRef(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyModuleRef(ModuleRefBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         ModuleRefBean[] _old = this.getModuleRefs();
         ModuleRefBean[] _new = (ModuleRefBean[])((ModuleRefBean[])this._getHelper()._removeElement(_old, ModuleRefBean.class, param0));
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
               this.setModuleRefs(_new);
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

   public void addClassloaderStructure(ClassloaderStructureBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         ClassloaderStructureBean[] _new;
         if (this._isSet(1)) {
            _new = (ClassloaderStructureBean[])((ClassloaderStructureBean[])this._getHelper()._extendArray(this.getClassloaderStructures(), ClassloaderStructureBean.class, param0));
         } else {
            _new = new ClassloaderStructureBean[]{param0};
         }

         try {
            this.setClassloaderStructures(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ClassloaderStructureBean[] getClassloaderStructures() {
      return this._ClassloaderStructures;
   }

   public boolean isClassloaderStructuresInherited() {
      return false;
   }

   public boolean isClassloaderStructuresSet() {
      return this._isSet(1);
   }

   public void removeClassloaderStructure(ClassloaderStructureBean param0) {
      this.destroyClassloaderStructure(param0);
   }

   public void setClassloaderStructures(ClassloaderStructureBean[] param0) throws InvalidAttributeValueException {
      ClassloaderStructureBean[] param0 = param0 == null ? new ClassloaderStructureBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ClassloaderStructureBean[] _oldVal = this._ClassloaderStructures;
      this._ClassloaderStructures = (ClassloaderStructureBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public ClassloaderStructureBean createClassloaderStructure() {
      ClassloaderStructureBeanImpl _val = new ClassloaderStructureBeanImpl(this, -1);

      try {
         this.addClassloaderStructure(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyClassloaderStructure(ClassloaderStructureBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         ClassloaderStructureBean[] _old = this.getClassloaderStructures();
         ClassloaderStructureBean[] _new = (ClassloaderStructureBean[])((ClassloaderStructureBean[])this._getHelper()._removeElement(_old, ClassloaderStructureBean.class, param0));
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
               this.setClassloaderStructures(_new);
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
               this._ClassloaderStructures = new ClassloaderStructureBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ModuleRefs = new ModuleRefBean[0];
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
            case 10:
               if (s.equals("module-ref")) {
                  return 0;
               }
               break;
            case 21:
               if (s.equals("classloader-structure")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new ModuleRefBeanImpl.SchemaHelper2();
            case 1:
               return new SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "module-ref";
            case 1:
               return "classloader-structure";
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
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ClassloaderStructureBeanImpl bean;

      protected Helper(ClassloaderStructureBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ModuleRefs";
            case 1:
               return "ClassloaderStructures";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClassloaderStructures")) {
            return 1;
         } else {
            return propName.equals("ModuleRefs") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getClassloaderStructures()));
         iterators.add(new ArrayIterator(this.bean.getModuleRefs()));
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
            for(i = 0; i < this.bean.getClassloaderStructures().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getClassloaderStructures()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getModuleRefs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getModuleRefs()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            ClassloaderStructureBeanImpl otherTyped = (ClassloaderStructureBeanImpl)other;
            this.computeChildDiff("ClassloaderStructures", this.bean.getClassloaderStructures(), otherTyped.getClassloaderStructures(), false);
            this.computeChildDiff("ModuleRefs", this.bean.getModuleRefs(), otherTyped.getModuleRefs(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ClassloaderStructureBeanImpl original = (ClassloaderStructureBeanImpl)event.getSourceBean();
            ClassloaderStructureBeanImpl proposed = (ClassloaderStructureBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClassloaderStructures")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addClassloaderStructure((ClassloaderStructureBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeClassloaderStructure((ClassloaderStructureBean)update.getRemovedObject());
                  }

                  if (original.getClassloaderStructures() == null || original.getClassloaderStructures().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("ModuleRefs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addModuleRef((ModuleRefBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeModuleRef((ModuleRefBean)update.getRemovedObject());
                  }

                  if (original.getModuleRefs() == null || original.getModuleRefs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
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
            ClassloaderStructureBeanImpl copy = (ClassloaderStructureBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("ClassloaderStructures")) && this.bean.isClassloaderStructuresSet() && !copy._isSet(1)) {
               ClassloaderStructureBean[] oldClassloaderStructures = this.bean.getClassloaderStructures();
               ClassloaderStructureBean[] newClassloaderStructures = new ClassloaderStructureBean[oldClassloaderStructures.length];

               for(i = 0; i < newClassloaderStructures.length; ++i) {
                  newClassloaderStructures[i] = (ClassloaderStructureBean)((ClassloaderStructureBean)this.createCopy((AbstractDescriptorBean)oldClassloaderStructures[i], includeObsolete));
               }

               copy.setClassloaderStructures(newClassloaderStructures);
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleRefs")) && this.bean.isModuleRefsSet() && !copy._isSet(0)) {
               ModuleRefBean[] oldModuleRefs = this.bean.getModuleRefs();
               ModuleRefBean[] newModuleRefs = new ModuleRefBean[oldModuleRefs.length];

               for(i = 0; i < newModuleRefs.length; ++i) {
                  newModuleRefs[i] = (ModuleRefBean)((ModuleRefBean)this.createCopy((AbstractDescriptorBean)oldModuleRefs[i], includeObsolete));
               }

               copy.setModuleRefs(newModuleRefs);
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
         this.inferSubTree(this.bean.getClassloaderStructures(), clazz, annotation);
         this.inferSubTree(this.bean.getModuleRefs(), clazz, annotation);
      }
   }
}

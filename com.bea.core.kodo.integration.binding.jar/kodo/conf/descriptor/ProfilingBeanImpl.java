package kodo.conf.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import kodo.conf.customizers.ProfilingBeanCustomizer;
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

public class ProfilingBeanImpl extends AbstractDescriptorBean implements ProfilingBean, Serializable {
   private ExportProfilingBean _ExportProfiling;
   private GUIProfilingBean _GUIProfiling;
   private LocalProfilingBean _LocalProfiling;
   private NoneProfilingBean _NoneProfiling;
   private transient ProfilingBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public ProfilingBeanImpl() {
      try {
         this._customizer = new ProfilingBeanCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ProfilingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new ProfilingBeanCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ProfilingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new ProfilingBeanCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public NoneProfilingBean getNoneProfiling() {
      return this._NoneProfiling;
   }

   public boolean isNoneProfilingInherited() {
      return false;
   }

   public boolean isNoneProfilingSet() {
      return this._isSet(0);
   }

   public void setNoneProfiling(NoneProfilingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getNoneProfiling() != null && param0 != this.getNoneProfiling()) {
         throw new BeanAlreadyExistsException(this.getNoneProfiling() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NoneProfilingBean _oldVal = this._NoneProfiling;
         this._NoneProfiling = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public NoneProfilingBean createNoneProfiling() {
      NoneProfilingBeanImpl _val = new NoneProfilingBeanImpl(this, -1);

      try {
         this.setNoneProfiling(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyNoneProfiling() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._NoneProfiling;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setNoneProfiling((NoneProfilingBean)null);
               this._unSet(0);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public LocalProfilingBean getLocalProfiling() {
      return this._LocalProfiling;
   }

   public boolean isLocalProfilingInherited() {
      return false;
   }

   public boolean isLocalProfilingSet() {
      return this._isSet(1);
   }

   public void setLocalProfiling(LocalProfilingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getLocalProfiling() != null && param0 != this.getLocalProfiling()) {
         throw new BeanAlreadyExistsException(this.getLocalProfiling() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         LocalProfilingBean _oldVal = this._LocalProfiling;
         this._LocalProfiling = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public LocalProfilingBean createLocalProfiling() {
      LocalProfilingBeanImpl _val = new LocalProfilingBeanImpl(this, -1);

      try {
         this.setLocalProfiling(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyLocalProfiling() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._LocalProfiling;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setLocalProfiling((LocalProfilingBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ExportProfilingBean getExportProfiling() {
      return this._ExportProfiling;
   }

   public boolean isExportProfilingInherited() {
      return false;
   }

   public boolean isExportProfilingSet() {
      return this._isSet(2);
   }

   public void setExportProfiling(ExportProfilingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getExportProfiling() != null && param0 != this.getExportProfiling()) {
         throw new BeanAlreadyExistsException(this.getExportProfiling() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ExportProfilingBean _oldVal = this._ExportProfiling;
         this._ExportProfiling = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public ExportProfilingBean createExportProfiling() {
      ExportProfilingBeanImpl _val = new ExportProfilingBeanImpl(this, -1);

      try {
         this.setExportProfiling(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyExportProfiling() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ExportProfiling;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setExportProfiling((ExportProfilingBean)null);
               this._unSet(2);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public GUIProfilingBean getGUIProfiling() {
      return this._GUIProfiling;
   }

   public boolean isGUIProfilingInherited() {
      return false;
   }

   public boolean isGUIProfilingSet() {
      return this._isSet(3);
   }

   public void setGUIProfiling(GUIProfilingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getGUIProfiling() != null && param0 != this.getGUIProfiling()) {
         throw new BeanAlreadyExistsException(this.getGUIProfiling() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         GUIProfilingBean _oldVal = this._GUIProfiling;
         this._GUIProfiling = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public GUIProfilingBean createGUIProfiling() {
      GUIProfilingBeanImpl _val = new GUIProfilingBeanImpl(this, -1);

      try {
         this.setGUIProfiling(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyGUIProfiling() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._GUIProfiling;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setGUIProfiling((GUIProfilingBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Class[] getProfilingTypes() {
      return this._customizer.getProfilingTypes();
   }

   public ProfilingBean getProfiling() {
      return this._customizer.getProfiling();
   }

   public ProfilingBean createProfiling(Class param0) {
      return this._customizer.createProfiling(param0);
   }

   public void destroyProfiling() {
      this._customizer.destroyProfiling();
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._ExportProfiling = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._GUIProfiling = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._LocalProfiling = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._NoneProfiling = null;
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
            case 13:
               if (s.equals("gui-profiling")) {
                  return 3;
               }
               break;
            case 14:
               if (s.equals("none-profiling")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("local-profiling")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("export-profiling")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new NoneProfilingBeanImpl.SchemaHelper2();
            case 1:
               return new LocalProfilingBeanImpl.SchemaHelper2();
            case 2:
               return new ExportProfilingBeanImpl.SchemaHelper2();
            case 3:
               return new GUIProfilingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "none-profiling";
            case 1:
               return "local-profiling";
            case 2:
               return "export-profiling";
            case 3:
               return "gui-profiling";
            default:
               return super.getElementName(propIndex);
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
            case 3:
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
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ProfilingBeanImpl bean;

      protected Helper(ProfilingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "NoneProfiling";
            case 1:
               return "LocalProfiling";
            case 2:
               return "ExportProfiling";
            case 3:
               return "GUIProfiling";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ExportProfiling")) {
            return 2;
         } else if (propName.equals("GUIProfiling")) {
            return 3;
         } else if (propName.equals("LocalProfiling")) {
            return 1;
         } else {
            return propName.equals("NoneProfiling") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getExportProfiling() != null) {
            iterators.add(new ArrayIterator(new ExportProfilingBean[]{this.bean.getExportProfiling()}));
         }

         if (this.bean.getGUIProfiling() != null) {
            iterators.add(new ArrayIterator(new GUIProfilingBean[]{this.bean.getGUIProfiling()}));
         }

         if (this.bean.getLocalProfiling() != null) {
            iterators.add(new ArrayIterator(new LocalProfilingBean[]{this.bean.getLocalProfiling()}));
         }

         if (this.bean.getNoneProfiling() != null) {
            iterators.add(new ArrayIterator(new NoneProfilingBean[]{this.bean.getNoneProfiling()}));
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
            childValue = this.computeChildHashValue(this.bean.getExportProfiling());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getGUIProfiling());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getLocalProfiling());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getNoneProfiling());
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
            ProfilingBeanImpl otherTyped = (ProfilingBeanImpl)other;
            this.computeChildDiff("ExportProfiling", this.bean.getExportProfiling(), otherTyped.getExportProfiling(), false);
            this.computeChildDiff("GUIProfiling", this.bean.getGUIProfiling(), otherTyped.getGUIProfiling(), false);
            this.computeChildDiff("LocalProfiling", this.bean.getLocalProfiling(), otherTyped.getLocalProfiling(), false);
            this.computeChildDiff("NoneProfiling", this.bean.getNoneProfiling(), otherTyped.getNoneProfiling(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ProfilingBeanImpl original = (ProfilingBeanImpl)event.getSourceBean();
            ProfilingBeanImpl proposed = (ProfilingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ExportProfiling")) {
                  if (type == 2) {
                     original.setExportProfiling((ExportProfilingBean)this.createCopy((AbstractDescriptorBean)proposed.getExportProfiling()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ExportProfiling", (DescriptorBean)original.getExportProfiling());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("GUIProfiling")) {
                  if (type == 2) {
                     original.setGUIProfiling((GUIProfilingBean)this.createCopy((AbstractDescriptorBean)proposed.getGUIProfiling()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("GUIProfiling", (DescriptorBean)original.getGUIProfiling());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("LocalProfiling")) {
                  if (type == 2) {
                     original.setLocalProfiling((LocalProfilingBean)this.createCopy((AbstractDescriptorBean)proposed.getLocalProfiling()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("LocalProfiling", (DescriptorBean)original.getLocalProfiling());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("NoneProfiling")) {
                  if (type == 2) {
                     original.setNoneProfiling((NoneProfilingBean)this.createCopy((AbstractDescriptorBean)proposed.getNoneProfiling()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("NoneProfiling", (DescriptorBean)original.getNoneProfiling());
                  }

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
            ProfilingBeanImpl copy = (ProfilingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ExportProfiling")) && this.bean.isExportProfilingSet() && !copy._isSet(2)) {
               Object o = this.bean.getExportProfiling();
               copy.setExportProfiling((ExportProfilingBean)null);
               copy.setExportProfiling(o == null ? null : (ExportProfilingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("GUIProfiling")) && this.bean.isGUIProfilingSet() && !copy._isSet(3)) {
               Object o = this.bean.getGUIProfiling();
               copy.setGUIProfiling((GUIProfilingBean)null);
               copy.setGUIProfiling(o == null ? null : (GUIProfilingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LocalProfiling")) && this.bean.isLocalProfilingSet() && !copy._isSet(1)) {
               Object o = this.bean.getLocalProfiling();
               copy.setLocalProfiling((LocalProfilingBean)null);
               copy.setLocalProfiling(o == null ? null : (LocalProfilingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NoneProfiling")) && this.bean.isNoneProfilingSet() && !copy._isSet(0)) {
               Object o = this.bean.getNoneProfiling();
               copy.setNoneProfiling((NoneProfilingBean)null);
               copy.setNoneProfiling(o == null ? null : (NoneProfilingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getExportProfiling(), clazz, annotation);
         this.inferSubTree(this.bean.getGUIProfiling(), clazz, annotation);
         this.inferSubTree(this.bean.getLocalProfiling(), clazz, annotation);
         this.inferSubTree(this.bean.getNoneProfiling(), clazz, annotation);
      }
   }
}

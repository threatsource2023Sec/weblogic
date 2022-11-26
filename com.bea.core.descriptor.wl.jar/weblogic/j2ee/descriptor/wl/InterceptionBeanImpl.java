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

public class InterceptionBeanImpl extends AbstractDescriptorBean implements InterceptionBean, Serializable {
   private AssociationBean[] _Associations;
   private ProcessorTypeBean[] _ProcessorTypes;
   private ProcessorBean[] _Processors;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public InterceptionBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public InterceptionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public InterceptionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addAssociation(AssociationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         AssociationBean[] _new;
         if (this._isSet(0)) {
            _new = (AssociationBean[])((AssociationBean[])this._getHelper()._extendArray(this.getAssociations(), AssociationBean.class, param0));
         } else {
            _new = new AssociationBean[]{param0};
         }

         try {
            this.setAssociations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AssociationBean[] getAssociations() {
      return this._Associations;
   }

   public boolean isAssociationsInherited() {
      return false;
   }

   public boolean isAssociationsSet() {
      return this._isSet(0);
   }

   public void removeAssociation(AssociationBean param0) {
      this.destroyAssociation(param0);
   }

   public void setAssociations(AssociationBean[] param0) throws InvalidAttributeValueException {
      AssociationBean[] param0 = param0 == null ? new AssociationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AssociationBean[] _oldVal = this._Associations;
      this._Associations = (AssociationBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public AssociationBean createAssociation() {
      AssociationBeanImpl _val = new AssociationBeanImpl(this, -1);

      try {
         this.addAssociation(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAssociation(AssociationBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         AssociationBean[] _old = this.getAssociations();
         AssociationBean[] _new = (AssociationBean[])((AssociationBean[])this._getHelper()._removeElement(_old, AssociationBean.class, param0));
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
               this.setAssociations(_new);
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

   public void addProcessor(ProcessorBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         ProcessorBean[] _new;
         if (this._isSet(1)) {
            _new = (ProcessorBean[])((ProcessorBean[])this._getHelper()._extendArray(this.getProcessors(), ProcessorBean.class, param0));
         } else {
            _new = new ProcessorBean[]{param0};
         }

         try {
            this.setProcessors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ProcessorBean[] getProcessors() {
      return this._Processors;
   }

   public boolean isProcessorsInherited() {
      return false;
   }

   public boolean isProcessorsSet() {
      return this._isSet(1);
   }

   public void removeProcessor(ProcessorBean param0) {
      this.destroyProcessor(param0);
   }

   public void setProcessors(ProcessorBean[] param0) throws InvalidAttributeValueException {
      ProcessorBean[] param0 = param0 == null ? new ProcessorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ProcessorBean[] _oldVal = this._Processors;
      this._Processors = (ProcessorBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public ProcessorBean createProcessor() {
      ProcessorBeanImpl _val = new ProcessorBeanImpl(this, -1);

      try {
         this.addProcessor(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyProcessor(ProcessorBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         ProcessorBean[] _old = this.getProcessors();
         ProcessorBean[] _new = (ProcessorBean[])((ProcessorBean[])this._getHelper()._removeElement(_old, ProcessorBean.class, param0));
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
               this.setProcessors(_new);
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

   public void addProcessorType(ProcessorTypeBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         ProcessorTypeBean[] _new;
         if (this._isSet(2)) {
            _new = (ProcessorTypeBean[])((ProcessorTypeBean[])this._getHelper()._extendArray(this.getProcessorTypes(), ProcessorTypeBean.class, param0));
         } else {
            _new = new ProcessorTypeBean[]{param0};
         }

         try {
            this.setProcessorTypes(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ProcessorTypeBean[] getProcessorTypes() {
      return this._ProcessorTypes;
   }

   public boolean isProcessorTypesInherited() {
      return false;
   }

   public boolean isProcessorTypesSet() {
      return this._isSet(2);
   }

   public void removeProcessorType(ProcessorTypeBean param0) {
      this.destroyProcessorType(param0);
   }

   public void setProcessorTypes(ProcessorTypeBean[] param0) throws InvalidAttributeValueException {
      ProcessorTypeBean[] param0 = param0 == null ? new ProcessorTypeBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ProcessorTypeBean[] _oldVal = this._ProcessorTypes;
      this._ProcessorTypes = (ProcessorTypeBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public ProcessorTypeBean createProcessorType() {
      ProcessorTypeBeanImpl _val = new ProcessorTypeBeanImpl(this, -1);

      try {
         this.addProcessorType(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyProcessorType(ProcessorTypeBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         ProcessorTypeBean[] _old = this.getProcessorTypes();
         ProcessorTypeBean[] _new = (ProcessorTypeBean[])((ProcessorTypeBean[])this._getHelper()._removeElement(_old, ProcessorTypeBean.class, param0));
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
               this.setProcessorTypes(_new);
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

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(3);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(3, _oldVal, param0);
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
               this._Associations = new AssociationBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._ProcessorTypes = new ProcessorTypeBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Processors = new ProcessorBean[0];
               if (initOne) {
                  break;
               }
            case 3:
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
      return "http://xmlns.oracle.com/weblogic/weblogic-interception/1.0/weblogic-interception.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-interception";
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
                  return 3;
               }
            case 8:
            case 10:
            case 12:
            case 13:
            default:
               break;
            case 9:
               if (s.equals("processor")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("association")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("processor-type")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new AssociationBeanImpl.SchemaHelper2();
            case 1:
               return new ProcessorBeanImpl.SchemaHelper2();
            case 2:
               return new ProcessorTypeBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "weblogic-interception";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "association";
            case 1:
               return "processor";
            case 2:
               return "processor-type";
            case 3:
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
      private InterceptionBeanImpl bean;

      protected Helper(InterceptionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Associations";
            case 1:
               return "Processors";
            case 2:
               return "ProcessorTypes";
            case 3:
               return "Version";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Associations")) {
            return 0;
         } else if (propName.equals("ProcessorTypes")) {
            return 2;
         } else if (propName.equals("Processors")) {
            return 1;
         } else {
            return propName.equals("Version") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAssociations()));
         iterators.add(new ArrayIterator(this.bean.getProcessorTypes()));
         iterators.add(new ArrayIterator(this.bean.getProcessors()));
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
            for(i = 0; i < this.bean.getAssociations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAssociations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getProcessorTypes().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getProcessorTypes()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getProcessors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getProcessors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            InterceptionBeanImpl otherTyped = (InterceptionBeanImpl)other;
            this.computeChildDiff("Associations", this.bean.getAssociations(), otherTyped.getAssociations(), false);
            this.computeChildDiff("ProcessorTypes", this.bean.getProcessorTypes(), otherTyped.getProcessorTypes(), false);
            this.computeChildDiff("Processors", this.bean.getProcessors(), otherTyped.getProcessors(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InterceptionBeanImpl original = (InterceptionBeanImpl)event.getSourceBean();
            InterceptionBeanImpl proposed = (InterceptionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Associations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAssociation((AssociationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAssociation((AssociationBean)update.getRemovedObject());
                  }

                  if (original.getAssociations() == null || original.getAssociations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("ProcessorTypes")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addProcessorType((ProcessorTypeBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeProcessorType((ProcessorTypeBean)update.getRemovedObject());
                  }

                  if (original.getProcessorTypes() == null || original.getProcessorTypes().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Processors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addProcessor((ProcessorBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeProcessor((ProcessorBean)update.getRemovedObject());
                  }

                  if (original.getProcessors() == null || original.getProcessors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            InterceptionBeanImpl copy = (InterceptionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("Associations")) && this.bean.isAssociationsSet() && !copy._isSet(0)) {
               AssociationBean[] oldAssociations = this.bean.getAssociations();
               AssociationBean[] newAssociations = new AssociationBean[oldAssociations.length];

               for(i = 0; i < newAssociations.length; ++i) {
                  newAssociations[i] = (AssociationBean)((AssociationBean)this.createCopy((AbstractDescriptorBean)oldAssociations[i], includeObsolete));
               }

               copy.setAssociations(newAssociations);
            }

            if ((excludeProps == null || !excludeProps.contains("ProcessorTypes")) && this.bean.isProcessorTypesSet() && !copy._isSet(2)) {
               ProcessorTypeBean[] oldProcessorTypes = this.bean.getProcessorTypes();
               ProcessorTypeBean[] newProcessorTypes = new ProcessorTypeBean[oldProcessorTypes.length];

               for(i = 0; i < newProcessorTypes.length; ++i) {
                  newProcessorTypes[i] = (ProcessorTypeBean)((ProcessorTypeBean)this.createCopy((AbstractDescriptorBean)oldProcessorTypes[i], includeObsolete));
               }

               copy.setProcessorTypes(newProcessorTypes);
            }

            if ((excludeProps == null || !excludeProps.contains("Processors")) && this.bean.isProcessorsSet() && !copy._isSet(1)) {
               ProcessorBean[] oldProcessors = this.bean.getProcessors();
               ProcessorBean[] newProcessors = new ProcessorBean[oldProcessors.length];

               for(i = 0; i < newProcessors.length; ++i) {
                  newProcessors[i] = (ProcessorBean)((ProcessorBean)this.createCopy((AbstractDescriptorBean)oldProcessors[i], includeObsolete));
               }

               copy.setProcessors(newProcessors);
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
         this.inferSubTree(this.bean.getAssociations(), clazz, annotation);
         this.inferSubTree(this.bean.getProcessorTypes(), clazz, annotation);
         this.inferSubTree(this.bean.getProcessors(), clazz, annotation);
      }
   }
}

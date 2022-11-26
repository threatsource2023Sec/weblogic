package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SelfTuningMBeanImpl extends ConfigurationMBeanImpl implements SelfTuningMBean, Serializable {
   private CapacityMBean[] _Capacities;
   private ContextRequestClassMBean[] _ContextRequestClasses;
   private FairShareRequestClassMBean[] _FairShareRequestClasses;
   private MaxThreadsConstraintMBean[] _MaxThreadsConstraints;
   private MinThreadsConstraintMBean[] _MinThreadsConstraints;
   private int _PartitionFairShare;
   private ResponseTimeRequestClassMBean[] _ResponseTimeRequestClasses;
   private WorkManagerMBean[] _WorkManagers;
   private static SchemaHelper2 _schemaHelper;

   public SelfTuningMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SelfTuningMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SelfTuningMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addFairShareRequestClass(FairShareRequestClassMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         FairShareRequestClassMBean[] _new;
         if (this._isSet(10)) {
            _new = (FairShareRequestClassMBean[])((FairShareRequestClassMBean[])this._getHelper()._extendArray(this.getFairShareRequestClasses(), FairShareRequestClassMBean.class, param0));
         } else {
            _new = new FairShareRequestClassMBean[]{param0};
         }

         try {
            this.setFairShareRequestClasses(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public FairShareRequestClassMBean[] getFairShareRequestClasses() {
      return this._FairShareRequestClasses;
   }

   public boolean isFairShareRequestClassesInherited() {
      return false;
   }

   public boolean isFairShareRequestClassesSet() {
      return this._isSet(10);
   }

   public void removeFairShareRequestClass(FairShareRequestClassMBean param0) {
      this.destroyFairShareRequestClass(param0);
   }

   public void setFairShareRequestClasses(FairShareRequestClassMBean[] param0) throws InvalidAttributeValueException {
      FairShareRequestClassMBean[] param0 = param0 == null ? new FairShareRequestClassMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      FairShareRequestClassMBean[] _oldVal = this._FairShareRequestClasses;
      this._FairShareRequestClasses = (FairShareRequestClassMBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public FairShareRequestClassMBean createFairShareRequestClass(String param0) {
      FairShareRequestClassMBeanImpl lookup = (FairShareRequestClassMBeanImpl)this.lookupFairShareRequestClass(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         FairShareRequestClassMBeanImpl _val = new FairShareRequestClassMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addFairShareRequestClass(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyFairShareRequestClass(FairShareRequestClassMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         FairShareRequestClassMBean[] _old = this.getFairShareRequestClasses();
         FairShareRequestClassMBean[] _new = (FairShareRequestClassMBean[])((FairShareRequestClassMBean[])this._getHelper()._removeElement(_old, FairShareRequestClassMBean.class, param0));
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
               this.setFairShareRequestClasses(_new);
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

   public FairShareRequestClassMBean lookupFairShareRequestClass(String param0) {
      Object[] aary = (Object[])this._FairShareRequestClasses;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      FairShareRequestClassMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (FairShareRequestClassMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addResponseTimeRequestClass(ResponseTimeRequestClassMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         ResponseTimeRequestClassMBean[] _new;
         if (this._isSet(11)) {
            _new = (ResponseTimeRequestClassMBean[])((ResponseTimeRequestClassMBean[])this._getHelper()._extendArray(this.getResponseTimeRequestClasses(), ResponseTimeRequestClassMBean.class, param0));
         } else {
            _new = new ResponseTimeRequestClassMBean[]{param0};
         }

         try {
            this.setResponseTimeRequestClasses(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ResponseTimeRequestClassMBean[] getResponseTimeRequestClasses() {
      return this._ResponseTimeRequestClasses;
   }

   public boolean isResponseTimeRequestClassesInherited() {
      return false;
   }

   public boolean isResponseTimeRequestClassesSet() {
      return this._isSet(11);
   }

   public void removeResponseTimeRequestClass(ResponseTimeRequestClassMBean param0) {
      this.destroyResponseTimeRequestClass(param0);
   }

   public void setResponseTimeRequestClasses(ResponseTimeRequestClassMBean[] param0) throws InvalidAttributeValueException {
      ResponseTimeRequestClassMBean[] param0 = param0 == null ? new ResponseTimeRequestClassMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ResponseTimeRequestClassMBean[] _oldVal = this._ResponseTimeRequestClasses;
      this._ResponseTimeRequestClasses = (ResponseTimeRequestClassMBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public ResponseTimeRequestClassMBean createResponseTimeRequestClass(String param0) {
      ResponseTimeRequestClassMBeanImpl lookup = (ResponseTimeRequestClassMBeanImpl)this.lookupResponseTimeRequestClass(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ResponseTimeRequestClassMBeanImpl _val = new ResponseTimeRequestClassMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addResponseTimeRequestClass(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyResponseTimeRequestClass(ResponseTimeRequestClassMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         ResponseTimeRequestClassMBean[] _old = this.getResponseTimeRequestClasses();
         ResponseTimeRequestClassMBean[] _new = (ResponseTimeRequestClassMBean[])((ResponseTimeRequestClassMBean[])this._getHelper()._removeElement(_old, ResponseTimeRequestClassMBean.class, param0));
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
               this.setResponseTimeRequestClasses(_new);
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

   public ResponseTimeRequestClassMBean lookupResponseTimeRequestClass(String param0) {
      Object[] aary = (Object[])this._ResponseTimeRequestClasses;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ResponseTimeRequestClassMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ResponseTimeRequestClassMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addContextRequestClass(ContextRequestClassMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         ContextRequestClassMBean[] _new;
         if (this._isSet(12)) {
            _new = (ContextRequestClassMBean[])((ContextRequestClassMBean[])this._getHelper()._extendArray(this.getContextRequestClasses(), ContextRequestClassMBean.class, param0));
         } else {
            _new = new ContextRequestClassMBean[]{param0};
         }

         try {
            this.setContextRequestClasses(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ContextRequestClassMBean[] getContextRequestClasses() {
      return this._ContextRequestClasses;
   }

   public boolean isContextRequestClassesInherited() {
      return false;
   }

   public boolean isContextRequestClassesSet() {
      return this._isSet(12);
   }

   public void removeContextRequestClass(ContextRequestClassMBean param0) {
      this.destroyContextRequestClass(param0);
   }

   public void setContextRequestClasses(ContextRequestClassMBean[] param0) throws InvalidAttributeValueException {
      ContextRequestClassMBean[] param0 = param0 == null ? new ContextRequestClassMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      ContextRequestClassMBean[] _oldVal = this._ContextRequestClasses;
      this._ContextRequestClasses = (ContextRequestClassMBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public ContextRequestClassMBean createContextRequestClass(String param0) {
      ContextRequestClassMBeanImpl lookup = (ContextRequestClassMBeanImpl)this.lookupContextRequestClass(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         ContextRequestClassMBeanImpl _val = new ContextRequestClassMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addContextRequestClass(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyContextRequestClass(ContextRequestClassMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         ContextRequestClassMBean[] _old = this.getContextRequestClasses();
         ContextRequestClassMBean[] _new = (ContextRequestClassMBean[])((ContextRequestClassMBean[])this._getHelper()._removeElement(_old, ContextRequestClassMBean.class, param0));
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
               this.setContextRequestClasses(_new);
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

   public ContextRequestClassMBean lookupContextRequestClass(String param0) {
      Object[] aary = (Object[])this._ContextRequestClasses;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ContextRequestClassMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ContextRequestClassMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addMinThreadsConstraint(MinThreadsConstraintMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         MinThreadsConstraintMBean[] _new;
         if (this._isSet(13)) {
            _new = (MinThreadsConstraintMBean[])((MinThreadsConstraintMBean[])this._getHelper()._extendArray(this.getMinThreadsConstraints(), MinThreadsConstraintMBean.class, param0));
         } else {
            _new = new MinThreadsConstraintMBean[]{param0};
         }

         try {
            this.setMinThreadsConstraints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MinThreadsConstraintMBean[] getMinThreadsConstraints() {
      return this._MinThreadsConstraints;
   }

   public boolean isMinThreadsConstraintsInherited() {
      return false;
   }

   public boolean isMinThreadsConstraintsSet() {
      return this._isSet(13);
   }

   public void removeMinThreadsConstraint(MinThreadsConstraintMBean param0) {
      this.destroyMinThreadsConstraint(param0);
   }

   public void setMinThreadsConstraints(MinThreadsConstraintMBean[] param0) throws InvalidAttributeValueException {
      MinThreadsConstraintMBean[] param0 = param0 == null ? new MinThreadsConstraintMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 13)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      MinThreadsConstraintMBean[] _oldVal = this._MinThreadsConstraints;
      this._MinThreadsConstraints = (MinThreadsConstraintMBean[])param0;
      this._postSet(13, _oldVal, param0);
   }

   public MinThreadsConstraintMBean createMinThreadsConstraint(String param0) {
      MinThreadsConstraintMBeanImpl lookup = (MinThreadsConstraintMBeanImpl)this.lookupMinThreadsConstraint(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MinThreadsConstraintMBeanImpl _val = new MinThreadsConstraintMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMinThreadsConstraint(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyMinThreadsConstraint(MinThreadsConstraintMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 13);
         MinThreadsConstraintMBean[] _old = this.getMinThreadsConstraints();
         MinThreadsConstraintMBean[] _new = (MinThreadsConstraintMBean[])((MinThreadsConstraintMBean[])this._getHelper()._removeElement(_old, MinThreadsConstraintMBean.class, param0));
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
               this.setMinThreadsConstraints(_new);
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

   public MinThreadsConstraintMBean lookupMinThreadsConstraint(String param0) {
      Object[] aary = (Object[])this._MinThreadsConstraints;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MinThreadsConstraintMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MinThreadsConstraintMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addMaxThreadsConstraint(MaxThreadsConstraintMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         MaxThreadsConstraintMBean[] _new;
         if (this._isSet(14)) {
            _new = (MaxThreadsConstraintMBean[])((MaxThreadsConstraintMBean[])this._getHelper()._extendArray(this.getMaxThreadsConstraints(), MaxThreadsConstraintMBean.class, param0));
         } else {
            _new = new MaxThreadsConstraintMBean[]{param0};
         }

         try {
            this.setMaxThreadsConstraints(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MaxThreadsConstraintMBean[] getMaxThreadsConstraints() {
      return this._MaxThreadsConstraints;
   }

   public boolean isMaxThreadsConstraintsInherited() {
      return false;
   }

   public boolean isMaxThreadsConstraintsSet() {
      return this._isSet(14);
   }

   public void removeMaxThreadsConstraint(MaxThreadsConstraintMBean param0) {
      this.destroyMaxThreadsConstraint(param0);
   }

   public void setMaxThreadsConstraints(MaxThreadsConstraintMBean[] param0) throws InvalidAttributeValueException {
      MaxThreadsConstraintMBean[] param0 = param0 == null ? new MaxThreadsConstraintMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 14)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      MaxThreadsConstraintMBean[] _oldVal = this._MaxThreadsConstraints;
      this._MaxThreadsConstraints = (MaxThreadsConstraintMBean[])param0;
      this._postSet(14, _oldVal, param0);
   }

   public MaxThreadsConstraintMBean createMaxThreadsConstraint(String param0) {
      MaxThreadsConstraintMBeanImpl lookup = (MaxThreadsConstraintMBeanImpl)this.lookupMaxThreadsConstraint(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         MaxThreadsConstraintMBeanImpl _val = new MaxThreadsConstraintMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addMaxThreadsConstraint(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyMaxThreadsConstraint(MaxThreadsConstraintMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 14);
         MaxThreadsConstraintMBean[] _old = this.getMaxThreadsConstraints();
         MaxThreadsConstraintMBean[] _new = (MaxThreadsConstraintMBean[])((MaxThreadsConstraintMBean[])this._getHelper()._removeElement(_old, MaxThreadsConstraintMBean.class, param0));
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
               this.setMaxThreadsConstraints(_new);
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

   public MaxThreadsConstraintMBean lookupMaxThreadsConstraint(String param0) {
      Object[] aary = (Object[])this._MaxThreadsConstraints;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      MaxThreadsConstraintMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (MaxThreadsConstraintMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addCapacity(CapacityMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 15)) {
         CapacityMBean[] _new;
         if (this._isSet(15)) {
            _new = (CapacityMBean[])((CapacityMBean[])this._getHelper()._extendArray(this.getCapacities(), CapacityMBean.class, param0));
         } else {
            _new = new CapacityMBean[]{param0};
         }

         try {
            this.setCapacities(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CapacityMBean[] getCapacities() {
      return this._Capacities;
   }

   public boolean isCapacitiesInherited() {
      return false;
   }

   public boolean isCapacitiesSet() {
      return this._isSet(15);
   }

   public void removeCapacity(CapacityMBean param0) {
      this.destroyCapacity(param0);
   }

   public void setCapacities(CapacityMBean[] param0) throws InvalidAttributeValueException {
      CapacityMBean[] param0 = param0 == null ? new CapacityMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 15)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      CapacityMBean[] _oldVal = this._Capacities;
      this._Capacities = (CapacityMBean[])param0;
      this._postSet(15, _oldVal, param0);
   }

   public CapacityMBean createCapacity(String param0) {
      CapacityMBeanImpl lookup = (CapacityMBeanImpl)this.lookupCapacity(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CapacityMBeanImpl _val = new CapacityMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCapacity(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyCapacity(CapacityMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 15);
         CapacityMBean[] _old = this.getCapacities();
         CapacityMBean[] _new = (CapacityMBean[])((CapacityMBean[])this._getHelper()._removeElement(_old, CapacityMBean.class, param0));
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
               this.setCapacities(_new);
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

   public CapacityMBean lookupCapacity(String param0) {
      Object[] aary = (Object[])this._Capacities;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CapacityMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CapacityMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void addWorkManager(WorkManagerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 16)) {
         WorkManagerMBean[] _new;
         if (this._isSet(16)) {
            _new = (WorkManagerMBean[])((WorkManagerMBean[])this._getHelper()._extendArray(this.getWorkManagers(), WorkManagerMBean.class, param0));
         } else {
            _new = new WorkManagerMBean[]{param0};
         }

         try {
            this.setWorkManagers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WorkManagerMBean[] getWorkManagers() {
      return this._WorkManagers;
   }

   public boolean isWorkManagersInherited() {
      return false;
   }

   public boolean isWorkManagersSet() {
      return this._isSet(16);
   }

   public void removeWorkManager(WorkManagerMBean param0) {
      this.destroyWorkManager(param0);
   }

   public void setWorkManagers(WorkManagerMBean[] param0) throws InvalidAttributeValueException {
      WorkManagerMBean[] param0 = param0 == null ? new WorkManagerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 16)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WorkManagerMBean[] _oldVal = this._WorkManagers;
      this._WorkManagers = (WorkManagerMBean[])param0;
      this._postSet(16, _oldVal, param0);
   }

   public WorkManagerMBean createWorkManager(String param0) {
      WorkManagerMBeanImpl lookup = (WorkManagerMBeanImpl)this.lookupWorkManager(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WorkManagerMBeanImpl _val = new WorkManagerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWorkManager(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWorkManager(WorkManagerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 16);
         WorkManagerMBean[] _old = this.getWorkManagers();
         WorkManagerMBean[] _new = (WorkManagerMBean[])((WorkManagerMBean[])this._getHelper()._removeElement(_old, WorkManagerMBean.class, param0));
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
               this.setWorkManagers(_new);
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

   public WorkManagerMBean lookupWorkManager(String param0) {
      Object[] aary = (Object[])this._WorkManagers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WorkManagerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WorkManagerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public int getPartitionFairShare() {
      return this._PartitionFairShare;
   }

   public boolean isPartitionFairShareInherited() {
      return false;
   }

   public boolean isPartitionFairShareSet() {
      return this._isSet(17);
   }

   public void setPartitionFairShare(int param0) {
      LegalChecks.checkInRange("PartitionFairShare", (long)param0, 1L, 99L);
      int _oldVal = this._PartitionFairShare;
      this._PartitionFairShare = param0;
      this._postSet(17, _oldVal, param0);
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
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._Capacities = new CapacityMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._ContextRequestClasses = new ContextRequestClassMBean[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._FairShareRequestClasses = new FairShareRequestClassMBean[0];
               if (initOne) {
                  break;
               }
            case 14:
               this._MaxThreadsConstraints = new MaxThreadsConstraintMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._MinThreadsConstraints = new MinThreadsConstraintMBean[0];
               if (initOne) {
                  break;
               }
            case 17:
               this._PartitionFairShare = 50;
               if (initOne) {
                  break;
               }
            case 11:
               this._ResponseTimeRequestClasses = new ResponseTimeRequestClassMBean[0];
               if (initOne) {
                  break;
               }
            case 16:
               this._WorkManagers = new WorkManagerMBean[0];
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
      return "SelfTuning";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Capacities")) {
         CapacityMBean[] oldVal = this._Capacities;
         this._Capacities = (CapacityMBean[])((CapacityMBean[])v);
         this._postSet(15, oldVal, this._Capacities);
      } else if (name.equals("ContextRequestClasses")) {
         ContextRequestClassMBean[] oldVal = this._ContextRequestClasses;
         this._ContextRequestClasses = (ContextRequestClassMBean[])((ContextRequestClassMBean[])v);
         this._postSet(12, oldVal, this._ContextRequestClasses);
      } else if (name.equals("FairShareRequestClasses")) {
         FairShareRequestClassMBean[] oldVal = this._FairShareRequestClasses;
         this._FairShareRequestClasses = (FairShareRequestClassMBean[])((FairShareRequestClassMBean[])v);
         this._postSet(10, oldVal, this._FairShareRequestClasses);
      } else if (name.equals("MaxThreadsConstraints")) {
         MaxThreadsConstraintMBean[] oldVal = this._MaxThreadsConstraints;
         this._MaxThreadsConstraints = (MaxThreadsConstraintMBean[])((MaxThreadsConstraintMBean[])v);
         this._postSet(14, oldVal, this._MaxThreadsConstraints);
      } else if (name.equals("MinThreadsConstraints")) {
         MinThreadsConstraintMBean[] oldVal = this._MinThreadsConstraints;
         this._MinThreadsConstraints = (MinThreadsConstraintMBean[])((MinThreadsConstraintMBean[])v);
         this._postSet(13, oldVal, this._MinThreadsConstraints);
      } else if (name.equals("PartitionFairShare")) {
         int oldVal = this._PartitionFairShare;
         this._PartitionFairShare = (Integer)v;
         this._postSet(17, oldVal, this._PartitionFairShare);
      } else if (name.equals("ResponseTimeRequestClasses")) {
         ResponseTimeRequestClassMBean[] oldVal = this._ResponseTimeRequestClasses;
         this._ResponseTimeRequestClasses = (ResponseTimeRequestClassMBean[])((ResponseTimeRequestClassMBean[])v);
         this._postSet(11, oldVal, this._ResponseTimeRequestClasses);
      } else if (name.equals("WorkManagers")) {
         WorkManagerMBean[] oldVal = this._WorkManagers;
         this._WorkManagers = (WorkManagerMBean[])((WorkManagerMBean[])v);
         this._postSet(16, oldVal, this._WorkManagers);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Capacities")) {
         return this._Capacities;
      } else if (name.equals("ContextRequestClasses")) {
         return this._ContextRequestClasses;
      } else if (name.equals("FairShareRequestClasses")) {
         return this._FairShareRequestClasses;
      } else if (name.equals("MaxThreadsConstraints")) {
         return this._MaxThreadsConstraints;
      } else if (name.equals("MinThreadsConstraints")) {
         return this._MinThreadsConstraints;
      } else if (name.equals("PartitionFairShare")) {
         return new Integer(this._PartitionFairShare);
      } else if (name.equals("ResponseTimeRequestClasses")) {
         return this._ResponseTimeRequestClasses;
      } else {
         return name.equals("WorkManagers") ? this._WorkManagers : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("capacity")) {
                  return 15;
               }
            case 9:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 23:
            case 25:
            case 26:
            default:
               break;
            case 12:
               if (s.equals("work-manager")) {
                  return 16;
               }
               break;
            case 20:
               if (s.equals("partition-fair-share")) {
                  return 17;
               }
               break;
            case 21:
               if (s.equals("context-request-class")) {
                  return 12;
               }
               break;
            case 22:
               if (s.equals("max-threads-constraint")) {
                  return 14;
               }

               if (s.equals("min-threads-constraint")) {
                  return 13;
               }
               break;
            case 24:
               if (s.equals("fair-share-request-class")) {
                  return 10;
               }
               break;
            case 27:
               if (s.equals("response-time-request-class")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new FairShareRequestClassMBeanImpl.SchemaHelper2();
            case 11:
               return new ResponseTimeRequestClassMBeanImpl.SchemaHelper2();
            case 12:
               return new ContextRequestClassMBeanImpl.SchemaHelper2();
            case 13:
               return new MinThreadsConstraintMBeanImpl.SchemaHelper2();
            case 14:
               return new MaxThreadsConstraintMBeanImpl.SchemaHelper2();
            case 15:
               return new CapacityMBeanImpl.SchemaHelper2();
            case 16:
               return new WorkManagerMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "fair-share-request-class";
            case 11:
               return "response-time-request-class";
            case 12:
               return "context-request-class";
            case 13:
               return "min-threads-constraint";
            case 14:
               return "max-threads-constraint";
            case 15:
               return "capacity";
            case 16:
               return "work-manager";
            case 17:
               return "partition-fair-share";
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
            default:
               return super.isArray(propIndex);
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
               return true;
            case 16:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private SelfTuningMBeanImpl bean;

      protected Helper(SelfTuningMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "FairShareRequestClasses";
            case 11:
               return "ResponseTimeRequestClasses";
            case 12:
               return "ContextRequestClasses";
            case 13:
               return "MinThreadsConstraints";
            case 14:
               return "MaxThreadsConstraints";
            case 15:
               return "Capacities";
            case 16:
               return "WorkManagers";
            case 17:
               return "PartitionFairShare";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Capacities")) {
            return 15;
         } else if (propName.equals("ContextRequestClasses")) {
            return 12;
         } else if (propName.equals("FairShareRequestClasses")) {
            return 10;
         } else if (propName.equals("MaxThreadsConstraints")) {
            return 14;
         } else if (propName.equals("MinThreadsConstraints")) {
            return 13;
         } else if (propName.equals("PartitionFairShare")) {
            return 17;
         } else if (propName.equals("ResponseTimeRequestClasses")) {
            return 11;
         } else {
            return propName.equals("WorkManagers") ? 16 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCapacities()));
         iterators.add(new ArrayIterator(this.bean.getContextRequestClasses()));
         iterators.add(new ArrayIterator(this.bean.getFairShareRequestClasses()));
         iterators.add(new ArrayIterator(this.bean.getMaxThreadsConstraints()));
         iterators.add(new ArrayIterator(this.bean.getMinThreadsConstraints()));
         iterators.add(new ArrayIterator(this.bean.getResponseTimeRequestClasses()));
         iterators.add(new ArrayIterator(this.bean.getWorkManagers()));
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
            for(i = 0; i < this.bean.getCapacities().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCapacities()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getContextRequestClasses().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getContextRequestClasses()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getFairShareRequestClasses().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFairShareRequestClasses()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMaxThreadsConstraints().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMaxThreadsConstraints()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMinThreadsConstraints().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMinThreadsConstraints()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isPartitionFairShareSet()) {
               buf.append("PartitionFairShare");
               buf.append(String.valueOf(this.bean.getPartitionFairShare()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getResponseTimeRequestClasses().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getResponseTimeRequestClasses()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWorkManagers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWorkManagers()[i]);
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
            SelfTuningMBeanImpl otherTyped = (SelfTuningMBeanImpl)other;
            this.computeChildDiff("Capacities", this.bean.getCapacities(), otherTyped.getCapacities(), true);
            this.computeChildDiff("ContextRequestClasses", this.bean.getContextRequestClasses(), otherTyped.getContextRequestClasses(), true);
            this.computeChildDiff("FairShareRequestClasses", this.bean.getFairShareRequestClasses(), otherTyped.getFairShareRequestClasses(), true);
            this.computeChildDiff("MaxThreadsConstraints", this.bean.getMaxThreadsConstraints(), otherTyped.getMaxThreadsConstraints(), true);
            this.computeChildDiff("MinThreadsConstraints", this.bean.getMinThreadsConstraints(), otherTyped.getMinThreadsConstraints(), true);
            this.computeDiff("PartitionFairShare", this.bean.getPartitionFairShare(), otherTyped.getPartitionFairShare(), true);
            this.computeChildDiff("ResponseTimeRequestClasses", this.bean.getResponseTimeRequestClasses(), otherTyped.getResponseTimeRequestClasses(), true);
            this.computeChildDiff("WorkManagers", this.bean.getWorkManagers(), otherTyped.getWorkManagers(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SelfTuningMBeanImpl original = (SelfTuningMBeanImpl)event.getSourceBean();
            SelfTuningMBeanImpl proposed = (SelfTuningMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Capacities")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCapacity((CapacityMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCapacity((CapacityMBean)update.getRemovedObject());
                  }

                  if (original.getCapacities() == null || original.getCapacities().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  }
               } else if (prop.equals("ContextRequestClasses")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addContextRequestClass((ContextRequestClassMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeContextRequestClass((ContextRequestClassMBean)update.getRemovedObject());
                  }

                  if (original.getContextRequestClasses() == null || original.getContextRequestClasses().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("FairShareRequestClasses")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addFairShareRequestClass((FairShareRequestClassMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeFairShareRequestClass((FairShareRequestClassMBean)update.getRemovedObject());
                  }

                  if (original.getFairShareRequestClasses() == null || original.getFairShareRequestClasses().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
               } else if (prop.equals("MaxThreadsConstraints")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMaxThreadsConstraint((MaxThreadsConstraintMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMaxThreadsConstraint((MaxThreadsConstraintMBean)update.getRemovedObject());
                  }

                  if (original.getMaxThreadsConstraints() == null || original.getMaxThreadsConstraints().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("MinThreadsConstraints")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMinThreadsConstraint((MinThreadsConstraintMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMinThreadsConstraint((MinThreadsConstraintMBean)update.getRemovedObject());
                  }

                  if (original.getMinThreadsConstraints() == null || original.getMinThreadsConstraints().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  }
               } else if (prop.equals("PartitionFairShare")) {
                  original.setPartitionFairShare(proposed.getPartitionFairShare());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("ResponseTimeRequestClasses")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addResponseTimeRequestClass((ResponseTimeRequestClassMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeResponseTimeRequestClass((ResponseTimeRequestClassMBean)update.getRemovedObject());
                  }

                  if (original.getResponseTimeRequestClasses() == null || original.getResponseTimeRequestClasses().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("WorkManagers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWorkManager((WorkManagerMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWorkManager((WorkManagerMBean)update.getRemovedObject());
                  }

                  if (original.getWorkManagers() == null || original.getWorkManagers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 16);
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
            SelfTuningMBeanImpl copy = (SelfTuningMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("Capacities")) && this.bean.isCapacitiesSet() && !copy._isSet(15)) {
               CapacityMBean[] oldCapacities = this.bean.getCapacities();
               CapacityMBean[] newCapacities = new CapacityMBean[oldCapacities.length];

               for(i = 0; i < newCapacities.length; ++i) {
                  newCapacities[i] = (CapacityMBean)((CapacityMBean)this.createCopy((AbstractDescriptorBean)oldCapacities[i], includeObsolete));
               }

               copy.setCapacities(newCapacities);
            }

            if ((excludeProps == null || !excludeProps.contains("ContextRequestClasses")) && this.bean.isContextRequestClassesSet() && !copy._isSet(12)) {
               ContextRequestClassMBean[] oldContextRequestClasses = this.bean.getContextRequestClasses();
               ContextRequestClassMBean[] newContextRequestClasses = new ContextRequestClassMBean[oldContextRequestClasses.length];

               for(i = 0; i < newContextRequestClasses.length; ++i) {
                  newContextRequestClasses[i] = (ContextRequestClassMBean)((ContextRequestClassMBean)this.createCopy((AbstractDescriptorBean)oldContextRequestClasses[i], includeObsolete));
               }

               copy.setContextRequestClasses(newContextRequestClasses);
            }

            if ((excludeProps == null || !excludeProps.contains("FairShareRequestClasses")) && this.bean.isFairShareRequestClassesSet() && !copy._isSet(10)) {
               FairShareRequestClassMBean[] oldFairShareRequestClasses = this.bean.getFairShareRequestClasses();
               FairShareRequestClassMBean[] newFairShareRequestClasses = new FairShareRequestClassMBean[oldFairShareRequestClasses.length];

               for(i = 0; i < newFairShareRequestClasses.length; ++i) {
                  newFairShareRequestClasses[i] = (FairShareRequestClassMBean)((FairShareRequestClassMBean)this.createCopy((AbstractDescriptorBean)oldFairShareRequestClasses[i], includeObsolete));
               }

               copy.setFairShareRequestClasses(newFairShareRequestClasses);
            }

            if ((excludeProps == null || !excludeProps.contains("MaxThreadsConstraints")) && this.bean.isMaxThreadsConstraintsSet() && !copy._isSet(14)) {
               MaxThreadsConstraintMBean[] oldMaxThreadsConstraints = this.bean.getMaxThreadsConstraints();
               MaxThreadsConstraintMBean[] newMaxThreadsConstraints = new MaxThreadsConstraintMBean[oldMaxThreadsConstraints.length];

               for(i = 0; i < newMaxThreadsConstraints.length; ++i) {
                  newMaxThreadsConstraints[i] = (MaxThreadsConstraintMBean)((MaxThreadsConstraintMBean)this.createCopy((AbstractDescriptorBean)oldMaxThreadsConstraints[i], includeObsolete));
               }

               copy.setMaxThreadsConstraints(newMaxThreadsConstraints);
            }

            if ((excludeProps == null || !excludeProps.contains("MinThreadsConstraints")) && this.bean.isMinThreadsConstraintsSet() && !copy._isSet(13)) {
               MinThreadsConstraintMBean[] oldMinThreadsConstraints = this.bean.getMinThreadsConstraints();
               MinThreadsConstraintMBean[] newMinThreadsConstraints = new MinThreadsConstraintMBean[oldMinThreadsConstraints.length];

               for(i = 0; i < newMinThreadsConstraints.length; ++i) {
                  newMinThreadsConstraints[i] = (MinThreadsConstraintMBean)((MinThreadsConstraintMBean)this.createCopy((AbstractDescriptorBean)oldMinThreadsConstraints[i], includeObsolete));
               }

               copy.setMinThreadsConstraints(newMinThreadsConstraints);
            }

            if ((excludeProps == null || !excludeProps.contains("PartitionFairShare")) && this.bean.isPartitionFairShareSet()) {
               copy.setPartitionFairShare(this.bean.getPartitionFairShare());
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseTimeRequestClasses")) && this.bean.isResponseTimeRequestClassesSet() && !copy._isSet(11)) {
               ResponseTimeRequestClassMBean[] oldResponseTimeRequestClasses = this.bean.getResponseTimeRequestClasses();
               ResponseTimeRequestClassMBean[] newResponseTimeRequestClasses = new ResponseTimeRequestClassMBean[oldResponseTimeRequestClasses.length];

               for(i = 0; i < newResponseTimeRequestClasses.length; ++i) {
                  newResponseTimeRequestClasses[i] = (ResponseTimeRequestClassMBean)((ResponseTimeRequestClassMBean)this.createCopy((AbstractDescriptorBean)oldResponseTimeRequestClasses[i], includeObsolete));
               }

               copy.setResponseTimeRequestClasses(newResponseTimeRequestClasses);
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManagers")) && this.bean.isWorkManagersSet() && !copy._isSet(16)) {
               WorkManagerMBean[] oldWorkManagers = this.bean.getWorkManagers();
               WorkManagerMBean[] newWorkManagers = new WorkManagerMBean[oldWorkManagers.length];

               for(i = 0; i < newWorkManagers.length; ++i) {
                  newWorkManagers[i] = (WorkManagerMBean)((WorkManagerMBean)this.createCopy((AbstractDescriptorBean)oldWorkManagers[i], includeObsolete));
               }

               copy.setWorkManagers(newWorkManagers);
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
         this.inferSubTree(this.bean.getCapacities(), clazz, annotation);
         this.inferSubTree(this.bean.getContextRequestClasses(), clazz, annotation);
         this.inferSubTree(this.bean.getFairShareRequestClasses(), clazz, annotation);
         this.inferSubTree(this.bean.getMaxThreadsConstraints(), clazz, annotation);
         this.inferSubTree(this.bean.getMinThreadsConstraints(), clazz, annotation);
         this.inferSubTree(this.bean.getResponseTimeRequestClasses(), clazz, annotation);
         this.inferSubTree(this.bean.getWorkManagers(), clazz, annotation);
      }
   }
}

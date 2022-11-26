package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WorkManagerBeanImpl extends SettableBeanImpl implements WorkManagerBean, Serializable {
   private CapacityBean _Capacity;
   private String _CapacityName;
   private ContextRequestClassBean _ContextRequestClass;
   private FairShareRequestClassBean _FairShareRequestClass;
   private String _Id;
   private boolean _IgnoreStuckThreads;
   private MaxThreadsConstraintBean _MaxThreadsConstraint;
   private String _MaxThreadsConstraintName;
   private MinThreadsConstraintBean _MinThreadsConstraint;
   private String _MinThreadsConstraintName;
   private String _Name;
   private String _RequestClassName;
   private ResponseTimeRequestClassBean _ResponseTimeRequestClass;
   private WorkManagerShutdownTriggerBean _WorkManagerShutdownTrigger;
   private static SchemaHelper2 _schemaHelper;

   public WorkManagerBeanImpl() {
      this._initializeProperty(-1);
   }

   public WorkManagerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WorkManagerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public ResponseTimeRequestClassBean getResponseTimeRequestClass() {
      return this._ResponseTimeRequestClass;
   }

   public boolean isResponseTimeRequestClassInherited() {
      return false;
   }

   public boolean isResponseTimeRequestClassSet() {
      return this._isSet(1);
   }

   public void setResponseTimeRequestClass(ResponseTimeRequestClassBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getResponseTimeRequestClass() != null && param0 != this.getResponseTimeRequestClass()) {
         throw new BeanAlreadyExistsException(this.getResponseTimeRequestClass() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         ResponseTimeRequestClassBean _oldVal = this._ResponseTimeRequestClass;
         this._ResponseTimeRequestClass = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public ResponseTimeRequestClassBean createResponseTimeRequestClass() {
      ResponseTimeRequestClassBeanImpl _val = new ResponseTimeRequestClassBeanImpl(this, -1);

      try {
         this.setResponseTimeRequestClass(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResponseTimeRequestClass() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ResponseTimeRequestClass;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setResponseTimeRequestClass((ResponseTimeRequestClassBean)null);
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

   public FairShareRequestClassBean getFairShareRequestClass() {
      return this._FairShareRequestClass;
   }

   public boolean isFairShareRequestClassInherited() {
      return false;
   }

   public boolean isFairShareRequestClassSet() {
      return this._isSet(2);
   }

   public void setFairShareRequestClass(FairShareRequestClassBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getFairShareRequestClass() != null && param0 != this.getFairShareRequestClass()) {
         throw new BeanAlreadyExistsException(this.getFairShareRequestClass() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         FairShareRequestClassBean _oldVal = this._FairShareRequestClass;
         this._FairShareRequestClass = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public FairShareRequestClassBean createFairShareRequestClass() {
      FairShareRequestClassBeanImpl _val = new FairShareRequestClassBeanImpl(this, -1);

      try {
         this.setFairShareRequestClass(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFairShareRequestClass() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._FairShareRequestClass;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFairShareRequestClass((FairShareRequestClassBean)null);
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

   public ContextRequestClassBean getContextRequestClass() {
      return this._ContextRequestClass;
   }

   public boolean isContextRequestClassInherited() {
      return false;
   }

   public boolean isContextRequestClassSet() {
      return this._isSet(3);
   }

   public void setContextRequestClass(ContextRequestClassBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getContextRequestClass() != null && param0 != this.getContextRequestClass()) {
         throw new BeanAlreadyExistsException(this.getContextRequestClass() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         ContextRequestClassBean _oldVal = this._ContextRequestClass;
         this._ContextRequestClass = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public ContextRequestClassBean createContextRequestClass() {
      ContextRequestClassBeanImpl _val = new ContextRequestClassBeanImpl(this, -1);

      try {
         this.setContextRequestClass(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyContextRequestClass() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ContextRequestClass;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setContextRequestClass((ContextRequestClassBean)null);
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

   public String getRequestClassName() {
      return this._RequestClassName;
   }

   public boolean isRequestClassNameInherited() {
      return false;
   }

   public boolean isRequestClassNameSet() {
      return this._isSet(4);
   }

   public void setRequestClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RequestClassName;
      this._RequestClassName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public MinThreadsConstraintBean getMinThreadsConstraint() {
      return this._MinThreadsConstraint;
   }

   public boolean isMinThreadsConstraintInherited() {
      return false;
   }

   public boolean isMinThreadsConstraintSet() {
      return this._isSet(5);
   }

   public void setMinThreadsConstraint(MinThreadsConstraintBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMinThreadsConstraint() != null && param0 != this.getMinThreadsConstraint()) {
         throw new BeanAlreadyExistsException(this.getMinThreadsConstraint() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         MinThreadsConstraintBean _oldVal = this._MinThreadsConstraint;
         this._MinThreadsConstraint = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public MinThreadsConstraintBean createMinThreadsConstraint() {
      MinThreadsConstraintBeanImpl _val = new MinThreadsConstraintBeanImpl(this, -1);

      try {
         this.setMinThreadsConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMinThreadsConstraint() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MinThreadsConstraint;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMinThreadsConstraint((MinThreadsConstraintBean)null);
               this._unSet(5);
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

   public String getMinThreadsConstraintName() {
      return this._MinThreadsConstraintName;
   }

   public boolean isMinThreadsConstraintNameInherited() {
      return false;
   }

   public boolean isMinThreadsConstraintNameSet() {
      return this._isSet(6);
   }

   public void setMinThreadsConstraintName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MinThreadsConstraintName;
      this._MinThreadsConstraintName = param0;
      this._postSet(6, _oldVal, param0);
   }

   public MaxThreadsConstraintBean getMaxThreadsConstraint() {
      return this._MaxThreadsConstraint;
   }

   public boolean isMaxThreadsConstraintInherited() {
      return false;
   }

   public boolean isMaxThreadsConstraintSet() {
      return this._isSet(7);
   }

   public void setMaxThreadsConstraint(MaxThreadsConstraintBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMaxThreadsConstraint() != null && param0 != this.getMaxThreadsConstraint()) {
         throw new BeanAlreadyExistsException(this.getMaxThreadsConstraint() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 7)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         MaxThreadsConstraintBean _oldVal = this._MaxThreadsConstraint;
         this._MaxThreadsConstraint = param0;
         this._postSet(7, _oldVal, param0);
      }
   }

   public MaxThreadsConstraintBean createMaxThreadsConstraint() {
      MaxThreadsConstraintBeanImpl _val = new MaxThreadsConstraintBeanImpl(this, -1);

      try {
         this.setMaxThreadsConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMaxThreadsConstraint() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MaxThreadsConstraint;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMaxThreadsConstraint((MaxThreadsConstraintBean)null);
               this._unSet(7);
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

   public String getMaxThreadsConstraintName() {
      return this._MaxThreadsConstraintName;
   }

   public boolean isMaxThreadsConstraintNameInherited() {
      return false;
   }

   public boolean isMaxThreadsConstraintNameSet() {
      return this._isSet(8);
   }

   public void setMaxThreadsConstraintName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MaxThreadsConstraintName;
      this._MaxThreadsConstraintName = param0;
      this._postSet(8, _oldVal, param0);
   }

   public CapacityBean getCapacity() {
      return this._Capacity;
   }

   public boolean isCapacityInherited() {
      return false;
   }

   public boolean isCapacitySet() {
      return this._isSet(9);
   }

   public void setCapacity(CapacityBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getCapacity() != null && param0 != this.getCapacity()) {
         throw new BeanAlreadyExistsException(this.getCapacity() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 9)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         CapacityBean _oldVal = this._Capacity;
         this._Capacity = param0;
         this._postSet(9, _oldVal, param0);
      }
   }

   public CapacityBean createCapacity() {
      CapacityBeanImpl _val = new CapacityBeanImpl(this, -1);

      try {
         this.setCapacity(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCapacity() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Capacity;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setCapacity((CapacityBean)null);
               this._unSet(9);
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

   public String getCapacityName() {
      return this._CapacityName;
   }

   public boolean isCapacityNameInherited() {
      return false;
   }

   public boolean isCapacityNameSet() {
      return this._isSet(10);
   }

   public void setCapacityName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CapacityName;
      this._CapacityName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public WorkManagerShutdownTriggerBean getWorkManagerShutdownTrigger() {
      return this._WorkManagerShutdownTrigger;
   }

   public boolean isWorkManagerShutdownTriggerInherited() {
      return false;
   }

   public boolean isWorkManagerShutdownTriggerSet() {
      return this._isSet(11);
   }

   public void setWorkManagerShutdownTrigger(WorkManagerShutdownTriggerBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWorkManagerShutdownTrigger() != null && param0 != this.getWorkManagerShutdownTrigger()) {
         throw new BeanAlreadyExistsException(this.getWorkManagerShutdownTrigger() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 11)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WorkManagerShutdownTriggerBean _oldVal = this._WorkManagerShutdownTrigger;
         this._WorkManagerShutdownTrigger = param0;
         this._postSet(11, _oldVal, param0);
      }
   }

   public WorkManagerShutdownTriggerBean createWorkManagerShutdownTrigger() {
      WorkManagerShutdownTriggerBeanImpl _val = new WorkManagerShutdownTriggerBeanImpl(this, -1);

      try {
         this.setWorkManagerShutdownTrigger(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWorkManagerShutdownTrigger() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WorkManagerShutdownTrigger;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWorkManagerShutdownTrigger((WorkManagerShutdownTriggerBean)null);
               this._unSet(11);
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

   public boolean getIgnoreStuckThreads() {
      return this._IgnoreStuckThreads;
   }

   public boolean isIgnoreStuckThreadsInherited() {
      return false;
   }

   public boolean isIgnoreStuckThreadsSet() {
      return this._isSet(12);
   }

   public void setIgnoreStuckThreads(boolean param0) {
      boolean _oldVal = this._IgnoreStuckThreads;
      this._IgnoreStuckThreads = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(13);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(13, _oldVal, param0);
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
         idx = 9;
      }

      try {
         switch (idx) {
            case 9:
               this._Capacity = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._CapacityName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ContextRequestClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._FairShareRequestClass = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._IgnoreStuckThreads = false;
               if (initOne) {
                  break;
               }
            case 7:
               this._MaxThreadsConstraint = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._MaxThreadsConstraintName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._MinThreadsConstraint = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._MinThreadsConstraintName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._RequestClassName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ResponseTimeRequestClass = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._WorkManagerShutdownTrigger = null;
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 13;
               }
            case 3:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 19:
            case 23:
            case 25:
            case 26:
            case 28:
            default:
               break;
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 8:
               if (s.equals("capacity")) {
                  return 9;
               }
               break;
            case 13:
               if (s.equals("capacity-name")) {
                  return 10;
               }
               break;
            case 18:
               if (s.equals("request-class-name")) {
                  return 4;
               }
               break;
            case 20:
               if (s.equals("ignore-stuck-threads")) {
                  return 12;
               }
               break;
            case 21:
               if (s.equals("context-request-class")) {
                  return 3;
               }
               break;
            case 22:
               if (s.equals("max-threads-constraint")) {
                  return 7;
               }

               if (s.equals("min-threads-constraint")) {
                  return 5;
               }
               break;
            case 24:
               if (s.equals("fair-share-request-class")) {
                  return 2;
               }
               break;
            case 27:
               if (s.equals("max-threads-constraint-name")) {
                  return 8;
               }

               if (s.equals("min-threads-constraint-name")) {
                  return 6;
               }

               if (s.equals("response-time-request-class")) {
                  return 1;
               }
               break;
            case 29:
               if (s.equals("work-manager-shutdown-trigger")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ResponseTimeRequestClassBeanImpl.SchemaHelper2();
            case 2:
               return new FairShareRequestClassBeanImpl.SchemaHelper2();
            case 3:
               return new ContextRequestClassBeanImpl.SchemaHelper2();
            case 4:
            case 6:
            case 8:
            case 10:
            default:
               return super.getSchemaHelper(propIndex);
            case 5:
               return new MinThreadsConstraintBeanImpl.SchemaHelper2();
            case 7:
               return new MaxThreadsConstraintBeanImpl.SchemaHelper2();
            case 9:
               return new CapacityBeanImpl.SchemaHelper2();
            case 11:
               return new WorkManagerShutdownTriggerBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "name";
            case 1:
               return "response-time-request-class";
            case 2:
               return "fair-share-request-class";
            case 3:
               return "context-request-class";
            case 4:
               return "request-class-name";
            case 5:
               return "min-threads-constraint";
            case 6:
               return "min-threads-constraint-name";
            case 7:
               return "max-threads-constraint";
            case 8:
               return "max-threads-constraint-name";
            case 9:
               return "capacity";
            case 10:
               return "capacity-name";
            case 11:
               return "work-manager-shutdown-trigger";
            case 12:
               return "ignore-stuck-threads";
            case 13:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
            case 6:
            case 8:
            case 10:
            default:
               return super.isBean(propIndex);
            case 5:
               return true;
            case 7:
               return true;
            case 9:
               return true;
            case 11:
               return true;
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
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
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
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends SettableBeanImpl.Helper {
      private WorkManagerBeanImpl bean;

      protected Helper(WorkManagerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "ResponseTimeRequestClass";
            case 2:
               return "FairShareRequestClass";
            case 3:
               return "ContextRequestClass";
            case 4:
               return "RequestClassName";
            case 5:
               return "MinThreadsConstraint";
            case 6:
               return "MinThreadsConstraintName";
            case 7:
               return "MaxThreadsConstraint";
            case 8:
               return "MaxThreadsConstraintName";
            case 9:
               return "Capacity";
            case 10:
               return "CapacityName";
            case 11:
               return "WorkManagerShutdownTrigger";
            case 12:
               return "IgnoreStuckThreads";
            case 13:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Capacity")) {
            return 9;
         } else if (propName.equals("CapacityName")) {
            return 10;
         } else if (propName.equals("ContextRequestClass")) {
            return 3;
         } else if (propName.equals("FairShareRequestClass")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 13;
         } else if (propName.equals("IgnoreStuckThreads")) {
            return 12;
         } else if (propName.equals("MaxThreadsConstraint")) {
            return 7;
         } else if (propName.equals("MaxThreadsConstraintName")) {
            return 8;
         } else if (propName.equals("MinThreadsConstraint")) {
            return 5;
         } else if (propName.equals("MinThreadsConstraintName")) {
            return 6;
         } else if (propName.equals("Name")) {
            return 0;
         } else if (propName.equals("RequestClassName")) {
            return 4;
         } else if (propName.equals("ResponseTimeRequestClass")) {
            return 1;
         } else {
            return propName.equals("WorkManagerShutdownTrigger") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCapacity() != null) {
            iterators.add(new ArrayIterator(new CapacityBean[]{this.bean.getCapacity()}));
         }

         if (this.bean.getContextRequestClass() != null) {
            iterators.add(new ArrayIterator(new ContextRequestClassBean[]{this.bean.getContextRequestClass()}));
         }

         if (this.bean.getFairShareRequestClass() != null) {
            iterators.add(new ArrayIterator(new FairShareRequestClassBean[]{this.bean.getFairShareRequestClass()}));
         }

         if (this.bean.getMaxThreadsConstraint() != null) {
            iterators.add(new ArrayIterator(new MaxThreadsConstraintBean[]{this.bean.getMaxThreadsConstraint()}));
         }

         if (this.bean.getMinThreadsConstraint() != null) {
            iterators.add(new ArrayIterator(new MinThreadsConstraintBean[]{this.bean.getMinThreadsConstraint()}));
         }

         if (this.bean.getResponseTimeRequestClass() != null) {
            iterators.add(new ArrayIterator(new ResponseTimeRequestClassBean[]{this.bean.getResponseTimeRequestClass()}));
         }

         if (this.bean.getWorkManagerShutdownTrigger() != null) {
            iterators.add(new ArrayIterator(new WorkManagerShutdownTriggerBean[]{this.bean.getWorkManagerShutdownTrigger()}));
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
            childValue = this.computeChildHashValue(this.bean.getCapacity());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCapacityNameSet()) {
               buf.append("CapacityName");
               buf.append(String.valueOf(this.bean.getCapacityName()));
            }

            childValue = this.computeChildHashValue(this.bean.getContextRequestClass());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getFairShareRequestClass());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isIgnoreStuckThreadsSet()) {
               buf.append("IgnoreStuckThreads");
               buf.append(String.valueOf(this.bean.getIgnoreStuckThreads()));
            }

            childValue = this.computeChildHashValue(this.bean.getMaxThreadsConstraint());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMaxThreadsConstraintNameSet()) {
               buf.append("MaxThreadsConstraintName");
               buf.append(String.valueOf(this.bean.getMaxThreadsConstraintName()));
            }

            childValue = this.computeChildHashValue(this.bean.getMinThreadsConstraint());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMinThreadsConstraintNameSet()) {
               buf.append("MinThreadsConstraintName");
               buf.append(String.valueOf(this.bean.getMinThreadsConstraintName()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isRequestClassNameSet()) {
               buf.append("RequestClassName");
               buf.append(String.valueOf(this.bean.getRequestClassName()));
            }

            childValue = this.computeChildHashValue(this.bean.getResponseTimeRequestClass());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWorkManagerShutdownTrigger());
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
            WorkManagerBeanImpl otherTyped = (WorkManagerBeanImpl)other;
            this.computeChildDiff("Capacity", this.bean.getCapacity(), otherTyped.getCapacity(), false);
            this.computeDiff("CapacityName", this.bean.getCapacityName(), otherTyped.getCapacityName(), false);
            this.computeChildDiff("ContextRequestClass", this.bean.getContextRequestClass(), otherTyped.getContextRequestClass(), false);
            this.computeChildDiff("FairShareRequestClass", this.bean.getFairShareRequestClass(), otherTyped.getFairShareRequestClass(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("IgnoreStuckThreads", this.bean.getIgnoreStuckThreads(), otherTyped.getIgnoreStuckThreads(), false);
            this.computeChildDiff("MaxThreadsConstraint", this.bean.getMaxThreadsConstraint(), otherTyped.getMaxThreadsConstraint(), false);
            this.computeDiff("MaxThreadsConstraintName", this.bean.getMaxThreadsConstraintName(), otherTyped.getMaxThreadsConstraintName(), false);
            this.computeChildDiff("MinThreadsConstraint", this.bean.getMinThreadsConstraint(), otherTyped.getMinThreadsConstraint(), false);
            this.computeDiff("MinThreadsConstraintName", this.bean.getMinThreadsConstraintName(), otherTyped.getMinThreadsConstraintName(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("RequestClassName", this.bean.getRequestClassName(), otherTyped.getRequestClassName(), false);
            this.computeChildDiff("ResponseTimeRequestClass", this.bean.getResponseTimeRequestClass(), otherTyped.getResponseTimeRequestClass(), false);
            this.computeChildDiff("WorkManagerShutdownTrigger", this.bean.getWorkManagerShutdownTrigger(), otherTyped.getWorkManagerShutdownTrigger(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WorkManagerBeanImpl original = (WorkManagerBeanImpl)event.getSourceBean();
            WorkManagerBeanImpl proposed = (WorkManagerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Capacity")) {
                  if (type == 2) {
                     original.setCapacity((CapacityBean)this.createCopy((AbstractDescriptorBean)proposed.getCapacity()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Capacity", (DescriptorBean)original.getCapacity());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("CapacityName")) {
                  original.setCapacityName(proposed.getCapacityName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ContextRequestClass")) {
                  if (type == 2) {
                     original.setContextRequestClass((ContextRequestClassBean)this.createCopy((AbstractDescriptorBean)proposed.getContextRequestClass()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ContextRequestClass", (DescriptorBean)original.getContextRequestClass());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("FairShareRequestClass")) {
                  if (type == 2) {
                     original.setFairShareRequestClass((FairShareRequestClassBean)this.createCopy((AbstractDescriptorBean)proposed.getFairShareRequestClass()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("FairShareRequestClass", (DescriptorBean)original.getFairShareRequestClass());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("IgnoreStuckThreads")) {
                  original.setIgnoreStuckThreads(proposed.getIgnoreStuckThreads());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MaxThreadsConstraint")) {
                  if (type == 2) {
                     original.setMaxThreadsConstraint((MaxThreadsConstraintBean)this.createCopy((AbstractDescriptorBean)proposed.getMaxThreadsConstraint()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MaxThreadsConstraint", (DescriptorBean)original.getMaxThreadsConstraint());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("MaxThreadsConstraintName")) {
                  original.setMaxThreadsConstraintName(proposed.getMaxThreadsConstraintName());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("MinThreadsConstraint")) {
                  if (type == 2) {
                     original.setMinThreadsConstraint((MinThreadsConstraintBean)this.createCopy((AbstractDescriptorBean)proposed.getMinThreadsConstraint()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MinThreadsConstraint", (DescriptorBean)original.getMinThreadsConstraint());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("MinThreadsConstraintName")) {
                  original.setMinThreadsConstraintName(proposed.getMinThreadsConstraintName());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("RequestClassName")) {
                  original.setRequestClassName(proposed.getRequestClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ResponseTimeRequestClass")) {
                  if (type == 2) {
                     original.setResponseTimeRequestClass((ResponseTimeRequestClassBean)this.createCopy((AbstractDescriptorBean)proposed.getResponseTimeRequestClass()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ResponseTimeRequestClass", (DescriptorBean)original.getResponseTimeRequestClass());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("WorkManagerShutdownTrigger")) {
                  if (type == 2) {
                     original.setWorkManagerShutdownTrigger((WorkManagerShutdownTriggerBean)this.createCopy((AbstractDescriptorBean)proposed.getWorkManagerShutdownTrigger()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WorkManagerShutdownTrigger", (DescriptorBean)original.getWorkManagerShutdownTrigger());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            WorkManagerBeanImpl copy = (WorkManagerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Capacity")) && this.bean.isCapacitySet() && !copy._isSet(9)) {
               Object o = this.bean.getCapacity();
               copy.setCapacity((CapacityBean)null);
               copy.setCapacity(o == null ? null : (CapacityBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CapacityName")) && this.bean.isCapacityNameSet()) {
               copy.setCapacityName(this.bean.getCapacityName());
            }

            if ((excludeProps == null || !excludeProps.contains("ContextRequestClass")) && this.bean.isContextRequestClassSet() && !copy._isSet(3)) {
               Object o = this.bean.getContextRequestClass();
               copy.setContextRequestClass((ContextRequestClassBean)null);
               copy.setContextRequestClass(o == null ? null : (ContextRequestClassBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("FairShareRequestClass")) && this.bean.isFairShareRequestClassSet() && !copy._isSet(2)) {
               Object o = this.bean.getFairShareRequestClass();
               copy.setFairShareRequestClass((FairShareRequestClassBean)null);
               copy.setFairShareRequestClass(o == null ? null : (FairShareRequestClassBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreStuckThreads")) && this.bean.isIgnoreStuckThreadsSet()) {
               copy.setIgnoreStuckThreads(this.bean.getIgnoreStuckThreads());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxThreadsConstraint")) && this.bean.isMaxThreadsConstraintSet() && !copy._isSet(7)) {
               Object o = this.bean.getMaxThreadsConstraint();
               copy.setMaxThreadsConstraint((MaxThreadsConstraintBean)null);
               copy.setMaxThreadsConstraint(o == null ? null : (MaxThreadsConstraintBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MaxThreadsConstraintName")) && this.bean.isMaxThreadsConstraintNameSet()) {
               copy.setMaxThreadsConstraintName(this.bean.getMaxThreadsConstraintName());
            }

            if ((excludeProps == null || !excludeProps.contains("MinThreadsConstraint")) && this.bean.isMinThreadsConstraintSet() && !copy._isSet(5)) {
               Object o = this.bean.getMinThreadsConstraint();
               copy.setMinThreadsConstraint((MinThreadsConstraintBean)null);
               copy.setMinThreadsConstraint(o == null ? null : (MinThreadsConstraintBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MinThreadsConstraintName")) && this.bean.isMinThreadsConstraintNameSet()) {
               copy.setMinThreadsConstraintName(this.bean.getMinThreadsConstraintName());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("RequestClassName")) && this.bean.isRequestClassNameSet()) {
               copy.setRequestClassName(this.bean.getRequestClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseTimeRequestClass")) && this.bean.isResponseTimeRequestClassSet() && !copy._isSet(1)) {
               Object o = this.bean.getResponseTimeRequestClass();
               copy.setResponseTimeRequestClass((ResponseTimeRequestClassBean)null);
               copy.setResponseTimeRequestClass(o == null ? null : (ResponseTimeRequestClassBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManagerShutdownTrigger")) && this.bean.isWorkManagerShutdownTriggerSet() && !copy._isSet(11)) {
               Object o = this.bean.getWorkManagerShutdownTrigger();
               copy.setWorkManagerShutdownTrigger((WorkManagerShutdownTriggerBean)null);
               copy.setWorkManagerShutdownTrigger(o == null ? null : (WorkManagerShutdownTriggerBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getCapacity(), clazz, annotation);
         this.inferSubTree(this.bean.getContextRequestClass(), clazz, annotation);
         this.inferSubTree(this.bean.getFairShareRequestClass(), clazz, annotation);
         this.inferSubTree(this.bean.getMaxThreadsConstraint(), clazz, annotation);
         this.inferSubTree(this.bean.getMinThreadsConstraint(), clazz, annotation);
         this.inferSubTree(this.bean.getResponseTimeRequestClass(), clazz, annotation);
         this.inferSubTree(this.bean.getWorkManagerShutdownTrigger(), clazz, annotation);
      }
   }
}

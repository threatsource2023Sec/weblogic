package weblogic.management.configuration;

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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WorkManagerMBeanImpl extends DeploymentMBeanImpl implements WorkManagerMBean, Serializable {
   private boolean _ApplicationScope;
   private CapacityMBean _Capacity;
   private ContextRequestClassMBean _ContextRequestClass;
   private FairShareRequestClassMBean _FairShareRequestClass;
   private boolean _IgnoreStuckThreads;
   private MaxThreadsConstraintMBean _MaxThreadsConstraint;
   private MinThreadsConstraintMBean _MinThreadsConstraint;
   private ResponseTimeRequestClassMBean _ResponseTimeRequestClass;
   private WorkManagerShutdownTriggerMBean _WorkManagerShutdownTrigger;
   private static SchemaHelper2 _schemaHelper;

   public WorkManagerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WorkManagerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WorkManagerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public FairShareRequestClassMBean getFairShareRequestClass() {
      return this._FairShareRequestClass;
   }

   public String getFairShareRequestClassAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getFairShareRequestClass();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isFairShareRequestClassInherited() {
      return false;
   }

   public boolean isFairShareRequestClassSet() {
      return this._isSet(12);
   }

   public void setFairShareRequestClassAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, FairShareRequestClassMBean.class, new ReferenceManager.Resolver(this, 12) {
            public void resolveReference(Object value) {
               try {
                  WorkManagerMBeanImpl.this.setFairShareRequestClass((FairShareRequestClassMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         FairShareRequestClassMBean _oldVal = this._FairShareRequestClass;
         this._initializeProperty(12);
         this._postSet(12, _oldVal, this._FairShareRequestClass);
      }

   }

   public void setFairShareRequestClass(FairShareRequestClassMBean param0) {
      WorkManagerLegalHelper.validateTargets(this, param0);
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 12, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return WorkManagerMBeanImpl.this.getFairShareRequestClass();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      FairShareRequestClassMBean _oldVal = this._FairShareRequestClass;
      this._FairShareRequestClass = param0;
      this._postSet(12, _oldVal, param0);
   }

   public ResponseTimeRequestClassMBean getResponseTimeRequestClass() {
      return this._ResponseTimeRequestClass;
   }

   public String getResponseTimeRequestClassAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getResponseTimeRequestClass();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isResponseTimeRequestClassInherited() {
      return false;
   }

   public boolean isResponseTimeRequestClassSet() {
      return this._isSet(13);
   }

   public void setResponseTimeRequestClassAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ResponseTimeRequestClassMBean.class, new ReferenceManager.Resolver(this, 13) {
            public void resolveReference(Object value) {
               try {
                  WorkManagerMBeanImpl.this.setResponseTimeRequestClass((ResponseTimeRequestClassMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ResponseTimeRequestClassMBean _oldVal = this._ResponseTimeRequestClass;
         this._initializeProperty(13);
         this._postSet(13, _oldVal, this._ResponseTimeRequestClass);
      }

   }

   public void setResponseTimeRequestClass(ResponseTimeRequestClassMBean param0) {
      WorkManagerLegalHelper.validateTargets(this, param0);
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 13, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return WorkManagerMBeanImpl.this.getResponseTimeRequestClass();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      ResponseTimeRequestClassMBean _oldVal = this._ResponseTimeRequestClass;
      this._ResponseTimeRequestClass = param0;
      this._postSet(13, _oldVal, param0);
   }

   public ContextRequestClassMBean getContextRequestClass() {
      return this._ContextRequestClass;
   }

   public String getContextRequestClassAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getContextRequestClass();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isContextRequestClassInherited() {
      return false;
   }

   public boolean isContextRequestClassSet() {
      return this._isSet(14);
   }

   public void setContextRequestClassAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ContextRequestClassMBean.class, new ReferenceManager.Resolver(this, 14) {
            public void resolveReference(Object value) {
               try {
                  WorkManagerMBeanImpl.this.setContextRequestClass((ContextRequestClassMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ContextRequestClassMBean _oldVal = this._ContextRequestClass;
         this._initializeProperty(14);
         this._postSet(14, _oldVal, this._ContextRequestClass);
      }

   }

   public void setContextRequestClass(ContextRequestClassMBean param0) {
      WorkManagerLegalHelper.validateTargets(this, param0);
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 14, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return WorkManagerMBeanImpl.this.getContextRequestClass();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      ContextRequestClassMBean _oldVal = this._ContextRequestClass;
      this._ContextRequestClass = param0;
      this._postSet(14, _oldVal, param0);
   }

   public MinThreadsConstraintMBean getMinThreadsConstraint() {
      return this._MinThreadsConstraint;
   }

   public String getMinThreadsConstraintAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getMinThreadsConstraint();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isMinThreadsConstraintInherited() {
      return false;
   }

   public boolean isMinThreadsConstraintSet() {
      return this._isSet(15);
   }

   public void setMinThreadsConstraintAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, MinThreadsConstraintMBean.class, new ReferenceManager.Resolver(this, 15) {
            public void resolveReference(Object value) {
               try {
                  WorkManagerMBeanImpl.this.setMinThreadsConstraint((MinThreadsConstraintMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         MinThreadsConstraintMBean _oldVal = this._MinThreadsConstraint;
         this._initializeProperty(15);
         this._postSet(15, _oldVal, this._MinThreadsConstraint);
      }

   }

   public void setMinThreadsConstraint(MinThreadsConstraintMBean param0) {
      WorkManagerLegalHelper.validateTargets(this, param0);
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 15, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return WorkManagerMBeanImpl.this.getMinThreadsConstraint();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      MinThreadsConstraintMBean _oldVal = this._MinThreadsConstraint;
      this._MinThreadsConstraint = param0;
      this._postSet(15, _oldVal, param0);
   }

   public MaxThreadsConstraintMBean getMaxThreadsConstraint() {
      return this._MaxThreadsConstraint;
   }

   public String getMaxThreadsConstraintAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getMaxThreadsConstraint();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isMaxThreadsConstraintInherited() {
      return false;
   }

   public boolean isMaxThreadsConstraintSet() {
      return this._isSet(16);
   }

   public void setMaxThreadsConstraintAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, MaxThreadsConstraintMBean.class, new ReferenceManager.Resolver(this, 16) {
            public void resolveReference(Object value) {
               try {
                  WorkManagerMBeanImpl.this.setMaxThreadsConstraint((MaxThreadsConstraintMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         MaxThreadsConstraintMBean _oldVal = this._MaxThreadsConstraint;
         this._initializeProperty(16);
         this._postSet(16, _oldVal, this._MaxThreadsConstraint);
      }

   }

   public void setMaxThreadsConstraint(MaxThreadsConstraintMBean param0) {
      WorkManagerLegalHelper.validateTargets(this, param0);
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 16, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return WorkManagerMBeanImpl.this.getMaxThreadsConstraint();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      MaxThreadsConstraintMBean _oldVal = this._MaxThreadsConstraint;
      this._MaxThreadsConstraint = param0;
      this._postSet(16, _oldVal, param0);
   }

   public CapacityMBean getCapacity() {
      return this._Capacity;
   }

   public String getCapacityAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getCapacity();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isCapacityInherited() {
      return false;
   }

   public boolean isCapacitySet() {
      return this._isSet(17);
   }

   public void setCapacityAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, CapacityMBean.class, new ReferenceManager.Resolver(this, 17) {
            public void resolveReference(Object value) {
               try {
                  WorkManagerMBeanImpl.this.setCapacity((CapacityMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         CapacityMBean _oldVal = this._Capacity;
         this._initializeProperty(17);
         this._postSet(17, _oldVal, this._Capacity);
      }

   }

   public void setCapacity(CapacityMBean param0) {
      WorkManagerLegalHelper.validateTargets(this, param0);
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 17, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return WorkManagerMBeanImpl.this.getCapacity();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      CapacityMBean _oldVal = this._Capacity;
      this._Capacity = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean getIgnoreStuckThreads() {
      return this._IgnoreStuckThreads;
   }

   public boolean isIgnoreStuckThreadsInherited() {
      return false;
   }

   public boolean isIgnoreStuckThreadsSet() {
      return this._isSet(18);
   }

   public void setIgnoreStuckThreads(boolean param0) {
      boolean _oldVal = this._IgnoreStuckThreads;
      this._IgnoreStuckThreads = param0;
      this._postSet(18, _oldVal, param0);
   }

   public WorkManagerShutdownTriggerMBean getWorkManagerShutdownTrigger() {
      return this._WorkManagerShutdownTrigger;
   }

   public boolean isWorkManagerShutdownTriggerInherited() {
      return false;
   }

   public boolean isWorkManagerShutdownTriggerSet() {
      return this._isSet(19);
   }

   public void setWorkManagerShutdownTrigger(WorkManagerShutdownTriggerMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWorkManagerShutdownTrigger() != null && param0 != this.getWorkManagerShutdownTrigger()) {
         throw new BeanAlreadyExistsException(this.getWorkManagerShutdownTrigger() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 19)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WorkManagerShutdownTriggerMBean _oldVal = this._WorkManagerShutdownTrigger;
         this._WorkManagerShutdownTrigger = param0;
         this._postSet(19, _oldVal, param0);
      }
   }

   public WorkManagerShutdownTriggerMBean createWorkManagerShutdownTrigger() {
      WorkManagerShutdownTriggerMBeanImpl _val = new WorkManagerShutdownTriggerMBeanImpl(this, -1);

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
               this.setWorkManagerShutdownTrigger((WorkManagerShutdownTriggerMBean)null);
               this._unSet(19);
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

   public boolean isApplicationScope() {
      return this._ApplicationScope;
   }

   public boolean isApplicationScopeInherited() {
      return false;
   }

   public boolean isApplicationScopeSet() {
      return this._isSet(20);
   }

   public void setApplicationScope(boolean param0) {
      boolean _oldVal = this._ApplicationScope;
      this._ApplicationScope = param0;
      this._postSet(20, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WorkManagerLegalHelper.validateWorkManager(this);
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
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._Capacity = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._ContextRequestClass = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._FairShareRequestClass = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._IgnoreStuckThreads = false;
               if (initOne) {
                  break;
               }
            case 16:
               this._MaxThreadsConstraint = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._MinThreadsConstraint = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ResponseTimeRequestClass = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._WorkManagerShutdownTrigger = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._ApplicationScope = true;
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
      return "WorkManager";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("ApplicationScope")) {
         oldVal = this._ApplicationScope;
         this._ApplicationScope = (Boolean)v;
         this._postSet(20, oldVal, this._ApplicationScope);
      } else if (name.equals("Capacity")) {
         CapacityMBean oldVal = this._Capacity;
         this._Capacity = (CapacityMBean)v;
         this._postSet(17, oldVal, this._Capacity);
      } else if (name.equals("ContextRequestClass")) {
         ContextRequestClassMBean oldVal = this._ContextRequestClass;
         this._ContextRequestClass = (ContextRequestClassMBean)v;
         this._postSet(14, oldVal, this._ContextRequestClass);
      } else if (name.equals("FairShareRequestClass")) {
         FairShareRequestClassMBean oldVal = this._FairShareRequestClass;
         this._FairShareRequestClass = (FairShareRequestClassMBean)v;
         this._postSet(12, oldVal, this._FairShareRequestClass);
      } else if (name.equals("IgnoreStuckThreads")) {
         oldVal = this._IgnoreStuckThreads;
         this._IgnoreStuckThreads = (Boolean)v;
         this._postSet(18, oldVal, this._IgnoreStuckThreads);
      } else if (name.equals("MaxThreadsConstraint")) {
         MaxThreadsConstraintMBean oldVal = this._MaxThreadsConstraint;
         this._MaxThreadsConstraint = (MaxThreadsConstraintMBean)v;
         this._postSet(16, oldVal, this._MaxThreadsConstraint);
      } else if (name.equals("MinThreadsConstraint")) {
         MinThreadsConstraintMBean oldVal = this._MinThreadsConstraint;
         this._MinThreadsConstraint = (MinThreadsConstraintMBean)v;
         this._postSet(15, oldVal, this._MinThreadsConstraint);
      } else if (name.equals("ResponseTimeRequestClass")) {
         ResponseTimeRequestClassMBean oldVal = this._ResponseTimeRequestClass;
         this._ResponseTimeRequestClass = (ResponseTimeRequestClassMBean)v;
         this._postSet(13, oldVal, this._ResponseTimeRequestClass);
      } else if (name.equals("WorkManagerShutdownTrigger")) {
         WorkManagerShutdownTriggerMBean oldVal = this._WorkManagerShutdownTrigger;
         this._WorkManagerShutdownTrigger = (WorkManagerShutdownTriggerMBean)v;
         this._postSet(19, oldVal, this._WorkManagerShutdownTrigger);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ApplicationScope")) {
         return new Boolean(this._ApplicationScope);
      } else if (name.equals("Capacity")) {
         return this._Capacity;
      } else if (name.equals("ContextRequestClass")) {
         return this._ContextRequestClass;
      } else if (name.equals("FairShareRequestClass")) {
         return this._FairShareRequestClass;
      } else if (name.equals("IgnoreStuckThreads")) {
         return new Boolean(this._IgnoreStuckThreads);
      } else if (name.equals("MaxThreadsConstraint")) {
         return this._MaxThreadsConstraint;
      } else if (name.equals("MinThreadsConstraint")) {
         return this._MinThreadsConstraint;
      } else if (name.equals("ResponseTimeRequestClass")) {
         return this._ResponseTimeRequestClass;
      } else {
         return name.equals("WorkManagerShutdownTrigger") ? this._WorkManagerShutdownTrigger : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("capacity")) {
                  return 17;
               }
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 23:
            case 25:
            case 26:
            case 28:
            default:
               break;
            case 17:
               if (s.equals("application-scope")) {
                  return 20;
               }
               break;
            case 20:
               if (s.equals("ignore-stuck-threads")) {
                  return 18;
               }
               break;
            case 21:
               if (s.equals("context-request-class")) {
                  return 14;
               }
               break;
            case 22:
               if (s.equals("max-threads-constraint")) {
                  return 16;
               }

               if (s.equals("min-threads-constraint")) {
                  return 15;
               }
               break;
            case 24:
               if (s.equals("fair-share-request-class")) {
                  return 12;
               }
               break;
            case 27:
               if (s.equals("response-time-request-class")) {
                  return 13;
               }
               break;
            case 29:
               if (s.equals("work-manager-shutdown-trigger")) {
                  return 19;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 19:
               return new WorkManagerShutdownTriggerMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "fair-share-request-class";
            case 13:
               return "response-time-request-class";
            case 14:
               return "context-request-class";
            case 15:
               return "min-threads-constraint";
            case 16:
               return "max-threads-constraint";
            case 17:
               return "capacity";
            case 18:
               return "ignore-stuck-threads";
            case 19:
               return "work-manager-shutdown-trigger";
            case 20:
               return "application-scope";
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private WorkManagerMBeanImpl bean;

      protected Helper(WorkManagerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "FairShareRequestClass";
            case 13:
               return "ResponseTimeRequestClass";
            case 14:
               return "ContextRequestClass";
            case 15:
               return "MinThreadsConstraint";
            case 16:
               return "MaxThreadsConstraint";
            case 17:
               return "Capacity";
            case 18:
               return "IgnoreStuckThreads";
            case 19:
               return "WorkManagerShutdownTrigger";
            case 20:
               return "ApplicationScope";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Capacity")) {
            return 17;
         } else if (propName.equals("ContextRequestClass")) {
            return 14;
         } else if (propName.equals("FairShareRequestClass")) {
            return 12;
         } else if (propName.equals("IgnoreStuckThreads")) {
            return 18;
         } else if (propName.equals("MaxThreadsConstraint")) {
            return 16;
         } else if (propName.equals("MinThreadsConstraint")) {
            return 15;
         } else if (propName.equals("ResponseTimeRequestClass")) {
            return 13;
         } else if (propName.equals("WorkManagerShutdownTrigger")) {
            return 19;
         } else {
            return propName.equals("ApplicationScope") ? 20 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getWorkManagerShutdownTrigger() != null) {
            iterators.add(new ArrayIterator(new WorkManagerShutdownTriggerMBean[]{this.bean.getWorkManagerShutdownTrigger()}));
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
            if (this.bean.isCapacitySet()) {
               buf.append("Capacity");
               buf.append(String.valueOf(this.bean.getCapacity()));
            }

            if (this.bean.isContextRequestClassSet()) {
               buf.append("ContextRequestClass");
               buf.append(String.valueOf(this.bean.getContextRequestClass()));
            }

            if (this.bean.isFairShareRequestClassSet()) {
               buf.append("FairShareRequestClass");
               buf.append(String.valueOf(this.bean.getFairShareRequestClass()));
            }

            if (this.bean.isIgnoreStuckThreadsSet()) {
               buf.append("IgnoreStuckThreads");
               buf.append(String.valueOf(this.bean.getIgnoreStuckThreads()));
            }

            if (this.bean.isMaxThreadsConstraintSet()) {
               buf.append("MaxThreadsConstraint");
               buf.append(String.valueOf(this.bean.getMaxThreadsConstraint()));
            }

            if (this.bean.isMinThreadsConstraintSet()) {
               buf.append("MinThreadsConstraint");
               buf.append(String.valueOf(this.bean.getMinThreadsConstraint()));
            }

            if (this.bean.isResponseTimeRequestClassSet()) {
               buf.append("ResponseTimeRequestClass");
               buf.append(String.valueOf(this.bean.getResponseTimeRequestClass()));
            }

            childValue = this.computeChildHashValue(this.bean.getWorkManagerShutdownTrigger());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isApplicationScopeSet()) {
               buf.append("ApplicationScope");
               buf.append(String.valueOf(this.bean.isApplicationScope()));
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
            WorkManagerMBeanImpl otherTyped = (WorkManagerMBeanImpl)other;
            this.computeDiff("Capacity", this.bean.getCapacity(), otherTyped.getCapacity(), false);
            this.computeDiff("ContextRequestClass", this.bean.getContextRequestClass(), otherTyped.getContextRequestClass(), false);
            this.computeDiff("FairShareRequestClass", this.bean.getFairShareRequestClass(), otherTyped.getFairShareRequestClass(), false);
            this.computeDiff("IgnoreStuckThreads", this.bean.getIgnoreStuckThreads(), otherTyped.getIgnoreStuckThreads(), false);
            this.computeDiff("MaxThreadsConstraint", this.bean.getMaxThreadsConstraint(), otherTyped.getMaxThreadsConstraint(), false);
            this.computeDiff("MinThreadsConstraint", this.bean.getMinThreadsConstraint(), otherTyped.getMinThreadsConstraint(), false);
            this.computeDiff("ResponseTimeRequestClass", this.bean.getResponseTimeRequestClass(), otherTyped.getResponseTimeRequestClass(), false);
            this.computeChildDiff("WorkManagerShutdownTrigger", this.bean.getWorkManagerShutdownTrigger(), otherTyped.getWorkManagerShutdownTrigger(), false);
            this.computeDiff("ApplicationScope", this.bean.isApplicationScope(), otherTyped.isApplicationScope(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WorkManagerMBeanImpl original = (WorkManagerMBeanImpl)event.getSourceBean();
            WorkManagerMBeanImpl proposed = (WorkManagerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Capacity")) {
                  original.setCapacityAsString(proposed.getCapacityAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("ContextRequestClass")) {
                  original.setContextRequestClassAsString(proposed.getContextRequestClassAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("FairShareRequestClass")) {
                  original.setFairShareRequestClassAsString(proposed.getFairShareRequestClassAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("IgnoreStuckThreads")) {
                  original.setIgnoreStuckThreads(proposed.getIgnoreStuckThreads());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("MaxThreadsConstraint")) {
                  original.setMaxThreadsConstraintAsString(proposed.getMaxThreadsConstraintAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("MinThreadsConstraint")) {
                  original.setMinThreadsConstraintAsString(proposed.getMinThreadsConstraintAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("ResponseTimeRequestClass")) {
                  original.setResponseTimeRequestClassAsString(proposed.getResponseTimeRequestClassAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("WorkManagerShutdownTrigger")) {
                  if (type == 2) {
                     original.setWorkManagerShutdownTrigger((WorkManagerShutdownTriggerMBean)this.createCopy((AbstractDescriptorBean)proposed.getWorkManagerShutdownTrigger()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WorkManagerShutdownTrigger", original.getWorkManagerShutdownTrigger());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("ApplicationScope")) {
                  original.setApplicationScope(proposed.isApplicationScope());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
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
            WorkManagerMBeanImpl copy = (WorkManagerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Capacity")) && this.bean.isCapacitySet()) {
               copy._unSet(copy, 17);
               copy.setCapacityAsString(this.bean.getCapacityAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ContextRequestClass")) && this.bean.isContextRequestClassSet()) {
               copy._unSet(copy, 14);
               copy.setContextRequestClassAsString(this.bean.getContextRequestClassAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("FairShareRequestClass")) && this.bean.isFairShareRequestClassSet()) {
               copy._unSet(copy, 12);
               copy.setFairShareRequestClassAsString(this.bean.getFairShareRequestClassAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("IgnoreStuckThreads")) && this.bean.isIgnoreStuckThreadsSet()) {
               copy.setIgnoreStuckThreads(this.bean.getIgnoreStuckThreads());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxThreadsConstraint")) && this.bean.isMaxThreadsConstraintSet()) {
               copy._unSet(copy, 16);
               copy.setMaxThreadsConstraintAsString(this.bean.getMaxThreadsConstraintAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("MinThreadsConstraint")) && this.bean.isMinThreadsConstraintSet()) {
               copy._unSet(copy, 15);
               copy.setMinThreadsConstraintAsString(this.bean.getMinThreadsConstraintAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseTimeRequestClass")) && this.bean.isResponseTimeRequestClassSet()) {
               copy._unSet(copy, 13);
               copy.setResponseTimeRequestClassAsString(this.bean.getResponseTimeRequestClassAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManagerShutdownTrigger")) && this.bean.isWorkManagerShutdownTriggerSet() && !copy._isSet(19)) {
               Object o = this.bean.getWorkManagerShutdownTrigger();
               copy.setWorkManagerShutdownTrigger((WorkManagerShutdownTriggerMBean)null);
               copy.setWorkManagerShutdownTrigger(o == null ? null : (WorkManagerShutdownTriggerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ApplicationScope")) && this.bean.isApplicationScopeSet()) {
               copy.setApplicationScope(this.bean.isApplicationScope());
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

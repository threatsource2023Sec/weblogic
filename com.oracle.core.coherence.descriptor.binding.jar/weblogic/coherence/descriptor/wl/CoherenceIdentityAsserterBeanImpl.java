package weblogic.coherence.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
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

public class CoherenceIdentityAsserterBeanImpl extends SettableBeanImpl implements CoherenceIdentityAsserterBean, Serializable {
   private String _ClassName;
   private CoherenceInitParamBean[] _CoherenceInitParams;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceIdentityAsserterBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceIdentityAsserterBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceIdentityAsserterBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getClassName() {
      return this._ClassName;
   }

   public boolean isClassNameInherited() {
      return false;
   }

   public boolean isClassNameSet() {
      return this._isSet(0);
   }

   public void setClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ClassName;
      this._ClassName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addCoherenceInitParam(CoherenceInitParamBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         CoherenceInitParamBean[] _new;
         if (this._isSet(1)) {
            _new = (CoherenceInitParamBean[])((CoherenceInitParamBean[])this._getHelper()._extendArray(this.getCoherenceInitParams(), CoherenceInitParamBean.class, param0));
         } else {
            _new = new CoherenceInitParamBean[]{param0};
         }

         try {
            this.setCoherenceInitParams(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceInitParamBean[] getCoherenceInitParams() {
      return this._CoherenceInitParams;
   }

   public boolean isCoherenceInitParamsInherited() {
      return false;
   }

   public boolean isCoherenceInitParamsSet() {
      return this._isSet(1);
   }

   public void removeCoherenceInitParam(CoherenceInitParamBean param0) {
      this.destroyCoherenceInitParam(param0);
   }

   public void setCoherenceInitParams(CoherenceInitParamBean[] param0) {
      CoherenceInitParamBean[] param0 = param0 == null ? new CoherenceInitParamBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CoherenceInitParamBean[] _oldVal = this._CoherenceInitParams;
      this._CoherenceInitParams = (CoherenceInitParamBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public CoherenceInitParamBean createCoherenceInitParam(String param0) {
      CoherenceInitParamBeanImpl lookup = (CoherenceInitParamBeanImpl)this.lookupCoherenceInitParam(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceInitParamBeanImpl _val = new CoherenceInitParamBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceInitParam(_val);
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

   public CoherenceInitParamBean lookupCoherenceInitParam(String param0) {
      Object[] aary = (Object[])this._CoherenceInitParams;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceInitParamBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceInitParamBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyCoherenceInitParam(CoherenceInitParamBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         CoherenceInitParamBean[] _old = this.getCoherenceInitParams();
         CoherenceInitParamBean[] _new = (CoherenceInitParamBean[])((CoherenceInitParamBean[])this._getHelper()._removeElement(_old, CoherenceInitParamBean.class, param0));
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
               this.setCoherenceInitParams(_new);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ClassName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._CoherenceInitParams = new CoherenceInitParamBean[0];
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
            case 10:
               if (s.equals("class-name")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("coherence-init-param")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new CoherenceInitParamBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "class-name";
            case 1:
               return "coherence-init-param";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private CoherenceIdentityAsserterBeanImpl bean;

      protected Helper(CoherenceIdentityAsserterBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ClassName";
            case 1:
               return "CoherenceInitParams";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClassName")) {
            return 0;
         } else {
            return propName.equals("CoherenceInitParams") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCoherenceInitParams()));
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
            if (this.bean.isClassNameSet()) {
               buf.append("ClassName");
               buf.append(String.valueOf(this.bean.getClassName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getCoherenceInitParams().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceInitParams()[i]);
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
            CoherenceIdentityAsserterBeanImpl otherTyped = (CoherenceIdentityAsserterBeanImpl)other;
            this.computeDiff("ClassName", this.bean.getClassName(), otherTyped.getClassName(), false);
            this.computeChildDiff("CoherenceInitParams", this.bean.getCoherenceInitParams(), otherTyped.getCoherenceInitParams(), false, true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceIdentityAsserterBeanImpl original = (CoherenceIdentityAsserterBeanImpl)event.getSourceBean();
            CoherenceIdentityAsserterBeanImpl proposed = (CoherenceIdentityAsserterBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClassName")) {
                  original.setClassName(proposed.getClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("CoherenceInitParams")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceInitParam((CoherenceInitParamBean)update.getAddedObject());
                     }

                     this.reorderArrayObjects(original.getCoherenceInitParams(), proposed.getCoherenceInitParams());
                  } else if (type == 3) {
                     original.removeCoherenceInitParam((CoherenceInitParamBean)update.getRemovedObject());
                  } else {
                     this.reorderArrayObjects(original.getCoherenceInitParams(), proposed.getCoherenceInitParams());
                  }

                  if (original.getCoherenceInitParams() == null || original.getCoherenceInitParams().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            CoherenceIdentityAsserterBeanImpl copy = (CoherenceIdentityAsserterBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClassName")) && this.bean.isClassNameSet()) {
               copy.setClassName(this.bean.getClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceInitParams")) && this.bean.isCoherenceInitParamsSet() && !copy._isSet(1)) {
               CoherenceInitParamBean[] oldCoherenceInitParams = this.bean.getCoherenceInitParams();
               CoherenceInitParamBean[] newCoherenceInitParams = new CoherenceInitParamBean[oldCoherenceInitParams.length];

               for(int i = 0; i < newCoherenceInitParams.length; ++i) {
                  newCoherenceInitParams[i] = (CoherenceInitParamBean)((CoherenceInitParamBean)this.createCopy((AbstractDescriptorBean)oldCoherenceInitParams[i], includeObsolete));
               }

               copy.setCoherenceInitParams(newCoherenceInitParams);
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
         this.inferSubTree(this.bean.getCoherenceInitParams(), clazz, annotation);
      }
   }
}

package weblogic.j2ee.descriptor;

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

public class InterceptorMethodsBeanImpl extends AbstractDescriptorBean implements InterceptorMethodsBean, Serializable {
   private AroundInvokeBean[] _AroundInvokes;
   private AroundTimeoutBean[] _AroundTimeouts;
   private static SchemaHelper2 _schemaHelper;

   public InterceptorMethodsBeanImpl() {
      this._initializeProperty(-1);
   }

   public InterceptorMethodsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InterceptorMethodsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addAroundInvoke(AroundInvokeBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         AroundInvokeBean[] _new;
         if (this._isSet(0)) {
            _new = (AroundInvokeBean[])((AroundInvokeBean[])this._getHelper()._extendArray(this.getAroundInvokes(), AroundInvokeBean.class, param0));
         } else {
            _new = new AroundInvokeBean[]{param0};
         }

         try {
            this.setAroundInvokes(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AroundInvokeBean[] getAroundInvokes() {
      return this._AroundInvokes;
   }

   public boolean isAroundInvokesInherited() {
      return false;
   }

   public boolean isAroundInvokesSet() {
      return this._isSet(0);
   }

   public void removeAroundInvoke(AroundInvokeBean param0) {
      this.destroyAroundInvoke(param0);
   }

   public void setAroundInvokes(AroundInvokeBean[] param0) throws InvalidAttributeValueException {
      AroundInvokeBean[] param0 = param0 == null ? new AroundInvokeBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AroundInvokeBean[] _oldVal = this._AroundInvokes;
      this._AroundInvokes = (AroundInvokeBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public AroundInvokeBean createAroundInvoke() {
      AroundInvokeBeanImpl _val = new AroundInvokeBeanImpl(this, -1);

      try {
         this.addAroundInvoke(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAroundInvoke(AroundInvokeBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         AroundInvokeBean[] _old = this.getAroundInvokes();
         AroundInvokeBean[] _new = (AroundInvokeBean[])((AroundInvokeBean[])this._getHelper()._removeElement(_old, AroundInvokeBean.class, param0));
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
               this.setAroundInvokes(_new);
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

   public void addAroundTimeout(AroundTimeoutBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         AroundTimeoutBean[] _new;
         if (this._isSet(1)) {
            _new = (AroundTimeoutBean[])((AroundTimeoutBean[])this._getHelper()._extendArray(this.getAroundTimeouts(), AroundTimeoutBean.class, param0));
         } else {
            _new = new AroundTimeoutBean[]{param0};
         }

         try {
            this.setAroundTimeouts(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AroundTimeoutBean[] getAroundTimeouts() {
      return this._AroundTimeouts;
   }

   public boolean isAroundTimeoutsInherited() {
      return false;
   }

   public boolean isAroundTimeoutsSet() {
      return this._isSet(1);
   }

   public void removeAroundTimeout(AroundTimeoutBean param0) {
      this.destroyAroundTimeout(param0);
   }

   public void setAroundTimeouts(AroundTimeoutBean[] param0) throws InvalidAttributeValueException {
      AroundTimeoutBean[] param0 = param0 == null ? new AroundTimeoutBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AroundTimeoutBean[] _oldVal = this._AroundTimeouts;
      this._AroundTimeouts = (AroundTimeoutBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public AroundTimeoutBean createAroundTimeout() {
      AroundTimeoutBeanImpl _val = new AroundTimeoutBeanImpl(this, -1);

      try {
         this.addAroundTimeout(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAroundTimeout(AroundTimeoutBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         AroundTimeoutBean[] _old = this.getAroundTimeouts();
         AroundTimeoutBean[] _new = (AroundTimeoutBean[])((AroundTimeoutBean[])this._getHelper()._removeElement(_old, AroundTimeoutBean.class, param0));
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
               this.setAroundTimeouts(_new);
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
               this._AroundInvokes = new AroundInvokeBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._AroundTimeouts = new AroundTimeoutBean[0];
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
               if (s.equals("around-invoke")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("around-timeout")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new AroundInvokeBeanImpl.SchemaHelper2();
            case 1:
               return new AroundTimeoutBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "around-invoke";
            case 1:
               return "around-timeout";
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
      private InterceptorMethodsBeanImpl bean;

      protected Helper(InterceptorMethodsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AroundInvokes";
            case 1:
               return "AroundTimeouts";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AroundInvokes")) {
            return 0;
         } else {
            return propName.equals("AroundTimeouts") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAroundInvokes()));
         iterators.add(new ArrayIterator(this.bean.getAroundTimeouts()));
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
            for(i = 0; i < this.bean.getAroundInvokes().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAroundInvokes()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getAroundTimeouts().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAroundTimeouts()[i]);
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
            InterceptorMethodsBeanImpl otherTyped = (InterceptorMethodsBeanImpl)other;
            this.computeChildDiff("AroundInvokes", this.bean.getAroundInvokes(), otherTyped.getAroundInvokes(), false);
            this.computeChildDiff("AroundTimeouts", this.bean.getAroundTimeouts(), otherTyped.getAroundTimeouts(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InterceptorMethodsBeanImpl original = (InterceptorMethodsBeanImpl)event.getSourceBean();
            InterceptorMethodsBeanImpl proposed = (InterceptorMethodsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AroundInvokes")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAroundInvoke((AroundInvokeBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAroundInvoke((AroundInvokeBean)update.getRemovedObject());
                  }

                  if (original.getAroundInvokes() == null || original.getAroundInvokes().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("AroundTimeouts")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAroundTimeout((AroundTimeoutBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAroundTimeout((AroundTimeoutBean)update.getRemovedObject());
                  }

                  if (original.getAroundTimeouts() == null || original.getAroundTimeouts().length == 0) {
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
            InterceptorMethodsBeanImpl copy = (InterceptorMethodsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("AroundInvokes")) && this.bean.isAroundInvokesSet() && !copy._isSet(0)) {
               AroundInvokeBean[] oldAroundInvokes = this.bean.getAroundInvokes();
               AroundInvokeBean[] newAroundInvokes = new AroundInvokeBean[oldAroundInvokes.length];

               for(i = 0; i < newAroundInvokes.length; ++i) {
                  newAroundInvokes[i] = (AroundInvokeBean)((AroundInvokeBean)this.createCopy((AbstractDescriptorBean)oldAroundInvokes[i], includeObsolete));
               }

               copy.setAroundInvokes(newAroundInvokes);
            }

            if ((excludeProps == null || !excludeProps.contains("AroundTimeouts")) && this.bean.isAroundTimeoutsSet() && !copy._isSet(1)) {
               AroundTimeoutBean[] oldAroundTimeouts = this.bean.getAroundTimeouts();
               AroundTimeoutBean[] newAroundTimeouts = new AroundTimeoutBean[oldAroundTimeouts.length];

               for(i = 0; i < newAroundTimeouts.length; ++i) {
                  newAroundTimeouts[i] = (AroundTimeoutBean)((AroundTimeoutBean)this.createCopy((AbstractDescriptorBean)oldAroundTimeouts[i], includeObsolete));
               }

               copy.setAroundTimeouts(newAroundTimeouts);
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
         this.inferSubTree(this.bean.getAroundInvokes(), clazz, annotation);
         this.inferSubTree(this.bean.getAroundTimeouts(), clazz, annotation);
      }
   }
}

package weblogic.j2ee.descriptor.wl;

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
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.InterceptorBindingBean;
import weblogic.j2ee.descriptor.InterceptorBindingBeanImpl;
import weblogic.j2ee.descriptor.InterceptorsBean;
import weblogic.j2ee.descriptor.InterceptorsBeanImpl;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ManagedBeansBeanImpl extends AbstractDescriptorBean implements ManagedBeansBean, Serializable {
   private InterceptorBindingBean[] _InterceptorBindings;
   private InterceptorsBean _Interceptors;
   private ManagedBeanBean[] _ManagedBeans;
   private static SchemaHelper2 _schemaHelper;

   public ManagedBeansBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ManagedBeansBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public ManagedBeansBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addManagedBean(ManagedBeanBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         ManagedBeanBean[] _new;
         if (this._isSet(0)) {
            _new = (ManagedBeanBean[])((ManagedBeanBean[])this._getHelper()._extendArray(this.getManagedBeans(), ManagedBeanBean.class, param0));
         } else {
            _new = new ManagedBeanBean[]{param0};
         }

         try {
            this.setManagedBeans(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ManagedBeanBean[] getManagedBeans() {
      return this._ManagedBeans;
   }

   public boolean isManagedBeansInherited() {
      return false;
   }

   public boolean isManagedBeansSet() {
      return this._isSet(0);
   }

   public void removeManagedBean(ManagedBeanBean param0) {
      this.destroyManagedBean(param0);
   }

   public void setManagedBeans(ManagedBeanBean[] param0) throws InvalidAttributeValueException {
      ManagedBeanBean[] param0 = param0 == null ? new ManagedBeanBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ManagedBeanBean[] _oldVal = this._ManagedBeans;
      this._ManagedBeans = (ManagedBeanBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public ManagedBeanBean createManagedBean() {
      ManagedBeanBeanImpl _val = new ManagedBeanBeanImpl(this, -1);

      try {
         this.addManagedBean(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public ManagedBeanBean lookupManagedBean(String param0) {
      Object[] aary = (Object[])this._ManagedBeans;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      ManagedBeanBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (ManagedBeanBeanImpl)it.previous();
      } while(!bean.getManagedBeanName().equals(param0));

      return bean;
   }

   public void destroyManagedBean(ManagedBeanBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         ManagedBeanBean[] _old = this.getManagedBeans();
         ManagedBeanBean[] _new = (ManagedBeanBean[])((ManagedBeanBean[])this._getHelper()._removeElement(_old, ManagedBeanBean.class, param0));
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
               this.setManagedBeans(_new);
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

   public InterceptorsBean getInterceptors() {
      return this._Interceptors;
   }

   public boolean isInterceptorsInherited() {
      return false;
   }

   public boolean isInterceptorsSet() {
      return this._isSet(1);
   }

   public void setInterceptors(InterceptorsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getInterceptors() != null && param0 != this.getInterceptors()) {
         throw new BeanAlreadyExistsException(this.getInterceptors() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         InterceptorsBean _oldVal = this._Interceptors;
         this._Interceptors = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public InterceptorsBean createInterceptors() {
      InterceptorsBeanImpl _val = new InterceptorsBeanImpl(this, -1);

      try {
         this.setInterceptors(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInterceptors(InterceptorsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Interceptors;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setInterceptors((InterceptorsBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void addInterceptorBinding(InterceptorBindingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         InterceptorBindingBean[] _new;
         if (this._isSet(2)) {
            _new = (InterceptorBindingBean[])((InterceptorBindingBean[])this._getHelper()._extendArray(this.getInterceptorBindings(), InterceptorBindingBean.class, param0));
         } else {
            _new = new InterceptorBindingBean[]{param0};
         }

         try {
            this.setInterceptorBindings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InterceptorBindingBean[] getInterceptorBindings() {
      return this._InterceptorBindings;
   }

   public boolean isInterceptorBindingsInherited() {
      return false;
   }

   public boolean isInterceptorBindingsSet() {
      return this._isSet(2);
   }

   public void removeInterceptorBinding(InterceptorBindingBean param0) {
      this.destroyInterceptorBinding(param0);
   }

   public void setInterceptorBindings(InterceptorBindingBean[] param0) throws InvalidAttributeValueException {
      InterceptorBindingBean[] param0 = param0 == null ? new InterceptorBindingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InterceptorBindingBean[] _oldVal = this._InterceptorBindings;
      this._InterceptorBindings = (InterceptorBindingBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public InterceptorBindingBean createInterceptorBinding() {
      InterceptorBindingBeanImpl _val = new InterceptorBindingBeanImpl(this, -1);

      try {
         this.addInterceptorBinding(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInterceptorBinding(InterceptorBindingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         InterceptorBindingBean[] _old = this.getInterceptorBindings();
         InterceptorBindingBean[] _new = (InterceptorBindingBean[])((InterceptorBindingBean[])this._getHelper()._removeElement(_old, InterceptorBindingBean.class, param0));
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
               this.setInterceptorBindings(_new);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._InterceptorBindings = new InterceptorBindingBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Interceptors = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ManagedBeans = new ManagedBeanBean[0];
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
            case 12:
               if (s.equals("interceptors")) {
                  return 1;
               }

               if (s.equals("managed-bean")) {
                  return 0;
               }
               break;
            case 19:
               if (s.equals("interceptor-binding")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new ManagedBeanBeanImpl.SchemaHelper2();
            case 1:
               return new InterceptorsBeanImpl.SchemaHelper2();
            case 2:
               return new InterceptorBindingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "managed-beans";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "managed-bean";
            case 1:
               return "interceptors";
            case 2:
               return "interceptor-binding";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
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
      private ManagedBeansBeanImpl bean;

      protected Helper(ManagedBeansBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ManagedBeans";
            case 1:
               return "Interceptors";
            case 2:
               return "InterceptorBindings";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("InterceptorBindings")) {
            return 2;
         } else if (propName.equals("Interceptors")) {
            return 1;
         } else {
            return propName.equals("ManagedBeans") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getInterceptorBindings()));
         if (this.bean.getInterceptors() != null) {
            iterators.add(new ArrayIterator(new InterceptorsBean[]{this.bean.getInterceptors()}));
         }

         iterators.add(new ArrayIterator(this.bean.getManagedBeans()));
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
            for(i = 0; i < this.bean.getInterceptorBindings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInterceptorBindings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getInterceptors());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getManagedBeans().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getManagedBeans()[i]);
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
            ManagedBeansBeanImpl otherTyped = (ManagedBeansBeanImpl)other;
            this.computeChildDiff("InterceptorBindings", this.bean.getInterceptorBindings(), otherTyped.getInterceptorBindings(), false);
            this.computeChildDiff("Interceptors", this.bean.getInterceptors(), otherTyped.getInterceptors(), false);
            this.computeChildDiff("ManagedBeans", this.bean.getManagedBeans(), otherTyped.getManagedBeans(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ManagedBeansBeanImpl original = (ManagedBeansBeanImpl)event.getSourceBean();
            ManagedBeansBeanImpl proposed = (ManagedBeansBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("InterceptorBindings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addInterceptorBinding((InterceptorBindingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInterceptorBinding((InterceptorBindingBean)update.getRemovedObject());
                  }

                  if (original.getInterceptorBindings() == null || original.getInterceptorBindings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Interceptors")) {
                  if (type == 2) {
                     original.setInterceptors((InterceptorsBean)this.createCopy((AbstractDescriptorBean)proposed.getInterceptors()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Interceptors", (DescriptorBean)original.getInterceptors());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ManagedBeans")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addManagedBean((ManagedBeanBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeManagedBean((ManagedBeanBean)update.getRemovedObject());
                  }

                  if (original.getManagedBeans() == null || original.getManagedBeans().length == 0) {
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
            ManagedBeansBeanImpl copy = (ManagedBeansBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("InterceptorBindings")) && this.bean.isInterceptorBindingsSet() && !copy._isSet(2)) {
               InterceptorBindingBean[] oldInterceptorBindings = this.bean.getInterceptorBindings();
               InterceptorBindingBean[] newInterceptorBindings = new InterceptorBindingBean[oldInterceptorBindings.length];

               for(i = 0; i < newInterceptorBindings.length; ++i) {
                  newInterceptorBindings[i] = (InterceptorBindingBean)((InterceptorBindingBean)this.createCopy((AbstractDescriptorBean)oldInterceptorBindings[i], includeObsolete));
               }

               copy.setInterceptorBindings(newInterceptorBindings);
            }

            if ((excludeProps == null || !excludeProps.contains("Interceptors")) && this.bean.isInterceptorsSet() && !copy._isSet(1)) {
               Object o = this.bean.getInterceptors();
               copy.setInterceptors((InterceptorsBean)null);
               copy.setInterceptors(o == null ? null : (InterceptorsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedBeans")) && this.bean.isManagedBeansSet() && !copy._isSet(0)) {
               ManagedBeanBean[] oldManagedBeans = this.bean.getManagedBeans();
               ManagedBeanBean[] newManagedBeans = new ManagedBeanBean[oldManagedBeans.length];

               for(i = 0; i < newManagedBeans.length; ++i) {
                  newManagedBeans[i] = (ManagedBeanBean)((ManagedBeanBean)this.createCopy((AbstractDescriptorBean)oldManagedBeans[i], includeObsolete));
               }

               copy.setManagedBeans(newManagedBeans);
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
         this.inferSubTree(this.bean.getInterceptorBindings(), clazz, annotation);
         this.inferSubTree(this.bean.getInterceptors(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedBeans(), clazz, annotation);
      }
   }
}

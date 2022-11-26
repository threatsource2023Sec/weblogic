package com.bea.httppubsub.descriptor;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ChannelConstraintBeanImpl extends AbstractDescriptorBean implements ChannelConstraintBean, Serializable {
   private AuthConstraintBean _AuthConstraint;
   private ChannelResourceCollectionBean[] _ChannelResourceCollections;
   private static SchemaHelper2 _schemaHelper;

   public ChannelConstraintBeanImpl() {
      this._initializeProperty(-1);
   }

   public ChannelConstraintBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ChannelConstraintBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addChannelResourceCollection(ChannelResourceCollectionBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         ChannelResourceCollectionBean[] _new;
         if (this._isSet(0)) {
            _new = (ChannelResourceCollectionBean[])((ChannelResourceCollectionBean[])this._getHelper()._extendArray(this.getChannelResourceCollections(), ChannelResourceCollectionBean.class, param0));
         } else {
            _new = new ChannelResourceCollectionBean[]{param0};
         }

         try {
            this.setChannelResourceCollections(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ChannelResourceCollectionBean[] getChannelResourceCollections() {
      return this._ChannelResourceCollections;
   }

   public boolean isChannelResourceCollectionsInherited() {
      return false;
   }

   public boolean isChannelResourceCollectionsSet() {
      return this._isSet(0);
   }

   public void removeChannelResourceCollection(ChannelResourceCollectionBean param0) {
      this.destroyChannelResourceCollection(param0);
   }

   public void setChannelResourceCollections(ChannelResourceCollectionBean[] param0) throws InvalidAttributeValueException {
      ChannelResourceCollectionBean[] param0 = param0 == null ? new ChannelResourceCollectionBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ChannelResourceCollectionBean[] _oldVal = this._ChannelResourceCollections;
      this._ChannelResourceCollections = (ChannelResourceCollectionBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public ChannelResourceCollectionBean createChannelResourceCollection() {
      ChannelResourceCollectionBeanImpl _val = new ChannelResourceCollectionBeanImpl(this, -1);

      try {
         this.addChannelResourceCollection(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyChannelResourceCollection(ChannelResourceCollectionBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         ChannelResourceCollectionBean[] _old = this.getChannelResourceCollections();
         ChannelResourceCollectionBean[] _new = (ChannelResourceCollectionBean[])((ChannelResourceCollectionBean[])this._getHelper()._removeElement(_old, ChannelResourceCollectionBean.class, param0));
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
               this.setChannelResourceCollections(_new);
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

   public AuthConstraintBean getAuthConstraint() {
      return this._AuthConstraint;
   }

   public boolean isAuthConstraintInherited() {
      return false;
   }

   public boolean isAuthConstraintSet() {
      return this._isSet(1);
   }

   public void setAuthConstraint(AuthConstraintBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAuthConstraint() != null && param0 != this.getAuthConstraint()) {
         throw new BeanAlreadyExistsException(this.getAuthConstraint() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AuthConstraintBean _oldVal = this._AuthConstraint;
         this._AuthConstraint = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public AuthConstraintBean createAuthConstraint() {
      AuthConstraintBeanImpl _val = new AuthConstraintBeanImpl(this, -1);

      try {
         this.setAuthConstraint(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyAuthConstraint(AuthConstraintBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._AuthConstraint;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setAuthConstraint((AuthConstraintBean)null);
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
               this._AuthConstraint = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ChannelResourceCollections = new ChannelResourceCollectionBean[0];
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
            case 15:
               if (s.equals("auth-constraint")) {
                  return 1;
               }
               break;
            case 27:
               if (s.equals("channel-resource-collection")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new ChannelResourceCollectionBeanImpl.SchemaHelper2();
            case 1:
               return new AuthConstraintBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "channel-resource-collection";
            case 1:
               return "auth-constraint";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
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
      private ChannelConstraintBeanImpl bean;

      protected Helper(ChannelConstraintBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ChannelResourceCollections";
            case 1:
               return "AuthConstraint";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AuthConstraint")) {
            return 1;
         } else {
            return propName.equals("ChannelResourceCollections") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAuthConstraint() != null) {
            iterators.add(new ArrayIterator(new AuthConstraintBean[]{this.bean.getAuthConstraint()}));
         }

         iterators.add(new ArrayIterator(this.bean.getChannelResourceCollections()));
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
            childValue = this.computeChildHashValue(this.bean.getAuthConstraint());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getChannelResourceCollections().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getChannelResourceCollections()[i]);
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
            ChannelConstraintBeanImpl otherTyped = (ChannelConstraintBeanImpl)other;
            this.computeChildDiff("AuthConstraint", this.bean.getAuthConstraint(), otherTyped.getAuthConstraint(), false);
            this.computeChildDiff("ChannelResourceCollections", this.bean.getChannelResourceCollections(), otherTyped.getChannelResourceCollections(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ChannelConstraintBeanImpl original = (ChannelConstraintBeanImpl)event.getSourceBean();
            ChannelConstraintBeanImpl proposed = (ChannelConstraintBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AuthConstraint")) {
                  if (type == 2) {
                     original.setAuthConstraint((AuthConstraintBean)this.createCopy((AbstractDescriptorBean)proposed.getAuthConstraint()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AuthConstraint", (DescriptorBean)original.getAuthConstraint());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ChannelResourceCollections")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addChannelResourceCollection((ChannelResourceCollectionBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeChannelResourceCollection((ChannelResourceCollectionBean)update.getRemovedObject());
                  }

                  if (original.getChannelResourceCollections() == null || original.getChannelResourceCollections().length == 0) {
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
            ChannelConstraintBeanImpl copy = (ChannelConstraintBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AuthConstraint")) && this.bean.isAuthConstraintSet() && !copy._isSet(1)) {
               Object o = this.bean.getAuthConstraint();
               copy.setAuthConstraint((AuthConstraintBean)null);
               copy.setAuthConstraint(o == null ? null : (AuthConstraintBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ChannelResourceCollections")) && this.bean.isChannelResourceCollectionsSet() && !copy._isSet(0)) {
               ChannelResourceCollectionBean[] oldChannelResourceCollections = this.bean.getChannelResourceCollections();
               ChannelResourceCollectionBean[] newChannelResourceCollections = new ChannelResourceCollectionBean[oldChannelResourceCollections.length];

               for(int i = 0; i < newChannelResourceCollections.length; ++i) {
                  newChannelResourceCollections[i] = (ChannelResourceCollectionBean)((ChannelResourceCollectionBean)this.createCopy((AbstractDescriptorBean)oldChannelResourceCollections[i], includeObsolete));
               }

               copy.setChannelResourceCollections(newChannelResourceCollections);
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
         this.inferSubTree(this.bean.getAuthConstraint(), clazz, annotation);
         this.inferSubTree(this.bean.getChannelResourceCollections(), clazz, annotation);
      }
   }
}

package kodo.conf.descriptor;

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

public class AggregateListenersBeanImpl extends AbstractDescriptorBean implements AggregateListenersBean, Serializable {
   private CustomAggregateListenerBean[] _CustomAggregateListeners;
   private static SchemaHelper2 _schemaHelper;

   public AggregateListenersBeanImpl() {
      this._initializeProperty(-1);
   }

   public AggregateListenersBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AggregateListenersBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addCustomAggregateListener(CustomAggregateListenerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         CustomAggregateListenerBean[] _new;
         if (this._isSet(0)) {
            _new = (CustomAggregateListenerBean[])((CustomAggregateListenerBean[])this._getHelper()._extendArray(this.getCustomAggregateListeners(), CustomAggregateListenerBean.class, param0));
         } else {
            _new = new CustomAggregateListenerBean[]{param0};
         }

         try {
            this.setCustomAggregateListeners(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CustomAggregateListenerBean[] getCustomAggregateListeners() {
      return this._CustomAggregateListeners;
   }

   public boolean isCustomAggregateListenersInherited() {
      return false;
   }

   public boolean isCustomAggregateListenersSet() {
      return this._isSet(0);
   }

   public void removeCustomAggregateListener(CustomAggregateListenerBean param0) {
      this.destroyCustomAggregateListener(param0);
   }

   public void setCustomAggregateListeners(CustomAggregateListenerBean[] param0) throws InvalidAttributeValueException {
      CustomAggregateListenerBean[] param0 = param0 == null ? new CustomAggregateListenerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CustomAggregateListenerBean[] _oldVal = this._CustomAggregateListeners;
      this._CustomAggregateListeners = (CustomAggregateListenerBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public CustomAggregateListenerBean createCustomAggregateListener() {
      CustomAggregateListenerBeanImpl _val = new CustomAggregateListenerBeanImpl(this, -1);

      try {
         this.addCustomAggregateListener(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCustomAggregateListener(CustomAggregateListenerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         CustomAggregateListenerBean[] _old = this.getCustomAggregateListeners();
         CustomAggregateListenerBean[] _new = (CustomAggregateListenerBean[])((CustomAggregateListenerBean[])this._getHelper()._removeElement(_old, CustomAggregateListenerBean.class, param0));
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
               this.setCustomAggregateListeners(_new);
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
               this._CustomAggregateListeners = new CustomAggregateListenerBean[0];
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
            case 25:
               if (s.equals("custom-aggregate-listener")) {
                  return 0;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new CustomAggregateListenerBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "custom-aggregate-listener";
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
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AggregateListenersBeanImpl bean;

      protected Helper(AggregateListenersBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "CustomAggregateListeners";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("CustomAggregateListeners") ? 0 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCustomAggregateListeners()));
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

            for(int i = 0; i < this.bean.getCustomAggregateListeners().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCustomAggregateListeners()[i]);
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
            AggregateListenersBeanImpl otherTyped = (AggregateListenersBeanImpl)other;
            this.computeChildDiff("CustomAggregateListeners", this.bean.getCustomAggregateListeners(), otherTyped.getCustomAggregateListeners(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AggregateListenersBeanImpl original = (AggregateListenersBeanImpl)event.getSourceBean();
            AggregateListenersBeanImpl proposed = (AggregateListenersBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CustomAggregateListeners")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCustomAggregateListener((CustomAggregateListenerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCustomAggregateListener((CustomAggregateListenerBean)update.getRemovedObject());
                  }

                  if (original.getCustomAggregateListeners() == null || original.getCustomAggregateListeners().length == 0) {
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
            AggregateListenersBeanImpl copy = (AggregateListenersBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CustomAggregateListeners")) && this.bean.isCustomAggregateListenersSet() && !copy._isSet(0)) {
               CustomAggregateListenerBean[] oldCustomAggregateListeners = this.bean.getCustomAggregateListeners();
               CustomAggregateListenerBean[] newCustomAggregateListeners = new CustomAggregateListenerBean[oldCustomAggregateListeners.length];

               for(int i = 0; i < newCustomAggregateListeners.length; ++i) {
                  newCustomAggregateListeners[i] = (CustomAggregateListenerBean)((CustomAggregateListenerBean)this.createCopy((AbstractDescriptorBean)oldCustomAggregateListeners[i], includeObsolete));
               }

               copy.setCustomAggregateListeners(newCustomAggregateListeners);
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
         this.inferSubTree(this.bean.getCustomAggregateListeners(), clazz, annotation);
      }
   }
}

package weblogic.coherence.descriptor.wl;

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
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceClusterWellKnownAddressesBeanImpl extends SettableBeanImpl implements CoherenceClusterWellKnownAddressesBean, Serializable {
   private CoherenceClusterWellKnownAddressBean[] _CoherenceClusterWellKnownAddresses;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceClusterWellKnownAddressesBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceClusterWellKnownAddressesBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceClusterWellKnownAddressesBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addCoherenceClusterWellKnownAddress(CoherenceClusterWellKnownAddressBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         CoherenceClusterWellKnownAddressBean[] _new;
         if (this._isSet(0)) {
            _new = (CoherenceClusterWellKnownAddressBean[])((CoherenceClusterWellKnownAddressBean[])this._getHelper()._extendArray(this.getCoherenceClusterWellKnownAddresses(), CoherenceClusterWellKnownAddressBean.class, param0));
         } else {
            _new = new CoherenceClusterWellKnownAddressBean[]{param0};
         }

         try {
            this.setCoherenceClusterWellKnownAddresses(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceClusterWellKnownAddressBean[] getCoherenceClusterWellKnownAddresses() {
      return this._CoherenceClusterWellKnownAddresses;
   }

   public boolean isCoherenceClusterWellKnownAddressesInherited() {
      return false;
   }

   public boolean isCoherenceClusterWellKnownAddressesSet() {
      return this._isSet(0);
   }

   public void removeCoherenceClusterWellKnownAddress(CoherenceClusterWellKnownAddressBean param0) {
      this.destroyCoherenceClusterWellKnownAddress(param0);
   }

   public void setCoherenceClusterWellKnownAddresses(CoherenceClusterWellKnownAddressBean[] param0) throws InvalidAttributeValueException {
      CoherenceClusterWellKnownAddressBean[] param0 = param0 == null ? new CoherenceClusterWellKnownAddressBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CoherenceClusterWellKnownAddressBean[] _oldVal = this._CoherenceClusterWellKnownAddresses;
      this._CoherenceClusterWellKnownAddresses = (CoherenceClusterWellKnownAddressBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public CoherenceClusterWellKnownAddressBean createCoherenceClusterWellKnownAddress(String param0) {
      CoherenceClusterWellKnownAddressBeanImpl lookup = (CoherenceClusterWellKnownAddressBeanImpl)this.lookupCoherenceClusterWellKnownAddress(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceClusterWellKnownAddressBeanImpl _val = new CoherenceClusterWellKnownAddressBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceClusterWellKnownAddress(_val);
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

   public CoherenceClusterWellKnownAddressBean lookupCoherenceClusterWellKnownAddress(String param0) {
      Object[] aary = (Object[])this._CoherenceClusterWellKnownAddresses;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceClusterWellKnownAddressBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceClusterWellKnownAddressBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyCoherenceClusterWellKnownAddress(CoherenceClusterWellKnownAddressBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         CoherenceClusterWellKnownAddressBean[] _old = this.getCoherenceClusterWellKnownAddresses();
         CoherenceClusterWellKnownAddressBean[] _new = (CoherenceClusterWellKnownAddressBean[])((CoherenceClusterWellKnownAddressBean[])this._getHelper()._removeElement(_old, CoherenceClusterWellKnownAddressBean.class, param0));
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
               this.setCoherenceClusterWellKnownAddresses(_new);
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
               this._CoherenceClusterWellKnownAddresses = new CoherenceClusterWellKnownAddressBean[0];
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
            case 36:
               if (s.equals("coherence-cluster-well-known-address")) {
                  return 0;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new CoherenceClusterWellKnownAddressBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "coherence-cluster-well-known-address";
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

   protected static class Helper extends SettableBeanImpl.Helper {
      private CoherenceClusterWellKnownAddressesBeanImpl bean;

      protected Helper(CoherenceClusterWellKnownAddressesBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "CoherenceClusterWellKnownAddresses";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("CoherenceClusterWellKnownAddresses") ? 0 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCoherenceClusterWellKnownAddresses()));
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

            for(int i = 0; i < this.bean.getCoherenceClusterWellKnownAddresses().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceClusterWellKnownAddresses()[i]);
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
            CoherenceClusterWellKnownAddressesBeanImpl otherTyped = (CoherenceClusterWellKnownAddressesBeanImpl)other;
            this.computeChildDiff("CoherenceClusterWellKnownAddresses", this.bean.getCoherenceClusterWellKnownAddresses(), otherTyped.getCoherenceClusterWellKnownAddresses(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceClusterWellKnownAddressesBeanImpl original = (CoherenceClusterWellKnownAddressesBeanImpl)event.getSourceBean();
            CoherenceClusterWellKnownAddressesBeanImpl proposed = (CoherenceClusterWellKnownAddressesBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CoherenceClusterWellKnownAddresses")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceClusterWellKnownAddress((CoherenceClusterWellKnownAddressBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherenceClusterWellKnownAddress((CoherenceClusterWellKnownAddressBean)update.getRemovedObject());
                  }

                  if (original.getCoherenceClusterWellKnownAddresses() == null || original.getCoherenceClusterWellKnownAddresses().length == 0) {
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
            CoherenceClusterWellKnownAddressesBeanImpl copy = (CoherenceClusterWellKnownAddressesBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterWellKnownAddresses")) && this.bean.isCoherenceClusterWellKnownAddressesSet() && !copy._isSet(0)) {
               CoherenceClusterWellKnownAddressBean[] oldCoherenceClusterWellKnownAddresses = this.bean.getCoherenceClusterWellKnownAddresses();
               CoherenceClusterWellKnownAddressBean[] newCoherenceClusterWellKnownAddresses = new CoherenceClusterWellKnownAddressBean[oldCoherenceClusterWellKnownAddresses.length];

               for(int i = 0; i < newCoherenceClusterWellKnownAddresses.length; ++i) {
                  newCoherenceClusterWellKnownAddresses[i] = (CoherenceClusterWellKnownAddressBean)((CoherenceClusterWellKnownAddressBean)this.createCopy((AbstractDescriptorBean)oldCoherenceClusterWellKnownAddresses[i], includeObsolete));
               }

               copy.setCoherenceClusterWellKnownAddresses(newCoherenceClusterWellKnownAddresses);
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
         this.inferSubTree(this.bean.getCoherenceClusterWellKnownAddresses(), clazz, annotation);
      }
   }
}

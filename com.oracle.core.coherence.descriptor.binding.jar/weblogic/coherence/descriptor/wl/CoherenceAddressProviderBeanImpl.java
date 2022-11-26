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

public class CoherenceAddressProviderBeanImpl extends SettableBeanImpl implements CoherenceAddressProviderBean, Serializable {
   private CoherenceSocketAddressBean[] _CoherenceSocketAddresses;
   private String _Name;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceAddressProviderBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceAddressProviderBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceAddressProviderBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public void addCoherenceSocketAddress(CoherenceSocketAddressBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         CoherenceSocketAddressBean[] _new;
         if (this._isSet(1)) {
            _new = (CoherenceSocketAddressBean[])((CoherenceSocketAddressBean[])this._getHelper()._extendArray(this.getCoherenceSocketAddresses(), CoherenceSocketAddressBean.class, param0));
         } else {
            _new = new CoherenceSocketAddressBean[]{param0};
         }

         try {
            this.setCoherenceSocketAddresses(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CoherenceSocketAddressBean[] getCoherenceSocketAddresses() {
      return this._CoherenceSocketAddresses;
   }

   public boolean isCoherenceSocketAddressesInherited() {
      return false;
   }

   public boolean isCoherenceSocketAddressesSet() {
      return this._isSet(1);
   }

   public void removeCoherenceSocketAddress(CoherenceSocketAddressBean param0) {
      this.destroyCoherenceSocketAddress(param0);
   }

   public void setCoherenceSocketAddresses(CoherenceSocketAddressBean[] param0) throws InvalidAttributeValueException {
      CoherenceSocketAddressBean[] param0 = param0 == null ? new CoherenceSocketAddressBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CoherenceSocketAddressBean[] _oldVal = this._CoherenceSocketAddresses;
      this._CoherenceSocketAddresses = (CoherenceSocketAddressBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public CoherenceSocketAddressBean createCoherenceSocketAddress(String param0) {
      CoherenceSocketAddressBeanImpl lookup = (CoherenceSocketAddressBeanImpl)this.lookupCoherenceSocketAddress(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         CoherenceSocketAddressBeanImpl _val = new CoherenceSocketAddressBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addCoherenceSocketAddress(_val);
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

   public CoherenceSocketAddressBean lookupCoherenceSocketAddress(String param0) {
      Object[] aary = (Object[])this._CoherenceSocketAddresses;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      CoherenceSocketAddressBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (CoherenceSocketAddressBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public void destroyCoherenceSocketAddress(CoherenceSocketAddressBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         CoherenceSocketAddressBean[] _old = this.getCoherenceSocketAddresses();
         CoherenceSocketAddressBean[] _new = (CoherenceSocketAddressBean[])((CoherenceSocketAddressBean[])this._getHelper()._removeElement(_old, CoherenceSocketAddressBean.class, param0));
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
               this.setCoherenceSocketAddresses(_new);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._CoherenceSocketAddresses = new CoherenceSocketAddressBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
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
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 24:
               if (s.equals("coherence-socket-address")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new CoherenceSocketAddressBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "name";
            case 1:
               return "coherence-socket-address";
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

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
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
      private CoherenceAddressProviderBeanImpl bean;

      protected Helper(CoherenceAddressProviderBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "CoherenceSocketAddresses";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CoherenceSocketAddresses")) {
            return 1;
         } else {
            return propName.equals("Name") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCoherenceSocketAddresses()));
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

            for(int i = 0; i < this.bean.getCoherenceSocketAddresses().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCoherenceSocketAddresses()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
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
            CoherenceAddressProviderBeanImpl otherTyped = (CoherenceAddressProviderBeanImpl)other;
            this.computeChildDiff("CoherenceSocketAddresses", this.bean.getCoherenceSocketAddresses(), otherTyped.getCoherenceSocketAddresses(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceAddressProviderBeanImpl original = (CoherenceAddressProviderBeanImpl)event.getSourceBean();
            CoherenceAddressProviderBeanImpl proposed = (CoherenceAddressProviderBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CoherenceSocketAddresses")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCoherenceSocketAddress((CoherenceSocketAddressBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCoherenceSocketAddress((CoherenceSocketAddressBean)update.getRemovedObject());
                  }

                  if (original.getCoherenceSocketAddresses() == null || original.getCoherenceSocketAddresses().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            CoherenceAddressProviderBeanImpl copy = (CoherenceAddressProviderBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CoherenceSocketAddresses")) && this.bean.isCoherenceSocketAddressesSet() && !copy._isSet(1)) {
               CoherenceSocketAddressBean[] oldCoherenceSocketAddresses = this.bean.getCoherenceSocketAddresses();
               CoherenceSocketAddressBean[] newCoherenceSocketAddresses = new CoherenceSocketAddressBean[oldCoherenceSocketAddresses.length];

               for(int i = 0; i < newCoherenceSocketAddresses.length; ++i) {
                  newCoherenceSocketAddresses[i] = (CoherenceSocketAddressBean)((CoherenceSocketAddressBean)this.createCopy((AbstractDescriptorBean)oldCoherenceSocketAddresses[i], includeObsolete));
               }

               copy.setCoherenceSocketAddresses(newCoherenceSocketAddresses);
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
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
         this.inferSubTree(this.bean.getCoherenceSocketAddresses(), clazz, annotation);
      }
   }
}

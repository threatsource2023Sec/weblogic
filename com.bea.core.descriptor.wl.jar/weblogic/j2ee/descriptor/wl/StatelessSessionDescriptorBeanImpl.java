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

public class StatelessSessionDescriptorBeanImpl extends AbstractDescriptorBean implements StatelessSessionDescriptorBean, Serializable {
   private BusinessInterfaceJndiNameMapBean[] _BusinessInterfaceJndiNameMaps;
   private String _Id;
   private PoolBean _Pool;
   private StatelessClusteringBean _StatelessClustering;
   private TimerDescriptorBean _TimerDescriptor;
   private static SchemaHelper2 _schemaHelper;

   public StatelessSessionDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public StatelessSessionDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public StatelessSessionDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public PoolBean getPool() {
      return this._Pool;
   }

   public boolean isPoolInherited() {
      return false;
   }

   public boolean isPoolSet() {
      return this._isSet(0) || this._isAnythingSet((AbstractDescriptorBean)this.getPool());
   }

   public void setPool(PoolBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 0)) {
         this._postCreate(_child);
      }

      PoolBean _oldVal = this._Pool;
      this._Pool = param0;
      this._postSet(0, _oldVal, param0);
   }

   public TimerDescriptorBean getTimerDescriptor() {
      return this._TimerDescriptor;
   }

   public boolean isTimerDescriptorInherited() {
      return false;
   }

   public boolean isTimerDescriptorSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getTimerDescriptor());
   }

   public void setTimerDescriptor(TimerDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      TimerDescriptorBean _oldVal = this._TimerDescriptor;
      this._TimerDescriptor = param0;
      this._postSet(1, _oldVal, param0);
   }

   public StatelessClusteringBean getStatelessClustering() {
      return this._StatelessClustering;
   }

   public boolean isStatelessClusteringInherited() {
      return false;
   }

   public boolean isStatelessClusteringSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getStatelessClustering());
   }

   public void setStatelessClustering(StatelessClusteringBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      StatelessClusteringBean _oldVal = this._StatelessClustering;
      this._StatelessClustering = param0;
      this._postSet(2, _oldVal, param0);
   }

   public void addBusinessInterfaceJndiNameMap(BusinessInterfaceJndiNameMapBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         BusinessInterfaceJndiNameMapBean[] _new;
         if (this._isSet(3)) {
            _new = (BusinessInterfaceJndiNameMapBean[])((BusinessInterfaceJndiNameMapBean[])this._getHelper()._extendArray(this.getBusinessInterfaceJndiNameMaps(), BusinessInterfaceJndiNameMapBean.class, param0));
         } else {
            _new = new BusinessInterfaceJndiNameMapBean[]{param0};
         }

         try {
            this.setBusinessInterfaceJndiNameMaps(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public BusinessInterfaceJndiNameMapBean[] getBusinessInterfaceJndiNameMaps() {
      return this._BusinessInterfaceJndiNameMaps;
   }

   public boolean isBusinessInterfaceJndiNameMapsInherited() {
      return false;
   }

   public boolean isBusinessInterfaceJndiNameMapsSet() {
      return this._isSet(3);
   }

   public void removeBusinessInterfaceJndiNameMap(BusinessInterfaceJndiNameMapBean param0) {
      this.destroyBusinessInterfaceJndiNameMap(param0);
   }

   public void setBusinessInterfaceJndiNameMaps(BusinessInterfaceJndiNameMapBean[] param0) throws InvalidAttributeValueException {
      BusinessInterfaceJndiNameMapBean[] param0 = param0 == null ? new BusinessInterfaceJndiNameMapBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      BusinessInterfaceJndiNameMapBean[] _oldVal = this._BusinessInterfaceJndiNameMaps;
      this._BusinessInterfaceJndiNameMaps = (BusinessInterfaceJndiNameMapBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public BusinessInterfaceJndiNameMapBean createBusinessInterfaceJndiNameMap() {
      BusinessInterfaceJndiNameMapBeanImpl _val = new BusinessInterfaceJndiNameMapBeanImpl(this, -1);

      try {
         this.addBusinessInterfaceJndiNameMap(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyBusinessInterfaceJndiNameMap(BusinessInterfaceJndiNameMapBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         BusinessInterfaceJndiNameMapBean[] _old = this.getBusinessInterfaceJndiNameMaps();
         BusinessInterfaceJndiNameMapBean[] _new = (BusinessInterfaceJndiNameMapBean[])((BusinessInterfaceJndiNameMapBean[])this._getHelper()._removeElement(_old, BusinessInterfaceJndiNameMapBean.class, param0));
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
               this.setBusinessInterfaceJndiNameMaps(_new);
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

   public BusinessInterfaceJndiNameMapBean lookupBusinessInterfaceJndiNameMap(String param0) {
      Object[] aary = (Object[])this._BusinessInterfaceJndiNameMaps;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      BusinessInterfaceJndiNameMapBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (BusinessInterfaceJndiNameMapBeanImpl)it.previous();
      } while(!bean.getBusinessRemote().equals(param0));

      return bean;
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(4, _oldVal, param0);
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
      return super._isAnythingSet() || this.isPoolSet() || this.isStatelessClusteringSet() || this.isTimerDescriptorSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._BusinessInterfaceJndiNameMaps = new BusinessInterfaceJndiNameMapBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Pool = new PoolBeanImpl(this, 0);
               this._postCreate((AbstractDescriptorBean)this._Pool);
               if (initOne) {
                  break;
               }
            case 2:
               this._StatelessClustering = new StatelessClusteringBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._StatelessClustering);
               if (initOne) {
                  break;
               }
            case 1:
               this._TimerDescriptor = new TimerDescriptorBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._TimerDescriptor);
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
            case 2:
               if (s.equals("id")) {
                  return 4;
               }
               break;
            case 4:
               if (s.equals("pool")) {
                  return 0;
               }
               break;
            case 16:
               if (s.equals("timer-descriptor")) {
                  return 1;
               }
               break;
            case 20:
               if (s.equals("stateless-clustering")) {
                  return 2;
               }
               break;
            case 32:
               if (s.equals("business-interface-jndi-name-map")) {
                  return 3;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new PoolBeanImpl.SchemaHelper2();
            case 1:
               return new TimerDescriptorBeanImpl.SchemaHelper2();
            case 2:
               return new StatelessClusteringBeanImpl.SchemaHelper2();
            case 3:
               return new BusinessInterfaceJndiNameMapBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "pool";
            case 1:
               return "timer-descriptor";
            case 2:
               return "stateless-clustering";
            case 3:
               return "business-interface-jndi-name-map";
            case 4:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
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
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private StatelessSessionDescriptorBeanImpl bean;

      protected Helper(StatelessSessionDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Pool";
            case 1:
               return "TimerDescriptor";
            case 2:
               return "StatelessClustering";
            case 3:
               return "BusinessInterfaceJndiNameMaps";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("BusinessInterfaceJndiNameMaps")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("Pool")) {
            return 0;
         } else if (propName.equals("StatelessClustering")) {
            return 2;
         } else {
            return propName.equals("TimerDescriptor") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getBusinessInterfaceJndiNameMaps()));
         if (this.bean.getPool() != null) {
            iterators.add(new ArrayIterator(new PoolBean[]{this.bean.getPool()}));
         }

         if (this.bean.getStatelessClustering() != null) {
            iterators.add(new ArrayIterator(new StatelessClusteringBean[]{this.bean.getStatelessClustering()}));
         }

         if (this.bean.getTimerDescriptor() != null) {
            iterators.add(new ArrayIterator(new TimerDescriptorBean[]{this.bean.getTimerDescriptor()}));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getBusinessInterfaceJndiNameMaps().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getBusinessInterfaceJndiNameMaps()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getPool());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getStatelessClustering());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTimerDescriptor());
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
            StatelessSessionDescriptorBeanImpl otherTyped = (StatelessSessionDescriptorBeanImpl)other;
            this.computeChildDiff("BusinessInterfaceJndiNameMaps", this.bean.getBusinessInterfaceJndiNameMaps(), otherTyped.getBusinessInterfaceJndiNameMaps(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeSubDiff("Pool", this.bean.getPool(), otherTyped.getPool());
            this.computeSubDiff("StatelessClustering", this.bean.getStatelessClustering(), otherTyped.getStatelessClustering());
            this.computeSubDiff("TimerDescriptor", this.bean.getTimerDescriptor(), otherTyped.getTimerDescriptor());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            StatelessSessionDescriptorBeanImpl original = (StatelessSessionDescriptorBeanImpl)event.getSourceBean();
            StatelessSessionDescriptorBeanImpl proposed = (StatelessSessionDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("BusinessInterfaceJndiNameMaps")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addBusinessInterfaceJndiNameMap((BusinessInterfaceJndiNameMapBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeBusinessInterfaceJndiNameMap((BusinessInterfaceJndiNameMapBean)update.getRemovedObject());
                  }

                  if (original.getBusinessInterfaceJndiNameMaps() == null || original.getBusinessInterfaceJndiNameMaps().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Pool")) {
                  if (type == 2) {
                     original.setPool((PoolBean)this.createCopy((AbstractDescriptorBean)proposed.getPool()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Pool", (DescriptorBean)original.getPool());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("StatelessClustering")) {
                  if (type == 2) {
                     original.setStatelessClustering((StatelessClusteringBean)this.createCopy((AbstractDescriptorBean)proposed.getStatelessClustering()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("StatelessClustering", (DescriptorBean)original.getStatelessClustering());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TimerDescriptor")) {
                  if (type == 2) {
                     original.setTimerDescriptor((TimerDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getTimerDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TimerDescriptor", (DescriptorBean)original.getTimerDescriptor());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            StatelessSessionDescriptorBeanImpl copy = (StatelessSessionDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("BusinessInterfaceJndiNameMaps")) && this.bean.isBusinessInterfaceJndiNameMapsSet() && !copy._isSet(3)) {
               BusinessInterfaceJndiNameMapBean[] oldBusinessInterfaceJndiNameMaps = this.bean.getBusinessInterfaceJndiNameMaps();
               BusinessInterfaceJndiNameMapBean[] newBusinessInterfaceJndiNameMaps = new BusinessInterfaceJndiNameMapBean[oldBusinessInterfaceJndiNameMaps.length];

               for(int i = 0; i < newBusinessInterfaceJndiNameMaps.length; ++i) {
                  newBusinessInterfaceJndiNameMaps[i] = (BusinessInterfaceJndiNameMapBean)((BusinessInterfaceJndiNameMapBean)this.createCopy((AbstractDescriptorBean)oldBusinessInterfaceJndiNameMaps[i], includeObsolete));
               }

               copy.setBusinessInterfaceJndiNameMaps(newBusinessInterfaceJndiNameMaps);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Pool")) && this.bean.isPoolSet() && !copy._isSet(0)) {
               Object o = this.bean.getPool();
               copy.setPool((PoolBean)null);
               copy.setPool(o == null ? null : (PoolBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("StatelessClustering")) && this.bean.isStatelessClusteringSet() && !copy._isSet(2)) {
               Object o = this.bean.getStatelessClustering();
               copy.setStatelessClustering((StatelessClusteringBean)null);
               copy.setStatelessClustering(o == null ? null : (StatelessClusteringBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TimerDescriptor")) && this.bean.isTimerDescriptorSet() && !copy._isSet(1)) {
               Object o = this.bean.getTimerDescriptor();
               copy.setTimerDescriptor((TimerDescriptorBean)null);
               copy.setTimerDescriptor(o == null ? null : (TimerDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getBusinessInterfaceJndiNameMaps(), clazz, annotation);
         this.inferSubTree(this.bean.getPool(), clazz, annotation);
         this.inferSubTree(this.bean.getStatelessClustering(), clazz, annotation);
         this.inferSubTree(this.bean.getTimerDescriptor(), clazz, annotation);
      }
   }
}

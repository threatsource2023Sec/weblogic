package weblogic.diagnostics.image.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WatchImageSourceBeanImpl extends AbstractDescriptorBean implements WatchImageSourceBean, Serializable {
   private WatchStatisticsBean _AggregateWatchStatistics;
   private WatchManagerBean[] _WatchManagers;
   private static SchemaHelper2 _schemaHelper;

   public WatchImageSourceBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WatchImageSourceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WatchImageSourceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WatchStatisticsBean getAggregateWatchStatistics() {
      return this._AggregateWatchStatistics;
   }

   public boolean isAggregateWatchStatisticsInherited() {
      return false;
   }

   public boolean isAggregateWatchStatisticsSet() {
      return this._isSet(0);
   }

   public void setAggregateWatchStatistics(WatchStatisticsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAggregateWatchStatistics() != null && param0 != this.getAggregateWatchStatistics()) {
         throw new BeanAlreadyExistsException(this.getAggregateWatchStatistics() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WatchStatisticsBean _oldVal = this._AggregateWatchStatistics;
         this._AggregateWatchStatistics = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public WatchStatisticsBean createAggregateWatchStatistics() {
      WatchStatisticsBeanImpl _val = new WatchStatisticsBeanImpl(this, -1);

      try {
         this.setAggregateWatchStatistics(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addWatchManager(WatchManagerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         WatchManagerBean[] _new;
         if (this._isSet(1)) {
            _new = (WatchManagerBean[])((WatchManagerBean[])this._getHelper()._extendArray(this.getWatchManagers(), WatchManagerBean.class, param0));
         } else {
            _new = new WatchManagerBean[]{param0};
         }

         try {
            this.setWatchManagers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WatchManagerBean[] getWatchManagers() {
      return this._WatchManagers;
   }

   public boolean isWatchManagersInherited() {
      return false;
   }

   public boolean isWatchManagersSet() {
      return this._isSet(1);
   }

   public void removeWatchManager(WatchManagerBean param0) {
      WatchManagerBean[] _old = this.getWatchManagers();
      WatchManagerBean[] _new = (WatchManagerBean[])((WatchManagerBean[])this._getHelper()._removeElement(_old, WatchManagerBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setWatchManagers(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setWatchManagers(WatchManagerBean[] param0) throws InvalidAttributeValueException {
      WatchManagerBean[] param0 = param0 == null ? new WatchManagerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WatchManagerBean[] _oldVal = this._WatchManagers;
      this._WatchManagers = (WatchManagerBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public WatchManagerBean createWatchManager() {
      WatchManagerBeanImpl _val = new WatchManagerBeanImpl(this, -1);

      try {
         this.addWatchManager(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
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
               this._AggregateWatchStatistics = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WatchManagers = new WatchManagerBean[0];
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
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
               if (s.equals("watch-manager")) {
                  return 1;
               }
               break;
            case 26:
               if (s.equals("aggregate-watch-statistics")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new WatchStatisticsBeanImpl.SchemaHelper2();
            case 1:
               return new WatchManagerBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "watch-image-source";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "aggregate-watch-statistics";
            case 1:
               return "watch-manager";
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
      private WatchImageSourceBeanImpl bean;

      protected Helper(WatchImageSourceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AggregateWatchStatistics";
            case 1:
               return "WatchManagers";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AggregateWatchStatistics")) {
            return 0;
         } else {
            return propName.equals("WatchManagers") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAggregateWatchStatistics() != null) {
            iterators.add(new ArrayIterator(new WatchStatisticsBean[]{this.bean.getAggregateWatchStatistics()}));
         }

         iterators.add(new ArrayIterator(this.bean.getWatchManagers()));
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
            childValue = this.computeChildHashValue(this.bean.getAggregateWatchStatistics());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getWatchManagers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWatchManagers()[i]);
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
            WatchImageSourceBeanImpl otherTyped = (WatchImageSourceBeanImpl)other;
            this.computeChildDiff("AggregateWatchStatistics", this.bean.getAggregateWatchStatistics(), otherTyped.getAggregateWatchStatistics(), false);
            this.computeChildDiff("WatchManagers", this.bean.getWatchManagers(), otherTyped.getWatchManagers(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WatchImageSourceBeanImpl original = (WatchImageSourceBeanImpl)event.getSourceBean();
            WatchImageSourceBeanImpl proposed = (WatchImageSourceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AggregateWatchStatistics")) {
                  if (type == 2) {
                     original.setAggregateWatchStatistics((WatchStatisticsBean)this.createCopy((AbstractDescriptorBean)proposed.getAggregateWatchStatistics()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("AggregateWatchStatistics", (DescriptorBean)original.getAggregateWatchStatistics());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("WatchManagers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWatchManager((WatchManagerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWatchManager((WatchManagerBean)update.getRemovedObject());
                  }

                  if (original.getWatchManagers() == null || original.getWatchManagers().length == 0) {
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
            WatchImageSourceBeanImpl copy = (WatchImageSourceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AggregateWatchStatistics")) && this.bean.isAggregateWatchStatisticsSet() && !copy._isSet(0)) {
               Object o = this.bean.getAggregateWatchStatistics();
               copy.setAggregateWatchStatistics((WatchStatisticsBean)null);
               copy.setAggregateWatchStatistics(o == null ? null : (WatchStatisticsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WatchManagers")) && this.bean.isWatchManagersSet() && !copy._isSet(1)) {
               WatchManagerBean[] oldWatchManagers = this.bean.getWatchManagers();
               WatchManagerBean[] newWatchManagers = new WatchManagerBean[oldWatchManagers.length];

               for(int i = 0; i < newWatchManagers.length; ++i) {
                  newWatchManagers[i] = (WatchManagerBean)((WatchManagerBean)this.createCopy((AbstractDescriptorBean)oldWatchManagers[i], includeObsolete));
               }

               copy.setWatchManagers(newWatchManagers);
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
         this.inferSubTree(this.bean.getAggregateWatchStatistics(), clazz, annotation);
         this.inferSubTree(this.bean.getWatchManagers(), clazz, annotation);
      }
   }
}

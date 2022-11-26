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

public class WatchManagerBeanImpl extends AbstractDescriptorBean implements WatchManagerBean, Serializable {
   private String _ModuleName;
   private WatchAlarmStateBean[] _WatchAlarmStates;
   private WatchManagerStatisticsBean _WatchManagerStatistics;
   private static SchemaHelper2 _schemaHelper;

   public WatchManagerBeanImpl() {
      this._initializeProperty(-1);
   }

   public WatchManagerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WatchManagerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getModuleName() {
      return this._ModuleName;
   }

   public boolean isModuleNameInherited() {
      return false;
   }

   public boolean isModuleNameSet() {
      return this._isSet(0);
   }

   public void setModuleName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ModuleName;
      this._ModuleName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addWatchAlarmState(WatchAlarmStateBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         WatchAlarmStateBean[] _new;
         if (this._isSet(1)) {
            _new = (WatchAlarmStateBean[])((WatchAlarmStateBean[])this._getHelper()._extendArray(this.getWatchAlarmStates(), WatchAlarmStateBean.class, param0));
         } else {
            _new = new WatchAlarmStateBean[]{param0};
         }

         try {
            this.setWatchAlarmStates(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WatchAlarmStateBean[] getWatchAlarmStates() {
      return this._WatchAlarmStates;
   }

   public boolean isWatchAlarmStatesInherited() {
      return false;
   }

   public boolean isWatchAlarmStatesSet() {
      return this._isSet(1);
   }

   public void removeWatchAlarmState(WatchAlarmStateBean param0) {
      WatchAlarmStateBean[] _old = this.getWatchAlarmStates();
      WatchAlarmStateBean[] _new = (WatchAlarmStateBean[])((WatchAlarmStateBean[])this._getHelper()._removeElement(_old, WatchAlarmStateBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setWatchAlarmStates(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setWatchAlarmStates(WatchAlarmStateBean[] param0) throws InvalidAttributeValueException {
      WatchAlarmStateBean[] param0 = param0 == null ? new WatchAlarmStateBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WatchAlarmStateBean[] _oldVal = this._WatchAlarmStates;
      this._WatchAlarmStates = (WatchAlarmStateBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public WatchAlarmStateBean createWatchAlarmState() {
      WatchAlarmStateBeanImpl _val = new WatchAlarmStateBeanImpl(this, -1);

      try {
         this.addWatchAlarmState(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public WatchManagerStatisticsBean getWatchManagerStatistics() {
      return this._WatchManagerStatistics;
   }

   public boolean isWatchManagerStatisticsInherited() {
      return false;
   }

   public boolean isWatchManagerStatisticsSet() {
      return this._isSet(2);
   }

   public void setWatchManagerStatistics(WatchManagerStatisticsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWatchManagerStatistics() != null && param0 != this.getWatchManagerStatistics()) {
         throw new BeanAlreadyExistsException(this.getWatchManagerStatistics() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WatchManagerStatisticsBean _oldVal = this._WatchManagerStatistics;
         this._WatchManagerStatistics = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public WatchManagerStatisticsBean createWatchManagerStatistics() {
      WatchManagerStatisticsBeanImpl _val = new WatchManagerStatisticsBeanImpl(this, -1);

      try {
         this.setWatchManagerStatistics(_val);
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
               this._ModuleName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WatchAlarmStates = new WatchAlarmStateBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._WatchManagerStatistics = null;
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
            case 11:
               if (s.equals("module-name")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("watch-alarm-state")) {
                  return 1;
               }
               break;
            case 24:
               if (s.equals("watch-manager-statistics")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new WatchAlarmStateBeanImpl.SchemaHelper2();
            case 2:
               return new WatchManagerStatisticsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "module-name";
            case 1:
               return "watch-alarm-state";
            case 2:
               return "watch-manager-statistics";
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
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WatchManagerBeanImpl bean;

      protected Helper(WatchManagerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ModuleName";
            case 1:
               return "WatchAlarmStates";
            case 2:
               return "WatchManagerStatistics";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ModuleName")) {
            return 0;
         } else if (propName.equals("WatchAlarmStates")) {
            return 1;
         } else {
            return propName.equals("WatchManagerStatistics") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWatchAlarmStates()));
         if (this.bean.getWatchManagerStatistics() != null) {
            iterators.add(new ArrayIterator(new WatchManagerStatisticsBean[]{this.bean.getWatchManagerStatistics()}));
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
            if (this.bean.isModuleNameSet()) {
               buf.append("ModuleName");
               buf.append(String.valueOf(this.bean.getModuleName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getWatchAlarmStates().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWatchAlarmStates()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWatchManagerStatistics());
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
            WatchManagerBeanImpl otherTyped = (WatchManagerBeanImpl)other;
            this.computeDiff("ModuleName", this.bean.getModuleName(), otherTyped.getModuleName(), false);
            this.computeChildDiff("WatchAlarmStates", this.bean.getWatchAlarmStates(), otherTyped.getWatchAlarmStates(), false);
            this.computeChildDiff("WatchManagerStatistics", this.bean.getWatchManagerStatistics(), otherTyped.getWatchManagerStatistics(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WatchManagerBeanImpl original = (WatchManagerBeanImpl)event.getSourceBean();
            WatchManagerBeanImpl proposed = (WatchManagerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ModuleName")) {
                  original.setModuleName(proposed.getModuleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("WatchAlarmStates")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWatchAlarmState((WatchAlarmStateBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWatchAlarmState((WatchAlarmStateBean)update.getRemovedObject());
                  }

                  if (original.getWatchAlarmStates() == null || original.getWatchAlarmStates().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("WatchManagerStatistics")) {
                  if (type == 2) {
                     original.setWatchManagerStatistics((WatchManagerStatisticsBean)this.createCopy((AbstractDescriptorBean)proposed.getWatchManagerStatistics()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WatchManagerStatistics", (DescriptorBean)original.getWatchManagerStatistics());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            WatchManagerBeanImpl copy = (WatchManagerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ModuleName")) && this.bean.isModuleNameSet()) {
               copy.setModuleName(this.bean.getModuleName());
            }

            if ((excludeProps == null || !excludeProps.contains("WatchAlarmStates")) && this.bean.isWatchAlarmStatesSet() && !copy._isSet(1)) {
               WatchAlarmStateBean[] oldWatchAlarmStates = this.bean.getWatchAlarmStates();
               WatchAlarmStateBean[] newWatchAlarmStates = new WatchAlarmStateBean[oldWatchAlarmStates.length];

               for(int i = 0; i < newWatchAlarmStates.length; ++i) {
                  newWatchAlarmStates[i] = (WatchAlarmStateBean)((WatchAlarmStateBean)this.createCopy((AbstractDescriptorBean)oldWatchAlarmStates[i], includeObsolete));
               }

               copy.setWatchAlarmStates(newWatchAlarmStates);
            }

            if ((excludeProps == null || !excludeProps.contains("WatchManagerStatistics")) && this.bean.isWatchManagerStatisticsSet() && !copy._isSet(2)) {
               Object o = this.bean.getWatchManagerStatistics();
               copy.setWatchManagerStatistics((WatchManagerStatisticsBean)null);
               copy.setWatchManagerStatistics(o == null ? null : (WatchManagerStatisticsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getWatchAlarmStates(), clazz, annotation);
         this.inferSubTree(this.bean.getWatchManagerStatistics(), clazz, annotation);
      }
   }
}

package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SizeParamsBeanImpl extends AbstractDescriptorBean implements SizeParamsBean, Serializable {
   private int _CapacityIncrement;
   private int _HighestNumUnavailable;
   private int _HighestNumWaiters;
   private int _InitialCapacity;
   private int _MaxCapacity;
   private int _ShrinkFrequencySeconds;
   private int _ShrinkPeriodMinutes;
   private boolean _ShrinkingEnabled;
   private static SchemaHelper2 _schemaHelper;

   public SizeParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public SizeParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SizeParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getInitialCapacity() {
      return this._InitialCapacity;
   }

   public boolean isInitialCapacityInherited() {
      return false;
   }

   public boolean isInitialCapacitySet() {
      return this._isSet(0);
   }

   public void setInitialCapacity(int param0) {
      int _oldVal = this._InitialCapacity;
      this._InitialCapacity = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getMaxCapacity() {
      return this._MaxCapacity;
   }

   public boolean isMaxCapacityInherited() {
      return false;
   }

   public boolean isMaxCapacitySet() {
      return this._isSet(1);
   }

   public void setMaxCapacity(int param0) {
      int _oldVal = this._MaxCapacity;
      this._MaxCapacity = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getCapacityIncrement() {
      return this._CapacityIncrement;
   }

   public boolean isCapacityIncrementInherited() {
      return false;
   }

   public boolean isCapacityIncrementSet() {
      return this._isSet(2);
   }

   public void setCapacityIncrement(int param0) {
      int _oldVal = this._CapacityIncrement;
      this._CapacityIncrement = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isShrinkingEnabled() {
      return this._ShrinkingEnabled;
   }

   public boolean isShrinkingEnabledInherited() {
      return false;
   }

   public boolean isShrinkingEnabledSet() {
      return this._isSet(3);
   }

   public void setShrinkingEnabled(boolean param0) {
      boolean _oldVal = this._ShrinkingEnabled;
      this._ShrinkingEnabled = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getShrinkPeriodMinutes() {
      return this._ShrinkPeriodMinutes;
   }

   public boolean isShrinkPeriodMinutesInherited() {
      return false;
   }

   public boolean isShrinkPeriodMinutesSet() {
      return this._isSet(4);
   }

   public void setShrinkPeriodMinutes(int param0) {
      int _oldVal = this._ShrinkPeriodMinutes;
      this._ShrinkPeriodMinutes = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getShrinkFrequencySeconds() {
      return this._ShrinkFrequencySeconds;
   }

   public boolean isShrinkFrequencySecondsInherited() {
      return false;
   }

   public boolean isShrinkFrequencySecondsSet() {
      return this._isSet(5);
   }

   public void setShrinkFrequencySeconds(int param0) {
      int _oldVal = this._ShrinkFrequencySeconds;
      this._ShrinkFrequencySeconds = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getHighestNumWaiters() {
      return this._HighestNumWaiters;
   }

   public boolean isHighestNumWaitersInherited() {
      return false;
   }

   public boolean isHighestNumWaitersSet() {
      return this._isSet(6);
   }

   public void setHighestNumWaiters(int param0) {
      int _oldVal = this._HighestNumWaiters;
      this._HighestNumWaiters = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getHighestNumUnavailable() {
      return this._HighestNumUnavailable;
   }

   public boolean isHighestNumUnavailableInherited() {
      return false;
   }

   public boolean isHighestNumUnavailableSet() {
      return this._isSet(7);
   }

   public void setHighestNumUnavailable(int param0) {
      int _oldVal = this._HighestNumUnavailable;
      this._HighestNumUnavailable = param0;
      this._postSet(7, _oldVal, param0);
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
               this._CapacityIncrement = 0;
               if (initOne) {
                  break;
               }
            case 7:
               this._HighestNumUnavailable = 0;
               if (initOne) {
                  break;
               }
            case 6:
               this._HighestNumWaiters = 0;
               if (initOne) {
                  break;
               }
            case 0:
               this._InitialCapacity = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._MaxCapacity = 0;
               if (initOne) {
                  break;
               }
            case 5:
               this._ShrinkFrequencySeconds = 0;
               if (initOne) {
                  break;
               }
            case 4:
               this._ShrinkPeriodMinutes = 0;
               if (initOne) {
                  break;
               }
            case 3:
               this._ShrinkingEnabled = false;
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
               if (s.equals("max-capacity")) {
                  return 1;
               }
            case 13:
            case 14:
            case 15:
            case 20:
            case 22:
            default:
               break;
            case 16:
               if (s.equals("initial-capacity")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("shrinking-enabled")) {
                  return 3;
               }
               break;
            case 18:
               if (s.equals("capacity-increment")) {
                  return 2;
               }
               break;
            case 19:
               if (s.equals("highest-num-waiters")) {
                  return 6;
               }
               break;
            case 21:
               if (s.equals("shrink-period-minutes")) {
                  return 4;
               }
               break;
            case 23:
               if (s.equals("highest-num-unavailable")) {
                  return 7;
               }
               break;
            case 24:
               if (s.equals("shrink-frequency-seconds")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "initial-capacity";
            case 1:
               return "max-capacity";
            case 2:
               return "capacity-increment";
            case 3:
               return "shrinking-enabled";
            case 4:
               return "shrink-period-minutes";
            case 5:
               return "shrink-frequency-seconds";
            case 6:
               return "highest-num-waiters";
            case 7:
               return "highest-num-unavailable";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SizeParamsBeanImpl bean;

      protected Helper(SizeParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InitialCapacity";
            case 1:
               return "MaxCapacity";
            case 2:
               return "CapacityIncrement";
            case 3:
               return "ShrinkingEnabled";
            case 4:
               return "ShrinkPeriodMinutes";
            case 5:
               return "ShrinkFrequencySeconds";
            case 6:
               return "HighestNumWaiters";
            case 7:
               return "HighestNumUnavailable";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CapacityIncrement")) {
            return 2;
         } else if (propName.equals("HighestNumUnavailable")) {
            return 7;
         } else if (propName.equals("HighestNumWaiters")) {
            return 6;
         } else if (propName.equals("InitialCapacity")) {
            return 0;
         } else if (propName.equals("MaxCapacity")) {
            return 1;
         } else if (propName.equals("ShrinkFrequencySeconds")) {
            return 5;
         } else if (propName.equals("ShrinkPeriodMinutes")) {
            return 4;
         } else {
            return propName.equals("ShrinkingEnabled") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isCapacityIncrementSet()) {
               buf.append("CapacityIncrement");
               buf.append(String.valueOf(this.bean.getCapacityIncrement()));
            }

            if (this.bean.isHighestNumUnavailableSet()) {
               buf.append("HighestNumUnavailable");
               buf.append(String.valueOf(this.bean.getHighestNumUnavailable()));
            }

            if (this.bean.isHighestNumWaitersSet()) {
               buf.append("HighestNumWaiters");
               buf.append(String.valueOf(this.bean.getHighestNumWaiters()));
            }

            if (this.bean.isInitialCapacitySet()) {
               buf.append("InitialCapacity");
               buf.append(String.valueOf(this.bean.getInitialCapacity()));
            }

            if (this.bean.isMaxCapacitySet()) {
               buf.append("MaxCapacity");
               buf.append(String.valueOf(this.bean.getMaxCapacity()));
            }

            if (this.bean.isShrinkFrequencySecondsSet()) {
               buf.append("ShrinkFrequencySeconds");
               buf.append(String.valueOf(this.bean.getShrinkFrequencySeconds()));
            }

            if (this.bean.isShrinkPeriodMinutesSet()) {
               buf.append("ShrinkPeriodMinutes");
               buf.append(String.valueOf(this.bean.getShrinkPeriodMinutes()));
            }

            if (this.bean.isShrinkingEnabledSet()) {
               buf.append("ShrinkingEnabled");
               buf.append(String.valueOf(this.bean.isShrinkingEnabled()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            SizeParamsBeanImpl otherTyped = (SizeParamsBeanImpl)other;
            this.computeDiff("CapacityIncrement", this.bean.getCapacityIncrement(), otherTyped.getCapacityIncrement(), false);
            this.computeDiff("HighestNumUnavailable", this.bean.getHighestNumUnavailable(), otherTyped.getHighestNumUnavailable(), false);
            this.computeDiff("HighestNumWaiters", this.bean.getHighestNumWaiters(), otherTyped.getHighestNumWaiters(), false);
            this.computeDiff("InitialCapacity", this.bean.getInitialCapacity(), otherTyped.getInitialCapacity(), false);
            this.computeDiff("MaxCapacity", this.bean.getMaxCapacity(), otherTyped.getMaxCapacity(), false);
            this.computeDiff("ShrinkFrequencySeconds", this.bean.getShrinkFrequencySeconds(), otherTyped.getShrinkFrequencySeconds(), false);
            this.computeDiff("ShrinkPeriodMinutes", this.bean.getShrinkPeriodMinutes(), otherTyped.getShrinkPeriodMinutes(), false);
            this.computeDiff("ShrinkingEnabled", this.bean.isShrinkingEnabled(), otherTyped.isShrinkingEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SizeParamsBeanImpl original = (SizeParamsBeanImpl)event.getSourceBean();
            SizeParamsBeanImpl proposed = (SizeParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CapacityIncrement")) {
                  original.setCapacityIncrement(proposed.getCapacityIncrement());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("HighestNumUnavailable")) {
                  original.setHighestNumUnavailable(proposed.getHighestNumUnavailable());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("HighestNumWaiters")) {
                  original.setHighestNumWaiters(proposed.getHighestNumWaiters());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("InitialCapacity")) {
                  original.setInitialCapacity(proposed.getInitialCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MaxCapacity")) {
                  original.setMaxCapacity(proposed.getMaxCapacity());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ShrinkFrequencySeconds")) {
                  original.setShrinkFrequencySeconds(proposed.getShrinkFrequencySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ShrinkPeriodMinutes")) {
                  original.setShrinkPeriodMinutes(proposed.getShrinkPeriodMinutes());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ShrinkingEnabled")) {
                  original.setShrinkingEnabled(proposed.isShrinkingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            SizeParamsBeanImpl copy = (SizeParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CapacityIncrement")) && this.bean.isCapacityIncrementSet()) {
               copy.setCapacityIncrement(this.bean.getCapacityIncrement());
            }

            if ((excludeProps == null || !excludeProps.contains("HighestNumUnavailable")) && this.bean.isHighestNumUnavailableSet()) {
               copy.setHighestNumUnavailable(this.bean.getHighestNumUnavailable());
            }

            if ((excludeProps == null || !excludeProps.contains("HighestNumWaiters")) && this.bean.isHighestNumWaitersSet()) {
               copy.setHighestNumWaiters(this.bean.getHighestNumWaiters());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialCapacity")) && this.bean.isInitialCapacitySet()) {
               copy.setInitialCapacity(this.bean.getInitialCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxCapacity")) && this.bean.isMaxCapacitySet()) {
               copy.setMaxCapacity(this.bean.getMaxCapacity());
            }

            if ((excludeProps == null || !excludeProps.contains("ShrinkFrequencySeconds")) && this.bean.isShrinkFrequencySecondsSet()) {
               copy.setShrinkFrequencySeconds(this.bean.getShrinkFrequencySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("ShrinkPeriodMinutes")) && this.bean.isShrinkPeriodMinutesSet()) {
               copy.setShrinkPeriodMinutes(this.bean.getShrinkPeriodMinutes());
            }

            if ((excludeProps == null || !excludeProps.contains("ShrinkingEnabled")) && this.bean.isShrinkingEnabledSet()) {
               copy.setShrinkingEnabled(this.bean.isShrinkingEnabled());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}

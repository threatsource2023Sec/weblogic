package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class FlowControlParamsBeanImpl extends SettableBeanImpl implements FlowControlParamsBean, Serializable {
   private boolean _FlowControlEnabled;
   private int _FlowInterval;
   private int _FlowMaximum;
   private int _FlowMinimum;
   private int _FlowSteps;
   private String _OneWaySendMode;
   private int _OneWaySendWindowSize;
   private static SchemaHelper2 _schemaHelper;

   public FlowControlParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public FlowControlParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public FlowControlParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getFlowMinimum() {
      return this._FlowMinimum;
   }

   public boolean isFlowMinimumInherited() {
      return false;
   }

   public boolean isFlowMinimumSet() {
      return this._isSet(0);
   }

   public void setFlowMinimum(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("FlowMinimum", (long)param0, 1L, 2147483647L);
      int _oldVal = this._FlowMinimum;
      this._FlowMinimum = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getFlowMaximum() {
      return this._FlowMaximum;
   }

   public boolean isFlowMaximumInherited() {
      return false;
   }

   public boolean isFlowMaximumSet() {
      return this._isSet(1);
   }

   public void setFlowMaximum(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("FlowMaximum", (long)param0, 1L, 2147483647L);
      int _oldVal = this._FlowMaximum;
      this._FlowMaximum = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getFlowInterval() {
      return this._FlowInterval;
   }

   public boolean isFlowIntervalInherited() {
      return false;
   }

   public boolean isFlowIntervalSet() {
      return this._isSet(2);
   }

   public void setFlowInterval(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("FlowInterval", (long)param0, 0L, 2147483647L);
      int _oldVal = this._FlowInterval;
      this._FlowInterval = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getFlowSteps() {
      return this._FlowSteps;
   }

   public boolean isFlowStepsInherited() {
      return false;
   }

   public boolean isFlowStepsSet() {
      return this._isSet(3);
   }

   public void setFlowSteps(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("FlowSteps", (long)param0, 1L, 2147483647L);
      int _oldVal = this._FlowSteps;
      this._FlowSteps = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isFlowControlEnabled() {
      return this._FlowControlEnabled;
   }

   public boolean isFlowControlEnabledInherited() {
      return false;
   }

   public boolean isFlowControlEnabledSet() {
      return this._isSet(4);
   }

   public void setFlowControlEnabled(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._FlowControlEnabled;
      this._FlowControlEnabled = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getOneWaySendMode() {
      return this._OneWaySendMode;
   }

   public boolean isOneWaySendModeInherited() {
      return false;
   }

   public boolean isOneWaySendModeSet() {
      return this._isSet(5);
   }

   public void setOneWaySendMode(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._OneWaySendMode;
      this._OneWaySendMode = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getOneWaySendWindowSize() {
      return this._OneWaySendWindowSize;
   }

   public boolean isOneWaySendWindowSizeInherited() {
      return false;
   }

   public boolean isOneWaySendWindowSizeSet() {
      return this._isSet(6);
   }

   public void setOneWaySendWindowSize(int param0) throws IllegalArgumentException {
      LegalChecks.checkInRange("OneWaySendWindowSize", (long)param0, 1L, 2147483647L);
      int _oldVal = this._OneWaySendWindowSize;
      this._OneWaySendWindowSize = param0;
      this._postSet(6, _oldVal, param0);
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
               this._FlowInterval = 60;
               if (initOne) {
                  break;
               }
            case 1:
               this._FlowMaximum = 500;
               if (initOne) {
                  break;
               }
            case 0:
               this._FlowMinimum = 50;
               if (initOne) {
                  break;
               }
            case 3:
               this._FlowSteps = 10;
               if (initOne) {
                  break;
               }
            case 5:
               this._OneWaySendMode = "disabled";
               if (initOne) {
                  break;
               }
            case 6:
               this._OneWaySendWindowSize = 1;
               if (initOne) {
                  break;
               }
            case 4:
               this._FlowControlEnabled = true;
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
            case 10:
               if (s.equals("flow-steps")) {
                  return 3;
               }
            case 11:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            default:
               break;
            case 12:
               if (s.equals("flow-maximum")) {
                  return 1;
               }

               if (s.equals("flow-minimum")) {
                  return 0;
               }
               break;
            case 13:
               if (s.equals("flow-interval")) {
                  return 2;
               }
               break;
            case 17:
               if (s.equals("one-way-send-mode")) {
                  return 5;
               }
               break;
            case 20:
               if (s.equals("flow-control-enabled")) {
                  return 4;
               }
               break;
            case 24:
               if (s.equals("one-way-send-window-size")) {
                  return 6;
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
               return "flow-minimum";
            case 1:
               return "flow-maximum";
            case 2:
               return "flow-interval";
            case 3:
               return "flow-steps";
            case 4:
               return "flow-control-enabled";
            case 5:
               return "one-way-send-mode";
            case 6:
               return "one-way-send-window-size";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private FlowControlParamsBeanImpl bean;

      protected Helper(FlowControlParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "FlowMinimum";
            case 1:
               return "FlowMaximum";
            case 2:
               return "FlowInterval";
            case 3:
               return "FlowSteps";
            case 4:
               return "FlowControlEnabled";
            case 5:
               return "OneWaySendMode";
            case 6:
               return "OneWaySendWindowSize";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FlowInterval")) {
            return 2;
         } else if (propName.equals("FlowMaximum")) {
            return 1;
         } else if (propName.equals("FlowMinimum")) {
            return 0;
         } else if (propName.equals("FlowSteps")) {
            return 3;
         } else if (propName.equals("OneWaySendMode")) {
            return 5;
         } else if (propName.equals("OneWaySendWindowSize")) {
            return 6;
         } else {
            return propName.equals("FlowControlEnabled") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isFlowIntervalSet()) {
               buf.append("FlowInterval");
               buf.append(String.valueOf(this.bean.getFlowInterval()));
            }

            if (this.bean.isFlowMaximumSet()) {
               buf.append("FlowMaximum");
               buf.append(String.valueOf(this.bean.getFlowMaximum()));
            }

            if (this.bean.isFlowMinimumSet()) {
               buf.append("FlowMinimum");
               buf.append(String.valueOf(this.bean.getFlowMinimum()));
            }

            if (this.bean.isFlowStepsSet()) {
               buf.append("FlowSteps");
               buf.append(String.valueOf(this.bean.getFlowSteps()));
            }

            if (this.bean.isOneWaySendModeSet()) {
               buf.append("OneWaySendMode");
               buf.append(String.valueOf(this.bean.getOneWaySendMode()));
            }

            if (this.bean.isOneWaySendWindowSizeSet()) {
               buf.append("OneWaySendWindowSize");
               buf.append(String.valueOf(this.bean.getOneWaySendWindowSize()));
            }

            if (this.bean.isFlowControlEnabledSet()) {
               buf.append("FlowControlEnabled");
               buf.append(String.valueOf(this.bean.isFlowControlEnabled()));
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
            FlowControlParamsBeanImpl otherTyped = (FlowControlParamsBeanImpl)other;
            this.computeDiff("FlowInterval", this.bean.getFlowInterval(), otherTyped.getFlowInterval(), true);
            this.computeDiff("FlowMaximum", this.bean.getFlowMaximum(), otherTyped.getFlowMaximum(), true);
            this.computeDiff("FlowMinimum", this.bean.getFlowMinimum(), otherTyped.getFlowMinimum(), true);
            this.computeDiff("FlowSteps", this.bean.getFlowSteps(), otherTyped.getFlowSteps(), true);
            this.computeDiff("OneWaySendMode", this.bean.getOneWaySendMode(), otherTyped.getOneWaySendMode(), true);
            this.computeDiff("OneWaySendWindowSize", this.bean.getOneWaySendWindowSize(), otherTyped.getOneWaySendWindowSize(), true);
            this.computeDiff("FlowControlEnabled", this.bean.isFlowControlEnabled(), otherTyped.isFlowControlEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FlowControlParamsBeanImpl original = (FlowControlParamsBeanImpl)event.getSourceBean();
            FlowControlParamsBeanImpl proposed = (FlowControlParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FlowInterval")) {
                  original.setFlowInterval(proposed.getFlowInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("FlowMaximum")) {
                  original.setFlowMaximum(proposed.getFlowMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("FlowMinimum")) {
                  original.setFlowMinimum(proposed.getFlowMinimum());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("FlowSteps")) {
                  original.setFlowSteps(proposed.getFlowSteps());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("OneWaySendMode")) {
                  original.setOneWaySendMode(proposed.getOneWaySendMode());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("OneWaySendWindowSize")) {
                  original.setOneWaySendWindowSize(proposed.getOneWaySendWindowSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("FlowControlEnabled")) {
                  original.setFlowControlEnabled(proposed.isFlowControlEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            FlowControlParamsBeanImpl copy = (FlowControlParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FlowInterval")) && this.bean.isFlowIntervalSet()) {
               copy.setFlowInterval(this.bean.getFlowInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("FlowMaximum")) && this.bean.isFlowMaximumSet()) {
               copy.setFlowMaximum(this.bean.getFlowMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("FlowMinimum")) && this.bean.isFlowMinimumSet()) {
               copy.setFlowMinimum(this.bean.getFlowMinimum());
            }

            if ((excludeProps == null || !excludeProps.contains("FlowSteps")) && this.bean.isFlowStepsSet()) {
               copy.setFlowSteps(this.bean.getFlowSteps());
            }

            if ((excludeProps == null || !excludeProps.contains("OneWaySendMode")) && this.bean.isOneWaySendModeSet()) {
               copy.setOneWaySendMode(this.bean.getOneWaySendMode());
            }

            if ((excludeProps == null || !excludeProps.contains("OneWaySendWindowSize")) && this.bean.isOneWaySendWindowSizeSet()) {
               copy.setOneWaySendWindowSize(this.bean.getOneWaySendWindowSize());
            }

            if ((excludeProps == null || !excludeProps.contains("FlowControlEnabled")) && this.bean.isFlowControlEnabledSet()) {
               copy.setFlowControlEnabled(this.bean.isFlowControlEnabled());
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

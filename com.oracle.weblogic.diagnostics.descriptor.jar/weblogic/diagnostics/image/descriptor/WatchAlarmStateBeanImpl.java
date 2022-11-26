package weblogic.diagnostics.image.descriptor;

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

public class WatchAlarmStateBeanImpl extends AbstractDescriptorBean implements WatchAlarmStateBean, Serializable {
   private String _AlarmResetPeriod;
   private String _AlarmResetType;
   private String _WatchName;
   private static SchemaHelper2 _schemaHelper;

   public WatchAlarmStateBeanImpl() {
      this._initializeProperty(-1);
   }

   public WatchAlarmStateBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WatchAlarmStateBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getWatchName() {
      return this._WatchName;
   }

   public boolean isWatchNameInherited() {
      return false;
   }

   public boolean isWatchNameSet() {
      return this._isSet(0);
   }

   public void setWatchName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WatchName;
      this._WatchName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void setAlarmResetType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AlarmResetType;
      this._AlarmResetType = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getAlarmResetType() {
      return this._AlarmResetType;
   }

   public boolean isAlarmResetTypeInherited() {
      return false;
   }

   public boolean isAlarmResetTypeSet() {
      return this._isSet(1);
   }

   public void setAlarmResetPeriod(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AlarmResetPeriod;
      this._AlarmResetPeriod = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getAlarmResetPeriod() {
      return this._AlarmResetPeriod;
   }

   public boolean isAlarmResetPeriodInherited() {
      return false;
   }

   public boolean isAlarmResetPeriodSet() {
      return this._isSet(2);
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
               this._AlarmResetPeriod = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._AlarmResetType = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._WatchName = null;
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
            case 10:
               if (s.equals("watch-name")) {
                  return 0;
               }
               break;
            case 16:
               if (s.equals("alarm-reset-type")) {
                  return 1;
               }
               break;
            case 18:
               if (s.equals("alarm-reset-period")) {
                  return 2;
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
               return "watch-name";
            case 1:
               return "alarm-reset-type";
            case 2:
               return "alarm-reset-period";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private WatchAlarmStateBeanImpl bean;

      protected Helper(WatchAlarmStateBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "WatchName";
            case 1:
               return "AlarmResetType";
            case 2:
               return "AlarmResetPeriod";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AlarmResetPeriod")) {
            return 2;
         } else if (propName.equals("AlarmResetType")) {
            return 1;
         } else {
            return propName.equals("WatchName") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isAlarmResetPeriodSet()) {
               buf.append("AlarmResetPeriod");
               buf.append(String.valueOf(this.bean.getAlarmResetPeriod()));
            }

            if (this.bean.isAlarmResetTypeSet()) {
               buf.append("AlarmResetType");
               buf.append(String.valueOf(this.bean.getAlarmResetType()));
            }

            if (this.bean.isWatchNameSet()) {
               buf.append("WatchName");
               buf.append(String.valueOf(this.bean.getWatchName()));
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
            WatchAlarmStateBeanImpl otherTyped = (WatchAlarmStateBeanImpl)other;
            this.computeDiff("AlarmResetPeriod", this.bean.getAlarmResetPeriod(), otherTyped.getAlarmResetPeriod(), false);
            this.computeDiff("AlarmResetType", this.bean.getAlarmResetType(), otherTyped.getAlarmResetType(), false);
            this.computeDiff("WatchName", this.bean.getWatchName(), otherTyped.getWatchName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WatchAlarmStateBeanImpl original = (WatchAlarmStateBeanImpl)event.getSourceBean();
            WatchAlarmStateBeanImpl proposed = (WatchAlarmStateBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AlarmResetPeriod")) {
                  original.setAlarmResetPeriod(proposed.getAlarmResetPeriod());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("AlarmResetType")) {
                  original.setAlarmResetType(proposed.getAlarmResetType());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("WatchName")) {
                  original.setWatchName(proposed.getWatchName());
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
            WatchAlarmStateBeanImpl copy = (WatchAlarmStateBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AlarmResetPeriod")) && this.bean.isAlarmResetPeriodSet()) {
               copy.setAlarmResetPeriod(this.bean.getAlarmResetPeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("AlarmResetType")) && this.bean.isAlarmResetTypeSet()) {
               copy.setAlarmResetType(this.bean.getAlarmResetType());
            }

            if ((excludeProps == null || !excludeProps.contains("WatchName")) && this.bean.isWatchNameSet()) {
               copy.setWatchName(this.bean.getWatchName());
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

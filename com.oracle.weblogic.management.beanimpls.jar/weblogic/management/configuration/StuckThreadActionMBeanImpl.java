package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class StuckThreadActionMBeanImpl extends ConfigurationMBeanImpl implements StuckThreadActionMBean, Serializable {
   private String _ActionCode;
   private String _ApplicationName;
   private int _MaxStuckThreadsCount;
   private String _ModuleName;
   private String _WorkManagerName;
   private static SchemaHelper2 _schemaHelper;

   public StuckThreadActionMBeanImpl() {
      this._initializeProperty(-1);
   }

   public StuckThreadActionMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public StuckThreadActionMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setWorkManagerName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WorkManagerName;
      this._WorkManagerName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getWorkManagerName() {
      return this._WorkManagerName;
   }

   public boolean isWorkManagerNameInherited() {
      return false;
   }

   public boolean isWorkManagerNameSet() {
      return this._isSet(10);
   }

   public void setModuleName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ModuleName;
      this._ModuleName = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getModuleName() {
      return this._ModuleName;
   }

   public boolean isModuleNameInherited() {
      return false;
   }

   public boolean isModuleNameSet() {
      return this._isSet(11);
   }

   public void setApplicationName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ApplicationName;
      this._ApplicationName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getApplicationName() {
      return this._ApplicationName;
   }

   public boolean isApplicationNameInherited() {
      return false;
   }

   public boolean isApplicationNameSet() {
      return this._isSet(12);
   }

   public void setMaxStuckThreadsCount(int param0) {
      int _oldVal = this._MaxStuckThreadsCount;
      this._MaxStuckThreadsCount = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getMaxStuckThreadsCount() {
      return this._MaxStuckThreadsCount;
   }

   public boolean isMaxStuckThreadsCountInherited() {
      return false;
   }

   public boolean isMaxStuckThreadsCountSet() {
      return this._isSet(13);
   }

   public void setActionCode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ActionCode;
      this._ActionCode = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getActionCode() {
      return this._ActionCode;
   }

   public boolean isActionCodeInherited() {
      return false;
   }

   public boolean isActionCodeSet() {
      return this._isSet(14);
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._ActionCode = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._ApplicationName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._MaxStuckThreadsCount = 0;
               if (initOne) {
                  break;
               }
            case 11:
               this._ModuleName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._WorkManagerName = null;
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
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "StuckThreadAction";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ActionCode")) {
         oldVal = this._ActionCode;
         this._ActionCode = (String)v;
         this._postSet(14, oldVal, this._ActionCode);
      } else if (name.equals("ApplicationName")) {
         oldVal = this._ApplicationName;
         this._ApplicationName = (String)v;
         this._postSet(12, oldVal, this._ApplicationName);
      } else if (name.equals("MaxStuckThreadsCount")) {
         int oldVal = this._MaxStuckThreadsCount;
         this._MaxStuckThreadsCount = (Integer)v;
         this._postSet(13, oldVal, this._MaxStuckThreadsCount);
      } else if (name.equals("ModuleName")) {
         oldVal = this._ModuleName;
         this._ModuleName = (String)v;
         this._postSet(11, oldVal, this._ModuleName);
      } else if (name.equals("WorkManagerName")) {
         oldVal = this._WorkManagerName;
         this._WorkManagerName = (String)v;
         this._postSet(10, oldVal, this._WorkManagerName);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ActionCode")) {
         return this._ActionCode;
      } else if (name.equals("ApplicationName")) {
         return this._ApplicationName;
      } else if (name.equals("MaxStuckThreadsCount")) {
         return new Integer(this._MaxStuckThreadsCount);
      } else if (name.equals("ModuleName")) {
         return this._ModuleName;
      } else {
         return name.equals("WorkManagerName") ? this._WorkManagerName : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("action-code")) {
                  return 14;
               }

               if (s.equals("module-name")) {
                  return 11;
               }
               break;
            case 16:
               if (s.equals("application-name")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("work-manager-name")) {
                  return 10;
               }
               break;
            case 23:
               if (s.equals("max-stuck-threads-count")) {
                  return 13;
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
            case 10:
               return "work-manager-name";
            case 11:
               return "module-name";
            case 12:
               return "application-name";
            case 13:
               return "max-stuck-threads-count";
            case 14:
               return "action-code";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private StuckThreadActionMBeanImpl bean;

      protected Helper(StuckThreadActionMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "WorkManagerName";
            case 11:
               return "ModuleName";
            case 12:
               return "ApplicationName";
            case 13:
               return "MaxStuckThreadsCount";
            case 14:
               return "ActionCode";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActionCode")) {
            return 14;
         } else if (propName.equals("ApplicationName")) {
            return 12;
         } else if (propName.equals("MaxStuckThreadsCount")) {
            return 13;
         } else if (propName.equals("ModuleName")) {
            return 11;
         } else {
            return propName.equals("WorkManagerName") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isActionCodeSet()) {
               buf.append("ActionCode");
               buf.append(String.valueOf(this.bean.getActionCode()));
            }

            if (this.bean.isApplicationNameSet()) {
               buf.append("ApplicationName");
               buf.append(String.valueOf(this.bean.getApplicationName()));
            }

            if (this.bean.isMaxStuckThreadsCountSet()) {
               buf.append("MaxStuckThreadsCount");
               buf.append(String.valueOf(this.bean.getMaxStuckThreadsCount()));
            }

            if (this.bean.isModuleNameSet()) {
               buf.append("ModuleName");
               buf.append(String.valueOf(this.bean.getModuleName()));
            }

            if (this.bean.isWorkManagerNameSet()) {
               buf.append("WorkManagerName");
               buf.append(String.valueOf(this.bean.getWorkManagerName()));
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
            StuckThreadActionMBeanImpl otherTyped = (StuckThreadActionMBeanImpl)other;
            this.computeDiff("ActionCode", this.bean.getActionCode(), otherTyped.getActionCode(), false);
            this.computeDiff("ApplicationName", this.bean.getApplicationName(), otherTyped.getApplicationName(), false);
            this.computeDiff("MaxStuckThreadsCount", this.bean.getMaxStuckThreadsCount(), otherTyped.getMaxStuckThreadsCount(), false);
            this.computeDiff("ModuleName", this.bean.getModuleName(), otherTyped.getModuleName(), false);
            this.computeDiff("WorkManagerName", this.bean.getWorkManagerName(), otherTyped.getWorkManagerName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            StuckThreadActionMBeanImpl original = (StuckThreadActionMBeanImpl)event.getSourceBean();
            StuckThreadActionMBeanImpl proposed = (StuckThreadActionMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ActionCode")) {
                  original.setActionCode(proposed.getActionCode());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ApplicationName")) {
                  original.setApplicationName(proposed.getApplicationName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MaxStuckThreadsCount")) {
                  original.setMaxStuckThreadsCount(proposed.getMaxStuckThreadsCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ModuleName")) {
                  original.setModuleName(proposed.getModuleName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("WorkManagerName")) {
                  original.setWorkManagerName(proposed.getWorkManagerName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            StuckThreadActionMBeanImpl copy = (StuckThreadActionMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ActionCode")) && this.bean.isActionCodeSet()) {
               copy.setActionCode(this.bean.getActionCode());
            }

            if ((excludeProps == null || !excludeProps.contains("ApplicationName")) && this.bean.isApplicationNameSet()) {
               copy.setApplicationName(this.bean.getApplicationName());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxStuckThreadsCount")) && this.bean.isMaxStuckThreadsCountSet()) {
               copy.setMaxStuckThreadsCount(this.bean.getMaxStuckThreadsCount());
            }

            if ((excludeProps == null || !excludeProps.contains("ModuleName")) && this.bean.isModuleNameSet()) {
               copy.setModuleName(this.bean.getModuleName());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManagerName")) && this.bean.isWorkManagerNameSet()) {
               copy.setWorkManagerName(this.bean.getWorkManagerName());
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

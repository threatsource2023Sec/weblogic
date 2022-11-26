package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class TriggerMBeanImpl extends ConfigurationMBeanImpl implements TriggerMBean, Serializable {
   private String _Action;
   private long _Value;
   private static SchemaHelper2 _schemaHelper;

   public TriggerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public TriggerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TriggerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public long getValue() {
      return this._Value;
   }

   public boolean isValueInherited() {
      return false;
   }

   public boolean isValueSet() {
      return this._isSet(10);
   }

   public void setValue(long param0) {
      LegalChecks.checkMin("Value", param0, 1L);
      long _oldVal = this._Value;
      this._Value = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getAction() {
      return this._Action;
   }

   public boolean isActionInherited() {
      return false;
   }

   public boolean isActionSet() {
      return this._isSet(11);
   }

   public void setAction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Action;
      this._Action = param0;
      this._postSet(11, _oldVal, param0);
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._Action = "notify";
               if (initOne) {
                  break;
               }
            case 10:
               this._Value = Long.MAX_VALUE;
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
      return "Trigger";
   }

   public void putValue(String name, Object v) {
      if (name.equals("Action")) {
         String oldVal = this._Action;
         this._Action = (String)v;
         this._postSet(11, oldVal, this._Action);
      } else if (name.equals("Value")) {
         long oldVal = this._Value;
         this._Value = (Long)v;
         this._postSet(10, oldVal, this._Value);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Action")) {
         return this._Action;
      } else {
         return name.equals("Value") ? new Long(this._Value) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 5:
               if (s.equals("value")) {
                  return 10;
               }
               break;
            case 6:
               if (s.equals("action")) {
                  return 11;
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
               return "value";
            case 11:
               return "action";
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
      private TriggerMBeanImpl bean;

      protected Helper(TriggerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Value";
            case 11:
               return "Action";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Action")) {
            return 11;
         } else {
            return propName.equals("Value") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isActionSet()) {
               buf.append("Action");
               buf.append(String.valueOf(this.bean.getAction()));
            }

            if (this.bean.isValueSet()) {
               buf.append("Value");
               buf.append(String.valueOf(this.bean.getValue()));
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
            TriggerMBeanImpl otherTyped = (TriggerMBeanImpl)other;
            this.computeDiff("Action", this.bean.getAction(), otherTyped.getAction(), true);
            this.computeDiff("Value", this.bean.getValue(), otherTyped.getValue(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TriggerMBeanImpl original = (TriggerMBeanImpl)event.getSourceBean();
            TriggerMBeanImpl proposed = (TriggerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Action")) {
                  original.setAction(proposed.getAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Value")) {
                  original.setValue(proposed.getValue());
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
            TriggerMBeanImpl copy = (TriggerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Action")) && this.bean.isActionSet()) {
               copy.setAction(this.bean.getAction());
            }

            if ((excludeProps == null || !excludeProps.contains("Value")) && this.bean.isValueSet()) {
               copy.setValue(this.bean.getValue());
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

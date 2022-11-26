package kodo.conf.descriptor;

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

public class InverseManagerBeanImpl extends AbstractDescriptorBean implements InverseManagerBean, Serializable {
   private String _Action;
   private boolean _ManageLRS;
   private static SchemaHelper2 _schemaHelper;

   public InverseManagerBeanImpl() {
      this._initializeProperty(-1);
   }

   public InverseManagerBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InverseManagerBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getAction() {
      return this._Action;
   }

   public boolean isActionInherited() {
      return false;
   }

   public boolean isActionSet() {
      return this._isSet(0);
   }

   public void setAction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Action;
      this._Action = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean getManageLRS() {
      return this._ManageLRS;
   }

   public boolean isManageLRSInherited() {
      return false;
   }

   public boolean isManageLRSSet() {
      return this._isSet(1);
   }

   public void setManageLRS(boolean param0) {
      boolean _oldVal = this._ManageLRS;
      this._ManageLRS = param0;
      this._postSet(1, _oldVal, param0);
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
               this._Action = "0";
               if (initOne) {
                  break;
               }
            case 1:
               this._ManageLRS = false;
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
            case 6:
               if (s.equals("action")) {
                  return 0;
               }
               break;
            case 9:
               if (s.equals("managelrs")) {
                  return 1;
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
               return "action";
            case 1:
               return "managelrs";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private InverseManagerBeanImpl bean;

      protected Helper(InverseManagerBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Action";
            case 1:
               return "ManageLRS";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Action")) {
            return 0;
         } else {
            return propName.equals("ManageLRS") ? 1 : super.getPropertyIndex(propName);
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

            if (this.bean.isManageLRSSet()) {
               buf.append("ManageLRS");
               buf.append(String.valueOf(this.bean.getManageLRS()));
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
            InverseManagerBeanImpl otherTyped = (InverseManagerBeanImpl)other;
            this.computeDiff("Action", this.bean.getAction(), otherTyped.getAction(), false);
            this.computeDiff("ManageLRS", this.bean.getManageLRS(), otherTyped.getManageLRS(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InverseManagerBeanImpl original = (InverseManagerBeanImpl)event.getSourceBean();
            InverseManagerBeanImpl proposed = (InverseManagerBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Action")) {
                  original.setAction(proposed.getAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ManageLRS")) {
                  original.setManageLRS(proposed.getManageLRS());
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
            InverseManagerBeanImpl copy = (InverseManagerBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Action")) && this.bean.isActionSet()) {
               copy.setAction(this.bean.getAction());
            }

            if ((excludeProps == null || !excludeProps.contains("ManageLRS")) && this.bean.isManageLRSSet()) {
               copy.setManageLRS(this.bean.getManageLRS());
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

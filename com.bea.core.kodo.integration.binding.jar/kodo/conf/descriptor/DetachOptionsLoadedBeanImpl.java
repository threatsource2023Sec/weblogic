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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class DetachOptionsLoadedBeanImpl extends DetachStateBeanImpl implements DetachOptionsLoadedBean, Serializable {
   private boolean _AccessUnloaded;
   private boolean _DetachedStateField;
   private boolean _DetachedStateManager;
   private boolean _DetachedStateTransient;
   private static SchemaHelper2 _schemaHelper;

   public DetachOptionsLoadedBeanImpl() {
      this._initializeProperty(-1);
   }

   public DetachOptionsLoadedBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public DetachOptionsLoadedBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean getDetachedStateManager() {
      return this._DetachedStateManager;
   }

   public boolean isDetachedStateManagerInherited() {
      return false;
   }

   public boolean isDetachedStateManagerSet() {
      return this._isSet(0);
   }

   public void setDetachedStateManager(boolean param0) {
      boolean _oldVal = this._DetachedStateManager;
      this._DetachedStateManager = param0;
      this._postSet(0, _oldVal, param0);
   }

   public boolean getDetachedStateTransient() {
      return this._DetachedStateTransient;
   }

   public boolean isDetachedStateTransientInherited() {
      return false;
   }

   public boolean isDetachedStateTransientSet() {
      return this._isSet(1);
   }

   public void setDetachedStateTransient(boolean param0) {
      boolean _oldVal = this._DetachedStateTransient;
      this._DetachedStateTransient = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean getAccessUnloaded() {
      return this._AccessUnloaded;
   }

   public boolean isAccessUnloadedInherited() {
      return false;
   }

   public boolean isAccessUnloadedSet() {
      return this._isSet(2);
   }

   public void setAccessUnloaded(boolean param0) {
      boolean _oldVal = this._AccessUnloaded;
      this._AccessUnloaded = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean getDetachedStateField() {
      return this._DetachedStateField;
   }

   public boolean isDetachedStateFieldInherited() {
      return false;
   }

   public boolean isDetachedStateFieldSet() {
      return this._isSet(3);
   }

   public void setDetachedStateField(boolean param0) {
      boolean _oldVal = this._DetachedStateField;
      this._DetachedStateField = param0;
      this._postSet(3, _oldVal, param0);
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
               this._AccessUnloaded = true;
               if (initOne) {
                  break;
               }
            case 3:
               this._DetachedStateField = true;
               if (initOne) {
                  break;
               }
            case 0:
               this._DetachedStateManager = true;
               if (initOne) {
                  break;
               }
            case 1:
               this._DetachedStateTransient = false;
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

   public static class SchemaHelper2 extends DetachStateBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 15:
               if (s.equals("access-unloaded")) {
                  return 2;
               }
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 23:
            default:
               break;
            case 20:
               if (s.equals("detached-state-field")) {
                  return 3;
               }
               break;
            case 22:
               if (s.equals("detached-state-manager")) {
                  return 0;
               }
               break;
            case 24:
               if (s.equals("detached-state-transient")) {
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
               return "detached-state-manager";
            case 1:
               return "detached-state-transient";
            case 2:
               return "access-unloaded";
            case 3:
               return "detached-state-field";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends DetachStateBeanImpl.Helper {
      private DetachOptionsLoadedBeanImpl bean;

      protected Helper(DetachOptionsLoadedBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "DetachedStateManager";
            case 1:
               return "DetachedStateTransient";
            case 2:
               return "AccessUnloaded";
            case 3:
               return "DetachedStateField";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AccessUnloaded")) {
            return 2;
         } else if (propName.equals("DetachedStateField")) {
            return 3;
         } else if (propName.equals("DetachedStateManager")) {
            return 0;
         } else {
            return propName.equals("DetachedStateTransient") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isAccessUnloadedSet()) {
               buf.append("AccessUnloaded");
               buf.append(String.valueOf(this.bean.getAccessUnloaded()));
            }

            if (this.bean.isDetachedStateFieldSet()) {
               buf.append("DetachedStateField");
               buf.append(String.valueOf(this.bean.getDetachedStateField()));
            }

            if (this.bean.isDetachedStateManagerSet()) {
               buf.append("DetachedStateManager");
               buf.append(String.valueOf(this.bean.getDetachedStateManager()));
            }

            if (this.bean.isDetachedStateTransientSet()) {
               buf.append("DetachedStateTransient");
               buf.append(String.valueOf(this.bean.getDetachedStateTransient()));
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
            DetachOptionsLoadedBeanImpl otherTyped = (DetachOptionsLoadedBeanImpl)other;
            this.computeDiff("AccessUnloaded", this.bean.getAccessUnloaded(), otherTyped.getAccessUnloaded(), false);
            this.computeDiff("DetachedStateField", this.bean.getDetachedStateField(), otherTyped.getDetachedStateField(), false);
            this.computeDiff("DetachedStateManager", this.bean.getDetachedStateManager(), otherTyped.getDetachedStateManager(), false);
            this.computeDiff("DetachedStateTransient", this.bean.getDetachedStateTransient(), otherTyped.getDetachedStateTransient(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            DetachOptionsLoadedBeanImpl original = (DetachOptionsLoadedBeanImpl)event.getSourceBean();
            DetachOptionsLoadedBeanImpl proposed = (DetachOptionsLoadedBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AccessUnloaded")) {
                  original.setAccessUnloaded(proposed.getAccessUnloaded());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("DetachedStateField")) {
                  original.setDetachedStateField(proposed.getDetachedStateField());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("DetachedStateManager")) {
                  original.setDetachedStateManager(proposed.getDetachedStateManager());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("DetachedStateTransient")) {
                  original.setDetachedStateTransient(proposed.getDetachedStateTransient());
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
            DetachOptionsLoadedBeanImpl copy = (DetachOptionsLoadedBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AccessUnloaded")) && this.bean.isAccessUnloadedSet()) {
               copy.setAccessUnloaded(this.bean.getAccessUnloaded());
            }

            if ((excludeProps == null || !excludeProps.contains("DetachedStateField")) && this.bean.isDetachedStateFieldSet()) {
               copy.setDetachedStateField(this.bean.getDetachedStateField());
            }

            if ((excludeProps == null || !excludeProps.contains("DetachedStateManager")) && this.bean.isDetachedStateManagerSet()) {
               copy.setDetachedStateManager(this.bean.getDetachedStateManager());
            }

            if ((excludeProps == null || !excludeProps.contains("DetachedStateTransient")) && this.bean.isDetachedStateTransientSet()) {
               copy.setDetachedStateTransient(this.bean.getDetachedStateTransient());
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

package weblogic.j2ee.descriptor.wl;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class FastSwapBeanImpl extends AbstractDescriptorBean implements FastSwapBean, Serializable {
   private boolean _Enabled;
   private int _RedefinitionTaskLimit;
   private int _RefreshInterval;
   private static SchemaHelper2 _schemaHelper;

   public FastSwapBeanImpl() {
      this._initializeProperty(-1);
   }

   public FastSwapBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public FastSwapBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(0);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getRefreshInterval() {
      return this._RefreshInterval;
   }

   public boolean isRefreshIntervalInherited() {
      return false;
   }

   public boolean isRefreshIntervalSet() {
      return this._isSet(1);
   }

   public void setRefreshInterval(int param0) {
      int _oldVal = this._RefreshInterval;
      this._RefreshInterval = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getRedefinitionTaskLimit() {
      return this._RedefinitionTaskLimit;
   }

   public boolean isRedefinitionTaskLimitInherited() {
      return false;
   }

   public boolean isRedefinitionTaskLimitSet() {
      return this._isSet(2);
   }

   public void setRedefinitionTaskLimit(int param0) {
      LegalChecks.checkMin("RedefinitionTaskLimit", param0, 1);
      int _oldVal = this._RedefinitionTaskLimit;
      this._RedefinitionTaskLimit = param0;
      this._postSet(2, _oldVal, param0);
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
               this._RedefinitionTaskLimit = 10;
               if (initOne) {
                  break;
               }
            case 1:
               this._RefreshInterval = 3;
               if (initOne) {
                  break;
               }
            case 0:
               this._Enabled = true;
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
            case 7:
               if (s.equals("enabled")) {
                  return 0;
               }
               break;
            case 16:
               if (s.equals("refresh-interval")) {
                  return 1;
               }
               break;
            case 23:
               if (s.equals("redefinition-task-limit")) {
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
               return "enabled";
            case 1:
               return "refresh-interval";
            case 2:
               return "redefinition-task-limit";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private FastSwapBeanImpl bean;

      protected Helper(FastSwapBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Enabled";
            case 1:
               return "RefreshInterval";
            case 2:
               return "RedefinitionTaskLimit";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("RedefinitionTaskLimit")) {
            return 2;
         } else if (propName.equals("RefreshInterval")) {
            return 1;
         } else {
            return propName.equals("Enabled") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isRedefinitionTaskLimitSet()) {
               buf.append("RedefinitionTaskLimit");
               buf.append(String.valueOf(this.bean.getRedefinitionTaskLimit()));
            }

            if (this.bean.isRefreshIntervalSet()) {
               buf.append("RefreshInterval");
               buf.append(String.valueOf(this.bean.getRefreshInterval()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            FastSwapBeanImpl otherTyped = (FastSwapBeanImpl)other;
            this.computeDiff("RedefinitionTaskLimit", this.bean.getRedefinitionTaskLimit(), otherTyped.getRedefinitionTaskLimit(), false);
            this.computeDiff("RefreshInterval", this.bean.getRefreshInterval(), otherTyped.getRefreshInterval(), false);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            FastSwapBeanImpl original = (FastSwapBeanImpl)event.getSourceBean();
            FastSwapBeanImpl proposed = (FastSwapBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("RedefinitionTaskLimit")) {
                  original.setRedefinitionTaskLimit(proposed.getRedefinitionTaskLimit());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RefreshInterval")) {
                  original.setRefreshInterval(proposed.getRefreshInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
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
            FastSwapBeanImpl copy = (FastSwapBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("RedefinitionTaskLimit")) && this.bean.isRedefinitionTaskLimitSet()) {
               copy.setRedefinitionTaskLimit(this.bean.getRedefinitionTaskLimit());
            }

            if ((excludeProps == null || !excludeProps.contains("RefreshInterval")) && this.bean.isRefreshIntervalSet()) {
               copy.setRefreshInterval(this.bean.getRefreshInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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

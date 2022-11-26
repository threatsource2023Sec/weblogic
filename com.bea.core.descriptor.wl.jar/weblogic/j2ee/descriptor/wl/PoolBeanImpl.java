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

public class PoolBeanImpl extends AbstractDescriptorBean implements PoolBean, Serializable {
   private String _Id;
   private int _IdleTimeoutSeconds;
   private int _InitialBeansInFreePool;
   private int _MaxBeansInFreePool;
   private static SchemaHelper2 _schemaHelper;

   public PoolBeanImpl() {
      this._initializeProperty(-1);
   }

   public PoolBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PoolBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxBeansInFreePool() {
      return this._MaxBeansInFreePool;
   }

   public boolean isMaxBeansInFreePoolInherited() {
      return false;
   }

   public boolean isMaxBeansInFreePoolSet() {
      return this._isSet(0);
   }

   public void setMaxBeansInFreePool(int param0) {
      LegalChecks.checkMin("MaxBeansInFreePool", param0, 0);
      int _oldVal = this._MaxBeansInFreePool;
      this._MaxBeansInFreePool = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getInitialBeansInFreePool() {
      return this._InitialBeansInFreePool;
   }

   public boolean isInitialBeansInFreePoolInherited() {
      return false;
   }

   public boolean isInitialBeansInFreePoolSet() {
      return this._isSet(1);
   }

   public void setInitialBeansInFreePool(int param0) {
      LegalChecks.checkMin("InitialBeansInFreePool", param0, 0);
      int _oldVal = this._InitialBeansInFreePool;
      this._InitialBeansInFreePool = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getIdleTimeoutSeconds() {
      return this._IdleTimeoutSeconds;
   }

   public boolean isIdleTimeoutSecondsInherited() {
      return false;
   }

   public boolean isIdleTimeoutSecondsSet() {
      return this._isSet(2);
   }

   public void setIdleTimeoutSeconds(int param0) {
      LegalChecks.checkMin("IdleTimeoutSeconds", param0, 0);
      int _oldVal = this._IdleTimeoutSeconds;
      this._IdleTimeoutSeconds = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._IdleTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._InitialBeansInFreePool = 0;
               if (initOne) {
                  break;
               }
            case 0:
               this._MaxBeansInFreePool = 1000;
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
            case 2:
               if (s.equals("id")) {
                  return 3;
               }
               break;
            case 20:
               if (s.equals("idle-timeout-seconds")) {
                  return 2;
               }
               break;
            case 22:
               if (s.equals("max-beans-in-free-pool")) {
                  return 0;
               }
               break;
            case 26:
               if (s.equals("initial-beans-in-free-pool")) {
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
               return "max-beans-in-free-pool";
            case 1:
               return "initial-beans-in-free-pool";
            case 2:
               return "idle-timeout-seconds";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PoolBeanImpl bean;

      protected Helper(PoolBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MaxBeansInFreePool";
            case 1:
               return "InitialBeansInFreePool";
            case 2:
               return "IdleTimeoutSeconds";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("IdleTimeoutSeconds")) {
            return 2;
         } else if (propName.equals("InitialBeansInFreePool")) {
            return 1;
         } else {
            return propName.equals("MaxBeansInFreePool") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isIdleTimeoutSecondsSet()) {
               buf.append("IdleTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getIdleTimeoutSeconds()));
            }

            if (this.bean.isInitialBeansInFreePoolSet()) {
               buf.append("InitialBeansInFreePool");
               buf.append(String.valueOf(this.bean.getInitialBeansInFreePool()));
            }

            if (this.bean.isMaxBeansInFreePoolSet()) {
               buf.append("MaxBeansInFreePool");
               buf.append(String.valueOf(this.bean.getMaxBeansInFreePool()));
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
            PoolBeanImpl otherTyped = (PoolBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("IdleTimeoutSeconds", this.bean.getIdleTimeoutSeconds(), otherTyped.getIdleTimeoutSeconds(), true);
            this.computeDiff("InitialBeansInFreePool", this.bean.getInitialBeansInFreePool(), otherTyped.getInitialBeansInFreePool(), false);
            this.computeDiff("MaxBeansInFreePool", this.bean.getMaxBeansInFreePool(), otherTyped.getMaxBeansInFreePool(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PoolBeanImpl original = (PoolBeanImpl)event.getSourceBean();
            PoolBeanImpl proposed = (PoolBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("IdleTimeoutSeconds")) {
                  original.setIdleTimeoutSeconds(proposed.getIdleTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("InitialBeansInFreePool")) {
                  original.setInitialBeansInFreePool(proposed.getInitialBeansInFreePool());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MaxBeansInFreePool")) {
                  original.setMaxBeansInFreePool(proposed.getMaxBeansInFreePool());
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
            PoolBeanImpl copy = (PoolBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("IdleTimeoutSeconds")) && this.bean.isIdleTimeoutSecondsSet()) {
               copy.setIdleTimeoutSeconds(this.bean.getIdleTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialBeansInFreePool")) && this.bean.isInitialBeansInFreePoolSet()) {
               copy.setInitialBeansInFreePool(this.bean.getInitialBeansInFreePool());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxBeansInFreePool")) && this.bean.isMaxBeansInFreePoolSet()) {
               copy.setMaxBeansInFreePool(this.bean.getMaxBeansInFreePool());
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

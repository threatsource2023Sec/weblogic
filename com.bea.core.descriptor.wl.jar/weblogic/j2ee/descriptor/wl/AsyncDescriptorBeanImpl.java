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

public class AsyncDescriptorBeanImpl extends AbstractDescriptorBean implements AsyncDescriptorBean, Serializable {
   private String _AsyncWorkManager;
   private int _TimeoutCheckIntervalSecs;
   private int _TimeoutSecs;
   private static SchemaHelper2 _schemaHelper;

   public AsyncDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public AsyncDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AsyncDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getTimeoutSecs() {
      return this._TimeoutSecs;
   }

   public boolean isTimeoutSecsInherited() {
      return false;
   }

   public boolean isTimeoutSecsSet() {
      return this._isSet(0);
   }

   public void setTimeoutSecs(int param0) {
      int _oldVal = this._TimeoutSecs;
      this._TimeoutSecs = param0;
      this._postSet(0, _oldVal, param0);
   }

   public int getTimeoutCheckIntervalSecs() {
      return this._TimeoutCheckIntervalSecs;
   }

   public boolean isTimeoutCheckIntervalSecsInherited() {
      return false;
   }

   public boolean isTimeoutCheckIntervalSecsSet() {
      return this._isSet(1);
   }

   public void setTimeoutCheckIntervalSecs(int param0) {
      int _oldVal = this._TimeoutCheckIntervalSecs;
      this._TimeoutCheckIntervalSecs = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getAsyncWorkManager() {
      return this._AsyncWorkManager;
   }

   public boolean isAsyncWorkManagerInherited() {
      return false;
   }

   public boolean isAsyncWorkManagerSet() {
      return this._isSet(2);
   }

   public void setAsyncWorkManager(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AsyncWorkManager;
      this._AsyncWorkManager = param0;
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
               this._AsyncWorkManager = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._TimeoutCheckIntervalSecs = 10;
               if (initOne) {
                  break;
               }
            case 0:
               this._TimeoutSecs = 30;
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
               if (s.equals("timeout-secs")) {
                  return 0;
               }
               break;
            case 18:
               if (s.equals("async-work-manager")) {
                  return 2;
               }
               break;
            case 27:
               if (s.equals("timeout-check-interval-secs")) {
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
               return "timeout-secs";
            case 1:
               return "timeout-check-interval-secs";
            case 2:
               return "async-work-manager";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AsyncDescriptorBeanImpl bean;

      protected Helper(AsyncDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TimeoutSecs";
            case 1:
               return "TimeoutCheckIntervalSecs";
            case 2:
               return "AsyncWorkManager";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AsyncWorkManager")) {
            return 2;
         } else if (propName.equals("TimeoutCheckIntervalSecs")) {
            return 1;
         } else {
            return propName.equals("TimeoutSecs") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isAsyncWorkManagerSet()) {
               buf.append("AsyncWorkManager");
               buf.append(String.valueOf(this.bean.getAsyncWorkManager()));
            }

            if (this.bean.isTimeoutCheckIntervalSecsSet()) {
               buf.append("TimeoutCheckIntervalSecs");
               buf.append(String.valueOf(this.bean.getTimeoutCheckIntervalSecs()));
            }

            if (this.bean.isTimeoutSecsSet()) {
               buf.append("TimeoutSecs");
               buf.append(String.valueOf(this.bean.getTimeoutSecs()));
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
            AsyncDescriptorBeanImpl otherTyped = (AsyncDescriptorBeanImpl)other;
            this.computeDiff("AsyncWorkManager", this.bean.getAsyncWorkManager(), otherTyped.getAsyncWorkManager(), false);
            this.computeDiff("TimeoutCheckIntervalSecs", this.bean.getTimeoutCheckIntervalSecs(), otherTyped.getTimeoutCheckIntervalSecs(), false);
            this.computeDiff("TimeoutSecs", this.bean.getTimeoutSecs(), otherTyped.getTimeoutSecs(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AsyncDescriptorBeanImpl original = (AsyncDescriptorBeanImpl)event.getSourceBean();
            AsyncDescriptorBeanImpl proposed = (AsyncDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AsyncWorkManager")) {
                  original.setAsyncWorkManager(proposed.getAsyncWorkManager());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TimeoutCheckIntervalSecs")) {
                  original.setTimeoutCheckIntervalSecs(proposed.getTimeoutCheckIntervalSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("TimeoutSecs")) {
                  original.setTimeoutSecs(proposed.getTimeoutSecs());
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
            AsyncDescriptorBeanImpl copy = (AsyncDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AsyncWorkManager")) && this.bean.isAsyncWorkManagerSet()) {
               copy.setAsyncWorkManager(this.bean.getAsyncWorkManager());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeoutCheckIntervalSecs")) && this.bean.isTimeoutCheckIntervalSecsSet()) {
               copy.setTimeoutCheckIntervalSecs(this.bean.getTimeoutCheckIntervalSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeoutSecs")) && this.bean.isTimeoutSecsSet()) {
               copy.setTimeoutSecs(this.bean.getTimeoutSecs());
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

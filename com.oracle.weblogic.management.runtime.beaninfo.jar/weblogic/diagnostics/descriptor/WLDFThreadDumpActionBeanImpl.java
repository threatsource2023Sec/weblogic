package weblogic.diagnostics.descriptor;

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
import weblogic.diagnostics.descriptor.validation.WatchNotificationValidators;
import weblogic.utils.collections.CombinedIterator;

public class WLDFThreadDumpActionBeanImpl extends WLDFNotificationBeanImpl implements WLDFThreadDumpActionBean, Serializable {
   private int _ThreadDumpCount;
   private int _ThreadDumpDelaySeconds;
   private static SchemaHelper2 _schemaHelper;

   public WLDFThreadDumpActionBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFThreadDumpActionBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFThreadDumpActionBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getThreadDumpCount() {
      return this._ThreadDumpCount;
   }

   public boolean isThreadDumpCountInherited() {
      return false;
   }

   public boolean isThreadDumpCountSet() {
      return this._isSet(4);
   }

   public void setThreadDumpCount(int param0) {
      LegalChecks.checkInRange("ThreadDumpCount", (long)param0, 1L, 20L);
      int _oldVal = this._ThreadDumpCount;
      this._ThreadDumpCount = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getThreadDumpDelaySeconds() {
      return this._ThreadDumpDelaySeconds;
   }

   public boolean isThreadDumpDelaySecondsInherited() {
      return false;
   }

   public boolean isThreadDumpDelaySecondsSet() {
      return this._isSet(5);
   }

   public void setThreadDumpDelaySeconds(int param0) {
      LegalChecks.checkMin("ThreadDumpDelaySeconds", param0, 1);
      int _oldVal = this._ThreadDumpDelaySeconds;
      this._ThreadDumpDelaySeconds = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      WatchNotificationValidators.validateTheadDumpAction(this);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._ThreadDumpCount = 3;
               if (initOne) {
                  break;
               }
            case 5:
               this._ThreadDumpDelaySeconds = 10;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFNotificationBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 17:
               if (s.equals("thread-dump-count")) {
                  return 4;
               }
               break;
            case 25:
               if (s.equals("thread-dump-delay-seconds")) {
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
            case 4:
               return "thread-dump-count";
            case 5:
               return "thread-dump-delay-seconds";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends WLDFNotificationBeanImpl.Helper {
      private WLDFThreadDumpActionBeanImpl bean;

      protected Helper(WLDFThreadDumpActionBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 4:
               return "ThreadDumpCount";
            case 5:
               return "ThreadDumpDelaySeconds";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ThreadDumpCount")) {
            return 4;
         } else {
            return propName.equals("ThreadDumpDelaySeconds") ? 5 : super.getPropertyIndex(propName);
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
            if (this.bean.isThreadDumpCountSet()) {
               buf.append("ThreadDumpCount");
               buf.append(String.valueOf(this.bean.getThreadDumpCount()));
            }

            if (this.bean.isThreadDumpDelaySecondsSet()) {
               buf.append("ThreadDumpDelaySeconds");
               buf.append(String.valueOf(this.bean.getThreadDumpDelaySeconds()));
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
            WLDFThreadDumpActionBeanImpl otherTyped = (WLDFThreadDumpActionBeanImpl)other;
            this.computeDiff("ThreadDumpCount", this.bean.getThreadDumpCount(), otherTyped.getThreadDumpCount(), true);
            this.computeDiff("ThreadDumpDelaySeconds", this.bean.getThreadDumpDelaySeconds(), otherTyped.getThreadDumpDelaySeconds(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFThreadDumpActionBeanImpl original = (WLDFThreadDumpActionBeanImpl)event.getSourceBean();
            WLDFThreadDumpActionBeanImpl proposed = (WLDFThreadDumpActionBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ThreadDumpCount")) {
                  original.setThreadDumpCount(proposed.getThreadDumpCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ThreadDumpDelaySeconds")) {
                  original.setThreadDumpDelaySeconds(proposed.getThreadDumpDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            WLDFThreadDumpActionBeanImpl copy = (WLDFThreadDumpActionBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ThreadDumpCount")) && this.bean.isThreadDumpCountSet()) {
               copy.setThreadDumpCount(this.bean.getThreadDumpCount());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadDumpDelaySeconds")) && this.bean.isThreadDumpDelaySecondsSet()) {
               copy.setThreadDumpDelaySeconds(this.bean.getThreadDumpDelaySeconds());
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

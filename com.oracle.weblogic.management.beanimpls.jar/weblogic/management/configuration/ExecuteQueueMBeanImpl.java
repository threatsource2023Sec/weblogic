package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ExecuteQueueMBeanImpl extends ConfigurationMBeanImpl implements ExecuteQueueMBean, Serializable {
   private int _QueueLength;
   private int _QueueLengthThresholdPercent;
   private int _ThreadCount;
   private int _ThreadPriority;
   private int _ThreadsIncrease;
   private int _ThreadsMaximum;
   private int _ThreadsMinimum;
   private static SchemaHelper2 _schemaHelper;

   public ExecuteQueueMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ExecuteQueueMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ExecuteQueueMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getQueueLength() {
      return this._QueueLength;
   }

   public boolean isQueueLengthInherited() {
      return false;
   }

   public boolean isQueueLengthSet() {
      return this._isSet(10);
   }

   public void setQueueLength(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("QueueLength", (long)param0, 256L, 1073741824L);
      int _oldVal = this._QueueLength;
      this._QueueLength = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getThreadPriority() {
      return this._ThreadPriority;
   }

   public boolean isThreadPriorityInherited() {
      return false;
   }

   public boolean isThreadPrioritySet() {
      return this._isSet(11);
   }

   public void setThreadPriority(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("ThreadPriority", (long)param0, 1L, 10L);
      int _oldVal = this._ThreadPriority;
      this._ThreadPriority = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getThreadCount() {
      if (!this._isSet(12)) {
         return this._isProductionModeEnabled() ? 25 : 15;
      } else {
         return this._ThreadCount;
      }
   }

   public boolean isThreadCountInherited() {
      return false;
   }

   public boolean isThreadCountSet() {
      return this._isSet(12);
   }

   public void setThreadCount(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("ThreadCount", (long)param0, 0L, 65536L);
      int _oldVal = this._ThreadCount;
      this._ThreadCount = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getQueueLengthThresholdPercent() {
      return this._QueueLengthThresholdPercent;
   }

   public boolean isQueueLengthThresholdPercentInherited() {
      return false;
   }

   public boolean isQueueLengthThresholdPercentSet() {
      return this._isSet(13);
   }

   public void setQueueLengthThresholdPercent(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("QueueLengthThresholdPercent", (long)param0, 1L, 99L);
      int _oldVal = this._QueueLengthThresholdPercent;
      this._QueueLengthThresholdPercent = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getThreadsIncrease() {
      return this._ThreadsIncrease;
   }

   public boolean isThreadsIncreaseInherited() {
      return false;
   }

   public boolean isThreadsIncreaseSet() {
      return this._isSet(14);
   }

   public void setThreadsIncrease(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("ThreadsIncrease", (long)param0, 0L, 65536L);
      int _oldVal = this._ThreadsIncrease;
      this._ThreadsIncrease = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getThreadsMaximum() {
      return this._ThreadsMaximum;
   }

   public boolean isThreadsMaximumInherited() {
      return false;
   }

   public boolean isThreadsMaximumSet() {
      return this._isSet(15);
   }

   public void setThreadsMaximum(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("ThreadsMaximum", (long)param0, 1L, 65536L);
      int _oldVal = this._ThreadsMaximum;
      this._ThreadsMaximum = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getThreadsMinimum() {
      return this._ThreadsMinimum;
   }

   public boolean isThreadsMinimumInherited() {
      return false;
   }

   public boolean isThreadsMinimumSet() {
      return this._isSet(16);
   }

   public void setThreadsMinimum(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("ThreadsMinimum", (long)param0, 0L, 65536L);
      int _oldVal = this._ThreadsMinimum;
      this._ThreadsMinimum = param0;
      this._postSet(16, _oldVal, param0);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._QueueLength = 65536;
               if (initOne) {
                  break;
               }
            case 13:
               this._QueueLengthThresholdPercent = 90;
               if (initOne) {
                  break;
               }
            case 12:
               this._ThreadCount = 15;
               if (initOne) {
                  break;
               }
            case 11:
               this._ThreadPriority = 5;
               if (initOne) {
                  break;
               }
            case 14:
               this._ThreadsIncrease = 0;
               if (initOne) {
                  break;
               }
            case 15:
               this._ThreadsMaximum = 400;
               if (initOne) {
                  break;
               }
            case 16:
               this._ThreadsMinimum = 5;
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
      return "ExecuteQueue";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("QueueLength")) {
         oldVal = this._QueueLength;
         this._QueueLength = (Integer)v;
         this._postSet(10, oldVal, this._QueueLength);
      } else if (name.equals("QueueLengthThresholdPercent")) {
         oldVal = this._QueueLengthThresholdPercent;
         this._QueueLengthThresholdPercent = (Integer)v;
         this._postSet(13, oldVal, this._QueueLengthThresholdPercent);
      } else if (name.equals("ThreadCount")) {
         oldVal = this._ThreadCount;
         this._ThreadCount = (Integer)v;
         this._postSet(12, oldVal, this._ThreadCount);
      } else if (name.equals("ThreadPriority")) {
         oldVal = this._ThreadPriority;
         this._ThreadPriority = (Integer)v;
         this._postSet(11, oldVal, this._ThreadPriority);
      } else if (name.equals("ThreadsIncrease")) {
         oldVal = this._ThreadsIncrease;
         this._ThreadsIncrease = (Integer)v;
         this._postSet(14, oldVal, this._ThreadsIncrease);
      } else if (name.equals("ThreadsMaximum")) {
         oldVal = this._ThreadsMaximum;
         this._ThreadsMaximum = (Integer)v;
         this._postSet(15, oldVal, this._ThreadsMaximum);
      } else if (name.equals("ThreadsMinimum")) {
         oldVal = this._ThreadsMinimum;
         this._ThreadsMinimum = (Integer)v;
         this._postSet(16, oldVal, this._ThreadsMinimum);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("QueueLength")) {
         return new Integer(this._QueueLength);
      } else if (name.equals("QueueLengthThresholdPercent")) {
         return new Integer(this._QueueLengthThresholdPercent);
      } else if (name.equals("ThreadCount")) {
         return new Integer(this._ThreadCount);
      } else if (name.equals("ThreadPriority")) {
         return new Integer(this._ThreadPriority);
      } else if (name.equals("ThreadsIncrease")) {
         return new Integer(this._ThreadsIncrease);
      } else if (name.equals("ThreadsMaximum")) {
         return new Integer(this._ThreadsMaximum);
      } else {
         return name.equals("ThreadsMinimum") ? new Integer(this._ThreadsMinimum) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("queue-length")) {
                  return 10;
               }

               if (s.equals("thread-count")) {
                  return 12;
               }
               break;
            case 15:
               if (s.equals("thread-priority")) {
                  return 11;
               }

               if (s.equals("threads-maximum")) {
                  return 15;
               }

               if (s.equals("threads-minimum")) {
                  return 16;
               }
               break;
            case 16:
               if (s.equals("threads-increase")) {
                  return 14;
               }
               break;
            case 30:
               if (s.equals("queue-length-threshold-percent")) {
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
               return "queue-length";
            case 11:
               return "thread-priority";
            case 12:
               return "thread-count";
            case 13:
               return "queue-length-threshold-percent";
            case 14:
               return "threads-increase";
            case 15:
               return "threads-maximum";
            case 16:
               return "threads-minimum";
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
      private ExecuteQueueMBeanImpl bean;

      protected Helper(ExecuteQueueMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "QueueLength";
            case 11:
               return "ThreadPriority";
            case 12:
               return "ThreadCount";
            case 13:
               return "QueueLengthThresholdPercent";
            case 14:
               return "ThreadsIncrease";
            case 15:
               return "ThreadsMaximum";
            case 16:
               return "ThreadsMinimum";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("QueueLength")) {
            return 10;
         } else if (propName.equals("QueueLengthThresholdPercent")) {
            return 13;
         } else if (propName.equals("ThreadCount")) {
            return 12;
         } else if (propName.equals("ThreadPriority")) {
            return 11;
         } else if (propName.equals("ThreadsIncrease")) {
            return 14;
         } else if (propName.equals("ThreadsMaximum")) {
            return 15;
         } else {
            return propName.equals("ThreadsMinimum") ? 16 : super.getPropertyIndex(propName);
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
            if (this.bean.isQueueLengthSet()) {
               buf.append("QueueLength");
               buf.append(String.valueOf(this.bean.getQueueLength()));
            }

            if (this.bean.isQueueLengthThresholdPercentSet()) {
               buf.append("QueueLengthThresholdPercent");
               buf.append(String.valueOf(this.bean.getQueueLengthThresholdPercent()));
            }

            if (this.bean.isThreadCountSet()) {
               buf.append("ThreadCount");
               buf.append(String.valueOf(this.bean.getThreadCount()));
            }

            if (this.bean.isThreadPrioritySet()) {
               buf.append("ThreadPriority");
               buf.append(String.valueOf(this.bean.getThreadPriority()));
            }

            if (this.bean.isThreadsIncreaseSet()) {
               buf.append("ThreadsIncrease");
               buf.append(String.valueOf(this.bean.getThreadsIncrease()));
            }

            if (this.bean.isThreadsMaximumSet()) {
               buf.append("ThreadsMaximum");
               buf.append(String.valueOf(this.bean.getThreadsMaximum()));
            }

            if (this.bean.isThreadsMinimumSet()) {
               buf.append("ThreadsMinimum");
               buf.append(String.valueOf(this.bean.getThreadsMinimum()));
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
            ExecuteQueueMBeanImpl otherTyped = (ExecuteQueueMBeanImpl)other;
            this.computeDiff("QueueLength", this.bean.getQueueLength(), otherTyped.getQueueLength(), false);
            this.computeDiff("QueueLengthThresholdPercent", this.bean.getQueueLengthThresholdPercent(), otherTyped.getQueueLengthThresholdPercent(), true);
            this.computeDiff("ThreadCount", this.bean.getThreadCount(), otherTyped.getThreadCount(), false);
            this.computeDiff("ThreadPriority", this.bean.getThreadPriority(), otherTyped.getThreadPriority(), false);
            this.computeDiff("ThreadsIncrease", this.bean.getThreadsIncrease(), otherTyped.getThreadsIncrease(), true);
            this.computeDiff("ThreadsMaximum", this.bean.getThreadsMaximum(), otherTyped.getThreadsMaximum(), true);
            this.computeDiff("ThreadsMinimum", this.bean.getThreadsMinimum(), otherTyped.getThreadsMinimum(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ExecuteQueueMBeanImpl original = (ExecuteQueueMBeanImpl)event.getSourceBean();
            ExecuteQueueMBeanImpl proposed = (ExecuteQueueMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("QueueLength")) {
                  original.setQueueLength(proposed.getQueueLength());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("QueueLengthThresholdPercent")) {
                  original.setQueueLengthThresholdPercent(proposed.getQueueLengthThresholdPercent());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ThreadCount")) {
                  original.setThreadCount(proposed.getThreadCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ThreadPriority")) {
                  original.setThreadPriority(proposed.getThreadPriority());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("ThreadsIncrease")) {
                  original.setThreadsIncrease(proposed.getThreadsIncrease());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ThreadsMaximum")) {
                  original.setThreadsMaximum(proposed.getThreadsMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("ThreadsMinimum")) {
                  original.setThreadsMinimum(proposed.getThreadsMinimum());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
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
            ExecuteQueueMBeanImpl copy = (ExecuteQueueMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("QueueLength")) && this.bean.isQueueLengthSet()) {
               copy.setQueueLength(this.bean.getQueueLength());
            }

            if ((excludeProps == null || !excludeProps.contains("QueueLengthThresholdPercent")) && this.bean.isQueueLengthThresholdPercentSet()) {
               copy.setQueueLengthThresholdPercent(this.bean.getQueueLengthThresholdPercent());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadCount")) && this.bean.isThreadCountSet()) {
               copy.setThreadCount(this.bean.getThreadCount());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadPriority")) && this.bean.isThreadPrioritySet()) {
               copy.setThreadPriority(this.bean.getThreadPriority());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadsIncrease")) && this.bean.isThreadsIncreaseSet()) {
               copy.setThreadsIncrease(this.bean.getThreadsIncrease());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadsMaximum")) && this.bean.isThreadsMaximumSet()) {
               copy.setThreadsMaximum(this.bean.getThreadsMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("ThreadsMinimum")) && this.bean.isThreadsMinimumSet()) {
               copy.setThreadsMinimum(this.bean.getThreadsMinimum());
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

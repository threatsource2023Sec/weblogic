package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ServerFailureTriggerMBeanImpl extends ConfigurationMBeanImpl implements ServerFailureTriggerMBean, Serializable {
   private int _MaxStuckThreadTime;
   private int _StuckThreadCount;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ServerFailureTriggerMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ServerFailureTriggerMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ServerFailureTriggerMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ServerFailureTriggerMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ServerFailureTriggerMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ServerFailureTriggerMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ServerFailureTriggerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServerFailureTriggerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServerFailureTriggerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxStuckThreadTime() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getMaxStuckThreadTime() : this._MaxStuckThreadTime;
   }

   public boolean isMaxStuckThreadTimeInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isMaxStuckThreadTimeSet() {
      return this._isSet(10);
   }

   public void setMaxStuckThreadTime(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxStuckThreadTime", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(10);
      int _oldVal = this._MaxStuckThreadTime;
      this._MaxStuckThreadTime = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerFailureTriggerMBeanImpl source = (ServerFailureTriggerMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public int getStuckThreadCount() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().getStuckThreadCount() : this._StuckThreadCount;
   }

   public boolean isStuckThreadCountInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isStuckThreadCountSet() {
      return this._isSet(11);
   }

   public void setStuckThreadCount(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("StuckThreadCount", (long)param0, 0L, 2147483647L);
      boolean wasSet = this._isSet(11);
      int _oldVal = this._StuckThreadCount;
      this._StuckThreadCount = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ServerFailureTriggerMBeanImpl source = (ServerFailureTriggerMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

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
               this._MaxStuckThreadTime = 600;
               if (initOne) {
                  break;
               }
            case 11:
               this._StuckThreadCount = 0;
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
      return "ServerFailureTrigger";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("MaxStuckThreadTime")) {
         oldVal = this._MaxStuckThreadTime;
         this._MaxStuckThreadTime = (Integer)v;
         this._postSet(10, oldVal, this._MaxStuckThreadTime);
      } else if (name.equals("StuckThreadCount")) {
         oldVal = this._StuckThreadCount;
         this._StuckThreadCount = (Integer)v;
         this._postSet(11, oldVal, this._StuckThreadCount);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("MaxStuckThreadTime")) {
         return new Integer(this._MaxStuckThreadTime);
      } else {
         return name.equals("StuckThreadCount") ? new Integer(this._StuckThreadCount) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 18:
               if (s.equals("stuck-thread-count")) {
                  return 11;
               }
               break;
            case 21:
               if (s.equals("max-stuck-thread-time")) {
                  return 10;
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
               return "max-stuck-thread-time";
            case 11:
               return "stuck-thread-count";
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
      private ServerFailureTriggerMBeanImpl bean;

      protected Helper(ServerFailureTriggerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "MaxStuckThreadTime";
            case 11:
               return "StuckThreadCount";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MaxStuckThreadTime")) {
            return 10;
         } else {
            return propName.equals("StuckThreadCount") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isMaxStuckThreadTimeSet()) {
               buf.append("MaxStuckThreadTime");
               buf.append(String.valueOf(this.bean.getMaxStuckThreadTime()));
            }

            if (this.bean.isStuckThreadCountSet()) {
               buf.append("StuckThreadCount");
               buf.append(String.valueOf(this.bean.getStuckThreadCount()));
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
            ServerFailureTriggerMBeanImpl otherTyped = (ServerFailureTriggerMBeanImpl)other;
            this.computeDiff("MaxStuckThreadTime", this.bean.getMaxStuckThreadTime(), otherTyped.getMaxStuckThreadTime(), false);
            this.computeDiff("StuckThreadCount", this.bean.getStuckThreadCount(), otherTyped.getStuckThreadCount(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServerFailureTriggerMBeanImpl original = (ServerFailureTriggerMBeanImpl)event.getSourceBean();
            ServerFailureTriggerMBeanImpl proposed = (ServerFailureTriggerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MaxStuckThreadTime")) {
                  original.setMaxStuckThreadTime(proposed.getMaxStuckThreadTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("StuckThreadCount")) {
                  original.setStuckThreadCount(proposed.getStuckThreadCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            ServerFailureTriggerMBeanImpl copy = (ServerFailureTriggerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MaxStuckThreadTime")) && this.bean.isMaxStuckThreadTimeSet()) {
               copy.setMaxStuckThreadTime(this.bean.getMaxStuckThreadTime());
            }

            if ((excludeProps == null || !excludeProps.contains("StuckThreadCount")) && this.bean.isStuckThreadCountSet()) {
               copy.setStuckThreadCount(this.bean.getStuckThreadCount());
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

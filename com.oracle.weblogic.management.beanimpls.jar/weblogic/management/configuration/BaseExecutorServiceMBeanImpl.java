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

public class BaseExecutorServiceMBeanImpl extends DeploymentMBeanImpl implements BaseExecutorServiceMBean, Serializable {
   private String _DispatchPolicy;
   private int _LongRunningPriority;
   private int _MaxConcurrentLongRunningRequests;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private BaseExecutorServiceMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(BaseExecutorServiceMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(BaseExecutorServiceMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public BaseExecutorServiceMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(BaseExecutorServiceMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      BaseExecutorServiceMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public BaseExecutorServiceMBeanImpl() {
      this._initializeProperty(-1);
   }

   public BaseExecutorServiceMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public BaseExecutorServiceMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDispatchPolicy() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getDispatchPolicy(), this) : this._DispatchPolicy;
   }

   public boolean isDispatchPolicyInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isDispatchPolicySet() {
      return this._isSet(12);
   }

   public void setDispatchPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._DispatchPolicy;
      this._DispatchPolicy = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BaseExecutorServiceMBeanImpl source = (BaseExecutorServiceMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMaxConcurrentLongRunningRequests() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getMaxConcurrentLongRunningRequests() : this._MaxConcurrentLongRunningRequests;
   }

   public boolean isMaxConcurrentLongRunningRequestsInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isMaxConcurrentLongRunningRequestsSet() {
      return this._isSet(13);
   }

   public void setMaxConcurrentLongRunningRequests(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxConcurrentLongRunningRequests", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(13);
      int _oldVal = this._MaxConcurrentLongRunningRequests;
      this._MaxConcurrentLongRunningRequests = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BaseExecutorServiceMBeanImpl source = (BaseExecutorServiceMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public int getLongRunningPriority() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getLongRunningPriority() : this._LongRunningPriority;
   }

   public boolean isLongRunningPriorityInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isLongRunningPrioritySet() {
      return this._isSet(14);
   }

   public void setLongRunningPriority(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("LongRunningPriority", (long)param0, 1L, 10L);
      boolean wasSet = this._isSet(14);
      int _oldVal = this._LongRunningPriority;
      this._LongRunningPriority = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BaseExecutorServiceMBeanImpl source = (BaseExecutorServiceMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._DispatchPolicy = "";
               if (initOne) {
                  break;
               }
            case 14:
               this._LongRunningPriority = 5;
               if (initOne) {
                  break;
               }
            case 13:
               this._MaxConcurrentLongRunningRequests = 10;
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
      return "BaseExecutorService";
   }

   public void putValue(String name, Object v) {
      if (name.equals("DispatchPolicy")) {
         String oldVal = this._DispatchPolicy;
         this._DispatchPolicy = (String)v;
         this._postSet(12, oldVal, this._DispatchPolicy);
      } else {
         int oldVal;
         if (name.equals("LongRunningPriority")) {
            oldVal = this._LongRunningPriority;
            this._LongRunningPriority = (Integer)v;
            this._postSet(14, oldVal, this._LongRunningPriority);
         } else if (name.equals("MaxConcurrentLongRunningRequests")) {
            oldVal = this._MaxConcurrentLongRunningRequests;
            this._MaxConcurrentLongRunningRequests = (Integer)v;
            this._postSet(13, oldVal, this._MaxConcurrentLongRunningRequests);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("DispatchPolicy")) {
         return this._DispatchPolicy;
      } else if (name.equals("LongRunningPriority")) {
         return new Integer(this._LongRunningPriority);
      } else {
         return name.equals("MaxConcurrentLongRunningRequests") ? new Integer(this._MaxConcurrentLongRunningRequests) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 15:
               if (s.equals("dispatch-policy")) {
                  return 12;
               }
               break;
            case 21:
               if (s.equals("long-running-priority")) {
                  return 14;
               }
               break;
            case 36:
               if (s.equals("max-concurrent-long-running-requests")) {
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
            case 12:
               return "dispatch-policy";
            case 13:
               return "max-concurrent-long-running-requests";
            case 14:
               return "long-running-priority";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 12:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private BaseExecutorServiceMBeanImpl bean;

      protected Helper(BaseExecutorServiceMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "DispatchPolicy";
            case 13:
               return "MaxConcurrentLongRunningRequests";
            case 14:
               return "LongRunningPriority";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DispatchPolicy")) {
            return 12;
         } else if (propName.equals("LongRunningPriority")) {
            return 14;
         } else {
            return propName.equals("MaxConcurrentLongRunningRequests") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isDispatchPolicySet()) {
               buf.append("DispatchPolicy");
               buf.append(String.valueOf(this.bean.getDispatchPolicy()));
            }

            if (this.bean.isLongRunningPrioritySet()) {
               buf.append("LongRunningPriority");
               buf.append(String.valueOf(this.bean.getLongRunningPriority()));
            }

            if (this.bean.isMaxConcurrentLongRunningRequestsSet()) {
               buf.append("MaxConcurrentLongRunningRequests");
               buf.append(String.valueOf(this.bean.getMaxConcurrentLongRunningRequests()));
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
            BaseExecutorServiceMBeanImpl otherTyped = (BaseExecutorServiceMBeanImpl)other;
            this.computeDiff("DispatchPolicy", this.bean.getDispatchPolicy(), otherTyped.getDispatchPolicy(), true);
            this.computeDiff("LongRunningPriority", this.bean.getLongRunningPriority(), otherTyped.getLongRunningPriority(), true);
            this.computeDiff("MaxConcurrentLongRunningRequests", this.bean.getMaxConcurrentLongRunningRequests(), otherTyped.getMaxConcurrentLongRunningRequests(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            BaseExecutorServiceMBeanImpl original = (BaseExecutorServiceMBeanImpl)event.getSourceBean();
            BaseExecutorServiceMBeanImpl proposed = (BaseExecutorServiceMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DispatchPolicy")) {
                  original.setDispatchPolicy(proposed.getDispatchPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("LongRunningPriority")) {
                  original.setLongRunningPriority(proposed.getLongRunningPriority());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("MaxConcurrentLongRunningRequests")) {
                  original.setMaxConcurrentLongRunningRequests(proposed.getMaxConcurrentLongRunningRequests());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            BaseExecutorServiceMBeanImpl copy = (BaseExecutorServiceMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DispatchPolicy")) && this.bean.isDispatchPolicySet()) {
               copy.setDispatchPolicy(this.bean.getDispatchPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("LongRunningPriority")) && this.bean.isLongRunningPrioritySet()) {
               copy.setLongRunningPriority(this.bean.getLongRunningPriority());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConcurrentLongRunningRequests")) && this.bean.isMaxConcurrentLongRunningRequestsSet()) {
               copy.setMaxConcurrentLongRunningRequests(this.bean.getMaxConcurrentLongRunningRequests());
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

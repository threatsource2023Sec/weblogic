package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class OverloadProtectionMBeanImpl extends ConfigurationMBeanImpl implements OverloadProtectionMBean, Serializable {
   private String _FailureAction;
   private int _FreeMemoryPercentHighThreshold;
   private int _FreeMemoryPercentLowThreshold;
   private String _PanicAction;
   private ServerFailureTriggerMBean _ServerFailureTrigger;
   private int _SharedCapacityForWorkManagers;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private OverloadProtectionMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(OverloadProtectionMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(OverloadProtectionMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public OverloadProtectionMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(OverloadProtectionMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      OverloadProtectionMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._ServerFailureTrigger instanceof ServerFailureTriggerMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getServerFailureTrigger() != null) {
            this._getReferenceManager().unregisterBean((ServerFailureTriggerMBeanImpl)oldDelegate.getServerFailureTrigger());
         }

         if (delegate != null && delegate.getServerFailureTrigger() != null) {
            this._getReferenceManager().registerBean((ServerFailureTriggerMBeanImpl)delegate.getServerFailureTrigger(), false);
         }

         ((ServerFailureTriggerMBeanImpl)this._ServerFailureTrigger)._setDelegateBean((ServerFailureTriggerMBeanImpl)((ServerFailureTriggerMBeanImpl)(delegate == null ? null : delegate.getServerFailureTrigger())));
      }

   }

   public OverloadProtectionMBeanImpl() {
      this._initializeProperty(-1);
   }

   public OverloadProtectionMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public OverloadProtectionMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setSharedCapacityForWorkManagers(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("SharedCapacityForWorkManagers", (long)param0, 1L, 1073741824L);
      boolean wasSet = this._isSet(10);
      int _oldVal = this._SharedCapacityForWorkManagers;
      this._SharedCapacityForWorkManagers = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OverloadProtectionMBeanImpl source = (OverloadProtectionMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public int getSharedCapacityForWorkManagers() {
      if (!this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10)) {
         return this._getDelegateBean().getSharedCapacityForWorkManagers();
      } else {
         if (!this._isSet(10)) {
            try {
               return this.getParent() instanceof ServerTemplateMBean ? (((ServerTemplateMBean)this.getParent()).getCluster() == null ? 65536 : ((ServerTemplateMBean)this.getParent()).getCluster().getOverloadProtection().getSharedCapacityForWorkManagers()) : 65536;
            } catch (NullPointerException var2) {
            }
         }

         return this._SharedCapacityForWorkManagers;
      }
   }

   public boolean isSharedCapacityForWorkManagersInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isSharedCapacityForWorkManagersSet() {
      return this._isSet(10);
   }

   public void setPanicAction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"no-action", "system-exit"};
      param0 = LegalChecks.checkInEnum("PanicAction", param0, _set);
      boolean wasSet = this._isSet(11);
      String _oldVal = this._PanicAction;
      this._PanicAction = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         OverloadProtectionMBeanImpl source = (OverloadProtectionMBeanImpl)var5.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPanicAction() {
      if (!this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11)) {
         return this._performMacroSubstitution(this._getDelegateBean().getPanicAction(), this);
      } else {
         if (!this._isSet(11)) {
            try {
               return this.getParent() instanceof ServerTemplateMBean ? (((ServerTemplateMBean)this.getParent()).getCluster() == null ? "system-exit" : ((ServerTemplateMBean)this.getParent()).getCluster().getOverloadProtection().getPanicAction()) : "system-exit";
            } catch (NullPointerException var2) {
            }
         }

         return this._PanicAction;
      }
   }

   public boolean isPanicActionInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isPanicActionSet() {
      return this._isSet(11);
   }

   public void setFailureAction(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"no-action", "force-shutdown", "admin-state"};
      param0 = LegalChecks.checkInEnum("FailureAction", param0, _set);
      boolean wasSet = this._isSet(12);
      String _oldVal = this._FailureAction;
      this._FailureAction = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         OverloadProtectionMBeanImpl source = (OverloadProtectionMBeanImpl)var5.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getFailureAction() {
      if (!this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         return this._performMacroSubstitution(this._getDelegateBean().getFailureAction(), this);
      } else {
         if (!this._isSet(12)) {
            try {
               return this.getParent() instanceof ServerTemplateMBean ? (((ServerTemplateMBean)this.getParent()).getCluster() == null ? "no-action" : ((ServerTemplateMBean)this.getParent()).getCluster().getOverloadProtection().getFailureAction()) : "no-action";
            } catch (NullPointerException var2) {
            }
         }

         return this._FailureAction;
      }
   }

   public boolean isFailureActionInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isFailureActionSet() {
      return this._isSet(12);
   }

   public void setFreeMemoryPercentHighThreshold(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("FreeMemoryPercentHighThreshold", (long)param0, 0L, 99L);
      boolean wasSet = this._isSet(13);
      int _oldVal = this._FreeMemoryPercentHighThreshold;
      this._FreeMemoryPercentHighThreshold = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OverloadProtectionMBeanImpl source = (OverloadProtectionMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public int getFreeMemoryPercentHighThreshold() {
      if (!this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13)) {
         return this._getDelegateBean().getFreeMemoryPercentHighThreshold();
      } else {
         if (!this._isSet(13)) {
            try {
               return this.getParent() instanceof ServerTemplateMBean ? (((ServerTemplateMBean)this.getParent()).getCluster() == null ? 0 : ((ServerTemplateMBean)this.getParent()).getCluster().getOverloadProtection().getFreeMemoryPercentHighThreshold()) : 0;
            } catch (NullPointerException var2) {
            }
         }

         return this._FreeMemoryPercentHighThreshold;
      }
   }

   public boolean isFreeMemoryPercentHighThresholdInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isFreeMemoryPercentHighThresholdSet() {
      return this._isSet(13);
   }

   public void setFreeMemoryPercentLowThreshold(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("FreeMemoryPercentLowThreshold", (long)param0, 0L, 99L);
      boolean wasSet = this._isSet(14);
      int _oldVal = this._FreeMemoryPercentLowThreshold;
      this._FreeMemoryPercentLowThreshold = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         OverloadProtectionMBeanImpl source = (OverloadProtectionMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public int getFreeMemoryPercentLowThreshold() {
      if (!this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14)) {
         return this._getDelegateBean().getFreeMemoryPercentLowThreshold();
      } else {
         if (!this._isSet(14)) {
            try {
               return this.getParent() instanceof ServerTemplateMBean ? (((ServerTemplateMBean)this.getParent()).getCluster() == null ? 0 : ((ServerTemplateMBean)this.getParent()).getCluster().getOverloadProtection().getFreeMemoryPercentLowThreshold()) : 0;
            } catch (NullPointerException var2) {
            }
         }

         return this._FreeMemoryPercentLowThreshold;
      }
   }

   public boolean isFreeMemoryPercentLowThresholdInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isFreeMemoryPercentLowThresholdSet() {
      return this._isSet(14);
   }

   public ServerFailureTriggerMBean getServerFailureTrigger() {
      if (!this._isSet(15)) {
         try {
            return this.getParent() instanceof ServerTemplateMBean ? (((ServerTemplateMBean)this.getParent()).getCluster() == null ? null : ((ServerTemplateMBean)this.getParent()).getCluster().getOverloadProtection().getServerFailureTrigger()) : null;
         } catch (NullPointerException var2) {
         }
      }

      return this._ServerFailureTrigger;
   }

   public boolean isServerFailureTriggerInherited() {
      return false;
   }

   public boolean isServerFailureTriggerSet() {
      return this._isSet(15);
   }

   public void setServerFailureTrigger(ServerFailureTriggerMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getServerFailureTrigger() != null && param0 != this.getServerFailureTrigger()) {
         throw new BeanAlreadyExistsException(this.getServerFailureTrigger() + " has already been created");
      } else {
         if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
            this._untransient();
         }

         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 15)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         boolean wasSet = this._isSet(15);
         ServerFailureTriggerMBean _oldVal = this._ServerFailureTrigger;
         this._ServerFailureTrigger = param0;
         this._postSet(15, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            OverloadProtectionMBeanImpl source = (OverloadProtectionMBeanImpl)var4.next();
            if (source != null && !source._isSet(15)) {
               source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public ServerFailureTriggerMBean createServerFailureTrigger() {
      ServerFailureTriggerMBeanImpl _val = new ServerFailureTriggerMBeanImpl(this, -1);

      try {
         this.setServerFailureTrigger(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyServerFailureTrigger() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ServerFailureTrigger;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setServerFailureTrigger((ServerFailureTriggerMBean)null);
               this._unSet(15);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
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
               this._FailureAction = "no-action";
               if (initOne) {
                  break;
               }
            case 13:
               this._FreeMemoryPercentHighThreshold = 0;
               if (initOne) {
                  break;
               }
            case 14:
               this._FreeMemoryPercentLowThreshold = 0;
               if (initOne) {
                  break;
               }
            case 11:
               this._PanicAction = "system-exit";
               if (initOne) {
                  break;
               }
            case 15:
               this._ServerFailureTrigger = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._SharedCapacityForWorkManagers = 65536;
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
      return "OverloadProtection";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("FailureAction")) {
         oldVal = this._FailureAction;
         this._FailureAction = (String)v;
         this._postSet(12, oldVal, this._FailureAction);
      } else {
         int oldVal;
         if (name.equals("FreeMemoryPercentHighThreshold")) {
            oldVal = this._FreeMemoryPercentHighThreshold;
            this._FreeMemoryPercentHighThreshold = (Integer)v;
            this._postSet(13, oldVal, this._FreeMemoryPercentHighThreshold);
         } else if (name.equals("FreeMemoryPercentLowThreshold")) {
            oldVal = this._FreeMemoryPercentLowThreshold;
            this._FreeMemoryPercentLowThreshold = (Integer)v;
            this._postSet(14, oldVal, this._FreeMemoryPercentLowThreshold);
         } else if (name.equals("PanicAction")) {
            oldVal = this._PanicAction;
            this._PanicAction = (String)v;
            this._postSet(11, oldVal, this._PanicAction);
         } else if (name.equals("ServerFailureTrigger")) {
            ServerFailureTriggerMBean oldVal = this._ServerFailureTrigger;
            this._ServerFailureTrigger = (ServerFailureTriggerMBean)v;
            this._postSet(15, oldVal, this._ServerFailureTrigger);
         } else if (name.equals("SharedCapacityForWorkManagers")) {
            oldVal = this._SharedCapacityForWorkManagers;
            this._SharedCapacityForWorkManagers = (Integer)v;
            this._postSet(10, oldVal, this._SharedCapacityForWorkManagers);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("FailureAction")) {
         return this._FailureAction;
      } else if (name.equals("FreeMemoryPercentHighThreshold")) {
         return new Integer(this._FreeMemoryPercentHighThreshold);
      } else if (name.equals("FreeMemoryPercentLowThreshold")) {
         return new Integer(this._FreeMemoryPercentLowThreshold);
      } else if (name.equals("PanicAction")) {
         return this._PanicAction;
      } else if (name.equals("ServerFailureTrigger")) {
         return this._ServerFailureTrigger;
      } else {
         return name.equals("SharedCapacityForWorkManagers") ? new Integer(this._SharedCapacityForWorkManagers) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("panic-action")) {
                  return 11;
               }
               break;
            case 14:
               if (s.equals("failure-action")) {
                  return 12;
               }
               break;
            case 22:
               if (s.equals("server-failure-trigger")) {
                  return 15;
               }
               break;
            case 33:
               if (s.equals("free-memory-percent-low-threshold")) {
                  return 14;
               }

               if (s.equals("shared-capacity-for-work-managers")) {
                  return 10;
               }
               break;
            case 34:
               if (s.equals("free-memory-percent-high-threshold")) {
                  return 13;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 15:
               return new ServerFailureTriggerMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "shared-capacity-for-work-managers";
            case 11:
               return "panic-action";
            case 12:
               return "failure-action";
            case 13:
               return "free-memory-percent-high-threshold";
            case 14:
               return "free-memory-percent-low-threshold";
            case 15:
               return "server-failure-trigger";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 15:
               return true;
            default:
               return super.isBean(propIndex);
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
      private OverloadProtectionMBeanImpl bean;

      protected Helper(OverloadProtectionMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "SharedCapacityForWorkManagers";
            case 11:
               return "PanicAction";
            case 12:
               return "FailureAction";
            case 13:
               return "FreeMemoryPercentHighThreshold";
            case 14:
               return "FreeMemoryPercentLowThreshold";
            case 15:
               return "ServerFailureTrigger";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FailureAction")) {
            return 12;
         } else if (propName.equals("FreeMemoryPercentHighThreshold")) {
            return 13;
         } else if (propName.equals("FreeMemoryPercentLowThreshold")) {
            return 14;
         } else if (propName.equals("PanicAction")) {
            return 11;
         } else if (propName.equals("ServerFailureTrigger")) {
            return 15;
         } else {
            return propName.equals("SharedCapacityForWorkManagers") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getServerFailureTrigger() != null) {
            iterators.add(new ArrayIterator(new ServerFailureTriggerMBean[]{this.bean.getServerFailureTrigger()}));
         }

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
            if (this.bean.isFailureActionSet()) {
               buf.append("FailureAction");
               buf.append(String.valueOf(this.bean.getFailureAction()));
            }

            if (this.bean.isFreeMemoryPercentHighThresholdSet()) {
               buf.append("FreeMemoryPercentHighThreshold");
               buf.append(String.valueOf(this.bean.getFreeMemoryPercentHighThreshold()));
            }

            if (this.bean.isFreeMemoryPercentLowThresholdSet()) {
               buf.append("FreeMemoryPercentLowThreshold");
               buf.append(String.valueOf(this.bean.getFreeMemoryPercentLowThreshold()));
            }

            if (this.bean.isPanicActionSet()) {
               buf.append("PanicAction");
               buf.append(String.valueOf(this.bean.getPanicAction()));
            }

            childValue = this.computeChildHashValue(this.bean.getServerFailureTrigger());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSharedCapacityForWorkManagersSet()) {
               buf.append("SharedCapacityForWorkManagers");
               buf.append(String.valueOf(this.bean.getSharedCapacityForWorkManagers()));
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
            OverloadProtectionMBeanImpl otherTyped = (OverloadProtectionMBeanImpl)other;
            this.computeDiff("FailureAction", this.bean.getFailureAction(), otherTyped.getFailureAction(), true);
            this.computeDiff("FreeMemoryPercentHighThreshold", this.bean.getFreeMemoryPercentHighThreshold(), otherTyped.getFreeMemoryPercentHighThreshold(), false);
            this.computeDiff("FreeMemoryPercentLowThreshold", this.bean.getFreeMemoryPercentLowThreshold(), otherTyped.getFreeMemoryPercentLowThreshold(), false);
            this.computeDiff("PanicAction", this.bean.getPanicAction(), otherTyped.getPanicAction(), false);
            this.computeChildDiff("ServerFailureTrigger", this.bean.getServerFailureTrigger(), otherTyped.getServerFailureTrigger(), false);
            this.computeDiff("SharedCapacityForWorkManagers", this.bean.getSharedCapacityForWorkManagers(), otherTyped.getSharedCapacityForWorkManagers(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            OverloadProtectionMBeanImpl original = (OverloadProtectionMBeanImpl)event.getSourceBean();
            OverloadProtectionMBeanImpl proposed = (OverloadProtectionMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FailureAction")) {
                  original.setFailureAction(proposed.getFailureAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("FreeMemoryPercentHighThreshold")) {
                  original.setFreeMemoryPercentHighThreshold(proposed.getFreeMemoryPercentHighThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("FreeMemoryPercentLowThreshold")) {
                  original.setFreeMemoryPercentLowThreshold(proposed.getFreeMemoryPercentLowThreshold());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("PanicAction")) {
                  original.setPanicAction(proposed.getPanicAction());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("ServerFailureTrigger")) {
                  if (type == 2) {
                     original.setServerFailureTrigger((ServerFailureTriggerMBean)this.createCopy((AbstractDescriptorBean)proposed.getServerFailureTrigger()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ServerFailureTrigger", original.getServerFailureTrigger());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("SharedCapacityForWorkManagers")) {
                  original.setSharedCapacityForWorkManagers(proposed.getSharedCapacityForWorkManagers());
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
            OverloadProtectionMBeanImpl copy = (OverloadProtectionMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FailureAction")) && this.bean.isFailureActionSet()) {
               copy.setFailureAction(this.bean.getFailureAction());
            }

            if ((excludeProps == null || !excludeProps.contains("FreeMemoryPercentHighThreshold")) && this.bean.isFreeMemoryPercentHighThresholdSet()) {
               copy.setFreeMemoryPercentHighThreshold(this.bean.getFreeMemoryPercentHighThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("FreeMemoryPercentLowThreshold")) && this.bean.isFreeMemoryPercentLowThresholdSet()) {
               copy.setFreeMemoryPercentLowThreshold(this.bean.getFreeMemoryPercentLowThreshold());
            }

            if ((excludeProps == null || !excludeProps.contains("PanicAction")) && this.bean.isPanicActionSet()) {
               copy.setPanicAction(this.bean.getPanicAction());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerFailureTrigger")) && this.bean.isServerFailureTriggerSet() && !copy._isSet(15)) {
               Object o = this.bean.getServerFailureTrigger();
               copy.setServerFailureTrigger((ServerFailureTriggerMBean)null);
               copy.setServerFailureTrigger(o == null ? null : (ServerFailureTriggerMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("SharedCapacityForWorkManagers")) && this.bean.isSharedCapacityForWorkManagersSet()) {
               copy.setSharedCapacityForWorkManagers(this.bean.getSharedCapacityForWorkManagers());
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
         this.inferSubTree(this.bean.getServerFailureTrigger(), clazz, annotation);
      }
   }
}

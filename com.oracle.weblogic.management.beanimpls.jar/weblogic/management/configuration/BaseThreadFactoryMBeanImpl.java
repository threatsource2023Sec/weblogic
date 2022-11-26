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

public class BaseThreadFactoryMBeanImpl extends DeploymentMBeanImpl implements BaseThreadFactoryMBean, Serializable {
   private int _MaxConcurrentNewThreads;
   private int _Priority;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private BaseThreadFactoryMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(BaseThreadFactoryMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(BaseThreadFactoryMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public BaseThreadFactoryMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(BaseThreadFactoryMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      BaseThreadFactoryMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public BaseThreadFactoryMBeanImpl() {
      this._initializeProperty(-1);
   }

   public BaseThreadFactoryMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public BaseThreadFactoryMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMaxConcurrentNewThreads() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getMaxConcurrentNewThreads() : this._MaxConcurrentNewThreads;
   }

   public boolean isMaxConcurrentNewThreadsInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isMaxConcurrentNewThreadsSet() {
      return this._isSet(12);
   }

   public void setMaxConcurrentNewThreads(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MaxConcurrentNewThreads", (long)param0, 0L, 65534L);
      boolean wasSet = this._isSet(12);
      int _oldVal = this._MaxConcurrentNewThreads;
      this._MaxConcurrentNewThreads = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BaseThreadFactoryMBeanImpl source = (BaseThreadFactoryMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public int getPriority() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getPriority() : this._Priority;
   }

   public boolean isPriorityInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isPrioritySet() {
      return this._isSet(13);
   }

   public void setPriority(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("Priority", (long)param0, 1L, 10L);
      boolean wasSet = this._isSet(13);
      int _oldVal = this._Priority;
      this._Priority = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BaseThreadFactoryMBeanImpl source = (BaseThreadFactoryMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
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
               this._MaxConcurrentNewThreads = 10;
               if (initOne) {
                  break;
               }
            case 13:
               this._Priority = 5;
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
      return "BaseThreadFactory";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("MaxConcurrentNewThreads")) {
         oldVal = this._MaxConcurrentNewThreads;
         this._MaxConcurrentNewThreads = (Integer)v;
         this._postSet(12, oldVal, this._MaxConcurrentNewThreads);
      } else if (name.equals("Priority")) {
         oldVal = this._Priority;
         this._Priority = (Integer)v;
         this._postSet(13, oldVal, this._Priority);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("MaxConcurrentNewThreads")) {
         return new Integer(this._MaxConcurrentNewThreads);
      } else {
         return name.equals("Priority") ? new Integer(this._Priority) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("priority")) {
                  return 13;
               }
               break;
            case 26:
               if (s.equals("max-concurrent-new-threads")) {
                  return 12;
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
               return "max-concurrent-new-threads";
            case 13:
               return "priority";
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
      private BaseThreadFactoryMBeanImpl bean;

      protected Helper(BaseThreadFactoryMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "MaxConcurrentNewThreads";
            case 13:
               return "Priority";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MaxConcurrentNewThreads")) {
            return 12;
         } else {
            return propName.equals("Priority") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isMaxConcurrentNewThreadsSet()) {
               buf.append("MaxConcurrentNewThreads");
               buf.append(String.valueOf(this.bean.getMaxConcurrentNewThreads()));
            }

            if (this.bean.isPrioritySet()) {
               buf.append("Priority");
               buf.append(String.valueOf(this.bean.getPriority()));
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
            BaseThreadFactoryMBeanImpl otherTyped = (BaseThreadFactoryMBeanImpl)other;
            this.computeDiff("MaxConcurrentNewThreads", this.bean.getMaxConcurrentNewThreads(), otherTyped.getMaxConcurrentNewThreads(), true);
            this.computeDiff("Priority", this.bean.getPriority(), otherTyped.getPriority(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            BaseThreadFactoryMBeanImpl original = (BaseThreadFactoryMBeanImpl)event.getSourceBean();
            BaseThreadFactoryMBeanImpl proposed = (BaseThreadFactoryMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MaxConcurrentNewThreads")) {
                  original.setMaxConcurrentNewThreads(proposed.getMaxConcurrentNewThreads());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Priority")) {
                  original.setPriority(proposed.getPriority());
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
            BaseThreadFactoryMBeanImpl copy = (BaseThreadFactoryMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MaxConcurrentNewThreads")) && this.bean.isMaxConcurrentNewThreadsSet()) {
               copy.setMaxConcurrentNewThreads(this.bean.getMaxConcurrentNewThreads());
            }

            if ((excludeProps == null || !excludeProps.contains("Priority")) && this.bean.isPrioritySet()) {
               copy.setPriority(this.bean.getPriority());
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

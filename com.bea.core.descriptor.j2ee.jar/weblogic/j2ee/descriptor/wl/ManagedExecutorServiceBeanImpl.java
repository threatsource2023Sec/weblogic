package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ManagedExecutorServiceBeanImpl extends SettableBeanImpl implements ManagedExecutorServiceBean, Serializable {
   private String _DispatchPolicy;
   private String _Id;
   private int _LongRunningPriority;
   private int _MaxConcurrentLongRunningRequests;
   private String _Name;
   private static SchemaHelper2 _schemaHelper;

   public ManagedExecutorServiceBeanImpl() {
      this._initializeProperty(-1);
   }

   public ManagedExecutorServiceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ManagedExecutorServiceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDispatchPolicy() {
      return this._DispatchPolicy;
   }

   public boolean isDispatchPolicyInherited() {
      return false;
   }

   public boolean isDispatchPolicySet() {
      return this._isSet(1);
   }

   public void setDispatchPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DispatchPolicy;
      this._DispatchPolicy = param0;
      this._postSet(1, _oldVal, param0);
   }

   public int getMaxConcurrentLongRunningRequests() {
      return this._MaxConcurrentLongRunningRequests;
   }

   public boolean isMaxConcurrentLongRunningRequestsInherited() {
      return false;
   }

   public boolean isMaxConcurrentLongRunningRequestsSet() {
      return this._isSet(2);
   }

   public void setMaxConcurrentLongRunningRequests(int param0) {
      LegalChecks.checkInRange("MaxConcurrentLongRunningRequests", (long)param0, 0L, 65534L);
      int _oldVal = this._MaxConcurrentLongRunningRequests;
      this._MaxConcurrentLongRunningRequests = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getLongRunningPriority() {
      return this._LongRunningPriority;
   }

   public boolean isLongRunningPriorityInherited() {
      return false;
   }

   public boolean isLongRunningPrioritySet() {
      return this._isSet(3);
   }

   public void setLongRunningPriority(int param0) {
      LegalChecks.checkInRange("LongRunningPriority", (long)param0, 1L, 10L);
      int _oldVal = this._LongRunningPriority;
      this._LongRunningPriority = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(4, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._DispatchPolicy = "";
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._LongRunningPriority = 5;
               if (initOne) {
                  break;
               }
            case 2:
               this._MaxConcurrentLongRunningRequests = 10;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 4;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("dispatch-policy")) {
                  return 1;
               }
               break;
            case 21:
               if (s.equals("long-running-priority")) {
                  return 3;
               }
               break;
            case 36:
               if (s.equals("max-concurrent-long-running-requests")) {
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
               return "name";
            case 1:
               return "dispatch-policy";
            case 2:
               return "max-concurrent-long-running-requests";
            case 3:
               return "long-running-priority";
            case 4:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private ManagedExecutorServiceBeanImpl bean;

      protected Helper(ManagedExecutorServiceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "DispatchPolicy";
            case 2:
               return "MaxConcurrentLongRunningRequests";
            case 3:
               return "LongRunningPriority";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DispatchPolicy")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("LongRunningPriority")) {
            return 3;
         } else if (propName.equals("MaxConcurrentLongRunningRequests")) {
            return 2;
         } else {
            return propName.equals("Name") ? 0 : super.getPropertyIndex(propName);
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

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isLongRunningPrioritySet()) {
               buf.append("LongRunningPriority");
               buf.append(String.valueOf(this.bean.getLongRunningPriority()));
            }

            if (this.bean.isMaxConcurrentLongRunningRequestsSet()) {
               buf.append("MaxConcurrentLongRunningRequests");
               buf.append(String.valueOf(this.bean.getMaxConcurrentLongRunningRequests()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
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
            ManagedExecutorServiceBeanImpl otherTyped = (ManagedExecutorServiceBeanImpl)other;
            this.computeDiff("DispatchPolicy", this.bean.getDispatchPolicy(), otherTyped.getDispatchPolicy(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("LongRunningPriority", this.bean.getLongRunningPriority(), otherTyped.getLongRunningPriority(), false);
            this.computeDiff("MaxConcurrentLongRunningRequests", this.bean.getMaxConcurrentLongRunningRequests(), otherTyped.getMaxConcurrentLongRunningRequests(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ManagedExecutorServiceBeanImpl original = (ManagedExecutorServiceBeanImpl)event.getSourceBean();
            ManagedExecutorServiceBeanImpl proposed = (ManagedExecutorServiceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DispatchPolicy")) {
                  original.setDispatchPolicy(proposed.getDispatchPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("LongRunningPriority")) {
                  original.setLongRunningPriority(proposed.getLongRunningPriority());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("MaxConcurrentLongRunningRequests")) {
                  original.setMaxConcurrentLongRunningRequests(proposed.getMaxConcurrentLongRunningRequests());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
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
            ManagedExecutorServiceBeanImpl copy = (ManagedExecutorServiceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DispatchPolicy")) && this.bean.isDispatchPolicySet()) {
               copy.setDispatchPolicy(this.bean.getDispatchPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("LongRunningPriority")) && this.bean.isLongRunningPrioritySet()) {
               copy.setLongRunningPriority(this.bean.getLongRunningPriority());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConcurrentLongRunningRequests")) && this.bean.isMaxConcurrentLongRunningRequestsSet()) {
               copy.setMaxConcurrentLongRunningRequests(this.bean.getMaxConcurrentLongRunningRequests());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
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

package weblogic.management.security.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.commo.AbstractCommoConfigurationBean;
import weblogic.utils.collections.CombinedIterator;

public class GroupMembershipHierarchyCacheMBeanImpl extends AbstractCommoConfigurationBean implements GroupMembershipHierarchyCacheMBean, Serializable {
   private Boolean _EnableGroupMembershipLookupHierarchyCaching;
   private Integer _GroupHierarchyCacheTTL;
   private Integer _MaxGroupHierarchiesInCache;
   private static SchemaHelper2 _schemaHelper;

   public GroupMembershipHierarchyCacheMBeanImpl() {
      this._initializeProperty(-1);
   }

   public GroupMembershipHierarchyCacheMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public GroupMembershipHierarchyCacheMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public Boolean getEnableGroupMembershipLookupHierarchyCaching() {
      return this._EnableGroupMembershipLookupHierarchyCaching;
   }

   public boolean isEnableGroupMembershipLookupHierarchyCachingInherited() {
      return false;
   }

   public boolean isEnableGroupMembershipLookupHierarchyCachingSet() {
      return this._isSet(2);
   }

   public void setEnableGroupMembershipLookupHierarchyCaching(Boolean param0) throws InvalidAttributeValueException {
      Boolean _oldVal = this._EnableGroupMembershipLookupHierarchyCaching;
      this._EnableGroupMembershipLookupHierarchyCaching = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Integer getMaxGroupHierarchiesInCache() {
      return this._MaxGroupHierarchiesInCache;
   }

   public boolean isMaxGroupHierarchiesInCacheInherited() {
      return false;
   }

   public boolean isMaxGroupHierarchiesInCacheSet() {
      return this._isSet(3);
   }

   public void setMaxGroupHierarchiesInCache(Integer param0) throws InvalidAttributeValueException {
      Integer _oldVal = this._MaxGroupHierarchiesInCache;
      this._MaxGroupHierarchiesInCache = param0;
      this._postSet(3, _oldVal, param0);
   }

   public Integer getGroupHierarchyCacheTTL() {
      return this._GroupHierarchyCacheTTL;
   }

   public boolean isGroupHierarchyCacheTTLInherited() {
      return false;
   }

   public boolean isGroupHierarchyCacheTTLSet() {
      return this._isSet(4);
   }

   public void setGroupHierarchyCacheTTL(Integer param0) throws InvalidAttributeValueException {
      Integer _oldVal = this._GroupHierarchyCacheTTL;
      this._GroupHierarchyCacheTTL = param0;
      this._postSet(4, _oldVal, param0);
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
               this._EnableGroupMembershipLookupHierarchyCaching = new Boolean(false);
               if (initOne) {
                  break;
               }
            case 4:
               this._GroupHierarchyCacheTTL = new Integer(60);
               if (initOne) {
                  break;
               }
            case 3:
               this._MaxGroupHierarchiesInCache = new Integer(100);
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
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.security.authentication.GroupMembershipHierarchyCacheMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 24:
               if (s.equals("group-hierarchy-cachettl")) {
                  return 4;
               }
               break;
            case 30:
               if (s.equals("max-group-hierarchies-in-cache")) {
                  return 3;
               }
               break;
            case 48:
               if (s.equals("enable-group-membership-lookup-hierarchy-caching")) {
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
            case 2:
               return "enable-group-membership-lookup-hierarchy-caching";
            case 3:
               return "max-group-hierarchies-in-cache";
            case 4:
               return "group-hierarchy-cachettl";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractCommoConfigurationBean.Helper {
      private GroupMembershipHierarchyCacheMBeanImpl bean;

      protected Helper(GroupMembershipHierarchyCacheMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "EnableGroupMembershipLookupHierarchyCaching";
            case 3:
               return "MaxGroupHierarchiesInCache";
            case 4:
               return "GroupHierarchyCacheTTL";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EnableGroupMembershipLookupHierarchyCaching")) {
            return 2;
         } else if (propName.equals("GroupHierarchyCacheTTL")) {
            return 4;
         } else {
            return propName.equals("MaxGroupHierarchiesInCache") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isEnableGroupMembershipLookupHierarchyCachingSet()) {
               buf.append("EnableGroupMembershipLookupHierarchyCaching");
               buf.append(String.valueOf(this.bean.getEnableGroupMembershipLookupHierarchyCaching()));
            }

            if (this.bean.isGroupHierarchyCacheTTLSet()) {
               buf.append("GroupHierarchyCacheTTL");
               buf.append(String.valueOf(this.bean.getGroupHierarchyCacheTTL()));
            }

            if (this.bean.isMaxGroupHierarchiesInCacheSet()) {
               buf.append("MaxGroupHierarchiesInCache");
               buf.append(String.valueOf(this.bean.getMaxGroupHierarchiesInCache()));
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
            GroupMembershipHierarchyCacheMBeanImpl otherTyped = (GroupMembershipHierarchyCacheMBeanImpl)other;
            this.computeDiff("EnableGroupMembershipLookupHierarchyCaching", this.bean.getEnableGroupMembershipLookupHierarchyCaching(), otherTyped.getEnableGroupMembershipLookupHierarchyCaching(), true);
            this.computeDiff("GroupHierarchyCacheTTL", this.bean.getGroupHierarchyCacheTTL(), otherTyped.getGroupHierarchyCacheTTL(), true);
            this.computeDiff("MaxGroupHierarchiesInCache", this.bean.getMaxGroupHierarchiesInCache(), otherTyped.getMaxGroupHierarchiesInCache(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            GroupMembershipHierarchyCacheMBeanImpl original = (GroupMembershipHierarchyCacheMBeanImpl)event.getSourceBean();
            GroupMembershipHierarchyCacheMBeanImpl proposed = (GroupMembershipHierarchyCacheMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EnableGroupMembershipLookupHierarchyCaching")) {
                  original.setEnableGroupMembershipLookupHierarchyCaching(proposed.getEnableGroupMembershipLookupHierarchyCaching());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("GroupHierarchyCacheTTL")) {
                  original.setGroupHierarchyCacheTTL(proposed.getGroupHierarchyCacheTTL());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("MaxGroupHierarchiesInCache")) {
                  original.setMaxGroupHierarchiesInCache(proposed.getMaxGroupHierarchiesInCache());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            GroupMembershipHierarchyCacheMBeanImpl copy = (GroupMembershipHierarchyCacheMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EnableGroupMembershipLookupHierarchyCaching")) && this.bean.isEnableGroupMembershipLookupHierarchyCachingSet()) {
               copy.setEnableGroupMembershipLookupHierarchyCaching(this.bean.getEnableGroupMembershipLookupHierarchyCaching());
            }

            if ((excludeProps == null || !excludeProps.contains("GroupHierarchyCacheTTL")) && this.bean.isGroupHierarchyCacheTTLSet()) {
               copy.setGroupHierarchyCacheTTL(this.bean.getGroupHierarchyCacheTTL());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxGroupHierarchiesInCache")) && this.bean.isMaxGroupHierarchiesInCacheSet()) {
               copy.setMaxGroupHierarchiesInCache(this.bean.getMaxGroupHierarchiesInCache());
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

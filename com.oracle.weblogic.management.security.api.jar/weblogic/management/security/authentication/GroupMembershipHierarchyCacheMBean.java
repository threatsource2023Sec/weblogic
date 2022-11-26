package weblogic.management.security.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface GroupMembershipHierarchyCacheMBean extends StandardInterface, DescriptorBean {
   Boolean getEnableGroupMembershipLookupHierarchyCaching();

   void setEnableGroupMembershipLookupHierarchyCaching(Boolean var1) throws InvalidAttributeValueException;

   Integer getMaxGroupHierarchiesInCache();

   void setMaxGroupHierarchiesInCache(Integer var1) throws InvalidAttributeValueException;

   Integer getGroupHierarchyCacheTTL();

   void setGroupHierarchyCacheTTL(Integer var1) throws InvalidAttributeValueException;
}

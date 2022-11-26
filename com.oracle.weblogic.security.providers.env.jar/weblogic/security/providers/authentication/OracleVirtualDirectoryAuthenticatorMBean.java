package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface OracleVirtualDirectoryAuthenticatorMBean extends StandardInterface, DescriptorBean, LDAPAuthenticatorMBean {
   String getDescription();

   String getUserNameAttribute();

   void setUserNameAttribute(String var1) throws InvalidAttributeValueException;

   String getAllUsersFilter();

   void setAllUsersFilter(String var1) throws InvalidAttributeValueException;

   String getUserFromNameFilter();

   void setUserFromNameFilter(String var1) throws InvalidAttributeValueException;

   String getAllGroupsFilter();

   void setAllGroupsFilter(String var1) throws InvalidAttributeValueException;

   String getGroupFromNameFilter();

   void setGroupFromNameFilter(String var1) throws InvalidAttributeValueException;

   String getDynamicGroupNameAttribute();

   void setDynamicGroupNameAttribute(String var1) throws InvalidAttributeValueException;

   String getDynamicGroupObjectClass();

   void setDynamicGroupObjectClass(String var1) throws InvalidAttributeValueException;

   String getDynamicMemberURLAttribute();

   void setDynamicMemberURLAttribute(String var1) throws InvalidAttributeValueException;

   String getGuidAttribute();

   void setGuidAttribute(String var1) throws InvalidAttributeValueException;

   String getName();
}

package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface ActiveDirectoryAuthenticatorMBean extends StandardInterface, DescriptorBean, LDAPAuthenticatorMBean {
   String getUserObjectClass();

   void setUserObjectClass(String var1) throws InvalidAttributeValueException;

   String getUserNameAttribute();

   void setUserNameAttribute(String var1) throws InvalidAttributeValueException;

   String getUserBaseDN();

   void setUserBaseDN(String var1) throws InvalidAttributeValueException;

   String getUserFromNameFilter();

   void setUserFromNameFilter(String var1) throws InvalidAttributeValueException;

   String getGroupBaseDN();

   void setGroupBaseDN(String var1) throws InvalidAttributeValueException;

   String getGroupFromNameFilter();

   void setGroupFromNameFilter(String var1) throws InvalidAttributeValueException;

   String getStaticGroupDNsfromMemberDNFilter();

   void setStaticGroupDNsfromMemberDNFilter(String var1) throws InvalidAttributeValueException;

   String getStaticGroupObjectClass();

   void setStaticGroupObjectClass(String var1) throws InvalidAttributeValueException;

   String getStaticMemberDNAttribute();

   void setStaticMemberDNAttribute(String var1) throws InvalidAttributeValueException;

   Boolean getUseTokenGroupsForGroupMembershipLookup();

   void setUseTokenGroupsForGroupMembershipLookup(Boolean var1) throws InvalidAttributeValueException;

   Boolean getEnableSIDtoGroupLookupCaching();

   void setEnableSIDtoGroupLookupCaching(Boolean var1) throws InvalidAttributeValueException;

   Integer getMaxSIDToGroupLookupsInCache();

   void setMaxSIDToGroupLookupsInCache(Integer var1) throws InvalidAttributeValueException;

   String getGuidAttribute();

   void setGuidAttribute(String var1) throws InvalidAttributeValueException;

   Boolean getRetrieveUserAccountControl();

   void setRetrieveUserAccountControl(Boolean var1) throws InvalidAttributeValueException;

   String getName();
}

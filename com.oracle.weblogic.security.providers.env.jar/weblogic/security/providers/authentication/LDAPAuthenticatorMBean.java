package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.GroupMemberListerMBean;
import weblogic.management.security.authentication.GroupMembershipHierarchyCacheMBean;
import weblogic.management.security.authentication.GroupReaderMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.MemberGroupListerMBean;
import weblogic.management.security.authentication.UserPasswordEditorMBean;
import weblogic.management.security.authentication.UserReaderMBean;
import weblogic.management.utils.LDAPServerMBean;

public interface LDAPAuthenticatorMBean extends StandardInterface, DescriptorBean, LoginExceptionPropagatorMBean, LDAPServerMBean, UserReaderMBean, GroupReaderMBean, GroupMemberListerMBean, MemberGroupListerMBean, UserPasswordEditorMBean, GroupMembershipHierarchyCacheMBean, IdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String getUserObjectClass();

   void setUserObjectClass(String var1) throws InvalidAttributeValueException;

   String getUserNameAttribute();

   void setUserNameAttribute(String var1) throws InvalidAttributeValueException;

   String getUserDynamicGroupDNAttribute();

   void setUserDynamicGroupDNAttribute(String var1) throws InvalidAttributeValueException;

   String getUserBaseDN();

   void setUserBaseDN(String var1) throws InvalidAttributeValueException;

   String getUserSearchScope();

   void setUserSearchScope(String var1) throws InvalidAttributeValueException;

   String getUserFromNameFilter();

   void setUserFromNameFilter(String var1) throws InvalidAttributeValueException;

   String getAllUsersFilter();

   void setAllUsersFilter(String var1) throws InvalidAttributeValueException;

   String getGroupBaseDN();

   void setGroupBaseDN(String var1) throws InvalidAttributeValueException;

   Boolean getMatchGroupBaseDN();

   void setMatchGroupBaseDN(Boolean var1) throws InvalidAttributeValueException;

   String getGroupSearchScope();

   void setGroupSearchScope(String var1) throws InvalidAttributeValueException;

   String getGroupFromNameFilter();

   void setGroupFromNameFilter(String var1) throws InvalidAttributeValueException;

   String getAllGroupsFilter();

   void setAllGroupsFilter(String var1) throws InvalidAttributeValueException;

   String getStaticGroupObjectClass();

   void setStaticGroupObjectClass(String var1) throws InvalidAttributeValueException;

   String getStaticGroupNameAttribute();

   void setStaticGroupNameAttribute(String var1) throws InvalidAttributeValueException;

   String getStaticMemberDNAttribute();

   void setStaticMemberDNAttribute(String var1) throws InvalidAttributeValueException;

   String getStaticGroupDNsfromMemberDNFilter();

   void setStaticGroupDNsfromMemberDNFilter(String var1) throws InvalidAttributeValueException;

   String getDynamicGroupObjectClass();

   void setDynamicGroupObjectClass(String var1) throws InvalidAttributeValueException;

   String getDynamicGroupNameAttribute();

   void setDynamicGroupNameAttribute(String var1) throws InvalidAttributeValueException;

   String getDynamicMemberURLAttribute();

   void setDynamicMemberURLAttribute(String var1) throws InvalidAttributeValueException;

   String getGroupMembershipSearching();

   void setGroupMembershipSearching(String var1) throws InvalidAttributeValueException;

   Integer getMaxGroupMembershipSearchLevel();

   void setMaxGroupMembershipSearchLevel(Integer var1) throws InvalidAttributeValueException;

   Boolean getUseRetrievedUserNameAsPrincipal();

   void setUseRetrievedUserNameAsPrincipal(Boolean var1) throws InvalidAttributeValueException;

   Boolean getRetrievePrincipalFromUserDN();

   void setRetrievePrincipalFromUserDN(Boolean var1) throws InvalidAttributeValueException;

   Boolean getIgnoreDuplicateMembership();

   void setIgnoreDuplicateMembership(Boolean var1) throws InvalidAttributeValueException;

   boolean isKeepAliveEnabled();

   void setKeepAliveEnabled(boolean var1) throws InvalidAttributeValueException;

   String getCredential();

   void setCredential(String var1) throws InvalidAttributeValueException;

   Boolean getEnableGroupMembershipLookupHierarchyCaching();

   void setEnableGroupMembershipLookupHierarchyCaching(Boolean var1) throws InvalidAttributeValueException;

   Boolean getEnableCacheStatistics();

   void setEnableCacheStatistics(Boolean var1) throws InvalidAttributeValueException;

   String getGuidAttribute();

   void setGuidAttribute(String var1) throws InvalidAttributeValueException;

   String getName();

   void setCredentialEncrypted(byte[] var1);

   byte[] getCredentialEncrypted();
}

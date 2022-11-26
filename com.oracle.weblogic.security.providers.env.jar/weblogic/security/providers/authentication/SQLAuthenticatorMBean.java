package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.GroupEditorMBean;
import weblogic.management.security.authentication.GroupMemberListerMBean;
import weblogic.management.security.authentication.MemberGroupListerMBean;
import weblogic.management.security.authentication.UserEditorMBean;
import weblogic.management.security.authentication.UserPasswordEditorMBean;

public interface SQLAuthenticatorMBean extends StandardInterface, DescriptorBean, ReadOnlySQLAuthenticatorMBean, GroupMemberListerMBean, MemberGroupListerMBean, UserPasswordEditorMBean, UserEditorMBean, GroupEditorMBean {
   String getProviderClassName();

   String getPasswordAlgorithm();

   void setPasswordAlgorithm(String var1) throws InvalidAttributeValueException;

   String getPasswordStyle();

   void setPasswordStyle(String var1) throws InvalidAttributeValueException;

   boolean isPasswordStyleRetained();

   void setPasswordStyleRetained(boolean var1) throws InvalidAttributeValueException;

   String getSQLCreateUser();

   void setSQLCreateUser(String var1) throws InvalidAttributeValueException;

   String getSQLRemoveUser();

   void setSQLRemoveUser(String var1) throws InvalidAttributeValueException;

   String getSQLRemoveGroupMemberships();

   void setSQLRemoveGroupMemberships(String var1) throws InvalidAttributeValueException;

   String getSQLSetUserDescription();

   void setSQLSetUserDescription(String var1) throws InvalidAttributeValueException;

   String getSQLSetUserPassword();

   void setSQLSetUserPassword(String var1) throws InvalidAttributeValueException;

   String getSQLCreateGroup();

   void setSQLCreateGroup(String var1) throws InvalidAttributeValueException;

   String getSQLSetGroupDescription();

   void setSQLSetGroupDescription(String var1) throws InvalidAttributeValueException;

   String getSQLAddMemberToGroup();

   void setSQLAddMemberToGroup(String var1) throws InvalidAttributeValueException;

   String getSQLRemoveMemberFromGroup();

   void setSQLRemoveMemberFromGroup(String var1) throws InvalidAttributeValueException;

   String getSQLRemoveGroup();

   void setSQLRemoveGroup(String var1) throws InvalidAttributeValueException;

   String getSQLRemoveGroupMember();

   void setSQLRemoveGroupMember(String var1) throws InvalidAttributeValueException;

   String getSQLListGroupMembers();

   void setSQLListGroupMembers(String var1) throws InvalidAttributeValueException;

   String getName();
}

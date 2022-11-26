package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.AuthenticatorMBean;
import weblogic.management.security.authentication.GroupMembershipHierarchyCacheMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;

public interface DBMSAuthenticatorMBean extends StandardInterface, DescriptorBean, AuthenticatorMBean, GroupMembershipHierarchyCacheMBean, IdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   String getDataSourceName();

   void setDataSourceName(String var1) throws InvalidAttributeValueException;

   String getGroupMembershipSearching();

   void setGroupMembershipSearching(String var1) throws InvalidAttributeValueException;

   Integer getMaxGroupMembershipSearchLevel();

   void setMaxGroupMembershipSearchLevel(Integer var1) throws InvalidAttributeValueException;

   boolean isPlaintextPasswordsEnabled();

   void setPlaintextPasswordsEnabled(boolean var1) throws InvalidAttributeValueException;

   String getName();
}

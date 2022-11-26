package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.authentication.GroupReaderMBean;
import weblogic.management.security.authentication.UserReaderMBean;

public interface ReadOnlySQLAuthenticatorMBean extends StandardInterface, DescriptorBean, DBMSAuthenticatorMBean, UserReaderMBean, GroupReaderMBean {
   String getProviderClassName();

   boolean getDescriptionsSupported();

   void setDescriptionsSupported(boolean var1) throws InvalidAttributeValueException;

   String getSQLGetUsersPassword();

   void setSQLGetUsersPassword(String var1) throws InvalidAttributeValueException;

   String getSQLUserExists();

   void setSQLUserExists(String var1) throws InvalidAttributeValueException;

   String getSQLListMemberGroups();

   void setSQLListMemberGroups(String var1) throws InvalidAttributeValueException;

   String getSQLListUsers();

   void setSQLListUsers(String var1) throws InvalidAttributeValueException;

   String getSQLGetUserDescription();

   void setSQLGetUserDescription(String var1) throws InvalidAttributeValueException;

   String getSQLListGroups();

   void setSQLListGroups(String var1) throws InvalidAttributeValueException;

   String getSQLGroupExists();

   void setSQLGroupExists(String var1) throws InvalidAttributeValueException;

   String getSQLIsMember();

   void setSQLIsMember(String var1) throws InvalidAttributeValueException;

   String getSQLGetGroupDescription();

   void setSQLGetGroupDescription(String var1) throws InvalidAttributeValueException;

   String getName();
}

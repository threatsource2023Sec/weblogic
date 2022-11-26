package weblogic.security.providers.authentication;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ExportMBean;
import weblogic.management.security.ImportMBean;
import weblogic.management.security.authentication.GroupEditorMBean;
import weblogic.management.security.authentication.GroupMemberListerMBean;
import weblogic.management.security.authentication.GroupMembershipHierarchyCacheMBean;
import weblogic.management.security.authentication.GroupUserListerMBean;
import weblogic.management.security.authentication.IdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.MemberGroupListerMBean;
import weblogic.management.security.authentication.MultiIdentityDomainAuthenticatorMBean;
import weblogic.management.security.authentication.UserAttributeEditorMBean;
import weblogic.management.security.authentication.UserEditorMBean;

public interface DefaultAuthenticatorMBean extends StandardInterface, DescriptorBean, LoginExceptionPropagatorMBean, UserEditorMBean, GroupEditorMBean, GroupMemberListerMBean, MemberGroupListerMBean, GroupUserListerMBean, ImportMBean, ExportMBean, GroupMembershipHierarchyCacheMBean, UserAttributeEditorMBean, IdentityDomainAuthenticatorMBean, MultiIdentityDomainAuthenticatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   int getMinimumPasswordLength();

   void setMinimumPasswordLength(int var1) throws InvalidAttributeValueException;

   String[] getSupportedImportFormats();

   String[] getSupportedImportConstraints();

   String[] getSupportedExportFormats();

   String[] getSupportedExportConstraints();

   String getGroupMembershipSearching();

   void setGroupMembershipSearching(String var1) throws InvalidAttributeValueException;

   Integer getMaxGroupMembershipSearchLevel();

   void setMaxGroupMembershipSearchLevel(Integer var1) throws InvalidAttributeValueException;

   Boolean getUseRetrievedUserNameAsPrincipal();

   void setUseRetrievedUserNameAsPrincipal(Boolean var1) throws InvalidAttributeValueException;

   boolean isPasswordDigestEnabled();

   void setPasswordDigestEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isKeepAliveEnabled();

   void setKeepAliveEnabled(boolean var1) throws InvalidAttributeValueException;

   Boolean getEnableGroupMembershipLookupHierarchyCaching();

   void setEnableGroupMembershipLookupHierarchyCaching(Boolean var1) throws InvalidAttributeValueException;

   String[] getIdentityDomains();

   void setIdentityDomains(String[] var1) throws InvalidAttributeValueException;

   boolean isNameCallbackAllowed();

   void setNameCallbackAllowed(boolean var1) throws InvalidAttributeValueException;

   String getName();
}

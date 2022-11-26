package weblogic.management.descriptors.webappext;

import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.XMLDeclarationMBean;

public interface WebAppExtDescriptorMBean extends WebElementMBean, XMLDeclarationMBean {
   void setDescription(String var1);

   String getDescription();

   void setWebLogicVersion(String var1);

   String getWebLogicVersion();

   SecurityRoleAssignmentMBean[] getSecurityRoleAssignments();

   void setSecurityRoleAssignments(SecurityRoleAssignmentMBean[] var1);

   void addSecurityRoleAssignment(SecurityRoleAssignmentMBean var1);

   void removeSecurityRoleAssignment(SecurityRoleAssignmentMBean var1);

   VirtualDirectoryMappingMBean[] getVirtualDirectoryMappings();

   void setVirtualDirectoryMappings(VirtualDirectoryMappingMBean[] var1);

   void addVirtualDirectoryMapping(VirtualDirectoryMappingMBean var1);

   void removeVirtualDirectoryMapping(VirtualDirectoryMappingMBean var1);

   void setReferenceDescriptor(ReferenceDescriptorMBean var1);

   ReferenceDescriptorMBean getReferenceDescriptor();

   void setSessionDescriptor(SessionDescriptorMBean var1);

   SessionDescriptorMBean getSessionDescriptor();

   void setJspDescriptor(JspDescriptorMBean var1);

   JspDescriptorMBean getJspDescriptor();

   void setAuthFilter(AuthFilterMBean var1);

   AuthFilterMBean getAuthFilter();

   void setURLMatchMap(URLMatchMapMBean var1);

   URLMatchMapMBean getURLMatchMap();

   void setContainerDescriptor(ContainerDescriptorMBean var1);

   ContainerDescriptorMBean getContainerDescriptor();

   void setCharsetParams(CharsetParamsMBean var1);

   CharsetParamsMBean getCharsetParams();

   void setPreprocessors(PreprocessorMBean[] var1);

   PreprocessorMBean[] getPreprocessors();

   void addPreprocessorMBean(PreprocessorMBean var1);

   void removePreprocessorMBean(PreprocessorMBean var1);

   void setPreprocessorMappings(PreprocessorMappingMBean[] var1);

   PreprocessorMappingMBean[] getPreprocessorMappings();

   void addPreprocessorMappingMBean(PreprocessorMappingMBean var1);

   void removePreprocessorMappingMBean(PreprocessorMappingMBean var1);

   void setSecurityPermissionMBean(SecurityPermissionMBean var1);

   SecurityPermissionMBean getSecurityPermissionMBean();

   void setContextRoot(String var1);

   String getContextRoot();

   void setDispatchPolicy(String var1);

   String getDispatchPolicy();
}

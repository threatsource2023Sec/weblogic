package weblogic.j2ee.descriptor.wl;

public interface ExternalResourceOverrideBean {
   String getResourceName();

   void setResourceName(String var1);

   String getResourceType();

   void setResourceType(String var1);

   String getRootElement();

   void setRootElement(String var1);

   String getDescriptorFilePath();

   void setDescriptorFilePath(String var1);

   VariableAssignmentBean[] getVariableAssignments();

   VariableAssignmentBean createVariableAssignment();

   void destroyVariableAssignment(VariableAssignmentBean var1);
}

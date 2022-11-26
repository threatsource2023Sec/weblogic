package weblogic.j2ee.descriptor.wl;

public interface ConfigResourceOverrideBean {
   String getResourceName();

   void setResourceName(String var1);

   String getResourceType();

   void setResourceType(String var1);

   VariableAssignmentBean[] getVariableAssignments();

   VariableAssignmentBean createVariableAssignment();

   void destroyVariableAssignment(VariableAssignmentBean var1);
}

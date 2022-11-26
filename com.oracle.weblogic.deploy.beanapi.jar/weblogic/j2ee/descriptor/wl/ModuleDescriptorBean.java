package weblogic.j2ee.descriptor.wl;

public interface ModuleDescriptorBean {
   String getRootElement();

   void setRootElement(String var1);

   String getUri();

   void setUri(String var1);

   VariableAssignmentBean[] getVariableAssignments();

   VariableAssignmentBean createVariableAssignment();

   void destroyVariableAssignment(VariableAssignmentBean var1);

   String getHashCode();

   void setHashCode(String var1);

   boolean isExternal();

   void setExternal(boolean var1);

   boolean isChanged();

   void setChanged(boolean var1);
}

package weblogic.j2ee.descriptor.wl;

public interface VariableDefinitionBean {
   VariableBean[] getVariables();

   VariableBean createVariable();

   void destroyVariable(VariableBean var1);
}

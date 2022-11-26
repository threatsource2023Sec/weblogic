package weblogic.j2ee.descriptor.wl;

public interface PortInfoBean {
   String getPortName();

   void setPortName(String var1);

   PropertyNamevalueBean[] getStubProperties();

   PropertyNamevalueBean createStubProperty();

   void destroyStubProperty(PropertyNamevalueBean var1);

   PropertyNamevalueBean[] getCallProperties();

   PropertyNamevalueBean createCallProperty();

   void destroyCallProperty(PropertyNamevalueBean var1);

   WSATConfigBean getWSATConfig();

   WSATConfigBean createWSATConfig();

   void destroyWSATConfig();

   OperationInfoBean createOperation();

   void destroyOperation(OperationInfoBean var1);

   OperationInfoBean[] getOperations();

   OperationInfoBean lookupOperation(String var1);

   OwsmPolicyBean[] getOwsmPolicy();

   OwsmPolicyBean createOwsmPolicy();

   void destroyOwsmPolicy(OwsmPolicyBean var1);
}

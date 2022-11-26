package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface OperationsMBean extends XMLElementMBean {
   OperationMBean[] getOperations();

   void setOperations(OperationMBean[] var1);

   void addOperation(OperationMBean var1);

   void removeOperation(OperationMBean var1);
}

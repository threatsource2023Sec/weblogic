package weblogic.management;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface PartitionRuntimeStateManagerContract {
   void setPartitionState(String var1, String var2, String... var3) throws ManagementException;

   void setResourceGroupState(String var1, String var2, String var3, String... var4) throws ManagementException;

   String getPartitionState(String var1);

   String getPartitionState(String var1, String var2);

   String getResourceGroupState(String var1, String var2, boolean var3);

   String getResourceGroupState(String var1, String var2, String var3, boolean var4);

   String getDefaultPartitionState();

   String getDefaultResourceGroupState();
}

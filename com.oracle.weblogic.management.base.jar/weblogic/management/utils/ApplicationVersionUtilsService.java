package weblogic.management.utils;

import java.util.HashMap;
import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.runtime.ApplicationRuntimeMBean;

@Contract
public interface ApplicationVersionUtilsService {
   String getPartitionName(String var1, PartitionMBean[] var2);

   String getApplicationName(String var1, PartitionMBean[] var2);

   String getVersionId(String var1, PartitionMBean[] var2);

   String getApplicationId(String var1, String var2);

   String getApplicationName(String var1);

   String getPartitionName(String var1);

   String getVersionId(String var1);

   String getCurrentVersionId(String var1);

   String getCurrentApplicationId();

   HashMap getDebugWorkContexts();

   void setCurrentVersionId(String var1, String var2);

   boolean isAdminModeRequest();

   String getBindApplicationId();

   String getDetailedInfoAboutVersionContext();

   String getCurrentVersionId();

   String getDisplayName(String var1, String var2);

   String getDisplayName(BasicDeploymentMBean var1);

   String getDisplayName(String var1);

   ApplicationRuntimeMBean getCurrentApplicationRuntime();

   void setBindApplicationId(String var1);

   void unsetBindApplicationId();
}

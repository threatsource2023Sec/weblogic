package weblogic.management.runtime;

import java.util.Properties;

public interface ScalingTaskRuntimeMBean extends TaskRuntimeMBean {
   String getClusterName();

   String getScalingType();

   boolean isSuccess();

   String[] getSelectedInstanceNames();

   String[] getScaledInstanceNames();

   Properties getSelectedInstanceMetadata();

   public static enum ScalingType {
      ScaleUp,
      ScaleDown;
   }
}

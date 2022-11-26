package weblogic.management;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import weblogic.management.deploy.internal.ComponentTarget;

/** @deprecated */
@Deprecated
public interface TargetAvailabilityStatus extends Serializable {
   int DEPLOYMENT_STATUS_NOTAVAILABLE = 0;
   int DEPLOYMENT_STATUS_AVAILABLE = 1;
   int DEPLOYMENT_STATUS_PARTIALLYAVAILABLE = 2;
   int AVAILABILITY_STATUS_NOTAVAILABLE = 0;
   int AVAILABILITY_STATUS_AVAILABLE = 1;
   int AVAILABILITY_STATUS_DISTRIBUTED = 2;
   int AVAILABILITY_STATUS_NOTDISTRIBUTED = 3;
   int TARGET_TYPE_SERVER = 1;
   int TARGET_TYPE_CLUSTER = 2;
   int TARGET_TYPE_VIRTUALHOST = 3;
   int TARGET_TYPE_VIRTUALTARGET = 4;

   String getTargetName();

   int getTargetType();

   int getDeploymentStatus();

   int getAvailabilityStatus();

   Set getServersAvailabilityStatus();

   Set getClustersAvailabilityStatus();

   void updateAvailabilityStatus(ComponentTarget var1);

   void updateUnavailabilityStatus(List var1);
}

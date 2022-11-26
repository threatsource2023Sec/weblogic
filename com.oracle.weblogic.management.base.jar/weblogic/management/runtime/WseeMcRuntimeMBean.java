package weblogic.management.runtime;

import java.util.List;
import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;

public interface WseeMcRuntimeMBean extends RuntimeMBean {
   List getAnonymousEndpointIds();

   CompositeData getAnonymousEndpointInfo(String var1) throws ManagementException;
}

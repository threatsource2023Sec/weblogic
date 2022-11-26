package weblogic.management.runtime;

import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;

public interface WseeWsrmRuntimeMBean extends RuntimeMBean {
   String[] getSequenceIds();

   CompositeData getSequenceInfo(String var1) throws ManagementException;
}

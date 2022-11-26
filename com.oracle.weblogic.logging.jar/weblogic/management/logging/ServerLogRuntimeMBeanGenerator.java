package weblogic.management.logging;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.ManagementException;
import weblogic.management.configuration.LogFileMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerLogRuntimeMBean;

@Contract
public interface ServerLogRuntimeMBeanGenerator {
   ServerLogRuntimeMBean createServerLogRuntimeMBean(LogFileMBean var1, RuntimeMBean var2) throws ManagementException;
}

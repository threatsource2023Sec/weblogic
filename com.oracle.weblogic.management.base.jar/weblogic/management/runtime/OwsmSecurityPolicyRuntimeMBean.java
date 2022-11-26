package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface OwsmSecurityPolicyRuntimeMBean extends RuntimeMBean {
   String[] getAvailablePolicies() throws ManagementException;
}

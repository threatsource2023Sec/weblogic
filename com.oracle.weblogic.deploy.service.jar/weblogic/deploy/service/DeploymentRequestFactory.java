package weblogic.deploy.service;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.ManagementException;

@Contract
public interface DeploymentRequestFactory {
   DeploymentRequest createDeploymentRequest() throws ManagementException;
}

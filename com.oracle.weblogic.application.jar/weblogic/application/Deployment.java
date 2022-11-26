package weblogic.application;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface Deployment extends WorkDeployment {
   ApplicationContext getApplicationContext();
}

package weblogic.deploy.service.internal.transport;

import org.jvnet.hk2.annotations.Contract;

@Contract
interface AdminServerMessageReceiver {
   void receivePrepareAckMsg(DeploymentServiceMessage var1);

   void receivePrepareNakMsg(DeploymentServiceMessage var1);

   void receiveGetDeploymentsMsg(DeploymentServiceMessage var1);

   DeploymentServiceMessage receiveBlockingGetDeploymentsMsg(DeploymentServiceMessage var1);

   void receiveCommitSucceededMsg(DeploymentServiceMessage var1);

   void receiveCommitFailedMsg(DeploymentServiceMessage var1);

   void receiveCancelSucceededMsg(DeploymentServiceMessage var1);

   void receiveCancelFailedMsg(DeploymentServiceMessage var1);

   void receiveStatusMsg(DeploymentServiceMessage var1);
}

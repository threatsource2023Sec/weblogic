package weblogic.deploy.service.internal.transport;

import java.io.Serializable;
import java.rmi.RemoteException;
import org.jvnet.hk2.annotations.Contract;
import weblogic.deploy.service.internal.DomainVersion;

@Contract
interface TargetServerMessageSender {
   void sendPrepareAckMsg(long var1, boolean var3) throws RemoteException;

   void sendPrepareNakMsg(long var1, Throwable var3) throws RemoteException;

   DeploymentServiceMessage sendBlockingGetDeploymentsMsg(DomainVersion var1, String var2) throws Exception;

   DeploymentServiceMessage sendBlockingGetDeploymentsMsgForPartition(DomainVersion var1, String var2, String var3) throws Exception;

   void sendGetDeploymentsMsg(DomainVersion var1, long var2);

   void sendCommitSucceededMsg(long var1, long var3);

   void sendCommitFailedMsg(long var1, Throwable var3) throws RemoteException;

   void sendCancelSucceededMsg(long var1);

   void sendCancelFailedMsg(long var1, Throwable var3);

   void sendStatusMsg(String var1, Serializable var2);

   void sendStatusMsg(long var1, String var3, Serializable var4);
}

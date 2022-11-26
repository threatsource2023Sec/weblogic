package weblogic.deploy.service.internal.transport;

import java.util.ArrayList;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;
import weblogic.deploy.service.internal.DomainVersion;
import weblogic.deploy.service.internal.adminserver.AdminRequestImpl;

@Contract
interface AdminServerMessageSender {
   void sendHeartbeatMsg(List var1);

   void sendRequestPrepareMsg(AdminRequestImpl var1);

   void sendRequestCommitMsg(AdminRequestImpl var1);

   void sendRequestCancelMsg(AdminRequestImpl var1, Throwable var2);

   void sendGetDeploymentsResponse(ArrayList var1, String var2, DomainVersion var3, long var4);
}

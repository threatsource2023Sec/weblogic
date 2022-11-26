package weblogic.deploy.service.internal.targetserver;

import java.util.Iterator;
import java.util.Map;
import weblogic.application.utils.XMLWriter;
import weblogic.deploy.internal.diagnostics.ImageProvider;

public class TargetDeploymentServiceImageProvider extends ImageProvider {
   public void writeDiagnosticImage(XMLWriter writer) {
      TargetRequestManager targReqManager = TargetRequestManager.getInstance();
      Iterator iter = targReqManager.getRequests().iterator();

      while(iter.hasNext() && !this.timedOut) {
         TargetRequestImpl targetRequest = (TargetRequestImpl)((Map.Entry)iter.next()).getValue();
         TargetRequestStatus targetStatus = targetRequest.getDeploymentStatus();
         writer.addElement("target-deployment-service-request");
         writer.addElement("id", "" + targetStatus.getId());
         writer.addElement("state", targetStatus.getCurrentState().toString());
         if (targetStatus.isTimedOut()) {
            writer.addElement("timed-out", "true");
         }

         writer.closeElement();
         writer.flush();
      }

   }
}

package weblogic.deploy.service.internal.adminserver;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.application.utils.XMLWriter;
import weblogic.deploy.internal.diagnostics.ImageProvider;
import weblogic.deploy.service.FailureDescription;

public class AdminDeploymentServiceImageProvider extends ImageProvider {
   public void writeDiagnosticImage(XMLWriter writer) {
      if (isAdminServer) {
         AdminRequestManager adminReqManager = AdminRequestManager.getInstance();
         Iterator iter = adminReqManager.getRequests().iterator();

         while(iter.hasNext() && !this.timedOut) {
            AdminRequestImpl adminRequest = (AdminRequestImpl)((Map.Entry)iter.next()).getValue();
            AdminRequestStatus adminStatus = adminRequest.getStatus();
            writer.addElement("admin-deployment-service-request");
            writer.addElement("id", "" + adminStatus.getId());
            writer.addElement("current-state", adminStatus.getCurrentState().toString());
            this.writeCollection(writer, adminStatus.getTargetedServers(), "targets");
            this.writePrepareDeliveryFailed(writer, adminStatus);
            this.writeRespondPrepareTargets(writer, adminStatus);
            this.writeRestartTargets(writer, adminStatus);
            this.writeCommittedTargets(writer, adminStatus);
            this.writeCommitDeliveryFailed(writer, adminStatus);
            this.writeRespondCommitTargets(writer, adminStatus);
            this.writeCommitFailures(writer, adminStatus);
            this.writeCancelTargets(writer, adminStatus);
            this.writeRespondCancelTargets(writer, adminStatus);
            this.writeCancelFailures(writer, adminStatus);
            this.writePrepareFailure(writer, adminStatus);
            if (adminStatus.timedOut()) {
               writer.addElement("timed-out", "true");
            }

            if (adminStatus.isCancelledByUser()) {
               writer.addElement("cancelled-by", "user / administrator");
            }

            if (adminStatus.isCancelledByClusterConstraints()) {
               writer.addElement("cancelled-by", "cluster-constraints-enabled");
            }

            writer.closeElement();
            writer.flush();
         }

      }
   }

   private void writePrepareDeliveryFailed(XMLWriter writer, AdminRequestStatus adminStatus) {
      Map prepareDeliveryFailureTargets = adminStatus.getPrepareDeliveryFailureTargets();
      if (!prepareDeliveryFailureTargets.isEmpty()) {
         this.writeCollection(writer, prepareDeliveryFailureTargets.keySet().iterator(), "prepare-failed-to-be-delivered-to");
      }

   }

   private void writeRespondPrepareTargets(XMLWriter writer, AdminRequestStatus adminStatus) {
      Set targetsToRespondToPrepare = adminStatus.getTargetsToRespondToPrepare();
      if (!targetsToRespondToPrepare.isEmpty()) {
         this.writeCollection(writer, targetsToRespondToPrepare.iterator(), "targets-to-respond-to-prepare");
      }

   }

   private void writeRestartTargets(XMLWriter writer, AdminRequestStatus adminStatus) {
      Set targetsToBeRestarted = adminStatus.getTargetsToBeRestarted();
      if (!targetsToBeRestarted.isEmpty()) {
         this.writeCollection(writer, targetsToBeRestarted.iterator(), "targets-to-be-restarted");
      }

   }

   private void writeCommittedTargets(XMLWriter writer, AdminRequestStatus adminStatus) {
      Set targetsToBeCommited = adminStatus.getTargetsToBeCommited();
      if (!targetsToBeCommited.isEmpty()) {
         this.writeCollection(writer, targetsToBeCommited.iterator(), "targets-that-are-to-be-commited");
      }

   }

   private void writeCommitDeliveryFailed(XMLWriter writer, AdminRequestStatus adminStatus) {
      Map commitDeliveryFailureTargets = adminStatus.getCommitDeliveryFailureTargets();
      if (!commitDeliveryFailureTargets.isEmpty()) {
         this.writeCollection(writer, commitDeliveryFailureTargets.keySet().iterator(), "commit-failed-to-be-delivered-to");
      }

   }

   private void writeRespondCommitTargets(XMLWriter writer, AdminRequestStatus adminStatus) {
      Set targetsToRespondToCommit = adminStatus.getTargetsToRespondToCommit();
      if (!targetsToRespondToCommit.isEmpty()) {
         this.writeCollection(writer, targetsToRespondToCommit.iterator(), "targets-to-respond-to-commit");
      }

   }

   private void writeCommitFailures(XMLWriter writer, AdminRequestStatus adminStatus) {
      Set targetsToBeCanceled = adminStatus.getTargetsToBeCanceled();
      if (!targetsToBeCanceled.isEmpty()) {
         this.writeCollection(writer, targetsToBeCanceled.iterator(), "targets-to-be-canceled");
      }

   }

   private void writeCancelTargets(XMLWriter writer, AdminRequestStatus adminStatus) {
      Map cancelDeliveryFailureTargets = adminStatus.getCancelDeliveryFailureTargets();
      if (!cancelDeliveryFailureTargets.isEmpty()) {
         this.writeCollection(writer, cancelDeliveryFailureTargets.keySet().iterator(), "cancel-failed-to-be-delivered-to");
      }

   }

   private void writeRespondCancelTargets(XMLWriter writer, AdminRequestStatus adminStatus) {
      Set targetsToRespondToCancel = adminStatus.getTargetsToRespondToCancel();
      if (!targetsToRespondToCancel.isEmpty()) {
         this.writeCollection(writer, targetsToRespondToCancel.iterator(), "targets-to-respond-to-cancel");
      }

   }

   private void writeCancelFailures(XMLWriter writer, AdminRequestStatus adminStatus) {
      Set cancelFailures = adminStatus.getCancelFailureSet();
      if (!cancelFailures.isEmpty()) {
         Iterator iter = cancelFailures.iterator();
         StringBuffer sb = new StringBuffer();

         while(iter.hasNext()) {
            FailureDescription fd = (FailureDescription)iter.next();
            sb.append(fd.getServer());
            if (iter.hasNext()) {
               sb.append(", ");
            }
         }

         writer.addElement("targets-cancel-failed-on", sb.toString());
      }
   }

   private void writePrepareFailure(XMLWriter writer, AdminRequestStatus adminStatus) {
      Throwable prepareFailure = adminStatus.getPrepareFailure();
      if (prepareFailure != null) {
         writer.addElement("prepare-failure");
         writer.addElement("message", prepareFailure.toString());
         writer.addElement("target", adminStatus.getPrepareFailureSource());
      }

   }
}

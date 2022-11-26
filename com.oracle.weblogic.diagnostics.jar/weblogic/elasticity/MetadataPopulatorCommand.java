package weblogic.elasticity;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.watch.actions.ClusterInfo;
import weblogic.diagnostics.watch.actions.ClusterMember;
import weblogic.elasticity.i18n.ElasticityTextTextFormatter;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.workflow.command.CommandInterface;
import weblogic.management.workflow.command.SharedState;
import weblogic.management.workflow.command.WorkflowContext;
import weblogic.server.GlobalServiceLocator;

public class MetadataPopulatorCommand implements CommandInterface {
   private static final ElasticityTextTextFormatter txtFmt = ElasticityTextTextFormatter.getInstance();
   @SharedState(
      name = "InterceptorSharedDataConstants_workflow_shared_data_map_key"
   )
   private Map sharedMap;
   private final String clusterName;
   private final String operationName;
   private final int scalingSize;

   public MetadataPopulatorCommand(String clusterName, String operationName, int scalingSize) {
      this.clusterName = clusterName;
      this.operationName = operationName;
      this.scalingSize = scalingSize;
   }

   public void initialize(WorkflowContext context) {
   }

   public boolean execute() throws Exception {
      boolean canProceed = true;
      if (this.operationName.equals("scaleUp") || this.operationName.equals("scaleDown")) {
         if (ClusterInfo.getClusterInfo(this.clusterName) == null) {
            throw new IllegalArgumentException(txtFmt.getMetadataPopulatorInvalidClusterNameText(this.clusterName));
         }

         ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
         WLSDynamicClusterScalingService wlsDynamicClusterScalingService = (WLSDynamicClusterScalingService)serviceLocator.getService(WLSDynamicClusterScalingService.class, new Annotation[0]);
         DynamicServersMBean dynamicServersMBean = wlsDynamicClusterScalingService.getDynamicServersMBean(this.clusterName);
         ScalingOperationStatus operationStatus = (ScalingOperationStatus)this.sharedMap.get("InterceptorSharedDataConstants_elasticity_scaling_operation_status_key");
         canProceed = this.populateMetadata(dynamicServersMBean, operationStatus);
      }

      return canProceed;
   }

   private boolean populateMetadata(DynamicServersMBean dynamicServersMBean, ScalingOperationStatus operationStatus) throws Exception {
      ClusterInfo currentClusterInfo = ClusterInfo.getClusterInfo(this.clusterName);
      if (currentClusterInfo == null) {
         throw new IllegalArgumentException(txtFmt.getMetadataPopulatorInvalidClusterNameText(this.clusterName));
      } else {
         operationStatus.getInstanceMetadata().put("metadata.keys", new String[]{"name", "listenAddress", "listenPort"});
         Iterator var4 = operationStatus.getCandidateMemberNames().iterator();

         while(var4.hasNext()) {
            String name = (String)var4.next();
            ClusterMember m = currentClusterInfo.getMember(name);
            if (m != null) {
               Properties props = new Properties();
               props.put("name", name);
               props.put("listenAddress", "" + m.getListenAddr());
               props.put("listenPort", "" + m.getListenPort());
               operationStatus.getInstanceMetadata().put(name, props);
            }
         }

         return true;
      }
   }
}

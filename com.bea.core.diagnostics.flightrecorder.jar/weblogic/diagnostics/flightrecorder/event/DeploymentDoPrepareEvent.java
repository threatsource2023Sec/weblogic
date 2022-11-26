package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Deployment Do Prepare",
   description = "This event covers the deployment operation do prepare",
   path = "wls/Deployment/Deployment_Do_Prepare",
   thread = true
)
public class DeploymentDoPrepareEvent extends DeploymentBaseInstantEvent {
}

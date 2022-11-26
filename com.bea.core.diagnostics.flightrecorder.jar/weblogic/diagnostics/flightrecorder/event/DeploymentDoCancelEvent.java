package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;

@EventDefinition(
   name = "Deployment Do Cancel",
   description = "This event covers the cancellation of a deployment operation",
   path = "wls/Deployment/Deployment_Do_Cancel",
   thread = true
)
public class DeploymentDoCancelEvent extends DeploymentBaseInstantEvent {
}

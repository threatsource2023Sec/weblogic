package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Deployment Do Cancel")
@Name("com.oracle.weblogic.deployment.DeploymentDoCancelEvent")
@Description("This event covers the cancellation of a deployment operation")
@Category({"WebLogic Server", "Deployment"})
public class DeploymentDoCancelEvent extends DeploymentBaseEvent {
}

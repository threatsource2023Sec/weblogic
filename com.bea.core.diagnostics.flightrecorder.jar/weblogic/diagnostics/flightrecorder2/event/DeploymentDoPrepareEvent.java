package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;

@Label("Deployment Do Prepare")
@Name("com.oracle.weblogic.deployment.DeploymentDoPrepareEvent")
@Description("This event covers the deployment operation do prepare")
@Category({"WebLogic Server", "Deployment"})
public class DeploymentDoPrepareEvent extends DeploymentBaseEvent {
}

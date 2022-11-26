package weblogic.application.internal.flow;

import weblogic.application.Module;
import weblogic.application.internal.FlowContext;
import weblogic.management.DeploymentException;

public interface ModulesCreator {
   Module[] create(FlowContext var1) throws DeploymentException;
}

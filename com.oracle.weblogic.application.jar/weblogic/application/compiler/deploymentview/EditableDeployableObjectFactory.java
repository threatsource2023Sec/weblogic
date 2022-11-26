package weblogic.application.compiler.deploymentview;

import java.io.IOException;
import javax.enterprise.deploy.shared.ModuleType;

public interface EditableDeployableObjectFactory {
   EditableJ2eeApplicationObject createApplicationObject() throws IOException;

   EditableScaApplicationObject createScaApplicationObject() throws IOException;

   EditableDeployableObject createDeployableObject(String var1, String var2, ModuleType var3) throws IOException;
}

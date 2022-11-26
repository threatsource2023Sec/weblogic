package weblogic.jaxrs.server.extension;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class JaxRsServerService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      ApplicationFactoryManager.getApplicationFactoryManager().addModuleExtensionFactory("war", new JaxRsModuleExtensionFactory());
      ToolsFactoryManager.addModuleExtensionFactory("war", new JaxRsToolsModuleExtensionFactory());
   }
}

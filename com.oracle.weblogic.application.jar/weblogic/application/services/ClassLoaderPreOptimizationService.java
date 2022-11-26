package weblogic.application.services;

import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.classloaders.GenericClassLoader;

@Service
@Named
@RunLevel(5)
public class ClassLoaderPreOptimizationService extends AbstractServerService {
   public void start() throws ServiceFailureException {
      GenericClassLoader.setOptimizedEnvironment();
   }
}

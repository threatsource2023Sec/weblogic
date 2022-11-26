package weblogic.t3.srvr;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.runlevel.RunLevel;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.server.AbstractServerService;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class FinalThreadLocalService extends AbstractServerService {
   @Inject
   @Named("PreConfigBootService")
   private ServerService dependencyOnPreConfigBootService;
   @Inject
   @Named("RuntimeAccessService")
   private ServerService dependencyOnRuntimeAccessService;
   private final DebugLogger debugSLCWLDF = DebugLogger.getDebugLogger("DebugServerLifeCycle");

   public void start() throws ServiceFailureException {
      List allServices = GlobalServiceLocator.getServiceLocator().getDescriptors(BuilderHelper.createContractFilter(FastThreadLocalMarker.class.getName()));

      ActiveDescriptor service;
      for(Iterator var2 = allServices.iterator(); var2.hasNext(); this.debugSLCWLDF.debug("ClassLoaded FastThreadLocal " + service.getImplementation())) {
         service = (ActiveDescriptor)var2.next();

         try {
            Class sClass = service.getLoader().loadClass(service.getImplementation());
            Class.forName(service.getImplementation(), true, sClass.getClassLoader());
         } catch (ExceptionInInitializerError var5) {
            throw new ServiceFailureException(var5);
         } catch (ClassNotFoundException var6) {
            throw new ServiceFailureException(var6);
         }
      }

   }
}

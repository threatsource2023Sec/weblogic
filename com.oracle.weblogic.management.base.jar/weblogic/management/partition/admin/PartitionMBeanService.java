package weblogic.management.partition.admin;

import javax.inject.Inject;
import javax.inject.Named;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;

@Service
@Named
public class PartitionMBeanService extends AbstractServerService {
   @Inject
   @Named("ConcurrentManagedObjectDeploymentService")
   private ServerService dependencyOnNamingService;
}

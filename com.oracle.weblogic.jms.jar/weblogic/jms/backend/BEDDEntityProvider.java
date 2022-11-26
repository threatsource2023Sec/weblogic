package weblogic.jms.backend;

import java.util.List;
import javax.naming.Context;
import weblogic.application.ApplicationContext;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.DistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.jms.common.EntityName;
import weblogic.jms.dd.DistributedDestination;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.jms.module.JMSModuleManagedEntityProvider;
import weblogic.management.configuration.DomainMBean;

public class BEDDEntityProvider implements JMSModuleManagedEntityProvider {
   public JMSModuleManagedEntity createEntity(ApplicationContext appCtx, EntityName entityName, Context namingContext, JMSBean wholeModule, NamedEntityBean specificBean, List localTargets, DomainMBean proposedDomain, boolean isJMSResourceDefinition) throws ModuleException {
      String name = entityName.toString();
      DistributedDestinationBean ddBean = (DistributedDestinationBean)specificBean;
      DistributedDestination dd = new DistributedDestination(name, wholeModule, ddBean, appCtx.getAppDeploymentMBean(), entityName.getEARModuleName(), entityName.getFullyQualifiedModuleName(), appCtx);
      return dd;
   }
}

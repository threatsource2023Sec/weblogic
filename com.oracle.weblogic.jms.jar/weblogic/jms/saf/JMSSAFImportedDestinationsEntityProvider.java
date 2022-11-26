package weblogic.jms.saf;

import java.util.List;
import javax.naming.Context;
import weblogic.application.ApplicationContext;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.jms.common.EntityName;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.jms.module.JMSModuleManagedEntityProvider;
import weblogic.management.configuration.DomainMBean;

public class JMSSAFImportedDestinationsEntityProvider implements JMSModuleManagedEntityProvider {
   public JMSModuleManagedEntity createEntity(ApplicationContext appCtx, EntityName entityName, Context namingContext, JMSBean wholeModule, NamedEntityBean specificBean, List localTargets, DomainMBean proposedDomain, boolean isJMSResourceDefinition) throws ModuleException {
      String name = entityName.toString();
      SAFImportedDestinationsBean safImportedDestinationsBean = (SAFImportedDestinationsBean)specificBean;
      return new ImportedDestinationGroup(name, appCtx, entityName, safImportedDestinationsBean, localTargets, proposedDomain);
   }
}

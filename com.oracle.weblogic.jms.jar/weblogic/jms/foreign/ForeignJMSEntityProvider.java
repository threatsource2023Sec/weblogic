package weblogic.jms.foreign;

import java.util.List;
import javax.naming.Context;
import weblogic.application.ApplicationContext;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.jms.common.EntityName;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.jms.module.JMSModuleManagedEntityProvider;
import weblogic.management.configuration.DomainMBean;

public class ForeignJMSEntityProvider implements JMSModuleManagedEntityProvider {
   public JMSModuleManagedEntity createEntity(ApplicationContext appCtx, EntityName entityName, Context namingContext, JMSBean wholeModule, NamedEntityBean specificBean, List localTargets, DomainMBean proposedDomain, boolean isJMSResourceDefinition) throws ModuleException {
      ForeignServerBean fsb = (ForeignServerBean)specificBean;
      return new ForeignJMSImpl(fsb, entityName.getFullyQualifiedModuleName());
   }
}

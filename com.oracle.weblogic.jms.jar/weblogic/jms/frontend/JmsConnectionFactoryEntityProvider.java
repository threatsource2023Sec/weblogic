package weblogic.jms.frontend;

import java.util.List;
import javax.naming.Context;
import weblogic.application.ApplicationContext;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.jms.JMSService;
import weblogic.jms.common.EntityName;
import weblogic.jms.module.JMSModuleManagedEntity;
import weblogic.jms.module.JMSModuleManagedEntityProvider;
import weblogic.management.configuration.DomainMBean;

public class JmsConnectionFactoryEntityProvider implements JMSModuleManagedEntityProvider {
   public JMSModuleManagedEntity createEntity(ApplicationContext appCtx, EntityName entityName, Context namingContext, JMSBean wholeModule, NamedEntityBean specificBean, List localTargets, DomainMBean proposedDomain, boolean isJMSResourceDefinition) throws ModuleException {
      if ("WebLogic_Debug_CON_fail_init".equals(specificBean.getName())) {
         throw new ModuleException("DEBUG: A connection factory with name WebLogic_Debug_CON_fail_init will force the initializer to fail");
      } else {
         JMSConnectionFactoryBean jmsConnectionFactoryBean = (JMSConnectionFactoryBean)specificBean;
         FEConnectionFactory fecf = new FEConnectionFactory(JMSService.getJMSServiceWithModuleException().getFrontEnd(), jmsConnectionFactoryBean, entityName.getEARModuleName(), entityName.getFullyQualifiedModuleName(), namingContext, isJMSResourceDefinition, appCtx);
         JMSModuleManagedEntity cf = new FEConnectionFactoryDelegate(fecf);
         return cf;
      }
   }
}

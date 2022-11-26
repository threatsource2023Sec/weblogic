package weblogic.jms.module;

import java.util.List;
import javax.naming.Context;
import weblogic.application.ApplicationContext;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.jms.common.EntityName;
import weblogic.management.configuration.DomainMBean;

public interface JMSModuleManagedEntityProvider {
   JMSModuleManagedEntity createEntity(ApplicationContext var1, EntityName var2, Context var3, JMSBean var4, NamedEntityBean var5, List var6, DomainMBean var7, boolean var8) throws ModuleException;
}

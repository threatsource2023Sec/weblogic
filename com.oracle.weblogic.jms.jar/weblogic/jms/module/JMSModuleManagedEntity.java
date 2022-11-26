package weblogic.jms.module;

import java.util.List;
import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.management.configuration.DomainMBean;

public interface JMSModuleManagedEntity {
   void prepare() throws ModuleException;

   void activate(JMSBean var1) throws ModuleException;

   void deactivate() throws ModuleException;

   void unprepare() throws ModuleException;

   void destroy() throws ModuleException;

   void remove() throws ModuleException;

   String getEntityName();

   void prepareChangeOfTargets(List var1, DomainMBean var2) throws ModuleException;

   void activateChangeOfTargets() throws ModuleException;

   void rollbackChangeOfTargets();
}

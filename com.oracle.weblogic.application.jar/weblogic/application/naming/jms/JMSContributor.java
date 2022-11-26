package weblogic.application.naming.jms;

import java.util.Set;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.naming.Environment;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;

public interface JMSContributor {
   void bindJMSResourceDefinitions(ApplicationContextInternal var1, J2eeClientEnvironmentBean var2, Set var3, Set var4, String var5, String var6, String var7, String var8, Environment.EnvType var9) throws ResourceException;

   void unbindJMSResourceDefinitions(ApplicationContextInternal var1) throws ResourceException;
}

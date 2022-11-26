package weblogic.jms.module;

import javax.naming.Context;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.naming.jms.JMSContributor;
import weblogic.application.naming.jms.JMSContributorFactory;

@Service
public class JMSContributorFactoryImpl implements JMSContributorFactory {
   public JMSContributor get(Context javaGlobalCtx, Context javaAppContext, Context javaModuleCtx, Context javaCompCtx) {
      return new JMSContributorImpl(javaGlobalCtx, javaAppContext, javaModuleCtx, javaCompCtx);
   }
}

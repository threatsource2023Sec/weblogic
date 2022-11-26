package weblogic.deployment.jms;

import java.util.Properties;
import weblogic.common.resourcepool.PooledResourceFactory;
import weblogic.common.resourcepool.ResourcePoolImpl;

class JMSResourcePoolImpl extends ResourcePoolImpl {
   private JMSSessionPool pool;

   protected JMSResourcePoolImpl(JMSSessionPool pool) {
      this.pool = pool;
   }

   public PooledResourceFactory initPooledResourceFactory(Properties initInfo) {
      return this.pool;
   }
}

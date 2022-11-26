package weblogic.ejb.container.internal;

import weblogic.ejb.container.interfaces.BeanManager;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.spi.BaseWSObjectIntf;
import weblogic.ejb.spi.WSObjectFactory;
import weblogic.utils.Debug;

public class WSObjectFactoryImpl implements WSObjectFactory {
   private final Class wsoClass;
   private final BeanManager beanManager;
   private final ClientDrivenBeanInfo cdBeanInfo;

   public WSObjectFactoryImpl(BeanManager bm, ClientDrivenBeanInfo cdbi) {
      this.beanManager = bm;
      this.cdBeanInfo = cdbi;
      this.wsoClass = this.cdBeanInfo.getWebserviceObjectClass();
   }

   public BaseWSObjectIntf create() {
      Debug.assertion(this.wsoClass != null, "webservice object class is NULL !");
      BaseWSLocalObject wso = null;

      try {
         wso = (BaseWSLocalObject)this.wsoClass.newInstance();
      } catch (Exception var3) {
         throw new AssertionError("Exception attempting to create new webservice object class '" + this.wsoClass.getName() + "'  " + var3.getMessage());
      }

      wso.setBeanManager(this.beanManager);
      wso.setBeanInfo(this.cdBeanInfo);
      return wso;
   }
}

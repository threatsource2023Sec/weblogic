package weblogic.buzzmessagebus;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Named
@Service
@RunLevel(20)
public final class BuzzService extends AbstractServerService {
   @Inject
   @Named("BuzzHTTPFactoryImpl")
   private Provider factoryProvider;
   @Inject
   @Named("BuzzEndPointMBeanInfo")
   private Provider infoProvider;
   private volatile boolean buzzEnabled = false;

   public void start() throws ServiceFailureException {
      BuzzMessageBusEndPointMBeanInfo info = (BuzzMessageBusEndPointMBeanInfo)this.infoProvider.get();
      if (info.isBuzzEnabled()) {
         BuzzController.getInstance().init((BuzzHTTPFactory)this.factoryProvider.get(), info);
         this.buzzEnabled = true;
      }

   }

   public void stop() throws ServiceFailureException {
      if (this.buzzEnabled) {
         BuzzController.getInstance().close();
         this.buzzEnabled = false;
      }

   }

   public void halt() throws ServiceFailureException {
      this.stop();
   }
}

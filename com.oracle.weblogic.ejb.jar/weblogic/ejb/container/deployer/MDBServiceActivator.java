package weblogic.ejb.container.deployer;

import java.lang.annotation.Annotation;
import javax.inject.Named;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.ejb.container.EJBServiceActivator;
import weblogic.server.GlobalServiceLocator;

@Service
@Named
@RunLevel(20)
public class MDBServiceActivator extends EJBServiceActivator {
   private static final String SERVICE_CLASS = "weblogic.ejb.container.deployer.MDBServiceImpl";
   private static MDBServiceActivator INSTANCE;

   public static synchronized MDBServiceActivator getInstance() {
      if (INSTANCE == null) {
         INSTANCE = new MDBServiceActivator();
      }

      return INSTANCE;
   }

   private MDBServiceActivator() {
      super("weblogic.ejb.container.deployer.MDBServiceImpl");
      this.setServiceAvailableBeforeStart(true);
   }

   public String getName() {
      return "MDBService";
   }

   MDBService getMDBService() {
      return (MDBService)this.getServiceObj();
   }

   public static class MDBServiceLocator {
      public static MDBService getMDBService() {
         try {
            ServiceLocator sl = GlobalServiceLocator.getServiceLocator();
            return (MDBService)sl.getService(Class.forName("weblogic.ejb.container.deployer.MDBServiceImpl"), new Annotation[0]);
         } catch (IllegalStateException var1) {
            return MDBServiceActivator.getInstance().getMDBService();
         } catch (ClassNotFoundException var2) {
            throw new RuntimeException(var2);
         }
      }
   }
}

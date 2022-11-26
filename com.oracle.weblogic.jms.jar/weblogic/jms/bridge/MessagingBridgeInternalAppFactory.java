package weblogic.jms.bridge;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jvnet.hk2.annotations.Service;
import weblogic.Home;
import weblogic.deploy.api.spi.deploy.internal.InternalApp;
import weblogic.deploy.api.spi.deploy.internal.InternalAppFactory;

@Service
public class MessagingBridgeInternalAppFactory implements InternalAppFactory {
   public List createInternalApps() {
      String location = Home.getMiddlewareHomePath() + File.separator + "wlserver" + File.separator + "modules" + File.separator + "internal";
      List internalApps = new ArrayList();
      internalApps.add(new InternalApp("jms-internal-xa-adp", ".rar", false, false, false, true, (String[])null, false, location, true, false));
      internalApps.add(new InternalApp("jms-internal-notran-adp", ".rar", false, false, false, true, (String[])null, false, location, true, false));
      return internalApps;
   }
}

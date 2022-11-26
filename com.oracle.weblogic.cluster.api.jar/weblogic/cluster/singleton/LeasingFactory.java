package weblogic.cluster.singleton;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface LeasingFactory {
   Leasing createLeasingService(String var1, LeasingBasis var2, int var3, int var4, int var5) throws IllegalArgumentException;

   Leasing findOrCreateLeasingService(String var1);

   Leasing findOrCreateTimerLeasingService() throws LeasingException;

   public static class Locator {
      public static LeasingFactory locateService() {
         return (LeasingFactory)GlobalServiceLocator.getServiceLocator().getService(LeasingFactory.class, new Annotation[0]);
      }
   }
}

package weblogic.common;

import java.lang.annotation.Annotation;
import weblogic.server.GlobalServiceLocator;
import weblogic.time.common.TimeServices;
import weblogic.time.common.TimeServicesDef;

final class T3ClientServices implements T3ServicesDef {
   T3Client t3;
   private TimeServicesDef timeSvc;

   public T3ClientServices(T3Client t3) {
      this.t3 = t3;
   }

   public TimeServicesDef time() {
      if (this.timeSvc != null) {
         return this.timeSvc;
      } else {
         TimeServices svc = (TimeServices)GlobalServiceLocator.getServiceLocator().getService(TimeServices.class, new Annotation[0]);
         svc.setT3Client(this.t3);
         svc.setT3ServicesDef(this);
         this.timeSvc = (TimeServicesDef)svc;
         return this.timeSvc;
      }
   }
}

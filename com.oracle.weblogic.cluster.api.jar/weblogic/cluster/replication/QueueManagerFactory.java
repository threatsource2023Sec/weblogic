package weblogic.cluster.replication;

import java.lang.annotation.Annotation;
import org.jvnet.hk2.annotations.Contract;
import weblogic.server.GlobalServiceLocator;

@Contract
public interface QueueManagerFactory {
   QueueManager newAsyncQueue(AsyncFlush var1, boolean var2, int var3, int var4, int var5);

   QueueManager newAsyncQueue(AsyncFlush var1, boolean var2);

   public static class Locator {
      public static QueueManagerFactory locate() {
         return (QueueManagerFactory)GlobalServiceLocator.getServiceLocator().getService(QueueManagerFactory.class, new Annotation[0]);
      }
   }
}

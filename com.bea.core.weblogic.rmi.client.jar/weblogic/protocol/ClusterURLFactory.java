package weblogic.protocol;

import java.net.MalformedURLException;

public class ClusterURLFactory {
   private static ClusterURL _instance = null;
   private static String CLUSTERURL_IMPL = "weblogic.protocol.ClusterURLImpl";

   public static ClusterURL getInstance() {
      return _instance;
   }

   static {
      try {
         Class cl = Class.forName(CLUSTERURL_IMPL);
         _instance = (ClusterURL)cl.newInstance();
      } catch (Exception var1) {
         _instance = new NoOpClusterURLImpl();
      }

   }

   public static class NoOpClusterURLImpl implements ClusterURL {
      public String parseClusterURL(String url) throws MalformedURLException {
         return url;
      }
   }
}

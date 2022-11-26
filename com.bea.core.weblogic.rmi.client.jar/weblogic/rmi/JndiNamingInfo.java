package weblogic.rmi;

import javax.naming.NamingException;
import weblogic.protocol.ServerURL;

public class JndiNamingInfo extends JndiInfo {
   private String path;

   JndiNamingInfo(String providerUrl, String partitionName, ServerURL url, String path) throws NamingException {
      super(providerUrl, partitionName, url);
      this.path = path;
   }

   JndiNamingInfo(ProviderUrlInfo providerInfo, ServerURL url, String path) {
      super(providerInfo, url);
      this.path = path;
   }

   public String getPath() {
      return this.path;
   }
}

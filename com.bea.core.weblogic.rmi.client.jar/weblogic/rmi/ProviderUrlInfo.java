package weblogic.rmi;

import java.net.MalformedURLException;
import javax.naming.NamingException;
import weblogic.protocol.ServerURL;
import weblogic.rmi.registry.LocateRegistry;

public class ProviderUrlInfo extends JndiInfo {
   private String vtPrefix;

   ProviderUrlInfo(ServerURL providerUrl, String partitionName, String vtPrefix) throws NamingException {
      super(providerUrl.toString(), partitionName, providerUrl);
      this.vtPrefix = vtPrefix;
   }

   private ProviderUrlInfo(String vtPrefix, JndiInfo providerInfo, ServerURL url) {
      super(providerInfo, url);
      this.vtPrefix = vtPrefix;
   }

   public String getVirtualTargetPrefix() {
      return this.vtPrefix;
   }

   public JndiNamingInfo parse(String name) throws MalformedURLException, NamingException {
      if (name.indexOf("://") < 0) {
         return new JndiNamingInfo(this, this.getServerURL(), name);
      } else {
         ServerURL url = LocateRegistry.getServerURL(name);
         JndiNamingInfo bindInfo = (JndiNamingInfo)JndiInfo.parse(url, false);
         if (!this.isCompatible(bindInfo)) {
            return bindInfo;
         } else {
            String bindFile = bindInfo.getPath();
            if (this.vtPrefix.length() == 0) {
               return new JndiNamingInfo(this, url, bindFile);
            } else if (bindFile.startsWith(this.vtPrefix) && !this.vtPrefix.equals(bindFile)) {
               bindFile = bindFile.substring(this.vtPrefix.length());
               if (bindFile.startsWith("/")) {
                  bindFile = bindFile.substring(1);
               }

               return new JndiNamingInfo(this, url, bindFile);
            } else {
               return bindInfo;
            }
         }
      }
   }
}

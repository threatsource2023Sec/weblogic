package weblogic.rmi;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.protocol.ServerURL;

public class JndiInfo {
   private String providerUrl;
   private String partitionName;
   private ServerURL url;
   private Context context;

   protected JndiInfo(String providerUrl, String partitionName, ServerURL url) throws NamingException {
      this.providerUrl = providerUrl;
      this.partitionName = partitionName;
      this.url = url;
      this.context = this.createRMIContext();
   }

   protected JndiInfo(JndiInfo providerInfo, ServerURL url) {
      this.providerUrl = providerInfo.providerUrl;
      this.partitionName = providerInfo.partitionName;
      this.url = url;
      this.context = providerInfo.context;
   }

   ServerURL getServerURL() {
      return this.url;
   }

   public String getFullURL(String newFile) {
      String path = this.url.getFile();
      ServerURL newUrl;
      if (this.partitionName != null && path != null && path.startsWith("/partitions/")) {
         newUrl = new ServerURL(this.url, "/partitions/" + this.partitionName + "/" + newFile, "");
      } else {
         newUrl = new ServerURL(this.url, newFile, this.url.getQuery());
      }

      return newUrl.toString();
   }

   public String getProviderUrl() {
      return this.providerUrl;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public static JndiInfo parse(ServerURL url, boolean isRegistryURL) throws NamingException {
      String path = url.getFile();
      String query = url.getQuery();
      if (path != null && path.startsWith("/partitions/")) {
         int startIndex = "/partitions/".length();
         int slashIndex = path.indexOf("/", startIndex);
         String partitionName;
         ServerURL providerUrl;
         if (slashIndex > 0) {
            partitionName = path.substring(startIndex, slashIndex);
            providerUrl = new ServerURL(url, path.substring(0, slashIndex), query);
            if (isRegistryURL) {
               return new ProviderUrlInfo(providerUrl, partitionName, "");
            }

            return new JndiNamingInfo(providerUrl.toString(), partitionName, url, path.substring(slashIndex + 1));
         }

         if (isRegistryURL) {
            partitionName = path.substring(startIndex);
            providerUrl = new ServerURL(url, path, query);
            return new ProviderUrlInfo(providerUrl, partitionName, "");
         }
      }

      if (path.length() > 1) {
         path = path.substring(1);
      }

      ServerURL providerUrl;
      if (query != null && query.contains("partitionName=")) {
         providerUrl = new ServerURL(url, (String)null, query);
         String partitionName = null;
         int startIndex = query.indexOf("partitionName=") + "partitionName=".length();
         int ampersandIndex = query.indexOf("&", startIndex);
         if (ampersandIndex == -1) {
            partitionName = query.substring(startIndex);
         } else {
            partitionName = query.substring(startIndex, ampersandIndex);
         }

         return (JndiInfo)(isRegistryURL ? new ProviderUrlInfo(providerUrl, partitionName, "") : new JndiNamingInfo(providerUrl.toString(), partitionName, url, path));
      } else if (isRegistryURL) {
         return new ProviderUrlInfo(url, (String)null, path);
      } else {
         providerUrl = new ServerURL(url, (String)null, query);
         return new JndiNamingInfo(providerUrl.toString(), (String)null, url, path);
      }
   }

   private static boolean same(String a, String b) {
      if (a != null && a.length() == 0) {
         a = null;
      }

      if (b != null && b.length() == 0) {
         b = null;
      }

      if (a == null && b != null) {
         return false;
      } else if (a != null && b == null) {
         return false;
      } else {
         return a == null && b == null ? true : a.equals(b);
      }
   }

   protected boolean isCompatible(JndiInfo bindInfo) {
      if (!same(this.url.getProtocol(), bindInfo.url.getProtocol())) {
         return false;
      } else if (!same(this.url.getHost(), bindInfo.url.getHost())) {
         return false;
      } else if (this.url.getPort() != bindInfo.url.getPort()) {
         return false;
      } else {
         return same(this.partitionName, bindInfo.getPartitionName());
      }
   }

   private Context createRMIContext() throws NamingException {
      Hashtable env = new Hashtable();
      env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      if (this.url.getPort() != -1) {
         env.put("java.naming.provider.url", this.providerUrl);
      }

      Context ctx = new InitialContext(env);
      return (Context)ctx.lookup("weblogic.rmi");
   }

   public Context getContext() {
      return this.context;
   }
}

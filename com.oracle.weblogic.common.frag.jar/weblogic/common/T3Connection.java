package weblogic.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Hashtable;
import weblogic.rjvm.ClientServerURL;
import weblogic.security.acl.UserInfo;
import weblogic.utils.http.HttpParsing;

public final class T3Connection {
   private String protocol;
   private String host;
   private int port;
   private String path;
   private Hashtable queryParams;
   private UserInfo user;
   private String url;

   /** @deprecated */
   @Deprecated
   public String getHost() {
      return this.host;
   }

   /** @deprecated */
   @Deprecated
   public int getPort() {
      return this.port;
   }

   /** @deprecated */
   @Deprecated
   public String getProtocol() {
      return this.protocol;
   }

   /** @deprecated */
   @Deprecated
   public UserInfo getUser() {
      return this.user;
   }

   /** @deprecated */
   @Deprecated
   public String getURL() {
      return this.url;
   }

   /** @deprecated */
   @Deprecated
   public String getPath() {
      return this.path;
   }

   /** @deprecated */
   @Deprecated
   public Hashtable getQueryParams() {
      return this.queryParams;
   }

   /** @deprecated */
   @Deprecated
   public String getQueryParam(String key) {
      return this.getQueryParams() == null ? null : (String)this.getQueryParams().get(key);
   }

   /** @deprecated */
   @Deprecated
   public T3Connection(String url) throws UnknownHostException, MalformedURLException {
      this(url, T3User.GUEST);
   }

   /** @deprecated */
   @Deprecated
   public T3Connection(String url, UserInfo t3usr) throws UnknownHostException, MalformedURLException {
      this.url = url;
      this.user = t3usr;
      if (this.user == null) {
         this.user = T3User.GUEST;
      }

      ClientServerURL sURL = new ClientServerURL(url);
      this.protocol = sURL.getProtocol();
      this.host = sURL.getHost();
      this.port = sURL.getPort();
      this.path = sURL.getFile();
      if (sURL.getQuery().length() != 0) {
         this.queryParams = new Hashtable();
         HttpParsing.parseQueryString(sURL.getQuery().replace('?', '&'), this.queryParams);
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean isConnected() {
      return true;
   }

   /** @deprecated */
   @Deprecated
   public T3Connection connect() throws IOException, T3Exception {
      return this;
   }

   /** @deprecated */
   @Deprecated
   public void disconnect() throws IOException, T3Exception {
   }

   /** @deprecated */
   @Deprecated
   public String toString() {
      return "[T3Connection host=" + this.host + ", port=" + this.port + ", user=" + this.user + ", hashCode=" + this.hashCode() + "]";
   }
}

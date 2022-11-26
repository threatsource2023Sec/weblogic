package net.shibboleth.utilities.java.support.net;

import com.google.common.base.Strings;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class URLBuilder {
   @Nullable
   private String scheme;
   @Nullable
   private String username;
   @Nullable
   private String password;
   @Nullable
   private String host;
   @Nullable
   private Integer port;
   @Nullable
   private String path;
   @Nonnull
   private List queryParams;
   private String fragement;

   public URLBuilder() {
      this.queryParams = new ArrayList();
   }

   public URLBuilder(@Nonnull @NotEmpty String baseURL) throws MalformedURLException {
      URL url = new URL(baseURL);
      this.setScheme(url.getProtocol());
      String userInfo = url.getUserInfo();
      if (!Strings.isNullOrEmpty(userInfo)) {
         if (userInfo.contains(":")) {
            String[] userInfoComps = userInfo.split(":");
            this.setUsername(URISupport.doURLDecode(userInfoComps[0]));
            this.setPassword(URISupport.doURLDecode(userInfoComps[1]));
         } else {
            this.setUsername(userInfo);
         }
      }

      this.setHost(url.getHost());
      if (url.getPort() > 0) {
         this.setPort(url.getPort());
      }

      this.setPath(url.getPath());
      this.queryParams = new ArrayList();
      String queryString = url.getQuery();
      if (!Strings.isNullOrEmpty(queryString)) {
         String[] queryComps = queryString.split("&");
         String[] arr$ = queryComps;
         int len$ = queryComps.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            String queryComp = arr$[i$];
            int where = queryComp.indexOf(61);
            String paramName;
            if (where < 0) {
               paramName = URISupport.doURLDecode(queryComp);
               this.queryParams.add(new Pair(paramName, (Object)null));
            } else {
               paramName = URISupport.doURLDecode(queryComp.substring(0, where));
               if ("".equals(paramName)) {
                  paramName = null;
               }

               String paramValue = URISupport.doURLDecode(queryComp.substring(where + 1));
               if ("".equals(paramValue)) {
                  paramValue = null;
               }

               this.queryParams.add(new Pair(paramName, paramValue));
            }
         }
      }

      this.setFragment(url.getRef());
   }

   @Nullable
   public String getFragment() {
      return this.fragement;
   }

   public void setFragment(@Nullable String newFragment) {
      this.fragement = StringSupport.trimOrNull(newFragment);
   }

   @Nullable
   public String getHost() {
      return this.host;
   }

   public void setHost(@Nullable String newHost) {
      this.host = StringSupport.trimOrNull(newHost);
   }

   @Nullable
   public String getPassword() {
      return this.password;
   }

   public void setPassword(@Nullable String newPassword) {
      this.password = StringSupport.trimOrNull(newPassword);
   }

   @Nullable
   public String getPath() {
      return this.path;
   }

   public void setPath(@Nullable String newPath) {
      this.path = StringSupport.trimOrNull(newPath);
   }

   @Nullable
   public Integer getPort() {
      return this.port;
   }

   public void setPort(@Nullable Integer newPort) {
      this.port = newPort;
   }

   @Nonnull
   public List getQueryParams() {
      return this.queryParams;
   }

   @Nullable
   public String getScheme() {
      return this.scheme;
   }

   public void setScheme(@Nullable String newScheme) {
      this.scheme = StringSupport.trimOrNull(newScheme);
   }

   @Nullable
   public String getUsername() {
      return this.username;
   }

   public void setUsername(@Nullable String newUsername) {
      this.username = StringSupport.trimOrNull(newUsername);
   }

   @Nullable
   public String buildURL() {
      StringBuilder builder = new StringBuilder();
      if (!Strings.isNullOrEmpty(this.scheme)) {
         builder.append(this.scheme);
         builder.append("://");
      }

      if (!Strings.isNullOrEmpty(this.username)) {
         builder.append(this.username);
         if (!Strings.isNullOrEmpty(this.password)) {
            builder.append(":");
            builder.append(this.password);
         }

         builder.append("@");
      }

      if (!Strings.isNullOrEmpty(this.host)) {
         builder.append(this.host);
         if (this.port != null && this.port > 0) {
            builder.append(":");
            builder.append(this.port);
         }
      }

      if (!Strings.isNullOrEmpty(this.path)) {
         if (!this.path.startsWith("/")) {
            builder.append("/");
         }

         builder.append(this.path);
      }

      String queryString = this.buildQueryString();
      if (!Strings.isNullOrEmpty(queryString)) {
         builder.append("?");
         builder.append(queryString);
      }

      if (!Strings.isNullOrEmpty(this.fragement)) {
         builder.append("#");
         builder.append(this.fragement);
      }

      return builder.toString();
   }

   @Nullable
   public String buildQueryString() {
      if (this.queryParams.size() > 0) {
         StringBuilder builder = new StringBuilder();

         for(int i = 0; i < this.queryParams.size(); ++i) {
            Pair param = (Pair)this.queryParams.get(i);
            String name = StringSupport.trimOrNull((String)param.getFirst());
            if (name != null) {
               builder.append(URISupport.doURLEncode(name));
               String value = StringSupport.trimOrNull((String)param.getSecond());
               if (value != null) {
                  builder.append("=");
                  builder.append(URISupport.doURLEncode(value));
               }

               if (i < this.queryParams.size() - 1) {
                  builder.append("&");
               }
            }
         }

         return builder.toString();
      } else {
         return null;
      }
   }
}

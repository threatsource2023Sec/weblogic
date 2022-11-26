package weblogic.servlet.internal.session;

import javax.servlet.SessionCookieConfig;
import weblogic.servlet.internal.WebAppServletContext;

public class SessionCookieConfigImpl implements SessionCookieConfig {
   private WebAppServletContext context;
   private SessionConfigManager sessionConfig;

   public SessionCookieConfigImpl(WebAppServletContext context, SessionConfigManager sessionConfigManager) {
      this.context = context;
      this.sessionConfig = sessionConfigManager;
   }

   public String getComment() {
      return this.sessionConfig.getCookieComment();
   }

   public String getDomain() {
      return this.sessionConfig.getCookieDomain();
   }

   public int getMaxAge() {
      return this.sessionConfig.getCookieMaxAgeSecs();
   }

   public String getName() {
      return this.sessionConfig.getCookieName();
   }

   public String getPath() {
      return this.sessionConfig.getCookiePath();
   }

   public boolean isHttpOnly() {
      return this.sessionConfig.isCookieHttpOnly();
   }

   public boolean isSecure() {
      return this.sessionConfig.isCookieSecure();
   }

   public void setComment(String comment) {
      this.checkContextStarted();
      this.sessionConfig.setCookieComment(comment);
   }

   public void setDomain(String domain) {
      this.checkContextStarted();
      this.sessionConfig.setCookieDomain(domain);
   }

   public void setHttpOnly(boolean httpOnly) {
      this.checkContextStarted();
      this.sessionConfig.setCookieHttpOnly(httpOnly);
   }

   public void setMaxAge(int maxAge) {
      this.checkContextStarted();
      this.sessionConfig.setCookieMaxAgeSecs(maxAge);
   }

   public void setName(String name) {
      this.checkContextStarted();
      this.sessionConfig.setCookieName(name);
   }

   public void setPath(String path) {
      this.checkContextStarted();
      this.sessionConfig.setCookiePath(path);
   }

   public void setSecure(boolean secure) {
      this.checkContextStarted();
      this.sessionConfig.setCookieSecure(secure);
   }

   private void checkContextStarted() {
      if (this.context.isStarted()) {
         throw new IllegalStateException("ServletContext has already been initialized.");
      }
   }
}

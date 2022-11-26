package org.glassfish.grizzly.servlet;

public class SessionCookieConfig implements javax.servlet.SessionCookieConfig {
   private String name;
   private String domain;
   private String path;
   private String comment;
   private boolean httpOnly = true;
   private boolean secure;
   private final WebappContext ctx;
   private int maxAge = -1;

   public SessionCookieConfig(WebappContext ctx) {
      this.ctx = ctx;
   }

   public void setName(String name) {
      if (this.ctx.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else {
         this.name = name;
      }
   }

   public String getName() {
      return this.name;
   }

   public void setDomain(String domain) {
      if (this.ctx.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else {
         this.domain = domain;
      }
   }

   public String getDomain() {
      return this.domain;
   }

   public void setPath(String path) {
      if (this.ctx.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else {
         this.path = path;
      }
   }

   public String getPath() {
      return this.path;
   }

   public void setComment(String comment) {
      if (this.ctx.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else {
         this.comment = comment;
      }
   }

   public String getComment() {
      return this.comment;
   }

   public void setHttpOnly(boolean httpOnly) {
      if (this.ctx.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else {
         this.httpOnly = httpOnly;
      }
   }

   public boolean isHttpOnly() {
      return this.httpOnly;
   }

   public void setSecure(boolean secure) {
      if (this.ctx.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else {
         this.secure = secure;
      }
   }

   public boolean isSecure() {
      return this.secure;
   }

   public void setMaxAge(int maxAge) {
      if (this.ctx.deployed) {
         throw new IllegalArgumentException("WebappContext has already been deployed");
      } else {
         this.maxAge = maxAge;
      }
   }

   public int getMaxAge() {
      return this.maxAge;
   }
}

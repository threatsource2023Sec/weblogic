package org.glassfish.grizzly.servlet;

import org.glassfish.grizzly.http.Cookie;

public class CookieWrapper extends Cookie {
   private javax.servlet.http.Cookie wrappedCookie = null;

   public CookieWrapper(String name, String value) {
      super(name, value);
   }

   public void setComment(String purpose) {
      this.wrappedCookie.setComment(purpose);
   }

   public String getComment() {
      return this.wrappedCookie.getComment();
   }

   public void setDomain(String pattern) {
      this.wrappedCookie.setDomain(pattern);
   }

   public String getDomain() {
      return this.wrappedCookie.getDomain();
   }

   public void setMaxAge(int expiry) {
      this.wrappedCookie.setMaxAge(expiry);
   }

   public int getMaxAge() {
      return this.wrappedCookie.getMaxAge();
   }

   public void setPath(String uri) {
      this.wrappedCookie.setPath(uri);
   }

   public String getPath() {
      return this.wrappedCookie.getPath();
   }

   public void setSecure(boolean flag) {
      this.wrappedCookie.setSecure(flag);
   }

   public boolean isSecure() {
      return this.wrappedCookie.getSecure();
   }

   public String getName() {
      return this.wrappedCookie.getName();
   }

   public void setValue(String newValue) {
      this.wrappedCookie.setValue(newValue);
   }

   public String getValue() {
      return this.wrappedCookie.getValue();
   }

   public int getVersion() {
      return this.wrappedCookie.getVersion();
   }

   public void setVersion(int v) {
      this.wrappedCookie.setVersion(v);
   }

   public boolean isHttpOnly() {
      return this.wrappedCookie.isHttpOnly();
   }

   public void setHttpOnly(boolean isHttpOnly) {
      this.wrappedCookie.setHttpOnly(isHttpOnly);
   }

   public Object cloneCookie() {
      return this.wrappedCookie.clone();
   }

   public javax.servlet.http.Cookie getWrappedCookie() {
      return this.wrappedCookie;
   }

   public void setWrappedCookie(javax.servlet.http.Cookie wrappedCookie) {
      this.wrappedCookie = wrappedCookie;
   }
}

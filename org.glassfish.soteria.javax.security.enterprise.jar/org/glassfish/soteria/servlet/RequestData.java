package org.glassfish.soteria.servlet;

import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.soteria.Utils;

public class RequestData {
   private Cookie[] cookies;
   private Map headers;
   private List locales;
   private Map parameters;
   private String method;
   private String requestURL;
   private String queryString;
   private boolean restoreRequest = true;

   public Cookie[] getCookies() {
      return this.cookies;
   }

   public void setCookies(Cookie[] cookies) {
      this.cookies = cookies;
   }

   public Map getHeaders() {
      return this.headers;
   }

   public void setHeaders(Map headers) {
      this.headers = headers;
   }

   public List getLocales() {
      return this.locales;
   }

   public void setLocales(List locales) {
      this.locales = locales;
   }

   public Map getParameters() {
      return this.parameters;
   }

   public void setParameters(Map parameters) {
      this.parameters = parameters;
   }

   public String getMethod() {
      return this.method;
   }

   public void setMethod(String method) {
      this.method = method;
   }

   public String getQueryString() {
      return this.queryString;
   }

   public void setQueryString(String queryString) {
      this.queryString = queryString;
   }

   public String getRequestURL() {
      return this.requestURL;
   }

   public void setRequestURL(String requestURL) {
      this.requestURL = requestURL;
   }

   public boolean isRestoreRequest() {
      return this.restoreRequest;
   }

   public void setRestoreRequest(boolean restoreRequest) {
      this.restoreRequest = restoreRequest;
   }

   public String getFullRequestURL() {
      return this.buildFullRequestURL(this.requestURL, this.queryString);
   }

   public boolean matchesRequest(HttpServletRequest request) {
      return this.getFullRequestURL().equals(this.buildFullRequestURL(request.getRequestURL().toString(), request.getQueryString()));
   }

   public String toString() {
      return String.format("%s %s", this.method, this.getFullRequestURL());
   }

   private String buildFullRequestURL(String requestURL, String queryString) {
      return requestURL + (Utils.isEmpty(queryString) ? "" : "?" + queryString);
   }
}

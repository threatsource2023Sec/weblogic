package org.jboss.weld.module.jsf;

import javax.faces.context.FacesContext;

public class FacesUrlTransformer {
   private static final String HTTP_PROTOCOL_URL_PREFIX = "http://";
   private static final String HTTPS_PROTOCOL_URL_PREFIX = "https://";
   private static final String QUERY_STRING_DELIMITER = "?";
   private static final String PARAMETER_PAIR_DELIMITER = "&";
   private static final String PARAMETER_PAIR_DELIMITER_ENCODED = ";";
   private static final String PARAMETER_ASSIGNMENT_OPERATOR = "=";
   private String url;
   private final FacesContext context;

   public FacesUrlTransformer(String url, FacesContext facesContext) {
      this.url = url;
      this.context = facesContext;
   }

   public FacesUrlTransformer appendConversationIdIfNecessary(String cidParameterName, String cid) {
      this.url = appendParameterIfNeeded(this.url, cidParameterName, cid);
      return this;
   }

   private static String appendParameterIfNeeded(String url, String parameterName, String parameterValue) {
      int queryStringIndex = url.indexOf("?");
      if (queryStringIndex >= 0 && !isCidParamAbsent(url, parameterName, queryStringIndex)) {
         return url;
      } else {
         StringBuilder builder = new StringBuilder(url);
         if (queryStringIndex < 0) {
            builder.append("?");
         } else {
            builder.append("&");
         }

         builder.append(parameterName).append("=");
         if (parameterValue != null) {
            builder.append(parameterValue);
         }

         return builder.toString();
      }
   }

   private static boolean isCidParamAbsent(String url, String parameterName, int queryStringIndex) {
      return url.indexOf("?" + parameterName + "=", queryStringIndex) < 0 && url.indexOf("&" + parameterName + "=", queryStringIndex) < 0 && url.indexOf(";" + parameterName + "=", queryStringIndex) < 0;
   }

   public String getUrl() {
      return this.url;
   }

   public FacesUrlTransformer toRedirectViewId() {
      String requestPath = this.context.getExternalContext().getRequestContextPath();
      if (this.isUrlAbsolute()) {
         this.url = this.url.substring(this.url.indexOf(requestPath) + requestPath.length());
      } else if (this.url.startsWith(requestPath)) {
         this.url = this.url.substring(requestPath.length());
      }

      return this;
   }

   public FacesUrlTransformer toActionUrl() {
      String actionUrl = this.context.getApplication().getViewHandler().getActionURL(this.context, this.url);
      int queryStringIndex = this.url.indexOf("?");
      if (queryStringIndex < 0) {
         this.url = actionUrl;
      } else {
         String queryParameters = this.url.substring(queryStringIndex + 1);
         int actionQueryStringIndex = actionUrl.indexOf("?");
         if (actionQueryStringIndex < 0) {
            this.url = actionUrl + "?" + queryParameters;
         } else {
            String actionQueryParameters = actionUrl.substring(actionQueryStringIndex + 1);
            if (queryParameters.startsWith(actionQueryParameters)) {
               this.url = actionUrl.substring(0, actionQueryStringIndex) + "?" + queryParameters;
            } else {
               this.url = actionUrl + "&" + queryParameters;
            }
         }
      }

      return this;
   }

   public String encode() {
      return this.context.getExternalContext().encodeActionURL(this.url);
   }

   private boolean isUrlAbsolute() {
      return this.url.startsWith("http://") || this.url.startsWith("https://");
   }
}

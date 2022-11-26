package weblogic.servlet.security.internal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;

public class WebAppContextHandler implements ContextHandler {
   public static final String HTTP_SERVLET_REQUEST = "HttpServletRequest";
   public static final String HTTP_SERVLET_RESPONSE = "HttpServletResponse";
   private int SIZE = 4;
   private static final String[] names = new String[]{"com.bea.contextelement.servlet.HttpServletRequest", "com.bea.contextelement.servlet.HttpServletResponse", "HttpServletRequest", "HttpServletResponse"};
   private Object[] values = null;
   private ContextElement requestElement = null;
   private ContextElement responseElement = null;
   private ContextElement requestElementDeprecated = null;
   private ContextElement responseElementDeprecated = null;

   public WebAppContextHandler(HttpServletRequest request, HttpServletResponse response) {
      this.values = new Object[this.SIZE];
      this.values[0] = request;
      this.values[1] = response;
      this.values[2] = request;
      this.values[3] = response;
      this.requestElement = new ContextElement("com.bea.contextelement.servlet.HttpServletRequest", request);
      this.responseElement = new ContextElement("com.bea.contextelement.servlet.HttpServletResponse", response);
      this.requestElementDeprecated = new ContextElement("HttpServletRequest", request);
      this.responseElementDeprecated = new ContextElement("HttpServletResponse", response);
   }

   public int size() {
      return this.SIZE;
   }

   public String[] getNames() {
      return names;
   }

   public Object getValue(String name) {
      if (name == null) {
         return null;
      } else if (name.equals("com.bea.contextelement.servlet.HttpServletRequest")) {
         return this.values[0];
      } else if (name.equals("com.bea.contextelement.servlet.HttpServletResponse")) {
         return this.values[1];
      } else if (name.equals("HttpServletRequest")) {
         return this.values[2];
      } else {
         return name.equals("HttpServletResponse") ? this.values[3] : null;
      }
   }

   private int indexOf(String name) {
      if (name == null) {
         return -1;
      } else if (name.equals("com.bea.contextelement.servlet.HttpServletRequest")) {
         return 0;
      } else if (name.equals("com.bea.contextelement.servlet.HttpServletResponse")) {
         return 1;
      } else if (name.equals("HttpServletRequest")) {
         return 2;
      } else {
         return name.equals("HttpServletResponse") ? 3 : -1;
      }
   }

   public ContextElement[] getValues(String[] requested) {
      if (requested != null && requested.length >= 1) {
         boolean wantRequest = false;
         boolean wantResponse = false;

         for(int i = 0; i < requested.length; ++i) {
            int index = this.indexOf(requested[i]);
            if (index != 0 && index != 2) {
               if (index == 1 || index == 3) {
                  wantResponse = true;
               }
            } else {
               wantRequest = true;
            }
         }

         ContextElement[] result = null;
         if (wantRequest && wantResponse) {
            result = new ContextElement[]{this.requestElement, this.responseElement, this.requestElementDeprecated, this.responseElementDeprecated};
         } else if (wantRequest && !wantResponse) {
            result = new ContextElement[]{this.requestElement, this.requestElementDeprecated};
         } else if (!wantRequest && wantResponse) {
            result = new ContextElement[]{this.responseElement, null};
            result[0] = this.responseElementDeprecated;
         }

         return result;
      } else {
         return null;
      }
   }
}

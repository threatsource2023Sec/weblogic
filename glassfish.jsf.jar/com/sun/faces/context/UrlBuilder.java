package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.ClientWindow;

class UrlBuilder {
   public static final String QUERY_STRING_SEPARATOR = "?";
   public static final String PARAMETER_PAIR_SEPARATOR = "&";
   public static final String PARAMETER_NAME_VALUE_SEPARATOR = "=";
   public static final String FRAGMENT_SEPARATOR = "#";
   public static final String DEFAULT_ENCODING = "UTF-8";
   private static final List NULL_LIST = Arrays.asList((String)null);
   private StringBuilder url;
   private String path;
   private String queryString;
   private String fragment;
   private Map parameters;
   private String encoding;

   public UrlBuilder(String url, String encoding) {
      if (url != null && url.trim().length() != 0) {
         this.url = new StringBuilder(url.length() * 2);
         this.extractSegments(url);
         this.encoding = encoding;
      } else {
         throw new IllegalArgumentException("Url cannot be empty");
      }
   }

   public UrlBuilder(String url) {
      this(url, "UTF-8");
   }

   public UrlBuilder addParameters(String name, List values) {
      if (name != null && name.trim().length() != 0) {
         this.addValuesToParameter(name.trim(), values, true);
         return this;
      } else {
         throw new IllegalArgumentException("Parameter name cannot be empty");
      }
   }

   public UrlBuilder addParameters(Map params) {
      if (params != null && !params.isEmpty()) {
         Iterator var2 = params.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            if (entry.getKey() == null || ((String)entry.getKey()).trim().length() == 0) {
               throw new IllegalArgumentException("Parameter name cannot be empty");
            }

            List values = (List)entry.getValue();
            this.addValuesToParameter(((String)entry.getKey()).trim(), values, true);
         }
      }

      return this;
   }

   public UrlBuilder setPath(String path) {
      if (path != null && path.trim().length() != 0) {
         this.path = path;
         return this;
      } else {
         throw new IllegalArgumentException("Path cannot be empty");
      }
   }

   public UrlBuilder setQueryString(String queryString) {
      this.queryString = queryString;
      this.cleanQueryString();
      return this;
   }

   public UrlBuilder setFragment(String fragment) {
      this.fragment = fragment;
      this.cleanFragment();
      return this;
   }

   public String createUrl() {
      this.appendPath();
      this.appendQueryString();
      this.appendFragment();
      return this.url.toString();
   }

   protected String getPath() {
      return this.path;
   }

   protected Map getParameters() {
      this.parseQueryString();
      return this.parameters;
   }

   protected void parseQueryString() {
      if (this.parameters == null) {
         this.parameters = new LinkedHashMap();
      }

      if (this.queryString != null) {
         Map appMap = FacesContext.getCurrentInstance().getExternalContext().getApplicationMap();
         String[] pairs = Util.split(appMap, this.queryString, "&");
         String[] var3 = pairs;
         int var4 = pairs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String pair = var3[var5];
            String[] nameAndValue = Util.split(appMap, pair, "=");
            if (nameAndValue.length == 2 && nameAndValue[0].trim().length() != 0) {
               this.addValueToParameter(nameAndValue[0], nameAndValue[1], false);
            }
         }

         this.queryString = null;
      }
   }

   protected void appendPath() {
      this.url.append(this.path);
   }

   protected void appendQueryString() {
      boolean hasQueryString = false;
      if (this.parameters != null) {
         String nextSeparatorChar;
         if (this.queryString == null) {
            nextSeparatorChar = "?";
         } else {
            nextSeparatorChar = "&";
            this.url.append("?").append(this.queryString);
         }

         Iterator var3 = this.parameters.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry param = (Map.Entry)var3.next();

            for(Iterator var5 = ((List)param.getValue()).iterator(); var5.hasNext(); nextSeparatorChar = "&") {
               String value = (String)var5.next();
               this.url.append(nextSeparatorChar);
               this.url.append((String)param.getKey());
               this.url.append("=");
               this.url.append(value);
            }
         }

         hasQueryString = true;
      } else if (this.queryString != null) {
         this.url.append("?").append(this.queryString);
         hasQueryString = true;
      }

      FacesContext context = FacesContext.getCurrentInstance();
      ClientWindow cw = context.getExternalContext().getClientWindow();
      boolean appendClientWindow = false;
      if (null != cw) {
         appendClientWindow = cw.isClientWindowRenderModeEnabled(context);
      }

      if (appendClientWindow && -1 == this.url.indexOf("jfwid") && null != cw) {
         String clientWindow = cw.getId();
         if (!hasQueryString) {
            this.url.append("?");
         } else {
            this.url.append("&");
         }

         this.url.append("jfwid").append("=").append(clientWindow);
         Map additionalParams = cw.getQueryURLParameters(context);
         if (null != additionalParams) {
            Iterator var7 = additionalParams.entrySet().iterator();

            while(var7.hasNext()) {
               Map.Entry cur = (Map.Entry)var7.next();
               this.url.append("=");
               this.url.append((String)cur.getKey()).append("=").append((String)cur.getValue());
            }
         }
      }

   }

   protected void appendFragment() {
      if (this.fragment != null) {
         this.url.append("#").append(this.fragment);
      }

   }

   protected void extractSegments(String url) {
      int fragmentIndex = url.indexOf("#");
      if (fragmentIndex != -1) {
         this.fragment = url.substring(fragmentIndex + 1);
         this.cleanFragment();
         url = url.substring(0, fragmentIndex);
      }

      int queryStringIndex = url.indexOf("?");
      if (queryStringIndex != -1) {
         this.queryString = url.substring(queryStringIndex + 1);
         this.cleanQueryString();
         this.path = url.substring(0, queryStringIndex);
      } else {
         this.path = url;
      }

   }

   protected void addValueToParameter(String name, String value, boolean replace) {
      List values = new ArrayList(value == null ? 0 : 1);
      if (value != null) {
         values.add(value);
      }

      this.addValuesToParameter(name, values, replace);
   }

   protected void addValuesToParameter(String name, List valuesRef, boolean replace) {
      List values = new ArrayList();
      if (valuesRef != null) {
         Iterator it = valuesRef.iterator();

         while(it.hasNext()) {
            String string = (String)it.next();
            if (this.encoding != null) {
               try {
                  values.add(URLEncoder.encode(string, this.encoding));
               } catch (UnsupportedEncodingException var8) {
                  throw new RuntimeException(var8);
               }
            } else {
               values.add(string);
            }
         }

         values.removeAll(NULL_LIST);
      }

      if (this.parameters == null) {
         this.parameters = new LinkedHashMap();
      }

      if (replace) {
         this.parameters.put(name, values);
      } else {
         List currentValues = (List)this.parameters.get(name);
         if (currentValues == null) {
            currentValues = new ArrayList(1);
            this.parameters.put(name, currentValues);
         }

         ((List)currentValues).addAll(values);
      }

   }

   private void cleanFragment() {
      if (this.fragment != null) {
         String f = this.fragment;
         f = f.trim();
         if (f.startsWith("#")) {
            f = f.substring(1);
         }

         if (f.length() == 0) {
            f = null;
         }

         this.fragment = f;
      }

   }

   private void cleanQueryString() {
      if (this.queryString != null) {
         String q = this.queryString;
         q = q.trim();
         if (q.startsWith("?")) {
            q = q.substring(1);
         }

         if (q.length() == 0) {
            q = null;
         }

         this.queryString = q;
      }

   }
}

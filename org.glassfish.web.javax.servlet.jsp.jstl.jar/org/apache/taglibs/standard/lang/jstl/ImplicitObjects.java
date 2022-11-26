package org.apache.taglibs.standard.lang.jstl;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class ImplicitObjects {
   static final String sAttributeName = "org.apache.taglibs.standard.ImplicitObjects";
   PageContext mContext;
   Map mPage;
   Map mRequest;
   Map mSession;
   Map mApplication;
   Map mParam;
   Map mParams;
   Map mHeader;
   Map mHeaders;
   Map mInitParam;
   Map mCookie;

   public ImplicitObjects(PageContext pContext) {
      this.mContext = pContext;
   }

   public static ImplicitObjects getImplicitObjects(PageContext pContext) {
      ImplicitObjects objs = (ImplicitObjects)pContext.getAttribute("org.apache.taglibs.standard.ImplicitObjects", 1);
      if (objs == null) {
         objs = new ImplicitObjects(pContext);
         pContext.setAttribute("org.apache.taglibs.standard.ImplicitObjects", objs, 1);
      }

      return objs;
   }

   public Map getPageScopeMap() {
      if (this.mPage == null) {
         this.mPage = createPageScopeMap(this.mContext);
      }

      return this.mPage;
   }

   public Map getRequestScopeMap() {
      if (this.mRequest == null) {
         this.mRequest = createRequestScopeMap(this.mContext);
      }

      return this.mRequest;
   }

   public Map getSessionScopeMap() {
      if (this.mSession == null) {
         this.mSession = createSessionScopeMap(this.mContext);
      }

      return this.mSession;
   }

   public Map getApplicationScopeMap() {
      if (this.mApplication == null) {
         this.mApplication = createApplicationScopeMap(this.mContext);
      }

      return this.mApplication;
   }

   public Map getParamMap() {
      if (this.mParam == null) {
         this.mParam = createParamMap(this.mContext);
      }

      return this.mParam;
   }

   public Map getParamsMap() {
      if (this.mParams == null) {
         this.mParams = createParamsMap(this.mContext);
      }

      return this.mParams;
   }

   public Map getHeaderMap() {
      if (this.mHeader == null) {
         this.mHeader = createHeaderMap(this.mContext);
      }

      return this.mHeader;
   }

   public Map getHeadersMap() {
      if (this.mHeaders == null) {
         this.mHeaders = createHeadersMap(this.mContext);
      }

      return this.mHeaders;
   }

   public Map getInitParamMap() {
      if (this.mInitParam == null) {
         this.mInitParam = createInitParamMap(this.mContext);
      }

      return this.mInitParam;
   }

   public Map getCookieMap() {
      if (this.mCookie == null) {
         this.mCookie = createCookieMap(this.mContext);
      }

      return this.mCookie;
   }

   public static Map createPageScopeMap(final PageContext pContext) {
      return new EnumeratedMap() {
         public Enumeration enumerateKeys() {
            return pContext.getAttributeNamesInScope(1);
         }

         public Object getValue(Object pKey) {
            return pKey instanceof String ? pContext.getAttribute((String)pKey, 1) : null;
         }

         public boolean isMutable() {
            return true;
         }
      };
   }

   public static Map createRequestScopeMap(final PageContext pContext) {
      return new EnumeratedMap() {
         public Enumeration enumerateKeys() {
            return pContext.getAttributeNamesInScope(2);
         }

         public Object getValue(Object pKey) {
            return pKey instanceof String ? pContext.getAttribute((String)pKey, 2) : null;
         }

         public boolean isMutable() {
            return true;
         }
      };
   }

   public static Map createSessionScopeMap(final PageContext pContext) {
      return new EnumeratedMap() {
         public Enumeration enumerateKeys() {
            return pContext.getAttributeNamesInScope(3);
         }

         public Object getValue(Object pKey) {
            return pKey instanceof String ? pContext.getAttribute((String)pKey, 3) : null;
         }

         public boolean isMutable() {
            return true;
         }
      };
   }

   public static Map createApplicationScopeMap(final PageContext pContext) {
      return new EnumeratedMap() {
         public Enumeration enumerateKeys() {
            return pContext.getAttributeNamesInScope(4);
         }

         public Object getValue(Object pKey) {
            return pKey instanceof String ? pContext.getAttribute((String)pKey, 4) : null;
         }

         public boolean isMutable() {
            return true;
         }
      };
   }

   public static Map createParamMap(PageContext pContext) {
      final HttpServletRequest request = (HttpServletRequest)pContext.getRequest();
      return new EnumeratedMap() {
         public Enumeration enumerateKeys() {
            return request.getParameterNames();
         }

         public Object getValue(Object pKey) {
            return pKey instanceof String ? request.getParameter((String)pKey) : null;
         }

         public boolean isMutable() {
            return false;
         }
      };
   }

   public static Map createParamsMap(PageContext pContext) {
      final HttpServletRequest request = (HttpServletRequest)pContext.getRequest();
      return new EnumeratedMap() {
         public Enumeration enumerateKeys() {
            return request.getParameterNames();
         }

         public Object getValue(Object pKey) {
            return pKey instanceof String ? request.getParameterValues((String)pKey) : null;
         }

         public boolean isMutable() {
            return false;
         }
      };
   }

   public static Map createHeaderMap(PageContext pContext) {
      final HttpServletRequest request = (HttpServletRequest)pContext.getRequest();
      return new EnumeratedMap() {
         public Enumeration enumerateKeys() {
            return request.getHeaderNames();
         }

         public Object getValue(Object pKey) {
            return pKey instanceof String ? request.getHeader((String)pKey) : null;
         }

         public boolean isMutable() {
            return false;
         }
      };
   }

   public static Map createHeadersMap(PageContext pContext) {
      final HttpServletRequest request = (HttpServletRequest)pContext.getRequest();
      return new EnumeratedMap() {
         public Enumeration enumerateKeys() {
            return request.getHeaderNames();
         }

         public Object getValue(Object pKey) {
            if (!(pKey instanceof String)) {
               return null;
            } else {
               List l = new ArrayList();
               Enumeration enum_ = request.getHeaders((String)pKey);
               if (enum_ != null) {
                  while(enum_.hasMoreElements()) {
                     l.add(enum_.nextElement());
                  }
               }

               String[] ret = (String[])((String[])l.toArray(new String[l.size()]));
               return ret;
            }
         }

         public boolean isMutable() {
            return false;
         }
      };
   }

   public static Map createInitParamMap(PageContext pContext) {
      final ServletContext context = pContext.getServletContext();
      return new EnumeratedMap() {
         public Enumeration enumerateKeys() {
            return context.getInitParameterNames();
         }

         public Object getValue(Object pKey) {
            return pKey instanceof String ? context.getInitParameter((String)pKey) : null;
         }

         public boolean isMutable() {
            return false;
         }
      };
   }

   public static Map createCookieMap(PageContext pContext) {
      HttpServletRequest request = (HttpServletRequest)pContext.getRequest();
      Cookie[] cookies = request.getCookies();
      Map ret = new HashMap();

      for(int i = 0; cookies != null && i < cookies.length; ++i) {
         Cookie cookie = cookies[i];
         if (cookie != null) {
            String name = cookie.getName();
            if (!ret.containsKey(name)) {
               ret.put(name, cookie);
            }
         }
      }

      return ret;
   }
}

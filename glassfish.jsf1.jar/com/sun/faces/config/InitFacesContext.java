package com.sun.faces.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.servlet.ServletContext;

class InitFacesContext extends FacesContext {
   private ExternalContext ec;
   private UIViewRoot viewRoot;
   private FacesContext orig;

   public InitFacesContext(ServletContext sc) {
      this.ec = new ServletContextAdapter(sc);
      this.orig = FacesContext.getCurrentInstance();
      setCurrentInstance(this);
   }

   public Application getApplication() {
      ApplicationFactory factory = (ApplicationFactory)FactoryFinder.getFactory("javax.faces.application.ApplicationFactory");
      return factory.getApplication();
   }

   public Iterator getClientIdsWithMessages() {
      List list = Collections.emptyList();
      return list.iterator();
   }

   public ExternalContext getExternalContext() {
      return this.ec;
   }

   public FacesMessage.Severity getMaximumSeverity() {
      return FacesMessage.SEVERITY_INFO;
   }

   public Iterator getMessages() {
      List list = Collections.emptyList();
      return list.iterator();
   }

   public Iterator getMessages(String clientId) {
      return this.getMessages();
   }

   public RenderKit getRenderKit() {
      return null;
   }

   public boolean getRenderResponse() {
      return true;
   }

   public boolean getResponseComplete() {
      return true;
   }

   public ResponseStream getResponseStream() {
      return null;
   }

   public void setResponseStream(ResponseStream responseStream) {
   }

   public ResponseWriter getResponseWriter() {
      return null;
   }

   public void setResponseWriter(ResponseWriter responseWriter) {
   }

   public UIViewRoot getViewRoot() {
      if (this.viewRoot == null) {
         this.viewRoot = new UIViewRoot();
         this.viewRoot.setLocale(Locale.getDefault());
      }

      return this.viewRoot;
   }

   public void setViewRoot(UIViewRoot root) {
   }

   public void addMessage(String clientId, FacesMessage message) {
   }

   public void release() {
      setCurrentInstance(this.orig);
   }

   public void renderResponse() {
   }

   public void responseComplete() {
   }

   public ELContext getELContext() {
      return new ELContext() {
         public ELResolver getELResolver() {
            return null;
         }

         public FunctionMapper getFunctionMapper() {
            return null;
         }

         public VariableMapper getVariableMapper() {
            return null;
         }
      };
   }

   static class ApplicationMap extends AbstractMap {
      private final ServletContext servletContext;

      ApplicationMap(ServletContext servletContext) {
         this.servletContext = servletContext;
      }

      public Object get(Object key) {
         if (key == null) {
            throw new NullPointerException();
         } else {
            return this.servletContext.getAttribute(key.toString());
         }
      }

      public Object put(Object key, Object value) {
         if (key == null) {
            throw new NullPointerException();
         } else {
            String keyString = key.toString();
            Object result = this.servletContext.getAttribute(keyString);
            this.servletContext.setAttribute(keyString, value);
            return result;
         }
      }

      public Object remove(Object key) {
         if (key == null) {
            return null;
         } else {
            String keyString = key.toString();
            Object result = this.servletContext.getAttribute(keyString);
            this.servletContext.removeAttribute(keyString);
            return result;
         }
      }

      public Set entrySet() {
         Set result = new Set() {
            public boolean add(Map.Entry e) {
               boolean didContain = null != ApplicationMap.this.servletContext.getAttribute((String)e.getKey());
               ApplicationMap.this.servletContext.setAttribute((String)e.getKey(), e.getValue());
               return !didContain;
            }

            public boolean addAll(Collection c) {
               boolean curModified = false;
               boolean modified = false;
               Iterator i$ = c.iterator();

               while(i$.hasNext()) {
                  Map.Entry cur = (Map.Entry)i$.next();
                  curModified = this.add(cur);
                  if (curModified) {
                     modified = true;
                  }
               }

               return modified;
            }

            public void clear() {
               Enumeration names = ApplicationMap.this.servletContext.getAttributeNames();
               String cur = null;

               while(names.hasMoreElements()) {
                  cur = (String)names.nextElement();
                  ApplicationMap.this.servletContext.removeAttribute(cur);
               }

            }

            public boolean contains(Object o) {
               Map.Entry e = (Map.Entry)o;
               return null != ApplicationMap.this.servletContext.getAttribute((String)e.getKey());
            }

            public boolean containsAll(Collection o) {
               Iterator i$ = o.iterator();

               Map.Entry cur;
               do {
                  if (!i$.hasNext()) {
                     return true;
                  }

                  cur = (Map.Entry)i$.next();
               } while(null != ApplicationMap.this.servletContext.getAttribute((String)cur.getKey()));

               return false;
            }

            public boolean isEmpty() {
               return ApplicationMap.this.servletContext.getAttributeNames().hasMoreElements();
            }

            public Iterator iterator() {
               return new Iterator() {
                  private Enumeration enumer;
                  String key;

                  {
                     this.enumer = ApplicationMap.this.servletContext.getAttributeNames();
                  }

                  public boolean hasNext() {
                     boolean result = false;
                     if (null != this.enumer) {
                        result = this.enumer.hasMoreElements();
                     }

                     return result;
                  }

                  public Map.Entry next() {
                     if (null == this.enumer) {
                        throw new NoSuchElementException();
                     } else {
                        this.key = (String)this.enumer.nextElement();
                        Object value = ApplicationMap.this.servletContext.getAttribute(this.key);
                        Map.Entry result = new SimpleEntry(this.key, value);
                        return result;
                     }
                  }

                  public void remove() {
                     if (null == this.key) {
                        this.next();
                     }

                     ApplicationMap.this.servletContext.removeAttribute(this.key);
                  }
               };
            }

            public boolean remove(Object o) {
               Map.Entry e = (Map.Entry)o;
               boolean result = null != ApplicationMap.this.servletContext.getAttribute((String)e.getKey());
               if (result) {
                  ApplicationMap.this.servletContext.removeAttribute((String)e.getKey());
               }

               return result;
            }

            public boolean removeAll(Collection c) {
               boolean modified = false;
               Iterator i$ = c.iterator();

               while(i$.hasNext()) {
                  Map.Entry cur = (Map.Entry)i$.next();
                  if (null != ApplicationMap.this.servletContext.getAttribute((String)cur.getKey())) {
                     modified = true;
                     ApplicationMap.this.servletContext.removeAttribute((String)cur.getKey());
                  }
               }

               return modified;
            }

            public boolean retainAll(Collection c) {
               boolean modified = false;
               Map attrNames = new HashMap();
               Enumeration enumer = ApplicationMap.this.servletContext.getAttributeNames();

               while(enumer.hasMoreElements()) {
                  attrNames.put(enumer.nextElement(), Boolean.TRUE);
               }

               Iterator i$ = c.iterator();

               while(i$.hasNext()) {
                  Map.Entry cur = (Map.Entry)i$.next();
                  attrNames.remove(cur.getKey());
               }

               i$ = attrNames.keySet().iterator();

               while(i$.hasNext()) {
                  String curx = (String)i$.next();
                  if (null != ApplicationMap.this.servletContext.getAttribute(curx)) {
                     modified = true;
                     ApplicationMap.this.servletContext.removeAttribute(curx);
                  }
               }

               return modified;
            }

            public int size() {
               int size = 0;

               for(Enumeration enumer = ApplicationMap.this.servletContext.getAttributeNames(); enumer.hasMoreElements(); ++size) {
                  enumer.nextElement();
               }

               return size;
            }

            public Object[] toArray() {
               Object[] result = new Object[this.size()];
               Enumeration enumer = ApplicationMap.this.servletContext.getAttributeNames();

               String cur;
               for(int i = 0; enumer.hasMoreElements(); result[i] = new SimpleEntry(cur, ApplicationMap.this.servletContext.getAttribute(cur))) {
                  cur = (String)enumer.nextElement();
               }

               return result;
            }

            public Object[] toArray(Object[] t) {
               Object[] result = (Object[])t;
               Enumeration enumer = ApplicationMap.this.servletContext.getAttributeNames();

               String cur;
               for(int i = 0; enumer.hasMoreElements(); result[i] = new SimpleEntry(cur, ApplicationMap.this.servletContext.getAttribute(cur))) {
                  cur = (String)enumer.nextElement();
               }

               return t;
            }
         };
         return result;
      }

      public boolean equals(Object obj) {
         return obj != null && obj instanceof ApplicationMap && super.equals(obj);
      }

      public int hashCode() {
         int hashCode = 7 * this.servletContext.hashCode();

         Object o;
         for(Iterator i$ = this.entrySet().iterator(); i$.hasNext(); hashCode += o.hashCode()) {
            o = i$.next();
         }

         return hashCode;
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public void putAll(Map t) {
         throw new UnsupportedOperationException();
      }

      public boolean containsKey(Object key) {
         return this.servletContext.getAttribute(key.toString()) != null;
      }

      public static class SimpleEntry implements Map.Entry, Serializable {
         private static final long serialVersionUID = -8499721149061103585L;
         private final Object key;
         private Object value;

         private static boolean eq(Object o1, Object o2) {
            return o1 == null ? o2 == null : o1.equals(o2);
         }

         public SimpleEntry(Object key, Object value) {
            this.key = key;
            this.value = value;
         }

         public SimpleEntry(Map.Entry entry) {
            this.key = entry.getKey();
            this.value = entry.getValue();
         }

         public Object getKey() {
            return this.key;
         }

         public Object getValue() {
            return this.value;
         }

         public Object setValue(Object value) {
            Object oldValue = this.value;
            this.value = value;
            return oldValue;
         }

         public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
               return false;
            } else {
               Map.Entry e = (Map.Entry)o;
               return eq(this.key, e.getKey()) && eq(this.value, e.getValue());
            }
         }

         public int hashCode() {
            return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
         }

         public String toString() {
            return this.key + "=" + this.value;
         }
      }
   }

   private static class ServletContextAdapter extends ExternalContext {
      private ServletContext servletContext = null;
      private ApplicationMap applicationMap = null;

      public ServletContextAdapter(ServletContext sc) {
         this.servletContext = sc;
      }

      public void dispatch(String path) throws IOException {
      }

      public String encodeActionURL(String url) {
         return null;
      }

      public String encodeNamespace(String name) {
         return null;
      }

      public String encodeResourceURL(String url) {
         return null;
      }

      public Map getApplicationMap() {
         if (this.applicationMap == null) {
            this.applicationMap = new ApplicationMap(this.servletContext);
         }

         return this.applicationMap;
      }

      public String getAuthType() {
         return null;
      }

      public Object getContext() {
         return this.servletContext;
      }

      public String getInitParameter(String name) {
         return null;
      }

      public Map getInitParameterMap() {
         return null;
      }

      public String getRemoteUser() {
         return null;
      }

      public Object getRequest() {
         return null;
      }

      public void setRequest(Object request) {
      }

      public String getRequestContextPath() {
         return null;
      }

      public Map getRequestCookieMap() {
         return null;
      }

      public Map getRequestHeaderMap() {
         return null;
      }

      public Map getRequestHeaderValuesMap() {
         return null;
      }

      public Locale getRequestLocale() {
         return null;
      }

      public Iterator getRequestLocales() {
         return null;
      }

      public Map getRequestMap() {
         return null;
      }

      public Map getRequestParameterMap() {
         return null;
      }

      public Iterator getRequestParameterNames() {
         return null;
      }

      public Map getRequestParameterValuesMap() {
         return null;
      }

      public String getRequestPathInfo() {
         return null;
      }

      public String getRequestServletPath() {
         return null;
      }

      public String getRequestContentType() {
         return null;
      }

      public String getResponseContentType() {
         return null;
      }

      public URL getResource(String path) throws MalformedURLException {
         return this.servletContext.getResource(path);
      }

      public InputStream getResourceAsStream(String path) {
         return this.servletContext.getResourceAsStream(path);
      }

      public Set getResourcePaths(String path) {
         return this.servletContext.getResourcePaths(path);
      }

      public Object getResponse() {
         return null;
      }

      public void setResponse(Object response) {
      }

      public Object getSession(boolean create) {
         return null;
      }

      public Map getSessionMap() {
         return null;
      }

      public Principal getUserPrincipal() {
         return null;
      }

      public boolean isUserInRole(String role) {
         return false;
      }

      public void log(String message) {
         this.servletContext.log(message);
      }

      public void log(String message, Throwable exception) {
         this.servletContext.log(message, exception);
      }

      public void redirect(String url) throws IOException {
      }

      public String getRequestCharacterEncoding() {
         return null;
      }

      public void setRequestCharacterEncoding(String requestCharacterEncoding) throws UnsupportedEncodingException {
      }

      public String getResponseCharacterEncoding() {
         return null;
      }

      public void setResponseCharacterEncoding(String responseCharacterEncoding) {
      }
   }
}

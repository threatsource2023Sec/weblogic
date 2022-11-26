package weblogic.cache.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import weblogic.cache.CacheException;
import weblogic.cache.CacheValue;
import weblogic.cache.KeyEnumerator;
import weblogic.cache.webapp.KeySet;
import weblogic.cache.webapp.ServletCacheUtils;
import weblogic.cache.webapp.WebAppCacheSystem;
import weblogic.servlet.internal.ServletOutputStreamImpl;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.utils.StackTraceUtils;

public class CacheTag extends BodyTagSupport implements TryCatchFinally {
   static final long serialVersionUID = 2706226325452664446L;
   private static boolean debug = false;
   private static boolean verbose = false;
   private int timeout = -1;
   private String timeoutString;
   private String scope = "application";
   private String name;
   private int size = -1;
   private String key;
   private boolean async = false;
   private String vars;
   private String flush;
   private boolean trigger;
   private static boolean initialized;
   private long startTime;
   private KeySet keySet;
   private WebAppCacheSystem cs = new WebAppCacheSystem();
   private CacheValue cache;
   private static boolean nestwarning;
   private CacheValue oldCache;
   private boolean failed;
   protected Map savedPageContextValues;
   private static final Object DELETE = new Object();
   private boolean cacheUpdate = false;
   private Map savedPageScope;
   private Map savedRequestScope;
   private Map savedSessionScope;
   private Map savedApplicationScope;
   private Map updatedPageScope;
   private Map updatedRequestScope;
   private Map updatedSessionScope;
   private Map updatedApplicationScope;

   public static boolean getDebug() {
      return debug;
   }

   public static void setDebug(boolean debug) {
      CacheTag.debug = debug;
   }

   public static boolean getVerbose() {
      return verbose;
   }

   public static void setVerbose(boolean verbose) {
      CacheTag.verbose = verbose;
   }

   public void setTimeout(String timeoutString) throws CacheException {
      this.timeout = ServletCacheUtils.getTimeout(timeoutString);
      if (debug) {
         System.out.println("Timeout: " + this.timeout);
      }

      this.timeoutString = timeoutString;
   }

   public String getTimeout() {
      return this.timeoutString;
   }

   public void setScope(String scope) {
      this.scope = scope;
   }

   public String getScope() {
      return this.scope;
   }

   public void setName(String name) {
      this.name = name.intern();
   }

   public String getName() {
      HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
      if (this.name == null) {
         Integer instanceNumObject = (Integer)this.pageContext.getAttribute("weblogicx.cache.tag.CacheTag.instanceNum");
         int instanceNum;
         if (instanceNumObject == null) {
            instanceNum = 0;
         } else {
            instanceNum = instanceNumObject + 1;
         }

         this.pageContext.setAttribute("weblogicx.cache.tag.CacheTag.instanceNum", new Integer(instanceNum));
         Object uri = request.getAttribute("javax.servlet.include.request_uri");
         if (uri == null) {
            uri = request.getRequestURI();
         }

         this.name = ("weblogicx.cache.tag.CacheTag." + uri + "." + instanceNum).intern();
      }

      return this.name;
   }

   public void setSize(int size) {
      this.size = size;
   }

   public int getSize() {
      return this.size;
   }

   public void setKey(String key) {
      this.key = key;
   }

   public String getKey() {
      return this.key;
   }

   public void setAsync(boolean async) {
      this.async = async;
   }

   public boolean getAsync() {
      return this.async;
   }

   public void setVars(String vars) {
      this.vars = vars;
   }

   public String getVars() {
      return this.vars;
   }

   public void setFlush(String flush) {
      this.flush = flush;
   }

   public String getFlush() {
      return this.flush;
   }

   public void setTrigger(boolean trigger) {
      this.trigger = trigger;
   }

   public boolean getTrigger() {
      return this.trigger;
   }

   public int doStartTag() throws JspException {
      try {
         if (this.flush == null) {
            ServletRequest request = this.pageContext.getRequest();
            if (request.getAttribute("weblogic.cache.tag.CacheTag.caching") == null) {
               request.setAttribute("weblogic.cache.tag.CacheTag.caching", "true");
            } else if (!nestwarning) {
               this.pageContext.getServletContext().log("Nested cache tags have been detected.  Inner cache tags will only be updated when their containing tag is updated.");
               nestwarning = true;
            }
         }

         this.cs.setPageContext(this.pageContext);
         String method = ((HttpServletRequest)this.pageContext.getRequest()).getMethod();
         if (method.equals("POST") || method.equals("PUT")) {
            this.async = false;
         }

         return this.key == null ? this.doStartCache() : this.doStartKeyedCache();
      } catch (CacheException var2) {
         throw new JspException("We experienced an underlying cache exception during revert, cache may be corrupt: " + StackTraceUtils.throwable2StackTrace(var2));
      }
   }

   protected int doStartCache() throws CacheException, JspException {
      boolean flush = "true".equals(this.flush) || "now".equals(this.flush);
      if (flush || this.trigger) {
         this.cs.flushCache(this.scope, this.getName());
         if (flush) {
            return 0;
         }
      }

      if (this.async) {
         this.oldCache = this.cs.getCurrentCache(this.scope, this.getName());
      }

      this.cache = this.cs.getCache(this.scope, this.getName());
      if (this.cache != null && "lazy".equals(this.flush)) {
         this.cache.setFlush(true);
         return 0;
      } else if (this.cache == null) {
         if (this.async && this.oldCache != null) {
            this.asyncForward();
         }

         this.startTime = System.currentTimeMillis();
         this.cache = new CacheValue();
         this.pageContext.setAttribute(this.getName(), this.cache);
         return 2;
      } else {
         this.pageContext.setAttribute(this.getName(), this.cache);

         try {
            this.pageContext.getOut().print((String)this.cache.getContent());
         } catch (IOException var3) {
         }

         this.revertPageContextVariables();
         ServletCacheUtils.restoreVars(this.cs, this.cache, this.vars, "request");
         return 0;
      }
   }

   protected int doEndCache() throws CacheException {
      this.cache.setContent(this.getBodyContent().getString());
      this.cache.setTimeout(this.timeout);
      ServletCacheUtils.saveVars(this.cs, this.cache, this.vars, "request");
      int time = (int)(System.currentTimeMillis() - this.startTime);
      this.cs.setCache(this.scope, this.getName(), this.cache, time);

      try {
         if (!this.cacheUpdate) {
            this.getBodyContent().getEnclosingWriter().print((String)this.cache.getContent());
         }
      } catch (IOException var3) {
      }

      return 0;
   }

   protected int doStartKeyedCache() throws CacheException, JspException {
      KeySet keySet = this.getKeySet();
      boolean flush = "true".equals(this.flush) || "now".equals(this.flush);
      if (flush || this.trigger) {
         this.cs.flushCache(this.scope, this.getName(), keySet);
         if (flush) {
            return 0;
         }
      }

      if (this.async) {
         this.oldCache = this.cs.getCurrentCache(this.scope, this.getName(), this.size, keySet);
      }

      this.cache = this.cs.getCache(this.scope, this.getName(), this.size, keySet);
      if (this.cache != null && "lazy".equals(this.flush)) {
         this.cache.setFlush(true);
         return 0;
      } else if (this.cache == null) {
         if (this.async && this.oldCache != null) {
            this.asyncForward();
         }

         this.startTime = System.currentTimeMillis();
         this.cache = new CacheValue();
         this.pageContext.setAttribute(this.getName(), this.cache);
         return 2;
      } else {
         this.pageContext.setAttribute(this.getName(), this.cache);

         try {
            if (!this.cacheUpdate) {
               this.pageContext.getOut().print((String)this.cache.getContent());
            }
         } catch (IOException var4) {
         }

         this.revertPageContextVariables();
         ServletCacheUtils.restoreVars(this.cs, this.cache, this.vars, "request");
         return 0;
      }
   }

   protected int doEndKeyedCache() throws CacheException {
      KeySet keySet = this.getKeySet();
      this.cache.setContent(this.getBodyContent().getString());
      this.cache.setTimeout(this.timeout);
      ServletCacheUtils.saveVars(this.cs, this.cache, this.vars, "request");
      int time = (int)(System.currentTimeMillis() - this.startTime);
      this.cs.setCache(this.scope, this.getName(), this.size, keySet, this.cache, time);

      try {
         this.getBodyContent().getEnclosingWriter().print((String)this.cache.getContent());
      } catch (IOException var4) {
      }

      return 0;
   }

   public int doEndTag() {
      return this.cacheUpdate ? 5 : 6;
   }

   public int doAfterBody() throws JspException {
      if (this.failed) {
         return 0;
      } else {
         try {
            this.revertPageContextVariables();
            if (this.cacheUpdate) {
               this.revertKeysAfterCacheUpdate();
            }

            return this.key == null ? this.doEndCache() : this.doEndKeyedCache();
         } catch (CacheException var2) {
            throw new JspException("We experienced an underlying cache exception during revert, cache may be corrupt: " + StackTraceUtils.throwable2StackTrace(var2));
         }
      }
   }

   public void release() {
      try {
         this.cs.releaseAllLocks();
      } catch (CacheException var2) {
      }

      this.timeout = -1;
      this.timeoutString = null;
      this.scope = "application";
      this.name = null;
      this.size = -1;
      this.key = null;
      this.vars = null;
      this.flush = null;
      this.keySet = null;
      this.savedPageContextValues = null;
      this.cache = null;
      this.cacheUpdate = false;
      this.savedPageScope = null;
      this.savedRequestScope = null;
      this.savedSessionScope = null;
      this.savedApplicationScope = null;
      this.updatedPageScope = null;
      this.updatedRequestScope = null;
      this.updatedSessionScope = null;
      this.updatedApplicationScope = null;
   }

   public void doCatch(Throwable t) throws Throwable {
      this.pageContext.getServletContext().log("Cache " + this.getName() + " failed to refresh with an exception: " + StackTraceUtils.throwable2StackTrace(t));
      CacheValue oldCache = null;
      boolean var20 = false;

      try {
         var20 = true;
         this.revertPageContextVariables();
         if (this.key == null) {
            oldCache = this.cs.getCurrentCache(this.scope, this.getName());
         } else {
            oldCache = this.cs.getCurrentCache(this.scope, this.getName(), this.size, this.getKeySet());
         }

         if (oldCache == null) {
            this.pageContext.removeAttribute(this.getName());
            throw t;
         }

         try {
            this.pageContext.getOut().print(oldCache.getContent());
         } catch (IOException var23) {
         }

         ServletCacheUtils.restoreVars(this.cs, this.cache, this.vars, "request");
         this.failed = true;
         this.pageContext.setAttribute(this.getName(), oldCache);
         var20 = false;
      } finally {
         if (var20) {
            int time = (int)(System.currentTimeMillis() - this.startTime);

            try {
               if (this.key == null) {
                  this.cs.setCache(this.scope, this.getName(), oldCache, time);
               } else {
                  this.cs.setCache(this.scope, this.getName(), this.size, this.keySet, oldCache, time);
               }
            } finally {
               this.release();
            }

         }
      }

      int time = (int)(System.currentTimeMillis() - this.startTime);

      try {
         if (this.key == null) {
            this.cs.setCache(this.scope, this.getName(), oldCache, time);
         } else {
            this.cs.setCache(this.scope, this.getName(), this.size, this.keySet, oldCache, time);
         }
      } finally {
         this.release();
      }

   }

   public void doFinally() {
      this.oldCache = null;
      this.failed = false;
      ServletRequest request = this.pageContext.getRequest();
      request.removeAttribute("weblogic.cache.tag.CacheTag.caching");
   }

   protected void revertPageContextVariables() {
      if (this.key != null && this.savedPageContextValues != null) {
         Iterator i = this.savedPageContextValues.keySet().iterator();

         while(i.hasNext()) {
            String key = (String)i.next();
            Object value = this.savedPageContextValues.get(key);
            if (value == DELETE) {
               this.pageContext.removeAttribute(key, 1);
            } else {
               this.pageContext.setAttribute(key, value);
            }
         }
      }

   }

   protected void saveKeysBeforeForward() throws CacheException, JspException {
      if (this.key != null) {
         this.savedPageScope = new HashMap();
         this.savedRequestScope = new HashMap();
         this.savedSessionScope = new HashMap();
         this.savedApplicationScope = new HashMap();
         this.storeKeyValues(this.savedPageScope, this.savedRequestScope, this.savedSessionScope, this.savedApplicationScope);
      }
   }

   protected void revertKeysAfterForward() throws JspException {
      if (this.key != null) {
         this.revertValues(this.savedPageScope, this.savedRequestScope, this.savedSessionScope, this.savedApplicationScope);
      }
   }

   protected void saveKeysBeforeCacheUpdate() throws CacheException, JspException {
      if (this.key != null) {
         this.updatedPageScope = new HashMap();
         this.updatedRequestScope = new HashMap();
         this.updatedSessionScope = new HashMap();
         this.updatedApplicationScope = new HashMap();
         this.storeKeyValues(this.updatedPageScope, this.updatedRequestScope, this.updatedSessionScope, this.updatedApplicationScope);
      }
   }

   protected void revertKeysAfterCacheUpdate() throws JspException {
      if (this.key != null) {
         this.revertValues(this.updatedPageScope, this.updatedRequestScope, this.updatedSessionScope, this.updatedApplicationScope);
      }
   }

   protected void storeKeyValues(Map storePageScope, Map storeRequestScope, Map storeSessionScope, Map storeApplicationScope) throws CacheException, JspException {
      KeyEnumerator keys = new KeyEnumerator(this.key);

      while(true) {
         while(true) {
            String key;
            Object value;
            label50:
            do {
               while(keys.hasMoreKeys()) {
                  key = keys.getNextKey();
                  String keyScope = keys.getKeyScope();
                  value = this.cs.getValueFromScope(keyScope, key);
                  if (keyScope.equals("any")) {
                     continue label50;
                  }

                  if (keyScope.equals("page")) {
                     if (value != null) {
                        storePageScope.put(key, value);
                     }
                  } else if (keyScope.equals("request")) {
                     if (value != null) {
                        storeRequestScope.put(key, value);
                     }
                  } else if (keyScope.equals("session")) {
                     if (value != null) {
                        storeSessionScope.put(key, value);
                     }
                  } else if (keyScope.equals("application") && value != null) {
                     storeApplicationScope.put(key, value);
                  }
               }

               return;
            } while(this.pageContext.getRequest().getParameter(key) != null);

            if (this.pageContext.getAttribute(key) != null) {
               storePageScope.put(key, value);
            } else if (this.pageContext.getRequest().getAttribute(key) != null) {
               storeRequestScope.put(key, value);
            } else {
               HttpSession session = this.pageContext.getSession();
               if (session != null && session.getAttribute(key) != null) {
                  storeSessionScope.put(key, value);
               } else if (this.pageContext.getServletContext().getAttribute(key) != null) {
                  storeApplicationScope.put(key, value);
               }
            }
         }
      }
   }

   protected void revertValues(Map revertedPageScope, Map revertedRequestScope, Map revertedSessionScope, Map revertedApplicationScope) throws JspException {
      KeyEnumerator keys = new KeyEnumerator(this.key);

      while(keys.hasMoreKeys()) {
         String key = keys.getNextKey();
         String keyScope = keys.getKeyScope();
         Object value;
         if (keyScope.equals("any")) {
            if ((value = revertedPageScope.get(key)) != null) {
               this.pageContext.setAttribute(key, value);
            } else if ((value = revertedRequestScope.get(key)) != null) {
               this.pageContext.getRequest().setAttribute(key, value);
            } else {
               HttpSession session;
               if ((value = revertedSessionScope.get(key)) != null) {
                  session = this.pageContext.getSession();
                  if (session == null) {
                     throw new JspException("Session scope specified but this page has no session");
                  }

                  session.setAttribute(key, value);
               } else if ((value = revertedApplicationScope.get(key)) != null) {
                  this.pageContext.getServletContext().setAttribute(key, value);
               } else if (this.pageContext.getRequest().getParameter(key) == null) {
                  this.pageContext.removeAttribute(key);
                  this.pageContext.getRequest().removeAttribute(key);
                  session = this.pageContext.getSession();
                  if (session != null) {
                     session.removeAttribute(key);
                  }

                  this.pageContext.getServletContext().removeAttribute(key);
               }
            }
         } else if (keyScope.equals("page")) {
            value = revertedPageScope.get(key);
            if (value == null) {
               this.pageContext.removeAttribute(key);
            } else {
               this.pageContext.setAttribute(key, value);
            }
         } else if (keyScope.equals("request")) {
            value = revertedRequestScope.get(key);
            if (value == null) {
               this.pageContext.getRequest().removeAttribute(key);
            } else {
               this.pageContext.getRequest().setAttribute(key, value);
            }
         } else if (keyScope.equals("session")) {
            HttpSession session = this.pageContext.getSession();
            if (session == null) {
               throw new JspException("Session scope specified but this page has no session");
            }

            Object value = revertedSessionScope.get(key);
            if (value == null) {
               session.removeAttribute(key);
            } else {
               session.setAttribute(key, value);
            }
         } else if (keyScope.equals("application")) {
            value = revertedApplicationScope.get(key);
            if (value == null) {
               this.pageContext.getServletContext().removeAttribute(key);
            } else {
               this.pageContext.getServletContext().setAttribute(key, value);
            }
         }
      }

   }

   protected void asyncForward() throws CacheException, JspException {
      try {
         this.saveKeysBeforeForward();
         if (verbose) {
            System.out.println("Forwarding request");
         }

         this.pageContext.forward(((HttpServletRequest)this.pageContext.getRequest()).getRequestURI().substring(((WebAppServletContext)this.pageContext.getServletContext()).getContextPath().length()));
         ServletOutputStreamImpl sosi = (ServletOutputStreamImpl)this.pageContext.getResponse().getOutputStream();
         if (!this.pageContext.getResponse().isCommitted()) {
            ((HttpServletResponse)this.pageContext.getResponse()).setHeader("Content-Length", "" + sosi.getCount());
            sosi.flush();
         }

         this.saveKeysBeforeCacheUpdate();
         this.revertKeysAfterForward();
      } catch (IOException var2) {
         throw new JspException("Could not asynchronously execute page: " + var2);
      } catch (ServletException var3) {
         throw new JspException("Could not asynchronously execute page: " + var3);
      }

      this.cacheUpdate = true;
      this.startTime = System.currentTimeMillis();
      if (verbose) {
         System.out.println("Doing cache update");
      }

   }

   protected KeySet getKeySet() throws CacheException {
      if (this.keySet != null) {
         return this.keySet;
      } else {
         this.keySet = new KeySet(this.cs);
         KeyEnumerator ke = new KeyEnumerator(this.key);

         while(ke.hasMoreKeys()) {
            String key = ke.getNextKey();
            String scope = ke.getKeyScope();
            Object value = this.keySet.addKey(scope, key);
            if (this.savedPageContextValues == null) {
               this.savedPageContextValues = new HashMap();
            }

            Object current;
            if ((current = this.pageContext.getAttribute(key)) != null) {
               this.savedPageContextValues.put(key, current);
            } else {
               this.savedPageContextValues.put(key, DELETE);
            }

            if (value == null) {
               this.pageContext.removeAttribute(key);
            } else {
               this.pageContext.setAttribute(key, value);
            }
         }

         return this.keySet;
      }
   }
}

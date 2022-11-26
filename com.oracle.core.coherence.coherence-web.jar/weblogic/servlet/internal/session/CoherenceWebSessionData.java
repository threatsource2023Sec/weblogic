package weblogic.servlet.internal.session;

import com.tangosol.coherence.servlet.HttpSessionCollection;
import com.tangosol.coherence.servlet.HttpSessionModel;
import com.tangosol.coherence.servlet.SessionHelper;
import com.tangosol.util.ChainedEnumerator;
import com.tangosol.util.Filter;
import com.tangosol.util.FilterEnumerator;
import com.tangosol.util.IteratorEnumerator;
import com.tangosol.util.SafeHashMap;
import com.tangosol.util.filter.NotFilter;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpSession;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.WebAppServletContext;

public class CoherenceWebSessionData extends SessionData {
   protected HttpSession session;
   protected Map localAttributes;
   protected WebLogicSPISessionHelper sessionHelper;
   protected transient boolean isNew;
   protected static final String CW_APP_VERSION = "coherence-app-version";
   private final transient String internalAttributePrefix;
   private final transient Filter internalAttributeFilter;
   private final transient NotFilter externalAttributeFilter;

   public CoherenceWebSessionData() {
      this.isNew = false;
      this.internalAttributePrefix = null;
      this.internalAttributeFilter = null;
      this.externalAttributeFilter = null;
   }

   public CoherenceWebSessionData(CoherenceWebSessionContextImpl context, SessionHelper helper, HttpSessionCollection collection) {
      this(context, helper, collection, (String)null, false);
   }

   public CoherenceWebSessionData(CoherenceWebSessionContextImpl context, SessionHelper helper, HttpSessionCollection collection, String sessionId, boolean createModel) {
      this.isNew = false;
      if (context != null && helper != null && collection != null) {
         this.internalAttributePrefix = context.internalAttributePrefix;
         this.internalAttributeFilter = context.internalAttributeFilter;
         this.externalAttributeFilter = context.externalAttributeFilter;
         boolean newSession = createModel;
         if (sessionId == null) {
            this.session = new HttpSessionImpl(helper, collection);
            newSession = true;
         } else {
            this.session = new HttpSessionImpl(helper, collection, sessionId, createModel);
         }

         this.localAttributes = new SafeHashMap();
         this.sessionContext = context;
         this.sessionHelper = (WebLogicSPISessionHelper)helper;
         this.id = context.adjustIdForWebLogic(this.session.getId());
         this.creationTime = this.session.getCreationTime();
         this.accessTime = this.session.getLastAccessedTime();
         this.maxInactiveInterval = this.session.getMaxInactiveInterval();
         this.isNew = this.session.isNew();
         this.setValid(true);
         if (newSession) {
            this.versionId = context.getServletContext().getVersionId();
            this.setInternalAttribute("coherence-app-version", this.versionId);
         } else {
            this.versionId = (String)this.getInternalAttribute("coherence-app-version");
         }

         if (this.isNew) {
            this.sessionContext.incrementOpenSessionsCount();
         }

         this.getMonitoringId();
      } else {
         throw new IllegalArgumentException("CoherenceWebSessionData constructor arguments can not be null");
      }
   }

   public Object getInternalAttribute(String name) {
      this.checkValid();
      Object value = this.localAttributes.get(name);
      if (value == null) {
         value = this.session.getAttribute(this.internalAttributePrefix + name);
      }

      return value;
   }

   public void setInternalAttribute(String name, Object value) {
      if (value == null) {
         this.removeInternalAttribute(name);
      }

      this.checkValid();
      if (value instanceof Serializable) {
         this.session.setAttribute(this.internalAttributePrefix + name, value);
         this.localAttributes.remove(name);
      } else {
         this.session.removeAttribute(this.internalAttributePrefix + name);
         this.localAttributes.put(name, value);
      }

   }

   public void removeInternalAttribute(String name) {
      this.checkValid();
      if (this.localAttributes.remove(name) == null) {
         this.session.removeAttribute(this.internalAttributePrefix + name);
      }

   }

   public Enumeration getInternalAttributeNames() {
      this.checkValid();
      Enumeration e = this.session.getAttributeNames();
      final int prefixLength = this.internalAttributePrefix.length();
      Enumeration e = new FilterEnumerator(e, this.externalAttributeFilter) {
         public Object nextElement() {
            String name = (String)super.nextElement();
            return name.substring(prefixLength);
         }

         public Object next() {
            String name = (String)super.next();
            return name.substring(prefixLength);
         }
      };
      if (!this.localAttributes.isEmpty()) {
         e = new ChainedEnumerator(new IteratorEnumerator(this.localAttributes.keySet().iterator()), (Enumeration)e);
      }

      return (Enumeration)e;
   }

   protected void logTransientAttributeError(String name) {
      if (this.getContext().getConfigMgr().isSaveSessionsOnRedeployEnabled()) {
         HTTPSessionLogger.logTransientMemoryAttributeError(this.getWebAppServletContext().getLogContext(), name, this.getId());
      }

   }

   protected String getCoherenceWebSessionId() {
      return this.session.getId();
   }

   protected void removeAttribute(String name, boolean isChange, boolean notify) {
      this.removeAttribute(name);
   }

   public void setAttribute(String name, Object value, boolean notify) {
      this.setAttribute(name, value);
   }

   public void invalidate(boolean notify) {
      if (this.isValid()) {
         this.localAttributes.clear();
         String id = this.session.getId();
         boolean restoreThreadCL = false;
         WebAppServletContext servletContext = this.sessionContext.getServletContext();
         ClassLoader threadCL = Thread.currentThread().getContextClassLoader();
         ClassLoader servletCL = servletContext.getServletClassLoader();
         if (servletCL != threadCL) {
            Thread.currentThread().setContextClassLoader(servletCL);
            restoreThreadCL = true;
         }

         this.session.invalidate();
         if (restoreThreadCL) {
            Thread.currentThread().setContextClassLoader(threadCL);
         }

         this.sessionContext.decrementOpenSessionsCount();
         this.setValid(false);
         HttpServer.SessionLogin sessionLogin = servletContext.getServer().getSessionLogin();
         synchronized(sessionLogin) {
            sessionLogin.unregister(id, servletContext.getContextPath());
         }
      }

   }

   public void invalidate() {
      this.invalidate(true);
   }

   public long getLastAccessedTime() {
      this.checkValid();
      return this.accessTime = this.session.getLastAccessedTime();
   }

   public void setMaxInactiveInterval(int i) {
      this.checkValid();
      this.session.setMaxInactiveInterval(i);
      this.maxInactiveInterval = i;
   }

   public boolean isNew() {
      this.checkValid();
      return this.session.isNew();
   }

   public Enumeration getAttributeNames() {
      this.checkValid();
      return new FilterEnumerator(this.session.getAttributeNames(), this.internalAttributeFilter);
   }

   public Object getAttribute(String name) {
      this.checkValid();
      return this.internalAttributeFilter.evaluate(name) ? this.session.getAttribute(name) : null;
   }

   public void setAttribute(String name, Object object) {
      this.checkValid();
      if (this.internalAttributeFilter.evaluate(name)) {
         this.session.setAttribute(name, object);
      }

   }

   public void removeAttribute(String name) {
      this.checkValid();
      if (this.internalAttributeFilter.evaluate(name)) {
         this.session.removeAttribute(name);
      }

   }

   public String changeSessionId(String newId) {
      CoherenceWebSessionContextImpl ctx = (CoherenceWebSessionContextImpl)this.sessionContext;
      newId = this.sessionHelper.updateSessionId(this.session.getId(), newId);
      this.id = ctx.adjustIdForWebLogic(newId);
      return this.id;
   }

   protected void checkValid() {
      if (!super.isValid()) {
         throw new IllegalStateException("HttpSession is invalid: " + this.id);
      }
   }

   protected void finishedRequest() {
      this.isNew = false;
   }

   public String toString() {
      try {
         return "CoherenceWebSessionData{" + this.session + "} " + super.toString();
      } catch (RuntimeException var2) {
         return "CoherenceWebSessionData{Underlying Coherence*Web session object is invalid}" + super.toString();
      }
   }

   public class HttpSessionImpl extends com.tangosol.coherence.servlet.api23.HttpSessionImpl {
      public HttpSessionImpl(SessionHelper helper, HttpSessionCollection collection) {
         this(helper, collection, collection.create(CoherenceWebSessionData.this).getId(), false);
      }

      public HttpSessionImpl(SessionHelper helper, HttpSessionCollection collection, String sId, boolean createModel) {
         super(helper, collection, sId);
         if (createModel) {
            HttpSessionModel model = collection.create(this, sId);
            if (model == null) {
               throw new IllegalStateException("Could not create session with session id: " + sId);
            }
         }

      }

      public Object getAttribute(String name) {
         this.validateAttributeName(name);
         return super.getAttribute(name);
      }

      public void setAttribute(String name, Object object) {
         this.validateAttributeName(name);
         super.setAttribute(name, object);
      }

      public void removeAttribute(String name) {
         this.validateAttributeName(name);
         super.removeAttribute(name);
      }

      protected HttpSessionModel ensureActiveModel() {
         HttpSessionModel model = this.ensureUsableModel();
         String sId = model.getId();
         HttpSessionCollection collection = this.getCollection();
         if (!collection.isActive(sId)) {
            collection.activate(sId, CoherenceWebSessionData.this);
         }

         return model;
      }

      private void validateAttributeName(String name) {
         if (name == null) {
            throw new IllegalArgumentException("session attribute names cannot be null");
         }
      }

      protected void enter() {
         HttpSessionCollection collection = this.getCollection();
         String sId = this.getId();
         collection.enter(sId, true);
      }
   }
}

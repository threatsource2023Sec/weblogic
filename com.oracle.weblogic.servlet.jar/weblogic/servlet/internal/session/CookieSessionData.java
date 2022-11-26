package weblogic.servlet.internal.session;

import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.Cookie;
import weblogic.servlet.internal.AttributeWrapper;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;

public final class CookieSessionData extends SessionData {
   private static final long serialVersionUID = 1423350728235186420L;
   private ServletResponseImpl response = null;
   private Cookie wlcookie = null;
   private static final String EQL = "/";
   static final String DELIMITER = "|";

   public CookieSessionData(String sessionId, SessionContext context, ServletRequestImpl req, ServletResponseImpl res, Cookie cookie) {
      super(sessionId, context, false);
      this.wlcookie = cookie;
      this.response = res;
      this.initSessionPropsFromCookie(this.wlcookie.getValue());
      if (!context.getConfigMgr().isSessionSharingEnabled()) {
         this.wlcookie.setPath(this.response.processProxyPathHeaders(this.getContextPath()));
      }

      this.response.addCookie(this.wlcookie);
      this.setNew(false);
   }

   public CookieSessionData(String sessionId, SessionContext context, ServletRequestImpl req, ServletResponseImpl res) {
      super(sessionId, context, true);
      this.response = res;
      this.wlcookie = new Cookie(((CookieSessionContext)context).getWLCookieName(), this.getCookieValue());
      if (!context.getConfigMgr().isSessionSharingEnabled()) {
         this.wlcookie.setPath(this.response.processProxyPathHeaders(this.getContextPath()));
      }

      this.response.addCookie(this.wlcookie);
      this.getWebAppServletContext().getEventsManager().notifySessionLifetimeEvent(this, true);
   }

   protected void initRuntime() {
   }

   public void setAttribute(String name, Object value) throws IllegalStateException, IllegalArgumentException {
      synchronized(this.getInternalLock()) {
         if (!(value instanceof String)) {
            throw new IllegalArgumentException("Cookie based sessions support attributes of type \"String\" only; could not set attribute: " + name);
         } else {
            super.setAttribute(name, value);
            this.wlcookie.setValue(this.getCookieValue());
         }
      }
   }

   protected void removeAttribute(String name, boolean isChange) throws IllegalStateException {
      super.removeAttribute(name, isChange);
      this.wlcookie.setValue(this.getCookieValue());
   }

   public void setInternalAttribute(String name, Object value) throws IllegalStateException, IllegalArgumentException {
      synchronized(this.getInternalLock()) {
         if (value instanceof String) {
            super.setInternalAttribute(name, value);
            this.wlcookie.setValue(this.getCookieValue());
         }

      }
   }

   public void removeInternalAttribute(String name) throws IllegalStateException {
      super.removeInternalAttribute(name);
      this.wlcookie.setValue(this.getCookieValue());
   }

   public void setLastAccessedTime(long t) {
      super.setLastAccessedTime(t);
      if (this.wlcookie != null) {
         this.wlcookie.setValue(this.getCookieValue());
      }

   }

   public void setMaxInactiveInterval(int interval) {
      super.setMaxInactiveInterval(interval);
      this.wlcookie.setValue(this.getCookieValue());
   }

   public void invalidate(boolean ignore) throws IllegalStateException {
      if (!this.isValid) {
         throw new IllegalStateException("Session already invalidated");
      } else {
         this.expiring = true;
         if (!this.isRefByOthers()) {
            synchronized(this.getInternalLock()) {
               if (!this.isRefByOthers()) {
                  this.getContext().invalidateSession(this, false);
                  this.wlcookie.setValue("");
                  this.wlcookie.setMaxAge(0);
                  this.setValid(false);
               }
            }
         }
      }
   }

   private String getCookieValue() {
      String ret = "" + this.creationTime + "|" + this.accessTime + "|" + this.maxInactiveInterval;
      if (this.attributes != null) {
         Iterator var2 = this.attributes.keySet().iterator();

         while(var2.hasNext()) {
            String name = (String)var2.next();
            if (!"weblogic.authuser".equals(name) && !"weblogic.authuser.associated.data".equals(name)) {
               String value = this.getAttribute(name).toString();
               if (value == null) {
                  value = "";
               }

               ret = ret + "|" + name + "/" + value;
            }
         }
      }

      return ret;
   }

   private void initSessionPropsFromCookie(String cookieVal) {
      try {
         if (cookieVal != null) {
            StringTokenizer st = new StringTokenizer(cookieVal, "|");
            if (st.hasMoreTokens()) {
               String next = st.nextToken();
               this.creationTime = Long.parseLong(next);
               if (st.hasMoreTokens()) {
                  next = st.nextToken();
                  this.accessTime = Long.parseLong(next);
                  if (st.hasMoreTokens()) {
                     next = st.nextToken();
                     this.maxInactiveInterval = Integer.parseInt(next);
                     this.attributes = new ConcurrentHashMap();

                     while(st.hasMoreTokens()) {
                        String attr = st.nextToken();
                        int pos = attr.indexOf("/");
                        String name = attr.substring(0, pos);
                        String value = attr.substring(pos + 1);
                        this.attributes.put(name, new AttributeWrapper(value));
                     }

                  }
               }
            }
         }
      } catch (Exception var8) {
         HTTPSessionLogger.logMalformedWLCookie(cookieVal, var8);
      }
   }

   protected void logTransientAttributeError(String name) {
   }
}

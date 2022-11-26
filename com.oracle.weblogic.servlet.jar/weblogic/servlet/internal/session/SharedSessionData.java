package weblogic.servlet.internal.session;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import weblogic.servlet.internal.WebAppServletContext;

public final class SharedSessionData implements HttpSession, SessionInternal {
   private HttpSession session;
   private WebAppServletContext context;
   private String versionId;

   public SharedSessionData(HttpSession s, WebAppServletContext ctx) {
      this.session = s;
      this.context = ctx;
      this.versionId = this.context.getVersionId();
   }

   public HttpSession getSession() {
      return this.session;
   }

   public long getCreationTime() {
      return this.session.getCreationTime();
   }

   public String getId() {
      return this.session.getId();
   }

   public long getLastAccessedTime() {
      return this.session.getLastAccessedTime();
   }

   public ServletContext getServletContext() {
      return this.context;
   }

   public void setMaxInactiveInterval(int i) {
      this.session.setMaxInactiveInterval(i);
   }

   public int getMaxInactiveInterval() {
      return this.session.getMaxInactiveInterval();
   }

   public HttpSessionContext getSessionContext() {
      return this.session.getSessionContext();
   }

   public Object getAttribute(String s) {
      return this.session.getAttribute(s);
   }

   public Object getValue(String s) {
      return this.session.getValue(s);
   }

   public Enumeration getAttributeNames() {
      return this.session.getAttributeNames();
   }

   public String[] getValueNames() {
      return this.session.getValueNames();
   }

   public void setAttribute(String s, Object o) {
      this.session.setAttribute(s, o);
   }

   public void putValue(String s, Object o) {
      this.session.putValue(s, o);
   }

   public void removeAttribute(String s) {
      this.session.removeAttribute(s);
   }

   public void removeValue(String s) {
      this.session.removeValue(s);
   }

   public boolean isNew() {
      return this.session.isNew();
   }

   public void invalidate() {
      this.session.invalidate();
   }

   public String getInternalId() {
      return ((SessionInternal)this.session).getInternalId();
   }

   public String getIdWithServerInfo() {
      return ((SessionInternal)this.session).getIdWithServerInfo();
   }

   public SessionContext getContext() {
      return ((SessionInternal)this.session).getContext();
   }

   public boolean isValid() {
      return ((SessionInternal)this.session).isValid();
   }

   public boolean isExpiring() {
      return ((SessionInternal)this.session).isExpiring();
   }

   public boolean isInvalidating() {
      return ((SessionInternal)this.session).isInvalidating();
   }

   public int getConcurrentRequestCount() {
      return ((SessionInternal)this.session).getConcurrentRequestCount();
   }

   public long getLAT() {
      return ((SessionInternal)this.session).getLAT();
   }

   public void setLastAccessedTime(long t) {
      ((SessionInternal)this.session).setLastAccessedTime(t);
   }

   public void setNew(boolean n) {
      ((SessionInternal)this.session).setNew(n);
   }

   public Object getInternalAttribute(String name) throws IllegalStateException {
      return ((SessionInternal)this.session).getInternalAttribute(name);
   }

   public void setInternalAttribute(String name, Object value) throws IllegalStateException, IllegalArgumentException {
      ((SessionInternal)this.session).setInternalAttribute(name, value);
   }

   public void removeInternalAttribute(String name) throws IllegalStateException {
      ((SessionInternal)this.session).removeInternalAttribute(name);
   }

   public Enumeration getInternalAttributeNames() {
      return ((SessionInternal)this.session).getInternalAttributeNames();
   }

   public boolean hasStateAttributes() {
      return ((SessionInternal)this.session).hasStateAttributes();
   }

   public String getVersionId() {
      return this.versionId;
   }

   public final void setVersionId(String versionId) {
      this.versionId = versionId;
   }

   public String changeSessionId(String newId) {
      return ((SessionInternal)this.session).changeSessionId(newId);
   }
}

package weblogic.servlet.internal.session;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.cluster.wan.PersistenceService;
import weblogic.servlet.cluster.wan.SessionDiff;

public class WANSessionData extends ReplicatedSessionData {
   private static final long serialVersionUID = -8758624332473471640L;
   private final SessionDiff diff;
   private static final String WAN_SESSION_VERSION = "wls_wan_session_version";
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public WANSessionData() {
      this.diff = new SessionDiff();
   }

   public WANSessionData(String sessionid, SessionContext httpCtx) {
      this(sessionid, httpCtx, true);
   }

   protected WANSessionData(String sessionid, SessionContext httpCtx, boolean isNew) {
      super(sessionid, httpCtx, isNew);
      this.diff = new SessionDiff();
   }

   public void removeInternalAttribute(String name) throws IllegalStateException {
      super.removeInternalAttribute(name);
      if (!isPartitionShuttingDown()) {
         this.diff.setAttribute(name, (Object)null, false, true);
      }

      super.setInternalAttribute("wls_wan_session_version", new Integer(this.diff.getVersionCount()));
   }

   public final void setInternalAttribute(String name, Object value) throws IllegalStateException {
      Object oldValue = super.getInternalAttribute(name);
      super.setInternalAttribute(name, value);
      this.diff.setAttribute(name, value, oldValue == null, true);
      super.setInternalAttribute("wls_wan_session_version", new Integer(this.diff.getVersionCount()));
   }

   public final void removeAttribute(String name) throws IllegalStateException {
      super.removeAttribute(name);
      if (!isPartitionShuttingDown()) {
         this.diff.setAttribute(name, (Object)null, false, false);
      }

      super.setInternalAttribute("wls_wan_session_version", new Integer(this.diff.getVersionCount()));
   }

   public final void setAttribute(String name, Object value) throws IllegalStateException {
      Object oldValue = super.getAttribute(name);
      super.setAttribute(name, value);
      this.diff.setAttribute(name, value, oldValue == null, false);
      super.setInternalAttribute("wls_wan_session_version", new Integer(this.diff.getVersionCount()));
   }

   private String getIdWithoutServerInfo() {
      return getID(this.getIdWithServerInfo());
   }

   public void invalidate() {
      super.invalidate();
      this.getPersistenceService().invalidate(this.getIdWithoutServerInfo(), this.getContextPath());
   }

   void remove() {
      super.remove();
      if (!isPartitionShuttingDown()) {
         this.getPersistenceService().invalidate(this.getIdWithoutServerInfo(), this.getContextPath());
      }

   }

   public SessionDiff getChange() {
      return this.diff;
   }

   public void setCreationTime(long creationTime) {
      this.creationTime = creationTime;
   }

   public String changeSessionId(String newId) {
      super.changeSessionId(newId);
      this.diff.setAttribute("weblogic.servlet.session.newId", this.id, false, true);
      this.diff.setAttribute("weblogic.servlet.session.oldId", this.oldId, false, true);
      return this.id;
   }

   void syncSession() {
      try {
         super.syncSession();
         this.getPersistenceService().update(this.getIdWithoutServerInfo(), this.getCreationTime(), this.getContextPath(), this.getMaxInactiveInterval(), this.getLAT(), this.diff);
      } finally {
         this.postInvalidate();
      }

   }

   SessionDiff getSessionDiff() {
      return this.diff;
   }

   public void setSessionCreationTime(long time) {
      this.creationTime = time;
   }

   public void applySessionDiff(SessionDiff diff) {
      HashMap attributes = diff.getAttributes();
      Iterator iterator = attributes.keySet().iterator();

      while(iterator.hasNext()) {
         String key = (String)iterator.next();
         this.setAttribute(key, attributes.get(key));
      }

      HashMap internalAttributes = diff.getInternalAttributes();
      iterator = internalAttributes.keySet().iterator();

      while(iterator.hasNext()) {
         String key = (String)iterator.next();
         this.setInternalAttribute(key, internalAttributes.get(key));
      }

      this.diff.setVersionCounter(diff.getVersionCount());
   }

   protected void applySessionChange(ReplicatedSessionChange chg) {
      Map internals = chg.getInternalAttributeChanges();
      Integer i = (Integer)internals.remove("wls_wan_session_version");
      if (i != null) {
         this.diff.setVersionCounter(i + 1);
      }

      super.applySessionChange(chg);
   }

   private PersistenceService getPersistenceService() {
      return (PersistenceService)GlobalServiceLocator.getServiceLocator().getService(PersistenceService.class, new Annotation[0]);
   }

   private static boolean isPartitionShuttingDown() {
      String partitionName = getPartitionName();
      if (partitionName.equals("DOMAIN")) {
         return false;
      } else {
         PartitionRuntimeMBean partitionRuntimeMBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupPartitionRuntime(partitionName);
         if (partitionRuntimeMBean != null) {
            PartitionRuntimeMBean.State internalState = partitionRuntimeMBean.getInternalState();
            if (State.FORCE_SHUTTING_DOWN.equals(internalState) || State.SHUTTING_DOWN.equals(internalState)) {
               return true;
            }
         }

         return false;
      }
   }

   private static String getPartitionName() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (cic != null) {
         String partitionName = cic.getPartitionName();
         if (partitionName != null) {
            return partitionName;
         }
      }

      return "DOMAIN";
   }
}

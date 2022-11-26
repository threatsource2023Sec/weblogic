package weblogic.ejb;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public abstract class GenericSessionBean extends GenericEnterpriseBean implements SessionBean {
   private static final long serialVersionUID = 8994951888307145398L;
   private SessionContext ctx;

   public GenericSessionBean() {
      if (this.isTracingEnabled()) {
         this.trace("constructor");
      }

   }

   public void setSessionContext(SessionContext c) {
      if (this.isTracingEnabled()) {
         this.trace("setSessionContext");
      }

      this.ctx = c;
   }

   protected SessionContext getSessionContext() {
      return this.ctx;
   }

   public void ejbCreate() throws CreateException {
      if (this.isTracingEnabled()) {
         this.trace("ejbCreate");
      }

   }

   public void ejbRemove() {
      if (this.isTracingEnabled()) {
         this.trace("ejbRemove");
      }

   }

   public void ejbActivate() {
      if (this.isTracingEnabled()) {
         this.trace("ejbActivate");
      }

   }

   public void ejbPassivate() {
      if (this.isTracingEnabled()) {
         this.trace("ejbPassivate");
      }

   }
}

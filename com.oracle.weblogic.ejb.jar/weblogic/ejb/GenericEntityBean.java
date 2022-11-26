package weblogic.ejb;

import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

public abstract class GenericEntityBean extends GenericEnterpriseBean implements EntityBean {
   private static final long serialVersionUID = -3360313665517979109L;
   private EntityContext ctx;

   public void setEntityContext(EntityContext c) {
      if (this.isTracingEnabled()) {
         this.trace("setEntityContext");
      }

      this.ctx = c;
   }

   public void unsetEntityContext() {
      if (this.isTracingEnabled()) {
         this.trace("unsetEntityContext");
      }

      this.ctx = null;
   }

   protected EntityContext getEntityContext() {
      return this.ctx;
   }

   public void ejbRemove() {
      if (this.isTracingEnabled()) {
         this.trace("ejbRemove", this.ctx.getPrimaryKey());
      }

   }

   public void ejbActivate() {
      if (this.isTracingEnabled()) {
         this.trace("ejbActivate", this.ctx.getPrimaryKey());
      }

   }

   public void ejbPassivate() {
      if (this.isTracingEnabled()) {
         this.trace("ejbPassivate", this.ctx.getPrimaryKey());
      }

   }

   public void ejbLoad() {
      if (this.isTracingEnabled()) {
         this.trace("ejbLoad", this.ctx.getPrimaryKey());
      }

   }

   public void ejbStore() {
      if (this.isTracingEnabled()) {
         this.trace("ejbStore", this.ctx.getPrimaryKey());
      }

   }
}

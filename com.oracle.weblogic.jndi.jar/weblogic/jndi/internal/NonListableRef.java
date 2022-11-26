package weblogic.jndi.internal;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.jndi.NonListable;
import weblogic.jndi.OpaqueReference;
import weblogic.jndi.SimpleContext;

public final class NonListableRef implements OpaqueReference, NonListable {
   private Object referent;

   public NonListableRef(Object obj) {
      this.referent = obj;
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      this.referent = WLNamingManager.getObjectInstance(this.referent, name, ctx, ctx == null ? null : ctx.getEnvironment());
      return this.referent instanceof SimpleContext.SimpleReference ? ((SimpleContext.SimpleReference)this.referent).get() : this.referent;
   }
}

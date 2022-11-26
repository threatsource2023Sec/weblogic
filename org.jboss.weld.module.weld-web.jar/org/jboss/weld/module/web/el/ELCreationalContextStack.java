package org.jboss.weld.module.web.el;

import java.util.Stack;
import javax.el.ELContext;

class ELCreationalContextStack extends Stack {
   private static final long serialVersionUID = -57142365866995726L;

   public static ELCreationalContextStack addToContext(ELContext context) {
      ELCreationalContextStack store = new ELCreationalContextStack();
      context.putContext(ELCreationalContextStack.class, store);
      return store;
   }

   public static ELCreationalContextStack getCreationalContextStore(ELContext context) {
      Object o = context.getContext(ELCreationalContextStack.class);
      return o != null ? (ELCreationalContextStack)o : addToContext(context);
   }
}

package weblogic.j2eeclient;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.spi.NamingManager;
import weblogic.jndi.OpaqueReference;
import weblogic.jndi.SimpleContext;

/** @deprecated */
@Deprecated
public final class ClientSimpleContext extends SimpleContext {
   private Context remoteRootCtx;
   private Context remoteGlobalContext;
   private Context remoteAppContext;

   public ClientSimpleContext(Context remoteRootContext, Context remoteGlobalContext, Context remoteAppContext) {
      this.remoteGlobalContext = remoteGlobalContext;
      this.remoteAppContext = remoteAppContext;
      this.remoteRootCtx = remoteRootContext;
   }

   /** @deprecated */
   @Deprecated
   public ClientSimpleContext() {
   }

   public Object lookup(Name name) throws NamingException {
      Object found = this.internalLookup(name);
      return found instanceof OpaqueReference ? ((OpaqueReference)found).getReferent(name, this) : found;
   }

   private Object internalLookup(Name name) throws NamingException {
      try {
         switch (name.size()) {
            case 0:
               return this;
            case 1:
               Object key = name.get(0);
               if (!this.map.containsKey(key)) {
                  throw new NameNotFoundException("remaining name: " + name);
               }

               Object result = this.map.get(key);
               return this.processResultantRefs(name, result);
            default:
               return this.resolve(name).lookup(name.getSuffix(1));
         }
      } catch (NameNotFoundException var4) {
         if (name.get(0).equals("java:app")) {
            if (this.remoteAppContext == null) {
               throw var4;
            } else {
               return this.processResultantRefs(name, this.checkForNameUnderRemoteNode(this.remoteAppContext, name));
            }
         } else if (name.get(0).equals("java:global")) {
            return this.processResultantRefs(name, this.checkForNameUnderRemoteNode(this.remoteGlobalContext, name));
         } else if (this.remoteRootCtx != null) {
            return this.remoteRootCtx.lookup(name);
         } else {
            throw new NameNotFoundException("remaining name: " + name);
         }
      }
   }

   private Object processResultantRefs(Name name, Object result) throws NamingException {
      if (result instanceof SimpleContext.SimpleReference) {
         result = ((SimpleContext.SimpleReference)result).get();
      } else if (result instanceof LinkRef) {
         result = (new InitialContext(this.getEnvironment())).lookup(((LinkRef)result).getLinkName());
      } else if (result instanceof Reference) {
         try {
            result = NamingManager.getObjectInstance(result, name, this, this.getEnvironment());
         } catch (Exception var4) {
         }
      }

      return result;
   }

   private Object checkForNameUnderRemoteNode(Context c, Name name) throws NamingException {
      if (c == null) {
         throw new AssertionError("Context may not be null");
      } else {
         return name.size() > 1 ? c.lookup(name.getSuffix(1)) : c;
      }
   }
}

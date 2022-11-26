package weblogic.jndi.internal;

import java.security.AccessController;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.naming.CompositeName;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.LinkException;
import javax.naming.LinkRef;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.Referenceable;
import javax.naming.spi.NamingManager;
import javax.naming.spi.ObjectFactory;
import javax.naming.spi.StateFactory;
import weblogic.jndi.ClassTypeOpaqueReference;
import weblogic.jndi.OpaqueReference;
import weblogic.jndi.ThreadLocalMap;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;

public final class WLNamingManager {
   private static volatile List transportableFactories = null;
   private static volatile List stateFactories = null;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static void initialize() throws ServiceFailureException {
      Class var0 = WLNamingManager.class;
      synchronized(WLNamingManager.class) {
         if (transportableFactories == null) {
            transportableFactories = new CopyOnWriteArrayList();
         }
      }

      try {
         JNDIEnvironment.getJNDIEnvironment().loadTransportableFactories(transportableFactories);
      } catch (ConfigurationException var2) {
         throw new ServiceFailureException(var2);
      }
   }

   public static void addStateFactory(StateFactory factory) throws NamingException {
      if (stateFactories == null) {
         loadStateFactories();
      }

      stateFactories.add(0, factory);
   }

   public static void addTransportableFactory(ObjectFactory factory) {
      transportableFactories.add(0, factory);
   }

   public static Object getObjectInstance(Object boundObject, Name name, Context ctx, Hashtable env) throws NamingException {
      if (boundObject instanceof ClassTypeOpaqueReference) {
         Hashtable jndiEnv = ThreadLocalMap.get();
         if (jndiEnv != null && Boolean.parseBoolean((String)jndiEnv.get("weblogic.jndi.onlyGetClassType"))) {
            boundObject = ((ClassTypeOpaqueReference)boundObject).getObjectClass();
         } else {
            boundObject = ((OpaqueReference)boundObject).getReferent(name, ctx);
         }
      } else if (boundObject instanceof OpaqueReference) {
         boundObject = ((OpaqueReference)boundObject).getReferent(name, ctx);
      } else if (boundObject instanceof LinkRef) {
         String linkName = ((LinkRef)boundObject).getLinkName();
         InitialContext ic = null;

         try {
            ic = new InitialContext(env);
            boundObject = ic.lookup(linkName);
         } catch (NamingException var15) {
            LinkException le = new LinkException("");
            le.setLinkRemainingName(new CompositeName(linkName));
            le.setLinkResolvedName(name);
            le.setLinkResolvedObj(boundObject);
            le.setRootCause(var15);
            throw le;
         } finally {
            try {
               ic.close();
            } catch (Exception var14) {
            }

         }
      }

      return boundObject;
   }

   public static Object getTransportableInstance(Object boundObject, Name name, Context ctx, Hashtable env) throws NamingException {
      return getReplacement(transportableFactories, boundObject, name, ctx, env);
   }

   public static Object getStateToBind(Object objectToBind, Name name, Context ctx, Hashtable env) throws NamingException {
      if (objectToBind instanceof WLContextImpl) {
         return ((WLContextImpl)objectToBind).getNode();
      } else if (objectToBind instanceof Referenceable) {
         return ((Referenceable)objectToBind).getReference();
      } else {
         Object replacement = NamingManager.getStateToBind(objectToBind, name, ctx, env);
         if (replacement == objectToBind) {
            if (stateFactories == null) {
               loadStateFactories();
            }

            replacement = getReplacement(stateFactories, objectToBind, name, ctx, env);
         }

         return replacement;
      }
   }

   private static Object getReplacement(List replacerList, Object object, Name name, Context ctx, Hashtable env) throws NamingException {
      Object replacement = object;
      if (replacerList == null) {
         return object;
      } else {
         Iterator it = replacerList.iterator();

         while(it.hasNext()) {
            Object f = it.next();
            Object newObject = null;

            try {
               if (f instanceof ObjectFactory) {
                  newObject = ((ObjectFactory)f).getObjectInstance(object, name, ctx, env);
               } else {
                  newObject = ((StateFactory)f).getStateToBind(object, name, ctx, env);
               }

               if (newObject != null) {
                  replacement = newObject;
                  break;
               }
            } catch (NamingException var11) {
               throw var11;
            } catch (RuntimeException var12) {
               throw var12;
            } catch (Exception var13) {
               NamingException ce = new ConfigurationException(f.getClass().getName() + " threw an exception");
               ce.setRootCause(var13);
               throw ce;
            }
         }

         if (NamingFactoriesDebugLogger.isDebugEnabled() && replacement != object) {
            NamingFactoriesDebugLogger.debug("Replacing " + object.getClass().getName() + " with " + replacement.getClass().getName());
         }

         return replacement;
      }
   }

   private static void loadStateFactories() {
      Class var0 = WLNamingManager.class;
      synchronized(WLNamingManager.class) {
         if (stateFactories == null) {
            stateFactories = new CopyOnWriteArrayList();
         }

      }
   }
}

package weblogic.jndi.internal;

import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.naming.event.EventContext;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.event.ObjectChangeListener;
import weblogic.jndi.Environment;
import weblogic.jndi.OpaqueReference;
import weblogic.jndi.ThreadLocalMap;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.CBVWrapper;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.utils.classloaders.ClassLoaderUtils;

public class ApplicationNamingNode extends BasicNamingNode {
   private static final char DEFAULT_SEPARATOR = '/';
   private static final char[] DEFAULT_SEPARATOR_ARRAY = new char[]{'/'};
   private final char[] separators;
   private final ConcurrentHashMap cache;
   private String relativeName;
   ApplicationNamingNode parent;
   private final ApplicationNamingInfo info;

   public ApplicationNamingNode() {
      this((char[])DEFAULT_SEPARATOR_ARRAY, (ApplicationNamingNode)null, "", (ApplicationNamingInfo)null);
   }

   public ApplicationNamingNode(ApplicationNamingInfo info) {
      this((char[])DEFAULT_SEPARATOR_ARRAY, (ApplicationNamingNode)null, "", info);
   }

   public ApplicationNamingNode(String separators, ApplicationNamingNode parent, String relativeName, ApplicationNamingInfo info) {
      this(separators.toCharArray(), parent, relativeName, info);
   }

   private ApplicationNamingNode(char[] separators, ApplicationNamingNode parent, String relativeName, ApplicationNamingInfo info) {
      super(separators, parent, relativeName);
      this.cache = new ConcurrentHashMap(1);
      this.relativeName = "";
      this.separators = separators;
      this.relativeName = relativeName;
      this.parent = parent;
      if (info == null) {
         this.info = new ApplicationNamingInfo();
      } else {
         this.info = info;
      }

   }

   protected BasicNamingNode newSubnode(String relativeName) {
      return new ApplicationNamingNode(this.separators, this, relativeName, this.info);
   }

   public Context getContext(Hashtable env) {
      return new WLEventContextImpl(env, this, true);
   }

   protected NamingNode createSubnodeHere(String name, Hashtable env) throws NoPermissionException, NamingException {
      return super.createSubnodeHere(name, env);
   }

   protected void destroySubnodeHere(String name, Hashtable env) throws NoPermissionException, NamingException {
      String fullName = this.getNameInNamespace(name);
      super.destroySubnodeHere(name, env);
      if (NamingDebugLogger.isDebugEnabled()) {
         NamingDebugLogger.debug("+++ destroySubContext(" + fullName + ")");
      }

   }

   public Object lookup(String name, Hashtable env) throws NamingException, RemoteException {
      try {
         Object o = super.lookup(name, env);
         o = this.getObjectOrStub(o, name);
         return o;
      } catch (NameNotFoundException var5) {
         String nodeName = this.getRelativeName();
         if (nodeName.endsWith("comp/env")) {
            nodeName = "java:comp/env";
         }

         throw this.newNameNotFoundException("While trying to look up " + name + " in " + nodeName + ".", name, env);
      }
   }

   protected Object resolveObject(String name, Object obj, int mode, Hashtable env) throws NamingException {
      Object resolved;
      if (obj != null) {
         try {
            if (obj instanceof NamingNode) {
               NamingNode node = (NamingNode)obj;
               return node.getContext(env);
            }

            if (mode >= 0 && obj instanceof LinkRef) {
               LinkRef linkRef = (LinkRef)obj;
               String linkName = linkRef.getLinkName();
               Object cached = null;
               cached = this.cache.get(name);
               if (cached == null) {
                  EventContext etx;
                  String properties;
                  if (linkName.indexOf("java:") > -1) {
                     int index = linkName.lastIndexOf(47);
                     String objectName = linkName.substring(index + 1);
                     properties = linkName.substring(0, index);
                     Context ctx = new InitialContext();
                     etx = (EventContext)ctx.lookup(properties);
                     resolved = etx.lookup(objectName);
                     if (!this.isGetClasstype() && !this.isOpaqueReference(etx, objectName)) {
                        this.cache.put(name, resolved);
                        new CacheInvalidationListener(objectName, name, etx, this.cache);
                     }
                  } else if (!this.linkRefResolvesToRemoteServer(linkName)) {
                     resolved = super.resolveObject(name, obj, mode, env);
                     String[] parsedNames = this.parseName(linkName);
                     properties = null;
                     Hashtable properties;
                     if (env == null) {
                        properties = new Hashtable();
                     } else {
                        properties = (Hashtable)env.clone();
                     }

                     properties.put("weblogic.jndi.events.enable", "true");
                     Environment environ = new Environment(properties);
                     etx = (EventContext)environ.getContext(parsedNames[0]);
                     if (!this.isGetClasstype() && !this.isOpaqueReference(etx, parsedNames[1])) {
                        this.cache.put(name, resolved);
                        new CacheInvalidationListener(parsedNames[1], name, etx, this.cache);
                     }
                  } else {
                     resolved = super.resolveObject(name, obj, mode, env);
                  }
               } else {
                  resolved = cached;
                  if (NamingDebugLogger.isDebugEnabled()) {
                     NamingDebugLogger.debug(name + " found object in cache" + cached + " CACHE " + this.getNameInNamespace());
                  }
               }
            } else {
               resolved = super.resolveObject(name, obj, mode, env);
            }
         } catch (NamingException var14) {
            this.cache.remove(name);
            throw var14;
         } catch (Exception var15) {
            this.cache.remove(name);
            NamingException ne = this.fillInException(new ConfigurationException("Call to NamingManager.getObjectInstance() failed: "), name, obj, (String)null);
            ne.setRootCause(var15);
            throw ne;
         }
      } else {
         resolved = super.resolveObject(name, obj, mode, env);
      }

      return resolved;
   }

   private boolean isOpaqueReference(Context ctx, String objectName) {
      try {
         Object obj = ctx.lookupLink(objectName);

         do {
            if (obj instanceof LinkRef) {
               objectName = ((LinkRef)obj).getLinkName();
               Context ic = new InitialContext();
               obj = ic.lookupLink(objectName);
            }
         } while(obj instanceof LinkRef);

         if (obj instanceof OpaqueReference) {
            return true;
         } else {
            return false;
         }
      } catch (NamingException var5) {
         return false;
      }
   }

   private boolean isGetClasstype() {
      Hashtable jndiEnv = ThreadLocalMap.get();
      return jndiEnv != null && Boolean.parseBoolean((String)jndiEnv.get("weblogic.jndi.onlyGetClassType"));
   }

   private Object getObjectOrStub(Object obj, String name) throws RemoteException {
      if (!(obj instanceof ForceCallByReference) && !this.info.isForceCallByReferenceEnabled()) {
         if (ClassLoaderUtils.visibleToClassLoader(obj)) {
            return obj;
         } else {
            Object obj1 = obj;
            if (obj instanceof StubInfoIntf) {
               obj1 = StubFactory.getStub(((StubInfoIntf)obj).getStubInfo());
            } else if (obj instanceof Remote) {
               if (ServerHelper.isIIOPStub((Remote)obj)) {
                  return obj;
               }

               if (obj instanceof Proxy || ServerHelper.isWLSStub((Remote)obj)) {
                  return obj;
               }

               if (ServerHelper.isClusterable((Remote)obj) && RemoteHelper.isCollocated(obj)) {
                  ServerHelper.exportObject((Remote)obj, name);
               } else {
                  ServerHelper.exportObject((Remote)obj);
               }

               obj1 = StubFactory.getStub((Remote)obj);
            } else if (obj instanceof StubReference) {
               obj1 = StubFactory.getStub((StubReference)obj);
            }

            return obj1;
         }
      } else if (obj instanceof CBVWrapper) {
         CBVWrapper wrapper = (CBVWrapper)obj;
         return wrapper.getDelegate();
      } else {
         return obj;
      }
   }

   private String[] parseName(String name) {
      for(int i = name.length() - 1; i >= 0; --i) {
         char c = name.charAt(i);
         if (c == '.' || c == '/') {
            return new String[]{name.substring(0, i), name.substring(i + 1)};
         }
      }

      return new String[]{"", name};
   }

   private boolean linkRefResolvesToRemoteServer(String linkName) {
      return linkName.indexOf(58) > -1;
   }

   public String getRelativeName() throws NamingException {
      String result = this.relativeName;
      if (this.parent != null) {
         result = this.parent.getRelativeName() + "/" + this.relativeName;
      }

      return result;
   }

   private static final class CacheInvalidationListener implements ObjectChangeListener {
      private final String name;
      private final EventContext eventContext;
      private final ConcurrentHashMap cache;
      private final String objectName;
      private final String fullName;

      private CacheInvalidationListener(String objectName, String name, EventContext eventContext, ConcurrentHashMap cache) {
         this.name = name;
         this.eventContext = eventContext;
         this.cache = cache;
         this.objectName = objectName;
         String ns = null;

         try {
            this.eventContext.addNamingListener(objectName, 0, this);
            if (NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("REGISTERED UNDER NAME " + objectName + " LINK " + name + " CTX ");
            }

            ns = eventContext.getNameInNamespace();
         } catch (NamingException var7) {
            cache.remove(name);
            if (NamingDebugLogger.isDebugEnabled()) {
               throw (Error)(new AssertionError("Failed to register event for name " + name)).initCause(var7);
            }
         }

         this.fullName = ns + "/" + objectName;
      }

      public void objectChanged(NamingEvent event) {
         try {
            if (NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("REMOVING " + this.cache.get(this.name) + " from cache");
            }

            this.cache.remove(this.name);
            this.eventContext.removeNamingListener(this);
         } catch (NamingException var3) {
         }

      }

      public void namingExceptionThrown(NamingExceptionEvent event) {
      }

      public boolean equals(Object object) {
         if (object == this) {
            return true;
         } else if (object instanceof CacheInvalidationListener) {
            CacheInvalidationListener other = (CacheInvalidationListener)object;
            return this.fullName.equals(other.fullName);
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.fullName.hashCode();
      }

      // $FF: synthetic method
      CacheInvalidationListener(String x0, String x1, EventContext x2, ConcurrentHashMap x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}

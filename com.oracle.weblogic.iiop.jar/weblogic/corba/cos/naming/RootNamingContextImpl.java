package weblogic.corba.cos.naming;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ior.IOR;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.ServerReference;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;

public class RootNamingContextImpl extends NamingContextImpl {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static RootNamingContextImpl initialReference = new RootNamingContextImpl();
   private List bindingInterceptors = new ArrayList();
   private Map rootContexts = new HashMap();
   private Context context;
   private ObjectKey objectKey;
   private IOR ior;

   public static RootNamingContextImpl getInitialReference() {
      return initialReference;
   }

   protected RootNamingContextImpl() {
   }

   public static boolean isInitialReferenceObjectKey(ObjectKey objectKey) throws IOException {
      return getRootContextServerReference() != null && objectKey.equals(initialReference.getObjectKey());
   }

   public ObjectKey getObjectKey() throws IOException {
      if (this.objectKey == null) {
         this.objectKey = ObjectKey.getObjectKey(this.getIOR());
      }

      return this.objectKey;
   }

   public IOR getIOR() throws IOException {
      if (this.ior == null) {
         this.ior = IIOPReplacer.getIIOPReplacer().replaceRemote(getRootContextServerReference().getStubReference());
      }

      return this.ior;
   }

   private static ServerReference getRootContextServerReference() {
      return OIDManager.getInstance().getServerReference(initialReference);
   }

   public synchronized NamingContextImpl getRootContextForCurrentPartition() {
      return this.rootContexts.containsKey(RmiInvocationFacade.getCurrentPartitionName(KERNEL_ID)) ? (NamingContextImpl)this.rootContexts.get(RmiInvocationFacade.getCurrentPartitionName(KERNEL_ID)) : this.createRootContextForPartition();
   }

   private NamingContextImpl createRootContextForPartition() {
      try {
         InitialContext jndiContext = this.getUnderlyingContextForCurrentPartition();
         Iterator var2 = this.bindingInterceptors.iterator();

         while(var2.hasNext()) {
            RootContextBindingInterceptor bindingInterceptor = (RootContextBindingInterceptor)var2.next();
            this.bindPartitionSpecificObject(jndiContext, bindingInterceptor);
         }

         NamingContextImpl newContext = new NamingContextImpl(jndiContext);
         this.rootContexts.put(RmiInvocationFacade.getCurrentPartitionName(KERNEL_ID), newContext);
         return newContext;
      } catch (NamingException var4) {
         throw new RuntimeException("Unable to create root context", var4);
      }
   }

   private void bindPartitionSpecificObject(Context jndiContext, RootContextBindingInterceptor bindingInterceptor) throws NamingException {
      Context context = jndiContext;
      StringTokenizer st = new StringTokenizer(bindingInterceptor.getBindingName(), "/");

      for(int i = st.countTokens() - 1; i > 0; --i) {
         context = this.createSubcontextIfNeeded(context, st.nextToken());
      }

      context.addToEnvironment("weblogic.jndi.replicateBindings", "false");
      this.rebindObject(context, st.nextToken(), bindingInterceptor.getObjectToBind());
   }

   private void rebindObject(Context context, String name, Object objectToBind) throws NamingException {
      try {
         SecurityManager.runAs(KERNEL_ID, KERNEL_ID, new RebindAction(context, name, objectToBind));
      } catch (PrivilegedActionException var5) {
         this.rethrowNamingException(var5);
      }

   }

   private Context createSubcontextIfNeeded(Context context, String subcontextName) throws NamingException {
      try {
         return (Context)SecurityManager.runAs(KERNEL_ID, KERNEL_ID, new SubcontextCreationAction(context, subcontextName));
      } catch (PrivilegedActionException var4) {
         this.rethrowNamingException(var4);
         return null;
      }
   }

   private static Context doCreateSubcontextIfNeeded(Context context, String subcontextName) throws NamingException {
      ensureSubcontextExists(context, subcontextName);
      context = (Context)context.lookup(subcontextName);
      return context;
   }

   private static void ensureSubcontextExists(Context context, String name) throws NamingException {
      try {
         Object subcontext = context.lookup(name);
         if (!(subcontext instanceof Context)) {
            throw new NamingException("No subcontext " + context.getNameInNamespace() + "/" + name + " present");
         }
      } catch (NameNotFoundException var3) {
         createSubcontext(context, name);
      }

   }

   private static void createSubcontext(Context context, String name) throws NamingException {
      context.createSubcontext(name);
   }

   private void rethrowNamingException(PrivilegedActionException e) throws NamingException {
      if (e.getCause() instanceof NamingException) {
         throw (NamingException)e.getCause();
      } else if (e.getCause() instanceof RuntimeException) {
         throw (RuntimeException)e.getCause();
      } else if (e.getCause() instanceof Error) {
         throw (Error)e.getCause();
      } else {
         throw new RuntimeException(e.getCause());
      }
   }

   private InitialContext getUnderlyingContextForCurrentPartition() throws NamingException {
      Hashtable env = new Hashtable();
      env.put("weblogic.jndi.createUnderSharable", "true");
      return new InitialContext(env);
   }

   public void addBindInterceptor(RootContextBindingInterceptor interceptor) {
      this.bindingInterceptors.add(interceptor);
   }

   static class SubcontextCreationAction implements PrivilegedExceptionAction {
      private Context parentContext;
      private String subcontextName;

      public SubcontextCreationAction(Context parentContext, String subcontextName) {
         this.parentContext = parentContext;
         this.subcontextName = subcontextName;
      }

      public Context run() throws Exception {
         return RootNamingContextImpl.doCreateSubcontextIfNeeded(this.parentContext, this.subcontextName);
      }
   }

   static class RebindAction implements PrivilegedExceptionAction {
      private Context context;
      private String name;
      private Object object;

      public RebindAction(Context context, String name, Object object) {
         this.context = context;
         this.name = name;
         this.object = object;
      }

      public Object run() throws Exception {
         this.context.rebind(this.name, this.object);
         return this.object;
      }
   }
}

package weblogic.corba.cos.naming;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import org.omg.CORBA.Any;
import org.omg.CORBA.NO_PERMISSION;
import org.omg.CORBA.Object;
import org.omg.CORBA.UNKNOWN;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;
import weblogic.corba.cos.naming.NamingContextAnyPackage.AppException;
import weblogic.corba.cos.naming.NamingContextAnyPackage.TypeNotSupported;
import weblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;
import weblogic.corba.cos.transactions.TransactionFactoryImpl;
import weblogic.corba.idl.AnyImpl;
import weblogic.corba.idl.CorbaServerRef;
import weblogic.corba.idl.IDLHelper;
import weblogic.corba.idl.ObjectImpl;
import weblogic.corba.j2ee.naming.Utils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.InvocationHandlerFactory;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.server.ior.ServerIORFactory;
import weblogic.rmi.cluster.ClusterableServerRef;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.internal.InitialReferenceConstants;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.internal.SystemObject;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public class NamingContextImpl extends _NamingContextAnyImplBase implements InitialReferenceConstants, SystemObject {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String TYPE_ID = NamingContextAnyHelper.id();
   private static final boolean DEBUG = false;
   private Context ctx;
   private static final DebugCategory debugNaming = Debug.getCategory("weblogic.iiop.naming");
   private static final DebugLogger debugIIOPNaming = DebugLogger.getDebugLogger("DebugIIOPNaming");
   private IOR ior;
   private ServerReference serverReference;

   protected static void p(String s) {
      System.err.println("<NamingContextImpl> " + s);
   }

   public static IOR getBootstrapIOR() {
      return ServerIORFactory.createWellKnownIor(TYPE_ID, 8);
   }

   protected NamingContextImpl() {
   }

   protected NamingContextImpl(Context ctx) {
      this.ctx = ctx;
   }

   public IOR getIOR() throws IOException {
      if (this.ior == null) {
         this.ior = this.getIor(this.getServerReference());
      }

      return this.ior;
   }

   public synchronized ServerReference getServerReference() throws RemoteException {
      if (this.serverReference == null) {
         this.serverReference = IDLHelper.exportObject(this);
      }

      return this.serverReference;
   }

   private IOR getIor(ServerReference serverReference) {
      if (serverReference instanceof CorbaServerRef) {
         return this.getIor((CorbaServerRef)serverReference);
      } else if (serverReference instanceof ClusterableServerRef) {
         return this.getIor(this.getServerRef((ClusterableServerRef)serverReference));
      } else {
         throw new RuntimeException("Cannot obtain an IOR from " + serverReference);
      }
   }

   private ServerReference getServerRef(ClusterableServerRef serverReference) {
      serverReference.initialize(RmiInvocationFacade.getCurrentPartitionName(KERNEL_ID) + '/' + "NameService");
      return serverReference.getServerRef();
   }

   private IOR getIor(CorbaServerRef ref) {
      return ref.getIOR();
   }

   protected Context getContext() throws NamingException {
      if (this.ctx == null) {
         this.ctx = new InitialContext();
      }

      return this.ctx;
   }

   public void bind(NameComponent[] components, Object obj) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
      String name = Utils.nameComponentToString(components);

      try {
         this.getContext().bind(name, IIOPReplacer.getReplacer().resolveObject(obj));
      } catch (NamingException | IOException var5) {
         this.throwNamingBindException(var5, components);
      }

   }

   public void bind_context(NameComponent[] components, NamingContext context) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
      this.bind(components, context);
   }

   public void rebind(NameComponent[] components, Object obj) throws NotFound, CannotProceed, InvalidName {
      String name = Utils.nameComponentToString(components);

      try {
         this.getContext().rebind(name, IIOPReplacer.getReplacer().resolveObject(obj));
      } catch (NamingException | IOException var5) {
         this.throwNamingException(var5, (NameComponent[])components);
      }

   }

   public void rebind_context(NameComponent[] components, NamingContext context) throws NotFound, CannotProceed, InvalidName {
      this.rebind(components, context);
   }

   public Object resolve(NameComponent[] components) throws NotFound, CannotProceed, InvalidName {
      String name = Utils.nameComponentToString(components);
      java.lang.Object o = null;

      try {
         o = this.resolveObject(name);
      } catch (NamingException var7) {
         this.throwNamingException(var7, (NameComponent[])components);
      }

      if (!(o instanceof Object)) {
         try {
            return InvocationHandlerFactory.makeInvocationHandler((IOR)IIOPReplacer.getReplacer().replaceObject(o));
         } catch (IOException var5) {
            throw (CannotProceed)(new CannotProceed(this, components)).initCause(var5);
         } catch (ClassCastException var6) {
            throw (NotFound)(new NotFound(NotFoundReason.not_object, components)).initCause(var6);
         }
      } else {
         return (Object)o;
      }
   }

   public void unbind(NameComponent[] components) throws NotFound, CannotProceed, InvalidName {
      String name = Utils.nameComponentToString(components);
      if (!name.equals("")) {
         try {
            this.getContext().unbind(name);

            try {
               this.getContext().lookup(name);
               p("error unbound name still exists");
            } catch (NameNotFoundException var4) {
            }
         } catch (NamingException var5) {
            this.throwNamingException(var5, (NameComponent[])components);
         }

      }
   }

   public void list(int how_many, BindingListHolder bl, BindingIteratorHolder bi) {
      try {
         NamingEnumeration nenum = (new InitialContext()).listBindings(this.getContext().getNameInNamespace());
         BindingIteratorImpl.getBindings(nenum, how_many, bl);
         if (nenum.hasMore()) {
            bi.value = new BindingIteratorImpl(nenum);
         } else {
            bi.value = null;
         }
      } catch (NamingException var5) {
         this.throwUncheckedNamingException(var5);
      }

   }

   public NamingContext new_context() {
      return null;
   }

   public NamingContext bind_new_context(NameComponent[] components) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
      try {
         Context c = this.getContext().createSubcontext(Utils.nameComponentToString(components));
         return new NamingContextImpl(c);
      } catch (NamingException var3) {
         this.throwNamingBindException(var3, components);
         return null;
      }
   }

   public void destroy() throws NotEmpty {
      try {
         String name = this.getContext().getNameInNamespace();
         String context;
         int idx;
         if ((idx = name.lastIndexOf(47)) >= 0) {
            context = name.substring(0, idx);
            name = name.substring(idx + 1);
         } else {
            if ((idx = name.lastIndexOf(46)) < 0) {
               throw new NotEmpty();
            }

            context = name.substring(0, idx);
            name = name.substring(idx + 1);
         }

         ((Context)(new InitialContext()).lookup(context)).destroySubcontext(name);
      } catch (ContextNotEmptyException var4) {
         throw (NotEmpty)(new NotEmpty()).initCause(var4);
      } catch (NamingException var5) {
         this.throwUncheckedNamingException(var5);
      }

   }

   public void bind_any(WNameComponent[] components, Any any) throws weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound, weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed, InvalidName, AlreadyBound, TypeNotSupported, AppException {
      String name = Utils.nameComponentToString(components);

      try {
         if (debugNaming.isEnabled() || debugIIOPNaming.isDebugEnabled()) {
            IIOPLogger.logDebugNaming("bind_any(" + name + ")");
         }

         this.getContext().bind(name, resolveAny(any));
      } catch (NameAlreadyBoundException var5) {
         throw (AlreadyBound)(new AlreadyBound()).initCause(var5);
      } catch (NamingException var6) {
         this.throwNamingException(var6, (WNameComponent[])components);
      } catch (IOException var7) {
         this.throwNamingException(var7, (WNameComponent[])components);
      }

   }

   public void rebind_any(WNameComponent[] components, Any any) throws weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound, weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed, InvalidName, TypeNotSupported, AppException {
      String name = Utils.nameComponentToString(components);

      try {
         if (debugNaming.isEnabled() || debugIIOPNaming.isDebugEnabled()) {
            IIOPLogger.logDebugNaming("rebind_any(" + name + ")");
         }

         this.getContext().rebind(name, resolveAny(any));
      } catch (NamingException var5) {
         this.throwNamingException(var5, (WNameComponent[])components);
      } catch (IOException var6) {
         this.throwNamingException(var6, (WNameComponent[])components);
      }

   }

   public Any resolve_any(WNameComponent[] components) throws weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound, weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed, InvalidName, AppException {
      Any any = new AnyImpl();
      String name = Utils.nameComponentToString(components);
      if (debugNaming.isEnabled() || debugIIOPNaming.isDebugEnabled()) {
         IIOPLogger.logDebugNaming("resolve_any(" + name + ")");
      }

      try {
         java.lang.Object o = this.resolveObject(name);
         if (o instanceof Object) {
            any.insert_Object((Object)o);
         } else if (o instanceof Serializable) {
            any.insert_Value((Serializable)o);
         }
      } catch (NamingException var6) {
         this.throwNamingException(var6, (WNameComponent[])components);
      }

      return any;
   }

   public Any resolve_str_any(String name) throws weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound, weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed, InvalidName, AppException {
      try {
         return this.resolve_any(Utils.stringToWNameComponent(name));
      } catch (NamingException var3) {
         throw (InvalidName)(new InvalidName()).initCause(var3);
      }
   }

   public String to_string(NameComponent[] name) throws InvalidName {
      return Utils.nameComponentToString(name);
   }

   public NameComponent[] to_name(String str) throws InvalidName {
      try {
         return Utils.stringToNameComponent(str);
      } catch (NamingException var3) {
         throw (InvalidName)(new InvalidName()).initCause(var3);
      }
   }

   public String to_url(String addr, String sn) throws InvalidName, InvalidAddress {
      return "corbaname:iiop:" + addr + "#" + sn;
   }

   public Object resolve_str(String name) throws NotFound, CannotProceed, InvalidName {
      NameComponent[] components = null;

      try {
         components = Utils.stringToNameComponent(name);
         return this.resolve(components);
      } catch (NamingException var4) {
         this.throwNamingException(var4, (NameComponent[])components);
         return null;
      }
   }

   public static java.lang.Object resolveAny(Any any) throws TypeNotSupported, IOException {
      java.lang.Object obj = null;
      switch (any.type().kind().value()) {
         case 14:
            obj = IIOPReplacer.getReplacer().resolveObject(any.extract_Object());
            break;
         case 29:
         case 30:
         case 32:
            obj = any.extract_Value();
            break;
         default:
            throw new TypeNotSupported();
      }

      return obj;
   }

   private java.lang.Object resolveObject(String name) throws NamingException {
      if (name.equals("")) {
         return this;
      } else {
         java.lang.Object o = this.getContext().lookup(name);
         Thread et = Thread.currentThread();
         ClassLoader clSave = et.getContextClassLoader();

         java.lang.Object var5;
         try {
            et.setContextClassLoader(o.getClass().getClassLoader());
            if (o instanceof Proxy) {
               var5 = o;
               return var5;
            }

            if (o instanceof Remote) {
               if (!(o instanceof Object)) {
                  o = new ObjectImpl((Remote)o);
               }
            } else if (o instanceof Context) {
               o = new NamingContextImpl((Context)o);
            } else if (o instanceof UserTransaction) {
               o = TransactionFactoryImpl.getTransactionFactory();
            } else if (o instanceof TransactionManager) {
               o = TransactionFactoryImpl.getTransactionFactory();
            }

            var5 = o;
         } finally {
            et.setContextClassLoader(clSave);
         }

         return var5;
      }
   }

   private void throwNamingBindException(Exception ne, NameComponent[] components) throws AlreadyBound, InvalidName, NotFound, CannotProceed {
      if (!(ne instanceof NameAlreadyBoundException)) {
         this.throwNamingException(ne, components);
      } else {
         if (debugNaming.isEnabled() || debugIIOPNaming.isDebugEnabled()) {
            IIOPLogger.logNamingException(ne);
         }

         throw (AlreadyBound)(new AlreadyBound()).initCause(ne);
      }
   }

   private void throwNamingException(Exception ne, NameComponent[] components) throws InvalidName, NotFound, CannotProceed {
      if (debugNaming.isEnabled() || debugIIOPNaming.isDebugEnabled()) {
         IIOPLogger.logNamingException(ne);
      }

      if (ne instanceof NameNotFoundException) {
         throw (NotFound)(new NotFound(NotFoundReason.missing_node, components)).initCause(ne);
      } else if (ne instanceof NoPermissionException) {
         throw (NO_PERMISSION)(new NO_PERMISSION(ne.getMessage())).initCause(ne);
      } else if (ne instanceof AuthenticationException) {
         throw (NO_PERMISSION)(new NO_PERMISSION(ne.getMessage())).initCause(ne);
      } else if (ne instanceof IOException) {
         throw (CannotProceed)(new CannotProceed(this, components)).initCause(ne);
      } else {
         throw (InvalidName)(new InvalidName()).initCause(ne);
      }
   }

   private void throwUncheckedNamingException(NamingException ne) {
      if (debugNaming.isEnabled() || debugIIOPNaming.isDebugEnabled()) {
         IIOPLogger.logNamingException(ne);
      }

      if (ne instanceof NoPermissionException) {
         throw (NO_PERMISSION)(new NO_PERMISSION(ne.getMessage())).initCause(ne);
      } else if (ne instanceof AuthenticationException) {
         throw (NO_PERMISSION)(new NO_PERMISSION(ne.getMessage())).initCause(ne);
      } else {
         throw (UNKNOWN)(new UNKNOWN(ne.getMessage())).initCause(ne);
      }
   }

   private void throwNamingException(Exception ne, WNameComponent[] components) throws weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound, weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed, InvalidName, AppException {
      if (debugNaming.isEnabled() || debugIIOPNaming.isDebugEnabled()) {
         IIOPLogger.logNamingException(ne);
      }

      if (ne instanceof NameNotFoundException) {
         throw (weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound)(new weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound(NotFoundReason.missing_node, components)).initCause(ne);
      } else if (ne instanceof NoPermissionException) {
         throw (NO_PERMISSION)(new NO_PERMISSION(ne.getMessage())).initCause(ne);
      } else if (ne instanceof AuthenticationException) {
         throw (NO_PERMISSION)(new NO_PERMISSION(ne.getMessage())).initCause(ne);
      } else if (ne instanceof NamingException) {
         throw new AppException(ne.getCause().getMessage());
      } else if (ne instanceof IOException) {
         throw (weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed)(new weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed(this, components)).initCause(ne);
      } else {
         throw (InvalidName)(new InvalidName()).initCause(ne);
      }
   }
}

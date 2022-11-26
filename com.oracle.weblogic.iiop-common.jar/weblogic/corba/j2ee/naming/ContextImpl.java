package weblogic.corba.j2ee.naming;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.NamingManager;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UserException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosTransactions.TransactionFactory;
import weblogic.corba.client.naming.ReferenceHelperImpl;
import weblogic.corba.client.spi.ServiceManager;
import weblogic.corba.cos.naming.NamingContextAny;
import weblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;
import weblogic.iiop.RequestUrl;
import weblogic.rmi.extensions.server.ReferenceHelper;
import weblogic.transaction.TransactionHelper;

public final class ContextImpl implements Context, NamingContextHolder {
   private ORBInfo orbinfo;
   private Hashtable env;
   private NamingContext ctx;
   private static final boolean DEBUG = false;
   private transient Thread loginThread;

   private static void p(String s) {
      System.err.println("<ContextImpl> " + s);
   }

   public static Context createCorbanameUrlHandlingContext(Hashtable env) throws NamingException {
      return new ContextImpl(env, (ORBInfo)null, (NamingContext)null);
   }

   public ContextImpl(Hashtable env, ORBInfo orbinfo, NamingContext ctx) throws NamingException {
      this.loginThread = null;
      this.env = env;
      this.orbinfo = orbinfo;
      this.ctx = ctx;
      if (JmxAuthenticationSupport.useJmxCredentials(env)) {
         JmxAuthenticationSupport.establishJmxCredentials(env);
      }

   }

   private ContextImpl(ContextImpl parentContext, NamingContext ctx) throws NamingException {
      this(parentContext.env, parentContext.orbinfo, ctx);
   }

   public Delegate getDelegate() {
      return ((ObjectImpl)this.getContext())._get_delegate();
   }

   public NamingContext getContext() {
      return this.ctx;
   }

   private NamingContext getContext(String name) throws NamingException {
      if (this.ctx != null) {
         if (this.orbinfo != null) {
            ORBHelper.getORBHelper().setCurrent(this.orbinfo);
         }

         return this.ctx;
      } else {
         return this.createNamingContext(name);
      }
   }

   private NamingContext createNamingContext(String name) throws NamingException {
      String url = NameParser.getBasicUrl(name);
      if (url == null) {
         throw new InvalidNameException("No usable protocol url specified in: " + name);
      } else {
         Object nc = ORBHelper.getORBHelper().getORBInitialReference(url, this.env, "NameService");
         this.orbinfo = ORBHelper.getORBHelper().getCurrent();
         this.ctx = Utils.narrowContext(nc);
         ServiceManager.getSecurityManager().pushSubject(this.env, this);
         ORBHelper.getORBHelper().pushTransactionHelper();
         return this.ctx;
      }
   }

   public java.lang.Object lookup(Name n) throws NamingException {
      return this.lookup(n.toString());
   }

   public java.lang.Object lookup(String name) throws NamingException {
      java.lang.Object ctx = this.getContext(name);

      try {
         if (ctx instanceof Context) {
            return ((Context)ctx).lookup(NameParser.getNameString(name));
         } else {
            return ctx instanceof NamingContextAny ? this.lookup((NamingContextAny)ctx, Utils.stringToWNameComponent(name)) : this.lookup((NamingContext)ctx, Utils.stringToNameComponent(name));
         }
      } catch (CommunicationException var5) {
         String providerURL = (String)this.env.get("java.naming.provider.url");
         if (providerURL != null) {
            this.createNamingContext(providerURL);
            return this.lookup(name);
         } else {
            throw var5;
         }
      }
   }

   java.lang.Object lookup(NamingContext ctx, NameComponent[] name) throws NamingException {
      try {
         java.lang.Object o = ctx.resolve(name);
         if (o instanceof NamingContext) {
            o = new ContextImpl(this, (NamingContext)o);
         }

         return o;
      } catch (UserException var4) {
         throw Utils.wrapNamingException(var4, "Exception in lookup.");
      } catch (Exception var5) {
         throw Utils.wrapNamingException(var5, "Unhandled exception in lookup");
      }
   }

   java.lang.Object lookup(NamingContextAny ctx, WNameComponent[] name) throws NamingException {
      java.lang.Object var5;
      try {
         java.lang.Object o = null;
         Any any = ctx.resolve_any(name);
         switch (any.type().kind().value()) {
            case 14:
               o = any.extract_Object();
               break;
            case 29:
            case 30:
            case 32:
               o = any.extract_Value();
         }

         if (o instanceof NamingContext) {
            o = new ContextImpl(this, (NamingContext)o);
         } else if (o instanceof TransactionFactory) {
            o = TransactionHelper.getTransactionHelper().getUserTransaction();
         }

         var5 = o;
      } catch (UserException var10) {
         throw Utils.wrapNamingException(var10, "Exception in lookup.");
      } catch (Exception var11) {
         throw Utils.wrapNamingException(var11, "Unhandled exception in lookup");
      } finally {
         RequestUrl.clear();
      }

      return var5;
   }

   public void bind(String name, java.lang.Object o) throws NamingException {
      this.bind(Utils.stringToName(name), o);
   }

   public void bind(Name name, java.lang.Object newObject) throws NamingException {
      try {
         NamingContext ctx = this.getContext(name.toString());
         if (!ReferenceHelper.exists()) {
            ReferenceHelper.setReferenceHelper(new ReferenceHelperImpl());
         }

         java.lang.Object o = ReferenceHelper.getReferenceHelper().replaceObject(newObject);
         o = NamingManager.getStateToBind(o, name, this, this.env);
         if (!(o instanceof Object)) {
            try {
               NamingContextAny ctxa = (NamingContextAny)ctx;
               ORB orb = ORB.init();
               Any any = orb.create_any();
               TypeCode tc = orb.get_primitive_tc(TCKind.tk_value);
               any.insert_Value((Serializable)o, tc);
               ctxa.bind_any(Utils.nameToWName(name), any);
            } catch (ClassCastException var9) {
               throw new IllegalArgumentException("Object must be a CORBA object: " + o);
            }
         } else {
            ctx.bind(Utils.nameToName(name), (Object)o);
         }

      } catch (UserException var10) {
         throw Utils.wrapNamingException(var10, "Exception in bind()");
      } catch (IOException var11) {
         throw new CommunicationException();
      } catch (Exception var12) {
         throw Utils.wrapNamingException(var12, "Unhandled exception in bind()");
      }
   }

   public void rebind(String name, java.lang.Object o) throws NamingException {
      this.rebind(Utils.stringToName(name), o);
   }

   public void rebind(Name name, java.lang.Object newObject) throws NamingException {
      try {
         NamingContext ctx = this.getContext(name.toString());
         if (!ReferenceHelper.exists()) {
            ReferenceHelper.setReferenceHelper(new ReferenceHelperImpl());
         }

         java.lang.Object o = ReferenceHelper.getReferenceHelper().replaceObject(newObject);
         o = NamingManager.getStateToBind(o, name, this, this.env);
         if (!(o instanceof Object)) {
            try {
               NamingContextAny ctxa = (NamingContextAny)ctx;
               ORB orb = ORB.init();
               Any any = orb.create_any();
               TypeCode tc = orb.get_primitive_tc(TCKind.tk_value);
               any.insert_Value((Serializable)o, tc);
               ctxa.rebind_any(Utils.nameToWName(name), any);
            } catch (ClassCastException var9) {
               throw new IllegalArgumentException("Object must be a CORBA object: " + o);
            }
         } else {
            ctx.rebind(Utils.nameToName(name), (Object)o);
         }

      } catch (UserException var10) {
         throw Utils.wrapNamingException(var10, "Exception in rebind()");
      } catch (IOException var11) {
         throw new CommunicationException();
      } catch (Exception var12) {
         throw Utils.wrapNamingException(var12, "Unhandled exception in rebind()");
      }
   }

   public void unbind(Name n) throws NamingException {
      this.unbind(n.toString());
   }

   public void unbind(String name) throws NamingException {
      try {
         NamingContext ctx = this.getContext(name);
         ctx.unbind(Utils.stringToNameComponent(name));
      } catch (UserException var3) {
         throw Utils.wrapNamingException(var3, "Exception in unbind()");
      } catch (Exception var4) {
         throw Utils.wrapNamingException(var4, "Unhandled exception in unbind()");
      }
   }

   public void rename(Name n1, Name n2) throws NamingException {
      this.rename(n1.toString(), n2.toString());
   }

   public void rename(String oldName, String newName) throws NamingException {
      java.lang.Object o = this.lookup(oldName);
      this.bind(newName, o);
      this.unbind(oldName);
   }

   public NamingEnumeration list(Name n) throws NamingException {
      return this.listBindings(n);
   }

   public NamingEnumeration list(String name) throws NamingException {
      return this.listBindings(name);
   }

   public NamingEnumeration listBindings(Name name) throws NamingException {
      return this.listBindings(name.toString());
   }

   public NamingEnumeration listBindings(String name) throws NamingException {
      try {
         NamingContext ctx = this.getContext(name);
         if (name.length() > 0) {
            NameComponent[] subname = Utils.stringToNameComponent(name);
            ctx = Utils.narrowContext(ctx.resolve(subname));
         }

         BindingIteratorHolder biter = new BindingIteratorHolder();
         ctx.list(0, new BindingListHolder(new Binding[0]), biter);
         return new NamingEnumerationImpl(biter.value, ctx, this);
      } catch (Exception var4) {
         throw Utils.wrapNamingException(var4, "Exception in listBindings");
      }
   }

   public void destroySubcontext(Name n) throws NamingException {
      this.destroySubcontext(n.toString());
   }

   public void destroySubcontext(String s) throws NamingException {
      try {
         int idx = s.lastIndexOf(47);
         String path = "";
         if (idx >= 0) {
            s.substring(idx + 1);
            path = s.substring(0, idx);
         }

         this.getContext(path);
         NameComponent[] p = Utils.stringToNameComponent(s);
         NamingContext subctx = Utils.narrowContext(this.getContext(s).resolve(p));
         subctx.destroy();
      } catch (Exception var7) {
         throw Utils.wrapNamingException(var7, "Exception in destroySubcontext()");
      }
   }

   public Context createSubcontext(Name n) throws NamingException {
      return this.createSubcontext(n.toString());
   }

   public Context createSubcontext(String s) throws NamingException {
      try {
         int idx = s.lastIndexOf(47);
         String path = "";
         if (idx >= 0) {
            s.substring(idx + 1);
            path = s.substring(0, idx);
         }

         NamingContext ctx = this.getContext(path);
         return new ContextImpl(this, Utils.narrowContext(ctx.bind_new_context(Utils.stringToNameComponent(s))));
      } catch (UserException var6) {
         throw Utils.wrapNamingException(var6, "CosNaming exception");
      } catch (Exception var7) {
         throw Utils.wrapNamingException(var7, "Unhandled error in createSubcontext");
      }
   }

   public java.lang.Object lookupLink(Name n) throws NamingException {
      throw new UnsupportedOperationException("naming operation using Name");
   }

   public java.lang.Object lookupLink(String s) throws NamingException {
      return this.lookup(s);
   }

   public String getNameInNamespace() throws NamingException {
      throw new UnsupportedOperationException("naming operation using Name");
   }

   public javax.naming.NameParser getNameParser(Name n) throws NamingException {
      throw new UnsupportedOperationException("naming operation using Name");
   }

   public javax.naming.NameParser getNameParser(String name) throws NamingException {
      return new NameParser();
   }

   public Name composeName(Name n1, Name n2) throws NamingException {
      throw new UnsupportedOperationException("naming operation using Name");
   }

   public String composeName(String s1, String s2) throws NamingException {
      throw new UnsupportedOperationException("naming operation using Name");
   }

   public java.lang.Object addToEnvironment(String s, java.lang.Object o) throws NamingException {
      java.lang.Object oldValue = this.env.get(s);
      this.env.put(s, o);
      return oldValue;
   }

   public java.lang.Object removeFromEnvironment(String s) throws NamingException {
      return this.env.remove(s);
   }

   public Hashtable getEnvironment() throws NamingException {
      return this.env;
   }

   public void close() {
      if (this.loginThread != null) {
         if (this.loginThread == Thread.currentThread()) {
            if (this.orbinfo != null) {
               this.orbinfo.removeClientSecurityContext();
            }

            ServiceManager.getSecurityManager().popSubject();
         }

         this.loginThread = null;
      }

      ORBHelper.getORBHelper().popTransactionHelper();
   }

   public java.lang.Object writeReplace() throws ObjectStreamException {
      return this.getContext() != null ? this.getContext() : this;
   }

   public void enableLogoutOnClose() {
      this.loginThread = Thread.currentThread();
   }

   static class UnsupportedOperationException extends NamingException {
      private static final long serialVersionUID = -4020884966249797871L;

      UnsupportedOperationException(String msg) {
         super(msg);
      }
   }
}

package weblogic.jndi.remote;

import java.util.Hashtable;
import javax.naming.CompositeName;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import weblogic.jndi.internal.WLNamingManager;

public class ContextWrapper implements RemoteContext {
   private static final Hashtable DEFAULT_ENV = new Hashtable();
   private Context delegate;
   private Hashtable env;

   protected Hashtable env() {
      return this.env;
   }

   public ContextWrapper(Context delegate) {
      this.env = DEFAULT_ENV;
      this.delegate = delegate;
   }

   public ContextWrapper(Context delegate, Hashtable env) {
      this(delegate);
      this.env = env;
   }

   public void bind(String name, Object obj) throws NamingException {
      this.delegate.bind(name, obj);
   }

   public void bind(Name name, Object obj) throws NamingException {
      this.delegate.bind(name, obj);
   }

   public void close() throws NamingException {
      this.delegate.close();
   }

   public String composeName(String name, String prefix) throws NamingException {
      return this.delegate.composeName(name, prefix);
   }

   public Name composeName(Name name, Name prefix) throws NamingException {
      return this.delegate.composeName(name, prefix);
   }

   public Context createSubcontext(String name) throws NamingException {
      return this.createSubcontext((Name)(new CompositeName(name)));
   }

   public Context createSubcontext(Name name) throws NamingException {
      return this.makeTransportable(this.delegate.createSubcontext(name));
   }

   public void destroySubcontext(String name) throws NamingException {
      this.delegate.destroySubcontext(name);
   }

   public void destroySubcontext(Name name) throws NamingException {
      this.delegate.destroySubcontext(name);
   }

   public String getNameInNamespace() throws NamingException {
      return this.delegate.getNameInNamespace();
   }

   public NameParser getNameParser(String name) throws NamingException {
      return this.delegate.getNameParser(name);
   }

   public NameParser getNameParser(Name name) throws NamingException {
      return this.delegate.getNameParser(name);
   }

   public NamingEnumeration list(String name) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.list(name), this.env);
   }

   public NamingEnumeration list(Name name) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.list(name), this.env);
   }

   public NamingEnumeration listBindings(String name) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.listBindings(name), this.env);
   }

   public NamingEnumeration listBindings(Name name) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.listBindings(name), this.env);
   }

   public Object lookup(String name) throws NamingException {
      return this.lookup((Name)(new CompositeName(name)));
   }

   public Object lookup(Name name) throws NamingException {
      return this.makeTransportable(this.delegate.lookup(name));
   }

   public Object lookupLink(String name) throws NamingException {
      return this.lookupLink((Name)(new CompositeName(name)));
   }

   public Object lookupLink(Name name) throws NamingException {
      return this.makeTransportable(this.delegate.lookupLink(name));
   }

   public void rebind(String name, Object obj) throws NamingException {
      this.delegate.rebind(name, obj);
   }

   public void rebind(Name name, Object obj) throws NamingException {
      this.delegate.rebind(name, obj);
   }

   public void rename(String oldName, String newName) throws NamingException {
      this.delegate.rename(oldName, newName);
   }

   public void rename(Name oldName, Name newName) throws NamingException {
      this.delegate.rename(oldName, newName);
   }

   public void unbind(String name) throws NamingException {
      this.delegate.unbind(name);
   }

   public void unbind(Name name) throws NamingException {
      this.delegate.unbind(name);
   }

   public Object removeFromEnvironment(String propName) throws NamingException {
      return this.delegate.removeFromEnvironment(propName);
   }

   public Object addToEnvironment(String propName, Object object) throws NamingException {
      return this.delegate.addToEnvironment(propName, object);
   }

   public Hashtable getEnvironment() throws NamingException {
      return this.delegate.getEnvironment();
   }

   protected final Object makeTransportable(Object boundObject) throws NamingException {
      return WLNamingManager.getTransportableInstance(boundObject, (Name)null, (Context)null, this.env);
   }

   protected final Context makeTransportable(Context boundCtx) throws NamingException {
      try {
         return (Context)this.makeTransportable((Object)boundCtx);
      } catch (ClassCastException var3) {
         throw new ConfigurationException("A TransportableObjectFactory converted " + boundCtx.toString() + " into a object that does not implement Context");
      }
   }
}

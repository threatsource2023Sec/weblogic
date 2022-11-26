package weblogic.jndi.internal;

import java.net.MalformedURLException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import weblogic.protocol.ServerURL;

public abstract class AbstractURLContext implements Context {
   protected abstract Context getContext(String var1) throws NamingException;

   protected String removeURL(String name) throws InvalidNameException {
      try {
         return name.indexOf(":") < 0 ? name : (new ServerURL(ServerURL.DEFAULT_URL, name)).getFile();
      } catch (MalformedURLException var4) {
         InvalidNameException ne = new InvalidNameException();
         ne.setRootCause(var4);
         throw ne;
      }
   }

   public final Object addToEnvironment(String s, Object obj) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public void bind(String s, Object obj) throws NamingException {
      this.getContext(s).bind(this.removeURL(s), obj);
   }

   public final void bind(Name name, Object obj) throws NamingException {
      this.bind(name.toString(), obj);
   }

   public final void close() throws NamingException {
      throw new OperationNotSupportedException();
   }

   public final String composeName(String s, String s1) throws NamingException {
      return s;
   }

   public final Name composeName(Name name, Name name1) throws NamingException {
      return (Name)name.clone();
   }

   public Context createSubcontext(String s) throws NamingException {
      return this.getContext(s).createSubcontext(this.removeURL(s));
   }

   public final Context createSubcontext(Name name) throws NamingException {
      return this.createSubcontext(name.toString());
   }

   public void destroySubcontext(String s) throws NamingException {
      this.getContext(s).destroySubcontext(this.removeURL(s));
   }

   public final void destroySubcontext(Name name) throws NamingException {
      this.destroySubcontext(name.toString());
   }

   public final Hashtable getEnvironment() throws NamingException {
      throw new OperationNotSupportedException();
   }

   public final String getNameInNamespace() throws NamingException {
      throw new OperationNotSupportedException();
   }

   public final NameParser getNameParser(String s) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public final NameParser getNameParser(Name name) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public NamingEnumeration list(String s) throws NamingException {
      return this.getContext(s).list(this.removeURL(s));
   }

   public final NamingEnumeration list(Name name) throws NamingException {
      return this.list(name.toString());
   }

   public NamingEnumeration listBindings(String s) throws NamingException {
      return this.getContext(s).listBindings(this.removeURL(s));
   }

   public final NamingEnumeration listBindings(Name name) throws NamingException {
      return this.listBindings(name.toString());
   }

   public Object lookup(String s) throws NamingException {
      return this.getContext(s).lookup(this.removeURL(s));
   }

   public final Object lookup(Name name) throws NamingException {
      return this.lookup(name.toString());
   }

   public Object lookupLink(String s) throws NamingException {
      return this.getContext(s).lookupLink(this.removeURL(s));
   }

   public final Object lookupLink(Name name) throws NamingException {
      return this.lookupLink(name.toString());
   }

   public void rebind(String s, Object obj) throws NamingException {
      this.getContext(s).rebind(this.removeURL(s), obj);
   }

   public final void rebind(Name name, Object obj) throws NamingException {
      this.rebind(name.toString(), obj);
   }

   public final Object removeFromEnvironment(String s) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public void rename(String s, String s1) throws NamingException {
      this.getContext(s).rename(this.removeURL(s), s1);
   }

   public final void rename(Name name, Name name1) throws NamingException {
      this.rename(name.toString(), name1.toString());
   }

   public void unbind(String s) throws NamingException {
      this.getContext(s).unbind(this.removeURL(s));
   }

   public final void unbind(Name name) throws NamingException {
      this.unbind(name.toString());
   }
}

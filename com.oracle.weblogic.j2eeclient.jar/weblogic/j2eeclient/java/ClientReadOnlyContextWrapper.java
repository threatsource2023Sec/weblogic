package weblogic.j2eeclient.java;

import java.util.Hashtable;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;

public class ClientReadOnlyContextWrapper implements Context {
   protected final Context context;

   public ClientReadOnlyContextWrapper(Context context) {
      this.context = context;
   }

   public final String getNameInNamespace() throws NamingException {
      return this.context.getNameInNamespace();
   }

   public final void close() throws NamingException {
      this.context.close();
   }

   public final Object lookup(Name n) throws NamingException {
      return this.wrapIfContext(this.context.lookup(n));
   }

   public final Object lookup(String n) throws NamingException {
      return this.wrapIfContext(this.context.lookup(n));
   }

   public final Object lookupLink(Name n) throws NamingException {
      return this.wrapIfContext(this.context.lookupLink(n));
   }

   public final Object lookupLink(String n) throws NamingException {
      return this.wrapIfContext(this.context.lookupLink(n));
   }

   public final void bind(Name n, Object obj) throws NamingException {
      throw this.newOperationNotSupportedException("bind", n);
   }

   public final void bind(String n, Object obj) throws NamingException {
      throw this.newOperationNotSupportedException("bind", n);
   }

   public final void rebind(Name n, Object obj) throws NamingException {
      throw this.newOperationNotSupportedException("rebind", n);
   }

   public final void rebind(String n, Object obj) throws NamingException {
      throw this.newOperationNotSupportedException("rebind", n);
   }

   public final void unbind(Name n) throws NamingException {
      throw this.newOperationNotSupportedException("unbind", n);
   }

   public final void unbind(String n) throws NamingException {
      throw this.newOperationNotSupportedException("unbind", n);
   }

   public final void rename(Name oldName, Name newName) throws NamingException {
      throw this.newOperationNotSupportedException("rename", oldName);
   }

   public final void rename(String oldName, String newName) throws NamingException {
      throw this.newOperationNotSupportedException("rename", oldName);
   }

   public final NamingEnumeration list(Name n) throws NamingException {
      return this.context.list(n);
   }

   public final NamingEnumeration list(String n) throws NamingException {
      return this.context.list(n);
   }

   public final NamingEnumeration listBindings(Name n) throws NamingException {
      return this.context.listBindings(n);
   }

   public final NamingEnumeration listBindings(String n) throws NamingException {
      return this.context.listBindings(n);
   }

   public final NameParser getNameParser(Name name) throws NamingException {
      return this.context.getNameParser(name);
   }

   public final NameParser getNameParser(String name) throws NamingException {
      return this.context.getNameParser(name);
   }

   public final Name composeName(Name name, Name prefix) throws NamingException {
      return this.context.composeName(name, prefix);
   }

   public final String composeName(String name, String prefix) throws NamingException {
      return this.context.composeName(name, prefix);
   }

   public final Context createSubcontext(Name n) throws NamingException {
      throw this.newOperationNotSupportedException("createSubcontext", n);
   }

   public final Context createSubcontext(String n) throws NamingException {
      throw this.newOperationNotSupportedException("createSubcontext", n);
   }

   public final void destroySubcontext(Name n) throws NamingException {
      throw this.newOperationNotSupportedException("destroySubcontext", n);
   }

   public final void destroySubcontext(String n) throws NamingException {
      throw this.newOperationNotSupportedException("destroySubcontext", n);
   }

   public final Hashtable getEnvironment() throws NamingException {
      return this.context.getEnvironment();
   }

   public final Object addToEnvironment(String propName, Object value) throws NamingException {
      return this.context.addToEnvironment(propName, value);
   }

   public final Object removeFromEnvironment(String propName) throws NamingException {
      return this.context.removeFromEnvironment(propName);
   }

   public final String toString() {
      return this.context.toString();
   }

   protected Object wrapIfContext(Object o) {
      return !(o instanceof ClientReadOnlyContextWrapper) && o instanceof Context ? new ClientReadOnlyContextWrapper((Context)o) : o;
   }

   private OperationNotSupportedException newOperationNotSupportedException(String operation, Name remainingName) {
      OperationNotSupportedException ne = new OperationNotSupportedException(operation + " not allowed in a ReadOnlyContext");
      ne.setRemainingName(remainingName);
      return ne;
   }

   private OperationNotSupportedException newOperationNotSupportedException(String operation, String remainingName) {
      Name remaining = null;

      try {
         remaining = new CompositeName(remainingName);
      } catch (InvalidNameException var5) {
      }

      return this.newOperationNotSupportedException(operation, (Name)remaining);
   }
}

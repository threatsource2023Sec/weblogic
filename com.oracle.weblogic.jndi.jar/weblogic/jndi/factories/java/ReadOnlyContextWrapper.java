package weblogic.jndi.factories.java;

import java.util.Hashtable;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.event.EventContext;
import javax.naming.event.NamingListener;

public final class ReadOnlyContextWrapper implements EventContext {
   private final Context context;
   private final EventContext eventContext;

   public ReadOnlyContextWrapper(Context context) {
      this.context = context;
      if (context instanceof EventContext) {
         this.eventContext = (EventContext)context;
      } else {
         this.eventContext = null;
      }

   }

   public String getNameInNamespace() throws NamingException {
      return this.context.getNameInNamespace();
   }

   public void close() throws NamingException {
      this.context.close();
   }

   public Object lookup(Name n) throws NamingException {
      return this.wrapIfContext(this.context.lookup(n));
   }

   public Object lookup(String n) throws NamingException {
      return this.wrapIfContext(this.context.lookup(n));
   }

   public Object lookupLink(Name n) throws NamingException {
      return this.wrapIfContext(this.context.lookupLink(n));
   }

   public Object lookupLink(String n) throws NamingException {
      return this.wrapIfContext(this.context.lookupLink(n));
   }

   public void bind(Name n, Object obj) throws NamingException {
      throw this.newOperationNotSupportedException("bind", n);
   }

   public void bind(String n, Object obj) throws NamingException {
      throw this.newOperationNotSupportedException("bind", n);
   }

   public void rebind(Name n, Object obj) throws NamingException {
      throw this.newOperationNotSupportedException("rebind", n);
   }

   public void rebind(String n, Object obj) throws NamingException {
      throw this.newOperationNotSupportedException("rebind", n);
   }

   public void unbind(Name n) throws NamingException {
      throw this.newOperationNotSupportedException("unbind", n);
   }

   public void unbind(String n) throws NamingException {
      throw this.newOperationNotSupportedException("unbind", n);
   }

   public void rename(Name oldName, Name newName) throws NamingException {
      throw this.newOperationNotSupportedException("rename", oldName);
   }

   public void rename(String oldName, String newName) throws NamingException {
      throw this.newOperationNotSupportedException("rename", oldName);
   }

   public NamingEnumeration list(Name n) throws NamingException {
      return this.context.list(n);
   }

   public NamingEnumeration list(String n) throws NamingException {
      return this.context.list(n);
   }

   public NamingEnumeration listBindings(Name n) throws NamingException {
      return this.context.listBindings(n);
   }

   public NamingEnumeration listBindings(String n) throws NamingException {
      return this.context.listBindings(n);
   }

   public NameParser getNameParser(Name name) throws NamingException {
      return this.context.getNameParser(name);
   }

   public NameParser getNameParser(String name) throws NamingException {
      return this.context.getNameParser(name);
   }

   public Name composeName(Name name, Name prefix) throws NamingException {
      return this.context.composeName(name, prefix);
   }

   public String composeName(String name, String prefix) throws NamingException {
      return this.context.composeName(name, prefix);
   }

   public Context createSubcontext(Name n) throws NamingException {
      throw this.newOperationNotSupportedException("createSubcontext", n);
   }

   public Context createSubcontext(String n) throws NamingException {
      throw this.newOperationNotSupportedException("createSubcontext", n);
   }

   public void destroySubcontext(Name n) throws NamingException {
      throw this.newOperationNotSupportedException("destroySubcontext", n);
   }

   public void destroySubcontext(String n) throws NamingException {
      throw this.newOperationNotSupportedException("destroySubcontext", n);
   }

   public Hashtable getEnvironment() throws NamingException {
      return this.context.getEnvironment();
   }

   public Object addToEnvironment(String propName, Object value) throws NamingException {
      return this.context.addToEnvironment(propName, value);
   }

   public Object removeFromEnvironment(String propName) throws NamingException {
      return this.context.removeFromEnvironment(propName);
   }

   public String toString() {
      return this.context.toString();
   }

   private Object wrapIfContext(Object o) {
      return !(o instanceof ReadOnlyContextWrapper) && o instanceof Context ? new ReadOnlyContextWrapper((Context)o) : o;
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

   public void addNamingListener(Name name, int scope, NamingListener listener) throws NamingException {
      this.addNamingListener(name.toString(), scope, listener);
   }

   public void addNamingListener(String name, int scope, NamingListener listener) throws NamingException {
      if (this.eventContext != null) {
         this.eventContext.addNamingListener(name, scope, listener);
      }

   }

   public void removeNamingListener(NamingListener listener) throws NamingException {
      if (this.eventContext != null) {
         this.eventContext.removeNamingListener(listener);
      }

   }

   public boolean targetMustExist() throws NamingException {
      return true;
   }
}

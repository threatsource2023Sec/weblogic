package com.bea.cache.tx;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.spi.InitialContextFactory;

public class LocalContextFactory implements InitialContextFactory {
   private static final LocalContext _ctx = new LocalContext();

   public Context getInitialContext(Hashtable env) {
      return _ctx;
   }

   private static class LocalContext implements Context {
      private final Map _bindings = new HashMap();

      public LocalContext() {
         LocalUserTransaction trans = new LocalUserTransaction();
         this._bindings.put("java:comp/UserTransaction", trans);
         this._bindings.put("javax.transaction.UserTransaction", trans);
         this._bindings.put("UserTransaction", trans);
      }

      public Object lookup(Name name) {
         return this.lookup(name.toString());
      }

      public Object lookup(String name) {
         return this._bindings.get(name);
      }

      public void bind(Name name, Object obj) throws NamingException {
         this.bind(name.toString(), obj);
      }

      public void bind(String name, Object obj) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public void rebind(Name name, Object obj) throws NamingException {
         this.rebind(name.toString(), obj);
      }

      public void rebind(String name, Object obj) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public void unbind(Name name) throws NamingException {
         this.unbind(name.toString());
      }

      public void unbind(String name) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public void rename(Name old, Name name) throws NamingException {
         this.rename(old.toString(), name.toString());
      }

      public void rename(String old, String name) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public NamingEnumeration list(Name name) throws NamingException {
         return this.list(name.toString());
      }

      public NamingEnumeration list(String name) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public NamingEnumeration listBindings(Name name) throws NamingException {
         return this.listBindings(name.toString());
      }

      public NamingEnumeration listBindings(String name) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public void destroySubcontext(Name name) throws NamingException {
         this.destroySubcontext(name.toString());
      }

      public void destroySubcontext(String name) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public Context createSubcontext(Name name) throws NamingException {
         return this.createSubcontext(name.toString());
      }

      public Context createSubcontext(String name) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public Object lookupLink(Name name) throws NamingException {
         return this.lookupLink(name.toString());
      }

      public Object lookupLink(String name) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public NameParser getNameParser(Name name) throws NamingException {
         return this.getNameParser(name.toString());
      }

      public NameParser getNameParser(String name) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public Name composeName(Name name, Name prefix) throws NamingException {
         throw new NamingException("Not supported.");
      }

      public String composeName(String name, String prefix) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public Object addToEnvironment(String prop, Object val) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public Object removeFromEnvironment(String prop) throws NamingException {
         throw new OperationNotSupportedException();
      }

      public Hashtable getEnvironment() throws NamingException {
         throw new OperationNotSupportedException();
      }

      public void close() {
      }

      public String getNameInNamespace() throws NamingException {
         throw new OperationNotSupportedException();
      }
   }
}

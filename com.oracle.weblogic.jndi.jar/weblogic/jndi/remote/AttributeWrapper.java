package weblogic.jndi.remote;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import weblogic.jndi.internal.WLNamingManager;
import weblogic.rmi.extensions.RemoteRuntimeException;

public final class AttributeWrapper implements RemoteAttribute {
   private Attribute delegate;
   private Hashtable env;

   protected Hashtable env() {
      return this.env;
   }

   public Attribute delegate() {
      return this.delegate;
   }

   public AttributeWrapper(Attribute delegate, Hashtable env) {
      this.delegate = delegate;
      this.env = env;
   }

   public boolean add(Object value) {
      return this.delegate.add(value);
   }

   public void add(int idx, Object value) {
      this.delegate.add(idx, value);
   }

   public void clear() {
      this.delegate.clear();
   }

   public Object clone() {
      try {
         return this.makeTransportable(this.delegate.clone());
      } catch (NamingException var2) {
         throw new RemoteRuntimeException("Failed to create a transportable instance of " + this.delegate + " due to :", var2);
      }
   }

   public boolean contains(Object value) {
      return this.delegate.contains(value);
   }

   public Object get() throws NamingException {
      return this.makeTransportable(this.delegate.get());
   }

   public Object get(int idx) throws NamingException {
      return this.makeTransportable(this.delegate.get(idx));
   }

   public NamingEnumeration getAll() throws NamingException {
      return this.makeTransportable(this.delegate.getAll());
   }

   public DirContext getAttributeDefinition() throws NamingException {
      return this.makeTransportable(this.delegate.getAttributeDefinition());
   }

   public DirContext getAttributeSyntaxDefinition() throws NamingException {
      return this.makeTransportable(this.delegate.getAttributeSyntaxDefinition());
   }

   public String getID() {
      return this.delegate.getID();
   }

   public boolean isOrdered() {
      return this.delegate.isOrdered();
   }

   public Object remove(int idx) {
      try {
         return this.makeTransportable(this.delegate.remove(idx));
      } catch (NamingException var3) {
         return null;
      }
   }

   public boolean remove(Object value) {
      return this.delegate.remove(value);
   }

   public Object set(int idx, Object value) {
      try {
         return this.makeTransportable(this.delegate.set(idx, value));
      } catch (NamingException var4) {
         return null;
      }
   }

   public int size() {
      return this.delegate.size();
   }

   protected final Object makeTransportable(Object object) throws NamingException {
      return WLNamingManager.getTransportableInstance(object, (Name)null, (Context)null, this.env);
   }

   protected final NamingEnumeration makeTransportable(NamingEnumeration enum_) throws NamingException {
      return (NamingEnumeration)this.makeTransportable((Object)enum_);
   }

   protected final DirContext makeTransportable(DirContext ctx) throws NamingException {
      return (DirContext)this.makeTransportable((Object)ctx);
   }
}

package weblogic.jndi.remote;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import weblogic.jndi.internal.WLNamingManager;
import weblogic.rmi.extensions.RemoteRuntimeException;

public final class AttributesWrapper implements RemoteAttributes {
   private Attributes delegate;
   private Hashtable env;

   protected Hashtable env() {
      return this.env;
   }

   protected Attributes delegate() {
      return this.delegate;
   }

   public AttributesWrapper(Attributes delegate, Hashtable env) {
      this.delegate = delegate;
      this.env = env;
   }

   public Object clone() {
      return this.makeTransportable(this.delegate.clone());
   }

   public Attribute get(String attrName) {
      return this.makeTransportable(this.delegate.get(attrName));
   }

   public NamingEnumeration getAll() {
      return this.makeTransportable(this.delegate.getAll());
   }

   public NamingEnumeration getIDs() {
      return this.makeTransportable(this.delegate.getIDs());
   }

   public boolean isCaseIgnored() {
      return this.delegate.isCaseIgnored();
   }

   public Attribute put(Attribute attr) {
      Attribute realAttr = attr;
      if (attr instanceof AttributeWrapper) {
         realAttr = ((AttributeWrapper)attr).delegate();
      }

      return this.makeTransportable(this.delegate.put(realAttr));
   }

   public Attribute put(String name, Object value) {
      return this.makeTransportable(this.delegate.put(name, value));
   }

   public Attribute remove(String attrName) {
      return this.makeTransportable(this.delegate.remove(attrName));
   }

   public int size() {
      return this.delegate.size();
   }

   protected final Object makeTransportable(Object object) {
      try {
         return WLNamingManager.getTransportableInstance(object, (Name)null, (Context)null, this.env);
      } catch (NamingException var3) {
         throw new RemoteRuntimeException("Failed to create a transportable instance of " + object + " due to :", var3);
      }
   }

   protected final NamingEnumeration makeTransportable(NamingEnumeration enum_) {
      return (NamingEnumeration)this.makeTransportable((Object)enum_);
   }

   protected final Attribute makeTransportable(Attribute attr) {
      return (Attribute)this.makeTransportable((Object)attr);
   }
}

package weblogic.jndi.internal;

import java.rmi.Remote;
import java.util.Hashtable;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;
import weblogic.common.internal.PassivationUtils;
import weblogic.jndi.TransportableObjectFactory;
import weblogic.jndi.WLContext;
import weblogic.jndi.remote.AttributeWrapper;
import weblogic.jndi.remote.AttributesWrapper;
import weblogic.jndi.remote.ContextWrapper;
import weblogic.jndi.remote.DirContextWrapper;
import weblogic.jndi.remote.NamingEnumerationWrapper;

public final class BuiltinTransportableObjectFactory implements TransportableObjectFactory {
   public Object getObjectInstance(Object object, Name name, Context context, Hashtable env) throws Exception {
      Object transportable = object;
      if (!this.isAlreadyTransportable(object)) {
         if (object instanceof Context) {
            transportable = this.makeTransportable((Context)object, env);
         } else if (object instanceof Binding) {
            transportable = this.makeTransportable((Binding)object, env);
         } else if (object instanceof NamingEnumeration) {
            transportable = this.makeTransportable((NamingEnumeration)object, env);
         } else if (object instanceof Attributes) {
            transportable = this.makeTransportable((Attributes)object, env);
         } else if (object instanceof Attribute) {
            transportable = this.makeTransportable((Attribute)object, env);
         } else {
            transportable = null;
         }
      }

      if (NamingFactoriesDebugLogger.isDebugEnabled() && transportable != null && transportable != object) {
         NamingFactoriesDebugLogger.debug("Wrapping " + object.getClass().getName() + " with " + transportable.getClass().getName() + " to make transportable");
      }

      return transportable;
   }

   private final Object makeTransportable(Context ctx, Hashtable env) {
      return ctx instanceof DirContext ? new DirContextWrapper((DirContext)ctx, env) : new ContextWrapper(ctx, env);
   }

   private final Object makeTransportable(NamingEnumeration e, Hashtable env) {
      return new NamingEnumerationWrapper(e, env);
   }

   private final Object makeTransportable(Binding binding, Hashtable env) throws NamingException {
      Object newO;
      if (binding instanceof SearchResult) {
         SearchResult sr = (SearchResult)binding;
         newO = sr.getObject();
         Object newO = WLNamingManager.getTransportableInstance(newO, (Name)null, (Context)null, env);
         Attributes attrs = sr.getAttributes();
         Attributes newAttrs = (Attributes)WLNamingManager.getTransportableInstance(attrs, (Name)null, (Context)null, env);
         return newO == newO && attrs == newAttrs ? binding : new SearchResult(sr.getName(), newO, newAttrs);
      } else {
         Object o = binding.getObject();
         newO = WLNamingManager.getTransportableInstance(o, (Name)null, (Context)null, env);
         return o != newO ? new Binding(binding.getName(), newO) : binding;
      }
   }

   private final Object makeTransportable(Attributes attrs, Hashtable env) {
      return this.isSerializable(attrs) ? attrs : new AttributesWrapper(attrs, env);
   }

   private final Object makeTransportable(Attribute attr, Hashtable env) {
      return this.isSerializable(attr) ? attr : new AttributeWrapper(attr, env);
   }

   public boolean isAlreadyTransportable(Object o) {
      return o instanceof WLContext || o instanceof Remote || o instanceof NameClassPairEnumeration;
   }

   public boolean isSerializable(Object o) {
      return PassivationUtils.isSerializable(o);
   }
}

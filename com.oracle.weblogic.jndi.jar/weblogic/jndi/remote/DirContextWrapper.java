package weblogic.jndi.remote;

import java.util.Hashtable;
import javax.naming.CompositeName;
import javax.naming.ConfigurationException;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

public final class DirContextWrapper extends ContextWrapper implements RemoteDirContext {
   private DirContext delegate;

   public DirContextWrapper(DirContext delegate) {
      super(delegate);
      this.delegate = delegate;
   }

   public DirContextWrapper(DirContext delegate, Hashtable env) {
      super(delegate, env);
      this.delegate = delegate;
   }

   public void bind(String name, Object obj, Attributes attrs) throws NamingException {
      this.delegate.bind(name, obj, attrs);
   }

   public void bind(Name name, Object obj, Attributes attrs) throws NamingException {
      this.delegate.bind(name, obj, attrs);
   }

   public DirContext createSubcontext(String name, Attributes attrs) throws NamingException {
      return this.createSubcontext((Name)(new CompositeName(name)), attrs);
   }

   public DirContext createSubcontext(Name name, Attributes attrs) throws NamingException {
      return this.makeTransportable(this.delegate.createSubcontext(name, attrs));
   }

   public Attributes getAttributes(String name) throws NamingException {
      return (Attributes)this.makeTransportable(this.delegate.getAttributes(name));
   }

   public Attributes getAttributes(Name name) throws NamingException {
      return (Attributes)this.makeTransportable(this.delegate.getAttributes(name));
   }

   public Attributes getAttributes(String name, String[] attrIds) throws NamingException {
      return (Attributes)this.makeTransportable(this.delegate.getAttributes(name, attrIds));
   }

   public Attributes getAttributes(Name name, String[] attrIds) throws NamingException {
      return this.delegate.getAttributes(name, attrIds);
   }

   public void modifyAttributes(String name, int mod_op, Attributes attrs) throws NamingException {
      this.delegate.modifyAttributes(name, mod_op, attrs);
   }

   public void modifyAttributes(Name name, int mod_op, Attributes attrs) throws NamingException {
      this.delegate.modifyAttributes(name, mod_op, attrs);
   }

   public void modifyAttributes(String name, ModificationItem[] mods) throws NamingException {
      this.delegate.modifyAttributes(name, mods);
   }

   public void modifyAttributes(Name name, ModificationItem[] mods) throws NamingException {
      this.delegate.modifyAttributes(name, mods);
   }

   public void rebind(String name, Object obj, Attributes attrs) throws NamingException {
      this.delegate.rebind(name, obj, attrs);
   }

   public void rebind(Name name, Object obj, Attributes attrs) throws NamingException {
      this.delegate.rebind(name, obj, attrs);
   }

   public DirContext getSchema(String name) throws NamingException {
      return this.getSchema((Name)(new CompositeName(name)));
   }

   public DirContext getSchema(Name name) throws NamingException {
      return this.makeTransportable(this.delegate.getSchema(name));
   }

   public DirContext getSchemaClassDefinition(String name) throws NamingException {
      return this.getSchemaClassDefinition((Name)(new CompositeName(name)));
   }

   public DirContext getSchemaClassDefinition(Name name) throws NamingException {
      return this.makeTransportable(this.delegate.getSchemaClassDefinition(name));
   }

   public NamingEnumeration search(String name, Attributes matchingAttributes) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.search(name, matchingAttributes), this.env());
   }

   public NamingEnumeration search(Name name, Attributes matchingAttributes) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.search(name, matchingAttributes), this.env());
   }

   public NamingEnumeration search(String name, Attributes matchingAttrs, String[] attrsToReturn) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.search(name, matchingAttrs, attrsToReturn), this.env());
   }

   public NamingEnumeration search(Name name, Attributes matchingAttrs, String[] attrsToReturn) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.search(name, matchingAttrs, attrsToReturn), this.env());
   }

   public NamingEnumeration search(String name, String filter, SearchControls controls) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.search(name, filter, controls), this.env());
   }

   public NamingEnumeration search(Name name, String filter, SearchControls controls) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.search(name, filter, controls), this.env());
   }

   public NamingEnumeration search(String name, String filterExpr, Object[] filterArgs, SearchControls controls) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.search(name, filterExpr, filterArgs, controls), this.env());
   }

   public NamingEnumeration search(Name name, String filterExpr, Object[] filterArgs, SearchControls controls) throws NamingException {
      return new NamingEnumerationWrapper(this.delegate.search(name, filterExpr, filterArgs, controls), this.env());
   }

   protected final DirContext makeTransportable(DirContext boundCtx) throws NamingException {
      try {
         return (DirContext)this.makeTransportable(boundCtx);
      } catch (ClassCastException var3) {
         throw new ConfigurationException("A TransportableObjectFactory converted " + boundCtx.toString() + " into a object that does not implement DirContext");
      }
   }
}

package weblogic.jndi;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Binding;
import javax.naming.CompoundName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.LinkRef;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.Reference;
import javax.naming.spi.NamingManager;

public class SimpleContext implements Context {
   private boolean nonListable = false;
   private static Properties syntax = new Properties();
   protected static final NameParser parser;
   protected Map map = new ConcurrentHashMap();

   protected Context resolve(Name name, boolean createSubcontexts) throws NamingException {
      Object o = this.map.get(name.get(0));
      if (o instanceof Context) {
         return (Context)o;
      } else if (createSubcontexts && o == null) {
         return this.createSubcontext(name.get(0));
      } else {
         throw new NameNotFoundException("remaining name: " + name);
      }
   }

   protected Context resolve(Name name) throws NamingException {
      return this.resolve(name, false);
   }

   public Object lookup(Name name) throws NamingException {
      try {
         return this.doLookup(name);
      } catch (NamingException var4) {
         throw var4;
      } catch (Throwable var5) {
         NamingException namingException = new NamingException();
         namingException.setRootCause(var5);
         throw namingException;
      }
   }

   private Object doLookup(Name name) throws NamingException {
      switch (name.size()) {
         case 0:
            return this;
         case 1:
            Object key = name.get(0);
            if (!this.map.containsKey(key)) {
               throw new NameNotFoundException("remaining name: " + name);
            }

            Object result = this.map.get(key);
            if (result instanceof SimpleReference) {
               result = ((SimpleReference)result).get();
            } else if (result instanceof LinkRef) {
               result = (new InitialContext(this.getEnvironment())).lookup(((LinkRef)result).getLinkName());
            } else if (result instanceof Reference) {
               try {
                  result = NamingManager.getObjectInstance(result, name, this, this.getEnvironment());
               } catch (Exception var5) {
               }
            } else if (result instanceof OpaqueReference) {
               result = ((OpaqueReference)result).getReferent(name, this);
            }

            return result;
         default:
            return this.resolve(name).lookup(name.getSuffix(1));
      }
   }

   public Object lookup(String name) throws NamingException {
      return this.lookup(parser.parse(name));
   }

   public void bind(Name name, Object obj) throws NamingException {
      switch (name.size()) {
         case 0:
            throw new NamingException("bind name my not be empty");
         case 1:
            this.map.put(name.get(0), obj);
            return;
         default:
            this.resolve(name, true).bind(name.getSuffix(1), obj);
      }
   }

   public void bind(String name, Object obj) throws NamingException {
      this.bind(parser.parse(name), obj);
   }

   public void rebind(Name name, Object obj) throws NamingException {
      switch (name.size()) {
         case 0:
            throw new NamingException("rebind name may not be empty");
         case 1:
            this.map.put(name.get(0), obj);
            return;
         default:
            this.resolve(name).rebind(name.getSuffix(1), obj);
      }
   }

   public void rebind(String name, Object obj) throws NamingException {
      this.rebind(parser.parse(name), obj);
   }

   public void unbind(Name name) throws NamingException {
      switch (name.size()) {
         case 0:
            throw new NamingException("unbind name may not be empty");
         case 1:
            this.map.remove(name.get(0));
            return;
         default:
            this.resolve(name).unbind(name.getSuffix(1));
      }
   }

   public void unbind(String name) throws NamingException {
      this.unbind(parser.parse(name));
   }

   public void rename(Name oldName, Name newName) throws NamingException {
      this.bind(newName, this.lookup(oldName));
      this.unbind(oldName);
   }

   public void rename(String oldName, String newName) throws NamingException {
      this.bind(newName, this.lookup(oldName));
      this.unbind(oldName);
   }

   public NamingEnumeration list(Name name) throws NamingException {
      return (NamingEnumeration)(name.isEmpty() ? new NamingEnumerationBase(this.getFilterNonListableMap().entrySet().iterator()) {
         public Object nextElement() {
            Map.Entry e = (Map.Entry)this.i.next();
            Object v = e.getValue();
            return new NameClassPair(e.getKey().toString(), v == null ? null : v.getClass().getName());
         }
      } : this.resolve(name).list(name.getSuffix(1)));
   }

   private Map getFilterNonListableMap() {
      Map filteredMap = new HashMap();
      Iterator var2 = this.map.keySet().iterator();

      while(true) {
         Object key;
         Object value;
         do {
            if (!var2.hasNext()) {
               return filteredMap;
            }

            key = var2.next();
            value = this.map.get(key);
         } while(value instanceof SimpleContext && ((SimpleContext)value).nonListable);

         if (!(value instanceof NonListable)) {
            filteredMap.put(key, value);
         }
      }
   }

   public NamingEnumeration list(String name) throws NamingException {
      return this.list(parser.parse(name));
   }

   public NamingEnumeration listBindings(Name name) throws NamingException {
      return (NamingEnumeration)(name.isEmpty() ? new NamingEnumerationBase(this.getFilterNonListableMap().entrySet().iterator()) {
         public Object nextElement() {
            Map.Entry e = (Map.Entry)this.i.next();
            return new Binding(e.getKey().toString(), e.getValue());
         }
      } : this.resolve(name).listBindings(name.getSuffix(1)));
   }

   public NamingEnumeration listBindings(String name) throws NamingException {
      return this.listBindings(parser.parse(name));
   }

   public void destroySubcontext(Name name) throws NamingException {
      switch (name.size()) {
         case 0:
            throw new NamingException("destroySubcontext name may not be empty");
         case 1:
            this.map.remove(name.get(0));
            return;
         default:
            this.resolve(name).destroySubcontext(name.getSuffix(1));
      }
   }

   public void destroySubcontext(String name) throws NamingException {
      this.destroySubcontext(parser.parse(name));
   }

   public Context createSubcontext(Name name) throws NamingException {
      switch (name.size()) {
         case 0:
            throw new NamingException("createSubcontext name may not be empty");
         case 1:
            Context c = this.createNewSubcontext(this, name);
            this.map.put(name.get(0), c);
            return c;
         default:
            return this.resolve(name).createSubcontext(name.getSuffix(1));
      }
   }

   public Context createNonListableSubcontext(Name name) throws NamingException {
      switch (name.size()) {
         case 0:
            throw new NamingException("createNonListableSubcontext name may not be empty");
         case 1:
            SimpleContext c = new SimpleContext();
            c.nonListable = true;
            this.map.put(name.get(0), c);
            return c;
         default:
            return ((SimpleContext)this.resolve(name)).createNonListableSubcontext(name.getSuffix(1));
      }
   }

   public Context createNonListableSubcontext(String name) throws NamingException {
      return this.createNonListableSubcontext(parser.parse(name));
   }

   protected Context createNewSubcontext(Context parent, Name name) throws NamingException {
      return new SimpleContext();
   }

   public Context createSubcontext(String name) throws NamingException {
      return this.createSubcontext(parser.parse(name));
   }

   public Object lookupLink(Name name) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public Object lookupLink(String name) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public NameParser getNameParser(Name name) throws NamingException {
      return name.isEmpty() ? parser : this.resolve(name).getNameParser(name.getSuffix(1));
   }

   public NameParser getNameParser(String name) throws NamingException {
      return this.getNameParser(parser.parse(name));
   }

   public Name composeName(Name name, Name prefix) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public String composeName(String name, String prefix) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public Object addToEnvironment(String propName, Object propVal) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public Object removeFromEnvironment(String propName) throws NamingException {
      throw new OperationNotSupportedException();
   }

   public Hashtable getEnvironment() throws NamingException {
      return null;
   }

   public void close() throws NamingException {
   }

   public String getNameInNamespace() throws NamingException {
      throw new OperationNotSupportedException();
   }

   static {
      syntax.put("jndi.syntax.direction", "left_to_right");
      syntax.put("jndi.syntax.separator", "/");
      parser = new NameParser() {
         public Name parse(String name) throws NamingException {
            return new CompoundName(name, SimpleContext.syntax);
         }
      };
   }

   abstract static class NamingEnumerationBase implements NamingEnumeration {
      protected Iterator i;

      protected NamingEnumerationBase(Iterator i) {
         this.i = i;
      }

      public Object next() throws NamingException {
         return this.nextElement();
      }

      public boolean hasMore() throws NamingException {
         return this.hasMoreElements();
      }

      public boolean hasMoreElements() {
         return this.i.hasNext();
      }

      public void close() {
      }
   }

   public interface SimpleReference {
      Object get() throws NamingException;
   }
}

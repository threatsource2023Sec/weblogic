package weblogic.jndi.internal;

import java.rmi.ConnectIOException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.naming.Binding;
import javax.naming.CannotProceedException;
import javax.naming.CompositeName;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.ContextNotEmptyException;
import javax.naming.InvalidNameException;
import javax.naming.LinkException;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.DirContext;
import javax.naming.event.EventContext;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingListener;
import javax.naming.spi.DirectoryManager;
import javax.naming.spi.NamingManager;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.AggregatableInternal;
import weblogic.jndi.WLContext;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.work.WorkManagerFactory;

public class BasicNamingNode implements NamingNode {
   private static final char[] DEFAULT_SEPARATOR_ARRAY = new char[]{'/'};
   private final char[] separators;
   private final int separatorsLength;
   private String nameInNamespace;
   private BasicNamingNode parent;
   private final ConcurrentHashMap map;
   private final CopyOnWriteArrayList onelevelNameListenerList;
   private final ConcurrentHashMap objectListenerMap;
   protected final CopyOnWriteArrayList subtreeScopeNameListenerList;
   protected NameParser nameParser;
   private boolean nonListable;
   static final int RESOLVE_TO_LINK = 0;
   private static final int RESOLVE_FULLY = 1;

   public BasicNamingNode() {
      this(DEFAULT_SEPARATOR_ARRAY, (BasicNamingNode)null, "");
   }

   protected BasicNamingNode(char[] separators) {
      this(separators, (BasicNamingNode)null, "");
   }

   protected BasicNamingNode(char[] separators, BasicNamingNode parent, String relativeName) {
      this(separators, parent, relativeName, new CopyOnWriteArrayList());
   }

   protected BasicNamingNode(char[] separators, BasicNamingNode parent, String relativeName, CopyOnWriteArrayList subtreeScopeNameListenerList) {
      this.map = new ConcurrentHashMap(3);
      this.onelevelNameListenerList = new CopyOnWriteArrayList();
      this.objectListenerMap = new ConcurrentHashMap(5);
      this.separators = separators;
      this.separatorsLength = separators.length;
      if (parent == null) {
         this.nameParser = new WLNameParser(separators);
      } else {
         this.nameParser = parent.nameParser;
      }

      this.setParent(parent, relativeName);
      this.subtreeScopeNameListenerList = subtreeScopeNameListenerList;
   }

   public String getNameInNamespace() {
      return this.nameInNamespace;
   }

   public String getRelativeName() throws NamingException {
      String relativeName = "";

      for(String name = this.getNameInNamespace(); name.length() > 0; name = this.getRest(name)) {
         relativeName = this.getPrefix(name);
      }

      return relativeName;
   }

   public NamingNode getParent() {
      return this.parent;
   }

   public void setParent(BasicNamingNode parent, String relativeName) {
      this.parent = parent;
      if (parent == null) {
         this.nameInNamespace = "";
      } else {
         try {
            this.nameInNamespace = parent.getNameInNamespace(relativeName);
         } catch (NamingException var4) {
            throw new AssertionError(var4);
         }
      }

   }

   public NameParser getNameParser(String name, Hashtable env) throws NameNotFoundException, NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (prefix.length() == 0) {
         return this.nameParser;
      } else {
         Object object = this.lookupHere(prefix, env, restOfName);

         try {
            NameParser parser;
            if (object instanceof NamingNode) {
               parser = ((NamingNode)object).getNameParser(restOfName, env);
            } else {
               parser = this.getContinuationCtx(object, prefix, restOfName, env).getNameParser(restOfName);
            }

            return parser;
         } catch (NamingException var7) {
            throw this.prependResolvedNameToException(prefix, var7);
         }
      }
   }

   private String composeName(String relativeName, String prefix) {
      if (prefix.length() == 0) {
         return relativeName;
      } else {
         return relativeName != null && relativeName.length() != 0 ? prefix + this.separators[0] + relativeName : prefix;
      }
   }

   public String getNameInNamespace(String relativeName) throws NamingException {
      if (relativeName.length() == 0) {
         return this.nameInNamespace;
      } else if (this.nameInNamespace.length() > 0) {
         return this.nameParser.equals(this.parent.nameParser) ? this.composeName(this.escapeBinding(relativeName), this.nameInNamespace) : this.nameInNamespace + "/" + this.escapeBinding(relativeName);
      } else {
         return relativeName;
      }
   }

   public Object lookup(String name, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      Object object = this.lookupHere(prefix, env, restOfName);
      if (restOfName.length() == 0) {
         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ lookup(" + name + ", " + object.getClass().getName() + ") succeeded");
         }

         return this.resolveObject(prefix, object, env);
      } else {
         try {
            if (object instanceof NamingNode) {
               object = ((NamingNode)object).lookup(restOfName, env);
            } else {
               object = this.getContinuationCtx(object, prefix, restOfName, env).lookup(restOfName);
            }
         } catch (NamingException var7) {
            throw this.prependResolvedNameToException(prefix, var7);
         }

         return this.makeTransportable(object, restOfName, env);
      }
   }

   public boolean isBindable(String name, Object boundObject, Hashtable env) throws NamingException, RemoteException {
      try {
         String prefix = this.getPrefix(name);
         String restOfName = this.getRest(name);
         Object object = this.lookupHere(prefix, env, restOfName);
         if (restOfName.length() == 0) {
            if (NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("+++ isBindable(" + name + ", " + object.getClass().getName() + ") exists");
            }

            if (boundObject instanceof AggregatableInternal && object instanceof AggregatableInternal) {
               return ((AggregatableInternal)object).isBindable((AggregatableInternal)boundObject);
            } else {
               return boundObject instanceof Aggregatable && object instanceof Aggregatable ? object.getClass().isAssignableFrom(boundObject.getClass()) : false;
            }
         } else {
            try {
               return object instanceof NamingNode ? ((NamingNode)object).isBindable(restOfName, boundObject, env) : false;
            } catch (NamingException var8) {
               throw this.prependResolvedNameToException(prefix, var8);
            }
         }
      } catch (NameNotFoundException var9) {
         return true;
      }
   }

   public boolean isBindable(String name, boolean isAggregatable, Hashtable env) throws NamingException, RemoteException {
      try {
         String prefix = this.getPrefix(name);
         String restOfName = this.getRest(name);
         Object object = this.lookupHere(prefix, env, restOfName);
         if (restOfName.length() == 0) {
            if (NamingDebugLogger.isDebugEnabled()) {
               NamingDebugLogger.debug("+++ isBindable(" + name + ", " + object.getClass().getName() + ") exists");
            }

            return isAggregatable && object instanceof Aggregatable;
         } else {
            try {
               return object instanceof NamingNode ? ((NamingNode)object).isBindable(restOfName, isAggregatable, env) : false;
            } catch (NamingException var8) {
               throw this.prependResolvedNameToException(prefix, var8);
            }
         }
      } catch (NameNotFoundException var9) {
         return true;
      }
   }

   public Object authenticatedLookup(String name, Hashtable env, AuthenticatedSubject id) throws NamingException, RemoteException {
      throw new UnsupportedOperationException("authenticatedLookup is unavailable for BasicNamingNode types");
   }

   protected Object lookupHere(String atom, Hashtable env, String restOfName) throws NameNotFoundException, NamingException {
      if (atom.length() == 0) {
         return this;
      } else {
         Object object = this.map.get(atom);
         if (object == null) {
            if (NamingResolutionDebugLogger.isDebugEnabled()) {
               NamingResolutionDebugLogger.debug("--- failed to find " + atom);
            }

            String name;
            if (this.separatorsLength == 0) {
               name = restOfName + this.getNameInNamespace(atom);
            } else {
               name = this.composeName(restOfName, this.getNameInNamespace(atom));
            }

            if (restOfName != null && restOfName.length() > 0) {
               throw this.newNameNotFoundException("While trying to lookup '" + name + "' didn't find subcontext '" + atom + "'. Resolved '" + this.getNameInNamespace() + "'", this.composeName(restOfName, atom), env);
            } else {
               throw this.newNameNotFoundException("Unable to resolve '" + name + "'. Resolved '" + this.getNameInNamespace() + "'", this.composeName(restOfName, atom), env);
            }
         } else {
            if (!(object instanceof BasicNamingNode)) {
               object = this.getPartitionVisibleObject(atom, object, env);
            }

            return object;
         }
      }
   }

   private Object lookupHereOrCreate(String atom, Hashtable env, String restOfName) throws NamingException {
      try {
         return this.lookupHere(atom, env, restOfName);
      } catch (NameNotFoundException var5) {
         if ("true".equals(this.getProperty(env, "weblogic.jndi.createIntermediateContexts"))) {
            return this.createSubnodeHere(atom, env);
         } else {
            throw var5;
         }
      }
   }

   public Object lookupLink(String name, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      Object object = this.lookupHere(prefix, env, restOfName);
      if (restOfName.length() == 0) {
         return this.resolveObject(prefix, object, 0, env);
      } else {
         try {
            if (object instanceof NamingNode) {
               object = ((NamingNode)object).lookupLink(restOfName, env);
            } else {
               object = this.getContinuationCtx(object, prefix, restOfName, env).lookupLink(restOfName);
            }

            return object;
         } catch (NamingException var7) {
            throw this.prependResolvedNameToException(prefix, var7);
         }
      }
   }

   public void bind(String name, Object newObject, Hashtable env) throws NamingException, RemoteException {
      if (newObject == null) {
         throw new NamingException("Cannot bind null object to jndi with name " + name);
      } else {
         String prefix = this.getPrefix(name);
         String restOfName = this.getRest(name);
         if (restOfName.length() == 0) {
            this.bindHere(prefix, newObject, env, true, (Object)null);
         } else {
            Object bound = this.lookupHereOrCreate(prefix, env, restOfName);

            try {
               if (bound instanceof NamingNode) {
                  ((NamingNode)bound).bind(restOfName, newObject, env);
               } else {
                  this.getContinuationCtx(bound, prefix, restOfName, env).bind(restOfName, newObject);
               }
            } catch (NamingException var8) {
               throw this.prependResolvedNameToException(prefix, var8);
            }
         }

      }
   }

   protected void bindHere(String atom, Object object, Hashtable env, boolean enableVersion, Object original) throws NamingException {
      Object bound = null;

      try {
         if (!(object instanceof Aggregatable)) {
            synchronized(this.map) {
               bound = this.map.get(atom);
               if (bound == null) {
                  if (object instanceof BasicNamingNode) {
                     ((BasicNamingNode)object).setParent(this, atom);
                  } else {
                     object = this.getObjectOrCreatePartitionVisibleRef(original, env, object);
                  }

                  this.map.put(atom, object);
               } else {
                  if (!(bound instanceof BasicNamingNode)) {
                     bound = this.getPartitionVisibleObject(atom, bound, env);
                  }

                  if (!object.equals(bound)) {
                     throw this.fillInException(new NameAlreadyBoundException(atom + " is already bound"), atom, bound, "");
                  }
               }
            }
         } else {
            synchronized(this.map) {
               bound = this.map.get(atom);
               if (bound == null) {
                  this.onBind((Aggregatable)object, (Aggregatable)null, atom, env);
                  this.map.put(atom, this.getObjectOrCreatePartitionVisibleRef(original, env, object));
               } else {
                  if (!(bound instanceof BasicNamingNode)) {
                     bound = this.getPartitionVisibleObject(atom, bound, env);
                  }

                  if (!(bound instanceof Aggregatable)) {
                     throw this.fillInException(new NameAlreadyBoundException(atom + " is already bound"), atom, bound, "");
                  }

                  this.onBind((Aggregatable)bound, (Aggregatable)object, atom, env);
                  Object newObject = this.getObjectOrCreatePartitionVisibleRef(original, env, bound);
                  if (newObject != bound) {
                     this.map.put(atom, newObject);
                  }
               }
            }
         }

         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ bind(" + this.getNameInNamespace(atom) + ", " + object.getClass().getName() + ") succeeded");
         }
      } catch (NamingException var12) {
         NamingException e = this.fillInException(var12, atom, this, this.getNameInNamespace());
         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ bind(" + atom + ", " + object.getClass().getName() + ") failed due to: " + e);
         }

         throw e;
      }

      if (this.notifyNameListeners()) {
         this.fireNameListeners(atom, this.setUpNotification(atom, 0, env, object, bound));
      }

   }

   protected void onBind(Aggregatable bound, Aggregatable object, String atom, Hashtable env) throws NamingException {
      bound.onBind(this, atom, object);
   }

   public void rebind(String name, Object newObject, Hashtable env) throws NamingException, RemoteException {
      if (newObject == null) {
         throw new NamingException("Cannot rebind null object into jndi tree " + name);
      } else {
         String prefix = this.getPrefix(name);
         String restOfName = this.getRest(name);
         if (restOfName.length() == 0) {
            this.rebindHere(prefix, newObject, env, true, (Object)null);
         } else {
            Object bound = this.lookupHereOrCreate(prefix, env, restOfName);

            try {
               if (bound instanceof NamingNode) {
                  ((NamingNode)bound).rebind(restOfName, newObject, env);
               } else {
                  this.getContinuationCtx(bound, prefix, restOfName, env).rebind(restOfName, newObject);
               }
            } catch (NamingException var8) {
               throw this.prependResolvedNameToException(prefix, var8);
            }
         }

      }
   }

   public void rebind(Name name, Object newObject, Hashtable env) throws NamingException, RemoteException {
      int size = name.size();
      if (size != 0) {
         String prefix;
         if (size == 1) {
            prefix = name.get(0);
            this.rebindHere(prefix, newObject, env, true, (Object)null);
         } else {
            prefix = name.get(0);
            name.remove(0);
            Object bound = this.lookupHereOrCreate(prefix, env, name.toString());

            try {
               if (bound instanceof NamingNode) {
                  ((NamingNode)bound).rebind(name, newObject, env);
               } else {
                  this.getContinuationCtx(bound, prefix, name.toString(), env).rebind(name, newObject);
               }
            } catch (NamingException var8) {
               throw this.prependResolvedNameToException(prefix, var8);
            }
         }

      }
   }

   public void rebind(String name, Object oldObject, Object newObject, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (restOfName.length() == 0) {
         this.rebindHere(prefix, oldObject, newObject, env, true);
      } else {
         Object bound = this.lookupHereOrCreate(prefix, env, restOfName);

         try {
            if (bound instanceof NamingNode) {
               ((NamingNode)bound).rebind(restOfName, oldObject, newObject, env);
            } else {
               ((WLContext)this.getContinuationCtx(bound, prefix, restOfName, env)).rebind(restOfName, oldObject, newObject);
            }
         } catch (NamingException var9) {
            throw this.prependResolvedNameToException(prefix, var9);
         }
      }

   }

   protected void rebindHere(String atom, Object OldObject, Object newObject, Hashtable env, boolean enableVersion) throws NamingException {
      this.rebindHere(atom, newObject, env, enableVersion, OldObject);
   }

   protected void rebindHere(String atom, Object object, Hashtable env, boolean enableVersion, Object original) throws NamingException {
      Object bound = null;

      try {
         if (!(object instanceof Aggregatable)) {
            synchronized(this.map) {
               bound = this.map.get(atom);
               if (bound instanceof BasicNamingNode) {
                  if (((BasicNamingNode)bound).isVersioned()) {
                     throw this.fillInException(new NamingException(atom + " was bound with version previously.  Cannot rebind without version."), atom, bound, "");
                  }
               } else {
                  bound = this.getPartitionVisibleObject(atom, bound, env);
               }

               if (bound != object && bound instanceof Remote) {
                  this.unexportRemoteObject(atom, (Remote)bound, false);
               }

               if (object instanceof BasicNamingNode) {
                  ((BasicNamingNode)object).setParent(this, atom);
               } else {
                  object = this.getObjectOrCreatePartitionVisibleRef(original, env, object);
               }

               this.map.put(atom, object);
            }
         } else {
            synchronized(this.map) {
               bound = this.map.get(atom);
               if (bound instanceof BasicNamingNode) {
                  if (((BasicNamingNode)bound).isVersioned()) {
                     throw this.fillInException(new NamingException(atom + " was bound with version previously.  Cannot rebind without version."), atom, bound, "");
                  }
               } else {
                  bound = this.getPartitionVisibleObject(atom, bound, env);
               }

               if (bound == null) {
                  ((Aggregatable)object).onBind(this, atom, (Aggregatable)null);
                  this.map.put(atom, this.getObjectOrCreatePartitionVisibleRef(original, env, object));
               } else if (bound instanceof Aggregatable) {
                  ((Aggregatable)bound).onRebind(this, atom, (Aggregatable)object);
                  Object newObject = this.getObjectOrCreatePartitionVisibleRef(original, env, bound);
                  if (newObject != bound) {
                     this.map.put(atom, newObject);
                  }
               }
            }
         }

         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ rebind(" + atom + ", " + object.getClass().getName() + ") succeeded");
         }
      } catch (NamingException var12) {
         NamingException e = this.fillInException(var12, atom, this, "");
         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ rebind(" + atom + ", " + object.getClass().getName() + ") failed due to: " + e);
         }

         throw e;
      }

      if (this.notifyNameListeners()) {
         this.fireNameListeners(atom, this.setUpNotification(atom, 3, env, object, bound));
      }

   }

   public void unbind(String name, Hashtable env) throws NamingException, RemoteException {
      this.unbind(name, (Object)null, env);
   }

   public void unbind(String name, Object object, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (restOfName.length() == 0) {
         this.unbindHere(prefix, object, env, true);
      } else {
         Object bound;
         try {
            bound = this.lookupHere(prefix, env, restOfName);
         } catch (NameNotFoundException var9) {
            return;
         }

         try {
            if (bound instanceof NamingNode) {
               ((NamingNode)bound).unbind(restOfName, object, env);
            } else {
               this.getContinuationCtx(bound, prefix, restOfName, env).unbind(restOfName);
            }
         } catch (NamingException var8) {
            throw this.prependResolvedNameToException(prefix, var8);
         }
      }

   }

   protected Object unbindHere(String atom, Object object, Hashtable env, boolean enableVersion) throws NamingException {
      Object bound;
      synchronized(this.map) {
         bound = this.map.get(atom);
         if (!(bound instanceof BasicNamingNode)) {
            bound = this.getPartitionVisibleObject(atom, bound, env);
         }

         if (bound instanceof Aggregatable) {
            if ((object == null || object instanceof Aggregatable) && ((Aggregatable)bound).onUnbind(this, atom, (Aggregatable)object)) {
               this.map.remove(atom);
               this.notifyObjectRemovedListeners(atom, env, object, bound);
            }
         } else {
            if (bound instanceof Remote) {
               this.unexportRemoteObject(atom, (Remote)bound, false);
            }

            this.map.remove(atom);
            this.notifyObjectRemovedListeners(atom, env, object, bound);
         }
      }

      if (NamingDebugLogger.isDebugEnabled()) {
         NamingDebugLogger.debug("+++ unbind(" + atom + ") succeeded ");
      }

      return bound;
   }

   void notifyObjectRemovedListeners(String atom, Hashtable env, Object object, Object bound) throws NamingException {
      if (this.notifyNameListeners()) {
         this.fireNameListeners(atom, this.setUpNotification(atom, 1, env, object, bound));
      }

   }

   public void rename(String oldName, String newName, Hashtable env) throws NamingException, RemoteException {
      Object object = this.lookupLink(oldName, env);
      this.bind(newName, object, env);
      this.unbind(oldName, env);
   }

   public NamingEnumeration list(String name, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (prefix.length() == 0) {
         Collection entries = this.listThis(env);
         Iterator iter = entries.iterator();
         NameClassPair[] pairs = new NameClassPair[entries.size()];

         for(int i = 0; i < pairs.length; ++i) {
            Map.Entry entry = (Map.Entry)iter.next();
            String bindingName = (String)entry.getKey();
            Object value = entry.getValue();
            if (!(value instanceof BasicNamingNode)) {
               value = this.getPartitionVisibleObject(name, value, env);
            }

            String className = value.getClass().getName();
            pairs[i] = new NameClassPair(bindingName, className);
         }

         return new NameClassPairEnumeration(pairs);
      } else {
         Object object = this.lookupHere(prefix, env, restOfName);

         NamingEnumeration nameEnum;
         try {
            if (object instanceof NamingNode) {
               nameEnum = ((NamingNode)object).list(restOfName, env);
            } else {
               nameEnum = this.getContinuationCtx(object, prefix, restOfName, env).list(restOfName);
            }
         } catch (NamingException var13) {
            throw this.prependResolvedNameToException(prefix, var13);
         }

         return (NamingEnumeration)this.makeTransportable(nameEnum, (String)restOfName, env);
      }
   }

   protected Collection listThis(Hashtable env) throws NamingException {
      Collection list = new LinkedList();
      Iterator var3 = this.map.entrySet().iterator();

      while(true) {
         Object entry;
         Object value;
         do {
            if (!var3.hasNext()) {
               return list;
            }

            entry = var3.next();
            value = ((Map.Entry)entry).getValue();
         } while(value instanceof BasicNamingNode && ((BasicNamingNode)value).nonListable);

         Class clazz = value.getClass();
         if (clazz != AuthenticatedNamingNode.class && clazz != NonListableRef.class) {
            list.add(entry);
         }
      }
   }

   public NamingEnumeration listBindings(String name, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (prefix.length() == 0) {
         Collection entries = this.listThis(env);
         Iterator iter = entries.iterator();
         Binding[] b = new Binding[entries.size()];

         for(int i = 0; i < b.length; ++i) {
            Map.Entry entry = (Map.Entry)iter.next();
            String bindingName = (String)entry.getKey();
            Object obj = entry.getValue();
            if (!(obj instanceof BasicNamingNode)) {
               obj = this.getPartitionVisibleObject(name, obj, env);
            }

            try {
               obj = this.resolveObject(bindingName, obj, env);
            } catch (LinkException var13) {
            } catch (NameNotFoundException var14) {
            }

            b[i] = new LazyBinding(bindingName, obj);
         }

         return new BindingEnumeration(b);
      } else {
         Object object = this.lookupHere(prefix, env, restOfName);

         NamingEnumeration nameEnum;
         try {
            if (object instanceof NamingNode) {
               nameEnum = ((NamingNode)object).listBindings(restOfName, env);
            } else {
               nameEnum = this.getContinuationCtx(object, prefix, restOfName, env).listBindings(restOfName);
            }
         } catch (NamingException var15) {
            throw this.prependResolvedNameToException(prefix, var15);
         }

         return (NamingEnumeration)this.makeTransportable(nameEnum, (String)restOfName, env);
      }
   }

   public Context createSubcontext(String name, Hashtable env) throws NamingException, RemoteException {
      if (this.separatorsLength == 0) {
         NamingException ne = new OperationNotSupportedException("Cannot call createSubcontext is a flat namespace");
         throw this.fillInException(ne, name, (Object)null, (String)null);
      } else {
         String prefix = this.getPrefix(name);
         String restOfName = this.getRest(name);
         if (restOfName.length() == 0) {
            return this.createSubnodeHere(prefix, env).getContext(env);
         } else {
            Object bound = this.lookupHereOrCreate(prefix, env, restOfName);

            try {
               return bound instanceof NamingNode ? ((NamingNode)bound).createSubcontext(restOfName, env) : this.getContinuationCtx(bound, prefix, restOfName, env).createSubcontext(restOfName);
            } catch (NamingException var7) {
               throw this.prependResolvedNameToException(prefix, var7);
            }
         }
      }
   }

   protected NamingNode createSubnodeHere(String atom, Hashtable env) throws NamingException {
      synchronized(this.map) {
         Object bound = this.map.get(atom);
         if (bound == null) {
            if (atom.length() == 0) {
               return this;
            } else {
               BasicNamingNode subnode = this.newSubnode(atom);
               if (Boolean.parseBoolean(this.getProperty(env, "weblogic.jndi.createNonListableNode"))) {
                  subnode.nonListable = true;
               }

               if (NamingResolutionDebugLogger.isDebugEnabled()) {
                  NamingResolutionDebugLogger.debug("--- created sub node " + atom + " " + subnode);
               }

               this.map.put(atom, subnode);
               if (this.notifyNameListeners()) {
                  this.fireNameListeners(atom, this.setUpNotification(atom, 0, env, subnode.getContext(env), (Object)null));
               }

               return subnode;
            }
         } else if (bound instanceof NamingNode) {
            return (NamingNode)bound;
         } else {
            throw this.fillInException(new NameAlreadyBoundException(atom), atom, (Object)null, "");
         }
      }
   }

   protected BasicNamingNode newSubnode(String relativeName) {
      return new BasicNamingNode(this.separators, this, relativeName);
   }

   public void destroySubcontext(String name, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (restOfName.length() == 0) {
         this.destroySubnodeHere(name, env);
      } else {
         Object bound = this.map.get(prefix);
         if (bound == null) {
            throw this.newNameNotFoundException("Cannot destroy non exisiting context " + prefix + " in " + this.getNameInNamespace(prefix), restOfName, env);
         }

         try {
            if (bound instanceof NamingNode) {
               ((NamingNode)bound).destroySubcontext(restOfName, env);
            } else {
               this.getContinuationCtx(bound, prefix, restOfName, env).destroySubcontext(restOfName);
            }
         } catch (NamingException var7) {
            throw this.prependResolvedNameToException(prefix, var7);
         }
      }

   }

   protected void destroySubnodeHere(String atom, Hashtable env) throws NameNotFoundException, ContextNotEmptyException, NamingException {
      Object bound = this.map.get(atom);
      if (bound != null) {
         if (!(bound instanceof BasicNamingNode)) {
            throw this.newNameNotFoundException("Cannot destroy non existing subcontext " + atom + " in " + this.getNameInNamespace(atom), "", env);
         } else {
            BasicNamingNode subnode = (BasicNamingNode)bound;
            if (subnode.map.size() > 0) {
               if (NamingDebugLogger.isDebugEnabled()) {
                  throw this.fillInException(new ContextNotEmptyException("Node is not empty : " + this.getNameInNamespace(atom) + ", bound objects : " + this.getBoundObjects()), atom, bound, "");
               } else {
                  throw this.fillInException(new ContextNotEmptyException(), atom, bound, "");
               }
            } else {
               this.unexportRemoteObject(atom, (Remote)bound, true);
               this.map.remove(atom);
            }
         }
      }
   }

   public Context getContext(Hashtable env) {
      return new WLEventContextImpl(env, this);
   }

   private EventContext getEventContext(Hashtable env) {
      return new WLEventContextImpl(env, this);
   }

   private Object resolveObject(String name, Object obj, Hashtable env) throws NamingException {
      return this.resolveObject(name, obj, 1, env);
   }

   protected Object resolveObject(String name, Object obj, int mode, Hashtable env) throws NamingException {
      Object resolved = obj;
      if (obj != null) {
         try {
            if (obj instanceof NamingNode) {
               resolved = ((NamingNode)obj).getContext(env);
            } else if (mode != 0 && mode >= 0) {
               resolved = WLNamingManager.getObjectInstance(obj, new CompositeName(name), (Context)null, env);
               resolved = this.makeTransportable(resolved, name, env);
            }
         } catch (NamingException var8) {
            NamingService ns = NamingService.getNamingService();
            if (ns != null && !ns.isRunning()) {
               var8.setRootCause(new ConnectIOException("Server is being shut down"));
            }

            throw var8;
         } catch (Exception var9) {
            NamingException ne = this.fillInException(new ConfigurationException("Call to NamingManager.getObjectInstance() failed: "), name, obj, (String)null);
            ne.setRootCause(var9);
            throw ne;
         }
      }

      return resolved;
   }

   private String getPrefix(String name) throws NamingException {
      int nameLength = name.length();
      if (nameLength == 0) {
         return name;
      } else {
         char firstChar = name.charAt(0);
         int idx;
         if (firstChar == '"') {
            idx = name.indexOf(34, 1);
            this.checkQuoteClose(idx, name);
            return name.substring(1, idx);
         } else if (firstChar == '\'') {
            idx = name.indexOf(39, 1);
            this.checkQuoteClose(idx, name);
            return name.substring(1, idx);
         } else {
            StringBuilder prefix = new StringBuilder();

            for(int i = 0; i < nameLength; ++i) {
               char currentChar = name.charAt(i);
               switch (currentChar) {
                  case '"':
                  case '\'':
                     throw new InvalidNameException("Unescaped quote in a component");
                  case '\\':
                     ++i;
                     if (i == nameLength) {
                        throw new InvalidNameException("An escape at the end of a name must be escaped");
                     }

                     prefix.append(name.charAt(i));
                     break;
                  default:
                     if (this.isSeparator(currentChar)) {
                        return prefix.toString();
                     }

                     prefix.append(currentChar);
               }
            }

            return prefix.toString();
         }
      }
   }

   private boolean isSeparator(char c) {
      for(int i = 0; i < this.separatorsLength; ++i) {
         if (c == this.separators[i]) {
            return true;
         }
      }

      return false;
   }

   private String getRest(String name) throws NamingException {
      int nameLength = name.length();
      if (nameLength == 0) {
         return name;
      } else {
         char firstChar = name.charAt(0);
         int i;
         if (firstChar == '"') {
            i = name.indexOf(34, 1);
            this.checkQuoteClose(i, name);
            ++i;
            if (i < nameLength && this.isSeparator(name.charAt(i))) {
               ++i;
            }

            return name.substring(i);
         } else if (firstChar == '\'') {
            i = name.indexOf(39, 1);
            this.checkQuoteClose(i, name);
            ++i;
            if (i < nameLength && this.isSeparator(name.charAt(i))) {
               ++i;
            }

            return name.substring(i);
         } else {
            for(i = 0; i < nameLength; ++i) {
               char currentChar = name.charAt(i);
               switch (currentChar) {
                  case '"':
                  case '\'':
                     throw new InvalidNameException("Unescaped quote in a component");
                  case '\\':
                     ++i;
                     if (i == nameLength) {
                        throw new InvalidNameException("An escape at the end of a name must be escaped");
                     }
                     break;
                  default:
                     if (this.isSeparator(currentChar)) {
                        return name.substring(i + 1);
                     }
               }
            }

            return "";
         }
      }
   }

   private void checkQuoteClose(int idx, String name) throws NamingException {
      if (idx < 0) {
         throw new InvalidNameException("No closing quote");
      } else if (idx < name.length() - 1 && !this.isSeparator(name.charAt(idx + 1))) {
         throw new InvalidNameException("Closing quote must be at component end");
      }
   }

   protected String escapeBinding(String n) {
      int length = n.length();
      StringBuilder s = new StringBuilder(length + 2);
      boolean changed = false;

      for(int j = 0; j < length; ++j) {
         char c = n.charAt(j);
         if (j != 0 && j != length - 1 && (c == '"' || c == '\'') || this.isSeparator(c)) {
            s.append('\\');
            changed = true;
         }

         s.append(c);
      }

      if (changed) {
         return s.toString();
      } else {
         return n;
      }
   }

   protected final Object makeTransportable(Object boundObject, String name, Hashtable env) throws NamingException {
      return this.makeTransportable(boundObject, (Name)(new CompositeName(name)), env);
   }

   protected final Object makeTransportable(Object boundObject, Name name, Hashtable env) throws NamingException {
      return WLNamingManager.getTransportableInstance(boundObject, name, (Context)null, env);
   }

   protected final NamingException fillInException(NamingException ne, String resolvedName, Object resolvedObj, String remainingName) {
      try {
         if (remainingName == null) {
            remainingName = "";
         } else {
            remainingName = remainingName.replace('.', '/');
         }

         ne.setResolvedName(WLNameParser.defaultParse(resolvedName));
         ne.setResolvedObj(resolvedObj);
         ne.setRemainingName(new CompositeName(remainingName));
         NamingService ns = NamingService.getNamingService();
         if (ns != null && !ns.isRunning()) {
            ne.setRootCause(new ConnectIOException("Server is being shut down"));
         }

         return ne;
      } catch (NamingException var6) {
         throw new AssertionError(var6);
      }
   }

   protected final NameNotFoundException newNameNotFoundException(String message, String restOfName, Hashtable env) {
      return this.newNameNotFoundException((NamingException)(new NameNotFoundException(message)), restOfName, env);
   }

   public NameNotFoundException newNameNotFoundException(NamingException ne, String restOfName, Hashtable env) {
      NamingService ns = NamingService.getNamingService();
      return ns != null && ns.isRunning() ? (NameNotFoundException)this.fillInException(ne, "", this.getContextForException(env), restOfName) : (NameNotFoundException)this.fillInException(ne, "", this, restOfName);
   }

   protected Context getContextForException(Hashtable env) {
      return this.getContext(env);
   }

   private Context getContinuationCtx(Object resolvedObj, String resolvedName, String remainingName, Hashtable env) throws NamingException {
      CannotProceedException cpe = new CannotProceedException();
      Hashtable e = (Hashtable)env.clone();
      if (e.get("java.naming.factory.url.pkgs") == null) {
         e.put("java.naming.factory.url.pkgs", NamingService.getNamingService().getUrlPkgPrefixes());
      }

      cpe.setEnvironment(e);
      this.fillInException(cpe, resolvedName, resolvedObj, remainingName);
      return (Context)(this instanceof DirContext ? DirectoryManager.getContinuationDirContext(cpe) : NamingManager.getContinuationContext(cpe));
   }

   private NamingException prependResolvedNameToException(String resolvedName, NamingException ne) {
      try {
         Name newName = ne.getResolvedName();
         if (newName == null) {
            try {
               newName = WLNameParser.defaultParse("");
            } catch (NamingException var5) {
               throw new AssertionError(var5);
            }
         } else {
            newName.add(0, resolvedName);
         }

         ne.setResolvedName(newName);
         return ne;
      } catch (InvalidNameException var6) {
         throw new AssertionError(var6);
      }
   }

   protected final String getProperty(Hashtable env, String property) {
      String value = null;
      if (env != null) {
         value = (String)env.get(property);
      }

      if (value == null) {
         value = System.getProperty(property);
      }

      return value == null ? "" : value;
   }

   protected int getNumOfBindings() {
      return this.map.size();
   }

   protected void getBoundObjects(BasicNamingNode node, StringBuilder sb) {
      Iterator i = node.map.keySet().iterator();

      while(i.hasNext()) {
         String keyName = (String)i.next();
         sb.append(keyName);
         Object value = node.map.get(keyName);
         if (value instanceof BasicNamingNode) {
            sb.append("(");
            this.getBoundObjects((BasicNamingNode)value, sb);
            sb.append(")");
         }

         if (i.hasNext()) {
            sb.append(",");
         }
      }

   }

   protected String getBoundObjects() {
      StringBuilder sb = new StringBuilder();
      this.getBoundObjects(this, sb);
      return sb.toString();
   }

   protected boolean isVersioned() {
      return false;
   }

   public void addNamingListener(String name, int scope, NamingListener l, Hashtable env) throws NamingException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (restOfName.length() == 0) {
         if (scope == 1) {
            NamingNode node = (NamingNode)this.lookupHereOrCreate(prefix, env, restOfName);
            node.addOneLevelScopeNamingListener(l);
         } else {
            this.addListener(prefix, scope, l);
         }

         if (NamingResolutionDebugLogger.isDebugEnabled()) {
            Name fullName = this.nameParser.parse(this.getNameInNamespace(name));
            NamingResolutionDebugLogger.debug("+++ Added listener of scope " + scope + " at " + fullName);
         }
      } else {
         Object bound = this.lookupHereOrCreate(prefix, env, restOfName);
         if (!(bound instanceof NamingNode)) {
            throw new AssertionError("Tried to create context but failed" + bound.toString());
         }

         ((NamingNode)bound).addNamingListener(restOfName, scope, l, env);
      }

   }

   private synchronized void addListener(String prefix, int scope, NamingListener l) {
      if (scope == 0) {
         CopyOnWriteArrayList list = (CopyOnWriteArrayList)this.objectListenerMap.get(prefix);
         if (list == null) {
            synchronized(this.objectListenerMap) {
               list = (CopyOnWriteArrayList)this.objectListenerMap.get(prefix);
               if (list == null) {
                  list = new CopyOnWriteArrayList();
                  this.objectListenerMap.put(prefix, list);
               }
            }
         }

         list.add(l);
      } else if (scope == 2) {
         this.subtreeScopeNameListenerList.add(l);
      }

   }

   public void addOneLevelScopeNamingListener(NamingListener l) {
      this.onelevelNameListenerList.add(l);
   }

   public void removeNamingListener(NamingListener l, Hashtable env) throws NamingException {
      this.onelevelNameListenerList.remove(l);
      this.subtreeScopeNameListenerList.remove(l);
      Iterator var3 = this.objectListenerMap.keySet().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         CopyOnWriteArrayList list = (CopyOnWriteArrayList)this.objectListenerMap.get(key);
         if (list.remove(l)) {
            break;
         }
      }

      if (NamingResolutionDebugLogger.isDebugEnabled()) {
         NamingResolutionDebugLogger.debug("--- Removed listener " + l + " from current context ");
      }

   }

   public List getOneLevelScopeNamingListeners() {
      return this.onelevelNameListenerList;
   }

   protected boolean notifyNameListeners() {
      return this.getOneLevelScopeNamingListeners().size() > 0 || this.objectListenerMap.size() > 0 || this.subtreeScopeNameListenerList.size() > 0;
   }

   protected void fireNameListeners(String name, NamingEvent event) {
      ArrayList listenerList = new ArrayList();
      CopyOnWriteArrayList list = (CopyOnWriteArrayList)this.objectListenerMap.get(name);
      if (list != null) {
         listenerList.addAll(list);
      }

      if (this.getOneLevelScopeNamingListeners().size() > 0) {
         listenerList.addAll(0, this.getOneLevelScopeNamingListeners());
      }

      if (this.subtreeScopeNameListenerList.size() > 0) {
         listenerList.addAll(0, this.subtreeScopeNameListenerList);
      }

      final NotifyEventListeners listeners = new NotifyEventListeners(listenerList, event, event.getType());
      if (KernelStatus.isServer()) {
         Runnable work = new Runnable() {
            public void run() {
               listeners.notifyListeners();
            }
         };
         WorkManagerFactory.getInstance().getSystem().schedule(work);
      } else {
         listeners.notifyListeners();
      }

   }

   private NamingEvent setUpNotification(String atom, int event, Hashtable env, Object object, Object bound) throws NamingException {
      String name = this.nameParser.parse(this.getNameInNamespace(atom)).toString();
      return new NamingEvent(this.getEventContext(env), event, new Binding(name, object), new Binding(name, bound), (Object)null);
   }

   protected boolean isUnbound(String atom) {
      return this.map.get(atom) == null;
   }

   protected Object getBound(String atom) {
      return this.map.get(atom);
   }

   private void unexportRemoteObject(String atom, Remote bound, boolean force) {
      try {
         boolean result = ServerHelper.unexportObject(bound, force);
         if (!result && NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("unexportObject failed for " + atom);
         }
      } catch (NoSuchObjectException var5) {
         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("No such object exception when trying to destroy a bound object. Possibly a race condition between two threads both unexporting the object. This should not cause any errors in and of itself, however. Both threads have achieved their goal. Exception: " + var5);
         }
      }

   }

   protected Object lookupSharable(String name, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      Object object = this.lookupHere(prefix, env, restOfName);
      if (restOfName.length() == 0) {
         if (NamingDebugLogger.isDebugEnabled()) {
            NamingDebugLogger.debug("+++ lookupInSharable(" + name + ", " + object.getClass().getName() + ") succeeded");
         }

         return this.resolveObject(prefix, object, env);
      } else {
         try {
            if (object instanceof BasicNamingNode) {
               object = ((BasicNamingNode)object).lookupSharable(restOfName, env);
            } else {
               object = this.getContinuationCtx(object, prefix, restOfName, env).lookup(restOfName);
            }
         } catch (NamingException var7) {
            throw this.prependResolvedNameToException(prefix, var7);
         }

         return this.makeTransportable(object, restOfName, env);
      }
   }

   protected Object lookupLinkSharable(String name, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      Object object = this.lookupHere(prefix, env, restOfName);
      if (restOfName.length() == 0) {
         return this.resolveObject(prefix, object, 0, env);
      } else {
         try {
            if (object instanceof NamingNode) {
               object = ((BasicNamingNode)object).lookupLinkSharable(restOfName, env);
            } else {
               object = this.getContinuationCtx(object, prefix, restOfName, env).lookupLink(restOfName);
            }

            return object;
         } catch (NamingException var7) {
            throw this.prependResolvedNameToException(prefix, var7);
         }
      }
   }

   protected NamingEnumeration listSharable(String name, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (prefix.length() == 0) {
         Collection entries = this.listThis(env);
         Iterator iter = entries.iterator();
         NameClassPair[] pairs = new NameClassPair[entries.size()];

         for(int i = 0; i < pairs.length; ++i) {
            Map.Entry entry = (Map.Entry)iter.next();
            String bindingName = (String)entry.getKey();
            Object value = entry.getValue();
            if (!(value instanceof BasicNamingNode)) {
               value = this.getPartitionVisibleObject(name, value, env);
            }

            String className = value.getClass().getName();
            pairs[i] = new NameClassPair(bindingName, className);
         }

         return new NameClassPairEnumeration(pairs);
      } else {
         Object object = this.lookupHere(prefix, env, restOfName);

         NamingEnumeration nameEnum;
         try {
            if (object instanceof NamingNode) {
               nameEnum = ((BasicNamingNode)object).listSharable(restOfName, env);
            } else {
               nameEnum = this.getContinuationCtx(object, prefix, restOfName, env).list(restOfName);
            }
         } catch (NamingException var13) {
            throw this.prependResolvedNameToException(prefix, var13);
         }

         return (NamingEnumeration)this.makeTransportable(nameEnum, (String)restOfName, env);
      }
   }

   protected NamingEnumeration listBindingsSharable(String name, Hashtable env) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (prefix.length() == 0) {
         Collection entries = this.listThis(env);
         Iterator iter = entries.iterator();
         Binding[] b = new Binding[entries.size()];

         for(int i = 0; i < b.length; ++i) {
            Map.Entry entry = (Map.Entry)iter.next();
            String bindingName = (String)entry.getKey();
            Object obj = entry.getValue();
            if (!(obj instanceof BasicNamingNode)) {
               obj = this.getPartitionVisibleObject(name, obj, env);
            }

            try {
               obj = this.resolveObject(bindingName, obj, env);
            } catch (LinkException var13) {
            } catch (NameNotFoundException var14) {
            }

            b[i] = new LazyBinding(bindingName, obj);
         }

         return new BindingEnumeration(b);
      } else {
         Object object = this.lookupHere(prefix, env, restOfName);

         NamingEnumeration nameEnum;
         try {
            if (object instanceof NamingNode) {
               nameEnum = ((BasicNamingNode)object).listBindingsSharable(restOfName, env);
            } else {
               nameEnum = this.getContinuationCtx(object, prefix, restOfName, env).listBindings(restOfName);
            }
         } catch (NamingException var15) {
            throw this.prependResolvedNameToException(prefix, var15);
         }

         return (NamingEnumeration)this.makeTransportable(nameEnum, (String)restOfName, env);
      }
   }

   protected void cascadeDestroySubNode() {
      this.parent = null;

      try {
         ServerHelper.unexportObject(this, true);
      } catch (NoSuchObjectException var4) {
      }

      if (!this.map.isEmpty()) {
         Iterator var1 = this.map.values().iterator();

         while(true) {
            while(var1.hasNext()) {
               Object o = var1.next();
               if (o instanceof BasicNamingNode) {
                  ((BasicNamingNode)o).cascadeDestroySubNode();
               } else if (!(o instanceof Aggregatable) && o instanceof Remote) {
                  try {
                     ServerHelper.unexportObject((Remote)o, true);
                  } catch (NoSuchObjectException var5) {
                     if (NamingDebugLogger.isDebugEnabled()) {
                        NamingDebugLogger.debug("No such object exception when trying to destroy a bound object. Exception: " + var5);
                     }
                  }
               }
            }

            this.map.clear();
            this.onelevelNameListenerList.clear();
            this.objectListenerMap.clear();
            if (this.subtreeScopeNameListenerList != null) {
               this.subtreeScopeNameListenerList.clear();
            }
            break;
         }
      }

   }

   protected Context createSubcontext(String name, Hashtable env, String separators) throws NamingException, RemoteException {
      String prefix = this.getPrefix(name);
      String restOfName = this.getRest(name);
      if (restOfName.length() == 0) {
         return this.createSubnodeHere(prefix, env, separators).getContext(env);
      } else {
         Object bound = this.lookupHereOrCreate(prefix, env, restOfName);

         try {
            return bound instanceof BasicNamingNode ? ((BasicNamingNode)bound).createSubcontext(restOfName, env, separators) : this.getContinuationCtx(bound, prefix, restOfName, env).createSubcontext(restOfName);
         } catch (NamingException var8) {
            throw this.prependResolvedNameToException(prefix, var8);
         }
      }
   }

   protected NamingNode createSubnodeHere(String atom, Hashtable env, String separators) throws NamingException {
      Object bound = this.map.get(atom);
      if (bound == null) {
         if (atom.length() == 0) {
            return this;
         }

         synchronized(this.map) {
            bound = this.map.get(atom);
            if (bound == null) {
               BasicNamingNode subnode = this.newSubnode(atom, separators);
               if (NamingResolutionDebugLogger.isDebugEnabled()) {
                  NamingResolutionDebugLogger.debug("--- created sub node " + atom + " " + subnode);
               }

               subnode.setParent(this, atom);
               this.map.put(atom, subnode);
               if (this.notifyNameListeners()) {
                  this.fireNameListeners(atom, this.setUpNotification(atom, 0, env, subnode.getContext(env), (Object)null));
               }

               return subnode;
            }
         }
      }

      if (bound instanceof NamingNode) {
         return (NamingNode)bound;
      } else {
         throw this.fillInException(new NameAlreadyBoundException(atom), atom, (Object)null, "");
      }
   }

   protected BasicNamingNode newSubnode(String relativeName, String s) {
      return new BasicNamingNode(s.toCharArray(), (BasicNamingNode)null, relativeName);
   }

   protected Object getPartitionVisibleObject(String jndiName, Object bound, Hashtable env) throws NamingException {
      if (bound instanceof PartitionVisibleRef) {
         bound = ((PartitionVisibleRef)bound).getReferent();
      }

      return bound;
   }

   protected PartitionVisibleRef.CPAwareSource isCrossPartitionAware(Object obj, Hashtable env) {
      return PartitionVisibleRef.CPAwareSource.NONE;
   }

   private Object getObjectOrCreatePartitionVisibleRef(Object original, Hashtable env, Object object) {
      PartitionVisibleRef.CPAwareSource source = this.isCrossPartitionAware(original, env);
      if (source != PartitionVisibleRef.CPAwareSource.NONE) {
         object = new PartitionVisibleRef(original, object, source);
      }

      return object;
   }
}

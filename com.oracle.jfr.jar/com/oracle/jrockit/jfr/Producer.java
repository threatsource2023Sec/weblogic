package com.oracle.jrockit.jfr;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import oracle.jrockit.jfr.JFR;
import oracle.jrockit.jfr.StringConstantPool;
import oracle.jrockit.jfr.events.DynamicValueDescriptor;
import oracle.jrockit.jfr.events.EventHandler;
import oracle.jrockit.jfr.events.JavaEventDescriptor;
import oracle.jrockit.jfr.events.ValueDescriptor;

public final class Producer {
   private final JFR jfr;
   private final String name;
   private final String description;
   private final URI uri;
   private final List tokens;
   private final List events;
   private int id;
   private boolean unregistered;
   private boolean enabled;
   private final HashMap constantPools;
   private static final Map globalTokens = new ConcurrentHashMap();
   private static final Object globalTokenLock = new Object();
   private static final HashSet knownURIs = new HashSet();

   static EventToken locateToken(Class type) {
      EventToken t = (EventToken)globalTokens.get(type);
      if (t == null) {
         throw new IllegalArgumentException(type.getName() + " is not registered");
      } else {
         return t;
      }
   }

   public Producer(String name, String description, URI uri) {
      this.tokens = new ArrayList();
      this.events = new ArrayList();
      this.constantPools = new HashMap();
      if (!uri.toString().endsWith("/")) {
         try {
            uri = new URI(uri.toString() + "/");
         } catch (URISyntaxException var5) {
         }
      }

      this.name = name;
      this.description = description;
      this.jfr = JFR.get();
      this.uri = uri;
   }

   public Producer(String name, String description, String uri) throws URISyntaxException {
      this(name, description, new URI(uri));
   }

   public synchronized void register() {
      if (this.id != 0) {
         throw new IllegalStateException("Already registered.");
      } else if (this.unregistered) {
         throw new IllegalStateException("Unregistered producer");
      } else {
         synchronized(globalTokenLock) {
            if (knownURIs.contains(this.uri)) {
               throw new IllegalStateException("Producer with URI " + this.uri + " already exists");
            }

            knownURIs.add(this.uri);
         }

         this.id = this.jfr.nextID();
         this.enable();
      }
   }

   public synchronized void unregister() throws IllegalStateException {
      if (this.id == 0) {
         throw new IllegalStateException("Not registered");
      } else if (this.unregistered) {
         throw new IllegalStateException("Already unregistered");
      } else {
         this.disable();

         try {
            HashSet set = new HashSet(this.events.size());
            set.addAll(this.tokens);
            synchronized(globalTokenLock) {
               knownURIs.remove(this.uri);
               Iterator i = globalTokens.values().iterator();

               while(i.hasNext()) {
                  EventToken t = (EventToken)i.next();
                  if (set.contains(t)) {
                     i.remove();
                  }
               }
            }
         } finally {
            this.unregistered = true;
         }

      }
   }

   public synchronized void enable() {
      if (this.id == 0) {
         throw new IllegalStateException("Not registered");
      } else if (this.unregistered) {
         throw new IllegalStateException("Already unregistered");
      } else if (!this.enabled) {
         Iterator i$ = this.constantPools.values().iterator();

         while(i$.hasNext()) {
            StringConstantPool pool = (StringConstantPool)i$.next();
            pool.enable();
         }

         this.jfr.addProducer(this, this.id, this.events, this.constantPools);
         this.enabled = true;
      }
   }

   public synchronized void disable() {
      if (this.id == 0) {
         throw new IllegalStateException("Not registered");
      } else if (this.unregistered) {
         throw new IllegalStateException("Already unregistered");
      } else if (this.enabled) {
         this.jfr.removeProducer(this.id);
         Iterator i$ = this.constantPools.values().iterator();

         while(i$.hasNext()) {
            StringConstantPool pool = (StringConstantPool)i$.next();
            pool.disable();
         }

         this.enabled = false;
      }
   }

   private void add(EventToken t, EventHandler e) throws InvalidEventDefinitionException {
      String path = t.getPath();
      Iterator i$ = this.tokens.iterator();

      EventToken o;
      do {
         if (!i$.hasNext()) {
            this.events.add(e);
            this.tokens.add(t);
            if (this.enabled) {
               this.jfr.addEventsToRegisteredProducer(this, this.id, Collections.singletonList(e), this.constantPools);
            }

            return;
         }

         o = (EventToken)i$.next();
      } while(!o.getPath().equals(path));

      throw new IllegalArgumentException("Event with path " + path + " already exists in this producer");
   }

   public synchronized EventToken addEvent(Class eventClass) throws InvalidEventDefinitionException, InvalidValueException {
      if (this.getToken(eventClass) != null) {
         throw new IllegalArgumentException("The event class has already been added.");
      } else {
         int id = this.jfr.nextID();
         JavaEventDescriptor d = new JavaEventDescriptor(eventClass, this.uri, id);
         EventHandler e = this.jfr.createHandler(d, eventClass, this.constantPools);
         EventToken t = new EventToken(e);
         this.add(t, e);
         if (id != 0) {
            synchronized(globalTokenLock) {
               if (globalTokens.containsKey(eventClass)) {
                  throw new IllegalStateException("Class " + eventClass + " already registered in another producer");
               }

               globalTokens.put(eventClass, t);
            }
         }

         return t;
      }
   }

   private synchronized DynamicEventToken createDynamicEvent(Class eventClass, String name, String description, String path, boolean addThread, boolean addStacktrace, RequestDelegate delegate, DynamicValue... values) throws InvalidEventDefinitionException {
      DynamicValueDescriptor[] descriptors = new DynamicValueDescriptor[values.length];
      int i = 0;
      DynamicValue[] arr$ = values;
      int len$ = values.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         DynamicValue dv = arr$[i$];
         ValueDescriptor d = dv.getDescriptor();
         descriptors[i] = new DynamicValueDescriptor(d, i);
         ++i;
      }

      int id = this.jfr.nextID();
      JavaEventDescriptor d = new JavaEventDescriptor(eventClass, this.uri, id, name, description, path, addThread, addStacktrace, descriptors);
      EventHandler e = this.jfr.createHandler(d, Object[].class, this.constantPools);
      DynamicEventToken t = new DynamicEventToken(e, delegate);
      this.add(t, e);
      return t;
   }

   public DynamicEventToken createDynamicInstantEvent(String name, String description, String path, boolean addThread, boolean addStacktrace, DynamicValue... values) throws InvalidEventDefinitionException {
      return this.createDynamicEvent(InstantEvent.class, name, description, path, addThread, addStacktrace, (RequestDelegate)null, values);
   }

   public DynamicEventToken createDynamicDurationEvent(String name, String description, String path, boolean addThread, boolean addStacktrace, DynamicValue... values) throws InvalidEventDefinitionException {
      return this.createDynamicEvent(DurationEvent.class, name, description, path, addThread, addStacktrace, (RequestDelegate)null, values);
   }

   public DynamicEventToken createDynamicTimedEvent(String name, String description, String path, boolean addThread, boolean addStacktrace, DynamicValue... values) throws InvalidEventDefinitionException {
      return this.createDynamicEvent(TimedEvent.class, name, description, path, addThread, addStacktrace, (RequestDelegate)null, values);
   }

   public DynamicEventToken createDynamicRequestableEvent(String name, String description, String path, boolean addThread, boolean addStacktrace, RequestDelegate delegate, DynamicValue... values) throws InvalidEventDefinitionException {
      return this.createDynamicEvent(DelgatingDynamicRequestableEvent.class, name, description, path, addThread, addStacktrace, delegate, values);
   }

   private EventToken getToken(Class eventClass) {
      Iterator i$ = this.tokens.iterator();

      EventToken t;
      do {
         if (!i$.hasNext()) {
            return null;
         }

         t = (EventToken)i$.next();
      } while(t.getEventInfo().getDescriptor().getEventClass() != eventClass);

      return t;
   }

   public void createConstantPool(Class type, String name, int initSize, int maxSize, boolean emptyOnRotation) {
      if (type != String.class) {
         throw new IllegalArgumentException("Unsupported constant pool type " + type);
      } else {
         synchronized(this) {
            if (this.constantPools.containsKey(name)) {
               throw new IllegalArgumentException("Duplicate constant pool " + name);
            } else {
               StringConstantPool pool = new StringConstantPool(this.jfr, this.jfr.nextID(), maxSize, emptyOnRotation);
               this.constantPools.put(name, pool);
               if (this.enabled) {
                  pool.enable();
               }

            }
         }
      }
   }

   public synchronized EventToken getEventToken(Class eventClass) {
      EventToken t = this.getToken(eventClass);
      if (t == null) {
         throw new IllegalArgumentException(eventClass + " not registered");
      } else {
         return t;
      }
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public URI getURI() {
      return this.uri;
   }

   public synchronized boolean isRegistered() {
      return this.id != 0 && !this.unregistered;
   }

   public synchronized boolean isUnregistered() {
      return this.unregistered;
   }

   public synchronized boolean isEnabled() {
      return this.enabled;
   }

   protected void finalize() throws Throwable {
      super.finalize();
      synchronized(this) {
         if (this.id != 0 && !this.unregistered) {
            this.unregister();
         }

      }
   }
}

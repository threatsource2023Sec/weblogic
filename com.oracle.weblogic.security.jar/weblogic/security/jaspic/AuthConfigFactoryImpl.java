package weblogic.security.jaspic;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.config.AuthConfigProvider;
import javax.security.auth.message.config.RegistrationListener;

public class AuthConfigFactoryImpl extends AuthConfigFactory {
   private static final String SEPARATOR = "%";
   private Map registrations = new HashMap();
   private Map persistentRegistrations = new HashMap();
   private Map listeners = new HashMap();
   private PersistentRegistrationSource persistentRegSource = null;

   public AuthConfigFactoryImpl() {
   }

   public AuthConfigFactoryImpl(PersistentRegistrationSource regStoreFileParser) {
      if (regStoreFileParser != null) {
         this.persistentRegSource = regStoreFileParser;
         List entries = this.persistentRegSource.getPersistedEntries();
         this.registerPersistentEntries(entries);
      }
   }

   private void registerPersistentEntries(List entries) {
      Iterator var2 = entries.iterator();

      while(var2.hasNext()) {
         EntryInfo entry = (EntryInfo)var2.next();
         String classname = entry.getClassName();
         Map props = entry.getProperties();
         List ctxs = entry.getRegContexts();
         if (ctxs != null) {
            this.registerForEachRegContext(entry, classname, props, ctxs);
         } else if (entry.isConstructorEntry()) {
            this.newProviderInstance(classname, props, this);
         }
      }

   }

   private void registerForEachRegContext(EntryInfo entry, String classname, Map props, List ctxs) {
      Iterator var5 = ctxs.iterator();

      while(var5.hasNext()) {
         AuthConfigFactory.RegistrationContext ctx = (AuthConfigFactory.RegistrationContext)var5.next();
         String appctx = ctx.getAppContext();
         String layer = ctx.getMessageLayer();
         String description = ctx.getDescription();
         this.registerConfigProvider(this.newProviderInstance(classname, props, (AuthConfigFactory)null), layer, appctx, description);
      }

   }

   private AuthConfigProvider newProviderInstance(String className, Map properties, AuthConfigFactory factory) {
      AuthConfigProvider provider = null;
      if (className != null) {
         try {
            Class c = Class.forName(className);
            Constructor constr = c.getConstructor(Map.class, AuthConfigFactory.class);
            provider = (AuthConfigProvider)constr.newInstance(properties, factory);
         } catch (Exception var7) {
            System.out.println("Unable to load provider.");
         }
      }

      return provider;
   }

   public synchronized String registerConfigProvider(AuthConfigProvider provider, String messageLayer, String appContextID, String description) {
      String registrationID = this.getRegistrationID(messageLayer, appContextID);
      this.registrations.put(registrationID, new RegistrationContextImpl(provider, messageLayer, appContextID, description));
      this.notifyListeners(registrationID);
      return registrationID;
   }

   public synchronized String registerConfigProvider(String providerClassName, Map properties, String messageLayer, String appContextID, String description) {
      try {
         String registrationID = this.getRegistrationID(messageLayer, appContextID);
         PersistentRegistration registration = new PersistentRegistration(providerClassName, properties, messageLayer, appContextID, description);
         PersistentRegistration reg = (PersistentRegistration)this.persistentRegistrations.get(registrationID);
         if (registration.equals(reg)) {
            if (this.registrations.get(registrationID) == null) {
               this.registrations.remove(registrationID);
            }

            this.persistentRegistrations.remove(registrationID);
         }

         if (this.persistentRegSource != null) {
            if (reg != null) {
               this.persistentRegSource.delete(reg);
            }

            this.persistentRegSource.store(providerClassName, registration, properties);
         }

         this.persistentRegistrations.put(registrationID, registration);
         this.registrations.put(registrationID, registration);
         RegistrationListener listener = (RegistrationListener)this.listeners.get(registrationID);
         if (listener != null) {
            listener.notify(messageLayer, appContextID);
         }

         return registrationID;
      } catch (AuthException var10) {
         throw new RuntimeException("Unable to register " + providerClassName + ": ", var10);
      }
   }

   public synchronized AuthConfigProvider getConfigProvider(String messageLayer, String appContextID, RegistrationListener listener) {
      String registrationID = this.getRegistrationID(messageLayer, appContextID);
      this.listeners.put(registrationID, listener);
      String id = this.getMatchingRegistrationId(registrationID, messageLayer, appContextID);
      return id == null ? null : this.getProvider(id);
   }

   private synchronized AuthConfigProvider getProvider(String registrationID) {
      return !this.registrations.containsKey(registrationID) ? null : ((RegistrationContextImpl)this.registrations.get(registrationID)).provider;
   }

   private String getRegistrationID(String messageLayer, String appContextID) {
      StringBuffer sb = new StringBuffer();
      if (messageLayer != null) {
         sb.append(messageLayer);
      }

      sb.append("%");
      if (appContextID != null) {
         sb.append(appContextID);
      }

      return sb.toString();
   }

   public synchronized String[] detachListener(RegistrationListener registrationListener, String messageLayer, String appContextID) {
      List listenerKeys = new ArrayList();
      String idPattern = this.getRegistrationID(messageLayer, appContextID);
      Iterator var6 = this.listeners.entrySet().iterator();

      while(var6.hasNext()) {
         Map.Entry entry = (Map.Entry)var6.next();
         if (entry.getValue() == registrationListener && this.matchesKey(idPattern, (String)entry.getKey())) {
            listenerKeys.add(entry.getKey());
         }
      }

      Set affected = new HashSet();
      Iterator var11 = listenerKeys.iterator();

      while(var11.hasNext()) {
         String id = (String)var11.next();
         this.listeners.remove(id);
         String affectedId = this.getMatchingRegistrationId(id, messageLayer, appContextID);
         if (affectedId != null) {
            affected.add(affectedId);
         }
      }

      return (String[])affected.toArray(new String[affected.size()]);
   }

   private String getMatchingRegistrationId(String candidateId, String messageLayer, String appContextID) {
      String affectedId = candidateId;
      if (!this.registrations.containsKey(candidateId)) {
         affectedId = this.getRegistrationID((String)null, appContextID);
      }

      if (!this.registrations.containsKey(affectedId)) {
         affectedId = this.getRegistrationID(messageLayer, (String)null);
      }

      if (!this.registrations.containsKey(affectedId)) {
         affectedId = this.getRegistrationID((String)null, (String)null);
      }

      if (!this.registrations.containsKey(affectedId)) {
         affectedId = null;
      }

      return affectedId;
   }

   private boolean matchesKey(String pattern, String id) {
      if (pattern.equals("%")) {
         return true;
      } else if (pattern.endsWith("%")) {
         return id.startsWith(pattern);
      } else {
         return pattern.startsWith("%") ? id.endsWith(pattern) : id.equals(pattern);
      }
   }

   public AuthConfigFactory.RegistrationContext getRegistrationContext(String registrationId) {
      return (AuthConfigFactory.RegistrationContext)this.registrations.get(registrationId);
   }

   public synchronized String[] getRegistrationIDs(AuthConfigProvider authConfigProvider) {
      List ids = new ArrayList();
      if (authConfigProvider == null) {
         ids.addAll(this.registrations.keySet());
      } else {
         Iterator var3 = this.registrations.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            if (((RegistrationContextImpl)entry.getValue()).provider == authConfigProvider) {
               ids.add(entry.getKey());
            }
         }
      }

      return (String[])ids.toArray(new String[ids.size()]);
   }

   public synchronized boolean removeRegistration(String registrationId) {
      RegistrationContextImpl ctx = (RegistrationContextImpl)this.registrations.get(registrationId);
      if (ctx == null) {
         return false;
      } else {
         if (this.persistentRegSource != null) {
            this.persistentRegSource.delete(ctx);
         }

         List entries = this.getListenersForRegId(registrationId);
         this.registrations.remove(registrationId);
         this.notifyListeners(entries);
         return true;
      }
   }

   private void notifyListeners(String registrationId) {
      List listeners = this.getListenersForRegId(registrationId);
      this.notifyListeners(listeners);
   }

   private void notifyListeners(List entries) {
      Iterator var2 = entries.iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         ((RegistrationListener)entry.getValue()).notify(this.getLayer((String)entry.getKey()), this.getAppContextId((String)entry.getKey()));
      }

   }

   private List getListenersForRegId(String registrationId) {
      List list = new ArrayList();
      String layer = this.getLayer(registrationId);
      String appContextId = this.getAppContextId(registrationId);
      Iterator var5 = this.listeners.entrySet().iterator();

      while(var5.hasNext()) {
         Map.Entry entry = (Map.Entry)var5.next();
         if (((String)entry.getKey()).contains(registrationId) && registrationId.equals(this.getMatchingRegistrationId((String)entry.getKey(), layer, appContextId)) && entry.getValue() != null) {
            list.add(entry);
         }
      }

      return list;
   }

   private String getAppContextId(String registrationId) {
      int i = registrationId.indexOf("%");
      if (i < 0) {
         return null;
      } else {
         String result = registrationId.substring(i + 1);
         return result.length() == 0 ? null : result;
      }
   }

   private String getLayer(String registrationId) {
      int i = registrationId.indexOf("%");
      return i <= 0 ? null : registrationId.substring(0, i);
   }

   public synchronized void refresh() {
      this.registrations.clear();
      Iterator var1 = this.persistentRegistrations.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         this.registrations.put(entry.getKey(), entry.getValue());
         this.notifyListeners((String)entry.getKey());
      }

   }

   static AuthConfigProvider createProvider(String className, Map properties) throws AuthException {
      try {
         Class aClass = Class.forName(className);
         Constructor constructor = aClass.getConstructor(Map.class, AuthConfigFactory.class);
         return (AuthConfigProvider)constructor.newInstance(properties, null);
      } catch (Exception var4) {
         AuthException authException = new AuthException("Error creating provider: " + className);
         authException.initCause(var4);
         throw authException;
      }
   }

   private class PersistentRegistration extends RegistrationContextImpl {
      private String className;
      private Map properties;

      private PersistentRegistration(String className, Map properties, String messageLayer, String appContext, String description) throws AuthException {
         super(AuthConfigFactoryImpl.createProvider(className, properties), messageLayer, appContext, description);
         this.className = className;
         this.properties = properties;
      }

      public boolean isPersistent() {
         return true;
      }

      public int hashCode() {
         return this.className.hashCode() + this.properties.hashCode();
      }

      public boolean equals(Object obj) {
         if (obj == null) {
            return false;
         } else if (!this.getClass().equals(obj.getClass())) {
            return false;
         } else {
            PersistentRegistration other = (PersistentRegistration)obj;
            return this.equals(this.className, other.className) && this.equals(this.properties, other.properties);
         }
      }

      private boolean equals(Object obj1, Object obj2) {
         if (obj1 == null) {
            return obj2 == null;
         } else {
            return obj1.equals(obj2);
         }
      }

      // $FF: synthetic method
      PersistentRegistration(String x1, Map x2, String x3, String x4, String x5, Object x6) throws AuthException {
         this(x1, x2, x3, x4, x5);
      }
   }

   private class RegistrationContextImpl implements AuthConfigFactory.RegistrationContext {
      AuthConfigProvider provider;
      private String appContext;
      private String messageLayer;
      private String description;

      public RegistrationContextImpl(AuthConfigProvider provider, String messageLayer, String appContext, String description) {
         this.provider = provider;
         this.messageLayer = messageLayer;
         this.appContext = appContext;
         this.description = description;
      }

      public String getAppContext() {
         return this.appContext;
      }

      public String getDescription() {
         return this.description;
      }

      public String getMessageLayer() {
         return this.messageLayer;
      }

      public boolean isPersistent() {
         return false;
      }
   }
}

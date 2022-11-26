package javax.jdo.spi;

import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import javax.jdo.JDOException;
import javax.jdo.JDOFatalInternalException;
import javax.jdo.JDOFatalUserException;
import javax.jdo.JDOUserException;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.ErrorHandler;

public class JDOImplHelper {
   private static Map registeredClasses = Collections.synchronizedMap(new HashMap());
   private static final Map authorizedStateManagerClasses = new WeakHashMap();
   private static final List listeners = new ArrayList();
   private static List stateInterrogations = new ArrayList();
   private static JDOImplHelper jdoImplHelper = new JDOImplHelper();
   private static final I18NHelper msg = I18NHelper.getInstance("javax.jdo.Bundle");
   private static String dateFormatPattern;
   private static DateFormat dateFormat;
   private static DocumentBuilderFactory documentBuilderFactory;
   private static ErrorHandler errorHandler;
   public static final Set USER_CONFIGURABLE_STANDARD_PROPERTIES = createUserConfigurableStandardProperties();
   static final String JAVAX_JDO_PREFIX_LOWER_CASED = "javax.jdo.".toLowerCase();
   static final String PROPERTY_PREFIX_INSTANCE_LIFECYCLE_LISTENER_LOWER_CASED = "javax.jdo.listener.InstanceLifecycleListener.".toLowerCase();
   static final Set USER_CONFIGURABLE_STANDARD_PROPERTIES_LOWER_CASED = createUserConfigurableStandardPropertiesLowerCased();
   static final Map stringConstructorMap;

   private static Set createUserConfigurableStandardProperties() {
      Set props = new HashSet();
      props.add("javax.jdo.option.ConnectionDriverName");
      props.add("javax.jdo.option.ConnectionFactory2Name");
      props.add("javax.jdo.option.ConnectionFactoryName");
      props.add("javax.jdo.option.ConnectionPassword");
      props.add("javax.jdo.option.ConnectionURL");
      props.add("javax.jdo.option.ConnectionUserName");
      props.add("javax.jdo.option.CopyOnAttach");
      props.add("javax.jdo.option.DatastoreReadTimeoutMillis");
      props.add("javax.jdo.option.DatastoreWriteTimeoutMillis");
      props.add("javax.jdo.option.DetachAllOnCommit");
      props.add("javax.jdo.option.IgnoreCache");
      props.add("javax.jdo.listener.InstanceLifecycleListener");
      props.add("javax.jdo.option.Mapping");
      props.add("javax.jdo.mapping.Catalog");
      props.add("javax.jdo.mapping.Schema");
      props.add("javax.jdo.option.Multithreaded");
      props.add("javax.jdo.option.Name");
      props.add("javax.jdo.option.NontransactionalRead");
      props.add("javax.jdo.option.NontransactionalWrite");
      props.add("javax.jdo.option.Optimistic");
      props.add("javax.jdo.PersistenceManagerFactoryClass");
      props.add("javax.jdo.option.PersistenceUnitName");
      props.add("javax.jdo.option.ReadOnly");
      props.add("javax.jdo.option.RestoreValues");
      props.add("javax.jdo.option.RetainValues");
      props.add("javax.jdo.option.ServerTimeZoneID");
      props.add("javax.jdo.option.spi.ResourceName");
      props.add("javax.jdo.option.TransactionIsolationLevel");
      props.add("javax.jdo.option.TransactionType");
      return Collections.unmodifiableSet(props);
   }

   static Set createUserConfigurableStandardPropertiesLowerCased() {
      Set mixedCased = createUserConfigurableStandardProperties();
      Set lowerCased = new HashSet(mixedCased.size());
      Iterator i$ = mixedCased.iterator();

      while(i$.hasNext()) {
         String propertyName = (String)i$.next();
         lowerCased.add(propertyName.toLowerCase());
      }

      return Collections.unmodifiableSet(lowerCased);
   }

   private JDOImplHelper() {
   }

   public static JDOImplHelper getInstance() throws SecurityException {
      SecurityManager sec = System.getSecurityManager();
      if (sec != null) {
         sec.checkPermission(JDOPermission.GET_METADATA);
      }

      return jdoImplHelper;
   }

   public String[] getFieldNames(Class pcClass) {
      Meta meta = getMeta(pcClass);
      return meta.getFieldNames();
   }

   public Class[] getFieldTypes(Class pcClass) {
      Meta meta = getMeta(pcClass);
      return meta.getFieldTypes();
   }

   public byte[] getFieldFlags(Class pcClass) {
      Meta meta = getMeta(pcClass);
      return meta.getFieldFlags();
   }

   public Class getPersistenceCapableSuperclass(Class pcClass) {
      Meta meta = getMeta(pcClass);
      return meta.getPersistenceCapableSuperclass();
   }

   public PersistenceCapable newInstance(Class pcClass, StateManager sm) {
      Meta meta = getMeta(pcClass);
      PersistenceCapable pcInstance = meta.getPC();
      return pcInstance == null ? null : pcInstance.jdoNewInstance(sm);
   }

   public PersistenceCapable newInstance(Class pcClass, StateManager sm, Object oid) {
      Meta meta = getMeta(pcClass);
      PersistenceCapable pcInstance = meta.getPC();
      return pcInstance == null ? null : pcInstance.jdoNewInstance(sm, oid);
   }

   public Object newObjectIdInstance(Class pcClass) {
      Meta meta = getMeta(pcClass);
      PersistenceCapable pcInstance = meta.getPC();
      return pcInstance == null ? null : pcInstance.jdoNewObjectIdInstance();
   }

   public Object newObjectIdInstance(Class pcClass, Object obj) {
      Meta meta = getMeta(pcClass);
      PersistenceCapable pcInstance = meta.getPC();
      return pcInstance == null ? null : pcInstance.jdoNewObjectIdInstance(obj);
   }

   public void copyKeyFieldsToObjectId(Class pcClass, PersistenceCapable.ObjectIdFieldSupplier fm, Object oid) {
      Meta meta = getMeta(pcClass);
      PersistenceCapable pcInstance = meta.getPC();
      if (pcInstance == null) {
         throw new JDOFatalInternalException(msg.msg("ERR_AbstractClassNoIdentity", (Object)pcClass.getName()));
      } else {
         pcInstance.jdoCopyKeyFieldsToObjectId(fm, oid);
      }
   }

   public void copyKeyFieldsFromObjectId(Class pcClass, PersistenceCapable.ObjectIdFieldConsumer fm, Object oid) {
      Meta meta = getMeta(pcClass);
      PersistenceCapable pcInstance = meta.getPC();
      if (pcInstance == null) {
         throw new JDOFatalInternalException(msg.msg("ERR_AbstractClassNoIdentity", (Object)pcClass.getName()));
      } else {
         pcInstance.jdoCopyKeyFieldsFromObjectId(fm, oid);
      }
   }

   public static void registerClass(Class pcClass, String[] fieldNames, Class[] fieldTypes, byte[] fieldFlags, Class persistenceCapableSuperclass, PersistenceCapable pc) {
      if (pcClass == null) {
         throw new NullPointerException(msg.msg("ERR_NullClass"));
      } else {
         Meta meta = new Meta(fieldNames, fieldTypes, fieldFlags, persistenceCapableSuperclass, pc);
         registeredClasses.put(pcClass, meta);
         synchronized(listeners) {
            if (!listeners.isEmpty()) {
               RegisterClassEvent event = new RegisterClassEvent(jdoImplHelper, pcClass, fieldNames, fieldTypes, fieldFlags, persistenceCapableSuperclass);
               Iterator i = listeners.iterator();

               while(i.hasNext()) {
                  RegisterClassListener crl = (RegisterClassListener)i.next();
                  if (crl != null) {
                     crl.registerClass(event);
                  }
               }
            }

         }
      }
   }

   public void unregisterClasses(ClassLoader cl) {
      SecurityManager sec = System.getSecurityManager();
      if (sec != null) {
         sec.checkPermission(JDOPermission.MANAGE_METADATA);
      }

      synchronized(registeredClasses) {
         Iterator i = registeredClasses.keySet().iterator();

         while(i.hasNext()) {
            Class pcClass = (Class)i.next();
            if (pcClass != null && pcClass.getClassLoader() == cl) {
               i.remove();
            }
         }

      }
   }

   public void unregisterClass(Class pcClass) {
      if (pcClass == null) {
         throw new NullPointerException(msg.msg("ERR_NullClass"));
      } else {
         SecurityManager sec = System.getSecurityManager();
         if (sec != null) {
            sec.checkPermission(JDOPermission.MANAGE_METADATA);
         }

         registeredClasses.remove(pcClass);
      }
   }

   public void addRegisterClassListener(RegisterClassListener crl) {
      HashSet alreadyRegisteredClasses = null;
      synchronized(listeners) {
         listeners.add(crl);
         alreadyRegisteredClasses = new HashSet(registeredClasses.keySet());
      }

      Iterator it = alreadyRegisteredClasses.iterator();

      while(it.hasNext()) {
         Class pcClass = (Class)it.next();
         Meta meta = getMeta(pcClass);
         RegisterClassEvent event = new RegisterClassEvent(this, pcClass, meta.getFieldNames(), meta.getFieldTypes(), meta.getFieldFlags(), meta.getPersistenceCapableSuperclass());
         crl.registerClass(event);
      }

   }

   public void removeRegisterClassListener(RegisterClassListener crl) {
      synchronized(listeners) {
         listeners.remove(crl);
      }
   }

   public Collection getRegisteredClasses() {
      return Collections.unmodifiableCollection(registeredClasses.keySet());
   }

   private static Meta getMeta(Class pcClass) {
      Meta ret = (Meta)registeredClasses.get(pcClass);
      if (ret == null) {
         throw new JDOFatalUserException(msg.msg("ERR_NoMetadata", (Object)pcClass.getName()));
      } else {
         return ret;
      }
   }

   public static void registerAuthorizedStateManagerClass(Class smClass) throws SecurityException {
      if (smClass == null) {
         throw new NullPointerException(msg.msg("ERR_NullClass"));
      } else {
         SecurityManager sm = System.getSecurityManager();
         if (sm != null) {
            sm.checkPermission(JDOPermission.SET_STATE_MANAGER);
         }

         synchronized(authorizedStateManagerClasses) {
            authorizedStateManagerClasses.put(smClass, (Object)null);
         }
      }
   }

   public static void registerAuthorizedStateManagerClasses(Collection smClasses) throws SecurityException {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null) {
         sm.checkPermission(JDOPermission.SET_STATE_MANAGER);
         synchronized(authorizedStateManagerClasses) {
            Iterator it = smClasses.iterator();

            while(it.hasNext()) {
               Object smClass = it.next();
               if (!(smClass instanceof Class)) {
                  throw new ClassCastException(msg.msg("ERR_StateManagerClassCast", (Object)smClass.getClass().getName()));
               }

               registerAuthorizedStateManagerClass((Class)it.next());
            }
         }
      }

   }

   public synchronized void registerDocumentBuilderFactory(DocumentBuilderFactory factory) {
      documentBuilderFactory = factory;
   }

   public static DocumentBuilderFactory getRegisteredDocumentBuilderFactory() {
      return documentBuilderFactory;
   }

   public synchronized void registerErrorHandler(ErrorHandler handler) {
      errorHandler = handler;
   }

   public static ErrorHandler getRegisteredErrorHandler() {
      return errorHandler;
   }

   public static void checkAuthorizedStateManager(StateManager sm) {
      checkAuthorizedStateManagerClass(sm.getClass());
   }

   public static void checkAuthorizedStateManagerClass(Class smClass) {
      SecurityManager scm = System.getSecurityManager();
      if (scm != null) {
         synchronized(authorizedStateManagerClasses) {
            if (authorizedStateManagerClasses.containsKey(smClass)) {
               return;
            }
         }

         scm.checkPermission(JDOPermission.SET_STATE_MANAGER);
      }
   }

   public Object registerStringConstructor(Class cls, StringConstructor sc) {
      synchronized(stringConstructorMap) {
         return stringConstructorMap.put(cls, sc);
      }
   }

   private static Locale getLocale(String s) {
      int firstUnderbar = s.indexOf(95);
      if (firstUnderbar == -1) {
         return new Locale(s);
      } else {
         String lang = s.substring(0, firstUnderbar);
         int secondUnderbar = s.indexOf(95, firstUnderbar + 1);
         String country;
         if (secondUnderbar == -1) {
            country = s.substring(firstUnderbar + 1);
            return new Locale(lang, country);
         } else {
            country = s.substring(firstUnderbar + 1, secondUnderbar);
            String variant = s.substring(secondUnderbar + 1);
            return new Locale(lang, country, variant);
         }
      }
   }

   private static boolean isClassLoadable(String className) {
      try {
         Class.forName(className);
         return true;
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   public static Object construct(String className, String keyString) {
      try {
         Class keyClass = Class.forName(className);
         StringConstructor stringConstructor;
         synchronized(stringConstructorMap) {
            stringConstructor = (StringConstructor)stringConstructorMap.get(keyClass);
         }

         if (stringConstructor != null) {
            return stringConstructor.construct(keyString);
         } else {
            Constructor keyConstructor = keyClass.getConstructor(String.class);
            return keyConstructor.newInstance(keyString);
         }
      } catch (JDOException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new JDOUserException(msg.msg("EXC_ObjectIdentityStringConstruction", new Object[]{var8.toString(), className, keyString}), var8);
      }
   }

   static DateFormat getDateTimeInstance() {
      DateFormat result = null;

      try {
         result = (DateFormat)AccessController.doPrivileged(new PrivilegedAction() {
            public DateFormat run() {
               return DateFormat.getDateTimeInstance();
            }
         });
      } catch (Exception var2) {
         result = DateFormat.getInstance();
      }

      return result;
   }

   public synchronized void registerDateFormat(DateFormat df) {
      dateFormat = df;
      if (df instanceof SimpleDateFormat) {
         dateFormatPattern = ((SimpleDateFormat)df).toPattern();
      } else {
         dateFormatPattern = msg.msg("MSG_unknown");
      }

   }

   public synchronized void addStateInterrogation(StateInterrogation si) {
      List newList = new ArrayList(stateInterrogations);
      newList.add(si);
      stateInterrogations = newList;
   }

   public synchronized void removeStateInterrogation(StateInterrogation si) {
      List newList = new ArrayList(stateInterrogations);
      newList.remove(si);
      stateInterrogations = newList;
   }

   private synchronized Iterator getStateInterrogationIterator() {
      return stateInterrogations.iterator();
   }

   public void nonBinaryCompatibleMakeDirty(Object pc, String fieldName) {
      Iterator sit = this.getStateInterrogationIterator();

      while(sit.hasNext()) {
         StateInterrogation si = (StateInterrogation)sit.next();

         try {
            if (si.makeDirty(pc, fieldName)) {
               return;
            }
         } catch (Throwable var6) {
         }
      }

   }

   public boolean nonBinaryCompatibleIs(Object pc, StateInterrogationBooleanReturn sibr) {
      Iterator sit = this.getStateInterrogationIterator();

      while(sit.hasNext()) {
         StateInterrogation si = (StateInterrogation)sit.next();

         Boolean result;
         try {
            result = sibr.is(pc, si);
         } catch (Throwable var7) {
            continue;
         }

         if (result != null) {
            return result;
         }
      }

      return false;
   }

   public Object nonBinaryCompatibleGet(Object pc, StateInterrogationObjectReturn sibr) {
      Iterator sit = this.getStateInterrogationIterator();

      while(sit.hasNext()) {
         StateInterrogation si = (StateInterrogation)sit.next();

         Object result;
         try {
            result = sibr.get(pc, si);
         } catch (Throwable var7) {
            continue;
         }

         if (result != null) {
            return result;
         }
      }

      return null;
   }

   public static void assertOnlyKnownStandardProperties(Map properties) {
      if (properties != null && !properties.isEmpty()) {
         List exceptions = new ArrayList();
         StringBuilder unknowns = new StringBuilder();
         Iterator i$ = properties.keySet().iterator();

         while(i$.hasNext()) {
            Object key = i$.next();
            if (key instanceof String) {
               String s = ((String)key).toLowerCase();
               if (s.startsWith(JAVAX_JDO_PREFIX_LOWER_CASED) && !s.startsWith(PROPERTY_PREFIX_INSTANCE_LIFECYCLE_LISTENER_LOWER_CASED) && !USER_CONFIGURABLE_STANDARD_PROPERTIES_LOWER_CASED.contains(s)) {
                  exceptions.add(new JDOUserException(msg.msg("EXC_UnknownStandardProperty", (Object)s)));
                  if (exceptions.size() > 1) {
                     unknowns.append(",");
                  }

                  unknowns.append(s);
               }
            }
         }

         if (exceptions.size() == 1) {
            throw (JDOUserException)exceptions.get(0);
         } else if (exceptions.size() > 1) {
            throw new JDOUserException(msg.msg("EXC_UnknownStandardProperties", (Object)unknowns.toString()), (Throwable[])exceptions.toArray(new JDOUserException[0]));
         }
      }
   }

   static {
      jdoImplHelper.registerDateFormat(getDateTimeInstance());
      stringConstructorMap = new HashMap();
      if (isClassLoadable("java.util.Currency")) {
         jdoImplHelper.registerStringConstructor(Currency.class, new StringConstructor() {
            public Object construct(String s) {
               try {
                  return Currency.getInstance(s);
               } catch (IllegalArgumentException var3) {
                  throw new JDOUserException(JDOImplHelper.msg.msg("EXC_CurrencyStringConstructorIllegalArgument", (Object)s), var3);
               } catch (Exception var4) {
                  throw new JDOUserException(JDOImplHelper.msg.msg("EXC_CurrencyStringConstructorException"), var4);
               }
            }
         });
      }

      jdoImplHelper.registerStringConstructor(Locale.class, new StringConstructor() {
         public Object construct(String s) {
            try {
               return JDOImplHelper.getLocale(s);
            } catch (Exception var3) {
               throw new JDOUserException(JDOImplHelper.msg.msg("EXC_LocaleStringConstructorException"), var3);
            }
         }
      });
      jdoImplHelper.registerStringConstructor(Date.class, new StringConstructor() {
         public synchronized Object construct(String s) {
            try {
               return new Date(Long.parseLong(s));
            } catch (NumberFormatException var5) {
               ParsePosition pp = new ParsePosition(0);
               Date result = JDOImplHelper.dateFormat.parse(s, pp);
               if (result == null) {
                  throw new JDOUserException(JDOImplHelper.msg.msg("EXC_DateStringConstructor", new Object[]{s, new Integer(pp.getErrorIndex()), JDOImplHelper.dateFormatPattern}));
               } else {
                  return result;
               }
            }
         }
      });
   }

   public interface StateInterrogationObjectReturn {
      Object get(Object var1, StateInterrogation var2);
   }

   public interface StateInterrogationBooleanReturn {
      Boolean is(Object var1, StateInterrogation var2);
   }

   static class Meta {
      String[] fieldNames;
      Class[] fieldTypes;
      byte[] fieldFlags;
      Class persistenceCapableSuperclass;
      PersistenceCapable pc;

      Meta(String[] fieldNames, Class[] fieldTypes, byte[] fieldFlags, Class persistenceCapableSuperclass, PersistenceCapable pc) {
         this.fieldNames = fieldNames;
         this.fieldTypes = fieldTypes;
         this.fieldFlags = fieldFlags;
         this.persistenceCapableSuperclass = persistenceCapableSuperclass;
         this.pc = pc;
      }

      String[] getFieldNames() {
         return this.fieldNames;
      }

      Class[] getFieldTypes() {
         return this.fieldTypes;
      }

      byte[] getFieldFlags() {
         return this.fieldFlags;
      }

      Class getPersistenceCapableSuperclass() {
         return this.persistenceCapableSuperclass;
      }

      PersistenceCapable getPC() {
         return this.pc;
      }

      public String toString() {
         return "Meta-" + this.pc.getClass().getName();
      }
   }

   public interface StringConstructor {
      Object construct(String var1);
   }
}

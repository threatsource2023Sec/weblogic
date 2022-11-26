package weblogic.management.eventbus.apis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import weblogic.management.eventbus.InternalEventBusLogger;

public class InternalEventBusFactory {
   private static InternalEventBus singleton = null;
   private static final String INTERNAL_EVENT_BUS_IMPLEMENTATION = "weblogic.management.eventbus.spi.InternalEventBusImpl";
   public static final String[] INTERNAL_LISTENERS_LOCATION = new String[]{"META-INF/" + InternalEventBusFactory.class.getName() + ".resource", "meta-inf/" + InternalEventBusFactory.class.getName() + ".resource"};

   public static synchronized InternalEventBus getInstance() {
      if (singleton == null) {
         try {
            Class internalEventBusImplClass = Class.forName("weblogic.management.eventbus.spi.InternalEventBusImpl");
            singleton = (InternalEventBus)InternalEventBus.class.cast(internalEventBusImplClass.newInstance());
         } catch (Throwable var3) {
            InternalEventBusLogger.logErrorInitializingEventBus("weblogic.management.eventbus.spi.InternalEventBusImpl", var3);
         }

         if (singleton != null) {
            try {
               List listeners = loadListeners();
               Iterator var1 = listeners.iterator();

               while(var1.hasNext()) {
                  InternalEventListener listener = (InternalEventListener)var1.next();
                  singleton.registerListener(listener);
               }
            } catch (Throwable var4) {
               InternalEventBusLogger.logErrorRegisteringResourceListeners(var4);
            }
         }
      }

      return singleton;
   }

   private static List loadListeners() throws IOException {
      List listeners = new ArrayList();
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      Set listenerNames = new HashSet();
      String[] var3 = INTERNAL_LISTENERS_LOCATION;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String listenerResourceLocation = var3[var5];
         Enumeration urls = cl.getResources(listenerResourceLocation);

         while(urls.hasMoreElements()) {
            URL url = (URL)urls.nextElement();
            List names = loadListenersNamesFromURL(url);
            listenerNames.addAll(names);
         }
      }

      List urlListeners = loadListenerInstances(listenerNames);
      listeners.addAll(urlListeners);
      return listeners;
   }

   private static List loadListenersNamesFromURL(URL url) throws IOException {
      InputStream is = null;
      List listenerNames = null;

      try {
         is = url.openStream();
         listenerNames = getListenerEntries(is);
      } finally {
         is.close();
      }

      return listenerNames;
   }

   private static List loadListenerInstances(Set listenerNames) {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      List listeners = new ArrayList();
      Iterator var3 = listenerNames.iterator();

      while(var3.hasNext()) {
         String listenerClass = (String)var3.next();
         Class listenerClassInstance = null;

         try {
            listenerClassInstance = Class.forName(listenerClass, true, cl);
         } catch (Throwable var8) {
            InternalEventBusLogger.logErrorLoadingListenerClass(listenerClass, var8);
         }

         if (listenerClassInstance != null) {
            try {
               InternalEventListener listener = (InternalEventListener)InternalEventListener.class.cast(listenerClassInstance.newInstance());
               listeners.add(listener);
            } catch (Throwable var7) {
               InternalEventBusLogger.logErrorInstantiatingListenerInstance(listenerClass, var7);
            }
         }
      }

      return listeners;
   }

   private static List getListenerEntries(InputStream is) throws IOException {
      List listenerEntries = new ArrayList();
      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String listenerEntry = null;

      while((listenerEntry = br.readLine()) != null) {
         listenerEntry = listenerEntry.trim();
         if (listenerEntry.length() > 0) {
            listenerEntries.add(listenerEntry);
         }
      }

      return listenerEntries;
   }
}

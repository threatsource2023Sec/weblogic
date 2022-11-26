package weblogic.nodemanager.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import org.glassfish.jersey.internal.ServiceFinder;
import weblogic.nodemanager.server.NMServer;

public class NMServiceIteratorProvider extends ServiceFinder.ServiceIteratorProvider {
   private static final ArrayList packages = new ArrayList();
   private static final String SERVICEFINDER_PACKAGES = "weblogic.nodemanager.server.jersey.servicefinder.packages";
   private static final String packageNameDelimiter = ":";
   private static final ServiceFinder.DefaultServiceIteratorProvider defaultServiceIteratorProvider = new ServiceFinder.DefaultServiceIteratorProvider();

   public Iterator createIterator(Class service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
      Iterator it = defaultServiceIteratorProvider.createIterator(service, serviceName, loader, ignoreOnClassNotFound);
      ArrayList instances = new ArrayList();

      while(it.hasNext()) {
         Object cl = it.next();
         String className = cl.getClass().getName();
         boolean invalid = true;
         Iterator var10 = packages.iterator();

         while(var10.hasNext()) {
            String packageName = (String)var10.next();
            if (className.startsWith(packageName)) {
               instances.add(cl);
               invalid = false;
               break;
            }
         }

         if (invalid) {
            this.printExcluded(Level.FINE, service, className);
         }
      }

      if (instances.isEmpty()) {
         this.printNoImpl(Level.FINE, serviceName);
      }

      return instances.iterator();
   }

   public Iterator createClassIterator(Class service, String serviceName, ClassLoader loader, boolean ignoreOnClassNotFound) {
      Iterator it = defaultServiceIteratorProvider.createClassIterator(service, serviceName, loader, ignoreOnClassNotFound);
      ArrayList classes = new ArrayList();

      while(it.hasNext()) {
         Class cl = (Class)it.next();
         String className = cl.getName();
         boolean invalid = true;
         Iterator var10 = packages.iterator();

         while(var10.hasNext()) {
            String packageName = (String)var10.next();
            if (className.startsWith(packageName)) {
               classes.add(cl);
               invalid = false;
               break;
            }
         }

         if (invalid) {
            this.printExcluded(Level.FINE, service, className);
         }
      }

      if (classes.isEmpty()) {
         this.printNoImpl(Level.FINE, serviceName);
      }

      return classes.iterator();
   }

   private static void addPackages(String[] packageNamesArray) {
      if (packageNamesArray != null) {
         String[] var1 = packageNamesArray;
         int var2 = packageNamesArray.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String packageName = var1[var3];
            packages.add(packageName);
         }

      }
   }

   private void printNoImpl(Level logLevel, String serviceName) {
      if (NMServer.nmLog.isLoggable(logLevel)) {
         NMServer.nmLog.log(Level.FINE, "No implementations for service " + serviceName);
      }

   }

   private void printExcluded(Level logLevel, Class service, String className) {
      if (NMServer.nmLog.isLoggable(logLevel)) {
         NMServer.nmLog.log(logLevel, "Class " + className + " for service " + service + " excluded.");
      }

   }

   static {
      String[] defaultPackages = new String[]{"org.glassfish.jersey", "weblogic.nodemanager"};
      addPackages(defaultPackages);
      String packageNames = System.getProperty("weblogic.nodemanager.server.jersey.servicefinder.packages");
      if (packageNames != null) {
         addPackages(packageNames.split(":"));
      }

   }
}

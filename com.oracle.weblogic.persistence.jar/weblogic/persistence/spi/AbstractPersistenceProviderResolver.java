package weblogic.persistence.spi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;
import javax.persistence.spi.PersistenceProviderResolverHolder;

public abstract class AbstractPersistenceProviderResolver implements PersistenceProviderResolver {
   private static final String JPA_PERSISTENCE_NAME = "javax.persistence.Persistence";
   private static final String CREATE_EMF_NAME = "createEntityManagerFactory";
   private static final String JPA_PACKAGE_NAME = "javax.persistence";
   private volatile boolean installed;
   private PersistenceProviderResolver delegate;
   private boolean skipCallerCheck;

   public void postConstruct() {
      this.install();
   }

   protected abstract DefaultPP getDefaultPP();

   private void install() {
      if (!this.installed) {
         PersistenceProviderResolver existing = PersistenceProviderResolverHolder.getPersistenceProviderResolver();
         if (!(existing instanceof PersistenceProviderResolverService)) {
            PersistenceProviderResolverHolder.setPersistenceProviderResolver(this);
            this.delegate = existing;
            this.installed = true;
         }
      }

   }

   public void install_but_skip_caller_check() {
      this.install();
      this.skipCallerCheck = true;
   }

   public List getPersistenceProviders() {
      if (!this.installed) {
         throw new AssertionError("This persistence provider resolver has not been installed");
      } else {
         List list = this.delegate.getPersistenceProviders();
         if (!this.skipCallerCheck && !this.isJPA1CompatibleCaller()) {
            return this.createListWithoutKodo(list);
         } else {
            return this.getDefaultPP() == AbstractPersistenceProviderResolver.DefaultPP.ECLIPSELINK ? this.createListForTopLink(list) : this.createListForKodo(list);
         }
      }
   }

   private List createListWithoutKodo(List pps) {
      LinkedList newList = new LinkedList();
      Iterator var3 = pps.iterator();

      while(var3.hasNext()) {
         PersistenceProvider pp = (PersistenceProvider)var3.next();
         String clName = pp.getClass().getName();
         if (!clName.startsWith("kodo.persistence") && !clName.startsWith("org.apache.openjpa")) {
            newList.add(pp);
         }
      }

      return newList;
   }

   private List createListForKodo(List pps) {
      int countKodoProviders = 0;
      LinkedList newList = new LinkedList();
      Iterator var4 = pps.iterator();

      while(var4.hasNext()) {
         PersistenceProvider pp = (PersistenceProvider)var4.next();
         String clName = pp.getClass().getName();
         if (clName.startsWith("kodo.persistence")) {
            newList.add(countKodoProviders++, pp);
         } else if (clName.startsWith("org.apache.openjpa")) {
            newList.add(countKodoProviders++, pp);
         } else {
            newList.addLast(pp);
         }
      }

      return newList;
   }

   private List createListForTopLink(List pps) {
      int countELProviders = 0;
      LinkedList newList = new LinkedList();
      Iterator var4 = pps.iterator();

      while(var4.hasNext()) {
         PersistenceProvider pp = (PersistenceProvider)var4.next();
         String clName = pp.getClass().getName();
         if (clName.startsWith("org.eclipse.persistence")) {
            newList.add(countELProviders++, pp);
         } else {
            newList.addLast(pp);
         }
      }

      return newList;
   }

   private boolean isJPA1CompatibleCaller() {
      StackTraceElement[] var1 = (new RuntimeException()).getStackTrace();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         StackTraceElement entry = var1[var3];
         String clazz = entry.getClassName();
         String method = entry.getMethodName();
         if ("javax.persistence.Persistence".equals(clazz) && "createEntityManagerFactory".equals(method)) {
            return true;
         }

         if (clazz.startsWith("javax.persistence")) {
            break;
         }
      }

      return false;
   }

   public void clearCachedProviders() {
      this.delegate.clearCachedProviders();
   }

   protected static enum DefaultPP {
      KODO,
      ECLIPSELINK;
   }
}

package javax.enterprise.inject.se;

import java.util.Iterator;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import javax.enterprise.inject.spi.Extension;

public abstract class SeContainerInitializer {
   public static SeContainerInitializer newInstance() {
      return findSeContainerInitializer();
   }

   private static SeContainerInitializer findSeContainerInitializer() {
      Iterator iterator = ServiceLoader.load(SeContainerInitializer.class, SeContainerInitializer.class.getClassLoader()).iterator();
      if (!iterator.hasNext()) {
         throw new IllegalStateException("No valid CDI implementation found");
      } else {
         SeContainerInitializer result;
         try {
            result = (SeContainerInitializer)iterator.next();
         } catch (ServiceConfigurationError var3) {
            throw new IllegalStateException("Error while instantiating SeContainerInitializer", var3);
         }

         if (iterator.hasNext()) {
            throw new IllegalStateException("Two or more CDI implementations found, only one is supported");
         } else {
            return result;
         }
      }
   }

   public abstract SeContainerInitializer addBeanClasses(Class... var1);

   public abstract SeContainerInitializer addPackages(Class... var1);

   public abstract SeContainerInitializer addPackages(boolean var1, Class... var2);

   public abstract SeContainerInitializer addPackages(Package... var1);

   public abstract SeContainerInitializer addPackages(boolean var1, Package... var2);

   public abstract SeContainerInitializer addExtensions(Extension... var1);

   public abstract SeContainerInitializer addExtensions(Class... var1);

   public abstract SeContainerInitializer enableInterceptors(Class... var1);

   public abstract SeContainerInitializer enableDecorators(Class... var1);

   public abstract SeContainerInitializer selectAlternatives(Class... var1);

   public abstract SeContainerInitializer selectAlternativeStereotypes(Class... var1);

   public abstract SeContainerInitializer addProperty(String var1, Object var2);

   public abstract SeContainerInitializer setProperties(Map var1);

   public abstract SeContainerInitializer disableDiscovery();

   public abstract SeContainerInitializer setClassLoader(ClassLoader var1);

   public abstract SeContainer initialize();
}

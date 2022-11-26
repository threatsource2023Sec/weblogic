package org.glassfish.tyrus.spi;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

public abstract class ServerContainerFactory {
   private static final String CONTAINTER_CLASS = "org.glassfish.tyrus.container.grizzly.server.GrizzlyServerContainer";

   public static ServerContainer createServerContainer() {
      return createServerContainer(Collections.emptyMap());
   }

   public static ServerContainer createServerContainer(Map properties) {
      ServerContainerFactory factory = null;
      Iterator it = ServiceLoader.load(ServerContainerFactory.class).iterator();
      if (it.hasNext()) {
         factory = (ServerContainerFactory)it.next();
      }

      if (factory == null) {
         try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class factoryClass = classLoader == null ? Class.forName("org.glassfish.tyrus.container.grizzly.server.GrizzlyServerContainer") : classLoader.loadClass("org.glassfish.tyrus.container.grizzly.server.GrizzlyServerContainer");
            factory = (ServerContainerFactory)factoryClass.newInstance();
         } catch (ClassNotFoundException var5) {
            throw new RuntimeException(var5);
         } catch (InstantiationException var6) {
            throw new RuntimeException(var6);
         } catch (IllegalAccessException var7) {
            throw new RuntimeException(var7);
         }
      }

      return factory.createContainer(properties);
   }

   public abstract ServerContainer createContainer(Map var1);
}

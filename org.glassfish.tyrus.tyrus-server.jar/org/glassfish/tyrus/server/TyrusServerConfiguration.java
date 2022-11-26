package org.glassfish.tyrus.server;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DeploymentException;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpoint;
import org.glassfish.tyrus.core.ErrorCollector;
import org.glassfish.tyrus.core.ReflectionHelper;

public class TyrusServerConfiguration implements ServerApplicationConfig {
   private static final Logger LOGGER = Logger.getLogger(TyrusServerConfiguration.class.getName());
   private final Set serverEndpointConfigs;
   private final Set annotatedClasses;

   public TyrusServerConfiguration(Set classes, Set serverEndpointConfigs) {
      this(classes, Collections.emptySet(), serverEndpointConfigs, new ErrorCollector());
   }

   public TyrusServerConfiguration(Set classes, Set dynamicallyAddedClasses, Set serverEndpointConfigs, ErrorCollector errorCollector) {
      this.serverEndpointConfigs = new HashSet();
      this.annotatedClasses = new HashSet();
      if (classes != null && serverEndpointConfigs != null && errorCollector != null) {
         this.serverEndpointConfigs.addAll(serverEndpointConfigs);
         Set configurations = new HashSet();
         Set scannedProgramatics = new HashSet();
         Set scannedAnnotateds = new HashSet();
         Iterator it = classes.iterator();

         Class c;
         ServerApplicationConfig serverApplicationConfig;
         while(it.hasNext()) {
            c = (Class)it.next();
            if (this.isAbstract(c, errorCollector)) {
               it.remove();
            } else {
               if (ServerApplicationConfig.class.isAssignableFrom(c)) {
                  serverApplicationConfig = (ServerApplicationConfig)ReflectionHelper.getInstance(c, errorCollector);
                  configurations.add(serverApplicationConfig);
               }

               if (Endpoint.class.isAssignableFrom(c)) {
                  scannedProgramatics.add(c);
               }

               if (c.isAnnotationPresent(ServerEndpoint.class)) {
                  scannedAnnotateds.add(c);
               }
            }
         }

         it = dynamicallyAddedClasses.iterator();

         while(it.hasNext()) {
            c = (Class)it.next();
            if (!this.isAbstract(c, errorCollector)) {
               if (c.isAnnotationPresent(ServerEndpoint.class)) {
                  this.annotatedClasses.add(c);
               } else if (ServerApplicationConfig.class.isAssignableFrom(c)) {
                  serverApplicationConfig = (ServerApplicationConfig)ReflectionHelper.getInstance(c, errorCollector);
                  configurations.add(serverApplicationConfig);
               } else {
                  errorCollector.addException(new DeploymentException(String.format("Class %s is not ServerApplicationConfig descendant nor has @ServerEndpoint annotation.", c.getName())));
               }
            }
         }

         if (LOGGER.isLoggable(Level.CONFIG)) {
            StringBuilder logMessage = new StringBuilder();
            if (!configurations.isEmpty()) {
               logMessage.append("Found server application configs:\n");
            }

            Iterator var13 = configurations.iterator();

            while(var13.hasNext()) {
               serverApplicationConfig = (ServerApplicationConfig)var13.next();
               logMessage.append("\t").append(serverApplicationConfig.getClass().getName()).append("\n");
            }

            if (!scannedProgramatics.isEmpty()) {
               logMessage.append("Found programmatic endpoints:\n");
            }

            var13 = scannedProgramatics.iterator();

            Class endpoint;
            while(var13.hasNext()) {
               endpoint = (Class)var13.next();
               logMessage.append("\t").append(endpoint.getName()).append("\n");
            }

            if (!scannedAnnotateds.isEmpty() || !this.annotatedClasses.isEmpty()) {
               logMessage.append("Found annotated endpoints:\n");
            }

            var13 = scannedAnnotateds.iterator();

            while(var13.hasNext()) {
               endpoint = (Class)var13.next();
               logMessage.append("\t").append(endpoint.getName()).append("\n");
            }

            var13 = this.annotatedClasses.iterator();

            while(var13.hasNext()) {
               endpoint = (Class)var13.next();
               logMessage.append("\t").append(endpoint.getName()).append("\n");
            }

            if (!logMessage.toString().equals("")) {
               LOGGER.config(logMessage.toString());
            }
         }

         if (!configurations.isEmpty()) {
            it = configurations.iterator();

            while(it.hasNext()) {
               ServerApplicationConfig configuration = (ServerApplicationConfig)it.next();
               Set programmatic = configuration.getEndpointConfigs(scannedProgramatics);
               Set programmatic = programmatic == null ? new HashSet() : programmatic;
               this.serverEndpointConfigs.addAll((Collection)programmatic);
               Set annotated = configuration.getAnnotatedEndpointClasses(scannedAnnotateds);
               Set annotated = annotated == null ? new HashSet() : annotated;
               this.annotatedClasses.addAll((Collection)annotated);
            }
         } else {
            this.annotatedClasses.addAll(scannedAnnotateds);
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   private boolean isAbstract(Class clazz, ErrorCollector errorCollector) {
      if (!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
         return false;
      } else {
         LOGGER.log(Level.WARNING, String.format("%s: Deployed class can't be abstract nor interface. The class will not be deployed.", clazz.getName()));
         return true;
      }
   }

   public Set getEndpointConfigs(Set scanned) {
      return Collections.unmodifiableSet(this.serverEndpointConfigs);
   }

   public Set getAnnotatedEndpointClasses(Set scanned) {
      return Collections.unmodifiableSet(this.annotatedClasses);
   }
}

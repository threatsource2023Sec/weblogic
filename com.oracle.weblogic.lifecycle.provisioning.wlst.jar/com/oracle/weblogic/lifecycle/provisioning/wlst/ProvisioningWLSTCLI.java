package com.oracle.weblogic.lifecycle.provisioning.wlst;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import weblogic.management.scripting.WLSTUtils;

public final class ProvisioningWLSTCLI implements AutoCloseable {
   private final Client client;
   private final Thread shutdownHook;
   private final Logger logger;

   public ProvisioningWLSTCLI(String host, int port, String user, String password) throws ProvisioningException, URISyntaxException {
      String cn = this.getClass().getName();
      String mn = "<init>";
      this.logger = Logger.getLogger(cn);

      assert this.logger != null;

      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "<init>", new Object[]{host, port, user, password});
      }

      Objects.requireNonNull(host, "host == null");
      this.shutdownHook = new ShutdownHook(this);
      this.client = new Client(host, port, user, password);
      this.connect();
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "<init>");
      }

   }

   /** @deprecated */
   @Deprecated
   public ProvisioningWLSTCLI(String suppliedRestURI, String username, String password, WLSTUtils utils) {
      String cn = this.getClass().getName();
      String mn = "<init>";
      this.logger = Logger.getLogger(cn);

      assert this.logger != null;

      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "<init>", new Object[]{suppliedRestURI, username, password, utils});
      }

      this.shutdownHook = new ShutdownHook(this);
      if (suppliedRestURI != null && !suppliedRestURI.isEmpty()) {
         if (username != null && !username.isEmpty()) {
            if (password != null && !password.isEmpty()) {
               int doubleSlashIndex = suppliedRestURI.indexOf("//");
               String restURI;
               if (doubleSlashIndex < 0) {
                  restURI = "http://" + suppliedRestURI;
               } else if (doubleSlashIndex == 0) {
                  restURI = "http:" + suppliedRestURI;
               } else if (suppliedRestURI.charAt(doubleSlashIndex - 1) == ':') {
                  restURI = "http:" + suppliedRestURI.substring(doubleSlashIndex);
               } else {
                  restURI = "http://" + suppliedRestURI;
               }

               URI uri = URI.create(restURI);

               assert uri != null;

               try {
                  this.client = new Client(uri, username, password);
               } catch (URISyntaxException var12) {
                  throw new IllegalArgumentException("suppliedRestURI: " + suppliedRestURI, var12);
               }

               try {
                  this.connect();
               } catch (ProvisioningException var11) {
                  throw (RuntimeException)toRuntimeException(uri, var11).fillInStackTrace();
               }

               if (this.logger.isLoggable(Level.FINER)) {
                  this.logger.exiting(cn, "<init>");
               }

            } else {
               throw new IllegalArgumentException("password cannot be null. Please provide a valid password");
            }
         } else {
            throw new IllegalArgumentException("username cannot be null. Please provide a valid username");
         }
      } else {
         throw new IllegalArgumentException("restURI cannot be null. Please provide a valid restURI");
      }
   }

   public final void close() {
      this.close0(true);
   }

   final void close0(boolean log) {
      if (log && this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(this.getClass().getName(), "close");
      }

      this.client.close0(log);

      try {
         Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
      } catch (IllegalStateException | SecurityException | IllegalArgumentException var3) {
      }

      if (log && this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(this.getClass().getName(), "close");
      }

   }

   public Properties getConfigurableAttributes() throws ProvisioningException {
      return this.getConfigurableAttributes((String)null, (Map)((Map)null));
   }

   public Properties getConfigurableAttributes(String provisioningComponentName) throws ProvisioningException {
      return this.getConfigurableAttributes(provisioningComponentName, (Map)null);
   }

   public Properties getConfigurableAttributes(String provisioningComponentName, Map provisioningOperationProperties) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "getConfigurableAttributes";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "getConfigurableAttributes", new Object[]{provisioningComponentName, provisioningOperationProperties});
      }

      JsonObject json = this.client.getConfigurableAttributes(provisioningComponentName, provisioningOperationProperties);

      assert json != null;

      Properties returnValue = getConfigurableAttributes(provisioningComponentName, json.getJsonArray("components"));
      if (returnValue == null) {
         returnValue = new Properties();
      }

      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "getConfigurableAttributes", returnValue);
      }

      return returnValue;
   }

   public final void provisionPartition(String partitionName, String provisioningComponentName) throws ProvisioningException {
      this.provisionPartition(provisioningComponentName, (Map)((Map)null), (Map)merge(partitionName, (Map)null));
   }

   public final void provisionPartition(String partitionName, String provisioningComponentName, Map configurableAttributeValues) throws ProvisioningException {
      this.provisionPartition(provisioningComponentName, (Map)configurableAttributeValues, (Map)merge(partitionName, (Map)null));
   }

   public final void provisionPartition(String partitionName, String provisioningComponentName, Map configurableAttributeValues, Map provisioningOperationProperties) throws ProvisioningException {
      this.provisionPartition(provisioningComponentName, (Map)configurableAttributeValues, (Map)merge(partitionName, provisioningOperationProperties));
   }

   public final void provisionPartition(String provisioningComponentName) throws ProvisioningException {
      this.provisionPartition(provisioningComponentName, (Map)null, (Map)null);
   }

   public final void provisionPartition(String provisioningComponentName, Map provisioningOperationProperties) throws ProvisioningException {
      this.provisionPartition(provisioningComponentName, (Map)null, provisioningOperationProperties);
   }

   public final void provisionPartition(String provisioningComponentName, Map configurableAttributeValues, Map provisioningOperationProperties) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "provisionPartition";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "provisionPartition", new Object[]{provisioningComponentName, configurableAttributeValues, provisioningOperationProperties});
      }

      Objects.requireNonNull(provisioningComponentName, "provisioningComponentName == null");
      JsonObject jsonStructure = (new ProvisioningOperationRequest(provisioningComponentName, configurableAttributeValues, provisioningOperationProperties)).toJson();

      assert jsonStructure != null;

      this.client.provisionPartition(jsonStructure);
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "provisionPartition");
      }

   }

   public final void deprovisionPartition(String partitionName, String provisioningComponentName) throws ProvisioningException {
      this.deprovisionPartition(provisioningComponentName, (Map)((Properties)null), (Map)merge(partitionName, (Map)null));
   }

   public final void deprovisionPartition(String partitionName, String provisioningComponentName, Map configurableAttributeValues) throws ProvisioningException {
      this.deprovisionPartition(provisioningComponentName, (Map)configurableAttributeValues, (Map)merge(partitionName, (Map)null));
   }

   public final void deprovisionPartition(String partitionName, String provisioningComponentName, Map configurableAttributeValues, Map provisioningOperationProperties) throws ProvisioningException {
      this.deprovisionPartition(provisioningComponentName, (Map)configurableAttributeValues, (Map)merge(partitionName, provisioningOperationProperties));
   }

   public final void deprovisionPartition(String provisioningComponentName, Map provisioningOperationProperties) throws ProvisioningException {
      this.deprovisionPartition(provisioningComponentName, (Map)null, provisioningOperationProperties);
   }

   public final void deprovisionPartition(String provisioningComponentName, Map configurableAttributeValues, Map provisioningOperationProperties) throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "deprovisionPartition";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "deprovisionPartition", new Object[]{provisioningComponentName, configurableAttributeValues, provisioningOperationProperties});
      }

      JsonObject jsonObject = (new ProvisioningOperationRequest(provisioningComponentName, configurableAttributeValues, provisioningOperationProperties)).toJson();

      assert jsonObject != null;

      this.client.deprovisionPartition(jsonObject);
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "deprovisionPartition");
      }

   }

   private final void connect() throws ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "connect";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "connect");
      }

      this.client.connect();

      try {
         Runtime.getRuntime().addShutdownHook(this.shutdownHook);
      } catch (IllegalStateException | SecurityException | IllegalArgumentException var4) {
      }

      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.exiting(cn, "connect");
      }

   }

   /** @deprecated */
   @Deprecated
   public final Properties getPartitionProvisioningConfigAttrs(String provisioningComponentName, String configFilename) throws IOException, ProvisioningException {
      String cn = this.getClass().getName();
      String mn = "getPartitionProvisioningConfigAttrs";
      if (this.logger.isLoggable(Level.FINER)) {
         this.logger.entering(cn, "getPartitionProvisioningConfigAttrs", new Object[]{provisioningComponentName, configFilename});
      }

      if (provisioningComponentName != null && !provisioningComponentName.isEmpty()) {
         if (configFilename != null && !configFilename.isEmpty()) {
            Properties configurableAttributes = this.getConfigurableAttributes(provisioningComponentName);

            assert configurableAttributes != null;

            if (configFilename != null) {
               StringBuilder sb = new StringBuilder();
               sb.append("Configurable attribute values for provisioning partitions with provisioning component ");
               sb.append(provisioningComponentName);
               Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFilename), StandardCharsets.UTF_8));
               Throwable var8 = null;

               try {
                  configurableAttributes.store(writer, sb.toString());
                  writer.flush();
               } catch (Throwable var17) {
                  var8 = var17;
                  throw var17;
               } finally {
                  if (writer != null) {
                     if (var8 != null) {
                        try {
                           writer.close();
                        } catch (Throwable var16) {
                           var8.addSuppressed(var16);
                        }
                     } else {
                        writer.close();
                     }
                  }

               }
            }

            if (this.logger.isLoggable(Level.FINER)) {
               this.logger.exiting(cn, "getPartitionProvisioningConfigAttrs", configurableAttributes);
            }

            return configurableAttributes;
         } else {
            throw new IllegalArgumentException("configFilename cannot be null. Please provide a valid configFilename");
         }
      } else {
         throw new IllegalArgumentException("provisioningComponentName cannot be null. Please provide a valid provisioningComponentName");
      }
   }

   /** @deprecated */
   @Deprecated
   public final void provisionPartition(String partitionName, String provisioningComponentName, String configFilename) throws IOException, ProvisioningException {
      this.provisionPartition(partitionName, provisioningComponentName, (String)configFilename, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public final void provisionPartition(String partitionName, String provisioningComponentName, String configurableAttributeValuesFileName, String propertiesFilename) throws IOException, ProvisioningException {
      Objects.requireNonNull(provisioningComponentName, "provisioningComponentName == null");
      Objects.requireNonNull(configurableAttributeValuesFileName, "configurableAttributeValuesFileName == null");
      Properties configurableAttributeValues = readPropertiesFromFile(configurableAttributeValuesFileName);
      Properties provisioningOperationPropertyValues = null;
      if (propertiesFilename != null) {
         provisioningOperationPropertyValues = readPropertiesFromFile(propertiesFilename);
      }

      provisioningOperationPropertyValues = merge(partitionName, provisioningOperationPropertyValues);

      assert provisioningOperationPropertyValues != null;

      partitionName = provisioningOperationPropertyValues.getProperty("wlsPartitionName");
      Objects.requireNonNull(partitionName, "partitionName == null");
      this.provisionPartition(partitionName, provisioningComponentName, (Map)configurableAttributeValues, (Map)provisioningOperationPropertyValues);
   }

   /** @deprecated */
   @Deprecated
   public void deprovisionPartition(String partitionName, String provisioningComponentName, String configurableAttributeValuesFileName) throws IOException, ProvisioningException {
      this.deprovisionPartition(partitionName, provisioningComponentName, (String)configurableAttributeValuesFileName, (String)null);
   }

   /** @deprecated */
   @Deprecated
   public void deprovisionPartition(String partitionName, String provisioningComponentName, String configurableAttributeValuesFileName, String propertiesFilename) throws IOException, ProvisioningException {
      Properties configurableAttributeValues = null;
      if (configurableAttributeValuesFileName != null && !configurableAttributeValuesFileName.isEmpty()) {
         configurableAttributeValues = readPropertiesFromFile(configurableAttributeValuesFileName);
      }

      if (configurableAttributeValues == null) {
         configurableAttributeValues = new Properties();
      }

      Properties provisioningOperationPropertyValues = null;
      if (propertiesFilename != null) {
         provisioningOperationPropertyValues = readPropertiesFromFile(propertiesFilename);
      }

      provisioningOperationPropertyValues = merge(partitionName, provisioningOperationPropertyValues);

      assert provisioningOperationPropertyValues != null;

      partitionName = provisioningOperationPropertyValues.getProperty("wlsPartitionName");
      Objects.requireNonNull(partitionName, "partitionName == null");
      this.deprovisionPartition(provisioningComponentName, (Map)configurableAttributeValues, (Map)provisioningOperationPropertyValues);
   }

   static final Properties getConfigurableAttributes(String provisioningComponentName, JsonArray jsonArray) {
      String cn = ProvisioningWLSTCLI.class.getName();
      String mn = "getConfigurableAttributes";
      Logger logger = Logger.getLogger(cn);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(cn, "getConfigurableAttributes", new Object[]{provisioningComponentName, jsonArray});
      }

      Properties returnValue = new Properties();
      if (jsonArray != null) {
         Collection elements = jsonArray.getValuesAs(JsonObject.class);
         if (elements != null && !elements.isEmpty()) {
            Iterator var7 = elements.iterator();

            label71:
            do {
               JsonObject element;
               do {
                  do {
                     if (!var7.hasNext()) {
                        break label71;
                     }

                     element = (JsonObject)var7.next();
                  } while(element == null);
               } while(provisioningComponentName != null && !provisioningComponentName.equals(element.getString("name")));

               JsonArray configurableAttributesArray = element.getJsonArray("configurableAttributes");
               if (configurableAttributesArray != null) {
                  Properties properties = (new GetConfigurableAttributesResponse(configurableAttributesArray)).toProperties();

                  assert properties != null;

                  Collection propertyNames = properties.stringPropertyNames();
                  if (propertyNames != null && !propertyNames.isEmpty()) {
                     Iterator var12 = propertyNames.iterator();

                     while(var12.hasNext()) {
                        String propertyName = (String)var12.next();

                        assert propertyName != null;

                        returnValue.setProperty(propertyName, properties.getProperty(propertyName));
                     }
                  }
               }
            } while(provisioningComponentName == null);
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(cn, "getConfigurableAttributes", returnValue);
      }

      return returnValue;
   }

   private static final Properties copy(Map map) {
      Properties returnValue = new Properties();
      if (map instanceof Properties) {
         Properties properties = (Properties)map;
         Collection propertyNames = properties.stringPropertyNames();
         if (propertyNames != null && !propertyNames.isEmpty()) {
            Iterator var4 = propertyNames.iterator();

            while(var4.hasNext()) {
               String propertyName = (String)var4.next();

               assert propertyName != null;

               returnValue.setProperty(propertyName, properties.getProperty(propertyName));
            }
         }
      } else if (map != null && !map.isEmpty()) {
         Collection entries = map.entrySet();
         if (entries != null && !entries.isEmpty()) {
            Iterator var8 = entries.iterator();

            while(var8.hasNext()) {
               Map.Entry entry = (Map.Entry)var8.next();
               if (entry != null) {
                  Object key = entry.getKey();
                  if (key instanceof String) {
                     Object value = entry.getValue();
                     if (value == null) {
                        returnValue.setProperty((String)key, (String)null);
                     } else {
                        returnValue.setProperty((String)key, value.toString());
                     }
                  }
               }
            }
         }
      }

      return returnValue;
   }

   public static final Properties merge(String partitionName, Map map) {
      Properties returnValue = null;
      Object o;
      if (partitionName == null) {
         if (map == null) {
            throw new NullPointerException("partitionName == null");
         }

         boolean mapIsInstanceOfProperties = map instanceof Properties;
         if (mapIsInstanceOfProperties) {
            o = ((Properties)map).getProperty("wlsPartitionName");
         } else {
            o = map.get("wlsPartitionName");
         }

         if (o == null) {
            throw new NullPointerException("partitionName == null");
         }

         if (!(o instanceof String)) {
            returnValue = copy(map);

            assert returnValue != null;

            returnValue.setProperty("wlsPartitionName", o.toString());
         } else if (mapIsInstanceOfProperties) {
            returnValue = (Properties)map;
         } else {
            returnValue = copy(map);

            assert returnValue != null;
         }
      } else if (map == null) {
         returnValue = copy(map);

         assert returnValue != null;

         returnValue.setProperty("wlsPartitionName", partitionName);
      } else if (map instanceof Properties) {
         if (!partitionName.equals(((Properties)map).getProperty("wlsPartitionName"))) {
            returnValue = copy(map);

            assert returnValue != null;

            returnValue.setProperty("wlsPartitionName", partitionName);
         } else {
            returnValue = (Properties)map;
         }
      } else {
         o = map.get("wlsPartitionName");
         String value;
         if (o == null) {
            value = null;
         } else {
            value = o.toString();
         }

         if (!partitionName.equals(value)) {
            returnValue = copy(map);

            assert returnValue != null;

            returnValue.setProperty("wlsPartitionName", partitionName);
         }
      }

      assert returnValue != null;

      if (!$assertionsDisabled) {
         if (partitionName == null) {
            if (returnValue.getProperty("wlsPartitionName") == null) {
               throw new AssertionError("merge() is misbehaving");
            }
         } else if (!partitionName.equals(returnValue.getProperty("wlsPartitionName"))) {
            throw new AssertionError("merge() is misbehaving");
         }
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public static final RuntimeException toRuntimeException(URI uri, ProvisioningException caughtException) {
      Object returnValue;
      if (caughtException == null) {
         returnValue = null;
      } else {
         Throwable cause = caughtException.getCause();
         if (cause instanceof NotAuthorizedException) {
            returnValue = new IllegalArgumentException("Invalid username or password", caughtException);
         } else {
            Response response;
            if (cause instanceof ResponseProcessingException) {
               response = ((ResponseProcessingException)cause).getResponse();
            } else if (cause instanceof WebApplicationException) {
               response = ((WebApplicationException)cause).getResponse();
            } else {
               response = null;
            }

            if (response == null) {
               if (cause instanceof ProcessingException && !(cause instanceof ResponseProcessingException)) {
                  returnValue = new RuntimeException("Exception talking to REST endpoint. " + cause.getMessage(), caughtException);
               } else {
                  returnValue = new RuntimeException("Error connecting to " + uri + ": Response Status = null", caughtException);
               }
            } else if (uri != null) {
               returnValue = new RuntimeException("Error connecting to " + uri + ": Response Status = " + Status.fromStatusCode(response.getStatus()), caughtException);
            } else {
               StringBuilder sb = new StringBuilder("HTTP Response Status = ");
               int statusCode = response.getStatus();
               sb.append(statusCode);
               sb.append(", ");
               sb.append(Status.fromStatusCode(statusCode));
               sb.append(" Server Error = ");
               sb.append(caughtException.getMessage());
               returnValue = new RuntimeException(sb.toString(), caughtException);
            }
         }
      }

      return (RuntimeException)returnValue;
   }

   private static final Properties readPropertiesFromFile(String propertiesFilename) throws IOException {
      Properties properties = new Properties();
      if (propertiesFilename != null) {
         Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(propertiesFilename), StandardCharsets.UTF_8));
         Throwable var3 = null;

         try {
            properties.load(reader);
         } catch (Throwable var12) {
            var3 = var12;
            throw var12;
         } finally {
            if (reader != null) {
               if (var3 != null) {
                  try {
                     reader.close();
                  } catch (Throwable var11) {
                     var3.addSuppressed(var11);
                  }
               } else {
                  reader.close();
               }
            }

         }
      }

      Collection propertyNames = properties.stringPropertyNames();
      if (propertyNames != null && !propertyNames.isEmpty()) {
         return properties;
      } else {
         throw new EmptyPropertiesException();
      }
   }

   private static final class ShutdownHook extends Thread {
      private final ProvisioningWLSTCLI closeable;

      private ShutdownHook(ProvisioningWLSTCLI closeable) {
         this.closeable = closeable;
      }

      public final void run() {
         if (this.closeable != null) {
            try {
               this.closeable.close0(false);
            } catch (Exception var2) {
            }
         }

      }

      // $FF: synthetic method
      ShutdownHook(ProvisioningWLSTCLI x0, Object x1) {
         this(x0);
      }
   }
}

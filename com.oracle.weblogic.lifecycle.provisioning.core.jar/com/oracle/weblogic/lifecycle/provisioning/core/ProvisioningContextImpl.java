package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentIdentifier;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentRepository;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperationDescriptor;
import com.oracle.weblogic.lifecycle.provisioning.api.WebLogicPartitionProvisioningContext;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.hk2.api.PostConstruct;
import org.glassfish.hk2.api.PreDestroy;
import org.glassfish.hk2.api.ProxyCtl;
import org.glassfish.hk2.extras.operation.OperationHandle;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;

@ProvisioningOperationScoped
@Service
public class ProvisioningContextImpl implements ConfigurableAttributeValueProvider, PostConstruct, PreDestroy, ProvisioningOperationPropertyValueProvider, Serializable, WebLogicPartitionProvisioningContext {
   private static final long serialVersionUID = 1L;
   private final transient OperationHandle operationHandle;
   private transient volatile ProvisioningComponentIdentifier currentProvisionerComponentId;
   private transient volatile ProvisioningComponentIdentifier currentProvisioningComponentId;
   private final HashMap contextDatas;
   private final Set affectedComponentIds;
   private transient Deque documentQueue;
   private volatile ConfigurableAttributeValueProvider defaultConfigurableAttributeValueProvider;
   private transient volatile ProvisioningOperation provisioningOperation;
   private transient volatile Exception exception;
   private transient Provider registryProvider;

   public ProvisioningContextImpl() {
      this((Provider)null, (OperationHandle)null);
   }

   @Inject
   public ProvisioningContextImpl(@Optional Provider registryProvider, @Optional OperationHandle operationHandle) {
      this.affectedComponentIds = new CopyOnWriteArraySet();
      this.contextDatas = new HashMap();
      this.documentQueue = new ArrayDeque();
      this.operationHandle = operationHandle;
      this.registryProvider = registryProvider;
   }

   public ProvisioningContextImpl(@Optional OperationHandle operationHandle) {
      this((Provider)null, operationHandle);
   }

   public void postConstruct() {
      if (this.operationHandle != null) {
         this.operationHandle.setOperationData(this);
      }

   }

   public void preDestroy() {
      if (this.operationHandle != null) {
         try {
            if (this == this.operationHandle.getOperationData()) {
               this.operationHandle.setOperationData((Object)null);
            }
         } catch (IllegalStateException var2) {
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public void initialize(Map configuredValues, String partitionName, ProvisioningOperationDescriptor provisioningOperationDescriptor) {
      Map provisioningOperationPropertyValues = new HashMap();
      provisioningOperationPropertyValues.put("wlsPartitionName", partitionName);
      ProvisioningOperation provisioningOperation = new ProvisioningOperation((Set)null, provisioningOperationDescriptor, configuredValues, provisioningOperationPropertyValues);
      this.initialize((ProvisioningOperation)provisioningOperation, (ConfigurableAttributeValueProvider)null, (Deque)null);
   }

   /** @deprecated */
   @Deprecated
   public void initialize(Map provisioningOperationPropertyValues, Map configuredValues, ProvisioningOperationDescriptor provisioningOperationDescriptor) {
      ProvisioningOperation provisioningOperation = new ProvisioningOperation((Set)null, provisioningOperationDescriptor, configuredValues, provisioningOperationPropertyValues);
      this.initialize((ProvisioningOperation)provisioningOperation, (ConfigurableAttributeValueProvider)null, (Deque)null);
   }

   /** @deprecated */
   @Deprecated
   public void initialize(Map provisioningOperationPropertyValues, Map configuredValues, ConfigurableAttributeValueProvider defaultConfigurableAttributeValueProvider, ProvisioningOperationDescriptor provisioningOperationDescriptor) {
      ProvisioningOperation provisioningOperation = new ProvisioningOperation((Set)null, provisioningOperationDescriptor, configuredValues, provisioningOperationPropertyValues);
      this.initialize((ProvisioningOperation)provisioningOperation, (ConfigurableAttributeValueProvider)defaultConfigurableAttributeValueProvider, (Deque)null);
   }

   /** @deprecated */
   @Deprecated
   public void initialize(Map provisioningOperationPropertyValues, Map configuredValues, ConfigurableAttributeValueProvider defaultConfigurableAttributeValueProvider, Deque documentQueue, ProvisioningOperationDescriptor provisioningOperationDescriptor) {
      this.initialize(new ProvisioningOperation((Set)null, provisioningOperationDescriptor, configuredValues, provisioningOperationPropertyValues), defaultConfigurableAttributeValueProvider, documentQueue);
   }

   public void initialize(ProvisioningOperation provisioningOperation, ConfigurableAttributeValueProvider defaultConfigurableAttributeValueProvider, Deque documentQueue) {
      String className = ProvisioningContextImpl.class.getName();
      String methodName = "initialize";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "initialize", new Object[]{provisioningOperation, defaultConfigurableAttributeValueProvider, documentQueue});
      }

      if (this.documentQueue != null && !this.documentQueue.isEmpty()) {
         throw new IllegalStateException("this.documentQueue != null && !this.documentQueue.isEmpty(): " + this.documentQueue);
      } else {
         if (provisioningOperation != null) {
            ProvisioningOperation oldProvisioningOperation = this.getProvisioningOperation();
            if (oldProvisioningOperation != null) {
               throw new IllegalStateException("this.getProvisioningOperation() != null: " + oldProvisioningOperation);
            }
         }

         if (defaultConfigurableAttributeValueProvider != null) {
            if (this.defaultConfigurableAttributeValueProvider != null) {
               throw new IllegalStateException("this.defaultConfigurableAttributeValueProvider != null: " + this.defaultConfigurableAttributeValueProvider);
            }

            if (defaultConfigurableAttributeValueProvider == this) {
               throw new IllegalArgumentException("defaultConfigurableAttributeValueProvider == this: " + this);
            }

            if (defaultConfigurableAttributeValueProvider instanceof ProxyCtl) {
               defaultConfigurableAttributeValueProvider = (ConfigurableAttributeValueProvider)((ProxyCtl)defaultConfigurableAttributeValueProvider).__make();

               assert defaultConfigurableAttributeValueProvider != null : "((ProxyCtl)defaultConfigurableAttributeValueProvider).__make() == null";
            }

            if (defaultConfigurableAttributeValueProvider == this) {
               throw new IllegalArgumentException("defaultConfigurableAttributeValueProvider == this: " + this);
            }
         }

         this.setCurrentProvisioningComponentId((ProvisioningComponentIdentifier)null);
         this.setCurrentProvisionerComponentId((ProvisioningComponentIdentifier)null);
         this.contextDatas.clear();
         this.provisioningOperation = provisioningOperation;
         this.defaultConfigurableAttributeValueProvider = defaultConfigurableAttributeValueProvider;
         this.documentQueue = documentQueue;
         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "initialize");
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public void setDefaultConfigurableAttributeValueProvider(ConfigurableAttributeValueProvider defaultConfigurableAttributeValueProvider) {
      if (this.defaultConfigurableAttributeValueProvider != null) {
         throw new IllegalStateException("this.defaultConfigurableAttributeValueProvider != null: " + this.defaultConfigurableAttributeValueProvider);
      } else {
         if (defaultConfigurableAttributeValueProvider instanceof ProxyCtl) {
            defaultConfigurableAttributeValueProvider = (ConfigurableAttributeValueProvider)((ProxyCtl)defaultConfigurableAttributeValueProvider).__make();

            assert defaultConfigurableAttributeValueProvider != null : "((ProxyCtl)defaultConfigurableAttributeValueProvider).__make() == null";
         }

         this.defaultConfigurableAttributeValueProvider = defaultConfigurableAttributeValueProvider;
      }
   }

   public boolean containsConfigurableAttributeValue(String namespace, String attributeName) {
      String className = ProvisioningContextImpl.class.getName();
      String methodName = "containsConfigurableAttributeValue";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "containsConfigurableAttributeValue", new Object[]{namespace, attributeName});
      }

      ProvisioningOperation provisioningOperation = this.getProvisioningOperation();
      boolean returnValue;
      if (provisioningOperation != null && provisioningOperation.containsConfigurableAttributeValue(namespace, attributeName)) {
         returnValue = true;
      } else if (this.defaultConfigurableAttributeValueProvider != null) {
         returnValue = this.defaultConfigurableAttributeValueProvider.containsConfigurableAttributeValue(namespace, attributeName);
      } else {
         returnValue = false;
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "containsConfigurableAttributeValue", returnValue);
      }

      return returnValue;
   }

   public String getConfigurableAttributeValue(String namespace, String attributeName) {
      String className = ProvisioningContextImpl.class.getName();
      String methodName = "getConfigurableAttributeValue";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConfigurableAttributeValue", new Object[]{namespace, attributeName});
      }

      ProvisioningOperation provisioningOperation = this.getProvisioningOperation();
      String returnValue;
      if (provisioningOperation != null && provisioningOperation.containsConfigurableAttributeValue(namespace, attributeName)) {
         returnValue = provisioningOperation.getConfigurableAttributeValue(namespace, attributeName);
      } else if (this.defaultConfigurableAttributeValueProvider != null && this.defaultConfigurableAttributeValueProvider.containsConfigurableAttributeValue(namespace, attributeName)) {
         returnValue = this.defaultConfigurableAttributeValueProvider.getConfigurableAttributeValue(namespace, attributeName);
      } else {
         returnValue = null;
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getConfigurableAttributeValue", returnValue);
      }

      return returnValue;
   }

   public String getProvisioningOperationPropertyValue(String name) {
      String className = ProvisioningContextImpl.class.getName();
      String methodName = "getProvisioningOperationPropertyValue";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getProvisioningOperationPropertyValue", name);
      }

      String returnValue;
      if (name == null) {
         returnValue = null;
      } else {
         ProvisioningOperation provisioningOperation = this.getProvisioningOperation();
         if (provisioningOperation == null) {
            returnValue = null;
         } else {
            returnValue = provisioningOperation.getProperty(name);
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getProvisioningOperationPropertyValue", returnValue);
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public String getCurrentProvisioningComponentName() {
      ProvisioningComponentIdentifier id = this.getCurrentProvisioningComponentId();
      String returnValue;
      if (id == null) {
         returnValue = null;
      } else {
         returnValue = id.getName();
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   void setCurrentProvisioningComponentName(String currentProvisioningComponentName) {
      if (currentProvisioningComponentName == null) {
         this.setCurrentProvisioningComponentId((ProvisioningComponentIdentifier)null);
      } else {
         this.setCurrentProvisioningComponentId(new ProvisioningComponentIdentifier(currentProvisioningComponentName));
      }

   }

   public ProvisioningComponentIdentifier getCurrentProvisioningComponentId() {
      return this.currentProvisioningComponentId;
   }

   public void setCurrentProvisioningComponentId(ProvisioningComponentIdentifier id) {
      this.currentProvisioningComponentId = id;
   }

   /** @deprecated */
   @Deprecated
   public String getCurrentProvisionerComponentName() {
      ProvisioningComponentIdentifier id = this.getCurrentProvisionerComponentId();
      String returnValue;
      if (id == null) {
         returnValue = null;
      } else {
         returnValue = id.getName();
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public void setCurrentProvisionerComponentName(String name) {
      if (name == null) {
         this.setCurrentProvisionerComponentId((ProvisioningComponentIdentifier)null);
      } else {
         this.setCurrentProvisionerComponentId(new ProvisioningComponentIdentifier(name));
      }

   }

   public ProvisioningComponentIdentifier getCurrentProvisionerComponentId() {
      return this.currentProvisionerComponentId;
   }

   public void setCurrentProvisionerComponentId(ProvisioningComponentIdentifier id) {
      this.currentProvisionerComponentId = id;
   }

   public ProvisioningOperation getProvisioningOperation() {
      return this.provisioningOperation;
   }

   public ProvisioningOperationDescriptor getProvisioningOperationDescriptor() {
      ProvisioningOperation provisioningOperation = this.getProvisioningOperation();
      ProvisioningOperationDescriptor returnValue;
      if (provisioningOperation == null) {
         returnValue = null;
      } else {
         returnValue = provisioningOperation.getProvisioningOperationDescriptor();
      }

      return returnValue;
   }

   public String getPartitionName() {
      return this.getProvisioningOperationPropertyValue("wlsPartitionName");
   }

   public Exception getException() {
      return this.exception;
   }

   public void setException(Exception exception) {
      this.exception = exception;
   }

   /** @deprecated */
   @Deprecated
   public Set getAffectedComponentNames() {
      Set ids = this.getAffectedComponentIds();
      Set returnValue = new HashSet();
      if (ids != null && !ids.isEmpty()) {
         Iterator var3 = ids.iterator();

         while(var3.hasNext()) {
            ProvisioningComponentIdentifier id = (ProvisioningComponentIdentifier)var3.next();
            if (id == null) {
               returnValue.add((Object)null);
            } else {
               returnValue.add(id.getName());
            }
         }
      }

      return Collections.unmodifiableSet(returnValue);
   }

   public Set getAffectedComponentIds() {
      assert this.affectedComponentIds != null;

      return Collections.unmodifiableSet(this.affectedComponentIds);
   }

   /** @deprecated */
   @Deprecated
   public void addAffectedComponentName(String name) {
      if (name != null) {
         this.addAffectedComponentId(new ProvisioningComponentIdentifier(name));
      }

   }

   public void addAffectedComponentId(ProvisioningComponentIdentifier id) {
      if (id != null) {
         assert this.affectedComponentIds != null;

         this.affectedComponentIds.add(id);
      }

   }

   public void publish(Document document) {
      if (document != null) {
         if (this.documentQueue == null) {
            this.documentQueue = new ArrayDeque();
         }

         this.documentQueue.addLast(document);
      }

   }

   public Document peekFirstDocument() {
      return this.documentQueue == null ? null : (Document)this.documentQueue.peek();
   }

   public Document removeFirstDocument() {
      Document returnValue;
      if (this.documentQueue != null && !this.documentQueue.isEmpty()) {
         returnValue = (Document)this.documentQueue.removeFirst();
      } else {
         returnValue = null;
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public Collection getDocumentQueue() {
      return this.documentQueue;
   }

   public Map getContextData() {
      ProvisioningComponentIdentifier currentProvisioningComponent = this.getCurrentProvisioningComponentId();
      Map result = (Map)this.contextDatas.get(currentProvisioningComponent);
      if (result == null) {
         result = new HashMap();
         this.contextDatas.put(currentProvisioningComponent, result);
      }

      return (Map)result;
   }

   Object writeReplace() throws ObjectStreamException {
      ProvisioningContextImpl returnValue;
      if (this instanceof ProxyCtl) {
         returnValue = (ProvisioningContextImpl)((ProxyCtl)this).__make();
      } else {
         returnValue = this;
      }

      return returnValue;
   }

   private void readObject(ObjectInputStream stream) throws ClassNotFoundException, IOException {
      if (stream != null) {
         stream.defaultReadObject();
      }

      ProvisioningOperationDescriptor provisioningOperationDescriptor = (ProvisioningOperationDescriptor)stream.readObject();
      Object o = stream.readObject();
      Object ids;
      if (o == null) {
         ids = null;
      } else {
         if (!(o instanceof Set)) {
            throw new IOException("Unexpected object read immediately after ProvisioningOperationDescriptor read in " + this.getClass().getName() + "#readObject(): " + o);
         }

         Set stuff = (Set)o;
         Object prototype = null;
         Iterator var7 = stuff.iterator();

         while(var7.hasNext()) {
            Object thing = var7.next();
            if (thing != null) {
               prototype = thing;
               break;
            }
         }

         Set names;
         if (prototype instanceof String) {
            names = (Set)o;
            ids = new HashSet();
            if (!names.isEmpty()) {
               Iterator var13 = names.iterator();

               while(var13.hasNext()) {
                  String name = (String)var13.next();
                  if (name != null) {
                     ((Set)ids).add(new ProvisioningComponentIdentifier(name));
                  }
               }
            }
         } else if (prototype instanceof ProvisioningComponentIdentifier) {
            names = (Set)o;
            ids = names;
         } else {
            if (prototype != null) {
               throw new IOException("Unexpected object read immediately after ProvisioningOperationDescriptor read in " + this.getClass().getName() + "#readObject(): " + o);
            }

            ids = Collections.emptySet();
         }
      }

      Map properties = (Map)stream.readObject();
      Map configurableAttributeValues = (Map)stream.readObject();
      this.provisioningOperation = new ProvisioningOperation((Set)ids, provisioningOperationDescriptor, configurableAttributeValues, properties);
      this.documentQueue = new ArrayDeque();
   }

   private void writeObject(ObjectOutputStream stream) throws IOException {
      if (stream != null) {
         stream.defaultWriteObject();
         ProvisioningOperation original = this.getProvisioningOperation();
         if (original == null) {
            stream.writeObject((Object)null);
            stream.writeObject((Object)null);
            stream.writeObject((Object)null);
         } else {
            stream.writeObject(original.getProvisioningOperationDescriptor());
            stream.writeObject(original.getProvisioningComponentIds());
            stream.writeObject(original.getProperties());
         }

         if (original == null) {
            stream.writeObject((Object)null);
         } else if (this.registryProvider == null) {
            stream.writeObject(original.getConfigurableAttributeValues());
         } else {
            ProvisioningComponentRepository registry = (ProvisioningComponentRepository)this.registryProvider.get();
            if (registry == null) {
               stream.writeObject(original.getConfigurableAttributeValues());
            } else {
               Map originalConfigurableAttributeValues = original.getConfigurableAttributeValues();
               if (originalConfigurableAttributeValues != null && !originalConfigurableAttributeValues.isEmpty()) {
                  Map newConfigurableAttributeValues = new HashMap();
                  Set originalConfigurableAttributeEntries = originalConfigurableAttributeValues.entrySet();
                  if (originalConfigurableAttributeEntries != null && !originalConfigurableAttributeEntries.isEmpty()) {
                     Iterator var7 = originalConfigurableAttributeEntries.iterator();

                     label92:
                     while(true) {
                        String componentName;
                        Set originalComponentValuesEntries;
                        do {
                           do {
                              Map originalComponentValues;
                              do {
                                 do {
                                    Map.Entry originalConfigurableAttributeEntry;
                                    do {
                                       if (!var7.hasNext()) {
                                          break label92;
                                       }

                                       originalConfigurableAttributeEntry = (Map.Entry)var7.next();
                                    } while(originalConfigurableAttributeEntry == null);

                                    componentName = (String)originalConfigurableAttributeEntry.getKey();
                                    originalComponentValues = (Map)originalConfigurableAttributeEntry.getValue();
                                 } while(originalComponentValues == null);
                              } while(originalComponentValues.isEmpty());

                              originalComponentValuesEntries = originalComponentValues.entrySet();
                           } while(originalComponentValuesEntries == null);
                        } while(originalComponentValuesEntries.isEmpty());

                        Map newComponentValues = new HashMap();
                        Iterator var13 = originalComponentValuesEntries.iterator();

                        while(true) {
                           Map.Entry originalComponentValuesEntry;
                           String attributeName;
                           ConfigurableAttribute ca;
                           do {
                              do {
                                 if (!var13.hasNext()) {
                                    if (!newComponentValues.isEmpty()) {
                                       newConfigurableAttributeValues.put(componentName, newComponentValues);
                                    }
                                    continue label92;
                                 }

                                 originalComponentValuesEntry = (Map.Entry)var13.next();
                              } while(originalComponentValuesEntry == null);

                              attributeName = (String)originalComponentValuesEntry.getKey();
                              ca = null;

                              try {
                                 ca = registry.getConfigurableAttribute(componentName, attributeName);
                              } catch (ProvisioningException var18) {
                                 throw new IOException(var18);
                              }
                           } while(ca != null && ca.isSensitive());

                           newComponentValues.put(attributeName, originalComponentValuesEntry.getValue());
                        }
                     }
                  }

                  stream.writeObject(newConfigurableAttributeValues);
               } else {
                  stream.writeObject(originalConfigurableAttributeValues);
               }
            }
         }
      }

   }
}

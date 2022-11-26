package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeProvider;
import com.oracle.weblogic.lifecycle.provisioning.api.Executor;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentContainer;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentIdentifier;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningContext;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperationDescriptor;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperationExecutor;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperationScopedLiteral;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningResource;
import com.oracle.weblogic.lifecycle.provisioning.api.TransactionalHandler;
import com.oracle.weblogic.lifecycle.provisioning.api.TransformationException;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.DeprovisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.InitialProvisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.spi.ProvisioningComponent;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;
import javax.xml.parsers.ParserConfigurationException;
import org.glassfish.hk2.api.AnnotationLiteral;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.ProxyCtl;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.messaging.Topic;
import org.glassfish.hk2.extras.ExtrasUtilities;
import org.glassfish.hk2.extras.operation.OperationHandle;
import org.glassfish.hk2.extras.operation.OperationManager;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.SAXException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.RuntimeAccess;

@Service
@Singleton
public class PartitionProvisioner implements ConfigurableAttributeProvider, Executor, ProvisioningOperationExecutor {
   private static final InitialProvisioningOperation INITIAL_PROVISIONING_OPERATION_LITERAL = new InitialProvisioningOperationLiteral();
   private static final DeprovisioningOperation DEPROVISIONING_OPERATION_LITERAL = new DeprovisioningOperationLiteral();
   private final OperationManager operationManager;
   private final ServiceLocator serviceLocator;
   private final DocumentTransformingDocumentFactory documentFactory;
   private final ComponentMetadataRegistry registry;
   private final ProvisioningComponentContainer provisioningComponentContainer;
   private final Provider provisioningContextProvider;
   private final ProvisioningContext.Serializer serializer;
   private final Topic provisioningOperationCompletionTopic;
   private final Provider runtimeAccessProvider;
   private final Validator validator;

   /** @deprecated */
   @Deprecated
   public PartitionProvisioner(ServiceLocator serviceLocator, DocumentTransformingDocumentFactory documentFactory, ComponentMetadataRegistry registry, Provider runtimeAccessProvider, Provider provisioningContextProvider, @Optional Validator validator, @Named("com.oracle.weblogic.lifecycle.provisioning.core.PartitionProvisioner") Provider ignoredPersistentMapProvider, Topic provisioningOperationCompletionTopic) {
      this(serviceLocator, documentFactory, registry, runtimeAccessProvider, provisioningContextProvider, (ProvisioningContext.Serializer)null, (Validator)validator, provisioningOperationCompletionTopic);
   }

   public PartitionProvisioner(ServiceLocator serviceLocator, DocumentTransformingDocumentFactory documentFactory, ComponentMetadataRegistry registry, Provider runtimeAccessProvider, Provider provisioningContextProvider, @Optional ProvisioningContext.Serializer serializer, @Optional Validator validator, Topic provisioningOperationCompletionTopic) {
      this(serviceLocator, documentFactory, registry, registry, runtimeAccessProvider, provisioningContextProvider, serializer, validator, provisioningOperationCompletionTopic);
   }

   @Inject
   public PartitionProvisioner(ServiceLocator serviceLocator, DocumentTransformingDocumentFactory documentFactory, ComponentMetadataRegistry registry, ProvisioningComponentContainer provisioningComponentContainer, Provider runtimeAccessProvider, Provider provisioningContextProvider, @Optional ProvisioningContext.Serializer serializer, @Optional Validator validator, Topic provisioningOperationCompletionTopic) {
      Objects.requireNonNull(serviceLocator);
      Objects.requireNonNull(documentFactory);
      Objects.requireNonNull(registry);
      Objects.requireNonNull(provisioningComponentContainer);
      Objects.requireNonNull(runtimeAccessProvider);
      Objects.requireNonNull(provisioningContextProvider);
      Objects.requireNonNull(provisioningOperationCompletionTopic);
      this.serviceLocator = serviceLocator;
      ExtrasUtilities.enableOperations(serviceLocator);
      ExtrasUtilities.enableTopicDistribution(serviceLocator);
      this.operationManager = (OperationManager)serviceLocator.getService(OperationManager.class, new Annotation[0]);

      assert this.operationManager != null;

      this.documentFactory = documentFactory;
      this.registry = registry;
      this.provisioningComponentContainer = provisioningComponentContainer;
      this.runtimeAccessProvider = runtimeAccessProvider;
      this.provisioningContextProvider = provisioningContextProvider;
      this.serializer = serializer;
      this.validator = validator;
      this.provisioningOperationCompletionTopic = provisioningOperationCompletionTopic;
   }

   /** @deprecated */
   @Deprecated
   public Map getConfigurableAttributesFor(ProvisioningOperationDescriptor provisioningOperationDescriptor, String componentName) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "getConfigurableAttributesFor";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConfigurableAttributesFor", componentName);
      }

      Objects.requireNonNull(provisioningOperationDescriptor);
      Objects.requireNonNull(componentName);
      Map returnValue = new HashMap();
      ProvisioningOperation provisioningOperation = new ProvisioningOperation(provisioningOperationDescriptor, Collections.singleton(componentName));
      Map attributes = this.getConfigurableAttributes(provisioningOperation);
      if (attributes != null && !attributes.isEmpty()) {
         Set entrySet = attributes.entrySet();
         if (entrySet != null && !entrySet.isEmpty()) {
            Iterator var10 = entrySet.iterator();

            while(var10.hasNext()) {
               Map.Entry entry = (Map.Entry)var10.next();
               if (entry != null) {
                  String key = (String)entry.getKey();
                  Collection value = (Collection)entry.getValue();
                  returnValue.put(key, value);
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConfigurableAttributesFor", returnValue);
      }

      return returnValue;
   }

   public Map getConfigurableAttributes(ProvisioningOperation provisioningOperation) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "getConfigurableAttributes";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConfigurableAttributes", provisioningOperation);
      }

      Objects.requireNonNull(provisioningOperation);
      Map returnValue = new HashMap();
      Collection provisioningSequence = provisioningOperation.getProvisioningSequence();
      if (provisioningSequence != null && !provisioningSequence.isEmpty()) {
         assert this.registry != null : "this.registry == null";

         ConfigurableAttributeValueProvider defaultValueProvider;
         if (this.serializer == null) {
            defaultValueProvider = null;
         } else {
            defaultValueProvider = (ConfigurableAttributeValueProvider)this.serializer.deserialize(provisioningOperation);
         }

         Iterator var8 = provisioningSequence.iterator();

         label69:
         while(true) {
            String provisioningSequenceElement;
            Collection componentAttributes;
            do {
               do {
                  do {
                     if (!var8.hasNext()) {
                        break label69;
                     }

                     provisioningSequenceElement = (String)var8.next();
                  } while(provisioningSequenceElement == null);

                  componentAttributes = this.registry.getConfigurableAttributes(provisioningSequenceElement);
               } while(componentAttributes == null);
            } while(componentAttributes.isEmpty());

            Collection componentAttributesWithDefaultValues = new ArrayList();
            Iterator var12 = componentAttributes.iterator();

            while(var12.hasNext()) {
               ConfigurableAttribute ca = (ConfigurableAttribute)var12.next();
               if (ca != null) {
                  ca = ConfigurableAttributes.installDefaultValue(ca, provisioningSequenceElement, ca.name(), defaultValueProvider);

                  assert ca != null;

                  componentAttributesWithDefaultValues.add(ca);
               }
            }

            returnValue.put(provisioningSequenceElement, Collections.unmodifiableCollection(componentAttributesWithDefaultValues));
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getConfigurableAttributes", returnValue);
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public boolean partitionExists(String partitionName) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "partitionExists";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "partitionExists", partitionName);
      }

      boolean returnValue;
      if (partitionName == null) {
         returnValue = false;
      } else {
         assert this.runtimeAccessProvider != null : "this.runtimeAccessProvider == null";

         RuntimeAccess runtimeAccess = (RuntimeAccess)this.runtimeAccessProvider.get();

         assert runtimeAccess != null;

         DomainMBean domain = runtimeAccess.getDomain();
         if (domain == null) {
            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.logp(Level.FINER, className, "partitionExists", "RuntimeAccess.getDomain() returned null");
            }

            returnValue = false;
         } else {
            returnValue = domain.lookupPartition(partitionName) != null;
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "partitionExists", returnValue);
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public Set provisionPartition(String wlsPartitionName, String requestedComponentName, Map configuredValues) throws ProvisioningException {
      Map map = new HashMap();
      map.put("wlsPartitionName", wlsPartitionName);
      return this.provisionPartition(requestedComponentName, (Map)map, configuredValues);
   }

   public Set provisionPartition(String requestedComponentName, Map propertyValues, Map configuredValues) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "provisionPartition";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "provisionPartition", new Object[]{requestedComponentName, propertyValues, configuredValues});
      }

      Objects.requireNonNull(requestedComponentName);
      if (propertyValues == null) {
         propertyValues = Collections.emptyMap();
      }

      if (configuredValues == null) {
         configuredValues = Collections.emptyMap();
      }

      ProvisioningOperationDescriptor provisioningOperationDescriptor = (ProvisioningOperationDescriptor)this.serviceLocator.getService(ProvisioningOperationDescriptor.class, new Annotation[]{INITIAL_PROVISIONING_OPERATION_LITERAL});

      assert provisioningOperationDescriptor != null;

      ProvisioningOperation provisioningOperation = new ProvisioningOperation(Collections.singleton(new ProvisioningComponentIdentifier(requestedComponentName)), provisioningOperationDescriptor, configuredValues, propertyValues);
      Set affectedIds = this.executeProvisioningOperation(provisioningOperation);

      assert affectedIds != null;

      Set returnValue = asNamesOnly(affectedIds);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "provisionPartition", returnValue);
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public final Set execute(ProvisioningOperation provisioningOperation) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "execute";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "execute", provisioningOperation);
      }

      Set returnValue = new HashSet();
      Set ids = this.executeProvisioningOperation(provisioningOperation);
      if (ids != null && !ids.isEmpty()) {
         Iterator var7 = ids.iterator();

         while(var7.hasNext()) {
            ProvisioningComponentIdentifier id = (ProvisioningComponentIdentifier)var7.next();
            if (id == null) {
               returnValue.add((Object)null);
            } else {
               returnValue.add(id.getName());
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "execute", returnValue);
      }

      return returnValue;
   }

   public final Set executeProvisioningOperation(ProvisioningOperation provisioningOperation) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "execute";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "execute", provisioningOperation);
      }

      Objects.requireNonNull(provisioningOperation);
      ProvisioningOperationDescriptor provisioningOperationDescriptor = provisioningOperation.getProvisioningOperationDescriptor();
      if (provisioningOperationDescriptor == null) {
         throw new IllegalArgumentException("provisioningOperation", new IllegalStateException("provisioningOperation.getProvisioningOperationDescriptor() == null"));
      } else {
         Set ids = provisioningOperation.getProvisioningComponentIds();
         boolean noIds = true;
         if (ids != null && !ids.isEmpty()) {
            Iterator var8 = ids.iterator();

            while(var8.hasNext()) {
               ProvisioningComponentIdentifier id = (ProvisioningComponentIdentifier)var8.next();
               if (id != null) {
                  noIds = false;
                  if (!this.provisioningComponentContainer.contains(id)) {
                     throw new ProvisioningException("No such provisioning component: " + id);
                  }
               }
            }
         }

         if (noIds) {
            throw new ProvisioningException("No provisioning components specified");
         } else {
            Map configuredValues = provisioningOperation.getConfigurableAttributeValues();
            if (configuredValues == null) {
               configuredValues = Collections.emptyMap();
            }

            Set returnValue = null;
            Deque transactionalHandlers = new ArrayDeque();
            Deque allHandlers = new ArrayDeque();
            OperationHandle operationHandle = this.operationManager.createAndStartOperation(ProvisioningOperationScopedLiteral.instance);
            ProvisioningContextImpl ctx = null;
            Object thrownException = null;
            boolean var32 = false;

            try {
               var32 = true;
               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "execute", "Created partition provisioning operation handle: {0}", operationHandle);
               }

               ctx = (ProvisioningContextImpl)this.provisioningContextProvider.get();

               assert ctx != null;

               assert ctx.getCurrentProvisionerComponentId() == null;

               assert ctx.getCurrentProvisioningComponentId() == null;

               ConfigurableAttributeValueProvider defaults;
               if (this.serializer == null) {
                  defaults = null;
               } else {
                  defaults = (ConfigurableAttributeValueProvider)this.serializer.deserialize(provisioningOperation);
               }

               Deque documentQueue = new ArrayDeque();
               ctx.initialize((ProvisioningOperation)provisioningOperation, (ConfigurableAttributeValueProvider)defaults, (Deque)documentQueue);
               Set provisioningSequence = provisioningOperation.getProvisioningSequence();
               if (provisioningSequence != null && !provisioningSequence.isEmpty()) {
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "execute", "Provisioning operation sequence: {0}", provisioningSequence);
                  }

                  Collection provisioningComponents = this.getProvisioningSequenceAsComponentMetadatas(provisioningOperationDescriptor, provisioningSequence);

                  assert provisioningComponents != null;

                  assert !provisioningComponents.isEmpty();

                  this.enqueueDocuments(ctx, documentQueue, provisioningComponents);

                  assert allHandlers != null;

                  assert allHandlers.isEmpty();

                  if (this.validator != null && isValidationEnabled()) {
                     this.runValidation(provisioningOperationDescriptor, documentQueue, allHandlers, transactionalHandlers);
                  }

                  this.deliverDocuments(ctx, documentQueue, allHandlers, transactionalHandlers);

                  assert documentQueue.isEmpty();
               } else if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "execute", "There were no provisioning components to enlist.");
               }

               ctx.setCurrentProvisioningComponentId((ProvisioningComponentIdentifier)null);

               assert transactionalHandlers != null;

               if (!transactionalHandlers.isEmpty()) {
                  Iterator var48 = transactionalHandlers.iterator();

                  while(var48.hasNext()) {
                     TransactionalHandler handler = (TransactionalHandler)var48.next();
                     if (handler != null) {
                        handler.commit();
                     }
                  }
               }

               this.broadcastProvisioningCompletionEvent();
               var32 = false;
            } catch (Exception var40) {
               Exception everything = var40;
               if (ctx != null) {
                  returnValue = ctx.getAffectedComponentIds();

                  assert returnValue != null : "ctx.getAffectedComponentIds() == null";

                  ctx.setCurrentProvisionerComponentId((ProvisioningComponentIdentifier)null);
                  ctx.setCurrentProvisioningComponentId((ProvisioningComponentIdentifier)null);
               }

               if (transactionalHandlers != null && !transactionalHandlers.isEmpty()) {
                  label706:
                  while(true) {
                     TransactionalHandler handler;
                     do {
                        if (transactionalHandlers.isEmpty()) {
                           assert transactionalHandlers.isEmpty();
                           break label706;
                        }

                        handler = (TransactionalHandler)transactionalHandlers.removeLast();
                     } while(handler == null);

                     try {
                        handler.rollback();
                     } catch (Exception var37) {
                        if (logger != null && logger.isLoggable(Level.SEVERE)) {
                           String handlerStringRepresentation = null;

                           try {
                              handlerStringRepresentation = handler.toString();
                           } catch (RuntimeException var33) {
                              logger.logp(Level.SEVERE, className, "execute", "Exception during invocation of toString() on a handler", var33);
                           }

                           logger.logp(Level.SEVERE, className, "execute", "Exception during rollback of handler" + (handlerStringRepresentation == null ? "" : " " + handlerStringRepresentation), var37);
                        }
                     }
                  }
               }

               try {
                  this.broadcastProvisioningFailureEvent(everything);
               } catch (Exception var36) {
                  if (logger != null && logger.isLoggable(Level.SEVERE)) {
                     logger.logp(Level.SEVERE, className, "execute", "Exception during cleanup of failed provisioning operation", var36);
                  }
               }

               if (var40 instanceof RuntimeException) {
                  thrownException = var40;
                  throw (RuntimeException)var40;
               }

               if (var40 instanceof ProvisioningException) {
                  thrownException = var40;
                  throw (ProvisioningException)var40;
               }

               thrownException = new ProvisioningException(var40);
               throw (ProvisioningException)thrownException;
            } finally {
               if (var32) {
                  transactionalHandlers.clear();
                  if (ctx != null) {
                     if (returnValue == null) {
                        returnValue = ctx.getAffectedComponentIds();

                        assert returnValue != null : "ctx.getAffectedComponentIds() == null";
                     }

                     ctx.setCurrentProvisionerComponentId((ProvisioningComponentIdentifier)null);
                     ctx.setCurrentProvisioningComponentId((ProvisioningComponentIdentifier)null);
                     ctx.setException((Exception)thrownException);
                     if (thrownException == null && this.serializer != null) {
                        try {
                           this.serializer.serialize(ctx);
                        } catch (ProvisioningException | RuntimeException var35) {
                           if (logger != null && logger.isLoggable(Level.SEVERE)) {
                              logger.logp(Level.SEVERE, className, "execute", "Exception during serializeProvisioningContext(ProvisioningContextImpl) operation on " + ctx, var35);
                           }
                        }
                     }
                  }

                  while(!allHandlers.isEmpty()) {
                     ServiceHandle sh = (ServiceHandle)allHandlers.removeLast();

                     assert sh != null;

                     try {
                        if (logger != null && logger.isLoggable(Level.FINE)) {
                           logger.logp(Level.FINE, className, "execute", "Destroying ServiceHandle representing Handler instance: {0}", sh);
                        }

                        sh.destroy();
                     } catch (Exception var34) {
                        if (logger != null && logger.isLoggable(Level.SEVERE)) {
                           logger.logp(Level.SEVERE, className, "execute", "Exception during ServiceHandle.destroy() operation on " + sh, var34);
                        }
                     }
                  }

                  assert allHandlers.isEmpty();

                  if (operationHandle != null) {
                     if (logger != null && logger.isLoggable(Level.FINE)) {
                        logger.logp(Level.FINE, className, "execute", "Closing operation handle: {0}", operationHandle);
                     }

                     operationHandle.setOperationData((Object)null);
                     operationHandle.closeOperation();
                  }

               }
            }

            transactionalHandlers.clear();
            if (ctx != null) {
               if (returnValue == null) {
                  returnValue = ctx.getAffectedComponentIds();

                  assert returnValue != null : "ctx.getAffectedComponentIds() == null";
               }

               ctx.setCurrentProvisionerComponentId((ProvisioningComponentIdentifier)null);
               ctx.setCurrentProvisioningComponentId((ProvisioningComponentIdentifier)null);
               ctx.setException((Exception)thrownException);
               if (thrownException == null && this.serializer != null) {
                  try {
                     this.serializer.serialize(ctx);
                  } catch (ProvisioningException | RuntimeException var39) {
                     if (logger != null && logger.isLoggable(Level.SEVERE)) {
                        logger.logp(Level.SEVERE, className, "execute", "Exception during serializeProvisioningContext(ProvisioningContextImpl) operation on " + ctx, var39);
                     }
                  }
               }
            }

            while(!allHandlers.isEmpty()) {
               ServiceHandle sh = (ServiceHandle)allHandlers.removeLast();

               assert sh != null;

               try {
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "execute", "Destroying ServiceHandle representing Handler instance: {0}", sh);
                  }

                  sh.destroy();
               } catch (Exception var38) {
                  if (logger != null && logger.isLoggable(Level.SEVERE)) {
                     logger.logp(Level.SEVERE, className, "execute", "Exception during ServiceHandle.destroy() operation on " + sh, var38);
                  }
               }
            }

            assert allHandlers.isEmpty();

            if (operationHandle != null) {
               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "execute", "Closing operation handle: {0}", operationHandle);
               }

               operationHandle.setOperationData((Object)null);
               operationHandle.closeOperation();
            }

            assert returnValue != null : "returnValue == null";

            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.exiting(className, "execute", returnValue);
            }

            return returnValue;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public Set deprovisionPartition(String wlsPartitionName, String requestedComponentName, Map configuredValues) throws ProvisioningException {
      Map map = new HashMap();
      map.put("wlsPartitionName", wlsPartitionName);
      return this.deprovisionPartition(requestedComponentName, (Map)map, configuredValues);
   }

   public Set deprovisionPartition(String requestedComponentName, Map propertyValues, Map configuredValues) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "deprovisionPartition";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "deprovisionPartition", new Object[]{requestedComponentName, propertyValues, configuredValues});
      }

      String wlsPartitionName = propertyValues != null ? (String)propertyValues.get("wlsPartitionName") : null;
      Objects.requireNonNull(wlsPartitionName);
      Objects.requireNonNull(requestedComponentName);
      if (propertyValues == null) {
         propertyValues = Collections.emptyMap();
      }

      if (configuredValues == null) {
         configuredValues = Collections.emptyMap();
      }

      ProvisioningComponentIdentifier id = new ProvisioningComponentIdentifier(requestedComponentName);
      if (!this.provisioningComponentContainer.contains(id)) {
         throw new ProvisioningException("No such provisioning component: " + requestedComponentName);
      } else {
         Set returnValue;
         if (isDeprovisioningEnabled()) {
            ProvisioningOperationDescriptor provisioningOperationDescriptor = (ProvisioningOperationDescriptor)this.serviceLocator.getService(ProvisioningOperationDescriptor.class, new Annotation[]{DEPROVISIONING_OPERATION_LITERAL});

            assert provisioningOperationDescriptor != null;

            ProvisioningOperation provisioningOperation = new ProvisioningOperation(Collections.singleton(id), provisioningOperationDescriptor, configuredValues, propertyValues);
            Set affectedIds = this.executeProvisioningOperation(provisioningOperation);
            returnValue = asNamesOnly(affectedIds);
         } else {
            returnValue = Collections.emptySet();
         }

         assert returnValue != null;

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "deprovisionPartition", returnValue);
         }

         return returnValue;
      }
   }

   private final Set getProvisioningSequenceAsComponentMetadatas(ProvisioningOperationDescriptor provisioningOperationDescriptor, Set componentNames) throws ProvisioningException {
      Objects.requireNonNull(provisioningOperationDescriptor);
      Set returnValue = new LinkedHashSet();
      if (componentNames != null && !componentNames.isEmpty()) {
         Iterator var4 = componentNames.iterator();

         while(var4.hasNext()) {
            String componentName = (String)var4.next();
            if (componentName != null) {
               ComponentMetadata cmd = this.registry.getComponentMetadata(componentName);
               if (cmd != null) {
                  returnValue.add(cmd);
               }
            }
         }
      }

      return returnValue;
   }

   private final void runValidation(ProvisioningOperationDescriptor provisioningOperationDescriptor, Collection documentQueue, Collection allHandlers, Collection transactionalHandlers) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "runValidation";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "runValidation", new Object[]{provisioningOperationDescriptor, documentQueue, allHandlers, transactionalHandlers});
      }

      Objects.requireNonNull(provisioningOperationDescriptor);
      Objects.requireNonNull(documentQueue);
      Objects.requireNonNull(allHandlers);
      Objects.requireNonNull(transactionalHandlers);
      if (this.validator != null && !documentQueue.isEmpty()) {
         Set allViolations = new LinkedHashSet();
         ExecutableValidator executableValidator = this.validator.forExecutables();

         assert executableValidator != null;

         Set seenHandlers = new HashSet();
         Set seenMethods = new HashSet();
         Iterator var12 = documentQueue.iterator();

         while(var12.hasNext()) {
            Document document = (Document)var12.next();
            if (document != null) {
               HandlerMethodDescriptor handlerMethodDescriptor = this.getHandlerMethodDescriptor(provisioningOperationDescriptor, document);
               if (handlerMethodDescriptor != null) {
                  ServiceHandle handlerServiceHandle = this.getHandlerServiceHandle(handlerMethodDescriptor, allHandlers);

                  assert handlerServiceHandle != null : "getHandlerServiceHandle(handlerMethodDescriptor, allHandlers) == null";

                  Object handlerInstance = handlerServiceHandle.getService();

                  assert handlerInstance != null;

                  Object unproxiedHandlerInstance;
                  if (handlerInstance instanceof ProxyCtl) {
                     unproxiedHandlerInstance = ((ProxyCtl)handlerInstance).__make();
                  } else {
                     unproxiedHandlerInstance = handlerInstance;
                  }

                  assert unproxiedHandlerInstance != null;

                  if (!seenHandlers.contains(handlerInstance)) {
                     seenHandlers.add(handlerInstance);
                     if (handlerInstance instanceof TransactionalHandler && !transactionalHandlers.contains(handlerInstance)) {
                        transactionalHandlers.add((TransactionalHandler)handlerInstance);
                     }

                     Set violations = this.validator.validate(unproxiedHandlerInstance, new Class[0]);
                     if (violations != null && !violations.isEmpty()) {
                        allViolations.addAll(violations);
                     }
                  }

                  Method handlerMethod = handlerMethodDescriptor.getMethod();

                  assert handlerMethod != null;

                  if (!seenMethods.contains(handlerMethod)) {
                     seenMethods.add(handlerMethod);
                     Set violations = executableValidator.validateParameters(unproxiedHandlerInstance, handlerMethod, new Object[]{new ProvisioningEvent(document)}, new Class[0]);
                     if (violations != null && !violations.isEmpty()) {
                        allViolations.addAll(violations);
                     }
                  }
               }
            }
         }

         if (allViolations != null && !allViolations.isEmpty()) {
            throw new ProvisioningException(new ConstraintViolationException(allViolations));
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "runValidation");
      }

   }

   private final Collection enqueueDocuments(ProvisioningContextImpl provisioningContext, Collection documentQueue, Collection components) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "enqueueDocuments";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "enqueueDocuments", new Object[]{provisioningContext, documentQueue, components});
      }

      Objects.requireNonNull(provisioningContext);
      Objects.requireNonNull(documentQueue);
      Objects.requireNonNull(components);
      Iterator var7 = components.iterator();

      while(true) {
         while(var7.hasNext()) {
            ProvisioningComponent component = (ProvisioningComponent)var7.next();
            Collection provisioningResources = component.getProvisioningResources();
            if (provisioningResources != null && !provisioningResources.isEmpty()) {
               Collection docs = this.enqueueDocuments(provisioningContext, component);
               if (docs != null && !docs.isEmpty()) {
                  documentQueue.addAll(docs);
               }
            } else if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.logp(Level.FINER, className, "enqueueDocuments", "Skipping provisioning component {0} because it contains no provisioning instruction documents", component);
            }
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "enqueueDocuments", documentQueue);
         }

         return documentQueue;
      }
   }

   private final Deque enqueueDocuments(ProvisioningContextImpl provisioningContext, ProvisioningComponent componentMetadata) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "enqueueDocuments";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "enqueueDocuments", new Object[]{provisioningContext, componentMetadata});
      }

      Objects.requireNonNull(provisioningContext);
      Objects.requireNonNull(componentMetadata);
      ProvisioningOperationDescriptor provisioningOperationDescriptor = provisioningContext.getProvisioningOperationDescriptor();
      if (provisioningOperationDescriptor == null) {
         throw new IllegalArgumentException("provisioningContext", new IllegalStateException("provisioningContext.getProvisioningOperationDescriptor() == null"));
      } else {
         final ProvisioningComponentIdentifier id = componentMetadata.getId();
         if (id == null) {
            throw new IllegalArgumentException("componentMetadata", new IllegalStateException("componentMetadata.getId() == null"));
         } else {
            Deque returnValue = new ArrayDeque();
            Iterable provisioningResources = componentMetadata.getProvisioningResources();
            if (provisioningResources != null) {
               Collection enabledDocumentsInCurrentComponent = new ArrayList();
               ProvisioningComponentIdentifier oldCurrentProvisioningComponentId = provisioningContext.getCurrentProvisioningComponentId();

               try {
                  provisioningContext.setCurrentProvisioningComponentId(id);
                  Iterator var12 = provisioningResources.iterator();

                  while(var12.hasNext()) {
                     final ProvisioningResource provisioningResource = (ProvisioningResource)var12.next();
                     if (provisioningResource != null) {
                        URI documentURI = provisioningResource.getResource();
                        if (documentURI != null) {
                           if (this.getHandlerMethodDescriptor(provisioningOperationDescriptor, provisioningResource) == null) {
                              if (logger != null && logger.isLoggable(Level.FINER)) {
                                 logger.logp(Level.FINER, className, "enqueueDocuments", "No HandlerMethodDescriptor found for {0}", documentURI);
                              }
                           } else {
                              Document inflatedDocument = null;
                              if (logger != null && logger.isLoggable(Level.FINE)) {
                                 logger.logp(Level.FINE, className, "enqueueDocuments", "Parsing and inflating/transforming {0} in provisioning component {1}...", new Object[]{documentURI, id});
                              }

                              try {
                                 inflatedDocument = this.documentFactory.getDocument(documentURI);
                              } catch (ParserConfigurationException | SAXException | TransformationException | IOException var23) {
                                 throw new ProvisioningException(var23);
                              }

                              if (inflatedDocument == null) {
                                 if (logger != null && logger.isLoggable(Level.FINE)) {
                                    logger.logp(Level.FINE, className, "enqueueDocuments", "No document resource was found for the URI {0}", documentURI);
                                 }
                              } else {
                                 assert inflatedDocument != null;

                                 if (logger != null && logger.isLoggable(Level.FINE)) {
                                    logger.logp(Level.FINE, className, "enqueueDocuments", "...done.");
                                 }

                                 inflatedDocument.setUserData(className + ".componentId", id, (UserDataHandler)null);
                                 inflatedDocument.setUserData(className + ".componentName", id.getName(), (UserDataHandler)null);
                                 inflatedDocument.setUserData(className + ".provisioningResource", provisioningResource, (UserDataHandler)null);
                                 boolean publish = true;
                                 Collection enabledHandlerMethodDescriptorsInCurrentComponent = this.registry.getHandlerMethodDescriptors(new HandlerMethodDescriptor.Filter() {
                                    public final boolean accept(HandlerMethodDescriptor handlerMethodDescriptor) {
                                       boolean returnValue;
                                       if (handlerMethodDescriptor != null && handlerMethodDescriptor.isEnabled()) {
                                          ProvisioningComponentIdentifier handlerMethodDescriptorComponentId = handlerMethodDescriptor.getComponentId();
                                          returnValue = handlerMethodDescriptorComponentId != null && handlerMethodDescriptorComponentId.implies(id) && Selectors.isSelected(PartitionProvisioner.this.serviceLocator, provisioningResource, handlerMethodDescriptor);
                                       } else {
                                          returnValue = false;
                                       }

                                       return returnValue;
                                    }
                                 });
                                 if (enabledHandlerMethodDescriptorsInCurrentComponent != null && !enabledHandlerMethodDescriptorsInCurrentComponent.isEmpty()) {
                                    Iterator i = enabledHandlerMethodDescriptorsInCurrentComponent.iterator();

                                    assert i != null;

                                    assert i.hasNext();

                                    HandlerMethodDescriptor descriptor = (HandlerMethodDescriptor)i.next();

                                    assert descriptor != null;

                                    publish = false;
                                    enabledDocumentsInCurrentComponent.add(inflatedDocument);
                                 }

                                 if (publish) {
                                    returnValue.add(inflatedDocument);
                                    if (logger != null && logger.isLoggable(Level.FINER)) {
                                       logger.logp(Level.FINER, className, "enqueueDocuments", "Published Document {0} with URI {1}", new Object[]{inflatedDocument, inflatedDocument.getDocumentURI()});
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }

                  if (!enabledDocumentsInCurrentComponent.isEmpty()) {
                     var12 = enabledDocumentsInCurrentComponent.iterator();

                     while(var12.hasNext()) {
                        Document inflatedDocument = (Document)var12.next();
                        if (inflatedDocument != null) {
                           returnValue.add(inflatedDocument);
                           if (logger != null && logger.isLoggable(Level.FINER)) {
                              logger.logp(Level.FINER, className, "enqueueDocuments", "Published Document {0} with URI {1}", new Object[]{inflatedDocument, inflatedDocument.getDocumentURI()});
                           }
                        }
                     }
                  }
               } finally {
                  provisioningContext.setCurrentProvisioningComponentId(oldCurrentProvisioningComponentId);
               }
            }

            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.exiting(className, "enqueueDocuments", returnValue);
            }

            return returnValue;
         }
      }
   }

   private final void deliverDocuments(ProvisioningContextImpl ctx, Deque documentQueue, Collection allHandlers, Collection transactionalHandlers) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "deliverDocuments";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "deliverDocuments", new Object[]{ctx, documentQueue, allHandlers, transactionalHandlers});
      }

      Objects.requireNonNull(ctx);
      Objects.requireNonNull(documentQueue);
      Objects.requireNonNull(allHandlers);
      Objects.requireNonNull(transactionalHandlers);

      while(!documentQueue.isEmpty()) {
         Document inflatedDocument = (Document)documentQueue.removeFirst();
         if (inflatedDocument != null) {
            this.deliverDocument(ctx, inflatedDocument, allHandlers, transactionalHandlers);
         }
      }

      assert documentQueue.isEmpty();

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "deliverDocuments");
      }

   }

   private final HandlerMethodDescriptor getHandlerMethodDescriptor(ProvisioningOperationDescriptor provisioningOperationDescriptor, Document document) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "getHandlerMethodDescriptor";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getHandlerMethodDescriptor", new Object[]{provisioningOperationDescriptor, document});
      }

      Objects.requireNonNull(provisioningOperationDescriptor);
      Objects.requireNonNull(document);
      Object provisioningResource = document.getUserData(PartitionProvisioner.class.getName() + ".provisioningResource");
      if (!(provisioningResource instanceof ProvisioningResource)) {
         throw new IllegalArgumentException("document", new IllegalStateException("!(document.getUserData(\"" + PartitionProvisioner.class.getName() + ".provisioningResource\") instanceof ProvisioningResource): " + provisioningResource));
      } else {
         HandlerMethodDescriptor returnValue = this.getHandlerMethodDescriptor(provisioningOperationDescriptor, (ProvisioningResource)provisioningResource);
         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "getHandlerMethodDescriptor", returnValue);
         }

         return returnValue;
      }
   }

   private final HandlerMethodDescriptor getHandlerMethodDescriptor(ProvisioningOperationDescriptor provisioningOperationDescriptor, ProvisioningResource provisioningResource) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "getHandlerMethodDescriptor";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getHandlerMethodDescriptor", new Object[]{provisioningOperationDescriptor, provisioningResource});
      }

      Objects.requireNonNull(provisioningOperationDescriptor);
      Objects.requireNonNull(provisioningResource);
      HandlerMethodDescriptor returnValue = this.registry.getHandlerMethodDescriptor(provisioningResource, provisioningOperationDescriptor);
      if (logger != null) {
         if (returnValue == null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, className, "getHandlerMethodDescriptor", "No HandlerMethodDescriptor found for {0}", provisioningResource);
         }

         if (logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "getHandlerMethodDescriptor", returnValue);
         }
      }

      return returnValue;
   }

   private final ServiceHandle getHandlerServiceHandle(HandlerMethodDescriptor handlerMethodDescriptor, Collection allHandlers) {
      String className = PartitionProvisioner.class.getName();
      String methodName = "getHandlerServiceHandle";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getHandlerServiceHandle", new Object[]{handlerMethodDescriptor, allHandlers});
      }

      Objects.requireNonNull(handlerMethodDescriptor);
      Objects.requireNonNull(allHandlers);
      Class handlerClass = handlerMethodDescriptor.getHandlerClass();
      if (handlerClass == null) {
         throw new IllegalArgumentException("handlerMethodDescriptor", new IllegalStateException("handlerMethodDescriptor.getHandlerClass() == null"));
      } else {
         ServiceHandle returnValue = getServiceHandle(this.serviceLocator, allHandlers, handlerClass);

         assert returnValue != null;

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "getHandlerServiceHandle", returnValue);
         }

         return returnValue;
      }
   }

   private final void deliverDocument(ProvisioningContextImpl ctx, Document inflatedDocument, Collection allHandlers, Collection transactionalHandlers) throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "deliverDocument";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "deliverDocument", new Object[]{ctx, inflatedDocument, allHandlers, transactionalHandlers});
      }

      Objects.requireNonNull(ctx);
      Objects.requireNonNull(inflatedDocument);
      Objects.requireNonNull(allHandlers);
      Objects.requireNonNull(transactionalHandlers);
      ProvisioningComponentIdentifier oldCurrentProvisioningComponentId = ctx.getCurrentProvisioningComponentId();

      try {
         Object currentProvisioningComponentId = inflatedDocument.getUserData(PartitionProvisioner.class.getName() + ".componentId");
         if (currentProvisioningComponentId instanceof ProvisioningComponentIdentifier) {
            ctx.setCurrentProvisioningComponentId((ProvisioningComponentIdentifier)currentProvisioningComponentId);
         } else {
            ctx.setCurrentProvisioningComponentId((ProvisioningComponentIdentifier)null);
         }

         ProvisioningComponentIdentifier affectedComponentId = ctx.getCurrentProvisioningComponentId();
         ProvisioningOperationDescriptor provisioningOperationDescriptor = ctx.getProvisioningOperationDescriptor();
         if (provisioningOperationDescriptor == null) {
            throw new IllegalArgumentException("ctx", new IllegalStateException("ctx.getProvisioningOperationDescriptor() == null"));
         }

         HandlerMethodDescriptor handlerMethodDescriptor = this.getHandlerMethodDescriptor(provisioningOperationDescriptor, inflatedDocument);
         if (handlerMethodDescriptor == null) {
            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.logp(Level.FINER, className, "deliverDocument", "No HandlerMethodDescriptor found for {0}", inflatedDocument.getDocumentURI());
            }
         } else {
            Method handlerMethod = handlerMethodDescriptor.getMethod();
            if (handlerMethod == null) {
               throw new IllegalStateException("handlerMethodDescriptor.getHandlerMethod() == null");
            }

            ServiceHandle handlerServiceHandle = this.getHandlerServiceHandle(handlerMethodDescriptor, allHandlers);

            assert handlerServiceHandle != null;

            Object handlerInstance = handlerServiceHandle.getService();

            assert handlerInstance != null;

            if (handlerInstance instanceof TransactionalHandler && !transactionalHandlers.contains(handlerInstance)) {
               transactionalHandlers.add((TransactionalHandler)handlerInstance);
            }

            ctx.setCurrentProvisionerComponentId(handlerMethodDescriptor.getComponentId());

            try {
               ProvisioningEvent event = new ProvisioningEvent(inflatedDocument);
               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "deliverDocument", "Delivering ProvisioningEvent ({0}) to handler method {1} of handler instance ({2})...", new Object[]{event, handlerMethod, handlerInstance});
               }

               handlerMethod.invoke(handlerInstance, event);
               if (affectedComponentId != null) {
                  ctx.addAffectedComponentId(affectedComponentId);
               }

               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "deliverDocument", "ProvisioningEvent {0} delivered.", event);
               }
            } catch (InvocationTargetException | IllegalAccessException var25) {
               throw new ProvisioningException(var25);
            } finally {
               ctx.setCurrentProvisionerComponentId((ProvisioningComponentIdentifier)null);
            }
         }
      } finally {
         ctx.setCurrentProvisioningComponentId(oldCurrentProvisioningComponentId);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "deliverDocument");
      }

   }

   private final void broadcastProvisioningCompletionEvent() throws ProvisioningException {
      String className = PartitionProvisioner.class.getName();
      String methodName = "broadcastProvisioningCompletionEvent";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "broadcastProvisioningCompletionEvent");
      }

      if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, className, "broadcastProvisioningCompletionEvent", "Completing provisioning operation; broadcasting notification event");
      }

      this.provisioningOperationCompletionTopic.publish(new ProvisioningOperationCompletionEvent());
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "broadcastProvisioningCompletionEvent");
      }

   }

   private final void broadcastProvisioningFailureEvent(Exception cause) {
   }

   private static final ServiceHandle getServiceHandle(ServiceLocator serviceLocator, Collection serviceHandles, Class cls) {
      Objects.requireNonNull(serviceLocator);
      Objects.requireNonNull(serviceHandles);
      Objects.requireNonNull(cls);
      ServiceHandle returnValue = null;
      if (!serviceHandles.isEmpty()) {
         String serviceClassName = cls.getName();
         Iterator var5 = serviceHandles.iterator();

         while(var5.hasNext()) {
            ServiceHandle serviceHandle = (ServiceHandle)var5.next();
            if (serviceHandle != null) {
               Descriptor descriptor = serviceHandle.getActiveDescriptor();
               if (descriptor != null) {
                  String implementationClassName = descriptor.getImplementation();
                  if (implementationClassName != null && implementationClassName.equals(serviceClassName)) {
                     returnValue = serviceHandle;
                     break;
                  }
               }
            }
         }
      }

      if (returnValue == null) {
         returnValue = serviceLocator.getServiceHandle(cls, new Annotation[0]);
         if (returnValue != null) {
            serviceHandles.add(returnValue);
         }
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public static final boolean isProvisioningContextSerializationEnabled() {
      return ProvisioningContextImplSerializer.isProvisioningContextSerializationEnabled();
   }

   public static final boolean isDeprovisioningEnabled() {
      String propertyValue = (String)AccessController.doPrivileged(new PrivilegedAction() {
         public final String run() {
            return System.getProperty("enableDeprovisioningOperation");
         }
      });
      boolean returnValue = propertyValue == null || Boolean.parseBoolean(propertyValue);
      return returnValue;
   }

   public static final boolean isValidationEnabled() {
      String propertyValue = (String)AccessController.doPrivileged(new PrivilegedAction() {
         public final String run() {
            return System.getProperty("disableProvisioningValidation");
         }
      });
      boolean returnValue = propertyValue == null || !"true".equalsIgnoreCase(propertyValue);
      return returnValue;
   }

   private static final Set asNamesOnly(Collection ids) {
      HashSet returnValue;
      if (ids == null) {
         returnValue = null;
      } else {
         returnValue = new HashSet();
         Iterator var2 = ids.iterator();

         while(var2.hasNext()) {
            ProvisioningComponentIdentifier id = (ProvisioningComponentIdentifier)var2.next();
            if (id == null) {
               returnValue.add((Object)null);
            } else {
               returnValue.add(id.getName());
            }
         }
      }

      return returnValue;
   }

   private static final class DeprovisioningOperationLiteral extends AnnotationLiteral implements DeprovisioningOperation {
      private static final long serialVersionUID = 1L;

      private DeprovisioningOperationLiteral() {
      }

      // $FF: synthetic method
      DeprovisioningOperationLiteral(Object x0) {
         this();
      }
   }

   private static final class InitialProvisioningOperationLiteral extends AnnotationLiteral implements InitialProvisioningOperation {
      private static final long serialVersionUID = 1L;

      private InitialProvisioningOperationLiteral() {
      }

      // $FF: synthetic method
      InitialProvisioningOperationLiteral(Object x0) {
         this();
      }
   }
}

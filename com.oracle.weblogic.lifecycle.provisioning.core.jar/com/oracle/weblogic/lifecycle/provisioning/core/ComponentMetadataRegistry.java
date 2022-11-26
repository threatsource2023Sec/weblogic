package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ConfigurableAttributeFactory;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentContainer;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentIdentifier;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentRepository;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEventMethods;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningOperationDescriptor;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningResource;
import com.oracle.weblogic.lifecycle.provisioning.api.TransformationException;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ConfigurableAttribute;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Handler;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import com.oracle.weblogic.lifecycle.provisioning.spi.ProvisioningComponent;
import com.oracle.weblogic.lifecycle.provisioning.spi.ProvisioningComponentFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.xml.parsers.ParserConfigurationException;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.HK2RuntimeException;
import org.glassfish.hk2.api.IndexedFilter;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Service
@Singleton
public class ComponentMetadataRegistry implements ProvisioningComponentContainer, ProvisioningComponentRepository {
   private static final boolean DO_NOT_TRANSFORM = false;
   private static final Filter COMPONENT_FILTER = new ComponentFilter();
   private static final Filter CONFIGURABLE_ATTRIBUTE_FACTORY_FILTER = BuilderHelper.createContractFilter(ConfigurableAttributeFactory.class.getName());
   private static final Filter NON_PROVISIONING_COMPONENT_FACTORY_POPULATOR_FILTER = new NonProvisioningComponentFactoryPopulatorFilter();
   private final ServiceLocator serviceLocator;
   private final Iterable provisioningComponentFactories;
   private final DocumentTransformingDocumentFactory documentFactory;
   private final Set selectableComponentNames;
   private final Map dependenciesAndAffiliations;
   private final Set handlerMethodDescriptors;

   /** @deprecated */
   @Deprecated
   public ComponentMetadataRegistry(ServiceLocator serviceLocator, DocumentTransformingDocumentFactory documentFactory) {
      this(serviceLocator, Collections.emptySet(), documentFactory);
   }

   @Inject
   public ComponentMetadataRegistry(ServiceLocator serviceLocator, Iterable provisioningComponentFactories, DocumentTransformingDocumentFactory documentFactory) {
      Objects.requireNonNull(serviceLocator);
      Objects.requireNonNull(documentFactory);
      this.serviceLocator = serviceLocator;
      if (provisioningComponentFactories == null) {
         this.provisioningComponentFactories = Collections.emptySet();
      } else {
         this.provisioningComponentFactories = provisioningComponentFactories;
      }

      this.documentFactory = documentFactory;
      this.selectableComponentNames = new HashSet();
      this.dependenciesAndAffiliations = new HashMap();
      this.handlerMethodDescriptors = new LinkedHashSet();
   }

   @PostConstruct
   private final void postConstruct() {
      try {
         this.initializeRegistry();
      } catch (ProvisioningException var2) {
         throw new IllegalStateException(var2);
      }
   }

   private final void initializeRegistry() throws ProvisioningException {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "initializeRegistry";
      Logger logger = Logger.getLogger(className);
      if (logger != null) {
         if (logger.isLoggable(Level.FINER)) {
            logger.entering(className, "initializeRegistry");
         }

         if (logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, className, "initializeRegistry", "Initializing ComponentMetadataRegistry");
         }
      }

      Map metadataMap = new HashMap();
      this.discoverComponentMetadatas(metadataMap);

      assert this.provisioningComponentFactories != null;

      Iterator var5 = this.provisioningComponentFactories.iterator();

      while(true) {
         while(true) {
            ProvisioningComponentFactory provisioningComponentFactory;
            Iterator var8;
            ProvisioningComponentIdentifier id;
            String name;
            do {
               if (!var5.hasNext()) {
                  assert this.serviceLocator != null;

                  Collection componentDescriptors = this.serviceLocator.getDescriptors(COMPONENT_FILTER);
                  if (componentDescriptors != null && !componentDescriptors.isEmpty()) {
                     Iterator var18 = componentDescriptors.iterator();

                     while(var18.hasNext()) {
                        ActiveDescriptor componentDescriptor = (ActiveDescriptor)var18.next();
                        if (componentDescriptor != null) {
                           this.detectInjectedConfigurableAttributes(componentDescriptor, (Map)metadataMap);
                        }
                     }
                  } else if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "initializeRegistry", "No ActiveDescriptors corresponding to Component-annotated classes found.");
                  }

                  this.loadHandlerMethodDescriptors(componentDescriptors);
                  Collection provisioningComponents = metadataMap.values();

                  assert provisioningComponents != null;

                  if (!provisioningComponents.isEmpty()) {
                     this.runConfigurableAttributeFactories(provisioningComponents);
                     this.inferDependencies(provisioningComponents);
                     DynamicConfiguration dc = ServiceLocatorUtilities.createDynamicConfiguration(this.serviceLocator);

                     assert dc != null;

                     var8 = provisioningComponents.iterator();

                     while(var8.hasNext()) {
                        ComponentMetadata provisioningComponent = (ComponentMetadata)var8.next();
                        if (provisioningComponent != null) {
                           id = provisioningComponent.getId();
                           if (id != null) {
                              name = id.getName();
                              if (name != null) {
                                 if (provisioningComponent.isSelectable()) {
                                    this.selectableComponentNames.add(name);
                                 }

                                 AbstractActiveDescriptor provisioningComponentDescriptor = BuilderHelper.createConstantDescriptor(provisioningComponent, name, new Type[]{ProvisioningComponent.class, ComponentMetadata.class});

                                 assert provisioningComponentDescriptor != null;

                                 provisioningComponentDescriptor.addMetadata("componentName", name);
                                 provisioningComponentDescriptor.addMetadata("componentVersion", id.getVersion());
                                 dc.addActiveDescriptor(provisioningComponentDescriptor);
                                 Set ids = provisioningComponent.getAffiliatedComponentIds();
                                 if (ids != null && !ids.isEmpty()) {
                                    Set affiliates = asAffiliateNames(ids);
                                    if (affiliates != null && !affiliates.isEmpty()) {
                                       DirectedAffiliation da = new DirectedAffiliation(name, affiliates);
                                       ActiveDescriptor ad = BuilderHelper.createConstantDescriptor(da, name, new Type[]{(new TypeLiteral() {
                                       }).getType()});

                                       assert ad != null;

                                       dc.addActiveDescriptor(ad);
                                    }
                                 }
                              }
                           }
                        }
                     }

                     dc.commit();
                  }

                  if (logger != null && logger.isLoggable(Level.FINER)) {
                     logger.logp(Level.FINER, className, "initializeRegistry", "Initialized ComponentMetadataRegistry: {0}", metadataMap);
                     if (logger.isLoggable(Level.FINEST)) {
                        logger.logp(Level.FINEST, className, "initializeRegistry", "All descriptors:");
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        PrintStream printStream = new PrintStream(baos);
                        ServiceLocatorUtilities.dumpAllDescriptors(this.serviceLocator, printStream);
                        String logMessage = baos.toString();
                        printStream.close();
                        logger.logp(Level.FINEST, className, "initializeRegistry", logMessage);
                     }

                     logger.exiting(className, "initializeRegistry");
                  }

                  return;
               }

               provisioningComponentFactory = (ProvisioningComponentFactory)var5.next();
            } while(provisioningComponentFactory == null);

            Set provisioningComponents = provisioningComponentFactory.getProvisioningComponents();
            if (provisioningComponents != null && !provisioningComponents.isEmpty()) {
               var8 = provisioningComponents.iterator();

               while(var8.hasNext()) {
                  ProvisioningComponent provisioningComponent = (ProvisioningComponent)var8.next();
                  if (provisioningComponent != null) {
                     id = provisioningComponent.getId();
                     if (id != null) {
                        name = id.getName();
                        if (name != null) {
                           if (provisioningComponent instanceof ComponentMetadata) {
                              metadataMap.put(name, (ComponentMetadata)provisioningComponent);
                           } else {
                              metadataMap.put(name, new ComponentMetadata(provisioningComponent));
                           }
                        }
                     }
                  }
               }
            } else if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "initializeRegistry", "The ProvisioningComponentFactory implementation {0} did not supply any ProvisioningComponents.", provisioningComponentFactory);
            }
         }
      }
   }

   /** @deprecated */
   @Deprecated
   private final void discoverComponentMetadatas(Map metadataMap) throws ProvisioningException {
      if (metadataMap != null) {
         Iterable populators = this.serviceLocator.getAllServices(NON_PROVISIONING_COMPONENT_FACTORY_POPULATOR_FILTER);
         if (populators != null) {
            Iterator var3 = populators.iterator();

            while(true) {
               Set componentMetadatas;
               do {
                  do {
                     ComponentMetadataRegistryPopulator populator;
                     do {
                        if (!var3.hasNext()) {
                           return;
                        }

                        populator = (ComponentMetadataRegistryPopulator)var3.next();
                     } while(populator == null);

                     componentMetadatas = populator.getComponentMetadata();
                  } while(componentMetadatas == null);
               } while(componentMetadatas.isEmpty());

               Iterator var6 = componentMetadatas.iterator();

               while(var6.hasNext()) {
                  ComponentMetadata cmd = (ComponentMetadata)var6.next();
                  if (cmd != null) {
                     String name = cmd.getName();
                     if (name != null) {
                        metadataMap.put(name, cmd);
                     }
                  }
               }
            }
         }
      }

   }

   private final ComponentMetadata createProvisioningComponent(String name, ActiveDescriptor activeDescriptor) {
      Objects.requireNonNull(name);
      Objects.requireNonNull(activeDescriptor);
      ComponentMetadata returnValue = new ComponentMetadata(new ProvisioningComponentIdentifier(name), (String)null, true, Collections.emptySet(), Components.getAffiliatedProvisioningComponentIds(this.serviceLocator, activeDescriptor), Collections.emptySet());
      return returnValue;
   }

   public final Set getSelectableProvisioningComponentNames() throws ProvisioningException {
      assert this.selectableComponentNames != null;

      return Collections.unmodifiableSet(this.selectableComponentNames);
   }

   final ComponentMetadata getComponentMetadata(String provisioningComponentName) throws ProvisioningException {
      Objects.requireNonNull(provisioningComponentName);

      try {
         return (ComponentMetadata)this.serviceLocator.getService(ComponentMetadata.class, provisioningComponentName, new Annotation[0]);
      } catch (HK2RuntimeException var3) {
         throw new ProvisioningException(var3);
      }
   }

   public boolean contains(ProvisioningComponentIdentifier id) {
      boolean returnValue = false;
      if (id != null) {
         returnValue = this.serviceLocator.getBestDescriptor(new ProvisioningComponentFilter(this.serviceLocator, id)) != null;
      }

      return returnValue;
   }

   public final Set getDependentAndAffiliatedProvisioningComponentNames(String componentName) throws ProvisioningException {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "getDependentAndAffiliatedProvisioningComponentNames";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getDependentAndAffiliatedProvisioningComponentNames", componentName);
      }

      Set returnValue = null;

      assert this.dependenciesAndAffiliations != null;

      synchronized(this.dependenciesAndAffiliations) {
         returnValue = (Set)this.dependenciesAndAffiliations.get(componentName);
         if (returnValue == null) {
            Set dependentAndAffiliatedComponentNames = this.computeDependentAndAffiliatedProvisioningComponentNames(Collections.singleton(componentName));

            assert dependentAndAffiliatedComponentNames != null;

            List dependentAndAffiliatedComponentNamesList = null;
            if (!dependentAndAffiliatedComponentNames.isEmpty()) {
               dependentAndAffiliatedComponentNamesList = new ArrayList(dependentAndAffiliatedComponentNames);
               dependentAndAffiliatedComponentNamesList.remove(dependentAndAffiliatedComponentNamesList.size() - 1);
               if (!dependentAndAffiliatedComponentNamesList.isEmpty()) {
                  Collections.reverse(dependentAndAffiliatedComponentNamesList);
               }
            }

            if (dependentAndAffiliatedComponentNamesList != null && !dependentAndAffiliatedComponentNamesList.isEmpty()) {
               returnValue = Collections.unmodifiableSet(new LinkedHashSet(dependentAndAffiliatedComponentNamesList));
            } else {
               returnValue = Collections.emptySet();
            }

            this.dependenciesAndAffiliations.put(componentName, returnValue);
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getDependentAndAffiliatedProvisioningComponentNames", returnValue);
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   final HandlerMethodDescriptor getHandlerMethodDescriptor(String documentURIString, ProvisioningOperationDescriptor provisioningOperationDescriptor) {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "getHandlerMethodDescriptor";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getHandlerMethodDescriptor", new Object[]{documentURIString, provisioningOperationDescriptor});
      }

      Objects.requireNonNull(documentURIString);
      Objects.requireNonNull(provisioningOperationDescriptor);
      HandlerMethodDescriptor returnValue = this.getHandlerMethodDescriptor(new ProvisioningResource(URI.create(documentURIString)), provisioningOperationDescriptor);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getHandlerMethodDescriptor", returnValue);
      }

      return returnValue;
   }

   final HandlerMethodDescriptor getHandlerMethodDescriptor(ProvisioningResource provisioningResource, ProvisioningOperationDescriptor provisioningOperationDescriptor) {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "getHandlerMethodDescriptor";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getHandlerMethodDescriptor", new Object[]{provisioningResource, provisioningOperationDescriptor});
      }

      Objects.requireNonNull(provisioningResource);
      Objects.requireNonNull(provisioningOperationDescriptor);
      HandlerMethodDescriptor returnValue = null;
      if (!this.handlerMethodDescriptors.isEmpty()) {
         Annotation provisioningOperationQualifier = provisioningOperationDescriptor.getProvisioningOperationQualifier();

         assert provisioningOperationQualifier != null;

         Iterator var8 = this.handlerMethodDescriptors.iterator();

         while(var8.hasNext()) {
            HandlerMethodDescriptor handlerMethodDescriptor = (HandlerMethodDescriptor)var8.next();
            if (handlerMethodDescriptor != null && provisioningOperationQualifier.equals(handlerMethodDescriptor.getProvisioningOperationQualifier()) && Selectors.isSelected(this.serviceLocator, provisioningResource, handlerMethodDescriptor)) {
               returnValue = handlerMethodDescriptor;
               break;
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getHandlerMethodDescriptor", returnValue);
      }

      return returnValue;
   }

   private final Set getAffiliatedProvisioningComponents(Collection components) throws ProvisioningException {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "getAffiliatedProvisioningComponents";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getAffiliatedProvisioningComponents", components);
      }

      Set directedAffiliations = new HashSet();
      TypeLiteral typeLiteral = new TypeLiteral() {
      };
      directedAffiliations.addAll(this.serviceLocator.getAllServices(typeLiteral.getType(), new Annotation[0]));
      Set returnValue = getAffiliatedProvisioningComponents(components, directedAffiliations);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getAffiliatedProvisioningComponents", returnValue);
      }

      return returnValue;
   }

   static final Set getAffiliatedProvisioningComponents(Collection components, Set allDas) throws ProvisioningException {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "getAffiliatedProvisioningComponents";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getAffiliatedProvisioningComponents", new Object[]{components, allDas});
      }

      Set returnValue = null;
      if (components != null && allDas != null && !components.isEmpty() && !allDas.isEmpty()) {
         Collection das = DirectedAffiliation.combine(allDas);
         if (das != null && !das.isEmpty()) {
            returnValue = new HashSet();
            Iterator var7 = das.iterator();

            while(var7.hasNext()) {
               DirectedAffiliation da = (DirectedAffiliation)var7.next();
               if (da != null) {
                  Object provisioningComponent = da.getPrincipal();
                  if (provisioningComponent != null && components.contains(provisioningComponent)) {
                     returnValue.addAll(da.getAffiliates());
                  }
               }
            }

            returnValue.removeAll(components);
         }
      }

      Set returnValue;
      if (returnValue != null && !returnValue.isEmpty()) {
         returnValue = Collections.unmodifiableSet(returnValue);
      } else {
         returnValue = Collections.emptySet();
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getAffiliatedProvisioningComponents", returnValue);
      }

      return returnValue;
   }

   public final Set computeDependentAndAffiliatedProvisioningComponentNames(Iterable componentNames) throws ProvisioningException {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "computeDependentAndAffiliatedProvisioningComponentNames";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "computeDependentAndAffiliatedProvisioningComponentNames", componentNames);
      }

      Set returnValue = new LinkedHashSet();
      Collection pendingComponentNames = new ArrayList();
      if (componentNames != null) {
         Iterator var7 = componentNames.iterator();

         while(var7.hasNext()) {
            String cn = (String)var7.next();
            if (cn != null) {
               pendingComponentNames.add(cn);
            }
         }
      }

      Collection affiliates = this.getAffiliatedProvisioningComponents(pendingComponentNames);
      if (affiliates != null && !affiliates.isEmpty()) {
         pendingComponentNames.addAll(affiliates);
      }

      Map dependents = new HashMap();

      while(!pendingComponentNames.isEmpty()) {
         Iterator iterator = pendingComponentNames.iterator();

         assert iterator != null;

         assert iterator.hasNext();

         String name = (String)iterator.next();
         iterator.remove();
         if (name != null && !dependents.containsKey(name)) {
            ComponentMetadata metadata = this.getComponentMetadata(name);
            if (metadata == null) {
               if (logger != null && logger.isLoggable(Level.FINER)) {
                  logger.logp(Level.FINER, className, "computeDependentAndAffiliatedProvisioningComponentNames", "Skipping \"{0}\" because it did not designate an existing provisioning component", name);
               }
            } else {
               synchronized(metadata) {
                  Collection dependentComponentNames = metadata.getDependentComponentNames();
                  if (dependentComponentNames != null) {
                     Object dependentComponentNamesSet;
                     if (dependentComponentNames.isEmpty()) {
                        dependentComponentNamesSet = Collections.emptySet();
                     } else {
                        dependentComponentNamesSet = new HashSet(dependentComponentNames);
                     }

                     pendingComponentNames.addAll((Collection)dependentComponentNamesSet);
                     dependents.put(name, dependentComponentNamesSet);
                  }
               }
            }
         }
      }

      label155:
      while(true) {
         Set entries;
         do {
            do {
               if (dependents.isEmpty()) {
                  if (logger != null && logger.isLoggable(Level.FINER)) {
                     logger.exiting(className, "computeDependentAndAffiliatedProvisioningComponentNames", returnValue);
                  }

                  return returnValue;
               }

               entries = dependents.entrySet();
            } while(entries == null);
         } while(entries.isEmpty());

         Set freeNodeNames = new HashSet();
         Iterator iterator = entries.iterator();
         if (iterator != null) {
            label130:
            while(true) {
               Map.Entry entry;
               Collection value;
               do {
                  do {
                     if (!iterator.hasNext()) {
                        break label130;
                     }

                     entry = (Map.Entry)iterator.next();
                  } while(entry == null);

                  value = (Collection)entry.getValue();
               } while(value != null && !value.isEmpty());

               String key = (String)entry.getKey();
               if (key != null) {
                  returnValue.add(key);
                  freeNodeNames.add(key);
                  iterator.remove();
               }
            }
         }

         if (freeNodeNames.isEmpty()) {
            throw new CyclicDependencyDetectedException("Couldn't reduce further. Current state: " + remainingDependentsAsString(dependents));
         }

         Iterator var23 = freeNodeNames.iterator();

         while(true) {
            String freeNodeName;
            do {
               do {
                  do {
                     if (!var23.hasNext()) {
                        continue label155;
                     }

                     freeNodeName = (String)var23.next();
                  } while(freeNodeName == null);

                  entries = dependents.entrySet();
               } while(entries == null);
            } while(entries.isEmpty());

            Iterator var27 = entries.iterator();

            while(var27.hasNext()) {
               Map.Entry entry = (Map.Entry)var27.next();
               if (entry != null) {
                  Collection value = (Collection)entry.getValue();
                  if (value != null && !value.isEmpty()) {
                     value.remove(freeNodeName);
                  }
               }
            }
         }
      }
   }

   final Collection getConfigurableAttributes(String componentName) throws ProvisioningException {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "getConfigurableAttributes";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConfigurableAttributes", componentName);
      }

      Objects.requireNonNull(componentName);
      ComponentMetadata provisioningComponent = (ComponentMetadata)this.serviceLocator.getService(ComponentMetadata.class, componentName, new Annotation[0]);
      if (provisioningComponent == null) {
         throw new ProvisioningException("No such provisioning component: " + componentName);
      } else {
         Collection returnValue = provisioningComponent.getConfigurableAttributes();
         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "getConfigurableAttributes", returnValue);
         }

         return returnValue;
      }
   }

   private final void runConfigurableAttributeFactories(Iterable provisioningComponents) throws ProvisioningException {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "runConfigurableAttributeFactories";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "runConfigurableAttributeFactories", provisioningComponents);
      }

      if (provisioningComponents != null) {
         Iterator var5 = provisioningComponents.iterator();

         label76:
         while(true) {
            ComponentMetadata provisioningComponent;
            Set provisioningResources;
            do {
               do {
                  if (!var5.hasNext()) {
                     break label76;
                  }

                  provisioningComponent = (ComponentMetadata)var5.next();
               } while(provisioningComponent == null);

               provisioningResources = provisioningComponent.getProvisioningResources();
            } while(provisioningResources == null);

            Iterator var8 = provisioningResources.iterator();

            while(true) {
               URI documentURI;
               ConfigurableAttributeFactory factoryInstance;
               do {
                  ProvisioningResource provisioningResource;
                  do {
                     do {
                        if (!var8.hasNext()) {
                           continue label76;
                        }

                        provisioningResource = (ProvisioningResource)var8.next();
                     } while(provisioningResource == null);

                     documentURI = provisioningResource.getResource();
                  } while(documentURI == null);

                  factoryInstance = this.getConfigurableAttributeFactory(provisioningResource);
               } while(factoryInstance == null);

               Document doc = null;

               try {
                  doc = this.documentFactory.getDocument(documentURI, false);
               } catch (ParserConfigurationException | SAXException | TransformationException | IOException var16) {
                  throw new ProvisioningException(var16);
               }

               ProvisioningEvent provisioningEvent = new ProvisioningEvent(doc);
               Iterator var14 = factoryInstance.getConfigurableAttributes(provisioningEvent).iterator();

               while(var14.hasNext()) {
                  ConfigurableAttribute attr = (ConfigurableAttribute)var14.next();
                  if (attr != null) {
                     provisioningComponent.putConfigurableAttribute(attr.name(), attr);
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "runConfigurableAttributeFactories");
      }

   }

   private final ConfigurableAttributeFactory getConfigurableAttributeFactory(ProvisioningResource provisioningResource) throws ProvisioningException {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "getConfigurableAttributeFactory";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConfigurableAttributeFactory", provisioningResource);
      }

      ConfigurableAttributeFactory returnValue = null;
      if (provisioningResource != null) {
         Iterable configurableAttributeFactoryDescriptors = this.serviceLocator.getDescriptors(CONFIGURABLE_ATTRIBUTE_FACTORY_FILTER);
         if (configurableAttributeFactoryDescriptors != null) {
            Iterator var7 = configurableAttributeFactoryDescriptors.iterator();

            label227:
            while(true) {
               while(true) {
                  Class factoryClass;
                  final Method getConfigurableAttributesMethod;
                  Annotation[] provisioningEventParameterAnnotations;
                  do {
                     do {
                        Annotation[][] arrayOfAnnotationsOnAllParameters;
                        do {
                           do {
                              if (!var7.hasNext()) {
                                 break label227;
                              }

                              ActiveDescriptor ad = (ActiveDescriptor)var7.next();

                              assert ad != null;

                              Class rawFactoryClass = this.serviceLocator.reifyDescriptor(ad).getImplementationClass();

                              assert rawFactoryClass != null;

                              factoryClass = rawFactoryClass.asSubclass(ConfigurableAttributeFactory.class);
                              Method m = null;

                              try {
                                 m = factoryClass.getMethod("getConfigurableAttributes", ProvisioningEvent.class);
                              } catch (NoSuchMethodException var24) {
                                 throw new ProvisioningException(var24);
                              } finally {
                                 getConfigurableAttributesMethod = m;
                              }

                              assert m != null;

                              arrayOfAnnotationsOnAllParameters = m.getParameterAnnotations();
                           } while(arrayOfAnnotationsOnAllParameters == null);
                        } while(arrayOfAnnotationsOnAllParameters.length <= 0);

                        assert arrayOfAnnotationsOnAllParameters.length == 1;

                        provisioningEventParameterAnnotations = arrayOfAnnotationsOnAllParameters[0];
                     } while(provisioningEventParameterAnnotations == null);
                  } while(provisioningEventParameterAnnotations.length <= 0);

                  Annotation[] var15 = provisioningEventParameterAnnotations;
                  int var16 = provisioningEventParameterAnnotations.length;

                  for(int var17 = 0; var17 < var16; ++var17) {
                     Annotation parameterAnnotation = var15[var17];

                     assert parameterAnnotation != null;

                     Class annotationType = parameterAnnotation.annotationType();

                     assert annotationType != null;

                     final HandlesResources handlesResources = (HandlesResources)annotationType.getAnnotation(HandlesResources.class);
                     if (handlesResources != null) {
                        ResourceHandlingMethodDescriptor descriptor = new ResourceHandlingMethodDescriptor() {
                           public final HandlesResources getHandlesResourcesQualifier() {
                              return handlesResources;
                           }

                           public final Method getMethod() {
                              return getConfigurableAttributesMethod;
                           }
                        };
                        if (Selectors.isSelected(this.serviceLocator, provisioningResource, descriptor)) {
                           returnValue = (ConfigurableAttributeFactory)ServiceLocatorUtilities.findOrCreateService(this.serviceLocator, factoryClass, new Annotation[0]);
                           break;
                        }
                     }
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getConfigurableAttributeFactory", returnValue);
      }

      return returnValue;
   }

   private static final String remainingDependentsAsString(Map dependents) {
      StringBuilder sb = new StringBuilder("current mappings: {");
      Iterator var2 = dependents.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry e = (Map.Entry)var2.next();
         if (e != null) {
            sb.append("\n\t").append((String)e.getKey()).append(" -> ").append(e.getValue());
         }
      }

      sb.append("\n}");
      return sb.toString();
   }

   public final ConfigurableAttribute getConfigurableAttribute(String componentName, String attributeName) {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "getConfigurableAttribute";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getConfigurableAttribute", new Object[]{componentName, attributeName});
      }

      ComponentMetadata provisioningComponent = (ComponentMetadata)this.serviceLocator.getService(ComponentMetadata.class, componentName, new Annotation[0]);
      ConfigurableAttribute returnValue;
      if (provisioningComponent == null) {
         returnValue = null;
      } else {
         returnValue = provisioningComponent.getConfigurableAttribute(attributeName);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getConfigurableAttribute", returnValue);
      }

      return returnValue;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("ComponentMetadataRegistry {");
      sb.append("\n\tmetadataMap: {");
      Iterable provisioningComponents = this.serviceLocator.getAllServices(ComponentMetadata.class, new Annotation[0]);
      if (provisioningComponents != null) {
         Iterator var3 = provisioningComponents.iterator();

         while(var3.hasNext()) {
            ComponentMetadata provisioningComponent = (ComponentMetadata)var3.next();
            if (provisioningComponent != null) {
               ProvisioningComponentIdentifier id = provisioningComponent.getId();

               assert id != null;

               sb.append("\n\t\t").append(id.getName()).append(" -> ").append(provisioningComponent.toString());
            }
         }
      }

      sb.append("\n\t}\n\tdependencies: {");

      assert this.dependenciesAndAffiliations != null;

      synchronized(this.dependenciesAndAffiliations) {
         Iterator var8 = this.dependenciesAndAffiliations.entrySet().iterator();

         while(true) {
            if (!var8.hasNext()) {
               break;
            }

            Map.Entry e = (Map.Entry)var8.next();
            sb.append("\n\t\t").append(e.getKey()).append(" -> ").append(e.getValue());
         }
      }

      sb.append("\n\t}\n}");
      return sb.toString();
   }

   private final void loadHandlerMethodDescriptors(Iterable componentDescriptors) {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "loadHandlerMethodDescriptors";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "loadHandlerMethodDescriptors", componentDescriptors);
      }

      Objects.requireNonNull(componentDescriptors);
      this.handlerMethodDescriptors.clear();
      Iterator var5 = componentDescriptors.iterator();

      while(var5.hasNext()) {
         Descriptor componentDescriptor = (Descriptor)var5.next();
         if (componentDescriptor != null) {
            Set qualifiers = componentDescriptor.getQualifiers();
            if (qualifiers != null && !qualifiers.isEmpty() && qualifiers.contains(Component.class.getName()) && qualifiers.contains(Handler.class.getName())) {
               String componentName = Components.getComponentName(this.serviceLocator, componentDescriptor);
               String componentVersion = ServiceLocatorUtilities.getOneMetadataField(componentDescriptor, "componentVersion");
               ProvisioningComponentIdentifier componentId = new ProvisioningComponentIdentifier(componentName, componentVersion);
               ActiveDescriptor ad = this.serviceLocator.reifyDescriptor(componentDescriptor);

               assert ad != null;

               Class handlerClass = ad.getImplementationClass();

               assert handlerClass != null;

               this.handlerMethodDescriptors.addAll(createHandlerMethodDescriptors(componentId, handlerClass));
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.logp(Level.FINER, className, "loadHandlerMethodDescriptors", "Initialized handler methods: {0}", this.handlerMethodDescriptors);
         logger.exiting(className, "loadHandlerMethodDescriptors");
      }

   }

   final Collection getHandlerMethodDescriptors(HandlerMethodDescriptor.Filter filter) {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "getHandlerMethodDescriptors";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getHandlerMethodDescriptors", filter);
      }

      Collection returnValue = new ArrayList();
      Iterator var6 = this.handlerMethodDescriptors.iterator();

      while(true) {
         HandlerMethodDescriptor handlerMethodDescriptor;
         do {
            do {
               if (!var6.hasNext()) {
                  Object returnValue;
                  if (returnValue != null && !returnValue.isEmpty()) {
                     returnValue = Collections.unmodifiableCollection(returnValue);
                  } else {
                     returnValue = Collections.emptySet();
                  }

                  if (logger != null && logger.isLoggable(Level.FINER)) {
                     logger.exiting(className, "getHandlerMethodDescriptors", returnValue);
                  }

                  return (Collection)returnValue;
               }

               handlerMethodDescriptor = (HandlerMethodDescriptor)var6.next();
            } while(handlerMethodDescriptor == null);
         } while(filter != null && !filter.accept(handlerMethodDescriptor));

         returnValue.add(handlerMethodDescriptor);
      }
   }

   static final Collection createHandlerMethodDescriptors(ProvisioningComponentIdentifier componentId, Class handlerClass) {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "createHandlerMethodDescriptors";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "createHandlerMethodDescriptors", new Object[]{componentId, handlerClass});
      }

      Objects.requireNonNull(componentId);
      Objects.requireNonNull(handlerClass);
      Collection returnValue = new ArrayList();
      Method[] var6 = handlerClass.getMethods();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Method method = var6[var8];

         assert method != null;

         if (ProvisioningEventMethods.isHandlerMethod(method)) {
            returnValue.add(new HandlerMethodDescriptor(componentId, method, handlerClass));
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "createHandlerMethodDescriptors", returnValue);
      }

      return returnValue;
   }

   private final void inferDependencies(Collection provisioningComponents) {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "inferDependencies";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "inferDependencies", provisioningComponents);
      }

      if (provisioningComponents != null && !provisioningComponents.isEmpty() && !this.handlerMethodDescriptors.isEmpty()) {
         Iterator var5 = provisioningComponents.iterator();

         label66:
         while(true) {
            ComponentMetadata provisioningComponent;
            Set provisioningResources;
            do {
               do {
                  do {
                     if (!var5.hasNext()) {
                        break label66;
                     }

                     provisioningComponent = (ComponentMetadata)var5.next();
                  } while(provisioningComponent == null);

                  provisioningResources = provisioningComponent.getProvisioningResources();
               } while(provisioningResources == null);
            } while(provisioningResources.isEmpty());

            Iterator var8 = provisioningResources.iterator();

            while(true) {
               ProvisioningResource provisioningResource;
               do {
                  if (!var8.hasNext()) {
                     continue label66;
                  }

                  provisioningResource = (ProvisioningResource)var8.next();
               } while(provisioningResource == null);

               Iterator var10 = this.handlerMethodDescriptors.iterator();

               while(var10.hasNext()) {
                  HandlerMethodDescriptor handlerMethodDescriptor = (HandlerMethodDescriptor)var10.next();
                  if (handlerMethodDescriptor != null && Selectors.isSelected(this.serviceLocator, provisioningResource, handlerMethodDescriptor)) {
                     provisioningComponent.addDependentComponentName(handlerMethodDescriptor.getComponentId().getName());
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "inferDependencies");
      }

   }

   final ProvisioningComponent detectInjectedConfigurableAttributes(ActiveDescriptor activeDescriptor, Map metadataMap) {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "detectInjectedConfigurableAttributes";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "detectInjectedConfigurableAttributes", new Object[]{activeDescriptor, metadataMap});
      }

      Objects.requireNonNull(activeDescriptor);
      Objects.requireNonNull(metadataMap);
      ComponentMetadata provisioningComponent = null;
      String componentName = Components.getComponentName(this.serviceLocator, activeDescriptor);
      if (componentName != null && !componentName.isEmpty()) {
         provisioningComponent = (ComponentMetadata)metadataMap.get(componentName);
         if (provisioningComponent == null) {
            provisioningComponent = this.createProvisioningComponent(componentName, activeDescriptor);
            if (provisioningComponent == null) {
               throw new IllegalStateException("createProvisioningComponent() == null");
            }

            metadataMap.put(componentName, provisioningComponent);
         }

         assert provisioningComponent != null;

         assert provisioningComponent.getId() != null;

         assert componentName.equals(provisioningComponent.getId().getName());

         provisioningComponent = detectInjectedConfigurableAttributes(this.serviceLocator.reifyDescriptor(activeDescriptor), provisioningComponent);
      } else if (logger != null && logger.isLoggable(Level.FINE)) {
         logger.logp(Level.FINE, className, "detectInjectedConfigurableAttributes", "No component name found for descriptor {0}", activeDescriptor);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "detectInjectedConfigurableAttributes", provisioningComponent);
      }

      return provisioningComponent;
   }

   static final ComponentMetadata detectInjectedConfigurableAttributes(ActiveDescriptor activeDescriptor, ComponentMetadata provisioningComponent) {
      String className = ComponentMetadataRegistry.class.getName();
      String methodName = "detectInjectedConfigurableAttributes";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "detectInjectedConfigurableAttributes", new Object[]{activeDescriptor, provisioningComponent});
      }

      Objects.requireNonNull(activeDescriptor);
      Objects.requireNonNull(provisioningComponent);
      if (!activeDescriptor.isReified()) {
         throw new IllegalArgumentException("activeDescriptor", new IllegalStateException("!activeDescriptor.isReified()"));
      } else {
         Collection injectees = activeDescriptor.getInjectees();
         if (injectees != null && !injectees.isEmpty()) {
            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.logp(Level.FINER, className, "detectInjectedConfigurableAttributes", "Discovered the following injectees for descriptor {0}: {1}", new Object[]{activeDescriptor, injectees});
            }

            Iterator var6 = injectees.iterator();

            label65:
            while(true) {
               Injectee injectee;
               AnnotatedElement injecteeParent;
               ConfigurableAttribute ca;
               do {
                  do {
                     do {
                        if (!var6.hasNext()) {
                           break label65;
                        }

                        injectee = (Injectee)var6.next();
                     } while(injectee == null);

                     injecteeParent = injectee.getParent();
                  } while(injecteeParent == null);

                  ca = (ConfigurableAttribute)injecteeParent.getAnnotation(ConfigurableAttribute.class);
               } while(ca == null);

               String name = ca.name();
               if (name == null || name.trim().isEmpty()) {
                  if (injecteeParent instanceof Field) {
                     name = ((Field)injecteeParent).getName();
                  } else {
                     name = null;
                  }
               }

               if (name != null) {
                  ConfigurableAttribute old = provisioningComponent.putConfigurableAttribute(name, ca);
                  if (logger != null && logger.isLoggable(Level.FINER)) {
                     logger.logp(Level.FINER, className, "detectInjectedConfigurableAttributes", "Found ConfigurableAttribute-annotated injectee ({0}); put {1} yielding {2}", new Object[]{injectee, ca, old});
                  }
               }
            }
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "detectInjectedConfigurableAttributes", provisioningComponent);
         }

         return provisioningComponent;
      }
   }

   /** @deprecated */
   @Deprecated
   private static final Set asAffiliateNames(Iterable ids) {
      Set returnValue = new HashSet();
      if (ids != null) {
         Iterator var2 = ids.iterator();

         while(var2.hasNext()) {
            ProvisioningComponentIdentifier id = (ProvisioningComponentIdentifier)var2.next();
            if (id != null) {
               returnValue.add(id.getName());
            } else {
               returnValue.add((Object)null);
            }
         }
      }

      return returnValue;
   }

   private static final class ProvisioningComponentFilter implements IndexedFilter {
      private final ServiceLocator serviceLocator;
      private final ProvisioningComponentIdentifier id;

      private ProvisioningComponentFilter(ServiceLocator serviceLocator, ProvisioningComponentIdentifier id) {
         Objects.requireNonNull(serviceLocator);
         Objects.requireNonNull(id);
         this.serviceLocator = serviceLocator;
         this.id = id;
      }

      public final String getAdvertisedContract() {
         return ProvisioningComponent.class.getName();
      }

      public final String getName() {
         return this.id.getName();
      }

      public final boolean matches(Descriptor descriptor) {
         boolean returnValue = false;
         if (descriptor != null && this.id != null) {
            String name = this.id.getName();
            if (name != null) {
               String candidateName = Components.getComponentName(this.serviceLocator, descriptor);
               if (name.equals(candidateName)) {
                  String version = this.id.getVersion();
                  if (version == null) {
                     returnValue = true;
                  } else {
                     returnValue = version.equals(ServiceLocatorUtilities.getOneMetadataField(descriptor, "componentVersion"));
                  }
               }
            }
         }

         return returnValue;
      }

      // $FF: synthetic method
      ProvisioningComponentFilter(ServiceLocator x0, ProvisioningComponentIdentifier x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class NonProvisioningComponentFactoryPopulatorFilter implements IndexedFilter {
      private NonProvisioningComponentFactoryPopulatorFilter() {
      }

      public final String getAdvertisedContract() {
         return ComponentMetadataRegistryPopulator.class.getName();
      }

      public final String getName() {
         return null;
      }

      public final boolean matches(Descriptor descriptor) {
         boolean returnValue;
         if (descriptor == null) {
            returnValue = false;
         } else {
            Set contracts = descriptor.getAdvertisedContracts();
            returnValue = contracts != null && contracts.contains(ComponentMetadataRegistryPopulator.class.getName()) && !contracts.contains(ProvisioningComponentFactory.class.getName());
         }

         return returnValue;
      }

      // $FF: synthetic method
      NonProvisioningComponentFactoryPopulatorFilter(Object x0) {
         this();
      }
   }

   private static final class ComponentFilter implements Filter {
      private ComponentFilter() {
      }

      public final boolean matches(Descriptor descriptor) {
         boolean returnValue;
         if (descriptor == null) {
            returnValue = false;
         } else {
            Collection qualifiers = descriptor.getQualifiers();
            returnValue = qualifiers != null && qualifiers.contains(Component.class.getName());
         }

         return returnValue;
      }

      // $FF: synthetic method
      ComponentFilter(Object x0) {
         this();
      }
   }
}

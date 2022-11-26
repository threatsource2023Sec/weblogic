package org.glassfish.hk2.configuration.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationListener;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.configuration.api.ConfiguredBy;
import org.glassfish.hk2.configuration.api.PostDynamicChange;
import org.glassfish.hk2.configuration.api.PreDynamicChange;
import org.glassfish.hk2.configuration.hub.api.BeanDatabase;
import org.glassfish.hk2.configuration.hub.api.BeanDatabaseUpdateListener;
import org.glassfish.hk2.configuration.hub.api.Change;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.Instance;
import org.glassfish.hk2.configuration.hub.api.Type;
import org.glassfish.hk2.configuration.hub.api.Change.ChangeCategory;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.glassfish.hk2.utilities.reflection.internal.ClassReflectionHelperImpl;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class ConfigurationListener implements BeanDatabaseUpdateListener {
   @Inject
   private Hub hub;
   @Inject
   private ServiceLocator locator;
   @Inject
   private DynamicConfigurationService configurationService;
   @Inject
   private ConfiguredByInjectionResolver injectionResolver;
   @Inject
   private ConfiguredByContext context;
   private final ConcurrentHashMap typeInformation = new ConcurrentHashMap();
   private final Object progenitorLock = new Object();
   private HashSet allProgenitors = new HashSet();

   @PostConstruct
   private void postConstruct() {
      this.initialize(this.hub.getCurrentDatabase());
   }

   private ActiveDescriptor addInstanceDescriptor(DynamicConfiguration config, ActiveDescriptor parent, String name, String type, Object bean, Object metadata) {
      DelegatingNamedActiveDescriptor addMe = new DelegatingNamedActiveDescriptor(parent, name);
      ActiveDescriptor systemDescriptor = config.addActiveDescriptor(addMe);
      this.injectionResolver.addBean(systemDescriptor, bean, type, metadata);
      return systemDescriptor;
   }

   private boolean invokePreMethod(Object target, List changes, String typeName) {
      ModificationInformation modInfo = (ModificationInformation)this.typeInformation.get(typeName);
      if (modInfo == null) {
         return true;
      } else {
         Method preMethod = modInfo.getPreDynamicChangeMethod(target.getClass());
         if (preMethod == null) {
            return true;
         } else {
            Class[] params = preMethod.getParameterTypes();
            Object result = null;
            if (params.length <= 0) {
               try {
                  result = ReflectionHelper.invoke(target, preMethod, new Object[0], true);
               } catch (Throwable var11) {
                  return true;
               }
            } else {
               if (params.length != 1) {
                  return true;
               }

               if (!List.class.isAssignableFrom(params[0])) {
                  return true;
               }

               Object[] pValues = new Object[]{Collections.unmodifiableList(changes)};

               try {
                  result = ReflectionHelper.invoke(target, preMethod, pValues, true);
               } catch (Throwable var10) {
                  return true;
               }
            }

            if (result != null && result instanceof Boolean) {
               Boolean b = (Boolean)result;
               return b;
            } else {
               return true;
            }
         }
      }
   }

   private void invokePostMethod(Object target, List changes, String typeName) {
      ModificationInformation modInfo = (ModificationInformation)this.typeInformation.get(typeName);
      if (modInfo != null) {
         Method postMethod = modInfo.getPostDynamicChangeMethod(target.getClass());
         if (postMethod != null) {
            Class[] params = postMethod.getParameterTypes();
            if (params.length <= 0) {
               try {
                  ReflectionHelper.invoke(target, postMethod, new Object[0], true);
               } catch (Throwable var10) {
                  return;
               }
            } else if (params.length == 1) {
               if (!List.class.isAssignableFrom(params[0])) {
                  return;
               }

               Object[] pValues = new Object[]{Collections.unmodifiableList(changes)};

               try {
                  ReflectionHelper.invoke(target, postMethod, pValues, true);
               } catch (Throwable var9) {
                  return;
               }
            }

         }
      }
   }

   private void modifyInstanceDescriptor(ActiveDescriptor parent, String name, Object bean, Object metadata, String typeName, List changes) {
      BeanInfo modifiedInfo = this.injectionResolver.addBean(parent, bean, typeName, metadata);
      Object target = this.context.findOnly(parent);
      if (target != null) {
         boolean moveForward = this.invokePreMethod(target, changes, typeName);
         if (moveForward) {
            HashMap changedProperties = new HashMap();
            Iterator var11 = changes.iterator();

            while(var11.hasNext()) {
               PropertyChangeEvent pce = (PropertyChangeEvent)var11.next();
               changedProperties.put(pce.getPropertyName(), pce);
               if (target instanceof PropertyChangeListener) {
                  PropertyChangeListener listener = (PropertyChangeListener)target;

                  try {
                     listener.propertyChange(pce);
                  } catch (Throwable var22) {
                  }
               }
            }

            HashMap dynamicMethods = new HashMap();
            HashSet notDynamicMethods = new HashSet();
            Iterator var25 = parent.getInjectees().iterator();

            while(var25.hasNext()) {
               Injectee injectee = (Injectee)var25.next();
               AnnotatedElement ae = injectee.getParent();
               if (ae != null) {
                  if (ae instanceof Field) {
                     String propName = BeanUtilities.getParameterNameFromField((Field)ae, true);
                     if (propName != null) {
                        PropertyChangeEvent pce = (PropertyChangeEvent)changedProperties.get(propName);
                        if (pce != null) {
                           try {
                              ReflectionHelper.setField((Field)ae, target, pce.getNewValue());
                           } catch (Throwable var21) {
                           }
                        }
                     }
                  } else if (ae instanceof Method) {
                     Method method = (Method)ae;
                     if (!notDynamicMethods.contains(method)) {
                        if (!BeanUtilities.hasDynamicParameter(method)) {
                           notDynamicMethods.add(method);
                        } else {
                           Object[] params = (Object[])dynamicMethods.get(method);
                           if (params == null) {
                              params = new Object[method.getParameterTypes().length];
                              dynamicMethods.put(method, params);
                           }

                           String propName = BeanUtilities.getParameterNameFromMethod(method, injectee.getPosition());
                           if (propName == null) {
                              ActiveDescriptor paramDescriptor = this.locator.getInjecteeDescriptor(injectee);
                              if (paramDescriptor == null) {
                                 params[injectee.getPosition()] = null;
                              } else {
                                 params[injectee.getPosition()] = this.locator.getServiceHandle(paramDescriptor).getService();
                              }
                           } else {
                              PropertyChangeEvent pce = (PropertyChangeEvent)changedProperties.get(propName);
                              if (pce != null) {
                                 params[injectee.getPosition()] = pce.getNewValue();
                              } else {
                                 params[injectee.getPosition()] = BeanUtilities.getBeanPropertyValue(injectee.getRequiredType(), propName, modifiedInfo);
                              }
                           }
                        }
                     }
                  }
               }
            }

            var25 = dynamicMethods.entrySet().iterator();

            while(var25.hasNext()) {
               Map.Entry entries = (Map.Entry)var25.next();

               try {
                  ReflectionHelper.invoke(target, (Method)entries.getKey(), (Object[])entries.getValue(), true);
               } catch (Throwable var20) {
               }
            }

            this.invokePostMethod(target, changes, typeName);
         }
      }
   }

   private void initialize(BeanDatabase database) {
      Set allTypes = database.getAllTypes();
      LinkedList added = new LinkedList();
      DynamicConfiguration config = this.configurationService.createDynamicConfiguration();
      Iterator var5 = allTypes.iterator();

      while(var5.hasNext()) {
         Type type = (Type)var5.next();
         String typeName = type.getName();
         this.typeInformation.put(typeName, new ModificationInformation());
         List typeDescriptors = this.locator.getDescriptors(new NoNameTypeFilter(this.locator, typeName, (String)null));
         Iterator var9 = typeDescriptors.iterator();

         while(var9.hasNext()) {
            ActiveDescriptor typeDescriptor = (ActiveDescriptor)var9.next();
            Map typeInstances = type.getInstances();
            Iterator var12 = typeInstances.entrySet().iterator();

            while(var12.hasNext()) {
               Map.Entry entry = (Map.Entry)var12.next();
               added.add(this.addInstanceDescriptor(config, typeDescriptor, (String)entry.getKey(), typeName, ((Instance)entry.getValue()).getBean(), ((Instance)entry.getValue()).getMetadata()));
            }
         }
      }

      List progenitors = this.locator.getDescriptors(new NoNameTypeFilter(this.locator, (String)null, (String)null));
      synchronized(this.progenitorLock) {
         this.allProgenitors.addAll(progenitors);
      }

      config.addActiveDescriptor(DescriptorListener.class);
      config.commit();
      Iterator var17 = added.iterator();

      while(var17.hasNext()) {
         ActiveDescriptor descriptor = (ActiveDescriptor)var17.next();
         if (isEager(descriptor)) {
            ServiceHandle handle = this.locator.getServiceHandle(descriptor);
            handle.getService();
         }
      }

   }

   private static boolean isEager(ActiveDescriptor descriptor) {
      Class implClass = descriptor.getImplementationClass();
      if (implClass == null) {
         return false;
      } else {
         ConfiguredBy configuredBy = (ConfiguredBy)implClass.getAnnotation(ConfiguredBy.class);
         return configuredBy == null ? false : ConfiguredBy.CreationPolicy.EAGER.equals(configuredBy.creationPolicy());
      }
   }

   private String getTypeFromConfiguredBy(ActiveDescriptor descriptor) {
      if (!descriptor.isReified()) {
         descriptor = this.locator.reifyDescriptor(descriptor);
      }

      Class implClass = descriptor.getImplementationClass();
      ConfiguredBy configuredBy = (ConfiguredBy)implClass.getAnnotation(ConfiguredBy.class);
      if (configuredBy == null) {
         throw new AssertionError("May only give this method ConfiguredBy descriptors");
      } else {
         return configuredBy.value();
      }
   }

   public void commitDatabaseChange(BeanDatabase reference, BeanDatabase newDatabase, Object commitMessage, List changes) {
      LinkedList added = new LinkedList();
      LinkedList removed = new LinkedList();
      DynamicConfiguration config = this.configurationService.createDynamicConfiguration();
      Iterator var8 = changes.iterator();

      while(true) {
         while(var8.hasNext()) {
            Change change = (Change)var8.next();
            String removedInstanceKey;
            List typeDescriptors;
            Iterator var15;
            ActiveDescriptor typeDescriptor;
            Object modifiedInstanceBean;
            Object modifiedMetadata;
            if (ChangeCategory.ADD_INSTANCE.equals(change.getChangeCategory())) {
               removedInstanceKey = change.getChangeType().getName();
               if (!this.typeInformation.containsKey(removedInstanceKey)) {
                  this.typeInformation.put(removedInstanceKey, new ModificationInformation());
               }

               String addedInstanceKey = change.getInstanceKey();
               modifiedInstanceBean = change.getInstanceValue().getBean();
               modifiedMetadata = change.getInstanceValue().getMetadata();
               typeDescriptors = this.locator.getDescriptors(new NoNameTypeFilter(this.locator, change.getChangeType().getName(), (String)null));
               var15 = typeDescriptors.iterator();

               while(var15.hasNext()) {
                  typeDescriptor = (ActiveDescriptor)var15.next();
                  added.add(this.addInstanceDescriptor(config, typeDescriptor, addedInstanceKey, removedInstanceKey, modifiedInstanceBean, modifiedMetadata));
               }
            } else if (ChangeCategory.MODIFY_INSTANCE.equals(change.getChangeCategory())) {
               removedInstanceKey = change.getInstanceKey();
               Instance instance = change.getInstanceValue();
               modifiedInstanceBean = instance != null ? instance.getBean() : null;
               modifiedMetadata = instance != null ? instance.getMetadata() : null;
               typeDescriptors = this.locator.getDescriptors(new NoNameTypeFilter(this.locator, change.getChangeType().getName(), removedInstanceKey));
               var15 = typeDescriptors.iterator();

               while(var15.hasNext()) {
                  typeDescriptor = (ActiveDescriptor)var15.next();
                  this.modifyInstanceDescriptor(typeDescriptor, removedInstanceKey, modifiedInstanceBean, modifiedMetadata, change.getChangeType().getName(), change.getModifiedProperties());
               }
            } else if (ChangeCategory.REMOVE_TYPE.equals(change.getChangeCategory())) {
               removedInstanceKey = change.getChangeType().getName();
               ModificationInformation cache = (ModificationInformation)this.typeInformation.remove(removedInstanceKey);
               if (cache != null) {
                  cache.dispose();
               }
            } else if (ChangeCategory.REMOVE_INSTANCE.equals(change.getChangeCategory())) {
               removedInstanceKey = change.getInstanceKey();
               List removeDescriptors = this.locator.getDescriptors(new NoNameTypeFilter(this.locator, change.getChangeType().getName(), removedInstanceKey));
               Iterator var12 = removeDescriptors.iterator();

               while(var12.hasNext()) {
                  ActiveDescriptor removeDescriptor = (ActiveDescriptor)var12.next();
                  config.addUnbindFilter(BuilderHelper.createSpecificDescriptorFilter(removeDescriptor));
                  this.injectionResolver.removeBean(removeDescriptor);
                  removed.add(removeDescriptor);
               }
            }
         }

         if (!added.isEmpty() || !removed.isEmpty()) {
            config.commit();
            var8 = added.iterator();

            ActiveDescriptor descriptor;
            ServiceHandle handle;
            while(var8.hasNext()) {
               descriptor = (ActiveDescriptor)var8.next();
               if (isEager(descriptor)) {
                  handle = this.locator.getServiceHandle(descriptor);
                  handle.getService();
               }
            }

            var8 = removed.iterator();

            while(var8.hasNext()) {
               descriptor = (ActiveDescriptor)var8.next();
               handle = this.locator.getServiceHandle(descriptor);
               handle.destroy();
            }
         }

         return;
      }
   }

   private void calculateProgenitorAddsAndRemoves() {
      BeanDatabase database = this.hub.getCurrentDatabase();
      DynamicConfiguration config = this.configurationService.createDynamicConfiguration();
      final LinkedList addedList = new LinkedList();
      final LinkedList removedList = new LinkedList();
      synchronized(this.progenitorLock) {
         HashSet removed = new HashSet(this.allProgenitors);
         List progenitors = this.locator.getDescriptors(new NoNameTypeFilter(this.locator, (String)null, (String)null));
         this.allProgenitors = new HashSet(progenitors);
         HashSet added = new HashSet(this.allProgenitors);
         added.removeAll(removed);
         removed.removeAll(progenitors);
         Iterator var9 = added.iterator();

         while(var9.hasNext()) {
            ActiveDescriptor addMe = (ActiveDescriptor)var9.next();
            String typeName = this.getTypeFromConfiguredBy(addMe);
            this.typeInformation.putIfAbsent(typeName, new ModificationInformation());
            Type type = database.getType(typeName);
            if (type != null) {
               Iterator var13 = type.getInstances().entrySet().iterator();

               while(var13.hasNext()) {
                  Map.Entry instance = (Map.Entry)var13.next();
                  String addedInstanceKey = (String)instance.getKey();
                  Object addedInstanceBean = ((Instance)instance.getValue()).getBean();
                  Object addedInstanceMetadata = ((Instance)instance.getValue()).getMetadata();
                  addedList.add(this.addInstanceDescriptor(config, addMe, addedInstanceKey, typeName, addedInstanceBean, addedInstanceMetadata));
               }
            }
         }
      }

      if (!addedList.isEmpty() || !removedList.isEmpty()) {
         config.commit();
         (new Thread(new Runnable() {
            public void run() {
               Iterator var1 = addedList.iterator();

               ActiveDescriptor descriptor;
               ServiceHandle handle;
               while(var1.hasNext()) {
                  descriptor = (ActiveDescriptor)var1.next();
                  if (ConfigurationListener.isEager(descriptor)) {
                     handle = ConfigurationListener.this.locator.getServiceHandle(descriptor);
                     handle.getService();
                  }
               }

               var1 = removedList.iterator();

               while(var1.hasNext()) {
                  descriptor = (ActiveDescriptor)var1.next();
                  handle = ConfigurationListener.this.locator.getServiceHandle(descriptor);
                  handle.destroy();
               }

            }
         })).start();
      }

   }

   public void prepareDatabaseChange(BeanDatabase currentDatabase, BeanDatabase proposedDatabase, Object commitMessage, List changes) {
   }

   public void rollbackDatabaseChange(BeanDatabase currentDatabase, BeanDatabase proposedDatabase, Object commitMessage, List changes) {
   }

   public String toString() {
      return "ConfigurationListener(" + System.identityHashCode(this) + ")";
   }

   @Singleton
   private static class DescriptorListener implements DynamicConfigurationListener {
      @Inject
      private ConfigurationListener parent;

      public void configurationChanged() {
         this.parent.calculateProgenitorAddsAndRemoves();
      }
   }

   private static class ModificationInformation {
      private final ClassReflectionHelper helper;
      private final HashMap preMethods;
      private final HashMap postMethods;

      private ModificationInformation() {
         this.helper = new ClassReflectionHelperImpl();
         this.preMethods = new HashMap();
         this.postMethods = new HashMap();
      }

      private Method getPreDynamicChangeMethod(Class rawClass) {
         if (this.preMethods.containsKey(rawClass)) {
            return (Method)this.preMethods.get(rawClass);
         } else {
            Method preModificationMethod = this.getSpecialMethod(rawClass, PreDynamicChange.class);
            this.preMethods.put(rawClass, preModificationMethod);
            return preModificationMethod;
         }
      }

      private Method getPostDynamicChangeMethod(Class rawClass) {
         if (this.postMethods.containsKey(rawClass)) {
            return (Method)this.postMethods.get(rawClass);
         } else {
            Method postModificationMethod = this.getSpecialMethod(rawClass, PostDynamicChange.class);
            this.postMethods.put(rawClass, postModificationMethod);
            return postModificationMethod;
         }
      }

      private Method getSpecialMethod(Class rawClass, Class anno) {
         Set wrappers = this.helper.getAllMethods(rawClass);
         Iterator var4 = wrappers.iterator();

         Method candidate;
         do {
            if (!var4.hasNext()) {
               return null;
            }

            MethodWrapper wrapper = (MethodWrapper)var4.next();
            candidate = wrapper.getMethod();
         } while(candidate.getAnnotation(anno) == null);

         return candidate;
      }

      private void dispose() {
         this.helper.dispose();
         this.preMethods.clear();
         this.postMethods.clear();
      }

      // $FF: synthetic method
      ModificationInformation(Object x0) {
         this();
      }
   }
}

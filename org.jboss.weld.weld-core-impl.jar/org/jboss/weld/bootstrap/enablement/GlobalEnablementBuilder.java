package org.jboss.weld.bootstrap.enablement;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.bootstrap.BeanDeployment;
import org.jboss.weld.bootstrap.api.helpers.AbstractBootstrapService;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.logging.LogMessageCallback;
import org.jboss.weld.logging.MessageCallback;
import org.jboss.weld.logging.ValidatorLogger;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.jboss.weld.resources.spi.ResourceLoadingException;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.collections.ImmutableMap;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class GlobalEnablementBuilder extends AbstractBootstrapService {
   private final List alternatives = Collections.synchronizedList(new ArrayList());
   private final List interceptors = Collections.synchronizedList(new ArrayList());
   private final List decorators = Collections.synchronizedList(new ArrayList());
   private volatile Map cachedAlternativeMap;
   private volatile boolean sorted;
   private volatile boolean dirty;

   private void addItem(List list, Class javaClass, int priority) {
      this.sorted = false;
      this.dirty = true;
      synchronized(list) {
         if (!list.isEmpty()) {
            int scaling = ((Item)list.get(0)).getNumberOfScaling();
            if (scaling > 0) {
               priority *= (new BigInteger("10")).pow(scaling).intValue();
            }
         }

         list.add(new Item(javaClass, priority, priority));
      }
   }

   public void addAlternative(Class javaClass, int priority) {
      this.addItem(this.alternatives, javaClass, priority);
   }

   public void addInterceptor(Class javaClass, int priority) {
      this.addItem(this.interceptors, javaClass, priority);
   }

   public void addDecorator(Class javaClass, int priority) {
      this.addItem(this.decorators, javaClass, priority);
   }

   public List getAlternativeList(final Extension extension) {
      this.initialize();
      return new EnablementListView() {
         protected Extension getExtension() {
            return extension;
         }

         protected EnablementListView.ViewType getViewType() {
            return EnablementListView.ViewType.ALTERNATIVES;
         }

         protected List getDelegate() {
            return GlobalEnablementBuilder.this.alternatives;
         }
      };
   }

   public List getInterceptorList(final Extension extension) {
      this.initialize();
      return new EnablementListView() {
         protected Extension getExtension() {
            return extension;
         }

         protected EnablementListView.ViewType getViewType() {
            return EnablementListView.ViewType.INTERCEPTORS;
         }

         protected List getDelegate() {
            return GlobalEnablementBuilder.this.interceptors;
         }
      };
   }

   public List getDecoratorList(final Extension extension) {
      this.initialize();
      return new EnablementListView() {
         protected Extension getExtension() {
            return extension;
         }

         protected EnablementListView.ViewType getViewType() {
            return EnablementListView.ViewType.DECORATORS;
         }

         protected List getDelegate() {
            return GlobalEnablementBuilder.this.decorators;
         }
      };
   }

   public boolean isDirty() {
      return this.dirty;
   }

   private Map getGlobalAlternativeMap() {
      if (this.cachedAlternativeMap == null || this.dirty) {
         Map map = new HashMap();
         Iterator var2 = this.alternatives.iterator();

         while(var2.hasNext()) {
            Item item = (Item)var2.next();
            map.put(item.getJavaClass(), item.getPriority());
         }

         this.cachedAlternativeMap = ImmutableMap.copyOf(map);
      }

      return this.cachedAlternativeMap;
   }

   private void initialize() {
      if (!this.sorted) {
         Collections.sort(this.alternatives);
         Collections.sort(this.interceptors);
         Collections.sort(this.decorators);
         this.sorted = true;
      }

   }

   public ModuleEnablement createModuleEnablement(BeanDeployment deployment) {
      ClassLoader loader = new ClassLoader((ResourceLoader)deployment.getBeanManager().getServices().get(ResourceLoader.class));
      BeansXml beansXml = deployment.getBeanDeploymentArchive().getBeansXml();
      Set alternativeClasses = null;
      Set alternativeStereotypes = null;
      List globallyEnabledInterceptors = this.getInterceptorList((Extension)null);
      List globallyEnabledDecorators = this.getDecoratorList((Extension)null);
      ImmutableList.Builder moduleInterceptorsBuilder = ImmutableList.builder();
      moduleInterceptorsBuilder.addAll((Iterable)globallyEnabledInterceptors);
      ImmutableList.Builder moduleDecoratorsBuilder = ImmutableList.builder();
      moduleDecoratorsBuilder.addAll((Iterable)globallyEnabledDecorators);
      if (beansXml != null) {
         this.checkForDuplicates(beansXml.getEnabledInterceptors(), ValidatorLogger.INTERCEPTOR_SPECIFIED_TWICE);
         this.checkForDuplicates(beansXml.getEnabledDecorators(), ValidatorLogger.DECORATOR_SPECIFIED_TWICE);
         this.checkForDuplicates(beansXml.getEnabledAlternativeClasses(), ValidatorLogger.ALTERNATIVE_CLASS_SPECIFIED_MULTIPLE_TIMES);
         this.checkForDuplicates(beansXml.getEnabledAlternativeStereotypes(), ValidatorLogger.ALTERNATIVE_STEREOTYPE_SPECIFIED_MULTIPLE_TIMES);
         List interceptorClasses = (List)beansXml.getEnabledInterceptors().stream().map(loader).collect(Collectors.toList());
         moduleInterceptorsBuilder.addAll((Iterable)this.filter(interceptorClasses, globallyEnabledInterceptors, ValidatorLogger.INTERCEPTOR_ENABLED_FOR_APP_AND_ARCHIVE, deployment));
         List decoratorClasses = (List)beansXml.getEnabledDecorators().stream().map(loader).collect(Collectors.toList());
         moduleDecoratorsBuilder.addAll((Iterable)this.filter(decoratorClasses, globallyEnabledDecorators, ValidatorLogger.DECORATOR_ENABLED_FOR_APP_AND_ARCHIVE, deployment));
         alternativeClasses = (Set)beansXml.getEnabledAlternativeClasses().stream().map(loader).collect(ImmutableSet.collector());
         alternativeStereotypes = (Set)Reflections.cast(beansXml.getEnabledAlternativeStereotypes().stream().map(loader).collect(ImmutableSet.collector()));
      } else {
         alternativeClasses = Collections.emptySet();
         alternativeStereotypes = Collections.emptySet();
      }

      Map globalAlternatives = this.getGlobalAlternativeMap();
      this.dirty = false;
      return new ModuleEnablement(moduleInterceptorsBuilder.build(), moduleDecoratorsBuilder.build(), globalAlternatives, alternativeClasses, alternativeStereotypes);
   }

   public void cleanupAfterBoot() {
      this.alternatives.clear();
      this.interceptors.clear();
      this.decorators.clear();
   }

   public String toString() {
      return "GlobalEnablementBuilder [alternatives=" + this.alternatives + ", interceptors=" + this.interceptors + ", decorators=" + this.decorators + "]";
   }

   private void checkForDuplicates(List list, MessageCallback messageCallback) {
      Map map = new HashMap();
      Iterator var4 = list.iterator();

      Metadata item;
      Metadata previousOccurrence;
      do {
         if (!var4.hasNext()) {
            return;
         }

         item = (Metadata)var4.next();
         previousOccurrence = (Metadata)map.put(item.getValue(), item);
      } while(previousOccurrence == null);

      throw (DeploymentException)messageCallback.construct(item.getValue(), item, previousOccurrence);
   }

   private List filter(List enabledClasses, List globallyEnabledClasses, LogMessageCallback logMessageCallback, BeanDeployment deployment) {
      Iterator iterator = enabledClasses.iterator();

      while(iterator.hasNext()) {
         Class enabledClass = (Class)iterator.next();
         if (globallyEnabledClasses.contains(enabledClass)) {
            logMessageCallback.log(enabledClass, deployment.getBeanDeploymentArchive().getId());
            iterator.remove();
         }
      }

      return enabledClasses;
   }

   private static class ClassLoader implements Function {
      private final ResourceLoader resourceLoader;

      public ClassLoader(ResourceLoader resourceLoader) {
         this.resourceLoader = resourceLoader;
      }

      public Class apply(Metadata from) {
         try {
            return this.resourceLoader.classForName((String)from.getValue());
         } catch (ResourceLoadingException var3) {
            throw BootstrapLogger.LOG.errorLoadingBeansXmlEntry(from.getValue(), from.getLocation(), var3.getCause());
         } catch (Exception var4) {
            throw BootstrapLogger.LOG.errorLoadingBeansXmlEntry(from.getValue(), from.getLocation(), var4);
         }
      }
   }
}

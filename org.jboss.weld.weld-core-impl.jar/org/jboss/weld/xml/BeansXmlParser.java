package org.jboss.weld.xml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import org.jboss.weld.bootstrap.spi.BeansXml;
import org.jboss.weld.bootstrap.spi.Metadata;
import org.jboss.weld.config.SystemPropertiesConfiguration;
import org.jboss.weld.metadata.BeansXmlImpl;
import org.jboss.weld.metadata.ScanningImpl;

public class BeansXmlParser {
   private final BeansXmlValidator beansXmlValidator;

   public BeansXmlParser() {
      this.beansXmlValidator = SystemPropertiesConfiguration.INSTANCE.isXmlValidationDisabled() ? null : new BeansXmlValidator();
   }

   public BeansXml parse(URL beansXml) {
      BeansXmlHandler handler = this.getHandler(beansXml);
      if (this.beansXmlValidator != null) {
         this.beansXmlValidator.validate(beansXml, handler);
      }

      return handler != null ? (new BeansXmlStreamParser(beansXml, (text) -> {
         return handler.interpolate(text);
      })).parse() : (new BeansXmlStreamParser(beansXml)).parse();
   }

   public BeansXml parse(Iterable urls) {
      return this.parse(urls, false);
   }

   public BeansXml parse(Iterable urls, boolean removeDuplicates) {
      return merge(urls, this::parse, removeDuplicates);
   }

   protected BeansXmlHandler getHandler(URL beansXml) {
      return null;
   }

   public static BeansXml merge(Iterable items, Function function, boolean removeDuplicates) {
      List alternatives = new ArrayList();
      List alternativeStereotypes = new ArrayList();
      List decorators = new ArrayList();
      List interceptors = new ArrayList();
      List includes = new ArrayList();
      List excludes = new ArrayList();
      boolean isTrimmed = false;
      URL beansXmlUrl = null;

      BeansXml beansXml;
      for(Iterator var11 = items.iterator(); var11.hasNext(); beansXmlUrl = beansXml.getUrl()) {
         Object item = var11.next();
         beansXml = (BeansXml)function.apply(item);
         addTo(alternatives, beansXml.getEnabledAlternativeClasses(), removeDuplicates);
         addTo(alternativeStereotypes, beansXml.getEnabledAlternativeStereotypes(), removeDuplicates);
         addTo(decorators, beansXml.getEnabledDecorators(), removeDuplicates);
         addTo(interceptors, beansXml.getEnabledInterceptors(), removeDuplicates);
         includes.addAll(beansXml.getScanning().getIncludes());
         excludes.addAll(beansXml.getScanning().getExcludes());
         isTrimmed = beansXml.isTrimmed();
      }

      return new BeansXmlImpl(alternatives, alternativeStereotypes, decorators, interceptors, new ScanningImpl(includes, excludes), beansXmlUrl, BeanDiscoveryMode.ALL, (String)null, isTrimmed);
   }

   private static void addTo(List list, List listToAdd, boolean removeDuplicates) {
      if (removeDuplicates) {
         List filteredListToAdd = new ArrayList(((List)listToAdd).size());
         Iterator var4 = ((List)listToAdd).iterator();

         while(var4.hasNext()) {
            Metadata metadata = (Metadata)var4.next();
            if (!alreadyAdded(metadata, list)) {
               filteredListToAdd.add(metadata);
            }
         }

         listToAdd = filteredListToAdd;
      }

      list.addAll((Collection)listToAdd);
   }

   private static boolean alreadyAdded(Metadata metadata, List list) {
      Iterator var2 = list.iterator();

      Metadata existing;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         existing = (Metadata)var2.next();
      } while(!((String)existing.getValue()).equals(metadata.getValue()));

      return true;
   }

   public static BeansXml mergeExisting(Iterable beanArchives, boolean removeDuplicates) {
      return merge(beanArchives, (bda) -> {
         return bda.getBeansXml();
      }, removeDuplicates);
   }

   public static BeansXml mergeExistingDescriptors(Iterable beanArchives, boolean removeDuplicates) {
      return merge(beanArchives, Function.identity(), removeDuplicates);
   }
}

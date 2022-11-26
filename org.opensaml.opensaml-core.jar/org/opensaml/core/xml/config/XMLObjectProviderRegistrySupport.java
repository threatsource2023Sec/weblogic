package org.opensaml.core.xml.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallerFactory;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;

public class XMLObjectProviderRegistrySupport {
   protected XMLObjectProviderRegistrySupport() {
   }

   @Nullable
   public static ParserPool getParserPool() {
      return ((XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class)).getParserPool();
   }

   public static void setParserPool(@Nullable ParserPool newParserPool) {
      ((XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class)).setParserPool(newParserPool);
   }

   public static QName getDefaultProviderQName() {
      return ((XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class)).getDefaultProviderQName();
   }

   public static void registerObjectProvider(@Nonnull QName providerName, @Nonnull XMLObjectBuilder builder, @Nonnull Marshaller marshaller, @Nonnull Unmarshaller unmarshaller) {
      XMLObjectProviderRegistry registry = (XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class);
      registry.getBuilderFactory().registerBuilder(providerName, builder);
      registry.getMarshallerFactory().registerMarshaller(providerName, marshaller);
      registry.getUnmarshallerFactory().registerUnmarshaller(providerName, unmarshaller);
   }

   public static void deregisterObjectProvider(@Nonnull QName key) {
      XMLObjectProviderRegistry registry = (XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class);
      registry.getBuilderFactory().deregisterBuilder(key);
      registry.getMarshallerFactory().deregisterMarshaller(key);
      registry.getUnmarshallerFactory().deregisterUnmarshaller(key);
   }

   public static XMLObjectBuilderFactory getBuilderFactory() {
      return ((XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class)).getBuilderFactory();
   }

   public static MarshallerFactory getMarshallerFactory() {
      return ((XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class)).getMarshallerFactory();
   }

   public static UnmarshallerFactory getUnmarshallerFactory() {
      return ((XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class)).getUnmarshallerFactory();
   }

   public static void registerIDAttribute(QName attributeName) {
      ((XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class)).registerIDAttribute(attributeName);
   }

   public static void deregisterIDAttribute(QName attributeName) {
      ((XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class)).deregisterIDAttribute(attributeName);
   }

   public static boolean isIDAttribute(QName attributeName) {
      return ((XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class)).isIDAttribute(attributeName);
   }
}

package org.opensaml.core.xml.config;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilderFactory;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallerFactory;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XMLObjectProviderRegistry {
   private static QName defaultProvider = new QName("http://www.opensaml.org/xmltooling-config", "DEFAULT");
   private Logger log = LoggerFactory.getLogger(XMLObjectProviderRegistry.class);
   @Nonnull
   private final Map configuredObjectProviders = new ConcurrentHashMap(0);
   private XMLObjectBuilderFactory builderFactory = new XMLObjectBuilderFactory();
   private MarshallerFactory marshallerFactory = new MarshallerFactory();
   private UnmarshallerFactory unmarshallerFactory = new UnmarshallerFactory();
   @Nonnull
   private final Set idAttributeNames = new CopyOnWriteArraySet();
   private ParserPool parserPool;

   public XMLObjectProviderRegistry() {
      this.registerIDAttribute(new QName("http://www.w3.org/XML/1998/namespace", "id"));
   }

   public ParserPool getParserPool() {
      return this.parserPool;
   }

   public void setParserPool(@Nullable ParserPool newParserPool) {
      this.parserPool = newParserPool;
   }

   public QName getDefaultProviderQName() {
      return defaultProvider;
   }

   public void registerObjectProvider(@Nonnull QName providerName, @Nonnull XMLObjectBuilder builder, @Nonnull Marshaller marshaller, @Nonnull Unmarshaller unmarshaller) {
      this.log.debug("Registering new builder, marshaller, and unmarshaller for {}", providerName);
      this.builderFactory.registerBuilder(providerName, builder);
      this.marshallerFactory.registerMarshaller(providerName, marshaller);
      this.unmarshallerFactory.registerUnmarshaller(providerName, unmarshaller);
   }

   public void deregisterObjectProvider(@Nonnull QName key) {
      this.log.debug("Unregistering builder, marshaller, and unmarshaller for {}", key);
      this.configuredObjectProviders.remove(key);
      this.builderFactory.deregisterBuilder(key);
      this.marshallerFactory.deregisterMarshaller(key);
      this.unmarshallerFactory.deregisterUnmarshaller(key);
   }

   public XMLObjectBuilderFactory getBuilderFactory() {
      return this.builderFactory;
   }

   public MarshallerFactory getMarshallerFactory() {
      return this.marshallerFactory;
   }

   public UnmarshallerFactory getUnmarshallerFactory() {
      return this.unmarshallerFactory;
   }

   public void registerIDAttribute(QName attributeName) {
      if (!this.idAttributeNames.contains(attributeName)) {
         this.idAttributeNames.add(attributeName);
      }

   }

   public void deregisterIDAttribute(QName attributeName) {
      if (this.idAttributeNames.contains(attributeName)) {
         this.idAttributeNames.remove(attributeName);
      }

   }

   public boolean isIDAttribute(QName attributeName) {
      return this.idAttributeNames.contains(attributeName);
   }
}

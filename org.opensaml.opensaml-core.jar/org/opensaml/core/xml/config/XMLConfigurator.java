package org.opensaml.core.xml.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.BasicParserPool;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLConfigurator {
   @Nonnull
   @NotEmpty
   public static final String XMLTOOLING_CONFIG_NS = "http://www.opensaml.org/xmltooling-config";
   @Nonnull
   @NotEmpty
   public static final String XMLTOOLING_CONFIG_PREFIX = "xt";
   @Nonnull
   @NotEmpty
   public static final String XMLTOOLING_DEFAULT_OBJECT_PROVIDER = "DEFAULT";
   @Nonnull
   @NotEmpty
   public static final String XMLTOOLING_SCHEMA_LOCATION = "/schema/xmltooling-config.xsd";
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(XMLConfigurator.class);
   private BasicParserPool parserPool = new BasicParserPool();
   private Schema configurationSchema;
   @Nonnull
   private final XMLObjectProviderRegistry registry;

   public XMLConfigurator() throws XMLConfigurationException {
      SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      Source schemaSource = new StreamSource(XMLConfigurator.class.getResourceAsStream("/schema/xmltooling-config.xsd"));

      try {
         this.configurationSchema = factory.newSchema(schemaSource);
         this.parserPool.setIgnoreComments(true);
         this.parserPool.setIgnoreElementContentWhitespace(true);
         this.parserPool.setSchema(this.configurationSchema);
         this.parserPool.initialize();
      } catch (SAXException var7) {
         throw new XMLConfigurationException("Unable to read XMLTooling configuration schema", var7);
      } catch (ComponentInitializationException var8) {
         throw new XMLConfigurationException("Unable to initialize parser pool", var8);
      }

      Class var3 = ConfigurationService.class;
      synchronized(ConfigurationService.class) {
         XMLObjectProviderRegistry reg = (XMLObjectProviderRegistry)ConfigurationService.get(XMLObjectProviderRegistry.class);
         if (reg == null) {
            this.log.debug("XMLObjectProviderRegistry did not exist in ConfigurationService, will be created");
            reg = new XMLObjectProviderRegistry();
            ConfigurationService.register(XMLObjectProviderRegistry.class, reg);
         }

         this.registry = reg;
      }
   }

   public void load(@Nullable File configurationFile) throws XMLConfigurationException {
      if (configurationFile != null && configurationFile.canRead()) {
         try {
            if (configurationFile.isDirectory()) {
               File[] configurations = configurationFile.listFiles();

               for(int i = 0; i < configurations.length; ++i) {
                  this.log.debug("Parsing configuration file {}", configurations[i].getAbsolutePath());
                  this.load((InputStream)(new FileInputStream(configurations[i])));
               }
            } else {
               this.log.debug("Parsing configuration file {}", configurationFile.getAbsolutePath());
               this.load((InputStream)(new FileInputStream(configurationFile)));
            }
         } catch (FileNotFoundException var4) {
         }

      } else {
         this.log.error("Unable to read configuration file {}", configurationFile);
      }
   }

   public void load(@Nonnull InputStream configurationStream) throws XMLConfigurationException {
      try {
         Document configuration = this.parserPool.parse(configurationStream);
         this.load(configuration);
      } catch (XMLParserException var3) {
         this.log.error("Invalid configuration file", var3);
         throw new XMLConfigurationException("Unable to create DocumentBuilder", var3);
      }
   }

   public void load(@Nonnull Document configuration) throws XMLConfigurationException {
      Element root = (Element)Constraint.isNotNull(configuration.getDocumentElement(), "Document element cannot be null");
      this.log.debug("Loading configuration from XML Document");
      this.log.trace("{}", SerializeSupport.nodeToString(root));
      this.log.debug("Schema validating configuration Document");
      this.validateConfiguration(configuration);
      this.log.debug("Configuration document validated");
      this.load(root);
   }

   protected void load(@Nonnull Element configurationRoot) throws XMLConfigurationException {
      NodeList objectProviders = configurationRoot.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "ObjectProviders");
      if (objectProviders.getLength() > 0) {
         this.log.debug("Preparing to load ObjectProviders");
         this.initializeObjectProviders((Element)objectProviders.item(0));
         this.log.debug("ObjectProviders load complete");
      }

      NodeList idAttributesNodes = configurationRoot.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "IDAttributes");
      if (idAttributesNodes.getLength() > 0) {
         this.log.debug("Preparing to load IDAttributes");
         this.initializeIDAttributes((Element)idAttributesNodes.item(0));
         this.log.debug("IDAttributes load complete");
      }

   }

   protected void initializeObjectProviders(Element objectProviders) throws XMLConfigurationException {
      NodeList providerList = objectProviders.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "ObjectProvider");

      for(int i = 0; i < providerList.getLength(); ++i) {
         Element objectProvider = (Element)providerList.item(i);
         Attr qNameAttrib = objectProvider.getAttributeNodeNS((String)null, "qualifiedName");
         QName objectProviderName = AttributeSupport.getAttributeValueAsQName(qNameAttrib);
         this.log.debug("Initializing object provider {}", objectProviderName);

         try {
            Element configuration = (Element)objectProvider.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "BuilderClass").item(0);
            XMLObjectBuilder builder = (XMLObjectBuilder)this.createClassInstance(configuration);
            configuration = (Element)objectProvider.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "MarshallingClass").item(0);
            Marshaller marshaller = (Marshaller)this.createClassInstance(configuration);
            configuration = (Element)objectProvider.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "UnmarshallingClass").item(0);
            Unmarshaller unmarshaller = (Unmarshaller)this.createClassInstance(configuration);
            this.getRegistry().registerObjectProvider(objectProviderName, builder, marshaller, unmarshaller);
            this.log.debug("{} intialized and configuration cached", objectProviderName);
         } catch (XMLConfigurationException var11) {
            this.log.error("Error initializing object provier {}", objectProvider, var11);
            this.getRegistry().deregisterObjectProvider(objectProviderName);
            throw var11;
         }
      }

   }

   protected void initializeIDAttributes(Element idAttributesElement) throws XMLConfigurationException {
      NodeList idAttributeList = idAttributesElement.getElementsByTagNameNS("http://www.opensaml.org/xmltooling-config", "IDAttribute");

      for(int i = 0; i < idAttributeList.getLength(); ++i) {
         Element idAttributeElement = (Element)idAttributeList.item(i);
         QName attributeQName = ElementSupport.getElementContentAsQName(idAttributeElement);
         if (attributeQName == null) {
            this.log.debug("IDAttribute element was empty, no registration performed");
         } else {
            this.getRegistry().registerIDAttribute(attributeQName);
            this.log.debug("IDAttribute {} has been registered", attributeQName);
         }
      }

   }

   protected Object createClassInstance(Element configuration) throws XMLConfigurationException {
      String className = StringSupport.trimOrNull(configuration.getAttributeNS((String)null, "className"));
      if (className == null) {
         return null;
      } else {
         try {
            this.log.trace("Creating instance of {}", className);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class clazz = classLoader.loadClass(className);
            Constructor constructor = clazz.getConstructor();
            return constructor.newInstance();
         } catch (Exception var6) {
            String errorMsg = "Cannot create instance of " + className;
            this.log.error(errorMsg, var6);
            throw new XMLConfigurationException(errorMsg, var6);
         }
      }
   }

   protected void validateConfiguration(Document configuration) throws XMLConfigurationException {
      String errorMsg;
      try {
         Validator schemaValidator = this.configurationSchema.newValidator();
         schemaValidator.validate(new DOMSource(configuration));
      } catch (IOException var4) {
         errorMsg = "Unable to read configuration file DOM";
         this.log.error(errorMsg, var4);
         throw new XMLConfigurationException(errorMsg, var4);
      } catch (SAXException var5) {
         errorMsg = "Configuration file does not validate against schema";
         this.log.error(errorMsg, var5);
         throw new XMLConfigurationException(errorMsg, var5);
      }
   }

   protected XMLObjectProviderRegistry getRegistry() {
      return this.registry;
   }
}

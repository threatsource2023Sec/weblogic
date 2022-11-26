package org.apache.xml.security.stax.config;

import java.net.URI;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.UnmarshallerHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.xml.security.configuration.ConfigurationType;
import org.apache.xml.security.configuration.ObjectFactory;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConfigurationException;
import org.apache.xml.security.utils.ClassLoaderUtils;
import org.apache.xml.security.utils.I18n;

public class Init {
   private static URI initialized;

   public static synchronized void init(URI uri, Class callingClass) throws XMLSecurityException {
      if (initialized == null || uri != null && !uri.equals(initialized)) {
         try {
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{ObjectFactory.class});
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            Schema schema = schemaFactory.newSchema(ClassLoaderUtils.getResource("schemas/security-config.xsd", Init.class));
            unmarshaller.setSchema(schema);
            UnmarshallerHandler unmarshallerHandler = unmarshaller.getUnmarshallerHandler();
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            saxParserFactory.setXIncludeAware(false);
            saxParserFactory.setNamespaceAware(true);
            SAXParser saxParser = saxParserFactory.newSAXParser();
            if (uri == null) {
               URL resource = ClassLoaderUtils.getResource("security-config.xml", Init.class);
               if (resource == null) {
                  I18n.init("en", "US");
                  throw new XMLSecurityConfigurationException("empty", new Object[]{"security-config.xml not found in classpath"});
               }

               uri = resource.toURI();
            }

            saxParser.parse(uri.toURL().toExternalForm(), new XIncludeHandler(unmarshallerHandler));
            JAXBElement configurationTypeJAXBElement = (JAXBElement)unmarshallerHandler.getResult();
            ConfigurationProperties.init(((ConfigurationType)configurationTypeJAXBElement.getValue()).getProperties(), callingClass);
            SecurityHeaderHandlerMapper.init(((ConfigurationType)configurationTypeJAXBElement.getValue()).getSecurityHeaderHandlers(), callingClass);
            JCEAlgorithmMapper.init(((ConfigurationType)configurationTypeJAXBElement.getValue()).getJCEAlgorithmMappings());
            TransformerAlgorithmMapper.init(((ConfigurationType)configurationTypeJAXBElement.getValue()).getTransformAlgorithms(), callingClass);
            ResourceResolverMapper.init(((ConfigurationType)configurationTypeJAXBElement.getValue()).getResourceResolvers(), callingClass);
            I18n.init(ConfigurationProperties.getProperty("DefaultLanguageCode"), ConfigurationProperties.getProperty("DefaultCountryCode"));
         } catch (Exception var10) {
            I18n.init("en", "US");
            throw new XMLSecurityConfigurationException(var10);
         }

         initialized = uri;
      }

   }
}

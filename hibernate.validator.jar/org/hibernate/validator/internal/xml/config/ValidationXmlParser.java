package org.hibernate.validator.internal.xml.config;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Map;
import javax.validation.BootstrapConfiguration;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.SetContextClassLoader;
import org.hibernate.validator.internal.xml.CloseIgnoringInputStream;
import org.hibernate.validator.internal.xml.XmlParserHelper;
import org.xml.sax.SAXException;

public class ValidationXmlParser {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String VALIDATION_XML_FILE = "META-INF/validation.xml";
   private static final Map SCHEMAS_BY_VERSION = Collections.unmodifiableMap(getSchemasByVersion());
   private final ClassLoader externalClassLoader;

   private static Map getSchemasByVersion() {
      Map schemasByVersion = CollectionHelper.newHashMap(3);
      schemasByVersion.put("1.0", "META-INF/validation-configuration-1.0.xsd");
      schemasByVersion.put("1.1", "META-INF/validation-configuration-1.1.xsd");
      schemasByVersion.put("2.0", "META-INF/validation-configuration-2.0.xsd");
      return schemasByVersion;
   }

   public ValidationXmlParser(ClassLoader externalClassLoader) {
      this.externalClassLoader = externalClassLoader;
   }

   public final BootstrapConfiguration parseValidationXml() {
      InputStream in = this.getValidationXmlInputStream();
      if (in == null) {
         return BootstrapConfigurationImpl.getDefaultBootstrapConfiguration();
      } else {
         ClassLoader previousTccl = (ClassLoader)run(GetClassLoader.fromContext());

         BootstrapConfiguration var9;
         try {
            run(SetContextClassLoader.action(ValidationXmlParser.class.getClassLoader()));
            XmlParserHelper xmlParserHelper = new XmlParserHelper();
            in.mark(Integer.MAX_VALUE);
            XMLEventReader xmlEventReader = xmlParserHelper.createXmlEventReader("META-INF/validation.xml", new CloseIgnoringInputStream(in));
            String schemaVersion = xmlParserHelper.getSchemaVersion("META-INF/validation.xml", xmlEventReader);
            xmlEventReader.close();
            in.reset();
            Schema schema = this.getSchema(xmlParserHelper, schemaVersion);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new CloseIgnoringInputStream(in)));
            in.reset();
            xmlEventReader = xmlParserHelper.createXmlEventReader("META-INF/validation.xml", new CloseIgnoringInputStream(in));
            ValidationConfigStaxBuilder validationConfigStaxBuilder = new ValidationConfigStaxBuilder(xmlEventReader);
            xmlEventReader.close();
            in.reset();
            var9 = validationConfigStaxBuilder.build();
         } catch (IOException | SAXException | XMLStreamException var13) {
            throw LOG.getUnableToParseValidationXmlFileException("META-INF/validation.xml", var13);
         } finally {
            run(SetContextClassLoader.action(previousTccl));
            this.closeStream(in);
         }

         return var9;
      }
   }

   private InputStream getValidationXmlInputStream() {
      LOG.debugf("Trying to load %s for XML based Validator configuration.", "META-INF/validation.xml");
      InputStream inputStream = ResourceLoaderHelper.getResettableInputStreamForPath("META-INF/validation.xml", this.externalClassLoader);
      if (inputStream != null) {
         return inputStream;
      } else {
         LOG.debugf("No %s found. Using annotation based configuration only.", "META-INF/validation.xml");
         return null;
      }
   }

   private Schema getSchema(XmlParserHelper xmlParserHelper, String schemaVersion) {
      String schemaResource = (String)SCHEMAS_BY_VERSION.get(schemaVersion);
      if (schemaResource == null) {
         throw LOG.getUnsupportedSchemaVersionException("META-INF/validation.xml", schemaVersion);
      } else {
         Schema schema = xmlParserHelper.getSchema(schemaResource);
         if (schema == null) {
            throw LOG.unableToGetXmlSchema(schemaResource);
         } else {
            return schema;
         }
      }
   }

   private void closeStream(InputStream inputStream) {
      try {
         inputStream.close();
      } catch (IOException var3) {
         LOG.unableToCloseXMLFileInputStream("META-INF/validation.xml");
      }

   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}

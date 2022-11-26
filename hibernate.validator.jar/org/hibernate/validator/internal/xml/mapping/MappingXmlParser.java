package org.hibernate.validator.internal.xml.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptions;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.SetContextClassLoader;
import org.hibernate.validator.internal.xml.CloseIgnoringInputStream;
import org.hibernate.validator.internal.xml.XmlParserHelper;
import org.xml.sax.SAXException;

public class MappingXmlParser {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Set processedClasses = CollectionHelper.newHashSet();
   private final ConstraintHelper constraintHelper;
   private final TypeResolutionHelper typeResolutionHelper;
   private final ValueExtractorManager valueExtractorManager;
   private final AnnotationProcessingOptionsImpl annotationProcessingOptions;
   private final Map defaultSequences;
   private final Map constrainedElements;
   private final XmlParserHelper xmlParserHelper;
   private final ClassLoadingHelper classLoadingHelper;
   private static final Map SCHEMAS_BY_VERSION = Collections.unmodifiableMap(getSchemasByVersion());

   private static Map getSchemasByVersion() {
      Map schemasByVersion = new HashMap();
      schemasByVersion.put("1.0", "META-INF/validation-mapping-1.0.xsd");
      schemasByVersion.put("1.1", "META-INF/validation-mapping-1.1.xsd");
      schemasByVersion.put("2.0", "META-INF/validation-mapping-2.0.xsd");
      return schemasByVersion;
   }

   public MappingXmlParser(ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, ClassLoader externalClassLoader) {
      this.constraintHelper = constraintHelper;
      this.typeResolutionHelper = typeResolutionHelper;
      this.valueExtractorManager = valueExtractorManager;
      this.annotationProcessingOptions = new AnnotationProcessingOptionsImpl();
      this.defaultSequences = CollectionHelper.newHashMap();
      this.constrainedElements = CollectionHelper.newHashMap();
      this.xmlParserHelper = new XmlParserHelper();
      this.classLoadingHelper = new ClassLoadingHelper(externalClassLoader, (ClassLoader)run(GetClassLoader.fromContext()));
   }

   public final void parse(Set mappingStreams) {
      ClassLoader previousTccl = (ClassLoader)run(GetClassLoader.fromContext());

      try {
         run(SetContextClassLoader.action(MappingXmlParser.class.getClassLoader()));
         Set alreadyProcessedConstraintDefinitions = CollectionHelper.newHashSet();
         Iterator var4 = mappingStreams.iterator();

         while(var4.hasNext()) {
            InputStream in = (InputStream)var4.next();
            in.mark(Integer.MAX_VALUE);
            XMLEventReader xmlEventReader = this.xmlParserHelper.createXmlEventReader("constraint mapping file", new CloseIgnoringInputStream(in));
            String schemaVersion = this.xmlParserHelper.getSchemaVersion("constraint mapping file", xmlEventReader);
            xmlEventReader.close();
            in.reset();
            String schemaResourceName = this.getSchemaResourceName(schemaVersion);
            Schema schema = this.xmlParserHelper.getSchema(schemaResourceName);
            if (schema == null) {
               throw LOG.unableToGetXmlSchema(schemaResourceName);
            }

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new CloseIgnoringInputStream(in)));
            in.reset();
            ConstraintMappingsStaxBuilder constraintMappingsStaxBuilder = new ConstraintMappingsStaxBuilder(this.classLoadingHelper, this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.annotationProcessingOptions, this.defaultSequences);
            xmlEventReader = this.xmlParserHelper.createXmlEventReader("constraint mapping file", new CloseIgnoringInputStream(in));

            while(xmlEventReader.hasNext()) {
               constraintMappingsStaxBuilder.process(xmlEventReader, xmlEventReader.nextEvent());
            }

            constraintMappingsStaxBuilder.build(this.processedClasses, this.constrainedElements, alreadyProcessedConstraintDefinitions);
            xmlEventReader.close();
            in.reset();
         }
      } catch (XMLStreamException | SAXException | IOException var15) {
         throw LOG.getErrorParsingMappingFileException(var15);
      } finally {
         run(SetContextClassLoader.action(previousTccl));
      }

   }

   public final Set getXmlConfiguredClasses() {
      return this.processedClasses;
   }

   public final AnnotationProcessingOptions getAnnotationProcessingOptions() {
      return this.annotationProcessingOptions;
   }

   public final Set getConstrainedElementsForClass(Class beanClass) {
      return this.constrainedElements.containsKey(beanClass) ? (Set)this.constrainedElements.get(beanClass) : Collections.emptySet();
   }

   public final List getDefaultSequenceForClass(Class beanClass) {
      return (List)this.defaultSequences.get(beanClass);
   }

   private String getSchemaResourceName(String schemaVersion) {
      String schemaResource = (String)SCHEMAS_BY_VERSION.get(schemaVersion);
      if (schemaResource == null) {
         throw LOG.getUnsupportedSchemaVersionException("constraint mapping file", schemaVersion);
      } else {
         return schemaResource;
      }
   }

   private static Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}

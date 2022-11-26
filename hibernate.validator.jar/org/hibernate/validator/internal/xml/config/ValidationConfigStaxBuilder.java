package org.hibernate.validator.internal.xml.config;

import java.lang.invoke.MethodHandles;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.validation.BootstrapConfiguration;
import javax.validation.executable.ExecutableType;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;

class ValidationConfigStaxBuilder extends AbstractStaxBuilder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String VALIDATION_CONFIG_QNAME = "validation-config";
   private final SimpleConfigurationsStaxBuilder simpleConfigurationsStaxBuilder = new SimpleConfigurationsStaxBuilder();
   private final PropertyStaxBuilder propertyStaxBuilder = new PropertyStaxBuilder();
   private final ValueExtractorsStaxBuilder valueExtractorsStaxBuilder = new ValueExtractorsStaxBuilder();
   private final ConstraintMappingsStaxBuilder constraintMappingsStaxBuilder = new ConstraintMappingsStaxBuilder();
   private final ExecutableValidationStaxBuilder executableValidationStaxBuilder = new ExecutableValidationStaxBuilder();
   private final Map builders = new HashMap();

   public ValidationConfigStaxBuilder(XMLEventReader xmlEventReader) throws XMLStreamException {
      this.builders.put(this.propertyStaxBuilder.getAcceptableQName(), this.propertyStaxBuilder);
      this.builders.put(this.valueExtractorsStaxBuilder.getAcceptableQName(), this.valueExtractorsStaxBuilder);
      this.builders.put(this.constraintMappingsStaxBuilder.getAcceptableQName(), this.constraintMappingsStaxBuilder);
      this.builders.put(this.executableValidationStaxBuilder.getAcceptableQName(), this.executableValidationStaxBuilder);
      Iterator var2 = ValidationConfigStaxBuilder.SimpleConfigurationsStaxBuilder.getProcessedElementNames().iterator();

      while(var2.hasNext()) {
         String name = (String)var2.next();
         this.builders.put(name, this.simpleConfigurationsStaxBuilder);
      }

      while(xmlEventReader.hasNext()) {
         this.process(xmlEventReader, xmlEventReader.nextEvent());
      }

   }

   protected String getAcceptableQName() {
      return "validation-config";
   }

   protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
      while(!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("validation-config")) {
         XMLEvent currentEvent = xmlEventReader.nextEvent();
         xmlEvent = currentEvent;
         if (currentEvent.isStartElement()) {
            StartElement startElement = currentEvent.asStartElement();
            String localPart = startElement.getName().getLocalPart();
            AbstractStaxBuilder builder = (AbstractStaxBuilder)this.builders.get(localPart);
            if (builder != null) {
               builder.process(xmlEventReader, currentEvent);
            } else {
               LOG.logUnknownElementInXmlConfiguration(localPart);
            }
         }
      }

   }

   public BootstrapConfiguration build() {
      Map properties = this.propertyStaxBuilder.build();
      return new BootstrapConfigurationImpl(this.simpleConfigurationsStaxBuilder.getDefaultProvider(), this.simpleConfigurationsStaxBuilder.getConstraintValidatorFactory(), this.simpleConfigurationsStaxBuilder.getMessageInterpolator(), this.simpleConfigurationsStaxBuilder.getTraversableResolver(), this.simpleConfigurationsStaxBuilder.getParameterNameProvider(), this.simpleConfigurationsStaxBuilder.getClockProvider(), this.valueExtractorsStaxBuilder.build(), this.executableValidationStaxBuilder.build(), this.executableValidationStaxBuilder.isEnabled(), this.constraintMappingsStaxBuilder.build(), properties);
   }

   private static class ExecutableValidationStaxBuilder extends AbstractStaxBuilder {
      private static final String EXECUTABLE_VALIDATION_QNAME_LOCAL_PART = "executable-validation";
      private static final String EXECUTABLE_TYPE_QNAME_LOCAL_PART = "executable-type";
      private static final QName ENABLED_QNAME = new QName("enabled");
      private Boolean enabled;
      private EnumSet executableTypes;

      private ExecutableValidationStaxBuilder() {
         this.executableTypes = EnumSet.noneOf(ExecutableType.class);
      }

      protected String getAcceptableQName() {
         return "executable-validation";
      }

      protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
         Optional enabledAttribute = this.readAttribute(xmlEvent.asStartElement(), ENABLED_QNAME);
         if (enabledAttribute.isPresent()) {
            this.enabled = Boolean.parseBoolean((String)enabledAttribute.get());
         }

         while(!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("executable-validation")) {
            XMLEvent currentEvent = xmlEventReader.nextEvent();
            xmlEvent = currentEvent;
            if (currentEvent.isStartElement() && currentEvent.asStartElement().getName().getLocalPart().equals("executable-type")) {
               this.executableTypes.add(ExecutableType.valueOf(this.readSingleElement(xmlEventReader)));
            }
         }

      }

      public boolean isEnabled() {
         return this.enabled == null ? true : this.enabled;
      }

      public EnumSet build() {
         return this.executableTypes.isEmpty() ? null : this.executableTypes;
      }

      // $FF: synthetic method
      ExecutableValidationStaxBuilder(Object x0) {
         this();
      }
   }

   private static class ConstraintMappingsStaxBuilder extends AbstractStaxBuilder {
      private static final String CONSTRAINT_MAPPING_QNAME_LOCAL_PART = "constraint-mapping";
      private final Set constraintMappings;

      private ConstraintMappingsStaxBuilder() {
         this.constraintMappings = new HashSet();
      }

      protected String getAcceptableQName() {
         return "constraint-mapping";
      }

      protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
         String value = this.readSingleElement(xmlEventReader);
         this.constraintMappings.add(value);
      }

      public Set build() {
         return this.constraintMappings;
      }

      // $FF: synthetic method
      ConstraintMappingsStaxBuilder(Object x0) {
         this();
      }
   }

   private static class ValueExtractorsStaxBuilder extends AbstractStaxBuilder {
      private static final String VALUE_EXTRACTOR_QNAME_LOCAL_PART = "value-extractor";
      private final Set valueExtractors;

      private ValueExtractorsStaxBuilder() {
         this.valueExtractors = new HashSet();
      }

      protected String getAcceptableQName() {
         return "value-extractor";
      }

      protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
         String value = this.readSingleElement(xmlEventReader);
         if (!this.valueExtractors.add(value)) {
            throw ValidationConfigStaxBuilder.LOG.getDuplicateDefinitionsOfValueExtractorException(value);
         }
      }

      public Set build() {
         return this.valueExtractors;
      }

      // $FF: synthetic method
      ValueExtractorsStaxBuilder(Object x0) {
         this();
      }
   }

   private static class PropertyStaxBuilder extends AbstractStaxBuilder {
      private static final String PROPERTY_QNAME_LOCAL_PART = "property";
      private static final QName NAME_QNAME = new QName("name");
      private final Map properties;

      private PropertyStaxBuilder() {
         this.properties = new HashMap();
      }

      protected String getAcceptableQName() {
         return "property";
      }

      protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
         StartElement startElement = xmlEvent.asStartElement();
         String name = (String)this.readAttribute(startElement, NAME_QNAME).get();
         String value = this.readSingleElement(xmlEventReader);
         if (ValidationConfigStaxBuilder.LOG.isDebugEnabled()) {
            ValidationConfigStaxBuilder.LOG.debugf("Found property '%s' with value '%s' in validation.xml.", name, value);
         }

         this.properties.put(name, value);
      }

      public Map build() {
         return this.properties;
      }

      // $FF: synthetic method
      PropertyStaxBuilder(Object x0) {
         this();
      }
   }

   private static class SimpleConfigurationsStaxBuilder extends AbstractStaxBuilder {
      private static final String DEFAULT_PROVIDER = "default-provider";
      private static final String MESSAGE_INTERPOLATOR = "message-interpolator";
      private static final String TRAVERSABLE_RESOLVER = "traversable-resolver";
      private static final String CONSTRAINT_VALIDATOR_FACTORY = "constraint-validator-factory";
      private static final String PARAMETER_NAME_PROVIDER = "parameter-name-provider";
      private static final String CLOCK_PROVIDER = "clock-provider";
      private static final Set SINGLE_ELEMENTS = CollectionHelper.toImmutableSet(CollectionHelper.asSet("default-provider", "message-interpolator", "traversable-resolver", "constraint-validator-factory", "parameter-name-provider", "clock-provider"));
      private final Map singleValuedElements;

      private SimpleConfigurationsStaxBuilder() {
         this.singleValuedElements = new HashMap();
      }

      protected String getAcceptableQName() {
         throw new UnsupportedOperationException("this method shouldn't be called");
      }

      protected boolean accept(XMLEvent xmlEvent) {
         return xmlEvent.isStartElement() && SINGLE_ELEMENTS.contains(xmlEvent.asStartElement().getName().getLocalPart());
      }

      protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
         String localPart = xmlEvent.asStartElement().getName().getLocalPart();
         this.singleValuedElements.put(localPart, this.readSingleElement(xmlEventReader));
      }

      public String getDefaultProvider() {
         return (String)this.singleValuedElements.get("default-provider");
      }

      public String getMessageInterpolator() {
         return (String)this.singleValuedElements.get("message-interpolator");
      }

      public String getTraversableResolver() {
         return (String)this.singleValuedElements.get("traversable-resolver");
      }

      public String getClockProvider() {
         return (String)this.singleValuedElements.get("clock-provider");
      }

      public String getConstraintValidatorFactory() {
         return (String)this.singleValuedElements.get("constraint-validator-factory");
      }

      public String getParameterNameProvider() {
         return (String)this.singleValuedElements.get("parameter-name-provider");
      }

      public static Set getProcessedElementNames() {
         return SINGLE_ELEMENTS;
      }

      // $FF: synthetic method
      SimpleConfigurationsStaxBuilder(Object x0) {
         this();
      }
   }
}

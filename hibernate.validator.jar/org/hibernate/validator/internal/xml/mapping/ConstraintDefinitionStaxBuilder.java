package org.hibernate.validator.internal.xml.mapping;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorDescriptor;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;

class ConstraintDefinitionStaxBuilder extends AbstractStaxBuilder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final String CONSTRAINT_DEFINITION_QNAME_LOCAL_PART = "constraint-definition";
   private static final QName ANNOTATION_QNAME = new QName("annotation");
   private final ClassLoadingHelper classLoadingHelper;
   private final ConstraintHelper constraintHelper;
   private final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
   private String annotation;
   private ValidatedByStaxBuilder validatedByStaxBuilder;

   ConstraintDefinitionStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintHelper constraintHelper, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
      this.classLoadingHelper = classLoadingHelper;
      this.constraintHelper = constraintHelper;
      this.defaultPackageStaxBuilder = defaultPackageStaxBuilder;
      this.validatedByStaxBuilder = new ValidatedByStaxBuilder(classLoadingHelper, defaultPackageStaxBuilder);
   }

   protected String getAcceptableQName() {
      return "constraint-definition";
   }

   protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
      for(this.annotation = (String)this.readAttribute(xmlEvent.asStartElement(), ANNOTATION_QNAME).get(); !xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals(this.getAcceptableQName()); xmlEvent = xmlEventReader.nextEvent()) {
         this.validatedByStaxBuilder.process(xmlEventReader, xmlEvent);
      }

   }

   void build(Set alreadyProcessedConstraintDefinitions) {
      this.checkProcessedAnnotations(alreadyProcessedConstraintDefinitions);
      String defaultPackage = (String)this.defaultPackageStaxBuilder.build().orElse("");
      Class clazz = this.classLoadingHelper.loadClass(this.annotation, defaultPackage);
      if (!clazz.isAnnotation()) {
         throw LOG.getIsNotAnAnnotationException(clazz);
      } else {
         this.addValidatorDefinitions(clazz);
      }
   }

   private void checkProcessedAnnotations(Set alreadyProcessedConstraintDefinitions) {
      if (alreadyProcessedConstraintDefinitions.contains(this.annotation)) {
         throw LOG.getOverridingConstraintDefinitionsInMultipleMappingFilesException(this.annotation);
      } else {
         alreadyProcessedConstraintDefinitions.add(this.annotation);
      }
   }

   private void addValidatorDefinitions(Class annotationClass) {
      this.constraintHelper.putValidatorDescriptors(annotationClass, this.validatedByStaxBuilder.build(annotationClass), this.validatedByStaxBuilder.isIncludeExistingValidators());
   }

   private static class ValidatedByStaxBuilder extends AbstractStaxBuilder {
      private static final String VALIDATED_BY_QNAME_LOCAL_PART = "validated-by";
      private static final String VALUE_QNAME_LOCAL_PART = "value";
      private static final QName INCLUDE_EXISTING_VALIDATORS_QNAME = new QName("include-existing-validators");
      private final ClassLoadingHelper classLoadingHelper;
      private final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
      private boolean includeExistingValidators;
      private final List values;

      protected ValidatedByStaxBuilder(ClassLoadingHelper classLoadingHelper, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
         this.classLoadingHelper = classLoadingHelper;
         this.defaultPackageStaxBuilder = defaultPackageStaxBuilder;
         this.values = new ArrayList();
      }

      protected String getAcceptableQName() {
         return "validated-by";
      }

      protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
         for(this.includeExistingValidators = (Boolean)this.readAttribute(xmlEvent.asStartElement(), INCLUDE_EXISTING_VALIDATORS_QNAME).map(Boolean::parseBoolean).orElse(true); !xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals(this.getAcceptableQName()); xmlEvent = xmlEventReader.nextEvent()) {
            if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals("value")) {
               this.values.add(this.readSingleElement(xmlEventReader));
            }
         }

      }

      List build(Class annotation) {
         String defaultPackage = (String)this.defaultPackageStaxBuilder.build().orElse("");
         return (List)this.values.stream().map((value) -> {
            return this.classLoadingHelper.loadClass(value, defaultPackage);
         }).peek(this::checkValidatorAssignability).map((clazz) -> {
            return clazz;
         }).map((validatorClass) -> {
            return ConstraintValidatorDescriptor.forClass(validatorClass, annotation);
         }).collect(Collectors.toList());
      }

      public boolean isIncludeExistingValidators() {
         return this.includeExistingValidators;
      }

      private void checkValidatorAssignability(Class validatorClass) {
         if (!ConstraintValidator.class.isAssignableFrom(validatorClass)) {
            throw ConstraintDefinitionStaxBuilder.LOG.getIsNotAConstraintValidatorClassException(validatorClass);
         }
      }
   }
}

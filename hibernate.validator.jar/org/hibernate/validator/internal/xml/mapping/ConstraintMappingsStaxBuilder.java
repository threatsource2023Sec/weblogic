package org.hibernate.validator.internal.xml.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;

class ConstraintMappingsStaxBuilder extends AbstractStaxBuilder {
   private static final String CONSTRAINT_MAPPINGS_QNAME = "constraint-mappings";
   private final ClassLoadingHelper classLoadingHelper;
   private final ConstraintHelper constraintHelper;
   private final TypeResolutionHelper typeResolutionHelper;
   private final ValueExtractorManager valueExtractorManager;
   private final AnnotationProcessingOptionsImpl annotationProcessingOptions;
   private final Map defaultSequences;
   private final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
   private final List beanStaxBuilders;
   private final List constraintDefinitionStaxBuilders;

   public ConstraintMappingsStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, AnnotationProcessingOptionsImpl annotationProcessingOptions, Map defaultSequences) {
      this.classLoadingHelper = classLoadingHelper;
      this.constraintHelper = constraintHelper;
      this.typeResolutionHelper = typeResolutionHelper;
      this.valueExtractorManager = valueExtractorManager;
      this.annotationProcessingOptions = annotationProcessingOptions;
      this.defaultSequences = defaultSequences;
      this.defaultPackageStaxBuilder = new DefaultPackageStaxBuilder();
      this.beanStaxBuilders = new ArrayList();
      this.constraintDefinitionStaxBuilders = new ArrayList();
   }

   protected String getAcceptableQName() {
      return "constraint-mappings";
   }

   protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
      BeanStaxBuilder beanStaxBuilder = this.getNewBeanStaxBuilder();

      for(ConstraintDefinitionStaxBuilder constraintDefinitionStaxBuilder = this.getNewConstraintDefinitionStaxBuilder(); !xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals(this.getAcceptableQName()); this.defaultPackageStaxBuilder.process(xmlEventReader, xmlEvent)) {
         xmlEvent = xmlEventReader.nextEvent();
         if (beanStaxBuilder.process(xmlEventReader, xmlEvent)) {
            this.beanStaxBuilders.add(beanStaxBuilder);
            beanStaxBuilder = this.getNewBeanStaxBuilder();
         } else if (constraintDefinitionStaxBuilder.process(xmlEventReader, xmlEvent)) {
            this.constraintDefinitionStaxBuilders.add(constraintDefinitionStaxBuilder);
            constraintDefinitionStaxBuilder = this.getNewConstraintDefinitionStaxBuilder();
         }
      }

   }

   private BeanStaxBuilder getNewBeanStaxBuilder() {
      return new BeanStaxBuilder(this.classLoadingHelper, this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.defaultPackageStaxBuilder, this.annotationProcessingOptions, this.defaultSequences);
   }

   private ConstraintDefinitionStaxBuilder getNewConstraintDefinitionStaxBuilder() {
      return new ConstraintDefinitionStaxBuilder(this.classLoadingHelper, this.constraintHelper, this.defaultPackageStaxBuilder);
   }

   public void build(Set processedClasses, Map constrainedElementsByType, Set alreadyProcessedConstraintDefinitions) {
      this.constraintDefinitionStaxBuilders.forEach((builder) -> {
         builder.build(alreadyProcessedConstraintDefinitions);
      });
      this.beanStaxBuilders.forEach((builder) -> {
         builder.build(processedClasses, constrainedElementsByType);
      });
   }
}

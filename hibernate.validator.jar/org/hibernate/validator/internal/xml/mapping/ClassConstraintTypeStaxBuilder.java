package org.hibernate.validator.internal.xml.mapping;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedType;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;

class ClassConstraintTypeStaxBuilder extends AbstractStaxBuilder {
   private static final String CLASS_QNAME_LOCAL_PART = "class";
   private static final QName IGNORE_ANNOTATIONS_QNAME = new QName("ignore-annotations");
   private final ClassLoadingHelper classLoadingHelper;
   private final ConstraintHelper constraintHelper;
   private final TypeResolutionHelper typeResolutionHelper;
   private final ValueExtractorManager valueExtractorManager;
   private final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
   private final AnnotationProcessingOptionsImpl annotationProcessingOptions;
   private final Map defaultSequences;
   private Optional ignoreAnnotations;
   private final List constraintTypeStaxBuilders;
   private final GroupSequenceStaxBuilder groupSequenceStaxBuilder;

   ClassConstraintTypeStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions, Map defaultSequences) {
      this.classLoadingHelper = classLoadingHelper;
      this.defaultPackageStaxBuilder = defaultPackageStaxBuilder;
      this.constraintHelper = constraintHelper;
      this.typeResolutionHelper = typeResolutionHelper;
      this.valueExtractorManager = valueExtractorManager;
      this.annotationProcessingOptions = annotationProcessingOptions;
      this.defaultSequences = defaultSequences;
      this.constraintTypeStaxBuilders = new ArrayList();
      this.groupSequenceStaxBuilder = new GroupSequenceStaxBuilder(classLoadingHelper, defaultPackageStaxBuilder);
   }

   protected String getAcceptableQName() {
      return "class";
   }

   protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
      this.ignoreAnnotations = this.readAttribute(xmlEvent.asStartElement(), IGNORE_ANNOTATIONS_QNAME).map(Boolean::parseBoolean);
      ConstraintTypeStaxBuilder constraintTypeStaxBuilder = this.getNewConstraintTypeStaxBuilder();

      while(!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals(this.getAcceptableQName())) {
         xmlEvent = xmlEventReader.nextEvent();
         this.groupSequenceStaxBuilder.process(xmlEventReader, xmlEvent);
         if (constraintTypeStaxBuilder.process(xmlEventReader, xmlEvent)) {
            this.constraintTypeStaxBuilders.add(constraintTypeStaxBuilder);
            constraintTypeStaxBuilder = this.getNewConstraintTypeStaxBuilder();
         }
      }

   }

   private ConstraintTypeStaxBuilder getNewConstraintTypeStaxBuilder() {
      return new ConstraintTypeStaxBuilder(this.classLoadingHelper, this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.defaultPackageStaxBuilder);
   }

   ConstrainedType build(Class beanClass) {
      List groupSequence = Arrays.asList(this.groupSequenceStaxBuilder.build());
      if (!groupSequence.isEmpty()) {
         this.defaultSequences.put(beanClass, groupSequence);
      }

      ConstraintLocation constraintLocation = ConstraintLocation.forClass(beanClass);
      Set metaConstraints = (Set)this.constraintTypeStaxBuilders.stream().map((builder) -> {
         return builder.build(constraintLocation, ElementType.TYPE, (ConstraintDescriptorImpl.ConstraintType)null);
      }).collect(Collectors.toSet());
      if (this.ignoreAnnotations.isPresent()) {
         this.annotationProcessingOptions.ignoreClassLevelConstraintAnnotations(beanClass, (Boolean)this.ignoreAnnotations.get());
      }

      return new ConstrainedType(ConfigurationSource.XML, beanClass, metaConstraints);
   }

   private static class GroupSequenceStaxBuilder extends AbstractMultiValuedElementStaxBuilder {
      private static final String GROUP_SEQUENCE_QNAME_LOCAL_PART = "group-sequence";

      private GroupSequenceStaxBuilder(ClassLoadingHelper classLoadingHelper, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
         super(classLoadingHelper, defaultPackageStaxBuilder);
      }

      public void verifyClass(Class clazz) {
      }

      protected String getAcceptableQName() {
         return "group-sequence";
      }

      // $FF: synthetic method
      GroupSequenceStaxBuilder(ClassLoadingHelper x0, DefaultPackageStaxBuilder x1, Object x2) {
         this(x0, x1);
      }
   }
}

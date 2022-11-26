package org.hibernate.validator.internal.xml.mapping;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;

class BeanStaxBuilder extends AbstractStaxBuilder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final QName IGNORE_ANNOTATIONS_QNAME = new QName("ignore-annotations");
   private static final QName CLASS_QNAME = new QName("class");
   private static final String BEAN_QNAME_LOCAL_PART = "bean";
   private final ClassLoadingHelper classLoadingHelper;
   private final ConstraintHelper constraintHelper;
   private final TypeResolutionHelper typeResolutionHelper;
   private final ValueExtractorManager valueExtractorManager;
   private final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
   private final AnnotationProcessingOptionsImpl annotationProcessingOptions;
   private final Map defaultSequences;
   protected String className;
   protected Optional ignoreAnnotations;
   private ClassConstraintTypeStaxBuilder classConstraintTypeStaxBuilder;
   private final List constrainedFieldStaxBuilders;
   private final List constrainedGetterStaxBuilders;
   private final List constrainedMethodStaxBuilders;
   private final List constrainedConstructorStaxBuilders;

   BeanStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions, Map defaultSequences) {
      this.classLoadingHelper = classLoadingHelper;
      this.defaultPackageStaxBuilder = defaultPackageStaxBuilder;
      this.constraintHelper = constraintHelper;
      this.typeResolutionHelper = typeResolutionHelper;
      this.valueExtractorManager = valueExtractorManager;
      this.annotationProcessingOptions = annotationProcessingOptions;
      this.defaultSequences = defaultSequences;
      this.constrainedFieldStaxBuilders = new ArrayList();
      this.constrainedGetterStaxBuilders = new ArrayList();
      this.constrainedMethodStaxBuilders = new ArrayList();
      this.constrainedConstructorStaxBuilders = new ArrayList();
   }

   protected String getAcceptableQName() {
      return "bean";
   }

   protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
      this.className = (String)this.readAttribute(xmlEvent.asStartElement(), CLASS_QNAME).get();
      this.ignoreAnnotations = this.readAttribute(xmlEvent.asStartElement(), IGNORE_ANNOTATIONS_QNAME).map(Boolean::parseBoolean);
      ConstrainedFieldStaxBuilder fieldStaxBuilder = this.getNewConstrainedFieldStaxBuilder();
      ConstrainedGetterStaxBuilder getterStaxBuilder = this.getNewConstrainedGetterStaxBuilder();
      ConstrainedMethodStaxBuilder methodStaxBuilder = this.getNewConstrainedMethodStaxBuilder();
      ConstrainedConstructorStaxBuilder constructorStaxBuilder = this.getNewConstrainedConstructorStaxBuilder();
      ClassConstraintTypeStaxBuilder localClassConstraintTypeStaxBuilder = new ClassConstraintTypeStaxBuilder(this.classLoadingHelper, this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.defaultPackageStaxBuilder, this.annotationProcessingOptions, this.defaultSequences);

      while(!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals(this.getAcceptableQName())) {
         xmlEvent = xmlEventReader.nextEvent();
         if (fieldStaxBuilder.process(xmlEventReader, xmlEvent)) {
            this.constrainedFieldStaxBuilders.add(fieldStaxBuilder);
            fieldStaxBuilder = this.getNewConstrainedFieldStaxBuilder();
         } else if (getterStaxBuilder.process(xmlEventReader, xmlEvent)) {
            this.constrainedGetterStaxBuilders.add(getterStaxBuilder);
            getterStaxBuilder = this.getNewConstrainedGetterStaxBuilder();
         } else if (methodStaxBuilder.process(xmlEventReader, xmlEvent)) {
            this.constrainedMethodStaxBuilders.add(methodStaxBuilder);
            methodStaxBuilder = this.getNewConstrainedMethodStaxBuilder();
         } else if (constructorStaxBuilder.process(xmlEventReader, xmlEvent)) {
            this.constrainedConstructorStaxBuilders.add(constructorStaxBuilder);
            constructorStaxBuilder = this.getNewConstrainedConstructorStaxBuilder();
         } else if (localClassConstraintTypeStaxBuilder.process(xmlEventReader, xmlEvent)) {
            this.classConstraintTypeStaxBuilder = localClassConstraintTypeStaxBuilder;
         }
      }

   }

   private ConstrainedFieldStaxBuilder getNewConstrainedFieldStaxBuilder() {
      return new ConstrainedFieldStaxBuilder(this.classLoadingHelper, this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.defaultPackageStaxBuilder, this.annotationProcessingOptions);
   }

   private ConstrainedGetterStaxBuilder getNewConstrainedGetterStaxBuilder() {
      return new ConstrainedGetterStaxBuilder(this.classLoadingHelper, this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.defaultPackageStaxBuilder, this.annotationProcessingOptions);
   }

   private ConstrainedMethodStaxBuilder getNewConstrainedMethodStaxBuilder() {
      return new ConstrainedMethodStaxBuilder(this.classLoadingHelper, this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.defaultPackageStaxBuilder, this.annotationProcessingOptions);
   }

   private ConstrainedConstructorStaxBuilder getNewConstrainedConstructorStaxBuilder() {
      return new ConstrainedConstructorStaxBuilder(this.classLoadingHelper, this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.defaultPackageStaxBuilder, this.annotationProcessingOptions);
   }

   void build(Set processedClasses, Map constrainedElementsByType) {
      Class beanClass = this.classLoadingHelper.loadClass(this.className, (String)this.defaultPackageStaxBuilder.build().orElse(""));
      this.checkClassHasNotBeenProcessed(processedClasses, beanClass);
      this.annotationProcessingOptions.ignoreAnnotationConstraintForClass(beanClass, (Boolean)this.ignoreAnnotations.orElse(true));
      if (this.classConstraintTypeStaxBuilder != null) {
         this.addConstrainedElements(constrainedElementsByType, beanClass, Collections.singleton(this.classConstraintTypeStaxBuilder.build(beanClass)));
      }

      List alreadyProcessedFieldNames = new ArrayList(this.constrainedFieldStaxBuilders.size());
      this.addConstrainedElements(constrainedElementsByType, beanClass, (Collection)this.constrainedFieldStaxBuilders.stream().map((builder) -> {
         return builder.build(beanClass, alreadyProcessedFieldNames);
      }).collect(Collectors.toList()));
      List alreadyProcessedGetterNames = new ArrayList(this.constrainedGetterStaxBuilders.size());
      this.addConstrainedElements(constrainedElementsByType, beanClass, (Collection)this.constrainedGetterStaxBuilders.stream().map((builder) -> {
         return builder.build(beanClass, alreadyProcessedGetterNames);
      }).collect(Collectors.toList()));
      List alreadyProcessedMethods = new ArrayList(this.constrainedMethodStaxBuilders.size());
      this.addConstrainedElements(constrainedElementsByType, beanClass, (Collection)this.constrainedMethodStaxBuilders.stream().map((builder) -> {
         return builder.build(beanClass, alreadyProcessedMethods);
      }).collect(Collectors.toList()));
      List alreadyProcessedConstructors = new ArrayList(this.constrainedConstructorStaxBuilders.size());
      this.addConstrainedElements(constrainedElementsByType, beanClass, (Collection)this.constrainedConstructorStaxBuilders.stream().map((builder) -> {
         return builder.build(beanClass, alreadyProcessedConstructors);
      }).collect(Collectors.toList()));
   }

   private void addConstrainedElements(Map constrainedElementsbyType, Class beanClass, Collection newConstrainedElements) {
      if (constrainedElementsbyType.containsKey(beanClass)) {
         Set existingConstrainedElements = (Set)constrainedElementsbyType.get(beanClass);
         Iterator var5 = newConstrainedElements.iterator();

         while(var5.hasNext()) {
            ConstrainedElement constrainedElement = (ConstrainedElement)var5.next();
            if (existingConstrainedElements.contains(constrainedElement)) {
               throw LOG.getConstrainedElementConfiguredMultipleTimesException(constrainedElement.toString());
            }
         }

         existingConstrainedElements.addAll(newConstrainedElements);
      } else {
         Set tmpSet = CollectionHelper.newHashSet();
         tmpSet.addAll(newConstrainedElements);
         constrainedElementsbyType.put(beanClass, tmpSet);
      }

   }

   private void checkClassHasNotBeenProcessed(Set processedClasses, Class beanClass) {
      if (processedClasses.contains(beanClass)) {
         throw LOG.getBeanClassHasAlreadyBeenConfiguredInXmlException(beanClass);
      } else {
         processedClasses.add(beanClass);
      }
   }
}

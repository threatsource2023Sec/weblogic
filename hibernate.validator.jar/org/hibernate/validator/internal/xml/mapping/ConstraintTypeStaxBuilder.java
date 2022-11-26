package org.hibernate.validator.internal.xml.mapping;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.Payload;
import javax.validation.ValidationException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.core.MetaConstraints;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;

class ConstraintTypeStaxBuilder extends AbstractStaxBuilder {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final Pattern IS_ONLY_WHITESPACE = Pattern.compile("\\s*");
   private static final String CONSTRAINT_QNAME_LOCAL_PART = "constraint";
   private static final QName CONSTRAINT_ANNOTATION_QNAME = new QName("annotation");
   private final ClassLoadingHelper classLoadingHelper;
   private final ConstraintHelper constraintHelper;
   private final TypeResolutionHelper typeResolutionHelper;
   private final ValueExtractorManager valueExtractorManager;
   private final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
   private final GroupsStaxBuilder groupsStaxBuilder;
   private final PayloadStaxBuilder payloadStaxBuilder;
   private final ConstraintParameterStaxBuilder constrainParameterStaxBuilder;
   private final MessageStaxBuilder messageStaxBuilder;
   private final List builders;
   private String constraintAnnotation;

   ConstraintTypeStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
      this.classLoadingHelper = classLoadingHelper;
      this.defaultPackageStaxBuilder = defaultPackageStaxBuilder;
      this.constraintHelper = constraintHelper;
      this.typeResolutionHelper = typeResolutionHelper;
      this.valueExtractorManager = valueExtractorManager;
      this.groupsStaxBuilder = new GroupsStaxBuilder(classLoadingHelper, defaultPackageStaxBuilder);
      this.payloadStaxBuilder = new PayloadStaxBuilder(classLoadingHelper, defaultPackageStaxBuilder);
      this.constrainParameterStaxBuilder = new ConstraintParameterStaxBuilder(classLoadingHelper, defaultPackageStaxBuilder);
      this.messageStaxBuilder = new MessageStaxBuilder();
      this.builders = (List)Stream.of(this.groupsStaxBuilder, this.payloadStaxBuilder, this.constrainParameterStaxBuilder, this.messageStaxBuilder).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
   }

   protected String getAcceptableQName() {
      return "constraint";
   }

   protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
      StartElement startElement = xmlEvent.asStartElement();

      for(this.constraintAnnotation = (String)this.readAttribute(startElement, CONSTRAINT_ANNOTATION_QNAME).get(); !xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("constraint"); xmlEvent = xmlEventReader.nextEvent()) {
         this.builders.forEach((builder) -> {
            builder.process(xmlEventReader, xmlEvent);
         });
      }

   }

   MetaConstraint build(ConstraintLocation constraintLocation, ElementType type, ConstraintDescriptorImpl.ConstraintType constraintType) {
      String defaultPackage = (String)this.defaultPackageStaxBuilder.build().orElse("");

      Class annotationClass;
      try {
         annotationClass = this.classLoadingHelper.loadClass(this.constraintAnnotation, defaultPackage);
      } catch (ValidationException var12) {
         throw LOG.getUnableToLoadConstraintAnnotationClassException(this.constraintAnnotation, var12);
      }

      ConstraintAnnotationDescriptor.Builder annotationDescriptorBuilder = new ConstraintAnnotationDescriptor.Builder(annotationClass);
      Optional message = this.messageStaxBuilder.build();
      if (message.isPresent()) {
         annotationDescriptorBuilder.setMessage((String)message.get());
      }

      annotationDescriptorBuilder.setGroups(this.groupsStaxBuilder.build()).setPayload(this.payloadStaxBuilder.build());
      Map parameters = this.constrainParameterStaxBuilder.build(annotationClass);
      Iterator var9 = parameters.entrySet().iterator();

      while(var9.hasNext()) {
         Map.Entry parameter = (Map.Entry)var9.next();
         annotationDescriptorBuilder.setAttribute((String)parameter.getKey(), parameter.getValue());
      }

      ConstraintAnnotationDescriptor annotationDescriptor;
      try {
         annotationDescriptor = annotationDescriptorBuilder.build();
      } catch (RuntimeException var11) {
         throw LOG.getUnableToCreateAnnotationForConfiguredConstraintException(var11);
      }

      ConstraintDescriptorImpl constraintDescriptor = new ConstraintDescriptorImpl(this.constraintHelper, constraintLocation.getMember(), annotationDescriptor, type, constraintType);
      return MetaConstraints.create(this.typeResolutionHelper, this.valueExtractorManager, constraintDescriptor, constraintLocation);
   }

   private static class PayloadStaxBuilder extends AbstractMultiValuedElementStaxBuilder {
      private static final String PAYLOAD_QNAME_LOCAL_PART = "payload";

      private PayloadStaxBuilder(ClassLoadingHelper classLoadingHelper, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
         super(classLoadingHelper, defaultPackageStaxBuilder);
      }

      public void verifyClass(Class payload) {
         if (!Payload.class.isAssignableFrom(payload)) {
            throw ConstraintTypeStaxBuilder.LOG.getWrongPayloadClassException(payload);
         }
      }

      protected String getAcceptableQName() {
         return "payload";
      }

      // $FF: synthetic method
      PayloadStaxBuilder(ClassLoadingHelper x0, DefaultPackageStaxBuilder x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class GroupsStaxBuilder extends AbstractMultiValuedElementStaxBuilder {
      private static final String GROUPS_QNAME_LOCAL_PART = "groups";

      private GroupsStaxBuilder(ClassLoadingHelper classLoadingHelper, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
         super(classLoadingHelper, defaultPackageStaxBuilder);
      }

      public void verifyClass(Class clazz) {
      }

      protected String getAcceptableQName() {
         return "groups";
      }

      // $FF: synthetic method
      GroupsStaxBuilder(ClassLoadingHelper x0, DefaultPackageStaxBuilder x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class AnnotationParameterStaxBuilder extends AbstractStaxBuilder {
      private static final String ANNOTATION_QNAME_LOCAL_PART = "annotation";
      private static final String ELEMENT_QNAME_LOCAL_PART = "element";
      private static final String VALUE_QNAME_LOCAL_PART = "value";
      private static final QName NAME_QNAME = new QName("name");
      private final ClassLoadingHelper classLoadingHelper;
      protected final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
      protected Map parameters;
      protected Map annotationParameters;

      public AnnotationParameterStaxBuilder(ClassLoadingHelper classLoadingHelper, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
         this.classLoadingHelper = classLoadingHelper;
         this.defaultPackageStaxBuilder = defaultPackageStaxBuilder;
         this.parameters = new HashMap();
         this.annotationParameters = new HashMap();
      }

      protected String getAcceptableQName() {
         return "annotation";
      }

      protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
         while(!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("annotation")) {
            xmlEvent = xmlEventReader.nextEvent();
            if (xmlEvent.isStartElement()) {
               StartElement startElement = xmlEvent.asStartElement();
               if (startElement.getName().getLocalPart().equals("element")) {
                  String name = (String)this.readAttribute(xmlEvent.asStartElement(), NAME_QNAME).get();
                  this.parameters.put(name, Collections.emptyList());

                  while(!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("element")) {
                     this.readElement(xmlEventReader, xmlEvent, name);
                     xmlEvent = xmlEventReader.nextEvent();
                  }
               }
            }
         }

      }

      protected void readElement(XMLEventReader xmlEventReader, XMLEvent xmlEvent, String name) throws XMLStreamException {
         if (xmlEvent.isCharacters() && !xmlEvent.asCharacters().getData().trim().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder(xmlEvent.asCharacters().getData());

            while(xmlEventReader.peek().isCharacters()) {
               xmlEvent = xmlEventReader.nextEvent();
               stringBuilder.append(xmlEvent.asCharacters().getData());
            }

            this.addParameterValue(name, stringBuilder.toString().trim());
         } else if (xmlEvent.isStartElement()) {
            StartElement startElement = xmlEvent.asStartElement();
            if (startElement.getName().getLocalPart().equals("value")) {
               this.addParameterValue(name, this.readSingleElement(xmlEventReader));
            } else if (startElement.getName().getLocalPart().equals("annotation")) {
               this.addAnnotationParameterValue(name, xmlEventReader, xmlEvent);
            }
         }

      }

      protected void addAnnotationParameterValue(String name, XMLEventReader xmlEventReader, XMLEvent xmlEvent) {
         this.checkNameIsValid(name);
         AnnotationParameterStaxBuilder annotationParameterStaxBuilder = new AnnotationParameterStaxBuilder(this.classLoadingHelper, this.defaultPackageStaxBuilder);
         annotationParameterStaxBuilder.process(xmlEventReader, xmlEvent);
         this.annotationParameters.merge(name, Collections.singletonList(annotationParameterStaxBuilder), (v1, v2) -> {
            return (List)Stream.concat(v1.stream(), v2.stream()).collect(Collectors.toList());
         });
      }

      protected void addParameterValue(String name, String value) {
         this.checkNameIsValid(name);
         this.parameters.merge(name, Collections.singletonList(value), (v1, v2) -> {
            return (List)Stream.concat(v1.stream(), v2.stream()).collect(Collectors.toList());
         });
      }

      protected void checkNameIsValid(String name) {
      }

      public Annotation build(Class annotationClass, String defaultPackage) {
         AnnotationDescriptor.Builder annotationDescriptorBuilder = new AnnotationDescriptor.Builder(annotationClass);
         Iterator var4 = this.parameters.entrySet().iterator();

         Map.Entry parameter;
         while(var4.hasNext()) {
            parameter = (Map.Entry)var4.next();
            annotationDescriptorBuilder.setAttribute((String)parameter.getKey(), this.getElementValue((List)parameter.getValue(), annotationClass, (String)parameter.getKey(), defaultPackage));
         }

         var4 = this.annotationParameters.entrySet().iterator();

         while(var4.hasNext()) {
            parameter = (Map.Entry)var4.next();
            annotationDescriptorBuilder.setAttribute((String)parameter.getKey(), this.getAnnotationElementValue((List)parameter.getValue(), annotationClass, (String)parameter.getKey(), defaultPackage));
         }

         return annotationDescriptorBuilder.build().getAnnotation();
      }

      protected Object getElementValue(List parsedParameters, Class annotationClass, String name, String defaultPackage) {
         List parameters = removeEmptyContentElements(parsedParameters);
         Class returnType = getAnnotationParameterType(annotationClass, name);
         boolean isArray = returnType.isArray();
         if (!isArray) {
            if (parameters.size() == 0) {
               return "";
            } else if (parameters.size() > 1) {
               throw ConstraintTypeStaxBuilder.LOG.getAttemptToSpecifyAnArrayWhereSingleValueIsExpectedException();
            } else {
               return this.convertStringToReturnType((String)parameters.get(0), returnType, defaultPackage);
            }
         } else {
            return parameters.stream().map((value) -> {
               return this.convertStringToReturnType(value, returnType.getComponentType(), defaultPackage);
            }).toArray((size) -> {
               return (Object[])((Object[])Array.newInstance(returnType.getComponentType(), size));
            });
         }
      }

      protected Object getAnnotationElementValue(List parameters, Class annotationClass, String name, String defaultPackage) {
         Class returnType = getAnnotationParameterType(annotationClass, name);
         boolean isArray = returnType.isArray();
         if (!isArray) {
            if (parameters.size() == 0) {
               throw ConstraintTypeStaxBuilder.LOG.getEmptyElementOnlySupportedWhenCharSequenceIsExpectedExpection();
            } else if (parameters.size() > 1) {
               throw ConstraintTypeStaxBuilder.LOG.getAttemptToSpecifyAnArrayWhereSingleValueIsExpectedException();
            } else {
               return ((AnnotationParameterStaxBuilder)parameters.get(0)).build(returnType, defaultPackage);
            }
         } else {
            return parameters.stream().map((value) -> {
               return value.build(returnType.getComponentType(), defaultPackage);
            }).toArray((size) -> {
               return (Object[])((Object[])Array.newInstance(returnType.getComponentType(), size));
            });
         }
      }

      private static List removeEmptyContentElements(List params) {
         return (List)params.stream().filter((content) -> {
            return !ConstraintTypeStaxBuilder.IS_ONLY_WHITESPACE.matcher(content).matches();
         }).collect(Collectors.toList());
      }

      private static Class getAnnotationParameterType(Class annotationClass, String name) {
         Method m = (Method)run(GetMethod.action(annotationClass, name));
         if (m == null) {
            throw ConstraintTypeStaxBuilder.LOG.getAnnotationDoesNotContainAParameterException(annotationClass, name);
         } else {
            return m.getReturnType();
         }
      }

      private Object convertStringToReturnType(String value, Class returnType, String defaultPackage) {
         Object returnValue;
         if (returnType == Byte.TYPE) {
            try {
               returnValue = Byte.parseByte(value);
            } catch (NumberFormatException var12) {
               throw ConstraintTypeStaxBuilder.LOG.getInvalidNumberFormatException("byte", var12);
            }
         } else if (returnType == Short.TYPE) {
            try {
               returnValue = Short.parseShort(value);
            } catch (NumberFormatException var11) {
               throw ConstraintTypeStaxBuilder.LOG.getInvalidNumberFormatException("short", var11);
            }
         } else if (returnType == Integer.TYPE) {
            try {
               returnValue = Integer.parseInt(value);
            } catch (NumberFormatException var10) {
               throw ConstraintTypeStaxBuilder.LOG.getInvalidNumberFormatException("int", var10);
            }
         } else if (returnType == Long.TYPE) {
            try {
               returnValue = Long.parseLong(value);
            } catch (NumberFormatException var9) {
               throw ConstraintTypeStaxBuilder.LOG.getInvalidNumberFormatException("long", var9);
            }
         } else if (returnType == Float.TYPE) {
            try {
               returnValue = Float.parseFloat(value);
            } catch (NumberFormatException var8) {
               throw ConstraintTypeStaxBuilder.LOG.getInvalidNumberFormatException("float", var8);
            }
         } else if (returnType == Double.TYPE) {
            try {
               returnValue = Double.parseDouble(value);
            } catch (NumberFormatException var7) {
               throw ConstraintTypeStaxBuilder.LOG.getInvalidNumberFormatException("double", var7);
            }
         } else if (returnType == Boolean.TYPE) {
            returnValue = Boolean.parseBoolean(value);
         } else if (returnType == Character.TYPE) {
            if (value.length() != 1) {
               throw ConstraintTypeStaxBuilder.LOG.getInvalidCharValueException(value);
            }

            returnValue = value.charAt(0);
         } else if (returnType == String.class) {
            returnValue = value;
         } else if (returnType == Class.class) {
            returnValue = this.classLoadingHelper.loadClass(value, defaultPackage);
         } else {
            try {
               returnValue = Enum.valueOf(returnType, value);
            } catch (ClassCastException var6) {
               throw ConstraintTypeStaxBuilder.LOG.getInvalidReturnTypeException(returnType, var6);
            }
         }

         return returnValue;
      }

      private static Object run(PrivilegedAction action) {
         return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
      }
   }

   private static class ConstraintParameterStaxBuilder extends AnnotationParameterStaxBuilder {
      private static final String ELEMENT_QNAME_LOCAL_PART = "element";
      private static final QName NAME_QNAME = new QName("name");

      public ConstraintParameterStaxBuilder(ClassLoadingHelper classLoadingHelper, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
         super(classLoadingHelper, defaultPackageStaxBuilder);
      }

      protected String getAcceptableQName() {
         return "element";
      }

      protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
         String name = (String)this.readAttribute(xmlEvent.asStartElement(), NAME_QNAME).get();

         while(!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals("element")) {
            xmlEvent = xmlEventReader.nextEvent();
            this.readElement(xmlEventReader, xmlEvent, name);
         }

      }

      protected void checkNameIsValid(String name) {
         if ("message".equals(name) || "groups".equals(name) || "payload".equals(name)) {
            throw ConstraintTypeStaxBuilder.LOG.getReservedParameterNamesException("message", "groups", "payload");
         }
      }

      public Map build(Class annotationClass) {
         String defaultPackage = (String)this.defaultPackageStaxBuilder.build().orElse("");
         Map builtParameters = new HashMap();
         Iterator var4 = this.parameters.entrySet().iterator();

         Map.Entry parameter;
         while(var4.hasNext()) {
            parameter = (Map.Entry)var4.next();
            builtParameters.put(parameter.getKey(), this.getElementValue((List)parameter.getValue(), annotationClass, (String)parameter.getKey(), defaultPackage));
         }

         var4 = this.annotationParameters.entrySet().iterator();

         while(var4.hasNext()) {
            parameter = (Map.Entry)var4.next();
            builtParameters.put(parameter.getKey(), this.getAnnotationElementValue((List)parameter.getValue(), annotationClass, (String)parameter.getKey(), defaultPackage));
         }

         return builtParameters;
      }
   }

   private static class MessageStaxBuilder extends AbstractOneLineStringStaxBuilder {
      private static final String MESSAGE_PACKAGE_QNAME = "message";

      private MessageStaxBuilder() {
      }

      protected String getAcceptableQName() {
         return "message";
      }

      // $FF: synthetic method
      MessageStaxBuilder(Object x0) {
         this();
      }
   }
}

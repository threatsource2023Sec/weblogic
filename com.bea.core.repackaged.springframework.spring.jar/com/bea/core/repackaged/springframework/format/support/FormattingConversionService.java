package com.bea.core.repackaged.springframework.format.support;

import com.bea.core.repackaged.springframework.context.EmbeddedValueResolverAware;
import com.bea.core.repackaged.springframework.context.i18n.LocaleContextHolder;
import com.bea.core.repackaged.springframework.core.DecoratingProxy;
import com.bea.core.repackaged.springframework.core.GenericTypeResolver;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.core.convert.support.GenericConversionService;
import com.bea.core.repackaged.springframework.format.AnnotationFormatterFactory;
import com.bea.core.repackaged.springframework.format.Formatter;
import com.bea.core.repackaged.springframework.format.FormatterRegistry;
import com.bea.core.repackaged.springframework.format.Parser;
import com.bea.core.repackaged.springframework.format.Printer;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FormattingConversionService extends GenericConversionService implements FormatterRegistry, EmbeddedValueResolverAware {
   @Nullable
   private StringValueResolver embeddedValueResolver;
   private final Map cachedPrinters = new ConcurrentHashMap(64);
   private final Map cachedParsers = new ConcurrentHashMap(64);

   public void setEmbeddedValueResolver(StringValueResolver resolver) {
      this.embeddedValueResolver = resolver;
   }

   public void addFormatter(Formatter formatter) {
      this.addFormatterForFieldType(getFieldType(formatter), formatter);
   }

   public void addFormatterForFieldType(Class fieldType, Formatter formatter) {
      this.addConverter(new PrinterConverter(fieldType, formatter, this));
      this.addConverter(new ParserConverter(fieldType, formatter, this));
   }

   public void addFormatterForFieldType(Class fieldType, Printer printer, Parser parser) {
      this.addConverter(new PrinterConverter(fieldType, printer, this));
      this.addConverter(new ParserConverter(fieldType, parser, this));
   }

   public void addFormatterForFieldAnnotation(AnnotationFormatterFactory annotationFormatterFactory) {
      Class annotationType = getAnnotationType(annotationFormatterFactory);
      if (this.embeddedValueResolver != null && annotationFormatterFactory instanceof EmbeddedValueResolverAware) {
         ((EmbeddedValueResolverAware)annotationFormatterFactory).setEmbeddedValueResolver(this.embeddedValueResolver);
      }

      Set fieldTypes = annotationFormatterFactory.getFieldTypes();
      Iterator var4 = fieldTypes.iterator();

      while(var4.hasNext()) {
         Class fieldType = (Class)var4.next();
         this.addConverter(new AnnotationPrinterConverter(annotationType, annotationFormatterFactory, fieldType));
         this.addConverter(new AnnotationParserConverter(annotationType, annotationFormatterFactory, fieldType));
      }

   }

   static Class getFieldType(Formatter formatter) {
      Class fieldType = GenericTypeResolver.resolveTypeArgument(formatter.getClass(), Formatter.class);
      if (fieldType == null && formatter instanceof DecoratingProxy) {
         fieldType = GenericTypeResolver.resolveTypeArgument(((DecoratingProxy)formatter).getDecoratedClass(), Formatter.class);
      }

      if (fieldType == null) {
         throw new IllegalArgumentException("Unable to extract the parameterized field type from Formatter [" + formatter.getClass().getName() + "]; does the class parameterize the <T> generic type?");
      } else {
         return fieldType;
      }
   }

   static Class getAnnotationType(AnnotationFormatterFactory factory) {
      Class annotationType = GenericTypeResolver.resolveTypeArgument(factory.getClass(), AnnotationFormatterFactory.class);
      if (annotationType == null) {
         throw new IllegalArgumentException("Unable to extract parameterized Annotation type argument from AnnotationFormatterFactory [" + factory.getClass().getName() + "]; does the factory parameterize the <A extends Annotation> generic type?");
      } else {
         return annotationType;
      }
   }

   private static class AnnotationConverterKey {
      private final Annotation annotation;
      private final Class fieldType;

      public AnnotationConverterKey(Annotation annotation, Class fieldType) {
         this.annotation = annotation;
         this.fieldType = fieldType;
      }

      public Annotation getAnnotation() {
         return this.annotation;
      }

      public Class getFieldType() {
         return this.fieldType;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else {
            AnnotationConverterKey otherKey = (AnnotationConverterKey)other;
            return this.fieldType == otherKey.fieldType && this.annotation.equals(otherKey.annotation);
         }
      }

      public int hashCode() {
         return this.fieldType.hashCode() * 29 + this.annotation.hashCode();
      }
   }

   private class AnnotationParserConverter implements ConditionalGenericConverter {
      private final Class annotationType;
      private final AnnotationFormatterFactory annotationFormatterFactory;
      private final Class fieldType;

      public AnnotationParserConverter(Class annotationType, AnnotationFormatterFactory annotationFormatterFactory, Class fieldType) {
         this.annotationType = annotationType;
         this.annotationFormatterFactory = annotationFormatterFactory;
         this.fieldType = fieldType;
      }

      public Set getConvertibleTypes() {
         return Collections.singleton(new GenericConverter.ConvertiblePair(String.class, this.fieldType));
      }

      public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
         return targetType.hasAnnotation(this.annotationType);
      }

      @Nullable
      public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
         Annotation ann = targetType.getAnnotation(this.annotationType);
         if (ann == null) {
            throw new IllegalStateException("Expected [" + this.annotationType.getName() + "] to be present on " + targetType);
         } else {
            AnnotationConverterKey converterKey = new AnnotationConverterKey(ann, targetType.getObjectType());
            GenericConverter converter = (GenericConverter)FormattingConversionService.this.cachedParsers.get(converterKey);
            if (converter == null) {
               Parser parser = this.annotationFormatterFactory.getParser(converterKey.getAnnotation(), converterKey.getFieldType());
               converter = new ParserConverter(this.fieldType, parser, FormattingConversionService.this);
               FormattingConversionService.this.cachedParsers.put(converterKey, converter);
            }

            return ((GenericConverter)converter).convert(source, sourceType, targetType);
         }
      }

      public String toString() {
         return String.class.getName() + " -> @" + this.annotationType.getName() + " " + this.fieldType.getName() + ": " + this.annotationFormatterFactory;
      }
   }

   private class AnnotationPrinterConverter implements ConditionalGenericConverter {
      private final Class annotationType;
      private final AnnotationFormatterFactory annotationFormatterFactory;
      private final Class fieldType;

      public AnnotationPrinterConverter(Class annotationType, AnnotationFormatterFactory annotationFormatterFactory, Class fieldType) {
         this.annotationType = annotationType;
         this.annotationFormatterFactory = annotationFormatterFactory;
         this.fieldType = fieldType;
      }

      public Set getConvertibleTypes() {
         return Collections.singleton(new GenericConverter.ConvertiblePair(this.fieldType, String.class));
      }

      public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
         return sourceType.hasAnnotation(this.annotationType);
      }

      @Nullable
      public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
         Annotation ann = sourceType.getAnnotation(this.annotationType);
         if (ann == null) {
            throw new IllegalStateException("Expected [" + this.annotationType.getName() + "] to be present on " + sourceType);
         } else {
            AnnotationConverterKey converterKey = new AnnotationConverterKey(ann, sourceType.getObjectType());
            GenericConverter converter = (GenericConverter)FormattingConversionService.this.cachedPrinters.get(converterKey);
            if (converter == null) {
               Printer printer = this.annotationFormatterFactory.getPrinter(converterKey.getAnnotation(), converterKey.getFieldType());
               converter = new PrinterConverter(this.fieldType, printer, FormattingConversionService.this);
               FormattingConversionService.this.cachedPrinters.put(converterKey, converter);
            }

            return ((GenericConverter)converter).convert(source, sourceType, targetType);
         }
      }

      public String toString() {
         return "@" + this.annotationType.getName() + " " + this.fieldType.getName() + " -> " + String.class.getName() + ": " + this.annotationFormatterFactory;
      }
   }

   private static class ParserConverter implements GenericConverter {
      private final Class fieldType;
      private final Parser parser;
      private final ConversionService conversionService;

      public ParserConverter(Class fieldType, Parser parser, ConversionService conversionService) {
         this.fieldType = fieldType;
         this.parser = parser;
         this.conversionService = conversionService;
      }

      public Set getConvertibleTypes() {
         return Collections.singleton(new GenericConverter.ConvertiblePair(String.class, this.fieldType));
      }

      @Nullable
      public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
         String text = (String)source;
         if (!StringUtils.hasText(text)) {
            return null;
         } else {
            Object result;
            try {
               result = this.parser.parse(text, LocaleContextHolder.getLocale());
            } catch (IllegalArgumentException var7) {
               throw var7;
            } catch (Throwable var8) {
               throw new IllegalArgumentException("Parse attempt failed for value [" + text + "]", var8);
            }

            TypeDescriptor resultType = TypeDescriptor.valueOf(result.getClass());
            if (!resultType.isAssignableTo(targetType)) {
               result = this.conversionService.convert(result, resultType, targetType);
            }

            return result;
         }
      }

      public String toString() {
         return String.class.getName() + " -> " + this.fieldType.getName() + ": " + this.parser;
      }
   }

   private static class PrinterConverter implements GenericConverter {
      private final Class fieldType;
      private final TypeDescriptor printerObjectType;
      private final Printer printer;
      private final ConversionService conversionService;

      public PrinterConverter(Class fieldType, Printer printer, ConversionService conversionService) {
         this.fieldType = fieldType;
         this.printerObjectType = TypeDescriptor.valueOf(this.resolvePrinterObjectType(printer));
         this.printer = printer;
         this.conversionService = conversionService;
      }

      public Set getConvertibleTypes() {
         return Collections.singleton(new GenericConverter.ConvertiblePair(this.fieldType, String.class));
      }

      public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
         if (!sourceType.isAssignableTo(this.printerObjectType)) {
            source = this.conversionService.convert(source, sourceType, this.printerObjectType);
         }

         return source == null ? "" : this.printer.print(source, LocaleContextHolder.getLocale());
      }

      @Nullable
      private Class resolvePrinterObjectType(Printer printer) {
         return GenericTypeResolver.resolveTypeArgument(printer.getClass(), Printer.class);
      }

      public String toString() {
         return this.fieldType.getName() + " -> " + String.class.getName() + " : " + this.printer;
      }
   }
}

package com.bea.core.repackaged.springframework.format;

import com.bea.core.repackaged.springframework.core.convert.converter.ConverterRegistry;

public interface FormatterRegistry extends ConverterRegistry {
   void addFormatter(Formatter var1);

   void addFormatterForFieldType(Class var1, Formatter var2);

   void addFormatterForFieldType(Class var1, Printer var2, Parser var3);

   void addFormatterForFieldAnnotation(AnnotationFormatterFactory var1);
}

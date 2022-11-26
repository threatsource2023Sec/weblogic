package com.bea.core.repackaged.springframework.format;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface AnnotationFormatterFactory {
   Set getFieldTypes();

   Printer getPrinter(Annotation var1, Class var2);

   Parser getParser(Annotation var1, Class var2);
}

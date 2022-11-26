package weblogic.connector.configuration.meta;

import java.lang.annotation.Annotation;
import weblogic.connector.exception.RAException;

public interface TypeAnnotationProcessor {
   void processClass(Class var1, Annotation var2) throws RAException;
}

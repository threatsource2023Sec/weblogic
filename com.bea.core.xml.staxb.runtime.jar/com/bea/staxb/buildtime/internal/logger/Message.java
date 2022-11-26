package com.bea.staxb.buildtime.internal.logger;

import com.bea.util.jam.JElement;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import java.util.logging.Level;

public interface Message {
   Level getLevel();

   String getMessage();

   Throwable getException();

   JElement getJavaContext();

   SchemaType getSchemaTypeContext();

   SchemaProperty getSchemaPropertyContext();
}

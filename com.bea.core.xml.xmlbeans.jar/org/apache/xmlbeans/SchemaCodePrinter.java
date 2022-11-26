package org.apache.xmlbeans;

import java.io.IOException;
import java.io.Writer;

public interface SchemaCodePrinter {
   void printTypeImpl(Writer var1, SchemaType var2) throws IOException;

   void printType(Writer var1, SchemaType var2) throws IOException;

   /** @deprecated */
   void printLoader(Writer var1, SchemaTypeSystem var2) throws IOException;
}

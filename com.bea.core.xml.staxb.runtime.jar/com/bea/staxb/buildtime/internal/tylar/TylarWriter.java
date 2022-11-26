package com.bea.staxb.buildtime.internal.tylar;

import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.joust.JavaOutputStream;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.SchemaTypeSystem;
import java.io.IOException;
import java.util.Map;

public interface TylarWriter {
   void writeBindingFile(BindingFile var1) throws IOException;

   void writeSchema(SchemaDocument var1, String var2, Map var3) throws IOException;

   void writeSchemaTypeSystem(SchemaTypeSystem var1) throws IOException;

   JavaOutputStream getJavaOutputStream();

   void close() throws IOException;
}

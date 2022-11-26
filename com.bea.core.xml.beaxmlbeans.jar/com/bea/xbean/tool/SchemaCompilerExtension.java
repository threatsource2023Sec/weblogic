package com.bea.xbean.tool;

import com.bea.xml.SchemaTypeSystem;
import java.util.Map;

public interface SchemaCompilerExtension {
   void schemaCompilerExtension(SchemaTypeSystem var1, Map var2);

   String getExtensionName();
}

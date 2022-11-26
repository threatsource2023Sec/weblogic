package org.apache.xmlbeans.impl.tool;

import java.util.Map;
import org.apache.xmlbeans.SchemaTypeSystem;

public interface SchemaCompilerExtension {
   void schemaCompilerExtension(SchemaTypeSystem var1, Map var2);

   String getExtensionName();
}

package org.apache.xmlbeans;

import java.io.File;

public interface SchemaTypeSystem extends SchemaTypeLoader {
   String getName();

   SchemaType[] globalTypes();

   SchemaType[] documentTypes();

   SchemaType[] attributeTypes();

   SchemaGlobalElement[] globalElements();

   SchemaGlobalAttribute[] globalAttributes();

   SchemaModelGroup[] modelGroups();

   SchemaAttributeGroup[] attributeGroups();

   SchemaAnnotation[] annotations();

   void resolve();

   SchemaComponent resolveHandle(String var1);

   SchemaType typeForHandle(String var1);

   ClassLoader getClassLoader();

   void saveToDirectory(File var1);

   void save(Filer var1);
}

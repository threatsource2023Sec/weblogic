package org.apache.xmlbeans.impl.tool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import org.apache.xmlbeans.impl.xb.xsdschema.ImportDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.IncludeDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;

public abstract class SchemaImportResolver {
   public abstract SchemaResource lookupResource(String var1, String var2);

   public abstract void reportActualNamespace(SchemaResource var1, String var2);

   protected final void resolveImports(SchemaResource[] resources) {
      LinkedList queueOfResources = new LinkedList(Arrays.asList(resources));
      LinkedList queueOfLocators = new LinkedList();
      Set seenResources = new HashSet();

      while(true) {
         SchemaResource nextResource;
         SchemaDocument.Schema schema;
         do {
            do {
               do {
                  if (!queueOfResources.isEmpty()) {
                     nextResource = (SchemaResource)queueOfResources.removeFirst();
                     break;
                  }

                  if (queueOfLocators.isEmpty()) {
                     return;
                  }

                  SchemaLocator locator = (SchemaLocator)queueOfLocators.removeFirst();
                  nextResource = this.lookupResource(locator.namespace, locator.schemaLocation);
               } while(nextResource == null);
            } while(seenResources.contains(nextResource));

            seenResources.add(nextResource);
            schema = nextResource.getSchema();
         } while(schema == null);

         String actualTargetNamespace = schema.getTargetNamespace();
         if (actualTargetNamespace == null) {
            actualTargetNamespace = "";
         }

         String expectedTargetNamespace = nextResource.getNamespace();
         if (expectedTargetNamespace == null || !actualTargetNamespace.equals(expectedTargetNamespace)) {
            this.reportActualNamespace(nextResource, actualTargetNamespace);
         }

         ImportDocument.Import[] schemaImports = schema.getImportArray();

         for(int i = 0; i < schemaImports.length; ++i) {
            queueOfLocators.add(new SchemaLocator(schemaImports[i].getNamespace() == null ? "" : schemaImports[i].getNamespace(), schemaImports[i].getSchemaLocation()));
         }

         IncludeDocument.Include[] schemaIncludes = schema.getIncludeArray();

         for(int i = 0; i < schemaIncludes.length; ++i) {
            queueOfLocators.add(new SchemaLocator((String)null, schemaIncludes[i].getSchemaLocation()));
         }
      }
   }

   private static class SchemaLocator {
      public final String namespace;
      public final String schemaLocation;

      public SchemaLocator(String namespace, String schemaLocation) {
         this.namespace = namespace;
         this.schemaLocation = schemaLocation;
      }
   }

   public interface SchemaResource {
      SchemaDocument.Schema getSchema();

      String getNamespace();

      String getSchemaLocation();
   }
}

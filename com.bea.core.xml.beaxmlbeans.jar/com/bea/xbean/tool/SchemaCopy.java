package com.bea.xbean.tool;

import com.bea.xbean.common.IOUtil;
import com.bea.xbean.xb.substwsdl.DefinitionsDocument;
import com.bea.xbean.xb.substwsdl.TImport;
import com.bea.xbean.xb.xsdschema.ImportDocument;
import com.bea.xbean.xb.xsdschema.IncludeDocument;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.XmlCursor;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class SchemaCopy {
   private static final XmlOptions loadOptions = (new XmlOptions()).setLoadSubstituteNamespaces(Collections.singletonMap("http://schemas.xmlsoap.org/wsdl/", "http://www.apache.org/internal/xmlbeans/wsdlsubst"));

   public static void printUsage() {
      System.out.println("Copies the XML schema at the specified URL to the specified file.");
      System.out.println("Usage: scopy sourceurl [targetfile]");
      System.out.println("    sourceurl - The URL at which the schema is located.");
      System.out.println("    targetfile - The file to which the schema should be copied.");
      System.out.println();
   }

   public static void main(String[] args) {
      if (args.length >= 1 && args.length <= 2) {
         URI source = null;
         URI target = null;

         try {
            if (args[0].compareToIgnoreCase("-usage") == 0) {
               printUsage();
               return;
            }

            source = new URI(args[0]);
            source.toURL();
         } catch (Exception var8) {
            System.err.println("Badly formed URL " + source);
            return;
         }

         if (args.length < 2) {
            try {
               URI dir = (new File(".")).getCanonicalFile().toURI();
               String lastPart = source.getPath();
               lastPart = lastPart.substring(lastPart.lastIndexOf(47) + 1);
               target = CodeGenUtil.resolve(dir, URI.create(lastPart));
            } catch (Exception var7) {
               System.err.println("Cannot canonicalize current directory");
               return;
            }
         } else {
            try {
               target = new URI(args[1]);
               if (!target.isAbsolute()) {
                  target = null;
               } else if (!target.getScheme().equals("file")) {
                  target = null;
               }
            } catch (Exception var6) {
               target = null;
            }

            if (target == null) {
               try {
                  target = (new File(target)).getCanonicalFile().toURI();
               } catch (Exception var5) {
                  System.err.println("Cannot canonicalize current directory");
                  return;
               }
            }
         }

         Map thingsToCopy = findAllRelative(source, target);
         copyAll(thingsToCopy, true);
      } else {
         printUsage();
      }
   }

   private static void copyAll(Map uriMap, boolean stdout) {
      Iterator i = uriMap.keySet().iterator();

      while(i.hasNext()) {
         URI source = (URI)i.next();
         URI target = (URI)uriMap.get(source);

         try {
            IOUtil.copyCompletely(source, target);
         } catch (Exception var6) {
            if (stdout) {
               System.out.println("Could not copy " + source + " -> " + target);
            }
            continue;
         }

         if (stdout) {
            System.out.println("Copied " + source + " -> " + target);
         }
      }

   }

   public static Map findAllRelative(URI source, URI target) {
      Map result = new LinkedHashMap();
      result.put(source, target);
      LinkedList process = new LinkedList();
      process.add(source);

      while(!process.isEmpty()) {
         URI nextSource = (URI)process.removeFirst();
         URI nextTarget = (URI)result.get(nextSource);
         Map nextResults = findRelativeInOne(nextSource, nextTarget);
         Iterator i = nextResults.keySet().iterator();

         while(i.hasNext()) {
            URI newSource = (URI)i.next();
            if (!result.containsKey(newSource)) {
               result.put(newSource, nextResults.get(newSource));
               process.add(newSource);
            }
         }
      }

      return result;
   }

   private static Map findRelativeInOne(URI source, URI target) {
      try {
         URL sourceURL = source.toURL();
         XmlObject xobj = XmlObject.Factory.parse(sourceURL, loadOptions);
         XmlCursor xcur = xobj.newCursor();
         xcur.toFirstChild();
         Map result = new LinkedHashMap();
         if (xobj instanceof SchemaDocument) {
            putMappingsFromSchema(result, source, target, ((SchemaDocument)xobj).getSchema());
         } else if (xobj instanceof DefinitionsDocument) {
            putMappingsFromWsdl(result, source, target, ((DefinitionsDocument)xobj).getDefinitions());
         }

         return result;
      } catch (Exception var6) {
         return Collections.EMPTY_MAP;
      }
   }

   private static void putNewMapping(Map result, URI origSource, URI origTarget, String literalURI) {
      try {
         if (literalURI == null) {
            return;
         }

         URI newRelative = new URI(literalURI);
         if (newRelative.isAbsolute()) {
            return;
         }

         URI newSource = CodeGenUtil.resolve(origSource, newRelative);
         URI newTarget = CodeGenUtil.resolve(origTarget, newRelative);
         result.put(newSource, newTarget);
      } catch (URISyntaxException var7) {
      }

   }

   private static void putMappingsFromSchema(Map result, URI source, URI target, SchemaDocument.Schema schema) {
      ImportDocument.Import[] imports = schema.getImportArray();

      for(int i = 0; i < imports.length; ++i) {
         putNewMapping(result, source, target, imports[i].getSchemaLocation());
      }

      IncludeDocument.Include[] includes = schema.getIncludeArray();

      for(int i = 0; i < includes.length; ++i) {
         putNewMapping(result, source, target, includes[i].getSchemaLocation());
      }

   }

   private static void putMappingsFromWsdl(Map result, URI source, URI target, DefinitionsDocument.Definitions wdoc) {
      XmlObject[] types = wdoc.getTypesArray();

      for(int i = 0; i < types.length; ++i) {
         SchemaDocument.Schema[] schemas = (SchemaDocument.Schema[])((SchemaDocument.Schema[])types[i].selectPath("declare namespace xs='http://www.w3.org/2001/XMLSchema' xs:schema"));

         for(int j = 0; j < schemas.length; ++j) {
            putMappingsFromSchema(result, source, target, schemas[j]);
         }
      }

      TImport[] imports = wdoc.getImportArray();

      for(int i = 0; i < imports.length; ++i) {
         putNewMapping(result, source, target, imports[i].getLocation());
      }

   }
}

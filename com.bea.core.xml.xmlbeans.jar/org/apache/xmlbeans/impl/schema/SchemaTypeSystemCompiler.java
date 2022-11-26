package org.apache.xmlbeans.impl.schema;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.xmlbeans.BindingConfig;
import org.apache.xmlbeans.Filer;
import org.apache.xmlbeans.ResourceLoader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.common.XmlErrorWatcher;
import org.apache.xmlbeans.impl.xb.xsdschema.SchemaDocument;

public class SchemaTypeSystemCompiler {
   public static SchemaTypeSystem compile(Parameters params) {
      return compileImpl(params.getExistingTypeSystem(), params.getName(), params.getSchemas(), params.getConfig(), params.getLinkTo(), params.getOptions(), params.getErrorListener(), params.isJavaize(), params.getBaseURI(), params.getSourcesToCopyMap(), params.getSchemasDir());
   }

   public static SchemaTypeSystemImpl compile(String name, SchemaTypeSystem existingSTS, XmlObject[] input, BindingConfig config, SchemaTypeLoader linkTo, Filer filer, XmlOptions options) throws XmlException {
      options = XmlOptions.maskNull(options);
      ArrayList schemas = new ArrayList();
      if (input != null) {
         for(int i = 0; i < input.length; ++i) {
            if (input[i] instanceof SchemaDocument.Schema) {
               schemas.add(input[i]);
            } else {
               if (!(input[i] instanceof SchemaDocument) || ((SchemaDocument)input[i]).getSchema() == null) {
                  throw new XmlException("Thread " + Thread.currentThread().getName() + ": The " + i + "th supplied input is not a schema document: its type is " + input[i].schemaType());
               }

               schemas.add(((SchemaDocument)input[i]).getSchema());
            }
         }
      }

      Collection userErrors = (Collection)options.get("ERROR_LISTENER");
      XmlErrorWatcher errorWatcher = new XmlErrorWatcher(userErrors);
      SchemaTypeSystemImpl stsi = compileImpl(existingSTS, name, (SchemaDocument.Schema[])((SchemaDocument.Schema[])schemas.toArray(new SchemaDocument.Schema[schemas.size()])), config, linkTo, options, errorWatcher, filer != null, (URI)options.get("BASE_URI"), (Map)null, (File)null);
      if (errorWatcher.hasError() && stsi == null) {
         throw new XmlException(errorWatcher.firstError());
      } else {
         if (stsi != null && !stsi.isIncomplete() && filer != null) {
            stsi.save(filer);
            generateTypes(stsi, filer, options);
         }

         return stsi;
      }
   }

   static SchemaTypeSystemImpl compileImpl(SchemaTypeSystem system, String name, SchemaDocument.Schema[] schemas, BindingConfig config, SchemaTypeLoader linkTo, XmlOptions options, Collection outsideErrors, boolean javaize, URI baseURI, Map sourcesToCopyMap, File schemasDir) {
      if (linkTo == null) {
         throw new IllegalArgumentException("Must supply linkTo");
      } else {
         XmlErrorWatcher errorWatcher = new XmlErrorWatcher(outsideErrors);
         boolean incremental = system != null;
         StscState state = StscState.start();
         boolean validate = options == null || !options.hasOption("COMPILE_NO_VALIDATION");

         try {
            state.setErrorListener(errorWatcher);
            state.setBindingConfig(config);
            state.setOptions(options);
            state.setGivenTypeSystemName(name);
            state.setSchemasDir(schemasDir);
            if (baseURI != null) {
               state.setBaseUri(baseURI);
            }

            linkTo = SchemaTypeLoaderImpl.build(new SchemaTypeLoader[]{BuiltinSchemaTypeSystem.get(), linkTo}, (ResourceLoader)null, (ClassLoader)null);
            state.setImportingTypeLoader(linkTo);
            List validSchemas = new ArrayList(schemas.length);
            if (validate) {
               XmlOptions validateOptions = (new XmlOptions()).setErrorListener(errorWatcher);
               if (options.hasOption("VALIDATE_TREAT_LAX_AS_SKIP")) {
                  validateOptions.setValidateTreatLaxAsSkip();
               }

               for(int i = 0; i < schemas.length; ++i) {
                  if (schemas[i].validate(validateOptions)) {
                     validSchemas.add(schemas[i]);
                  }
               }
            } else {
               validSchemas.addAll(Arrays.asList(schemas));
            }

            SchemaDocument.Schema[] startWith = (SchemaDocument.Schema[])((SchemaDocument.Schema[])validSchemas.toArray(new SchemaDocument.Schema[validSchemas.size()]));
            if (incremental) {
               Set namespaces = new HashSet();
               startWith = getSchemasToRecompile((SchemaTypeSystemImpl)system, startWith, namespaces);
               state.initFromTypeSystem((SchemaTypeSystemImpl)system, namespaces);
            } else {
               state.setDependencies(new SchemaDependencies());
            }

            StscImporter.SchemaToProcess[] schemasAndChameleons = StscImporter.resolveImportsAndIncludes(startWith, incremental);
            StscTranslator.addAllDefinitions(schemasAndChameleons);
            StscResolver.resolveAll();
            StscChecker.checkAll();
            StscJavaizer.javaizeAllTypes(javaize);
            StscState.get().sts().loadFromStscState(state);
            if (sourcesToCopyMap != null) {
               sourcesToCopyMap.putAll(state.sourceCopyMap());
            }

            SchemaTypeSystemImpl var18;
            if (errorWatcher.hasError()) {
               if (!state.allowPartial() || state.getRecovered() != errorWatcher.size()) {
                  var18 = null;
                  return var18;
               }

               StscState.get().sts().setIncomplete(true);
            }

            if (system != null) {
               ((SchemaTypeSystemImpl)system).setIncomplete(true);
            }

            var18 = StscState.get().sts();
            return var18;
         } finally {
            StscState.end();
         }
      }
   }

   private static SchemaDocument.Schema[] getSchemasToRecompile(SchemaTypeSystemImpl system, SchemaDocument.Schema[] modified, Set namespaces) {
      Set modifiedFiles = new HashSet();
      Map haveFile = new HashMap();
      List result = new ArrayList();

      for(int i = 0; i < modified.length; ++i) {
         String fileURL = modified[i].documentProperties().getSourceName();
         if (fileURL == null) {
            throw new IllegalArgumentException("One of the Schema files passed in doesn't have the source set, which prevents it to be incrementally compiled");
         }

         modifiedFiles.add(fileURL);
         haveFile.put(fileURL, modified[i]);
         result.add(modified[i]);
      }

      SchemaDependencies dep = system.getDependencies();
      List nss = dep.getNamespacesTouched(modifiedFiles);
      namespaces.addAll(dep.computeTransitiveClosure(nss));
      List needRecompilation = dep.getFilesTouched(namespaces);
      StscState.get().setDependencies(new SchemaDependencies(dep, namespaces));

      for(int i = 0; i < needRecompilation.size(); ++i) {
         String url = (String)needRecompilation.get(i);
         SchemaDocument.Schema have = (SchemaDocument.Schema)haveFile.get(url);
         if (have == null) {
            try {
               XmlObject xdoc = StscImporter.DownloadTable.downloadDocument(StscState.get().getS4SLoader(), (String)null, url);
               XmlOptions voptions = new XmlOptions();
               voptions.setErrorListener(StscState.get().getErrorListener());
               if (xdoc instanceof SchemaDocument && xdoc.validate(voptions)) {
                  SchemaDocument sDoc = (SchemaDocument)xdoc;
                  result.add(sDoc.getSchema());
               } else {
                  StscState.get().error("Referenced document is not a valid schema, URL = " + url, 56, (XmlObject)null);
               }
            } catch (MalformedURLException var15) {
               StscState.get().error("exception.loading.url", new Object[]{"MalformedURLException", url, var15.getMessage()}, (XmlObject)null);
            } catch (IOException var16) {
               StscState.get().error("exception.loading.url", new Object[]{"IOException", url, var16.getMessage()}, (XmlObject)null);
            } catch (XmlException var17) {
               StscState.get().error("exception.loading.url", new Object[]{"XmlException", url, var17.getMessage()}, (XmlObject)null);
            }
         }
      }

      return (SchemaDocument.Schema[])((SchemaDocument.Schema[])result.toArray(new SchemaDocument.Schema[result.size()]));
   }

   public static boolean generateTypes(SchemaTypeSystem system, Filer filer, XmlOptions options) {
      if (system instanceof SchemaTypeSystemImpl && ((SchemaTypeSystemImpl)system).isIncomplete()) {
         return false;
      } else {
         boolean success = true;
         List types = new ArrayList();
         types.addAll(Arrays.asList(system.globalTypes()));
         types.addAll(Arrays.asList(system.documentTypes()));
         types.addAll(Arrays.asList(system.attributeTypes()));
         Iterator i = types.iterator();

         while(i.hasNext()) {
            SchemaType type = (SchemaType)i.next();
            if (!type.isBuiltinType() && type.getFullJavaName() != null) {
               String fjn = type.getFullJavaName();
               Writer writer = null;

               try {
                  writer = filer.createSourceFile(fjn);
                  SchemaTypeCodePrinter.printType(writer, type, options);
               } catch (IOException var36) {
                  System.err.println("IO Error " + var36);
                  success = false;
               } finally {
                  try {
                     if (writer != null) {
                        writer.close();
                     }
                  } catch (IOException var33) {
                  }

               }

               try {
                  fjn = type.getFullJavaImplName();
                  writer = filer.createSourceFile(fjn);
                  SchemaTypeCodePrinter.printTypeImpl(writer, type, options);
               } catch (IOException var34) {
                  System.err.println("IO Error " + var34);
                  success = false;
               } finally {
                  try {
                     if (writer != null) {
                        writer.close();
                     }
                  } catch (IOException var32) {
                  }

               }
            }
         }

         return success;
      }
   }

   public static class Parameters {
      private SchemaTypeSystem existingSystem;
      private String name;
      private SchemaDocument.Schema[] schemas;
      private BindingConfig config;
      private SchemaTypeLoader linkTo;
      private XmlOptions options;
      private Collection errorListener;
      private boolean javaize;
      private URI baseURI;
      private Map sourcesToCopyMap;
      private File schemasDir;

      public SchemaTypeSystem getExistingTypeSystem() {
         return this.existingSystem;
      }

      public void setExistingTypeSystem(SchemaTypeSystem system) {
         this.existingSystem = system;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public SchemaDocument.Schema[] getSchemas() {
         return this.schemas;
      }

      public void setSchemas(SchemaDocument.Schema[] schemas) {
         this.schemas = schemas;
      }

      public BindingConfig getConfig() {
         return this.config;
      }

      public void setConfig(BindingConfig config) {
         this.config = config;
      }

      public SchemaTypeLoader getLinkTo() {
         return this.linkTo;
      }

      public void setLinkTo(SchemaTypeLoader linkTo) {
         this.linkTo = linkTo;
      }

      public XmlOptions getOptions() {
         return this.options;
      }

      public void setOptions(XmlOptions options) {
         this.options = options;
      }

      public Collection getErrorListener() {
         return this.errorListener;
      }

      public void setErrorListener(Collection errorListener) {
         this.errorListener = errorListener;
      }

      public boolean isJavaize() {
         return this.javaize;
      }

      public void setJavaize(boolean javaize) {
         this.javaize = javaize;
      }

      public URI getBaseURI() {
         return this.baseURI;
      }

      public void setBaseURI(URI baseURI) {
         this.baseURI = baseURI;
      }

      public Map getSourcesToCopyMap() {
         return this.sourcesToCopyMap;
      }

      public void setSourcesToCopyMap(Map sourcesToCopyMap) {
         this.sourcesToCopyMap = sourcesToCopyMap;
      }

      public File getSchemasDir() {
         return this.schemasDir;
      }

      public void setSchemasDir(File schemasDir) {
         this.schemasDir = schemasDir;
      }
   }
}

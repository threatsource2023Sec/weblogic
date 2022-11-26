package com.bea.xbean.tool;

import com.bea.xbean.common.IOUtil;
import com.bea.xbean.common.JarHelper;
import com.bea.xbean.common.ResolverUtil;
import com.bea.xbean.common.XmlErrorPrinter;
import com.bea.xbean.common.XmlErrorWatcher;
import com.bea.xbean.config.BindingConfigImpl;
import com.bea.xbean.schema.PathResourceLoader;
import com.bea.xbean.schema.SchemaTypeLoaderImpl;
import com.bea.xbean.schema.SchemaTypeSystemCompiler;
import com.bea.xbean.schema.SchemaTypeSystemImpl;
import com.bea.xbean.schema.StscState;
import com.bea.xbean.util.FilerImpl;
import com.bea.xbean.values.XmlListImpl;
import com.bea.xbean.xb.substwsdl.DefinitionsDocument;
import com.bea.xbean.xb.xmlconfig.ConfigDocument;
import com.bea.xbean.xb.xmlconfig.Extensionconfig;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.ResourceLoader;
import com.bea.xml.SchemaCodePrinter;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.SimpleValue;
import com.bea.xml.SystemProperties;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.xml.sax.EntityResolver;
import repackage.Repackager;

public class SchemaCompiler {
   private static final String CONFIG_URI = "http://xml.apache.org/xmlbeans/2004/02/xbean/config";
   private static final String COMPATIBILITY_CONFIG_URI = "http://www.bea.com/2002/09/xbean/config";
   private static final Map MAP_COMPATIBILITY_CONFIG_URIS = new HashMap();

   public static void printUsage() {
      System.out.println("Compiles a schema into XML Bean classes and metadata.");
      System.out.println("Usage: scomp [opts] [dirs]* [schema.xsd]* [service.wsdl]* [config.xsdconfig]*");
      System.out.println("Options include:");
      System.out.println("    -cp [a;b;c] - classpath");
      System.out.println("    -d [dir] - target binary directory for .class and .xsb files");
      System.out.println("    -src [dir] - target directory for generated .java files");
      System.out.println("    -srconly - do not compile .java files or jar the output.");
      System.out.println("    -out [xmltypes.jar] - the name of the output jar");
      System.out.println("    -dl - permit network downloads for imports and includes (default is off)");
      System.out.println("    -noupa - do not enforce the unique particle attribution rule");
      System.out.println("    -nopvr - do not enforce the particle valid (restriction) rule");
      System.out.println("    -noann - ignore annotations");
      System.out.println("    -novdoc - do not validate contents of <documentation>");
      System.out.println("    -noext - ignore all extension (Pre/Post and Interface) found in .xsdconfig files");
      System.out.println("    -compiler - path to external java compiler");
      System.out.println("    -javasource [version] - generate java source compatible for a Java version (1.4 or 1.5)");
      System.out.println("    -ms - initial memory for external java compiler (default '" + CodeGenUtil.DEFAULT_MEM_START + "')");
      System.out.println("    -mx - maximum memory for external java compiler (default '" + CodeGenUtil.DEFAULT_MEM_MAX + "')");
      System.out.println("    -debug - compile with debug symbols");
      System.out.println("    -quiet - print fewer informational messages");
      System.out.println("    -verbose - print more informational messages");
      System.out.println("    -version - prints version information");
      System.out.println("    -license - prints license information");
      System.out.println("    -allowmdef \"[ns] [ns] [ns]\" - ignores multiple defs in given namespaces (use ##local for no-namespace)");
      System.out.println("    -catalog [file] -  catalog file for org.apache.xml.resolver.tools.CatalogResolver. (Note: needs resolver.jar from http://xml.apache.org/commons/components/resolver/index.html)");
      System.out.println();
   }

   public static void main(String[] args) {
      if (args.length == 0) {
         printUsage();
         System.exit(0);
      } else {
         Set flags = new HashSet();
         flags.add("h");
         flags.add("help");
         flags.add("usage");
         flags.add("license");
         flags.add("quiet");
         flags.add("verbose");
         flags.add("version");
         flags.add("dl");
         flags.add("noupa");
         flags.add("nopvr");
         flags.add("noann");
         flags.add("novdoc");
         flags.add("noext");
         flags.add("srconly");
         flags.add("debug");
         Set opts = new HashSet();
         opts.add("out");
         opts.add("name");
         opts.add("src");
         opts.add("d");
         opts.add("cp");
         opts.add("compiler");
         opts.add("javasource");
         opts.add("jar");
         opts.add("ms");
         opts.add("mx");
         opts.add("repackage");
         opts.add("schemaCodePrinter");
         opts.add("extension");
         opts.add("extensionParms");
         opts.add("allowmdef");
         opts.add("catalog");
         CommandLine cl = new CommandLine(args, flags, opts);
         if (cl.getOpt("h") == null && cl.getOpt("help") == null && cl.getOpt("usage") == null) {
            String[] badopts = cl.getBadOpts();
            if (badopts.length > 0) {
               for(int i = 0; i < badopts.length; ++i) {
                  System.out.println("Unrecognized option: " + badopts[i]);
               }

               printUsage();
               System.exit(0);
            } else if (cl.getOpt("license") != null) {
               CommandLine.printLicense();
               System.exit(0);
            } else if (cl.getOpt("version") != null) {
               CommandLine.printVersion();
               System.exit(0);
            } else {
               args = cl.args();
               boolean verbose = cl.getOpt("verbose") != null;
               boolean quiet = cl.getOpt("quiet") != null;
               if (verbose) {
                  quiet = false;
               }

               if (verbose) {
                  CommandLine.printVersion();
               }

               String outputfilename = cl.getOpt("out");
               String repackage = cl.getOpt("repackage");
               String codePrinterClass = cl.getOpt("schemaCodePrinter");
               SchemaCodePrinter codePrinter = null;
               if (codePrinterClass != null) {
                  try {
                     codePrinter = (SchemaCodePrinter)Class.forName(codePrinterClass).newInstance();
                  } catch (Exception var49) {
                     System.err.println("Failed to load SchemaCodePrinter class " + codePrinterClass + "; proceeding with default printer");
                  }
               }

               String name = cl.getOpt("name");
               boolean download = cl.getOpt("dl") != null;
               boolean noUpa = cl.getOpt("noupa") != null;
               boolean noPvr = cl.getOpt("nopvr") != null;
               boolean noAnn = cl.getOpt("noann") != null;
               boolean noVDoc = cl.getOpt("novdoc") != null;
               boolean noExt = cl.getOpt("noext") != null;
               boolean nojavac = cl.getOpt("srconly") != null;
               boolean debug = cl.getOpt("debug") != null;
               String allowmdef = cl.getOpt("allowmdef");
               Set mdefNamespaces = allowmdef == null ? Collections.EMPTY_SET : new HashSet(Arrays.asList(XmlListImpl.split_list(allowmdef)));
               List extensions = new ArrayList();
               Extension e;
               if (cl.getOpt("extension") != null) {
                  try {
                     e = new Extension();
                     e.setClassName(Class.forName(cl.getOpt("extension"), false, Thread.currentThread().getContextClassLoader()));
                     extensions.add(e);
                  } catch (ClassNotFoundException var48) {
                     System.err.println("Could not find extension class: " + cl.getOpt("extension") + "  Is it on your classpath?");
                     System.exit(1);
                  }
               }

               String srcdir;
               Extension.Param classpath;
               if (extensions.size() > 0 && cl.getOpt("extensionParms") != null) {
                  e = (Extension)extensions.get(0);
                  StringTokenizer parmTokens = new StringTokenizer(cl.getOpt("extensionParms"), ";");

                  while(parmTokens.hasMoreTokens()) {
                     srcdir = parmTokens.nextToken();
                     int index = srcdir.indexOf(61);
                     if (index < 0) {
                        System.err.println("extensionParms should be name=value;name=value");
                        System.exit(1);
                     }

                     String n = srcdir.substring(0, index);
                     String v = srcdir.substring(index + 1);
                     classpath = e.createParam();
                     classpath.setName(n);
                     classpath.setValue(v);
                  }
               }

               String classesdir = cl.getOpt("d");
               File classes = null;
               if (classesdir != null) {
                  classes = new File(classesdir);
               }

               srcdir = cl.getOpt("src");
               File src = null;
               if (srcdir != null) {
                  src = new File(srcdir);
               }

               if (nojavac && srcdir == null && classes != null) {
                  src = classes;
               }

               File tempdir = null;
               if (src == null || classes == null) {
                  try {
                     tempdir = SchemaCodeGenerator.createTempDir();
                  } catch (IOException var47) {
                     System.err.println("Error creating temp dir " + var47);
                     System.exit(1);
                  }
               }

               File jarfile = null;
               if (outputfilename == null && classes == null && !nojavac) {
                  outputfilename = "xmltypes.jar";
               }

               if (outputfilename != null) {
                  jarfile = new File(outputfilename);
               }

               if (src == null) {
                  src = IOUtil.createDir(tempdir, "src");
               }

               if (classes == null) {
                  classes = IOUtil.createDir(tempdir, "classes");
               }

               classpath = null;
               String cpString = cl.getOpt("cp");
               File[] classpath;
               if (cpString != null) {
                  String[] cpparts = cpString.split(File.pathSeparator);
                  List cpList = new ArrayList();

                  for(int i = 0; i < cpparts.length; ++i) {
                     cpList.add(new File(cpparts[i]));
                  }

                  classpath = (File[])((File[])cpList.toArray(new File[cpList.size()]));
               } else {
                  classpath = CodeGenUtil.systemClasspath();
               }

               String javasource = cl.getOpt("javasource");
               String compiler = cl.getOpt("compiler");
               String jar = cl.getOpt("jar");
               if (verbose && jar != null) {
                  System.out.println("The 'jar' option is no longer supported.");
               }

               String memoryInitialSize = cl.getOpt("ms");
               String memoryMaximumSize = cl.getOpt("mx");
               File[] xsdFiles = cl.filesEndingWith(".xsd");
               File[] wsdlFiles = cl.filesEndingWith(".wsdl");
               File[] javaFiles = cl.filesEndingWith(".java");
               File[] configFiles = cl.filesEndingWith(".xsdconfig");
               URL[] urlFiles = cl.getURLs();
               if (xsdFiles.length + wsdlFiles.length + urlFiles.length == 0) {
                  System.out.println("Could not find any xsd or wsdl files to process.");
                  System.exit(0);
               }

               File baseDir = cl.getBaseDir();
               URI baseURI = baseDir == null ? null : baseDir.toURI();
               XmlErrorPrinter err = new XmlErrorPrinter(verbose, baseURI);
               String catString = cl.getOpt("catalog");
               Parameters params = new Parameters();
               params.setBaseDir(baseDir);
               params.setXsdFiles(xsdFiles);
               params.setWsdlFiles(wsdlFiles);
               params.setJavaFiles(javaFiles);
               params.setConfigFiles(configFiles);
               params.setUrlFiles(urlFiles);
               params.setClasspath(classpath);
               params.setOutputJar(jarfile);
               params.setName(name);
               params.setSrcDir(src);
               params.setClassesDir(classes);
               params.setCompiler(compiler);
               params.setJavaSource(javasource);
               params.setMemoryInitialSize(memoryInitialSize);
               params.setMemoryMaximumSize(memoryMaximumSize);
               params.setNojavac(nojavac);
               params.setQuiet(quiet);
               params.setVerbose(verbose);
               params.setDownload(download);
               params.setNoUpa(noUpa);
               params.setNoPvr(noPvr);
               params.setNoAnn(noAnn);
               params.setNoVDoc(noVDoc);
               params.setNoExt(noExt);
               params.setDebug(debug);
               params.setErrorListener(err);
               params.setRepackage(repackage);
               params.setExtensions(extensions);
               params.setMdefNamespaces((Set)mdefNamespaces);
               params.setCatalogFile(catString);
               params.setSchemaCodePrinter(codePrinter);
               boolean result = compile(params);
               if (tempdir != null) {
                  SchemaCodeGenerator.tryHardToDelete(tempdir);
               }

               if (!result) {
                  System.exit(1);
               }

               System.exit(0);
            }
         } else {
            printUsage();
            System.exit(0);
         }
      }
   }

   private static SchemaTypeSystem loadTypeSystem(String name, File[] xsdFiles, File[] wsdlFiles, URL[] urlFiles, File[] configFiles, File[] javaFiles, ResourceLoader cpResourceLoader, boolean download, boolean noUpa, boolean noPvr, boolean noAnn, boolean noVDoc, boolean noExt, Set mdefNamespaces, File baseDir, Map sourcesToCopyMap, Collection outerErrorListener, File schemasDir, EntityResolver entResolver, File[] classpath, String javasource) {
      XmlErrorWatcher errorListener = new XmlErrorWatcher(outerErrorListener);
      StscState state = StscState.start();
      state.setErrorListener(errorListener);
      SchemaTypeLoader loader = XmlBeans.typeLoaderForClassLoader(SchemaDocument.class.getClassLoader());
      ArrayList scontentlist = new ArrayList();
      int i;
      XmlOptions options;
      XmlObject urldoc;
      if (xsdFiles != null) {
         for(i = 0; i < xsdFiles.length; ++i) {
            try {
               options = new XmlOptions();
               options.setLoadLineNumbers();
               options.setLoadMessageDigest();
               options.setEntityResolver(entResolver);
               urldoc = loader.parse((File)xsdFiles[i], (SchemaType)null, options);
               if (!(urldoc instanceof SchemaDocument)) {
                  StscState.addError(errorListener, "invalid.document.type", new Object[]{xsdFiles[i], "schema"}, (XmlObject)urldoc);
               } else {
                  addSchema(xsdFiles[i].toString(), (SchemaDocument)urldoc, errorListener, noVDoc, scontentlist);
               }
            } catch (XmlException var38) {
               errorListener.add(var38.getError());
            } catch (Exception var39) {
               StscState.addError(errorListener, "cannot.load.file", new Object[]{"xsd", xsdFiles[i], var39.getMessage()}, (File)xsdFiles[i]);
            }
         }
      }

      if (wsdlFiles != null) {
         for(i = 0; i < wsdlFiles.length; ++i) {
            try {
               options = new XmlOptions();
               options.setLoadLineNumbers();
               options.setLoadSubstituteNamespaces(Collections.singletonMap("http://schemas.xmlsoap.org/wsdl/", "http://www.apache.org/internal/xmlbeans/wsdlsubst"));
               options.setEntityResolver(entResolver);
               urldoc = loader.parse((File)wsdlFiles[i], (SchemaType)null, options);
               if (!(urldoc instanceof DefinitionsDocument)) {
                  StscState.addError(errorListener, "invalid.document.type", new Object[]{wsdlFiles[i], "wsdl"}, (XmlObject)urldoc);
               } else {
                  addWsdlSchemas(wsdlFiles[i].toString(), (DefinitionsDocument)urldoc, errorListener, noVDoc, scontentlist);
               }
            } catch (XmlException var36) {
               errorListener.add(var36.getError());
            } catch (Exception var37) {
               StscState.addError(errorListener, "cannot.load.file", new Object[]{"wsdl", wsdlFiles[i], var37.getMessage()}, (File)wsdlFiles[i]);
            }
         }
      }

      if (urlFiles != null) {
         for(i = 0; i < urlFiles.length; ++i) {
            try {
               options = new XmlOptions();
               options.setLoadLineNumbers();
               options.setLoadSubstituteNamespaces(Collections.singletonMap("http://schemas.xmlsoap.org/wsdl/", "http://www.apache.org/internal/xmlbeans/wsdlsubst"));
               options.setEntityResolver(entResolver);
               urldoc = loader.parse((URL)urlFiles[i], (SchemaType)null, options);
               if (urldoc instanceof DefinitionsDocument) {
                  addWsdlSchemas(urlFiles[i].toString(), (DefinitionsDocument)urldoc, errorListener, noVDoc, scontentlist);
               } else if (urldoc instanceof SchemaDocument) {
                  addSchema(urlFiles[i].toString(), (SchemaDocument)urldoc, errorListener, noVDoc, scontentlist);
               } else {
                  StscState.addError(errorListener, "invalid.document.type", new Object[]{urlFiles[i], "wsdl or schema"}, (XmlObject)urldoc);
               }
            } catch (XmlException var34) {
               errorListener.add(var34.getError());
            } catch (Exception var35) {
               StscState.addError(errorListener, "cannot.load.file", new Object[]{"url", urlFiles[i], var35.getMessage()}, (URL)urlFiles[i]);
            }
         }
      }

      SchemaDocument.Schema[] sdocs = (SchemaDocument.Schema[])((SchemaDocument.Schema[])scontentlist.toArray(new SchemaDocument.Schema[scontentlist.size()]));
      ArrayList cdoclist = new ArrayList();
      if (configFiles != null) {
         if (noExt) {
            System.out.println("Pre/Post and Interface extensions will be ignored.");
         }

         for(int i = 0; i < configFiles.length; ++i) {
            try {
               XmlOptions options = new XmlOptions();
               options.put("LOAD_LINE_NUMBERS");
               options.setEntityResolver(entResolver);
               options.setLoadSubstituteNamespaces(MAP_COMPATIBILITY_CONFIG_URIS);
               XmlObject configdoc = loader.parse((File)configFiles[i], (SchemaType)null, options);
               if (!(configdoc instanceof ConfigDocument)) {
                  StscState.addError(errorListener, "invalid.document.type", new Object[]{configFiles[i], "xsd config"}, (XmlObject)configdoc);
               } else {
                  StscState.addInfo(errorListener, "Loading config file " + configFiles[i]);
                  if (configdoc.validate((new XmlOptions()).setErrorListener(errorListener))) {
                     ConfigDocument.Config config = ((ConfigDocument)configdoc).getConfig();
                     cdoclist.add(config);
                     if (noExt) {
                        config.setExtensionArray(new Extensionconfig[0]);
                     }
                  }
               }
            } catch (XmlException var32) {
               errorListener.add(var32.getError());
            } catch (Exception var33) {
               StscState.addError(errorListener, "cannot.load.file", new Object[]{"xsd config", configFiles[i], var33.getMessage()}, (File)configFiles[i]);
            }
         }
      }

      ConfigDocument.Config[] cdocs = (ConfigDocument.Config[])((ConfigDocument.Config[])cdoclist.toArray(new ConfigDocument.Config[cdoclist.size()]));
      SchemaTypeLoader linkTo = SchemaTypeLoaderImpl.build((SchemaTypeLoader[])null, cpResourceLoader, (ClassLoader)null);
      URI baseURI = null;
      if (baseDir != null) {
         baseURI = baseDir.toURI();
      }

      XmlOptions opts = new XmlOptions();
      if (download) {
         opts.setCompileDownloadUrls();
      }

      if (noUpa) {
         opts.setCompileNoUpaRule();
      }

      if (noPvr) {
         opts.setCompileNoPvrRule();
      }

      if (noAnn) {
         opts.setCompileNoAnnotations();
      }

      if (mdefNamespaces != null) {
         opts.setCompileMdefNamespaces(mdefNamespaces);
      }

      opts.setCompileNoValidation();
      opts.setEntityResolver(entResolver);
      if (javasource != null) {
         opts.setGenerateJavaVersion(javasource);
      }

      SchemaTypeSystemCompiler.Parameters params = new SchemaTypeSystemCompiler.Parameters();
      params.setName(name);
      params.setSchemas(sdocs);
      params.setConfig(BindingConfigImpl.forConfigDocuments(cdocs, javaFiles, classpath));
      params.setLinkTo(linkTo);
      params.setOptions(opts);
      params.setErrorListener(errorListener);
      params.setJavaize(true);
      params.setBaseURI(baseURI);
      params.setSourcesToCopyMap(sourcesToCopyMap);
      params.setSchemasDir(schemasDir);
      return SchemaTypeSystemCompiler.compile(params);
   }

   private static void addSchema(String name, SchemaDocument schemadoc, XmlErrorWatcher errorListener, boolean noVDoc, List scontentlist) {
      StscState.addInfo(errorListener, "Loading schema file " + name);
      XmlOptions opts = (new XmlOptions()).setErrorListener(errorListener);
      if (noVDoc) {
         opts.setValidateTreatLaxAsSkip();
      }

      if (schemadoc.validate(opts)) {
         scontentlist.add(schemadoc.getSchema());
      }

   }

   private static void addWsdlSchemas(String name, DefinitionsDocument wsdldoc, XmlErrorWatcher errorListener, boolean noVDoc, List scontentlist) {
      if (wsdlContainsEncoded(wsdldoc)) {
         StscState.addWarning(errorListener, "The WSDL " + name + " uses SOAP encoding. SOAP encoding is not compatible with literal XML Schema.", 60, wsdldoc);
      }

      StscState.addInfo(errorListener, "Loading wsdl file " + name);
      XmlOptions opts = (new XmlOptions()).setErrorListener(errorListener);
      if (noVDoc) {
         opts.setValidateTreatLaxAsSkip();
      }

      XmlObject[] types = wsdldoc.getDefinitions().getTypesArray();
      int count = 0;

      for(int j = 0; j < types.length; ++j) {
         XmlObject[] schemas = types[j].selectPath("declare namespace xs=\"http://www.w3.org/2001/XMLSchema\" xs:schema");
         if (schemas.length == 0) {
            StscState.addWarning(errorListener, "The WSDL " + name + " did not have any schema documents in namespace 'http://www.w3.org/2001/XMLSchema'", 60, wsdldoc);
         } else {
            for(int k = 0; k < schemas.length; ++k) {
               if (schemas[k] instanceof SchemaDocument.Schema && schemas[k].validate(opts)) {
                  ++count;
                  scontentlist.add(schemas[k]);
               }
            }
         }
      }

      StscState.addInfo(errorListener, "Processing " + count + " schema(s) in " + name);
   }

   public static boolean compile(Parameters params) {
      File baseDir = params.getBaseDir();
      File[] xsdFiles = params.getXsdFiles();
      File[] wsdlFiles = params.getWsdlFiles();
      URL[] urlFiles = params.getUrlFiles();
      File[] javaFiles = params.getJavaFiles();
      File[] configFiles = params.getConfigFiles();
      File[] classpath = params.getClasspath();
      File outputJar = params.getOutputJar();
      String name = params.getName();
      File srcDir = params.getSrcDir();
      File classesDir = params.getClassesDir();
      String compiler = params.getCompiler();
      String javasource = params.getJavaSource();
      String memoryInitialSize = params.getMemoryInitialSize();
      String memoryMaximumSize = params.getMemoryMaximumSize();
      boolean nojavac = params.isNojavac();
      boolean debug = params.isDebug();
      boolean verbose = params.isVerbose();
      boolean quiet = params.isQuiet();
      boolean download = params.isDownload();
      boolean noUpa = params.isNoUpa();
      boolean noPvr = params.isNoPvr();
      boolean noAnn = params.isNoAnn();
      boolean noVDoc = params.isNoVDoc();
      boolean noExt = params.isNoExt();
      boolean incrSrcGen = params.isIncrementalSrcGen();
      Collection outerErrorListener = params.getErrorListener();
      String repackage = params.getRepackage();
      if (repackage != null) {
         SchemaTypeLoaderImpl.METADATA_PACKAGE_LOAD = SchemaTypeSystemImpl.METADATA_PACKAGE_GEN;
         String stsPackage = SchemaTypeSystem.class.getPackage().getName();
         Repackager repackager = new Repackager(repackage);
         SchemaTypeSystemImpl.METADATA_PACKAGE_GEN = repackager.repackage(new StringBuffer(stsPackage)).toString().replace('.', '_');
         System.out.println("\n\n\n" + stsPackage + ".SchemaCompiler  Metadata LOAD:" + SchemaTypeLoaderImpl.METADATA_PACKAGE_LOAD + " GEN:" + SchemaTypeSystemImpl.METADATA_PACKAGE_GEN);
      }

      SchemaCodePrinter codePrinter = params.getSchemaCodePrinter();
      List extensions = params.getExtensions();
      Set mdefNamespaces = params.getMdefNamespaces();
      EntityResolver cmdLineEntRes = params.getEntityResolver() == null ? ResolverUtil.resolverForCatalog(params.getCatalogFile()) : params.getEntityResolver();
      if (srcDir != null && classesDir != null) {
         long start = System.currentTimeMillis();
         if (baseDir == null) {
            baseDir = new File(SystemProperties.getProperty("user.dir"));
         }

         ResourceLoader cpResourceLoader = null;
         Map sourcesToCopyMap = new HashMap();
         if (classpath != null) {
            cpResourceLoader = new PathResourceLoader(classpath);
         }

         boolean result = true;
         File schemasDir = IOUtil.createDir(classesDir, "schema" + SchemaTypeSystemImpl.METADATA_PACKAGE_GEN + "/src");
         XmlErrorWatcher errorListener = new XmlErrorWatcher(outerErrorListener);
         SchemaTypeSystem system = loadTypeSystem(name, xsdFiles, wsdlFiles, urlFiles, configFiles, javaFiles, cpResourceLoader, download, noUpa, noPvr, noAnn, noVDoc, noExt, mdefNamespaces, baseDir, sourcesToCopyMap, errorListener, schemasDir, cmdLineEntRes, classpath, javasource);
         if (errorListener.hasError()) {
            result = false;
         }

         long finish = System.currentTimeMillis();
         if (!quiet) {
            System.out.println("Time to build schema type system: " + (double)(finish - start) / 1000.0 + " seconds");
         }

         if (result && system != null) {
            start = System.currentTimeMillis();
            Repackager repackager = repackage == null ? null : new Repackager(repackage);
            FilerImpl filer = new FilerImpl(classesDir, srcDir, repackager, verbose, incrSrcGen);
            XmlOptions options = new XmlOptions();
            if (codePrinter != null) {
               options.setSchemaCodePrinter(codePrinter);
            }

            if (javasource != null) {
               options.setGenerateJavaVersion(javasource);
            }

            system.save(filer);
            result &= SchemaTypeSystemCompiler.generateTypes(system, filer, options);
            if (incrSrcGen) {
               SchemaCodeGenerator.deleteObsoleteFiles(srcDir, srcDir, new HashSet(filer.getSourceFiles()));
            }

            if (result) {
               finish = System.currentTimeMillis();
               if (!quiet) {
                  System.out.println("Time to generate code: " + (double)(finish - start) / 1000.0 + " seconds");
               }
            }

            if (result && !nojavac) {
               start = System.currentTimeMillis();
               List sourcefiles = filer.getSourceFiles();
               if (javaFiles != null) {
                  sourcefiles.addAll(Arrays.asList(javaFiles));
               }

               if (!CodeGenUtil.externalCompile(sourcefiles, classesDir, classpath, debug, compiler, javasource, memoryInitialSize, memoryMaximumSize, quiet, verbose)) {
                  result = false;
               }

               finish = System.currentTimeMillis();
               if (result && !params.isQuiet()) {
                  System.out.println("Time to compile code: " + (double)(finish - start) / 1000.0 + " seconds");
               }

               if (result && outputJar != null) {
                  try {
                     (new JarHelper()).jarDir(classesDir, outputJar);
                  } catch (IOException var48) {
                     System.err.println("IO Error " + var48);
                     result = false;
                  }

                  if (result && !params.isQuiet()) {
                     System.out.println("Compiled types to: " + outputJar);
                  }
               }
            }
         }

         if (!result && !quiet) {
            System.out.println("BUILD FAILED");
         } else {
            runExtensions(extensions, system, classesDir);
         }

         if (cpResourceLoader != null) {
            cpResourceLoader.close();
         }

         return result;
      } else {
         throw new IllegalArgumentException("src and class gen directories may not be null.");
      }
   }

   private static void runExtensions(List extensions, SchemaTypeSystem system, File classesDir) {
      if (extensions != null && extensions.size() > 0) {
         SchemaCompilerExtension sce = null;
         Iterator i = extensions.iterator();
         Map extensionParms = null;
         String classesDirName = null;

         try {
            classesDirName = classesDir.getCanonicalPath();
         } catch (IOException var10) {
            System.out.println("WARNING: Unable to get the path for schema jar file");
            classesDirName = classesDir.getAbsolutePath();
         }

         while(i.hasNext()) {
            Extension extension = (Extension)i.next();

            try {
               sce = (SchemaCompilerExtension)extension.getClassName().newInstance();
            } catch (InstantiationException var11) {
               System.out.println("UNABLE to instantiate schema compiler extension:" + extension.getClassName().getName());
               System.out.println("EXTENSION Class was not run");
               break;
            } catch (IllegalAccessException var12) {
               System.out.println("ILLEGAL ACCESS Exception when attempting to instantiate schema compiler extension: " + extension.getClassName().getName());
               System.out.println("EXTENSION Class was not run");
               break;
            }

            System.out.println("Running Extension: " + sce.getExtensionName());
            extensionParms = new HashMap();
            Iterator parmsi = extension.getParams().iterator();

            while(parmsi.hasNext()) {
               Extension.Param p = (Extension.Param)parmsi.next();
               extensionParms.put(p.getName(), p.getValue());
            }

            extensionParms.put("classesDir", classesDirName);
            sce.schemaCompilerExtension(system, extensionParms);
         }
      }

   }

   private static boolean wsdlContainsEncoded(XmlObject wsdldoc) {
      XmlObject[] useAttrs = wsdldoc.selectPath("declare namespace soap='http://schemas.xmlsoap.org/wsdl/soap/' .//soap:body/@use|.//soap:header/@use|.//soap:fault/@use");

      for(int i = 0; i < useAttrs.length; ++i) {
         if ("encoded".equals(((SimpleValue)useAttrs[i]).getStringValue())) {
            return true;
         }
      }

      return false;
   }

   static {
      MAP_COMPATIBILITY_CONFIG_URIS.put("http://www.bea.com/2002/09/xbean/config", "http://xml.apache.org/xmlbeans/2004/02/xbean/config");
   }

   public static class Parameters {
      private File baseDir;
      private File[] xsdFiles;
      private File[] wsdlFiles;
      private File[] javaFiles;
      private File[] configFiles;
      private URL[] urlFiles;
      private File[] classpath;
      private File outputJar;
      private String name;
      private File srcDir;
      private File classesDir;
      private String memoryInitialSize;
      private String memoryMaximumSize;
      private String compiler;
      private String javasource;
      private boolean nojavac;
      private boolean quiet;
      private boolean verbose;
      private boolean download;
      private Collection errorListener;
      private boolean noUpa;
      private boolean noPvr;
      private boolean noAnn;
      private boolean noVDoc;
      private boolean noExt;
      private boolean debug;
      private boolean incrementalSrcGen;
      private String repackage;
      private List extensions;
      private Set mdefNamespaces;
      private String catalogFile;
      private SchemaCodePrinter schemaCodePrinter;
      private EntityResolver entityResolver;

      public Parameters() {
         this.extensions = Collections.EMPTY_LIST;
         this.mdefNamespaces = Collections.EMPTY_SET;
      }

      public File getBaseDir() {
         return this.baseDir;
      }

      public void setBaseDir(File baseDir) {
         this.baseDir = baseDir;
      }

      public File[] getXsdFiles() {
         return this.xsdFiles;
      }

      public void setXsdFiles(File[] xsdFiles) {
         this.xsdFiles = xsdFiles;
      }

      public File[] getWsdlFiles() {
         return this.wsdlFiles;
      }

      public void setWsdlFiles(File[] wsdlFiles) {
         this.wsdlFiles = wsdlFiles;
      }

      public File[] getJavaFiles() {
         return this.javaFiles;
      }

      public void setJavaFiles(File[] javaFiles) {
         this.javaFiles = javaFiles;
      }

      public File[] getConfigFiles() {
         return this.configFiles;
      }

      public void setConfigFiles(File[] configFiles) {
         this.configFiles = configFiles;
      }

      public URL[] getUrlFiles() {
         return this.urlFiles;
      }

      public void setUrlFiles(URL[] urlFiles) {
         this.urlFiles = urlFiles;
      }

      public File[] getClasspath() {
         return this.classpath;
      }

      public void setClasspath(File[] classpath) {
         this.classpath = classpath;
      }

      public File getOutputJar() {
         return this.outputJar;
      }

      public void setOutputJar(File outputJar) {
         this.outputJar = outputJar;
      }

      public String getName() {
         return this.name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public File getSrcDir() {
         return this.srcDir;
      }

      public void setSrcDir(File srcDir) {
         this.srcDir = srcDir;
      }

      public File getClassesDir() {
         return this.classesDir;
      }

      public void setClassesDir(File classesDir) {
         this.classesDir = classesDir;
      }

      public boolean isNojavac() {
         return this.nojavac;
      }

      public void setNojavac(boolean nojavac) {
         this.nojavac = nojavac;
      }

      public boolean isQuiet() {
         return this.quiet;
      }

      public void setQuiet(boolean quiet) {
         this.quiet = quiet;
      }

      public boolean isVerbose() {
         return this.verbose;
      }

      public void setVerbose(boolean verbose) {
         this.verbose = verbose;
      }

      public boolean isDownload() {
         return this.download;
      }

      public void setDownload(boolean download) {
         this.download = download;
      }

      public boolean isNoUpa() {
         return this.noUpa;
      }

      public void setNoUpa(boolean noUpa) {
         this.noUpa = noUpa;
      }

      public boolean isNoPvr() {
         return this.noPvr;
      }

      public void setNoPvr(boolean noPvr) {
         this.noPvr = noPvr;
      }

      public boolean isNoAnn() {
         return this.noAnn;
      }

      public void setNoAnn(boolean noAnn) {
         this.noAnn = noAnn;
      }

      public boolean isNoVDoc() {
         return this.noVDoc;
      }

      public void setNoVDoc(boolean newNoVDoc) {
         this.noVDoc = newNoVDoc;
      }

      public boolean isNoExt() {
         return this.noExt;
      }

      public void setNoExt(boolean newNoExt) {
         this.noExt = newNoExt;
      }

      public boolean isIncrementalSrcGen() {
         return this.incrementalSrcGen;
      }

      public void setIncrementalSrcGen(boolean incrSrcGen) {
         this.incrementalSrcGen = incrSrcGen;
      }

      public boolean isDebug() {
         return this.debug;
      }

      public void setDebug(boolean debug) {
         this.debug = debug;
      }

      public String getMemoryInitialSize() {
         return this.memoryInitialSize;
      }

      public void setMemoryInitialSize(String memoryInitialSize) {
         this.memoryInitialSize = memoryInitialSize;
      }

      public String getMemoryMaximumSize() {
         return this.memoryMaximumSize;
      }

      public void setMemoryMaximumSize(String memoryMaximumSize) {
         this.memoryMaximumSize = memoryMaximumSize;
      }

      public String getCompiler() {
         return this.compiler;
      }

      public void setCompiler(String compiler) {
         this.compiler = compiler;
      }

      public String getJavaSource() {
         return this.javasource;
      }

      public void setJavaSource(String javasource) {
         this.javasource = javasource;
      }

      /** @deprecated */
      public String getJar() {
         return null;
      }

      /** @deprecated */
      public void setJar(String jar) {
      }

      public Collection getErrorListener() {
         return this.errorListener;
      }

      public void setErrorListener(Collection errorListener) {
         this.errorListener = errorListener;
      }

      public String getRepackage() {
         return this.repackage;
      }

      public void setRepackage(String newRepackage) {
         this.repackage = newRepackage;
      }

      public List getExtensions() {
         return this.extensions;
      }

      public void setExtensions(List extensions) {
         this.extensions = extensions;
      }

      public Set getMdefNamespaces() {
         return this.mdefNamespaces;
      }

      public void setMdefNamespaces(Set mdefNamespaces) {
         this.mdefNamespaces = mdefNamespaces;
      }

      public String getCatalogFile() {
         return this.catalogFile;
      }

      public void setCatalogFile(String catalogPropFile) {
         this.catalogFile = catalogPropFile;
      }

      public SchemaCodePrinter getSchemaCodePrinter() {
         return this.schemaCodePrinter;
      }

      public void setSchemaCodePrinter(SchemaCodePrinter schemaCodePrinter) {
         this.schemaCodePrinter = schemaCodePrinter;
      }

      public EntityResolver getEntityResolver() {
         return this.entityResolver;
      }

      public void setEntityResolver(EntityResolver entityResolver) {
         this.entityResolver = entityResolver;
      }
   }
}

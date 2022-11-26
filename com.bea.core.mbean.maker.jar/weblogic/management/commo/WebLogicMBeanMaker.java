package weblogic.management.commo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.utils.FileUtils;
import weblogic.utils.Getopt2;
import weblogic.utils.JavaExec;
import weblogic.utils.StringUtils;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.compiler.CompilerInvoker;

public class WebLogicMBeanMaker {
   public static final String EXTENSIONS_XML_NAMESPACE = "http://xmlns.oracle.com/weblogic/security/extension";
   public static final String QNAME_COMPATIBILITY_PROP = "com.sun.xml.namespace.QName.useCompatibleSerialVersionUID";
   private static String DOC_XML_EXT = "-doc.xml";
   private static Pattern pattern = Pattern.compile(".*(<Lead>|<Detail>)(.*)(</Lead>|</Detail>)", 32);
   private static Transformer transformer;
   private static List mbeanTypeAttributesNotSupportedIn90;
   private static List mbeanAttributesNotSupportedIn90;
   private static List mbeanOperationsNotSupportedIn90;
   private static List mbeanOperationsArgNotSupportedIn90;
   static Getopt2 opts;
   boolean compileDone = false;
   boolean error = false;
   Node root = null;
   String mBeanName = null;
   String fileDir = ".\\temp";
   String mdf = null;
   String mdfDir;
   String mjf = null;
   String mbeantypesDir = null;
   String fileLoc = null;
   String compiler = null;
   String source = null;
   String target = null;
   boolean includeSource = false;
   boolean createStubs = false;
   boolean preserveStubs = true;
   boolean validating = false;
   boolean verbose = false;
   boolean helpRequested = false;
   boolean compileDebug = false;
   boolean noCompile;
   Vector attributes = new Vector();
   Vector operations = new Vector();
   Vector notifications = new Vector();
   Vector constructors = new Vector();
   Vector implementsSet = new Vector();
   Vector imports = new Vector();
   String classpath;
   HashMap attributeDescriptionMap = new HashMap();
   boolean isDocFileLoaded = false;
   String mdfPath = "";
   private static final String JAVAC_COMPILER_CLASS = "com.sun.tools.javac.Main";
   boolean doBeanGen = true;
   String externalDir;
   boolean internalWLSBuild;
   String targetNameSpace;
   String schemaLocation;
   boolean doCheckDescription;
   String jvmArgs;

   private static void populateNotSupportedList() {
      mbeanTypeAttributesNotSupportedIn90.add("CachingDisabled");
      mbeanTypeAttributesNotSupportedIn90.add("Classification");
      mbeanTypeAttributesNotSupportedIn90.add("CurrencyTimeLimit");
      mbeanTypeAttributesNotSupportedIn90.add("DisplayMessage");
      mbeanTypeAttributesNotSupportedIn90.add("Export");
      mbeanTypeAttributesNotSupportedIn90.add("GenerateExtendedAccessors");
      mbeanTypeAttributesNotSupportedIn90.add("InstanceExtent");
      mbeanTypeAttributesNotSupportedIn90.add("LanguageMap");
      mbeanTypeAttributesNotSupportedIn90.add("Listen");
      mbeanTypeAttributesNotSupportedIn90.add("Log");
      mbeanTypeAttributesNotSupportedIn90.add("LogFile");
      mbeanTypeAttributesNotSupportedIn90.add("MBeanClassName");
      mbeanTypeAttributesNotSupportedIn90.add("MessageID");
      mbeanTypeAttributesNotSupportedIn90.add("PersistLocation");
      mbeanTypeAttributesNotSupportedIn90.add("PersistName");
      mbeanTypeAttributesNotSupportedIn90.add("PersistPeriod");
      mbeanTypeAttributesNotSupportedIn90.add("Readable");
      mbeanTypeAttributesNotSupportedIn90.add("Servers");
      mbeanTypeAttributesNotSupportedIn90.add("VersionID");
      mbeanTypeAttributesNotSupportedIn90.add("Visibility");
      mbeanTypeAttributesNotSupportedIn90.add("Writeable");
      mbeanAttributesNotSupportedIn90.add("CachingDisabled");
      mbeanAttributesNotSupportedIn90.add("CurrencyTimeLimit");
      mbeanAttributesNotSupportedIn90.add("DefaultString");
      mbeanAttributesNotSupportedIn90.add("DisplayMessage");
      mbeanAttributesNotSupportedIn90.add("Export");
      mbeanAttributesNotSupportedIn90.add("GenerateExtendedAccessors");
      mbeanAttributesNotSupportedIn90.add("GetMethod");
      mbeanAttributesNotSupportedIn90.add("Iterable");
      mbeanAttributesNotSupportedIn90.add("LanguageMap");
      mbeanAttributesNotSupportedIn90.add("Listen");
      mbeanAttributesNotSupportedIn90.add("Log");
      mbeanAttributesNotSupportedIn90.add("LogFile");
      mbeanAttributesNotSupportedIn90.add("MessageID");
      mbeanAttributesNotSupportedIn90.add("NoDump");
      mbeanAttributesNotSupportedIn90.add("PersistentLocation");
      mbeanAttributesNotSupportedIn90.add("PersistentName");
      mbeanAttributesNotSupportedIn90.add("PersistPeriod");
      mbeanAttributesNotSupportedIn90.add("PersistPolicy");
      mbeanAttributesNotSupportedIn90.add("ProtocolMap");
      mbeanAttributesNotSupportedIn90.add("Readable");
      mbeanAttributesNotSupportedIn90.add("SetMethod");
      mbeanAttributesNotSupportedIn90.add("Visibility");
      mbeanOperationsNotSupportedIn90.add("CurrencyTimeLimit");
      mbeanOperationsNotSupportedIn90.add("DisplayMessage");
      mbeanOperationsNotSupportedIn90.add("DisplayName");
      mbeanOperationsNotSupportedIn90.add("Impact");
      mbeanOperationsNotSupportedIn90.add("LanguageMap");
      mbeanOperationsNotSupportedIn90.add("Listen");
      mbeanOperationsNotSupportedIn90.add("MessageID");
      mbeanOperationsNotSupportedIn90.add("PresentationString");
      mbeanOperationsNotSupportedIn90.add("ReturnTypeDescription");
      mbeanOperationsNotSupportedIn90.add("Visibility");
      mbeanOperationsArgNotSupportedIn90.add("InterfaceType");
   }

   private static void dumpUnsupportedWarnings(Node rootNode) {
      populateNotSupportedList();
      NamedNodeMap typeAttrmap = rootNode.getAttributes();

      for(int j = 0; j < typeAttrmap.getLength(); ++j) {
         Node node = typeAttrmap.item(j);
         String attrName = node.getNodeName();
         if (mbeanTypeAttributesNotSupportedIn90.contains(attrName)) {
            System.out.println("<Warning> The MDF attribute '" + attrName + "' is not supported in 9.0 and will be ignored. Please remove it from your MDF (.xml file)");
         }
      }

      NodeList nodelist = rootNode.getChildNodes();

      for(int k = 0; k < nodelist.getLength(); ++k) {
         Node node1 = nodelist.item(k);
         if (node1.getNodeType() == 1) {
            NamedNodeMap _namednodemap;
            int h;
            Node node;
            String attrName;
            if ("MBeanAttribute".equals(node1.getNodeName())) {
               _namednodemap = node1.getAttributes();

               for(h = 0; h < _namednodemap.getLength(); ++h) {
                  node = _namednodemap.item(h);
                  attrName = node.getNodeName();
                  if (mbeanAttributesNotSupportedIn90.contains(attrName)) {
                     System.out.println("<warning> The MDF attribute '" + attrName + "' is not supported in 9.0 and will be ignored. Please remove it from your MDF (.xml file)");
                  }
               }
            } else if ("MBeanOperation".equals(node1.getNodeName())) {
               _namednodemap = node1.getAttributes();

               for(h = 0; h < _namednodemap.getLength(); ++h) {
                  node = _namednodemap.item(h);
                  attrName = node.getNodeName();
                  if (mbeanOperationsNotSupportedIn90.contains(attrName)) {
                     System.out.println("<warning> The MDF attribute '" + attrName + "' is not supported in 9.0 and will be ignored. Please remove it from your MDF (.xml file)");
                  }
               }
            } else if ("MBeanOperationArg".equals(node1.getNodeName())) {
               _namednodemap = node1.getAttributes();

               for(h = 0; h < _namednodemap.getLength(); ++h) {
                  node = _namednodemap.item(h);
                  attrName = node.getNodeName();
                  if (mbeanOperationsArgNotSupportedIn90.contains(attrName)) {
                     System.out.println("<warning> The MDF attribute '" + attrName + "' is not supported in 9.0 and will be ignored. Please remove it from your MDF (.xml file)");
                  }
               }
            } else if ("MBeanNotification".equals(node1.getNodeName())) {
               System.out.println("<warning> The MDF attribute '" + node1.getNodeName() + "' and it contents are not supported in 9.0 and will be ignored. Please remove it from your MDF (.xml file)");
            }
         }
      }

   }

   private static void verifyProviderClassNameCollision(Node typeNode, Node attributeNode) {
      String customizerName = "";
      String name = "";
      String packageName = "";
      String provCN = "";
      boolean isProviderCN = false;
      NamedNodeMap _namednodemap = attributeNode.getAttributes();

      int k;
      Node node;
      for(k = 0; k < _namednodemap.getLength(); ++k) {
         node = _namednodemap.item(k);
         if (node.getNodeName().equals("Name") && node.getNodeValue().equals("ProviderClassName")) {
            isProviderCN = true;
         }
      }

      if (isProviderCN) {
         for(k = 0; k < _namednodemap.getLength(); ++k) {
            node = _namednodemap.item(k);
            if (node.getNodeName().equals("Default")) {
               provCN = node.getNodeValue();
               provCN = StringUtils.replaceGlobal(provCN, "\"", "");
               break;
            }
         }

         NamedNodeMap namednodemap = typeNode.getAttributes();

         for(int j = 0; j < namednodemap.getLength(); ++j) {
            Node node = namednodemap.item(j);
            if (node.getNodeName().equals("Name")) {
               name = node.getNodeValue();
            } else if (node.getNodeName().equals("Package")) {
               packageName = node.getNodeValue();
            }
         }
      }

      customizerName = packageName + "." + name + "Impl";
      if (provCN.equals(customizerName)) {
         throw new RuntimeException("ProviderClassName '" + provCN + "' specified in your MDF (.xml file) collides with the name of the customizer that will be generated by the WebLogicMBeanMaker. Please choose a different ProviderClassName and retry.");
      }
   }

   public WebLogicMBeanMaker() {
      this.attributeDescriptionMap = new HashMap();
      this.mdfPath = "";
      this.targetNameSpace = null;
      this.schemaLocation = null;
      this.jvmArgs = null;
   }

   private void displayUsage() {
      opts.usageAndExit("weblogic.management.commo.WebLogicMBeanMaker");
   }

   public static synchronized void main(String[] args) throws Exception {
      WebLogicMBeanMaker weblogicmbeanmaker = new WebLogicMBeanMaker();
      opts = new Getopt2();
      opts.setFailOnUnrecognizedOpts(true);
      opts.addOption("MDF", "MDF file name", "The MBean Description File (MDF).  This or MDFDIR required if MJF is not provided.");
      opts.addOption("MDFDIR", "MDF directory name", "A directory of MBean Description Files (MDFs).  This or MDF required if MJF is not provided.");
      opts.addOption("files", "Generated file directory", "The directory to contain the generated files. Required unless both MJF and MDF (or MDFDIR) are provided.");
      opts.addOption("MJF", "MJF (jar) file name", "The filename of the generated MJF (MBean jar file). Required if MDF or MDFDIR is not provided.");
      opts.addOption("compiler", "Compiler name", "The compiler to use for Java compilation.");
      opts.addOption("source", "Source version", "The source version for Java compilation.");
      opts.addOption("target", "Target version", "The target version for Java compilation.");
      opts.addOption("classpath", "Classpath", "The classpath to use for Java compilation");
      opts.addOption("mbeantypesDir", "jar library directory", "The location of a jar library containing existing provider jars");
      opts.addFlag("includeSource", "Should generated source files be included in the MJF.");
      opts.addFlag("createStubs", "Should stub files be generated for operations and custom attribute accessor methods.");
      opts.addFlag("doBeanGen", "Temp flag to disable bean gen.");
      opts.markPrivate("doBeanGen");
      opts.addOption("externaldir", "The location in which to generate a BeanFactory external timestamp (.tstamp) file.", "If not specified, the timestamp is hard-coded in the generated BeanFactory.java file");
      opts.addFlag("doCheckDescription", "Internal flag to print message when description text is not in a doc file.");
      opts.markPrivate("doCheckDescription");
      opts.addFlag("preserveStubs", "When generating stubs do not overwrite existing source files if they exist.");
      opts.addFlag("validateXML", "Should the XML processor do validation.");
      opts.addFlag("g", "Compile generated files with the debug option.");
      opts.addFlag("verbose", "Causes additonal output messages.");
      opts.addFlag("noCompile", "Supresses Compilation.");
      opts.addFlag("internalWLSBuild", "Internal flag, do not use this flag.");
      opts.markPrivate("internalWLSBuild");
      opts.addOption("targetNameSpace", "The default name space for the generated schemas", "Target Name space for the schema generated by the Maker. By default this is set to http://xmlns.oracle.com/weblogic/security/extension");
      opts.addOption("schemaLocation", "The location where generated schemas will be stored", "The schema location for generated schemas. Default is null");
      opts.addOption("jvmArgs", "JVM arguments", "The JVM arguments to pass to subprocesses. Arguments should be separated by spaces. Default is null");
      opts.addFlag("help", "Print out this message.");

      try {
         args = opts.grok(args).args();
      } catch (IllegalArgumentException var23) {
         System.out.println("\nError: " + var23.getMessage() + "\n");
         weblogicmbeanmaker.displayUsage();
      }

      weblogicmbeanmaker.mdf = opts.getOption("MDF");
      weblogicmbeanmaker.mdfDir = opts.getOption("MDFDIR");
      weblogicmbeanmaker.noCompile = opts.hasOption("noCompile");
      weblogicmbeanmaker.mjf = opts.getOption("MJF");
      weblogicmbeanmaker.mbeantypesDir = opts.getOption("mbeantypesDir");
      weblogicmbeanmaker.fileLoc = opts.getOption("files");
      weblogicmbeanmaker.compiler = opts.getOption("compiler");
      weblogicmbeanmaker.source = opts.getOption("source");
      weblogicmbeanmaker.target = opts.getOption("target");
      weblogicmbeanmaker.classpath = opts.getOption("classpath");
      weblogicmbeanmaker.includeSource = opts.hasOption("includeSource");
      weblogicmbeanmaker.createStubs = opts.hasOption("createStubs");
      weblogicmbeanmaker.doBeanGen = true;
      weblogicmbeanmaker.externalDir = opts.getOption("externaldir");
      weblogicmbeanmaker.preserveStubs = opts.hasOption("preserveStubs");
      weblogicmbeanmaker.validating = opts.hasOption("validateXML");
      weblogicmbeanmaker.compileDebug = opts.hasOption("g");
      weblogicmbeanmaker.verbose = opts.hasOption("verbose");
      weblogicmbeanmaker.doCheckDescription = opts.hasOption("doCheckDescription");
      weblogicmbeanmaker.helpRequested = opts.hasOption("help");
      weblogicmbeanmaker.internalWLSBuild = opts.hasOption("internalWLSBuild");
      weblogicmbeanmaker.targetNameSpace = opts.getOption("targetNameSpace");
      weblogicmbeanmaker.schemaLocation = opts.getOption("schemaLocation");
      weblogicmbeanmaker.jvmArgs = opts.getOption("jvmArgs");

      try {
         if (weblogicmbeanmaker.mdf == null) {
            weblogicmbeanmaker.mdf = System.getProperty("MDF");
         }

         if (!weblogicmbeanmaker.noCompile) {
            weblogicmbeanmaker.noCompile = Boolean.getBoolean("noCompile");
         }

         if (weblogicmbeanmaker.mdfDir == null) {
            weblogicmbeanmaker.mdfDir = System.getProperty("MDFDIR");
         }

         if (weblogicmbeanmaker.mjf == null) {
            weblogicmbeanmaker.mjf = System.getProperty("MJF");
         }

         if (weblogicmbeanmaker.mbeantypesDir == null) {
            weblogicmbeanmaker.mbeantypesDir = System.getProperty("mbeantypesDir");
         }

         if (weblogicmbeanmaker.compiler == null) {
            weblogicmbeanmaker.compiler = System.getProperty("compiler");
         }

         if (weblogicmbeanmaker.source == null) {
            weblogicmbeanmaker.source = System.getProperty("source");
         }

         if (weblogicmbeanmaker.target == null) {
            weblogicmbeanmaker.target = System.getProperty("target");
         }

         if (weblogicmbeanmaker.fileLoc == null) {
            weblogicmbeanmaker.fileLoc = System.getProperty("files");
         }

         if (!weblogicmbeanmaker.includeSource) {
            weblogicmbeanmaker.includeSource = Boolean.getBoolean("includeSource");
         }

         if (!weblogicmbeanmaker.createStubs) {
            weblogicmbeanmaker.createStubs = Boolean.getBoolean("createStubs");
         }

         if (!weblogicmbeanmaker.preserveStubs) {
            weblogicmbeanmaker.preserveStubs = Boolean.getBoolean("preserveStubs");
         }

         if (!weblogicmbeanmaker.validating) {
            weblogicmbeanmaker.validating = Boolean.getBoolean("validateXML");
         }

         if (!weblogicmbeanmaker.verbose) {
            weblogicmbeanmaker.verbose = Boolean.getBoolean("verbose");
         }

         if (!weblogicmbeanmaker.helpRequested) {
            weblogicmbeanmaker.helpRequested = Boolean.getBoolean("helpRequested");
         }

         if (!weblogicmbeanmaker.compileDebug) {
            weblogicmbeanmaker.compileDebug = Boolean.getBoolean("g");
         }

         if (weblogicmbeanmaker.targetNameSpace == null) {
            weblogicmbeanmaker.targetNameSpace = System.getProperty("targetNameSpace", "http://xmlns.oracle.com/weblogic/security/extension");
         }

         if (weblogicmbeanmaker.jvmArgs == null) {
            weblogicmbeanmaker.jvmArgs = System.getProperty("jvmArgs");
         }

         if (weblogicmbeanmaker.helpRequested) {
            weblogicmbeanmaker.displayUsage();
         }

         if (weblogicmbeanmaker.helpRequested) {
            weblogicmbeanmaker.displayUsage();
         }

         boolean flag = true;
         if (weblogicmbeanmaker.mdf == null && weblogicmbeanmaker.mdfDir == null && weblogicmbeanmaker.fileLoc == null && weblogicmbeanmaker.mjf == null) {
            flag = false;
         }

         if (weblogicmbeanmaker.mdf != null && weblogicmbeanmaker.mdfDir == null && weblogicmbeanmaker.fileLoc == null && weblogicmbeanmaker.mjf == null) {
            flag = false;
         }

         if (flag && weblogicmbeanmaker.mjf != null && weblogicmbeanmaker.fileLoc == null && weblogicmbeanmaker.mdf == null && weblogicmbeanmaker.mdfDir == null) {
            flag = false;
         }

         if (flag && weblogicmbeanmaker.fileLoc != null && weblogicmbeanmaker.mjf == null && weblogicmbeanmaker.mdf == null && weblogicmbeanmaker.mdfDir == null) {
            flag = false;
         }

         if (!flag) {
            System.out.println("\nAt least 2 of the following 3 options must be provided: 1)-MDF or MDFDIR 2) -files 3)-MJF\n");
            weblogicmbeanmaker.displayUsage();
         }

         if (weblogicmbeanmaker.mdf != null && weblogicmbeanmaker.mdfDir != null) {
            System.out.println("\nBoth MDF and MDFDIR cannot be provided in the same run.\n");
            weblogicmbeanmaker.displayUsage();
         }

         if (args.length > 0) {
            System.out.println("\nThere are no parameters to this command, only options.  You have specifed " + args.length + " parameters.\n");
            weblogicmbeanmaker.displayUsage();
         }

         if (weblogicmbeanmaker.mdf != null && weblogicmbeanmaker.mdfDir != null) {
            System.out.println("\nIt is illegal to specify both MDF and MDFDIR\n");
            weblogicmbeanmaker.displayUsage();
         }

         if (weblogicmbeanmaker.mdf != null && !(new File(weblogicmbeanmaker.mdf)).exists()) {
            System.out.println("\nThe specified MDF file, \"" + weblogicmbeanmaker.mdf + "\", does not exist.\n");
            System.exit(1);
         }

         if (weblogicmbeanmaker.mdfDir != null && !(new File(weblogicmbeanmaker.mdfDir)).exists()) {
            System.out.println("\nThe specified MDF directory, \"" + weblogicmbeanmaker.mdfDir + "\", does not exist.\n");
            System.exit(1);
         }

         if (weblogicmbeanmaker.externalDir != null && !(new File(weblogicmbeanmaker.externalDir)).exists()) {
            System.out.println("\nThe specified exteraldir, \"" + weblogicmbeanmaker.externalDir + "\", does not exist.\n");
            System.exit(1);
         }

         File file;
         if (weblogicmbeanmaker.mdf == null && weblogicmbeanmaker.mdfDir == null) {
            file = new File(weblogicmbeanmaker.fileLoc);
            if (!file.exists()) {
               System.out.println("\nThe specified input files directory, \"" + weblogicmbeanmaker.fileLoc + "\", does not exist.\n");
               System.exit(1);
            }

            if (!file.isDirectory()) {
               System.out.println("\nThe specified input files location, \"" + weblogicmbeanmaker.fileLoc + "\", is not a directory.\n");
               System.exit(1);
            }
         }

         if (weblogicmbeanmaker.fileLoc != null) {
            file = new File(weblogicmbeanmaker.fileLoc);
            if (!file.exists()) {
               file.mkdirs();
            }

            weblogicmbeanmaker.fileDir = file.getCanonicalPath();
         }

         File[] mdfs = null;
         if (weblogicmbeanmaker.mdf != null) {
            mdfs = new File[]{new File(weblogicmbeanmaker.mdf)};
         } else if (weblogicmbeanmaker.mdfDir != null) {
            mdfs = (new File(weblogicmbeanmaker.mdfDir)).listFiles(new XMLFileNameFilter());
         }

         Document document;
         if (mdfs != null) {
            for(int i = 0; i < mdfs.length; ++i) {
               if (!mdfs[i].getName().endsWith(DOC_XML_EXT)) {
                  File thisMDF = mdfs[i];
                  weblogicmbeanmaker.isDocFileLoaded = false;
                  weblogicmbeanmaker.attributes = new Vector();
                  weblogicmbeanmaker.operations = new Vector();
                  weblogicmbeanmaker.notifications = new Vector();
                  weblogicmbeanmaker.constructors = new Vector();
                  weblogicmbeanmaker.imports = new Vector();
                  weblogicmbeanmaker.implementsSet = new Vector();
                  DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
                  if (weblogicmbeanmaker.validating) {
                     documentbuilderfactory.setValidating(true);
                  }

                  DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();
                  MyErrorHandler myerrorhandler = weblogicmbeanmaker.new MyErrorHandler();
                  documentbuilder.setErrorHandler(myerrorhandler);
                  if (weblogicmbeanmaker.verbose || weblogicmbeanmaker.mdf != null) {
                     System.out.println("Parsing the MBean definition file: " + thisMDF);
                  }

                  weblogicmbeanmaker.mdfPath = thisMDF.getParent();
                  document = documentbuilder.parse(thisMDF);
                  weblogicmbeanmaker.attributeDescriptionMap = new HashMap();
                  weblogicmbeanmaker.root = document.getDocumentElement();
                  if (document != null) {
                     NamedNodeMap namednodemap = weblogicmbeanmaker.root.getAttributes();

                     for(int j = 0; j < namednodemap.getLength(); ++j) {
                        Node node = namednodemap.item(j);
                        if (weblogicmbeanmaker.verbose) {
                           System.out.println("Document attribute: " + node.getNodeName() + "=" + node.getNodeValue());
                        }
                     }

                     NodeList nodelist = weblogicmbeanmaker.root.getChildNodes();

                     Node cg;
                     for(int k = 0; k < nodelist.getLength(); ++k) {
                        cg = nodelist.item(k);
                        if (cg.getNodeType() == 1) {
                           if ("MBeanAttribute".equals(cg.getNodeName())) {
                              try {
                                 verifyProviderClassNameCollision(weblogicmbeanmaker.root, cg);
                              } catch (Throwable var22) {
                              }
                           }

                           NamedNodeMap _namednodemap = cg.getAttributes();

                           for(int h = 0; h < _namednodemap.getLength(); ++h) {
                              Node node = _namednodemap.item(h);
                              if (weblogicmbeanmaker.verbose) {
                                 System.out.println(node.getNodeName() + "=" + node.getNodeValue());
                              }
                           }

                           weblogicmbeanmaker.processElement(cg);
                        }
                     }

                     try {
                        String fromInstaller = System.getProperty("weblogic.SPUpgrade.FromInstaller");
                        if (fromInstaller == null) {
                           dumpUnsupportedWarnings(weblogicmbeanmaker.root);
                        }
                     } catch (Throwable var21) {
                     }

                     boolean flag1 = false;
                     Enumeration enumeration = weblogicmbeanmaker.constructors.elements();

                     while(enumeration.hasMoreElements()) {
                        Node node2 = (Node)enumeration.nextElement();
                        NodeList nodelist1 = node2.getChildNodes();

                        int l;
                        for(l = 0; l < nodelist1.getLength(); ++l) {
                           Node node3 = nodelist1.item(l);
                           if (node3.getNodeType() == 1 && node3.getNodeName().equals("MBeanOperationArg")) {
                              break;
                           }
                        }

                        if (l == nodelist1.getLength()) {
                           flag1 = true;
                           break;
                        }
                     }

                     if (!flag1) {
                        Element element = document.createElement("MBeanConstructor");
                        element.setAttribute("Description", "Generated default constructor");
                        weblogicmbeanmaker.root.appendChild(element);
                        weblogicmbeanmaker.constructors.insertElementAt(element, 0);
                     }

                     weblogicmbeanmaker.mBeanName = getMBeanName(weblogicmbeanmaker.root);
                     if (!weblogicmbeanmaker.internalWLSBuild) {
                        cg = null;
                        CodeGenerator cg = new DiabloIntfCodeGenerator(weblogicmbeanmaker);
                        cg.setRootDirectoryName(weblogicmbeanmaker.fileDir);
                        cg.generate(new String[]{weblogicmbeanmaker.mBeanName});
                     }

                     boolean buildStub = false;
                     String fromUpgrade = System.getProperty("weblogic.FromSPUpgrade", "false");
                     if (fromUpgrade.equals("false")) {
                        if (weblogicmbeanmaker.createStubs) {
                           buildStub = true;
                           if (weblogicmbeanmaker.preserveStubs) {
                              File stubFile = new File(weblogicmbeanmaker.fileDir + File.separatorChar + weblogicmbeanmaker.mBeanName + "Impl.java");
                              if (stubFile.exists()) {
                                 buildStub = false;
                              }
                           }
                        }
                     } else {
                        String stubFileName = weblogicmbeanmaker.mBeanName + "Impl.java";
                        String stubClassName = weblogicmbeanmaker.mBeanName + "Impl.class";
                        List _filList = new ArrayList();
                        String _s = null;
                        boolean stubFileExists = doesThisFileExist(_filList, weblogicmbeanmaker.fileDir, (String)_s, stubFileName);
                        if (!stubFileExists) {
                           stubFileExists = doesThisFileExist(_filList, weblogicmbeanmaker.fileDir, (String)_s, stubClassName);
                        }

                        if (weblogicmbeanmaker.createStubs) {
                           buildStub = true;
                           if (stubFileExists) {
                              System.out.println("Warning: Customizer/stub already exists, but will be replaced because you specified -createStubs.");
                           }
                        } else if (!stubFileExists) {
                           buildStub = true;
                        }
                     }

                     if (buildStub) {
                        CodeGenerator cg = new ImplCodeGenerator(weblogicmbeanmaker);
                        cg.setRootDirectoryName(weblogicmbeanmaker.fileDir);

                        try {
                           cg.generate(new String[]{weblogicmbeanmaker.mBeanName});
                        } catch (Exception var20) {
                           var20.printStackTrace();
                        }
                     }
                  }
               }
            }
         }

         if (weblogicmbeanmaker.mjf != null) {
            System.out.println("Creating an MJF from the contents of directory " + weblogicmbeanmaker.fileDir + "...");
            if (!weblogicmbeanmaker.noCompile) {
               System.out.println("Compiling the files...");
               weblogicmbeanmaker.compile(true);
            }

            File fileDir = new File(weblogicmbeanmaker.fileDir);
            if (weblogicmbeanmaker.targetNameSpace == null) {
               weblogicmbeanmaker.targetNameSpace = "http://xmlns.oracle.com/weblogic/security/extension";
            }

            JavaExec jx = JavaExec.createCommand("weblogic.management.commo.BeanGenDriver " + quoteIfContainsSpaces(weblogicmbeanmaker.fileDir) + " " + quoteIfContainsSpaces(weblogicmbeanmaker.mjf) + " " + weblogicmbeanmaker.targetNameSpace + " " + weblogicmbeanmaker.schemaLocation);
            jx.addDefaultClassPath();
            String fromInstaller = System.getProperty("weblogic.SPUpgrade.FromInstaller");
            jx.addClassPath(new File(weblogicmbeanmaker.fileDir + "ClassesDir"));
            if (fromInstaller != null) {
               jx.addClassPath(new File(System.getProperty("weblogic.SPUpgrade.WLSJarPath")));
               jx.addClassPath(new File(System.getProperty("weblogic.SPUpgrade.ToolsJarPath")));
               jx.addClassPath(new File(System.getProperty("weblogic.SPUpgrade.XbeanJarPath")));
               jx.addClassPath(new File(System.getProperty("weblogic.SPUpgrade.AntJarPath")));
               jx.addSystemProp("weblogic.SPUpgrade.FromInstaller", "true");
               jx.addSystemProp("weblogic.SPUpgrade.MBeanJarPath", System.getProperty("weblogic.SPUpgrade.MBeanJarPath"));
               jx.addSystemProp("weblogic.SPUpgrade.BindingJarPath", System.getProperty("weblogic.SPUpgrade.BindingJarPath"));
               jx.addSystemProp("weblogic.SPUpgrade.MBeanImplJarPath", System.getProperty("weblogic.SPUpgrade.MBeanImplJarPath"));
               jx.addSystemProp("weblogic.SPUpgrade.AntJarPath", System.getProperty("weblogic.SPUpgrade.AntJarPath"));
               jx.addSystemProp("weblogic.SPUpgrade.XbeanJarPath", System.getProperty("weblogic.SPUpgrade.XbeanJarPath"));
               jx.addSystemProp("weblogic.SPUpgrade.WLSJarPath", System.getProperty("weblogic.SPUpgrade.WLSJarPath"));
               jx.addSystemProp("weblogic.SPUpgrade.ToolsJarPath", System.getProperty("weblogic.SPUpgrade.ToolsJarPath"));
            }

            if (weblogicmbeanmaker.mbeantypesDir != null) {
               jx.addSystemProp("mbeantypesDir", weblogicmbeanmaker.mbeantypesDir);
            }

            if (weblogicmbeanmaker.externalDir != null) {
               jx.addSystemProp("externalDir", weblogicmbeanmaker.externalDir);
            }

            if (weblogicmbeanmaker.jvmArgs != null) {
               jx.addJvmArgs(weblogicmbeanmaker.jvmArgs);
            }

            String compatPropValue = System.getProperty("com.sun.xml.namespace.QName.useCompatibleSerialVersionUID");
            if (compatPropValue != null) {
               jx.addSystemProp("com.sun.xml.namespace.QName.useCompatibleSerialVersionUID", compatPropValue);
            }

            Process myChild = jx.getProcess();
            CommoProcess.startIOThreads(myChild, "WLMaker-SubProcess: ", true);
            if (myChild.waitFor() != 0) {
               System.out.println("BeanGen code generation failed");
               System.exit(1);
            }

            if (!weblogicmbeanmaker.mjf.endsWith("wlManagement.jar")) {
               generateSchema(weblogicmbeanmaker.fileDir, weblogicmbeanmaker.mjf, weblogicmbeanmaker.mbeantypesDir, weblogicmbeanmaker.jvmArgs);
            }

            weblogicmbeanmaker.compile(true);
            FileUtils.copy(new File(weblogicmbeanmaker.fileDir + "ClassesDir"), fileDir);
            FileUtils.remove(new File(weblogicmbeanmaker.fileDir + "ClassesDir"));
            System.out.println("Creating the MJF...");
            document = null;
            String[] args2;
            if (weblogicmbeanmaker.includeSource) {
               args2 = new String[0];
            } else {
               args2 = new String[]{"java"};
            }

            JarFileObject jarfileobject = JarFileObject.makeJar(weblogicmbeanmaker.mjf, fileDir, args2);
            jarfileobject.save();
            System.out.println("MJF is created.");
         }

         if (weblogicmbeanmaker.fileLoc == null) {
            System.out.println("Deleting generated files in " + weblogicmbeanmaker.fileDir + "...");
         }
      } catch (FileNotFoundException var24) {
         var24.printStackTrace();
      } catch (IOException var25) {
         var25.printStackTrace();
      } catch (ParserConfigurationException var26) {
         var26.printStackTrace();
      } catch (SAXException var27) {
         var27.printStackTrace();
      } catch (SecurityException var28) {
         System.out.println("Security breach!. Request Terminated.");
      }

   }

   private static boolean doesThisFileExist(List fileNameArray, String baseDir, String dir, String theFileName) {
      String fullDir = null;
      if (dir == null) {
         fullDir = baseDir;
      } else {
         fullDir = baseDir + File.separator + dir;
      }

      File df = new File(fullDir);
      String[] files = df.list();

      for(int i = 0; i < files.length; ++i) {
         String fileName = null;
         if (dir != null) {
            fileName = dir + File.separator + files[i];
         } else {
            fileName = files[i];
         }

         File file = new File(baseDir + File.separator + fileName);
         if (file.isDirectory()) {
            doesThisFileExist(fileNameArray, baseDir, fileName, theFileName);
         } else if (file.getName().equals(theFileName)) {
            return true;
         }
      }

      return false;
   }

   private static void generateSchema(String fileDir, String mjf, String mbeantypesDir, String jvmArgs) throws IOException, InterruptedException {
      if (mbeantypesDir == null) {
         String var10000 = "";
      } else {
         (new StringBuilder()).append(" ").append(quoteIfContainsSpaces(mbeantypesDir)).toString();
      }

      JavaExec jx = JavaExec.createCommand("weblogic.management.commo.JavaToSchemaUtil " + quoteIfContainsSpaces(fileDir) + " " + quoteIfContainsSpaces(mjf) + mbeantypesDir);
      jx.addDefaultClassPath();
      String fromInstaller = System.getProperty("weblogic.SPUpgrade.FromInstaller");
      if (fromInstaller != null) {
         jx.addClassPath(new File(System.getProperty("weblogic.SPUpgrade.WLSJarPath")));
         jx.addClassPath(new File(System.getProperty("weblogic.SPUpgrade.ToolsJarPath")));
         jx.addClassPath(new File(System.getProperty("weblogic.SPUpgrade.XbeanJarPath")));
         jx.addClassPath(new File(System.getProperty("weblogic.SPUpgrade.AntJarPath")));
         jx.addSystemProp("weblogic.SPUpgrade.FromInstaller", "true");
         jx.addSystemProp("weblogic.SPUpgrade.MBeanJarPath", System.getProperty("weblogic.SPUpgrade.MBeanJarPath"));
         jx.addSystemProp("weblogic.SPUpgrade.MBeanImplJarPath", System.getProperty("weblogic.SPUpgrade.MBeanImplJarPath"));
         jx.addSystemProp("weblogic.SPUpgrade.BindingJarPath", System.getProperty("weblogic.SPUpgrade.BindingJarPath"));
         jx.addSystemProp("weblogic.SPUpgrade.AntJarPath", System.getProperty("weblogic.SPUpgrade.AntJarPath"));
         jx.addSystemProp("weblogic.SPUpgrade.XbeanJarPath", System.getProperty("weblogic.SPUpgrade.XbeanJarPath"));
         jx.addSystemProp("weblogic.SPUpgrade.WLSJarPath", System.getProperty("weblogic.SPUpgrade.WLSJarPath"));
         jx.addSystemProp("weblogic.SPUpgrade.ToolsJarPath", System.getProperty("weblogic.SPUpgrade.ToolsJarPath"));
      }

      if (mbeantypesDir != null) {
         System.out.println("Adding mbeantypesDir=" + mbeantypesDir);
         jx.addSystemProp("mbeantypesDir", mbeantypesDir);
      }

      if (jvmArgs != null) {
         jx.addJvmArgs(jvmArgs);
      }

      String compatPropValue = System.getProperty("com.sun.xml.namespace.QName.useCompatibleSerialVersionUID");
      if (compatPropValue != null) {
         jx.addSystemProp("com.sun.xml.namespace.QName.useCompatibleSerialVersionUID", compatPropValue);
      }

      Process childProcess = jx.getProcess();
      CommoProcess.startIOThreads(childProcess, "WLMaker-SchemaGen-SubProcess", true);
      if (childProcess.waitFor() != 0) {
         System.out.println("Schema generation failed");
         System.exit(1);
      }

   }

   static void deleteFiles(File file) {
      String[] as = file.list();
      String s = file.getAbsolutePath();

      for(int i = 0; i < as.length; ++i) {
         String s1 = s + File.separatorChar + as[i];
         File file1 = new File(s1);
         if (file1.isDirectory()) {
            deleteFiles(file1);
         }

         System.out.println("Deleting " + file1);
         file1.delete();
      }

      file.delete();
   }

   public static boolean checkCompilerClass() {
      try {
         Class.forName("com.sun.tools.javac.Main");
         return true;
      } catch (Throwable var1) {
         return false;
      }
   }

   void compile(boolean recursive) {
      Getopt2 getopt2 = new Getopt2();
      CompilerInvoker compilerinvoker = new CompilerInvoker(getopt2);
      File file = new File(this.fileDir);
      if (!file.exists()) {
         file.mkdirs();
      }

      try {
         if (this.verbose) {
            getopt2.setFlag("commentary", true);
         }

         getopt2.setOption("compiler", this.compiler);
         if (this.source != null) {
            getopt2.setOption("source", this.source);
         }

         if (this.target != null) {
            getopt2.setOption("target", this.target);
         }

         if ("javac".equals(this.compiler) && checkCompilerClass()) {
            getopt2.setOption("compilerclass", "com.sun.tools.javac.Main");
            getopt2.setFlag("noexit", true);
         }

         if (this.classpath != null) {
            getopt2.setOption("classpath", this.classpath);
         }

         if (this.fileLoc != null) {
            getopt2.setFlag("keepgenerated", true);
         } else if (this.includeSource) {
            getopt2.setFlag("keepgenerated", true);
         }

         getopt2.setOption("d", this.fileDir);
         getopt2.setFlag("g", this.compileDebug);
         List listOFiles = new Vector();
         System.out.println("Creating the list.");
         this.accumulateFiles(file, listOFiles, recursive);
         System.out.println("Doing the compile.");
         String appenDir = file.getAbsolutePath() + "ClassesDir";
         File appDir = new File(appenDir);
         if (!appDir.exists()) {
            appDir.mkdirs();
         }

         compilerinvoker.overrideTargetDirectory(appenDir);
         this.compile(listOFiles, compilerinvoker);
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }

   private void accumulateFiles(File fileDir, List listOFiles, boolean recursive) {
      File[] files = fileDir.listFiles();

      for(int i = 0; i < files.length; ++i) {
         if (recursive && files[i].isDirectory()) {
            this.accumulateFiles(files[i], listOFiles, true);
         } else if (files[i].getName().endsWith(".java")) {
            boolean accumulate = false;
            if (this.internalWLSBuild) {
               if (files[i].getName().endsWith("MBI.java")) {
                  accumulate = true;
               }
            } else {
               accumulate = true;
            }

            if (accumulate) {
               listOFiles.add(files[i].getAbsolutePath());
            }
         }
      }

   }

   private void compile(List listOfFiles, CompilerInvoker compilerinvoker) {
      if (this.verbose) {
         System.out.print("Compiling these files");
         if (this.compileDebug) {
            System.out.print(" (debug)");
         }

         System.out.print("");
         Iterator it = listOfFiles.iterator();

         while(it.hasNext()) {
            System.out.println("   " + (String)it.next());
         }
      }

      try {
         if (!listOfFiles.isEmpty()) {
            compilerinvoker.compile(listOfFiles);
         }
      } catch (IOException var4) {
         var4.printStackTrace();
         System.exit(1);
      }

   }

   String getMBeanPackageName() {
      return this.attrVal(this.root, "Package");
   }

   void makeExistingElementLists() {
      Class class1 = null;

      try {
         class1 = Class.forName(this.getMBeanPackageName() + "." + this.mBeanName);
      } catch (ClassNotFoundException var5) {
         System.out.println("Could not locate existing MBean class. Files will be overwritten");
         return;
      }

      try {
         Method method = class1.getMethod("makeMBeanInfo");
         ModelMBeanInfo var3 = (ModelMBeanInfo)method.invoke((Object)null);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }

   void processElement(Node node) {
      String s = node.getNodeName();
      if (s.equals("MBeanAttribute")) {
         this.processAttribute(node);
      } else if (s.equals("MBeanNotification")) {
         this.processNotification(node);
      } else if (s.equals("MBeanOperation")) {
         this.processOperation(node);
      } else if (s.equals("MBeanConstructor")) {
         this.processConstructor(node);
      } else if (s.equals("MBeanImport")) {
         this.processImport(node);
      } else if (s.equals("MBeanImplements")) {
         this.processImplements(node);
      }

   }

   String getData(Node node) {
      return node.getFirstChild().getNodeValue();
   }

   static String getMBeanName(Node root) {
      NamedNodeMap namednodemap = root.getAttributes();
      Node node1 = namednodemap.getNamedItem("Name");
      return node1.getNodeValue();
   }

   String attrVal(Node node, String s) {
      NamedNodeMap namednodemap = node.getAttributes();
      Node node1 = namednodemap.getNamedItem(s);
      return node1 != null ? node1.getNodeValue() : null;
   }

   boolean attrValBool(Node node, String s) {
      String s1 = this.attrVal(node, s);
      boolean flag = false;

      try {
         flag = Boolean.valueOf(s1);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

      return flag;
   }

   String getDescription(Node node) throws Exception {
      String desc = this.getDescriptionFromDocXML(node);
      String desc0 = this.attrVal(node, "Description");
      if (this.doCheckDescription) {
         this.checkDescription(node, desc0, desc);
      }

      return desc != null && desc.length() != 0 ? desc : "No description provided.";
   }

   void checkDescription(Node node, String beandesc, String docdesc) {
      if (!this.isDefined(docdesc) && this.isDefined(beandesc)) {
         String name = node.getNodeName();
         if (name != null && name.startsWith("MBean")) {
            String nameAttr = this.getNameAttribute(node);
            this.getMBeanTypeName(node.getOwnerDocument());
            String nodeName = node.getNodeName();
            String mdfName = this.mBeanName + ".xml";
            String docName = this.mBeanName + DOC_XML_EXT;
            String msg = "No description in " + docName + " for " + nodeName + " " + nameAttr + " in " + mdfName;
            System.out.println(msg);
         }
      }

   }

   private Node getMBeanType(Document d) {
      if (d == null) {
         return null;
      } else {
         NodeList nl = d.getElementsByTagName("MBeanType");
         return nl != null && nl.getLength() >= 1 ? nl.item(0) : null;
      }
   }

   private String getMBeanTypeName(Document d) {
      return this.getNameAttribute(this.getMBeanType(d));
   }

   private String getNameAttribute(Node n) {
      String result = "unknown";
      if (n != null) {
         NamedNodeMap map = n.getAttributes();
         if (map != null) {
            Node item = map.getNamedItem("Name");
            if (item != null) {
               result = item.getNodeValue();
            }
         }
      }

      return result;
   }

   private boolean isDefined(String s) {
      return s != null && s.length() != 0;
   }

   String getDescriptionFromDocXML(Node node) throws Exception {
      String docName = this.mBeanName + DOC_XML_EXT;
      File docFile = new File(this.mdfPath + File.separator + docName);
      if (!docFile.exists()) {
         return null;
      } else {
         this.getStringFromXMLDoc(docFile);
         return this.getDescriptionFromDoc(node);
      }
   }

   String getDescriptionFromDoc(Node node) {
      NamedNodeMap namednodemap = node.getAttributes();
      Node node1 = namednodemap.getNamedItem("Name");
      if (node1 != null) {
         String name = node1.getNodeValue();
         String descr = (String)this.attributeDescriptionMap.get(name);
         if (descr == null) {
            return null;
         } else {
            String[] des = StringUtils.splitCompletely(descr, "\n");
            String s = StringUtils.join(des, " ");
            return s;
         }
      } else {
         return null;
      }
   }

   void getStringFromXMLDoc(File docFile) throws Exception {
      if (!this.isDocFileLoaded) {
         DocumentBuilderFactory documentbuilderfactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder documentbuilder = documentbuilderfactory.newDocumentBuilder();
         Document document = documentbuilder.parse(docFile);
         NodeList nlist = document.getChildNodes();

         for(int i = 0; i < nlist.getLength(); ++i) {
            Node nde = nlist.item(i);
            if (nde.getNodeType() == 1) {
               this.processForDescription(nde);
            }
         }

         this.isDocFileLoaded = true;
      }
   }

   void processForDescription(Node node) {
      if (node.getNodeName().equals("MBeanType")) {
         this.handlemBeanType(node);
         this.processMBeanAttribute(node);
      }

   }

   void handlemBeanType(Node nde) {
      NodeList list = nde.getChildNodes();
      if (list != null) {
         for(int i = 0; i < list.getLength(); ++i) {
            Node nde1 = list.item(i);
            if (nde1.getNodeName().equals("MBeanAttribute")) {
               this.processMBeanAttribute(nde1);
            } else if (nde1.getNodeName().equals("MBeanOperation")) {
               this.processMBeanOperation(nde1);
            }
         }

         String var5;
         String s;
         for(Iterator iter = this.attributeDescriptionMap.keySet().iterator(); iter.hasNext(); var5 = (String)this.attributeDescriptionMap.get(s)) {
            s = (String)iter.next();
         }

      }
   }

   void processMBeanOperation(Node node) {
      this.processMBeanAttribute(node);
   }

   void processMBeanAttribute(Node node) {
      NodeList list = node.getChildNodes();
      String attrName = "";
      if (node.hasAttributes()) {
         NamedNodeMap nnm = node.getAttributes();

         for(int k = 0; k < nnm.getLength(); ++k) {
            Node ne = nnm.item(k);
            if (ne.getNodeName().equals("Name")) {
               attrName = ne.getNodeValue();
            }
         }
      }

      for(int i = 0; i < list.getLength(); ++i) {
         Node nde = list.item(i);
         if (nde.getNodeName().equals("Description")) {
            this.handleDescriptionChildren(nde, attrName);
         }
      }

   }

   void handleDescriptionChildren(Node node, String attrName) {
      NodeList list = node.getChildNodes();

      for(int i = 0; i < list.getLength(); ++i) {
         Node nde = list.item(i);
         if (nde.getNodeName().equals("Lead")) {
            this.handleDescriptionText(nde, attrName);
         } else if (nde.getNodeName().equals("Detail")) {
            this.handleDescriptionText(nde, attrName);
         }
      }

   }

   void handleDescriptionText(Node node, String attrName) {
      String k = "";
      if (this.attributeDescriptionMap.containsKey(attrName)) {
         k = (String)this.attributeDescriptionMap.get(attrName);
      }

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      Source source = new DOMSource(node);
      Result result = new StreamResult(out);

      try {
         transformer.transform(source, result);
      } catch (TransformerException var10) {
         throw new RuntimeException(var10);
      }

      String nodeText = out.toString();
      Matcher m = pattern.matcher(nodeText);
      if (!m.lookingAt()) {
         throw new RuntimeException("Input pattern does not match " + m.pattern().toString() + " : " + nodeText);
      } else {
         String subText = m.group(2).trim();
         this.attributeDescriptionMap.put(attrName, k + subText);
      }
   }

   boolean isArray(Node node) {
      String s = this.attrVal(node, "Type");
      return s != null ? s.trim().endsWith("]") : false;
   }

   void printName(Node node) {
      String s = node.getNodeName();
      String name = null;
      if (s.equals("MBeanConstructor")) {
         name = this.mBeanName;
      } else {
         NamedNodeMap namednodemap = node.getAttributes();
         Node node1 = namednodemap.getNamedItem("Name");
         name = node1.getNodeValue();
      }

      System.out.print(name + "\n");
   }

   void printNotificationTypes(Node node) {
      System.out.print(this.attrVal(node, "NotificationTypes") + "\n");
   }

   void printData(Node node) {
      System.out.print(this.getData(node) + "\n");
   }

   Vector getParameterTypes(Node node) {
      Vector vector = new Vector();
      NodeList nodelist = node.getChildNodes();

      for(int i = 0; i < nodelist.getLength(); ++i) {
         Node node1 = nodelist.item(i);
         if (node1.getNodeType() == 1 && node1.getNodeName().equals("MBeanOperationArg")) {
            NamedNodeMap namednodemap = node1.getAttributes();
            vector.addElement(namednodemap.getNamedItem("Type").getNodeValue());
         }
      }

      return vector;
   }

   void printSigniture(Node node) {
      NamedNodeMap namednodemap = node.getAttributes();
      String s = node.getNodeName();
      String name = null;
      if (!s.equals("MBeanConstructor")) {
         name = namednodemap.getNamedItem("Name").getNodeValue();
      } else {
         name = this.mBeanName;
      }

      System.out.print(name + " ( ");
      NodeList nodelist = node.getChildNodes();
      boolean flag = true;

      int j;
      Node node3;
      for(j = 0; j < nodelist.getLength(); ++j) {
         node3 = nodelist.item(j);
         if (node3.getNodeType() == 1 && node3.getNodeName().equals("MBeanOperationArg")) {
            if (!flag) {
               System.out.print(", ");
            }

            flag = false;
            NamedNodeMap namednodemap1 = node3.getAttributes();
            System.out.print(namednodemap1.getNamedItem("Type").getNodeValue() + " " + namednodemap1.getNamedItem("Name").getNodeValue());
         }
      }

      System.out.print(" )");
      flag = true;

      for(j = 0; j < nodelist.getLength(); ++j) {
         node3 = nodelist.item(j);
         if (node3.getNodeType() == 1 && node3.getNodeName().equals("MBeanException")) {
            if (flag) {
               System.out.print(" throws ");
            } else {
               System.out.print(", ");
            }

            flag = false;
            System.out.print(node3.getFirstChild().getNodeValue());
         }
      }

      System.out.print("\n");
   }

   void printTags(Node node) {
      NamedNodeMap namednodemap = node.getAttributes();

      for(int i = 0; i < namednodemap.getLength(); ++i) {
         Node node1 = namednodemap.item(i);
         if (!node1.getNodeName().equals("Name")) {
            System.out.println("     " + node1.getNodeName() + "=" + node1.getNodeValue());
         }
      }

   }

   void processOperation(Node node) {
      if (this.verbose) {
         System.out.print("   Operation: ");
      }

      if (this.verbose) {
         this.printSigniture(node);
      }

      if (this.verbose) {
         this.printTags(node);
      }

      this.operations.addElement(node);
   }

   void processConstructor(Node node) {
      if (this.verbose) {
         System.out.print("   Constructor: ");
      }

      if (this.verbose) {
         this.printSigniture(node);
      }

      if (this.verbose) {
         this.printTags(node);
      }

      this.constructors.addElement(node);
   }

   void processImport(Node node) {
      if (this.verbose) {
         System.out.print("   Import: ");
      }

      if (this.verbose) {
         this.printData(node);
      }

      if (this.verbose) {
         this.printTags(node);
      }

      this.imports.addElement(node);
   }

   void processImplements(Node node) {
      if (this.verbose) {
         System.out.print("   Implements: ");
      }

      if (this.verbose) {
         this.printData(node);
      }

      if (this.verbose) {
         this.printTags(node);
      }

      this.implementsSet.addElement(node);
   }

   void processAttribute(Node node) {
      if (this.verbose) {
         System.out.print("   Attribute: ");
      }

      if (this.verbose) {
         this.printName(node);
      }

      if (this.verbose) {
         this.printTags(node);
      }

      this.attributes.addElement(node);
   }

   void processNotification(Node node) {
      if (this.verbose) {
         System.out.print("   Notification: ");
      }

      if (this.verbose) {
         this.printNotificationTypes(node);
      }

      if (this.verbose) {
         this.printTags(node);
      }

      this.notifications.addElement(node);
   }

   public String mBeanName() {
      if (this.mBeanName == null) {
         this.mBeanName = this.getName(this.root);
      }

      return this.mBeanName;
   }

   String getName(Node node) {
      String s = node.getNodeName();
      if (s.equals("MBeanConstructor")) {
         return this.mBeanName;
      } else {
         NamedNodeMap namednodemap = node.getAttributes();
         Node node1 = namednodemap.getNamedItem("Name");
         return node1.getNodeValue();
      }
   }

   private static String quoteIfContainsSpaces(String str) {
      return str != null && str.indexOf(" ") != -1 ? '"' + str + '"' : str;
   }

   static {
      try {
         TransformerFactory factory = TransformerFactory.newInstance();
         transformer = factory.newTransformer();
      } catch (TransformerException var1) {
         throw new RuntimeException(var1);
      }

      mbeanTypeAttributesNotSupportedIn90 = new ArrayList();
      mbeanAttributesNotSupportedIn90 = new ArrayList();
      mbeanOperationsNotSupportedIn90 = new ArrayList();
      mbeanOperationsArgNotSupportedIn90 = new ArrayList();
      opts = null;
   }

   public class MyErrorHandler implements ErrorHandler {
      public void fatalError(SAXParseException saxparseexception) {
         System.out.println("******************************************************");
         System.out.println("fatalError:");
         saxparseexception.printStackTrace();
         System.out.println("******************************************************");
      }

      public void error(SAXParseException saxparseexception) {
         System.out.println("******************************************************");
         System.out.println("error:");
         saxparseexception.printStackTrace();
         System.out.println("******************************************************");
      }

      public void warning(SAXParseException saxparseexception) {
         System.out.println("******************************************************");
         System.out.println("warning:");
         saxparseexception.printStackTrace();
         System.out.println("******************************************************");
      }

      MyErrorHandler() {
      }
   }
}

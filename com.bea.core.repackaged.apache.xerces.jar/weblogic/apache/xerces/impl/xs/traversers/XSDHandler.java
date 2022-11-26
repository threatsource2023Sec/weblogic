package weblogic.apache.xerces.impl.xs.traversers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Stack;
import java.util.Vector;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
import weblogic.apache.xerces.impl.XMLEntityManager;
import weblogic.apache.xerces.impl.XMLErrorReporter;
import weblogic.apache.xerces.impl.dv.SchemaDVFactory;
import weblogic.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import weblogic.apache.xerces.impl.xs.SchemaGrammar;
import weblogic.apache.xerces.impl.xs.SchemaNamespaceSupport;
import weblogic.apache.xerces.impl.xs.SchemaSymbols;
import weblogic.apache.xerces.impl.xs.XMLSchemaException;
import weblogic.apache.xerces.impl.xs.XMLSchemaLoader;
import weblogic.apache.xerces.impl.xs.XSAttributeDecl;
import weblogic.apache.xerces.impl.xs.XSAttributeGroupDecl;
import weblogic.apache.xerces.impl.xs.XSComplexTypeDecl;
import weblogic.apache.xerces.impl.xs.XSDDescription;
import weblogic.apache.xerces.impl.xs.XSDeclarationPool;
import weblogic.apache.xerces.impl.xs.XSElementDecl;
import weblogic.apache.xerces.impl.xs.XSGrammarBucket;
import weblogic.apache.xerces.impl.xs.XSGroupDecl;
import weblogic.apache.xerces.impl.xs.XSModelGroupImpl;
import weblogic.apache.xerces.impl.xs.XSNotationDecl;
import weblogic.apache.xerces.impl.xs.XSParticleDecl;
import weblogic.apache.xerces.impl.xs.identity.IdentityConstraint;
import weblogic.apache.xerces.impl.xs.opti.ElementImpl;
import weblogic.apache.xerces.impl.xs.opti.SchemaDOM;
import weblogic.apache.xerces.impl.xs.opti.SchemaDOMParser;
import weblogic.apache.xerces.impl.xs.opti.SchemaParsingConfig;
import weblogic.apache.xerces.impl.xs.util.SimpleLocator;
import weblogic.apache.xerces.impl.xs.util.XSInputSource;
import weblogic.apache.xerces.parsers.SAXParser;
import weblogic.apache.xerces.parsers.XML11Configuration;
import weblogic.apache.xerces.util.DOMInputSource;
import weblogic.apache.xerces.util.DOMUtil;
import weblogic.apache.xerces.util.DefaultErrorHandler;
import weblogic.apache.xerces.util.ErrorHandlerWrapper;
import weblogic.apache.xerces.util.SAXInputSource;
import weblogic.apache.xerces.util.StAXInputSource;
import weblogic.apache.xerces.util.StAXLocationWrapper;
import weblogic.apache.xerces.util.SymbolHash;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.util.URI;
import weblogic.apache.xerces.util.XMLChar;
import weblogic.apache.xerces.util.XMLSymbols;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.grammars.Grammar;
import weblogic.apache.xerces.xni.grammars.XMLGrammarDescription;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.grammars.XMLSchemaDescription;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;
import weblogic.apache.xerces.xni.parser.XMLEntityResolver;
import weblogic.apache.xerces.xni.parser.XMLErrorHandler;
import weblogic.apache.xerces.xni.parser.XMLInputSource;
import weblogic.apache.xerces.xni.parser.XMLParseException;
import weblogic.apache.xerces.xs.StringList;
import weblogic.apache.xerces.xs.XSAttributeDeclaration;
import weblogic.apache.xerces.xs.XSAttributeGroupDefinition;
import weblogic.apache.xerces.xs.XSAttributeUse;
import weblogic.apache.xerces.xs.XSElementDeclaration;
import weblogic.apache.xerces.xs.XSModelGroup;
import weblogic.apache.xerces.xs.XSModelGroupDefinition;
import weblogic.apache.xerces.xs.XSNamedMap;
import weblogic.apache.xerces.xs.XSObject;
import weblogic.apache.xerces.xs.XSObjectList;
import weblogic.apache.xerces.xs.XSParticle;
import weblogic.apache.xerces.xs.XSSimpleTypeDefinition;
import weblogic.apache.xerces.xs.XSTerm;
import weblogic.apache.xerces.xs.XSTypeDefinition;
import weblogic.apache.xerces.xs.datatypes.ObjectList;

public class XSDHandler {
   protected static final String VALIDATION = "http://xml.org/sax/features/validation";
   protected static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
   protected static final String ALLOW_JAVA_ENCODINGS = "http://apache.org/xml/features/allow-java-encodings";
   protected static final String CONTINUE_AFTER_FATAL_ERROR = "http://apache.org/xml/features/continue-after-fatal-error";
   protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
   protected static final String DISALLOW_DOCTYPE = "http://apache.org/xml/features/disallow-doctype-decl";
   protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
   protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
   protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
   protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
   protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
   private static final String NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
   protected static final String STRING_INTERNING = "http://xml.org/sax/features/string-interning";
   protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
   protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
   public static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
   protected static final String ENTITY_MANAGER = "http://apache.org/xml/properties/internal/entity-manager";
   public static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
   public static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
   public static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
   protected static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
   protected static final String LOCALE = "http://apache.org/xml/properties/locale";
   protected static final boolean DEBUG_NODE_POOL = false;
   static final int ATTRIBUTE_TYPE = 1;
   static final int ATTRIBUTEGROUP_TYPE = 2;
   static final int ELEMENT_TYPE = 3;
   static final int GROUP_TYPE = 4;
   static final int IDENTITYCONSTRAINT_TYPE = 5;
   static final int NOTATION_TYPE = 6;
   static final int TYPEDECL_TYPE = 7;
   public static final String REDEF_IDENTIFIER = "_fn3dktizrknc9pi";
   protected Hashtable fNotationRegistry;
   protected XSDeclarationPool fDeclPool;
   private Hashtable fUnparsedAttributeRegistry;
   private Hashtable fUnparsedAttributeGroupRegistry;
   private Hashtable fUnparsedElementRegistry;
   private Hashtable fUnparsedGroupRegistry;
   private Hashtable fUnparsedIdentityConstraintRegistry;
   private Hashtable fUnparsedNotationRegistry;
   private Hashtable fUnparsedTypeRegistry;
   private Hashtable fUnparsedAttributeRegistrySub;
   private Hashtable fUnparsedAttributeGroupRegistrySub;
   private Hashtable fUnparsedElementRegistrySub;
   private Hashtable fUnparsedGroupRegistrySub;
   private Hashtable fUnparsedIdentityConstraintRegistrySub;
   private Hashtable fUnparsedNotationRegistrySub;
   private Hashtable fUnparsedTypeRegistrySub;
   private Hashtable[] fUnparsedRegistriesExt;
   private Hashtable fXSDocumentInfoRegistry;
   private Hashtable fDependencyMap;
   private Hashtable fImportMap;
   private Vector fAllTNSs;
   private Hashtable fLocationPairs;
   private static final Hashtable EMPTY_TABLE = new Hashtable();
   Hashtable fHiddenNodes;
   private Hashtable fTraversed;
   private Hashtable fDoc2SystemId;
   private XSDocumentInfo fRoot;
   private Hashtable fDoc2XSDocumentMap;
   private Hashtable fRedefine2XSDMap;
   private Hashtable fRedefine2NSSupport;
   private Hashtable fRedefinedRestrictedAttributeGroupRegistry;
   private Hashtable fRedefinedRestrictedGroupRegistry;
   private boolean fLastSchemaWasDuplicate;
   private boolean fValidateAnnotations;
   private boolean fHonourAllSchemaLocations;
   boolean fNamespaceGrowth;
   boolean fTolerateDuplicates;
   private XMLErrorReporter fErrorReporter;
   private XMLEntityResolver fEntityResolver;
   private XSAttributeChecker fAttributeChecker;
   private SymbolTable fSymbolTable;
   private XSGrammarBucket fGrammarBucket;
   private XSDDescription fSchemaGrammarDescription;
   private XMLGrammarPool fGrammarPool;
   XSDAttributeGroupTraverser fAttributeGroupTraverser;
   XSDAttributeTraverser fAttributeTraverser;
   XSDComplexTypeTraverser fComplexTypeTraverser;
   XSDElementTraverser fElementTraverser;
   XSDGroupTraverser fGroupTraverser;
   XSDKeyrefTraverser fKeyrefTraverser;
   XSDNotationTraverser fNotationTraverser;
   XSDSimpleTypeTraverser fSimpleTypeTraverser;
   XSDUniqueOrKeyTraverser fUniqueOrKeyTraverser;
   XSDWildcardTraverser fWildCardTraverser;
   SchemaDVFactory fDVFactory;
   SchemaDOMParser fSchemaParser;
   SchemaContentHandler fXSContentHandler;
   StAXSchemaParser fStAXSchemaParser;
   XML11Configuration fAnnotationValidator;
   XSAnnotationGrammarPool fGrammarBucketAdapter;
   private static final int INIT_STACK_SIZE = 30;
   private static final int INC_STACK_SIZE = 10;
   private int fLocalElemStackPos;
   private XSParticleDecl[] fParticle;
   private Element[] fLocalElementDecl;
   private XSDocumentInfo[] fLocalElementDecl_schema;
   private int[] fAllContext;
   private XSObject[] fParent;
   private String[][] fLocalElemNamespaceContext;
   private static final int INIT_KEYREF_STACK = 2;
   private static final int INC_KEYREF_STACK_AMOUNT = 2;
   private int fKeyrefStackPos;
   private Element[] fKeyrefs;
   private XSDocumentInfo[] fKeyrefsMapXSDocumentInfo;
   private XSElementDecl[] fKeyrefElems;
   private String[][] fKeyrefNamespaceContext;
   SymbolHash fGlobalAttrDecls;
   SymbolHash fGlobalAttrGrpDecls;
   SymbolHash fGlobalElemDecls;
   SymbolHash fGlobalGroupDecls;
   SymbolHash fGlobalNotationDecls;
   SymbolHash fGlobalIDConstraintDecls;
   SymbolHash fGlobalTypeDecls;
   private static final String[][] NS_ERROR_CODES = new String[][]{{"src-include.2.1", "src-include.2.1"}, {"src-redefine.3.1", "src-redefine.3.1"}, {"src-import.3.1", "src-import.3.2"}, null, {"TargetNamespace.1", "TargetNamespace.2"}, {"TargetNamespace.1", "TargetNamespace.2"}, {"TargetNamespace.1", "TargetNamespace.2"}, {"TargetNamespace.1", "TargetNamespace.2"}};
   private static final String[] ELE_ERROR_CODES = new String[]{"src-include.1", "src-redefine.2", "src-import.2", "schema_reference.4", "schema_reference.4", "schema_reference.4", "schema_reference.4", "schema_reference.4"};
   private Vector fReportedTNS;
   private static final String[] COMP_TYPE = new String[]{null, "attribute declaration", "attribute group", "element declaration", "group", "identity constraint", "notation", "type definition"};
   private static final String[] CIRCULAR_CODES = new String[]{"Internal-Error", "Internal-Error", "src-attribute_group.3", "e-props-correct.6", "mg-props-correct.2", "Internal-Error", "Internal-Error", "st-props-correct.2"};
   private SimpleLocator xl;

   private String null2EmptyString(String var1) {
      return var1 == null ? XMLSymbols.EMPTY_STRING : var1;
   }

   private String emptyString2Null(String var1) {
      return var1 == XMLSymbols.EMPTY_STRING ? null : var1;
   }

   private String doc2SystemId(Element var1) {
      String var2 = null;
      if (var1.getOwnerDocument() instanceof SchemaDOM) {
         var2 = ((SchemaDOM)var1.getOwnerDocument()).getDocumentURI();
      }

      return var2 != null ? var2 : (String)this.fDoc2SystemId.get(var1);
   }

   public XSDHandler() {
      this.fNotationRegistry = new Hashtable();
      this.fDeclPool = null;
      this.fUnparsedAttributeRegistry = new Hashtable();
      this.fUnparsedAttributeGroupRegistry = new Hashtable();
      this.fUnparsedElementRegistry = new Hashtable();
      this.fUnparsedGroupRegistry = new Hashtable();
      this.fUnparsedIdentityConstraintRegistry = new Hashtable();
      this.fUnparsedNotationRegistry = new Hashtable();
      this.fUnparsedTypeRegistry = new Hashtable();
      this.fUnparsedAttributeRegistrySub = new Hashtable();
      this.fUnparsedAttributeGroupRegistrySub = new Hashtable();
      this.fUnparsedElementRegistrySub = new Hashtable();
      this.fUnparsedGroupRegistrySub = new Hashtable();
      this.fUnparsedIdentityConstraintRegistrySub = new Hashtable();
      this.fUnparsedNotationRegistrySub = new Hashtable();
      this.fUnparsedTypeRegistrySub = new Hashtable();
      this.fUnparsedRegistriesExt = new Hashtable[]{null, new Hashtable(), new Hashtable(), new Hashtable(), new Hashtable(), new Hashtable(), new Hashtable(), new Hashtable()};
      this.fXSDocumentInfoRegistry = new Hashtable();
      this.fDependencyMap = new Hashtable();
      this.fImportMap = new Hashtable();
      this.fAllTNSs = new Vector();
      this.fLocationPairs = null;
      this.fHiddenNodes = null;
      this.fTraversed = new Hashtable();
      this.fDoc2SystemId = new Hashtable();
      this.fRoot = null;
      this.fDoc2XSDocumentMap = new Hashtable();
      this.fRedefine2XSDMap = new Hashtable();
      this.fRedefine2NSSupport = new Hashtable();
      this.fRedefinedRestrictedAttributeGroupRegistry = new Hashtable();
      this.fRedefinedRestrictedGroupRegistry = new Hashtable();
      this.fValidateAnnotations = false;
      this.fHonourAllSchemaLocations = false;
      this.fNamespaceGrowth = false;
      this.fTolerateDuplicates = false;
      this.fLocalElemStackPos = 0;
      this.fParticle = new XSParticleDecl[30];
      this.fLocalElementDecl = new Element[30];
      this.fLocalElementDecl_schema = new XSDocumentInfo[30];
      this.fAllContext = new int[30];
      this.fParent = new XSObject[30];
      this.fLocalElemNamespaceContext = new String[30][1];
      this.fKeyrefStackPos = 0;
      this.fKeyrefs = new Element[2];
      this.fKeyrefsMapXSDocumentInfo = new XSDocumentInfo[2];
      this.fKeyrefElems = new XSElementDecl[2];
      this.fKeyrefNamespaceContext = new String[2][1];
      this.fGlobalAttrDecls = new SymbolHash(12);
      this.fGlobalAttrGrpDecls = new SymbolHash(5);
      this.fGlobalElemDecls = new SymbolHash(25);
      this.fGlobalGroupDecls = new SymbolHash(5);
      this.fGlobalNotationDecls = new SymbolHash(1);
      this.fGlobalIDConstraintDecls = new SymbolHash(3);
      this.fGlobalTypeDecls = new SymbolHash(25);
      this.fReportedTNS = null;
      this.xl = new SimpleLocator();
      this.fHiddenNodes = new Hashtable();
      this.fSchemaParser = new SchemaDOMParser(new SchemaParsingConfig());
   }

   public XSDHandler(XSGrammarBucket var1) {
      this();
      this.fGrammarBucket = var1;
      this.fSchemaGrammarDescription = new XSDDescription();
   }

   public SchemaGrammar parseSchema(XMLInputSource var1, XSDDescription var2, Hashtable var3) throws IOException {
      this.fLocationPairs = var3;
      this.fSchemaParser.resetNodePool();
      SchemaGrammar var4 = null;
      String var5 = null;
      short var6 = var2.getContextType();
      if (var6 != 3) {
         if (this.fHonourAllSchemaLocations && var6 == 2 && this.isExistingGrammar(var2, this.fNamespaceGrowth)) {
            var4 = this.fGrammarBucket.getGrammar(var2.getTargetNamespace());
         } else {
            var4 = this.findGrammar(var2, this.fNamespaceGrowth);
         }

         if (var4 != null) {
            if (!this.fNamespaceGrowth) {
               return var4;
            }

            try {
               if (var4.getDocumentLocations().contains(XMLEntityManager.expandSystemId(var1.getSystemId(), var1.getBaseSystemId(), false))) {
                  return var4;
               }
            } catch (URI.MalformedURIException var16) {
            }
         }

         var5 = var2.getTargetNamespace();
         if (var5 != null) {
            var5 = this.fSymbolTable.addSymbol(var5);
         }
      }

      this.prepareForParse();
      Element var7 = null;
      if (var1 instanceof DOMInputSource) {
         var7 = this.getSchemaDocument(var5, (DOMInputSource)((DOMInputSource)var1), var6 == 3, var6, (Element)null);
      } else if (var1 instanceof SAXInputSource) {
         var7 = this.getSchemaDocument(var5, (SAXInputSource)((SAXInputSource)var1), var6 == 3, var6, (Element)null);
      } else if (var1 instanceof StAXInputSource) {
         var7 = this.getSchemaDocument(var5, (StAXInputSource)((StAXInputSource)var1), var6 == 3, var6, (Element)null);
      } else if (var1 instanceof XSInputSource) {
         var7 = this.getSchemaDocument((XSInputSource)var1, var2);
      } else {
         var7 = this.getSchemaDocument(var5, (XMLInputSource)var1, var6 == 3, var6, (Element)null);
      }

      if (var7 == null) {
         if (var1 instanceof XSInputSource) {
            XSInputSource var17 = (XSInputSource)var1;
            SchemaGrammar[] var19 = var17.getGrammars();
            if (var19 != null && var19.length > 0) {
               var4 = this.fGrammarBucket.getGrammar(var19[0].getTargetNamespace());
            } else {
               XSObject[] var21 = var17.getComponents();
               if (var21 != null && var21.length > 0) {
                  var4 = this.fGrammarBucket.getGrammar(var21[0].getNamespace());
               }
            }
         }

         return var4;
      } else {
         if (var6 == 3) {
            var5 = DOMUtil.getAttrValue(var7, SchemaSymbols.ATT_TARGETNAMESPACE);
            if (var5 != null && var5.length() > 0) {
               var5 = this.fSymbolTable.addSymbol(var5);
               var2.setTargetNamespace(var5);
            } else {
               var5 = null;
            }

            var4 = this.findGrammar(var2, this.fNamespaceGrowth);
            String var9 = XMLEntityManager.expandSystemId(var1.getSystemId(), var1.getBaseSystemId(), false);
            if (var4 != null && (!this.fNamespaceGrowth || var9 != null && var4.getDocumentLocations().contains(var9))) {
               return var4;
            }

            XSDKey var10 = new XSDKey(var9, var6, var5);
            this.fTraversed.put(var10, var7);
            if (var9 != null) {
               this.fDoc2SystemId.put(var7, var9);
            }
         }

         this.prepareForTraverse();
         this.fRoot = this.constructTrees(var7, var1.getSystemId(), var2, var4 != null);
         if (this.fRoot == null) {
            return null;
         } else {
            this.buildGlobalNameRegistries();
            ArrayList var8 = this.fValidateAnnotations ? new ArrayList() : null;
            this.traverseSchemas(var8);
            this.traverseLocalElements();
            this.resolveKeyRefs();

            for(int var18 = this.fAllTNSs.size() - 1; var18 >= 0; --var18) {
               String var20 = (String)this.fAllTNSs.elementAt(var18);
               Vector var11 = (Vector)this.fImportMap.get(var20);
               SchemaGrammar var12 = this.fGrammarBucket.getGrammar(this.emptyString2Null(var20));
               if (var12 != null) {
                  int var14 = 0;

                  for(int var15 = 0; var15 < var11.size(); ++var15) {
                     SchemaGrammar var13 = this.fGrammarBucket.getGrammar((String)var11.elementAt(var15));
                     if (var13 != null) {
                        var11.setElementAt(var13, var14++);
                     }
                  }

                  var11.setSize(var14);
                  var12.setImportedGrammars(var11);
               }
            }

            if (this.fValidateAnnotations && var8.size() > 0) {
               this.validateAnnotations(var8);
            }

            return this.fGrammarBucket.getGrammar(this.fRoot.fTargetNamespace);
         }
      }
   }

   private void validateAnnotations(ArrayList var1) {
      if (this.fAnnotationValidator == null) {
         this.createAnnotationValidator();
      }

      int var2 = var1.size();
      XMLInputSource var3 = new XMLInputSource((String)null, (String)null, (String)null);
      this.fGrammarBucketAdapter.refreshGrammars(this.fGrammarBucket);

      for(int var4 = 0; var4 < var2; var4 += 2) {
         var3.setSystemId((String)var1.get(var4));

         for(XSAnnotationInfo var5 = (XSAnnotationInfo)var1.get(var4 + 1); var5 != null; var5 = var5.next) {
            var3.setCharacterStream(new StringReader(var5.fAnnotation));

            try {
               this.fAnnotationValidator.parse(var3);
            } catch (IOException var7) {
            }
         }
      }

   }

   private void createAnnotationValidator() {
      this.fAnnotationValidator = new XML11Configuration();
      this.fGrammarBucketAdapter = new XSAnnotationGrammarPool();
      this.fAnnotationValidator.setFeature("http://xml.org/sax/features/validation", true);
      this.fAnnotationValidator.setFeature("http://apache.org/xml/features/validation/schema", true);
      this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fGrammarBucketAdapter);
      XMLErrorHandler var1 = this.fErrorReporter.getErrorHandler();
      this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/error-handler", var1 != null ? var1 : new DefaultErrorHandler());
      Locale var2 = this.fErrorReporter.getLocale();
      this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/locale", var2);
   }

   SchemaGrammar getGrammar(String var1) {
      return this.fGrammarBucket.getGrammar(var1);
   }

   protected SchemaGrammar findGrammar(XSDDescription var1, boolean var2) {
      SchemaGrammar var3 = this.fGrammarBucket.getGrammar(var1.getTargetNamespace());
      if (var3 == null && this.fGrammarPool != null) {
         var3 = (SchemaGrammar)this.fGrammarPool.retrieveGrammar(var1);
         if (var3 != null && !this.fGrammarBucket.putGrammar(var3, true, var2)) {
            this.reportSchemaWarning("GrammarConflict", (Object[])null, (Element)null);
            var3 = null;
         }
      }

      return var3;
   }

   protected XSDocumentInfo constructTrees(Element var1, String var2, XSDDescription var3, boolean var4) {
      if (var1 == null) {
         return null;
      } else {
         String var5 = var3.getTargetNamespace();
         short var6 = var3.getContextType();
         XSDocumentInfo var7 = null;

         try {
            var7 = new XSDocumentInfo(var1, this.fAttributeChecker, this.fSymbolTable);
         } catch (XMLSchemaException var25) {
            this.reportSchemaError(ELE_ERROR_CODES[var6], new Object[]{var2}, var1);
            return null;
         }

         if (var7.fTargetNamespace != null && var7.fTargetNamespace.length() == 0) {
            this.reportSchemaWarning("EmptyTargetNamespace", new Object[]{var2}, var1);
            var7.fTargetNamespace = null;
         }

         byte var8;
         if (var5 != null) {
            var8 = 0;
            if (var6 != 0 && var6 != 1) {
               if (var6 != 3 && var5 != var7.fTargetNamespace) {
                  this.reportSchemaError(NS_ERROR_CODES[var6][var8], new Object[]{var5, var7.fTargetNamespace}, var1);
                  return null;
               }
            } else if (var7.fTargetNamespace == null) {
               var7.fTargetNamespace = var5;
               var7.fIsChameleonSchema = true;
            } else if (var5 != var7.fTargetNamespace) {
               this.reportSchemaError(NS_ERROR_CODES[var6][var8], new Object[]{var5, var7.fTargetNamespace}, var1);
               return null;
            }
         } else if (var7.fTargetNamespace != null) {
            if (var6 != 3) {
               var8 = 1;
               this.reportSchemaError(NS_ERROR_CODES[var6][var8], new Object[]{var5, var7.fTargetNamespace}, var1);
               return null;
            }

            var3.setTargetNamespace(var7.fTargetNamespace);
            var5 = var7.fTargetNamespace;
         }

         var7.addAllowedNS(var7.fTargetNamespace);
         SchemaGrammar var27 = null;
         if (var4) {
            SchemaGrammar var9 = this.fGrammarBucket.getGrammar(var7.fTargetNamespace);
            if (var9.isImmutable()) {
               var27 = new SchemaGrammar(var9);
               this.fGrammarBucket.putGrammar(var27);
               this.updateImportListWith(var27);
            } else {
               var27 = var9;
            }

            this.updateImportListFor(var27);
         } else if (var6 != 0 && var6 != 1) {
            if (this.fHonourAllSchemaLocations && var6 == 2) {
               var27 = this.findGrammar(var3, false);
               if (var27 == null) {
                  var27 = new SchemaGrammar(var7.fTargetNamespace, var3.makeClone(), this.fSymbolTable);
                  this.fGrammarBucket.putGrammar(var27);
               }
            } else {
               var27 = new SchemaGrammar(var7.fTargetNamespace, var3.makeClone(), this.fSymbolTable);
               this.fGrammarBucket.putGrammar(var27);
            }
         } else {
            var27 = this.fGrammarBucket.getGrammar(var7.fTargetNamespace);
         }

         var27.addDocument((Object)null, (String)this.fDoc2SystemId.get(var7.fSchemaElement));
         this.fDoc2XSDocumentMap.put(var1, var7);
         Vector var28 = new Vector();
         Element var11 = null;

         for(Element var12 = DOMUtil.getFirstChildElement(var1); var12 != null; var12 = DOMUtil.getNextSiblingElement(var12)) {
            String var13 = null;
            String var14 = null;
            String var15 = DOMUtil.getLocalName(var12);
            boolean var16 = true;
            boolean var17 = false;
            if (!var15.equals(SchemaSymbols.ELT_ANNOTATION)) {
               Object[] var18;
               Element var19;
               String var20;
               if (var15.equals(SchemaSymbols.ELT_IMPORT)) {
                  var16 = true;
                  var18 = this.fAttributeChecker.checkAttributes(var12, true, var7);
                  var14 = (String)var18[XSAttributeChecker.ATTIDX_SCHEMALOCATION];
                  var13 = (String)var18[XSAttributeChecker.ATTIDX_NAMESPACE];
                  if (var13 != null) {
                     var13 = this.fSymbolTable.addSymbol(var13);
                  }

                  var19 = DOMUtil.getFirstChildElement(var12);
                  if (var19 != null) {
                     var20 = DOMUtil.getLocalName(var19);
                     if (var20.equals(SchemaSymbols.ELT_ANNOTATION)) {
                        var27.addAnnotation(this.fElementTraverser.traverseAnnotationDecl(var19, var18, true, var7));
                     } else {
                        this.reportSchemaError("s4s-elt-must-match.1", new Object[]{var15, "annotation?", var20}, var12);
                     }

                     if (DOMUtil.getNextSiblingElement(var19) != null) {
                        this.reportSchemaError("s4s-elt-must-match.1", new Object[]{var15, "annotation?", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(var19))}, var12);
                     }
                  } else {
                     var20 = DOMUtil.getSyntheticAnnotation(var12);
                     if (var20 != null) {
                        var27.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(var12, var20, var18, true, var7));
                     }
                  }

                  this.fAttributeChecker.returnAttrArray(var18, var7);
                  if (var13 == var7.fTargetNamespace) {
                     this.reportSchemaError(var13 != null ? "src-import.1.1" : "src-import.1.2", new Object[]{var13}, var12);
                     continue;
                  }

                  if (var7.isAllowedNS(var13)) {
                     if (!this.fHonourAllSchemaLocations && !this.fNamespaceGrowth) {
                        continue;
                     }
                  } else {
                     var7.addAllowedNS(var13);
                  }

                  var20 = this.null2EmptyString(var7.fTargetNamespace);
                  Vector var21 = (Vector)this.fImportMap.get(var20);
                  if (var21 == null) {
                     this.fAllTNSs.addElement(var20);
                     var21 = new Vector();
                     this.fImportMap.put(var20, var21);
                     var21.addElement(var13);
                  } else if (!var21.contains(var13)) {
                     var21.addElement(var13);
                  }

                  this.fSchemaGrammarDescription.reset();
                  this.fSchemaGrammarDescription.setContextType((short)2);
                  this.fSchemaGrammarDescription.setBaseSystemId(this.doc2SystemId(var1));
                  this.fSchemaGrammarDescription.setLiteralSystemId(var14);
                  this.fSchemaGrammarDescription.setLocationHints(new String[]{var14});
                  this.fSchemaGrammarDescription.setTargetNamespace(var13);
                  SchemaGrammar var22 = this.findGrammar(this.fSchemaGrammarDescription, this.fNamespaceGrowth);
                  if (var22 != null) {
                     if (this.fNamespaceGrowth) {
                        try {
                           if (var22.getDocumentLocations().contains(XMLEntityManager.expandSystemId(var14, this.fSchemaGrammarDescription.getBaseSystemId(), false))) {
                              continue;
                           }

                           var17 = true;
                        } catch (URI.MalformedURIException var26) {
                        }
                     } else if (!this.fHonourAllSchemaLocations || this.isExistingGrammar(this.fSchemaGrammarDescription, false)) {
                        continue;
                     }
                  }

                  var11 = this.resolveSchema(this.fSchemaGrammarDescription, false, var12, var22 == null);
               } else {
                  if (!var15.equals(SchemaSymbols.ELT_INCLUDE) && !var15.equals(SchemaSymbols.ELT_REDEFINE)) {
                     break;
                  }

                  var18 = this.fAttributeChecker.checkAttributes(var12, true, var7);
                  var14 = (String)var18[XSAttributeChecker.ATTIDX_SCHEMALOCATION];
                  if (var15.equals(SchemaSymbols.ELT_REDEFINE)) {
                     this.fRedefine2NSSupport.put(var12, new SchemaNamespaceSupport(var7.fNamespaceSupport));
                  }

                  if (var15.equals(SchemaSymbols.ELT_INCLUDE)) {
                     var19 = DOMUtil.getFirstChildElement(var12);
                     if (var19 != null) {
                        var20 = DOMUtil.getLocalName(var19);
                        if (var20.equals(SchemaSymbols.ELT_ANNOTATION)) {
                           var27.addAnnotation(this.fElementTraverser.traverseAnnotationDecl(var19, var18, true, var7));
                        } else {
                           this.reportSchemaError("s4s-elt-must-match.1", new Object[]{var15, "annotation?", var20}, var12);
                        }

                        if (DOMUtil.getNextSiblingElement(var19) != null) {
                           this.reportSchemaError("s4s-elt-must-match.1", new Object[]{var15, "annotation?", DOMUtil.getLocalName(DOMUtil.getNextSiblingElement(var19))}, var12);
                        }
                     } else {
                        var20 = DOMUtil.getSyntheticAnnotation(var12);
                        if (var20 != null) {
                           var27.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(var12, var20, var18, true, var7));
                        }
                     }
                  } else {
                     for(var19 = DOMUtil.getFirstChildElement(var12); var19 != null; var19 = DOMUtil.getNextSiblingElement(var19)) {
                        var20 = DOMUtil.getLocalName(var19);
                        if (var20.equals(SchemaSymbols.ELT_ANNOTATION)) {
                           var27.addAnnotation(this.fElementTraverser.traverseAnnotationDecl(var19, var18, true, var7));
                           DOMUtil.setHidden(var19, this.fHiddenNodes);
                        } else {
                           String var32 = DOMUtil.getSyntheticAnnotation(var12);
                           if (var32 != null) {
                              var27.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(var12, var32, var18, true, var7));
                           }
                        }
                     }
                  }

                  this.fAttributeChecker.returnAttrArray(var18, var7);
                  if (var14 == null) {
                     this.reportSchemaError("s4s-att-must-appear", new Object[]{"<include> or <redefine>", "schemaLocation"}, var12);
                  }

                  boolean var31 = false;
                  byte var29 = 0;
                  if (var15.equals(SchemaSymbols.ELT_REDEFINE)) {
                     var31 = this.nonAnnotationContent(var12);
                     var29 = 1;
                  }

                  this.fSchemaGrammarDescription.reset();
                  this.fSchemaGrammarDescription.setContextType(var29);
                  this.fSchemaGrammarDescription.setBaseSystemId(this.doc2SystemId(var1));
                  this.fSchemaGrammarDescription.setLocationHints(new String[]{var14});
                  this.fSchemaGrammarDescription.setTargetNamespace(var5);
                  boolean var33 = false;
                  XMLInputSource var34 = this.resolveSchemaSource(this.fSchemaGrammarDescription, var31, var12, true);
                  if (this.fNamespaceGrowth && var29 == 0) {
                     try {
                        String var35 = XMLEntityManager.expandSystemId(var34.getSystemId(), var34.getBaseSystemId(), false);
                        var33 = var27.getDocumentLocations().contains(var35);
                     } catch (URI.MalformedURIException var24) {
                     }
                  }

                  if (!var33) {
                     var11 = this.resolveSchema(var34, this.fSchemaGrammarDescription, var31, var12);
                     var13 = var7.fTargetNamespace;
                  } else {
                     this.fLastSchemaWasDuplicate = true;
                  }
               }

               var18 = null;
               XSDocumentInfo var30;
               if (this.fLastSchemaWasDuplicate) {
                  var30 = var11 == null ? null : (XSDocumentInfo)this.fDoc2XSDocumentMap.get(var11);
               } else {
                  var30 = this.constructTrees(var11, var14, this.fSchemaGrammarDescription, var17);
               }

               if (var15.equals(SchemaSymbols.ELT_REDEFINE) && var30 != null) {
                  this.fRedefine2XSDMap.put(var12, var30);
               }

               if (var11 != null) {
                  if (var30 != null) {
                     var28.addElement(var30);
                  }

                  var11 = null;
               }
            }
         }

         this.fDependencyMap.put(var7, var28);
         return var7;
      }
   }

   private boolean isExistingGrammar(XSDDescription var1, boolean var2) {
      SchemaGrammar var3 = this.fGrammarBucket.getGrammar(var1.getTargetNamespace());
      if (var3 == null) {
         return this.findGrammar(var1, var2) != null;
      } else if (var3.isImmutable()) {
         return true;
      } else {
         try {
            return var3.getDocumentLocations().contains(XMLEntityManager.expandSystemId(var1.getLiteralSystemId(), var1.getBaseSystemId(), false));
         } catch (URI.MalformedURIException var5) {
            return false;
         }
      }
   }

   private void updateImportListFor(SchemaGrammar var1) {
      Vector var2 = var1.getImportedGrammars();
      if (var2 != null) {
         for(int var3 = 0; var3 < var2.size(); ++var3) {
            SchemaGrammar var4 = (SchemaGrammar)var2.elementAt(var3);
            SchemaGrammar var5 = this.fGrammarBucket.getGrammar(var4.getTargetNamespace());
            if (var5 != null && var4 != var5) {
               var2.set(var3, var5);
            }
         }
      }

   }

   private void updateImportListWith(SchemaGrammar var1) {
      SchemaGrammar[] var2 = this.fGrammarBucket.getGrammars();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         SchemaGrammar var4 = var2[var3];
         if (var4 != var1) {
            Vector var5 = var4.getImportedGrammars();
            if (var5 != null) {
               for(int var6 = 0; var6 < var5.size(); ++var6) {
                  SchemaGrammar var7 = (SchemaGrammar)var5.elementAt(var6);
                  if (this.null2EmptyString(var7.getTargetNamespace()).equals(this.null2EmptyString(var1.getTargetNamespace()))) {
                     if (var7 != var1) {
                        var5.set(var6, var1);
                     }
                     break;
                  }
               }
            }
         }
      }

   }

   protected void buildGlobalNameRegistries() {
      Stack var1 = new Stack();
      var1.push(this.fRoot);

      while(true) {
         XSDocumentInfo var2;
         Element var3;
         do {
            if (var1.empty()) {
               return;
            }

            var2 = (XSDocumentInfo)var1.pop();
            var3 = var2.fSchemaElement;
         } while(DOMUtil.isHidden(var3, this.fHiddenNodes));

         boolean var5 = true;

         for(Element var6 = DOMUtil.getFirstChildElement(var3); var6 != null; var6 = DOMUtil.getNextSiblingElement(var6)) {
            if (!DOMUtil.getLocalName(var6).equals(SchemaSymbols.ELT_ANNOTATION)) {
               if (!DOMUtil.getLocalName(var6).equals(SchemaSymbols.ELT_INCLUDE) && !DOMUtil.getLocalName(var6).equals(SchemaSymbols.ELT_IMPORT)) {
                  String var8;
                  String var9;
                  if (!DOMUtil.getLocalName(var6).equals(SchemaSymbols.ELT_REDEFINE)) {
                     var5 = false;
                     String var12 = DOMUtil.getAttrValue(var6, SchemaSymbols.ATT_NAME);
                     if (var12.length() != 0) {
                        var8 = var2.fTargetNamespace == null ? "," + var12 : var2.fTargetNamespace + "," + var12;
                        var8 = XMLChar.trim(var8);
                        var9 = DOMUtil.getLocalName(var6);
                        if (var9.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
                           this.checkForDuplicateNames(var8, 1, this.fUnparsedAttributeRegistry, this.fUnparsedAttributeRegistrySub, var6, var2);
                        } else if (var9.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                           this.checkForDuplicateNames(var8, 2, this.fUnparsedAttributeGroupRegistry, this.fUnparsedAttributeGroupRegistrySub, var6, var2);
                        } else if (!var9.equals(SchemaSymbols.ELT_COMPLEXTYPE) && !var9.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                           if (var9.equals(SchemaSymbols.ELT_ELEMENT)) {
                              this.checkForDuplicateNames(var8, 3, this.fUnparsedElementRegistry, this.fUnparsedElementRegistrySub, var6, var2);
                           } else if (var9.equals(SchemaSymbols.ELT_GROUP)) {
                              this.checkForDuplicateNames(var8, 4, this.fUnparsedGroupRegistry, this.fUnparsedGroupRegistrySub, var6, var2);
                           } else if (var9.equals(SchemaSymbols.ELT_NOTATION)) {
                              this.checkForDuplicateNames(var8, 6, this.fUnparsedNotationRegistry, this.fUnparsedNotationRegistrySub, var6, var2);
                           }
                        } else {
                           this.checkForDuplicateNames(var8, 7, this.fUnparsedTypeRegistry, this.fUnparsedTypeRegistrySub, var6, var2);
                        }
                     }
                  } else {
                     if (!var5) {
                        this.reportSchemaError("s4s-elt-invalid-content.3", new Object[]{DOMUtil.getLocalName(var6)}, var6);
                     }

                     for(Element var7 = DOMUtil.getFirstChildElement(var6); var7 != null; var7 = DOMUtil.getNextSiblingElement(var7)) {
                        var8 = DOMUtil.getAttrValue(var7, SchemaSymbols.ATT_NAME);
                        if (var8.length() != 0) {
                           var9 = var2.fTargetNamespace == null ? "," + var8 : var2.fTargetNamespace + "," + var8;
                           var9 = XMLChar.trim(var9);
                           String var10 = DOMUtil.getLocalName(var7);
                           String var11;
                           if (var10.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                              this.checkForDuplicateNames(var9, 2, this.fUnparsedAttributeGroupRegistry, this.fUnparsedAttributeGroupRegistrySub, var7, var2);
                              var11 = DOMUtil.getAttrValue(var7, SchemaSymbols.ATT_NAME) + "_fn3dktizrknc9pi";
                              this.renameRedefiningComponents(var2, var7, SchemaSymbols.ELT_ATTRIBUTEGROUP, var8, var11);
                           } else if (!var10.equals(SchemaSymbols.ELT_COMPLEXTYPE) && !var10.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                              if (var10.equals(SchemaSymbols.ELT_GROUP)) {
                                 this.checkForDuplicateNames(var9, 4, this.fUnparsedGroupRegistry, this.fUnparsedGroupRegistrySub, var7, var2);
                                 var11 = DOMUtil.getAttrValue(var7, SchemaSymbols.ATT_NAME) + "_fn3dktizrknc9pi";
                                 this.renameRedefiningComponents(var2, var7, SchemaSymbols.ELT_GROUP, var8, var11);
                              }
                           } else {
                              this.checkForDuplicateNames(var9, 7, this.fUnparsedTypeRegistry, this.fUnparsedTypeRegistrySub, var7, var2);
                              var11 = DOMUtil.getAttrValue(var7, SchemaSymbols.ATT_NAME) + "_fn3dktizrknc9pi";
                              if (var10.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                                 this.renameRedefiningComponents(var2, var7, SchemaSymbols.ELT_COMPLEXTYPE, var8, var11);
                              } else {
                                 this.renameRedefiningComponents(var2, var7, SchemaSymbols.ELT_SIMPLETYPE, var8, var11);
                              }
                           }
                        }
                     }
                  }
               } else {
                  if (!var5) {
                     this.reportSchemaError("s4s-elt-invalid-content.3", new Object[]{DOMUtil.getLocalName(var6)}, var6);
                  }

                  DOMUtil.setHidden(var6, this.fHiddenNodes);
               }
            }
         }

         DOMUtil.setHidden(var3, this.fHiddenNodes);
         Vector var13 = (Vector)this.fDependencyMap.get(var2);

         for(int var14 = 0; var14 < var13.size(); ++var14) {
            var1.push(var13.elementAt(var14));
         }
      }
   }

   protected void traverseSchemas(ArrayList var1) {
      this.setSchemasVisible(this.fRoot);
      Stack var2 = new Stack();
      var2.push(this.fRoot);

      while(true) {
         XSDocumentInfo var3;
         Element var4;
         SchemaGrammar var5;
         do {
            if (var2.empty()) {
               return;
            }

            var3 = (XSDocumentInfo)var2.pop();
            var4 = var3.fSchemaElement;
            var5 = this.fGrammarBucket.getGrammar(var3.fTargetNamespace);
         } while(DOMUtil.isHidden(var4, this.fHiddenNodes));

         boolean var7 = false;

         String var9;
         for(Element var8 = DOMUtil.getFirstVisibleChildElement(var4, this.fHiddenNodes); var8 != null; var8 = DOMUtil.getNextVisibleSiblingElement(var8, this.fHiddenNodes)) {
            DOMUtil.setHidden(var8, this.fHiddenNodes);
            var9 = DOMUtil.getLocalName(var8);
            if (!DOMUtil.getLocalName(var8).equals(SchemaSymbols.ELT_REDEFINE)) {
               if (var9.equals(SchemaSymbols.ELT_ATTRIBUTE)) {
                  this.fAttributeTraverser.traverseGlobal(var8, var3, var5);
               } else if (var9.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                  this.fAttributeGroupTraverser.traverseGlobal(var8, var3, var5);
               } else if (var9.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                  this.fComplexTypeTraverser.traverseGlobal(var8, var3, var5);
               } else if (var9.equals(SchemaSymbols.ELT_ELEMENT)) {
                  this.fElementTraverser.traverseGlobal(var8, var3, var5);
               } else if (var9.equals(SchemaSymbols.ELT_GROUP)) {
                  this.fGroupTraverser.traverseGlobal(var8, var3, var5);
               } else if (var9.equals(SchemaSymbols.ELT_NOTATION)) {
                  this.fNotationTraverser.traverse(var8, var3, var5);
               } else if (var9.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                  this.fSimpleTypeTraverser.traverseGlobal(var8, var3, var5);
               } else if (var9.equals(SchemaSymbols.ELT_ANNOTATION)) {
                  var5.addAnnotation(this.fElementTraverser.traverseAnnotationDecl(var8, var3.getSchemaAttrs(), true, var3));
                  var7 = true;
               } else {
                  this.reportSchemaError("s4s-elt-invalid-content.1", new Object[]{SchemaSymbols.ELT_SCHEMA, DOMUtil.getLocalName(var8)}, var8);
               }
            } else {
               var3.backupNSSupport((SchemaNamespaceSupport)this.fRedefine2NSSupport.get(var8));

               for(Element var10 = DOMUtil.getFirstVisibleChildElement(var8, this.fHiddenNodes); var10 != null; var10 = DOMUtil.getNextVisibleSiblingElement(var10, this.fHiddenNodes)) {
                  String var11 = DOMUtil.getLocalName(var10);
                  DOMUtil.setHidden(var10, this.fHiddenNodes);
                  if (var11.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
                     this.fAttributeGroupTraverser.traverseGlobal(var10, var3, var5);
                  } else if (var11.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
                     this.fComplexTypeTraverser.traverseGlobal(var10, var3, var5);
                  } else if (var11.equals(SchemaSymbols.ELT_GROUP)) {
                     this.fGroupTraverser.traverseGlobal(var10, var3, var5);
                  } else if (var11.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
                     this.fSimpleTypeTraverser.traverseGlobal(var10, var3, var5);
                  } else {
                     this.reportSchemaError("s4s-elt-must-match.1", new Object[]{DOMUtil.getLocalName(var8), "(annotation | (simpleType | complexType | group | attributeGroup))*", var11}, var10);
                  }
               }

               var3.restoreNSSupport();
            }
         }

         if (!var7) {
            var9 = DOMUtil.getSyntheticAnnotation(var4);
            if (var9 != null) {
               var5.addAnnotation(this.fElementTraverser.traverseSyntheticAnnotation(var4, var9, var3.getSchemaAttrs(), true, var3));
            }
         }

         if (var1 != null) {
            XSAnnotationInfo var12 = var3.getAnnotations();
            if (var12 != null) {
               var1.add(this.doc2SystemId(var4));
               var1.add(var12);
            }
         }

         var3.returnSchemaAttrs();
         DOMUtil.setHidden(var4, this.fHiddenNodes);
         Vector var13 = (Vector)this.fDependencyMap.get(var3);

         for(int var14 = 0; var14 < var13.size(); ++var14) {
            var2.push(var13.elementAt(var14));
         }
      }
   }

   private final boolean needReportTNSError(String var1) {
      if (this.fReportedTNS == null) {
         this.fReportedTNS = new Vector();
      } else if (this.fReportedTNS.contains(var1)) {
         return false;
      }

      this.fReportedTNS.addElement(var1);
      return true;
   }

   void addGlobalAttributeDecl(XSAttributeDecl var1) {
      String var2 = var1.getNamespace();
      String var3 = var2 != null && var2.length() != 0 ? var2 + "," + var1.getName() : "," + var1.getName();
      if (this.fGlobalAttrDecls.get(var3) == null) {
         this.fGlobalAttrDecls.put(var3, var1);
      }

   }

   void addGlobalAttributeGroupDecl(XSAttributeGroupDecl var1) {
      String var2 = var1.getNamespace();
      String var3 = var2 != null && var2.length() != 0 ? var2 + "," + var1.getName() : "," + var1.getName();
      if (this.fGlobalAttrGrpDecls.get(var3) == null) {
         this.fGlobalAttrGrpDecls.put(var3, var1);
      }

   }

   void addGlobalElementDecl(XSElementDecl var1) {
      String var2 = var1.getNamespace();
      String var3 = var2 != null && var2.length() != 0 ? var2 + "," + var1.getName() : "," + var1.getName();
      if (this.fGlobalElemDecls.get(var3) == null) {
         this.fGlobalElemDecls.put(var3, var1);
      }

   }

   void addGlobalGroupDecl(XSGroupDecl var1) {
      String var2 = var1.getNamespace();
      String var3 = var2 != null && var2.length() != 0 ? var2 + "," + var1.getName() : "," + var1.getName();
      if (this.fGlobalGroupDecls.get(var3) == null) {
         this.fGlobalGroupDecls.put(var3, var1);
      }

   }

   void addGlobalNotationDecl(XSNotationDecl var1) {
      String var2 = var1.getNamespace();
      String var3 = var2 != null && var2.length() != 0 ? var2 + "," + var1.getName() : "," + var1.getName();
      if (this.fGlobalNotationDecls.get(var3) == null) {
         this.fGlobalNotationDecls.put(var3, var1);
      }

   }

   void addGlobalTypeDecl(XSTypeDefinition var1) {
      String var2 = var1.getNamespace();
      String var3 = var2 != null && var2.length() != 0 ? var2 + "," + var1.getName() : "," + var1.getName();
      if (this.fGlobalTypeDecls.get(var3) == null) {
         this.fGlobalTypeDecls.put(var3, var1);
      }

   }

   void addIDConstraintDecl(IdentityConstraint var1) {
      String var2 = var1.getNamespace();
      String var3 = var2 != null && var2.length() != 0 ? var2 + "," + var1.getIdentityConstraintName() : "," + var1.getIdentityConstraintName();
      if (this.fGlobalIDConstraintDecls.get(var3) == null) {
         this.fGlobalIDConstraintDecls.put(var3, var1);
      }

   }

   private XSAttributeDecl getGlobalAttributeDecl(String var1) {
      return (XSAttributeDecl)this.fGlobalAttrDecls.get(var1);
   }

   private XSAttributeGroupDecl getGlobalAttributeGroupDecl(String var1) {
      return (XSAttributeGroupDecl)this.fGlobalAttrGrpDecls.get(var1);
   }

   private XSElementDecl getGlobalElementDecl(String var1) {
      return (XSElementDecl)this.fGlobalElemDecls.get(var1);
   }

   private XSGroupDecl getGlobalGroupDecl(String var1) {
      return (XSGroupDecl)this.fGlobalGroupDecls.get(var1);
   }

   private XSNotationDecl getGlobalNotationDecl(String var1) {
      return (XSNotationDecl)this.fGlobalNotationDecls.get(var1);
   }

   private XSTypeDefinition getGlobalTypeDecl(String var1) {
      return (XSTypeDefinition)this.fGlobalTypeDecls.get(var1);
   }

   private IdentityConstraint getIDConstraintDecl(String var1) {
      return (IdentityConstraint)this.fGlobalIDConstraintDecls.get(var1);
   }

   protected Object getGlobalDecl(XSDocumentInfo var1, int var2, QName var3, Element var4) {
      if (var3.uri != null && var3.uri == SchemaSymbols.URI_SCHEMAFORSCHEMA && var2 == 7) {
         XSTypeDefinition var5 = SchemaGrammar.SG_SchemaNS.getGlobalTypeDecl(var3.localpart);
         if (var5 != null) {
            return var5;
         }
      }

      if (!var1.isAllowedNS(var3.uri) && var1.needReportTNSError(var3.uri)) {
         String var12 = var3.uri == null ? "src-resolve.4.1" : "src-resolve.4.2";
         this.reportSchemaError(var12, new Object[]{this.fDoc2SystemId.get(var1.fSchemaElement), var3.uri, var3.rawname}, var4);
      }

      SchemaGrammar var13 = this.fGrammarBucket.getGrammar(var3.uri);
      if (var13 == null) {
         if (this.needReportTNSError(var3.uri)) {
            this.reportSchemaError("src-resolve", new Object[]{var3.rawname, COMP_TYPE[var2]}, var4);
         }

         return null;
      } else {
         Object var6 = this.getGlobalDeclFromGrammar(var13, var2, var3.localpart);
         String var7 = var3.uri == null ? "," + var3.localpart : var3.uri + "," + var3.localpart;
         Object var8;
         if (!this.fTolerateDuplicates) {
            if (var6 != null) {
               return var6;
            }
         } else {
            var8 = this.getGlobalDecl(var7, var2);
            if (var8 != null) {
               return var8;
            }
         }

         var8 = null;
         Element var9 = null;
         XSDocumentInfo var10 = null;
         switch (var2) {
            case 1:
               var9 = (Element)this.fUnparsedAttributeRegistry.get(var7);
               var10 = (XSDocumentInfo)this.fUnparsedAttributeRegistrySub.get(var7);
               break;
            case 2:
               var9 = (Element)this.fUnparsedAttributeGroupRegistry.get(var7);
               var10 = (XSDocumentInfo)this.fUnparsedAttributeGroupRegistrySub.get(var7);
               break;
            case 3:
               var9 = (Element)this.fUnparsedElementRegistry.get(var7);
               var10 = (XSDocumentInfo)this.fUnparsedElementRegistrySub.get(var7);
               break;
            case 4:
               var9 = (Element)this.fUnparsedGroupRegistry.get(var7);
               var10 = (XSDocumentInfo)this.fUnparsedGroupRegistrySub.get(var7);
               break;
            case 5:
               var9 = (Element)this.fUnparsedIdentityConstraintRegistry.get(var7);
               var10 = (XSDocumentInfo)this.fUnparsedIdentityConstraintRegistrySub.get(var7);
               break;
            case 6:
               var9 = (Element)this.fUnparsedNotationRegistry.get(var7);
               var10 = (XSDocumentInfo)this.fUnparsedNotationRegistrySub.get(var7);
               break;
            case 7:
               var9 = (Element)this.fUnparsedTypeRegistry.get(var7);
               var10 = (XSDocumentInfo)this.fUnparsedTypeRegistrySub.get(var7);
               break;
            default:
               this.reportSchemaError("Internal-Error", new Object[]{"XSDHandler asked to locate component of type " + var2 + "; it does not recognize this type!"}, var4);
         }

         if (var9 == null) {
            if (var6 == null) {
               this.reportSchemaError("src-resolve", new Object[]{var3.rawname, COMP_TYPE[var2]}, var4);
            }

            return var6;
         } else {
            XSDocumentInfo var14 = this.findXSDocumentForDecl(var1, var9, var10);
            String var11;
            if (var14 == null) {
               if (var6 == null) {
                  var11 = var3.uri == null ? "src-resolve.4.1" : "src-resolve.4.2";
                  this.reportSchemaError(var11, new Object[]{this.fDoc2SystemId.get(var1.fSchemaElement), var3.uri, var3.rawname}, var4);
               }

               return var6;
            } else if (DOMUtil.isHidden(var9, this.fHiddenNodes)) {
               if (var6 == null) {
                  var11 = CIRCULAR_CODES[var2];
                  if (var2 == 7 && SchemaSymbols.ELT_COMPLEXTYPE.equals(DOMUtil.getLocalName(var9))) {
                     var11 = "ct-props-correct.3";
                  }

                  this.reportSchemaError(var11, new Object[]{var3.prefix + ":" + var3.localpart}, var4);
               }

               return var6;
            } else {
               return this.traverseGlobalDecl(var2, var9, var14, var13);
            }
         }
      }
   }

   protected Object getGlobalDecl(String var1, int var2) {
      Object var3 = null;
      switch (var2) {
         case 1:
            var3 = this.getGlobalAttributeDecl(var1);
            break;
         case 2:
            var3 = this.getGlobalAttributeGroupDecl(var1);
            break;
         case 3:
            var3 = this.getGlobalElementDecl(var1);
            break;
         case 4:
            var3 = this.getGlobalGroupDecl(var1);
            break;
         case 5:
            var3 = this.getIDConstraintDecl(var1);
            break;
         case 6:
            var3 = this.getGlobalNotationDecl(var1);
            break;
         case 7:
            var3 = this.getGlobalTypeDecl(var1);
      }

      return var3;
   }

   protected Object getGlobalDeclFromGrammar(SchemaGrammar var1, int var2, String var3) {
      Object var4 = null;
      switch (var2) {
         case 1:
            var4 = var1.getGlobalAttributeDecl(var3);
            break;
         case 2:
            var4 = var1.getGlobalAttributeGroupDecl(var3);
            break;
         case 3:
            var4 = var1.getGlobalElementDecl(var3);
            break;
         case 4:
            var4 = var1.getGlobalGroupDecl(var3);
            break;
         case 5:
            var4 = var1.getIDConstraintDecl(var3);
            break;
         case 6:
            var4 = var1.getGlobalNotationDecl(var3);
            break;
         case 7:
            var4 = var1.getGlobalTypeDecl(var3);
      }

      return var4;
   }

   protected Object getGlobalDeclFromGrammar(SchemaGrammar var1, int var2, String var3, String var4) {
      Object var5 = null;
      switch (var2) {
         case 1:
            var5 = var1.getGlobalAttributeDecl(var3, var4);
            break;
         case 2:
            var5 = var1.getGlobalAttributeGroupDecl(var3, var4);
            break;
         case 3:
            var5 = var1.getGlobalElementDecl(var3, var4);
            break;
         case 4:
            var5 = var1.getGlobalGroupDecl(var3, var4);
            break;
         case 5:
            var5 = var1.getIDConstraintDecl(var3, var4);
            break;
         case 6:
            var5 = var1.getGlobalNotationDecl(var3, var4);
            break;
         case 7:
            var5 = var1.getGlobalTypeDecl(var3, var4);
      }

      return var5;
   }

   protected Object traverseGlobalDecl(int var1, Element var2, XSDocumentInfo var3, SchemaGrammar var4) {
      Object var5 = null;
      DOMUtil.setHidden(var2, this.fHiddenNodes);
      SchemaNamespaceSupport var6 = null;
      Element var7 = DOMUtil.getParent(var2);
      if (DOMUtil.getLocalName(var7).equals(SchemaSymbols.ELT_REDEFINE)) {
         var6 = (SchemaNamespaceSupport)this.fRedefine2NSSupport.get(var7);
      }

      var3.backupNSSupport(var6);
      switch (var1) {
         case 1:
            var5 = this.fAttributeTraverser.traverseGlobal(var2, var3, var4);
            break;
         case 2:
            var5 = this.fAttributeGroupTraverser.traverseGlobal(var2, var3, var4);
            break;
         case 3:
            var5 = this.fElementTraverser.traverseGlobal(var2, var3, var4);
            break;
         case 4:
            var5 = this.fGroupTraverser.traverseGlobal(var2, var3, var4);
         case 5:
         default:
            break;
         case 6:
            var5 = this.fNotationTraverser.traverse(var2, var3, var4);
            break;
         case 7:
            if (DOMUtil.getLocalName(var2).equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
               var5 = this.fComplexTypeTraverser.traverseGlobal(var2, var3, var4);
            } else {
               var5 = this.fSimpleTypeTraverser.traverseGlobal(var2, var3, var4);
            }
      }

      var3.restoreNSSupport();
      return var5;
   }

   public String schemaDocument2SystemId(XSDocumentInfo var1) {
      return (String)this.fDoc2SystemId.get(var1.fSchemaElement);
   }

   Object getGrpOrAttrGrpRedefinedByRestriction(int var1, QName var2, XSDocumentInfo var3, Element var4) {
      String var5 = var2.uri != null ? var2.uri + "," + var2.localpart : "," + var2.localpart;
      String var6 = null;
      switch (var1) {
         case 2:
            var6 = (String)this.fRedefinedRestrictedAttributeGroupRegistry.get(var5);
            break;
         case 4:
            var6 = (String)this.fRedefinedRestrictedGroupRegistry.get(var5);
            break;
         default:
            return null;
      }

      if (var6 == null) {
         return null;
      } else {
         int var7 = var6.indexOf(",");
         QName var8 = new QName(XMLSymbols.EMPTY_STRING, var6.substring(var7 + 1), var6.substring(var7), var7 == 0 ? null : var6.substring(0, var7));
         Object var9 = this.getGlobalDecl(var3, var1, var8, var4);
         if (var9 == null) {
            switch (var1) {
               case 2:
                  this.reportSchemaError("src-redefine.7.2.1", new Object[]{var2.localpart}, var4);
                  break;
               case 4:
                  this.reportSchemaError("src-redefine.6.2.1", new Object[]{var2.localpart}, var4);
            }

            return null;
         } else {
            return var9;
         }
      }
   }

   protected void resolveKeyRefs() {
      for(int var1 = 0; var1 < this.fKeyrefStackPos; ++var1) {
         XSDocumentInfo var2 = this.fKeyrefsMapXSDocumentInfo[var1];
         var2.fNamespaceSupport.makeGlobal();
         var2.fNamespaceSupport.setEffectiveContext(this.fKeyrefNamespaceContext[var1]);
         SchemaGrammar var3 = this.fGrammarBucket.getGrammar(var2.fTargetNamespace);
         DOMUtil.setHidden(this.fKeyrefs[var1], this.fHiddenNodes);
         this.fKeyrefTraverser.traverse(this.fKeyrefs[var1], this.fKeyrefElems[var1], var2, var3);
      }

   }

   protected Hashtable getIDRegistry() {
      return this.fUnparsedIdentityConstraintRegistry;
   }

   protected Hashtable getIDRegistry_sub() {
      return this.fUnparsedIdentityConstraintRegistrySub;
   }

   protected void storeKeyRef(Element var1, XSDocumentInfo var2, XSElementDecl var3) {
      String var4 = DOMUtil.getAttrValue(var1, SchemaSymbols.ATT_NAME);
      if (var4.length() != 0) {
         String var5 = var2.fTargetNamespace == null ? "," + var4 : var2.fTargetNamespace + "," + var4;
         this.checkForDuplicateNames(var5, 5, this.fUnparsedIdentityConstraintRegistry, this.fUnparsedIdentityConstraintRegistrySub, var1, var2);
      }

      if (this.fKeyrefStackPos == this.fKeyrefs.length) {
         Element[] var9 = new Element[this.fKeyrefStackPos + 2];
         System.arraycopy(this.fKeyrefs, 0, var9, 0, this.fKeyrefStackPos);
         this.fKeyrefs = var9;
         XSElementDecl[] var6 = new XSElementDecl[this.fKeyrefStackPos + 2];
         System.arraycopy(this.fKeyrefElems, 0, var6, 0, this.fKeyrefStackPos);
         this.fKeyrefElems = var6;
         String[][] var7 = new String[this.fKeyrefStackPos + 2][];
         System.arraycopy(this.fKeyrefNamespaceContext, 0, var7, 0, this.fKeyrefStackPos);
         this.fKeyrefNamespaceContext = var7;
         XSDocumentInfo[] var8 = new XSDocumentInfo[this.fKeyrefStackPos + 2];
         System.arraycopy(this.fKeyrefsMapXSDocumentInfo, 0, var8, 0, this.fKeyrefStackPos);
         this.fKeyrefsMapXSDocumentInfo = var8;
      }

      this.fKeyrefs[this.fKeyrefStackPos] = var1;
      this.fKeyrefElems[this.fKeyrefStackPos] = var3;
      this.fKeyrefNamespaceContext[this.fKeyrefStackPos] = var2.fNamespaceSupport.getEffectiveLocalContext();
      this.fKeyrefsMapXSDocumentInfo[this.fKeyrefStackPos++] = var2;
   }

   private Element resolveSchema(XSDDescription var1, boolean var2, Element var3, boolean var4) {
      XMLInputSource var5 = null;

      try {
         Hashtable var6 = var4 ? this.fLocationPairs : EMPTY_TABLE;
         var5 = XMLSchemaLoader.resolveDocument(var1, var6, this.fEntityResolver);
      } catch (IOException var7) {
         if (var2) {
            this.reportSchemaError("schema_reference.4", new Object[]{var1.getLocationHints()[0]}, var3);
         } else {
            this.reportSchemaWarning("schema_reference.4", new Object[]{var1.getLocationHints()[0]}, var3);
         }
      }

      if (var5 instanceof DOMInputSource) {
         return this.getSchemaDocument(var1.getTargetNamespace(), (DOMInputSource)var5, var2, var1.getContextType(), var3);
      } else if (var5 instanceof SAXInputSource) {
         return this.getSchemaDocument(var1.getTargetNamespace(), (SAXInputSource)var5, var2, var1.getContextType(), var3);
      } else if (var5 instanceof StAXInputSource) {
         return this.getSchemaDocument(var1.getTargetNamespace(), (StAXInputSource)var5, var2, var1.getContextType(), var3);
      } else {
         return var5 instanceof XSInputSource ? this.getSchemaDocument((XSInputSource)var5, var1) : this.getSchemaDocument(var1.getTargetNamespace(), var5, var2, var1.getContextType(), var3);
      }
   }

   private Element resolveSchema(XMLInputSource var1, XSDDescription var2, boolean var3, Element var4) {
      if (var1 instanceof DOMInputSource) {
         return this.getSchemaDocument(var2.getTargetNamespace(), (DOMInputSource)var1, var3, var2.getContextType(), var4);
      } else if (var1 instanceof SAXInputSource) {
         return this.getSchemaDocument(var2.getTargetNamespace(), (SAXInputSource)var1, var3, var2.getContextType(), var4);
      } else if (var1 instanceof StAXInputSource) {
         return this.getSchemaDocument(var2.getTargetNamespace(), (StAXInputSource)var1, var3, var2.getContextType(), var4);
      } else {
         return var1 instanceof XSInputSource ? this.getSchemaDocument((XSInputSource)var1, var2) : this.getSchemaDocument(var2.getTargetNamespace(), var1, var3, var2.getContextType(), var4);
      }
   }

   private XMLInputSource resolveSchemaSource(XSDDescription var1, boolean var2, Element var3, boolean var4) {
      XMLInputSource var5 = null;

      try {
         Hashtable var6 = var4 ? this.fLocationPairs : EMPTY_TABLE;
         var5 = XMLSchemaLoader.resolveDocument(var1, var6, this.fEntityResolver);
      } catch (IOException var7) {
         if (var2) {
            this.reportSchemaError("schema_reference.4", new Object[]{var1.getLocationHints()[0]}, var3);
         } else {
            this.reportSchemaWarning("schema_reference.4", new Object[]{var1.getLocationHints()[0]}, var3);
         }
      }

      return var5;
   }

   private Element getSchemaDocument(String var1, XMLInputSource var2, boolean var3, short var4, Element var5) {
      boolean var6 = true;
      IOException var7 = null;
      Element var8 = null;

      try {
         if (var2 != null && (var2.getSystemId() != null || var2.getByteStream() != null || var2.getCharacterStream() != null)) {
            XSDKey var9 = null;
            String var10 = null;
            if (var4 != 3) {
               var10 = XMLEntityManager.expandSystemId(var2.getSystemId(), var2.getBaseSystemId(), false);
               var9 = new XSDKey(var10, var4, var1);
               if ((var8 = (Element)this.fTraversed.get(var9)) != null) {
                  this.fLastSchemaWasDuplicate = true;
                  return var8;
               }
            }

            this.fSchemaParser.parse(var2);
            Document var11 = this.fSchemaParser.getDocument();
            var8 = var11 != null ? DOMUtil.getRoot(var11) : null;
            return this.getSchemaDocument0(var9, var10, var8);
         }

         var6 = false;
      } catch (IOException var12) {
         var7 = var12;
      }

      return this.getSchemaDocument1(var3, var6, var2, var5, var7);
   }

   private Element getSchemaDocument(String var1, SAXInputSource var2, boolean var3, short var4, Element var5) {
      Object var6 = var2.getXMLReader();
      InputSource var7 = var2.getInputSource();
      boolean var8 = true;
      IOException var9 = null;
      Element var10 = null;

      try {
         if (var7 != null && (var7.getSystemId() != null || var7.getByteStream() != null || var7.getCharacterStream() != null)) {
            XSDKey var11 = null;
            String var12 = null;
            if (var4 != 3) {
               var12 = XMLEntityManager.expandSystemId(var7.getSystemId(), var2.getBaseSystemId(), false);
               var11 = new XSDKey(var12, var4, var1);
               if ((var10 = (Element)this.fTraversed.get(var11)) != null) {
                  this.fLastSchemaWasDuplicate = true;
                  return var10;
               }
            }

            boolean var13 = false;
            if (var6 != null) {
               try {
                  var13 = ((XMLReader)var6).getFeature("http://xml.org/sax/features/namespace-prefixes");
               } catch (SAXException var20) {
               }
            } else {
               try {
                  var6 = XMLReaderFactory.createXMLReader();
               } catch (SAXException var19) {
                  var6 = new SAXParser();
               }

               try {
                  ((XMLReader)var6).setFeature("http://xml.org/sax/features/namespace-prefixes", true);
                  var13 = true;
                  if (var6 instanceof SAXParser) {
                     Object var14 = this.fSchemaParser.getProperty("http://apache.org/xml/properties/security-manager");
                     if (var14 != null) {
                        ((XMLReader)var6).setProperty("http://apache.org/xml/properties/security-manager", var14);
                     }
                  }
               } catch (SAXException var18) {
               }
            }

            boolean var24 = false;

            try {
               var24 = ((XMLReader)var6).getFeature("http://xml.org/sax/features/string-interning");
            } catch (SAXException var17) {
            }

            if (this.fXSContentHandler == null) {
               this.fXSContentHandler = new SchemaContentHandler();
            }

            this.fXSContentHandler.reset(this.fSchemaParser, this.fSymbolTable, var13, var24);
            ((XMLReader)var6).setContentHandler(this.fXSContentHandler);
            ((XMLReader)var6).setErrorHandler(this.fErrorReporter.getSAXErrorHandler());
            ((XMLReader)var6).parse(var7);

            try {
               ((XMLReader)var6).setContentHandler((ContentHandler)null);
               ((XMLReader)var6).setErrorHandler((ErrorHandler)null);
            } catch (Exception var16) {
            }

            Document var15 = this.fXSContentHandler.getDocument();
            var10 = var15 != null ? DOMUtil.getRoot(var15) : null;
            return this.getSchemaDocument0(var11, var12, var10);
         }

         var8 = false;
      } catch (SAXParseException var21) {
         throw XSDHandler.SAX2XNIUtil.createXMLParseException0(var21);
      } catch (SAXException var22) {
         throw XSDHandler.SAX2XNIUtil.createXNIException0(var22);
      } catch (IOException var23) {
         var9 = var23;
      }

      return this.getSchemaDocument1(var3, var8, var2, var5, var9);
   }

   private Element getSchemaDocument(String var1, DOMInputSource var2, boolean var3, short var4, Element var5) {
      boolean var6 = true;
      IOException var7 = null;
      Element var8 = null;
      Element var9 = null;
      Node var10 = var2.getNode();
      short var11 = -1;
      if (var10 != null) {
         var11 = var10.getNodeType();
         if (var11 == 9) {
            var9 = DOMUtil.getRoot((Document)var10);
         } else if (var11 == 1) {
            var9 = (Element)var10;
         }
      }

      try {
         if (var9 != null) {
            XSDKey var12 = null;
            String var13 = null;
            if (var4 != 3) {
               var13 = XMLEntityManager.expandSystemId(var2.getSystemId(), var2.getBaseSystemId(), false);
               boolean var14 = var11 == 9;
               if (!var14) {
                  Node var15 = var9.getParentNode();
                  if (var15 != null) {
                     var14 = var15.getNodeType() == 9;
                  }
               }

               if (var14) {
                  var12 = new XSDKey(var13, var4, var1);
                  if ((var8 = (Element)this.fTraversed.get(var12)) != null) {
                     this.fLastSchemaWasDuplicate = true;
                     return var8;
                  }
               }
            }

            return this.getSchemaDocument0(var12, var13, var9);
         }

         var6 = false;
      } catch (IOException var16) {
         var7 = var16;
      }

      return this.getSchemaDocument1(var3, var6, var2, var5, var7);
   }

   private Element getSchemaDocument(String var1, StAXInputSource var2, boolean var3, short var4, Element var5) {
      IOException var6 = null;
      Element var7 = null;

      try {
         boolean var8 = var2.shouldConsumeRemainingContent();
         XMLStreamReader var16 = var2.getXMLStreamReader();
         XMLEventReader var17 = var2.getXMLEventReader();
         XSDKey var11 = null;
         String var12 = null;
         if (var4 != 3) {
            var12 = XMLEntityManager.expandSystemId(var2.getSystemId(), var2.getBaseSystemId(), false);
            boolean var13 = var8;
            if (!var8) {
               if (var16 != null) {
                  var13 = var16.getEventType() == 7;
               } else {
                  var13 = var17.peek().isStartDocument();
               }
            }

            if (var13) {
               var11 = new XSDKey(var12, var4, var1);
               if ((var7 = (Element)this.fTraversed.get(var11)) != null) {
                  this.fLastSchemaWasDuplicate = true;
                  return var7;
               }
            }
         }

         if (this.fStAXSchemaParser == null) {
            this.fStAXSchemaParser = new StAXSchemaParser();
         }

         this.fStAXSchemaParser.reset(this.fSchemaParser, this.fSymbolTable);
         if (var16 != null) {
            this.fStAXSchemaParser.parse(var16);
            if (var8) {
               while(var16.hasNext()) {
                  var16.next();
               }
            }
         } else {
            this.fStAXSchemaParser.parse(var17);
            if (var8) {
               while(var17.hasNext()) {
                  var17.nextEvent();
               }
            }
         }

         Document var18 = this.fStAXSchemaParser.getDocument();
         var7 = var18 != null ? DOMUtil.getRoot(var18) : null;
         return this.getSchemaDocument0(var11, var12, var7);
      } catch (XMLStreamException var14) {
         Throwable var9 = var14.getNestedException();
         if (!(var9 instanceof IOException)) {
            StAXLocationWrapper var10 = new StAXLocationWrapper();
            var10.setLocation(var14.getLocation());
            throw new XMLParseException(var10, var14.getMessage(), var14);
         }

         var6 = (IOException)var9;
      } catch (IOException var15) {
         var6 = var15;
      }

      return this.getSchemaDocument1(var3, true, var2, var5, var6);
   }

   private Element getSchemaDocument0(XSDKey var1, String var2, Element var3) {
      if (var1 != null) {
         this.fTraversed.put(var1, var3);
      }

      if (var2 != null) {
         this.fDoc2SystemId.put(var3, var2);
      }

      this.fLastSchemaWasDuplicate = false;
      return var3;
   }

   private Element getSchemaDocument1(boolean var1, boolean var2, XMLInputSource var3, Element var4, IOException var5) {
      if (var1) {
         if (var2) {
            this.reportSchemaError("schema_reference.4", new Object[]{var3.getSystemId()}, var4, var5);
         } else {
            this.reportSchemaError("schema_reference.4", new Object[]{var3 == null ? "" : var3.getSystemId()}, var4, var5);
         }
      } else if (var2) {
         this.reportSchemaWarning("schema_reference.4", new Object[]{var3.getSystemId()}, var4, var5);
      }

      this.fLastSchemaWasDuplicate = false;
      return null;
   }

   private Element getSchemaDocument(XSInputSource var1, XSDDescription var2) {
      SchemaGrammar[] var3 = var1.getGrammars();
      short var4 = var2.getContextType();
      if (var3 != null && var3.length > 0) {
         Vector var8 = this.expandGrammars(var3);
         if (this.fNamespaceGrowth || !this.existingGrammars(var8)) {
            this.addGrammars(var8);
            if (var4 == 3) {
               var2.setTargetNamespace(var3[0].getTargetNamespace());
            }
         }
      } else {
         XSObject[] var5 = var1.getComponents();
         if (var5 != null && var5.length > 0) {
            Hashtable var6 = new Hashtable();
            Vector var7 = this.expandComponents(var5, var6);
            if (this.fNamespaceGrowth || this.canAddComponents(var7)) {
               this.addGlobalComponents(var7, var6);
               if (var4 == 3) {
                  var2.setTargetNamespace(var5[0].getNamespace());
               }
            }
         }
      }

      return null;
   }

   private Vector expandGrammars(SchemaGrammar[] var1) {
      Vector var2 = new Vector();

      for(int var3 = 0; var3 < var1.length; ++var3) {
         if (!var2.contains(var1[var3])) {
            var2.add(var1[var3]);
         }
      }

      for(int var7 = 0; var7 < var2.size(); ++var7) {
         SchemaGrammar var4 = (SchemaGrammar)var2.elementAt(var7);
         Vector var6 = var4.getImportedGrammars();
         if (var6 != null) {
            for(int var8 = var6.size() - 1; var8 >= 0; --var8) {
               SchemaGrammar var5 = (SchemaGrammar)var6.elementAt(var8);
               if (!var2.contains(var5)) {
                  var2.addElement(var5);
               }
            }
         }
      }

      return var2;
   }

   private boolean existingGrammars(Vector var1) {
      int var2 = var1.size();
      XSDDescription var3 = new XSDDescription();

      for(int var4 = 0; var4 < var2; ++var4) {
         SchemaGrammar var5 = (SchemaGrammar)var1.elementAt(var4);
         var3.setNamespace(var5.getTargetNamespace());
         SchemaGrammar var6 = this.findGrammar(var3, false);
         if (var6 != null) {
            return true;
         }
      }

      return false;
   }

   private boolean canAddComponents(Vector var1) {
      int var2 = var1.size();
      XSDDescription var3 = new XSDDescription();

      for(int var4 = 0; var4 < var2; ++var4) {
         XSObject var5 = (XSObject)var1.elementAt(var4);
         if (!this.canAddComponent(var5, var3)) {
            return false;
         }
      }

      return true;
   }

   private boolean canAddComponent(XSObject var1, XSDDescription var2) {
      var2.setNamespace(var1.getNamespace());
      SchemaGrammar var3 = this.findGrammar(var2, false);
      if (var3 == null) {
         return true;
      } else if (var3.isImmutable()) {
         return false;
      } else {
         short var4 = var1.getType();
         String var5 = var1.getName();
         switch (var4) {
            case 1:
               if (var3.getGlobalAttributeDecl(var5) == var1) {
                  return true;
               }
               break;
            case 2:
               if (var3.getGlobalElementDecl(var5) == var1) {
                  return true;
               }
               break;
            case 3:
               if (var3.getGlobalTypeDecl(var5) == var1) {
                  return true;
               }
               break;
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
               return true;
            case 5:
               if (var3.getGlobalAttributeDecl(var5) == var1) {
                  return true;
               }
               break;
            case 6:
               if (var3.getGlobalGroupDecl(var5) == var1) {
                  return true;
               }
               break;
            case 11:
               if (var3.getGlobalNotationDecl(var5) == var1) {
                  return true;
               }
         }

         return false;
      }
   }

   private void addGrammars(Vector var1) {
      int var2 = var1.size();
      XSDDescription var3 = new XSDDescription();

      for(int var4 = 0; var4 < var2; ++var4) {
         SchemaGrammar var5 = (SchemaGrammar)var1.elementAt(var4);
         var3.setNamespace(var5.getTargetNamespace());
         SchemaGrammar var6 = this.findGrammar(var3, this.fNamespaceGrowth);
         if (var5 != var6) {
            this.addGrammarComponents(var5, var6);
         }
      }

   }

   private void addGrammarComponents(SchemaGrammar var1, SchemaGrammar var2) {
      if (var2 == null) {
         this.createGrammarFrom(var1);
      } else {
         SchemaGrammar var3 = var2;
         if (var2.isImmutable()) {
            var3 = this.createGrammarFrom(var2);
         }

         this.addNewGrammarLocations(var1, var3);
         this.addNewImportedGrammars(var1, var3);
         this.addNewGrammarComponents(var1, var3);
      }
   }

   private SchemaGrammar createGrammarFrom(SchemaGrammar var1) {
      SchemaGrammar var2 = new SchemaGrammar(var1);
      this.fGrammarBucket.putGrammar(var2);
      this.updateImportListWith(var2);
      this.updateImportListFor(var2);
      return var2;
   }

   private void addNewGrammarLocations(SchemaGrammar var1, SchemaGrammar var2) {
      StringList var3 = var1.getDocumentLocations();
      int var4 = var3.size();
      StringList var5 = var2.getDocumentLocations();

      for(int var6 = 0; var6 < var4; ++var6) {
         String var7 = var3.item(var6);
         if (!var5.contains(var7)) {
            var2.addDocument((Object)null, var7);
         }
      }

   }

   private void addNewImportedGrammars(SchemaGrammar var1, SchemaGrammar var2) {
      Vector var3 = var1.getImportedGrammars();
      if (var3 != null) {
         Vector var4 = var2.getImportedGrammars();
         if (var4 == null) {
            var4 = new Vector();
            var2.setImportedGrammars(var4);
         }

         int var5 = var3.size();

         for(int var6 = 0; var6 < var5; ++var6) {
            SchemaGrammar var7 = (SchemaGrammar)var3.elementAt(var6);
            SchemaGrammar var8 = this.fGrammarBucket.getGrammar(var7.getTargetNamespace());
            if (var8 != null) {
               var7 = var8;
            }

            if (!this.containedImportedGrammar(var4, var7)) {
               var4.add(var7);
            }
         }
      }

   }

   private void updateImportList(Vector var1, Vector var2) {
      int var3 = var1.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         SchemaGrammar var5 = (SchemaGrammar)var1.elementAt(var4);
         if (!this.containedImportedGrammar(var2, var5)) {
            var2.add(var5);
         }
      }

   }

   private void addNewGrammarComponents(SchemaGrammar var1, SchemaGrammar var2) {
      var2.resetComponents();
      this.addGlobalElementDecls(var1, var2);
      this.addGlobalAttributeDecls(var1, var2);
      this.addGlobalAttributeGroupDecls(var1, var2);
      this.addGlobalGroupDecls(var1, var2);
      this.addGlobalTypeDecls(var1, var2);
      this.addGlobalNotationDecls(var1, var2);
   }

   private void addGlobalElementDecls(SchemaGrammar var1, SchemaGrammar var2) {
      XSNamedMap var3 = var1.getComponents((short)2);
      int var4 = var3.getLength();

      XSElementDecl var5;
      XSElementDecl var6;
      for(int var7 = 0; var7 < var4; ++var7) {
         var5 = (XSElementDecl)var3.item(var7);
         var6 = var2.getGlobalElementDecl(var5.getName());
         if (var6 == null) {
            var2.addGlobalElementDecl(var5);
         } else if (var6 != var5) {
         }
      }

      ObjectList var8 = var1.getComponentsExt((short)2);
      var4 = var8.getLength();

      for(int var9 = 0; var9 < var4; var9 += 2) {
         String var10 = (String)var8.item(var9);
         int var11 = var10.indexOf(44);
         String var12 = var10.substring(0, var11);
         String var13 = var10.substring(var11 + 1, var10.length());
         var5 = (XSElementDecl)var8.item(var9 + 1);
         var6 = var2.getGlobalElementDecl(var13, var12);
         if (var6 == null) {
            var2.addGlobalElementDecl(var5, var12);
         } else if (var6 != var5) {
         }
      }

   }

   private void addGlobalAttributeDecls(SchemaGrammar var1, SchemaGrammar var2) {
      XSNamedMap var3 = var1.getComponents((short)1);
      int var4 = var3.getLength();

      XSAttributeDecl var5;
      XSAttributeDecl var6;
      for(int var7 = 0; var7 < var4; ++var7) {
         var5 = (XSAttributeDecl)var3.item(var7);
         var6 = var2.getGlobalAttributeDecl(var5.getName());
         if (var6 == null) {
            var2.addGlobalAttributeDecl(var5);
         } else if (var6 != var5 && !this.fTolerateDuplicates) {
            this.reportSharingError(var5.getNamespace(), var5.getName());
         }
      }

      ObjectList var8 = var1.getComponentsExt((short)1);
      var4 = var8.getLength();

      for(int var9 = 0; var9 < var4; var9 += 2) {
         String var10 = (String)var8.item(var9);
         int var11 = var10.indexOf(44);
         String var12 = var10.substring(0, var11);
         String var13 = var10.substring(var11 + 1, var10.length());
         var5 = (XSAttributeDecl)var8.item(var9 + 1);
         var6 = var2.getGlobalAttributeDecl(var13, var12);
         if (var6 == null) {
            var2.addGlobalAttributeDecl(var5, var12);
         } else if (var6 != var5) {
         }
      }

   }

   private void addGlobalAttributeGroupDecls(SchemaGrammar var1, SchemaGrammar var2) {
      XSNamedMap var3 = var1.getComponents((short)5);
      int var4 = var3.getLength();

      XSAttributeGroupDecl var5;
      XSAttributeGroupDecl var6;
      for(int var7 = 0; var7 < var4; ++var7) {
         var5 = (XSAttributeGroupDecl)var3.item(var7);
         var6 = var2.getGlobalAttributeGroupDecl(var5.getName());
         if (var6 == null) {
            var2.addGlobalAttributeGroupDecl(var5);
         } else if (var6 != var5 && !this.fTolerateDuplicates) {
            this.reportSharingError(var5.getNamespace(), var5.getName());
         }
      }

      ObjectList var8 = var1.getComponentsExt((short)5);
      var4 = var8.getLength();

      for(int var9 = 0; var9 < var4; var9 += 2) {
         String var10 = (String)var8.item(var9);
         int var11 = var10.indexOf(44);
         String var12 = var10.substring(0, var11);
         String var13 = var10.substring(var11 + 1, var10.length());
         var5 = (XSAttributeGroupDecl)var8.item(var9 + 1);
         var6 = var2.getGlobalAttributeGroupDecl(var13, var12);
         if (var6 == null) {
            var2.addGlobalAttributeGroupDecl(var5, var12);
         } else if (var6 != var5) {
         }
      }

   }

   private void addGlobalNotationDecls(SchemaGrammar var1, SchemaGrammar var2) {
      XSNamedMap var3 = var1.getComponents((short)11);
      int var4 = var3.getLength();

      XSNotationDecl var5;
      XSNotationDecl var6;
      for(int var7 = 0; var7 < var4; ++var7) {
         var5 = (XSNotationDecl)var3.item(var7);
         var6 = var2.getGlobalNotationDecl(var5.getName());
         if (var6 == null) {
            var2.addGlobalNotationDecl(var5);
         } else if (var6 != var5 && !this.fTolerateDuplicates) {
            this.reportSharingError(var5.getNamespace(), var5.getName());
         }
      }

      ObjectList var8 = var1.getComponentsExt((short)11);
      var4 = var8.getLength();

      for(int var9 = 0; var9 < var4; var9 += 2) {
         String var10 = (String)var8.item(var9);
         int var11 = var10.indexOf(44);
         String var12 = var10.substring(0, var11);
         String var13 = var10.substring(var11 + 1, var10.length());
         var5 = (XSNotationDecl)var8.item(var9 + 1);
         var6 = var2.getGlobalNotationDecl(var13, var12);
         if (var6 == null) {
            var2.addGlobalNotationDecl(var5, var12);
         } else if (var6 != var5) {
         }
      }

   }

   private void addGlobalGroupDecls(SchemaGrammar var1, SchemaGrammar var2) {
      XSNamedMap var3 = var1.getComponents((short)6);
      int var4 = var3.getLength();

      XSGroupDecl var5;
      XSGroupDecl var6;
      for(int var7 = 0; var7 < var4; ++var7) {
         var5 = (XSGroupDecl)var3.item(var7);
         var6 = var2.getGlobalGroupDecl(var5.getName());
         if (var6 == null) {
            var2.addGlobalGroupDecl(var5);
         } else if (var5 != var6 && !this.fTolerateDuplicates) {
            this.reportSharingError(var5.getNamespace(), var5.getName());
         }
      }

      ObjectList var8 = var1.getComponentsExt((short)6);
      var4 = var8.getLength();

      for(int var9 = 0; var9 < var4; var9 += 2) {
         String var10 = (String)var8.item(var9);
         int var11 = var10.indexOf(44);
         String var12 = var10.substring(0, var11);
         String var13 = var10.substring(var11 + 1, var10.length());
         var5 = (XSGroupDecl)var8.item(var9 + 1);
         var6 = var2.getGlobalGroupDecl(var13, var12);
         if (var6 == null) {
            var2.addGlobalGroupDecl(var5, var12);
         } else if (var6 != var5) {
         }
      }

   }

   private void addGlobalTypeDecls(SchemaGrammar var1, SchemaGrammar var2) {
      XSNamedMap var3 = var1.getComponents((short)3);
      int var4 = var3.getLength();

      XSTypeDefinition var5;
      XSTypeDefinition var6;
      for(int var7 = 0; var7 < var4; ++var7) {
         var5 = (XSTypeDefinition)var3.item(var7);
         var6 = var2.getGlobalTypeDecl(var5.getName());
         if (var6 == null) {
            var2.addGlobalTypeDecl(var5);
         } else if (var6 != var5 && !this.fTolerateDuplicates) {
            this.reportSharingError(var5.getNamespace(), var5.getName());
         }
      }

      ObjectList var8 = var1.getComponentsExt((short)3);
      var4 = var8.getLength();

      for(int var9 = 0; var9 < var4; var9 += 2) {
         String var10 = (String)var8.item(var9);
         int var11 = var10.indexOf(44);
         String var12 = var10.substring(0, var11);
         String var13 = var10.substring(var11 + 1, var10.length());
         var5 = (XSTypeDefinition)var8.item(var9 + 1);
         var6 = var2.getGlobalTypeDecl(var13, var12);
         if (var6 == null) {
            var2.addGlobalTypeDecl(var5, var12);
         } else if (var6 != var5) {
         }
      }

   }

   private Vector expandComponents(XSObject[] var1, Hashtable var2) {
      Vector var3 = new Vector();

      for(int var4 = 0; var4 < var1.length; ++var4) {
         if (!var3.contains(var1[var4])) {
            var3.add(var1[var4]);
         }
      }

      for(int var5 = 0; var5 < var3.size(); ++var5) {
         XSObject var6 = (XSObject)var3.elementAt(var5);
         this.expandRelatedComponents(var6, var3, var2);
      }

      return var3;
   }

   private void expandRelatedComponents(XSObject var1, Vector var2, Hashtable var3) {
      short var4 = var1.getType();
      switch (var4) {
         case 1:
            this.expandRelatedAttributeComponents((XSAttributeDeclaration)var1, var2, var1.getNamespace(), var3);
            break;
         case 3:
            this.expandRelatedTypeComponents((XSTypeDefinition)var1, var2, var1.getNamespace(), var3);
         case 4:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         default:
            break;
         case 5:
            this.expandRelatedAttributeGroupComponents((XSAttributeGroupDefinition)var1, var2, var1.getNamespace(), var3);
         case 2:
            this.expandRelatedElementComponents((XSElementDeclaration)var1, var2, var1.getNamespace(), var3);
            break;
         case 6:
            this.expandRelatedModelGroupDefinitionComponents((XSModelGroupDefinition)var1, var2, var1.getNamespace(), var3);
      }

   }

   private void expandRelatedAttributeComponents(XSAttributeDeclaration var1, Vector var2, String var3, Hashtable var4) {
      this.addRelatedType(var1.getTypeDefinition(), var2, var3, var4);
   }

   private void expandRelatedElementComponents(XSElementDeclaration var1, Vector var2, String var3, Hashtable var4) {
      this.addRelatedType(var1.getTypeDefinition(), var2, var3, var4);
      XSElementDeclaration var5 = var1.getSubstitutionGroupAffiliation();
      if (var5 != null) {
         this.addRelatedElement(var5, var2, var3, var4);
      }

   }

   private void expandRelatedTypeComponents(XSTypeDefinition var1, Vector var2, String var3, Hashtable var4) {
      if (var1 instanceof XSComplexTypeDecl) {
         this.expandRelatedComplexTypeComponents((XSComplexTypeDecl)var1, var2, var3, var4);
      } else if (var1 instanceof XSSimpleTypeDecl) {
         this.expandRelatedSimpleTypeComponents((XSSimpleTypeDefinition)var1, var2, var3, var4);
      }

   }

   private void expandRelatedModelGroupDefinitionComponents(XSModelGroupDefinition var1, Vector var2, String var3, Hashtable var4) {
      this.expandRelatedModelGroupComponents(var1.getModelGroup(), var2, var3, var4);
   }

   private void expandRelatedAttributeGroupComponents(XSAttributeGroupDefinition var1, Vector var2, String var3, Hashtable var4) {
      this.expandRelatedAttributeUsesComponents(var1.getAttributeUses(), var2, var3, var4);
   }

   private void expandRelatedComplexTypeComponents(XSComplexTypeDecl var1, Vector var2, String var3, Hashtable var4) {
      this.addRelatedType(var1.getBaseType(), var2, var3, var4);
      this.expandRelatedAttributeUsesComponents(var1.getAttributeUses(), var2, var3, var4);
      XSParticle var5 = var1.getParticle();
      if (var5 != null) {
         this.expandRelatedParticleComponents(var5, var2, var3, var4);
      }

   }

   private void expandRelatedSimpleTypeComponents(XSSimpleTypeDefinition var1, Vector var2, String var3, Hashtable var4) {
      XSTypeDefinition var5 = var1.getBaseType();
      if (var5 != null) {
         this.addRelatedType(var5, var2, var3, var4);
      }

      XSSimpleTypeDefinition var6 = var1.getItemType();
      if (var6 != null) {
         this.addRelatedType(var6, var2, var3, var4);
      }

      XSSimpleTypeDefinition var7 = var1.getPrimitiveType();
      if (var7 != null) {
         this.addRelatedType(var7, var2, var3, var4);
      }

      XSObjectList var8 = var1.getMemberTypes();
      if (var8.size() > 0) {
         for(int var9 = 0; var9 < var8.size(); ++var9) {
            this.addRelatedType((XSTypeDefinition)var8.item(var9), var2, var3, var4);
         }
      }

   }

   private void expandRelatedAttributeUsesComponents(XSObjectList var1, Vector var2, String var3, Hashtable var4) {
      int var5 = var1 == null ? 0 : var1.size();

      for(int var6 = 0; var6 < var5; ++var6) {
         this.expandRelatedAttributeUseComponents((XSAttributeUse)var1.item(var6), var2, var3, var4);
      }

   }

   private void expandRelatedAttributeUseComponents(XSAttributeUse var1, Vector var2, String var3, Hashtable var4) {
      this.addRelatedAttribute(var1.getAttrDeclaration(), var2, var3, var4);
   }

   private void expandRelatedParticleComponents(XSParticle var1, Vector var2, String var3, Hashtable var4) {
      XSTerm var5 = var1.getTerm();
      switch (var5.getType()) {
         case 2:
            this.addRelatedElement((XSElementDeclaration)var5, var2, var3, var4);
            break;
         case 7:
            this.expandRelatedModelGroupComponents((XSModelGroup)var5, var2, var3, var4);
      }

   }

   private void expandRelatedModelGroupComponents(XSModelGroup var1, Vector var2, String var3, Hashtable var4) {
      XSObjectList var5 = var1.getParticles();
      int var6 = var5 == null ? 0 : var5.getLength();

      for(int var7 = 0; var7 < var6; ++var7) {
         this.expandRelatedParticleComponents((XSParticle)var5.item(var7), var2, var3, var4);
      }

   }

   private void addRelatedType(XSTypeDefinition var1, Vector var2, String var3, Hashtable var4) {
      if (!var1.getAnonymous()) {
         if (!SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var1.getNamespace()) && !var2.contains(var1)) {
            Vector var5 = this.findDependentNamespaces(var3, var4);
            this.addNamespaceDependency(var3, var1.getNamespace(), var5);
            var2.add(var1);
         }
      } else {
         this.expandRelatedTypeComponents(var1, var2, var3, var4);
      }

   }

   private void addRelatedElement(XSElementDeclaration var1, Vector var2, String var3, Hashtable var4) {
      if (var1.getScope() == 1) {
         if (!var2.contains(var1)) {
            Vector var5 = this.findDependentNamespaces(var3, var4);
            this.addNamespaceDependency(var3, var1.getNamespace(), var5);
            var2.add(var1);
         }
      } else {
         this.expandRelatedElementComponents(var1, var2, var3, var4);
      }

   }

   private void addRelatedAttribute(XSAttributeDeclaration var1, Vector var2, String var3, Hashtable var4) {
      if (var1.getScope() == 1) {
         if (!var2.contains(var1)) {
            Vector var5 = this.findDependentNamespaces(var3, var4);
            this.addNamespaceDependency(var3, var1.getNamespace(), var5);
            var2.add(var1);
         }
      } else {
         this.expandRelatedAttributeComponents(var1, var2, var3, var4);
      }

   }

   private void addGlobalComponents(Vector var1, Hashtable var2) {
      XSDDescription var3 = new XSDDescription();
      int var4 = var1.size();

      for(int var5 = 0; var5 < var4; ++var5) {
         this.addGlobalComponent((XSObject)var1.elementAt(var5), var3);
      }

      this.updateImportDependencies(var2);
   }

   private void addGlobalComponent(XSObject var1, XSDDescription var2) {
      String var3 = var1.getNamespace();
      var2.setNamespace(var3);
      SchemaGrammar var4 = this.getSchemaGrammar(var2);
      short var5 = var1.getType();
      String var6 = var1.getName();
      switch (var5) {
         case 1:
            if (((XSAttributeDecl)var1).getScope() == 1) {
               if (var4.getGlobalAttributeDecl(var6) == null) {
                  var4.addGlobalAttributeDecl((XSAttributeDecl)var1);
               }

               if (var4.getGlobalAttributeDecl(var6, "") == null) {
                  var4.addGlobalAttributeDecl((XSAttributeDecl)var1, "");
               }
            }
            break;
         case 2:
            if (((XSElementDecl)var1).getScope() == 1) {
               var4.addGlobalElementDeclAll((XSElementDecl)var1);
               if (var4.getGlobalElementDecl(var6) == null) {
                  var4.addGlobalElementDecl((XSElementDecl)var1);
               }

               if (var4.getGlobalElementDecl(var6, "") == null) {
                  var4.addGlobalElementDecl((XSElementDecl)var1, "");
               }
            }
            break;
         case 3:
            if (!((XSTypeDefinition)var1).getAnonymous()) {
               if (var4.getGlobalTypeDecl(var6) == null) {
                  var4.addGlobalTypeDecl((XSTypeDefinition)var1);
               }

               if (var4.getGlobalTypeDecl(var6, "") == null) {
                  var4.addGlobalTypeDecl((XSTypeDefinition)var1, "");
               }
            }
         case 4:
         case 7:
         case 8:
         case 9:
         case 10:
         default:
            break;
         case 5:
            if (var4.getGlobalAttributeDecl(var6) == null) {
               var4.addGlobalAttributeGroupDecl((XSAttributeGroupDecl)var1);
            }

            if (var4.getGlobalAttributeDecl(var6, "") == null) {
               var4.addGlobalAttributeGroupDecl((XSAttributeGroupDecl)var1, "");
            }
            break;
         case 6:
            if (var4.getGlobalGroupDecl(var6) == null) {
               var4.addGlobalGroupDecl((XSGroupDecl)var1);
            }

            if (var4.getGlobalGroupDecl(var6, "") == null) {
               var4.addGlobalGroupDecl((XSGroupDecl)var1, "");
            }
            break;
         case 11:
            if (var4.getGlobalNotationDecl(var6) == null) {
               var4.addGlobalNotationDecl((XSNotationDecl)var1);
            }

            if (var4.getGlobalNotationDecl(var6, "") == null) {
               var4.addGlobalNotationDecl((XSNotationDecl)var1, "");
            }
      }

   }

   private void updateImportDependencies(Hashtable var1) {
      Enumeration var2 = var1.keys();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         Vector var4 = (Vector)var1.get(this.null2EmptyString(var3));
         if (var4.size() > 0) {
            this.expandImportList(var3, var4);
         }
      }

   }

   private void expandImportList(String var1, Vector var2) {
      SchemaGrammar var3 = this.fGrammarBucket.getGrammar(var1);
      if (var3 != null) {
         Vector var4 = var3.getImportedGrammars();
         if (var4 == null) {
            var4 = new Vector();
            this.addImportList(var3, var4, var2);
            var3.setImportedGrammars(var4);
         } else {
            this.updateImportList(var3, var4, var2);
         }
      }

   }

   private void addImportList(SchemaGrammar var1, Vector var2, Vector var3) {
      int var4 = var3.size();

      for(int var6 = 0; var6 < var4; ++var6) {
         SchemaGrammar var5 = this.fGrammarBucket.getGrammar((String)var3.elementAt(var6));
         if (var5 != null) {
            var2.add(var5);
         }
      }

   }

   private void updateImportList(SchemaGrammar var1, Vector var2, Vector var3) {
      int var4 = var3.size();

      for(int var6 = 0; var6 < var4; ++var6) {
         SchemaGrammar var5 = this.fGrammarBucket.getGrammar((String)var3.elementAt(var6));
         if (var5 != null && !this.containedImportedGrammar(var2, var5)) {
            var2.add(var5);
         }
      }

   }

   private boolean containedImportedGrammar(Vector var1, SchemaGrammar var2) {
      int var3 = var1.size();

      for(int var5 = 0; var5 < var3; ++var5) {
         SchemaGrammar var4 = (SchemaGrammar)var1.elementAt(var5);
         if (this.null2EmptyString(var4.getTargetNamespace()).equals(this.null2EmptyString(var2.getTargetNamespace()))) {
            return true;
         }
      }

      return false;
   }

   private SchemaGrammar getSchemaGrammar(XSDDescription var1) {
      SchemaGrammar var2 = this.findGrammar(var1, this.fNamespaceGrowth);
      if (var2 == null) {
         var2 = new SchemaGrammar(var1.getNamespace(), var1.makeClone(), this.fSymbolTable);
         this.fGrammarBucket.putGrammar(var2);
      } else if (var2.isImmutable()) {
         var2 = this.createGrammarFrom(var2);
      }

      return var2;
   }

   private Vector findDependentNamespaces(String var1, Hashtable var2) {
      String var3 = this.null2EmptyString(var1);
      Vector var4 = (Vector)var2.get(var3);
      if (var4 == null) {
         var4 = new Vector();
         var2.put(var3, var4);
      }

      return var4;
   }

   private void addNamespaceDependency(String var1, String var2, Vector var3) {
      String var4 = this.null2EmptyString(var1);
      String var5 = this.null2EmptyString(var2);
      if (!var4.equals(var5) && !var3.contains(var5)) {
         var3.add(var5);
      }

   }

   private void reportSharingError(String var1, String var2) {
      String var3 = var1 == null ? "," + var2 : var1 + "," + var2;
      this.reportSchemaError("sch-props-correct.2", new Object[]{var3}, (Element)null);
   }

   private void createTraversers() {
      this.fAttributeChecker = new XSAttributeChecker(this);
      this.fAttributeGroupTraverser = new XSDAttributeGroupTraverser(this, this.fAttributeChecker);
      this.fAttributeTraverser = new XSDAttributeTraverser(this, this.fAttributeChecker);
      this.fComplexTypeTraverser = new XSDComplexTypeTraverser(this, this.fAttributeChecker);
      this.fElementTraverser = new XSDElementTraverser(this, this.fAttributeChecker);
      this.fGroupTraverser = new XSDGroupTraverser(this, this.fAttributeChecker);
      this.fKeyrefTraverser = new XSDKeyrefTraverser(this, this.fAttributeChecker);
      this.fNotationTraverser = new XSDNotationTraverser(this, this.fAttributeChecker);
      this.fSimpleTypeTraverser = new XSDSimpleTypeTraverser(this, this.fAttributeChecker);
      this.fUniqueOrKeyTraverser = new XSDUniqueOrKeyTraverser(this, this.fAttributeChecker);
      this.fWildCardTraverser = new XSDWildcardTraverser(this, this.fAttributeChecker);
   }

   void prepareForParse() {
      this.fTraversed.clear();
      this.fDoc2SystemId.clear();
      this.fHiddenNodes.clear();
      this.fLastSchemaWasDuplicate = false;
   }

   void prepareForTraverse() {
      this.fUnparsedAttributeRegistry.clear();
      this.fUnparsedAttributeGroupRegistry.clear();
      this.fUnparsedElementRegistry.clear();
      this.fUnparsedGroupRegistry.clear();
      this.fUnparsedIdentityConstraintRegistry.clear();
      this.fUnparsedNotationRegistry.clear();
      this.fUnparsedTypeRegistry.clear();
      this.fUnparsedAttributeRegistrySub.clear();
      this.fUnparsedAttributeGroupRegistrySub.clear();
      this.fUnparsedElementRegistrySub.clear();
      this.fUnparsedGroupRegistrySub.clear();
      this.fUnparsedIdentityConstraintRegistrySub.clear();
      this.fUnparsedNotationRegistrySub.clear();
      this.fUnparsedTypeRegistrySub.clear();

      for(int var1 = 1; var1 <= 7; ++var1) {
         this.fUnparsedRegistriesExt[var1].clear();
      }

      this.fXSDocumentInfoRegistry.clear();
      this.fDependencyMap.clear();
      this.fDoc2XSDocumentMap.clear();
      this.fRedefine2XSDMap.clear();
      this.fRedefine2NSSupport.clear();
      this.fAllTNSs.removeAllElements();
      this.fImportMap.clear();
      this.fRoot = null;

      for(int var2 = 0; var2 < this.fLocalElemStackPos; ++var2) {
         this.fParticle[var2] = null;
         this.fLocalElementDecl[var2] = null;
         this.fLocalElementDecl_schema[var2] = null;
         this.fLocalElemNamespaceContext[var2] = null;
      }

      this.fLocalElemStackPos = 0;

      for(int var3 = 0; var3 < this.fKeyrefStackPos; ++var3) {
         this.fKeyrefs[var3] = null;
         this.fKeyrefElems[var3] = null;
         this.fKeyrefNamespaceContext[var3] = null;
         this.fKeyrefsMapXSDocumentInfo[var3] = null;
      }

      this.fKeyrefStackPos = 0;
      if (this.fAttributeChecker == null) {
         this.createTraversers();
      }

      Locale var4 = this.fErrorReporter.getLocale();
      this.fAttributeChecker.reset(this.fSymbolTable);
      this.fAttributeGroupTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fAttributeTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fComplexTypeTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fElementTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fGroupTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fKeyrefTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fNotationTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fSimpleTypeTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fUniqueOrKeyTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fWildCardTraverser.reset(this.fSymbolTable, this.fValidateAnnotations, var4);
      this.fRedefinedRestrictedAttributeGroupRegistry.clear();
      this.fRedefinedRestrictedGroupRegistry.clear();
      this.fGlobalAttrDecls.clear();
      this.fGlobalAttrGrpDecls.clear();
      this.fGlobalElemDecls.clear();
      this.fGlobalGroupDecls.clear();
      this.fGlobalNotationDecls.clear();
      this.fGlobalIDConstraintDecls.clear();
      this.fGlobalTypeDecls.clear();
   }

   public void setDeclPool(XSDeclarationPool var1) {
      this.fDeclPool = var1;
   }

   public void setDVFactory(SchemaDVFactory var1) {
      this.fDVFactory = var1;
   }

   public void reset(XMLComponentManager var1) {
      this.fSymbolTable = (SymbolTable)var1.getProperty("http://apache.org/xml/properties/internal/symbol-table");
      this.fEntityResolver = (XMLEntityResolver)var1.getProperty("http://apache.org/xml/properties/internal/entity-manager");
      XMLEntityResolver var2 = (XMLEntityResolver)var1.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
      if (var2 != null) {
         this.fSchemaParser.setEntityResolver(var2);
      }

      this.fErrorReporter = (XMLErrorReporter)var1.getProperty("http://apache.org/xml/properties/internal/error-reporter");

      try {
         XMLErrorHandler var3 = this.fErrorReporter.getErrorHandler();
         if (var3 != this.fSchemaParser.getProperty("http://apache.org/xml/properties/internal/error-handler")) {
            this.fSchemaParser.setProperty("http://apache.org/xml/properties/internal/error-handler", var3 != null ? var3 : new DefaultErrorHandler());
            if (this.fAnnotationValidator != null) {
               this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/internal/error-handler", var3 != null ? var3 : new DefaultErrorHandler());
            }
         }

         Locale var4 = this.fErrorReporter.getLocale();
         if (var4 != this.fSchemaParser.getProperty("http://apache.org/xml/properties/locale")) {
            this.fSchemaParser.setProperty("http://apache.org/xml/properties/locale", var4);
            if (this.fAnnotationValidator != null) {
               this.fAnnotationValidator.setProperty("http://apache.org/xml/properties/locale", var4);
            }
         }
      } catch (XMLConfigurationException var15) {
      }

      try {
         this.fValidateAnnotations = var1.getFeature("http://apache.org/xml/features/validate-annotations");
      } catch (XMLConfigurationException var14) {
         this.fValidateAnnotations = false;
      }

      try {
         this.fHonourAllSchemaLocations = var1.getFeature("http://apache.org/xml/features/honour-all-schemaLocations");
      } catch (XMLConfigurationException var13) {
         this.fHonourAllSchemaLocations = false;
      }

      try {
         this.fNamespaceGrowth = var1.getFeature("http://apache.org/xml/features/namespace-growth");
      } catch (XMLConfigurationException var12) {
         this.fNamespaceGrowth = false;
      }

      try {
         this.fTolerateDuplicates = var1.getFeature("http://apache.org/xml/features/internal/tolerate-duplicates");
      } catch (XMLConfigurationException var11) {
         this.fTolerateDuplicates = false;
      }

      try {
         this.fSchemaParser.setFeature("http://apache.org/xml/features/continue-after-fatal-error", this.fErrorReporter.getFeature("http://apache.org/xml/features/continue-after-fatal-error"));
      } catch (XMLConfigurationException var10) {
      }

      try {
         this.fSchemaParser.setFeature("http://apache.org/xml/features/allow-java-encodings", var1.getFeature("http://apache.org/xml/features/allow-java-encodings"));
      } catch (XMLConfigurationException var9) {
      }

      try {
         this.fSchemaParser.setFeature("http://apache.org/xml/features/standard-uri-conformant", var1.getFeature("http://apache.org/xml/features/standard-uri-conformant"));
      } catch (XMLConfigurationException var8) {
      }

      try {
         this.fGrammarPool = (XMLGrammarPool)var1.getProperty("http://apache.org/xml/properties/internal/grammar-pool");
      } catch (XMLConfigurationException var7) {
         this.fGrammarPool = null;
      }

      try {
         this.fSchemaParser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", var1.getFeature("http://apache.org/xml/features/disallow-doctype-decl"));
      } catch (XMLConfigurationException var6) {
      }

      try {
         Object var16 = var1.getProperty("http://apache.org/xml/properties/security-manager");
         if (var16 != null) {
            this.fSchemaParser.setProperty("http://apache.org/xml/properties/security-manager", var16);
         }
      } catch (XMLConfigurationException var5) {
      }

   }

   void traverseLocalElements() {
      this.fElementTraverser.fDeferTraversingLocalElements = false;

      for(int var1 = 0; var1 < this.fLocalElemStackPos; ++var1) {
         Element var2 = this.fLocalElementDecl[var1];
         XSDocumentInfo var3 = this.fLocalElementDecl_schema[var1];
         SchemaGrammar var4 = this.fGrammarBucket.getGrammar(var3.fTargetNamespace);
         this.fElementTraverser.traverseLocal(this.fParticle[var1], var2, var3, var4, this.fAllContext[var1], this.fParent[var1], this.fLocalElemNamespaceContext[var1]);
         if (this.fParticle[var1].fType == 0) {
            XSModelGroupImpl var5 = null;
            if (this.fParent[var1] instanceof XSComplexTypeDecl) {
               XSParticle var6 = ((XSComplexTypeDecl)this.fParent[var1]).getParticle();
               if (var6 != null) {
                  var5 = (XSModelGroupImpl)var6.getTerm();
               }
            } else {
               var5 = ((XSGroupDecl)this.fParent[var1]).fModelGroup;
            }

            if (var5 != null) {
               this.removeParticle(var5, this.fParticle[var1]);
            }
         }
      }

   }

   private boolean removeParticle(XSModelGroupImpl var1, XSParticleDecl var2) {
      for(int var4 = 0; var4 < var1.fParticleCount; ++var4) {
         XSParticleDecl var3 = var1.fParticles[var4];
         if (var3 == var2) {
            for(int var5 = var4; var5 < var1.fParticleCount - 1; ++var5) {
               var1.fParticles[var5] = var1.fParticles[var5 + 1];
            }

            --var1.fParticleCount;
            return true;
         }

         if (var3.fType == 3 && this.removeParticle((XSModelGroupImpl)var3.fValue, var2)) {
            return true;
         }
      }

      return false;
   }

   void fillInLocalElemInfo(Element var1, XSDocumentInfo var2, int var3, XSObject var4, XSParticleDecl var5) {
      if (this.fParticle.length == this.fLocalElemStackPos) {
         XSParticleDecl[] var6 = new XSParticleDecl[this.fLocalElemStackPos + 10];
         System.arraycopy(this.fParticle, 0, var6, 0, this.fLocalElemStackPos);
         this.fParticle = var6;
         Element[] var7 = new Element[this.fLocalElemStackPos + 10];
         System.arraycopy(this.fLocalElementDecl, 0, var7, 0, this.fLocalElemStackPos);
         this.fLocalElementDecl = var7;
         XSDocumentInfo[] var8 = new XSDocumentInfo[this.fLocalElemStackPos + 10];
         System.arraycopy(this.fLocalElementDecl_schema, 0, var8, 0, this.fLocalElemStackPos);
         this.fLocalElementDecl_schema = var8;
         int[] var9 = new int[this.fLocalElemStackPos + 10];
         System.arraycopy(this.fAllContext, 0, var9, 0, this.fLocalElemStackPos);
         this.fAllContext = var9;
         XSObject[] var10 = new XSObject[this.fLocalElemStackPos + 10];
         System.arraycopy(this.fParent, 0, var10, 0, this.fLocalElemStackPos);
         this.fParent = var10;
         String[][] var11 = new String[this.fLocalElemStackPos + 10][];
         System.arraycopy(this.fLocalElemNamespaceContext, 0, var11, 0, this.fLocalElemStackPos);
         this.fLocalElemNamespaceContext = var11;
      }

      this.fParticle[this.fLocalElemStackPos] = var5;
      this.fLocalElementDecl[this.fLocalElemStackPos] = var1;
      this.fLocalElementDecl_schema[this.fLocalElemStackPos] = var2;
      this.fAllContext[this.fLocalElemStackPos] = var3;
      this.fParent[this.fLocalElemStackPos] = var4;
      this.fLocalElemNamespaceContext[this.fLocalElemStackPos++] = var2.fNamespaceSupport.getEffectiveLocalContext();
   }

   void checkForDuplicateNames(String var1, int var2, Hashtable var3, Hashtable var4, Element var5, XSDocumentInfo var6) {
      Object var7 = null;
      if ((var7 = var3.get(var1)) == null) {
         if (this.fNamespaceGrowth && !this.fTolerateDuplicates) {
            this.checkForDuplicateNames(var1, var2, var5);
         }

         var3.put(var1, var5);
         var4.put(var1, var6);
      } else {
         Element var8 = (Element)var7;
         XSDocumentInfo var9 = (XSDocumentInfo)var4.get(var1);
         if (var8 == var5) {
            return;
         }

         Element var10 = null;
         XSDocumentInfo var11 = null;
         boolean var12 = true;
         if (DOMUtil.getLocalName(var10 = DOMUtil.getParent(var8)).equals(SchemaSymbols.ELT_REDEFINE)) {
            var11 = (XSDocumentInfo)this.fRedefine2XSDMap.get(var10);
         } else if (DOMUtil.getLocalName(DOMUtil.getParent(var5)).equals(SchemaSymbols.ELT_REDEFINE)) {
            var11 = var9;
            var12 = false;
         }

         if (var11 != null) {
            if (var9 == var6) {
               this.reportSchemaError("sch-props-correct.2", new Object[]{var1}, var5);
               return;
            }

            String var13 = var1.substring(var1.lastIndexOf(44) + 1) + "_fn3dktizrknc9pi";
            if (var11 == var6) {
               var5.setAttribute(SchemaSymbols.ATT_NAME, var13);
               if (var6.fTargetNamespace == null) {
                  var3.put("," + var13, var5);
                  var4.put("," + var13, var6);
               } else {
                  var3.put(var6.fTargetNamespace + "," + var13, var5);
                  var4.put(var6.fTargetNamespace + "," + var13, var6);
               }

               if (var6.fTargetNamespace == null) {
                  this.checkForDuplicateNames("," + var13, var2, var3, var4, var5, var6);
               } else {
                  this.checkForDuplicateNames(var6.fTargetNamespace + "," + var13, var2, var3, var4, var5, var6);
               }
            } else if (var12) {
               if (var6.fTargetNamespace == null) {
                  this.checkForDuplicateNames("," + var13, var2, var3, var4, var5, var6);
               } else {
                  this.checkForDuplicateNames(var6.fTargetNamespace + "," + var13, var2, var3, var4, var5, var6);
               }
            } else {
               this.reportSchemaError("sch-props-correct.2", new Object[]{var1}, var5);
            }
         } else if (!this.fTolerateDuplicates || this.fUnparsedRegistriesExt[var2].get(var1) == var6) {
            this.reportSchemaError("sch-props-correct.2", new Object[]{var1}, var5);
         }
      }

      if (this.fTolerateDuplicates) {
         this.fUnparsedRegistriesExt[var2].put(var1, var6);
      }

   }

   void checkForDuplicateNames(String var1, int var2, Element var3) {
      int var4 = var1.indexOf(44);
      String var5 = var1.substring(0, var4);
      SchemaGrammar var6 = this.fGrammarBucket.getGrammar(this.emptyString2Null(var5));
      if (var6 != null) {
         Object var7 = this.getGlobalDeclFromGrammar(var6, var2, var1.substring(var4 + 1));
         if (var7 != null) {
            this.reportSchemaError("sch-props-correct.2", new Object[]{var1}, var3);
         }
      }

   }

   private void renameRedefiningComponents(XSDocumentInfo var1, Element var2, String var3, String var4, String var5) {
      Element var6;
      if (var3.equals(SchemaSymbols.ELT_SIMPLETYPE)) {
         var6 = DOMUtil.getFirstChildElement(var2);
         if (var6 == null) {
            this.reportSchemaError("src-redefine.5.a.a", (Object[])null, var2);
         } else {
            String var7 = DOMUtil.getLocalName(var6);
            if (var7.equals(SchemaSymbols.ELT_ANNOTATION)) {
               var6 = DOMUtil.getNextSiblingElement(var6);
            }

            if (var6 == null) {
               this.reportSchemaError("src-redefine.5.a.a", (Object[])null, var2);
            } else {
               var7 = DOMUtil.getLocalName(var6);
               if (!var7.equals(SchemaSymbols.ELT_RESTRICTION)) {
                  this.reportSchemaError("src-redefine.5.a.b", new Object[]{var7}, var2);
               } else {
                  Object[] var8 = this.fAttributeChecker.checkAttributes(var6, false, var1);
                  QName var9 = (QName)var8[XSAttributeChecker.ATTIDX_BASE];
                  if (var9 != null && var9.uri == var1.fTargetNamespace && var9.localpart.equals(var4)) {
                     if (var9.prefix != null && var9.prefix.length() > 0) {
                        var6.setAttribute(SchemaSymbols.ATT_BASE, var9.prefix + ":" + var5);
                     } else {
                        var6.setAttribute(SchemaSymbols.ATT_BASE, var5);
                     }
                  } else {
                     this.reportSchemaError("src-redefine.5.a.c", new Object[]{var7, (var1.fTargetNamespace == null ? "" : var1.fTargetNamespace) + "," + var4}, var2);
                  }

                  this.fAttributeChecker.returnAttrArray(var8, var1);
               }
            }
         }
      } else if (var3.equals(SchemaSymbols.ELT_COMPLEXTYPE)) {
         var6 = DOMUtil.getFirstChildElement(var2);
         if (var6 == null) {
            this.reportSchemaError("src-redefine.5.b.a", (Object[])null, var2);
         } else {
            if (DOMUtil.getLocalName(var6).equals(SchemaSymbols.ELT_ANNOTATION)) {
               var6 = DOMUtil.getNextSiblingElement(var6);
            }

            if (var6 == null) {
               this.reportSchemaError("src-redefine.5.b.a", (Object[])null, var2);
            } else {
               Element var11 = DOMUtil.getFirstChildElement(var6);
               if (var11 == null) {
                  this.reportSchemaError("src-redefine.5.b.b", (Object[])null, var6);
               } else {
                  String var13 = DOMUtil.getLocalName(var11);
                  if (var13.equals(SchemaSymbols.ELT_ANNOTATION)) {
                     var11 = DOMUtil.getNextSiblingElement(var11);
                  }

                  if (var11 == null) {
                     this.reportSchemaError("src-redefine.5.b.b", (Object[])null, var6);
                  } else {
                     var13 = DOMUtil.getLocalName(var11);
                     if (!var13.equals(SchemaSymbols.ELT_RESTRICTION) && !var13.equals(SchemaSymbols.ELT_EXTENSION)) {
                        this.reportSchemaError("src-redefine.5.b.c", new Object[]{var13}, var11);
                     } else {
                        Object[] var15 = this.fAttributeChecker.checkAttributes(var11, false, var1);
                        QName var10 = (QName)var15[XSAttributeChecker.ATTIDX_BASE];
                        if (var10 != null && var10.uri == var1.fTargetNamespace && var10.localpart.equals(var4)) {
                           if (var10.prefix != null && var10.prefix.length() > 0) {
                              var11.setAttribute(SchemaSymbols.ATT_BASE, var10.prefix + ":" + var5);
                           } else {
                              var11.setAttribute(SchemaSymbols.ATT_BASE, var5);
                           }
                        } else {
                           this.reportSchemaError("src-redefine.5.b.d", new Object[]{var13, (var1.fTargetNamespace == null ? "" : var1.fTargetNamespace) + "," + var4}, var11);
                        }
                     }
                  }
               }
            }
         }
      } else {
         String var12;
         int var14;
         if (var3.equals(SchemaSymbols.ELT_ATTRIBUTEGROUP)) {
            var12 = var1.fTargetNamespace == null ? "," + var4 : var1.fTargetNamespace + "," + var4;
            var14 = this.changeRedefineGroup(var12, var3, var5, var2, var1);
            if (var14 > 1) {
               this.reportSchemaError("src-redefine.7.1", new Object[]{new Integer(var14)}, var2);
            } else if (var14 != 1) {
               if (var1.fTargetNamespace == null) {
                  this.fRedefinedRestrictedAttributeGroupRegistry.put(var12, "," + var5);
               } else {
                  this.fRedefinedRestrictedAttributeGroupRegistry.put(var12, var1.fTargetNamespace + "," + var5);
               }
            }
         } else if (var3.equals(SchemaSymbols.ELT_GROUP)) {
            var12 = var1.fTargetNamespace == null ? "," + var4 : var1.fTargetNamespace + "," + var4;
            var14 = this.changeRedefineGroup(var12, var3, var5, var2, var1);
            if (var14 > 1) {
               this.reportSchemaError("src-redefine.6.1.1", new Object[]{new Integer(var14)}, var2);
            } else if (var14 != 1) {
               if (var1.fTargetNamespace == null) {
                  this.fRedefinedRestrictedGroupRegistry.put(var12, "," + var5);
               } else {
                  this.fRedefinedRestrictedGroupRegistry.put(var12, var1.fTargetNamespace + "," + var5);
               }
            }
         } else {
            this.reportSchemaError("Internal-Error", new Object[]{"could not handle this particular <redefine>; please submit your schemas and instance document in a bug report!"}, var2);
         }
      }

   }

   private String findQName(String var1, XSDocumentInfo var2) {
      SchemaNamespaceSupport var3 = var2.fNamespaceSupport;
      int var4 = var1.indexOf(58);
      String var5 = XMLSymbols.EMPTY_STRING;
      if (var4 > 0) {
         var5 = var1.substring(0, var4);
      }

      String var6 = var3.getURI(this.fSymbolTable.addSymbol(var5));
      String var7 = var4 == 0 ? var1 : var1.substring(var4 + 1);
      if (var5 == XMLSymbols.EMPTY_STRING && var6 == null && var2.fIsChameleonSchema) {
         var6 = var2.fTargetNamespace;
      }

      return var6 == null ? "," + var7 : var6 + "," + var7;
   }

   private int changeRedefineGroup(String var1, String var2, String var3, Element var4, XSDocumentInfo var5) {
      int var6 = 0;

      for(Element var7 = DOMUtil.getFirstChildElement(var4); var7 != null; var7 = DOMUtil.getNextSiblingElement(var7)) {
         String var8 = DOMUtil.getLocalName(var7);
         if (!var8.equals(var2)) {
            var6 += this.changeRedefineGroup(var1, var2, var3, var7, var5);
         } else {
            String var9 = var7.getAttribute(SchemaSymbols.ATT_REF);
            if (var9.length() != 0) {
               String var10 = this.findQName(var9, var5);
               if (var1.equals(var10)) {
                  String var11 = XMLSymbols.EMPTY_STRING;
                  int var12 = var9.indexOf(":");
                  if (var12 > 0) {
                     var11 = var9.substring(0, var12);
                     var7.setAttribute(SchemaSymbols.ATT_REF, var11 + ":" + var3);
                  } else {
                     var7.setAttribute(SchemaSymbols.ATT_REF, var3);
                  }

                  ++var6;
                  if (var2.equals(SchemaSymbols.ELT_GROUP)) {
                     String var13 = var7.getAttribute(SchemaSymbols.ATT_MINOCCURS);
                     String var14 = var7.getAttribute(SchemaSymbols.ATT_MAXOCCURS);
                     if (var14.length() != 0 && !var14.equals("1") || var13.length() != 0 && !var13.equals("1")) {
                        this.reportSchemaError("src-redefine.6.1.2", new Object[]{var9}, var7);
                     }
                  }
               }
            }
         }
      }

      return var6;
   }

   private XSDocumentInfo findXSDocumentForDecl(XSDocumentInfo var1, Element var2, XSDocumentInfo var3) {
      if (var3 == null) {
         return null;
      } else {
         XSDocumentInfo var5 = (XSDocumentInfo)var3;
         return var5;
      }
   }

   private boolean nonAnnotationContent(Element var1) {
      for(Element var2 = DOMUtil.getFirstChildElement(var1); var2 != null; var2 = DOMUtil.getNextSiblingElement(var2)) {
         if (!DOMUtil.getLocalName(var2).equals(SchemaSymbols.ELT_ANNOTATION)) {
            return true;
         }
      }

      return false;
   }

   private void setSchemasVisible(XSDocumentInfo var1) {
      if (DOMUtil.isHidden(var1.fSchemaElement, this.fHiddenNodes)) {
         DOMUtil.setVisible(var1.fSchemaElement, this.fHiddenNodes);
         Vector var2 = (Vector)this.fDependencyMap.get(var1);

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            this.setSchemasVisible((XSDocumentInfo)var2.elementAt(var3));
         }
      }

   }

   public SimpleLocator element2Locator(Element var1) {
      if (!(var1 instanceof ElementImpl)) {
         return null;
      } else {
         SimpleLocator var2 = new SimpleLocator();
         return this.element2Locator(var1, var2) ? var2 : null;
      }
   }

   public boolean element2Locator(Element var1, SimpleLocator var2) {
      if (var2 == null) {
         return false;
      } else if (var1 instanceof ElementImpl) {
         ElementImpl var3 = (ElementImpl)var1;
         Document var4 = var3.getOwnerDocument();
         String var5 = (String)this.fDoc2SystemId.get(DOMUtil.getRoot(var4));
         int var6 = var3.getLineNumber();
         int var7 = var3.getColumnNumber();
         var2.setValues(var5, var5, var6, var7, var3.getCharacterOffset());
         return true;
      } else {
         return false;
      }
   }

   void reportSchemaError(String var1, Object[] var2, Element var3) {
      this.reportSchemaError(var1, var2, var3, (Exception)null);
   }

   void reportSchemaError(String var1, Object[] var2, Element var3, Exception var4) {
      if (this.element2Locator(var3, this.xl)) {
         this.fErrorReporter.reportError(this.xl, "http://www.w3.org/TR/xml-schema-1", var1, var2, (short)1, var4);
      } else {
         this.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", var1, var2, (short)1, var4);
      }

   }

   void reportSchemaWarning(String var1, Object[] var2, Element var3) {
      this.reportSchemaWarning(var1, var2, var3, (Exception)null);
   }

   void reportSchemaWarning(String var1, Object[] var2, Element var3, Exception var4) {
      if (this.element2Locator(var3, this.xl)) {
         this.fErrorReporter.reportError(this.xl, "http://www.w3.org/TR/xml-schema-1", var1, var2, (short)0, var4);
      } else {
         this.fErrorReporter.reportError("http://www.w3.org/TR/xml-schema-1", var1, var2, (short)0, var4);
      }

   }

   public void setGenerateSyntheticAnnotations(boolean var1) {
      this.fSchemaParser.setFeature("http://apache.org/xml/features/generate-synthetic-annotations", var1);
   }

   private static final class SAX2XNIUtil extends ErrorHandlerWrapper {
      public static XMLParseException createXMLParseException0(SAXParseException var0) {
         return ErrorHandlerWrapper.createXMLParseException(var0);
      }

      public static XNIException createXNIException0(SAXException var0) {
         return ErrorHandlerWrapper.createXNIException(var0);
      }
   }

   private static class XSDKey {
      String systemId;
      short referType;
      String referNS;

      XSDKey(String var1, short var2, String var3) {
         this.systemId = var1;
         this.referType = var2;
         this.referNS = var3;
      }

      public int hashCode() {
         return this.referNS == null ? 0 : this.referNS.hashCode();
      }

      public boolean equals(Object var1) {
         if (!(var1 instanceof XSDKey)) {
            return false;
         } else {
            XSDKey var2 = (XSDKey)var1;
            if (this.referNS != var2.referNS) {
               return false;
            } else {
               return this.systemId != null && this.systemId.equals(var2.systemId);
            }
         }
      }
   }

   private static class XSAnnotationGrammarPool implements XMLGrammarPool {
      private XSGrammarBucket fGrammarBucket;
      private Grammar[] fInitialGrammarSet;

      private XSAnnotationGrammarPool() {
      }

      public Grammar[] retrieveInitialGrammarSet(String var1) {
         if (var1 != "http://www.w3.org/2001/XMLSchema") {
            return new Grammar[0];
         } else {
            if (this.fInitialGrammarSet == null) {
               if (this.fGrammarBucket == null) {
                  this.fInitialGrammarSet = new Grammar[]{SchemaGrammar.Schema4Annotations.INSTANCE};
               } else {
                  SchemaGrammar[] var2 = this.fGrammarBucket.getGrammars();

                  for(int var3 = 0; var3 < var2.length; ++var3) {
                     if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var2[var3].getTargetNamespace())) {
                        this.fInitialGrammarSet = var2;
                        return this.fInitialGrammarSet;
                     }
                  }

                  Grammar[] var4 = new Grammar[var2.length + 1];
                  System.arraycopy(var2, 0, var4, 0, var2.length);
                  var4[var4.length - 1] = SchemaGrammar.Schema4Annotations.INSTANCE;
                  this.fInitialGrammarSet = var4;
               }
            }

            return this.fInitialGrammarSet;
         }
      }

      public void cacheGrammars(String var1, Grammar[] var2) {
      }

      public Grammar retrieveGrammar(XMLGrammarDescription var1) {
         if (var1.getGrammarType() == "http://www.w3.org/2001/XMLSchema") {
            String var2 = ((XMLSchemaDescription)var1).getTargetNamespace();
            if (this.fGrammarBucket != null) {
               SchemaGrammar var3 = this.fGrammarBucket.getGrammar(var2);
               if (var3 != null) {
                  return var3;
               }
            }

            if (SchemaSymbols.URI_SCHEMAFORSCHEMA.equals(var2)) {
               return SchemaGrammar.Schema4Annotations.INSTANCE;
            }
         }

         return null;
      }

      public void refreshGrammars(XSGrammarBucket var1) {
         this.fGrammarBucket = var1;
         this.fInitialGrammarSet = null;
      }

      public void lockPool() {
      }

      public void unlockPool() {
      }

      public void clear() {
      }

      // $FF: synthetic method
      XSAnnotationGrammarPool(Object var1) {
         this();
      }
   }
}

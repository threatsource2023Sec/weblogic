package weblogic.apache.xerces.parsers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;
import java.util.StringTokenizer;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSParserFilter;
import org.w3c.dom.ls.LSResourceResolver;
import weblogic.apache.xerces.dom.DOMErrorImpl;
import weblogic.apache.xerces.dom.DOMMessageFormatter;
import weblogic.apache.xerces.dom.DOMStringListImpl;
import weblogic.apache.xerces.impl.Constants;
import weblogic.apache.xerces.util.DOMEntityResolverWrapper;
import weblogic.apache.xerces.util.DOMErrorHandlerWrapper;
import weblogic.apache.xerces.util.DOMUtil;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.util.XMLSymbols;
import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.NamespaceContext;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xni.XMLAttributes;
import weblogic.apache.xerces.xni.XMLDTDContentModelHandler;
import weblogic.apache.xerces.xni.XMLDTDHandler;
import weblogic.apache.xerces.xni.XMLDocumentHandler;
import weblogic.apache.xerces.xni.XMLLocator;
import weblogic.apache.xerces.xni.XMLResourceIdentifier;
import weblogic.apache.xerces.xni.XMLString;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;
import weblogic.apache.xerces.xni.parser.XMLDTDContentModelSource;
import weblogic.apache.xerces.xni.parser.XMLDTDSource;
import weblogic.apache.xerces.xni.parser.XMLDocumentSource;
import weblogic.apache.xerces.xni.parser.XMLEntityResolver;
import weblogic.apache.xerces.xni.parser.XMLInputSource;
import weblogic.apache.xerces.xni.parser.XMLParseException;
import weblogic.apache.xerces.xni.parser.XMLParserConfiguration;

public class DOMParserImpl extends AbstractDOMParser implements LSParser, DOMConfiguration {
   protected static final String NAMESPACES = "http://xml.org/sax/features/namespaces";
   protected static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
   protected static final String XMLSCHEMA = "http://apache.org/xml/features/validation/schema";
   protected static final String XMLSCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
   protected static final String DYNAMIC_VALIDATION = "http://apache.org/xml/features/validation/dynamic";
   protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
   protected static final String DISALLOW_DOCTYPE_DECL_FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
   protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
   protected static final String NAMESPACE_GROWTH = "http://apache.org/xml/features/namespace-growth";
   protected static final String TOLERATE_DUPLICATES = "http://apache.org/xml/features/internal/tolerate-duplicates";
   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
   protected static final String PSVI_AUGMENT = "http://apache.org/xml/features/validation/schema/augment-psvi";
   protected boolean fNamespaceDeclarations;
   protected String fSchemaType;
   protected boolean fBusy;
   private boolean abortNow;
   private Thread currentThread;
   protected static final boolean DEBUG = false;
   private String fSchemaLocation;
   private DOMStringList fRecognizedParameters;
   private boolean fNullFilterInUse;
   private AbortHandler abortHandler;

   public DOMParserImpl(String var1, String var2) {
      this((XMLParserConfiguration)ObjectFactory.createObject("weblogic.apache.xerces.xni.parser.XMLParserConfiguration", var1));
      if (var2 != null) {
         if (var2.equals(Constants.NS_DTD)) {
            this.fConfiguration.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", Constants.NS_DTD);
            this.fSchemaType = Constants.NS_DTD;
         } else if (var2.equals(Constants.NS_XMLSCHEMA)) {
            this.fConfiguration.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", Constants.NS_XMLSCHEMA);
         }
      }

   }

   public DOMParserImpl(XMLParserConfiguration var1) {
      super(var1);
      this.fNamespaceDeclarations = true;
      this.fSchemaType = null;
      this.fBusy = false;
      this.abortNow = false;
      this.fSchemaLocation = null;
      this.fNullFilterInUse = false;
      this.abortHandler = null;
      String[] var2 = new String[]{"canonical-form", "cdata-sections", "charset-overrides-xml-encoding", "infoset", "namespace-declarations", "split-cdata-sections", "supported-media-types-only", "certified", "well-formed", "ignore-unknown-character-denormalizations"};
      this.fConfiguration.addRecognizedFeatures(var2);
      this.fConfiguration.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
      this.fConfiguration.setFeature("namespace-declarations", true);
      this.fConfiguration.setFeature("well-formed", true);
      this.fConfiguration.setFeature("http://apache.org/xml/features/include-comments", true);
      this.fConfiguration.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", true);
      this.fConfiguration.setFeature("http://xml.org/sax/features/namespaces", true);
      this.fConfiguration.setFeature("http://apache.org/xml/features/validation/dynamic", false);
      this.fConfiguration.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", false);
      this.fConfiguration.setFeature("http://apache.org/xml/features/create-cdata-nodes", false);
      this.fConfiguration.setFeature("canonical-form", false);
      this.fConfiguration.setFeature("charset-overrides-xml-encoding", true);
      this.fConfiguration.setFeature("split-cdata-sections", true);
      this.fConfiguration.setFeature("supported-media-types-only", false);
      this.fConfiguration.setFeature("ignore-unknown-character-denormalizations", true);
      this.fConfiguration.setFeature("certified", true);

      try {
         this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema/normalized-value", false);
      } catch (XMLConfigurationException var4) {
      }

   }

   public DOMParserImpl(SymbolTable var1) {
      this((XMLParserConfiguration)ObjectFactory.createObject("weblogic.apache.xerces.xni.parser.XMLParserConfiguration", "weblogic.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", var1);
   }

   public DOMParserImpl(SymbolTable var1, XMLGrammarPool var2) {
      this((XMLParserConfiguration)ObjectFactory.createObject("weblogic.apache.xerces.xni.parser.XMLParserConfiguration", "weblogic.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", var1);
      this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/grammar-pool", var2);
   }

   public void reset() {
      super.reset();
      this.fNamespaceDeclarations = this.fConfiguration.getFeature("namespace-declarations");
      if (this.fNullFilterInUse) {
         this.fDOMFilter = null;
         this.fNullFilterInUse = false;
      }

      if (this.fSkippedElemStack != null) {
         this.fSkippedElemStack.removeAllElements();
      }

      this.fRejectedElementDepth = 0;
      this.fFilterReject = false;
      this.fSchemaType = null;
   }

   public DOMConfiguration getDomConfig() {
      return this;
   }

   public LSParserFilter getFilter() {
      return !this.fNullFilterInUse ? this.fDOMFilter : null;
   }

   public void setFilter(LSParserFilter var1) {
      if (this.fBusy && var1 == null && this.fDOMFilter != null) {
         this.fNullFilterInUse = true;
         this.fDOMFilter = DOMParserImpl.NullLSParserFilter.INSTANCE;
      } else {
         this.fDOMFilter = var1;
      }

      if (this.fSkippedElemStack == null) {
         this.fSkippedElemStack = new Stack();
      }

   }

   public void setParameter(String var1, Object var2) throws DOMException {
      if (var2 instanceof Boolean) {
         boolean var3 = (Boolean)var2;

         try {
            if (var1.equalsIgnoreCase("comments")) {
               this.fConfiguration.setFeature("http://apache.org/xml/features/include-comments", var3);
            } else if (var1.equalsIgnoreCase("datatype-normalization")) {
               this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema/normalized-value", var3);
            } else if (var1.equalsIgnoreCase("entities")) {
               this.fConfiguration.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", var3);
            } else if (var1.equalsIgnoreCase("disallow-doctype")) {
               this.fConfiguration.setFeature("http://apache.org/xml/features/disallow-doctype-decl", var3);
            } else if (!var1.equalsIgnoreCase("supported-media-types-only") && !var1.equalsIgnoreCase("normalize-characters") && !var1.equalsIgnoreCase("check-character-normalization") && !var1.equalsIgnoreCase("canonical-form")) {
               if (var1.equalsIgnoreCase("namespaces")) {
                  this.fConfiguration.setFeature("http://xml.org/sax/features/namespaces", var3);
               } else if (var1.equalsIgnoreCase("infoset")) {
                  if (var3) {
                     this.fConfiguration.setFeature("http://xml.org/sax/features/namespaces", true);
                     this.fConfiguration.setFeature("namespace-declarations", true);
                     this.fConfiguration.setFeature("http://apache.org/xml/features/include-comments", true);
                     this.fConfiguration.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", true);
                     this.fConfiguration.setFeature("http://apache.org/xml/features/validation/dynamic", false);
                     this.fConfiguration.setFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes", false);
                     this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema/normalized-value", false);
                     this.fConfiguration.setFeature("http://apache.org/xml/features/create-cdata-nodes", false);
                  }
               } else if (var1.equalsIgnoreCase("cdata-sections")) {
                  this.fConfiguration.setFeature("http://apache.org/xml/features/create-cdata-nodes", var3);
               } else if (var1.equalsIgnoreCase("namespace-declarations")) {
                  this.fConfiguration.setFeature("namespace-declarations", var3);
               } else if (!var1.equalsIgnoreCase("well-formed") && !var1.equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
                  if (var1.equalsIgnoreCase("validate")) {
                     this.fConfiguration.setFeature("http://xml.org/sax/features/validation", var3);
                     if (this.fSchemaType != Constants.NS_DTD) {
                        this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema", var3);
                        this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema-full-checking", var3);
                     }

                     if (var3) {
                        this.fConfiguration.setFeature("http://apache.org/xml/features/validation/dynamic", false);
                     }
                  } else if (var1.equalsIgnoreCase("validate-if-schema")) {
                     this.fConfiguration.setFeature("http://apache.org/xml/features/validation/dynamic", var3);
                     if (var3) {
                        this.fConfiguration.setFeature("http://xml.org/sax/features/validation", false);
                     }
                  } else if (var1.equalsIgnoreCase("element-content-whitespace")) {
                     this.fConfiguration.setFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace", var3);
                  } else if (var1.equalsIgnoreCase("psvi")) {
                     this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema/augment-psvi", true);
                     this.fConfiguration.setProperty("http://apache.org/xml/properties/dom/document-class-name", "weblogic.apache.xerces.dom.PSVIDocumentImpl");
                  } else {
                     String var4;
                     if (var1.equalsIgnoreCase("http://apache.org/xml/features/honour-all-schemaLocations")) {
                        var4 = "http://apache.org/xml/features/honour-all-schemaLocations";
                     } else if (var1.equals("http://apache.org/xml/features/namespace-growth")) {
                        var4 = "http://apache.org/xml/features/namespace-growth";
                     } else if (var1.equals("http://apache.org/xml/features/internal/tolerate-duplicates")) {
                        var4 = "http://apache.org/xml/features/internal/tolerate-duplicates";
                     } else {
                        var4 = var1.toLowerCase(Locale.ENGLISH);
                     }

                     this.fConfiguration.setFeature(var4, var3);
                  }
               } else if (!var3) {
                  throw newFeatureNotSupportedError(var1);
               }
            } else if (var3) {
               throw newFeatureNotSupportedError(var1);
            }
         } catch (XMLConfigurationException var10) {
            throw newFeatureNotFoundError(var1);
         }
      } else if (var1.equalsIgnoreCase("error-handler")) {
         if (!(var2 instanceof DOMErrorHandler) && var2 != null) {
            throw newTypeMismatchError(var1);
         }

         try {
            this.fErrorHandler = new DOMErrorHandlerWrapper((DOMErrorHandler)var2);
            this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/error-handler", this.fErrorHandler);
         } catch (XMLConfigurationException var8) {
         }
      } else if (var1.equalsIgnoreCase("resource-resolver")) {
         if (!(var2 instanceof LSResourceResolver) && var2 != null) {
            throw newTypeMismatchError(var1);
         }

         try {
            this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/entity-resolver", new DOMEntityResolverWrapper((LSResourceResolver)var2));
         } catch (XMLConfigurationException var7) {
         }
      } else if (var1.equalsIgnoreCase("schema-location")) {
         if (!(var2 instanceof String) && var2 != null) {
            throw newTypeMismatchError(var1);
         }

         try {
            if (var2 == null) {
               this.fSchemaLocation = null;
               this.fConfiguration.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", (Object)null);
            } else {
               this.fSchemaLocation = (String)var2;
               StringTokenizer var12 = new StringTokenizer(this.fSchemaLocation, " \n\t\r");
               if (var12.hasMoreTokens()) {
                  ArrayList var14 = new ArrayList();
                  var14.add(var12.nextToken());

                  while(var12.hasMoreTokens()) {
                     var14.add(var12.nextToken());
                  }

                  this.fConfiguration.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", var14.toArray());
               } else {
                  this.fConfiguration.setProperty("http://java.sun.com/xml/jaxp/properties/schemaSource", var2);
               }
            }
         } catch (XMLConfigurationException var11) {
         }
      } else if (var1.equalsIgnoreCase("schema-type")) {
         if (!(var2 instanceof String) && var2 != null) {
            throw newTypeMismatchError(var1);
         }

         try {
            if (var2 == null) {
               this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema", false);
               this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema-full-checking", false);
               this.fConfiguration.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", (Object)null);
               this.fSchemaType = null;
            } else if (var2.equals(Constants.NS_XMLSCHEMA)) {
               this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema", true);
               this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
               this.fConfiguration.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", Constants.NS_XMLSCHEMA);
               this.fSchemaType = Constants.NS_XMLSCHEMA;
            } else if (var2.equals(Constants.NS_DTD)) {
               this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema", false);
               this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema-full-checking", false);
               this.fConfiguration.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage", Constants.NS_DTD);
               this.fSchemaType = Constants.NS_DTD;
            }
         } catch (XMLConfigurationException var6) {
         }
      } else {
         if (!var1.equalsIgnoreCase("http://apache.org/xml/properties/dom/document-class-name")) {
            String var13 = var1.toLowerCase(Locale.ENGLISH);

            try {
               this.fConfiguration.setProperty(var13, var2);
               return;
            } catch (XMLConfigurationException var9) {
               try {
                  if (var1.equalsIgnoreCase("http://apache.org/xml/features/honour-all-schemaLocations")) {
                     var13 = "http://apache.org/xml/features/honour-all-schemaLocations";
                  } else if (var1.equals("http://apache.org/xml/features/namespace-growth")) {
                     var13 = "http://apache.org/xml/features/namespace-growth";
                  } else if (var1.equals("http://apache.org/xml/features/internal/tolerate-duplicates")) {
                     var13 = "http://apache.org/xml/features/internal/tolerate-duplicates";
                  }

                  this.fConfiguration.getFeature(var13);
                  throw newTypeMismatchError(var1);
               } catch (XMLConfigurationException var5) {
                  throw newFeatureNotFoundError(var1);
               }
            }
         }

         this.fConfiguration.setProperty("http://apache.org/xml/properties/dom/document-class-name", var2);
      }

   }

   public Object getParameter(String var1) throws DOMException {
      if (var1.equalsIgnoreCase("comments")) {
         return this.fConfiguration.getFeature("http://apache.org/xml/features/include-comments") ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("datatype-normalization")) {
         return this.fConfiguration.getFeature("http://apache.org/xml/features/validation/schema/normalized-value") ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("entities")) {
         return this.fConfiguration.getFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes") ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("namespaces")) {
         return this.fConfiguration.getFeature("http://xml.org/sax/features/namespaces") ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("validate")) {
         return this.fConfiguration.getFeature("http://xml.org/sax/features/validation") ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("validate-if-schema")) {
         return this.fConfiguration.getFeature("http://apache.org/xml/features/validation/dynamic") ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("element-content-whitespace")) {
         return this.fConfiguration.getFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace") ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("disallow-doctype")) {
         return this.fConfiguration.getFeature("http://apache.org/xml/features/disallow-doctype-decl") ? Boolean.TRUE : Boolean.FALSE;
      } else if (!var1.equalsIgnoreCase("infoset")) {
         if (var1.equalsIgnoreCase("cdata-sections")) {
            return this.fConfiguration.getFeature("http://apache.org/xml/features/create-cdata-nodes") ? Boolean.TRUE : Boolean.FALSE;
         } else if (!var1.equalsIgnoreCase("check-character-normalization") && !var1.equalsIgnoreCase("normalize-characters")) {
            if (!var1.equalsIgnoreCase("namespace-declarations") && !var1.equalsIgnoreCase("well-formed") && !var1.equalsIgnoreCase("ignore-unknown-character-denormalizations") && !var1.equalsIgnoreCase("canonical-form") && !var1.equalsIgnoreCase("supported-media-types-only") && !var1.equalsIgnoreCase("split-cdata-sections") && !var1.equalsIgnoreCase("charset-overrides-xml-encoding")) {
               if (var1.equalsIgnoreCase("error-handler")) {
                  return this.fErrorHandler != null ? this.fErrorHandler.getErrorHandler() : null;
               } else if (var1.equalsIgnoreCase("resource-resolver")) {
                  try {
                     XMLEntityResolver var8 = (XMLEntityResolver)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/entity-resolver");
                     if (var8 != null && var8 instanceof DOMEntityResolverWrapper) {
                        return ((DOMEntityResolverWrapper)var8).getEntityResolver();
                     }
                  } catch (XMLConfigurationException var5) {
                  }

                  return null;
               } else if (var1.equalsIgnoreCase("schema-type")) {
                  return this.fConfiguration.getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
               } else if (var1.equalsIgnoreCase("schema-location")) {
                  return this.fSchemaLocation;
               } else if (var1.equalsIgnoreCase("http://apache.org/xml/properties/internal/symbol-table")) {
                  return this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/symbol-table");
               } else if (var1.equalsIgnoreCase("http://apache.org/xml/properties/dom/document-class-name")) {
                  return this.fConfiguration.getProperty("http://apache.org/xml/properties/dom/document-class-name");
               } else {
                  String var7;
                  if (var1.equalsIgnoreCase("http://apache.org/xml/features/honour-all-schemaLocations")) {
                     var7 = "http://apache.org/xml/features/honour-all-schemaLocations";
                  } else if (var1.equals("http://apache.org/xml/features/namespace-growth")) {
                     var7 = "http://apache.org/xml/features/namespace-growth";
                  } else if (var1.equals("http://apache.org/xml/features/internal/tolerate-duplicates")) {
                     var7 = "http://apache.org/xml/features/internal/tolerate-duplicates";
                  } else {
                     var7 = var1.toLowerCase(Locale.ENGLISH);
                  }

                  try {
                     return this.fConfiguration.getFeature(var7) ? Boolean.TRUE : Boolean.FALSE;
                  } catch (XMLConfigurationException var6) {
                     try {
                        return this.fConfiguration.getProperty(var7);
                     } catch (XMLConfigurationException var4) {
                        throw newFeatureNotFoundError(var1);
                     }
                  }
               }
            } else {
               return this.fConfiguration.getFeature(var1.toLowerCase(Locale.ENGLISH)) ? Boolean.TRUE : Boolean.FALSE;
            }
         } else {
            return Boolean.FALSE;
         }
      } else {
         boolean var2 = this.fConfiguration.getFeature("http://xml.org/sax/features/namespaces") && this.fConfiguration.getFeature("namespace-declarations") && this.fConfiguration.getFeature("http://apache.org/xml/features/include-comments") && this.fConfiguration.getFeature("http://apache.org/xml/features/dom/include-ignorable-whitespace") && !this.fConfiguration.getFeature("http://apache.org/xml/features/validation/dynamic") && !this.fConfiguration.getFeature("http://apache.org/xml/features/dom/create-entity-ref-nodes") && !this.fConfiguration.getFeature("http://apache.org/xml/features/validation/schema/normalized-value") && !this.fConfiguration.getFeature("http://apache.org/xml/features/create-cdata-nodes");
         return var2 ? Boolean.TRUE : Boolean.FALSE;
      }
   }

   public boolean canSetParameter(String var1, Object var2) {
      if (var2 == null) {
         return true;
      } else if (var2 instanceof Boolean) {
         boolean var3 = (Boolean)var2;
         if (!var1.equalsIgnoreCase("supported-media-types-only") && !var1.equalsIgnoreCase("normalize-characters") && !var1.equalsIgnoreCase("check-character-normalization") && !var1.equalsIgnoreCase("canonical-form")) {
            if (!var1.equalsIgnoreCase("well-formed") && !var1.equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
               if (!var1.equalsIgnoreCase("cdata-sections") && !var1.equalsIgnoreCase("charset-overrides-xml-encoding") && !var1.equalsIgnoreCase("comments") && !var1.equalsIgnoreCase("datatype-normalization") && !var1.equalsIgnoreCase("disallow-doctype") && !var1.equalsIgnoreCase("entities") && !var1.equalsIgnoreCase("infoset") && !var1.equalsIgnoreCase("namespaces") && !var1.equalsIgnoreCase("namespace-declarations") && !var1.equalsIgnoreCase("validate") && !var1.equalsIgnoreCase("validate-if-schema") && !var1.equalsIgnoreCase("element-content-whitespace") && !var1.equalsIgnoreCase("xml-declaration")) {
                  try {
                     String var4;
                     if (var1.equalsIgnoreCase("http://apache.org/xml/features/honour-all-schemaLocations")) {
                        var4 = "http://apache.org/xml/features/honour-all-schemaLocations";
                     } else if (var1.equalsIgnoreCase("http://apache.org/xml/features/namespace-growth")) {
                        var4 = "http://apache.org/xml/features/namespace-growth";
                     } else if (var1.equalsIgnoreCase("http://apache.org/xml/features/internal/tolerate-duplicates")) {
                        var4 = "http://apache.org/xml/features/internal/tolerate-duplicates";
                     } else {
                        var4 = var1.toLowerCase(Locale.ENGLISH);
                     }

                     this.fConfiguration.getFeature(var4);
                     return true;
                  } catch (XMLConfigurationException var5) {
                     return false;
                  }
               } else {
                  return true;
               }
            } else {
               return var3;
            }
         } else {
            return !var3;
         }
      } else if (var1.equalsIgnoreCase("error-handler")) {
         return var2 instanceof DOMErrorHandler || var2 == null;
      } else if (var1.equalsIgnoreCase("resource-resolver")) {
         return var2 instanceof LSResourceResolver || var2 == null;
      } else if (!var1.equalsIgnoreCase("schema-type")) {
         if (var1.equalsIgnoreCase("schema-location")) {
            return var2 instanceof String || var2 == null;
         } else if (var1.equalsIgnoreCase("http://apache.org/xml/properties/dom/document-class-name")) {
            return true;
         } else {
            try {
               this.fConfiguration.getProperty(var1.toLowerCase(Locale.ENGLISH));
               return true;
            } catch (XMLConfigurationException var6) {
               return false;
            }
         }
      } else {
         return var2 instanceof String && (var2.equals(Constants.NS_XMLSCHEMA) || var2.equals(Constants.NS_DTD)) || var2 == null;
      }
   }

   public DOMStringList getParameterNames() {
      if (this.fRecognizedParameters == null) {
         ArrayList var1 = new ArrayList();
         var1.add("namespaces");
         var1.add("cdata-sections");
         var1.add("canonical-form");
         var1.add("namespace-declarations");
         var1.add("split-cdata-sections");
         var1.add("entities");
         var1.add("validate-if-schema");
         var1.add("validate");
         var1.add("datatype-normalization");
         var1.add("charset-overrides-xml-encoding");
         var1.add("check-character-normalization");
         var1.add("supported-media-types-only");
         var1.add("ignore-unknown-character-denormalizations");
         var1.add("normalize-characters");
         var1.add("well-formed");
         var1.add("infoset");
         var1.add("disallow-doctype");
         var1.add("element-content-whitespace");
         var1.add("comments");
         var1.add("error-handler");
         var1.add("resource-resolver");
         var1.add("schema-location");
         var1.add("schema-type");
         this.fRecognizedParameters = new DOMStringListImpl(var1);
      }

      return this.fRecognizedParameters;
   }

   public Document parseURI(String var1) throws LSException {
      if (this.fBusy) {
         throw newInvalidStateError();
      } else {
         XMLInputSource var2 = new XMLInputSource((String)null, var1, (String)null);

         try {
            this.currentThread = Thread.currentThread();
            this.fBusy = true;
            this.parse(var2);
            this.fBusy = false;
            if (this.abortNow && this.currentThread.isInterrupted()) {
               this.abortNow = false;
               Thread.interrupted();
            }
         } catch (Exception var5) {
            this.fBusy = false;
            if (this.abortNow && this.currentThread.isInterrupted()) {
               Thread.interrupted();
            }

            if (this.abortNow) {
               this.abortNow = false;
               this.restoreHandlers();
               return null;
            }

            if (var5 != AbstractDOMParser.Abort.INSTANCE) {
               if (!(var5 instanceof XMLParseException) && this.fErrorHandler != null) {
                  DOMErrorImpl var4 = new DOMErrorImpl();
                  var4.fException = var5;
                  var4.fMessage = var5.getMessage();
                  var4.fSeverity = 3;
                  this.fErrorHandler.getErrorHandler().handleError(var4);
               }

               throw (LSException)DOMUtil.createLSException((short)81, var5).fillInStackTrace();
            }
         }

         Document var3 = this.getDocument();
         this.dropDocumentReferences();
         return var3;
      }
   }

   public Document parse(LSInput var1) throws LSException {
      XMLInputSource var2 = this.dom2xmlInputSource(var1);
      if (this.fBusy) {
         throw newInvalidStateError();
      } else {
         try {
            this.currentThread = Thread.currentThread();
            this.fBusy = true;
            this.parse(var2);
            this.fBusy = false;
            if (this.abortNow && this.currentThread.isInterrupted()) {
               this.abortNow = false;
               Thread.interrupted();
            }
         } catch (Exception var5) {
            this.fBusy = false;
            if (this.abortNow && this.currentThread.isInterrupted()) {
               Thread.interrupted();
            }

            if (this.abortNow) {
               this.abortNow = false;
               this.restoreHandlers();
               return null;
            }

            if (var5 != AbstractDOMParser.Abort.INSTANCE) {
               if (!(var5 instanceof XMLParseException) && this.fErrorHandler != null) {
                  DOMErrorImpl var4 = new DOMErrorImpl();
                  var4.fException = var5;
                  var4.fMessage = var5.getMessage();
                  var4.fSeverity = 3;
                  this.fErrorHandler.getErrorHandler().handleError(var4);
               }

               throw (LSException)DOMUtil.createLSException((short)81, var5).fillInStackTrace();
            }
         }

         Document var3 = this.getDocument();
         this.dropDocumentReferences();
         return var3;
      }
   }

   private void restoreHandlers() {
      this.fConfiguration.setDocumentHandler(this);
      this.fConfiguration.setDTDHandler(this);
      this.fConfiguration.setDTDContentModelHandler(this);
   }

   public Node parseWithContext(LSInput var1, Node var2, short var3) throws DOMException, LSException {
      throw new DOMException((short)9, "Not supported");
   }

   XMLInputSource dom2xmlInputSource(LSInput var1) {
      XMLInputSource var2 = null;
      if (var1.getCharacterStream() != null) {
         var2 = new XMLInputSource(var1.getPublicId(), var1.getSystemId(), var1.getBaseURI(), var1.getCharacterStream(), "UTF-16");
      } else if (var1.getByteStream() != null) {
         var2 = new XMLInputSource(var1.getPublicId(), var1.getSystemId(), var1.getBaseURI(), var1.getByteStream(), var1.getEncoding());
      } else if (var1.getStringData() != null && var1.getStringData().length() > 0) {
         var2 = new XMLInputSource(var1.getPublicId(), var1.getSystemId(), var1.getBaseURI(), new StringReader(var1.getStringData()), "UTF-16");
      } else {
         if ((var1.getSystemId() == null || var1.getSystemId().length() <= 0) && (var1.getPublicId() == null || var1.getPublicId().length() <= 0)) {
            if (this.fErrorHandler != null) {
               DOMErrorImpl var3 = new DOMErrorImpl();
               var3.fType = "no-input-specified";
               var3.fMessage = "no-input-specified";
               var3.fSeverity = 3;
               this.fErrorHandler.getErrorHandler().handleError(var3);
            }

            throw new LSException((short)81, "no-input-specified");
         }

         var2 = new XMLInputSource(var1.getPublicId(), var1.getSystemId(), var1.getBaseURI());
      }

      return var2;
   }

   public boolean getAsync() {
      return false;
   }

   public boolean getBusy() {
      return this.fBusy;
   }

   public void abort() {
      if (this.fBusy) {
         this.fBusy = false;
         if (this.currentThread != null) {
            this.abortNow = true;
            if (this.abortHandler == null) {
               this.abortHandler = new AbortHandler();
            }

            this.fConfiguration.setDocumentHandler(this.abortHandler);
            this.fConfiguration.setDTDHandler(this.abortHandler);
            this.fConfiguration.setDTDContentModelHandler(this.abortHandler);
            if (this.currentThread == Thread.currentThread()) {
               throw AbstractDOMParser.Abort.INSTANCE;
            }

            this.currentThread.interrupt();
         }
      }

   }

   public void startElement(QName var1, XMLAttributes var2, Augmentations var3) {
      if (!this.fNamespaceDeclarations && this.fNamespaceAware) {
         int var4 = var2.getLength();

         for(int var5 = var4 - 1; var5 >= 0; --var5) {
            if (XMLSymbols.PREFIX_XMLNS == var2.getPrefix(var5) || XMLSymbols.PREFIX_XMLNS == var2.getQName(var5)) {
               var2.removeAttributeAt(var5);
            }
         }
      }

      super.startElement(var1, var2, var3);
   }

   private static DOMException newInvalidStateError() {
      String var0 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_STATE_ERR", (Object[])null);
      throw new DOMException((short)11, var0);
   }

   private static DOMException newFeatureNotSupportedError(String var0) {
      String var1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[]{var0});
      return new DOMException((short)9, var1);
   }

   private static DOMException newFeatureNotFoundError(String var0) {
      String var1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[]{var0});
      return new DOMException((short)8, var1);
   }

   private static DOMException newTypeMismatchError(String var0) {
      String var1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[]{var0});
      return new DOMException((short)17, var1);
   }

   private static final class AbortHandler implements XMLDocumentHandler, XMLDTDHandler, XMLDTDContentModelHandler {
      private XMLDocumentSource documentSource;
      private XMLDTDContentModelSource dtdContentSource;
      private XMLDTDSource dtdSource;

      private AbortHandler() {
      }

      public void startDocument(XMLLocator var1, String var2, NamespaceContext var3, Augmentations var4) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void xmlDecl(String var1, String var2, String var3, Augmentations var4) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void doctypeDecl(String var1, String var2, String var3, Augmentations var4) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void comment(XMLString var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void processingInstruction(String var1, XMLString var2, Augmentations var3) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void startElement(QName var1, XMLAttributes var2, Augmentations var3) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void emptyElement(QName var1, XMLAttributes var2, Augmentations var3) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void startGeneralEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void textDecl(String var1, String var2, Augmentations var3) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endGeneralEntity(String var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void characters(XMLString var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void ignorableWhitespace(XMLString var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endElement(QName var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void startCDATA(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endCDATA(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endDocument(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void setDocumentSource(XMLDocumentSource var1) {
         this.documentSource = var1;
      }

      public XMLDocumentSource getDocumentSource() {
         return this.documentSource;
      }

      public void startDTD(XMLLocator var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void startParameterEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endParameterEntity(String var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void startExternalSubset(XMLResourceIdentifier var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endExternalSubset(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void elementDecl(String var1, String var2, Augmentations var3) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void startAttlist(String var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void attributeDecl(String var1, String var2, String var3, String[] var4, String var5, XMLString var6, XMLString var7, Augmentations var8) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endAttlist(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void internalEntityDecl(String var1, XMLString var2, XMLString var3, Augmentations var4) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void externalEntityDecl(String var1, XMLResourceIdentifier var2, Augmentations var3) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void unparsedEntityDecl(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void notationDecl(String var1, XMLResourceIdentifier var2, Augmentations var3) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void startConditional(short var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void ignoredCharacters(XMLString var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endConditional(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endDTD(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void setDTDSource(XMLDTDSource var1) {
         this.dtdSource = var1;
      }

      public XMLDTDSource getDTDSource() {
         return this.dtdSource;
      }

      public void startContentModel(String var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void any(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void empty(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void startGroup(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void pcdata(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void element(String var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void separator(short var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void occurrence(short var1, Augmentations var2) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endGroup(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void endContentModel(Augmentations var1) throws XNIException {
         throw AbstractDOMParser.Abort.INSTANCE;
      }

      public void setDTDContentModelSource(XMLDTDContentModelSource var1) {
         this.dtdContentSource = var1;
      }

      public XMLDTDContentModelSource getDTDContentModelSource() {
         return this.dtdContentSource;
      }

      // $FF: synthetic method
      AbortHandler(Object var1) {
         this();
      }
   }

   static final class NullLSParserFilter implements LSParserFilter {
      static final NullLSParserFilter INSTANCE = new NullLSParserFilter();

      private NullLSParserFilter() {
      }

      public short acceptNode(Node var1) {
         return 1;
      }

      public int getWhatToShow() {
         return -1;
      }

      public short startElement(Element var1) {
         return 1;
      }
   }
}

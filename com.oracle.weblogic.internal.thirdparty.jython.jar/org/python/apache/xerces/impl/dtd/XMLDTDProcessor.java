package org.python.apache.xerces.impl.dtd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import org.python.apache.xerces.impl.XMLErrorReporter;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.XMLChar;
import org.python.apache.xerces.util.XMLSymbols;
import org.python.apache.xerces.xni.Augmentations;
import org.python.apache.xerces.xni.XMLDTDContentModelHandler;
import org.python.apache.xerces.xni.XMLDTDHandler;
import org.python.apache.xerces.xni.XMLLocator;
import org.python.apache.xerces.xni.XMLResourceIdentifier;
import org.python.apache.xerces.xni.XMLString;
import org.python.apache.xerces.xni.XNIException;
import org.python.apache.xerces.xni.grammars.Grammar;
import org.python.apache.xerces.xni.grammars.XMLGrammarPool;
import org.python.apache.xerces.xni.parser.XMLComponent;
import org.python.apache.xerces.xni.parser.XMLComponentManager;
import org.python.apache.xerces.xni.parser.XMLConfigurationException;
import org.python.apache.xerces.xni.parser.XMLDTDContentModelFilter;
import org.python.apache.xerces.xni.parser.XMLDTDContentModelSource;
import org.python.apache.xerces.xni.parser.XMLDTDFilter;
import org.python.apache.xerces.xni.parser.XMLDTDSource;

public class XMLDTDProcessor implements XMLComponent, XMLDTDFilter, XMLDTDContentModelFilter {
   private static final int TOP_LEVEL_SCOPE = -1;
   protected static final String VALIDATION = "http://xml.org/sax/features/validation";
   protected static final String NOTIFY_CHAR_REFS = "http://apache.org/xml/features/scanner/notify-char-refs";
   protected static final String WARN_ON_DUPLICATE_ATTDEF = "http://apache.org/xml/features/validation/warn-on-duplicate-attdef";
   protected static final String WARN_ON_UNDECLARED_ELEMDEF = "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef";
   protected static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
   protected static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
   protected static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
   protected static final String GRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
   protected static final String DTD_VALIDATOR = "http://apache.org/xml/properties/internal/validator/dtd";
   private static final String[] RECOGNIZED_FEATURES = new String[]{"http://xml.org/sax/features/validation", "http://apache.org/xml/features/validation/warn-on-duplicate-attdef", "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", "http://apache.org/xml/features/scanner/notify-char-refs"};
   private static final Boolean[] FEATURE_DEFAULTS;
   private static final String[] RECOGNIZED_PROPERTIES;
   private static final Object[] PROPERTY_DEFAULTS;
   protected boolean fValidation;
   protected boolean fDTDValidation;
   protected boolean fWarnDuplicateAttdef;
   protected boolean fWarnOnUndeclaredElemdef;
   protected SymbolTable fSymbolTable;
   protected XMLErrorReporter fErrorReporter;
   protected DTDGrammarBucket fGrammarBucket;
   protected XMLDTDValidator fValidator;
   protected XMLGrammarPool fGrammarPool;
   protected Locale fLocale;
   protected XMLDTDHandler fDTDHandler;
   protected XMLDTDSource fDTDSource;
   protected XMLDTDContentModelHandler fDTDContentModelHandler;
   protected XMLDTDContentModelSource fDTDContentModelSource;
   protected DTDGrammar fDTDGrammar;
   private boolean fPerformValidation;
   protected boolean fInDTDIgnore;
   private boolean fMixed;
   private final XMLEntityDecl fEntityDecl = new XMLEntityDecl();
   private final HashMap fNDataDeclNotations = new HashMap();
   private String fDTDElementDeclName = null;
   private final ArrayList fMixedElementTypes = new ArrayList();
   private final ArrayList fDTDElementDecls = new ArrayList();
   private HashMap fTableOfIDAttributeNames;
   private HashMap fTableOfNOTATIONAttributeNames;
   private HashMap fNotationEnumVals;

   public void reset(XMLComponentManager var1) throws XMLConfigurationException {
      boolean var2;
      try {
         var2 = var1.getFeature("http://apache.org/xml/features/internal/parser-settings");
      } catch (XMLConfigurationException var12) {
         var2 = true;
      }

      if (!var2) {
         this.reset();
      } else {
         try {
            this.fValidation = var1.getFeature("http://xml.org/sax/features/validation");
         } catch (XMLConfigurationException var11) {
            this.fValidation = false;
         }

         try {
            this.fDTDValidation = !var1.getFeature("http://apache.org/xml/features/validation/schema");
         } catch (XMLConfigurationException var10) {
            this.fDTDValidation = true;
         }

         try {
            this.fWarnDuplicateAttdef = var1.getFeature("http://apache.org/xml/features/validation/warn-on-duplicate-attdef");
         } catch (XMLConfigurationException var9) {
            this.fWarnDuplicateAttdef = false;
         }

         try {
            this.fWarnOnUndeclaredElemdef = var1.getFeature("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef");
         } catch (XMLConfigurationException var8) {
            this.fWarnOnUndeclaredElemdef = false;
         }

         this.fErrorReporter = (XMLErrorReporter)var1.getProperty("http://apache.org/xml/properties/internal/error-reporter");
         this.fSymbolTable = (SymbolTable)var1.getProperty("http://apache.org/xml/properties/internal/symbol-table");

         try {
            this.fGrammarPool = (XMLGrammarPool)var1.getProperty("http://apache.org/xml/properties/internal/grammar-pool");
         } catch (XMLConfigurationException var7) {
            this.fGrammarPool = null;
         }

         try {
            this.fValidator = (XMLDTDValidator)var1.getProperty("http://apache.org/xml/properties/internal/validator/dtd");
         } catch (XMLConfigurationException var5) {
            this.fValidator = null;
         } catch (ClassCastException var6) {
            this.fValidator = null;
         }

         if (this.fValidator != null) {
            this.fGrammarBucket = this.fValidator.getGrammarBucket();
         } else {
            this.fGrammarBucket = null;
         }

         this.reset();
      }
   }

   protected void reset() {
      this.fDTDGrammar = null;
      this.fInDTDIgnore = false;
      this.fNDataDeclNotations.clear();
      if (this.fValidation) {
         if (this.fNotationEnumVals == null) {
            this.fNotationEnumVals = new HashMap();
         }

         this.fNotationEnumVals.clear();
         this.fTableOfIDAttributeNames = new HashMap();
         this.fTableOfNOTATIONAttributeNames = new HashMap();
      }

   }

   public String[] getRecognizedFeatures() {
      return (String[])RECOGNIZED_FEATURES.clone();
   }

   public void setFeature(String var1, boolean var2) throws XMLConfigurationException {
   }

   public String[] getRecognizedProperties() {
      return (String[])RECOGNIZED_PROPERTIES.clone();
   }

   public void setProperty(String var1, Object var2) throws XMLConfigurationException {
   }

   public Boolean getFeatureDefault(String var1) {
      for(int var2 = 0; var2 < RECOGNIZED_FEATURES.length; ++var2) {
         if (RECOGNIZED_FEATURES[var2].equals(var1)) {
            return FEATURE_DEFAULTS[var2];
         }
      }

      return null;
   }

   public Object getPropertyDefault(String var1) {
      for(int var2 = 0; var2 < RECOGNIZED_PROPERTIES.length; ++var2) {
         if (RECOGNIZED_PROPERTIES[var2].equals(var1)) {
            return PROPERTY_DEFAULTS[var2];
         }
      }

      return null;
   }

   public void setDTDHandler(XMLDTDHandler var1) {
      this.fDTDHandler = var1;
   }

   public XMLDTDHandler getDTDHandler() {
      return this.fDTDHandler;
   }

   public void setDTDContentModelHandler(XMLDTDContentModelHandler var1) {
      this.fDTDContentModelHandler = var1;
   }

   public XMLDTDContentModelHandler getDTDContentModelHandler() {
      return this.fDTDContentModelHandler;
   }

   public void startExternalSubset(XMLResourceIdentifier var1, Augmentations var2) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.startExternalSubset(var1, var2);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.startExternalSubset(var1, var2);
      }

   }

   public void endExternalSubset(Augmentations var1) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.endExternalSubset(var1);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.endExternalSubset(var1);
      }

   }

   protected static void checkStandaloneEntityRef(String var0, DTDGrammar var1, XMLEntityDecl var2, XMLErrorReporter var3) throws XNIException {
      int var4 = var1.getEntityDeclIndex(var0);
      if (var4 > -1) {
         var1.getEntityDecl(var4, var2);
         if (var2.inExternal) {
            var3.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_REFERENCE_TO_EXTERNALLY_DECLARED_ENTITY_WHEN_STANDALONE", new Object[]{var0}, (short)1);
         }
      }

   }

   public void comment(XMLString var1, Augmentations var2) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.comment(var1, var2);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.comment(var1, var2);
      }

   }

   public void processingInstruction(String var1, XMLString var2, Augmentations var3) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.processingInstruction(var1, var2, var3);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.processingInstruction(var1, var2, var3);
      }

   }

   public void startDTD(XMLLocator var1, Augmentations var2) throws XNIException {
      this.fNDataDeclNotations.clear();
      this.fDTDElementDecls.clear();
      if (!this.fGrammarBucket.getActiveGrammar().isImmutable()) {
         this.fDTDGrammar = this.fGrammarBucket.getActiveGrammar();
      }

      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.startDTD(var1, var2);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.startDTD(var1, var2);
      }

   }

   public void ignoredCharacters(XMLString var1, Augmentations var2) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.ignoredCharacters(var1, var2);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.ignoredCharacters(var1, var2);
      }

   }

   public void textDecl(String var1, String var2, Augmentations var3) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.textDecl(var1, var2, var3);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.textDecl(var1, var2, var3);
      }

   }

   public void startParameterEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
      if (this.fPerformValidation && this.fDTDGrammar != null && this.fGrammarBucket.getStandalone()) {
         checkStandaloneEntityRef(var1, this.fDTDGrammar, this.fEntityDecl, this.fErrorReporter);
      }

      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.startParameterEntity(var1, var2, var3, var4);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.startParameterEntity(var1, var2, var3, var4);
      }

   }

   public void endParameterEntity(String var1, Augmentations var2) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.endParameterEntity(var1, var2);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.endParameterEntity(var1, var2);
      }

   }

   public void elementDecl(String var1, String var2, Augmentations var3) throws XNIException {
      if (this.fValidation) {
         if (this.fDTDElementDecls.contains(var1)) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ELEMENT_ALREADY_DECLARED", new Object[]{var1}, (short)1);
         } else {
            this.fDTDElementDecls.add(var1);
         }
      }

      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.elementDecl(var1, var2, var3);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.elementDecl(var1, var2, var3);
      }

   }

   public void startAttlist(String var1, Augmentations var2) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.startAttlist(var1, var2);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.startAttlist(var1, var2);
      }

   }

   public void attributeDecl(String var1, String var2, String var3, String[] var4, String var5, XMLString var6, XMLString var7, Augmentations var8) throws XNIException {
      if (var3 != XMLSymbols.fCDATASymbol && var6 != null) {
         this.normalizeDefaultAttrValue(var6);
      }

      if (this.fValidation) {
         boolean var9 = false;
         DTDGrammar var10 = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
         int var11 = var10.getElementDeclIndex(var1);
         if (var10.getAttributeDeclIndex(var11, var2) != -1) {
            var9 = true;
            if (this.fWarnDuplicateAttdef) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_DUPLICATE_ATTRIBUTE_DEFINITION", new Object[]{var1, var2}, (short)0);
            }
         }

         if (var3 == XMLSymbols.fIDSymbol) {
            if (var6 != null && var6.length != 0 && (var5 == null || var5 != XMLSymbols.fIMPLIEDSymbol && var5 != XMLSymbols.fREQUIREDSymbol)) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "IDDefaultTypeInvalid", new Object[]{var2}, (short)1);
            }

            if (!this.fTableOfIDAttributeNames.containsKey(var1)) {
               this.fTableOfIDAttributeNames.put(var1, var2);
            } else if (!var9) {
               String var12 = (String)this.fTableOfIDAttributeNames.get(var1);
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_MORE_THAN_ONE_ID_ATTRIBUTE", new Object[]{var1, var12, var2}, (short)1);
            }
         }

         String var13;
         int var16;
         if (var3 == XMLSymbols.fNOTATIONSymbol) {
            for(var16 = 0; var16 < var4.length; ++var16) {
               this.fNotationEnumVals.put(var4[var16], var2);
            }

            if (!this.fTableOfNOTATIONAttributeNames.containsKey(var1)) {
               this.fTableOfNOTATIONAttributeNames.put(var1, var2);
            } else if (!var9) {
               var13 = (String)this.fTableOfNOTATIONAttributeNames.get(var1);
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_MORE_THAN_ONE_NOTATION_ATTRIBUTE", new Object[]{var1, var13, var2}, (short)1);
            }
         }

         if (var3 == XMLSymbols.fENUMERATIONSymbol || var3 == XMLSymbols.fNOTATIONSymbol) {
            label167:
            for(var16 = 0; var16 < var4.length; ++var16) {
               for(int var17 = var16 + 1; var17 < var4.length; ++var17) {
                  if (var4[var16].equals(var4[var17])) {
                     this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", var3 == XMLSymbols.fENUMERATIONSymbol ? "MSG_DISTINCT_TOKENS_IN_ENUMERATION" : "MSG_DISTINCT_NOTATION_IN_ENUMERATION", new Object[]{var1, var4[var16], var2}, (short)1);
                     break label167;
                  }
               }
            }
         }

         boolean var18 = true;
         if (var6 != null && (var5 == null || var5 != null && var5 == XMLSymbols.fFIXEDSymbol)) {
            var13 = var6.toString();
            if (var3 != XMLSymbols.fNMTOKENSSymbol && var3 != XMLSymbols.fENTITIESSymbol && var3 != XMLSymbols.fIDREFSSymbol) {
               if (var3 != XMLSymbols.fENTITYSymbol && var3 != XMLSymbols.fIDSymbol && var3 != XMLSymbols.fIDREFSymbol && var3 != XMLSymbols.fNOTATIONSymbol) {
                  if ((var3 == XMLSymbols.fNMTOKENSymbol || var3 == XMLSymbols.fENUMERATIONSymbol) && !this.isValidNmtoken(var13)) {
                     var18 = false;
                  }
               } else if (!this.isValidName(var13)) {
                  var18 = false;
               }

               if (var3 == XMLSymbols.fNOTATIONSymbol || var3 == XMLSymbols.fENUMERATIONSymbol) {
                  var18 = false;

                  for(int var19 = 0; var19 < var4.length; ++var19) {
                     if (var6.equals(var4[var19])) {
                        var18 = true;
                     }
                  }
               }
            } else {
               StringTokenizer var14 = new StringTokenizer(var13, " ");
               if (var14.hasMoreTokens()) {
                  do {
                     String var15 = var14.nextToken();
                     if (var3 == XMLSymbols.fNMTOKENSSymbol) {
                        if (!this.isValidNmtoken(var15)) {
                           var18 = false;
                           break;
                        }
                     } else if ((var3 == XMLSymbols.fENTITIESSymbol || var3 == XMLSymbols.fIDREFSSymbol) && !this.isValidName(var15)) {
                        var18 = false;
                        break;
                     }
                  } while(var14.hasMoreTokens());
               }
            }

            if (!var18) {
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_ATT_DEFAULT_INVALID", new Object[]{var2, var13}, (short)1);
            }
         }
      }

      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.attributeDecl(var1, var2, var3, var4, var5, var6, var7, var8);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.attributeDecl(var1, var2, var3, var4, var5, var6, var7, var8);
      }

   }

   public void endAttlist(Augmentations var1) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.endAttlist(var1);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.endAttlist(var1);
      }

   }

   public void internalEntityDecl(String var1, XMLString var2, XMLString var3, Augmentations var4) throws XNIException {
      DTDGrammar var5 = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
      int var6 = var5.getEntityDeclIndex(var1);
      if (var6 == -1) {
         if (this.fDTDGrammar != null) {
            this.fDTDGrammar.internalEntityDecl(var1, var2, var3, var4);
         }

         if (this.fDTDHandler != null) {
            this.fDTDHandler.internalEntityDecl(var1, var2, var3, var4);
         }
      }

   }

   public void externalEntityDecl(String var1, XMLResourceIdentifier var2, Augmentations var3) throws XNIException {
      DTDGrammar var4 = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
      int var5 = var4.getEntityDeclIndex(var1);
      if (var5 == -1) {
         if (this.fDTDGrammar != null) {
            this.fDTDGrammar.externalEntityDecl(var1, var2, var3);
         }

         if (this.fDTDHandler != null) {
            this.fDTDHandler.externalEntityDecl(var1, var2, var3);
         }
      }

   }

   public void unparsedEntityDecl(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
      if (this.fValidation) {
         this.fNDataDeclNotations.put(var1, var3);
      }

      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.unparsedEntityDecl(var1, var2, var3, var4);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.unparsedEntityDecl(var1, var2, var3, var4);
      }

   }

   public void notationDecl(String var1, XMLResourceIdentifier var2, Augmentations var3) throws XNIException {
      if (this.fValidation) {
         DTDGrammar var4 = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
         if (var4.getNotationDeclIndex(var1) != -1) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "UniqueNotationName", new Object[]{var1}, (short)1);
         }
      }

      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.notationDecl(var1, var2, var3);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.notationDecl(var1, var2, var3);
      }

   }

   public void startConditional(short var1, Augmentations var2) throws XNIException {
      this.fInDTDIgnore = var1 == 1;
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.startConditional(var1, var2);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.startConditional(var1, var2);
      }

   }

   public void endConditional(Augmentations var1) throws XNIException {
      this.fInDTDIgnore = false;
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.endConditional(var1);
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.endConditional(var1);
      }

   }

   public void endDTD(Augmentations var1) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.endDTD(var1);
         if (this.fGrammarPool != null) {
            this.fGrammarPool.cacheGrammars("http://www.w3.org/TR/REC-xml", new Grammar[]{this.fDTDGrammar});
         }
      }

      if (this.fValidation) {
         DTDGrammar var2 = this.fDTDGrammar != null ? this.fDTDGrammar : this.fGrammarBucket.getActiveGrammar();
         Iterator var3 = this.fNDataDeclNotations.entrySet().iterator();

         String var6;
         while(var3.hasNext()) {
            Map.Entry var4 = (Map.Entry)var3.next();
            String var5 = (String)var4.getValue();
            if (var2.getNotationDeclIndex(var5) == -1) {
               var6 = (String)var4.getKey();
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_NOTATION_NOT_DECLARED_FOR_UNPARSED_ENTITYDECL", new Object[]{var6, var5}, (short)1);
            }
         }

         Iterator var10 = this.fNotationEnumVals.entrySet().iterator();

         String var7;
         while(var10.hasNext()) {
            Map.Entry var11 = (Map.Entry)var10.next();
            var6 = (String)var11.getKey();
            if (var2.getNotationDeclIndex(var6) == -1) {
               var7 = (String)var11.getValue();
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MSG_NOTATION_NOT_DECLARED_FOR_NOTATIONTYPE_ATTRIBUTE", new Object[]{var7, var6}, (short)1);
            }
         }

         Iterator var12 = this.fTableOfNOTATIONAttributeNames.entrySet().iterator();

         while(var12.hasNext()) {
            Map.Entry var13 = (Map.Entry)var12.next();
            var7 = (String)var13.getKey();
            int var8 = var2.getElementDeclIndex(var7);
            if (var2.getContentSpecType(var8) == 1) {
               String var9 = (String)var13.getValue();
               this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "NoNotationOnEmptyElement", new Object[]{var7, var9}, (short)1);
            }
         }

         this.fTableOfIDAttributeNames = null;
         this.fTableOfNOTATIONAttributeNames = null;
         if (this.fWarnOnUndeclaredElemdef) {
            this.checkDeclaredElements(var2);
         }
      }

      if (this.fDTDHandler != null) {
         this.fDTDHandler.endDTD(var1);
      }

   }

   public void setDTDSource(XMLDTDSource var1) {
      this.fDTDSource = var1;
   }

   public XMLDTDSource getDTDSource() {
      return this.fDTDSource;
   }

   public void setDTDContentModelSource(XMLDTDContentModelSource var1) {
      this.fDTDContentModelSource = var1;
   }

   public XMLDTDContentModelSource getDTDContentModelSource() {
      return this.fDTDContentModelSource;
   }

   public void startContentModel(String var1, Augmentations var2) throws XNIException {
      if (this.fValidation) {
         this.fDTDElementDeclName = var1;
         this.fMixedElementTypes.clear();
      }

      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.startContentModel(var1, var2);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.startContentModel(var1, var2);
      }

   }

   public void any(Augmentations var1) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.any(var1);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.any(var1);
      }

   }

   public void empty(Augmentations var1) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.empty(var1);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.empty(var1);
      }

   }

   public void startGroup(Augmentations var1) throws XNIException {
      this.fMixed = false;
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.startGroup(var1);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.startGroup(var1);
      }

   }

   public void pcdata(Augmentations var1) {
      this.fMixed = true;
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.pcdata(var1);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.pcdata(var1);
      }

   }

   public void element(String var1, Augmentations var2) throws XNIException {
      if (this.fMixed && this.fValidation) {
         if (this.fMixedElementTypes.contains(var1)) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "DuplicateTypeInMixedContent", new Object[]{this.fDTDElementDeclName, var1}, (short)1);
         } else {
            this.fMixedElementTypes.add(var1);
         }
      }

      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.element(var1, var2);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.element(var1, var2);
      }

   }

   public void separator(short var1, Augmentations var2) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.separator(var1, var2);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.separator(var1, var2);
      }

   }

   public void occurrence(short var1, Augmentations var2) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.occurrence(var1, var2);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.occurrence(var1, var2);
      }

   }

   public void endGroup(Augmentations var1) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.endGroup(var1);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.endGroup(var1);
      }

   }

   public void endContentModel(Augmentations var1) throws XNIException {
      if (this.fDTDGrammar != null) {
         this.fDTDGrammar.endContentModel(var1);
      }

      if (this.fDTDContentModelHandler != null) {
         this.fDTDContentModelHandler.endContentModel(var1);
      }

   }

   private boolean normalizeDefaultAttrValue(XMLString var1) {
      boolean var2 = true;
      int var3 = var1.offset;
      int var4 = var1.offset + var1.length;

      for(int var5 = var1.offset; var5 < var4; ++var5) {
         if (var1.ch[var5] == ' ') {
            if (!var2) {
               var1.ch[var3++] = ' ';
               var2 = true;
            }
         } else {
            if (var3 != var5) {
               var1.ch[var3] = var1.ch[var5];
            }

            ++var3;
            var2 = false;
         }
      }

      if (var3 != var4) {
         if (var2) {
            --var3;
         }

         var1.length = var3 - var1.offset;
         return true;
      } else {
         return false;
      }
   }

   protected boolean isValidNmtoken(String var1) {
      return XMLChar.isValidNmtoken(var1);
   }

   protected boolean isValidName(String var1) {
      return XMLChar.isValidName(var1);
   }

   private void checkDeclaredElements(DTDGrammar var1) {
      int var2 = var1.getFirstElementDeclIndex();

      for(XMLContentSpec var3 = new XMLContentSpec(); var2 >= 0; var2 = var1.getNextElementDeclIndex(var2)) {
         short var4 = var1.getContentSpecType(var2);
         if (var4 == 3 || var4 == 2) {
            this.checkDeclaredElements(var1, var2, var1.getContentSpecIndex(var2), var3);
         }
      }

   }

   private void checkDeclaredElements(DTDGrammar var1, int var2, int var3, XMLContentSpec var4) {
      var1.getContentSpec(var3, var4);
      if (var4.type == 0) {
         String var5 = (String)var4.value;
         if (var5 != null && var1.getElementDeclIndex(var5) == -1) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "UndeclaredElementInContentSpec", new Object[]{var1.getElementDeclName(var2).rawname, var5}, (short)0);
         }
      } else {
         int var7;
         if (var4.type != 4 && var4.type != 5) {
            if (var4.type == 2 || var4.type == 1 || var4.type == 3) {
               var7 = ((int[])var4.value)[0];
               this.checkDeclaredElements(var1, var2, var7, var4);
            }
         } else {
            var7 = ((int[])var4.value)[0];
            int var6 = ((int[])var4.otherValue)[0];
            this.checkDeclaredElements(var1, var2, var7, var4);
            this.checkDeclaredElements(var1, var2, var6, var4);
         }
      }

   }

   static {
      FEATURE_DEFAULTS = new Boolean[]{null, Boolean.FALSE, Boolean.FALSE, null};
      RECOGNIZED_PROPERTIES = new String[]{"http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/validator/dtd"};
      PROPERTY_DEFAULTS = new Object[]{null, null, null, null};
   }
}

package weblogic.apache.xerces.dom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import weblogic.apache.xerces.impl.Constants;
import weblogic.apache.xerces.impl.RevalidationHandler;
import weblogic.apache.xerces.impl.dtd.XMLDTDLoader;
import weblogic.apache.xerces.impl.dtd.XMLDTDValidator;
import weblogic.apache.xerces.impl.dv.XSSimpleType;
import weblogic.apache.xerces.impl.xs.util.SimpleLocator;
import weblogic.apache.xerces.util.AugmentationsImpl;
import weblogic.apache.xerces.util.NamespaceSupport;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.util.XML11Char;
import weblogic.apache.xerces.util.XMLChar;
import weblogic.apache.xerces.util.XMLSymbols;
import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.NamespaceContext;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xni.XMLAttributes;
import weblogic.apache.xerces.xni.XMLDocumentHandler;
import weblogic.apache.xerces.xni.XMLLocator;
import weblogic.apache.xerces.xni.XMLResourceIdentifier;
import weblogic.apache.xerces.xni.XMLString;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.parser.XMLComponent;
import weblogic.apache.xerces.xni.parser.XMLDocumentSource;
import weblogic.apache.xerces.xs.AttributePSVI;
import weblogic.apache.xerces.xs.ElementPSVI;
import weblogic.apache.xerces.xs.XSTypeDefinition;

public class DOMNormalizer implements XMLDocumentHandler {
   protected static final boolean DEBUG_ND = false;
   protected static final boolean DEBUG = false;
   protected static final boolean DEBUG_EVENTS = false;
   protected static final String PREFIX = "NS";
   protected DOMConfigurationImpl fConfiguration = null;
   protected CoreDocumentImpl fDocument = null;
   protected final XMLAttributesProxy fAttrProxy = new XMLAttributesProxy();
   protected final QName fQName = new QName();
   protected RevalidationHandler fValidationHandler;
   protected SymbolTable fSymbolTable;
   protected DOMErrorHandler fErrorHandler;
   private final DOMErrorImpl fError = new DOMErrorImpl();
   protected boolean fNamespaceValidation = false;
   protected boolean fPSVI = false;
   protected final NamespaceContext fNamespaceContext = new NamespaceSupport();
   protected final NamespaceContext fLocalNSBinder = new NamespaceSupport();
   protected final ArrayList fAttributeList = new ArrayList(5);
   protected final DOMLocatorImpl fLocator = new DOMLocatorImpl();
   protected Node fCurrentNode = null;
   private final QName fAttrQName = new QName();
   final XMLString fNormalizedValue = new XMLString(new char[16], 0, 0);
   public static final RuntimeException abort = new RuntimeException() {
      private static final long serialVersionUID = 5361322877988412432L;

      public Throwable fillInStackTrace() {
         return this;
      }
   };
   public static final XMLString EMPTY_STRING = new XMLString();
   private boolean fAllWhitespace = false;

   protected void normalizeDocument(CoreDocumentImpl var1, DOMConfigurationImpl var2) {
      this.fDocument = var1;
      this.fConfiguration = var2;
      this.fAllWhitespace = false;
      this.fNamespaceValidation = false;
      String var3 = this.fDocument.getXmlVersion();
      String var4 = null;
      String[] var5 = null;
      this.fSymbolTable = (SymbolTable)this.fConfiguration.getProperty("http://apache.org/xml/properties/internal/symbol-table");
      this.fNamespaceContext.reset();
      this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, (String)null);
      if ((this.fConfiguration.features & 64) != 0) {
         String var6 = (String)this.fConfiguration.getProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage");
         if (var6 != null && var6.equals(Constants.NS_XMLSCHEMA)) {
            var4 = "http://www.w3.org/2001/XMLSchema";
            this.fValidationHandler = CoreDOMImplementationImpl.singleton.getValidator(var4, var3);
            this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema", true);
            this.fConfiguration.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
            this.fNamespaceValidation = true;
            this.fPSVI = (this.fConfiguration.features & 128) != 0;
         } else {
            var4 = "http://www.w3.org/TR/REC-xml";
            if (var6 != null) {
               var5 = (String[])this.fConfiguration.getProperty("http://java.sun.com/xml/jaxp/properties/schemaSource");
            }

            this.fConfiguration.setDTDValidatorFactory(var3);
            this.fValidationHandler = CoreDOMImplementationImpl.singleton.getValidator(var4, var3);
            this.fPSVI = false;
         }

         this.fConfiguration.setFeature("http://xml.org/sax/features/validation", true);
         this.fDocument.clearIdentifiers();
         if (this.fValidationHandler != null) {
            ((XMLComponent)this.fValidationHandler).reset(this.fConfiguration);
         }
      } else {
         this.fValidationHandler = null;
      }

      this.fErrorHandler = (DOMErrorHandler)this.fConfiguration.getParameter("error-handler");
      if (this.fValidationHandler != null) {
         this.fValidationHandler.setDocumentHandler(this);
         this.fValidationHandler.startDocument(new SimpleLocator(this.fDocument.fDocumentURI, this.fDocument.fDocumentURI, -1, -1), this.fDocument.encoding, this.fNamespaceContext, (Augmentations)null);
         this.fValidationHandler.xmlDecl(this.fDocument.getXmlVersion(), this.fDocument.getXmlEncoding(), this.fDocument.getXmlStandalone() ? "yes" : "no", (Augmentations)null);
      }

      try {
         if (var4 == "http://www.w3.org/TR/REC-xml") {
            this.processDTD(var3, var5 != null ? var5[0] : null);
         }

         Node var7;
         for(Node var9 = this.fDocument.getFirstChild(); var9 != null; var9 = var7) {
            var7 = var9.getNextSibling();
            var9 = this.normalizeNode(var9);
            if (var9 != null) {
               var7 = var9;
            }
         }

         if (this.fValidationHandler != null) {
            this.fValidationHandler.endDocument((Augmentations)null);
            this.fValidationHandler.setDocumentHandler((XMLDocumentHandler)null);
            CoreDOMImplementationImpl.singleton.releaseValidator(var4, var3, this.fValidationHandler);
            this.fValidationHandler = null;
         }

      } catch (RuntimeException var8) {
         if (this.fValidationHandler != null) {
            this.fValidationHandler.setDocumentHandler((XMLDocumentHandler)null);
            CoreDOMImplementationImpl.singleton.releaseValidator(var4, var3, this.fValidationHandler);
            this.fValidationHandler = null;
         }

         if (var8 != abort) {
            throw var8;
         }
      }
   }

   protected Node normalizeNode(Node var1) {
      short var2 = ((Node)var1).getNodeType();
      this.fLocator.fRelatedNode = (Node)var1;
      boolean var3;
      Node var4;
      Node var5;
      Node var6;
      String var10;
      switch (var2) {
         case 1:
            if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0 && this.fDocument.isXMLVersionChanged()) {
               if (this.fNamespaceValidation) {
                  var3 = CoreDocumentImpl.isValidQName(((Node)var1).getPrefix(), ((Node)var1).getLocalName(), this.fDocument.isXML11Version());
               } else {
                  var3 = CoreDocumentImpl.isXMLName(((Node)var1).getNodeName(), this.fDocument.isXML11Version());
               }

               if (!var3) {
                  var10 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[]{"Element", ((Node)var1).getNodeName()});
                  reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var10, (short)2, "wf-invalid-character-in-node-name");
               }
            }

            this.fNamespaceContext.pushContext();
            this.fLocalNSBinder.reset();
            ElementImpl var17 = (ElementImpl)var1;
            if (var17.needsSyncChildren()) {
               var17.synchronizeChildren();
            }

            AttributeMap var18 = var17.hasAttributes() ? (AttributeMap)var17.getAttributes() : null;
            Attr var19;
            int var20;
            if ((this.fConfiguration.features & 1) == 0) {
               if (var18 != null) {
                  for(var20 = 0; var20 < var18.getLength(); ++var20) {
                     var19 = (Attr)var18.item(var20);
                     var19.normalize();
                     if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
                        isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, var18, var19, var19.getValue(), this.fDocument.isXML11Version());
                        if (this.fDocument.isXMLVersionChanged()) {
                           if (this.fNamespaceValidation) {
                              var3 = CoreDocumentImpl.isValidQName(((Node)var1).getPrefix(), ((Node)var1).getLocalName(), this.fDocument.isXML11Version());
                           } else {
                              var3 = CoreDocumentImpl.isXMLName(((Node)var1).getNodeName(), this.fDocument.isXML11Version());
                           }

                           if (!var3) {
                              String var22 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[]{"Attr", ((Node)var1).getNodeName()});
                              reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var22, (short)2, "wf-invalid-character-in-node-name");
                           }
                        }
                     }
                  }
               }
            } else {
               this.namespaceFixUp(var17, var18);
               if ((this.fConfiguration.features & 512) == 0) {
                  if (var18 == null) {
                     var18 = var17.hasAttributes() ? (AttributeMap)var17.getAttributes() : null;
                  }

                  if (var18 != null) {
                     for(var20 = 0; var20 < var18.getLength(); ++var20) {
                        var19 = (Attr)var18.getItem(var20);
                        if (XMLSymbols.PREFIX_XMLNS.equals(var19.getPrefix()) || XMLSymbols.PREFIX_XMLNS.equals(var19.getName())) {
                           var17.removeAttributeNode(var19);
                           --var20;
                        }
                     }
                  }
               }
            }

            if (this.fValidationHandler != null) {
               this.fAttrProxy.setAttributes(var18, this.fDocument, var17);
               this.updateQName(var17, this.fQName);
               this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = (Node)var1;
               this.fCurrentNode = (Node)var1;
               this.fValidationHandler.startElement(this.fQName, this.fAttrProxy, (Augmentations)null);
            }

            Node var21;
            for(var6 = var17.getFirstChild(); var6 != null; var6 = var21) {
               var21 = var6.getNextSibling();
               var6 = this.normalizeNode(var6);
               if (var6 != null) {
                  var21 = var6;
               }
            }

            if (this.fValidationHandler != null) {
               this.updateQName(var17, this.fQName);
               this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = (Node)var1;
               this.fCurrentNode = (Node)var1;
               this.fValidationHandler.endElement(this.fQName, (Augmentations)null);
            }

            this.fNamespaceContext.popContext();
         case 2:
         case 6:
         case 9:
         case 10:
         default:
            break;
         case 3:
            var4 = ((Node)var1).getNextSibling();
            if (var4 != null && var4.getNodeType() == 3) {
               ((Text)var1).appendData(var4.getNodeValue());
               ((Node)var1).getParentNode().removeChild(var4);
               return (Node)var1;
            }

            if (((Node)var1).getNodeValue().length() == 0) {
               ((Node)var1).getParentNode().removeChild((Node)var1);
            } else {
               short var16 = var4 != null ? var4.getNodeType() : -1;
               if (var16 == -1 || ((this.fConfiguration.features & 4) != 0 || var16 != 6) && ((this.fConfiguration.features & 32) != 0 || var16 != 8) && ((this.fConfiguration.features & 8) != 0 || var16 != 4)) {
                  if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
                     isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, ((Node)var1).getNodeValue(), this.fDocument.isXML11Version());
                  }

                  if (this.fValidationHandler != null) {
                     this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = (Node)var1;
                     this.fCurrentNode = (Node)var1;
                     this.fValidationHandler.characterData(((Node)var1).getNodeValue(), (Augmentations)null);
                     if (!this.fNamespaceValidation) {
                        if (this.fAllWhitespace) {
                           this.fAllWhitespace = false;
                           ((TextImpl)var1).setIgnorableWhitespace(true);
                        } else {
                           ((TextImpl)var1).setIgnorableWhitespace(false);
                        }
                     }
                  }
               }
            }
            break;
         case 4:
            if ((this.fConfiguration.features & 8) == 0) {
               var4 = ((Node)var1).getPreviousSibling();
               if (var4 != null && var4.getNodeType() == 3) {
                  ((Text)var4).appendData(((Node)var1).getNodeValue());
                  ((Node)var1).getParentNode().removeChild((Node)var1);
                  return var4;
               }

               Text var14 = this.fDocument.createTextNode(((Node)var1).getNodeValue());
               var6 = ((Node)var1).getParentNode();
               var6.replaceChild(var14, (Node)var1);
               return var14;
            }

            if (this.fValidationHandler != null) {
               this.fConfiguration.fErrorHandlerWrapper.fCurrentNode = (Node)var1;
               this.fCurrentNode = (Node)var1;
               this.fValidationHandler.startCDATA((Augmentations)null);
               this.fValidationHandler.characterData(((Node)var1).getNodeValue(), (Augmentations)null);
               this.fValidationHandler.endCDATA((Augmentations)null);
            }

            var10 = ((Node)var1).getNodeValue();
            if ((this.fConfiguration.features & 16) != 0) {
               var6 = ((Node)var1).getParentNode();
               if (this.fDocument.errorChecking) {
                  isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, ((Node)var1).getNodeValue(), this.fDocument.isXML11Version());
               }

               int var13;
               while((var13 = var10.indexOf("]]>")) >= 0) {
                  ((Node)var1).setNodeValue(var10.substring(0, var13 + 2));
                  var10 = var10.substring(var13 + 2);
                  Object var7 = var1;
                  CDATASection var8 = this.fDocument.createCDATASection(var10);
                  var6.insertBefore(var8, ((Node)var1).getNextSibling());
                  var1 = var8;
                  this.fLocator.fRelatedNode = (Node)var7;
                  String var9 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "cdata-sections-splitted", (Object[])null);
                  reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var9, (short)1, "cdata-sections-splitted");
               }

               return null;
            } else {
               if (this.fDocument.errorChecking) {
                  isCDataWF(this.fErrorHandler, this.fError, this.fLocator, var10, this.fDocument.isXML11Version());
               }
               break;
            }
         case 5:
            if ((this.fConfiguration.features & 4) == 0) {
               var4 = ((Node)var1).getPreviousSibling();
               var5 = ((Node)var1).getParentNode();
               ((EntityReferenceImpl)var1).setReadOnly(false, true);
               this.expandEntityRef(var5, (Node)var1);
               var5.removeChild((Node)var1);
               var6 = var4 != null ? var4.getNextSibling() : var5.getFirstChild();
               if (var4 != null && var6 != null && var4.getNodeType() == 3 && var6.getNodeType() == 3) {
                  return var4;
               }

               return var6;
            }

            if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0 && this.fDocument.isXMLVersionChanged()) {
               CoreDocumentImpl.isXMLName(((Node)var1).getNodeName(), this.fDocument.isXML11Version());
            }
            break;
         case 7:
            if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
               ProcessingInstruction var11 = (ProcessingInstruction)var1;
               String var12 = var11.getTarget();
               if (this.fDocument.isXML11Version()) {
                  var3 = XML11Char.isXML11ValidName(var12);
               } else {
                  var3 = XMLChar.isValidName(var12);
               }

               if (!var3) {
                  String var15 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[]{"Element", ((Node)var1).getNodeName()});
                  reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var15, (short)2, "wf-invalid-character-in-node-name");
               }

               isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, var11.getData(), this.fDocument.isXML11Version());
            }

            if (this.fValidationHandler != null) {
               this.fValidationHandler.processingInstruction(((ProcessingInstruction)var1).getTarget(), EMPTY_STRING, (Augmentations)null);
            }
            break;
         case 8:
            if ((this.fConfiguration.features & 32) == 0) {
               var4 = ((Node)var1).getPreviousSibling();
               var5 = ((Node)var1).getParentNode();
               var5.removeChild((Node)var1);
               if (var4 != null && var4.getNodeType() == 3) {
                  var6 = var4.getNextSibling();
                  if (var6 != null && var6.getNodeType() == 3) {
                     ((TextImpl)var6).insertData(0, var4.getNodeValue());
                     var5.removeChild(var4);
                     return var6;
                  }
               }
            } else {
               if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
                  var10 = ((Comment)var1).getData();
                  isCommentWF(this.fErrorHandler, this.fError, this.fLocator, var10, this.fDocument.isXML11Version());
               }

               if (this.fValidationHandler != null) {
                  this.fValidationHandler.comment(EMPTY_STRING, (Augmentations)null);
               }
            }
      }

      return null;
   }

   private void processDTD(String var1, String var2) {
      String var3 = null;
      String var4 = null;
      String var5 = var2;
      String var6 = this.fDocument.getDocumentURI();
      String var7 = null;
      DocumentType var8 = this.fDocument.getDoctype();
      if (var8 != null) {
         var3 = var8.getName();
         var4 = var8.getPublicId();
         if (var2 == null || var2.length() == 0) {
            var5 = var8.getSystemId();
         }

         var7 = var8.getInternalSubset();
      } else {
         Element var9 = this.fDocument.getDocumentElement();
         if (var9 == null) {
            return;
         }

         var3 = var9.getNodeName();
         if (var2 == null || var2.length() == 0) {
            return;
         }
      }

      XMLDTDLoader var17 = null;

      try {
         this.fValidationHandler.doctypeDecl(var3, var4, var5, (Augmentations)null);
         var17 = CoreDOMImplementationImpl.singleton.getDTDLoader(var1);
         var17.setFeature("http://xml.org/sax/features/validation", true);
         var17.setEntityResolver(this.fConfiguration.getEntityResolver());
         var17.setErrorHandler(this.fConfiguration.getErrorHandler());
         var17.loadGrammarWithContext((XMLDTDValidator)this.fValidationHandler, var3, var4, var5, var6, var7);
      } catch (IOException var15) {
      } finally {
         if (var17 != null) {
            CoreDOMImplementationImpl.singleton.releaseDTDLoader(var1, var17);
         }

      }

   }

   protected final void expandEntityRef(Node var1, Node var2) {
      Node var4;
      for(Node var3 = var2.getFirstChild(); var3 != null; var3 = var4) {
         var4 = var3.getNextSibling();
         var1.insertBefore(var3, var2);
      }

   }

   protected final void namespaceFixUp(ElementImpl var1, AttributeMap var2) {
      String var3;
      String var4;
      String var5;
      int var6;
      Attr var7;
      String var8;
      if (var2 != null) {
         for(var6 = 0; var6 < var2.getLength(); ++var6) {
            var7 = (Attr)var2.getItem(var6);
            var4 = var7.getNamespaceURI();
            if (var4 != null && var4.equals(NamespaceContext.XMLNS_URI)) {
               var3 = var7.getNodeValue();
               if (var3 == null) {
                  var3 = XMLSymbols.EMPTY_STRING;
               }

               if (this.fDocument.errorChecking && var3.equals(NamespaceContext.XMLNS_URI)) {
                  this.fLocator.fRelatedNode = var7;
                  var8 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CantBindXMLNS", (Object[])null);
                  reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var8, (short)2, "CantBindXMLNS");
               } else {
                  var5 = var7.getPrefix();
                  var5 = var5 != null && var5.length() != 0 ? this.fSymbolTable.addSymbol(var5) : XMLSymbols.EMPTY_STRING;
                  var8 = this.fSymbolTable.addSymbol(var7.getLocalName());
                  if (var5 == XMLSymbols.PREFIX_XMLNS) {
                     var3 = this.fSymbolTable.addSymbol(var3);
                     if (var3.length() != 0) {
                        this.fNamespaceContext.declarePrefix(var8, var3);
                     }
                  } else {
                     var3 = this.fSymbolTable.addSymbol(var3);
                     this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, var3.length() != 0 ? var3 : null);
                  }
               }
            }
         }
      }

      var4 = var1.getNamespaceURI();
      var5 = var1.getPrefix();
      if (var4 != null) {
         var4 = this.fSymbolTable.addSymbol(var4);
         var5 = var5 != null && var5.length() != 0 ? this.fSymbolTable.addSymbol(var5) : XMLSymbols.EMPTY_STRING;
         if (this.fNamespaceContext.getURI(var5) != var4) {
            this.addNamespaceDecl(var5, var4, var1);
            this.fLocalNSBinder.declarePrefix(var5, var4);
            this.fNamespaceContext.declarePrefix(var5, var4);
         }
      } else if (var1.getLocalName() == null) {
         String var11;
         if (this.fNamespaceValidation) {
            var11 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalElementName", new Object[]{var1.getNodeName()});
            reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var11, (short)3, "NullLocalElementName");
         } else {
            var11 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalElementName", new Object[]{var1.getNodeName()});
            reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var11, (short)2, "NullLocalElementName");
         }
      } else {
         var4 = this.fNamespaceContext.getURI(XMLSymbols.EMPTY_STRING);
         if (var4 != null && var4.length() > 0) {
            this.addNamespaceDecl(XMLSymbols.EMPTY_STRING, XMLSymbols.EMPTY_STRING, var1);
            this.fLocalNSBinder.declarePrefix(XMLSymbols.EMPTY_STRING, (String)null);
            this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, (String)null);
         }
      }

      if (var2 != null) {
         var2.cloneMap(this.fAttributeList);

         for(var6 = 0; var6 < this.fAttributeList.size(); ++var6) {
            var7 = (Attr)this.fAttributeList.get(var6);
            this.fLocator.fRelatedNode = var7;
            var7.normalize();
            var3 = var7.getValue();
            var4 = var7.getNamespaceURI();
            if (var3 == null) {
               var3 = XMLSymbols.EMPTY_STRING;
            }

            String var9;
            if (this.fDocument.errorChecking && (this.fConfiguration.features & 256) != 0) {
               isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, var2, var7, var3, this.fDocument.isXML11Version());
               if (this.fDocument.isXMLVersionChanged()) {
                  boolean var12;
                  if (this.fNamespaceValidation) {
                     var12 = CoreDocumentImpl.isValidQName(var7.getPrefix(), var7.getLocalName(), this.fDocument.isXML11Version());
                  } else {
                     var12 = CoreDocumentImpl.isXMLName(var7.getNodeName(), this.fDocument.isXML11Version());
                  }

                  if (!var12) {
                     var9 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[]{"Attr", var7.getNodeName()});
                     reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var9, (short)2, "wf-invalid-character-in-node-name");
                  }
               }
            }

            if (var4 == null) {
               ((AttrImpl)var7).setIdAttribute(false);
               if (var7.getLocalName() == null) {
                  if (this.fNamespaceValidation) {
                     var8 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalAttrName", new Object[]{var7.getNodeName()});
                     reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var8, (short)3, "NullLocalAttrName");
                  } else {
                     var8 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NullLocalAttrName", new Object[]{var7.getNodeName()});
                     reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var8, (short)2, "NullLocalAttrName");
                  }
               }
            } else {
               var5 = var7.getPrefix();
               var5 = var5 != null && var5.length() != 0 ? this.fSymbolTable.addSymbol(var5) : XMLSymbols.EMPTY_STRING;
               this.fSymbolTable.addSymbol(var7.getLocalName());
               if (var4 == null || !var4.equals(NamespaceContext.XMLNS_URI)) {
                  ((AttrImpl)var7).setIdAttribute(false);
                  var4 = this.fSymbolTable.addSymbol(var4);
                  var8 = this.fNamespaceContext.getURI(var5);
                  if (var5 == XMLSymbols.EMPTY_STRING || var8 != var4) {
                     var9 = this.fNamespaceContext.getPrefix(var4);
                     if (var9 != null && var9 != XMLSymbols.EMPTY_STRING) {
                        var5 = var9;
                     } else {
                        if (var5 == XMLSymbols.EMPTY_STRING || this.fLocalNSBinder.getURI(var5) != null) {
                           int var10 = 1;

                           for(var5 = this.fSymbolTable.addSymbol("NS" + var10++); this.fLocalNSBinder.getURI(var5) != null; var5 = this.fSymbolTable.addSymbol("NS" + var10++)) {
                           }
                        }

                        this.addNamespaceDecl(var5, var4, var1);
                        var3 = this.fSymbolTable.addSymbol(var3);
                        this.fLocalNSBinder.declarePrefix(var5, var3);
                        this.fNamespaceContext.declarePrefix(var5, var4);
                     }

                     var7.setPrefix(var5);
                  }
               }
            }
         }
      }

   }

   protected final void addNamespaceDecl(String var1, String var2, ElementImpl var3) {
      if (var1 == XMLSymbols.EMPTY_STRING) {
         var3.setAttributeNS(NamespaceContext.XMLNS_URI, XMLSymbols.PREFIX_XMLNS, var2);
      } else {
         var3.setAttributeNS(NamespaceContext.XMLNS_URI, "xmlns:" + var1, var2);
      }

   }

   public static final void isCDataWF(DOMErrorHandler var0, DOMErrorImpl var1, DOMLocatorImpl var2, String var3, boolean var4) {
      if (var3 != null && var3.length() != 0) {
         char[] var5 = var3.toCharArray();
         int var6 = var5.length;
         int var7;
         char var8;
         int var9;
         String var10;
         char var11;
         String var12;
         if (var4) {
            var7 = 0;

            while(true) {
               label110:
               do {
                  while(var7 < var6) {
                     var8 = var5[var7++];
                     if (XML11Char.isXML11Invalid(var8)) {
                        if (!XMLChar.isHighSurrogate(var8) || var7 >= var6) {
                           break label110;
                        }

                        var11 = var5[var7++];
                        continue label110;
                     }

                     if (var8 == ']') {
                        var9 = var7;
                        if (var7 < var6 && var5[var7] == ']') {
                           do {
                              ++var9;
                           } while(var9 < var6 && var5[var9] == ']');

                           if (var9 < var6 && var5[var9] == '>') {
                              var10 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CDEndInContent", (Object[])null);
                              reportDOMError(var0, var1, var2, var10, (short)2, "wf-invalid-character");
                           }
                        }
                     }
                  }

                  return;
               } while(XMLChar.isLowSurrogate(var11) && XMLChar.isSupplemental(XMLChar.supplemental(var8, var11)));

               var12 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInCDSect", new Object[]{Integer.toString(var8, 16)});
               reportDOMError(var0, var1, var2, var12, (short)2, "wf-invalid-character");
            }
         } else {
            var7 = 0;

            while(true) {
               label78:
               do {
                  while(var7 < var6) {
                     var8 = var5[var7++];
                     if (XMLChar.isInvalid(var8)) {
                        if (!XMLChar.isHighSurrogate(var8) || var7 >= var6) {
                           break label78;
                        }

                        var11 = var5[var7++];
                        continue label78;
                     }

                     if (var8 == ']') {
                        var9 = var7;
                        if (var7 < var6 && var5[var7] == ']') {
                           do {
                              ++var9;
                           } while(var9 < var6 && var5[var9] == ']');

                           if (var9 < var6 && var5[var9] == '>') {
                              var10 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "CDEndInContent", (Object[])null);
                              reportDOMError(var0, var1, var2, var10, (short)2, "wf-invalid-character");
                           }
                        }
                     }
                  }

                  return;
               } while(XMLChar.isLowSurrogate(var11) && XMLChar.isSupplemental(XMLChar.supplemental(var8, var11)));

               var12 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInCDSect", new Object[]{Integer.toString(var8, 16)});
               reportDOMError(var0, var1, var2, var12, (short)2, "wf-invalid-character");
            }
         }
      }
   }

   public static final void isXMLCharWF(DOMErrorHandler var0, DOMErrorImpl var1, DOMLocatorImpl var2, String var3, boolean var4) {
      if (var3 != null && var3.length() != 0) {
         char[] var5 = var3.toCharArray();
         int var6 = var5.length;
         int var7;
         char var8;
         char var9;
         String var10;
         if (var4) {
            var7 = 0;

            while(true) {
               do {
                  do {
                     if (var7 >= var6) {
                        return;
                     }
                  } while(!XML11Char.isXML11Invalid(var5[var7++]));

                  var8 = var5[var7 - 1];
                  if (!XMLChar.isHighSurrogate(var8) || var7 >= var6) {
                     break;
                  }

                  var9 = var5[var7++];
               } while(XMLChar.isLowSurrogate(var9) && XMLChar.isSupplemental(XMLChar.supplemental(var8, var9)));

               var10 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "InvalidXMLCharInDOM", new Object[]{Integer.toString(var5[var7 - 1], 16)});
               reportDOMError(var0, var1, var2, var10, (short)2, "wf-invalid-character");
            }
         } else {
            var7 = 0;

            while(true) {
               do {
                  do {
                     if (var7 >= var6) {
                        return;
                     }
                  } while(!XMLChar.isInvalid(var5[var7++]));

                  var8 = var5[var7 - 1];
                  if (!XMLChar.isHighSurrogate(var8) || var7 >= var6) {
                     break;
                  }

                  var9 = var5[var7++];
               } while(XMLChar.isLowSurrogate(var9) && XMLChar.isSupplemental(XMLChar.supplemental(var8, var9)));

               var10 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "InvalidXMLCharInDOM", new Object[]{Integer.toString(var5[var7 - 1], 16)});
               reportDOMError(var0, var1, var2, var10, (short)2, "wf-invalid-character");
            }
         }
      }
   }

   public static final void isCommentWF(DOMErrorHandler var0, DOMErrorImpl var1, DOMLocatorImpl var2, String var3, boolean var4) {
      if (var3 != null && var3.length() != 0) {
         char[] var5 = var3.toCharArray();
         int var6 = var5.length;
         int var7;
         char var8;
         String var9;
         char var10;
         if (var4) {
            var7 = 0;

            while(true) {
               label78:
               do {
                  while(var7 < var6) {
                     var8 = var5[var7++];
                     if (XML11Char.isXML11Invalid(var8)) {
                        if (!XMLChar.isHighSurrogate(var8) || var7 >= var6) {
                           break label78;
                        }

                        var10 = var5[var7++];
                        continue label78;
                     }

                     if (var8 == '-' && var7 < var6 && var5[var7] == '-') {
                        var9 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "DashDashInComment", (Object[])null);
                        reportDOMError(var0, var1, var2, var9, (short)2, "wf-invalid-character");
                     }
                  }

                  return;
               } while(XMLChar.isLowSurrogate(var10) && XMLChar.isSupplemental(XMLChar.supplemental(var8, var10)));

               var9 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInComment", new Object[]{Integer.toString(var5[var7 - 1], 16)});
               reportDOMError(var0, var1, var2, var9, (short)2, "wf-invalid-character");
            }
         } else {
            var7 = 0;

            while(true) {
               label58:
               do {
                  while(var7 < var6) {
                     var8 = var5[var7++];
                     if (XMLChar.isInvalid(var8)) {
                        if (!XMLChar.isHighSurrogate(var8) || var7 >= var6) {
                           break label58;
                        }

                        var10 = var5[var7++];
                        continue label58;
                     }

                     if (var8 == '-' && var7 < var6 && var5[var7] == '-') {
                        var9 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "DashDashInComment", (Object[])null);
                        reportDOMError(var0, var1, var2, var9, (short)2, "wf-invalid-character");
                     }
                  }

                  return;
               } while(XMLChar.isLowSurrogate(var10) && XMLChar.isSupplemental(XMLChar.supplemental(var8, var10)));

               var9 = DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInComment", new Object[]{Integer.toString(var5[var7 - 1], 16)});
               reportDOMError(var0, var1, var2, var9, (short)2, "wf-invalid-character");
            }
         }
      }
   }

   public static final void isAttrValueWF(DOMErrorHandler var0, DOMErrorImpl var1, DOMLocatorImpl var2, NamedNodeMap var3, Attr var4, String var5, boolean var6) {
      if (var4 instanceof AttrImpl && ((AttrImpl)var4).hasStringValue()) {
         isXMLCharWF(var0, var1, var2, var5, var6);
      } else {
         NodeList var7 = var4.getChildNodes();

         for(int var8 = 0; var8 < var7.getLength(); ++var8) {
            Node var9 = var7.item(var8);
            if (var9.getNodeType() == 5) {
               Document var10 = var4.getOwnerDocument();
               Entity var11 = null;
               if (var10 != null) {
                  DocumentType var12 = var10.getDoctype();
                  if (var12 != null) {
                     NamedNodeMap var13 = var12.getEntities();
                     var11 = (Entity)var13.getNamedItemNS("*", var9.getNodeName());
                  }
               }

               if (var11 == null) {
                  String var14 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "UndeclaredEntRefInAttrValue", new Object[]{var4.getNodeName()});
                  reportDOMError(var0, var1, var2, var14, (short)2, "UndeclaredEntRefInAttrValue");
               }
            } else {
               isXMLCharWF(var0, var1, var2, var9.getNodeValue(), var6);
            }
         }
      }

   }

   public static final void reportDOMError(DOMErrorHandler var0, DOMErrorImpl var1, DOMLocatorImpl var2, String var3, short var4, String var5) {
      if (var0 != null) {
         var1.reset();
         var1.fMessage = var3;
         var1.fSeverity = var4;
         var1.fLocator = var2;
         var1.fType = var5;
         var1.fRelatedData = var2.fRelatedNode;
         if (!var0.handleError(var1)) {
            throw abort;
         }
      }

      if (var4 == 3) {
         throw abort;
      }
   }

   protected final void updateQName(Node var1, QName var2) {
      String var3 = var1.getPrefix();
      String var4 = var1.getNamespaceURI();
      String var5 = var1.getLocalName();
      var2.prefix = var3 != null && var3.length() != 0 ? this.fSymbolTable.addSymbol(var3) : null;
      var2.localpart = var5 != null ? this.fSymbolTable.addSymbol(var5) : null;
      var2.rawname = this.fSymbolTable.addSymbol(var1.getNodeName());
      var2.uri = var4 != null ? this.fSymbolTable.addSymbol(var4) : null;
   }

   final String normalizeAttributeValue(String var1, Attr var2) {
      if (!var2.getSpecified()) {
         return var1;
      } else {
         int var3 = var1.length();
         if (this.fNormalizedValue.ch.length < var3) {
            this.fNormalizedValue.ch = new char[var3];
         }

         this.fNormalizedValue.length = 0;
         boolean var4 = false;

         for(int var5 = 0; var5 < var3; ++var5) {
            char var6 = var1.charAt(var5);
            if (var6 != '\t' && var6 != '\n') {
               if (var6 == '\r') {
                  var4 = true;
                  this.fNormalizedValue.ch[this.fNormalizedValue.length++] = ' ';
                  int var7 = var5 + 1;
                  if (var7 < var3 && var1.charAt(var7) == '\n') {
                     var5 = var7;
                  }
               } else {
                  this.fNormalizedValue.ch[this.fNormalizedValue.length++] = var6;
               }
            } else {
               this.fNormalizedValue.ch[this.fNormalizedValue.length++] = ' ';
               var4 = true;
            }
         }

         if (var4) {
            var1 = this.fNormalizedValue.toString();
            var2.setValue(var1);
         }

         return var1;
      }
   }

   public void startDocument(XMLLocator var1, String var2, NamespaceContext var3, Augmentations var4) throws XNIException {
   }

   public void xmlDecl(String var1, String var2, String var3, Augmentations var4) throws XNIException {
   }

   public void doctypeDecl(String var1, String var2, String var3, Augmentations var4) throws XNIException {
   }

   public void comment(XMLString var1, Augmentations var2) throws XNIException {
   }

   public void processingInstruction(String var1, XMLString var2, Augmentations var3) throws XNIException {
   }

   public void startElement(QName var1, XMLAttributes var2, Augmentations var3) throws XNIException {
      Element var4 = (Element)this.fCurrentNode;
      int var5 = var2.getLength();

      for(int var6 = 0; var6 < var5; ++var6) {
         var2.getName(var6, this.fAttrQName);
         Attr var7 = null;
         var7 = var4.getAttributeNodeNS(this.fAttrQName.uri, this.fAttrQName.localpart);
         if (var7 == null) {
            var7 = var4.getAttributeNode(this.fAttrQName.rawname);
         }

         AttributePSVI var8 = (AttributePSVI)var2.getAugmentations(var6).getItem("ATTRIBUTE_PSVI");
         boolean var10;
         if (var8 != null) {
            Object var9 = var8.getMemberTypeDefinition();
            var10 = false;
            if (var9 != null) {
               var10 = ((XSSimpleType)var9).isIDType();
            } else {
               var9 = var8.getTypeDefinition();
               if (var9 != null) {
                  var10 = ((XSSimpleType)var9).isIDType();
               }
            }

            if (var10) {
               ((ElementImpl)var4).setIdAttributeNode(var7, true);
            }

            if (this.fPSVI) {
               ((PSVIAttrNSImpl)var7).setPSVI(var8);
            }

            ((AttrImpl)var7).setType(var9);
            if ((this.fConfiguration.features & 2) != 0) {
               String var11 = var8.getSchemaNormalizedValue();
               if (var11 != null) {
                  boolean var12 = var7.getSpecified();
                  var7.setValue(var11);
                  if (!var12) {
                     ((AttrImpl)var7).setSpecified(var12);
                  }
               }
            }
         } else {
            String var13 = null;
            var10 = Boolean.TRUE.equals(var2.getAugmentations(var6).getItem("ATTRIBUTE_DECLARED"));
            if (var10) {
               var13 = var2.getType(var6);
               if ("ID".equals(var13)) {
                  ((ElementImpl)var4).setIdAttributeNode(var7, true);
               }
            }

            ((AttrImpl)var7).setType(var13);
         }
      }

   }

   public void emptyElement(QName var1, XMLAttributes var2, Augmentations var3) throws XNIException {
      this.startElement(var1, var2, var3);
      this.endElement(var1, var3);
   }

   public void startGeneralEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
   }

   public void textDecl(String var1, String var2, Augmentations var3) throws XNIException {
   }

   public void endGeneralEntity(String var1, Augmentations var2) throws XNIException {
   }

   public void characters(XMLString var1, Augmentations var2) throws XNIException {
   }

   public void ignorableWhitespace(XMLString var1, Augmentations var2) throws XNIException {
      this.fAllWhitespace = true;
   }

   public void endElement(QName var1, Augmentations var2) throws XNIException {
      if (var2 != null) {
         ElementPSVI var3 = (ElementPSVI)var2.getItem("ELEMENT_PSVI");
         if (var3 != null) {
            ElementImpl var4 = (ElementImpl)this.fCurrentNode;
            if (this.fPSVI) {
               ((PSVIElementNSImpl)this.fCurrentNode).setPSVI(var3);
            }

            if (var4 instanceof ElementNSImpl) {
               Object var5 = var3.getMemberTypeDefinition();
               if (var5 == null) {
                  var5 = var3.getTypeDefinition();
               }

               ((ElementNSImpl)var4).setType((XSTypeDefinition)var5);
            }

            String var7 = var3.getSchemaNormalizedValue();
            if ((this.fConfiguration.features & 2) != 0) {
               if (var7 != null) {
                  var4.setTextContent(var7);
               }
            } else {
               String var6 = var4.getTextContent();
               if (var6.length() == 0 && var7 != null) {
                  var4.setTextContent(var7);
               }
            }

            return;
         }
      }

      if (this.fCurrentNode instanceof ElementNSImpl) {
         ((ElementNSImpl)this.fCurrentNode).setType((XSTypeDefinition)null);
      }

   }

   public void startCDATA(Augmentations var1) throws XNIException {
   }

   public void endCDATA(Augmentations var1) throws XNIException {
   }

   public void endDocument(Augmentations var1) throws XNIException {
   }

   public void setDocumentSource(XMLDocumentSource var1) {
   }

   public XMLDocumentSource getDocumentSource() {
      return null;
   }

   protected final class XMLAttributesProxy implements XMLAttributes {
      protected AttributeMap fAttributes;
      protected CoreDocumentImpl fDocument;
      protected ElementImpl fElement;
      protected final Vector fDTDTypes = new Vector(5);
      protected final Vector fAugmentations = new Vector(5);

      public void setAttributes(AttributeMap var1, CoreDocumentImpl var2, ElementImpl var3) {
         this.fDocument = var2;
         this.fAttributes = var1;
         this.fElement = var3;
         if (var1 != null) {
            int var4 = var1.getLength();
            this.fDTDTypes.setSize(var4);
            this.fAugmentations.setSize(var4);

            for(int var5 = 0; var5 < var4; ++var5) {
               this.fAugmentations.setElementAt(new AugmentationsImpl(), var5);
            }
         } else {
            this.fDTDTypes.setSize(0);
            this.fAugmentations.setSize(0);
         }

      }

      public int addAttribute(QName var1, String var2, String var3) {
         int var4 = this.fElement.getXercesAttribute(var1.uri, var1.localpart);
         if (var4 < 0) {
            AttrImpl var5 = (AttrImpl)((CoreDocumentImpl)this.fElement.getOwnerDocument()).createAttributeNS(var1.uri, var1.rawname, var1.localpart);
            var5.setNodeValue(var3);
            var4 = this.fElement.setXercesAttributeNode(var5);
            this.fDTDTypes.insertElementAt(var2, var4);
            this.fAugmentations.insertElementAt(new AugmentationsImpl(), var4);
            var5.setSpecified(false);
         }

         return var4;
      }

      public void removeAllAttributes() {
      }

      public void removeAttributeAt(int var1) {
      }

      public int getLength() {
         return this.fAttributes != null ? this.fAttributes.getLength() : 0;
      }

      public int getIndex(String var1) {
         return -1;
      }

      public int getIndex(String var1, String var2) {
         return -1;
      }

      public void setName(int var1, QName var2) {
      }

      public void getName(int var1, QName var2) {
         if (this.fAttributes != null) {
            DOMNormalizer.this.updateQName((Node)this.fAttributes.getItem(var1), var2);
         }

      }

      public String getPrefix(int var1) {
         if (this.fAttributes == null) {
            return null;
         } else {
            Node var2 = (Node)this.fAttributes.getItem(var1);
            String var3 = var2.getPrefix();
            var3 = var3 != null && var3.length() != 0 ? DOMNormalizer.this.fSymbolTable.addSymbol(var3) : null;
            return var3;
         }
      }

      public String getURI(int var1) {
         if (this.fAttributes != null) {
            Node var2 = (Node)this.fAttributes.getItem(var1);
            String var3 = var2.getNamespaceURI();
            var3 = var3 != null ? DOMNormalizer.this.fSymbolTable.addSymbol(var3) : null;
            return var3;
         } else {
            return null;
         }
      }

      public String getLocalName(int var1) {
         if (this.fAttributes != null) {
            Node var2 = (Node)this.fAttributes.getItem(var1);
            String var3 = var2.getLocalName();
            var3 = var3 != null ? DOMNormalizer.this.fSymbolTable.addSymbol(var3) : null;
            return var3;
         } else {
            return null;
         }
      }

      public String getQName(int var1) {
         if (this.fAttributes != null) {
            Node var2 = (Node)this.fAttributes.getItem(var1);
            String var3 = DOMNormalizer.this.fSymbolTable.addSymbol(var2.getNodeName());
            return var3;
         } else {
            return null;
         }
      }

      public void setType(int var1, String var2) {
         this.fDTDTypes.setElementAt(var2, var1);
      }

      public String getType(int var1) {
         String var2 = (String)this.fDTDTypes.elementAt(var1);
         return var2 != null ? this.getReportableType(var2) : "CDATA";
      }

      public String getType(String var1) {
         return "CDATA";
      }

      public String getType(String var1, String var2) {
         return "CDATA";
      }

      private String getReportableType(String var1) {
         return var1.charAt(0) == '(' ? "NMTOKEN" : var1;
      }

      public void setValue(int var1, String var2) {
         if (this.fAttributes != null) {
            AttrImpl var3 = (AttrImpl)this.fAttributes.getItem(var1);
            boolean var4 = var3.getSpecified();
            var3.setValue(var2);
            var3.setSpecified(var4);
         }

      }

      public String getValue(int var1) {
         return this.fAttributes != null ? this.fAttributes.item(var1).getNodeValue() : "";
      }

      public String getValue(String var1) {
         return null;
      }

      public String getValue(String var1, String var2) {
         if (this.fAttributes != null) {
            Node var3 = this.fAttributes.getNamedItemNS(var1, var2);
            return var3 != null ? var3.getNodeValue() : null;
         } else {
            return null;
         }
      }

      public void setNonNormalizedValue(int var1, String var2) {
      }

      public String getNonNormalizedValue(int var1) {
         return null;
      }

      public void setSpecified(int var1, boolean var2) {
         AttrImpl var3 = (AttrImpl)this.fAttributes.getItem(var1);
         var3.setSpecified(var2);
      }

      public boolean isSpecified(int var1) {
         return ((Attr)this.fAttributes.getItem(var1)).getSpecified();
      }

      public Augmentations getAugmentations(int var1) {
         return (Augmentations)this.fAugmentations.elementAt(var1);
      }

      public Augmentations getAugmentations(String var1, String var2) {
         return null;
      }

      public Augmentations getAugmentations(String var1) {
         return null;
      }

      public void setAugmentations(int var1, Augmentations var2) {
         this.fAugmentations.setElementAt(var2, var1);
      }
   }
}

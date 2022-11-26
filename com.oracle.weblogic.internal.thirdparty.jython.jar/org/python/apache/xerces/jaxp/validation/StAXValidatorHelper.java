package org.python.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.EntityDeclaration;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import org.python.apache.xerces.impl.XMLErrorReporter;
import org.python.apache.xerces.impl.validation.EntityState;
import org.python.apache.xerces.impl.validation.ValidationManager;
import org.python.apache.xerces.impl.xs.XMLSchemaValidator;
import org.python.apache.xerces.util.JAXPNamespaceContextWrapper;
import org.python.apache.xerces.util.StAXLocationWrapper;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.XMLAttributesImpl;
import org.python.apache.xerces.util.XMLStringBuffer;
import org.python.apache.xerces.util.XMLSymbols;
import org.python.apache.xerces.xni.Augmentations;
import org.python.apache.xerces.xni.QName;
import org.python.apache.xerces.xni.XMLDocumentHandler;
import org.python.apache.xerces.xni.XMLString;
import org.python.apache.xerces.xni.XNIException;
import org.python.apache.xerces.xni.parser.XMLParseException;
import org.xml.sax.SAXException;

final class StAXValidatorHelper implements ValidatorHelper, EntityState {
   private static final String STRING_INTERNING = "javax.xml.stream.isInterning";
   private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
   private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
   private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
   private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
   private final XMLErrorReporter fErrorReporter;
   private final XMLSchemaValidator fSchemaValidator;
   private final SymbolTable fSymbolTable;
   private final ValidationManager fValidationManager;
   private final XMLSchemaValidatorComponentManager fComponentManager;
   private final JAXPNamespaceContextWrapper fNamespaceContext;
   private final StAXLocationWrapper fStAXLocationWrapper = new StAXLocationWrapper();
   private final XMLStreamReaderLocation fXMLStreamReaderLocation = new XMLStreamReaderLocation();
   private HashMap fEntities = null;
   private boolean fStringsInternalized = false;
   private StreamHelper fStreamHelper;
   private EventHelper fEventHelper;
   private StAXDocumentHandler fStAXValidatorHandler;
   private StAXStreamResultBuilder fStAXStreamResultBuilder;
   private StAXEventResultBuilder fStAXEventResultBuilder;
   private int fDepth = 0;
   private XMLEvent fCurrentEvent = null;
   final QName fElementQName = new QName();
   final QName fAttributeQName = new QName();
   final XMLAttributesImpl fAttributes = new XMLAttributesImpl();
   final ArrayList fDeclaredPrefixes = new ArrayList();
   final XMLString fTempString = new XMLString();
   final XMLStringBuffer fStringBuffer = new XMLStringBuffer();

   public StAXValidatorHelper(XMLSchemaValidatorComponentManager var1) {
      this.fComponentManager = var1;
      this.fErrorReporter = (XMLErrorReporter)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
      this.fSchemaValidator = (XMLSchemaValidator)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/schema");
      this.fSymbolTable = (SymbolTable)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table");
      this.fValidationManager = (ValidationManager)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager");
      this.fNamespaceContext = new JAXPNamespaceContextWrapper(this.fSymbolTable);
      this.fNamespaceContext.setDeclaredPrefixes(this.fDeclaredPrefixes);
   }

   public void validate(Source var1, Result var2) throws SAXException, IOException {
      if (!(var2 instanceof StAXResult) && var2 != null) {
         throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[]{var1.getClass().getName(), var2.getClass().getName()}));
      } else {
         StAXSource var3 = (StAXSource)var1;
         StAXResult var4 = (StAXResult)var2;

         try {
            XMLStreamReader var5 = var3.getXMLStreamReader();
            if (var5 != null) {
               if (this.fStreamHelper == null) {
                  this.fStreamHelper = new StreamHelper();
               }

               this.fStreamHelper.validate(var5, var4);
            } else {
               if (this.fEventHelper == null) {
                  this.fEventHelper = new EventHelper();
               }

               this.fEventHelper.validate(var3.getXMLEventReader(), var4);
            }
         } catch (XMLStreamException var14) {
            throw new SAXException(var14);
         } catch (XMLParseException var15) {
            throw Util.toSAXParseException(var15);
         } catch (XNIException var16) {
            throw Util.toSAXException(var16);
         } finally {
            this.fCurrentEvent = null;
            this.fStAXLocationWrapper.setLocation((Location)null);
            this.fXMLStreamReaderLocation.setXMLStreamReader((XMLStreamReader)null);
            if (this.fStAXValidatorHandler != null) {
               this.fStAXValidatorHandler.setStAXResult((StAXResult)null);
            }

         }

      }
   }

   public boolean isEntityDeclared(String var1) {
      return this.fEntities != null ? this.fEntities.containsKey(var1) : false;
   }

   public boolean isEntityUnparsed(String var1) {
      if (this.fEntities != null) {
         EntityDeclaration var2 = (EntityDeclaration)this.fEntities.get(var1);
         if (var2 != null) {
            return var2.getNotationName() != null;
         }
      }

      return false;
   }

   final EntityDeclaration getEntityDeclaration(String var1) {
      return this.fEntities != null ? (EntityDeclaration)this.fEntities.get(var1) : null;
   }

   final XMLEvent getCurrentEvent() {
      return this.fCurrentEvent;
   }

   final void fillQName(QName var1, String var2, String var3, String var4) {
      if (!this.fStringsInternalized) {
         var2 = var2 != null && var2.length() > 0 ? this.fSymbolTable.addSymbol(var2) : null;
         var3 = var3 != null ? this.fSymbolTable.addSymbol(var3) : XMLSymbols.EMPTY_STRING;
         var4 = var4 != null && var4.length() > 0 ? this.fSymbolTable.addSymbol(var4) : XMLSymbols.EMPTY_STRING;
      } else {
         if (var2 != null && var2.length() == 0) {
            var2 = null;
         }

         if (var3 == null) {
            var3 = XMLSymbols.EMPTY_STRING;
         }

         if (var4 == null) {
            var4 = XMLSymbols.EMPTY_STRING;
         }
      }

      String var5 = var3;
      if (var4 != XMLSymbols.EMPTY_STRING) {
         this.fStringBuffer.clear();
         this.fStringBuffer.append(var4);
         this.fStringBuffer.append(':');
         this.fStringBuffer.append(var3);
         var5 = this.fSymbolTable.addSymbol(this.fStringBuffer.ch, this.fStringBuffer.offset, this.fStringBuffer.length);
      }

      var1.setValues(var4, var3, var5, var2);
   }

   final void setup(Location var1, StAXResult var2, boolean var3) {
      this.fDepth = 0;
      this.fComponentManager.reset();
      this.setupStAXResultHandler(var2);
      this.fValidationManager.setEntityState(this);
      if (this.fEntities != null && !this.fEntities.isEmpty()) {
         this.fEntities.clear();
      }

      this.fStAXLocationWrapper.setLocation(var1);
      this.fErrorReporter.setDocumentLocator(this.fStAXLocationWrapper);
      this.fStringsInternalized = var3;
   }

   final void processEntityDeclarations(List var1) {
      int var2 = var1 != null ? var1.size() : 0;
      if (var2 > 0) {
         if (this.fEntities == null) {
            this.fEntities = new HashMap();
         }

         for(int var3 = 0; var3 < var2; ++var3) {
            EntityDeclaration var4 = (EntityDeclaration)var1.get(var3);
            this.fEntities.put(var4.getName(), var4);
         }
      }

   }

   private void setupStAXResultHandler(StAXResult var1) {
      if (var1 == null) {
         this.fStAXValidatorHandler = null;
         this.fSchemaValidator.setDocumentHandler((XMLDocumentHandler)null);
      } else {
         XMLStreamWriter var2 = var1.getXMLStreamWriter();
         if (var2 != null) {
            if (this.fStAXStreamResultBuilder == null) {
               this.fStAXStreamResultBuilder = new StAXStreamResultBuilder(this.fNamespaceContext);
            }

            this.fStAXValidatorHandler = this.fStAXStreamResultBuilder;
            this.fStAXStreamResultBuilder.setStAXResult(var1);
         } else {
            if (this.fStAXEventResultBuilder == null) {
               this.fStAXEventResultBuilder = new StAXEventResultBuilder(this, this.fNamespaceContext);
            }

            this.fStAXValidatorHandler = this.fStAXEventResultBuilder;
            this.fStAXEventResultBuilder.setStAXResult(var1);
         }

         this.fSchemaValidator.setDocumentHandler(this.fStAXValidatorHandler);
      }
   }

   static final class XMLStreamReaderLocation implements Location {
      private XMLStreamReader reader;

      public XMLStreamReaderLocation() {
      }

      public int getCharacterOffset() {
         Location var1 = this.getLocation();
         return var1 != null ? var1.getCharacterOffset() : -1;
      }

      public int getColumnNumber() {
         Location var1 = this.getLocation();
         return var1 != null ? var1.getColumnNumber() : -1;
      }

      public int getLineNumber() {
         Location var1 = this.getLocation();
         return var1 != null ? var1.getLineNumber() : -1;
      }

      public String getPublicId() {
         Location var1 = this.getLocation();
         return var1 != null ? var1.getPublicId() : null;
      }

      public String getSystemId() {
         Location var1 = this.getLocation();
         return var1 != null ? var1.getSystemId() : null;
      }

      public void setXMLStreamReader(XMLStreamReader var1) {
         this.reader = var1;
      }

      private Location getLocation() {
         return this.reader != null ? this.reader.getLocation() : null;
      }
   }

   final class EventHelper {
      private static final int CHUNK_SIZE = 1024;
      private static final int CHUNK_MASK = 1023;
      private final char[] fCharBuffer = new char[1024];

      final void validate(XMLEventReader var1, StAXResult var2) throws SAXException, XMLStreamException {
         StAXValidatorHelper.this.fCurrentEvent = var1.peek();
         if (StAXValidatorHelper.this.fCurrentEvent != null) {
            int var3 = StAXValidatorHelper.this.fCurrentEvent.getEventType();
            if (var3 != 7 && var3 != 1) {
               throw new SAXException(JAXPValidationMessageFormatter.formatMessage(StAXValidatorHelper.this.fComponentManager.getLocale(), "StAXIllegalInitialState", (Object[])null));
            }

            StAXValidatorHelper.this.setup((Location)null, var2, false);
            StAXValidatorHelper.this.fSchemaValidator.startDocument(StAXValidatorHelper.this.fStAXLocationWrapper, (String)null, StAXValidatorHelper.this.fNamespaceContext, (Augmentations)null);

            label52:
            while(var1.hasNext()) {
               StAXValidatorHelper.this.fCurrentEvent = var1.nextEvent();
               var3 = StAXValidatorHelper.this.fCurrentEvent.getEventType();
               Characters var6;
               switch (var3) {
                  case 1:
                     ++StAXValidatorHelper.this.fDepth;
                     StartElement var4 = StAXValidatorHelper.this.fCurrentEvent.asStartElement();
                     this.fillQName(StAXValidatorHelper.this.fElementQName, var4.getName());
                     this.fillXMLAttributes(var4);
                     this.fillDeclaredPrefixes(var4);
                     StAXValidatorHelper.this.fNamespaceContext.setNamespaceContext(var4.getNamespaceContext());
                     StAXValidatorHelper.this.fStAXLocationWrapper.setLocation(var4.getLocation());
                     StAXValidatorHelper.this.fSchemaValidator.startElement(StAXValidatorHelper.this.fElementQName, StAXValidatorHelper.this.fAttributes, (Augmentations)null);
                     break;
                  case 2:
                     EndElement var5 = StAXValidatorHelper.this.fCurrentEvent.asEndElement();
                     this.fillQName(StAXValidatorHelper.this.fElementQName, var5.getName());
                     this.fillDeclaredPrefixes(var5);
                     StAXValidatorHelper.this.fStAXLocationWrapper.setLocation(var5.getLocation());
                     StAXValidatorHelper.this.fSchemaValidator.endElement(StAXValidatorHelper.this.fElementQName, (Augmentations)null);
                     if (--StAXValidatorHelper.this.fDepth <= 0) {
                        break label52;
                     }
                     break;
                  case 3:
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.processingInstruction((ProcessingInstruction)StAXValidatorHelper.this.fCurrentEvent);
                     }
                     break;
                  case 4:
                  case 6:
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        var6 = StAXValidatorHelper.this.fCurrentEvent.asCharacters();
                        StAXValidatorHelper.this.fStAXValidatorHandler.setIgnoringCharacters(true);
                        this.sendCharactersToValidator(var6.getData());
                        StAXValidatorHelper.this.fStAXValidatorHandler.setIgnoringCharacters(false);
                        StAXValidatorHelper.this.fStAXValidatorHandler.characters(var6);
                     } else {
                        this.sendCharactersToValidator(StAXValidatorHelper.this.fCurrentEvent.asCharacters().getData());
                     }
                     break;
                  case 5:
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.comment((Comment)StAXValidatorHelper.this.fCurrentEvent);
                     }
                     break;
                  case 7:
                     ++StAXValidatorHelper.this.fDepth;
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.startDocument((StartDocument)StAXValidatorHelper.this.fCurrentEvent);
                     }
                     break;
                  case 8:
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.endDocument((EndDocument)StAXValidatorHelper.this.fCurrentEvent);
                     }
                     break;
                  case 9:
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.entityReference((EntityReference)StAXValidatorHelper.this.fCurrentEvent);
                     }
                  case 10:
                  default:
                     break;
                  case 11:
                     DTD var7 = (DTD)StAXValidatorHelper.this.fCurrentEvent;
                     StAXValidatorHelper.this.processEntityDeclarations(var7.getEntities());
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.doctypeDecl(var7);
                     }
                     break;
                  case 12:
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        var6 = StAXValidatorHelper.this.fCurrentEvent.asCharacters();
                        StAXValidatorHelper.this.fStAXValidatorHandler.setIgnoringCharacters(true);
                        StAXValidatorHelper.this.fSchemaValidator.startCDATA((Augmentations)null);
                        this.sendCharactersToValidator(StAXValidatorHelper.this.fCurrentEvent.asCharacters().getData());
                        StAXValidatorHelper.this.fSchemaValidator.endCDATA((Augmentations)null);
                        StAXValidatorHelper.this.fStAXValidatorHandler.setIgnoringCharacters(false);
                        StAXValidatorHelper.this.fStAXValidatorHandler.cdata(var6);
                     } else {
                        StAXValidatorHelper.this.fSchemaValidator.startCDATA((Augmentations)null);
                        this.sendCharactersToValidator(StAXValidatorHelper.this.fCurrentEvent.asCharacters().getData());
                        StAXValidatorHelper.this.fSchemaValidator.endCDATA((Augmentations)null);
                     }
               }
            }

            StAXValidatorHelper.this.fSchemaValidator.endDocument((Augmentations)null);
         }

      }

      private void fillQName(QName var1, javax.xml.namespace.QName var2) {
         StAXValidatorHelper.this.fillQName(var1, var2.getNamespaceURI(), var2.getLocalPart(), var2.getPrefix());
      }

      private void fillXMLAttributes(StartElement var1) {
         StAXValidatorHelper.this.fAttributes.removeAllAttributes();
         Iterator var2 = var1.getAttributes();

         while(var2.hasNext()) {
            Attribute var3 = (Attribute)var2.next();
            this.fillQName(StAXValidatorHelper.this.fAttributeQName, var3.getName());
            String var4 = var3.getDTDType();
            int var5 = StAXValidatorHelper.this.fAttributes.getLength();
            StAXValidatorHelper.this.fAttributes.addAttributeNS(StAXValidatorHelper.this.fAttributeQName, var4 != null ? var4 : XMLSymbols.fCDATASymbol, var3.getValue());
            StAXValidatorHelper.this.fAttributes.setSpecified(var5, var3.isSpecified());
         }

      }

      private void fillDeclaredPrefixes(StartElement var1) {
         this.fillDeclaredPrefixes(var1.getNamespaces());
      }

      private void fillDeclaredPrefixes(EndElement var1) {
         this.fillDeclaredPrefixes(var1.getNamespaces());
      }

      private void fillDeclaredPrefixes(Iterator var1) {
         StAXValidatorHelper.this.fDeclaredPrefixes.clear();

         while(var1.hasNext()) {
            Namespace var2 = (Namespace)var1.next();
            String var3 = var2.getPrefix();
            StAXValidatorHelper.this.fDeclaredPrefixes.add(var3 != null ? var3 : "");
         }

      }

      private void sendCharactersToValidator(String var1) {
         if (var1 != null) {
            int var2 = var1.length();
            int var3 = var2 & 1023;
            if (var3 > 0) {
               var1.getChars(0, var3, this.fCharBuffer, 0);
               StAXValidatorHelper.this.fTempString.setValues(this.fCharBuffer, 0, var3);
               StAXValidatorHelper.this.fSchemaValidator.characters(StAXValidatorHelper.this.fTempString, (Augmentations)null);
            }

            int var4 = var3;

            while(var4 < var2) {
               int var10001 = var4;
               var4 += 1024;
               var1.getChars(var10001, var4, this.fCharBuffer, 0);
               StAXValidatorHelper.this.fTempString.setValues(this.fCharBuffer, 0, 1024);
               StAXValidatorHelper.this.fSchemaValidator.characters(StAXValidatorHelper.this.fTempString, (Augmentations)null);
            }
         }

      }
   }

   final class StreamHelper {
      final void validate(XMLStreamReader var1, StAXResult var2) throws SAXException, XMLStreamException {
         if (var1.hasNext()) {
            int var3 = var1.getEventType();
            if (var3 != 7 && var3 != 1) {
               throw new SAXException(JAXPValidationMessageFormatter.formatMessage(StAXValidatorHelper.this.fComponentManager.getLocale(), "StAXIllegalInitialState", (Object[])null));
            }

            StAXValidatorHelper.this.fXMLStreamReaderLocation.setXMLStreamReader(var1);
            StAXValidatorHelper.this.setup(StAXValidatorHelper.this.fXMLStreamReaderLocation, var2, Boolean.TRUE.equals(var1.getProperty("javax.xml.stream.isInterning")));
            StAXValidatorHelper.this.fSchemaValidator.startDocument(StAXValidatorHelper.this.fStAXLocationWrapper, (String)null, StAXValidatorHelper.this.fNamespaceContext, (Augmentations)null);

            do {
               switch (var3) {
                  case 1:
                     ++StAXValidatorHelper.this.fDepth;
                     StAXValidatorHelper.this.fillQName(StAXValidatorHelper.this.fElementQName, var1.getNamespaceURI(), var1.getLocalName(), var1.getPrefix());
                     this.fillXMLAttributes(var1);
                     this.fillDeclaredPrefixes(var1);
                     StAXValidatorHelper.this.fNamespaceContext.setNamespaceContext(var1.getNamespaceContext());
                     StAXValidatorHelper.this.fSchemaValidator.startElement(StAXValidatorHelper.this.fElementQName, StAXValidatorHelper.this.fAttributes, (Augmentations)null);
                     break;
                  case 2:
                     StAXValidatorHelper.this.fillQName(StAXValidatorHelper.this.fElementQName, var1.getNamespaceURI(), var1.getLocalName(), var1.getPrefix());
                     this.fillDeclaredPrefixes(var1);
                     StAXValidatorHelper.this.fNamespaceContext.setNamespaceContext(var1.getNamespaceContext());
                     StAXValidatorHelper.this.fSchemaValidator.endElement(StAXValidatorHelper.this.fElementQName, (Augmentations)null);
                     --StAXValidatorHelper.this.fDepth;
                     break;
                  case 3:
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.processingInstruction(var1);
                     }
                     break;
                  case 4:
                  case 6:
                     StAXValidatorHelper.this.fTempString.setValues(var1.getTextCharacters(), var1.getTextStart(), var1.getTextLength());
                     StAXValidatorHelper.this.fSchemaValidator.characters(StAXValidatorHelper.this.fTempString, (Augmentations)null);
                     break;
                  case 5:
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.comment(var1);
                     }
                     break;
                  case 7:
                     ++StAXValidatorHelper.this.fDepth;
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.startDocument(var1);
                     }
                  case 8:
                  case 10:
                  default:
                     break;
                  case 9:
                     if (StAXValidatorHelper.this.fStAXValidatorHandler != null) {
                        StAXValidatorHelper.this.fStAXValidatorHandler.entityReference(var1);
                     }
                     break;
                  case 11:
                     StAXValidatorHelper.this.processEntityDeclarations((List)var1.getProperty("javax.xml.stream.entities"));
                     break;
                  case 12:
                     StAXValidatorHelper.this.fSchemaValidator.startCDATA((Augmentations)null);
                     StAXValidatorHelper.this.fTempString.setValues(var1.getTextCharacters(), var1.getTextStart(), var1.getTextLength());
                     StAXValidatorHelper.this.fSchemaValidator.characters(StAXValidatorHelper.this.fTempString, (Augmentations)null);
                     StAXValidatorHelper.this.fSchemaValidator.endCDATA((Augmentations)null);
               }

               var3 = var1.next();
            } while(var1.hasNext() && StAXValidatorHelper.this.fDepth > 0);

            StAXValidatorHelper.this.fSchemaValidator.endDocument((Augmentations)null);
            if (var3 == 8 && StAXValidatorHelper.this.fStAXValidatorHandler != null) {
               StAXValidatorHelper.this.fStAXValidatorHandler.endDocument(var1);
            }
         }

      }

      private void fillXMLAttributes(XMLStreamReader var1) {
         StAXValidatorHelper.this.fAttributes.removeAllAttributes();
         int var2 = var1.getAttributeCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            StAXValidatorHelper.this.fillQName(StAXValidatorHelper.this.fAttributeQName, var1.getAttributeNamespace(var3), var1.getAttributeLocalName(var3), var1.getAttributePrefix(var3));
            String var4 = var1.getAttributeType(var3);
            StAXValidatorHelper.this.fAttributes.addAttributeNS(StAXValidatorHelper.this.fAttributeQName, var4 != null ? var4 : XMLSymbols.fCDATASymbol, var1.getAttributeValue(var3));
            StAXValidatorHelper.this.fAttributes.setSpecified(var3, var1.isAttributeSpecified(var3));
         }

      }

      private void fillDeclaredPrefixes(XMLStreamReader var1) {
         StAXValidatorHelper.this.fDeclaredPrefixes.clear();
         int var2 = var1.getNamespaceCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            String var4 = var1.getNamespacePrefix(var3);
            StAXValidatorHelper.this.fDeclaredPrefixes.add(var4 != null ? var4 : "");
         }

      }
   }
}

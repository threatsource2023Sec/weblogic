package org.python.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.SoftReference;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.python.apache.xerces.impl.XMLEntityManager;
import org.python.apache.xerces.impl.XMLErrorReporter;
import org.python.apache.xerces.impl.msg.XMLMessageFormatter;
import org.python.apache.xerces.impl.xs.XMLSchemaValidator;
import org.python.apache.xerces.parsers.SAXParser;
import org.python.apache.xerces.parsers.XML11Configuration;
import org.python.apache.xerces.xni.XMLDTDContentModelHandler;
import org.python.apache.xerces.xni.XMLDTDHandler;
import org.python.apache.xerces.xni.XMLDocumentHandler;
import org.python.apache.xerces.xni.XNIException;
import org.python.apache.xerces.xni.parser.XMLInputSource;
import org.python.apache.xerces.xni.parser.XMLParseException;
import org.python.apache.xerces.xni.parser.XMLParserConfiguration;
import org.python.apache.xml.serialize.OutputFormat;
import org.python.apache.xml.serialize.Serializer;
import org.python.apache.xml.serialize.SerializerFactory;
import org.xml.sax.SAXException;

final class StreamValidatorHelper implements ValidatorHelper {
   private static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
   private static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
   private static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
   private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
   private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
   private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
   private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
   private SoftReference fConfiguration = new SoftReference((Object)null);
   private final XMLSchemaValidator fSchemaValidator;
   private final XMLSchemaValidatorComponentManager fComponentManager;
   private SoftReference fParser = new SoftReference((Object)null);
   private SerializerFactory fSerializerFactory;

   public StreamValidatorHelper(XMLSchemaValidatorComponentManager var1) {
      this.fComponentManager = var1;
      this.fSchemaValidator = (XMLSchemaValidator)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/schema");
   }

   public void validate(Source var1, Result var2) throws SAXException, IOException {
      if (!(var2 instanceof StreamResult) && var2 != null) {
         throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceResultMismatch", new Object[]{var1.getClass().getName(), var2.getClass().getName()}));
      } else {
         StreamSource var3 = (StreamSource)var1;
         StreamResult var4 = (StreamResult)var2;
         XMLInputSource var5 = new XMLInputSource(var3.getPublicId(), var3.getSystemId(), (String)null);
         var5.setByteStream(var3.getInputStream());
         var5.setCharacterStream(var3.getReader());
         boolean var6 = false;
         XMLParserConfiguration var7 = (XMLParserConfiguration)this.fConfiguration.get();
         if (var7 == null) {
            var7 = this.initialize();
            var6 = true;
         } else if (this.fComponentManager.getFeature("http://apache.org/xml/features/internal/parser-settings")) {
            var7.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"));
            var7.setProperty("http://apache.org/xml/properties/internal/error-handler", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-handler"));
            var7.setProperty("http://apache.org/xml/properties/security-manager", this.fComponentManager.getProperty("http://apache.org/xml/properties/security-manager"));
         }

         this.fComponentManager.reset();
         if (var4 != null) {
            if (this.fSerializerFactory == null) {
               this.fSerializerFactory = SerializerFactory.getSerializerFactory("xml");
            }

            Serializer var8;
            if (var4.getWriter() != null) {
               var8 = this.fSerializerFactory.makeSerializer(var4.getWriter(), new OutputFormat());
            } else if (var4.getOutputStream() != null) {
               var8 = this.fSerializerFactory.makeSerializer(var4.getOutputStream(), new OutputFormat());
            } else {
               if (var4.getSystemId() == null) {
                  throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "StreamResultNotInitialized", (Object[])null));
               }

               String var9 = var4.getSystemId();
               OutputStream var10 = XMLEntityManager.createOutputStream(var9);
               var8 = this.fSerializerFactory.makeSerializer(var10, new OutputFormat());
            }

            SAXParser var19 = (SAXParser)this.fParser.get();
            if (!var6 && var19 != null) {
               var19.reset();
            } else {
               var19 = new SAXParser(var7);
               this.fParser = new SoftReference(var19);
            }

            var7.setDocumentHandler(this.fSchemaValidator);
            this.fSchemaValidator.setDocumentHandler(var19);
            var19.setContentHandler(var8.asContentHandler());
         } else {
            this.fSchemaValidator.setDocumentHandler((XMLDocumentHandler)null);
         }

         try {
            var7.parse(var5);
         } catch (XMLParseException var16) {
            throw Util.toSAXParseException(var16);
         } catch (XNIException var17) {
            throw Util.toSAXException(var17);
         } finally {
            this.fSchemaValidator.setDocumentHandler((XMLDocumentHandler)null);
         }

      }
   }

   private XMLParserConfiguration initialize() {
      XML11Configuration var1 = new XML11Configuration();
      var1.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/entity-resolver"));
      var1.setProperty("http://apache.org/xml/properties/internal/error-handler", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-handler"));
      XMLErrorReporter var2 = (XMLErrorReporter)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
      var1.setProperty("http://apache.org/xml/properties/internal/error-reporter", var2);
      if (var2.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null) {
         XMLMessageFormatter var3 = new XMLMessageFormatter();
         var2.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", var3);
         var2.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", var3);
      }

      var1.setProperty("http://apache.org/xml/properties/internal/symbol-table", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"));
      var1.setProperty("http://apache.org/xml/properties/internal/validation-manager", this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager"));
      var1.setProperty("http://apache.org/xml/properties/security-manager", this.fComponentManager.getProperty("http://apache.org/xml/properties/security-manager"));
      var1.setDocumentHandler(this.fSchemaValidator);
      var1.setDTDHandler((XMLDTDHandler)null);
      var1.setDTDContentModelHandler((XMLDTDContentModelHandler)null);
      this.fConfiguration = new SoftReference(var1);
      return var1;
   }
}

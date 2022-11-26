package weblogic.apache.xerces.dom;

import java.lang.ref.SoftReference;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;
import weblogic.apache.xerces.impl.RevalidationHandler;
import weblogic.apache.xerces.impl.dtd.XMLDTDLoader;
import weblogic.apache.xerces.parsers.DOMParserImpl;
import weblogic.apache.xerces.util.XMLChar;
import weblogic.apache.xml.serialize.DOMSerializerImpl;

public class CoreDOMImplementationImpl implements DOMImplementation, DOMImplementationLS {
   private static final int SIZE = 2;
   private SoftReference[] schemaValidators = new SoftReference[2];
   private SoftReference[] xml10DTDValidators = new SoftReference[2];
   private SoftReference[] xml11DTDValidators = new SoftReference[2];
   private int freeSchemaValidatorIndex = -1;
   private int freeXML10DTDValidatorIndex = -1;
   private int freeXML11DTDValidatorIndex = -1;
   private int schemaValidatorsCurrentSize = 2;
   private int xml10DTDValidatorsCurrentSize = 2;
   private int xml11DTDValidatorsCurrentSize = 2;
   private SoftReference[] xml10DTDLoaders = new SoftReference[2];
   private SoftReference[] xml11DTDLoaders = new SoftReference[2];
   private int freeXML10DTDLoaderIndex = -1;
   private int freeXML11DTDLoaderIndex = -1;
   private int xml10DTDLoaderCurrentSize = 2;
   private int xml11DTDLoaderCurrentSize = 2;
   private int docAndDoctypeCounter = 0;
   static final CoreDOMImplementationImpl singleton = new CoreDOMImplementationImpl();

   public static DOMImplementation getDOMImplementation() {
      return singleton;
   }

   public boolean hasFeature(String var1, String var2) {
      boolean var3 = var2 == null || var2.length() == 0;
      if (!var1.equalsIgnoreCase("+XPath") || !var3 && !var2.equals("3.0")) {
         if (var1.startsWith("+")) {
            var1 = var1.substring(1);
         }

         return var1.equalsIgnoreCase("Core") && (var3 || var2.equals("1.0") || var2.equals("2.0") || var2.equals("3.0")) || var1.equalsIgnoreCase("XML") && (var3 || var2.equals("1.0") || var2.equals("2.0") || var2.equals("3.0")) || var1.equalsIgnoreCase("XMLVersion") && (var3 || var2.equals("1.0") || var2.equals("1.1")) || var1.equalsIgnoreCase("LS") && (var3 || var2.equals("3.0")) || var1.equalsIgnoreCase("ElementTraversal") && (var3 || var2.equals("1.0"));
      } else {
         try {
            Class var4 = ObjectFactory.findProviderClass("weblogic.apache.xpath.domapi.XPathEvaluatorImpl", ObjectFactory.findClassLoader(), true);
            Class[] var5 = var4.getInterfaces();

            for(int var6 = 0; var6 < var5.length; ++var6) {
               if (var5[var6].getName().equals("org.w3c.dom.xpath.XPathEvaluator")) {
                  return true;
               }
            }

            return true;
         } catch (Exception var7) {
            return false;
         }
      }
   }

   public DocumentType createDocumentType(String var1, String var2, String var3) {
      this.checkQName(var1);
      return new DocumentTypeImpl((CoreDocumentImpl)null, var1, var2, var3);
   }

   final void checkQName(String var1) {
      int var2 = var1.indexOf(58);
      int var3 = var1.lastIndexOf(58);
      int var4 = var1.length();
      if (var2 != 0 && var2 != var4 - 1 && var3 == var2) {
         int var8 = 0;
         int var6;
         String var7;
         String var9;
         if (var2 > 0) {
            if (!XMLChar.isNCNameStart(var1.charAt(var8))) {
               var9 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", (Object[])null);
               throw new DOMException((short)5, var9);
            }

            for(var6 = 1; var6 < var2; ++var6) {
               if (!XMLChar.isNCName(var1.charAt(var6))) {
                  var7 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", (Object[])null);
                  throw new DOMException((short)5, var7);
               }
            }

            var8 = var2 + 1;
         }

         if (!XMLChar.isNCNameStart(var1.charAt(var8))) {
            var9 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", (Object[])null);
            throw new DOMException((short)5, var9);
         } else {
            for(var6 = var8 + 1; var6 < var4; ++var6) {
               if (!XMLChar.isNCName(var1.charAt(var6))) {
                  var7 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", (Object[])null);
                  throw new DOMException((short)5, var7);
               }
            }

         }
      } else {
         String var5 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", (Object[])null);
         throw new DOMException((short)14, var5);
      }
   }

   public Document createDocument(String var1, String var2, DocumentType var3) throws DOMException {
      if (var3 != null && var3.getOwnerDocument() != null) {
         String var6 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "WRONG_DOCUMENT_ERR", (Object[])null);
         throw new DOMException((short)4, var6);
      } else {
         CoreDocumentImpl var4 = this.createDocument(var3);
         if (var2 != null || var1 != null) {
            Element var5 = var4.createElementNS(var1, var2);
            var4.appendChild(var5);
         }

         return var4;
      }
   }

   protected CoreDocumentImpl createDocument(DocumentType var1) {
      return new CoreDocumentImpl(var1);
   }

   public Object getFeature(String var1, String var2) {
      if (singleton.hasFeature(var1, var2)) {
         if (!var1.equalsIgnoreCase("+XPath")) {
            return singleton;
         }

         try {
            Class var3 = ObjectFactory.findProviderClass("weblogic.apache.xpath.domapi.XPathEvaluatorImpl", ObjectFactory.findClassLoader(), true);
            Class[] var4 = var3.getInterfaces();

            for(int var5 = 0; var5 < var4.length; ++var5) {
               if (var4[var5].getName().equals("org.w3c.dom.xpath.XPathEvaluator")) {
                  return var3.newInstance();
               }
            }
         } catch (Exception var6) {
            return null;
         }
      }

      return null;
   }

   public LSParser createLSParser(short var1, String var2) throws DOMException {
      if (var1 == 1 && (var2 == null || "http://www.w3.org/2001/XMLSchema".equals(var2) || "http://www.w3.org/TR/REC-xml".equals(var2))) {
         return var2 != null && var2.equals("http://www.w3.org/TR/REC-xml") ? new DOMParserImpl("weblogic.apache.xerces.parsers.XML11DTDConfiguration", var2) : new DOMParserImpl("weblogic.apache.xerces.parsers.XIncludeAwareParserConfiguration", var2);
      } else {
         String var3 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NOT_SUPPORTED_ERR", (Object[])null);
         throw new DOMException((short)9, var3);
      }
   }

   public LSSerializer createLSSerializer() {
      try {
         Class var1 = ObjectFactory.findProviderClass("weblogic.apache.xml.serializer.dom3.LSSerializerImpl", ObjectFactory.findClassLoader(), true);
         return (LSSerializer)var1.newInstance();
      } catch (Exception var2) {
         return new DOMSerializerImpl();
      }
   }

   public LSInput createLSInput() {
      return new DOMInputImpl();
   }

   synchronized RevalidationHandler getValidator(String var1, String var2) {
      SoftReference var3;
      RevalidationHandlerHolder var4;
      RevalidationHandler var5;
      if (var1 == "http://www.w3.org/2001/XMLSchema") {
         while(this.freeSchemaValidatorIndex >= 0) {
            var3 = this.schemaValidators[this.freeSchemaValidatorIndex];
            var4 = (RevalidationHandlerHolder)var3.get();
            if (var4 != null && var4.handler != null) {
               var5 = var4.handler;
               var4.handler = null;
               --this.freeSchemaValidatorIndex;
               return var5;
            }

            this.schemaValidators[this.freeSchemaValidatorIndex--] = null;
         }

         return (RevalidationHandler)ObjectFactory.newInstance("weblogic.apache.xerces.impl.xs.XMLSchemaValidator", ObjectFactory.findClassLoader(), true);
      } else if (var1 == "http://www.w3.org/TR/REC-xml") {
         if ("1.1".equals(var2)) {
            while(this.freeXML11DTDValidatorIndex >= 0) {
               var3 = this.xml11DTDValidators[this.freeXML11DTDValidatorIndex];
               var4 = (RevalidationHandlerHolder)var3.get();
               if (var4 != null && var4.handler != null) {
                  var5 = var4.handler;
                  var4.handler = null;
                  --this.freeXML11DTDValidatorIndex;
                  return var5;
               }

               this.xml11DTDValidators[this.freeXML11DTDValidatorIndex--] = null;
            }

            return (RevalidationHandler)ObjectFactory.newInstance("weblogic.apache.xerces.impl.dtd.XML11DTDValidator", ObjectFactory.findClassLoader(), true);
         } else {
            while(this.freeXML10DTDValidatorIndex >= 0) {
               var3 = this.xml10DTDValidators[this.freeXML10DTDValidatorIndex];
               var4 = (RevalidationHandlerHolder)var3.get();
               if (var4 != null && var4.handler != null) {
                  var5 = var4.handler;
                  var4.handler = null;
                  --this.freeXML10DTDValidatorIndex;
                  return var5;
               }

               this.xml10DTDValidators[this.freeXML10DTDValidatorIndex--] = null;
            }

            return (RevalidationHandler)ObjectFactory.newInstance("weblogic.apache.xerces.impl.dtd.XMLDTDValidator", ObjectFactory.findClassLoader(), true);
         }
      } else {
         return null;
      }
   }

   synchronized void releaseValidator(String var1, String var2, RevalidationHandler var3) {
      SoftReference[] var4;
      RevalidationHandlerHolder var5;
      SoftReference var6;
      if (var1 == "http://www.w3.org/2001/XMLSchema") {
         ++this.freeSchemaValidatorIndex;
         if (this.schemaValidators.length == this.freeSchemaValidatorIndex) {
            this.schemaValidatorsCurrentSize += 2;
            var4 = new SoftReference[this.schemaValidatorsCurrentSize];
            System.arraycopy(this.schemaValidators, 0, var4, 0, this.schemaValidators.length);
            this.schemaValidators = var4;
         }

         var6 = this.schemaValidators[this.freeSchemaValidatorIndex];
         if (var6 != null) {
            var5 = (RevalidationHandlerHolder)var6.get();
            if (var5 != null) {
               var5.handler = var3;
               return;
            }
         }

         this.schemaValidators[this.freeSchemaValidatorIndex] = new SoftReference(new RevalidationHandlerHolder(var3));
      } else if (var1 == "http://www.w3.org/TR/REC-xml") {
         if ("1.1".equals(var2)) {
            ++this.freeXML11DTDValidatorIndex;
            if (this.xml11DTDValidators.length == this.freeXML11DTDValidatorIndex) {
               this.xml11DTDValidatorsCurrentSize += 2;
               var4 = new SoftReference[this.xml11DTDValidatorsCurrentSize];
               System.arraycopy(this.xml11DTDValidators, 0, var4, 0, this.xml11DTDValidators.length);
               this.xml11DTDValidators = var4;
            }

            var6 = this.xml11DTDValidators[this.freeXML11DTDValidatorIndex];
            if (var6 != null) {
               var5 = (RevalidationHandlerHolder)var6.get();
               if (var5 != null) {
                  var5.handler = var3;
                  return;
               }
            }

            this.xml11DTDValidators[this.freeXML11DTDValidatorIndex] = new SoftReference(new RevalidationHandlerHolder(var3));
         } else {
            ++this.freeXML10DTDValidatorIndex;
            if (this.xml10DTDValidators.length == this.freeXML10DTDValidatorIndex) {
               this.xml10DTDValidatorsCurrentSize += 2;
               var4 = new SoftReference[this.xml10DTDValidatorsCurrentSize];
               System.arraycopy(this.xml10DTDValidators, 0, var4, 0, this.xml10DTDValidators.length);
               this.xml10DTDValidators = var4;
            }

            var6 = this.xml10DTDValidators[this.freeXML10DTDValidatorIndex];
            if (var6 != null) {
               var5 = (RevalidationHandlerHolder)var6.get();
               if (var5 != null) {
                  var5.handler = var3;
                  return;
               }
            }

            this.xml10DTDValidators[this.freeXML10DTDValidatorIndex] = new SoftReference(new RevalidationHandlerHolder(var3));
         }
      }

   }

   final synchronized XMLDTDLoader getDTDLoader(String var1) {
      SoftReference var2;
      XMLDTDLoaderHolder var3;
      XMLDTDLoader var4;
      if ("1.1".equals(var1)) {
         while(this.freeXML11DTDLoaderIndex >= 0) {
            var2 = this.xml11DTDLoaders[this.freeXML11DTDLoaderIndex];
            var3 = (XMLDTDLoaderHolder)var2.get();
            if (var3 != null && var3.loader != null) {
               var4 = var3.loader;
               var3.loader = null;
               --this.freeXML11DTDLoaderIndex;
               return var4;
            }

            this.xml11DTDLoaders[this.freeXML11DTDLoaderIndex--] = null;
         }

         return (XMLDTDLoader)ObjectFactory.newInstance("weblogic.apache.xerces.impl.dtd.XML11DTDProcessor", ObjectFactory.findClassLoader(), true);
      } else {
         while(this.freeXML10DTDLoaderIndex >= 0) {
            var2 = this.xml10DTDLoaders[this.freeXML10DTDLoaderIndex];
            var3 = (XMLDTDLoaderHolder)var2.get();
            if (var3 != null && var3.loader != null) {
               var4 = var3.loader;
               var3.loader = null;
               --this.freeXML10DTDLoaderIndex;
               return var4;
            }

            this.xml10DTDLoaders[this.freeXML10DTDLoaderIndex--] = null;
         }

         return new XMLDTDLoader();
      }
   }

   final synchronized void releaseDTDLoader(String var1, XMLDTDLoader var2) {
      SoftReference[] var3;
      XMLDTDLoaderHolder var4;
      SoftReference var5;
      if ("1.1".equals(var1)) {
         ++this.freeXML11DTDLoaderIndex;
         if (this.xml11DTDLoaders.length == this.freeXML11DTDLoaderIndex) {
            this.xml11DTDLoaderCurrentSize += 2;
            var3 = new SoftReference[this.xml11DTDLoaderCurrentSize];
            System.arraycopy(this.xml11DTDLoaders, 0, var3, 0, this.xml11DTDLoaders.length);
            this.xml11DTDLoaders = var3;
         }

         var5 = this.xml11DTDLoaders[this.freeXML11DTDLoaderIndex];
         if (var5 != null) {
            var4 = (XMLDTDLoaderHolder)var5.get();
            if (var4 != null) {
               var4.loader = var2;
               return;
            }
         }

         this.xml11DTDLoaders[this.freeXML11DTDLoaderIndex] = new SoftReference(new XMLDTDLoaderHolder(var2));
      } else {
         ++this.freeXML10DTDLoaderIndex;
         if (this.xml10DTDLoaders.length == this.freeXML10DTDLoaderIndex) {
            this.xml10DTDLoaderCurrentSize += 2;
            var3 = new SoftReference[this.xml10DTDLoaderCurrentSize];
            System.arraycopy(this.xml10DTDLoaders, 0, var3, 0, this.xml10DTDLoaders.length);
            this.xml10DTDLoaders = var3;
         }

         var5 = this.xml10DTDLoaders[this.freeXML10DTDLoaderIndex];
         if (var5 != null) {
            var4 = (XMLDTDLoaderHolder)var5.get();
            if (var4 != null) {
               var4.loader = var2;
               return;
            }
         }

         this.xml10DTDLoaders[this.freeXML10DTDLoaderIndex] = new SoftReference(new XMLDTDLoaderHolder(var2));
      }

   }

   protected synchronized int assignDocumentNumber() {
      return ++this.docAndDoctypeCounter;
   }

   protected synchronized int assignDocTypeNumber() {
      return ++this.docAndDoctypeCounter;
   }

   public LSOutput createLSOutput() {
      return new DOMOutputImpl();
   }

   static final class XMLDTDLoaderHolder {
      XMLDTDLoader loader;

      XMLDTDLoaderHolder(XMLDTDLoader var1) {
         this.loader = var1;
      }
   }

   static final class RevalidationHandlerHolder {
      RevalidationHandler handler;

      RevalidationHandlerHolder(RevalidationHandler var1) {
         this.handler = var1;
      }
   }
}

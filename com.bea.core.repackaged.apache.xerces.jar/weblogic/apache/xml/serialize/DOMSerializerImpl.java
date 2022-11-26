package weblogic.apache.xml.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSSerializerFilter;
import weblogic.apache.xerces.dom.CoreDocumentImpl;
import weblogic.apache.xerces.dom.DOMErrorImpl;
import weblogic.apache.xerces.dom.DOMLocatorImpl;
import weblogic.apache.xerces.dom.DOMMessageFormatter;
import weblogic.apache.xerces.dom.DOMNormalizer;
import weblogic.apache.xerces.dom.DOMStringListImpl;
import weblogic.apache.xerces.impl.XMLEntityManager;
import weblogic.apache.xerces.util.DOMUtil;
import weblogic.apache.xerces.util.NamespaceSupport;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.util.XML11Char;
import weblogic.apache.xerces.util.XMLChar;

/** @deprecated */
public class DOMSerializerImpl implements LSSerializer, DOMConfiguration {
   private XMLSerializer serializer;
   private XML11Serializer xml11Serializer;
   private DOMStringList fRecognizedParameters;
   protected short features = 0;
   protected static final short NAMESPACES = 1;
   protected static final short WELLFORMED = 2;
   protected static final short ENTITIES = 4;
   protected static final short CDATA = 8;
   protected static final short SPLITCDATA = 16;
   protected static final short COMMENTS = 32;
   protected static final short DISCARDDEFAULT = 64;
   protected static final short INFOSET = 128;
   protected static final short XMLDECL = 256;
   protected static final short NSDECL = 512;
   protected static final short DOM_ELEMENT_CONTENT_WHITESPACE = 1024;
   protected static final short PRETTY_PRINT = 2048;
   private DOMErrorHandler fErrorHandler = null;
   private final DOMErrorImpl fError = new DOMErrorImpl();
   private final DOMLocatorImpl fLocator = new DOMLocatorImpl();

   public DOMSerializerImpl() {
      this.features = (short)(this.features | 1);
      this.features = (short)(this.features | 4);
      this.features = (short)(this.features | 32);
      this.features = (short)(this.features | 8);
      this.features = (short)(this.features | 16);
      this.features = (short)(this.features | 2);
      this.features = (short)(this.features | 512);
      this.features = (short)(this.features | 1024);
      this.features = (short)(this.features | 64);
      this.features = (short)(this.features | 256);
      this.serializer = new XMLSerializer();
      this.initSerializer(this.serializer);
   }

   public DOMConfiguration getDomConfig() {
      return this;
   }

   public void setParameter(String var1, Object var2) throws DOMException {
      if (var2 instanceof Boolean) {
         boolean var3 = (Boolean)var2;
         if (var1.equalsIgnoreCase("infoset")) {
            if (var3) {
               this.features = (short)(this.features & -5);
               this.features = (short)(this.features & -9);
               this.features = (short)(this.features | 1);
               this.features = (short)(this.features | 512);
               this.features = (short)(this.features | 2);
               this.features = (short)(this.features | 32);
            }
         } else if (var1.equalsIgnoreCase("xml-declaration")) {
            this.features = var3 ? (short)(this.features | 256) : (short)(this.features & -257);
         } else if (var1.equalsIgnoreCase("namespaces")) {
            this.features = var3 ? (short)(this.features | 1) : (short)(this.features & -2);
            this.serializer.fNamespaces = var3;
         } else if (var1.equalsIgnoreCase("split-cdata-sections")) {
            this.features = var3 ? (short)(this.features | 16) : (short)(this.features & -17);
         } else if (var1.equalsIgnoreCase("discard-default-content")) {
            this.features = var3 ? (short)(this.features | 64) : (short)(this.features & -65);
         } else if (var1.equalsIgnoreCase("well-formed")) {
            this.features = var3 ? (short)(this.features | 2) : (short)(this.features & -3);
         } else if (var1.equalsIgnoreCase("entities")) {
            this.features = var3 ? (short)(this.features | 4) : (short)(this.features & -5);
         } else if (var1.equalsIgnoreCase("cdata-sections")) {
            this.features = var3 ? (short)(this.features | 8) : (short)(this.features & -9);
         } else if (var1.equalsIgnoreCase("comments")) {
            this.features = var3 ? (short)(this.features | 32) : (short)(this.features & -33);
         } else if (var1.equalsIgnoreCase("format-pretty-print")) {
            this.features = var3 ? (short)(this.features | 2048) : (short)(this.features & -2049);
         } else {
            String var4;
            if (!var1.equalsIgnoreCase("canonical-form") && !var1.equalsIgnoreCase("validate-if-schema") && !var1.equalsIgnoreCase("validate") && !var1.equalsIgnoreCase("check-character-normalization") && !var1.equalsIgnoreCase("datatype-normalization") && !var1.equalsIgnoreCase("normalize-characters")) {
               if (var1.equalsIgnoreCase("namespace-declarations")) {
                  this.features = var3 ? (short)(this.features | 512) : (short)(this.features & -513);
                  this.serializer.fNamespacePrefixes = var3;
               } else {
                  if (!var1.equalsIgnoreCase("element-content-whitespace") && !var1.equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
                     var4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[]{var1});
                     throw new DOMException((short)9, var4);
                  }

                  if (!var3) {
                     var4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[]{var1});
                     throw new DOMException((short)9, var4);
                  }
               }
            } else if (var3) {
               var4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[]{var1});
               throw new DOMException((short)9, var4);
            }
         }
      } else {
         String var5;
         if (!var1.equalsIgnoreCase("error-handler")) {
            if (!var1.equalsIgnoreCase("resource-resolver") && !var1.equalsIgnoreCase("schema-location") && (!var1.equalsIgnoreCase("schema-type") || var2 == null)) {
               var5 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[]{var1});
               throw new DOMException((short)8, var5);
            }

            var5 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[]{var1});
            throw new DOMException((short)9, var5);
         }

         if (var2 != null && !(var2 instanceof DOMErrorHandler)) {
            var5 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[]{var1});
            throw new DOMException((short)17, var5);
         }

         this.fErrorHandler = (DOMErrorHandler)var2;
      }

   }

   public boolean canSetParameter(String var1, Object var2) {
      if (var2 == null) {
         return true;
      } else if (var2 instanceof Boolean) {
         boolean var3 = (Boolean)var2;
         if (!var1.equalsIgnoreCase("namespaces") && !var1.equalsIgnoreCase("split-cdata-sections") && !var1.equalsIgnoreCase("discard-default-content") && !var1.equalsIgnoreCase("xml-declaration") && !var1.equalsIgnoreCase("well-formed") && !var1.equalsIgnoreCase("infoset") && !var1.equalsIgnoreCase("entities") && !var1.equalsIgnoreCase("cdata-sections") && !var1.equalsIgnoreCase("comments") && !var1.equalsIgnoreCase("format-pretty-print") && !var1.equalsIgnoreCase("namespace-declarations")) {
            if (!var1.equalsIgnoreCase("canonical-form") && !var1.equalsIgnoreCase("validate-if-schema") && !var1.equalsIgnoreCase("validate") && !var1.equalsIgnoreCase("check-character-normalization") && !var1.equalsIgnoreCase("datatype-normalization") && !var1.equalsIgnoreCase("normalize-characters")) {
               if (!var1.equalsIgnoreCase("element-content-whitespace") && !var1.equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
                  return false;
               } else {
                  return var3;
               }
            } else {
               return !var3;
            }
         } else {
            return true;
         }
      } else if (var1.equalsIgnoreCase("error-handler") && var2 == null || var2 instanceof DOMErrorHandler) {
         return true;
      } else {
         return false;
      }
   }

   public DOMStringList getParameterNames() {
      if (this.fRecognizedParameters == null) {
         ArrayList var1 = new ArrayList();
         var1.add("namespaces");
         var1.add("split-cdata-sections");
         var1.add("discard-default-content");
         var1.add("xml-declaration");
         var1.add("canonical-form");
         var1.add("validate-if-schema");
         var1.add("validate");
         var1.add("check-character-normalization");
         var1.add("datatype-normalization");
         var1.add("format-pretty-print");
         var1.add("normalize-characters");
         var1.add("well-formed");
         var1.add("infoset");
         var1.add("namespace-declarations");
         var1.add("element-content-whitespace");
         var1.add("entities");
         var1.add("cdata-sections");
         var1.add("comments");
         var1.add("ignore-unknown-character-denormalizations");
         var1.add("error-handler");
         this.fRecognizedParameters = new DOMStringListImpl(var1);
      }

      return this.fRecognizedParameters;
   }

   public Object getParameter(String var1) throws DOMException {
      if (var1.equalsIgnoreCase("comments")) {
         return (this.features & 32) != 0 ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("namespaces")) {
         return (this.features & 1) != 0 ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("xml-declaration")) {
         return (this.features & 256) != 0 ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("cdata-sections")) {
         return (this.features & 8) != 0 ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("entities")) {
         return (this.features & 4) != 0 ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("split-cdata-sections")) {
         return (this.features & 16) != 0 ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("well-formed")) {
         return (this.features & 2) != 0 ? Boolean.TRUE : Boolean.FALSE;
      } else if (var1.equalsIgnoreCase("namespace-declarations")) {
         return (this.features & 512) != 0 ? Boolean.TRUE : Boolean.FALSE;
      } else if (!var1.equalsIgnoreCase("element-content-whitespace") && !var1.equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
         if (var1.equalsIgnoreCase("discard-default-content")) {
            return (this.features & 64) != 0 ? Boolean.TRUE : Boolean.FALSE;
         } else if (var1.equalsIgnoreCase("format-pretty-print")) {
            return (this.features & 2048) != 0 ? Boolean.TRUE : Boolean.FALSE;
         } else if (var1.equalsIgnoreCase("infoset")) {
            return (this.features & 4) == 0 && (this.features & 8) == 0 && (this.features & 1) != 0 && (this.features & 512) != 0 && (this.features & 2) != 0 && (this.features & 32) != 0 ? Boolean.TRUE : Boolean.FALSE;
         } else if (!var1.equalsIgnoreCase("normalize-characters") && !var1.equalsIgnoreCase("canonical-form") && !var1.equalsIgnoreCase("validate-if-schema") && !var1.equalsIgnoreCase("check-character-normalization") && !var1.equalsIgnoreCase("validate") && !var1.equalsIgnoreCase("validate-if-schema") && !var1.equalsIgnoreCase("datatype-normalization")) {
            if (var1.equalsIgnoreCase("error-handler")) {
               return this.fErrorHandler;
            } else {
               String var2;
               if (!var1.equalsIgnoreCase("resource-resolver") && !var1.equalsIgnoreCase("schema-location") && !var1.equalsIgnoreCase("schema-type")) {
                  var2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[]{var1});
                  throw new DOMException((short)8, var2);
               } else {
                  var2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[]{var1});
                  throw new DOMException((short)9, var2);
               }
            }
         } else {
            return Boolean.FALSE;
         }
      } else {
         return Boolean.TRUE;
      }
   }

   public String writeToString(Node var1) throws DOMException, LSException {
      Object var2 = null;
      String var3 = this._getXmlVersion(var1);
      if (var3 != null && var3.equals("1.1")) {
         if (this.xml11Serializer == null) {
            this.xml11Serializer = new XML11Serializer();
            this.initSerializer(this.xml11Serializer);
         }

         this.copySettings(this.serializer, this.xml11Serializer);
         var2 = this.xml11Serializer;
      } else {
         var2 = this.serializer;
      }

      StringWriter var4 = new StringWriter();

      Object var7;
      try {
         try {
            this.prepareForSerialization((XMLSerializer)var2, var1);
            ((XMLSerializer)var2)._format.setEncoding("UTF-16");
            ((XMLSerializer)var2).setOutputCharStream(var4);
            if (var1.getNodeType() == 9) {
               ((XMLSerializer)var2).serialize((Document)var1);
               return var4.toString();
            } else {
               if (var1.getNodeType() == 11) {
                  ((XMLSerializer)var2).serialize((DocumentFragment)var1);
               } else {
                  if (var1.getNodeType() != 1) {
                     String var5 = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "unable-to-serialize-node", (Object[])null);
                     if (((XMLSerializer)var2).fDOMErrorHandler != null) {
                        DOMErrorImpl var6 = new DOMErrorImpl();
                        var6.fType = "unable-to-serialize-node";
                        var6.fMessage = var5;
                        var6.fSeverity = 3;
                        ((XMLSerializer)var2).fDOMErrorHandler.handleError(var6);
                     }

                     throw new LSException((short)82, var5);
                  }

                  ((XMLSerializer)var2).serialize((Element)var1);
               }

               return var4.toString();
            }
         } catch (LSException var15) {
            throw var15;
         } catch (RuntimeException var16) {
            if (var16 != DOMNormalizer.abort) {
               throw (LSException)DOMUtil.createLSException((short)82, var16).fillInStackTrace();
            }
         } catch (IOException var17) {
            String var8 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "STRING_TOO_LONG", new Object[]{var17.getMessage()});
            throw new DOMException((short)2, var8);
         }

         var7 = null;
      } finally {
         ((XMLSerializer)var2).clearDocumentState();
      }

      return (String)var7;
   }

   public void setNewLine(String var1) {
      this.serializer._format.setLineSeparator(var1);
   }

   public String getNewLine() {
      return this.serializer._format.getLineSeparator();
   }

   public LSSerializerFilter getFilter() {
      return this.serializer.fDOMFilter;
   }

   public void setFilter(LSSerializerFilter var1) {
      this.serializer.fDOMFilter = var1;
   }

   private void initSerializer(XMLSerializer var1) {
      var1.fNSBinder = new NamespaceSupport();
      var1.fLocalNSBinder = new NamespaceSupport();
      var1.fSymbolTable = new SymbolTable();
   }

   private void copySettings(XMLSerializer var1, XMLSerializer var2) {
      var2.fDOMErrorHandler = this.fErrorHandler;
      var2._format.setEncoding(var1._format.getEncoding());
      var2._format.setLineSeparator(var1._format.getLineSeparator());
      var2.fDOMFilter = var1.fDOMFilter;
   }

   public boolean write(Node var1, LSOutput var2) throws LSException {
      if (var1 == null) {
         return false;
      } else {
         Object var3 = null;
         String var4 = this._getXmlVersion(var1);
         if (var4 != null && var4.equals("1.1")) {
            if (this.xml11Serializer == null) {
               this.xml11Serializer = new XML11Serializer();
               this.initSerializer(this.xml11Serializer);
            }

            this.copySettings(this.serializer, this.xml11Serializer);
            var3 = this.xml11Serializer;
         } else {
            var3 = this.serializer;
         }

         String var5 = null;
         if ((var5 = var2.getEncoding()) == null) {
            var5 = this._getInputEncoding(var1);
            if (var5 == null) {
               var5 = this._getXmlEncoding(var1);
               if (var5 == null) {
                  var5 = "UTF-8";
               }
            }
         }

         boolean var9;
         try {
            DOMErrorImpl var10;
            try {
               this.prepareForSerialization((XMLSerializer)var3, var1);
               ((XMLSerializer)var3)._format.setEncoding(var5);
               OutputStream var6 = var2.getByteStream();
               Writer var23 = var2.getCharacterStream();
               String var8 = var2.getSystemId();
               if (var23 == null) {
                  if (var6 == null) {
                     if (var8 == null) {
                        String var24 = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "no-output-specified", (Object[])null);
                        if (((XMLSerializer)var3).fDOMErrorHandler != null) {
                           var10 = new DOMErrorImpl();
                           var10.fType = "no-output-specified";
                           var10.fMessage = var24;
                           var10.fSeverity = 3;
                           ((XMLSerializer)var3).fDOMErrorHandler.handleError(var10);
                        }

                        throw new LSException((short)82, var24);
                     }

                     ((XMLSerializer)var3).setOutputByteStream(XMLEntityManager.createOutputStream(var8));
                  } else {
                     ((XMLSerializer)var3).setOutputByteStream(var6);
                  }
               } else {
                  ((XMLSerializer)var3).setOutputCharStream(var23);
               }

               if (var1.getNodeType() == 9) {
                  ((XMLSerializer)var3).serialize((Document)var1);
                  return true;
               }

               if (var1.getNodeType() == 11) {
                  ((XMLSerializer)var3).serialize((DocumentFragment)var1);
                  return true;
               }

               if (var1.getNodeType() == 1) {
                  ((XMLSerializer)var3).serialize((Element)var1);
                  return true;
               }

               var9 = false;
               return var9;
            } catch (UnsupportedEncodingException var18) {
               if (((XMLSerializer)var3).fDOMErrorHandler != null) {
                  DOMErrorImpl var7 = new DOMErrorImpl();
                  var7.fException = var18;
                  var7.fType = "unsupported-encoding";
                  var7.fMessage = var18.getMessage();
                  var7.fSeverity = 3;
                  ((XMLSerializer)var3).fDOMErrorHandler.handleError(var7);
               }

               throw new LSException((short)82, DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "unsupported-encoding", (Object[])null));
            } catch (LSException var19) {
               throw var19;
            } catch (RuntimeException var20) {
               if (var20 != DOMNormalizer.abort) {
                  throw (LSException)DOMUtil.createLSException((short)82, var20).fillInStackTrace();
               }
            } catch (Exception var21) {
               if (((XMLSerializer)var3).fDOMErrorHandler != null) {
                  var10 = new DOMErrorImpl();
                  var10.fException = var21;
                  var10.fMessage = var21.getMessage();
                  var10.fSeverity = 2;
                  ((XMLSerializer)var3).fDOMErrorHandler.handleError(var10);
               }

               throw (LSException)DOMUtil.createLSException((short)82, var21).fillInStackTrace();
            }

            var9 = false;
         } finally {
            ((XMLSerializer)var3).clearDocumentState();
         }

         return var9;
      }
   }

   public boolean writeToURI(Node var1, String var2) throws LSException {
      if (var1 == null) {
         return false;
      } else {
         Object var3 = null;
         String var4 = this._getXmlVersion(var1);
         if (var4 != null && var4.equals("1.1")) {
            if (this.xml11Serializer == null) {
               this.xml11Serializer = new XML11Serializer();
               this.initSerializer(this.xml11Serializer);
            }

            this.copySettings(this.serializer, this.xml11Serializer);
            var3 = this.xml11Serializer;
         } else {
            var3 = this.serializer;
         }

         String var5 = this._getInputEncoding(var1);
         if (var5 == null) {
            var5 = this._getXmlEncoding(var1);
            if (var5 == null) {
               var5 = "UTF-8";
            }
         }

         boolean var8;
         try {
            try {
               this.prepareForSerialization((XMLSerializer)var3, var1);
               ((XMLSerializer)var3)._format.setEncoding(var5);
               ((XMLSerializer)var3).setOutputByteStream(XMLEntityManager.createOutputStream(var2));
               if (var1.getNodeType() == 9) {
                  ((XMLSerializer)var3).serialize((Document)var1);
                  return true;
               } else {
                  if (var1.getNodeType() == 11) {
                     ((XMLSerializer)var3).serialize((DocumentFragment)var1);
                  } else {
                     if (var1.getNodeType() != 1) {
                        boolean var6 = false;
                        return var6;
                     }

                     ((XMLSerializer)var3).serialize((Element)var1);
                  }

                  return true;
               }
            } catch (LSException var16) {
               throw var16;
            } catch (RuntimeException var17) {
               if (var17 != DOMNormalizer.abort) {
                  throw (LSException)DOMUtil.createLSException((short)82, var17).fillInStackTrace();
               }
            } catch (Exception var18) {
               if (((XMLSerializer)var3).fDOMErrorHandler != null) {
                  DOMErrorImpl var9 = new DOMErrorImpl();
                  var9.fException = var18;
                  var9.fMessage = var18.getMessage();
                  var9.fSeverity = 2;
                  ((XMLSerializer)var3).fDOMErrorHandler.handleError(var9);
               }

               throw (LSException)DOMUtil.createLSException((short)82, var18).fillInStackTrace();
            }

            var8 = false;
         } finally {
            ((XMLSerializer)var3).clearDocumentState();
         }

         return var8;
      }
   }

   private void prepareForSerialization(XMLSerializer var1, Node var2) {
      var1.reset();
      var1.features = this.features;
      var1.fDOMErrorHandler = this.fErrorHandler;
      var1.fNamespaces = (this.features & 1) != 0;
      var1.fNamespacePrefixes = (this.features & 512) != 0;
      var1._format.setIndenting((this.features & 2048) != 0);
      var1._format.setOmitComments((this.features & 32) == 0);
      var1._format.setOmitXMLDeclaration((this.features & 256) == 0);
      if ((this.features & 2) != 0) {
         Node var4 = var2;
         boolean var6 = true;
         Document var7 = var2.getNodeType() == 9 ? (Document)var2 : var2.getOwnerDocument();

         try {
            java.lang.reflect.Method var5 = var7.getClass().getMethod("isXMLVersionChanged()");
            if (var5 != null) {
               var6 = (Boolean)var5.invoke(var7, (Object[])null);
            }
         } catch (Exception var9) {
         }

         Node var3;
         if (var2.getFirstChild() == null) {
            this.verify(var2, var6, false);
         } else {
            for(; var2 != null; var2 = var3) {
               this.verify(var2, var6, false);
               var3 = var2.getFirstChild();

               while(var3 == null) {
                  var3 = var2.getNextSibling();
                  if (var3 == null) {
                     var2 = var2.getParentNode();
                     if (var4 == var2) {
                        var3 = null;
                        break;
                     }

                     var3 = var2.getNextSibling();
                  }
               }
            }
         }
      }

   }

   private void verify(Node var1, boolean var2, boolean var3) {
      short var4 = var1.getNodeType();
      this.fLocator.fRelatedNode = var1;
      boolean var5;
      switch (var4) {
         case 1:
            if (var2) {
               if ((this.features & 1) != 0) {
                  var5 = CoreDocumentImpl.isValidQName(var1.getPrefix(), var1.getLocalName(), var3);
               } else {
                  var5 = CoreDocumentImpl.isXMLName(var1.getNodeName(), var3);
               }

               if (!var5 && this.fErrorHandler != null) {
                  String var10 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[]{"Element", var1.getNodeName()});
                  DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var10, (short)3, "wf-invalid-character-in-node-name");
               }
            }

            NamedNodeMap var11 = var1.hasAttributes() ? var1.getAttributes() : null;
            if (var11 != null) {
               for(int var12 = 0; var12 < var11.getLength(); ++var12) {
                  Attr var13 = (Attr)var11.item(var12);
                  this.fLocator.fRelatedNode = var13;
                  DOMNormalizer.isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, var11, var13, var13.getValue(), var3);
                  if (var2) {
                     var5 = CoreDocumentImpl.isXMLName(var13.getNodeName(), var3);
                     if (!var5) {
                        String var9 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[]{"Attr", var1.getNodeName()});
                        DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var9, (short)3, "wf-invalid-character-in-node-name");
                     }
                  }
               }
            }
         case 2:
         case 6:
         case 9:
         case 10:
         default:
            break;
         case 3:
            DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, var1.getNodeValue(), var3);
            break;
         case 4:
            DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, var1.getNodeValue(), var3);
            break;
         case 5:
            if (var2 && (this.features & 4) != 0) {
               CoreDocumentImpl.isXMLName(var1.getNodeName(), var3);
            }
            break;
         case 7:
            ProcessingInstruction var6 = (ProcessingInstruction)var1;
            String var7 = var6.getTarget();
            if (var2) {
               if (var3) {
                  var5 = XML11Char.isXML11ValidName(var7);
               } else {
                  var5 = XMLChar.isValidName(var7);
               }

               if (!var5) {
                  String var8 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[]{"Element", var1.getNodeName()});
                  DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, var8, (short)3, "wf-invalid-character-in-node-name");
               }
            }

            DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, var6.getData(), var3);
            break;
         case 8:
            if ((this.features & 32) != 0) {
               DOMNormalizer.isCommentWF(this.fErrorHandler, this.fError, this.fLocator, ((Comment)var1).getData(), var3);
            }
      }

      this.fLocator.fRelatedNode = null;
   }

   private String _getXmlVersion(Node var1) {
      Document var2 = var1.getNodeType() == 9 ? (Document)var1 : var1.getOwnerDocument();
      if (var2 != null && DOMSerializerImpl.DocumentMethods.fgDocumentMethodsAvailable) {
         try {
            return (String)DOMSerializerImpl.DocumentMethods.fgDocumentGetXmlVersionMethod.invoke(var2, (Object[])null);
         } catch (VirtualMachineError var6) {
            throw var6;
         } catch (ThreadDeath var7) {
            throw var7;
         } catch (Throwable var8) {
         }
      }

      return null;
   }

   private String _getInputEncoding(Node var1) {
      Document var2 = var1.getNodeType() == 9 ? (Document)var1 : var1.getOwnerDocument();
      if (var2 != null && DOMSerializerImpl.DocumentMethods.fgDocumentMethodsAvailable) {
         try {
            return (String)DOMSerializerImpl.DocumentMethods.fgDocumentGetInputEncodingMethod.invoke(var2, (Object[])null);
         } catch (VirtualMachineError var6) {
            throw var6;
         } catch (ThreadDeath var7) {
            throw var7;
         } catch (Throwable var8) {
         }
      }

      return null;
   }

   private String _getXmlEncoding(Node var1) {
      Document var2 = var1.getNodeType() == 9 ? (Document)var1 : var1.getOwnerDocument();
      if (var2 != null && DOMSerializerImpl.DocumentMethods.fgDocumentMethodsAvailable) {
         try {
            return (String)DOMSerializerImpl.DocumentMethods.fgDocumentGetXmlEncodingMethod.invoke(var2, (Object[])null);
         } catch (VirtualMachineError var6) {
            throw var6;
         } catch (ThreadDeath var7) {
            throw var7;
         } catch (Throwable var8) {
         }
      }

      return null;
   }

   static class DocumentMethods {
      private static java.lang.reflect.Method fgDocumentGetXmlVersionMethod = null;
      private static java.lang.reflect.Method fgDocumentGetInputEncodingMethod = null;
      private static java.lang.reflect.Method fgDocumentGetXmlEncodingMethod = null;
      private static boolean fgDocumentMethodsAvailable = false;
      // $FF: synthetic field
      static Class class$org$w3c$dom$Document;

      private DocumentMethods() {
      }

      // $FF: synthetic method
      static Class class$(String var0) {
         try {
            return Class.forName(var0);
         } catch (ClassNotFoundException var2) {
            throw new NoClassDefFoundError(var2.getMessage());
         }
      }

      static {
         try {
            fgDocumentGetXmlVersionMethod = (class$org$w3c$dom$Document == null ? (class$org$w3c$dom$Document = class$("org.w3c.dom.Document")) : class$org$w3c$dom$Document).getMethod("getXmlVersion");
            fgDocumentGetInputEncodingMethod = (class$org$w3c$dom$Document == null ? (class$org$w3c$dom$Document = class$("org.w3c.dom.Document")) : class$org$w3c$dom$Document).getMethod("getInputEncoding");
            fgDocumentGetXmlEncodingMethod = (class$org$w3c$dom$Document == null ? (class$org$w3c$dom$Document = class$("org.w3c.dom.Document")) : class$org$w3c$dom$Document).getMethod("getXmlEncoding");
            fgDocumentMethodsAvailable = true;
         } catch (Exception var1) {
            fgDocumentGetXmlVersionMethod = null;
            fgDocumentGetInputEncodingMethod = null;
            fgDocumentGetXmlEncodingMethod = null;
            fgDocumentMethodsAvailable = false;
         }

      }
   }
}

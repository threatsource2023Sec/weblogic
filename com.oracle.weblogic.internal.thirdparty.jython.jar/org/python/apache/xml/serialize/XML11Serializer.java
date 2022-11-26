package org.python.apache.xml.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import org.python.apache.xerces.dom.DOMMessageFormatter;
import org.python.apache.xerces.util.NamespaceSupport;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.XML11Char;
import org.python.apache.xerces.util.XMLChar;
import org.xml.sax.SAXException;

/** @deprecated */
public class XML11Serializer extends XMLSerializer {
   protected static final boolean DEBUG = false;
   protected NamespaceSupport fNSBinder;
   protected NamespaceSupport fLocalNSBinder;
   protected SymbolTable fSymbolTable;
   protected boolean fDOML1 = false;
   protected int fNamespaceCounter = 1;
   protected static final String PREFIX = "NS";
   protected boolean fNamespaces = false;

   public XML11Serializer() {
      this._format.setVersion("1.1");
   }

   public XML11Serializer(OutputFormat var1) {
      super(var1);
      this._format.setVersion("1.1");
   }

   public XML11Serializer(Writer var1, OutputFormat var2) {
      super(var1, var2);
      this._format.setVersion("1.1");
   }

   public XML11Serializer(OutputStream var1, OutputFormat var2) {
      super(var1, var2 != null ? var2 : new OutputFormat("xml", (String)null, false));
      this._format.setVersion("1.1");
   }

   public void characters(char[] var1, int var2, int var3) throws SAXException {
      try {
         ElementState var4 = this.content();
         int var5;
         if (!var4.inCData && !var4.doCData) {
            if (var4.preserveSpace) {
               var5 = this._printer.getNextIndent();
               this._printer.setNextIndent(0);
               this.printText(var1, var2, var3, true, var4.unescaped);
               this._printer.setNextIndent(var5);
            } else {
               this.printText(var1, var2, var3, false, var4.unescaped);
            }
         } else {
            if (!var4.inCData) {
               this._printer.printText("<![CDATA[");
               var4.inCData = true;
            }

            var5 = this._printer.getNextIndent();
            this._printer.setNextIndent(0);
            int var6 = var2 + var3;

            for(int var7 = var2; var7 < var6; ++var7) {
               char var8 = var1[var7];
               if (var8 == ']' && var7 + 2 < var6 && var1[var7 + 1] == ']' && var1[var7 + 2] == '>') {
                  this._printer.printText("]]]]><![CDATA[>");
                  var7 += 2;
               } else if (!XML11Char.isXML11Valid(var8)) {
                  ++var7;
                  if (var7 < var6) {
                     this.surrogates(var8, var1[var7], true);
                  } else {
                     this.fatalError("The character '" + var8 + "' is an invalid XML character");
                  }
               } else if (this._encodingInfo.isPrintable(var8) && XML11Char.isXML11ValidLiteral(var8)) {
                  this._printer.printText(var8);
               } else {
                  this._printer.printText("]]>&#x");
                  this._printer.printText(Integer.toHexString(var8));
                  this._printer.printText(";<![CDATA[");
               }
            }

            this._printer.setNextIndent(var5);
         }

      } catch (IOException var9) {
         throw new SAXException(var9);
      }
   }

   protected void printEscaped(String var1) throws IOException {
      int var2 = var1.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         char var4 = var1.charAt(var3);
         if (!XML11Char.isXML11Valid(var4)) {
            ++var3;
            if (var3 < var2) {
               this.surrogates(var4, var1.charAt(var3), false);
            } else {
               this.fatalError("The character '" + (char)var4 + "' is an invalid XML character");
            }
         } else if (var4 != '\n' && var4 != '\r' && var4 != '\t' && var4 != 133 && var4 != 8232) {
            if (var4 == '<') {
               this._printer.printText("&lt;");
            } else if (var4 == '&') {
               this._printer.printText("&amp;");
            } else if (var4 == '"') {
               this._printer.printText("&quot;");
            } else if (var4 >= ' ' && this._encodingInfo.isPrintable((char)var4)) {
               this._printer.printText((char)var4);
            } else {
               this.printHex(var4);
            }
         } else {
            this.printHex(var4);
         }
      }

   }

   protected final void printCDATAText(String var1) throws IOException {
      int var2 = var1.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         char var4 = var1.charAt(var3);
         if (var4 == ']' && var3 + 2 < var2 && var1.charAt(var3 + 1) == ']' && var1.charAt(var3 + 2) == '>') {
            if (this.fDOMErrorHandler != null) {
               String var5;
               if ((this.features & 16) == 0 && (this.features & 2) == 0) {
                  var5 = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "EndingCDATA", (Object[])null);
                  this.modifyDOMError(var5, (short)3, (String)null, this.fCurrentNode);
                  boolean var6 = this.fDOMErrorHandler.handleError(this.fDOMError);
                  if (!var6) {
                     throw new IOException();
                  }
               } else {
                  var5 = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "SplittingCDATA", (Object[])null);
                  this.modifyDOMError(var5, (short)1, (String)null, this.fCurrentNode);
                  this.fDOMErrorHandler.handleError(this.fDOMError);
               }
            }

            this._printer.printText("]]]]><![CDATA[>");
            var3 += 2;
         } else if (!XML11Char.isXML11Valid(var4)) {
            ++var3;
            if (var3 < var2) {
               this.surrogates(var4, var1.charAt(var3), true);
            } else {
               this.fatalError("The character '" + var4 + "' is an invalid XML character");
            }
         } else if (this._encodingInfo.isPrintable(var4) && XML11Char.isXML11ValidLiteral(var4)) {
            this._printer.printText(var4);
         } else {
            this._printer.printText("]]>&#x");
            this._printer.printText(Integer.toHexString(var4));
            this._printer.printText(";<![CDATA[");
         }
      }

   }

   protected final void printXMLChar(int var1) throws IOException {
      if (var1 != 13 && var1 != 133 && var1 != 8232) {
         if (var1 == 60) {
            this._printer.printText("&lt;");
         } else if (var1 == 38) {
            this._printer.printText("&amp;");
         } else if (var1 == 62) {
            this._printer.printText("&gt;");
         } else if (this._encodingInfo.isPrintable((char)var1) && XML11Char.isXML11ValidLiteral(var1)) {
            this._printer.printText((char)var1);
         } else {
            this.printHex(var1);
         }
      } else {
         this.printHex(var1);
      }

   }

   protected final void surrogates(int var1, int var2, boolean var3) throws IOException {
      if (XMLChar.isHighSurrogate(var1)) {
         if (!XMLChar.isLowSurrogate(var2)) {
            this.fatalError("The character '" + (char)var2 + "' is an invalid XML character");
         } else {
            int var4 = XMLChar.supplemental((char)var1, (char)var2);
            if (!XML11Char.isXML11Valid(var4)) {
               this.fatalError("The character '" + (char)var4 + "' is an invalid XML character");
            } else if (var3 && this.content().inCData) {
               this._printer.printText("]]>&#x");
               this._printer.printText(Integer.toHexString(var4));
               this._printer.printText(";<![CDATA[");
            } else {
               this.printHex(var4);
            }
         }
      } else {
         this.fatalError("The character '" + (char)var1 + "' is an invalid XML character");
      }

   }

   protected void printText(String var1, boolean var2, boolean var3) throws IOException {
      int var4 = var1.length();
      int var5;
      char var6;
      if (var2) {
         for(var5 = 0; var5 < var4; ++var5) {
            var6 = var1.charAt(var5);
            if (!XML11Char.isXML11Valid(var6)) {
               ++var5;
               if (var5 < var4) {
                  this.surrogates(var6, var1.charAt(var5), true);
               } else {
                  this.fatalError("The character '" + var6 + "' is an invalid XML character");
               }
            } else if (var3 && XML11Char.isXML11ValidLiteral(var6)) {
               this._printer.printText(var6);
            } else {
               this.printXMLChar(var6);
            }
         }
      } else {
         for(var5 = 0; var5 < var4; ++var5) {
            var6 = var1.charAt(var5);
            if (!XML11Char.isXML11Valid(var6)) {
               ++var5;
               if (var5 < var4) {
                  this.surrogates(var6, var1.charAt(var5), true);
               } else {
                  this.fatalError("The character '" + var6 + "' is an invalid XML character");
               }
            } else if (var3 && XML11Char.isXML11ValidLiteral(var6)) {
               this._printer.printText(var6);
            } else {
               this.printXMLChar(var6);
            }
         }
      }

   }

   protected void printText(char[] var1, int var2, int var3, boolean var4, boolean var5) throws IOException {
      char var6;
      if (var4) {
         while(true) {
            while(var3-- > 0) {
               var6 = var1[var2++];
               if (!XML11Char.isXML11Valid(var6)) {
                  if (var3-- > 0) {
                     this.surrogates(var6, var1[var2++], true);
                  } else {
                     this.fatalError("The character '" + var6 + "' is an invalid XML character");
                  }
               } else if (var5 && XML11Char.isXML11ValidLiteral(var6)) {
                  this._printer.printText(var6);
               } else {
                  this.printXMLChar(var6);
               }
            }

            return;
         }
      } else {
         while(true) {
            while(var3-- > 0) {
               var6 = var1[var2++];
               if (!XML11Char.isXML11Valid(var6)) {
                  if (var3-- > 0) {
                     this.surrogates(var6, var1[var2++], true);
                  } else {
                     this.fatalError("The character '" + var6 + "' is an invalid XML character");
                  }
               } else if (var5 && XML11Char.isXML11ValidLiteral(var6)) {
                  this._printer.printText(var6);
               } else {
                  this.printXMLChar(var6);
               }
            }

            return;
         }
      }
   }

   public boolean reset() {
      super.reset();
      return true;
   }
}

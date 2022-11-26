package com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.output;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.trax.SAX2DOM;
import com.oracle.wls.shaded.org.apache.xml.serializer.SerializationHandler;
import com.oracle.wls.shaded.org.apache.xml.serializer.ToHTMLStream;
import com.oracle.wls.shaded.org.apache.xml.serializer.ToTextStream;
import com.oracle.wls.shaded.org.apache.xml.serializer.ToUnknownStream;
import com.oracle.wls.shaded.org.apache.xml.serializer.ToXMLSAXHandler;
import com.oracle.wls.shaded.org.apache.xml.serializer.ToXMLStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;

public class TransletOutputHandlerFactory {
   public static final int STREAM = 0;
   public static final int SAX = 1;
   public static final int DOM = 2;
   private String _encoding = "utf-8";
   private String _method = null;
   private int _outputType = 0;
   private OutputStream _ostream;
   private Writer _writer;
   private Node _node;
   private Node _nextSibling;
   private int _indentNumber;
   private ContentHandler _handler;
   private LexicalHandler _lexHandler;

   public TransletOutputHandlerFactory() {
      this._ostream = System.out;
      this._writer = null;
      this._node = null;
      this._nextSibling = null;
      this._indentNumber = -1;
      this._handler = null;
      this._lexHandler = null;
   }

   public static TransletOutputHandlerFactory newInstance() {
      return new TransletOutputHandlerFactory();
   }

   public void setOutputType(int outputType) {
      this._outputType = outputType;
   }

   public void setEncoding(String encoding) {
      if (encoding != null) {
         this._encoding = encoding;
      }

   }

   public void setOutputMethod(String method) {
      this._method = method;
   }

   public void setOutputStream(OutputStream ostream) {
      this._ostream = ostream;
   }

   public void setWriter(Writer writer) {
      this._writer = writer;
   }

   public void setHandler(ContentHandler handler) {
      this._handler = handler;
   }

   public void setLexicalHandler(LexicalHandler lex) {
      this._lexHandler = lex;
   }

   public void setNode(Node node) {
      this._node = node;
   }

   public Node getNode() {
      return this._handler instanceof SAX2DOM ? ((SAX2DOM)this._handler).getDOM() : null;
   }

   public void setNextSibling(Node nextSibling) {
      this._nextSibling = nextSibling;
   }

   public void setIndentNumber(int value) {
      this._indentNumber = value;
   }

   public SerializationHandler getSerializationHandler() throws IOException, ParserConfigurationException {
      SerializationHandler result = null;
      switch (this._outputType) {
         case 0:
            if (this._method == null) {
               result = new ToUnknownStream();
            } else if (this._method.equalsIgnoreCase("xml")) {
               result = new ToXMLStream();
            } else if (this._method.equalsIgnoreCase("html")) {
               result = new ToHTMLStream();
            } else if (this._method.equalsIgnoreCase("text")) {
               result = new ToTextStream();
            }

            if (result != null && this._indentNumber >= 0) {
               ((SerializationHandler)result).setIndentAmount(this._indentNumber);
            }

            ((SerializationHandler)result).setEncoding(this._encoding);
            if (this._writer != null) {
               ((SerializationHandler)result).setWriter(this._writer);
            } else {
               ((SerializationHandler)result).setOutputStream(this._ostream);
            }

            return (SerializationHandler)result;
         case 2:
            this._handler = this._node != null ? new SAX2DOM(this._node, this._nextSibling) : new SAX2DOM();
            this._lexHandler = (LexicalHandler)this._handler;
         case 1:
            if (this._method == null) {
               this._method = "xml";
            }

            ToXMLSAXHandler result;
            if (this._lexHandler == null) {
               result = new ToXMLSAXHandler(this._handler, this._encoding);
            } else {
               result = new ToXMLSAXHandler(this._handler, this._lexHandler, this._encoding);
            }

            return result;
         default:
            return null;
      }
   }
}

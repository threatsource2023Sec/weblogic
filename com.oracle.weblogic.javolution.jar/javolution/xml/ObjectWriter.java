package javolution.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import javolution.io.Utf8ByteBufferWriter;
import javolution.io.Utf8StreamWriter;
import javolution.lang.Reusable;
import javolution.lang.Text;
import javolution.util.FastTable;
import javolution.xml.pull.XmlPullParserImpl;
import javolution.xml.sax.ContentHandler;
import javolution.xml.sax.WriterHandler;
import org.xml.sax.SAXException;

public class ObjectWriter implements Reusable {
   static final Text JAVOLUTION_PREFIX = Text.intern("j");
   static final Text JAVOLUTION_URI = Text.intern("http://javolution.org");
   private final Utf8StreamWriter _utf8StreamWriter = new Utf8StreamWriter();
   private final Utf8ByteBufferWriter _utf8ByteBufferWriter = new Utf8ByteBufferWriter();
   private final WriterHandler _writerHandler = new WriterHandler();
   private FastTable _namespaces = new FastTable();
   private final XmlElement _xml = new XmlElement((XmlPullParserImpl)null);
   private String _rootName;
   private boolean _areReferencesEnabled = false;
   private boolean _expandReferences = false;
   private boolean _isClassIdentifierEnabled = true;

   public void setNamespace(String var1, String var2) {
      if (var1.length() == 1 && var1.charAt(0) == 'j') {
         throw new IllegalArgumentException("Prefix: \"j\" is reserved.");
      } else {
         this._namespaces.addLast(this.toCharSeq(var1));
         this._namespaces.addLast(this.toCharSeq(var2));
         if (var1.length() == 0) {
            this._xml._packagePrefixes.addLast("j");
            this._xml._packagePrefixes.addLast("");
         }

      }
   }

   public void setPackagePrefix(String var1, String var2) {
      this.setNamespace(var1, "java:" + var2);
      this._xml._packagePrefixes.addLast(var1);
      this._xml._packagePrefixes.addLast(var2);
   }

   public void write(Object var1, Writer var2) throws IOException {
      try {
         this._writerHandler.setWriter(var2);
         this.write(var1, (ContentHandler)this._writerHandler);
      } catch (SAXException var7) {
         if (var7.getException() instanceof IOException) {
            throw (IOException)var7.getException();
         }
      } finally {
         this._writerHandler.reset();
      }

   }

   public void write(Object var1, OutputStream var2) throws IOException {
      try {
         this._utf8StreamWriter.setOutputStream(var2);
         this._writerHandler.setWriter(this._utf8StreamWriter);
         this.write(var1, (ContentHandler)this._writerHandler);
      } catch (SAXException var7) {
         if (var7.getException() instanceof IOException) {
            throw (IOException)var7.getException();
         }
      } finally {
         this._utf8StreamWriter.reset();
         this._writerHandler.reset();
      }

   }

   public void write(Object var1, ByteBuffer var2) throws IOException {
      try {
         this._utf8ByteBufferWriter.setByteBuffer(var2);
         this._writerHandler.setWriter(this._utf8ByteBufferWriter);
         this.write(var1, (ContentHandler)this._writerHandler);
      } catch (SAXException var7) {
         if (var7.getException() instanceof IOException) {
            throw (IOException)var7.getException();
         }
      } finally {
         this._utf8ByteBufferWriter.reset();
         this._writerHandler.reset();
      }

   }

   public void write(Object var1, ContentHandler var2) throws SAXException {
      var2.startDocument();
      boolean var10 = false;

      int var3;
      CharSequence var4;
      try {
         var10 = true;
         var2.startPrefixMapping(JAVOLUTION_PREFIX, JAVOLUTION_URI);
         var3 = 0;

         while(var3 < this._namespaces.size()) {
            var4 = (CharSequence)this._namespaces.get(var3++);
            CharSequence var5 = (CharSequence)this._namespaces.get(var3++);
            var2.startPrefixMapping(var4, var5);
         }

         this._xml._formatHandler = var2;
         this._xml._areReferencesEnabled = this._areReferencesEnabled;
         this._xml._expandReferences = this._expandReferences;
         this._xml._isClassIdentifierEnabled = this._isClassIdentifierEnabled;
         if (this._rootName != null) {
            this._xml.add(var1, this._rootName);
            var10 = false;
         } else {
            this._xml.add(var1);
            var10 = false;
         }
      } finally {
         if (var10) {
            var2.endPrefixMapping(JAVOLUTION_PREFIX);
            int var7 = 0;

            while(var7 < this._namespaces.size()) {
               CharSequence var8 = (CharSequence)this._namespaces.get(var7++);
               ++var7;
               var2.endPrefixMapping(var8);
            }

            var2.endDocument();
            this._xml.reset();
         }
      }

      var2.endPrefixMapping(JAVOLUTION_PREFIX);
      var3 = 0;

      while(var3 < this._namespaces.size()) {
         var4 = (CharSequence)this._namespaces.get(var3++);
         ++var3;
         var2.endPrefixMapping(var4);
      }

      var2.endDocument();
      this._xml.reset();
   }

   public void reset() {
      this._xml.reset();
      this._namespaces.clear();
      this._xml._packagePrefixes.clear();
      this._areReferencesEnabled = false;
      this._expandReferences = false;
      this._isClassIdentifierEnabled = true;
   }

   private CharSequence toCharSeq(Object var1) {
      return (CharSequence)(var1 instanceof CharSequence ? (CharSequence)var1 : Text.valueOf((Object)((String)var1)));
   }

   public void setReferencesEnabled(boolean var1) {
      this._areReferencesEnabled = var1;
   }

   public void setExpandReferences(boolean var1) {
      this._expandReferences = var1;
   }

   public void setClassIdentifierEnabled(boolean var1) {
      this._isClassIdentifierEnabled = var1;
   }

   public void setRootName(String var1) {
      this._rootName = var1;
   }
}

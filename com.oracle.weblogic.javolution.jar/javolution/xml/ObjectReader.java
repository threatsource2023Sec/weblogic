package javolution.xml;

import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import javolution.lang.Reusable;
import javolution.xml.pull.XmlPullParserException;
import javolution.xml.pull.XmlPullParserImpl;

public class ObjectReader implements Reusable {
   private final XmlElement _xml = new XmlElement(new XmlPullParserImpl());

   public Object read(Reader var1) throws XmlException {
      this._xml._parser.setInput(var1);
      return this.parse();
   }

   public Object read(InputStream var1) throws XmlException {
      this._xml._parser.setInput(var1);
      return this.parse();
   }

   public Object read(ByteBuffer var1) throws XmlException {
      this._xml._parser.setInput(var1);
      return this.parse();
   }

   private Object parse() throws XmlException {
      Object var2;
      try {
         XmlPullParserImpl var10001 = this._xml._parser;
         this._xml._parser.setFeature("http://javolution.org/xml/pull/ignore-whitespace", true);
         this._xml._areReferencesEnabled = true;
         Object var1 = this._xml.getNext();
         if (this._xml.hasNext() || this._xml._parser.getEventType() != 1) {
            throw new XmlException("End Document Event Expected");
         }

         var2 = var1;
      } catch (XmlPullParserException var6) {
         throw new XmlException(var6);
      } finally {
         this.reset();
      }

      return var2;
   }

   public void reset() {
      this._xml.reset();
   }
}

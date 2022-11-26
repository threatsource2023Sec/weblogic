package weblogic.xml.babel.stream;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.xml.sax.InputSource;
import weblogic.utils.collections.CircularQueue;
import weblogic.xml.stream.StartDocument;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.NullEvent;

public class XMLEventPlayer extends XMLInputStreamBase {
   protected XMLEventReader reader;

   public XMLEventPlayer() {
      this.elementQ = new CircularQueue(8);
   }

   public void open(InputSource inputSource) throws XMLStreamException {
      try {
         this.reader = new XMLEventReader(this.resolve(inputSource));
         this.open = true;
         this.parseSome();
         if (inputSource.getByteStream() != null) {
            this.checkEncoding(inputSource.getByteStream());
         }

      } catch (IOException var3) {
         throw new XMLStreamException("Unable to instantiate the stream, the error was: " + var3.getMessage());
      }
   }

   public void open(InputStream inputStream) throws XMLStreamException {
      try {
         BufferedInputStream bs = new BufferedInputStream(inputStream);
         bs.mark(4096);
         this.reader = new XMLEventReader(new InputStreamReader(bs));
         this.open = true;
         this.parseSome();

         try {
            XMLEvent e = (XMLEvent)this.elementQ.peek();
            if (e.isStartDocument() && !((StartDocument)e).getCharacterEncodingScheme().equals("")) {
               bs.reset();
               this.checkEncoding(bs);
            }
         } catch (IOException var4) {
         }

      } catch (IOException var5) {
         throw new XMLStreamException("Unable to instantiate the stream, the error was: " + var5.getMessage());
      }
   }

   public void open(Reader reader) throws XMLStreamException {
      try {
         this.reader = new XMLEventReader(reader);
         this.open = true;
         this.parseSome();
      } catch (IOException var3) {
         throw new XMLStreamException("Unable to instantiate the stream, the error was: " + var3.getMessage());
      }
   }

   protected void checkEncoding(InputStream is) throws XMLStreamException {
      XMLEvent e = (XMLEvent)this.elementQ.remove();
      if (e.isStartDocument()) {
         StartDocument sd = (StartDocument)e;

         try {
            this.reader = new XMLEventReader(new InputStreamReader(is, sd.getCharacterEncodingScheme()));
            this.open = true;
            this.parseSome();
         } catch (UnsupportedEncodingException var5) {
            throw new XMLStreamException(var5);
         } catch (IOException var6) {
            throw new XMLStreamException(var6);
         }
      }

   }

   protected Reader resolve(InputSource source) throws XMLStreamException {
      if (source.getCharacterStream() != null) {
         return source.getCharacterStream();
      } else if (source.getByteStream() != null) {
         return new InputStreamReader(source.getByteStream());
      } else {
         throw new XMLStreamException("XMLEventPlayer needs a stream or a reader as input");
      }
   }

   public boolean parseSome() throws XMLStreamException {
      try {
         if (!this.reader.hasNext()) {
            return false;
         } else {
            XMLEvent e = this.reader.readElement();
            this.add(e);
            return true;
         }
      } catch (IOException var2) {
         throw new XMLStreamException("Unable to read from the reader " + var2.getMessage());
      }
   }

   public boolean hasNext() throws XMLStreamException {
      if (!this.open) {
         return false;
      } else if (!this.elementQ.isEmpty()) {
         return true;
      } else {
         try {
            return this.reader != null && this.reader.hasNext();
         } catch (IOException var2) {
            throw new XMLStreamException("Unable to read from the reader " + var2.getMessage());
         }
      }
   }

   public XMLEvent peek() throws XMLStreamException {
      if (!this.elementQ.isEmpty()) {
         return (XMLEvent)this.elementQ.peek();
      } else {
         try {
            return (XMLEvent)(this.reader != null && this.reader.hasNext() && this.parseSome() ? (XMLEvent)this.elementQ.peek() : new NullEvent());
         } catch (IOException var2) {
            throw new XMLStreamException("Unable to read from the reader " + var2.getMessage());
         }
      }
   }

   public static void main(String[] args) throws Exception {
      XMLEventPlayer input = new XMLEventPlayer();
      input.open((Reader)(new FileReader(args[0])));

      while(input.hasNext()) {
         XMLEvent elementEvent = input.next();
         System.out.print("EVENT: " + elementEvent.getTypeAsString() + " [");
         System.out.print(elementEvent);
         System.out.println("]");
      }

   }
}

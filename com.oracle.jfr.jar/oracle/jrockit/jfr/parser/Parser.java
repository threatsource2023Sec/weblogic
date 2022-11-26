package oracle.jrockit.jfr.parser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Iterator;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class Parser implements Iterable {
   private final FLRInput input;
   private long nextChunk;
   static final String JFR_URI = "http://www.oracle.com/jrockit/jfr/";
   static final String JFR_NS = "jfr";

   public Parser(String path) throws IOException {
      this(path, true);
   }

   public Parser(String path, boolean useMapped) throws IOException {
      this(new File(path));
   }

   public Parser(File file, boolean useMapping) throws IOException {
      RandomAccessFile r = new RandomAccessFile(file, "r");
      long size = r.length();
      FLRInput in = null;
      if (useMapping && size < 52428800L) {
         FileChannel ch = r.getChannel();

         try {
            MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0L, size);
            in = new MappedFLRInput(buf);
         } catch (IOException var9) {
         }
      }

      if (in == null) {
         in = new RandomAccessFileFLRInput(r);
      }

      this.input = (FLRInput)in;
   }

   public Parser(File file) throws IOException {
      this(file, true);
   }

   public ChunkParser next() throws IOException, ParseException {
      if (this.nextChunk >= this.input.size()) {
         return null;
      } else {
         this.input.position(this.nextChunk);
         ChunkParser p = new ChunkParser(this.input);
         this.nextChunk = p.getChunkEnd();
         return p;
      }
   }

   public void writeXML(ContentHandler h) throws SAXException, IOException, ParseException {
      h.startElement("http://www.oracle.com/jrockit/jfr/", "flightrecording", "jfr:flightrecording", new AttributesImpl());

      ChunkParser c;
      while((c = this.next()) != null) {
         c.writeXML(h);
      }

      h.endElement("http://www.oracle.com/jrockit/jfr/", "flightrecording", "jfr:flightrecording");
   }

   public Iterator iterator() {
      return new Iterator() {
         private ChunkParser p;

         public boolean hasNext() {
            try {
               this.p = Parser.this.next();
            } catch (Exception var2) {
               throw new RuntimeException(var2);
            }

            return this.p != null;
         }

         public ChunkParser next() {
            return this.p;
         }

         public void remove() {
            throw new UnsupportedOperationException();
         }
      };
   }

   public void close() throws IOException {
      this.input.close();
   }

   public static void main(String[] args) throws Exception {
      int i = 0;
      boolean xml = false;
      if (args[0].equals("-xml")) {
         xml = true;
         ++i;
      }

      Parser p = new Parser(args[i]);
      if (xml) {
         TransformerFactory f = TransformerFactory.newInstance();
         if (!f.getFeature("http://javax.xml.transform.sax.SAXTransformerFactory/feature")) {
            throw new SAXException("cannot construct output handler");
         } else {
            SAXTransformerFactory sf = (SAXTransformerFactory)f;
            sf.setAttribute("indent-number", "3");
            TransformerHandler th = sf.newTransformerHandler();
            th.getTransformer().setOutputProperty("method", "xml");
            th.getTransformer().setOutputProperty("indent", "yes");
            th.getTransformer().setOutputProperty("encoding", "iso-8859-1");
            PrintWriter w = new PrintWriter(System.out);
            th.setResult(new StreamResult(w));
            p.writeXML(th);
            w.flush();
         }
      } else {
         ChunkParser c;
         try {
            while((c = p.next()) != null) {
               System.out.println("new chunk");

               FLREvent o;
               while((o = c.next()) != null) {
                  System.out.println(o);
               }
            }
         } finally {
            System.out.flush();
         }

      }
   }
}

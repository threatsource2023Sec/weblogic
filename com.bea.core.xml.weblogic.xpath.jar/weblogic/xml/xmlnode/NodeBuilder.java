package weblogic.xml.xmlnode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.xml.sax.InputSource;
import weblogic.utils.AssertionError;
import weblogic.xml.babel.stream.XMLInputStreamBase;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.util.RecyclingFactory;

public class NodeBuilder {
   private static RecyclingFactory streamFactory = new RecyclingFactory();
   private XMLNode current;

   public NodeBuilder(XMLNode root, InputStream stream) throws IOException {
      this.current = root;
      this.build(stream);
   }

   public NodeBuilder(XMLNode root, Reader reader) throws IOException {
      this.current = root;
      this.build(reader);
   }

   private void build(Object readerOrInputStream) throws IOException {
      XMLInputStream xmlStreamFromPool = null;

      try {
         if (readerOrInputStream instanceof InputStream) {
            xmlStreamFromPool = streamFactory.remove((InputStream)readerOrInputStream);
         } else {
            if (!(readerOrInputStream instanceof Reader)) {
               throw new AssertionError("Object passed for getting stream from pool must be either Reader of InputStream");
            }

            xmlStreamFromPool = streamFactory.remove((Reader)readerOrInputStream);
         }

         this.current.readInternal(xmlStreamFromPool);
      } finally {
         if (xmlStreamFromPool != null) {
            try {
               streamFactory.add(xmlStreamFromPool);
            } catch (IOException var9) {
               throw new AssertionError("Exception while adding the stream back to Pool", var9);
            }
         }

      }

   }

   public static void main(String[] args) throws Exception {
      int iteration = Integer.parseInt(args[0]);
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      FileInputStream fio = new FileInputStream(args[1]);

      int ch;
      while((ch = fio.read()) != -1) {
         bout.write((char)ch);
      }

      bout.flush();
      byte[] bytes = bout.toByteArray();
      long start = System.currentTimeMillis();

      int i;
      XMLNode node;
      for(i = 0; i < iteration; ++i) {
         node = new XMLNode();
         node.read((InputStream)(new ByteArrayInputStream(bytes)));
      }

      System.out.println("old node " + (System.currentTimeMillis() - start));
      start = System.currentTimeMillis();

      for(i = 0; i < iteration; ++i) {
         XMLInputStreamBase base = new XMLInputStreamBase();
         base.open(new InputSource(new ByteArrayInputStream(bytes)));

         while(base.hasNext()) {
            base.next();
         }
      }

      System.out.println("stream " + (System.currentTimeMillis() - start));
      start = System.currentTimeMillis();

      for(i = 0; i < iteration; ++i) {
         node = new XMLNode();
         new NodeBuilder(node, new ByteArrayInputStream(bytes));
      }

      System.out.println("new node " + (System.currentTimeMillis() - start));
   }
}

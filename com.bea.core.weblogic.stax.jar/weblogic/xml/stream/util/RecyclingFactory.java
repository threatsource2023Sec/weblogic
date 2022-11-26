package weblogic.xml.stream.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import weblogic.utils.collections.Pool;
import weblogic.utils.collections.StackPool;
import weblogic.xml.babel.reader.XmlReader;
import weblogic.xml.babel.stream.XMLInputStreamBase;
import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLStreamException;

/** @deprecated */
@Deprecated
public final class RecyclingFactory {
   private static final boolean debug = false;
   private Pool pool;
   private static final XMLInputStreamFactory factory = XMLInputStreamFactory.newInstance();

   public RecyclingFactory() {
      this.pool = new StackPool(32);
   }

   public RecyclingFactory(int poolSize) {
      this.pool = new StackPool(poolSize);
   }

   private static XMLInputStream wrap(XMLInputStream stream, ElementFilter filter) throws XMLStreamException {
      return new XMLInputStreamFilterBase(stream, filter);
   }

   public XMLInputStream remove(InputStream stream) throws XMLStreamException {
      try {
         return this.remove(XmlReader.createReader(stream));
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   public XMLInputStream remove(Reader reader) throws XMLStreamException {
      XMLInputStreamBase base = (XMLInputStreamBase)this.pool.remove();
      if (base == null) {
         return factory.newInputStream(reader);
      } else {
         base.recycle(reader);
         return base;
      }
   }

   public XMLInputStream remove(InputStream stream, ElementFilter filter) throws XMLStreamException {
      return wrap(this.remove(stream), filter);
   }

   public XMLInputStream remove(Reader reader, ElementFilter filter) throws XMLStreamException {
      return wrap(this.remove(reader), filter);
   }

   public boolean add(XMLInputStream stream) throws XMLStreamException {
      if (stream instanceof XMLInputStreamBase) {
         ((XMLInputStreamBase)stream).clear();
         this.pool.add(stream);
         return true;
      } else {
         return stream instanceof XMLInputStreamFilterBase ? this.add(((XMLInputStreamFilterBase)stream).getParent()) : false;
      }
   }

   public static void main(String[] args) throws Exception {
      RecyclingFactory factory = new RecyclingFactory();
      TypeFilter filter = new TypeFilter(6);

      for(int i = 0; i < 50; ++i) {
         XMLInputStream input = factory.remove((InputStream)(new FileInputStream(args[0])));
         XMLInputStream input2 = factory.remove((InputStream)(new FileInputStream(args[0])));
         factory.add(input);
         factory.add(input2);
         input = factory.remove((InputStream)(new FileInputStream(args[0])), filter);
         input2 = wrap(input, filter);
         System.out.println(factory.add(input));
         System.out.println(factory.add(input2));
      }

   }
}

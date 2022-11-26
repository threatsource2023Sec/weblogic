package weblogic.xml.stax;

import java.io.FileReader;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import weblogic.utils.XXEUtils;
import weblogic.xml.stax.filters.TypeFilter;

public class StreamReaderFilter extends StreamReaderDelegate {
   private StreamFilter filter;

   public StreamReaderFilter(XMLStreamReader reader) {
      super(reader);
   }

   public StreamReaderFilter(XMLStreamReader reader, StreamFilter filter) {
      super(reader);
      this.setFilter(filter);
   }

   public void setFilter(StreamFilter filter) {
      this.filter = filter;
   }

   public int next() throws XMLStreamException {
      if (!this.hasNext()) {
         throw new IllegalStateException("next() may not be called  when there are no more  items to return");
      } else {
         int retval;
         for(retval = super.next(); retval != -1 && !this.filter.accept(this.getParent()) && super.hasNext(); retval = super.next()) {
         }

         return retval;
      }
   }

   public boolean hasNext() throws XMLStreamException {
      while(super.hasNext()) {
         if (this.filter.accept(this.getParent())) {
            return true;
         }

         super.next();
      }

      return false;
   }

   public static void main(String[] args) throws Exception {
      XMLInputFactory factory = XXEUtils.createXMLInputFactoryInstance();
      TypeFilter f = new TypeFilter();
      f.addType(1);
      f.addType(2);
      XMLStreamReader reader = factory.createFilteredReader(factory.createXMLStreamReader(new FileReader(args[0])), f);

      while(reader.hasNext()) {
         System.out.println(reader.getLocalName());
         reader.next();
      }

   }
}

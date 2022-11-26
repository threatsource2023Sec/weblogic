package weblogic.j2ee.descriptor;

import com.bea.staxb.runtime.StaxDeSerializer;
import com.bea.staxb.runtime.StaxSerializer;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public interface AbsoluteOrderingBean extends StaxSerializer, StaxDeSerializer {
   NameOrOrderingOthersBean[] getNameOrOrderingOther();

   NameOrOrderingOthersBean createNameOrOrderingOther();

   void destroyNameOrOrderingOther(NameOrOrderingOthersBean var1);

   void readFrom(XMLStreamReader var1) throws XMLStreamException;

   void writeTo(XMLStreamWriter var1) throws XMLStreamException;
}

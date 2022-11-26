package weblogic.xml.dom.marshal;

import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface WLDOMStructure {
   void marshal(Element var1, Node var2, Map var3) throws MarshalException;

   void unmarshal(Node var1) throws MarshalException;
}

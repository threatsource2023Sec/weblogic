package weblogic.xml.dom.marshal;

import javax.xml.namespace.QName;

public interface WLDOMStructureFactory {
   WLDOMStructure newWLDOMStructure();

   QName getQName();
}

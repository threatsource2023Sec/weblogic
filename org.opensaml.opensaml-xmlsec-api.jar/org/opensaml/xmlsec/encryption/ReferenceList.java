package org.opensaml.xmlsec.encryption;

import java.util.List;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface ReferenceList extends XMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ReferenceList";
   QName DEFAULT_ELEMENT_NAME = new QName("http://www.w3.org/2001/04/xmlenc#", "ReferenceList", "xenc");

   @Nonnull
   List getReferences();

   @Nonnull
   List getDataReferences();

   @Nonnull
   List getKeyReferences();
}

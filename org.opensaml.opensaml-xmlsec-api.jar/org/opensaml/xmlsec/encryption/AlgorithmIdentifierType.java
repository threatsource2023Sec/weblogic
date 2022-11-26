package org.opensaml.xmlsec.encryption;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;

public interface AlgorithmIdentifierType extends XMLObject {
   String TYPE_LOCAL_NAME = "AlgorithmIdentifierType";
   QName TYPE_NAME = new QName("http://www.w3.org/2009/xmlenc11#", "AlgorithmIdentifierType", "xenc11");
   String ALGORITHM_ATTRIB_NAME = "Algorithm";

   @Nullable
   String getAlgorithm();

   void setAlgorithm(@Nullable String var1);

   @Nullable
   XMLObject getParameters();

   void setParameters(@Nullable XMLObject var1);
}

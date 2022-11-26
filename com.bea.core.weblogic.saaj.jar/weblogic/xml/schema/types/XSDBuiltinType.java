package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public interface XSDBuiltinType {
   QName getTypeName();

   String getXml();

   String getCanonicalXml();

   Object getJavaObject();
}

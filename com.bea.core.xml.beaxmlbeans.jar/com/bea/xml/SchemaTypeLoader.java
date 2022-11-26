package com.bea.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface SchemaTypeLoader {
   SchemaType findType(QName var1);

   SchemaType findDocumentType(QName var1);

   SchemaType findAttributeType(QName var1);

   SchemaGlobalElement findElement(QName var1);

   SchemaGlobalAttribute findAttribute(QName var1);

   SchemaModelGroup findModelGroup(QName var1);

   SchemaAttributeGroup findAttributeGroup(QName var1);

   boolean isNamespaceDefined(String var1);

   SchemaType.Ref findTypeRef(QName var1);

   SchemaType.Ref findDocumentTypeRef(QName var1);

   SchemaType.Ref findAttributeTypeRef(QName var1);

   SchemaGlobalElement.Ref findElementRef(QName var1);

   SchemaGlobalAttribute.Ref findAttributeRef(QName var1);

   SchemaModelGroup.Ref findModelGroupRef(QName var1);

   SchemaAttributeGroup.Ref findAttributeGroupRef(QName var1);

   SchemaIdentityConstraint.Ref findIdentityConstraintRef(QName var1);

   SchemaType typeForSignature(String var1);

   SchemaType typeForClassname(String var1);

   InputStream getSourceAsStream(String var1);

   String compilePath(String var1, XmlOptions var2) throws XmlException;

   String compileQuery(String var1, XmlOptions var2) throws XmlException;

   XmlObject newInstance(SchemaType var1, XmlOptions var2);

   XmlObject parse(String var1, SchemaType var2, XmlOptions var3) throws XmlException;

   XmlObject parse(File var1, SchemaType var2, XmlOptions var3) throws XmlException, IOException;

   XmlObject parse(URL var1, SchemaType var2, XmlOptions var3) throws XmlException, IOException;

   XmlObject parse(InputStream var1, SchemaType var2, XmlOptions var3) throws XmlException, IOException;

   XmlObject parse(XMLStreamReader var1, SchemaType var2, XmlOptions var3) throws XmlException;

   XmlObject parse(Reader var1, SchemaType var2, XmlOptions var3) throws XmlException, IOException;

   XmlObject parse(Node var1, SchemaType var2, XmlOptions var3) throws XmlException;

   /** @deprecated */
   XmlObject parse(XMLInputStream var1, SchemaType var2, XmlOptions var3) throws XmlException, XMLStreamException;

   XmlSaxHandler newXmlSaxHandler(SchemaType var1, XmlOptions var2);

   DOMImplementation newDomImplementation(XmlOptions var1);

   /** @deprecated */
   XMLInputStream newValidatingXMLInputStream(XMLInputStream var1, SchemaType var2, XmlOptions var3) throws XmlException, XMLStreamException;
}

package weblogic.xml.xpath.stream;

import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.XMLName;

public interface StreamNode {
   int XML_EVENT = 1;
   int START_ELEMENT = 2;
   int START_DOCUMENT = 256;
   int END_ELEMENT = 4;
   int END_DOCUMENT = 512;
   int CHARACTER_DATA = 16;
   int COMMENT = 32;
   int SPACE = 64;
   int NULL_ELEMENT = 128;
   int PROCESSING_INSTRUCTION = 8;
   int ATTRIBUTE = -1;
   int NAMESPACE = -2;

   XMLName getNodeName();

   int getNodeType();

   String getNodeStringValue();

   Attribute getAttributeByName(XMLName var1);
}

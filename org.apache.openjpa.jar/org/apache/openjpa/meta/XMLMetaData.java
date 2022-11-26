package org.apache.openjpa.meta;

import java.io.Serializable;

public interface XMLMetaData extends Serializable {
   String defaultName = "##default";
   int XMLTYPE = 0;
   int ELEMENT = 1;
   int ATTRIBUTE = 2;

   boolean isXmlRootElement();

   boolean isXmlElement();

   boolean isXmlAttribute();

   XMLMetaData getFieldMapping(String var1);

   void setType(Class var1);

   Class getType();

   int getTypeCode();

   String getName();

   String getXmlname();

   String getXmlnamespace();

   void setName(String var1);

   void setXmlname(String var1);

   void setXmlnamespace(String var1);

   void setXmltype(int var1);

   int getXmltype();

   void setXmlRootElement(boolean var1);

   void addField(String var1, XMLMetaData var2);
}

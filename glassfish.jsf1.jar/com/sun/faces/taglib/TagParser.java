package com.sun.faces.taglib;

public interface TagParser {
   String getMessage();

   boolean hasFailed();

   void setValidatorInfo(ValidatorInfo var1);

   void parseStartElement();

   void parseEndElement();
}

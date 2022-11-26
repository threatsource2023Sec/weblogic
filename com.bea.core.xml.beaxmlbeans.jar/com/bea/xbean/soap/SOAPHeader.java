package com.bea.xbean.soap;

import java.util.Iterator;

public interface SOAPHeader extends SOAPElement {
   SOAPHeaderElement addHeaderElement(Name var1) throws SOAPException;

   Iterator examineHeaderElements(String var1);

   Iterator extractHeaderElements(String var1);

   Iterator examineMustUnderstandHeaderElements(String var1);

   Iterator examineAllHeaderElements();

   Iterator extractAllHeaderElements();
}

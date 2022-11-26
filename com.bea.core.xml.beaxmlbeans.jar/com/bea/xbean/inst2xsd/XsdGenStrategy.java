package com.bea.xbean.inst2xsd;

import com.bea.xbean.inst2xsd.util.TypeSystemHolder;
import com.bea.xml.XmlObject;

public interface XsdGenStrategy {
   void processDoc(XmlObject[] var1, Inst2XsdOptions var2, TypeSystemHolder var3);
}

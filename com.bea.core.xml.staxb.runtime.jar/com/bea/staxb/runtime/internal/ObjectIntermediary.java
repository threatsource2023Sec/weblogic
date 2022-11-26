package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

interface ObjectIntermediary {
   Object getFinalValue() throws XmlException;

   void addItem(int var1, Object var2) throws XmlException;

   Object getActualValue();

   void setValue(RuntimeBindingProperty var1, Object var2) throws XmlException;
}

package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;
import org.w3c.dom.Node;

interface TypeUnmarshaller {
   Object unmarshal(UnmarshalResult var1, Node var2, Class var3) throws XmlException;

   void unmarshalIntoIntermediary(Object var1, UnmarshalResult var2, Node var3, Class var4) throws XmlException;

   Object unmarshalAttribute(UnmarshalResult var1) throws XmlException;

   void unmarshalAttribute(Object var1, UnmarshalResult var2) throws XmlException;

   void initialize(RuntimeBindingTypeTable var1, BindingLoader var2) throws XmlException;
}

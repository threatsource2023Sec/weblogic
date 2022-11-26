package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;

interface TypeUnmarshaller {
   Object unmarshal(UnmarshalResult var1) throws XmlException;

   void unmarshalIntoIntermediary(Object var1, UnmarshalResult var2) throws XmlException;

   Object unmarshalAttribute(UnmarshalResult var1) throws XmlException;

   Object unmarshalAttribute(CharSequence var1, UnmarshalResult var2) throws XmlException;

   void unmarshalAttribute(Object var1, UnmarshalResult var2) throws XmlException;

   void initialize(RuntimeBindingTypeTable var1, BindingLoader var2);
}

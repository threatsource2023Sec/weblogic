package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;

interface TypeMarshaller {
   CharSequence print(Object var1, MarshalResult var2) throws XmlException;
}

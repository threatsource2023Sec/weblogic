package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;
import javax.xml.soap.SOAPElement;

final class LiteralPushMarshalResult extends PushMarshalResult {
   LiteralPushMarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable, SOAPElement soapElement) throws XmlException {
      super(bindingLoader, typeTable, soapElement);
   }
}

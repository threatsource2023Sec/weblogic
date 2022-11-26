package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.runtime.BindingContext;
import com.bea.xbeanmarshal.runtime.Marshaller;
import com.bea.xbeanmarshal.runtime.Unmarshaller;
import com.bea.xml.XmlException;

final class BindingContextImpl implements BindingContext {
   private final BindingLoader bindingLoader;
   private final RuntimeBindingTypeTable typeTable;

   BindingContextImpl(BindingLoader bindingLoader) {
      this.bindingLoader = bindingLoader;
      this.typeTable = RuntimeBindingTypeTable.createTable();
   }

   public Unmarshaller createUnmarshaller() throws XmlException {
      return new UnmarshallerImpl(this.bindingLoader, this.typeTable);
   }

   public Marshaller createMarshaller() throws XmlException {
      return new MarshallerImpl(this.bindingLoader, this.typeTable);
   }
}

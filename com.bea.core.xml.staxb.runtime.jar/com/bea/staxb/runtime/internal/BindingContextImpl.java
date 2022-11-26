package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.BindingContext;
import com.bea.staxb.runtime.EncodingStyle;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.staxb.runtime.Marshaller;
import com.bea.staxb.runtime.SoapMarshaller;
import com.bea.staxb.runtime.SoapUnmarshaller;
import com.bea.staxb.runtime.UnmarshalOptions;
import com.bea.staxb.runtime.Unmarshaller;
import com.bea.xml.XmlException;
import java.util.Collection;
import org.w3c.dom.Element;

final class BindingContextImpl implements BindingContext {
   private final BindingLoader bindingLoader;
   private final RuntimeBindingTypeTable typeTable;
   private final SchemaTypeLoaderProvider schemaTypeLoaderProvider;

   BindingContextImpl(BindingLoader bindingLoader, SchemaTypeLoaderProvider provider) {
      this.bindingLoader = bindingLoader;
      this.typeTable = RuntimeBindingTypeTable.createTable();
      this.schemaTypeLoaderProvider = provider;
   }

   public Unmarshaller createUnmarshaller() throws XmlException {
      return new UnmarshallerImpl(this.bindingLoader, this.typeTable, this.schemaTypeLoaderProvider);
   }

   public Marshaller createMarshaller() throws XmlException {
      return new MarshallerImpl(this.bindingLoader, this.typeTable, this.schemaTypeLoaderProvider);
   }

   public SoapMarshaller createSoapMarshaller(EncodingStyle encodingStyle) throws XmlException {
      if (encodingStyle == null) {
         throw new IllegalArgumentException("null encodingStyle");
      } else {
         return new SoapMarshallerImpl(this.bindingLoader, this.typeTable, encodingStyle);
      }
   }

   public SoapUnmarshaller createSoapUnmarshaller(EncodingStyle encodingStyle, Element referenceRoot) throws XmlException {
      if (encodingStyle == null) {
         throw new IllegalArgumentException("null encodingStyle");
      } else if (referenceRoot == null) {
         throw new IllegalArgumentException("null referenceRoot");
      } else {
         return new SoapUnmarshallerImpl(this.bindingLoader, this.typeTable, encodingStyle, referenceRoot);
      }
   }

   static Collection extractErrorHandler(MarshalOptions options) {
      if (options != null) {
         Collection underlying = options.getErrorListener();
         if (underlying != null) {
            return underlying;
         }
      }

      return FailFastErrorHandler.getInstance();
   }

   static Collection extractErrorHandler(UnmarshalOptions options) {
      if (options != null) {
         Collection underlying = options.getErrorListener();
         if (underlying != null) {
            return underlying;
         }
      }

      return FailFastErrorHandler.getInstance();
   }
}

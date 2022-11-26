package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.xml.XmlException;

final class LiteralMarshalResult extends PullMarshalResult {
   LiteralMarshalResult(BindingLoader loader, RuntimeBindingTypeTable tbl, RuntimeBindingProperty property, Object obj, MarshalOptions options) throws XmlException {
      super(loader, tbl, property, obj, options);
   }
}

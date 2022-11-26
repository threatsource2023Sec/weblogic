package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.xb.xsdschema.NoFixedFacet;
import com.bea.xml.SchemaType;

public class NoFixedFacetImpl extends FacetImpl implements NoFixedFacet {
   public NoFixedFacetImpl(SchemaType sType) {
      super(sType);
   }
}

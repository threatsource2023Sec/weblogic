package com.bea.security.xacml;

import com.bea.common.security.xacml.URI;
import java.util.List;

public class MissingAttributeException extends IndeterminateEvaluationException {
   private URI attributeId;
   private URI dataType;

   public MissingAttributeException(URI attributeId, URI dataType) {
      this.attributeId = attributeId;
      this.dataType = dataType;
   }

   public MissingAttributeException(List indeterminates) {
   }

   public URI getAttributeId() {
      return this.attributeId;
   }

   public URI getDataType() {
      return this.dataType;
   }
}

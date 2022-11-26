package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.attr.StandardAttributes;
import com.bea.common.security.xacml.policy.AttributeValue;
import java.net.URISyntaxException;

public class ValueParameter implements Parameter {
   private URI dataType;
   private String value;

   public ValueParameter(String value) throws InvalidParameterException {
      this(value, "http://www.w3.org/2001/XMLSchema#string");
   }

   public ValueParameter(String value, String dataType) throws InvalidParameterException {
      this.value = value;

      try {
         this.dataType = new URI(dataType);
      } catch (URISyntaxException var4) {
         throw new InvalidParameterException(var4);
      }
   }

   public com.bea.common.security.xacml.policy.Expression toXACML() throws InvalidParameterException {
      try {
         return new AttributeValue((new StandardAttributes()).createAttribute(this.dataType, this.value));
      } catch (InvalidAttributeException var2) {
         throw new InvalidParameterException(var2);
      } catch (com.bea.common.security.xacml.URISyntaxException var3) {
         throw new InvalidParameterException(var3);
      }
   }
}

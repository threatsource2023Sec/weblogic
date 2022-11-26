package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.policy.ActionAttributeDesignator;
import com.bea.common.security.xacml.policy.EnvironmentAttributeDesignator;
import com.bea.common.security.xacml.policy.ResourceAttributeDesignator;
import com.bea.common.security.xacml.policy.SubjectAttributeDesignator;
import java.net.URISyntaxException;

public class AttributeParameter implements Parameter {
   public static final String SUBJECT = "SUBJECT";
   public static final String ACTION = "ACTION";
   public static final String RESOURCE = "RESOURCE";
   public static final String ENVIRONMENT = "ENVIRONMENT";
   private URI attributeId;
   private String applyTo;
   private boolean mustBePresent;
   private String issuer;
   private URI dataType;
   private URI subjectCategory;

   public AttributeParameter(String applyTo, String attributeId) throws InvalidParameterException {
      this(applyTo, attributeId, "http://www.w3.org/2001/XMLSchema#string", false, (String)null);
   }

   public AttributeParameter(String applyTo, String attributeId, String dataType) throws InvalidParameterException {
      this(applyTo, attributeId, dataType, false, (String)null);
   }

   public AttributeParameter(String applyTo, String attributeId, String dataType, boolean mustBePresent, String issuer) throws InvalidParameterException {
      if (!applyTo.equals("SUBJECT") && !applyTo.equals("ACTION") && !applyTo.equals("RESOURCE") && !applyTo.equals("ENVIRONMENT")) {
         throw new InvalidParameterException("The apply to parameter should be \"SUBJECT\", \"ACTION\", \"RESOURCE\" or \"ENVIRONMENT\"");
      } else if (attributeId != null && attributeId.trim().length() != 0) {
         if (dataType != null && dataType.trim().length() != 0) {
            this.applyTo = applyTo;

            try {
               this.attributeId = new URI(attributeId);
               this.dataType = new URI(dataType);
            } catch (URISyntaxException var7) {
               throw new InvalidParameterException(var7);
            }

            this.mustBePresent = mustBePresent;
            this.issuer = issuer;
         } else {
            throw new InvalidParameterException("The data type should not be null or empty.");
         }
      } else {
         throw new InvalidParameterException("The attribute id should not be null or empty.");
      }
   }

   public AttributeParameter(String attributeId, String dataType, boolean mustBePresent, String issuer, String subjectCategory) throws InvalidParameterException {
      this("SUBJECT", attributeId, dataType, mustBePresent, issuer);
      if (subjectCategory != null) {
         try {
            this.subjectCategory = new URI(subjectCategory);
         } catch (URISyntaxException var7) {
            throw new InvalidParameterException(var7);
         }
      }

   }

   public com.bea.common.security.xacml.policy.Expression toXACML() {
      if (this.applyTo.equalsIgnoreCase("SUBJECT")) {
         return new SubjectAttributeDesignator(this.attributeId, this.dataType, this.mustBePresent, this.issuer, this.subjectCategory);
      } else if (this.applyTo.equalsIgnoreCase("ACTION")) {
         return new ActionAttributeDesignator(this.attributeId, this.dataType, this.mustBePresent, this.issuer);
      } else {
         return (com.bea.common.security.xacml.policy.Expression)(this.applyTo.equalsIgnoreCase("RESOURCE") ? new ResourceAttributeDesignator(this.attributeId, this.dataType, this.mustBePresent, this.issuer) : new EnvironmentAttributeDesignator(this.attributeId, this.dataType, this.mustBePresent, this.issuer));
      }
   }
}

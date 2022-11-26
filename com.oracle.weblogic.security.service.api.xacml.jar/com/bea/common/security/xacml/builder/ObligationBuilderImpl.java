package com.bea.common.security.xacml.builder;

import com.bea.common.security.xacml.InvalidParameterException;
import com.bea.common.security.xacml.InvalidXACMLPolicyException;
import com.bea.common.security.xacml.PolicyUtils;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.XACMLException;
import com.bea.common.security.xacml.attr.StandardAttributes;
import com.bea.common.security.xacml.policy.AttributeAssignment;
import com.bea.common.security.xacml.policy.AttributeValue;
import com.bea.common.security.xacml.policy.Obligation;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

class ObligationBuilderImpl implements ObligationBuilder {
   private boolean isFulfillOnPermit = true;
   private URI obligationId;
   private List attributeAssignments = new ArrayList();

   ObligationBuilderImpl() {
   }

   ObligationBuilderImpl(Obligation obligation) throws InvalidParameterException {
      if (obligation == null) {
         throw new InvalidParameterException("The obligation should not be null.");
      } else {
         this.obligationId = obligation.getId();
         this.isFulfillOnPermit = obligation.isFulfillOnPermit();
         this.attributeAssignments.addAll(obligation.getAttributeAssignments());
      }
   }

   ObligationBuilderImpl(URI obligationId) throws InvalidParameterException {
      if (obligationId == null) {
         throw new InvalidParameterException("The obligation id should not be null or empty.");
      } else {
         this.obligationId = obligationId;
      }
   }

   public ObligationBuilder setObligationId(String id) throws InvalidParameterException {
      if (this.checkNull(id)) {
         throw new InvalidParameterException("The obligation id should not be null or empty.");
      } else {
         try {
            this.obligationId = new URI(id);
            return this;
         } catch (URISyntaxException var3) {
            throw new InvalidParameterException(var3);
         }
      }
   }

   public ObligationBuilder addAttributeAssignment(String attributeId, String dataType, String value) throws InvalidParameterException {
      if (this.checkNull(attributeId)) {
         throw new InvalidParameterException("The attribute id should not be null or empty.");
      } else if (this.checkNull(dataType)) {
         throw new InvalidParameterException("The data type should not be null or empty.");
      } else if (this.checkNull(value)) {
         throw new InvalidParameterException("The value should not be null or empty.");
      } else {
         try {
            AttributeValue attValue = new AttributeValue((new StandardAttributes()).createAttribute(new URI(dataType), value));
            this.attributeAssignments.add(new AttributeAssignment(new URI(attributeId), attValue));
            return this;
         } catch (XACMLException var5) {
            throw new InvalidParameterException(var5);
         } catch (URISyntaxException var6) {
            throw new InvalidParameterException(var6);
         }
      }
   }

   public ObligationBuilder setFulfillOnPermit(boolean isFulfillOnPermit) {
      this.isFulfillOnPermit = isFulfillOnPermit;
      return this;
   }

   public Obligation getResult() throws InvalidXACMLPolicyException {
      if (this.obligationId == null) {
         throw new InvalidXACMLPolicyException("The obligation id should not be null or empty.");
      } else {
         Obligation o = new Obligation(this.obligationId, this.isFulfillOnPermit, this.attributeAssignments);

         try {
            PolicyUtils.checkXACMLSchema(o.toString());
            return o;
         } catch (XACMLException var3) {
            throw new InvalidXACMLPolicyException(var3);
         }
      }
   }

   public List removeAllAttributeAssignments() {
      List tmp = this.attributeAssignments;
      this.attributeAssignments = new ArrayList();
      return tmp;
   }

   private boolean checkNull(String string) {
      return string == null || string.trim().length() == 0;
   }
}

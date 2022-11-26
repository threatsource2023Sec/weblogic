package com.bea.common.security.xacml.policy;

import com.bea.common.security.utils.HashCodeUtil;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class AbstractPolicyCombinerParameters extends CombinerParameters {
   private URI idRef;

   public AbstractPolicyCombinerParameters(List combinerParameters, URI idRef) {
      super(combinerParameters);
      this.idRef = idRef;
   }

   protected AbstractPolicyCombinerParameters(AttributeRegistry registry, Node root, String policyPrefix) throws URISyntaxException, DocumentParseException {
      super(registry, root);
      NamedNodeMap attrs = root.getAttributes();

      try {
         this.idRef = new URI(attrs.getNamedItem(policyPrefix + "IdRef").getNodeValue());
      } catch (java.net.URISyntaxException var6) {
         throw new URISyntaxException(var6);
      }
   }

   public boolean equals(Object other) {
      if (super.equals(other) && other instanceof AbstractPolicyCombinerParameters) {
         AbstractPolicyCombinerParameters o = (AbstractPolicyCombinerParameters)other;
         return this.idRef == o.idRef || this.idRef != null && this.idRef.equals(o.idRef);
      } else {
         return false;
      }
   }

   public int internalHashCode() {
      int result = super.internalHashCode();
      result = HashCodeUtil.hash(result, this.idRef);
      return result;
   }

   public URI getIdRef() {
      return this.idRef;
   }
}

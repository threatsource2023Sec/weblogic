package com.bea.security.providers.xacml;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.GenericBag;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.StringAttributeBag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.AttributeEvaluatorWrapper;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.AttributeEvaluatableFactory;
import com.bea.security.xacml.attr.BaseAttributeDesignatorFactory;
import java.util.ArrayList;
import java.util.Collections;

public class ResourceAttributeDesignatorFactory extends BaseAttributeDesignatorFactory {
   public static final String RESOURCEID_ATTR = "urn:oasis:names:tc:xacml:1.0:resource:resource-id";
   public static final String RESOURCEPARENT_ATTR = "urn:oasis:names:tc:xacml:2.0:resource:resource-parent";
   public static final String RESOURCEANCESTOR_ATTR = "urn:oasis:names:tc:xacml:2.0:resource:resource-ancestor";
   public static final String TARGETNAMESPACE_ATTR = "urn:oasis:names:tc:xacml:2.0:resource:target-namespace";
   private static final Bag EMPTY_BAG = new StringAttributeBag(Collections.unmodifiableList(new ArrayList()));
   private final URI RESOURCEID_ATTR_URI;
   private final URI RESOURCEPARENT_ATTR_URI;
   private final URI RESOURCEANCESTOR_ATTR_URI;
   private final URI TARGETNAMESPACE_ATTR_URI;
   private final URI STRING_TYPE;
   private String resourceIssuer;

   public ResourceAttributeDesignatorFactory(String resourceIssuer) throws URISyntaxException {
      try {
         this.RESOURCEID_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
         this.RESOURCEPARENT_ATTR_URI = new URI("urn:oasis:names:tc:xacml:2.0:resource:resource-parent");
         this.RESOURCEANCESTOR_ATTR_URI = new URI("urn:oasis:names:tc:xacml:2.0:resource:resource-ancestor");
         this.TARGETNAMESPACE_ATTR_URI = new URI("urn:oasis:names:tc:xacml:2.0:resource:target-namespace");
      } catch (java.net.URISyntaxException var3) {
         throw new URISyntaxException(var3);
      }

      this.STRING_TYPE = Type.STRING.getDataType();
      this.resourceIssuer = resourceIssuer;
   }

   public ResourceConverter getResourceConverter(final EvaluationCtx context) {
      return context instanceof ExtendedEvaluationCtx ? ((ExtendedEvaluationCtx)context).getResourceConverter() : new ResourceConverter() {
         private AttributeEvaluatableFactory aef = context.getResourceAttributes();

         public StringAttribute getResourceID() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(ResourceAttributeDesignatorFactory.this.RESOURCEID_ATTR_URI, ResourceAttributeDesignatorFactory.this.STRING_TYPE);
            if (ae != null) {
               Bag b = ae.evaluateToBag(context);
               if (b != null && b.size() > 0) {
                  return (StringAttribute)b.iterator().next();
               }
            }

            return null;
         }

         public StringAttribute getActionID() {
            return null;
         }

         public AttributeEvaluator getEvaluator(URI id, URI type, String issuer) {
            return this.aef.getEvaluatable(id, type, issuer);
         }

         public StringAttribute getResourceParent() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(ResourceAttributeDesignatorFactory.this.RESOURCEPARENT_ATTR_URI, ResourceAttributeDesignatorFactory.this.STRING_TYPE);
            if (ae != null) {
               Bag b = ae.evaluateToBag(context);
               if (b != null && b.size() > 0) {
                  return (StringAttribute)b.iterator().next();
               }
            }

            return null;
         }

         public Bag getAncestorResources() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(ResourceAttributeDesignatorFactory.this.RESOURCEANCESTOR_ATTR_URI, ResourceAttributeDesignatorFactory.this.STRING_TYPE);
            return ae != null ? ae.evaluateToBag(context) : null;
         }
      };
   }

   private Bag debugDesignator(EvaluationCtx context, URI id, Bag value) {
      if (context.isDebugEnabled()) {
         context.debug("Accessed Resource: Id=" + id + ", Value=" + (value != null ? value : "not present"));
      }

      return value;
   }

   public AttributeEvaluatableFactory getFactory() {
      return new AttributeEvaluatableFactory() {
         public AttributeEvaluator getEvaluatable(URI id, URI type) {
            return this.getEvaluatable(id, type, (String)null);
         }

         public AttributeEvaluator getEvaluatable(final URI id, final URI type, final String issuer) {
            if (issuer == null || ResourceAttributeDesignatorFactory.this.resourceIssuer.equals(issuer)) {
               if (ResourceAttributeDesignatorFactory.this.RESOURCEID_ATTR_URI.equals(id) && ResourceAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                  return new AttributeEvaluatorWrapper(type) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute result = (StringAttribute)ResourceAttributeDesignatorFactory.this.debugDesignator(context, id, ResourceAttributeDesignatorFactory.this.getResourceConverter(context).getResourceID());
                        return (Bag)(result != null ? result : ResourceAttributeDesignatorFactory.EMPTY_BAG);
                     }
                  };
               }

               if (ResourceAttributeDesignatorFactory.this.RESOURCEPARENT_ATTR_URI.equals(id) && ResourceAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                  return new AttributeEvaluatorWrapper(type) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute result = (StringAttribute)ResourceAttributeDesignatorFactory.this.debugDesignator(context, id, ResourceAttributeDesignatorFactory.this.getResourceConverter(context).getResourceParent());
                        return (Bag)(result != null ? result : ResourceAttributeDesignatorFactory.EMPTY_BAG);
                     }
                  };
               }

               if (ResourceAttributeDesignatorFactory.this.RESOURCEANCESTOR_ATTR_URI.equals(id) && ResourceAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                  return new AttributeEvaluatorWrapper(type) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Bag result = ResourceAttributeDesignatorFactory.this.debugDesignator(context, id, ResourceAttributeDesignatorFactory.this.getResourceConverter(context).getAncestorResources());
                        return result != null ? result : ResourceAttributeDesignatorFactory.EMPTY_BAG;
                     }
                  };
               }
            }

            return new AttributeEvaluatorWrapper(type) {
               public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                  AttributeEvaluator ae = ResourceAttributeDesignatorFactory.this.getResourceConverter(context).getEvaluator(id, type, issuer);
                  Bag result = ResourceAttributeDesignatorFactory.this.debugDesignator(context, id, ae != null ? ae.evaluateToBag(context) : null);
                  if (result != null) {
                     return result;
                  } else {
                     Type t = Type.findType(type.toString());
                     if (t == null) {
                        throw new IndeterminateEvaluationException("Unknown type: " + type);
                     } else {
                        return new GenericBag(t);
                     }
                  }
               }
            };
         }
      };
   }
}

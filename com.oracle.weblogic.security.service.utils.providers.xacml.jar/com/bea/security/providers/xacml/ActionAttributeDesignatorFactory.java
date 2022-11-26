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

public class ActionAttributeDesignatorFactory extends BaseAttributeDesignatorFactory {
   public static final String ACTIONID_ATTR = "urn:oasis:names:tc:xacml:1.0:action:action-id";
   public static final String IMPLIEDACTION_ATTR = "urn:oasis:names:tc:xacml:1.0:action:implied-action";
   public static final String ACTIONNAMESPACE_ATTR = "urn:oasis:names:tc:xacml:1.0:action:action-namespace";
   public static final String DIRECTION_ATTR = "urn:bea:xacml:2.0:action:direction";
   private static final Bag EMPTY_BAG = new StringAttributeBag(Collections.unmodifiableList(new ArrayList()));
   private final URI ACTIONID_ATTR_URI;
   private final URI IMPLIEDACTION_ATTR_URI;
   private final URI ACTIONNAMESPACE_ATTR_URI;
   private final URI DIRECTION_ATTR_URI;
   private final URI STRING_TYPE;
   private String actionIssuer;

   public ActionAttributeDesignatorFactory(String actionIssuer) throws URISyntaxException {
      try {
         this.ACTIONID_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");
         this.IMPLIEDACTION_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:action:implied-action");
         this.ACTIONNAMESPACE_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:action:action-namespace");
         this.DIRECTION_ATTR_URI = new URI("urn:bea:xacml:2.0:action:direction");
      } catch (java.net.URISyntaxException var3) {
         throw new URISyntaxException(var3);
      }

      this.STRING_TYPE = Type.STRING.getDataType();
      this.actionIssuer = actionIssuer;
   }

   public ResourceConverter getResourceConverter(final EvaluationCtx context) {
      return context instanceof ExtendedEvaluationCtx ? ((ExtendedEvaluationCtx)context).getResourceConverter() : new ResourceConverter() {
         private AttributeEvaluatableFactory aef = context.getActionAttributes();

         public StringAttribute getResourceID() {
            return null;
         }

         public StringAttribute getActionID() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(ActionAttributeDesignatorFactory.this.ACTIONID_ATTR_URI, ActionAttributeDesignatorFactory.this.STRING_TYPE);
            if (ae != null) {
               Bag b = ae.evaluateToBag(context);
               if (b != null && b.size() > 0) {
                  return (StringAttribute)b.iterator().next();
               }
            }

            return null;
         }

         public AttributeEvaluator getEvaluator(URI id, URI type, String issuer) {
            return this.aef.getEvaluatable(id, type, issuer);
         }

         public StringAttribute getResourceParent() {
            return null;
         }

         public Bag getAncestorResources() {
            return null;
         }
      };
   }

   public DirectionConverter getDirectionConverter(final EvaluationCtx context) {
      return context instanceof ExtendedEvaluationCtx ? ((ExtendedEvaluationCtx)context).getDirectionConverter() : new DirectionConverter() {
         private AttributeEvaluatableFactory aef = context.getActionAttributes();

         public StringAttribute getDirectionAttribute() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(ActionAttributeDesignatorFactory.this.DIRECTION_ATTR_URI, ActionAttributeDesignatorFactory.this.STRING_TYPE);
            if (ae != null) {
               Bag b = ae.evaluateToBag(context);
               if (b != null && b.size() > 0) {
                  return (StringAttribute)b.iterator().next();
               }
            }

            return null;
         }

         public AttributeEvaluator getEvaluator(URI id, URI type, String issuer) {
            return this.aef.getEvaluatable(id, type, issuer);
         }
      };
   }

   private Bag debugDesignator(EvaluationCtx context, URI id, Bag value) {
      if (context.isDebugEnabled()) {
         context.debug("Accessed Action: Id=" + id + ", Value=" + (value != null ? value : "not present"));
      }

      return value;
   }

   public AttributeEvaluatableFactory getFactory() {
      return new AttributeEvaluatableFactory() {
         public AttributeEvaluator getEvaluatable(URI id, URI type) {
            return this.getEvaluatable(id, type, (String)null);
         }

         public AttributeEvaluator getEvaluatable(final URI id, final URI type, final String issuer) {
            if (issuer == null || ActionAttributeDesignatorFactory.this.actionIssuer.equals(issuer)) {
               if (ActionAttributeDesignatorFactory.this.DIRECTION_ATTR_URI.equals(id) && ActionAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                  return new AttributeEvaluatorWrapper(type) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute result = (StringAttribute)ActionAttributeDesignatorFactory.this.debugDesignator(context, id, ActionAttributeDesignatorFactory.this.getDirectionConverter(context).getDirectionAttribute());
                        return (Bag)(result != null ? result : ActionAttributeDesignatorFactory.EMPTY_BAG);
                     }
                  };
               }

               if (ActionAttributeDesignatorFactory.this.ACTIONID_ATTR_URI.equals(id) && ActionAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                  return new AttributeEvaluatorWrapper(type) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        StringAttribute result = (StringAttribute)ActionAttributeDesignatorFactory.this.debugDesignator(context, id, ActionAttributeDesignatorFactory.this.getResourceConverter(context).getActionID());
                        return (Bag)(result != null ? result : ActionAttributeDesignatorFactory.EMPTY_BAG);
                     }
                  };
               }
            }

            return new AttributeEvaluatorWrapper(type) {
               public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                  AttributeEvaluator ae = ActionAttributeDesignatorFactory.this.getResourceConverter(context).getEvaluator(id, type, issuer);
                  if (ae == null) {
                     ae = ActionAttributeDesignatorFactory.this.getDirectionConverter(context).getEvaluator(id, type, issuer);
                  }

                  Bag result = ActionAttributeDesignatorFactory.this.debugDesignator(context, id, ae != null ? ae.evaluateToBag(context) : null);
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

package com.bea.security.providers.xacml;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.DoubleAttribute;
import com.bea.common.security.xacml.attr.GenericBag;
import com.bea.common.security.xacml.attr.JavaObjectAttribute;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.AttributeEvaluatorWrapper;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.AttributeEvaluatableFactory;
import com.bea.security.xacml.attr.BaseAttributeDesignatorFactory;
import weblogic.security.service.ContextHandler;

public class EnvironmentAttributeDesignatorFactory extends BaseAttributeDesignatorFactory {
   public static final String TIME_ATTR = "urn:oasis:names:tc:xacml:1.0:environment:current-time";
   public static final String DATE_ATTR = "urn:oasis:names:tc:xacml:1.0:environment:current-date";
   public static final String DATETIME_ATTR = "urn:oasis:names:tc:xacml:1.0:environment:current-dateTime";
   public static final String ADMIN_IDD_ATTR = "urn:bea:xacml:2.0:environment:context:com.oracle.contextelement.security.AdminIdentityDomain";
   public static final String RESOURCE_IDD_ATTR = "urn:bea:xacml:2.0:environment:context:com.oracle.contextelement.security.ResourceIdentityDomain";
   public static final String CONTEXT_ATTR = "urn:bea:xacml:2.0:environment:context:";
   private final URI TIME_ATTR_URI;
   private final URI DATE_ATTR_URI;
   private final URI DATETIME_ATTR_URI;
   private final URI ADMIN_IDD_ATTR_URI;
   private final URI RESOURCE_IDD_ATTR_URI;
   private final URI TIME_TYPE;
   private final URI DATE_TYPE;
   private final URI DATETIME_TYPE;
   private final URI STRING_TYPE;
   private final URI DOUBLE_TYPE;
   private final URI OBJECT_TYPE;
   private String environmentIssuer;

   public EnvironmentAttributeDesignatorFactory(String environmentIssuer) throws URISyntaxException {
      try {
         this.TIME_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:environment:current-time");
         this.DATE_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:environment:current-date");
         this.DATETIME_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime");
         this.ADMIN_IDD_ATTR_URI = new URI("urn:bea:xacml:2.0:environment:context:com.oracle.contextelement.security.AdminIdentityDomain");
         this.RESOURCE_IDD_ATTR_URI = new URI("urn:bea:xacml:2.0:environment:context:com.oracle.contextelement.security.ResourceIdentityDomain");
      } catch (java.net.URISyntaxException var3) {
         throw new URISyntaxException(var3);
      }

      this.TIME_TYPE = Type.TIME.getDataType();
      this.DATE_TYPE = Type.DATE.getDataType();
      this.DATETIME_TYPE = Type.DATE_TIME.getDataType();
      this.STRING_TYPE = Type.STRING.getDataType();
      this.DOUBLE_TYPE = Type.DOUBLE.getDataType();
      this.OBJECT_TYPE = Type.OBJECT.getDataType();
      this.environmentIssuer = environmentIssuer;
   }

   public ContextConverter getContextConverter(final EvaluationCtx context) {
      return context instanceof ExtendedEvaluationCtx ? ((ExtendedEvaluationCtx)context).getContextConverter() : new ContextConverter() {
         private AttributeEvaluatableFactory aef = context.getEnvironmentAttributes();

         public JavaObjectAttribute getContextValue(String key) throws IndeterminateEvaluationException {
            try {
               AttributeEvaluator ae = this.aef.getEvaluatable(new URI("urn:bea:xacml:2.0:environment:context:" + key), EnvironmentAttributeDesignatorFactory.this.OBJECT_TYPE);
               if (ae != null) {
                  Bag b = ae.evaluateToBag(context);
                  if (b != null && b.size() > 0) {
                     return (JavaObjectAttribute)b.iterator().next();
                  }
               }

               return null;
            } catch (java.net.URISyntaxException var4) {
               throw new IndeterminateEvaluationException(var4);
            }
         }

         public AttributeEvaluator getEvaluator(URI id, URI type, String issuer) {
            return this.aef.getEvaluatable(id, type, issuer);
         }
      };
   }

   private Bag debugDesignator(EvaluationCtx context, URI id, Bag value) {
      if (context.isDebugEnabled()) {
         context.debug("Accessed Environment: Id=" + id + ", Value=" + (value != null ? value : "not present"));
      }

      return value;
   }

   public AttributeEvaluatableFactory getFactory() {
      return new AttributeEvaluatableFactory() {
         public AttributeEvaluator getEvaluatable(URI id, URI type) {
            return this.getEvaluatable(id, type, (String)null);
         }

         public AttributeEvaluator getEvaluatable(final URI id, final URI type, final String issuer) {
            if (issuer == null || EnvironmentAttributeDesignatorFactory.this.environmentIssuer.equals(issuer)) {
               if (EnvironmentAttributeDesignatorFactory.this.TIME_ATTR_URI.equals(id) && EnvironmentAttributeDesignatorFactory.this.TIME_TYPE.equals(type)) {
                  return new AttributeEvaluatorWrapper(type) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        return EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, context.getCurrentTime());
                     }
                  };
               }

               if (EnvironmentAttributeDesignatorFactory.this.DATE_ATTR_URI.equals(id) && EnvironmentAttributeDesignatorFactory.this.DATE_TYPE.equals(type)) {
                  return new AttributeEvaluatorWrapper(type) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        return EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, context.getCurrentDate());
                     }
                  };
               }

               if (EnvironmentAttributeDesignatorFactory.this.DATETIME_ATTR_URI.equals(id) && EnvironmentAttributeDesignatorFactory.this.DATETIME_TYPE.equals(type)) {
                  return new AttributeEvaluatorWrapper(type) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        return EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, context.getCurrentDateTime());
                     }
                  };
               }

               final String sid;
               if ((EnvironmentAttributeDesignatorFactory.this.ADMIN_IDD_ATTR_URI.equals(id) || EnvironmentAttributeDesignatorFactory.this.RESOURCE_IDD_ATTR_URI.equals(id)) && EnvironmentAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                  sid = id.toString().substring("urn:bea:xacml:2.0:environment:context:".length());
                  return new AttributeEvaluatorWrapper(type) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Object o = null;
                        ContextHandler handler = null;
                        if (context instanceof ExtendedEvaluationCtx) {
                           handler = ((ExtendedEvaluationCtx)context).getContextHandler();
                        }

                        if (handler != null) {
                           o = handler.getValue(sid);
                        } else {
                           JavaObjectAttribute joa = EnvironmentAttributeDesignatorFactory.this.getContextConverter(context).getContextValue(sid);
                           if (joa != null) {
                              o = joa.getValue();
                           }
                        }

                        if (o != null) {
                           return EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, new StringAttribute(o.toString()));
                        } else {
                           EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, (Bag)null);
                           Type t = Type.findType(type.toString());
                           if (t == null) {
                              throw new IndeterminateEvaluationException("Unknown type: " + type);
                           } else {
                              return new GenericBag(t, new StringAttribute(""));
                           }
                        }
                     }
                  };
               }

               sid = id.toString();
               if (sid.startsWith("urn:bea:xacml:2.0:environment:context:")) {
                  final String key = sid.substring("urn:bea:xacml:2.0:environment:context:".length());
                  if (EnvironmentAttributeDesignatorFactory.this.OBJECT_TYPE.equals(type)) {
                     return new AttributeEvaluatorWrapper(type) {
                        public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                           Bag result = EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, EnvironmentAttributeDesignatorFactory.this.getContextConverter(context).getContextValue(key));
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

                  if (EnvironmentAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                     return new AttributeEvaluatorWrapper(type) {
                        public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                           JavaObjectAttribute joa = EnvironmentAttributeDesignatorFactory.this.getContextConverter(context).getContextValue(key);
                           if (joa != null) {
                              Object o = joa.getValue();
                              if (o != null) {
                                 return EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, new StringAttribute(o.toString()));
                              }
                           }

                           EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, (Bag)null);
                           Type t = Type.findType(type.toString());
                           if (t == null) {
                              throw new IndeterminateEvaluationException("Unknown type: " + type);
                           } else {
                              return new GenericBag(t);
                           }
                        }
                     };
                  }

                  if (EnvironmentAttributeDesignatorFactory.this.DOUBLE_TYPE.equals(type)) {
                     return new AttributeEvaluatorWrapper(type) {
                        public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                           JavaObjectAttribute joa = EnvironmentAttributeDesignatorFactory.this.getContextConverter(context).getContextValue(key);
                           if (joa != null) {
                              Object o = joa.getValue();
                              if (o != null) {
                                 if (o instanceof Double) {
                                    return EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, new DoubleAttribute((Double)o));
                                 }

                                 if (o instanceof Number) {
                                    return EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, new DoubleAttribute(((Number)o).doubleValue()));
                                 }

                                 try {
                                    return EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, new DoubleAttribute(o.toString()));
                                 } catch (InvalidAttributeException var5) {
                                    throw new IndeterminateEvaluationException(var5);
                                 }
                              }
                           }

                           EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, (Bag)null);
                           Type t = Type.findType(type.toString());
                           if (t == null) {
                              throw new IndeterminateEvaluationException("Unknown type: " + type);
                           } else {
                              return new GenericBag(t);
                           }
                        }
                     };
                  }
               }
            }

            return new AttributeEvaluatorWrapper(type) {
               public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                  AttributeEvaluator ae = EnvironmentAttributeDesignatorFactory.this.getContextConverter(context).getEvaluator(id, type, issuer);
                  Bag result = EnvironmentAttributeDesignatorFactory.this.debugDesignator(context, id, ae != null ? ae.evaluateToBag(context) : null);
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

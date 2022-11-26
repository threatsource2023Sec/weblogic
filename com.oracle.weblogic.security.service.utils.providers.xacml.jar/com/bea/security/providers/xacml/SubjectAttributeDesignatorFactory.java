package com.bea.security.providers.xacml;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AnyURIAttribute;
import com.bea.common.security.xacml.attr.AnyURIAttributeBag;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.GenericBag;
import com.bea.common.security.xacml.attr.JavaObjectAttribute;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.StringAttributeBag;
import com.bea.common.security.xacml.policy.SubjectAttributeDesignator;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.AttributeEvaluatorWrapper;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.MissingAttributeException;
import com.bea.security.xacml.attr.AttributeEvaluatableFactory;
import com.bea.security.xacml.attr.SubjectAttributeEvaluatableFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.security.auth.Subject;

public class SubjectAttributeDesignatorFactory implements com.bea.security.xacml.attr.designator.SubjectAttributeDesignatorFactory {
   private static final StringAttribute EVERYONE = new StringAttribute("everyone");
   public static final String SUBJECTCATEGORY_ACCESSSSUBJECT = "urn:oasis:names:tc:xacml:1.0:subject-category:access-subject";
   public static final String SUBJECTID_ATTR = "urn:oasis:names:tc:xacml:1.0:subject:subject-id";
   public static final String IDDNAME_ATTR = "urn:oasis:names:tc:xacml:1.0:subject:iddname";
   public static final String AUTHENTICATIONTIME_ATTR = "urn:oasis:names:tc:xacml:1.0:subject:authentication-time";
   public static final String AUTHENTICATIONMETHOD_ATTR = "urn:oasis:names:tc:xacml:1.0:subject:authn-locality:authentication-method";
   public static final String REQUESTTIME_ATTR = "urn:oasis:names:tc:xacml:1.0:subject:request-time";
   public static final String SESSIONSTARTTIME_ATTR = "urn:oasis:names:tc:xacml:1.0:subject:session-start-time";
   public static final String IPADDRESS_ATTR = "urn:oasis:names:tc:xacml:1.0:subject:authn-locality:ip-address";
   public static final String DNSNAME_ATTR = "urn:oasis:names:tc:xacml:1.0:subject:authn-locality:dns-name";
   public static final String GROUP_ATTR = "urn:oasis:names:tc:xacml:2.0:subject:group";
   public static final String ROLE_ATTR = "urn:oasis:names:tc:xacml:2.0:subject:role";
   public static final String CONTEXT_ATTR = "urn:bea:xacml:2.0:subject:context:";
   public static final String IDCSAPPROLE_ATTR = "urn:oasis:names:tc:xacml:1.0:subject:application-roles";
   private static final Bag EMPTY_BAG = new StringAttributeBag(Collections.unmodifiableList(new ArrayList()));
   private static final Bag EMPTY_BAG_URI = new AnyURIAttributeBag(Collections.unmodifiableList(new ArrayList()));
   private final URI SUBJECTCATEGORY_ACCESSSUBJECT_URI;
   private final URI SUBJECTID_ATTR_URI;
   private final URI IDDNAME_ATTR_URI;
   private final URI AUTHENTICATIONTIME_ATTR_URI;
   private final URI AUTHENTICATIONMETHOD_ATTR_URI;
   private final URI REQUESTTIME_ATTR_URI;
   private final URI SESSIONSTARTTIME_ATTR_URI;
   private final URI IPADDRESS_ATTR_URI;
   private final URI DNSNAME_ATTR_URI;
   private final URI GROUP_ATTR_URI;
   private final URI ROLE_ATTR_URI;
   private final URI IDCSAPPROLE_ATTR_URI;
   private final URI STRING_TYPE;
   private final URI ANYURI_TYPE;
   private final URI OBJECT_TYPE;
   private String subjectIssuer;
   private EvaluationCtxFactory fac;

   public SubjectAttributeDesignatorFactory(String subjectIssuer, EvaluationCtxFactory fac) throws URISyntaxException {
      try {
         this.SUBJECTCATEGORY_ACCESSSUBJECT_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject-category:access-subject");
         this.SUBJECTID_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject:subject-id");
         this.IDDNAME_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname");
         this.AUTHENTICATIONTIME_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject:authentication-time");
         this.AUTHENTICATIONMETHOD_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject:authn-locality:authentication-method");
         this.REQUESTTIME_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject:request-time");
         this.SESSIONSTARTTIME_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject:session-start-time");
         this.IPADDRESS_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject:authn-locality:ip-address");
         this.DNSNAME_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject:authn-locality:dns-name");
         this.GROUP_ATTR_URI = new URI("urn:oasis:names:tc:xacml:2.0:subject:group");
         this.ROLE_ATTR_URI = new URI("urn:oasis:names:tc:xacml:2.0:subject:role");
         this.IDCSAPPROLE_ATTR_URI = new URI("urn:oasis:names:tc:xacml:1.0:subject:application-roles");
      } catch (java.net.URISyntaxException var4) {
         throw new URISyntaxException(var4);
      }

      this.STRING_TYPE = Type.STRING.getDataType();
      this.ANYURI_TYPE = Type.ANY_URI.getDataType();
      this.OBJECT_TYPE = Type.OBJECT.getDataType();
      this.subjectIssuer = subjectIssuer;
      this.fac = fac;
   }

   public SubjectConverter getSubjectConverter(final EvaluationCtx context) {
      return context instanceof ExtendedEvaluationCtx ? ((ExtendedEvaluationCtx)context).getSubjectConverter() : new SubjectConverter() {
         private SubjectAttributeEvaluatableFactory aef = context.getSubjectAttributes();

         public boolean isUserAnonymous() {
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.SUBJECTID_ATTR_URI, SubjectAttributeDesignatorFactory.this.STRING_TYPE);
            return ae == null;
         }

         public boolean isUser(StringAttribute userId) throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.SUBJECTID_ATTR_URI, SubjectAttributeDesignatorFactory.this.STRING_TYPE);
            return ae != null ? ae.evaluateToBag(context).contains(userId) : false;
         }

         public boolean isMemberOf(StringAttribute groupId) throws IndeterminateEvaluationException {
            if (SubjectAttributeDesignatorFactory.EVERYONE.equals(groupId)) {
               return true;
            } else {
               AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.GROUP_ATTR_URI, SubjectAttributeDesignatorFactory.this.STRING_TYPE);
               return ae != null ? ae.evaluateToBag(context).contains(groupId) : false;
            }
         }

         public boolean isIddName(AnyURIAttribute iddName) throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.IDDNAME_ATTR_URI, SubjectAttributeDesignatorFactory.this.ANYURI_TYPE);
            return ae != null ? ae.evaluateToBag(context).contains(iddName) : false;
         }

         public Bag getUserNameAttributes() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.SUBJECTID_ATTR_URI, SubjectAttributeDesignatorFactory.this.STRING_TYPE);
            return ae != null ? ae.evaluateToBag(context) : null;
         }

         public Bag getGroupAttributes() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.GROUP_ATTR_URI, SubjectAttributeDesignatorFactory.this.STRING_TYPE);
            return (Bag)(ae != null ? ae.evaluateToBag(context) : SubjectAttributeDesignatorFactory.EVERYONE);
         }

         public Bag getIddNameAttributes() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.IDDNAME_ATTR_URI, SubjectAttributeDesignatorFactory.this.ANYURI_TYPE);
            return ae != null ? ae.evaluateToBag(context) : null;
         }

         public boolean isIDCSAppRole(AnyURIAttribute appRole) throws IndeterminateEvaluationException {
            boolean result = false;
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.IDCSAPPROLE_ATTR_URI, SubjectAttributeDesignatorFactory.this.ANYURI_TYPE);
            if (ae != null) {
               result = ae.evaluateToBag(context).contains(appRole);
            }

            return result;
         }

         public Bag getIDCSAppRoleAttributes() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.IDCSAPPROLE_ATTR_URI, SubjectAttributeDesignatorFactory.this.ANYURI_TYPE);
            Bag bag = null;
            if (ae != null) {
               bag = ae.evaluateToBag(context);
            }

            return bag;
         }

         public AttributeEvaluator getEvaluator(URI id, URI type, String issuer, URI category) {
            return this.aef.getEvaluatable(id, type, issuer, category);
         }
      };
   }

   public SubjectConverter getSubjectConverter(EvaluationCtx context, Subject s2) {
      return this.fac.getSubjectConverter(s2);
   }

   public RoleConverter getRoleConverter(final EvaluationCtx context) {
      return context instanceof ExtendedEvaluationCtx ? ((ExtendedEvaluationCtx)context).getRoleConverter() : new RoleConverter() {
         private AttributeEvaluatableFactory aef = context.getSubjectAttributes();

         public boolean isInRole(StringAttribute roleName) throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.ROLE_ATTR_URI, SubjectAttributeDesignatorFactory.this.STRING_TYPE);
            return ae != null ? ae.evaluateToBag(context).contains(roleName) : false;
         }

         public Bag getRoleAttributes() throws IndeterminateEvaluationException {
            AttributeEvaluator ae = this.aef.getEvaluatable(SubjectAttributeDesignatorFactory.this.ROLE_ATTR_URI, SubjectAttributeDesignatorFactory.this.STRING_TYPE);
            return ae != null ? ae.evaluateToBag(context) : null;
         }

         public AttributeEvaluator getEvaluator(URI id, URI type, String issuer, URI category) {
            return this.aef.getEvaluatable(id, type, issuer);
         }
      };
   }

   public ContextConverter getContextConverter(final EvaluationCtx context) {
      return context instanceof ExtendedEvaluationCtx ? ((ExtendedEvaluationCtx)context).getContextConverter() : new ContextConverter() {
         private AttributeEvaluatableFactory aef = context.getEnvironmentAttributes();

         public JavaObjectAttribute getContextValue(String key) throws IndeterminateEvaluationException {
            try {
               AttributeEvaluator ae = this.aef.getEvaluatable(new URI("urn:bea:xacml:2.0:subject:context:" + key), SubjectAttributeDesignatorFactory.this.OBJECT_TYPE);
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

   private Bag debugDesignator(EvaluationCtx context, URI id, URI sc, Bag value) {
      if (context.isDebugEnabled()) {
         StringBuffer sb = new StringBuffer();
         sb.append("Accessed Subject: Id=");
         sb.append(id);
         if (!this.SUBJECTCATEGORY_ACCESSSUBJECT_URI.equals(sc)) {
            sb.append(", SC=");
            sb.append(sc);
         }

         sb.append(", Value=");
         sb.append(value != null ? value : "not present");
         context.debug(sb.toString());
      }

      return value;
   }

   public SubjectAttributeEvaluatableFactory getFactory() {
      return new SubjectAttributeEvaluatableFactory() {
         public AttributeEvaluator getEvaluatable(URI id, URI type) {
            return this.getEvaluatable(id, type, (String)null, (URI)null);
         }

         public AttributeEvaluator getEvaluatable(URI id, URI type, String issuer) {
            return this.getEvaluatable(id, type, issuer, (URI)null);
         }

         public AttributeEvaluator getEvaluatable(URI id, URI type, URI category) {
            return this.getEvaluatable(id, type, (String)null, category);
         }

         public AttributeEvaluator getEvaluatable(final URI id, final URI type, final String issuer, final URI category) {
            if (issuer == null || SubjectAttributeDesignatorFactory.this.subjectIssuer.equals(issuer)) {
               if (category != null && !SubjectAttributeDesignatorFactory.this.SUBJECTCATEGORY_ACCESSSUBJECT_URI.equals(category)) {
                  String categoryId = category.toString();
                  if (categoryId.startsWith("urn:bea:xacml:2.0:subject:context:")) {
                     final String key = categoryId.substring("urn:bea:xacml:2.0:subject:context:".length());
                     if (SubjectAttributeDesignatorFactory.this.SUBJECTID_ATTR_URI.equals(id) && SubjectAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                        return new AttributeEvaluatorWrapper(type) {
                           public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                              JavaObjectAttribute joa = SubjectAttributeDesignatorFactory.this.getContextConverter(context).getContextValue(key);
                              if (joa != null) {
                                 Object o = joa.getValue();
                                 if (o instanceof Subject) {
                                    Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, SubjectAttributeDesignatorFactory.this.getSubjectConverter(context, (Subject)o).getUserNameAttributes());
                                    return result != null ? result : SubjectAttributeDesignatorFactory.EMPTY_BAG;
                                 }
                              }

                              SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, (Bag)null);
                              return SubjectAttributeDesignatorFactory.EMPTY_BAG;
                           }
                        };
                     }

                     if (SubjectAttributeDesignatorFactory.this.GROUP_ATTR_URI.equals(id) && SubjectAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                        return new AttributeEvaluatorWrapper(type) {
                           public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                              JavaObjectAttribute joa = SubjectAttributeDesignatorFactory.this.getContextConverter(context).getContextValue(key);
                              if (joa != null) {
                                 Object o = joa.getValue();
                                 if (o instanceof Subject) {
                                    Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, SubjectAttributeDesignatorFactory.this.getSubjectConverter(context, (Subject)o).getGroupAttributes());
                                    return result != null ? result : SubjectAttributeDesignatorFactory.EMPTY_BAG;
                                 }
                              }

                              SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, (Bag)null);
                              return SubjectAttributeDesignatorFactory.EMPTY_BAG;
                           }
                        };
                     }

                     if (SubjectAttributeDesignatorFactory.this.IDDNAME_ATTR_URI.equals(id) && SubjectAttributeDesignatorFactory.this.ANYURI_TYPE.equals(type)) {
                        return new AttributeEvaluatorWrapper(type) {
                           public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                              JavaObjectAttribute joa = SubjectAttributeDesignatorFactory.this.getContextConverter(context).getContextValue(key);
                              if (joa != null) {
                                 Object o = joa.getValue();
                                 if (o instanceof Subject) {
                                    Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, SubjectAttributeDesignatorFactory.this.getSubjectConverter(context, (Subject)o).getIddNameAttributes());
                                    return result != null ? result : SubjectAttributeDesignatorFactory.EMPTY_BAG_URI;
                                 }
                              }

                              SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, (Bag)null);
                              return SubjectAttributeDesignatorFactory.EMPTY_BAG_URI;
                           }
                        };
                     }

                     if (SubjectAttributeDesignatorFactory.this.IDCSAPPROLE_ATTR_URI.equals(id) && SubjectAttributeDesignatorFactory.this.ANYURI_TYPE.equals(type)) {
                        return new AttributeEvaluatorWrapper(type) {
                           public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                              JavaObjectAttribute joa = SubjectAttributeDesignatorFactory.this.getContextConverter(context).getContextValue(key);
                              if (joa != null) {
                                 Object o = joa.getValue();
                                 if (o instanceof Subject) {
                                    Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, SubjectAttributeDesignatorFactory.this.getSubjectConverter(context, (Subject)o).getIDCSAppRoleAttributes());
                                    return result != null ? result : SubjectAttributeDesignatorFactory.EMPTY_BAG;
                                 }
                              }

                              SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, (Bag)null);
                              return SubjectAttributeDesignatorFactory.EMPTY_BAG;
                           }
                        };
                     }
                  }
               } else {
                  if (SubjectAttributeDesignatorFactory.this.SUBJECTID_ATTR_URI.equals(id) && SubjectAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                     return new AttributeEvaluatorWrapper(type) {
                        public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                           Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, SubjectAttributeDesignatorFactory.this.getSubjectConverter(context).getUserNameAttributes());
                           return result != null ? result : SubjectAttributeDesignatorFactory.EMPTY_BAG;
                        }
                     };
                  }

                  if (SubjectAttributeDesignatorFactory.this.GROUP_ATTR_URI.equals(id) && SubjectAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                     return new AttributeEvaluatorWrapper(type) {
                        public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                           Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, SubjectAttributeDesignatorFactory.this.getSubjectConverter(context).getGroupAttributes());
                           return result != null ? result : SubjectAttributeDesignatorFactory.EMPTY_BAG;
                        }
                     };
                  }

                  if (SubjectAttributeDesignatorFactory.this.IDDNAME_ATTR_URI.equals(id) && SubjectAttributeDesignatorFactory.this.ANYURI_TYPE.equals(type)) {
                     return new AttributeEvaluatorWrapper(type) {
                        public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                           Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, SubjectAttributeDesignatorFactory.this.getSubjectConverter(context).getIddNameAttributes());
                           return result != null ? result : SubjectAttributeDesignatorFactory.EMPTY_BAG_URI;
                        }
                     };
                  }

                  if (SubjectAttributeDesignatorFactory.this.ROLE_ATTR_URI.equals(id) && SubjectAttributeDesignatorFactory.this.STRING_TYPE.equals(type)) {
                     return new AttributeEvaluatorWrapper(type) {
                        public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                           Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, SubjectAttributeDesignatorFactory.this.getRoleConverter(context).getRoleAttributes());
                           return result != null ? result : SubjectAttributeDesignatorFactory.EMPTY_BAG;
                        }
                     };
                  }

                  if (SubjectAttributeDesignatorFactory.this.IDCSAPPROLE_ATTR_URI.equals(id) && SubjectAttributeDesignatorFactory.this.ANYURI_TYPE.equals(type)) {
                     return new AttributeEvaluatorWrapper(type) {
                        public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                           Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, SubjectAttributeDesignatorFactory.this.getSubjectConverter(context).getIDCSAppRoleAttributes());
                           return result != null ? result : SubjectAttributeDesignatorFactory.EMPTY_BAG;
                        }
                     };
                  }
               }
            }

            return new AttributeEvaluatorWrapper(type) {
               public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                  AttributeEvaluator ae = SubjectAttributeDesignatorFactory.this.getSubjectConverter(context).getEvaluator(id, type, issuer, category);
                  if (ae == null) {
                     ae = SubjectAttributeDesignatorFactory.this.getRoleConverter(context).getEvaluator(id, type, issuer, category);
                  }

                  Bag result = SubjectAttributeDesignatorFactory.this.debugDesignator(context, id, category, ae != null ? ae.evaluateToBag(context) : null);
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

   public AttributeEvaluator createDesignator(SubjectAttributeDesignator attribute, Configuration config, Iterator otherFactories) throws URISyntaxException {
      SubjectAttributeEvaluatableFactory fac = this.getFactory();
      final URI id = attribute.getAttributeId();
      final URI dataType = attribute.getDataType();
      String issuer = attribute.getIssuer();
      URI category = attribute.getSubjectCategory();
      boolean isMustBePresent = attribute.isMustBePresent();
      if (isMustBePresent) {
         final AttributeEvaluator proxy = this.generateEvaluator(fac, id, dataType, issuer, category);
         return new AttributeEvaluatorWrapper(proxy.getType()) {
            public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
               Bag bag = proxy.evaluateToBag(context);
               if (bag != null && !bag.isEmpty()) {
                  return bag;
               } else {
                  throw new MissingAttributeException(id, dataType);
               }
            }

            public Type getType() throws URISyntaxException {
               return proxy.getType();
            }
         };
      } else {
         return this.generateEvaluator(fac, id, dataType, issuer, category);
      }
   }

   private AttributeEvaluator generateEvaluator(SubjectAttributeEvaluatableFactory fac, URI id, URI dataType, String issuer, URI category) {
      return issuer != null ? fac.getEvaluatable(id, dataType, issuer, category) : fac.getEvaluatable(id, dataType, category);
   }
}

package com.bea.security.providers.xacml.entitlement;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AnyURIAttribute;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.DateTimeAttribute;
import com.bea.common.security.xacml.attr.DayTimeDurationAttribute;
import com.bea.common.security.xacml.attr.DoubleAttribute;
import com.bea.common.security.xacml.attr.IntegerAttribute;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.policy.Action;
import com.bea.common.security.xacml.policy.Actions;
import com.bea.common.security.xacml.policy.Apply;
import com.bea.common.security.xacml.policy.AttributeValue;
import com.bea.common.security.xacml.policy.Condition;
import com.bea.common.security.xacml.policy.EnvironmentAttributeDesignator;
import com.bea.common.security.xacml.policy.Obligations;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicyDefaults;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.Resources;
import com.bea.common.security.xacml.policy.Rule;
import com.bea.common.security.xacml.policy.SubjectAttributeDesignator;
import com.bea.common.security.xacml.policy.Target;
import com.bea.common.security.xacml.policy.VariableDefinition;
import com.bea.common.security.xacml.policy.VariableReference;
import com.bea.security.providers.xacml.entitlement.p13n.P13NExpressionConverter;
import com.bea.security.providers.xacml.entitlement.parser.BinaryOp;
import com.bea.security.providers.xacml.entitlement.parser.Complement;
import com.bea.security.providers.xacml.entitlement.parser.Difference;
import com.bea.security.providers.xacml.entitlement.parser.Expression;
import com.bea.security.providers.xacml.entitlement.parser.Groups;
import com.bea.security.providers.xacml.entitlement.parser.Intersect;
import com.bea.security.providers.xacml.entitlement.parser.Parser;
import com.bea.security.providers.xacml.entitlement.parser.Predicate;
import com.bea.security.providers.xacml.entitlement.parser.Roles;
import com.bea.security.providers.xacml.entitlement.parser.UnaryOp;
import com.bea.security.providers.xacml.entitlement.parser.Union;
import com.bea.security.providers.xacml.entitlement.parser.Users;
import com.bea.security.xacml.Logger;
import com.bea.security.xacml.cache.resource.ResourceMatchUtil;
import com.bea.security.xacml.cache.resource.ResourcePolicyIdUtil;
import com.bea.security.xacml.cache.role.RoleMatchUtil;
import com.bea.security.xacml.entitlement.ExpressionConverter;
import com.bea.security.xacml.entitlement.SimplificationException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import weblogic.security.spi.Resource;

public class EntitlementConverter {
   private static final String ENTITLEMENT_COMBINER = "urn:bea:xacml:2.0:entitlement:policy-combining-algorithm:most-specific";
   private static final String RULES_PACKAGE = "weblogic.entitlement.rules";
   private static final String AFTER = "weblogic.entitlement.rules.After";
   private static final String AFTER_DAY_OF_MONTH = "weblogic.entitlement.rules.AfterDayOfMonth";
   private static final String BEFORE = "weblogic.entitlement.rules.Before";
   private static final String BEFORE_DAY_OF_MONTH = "weblogic.entitlement.rules.BeforeDayOfMonth";
   private static final String CONTEXT_ELEMENT_IS_SET = "weblogic.entitlement.rules.ContextElementIsSet";
   private static final String CONTEXT_ELEMENT_NUMBER_EQUALS = "weblogic.entitlement.rules.ContextElementNumberEquals";
   private static final String CONTEXT_ELEMENT_NUMBER_GREATER = "weblogic.entitlement.rules.ContextElementNumberGreater";
   private static final String CONTEXT_ELEMENT_NUMBER_LESS = "weblogic.entitlement.rules.ContextElementNumberLess";
   private static final String CONTEXT_ELEMENT_STRING_EQUALS = "weblogic.entitlement.rules.ContextElementStringEquals";
   private static final String EXCLUDED_POLICY = "weblogic.entitlement.rules.ExcludedPolicy";
   private static final String HTTP_REQUEST_ATTR_IS_SET = "weblogic.entitlement.rules.HttpRequestAttrIsSet";
   private static final String HTTP_REQUEST_ATTR_NUMBER_EQUALS = "weblogic.entitlement.rules.HttpRequestAttrNumberEquals";
   private static final String HTTP_REQUEST_ATTR_NUMBER_GREATER = "weblogic.entitlement.rules.HttpRequestAttrNumberGreater";
   private static final String HTTP_REQUEST_ATTR_NUMBER_LESS = "weblogic.entitlement.rules.HttpRequestAttrNumberLess";
   private static final String HTTP_REQUEST_ATTR_STRING_EQUALS = "weblogic.entitlement.rules.HttpRequestAttrStringEquals";
   private static final String IN_DEVELOPMENT_MODE = "weblogic.entitlement.rules.InDevelopmentMode";
   private static final String IN_SECURE_MODE = "weblogic.entitlement.rules.InSecureMode";
   private static final String IS_DAY_OF_MONTH = "weblogic.entitlement.rules.IsDayOfMonth";
   private static final String IS_DAY_OF_WEEK = "weblogic.entitlement.rules.IsDayOfWeek";
   private static final String IDD_USER = "weblogic.entitlement.rules.IDDUser";
   private static final String IDD_GROUP = "weblogic.entitlement.rules.IDDGroup";
   private static final String OWNER_IDD_USER = "weblogic.entitlement.rules.OwnerIDDUser";
   private static final String OWNER_IDD_GROUP = "weblogic.entitlement.rules.OwnerIDDGroup";
   private static final String ADMIN_IDD_USER = "weblogic.entitlement.rules.AdminIDDUser";
   private static final String ADMIN_IDD_GROUP = "weblogic.entitlement.rules.AdminIDDGroup";
   private static final String IDCS_APPROLE = "weblogic.entitlement.rules.IDCSAppRoleName";
   private static final String ADMINISTRATIVE_GROUP = "weblogic.entitlement.rules.AdministrativeGroup";
   private static final String SCOPE = "weblogic.entitlement.rules.Scope";
   private static final String SIGNATURE = "weblogic.entitlement.rules.SignaturePredicate";
   private static final String TIME = "weblogic.entitlement.rules.TimePredicate";
   private static final String UNCHECKED_POLICY = "weblogic.entitlement.rules.UncheckedPolicy";
   private static final String WLP_EXPRESSION = "com.bea.p13n.entitlements.rules.internal.ExpressionPredicate";
   private static final long NANOS_PER_MILLI = 1000000L;
   private static final int SECONDS_PER_MINUTE = 60;
   private static final int MINUTES_PER_HOUR = 60;
   private static final String SUNDAY = "Sunday";
   private static final String MONDAY = "Monday";
   private static final String TUESDAY = "Tuesday";
   private static final String WEDNESDAY = "Wednesday";
   private static final String THURSDAY = "Thursday";
   private static final String FRIDAY = "Friday";
   private static final String SATURDAY = "Saturday";
   private static final String IDDNAME_PREFIX = Boolean.getBoolean("weblogic.security.xacml.PrevIDDNamePrefix") ? "iddname://idd_" : "iddname://idd.";
   private static final String IDDUSER_SCOPE = "/user/";
   private static final String IDDGROUP_SCOPE = "/group/";
   private static final String SCOPE_SCOPE = "/scope/";
   private static final String IDCS_APPROLE_PREFIX = "idcsrole://";
   private static final String IDCS_APP_SCOPE = "/app/";
   private RoleMatchUtil rmu;
   private P13NExpressionConverter p13nConverter;
   private LoggerSpi log;
   private Collection predicates = new HashSet();
   private final Map days = new HashMap();

   public EntitlementConverter(Logger log) throws URISyntaxException {
      this.log = new XACMLLoggerWrapper(log);
      this.rmu = new RoleMatchUtil();
      this.predicates.add("weblogic.entitlement.rules.After");
      this.predicates.add("weblogic.entitlement.rules.AfterDayOfMonth");
      this.predicates.add("weblogic.entitlement.rules.Before");
      this.predicates.add("weblogic.entitlement.rules.BeforeDayOfMonth");
      this.predicates.add("weblogic.entitlement.rules.ContextElementIsSet");
      this.predicates.add("weblogic.entitlement.rules.ContextElementNumberEquals");
      this.predicates.add("weblogic.entitlement.rules.ContextElementNumberGreater");
      this.predicates.add("weblogic.entitlement.rules.ContextElementNumberLess");
      this.predicates.add("weblogic.entitlement.rules.ContextElementStringEquals");
      this.predicates.add("weblogic.entitlement.rules.ExcludedPolicy");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrIsSet");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrNumberEquals");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrNumberGreater");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrNumberLess");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrStringEquals");
      this.predicates.add("weblogic.entitlement.rules.InDevelopmentMode");
      this.predicates.add("weblogic.entitlement.rules.InSecureMode");
      this.predicates.add("weblogic.entitlement.rules.IsDayOfMonth");
      this.predicates.add("weblogic.entitlement.rules.IsDayOfWeek");
      this.predicates.add("weblogic.entitlement.rules.IDDUser");
      this.predicates.add("weblogic.entitlement.rules.IDDGroup");
      this.predicates.add("weblogic.entitlement.rules.OwnerIDDUser");
      this.predicates.add("weblogic.entitlement.rules.OwnerIDDGroup");
      this.predicates.add("weblogic.entitlement.rules.AdminIDDUser");
      this.predicates.add("weblogic.entitlement.rules.AdminIDDGroup");
      this.predicates.add("weblogic.entitlement.rules.AdministrativeGroup");
      this.predicates.add("weblogic.entitlement.rules.IDCSAppRoleName");
      this.predicates.add("weblogic.entitlement.rules.Scope");
      this.predicates.add("weblogic.entitlement.rules.SignaturePredicate");
      this.predicates.add("weblogic.entitlement.rules.TimePredicate");
      this.predicates.add("weblogic.entitlement.rules.UncheckedPolicy");
      if (this.isPortal()) {
         this.predicates.add("com.bea.p13n.entitlements.rules.internal.ExpressionPredicate");
      }

      this.days.put("Sunday", new Integer(1));
      this.days.put("Monday", new Integer(2));
      this.days.put("Tuesday", new Integer(3));
      this.days.put("Wednesday", new Integer(4));
      this.days.put("Thursday", new Integer(5));
      this.days.put("Friday", new Integer(6));
      this.days.put("Saturday", new Integer(7));
   }

   public EntitlementConverter(LoggerSpi log) throws URISyntaxException {
      this.log = log;
      this.rmu = new RoleMatchUtil();
      this.predicates.add("weblogic.entitlement.rules.After");
      this.predicates.add("weblogic.entitlement.rules.AfterDayOfMonth");
      this.predicates.add("weblogic.entitlement.rules.Before");
      this.predicates.add("weblogic.entitlement.rules.BeforeDayOfMonth");
      this.predicates.add("weblogic.entitlement.rules.ContextElementIsSet");
      this.predicates.add("weblogic.entitlement.rules.ContextElementNumberEquals");
      this.predicates.add("weblogic.entitlement.rules.ContextElementNumberGreater");
      this.predicates.add("weblogic.entitlement.rules.ContextElementNumberLess");
      this.predicates.add("weblogic.entitlement.rules.ContextElementStringEquals");
      this.predicates.add("weblogic.entitlement.rules.ExcludedPolicy");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrIsSet");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrNumberEquals");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrNumberGreater");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrNumberLess");
      this.predicates.add("weblogic.entitlement.rules.HttpRequestAttrStringEquals");
      this.predicates.add("weblogic.entitlement.rules.InDevelopmentMode");
      this.predicates.add("weblogic.entitlement.rules.InSecureMode");
      this.predicates.add("weblogic.entitlement.rules.IsDayOfMonth");
      this.predicates.add("weblogic.entitlement.rules.IsDayOfWeek");
      this.predicates.add("weblogic.entitlement.rules.IDDUser");
      this.predicates.add("weblogic.entitlement.rules.IDDGroup");
      this.predicates.add("weblogic.entitlement.rules.OwnerIDDUser");
      this.predicates.add("weblogic.entitlement.rules.OwnerIDDGroup");
      this.predicates.add("weblogic.entitlement.rules.AdminIDDUser");
      this.predicates.add("weblogic.entitlement.rules.AdminIDDGroup");
      this.predicates.add("weblogic.entitlement.rules.AdministrativeGroup");
      this.predicates.add("weblogic.entitlement.rules.IDCSAppRoleName");
      this.predicates.add("weblogic.entitlement.rules.Scope");
      this.predicates.add("weblogic.entitlement.rules.SignaturePredicate");
      this.predicates.add("weblogic.entitlement.rules.TimePredicate");
      this.predicates.add("weblogic.entitlement.rules.UncheckedPolicy");
      if (this.isPortal()) {
         this.predicates.add("com.bea.p13n.entitlements.rules.internal.ExpressionPredicate");
      }

      this.days.put("Sunday", new Integer(1));
      this.days.put("Monday", new Integer(2));
      this.days.put("Tuesday", new Integer(3));
      this.days.put("Wednesday", new Integer(4));
      this.days.put("Thursday", new Integer(5));
      this.days.put("Friday", new Integer(6));
      this.days.put("Saturday", new Integer(7));
   }

   public Collection getPredicates() {
      return this.predicates;
   }

   public Policy convertResourceExpression(Resource resource, String expression) throws DocumentParseException, URISyntaxException {
      return this.convertExpression((Resource)resource, (String)null, expression);
   }

   public Policy convertResourceExpression(String resource, String expression) throws DocumentParseException, URISyntaxException {
      return this.convertExpression((String)resource, (String)null, expression);
   }

   public Policy convertRoleExpression(Resource resource, String role, String expression, String auxiliary) throws DocumentParseException, URISyntaxException {
      return this.convertExpression(resource, role, expression, auxiliary);
   }

   public Policy convertRoleExpression(String resource, String role, String expression, String auxiliary) throws DocumentParseException, URISyntaxException {
      return this.convertExpression(resource, role, expression, auxiliary);
   }

   public Target generateTarget(Resource resource, String role) throws DocumentParseException, URISyntaxException {
      List rm = ResourceMatchUtil.generateResourceMatches(resource);
      List am = null;
      if (role != null) {
         if (rm == null) {
            rm = new ArrayList();
         }

         ((List)rm).add(this.rmu.generateResourceMatch(role));
         am = Collections.singletonList(this.rmu.generateActionMatch());
      }

      return this.generateTarget((List)rm, (List)am);
   }

   public Target generateTarget(String resource, String role) throws DocumentParseException, URISyntaxException {
      List rm = ResourceMatchUtil.generateResourceMatches(resource);
      List am = null;
      if (role != null) {
         if (rm == null) {
            rm = new ArrayList();
         }

         ((List)rm).add(this.rmu.generateResourceMatch(role));
         am = Collections.singletonList(this.rmu.generateActionMatch());
      }

      return this.generateTarget((List)rm, (List)am);
   }

   private Target generateRoleTarget(String role) throws DocumentParseException, URISyntaxException {
      return this.rmu.generateTarget(role);
   }

   private Target generateTarget(List matchList, List actionMatch) {
      if (matchList == null && actionMatch == null) {
         return null;
      } else if (matchList == null) {
         return new Target(new Actions(Collections.singletonList(new Action(actionMatch))));
      } else {
         return actionMatch == null ? new Target(new Resources(Collections.singletonList(new com.bea.common.security.xacml.policy.Resource(matchList)))) : new Target(new Resources(Collections.singletonList(new com.bea.common.security.xacml.policy.Resource(matchList))), new Actions(Collections.singletonList(new Action(actionMatch))));
      }
   }

   private Policy convertExpression(Resource resource, String role, String expression) throws DocumentParseException, URISyntaxException {
      return this.convertExpression((Resource)resource, role, expression, (String)null);
   }

   private Policy convertExpression(Resource resource, String role, String expression, String auxiliary) throws DocumentParseException, URISyntaxException {
      return this.convertExpression(resource != null ? resource.toString() : null, role, expression, auxiliary);
   }

   public String getPolicyId(Resource resource) {
      return ResourcePolicyIdUtil.getPolicyId(resource);
   }

   public String getPolicyId(Resource resource, String role) {
      return ResourcePolicyIdUtil.getPolicyId(resource, role);
   }

   public String getPolicyId(String resource) {
      return ResourcePolicyIdUtil.getPolicyId(resource);
   }

   public String getSearchPolicyId(String resource) {
      return ResourcePolicyIdUtil.getSearchPolicyId(resource);
   }

   public String getPolicyId(String resource, String role) {
      return ResourcePolicyIdUtil.getPolicyId(resource, role);
   }

   public String getResourceId(String policyId) {
      return ResourcePolicyIdUtil.getResourceId(policyId);
   }

   public ResourcePolicyIdUtil.RoleResource getRoleResourceId(String policyId) {
      return ResourcePolicyIdUtil.getRoleResourceId(policyId);
   }

   private Policy convertExpression(String resource, String role, String expression) throws DocumentParseException, URISyntaxException {
      return this.convertExpression((String)resource, role, expression, (String)null);
   }

   private Policy convertExpression(String resource, String role, String expression, String auxiliary) throws DocumentParseException, URISyntaxException {
      try {
         List rules = new ArrayList();
         Set vdefs = new HashSet();

         try {
            if (expression != null && expression.length() > 0) {
               try {
                  ((List)rules).add(new Rule("primary-rule", true, this.generateCondition(expression, role != null, auxiliary, vdefs)));
               } catch (DocumentParseException var9) {
                  StringBuffer sb = new StringBuffer();
                  sb.append("Parsing failure for expression: ");
                  sb.append(expression);
                  if (auxiliary != null) {
                     sb.append(" with auxiliary document: \n");
                     sb.append(auxiliary);
                  }

                  throw new DocumentParseException(sb.toString(), var9);
               }
            }

            ((List)rules).add(new Rule("deny-rule", false));
         } catch (SimplificationException var10) {
            rules = var10.getRules();
         }

         String id = this.getPolicyId(resource, role);
         return new Policy(new URI(id), this.generateTarget(resource, role), new URI("urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-applicable"), this.generateDescription(expression, auxiliary), "1.0", (PolicyDefaults)null, (List)null, (Obligations)null, (List)rules, (List)null, vdefs);
      } catch (java.net.URISyntaxException var11) {
         throw new URISyntaxException(var11);
      }
   }

   private String generateDescription(String expression, String auxiliary) {
      StringBuffer sb = new StringBuffer();
      if (auxiliary != null) {
         sb.append("<![CDATA[<aux:desc><aux:ent>");
      }

      sb.append(expression != null ? expression : "");
      if (auxiliary != null) {
         sb.append("</aux:ent><aux:data>");
         sb.append(auxiliary);
         sb.append("</aux:data></aux:desc>]]>");
      }

      return sb.toString();
   }

   private Condition generateCondition(String expression, boolean isRoleExpression, String auxiliary, Set vdefs) throws DocumentParseException, SimplificationException, URISyntaxException {
      Expression exp = Parser.generateExpression(expression, isRoleExpression);
      return new Condition(this.convert(exp, auxiliary, vdefs));
   }

   public PolicySet createTopPolicySet() throws DocumentParseException, URISyntaxException {
      try {
         String id = this.getTopPolicyId();
         URI uid = new URI(id);
         return new PolicySet(uid, (Target)null, new URI("urn:bea:xacml:2.0:entitlement:policy-combining-algorithm:most-specific"), (List)null);
      } catch (java.net.URISyntaxException var3) {
         throw new URISyntaxException(var3);
      }
   }

   public PolicySet createTopPolicySet(String role) throws DocumentParseException, URISyntaxException {
      try {
         String id = this.getTopPolicyId(role);
         URI uid = new URI(id);
         return new PolicySet(uid, this.generateRoleTarget(role), new URI("urn:bea:xacml:2.0:entitlement:policy-combining-algorithm:most-specific"), (List)null);
      } catch (java.net.URISyntaxException var4) {
         throw new URISyntaxException(var4);
      }
   }

   public String getTopPolicyId() {
      return this.getTopPolicyId((String)null);
   }

   public String getTopPolicyId(String role) {
      return ResourcePolicyIdUtil.getPolicyId((String)null, role);
   }

   private com.bea.common.security.xacml.policy.Expression convert(Expression exp, String auxiliary, Set vdefs) throws DocumentParseException, SimplificationException, URISyntaxException {
      if (exp == null) {
         return new AttributeValue(new BooleanAttribute(false));
      } else {
         try {
            Apply resource_idd = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:bea:xacml:2.0:environment:context:com.oracle.contextelement.security.ResourceIdentityDomain"), new URI("http://www.w3.org/2001/XMLSchema#string"), false)));
            Apply admin_idd = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:bea:xacml:2.0:environment:context:com.oracle.contextelement.security.AdminIdentityDomain"), new URI("http://www.w3.org/2001/XMLSchema#string"), false)));
            BinaryOp bo;
            if (exp instanceof Union) {
               bo = (BinaryOp)exp;
               return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:or"), Arrays.asList(this.convert(bo.getLeft(), auxiliary, vdefs), this.convert(bo.getRight(), auxiliary, vdefs)));
            } else if (exp instanceof Intersect) {
               bo = (BinaryOp)exp;
               return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:and"), Arrays.asList(this.convert(bo.getLeft(), auxiliary, vdefs), this.convert(bo.getRight(), auxiliary, vdefs)));
            } else if (exp instanceof Difference) {
               bo = (BinaryOp)exp;
               return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:and"), Arrays.asList(this.convert(bo.getLeft(), auxiliary, vdefs), new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:not"), Arrays.asList(this.convert(bo.getRight(), auxiliary, vdefs)))));
            } else if (exp instanceof Complement) {
               UnaryOp uo = (UnaryOp)exp;
               return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:not"), Arrays.asList(this.convert(uo.getOp(), auxiliary, vdefs)));
            } else {
               List list;
               String s;
               ArrayList bag;
               Iterator var36;
               if (exp instanceof Users) {
                  list = ((Users)exp).getData().getData();
                  if (list.isEmpty()) {
                     throw new DocumentParseException("Cannot generate test for empty list");
                  } else if (list.size() == 1) {
                     return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-is-in"), Arrays.asList(new AttributeValue(new StringAttribute((String)list.get(0))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:subject-id"), new URI("http://www.w3.org/2001/XMLSchema#string"), false)));
                  } else {
                     bag = new ArrayList();
                     var36 = list.iterator();

                     while(var36.hasNext()) {
                        s = (String)var36.next();
                        bag.add(new AttributeValue(new StringAttribute(s)));
                     }

                     return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-bag"), bag), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:subject-id"), new URI("http://www.w3.org/2001/XMLSchema#string"), false)));
                  }
               } else if (exp instanceof Groups) {
                  list = ((Groups)exp).getData().getData();
                  if (list.isEmpty()) {
                     throw new DocumentParseException("Cannot generate test for empty list");
                  } else if (list.size() == 1) {
                     return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-is-in"), Arrays.asList(new AttributeValue(new StringAttribute((String)list.get(0))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:2.0:subject:group"), new URI("http://www.w3.org/2001/XMLSchema#string"), false)));
                  } else {
                     bag = new ArrayList();
                     var36 = list.iterator();

                     while(var36.hasNext()) {
                        s = (String)var36.next();
                        bag.add(new AttributeValue(new StringAttribute(s)));
                     }

                     return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-bag"), bag), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:2.0:subject:group"), new URI("http://www.w3.org/2001/XMLSchema#string"), false)));
                  }
               } else if (exp instanceof Roles) {
                  list = ((Roles)exp).getData().getData();
                  if (list.isEmpty()) {
                     throw new DocumentParseException("Cannot generate test for empty list");
                  } else if (list.size() == 1) {
                     return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-is-in"), Arrays.asList(new AttributeValue(new StringAttribute((String)list.get(0))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:2.0:subject:role"), new URI("http://www.w3.org/2001/XMLSchema#string"), false)));
                  } else {
                     bag = new ArrayList();
                     var36 = list.iterator();

                     while(var36.hasNext()) {
                        s = (String)var36.next();
                        bag.add(new AttributeValue(new StringAttribute(s)));
                     }

                     return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-at-least-one-member-of"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-bag"), bag), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:2.0:subject:role"), new URI("http://www.w3.org/2001/XMLSchema#string"), false)));
                  }
               } else {
                  Predicate p = (Predicate)exp;
                  List params;
                  TimeZone tz;
                  Calendar c;
                  SimpleDateFormat df;
                  if ("weblogic.entitlement.rules.After".equals(p.getClassname())) {
                     params = p.getParameters().getData();
                     if (params.size() < 1) {
                        throw new DocumentParseException("After predicate missing parameters");
                     } else {
                        df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                        if (params.size() >= 2) {
                           tz = TimeZone.getTimeZone((String)params.get(1));
                           if (tz != null) {
                              df.setTimeZone(tz);
                           }
                        }

                        try {
                           c = Calendar.getInstance();
                           c.setTime(df.parse((String)params.get(0)));
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than"), Arrays.asList(new AttributeValue(new DateTimeAttribute(c)), new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"), new URI("http://www.w3.org/2001/XMLSchema#dateTime"), true)))));
                        } catch (ParseException var14) {
                           throw new DocumentParseException(var14);
                        }
                     }
                  } else {
                     Apply tod;
                     int startTime;
                     Object domGuard;
                     Object currentTime;
                     if ("weblogic.entitlement.rules.AfterDayOfMonth".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 1) {
                           throw new DocumentParseException("After day of month predicate missing parameters");
                        } else {
                           vdefs.add(new VariableDefinition("current-dateTime", new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"), new URI("http://www.w3.org/2001/XMLSchema#dateTime"), true)))));
                           currentTime = new VariableReference("current-dateTime");
                           if (params.size() >= 2) {
                              tz = TimeZone.getTimeZone((String)params.get(1));
                              if (tz != null) {
                                 currentTime = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-subtract-dayTimeDuration"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-add-dayTimeDuration"), Arrays.asList((com.bea.common.security.xacml.policy.Expression)currentTime, new AttributeValue(new DayTimeDurationAttribute(0, 0, 0, 0, (long)tz.getRawOffset() * 1000000L)))), new Apply(new URI("urn:bea:xacml:2.0:function:dayTimeDuration-timeZoneOffset"), (List)null)));
                              }
                           }

                           tod = new Apply(new URI("urn:bea:xacml:2.0:function:dateTime-dayOfMonth"), Arrays.asList((com.bea.common.security.xacml.policy.Expression)currentTime));

                           try {
                              startTime = Integer.parseInt((String)params.get(0));
                              domGuard = startTime >= 0 ? new AttributeValue(new IntegerAttribute(startTime)) : new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:integer-add"), Arrays.asList(new AttributeValue(new IntegerAttribute(startTime)), new AttributeValue(new IntegerAttribute(1)), new Apply(new URI("urn:bea:xacml:2.0:function:dateTime-dayOfMonthMaximum"), Arrays.asList((com.bea.common.security.xacml.policy.Expression)currentTime))));
                              return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:integer-greater-than"), Arrays.asList(tod, (com.bea.common.security.xacml.policy.Expression)domGuard));
                           } catch (NumberFormatException var15) {
                              throw new DocumentParseException(var15);
                           }
                        }
                     } else if ("weblogic.entitlement.rules.Before".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 1) {
                           throw new DocumentParseException("Before predicate missing parameters");
                        } else {
                           df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                           if (params.size() >= 2) {
                              tz = TimeZone.getTimeZone((String)params.get(1));
                              if (tz != null) {
                                 df.setTimeZone(tz);
                              }
                           }

                           try {
                              c = Calendar.getInstance();
                              c.setTime(df.parse((String)params.get(0)));
                              return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than"), Arrays.asList(new AttributeValue(new DateTimeAttribute(c)), new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"), new URI("http://www.w3.org/2001/XMLSchema#dateTime"), true)))));
                           } catch (ParseException var16) {
                              throw new DocumentParseException(var16);
                           }
                        }
                     } else if ("weblogic.entitlement.rules.BeforeDayOfMonth".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 1) {
                           throw new DocumentParseException("Before day of month predicate missing parameters");
                        } else {
                           vdefs.add(new VariableDefinition("current-dateTime", new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"), new URI("http://www.w3.org/2001/XMLSchema#dateTime"), true)))));
                           currentTime = new VariableReference("current-dateTime");
                           if (params.size() >= 2) {
                              tz = TimeZone.getTimeZone((String)params.get(1));
                              if (tz != null) {
                                 currentTime = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-subtract-dayTimeDuration"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-add-dayTimeDuration"), Arrays.asList((com.bea.common.security.xacml.policy.Expression)currentTime, new AttributeValue(new DayTimeDurationAttribute(0, 0, 0, 0, (long)tz.getRawOffset() * 1000000L)))), new Apply(new URI("urn:bea:xacml:2.0:function:dayTimeDuration-timeZoneOffset"), (List)null)));
                              }
                           }

                           tod = new Apply(new URI("urn:bea:xacml:2.0:function:dateTime-dayOfMonth"), Arrays.asList((com.bea.common.security.xacml.policy.Expression)currentTime));

                           try {
                              startTime = Integer.parseInt((String)params.get(0));
                              domGuard = startTime >= 0 ? new AttributeValue(new IntegerAttribute(startTime)) : new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:integer-add"), Arrays.asList(new AttributeValue(new IntegerAttribute(startTime)), new AttributeValue(new IntegerAttribute(1)), new Apply(new URI("urn:bea:xacml:2.0:function:dateTime-dayOfMonthMaximum"), Arrays.asList((com.bea.common.security.xacml.policy.Expression)currentTime))));
                              return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:integer-less-than"), Arrays.asList(tod, (com.bea.common.security.xacml.policy.Expression)domGuard));
                           } catch (NumberFormatException var17) {
                              throw new DocumentParseException(var17);
                           }
                        }
                     } else if ("weblogic.entitlement.rules.ContextElementIsSet".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 1) {
                           throw new DocumentParseException("Context element is set missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:not"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:object-is-null"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:object-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:bea:xacml:2.0:environment:context:" + this.urlEncode((String)params.get(0))), new URI("urn:bea:xacml:2.0:data-type:object"), true)))))));
                        }
                     } else if ("weblogic.entitlement.rules.ContextElementNumberEquals".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 2) {
                           throw new DocumentParseException("Context element number equals missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:double-equal"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:double-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:bea:xacml:2.0:environment:context:" + this.urlEncode((String)params.get(0))), new URI("http://www.w3.org/2001/XMLSchema#double"), true))), new AttributeValue(new DoubleAttribute((String)params.get(1)))));
                        }
                     } else if ("weblogic.entitlement.rules.ContextElementNumberGreater".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 2) {
                           throw new DocumentParseException("Context element number greater missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:double-greater-than"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:double-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:bea:xacml:2.0:environment:context:" + this.urlEncode((String)params.get(0))), new URI("http://www.w3.org/2001/XMLSchema#double"), true))), new AttributeValue(new DoubleAttribute((String)params.get(1)))));
                        }
                     } else if ("weblogic.entitlement.rules.ContextElementNumberLess".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 2) {
                           throw new DocumentParseException("Context element number less missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:double-less-than"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:double-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:bea:xacml:2.0:environment:context:" + this.urlEncode((String)params.get(0))), new URI("http://www.w3.org/2001/XMLSchema#double"), true))), new AttributeValue(new DoubleAttribute((String)params.get(1)))));
                        }
                     } else if ("weblogic.entitlement.rules.ContextElementStringEquals".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 2) {
                           throw new DocumentParseException("Context element string equals missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-equal"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:bea:xacml:2.0:environment:context:" + this.urlEncode((String)params.get(0))), new URI("http://www.w3.org/2001/XMLSchema#string"), true))), new AttributeValue(new StringAttribute((String)params.get(1)))));
                        }
                     } else if ("weblogic.entitlement.rules.ExcludedPolicy".equals(p.getClassname())) {
                        throw new SimplificationException(Collections.singletonList(new Rule("excluded-policy", false)));
                     } else if ("weblogic.entitlement.rules.HttpRequestAttrIsSet".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 1) {
                           throw new DocumentParseException("HTTP request attr is set missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:not"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:object-is-null"), Arrays.asList(this.getHttpRequestAttrApply((String)params.get(0))))));
                        }
                     } else if ("weblogic.entitlement.rules.HttpRequestAttrNumberEquals".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 2) {
                           throw new DocumentParseException("HTTP request attr number equals missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:double-equal"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:object-to-double"), Arrays.asList(this.getHttpRequestAttrApply((String)params.get(0)))), new AttributeValue(new DoubleAttribute((String)params.get(1)))));
                        }
                     } else if ("weblogic.entitlement.rules.HttpRequestAttrNumberGreater".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 2) {
                           throw new DocumentParseException("HTTP request attr number greater missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:double-greater-than"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:object-to-double"), Arrays.asList(this.getHttpRequestAttrApply((String)params.get(0)))), new AttributeValue(new DoubleAttribute((String)params.get(1)))));
                        }
                     } else if ("weblogic.entitlement.rules.HttpRequestAttrNumberLess".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 2) {
                           throw new DocumentParseException("HTTP request attr number less missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:double-less-than"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:object-to-double"), Arrays.asList(this.getHttpRequestAttrApply((String)params.get(0)))), new AttributeValue(new DoubleAttribute((String)params.get(1)))));
                        }
                     } else if ("weblogic.entitlement.rules.HttpRequestAttrStringEquals".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 2) {
                           throw new DocumentParseException("HTTP request attr string equals missing parameters");
                        } else {
                           return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-equal"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:object-to-string"), Arrays.asList(this.getHttpRequestAttrApply((String)params.get(0)))), new AttributeValue(new StringAttribute((String)params.get(1)))));
                        }
                     } else if ("weblogic.entitlement.rules.InDevelopmentMode".equals(p.getClassname())) {
                        return new Apply(new URI("urn:bea:xacml:2.0:function:in-development-mode"), (List)null);
                     } else if ("weblogic.entitlement.rules.InSecureMode".equals(p.getClassname())) {
                        return new Apply(new URI("urn:bea:xacml:2.0:function:in-secure-mode"), (List)null);
                     } else if ("weblogic.entitlement.rules.IsDayOfMonth".equals(p.getClassname())) {
                        params = p.getParameters().getData();
                        if (params.size() < 1) {
                           throw new DocumentParseException("Is day of month predicate missing parameters");
                        } else {
                           vdefs.add(new VariableDefinition("current-dateTime", new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"), new URI("http://www.w3.org/2001/XMLSchema#dateTime"), true)))));
                           currentTime = new VariableReference("current-dateTime");
                           if (params.size() >= 2) {
                              tz = TimeZone.getTimeZone((String)params.get(1));
                              if (tz != null) {
                                 currentTime = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-subtract-dayTimeDuration"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-add-dayTimeDuration"), Arrays.asList((com.bea.common.security.xacml.policy.Expression)currentTime, new AttributeValue(new DayTimeDurationAttribute(0, 0, 0, 0, (long)tz.getRawOffset() * 1000000L)))), new Apply(new URI("urn:bea:xacml:2.0:function:dayTimeDuration-timeZoneOffset"), (List)null)));
                              }
                           }

                           tod = new Apply(new URI("urn:bea:xacml:2.0:function:dateTime-dayOfMonth"), Arrays.asList((com.bea.common.security.xacml.policy.Expression)currentTime));

                           try {
                              startTime = Integer.parseInt((String)params.get(0));
                              domGuard = startTime >= 0 ? new AttributeValue(new IntegerAttribute(startTime)) : new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:integer-add"), Arrays.asList(new AttributeValue(new IntegerAttribute(startTime)), new AttributeValue(new IntegerAttribute(1)), new Apply(new URI("urn:bea:xacml:2.0:function:dateTime-dayOfMonthMaximum"), Arrays.asList((com.bea.common.security.xacml.policy.Expression)currentTime))));
                              return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:integer-equal"), Arrays.asList(tod, (com.bea.common.security.xacml.policy.Expression)domGuard));
                           } catch (NumberFormatException var18) {
                              throw new DocumentParseException(var18);
                           }
                        }
                     } else {
                        Apply currentTime;
                        if ("weblogic.entitlement.rules.IsDayOfWeek".equals(p.getClassname())) {
                           params = p.getParameters().getData();
                           if (params.size() < 1) {
                              throw new DocumentParseException("Is day of week predicate missing parameters");
                           } else {
                              currentTime = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"), new URI("http://www.w3.org/2001/XMLSchema#dateTime"), true)));
                              if (params.size() >= 2) {
                                 tz = TimeZone.getTimeZone((String)params.get(1));
                                 if (tz != null) {
                                    currentTime = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-subtract-dayTimeDuration"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-add-dayTimeDuration"), Arrays.asList(currentTime, new AttributeValue(new DayTimeDurationAttribute(0, 0, 0, 0, (long)tz.getRawOffset() * 1000000L)))), new Apply(new URI("urn:bea:xacml:2.0:function:dayTimeDuration-timeZoneOffset"), (List)null)));
                                 }
                              }

                              tod = new Apply(new URI("urn:bea:xacml:2.0:function:dateTime-dayOfWeek"), Arrays.asList(currentTime));
                              Integer dowParam = (Integer)this.days.get(params.get(0));
                              if (dowParam != null) {
                                 return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:integer-equal"), Arrays.asList(tod, new AttributeValue(new IntegerAttribute(dowParam))));
                              } else {
                                 throw new DocumentParseException("Unknown day of week constant: " + (String)params.get(0));
                              }
                           }
                        } else {
                           URI iddname;
                           if ("weblogic.entitlement.rules.IDDUser".equals(p.getClassname())) {
                              params = p.getParameters().getData();
                              if (params.size() != 2) {
                                 throw new DocumentParseException("IDDUser predicate must have 2 parameters");
                              } else {
                                 iddname = new URI(IDDNAME_PREFIX + this.urlEncode((String)params.get(1)) + "/user/" + this.urlEncode((String)params.get(0)));
                                 return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new AttributeValue(new AnyURIAttribute(iddname)), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                              }
                           } else if ("weblogic.entitlement.rules.IDDGroup".equals(p.getClassname())) {
                              params = p.getParameters().getData();
                              if (params.size() != 2) {
                                 throw new DocumentParseException("IDDGroup predicate must have 2 parameters");
                              } else {
                                 iddname = new URI(IDDNAME_PREFIX + this.urlEncode((String)params.get(1)) + "/group/" + this.urlEncode((String)params.get(0)));
                                 return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new AttributeValue(new AnyURIAttribute(iddname)), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                              }
                           } else {
                              ArrayList bag;
                              Iterator var9;
                              String s;
                              if ("weblogic.entitlement.rules.OwnerIDDUser".equals(p.getClassname())) {
                                 params = p.getParameters().getData();
                                 if (params.size() < 1) {
                                    throw new DocumentParseException("OwnerIDDUser predicate must have 1 or more parameters");
                                 } else if (params.size() == 1) {
                                    return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), resource_idd, new AttributeValue(new StringAttribute("/user/" + this.urlEncode((String)params.get(0)))))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                 } else {
                                    bag = new ArrayList();
                                    var9 = params.iterator();

                                    while(var9.hasNext()) {
                                       s = (String)var9.next();
                                       bag.add(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), resource_idd, new AttributeValue(new StringAttribute("/user/" + this.urlEncode(s))))));
                                    }

                                    return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-at-least-one-member-of"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-bag"), bag), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                 }
                              } else if ("weblogic.entitlement.rules.OwnerIDDGroup".equals(p.getClassname())) {
                                 params = p.getParameters().getData();
                                 if (params.size() < 1) {
                                    throw new DocumentParseException("OwnerIDDGroup predicate must have 1 or more parameters");
                                 } else if (params.size() == 1) {
                                    return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), resource_idd, new AttributeValue(new StringAttribute("/group/" + this.urlEncode((String)params.get(0)))))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                 } else {
                                    bag = new ArrayList();
                                    var9 = params.iterator();

                                    while(var9.hasNext()) {
                                       s = (String)var9.next();
                                       bag.add(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), resource_idd, new AttributeValue(new StringAttribute("/group/" + this.urlEncode(s))))));
                                    }

                                    return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-at-least-one-member-of"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-bag"), bag), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                 }
                              } else if ("weblogic.entitlement.rules.AdminIDDUser".equals(p.getClassname())) {
                                 params = p.getParameters().getData();
                                 if (params.size() < 1) {
                                    throw new DocumentParseException("AdminIDDUser predicate must have 1 or more parameters");
                                 } else if (params.size() == 1) {
                                    return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), admin_idd, new AttributeValue(new StringAttribute("/user/" + this.urlEncode((String)params.get(0)))))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                 } else {
                                    bag = new ArrayList();
                                    var9 = params.iterator();

                                    while(var9.hasNext()) {
                                       s = (String)var9.next();
                                       bag.add(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), admin_idd, new AttributeValue(new StringAttribute("/user/" + this.urlEncode(s))))));
                                    }

                                    return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-at-least-one-member-of"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-bag"), bag), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                 }
                              } else if (!"weblogic.entitlement.rules.AdminIDDGroup".equals(p.getClassname())) {
                                 if ("weblogic.entitlement.rules.AdministrativeGroup".equals(p.getClassname())) {
                                    params = p.getParameters().getData();
                                    if (params.size() != 1) {
                                       throw new DocumentParseException("AdministrativeGroup predicate must have 1 parameter");
                                    } else {
                                       currentTime = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), resource_idd, new AttributeValue(new StringAttribute("/group/" + this.urlEncode((String)params.get(0)))))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                       tod = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), admin_idd, new AttributeValue(new StringAttribute("/group/" + this.urlEncode((String)params.get(0)))))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                       return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:or"), Arrays.asList(currentTime, tod));
                                    }
                                 } else if ("weblogic.entitlement.rules.IDCSAppRoleName".equals(p.getClassname())) {
                                    params = p.getParameters().getData();
                                    if (params.size() != 2) {
                                       throw new DocumentParseException("weblogic.entitlement.rules.IDCSAppRoleName predicate must have 2 parameters!");
                                    } else {
                                       iddname = new URI("idcsrole://" + this.urlEncode((String)params.get(0)) + "/app/" + this.urlEncode((String)params.get(1)));
                                       return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new AttributeValue(new AnyURIAttribute(iddname)), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:application-roles"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), true)));
                                    }
                                 } else if ("weblogic.entitlement.rules.Scope".equals(p.getClassname())) {
                                    params = p.getParameters().getData();
                                    if (params.size() < 1) {
                                       throw new DocumentParseException("Scope predicate must have 1 or more parameters");
                                    } else if (params.size() == 1) {
                                       return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), resource_idd, new AttributeValue(new StringAttribute("/scope/" + this.urlEncode((String)params.get(0)))))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                    } else if (params.size() > 2) {
                                       throw new DocumentParseException("Scope predicate must have 1 or 2 parameters");
                                    } else {
                                       iddname = new URI(IDDNAME_PREFIX + this.urlEncode((String)params.get(1)) + "/scope/" + this.urlEncode((String)params.get(0)));
                                       return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new AttributeValue(new AnyURIAttribute(iddname)), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                    }
                                 } else if ("weblogic.entitlement.rules.SignaturePredicate".equals(p.getClassname())) {
                                    params = p.getParameters().getData();
                                    if (params.size() != 3) {
                                       throw new DocumentParseException("Signature predicate must have 3 parameters");
                                    } else {
                                       return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:string-is-in"), Arrays.asList(new AttributeValue(new StringAttribute((String)params.get(2))), new SubjectAttributeDesignator(new URI("user".equalsIgnoreCase((String)params.get(0)) ? "urn:oasis:names:tc:xacml:1.0:subject:subject-id" : "urn:oasis:names:tc:xacml:2.0:subject:group"), new URI("http://www.w3.org/2001/XMLSchema#string"), true, new URI("urn:bea:xacml:2.0:subject:context:" + this.urlEncode("Integrity{" + (String)params.get(1) + "}")))));
                                    }
                                 } else if ("weblogic.entitlement.rules.TimePredicate".equals(p.getClassname())) {
                                    params = p.getParameters().getData();
                                    if (params.size() < 1) {
                                       throw new DocumentParseException("Time predicate missing parameters");
                                    } else {
                                       currentTime = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"), new URI("http://www.w3.org/2001/XMLSchema#dateTime"), true)));
                                       if (params.size() > 2) {
                                          tz = TimeZone.getTimeZone((String)params.get(2));
                                          if (tz != null) {
                                             currentTime = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-subtract-dayTimeDuration"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:dateTime-add-dayTimeDuration"), Arrays.asList(currentTime, new AttributeValue(new DayTimeDurationAttribute(0, 0, 0, 0, (long)tz.getRawOffset() * 1000000L)))), new Apply(new URI("urn:bea:xacml:2.0:function:dayTimeDuration-timeZoneOffset"), (List)null)));
                                          }
                                       }

                                       tod = new Apply(new URI("urn:bea:xacml:2.0:function:dateTime-secondsOfDay"), Arrays.asList(currentTime));
                                       startTime = this.parseTimeOfDay((String)params.get(0));
                                       int endTime = params.size() > 1 ? this.parseTimeOfDay((String)params.get(1)) : -1;
                                       Apply pastStart = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:integer-greater-than-or-equal"), Arrays.asList(tod, new AttributeValue(new IntegerAttribute(startTime))));
                                       if (endTime == -1) {
                                          return pastStart;
                                       } else {
                                          Apply beforeEnd = new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:integer-less-than"), Arrays.asList(tod, new AttributeValue(new IntegerAttribute(endTime))));
                                          return startTime <= endTime ? new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:and"), Arrays.asList(pastStart, beforeEnd)) : new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:or"), Arrays.asList(pastStart, beforeEnd));
                                       }
                                    }
                                 } else if ("weblogic.entitlement.rules.UncheckedPolicy".equals(p.getClassname())) {
                                    throw new SimplificationException(Collections.singletonList(new Rule("unchecked-policy", true)));
                                 } else if ("com.bea.p13n.entitlements.rules.internal.ExpressionPredicate".equals(p.getClassname())) {
                                    if (auxiliary != null) {
                                       ExpressionConverter p13n = this.getP13NConverter();
                                       if (p13n != null) {
                                          return p13n.convert(auxiliary, vdefs);
                                       } else {
                                          throw new DocumentParseException("P13N expression converter improperly installed or registered");
                                       }
                                    } else {
                                       throw new SimplificationException(Collections.singletonList(new Rule("missing-p13n-auxiliary-expression-hence-deny", false)));
                                    }
                                 } else {
                                    throw new DocumentParseException("Unrecognized predicate class: " + p.getClassname());
                                 }
                              } else {
                                 params = p.getParameters().getData();
                                 if (params.size() < 1) {
                                    throw new DocumentParseException("AdminIDDGroup predicate must have 1 or more parameters");
                                 } else if (params.size() == 1) {
                                    return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-is-in"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), admin_idd, new AttributeValue(new StringAttribute("/group/" + this.urlEncode((String)params.get(0)))))), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                 } else {
                                    bag = new ArrayList();
                                    var9 = params.iterator();

                                    while(var9.hasNext()) {
                                       s = (String)var9.next();
                                       bag.add(new Apply(new URI("urn:bea:xacml:2.0:function:uri-string-concat"), Arrays.asList(new AttributeValue(new StringAttribute(IDDNAME_PREFIX)), admin_idd, new AttributeValue(new StringAttribute("/group/" + this.urlEncode(s))))));
                                    }

                                    return new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-at-least-one-member-of"), Arrays.asList(new Apply(new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-bag"), bag), new SubjectAttributeDesignator(new URI("urn:oasis:names:tc:xacml:1.0:subject:iddname"), new URI("http://www.w3.org/2001/XMLSchema#anyURI"), false)));
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         } catch (java.net.URISyntaxException var19) {
            throw new URISyntaxException(var19);
         }
      }
   }

   private int parseTimeOfDay(String timeOfDay) throws DocumentParseException {
      int hour = 0;
      int minute = 0;
      int second = 0;
      StringTokenizer tk = new StringTokenizer(timeOfDay, ":", false);
      if (tk.hasMoreTokens()) {
         hour = Integer.parseInt(tk.nextToken().trim());
         if (tk.hasMoreTokens()) {
            minute = Integer.parseInt(tk.nextToken().trim());
            if (tk.hasMoreTokens()) {
               second = Integer.parseInt(tk.nextToken().trim());
               if (tk.hasMoreTokens()) {
                  throw new DocumentParseException("Illegal format for time of day.  Must be HH:MM:SS");
               }
            }
         }
      }

      if (hour >= 0 && hour <= 23) {
         if (minute >= 0 && minute <= 59) {
            if (second >= 0 && second <= 59) {
               return (hour * 60 + minute) * 60 + second;
            } else {
               throw new DocumentParseException("Illegal second value: " + second);
            }
         } else {
            throw new DocumentParseException("Illegal minute value: " + minute);
         }
      } else {
         throw new DocumentParseException("Illegal hour value: " + hour);
      }
   }

   private Apply getHttpRequestAttrApply(String attrName) throws URISyntaxException, DocumentParseException {
      try {
         Apply httpRequest = new Apply(new URI("urn:bea:xacml:2.0:function:object-one-and-only"), Arrays.asList(new EnvironmentAttributeDesignator(new URI("urn:bea:xacml:2.0:environment:context:com.bea.contextelement.servlet.HttpServletRequest"), new URI("urn:bea:xacml:2.0:data-type:object"), true)));
         if (attrName.startsWith("Attribute.")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getAttribute")), new AttributeValue(new StringAttribute(attrName.substring("Attribute.".length())))));
         }

         if (attrName.startsWith("Header.")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getHeader")), new AttributeValue(new StringAttribute(attrName.substring("Header.".length())))));
         }

         if (attrName.startsWith("Parameter.")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getParameter")), new AttributeValue(new StringAttribute(attrName.substring("Parameter.".length())))));
         }

         if (attrName.equals("Method")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getMethod"))));
         }

         if (attrName.equals("RequestURI")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getRequestURI"))));
         }

         if (attrName.equals("QueryString")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getQueryString"))));
         }

         if (attrName.equals("Protocol")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getProtocol"))));
         }

         if (attrName.equals("LocalPort")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getLocalPort"))));
         }

         if (attrName.equals("RemotePort")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getRemotePort"))));
         }

         if (attrName.equals("RemoteHost")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getRemoteHost"))));
         }

         if (attrName.startsWith("Session.Attribute.")) {
            return new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(new Apply(new URI("urn:bea:xacml:2.0:function:instance-method-match"), Arrays.asList(httpRequest, new AttributeValue(new StringAttribute("getSession")))), new AttributeValue(new StringAttribute("getAttribute")), new AttributeValue(new StringAttribute(attrName.substring("Session.Attribute.".length())))));
         }
      } catch (java.net.URISyntaxException var3) {
         throw new URISyntaxException(var3);
      }

      throw new DocumentParseException("Not a supported attribute: " + attrName);
   }

   private P13NExpressionConverter getP13NConverter() {
      if (this.p13nConverter == null) {
         this.p13nConverter = new P13NExpressionConverter();
      }

      return this.p13nConverter;
   }

   private boolean isPortal() {
      P13NExpressionConverter p = this.getP13NConverter();
      return p != null && p.isPortal();
   }

   private String urlEncode(String in) throws DocumentParseException {
      try {
         return URLEncoder.encode(in, "UTF-8");
      } catch (UnsupportedEncodingException var3) {
         throw new DocumentParseException(var3);
      }
   }

   private static class XACMLLoggerWrapper implements LoggerSpi {
      Logger debugLogger = null;

      public XACMLLoggerWrapper(Logger oldLogger) {
         this.debugLogger = oldLogger;
      }

      public boolean isDebugEnabled() {
         return this.debugLogger != null ? this.debugLogger.isDebugEnabled() : false;
      }

      public void debug(Object msg) {
         if (this.debugLogger != null) {
            this.debugLogger.debug(msg.toString());
         }

      }

      public void debug(Object msg, Throwable th) {
         if (this.debugLogger != null) {
            this.debugLogger.debug(msg.toString(), th);
         }

      }

      public void info(Object msg) {
         if (this.debugLogger != null) {
            this.debugLogger.info(msg.toString());
         }

      }

      public void info(Object msg, Throwable th) {
         if (this.debugLogger != null) {
            this.debugLogger.info(msg.toString(), th);
         }

      }

      public void warn(Object msg) {
         if (this.debugLogger != null) {
            this.debugLogger.warn(msg.toString());
         }

      }

      public void warn(Object msg, Throwable th) {
         if (this.debugLogger != null) {
            this.debugLogger.warn(msg.toString(), th);
         }

      }

      public void error(Object msg) {
         if (this.debugLogger != null) {
            this.debugLogger.error(msg.toString());
         }

      }

      public void error(Object msg, Throwable th) {
         if (this.debugLogger != null) {
            this.debugLogger.error(msg.toString(), th);
         }

      }

      public void severe(Object msg) {
         if (this.debugLogger != null) {
            this.debugLogger.severe(msg.toString());
         }

      }

      public void severe(Object msg, Throwable th) {
         if (this.debugLogger != null) {
            this.debugLogger.severe(msg.toString(), th);
         }

      }
   }
}

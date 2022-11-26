package com.bea.security.xacml.cache.role;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AnyURIAttribute;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.policy.Action;
import com.bea.common.security.xacml.policy.ActionAttributeDesignator;
import com.bea.common.security.xacml.policy.ActionMatch;
import com.bea.common.security.xacml.policy.Actions;
import com.bea.common.security.xacml.policy.AttributeValue;
import com.bea.common.security.xacml.policy.Resource;
import com.bea.common.security.xacml.policy.ResourceAttributeDesignator;
import com.bea.common.security.xacml.policy.ResourceMatch;
import com.bea.common.security.xacml.policy.Resources;
import com.bea.common.security.xacml.policy.Target;
import com.bea.security.xacml.InvalidRoleAssignmentPolicyException;
import com.bea.security.xacml.target.KnownActionMatch;
import com.bea.security.xacml.target.KnownMatch;
import com.bea.security.xacml.target.KnownResourceMatch;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RoleMatchUtil {
   public static final String ROLE_ID = "urn:oasis:names:tc:xacml:2.0:subject:role";
   public static final String ACTION_ID = "urn:oasis:names:tc:xacml:1.0:action:action-id";
   public static final String ENABLE_ROLE = "urn:oasis:names:tc:xacml:2.0:actions:enableRole";
   public static final String STRING_EQUAL = "urn:oasis:names:tc:xacml:1.0:function:string-equal";
   public static final String STRING_TYPE = "http://www.w3.org/2001/XMLSchema#string";
   public static final String ANYURI_EQUAL = "urn:oasis:names:tc:xacml:1.0:function:anyURI-equal";
   public static final String ANYURI_TYPE = "http://www.w3.org/2001/XMLSchema#anyURI";
   private final URI roleId;
   private final URI actionId;
   private final URI enableRole;
   private final URI stringEqual;
   private final URI stringType;
   private final URI anyURIEqual;
   private final URI anyURIType;
   private final KnownMatch enableRoleMatch;
   private final ActionMatch enableRoleActionMatch;

   public RoleMatchUtil() throws URISyntaxException {
      try {
         this.roleId = new URI("urn:oasis:names:tc:xacml:2.0:subject:role");
         this.actionId = new URI("urn:oasis:names:tc:xacml:1.0:action:action-id");
         this.enableRole = new URI("urn:oasis:names:tc:xacml:2.0:actions:enableRole");
         this.stringEqual = new URI("urn:oasis:names:tc:xacml:1.0:function:string-equal");
         this.stringType = new URI("http://www.w3.org/2001/XMLSchema#string");
         this.anyURIEqual = new URI("urn:oasis:names:tc:xacml:1.0:function:anyURI-equal");
         this.anyURIType = new URI("http://www.w3.org/2001/XMLSchema#anyURI");
      } catch (java.net.URISyntaxException var2) {
         throw new URISyntaxException(var2);
      }

      this.enableRoleMatch = new KnownActionMatch(this.anyURIEqual, this.actionId, this.anyURIType, new AnyURIAttribute(this.enableRole), true);
      this.enableRoleActionMatch = new ActionMatch(this.anyURIEqual, new AttributeValue(new AnyURIAttribute(this.enableRole)), new ActionAttributeDesignator(this.actionId, this.anyURIType, true));
   }

   public boolean isRoleTarget(Target t) throws InvalidRoleAssignmentPolicyException {
      if (t != null) {
         Actions as = t.getActions();
         if (as != null) {
            List al = as.getActions();
            if (al != null) {
               Iterator var4 = al.iterator();

               while(var4.hasNext()) {
                  Action a = (Action)var4.next();
                  List aml = a.getMatches();
                  if (aml != null && aml.contains(this.enableRoleActionMatch)) {
                     Resources rs = t.getResources();
                     if (rs != null) {
                        List rl = rs.getResources();
                        if (rl != null && rl.size() == 1) {
                           Resource r = (Resource)rl.get(0);
                           if (r != null) {
                              List rml = r.getMatches();
                              if (rml != null) {
                                 Iterator var11 = rml.iterator();

                                 while(var11.hasNext()) {
                                    ResourceMatch rm = (ResourceMatch)var11.next();
                                    if (this.stringEqual.equals(rm.getMatchId())) {
                                       ResourceAttributeDesignator rad = rm.getDesignator();
                                       if (rad != null && this.roleId.equals(rad.getAttributeId()) && this.stringType.equals(rad.getDataType())) {
                                          return true;
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }

                     throw new InvalidRoleAssignmentPolicyException("Policy or PolicySet has enableRole action-id, but no or invalid target role");
                  }
               }
            }
         }
      }

      return false;
   }

   public String getTargetRole(Target t) throws MultipleRoleTargetException {
      String role = null;
      if (t != null) {
         Resources rs = t.getResources();
         if (rs != null) {
            List rl = rs.getResources();
            if (rl.size() > 1) {
               throw new MultipleRoleTargetException("Multiple roles present in target; use alternate inspection method");
            }

            Iterator var5 = rl.iterator();

            while(true) {
               List rml;
               do {
                  if (!var5.hasNext()) {
                     return role;
                  }

                  Resource r = (Resource)var5.next();
                  rml = r.getMatches();
               } while(rml == null);

               Iterator var8 = rml.iterator();

               while(var8.hasNext()) {
                  ResourceMatch rm = (ResourceMatch)var8.next();
                  if (this.stringEqual.equals(rm.getMatchId())) {
                     ResourceAttributeDesignator rad = rm.getDesignator();
                     if (rad != null && this.roleId.equals(rad.getAttributeId()) && this.stringType.equals(rad.getDataType())) {
                        if (role != null) {
                           throw new MultipleRoleTargetException("Multiple role matches present in target; use alternate inspection method");
                        }

                        role = ((StringAttribute)rm.getAttributeValue().getValue()).getValue();
                     }
                  }
               }
            }
         }
      }

      return role;
   }

   public Target generateTarget(String role) throws DocumentParseException {
      return new Target(new Resources(Collections.singletonList(new Resource(Collections.singletonList(new ResourceMatch(this.stringEqual, new AttributeValue(new StringAttribute(role)), new ResourceAttributeDesignator(this.roleId, this.stringType, true)))))), new Actions(Collections.singletonList(new Action(Collections.singletonList(this.enableRoleActionMatch)))));
   }

   public ActionMatch generateActionMatch() {
      return this.enableRoleActionMatch;
   }

   public ResourceMatch generateResourceMatch(String role) {
      return new ResourceMatch(this.stringEqual, new AttributeValue(new StringAttribute(role)), new ResourceAttributeDesignator(this.roleId, this.stringType, true));
   }

   public KnownMatch generateRoleAssignmentMatch(String roleName) {
      return new KnownResourceMatch(this.stringEqual, this.roleId, this.stringType, new StringAttribute(roleName), true);
   }

   public KnownMatch getEnableRoleMatch() {
      return this.enableRoleMatch;
   }
}

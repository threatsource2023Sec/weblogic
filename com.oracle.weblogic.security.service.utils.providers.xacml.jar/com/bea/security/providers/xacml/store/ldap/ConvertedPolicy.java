package com.bea.security.providers.xacml.store.ldap;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.security.providers.xacml.entitlement.EntitlementConverter;

public class ConvertedPolicy {
   private Policy pol = null;
   private String resourceId = null;
   private String eexpr = null;
   private String roleName = null;
   private String roleAux = null;
   private EntitlementConverter converter = null;

   public ConvertedPolicy(String resourceId, String eexpr) throws EntlConversionException {
      this.resourceId = resourceId;
      this.eexpr = eexpr;

      try {
         this.converter = new EntitlementConverter(new ConverterLogger());
         this.pol = this.converter.convertResourceExpression(resourceId, eexpr);
      } catch (DocumentParseException var4) {
         throw new EntlConversionException(var4);
      } catch (URISyntaxException var5) {
         throw new EntlConversionException(var5);
      }
   }

   public ConvertedPolicy(String resourceId, String eexpr, String roleName, String roleAux) throws EntlConversionException {
      this.resourceId = resourceId;
      this.eexpr = eexpr;
      this.roleName = roleName;
      this.roleAux = roleAux;

      try {
         this.converter = new EntitlementConverter(new ConverterLogger());
         this.pol = this.converter.convertRoleExpression(resourceId, roleName, eexpr, roleAux);
      } catch (DocumentParseException var6) {
         throw new EntlConversionException(var6);
      } catch (URISyntaxException var7) {
         throw new EntlConversionException(var7);
      }
   }

   Policy getPolicy() {
      return this.pol;
   }

   String getResourceID() {
      return this.resourceId;
   }

   String getEexpr() {
      return this.eexpr;
   }

   String getRoleName() {
      return this.roleName;
   }

   String getRoleAux() {
      return this.roleAux;
   }
}

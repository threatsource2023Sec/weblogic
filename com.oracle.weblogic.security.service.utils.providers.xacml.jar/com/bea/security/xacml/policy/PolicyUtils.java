package com.bea.security.xacml.policy;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.security.xacml.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;

public class PolicyUtils {
   private PolicyUtils() {
   }

   public static AbstractPolicy read(AttributeRegistry attrReg, InputStream data, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         return com.bea.common.security.xacml.PolicyUtils.read(attrReg, data, dbFactory);
      } catch (com.bea.common.security.xacml.IOException var4) {
         throw new IOException(var4);
      }
   }

   public static AbstractPolicy read(AttributeRegistry attrReg, String data, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         return com.bea.common.security.xacml.PolicyUtils.read(attrReg, data, dbFactory);
      } catch (com.bea.common.security.xacml.IOException var4) {
         throw new IOException(var4);
      }
   }

   public static Policy readPolicy(AttributeRegistry attrReg, InputStream policy, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         return com.bea.common.security.xacml.PolicyUtils.readPolicy(attrReg, policy, dbFactory);
      } catch (com.bea.common.security.xacml.IOException var4) {
         throw new IOException(var4);
      }
   }

   public static Policy readPolicy(AttributeRegistry attrReg, String policy, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         return com.bea.common.security.xacml.PolicyUtils.readPolicy(attrReg, policy, dbFactory);
      } catch (com.bea.common.security.xacml.IOException var4) {
         throw new IOException(var4);
      }
   }

   public static PolicySet readPolicySet(AttributeRegistry attrReg, InputStream policySet, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException {
      return com.bea.common.security.xacml.PolicyUtils.readPolicySet(attrReg, policySet, dbFactory);
   }

   public static PolicySet readPolicySet(AttributeRegistry attrReg, String policySet, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException {
      return com.bea.common.security.xacml.PolicyUtils.readPolicySet(attrReg, policySet, dbFactory);
   }
}

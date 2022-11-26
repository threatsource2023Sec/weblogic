package com.bea.security.providers.xacml.store.ldap;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.Target;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class XACMLPolicyWriter implements ConvertedEntlWriter {
   private static final String SET_ID = "urn:bea:xacml:2.0:export-data:container-policy-set";
   private static final String DENY_OVERRIDES = "urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides";
   private URI SET_ID_URI;
   private URI DENY_OVERRIDES_URI;
   private static final String METADATA_XMLNS = "urn:bea:xacml:2.0:export-data:meta-data";
   private static final String METADATA_XML_START = "<![CDATA[<MetaData xmlns=\"urn:bea:xacml:2.0:export-data:meta-data\">";
   private static final String METADATA_XML_END = "</MetaData>]]>";

   public void writeConvertedEntlPolicies(List convertedPolicies, OutputStream out, boolean isStandard) throws EntlConversionException {
      if (convertedPolicies != null && !convertedPolicies.isEmpty()) {
         try {
            this.SET_ID_URI = new URI("urn:bea:xacml:2.0:export-data:container-policy-set");
            this.DENY_OVERRIDES_URI = new URI("urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides");
         } catch (URISyntaxException var9) {
            throw new EntlConversionException(var9);
         }

         List members = new ArrayList();
         Iterator iter = convertedPolicies.iterator();

         while(iter.hasNext()) {
            ConvertedPolicy cpol = (ConvertedPolicy)iter.next();
            members.add(cpol.getPolicy());
         }

         PrintStream pw = new PrintStream(out);
         String des = generatePolicyDescription(members, isStandard);
         PolicySet container = new PolicySet(this.SET_ID_URI, (Target)null, this.DENY_OVERRIDES_URI, des, members);
         container.encode(pw);
      }
   }

   private static String generatePolicyDescription(List policies, boolean isStandard) {
      if (policies != null && !policies.isEmpty()) {
         StringBuffer sb = new StringBuffer();
         sb.append("<![CDATA[<MetaData xmlns=\"urn:bea:xacml:2.0:export-data:meta-data\">");
         Iterator iter = policies.iterator();

         while(iter.hasNext()) {
            sb.append("<WLSMetaData PolicyId=\"");
            sb.append(((Policy)iter.next()).getId());
            sb.append("\" Status=\"" + (isStandard ? "0" : "3") + "\">");
            sb.append("</WLSMetaData>");
         }

         sb.append("</MetaData>]]>");
         return sb.toString();
      } else {
         return "";
      }
   }
}

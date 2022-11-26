package com.bea.security.providers.xacml.store.ldap;

import com.bea.common.security.utils.encoders.BASE64Encoder;
import com.bea.security.xacml.cache.resource.Escaping;
import com.bea.security.xacml.cache.resource.ResourcePolicyIdUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class XACMLLdiftWriter implements ConvertedEntlWriter {
   private static final Escaping escaper = ResourcePolicyIdUtil.getEscaper();
   private static final BASE64Encoder b64Encoder = new BASE64Encoder();
   private static final String ROLE_TYPE = "XACMLRole";
   private static final String AUTHORIZATION_TYPE = "XACMLAuthorization";
   private static final String POLICIES_TYPE = "Policies";
   private static final String POLICYSETS_TYPE = "PolicySets";
   private static final String[] TYPES = new String[]{"XACMLRole", "XACMLAuthorization"};
   private static final String[][] SUBTYPES = new String[][]{{"Policies", "PolicySets"}, {"Policies", "PolicySets"}};

   private static String uuEncode(String eaux) throws UnsupportedEncodingException {
      String eauxStr = eaux == null ? "" : eaux;
      return b64Encoder.encodeBuffer(eauxStr.getBytes("UTF8"));
   }

   public void writeConvertedEntlPolicies(List convertedPolicies, OutputStream out, boolean isStandard) throws EntlConversionException {
      if (convertedPolicies != null && !convertedPolicies.isEmpty()) {
         OutputStreamWriter writer = new OutputStreamWriter(out);

         try {
            writeHeader(writer);
            Iterator iter = convertedPolicies.iterator();

            while(iter.hasNext()) {
               ConvertedPolicy cpol = (ConvertedPolicy)iter.next();
               String roleName = cpol.getRoleName();
               String resourceId = cpol.getResourceID();
               boolean isRole = roleName != null && !roleName.equals("");
               String en = escaper.escapeString(cpol.getPolicy().getId().toString());
               writer.write("\n");
               writer.write("dn: cn=" + en + "+xacmlVersion=1.0,ou=" + "Policies" + ",ou=" + (isRole ? "XACMLRole" : "XACMLAuthorization") + ",ou=@realm@,dc=@domain@\n");
               writer.write("objectclass: top\n");
               writer.write("objectclass: xacmlEntry\n");
               writer.write("objectclass: " + (isRole ? "xacmlRoleAssignmentPolicy" : "xacmlAuthorizationPolicy") + "\n");
               if (resourceId != null && resourceId.length() > 0) {
                  writer.write("objectclass: xacmlResourceScoping\n");
               }

               writer.write("cn: " + en + "\n");
               if (resourceId != null && resourceId.length() > 0) {
                  writer.write("xacmlResourceScope: " + escaper.escapeString(resourceId) + "\n");
               }

               writer.write("xacmlVersion: 1.0\n");
               writer.write("xacmlStatus: " + (isStandard ? "0" : "3") + "\n");
               writer.write("xacmlDocument:: " + uuEncode(cpol.getPolicy().toString()) + "\n");
               if (isRole) {
                  writer.write("xacmlRole: " + escaper.escapeString(roleName) + "\n");
               }
            }

            writer.flush();
         } catch (UnsupportedEncodingException var19) {
            throw new EntlConversionException(var19);
         } catch (IOException var20) {
            throw new EntlConversionException(var20);
         } finally {
            try {
               if (writer != null) {
                  writer.close();
               }
            } catch (IOException var18) {
            }

         }
      }
   }

   private static void writeHeader(Writer out) throws IOException {
      out.write("dn: dc=@domain@\n");
      out.write("objectclass: top\n");
      out.write("objectclass: domain\n");
      out.write("dc: @domain@\n");
      out.write("\n");
      out.write("dn: ou=@realm@,dc=@domain@\n");
      out.write("objectclass: top\n");
      out.write("objectclass: organizationalUnit\n");
      out.write("ou: @realm@\n");

      for(int i = 0; i < TYPES.length; ++i) {
         out.write("\n");
         out.write("dn: ou=" + TYPES[i] + ",ou=@realm@,dc=@domain@\n");
         out.write("objectclass: top\n");
         out.write("objectclass: organizationalUnit\n");
         out.write("ou: " + TYPES[i] + "\n");
         if (SUBTYPES != null && SUBTYPES.length > i && SUBTYPES[i] != null) {
            for(int j = 0; j < SUBTYPES[i].length; ++j) {
               out.write("\n");
               out.write("dn: ou=" + SUBTYPES[i][j] + ",ou=" + TYPES[i] + ",ou=@realm@,dc=@domain@\n");
               out.write("objectclass: top\n");
               out.write("objectclass: organizationalUnit\n");
               out.write("ou: " + SUBTYPES[i][j] + "\n");
            }
         }
      }

   }
}

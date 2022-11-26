package weblogic.servlet.internal.dd.glassfish;

import java.util.Iterator;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.commons.lang.StringUtils;
import weblogic.j2ee.descriptor.wl.SecurityRoleAssignmentBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.HTTPLogger;
import weblogic.utils.collections.ArraySet;

public class SecurityRoleMappingTagParser extends BaseGlassfishTagParser {
   public void parse(XMLStreamReader reader, WeblogicWebAppBean weblogicWebAppBean) throws XMLStreamException {
      String roleName = null;
      Set principalList = new ArraySet();

      int event;
      do {
         event = reader.next();
         if (event == 1) {
            String TagName = reader.getLocalName();
            if ("role-name".equals(TagName)) {
               roleName = this.parseTagData(reader);
            } else if ("principal-name".equals(TagName) || "group-name".equals(TagName)) {
               principalList.add(this.parseTagData(reader));
            }
         }
      } while(reader.hasNext() && !this.isEndTag(event, reader, "security-role-mapping"));

      if (StringUtils.isNotEmpty(roleName) && principalList != null && principalList.size() > 0) {
         SecurityRoleAssignmentBean securityRoleAssignment = weblogicWebAppBean.createSecurityRoleAssignment();
         securityRoleAssignment.setRoleName(roleName);
         Iterator var7 = principalList.iterator();

         while(var7.hasNext()) {
            String principalName = (String)var7.next();
            securityRoleAssignment.addPrincipalName(principalName);
         }

         HTTPLogger.logGlassfishDescriptorParsed("security-role-mapping");
      }

   }
}

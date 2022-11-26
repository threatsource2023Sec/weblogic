package weblogic.jms.tools;

import java.util.List;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.WebLogicToolsModuleFactory;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public class JMSModuleFactory implements WebLogicToolsModuleFactory {
   public Boolean claim(WeblogicModuleBean m) {
      return m.getType().equals("JMS") ? true : null;
   }

   public Boolean claim(WeblogicModuleBean m, List allClaimants) {
      return this.claim(m);
   }

   public ToolsModule create(WeblogicModuleBean m) {
      String uri = m.getPath();
      JMSModule jmsModule = new JMSModule(uri, (String)null, m.getName());
      return jmsModule;
   }
}

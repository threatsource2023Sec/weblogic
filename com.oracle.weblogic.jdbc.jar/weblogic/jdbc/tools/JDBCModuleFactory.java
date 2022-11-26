package weblogic.jdbc.tools;

import java.util.List;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.compiler.WebLogicToolsModuleFactory;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;

public class JDBCModuleFactory implements WebLogicToolsModuleFactory {
   public Boolean claim(WeblogicModuleBean m) {
      return m.getType().equals("JDBC") ? true : null;
   }

   public Boolean claim(WeblogicModuleBean m, List allClaimants) {
      return this.claim(m);
   }

   public ToolsModule create(WeblogicModuleBean m) {
      String uri = m.getPath();
      JDBCModule jdbcModule = new JDBCModule(uri, (String)null, m.getName());
      return jdbcModule;
   }
}

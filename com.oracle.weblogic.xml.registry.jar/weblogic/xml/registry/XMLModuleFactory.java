package weblogic.xml.registry;

import weblogic.application.Module;
import weblogic.application.ModuleException;
import weblogic.application.WebLogicApplicationModuleFactory;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.XmlBean;

public final class XMLModuleFactory implements WebLogicApplicationModuleFactory {
   public Module[] createModule(WeblogicApplicationBean wldd) throws ModuleException {
      XmlBean xmlDD = wldd.getXml();
      return xmlDD != null ? new Module[]{new XMLModule(xmlDD)} : null;
   }
}

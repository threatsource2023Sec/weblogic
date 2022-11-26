package org.jboss.weld.module.web.el;

import javax.el.ELContextEvent;
import javax.el.ELContextListener;

public class WeldELContextListener implements ELContextListener {
   public void contextCreated(ELContextEvent contextEvent) {
      ELCreationalContextStack.addToContext(contextEvent.getELContext());
   }
}

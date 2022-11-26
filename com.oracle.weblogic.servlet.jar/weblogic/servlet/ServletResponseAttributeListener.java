package weblogic.servlet;

import java.util.EventListener;

public interface ServletResponseAttributeListener extends EventListener {
   void attributeChanged(ServletResponseAttributeEvent var1);
}

package weblogic.servlet;

import java.util.EventObject;
import javax.servlet.ServletResponse;

public class ServletResponseAttributeEvent extends EventObject {
   public static final String ATTR_ENCODING = "ENCODING";
   private String name;
   private Object value;

   public ServletResponseAttributeEvent(ServletResponse response, String name, Object value) {
      super(response);
      this.name = name;
      this.value = value;
   }

   public ServletResponse getResponse() {
      return (ServletResponse)this.getSource();
   }

   public String getName() {
      return this.name;
   }

   public Object getValue() {
      return this.value;
   }
}

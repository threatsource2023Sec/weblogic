package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONObject;

public class BeanUpdater extends AbstractBeanEditor {
   public BeanUpdater(InvocationContext ic) throws Exception {
      super(ic);
   }

   protected boolean _doWork(HttpServletRequest request, JSONObject values) throws Exception {
      BeanUtils.setBeanProperties(this.invocationContext(), values);
      return true;
   }
}

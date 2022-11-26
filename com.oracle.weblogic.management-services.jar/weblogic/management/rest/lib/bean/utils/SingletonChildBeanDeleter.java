package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONObject;

public class SingletonChildBeanDeleter extends AbstractBeanEditor {
   ContainedBeanType type;

   public SingletonChildBeanDeleter(InvocationContext ic, ContainedBeanType type) throws Exception {
      super(ic);
      this.type = type;
   }

   protected boolean _doWork(HttpServletRequest request, JSONObject item) throws Exception {
      BeanUtils.deleteSingletonChildBean(this.invocationContext(), PathUtils.getParent(this.invocationContext()), this.type);
      return true;
   }
}

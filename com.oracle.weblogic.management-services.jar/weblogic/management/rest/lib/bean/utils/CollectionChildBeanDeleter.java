package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONObject;

public class CollectionChildBeanDeleter extends AbstractBeanEditor {
   ContainedBeansType type;

   public CollectionChildBeanDeleter(InvocationContext ic, ContainedBeansType type) throws Exception {
      super(ic);
      this.type = type;
   }

   protected boolean _doWork(HttpServletRequest request, JSONObject item) throws Exception {
      BeanUtils.deleteCollectionChildBean(this.invocationContext(), PathUtils.getParent(this.invocationContext()), this.type);
      return true;
   }
}

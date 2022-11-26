package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.debug.DebugLogger;

public abstract class ChildBeanCreator extends AbstractBeanEditor implements BeanCreator {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(ChildBeanCreator.class);
   private ContainedBeanAttributeType type;
   private Object newBean;
   private JSONObject values;

   protected ChildBeanCreator(InvocationContext ic, ContainedBeanAttributeType type) throws Exception {
      super(ic);
      this.type = type;
   }

   protected boolean _doWork(HttpServletRequest request, JSONObject values) throws Exception {
      this.values = values;
      this.newBean = this.createNewBean();
      boolean success = false;

      try {
         this.updateNewBean();
         success = true;
      } finally {
         if (!success) {
            try {
               this.deleteNewBean();
            } catch (Throwable var10) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("REST problem cleaning up after failed creation", var10);
               }
            }
         }

      }

      return true;
   }

   protected Object createNewBean() throws Exception {
      return InvokeUtils.create(this.invocationContext(), this.getType(), this.getValues());
   }

   protected void updateNewBean() throws Exception {
      BeanUtils.setBeanProperties(this.invocationContext().clone(this.getNewBean()), this.getValues());
   }

   protected abstract void deleteNewBean() throws Exception;

   protected ContainedBeanAttributeType getType() {
      return this.type;
   }

   protected JSONObject getValues() {
      return this.values;
   }

   public Object getNewBean() {
      return this.newBean;
   }
}

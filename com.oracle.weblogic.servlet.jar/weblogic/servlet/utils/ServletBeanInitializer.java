package weblogic.servlet.utils;

import javax.servlet.ServletRequest;
import weblogic.utils.bean.BeanInitializer;
import weblogic.utils.bean.ConversionException;

public final class ServletBeanInitializer extends BeanInitializer {
   public final void initializeBean(Object bean, ServletRequest req) throws ConversionException {
      this.initializeBean(bean, new BeanInitializer.DataRetrieverHelper(req) {
         public Object get(String key) {
            return ((ServletRequest)this.data).getParameter(key);
         }
      });
   }
}

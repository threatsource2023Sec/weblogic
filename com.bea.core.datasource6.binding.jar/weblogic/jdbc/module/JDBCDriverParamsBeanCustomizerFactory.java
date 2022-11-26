package weblogic.jdbc.module;

import weblogic.descriptor.beangen.Customizer;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.j2ee.descriptor.wl.JDBCDriverParamsBean;

public class JDBCDriverParamsBeanCustomizerFactory implements CustomizerFactory {
   public Customizer createCustomizer(Object bean) {
      JDBCDriverParamsBean beanForUse = (JDBCDriverParamsBean)bean;
      return new JDBCDriverParamsCustomizer(beanForUse);
   }
}

package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.management.configuration.WebAppContainerMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class WebAppContainer extends ConfigurationMBeanCustomizer {
   private static final long serialVersionUID = -1873044084609391595L;

   public WebAppContainer(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void setMaxRequestParamterCount(int limit) throws InvalidAttributeValueException {
      if (!((WebAppContainerMBean)this.getMbean()).isMaxRequestParameterCountSet() && limit != 10000) {
         ((WebAppContainerMBean)this.getMbean()).setMaxRequestParameterCount(limit);
         if (((WebAppContainerMBean)this.getMbean()).isMaxRequestParamterCountSet()) {
            ((WebAppContainerMBean)this.getMbean()).unSet("MaxRequestParamterCount");
         }
      }

   }

   public int getMaxRequestParamterCount() {
      return ((WebAppContainerMBean)this.getMbean()).getMaxRequestParameterCount();
   }
}

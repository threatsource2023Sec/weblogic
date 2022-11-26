package weblogic.persistence.spi;

import java.net.URL;
import weblogic.descriptor.DescriptorBean;

public final class BeanInfo {
   public final Object bean;
   public final URL rootUrl;
   public final URL jarParentUrl;
   public final DescriptorBean rootBean;

   public BeanInfo(Object bean, URL rootUrl, URL jarParentUrl, DescriptorBean rootBean) {
      this.bean = bean;
      this.rootUrl = rootUrl;
      this.jarParentUrl = jarParentUrl;
      this.rootBean = rootBean;
   }
}

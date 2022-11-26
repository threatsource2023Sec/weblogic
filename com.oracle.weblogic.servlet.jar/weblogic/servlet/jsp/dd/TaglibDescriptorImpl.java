package weblogic.servlet.jsp.dd;

import javax.servlet.descriptor.TaglibDescriptor;
import weblogic.j2ee.descriptor.TagLibBean;

public class TaglibDescriptorImpl implements TaglibDescriptor {
   private TagLibBean bean;

   public TaglibDescriptorImpl(TagLibBean bean) {
      this.bean = bean;
   }

   public String getTaglibLocation() {
      return this.bean.getTagLibLocation();
   }

   public String getTaglibURI() {
      return this.bean.getTagLibUri();
   }
}

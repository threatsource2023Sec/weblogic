package weblogic.application;

import weblogic.j2ee.descriptor.wl.CdiDescriptorBean;

public interface PojoAnnotationSupportingModule extends Extensible {
   CdiDescriptorBean getCdiDescriptorBean();

   boolean isMetadataComplete();
}

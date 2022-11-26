package weblogic.management.extension;

import weblogic.application.ModuleException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.DescriptorBean;

public interface Resource extends BeanUpdateListener {
   void initialize(DescriptorBean var1) throws ModuleException;

   void prepare() throws ModuleException;

   void activate() throws ModuleException;

   void unprepare() throws ModuleException;

   void deactivate() throws ModuleException;

   void remove() throws ModuleException;
}

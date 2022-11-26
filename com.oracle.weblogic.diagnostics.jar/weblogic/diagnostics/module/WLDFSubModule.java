package weblogic.diagnostics.module;

import weblogic.application.ApplicationContext;
import weblogic.descriptor.DescriptorDiff;
import weblogic.diagnostics.descriptor.WLDFResourceBean;

public interface WLDFSubModule {
   void init(String var1, ApplicationContext var2, WLDFResourceBean var3) throws WLDFModuleException;

   void prepare() throws WLDFModuleException;

   void activate() throws WLDFModuleException;

   void deactivate() throws WLDFModuleException;

   void unprepare() throws WLDFModuleException;

   void destroy() throws WLDFModuleException;

   void prepareUpdate(WLDFResourceBean var1, DescriptorDiff var2) throws WLDFModuleException;

   void activateUpdate(WLDFResourceBean var1, DescriptorDiff var2) throws WLDFModuleException;

   void rollbackUpdate(WLDFResourceBean var1, DescriptorDiff var2);
}

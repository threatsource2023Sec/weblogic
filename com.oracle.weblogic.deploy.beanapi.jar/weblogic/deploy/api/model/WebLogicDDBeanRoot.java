package weblogic.deploy.api.model;

import java.io.IOException;
import javax.enterprise.deploy.model.DDBeanRoot;
import weblogic.deploy.api.shared.PlanConstants;
import weblogic.descriptor.DescriptorBean;

public interface WebLogicDDBeanRoot extends DDBeanRoot, WebLogicDDBean, PlanConstants {
   DescriptorBean getDescriptorBean() throws IOException;

   void export(int var1) throws IllegalArgumentException;

   void export(DescriptorBean var1, String[] var2) throws IllegalArgumentException;

   boolean isInitialized();

   String getFilename();

   boolean hasDBean();

   boolean isSchemaBased();
}

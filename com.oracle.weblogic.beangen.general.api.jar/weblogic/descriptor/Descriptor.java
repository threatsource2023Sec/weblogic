package weblogic.descriptor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface Descriptor {
   DescriptorBean getRootBean();

   void addUpdateListener(DescriptorUpdateListener var1);

   void removeUpdateListener(DescriptorUpdateListener var1);

   void validate() throws DescriptorValidateException;

   void prepareUpdate(Descriptor var1) throws DescriptorUpdateRejectedException;

   void prepareUpdate(Descriptor var1, boolean var2) throws DescriptorUpdateRejectedException;

   DescriptorDiff prepareUpdateDiff(Descriptor var1, boolean var2) throws DescriptorUpdateRejectedException;

   void activateUpdate() throws DescriptorUpdateFailedException, IllegalStateException;

   void activateUpdate(DescriptorPreNotifyProcessor var1) throws DescriptorUpdateFailedException, IllegalStateException;

   void applyDiff(DescriptorDiff var1) throws DescriptorUpdateFailedException, IllegalStateException;

   void rollbackUpdate();

   DescriptorDiff computeDiff(Descriptor var1);

   boolean isEditable();

   boolean isModified();

   Object clone();

   List getResolvedReferences(DescriptorBean var1);

   boolean isUnderConstruction();

   /** @deprecated */
   @Deprecated
   void toXML(OutputStream var1) throws IOException;

   void setProductionMode(boolean var1);

   void setSecureMode(boolean var1);

   String getOriginalVersionInfo();

   void setOriginalVersionInfo(String var1);
}

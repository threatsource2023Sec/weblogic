package weblogic.j2ee.descriptor.wl;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;

public class WeblogicRelationshipRoleBeanDConfigBeanInfo extends SimpleBeanInfo {
   BeanDescriptor bd = new BeanDescriptor(WeblogicRelationshipRoleBeanDConfig.class);
   static PropertyDescriptor[] pds = null;

   public BeanDescriptor getBeanDescriptor() {
      return this.bd;
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      if (pds != null) {
         return pds;
      } else {
         List plist = new ArrayList();

         try {
            PropertyDescriptor pd = new PropertyDescriptor("RelationshipRoleName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRelationshipRoleBeanDConfig"), "getRelationshipRoleName", "setRelationshipRoleName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", true);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("GroupName", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRelationshipRoleBeanDConfig"), "getGroupName", "setGroupName");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("RelationshipRoleMap", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRelationshipRoleBeanDConfig"), "getRelationshipRoleMap", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("DbCascadeDelete", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRelationshipRoleBeanDConfig"), "getDbCascadeDelete", (String)null);
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("EnableQueryCaching", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRelationshipRoleBeanDConfig"), "getEnableQueryCaching", "setEnableQueryCaching");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pd = new PropertyDescriptor("Id", Class.forName("weblogic.j2ee.descriptor.wl.WeblogicRelationshipRoleBeanDConfig"), "getId", "setId");
            pd.setValue("dependency", false);
            pd.setValue("declaration", false);
            pd.setValue("configurable", false);
            pd.setValue("key", false);
            pd.setValue("dynamic", false);
            plist.add(pd);
            pds = (PropertyDescriptor[])((PropertyDescriptor[])plist.toArray(new PropertyDescriptor[0]));
            return pds;
         } catch (Throwable var4) {
            var4.printStackTrace();
            throw new AssertionError("Failed to create PropertyDescriptors for WeblogicRelationshipRoleBeanDConfigBeanInfo");
         }
      }
   }
}

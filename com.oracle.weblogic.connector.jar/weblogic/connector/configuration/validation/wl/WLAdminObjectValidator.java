package weblogic.connector.configuration.validation.wl;

import java.util.List;
import weblogic.connector.configuration.validation.ValidationContext;
import weblogic.connector.external.PropSetterTable;
import weblogic.j2ee.descriptor.wl.AdminObjectGroupBean;
import weblogic.j2ee.descriptor.wl.AdminObjectInstanceBean;
import weblogic.j2ee.descriptor.wl.AdminObjectsBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertiesBean;
import weblogic.j2ee.descriptor.wl.ConfigPropertyBean;

class WLAdminObjectValidator extends DefaultWLRAValidator {
   private final AdminObjectsBean adminObjs;
   private final WLValidationUtils wlValidationUtils;

   WLAdminObjectValidator(ValidationContext context, AdminObjectsBean adminObjs, WLValidationUtils wlValidationUtils) {
      super(context);

      assert adminObjs != null : "should not create WLAdminObjectValidator if adminObjs is null";

      this.adminObjs = adminObjs;
      this.wlValidationUtils = wlValidationUtils;
   }

   public int order() {
      return 130;
   }

   public void doValidate() {
      ConfigPropertiesBean defaultProperties = this.adminObjs.getDefaultProperties();
      this.validateGlobalDefaultProps("Admin Objects Group", getComposedKeyOfAllBeans(this.adminObjs), "<admin-objects><default-properties>", defaultProperties);
      AdminObjectGroupBean[] adminGroups = this.adminObjs.getAdminObjectGroups();
      if (adminGroups != null) {
         AdminObjectGroupBean[] var3 = adminGroups;
         int var4 = adminGroups.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            AdminObjectGroupBean group = var3[var5];
            String wlAdminInterface = group.getAdminObjectInterface();
            String wlAdminClass = group.getAdminObjectClass();
            if (wlAdminClass == null) {
               List foundObjectClasses = this.wlValidationUtils.findMatchingAdminInterfaceInRA(this.raConnectorBean, wlAdminInterface);
               if (foundObjectClasses.size() == 0) {
                  this.errorInGroup(group, fmt.NO_MATCHING_ADMIN_INTERFACE(wlAdminInterface));
                  continue;
               }

               if (foundObjectClasses.size() != 1) {
                  this.errorInGroup(group, fmt.adminObjectInterfaceNotUnique(wlAdminInterface));
                  continue;
               }

               wlAdminClass = (String)foundObjectClasses.get(0);
            } else if (!this.wlValidationUtils.hasMatchingAdminInterfaceInRA(this.raConnectorBean, wlAdminInterface, wlAdminClass)) {
               this.errorInGroup(group, fmt.noMatchingAdminInterfaceAndClass(wlAdminInterface, wlAdminClass));
            }

            ConfigPropertiesBean groupProperties = group.getDefaultProperties();
            PropSetterTable groupSTable = this.raValidationInfo.getAdminPropSetterTable(wlAdminInterface, wlAdminClass);
            this.validateWLProps("Admin Objects Group", getComposedKeyOfGroup(group), "<admin-objects><admin-object-group>[admin-object-interface = " + wlAdminInterface + "]", groupProperties, groupSTable, wlAdminInterface, wlAdminClass);
            AdminObjectInstanceBean[] instances = group.getAdminObjectInstances();
            if (instances != null) {
               AdminObjectInstanceBean[] var12 = instances;
               int var13 = instances.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  AdminObjectInstanceBean adminInstance = var12[var14];
                  ConfigPropertiesBean adminProps = adminInstance.getProperties();
                  this.validateWLProps("Admin Objects Group", getComposedKeyOfGroup(group), "<admin-objects><admin-object-group>[admin-object-interface = " + wlAdminInterface + "]<admin-object-instance>[jndi-name = " + adminInstance.getJNDIName() + "]", adminProps, groupSTable, wlAdminInterface, wlAdminClass);
                  if (adminInstance.getJNDIName() == null || adminInstance.getJNDIName().trim().length() == 0) {
                     this.error("Admin Object", adminInstance.getJNDIName(), fmt.noJNDINameForAdminObjectInstance(wlAdminInterface, wlAdminClass));
                  }
               }
            }
         }
      }

   }

   void errorInGroup(AdminObjectGroupBean group, String message) {
      this.error("Admin Objects Group", getComposedKeyOfGroup(group), message);
   }

   static String getComposedKeyOfGroup(AdminObjectGroupBean group) {
      String key = "";
      AdminObjectInstanceBean[] instances = group.getAdminObjectInstances();
      if (instances != null) {
         AdminObjectInstanceBean[] var3 = instances;
         int var4 = instances.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            AdminObjectInstanceBean instance = var3[var5];
            key = key + instance.getJNDIName() + ";;;;";
         }
      }

      return key;
   }

   static String getComposedKeyOfAllBeans(AdminObjectsBean adminObjs) {
      String key = "";
      AdminObjectGroupBean[] groups = adminObjs.getAdminObjectGroups();
      if (groups != null) {
         AdminObjectGroupBean[] var3 = groups;
         int var4 = groups.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            AdminObjectGroupBean group = var3[var5];
            key = key + getComposedKeyOfGroup(group);
         }
      }

      return key;
   }

   protected void validateProp4AllSetterTable(String elementName, ConfigPropertyBean propBean) {
      AdminObjectGroupBean[] groups = this.adminObjs.getAdminObjectGroups();
      if (groups != null) {
         AdminObjectGroupBean[] var4 = groups;
         int var5 = groups.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            AdminObjectGroupBean gb = var4[var6];
            String wlAdminInterface = gb.getAdminObjectInterface();
            String wlAdminClass = gb.getAdminObjectClass();
            if (wlAdminClass == null) {
               List foundObjectClasses = this.wlValidationUtils.findMatchingAdminInterfaceInRA(this.raConnectorBean, wlAdminInterface);
               if (foundObjectClasses.size() != 1) {
                  continue;
               }

               wlAdminClass = (String)foundObjectClasses.get(0);
            }

            PropSetterTable table = this.raValidationInfo.getAdminPropSetterTable(wlAdminInterface, wlAdminClass);
            if (table != null) {
               this.validateWLConfigProperty("Admin Objects Group", getComposedKeyOfGroup(gb), elementName, table, propBean, wlAdminInterface, wlAdminClass);
            }
         }
      }

   }
}

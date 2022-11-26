package weblogic.connector.configuration.validation;

import java.util.Iterator;
import java.util.List;
import javax.resource.spi.ResourceAdapterAssociation;
import weblogic.connector.external.PropSetterTable;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.ConfigPropertyBean;

class AdminObjectValidator extends PropertyBaseValidator {
   private final AdminObjectBean selectedAdminObject;

   public AdminObjectValidator(ValidationContext context, AdminObjectBean adminObject) {
      super(context);
      this.selectedAdminObject = adminObject;
   }

   public void doValidate() {
      String source = "META-INF/ra.xml";
      String intfElement = "<adminobject-interface>";
      String classElement = "<adminobject-class>";
      String adminObjInterface = this.selectedAdminObject.getAdminObjectInterface();
      String adminObjClass = this.selectedAdminObject.getAdminObjectClass();
      if (this.isReadFromAnnotation("adminObject", new String[]{adminObjInterface, adminObjClass})) {
         source = "Class " + adminObjClass;
         intfElement = "@javax.resource.spi.AdministeredObject";
         classElement = "@javax.resource.spi.AdministeredObject";
      }

      Class adminInterface = this.checkClass(intfElement, source, adminObjInterface, (String[])null, (String[])null, false);
      String[] requiredInterfaces = new String[]{adminObjInterface};
      Class adminClass = this.checkClass(classElement, source, adminObjClass, requiredInterfaces, (String[])null, true);
      if (adminClass != null) {
         String[] suggestedInterfaces;
         if (ResourceAdapterAssociation.class.isAssignableFrom(adminClass)) {
            suggestedInterfaces = new String[]{"java.io.Serializable", "javax.resource.Referenceable"};
            this.checkClass(intfElement, source, adminInterface, suggestedInterfaces);
         } else {
            suggestedInterfaces = new String[]{"java.io.Serializable"};
         }

         this.checkClass(classElement, source, adminClass, suggestedInterfaces);
         List duplicatedProperties = this.checkForDuplicateProperty("General", "General", "<adminobject>", this.selectedAdminObject.getConfigProperties());
         Iterator var11 = duplicatedProperties.iterator();

         while(var11.hasNext()) {
            ConfigPropertyBean bean = (ConfigPropertyBean)var11.next();
            this.selectedAdminObject.destroyConfigProperty(bean);
         }

         PropSetterTable propSetterTable = this.getRAValidationInfo().getAdminPropSetterTable(adminObjInterface, adminObjClass);
         this.validateProperties("General", "General", adminClass, this.selectedAdminObject.getConfigProperties(), propSetterTable, "<adminobject>", source);
         this.validateAnnotations(adminClass, classElement, source);
      }

   }

   public int order() {
      return 50;
   }
}

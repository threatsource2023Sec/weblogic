package weblogic.connector.configuration.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.resource.spi.work.WorkContext;
import weblogic.connector.work.WorkContextValidator;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.IconBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;

public class ConnectorBeanValidator extends PropertyBaseValidator {
   private final ConnectorBean connector;
   private final ResourceAdapterBean resourceAdapter;
   private final IconBean[] icons;

   public ConnectorBeanValidator(ValidationContext context) {
      super(context);
      this.connector = context.getConnector();
      this.resourceAdapter = context.getConnector().getResourceAdapter();
      this.icons = context.getConnector().getIcons();
   }

   public void doValidate() {
      this.validateResourceAdapterBean();
      this.validateIcon();
      this.validateRequiredWorkContexts();
   }

   private void validateResourceAdapterBean() {
      if (this.resourceAdapter != null) {
         String[] requiredRAinterfaces = new String[]{"javax.resource.spi.ResourceAdapter"};
         String[] recommendedRAinterfaces = new String[0];
         String raClassName = this.resourceAdapter.getResourceAdapterClass();
         if (raClassName == null) {
            if (ValidationUtils.isInboundAdapter(this.resourceAdapter)) {
               this.warning(fmt.MISSING_RA_BEAN_FOR_INBOUND());
            }

         } else {
            String sourceFile = "META-INF/ra.xml";
            String adapterClassElement = "<resourceadapter-class>";
            String adapterElement = "<resourceadapter>";
            if (this.isReadFromAnnotation("resourceadapter-class", new String[0])) {
               sourceFile = "Class " + raClassName;
               adapterClassElement = "@javax.resource.spi.Connector";
            }

            Class raClass = this.checkClass(adapterClassElement, sourceFile, raClassName, requiredRAinterfaces, recommendedRAinterfaces, true, (Boolean)null);
            this.validateAnnotations(raClass, adapterClassElement, sourceFile);
            if (raClass != null) {
               List duplicatedProperties = this.checkForDuplicateProperty("General", "General", adapterElement, this.resourceAdapter.getConfigProperties());
               Iterator var9 = duplicatedProperties.iterator();

               while(var9.hasNext()) {
                  ConfigPropertyBean bean = (ConfigPropertyBean)var9.next();
                  this.resourceAdapter.destroyConfigProperty(bean);
               }

               this.validateProperties("General", "General", raClass, this.resourceAdapter.getConfigProperties(), this.getRAValidationInfo().getRAPropSetterTable(), adapterElement, sourceFile);
            }

         }
      }
   }

   private void validateIcon() {
      for(int i = 0; i < this.icons.length; ++i) {
         String lgIcon = this.icons[i].getLargeIcon();
         String smIcon = this.icons[i].getSmallIcon();
         boolean lgIconFound;
         if (smIcon != null) {
            lgIconFound = this.getClassLoader().getResource(smIcon) != null;
            if (!lgIconFound) {
               if (this.isReadFromAnnotation("smallicon", new String[]{smIcon})) {
                  this.warning(fmt.FILE_NOT_FOUND("class: " + this.resourceAdapter.getResourceAdapterClass(), "@javax.resource.spi.Connector.smallIcon()", smIcon));
               } else {
                  this.warning(fmt.FILE_NOT_FOUND("META-INF/ra.xml", "<small-icon>", smIcon));
               }
            }
         }

         if (lgIcon != null) {
            lgIconFound = this.getClassLoader().getResource(lgIcon) != null;
            if (!lgIconFound) {
               if (this.isReadFromAnnotation("largeicon", new String[]{lgIcon})) {
                  this.warning(fmt.FILE_NOT_FOUND("class: " + this.resourceAdapter.getResourceAdapterClass(), "@javax.resource.spi.Connector.largeIcon()", lgIcon));
               } else {
                  this.warning(fmt.FILE_NOT_FOUND("META-INF/ra.xml", "<large-icon>", lgIcon));
               }
            }
         }
      }

   }

   private void validateRequiredWorkContexts() {
      String[] names = this.connector.getRequiredWorkContexts();
      if (names != null && names.length != 0) {
         ArrayList list = new ArrayList();
         String[] var3 = this.connector.getRequiredWorkContexts();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String className = var3[var5];
            Class cls = null;

            try {
               cls = this.getClassLoader().loadClass(className);
            } catch (ClassNotFoundException var9) {
               this.error("General", "General", fmt.requiredWorkContexts_ClassNotFound(className, var9.toString()));
               continue;
            }

            if (!WorkContext.class.isAssignableFrom(cls)) {
               this.error("General", "General", fmt.requiredWorkContexts_NotImplementWorkContext(className));
            } else {
               list.add(cls);
            }
         }

         if (!list.isEmpty()) {
            Class[] classes = new Class[list.size()];
            classes = (Class[])list.toArray(classes);
            List unsupported = WorkContextValidator.checkRequiredWorkContexts(classes);
            Iterator var12 = unsupported.iterator();

            while(var12.hasNext()) {
               Class theclass = (Class)var12.next();
               this.error("General", "General", fmt.requiredWorkContexts_NotSupported(theclass.getName()));
            }

         }
      }
   }

   public int order() {
      return 20;
   }
}

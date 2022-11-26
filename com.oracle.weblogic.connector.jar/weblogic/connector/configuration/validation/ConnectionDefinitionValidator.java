package weblogic.connector.configuration.validation;

import java.util.Iterator;
import java.util.List;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;

class ConnectionDefinitionValidator extends PropertyBaseValidator {
   private final ConnectionDefinitionBean connDef;
   private static final String[] requiredMCFinterfaces = new String[]{"javax.resource.spi.ManagedConnectionFactory"};

   ConnectionDefinitionValidator(ValidationContext context, ConnectionDefinitionBean connDef) {
      super(context);
      this.connDef = connDef;
   }

   public void doValidate() {
      String[] requiredCFImplInterfaces = new String[]{"java.io.Serializable", "javax.resource.Referenceable", "<connectionfactory-interface>"};
      String[] requiredConnImplInterfaces = new String[]{"<connection-interface>"};
      String mcfClassName = this.connDef.getManagedConnectionFactoryClass();
      String connFactoryImplClassName = this.connDef.getConnectionFactoryImplClass();
      String connFactoryInterfaceClassName = this.connDef.getConnectionFactoryInterface();
      requiredCFImplInterfaces[2] = connFactoryInterfaceClassName;
      String connImplClassName = this.connDef.getConnectionImplClass();
      String connInterfaceClassName = this.connDef.getConnectionInterface();
      requiredConnImplInterfaces[0] = connInterfaceClassName;
      ConfigPropertyBean[] configProps = this.connDef.getConfigProperties();
      String source = "META-INF/ra.xml";
      String elementConnIntf = "<connection-interface>";
      String elementConnImpl = "<connection-impl-class>";
      String elementConnFactIntf = "<connectionfactory-interface>";
      String elementConnFactImpl = "<connectionfactory-impl-class>";
      String elementMCF = "<managedconnectionfactory-class>";
      if (this.isReadFromAnnotation("connectionDef", new String[]{connFactoryInterfaceClassName})) {
         source = "Class " + mcfClassName;
         elementConnIntf = "@javax.resource.spi.ConnectionDefinition.connection";
         elementConnImpl = "@javax.resource.spi.ConnectionDefinition.connectionImpl";
         elementConnFactIntf = "@javax.resource.spi.ConnectionDefinition.connectionFactory";
         elementConnFactImpl = "@javax.resource.spi.ConnectionDefinition.connectionFactoryImpl";
         elementMCF = "@javax.resource.spi.ConnectionDefinition";
      }

      this.checkClass(elementConnIntf, source, connInterfaceClassName, (String[])null, (String[])null, false);
      this.checkClass(elementConnImpl, source, connImplClassName, requiredConnImplInterfaces, (String[])null, false);
      Class mcfClass = this.checkClass(elementMCF, source, mcfClassName, requiredMCFinterfaces, new String[0], false, Boolean.TRUE);
      if (mcfClass != null) {
         this.checkClass(elementConnFactIntf, source, connFactoryInterfaceClassName, (String[])null, (String[])null, false);
         this.checkClass(elementConnFactImpl, source, connFactoryImplClassName, requiredCFImplInterfaces, (String[])null, false);
         List duplicatedProperties = this.checkForDuplicateProperty("General", "General", "<connection-definition>", configProps);
         Iterator var17 = duplicatedProperties.iterator();

         while(var17.hasNext()) {
            ConfigPropertyBean bean = (ConfigPropertyBean)var17.next();
            this.connDef.destroyConfigProperty(bean);
         }

         this.validateProperties("General", "General", mcfClass, this.connDef.getConfigProperties(), this.getRAValidationInfo().getConnectionFactoryPropSetterTable(connFactoryInterfaceClassName), "<connection-definition>", source);
         this.validateAnnotations(mcfClass, elementMCF, source);
      }

   }

   public int order() {
      return 30;
   }
}

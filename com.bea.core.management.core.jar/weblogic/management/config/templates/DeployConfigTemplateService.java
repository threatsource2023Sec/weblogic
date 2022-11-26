package weblogic.management.config.templates;

import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DeployConfigTemplateService {
   boolean requireEjbRefDConfig(DDBean var1, DConfigBean var2);

   void configureSecurity(DConfigBean var1);

   void configureWeblogicApplication(DConfigBean var1);

   void configureEntityDescriptor(DConfigBean var1);

   void configureMessageDrivenDescriptor(DConfigBean var1);

   void configureAdminObj(DConfigBean var1);
}

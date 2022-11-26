package weblogic.connector.extensions;

import javax.enterprise.inject.spi.BeanManager;
import javax.resource.ResourceException;
import javax.resource.spi.BootstrapContext;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public interface ExtendedBootstrapContext extends BootstrapContext {
   void setDiagnosticContextID(String var1);

   String getDiagnosticContextID();

   void setDyeBits(byte var1) throws ResourceException;

   byte getDyeBits() throws ResourceException;

   void complete();

   Validator getValidator();

   ValidatorFactory getValidatorFactory();

   BeanManager getBeanManager();
}

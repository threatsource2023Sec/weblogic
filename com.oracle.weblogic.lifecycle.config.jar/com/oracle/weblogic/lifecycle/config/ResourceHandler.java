package com.oracle.weblogic.lifecycle.config;

import javax.validation.ValidationException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ResourceHandler {
   boolean handles(Resource var1);

   void validate(Resource var1) throws ValidationException;

   void registerResource(Resource var1);

   void unregisterResource(Resource var1);

   void reconfigureResource(Resource var1);
}

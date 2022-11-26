package com.oracle.weblogic.lifecycle.config.database;

import java.util.Properties;
import javax.validation.ValidationException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ResourceHandler {
   boolean handles(String var1, String var2, Properties var3);

   void validate(String var1, String var2, Properties var3) throws ValidationException;

   void registerResource(ResourceConfigService var1);

   void unregisterResource(ResourceConfigService var1);

   void reconfigureResource(ResourceConfigService var1);
}

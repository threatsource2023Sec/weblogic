package com.bea.common.store.service.config;

import com.bea.common.store.service.StoreInitializationException;
import java.util.Properties;

public interface StoreServicePropertiesConfigurator {
   Properties convertStoreProperties(Properties var1, Properties var2, Properties var3) throws StoreInitializationException;
}

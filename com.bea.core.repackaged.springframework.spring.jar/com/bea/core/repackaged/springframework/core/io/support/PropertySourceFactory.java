package com.bea.core.repackaged.springframework.core.io.support;

import com.bea.core.repackaged.springframework.core.env.PropertySource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;

public interface PropertySourceFactory {
   PropertySource createPropertySource(@Nullable String var1, EncodedResource var2) throws IOException;
}

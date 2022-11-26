package com.bea.core.repackaged.springframework.core.io.support;

import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;
import java.io.IOException;

public interface ResourcePatternResolver extends ResourceLoader {
   String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

   Resource[] getResources(String var1) throws IOException;
}

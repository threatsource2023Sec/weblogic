package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.beans.factory.Aware;
import com.bea.core.repackaged.springframework.core.io.ResourceLoader;

public interface ResourceLoaderAware extends Aware {
   void setResourceLoader(ResourceLoader var1);
}

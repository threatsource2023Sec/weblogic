package com.bea.core.repackaged.springframework.jca.context;

import com.bea.core.repackaged.springframework.beans.factory.Aware;
import javax.resource.spi.BootstrapContext;

public interface BootstrapContextAware extends Aware {
   void setBootstrapContext(BootstrapContext var1);
}

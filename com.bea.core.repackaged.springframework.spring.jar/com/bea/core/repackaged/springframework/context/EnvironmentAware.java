package com.bea.core.repackaged.springframework.context;

import com.bea.core.repackaged.springframework.beans.factory.Aware;
import com.bea.core.repackaged.springframework.core.env.Environment;

public interface EnvironmentAware extends Aware {
   void setEnvironment(Environment var1);
}

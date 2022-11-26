package com.sun.faces.util.cdi11;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;

public interface CDIUtil {
   Bean createHelperBean(BeanManager var1, Class var2);
}

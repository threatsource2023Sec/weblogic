package org.jboss.weld.bean;

import java.util.List;
import javax.enterprise.inject.spi.Bean;

public interface DecorableBean extends Bean {
   List getDecorators();
}

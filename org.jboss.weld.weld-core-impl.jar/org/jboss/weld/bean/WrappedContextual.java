package org.jboss.weld.bean;

import javax.enterprise.context.spi.Contextual;

public interface WrappedContextual extends Contextual {
   Contextual delegate();
}

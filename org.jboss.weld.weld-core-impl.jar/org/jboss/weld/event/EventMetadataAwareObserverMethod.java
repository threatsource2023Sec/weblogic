package org.jboss.weld.event;

import javax.enterprise.inject.spi.ObserverMethod;

public interface EventMetadataAwareObserverMethod extends ObserverMethod {
   boolean isEventMetadataRequired();
}

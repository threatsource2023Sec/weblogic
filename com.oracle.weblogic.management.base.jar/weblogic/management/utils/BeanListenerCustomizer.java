package weblogic.management.utils;

import weblogic.descriptor.BeanUpdateFailedException;

public interface BeanListenerCustomizer {
   void activateFinished() throws BeanUpdateFailedException;
}

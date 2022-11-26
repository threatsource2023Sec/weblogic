package org.jboss.weld.bean;

import java.util.Set;
import org.jboss.weld.ejb.spi.EjbDescriptor;

public interface SessionBean extends ClassBean {
   EjbDescriptor getEjbDescriptor();

   Set getLocalBusinessMethodSignatures();

   Set getRemoteBusinessMethodSignatures();
}

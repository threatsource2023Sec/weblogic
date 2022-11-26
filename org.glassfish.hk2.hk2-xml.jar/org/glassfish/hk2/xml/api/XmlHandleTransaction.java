package org.glassfish.hk2.xml.api;

import org.glassfish.hk2.api.MultiException;

public interface XmlHandleTransaction {
   XmlRootHandle getRootHandle();

   void commit() throws MultiException;

   void abandon();
}

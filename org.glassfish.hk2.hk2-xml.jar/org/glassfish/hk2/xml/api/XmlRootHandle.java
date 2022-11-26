package org.glassfish.hk2.xml.api;

import java.beans.VetoableChangeListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

public interface XmlRootHandle {
   Object getRoot();

   Class getRootClass();

   URI getURI();

   boolean isAdvertisedInLocator();

   boolean isAdvertisedInHub();

   Object getReadOnlyRoot(boolean var1);

   XmlRootCopy getXmlRootCopy();

   void overlay(XmlRootHandle var1);

   void addRoot(Object var1);

   void addRoot();

   Object removeRoot();

   void addChangeListener(VetoableChangeListener... var1);

   void removeChangeListener(VetoableChangeListener... var1);

   List getChangeListeners();

   XmlHandleTransaction lockForTransaction() throws IllegalStateException;

   void startValidating();

   void stopValidating();

   boolean isValidating();

   void marshal(OutputStream var1) throws IOException;

   void marshal(OutputStream var1, Map var2) throws IOException;
}

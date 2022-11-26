package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface PropertyBean extends SettableBean {
   String getKey();

   void setKey(String var1) throws IllegalArgumentException;

   String getValue();

   void setValue(String var1) throws IllegalArgumentException;
}

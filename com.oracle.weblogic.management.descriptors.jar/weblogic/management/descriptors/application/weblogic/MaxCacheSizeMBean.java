package weblogic.management.descriptors.application.weblogic;

import weblogic.management.descriptors.XMLElementMBean;

public interface MaxCacheSizeMBean extends XMLElementMBean {
   int getBytes();

   void setBytes(int var1);

   int getMegabytes();

   void setMegabytes(int var1);
}

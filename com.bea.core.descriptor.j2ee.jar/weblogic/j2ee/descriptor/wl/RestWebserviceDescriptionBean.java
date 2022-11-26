package weblogic.j2ee.descriptor.wl;

public interface RestWebserviceDescriptionBean {
   String getApplicationClassName();

   void setApplicationClassName(String var1);

   String getServletName();

   void setServletName(String var1);

   String getFilterName();

   void setFilterName(String var1);

   String[] getApplicationBaseUris();

   void setApplicationBaseUris(String[] var1);
}

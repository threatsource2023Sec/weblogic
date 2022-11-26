package weblogic.j2ee.descriptor.wl;

public interface PreferApplicationPackagesBean {
   String[] getPackageNames();

   void addPackageName(String var1);

   void setPackageNames(String[] var1);

   void removePackageName(String var1);
}

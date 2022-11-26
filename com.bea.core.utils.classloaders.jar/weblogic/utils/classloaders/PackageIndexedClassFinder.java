package weblogic.utils.classloaders;

import java.util.Collection;

public interface PackageIndexedClassFinder extends ClassFinder {
   Collection getPackageNames();
}

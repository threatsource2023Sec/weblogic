package org.python.core.packagecache;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.ProviderNotFoundException;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.python.core.Py;
import org.python.core.PyJavaPackage;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.imp;
import org.python.core.util.RelativeFile;

public abstract class PathPackageManager extends CachedJarsPackageManager {
   public PyList searchPath = new PyList();

   protected boolean packageExists(PyList path, String pkg, String name) {
      String child = pkg.replace('.', File.separatorChar) + File.separator + name;

      for(int i = 0; i < path.__len__(); ++i) {
         PyObject entry = path.pyget(i);
         String dir = Py.fileSystemDecode(entry);
         File f = new RelativeFile(dir, child);

         try {
            if (f.isDirectory() && imp.caseok(f, name)) {
               PackageExistsFileFilter m = new PackageExistsFileFilter();
               f.listFiles(m);
               boolean exists = m.packageExists();
               if (exists) {
                  Py.writeComment("import", "java package as '" + f.getAbsolutePath() + "'");
               }

               return exists;
            }
         } catch (SecurityException var11) {
            return false;
         }
      }

      return false;
   }

   protected void doDir(PyList path, PyList ret, PyJavaPackage jpkg, boolean instantiate, boolean exclpkgs) {
      String child = jpkg.__name__.replace('.', File.separatorChar);

      for(int i = 0; i < path.__len__(); ++i) {
         String dir = Py.fileSystemDecode(path.pyget(i));
         if (dir.length() == 0) {
            dir = null;
         }

         File childFile = new File(dir, child);
         String[] list = childFile.list();
         if (list != null) {
            label92:
            for(int j = 0; j < list.length; ++j) {
               String jname = list[j];
               File cand = new File(childFile, jname);
               int jlen = jname.length();
               boolean pkgCand = false;
               if (cand.isDirectory()) {
                  if (!instantiate && exclpkgs) {
                     continue;
                  }

                  pkgCand = true;
               } else {
                  if (!jname.endsWith(".class")) {
                     continue;
                  }

                  jlen -= 6;
               }

               jname = jname.substring(0, jlen);
               PyString name = Py.newStringOrUnicode(jname);
               if (!this.filterByName(jname, pkgCand) && !jpkg.__dict__.has_key((PyObject)name) && !jpkg.clsSet.has_key((PyObject)name) && !ret.__contains__(name) && Character.isJavaIdentifierStart(jname.charAt(0))) {
                  int acc;
                  for(acc = 1; acc < jlen; ++acc) {
                     if (!Character.isJavaIdentifierPart(jname.charAt(acc))) {
                        continue label92;
                     }
                  }

                  if (!pkgCand) {
                     try {
                        acc = checkAccess(new BufferedInputStream(new FileInputStream(cand)));
                        if (acc == -1 || this.filterByAccess(jname, acc)) {
                           continue;
                        }
                     } catch (IOException var18) {
                        continue;
                     }
                  }

                  if (instantiate) {
                     if (pkgCand) {
                        jpkg.addPackage(jname);
                     } else {
                        jpkg.addClass(jname, Py.findClass(jpkg.__name__ + "." + jname));
                     }
                  }

                  ret.append(name);
               }
            }
         }
      }

   }

   public void addDirectory(File dir) {
      try {
         if (dir.getPath().length() == 0) {
            this.searchPath.append(Py.EmptyString);
         } else {
            this.searchPath.append(Py.newStringOrUnicode(dir.getCanonicalPath()));
         }
      } catch (IOException var3) {
         this.warning("skipping bad directory, '" + dir + "'");
      }

   }

   private void addTransformedPackages(Map pkgClasses) {
      Map transformed = new HashMap();

      String pn;
      String classes;
      for(Iterator var3 = pkgClasses.keySet().iterator(); var3.hasNext(); transformed.put(pn, classes)) {
         pn = (String)var3.next();
         List[] list = (List[])pkgClasses.get(pn);
         classes = listToString(list[0]);
         if (list[1].size() > 0) {
            classes = classes + '@' + listToString(list[1]);
         }
      }

      this.addPackages(transformed, (String)null);
   }

   private void walkThroughModules(List paths, final int modulePos, final String separator, final Map pkgClasses) {
      Iterator var5 = paths.iterator();

      while(var5.hasNext()) {
         Path mp = (Path)var5.next();
         if (mp.getNameCount() >= modulePos) {
            String mn = mp.getName(modulePos - 1).toString();

            try {
               Files.walkFileTree(mp, new SimpleFileVisitor() {
                  public FileVisitResult visitFile(Path p, BasicFileAttributes attrs) {
                     if (p.getNameCount() > modulePos && p.toString().endsWith(".class") && !p.toString().endsWith("module-info.class")) {
                        String f = p.subpath(modulePos, p.getNameCount()).toString().replace(separator, ".");
                        f = f.substring(0, f.lastIndexOf(".class"));
                        int pos = f.lastIndexOf(".");
                        String pkg = f.substring(0, pos);
                        String cls = f.substring(pos + 1);
                        if (!pkgClasses.containsKey(pkg)) {
                           pkgClasses.put(pkg, new ArrayList[]{new ArrayList(), new ArrayList()});
                        }

                        ArrayList[] al = (ArrayList[])pkgClasses.get(pkg);

                        try {
                           InputStream is = Files.newInputStream(p);
                           Throwable var9 = null;

                           try {
                              al[0].add(cls);
                           } catch (Throwable var19) {
                              var9 = var19;
                              throw var19;
                           } finally {
                              if (is != null) {
                                 if (var9 != null) {
                                    try {
                                       is.close();
                                    } catch (Throwable var18) {
                                       var9.addSuppressed(var18);
                                    }
                                 } else {
                                    is.close();
                                 }
                              }

                           }
                        } catch (IOException var21) {
                           throw new RuntimeException("can not access class file at: " + p, var21);
                        }
                     }

                     return FileVisitResult.CONTINUE;
                  }
               });
            } catch (IOException var9) {
               throw new RuntimeException("can not walk through exploded module path for module(" + mn + ") at: " + mp, var9);
            }
         }
      }

   }

   public void addJImageToPackages(Properties registry) {
      try {
         Map pkgClasses = new HashMap();
         String separator = "/";
         int modulePos = 2;
         FileSystem jrtfs = FileSystems.getFileSystem(URI.create("jrt:/"));
         Path mods = jrtfs.getPath("/modules");
         List paths = new ArrayList();
         Iterator var8 = Files.newDirectoryStream(mods).iterator();

         while(var8.hasNext()) {
            Path p = (Path)var8.next();
            paths.add(p);
         }

         this.walkThroughModules(paths, modulePos, separator, pkgClasses);
         this.addTransformedPackages(pkgClasses);
      } catch (ProviderNotFoundException var10) {
         this.warning("Provider \"jrt\" not found");
      } catch (IOException var11) {
         this.warning("can not walk through jrtfs");
      }

   }

   public void addClassPath(String path) {
      String[] paths = path.split(File.pathSeparator);
      String[] var3 = paths;
      int var4 = paths.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String entry = var3[var5];
         if (!entry.endsWith(".jar") && !entry.endsWith(".zip")) {
            File dir = new File(entry);
            if (entry.length() == 0 || dir.isDirectory()) {
               this.addDirectory(dir);
            }
         } else {
            this.addJarToPackages(new File(entry), true);
         }
      }

   }

   public PyList doDir(PyJavaPackage jpkg, boolean instantiate, boolean exclpkgs) {
      PyList basic = this.basicDoDir(jpkg, instantiate, exclpkgs);
      PyList ret = new PyList();
      this.doDir(this.searchPath, ret, jpkg, instantiate, exclpkgs);
      return this.merge(basic, ret);
   }

   public boolean packageExists(String pkg, String name) {
      return this.packageExists(this.searchPath, pkg, name);
   }

   private static class PackageExistsFileFilter implements FilenameFilter {
      private boolean java;
      private boolean python;

      private PackageExistsFileFilter() {
      }

      public boolean accept(File dir, String name) {
         if (!name.endsWith(".py") && !name.endsWith("$py.class") && !name.endsWith("$_PyInner.class")) {
            if (name.endsWith(".class")) {
               this.java = true;
            }
         } else {
            this.python = true;
         }

         return false;
      }

      public boolean packageExists() {
         return !this.python || this.java;
      }

      // $FF: synthetic method
      PackageExistsFileFilter(Object x0) {
         this();
      }
   }
}

package com.bea.core.repackaged.jdt.internal.compiler.apt.util;

import com.bea.core.repackaged.jdt.internal.compiler.batch.ClasspathJrt;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.tools.JavaFileManager;
import javax.tools.StandardLocation;

public class ModuleLocationHandler {
   Map containers = new HashMap();

   ModuleLocationHandler() {
   }

   public void newSystemLocation(JavaFileManager.Location loc, ClasspathJrt cp) throws IOException {
      SystemLocationContainer systemLocationWrapper = new SystemLocationContainer(StandardLocation.SYSTEM_MODULES, cp);
      this.containers.put(loc, systemLocationWrapper);
   }

   public void newSystemLocation(JavaFileManager.Location loc, JrtFileSystem jrt) throws IOException {
      SystemLocationContainer systemLocationWrapper = new SystemLocationContainer(StandardLocation.SYSTEM_MODULES, jrt);
      this.containers.put(loc, systemLocationWrapper);
   }

   public LocationWrapper getLocation(JavaFileManager.Location loc, String moduleName) {
      if (loc instanceof LocationWrapper) {
         loc = ((LocationWrapper)loc).loc;
      }

      LocationContainer forwarder = (LocationContainer)this.containers.get(loc);
      return forwarder != null ? forwarder.get(moduleName) : null;
   }

   public JavaFileManager.Location getLocation(JavaFileManager.Location loc, Path path) {
      LocationContainer forwarder = (LocationContainer)this.containers.get(loc);
      return forwarder != null ? forwarder.get(path) : null;
   }

   public LocationContainer getLocation(JavaFileManager.Location location) {
      return (LocationContainer)this.containers.get(location);
   }

   public void setLocation(JavaFileManager.Location location, Iterable paths) {
      LocationContainer container = (LocationContainer)this.containers.get(location);
      if (container == null) {
         container = new LocationContainer(location);
         this.containers.put(location, container);
      }

      container.setPaths(paths);
   }

   public void setLocation(JavaFileManager.Location location, String moduleName, Iterable paths) {
      LocationWrapper wrapper = null;
      LocationContainer container = (LocationContainer)this.containers.get(location);
      if (container != null) {
         wrapper = container.get(moduleName);
      } else {
         container = new LocationContainer(location);
         this.containers.put(location, container);
      }

      if (wrapper == null) {
         if (moduleName.equals("")) {
            wrapper = new LocationWrapper(location, location.isOutputLocation(), paths);
         } else {
            wrapper = new ModuleLocationWrapper(location, moduleName, location.isOutputLocation(), paths);
            Iterator var7 = paths.iterator();

            while(var7.hasNext()) {
               Path path = (Path)var7.next();
               container.put((Path)path, (LocationWrapper)wrapper);
            }
         }
      } else {
         ((LocationWrapper)wrapper).setPaths(paths);
      }

      container.put((String)moduleName, (LocationWrapper)wrapper);
   }

   public Iterable listLocationsForModules(JavaFileManager.Location location) {
      LocationContainer locationContainer = (LocationContainer)this.containers.get(location);
      if (locationContainer == null) {
         return Collections.emptyList();
      } else {
         Set set = new HashSet(locationContainer.locationNames.values());
         List singletonList = Collections.singletonList(set);
         return singletonList;
      }
   }

   public void close() {
      Collection values = this.containers.values();
      Iterator var3 = values.iterator();

      while(var3.hasNext()) {
         LocationContainer locationContainer = (LocationContainer)var3.next();
         locationContainer.clear();
      }

   }

   class LocationContainer extends LocationWrapper {
      Map locationNames;
      Map locationPaths;

      LocationContainer(JavaFileManager.Location loc) {
         super();
         this.loc = loc;
         this.locationNames = new HashMap();
         this.locationPaths = new HashMap();
      }

      LocationWrapper get(String moduleName) {
         return (LocationWrapper)this.locationNames.get(moduleName);
      }

      void put(String moduleName, LocationWrapper impl) {
         this.locationNames.put(moduleName, impl);
         this.paths = null;
      }

      void put(Path path, LocationWrapper impl) {
         this.locationPaths.put(path, impl);
         this.paths = null;
      }

      JavaFileManager.Location get(Path path) {
         return (JavaFileManager.Location)this.locationPaths.get(path);
      }

      void setPaths(Iterable paths) {
         super.setPaths(paths);
         this.clear();
      }

      Iterable getPaths() {
         return (Iterable)(this.paths != null ? this.paths : this.locationPaths.keySet());
      }

      public void clear() {
         this.locationNames.clear();
         this.locationPaths.clear();
      }
   }

   class LocationWrapper implements JavaFileManager.Location {
      JavaFileManager.Location loc;
      boolean output;
      List paths;

      LocationWrapper() {
      }

      public LocationWrapper(JavaFileManager.Location loc, boolean output, Iterable paths) {
         this.loc = loc;
         this.output = output;
         this.setPaths(paths);
      }

      public String getName() {
         return this.loc.getName();
      }

      public boolean isOutputLocation() {
         return this.output;
      }

      Iterable getPaths() {
         return this.paths;
      }

      void setPaths(Iterable paths) {
         if (paths == null) {
            this.paths = null;
         } else {
            List newPaths = new ArrayList();
            Iterator var4 = paths.iterator();

            while(var4.hasNext()) {
               Path file = (Path)var4.next();
               newPaths.add(file);
            }

            this.paths = Collections.unmodifiableList(newPaths);
         }

      }

      public String toString() {
         return this.loc.toString() + "[]";
      }
   }

   class ModuleLocationWrapper extends LocationWrapper {
      String modName;

      public ModuleLocationWrapper(JavaFileManager.Location loc, String mod, boolean output, Iterable paths) {
         super(loc, output, paths);
         this.modName = mod;
      }

      public String getName() {
         return this.loc.getName() + "[" + this.modName + "]";
      }

      public boolean isOutputLocation() {
         return this.output;
      }

      Iterable getPaths() {
         return this.paths;
      }

      public String toString() {
         return this.loc.toString() + "[" + this.modName + "]";
      }
   }

   class SystemLocationContainer extends LocationContainer {
      public SystemLocationContainer(JavaFileManager.Location loc, JrtFileSystem jrt) throws IOException {
         super(loc);
         jrt.initialize();
         HashMap modulePathMap = jrt.modulePathMap;
         Set keySet = modulePathMap.keySet();
         Iterator var7 = keySet.iterator();

         while(var7.hasNext()) {
            String mod = (String)var7.next();
            Path path = jrt.file.toPath();
            ModuleLocationWrapper wrapper = ModuleLocationHandler.this.new ModuleLocationWrapper(loc, mod, false, Collections.singletonList(path));
            this.locationNames.put(mod, wrapper);
            this.locationPaths.put(path, wrapper);
         }

      }

      public SystemLocationContainer(JavaFileManager.Location loc, ClasspathJrt cp) throws IOException {
         this(loc, (JrtFileSystem)(new JrtFileSystem(cp.file)));
      }
   }
}

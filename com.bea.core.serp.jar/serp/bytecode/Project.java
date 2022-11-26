package serp.bytecode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;
import serp.util.Strings;

public class Project implements VisitAcceptor {
   private final String _name;
   private final HashMap _cache;
   private final NameCache _names;

   public Project() {
      this((String)null);
   }

   public Project(String name) {
      this._cache = new HashMap();
      this._names = new NameCache();
      this._name = name;
   }

   public String getName() {
      return this._name;
   }

   public NameCache getNameCache() {
      return this._names;
   }

   public BCClass loadClass(String name) {
      return this.loadClass((String)name, (ClassLoader)null);
   }

   public BCClass loadClass(String name, ClassLoader loader) {
      name = this._names.getExternalForm(name, false);
      BCClass cached = this.checkCache(name);
      if (cached != null) {
         return cached;
      } else {
         if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
         }

         try {
            return this.loadClass(Strings.toClass(name, loader));
         } catch (Exception var6) {
            String componentName = this._names.getComponentName(name);
            BCClass ret = new BCClass(this);
            if (componentName != null) {
               ret.setState(new ArrayState(name, componentName));
            } else {
               ret.setState(new ObjectState(this._names));
               ret.setName(name);
               ret.setSuperclass(Object.class);
            }

            this.cache(name, ret);
            return ret;
         }
      }
   }

   public BCClass loadClass(Class type) {
      BCClass cached = this.checkCache(type.getName());
      if (cached != null) {
         return cached;
      } else {
         BCClass ret = new BCClass(this);
         if (type.isPrimitive()) {
            ret.setState(new PrimitiveState(type, this._names));
         } else if (type.isArray()) {
            ret.setState(new ArrayState(type.getName(), this._names.getExternalForm(type.getComponentType().getName(), false)));
         } else {
            ret.setState(new ObjectState(this._names));

            try {
               ret.read(type);
            } catch (IOException var5) {
               throw new RuntimeException(var5.toString());
            }
         }

         this.cache(type.getName(), ret);
         return ret;
      }
   }

   public BCClass loadClass(File classFile) {
      return this.loadClass((File)classFile, (ClassLoader)null);
   }

   public BCClass loadClass(File classFile, ClassLoader loader) {
      BCClass ret = new BCClass(this);
      ret.setState(new ObjectState(this._names));

      try {
         ret.read(classFile, loader);
      } catch (IOException var6) {
         throw new RuntimeException(var6.toString());
      }

      String name = ret.getName();
      BCClass cached = this.checkCache(name);
      if (cached != null) {
         return cached;
      } else {
         this.cache(name, ret);
         return ret;
      }
   }

   public BCClass loadClass(InputStream in) {
      return this.loadClass((InputStream)in, (ClassLoader)null);
   }

   public BCClass loadClass(InputStream in, ClassLoader loader) {
      BCClass ret = new BCClass(this);
      ret.setState(new ObjectState(this._names));

      try {
         ret.read(in, loader);
      } catch (IOException var6) {
         throw new RuntimeException(var6.toString());
      }

      String name = ret.getName();
      BCClass cached = this.checkCache(name);
      if (cached != null) {
         return cached;
      } else {
         this.cache(name, ret);
         return ret;
      }
   }

   public BCClass loadClass(BCClass bc) {
      String name = bc.getName();
      BCClass cached = this.checkCache(name);
      if (cached != null) {
         return cached;
      } else {
         BCClass ret = new BCClass(this);
         if (bc.isPrimitive()) {
            ret.setState(new PrimitiveState(bc.getType(), this._names));
         } else if (bc.isArray()) {
            ret.setState(new ArrayState(bc.getName(), bc.getComponentName()));
         } else {
            ret.setState(new ObjectState(this._names));
            ret.read(bc);
         }

         this.cache(name, ret);
         return ret;
      }
   }

   public void clear() {
      Collection values = this._cache.values();
      Iterator itr = values.iterator();

      while(itr.hasNext()) {
         BCClass bc = (BCClass)itr.next();
         itr.remove();
         bc.invalidate();
      }

      this._names.clear();
   }

   public boolean removeClass(String type) {
      return this.removeClass(this.checkCache(type));
   }

   public boolean removeClass(Class type) {
      return type == null ? false : this.removeClass(this.checkCache(type.getName()));
   }

   public boolean removeClass(BCClass type) {
      if (type == null) {
         return false;
      } else if (!this.removeFromCache(type.getName(), type)) {
         return false;
      } else {
         type.invalidate();
         return true;
      }
   }

   public BCClass[] getClasses() {
      Collection values = this._cache.values();
      return (BCClass[])((BCClass[])values.toArray(new BCClass[values.size()]));
   }

   public boolean containsClass(String type) {
      return this._cache.containsKey(type);
   }

   public boolean containsClass(Class type) {
      return type == null ? false : this.containsClass(type.getName());
   }

   public boolean containsClass(BCClass type) {
      return type == null ? false : this.containsClass(type.getName());
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterProject(this);
      BCClass[] classes = this.getClasses();

      for(int i = 0; i < classes.length; ++i) {
         classes[i].acceptVisit(visit);
      }

      visit.exitProject(this);
   }

   void renameClass(String oldName, String newName, BCClass bc) {
      if (!oldName.equals(newName)) {
         BCClass cached = this.checkCache(newName);
         if (cached != null) {
            throw new IllegalStateException("A class with name " + newName + " already exists in this project");
         } else {
            this.removeFromCache(oldName, bc);
            this.cache(newName, bc);
         }
      }
   }

   private BCClass checkCache(String name) {
      return (BCClass)this._cache.get(name);
   }

   private void cache(String name, BCClass bc) {
      this._cache.put(name, bc);
   }

   private boolean removeFromCache(String name, BCClass bc) {
      BCClass rem = this.checkCache(name);
      if (rem != bc) {
         return false;
      } else {
         this._cache.remove(name);
         return true;
      }
   }
}

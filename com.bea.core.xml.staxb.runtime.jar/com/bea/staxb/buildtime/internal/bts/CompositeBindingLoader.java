package com.bea.staxb.buildtime.internal.bts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

public class CompositeBindingLoader implements BindingLoader {
   private static final CompositeBindingLoader EMPTY_LOADER;
   private final Collection loaderPath;
   private BindingLoader lastFound;
   private BitSet usedLoaders;

   public static BindingLoader forPath(BindingLoader[] path) {
      if (path == null) {
         throw new IllegalArgumentException("null path");
      } else {
         return forPath((Collection)Arrays.asList(path));
      }
   }

   public static BindingLoader forPath(Collection path) {
      if (path == null) {
         throw new IllegalArgumentException("null path");
      } else {
         IdentityHashMap seen = new IdentityHashMap();
         List flattened = new ArrayList(path.size());
         Iterator i = path.iterator();

         while(i.hasNext()) {
            addToPath(flattened, seen, (BindingLoader)i.next());
         }

         if (flattened.size() == 0) {
            return EMPTY_LOADER;
         } else {
            return (BindingLoader)(flattened.size() == 1 ? (BindingLoader)flattened.get(0) : new CompositeBindingLoader(flattened));
         }
      }
   }

   public BindingType getBindingType(BindingTypeName btName) {
      if (btName == null) {
         throw new IllegalArgumentException("null btName");
      } else {
         BindingLoader checkFirst = this.lastFound;
         BindingType result = null;
         if (checkFirst != null) {
            result = checkFirst.getBindingType(btName);
            if (result != null) {
               return result;
            }
         }

         int idx = 0;

         for(Iterator i = this.loaderPath.iterator(); i.hasNext(); ++idx) {
            BindingLoader bl = (BindingLoader)i.next();
            if (bl != checkFirst) {
               result = bl.getBindingType(btName);
               if (result != null) {
                  if (idx > 0) {
                     this.lastFound = bl;
                  }

                  this.usedLoaders.set(idx);
                  return result;
               }
            }
         }

         return null;
      }
   }

   public BindingTypeName lookupPojoFor(XmlTypeName xName) {
      if (xName == null) {
         throw new IllegalArgumentException("null xName");
      } else {
         BindingLoader checkFirst = this.lastFound;
         BindingTypeName result = null;
         if (checkFirst != null) {
            result = checkFirst.lookupPojoFor(xName);
            if (result != null) {
               return result;
            }
         }

         int idx = 0;

         for(Iterator i = this.loaderPath.iterator(); i.hasNext(); ++idx) {
            BindingLoader bl = (BindingLoader)i.next();
            if (bl != checkFirst) {
               result = bl.lookupPojoFor(xName);
               if (result != null) {
                  if (idx > 0) {
                     this.lastFound = bl;
                  }

                  this.usedLoaders.set(idx);
                  return result;
               }
            }
         }

         return null;
      }
   }

   public BindingTypeName lookupXmlObjectFor(XmlTypeName xName) {
      if (xName == null) {
         throw new IllegalArgumentException("null xName");
      } else {
         BindingLoader checkFirst = this.lastFound;
         BindingTypeName result = null;
         if (checkFirst != null) {
            result = checkFirst.lookupXmlObjectFor(xName);
            if (result != null) {
               return result;
            }
         }

         int idx = 0;

         for(Iterator i = this.loaderPath.iterator(); i.hasNext(); ++idx) {
            BindingLoader bl = (BindingLoader)i.next();
            if (bl != checkFirst) {
               result = bl.lookupXmlObjectFor(xName);
               if (result != null) {
                  if (idx > 0) {
                     this.lastFound = bl;
                  }

                  this.usedLoaders.set(idx);
                  return result;
               }
            }
         }

         return null;
      }
   }

   public BindingTypeName lookupTypeFor(JavaTypeName jName) {
      if (jName == null) {
         throw new IllegalArgumentException("null jName");
      } else {
         BindingLoader checkFirst = this.lastFound;
         BindingTypeName result = null;
         if (checkFirst != null) {
            result = checkFirst.lookupTypeFor(jName);
            if (result != null) {
               return result;
            }
         }

         int idx = 0;

         Iterator i;
         BindingLoader bl;
         for(i = this.loaderPath.iterator(); i.hasNext(); ++idx) {
            bl = (BindingLoader)i.next();
            if (bl != checkFirst && this.usedLoaders.get(idx)) {
               result = bl.lookupTypeFor(jName);
               if (result != null) {
                  if (idx > 0) {
                     this.lastFound = bl;
                  }

                  return result;
               }
            }
         }

         idx = 0;

         for(i = this.loaderPath.iterator(); i.hasNext(); ++idx) {
            bl = (BindingLoader)i.next();
            if (bl != checkFirst) {
               result = bl.lookupTypeFor(jName);
               if (result != null) {
                  if (idx > 0) {
                     this.lastFound = bl;
                  }

                  this.usedLoaders.set(idx);
                  return result;
               }
            }
         }

         return null;
      }
   }

   public BindingTypeName lookupElementFor(JavaTypeName jName) {
      if (jName == null) {
         throw new IllegalArgumentException("null jName");
      } else {
         BindingLoader checkFirst = this.lastFound;
         BindingTypeName result = null;
         if (checkFirst != null) {
            result = checkFirst.lookupElementFor(jName);
            if (result != null) {
               return result;
            }
         }

         int idx = 0;

         Iterator i;
         BindingLoader bl;
         for(i = this.loaderPath.iterator(); i.hasNext(); ++idx) {
            bl = (BindingLoader)i.next();
            if (bl != checkFirst && this.usedLoaders.get(idx)) {
               result = bl.lookupElementFor(jName);
               if (result != null) {
                  if (idx > 0) {
                     this.lastFound = bl;
                  }

                  return result;
               }
            }
         }

         idx = 0;

         for(i = this.loaderPath.iterator(); i.hasNext(); ++idx) {
            bl = (BindingLoader)i.next();
            if (bl != checkFirst) {
               result = bl.lookupElementFor(jName);
               if (result != null) {
                  if (idx > 0) {
                     this.lastFound = bl;
                  }

                  this.usedLoaders.set(idx);
                  return result;
               }
            }
         }

         return null;
      }
   }

   private static void addToPath(List path, IdentityHashMap seen, BindingLoader loader) {
      if (!seen.containsKey(loader)) {
         if (loader instanceof CompositeBindingLoader) {
            Iterator j = ((CompositeBindingLoader)loader).loaderPath.iterator();

            while(j.hasNext()) {
               addToPath(path, seen, (BindingLoader)j.next());
            }
         } else {
            path.add(loader);
         }

      }
   }

   private CompositeBindingLoader(List path) {
      if (path == null) {
         throw new IllegalArgumentException("null path");
      } else {
         this.loaderPath = Collections.unmodifiableList(path);
         this.usedLoaders = new BitSet(this.loaderPath.size());
      }
   }

   static {
      EMPTY_LOADER = new CompositeBindingLoader(Collections.EMPTY_LIST);
   }
}

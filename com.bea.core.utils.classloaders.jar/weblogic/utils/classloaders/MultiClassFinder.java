package weblogic.utils.classloaders;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.StringTokenizer;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.index.BackgroundClassFinderIndex;
import weblogic.utils.classloaders.index.EagerClassFinderIndex;
import weblogic.utils.collections.CopyOnWriteArrayList;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.SequencingEnumerator;

public class MultiClassFinder implements ClassFinder {
   private final List finders = new CopyOnWriteArrayList();
   private volatile FrozenInfo frozen = null;
   private volatile ClassFinderWalker walker = new SimpleClassFinderWalker();
   private static final boolean isUseIndex = Boolean.valueOf(System.getProperty("weblogic.application.classloader.useIndex", "true"));
   private static final boolean isIndexInBackground = Boolean.valueOf(System.getProperty("weblogic.application.classloader.indexInBackground", "false"));

   public MultiClassFinder() {
   }

   public MultiClassFinder(ClassFinder finder) {
      this.addFinder(finder);
   }

   protected boolean isUnmodifiable() {
      return false;
   }

   public void addFinder(ClassFinder finder) {
      if (finder == null) {
         throw new IllegalArgumentException("Cannot add null finder");
      } else if (this.isUnmodifiable()) {
         throw new IllegalArgumentException("Finder is unmodifiable");
      } else {
         this.finders.add(finder);
         if (this.frozen != null) {
            this.handlePostFreezeAddition(this.finders.size() - 1, finder);
         }

      }
   }

   protected int getFirstIndex() {
      return 0;
   }

   public void addFinderFirst(ClassFinder finder) {
      if (finder == null) {
         throw new IllegalArgumentException("Cannot add null finder");
      } else if (this.isUnmodifiable()) {
         throw new IllegalArgumentException("Finder is unmodifiable");
      } else {
         int index = this.getFirstIndex();
         this.finders.add(index, finder);
         if (this.frozen != null) {
            this.handlePostFreezeAddition(index, finder);
         }

      }
   }

   public static String getPackageName(String className) {
      int lastDot = className.lastIndexOf(46);
      return lastDot > 0 ? className.substring(0, lastDot) : "";
   }

   public static String getResourcePackageName(String resourcePath) {
      while(StringUtils.startsWith(resourcePath, '/')) {
         resourcePath = resourcePath.substring(1);
      }

      int lastSlash = resourcePath.lastIndexOf(47);
      if (lastSlash > 0) {
         return resourcePath.substring(0, lastSlash).replace('/', '.');
      } else {
         return "";
      }
   }

   public static String getResourceDirectoryPackageName(String resourcePath) {
      while(StringUtils.startsWith(resourcePath, '/')) {
         resourcePath = resourcePath.substring(1);
      }

      while(StringUtils.endsWith(resourcePath, '/')) {
         resourcePath = resourcePath.substring(0, resourcePath.length() - 1);
      }

      return resourcePath.replace('/', '.');
   }

   protected final ClassFinderWalker getWalker() {
      return this.walker;
   }

   public final Source getClassSource(String name) {
      Iterator i = this.walker.iterator(getPackageName(name));

      Source src;
      do {
         if (!i.hasNext()) {
            return null;
         }

         ClassFinder finder = (ClassFinder)i.next();
         src = finder.getClassSource(name);
      } while(src == null);

      return src;
   }

   public final Source getSource(String name) {
      Iterator i = this.walker.iterator(getResourcePackageName(name));

      Source src;
      do {
         if (!i.hasNext()) {
            return null;
         }

         ClassFinder finder = (ClassFinder)i.next();
         src = finder.getSource(name);
      } while(src == null);

      return src;
   }

   public final Enumeration getSources(final String name) {
      return new LazyEnumerationWithConversion(this.walker.iterator(getResourcePackageName(name))) {
         protected Enumeration convert(ClassFinder s) {
            return s.getSources(name);
         }
      };
   }

   void freeze(Runnable onStartOptimization, Runnable onFinishOptimization) {
      this.freezeRecursive((MultiClassFinder)null, 0);
      if (isUseIndex) {
         if (onStartOptimization != null) {
            onStartOptimization.run();
         }

         this.index(onFinishOptimization);
      }

   }

   public void freeze() {
      this.freezeRecursive((MultiClassFinder)null, 0);
      if (isUseIndex) {
         this.index();
      }

   }

   private void freezeRecursive(MultiClassFinder parent, int myIndex) {
      this.frozen = new FrozenInfo(parent, myIndex);

      for(Iterator var3 = this.finders.iterator(); var3.hasNext(); ++myIndex) {
         ClassFinder finder = (ClassFinder)var3.next();
         if (finder instanceof MultiClassFinder) {
            ((MultiClassFinder)finder).freezeRecursive(this, myIndex);
         } else {
            finder.freeze();
         }
      }

   }

   public void handlePostFreezeAddition(int effectiveIndex, ClassFinder finder) {
      if (this.frozen.parent != null) {
         this.frozen.parent.handlePostFreezeAddition(effectiveIndex + this.frozen.myIndex, finder);
      } else {
         this.walker.handlePostFreezeAddition(effectiveIndex, finder);
      }

   }

   private void index() {
      this.index((Runnable)null);
   }

   private void index(Runnable onFinishOptimization) {
      this.walker = this.createIndexedWalker(this.getFlattenedListOfFinders(), onFinishOptimization);
   }

   protected ClassFinderIndex createIndexedWalker(List flatList) {
      return this.createIndexedWalker(flatList, (Runnable)null);
   }

   protected ClassFinderIndex createIndexedWalker(List flatList, Runnable onFinishOptimization) {
      ClassFinderIndex index = isIndexInBackground ? new BackgroundClassFinderIndex(flatList) : new EagerClassFinderIndex(flatList);
      ((ClassFinderIndex)index).build(onFinishOptimization);
      return (ClassFinderIndex)index;
   }

   public void close() {
      Iterator i = this.finders.iterator();

      while(i.hasNext()) {
         ClassFinder finder = (ClassFinder)i.next();
         finder.close();
      }

      this.frozen = null;
      this.walker = new SimpleClassFinderWalker();
      this.finders.clear();
   }

   public final String getClassPath() {
      StringBuffer sb = new StringBuffer();
      String sep = "";
      HashSet got = new HashSet();
      Iterator i = this.finders.iterator();

      while(true) {
         String cp;
         do {
            if (!i.hasNext()) {
               return sb.toString();
            }

            cp = ((ClassFinder)i.next()).getClassPath();
         } while(cp == null);

         StringTokenizer st = new StringTokenizer(cp, PlatformConstants.PATH_SEP);

         while(st.hasMoreTokens()) {
            String element = st.nextToken();
            if (!got.contains(element)) {
               sb.append(sep).append(element);
               sep = PlatformConstants.PATH_SEP;
               got.add(element);
            }
         }
      }
   }

   public ClassFinder getManifestFinder() {
      MultiClassFinder mcf = new MultiClassFinder();
      Iterator i = this.finders.iterator();

      while(i.hasNext()) {
         ClassFinder finder = (ClassFinder)i.next();
         ClassFinder cf = finder.getManifestFinder();
         if (cf != null) {
            mcf.addFinder(cf);
         }
      }

      return mcf;
   }

   public Enumeration entries() {
      List l = new ArrayList();
      Iterator i = this.finders.iterator();

      while(i.hasNext()) {
         Enumeration e = ((ClassFinder)i.next()).entries();
         if (e != EmptyEnumerator.EMPTY) {
            l.add(e);
         }
      }

      return new SequencingEnumerator((Enumeration[])l.toArray(new Enumeration[l.size()]));
   }

   public ClassFinder[] getClassFinders() {
      return (ClassFinder[])((ClassFinder[])this.finders.toArray(new ClassFinder[this.finders.size()]));
   }

   public ClassFinder[] getClassFindersForAnnotationScan() {
      return this.getClassFinders();
   }

   public ClassFinder remove(int index) {
      if (this.isUnmodifiable()) {
         throw new IllegalArgumentException("Finder is unmodifiable");
      } else if (this.frozen != null) {
         throw new IllegalArgumentException("Finder is frozen");
      } else {
         ClassFinder removed = (ClassFinder)this.finders.remove(index);
         return removed;
      }
   }

   public void flatten() {
      List temp = this.getFlattenedListOfFinders();
      this.finders.clear();
      this.finders.addAll(temp);
   }

   public List getFlattenedListOfFinders() {
      List flatList = new ArrayList();
      Set paths = new HashSet();
      this.flatten(flatList, paths, this.finders, (Object)null);
      return flatList;
   }

   private void flatten(List flatList, Set paths, List finders, Object metadata) {
      Iterator var5 = finders.iterator();

      while(true) {
         while(var5.hasNext()) {
            ClassFinder finder = (ClassFinder)var5.next();
            if (finder instanceof MetadataAttachingFinder && ((MetadataAttachingFinder)finder).getFinder() instanceof MultiClassFinder) {
               this.flatten(flatList, paths, ((MultiClassFinder)((MetadataAttachingFinder)finder).getFinder()).finders, ((MetadataAttachingFinder)finder).getMetadata());
            } else if (finder instanceof MultiClassFinder) {
               this.flatten(flatList, paths, ((MultiClassFinder)finder).finders, metadata);
            } else if (!(finder instanceof NullClassFinder)) {
               String path = null;
               File file = null;
               if (finder instanceof ZipClassFinder) {
                  file = new File(((ZipClassFinder)finder).getZipFile().getName());
               } else if (finder instanceof DirectoryClassFinder) {
                  file = new File(((DirectoryClassFinder)finder).getPath());
               }

               if (file != null) {
                  try {
                     path = file.getCanonicalPath();
                  } catch (IOException var10) {
                     throw new AssertionError("Could not create canonical path for " + file);
                  }
               }

               if (metadata != null) {
                  finder = new MetadataAttachingFinder((ClassFinder)finder, metadata);
               }

               if (path != null) {
                  if (!paths.contains(path)) {
                     paths.add(path);
                     flatList.add(finder);
                  }
               } else {
                  flatList.add(finder);
               }
            }
         }

         return;
      }
   }

   public int size() {
      return this.finders.size();
   }

   private class SimpleClassFinderWalker implements ClassFinderWalker {
      private SimpleClassFinderWalker() {
      }

      public Iterator iterator(String packageName) {
         final Deque its = new ArrayDeque();
         its.add(MultiClassFinder.this.finders.iterator());
         return new Iterator() {
            ClassFinder next = null;

            public boolean hasNext() {
               return this.next != null ? true : this.findNext();
            }

            public ClassFinder next() {
               if (this.next == null && !this.findNext()) {
                  throw new NoSuchElementException();
               } else {
                  ClassFinder finder = this.next;
                  this.next = null;
                  return finder;
               }
            }

            private boolean findNext() {
               Iterator last = (Iterator)its.peekLast();
               if (last == null) {
                  this.next = null;
                  return false;
               } else if (last.hasNext()) {
                  ClassFinder f = (ClassFinder)last.next();
                  if (f instanceof MultiClassFinder) {
                     its.addLast(((MultiClassFinder)f).finders.iterator());
                     return this.findNext();
                  } else {
                     this.next = f;
                     return true;
                  }
               } else {
                  its.removeLast();
                  return this.findNext();
               }
            }

            public void remove() {
               throw new UnsupportedOperationException();
            }
         };
      }

      public void handlePostFreezeAddition(int effectiveIndex, ClassFinder finder) {
      }

      // $FF: synthetic method
      SimpleClassFinderWalker(Object x1) {
         this();
      }
   }

   private static class FrozenInfo {
      private final MultiClassFinder parent;
      private final int myIndex;

      private FrozenInfo(MultiClassFinder parent, int myIndex) {
         this.parent = parent;
         this.myIndex = myIndex;
      }

      // $FF: synthetic method
      FrozenInfo(MultiClassFinder x0, int x1, Object x2) {
         this(x0, x1);
      }
   }

   private abstract class LazyEnumerationWithConversion implements Enumeration {
      private final Iterator iterator;
      private Enumeration current;

      public LazyEnumerationWithConversion(Iterator iterator) {
         this.iterator = iterator;
         this.current = null;
      }

      public boolean hasMoreElements() {
         if (this.current != null && this.current.hasMoreElements()) {
            return true;
         } else {
            do {
               if (!this.iterator.hasNext()) {
                  return false;
               }

               this.current = this.convert(this.iterator.next());
            } while(this.current == null || !this.current.hasMoreElements());

            return true;
         }
      }

      public Object nextElement() {
         if (this.hasMoreElements()) {
            return this.current.nextElement();
         } else {
            throw new NoSuchElementException();
         }
      }

      protected abstract Enumeration convert(Object var1);
   }
}

package weblogic.management.provider.internal.federatedconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import weblogic.management.DomainDir;
import weblogic.management.utils.federatedconfig.FederatedConfig;
import weblogic.management.utils.federatedconfig.FederatedConfigLocator;

public abstract class AbstractSearchPathTransformationFactory implements FederatedConfigLocator {
   private final List updateListeners = new ArrayList();
   private static final String FRAGMENT_FILE_NAME_PATTERN = "*-config";
   static final Iterator EMPTY_ITERATOR = new EmptyIterator();

   public abstract String getSearchPath() throws IOException;

   public File resolvePath(String relativePathString) {
      if (relativePathString != null && relativePathString.trim().length() != 0) {
         File file = new File(relativePathString);
         return file.isAbsolute() ? file : new File(this.getRelativePathRoot(), relativePathString);
      } else {
         return this.getRelativePathRoot();
      }
   }

   public File getRelativePathRoot() {
      return new File(DomainDir.getRootDir());
   }

   static boolean isDebug() {
      return FederatedConfigImpl.isDebug();
   }

   static void debug(String msg) {
      FederatedConfigImpl.debug(msg);
   }

   public Iterator sources(Set excludes, String descFileName) throws Exception {
      return new SearchPathSourceIterator();
   }

   public boolean registerUpdateListener(FederatedConfig.UpdateListener listener) {
      return this.updateListeners.add(listener);
   }

   public boolean unregisterUpdateListener(FederatedConfig.UpdateListener listener) {
      return this.updateListeners.remove(listener);
   }

   public void reportUpdateNeeded() {
      if (!this.updateListeners.isEmpty()) {
         Iterator var1 = this.updateListeners.iterator();

         while(var1.hasNext()) {
            FederatedConfig.UpdateListener listener = (FederatedConfig.UpdateListener)var1.next();
            listener.reportUpdateNeeded();
         }
      }

   }

   private static class SingletonIterator implements Iterator {
      private final Object element;
      private boolean isConsumed;

      private SingletonIterator(Object element) {
         this.isConsumed = false;
         this.element = element;
      }

      public boolean hasNext() {
         return !this.isConsumed;
      }

      public Object next() {
         this.isConsumed = true;
         return this.element;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      SingletonIterator(Object x0, Object x1) {
         this(x0);
      }
   }

   public static class ArrayIterator implements Iterator {
      private int nextSlot = 0;
      private final Object[] items;

      public ArrayIterator(Object... items) {
         this.items = items;
      }

      public boolean hasNext() {
         return this.nextSlot < this.items.length;
      }

      public Object next() {
         return this.items[this.nextSlot++];
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }

   private static class EmptyIterator implements Iterator {
      private EmptyIterator() {
      }

      public boolean hasNext() {
         return false;
      }

      public Object next() {
         throw new NoSuchElementException();
      }

      public void remove() {
         throw new IllegalStateException();
      }

      // $FF: synthetic method
      EmptyIterator(Object x0) {
         this();
      }
   }

   private class SearchPathElementSourceIterator implements Iterator {
      private Iterator xmlFilePaths;
      private Iterator xsltFilePaths;
      private final File pathElementPath;

      private SearchPathElementSourceIterator(String pathElement) throws IOException {
         this.xmlFilePaths = null;
         this.xsltFilePaths = null;
         this.pathElementPath = AbstractSearchPathTransformationFactory.this.resolvePath(pathElement);
         if (this.pathElementPath.isDirectory()) {
            this.xmlFilePaths = (new DirectoryLister(this.pathElementPath, new FileFragmentFilenameFilter("*-config.xml"))).iterator();
            this.xsltFilePaths = (new DirectoryLister(this.pathElementPath, new FileFragmentFilenameFilter("*-config.xslt"))).iterator();
         } else {
            if (this.pathElementPath.getName().endsWith(".xml")) {
               this.xmlFilePaths = new SingletonIterator(this.pathElementPath);
            } else if (this.pathElementPath.getName().endsWith(".xslt")) {
               this.xsltFilePaths = new SingletonIterator(this.pathElementPath);
            }

            if (this.xmlFilePaths == null) {
               this.xmlFilePaths = AbstractSearchPathTransformationFactory.EMPTY_ITERATOR;
            }

            if (this.xsltFilePaths == null) {
               this.xsltFilePaths = AbstractSearchPathTransformationFactory.EMPTY_ITERATOR;
            }
         }

      }

      public boolean hasNext() {
         return this.xmlFilePaths.hasNext() || this.xsltFilePaths.hasNext();
      }

      public Source next() {
         if (this.xmlFilePaths.hasNext()) {
            return this.createTransformationFromConfigFragments();
         } else {
            try {
               if (!this.xsltFilePaths.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  File path = (File)this.xsltFilePaths.next();
                  return new StreamSource(new FileInputStream(path), path.toURI().toString());
               }
            } catch (IOException var2) {
               throw new RuntimeException(var2);
            }
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      private Source createTransformationFromConfigFragments() {
         FederatedConfigFragmentsProcessor proc = new FederatedConfigFragmentsProcessor();

         try {
            return proc.getTransformation(this.pathElementPath, this.xmlFilePaths);
         } catch (IOException var3) {
            throw new RuntimeException(var3);
         } catch (XMLStreamException var4) {
            throw new RuntimeException(var4);
         }
      }

      // $FF: synthetic method
      SearchPathElementSourceIterator(String x1, Object x2) throws IOException {
         this(x1);
      }
   }

   private class SearchPathSourceIterator implements Iterator {
      private final Iterator pathIt;
      private Iterator currentElementIterator;

      private SearchPathSourceIterator() {
         this.currentElementIterator = null;

         try {
            this.pathIt = new ArrayIterator(AbstractSearchPathTransformationFactory.this.getSearchPath().split(","));
         } catch (IOException var3) {
            throw new RuntimeException(var3);
         }

         this.currentElementIterator = this.preparePathElementIterator();
      }

      private Iterator preparePathElementIterator() {
         while(true) {
            if (this.pathIt.hasNext()) {
               try {
                  Iterator result = AbstractSearchPathTransformationFactory.this.new SearchPathElementSourceIterator((String)this.pathIt.next());
                  if (!result.hasNext()) {
                     continue;
                  }

                  return result;
               } catch (IOException var3) {
                  throw new RuntimeException(var3);
               }
            }

            return new EmptyIterator();
         }
      }

      public boolean hasNext() {
         if (this.currentElementIterator.hasNext()) {
            return true;
         } else {
            this.currentElementIterator = this.preparePathElementIterator();
            return this.currentElementIterator.hasNext();
         }
      }

      public Source next() {
         return (Source)this.currentElementIterator.next();
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      SearchPathSourceIterator(Object x1) {
         this();
      }
   }
}

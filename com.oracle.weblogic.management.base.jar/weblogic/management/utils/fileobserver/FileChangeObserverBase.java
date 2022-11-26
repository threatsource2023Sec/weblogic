package weblogic.management.utils.fileobserver;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class FileChangeObserverBase implements FileChangeObserver {
   private final List listeners = new CopyOnWriteArrayList();

   public FileChangeObserverBase(FileChangeListener... listeners) {
      if (listeners != null) {
         FileChangeListener[] var2 = listeners;
         int var3 = listeners.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            FileChangeListener listener = var2[var4];
            if (listener == null) {
               throw new IllegalStateException("The listener cannot be null.");
            }

            this.addListener(listener);
         }
      }

   }

   public void initialize() throws Exception {
   }

   public void addListener(FileChangeListener listener) {
      if (listener != null) {
         this.listeners.add(listener);
      }

   }

   public void removeListener(FileChangeListener listener) {
      if (listener != null) {
         while(true) {
            if (this.listeners.remove(listener)) {
               continue;
            }
         }
      }

   }

   public Collection getListeners() {
      return this.listeners;
   }

   public void observeChanges() {
      Iterator var1 = this.listeners.iterator();

      FileChangeListener listener;
      while(var1.hasNext()) {
         listener = (FileChangeListener)var1.next();
         listener.onStart(this);
      }

      this.observeFileSystemChanges();
      var1 = this.listeners.iterator();

      while(var1.hasNext()) {
         listener = (FileChangeListener)var1.next();
         listener.onStop(this);
      }

   }

   protected abstract void observeFileSystemChanges();

   public void destroy() throws Exception {
      this.listeners.clear();
   }

   protected void doCreate(FileEntry entry) {
      Iterator var2 = this.getListeners().iterator();

      while(var2.hasNext()) {
         FileChangeListener listener = (FileChangeListener)var2.next();
         if (entry.isDirectory()) {
            listener.onDirectoryCreate(entry.getFile());
         } else {
            listener.onFileCreate(entry.getFile());
         }
      }

   }

   protected void doDelete(FileEntry entry) {
      Iterator var2 = this.getListeners().iterator();

      while(var2.hasNext()) {
         FileChangeListener listener = (FileChangeListener)var2.next();
         if (entry.isDirectory()) {
            listener.onDirectoryDelete(entry.getFile());
         } else {
            listener.onFileDelete(entry.getFile());
         }
      }

   }

   protected void doMatch(FileEntry entry, File file) {
      if (entry.refresh(file)) {
         this.doChange(entry, file);
      }

   }

   protected void doChange(FileEntry entry, File file) {
      Iterator var3 = this.getListeners().iterator();

      while(var3.hasNext()) {
         FileChangeListener listener = (FileChangeListener)var3.next();
         if (entry.isDirectory()) {
            listener.onDirectoryChange(file);
         } else {
            listener.onFileChange(file);
         }
      }

   }
}

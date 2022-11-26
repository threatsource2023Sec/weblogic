package com.bea.core.repackaged.jdt.internal.compiler.apt.dispatch;

import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import javax.tools.JavaFileObject.Kind;

public class BatchFilerImpl implements Filer {
   protected final BaseAnnotationProcessorManager _dispatchManager;
   protected final BaseProcessingEnvImpl _env;
   protected final JavaFileManager _fileManager;
   protected final HashSet _createdFiles;

   public BatchFilerImpl(BaseAnnotationProcessorManager dispatchManager, BatchProcessingEnvImpl env) {
      this._dispatchManager = dispatchManager;
      this._fileManager = env._fileManager;
      this._env = env;
      this._createdFiles = new HashSet();
   }

   public void addNewUnit(ICompilationUnit unit) {
      this._env.addNewUnit(unit);
   }

   public void addNewClassFile(ReferenceBinding binding) {
      this._env.addNewClassFile(binding);
   }

   public JavaFileObject createClassFile(CharSequence name, Element... originatingElements) throws IOException {
      JavaFileObject jfo = this._fileManager.getJavaFileForOutput(StandardLocation.CLASS_OUTPUT, name.toString(), Kind.CLASS, (FileObject)null);
      URI uri = jfo.toUri();
      if (this._createdFiles.contains(uri)) {
         throw new FilerException("Class file already created : " + name);
      } else {
         this._createdFiles.add(uri);
         return new HookedJavaFileObject(jfo, jfo.getName(), name.toString(), this);
      }
   }

   public FileObject createResource(JavaFileManager.Location location, CharSequence pkg, CharSequence relativeName, Element... originatingElements) throws IOException {
      validateName(relativeName);
      FileObject fo = this._fileManager.getFileForOutput(location, pkg.toString(), relativeName.toString(), (FileObject)null);
      URI uri = fo.toUri();
      if (this._createdFiles.contains(uri)) {
         throw new FilerException("Resource already created : " + location + '/' + pkg + '/' + relativeName);
      } else {
         this._createdFiles.add(uri);
         return fo;
      }
   }

   private static void validateName(CharSequence relativeName) {
      int length = relativeName.length();
      if (length == 0) {
         throw new IllegalArgumentException("relative path cannot be empty");
      } else {
         String path = relativeName.toString();
         if (path.indexOf(92) != -1) {
            path = path.replace('\\', '/');
         }

         if (path.charAt(0) == '/') {
            throw new IllegalArgumentException("relative path is absolute");
         } else {
            boolean hasDot = false;

            for(int i = 0; i < length; ++i) {
               switch (path.charAt(i)) {
                  case '.':
                     hasDot = true;
                     break;
                  case '/':
                     if (hasDot) {
                        throw new IllegalArgumentException("relative name " + relativeName + " is not relative");
                     }
                     break;
                  default:
                     hasDot = false;
               }
            }

            if (hasDot) {
               throw new IllegalArgumentException("relative name " + relativeName + " is not relative");
            }
         }
      }
   }

   public JavaFileObject createSourceFile(CharSequence name, Element... originatingElements) throws IOException {
      String moduleAndPkgString = ((CharSequence)name).toString();
      int slash = moduleAndPkgString.indexOf(47);
      String mod = null;
      if (slash != -1) {
         name = moduleAndPkgString.substring(slash + 1, ((CharSequence)name).length());
         mod = moduleAndPkgString.substring(0, slash);
      }

      TypeElement typeElement = this._env._elementUtils.getTypeElement((CharSequence)name);
      if (typeElement != null) {
         throw new FilerException("Source file already exists : " + moduleAndPkgString);
      } else {
         JavaFileManager.Location location = mod == null ? StandardLocation.SOURCE_OUTPUT : this._fileManager.getLocationForModule(StandardLocation.SOURCE_OUTPUT, mod);
         JavaFileObject jfo = this._fileManager.getJavaFileForOutput((JavaFileManager.Location)location, ((CharSequence)name).toString(), Kind.SOURCE, (FileObject)null);
         URI uri = jfo.toUri();
         if (this._createdFiles.contains(uri)) {
            throw new FilerException("Source file already created : " + name);
         } else {
            this._createdFiles.add(uri);
            return new HookedJavaFileObject(jfo, jfo.getName(), ((CharSequence)name).toString(), this);
         }
      }
   }

   public FileObject getResource(JavaFileManager.Location location, CharSequence pkg, CharSequence relativeName) throws IOException {
      validateName(relativeName);
      FileObject fo = this._fileManager.getFileForInput(location, pkg.toString(), relativeName.toString());
      if (fo == null) {
         throw new FileNotFoundException("Resource does not exist : " + location + '/' + pkg + '/' + relativeName);
      } else {
         URI uri = fo.toUri();
         if (this._createdFiles.contains(uri)) {
            throw new FilerException("Resource already created : " + location + '/' + pkg + '/' + relativeName);
         } else {
            return fo;
         }
      }
   }
}

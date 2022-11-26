package com.bea.core.repackaged.jdt.core;

import com.bea.core.repackaged.jdt.core.util.IClassFileReader;
import com.bea.core.repackaged.jdt.core.util.ICodeAttribute;
import com.bea.core.repackaged.jdt.core.util.IMethodInfo;
import com.bea.core.repackaged.jdt.internal.antadapter.AntAdapterMessages;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public final class CheckDebugAttributes extends Task {
   private String file;
   private String property;

   public void execute() throws BuildException {
      if (this.file == null) {
         throw new BuildException(AntAdapterMessages.getString("checkDebugAttributes.file.argument.cannot.be.null"));
      } else if (this.property == null) {
         throw new BuildException(AntAdapterMessages.getString("checkDebugAttributes.property.argument.cannot.be.null"));
      } else {
         try {
            boolean hasDebugAttributes = false;
            if (Util.isClassFileName(this.file)) {
               IClassFileReader classFileReader = ToolFactory.createDefaultClassFileReader(this.file, 65535);
               hasDebugAttributes = this.checkClassFile(classFileReader);
            } else {
               ZipFile jarFile = null;

               try {
                  jarFile = new ZipFile(this.file);
               } catch (ZipException var9) {
                  throw new BuildException(AntAdapterMessages.getString("checkDebugAttributes.file.argument.must.be.a.classfile.or.a.jarfile"), var9);
               } finally {
                  if (jarFile != null) {
                     jarFile.close();
                  }

               }

               Enumeration entries = jarFile.entries();

               while(!hasDebugAttributes && entries.hasMoreElements()) {
                  ZipEntry entry = (ZipEntry)entries.nextElement();
                  if (Util.isClassFileName(entry.getName())) {
                     IClassFileReader classFileReader = ToolFactory.createDefaultClassFileReader(this.file, entry.getName(), 65535);
                     hasDebugAttributes = this.checkClassFile(classFileReader);
                  }
               }
            }

            if (hasDebugAttributes) {
               this.getProject().setUserProperty(this.property, "has debug");
            }

         } catch (IOException var11) {
            throw new BuildException(AntAdapterMessages.getString("checkDebugAttributes.ioexception.occured") + this.file, var11);
         }
      }
   }

   private boolean checkClassFile(IClassFileReader classFileReader) {
      IMethodInfo[] methodInfos = classFileReader.getMethodInfos();
      int i = 0;

      for(int max = methodInfos.length; i < max; ++i) {
         ICodeAttribute codeAttribute = methodInfos[i].getCodeAttribute();
         if (codeAttribute != null && codeAttribute.getLineNumberAttribute() != null) {
            return true;
         }
      }

      return false;
   }

   public void setFile(String value) {
      this.file = value;
   }

   public void setProperty(String value) {
      this.property = value;
   }
}

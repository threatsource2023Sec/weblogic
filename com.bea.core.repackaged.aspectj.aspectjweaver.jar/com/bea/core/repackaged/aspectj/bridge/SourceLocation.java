package com.bea.core.repackaged.aspectj.bridge;

import com.bea.core.repackaged.aspectj.util.LangUtil;
import java.io.File;

public class SourceLocation implements ISourceLocation {
   private static final long serialVersionUID = -5434765814401009794L;
   private transient int cachedHashcode;
   public static final ISourceLocation UNKNOWN;
   private final File sourceFile;
   private final int startLine;
   private final int column;
   private final int endLine;
   private int offset;
   private final String context;
   private boolean noColumn;
   private String sourceFileName;

   public static final void validLine(int line) {
      if (line < 0) {
         throw new IllegalArgumentException("negative line: " + line);
      } else if (line > 1073741823) {
         throw new IllegalArgumentException("line too large: " + line);
      }
   }

   public static final void validColumn(int column) {
      if (column < 0) {
         throw new IllegalArgumentException("negative column: " + column);
      } else if (column > 1073741823) {
         throw new IllegalArgumentException("column too large: " + column);
      }
   }

   public SourceLocation(File file, int line) {
      this(file, line, line, -2147483647);
   }

   public SourceLocation(File file, int line, int endLine) {
      this(file, line, endLine, -2147483647);
   }

   public SourceLocation(File file, int line, int endLine, int column) {
      this(file, line, endLine, column, (String)null);
   }

   public SourceLocation(File file, int line, int endLine, int column, String context) {
      this.cachedHashcode = -1;
      if (column == -2147483647) {
         column = 0;
         this.noColumn = true;
      }

      if (null == file) {
         file = ISourceLocation.NO_FILE;
      }

      validLine(line);
      validLine(endLine);
      LangUtil.throwIaxIfFalse(line <= endLine, line + " > " + endLine);
      LangUtil.throwIaxIfFalse(column >= 0, "negative column: " + column);
      this.sourceFile = file;
      this.startLine = line;
      this.column = column;
      this.endLine = endLine;
      this.context = context;
   }

   public SourceLocation(File file, int line, int endLine, int column, String context, String sourceFileName) {
      this(file, line, endLine, column, context);
      this.sourceFileName = sourceFileName;
   }

   public File getSourceFile() {
      return this.sourceFile;
   }

   public int getLine() {
      return this.startLine;
   }

   public int getColumn() {
      return this.column;
   }

   public int getEndLine() {
      return this.endLine;
   }

   public String getContext() {
      return this.context;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (null != this.context) {
         sb.append(this.context);
         sb.append(LangUtil.EOL);
      }

      if (this.sourceFile != ISourceLocation.NO_FILE) {
         sb.append(this.sourceFile.getPath());
      }

      if (this.startLine > 0) {
         sb.append(":");
         sb.append(this.startLine);
      }

      if (!this.noColumn) {
         sb.append(":" + this.column);
      }

      if (this.offset >= 0) {
         sb.append("::" + this.offset);
      }

      return sb.toString();
   }

   public int getOffset() {
      return this.offset;
   }

   public void setOffset(int i) {
      this.cachedHashcode = -1;
      this.offset = i;
   }

   public String getSourceFileName() {
      return this.sourceFileName;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof SourceLocation)) {
         return false;
      } else {
         boolean var10000;
         label66: {
            SourceLocation o = (SourceLocation)obj;
            if (this.startLine == o.startLine && this.column == o.column && this.endLine == o.endLine && this.offset == o.offset) {
               label60: {
                  if (this.sourceFile == null) {
                     if (o.sourceFile != null) {
                        break label60;
                     }
                  } else if (!this.sourceFile.equals(o.sourceFile)) {
                     break label60;
                  }

                  if (this.context == null) {
                     if (o.context != null) {
                        break label60;
                     }
                  } else if (!this.context.equals(o.context)) {
                     break label60;
                  }

                  if (this.noColumn == o.noColumn) {
                     if (this.sourceFileName == null) {
                        if (o.sourceFileName == null) {
                           break label66;
                        }
                     } else if (this.sourceFileName.equals(o.sourceFileName)) {
                        break label66;
                     }
                  }
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      if (this.cachedHashcode == -1) {
         this.cachedHashcode = this.sourceFile == null ? 0 : this.sourceFile.hashCode();
         this.cachedHashcode = this.cachedHashcode * 37 + this.startLine;
         this.cachedHashcode = this.cachedHashcode * 37 + this.column;
         this.cachedHashcode = this.cachedHashcode * 37 + this.endLine;
         this.cachedHashcode = this.cachedHashcode * 37 + this.offset;
         this.cachedHashcode = this.cachedHashcode * 37 + (this.context == null ? 0 : this.context.hashCode());
         this.cachedHashcode = this.cachedHashcode * 37 + (this.noColumn ? 0 : 1);
         this.cachedHashcode = this.cachedHashcode * 37 + (this.sourceFileName == null ? 0 : this.sourceFileName.hashCode());
      }

      return this.cachedHashcode;
   }

   static {
      UNKNOWN = new SourceLocation(ISourceLocation.NO_FILE, 0, 0, 0);
   }
}

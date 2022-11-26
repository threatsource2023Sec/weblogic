package com.bea.core.repackaged.aspectj.bridge;

import java.io.File;
import java.io.Serializable;

public interface ISourceLocation extends Serializable {
   int MAX_LINE = 1073741823;
   int MAX_COLUMN = 1073741823;
   File NO_FILE = new File("ISourceLocation.NO_FILE");
   int NO_COLUMN = -2147483647;
   ISourceLocation EMPTY = new SourceLocation(NO_FILE, 0, 0, 0);

   File getSourceFile();

   int getLine();

   int getColumn();

   int getOffset();

   int getEndLine();

   String getContext();

   String getSourceFileName();
}

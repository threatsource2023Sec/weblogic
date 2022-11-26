package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.SourceLocation;

class SourceLocationImpl implements SourceLocation {
   Class withinType;
   String fileName;
   int line;

   SourceLocationImpl(Class withinType, String fileName, int line) {
      this.withinType = withinType;
      this.fileName = fileName;
      this.line = line;
   }

   public Class getWithinType() {
      return this.withinType;
   }

   public String getFileName() {
      return this.fileName;
   }

   public int getLine() {
      return this.line;
   }

   public int getColumn() {
      return -1;
   }

   public String toString() {
      return this.getFileName() + ":" + this.getLine();
   }
}

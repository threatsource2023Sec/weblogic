package org.antlr.tool;

import org.stringtemplate.v4.STGroupFile;

public class ToolSTGroupFile extends STGroupFile {
   public ToolSTGroupFile(String fileName) {
      super(fileName);
      this.setListener(ErrorManager.getSTErrorListener());
   }
}

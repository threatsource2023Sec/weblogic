package org.stringtemplate.v4;

import java.net.URL;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.stringtemplate.v4.compiler.CompiledST;
import org.stringtemplate.v4.compiler.Compiler;
import org.stringtemplate.v4.misc.Misc;

public class STRawGroupDir extends STGroupDir {
   public STRawGroupDir(String dirName) {
      super(dirName);
   }

   public STRawGroupDir(String dirName, char delimiterStartChar, char delimiterStopChar) {
      super(dirName, delimiterStartChar, delimiterStopChar);
   }

   public STRawGroupDir(String dirName, String encoding) {
      super(dirName, encoding);
   }

   public STRawGroupDir(String dirName, String encoding, char delimiterStartChar, char delimiterStopChar) {
      super(dirName, encoding, delimiterStartChar, delimiterStopChar);
   }

   public STRawGroupDir(URL root, String encoding, char delimiterStartChar, char delimiterStopChar) {
      super(root, encoding, delimiterStartChar, delimiterStopChar);
   }

   public CompiledST loadTemplateFile(String prefix, String unqualifiedFileName, CharStream templateStream) {
      String template = templateStream.substring(0, templateStream.size() - 1);
      String templateName = Misc.getFileNameNoSuffix(unqualifiedFileName);
      String fullyQualifiedTemplateName = prefix + templateName;
      CompiledST impl = (new Compiler(this)).compile(fullyQualifiedTemplateName, template);
      CommonToken nameT = new CommonToken(9);
      nameT.setInputStream(templateStream);
      this.rawDefineTemplate(fullyQualifiedTemplateName, impl, nameT);
      impl.defineImplicitlyDefinedTemplates(this);
      return impl;
   }
}

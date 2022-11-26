package weblogic.servlet.jsp;

import java.io.File;
import weblogic.servlet.internal.WebAppServletContext;

public interface JSPPrecompiler {
   void compile(WebAppServletContext var1, File var2) throws Exception;
}

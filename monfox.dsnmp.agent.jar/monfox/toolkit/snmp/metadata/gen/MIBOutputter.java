package monfox.toolkit.snmp.metadata.gen;

import java.io.File;
import org.w3c.dom.Document;

public interface MIBOutputter {
   void setDirectory(File var1);

   void preProcess() throws MIBOutputterException;

   void output(Document var1, String var2) throws MIBOutputterException;

   void postProcess() throws MIBOutputterException;
}

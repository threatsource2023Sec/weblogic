package weblogic.xml.process;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public interface XMLProcessor {
   ProcessorDriver getDriver();

   void process(String var1) throws IOException, XMLParsingException, XMLProcessingException;

   void process(File var1) throws IOException, XMLParsingException, XMLProcessingException;

   void process(InputStream var1) throws IOException, XMLParsingException, XMLProcessingException;

   void process(Reader var1) throws IOException, XMLParsingException, XMLProcessingException;
}

@start rule: main
package @package_name;

import @package_name.@element_class_name;
import java.io.*;
import java.net.*;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.ParserFactory;
import weblogic.xml.dtdc.Handler;
import weblogic.xml.dtdc.NullHandler;

public class @parser_class_name extends weblogic.xml.dtdc.BaseParser {

  // ============================================================
  // Parse

  public static void main(String[] args) throws Exception {
    try {
      Parser parser = ParserFactory.makeParser("@package_name.@parser_class_name");
      Handler handler = new Handler("@package_name", true);
      handler.setClassLoader(Class.forName("@package_name.@parser_class_name").getClassLoader());
      parser.setDocumentHandler(handler);
      parser.parse(args[0]);
    } catch(SAXParseException saxpe) {
      System.out.println("Line: " + saxpe.getLineNumber() + " Column: " + saxpe.getColumnNumber());
      throw saxpe;
    }
  }

  // ============================================================
  // Generated Code

  public void parse(char[] chars) throws SAXException {
    boolean done = false;
    int startLine = currentLine;
    if(dh == null) dh = new NullHandler();
    dh.setDocumentLocator(this);
    dh.startDocument();
    while(current != chars.length && !done) {
@parser
    }
    if(done) throw new SAXParseException("Could not parse (bad close tag): starting at line " + startLine, this);
    dh.endDocument();
  }

@read_element_declarations


  // ============================================================
  // Factory methods

  public static @package_name.@element_class_name make@element_class_name(String filename, Map map, boolean verbose) throws IOException, SAXException {
     Handler handler = new Handler(map,"@package_name",verbose);
     try {
       handler.setClassLoader(Class.forName("@package_name.@parser_class_name").getClassLoader());
     } catch(ClassNotFoundException cnfe) {
       cnfe.printStackTrace();  // really shouldn't happen
     }
     makeParser(handler).parse(filename);
     return (@package_name.@element_class_name)handler.getTopLevel();
  }

  public static @package_name.@element_class_name make@element_class_name(File file, Map map, boolean verbose) throws IOException, SAXException {
     Handler handler = new Handler(map,"@package_name",verbose);
     try {
       handler.setClassLoader(Class.forName("@package_name.@parser_class_name").getClassLoader());
     } catch(ClassNotFoundException cnfe) {
       cnfe.printStackTrace();  // really shouldn't happen
     }
     makeParser(handler).parse(file);
     return (@package_name.@element_class_name)handler.getTopLevel();
  }

  public static @package_name.@element_class_name make@element_class_name(InputSource is, Map map, boolean verbose) throws IOException, SAXException {
     Handler handler = new Handler(map,"@package_name",verbose);
     try {
       handler.setClassLoader(Class.forName("@package_name.@parser_class_name").getClassLoader());
     } catch(ClassNotFoundException cnfe) {
       cnfe.printStackTrace();  // really shouldn't happen
     }
     makeParser(handler).parse(is);
     return (@package_name.@element_class_name)handler.getTopLevel();
  }

  public static @package_name.@element_class_name make@element_class_name(InputStream in, Map map, boolean verbose) throws IOException, SAXException {
     Handler handler = new Handler(map,"@package_name",verbose);
     try {
       handler.setClassLoader(Class.forName("@package_name.@parser_class_name").getClassLoader());
     } catch(ClassNotFoundException cnfe) {
       cnfe.printStackTrace();  // really shouldn't happen
     }
     makeParser(handler).parse(in);
     return (@package_name.@element_class_name)handler.getTopLevel();
  }

  public static @package_name.@element_class_name make@element_class_name(InputStream in, String encodingType, Map map, boolean verbose) throws IOException, SAXException {
     Handler handler = new Handler(map,"@package_name",verbose);
     try {
       handler.setClassLoader(Class.forName("@package_name.@parser_class_name").getClassLoader());
     } catch(ClassNotFoundException cnfe) {
       cnfe.printStackTrace();  // really shouldn't happen
     }
     makeParser(handler).parse(in,encodingType);
     return (@package_name.@element_class_name)handler.getTopLevel();
  }



  private static @parser_class_name makeParser(Handler handler) throws IOException, SAXException {       
     @parser_class_name parser = new @parser_class_name();
     try {
       handler.setClassLoader(Class.forName("@package_name.@parser_class_name").getClassLoader());
     } catch(ClassNotFoundException cnfe) {
       cnfe.printStackTrace();  // really shouldn't happen
     }
     parser.setDocumentHandler(handler);
     return parser;
  }



  
  // ============================================================
  // Constructor

  public @parser_class_name() {
@set_attribute_types
    names  = new String[@max_attributes];
    types  = new String[@max_attributes];
    values = new String[@max_attributes];
  }

}
@end rule: main

@start rule: readAttribute
{String value = _readAttribute(chars);
@set_attribute_found
@ensure_unique
if(true@valid_attribute) throw new SAXParseException("Not a valid attribute value: [" + value + "]", this);
putAttribute("@attribute_realname", "@attribute_type", value);}
@end rule: readAttribute

@start rule: read_element_declaration
public void @read_element_method_name(char[] chars) throws SAXException {
  int startLine = currentLine;
  try {
    reset();
    boolean emptyTag = @read_element_attributes_method_name(chars);
    dh.startElement("@element_name", this);
    boolean done = false;
    while(!done && !emptyTag) {
@read_element
    }
    dh.endElement("@element_name");
  } catch(ArrayIndexOutOfBoundsException aioobe) {
    throw new SAXParseException("@element_name not closed, starts at line " + startLine, this);
  }
}

public boolean @read_element_attributes_method_name(char[] chars) throws SAXException {
  int startLine = currentLine;
  boolean done = false;
  boolean emptyTag = false;
@declare_required_attributes
  while(!done) {
@read_attributes
  }
@ensure_required_attributes
  return emptyTag;
}

@end rule: read_element_declaration


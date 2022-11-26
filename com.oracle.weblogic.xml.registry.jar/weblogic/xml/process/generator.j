@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@generator_package_decl

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.HashMap;
import java.util.Map;

import weblogic.utils.AssertionError;
import weblogic.utils.Debug;

import weblogic.xml.process.GeneratingContext;
import weblogic.xml.process.InvalidPathException;
import weblogic.xml.process.OutProcessor;
import weblogic.xml.process.XMLProcessingException;
import weblogic.xml.process.XMLGenerator;

public final class @generator_class @extends_super_class
  implements XMLGenerator, OutProcessor 
{

  private static final boolean __debug   = false;
  private static final boolean __verbose = false;

  private static Map __paths = new HashMap();

  private Writer __out;
  
  @public_id_decl
  @sys_id_decl

  @init_paths

  public void generate(String elementPath, Object[] params) 
    throws IOException, XMLProcessingException
  {
    if (__out == null) {
      __out = new BufferedWriter(new OutputStreamWriter(System.out));
    }
    docTypeHeader(__out, elementPath);
    GeneratingContext gctx = new GeneratingContext(elementPath);
    gctx.setWriter(__out);
    generate(gctx, params);
    gctx.release();
  }

  private void generate(GeneratingContext gctx, Object[] params)
    throws IOException, XMLProcessingException
  {
    if (__debug) Debug.assertion(gctx != null);
    String path = gctx.getPath();
    Integer pathId = (Integer)__paths.get(path);
    if (pathId == null) {
      throw new InvalidPathException(
        "No generating instructions exist for element with path = " + path
      );
    }

    @gen_dispatch
  }

  private static void docTypeHeader(Writer w, String topElt) 
    throws IOException
  {
    w.write(
      "<!DOCTYPE " + topElt + " PUBLIC \"" + __publicId + "\" \"" + __sysId +
      "\">\n"
    );
  }

  public void setWriter(Writer w) { __out = w; }

  public Writer getWriter() { return __out; }

  @element_generation_methods
}


@end rule: main

@start rule: element_generate_method
  // @generate_method_comment
  public void @generate_method_name(GeneratingContext gctx @generate_method_params) 
    throws XMLProcessingException
  {
    @param_variables
    gctx.setDelayedWrite(@delayed_write);
    @generate_method_body
  }
@end rule: element_generate_method

@start rule: write_xml_function_ref
GeneratingContext @ctx_var = gctx.newElementNode(@xml_element_to_generate);
    @call_xml_generate_method_name(@ctx_var@call_xml_generate_method_params);
    @ctx_var.release();
@end rule: write_xml_function_ref

@start rule: write_text_function_ref
GeneratingContext newctx = gctx.newTextNode();
    newctx.setValue(@get_text_value);
    newctx.release();
@end rule: write_text_function_ref

@start rule: set_attribute_function_ref
    // TBD
@end rule: set_attribute_function_ref

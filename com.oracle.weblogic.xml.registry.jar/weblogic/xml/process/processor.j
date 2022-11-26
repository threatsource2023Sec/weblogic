@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@processor_package_decl

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;

import weblogic.utils.AssertionError;
import weblogic.utils.Debug;

import weblogic.xml.process.Functions;
import weblogic.xml.process.InProcessor;
import weblogic.xml.process.ProcessorDriver;
import weblogic.xml.process.ProcessingContext;
import weblogic.xml.process.SAXValidationException;
import weblogic.xml.process.SAXProcessorException;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;
import weblogic.xml.process.XMLProcessor;

public final class @processor_class @extends_super_class
  implements XMLProcessor, InProcessor 
{

  private static final boolean debug   = true;
  private static final boolean verbose = true;

  private static final Map paths = new HashMap();

  private ProcessorDriver driver;

  public ProcessorDriver getDriver() { return driver; }

  @public_id_decl
  @local_dtd_decl

  @init_paths

  public @processor_class() {
    this(true); // validate by default
  }

  public @processor_class(boolean validate) {
    driver = @get_processor_driver;
  }

  public void process(String xmlFilePath)
    throws IOException, XMLParsingException, XMLProcessingException
  {
    driver.process(xmlFilePath);
  }

  public void process(File xmlFile)
    throws IOException, XMLParsingException, XMLProcessingException
  {
    driver.process(xmlFile);
  }

  public void process(Reader xmlReader)
    throws IOException, XMLParsingException, XMLProcessingException
  {
    driver.process(xmlReader);
  }

  public void process(InputSource xmlInputSource)
    throws IOException, XMLParsingException, XMLProcessingException
  {
    driver.process(xmlInputSource);
  }

  // FIX CR046934 : reading as binary to enable xerces automatic encoding detector.
  public void process(InputStream xmlInputStream)
    throws IOException, XMLParsingException, XMLProcessingException
  {
    driver.process(xmlInputStream);
  }

  public void preProc(ProcessingContext pctx) throws SAXProcessorException {
    if (debug) Debug.assertion(pctx != null);
    String path = pctx.getPath();
    if (debug) Debug.assertion(path != null);
    Integer id = (Integer)paths.get(path);
    if (id == null) return; // nothing to do
    @preproc_dispatch
  }

  public void postProc(ProcessingContext pctx) throws SAXProcessorException {
    if (debug) Debug.assertion(pctx != null);
    String path = pctx.getPath();
    if (debug) Debug.assertion(path != null);
    Integer id = (Integer)paths.get(path);
    if (id == null) return; // nothing to do
    @postproc_dispatch
  }

  @element_processing_dispatches

  @main_method

  public void addBoundObject(Object obj, String name) {
    driver.currentNode().addBoundObject(obj, name);
  }

}

@end rule: main

@start rule: element_processing_dispatch

  /* 
   * ELEMENT PATHS: @element_path
   */
  @document_scope_binding_getters
  private void @element_preproc_method(ProcessingContext pctx) {
    @new_bindings
    // pre-actions: not supported at this time
  }
  private void @element_postproc_method(ProcessingContext pctx) @throws_processing_exceptions {
    @fetch_function_values
    @fetch_bound_objects
    @validations
    @post_actions
  }
@end rule: element_processing_dispatch

@start rule: invoke_validation_method
    try {
      @validation_method_name(@element_value_variable);
    } catch (Exception e) {
      throw new SAXValidationException("Path " + pctx.getPath() + ": " + e.getMessage());
    }
@end rule: invoke_validation_method

@start rule: main_method_code
  // main method is usually just for debugging and testing purposes
  public static void main(String args[]) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage: @processor_class <XML file>");
      System.exit(1);
    }
    @processor_class processor = new @processor_class();
    processor.process(args[0]);
  }
	
@end rule: main_method_code


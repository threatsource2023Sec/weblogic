package org.apache.velocity.test;

public interface TemplateTestBase {
   String TMPL_FILE_EXT = "vm";
   String CMP_FILE_EXT = "cmp";
   String RESULT_FILE_EXT = "res";
   String FILE_RESOURCE_LOADER_PATH = "../test/templates";
   String TEST_CASE_PROPERTIES = "../test/templates/templates.properties";
   String RESULT_DIR = "../test/templates/results";
   String COMPARE_DIR = "../test/templates/compare";
}

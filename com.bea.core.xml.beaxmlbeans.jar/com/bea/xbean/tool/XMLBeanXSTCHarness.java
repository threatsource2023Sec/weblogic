package com.bea.xbean.tool;

import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class XMLBeanXSTCHarness implements XSTCTester.Harness {
   public void runTestCase(XSTCTester.TestCaseResult result) {
      XSTCTester.TestCase testCase = result.getTestCase();

      try {
         Collection errors = new ArrayList();
         boolean schemaValid = true;
         boolean instanceValid = true;
         if (testCase.getSchemaFile() != null) {
            SchemaTypeLoader loader = null;

            XmlObject instance;
            StringWriter sw;
            try {
               instance = XmlObject.Factory.parse(testCase.getSchemaFile(), (new XmlOptions()).setErrorListener(errors).setLoadLineNumbers());
               XmlObject schema2 = null;
               if (testCase.getResourceFile() != null) {
                  schema2 = XmlObject.Factory.parse(testCase.getResourceFile(), (new XmlOptions()).setErrorListener(errors).setLoadLineNumbers());
               }

               XmlObject[] schemas = schema2 == null ? new XmlObject[]{instance} : new XmlObject[]{instance, schema2};
               SchemaTypeSystem system = XmlBeans.compileXsd(schemas, XmlBeans.getBuiltinTypeSystem(), (new XmlOptions()).setErrorListener(errors));
               loader = XmlBeans.typeLoaderUnion(new SchemaTypeLoader[]{system, XmlBeans.getBuiltinTypeSystem()});
            } catch (Exception var16) {
               schemaValid = false;
               if (!(var16 instanceof XmlException) || errors.isEmpty()) {
                  result.setCrash(true);
                  sw = new StringWriter();
                  var16.printStackTrace(new PrintWriter(sw));
                  result.addSvMessages(Collections.singleton(sw.toString()));
               }
            }

            result.addSvMessages(errors);
            result.setSvActual(schemaValid);
            errors.clear();
            if (loader != null) {
               if (testCase.getInstanceFile() != null) {
                  try {
                     instance = loader.parse((File)testCase.getInstanceFile(), (SchemaType)null, (new XmlOptions()).setErrorListener(errors).setLoadLineNumbers());
                     if (!instance.validate((new XmlOptions()).setErrorListener(errors))) {
                        instanceValid = false;
                     }
                  } catch (Exception var15) {
                     instanceValid = false;
                     if (!(var15 instanceof XmlException) || errors.isEmpty()) {
                        result.setCrash(true);
                        sw = new StringWriter();
                        var15.printStackTrace(new PrintWriter(sw));
                        result.addIvMessages(Collections.singleton(sw.toString()));
                     }
                  }

                  result.addIvMessages(errors);
                  result.setIvActual(instanceValid);
               }
            }
         }
      } finally {
         ;
      }
   }
}

package org.apache.xmlbeans.impl.xb.ltgfmt.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase;
import org.apache.xmlbeans.impl.xb.ltgfmt.TestsDocument;

public class TestsDocumentImpl extends XmlComplexContentImpl implements TestsDocument {
   private static final QName TESTS$0 = new QName("http://www.bea.com/2003/05/xmlbean/ltgfmt", "tests");

   public TestsDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public TestsDocument.Tests getTests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TestsDocument.Tests target = null;
         target = (TestsDocument.Tests)this.get_store().find_element_user((QName)TESTS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setTests(TestsDocument.Tests tests) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TestsDocument.Tests target = null;
         target = (TestsDocument.Tests)this.get_store().find_element_user((QName)TESTS$0, 0);
         if (target == null) {
            target = (TestsDocument.Tests)this.get_store().add_element_user(TESTS$0);
         }

         target.set(tests);
      }
   }

   public TestsDocument.Tests addNewTests() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TestsDocument.Tests target = null;
         target = (TestsDocument.Tests)this.get_store().add_element_user(TESTS$0);
         return target;
      }
   }

   public static class TestsImpl extends XmlComplexContentImpl implements TestsDocument.Tests {
      private static final QName TEST$0 = new QName("http://www.bea.com/2003/05/xmlbean/ltgfmt", "test");

      public TestsImpl(SchemaType sType) {
         super(sType);
      }

      public TestCase[] getTestArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users((QName)TEST$0, targetList);
            TestCase[] result = new TestCase[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public TestCase getTestArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TestCase target = null;
            target = (TestCase)this.get_store().find_element_user(TEST$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfTestArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(TEST$0);
         }
      }

      public void setTestArray(TestCase[] testArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(testArray, TEST$0);
         }
      }

      public void setTestArray(int i, TestCase test) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TestCase target = null;
            target = (TestCase)this.get_store().find_element_user(TEST$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(test);
            }
         }
      }

      public TestCase insertNewTest(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TestCase target = null;
            target = (TestCase)this.get_store().insert_element_user(TEST$0, i);
            return target;
         }
      }

      public TestCase addNewTest() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            TestCase target = null;
            target = (TestCase)this.get_store().add_element_user(TEST$0);
            return target;
         }
      }

      public void removeTest(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(TEST$0, i);
         }
      }
   }
}

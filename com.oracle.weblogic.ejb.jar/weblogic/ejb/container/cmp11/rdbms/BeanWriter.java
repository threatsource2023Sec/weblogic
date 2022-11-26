package weblogic.ejb.container.cmp11.rdbms;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import weblogic.ejb.container.cmp11.rdbms.finders.Finder;

public final class BeanWriter {
   public static final boolean debug = false;
   public static final boolean verbose = false;
   private PrintWriter writer = null;
   private int indent = 0;

   public void putRDBMSBean(RDBMSBean bean, OutputStream xmlStream) {
      this.writer = new PrintWriter(xmlStream, true);
      this.docType("weblogic-rdbms-bean", "-//BEA Systems, Inc.//DTD WebLogic 5.1.0 EJB RDBMS Persistence//EN", "http://www.bea.com/servers/wls510/dtd/weblogic-rdbms-persistence.dtd");
      this.openTag("weblogic-rdbms-bean");
      this.stringValue("pool-name", bean.getPoolName(), true);
      this.stringValue("schema-name", bean.getSchemaName(), false);
      this.stringValue("table-name", bean.getTableName(), true);
      this.openTag("attribute-map");
      Iterator objectLinks = bean.getObjectLinks();

      while(objectLinks.hasNext()) {
         RDBMSBean.ObjectLink link = (RDBMSBean.ObjectLink)objectLinks.next();
         this.openTag("object-link");
         this.stringValue("bean-field", link.getBeanField(), true);
         this.stringValue("dbms-column", link.getDBMSColumn(), true);
         this.closeTag("object-link");
      }

      this.closeTag("attribute-map");
      this.openTag("finder-list");

      for(Iterator finders = bean.getFinders(); finders.hasNext(); this.closeTag("finder")) {
         Finder finder = (Finder)finders.next();
         this.openTag("finder");
         this.stringValue("method-name", finder.getName(), true);
         this.openTag("method-params");
         Iterator params = finder.getParameterTypes();

         while(params.hasNext()) {
            String param = (String)params.next();
            this.stringValue("method-param", param, false);
         }

         this.closeTag("method-params");
         this.cDataValue("finder-query", finder.getWeblogicQuery(), true);
         Iterator expressions = finder.getFinderExpressions();

         while(expressions.hasNext()) {
            Finder.FinderExpression expr = (Finder.FinderExpression)expressions.next();
            this.openTag("finder-expression");
            this.intValue("expression-number", expr.getNumber());
            this.cDataValue("expression-text", expr.getExpressionText(), false);
            this.stringValue("expression-type", expr.getExpressionType(), true);
            this.closeTag("finder-expression");
         }

         if (finder.getFinderOptions() != null) {
            this.openTag("finder-options");
            Finder.FinderOptions opts = finder.getFinderOptions();
            this.booleanValue("find-for-update", opts.getFindForUpdate());
            this.closeTag("finder-options");
         }
      }

      this.closeTag("finder-list");
      this.openTag("options");
      this.booleanValue("use-quoted-names", bean.getUseQuotedNames());
      if (bean.getTransactionIsolation() != null) {
         this.stringValue("transaction-isolation", RDBMSUtils.isolationLevelToString(bean.getTransactionIsolation()), false);
      }

      this.closeTag("options");
      this.closeTag("weblogic-rdbms-bean");
   }

   private void openTag(String tagName) {
      this.indentCurrentLevel();
      this.printOpenTag(tagName);
      this.writer.println();
      ++this.indent;
   }

   private void closeTag(String tagName) {
      --this.indent;
      this.indentCurrentLevel();
      this.printCloseTag(tagName);
      this.writer.println();
   }

   private void stringValue(String tagName, String value, boolean required) {
      if (required || value != null) {
         this.indentCurrentLevel();
         this.printOpenTag(tagName);
         if (value != null) {
            this.writer.print(value);
         }

         this.printCloseTag(tagName);
         this.writer.println();
      }

   }

   private void cDataValue(String tagName, String value, boolean required) {
      this.stringValue(tagName, "<![CDATA[" + value + "]]>", required);
   }

   private void booleanValue(String tagName, boolean value) {
      this.indentCurrentLevel();
      this.printOpenTag(tagName);
      this.writer.print((new Boolean(value)).toString());
      this.printCloseTag(tagName);
      this.writer.println();
   }

   private void intValue(String tagName, int value) {
      this.indentCurrentLevel();
      this.printOpenTag(tagName);
      this.writer.print(value);
      this.printCloseTag(tagName);
      this.writer.println();
   }

   private void indentCurrentLevel() {
      for(int iIndent = 0; iIndent < this.indent * 2; ++iIndent) {
         this.writer.print(' ');
      }

   }

   private void printOpenTag(String tagName) {
      this.writer.print("<");
      this.writer.print(tagName);
      this.writer.print(">");
   }

   private void printCloseTag(String tagName) {
      this.writer.print("</");
      this.writer.print(tagName);
      this.writer.print(">");
   }

   private void docType(String docName, String docTypeString, String docDTDUrl) {
      this.writer.print("<!DOCTYPE ");
      this.writer.print(docName);
      this.writer.println(" PUBLIC ");
      this.writer.print(" \"");
      this.writer.print(docTypeString);
      this.writer.println("\"");
      this.writer.print(" \"");
      this.writer.print(docDTDUrl);
      this.writer.print("\">");
      this.writer.println();
   }
}

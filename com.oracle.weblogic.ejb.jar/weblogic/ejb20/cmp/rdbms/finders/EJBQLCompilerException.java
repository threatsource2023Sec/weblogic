package weblogic.ejb20.cmp.rdbms.finders;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.xml.sax.SAXParseException;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.j2ee.validation.IDescriptorError;
import weblogic.j2ee.validation.IDescriptorErrorInfo;
import weblogic.logging.Loggable;
import weblogic.utils.AssertionError;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StackTraceUtils;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public class EJBQLCompilerException extends Exception implements IDescriptorError {
   private static final long serialVersionUID = 5756198563339201332L;
   private IDescriptorErrorInfo errorInfo;
   private ErrorCollectionException errorCollectionEx;
   private boolean ejbqlRewritten = false;
   private String ejbqlRewrittenReasonsString = "Unknown ?";
   private String originalEjbqlText;
   private List ejbqlTokenList;
   private String header = "";
   private String errorIndicatorLeft = " =>> ";
   private String errorIndicatorRight = " <<=  ";
   private static Class[] bugs = new Class[]{Error.class, NullPointerException.class, AssertionError.class};

   public EJBQLCompilerException(ErrorCollectionException ex, boolean ejbqlRewritten, String ejbqlRewrittenReasonsString, String originalEjbqlText, List tokens, String finderString, IDescriptorErrorInfo errorInfo) {
      this.errorCollectionEx = ex;
      this.ejbqlTokenList = tokens;
      this.originalEjbqlText = originalEjbqlText;
      this.ejbqlRewrittenReasonsString = ejbqlRewrittenReasonsString;
      this.ejbqlRewritten = ejbqlRewritten;
      this.errorInfo = errorInfo;
      if (finderString != null) {
         this.header = finderString;
      }

   }

   public ErrorCollectionException getErrorCollectionException() {
      return this.errorCollectionEx;
   }

   public Collection getExceptions() {
      return this.getErrorCollectionException().getExceptions();
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getErrorHeader()).append("\n\n");
      if (this.ejbqlRewritten) {
         sb.append(this.getEjbqlHasBeenRewrittenMessage()).append("\n\n");
         sb.append("    ").append(this.originalEjbqlText).append("\n\n\n\n");
         sb.append(" The rewritten query is:\n\n");
      }

      sb.append(this.getDefaultEjbqlString()).append("\n\n");
      sb.append(this.getExceptionMessagesOnly());
      return sb.toString();
   }

   public String getMessage() {
      return this.toString();
   }

   public List getEjbqlTokenList() {
      return this.ejbqlTokenList;
   }

   public String getErrorHeader() {
      return this.header;
   }

   public String getEjbqlHasBeenRewrittenMessage() {
      Loggable l = EJBLogger.logEjbqlHasBeenRewrittenLoggable(this.ejbqlRewrittenReasonsString);
      return l.getMessageText();
   }

   public boolean isEmpty() {
      return this.getErrorCollectionException().isEmpty();
   }

   public String getExceptionMessagesOnly() {
      StringBuilder sb = new StringBuilder();
      if (super.getMessage() != null) {
         sb.append(super.getMessage());
      }

      Iterator iter = this.getExceptions().iterator();

      while(iter.hasNext()) {
         Throwable e = (Throwable)iter.next();
         sb.append(this.formatExceptionMessage(e));
      }

      return sb.toString();
   }

   private String formatExceptionMessage(Throwable th) {
      String message = th.getMessage();
      StringBuilder result = new StringBuilder();
      if (th instanceof ClassNotFoundException) {
         result.append("Unable to load class: " + message + "\n");
      } else if (th instanceof XMLParsingException) {
         XMLParsingException xpe = (XMLParsingException)th;
         Throwable t = xpe.getNestedException();
         if (t instanceof SAXParseException) {
            SAXParseException spe = (SAXParseException)t;
            result.append("Error parsing '" + xpe.getFileName() + "' line " + spe.getLineNumber() + ": " + message + "\n");
         } else {
            result.append("Error parsing '" + xpe.getFileName() + "': " + message + "\n");
         }
      } else if (th instanceof XMLProcessingException) {
         XMLProcessingException xpe = (XMLProcessingException)th;
         result.append("Error processing '" + xpe.getFileName() + "': " + message + "\n");
      } else if (message != null) {
         result.append(message + "\n");
      }

      boolean debug = EJBDebugService.compilationLogger.isDebugEnabled() || EJBDebugService.deploymentLogger.isDebugEnabled();
      if (debug || message == null || this.isBug(th)) {
         result.append(StackTraceUtils.throwable2StackTrace(th));
      }

      return result.toString();
   }

   private boolean isBug(Throwable th) {
      for(int i = 0; i < bugs.length; ++i) {
         if (bugs[i].isAssignableFrom(th.getClass())) {
            return true;
         }
      }

      return false;
   }

   public String getDefaultEjbqlString() {
      StringBuilder sb = new StringBuilder();
      Iterator it = this.ejbqlTokenList.iterator();

      while(it.hasNext()) {
         EJBQLToken token = (EJBQLToken)it.next();
         if (token.getHadException()) {
            sb.append(this.errorIndicatorLeft);
            sb.append(token.getTokenText());
            sb.deleteCharAt(sb.length() - 1);
            sb.append(this.errorIndicatorRight);
         } else {
            sb.append(token.getTokenText());
         }
      }

      return sb.toString();
   }

   public boolean hasErrorInfo() {
      return this.errorInfo != null;
   }

   public IDescriptorErrorInfo getErrorInfo() {
      return this.errorInfo;
   }

   public void setDescriptorErrorInfo(IDescriptorErrorInfo errorInfo) {
      this.errorInfo = errorInfo;
   }
}

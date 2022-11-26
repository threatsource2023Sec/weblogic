package weblogic.servlet.logging;

import weblogic.servlet.HTTPLogger;
import weblogic.utils.StringUtils;

public final class LogFormat {
   private static final String CONTEXT_PREFIX = "ctx";
   private LogField[] entryFormat;

   public LogFormat(String format) {
      String[] fields = StringUtils.splitCompletely(format);
      this.entryFormat = new LogField[fields.length];

      for(int i = 0; i < fields.length; ++i) {
         String className = null;
         String prefix;
         if (fields[i].startsWith("x-")) {
            try {
               prefix = null;
               className = fields[i].substring(fields[i].indexOf("x-") + 2, fields[i].length());
               Object temp = Class.forName(className, true, Thread.currentThread().getContextClassLoader()).newInstance();
               CustomELFLogger customLogger = (CustomELFLogger)temp;
               this.entryFormat[i] = customLogger;
               continue;
            } catch (ClassNotFoundException var10) {
               HTTPLogger.logELFApplicationFieldFailure(fields[i], var10);
            } catch (InstantiationException var11) {
               HTTPLogger.logELFApplicationFieldFailure(fields[i], var11);
            } catch (IllegalAccessException var12) {
               HTTPLogger.logELFApplicationFieldFailure(fields[i], var12);
            } catch (ClassCastException var13) {
               HTTPLogger.logELFApplicationFieldFailureCCE(fields[i], className);
            }
         }

         if (!"time".equals(fields[i]) && !"date".equals(fields[i]) && !"bytes".equals(fields[i]) && !"time-taken".equals(fields[i])) {
            prefix = null;
            String header = null;
            String identifier = null;
            int delim;
            if ((delim = fields[i].indexOf(40)) != -1) {
               int close_paran;
               if ((close_paran = fields[i].indexOf(41)) == -1) {
                  HTTPLogger.logELFApplicationFieldFormatError(fields[i]);
                  continue;
               }

               prefix = fields[i].substring(0, delim);
               header = fields[i].substring(delim + 1, close_paran);
            } else if ((delim = fields[i].indexOf(45)) != -1) {
               prefix = fields[i].substring(0, delim);
               identifier = fields[i].substring(delim + 1);
            }

            if (header != null) {
               this.entryFormat[i] = new HeaderLogField(prefix, header);
            } else if (!"uri".equals(identifier) && !"uri-stem".equals(identifier) && !"uri-query".equals(identifier) && !"comment".equals(identifier) && !"authuser".equals(identifier) && !"method".equals(identifier) && !"status".equals(identifier)) {
               if (!"ip".equals(identifier) && !"dns".equals(identifier)) {
                  if (!"ctx".equals(prefix) || !"ecid".equals(identifier) && !"rid".equals(identifier)) {
                     this.entryFormat[i] = new NullLogField();
                  } else {
                     this.entryFormat[i] = new ContextLogField(prefix, identifier);
                  }
               } else {
                  this.entryFormat[i] = new HostLogField(prefix, identifier);
               }
            } else {
               this.entryFormat[i] = new URILogField(prefix, identifier);
            }
         } else {
            this.entryFormat[i] = new TimeLogField(fields[i]);
         }
      }

   }

   public int countFields() {
      return this.entryFormat.length;
   }

   public LogField getFieldAt(int i) {
      return this.entryFormat[i];
   }
}

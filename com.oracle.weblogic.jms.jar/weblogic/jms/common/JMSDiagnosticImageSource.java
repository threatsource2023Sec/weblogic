package weblogic.jms.common;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.health.HealthState;
import weblogic.jms.JMSService;
import weblogic.messaging.kernel.runtime.MessagingKernelDiagnosticImageSource;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;

public class JMSDiagnosticImageSource extends MessagingKernelDiagnosticImageSource {
   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      super.createDiagnosticImage(out);

      try {
         Class indentXMLStreamWriter = null;
         XMLStreamWriter xsw = null;

         try {
            indentXMLStreamWriter = Class.forName("com.sun.xml.internal.txw2.output.IndentingXMLStreamWriter");
            Constructor con = indentXMLStreamWriter.getConstructor(XMLStreamWriter.class);
            xsw = (XMLStreamWriter)con.newInstance(XMLOutputFactory.newInstance().createXMLStreamWriter(new PrintWriter(new OutputStreamWriter(out, "UTF-8"))));
            Method m = indentXMLStreamWriter.getMethod("setIndentStep", String.class);
            m.invoke(xsw, "  ");
         } catch (Throwable var7) {
            if (!(var7 instanceof ClassNotFoundException) && !(var7 instanceof IllegalAccessException)) {
               throw new AssertionError(var7);
            }

            xsw = XMLOutputFactory.newInstance().createXMLStreamWriter(new PrintWriter(new OutputStreamWriter(out, "UTF-8")));
         }

         xsw.writeStartDocument();

         try {
            JMSService.dump(this, xsw);
         } catch (DiagnosticImageTimeoutException var6) {
            this.dumpTimeoutComment(xsw);
            xsw.flush();
            return;
         }

         xsw.writeEndDocument();
         xsw.flush();
      } catch (XMLStreamException var8) {
         throw new ImageSourceCreationException("JMS image creation failed.", var8);
      } catch (IOException var9) {
         throw new ImageSourceCreationException("JMS image creation failed.", var9);
      }
   }

   public static void dumpHealthStateElement(XMLStreamWriter xsw, HealthState state) throws XMLStreamException {
      xsw.writeStartElement("Health");
      xsw.writeAttribute("state", HealthState.mapToString(state.getState()));
      String[] reasons = state.getReasonCode();
      if (reasons != null && reasons.length > 0) {
         for(int i = 0; i < reasons.length; ++i) {
            xsw.writeStartElement("Reason");
            xsw.writeCharacters(reasons[i]);
            xsw.writeEndElement();
         }
      }

      xsw.writeEndElement();
   }

   public static void dumpDestinationImpl(XMLStreamWriter xsw, DestinationImpl dest) throws XMLStreamException {
      xsw.writeAttribute("name", dest.getName() != null ? dest.getName() : "");
      xsw.writeAttribute("serverName", dest.getServerName());
      xsw.writeAttribute("applicationName", dest.getApplicationName() != null ? dest.getApplicationName() : "");
      xsw.writeAttribute("moduleName", dest.getModuleName() != null ? dest.getModuleName() : "");
      xsw.writeAttribute("id", dest.getDestinationId() != null ? dest.getDestinationId().toString() : "");
   }
}

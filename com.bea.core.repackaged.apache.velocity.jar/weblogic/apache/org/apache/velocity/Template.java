package weblogic.apache.org.apache.velocity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import weblogic.apache.org.apache.velocity.context.Context;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapterImpl;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.parser.ParseException;
import weblogic.apache.org.apache.velocity.runtime.parser.node.SimpleNode;
import weblogic.apache.org.apache.velocity.runtime.resource.Resource;

public class Template extends Resource {
   private boolean initialized = false;
   private Exception errorCondition = null;

   public boolean process() throws ResourceNotFoundException, ParseErrorException, Exception {
      super.data = null;
      InputStream is = null;
      this.errorCondition = null;

      try {
         is = super.resourceLoader.getResourceStream(super.name);
      } catch (ResourceNotFoundException var16) {
         this.errorCondition = var16;
         throw var16;
      }

      if (is != null) {
         boolean pex;
         try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, super.encoding));
            super.data = super.rsvc.parse(br, super.name);
            this.initDocument();
            pex = true;
         } catch (UnsupportedEncodingException var12) {
            String msg = "Template.process : Unsupported input encoding : " + super.encoding + " for template " + super.name;
            this.errorCondition = new ParseErrorException(msg);
            throw this.errorCondition;
         } catch (ParseException var13) {
            this.errorCondition = new ParseErrorException(var13.getMessage());
            throw this.errorCondition;
         } catch (Exception var14) {
            this.errorCondition = var14;
            throw var14;
         } finally {
            is.close();
         }

         return pex;
      } else {
         this.errorCondition = new ResourceNotFoundException("Unknown resource error for resource " + super.name);
         throw this.errorCondition;
      }
   }

   public void initDocument() throws Exception {
      InternalContextAdapterImpl ica = new InternalContextAdapterImpl(new VelocityContext());

      try {
         ica.pushCurrentTemplateName(super.name);
         ((SimpleNode)super.data).init(ica, super.rsvc);
      } finally {
         ica.popCurrentTemplateName();
      }

   }

   public void merge(Context context, Writer writer) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
      if (this.errorCondition != null) {
         throw this.errorCondition;
      } else if (super.data != null) {
         InternalContextAdapterImpl ica = new InternalContextAdapterImpl(context);

         try {
            ica.pushCurrentTemplateName(super.name);
            ica.setCurrentResource(this);
            ((SimpleNode)super.data).render(ica, writer);
         } finally {
            ica.popCurrentTemplateName();
            ica.setCurrentResource((Resource)null);
         }

      } else {
         String msg = "Template.merge() failure. The document is null, most likely due to parsing error.";
         super.rsvc.error(msg);
         throw new Exception(msg);
      }
   }
}

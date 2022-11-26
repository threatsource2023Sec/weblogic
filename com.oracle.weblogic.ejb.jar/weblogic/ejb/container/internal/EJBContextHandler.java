package weblogic.ejb.container.internal;

import javax.security.jacc.PolicyContextException;
import javax.xml.soap.SOAPMessage;
import weblogic.security.jacc.PolicyContextHandlerData;
import weblogic.security.service.ContextElement;
import weblogic.security.service.ContextHandler;

public class EJBContextHandler implements ContextHandler, PolicyContextHandlerData {
   public static final EJBContextHandler EMPTY = new EJBContextHandler(new String[0], new Object[0]);
   public static final String JACC_SOAP_MSG_KEY = "javax.xml.soap.SOAPMessage";
   public static final String JACC_EJB_KEY = "javax.ejb.EnterpriseBean";
   public static final String JACC_ARGUMENTS_KEY = "javax.ejb.arguments";
   private static final String[] jacc_keys = new String[]{"javax.xml.soap.SOAPMessage", "javax.ejb.EnterpriseBean", "javax.ejb.arguments"};
   private static final Object[] EMPTY_ARGS = new Object[0];
   private String[] names;
   private MethodDescriptor methodDescriptor;
   private Object[] values;
   private Object ejb;
   private SOAPMessage msg;

   public EJBContextHandler(Object ejb, Object[] values, SOAPMessage msg) {
      this.ejb = ejb;
      this.values = values != null ? values : EMPTY_ARGS;
      this.msg = msg;
   }

   public EJBContextHandler(MethodDescriptor md, Object[] values) {
      this.methodDescriptor = md;
      this.values = values != null ? values : EMPTY_ARGS;
   }

   private EJBContextHandler(String[] names, Object[] values) {
      this.names = names;
      this.values = values;
   }

   void setSOAPMessage(SOAPMessage m) {
      this.msg = m;
   }

   void setEjb(Object b) {
      this.ejb = b;
   }

   public int size() {
      return this.values.length;
   }

   public String[] getNames() {
      if (this.names == null) {
         this.names = md2paramNames(this.methodDescriptor);
      }

      return this.names;
   }

   public Object getValue(String name) {
      try {
         return this.values[this.indexOf(name)];
      } catch (ArrayIndexOutOfBoundsException var3) {
         return null;
      }
   }

   private int indexOf(String name) {
      if (this.names == null) {
         this.names = md2paramNames(this.methodDescriptor);
      }

      int split = this.names.length / 2;

      for(int i = 0; i < this.names.length; ++i) {
         if (name.equals(this.names[i])) {
            if (i < split) {
               return i;
            }

            return i - split;
         }
      }

      return -1;
   }

   public ContextElement[] getValues(String[] requested) {
      ContextElement[] result = new ContextElement[requested.length];
      int found = 0;
      String[] var4 = requested;
      int var5 = requested.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String r = var4[var6];
         int k = this.indexOf(r);
         if (k != -1) {
            result[found++] = new ContextElement(r, this.values[k]);
         }
      }

      if (found < requested.length) {
         ContextElement[] tooBig = result;
         result = new ContextElement[found];
         System.arraycopy(tooBig, 0, result, 0, found);
      }

      return result;
   }

   public Object getContext(String key) throws PolicyContextException {
      if (key == null) {
         return null;
      } else if (key.equals("javax.ejb.arguments")) {
         return this.values;
      } else if (key.equals("javax.xml.soap.SOAPMessage")) {
         return this.msg;
      } else {
         return key.equals("javax.ejb.EnterpriseBean") ? this.ejb : null;
      }
   }

   static String[] md2paramNames(MethodDescriptor md) {
      int offset = md.getMethodInfo().getMethodParams().length;
      int size = offset * 2;
      if (size == 0) {
         return new String[0];
      } else {
         String[] s = new String[size];

         for(int i = 0; i < offset; ++i) {
            s[i] = "Parameter" + (i + 1);
            s[i + offset] = "com.bea.contextelement.ejb20.Parameter" + (i + 1);
         }

         return s;
      }
   }

   public static String[] getKeys() {
      return jacc_keys;
   }
}

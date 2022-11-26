package weblogic.workarea.spi;

import java.io.IOException;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextInput;
import weblogic.workarea.WorkContextOutput;

public final class WorkContextEntryImpl implements WorkContextEntry {
   public static final String[] PROP_NAMES = new String[]{"LOCAL", "WORK", "RMI", "TRANSACTION", "JMS_QUEUE", "JMS_TOPIC", "SOAP", "MIME_HEADER", "ONEWAY"};
   private String name;
   private int propagationMode;
   private WorkContext context;
   private boolean originator;

   private WorkContextEntryImpl() {
   }

   public WorkContextEntryImpl(String name, WorkContext context, int propagationMode) {
      this.name = name;
      this.context = context;
      this.propagationMode = propagationMode;
      this.originator = true;
   }

   private WorkContextEntryImpl(String name, WorkContextInput in) throws IOException, ClassNotFoundException {
      this.name = name;
      this.propagationMode = in.readInt();
      this.context = in.readContext();
   }

   public WorkContext getWorkContext() {
      return this.context;
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public boolean equals(Object obj) {
      return obj instanceof WorkContextEntry ? ((WorkContextEntry)obj).getName().equals(this.name) : false;
   }

   public String getName() {
      return this.name;
   }

   public int getPropagationMode() {
      return this.propagationMode;
   }

   public boolean isOriginator() {
      return this.originator;
   }

   public void write(WorkContextOutput out) throws IOException {
      if (this == NULL_CONTEXT) {
         out.writeUTF("");
      } else {
         out.writeUTF(this.name);
         out.writeInt(this.propagationMode);
         out.writeContext(this.context);
      }

   }

   public static WorkContextEntry readEntry(WorkContextInput in) throws IOException, ClassNotFoundException {
      String name = in.readUTF();
      return (WorkContextEntry)(name.length() == 0 ? NULL_CONTEXT : new WorkContextEntryImpl(name, in));
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(this.name);
      sb.append(", ");
      int p = this.propagationMode;

      for(int i = 0; i < 9; ++i) {
         if ((p >>>= 1) == 1) {
            sb.append(" | ").append(PROP_NAMES[i]);
         }
      }

      return sb.toString();
   }
}

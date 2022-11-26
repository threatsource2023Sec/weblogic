package weblogic.jndi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Properties;
import javax.naming.CompoundName;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.utils.collections.ConcurrentProperties;

public final class WLNameParser implements NameParser, Externalizable {
   private static final long serialVersionUID = 8031032025200514222L;
   private static final Properties defaultProps = new ConcurrentProperties();
   private Properties nameProps;

   public WLNameParser() {
      this.nameProps = defaultProps;
   }

   public WLNameParser(String separators) {
      this(separators.toCharArray());
   }

   protected WLNameParser(char[] separators) {
      this.nameProps = (Properties)defaultProps.clone();
      if (separators.length > 0) {
         this.nameProps.put("jndi.syntax.separator", "" + separators[0]);
         if (separators.length > 1) {
            this.nameProps.put("jndi.syntax.separator2", "" + separators[1]);
         }
      } else {
         this.nameProps.put("jndi.syntax.ignorecase", "false");
         this.nameProps.put("jndi.syntax.direction", "flat");
      }

   }

   public Name parse(String name) throws NamingException {
      return new CompoundName(name, this.nameProps);
   }

   public static Name defaultParse(String name) throws NamingException {
      return new CompoundName(name, defaultProps);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         ((WLObjectOutput)out).writeProperties(this.nameProps);
      } else {
         out.writeObject(this.nameProps);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      if (in instanceof WLObjectInput) {
         this.nameProps = ((WLObjectInput)in).readProperties();
      } else {
         this.nameProps = (Properties)in.readObject();
      }

   }

   static {
      defaultProps.put("jndi.syntax.direction", "left_to_right");
      defaultProps.put("jndi.syntax.separator", ".");
      defaultProps.put("jndi.syntax.ignorecase", "false");
      defaultProps.put("jndi.syntax.escape", "\\");
      defaultProps.put("jndi.syntax.beginquote", "\"");
      defaultProps.put("jndi.syntax.endquote", "\"");
      defaultProps.put("jndi.syntax.beginquote2", "'");
      defaultProps.put("jndi.syntax.endquote2", "'");
      defaultProps.put("jndi.syntax.separator.ava", ",");
      defaultProps.put("jndi.syntax.separator.typeval", "=");
   }
}

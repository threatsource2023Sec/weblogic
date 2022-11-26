package weblogic.jms;

import java.io.Serializable;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.ServerSessionPool;
import javax.jms.TopicConnection;

/** @deprecated */
@Deprecated
public interface ServerSessionPoolFactory {
   ServerSessionPool getServerSessionPool(TopicConnection var1, int var2, boolean var3, int var4, String var5) throws JMSException;

   ServerSessionPool getServerSessionPool(QueueConnection var1, int var2, boolean var3, int var4, String var5) throws JMSException;

   ServerSessionPool createServerSessionPool(Connection var1, int var2, int var3, boolean var4, String var5, Serializable var6) throws JMSException;
}

package weblogic.jms.saf;

import java.util.Date;
import javax.management.openmbean.CompositeData;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SAFRemoteEndpointRuntimeMBean;
import weblogic.messaging.saf.SAFException;
import weblogic.store.common.PartitionNameUtils;

public class SAFRemoteEndpointCustomizer extends RuntimeMBeanDelegate implements SAFRemoteEndpointRuntimeMBean {
   private final SAFRemoteEndpointRuntimeMBean delegate;
   private final String decoratedName;

   public SAFRemoteEndpointCustomizer(String name, RuntimeMBean parent, SAFRemoteEndpointRuntimeMBean d) throws ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", name), parent, true);
      this.decoratedName = name;
      this.delegate = d;
   }

   public SAFRemoteEndpointRuntimeMBean getDelegate() {
      return this.delegate;
   }

   public String getURL() {
      return this.delegate.getURL();
   }

   public CompositeData getMessage(String messageID) throws ManagementException {
      return this.delegate.getMessage(messageID);
   }

   public String getEndpointType() {
      return this.delegate.getEndpointType();
   }

   public void pauseIncoming() throws SAFException {
      this.delegate.pauseIncoming();
   }

   public void resumeIncoming() throws SAFException {
      this.delegate.resumeIncoming();
   }

   public boolean isPausedForIncoming() {
      return this.delegate.isPausedForIncoming();
   }

   public void pauseForwarding() throws SAFException {
      this.delegate.pauseForwarding();
   }

   public void resumeForwarding() throws SAFException {
      this.delegate.resumeForwarding();
   }

   public boolean isPausedForForwarding() {
      return this.delegate.isPausedForForwarding();
   }

   public void expireAll() {
      this.delegate.expireAll();
   }

   public void purge() throws SAFException {
      this.delegate.purge();
   }

   public String getMessages(String selector, Integer timeout) throws ManagementException {
      return this.delegate.getMessages(selector, timeout);
   }

   public long getMessagesCurrentCount() {
      return this.delegate.getMessagesCurrentCount();
   }

   public long getMessagesPendingCount() {
      return this.delegate.getMessagesPendingCount();
   }

   public long getMessagesHighCount() {
      return this.delegate.getMessagesHighCount();
   }

   public long getMessagesReceivedCount() {
      return this.delegate.getMessagesReceivedCount();
   }

   public long getMessagesThresholdTime() {
      return this.delegate.getMessagesThresholdTime();
   }

   public long getBytesPendingCount() {
      return this.delegate.getBytesPendingCount();
   }

   public long getBytesCurrentCount() {
      return this.delegate.getBytesCurrentCount();
   }

   public long getBytesHighCount() {
      return this.delegate.getBytesHighCount();
   }

   public long getBytesReceivedCount() {
      return this.delegate.getBytesReceivedCount();
   }

   public long getBytesThresholdTime() {
      return this.delegate.getBytesThresholdTime();
   }

   public long getFailedMessagesTotal() {
      return this.delegate.getFailedMessagesTotal();
   }

   public Long sort(String cursorHandle, Long start, String[] fields, Boolean[] ascending) throws ManagementException {
      return this.delegate.sort(cursorHandle, start, fields, ascending);
   }

   public CompositeData getMessage(String cursorHandle, String messageID) throws ManagementException {
      return this.delegate.getMessage(cursorHandle, messageID);
   }

   public CompositeData getMessage(String cursorHandle, Long messageHandle) throws ManagementException {
      return this.delegate.getMessage(cursorHandle, messageHandle);
   }

   public Long getCursorStartPosition(String cursorHandle) throws ManagementException {
      return this.delegate.getCursorStartPosition(cursorHandle);
   }

   public Long getCursorEndPosition(String cursorHandle) throws ManagementException {
      return this.delegate.getCursorEndPosition(cursorHandle);
   }

   public CompositeData[] getItems(String cursorHandle, Long start, Integer count) throws ManagementException {
      return this.delegate.getItems(cursorHandle, start, count);
   }

   public CompositeData[] getNext(String cursorHandle, Integer count) throws ManagementException {
      return this.delegate.getNext(cursorHandle, count);
   }

   public CompositeData[] getPrevious(String cursorHandle, Integer count) throws ManagementException {
      return this.delegate.getPrevious(cursorHandle, count);
   }

   public Long getCursorSize(String cursorHandle) throws ManagementException {
      return this.delegate.getCursorSize(cursorHandle);
   }

   public Void closeCursor(String cursorHandle) throws ManagementException {
      return this.delegate.closeCursor(cursorHandle);
   }

   public long getDowntimeHigh() {
      return this.delegate.getDowntimeHigh();
   }

   public long getDowntimeTotal() {
      return this.delegate.getDowntimeTotal();
   }

   public long getUptimeHigh() {
      return this.delegate.getUptimeHigh();
   }

   public long getUptimeTotal() {
      return this.delegate.getUptimeTotal();
   }

   public Date getLastTimeConnected() {
      return this.delegate.getLastTimeConnected();
   }

   public Date getLastTimeFailedToConnect() {
      return this.delegate.getLastTimeFailedToConnect();
   }

   public Exception getLastException() {
      return this.delegate.getLastException();
   }

   public String getOperationState() {
      return this.delegate.getOperationState().toString();
   }

   public String getDecoratedName() {
      return this.decoratedName;
   }
}

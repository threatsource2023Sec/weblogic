package org.glassfish.grizzly.localization;

public final class LogMessages {
   private static final LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("org.glassfish.grizzly.localization.log");
   private static final Localizer localizer = new Localizer();

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLER_SERVICE_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.server.httphandler.service-error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLER_SERVICE_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLER_SERVICE_ERROR());
   }

   public static Localizable localizableINFO_GRIZZLY_HTTP_PARAMETERS_INVALID_CHUNK(Object arg0, Object arg1, Object arg2) {
      return messageFactory.getMessage("info.grizzly.http.parameters.invalidChunk", arg0, arg1, arg2);
   }

   public static String INFO_GRIZZLY_HTTP_PARAMETERS_INVALID_CHUNK(Object arg0, Object arg1, Object arg2) {
      return localizer.localize(localizableINFO_GRIZZLY_HTTP_PARAMETERS_INVALID_CHUNK(arg0, arg1, arg2));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_REQUEST_BODY_SKIP() {
      return messageFactory.getMessage("warning.grizzly.http.server.request.body-skip");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_REQUEST_BODY_SKIP() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_REQUEST_BODY_SKIP());
   }

   public static Localizable localizableINFO_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_INFO(Object arg0, Object arg1, Object arg2) {
      return messageFactory.getMessage("info.grizzly.http.parameters.decodeFail.info", arg0, arg1, arg2);
   }

   public static String INFO_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_INFO(Object arg0, Object arg1, Object arg2) {
      return localizer.localize(localizableINFO_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_INFO(arg0, arg1, arg2));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_REMOVE_ERROR(Object arg0, Object arg1) {
      return messageFactory.getMessage("warning.grizzly.http.servlet.attribute.listener.remove.error", arg0, arg1);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_REMOVE_ERROR(Object arg0, Object arg1) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_REMOVE_ERROR(arg0, arg1));
   }

   public static Localizable localizableFINE_GRIZZLY_HTTP_PARAMETERS_NOEQUAL(Object arg0, Object arg1, Object arg2) {
      return messageFactory.getMessage("fine.grizzly.http.parameters.noequal", arg0, arg1, arg2);
   }

   public static String FINE_GRIZZLY_HTTP_PARAMETERS_NOEQUAL(Object arg0, Object arg1, Object arg2) {
      return localizer.localize(localizableFINE_GRIZZLY_HTTP_PARAMETERS_NOEQUAL(arg0, arg1, arg2));
   }

   public static Localizable localizableWARNING_GRIZZLY_IOSTRATEGY_UNCAUGHT_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.iostrategy.uncaught.exception");
   }

   public static String WARNING_GRIZZLY_IOSTRATEGY_UNCAUGHT_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_IOSTRATEGY_UNCAUGHT_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_CONFIG_SSL_UNKNOWN_CIPHER_ERROR(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.config.ssl.unknown.cipher.error", arg0);
   }

   public static String WARNING_GRIZZLY_CONFIG_SSL_UNKNOWN_CIPHER_ERROR(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_CONFIG_SSL_UNKNOWN_CIPHER_ERROR(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_TRANSPORT_UNBINDING_CONNECTION_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.transport.unbinding-connection.exception", arg0);
   }

   public static String WARNING_GRIZZLY_TRANSPORT_UNBINDING_CONNECTION_EXCEPTION(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_TRANSPORT_UNBINDING_CONNECTION_EXCEPTION(arg0));
   }

   public static Localizable localizableSEVERE_GRIZZLY_TRANSPORT_LISTEN_INTERRUPTED_REBIND_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("severe.grizzly.transport.listen-interrupted-rebind.exception", arg0);
   }

   public static String SEVERE_GRIZZLY_TRANSPORT_LISTEN_INTERRUPTED_REBIND_EXCEPTION(Object arg0) {
      return localizer.localize(localizableSEVERE_GRIZZLY_TRANSPORT_LISTEN_INTERRUPTED_REBIND_EXCEPTION(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_TRANSPORT_NOT_STOP_STATE_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.transport.not-stop-state.exception");
   }

   public static String WARNING_GRIZZLY_TRANSPORT_NOT_STOP_STATE_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_TRANSPORT_NOT_STOP_STATE_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_OUTPUTSTREAM_ISREADY_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.servlet.outputstream.isready.error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_OUTPUTSTREAM_ISREADY_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_OUTPUTSTREAM_ISREADY_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_SOCKET_TIMEOUT_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.socket.timeout.exception", arg0);
   }

   public static String WARNING_GRIZZLY_SOCKET_TIMEOUT_EXCEPTION(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_SOCKET_TIMEOUT_EXCEPTION(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_UTILS_STATE_HOLDER_CONDITION_LISTENER_INVOCATION_ERROR() {
      return messageFactory.getMessage("warning.grizzly.utils.state.holder.condition-listener.invocation.error");
   }

   public static String WARNING_GRIZZLY_UTILS_STATE_HOLDER_CONDITION_LISTENER_INVOCATION_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_UTILS_STATE_HOLDER_CONDITION_LISTENER_INVOCATION_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_SOCKET_LINGER_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.socket.linger.exception", arg0);
   }

   public static String WARNING_GRIZZLY_SOCKET_LINGER_EXCEPTION(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_SOCKET_LINGER_EXCEPTION(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLERCHAIN_ERRORPAGE() {
      return messageFactory.getMessage("warning.grizzly.http.server.httphandlerchain.errorpage");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLERCHAIN_ERRORPAGE() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_HTTPHANDLERCHAIN_ERRORPAGE());
   }

   public static Localizable localizableWARNING_GRIZZLY_BUFFERS_OVERFLOW_EXCEPTION(Object arg0, Object arg1, Object arg2, Object arg3) {
      return messageFactory.getMessage("warning.grizzly.buffers.overflow.exception", arg0, arg1, arg2, arg3);
   }

   public static String WARNING_GRIZZLY_BUFFERS_OVERFLOW_EXCEPTION(Object arg0, Object arg1, Object arg2, Object arg3) {
      return localizer.localize(localizableWARNING_GRIZZLY_BUFFERS_OVERFLOW_EXCEPTION(arg0, arg1, arg2, arg3));
   }

   public static Localizable localizableWARNING_GRIZZLY_CONFIG_SSL_GENERAL_CONFIG_ERROR() {
      return messageFactory.getMessage("warning.grizzly.config.ssl.general.config.error");
   }

   public static String WARNING_GRIZZLY_CONFIG_SSL_GENERAL_CONFIG_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_CONFIG_SSL_GENERAL_CONFIG_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_CONNECTION_SET_WRITEBUFFER_SIZE_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.connection.set-writebuffer-size.exception");
   }

   public static String WARNING_GRIZZLY_CONNECTION_SET_WRITEBUFFER_SIZE_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_CONNECTION_SET_WRITEBUFFER_SIZE_EXCEPTION());
   }

   public static Localizable localizableINFO_GRIZZLY_HTTP_PARAMETERS_MULTIPLE_DECODING_FAIL(Object arg0) {
      return messageFactory.getMessage("info.grizzly.http.parameters.multipleDecodingFail", arg0);
   }

   public static String INFO_GRIZZLY_HTTP_PARAMETERS_MULTIPLE_DECODING_FAIL(Object arg0) {
      return localizer.localize(localizableINFO_GRIZZLY_HTTP_PARAMETERS_MULTIPLE_DECODING_FAIL(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_DESTROYED_ERROR(Object arg0, Object arg1, Object arg2) {
      return messageFactory.getMessage("warning.grizzly.http.servlet.container.object.destroyed.error", arg0, arg1, arg2);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_DESTROYED_ERROR(Object arg0, Object arg1, Object arg2) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_DESTROYED_ERROR(arg0, arg1, arg2));
   }

   public static Localizable localizableWARNING_GRIZZLY_GRACEFULSHUTDOWN_INTERRUPTED() {
      return messageFactory.getMessage("warning.grizzly.gracefulshutdown.interrupted");
   }

   public static String WARNING_GRIZZLY_GRACEFULSHUTDOWN_INTERRUPTED() {
      return localizer.localize(localizableWARNING_GRIZZLY_GRACEFULSHUTDOWN_INTERRUPTED());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_ADD_ERROR(Object arg0, Object arg1) {
      return messageFactory.getMessage("warning.grizzly.http.servlet.attribute.listener.add.error", arg0, arg1);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_ADD_ERROR(Object arg0, Object arg1) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_ATTRIBUTE_LISTENER_ADD_ERROR(arg0, arg1));
   }

   public static Localizable localizableWARNING_GRIZZLY_PROCESSOR_ERROR(Object arg0, Object arg1, Object arg2) {
      return messageFactory.getMessage("warning.grizzly.processor.error", arg0, arg1, arg2);
   }

   public static String WARNING_GRIZZLY_PROCESSOR_ERROR(Object arg0, Object arg1, Object arg2) {
      return localizer.localize(localizableWARNING_GRIZZLY_PROCESSOR_ERROR(arg0, arg1, arg2));
   }

   public static Localizable localizableWARNING_GRIZZLY_TRANSPORT_NOT_PAUSE_STATE_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.transport.not-pause-state.exception");
   }

   public static String WARNING_GRIZZLY_TRANSPORT_NOT_PAUSE_STATE_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_TRANSPORT_NOT_PAUSE_STATE_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_SOCKET_REUSEADDRESS_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.socket.reuseaddress.exception", arg0);
   }

   public static String WARNING_GRIZZLY_SOCKET_REUSEADDRESS_EXCEPTION(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_SOCKET_REUSEADDRESS_EXCEPTION(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SEVERE_GRIZZLY_HTTP_PARAMETERS_MAX_COUNT_FAIL(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.http.severe.grizzly.http.parameters.maxCountFail", arg0);
   }

   public static String WARNING_GRIZZLY_HTTP_SEVERE_GRIZZLY_HTTP_PARAMETERS_MAX_COUNT_FAIL(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SEVERE_GRIZZLY_HTTP_PARAMETERS_MAX_COUNT_FAIL(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_REQUEST_AFTERSERVICE_NOTIFICATION_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.server.request.afterservice-notification-error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_REQUEST_AFTERSERVICE_NOTIFICATION_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_REQUEST_AFTERSERVICE_NOTIFICATION_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_THREADPOOL_UNCAUGHT_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.threadpool.uncaught.exception", arg0);
   }

   public static String WARNING_GRIZZLY_THREADPOOL_UNCAUGHT_EXCEPTION(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_THREADPOOL_UNCAUGHT_EXCEPTION(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_TEMPORARY_SELECTOR_IO_CANCEL_KEY_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.temporary-selector-io.cancel-key.exception", arg0);
   }

   public static String WARNING_GRIZZLY_TEMPORARY_SELECTOR_IO_CANCEL_KEY_EXCEPTION(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_TEMPORARY_SELECTOR_IO_CANCEL_KEY_EXCEPTION(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_INPUTSTREAM_SETREADLISTENER_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.servlet.inputstream.setreadlistener.error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_INPUTSTREAM_SETREADLISTENER_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_INPUTSTREAM_SETREADLISTENER_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.servlet.non-blocking.error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_NON_BLOCKING_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_FILECACHE_GENERAL_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.server.filecache.general-error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_FILECACHE_GENERAL_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_FILECACHE_GENERAL_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_SOCKET_KEEPALIVE_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.socket.keepalive.exception", arg0);
   }

   public static String WARNING_GRIZZLY_SOCKET_KEEPALIVE_EXCEPTION(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_SOCKET_KEEPALIVE_EXCEPTION(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_FILTER_UNEXPECTED() {
      return messageFactory.getMessage("warning.grizzly.http.server.filter.unexpected");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_FILTER_UNEXPECTED() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_FILTER_UNEXPECTED());
   }

   public static Localizable localizableWARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_CREATE_SELECTOR_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.temporary-selector-pool.create-selector.exception");
   }

   public static String WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_CREATE_SELECTOR_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_CREATE_SELECTOR_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_GRACEFULSHUTDOWN_EXCEEDED(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.gracefulshutdown.exceeded", arg0);
   }

   public static String WARNING_GRIZZLY_GRACEFULSHUTDOWN_EXCEEDED(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_GRACEFULSHUTDOWN_EXCEEDED(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_INPUTSTREAM_ISREADY_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.servlet.inputstream.isready.error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_INPUTSTREAM_ISREADY_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_INPUTSTREAM_ISREADY_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_FILTER_HTTPHANDLER_INVOCATION_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.server.filter.httphandler-invocation-error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_FILTER_HTTPHANDLER_INVOCATION_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_FILTER_HTTPHANDLER_INVOCATION_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_CONTEXT_LISTENER_LOAD_ERROR(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.http.servlet.context.listener.load.error", arg0);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_CONTEXT_LISTENER_LOAD_ERROR(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_CONTEXT_LISTENER_LOAD_ERROR(arg0));
   }

   public static Localizable localizableSEVERE_GRIZZLY_COMET_ENGINE_INVALID_NOTIFICATION_HANDLER_ERROR(Object arg0) {
      return messageFactory.getMessage("severe.grizzly.comet.engine.invalid.notification-handler.error", arg0);
   }

   public static String SEVERE_GRIZZLY_COMET_ENGINE_INVALID_NOTIFICATION_HANDLER_ERROR(Object arg0) {
      return localizer.localize(localizableSEVERE_GRIZZLY_COMET_ENGINE_INVALID_NOTIFICATION_HANDLER_ERROR(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_FAILED(Object arg0, Object arg1) {
      return messageFactory.getMessage("warning.grizzly.http.server.serveroutputbuffer.file-transfer-failed", arg0, arg1);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_FAILED(Object arg0, Object arg1) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_FAILED(arg0, arg1));
   }

   public static Localizable localizableWARNING_GRIZZLY_GRACEFULSHUTDOWN_MSG(Object arg0, Object arg1, Object arg2) {
      return messageFactory.getMessage("warning.grizzly.gracefulshutdown.msg", arg0, arg1, arg2);
   }

   public static String WARNING_GRIZZLY_GRACEFULSHUTDOWN_MSG(Object arg0, Object arg1, Object arg2) {
      return localizer.localize(localizableWARNING_GRIZZLY_GRACEFULSHUTDOWN_MSG(arg0, arg1, arg2));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_REQUEST_POST_TOO_LARGE() {
      return messageFactory.getMessage("warning.grizzly.http.server.request.post-too-large");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_REQUEST_POST_TOO_LARGE() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_REQUEST_POST_TOO_LARGE());
   }

   public static Localizable localizableFINE_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_DEBUG(Object arg0, Object arg1) {
      return messageFactory.getMessage("fine.grizzly.http.parameters.decodeFail.debug", arg0, arg1);
   }

   public static String FINE_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_DEBUG(Object arg0, Object arg1) {
      return localizer.localize(localizableFINE_GRIZZLY_HTTP_PARAMETERS_DECODE_FAIL_DEBUG(arg0, arg1));
   }

   public static Localizable localizableSEVERE_GRIZZLY_CONFIG_SSL_CLASS_LOAD_FAILED_ERROR(Object arg0) {
      return messageFactory.getMessage("severe.grizzly.config.ssl.class.load.failed.error", arg0);
   }

   public static String SEVERE_GRIZZLY_CONFIG_SSL_CLASS_LOAD_FAILED_ERROR(Object arg0) {
      return localizer.localize(localizableSEVERE_GRIZZLY_CONFIG_SSL_CLASS_LOAD_FAILED_ERROR(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_TRANSPORT_START_SERVER_CONNECTION_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.transport.start-server-connection.exception", arg0);
   }

   public static String WARNING_GRIZZLY_TRANSPORT_START_SERVER_CONNECTION_EXCEPTION(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_TRANSPORT_START_SERVER_CONNECTION_EXCEPTION(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_FILTERCHAIN_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.filterchain.exception");
   }

   public static String WARNING_GRIZZLY_FILTERCHAIN_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_FILTERCHAIN_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_SESSION_LISTENER_BOUND_ERROR(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.http.servlet.session.listener.bound.error", arg0);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_SESSION_LISTENER_BOUND_ERROR(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_SESSION_LISTENER_BOUND_ERROR(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_CANCELLED(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.http.server.serveroutputbuffer.file-transfer-cancelled", arg0);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_CANCELLED(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_SERVEROUTPUTBUFFER_FILE_TRANSFER_CANCELLED(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_CONNECTION_UDPMULTICASTING_EXCEPTIONE() {
      return messageFactory.getMessage("warning.grizzly.connection.udpmulticasting.exceptione");
   }

   public static String WARNING_GRIZZLY_CONNECTION_UDPMULTICASTING_EXCEPTIONE() {
      return localizer.localize(localizableWARNING_GRIZZLY_CONNECTION_UDPMULTICASTING_EXCEPTIONE());
   }

   public static Localizable localizableWARNING_GRIZZLY_SOCKET_TCPNODELAY_EXCEPTION(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.socket.tcpnodelay.exception", arg0);
   }

   public static String WARNING_GRIZZLY_SOCKET_TCPNODELAY_EXCEPTION(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_SOCKET_TCPNODELAY_EXCEPTION(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_RESPONSE_FINISH_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.server.response.finish-error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_RESPONSE_FINISH_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_RESPONSE_FINISH_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_CONFIG_SSL_SSL_IMPLEMENTATION_LOAD_ERROR(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.config.ssl.ssl-implementation.load.error", arg0);
   }

   public static String WARNING_GRIZZLY_CONFIG_SSL_SSL_IMPLEMENTATION_LOAD_ERROR(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_CONFIG_SSL_SSL_IMPLEMENTATION_LOAD_ERROR(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_TRANSPORT_NOT_START_STATE_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.transport.not-start-state.exception");
   }

   public static String WARNING_GRIZZLY_TRANSPORT_NOT_START_STATE_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_TRANSPORT_NOT_START_STATE_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVER_REQUESTUTILS_SENDFILE_FAILED() {
      return messageFactory.getMessage("warning.grizzly.http.server.requestutils.sendfile-failed");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVER_REQUESTUTILS_SENDFILE_FAILED() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVER_REQUESTUTILS_SENDFILE_FAILED());
   }

   public static Localizable localizableWARNING_GRIZZLY_CONNECTION_GET_WRITEBUFFER_SIZE_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.connection.get-writebuffer-size.exception");
   }

   public static String WARNING_GRIZZLY_CONNECTION_GET_WRITEBUFFER_SIZE_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_CONNECTION_GET_WRITEBUFFER_SIZE_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_INITIALIZED_ERROR(Object arg0, Object arg1, Object arg2) {
      return messageFactory.getMessage("warning.grizzly.http.servlet.container.object.initialized.error", arg0, arg1, arg2);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_INITIALIZED_ERROR(Object arg0, Object arg1, Object arg2) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_CONTAINER_OBJECT_INITIALIZED_ERROR(arg0, arg1, arg2));
   }

   public static Localizable localizableWARNING_GRIZZLY_STATE_HOLDER_CALLING_CONDITIONLISTENER_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.state-holder.calling-conditionlistener.exception");
   }

   public static String WARNING_GRIZZLY_STATE_HOLDER_CALLING_CONDITIONLISTENER_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_STATE_HOLDER_CALLING_CONDITIONLISTENER_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_CONNECTION_GET_READBUFFER_SIZE_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.connection.get-readbuffer-size.exception");
   }

   public static String WARNING_GRIZZLY_CONNECTION_GET_READBUFFER_SIZE_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_CONNECTION_GET_READBUFFER_SIZE_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_COOKIE_CREATE_ERROR(Object arg0, Object arg1) {
      return messageFactory.getMessage("warning.grizzly.http.servlet.cookie.create.error", arg0, arg1);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_COOKIE_CREATE_ERROR(Object arg0, Object arg1) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_COOKIE_CREATE_ERROR(arg0, arg1));
   }

   public static Localizable localizableWARNING_GRIZZLY_SELECTOR_RUNNER_NOT_IN_STOPPED_STATE_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.selector-runner.not-in-stopped-state.exception");
   }

   public static String WARNING_GRIZZLY_SELECTOR_RUNNER_NOT_IN_STOPPED_STATE_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_SELECTOR_RUNNER_NOT_IN_STOPPED_STATE_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_OUTPUTSTREAM_SETWRITELISTENER_ERROR() {
      return messageFactory.getMessage("warning.grizzly.http.servlet.outputstream.setwritelistener.error");
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_OUTPUTSTREAM_SETWRITELISTENER_ERROR() {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_OUTPUTSTREAM_SETWRITELISTENER_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_CONNECTION_SET_READBUFFER_SIZE_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.connection.set-readbuffer-size.exception");
   }

   public static String WARNING_GRIZZLY_CONNECTION_SET_READBUFFER_SIZE_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_CONNECTION_SET_READBUFFER_SIZE_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_SELECTOR_FAILURE_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.temporary-selector-pool.selector-failure.exception");
   }

   public static String WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_SELECTOR_FAILURE_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_SELECTOR_FAILURE_EXCEPTION());
   }

   public static Localizable localizableWARNING_GRIZZLY_HTTP_SERVLET_SESSION_LISTENER_UNBOUND_ERROR(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.http.servlet.session.listener.unbound.error", arg0);
   }

   public static String WARNING_GRIZZLY_HTTP_SERVLET_SESSION_LISTENER_UNBOUND_ERROR(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_HTTP_SERVLET_SESSION_LISTENER_UNBOUND_ERROR(arg0));
   }

   public static Localizable localizableWARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_MISSES_EXCEPTION(Object arg0, Object arg1) {
      return messageFactory.getMessage("warning.grizzly.temporary-selector-pool.misses.exception", arg0, arg1);
   }

   public static String WARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_MISSES_EXCEPTION(Object arg0, Object arg1) {
      return localizer.localize(localizableWARNING_GRIZZLY_TEMPORARY_SELECTOR_POOL_MISSES_EXCEPTION(arg0, arg1));
   }

   public static Localizable localizableWARNING_GRIZZLY_TCPSELECTOR_HANDLER_ACCEPTCHANNEL_EXCEPTION() {
      return messageFactory.getMessage("warning.grizzly.tcpselector-handler.acceptchannel.exception");
   }

   public static String WARNING_GRIZZLY_TCPSELECTOR_HANDLER_ACCEPTCHANNEL_EXCEPTION() {
      return localizer.localize(localizableWARNING_GRIZZLY_TCPSELECTOR_HANDLER_ACCEPTCHANNEL_EXCEPTION());
   }

   public static Localizable localizableFINE_GRIZZLY_ASYNCQUEUE_ERROR_NOCALLBACK_ERROR(Object arg0) {
      return messageFactory.getMessage("fine.grizzly.asyncqueue.error-nocallback.error", arg0);
   }

   public static String FINE_GRIZZLY_ASYNCQUEUE_ERROR_NOCALLBACK_ERROR(Object arg0) {
      return localizer.localize(localizableFINE_GRIZZLY_ASYNCQUEUE_ERROR_NOCALLBACK_ERROR(arg0));
   }

   public static Localizable localizableSEVERE_GRIZZLY_CONFIG_SSL_ERROR() {
      return messageFactory.getMessage("severe.grizzly.config.ssl.error");
   }

   public static String SEVERE_GRIZZLY_CONFIG_SSL_ERROR() {
      return localizer.localize(localizableSEVERE_GRIZZLY_CONFIG_SSL_ERROR());
   }

   public static Localizable localizableWARNING_GRIZZLY_CONFIG_SSL_SECURE_PASSWORD_INITIALIZATION_ERROR(Object arg0) {
      return messageFactory.getMessage("warning.grizzly.config.ssl.secure.password.initialization.error", arg0);
   }

   public static String WARNING_GRIZZLY_CONFIG_SSL_SECURE_PASSWORD_INITIALIZATION_ERROR(Object arg0) {
      return localizer.localize(localizableWARNING_GRIZZLY_CONFIG_SSL_SECURE_PASSWORD_INITIALIZATION_ERROR(arg0));
   }
}

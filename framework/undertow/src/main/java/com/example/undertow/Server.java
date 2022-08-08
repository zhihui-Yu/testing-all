package com.example.undertow;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import org.xnio.Options;

/**
 * @author simple
 */
public class Server {
    /**
     * 版本： 2.2.18.Final
     *
     * 1. 初始化流程
     *      undertow 145 -> XnioWorker 310 -> NioXnioWorker 188 -> NioTcpServer -> QueuedNioTcpServer2
     *      注意：
     *          1. 在构建 undertow 时 就为 XnioWorker 初始化了 acceptThread 和 workerThread(= cpu数量)
     *          2. 当acceptThread运行时发现有accept事件，则会执行 QueuedNioTcpServer2.handleReady() 145 -> NioTcpServer.accept():393,
     *              创建出一个 NioSocketStreamConnection 对象放入需要执行队列中 （每个worker 有自己的queue，用 hash%len 计算槽位）
     *          3. WorkerThread.run() 437 一直循环worker的queue，执行相应的task
     */
    public static void main(String[] args) {
        Undertow.Builder builder = Undertow.builder();
        builder.addHttpListener(8889, "localhost");


        builder
            // undertow accepts incoming connection very quick, backlog is hard to be filled even under load test, this setting is more for DDOS protection
            // and not necessary under cloud env, here to set to match linux default value
            // to use larger value, it requires to update kernel accordingly, e.g. sysctl -w net.core.somaxconn=1024 && sysctl -w net.ipv4.tcp_max_syn_backlog=4096
            .setSocketOption(Options.BACKLOG, 1024)
            .setServerOption(UndertowOptions.DECODE_URL, Boolean.FALSE)
            .setServerOption(UndertowOptions.ENABLE_HTTP2, Boolean.TRUE)
            .setServerOption(UndertowOptions.ENABLE_RFC6265_COOKIE_VALIDATION, Boolean.TRUE)
            // since we don't use Expires or Last-Modified header, so it's not necessary to set Date header, for cache, prefer cache-control/max-age
            // refer to https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.18.1
            .setServerOption(UndertowOptions.ALWAYS_SET_DATE, Boolean.FALSE)
            .setServerOption(UndertowOptions.ALWAYS_SET_KEEP_ALIVE, Boolean.FALSE)
            // set tcp idle timeout to 620s, by default AWS ALB uses 60s, GCloud LB uses 600s, since it is always deployed with LB, longer timeout doesn't hurt
            // refer to https://cloud.google.com/load-balancing/docs/https/#timeouts_and_retries
            // refer to https://docs.aws.amazon.com/elasticloadbalancing/latest/application/application-load-balancers.html#connection-idle-timeout
            .setServerOption(UndertowOptions.NO_REQUEST_TIMEOUT, 620_000)         // 620s
            .setServerOption(UndertowOptions.SHUTDOWN_TIMEOUT, 10_000)            // 10s
            .setServerOption(UndertowOptions.MAX_ENTITY_SIZE, 10_000_000L)
            .setServerOption(UndertowOptions.RECORD_REQUEST_START_TIME, Boolean.TRUE);

        Undertow server = builder.build();
        server.start();
    }
}

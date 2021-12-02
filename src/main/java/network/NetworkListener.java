package network;

import model.NetStatus;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class NetworkListener extends EventListener {

    private static final AtomicInteger mNextRequestId = new AtomicInteger(0);
    private String mRequestId;

    private final Map<String, NetStatus> statusMap = new HashMap<>();

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    @Override
    public void callStart(@NotNull Call call) {
        super.callStart(call);
        mRequestId = String.valueOf(mNextRequestId.getAndIncrement());
        NetStatus netStatus = new NetStatus();
        netStatus.setCallStart(System.currentTimeMillis());
        statusMap.put(mRequestId, netStatus);
    }

    @Override
    public void dnsStart(@NotNull Call call, @NotNull String domainName) {
        super.dnsStart(call, domainName);
        statusMap.get(mRequestId).setDnsStart(System.currentTimeMillis());
    }

    @Override
    public void dnsEnd(@NotNull Call call, @NotNull String domainName, @NotNull List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        statusMap.get(mRequestId).setDnsEnd(System.currentTimeMillis());
    }

    @Override
    public void connectStart(@NotNull Call call, @NotNull InetSocketAddress inetSocketAddress, @NotNull Proxy proxy) {
        super.connectStart(call, inetSocketAddress, proxy);
        statusMap.get(mRequestId).setConnectStart(System.currentTimeMillis());
    }

    @Override
    public void secureConnectStart(@NotNull Call call) {
        super.secureConnectStart(call);
        statusMap.get(mRequestId).setSecureConnectStart(System.currentTimeMillis());
    }

    @Override
    public void secureConnectEnd(@NotNull Call call, @Nullable Handshake handshake) {
        super.secureConnectEnd(call, handshake);
        statusMap.get(mRequestId).setSecureConnectEnd(System.currentTimeMillis());
    }

    @Override
    public void connectEnd(@NotNull Call call, @NotNull InetSocketAddress inetSocketAddress, @NotNull Proxy proxy, @Nullable Protocol protocol) {
        super.connectEnd(call, inetSocketAddress, proxy, protocol);
        statusMap.get(mRequestId).setConnectEnd(System.currentTimeMillis());
    }

    @Override
    public void connectFailed(@NotNull Call call, @NotNull InetSocketAddress inetSocketAddress, @NotNull Proxy proxy, @Nullable Protocol protocol, @NotNull IOException ioe) {
        super.connectFailed(call, inetSocketAddress, proxy, protocol, ioe);
        System.out.println("connect failed at " + System.currentTimeMillis());
    }

    @Override
    public void requestHeadersStart(@NotNull Call call) {
        super.requestHeadersStart(call);
        statusMap.get(mRequestId).setRequestHeadersStart(System.currentTimeMillis());
    }

    @Override
    public void requestHeadersEnd(@NotNull Call call, @NotNull Request request) {
        super.requestHeadersEnd(call, request);
        statusMap.get(mRequestId).setRequestHeadersEnd(System.currentTimeMillis());
    }

    @Override
    public void requestBodyStart(@NotNull Call call) {
        super.requestBodyStart(call);
        statusMap.get(mRequestId).setRequestBodyStart(System.currentTimeMillis());
    }

    @Override
    public void requestBodyEnd(@NotNull Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
        statusMap.get(mRequestId).setRequestBodyEnd(System.currentTimeMillis());
    }

    @Override
    public void responseHeadersStart(@NotNull Call call) {
        super.responseHeadersStart(call);
        statusMap.get(mRequestId).setResponseHeadersStart(System.currentTimeMillis());
    }

    @Override
    public void responseHeadersEnd(@NotNull Call call, @NotNull Response response) {
        super.responseHeadersEnd(call, response);
        statusMap.get(mRequestId).setResponseHeadersEnd(System.currentTimeMillis());
    }

    @Override
    public void responseBodyStart(@NotNull Call call) {
        super.responseBodyStart(call);
        statusMap.get(mRequestId).setResponseBodyStart(System.currentTimeMillis());
    }

    @Override
    public void responseBodyEnd(@NotNull Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        statusMap.get(mRequestId).setResponseBodyEnd(System.currentTimeMillis());
    }

    @Override
    public void callEnd(@NotNull Call call) {
        super.callEnd(call);
        statusMap.get(mRequestId).setCallEnd(System.currentTimeMillis());
        NetStatus netStatus = statusMap.get(mRequestId);
        long callTime = netStatus.getCallEnd() - netStatus.getCallStart();
        long dnsTime = netStatus.getDnsEnd() - netStatus.getDnsStart();
        long connectTime = netStatus.getConnectEnd() - netStatus.getConnectStart();
        long secureConnectTime = netStatus.getSecureConnectEnd() - netStatus.getSecureConnectStart();
        long requestHeadersTime = netStatus.getRequestHeadersEnd() - netStatus.getRequestHeadersStart();
        long requestBodyTime = netStatus.getRequestBodyEnd() - netStatus.getRequestBodyStart();
        long responseHeadersTime = netStatus.getResponseHeadersEnd() - netStatus.getResponseHeadersStart();
        long responseBodyTime = netStatus.getResponseBodyEnd() - netStatus.getResponseBodyStart();
        if (callTime < 500) {
            System.out.println(ANSI_GREEN + "TotalTime: " + callTime
                    + "\t  dnsTime: " + dnsTime
                    + "\t  connTime: " + connectTime
                    + "\t  secureConnTime: " + secureConnectTime
                    + "\t  reqHeadersTime: " + requestHeadersTime
                    + "\t  reqBodyTime: " + requestBodyTime
                    + "\t  respHeadersTime: " + responseHeadersTime
                    + "\t  respBodyTime: " + responseBodyTime + ANSI_RESET
            );
        } else {
            System.out.println(ANSI_YELLOW + "TotalTime: " + callTime
                    + "\t  dnsTime: " + dnsTime
                    + "\t  connTime: " + connectTime
                    + "\t  secureConnTime: " + secureConnectTime
                    + "\t  reqHeadersTime: " + requestHeadersTime
                    + "\t  reqBodyTime: " + requestBodyTime
                    + "\t  respHeadersTime: " + responseHeadersTime
                    + "\t  respBodyTime: " + responseBodyTime + ANSI_RESET
            );
        }
    }

    @Override
    public void callFailed(@NotNull Call call, @NotNull IOException ioe) {
        super.callFailed(call, ioe);
        System.out.println("call failed at " + System.currentTimeMillis());
    }

}
package model;

public class NetStatus {

    private String id;
    private long callStart;
    private long dnsStart;
    private long dnsEnd;
    private long connectStart;
    private long secureConnectStart;
    private long secureConnectEnd;
    private long connectEnd;
    private long requestHeadersStart;
    private long requestHeadersEnd;
    private long requestBodyStart;
    private long requestBodyEnd;
    private long responseHeadersStart;
    private long responseHeadersEnd;
    private long responseBodyStart;
    private long responseBodyEnd;
    private long callEnd;

    public NetStatus(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCallStart() {
        return callStart;
    }

    public void setCallStart(long callStart) {
        this.callStart = callStart;
    }

    public long getDnsStart() {
        return dnsStart;
    }

    public void setDnsStart(long dnsStart) {
        this.dnsStart = dnsStart;
    }

    public long getDnsEnd() {
        return dnsEnd;
    }

    public void setDnsEnd(long dnsEnd) {
        this.dnsEnd = dnsEnd;
    }

    public long getConnectStart() {
        return connectStart;
    }

    public void setConnectStart(long connectStart) {
        this.connectStart = connectStart;
    }

    public long getSecureConnectStart() {
        return secureConnectStart;
    }

    public void setSecureConnectStart(long secureConnectStart) {
        this.secureConnectStart = secureConnectStart;
    }

    public long getSecureConnectEnd() {
        return secureConnectEnd;
    }

    public void setSecureConnectEnd(long secureConnectEnd) {
        this.secureConnectEnd = secureConnectEnd;
    }

    public long getConnectEnd() {
        return connectEnd;
    }

    public void setConnectEnd(long connectEnd) {
        this.connectEnd = connectEnd;
    }

    public long getRequestHeadersStart() {
        return requestHeadersStart;
    }

    public void setRequestHeadersStart(long requestHeadersStart) {
        this.requestHeadersStart = requestHeadersStart;
    }

    public long getRequestHeadersEnd() {
        return requestHeadersEnd;
    }

    public void setRequestHeadersEnd(long requestHeadersEnd) {
        this.requestHeadersEnd = requestHeadersEnd;
    }

    public long getRequestBodyStart() {
        return requestBodyStart;
    }

    public void setRequestBodyStart(long requestBodyStart) {
        this.requestBodyStart = requestBodyStart;
    }

    public long getRequestBodyEnd() {
        return requestBodyEnd;
    }

    public void setRequestBodyEnd(long requestBodyEnd) {
        this.requestBodyEnd = requestBodyEnd;
    }

    public long getResponseHeadersStart() {
        return responseHeadersStart;
    }

    public void setResponseHeadersStart(long responseHeadersStart) {
        this.responseHeadersStart = responseHeadersStart;
    }

    public long getResponseHeadersEnd() {
        return responseHeadersEnd;
    }

    public void setResponseHeadersEnd(long responseHeadersEnd) {
        this.responseHeadersEnd = responseHeadersEnd;
    }

    public long getResponseBodyStart() {
        return responseBodyStart;
    }

    public void setResponseBodyStart(long responseBodyStart) {
        this.responseBodyStart = responseBodyStart;
    }

    public long getResponseBodyEnd() {
        return responseBodyEnd;
    }

    public void setResponseBodyEnd(long responseBodyEnd) {
        this.responseBodyEnd = responseBodyEnd;
    }

    public long getCallEnd() {
        return callEnd;
    }

    public void setCallEnd(long callEnd) {
        this.callEnd = callEnd;
    }
}

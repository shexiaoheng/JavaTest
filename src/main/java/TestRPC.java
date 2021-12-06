import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.RpcRequest;
import network.OkHttpHelper;
import okhttp3.*;

import java.io.IOException;
import java.util.*;

public class TestRPC {

    private final String url;

    public TestRPC(String url) {
        this.url = url;
    }

    public void sysAssertEquals(String expect, String real) {
        System.out.println("assert\t" + expect + "equals\t" + real);
        assert expect.equals(real);
    }

    public void sysAssertEquals(int expect, int real) {
        System.out.println("assert\t" + expect + " == " + real);
        assert expect == real;
    }

    public void sysParams(String id, String method) {
        String builder = "params is " +
                "{\"jsonrpc\":\"2.0\",\"id\":\"" + id +
                "\",\"method\":\"" + method +
                "\"}";
        System.out.println(builder);
    }

    public void sysParams(String id, String method, String params) {
        String builder = "params is " +
                "{\"jsonrpc\":\"2.0\",\"id\":\"" + id +
                "\",\"method\":\"" + method +
                "\",\"params\":" +
                "[\"" +
                params +
                "\"]}";
        System.out.println(builder);
    }

    public void sysParams(String id, String method, String params1, boolean params2) {
        String builder = "params is " +
                "{\"jsonrpc\":\"2.0\",\"id\":\"" + id +
                "\",\"method\":\"" + method +
                "\",\"params\":" +
                "[\"" +
                params1 +
                "\"," +
                params2 +
                "]}";
        System.out.println(builder);
    }

    public void sysParams(String id, String method, String params1, String params2) {
        String builder = "params is " +
                "{\"jsonrpc\":\"2.0\",\"id\":\"" + id +
                "\",\"method\":\"" + method +
                "\",\"params\":" +
                "[\"" +
                params1 +
                "\",\"" +
                params2 +
                "\"]}";
        System.out.println(builder);
    }

    public RequestBody blockNumber() {
        List<Object> params = new ArrayList<>();
        return createReqBody("1", "eth_blockNumber", params);
    }

    public RequestBody getBlockByNumber(String number) {
        List<Object> params = new ArrayList<>();
        params.add(number);
        params.add(false);
        return createReqBody("2", "eth_getBlockByNumber", params);
    }

    public RequestBody getBlockByHash(String blockHash) {
        List<Object> params = new ArrayList<>();
        params.add(blockHash);
        params.add(false);
        return createReqBody("3", "eth_getBlockByHash", params);
    }

    public RequestBody getTransactionCount(String miner, String blockNumber) {
        List<Object> params = new ArrayList<>();
        params.add(miner);
        params.add(blockNumber);
        return createReqBody("4", "eth_getTransactionCount", params);
    }

    public RequestBody getBlockTransactionCountByHash(String blockHash) {
        List<Object> params = new ArrayList<>();
        params.add(blockHash);
        return createReqBody("5", "eth_getBlockTransactionCountByHash", params);
    }

    public RequestBody getBlockTransactionCountByNumber(String blockNumber) {
        List<Object> params = new ArrayList<>();
        params.add(blockNumber);
        return createReqBody("6", "eth_getBlockTransactionCountByNumber", params);
    }

    public RequestBody getTransactionByHash(String txHash) {
        List<Object> params = new ArrayList<>();
        params.add(txHash);
        return createReqBody("7", "eth_getTransactionByHash", params);
    }

    public RequestBody getTransactionByBlockHashAndIndex(String blockHash, String index) {
        List<Object> params = new ArrayList<>();
        params.add(blockHash);
        params.add(index);
        return createReqBody("8", "eth_getTransactionByBlockHashAndIndex", params);
    }

    public RequestBody getTransactionByBlockNumberAndIndex(String blockNumber, String index) {
        List<Object> params = new ArrayList<>();
        params.add(blockNumber);
        params.add(index);
        return createReqBody("9", "eth_getTransactionByBlockNumberAndIndex", params);
    }

    public RequestBody getTransactionReceipt(String txHash) {
        List<Object> params = new ArrayList<>();
        params.add(txHash);
        return createReqBody("10", "eth_getTransactionReceipt", params);
    }

    public RequestBody getUncleCountByBlockHash(String blockHash) {
        List<Object> params = new ArrayList<>();
        params.add(blockHash);
        return createReqBody("11", "eth_getUncleCountByBlockHash", params);
    }

    public RequestBody getUncleCountByBlockNumber(String blockNumber) {
        List<Object> params = new ArrayList<>();
        params.add(blockNumber);
        return createReqBody("12", "eth_getUncleCountByBlockNumber", params);
    }

    public RequestBody getUncleByBlockHashAndIndex(String blockHash, String index) {
        List<Object> params = new ArrayList<>();
        params.add(blockHash);
        params.add(index);
        return createReqBody("13", "eth_getUncleByBlockHashAndIndex", params);
    }

    public RequestBody getUncleByBlockNumberAndIndex(String blockNumber, String index) {
        List<Object> params = new ArrayList<>();
        params.add(blockNumber);
        params.add(index);
        return createReqBody("14", "eth_getUncleByBlockNumberAndIndex", params);
    }

    public RequestBody call(String toAddress, String blockNumber) {
        List<Object> params = new ArrayList<>();
        JsonObject object = new JsonObject();
        object.addProperty("to", toAddress);
        params.add(object);
        params.add(blockNumber);
        return createReqBody("15", "eth_call", params);
    }

    public RequestBody createReqBody(String id, String methodName, List<Object> params) {
        MediaType type = MediaType.parse("application/json");
        Gson gson = new Gson();
        RpcRequest<Object> rpc = new RpcRequest<>();
        rpc.setId(id);
        rpc.setJsonrpc("2.0");
        rpc.setMethod(methodName);
        rpc.setParams(params);
        String strReq = gson.toJson(rpc);
        return RequestBody.create(strReq, type);
    }

    public JsonObject execRpcMethod(RequestBody body) throws IOException {
        return OkHttpHelper.doHttp(url, body);
    }

    public void rpc(String url) throws IOException {
        JsonObject respBlockNumber = OkHttpHelper.doHttp(url, blockNumber());
        String blockNumber = respBlockNumber.get("result").getAsString();
        System.out.println(blockNumber);
        JsonObject blockByNumber = OkHttpHelper.doHttp(url, getBlockByNumber(blockNumber));
        System.out.println(blockByNumber);
        String reBlockNumber = blockByNumber.get("result").getAsJsonObject().get("number").getAsString();
        System.out.println(reBlockNumber);
        String reBlockHash = blockByNumber.get("result").getAsJsonObject().get("hash").getAsString();
        JsonObject blockByHash = OkHttpHelper.doHttp(url, getBlockByHash(reBlockHash));
        System.out.println(blockByHash);

    }

}

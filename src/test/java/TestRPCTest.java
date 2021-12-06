import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;


class TestRPCTest {

    private static final boolean LOOP_TX = false;
    private static final boolean LOOP_UNCLES = false;

    @Test
    void rpc() {
        try {
            String url = "https://apis-stage.ankr.com/9ae91bd778cc4d9cb26438b3aba94192/4988f3cb8b45045e98d07785193c734f/binance/full/main";
            // String url = "https://apis-stage.ankr.com/7a1d9dd39a7b4a869990f025769aa567/4988f3cb8b45045e98d07785193c734f/binance/full/main";
            TestRPC rpc = new TestRPC(url);

            // eth_blockNumber
            System.out.println("call eth_blockNumber");
            rpc.sysParams("1", "eth_blockNumber");

            JsonObject joBlockNumber = rpc.execRpcMethod(rpc.blockNumber());
            assert "1" .equals(joBlockNumber.get("id").getAsString());

            String strBlockNumber = joBlockNumber.get("result").getAsString();
            System.out.println("response block number is " + strBlockNumber + "\n");

            int intBlockNumber = Integer.parseInt(strBlockNumber.substring(2), 16);

            for (int i = 0; i < 10000; i++) {
                int _intBlockNumber = intBlockNumber - i;
                String _strBlockNumber = "0x" + Integer.toHexString(_intBlockNumber);

                // eth_getBlockByNumber
                System.out.println("call eth_getBlockByNumber");
                rpc.sysParams("2", "eth_getBlockByNumber", _strBlockNumber, false);

                JsonObject joBlockByNumber = rpc.execRpcMethod(rpc.getBlockByNumber(_strBlockNumber));
                assert "2" .equals(joBlockByNumber.get("id").getAsString());

                String reBlockNumberByNumber = joBlockByNumber.get("result").getAsJsonObject().get("number").getAsString();
                rpc.sysAssertEquals(_strBlockNumber, reBlockNumberByNumber);

                String reBlockHashByNumber = joBlockByNumber.get("result").getAsJsonObject().get("hash").getAsString();
                String reMinerByNumber = joBlockByNumber.get("result").getAsJsonObject().get("miner").getAsString();

                System.out.println("response block number is " + reBlockNumberByNumber
                        + "\nresponse block hash is " + reBlockHashByNumber
                        + "\nresponse miner is " + reMinerByNumber + "\n");


//                // eth_getBlockByHash
//                System.out.println("call eth_getBlockByHash");
//                rpc.sysParams("3", "eth_getBlockByHash", reBlockHashByNumber, false);
//
//                JsonObject joBlockByHash = rpc.execRpcMethod(rpc.getBlockByHash(reBlockHashByNumber));
//                assert "3" .equals(joBlockByHash.get("id").getAsString());
//
//                String reBlockNumberByHash = joBlockByHash.get("result").getAsJsonObject().get("number").getAsString();
//                rpc.sysAssertEquals(reBlockNumberByNumber, reBlockNumberByHash);
//
//                String reBlockHashByHash = joBlockByHash.get("result").getAsJsonObject().get("hash").getAsString();
//                rpc.sysAssertEquals(reBlockHashByNumber, reBlockHashByHash);
//
//                String reMinerByHash = joBlockByHash.get("result").getAsJsonObject().get("miner").getAsString();
//                rpc.sysAssertEquals(reMinerByNumber, reMinerByHash);
//
//                System.out.println("response block number is " + reBlockNumberByHash
//                        + "\nresponse block hash is " + reBlockHashByHash
//                        + "\nresponse miner is " + reMinerByHash + "\n");
//                JsonArray transactions = joBlockByHash.get("result").getAsJsonObject().get("transactions").getAsJsonArray();
//
//                // eth_getTransactionCount
//                System.out.println("call eth_getTransactionCount");
//                rpc.sysParams("4", "eth_getTransactionCount", reMinerByHash, _strBlockNumber);
//                JsonObject joTxCount = rpc.execRpcMethod(rpc.getTransactionCount(reMinerByHash, _strBlockNumber));
//                assert "4" .equals(joTxCount.get("id").getAsString());
//                String reTxCount = joTxCount.get("result").getAsString();
//                System.out.println("response transaction count is " + reTxCount + "\n");
//
//
//                // eth_getBlockTransactionCountByHash
//                System.out.println("call eth_getBlockTransactionCountByHash");
//                rpc.sysParams("5", "eth_getBlockTransactionCountByHash", "reBlockHashByNumber");
//
//                JsonObject joBlockTxCountByHash = rpc.execRpcMethod(rpc.getBlockTransactionCountByHash(reBlockHashByNumber));
//                assert "5" .equals(joBlockTxCountByHash.get("id").getAsString());
//
//                String reBlockTxCountByHash = joBlockTxCountByHash.get("result").getAsString();
//                int txCountByHash = Integer.parseInt(reBlockTxCountByHash.substring(2), 16);
//                rpc.sysAssertEquals(transactions.size(), txCountByHash);
//
//                System.out.println("response transaction count is " + reBlockTxCountByHash + "\n");
//
//                // eth_getBlockTransactionCountByNumber
//                System.out.println("call eth_getBlockTransactionCountByNumber");
//                rpc.sysParams("6", "eth_getBlockTransactionCountByNumber", reBlockNumberByNumber);
//                JsonObject joBlockTxCountByNumber = rpc.execRpcMethod(rpc.getBlockTransactionCountByNumber(reBlockNumberByNumber));
//                assert "6" .equals(joBlockTxCountByNumber.get("id").getAsString());
//
//                String reBlockTxCountByNumber = joBlockTxCountByNumber.get("result").getAsString();
//                int txCountByNumber = Integer.parseInt(reBlockTxCountByNumber.substring(2), 16);
//                rpc.sysAssertEquals(txCountByHash, txCountByNumber);
//                System.out.println("response transaction count is " + reBlockTxCountByNumber + "\n");
//
//                if (transactions.size() > 0) {
//                    if (LOOP_TX) {
//                        for (int j = 0; j < transactions.size(); j++) {
//                            String txHash = transactions.get(j).getAsString();
//                            // eth_getTransactionByHash
//                            System.out.println("call eth_getTransactionByHash");
//                            rpc.sysParams("7", "eth_getTransactionByHash", txHash);
//                            JsonObject joTxByHash = rpc.execRpcMethod(rpc.getTransactionByHash(txHash));
//                            assert "7" .equals(joTxByHash.get("id").getAsString());
//
//                            String reTxHashByTxHash = joTxByHash.get("result").getAsJsonObject().get("hash").getAsString();
//                            rpc.sysAssertEquals(txHash, reTxHashByTxHash);
//
//                            String reBlockNumberByTxHash = joTxByHash.get("result").getAsJsonObject().get("blockNumber").getAsString();
//                            rpc.sysAssertEquals(reBlockNumberByNumber, reBlockNumberByTxHash);
//
//                            String reBlockHashByTxHash = joTxByHash.get("result").getAsJsonObject().get("blockHash").getAsString();
//                            rpc.sysAssertEquals(reBlockHashByNumber, reBlockHashByTxHash);
//
//                            String reTxIndexByTxHash = joTxByHash.get("result").getAsJsonObject().get("transactionIndex").getAsString();
//                            rpc.sysAssertEquals(j, Integer.parseInt(reTxIndexByTxHash.substring(2), 16));
//                            System.out.println("response transaction hash is " + reTxHashByTxHash + "\n");
//
//                            // eth_getTransactionByBlockHashAndIndex
//                            System.out.println("call eth_getTransactionByBlockHashAndIndex");
//                            rpc.sysParams("8", "eth_getTransactionByBlockHashAndIndex",
//                                    reBlockHashByNumber, "0x" + Integer.toHexString(j));
//                            JsonObject joTxByBlockHash = rpc.execRpcMethod(rpc.getTransactionByBlockHashAndIndex
//                                    (reBlockHashByNumber, "0x" + Integer.toHexString(j)));
//                            assert "8" .equals(joTxByBlockHash.get("id").getAsString());
//
//                            String reTxHashByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("hash").getAsString();
//                            rpc.sysAssertEquals(txHash, reTxHashByBlockHash);
//
//                            String reBlockNumberByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("blockNumber").getAsString();
//                            rpc.sysAssertEquals(reBlockNumberByNumber, reBlockNumberByBlockHash);
//
//                            String reBlockHashByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("blockHash").getAsString();
//                            rpc.sysAssertEquals(reBlockHashByNumber, reBlockHashByBlockHash);
//
//                            String reTxIndexByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("transactionIndex").getAsString();
//                            rpc.sysAssertEquals(j, Integer.parseInt(reTxIndexByBlockHash.substring(2), 16));
//                            System.out.println("response transaction hash is " + reTxHashByBlockHash + "\n");
//
//                            // eth_getTransactionByBlockNumberAndIndex
//                            System.out.println("call eth_getTransactionByBlockNumberAndIndex");
//                            rpc.sysParams("9", "eth_getTransactionByBlockNumberAndIndex"
//                                    , reBlockNumberByNumber, "0x" + Integer.toHexString(j));
//                            JsonObject joTxByBlockNumber = rpc.execRpcMethod(rpc.getTransactionByBlockNumberAndIndex
//                                    (reBlockNumberByNumber, "0x" + Integer.toHexString(j)));
//                            assert "9" .equals(joTxByBlockNumber.get("id").getAsString());
//
//                            String reTxHashByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("hash").getAsString();
//                            rpc.sysAssertEquals(txHash, reTxHashByBlockNumber);
//
//                            String reBlockNumberByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("blockNumber").getAsString();
//                            rpc.sysAssertEquals(reBlockNumberByNumber, reBlockNumberByBlockNumber);
//
//                            String reBlockHashByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("blockHash").getAsString();
//                            rpc.sysAssertEquals(reBlockHashByNumber, reBlockHashByBlockNumber);
//
//                            String reTxIndexByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("transactionIndex").getAsString();
//                            rpc.sysAssertEquals(j, Integer.parseInt(reTxIndexByBlockNumber.substring(2), 16));
//                            System.out.println("response transaction hash is " + reTxHashByBlockNumber + "\n");
//
//                            // eth_getTransactionReceipt
//                            System.out.println("call eth_getTransactionReceipt");
//                            rpc.sysParams("10", "eth_getTransactionReceipt", txHash);
//                            JsonObject joTxReceipt = rpc.execRpcMethod(rpc.getTransactionReceipt(txHash));
//                            assert "10" .equals(joTxReceipt.get("id").getAsString());
//
//                            String reBlockHashByReceipt = joTxReceipt.get("result").getAsJsonObject().get("blockHash").getAsString();
//                            rpc.sysAssertEquals(reBlockHashByNumber, reBlockHashByReceipt);
//
//                            String reBlockNumberByReceipt = joTxReceipt.get("result").getAsJsonObject().get("blockNumber").getAsString();
//                            rpc.sysAssertEquals(reBlockNumberByNumber, reBlockNumberByReceipt);
//
//                            String reTxHashByReceipt = joTxReceipt.get("result").getAsJsonObject().get("transactionHash").getAsString();
//                            rpc.sysAssertEquals(txHash, reTxHashByReceipt);
//
//                            String reTxIndexByReceipt = joTxReceipt.get("result").getAsJsonObject().get("transactionIndex").getAsString();
//                            rpc.sysAssertEquals(j, Integer.parseInt(reTxIndexByReceipt.substring(2), 16));
//                            System.out.println("response receipt hash is " + reTxHashByReceipt + "\n");
//                        }
//                    } else {
//                        String txHash = transactions.get(0).getAsString();
//                        // eth_getTransactionByHash
//                        System.out.println("call eth_getTransactionByHash");
//                        rpc.sysParams("7", "eth_getTransactionByHash", txHash);
//                        JsonObject joTxByHash = rpc.execRpcMethod(rpc.getTransactionByHash(txHash));
//                        assert "7" .equals(joTxByHash.get("id").getAsString());
//
//                        String reTxHashByTxHash = joTxByHash.get("result").getAsJsonObject().get("hash").getAsString();
//                        rpc.sysAssertEquals(txHash, reTxHashByTxHash);
//
//                        String reBlockNumberByTxHash = joTxByHash.get("result").getAsJsonObject().get("blockNumber").getAsString();
//                        rpc.sysAssertEquals(reBlockNumberByNumber, reBlockNumberByTxHash);
//
//                        String reBlockHashByTxHash = joTxByHash.get("result").getAsJsonObject().get("blockHash").getAsString();
//                        rpc.sysAssertEquals(reBlockHashByNumber, reBlockHashByTxHash);
//
//                        String reTxIndexByTxHash = joTxByHash.get("result").getAsJsonObject().get("transactionIndex").getAsString();
//                        rpc.sysAssertEquals(0, Integer.parseInt(reTxIndexByTxHash.substring(2), 16));
//                        System.out.println("response transaction hash is " + reTxHashByTxHash + "\n");
//
//                        // eth_getTransactionByBlockHashAndIndex
//                        System.out.println("call eth_getTransactionByBlockHashAndIndex");
//                        rpc.sysParams("8", "eth_getTransactionByBlockHashAndIndex",
//                                reBlockHashByNumber, "0x" + Integer.toHexString(0));
//                        JsonObject joTxByBlockHash = rpc.execRpcMethod(rpc.getTransactionByBlockHashAndIndex
//                                (reBlockHashByNumber, "0x" + Integer.toHexString(0)));
//                        assert "8" .equals(joTxByBlockHash.get("id").getAsString());
//
//                        String reTxHashByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("hash").getAsString();
//                        rpc.sysAssertEquals(txHash, reTxHashByBlockHash);
//
//                        String reBlockNumberByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("blockNumber").getAsString();
//                        rpc.sysAssertEquals(reBlockNumberByNumber, reBlockNumberByBlockHash);
//
//                        String reBlockHashByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("blockHash").getAsString();
//                        rpc.sysAssertEquals(reBlockHashByNumber, reBlockHashByBlockHash);
//
//                        String reTxIndexByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("transactionIndex").getAsString();
//                        rpc.sysAssertEquals(0, Integer.parseInt(reTxIndexByBlockHash.substring(2), 16));
//                        System.out.println("response transaction hash is " + reTxHashByBlockHash + "\n");
//
//                        // eth_getTransactionByBlockNumberAndIndex
//                        System.out.println("call eth_getTransactionByBlockNumberAndIndex");
//                        rpc.sysParams("9", "eth_getTransactionByBlockNumberAndIndex"
//                                , reBlockNumberByNumber, "0x" + Integer.toHexString(0));
//                        JsonObject joTxByBlockNumber = rpc.execRpcMethod(rpc.getTransactionByBlockNumberAndIndex
//                                (reBlockNumberByNumber, "0x" + Integer.toHexString(0)));
//                        assert "9" .equals(joTxByBlockNumber.get("id").getAsString());
//
//                        String reTxHashByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("hash").getAsString();
//                        rpc.sysAssertEquals(txHash, reTxHashByBlockNumber);
//
//                        String reBlockNumberByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("blockNumber").getAsString();
//                        rpc.sysAssertEquals(reBlockNumberByNumber, reBlockNumberByBlockNumber);
//
//                        String reBlockHashByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("blockHash").getAsString();
//                        rpc.sysAssertEquals(reBlockHashByNumber, reBlockHashByBlockNumber);
//
//                        String reTxIndexByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("transactionIndex").getAsString();
//                        rpc.sysAssertEquals(0, Integer.parseInt(reTxIndexByBlockNumber.substring(2), 16));
//                        System.out.println("response transaction hash is " + reTxHashByBlockNumber + "\n");
//
//                        // eth_getTransactionReceipt
//                        System.out.println("call eth_getTransactionReceipt");
//                        rpc.sysParams("10", "eth_getTransactionReceipt", txHash);
//                        JsonObject joTxReceipt = rpc.execRpcMethod(rpc.getTransactionReceipt(txHash));
//                        assert "10" .equals(joTxReceipt.get("id").getAsString());
//
//                        String reBlockHashByReceipt = joTxReceipt.get("result").getAsJsonObject().get("blockHash").getAsString();
//                        rpc.sysAssertEquals(reBlockHashByNumber, reBlockHashByReceipt);
//
//                        String reBlockNumberByReceipt = joTxReceipt.get("result").getAsJsonObject().get("blockNumber").getAsString();
//                        rpc.sysAssertEquals(reBlockNumberByNumber, reBlockNumberByReceipt);
//
//                        String reTxHashByReceipt = joTxReceipt.get("result").getAsJsonObject().get("transactionHash").getAsString();
//                        rpc.sysAssertEquals(txHash, reTxHashByReceipt);
//
//                        String reTxIndexByReceipt = joTxReceipt.get("result").getAsJsonObject().get("transactionIndex").getAsString();
//                        rpc.sysAssertEquals(0, Integer.parseInt(reTxIndexByReceipt.substring(2), 16));
//                        System.out.println("response receipt hash is " + reTxHashByReceipt + "\n");
//                    }
//                }
//
//                JsonArray unclesArray = joBlockByNumber.get("result").getAsJsonObject().get("uncles").getAsJsonArray();
//
//                if (unclesArray.size() > 0) {
//                    // eth_getUncleCountByBlockHash
//                    System.out.println("call eth_getUncleCountByBlockHash");
//                    rpc.sysParams("11", "eth_getUncleCountByBlockHash", reBlockHashByNumber);
//                    JsonObject joUncleCountByHash = rpc.execRpcMethod(rpc.getUncleCountByBlockHash(reBlockHashByNumber));
//                    assert "11" .equals(joUncleCountByHash.get("id").getAsString());
//
//                    String reUncleCountByHash = joUncleCountByHash.get("result").getAsString();
//                    rpc.sysAssertEquals(unclesArray.size(), Integer.parseInt(reUncleCountByHash.substring(2), 16));
//                    System.out.println("response uncle count is " + reUncleCountByHash + "\n");
//
//                    // eth_getUncleCountByBlockNumber
//                    System.out.println("call eth_getUncleCountByBlockNumber");
//                    rpc.sysParams("12", "eth_getUncleCountByBlockNumber", reBlockNumberByNumber);
//                    JsonObject joUncleCountByNumber = rpc.execRpcMethod(rpc.getUncleCountByBlockNumber(reBlockNumberByNumber));
//                    assert "12" .equals(joUncleCountByNumber.get("id").getAsString());
//
//                    String reUncleCountByNumber = joUncleCountByNumber.get("result").getAsString();
//                    rpc.sysAssertEquals(unclesArray.size(), Integer.parseInt(reUncleCountByNumber.substring(2), 16));
//                    System.out.println("response uncle count is " + reUncleCountByNumber + "\n");
//
//                    if (LOOP_UNCLES) {
//                        for (int j = 0; j < unclesArray.size(); j++) {
//                            // eth_getUncleByBlockHashAndIndex
//                            System.out.println("call eth_getUncleByBlockHashAndIndex");
//                            rpc.sysParams("13", "eth_getUncleByBlockHashAndIndex", reBlockHashByNumber,
//                                    "0x" + Integer.toHexString(j));
//                            JsonObject joUncleByHashAndIndex = rpc.execRpcMethod(rpc.getUncleByBlockHashAndIndex
//                                    (reBlockHashByNumber, "0x" + Integer.toHexString(j)));
//                            assert "13" .equals(joUncleByHashAndIndex.get("id").getAsString());
//
//                            String reUncleHashByHash = joUncleByHashAndIndex.get("result").getAsJsonObject()
//                                    .get("hash").getAsString();
//                            rpc.sysAssertEquals(unclesArray.get(j).getAsString(), reUncleHashByHash);
//                            System.out.println("response uncle hash is " + reUncleHashByHash + "\n");
//
//                            // eth_getUncleByBlockNumberAndIndex
//                            System.out.println("call eth_getUncleByBlockNumberAndIndex");
//                            rpc.sysParams("14", "eth_getUncleByBlockNumberAndIndex", reBlockNumberByNumber,
//                                    "0x" + Integer.toHexString(j));
//                            JsonObject joUncleByNumberAndIndex = rpc.execRpcMethod(rpc.getUncleByBlockNumberAndIndex
//                                    (reBlockNumberByNumber, "0x" + Integer.toHexString(j)));
//                            assert "14" .equals(joUncleByNumberAndIndex.get("id").getAsString());
//
//                            String reUncleHashByNumber = joUncleByHashAndIndex.get("result").getAsJsonObject()
//                                    .get("hash").getAsString();
//                            rpc.sysAssertEquals(unclesArray.get(j).getAsString(), reUncleHashByNumber);
//                            System.out.println("response uncle hash is " + reUncleHashByNumber + "\n");
//                        }
//                    } else {
//                        // eth_getUncleByBlockHashAndIndex
//                        System.out.println("call eth_getUncleByBlockHashAndIndex");
//                        rpc.sysParams("13", "eth_getUncleByBlockHashAndIndex", reBlockHashByNumber,
//                                "0x" + Integer.toHexString(0));
//                        JsonObject joUncleByHashAndIndex = rpc.execRpcMethod(rpc.getUncleByBlockHashAndIndex
//                                (reBlockHashByNumber, "0x" + Integer.toHexString(0)));
//                        assert "13" .equals(joUncleByHashAndIndex.get("id").getAsString());
//                        String reUncleHashByHash = joUncleByHashAndIndex.get("result").getAsJsonObject()
//                                .get("hash").getAsString();
//                        rpc.sysAssertEquals(unclesArray.get(0).getAsString(), reUncleHashByHash);
//                        System.out.println("response uncle hash is " + reUncleHashByHash + "\n");
//
//                        // eth_getUncleByBlockNumberAndIndex
//                        System.out.println("call eth_getUncleByBlockNumberAndIndex");
//                        rpc.sysParams("14", "eth_getUncleByBlockNumberAndIndex", reBlockNumberByNumber,
//                                "0x" + Integer.toHexString(0));
//                        JsonObject joUncleByNumberAndIndex = rpc.execRpcMethod(rpc.getUncleByBlockNumberAndIndex
//                                (reBlockNumberByNumber, "0x" + Integer.toHexString(0)));
//                        assert "14" .equals(joUncleByNumberAndIndex.get("id").getAsString());
//
//                        String reUncleHashByNumber = joUncleByHashAndIndex.get("result").getAsJsonObject()
//                                .get("hash").getAsString();
//                        rpc.sysAssertEquals(unclesArray.get(0).getAsString(), reUncleHashByNumber);
//                        System.out.println("response uncle hash is " + reUncleHashByNumber + "\n");
//                    }
//                }

                // eth_call
                System.out.println("call eth_call");
                System.out.println("params is "
                        + "{\"jsonrpc\":\"2.0\",\"id\":\"15\",\"method\":\"eth_call\"," +
                        "\"params\":[{\"to\":\"" + reMinerByNumber + "\"},\"" + _strBlockNumber + "\"]}");
                JsonObject joCall = rpc.execRpcMethod(rpc.call(reMinerByNumber, _strBlockNumber));
                assert "15" .equals(joCall.get("id").getAsString());
                String result = joCall.get("result").getAsString();
                System.out.println("response result is " + result + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testPrintColor() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";
        System.out.println(ANSI_RED + "This text color" + ANSI_RESET);
        System.out.println(ANSI_BLACK + "This text color" + ANSI_RESET);
        System.out.println(ANSI_GREEN + "This text color" + ANSI_RESET);
        System.out.println(ANSI_YELLOW + "This text color" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "This text color" + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "This text color" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "This text color" + ANSI_RESET);
        System.out.println(ANSI_WHITE + "This text color" + ANSI_RESET);

        String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        String ANSI_RED_BACKGROUND = "\u001B[41m";
        String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
        String ANSI_BLUE_BACKGROUND = "\u001B[44m";
        String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
        String ANSI_CYAN_BACKGROUND = "\u001B[46m";
        String ANSI_WHITE_BACKGROUND = "\u001B[47m";

        System.out.println(ANSI_BLACK_BACKGROUND + "This text has a background" + ANSI_RESET);
        System.out.println(ANSI_RED_BACKGROUND + "This text has a background" + ANSI_RESET);
        System.out.println(ANSI_GREEN_BACKGROUND + "This text has a background" + ANSI_RESET);
        System.out.println(ANSI_YELLOW_BACKGROUND + "This text has a background" + ANSI_RESET);
        System.out.println(ANSI_BLUE_BACKGROUND + "This text has a background" + ANSI_RESET);
        System.out.println(ANSI_PURPLE_BACKGROUND + "This text has a background" + ANSI_RESET);
        System.out.println(ANSI_CYAN_BACKGROUND + "This text has a background" + ANSI_RESET);
        System.out.println(ANSI_WHITE_BACKGROUND + "This text has a background" + ANSI_RESET);
    }
}

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
            String url = "https://apis.ankr.com/27d292bd143e4d1594bbc66f79b4e157/06c3b7c60e1fccfdd506197e9158e1f8/binance/full/main";
            TestRPC rpc = new TestRPC(url);

            // eth_blockNumber
            System.out.println("call eth_blockNumber");
            JsonObject joBlockNumber = rpc.execRpcMethod(rpc.blockNumber());
            assert "1" .equals(joBlockNumber.get("id").getAsString());
            String strBlockNumber = joBlockNumber.get("result").getAsString();
            int intBlockNumber = Integer.parseInt(strBlockNumber.substring(2), 16);
            System.out.println("response block number is " + strBlockNumber + "\n");

            for (int i = 0; i < 300; i++) {
                int _intBlockNumber = intBlockNumber - i;
                String _strBlockNumber = "0x" + Integer.toHexString(_intBlockNumber);

                // eth_getBlockByNumber
                System.out.println("call eth_getBlockByNumber params is " + _strBlockNumber);
                JsonObject joBlockByNumber = rpc.execRpcMethod(rpc.getBlockByNumber(_strBlockNumber));
                assert "2" .equals(joBlockByNumber.get("id").getAsString());
                String reBlockNumberByNumber = joBlockByNumber.get("result").getAsJsonObject().get("number").getAsString();
                assert _strBlockNumber.equals(reBlockNumberByNumber);
                String reBlockHashByNumber = joBlockByNumber.get("result").getAsJsonObject().get("hash").getAsString();
                String reMinerByNumber = joBlockByNumber.get("result").getAsJsonObject().get("miner").getAsString();
                System.out.println("response block number is " + reBlockNumberByNumber
                        + "\nresponse block hash is" + reBlockHashByNumber + "\n");

                // eth_getBlockByHash
                System.out.println("call eth_getBlockByHash params is " + reBlockHashByNumber);
                JsonObject joBlockByHash = rpc.execRpcMethod(rpc.getBlockByHash(reBlockHashByNumber));
                assert "3" .equals(joBlockByHash.get("id").getAsString());
                String reBlockNumberByHash = joBlockByHash.get("result").getAsJsonObject().get("number").getAsString();
                assert reBlockNumberByNumber.equals(reBlockNumberByHash);
                String reBlockHashByHash = joBlockByHash.get("result").getAsJsonObject().get("hash").getAsString();
                assert reBlockHashByNumber.equals(reBlockHashByHash);
                String reMinerByHash = joBlockByHash.get("result").getAsJsonObject().get("miner").getAsString();
                assert reMinerByNumber.equals(reMinerByHash);
                System.out.println("response block number is " + reBlockNumberByHash
                        + "\nresponse block hash is" + reBlockHashByHash + "\n");
                JsonArray transactions = joBlockByHash.get("result").getAsJsonObject().get("transactions").getAsJsonArray();

                // eth_getTransactionCount
                System.out.println("call eth_getTransactionCount params is " + reMinerByHash + " | " + _strBlockNumber);
                JsonObject joTxCount = rpc.execRpcMethod(rpc.getTransactionCount(reMinerByHash, _strBlockNumber));
                System.out.println(joTxCount);
                assert "4" .equals(joTxCount.get("id").getAsString());
                String reTxCount = joTxCount.get("result").getAsString();
                System.out.println("response transaction count is " + reTxCount + "\n");

                // eth_getBlockTransactionCountByHash
                System.out.println("call eth_getBlockTransactionCountByHash params is " + reBlockHashByNumber);
                JsonObject joBlockTxCountByHash = rpc.execRpcMethod(rpc.getBlockTransactionCountByHash(reBlockHashByNumber));
                assert "5" .equals(joBlockTxCountByHash.get("id").getAsString());
                String reBlockTxCountByHash = joBlockTxCountByHash.get("result").getAsString();
                int txCountByHash = Integer.parseInt(reBlockTxCountByHash.substring(2), 16);
                assert transactions.size() == txCountByHash;
                System.out.println("response transaction count is " + reBlockTxCountByHash + "\n");

                // eth_getBlockTransactionCountByNumber
                System.out.println("call eth_getBlockTransactionCountByNumber params is " + reBlockNumberByNumber);
                JsonObject joBlockTxCountByNumber = rpc.execRpcMethod(rpc.getBlockTransactionCountByNumber(reBlockNumberByNumber));
                assert "6" .equals(joBlockTxCountByNumber.get("id").getAsString());
                String reBlockTxCountByNumber = joBlockTxCountByNumber.get("result").getAsString();
                int txCountByNumber = Integer.parseInt(reBlockTxCountByNumber.substring(2), 16);
                assert txCountByHash == txCountByNumber;
                System.out.println("response transaction count is " + reBlockTxCountByNumber + "\n");

                if (transactions.size() > 0) {
                    if (LOOP_TX) {
                        for (int j = 0; j < transactions.size(); j++) {
                            String txHash = transactions.get(j).getAsString();
                            // eth_getTransactionByHash
                            System.out.println("call eth_getTransactionByHash params is " + txHash);
                            JsonObject joTxByHash = rpc.execRpcMethod(rpc.getTransactionByHash(txHash));
                            assert "7" .equals(joTxByHash.get("id").getAsString());
                            String reTxHashByTxHash = joTxByHash.get("result").getAsJsonObject().get("hash").getAsString();
                            assert txHash.equals(reTxHashByTxHash);
                            String reBlockNumberByTxHash = joTxByHash.get("result").getAsJsonObject().get("blockNumber").getAsString();
                            assert reBlockNumberByNumber.equals(reBlockNumberByTxHash);
                            String reBlockHashByTxHash = joTxByHash.get("result").getAsJsonObject().get("blockHash").getAsString();
                            assert reBlockHashByNumber.equals(reBlockHashByTxHash);
                            String reTxIndexByTxHash = joTxByHash.get("result").getAsJsonObject().get("transactionIndex").getAsString();
                            assert j == Integer.parseInt(reTxIndexByTxHash.substring(2), 16);
                            System.out.println("response transaction hash is " + reTxHashByTxHash + "\n");

                            // eth_getTransactionByBlockHashAndIndex
                            System.out.println("call eth_getTransactionByBlockHashAndIndex params is "
                                    + reBlockHashByNumber + " | 0x" + Integer.toHexString(j));
                            JsonObject joTxByBlockHash = rpc.execRpcMethod(rpc.getTransactionByBlockHashAndIndex
                                    (reBlockHashByNumber, "0x" + Integer.toHexString(j)));
                            assert "8" .equals(joTxByBlockHash.get("id").getAsString());
                            String reTxHashByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("hash").getAsString();
                            assert txHash.equals(reTxHashByBlockHash);
                            String reBlockNumberByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("blockNumber").getAsString();
                            assert reBlockNumberByNumber.equals(reBlockNumberByBlockHash);
                            String reBlockHashByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("blockHash").getAsString();
                            assert reBlockHashByNumber.equals(reBlockHashByBlockHash);
                            String reTxIndexByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("transactionIndex").getAsString();
                            assert j == Integer.parseInt(reTxIndexByBlockHash.substring(2), 16);
                            System.out.println("response transaction hash is " + reTxHashByBlockHash + "\n");

                            // eth_getTransactionByBlockNumberAndIndex
                            System.out.println("call eth_getTransactionByBlockNumberAndIndex params is "
                                    + reBlockNumberByNumber + " | 0x" + Integer.toHexString(j));
                            JsonObject joTxByBlockNumber = rpc.execRpcMethod(rpc.getTransactionByBlockNumberAndIndex
                                    (reBlockNumberByNumber, "0x" + Integer.toHexString(j)));
                            assert "9" .equals(joTxByBlockNumber.get("id").getAsString());
                            String reTxHashByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("hash").getAsString();
                            assert txHash.equals(reTxHashByBlockNumber);
                            String reBlockNumberByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("blockNumber").getAsString();
                            assert reBlockNumberByNumber.equals(reBlockNumberByBlockNumber);
                            String reBlockHashByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("blockHash").getAsString();
                            assert reBlockHashByNumber.equals(reBlockHashByBlockNumber);
                            String reTxIndexByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("transactionIndex").getAsString();
                            assert j == Integer.parseInt(reTxIndexByBlockNumber.substring(2), 16);
                            System.out.println("response transaction hash is " + reTxHashByBlockNumber + "\n");

                            // eth_getTransactionReceipt
                            System.out.println("call eth_getTransactionReceipt params is " + txHash);
                            JsonObject joTxReceipt = rpc.execRpcMethod(rpc.getTransactionReceipt(txHash));
                            assert "10" .equals(joTxReceipt.get("id").getAsString());
                            String reBlockHashByReceipt = joTxReceipt.get("result").getAsJsonObject().get("blockHash").getAsString();
                            assert reBlockHashByNumber.equals(reBlockHashByReceipt);
                            String reBlockNumberByReceipt = joTxReceipt.get("result").getAsJsonObject().get("blockNumber").getAsString();
                            assert reBlockNumberByNumber.equals(reBlockNumberByReceipt);
                            String reTxHashByReceipt = joTxReceipt.get("result").getAsJsonObject().get("transactionHash").getAsString();
                            assert txHash.equals(reTxHashByReceipt);
                            String reTxIndexByReceipt = joTxReceipt.get("result").getAsJsonObject().get("transactionIndex").getAsString();
                            assert j == Integer.parseInt(reTxIndexByReceipt.substring(2), 16);
                            System.out.println("response receipt hash is " + reTxHashByReceipt + "\n");
                        }
                    } else {
                        String txHash = transactions.get(0).getAsString();
                        // eth_getTransactionByHash
                        System.out.println("call eth_getTransactionByHash params is " + txHash);
                        JsonObject joTxByHash = rpc.execRpcMethod(rpc.getTransactionByHash(txHash));
                        assert "7" .equals(joTxByHash.get("id").getAsString());
                        String reTxHashByTxHash = joTxByHash.get("result").getAsJsonObject().get("hash").getAsString();
                        assert txHash.equals(reTxHashByTxHash);
                        String reBlockNumberByTxHash = joTxByHash.get("result").getAsJsonObject().get("blockNumber").getAsString();
                        assert reBlockNumberByNumber.equals(reBlockNumberByTxHash);
                        String reBlockHashByTxHash = joTxByHash.get("result").getAsJsonObject().get("blockHash").getAsString();
                        assert reBlockHashByNumber.equals(reBlockHashByTxHash);
                        String reTxIndexByTxHash = joTxByHash.get("result").getAsJsonObject().get("transactionIndex").getAsString();
                        assert 0 == Integer.parseInt(reTxIndexByTxHash.substring(2), 16);
                        System.out.println("response transaction hash is " + reTxHashByTxHash + "\n");

                        // eth_getTransactionByBlockHashAndIndex
                        System.out.println("call eth_getTransactionByBlockHashAndIndex params is "
                                + reBlockHashByNumber + " | 0x" + Integer.toHexString(0));
                        JsonObject joTxByBlockHash = rpc.execRpcMethod(rpc.getTransactionByBlockHashAndIndex
                                (reBlockHashByNumber, "0x" + Integer.toHexString(0)));
                        assert "8" .equals(joTxByBlockHash.get("id").getAsString());
                        String reTxHashByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("hash").getAsString();
                        assert txHash.equals(reTxHashByBlockHash);
                        String reBlockNumberByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("blockNumber").getAsString();
                        assert reBlockNumberByNumber.equals(reBlockNumberByBlockHash);
                        String reBlockHashByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("blockHash").getAsString();
                        assert reBlockHashByNumber.equals(reBlockHashByBlockHash);
                        String reTxIndexByBlockHash = joTxByBlockHash.get("result").getAsJsonObject().get("transactionIndex").getAsString();
                        assert 0 == Integer.parseInt(reTxIndexByBlockHash.substring(2), 16);
                        System.out.println("response transaction hash is " + reTxHashByBlockHash + "\n");

                        // eth_getTransactionByBlockNumberAndIndex
                        System.out.println("call eth_getTransactionByBlockNumberAndIndex params is "
                                + reBlockNumberByNumber + " | 0x" + Integer.toHexString(0));
                        JsonObject joTxByBlockNumber = rpc.execRpcMethod(rpc.getTransactionByBlockNumberAndIndex
                                (reBlockNumberByNumber, "0x" + Integer.toHexString(0)));
                        assert "9" .equals(joTxByBlockNumber.get("id").getAsString());
                        String reTxHashByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("hash").getAsString();
                        assert txHash.equals(reTxHashByBlockNumber);
                        String reBlockNumberByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("blockNumber").getAsString();
                        assert reBlockNumberByNumber.equals(reBlockNumberByBlockNumber);
                        String reBlockHashByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("blockHash").getAsString();
                        assert reBlockHashByNumber.equals(reBlockHashByBlockNumber);
                        String reTxIndexByBlockNumber = joTxByBlockNumber.get("result").getAsJsonObject().get("transactionIndex").getAsString();
                        assert 0 == Integer.parseInt(reTxIndexByBlockNumber.substring(2), 16);
                        System.out.println("response transaction hash is " + reTxHashByBlockNumber + "\n");

                        // eth_getTransactionReceipt
                        System.out.println("call eth_getTransactionReceipt params is " + txHash);
                        JsonObject joTxReceipt = rpc.execRpcMethod(rpc.getTransactionReceipt(txHash));
                        assert "10" .equals(joTxReceipt.get("id").getAsString());
                        String reBlockHashByReceipt = joTxReceipt.get("result").getAsJsonObject().get("blockHash").getAsString();
                        assert reBlockHashByNumber.equals(reBlockHashByReceipt);
                        String reBlockNumberByReceipt = joTxReceipt.get("result").getAsJsonObject().get("blockNumber").getAsString();
                        assert reBlockNumberByNumber.equals(reBlockNumberByReceipt);
                        String reTxHashByReceipt = joTxReceipt.get("result").getAsJsonObject().get("transactionHash").getAsString();
                        assert txHash.equals(reTxHashByReceipt);
                        String reTxIndexByReceipt = joTxReceipt.get("result").getAsJsonObject().get("transactionIndex").getAsString();
                        assert 0 == Integer.parseInt(reTxIndexByReceipt.substring(2), 16);
                        System.out.println("response receipt hash is " + reTxHashByReceipt + "\n");
                    }
                }

                JsonArray unclesArray = joBlockByNumber.get("result").getAsJsonObject().get("uncles").getAsJsonArray();

                if (unclesArray.size() > 0) {
                    // eth_getUncleCountByBlockHash
                    System.out.println("call eth_getUncleCountByBlockHash params is " + reBlockHashByNumber);
                    JsonObject joUncleCountByHash = rpc.execRpcMethod(rpc.getUncleCountByBlockHash(reBlockHashByNumber));
                    assert "11" .equals(joUncleCountByHash.get("id").getAsString());
                    String reUncleCountByHash = joUncleCountByHash.get("result").getAsString();
                    assert unclesArray.size() == Integer.parseInt(reUncleCountByHash.substring(2), 16);
                    System.out.println("response uncle count is " + reUncleCountByHash + "\n");

                    // eth_getUncleCountByBlockNumber
                    System.out.println("call eth_getUncleCountByBlockNumber params is " + reBlockNumberByNumber);
                    JsonObject joUncleCountByNumber = rpc.execRpcMethod(rpc.getUncleCountByBlockNumber(reBlockNumberByNumber));
                    assert "12" .equals(joUncleCountByNumber.get("id").getAsString());
                    String reUncleCountByNumber = joUncleCountByNumber.get("result").getAsString();
                    assert unclesArray.size() == Integer.parseInt(reUncleCountByNumber.substring(2), 16);
                    System.out.println("response uncle count is " + reUncleCountByNumber + "\n");

                    if (LOOP_UNCLES) {
                        for (int j = 0; j < unclesArray.size(); j++) {
                            // eth_getUncleByBlockHashAndIndex
                            System.out.println("call eth_getUncleByBlockHashAndIndex params is "
                                    + reBlockHashByNumber + " | 0x" + Integer.toHexString(j));
                            JsonObject joUncleByHashAndIndex = rpc.execRpcMethod(rpc.getUncleByBlockHashAndIndex
                                    (reBlockHashByNumber, "0x" + Integer.toHexString(j)));
                            assert "13" .equals(joUncleByHashAndIndex.get("id").getAsString());
                            String reUncleHashByHash = joUncleByHashAndIndex.get("result").getAsJsonObject()
                                    .get("hash").getAsString();
                            assert unclesArray.get(j).getAsString().equals(reUncleHashByHash);
                            System.out.println("response uncle hash is " + reUncleHashByHash + "\n");

                            // eth_getUncleByBlockNumberAndIndex
                            System.out.println("call eth_getUncleByBlockNumberAndIndex params is "
                                    + reBlockNumberByNumber + " | 0x" + Integer.toHexString(j));
                            JsonObject joUncleByNumberAndIndex = rpc.execRpcMethod(rpc.getUncleByBlockNumberAndIndex
                                    (reBlockNumberByNumber, "0x" + Integer.toHexString(j)));
                            assert "14" .equals(joUncleByNumberAndIndex.get("id").getAsString());
                            String reUncleHashByNumber = joUncleByHashAndIndex.get("result").getAsJsonObject()
                                    .get("hash").getAsString();
                            assert unclesArray.get(j).getAsString().equals(reUncleHashByNumber);
                            System.out.println("response uncle hash is " + reUncleHashByNumber + "\n");
                        }
                    } else {
                        // eth_getUncleByBlockHashAndIndex
                        System.out.println("call eth_getUncleByBlockHashAndIndex params is "
                                + reBlockHashByNumber + " | 0x" + Integer.toHexString(0));
                        JsonObject joUncleByHashAndIndex = rpc.execRpcMethod(rpc.getUncleByBlockHashAndIndex
                                (reBlockHashByNumber, "0x" + Integer.toHexString(0)));
                        assert "13" .equals(joUncleByHashAndIndex.get("id").getAsString());
                        String reUncleHashByHash = joUncleByHashAndIndex.get("result").getAsJsonObject()
                                .get("hash").getAsString();
                        assert unclesArray.get(0).getAsString().equals(reUncleHashByHash);
                        System.out.println("response uncle hash is " + reUncleHashByHash + "\n");

                        // eth_getUncleByBlockNumberAndIndex
                        System.out.println("call eth_getUncleByBlockNumberAndIndex params is "
                                + reBlockNumberByNumber + " | 0x" + Integer.toHexString(0));
                        JsonObject joUncleByNumberAndIndex = rpc.execRpcMethod(rpc.getUncleByBlockNumberAndIndex
                                (reBlockNumberByNumber, "0x" + Integer.toHexString(0)));
                        assert "14" .equals(joUncleByNumberAndIndex.get("id").getAsString());
                        String reUncleHashByNumber = joUncleByHashAndIndex.get("result").getAsJsonObject()
                                .get("hash").getAsString();
                        assert unclesArray.get(0).getAsString().equals(reUncleHashByNumber);
                        System.out.println("response uncle hash is " + reUncleHashByNumber + "\n");
                    }
                }

                // eth_call
                System.out.println("call eth_call params is " + reMinerByHash + " | " + _strBlockNumber);
                JsonObject joCall = rpc.execRpcMethod(rpc.call(reMinerByHash, _strBlockNumber));
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
    public void testHex() {
        // hex to int
        String hex = "0x0";
        System.out.println(Integer.parseInt(hex.substring(2), 16));

        // int to hex
        System.out.println("0x" + Integer.toHexString(50));
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

package ai.bianjie.ddc.dto;

import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockEventBean {
    private ArrayList<BaseEventResponse> list;
    private Map<EthBlock.TransactionObject, List<BaseEventResponse>> resultMap;
    private String timeStamp;

    public BlockEventBean(ArrayList<BaseEventResponse> list, Map<EthBlock.TransactionObject, List<BaseEventResponse>> resultMap, String timeStamp) {
        this.list = list;
        this.resultMap = resultMap;
        this.timeStamp = timeStamp;
    }

    public ArrayList<BaseEventResponse> getList() {
        return list;
    }

    public void setList(ArrayList<BaseEventResponse> list) {
        this.list = list;
    }

    public Map<EthBlock.TransactionObject, List<BaseEventResponse>> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<EthBlock.TransactionObject, List<BaseEventResponse>> resultMap) {
        this.resultMap = resultMap;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

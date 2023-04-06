package ai.bianjie.ddc.constant;

/**
 *
 */
public enum CrossChainStateEnum {
    CROSS_CHAIN_PENDING("0"),
    CROSS_CHAIN_SUCCESS("1"),
    CROSS_CHAIN_FAILURE("2");

    public String getState() {
        return state;
    }

    String state;

    CrossChainStateEnum(final String state) {
        this.state = state;
    }
}

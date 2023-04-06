package ai.bianjie.ddc.constant;

public enum ErrorMessage {
    // Param check  1
    // Chain error  2
    // other 9

    UNKNOWN_ERROR(9999, "unknown error"),
    ACCOUNT_NAME_IS_EMPTY(1001, "accountName is empty"),
    ACCOUNT_IS_EMPTY(1002, "account is empty"),
    ACCOUNT_STASTUS_IS_EMPTY(1003, "account status is empty"),
    ACCOUNT_LEADER_DID_IS_EMPTY(1004, "leader DID is empty"),
    ACCOUNT_IS_NOT_ADDRESS_FORMAT(1005, "account is not a standard address format"),
    AMOUNT_IS_EMPTY(1006, "amount is empty"),
    TO_ACCOUNT_IS_EMPTY(1007, "to account is empty"),
    TO_ACCOUNT_IS_NOT_ADDRESS_FORMAT(1008, "to account is not a standard address format"),
    FROM_ACCOUNT_IS_EMPTY(1009, "from account is empty"),
    FROM_ACCOUNT_IS_NOT_ADDRESS_FORMAT(1010, "from account is not a standard address format"),
    ACC_ADDR_IS_EMPTY(1011, "accAddr is empty"),
    ACC_ADDR_IS_NOT_ADDRESS_FORMAT(1012, "accAddr is not a standard address format"),
    DDC_ADDR_IS_EMPTY(1013, "ddcAddr is empty"),
    DDC_ADDR_IS_NOT_ADDRESS_FORMAT(1014, "ddcAddr is not a standard address format"),
    AMOUNT_LT_ZERO(1015, "amount is less than 0"),
    DDCID_IS_WRONG(1016, "ddcId is wrong"),
    DDCURI_IS_EMPTY(1017, "ddcURI is empty"),
    REST_TEMPLATE_CONNT_TIMEOUT_EMPTY(1018, "rest template connection time out is empty"),
    REST_TEMPLATE_READ_TIMEOUT_EMPTY(1019, "rest template read time out is empty"),
    OPB_GATEWAY_ADDRESS_EMPTY(1020, "OPB gateway address is empty"),
    DDC_721_ADDRESS_EMPTY(1021, "DDC721 contract address is empty"),
    DDC_1155_ADDRESS_EMPTY(1022, "DDC1155 contract address is empty"),
    DDC_AUTHORITY_ADDRESS_EMPTY(1022, "DDC authority logic contract address is empty"),
    DDC_CHARGE_ADDRESS_EMPTY(1024, "DDC charge logic contract address is empty"),
    FILE_NOT_EXISTS(1025, "file is not exists"),
    READ_FILE_FAILED(1026, "raed file is failed"),
    SIGN_METHOD_EMPTY(1027, "sign method is empty"),
    NO_SIGN_EVENT_LISTNER(1028, "not register sign event listener"),
    SENDER_NOT_STANDARD_ADDRESS_FORMAT(1029, "sender is not a standard address format"),
    OWNER_ACCOUNT_IS_NOT_ADDRESS_FORMAT(1030, "owner account is not a standard address format"),
    SIG_IS_EMPTY(1031, "sig is empty"),
    SIG_IS_NOT_4BYTE_HASH(1032, "sig is not 4 byte hash"),
    REQUEST_FAILED(1033, ""),
    ERR_ACCOUNTS_SIZE(1034, "accounts size error"),
    DID_IS_EMPTY(1035, "did is empty"),
    ERR_AMOUNTS_SIZE(1036, "amounts size error"),
    ERR_NAME_DID_ADDRESS_SIZE(1037, "account names, dids, addresses, leaderdids size are not equal"),
    ERR_NAMES_SIZE(1038, "names size error"),
    ERR_DIDS_SIZE(1039, "dids size error"),
    ERR_TOLIST_ACCOUNTS_SIZE_MISMATCH(1040, "the size of to account and accounts mismatch"),
    ERR_GASLIMIT(1041, "gasLimit too low"),
    SENDER_IS_EMPTY(1042, "sender is empty"),
    DDC_TYPE_IS_EMPTY(1043, "ddcType is null"),
    TO_CHAIN_ID_IS_EMPTY(1044, "toChainID is empty"),
    REMARK_IS_EMPTY(1045, "remark is empty"),
    STATE_IS_NULL(1046, "state is null"),
    CROSS_CHAIN_ID_IS_EMPTY(1047, "crossChainId is empty"),
    ;

    private Integer code;

    private String message;

    private ErrorMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public static String getMessage(Integer code) {
        for (ErrorMessage error : ErrorMessage.values()) {
            if (error.code.equals(code)) {
                return error.message;
            }
        }
        return null;
    }

    public static String getMessage(ErrorMessage errorMessage) {
        for (ErrorMessage error : ErrorMessage.values()) {
            if (error.code.equals(errorMessage.code)) {
                return error.message;
            }
        }
        return null;
    }

}

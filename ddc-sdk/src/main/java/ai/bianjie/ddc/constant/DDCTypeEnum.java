package ai.bianjie.ddc.constant;

/**
 * DDC Type Enumeration Value
 * ERC721=0; ERC1155=1;
 */
public enum DDCTypeEnum {
    ERC721("0"),
    ERC1155("1");

    public String getType() {
        return type;
    }

    String type;

    DDCTypeEnum(final String type) {
        this.type = type;
    }
}

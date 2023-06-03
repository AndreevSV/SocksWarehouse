package pro.sky.sockswarehouse.model;

import javax.validation.constraints.NotNull;

@NotNull(message = "Для размера не может быть значенеи Null")
public enum Size {
    s36(36), s365(36.5), s37(37), s375(37.5), s38(38), s385(38.5), s39(39), s395(39.5), s40(40), s405(40.5), s41(41),
    s415(41.5), s42(42), s425(42.5), s43(43), s435(43.5), s44(44), s445(44.5), s45(45), s455(45.5), s46(46);

    private final double size;

    Size(double size) {
        this.size = size;
    }

    public double getSize() {
        return size;
    }
}

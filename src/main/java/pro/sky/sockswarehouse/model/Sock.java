package pro.sky.sockswarehouse.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

public class Sock implements Comparable<Integer>{

    @NotNull
    private Color color;
    @NotNull
    private Size size;
    @Positive
    private int cottonPart;

    @NotNull
    public Sock(Color color, Size size, int cottonPart) {
        setColor(color);
        setSize(size);
        setCottonPart(cottonPart);
    }

    @NotNull
    public Sock() {

    }

    public Color getColor() {
        return color;
    }

    public Size getSize() {
        return size;
    }

    public int getCottonPart() {
        return cottonPart;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setCottonPart(int cottonPart) {
        this.cottonPart = cottonPart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sock sock = (Sock) o;
        return cottonPart == sock.cottonPart && color == sock.color && size == sock.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size, cottonPart);
    }

    @Override
    public String toString() {
        return "Носок: цвет %s, размер %f, состав %d хлопка".formatted(color, size.getSize(), cottonPart);
    }

    @Override
    public int compareTo(Integer o) {
        if (this.cottonPart > o) {
            return 1;
        } else if (this.cottonPart < o) {
            return -1;
        } else return 0;
    }
}

package pro.sky.sockswarehouse.model;


import javax.validation.constraints.NotNull;

@NotNull(message = "Для цвета не может быть значенеи Null")
public enum Color {
    red,
    green,
    blue,
    white,
    black
}

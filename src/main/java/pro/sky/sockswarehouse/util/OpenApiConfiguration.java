package pro.sky.sockswarehouse.util;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Складская программа учета носков",
                description = "Позволяет регистрировать приход и отпуск носков, списание, " +
                        "распечатку остатков, распечатку отпущенных и утилизированных носков",
                version = "1.0.0",
                contact = @Contact(
                        name = "Сергей Андреев",
                        email = "andreevsv@gmail.com"
                )
        )
)
public class OpenApiConfiguration {
}

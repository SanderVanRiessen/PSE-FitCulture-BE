package app.dtos.user.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegister {
    private String email;
    private String name;
    private String password;
}

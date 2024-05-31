package app.dtos.user.response;

import app.models.User;
import org.springframework.stereotype.Component;

@Component
public class MapperUser {

    public GetUser toPlainUser(User user) {
        GetUser getUser = new GetUser();
        getUser.setId(user.getId());
        getUser.setName(user.getName());
        getUser.setEmail(user.getEmail());
        return getUser;
    }
}

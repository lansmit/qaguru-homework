package models.users;

import lombok.Data;

@Data
public class UserCreationResponseModel {
    String name, job, id, createdAt;
}

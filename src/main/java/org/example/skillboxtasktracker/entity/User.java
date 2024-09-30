package org.example.skillboxtasktracker.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document(collection = "users")
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    @Field("roles")
    private Set<RoleType> roles = new HashSet<>();
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

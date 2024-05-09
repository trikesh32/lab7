package common.user;

import common.utils.Validatable;

import java.io.Serializable;

public class User implements Validatable, Serializable, Comparable<User> {
    private final int id;
    private final String name;
    private final String password;
    public User(int id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
    public boolean check_validity(){
        return getName() != null && getName().length() < 40 && !getName().isEmpty();
    }
    public User copy(int id){
        return new User(id, getName(), getPassword());
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + "********" +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return this.getId() - o.getId();
    }
}

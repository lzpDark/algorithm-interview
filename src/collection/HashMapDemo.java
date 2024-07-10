package collection;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author : lzp
 */
public class HashMapDemo {

    static class User {
        String id;
        String name;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id) && Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

    static class UserRef {
        String id;
        String name;

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    public static void main(String[] args) {

        HashMap<UserRef, UserRef> refMap = new HashMap<>();
        UserRef userRef = new UserRef();
        refMap.put(userRef, userRef);
        userRef.setId("1");
        System.out.println(refMap.get(userRef).getId()); // 1

        // null pointer exception
        HashMap<User, User> map = new HashMap<>();
        User user = new User();
        map.put(user, user);
        user.setId("1");
        System.out.println(map.get(user).getId());
    }
}

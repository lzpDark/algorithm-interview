package collection;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : lzp
 */
public class StreamDemo {
    static class User {
        String id;
        String name;
        User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }
    }
    public static void main(String[] args) {
        Map<String, String> map = Stream.of(
                new User("1", "je"),
                new User("1", "je1"),
                new User("2", "hp"),
                new User("3", "al"))
                .distinct()
                .collect(Collectors.toMap(
                        user -> user.id,
                        user -> user.name));
        map.forEach((k,v)-> System.out.println(k + " " + v));
    }
}

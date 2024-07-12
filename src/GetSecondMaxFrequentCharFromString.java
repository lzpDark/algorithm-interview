import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : lzp
 */
public class GetSecondMaxFrequentCharFromString {
    public List<Character> getSecondMaxFrequentChar(String str) {
        if(str == null || str.length() == 0) {
            return null;
        }

        // count char
        Map<Character, Integer> countMap = new HashMap<>();
        for(int i = 0; i < str.length(); i++) {
            if (!countMap.containsKey(str.charAt(i))) {
                countMap.put(str.charAt(i), 1);
            } else {
                int count = countMap.get(str.charAt(i));
                countMap.put(str.charAt(i), count + 1);
            }
        }

        // get 2nd max count
        List<Integer> maxCountList = countMap.values().stream()
                .sorted(Comparator.reverseOrder())
                .distinct()
                .limit(2)
                .collect(Collectors.toList());
        if (maxCountList.size() < 2) {
            return null;
        }

        // get chars whose count equals secondMaxCount
        List<Character> result = new ArrayList<>();
        int secondMaxCount  = maxCountList.get(1);
        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if(entry.getValue() == secondMaxCount) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Character> res = new GetSecondMaxFrequentCharFromString()
                .getSecondMaxFrequentChar("zaaabbbccdde");
        System.out.println(res);
    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * @author : lzp
 */
public class TreeLevelPrint {
    /**
     * @param tree: a complete tree from top down, [3,9,20,null,null,15,7]
     * @return group nodes by level. [[3], [9,20], [15,7]]
     */
    public List<List<Integer>> levelTraverseTree(List<Integer> tree) {
        if(tree == null || tree.size() == 0) {
            return null;
        }
        int idx = 0;
        int level = 0;
        List<List<Integer>> result = new ArrayList<>();
        while(idx < tree.size()) {
            // get current level nodes count
            int levelCount = (int) Math.pow(2, level);

            // construct current level
            int left = idx, right = left + levelCount - 1;
            List<Integer> currentLevelNodeList = new ArrayList<>();
            for(int i = left; i <= right && i < tree.size(); i++) {
                if(tree.get(i) != null) {
                    currentLevelNodeList.add(tree.get(i));
                }
            }
            result.add(currentLevelNodeList);

            // prepare for next level
            idx = right + 1;
            level++;
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> input= new ArrayList<>();
        input.add(3);
        input.add(9);
        input.add(20);
        input.add(null);
        input.add(null);
        input.add(15);
        input.add(7);
        List<List<Integer>> res = new TreeLevelPrint()
                .levelTraverseTree(input);
        System.out.println(res);
    }
}

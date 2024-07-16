package leetcode;

/**
 * @author : lzp
 */
class Solution {

    private int find(int[] p, int i) {
        if(p[i] != i) {
            p[i] = find(p, p[i]);
        }
        return p[i];
    }

    private void merge(int[] p, int i, int j) {
        if(find(p, i) != find(p, j)) {
            p[find(p, i)] = find(p, j);
        }
    }

    public boolean validPath(int n, int[][] edges, int source, int destination) {
        int[] p = new int[n];
        for(int i = 0; i < n; i++) {
            p[i] = i;
        }

        for(int i = 0; i < edges.length; i++) {
            // merge 2 nodes
            merge(p, edges[i][0], edges[i][1]);
        }

        //
        return find(p, source) == find(p, destination);

    }

    public static void main(String[] args) {
        new Solution()
                .validPath(10,
                        new int[][]{
                                // [[0,7],[0,8],[6,1],[2,0],[0,4],[5,8],[4,7],[1,3],[3,5],[6,5]]
                                {0, 7},
                                {0, 8},
                                {6, 1},
                                {2, 0},
                                {0, 4},
                                {5, 8},
                                {4, 7},
                                {1, 3},
                                {3, 5},
                                {6, 5},
                        },
                        7, 5);
    }
}
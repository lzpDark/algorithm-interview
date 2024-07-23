/**
 * @author : lzp
 */
public class CompareSplitResult {
    /**
     * 给你一个句子的两种不同分词方式，让你求出两个分词结果中，一致的词语数量(词和位置都一致)，比方说：
     * gt_text = "巴基斯坦  政权 地方 选举 的 投票 工作 十二月 三十一日 在 巴 全国 的 四 个 省 同时 开始"
     * predict_text = "巴基 斯坦 政权 地方  选举 的 投票 工作 十二 月三 十一日 在 巴 全国 的 四 个 省 同时 开始"
     * 应该返回13 (选举 的 投票 工作 在 巴 全国 的 四 个 省 同时 开始)
     */
    public static int compareSplit(String a, String b) {
        String[] splitA = a.split(" ");
        String[] splitB = b.split(" ");
        if (splitA.length == 0 || splitB.length == 0) {
            return 0;
        }
        int idxA = 0, idxB = 0;
        int lenA = splitA.length, lenB = splitB.length;
        int countA = 0, countB = 0;
        int matchCount = 0;
        while(idxA < lenA && idxB < lenB) {

            if(countA == countB) {
                countA += splitA[idxA].length();
                countB += splitB[idxB].length();
//                System.out.println("A - B:" + splitA[idxA]);
                if(splitA[idxA].equals(splitB[idxB])) {
                    matchCount++;
                }
                idxA++;
                idxB++;
            } else if(countA < countB) {
                countA += splitA[idxA].length();
//                System.out.println("A:" + splitA[idxA]);
                idxA++;
            } else {
                countB += splitB[idxB].length();
//                System.out.println("B:" + splitB[idxB]);
                idxB++;
            }
        }

        return matchCount;
    }

    public static void main(String[] args) {
        String gt_text = "巴基斯坦  政权 地方 选举 的 投票 工作 十二月 三十一日 在 巴 全国 的 四 个 省 同时 开始";
        String predict_text = "巴基 斯坦 政权 地方  选举 的 投票 工作 十二 月三 十一日 在 巴 全国 的 四 个 省 同时 开始";
        System.out.println(compareSplit(gt_text, predict_text));
        // 13
    }
}

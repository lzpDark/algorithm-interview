/**
 * @author : lzp
 */
public class CompareSplitResult {
    /**
     * ����һ�����ӵ����ֲ�ͬ�ִʷ�ʽ��������������ִʽ���У�һ�µĴ�������(�ʺ�λ�ö�һ��)���ȷ�˵��
     * gt_text = "�ͻ�˹̹  ��Ȩ �ط� ѡ�� �� ͶƱ ���� ʮ���� ��ʮһ�� �� �� ȫ�� �� �� �� ʡ ͬʱ ��ʼ"
     * predict_text = "�ͻ� ˹̹ ��Ȩ �ط�  ѡ�� �� ͶƱ ���� ʮ�� ���� ʮһ�� �� �� ȫ�� �� �� �� ʡ ͬʱ ��ʼ"
     * Ӧ�÷���13 (ѡ�� �� ͶƱ ���� �� �� ȫ�� �� �� �� ʡ ͬʱ ��ʼ)
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
        String gt_text = "�ͻ�˹̹  ��Ȩ �ط� ѡ�� �� ͶƱ ���� ʮ���� ��ʮһ�� �� �� ȫ�� �� �� �� ʡ ͬʱ ��ʼ";
        String predict_text = "�ͻ� ˹̹ ��Ȩ �ط�  ѡ�� �� ͶƱ ���� ʮ�� ���� ʮһ�� �� �� ȫ�� �� �� �� ʡ ͬʱ ��ʼ";
        System.out.println(compareSplit(gt_text, predict_text));
        // 13
    }
}

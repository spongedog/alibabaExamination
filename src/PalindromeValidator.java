import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * 输入长度大于1小于5000的字符串，
 * @author 郑凯努 date 2019/7/5
 */
public class PalindromeValidator {

    public PalindromeWrapper createPalindromeWrapper(String str) {
        return new PalindromeWrapper(str);
    }


    public static void main(String[] args) {
        //正则判断标点
        Pattern pattern = Pattern.compile("\\p{P}");
        var scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        if (str == null || str.length() <= 1 || str.length() > 5000) {
            throw new RuntimeException("wrong str");
        }
        char[] chars = str.toCharArray();

        var palindromeWrapper = new PalindromeValidator().createPalindromeWrapper(str);
        int length = chars.length;
        for (int i = 0; i < length - 1; i ++) {
            for (int j = i; j <length; j ++) {
                int left = i, right = j, redundantSize = 0;
                while (left < right) {
                    var leftStr = String.valueOf(chars[left]);
                    if (pattern.matcher(leftStr).matches() || " ".equals(leftStr)) {
                        left ++;
                        redundantSize ++;
                        continue;
                    }

                    var rightStr = String.valueOf(chars[right]);
                    if (pattern.matcher(rightStr).matches() || " ".equals(rightStr)) {
                        right --;
                        redundantSize ++;
                        continue;
                    }

                    if (chars[left] != chars[right]) {
                        break;
                    }
                    left++;
                    right--;
                }

                //是回文字符串且长度大于当前回文，则更新wrapper
                if (left >= right && j - i - redundantSize > palindromeWrapper.getLength()) {
                    palindromeWrapper.setStart(i);
                    palindromeWrapper.setEnd(j);
                    palindromeWrapper.setRedundantSize(redundantSize);
                }
            }
        }

        System.out.println(palindromeWrapper.getPalindrome());
    }

    public class PalindromeWrapper {

        /**
         * 输入的字符串
         */
        private String str;

        /**
         * 当前最大回文在输入字符串中的开始下标
         */
        private int start;

        /**
         * 当前最大回文在输入字符串中的结束下标
         */
        private int end;

        /**
         * 多余字符的长度
         */
        private int redundantSize;

        public void setStart(int start) {
            this.start = start;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getRedundantSize() {
            return redundantSize;
        }

        public void setRedundantSize(int redundantSize) {
            this.redundantSize = redundantSize;
        }

        public PalindromeWrapper(String str) {
            this.str = str;
        }

        public int getLength() {
            return end - start - redundantSize;
        }

        /**
         * 返回最大回文，若不存在则返回空字符串
         * @return 回文字符串
         */
        private String getPalindrome() {
            return str.substring(start, end+1);
        }
    }
}

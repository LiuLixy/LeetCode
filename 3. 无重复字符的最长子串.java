给定一个字符串，找出不含有重复字符的最长子串的长度。

示例1：
输入: "abcabcbb"
输出: 3 
解释: 无重复字符的最长子串是 "abc"，其长度为 3。

示例2：
输入: "bbbbb"
输出: 1
解释: 无重复字符的最长子串是 "b"，其长度为 1。

示例2：
输入: "pwwkew"
输出: 3
解释: 无重复字符的最长子串是 "wke"，其长度为 3。
     请注意，答案必须是一个子串，"pwke" 是一个子序列，而不是子串。


方法一：暴力求解
逐个检查所有的子字符串，看它是否不含有重复的字符。

假设我们有一个函数 boolean allUnique(String substring)，如果子字符串中的字符都是唯一的，
它会返回true，否则会返回false。我们可以遍历给定字符串s的所有可能的字符串并调用函数 allUnique。
如果事实证明返回值为true，那么我们将会更新无重复字符串子串的最大长度的答案。

1. 为了枚举给定字符串的所有子串，我们需要枚举它们开始和结束的索引。假设开始和结束的索引分别为
i 和 j。那么我们有 0 ≤ i < j ≤ n（这里的结束索引 j 是按照惯例排除的）。因此，使用i从0到 n - 1 
以及j从 i+1 到n这两个嵌套循环，我们可以枚举出 s 的所有子字符串。
2. 要检查一个字符串是否有重复的字符，我们可以使用集合。我们遍历字符串中的所有字符，并将它们逐个
放入set中。在放置一个字符之前，我们检查该集合是否已经包含它。如果包含，我们会返回false。循环结束后，
我们返回true。

代码如下：
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (allUnique(s, i, j)) {
                	ans = Math.max(ans, j - i);
        	    }
        	}
        }
        return ans;
    }

    public boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            Character ch = s.charAt(i);
            if (set.contains(ch)) {
            	return false;
            }
            set.add(ch);
        }
        return true;
    }
}

复杂度分析：
时间复杂度：O(n^3)。
空间复杂度：O(min(n, m))，我们需要 O(k) 的空间来检查子字符串中是否有重复字符，其中 k 表示 Set 的大小。
而 Set 的大小取决于字符串 n 的大小以及字符集/字母 m 的大小。 


方法二：滑动窗口
通过使用 HashSet 作为滑动窗口，我们可以用 O(1) 的时间来完成对字符是否在当前的子字符串中的检查。滑动窗口是
数组/字符串问题中常用的抽象概念。 窗口通常是在数组/字符串中由开始和结束索引定义的一系列元素的集合，
即 [i, j)（左闭，右开）。而滑动窗口是可以将两个边界向某一方向“滑动”的窗口。例如，我们将 [i, j) 向右滑动1个元素，
则它将变为 [i+1, j+1)（左闭，右开）。
我们使用 HashSet 将字符存储在当前窗口 [i, j)（最初 j = i）中。 然后我们向右侧滑动索引j，如果它不在 HashSet 中，
我们会继续滑动 j。直到 s[j] 已经存在于 HashSet 中。此时，我们找到的没有重复字符的最长子字符串将会以索引 i 开头。
如果我们对所有的 i 这样做，就可以得到答案。

代码如下：
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            } else {
            	set.remove(s.charAt(i++));
            }
        }
        return ans;
    }
}

复杂度分析：
时间复杂度：O(2n) = O(n)，在最糟糕的情况下，每个字符将被 i 和 j 访问两次。
空间复杂度：O(min(m, n))，与之前的方法相同。滑动窗口法需要O(k)的空间，
其中k表示Set的大小。而Set的大小取决于字符串n的大小以及字符集/字母m的大小。 


方法三：优化的滑动窗口
上述的方法最多需要执行 2n 个步骤。事实上，它可以被进一步优化为仅需要 n 个步骤。我们可以定义字符
到索引的映射，而不是使用集合来判断一个字符是否存在。 当我们找到重复的字符时，我们可以立即跳过该窗口。

也就是说，如果 s[j] 在 [i, j) 范围内有与 j′ 重复的字符，我们不需要逐渐增加 i 。 我们可以直接跳过 [i，j′]
范围内的所有元素，并将 i 变为 j′+1。

代码如下：（使用HashMap）
public class Solution {
    public int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        Map<Character, Integer> map = new HashMap<>(); 
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }
}

复杂度分析：
时间复杂度：O(n)，索引j将会迭代n次。
空间复杂度：O(min(m, n))，与之前的方法相同。
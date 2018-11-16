2.两数相加

给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表。
你可以假设除了数字 0 之外，这两个数字都不会以零开头。

示例：
输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
输出：7 -> 0 -> 8
原因：342 + 465 = 807

伪代码如下：
1.将当前结点初始化为返回列表的哑结点。
2.将进位 flag 初始化为 0。
3.将 p 和 q 分别初始化为列表 l1 和 l2 的头部。
4.遍历列表 l1 和 l2 直至到达它们的尾端。
	4.1 将 x 设为结点 p 的值。如果 p 已经到达 l1 的末尾，则将其值设置为 0。
	4.2 将 y 设为结点 q 的值。如果 q 已经到达 l2 的末尾，则将其值设置为 0。
	4.3 设定 sum = x + y + flag。
	4.4 更新进位的值，flag = sum / 10。
	4.5 创建一个数值为(sum mod 10)的新结点，并将其设置为当前结点的下一个结点，然后将当前结点前进到下一个结点。
	4.6 同时，将 p 和 q 前进到下一个结点。
5.检查 flag = 1 是否成立，如果成立，则向返回列表追加一个含有数字 1 的新结点。
6.返回哑结点的下一个结点。

请注意，我们使用哑结点来简化代码。如果没有哑结点，则必须编写额外的条件语句来初始化表头的值。

代码如下：
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x; 
 *     }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode cur1 = l1, cur2 = l2, cur = head;
        // 表示是否需要进位(只有连个值:0跟1)
        int flag = 0;
        while(cur1 != null || cur2 != null) {
            // 需要考虑位数不同的数字相加
            int x = (cur1 != null ) ? cur1.val : 0;
            int y = (cur2 != null ) ? cur2.val : 0;
            int sum = flag + x + y;
            // 进位携带
            flag = sum / 10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            if(cur1 != null ) {
                cur1 = cur1.next;
            }
            if(cur2 != null ) {
                cur2 = cur2.next;
            }
        }
        // 加到最后一位
        if(flag > 0) {
            cur.next = new ListNode(flag);
        }
        return head.next;
    }
}

复杂度分析：
时间复杂度：O(max(m, n))，假设 m 和 n 分别表示 l1 和 l2 的长度，上面的算法最多重复max(m, n)次。
空间复杂度：O(max(m, n))，新列表的长度最多为 max(m, n) + 1。
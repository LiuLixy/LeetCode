1. 两数之和

给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。

示例：
给定 nums = [2, 7, 11, 15], target = 9
因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]

方法一：暴力求解
遍历每个元素 x，并查找是否存在一个值与 target − x 相等的目标元素。

代码如下：
class Solution {
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length <= 1) {
            return null;
        }
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (nums[i] + nums[j] == target && i != j) {
                    if (i < j) {
                        result[0] = i;
                        result[1] = j;
                        break;
                    } else {
                        result[0] = j;
                        result[1] = i;
                        break;
                    }
                }
            }
        }
        return result;
    }
}

复杂度分析：
时间复杂度：O(n^2)，对于每个元素，我们试图通过遍历数组的其余部分来寻找它所对应的目标元素，
这将耗费O(n) 的时间。因此时间复杂度为O(n^2)。
空间复杂度：O(1)。 


方法二：两遍哈希表
通过以空间换取速度的方式，我们可以将查找时间从O(n)降低到O(1)。哈希表正是为此目的而构建的，
它支持以近似恒定的时间进行快速查找。用“近似”来描述，是因为一旦出现冲突，查找用时可能会退化到O(n)。
但只要你仔细地挑选哈希函数，在哈希表中进行查找的用时应当被摊销为 O(1)。

一个简单的实现使用了两次迭代。在第一次迭代中，我们将每个元素的值和它的索引添加到表中。然后，在第二次迭代中，
我们将检查每个元素所对应的目标元素（target - nums[i]）是否存在于表中。注意，该目标元素不能是nums[i]本身！

代码如下：
class Solution {
    public int[] twoSum(int[] nums, int target) {
    	Map<Integer, Integer> map = new HashMap<>();
    	for (int i = 0; i < nums.length; i++) {
    		map.put(nums[i], i);
    	}
    	for (int i = 0; i < nums.length; i++) {
    		int complement = target - nums[i];
    		if (map.containsKey(complement) && map.get(complement) != i) {
    			return new int[] {i, map.get(complement)};
    		}
    	}
    	// 数组中没有满足条件的数字组合
    	return null;
    }
}

复杂度分析：
时间复杂度：O(n)， 我们把包含有n个元素的列表遍历两次。由于哈希表将查找时间缩短到O(1)，所以时间复杂度为O(n)。
空间复杂度：O(n)，所需的额外空间取决于哈希表中存储的元素数量，该表中存储了n个元素。


方法三：一遍哈希表
在进行迭代并将元素插入到表中的同时，我们还会回过头来检查表中是否已经存在当前元素所对应的目标元素。
如果它存在，那我们已经找到了对应解，并立即将其返回。

代码如下：
class Solution {
    public int[] twoSum(int[] nums, int target) {
    	Map<Integer, Integer> map = new HashMap<>();
    	for (int i = 0; i < nums.length; i++) {
    		int complement = target - nums[i];
    		if (map.containsKey(complement) && map.get(complement) != i) {
    			return new int[] {map.get(complement), i};
    		}
    		map.put(nums[i], i);
    	}
    	// 数组中没有满足条件的数字组合
    	return null;
    }
}

复杂度分析：
时间复杂度：O(n)， 我们只遍历了包含有n个元素的列表一次。在表中进行的每次查找只花费O(1)的时间。
空间复杂度：O(n)， 所需的额外空间取决于哈希表中存储的元素数量，该表最多需要存储n个元素。
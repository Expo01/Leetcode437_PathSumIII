import javax.swing.tree.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/*
You might want to use the prefix sum technique for the problems
like "Find a number of continuous subarrays/submatrices/tree paths
that sum to target".

is a harder version of 560, so get comfy witht that first *******


Now the current subtree is processed.
It's time to remove the current prefix sum from the hashmap,
in order not to blend the parallel subtrees: h[curr_sum] -= 1????????????
 */

class Solution {
    int count = 0;
    int k;
    HashMap<Long, Integer> h = new HashMap();

    public int pathSum(TreeNode root, int sum) {
        k = sum;
        preorder(root, 0L);
        return count;
    }

    public void preorder(TreeNode node, long currSum) {
        if (node == null) {return;}

        currSum += node.val;  // current prefix sum

        if (currSum == k) {count++;}  // sum found

        count += h.getOrDefault(currSum - k, 0); // adds to count # of times val currSum-k has appeared

        h.put(currSum, h.getOrDefault(currSum, 0) + 1);  // add currSum to hashmap to use in child nodes processing

        preorder(node.left, currSum);  // process left subtree
        preorder(node.right, currSum);         // process right subtree

        // removes current sum from the hashmap in order not to use it during the parallel subtree processing
        h.put(currSum, h.get(currSum) - 1);
    }

}


//class Solution {
//    int numOfWays = 0;
//    List<Integer> pathNodes = new ArrayList<Integer>();
//
//    public int pathSum(TreeNode root, int targetSum) {
//        if(root == null){ return 0;}
//        ArrayDeque<TreeNode> q = new ArrayDeque<>();
//        q.add(root);
//        ArrayList<TreeNode> allNodes = new ArrayList<>();
//        while(!q.isEmpty()){
//            TreeNode node = q.pop();
//            allNodes.add(node);
//            if(node.left != null){
//                q.add(node.left);
//            }
//            if(node.right != null){
//                q.add(node.right);
//            }
//        }
//
//        for(TreeNode n: allNodes){
//            recurseTree(n, (long) targetSum, pathNodes);
//        }
//
//        return numOfWays;
//    }
//
//    private void recurseTree(TreeNode node, long remainingSum, List<Integer> pathNodes) {
//
//        if (node == null) {return;}
//
//        pathNodes.add(node.val);
//
//        if (remainingSum == node.val) {numOfWays++;}
//
//
//        this.recurseTree(node.left, remainingSum, pathNodes);
//        this.recurseTree(node.right, remainingSum, pathNodes);
//
//        pathNodes.remove(pathNodes.size() - 1);
//    }
//}

// this will succesfully backtrack and add all occurences where sum-val == 0 starrting from root node
// now need to call for all nodes of tree
// perhps start by creating BFS que and then callig helper recursion on eeach new dequed "root"
// to avoid double recursion such as testing "roots" 5, then 4,11,7,2, etc.

//        if(remainingSum - node.val < Integer.MIN_VALUE){
//        remainingSum = Integer.MIN_VALUE;
//        } else {
//        remainingSum -= node.val;
//        }
// this does not handle the overflow edge case. gunna try using a long or something




// brute force could comple all descending paths from each node, and add sum colleections along the way
// so as to include any nested solutions such that  5,5 = 10 and 5,5,4,-4 = 10
// would involve backtracking in DFS say try 10,5,3,1, backtrack to 3 and try .right of -2 instead of
// .left of second 3. but this would capture only paths that start from the given root
// would then have to perform same DFS and backtrack with .right and .left of originnal node and
// so on. horribly redundant. recall doinng memoization with coin change, but no idea here...


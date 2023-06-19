package com.cakemonster.algorithm.dynamicprogram.backpack;

/**
 * 0-1背包问题
 * 对于一组不同重量的物品，选择其中一些物品装入背包，在不超过背包最大重量限制的前提下
 * 1. 背包可装物品总重量的最大值是多少 (最值)
 * 2. 是否能装满整个背包  （可行）
 * 3. 正好装满背包最少需要多少物品 （最值）
 * 4. 装满背包有多少种装法 （计数）
 * <p>
 * 物品重量：{2,2,4,6,3}
 * 背包最大承重：9
 *
 * @author cakemonster
 * @date 2023/6/18
 */
public class ZeroOneBackpack {

    /**
     * 1. 背包可装物品总重量的最大值是多少
     *
     * @param weight 物品重量
     * @param w      背包最大承重
     * @return 可达最大承重
     */
    public int maxTotalWeightCanBeLoadedInBackpack(int[] weight, int w) {
        if (weight == null || w < 0) {
            return -1;
        }
        int n = weight.length;
        // 1. 定义多阶段dp
        // 为什么是w+1?  因为重量不存在0，必须是1开始
        boolean[][] dp = new boolean[n][w + 1];

        // 2. 初始化
        // 这里初始化的就是第1个物品装或者不装
        dp[0][0] = true;
        if (weight[0] <= w) {
            dp[0][weight[0]] = true;
        }

        // 3. 状态转移方程
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                // dp[i][j] 只可能由两个状态转移过来一个是dp[i - 1][j]，一个是dp[i - 1][j - weight[i]]
                // 即上一个物品装或不装，这个dp[i - 1][j]状态不装第i个物品到dp[i][j]
                // 这个dp[i - 1][j - weight[i]]状态装第i个物品也到dp[i][j]这个状态
                if (dp[i - 1][j] || (j - weight[i] >= 0 && dp[i - 1][j - weight[i]])) {
                    dp[i][j] = true;
                }
            }
        }

        // 4. 返回结果
        for (int i = w; i >= 0; i--) {
            if (dp[n - 1][i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 2. 是否能装满整个背包
     *
     * @param weight 物品重量
     * @param w      背包最大承重
     * @return 是否可以装满整个背包
     */
    public boolean canFillBackpack(int[] weight, int w) {
        if (weight == null || w < 0) {
            return true;
        }
        int n = weight.length;
        // 1. 定义多阶段dp
        // 为什么是w+1?  因为重量不存在0，必须是1开始
        boolean[][] dp = new boolean[n][w + 1];
        dp[0][0] = true;
        if (weight[0] <= w) {
            dp[0][weight[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                if (dp[i - 1][j] || (j - weight[i] >= 0 && dp[i - 1][j - weight[i]])) {
                    dp[i][j] = true;
                }
            }
        }

        return dp[n - 1][w];
    }

    /**
     * 3. 正好装满背包最少需要多少物品
     *
     * @param weight 物品重量
     * @param w      背包最大承重
     * @return 装满背包最少需要多少物品
     */
    public int minItemsCanFillBackpack(int[] weight, int w) {
        if (weight == null || w < 0) {
            return -1;
        }
        int n = weight.length;
        // 1. 定义多阶段dp
        // 为什么是w+1?  因为重量不存在0，必须是1开始
        int[][] dp = new int[n][w + 1];
        for (int i = 0; i < weight.length; i++) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        dp[0][0] = 0;
        if (weight[0] <= w) {
            dp[0][weight[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                // 防止越界
                if (dp[i - 1][j] != Integer.MAX_VALUE) {
                    dp[i][j] = dp[i - 1][j];
                }
                // 防止越界
                if (j - weight[i] >= 0 && dp[i - 1][j - weight[i]] != Integer.MAX_VALUE) {
                    dp[i][j] = Math.min(dp[i - 1][j], dp[i - 1][j - weight[i]] + 1);
                }
            }
        }

        if (dp[n - 1][w] == Integer.MAX_VALUE) {
            return -1;
        }
        return dp[n - 1][w];
    }

    /**
     * 4. 装满背包有多少种装法
     *
     * @param weight 物品重量
     * @param w      背包最大承重
     * @return 可达最大承重
     */
    public int manyWaysToFillBackpack(int[] weight, int w) {
        if (weight == null || w < 0) {
            return -1;
        }
        int n = weight.length;
        // 1. 定义多阶段dp
        // 为什么是w+1?  因为重量不存在0，必须是1开始
        int[][] dp = new int[n][w + 1];
        dp[0][0] = 1;
        if (weight[0] <= w) {
            dp[0][weight[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                if (j - weight[i] < 0) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 参考路径的走法，就是两个可达的点的相加
                    dp[i][j] = dp[i - 1][j] + dp[i - 1][j - weight[i]];
                }
            }
        }

        return dp[n - 1][w];
    }

    public static void main(String[] args) {
        ZeroOneBackpack backpack = new ZeroOneBackpack();
        int[] weights = {2, 2, 4, 6, 3};
        int w = 9;
        System.out.println(backpack.maxTotalWeightCanBeLoadedInBackpack(weights, w));
        System.out.println(backpack.canFillBackpack(weights, w));
        System.out.println(backpack.minItemsCanFillBackpack(weights, w));
        System.out.println(backpack.manyWaysToFillBackpack(weights, w));
    }

}

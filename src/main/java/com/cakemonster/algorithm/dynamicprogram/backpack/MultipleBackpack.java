package com.cakemonster.algorithm.dynamicprogram.backpack;

/**
 * 多重背包问题
 * 有n个物品，重量分别是weight[i](i=0~n-1)，每个物品有count[i]个，选择一些物品装入背包，在不超过背包重量w的前提下，要求解的问题：
 * 1. 背包可装物品总重量的最大值是多少
 * 2. 是否能装满整个背包
 * 3. 正好装满背包最少需要多少物品
 * 4. 装满背包有多少种装法
 *
 * @author cakemonster
 * @date 2023/6/26
 */
public class MultipleBackpack {

    public int maxTotalWeightCanBeLoadedInBackpack(int[] weight, int[] counts, int w) {
        int n = weight.length;
        boolean[][] dp = new boolean[n][w + 1];
        for (int i = 0; i <= Math.min(w / weight[0], counts[0]); i++) {
            dp[0][i * weight[0]] = true;
        }

        for (int i = 1; i < weight.length; i++) {
            for (int j = 0; j <= w; j++) {
                int countLimit = Math.min(w / weight[i], counts[i]);
                for (int k = 0; k <= countLimit; k++) {
                    if (dp[i - 1][j - k * weight[i]]) {
                        dp[i][j] = true;
                        break;
                    }
                }
            }
        }

        for (int i = w; i >= 0; i--) {
            if (dp[n - 1][i]) {
                return i;
            }
        }

        return -1;
    }

    public boolean canFillBackpack(int[] weight, int[] counts, int w) {
        int n = weight.length;
        boolean[][] dp = new boolean[n][w + 1];
        for (int i = 0; i <= Math.min(w / weight[0], counts[0]); i++) {
            dp[0][i * weight[0]] = true;
        }

        for (int i = 1; i < weight.length; i++) {
            for (int j = 0; j <= w; j++) {
                int countLimit = Math.min(w / weight[i], counts[i]);
                for (int k = 0; k <= countLimit; k++) {
                    if (dp[i - 1][j - k * weight[i]]) {
                        dp[i][j] = true;
                        break;
                    }
                }
            }
        }

        return dp[n - 1][w];
    }

    public int minItemsCanFillBackpack(int[] weight, int[] counts, int w) {
        int n = weight.length;
        int[][] dp = new int[n][w + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < w; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i <= Math.min(w / weight[0], counts[0]); i++) {
            dp[0][i * weight[0]] = i;
        }

        for (int i = 1; i < weight.length; i++) {
            for (int j = 0; j <= w; j++) {
                int countLimit = Math.min(w / weight[i], counts[i]);
                for (int k = 0; k <= countLimit; k++) {
                    if (j - k * weight[i] != Integer.MAX_VALUE) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - k * weight[i]] + k);
                    }
                }
            }
        }

        return dp[n - 1][w];
    }

    public int manyWaysToFillBackpack(int[] weight, int[] counts, int w) {
        int n = weight.length;
        int[][] dp = new int[n][w + 1];

        for (int i = 0; i <= Math.min(w / weight[0], counts[0]); i++) {
            dp[0][i * weight[0]] = 1;
        }

        for (int i = 1; i < weight.length; i++) {
            for (int j = 0; j <= w; j++) {
                int countLimit = Math.min(w / weight[i], counts[i]);
                for (int k = 0; k <= countLimit; k++) {
                    dp[i][j] += dp[i - 1][j - k * weight[i]];
                }
            }
        }

        return dp[n - 1][w];
    }
}

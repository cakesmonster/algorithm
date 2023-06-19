package com.cakemonster.algorithm.dynamicprogram.backpack;

/**
 * 完全背包问题
 * 有n个物品，重量分别是weight[i](i=0~n-1)，每个物品有无限多个，选择一些物品装入背包，在不超过背包重量w的前提下，要求解的问题：
 * 1. 背包可装物品总重量的最大值是多少 (最值)
 * 2. 是否能装满整个背包  （可行）
 * 3. 正好装满背包最少需要多少物品 （最值）
 * 4. 装满背包有多少种装法 （计数）
 * <p>
 * 物品重量：{2,2,4,6,3}
 * 背包最大承重：9
 *
 * @author cakemonster
 * @date 2023/6/19
 */
public class CompleteBackpack implements Backpack {

    @Override
    public int maxTotalWeightCanBeLoadedInBackpack(int[] weight, int w) {
        int n = weight.length;
        boolean[][] dp = new boolean[n][w + 1];
        for (int k = 0; k <= w / weight[0]; k++) {
            dp[0][k * weight[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                int countLimit = w / weight[i];
                for (int k = 0; k <= countLimit; k++) {
                    // 状态转移方程：dp[i][j] = dp[i - 1][j] || dp[i - 1][j - weight[i]]
                    // || dp[i - 1][j - 2 * weight[i]] ... || dp[i - 1][j - n * weight[i]]
                    // j - k * weights[[i]] >= 0 这个不需要判断，因为 k是 j / weight[i] 求出来的，所以不管怎么乘肯定小于j
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

    @Override
    public boolean canFillBackpack(int[] weight, int w) {
        int n = weight.length;
        boolean[][] dp = new boolean[n][w + 1];
        for (int k = 0; k <= w / weight[0]; k++) {
            dp[0][k * weight[0]] = true;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                int countLimit = w / weight[i];
                for (int k = 0; k <= countLimit; k++) {
                    // dp[i][j] = dp[i - 1][j] || dp[i - 1][j - weight[i]]
                    // || dp[i - 1][j - 2 * weight[i]] ... || dp[i - 1][j - n * weight[i]]
                    if (dp[i - 1][j - k * weight[i]]) {
                        dp[i][j] = true;
                        break;
                    }
                }
            }
        }

        return dp[n - 1][w];
    }

    @Override
    public int minItemsCanFillBackpack(int[] weight, int w) {
        int n = weight.length;
        int[][] dp = new int[n][w + 1];
        for (int i = 0; i < weight.length; i++) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int k = 0; k <= w / weight[0]; k++) {
            dp[0][k * weight[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                int countLimit = w / weight[i];
                for (int k = 0; k <= countLimit; k++) {
                    // min(dp[i][j], dp[i - 1][j - k * weight[i]] + k)
                    if (j - k * weight[i] > 0 && dp[i - 1][j - k * weight[i]] != Integer.MAX_VALUE) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - k * weight[i]] + k);
                    }
                }
            }
        }

        return dp[n - 1][w];
    }

    @Override
    public int manyWaysToFillBackpack(int[] weight, int w) {
        int n = weight.length;
        int[][] dp = new int[n][w + 1];
        for (int k = 0; k <= w / weight[0]; k++) {
            dp[0][k * weight[0]] = 1;
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                int countLimit = w / weight[i];
                for (int k = 0; k <= countLimit; k++) {
                    // dp[i][j] = dp[i - 1][j] + ... dp[i - 1][j - k * weight[i]]
                    dp[i][j] += dp[i - 1][j - k * weight[i]] + k;
                }
            }
        }
        return dp[n - 1][w];
    }
}

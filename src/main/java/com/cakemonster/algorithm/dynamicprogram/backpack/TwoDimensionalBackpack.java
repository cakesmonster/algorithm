package com.cakemonster.algorithm.dynamicprogram.backpack;

/**
 * 二维费用背包问题
 * 对于一组不同重量，不同价值，不可分割的物品，选择将其中某些物品装入背包，在不超过背包最大重量限制的前提下
 * 背包中可装入物品的总价值最大是多少？注意这里求总最大价值，而非最大重量
 * <p>
 * 物品重量：{2, 2, 4, 6, 3}
 * 物品价值：{3, 4, 8, 9, 6}
 * 背包最大重量：9
 *
 * @author cakemonster
 * @date 2023/6/26
 */
public class TwoDimensionalBackpack {

    /**
     * 背包可装入物品的总价值最大是多少
     *
     * @param weight 物品重量
     * @param values 物品价值
     * @param w      背包最大承重
     * @return 背包可装入物品的总价值最大是多少
     */
    public int canFillBackpackMaxValue(int[] weight, int[] values, int w) {
        int n = weight.length;
        int[][] dp = new int[n][w + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                dp[i][j] = Integer.MIN_VALUE;
            }
        }

        dp[0][0] = 0;
        if (weight[0] <= w) {
            dp[0][weight[0]] = values[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= w; j++) {
                if (dp[i - 1][j] != Integer.MIN_VALUE) {
                    dp[i][j] = dp[i - 1][j];
                }
                if (j - weight[i] > 0 && dp[i - 1][j - weight[i]] != Integer.MIN_VALUE) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - weight[i]] + values[i]);
                }
            }
        }

        int res = Integer.MIN_VALUE;
        for (int i = 0; i <= w; i++) {
            res = Math.max(dp[n - 1][i], res);
        }

        return res;
    }

}

package com.cakemonster.algorithm.dynamicprogram.backpack;

/**
 * Backpack
 *
 * @author cakemonster
 * @date 2023/6/19
 */
public interface Backpack {

    /**
     * 1. 背包可装物品总重量的最大值是多少
     *
     * @param weight 物品重量
     * @param w      背包最大承重
     * @return 可达最大承重
     */
    int maxTotalWeightCanBeLoadedInBackpack(int[] weight, int w);

    /**
     * 2. 是否能装满整个背包
     *
     * @param weight 物品重量
     * @param w      背包最大承重
     * @return 是否可以装满整个背包
     */
    boolean canFillBackpack(int[] weight, int w);

    /**
     * 3. 正好装满背包最少需要多少物品
     *
     * @param weight 物品重量
     * @param w      背包最大承重
     * @return 装满背包最少需要多少物品
     */
    int minItemsCanFillBackpack(int[] weight, int w);

    /**
     * 4. 装满背包有多少种装法
     *
     * @param weight 物品重量
     * @param w      背包最大承重
     * @return 可达最大承重
     */
    int manyWaysToFillBackpack(int[] weight, int w);
}

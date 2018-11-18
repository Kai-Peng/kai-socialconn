package com.kai.socialconn.service;

public interface RedisRedPacketService {
    /**
     * save redis grap redpocket list
     * @param redPacketId
     * @param unitAmount
     */
    public void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount);

    /**
     * grab red package
     * @param redPacketId
     * @param userId
     * @return
     * 0-no repository,failed
     * 1-success, remain >1
     * 2-success, the last red packet
     */
    public long grapRedPacketByRedis(Long redPacketId, Long userId);
}

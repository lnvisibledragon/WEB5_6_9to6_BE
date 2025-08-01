package com.grepp.spring.app.model.reward.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_set_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemSetMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mappingId;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private ItemSet itemSet;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private RewardItem rewardItem;



    @Builder
    public ItemSetMapping(Long mappingId, ItemSet itemSet, RewardItem rewardItem) {
        this.mappingId = mappingId;
        this.itemSet = itemSet;
        this.rewardItem = rewardItem;
    }
}

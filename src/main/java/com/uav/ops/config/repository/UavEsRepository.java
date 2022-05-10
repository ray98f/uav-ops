package com.uav.ops.config.repository;

import com.uav.ops.entity.Uav;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UavEsRepository extends ElasticsearchRepository<Uav, String> {
 
}
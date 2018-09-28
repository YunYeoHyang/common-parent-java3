package com.czxy.bos.es.repository;

import com.czxy.bos.es.domain.EsWayBill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WayBillRepository extends ElasticsearchRepository<EsWayBill , Integer> {
}

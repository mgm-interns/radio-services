package com.mgmtp.radio.respository.user;

import com.mgmtp.radio.domain.user.RecentStation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RecentStationRepository extends ReactiveMongoRepository<RecentStation, String> {
    Flux<RecentStation> findByUserId(String userId);
}
package com.mgmtp.radio.service.user;

import com.mgmtp.radio.dto.user.FavoriteSongDTO;
import com.mgmtp.radio.exception.RadioNotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteSongService {

	Mono<FavoriteSongDTO> create(String userId, FavoriteSongDTO favoriteSongDTO);

	Flux<FavoriteSongDTO> findByUserId(String userId);

	Mono<FavoriteSongDTO> findByUserIdAndSongId(String userId, String songId) throws RadioNotFoundException;

	Mono<FavoriteSongDTO> delete(String favoriteSongId, String userId) throws RadioNotFoundException;
}